package com.bsva.panels.AcPasaReports;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.handler.resource.ResourceStreamRequestHandler;
import org.apache.wicket.util.resource.FileResourceStream;

import com.bsva.commons.model.CreditorBankModel;
import com.bsva.commons.model.MandateRejectionModel;
import com.bsva.commons.model.ReasonCodesModel;
import com.bsva.commons.model.SysctrlCompParamModel;
import com.bsva.commons.model.SystemParameterModel;
import com.bsva.controller.Controller;
import com.bsva.utils.DateUtil;
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

/***
 * 
 * @author DimakatsoN
 * @author SalehaR - 2017-09-07 - Rework report population
 *
 */

public class PasaMndtRejectionSummaryReport {
	
	public static Logger log=Logger.getLogger(PasaMndtRejectionSummaryReport.class);

	Date currentDate = new Date();
	private String downloaddirectory ="/home/opsjava/Delivery/Mandates/Reports/";

	Controller controller  = new Controller();
	
	List<ReasonCodesModel>rejectReasonCodesList;
	int fileSeqNo =000;
	
	List<CreditorBankModel> creditorBankModelList;
	CreditorBankModel creditorBankModel ;
	
	
	
	public void generateReport(String reportNr, String reportname) throws FileNotFoundException, DocumentException
	{
		log.debug("in the generate report method ");
		
		generateReportColumns( reportNr,reportname);
	}
	
	

	public void generateReportColumns(String reportNo,String reportname ) throws FileNotFoundException, DocumentException
	{
		log.info("*****GENERATING PMBD04 REPORT *****");
		log.debug("The report cloumns method ****************");
		DecimalFormat df = new DecimalFormat("### ### ### ### ### ##0.00");
		DateFormat dateTimeFormat = new SimpleDateFormat("yyyyMMdd_HH:mm:ss");
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat fileTimeFormat = new SimpleDateFormat("MM-yyyy");
		DateFormat endDateFormat = new SimpleDateFormat("ddMMyyyy");
		Date currentDate = new Date();
		String strFirstDate = null, strLastDate = null;

		 int pageNo = 1;
		 
		 fileSeqNo =fileSeqNo + 1;
		 String strSeqNo = String.format("%03d",fileSeqNo);

		 String files = downloaddirectory+"0000AC"+reportNo+endDateFormat.format(currentDate).toString()+"."+strSeqNo + ".pdf";

		  Document document = new Document(PageSize.A4.rotate());

		FileOutputStream fileOutputStream = new FileOutputStream(files);
		PdfWriter writer = null;
		writer = PdfWriter.getInstance(document, fileOutputStream);
		writer.open();
		document.open();

		File file = new File(files);


		ResourceStreamRequestHandler target = new ResourceStreamRequestHandler( new FileResourceStream(new org.apache.wicket.util.file.File(file)));
		PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(files));
		target.setFileName(files);
		RequestCycle.get().scheduleRequestHandlerAfterCurrent(target);
		document.open();

		SysctrlCompParamModel companyParamModel = new SysctrlCompParamModel();
		companyParamModel = (SysctrlCompParamModel) controller.retrieveCompanyParameters();

		SystemParameterModel systemParameterModel = new SystemParameterModel();
		systemParameterModel =(SystemParameterModel)controller.retrieveWebActiveSysParameter();

		PdfPTable freeline = new PdfPTable(1);
		freeline.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		freeline.setWidthPercentage(100);
		freeline.addCell( new PdfPCell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 8)))).setBorderColor(BaseColor.WHITE);

		PdfPTable firstHeading = new PdfPTable(3);
		firstHeading.setWidthPercentage(100);

		try {
			PdfPCell cell1 = new PdfPCell(new Paragraph("Date: " + dateTimeFormat.format(currentDate), FontFactory.getFont(FontFactory.HELVETICA, 8)));
			cell1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
			cell1.setBorder(Rectangle.NO_BORDER);
			firstHeading.addCell(cell1);

			PdfPCell cell2 = new PdfPCell(new Paragraph(companyParamModel.getCompName(), FontFactory.getFont(FontFactory.HELVETICA, 9, com.itextpdf.text.Font.BOLD)));
			cell2.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			cell2.setBorder(Rectangle.NO_BORDER);
			firstHeading.addCell(cell2);

			PdfPCell cell3 = new PdfPCell(new Paragraph("Page:  " + pageNo,   FontFactory.getFont(FontFactory.HELVETICA, 8)));
			cell3.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
			cell3.setBorder(Rectangle.NO_BORDER);
			firstHeading.addCell(cell3);

		} 
		catch (NullPointerException x) 
		{
			log.debug("Error on Page 1 Header" + x);
		}

		document.add(firstHeading);


		PdfPTable secondheading = new PdfPTable(3);
		secondheading.setWidthPercentage(100);
		try 
		{

			PdfPCell nullCell001 = new PdfPCell(new Paragraph(null,FontFactory.getFont(FontFactory.HELVETICA, 8)));
			nullCell001.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
			nullCell001.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
			secondheading.addCell(nullCell001);

			PdfPCell nullCell02 = new PdfPCell(new Paragraph("REG.NO."+ companyParamModel.getRegistrationNr(), FontFactory.getFont(FontFactory.HELVETICA, 8)));
			nullCell02.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			nullCell02.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
			secondheading.addCell(nullCell02);
			
			String strMonth = null;
			String month = null;
			String year = null;
			Date firstDate, lastDate = null;
			
	
			if(systemParameterModel.getProcessDate() != null)
			{
				strMonth =fileTimeFormat.format(systemParameterModel.getProcessDate()).substring(0,2);
				log.debug("strMonth ==> "+strMonth);
				month= DateUtil.getMonthFullname(Integer.valueOf(strMonth));
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

	          PdfPCell nullCell03 = new PdfPCell(new Paragraph("Process Month: " + month+" "+year,  FontFactory.getFont(FontFactory.HELVETICA, 8)));
	          nullCell03.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
	          nullCell03.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
	          secondheading.addCell(nullCell03);

		} 
		catch (NullPointerException x)
		{
			log.debug("Error Finding Company Reg No" + x);
		}
		document.add(secondheading);
		document.add(freeline);


		PdfPTable thirdHeading = new PdfPTable(1);
		thirdHeading.setWidthPercentage(100);

		PdfPCell cell1 = new PdfPCell(new Paragraph(reportNo+ " - "+ reportname, FontFactory.getFont( FontFactory.HELVETICA, 9, com.itextpdf.text.Font.BOLD)));
		cell1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		cell1.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
		thirdHeading.addCell(cell1);

		document.add(thirdHeading);
		document.add(freeline);
		

		creditorBankModelList = new ArrayList<CreditorBankModel>();
		creditorBankModelList=(List<CreditorBankModel>)controller.retrieveActiveCreditorBank();
		int headingSize = 0;
		
		if(creditorBankModelList != null && creditorBankModelList.size() > 0)
		{
			headingSize = creditorBankModelList.size() + 2;//for the 1st column
			log.debug("headingSize == "+headingSize);
			PdfPTable batchDetailsHeading = new PdfPTable(headingSize);
			batchDetailsHeading.setWidthPercentage(100);


			PdfPCell rejectReasonHeading = new PdfPCell(new Phrase("MANDATE REJECTION REASONS",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
			rejectReasonHeading.setColspan(2);
			rejectReasonHeading.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
			rejectReasonHeading.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
			batchDetailsHeading.addCell(rejectReasonHeading);

			String creditorBankId =  null;
			for(CreditorBankModel creditorBankModel :creditorBankModelList)
			{

				String creditorBank=creditorBankModel.getMemberName();
				creditorBankId= creditorBankModel.getMemberNo();

				PdfPCell creditorBanksData = new PdfPCell(new Phrase(creditorBank,FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
				creditorBanksData.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
				creditorBanksData.setBorder(com.itextpdf.text.Rectangle.TOP | com.itextpdf.text.Rectangle.BOTTOM);
				batchDetailsHeading.addCell(creditorBanksData);
				
				document.add(batchDetailsHeading);
				
				creditorBankModel.setTxnCount(BigDecimal.ZERO);
			}
			
			
			rejectReasonCodesList = new ArrayList<ReasonCodesModel>();
			rejectReasonCodesList = (List<ReasonCodesModel>)controller.retrieveAllReportRejections();
			if(rejectReasonCodesList!= null && rejectReasonCodesList.size()> 0)
			{
				log.debug("rejectReasonCodesList.size() ==> "+rejectReasonCodesList.size());
				
				PdfPTable reasonCodeData = new PdfPTable(headingSize);
				reasonCodeData.setWidthPercentage(100);
				
				//Loop through reason codes
				for(ReasonCodesModel reasonCodesModel :rejectReasonCodesList)
				{
					String reasonCode = reasonCodesModel.getReasonCode();
					String reasonDes = reasonCodesModel.getName();
					
					PdfPCell rejectReason = new PdfPCell(new Phrase(reasonDes,FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
					rejectReason.setColspan(2);
					rejectReason.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
					rejectReason.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
					reasonCodeData.addCell(rejectReason);
					
					
					//Loop through Creditor Banks to get information
					for(CreditorBankModel creditorBank :creditorBankModelList)
					{
						log.debug("Creditor Bank ==> "+creditorBank.getMemberNo());
						log.debug("rejectReasonCode ==> "+ reasonCode);
						log.debug("txnCount ==> "+ creditorBank.getTxnCount());
						
				
							MandateRejectionModel  mandateRejectionModel = new MandateRejectionModel();	
							mandateRejectionModel =(MandateRejectionModel)controller.retrievePASARejectionSummaryPAIN012(reasonCode, creditorBank.getMemberNo(), strFirstDate, strLastDate);
							
							log.debug(reasonCode+ " Count ---->"+mandateRejectionModel.getRejectReasonCodeCount());
							if(mandateRejectionModel == null || mandateRejectionModel.getRejectReasonCodeCount() == null)
								mandateRejectionModel.setRejectReasonCodeCount(BigDecimal.ZERO);
							
							creditorBank.setTxnCount(creditorBank.getTxnCount().add(mandateRejectionModel.getRejectReasonCodeCount()));
							
							PdfPCell bankData = new PdfPCell(new Phrase(String.valueOf(mandateRejectionModel.getRejectReasonCodeCount()),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
							bankData.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
							bankData.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
							reasonCodeData.addCell(bankData);	
												
					}
				}
				document.add(reasonCodeData);
			}
			
			//POPULATE TOTALS PER CREDITOR BANK/REASON CODE
			PdfPTable totalTable = new PdfPTable(headingSize);
			totalTable.setWidthPercentage(100);
			
			PdfPCell totalHeading = new PdfPCell(new Phrase("TOTAL",FontFactory.getFont(FontFactory.HELVETICA, 9, com.itextpdf.text.Font.BOLD)));
			totalHeading.setColspan(2);
			totalHeading.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
			totalHeading.setBorder(com.itextpdf.text.Rectangle.TOP | com.itextpdf.text.Rectangle.LEFT);
			totalTable.addCell(totalHeading);
			
			BigDecimal grandTotal = BigDecimal.ZERO;
			int count = 2;
			for(CreditorBankModel credModel :creditorBankModelList)
			{
				count++;
				
				grandTotal = grandTotal.add(credModel.getTxnCount());
				PdfPCell rsnCountData = new PdfPCell(new Phrase(String.valueOf(credModel.getTxnCount()),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
				rsnCountData.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
				
				if(count == headingSize)
				{
					rsnCountData.setBorder(com.itextpdf.text.Rectangle.TOP | com.itextpdf.text.Rectangle.BOTTOM |com.itextpdf.text.Rectangle.RIGHT);
				}
				else
				{
					rsnCountData.setBorder(com.itextpdf.text.Rectangle.TOP | com.itextpdf.text.Rectangle.BOTTOM);
				}
					
				
				totalTable.addCell(rsnCountData);	
			}
		
			document.add(totalTable);
			
			
			//POPULATE GRAND TOTAL
			PdfPTable grandTotalTable = new PdfPTable(headingSize);
			grandTotalTable.setWidthPercentage(100);
			
			PdfPCell grdTotalHeading = new PdfPCell(new Phrase("GRAND TOTAL           "+String.valueOf(grandTotal),FontFactory.getFont(FontFactory.HELVETICA, 9, com.itextpdf.text.Font.BOLD)));
			grdTotalHeading.setColspan(2);
			grdTotalHeading.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
			grdTotalHeading.setBorder(com.itextpdf.text.Rectangle.BOTTOM | com.itextpdf.text.Rectangle.LEFT |com.itextpdf.text.Rectangle.RIGHT);
			grandTotalTable.addCell(grdTotalHeading);
			
			PdfPCell nullCell001 = new PdfPCell(new Paragraph(null,FontFactory.getFont(FontFactory.HELVETICA, 8)));
			nullCell001.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
			nullCell001.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
			
			
			int loopSize = headingSize-2;
			log.debug("loopSize ==>"+loopSize);
			for(int i=0; i < loopSize; i++) 
			{
				grandTotalTable.addCell(nullCell001);
			}

			document.add(grandTotalTable);
		}//debtor bank list > 0
		
		document.close();
		log.info("*****REPORT GENERATION COMPLETED*****");
	}
}
