package com.bsva.reports;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.bsva.commons.model.*;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.apache.log4j.Logger;

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
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * @author SalehaR
 * @author SalehaR - 2019/10/09 - Align to Single Table 
 *
 */
public class PasaBatchAmendmentReport 
{
	public static Logger log=Logger.getLogger(PasaBatchAmendmentReport.class);

	public static ServiceBeanRemote beanRemote;
	public static ValidationBeanRemote valBeanRemote;
	private static AdminBeanRemote adminBeanRemote;
	private static PropertyUtilRemote propertyUtilRemote;
	private static ReportBeanRemote reportBeanRemote;


	Date currentDate = new Date();
	//	private String downloaddirectory ="/home/opsjava/Delivery/Mandates/Reports/";

	List<CreditorBankModel> creditorBankModelList;
	CreditorBankModel creditorBankModel = new CreditorBankModel();

	int fileSeqNo =000;
	String feedbackMsg = null;
	String reportName = null, reportNr = null, reportDir = null, tempDir = null, mdtLoadType = null;
	String pdfFileName = null, psmd06;
	String memberId = "0000";

	BigDecimal reqByCusTotal = new BigDecimal(0);
	BigDecimal canReqByIniTotal = new BigDecimal(0);
	BigDecimal unsusMdtChngTotal = new BigDecimal(0);
	BigDecimal unChngMdtTotal = new BigDecimal(0);
	BigDecimal notSpecByCusTotal = new BigDecimal(0);

	long startTime, endTime, duration;
	
	String activeIndicator = "Y";

	public void generateReport(Date frontFromDate,Date frontToDate) throws FileNotFoundException, DocumentException
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
			log.info("tempDir ==> "+tempDir);
			reportDir= propertyUtilRemote.getPropValue("Reports.Output");
			log.info("reportDir ==> "+reportDir);
			psmd06 = propertyUtilRemote.getPropValue("RPT.PASA.BATCH.AMENDMENTS");
			mdtLoadType = propertyUtilRemote.getPropValue("MDT.LOAD.TYPE");
		}
		catch(Exception ex)
		{
			log.error("PSMD06 - Could not find MandateMessageCommons.properties in classpath");	
			reportDir = "/home/opsjava/Delivery/Cession_Assign/Output/Reports/";
			tempDir="/home/opsjava/Delivery/Cession_Assign/Output/temp/";
		}

		//Retrieve Report Name
		CasCnfgReportNamesEntity reportNameEntity = new CasCnfgReportNamesEntity();
		reportNameEntity = (CasCnfgReportNamesEntity) adminBeanRemote.retrieveReportName(psmd06);

		if(reportNameEntity != null)
		{
			
			if(reportNameEntity.getActiveInd().equalsIgnoreCase(activeIndicator)) {
				reportNr = reportNameEntity.getReportNr();
				reportName = reportNameEntity.getReportName();
				
				try
				{
					generateReportColumns(reportNr ,reportName,frontFromDate,frontToDate);
					endTime = System.nanoTime();
					duration = (endTime - startTime) / 1000000;
					log.info("[Total Duration for PSMD06 Report is: "+ DurationFormatUtils.formatDuration(duration, "HH:mm:ss.S")+" milliseconds |");
				}
				catch(DocumentException de)
				{
					de.printStackTrace();
					log.error("<DE> Error generating generateMandateAmendmentReport "+ de.getMessage());
					feedbackMsg = "Error generating "+reportNr + " report";
				}
				catch(FileNotFoundException fne)
				{
					fne.printStackTrace();
					log.error("<FNE> Error generating generateMandateAmendmentReport "+ fne.getMessage());
					feedbackMsg = "Error generating "+reportNr + " report";
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
					log.error("<EX> Error generating generateMandateAmendmentReport "+ ex.getMessage());
					feedbackMsg = "Error generating "+reportNr + " report";
				}
			}
		}
	}

	public void generateReportColumns(String reportNo,String reportname,Date frontFromDate,Date frontToDate) throws FileNotFoundException, DocumentException
	{
		DecimalFormat df = new DecimalFormat("### ### ### ### ### ##0.00");
		DateFormat dateTimeFormat = new SimpleDateFormat("yyyyMMdd_HH:mm:ss");
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat fileTimeFormat = new SimpleDateFormat("MM-yyyy");
		DateFormat yearFormat = new SimpleDateFormat("-yyyy");
		Date currentDate = new Date();
		DateFormat endDateFormat = new SimpleDateFormat("ddMMyyyy");

		SysctrlCompParamModel companyParamModel = new SysctrlCompParamModel();
		companyParamModel = (SysctrlCompParamModel) beanRemote.retrieveCompanyParameters();

		SystemParameterModel systemParameterModel = new SystemParameterModel();
		systemParameterModel =(SystemParameterModel) adminBeanRemote.retrieveWebActiveSysParameter();

		int pageNo = 1;
		fileSeqNo =fileSeqNo + 1;
		String strSeqNo ;
		int lastSeqNoUsed;
		CasOpsRepSeqNrEntity casOpsRepSeqNrEntity = new CasOpsRepSeqNrEntity();
		casOpsRepSeqNrEntity = (CasOpsRepSeqNrEntity)adminBeanRemote.retrieveRepSeqNr(reportNr,memberId);
		log.info("retrieve seq number : "  + casOpsRepSeqNrEntity);
		
		if(casOpsRepSeqNrEntity != null)
		{
			lastSeqNoUsed = Integer.valueOf(casOpsRepSeqNrEntity.getLastSeqNo());
			lastSeqNoUsed = lastSeqNoUsed + 1;
		}
		else
		{
			lastSeqNoUsed = 1;
		}
		 strSeqNo = String.format("%06d",lastSeqNoUsed);
		 casOpsRepSeqNrEntity.setLastSeqNo(strSeqNo);
		adminBeanRemote.updateReportSeqNr(casOpsRepSeqNrEntity);
		String reportSeqNo = strSeqNo.substring(3,6);
		
		String files;
		
		if(frontFromDate != null && frontToDate != null) {
			files = tempDir+"0000AC"+reportNo+endDateFormat.format(frontToDate).toString()+"."+reportSeqNo + ".pdf";
			pdfFileName = "0000AC"+reportNo+endDateFormat.format(frontToDate).toString()+"."+reportSeqNo + ".pdf";
		}else {
			files = tempDir+"0000AC"+reportNo+endDateFormat.format(currentDate).toString()+"."+reportSeqNo + ".pdf";
			pdfFileName = "0000AC"+reportNo+endDateFormat.format(currentDate).toString()+"."+reportSeqNo + ".pdf";
		}

		Document document = new Document(PageSize.A4.rotate());

		FileOutputStream fileOutputStream = new FileOutputStream(files);

		PageEvent pageEvent = new PageEvent(companyParamModel, systemParameterModel, reportNr, reportName, false, true, null, null, null, creditorBankModelList,null, null,true);

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

		//		PdfPTable firstHeading = new PdfPTable(3);
		//		firstHeading.setWidthPercentage(100);
		//
		//		try 
		//		{
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

		//		PdfPTable secondheading = new PdfPTable(3);
		//		secondheading.setWidthPercentage(100);
		//		try 
		//		{

		if(systemParameterModel.getProcessDate() != null)
		{
			strMonth =fileTimeFormat.format(systemParameterModel.getProcessDate()).substring(0,2);
			log.debug("strMonth ==> "+strMonth);
			month= DateUtil.getMonthFullname(Integer.valueOf(strMonth), true);
			//log.info("Process Month----->"+month);
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
//			strFirstDate = "01092019";
//			strLastDate = "30092019";
//			log.info("strFirstDate ==> "+ strFirstDate);
//			log.info("strLastDate ==> "+ strLastDate);
		}
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
		//			PdfPCell nullCell03 = new PdfPCell(new Paragraph("Process Month: " + month+ " " + year,  FontFactory.getFont(FontFactory.HELVETICA, 8)));
		//			nullCell03.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
		//			nullCell03.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
		//			secondheading.addCell(nullCell03);
		//		} 
		//		catch (NullPointerException x)
		//		{
		//			log.debug("Error Finding Company Reg No" + x);
		//		}
		//		document.add(secondheading);
		//		document.add(freeline);


		//		PdfPTable thirdHeading = new PdfPTable(1);
		//		thirdHeading.setWidthPercentage(100);
		//
		//		PdfPCell cell1 = new PdfPCell(new Paragraph(reportNo+"-"+reportname, FontFactory.getFont( FontFactory.HELVETICA, 9, com.itextpdf.text.Font.BOLD)));
		//		cell1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		//		cell1.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
		//		thirdHeading.addCell(cell1);
		//
		//		document.add(thirdHeading);
		//		document.add(freeline);

		//		PdfPTable batchDetailsHeading = new PdfPTable(6);
		//		batchDetailsHeading.setWidthPercentage(100);
		//
		//		PdfPCell credBank = new PdfPCell(new Phrase("Creditor Bank",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		//		credBank.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		//		credBank.setUseVariableBorders(true);
		//		credBank.setBorderColorTop(BaseColor.BLACK);
		//		credBank.setBorderColorBottom(BaseColor.BLACK);
		//		credBank.setBorderColorLeft(BaseColor.WHITE);
		//		credBank.setBorderColorRight(BaseColor.WHITE);
		//		batchDetailsHeading.addCell(credBank);
		//
		//		PdfPCell md16 = new PdfPCell(new Phrase("Request By Customer",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		//		md16.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		//		md16.setUseVariableBorders(true);
		//		md16.setBorderColorTop(BaseColor.BLACK);
		//		md16.setBorderColorBottom(BaseColor.BLACK);
		//		md16.setBorderColorLeft(BaseColor.WHITE);
		//		md16.setBorderColorRight(BaseColor.WHITE);
		//		batchDetailsHeading.addCell(md16);
		//
		//		PdfPCell md17 = new PdfPCell(new Phrase("Cancellation/amendment requested By Initiating Party",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		//		md17.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		//		md17.setUseVariableBorders(true);
		//		md17.setBorderColorTop(BaseColor.BLACK);
		//		md17.setBorderColorBottom(BaseColor.BLACK);
		//		md17.setBorderColorLeft(BaseColor.WHITE);
		//		md17.setBorderColorRight(BaseColor.WHITE);
		//		batchDetailsHeading.addCell(md17);
		//
		//		PdfPCell md19 = new PdfPCell(new Phrase("Unsuspend a Mandate with changes",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		//		md19.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		//		md19.setUseVariableBorders(true);
		//		md19.setBorderColorTop(BaseColor.BLACK);
		//		md19.setBorderColorBottom(BaseColor.BLACK);
		//		md19.setBorderColorLeft(BaseColor.WHITE);
		//		md19.setBorderColorRight(BaseColor.WHITE);
		//		batchDetailsHeading.addCell(md19);
		//
		//		PdfPCell md20 = new PdfPCell(new Phrase("Unsuspend an unchanged Mandate",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		//		md20.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		//		md20.setUseVariableBorders(true);
		//		md20.setBorderColorTop(BaseColor.BLACK);
		//		md20.setBorderColorBottom(BaseColor.BLACK);
		//		md20.setBorderColorLeft(BaseColor.WHITE);
		//		md20.setBorderColorRight(BaseColor.WHITE);
		//		batchDetailsHeading.addCell(md20);
		//
		//
		//		PdfPCell ms02 = new PdfPCell(new Phrase("Reason has not been specified by Customer (Customer rejects Mandate)",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		//		ms02.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		//		ms02.setUseVariableBorders(true);
		//		ms02.setBorderColorTop(BaseColor.BLACK);
		//		ms02.setBorderColorBottom(BaseColor.BLACK);
		//		ms02.setBorderColorLeft(BaseColor.WHITE);
		//		ms02.setBorderColorRight(BaseColor.WHITE);
		//		batchDetailsHeading.addCell(ms02);
		//
		//
		//		document.add(batchDetailsHeading);
		//		document.add(freeline);

		//1. Retrieve all creditor banks in a list
		//2. Loop thru bank list and set member number
		//3. run each query per reason code per member number
//		creditorBankModelList = new ArrayList<CreditorBankModel>();
//		creditorBankModelList = (List<CreditorBankModel>) adminBeanRemote.retrieveActiveCreditorBank();
//		log.debug("creditorBankModelList------->"+creditorBankModelList);
//
//		if(creditorBankModelList != null && creditorBankModelList.size() > 0)
//		{
//			for(CreditorBankModel creditorBankModel : creditorBankModelList)
//			{
//				PdfPTable batchDataHeading = new PdfPTable(6);
//				batchDataHeading.setWidthPercentage(100);
//				log.debug("creditorBankModel------->"+creditorBankModel);
//				String creditorName= creditorBankModel.getMemberName();
//				log.info("creditorName------->"+creditorBankModel.getMemberName());
//
//				String creditorMember = creditorBankModel.getMemberNo();
//				log.debug("creditorMember------->"+creditorBankModel.getMemberNo());
//
//				PdfPCell credBankData = new PdfPCell(new Phrase(creditorName,FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
//				credBankData.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
//				credBankData.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
//				batchDataHeading.addCell(credBankData);
//
//				//Query to retrieve Count for MD16
//				MandateAmendModel mandateAmend16Model= new MandateAmendModel();
//				if(mdtLoadType.equalsIgnoreCase("M")) {
//					mandateAmend16Model = (MandateAmendModel) beanRemote.retrieveAmend16Data(creditorMember,"MD16",strFirstDate,strLastDate);
//				}else {
//					mandateAmend16Model = (MandateAmendModel) reportBeanRemote.retrieveAmendReportData(creditorMember,"MD16",strFirstDate,strLastDate);
//				}
//
//				log.debug("mandateAmend16Model1------->"+mandateAmend16Model);
//
//				if(mandateAmend16Model.getAmendReasonCodeCount()== null)
//					mandateAmend16Model.setAmendReasonCodeCount(new BigDecimal(0));
//				PdfPCell md16DataCount = new PdfPCell(new Phrase(mandateAmend16Model.getAmendReasonCodeCount().toString(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.NORMAL)));
//				md16DataCount.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
//				md16DataCount.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
//				batchDataHeading.addCell(md16DataCount);
//
//				//Query to retrieve Count for MD17
//				mandateAmend16Model= new MandateAmendModel();
//				if(mdtLoadType.equalsIgnoreCase("M")) {
//					mandateAmend16Model = (MandateAmendModel) beanRemote.retrieveAmend16Data(creditorMember,"MD17",strFirstDate,strLastDate);
//				}else {
//					mandateAmend16Model = (MandateAmendModel) reportBeanRemote.retrieveAmendReportData(creditorMember,"MD17",strFirstDate,strLastDate);
//				}
//
//				if(mandateAmend16Model.getAmendReasonCodeCount()== null)
//					mandateAmend16Model.setAmendReasonCodeCount(new BigDecimal(0));
//				PdfPCell md17DataCount = new PdfPCell(new Phrase(mandateAmend16Model.getAmendReasonCodeCount().toString(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.NORMAL)));
//				md17DataCount.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
//				md17DataCount.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
//				batchDataHeading.addCell(md17DataCount);
//
//				//Query to retrieve Count for MD19
//				mandateAmend16Model= new MandateAmendModel();
//				if(mdtLoadType.equalsIgnoreCase("M")) {
//					mandateAmend16Model = (MandateAmendModel) beanRemote.retrieveAmend16Data(creditorMember,"MD19",strFirstDate,strLastDate);
//				}else {
//					mandateAmend16Model = (MandateAmendModel) reportBeanRemote.retrieveAmendReportData(creditorMember,"MD19",strFirstDate,strLastDate);
//				}
//
//				if(mandateAmend16Model.getAmendReasonCodeCount()== null)
//					mandateAmend16Model.setAmendReasonCodeCount(new BigDecimal(0));
//				PdfPCell md19DataCount = new PdfPCell(new Phrase(mandateAmend16Model.getAmendReasonCodeCount().toString(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.NORMAL)));
//				md19DataCount.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
//				md19DataCount.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
//				batchDataHeading.addCell(md19DataCount);
//
//				//Query to retrieve Count for MD20
//				mandateAmend16Model= new MandateAmendModel();
//				if(mdtLoadType.equalsIgnoreCase("M")) {
//					mandateAmend16Model = (MandateAmendModel) beanRemote.retrieveAmend16Data(creditorMember,"MD20",strFirstDate,strLastDate);
//				}else {
//					mandateAmend16Model = (MandateAmendModel) reportBeanRemote.retrieveAmendReportData(creditorMember,"MD20",strFirstDate,strLastDate);
//				}
//
//
//				if(mandateAmend16Model.getAmendReasonCodeCount()== null)
//					mandateAmend16Model.setAmendReasonCodeCount(new BigDecimal(0));
//				PdfPCell md20DataCount = new PdfPCell(new Phrase(mandateAmend16Model.getAmendReasonCodeCount().toString(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.NORMAL)));
//				md20DataCount.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
//				md20DataCount.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
//				batchDataHeading.addCell(md20DataCount);
//
//
//				//Query to retrieve Count for MS02
//				mandateAmend16Model= new MandateAmendModel();
//				if(mdtLoadType.equalsIgnoreCase("M")) {
//					mandateAmend16Model = (MandateAmendModel) beanRemote.retrieveAmend16Data(creditorMember,"MS02",strFirstDate,strLastDate);
//				}else {
//					mandateAmend16Model = (MandateAmendModel) reportBeanRemote.retrieveAmendReportData(creditorMember,"MS02",strFirstDate,strLastDate);
//				}
//
//
//				if(mandateAmend16Model.getAmendReasonCodeCount()== null)
//					mandateAmend16Model.setAmendReasonCodeCount(new BigDecimal(0));
//				PdfPCell mS02DataCount = new PdfPCell(new Phrase(mandateAmend16Model.getAmendReasonCodeCount().toString(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.NORMAL)));
//				mS02DataCount.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
//				mS02DataCount.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
//				batchDataHeading.addCell(mS02DataCount);
//
//				document.add(batchDataHeading);
//			}
//		}
//
//
//		PdfPTable batchTotalHeading = new PdfPTable(6);
//		batchTotalHeading.setWidthPercentage(100);
//
//		PdfPCell totals = new PdfPCell(new Phrase("TOTAL",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
//		totals.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
//		totals.setBorder(com.itextpdf.text.Rectangle.LEFT|com.itextpdf.text.Rectangle.TOP);
//		batchTotalHeading.addCell(totals);
//
//		MandateAmendModel mandateAmend16Model= new MandateAmendModel();
//
//
//		//Query to retrieve total Count for MD16
//		mandateAmend16Model = (MandateAmendModel) beanRemote.retrieveReasonCodeTotal("MD16",strFirstDate,strLastDate);
//		log.debug("mandateAmend16Model1------->"+mandateAmend16Model);
//
//		if(mandateAmend16Model.getAmendReasonCodeCount()== null)
//			mandateAmend16Model.setAmendReasonCodeCount(new BigDecimal(0));
//		int md16Count = mandateAmend16Model.getAmendReasonCodeCount().intValue();
//
//		PdfPCell totalMd16Count = new PdfPCell(new Phrase(mandateAmend16Model.getAmendReasonCodeCount().toString(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
//		totalMd16Count.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
//		totalMd16Count.setBorder(com.itextpdf.text.Rectangle.TOP );
//		batchTotalHeading.addCell(totalMd16Count);
//
//		//Query to retrieve Count for MD17
//		mandateAmend16Model= new MandateAmendModel();
//		mandateAmend16Model = (MandateAmendModel) beanRemote.retrieveReasonCodeTotal("MD17",strFirstDate,strLastDate);
//
//		if(mandateAmend16Model.getAmendReasonCodeCount()== null)
//			mandateAmend16Model.setAmendReasonCodeCount(new BigDecimal(0));
//		int md17Count = mandateAmend16Model.getAmendReasonCodeCount().intValue();
//
//		PdfPCell totalMd17Count = new PdfPCell(new Phrase(mandateAmend16Model.getAmendReasonCodeCount().toString(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
//		totalMd17Count.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
//		totalMd17Count.setBorder(com.itextpdf.text.Rectangle.TOP | com.itextpdf.text.Rectangle.BOTTOM);
//		batchTotalHeading.addCell(totalMd17Count);
//
//		//Query to retrieve Count for MD19
//		mandateAmend16Model= new MandateAmendModel();
//		mandateAmend16Model = (MandateAmendModel) beanRemote.retrieveReasonCodeTotal("MD19",strFirstDate,strLastDate);
//
//		if(mandateAmend16Model.getAmendReasonCodeCount()== null)
//			mandateAmend16Model.setAmendReasonCodeCount(new BigDecimal(0));
//		int md19Count = mandateAmend16Model.getAmendReasonCodeCount().intValue();
//
//		PdfPCell totalMd19Count = new PdfPCell(new Phrase(mandateAmend16Model.getAmendReasonCodeCount().toString(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
//		totalMd19Count.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
//		totalMd19Count.setBorder(com.itextpdf.text.Rectangle.TOP | com.itextpdf.text.Rectangle.BOTTOM);
//		batchTotalHeading.addCell(totalMd19Count);
//
//		//Query to retrieve Count for MD20
//		mandateAmend16Model= new MandateAmendModel();
//		mandateAmend16Model = (MandateAmendModel) beanRemote.retrieveReasonCodeTotal("MD20",strFirstDate,strLastDate);
//
//		if(mandateAmend16Model.getAmendReasonCodeCount()== null)
//			mandateAmend16Model.setAmendReasonCodeCount(new BigDecimal(0));
//		int md20Count = mandateAmend16Model.getAmendReasonCodeCount().intValue();
//
//		PdfPCell totalMd20Count = new PdfPCell(new Phrase(mandateAmend16Model.getAmendReasonCodeCount().toString(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
//		totalMd20Count.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
//		totalMd20Count.setBorder(com.itextpdf.text.Rectangle.TOP | com.itextpdf.text.Rectangle.BOTTOM);
//		batchTotalHeading.addCell(totalMd20Count);
//
//
//		//Query to retrieve Count for MS02
//		mandateAmend16Model= new MandateAmendModel();
//		mandateAmend16Model = (MandateAmendModel) beanRemote.retrieveReasonCodeTotal("MS02",strFirstDate,strLastDate);
//
//
//		if(mandateAmend16Model.getAmendReasonCodeCount()== null)
//			mandateAmend16Model.setAmendReasonCodeCount(new BigDecimal(0));
//		int ms02Count = mandateAmend16Model.getAmendReasonCodeCount().intValue();
//
//		PdfPCell totalMS02Count = new PdfPCell(new Phrase(mandateAmend16Model.getAmendReasonCodeCount().toString(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
//		totalMS02Count.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
//		totalMS02Count.setBorder(com.itextpdf.text.Rectangle.TOP | com.itextpdf.text.Rectangle.BOTTOM |com.itextpdf.text.Rectangle.RIGHT);
//		batchTotalHeading.addCell(totalMS02Count);
//
//
//		document.add(batchTotalHeading);
//
//		PdfPTable batchGrandTotalHeading = new PdfPTable(6);
//		batchGrandTotalHeading.setWidthPercentage(100);
//
//		int grandTotal= md16Count+ md17Count +md19Count +md20Count + ms02Count;
//
//		PdfPCell grandTotals =new PdfPCell(new Phrase("GRAND TOTAL          "+String.valueOf(grandTotal),FontFactory.getFont(FontFactory.HELVETICA, 9, com.itextpdf.text.Font.BOLD)));
//		grandTotals.setColspan(2);
//		grandTotals.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
//		grandTotals.setBorder(com.itextpdf.text.Rectangle.BOTTOM | com.itextpdf.text.Rectangle.LEFT |com.itextpdf.text.Rectangle.RIGHT);
//		batchGrandTotalHeading.addCell(grandTotals);
//
//		PdfPCell nullCell001 = new PdfPCell(new Paragraph(null,FontFactory.getFont(FontFactory.HELVETICA, 8)));
//		nullCell001.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
//		nullCell001.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
//		batchGrandTotalHeading.addCell(nullCell001);
//
//		PdfPCell nullCell002 = new PdfPCell(new Paragraph(null,FontFactory.getFont(FontFactory.HELVETICA, 8)));
//		nullCell002.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
//		nullCell002.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
//		batchGrandTotalHeading.addCell(nullCell002);
//
//		PdfPCell nullCell003 = new PdfPCell(new Paragraph(null,FontFactory.getFont(FontFactory.HELVETICA, 8)));
//		nullCell003.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
//		nullCell003.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
//		batchGrandTotalHeading.addCell(nullCell003);
//
//		PdfPCell nullCell004 = new PdfPCell(new Paragraph(null,FontFactory.getFont(FontFactory.HELVETICA, 8)));
//		nullCell004.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
//		nullCell004.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
//		batchGrandTotalHeading.addCell(nullCell004);
//
//		PdfPCell nullCell005 = new PdfPCell(new Paragraph(null,FontFactory.getFont(FontFactory.HELVETICA, 8)));
//		nullCell005.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
//		nullCell005.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
//		batchGrandTotalHeading.addCell(nullCell005);
//
//		PdfPCell nullCell006 = new PdfPCell(new Paragraph(null,FontFactory.getFont(FontFactory.HELVETICA, 8)));
//		nullCell006.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
//		nullCell006.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
//		batchGrandTotalHeading.addCell(nullCell006);
//
//		document.add(batchGrandTotalHeading);

		List<BatchAmendmentModel> batchAmendmentModelList  = new ArrayList<>();
		
		if(frontFromDate != null && frontToDate != null) {
			log.info("Process Month----->" + DateUtil.getMonthFullname(Integer.valueOf(fileTimeFormat.format(frontToDate).substring(0,2)), true));
			log.info("strFirstDate ==> "+ endDateFormat.format(frontFromDate));
			log.info("strLastDate ==> "+ endDateFormat.format(frontToDate));
			batchAmendmentModelList = (List<BatchAmendmentModel>) reportBeanRemote.retrieveBatchAmendmentTrans(endDateFormat.format(frontFromDate),endDateFormat.format(frontToDate));
		}else {
			log.info("Process Month----->"+month);
			log.info("strFirstDate ==> "+ strFirstDate);
			log.info("strLastDate ==> "+ strLastDate);
			batchAmendmentModelList = (List<BatchAmendmentModel>) reportBeanRemote.retrieveBatchAmendmentTrans(strFirstDate,strLastDate);
		}

		if(batchAmendmentModelList != null && batchAmendmentModelList.size() > 0){

			for(BatchAmendmentModel batchAmendmentModel : batchAmendmentModelList){

				PdfPTable dataTable = new PdfPTable(6);
				dataTable.setWidthPercentage(100);

				PdfPCell credNameCell = new PdfPCell(new Phrase(batchAmendmentModel.getCreditor(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
				credNameCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
				credNameCell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
				dataTable.addCell(credNameCell);

				PdfPCell reqByCusCell = new PdfPCell(new Phrase(batchAmendmentModel.getReqByCustomer().toString(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
				reqByCusCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
				reqByCusCell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
				dataTable.addCell(reqByCusCell);

				PdfPCell canReqIniCell = new PdfPCell(new Phrase(batchAmendmentModel.getCanAmndReqByInitPrty().toString(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
				canReqIniCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
				canReqIniCell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
				dataTable.addCell(canReqIniCell);

				PdfPCell unsChangeCell = new PdfPCell(new Phrase(batchAmendmentModel.getUnspndMndtWithChngs().toString(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
				unsChangeCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
				unsChangeCell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
				dataTable.addCell(unsChangeCell);

				PdfPCell unChangedManCell = new PdfPCell(new Phrase(batchAmendmentModel.getUnspndUnchgMndt().toString(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
				unChangedManCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
				unChangedManCell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
				dataTable.addCell(unChangedManCell);

				PdfPCell reasonNtSpecCell = new PdfPCell(new Phrase(batchAmendmentModel.getRsnNotSpcByCust().toString(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
				reasonNtSpecCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
				reasonNtSpecCell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
				dataTable.addCell(reasonNtSpecCell);

				reqByCusTotal = reqByCusTotal.add(batchAmendmentModel.getReqByCustomer());
				canReqByIniTotal = canReqByIniTotal.add(batchAmendmentModel.getCanAmndReqByInitPrty());
				unsusMdtChngTotal = unsusMdtChngTotal.add(batchAmendmentModel.getUnspndMndtWithChngs());
				unChngMdtTotal = unChngMdtTotal.add(batchAmendmentModel.getUnspndUnchgMndt());
				notSpecByCusTotal = notSpecByCusTotal.add(batchAmendmentModel.getRsnNotSpcByCust());

				document.add(dataTable);
			}

			PdfPTable totalsTable = new PdfPTable(6);
			totalsTable.setWidthPercentage(100);

			PdfPCell totals = new PdfPCell(new Phrase("TOTAL",FontFactory.getFont(FontFactory.HELVETICA, 9,com.itextpdf.text.Font.BOLD)));
			totals.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
			totals.setBorder(com.itextpdf.text.Rectangle.TOP | com.itextpdf.text.Rectangle.LEFT);
			totalsTable.addCell(totals);

			List<BigDecimal> totalItems = new ArrayList<>();
			totalItems.add(reqByCusTotal);
			totalItems.add(canReqByIniTotal);
			totalItems.add(unsusMdtChngTotal);
			totalItems.add(unChngMdtTotal);
			totalItems.add(notSpecByCusTotal);

			for(int i = 0; i < totalItems.size(); i++){
				PdfPCell countCell = new PdfPCell(new Phrase(totalItems.get(i).toString(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
				countCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
				countCell.setBorder(com.itextpdf.text.Rectangle.TOP | com.itextpdf.text.Rectangle.BOTTOM);
				totalsTable.addCell(countCell);
			}

			document.add(totalsTable);

			int grandTotal = reqByCusTotal.intValue() + canReqByIniTotal.intValue() + unsusMdtChngTotal.intValue() + unChngMdtTotal.intValue() + notSpecByCusTotal.intValue();

			//	POPULATE GRAND TOTAL
			PdfPTable grandTotalTable = new PdfPTable(6);
			grandTotalTable.setWidthPercentage(100);

			PdfPCell grdTotalHeading = new PdfPCell(new Phrase("GRAND TOTAL               "+String.valueOf(grandTotal),FontFactory.getFont(FontFactory.HELVETICA, 9, com.itextpdf.text.Font.BOLD)));
			grdTotalHeading.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
			grdTotalHeading.setBorder(com.itextpdf.text.Rectangle.BOTTOM | com.itextpdf.text.Rectangle.LEFT |com.itextpdf.text.Rectangle.RIGHT);
			grandTotalTable.addCell(grdTotalHeading);

			PdfPCell nullCell001 = new PdfPCell(new Paragraph(null,FontFactory.getFont(FontFactory.HELVETICA, 8)));
			nullCell001.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
			nullCell001.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
			grandTotalTable.addCell(nullCell001);
			grandTotalTable.addCell(nullCell001);
			grandTotalTable.addCell(nullCell001);
			grandTotalTable.addCell(nullCell001);
			grandTotalTable.addCell(nullCell001);
			document.add(grandTotalTable);

		}

		document.close();
		//Copy the report to the Output reports folder 
		try
		{
			copyFile(pdfFileName);
		}
		catch(IOException ioe)
		{
			log.error("Error on copying PSMD06 report to temp "+ioe.getMessage());
			ioe.printStackTrace();
		}
		catch(Exception ex)
		{
			log.error("Error on copying PSMD06 report to temp "+ex.getMessage());
			ex.printStackTrace();
		}


	}

	public  void copyFile(String fileName) throws IOException 
	{
		File tmpFile = new File(tempDir, fileName);
		File destFile = new File(reportDir, fileName);

		log.info("tmpFile ==> "+ tmpFile);
		log.info("destFile===> "+ destFile);

		Files.copy(tmpFile, destFile);
		log.info("Copying "+fileName+"from temp to output directory...");
	}

	//CONTEXT LOOKUPS//
	public static void contextValidationBeanCheck() 
	{
		if (valBeanRemote == null) {
			valBeanRemote = Util.getValidationBean();
		}
	}

	private static void contextCheck() 
	{
		if (beanRemote == null) {
			beanRemote = Util.getServiceBean();
		}
	}

	private static void contextAdminBeanCheck() 
	{
		if (adminBeanRemote == null) 
		{
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
