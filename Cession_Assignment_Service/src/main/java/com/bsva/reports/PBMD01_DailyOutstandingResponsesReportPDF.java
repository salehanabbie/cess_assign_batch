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
import com.bsva.commons.model.DebtorBankModel;
import com.bsva.commons.model.SysctrlCompParamModel;
import com.bsva.commons.model.SystemParameterModel;
import com.bsva.entities.MandateResponseOutstandingPerBankEntityModel;
import com.bsva.entities.CasCnfgReportNamesEntity;
import com.bsva.entities.CasOpsRepSeqNrEntity;
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
 * @author SalehaR
 * @author SalehaR-2019/05/06 Debug Statements & Code CleanUp
 * @author SalehaR-2019/09/30 Align Report to Single Table
 * @author SalehaR-2019/11/10 Align Report to new requirements(Remove R/T)
 */
public class PBMD01_DailyOutstandingResponsesReportPDF 
{
	String reportName, reportNr;
	public static Logger log=Logger.getLogger("PBMD01_Outst Resp PDF");

	public TreeMap<String, List<MandateResponseOutstandingPerBankEntityModel>> pbmd01Map;

	List<DebtorBankModel> debtorBankModelList;
	//	List<MandateResponseOutstandingPerBankModel> pbmd01List;
	DebtorBankModel debtorBankModel ;
	SystemParameterModel systemParameterModel = new SystemParameterModel();

	int lastSeqNoUsed;

	String pbmd01, reportDir = null, tempDir = null, mdtLoadType = null;
	String pdfFileName = null;

	public static ServiceBeanRemote beanRemote;
	public static ValidationBeanRemote valBeanRemote;
	private static AdminBeanRemote adminBeanRemote;
	private static PropertyUtilRemote propertyUtilRemote;
	private static ReportBeanRemote reportBeanRemote;
	long startTime, endTime, duration;
	
	DateFormat dateFormatFront = new SimpleDateFormat("yyyy-MM-dd");

	CasCnfgReportNamesEntity reportNameEntity = new CasCnfgReportNamesEntity();
	String activeIndicator = "Y";

	public PBMD01_DailyOutstandingResponsesReportPDF()
	{
		contextAdminBeanCheck();
		contextCheck();
		contextValidationBeanCheck();
		contextPropertyFileCheck();
		contextReportCheck();

		try
		{
			tempDir = propertyUtilRemote.getPropValue("ExtractTemp.Out");
			reportDir= propertyUtilRemote.getPropValue("Reports.Output");
			pbmd01 = propertyUtilRemote.getPropValue("RPT.BANK.OUTSRESP");
			mdtLoadType = propertyUtilRemote.getPropValue("MDT.LOAD.TYPE");
		}
		catch(Exception ex)
		{
			log.error("PBMD01- Could not find MandateMessageCommons.properties in classpath");	
			reportDir = "/home/opsjava/Delivery/Mandates/Output/Reports/";
			tempDir="/home/opsjava/Delivery/Mandates/Output/temp/";
		}

		//Retrieve Report Name
		reportNameEntity = (CasCnfgReportNamesEntity) adminBeanRemote.retrieveReportName(pbmd01);
		if(reportNameEntity != null)
		{
			reportNr = reportNameEntity.getReportNr();
			reportName = reportNameEntity.getReportName();
		}

		systemParameterModel = (SystemParameterModel) adminBeanRemote.retrieveWebActiveSysParameter();
	}
	
	public void generateMandateResponseOutstandingPerBank(Date frontEndDate) {
		if (reportNameEntity != null) {

			if (reportNameEntity.getActiveInd().equalsIgnoreCase(activeIndicator)) {

				debtorBankModelList = new ArrayList<DebtorBankModel>();
				debtorBankModelList = (List<DebtorBankModel>) adminBeanRemote.retrieveActiveDebtorBank();


				if (debtorBankModelList != null && debtorBankModelList.size() > 0) {
					// Retrieve data
					if (frontEndDate != null) {
						loadData(dateFormatFront.format(frontEndDate));
					} else {
						loadData(dateFormatFront.format(systemParameterModel.getProcessDate()));
					}

					for (DebtorBankModel debtorBankModel : debtorBankModelList) {
						startTime = System.nanoTime();
						List<MandateResponseOutstandingPerBankEntityModel> batchOutsRespList = pbmd01Map.get(debtorBankModel.getMemberNo());

						log.info("GENERATING REPORT PBMD01 FOR " + debtorBankModel.getMemberNo());

//						2021/10/26 NCH-252660 SalehaR - Create null reports for banks
//						if (batchOutsRespList != null && batchOutsRespList.size() > 0) {
							// log.debug("batchOutsRespList--->"+batchOutsRespList);
							try {
								generateReportDetail(debtorBankModel.getMemberNo(), debtorBankModel.getMemberName(),batchOutsRespList,frontEndDate);
							} catch (DocumentException | FileNotFoundException ex) {
								log.error("Error generating mdtRespOutstandingReport " + ex.getMessage());
								ex.printStackTrace();
							}
//						}
						endTime = System.nanoTime();
						duration = (endTime - startTime) / 1000000;
						log.info("[Total Duration for Report is: " + DurationFormatUtils.formatDuration(duration, "HH:mm:ss.S") + " milliseconds |");
					}
				}
			}

		}

	}
	
	public void generateReportDetail(String memberId,String memberName,List<MandateResponseOutstandingPerBankEntityModel>mdtRespOutstandingList,Date frontEndDate)throws DocumentException, FileNotFoundException
	{
		DecimalFormat df = new DecimalFormat("### ### ### ### ### ##0.00");
		DateFormat dateTimeFormat = new SimpleDateFormat("yyyyMMdd_HH:mm:ss");
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat fileTimeFormat = new SimpleDateFormat("MM-yyyy");
		Date currentDate = new Date();
		String strSeqNo; 
		DateFormat endDateFormat = new SimpleDateFormat("ddMMyyyy");

		debtorBankModel	= new DebtorBankModel();
		int pageNo = 0;

		String bankId= memberId.substring(2, 6);
		log.debug("bankId---->"+bankId);

		CasOpsRepSeqNrEntity casOpsRepSeqNrEntity = new CasOpsRepSeqNrEntity();
		casOpsRepSeqNrEntity = (CasOpsRepSeqNrEntity)adminBeanRemote.retrieveRepSeqNr(reportNr,memberId);

		if(casOpsRepSeqNrEntity != null)
		{
			lastSeqNoUsed = Integer.valueOf(casOpsRepSeqNrEntity.getLastSeqNo());
			lastSeqNoUsed = lastSeqNoUsed + 1;
		}
		else
			lastSeqNoUsed = 1;

		strSeqNo = String.format("%06d",lastSeqNoUsed);
		casOpsRepSeqNrEntity.setLastSeqNo(strSeqNo);
		adminBeanRemote.updateReportSeqNr(casOpsRepSeqNrEntity);

		String reportSeqNo = strSeqNo.substring(3,6);

		String files;
		if(frontEndDate != null) {
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

				PdfPCell creditorBankData = new PdfPCell(new Phrase(mdtResponseOutstandingModel.getCrdMemberName(),FontFactory.getFont(FontFactory.HELVETICA,8, com.itextpdf.text.Font.NORMAL)));
				creditorBankData.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
				creditorBankData.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
				batchDetails.addCell(creditorBankData);

				PdfPCell memberNoData = new PdfPCell(new Phrase(mdtResponseOutstandingModel.getCrdMemberNo(),FontFactory.getFont(FontFactory.HELVETICA,8, com.itextpdf.text.Font.NORMAL)));
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

				PdfPCell totalNumberOutDataCell = new PdfPCell(new Phrase(String.valueOf(mdtResponseOutstandingModel.getNrDaysOutstanding()),FontFactory.getFont(FontFactory.HELVETICA,8, com.itextpdf.text.Font.NORMAL)));
				totalNumberOutDataCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
				totalNumberOutDataCell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
				batchDetails.addCell(totalNumberOutDataCell);

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

			PdfPTable batchDetails = new PdfPTable(9);
			batchDetails.setWidthPercentage(100);

			PdfPCell creditorBankData = new PdfPCell(new Phrase(null,FontFactory.getFont(FontFactory.HELVETICA,8, com.itextpdf.text.Font.NORMAL)));
			creditorBankData.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			creditorBankData.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
			batchDetails.addCell(creditorBankData);

			PdfPCell memberNoData = new PdfPCell(new Phrase(null,FontFactory.getFont(FontFactory.HELVETICA,8, com.itextpdf.text.Font.NORMAL)));
			memberNoData.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			memberNoData.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
			batchDetails.addCell(memberNoData);

			PdfPCell fileNameData = new PdfPCell(new Phrase("NO OUTSTANDING RESPONSES",FontFactory.getFont(FontFactory.HELVETICA,10, com.itextpdf.text.Font.NORMAL)));
			fileNameData.setColspan(3);
			fileNameData.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			fileNameData.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
			batchDetails.addCell(fileNameData);

			PdfPCell transTpyeData = new PdfPCell(new Phrase(null,FontFactory.getFont(FontFactory.HELVETICA,8, com.itextpdf.text.Font.NORMAL)));
			transTpyeData.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			transTpyeData.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
			batchDetails.addCell(transTpyeData);

			PdfPCell totalNumberOutDataCell = new PdfPCell(new Phrase(null,FontFactory.getFont(FontFactory.HELVETICA,8, com.itextpdf.text.Font.NORMAL)));
			totalNumberOutDataCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			totalNumberOutDataCell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
			batchDetails.addCell(totalNumberOutDataCell);

			PdfPCell mdtReqTransIdData = new PdfPCell(new Phrase(null,FontFactory.getFont(FontFactory.HELVETICA,8, com.itextpdf.text.Font.NORMAL)));
			mdtReqTransIdData.setColspan(2);
			mdtReqTransIdData.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			mdtReqTransIdData.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
			batchDetails.addCell(mdtReqTransIdData);
 
			document.add(batchDetails);
			document.close();
		}

		//Copy the report to the Output reports folder 
		try
		{
			copyFile(pdfFileName);
		}
		catch(IOException ioe)
		{
			log.error("Error on copying PBMD01 report to temp "+ioe.getMessage());
			ioe.printStackTrace();
		}
		catch(Exception ex)
		{
			log.error("Error on copying PBMD01 report to temp "+ex.getMessage());

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

	public void loadData(String frontEndDate) {
		pbmd01Map = new TreeMap<String, List<MandateResponseOutstandingPerBankEntityModel>>();
		List<MandateResponseOutstandingPerBankEntityModel> allDataList;
		
		if(frontEndDate != null) {
			allDataList = (List<MandateResponseOutstandingPerBankEntityModel>) reportBeanRemote.retrievePBMD01Data(frontEndDate);
		}else {
			allDataList = (List<MandateResponseOutstandingPerBankEntityModel>) reportBeanRemote.retrievePBMD01Data(dateFormatFront.format(systemParameterModel.getProcessDate()));
		}
		
		List<MandateResponseOutstandingPerBankEntityModel> dataPerDebtorBank;

		if(allDataList != null && allDataList.size() > 0) {
			for (DebtorBankModel debtorBankModel : debtorBankModelList) {
				dataPerDebtorBank = new ArrayList<MandateResponseOutstandingPerBankEntityModel>();

				for (MandateResponseOutstandingPerBankEntityModel mndtRespEntity : allDataList) {
					if(mndtRespEntity.getDbtrMemberNo().equalsIgnoreCase(debtorBankModel.getMemberNo())) {
						dataPerDebtorBank.add(mndtRespEntity);
					}
				}
				pbmd01Map.put(debtorBankModel.getMemberNo(), dataPerDebtorBank);
			}   
		}		
		log.info("pbmd01Map size===> "+pbmd01Map.size());
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
