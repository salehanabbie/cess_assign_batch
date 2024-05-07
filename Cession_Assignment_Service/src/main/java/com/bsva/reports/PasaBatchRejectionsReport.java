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
 * @author SalehaR-Align to Single Table
 *
 */
public class PasaBatchRejectionsReport 
{
	public static Logger log=Logger.getLogger(PasaBatchRejectionsReport.class);

	public static ServiceBeanRemote beanRemote;
	public static ValidationBeanRemote valBeanRemote;
	private static AdminBeanRemote adminBeanRemote;
	private static PropertyUtilRemote propertyUtilRemote;
	private static ReportBeanRemote reportBeanRemote;

	Date currentDate = new Date();
	//	private String downloaddirectory ="/home/opsjava/Delivery/Mandates/Reports/";

	List<ReasonCodesModel>rejectReasonCodesList;
	int fileSeqNo =000;

	List<CreditorBankModel> creditorBankModelList;
	CreditorBankModel creditorBankModel ;

	String reportName = null, reportNr = null, reportDir = null, tempDir = null, mdtLoadType = null;
	String pdfFileName = null, psmd04;
	String memberId = "0000";

	boolean ac01Boolean = false;
	boolean ac04Boolean = false;
	boolean ac06Boolean = false;
	boolean am05Boolean = false;
	boolean be05Boolean = false;
	boolean be06Boolean = false;
	boolean dt01Boolean = false;
	boolean md02Boolean = false;
	boolean md07Boolean = false;
	boolean md09Boolean = false;
	boolean md11Boolean = false;
	boolean md12Boolean = false;
	boolean md13Boolean = false;
	boolean md16Boolean = false;
	boolean md17Boolean = false;
	boolean md18Boolean = false;
	boolean ms02Boolean = false;
	boolean ms03Boolean = false;
	boolean nmtcBoolean = false;
	boolean to01Boolean = false;

	private BigDecimal absaTotal = new BigDecimal(0);
	private BigDecimal africanbTotal = new BigDecimal(0);
	private BigDecimal capitecTotal = new BigDecimal(0);
	private BigDecimal finbondTotal = new BigDecimal(0);
	private BigDecimal fnbTotal = new BigDecimal(0);
	private BigDecimal grobTotal = new BigDecimal(0);
	private BigDecimal mercanTotal = new BigDecimal(0);
	private BigDecimal nedbankTotal = new BigDecimal(0);
	private BigDecimal stdTotal = new BigDecimal(0);
	private BigDecimal ubankTotal = new BigDecimal(0);

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
			log.info("tempDir ==> "+tempDir);
			reportDir= propertyUtilRemote.getPropValue("Reports.Output");
			log.info("reportDir ==> "+reportDir);
			psmd04 = propertyUtilRemote.getPropValue("RPT.PASA.BATCH.REJECTIONS");
			mdtLoadType = propertyUtilRemote.getPropValue("MDT.LOAD.TYPE");
		}
		catch(Exception ex)
		{
			log.error("PBMD01- Could not find MandateMessageCommons.properties in classpath");	
			reportDir = "/home/opsjava/Delivery/Cession_Assign/Output/Reports/";
			tempDir="/home/opsjava/Delivery/Cession_Assign/Output/temp/";
		}

		//Retrieve Report Name
		CasCnfgReportNamesEntity reportNameEntity = new CasCnfgReportNamesEntity();
		reportNameEntity = (CasCnfgReportNamesEntity) adminBeanRemote.retrieveReportName(psmd04);

		if(reportNameEntity != null)
		{
			if(reportNameEntity.getActiveInd().equalsIgnoreCase(activeIndicator)) {
				
				reportNr = reportNameEntity.getReportNr();
				reportName = reportNameEntity.getReportName();
				
				generateReportColumns(reportNr, reportName,frontDate);
				endTime = System.nanoTime();
				duration = (endTime - startTime) / 1000000;
				log.info("[Total Duration for PSMD04 Report is: "+ DurationFormatUtils.formatDuration(duration, "HH:mm:ss.S")+" milliseconds |");
			}
		}
	}



	public void generateReportColumns(String reportNo,String reportname,Date frontDate) throws FileNotFoundException, DocumentException
	{
		log.info("*****GENERATING PSMD04 REPORT *****");
		log.debug("The report cloumns method ****************");
		DecimalFormat df = new DecimalFormat("### ### ### ### ### ##0.00");
		DateFormat dateTimeFormat = new SimpleDateFormat("yyyyMMdd_HH:mm:ss");
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat fileTimeFormat = new SimpleDateFormat("MM-yyyy");
		DateFormat endDateFormat = new SimpleDateFormat("ddMMyyyy");
		DateFormat frontDateFormat = new SimpleDateFormat("yyyyMM");
		Date currentDate = new Date();
		String strFirstDate = null, strLastDate = null;
		int pageNo = 1;
		fileSeqNo =fileSeqNo + 1;
		int lastSeqNoUsed;
		String strSeqNo;
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
		if (frontDate != null) {
			files = tempDir + "0000AC" + reportNo + endDateFormat.format(frontDate).toString() + "." + reportSeqNo + ".pdf";
			pdfFileName = "0000AC" + reportNo + endDateFormat.format(frontDate).toString() + "." + reportSeqNo + ".pdf";
		} else {
			files = tempDir + "0000AC" + reportNo + endDateFormat.format(currentDate).toString() + "." + reportSeqNo + ".pdf";
			pdfFileName = "0000AC" + reportNo + endDateFormat.format(currentDate).toString() + "." + reportSeqNo + ".pdf";
		}

		SysctrlCompParamModel companyParamModel = new SysctrlCompParamModel();
		companyParamModel = (SysctrlCompParamModel) beanRemote.retrieveCompanyParameters();

		SystemParameterModel systemParameterModel = new SystemParameterModel();
		systemParameterModel =(SystemParameterModel) adminBeanRemote.retrieveWebActiveSysParameter();

		creditorBankModelList = new ArrayList<CreditorBankModel>();
		creditorBankModelList=(List<CreditorBankModel>) adminBeanRemote.retrieveActiveCreditorBank();

		Document document = new Document(PageSize.A4.rotate());

		FileOutputStream fileOutputStream = new FileOutputStream(files);
		
		PageEvent pageEvent = new PageEvent(companyParamModel, systemParameterModel, reportNr, reportName, false, true, null, null, null, creditorBankModelList, null,null,true);

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
		//
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


		//		PdfPTable secondheading = new PdfPTable(3);
		//		secondheading.setWidthPercentage(100);
		//		try 
		//		{
		//
		//			PdfPCell nullCell001 = new PdfPCell(new Paragraph(null,FontFactory.getFont(FontFactory.HELVETICA, 8)));
		//			nullCell001.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		//			nullCell001.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
		//			secondheading.addCell(nullCell001);
		//
		//			PdfPCell nullCell02 = new PdfPCell(new Paragraph("REG.NO."+ companyParamModel.getRegistrationNr(), FontFactory.getFont(FontFactory.HELVETICA, 8)));
		//			nullCell02.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		//			nullCell02.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
		//			secondheading.addCell(nullCell02);

		String strMonth = null;
		String month = null;
		String year = null;
		Date firstDate, lastDate = null;

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

			//				strFirstDate = "01082017";
			//				strLastDate = "31082017";

			log.debug("lastDate ==> "+ lastDate);
			log.debug("endDate ==> "+ endDateFormat.format(lastDate));
		}

		//			PdfPCell nullCell03 = new PdfPCell(new Paragraph("Process Month: " + month+" "+year,  FontFactory.getFont(FontFactory.HELVETICA, 8)));
		//			nullCell03.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
		//			nullCell03.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
		//			secondheading.addCell(nullCell03);
		//
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
		//		PdfPCell cell1 = new PdfPCell(new Paragraph(reportNo+ " - "+ reportname, FontFactory.getFont( FontFactory.HELVETICA, 9, com.itextpdf.text.Font.BOLD)));
		//		cell1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		//		cell1.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
		//		thirdHeading.addCell(cell1);
		//
		//		document.add(thirdHeading);
		//		document.add(freeline);

		int headingSize = 0;

//		if(creditorBankModelList != null && creditorBankModelList.size() > 0)
//		{
//			headingSize = creditorBankModelList.size() + 3;//for the 1st column
//			log.debug("headingSize == "+headingSize);
//			//			PdfPTable batchDetailsHeading = new PdfPTable(headingSize);
//			//			batchDetailsHeading.setWidthPercentage(100);
//			//
//			//
//			//			PdfPCell rejectReasonHeading = new PdfPCell(new Phrase("MANDATE REJECTION REASONS",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
//			//			rejectReasonHeading.setColspan(3);
//			//			rejectReasonHeading.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
//			//			rejectReasonHeading.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
//			//			batchDetailsHeading.addCell(rejectReasonHeading);
//			//
//			//			String creditorBankId =  null;
//			//			for(CreditorBankModel creditorBankModel :creditorBankModelList)
//			//			{
//			//
//			//				String creditorBank=creditorBankModel.getMemberName();
//			//				creditorBankId= creditorBankModel.getMemberNo();
//			//
//			//				PdfPCell creditorBanksData = new PdfPCell(new Phrase(creditorBank,FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
//			//				creditorBanksData.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
//			//				creditorBanksData.setBorder(com.itextpdf.text.Rectangle.TOP | com.itextpdf.text.Rectangle.BOTTOM);
//			//				batchDetailsHeading.addCell(creditorBanksData);
//			//
//			//				document.add(batchDetailsHeading);
//			//
//			//				creditorBankModel.setTxnCount(BigDecimal.ZERO);
//			//			}
//
//			for(CreditorBankModel creditorBankModel :creditorBankModelList)
//			{
//				creditorBankModel.setTxnCount(BigDecimal.ZERO);
//			}
//
//			rejectReasonCodesList = new ArrayList<ReasonCodesModel>();
//			rejectReasonCodesList = (List<ReasonCodesModel>) adminBeanRemote.retrieveRejectionCodesForRejectionsReport();
//			if(rejectReasonCodesList!= null && rejectReasonCodesList.size()> 0)
//			{
//				log.debug("rejectReasonCodesList.size() ==> "+rejectReasonCodesList.size());
//
//				PdfPTable reasonCodeData = new PdfPTable(headingSize);
//				reasonCodeData.setWidthPercentage(100);
//
//				//Loop through reason codes
//				for(ReasonCodesModel reasonCodesModel :rejectReasonCodesList)
//				{
//					String reasonCode = reasonCodesModel.getReasonCode();
//					String reasonDes = reasonCodesModel.getName();
//
//					PdfPCell rejectReason = new PdfPCell(new Phrase(reasonDes,FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
//					rejectReason.setColspan(3);
//					rejectReason.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
//					rejectReason.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
//					reasonCodeData.addCell(rejectReason);
//
//
//					//Loop through Creditor Banks to get information
//					for(CreditorBankModel creditorBank :creditorBankModelList)
//					{
//						log.debug("Creditor Bank ==> "+creditorBank.getMemberNo());
//						log.debug("rejectReasonCode ==> "+ reasonCode);
//						log.debug("txnCount ==> "+ creditorBank.getTxnCount());
//
//						MandateRejectionModel  mandateRejectionModel = new MandateRejectionModel();
//						if(mdtLoadType.equalsIgnoreCase("M")) {
//							mandateRejectionModel =(MandateRejectionModel) adminBeanRemote.retrievePain012ReasonCodeDataPASA(reasonCode, creditorBank.getMemberNo(), strFirstDate, strLastDate);
//						}else {
//							mandateRejectionModel =(MandateRejectionModel) reportBeanRemote.retrievePain012ReasonCodeDataPASA(reasonCode, creditorBank.getMemberNo(), strFirstDate, strLastDate);
//						}
//
//
//						log.debug(reasonCode+ " Count ---->"+mandateRejectionModel.getRejectReasonCodeCount());
//						if(mandateRejectionModel == null || mandateRejectionModel.getRejectReasonCodeCount() == null)
//							mandateRejectionModel.setRejectReasonCodeCount(BigDecimal.ZERO);
//
//						creditorBank.setTxnCount(creditorBank.getTxnCount().add(mandateRejectionModel.getRejectReasonCodeCount()));
//
//						PdfPCell bankData = new PdfPCell(new Phrase(String.valueOf(mandateRejectionModel.getRejectReasonCodeCount()),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
//						bankData.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
//						bankData.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
//						reasonCodeData.addCell(bankData);
//					}
//				}
//				document.add(reasonCodeData);
//			}
//
//			//POPULATE TOTALS PER CREDITOR BANK/REASON CODE
//			PdfPTable totalTable = new PdfPTable(headingSize);
//			totalTable.setWidthPercentage(100);
//
//			PdfPCell totalHeading = new PdfPCell(new Phrase("TOTAL",FontFactory.getFont(FontFactory.HELVETICA, 9, com.itextpdf.text.Font.BOLD)));
//			totalHeading.setColspan(3);
//			totalHeading.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
//			totalHeading.setBorder(com.itextpdf.text.Rectangle.TOP | com.itextpdf.text.Rectangle.LEFT);
//			totalTable.addCell(totalHeading);
//
//			BigDecimal grandTotal = BigDecimal.ZERO;
//			int count = 2;
//			for(CreditorBankModel credModel :creditorBankModelList)
//			{
//				count++;
//
//				grandTotal = grandTotal.add(credModel.getTxnCount());
//				PdfPCell rsnCountData = new PdfPCell(new Phrase(String.valueOf(credModel.getTxnCount()),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
//				rsnCountData.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
//
//				if(count == headingSize)
//				{
//					rsnCountData.setBorder(com.itextpdf.text.Rectangle.TOP | com.itextpdf.text.Rectangle.BOTTOM |com.itextpdf.text.Rectangle.RIGHT);
//				}
//				else
//				{
//					rsnCountData.setBorder(com.itextpdf.text.Rectangle.TOP | com.itextpdf.text.Rectangle.BOTTOM);
//				}
//
//
//				totalTable.addCell(rsnCountData);
//			}
//
//			document.add(totalTable);
//
//
//			//POPULATE GRAND TOTAL
//			PdfPTable grandTotalTable = new PdfPTable(headingSize);
//			grandTotalTable.setWidthPercentage(100);
//
//			PdfPCell grdTotalHeading = new PdfPCell(new Phrase("GRAND TOTAL           "+String.valueOf(grandTotal),FontFactory.getFont(FontFactory.HELVETICA, 9, com.itextpdf.text.Font.BOLD)));
//			grdTotalHeading.setColspan(3);
//			grdTotalHeading.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
//			grdTotalHeading.setBorder(com.itextpdf.text.Rectangle.BOTTOM | com.itextpdf.text.Rectangle.LEFT |com.itextpdf.text.Rectangle.RIGHT);
//			grandTotalTable.addCell(grdTotalHeading);
//
//			PdfPCell nullCell001 = new PdfPCell(new Paragraph(null,FontFactory.getFont(FontFactory.HELVETICA, 8)));
//			nullCell001.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
//			nullCell001.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
//
//
//			int loopSize = headingSize-2;
//			log.debug("loopSize ==>"+loopSize);
//			for(int i=0; i < loopSize; i++)
//			{
//				grandTotalTable.addCell(nullCell001);
//			}
//
//			document.add(grandTotalTable);
//		}//debtor bank list > 0

		List<BatchRejectedTransactionModel> batchRejectedTransactionModelList = new ArrayList<>();
		
		if (frontDate != null) {
			batchRejectedTransactionModelList = (List<BatchRejectedTransactionModel>) reportBeanRemote.retrieveAllBatchRejectedTnxs(frontDateFormat.format(frontDate));
		} else {
			batchRejectedTransactionModelList = (List<BatchRejectedTransactionModel>) reportBeanRemote.retrieveAllBatchRejectedTnxs(null);
		}

		log.debug("batchRejectedTransactionModelList: " + batchRejectedTransactionModelList.toString());

		if(batchRejectedTransactionModelList != null && batchRejectedTransactionModelList.size() > 0){

			PdfPTable reasonCodeData = new PdfPTable(creditorBankModelList.size() + 3);
			reasonCodeData.setWidthPercentage(100);
				
			for(BatchRejectedTransactionModel batchRejectedTransactionModel : batchRejectedTransactionModelList){
				
				for(CreditorBankModel creditorBankModel : creditorBankModelList) {

				if(batchRejectedTransactionModel.getMemberNumber().equalsIgnoreCase(creditorBankModel.getMemberNo())){

					if(batchRejectedTransactionModel.getRescode().equalsIgnoreCase("AC01"))
					{
						if(ac01Boolean == false){
							PdfPCell rejectReason = new PdfPCell(new Phrase(batchRejectedTransactionModel.getMndtRejectionReason(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
							rejectReason.setColspan(3);
							rejectReason.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
							rejectReason.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
							reasonCodeData.addCell(rejectReason);
							ac01Boolean = true;
						}

						getTotals(batchRejectedTransactionModel);
					}

					if(batchRejectedTransactionModel.getRescode().equalsIgnoreCase("AC04"))
					{
						if(ac04Boolean == false){
							PdfPCell rejectReason = new PdfPCell(new Phrase(batchRejectedTransactionModel.getMndtRejectionReason(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
							rejectReason.setColspan(3);
							rejectReason.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
							rejectReason.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
							reasonCodeData.addCell(rejectReason);
							ac04Boolean = true;
						}

						getTotals(batchRejectedTransactionModel);
					}

					if(batchRejectedTransactionModel.getRescode().equalsIgnoreCase("AC06"))
					{
						if(ac06Boolean == false){
							PdfPCell rejectReason = new PdfPCell(new Phrase(batchRejectedTransactionModel.getMndtRejectionReason(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
							rejectReason.setColspan(3);
							rejectReason.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
							rejectReason.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
							reasonCodeData.addCell(rejectReason);
							ac06Boolean = true;
						}

						getTotals(batchRejectedTransactionModel);
					}

					if(batchRejectedTransactionModel.getRescode().equalsIgnoreCase("AM05"))
					{
						if(am05Boolean == false){
							PdfPCell rejectReason = new PdfPCell(new Phrase(batchRejectedTransactionModel.getMndtRejectionReason(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
							rejectReason.setColspan(3);
							rejectReason.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
							rejectReason.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
							reasonCodeData.addCell(rejectReason);
							am05Boolean = true;
						}

						getTotals(batchRejectedTransactionModel);
					}

					if(batchRejectedTransactionModel.getRescode().equalsIgnoreCase("BE05"))
					{
						if(be05Boolean == false){
							PdfPCell rejectReason = new PdfPCell(new Phrase(batchRejectedTransactionModel.getMndtRejectionReason(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
							rejectReason.setColspan(3);
							rejectReason.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
							rejectReason.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
							reasonCodeData.addCell(rejectReason);
							be05Boolean = true;
						}

						getTotals(batchRejectedTransactionModel);
					}

					if(batchRejectedTransactionModel.getRescode().equalsIgnoreCase("BE06"))
					{
						if(be06Boolean == false){
							PdfPCell rejectReason = new PdfPCell(new Phrase(batchRejectedTransactionModel.getMndtRejectionReason(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
							rejectReason.setColspan(3);
							rejectReason.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
							rejectReason.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
							reasonCodeData.addCell(rejectReason);
							be06Boolean = true;
						}

						getTotals(batchRejectedTransactionModel);
					}

					if(batchRejectedTransactionModel.getRescode().equalsIgnoreCase("DT01"))
					{
						if(dt01Boolean == false){
							PdfPCell rejectReason = new PdfPCell(new Phrase(batchRejectedTransactionModel.getMndtRejectionReason(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
							rejectReason.setColspan(3);
							rejectReason.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
							rejectReason.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
							reasonCodeData.addCell(rejectReason);
							dt01Boolean = true;
						}

						getTotals(batchRejectedTransactionModel);
					}

					if(batchRejectedTransactionModel.getRescode().equalsIgnoreCase("MD02"))
					{
						if(md02Boolean == false){
							PdfPCell rejectReason = new PdfPCell(new Phrase(batchRejectedTransactionModel.getMndtRejectionReason(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
							rejectReason.setColspan(3);
							rejectReason.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
							rejectReason.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
							reasonCodeData.addCell(rejectReason);
							md02Boolean = true;
						}

						getTotals(batchRejectedTransactionModel);
					}

					if(batchRejectedTransactionModel.getRescode().equalsIgnoreCase("MD07"))
					{
						if(md07Boolean == false){
							PdfPCell rejectReason = new PdfPCell(new Phrase(batchRejectedTransactionModel.getMndtRejectionReason(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
							rejectReason.setColspan(3);
							rejectReason.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
							rejectReason.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
							reasonCodeData.addCell(rejectReason);
							md07Boolean = true;
						}

						getTotals(batchRejectedTransactionModel);
					}

					if(batchRejectedTransactionModel.getRescode().equalsIgnoreCase("MD09"))
					{
						if(md09Boolean == false){
							PdfPCell rejectReason = new PdfPCell(new Phrase(batchRejectedTransactionModel.getMndtRejectionReason(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
							rejectReason.setColspan(3);
							rejectReason.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
							rejectReason.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
							reasonCodeData.addCell(rejectReason);
							md09Boolean = true;
						}

						getTotals(batchRejectedTransactionModel);
					}

					if(batchRejectedTransactionModel.getRescode().equalsIgnoreCase("MD11"))
					{
						if(md11Boolean == false){
							PdfPCell rejectReason = new PdfPCell(new Phrase(batchRejectedTransactionModel.getMndtRejectionReason(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
							rejectReason.setColspan(3);
							rejectReason.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
							rejectReason.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
							reasonCodeData.addCell(rejectReason);
							md11Boolean = true;
						}

						getTotals(batchRejectedTransactionModel);
					}

					if(batchRejectedTransactionModel.getRescode().equalsIgnoreCase("MD12"))
					{
						if(md12Boolean == false){
							PdfPCell rejectReason = new PdfPCell(new Phrase(batchRejectedTransactionModel.getMndtRejectionReason(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
							rejectReason.setColspan(3);
							rejectReason.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
							rejectReason.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
							reasonCodeData.addCell(rejectReason);
							md12Boolean = true;
						}

						getTotals(batchRejectedTransactionModel);
					}

					if(batchRejectedTransactionModel.getRescode().equalsIgnoreCase("MD13"))
					{
						if(md13Boolean == false){
							PdfPCell rejectReason = new PdfPCell(new Phrase(batchRejectedTransactionModel.getMndtRejectionReason(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
							rejectReason.setColspan(3);
							rejectReason.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
							rejectReason.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
							reasonCodeData.addCell(rejectReason);
							md13Boolean = true;
						}

						getTotals(batchRejectedTransactionModel);
					}

					if(batchRejectedTransactionModel.getRescode().equalsIgnoreCase("MD16"))
					{
						if(md16Boolean == false){
							PdfPCell rejectReason = new PdfPCell(new Phrase(batchRejectedTransactionModel.getMndtRejectionReason(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
							rejectReason.setColspan(3);
							rejectReason.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
							rejectReason.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
							reasonCodeData.addCell(rejectReason);
							md16Boolean = true;
						}

						getTotals(batchRejectedTransactionModel);
					}

					if(batchRejectedTransactionModel.getRescode().equalsIgnoreCase("MD17"))
					{
						if(md17Boolean == false){
							PdfPCell rejectReason = new PdfPCell(new Phrase(batchRejectedTransactionModel.getMndtRejectionReason(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
							rejectReason.setColspan(3);
							rejectReason.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
							rejectReason.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
							reasonCodeData.addCell(rejectReason);
							md17Boolean = true;
						}

						getTotals(batchRejectedTransactionModel);
					}

					if(batchRejectedTransactionModel.getRescode().equalsIgnoreCase("MD18"))
					{
						if(md18Boolean == false){
							PdfPCell rejectReason = new PdfPCell(new Phrase(batchRejectedTransactionModel.getMndtRejectionReason(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
							rejectReason.setColspan(3);
							rejectReason.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
							rejectReason.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
							reasonCodeData.addCell(rejectReason);
							md18Boolean = true;
						}

						getTotals(batchRejectedTransactionModel);
					}

					if(batchRejectedTransactionModel.getRescode().equalsIgnoreCase("MS02"))
					{
						if(ms02Boolean == false){
							PdfPCell rejectReason = new PdfPCell(new Phrase(batchRejectedTransactionModel.getMndtRejectionReason(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
							rejectReason.setColspan(3);
							rejectReason.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
							rejectReason.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
							reasonCodeData.addCell(rejectReason);
							ms02Boolean = true;
						}

						getTotals(batchRejectedTransactionModel);
					}

					if(batchRejectedTransactionModel.getRescode().equalsIgnoreCase("MS03"))
					{
						if(ms03Boolean == false){
							PdfPCell rejectReason = new PdfPCell(new Phrase(batchRejectedTransactionModel.getMndtRejectionReason(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
							rejectReason.setColspan(3);
							rejectReason.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
							rejectReason.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
							reasonCodeData.addCell(rejectReason);
							ms03Boolean = true;
						}

						getTotals(batchRejectedTransactionModel);
					}

					if(batchRejectedTransactionModel.getRescode().equalsIgnoreCase("NMTC"))
					{
						if(nmtcBoolean == false){
							PdfPCell rejectReason = new PdfPCell(new Phrase(batchRejectedTransactionModel.getMndtRejectionReason(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
							rejectReason.setColspan(3);
							rejectReason.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
							rejectReason.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
							reasonCodeData.addCell(rejectReason);
							nmtcBoolean = true;
						}

						getTotals(batchRejectedTransactionModel);
					}

					if(batchRejectedTransactionModel.getRescode().equalsIgnoreCase("TO01"))
					{
						if(to01Boolean == false){
							PdfPCell rejectReason = new PdfPCell(new Phrase(batchRejectedTransactionModel.getMndtRejectionReason(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
							rejectReason.setColspan(3);
							rejectReason.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
							rejectReason.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
							reasonCodeData.addCell(rejectReason);
							to01Boolean = true;
						}

						getTotals(batchRejectedTransactionModel);
					}

						PdfPCell bankData = new PdfPCell(new Phrase(String.valueOf(batchRejectedTransactionModel.getNoTxns()),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
						bankData.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
						bankData.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
						reasonCodeData.addCell(bankData);

				}
			}
		}

			document.add(reasonCodeData);
		}

		//POPULATE TOTALS PER CREDITOR BANK/REASON CODE
		PdfPTable totalTable = new PdfPTable(13);
		totalTable.setWidthPercentage(100);

		PdfPCell totalHeading = new PdfPCell(new Phrase("TOTAL",FontFactory.getFont(FontFactory.HELVETICA, 9, com.itextpdf.text.Font.BOLD)));
		totalHeading.setColspan(3);
		totalHeading.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		totalHeading.setBorder(com.itextpdf.text.Rectangle.TOP | com.itextpdf.text.Rectangle.LEFT);
		totalTable.addCell(totalHeading);

		List<BigDecimal> totalItems = new ArrayList<>();
		totalItems.add(absaTotal);
		totalItems.add(grobTotal);
		totalItems.add(africanbTotal);
		totalItems.add(capitecTotal);
		totalItems.add(finbondTotal);
		totalItems.add(fnbTotal);
		totalItems.add(mercanTotal);
		totalItems.add(nedbankTotal);
		totalItems.add(stdTotal);
		totalItems.add(ubankTotal);

		for(int i = 0; i < totalItems.size();i++){

			PdfPCell rsnCountData = new PdfPCell(new Phrase(totalItems.get(i).toString(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
			rsnCountData.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			rsnCountData.setBorder(com.itextpdf.text.Rectangle.TOP | com.itextpdf.text.Rectangle.BOTTOM);
			totalTable.addCell(rsnCountData);
		}

		document.add(totalTable);

		int grandTotal = absaTotal.intValue() + africanbTotal.intValue() + capitecTotal.intValue() + finbondTotal.intValue() + fnbTotal.intValue()
				+ grobTotal.intValue() + mercanTotal.intValue() + nedbankTotal.intValue() + stdTotal.intValue() + ubankTotal.intValue();


		//POPULATE GRAND TOTAL
		PdfPTable grandTotalTable = new PdfPTable(13);
		grandTotalTable.setWidthPercentage(100);

		PdfPCell grdTotalHeading = new PdfPCell(new Phrase("GRAND TOTAL           "+String.valueOf(grandTotal),FontFactory.getFont(FontFactory.HELVETICA, 9, com.itextpdf.text.Font.BOLD)));
		grdTotalHeading.setColspan(3);
		grdTotalHeading.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		grdTotalHeading.setBorder(com.itextpdf.text.Rectangle.BOTTOM | com.itextpdf.text.Rectangle.LEFT |com.itextpdf.text.Rectangle.RIGHT);
		grandTotalTable.addCell(grdTotalHeading);

		PdfPCell nullCell001 = new PdfPCell(new Paragraph(null,FontFactory.getFont(FontFactory.HELVETICA, 8)));
		nullCell001.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		nullCell001.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);


		int loopSize = 10;
		log.debug("loopSize ==>"+loopSize);
		for(int i=0; i < loopSize; i++)
		{
			grandTotalTable.addCell(nullCell001);
		}

		document.add(grandTotalTable);
		
		document.close();
		
		log.info("*****REPORT GENERATION COMPLETED*****");
		//Copy the report to the Output reports folder 
		try
		{
			copyFile(pdfFileName);
		}
		catch(IOException ioe)
		{
			log.error("Error on copying PSMD04 report to temp "+ioe.getMessage());
			ioe.printStackTrace();
		}
		catch(Exception ex)
		{
			log.error("Error on copying PSMD04 report to temp "+ex.getMessage());
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

	public void getTotals(BatchRejectedTransactionModel batchRejectedTransactionModel){

		if(batchRejectedTransactionModel.getMemberNumber().equalsIgnoreCase("210016")){
			absaTotal = absaTotal.add(batchRejectedTransactionModel.getNoTxns());
		}

		if(batchRejectedTransactionModel.getMemberNumber().equalsIgnoreCase("210007")){
			africanbTotal = africanbTotal.add(batchRejectedTransactionModel.getNoTxns());
		}

		if(batchRejectedTransactionModel.getMemberNumber().equalsIgnoreCase("210010")){
			capitecTotal = capitecTotal.add(batchRejectedTransactionModel.getNoTxns());
		}

		if(batchRejectedTransactionModel.getMemberNumber().equalsIgnoreCase("210055")){
			finbondTotal = finbondTotal.add(batchRejectedTransactionModel.getNoTxns());
		}

		if(batchRejectedTransactionModel.getMemberNumber().equalsIgnoreCase("210003")){
			fnbTotal = fnbTotal.add(batchRejectedTransactionModel.getNoTxns());
		}

		if(batchRejectedTransactionModel.getMemberNumber().equalsIgnoreCase("210006")){
			grobTotal = grobTotal.add(batchRejectedTransactionModel.getNoTxns());
		}

		if(batchRejectedTransactionModel.getMemberNumber().equalsIgnoreCase("210009")){
			mercanTotal = mercanTotal.add(batchRejectedTransactionModel.getNoTxns());
		}

		if(batchRejectedTransactionModel.getMemberNumber().equalsIgnoreCase("210002")){
			nedbankTotal = nedbankTotal.add(batchRejectedTransactionModel.getNoTxns());
		}

		if(batchRejectedTransactionModel.getMemberNumber().equalsIgnoreCase("210001")){
			stdTotal = stdTotal.add(batchRejectedTransactionModel.getNoTxns());
		}

		if(batchRejectedTransactionModel.getMemberNumber().equalsIgnoreCase("210019")){
			ubankTotal = ubankTotal.add(batchRejectedTransactionModel.getNoTxns());
		}
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
