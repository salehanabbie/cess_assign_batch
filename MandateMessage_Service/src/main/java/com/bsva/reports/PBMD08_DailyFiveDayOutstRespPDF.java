package com.bsva.reports;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import org.apache.commons.lang3.time.DurationFormatUtils;
import org.apache.log4j.Logger;

import com.bsva.commons.model.CreditorBankModel;
import com.bsva.commons.model.DebtorBankModel;
import com.bsva.commons.model.SysctrlCompParamModel;
import com.bsva.commons.model.SystemParameterModel;
import com.bsva.entities.MandateResponseOutstandingPerBankEntityModel;
import com.bsva.entities.MdtCnfgReportNamesEntity;
import com.bsva.entities.MdtOpsRepSeqNrEntity;
import com.bsva.interfaces.AdminBeanRemote;
import com.bsva.interfaces.PropertyUtilRemote;
import com.bsva.interfaces.ReportBeanRemote;
import com.bsva.interfaces.ServiceBeanRemote;
import com.bsva.interfaces.ValidationBeanRemote;
import com.bsva.utils.DateUtil;
import com.bsva.utils.Util;
import com.google.common.io.Files;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * @author SalehaR-2019/11/13
 * @author SalehaR-2019/11/21-Align to v2.0 of spec
 *
 */
public class PBMD08_DailyFiveDayOutstRespPDF {
	String reportName, reportNr;
	public static Logger log=Logger.getLogger("PBMD08_5DayOutsRespPDF");

	public TreeMap<String, List<MandateResponseOutstandingPerBankEntityModel>> pbmd08Map;

	List<CreditorBankModel> creditorBankModelList;
	CreditorBankModel creditorBankModel ;
	SystemParameterModel systemParameterModel = new SystemParameterModel();

	int lastSeqNoUsed;

	String pbmd08, reportDir = null, tempDir = null, mdtLoadType = null;
	String pdfFileName = null;

	public static ServiceBeanRemote beanRemote;
	public static ValidationBeanRemote valBeanRemote;
	private static AdminBeanRemote adminBeanRemote;
	private static PropertyUtilRemote propertyUtilRemote;
	private static ReportBeanRemote reportBeanRemote;
	long startTime, endTime, duration;
	
	DateFormat dateFormatFront = new SimpleDateFormat("yyyy-MM-dd");

	MdtCnfgReportNamesEntity reportNameEntity = new MdtCnfgReportNamesEntity();
	String activeIndicator = "Y";

	public PBMD08_DailyFiveDayOutstRespPDF() {
		contextAdminBeanCheck();
		contextCheck();
		contextValidationBeanCheck();
		contextPropertyFileCheck();
		contextReportCheck();

		try
		{
			tempDir = propertyUtilRemote.getPropValue("ExtractTemp.Out");
			reportDir= propertyUtilRemote.getPropValue("Reports.Output");
			pbmd08 = propertyUtilRemote.getPropValue("RPT.BANK.BATCH.5DAYOUTRESP");
			mdtLoadType = propertyUtilRemote.getPropValue("MDT.LOAD.TYPE");
		}
		catch(Exception ex)
		{
			log.error("PBMD08- Could not find MandateMessageCommons.properties in classpath");	
			reportDir = "/home/opsjava/Delivery/Mandates/Output/Reports/";
			tempDir="/home/opsjava/Delivery/Mandates/Output/temp/";
		}

		//Retrieve Report Name
		reportNameEntity = (MdtCnfgReportNamesEntity) adminBeanRemote.retrieveReportName(pbmd08);
		if(reportNameEntity != null)
		{
			reportNr = reportNameEntity.getReportNr();
			reportName = reportNameEntity.getReportName();
		}

		systemParameterModel = (SystemParameterModel) adminBeanRemote.retrieveWebActiveSysParameter();
	}

	public void generate5DayOutstRespPerBank(boolean frontEnd,Date frontEndDate)
	{
		if(reportNameEntity != null) {
			if(reportNameEntity.getActiveInd().equalsIgnoreCase(activeIndicator)) {
				
				creditorBankModelList = new ArrayList<CreditorBankModel>();
				creditorBankModelList = (List<CreditorBankModel>) adminBeanRemote.retrieveActiveCreditorBank();

				if(creditorBankModelList != null && creditorBankModelList.size() > 0)
				{
					//Retrieve data
					loadData(frontEnd,frontEndDate);

					for (CreditorBankModel creditorBankModel : creditorBankModelList) 
					{
						startTime = System.nanoTime();
						List<MandateResponseOutstandingPerBankEntityModel> batchOutsRespList = pbmd08Map.get(creditorBankModel.getMemberNo());

						log.info("GENERATING REPORT PBMD08 FOR "+creditorBankModel.getMemberNo());

						if(batchOutsRespList!= null && batchOutsRespList.size() > 0)
						{
							//					log.debug("batchOutsRespList--->"+batchOutsRespList);
							try
							{
								generateReportDetail(creditorBankModel.getMemberNo(),creditorBankModel.getMemberName(), batchOutsRespList,frontEnd,frontEndDate);
							}
							catch(DocumentException | FileNotFoundException ex)
							{
								log.error("Error PBMD08:  generate5DayOutstRespPerBank"+ ex.getMessage());
								ex.printStackTrace();
							}
						}
						endTime = System.nanoTime();
						duration = (endTime - startTime) / 1000000;
						log.info("[Total Duration for Report is: "+DurationFormatUtils.formatDuration(duration, "HH:mm:ss.S")+" milliseconds |"); 
					}
				}
			}
		}
	}

	public void generateReportDetail(String memberId,String memberName,List<MandateResponseOutstandingPerBankEntityModel>mdtRespOutstandingList,boolean frontEnd,Date frontEndDate)throws DocumentException, FileNotFoundException
	{
		DecimalFormat df = new DecimalFormat("### ### ### ### ### ##0.00");
		DateFormat dateTimeFormat = new SimpleDateFormat("yyyyMMdd_HH:mm:ss");
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat fileTimeFormat = new SimpleDateFormat("MM-yyyy");
		Date currentDate = new Date();
		String strSeqNo; 
		DateFormat endDateFormat = new SimpleDateFormat("ddMMyyyy");

		creditorBankModel = new CreditorBankModel();
		int pageNo = 0;

		String bankId= memberId.substring(2, 6);
		log.debug("bankId---->"+bankId);

		MdtOpsRepSeqNrEntity mdtOpsRepSeqNrEntity = new MdtOpsRepSeqNrEntity();
		mdtOpsRepSeqNrEntity = (MdtOpsRepSeqNrEntity)adminBeanRemote.retrieveRepSeqNr(reportNr,memberId);

		if(mdtOpsRepSeqNrEntity != null)
		{
			lastSeqNoUsed = Integer.valueOf(mdtOpsRepSeqNrEntity.getLastSeqNo());
			lastSeqNoUsed = lastSeqNoUsed + 1;
		}
		else
			lastSeqNoUsed = 1;

		strSeqNo = String.format("%06d",lastSeqNoUsed);
		mdtOpsRepSeqNrEntity.setLastSeqNo(strSeqNo);
		adminBeanRemote.updateReportSeqNr(mdtOpsRepSeqNrEntity);

		String reportSeqNo = strSeqNo.substring(3,6);
		
		String files;

		if(frontEnd) {
			files = tempDir+bankId+"AC"+reportNr+endDateFormat.format(frontEndDate).toString()+"."+reportSeqNo+".pdf";
			pdfFileName = bankId+"AC"+reportNr+endDateFormat.format(frontEndDate).toString()+"."+reportSeqNo+".pdf";
		}else {
			files = tempDir+bankId+"AC"+reportNr+endDateFormat.format(currentDate).toString()+"."+reportSeqNo+".pdf";
			pdfFileName = bankId+"AC"+reportNr+endDateFormat.format(currentDate).toString()+"."+reportSeqNo+".pdf";
		}

		SysctrlCompParamModel companyParamModel = new SysctrlCompParamModel();
		companyParamModel = (SysctrlCompParamModel) beanRemote.retrieveCompanyParameters();

		Document document = new Document(PageSize.A4.rotate());
		FileOutputStream fileOutputStream = new FileOutputStream(files);
		PageEvent pageEvent = new PageEvent(companyParamModel, systemParameterModel, reportNr, reportName, true, false, memberName, memberId, null, null,null, null,true);

		PdfWriter writer = null;
		writer = PdfWriter.getInstance(document, fileOutputStream);
		writer.setPageEvent(pageEvent);

		writer.open();
		document.open();

		File file = new File(files);

		PdfPTable freeline = new PdfPTable(1);
		freeline.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		freeline.setWidthPercentage(100);
		freeline.addCell( new PdfPCell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 8)))).setBorderColor(BaseColor.WHITE);

		String strMonth = null;
		String month = null;
		String year = null;
		Date firstDate, lastDate = null;
		String strFirstDate = null, strLastDate = null;

		String strProcDate = null;
		if(systemParameterModel.getProcessDate() != null)
		{
			strMonth =fileTimeFormat.format(systemParameterModel.getProcessDate()).substring(0,2);
			log.debug("strMonth ==> "+strMonth);
			month= DateUtil.getMonthFullname(Integer.valueOf(strMonth), true);
			log.debug("Process Month----->"+month);
			year = String.valueOf(DateUtil.getYear(systemParameterModel.getProcessDate()));
			log.debug("year----->"+year);

			//Calculate the last date of the month
			Calendar nextNotifTime = Calendar.getInstance();
			nextNotifTime.add(Calendar.MONTH, 1);
			nextNotifTime.set(Calendar.DATE, 1);
			nextNotifTime.add(Calendar.DATE, -1);
			lastDate = nextNotifTime.getTime();

			strFirstDate = "01"+strMonth+year;
			strLastDate = endDateFormat.format(lastDate);
			log.debug("lastDate ==> "+ lastDate);
			log.debug("endDate ==> "+ endDateFormat.format(lastDate));
		}

		int respondDate = 0;
		if(mdtRespOutstandingList!= null && mdtRespOutstandingList.size()> 0)
		{
			for(MandateResponseOutstandingPerBankEntityModel mdtResponseOutstandingModel : mdtRespOutstandingList)
			{
				PdfPTable batchDetails = new PdfPTable(9);
				batchDetails.setWidthPercentage(100);

				PdfPCell creditorBankData = new PdfPCell(new Phrase(mdtResponseOutstandingModel.getDbtrMemberName(),FontFactory.getFont(FontFactory.HELVETICA,8, com.itextpdf.text.Font.NORMAL)));
				creditorBankData.setColspan(2);
				creditorBankData.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
				creditorBankData.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
				batchDetails.addCell(creditorBankData);

				PdfPCell memberNoData = new PdfPCell(new Phrase(mdtResponseOutstandingModel.getDbtrMemberNo(),FontFactory.getFont(FontFactory.HELVETICA,8, com.itextpdf.text.Font.NORMAL)));
				memberNoData.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
				memberNoData.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
				batchDetails.addCell(memberNoData);

				PdfPCell fileNameData = new PdfPCell(new Phrase(mdtResponseOutstandingModel.getFileName(),FontFactory.getFont(FontFactory.HELVETICA,8, com.itextpdf.text.Font.NORMAL)));
				fileNameData.setColspan(2);
				fileNameData.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
				fileNameData.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
				batchDetails.addCell(fileNameData);

				PdfPCell transTpyeData = new PdfPCell(new Phrase(mdtResponseOutstandingModel.getTransType(),FontFactory.getFont(FontFactory.HELVETICA,8, com.itextpdf.text.Font.NORMAL)));
				transTpyeData.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
				transTpyeData.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
				batchDetails.addCell(transTpyeData);

				PdfPCell mdtReqTransIdData = new PdfPCell(new Phrase(mdtResponseOutstandingModel.getMandateReqTransId(),FontFactory.getFont(FontFactory.HELVETICA,8, com.itextpdf.text.Font.NORMAL)));
				mdtReqTransIdData.setColspan(2);
				mdtReqTransIdData.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
				mdtReqTransIdData.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
				batchDetails.addCell(mdtReqTransIdData);

				PdfPCell serviceIdData = new PdfPCell(new Phrase(mdtResponseOutstandingModel.getServiceId(),FontFactory.getFont(FontFactory.HELVETICA,8, com.itextpdf.text.Font.NORMAL)));
				serviceIdData.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
				serviceIdData.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
				batchDetails.addCell(serviceIdData);  

				document.add(batchDetails);
			}

			document.close();
		}
		else
		{
			log.info("THE SUMMARY TOTALS LIST IS EMPTY!!!!!!!!");
		}

		//Copy the report to the Output reports folder 
		try
		{
			copyFile(pdfFileName);
		}
		catch(IOException ioe)
		{
			log.error("Error on copying PBMD08 report to temp "+ioe.getMessage());
			ioe.printStackTrace();
		}
		catch(Exception ex)
		{
			log.error("Error on copying PBMD08 report to temp "+ex.getMessage());

			ex.printStackTrace();
		}
	}


	public  void copyFile(String fileName) throws IOException 
	{
		File tmpFile = new File(tempDir, fileName);
		File destFile = new File(reportDir, fileName);

		Files.copy(tmpFile, destFile);
		log.info("Copying "+fileName+" from temp to output directory...");
	}

	public void loadData(boolean frontEnd,Date frontEndDate) {
		pbmd08Map = new TreeMap<String, List<MandateResponseOutstandingPerBankEntityModel>>();
		List<MandateResponseOutstandingPerBankEntityModel> allDataList;
		
		if(frontEnd) {
			allDataList = (List<MandateResponseOutstandingPerBankEntityModel>) reportBeanRemote.retrievePBMD08Data(dateFormatFront.format(frontEndDate));
		}else {
			allDataList = (List<MandateResponseOutstandingPerBankEntityModel>) reportBeanRemote.retrievePBMD08Data(dateFormatFront.format(systemParameterModel.getProcessDate()));
		}
		
		List<MandateResponseOutstandingPerBankEntityModel> dataPerCreditorBank;

		if(allDataList != null && allDataList.size() > 0) {
			for (CreditorBankModel creditorBankModel : creditorBankModelList) {
				dataPerCreditorBank = new ArrayList<MandateResponseOutstandingPerBankEntityModel>();

				for (MandateResponseOutstandingPerBankEntityModel mndtRespEntity : allDataList) {
					if(mndtRespEntity.getCrdMemberNo().equalsIgnoreCase(creditorBankModel.getMemberNo())) {
						dataPerCreditorBank.add(mndtRespEntity);
					}
				}
				pbmd08Map.put(creditorBankModel.getMemberNo(), dataPerCreditorBank);
			}   
		}		
		log.info("pbmd08Map size===> "+pbmd08Map.size());
	}

	//CONTEXT LOOKUPS//
	public static void contextValidationBeanCheck()	{
		if (valBeanRemote == null) {
			valBeanRemote = Util.getValidationBean();
		}
	}

	private static void contextCheck() {
		if (beanRemote == null) {
			beanRemote = Util.getServiceBean();
		}
	}

	private static void contextAdminBeanCheck() {
		if (adminBeanRemote == null) {
			adminBeanRemote = Util.getAdminBean();	
		}
	}

	private static void contextPropertyFileCheck() {
		if (propertyUtilRemote == null) {
			propertyUtilRemote = Util.getPropertyUtil();
		}
	}

	private static void contextReportCheck() {
		if (reportBeanRemote == null) {
			reportBeanRemote = Util.getReportBean();
		}
	}
}
