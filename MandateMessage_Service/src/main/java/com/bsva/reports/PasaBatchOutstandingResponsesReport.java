package com.bsva.reports;
import java.io.File;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.bsva.commons.model.*;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.apache.log4j.Logger;

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
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * @author SalehaR
 * @author SalehaR-2019/05/06 Debug Statements & Code CleanUp
 * @author SalehaR-2019/09/30 Align to Single Table
 */
public class PasaBatchOutstandingResponsesReport 
{
	//	private String downloaddirectory ="/home/opsjava/Delivery/Mandates/Reports/";
	public static Logger log=Logger.getLogger(PasaBatchOutstandingResponsesReport.class);
	//int fileSeqNo =000;
	boolean respModel = false;
	String reportName = null, reportNr = null, reportDir = null, tempDir = null;
	String pdfFileName = null,  psmd01;
	public static ServiceBeanRemote beanRemote;
	public static ValidationBeanRemote valBeanRemote;
	private static AdminBeanRemote adminBeanRemote;
	private static PropertyUtilRemote propertyUtilRemote;
	private static ReportBeanRemote reportBeanRemote;
	List<DebtorBankModel> debtorBankModelList;
	DebtorBankModel debtorBankModel ;
	List<CreditorBankModel> creditorBankModelList;
	CreditorBankModel creditorBankModel ;
	MdtCnfgReportNamesEntity reportNameEntity;
	String memberId = "0000";
	String mdtLoadType;

	private BigDecimal noOfInitOneDayTot = new BigDecimal(0);
	private BigDecimal noOfInitTwoDayTot = new BigDecimal(0);
	private BigDecimal noOdfAmendOneDayTot = new BigDecimal(0);
	private BigDecimal noOdfAmendTwoDayTot = new BigDecimal(0);
	private BigDecimal noOfCancOneDayTot = new BigDecimal(0);
	private BigDecimal noOfCancTwoDayTot = new BigDecimal(0);

	long startTime, endTime, duration;
	
	String activeIndicator = "Y";

	public void generateReport(Date frontDate) throws FileNotFoundException, DocumentException
	{
		contextAdminBeanCheck();
		contextCheck();
		contextValidationBeanCheck();
		contextPropertyFileCheck();
		contextReportCheck();

		startTime = System.nanoTime();

		try
		{
			tempDir = propertyUtilRemote.getPropValue("ExtractTemp.Out");
//			log.info("tempDir ==> "+tempDir);
			reportDir= propertyUtilRemote.getPropValue("Reports.Output");
//			log.info("reportDir ==> "+reportDir);
			psmd01 = propertyUtilRemote.getPropValue("RPT.PASA.BATCH.OUTSRESP");
			mdtLoadType = propertyUtilRemote.getPropValue("MDT.LOAD.TYPE");
		}
		catch(Exception ex)
		{
			log.error("PSMD01- Could not find MandateMessageCommons.properties in classpath");	
			reportDir = "/home/opsjava/Delivery/Mandates/Output/Reports/";
			tempDir="/home/opsjava/Delivery/Mandates/Output/temp/";
		}

		//Retrieve Report Name
		reportNameEntity= new MdtCnfgReportNamesEntity();
		reportNameEntity = (MdtCnfgReportNamesEntity) adminBeanRemote.retrieveReportName(psmd01);
//		log.info("reportNameEntity ==>"+reportNameEntity);
		if(reportNameEntity != null)
		{
			reportNr = reportNameEntity.getReportNr();
			reportName = reportNameEntity.getReportName();
			
			if(reportNameEntity.getActiveInd().equalsIgnoreCase(activeIndicator)) {
				generateReportColumns( reportNr,reportName,frontDate);
				endTime = System.nanoTime();
				duration = (endTime - startTime) / 1000000;
				log.info("[Total Duration for PSMD01 Report is: "+ DurationFormatUtils.formatDuration(duration, "HH:mm:ss.S")+" milliseconds |");
			}
		}
	}

	public void generateReportColumns( String reportNr,String reportName, Date frontDate )  throws DocumentException, FileNotFoundException
	{
		DecimalFormat df = new DecimalFormat("### ### ### ### ### ##0.00");
		DateFormat dateTimeFormat = new SimpleDateFormat("yyyyMMdd _HH:mm:ss");
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat fileTimeFormat = new SimpleDateFormat("MM-yyyy");
		DateFormat endDateFormat = new SimpleDateFormat("ddMMyyyy");
		Date currentDate = new Date();
		DateFormat transDateFormat = new SimpleDateFormat("yyyyMMdd");
		int pageNo = 1;

		//fileSeqNo =fileSeqNo + 1;
		int lastSeqNoUsed;
		String strSeqNo; 
		MdtOpsRepSeqNrEntity mdtOpsRepSeqNrEntity = new MdtOpsRepSeqNrEntity();
		mdtOpsRepSeqNrEntity = (MdtOpsRepSeqNrEntity)adminBeanRemote.retrieveRepSeqNr(reportNr,memberId);
//		log.info("retrieve seq number : "  + mdtOpsRepSeqNrEntity );

		if(mdtOpsRepSeqNrEntity != null)
		{
			lastSeqNoUsed = Integer.valueOf(mdtOpsRepSeqNrEntity.getLastSeqNo());
			lastSeqNoUsed = lastSeqNoUsed + 1;
		}
		else
		{
			lastSeqNoUsed = 1;
		}
		strSeqNo = String.format("%06d",lastSeqNoUsed);
		mdtOpsRepSeqNrEntity.setLastSeqNo(strSeqNo);
		adminBeanRemote.updateReportSeqNr(mdtOpsRepSeqNrEntity);
		
		String reportSeqNo = strSeqNo.substring(3,6);
		String files;

		if(frontDate != null) {
			files = tempDir+"0000AC"+reportNr+endDateFormat.format(frontDate).toString()+"."+reportSeqNo + ".pdf";
			pdfFileName = "0000AC"+reportNr+endDateFormat.format(frontDate).toString()+"."+reportSeqNo + ".pdf";
		}else {
			files = tempDir+"0000AC"+reportNr+endDateFormat.format(currentDate).toString()+"."+reportSeqNo + ".pdf";
			pdfFileName = "0000AC"+reportNr+endDateFormat.format(currentDate).toString()+"."+reportSeqNo + ".pdf";
		}

		SysctrlCompParamModel companyParamModel = new SysctrlCompParamModel();
		companyParamModel = (SysctrlCompParamModel) beanRemote.retrieveCompanyParameters();

		SystemParameterModel systemParameterModel = new SystemParameterModel();
		systemParameterModel =(SystemParameterModel) adminBeanRemote.retrieveWebActiveSysParameter();

		Document document = new Document(PageSize.A4.rotate());

		FileOutputStream fileOutputStream = new FileOutputStream(files);

		PageEvent pageEvent = new PageEvent(companyParamModel, systemParameterModel, reportNr, reportName, false, true, null, null, null, null, null, null,true);

		PdfWriter writer = null;
		writer = PdfWriter.getInstance(document, fileOutputStream);
		writer.setPageEvent(pageEvent);

		writer.open();
		document.open();

		File file = new File(files);

		//		ResourceStreamRequestHandler target = new ResourceStreamRequestHandler( new FileResourceStream(new org.apache.wicket.util.file.File(file)));
		//		PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(files));
		//		target.setFileName(files);
		//		RequestCycle.get().scheduleRequestHandlerAfterCurrent(target);
		//		document.open();



		PdfPTable freeline = new PdfPTable(1);
		freeline.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		freeline.setWidthPercentage(100);
		freeline.addCell( new PdfPCell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 8)))).setBorderColor(BaseColor.WHITE);

		//		PdfPTable firstHeading = new PdfPTable(3);
		//		firstHeading.setWidthPercentage(100);
		//
		//		try {
		//			PdfPCell cell1 = new PdfPCell(new Paragraph("Date: " + dateTimeFormat.format(currentDate), FontFactory.getFont(FontFactory.HELVETICA, 8)));
		//			cell1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		//			cell1.setBorder(Rectangle.NO_BORDER);
		//			firstHeading.addCell(cell1);
		//
		//			PdfPCell cell2 = new PdfPCell(new Paragraph(companyParamModel.getCompName(), FontFactory.getFont(FontFactory.HELVETICA, 9, com.itextpdf.text.Font.BOLD)));
		//			cell2.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		//			cell2.setBorder(Rectangle.NO_BORDER);
		//			firstHeading.addCell(cell2);
		//
		//			PdfPCell cell3 = new PdfPCell(new Paragraph("Page:  " + pageNo,   FontFactory.getFont(FontFactory.HELVETICA, 8)));
		//			cell3.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
		//			cell3.setBorder(Rectangle.NO_BORDER);
		//			firstHeading.addCell(cell3);
		//
		//		} 
		//		catch (NullPointerException x) 
		//		{
		//			log.debug("Error on Page 1 Header" + x);
		//		}
		//
		//		document.add(firstHeading);

		String strMonth = null;
		String month = null;
		String year = null;
		Date firstDate, lastDate = null;
		String strFirstDate = null, strLastDate = null;

		//		try 
		//		{
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

		//			PdfPTable secondheading = new PdfPTable(3);
		//			secondheading.setWidthPercentage(100);
		//			
		//			PdfPCell nullCell001 = new PdfPCell(new Paragraph(null,FontFactory.getFont(FontFactory.HELVETICA, 8)));
		//			nullCell001.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
		//			nullCell001.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
		//			secondheading.addCell(nullCell001);
		//
		//
		//			PdfPCell nullCell02 = new PdfPCell(new Paragraph("REG.NO."+ companyParamModel.getRegistrationNr(), FontFactory.getFont(FontFactory.HELVETICA, 8)));
		//			nullCell02.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		//			nullCell02.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
		//			secondheading.addCell(nullCell02);
		//
		//			PdfPCell nullCell03 = new PdfPCell(new Paragraph("Process Date:"+endDateFormat.format(currentDate).toString(),  FontFactory.getFont(FontFactory.HELVETICA, 8)));
		//			nullCell03.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
		//			nullCell03.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
		//			secondheading.addCell(nullCell03);
		//}
		//		catch (NullPointerException x)
		//		{
		//			log.debug("Error Finding Company Reg No" + x);
		//		}
		//		document.add(secondheading);
		//		document.add(freeline);

		//		PdfPTable reportHeading = new PdfPTable(1);
		//		reportHeading.setWidthPercentage(100);
		//
		//		PdfPCell cell1 = new PdfPCell(new Paragraph(reportNo+ ","+ reportName.toUpperCase(), FontFactory.getFont( FontFactory.HELVETICA, 9, com.itextpdf.text.Font.BOLD)));
		//		cell1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		//		cell1.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
		//		reportHeading.addCell(cell1);
		//
		//		document.add(reportHeading);
		//		document.add(freeline);

		//		PdfPTable batchDetailsHeading = new PdfPTable(8);
		//		batchDetailsHeading.setWidthPercentage(100);
		//
		//		PdfPCell credBank = new PdfPCell(new Phrase("Debtor Bank",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		//		credBank.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		//		credBank.setUseVariableBorders(true);
		//		credBank.setBorderColorTop(BaseColor.BLACK);
		//		credBank.setBorderColorBottom(BaseColor.BLACK);
		//		credBank.setBorderColorLeft(BaseColor.WHITE);
		//		credBank.setBorderColorRight(BaseColor.WHITE);
		//		batchDetailsHeading.addCell(credBank);
		//
		//		PdfPCell drMemId = new PdfPCell(new Phrase("Creditor Bank",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		//		drMemId.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		//		drMemId.setUseVariableBorders(true);
		//		drMemId.setBorderColorTop(BaseColor.BLACK);
		//		drMemId.setBorderColorBottom(BaseColor.BLACK);
		//		drMemId.setBorderColorLeft(BaseColor.WHITE);
		//		drMemId.setBorderColorRight(BaseColor.WHITE);
		//		batchDetailsHeading.addCell(drMemId);
		//
		//		PdfPCell drMemName = new PdfPCell(new Phrase("Initiation Response Outstanding - 1 Day",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		//		drMemName.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		//		drMemName.setUseVariableBorders(true);
		//		drMemName.setBorderColorTop(BaseColor.BLACK);
		//		drMemName.setBorderColorBottom(BaseColor.BLACK);
		//		drMemName.setBorderColorLeft(BaseColor.WHITE);
		//		drMemName.setBorderColorRight(BaseColor.WHITE);
		//		batchDetailsHeading.addCell(drMemName);
		//
		//		PdfPCell totalOutstCell = new PdfPCell(new Phrase("Initiation Response Outstanding - 2 Days",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		//		totalOutstCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		//		totalOutstCell.setUseVariableBorders(true);
		//		totalOutstCell.setBorderColorTop(BaseColor.BLACK);
		//		totalOutstCell.setBorderColorBottom(BaseColor.BLACK);
		//		totalOutstCell.setBorderColorLeft(BaseColor.WHITE);
		//		totalOutstCell.setBorderColorRight(BaseColor.WHITE);
		//		batchDetailsHeading.addCell(totalOutstCell);
		//
		//		PdfPCell totalNrDays = new PdfPCell(new Phrase("Amendment Response Outstanding - 1 Day",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		//		totalNrDays.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		//		totalNrDays.setUseVariableBorders(true);
		//		totalNrDays.setBorderColorTop(BaseColor.BLACK);
		//		totalNrDays.setBorderColorBottom(BaseColor.BLACK);
		//		totalNrDays.setBorderColorLeft(BaseColor.WHITE);
		//		totalNrDays.setBorderColorRight(BaseColor.WHITE);
		//		batchDetailsHeading.addCell(totalNrDays);
		//
		//		PdfPCell amendData = new PdfPCell(new Phrase("Amendment Response Outstanding - 2 Days",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		//		amendData.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		//		amendData.setUseVariableBorders(true);
		//		amendData.setBorderColorTop(BaseColor.BLACK);
		//		amendData.setBorderColorBottom(BaseColor.BLACK);
		//		amendData.setBorderColorLeft(BaseColor.WHITE);
		//		amendData.setBorderColorRight(BaseColor.WHITE);
		//		batchDetailsHeading.addCell(amendData);
		//
		//		PdfPCell cancelData = new PdfPCell(new Phrase("Cancellation Response Outstanding - 1 Day",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		//		cancelData.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		//		cancelData.setUseVariableBorders(true);
		//		cancelData.setBorderColorTop(BaseColor.BLACK);
		//		cancelData.setBorderColorBottom(BaseColor.BLACK);
		//		cancelData.setBorderColorLeft(BaseColor.WHITE);
		//		cancelData.setBorderColorRight(BaseColor.WHITE);
		//		batchDetailsHeading.addCell(cancelData);
		//
		//		PdfPCell cancelData2 = new PdfPCell(new Phrase("Cancellation Response Outstanding - 2 Days",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		//		cancelData2.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		//		cancelData2.setUseVariableBorders(true);
		//		cancelData2.setBorderColorTop(BaseColor.BLACK);
		//		cancelData2.setBorderColorBottom(BaseColor.BLACK);
		//		cancelData2.setBorderColorLeft(BaseColor.WHITE);
		//		cancelData2.setBorderColorRight(BaseColor.WHITE);
		//		batchDetailsHeading.addCell(cancelData2);
		//
		//		document.add(batchDetailsHeading);
		//		document.add(freeline);

		debtorBankModelList = new ArrayList<DebtorBankModel>();
		debtorBankModelList = (List<DebtorBankModel>)  adminBeanRemote.retrieveActiveDebtorBank();
		creditorBankModelList = new ArrayList<CreditorBankModel>();
		creditorBankModelList=(List<CreditorBankModel>) adminBeanRemote.retrieveActiveCreditorBank();


//		if(debtorBankModelList != null && debtorBankModelList.size() > 0)
//		{
//			for(DebtorBankModel debtorBankModel : debtorBankModelList)
//			{
//				String debtorName= debtorBankModel.getMemberName();
//				String debtorMember = debtorBankModel.getMemberNo();
//				boolean debtPop = false;
//
//				PdfPTable batchDataHeading = new PdfPTable(8);
//				batchDataHeading.setWidthPercentage(100);
//
//				if(creditorBankModelList != null && creditorBankModelList.size() > 0)
//				{
//					for(CreditorBankModel creditorBankModel :creditorBankModelList)
//					{
//						String creditorName= creditorBankModel.getMemberName();
//						String creditorMember = creditorBankModel.getMemberNo();
//
//						BatchOustandingResponseModel manin1Model  = new BatchOustandingResponseModel();
//						BatchOustandingResponseModel manin2Model  = new BatchOustandingResponseModel();
//						BatchOustandingResponseModel manam1Model  = new BatchOustandingResponseModel();
//						BatchOustandingResponseModel manam2Model  = new BatchOustandingResponseModel();
//						BatchOustandingResponseModel mancn1Model  = new BatchOustandingResponseModel();
//						BatchOustandingResponseModel mancn2Model  = new BatchOustandingResponseModel();
//
//						if(mdtLoadType.equalsIgnoreCase("M")) {
//							manin1Model = (BatchOustandingResponseModel) beanRemote.retrieverRespOutstandForOneDay(debtorMember,creditorMember,"MANIN");
//							manin2Model = (BatchOustandingResponseModel) beanRemote.retrieverRespOutstandForTwoDays(debtorMember,creditorMember,"MANIN");
//							manam1Model = (BatchOustandingResponseModel) beanRemote.retrieverRespOutstandForOneDay(debtorMember,creditorMember,"MANAM");
//							manam2Model = (BatchOustandingResponseModel) beanRemote.retrieverRespOutstandForTwoDays(debtorMember,creditorMember,"MANAM");
//							mancn1Model = (BatchOustandingResponseModel) beanRemote.retrieverRespOutstandForOneDay(debtorMember,creditorMember,"MANCN");
//							mancn2Model = (BatchOustandingResponseModel) beanRemote.retrieverRespOutstandForTwoDays(debtorMember,creditorMember,"MANCN");
//						}else {
//							manin1Model = (BatchOustandingResponseModel) reportBeanRemote.retrieverRespOutstandForOneDay(debtorMember,creditorMember,"MANIN");
//							manin2Model = (BatchOustandingResponseModel) reportBeanRemote.retrieverRespOutstandForTwoDays(debtorMember,creditorMember,"MANIN");
//							manam1Model = (BatchOustandingResponseModel) reportBeanRemote.retrieverRespOutstandForOneDay(debtorMember,creditorMember,"MANAM");
//							manam2Model = (BatchOustandingResponseModel) reportBeanRemote.retrieverRespOutstandForTwoDays(debtorMember,creditorMember,"MANAM");
//							mancn1Model = (BatchOustandingResponseModel) reportBeanRemote.retrieverRespOutstandForOneDay(debtorMember,creditorMember,"MANCN");
//							mancn2Model = (BatchOustandingResponseModel) reportBeanRemote.retrieverRespOutstandForTwoDays(debtorMember,creditorMember,"MANCN");
//						}
//
//						if(manin1Model.getNrOfTxns()== null)
//							manin1Model.setNrOfTxns(BigDecimal.ZERO);
//						int oneDayManin=manin1Model.getNrOfTxns().intValue();
//
//						if(manin2Model.getNrOfTxns()== null)
//							manin2Model.setNrOfTxns(BigDecimal.ZERO);
//						int twoDayManin=manin2Model.getNrOfTxns().intValue();
//
//						if(manam1Model.getNrOfTxns()== null)
//							manam1Model.setNrOfTxns(BigDecimal.ZERO);
//						int oneDayManam=manam1Model.getNrOfTxns().intValue();
//						if(manam2Model.getNrOfTxns()== null)
//
//							manam2Model.setNrOfTxns(BigDecimal.ZERO);
//						int twoDayManam=manam2Model.getNrOfTxns().intValue();
//						if(mancn1Model.getNrOfTxns()== null)
//							mancn1Model.setNrOfTxns(BigDecimal.ZERO);
//						int oneDayMancn=mancn1Model.getNrOfTxns().intValue();
//
//						if(mancn2Model.getNrOfTxns()== null)
//							mancn2Model.setNrOfTxns(BigDecimal.ZERO);
//						int twoDayMancn=mancn2Model.getNrOfTxns().intValue();
//
//						if(oneDayManin > 0 ||twoDayManin > 0 ||oneDayManam > 0 ||twoDayManam > 0 ||oneDayMancn > 0 ||twoDayMancn > 0 )
//						{
//							if(debtPop == false)
//							{
//								PdfPCell credBankData = new PdfPCell(new Phrase(debtorName,FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
//								credBankData.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
//								credBankData.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
//								batchDataHeading.addCell(credBankData);
//								debtPop = true;
//							}
//							else
//							{
//								PdfPCell nullCell001 = new PdfPCell(new Paragraph(null,FontFactory.getFont(FontFactory.HELVETICA, 8)));
//								nullCell001.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
//								nullCell001.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
//								batchDataHeading.addCell(nullCell001);
//							}
//
//							PdfPCell debBankData = new PdfPCell(new Phrase(creditorName,FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
//							debBankData.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
//							debBankData.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
//							batchDataHeading.addCell(debBankData);
//
//							PdfPCell aDataCount = new PdfPCell(new Phrase(manin1Model.getNrOfTxns().toString(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.NORMAL)));
//							aDataCount.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
//							aDataCount.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
//							batchDataHeading.addCell(aDataCount);
//
//							PdfPCell bDataCount = new PdfPCell(new Phrase(manin2Model.getNrOfTxns().toString(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.NORMAL)));
//							bDataCount.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
//							bDataCount.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
//							batchDataHeading.addCell(bDataCount);
//
//							PdfPCell cDataCount = new PdfPCell(new Phrase(manam1Model.getNrOfTxns().toString(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.NORMAL)));
//							cDataCount.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
//							cDataCount.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
//							batchDataHeading.addCell(cDataCount);
//
//							PdfPCell dDataCount = new PdfPCell(new Phrase(manam2Model.getNrOfTxns().toString(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.NORMAL)));
//							dDataCount.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
//							dDataCount.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
//							batchDataHeading.addCell(dDataCount);
//
//							PdfPCell eDataCount = new PdfPCell(new Phrase(mancn1Model.getNrOfTxns().toString(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.NORMAL)));
//							eDataCount.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
//							eDataCount.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
//							batchDataHeading.addCell(eDataCount);
//
//							PdfPCell fDataCount = new PdfPCell(new Phrase(mancn2Model.getNrOfTxns().toString(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.NORMAL)));
//							fDataCount.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
//							fDataCount.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
//							batchDataHeading.addCell(fDataCount);
//						}
//					}
//					document.add(batchDataHeading);
//
//				}
//			}
//		}

		List<BatchOustandingResponseReportModel> batchOustandingResponseReportList = new ArrayList<>();
		List<String> unigueMember = new ArrayList<>();
		
		if(frontDate != null) {
			batchOustandingResponseReportList = (List<BatchOustandingResponseReportModel>) reportBeanRemote.retrieverRespOutstand(dateFormat.format(frontDate).toString());
		}else {
			batchOustandingResponseReportList = (List<BatchOustandingResponseReportModel>) reportBeanRemote.retrieverRespOutstand(dateFormat.format(systemParameterModel.getProcessDate()).toString());
		}


		if(batchOustandingResponseReportList != null && batchOustandingResponseReportList.size() > 0) {
			for(BatchOustandingResponseReportModel batchOustandingResponseReportModel : batchOustandingResponseReportList){

				PdfPTable batchDataHeading = new PdfPTable(8);
				batchDataHeading.setWidthPercentage(100);

				if(batchOustandingResponseReportModel.getManinOneDay() == null)
					batchOustandingResponseReportModel.setManinOneDay(BigDecimal.ZERO);
				if(batchOustandingResponseReportModel.getManinTwoDay() == null)
					batchOustandingResponseReportModel.setManinTwoDay(BigDecimal.ZERO);
				if(batchOustandingResponseReportModel.getManamOneDay() == null)
					batchOustandingResponseReportModel.setManamOneDay(BigDecimal.ZERO);
				if(batchOustandingResponseReportModel.getManamTwoDay() == null)
					batchOustandingResponseReportModel.setManamTwoDay(BigDecimal.ZERO);
				if(batchOustandingResponseReportModel.getMancnOneDay() == null)
					batchOustandingResponseReportModel.setMancnOneDay(BigDecimal.ZERO);
				if (batchOustandingResponseReportModel.getMancnTwoDay() == null)
					batchOustandingResponseReportModel.setMancnTwoDay(BigDecimal.ZERO);

				noOfInitOneDayTot = noOfInitOneDayTot.add(batchOustandingResponseReportModel.getManinOneDay());
				noOfInitTwoDayTot = noOfInitTwoDayTot.add(batchOustandingResponseReportModel.getManinTwoDay());
				noOdfAmendOneDayTot = noOdfAmendOneDayTot.add(batchOustandingResponseReportModel.getManamOneDay());
				noOdfAmendTwoDayTot = noOdfAmendTwoDayTot.add(batchOustandingResponseReportModel.getManamTwoDay());
				noOfCancOneDayTot = noOfCancOneDayTot.add(batchOustandingResponseReportModel.getMancnOneDay());
				noOfCancTwoDayTot = noOfCancTwoDayTot.add(batchOustandingResponseReportModel.getMancnTwoDay());

				if(batchOustandingResponseReportModel.getManinOneDay().intValue() > 0 || batchOustandingResponseReportModel.getManinTwoDay().intValue() > 0 ||
						batchOustandingResponseReportModel.getManamOneDay().intValue() > 0 || batchOustandingResponseReportModel.getManamTwoDay().intValue() > 0 ||
						batchOustandingResponseReportModel.getMancnOneDay().intValue() > 0 || batchOustandingResponseReportModel.getMancnTwoDay().intValue() > 0 )
				{
					if(unigueMember.contains(batchOustandingResponseReportModel.getDebtorBank())){
						PdfPCell nullCell001 = new PdfPCell(new Paragraph(null,FontFactory.getFont(FontFactory.HELVETICA, 8)));
						nullCell001.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
						nullCell001.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
						batchDataHeading.addCell(nullCell001);
					}else {
						PdfPCell credBankData = new PdfPCell(new Phrase(batchOustandingResponseReportModel.getDebtorName(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
						credBankData.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
						credBankData.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
						batchDataHeading.addCell(credBankData);
						unigueMember.add(batchOustandingResponseReportModel.getDebtorBank());
					}

					PdfPCell debBankData = new PdfPCell(new Phrase(batchOustandingResponseReportModel.getCreditorName(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
					debBankData.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
					debBankData.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
					batchDataHeading.addCell(debBankData);

					PdfPCell aDataCount = new PdfPCell(new Phrase(batchOustandingResponseReportModel.getManinOneDay().toString(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.NORMAL)));
					aDataCount.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
					aDataCount.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
					batchDataHeading.addCell(aDataCount);

					PdfPCell bDataCount = new PdfPCell(new Phrase(batchOustandingResponseReportModel.getManinTwoDay().toString(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.NORMAL)));
					bDataCount.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
					bDataCount.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
					batchDataHeading.addCell(bDataCount);

					PdfPCell cDataCount = new PdfPCell(new Phrase(batchOustandingResponseReportModel.getManamOneDay().toString(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.NORMAL)));
					cDataCount.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
					cDataCount.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
					batchDataHeading.addCell(cDataCount);

					PdfPCell dDataCount = new PdfPCell(new Phrase(batchOustandingResponseReportModel.getManamTwoDay().toString(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.NORMAL)));
					dDataCount.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
					dDataCount.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
					batchDataHeading.addCell(dDataCount);

					PdfPCell eDataCount = new PdfPCell(new Phrase(batchOustandingResponseReportModel.getMancnOneDay().toString(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.NORMAL)));
					eDataCount.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
					eDataCount.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
					batchDataHeading.addCell(eDataCount);

					PdfPCell fDataCount = new PdfPCell(new Phrase(batchOustandingResponseReportModel.getMancnTwoDay().toString(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.NORMAL)));
					fDataCount.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
					fDataCount.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
					batchDataHeading.addCell(fDataCount);
				}

				document.add(batchDataHeading);

			}
		}

//		OutstandingRespSummaryCountModel manin1ModelTotal  = new OutstandingRespSummaryCountModel();
//		OutstandingRespSummaryCountModel manin2ModelTotal  = new OutstandingRespSummaryCountModel();
//		OutstandingRespSummaryCountModel manam1ModelTotal  = new OutstandingRespSummaryCountModel();
//		OutstandingRespSummaryCountModel manam2ModelTotal  = new OutstandingRespSummaryCountModel();
//		OutstandingRespSummaryCountModel mancn1ModelTotal  = new OutstandingRespSummaryCountModel();
//		OutstandingRespSummaryCountModel mancn2ModelTotal  = new OutstandingRespSummaryCountModel();
//		//POPULATE TOTALS PER DEBTOR BANK/REASON CODE
//		if(mdtLoadType.equalsIgnoreCase("M")) {
//			manin1ModelTotal = (OutstandingRespSummaryCountModel) beanRemote.retrieverRespOutstandForOneDay("MANIN");
//			manin2ModelTotal = (OutstandingRespSummaryCountModel) beanRemote.retrieverRespOutstandForTwoDays("MANIN");
//			manam1ModelTotal = (OutstandingRespSummaryCountModel)beanRemote.retrieverRespOutstandForOneDay("MANAM");
//			manam2ModelTotal = (OutstandingRespSummaryCountModel)beanRemote.retrieverRespOutstandForTwoDays("MANAM");
//			mancn1ModelTotal = (OutstandingRespSummaryCountModel)beanRemote.retrieverRespOutstandForOneDay("MANCN");
//			mancn2ModelTotal = (OutstandingRespSummaryCountModel)beanRemote.retrieverRespOutstandForTwoDays("MANCN");
//		}else {
//			manin1ModelTotal = (OutstandingRespSummaryCountModel) reportBeanRemote.retrieverRespOutstandForOneDay("MANIN");
//			manin2ModelTotal = (OutstandingRespSummaryCountModel) reportBeanRemote.retrieverRespOutstandForTwoDays("MANIN");
//			manam1ModelTotal = (OutstandingRespSummaryCountModel)reportBeanRemote.retrieverRespOutstandForOneDay("MANAM");
//			manam2ModelTotal = (OutstandingRespSummaryCountModel)reportBeanRemote.retrieverRespOutstandForTwoDays("MANAM");
//			mancn1ModelTotal = (OutstandingRespSummaryCountModel)reportBeanRemote.retrieverRespOutstandForOneDay("MANCN");
//			mancn2ModelTotal = (OutstandingRespSummaryCountModel)reportBeanRemote.retrieverRespOutstandForTwoDays("MANCN");
//		}
//
//		if(manin1ModelTotal.getNrOfDays()== null)
//			manin1ModelTotal.setNrOfDays(BigDecimal.ZERO);
//		int oneDayManinTotal=manin1ModelTotal.getNrOfDays().intValue();
//
//		if(manin2ModelTotal.getNrOfDays()== null)
//			manin2ModelTotal.setNrOfDays(BigDecimal.ZERO);
//		int twoDayManinTotal=manin2ModelTotal.getNrOfDays().intValue();
//
//		if(manam1ModelTotal.getNrOfDays()== null)
//			manam1ModelTotal.setNrOfDays(BigDecimal.ZERO);
//		int oneDayManamTotal=manam1ModelTotal.getNrOfDays().intValue();
//
//		if(manam2ModelTotal.getNrOfDays()== null)
//			manam2ModelTotal.setNrOfDays(BigDecimal.ZERO);
//		int twoDayManamTotal=manam2ModelTotal.getNrOfDays().intValue();
//
//		if(mancn1ModelTotal.getNrOfDays()== null)
//			mancn1ModelTotal.setNrOfDays(BigDecimal.ZERO);
//		int oneDayMancnTotal=mancn1ModelTotal.getNrOfDays().intValue();
//
//		if(mancn2ModelTotal.getNrOfDays()== null)
//			mancn2ModelTotal.setNrOfDays(BigDecimal.ZERO);
//		int twoDayMancnTotal=mancn2ModelTotal.getNrOfDays().intValue();
//
//		int grandTotal =0;
//		grandTotal =oneDayManinTotal +twoDayManinTotal +oneDayManamTotal +twoDayManamTotal +oneDayMancnTotal +twoDayMancnTotal ;
//
////		log.info("oneDayManinTotal ==> "+oneDayManinTotal);
////		log.info("twoDayManinTotal ==> "+twoDayManinTotal);
////
////		log.info("oneDayManamTotal ==> "+oneDayManamTotal);
////		log.info("twoDayManamTotal ==> "+twoDayManamTotal);
////
////		log.info("oneDayMancnTotal ==> "+oneDayMancnTotal);
////		log.info("twoDayMancnTotal ==> "+twoDayMancnTotal);
//
//		if(oneDayManinTotal > 0 ||twoDayManinTotal > 0 ||oneDayManamTotal > 0 ||twoDayManamTotal > 0 ||oneDayMancnTotal > 0 ||twoDayMancnTotal > 0 )
//		{
//			PdfPTable totalTable = new PdfPTable(8);
//			totalTable.setWidthPercentage(100);
//
//			PdfPCell totalHeading = new PdfPCell(new Phrase("TOTAL",FontFactory.getFont(FontFactory.HELVETICA, 9, com.itextpdf.text.Font.BOLD)));
//			totalHeading.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
//			totalHeading.setBorder(com.itextpdf.text.Rectangle.TOP | com.itextpdf.text.Rectangle.LEFT);
//			totalTable.addCell(totalHeading);
//
//			PdfPCell nullCell001 = new PdfPCell(new Paragraph(null,FontFactory.getFont(FontFactory.HELVETICA, 8)));
//			nullCell001.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
//			nullCell001.setBorder(com.itextpdf.text.Rectangle.TOP );
//			totalTable.addCell(nullCell001);
//
//			PdfPCell aDataCount = new PdfPCell(new Phrase(manin1ModelTotal.getNrOfDays().toString(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
//			aDataCount.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
//			aDataCount.setBorder(com.itextpdf.text.Rectangle.TOP | com.itextpdf.text.Rectangle.BOTTOM);
//			totalTable.addCell(aDataCount);
//
//			PdfPCell bDataCount = new PdfPCell(new Phrase(manin2ModelTotal.getNrOfDays().toString(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
//			bDataCount.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
//			bDataCount.setBorder(com.itextpdf.text.Rectangle.TOP | com.itextpdf.text.Rectangle.BOTTOM);
//			totalTable.addCell(bDataCount);
//
//			PdfPCell cDataCount = new PdfPCell(new Phrase(manam1ModelTotal.getNrOfDays().toString(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
//			cDataCount.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
//			cDataCount.setBorder(com.itextpdf.text.Rectangle.TOP | com.itextpdf.text.Rectangle.BOTTOM);
//			totalTable.addCell(cDataCount);
//
//			PdfPCell dDataCount = new PdfPCell(new Phrase(manam2ModelTotal.getNrOfDays().toString(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
//			dDataCount.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
//			dDataCount.setBorder(com.itextpdf.text.Rectangle.TOP | com.itextpdf.text.Rectangle.BOTTOM);
//			totalTable.addCell(dDataCount);
//
//			PdfPCell eDataCount = new PdfPCell(new Phrase(mancn1ModelTotal.getNrOfDays().toString(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
//			eDataCount.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
//			eDataCount.setBorder(com.itextpdf.text.Rectangle.TOP | com.itextpdf.text.Rectangle.BOTTOM);
//			totalTable.addCell(eDataCount);
//
//			PdfPCell fDataCount = new PdfPCell(new Phrase(mancn2ModelTotal.getNrOfDays().toString(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
//			fDataCount.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
//			fDataCount.setBorder(com.itextpdf.text.Rectangle.TOP | com.itextpdf.text.Rectangle.BOTTOM|com.itextpdf.text.Rectangle.RIGHT);
//			totalTable.addCell(fDataCount);
//
//			document.add(totalTable);
//
//			//Only populate if > 0
//
//			PdfPTable grandTotalTable = new PdfPTable(8);
//			grandTotalTable.setWidthPercentage(100);
//
//			PdfPCell grdTotalHeading = new PdfPCell(new Phrase("GRAND TOTAL                    "+String.valueOf(grandTotal),FontFactory.getFont(FontFactory.HELVETICA, 9, com.itextpdf.text.Font.BOLD)));
//			grdTotalHeading.setColspan(2);
//			grdTotalHeading.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
//			grdTotalHeading.setBorder(com.itextpdf.text.Rectangle.BOTTOM | com.itextpdf.text.Rectangle.LEFT |com.itextpdf.text.Rectangle.RIGHT);
//			grandTotalTable.addCell(grdTotalHeading);
//
//			PdfPCell nullCell01 = new PdfPCell(new Paragraph(null,FontFactory.getFont(FontFactory.HELVETICA, 8)));
//			nullCell01.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
//			nullCell01.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
//			grandTotalTable.addCell(nullCell001);
//
//			PdfPCell nullCell002 = new PdfPCell(new Paragraph(null,FontFactory.getFont(FontFactory.HELVETICA, 8)));
//			nullCell002.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
//			nullCell002.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
//			grandTotalTable.addCell(nullCell002);
//
//			PdfPCell nullCell003 = new PdfPCell(new Paragraph(null,FontFactory.getFont(FontFactory.HELVETICA, 8)));
//			nullCell003.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
//			nullCell003.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
//			grandTotalTable.addCell(nullCell003);
//
//			PdfPCell nullCell004 = new PdfPCell(new Paragraph(null,FontFactory.getFont(FontFactory.HELVETICA, 8)));
//			nullCell004.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
//			nullCell004.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
//			grandTotalTable.addCell(nullCell004);
//
//			PdfPCell nullCell005 = new PdfPCell(new Paragraph(null,FontFactory.getFont(FontFactory.HELVETICA, 8)));
//			nullCell005.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
//			nullCell005.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
//			grandTotalTable.addCell(nullCell005);
//
//			PdfPCell nullCell006 = new PdfPCell(new Paragraph(null,FontFactory.getFont(FontFactory.HELVETICA, 8)));
//			nullCell006.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
//			nullCell006.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
//			grandTotalTable.addCell(nullCell006);
//			document.add(grandTotalTable);
//		}
//		else
//		{
//			PdfPTable totalTable = new PdfPTable(5);
//			totalTable.setWidthPercentage(100);
//
//			PdfPCell nullCell0001 = new PdfPCell(new Paragraph(null,FontFactory.getFont(FontFactory.HELVETICA, 8)));
//			nullCell0001.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
//			nullCell0001.setBorder(com.itextpdf.text.Rectangle.NO_BORDER );
//			totalTable.addCell(nullCell0001);
//
//			PdfPCell nullCell001 = new PdfPCell(new Paragraph(null,FontFactory.getFont(FontFactory.HELVETICA, 8)));
//			nullCell001.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
//			nullCell001.setBorder(com.itextpdf.text.Rectangle.NO_BORDER );
//			totalTable.addCell(nullCell001);
//
//			PdfPCell noDataCell = new PdfPCell(new Paragraph("NO OUTSTANDING RESPONSES",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
//			noDataCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
//			noDataCell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER );
//			totalTable.addCell(noDataCell);
//
//			PdfPCell nullCell002 = new PdfPCell(new Paragraph(null,FontFactory.getFont(FontFactory.HELVETICA, 8)));
//			nullCell002.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
//			nullCell002.setBorder(com.itextpdf.text.Rectangle.NO_BORDER );
//			totalTable.addCell(nullCell002);
//
//			PdfPCell nullCell003 = new PdfPCell(new Paragraph(null,FontFactory.getFont(FontFactory.HELVETICA, 8)));
//			nullCell003.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
//			nullCell003.setBorder(com.itextpdf.text.Rectangle.NO_BORDER );
//			totalTable.addCell(nullCell003);
//
//			document.add(totalTable);
//		}
//
//		document.close();

		int inOneDay = noOfInitOneDayTot.intValue();
		int inTwoDay = noOfInitTwoDayTot.intValue();
		int amOneDay = noOdfAmendOneDayTot.intValue();
		int amTwoDay = noOdfAmendTwoDayTot.intValue();
		int cnOneDay = noOfCancOneDayTot.intValue();
		int cnTwoDay = noOfCancTwoDayTot.intValue();

		int grandTotal = inOneDay + inTwoDay + amOneDay + amTwoDay + cnOneDay + cnTwoDay;

		//		log.info("oneDayManinTotal ==> "+oneDayManinTotal);
		//		log.info("twoDayManinTotal ==> "+twoDayManinTotal);
		//
		//		log.info("oneDayManamTotal ==> "+oneDayManamTotal);
		//		log.info("twoDayManamTotal ==> "+twoDayManamTotal);
		//
		//		log.info("oneDayMancnTotal ==> "+oneDayMancnTotal);
		//		log.info("twoDayMancnTotal ==> "+twoDayMancnTotal);

		if(inOneDay > 0 ||inTwoDay > 0 ||amOneDay > 0 ||amTwoDay > 0 ||cnOneDay > 0 ||cnTwoDay > 0 )
		{
			PdfPTable totalTable = new PdfPTable(8);
			totalTable.setWidthPercentage(100);

			PdfPCell totalHeading = new PdfPCell(new Phrase("TOTAL",FontFactory.getFont(FontFactory.HELVETICA, 9, com.itextpdf.text.Font.BOLD)));
			totalHeading.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
			totalHeading.setBorder(com.itextpdf.text.Rectangle.TOP | com.itextpdf.text.Rectangle.LEFT);
			totalTable.addCell(totalHeading);

			PdfPCell nullCell001 = new PdfPCell(new Paragraph(null,FontFactory.getFont(FontFactory.HELVETICA, 8)));
			nullCell001.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
			nullCell001.setBorder(com.itextpdf.text.Rectangle.TOP );
			totalTable.addCell(nullCell001);

			PdfPCell aDataCount = new PdfPCell(new Phrase(noOfInitOneDayTot.toString(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
			aDataCount.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			aDataCount.setBorder(com.itextpdf.text.Rectangle.TOP | com.itextpdf.text.Rectangle.BOTTOM);
			totalTable.addCell(aDataCount);

			PdfPCell bDataCount = new PdfPCell(new Phrase(noOfInitTwoDayTot.toString(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
			bDataCount.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			bDataCount.setBorder(com.itextpdf.text.Rectangle.TOP | com.itextpdf.text.Rectangle.BOTTOM);
			totalTable.addCell(bDataCount);

			PdfPCell cDataCount = new PdfPCell(new Phrase(noOdfAmendOneDayTot.toString(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
			cDataCount.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			cDataCount.setBorder(com.itextpdf.text.Rectangle.TOP | com.itextpdf.text.Rectangle.BOTTOM);
			totalTable.addCell(cDataCount);

			PdfPCell dDataCount = new PdfPCell(new Phrase(noOdfAmendTwoDayTot.toString(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
			dDataCount.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			dDataCount.setBorder(com.itextpdf.text.Rectangle.TOP | com.itextpdf.text.Rectangle.BOTTOM);
			totalTable.addCell(dDataCount);

			PdfPCell eDataCount = new PdfPCell(new Phrase(noOfCancOneDayTot.toString(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
			eDataCount.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			eDataCount.setBorder(com.itextpdf.text.Rectangle.TOP | com.itextpdf.text.Rectangle.BOTTOM);
			totalTable.addCell(eDataCount);

			PdfPCell fDataCount = new PdfPCell(new Phrase(noOfCancTwoDayTot.toString(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
			fDataCount.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			fDataCount.setBorder(com.itextpdf.text.Rectangle.TOP | com.itextpdf.text.Rectangle.BOTTOM|com.itextpdf.text.Rectangle.RIGHT);
			totalTable.addCell(fDataCount);

			document.add(totalTable);

			//Only populate if > 0

			PdfPTable grandTotalTable = new PdfPTable(8);
			grandTotalTable.setWidthPercentage(100);

			PdfPCell grdTotalHeading = new PdfPCell(new Phrase("GRAND TOTAL                    "+String.valueOf(grandTotal),FontFactory.getFont(FontFactory.HELVETICA, 9, com.itextpdf.text.Font.BOLD)));
			grdTotalHeading.setColspan(2);
			grdTotalHeading.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
			grdTotalHeading.setBorder(com.itextpdf.text.Rectangle.BOTTOM | com.itextpdf.text.Rectangle.LEFT |com.itextpdf.text.Rectangle.RIGHT);
			grandTotalTable.addCell(grdTotalHeading);

			PdfPCell nullCell01 = new PdfPCell(new Paragraph(null,FontFactory.getFont(FontFactory.HELVETICA, 8)));
			nullCell01.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
			nullCell01.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
			grandTotalTable.addCell(nullCell001);

			PdfPCell nullCell002 = new PdfPCell(new Paragraph(null,FontFactory.getFont(FontFactory.HELVETICA, 8)));
			nullCell002.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
			nullCell002.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
			grandTotalTable.addCell(nullCell002);

			PdfPCell nullCell003 = new PdfPCell(new Paragraph(null,FontFactory.getFont(FontFactory.HELVETICA, 8)));
			nullCell003.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
			nullCell003.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
			grandTotalTable.addCell(nullCell003);

			PdfPCell nullCell004 = new PdfPCell(new Paragraph(null,FontFactory.getFont(FontFactory.HELVETICA, 8)));
			nullCell004.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
			nullCell004.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
			grandTotalTable.addCell(nullCell004);

			PdfPCell nullCell005 = new PdfPCell(new Paragraph(null,FontFactory.getFont(FontFactory.HELVETICA, 8)));
			nullCell005.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
			nullCell005.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
			grandTotalTable.addCell(nullCell005);

			PdfPCell nullCell006 = new PdfPCell(new Paragraph(null,FontFactory.getFont(FontFactory.HELVETICA, 8)));
			nullCell006.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
			nullCell006.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
			grandTotalTable.addCell(nullCell006);
			document.add(grandTotalTable);
		}
		else
		{
			PdfPTable totalTable = new PdfPTable(5);
			totalTable.setWidthPercentage(100);

			PdfPCell nullCell0001 = new PdfPCell(new Paragraph(null,FontFactory.getFont(FontFactory.HELVETICA, 8)));
			nullCell0001.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
			nullCell0001.setBorder(com.itextpdf.text.Rectangle.NO_BORDER );
			totalTable.addCell(nullCell0001);

			PdfPCell nullCell001 = new PdfPCell(new Paragraph(null,FontFactory.getFont(FontFactory.HELVETICA, 8)));
			nullCell001.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
			nullCell001.setBorder(com.itextpdf.text.Rectangle.NO_BORDER );
			totalTable.addCell(nullCell001);

			PdfPCell noDataCell = new PdfPCell(new Paragraph("NO OUTSTANDING RESPONSES",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
			noDataCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
			noDataCell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER );
			totalTable.addCell(noDataCell);

			PdfPCell nullCell002 = new PdfPCell(new Paragraph(null,FontFactory.getFont(FontFactory.HELVETICA, 8)));
			nullCell002.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
			nullCell002.setBorder(com.itextpdf.text.Rectangle.NO_BORDER );
			totalTable.addCell(nullCell002);

			PdfPCell nullCell003 = new PdfPCell(new Paragraph(null,FontFactory.getFont(FontFactory.HELVETICA, 8)));
			nullCell003.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
			nullCell003.setBorder(com.itextpdf.text.Rectangle.NO_BORDER );
			totalTable.addCell(nullCell003);

			document.add(totalTable);
		}

		document.close();

		//Copy the report to the Output reports folder 
		try
		{
			copyFile(pdfFileName);
		}
		catch(IOException ioe)
		{
			log.error("Error on copying PSMD01 report to temp "+ioe.getMessage());
			ioe.printStackTrace();
		}
		catch(Exception ex)
		{
			log.error("Error on copying PSMD01 report to temp "+ex.getMessage());
			ex.printStackTrace();
		}
	}

	public  void copyFile(String fileName) throws IOException 
	{
		File tmpFile = new File(tempDir, fileName);
		File destFile = new File(reportDir, fileName);

//		log.info("tmpFile ==> "+ tmpFile);
//		log.info("destFile===> "+ destFile);

		Files.copy(tmpFile, destFile);
		log.info("Copying "+fileName+" from temp to output directory...");
	}


	//CONTEXT LOOKUPS//
	public static void contextValidationBeanCheck() 
	{
		if (valBeanRemote == null) {
			valBeanRemote = Util.getValidationBean();
		}
	}

	private static void contextCheck() {
		if (beanRemote == null) {
			beanRemote = Util.getServiceBean();
		}
	}

	private static void contextAdminBeanCheck() 
	{
		if (adminBeanRemote == null) {
			adminBeanRemote = Util.getAdminBean();	
		}
	}

	private static void contextPropertyFileCheck() 
	{
		if (propertyUtilRemote == null) 
		{
			propertyUtilRemote = Util.getPropertyUtil();
		}
	}
	
	private static void contextReportCheck() {
		if (reportBeanRemote == null) {
			reportBeanRemote = Util.getReportBean();
		}
	}

}

