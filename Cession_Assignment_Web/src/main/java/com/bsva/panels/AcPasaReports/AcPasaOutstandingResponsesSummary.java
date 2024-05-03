
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

import com.bsva.commons.model.BatchOustandingResponseModel;
import com.bsva.commons.model.CreditorBankModel;
import com.bsva.commons.model.CustomerParametersModel;
import com.bsva.commons.model.DebtorBankModel;
import com.bsva.commons.model.MandateAmendModel;
import com.bsva.commons.model.MndtSummaryTotalsModel;
import com.bsva.commons.model.OutstandingRespSummaryCountModel;
import com.bsva.commons.model.OutstandingResponsesDebtorModel;
import com.bsva.commons.model.PasaOutsatandingReportModel;
import com.bsva.commons.model.SysctrlCompParamModel;
import com.bsva.commons.model.SystemParameterModel;
import com.bsva.controller.Controller;
import com.bsva.panels.reports.OutstandingResponsesReport;
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
 *
 */

public class AcPasaOutstandingResponsesSummary {
	

	
	private String downloaddirectory ="/home/opsjava/Delivery/Cession_Assign/Reports/";
	public static Logger log=Logger.getLogger(OutstandingResponsesReport.class);
	Controller controller = new Controller();
	int fileSeqNo =000;
	boolean respModel = false;
	

	
	List<DebtorBankModel> debtorBankModelList;
	DebtorBankModel debtorBankModel ;

	List<CreditorBankModel> creditorBankModelList;
	CreditorBankModel creditorBankModel ;

	public void generateReport(String reportNr, String reportname) throws FileNotFoundException, DocumentException
	{
		log.info("in the generate report method ");
		generateReportColumns( reportNr,reportname);
	}
	
	
	

	public void generateReportColumns(String reportNo,String reportName )  throws DocumentException, FileNotFoundException
	{
		DecimalFormat df = new DecimalFormat("### ### ### ### ### ##0.00");
        DateFormat dateTimeFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat fileTimeFormat = new SimpleDateFormat("MM-yyyy");
        Date currentDate = new Date();
        DateFormat endDateFormat = new SimpleDateFormat("ddMMyyyy");
    	
        
        
        int pageNo = 1;
        
        fileSeqNo =fileSeqNo + 1;

        String files = downloaddirectory+"0000AC"+reportNo+endDateFormat.format(currentDate).toString()+"."+fileSeqNo + ".pdf";

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

		String strMonth = null;
		String month = null;
		String year = null;
		Date firstDate, lastDate = null;
		String strFirstDate = null, strLastDate = null;

		PdfPTable secondheading = new PdfPTable(3);
		secondheading.setWidthPercentage(100);
		try 
		{
			
	
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
			    log.debug("lastDate ==> "+ lastDate);
			    log.debug("endDate ==> "+ endDateFormat.format(lastDate));
			}
			PdfPCell nullCell001 = new PdfPCell(new Paragraph(null,FontFactory.getFont(FontFactory.HELVETICA, 8)));
			nullCell001.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
			nullCell001.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
			secondheading.addCell(nullCell001);

			
			PdfPCell nullCell02 = new PdfPCell(new Paragraph("REG.NO."+ companyParamModel.getRegistrationNr(), FontFactory.getFont(FontFactory.HELVETICA, 8)));
			nullCell02.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			nullCell02.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
			secondheading.addCell(nullCell02);
			
	         PdfPCell nullCell03 = new PdfPCell(new Paragraph("Process Date:"+endDateFormat.format(currentDate).toString(),  FontFactory.getFont(FontFactory.HELVETICA, 8)));
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

        
/*        PdfPTable thirdHeading = new PdfPTable(1);
        thirdHeading.setWidthPercentage(100);
        
        try{
        	String strProcDate = null;
			if(systemParameterModel.getProcessDate() != null)
			{
				 strProcDate = dateFormat.format(systemParameterModel.getProcessDate());
			}
				
	          PdfPCell cell3 = new PdfPCell(new Paragraph("As at: " + strProcDate,  FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
	          cell3.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
	          cell3.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
	          thirdHeading.addCell(cell3);
        }
        catch (NullPointerException x) 
        {
        	log.debug("Error Finding Process Date" + x);
        }
        document.add(thirdHeading);
    	document.add(freeline);*/
    	
    	
    	PdfPTable reportHeading = new PdfPTable(1);
    	reportHeading.setWidthPercentage(100);

		PdfPCell cell1 = new PdfPCell(new Paragraph(reportNo+ ","+ reportName.toUpperCase(), FontFactory.getFont( FontFactory.HELVETICA, 9, com.itextpdf.text.Font.BOLD)));
		cell1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		cell1.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
		reportHeading.addCell(cell1);

		document.add(reportHeading);
		document.add(freeline);


    	

  		PdfPTable batchDetailsHeading = new PdfPTable(8);
   		batchDetailsHeading.setWidthPercentage(100);


   		PdfPCell credBank = new PdfPCell(new Phrase("Debtor Bank",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
   		credBank.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
   		credBank.setUseVariableBorders(true);
   		credBank.setBorderColorTop(BaseColor.BLACK);
		credBank.setBorderColorBottom(BaseColor.BLACK);
		credBank.setBorderColorLeft(BaseColor.WHITE);
		credBank.setBorderColorRight(BaseColor.WHITE);
   		batchDetailsHeading.addCell(credBank);
   		
		PdfPCell drMemId = new PdfPCell(new Phrase("Creditor Bank",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
   		drMemId.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
   		drMemId.setUseVariableBorders(true);
   		drMemId.setBorderColorTop(BaseColor.BLACK);
   		drMemId.setBorderColorBottom(BaseColor.BLACK);
   		drMemId.setBorderColorLeft(BaseColor.WHITE);
   		drMemId.setBorderColorRight(BaseColor.WHITE);
   		batchDetailsHeading.addCell(drMemId);

		PdfPCell drMemName = new PdfPCell(new Phrase("Mandate intiation response outstanding 1 day",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		drMemName.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		drMemName.setUseVariableBorders(true);
		drMemName.setBorderColorTop(BaseColor.BLACK);
		drMemName.setBorderColorBottom(BaseColor.BLACK);
   		drMemName.setBorderColorLeft(BaseColor.WHITE);
   		drMemName.setBorderColorRight(BaseColor.WHITE);
   		batchDetailsHeading.addCell(drMemName);
   		
   		PdfPCell totalOutstCell = new PdfPCell(new Phrase("Mandate intiation response outstanding 2 days",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
   		totalOutstCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
   		totalOutstCell.setUseVariableBorders(true);
   		totalOutstCell.setBorderColorTop(BaseColor.BLACK);
   		totalOutstCell.setBorderColorBottom(BaseColor.BLACK);
   		totalOutstCell.setBorderColorLeft(BaseColor.WHITE);
   		totalOutstCell.setBorderColorRight(BaseColor.WHITE);
   		batchDetailsHeading.addCell(totalOutstCell);
   		
   		PdfPCell totalNrDays = new PdfPCell(new Phrase("Mandate amendement response outstanding 1 day",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
   		totalNrDays.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
   		totalNrDays.setUseVariableBorders(true);
   		totalNrDays.setBorderColorTop(BaseColor.BLACK);
   		totalNrDays.setBorderColorBottom(BaseColor.BLACK);
   		totalNrDays.setBorderColorLeft(BaseColor.WHITE);
   		totalNrDays.setBorderColorRight(BaseColor.WHITE);
   		batchDetailsHeading.addCell(totalNrDays);
   		
   		
   		PdfPCell amendData = new PdfPCell(new Phrase("Mandate amendement response outstanding 2 days",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
   		amendData.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
   		amendData.setUseVariableBorders(true);
   		amendData.setBorderColorTop(BaseColor.BLACK);
   		amendData.setBorderColorBottom(BaseColor.BLACK);
   		amendData.setBorderColorLeft(BaseColor.WHITE);
   		amendData.setBorderColorRight(BaseColor.WHITE);
   		batchDetailsHeading.addCell(amendData);
   		
   		
   		PdfPCell cancelData = new PdfPCell(new Phrase("Mandate cancellation response outstanding 1 day",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
   		cancelData.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
   		cancelData.setUseVariableBorders(true);
   		cancelData.setBorderColorTop(BaseColor.BLACK);
   		cancelData.setBorderColorBottom(BaseColor.BLACK);
   		cancelData.setBorderColorLeft(BaseColor.WHITE);
   		cancelData.setBorderColorRight(BaseColor.WHITE);
   		batchDetailsHeading.addCell(cancelData);
   		 		
   		PdfPCell cancelData2 = new PdfPCell(new Phrase("Mandate cancellation response outstanding 2 days",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
   		cancelData2.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
   		cancelData2.setUseVariableBorders(true);
   		cancelData2.setBorderColorTop(BaseColor.BLACK);
   		cancelData2.setBorderColorBottom(BaseColor.BLACK);
   		cancelData2.setBorderColorLeft(BaseColor.WHITE);
   		cancelData2.setBorderColorRight(BaseColor.WHITE);
   		batchDetailsHeading.addCell(cancelData2);

   		document.add(batchDetailsHeading);
   		document.add(freeline);

   		
   
		
   		debtorBankModelList = new ArrayList<DebtorBankModel>();
		debtorBankModelList = (List<DebtorBankModel>) controller.retrieveActiveDebtorBank();
		creditorBankModelList = new ArrayList<CreditorBankModel>();
   		creditorBankModelList=(List<CreditorBankModel>)controller.retrieveActiveCreditorBank();
   		
		
		if(debtorBankModelList != null && debtorBankModelList.size() > 0)
		{
			for(DebtorBankModel debtorBankModel : debtorBankModelList)
			{
				String debtorName= debtorBankModel.getMemberName();
		   		String debtorMember = debtorBankModel.getMemberNo();
		   		boolean debtPop = false;
		   		
				PdfPTable batchDataHeading = new PdfPTable(8);
		   		batchDataHeading.setWidthPercentage(100);
		   	
		   		if(creditorBankModelList != null && creditorBankModelList.size() > 0)
		   		{
		   			for(CreditorBankModel creditorBankModel :creditorBankModelList)
		   			{
		   				
		   				String creditorName= creditorBankModel.getMemberName();
				   		String creditorMember = creditorBankModel.getMemberNo();
				   		
				   		BatchOustandingResponseModel manin1Model  = new BatchOustandingResponseModel();
		   				manin1Model = (BatchOustandingResponseModel)controller.retrieverRespOutstandForOneDay(debtorMember,creditorMember,"MANIN");
		   				BatchOustandingResponseModel manin2Model  = new BatchOustandingResponseModel();
		   				manin2Model = (BatchOustandingResponseModel)controller.retrieverRespOutstandForTwoDays(debtorMember,creditorMember,"MANIN");
		   				BatchOustandingResponseModel manam1Model  = new BatchOustandingResponseModel();
		   				manam1Model = (BatchOustandingResponseModel)controller.retrieverRespOutstandForOneDay(debtorMember,creditorMember,"MANAM");
		   				BatchOustandingResponseModel manam2Model  = new BatchOustandingResponseModel();
		   				manam2Model = (BatchOustandingResponseModel)controller.retrieverRespOutstandForTwoDays(debtorMember,creditorMember,"MANAM");
		   				BatchOustandingResponseModel mancn1Model  = new BatchOustandingResponseModel();
		   				mancn1Model = (BatchOustandingResponseModel)controller.retrieverRespOutstandForOneDay(debtorMember,creditorMember,"MANCN");
		   				BatchOustandingResponseModel mancn2Model  = new BatchOustandingResponseModel();
		   				mancn2Model = (BatchOustandingResponseModel)controller.retrieverRespOutstandForTwoDays(debtorMember,creditorMember,"MANCN");
		   				
		   				
						if(manin1Model.getNrOfTxns()== null)
							manin1Model.setNrOfTxns(BigDecimal.ZERO);
		   				int oneDayManin=manin1Model.getNrOfTxns().intValue();
		   				
		   				if(manin2Model.getNrOfTxns()== null)
							manin2Model.setNrOfTxns(BigDecimal.ZERO);
		   				int twoDayManin=manin2Model.getNrOfTxns().intValue();
		   				
		   				if(manam1Model.getNrOfTxns()== null)
							manam1Model.setNrOfTxns(BigDecimal.ZERO);
		   				int oneDayManam=manam1Model.getNrOfTxns().intValue();
		   				if(manam2Model.getNrOfTxns()== null)
		   					
							manam2Model.setNrOfTxns(BigDecimal.ZERO);
		   				int twoDayManam=manam2Model.getNrOfTxns().intValue();
		   				if(mancn1Model.getNrOfTxns()== null)
							mancn1Model.setNrOfTxns(BigDecimal.ZERO);
		   				int oneDayMancn=mancn1Model.getNrOfTxns().intValue();
		   				
		   				if(mancn2Model.getNrOfTxns()== null)
							mancn2Model.setNrOfTxns(BigDecimal.ZERO);
		   				int twoDayMancn=mancn2Model.getNrOfTxns().intValue();

		   				
							
							if(oneDayManin > 0 ||twoDayManin > 0 ||oneDayManam > 0 ||twoDayManam > 0 ||oneDayMancn > 0 ||twoDayMancn > 0 )
							{
								if(debtPop == false)
								{
									PdfPCell credBankData = new PdfPCell(new Phrase(debtorName,FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
				   					credBankData.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
				   					credBankData.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
				   					batchDataHeading.addCell(credBankData);
				   					debtPop = true;
				   					
								}
							
								else{
									PdfPCell nullCell001 = new PdfPCell(new Paragraph(null,FontFactory.getFont(FontFactory.HELVETICA, 8)));
									nullCell001.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
									nullCell001.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
									batchDataHeading.addCell(nullCell001);
								}
									
			   					PdfPCell debBankData = new PdfPCell(new Phrase(creditorName,FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
								debBankData.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
								debBankData.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
								batchDataHeading.addCell(debBankData); 
								
								PdfPCell aDataCount = new PdfPCell(new Phrase(manin1Model.getNrOfTxns().toString(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.NORMAL)));
								aDataCount.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
								aDataCount.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
								batchDataHeading.addCell(aDataCount);	

								PdfPCell bDataCount = new PdfPCell(new Phrase(manin2Model.getNrOfTxns().toString(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.NORMAL)));
								bDataCount.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
								bDataCount.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
								batchDataHeading.addCell(bDataCount);	

								PdfPCell cDataCount = new PdfPCell(new Phrase(manam1Model.getNrOfTxns().toString(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.NORMAL)));
								cDataCount.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
								cDataCount.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
								batchDataHeading.addCell(cDataCount);	

								PdfPCell dDataCount = new PdfPCell(new Phrase(manam2Model.getNrOfTxns().toString(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.NORMAL)));
								dDataCount.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
								dDataCount.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
								batchDataHeading.addCell(dDataCount);	

								PdfPCell eDataCount = new PdfPCell(new Phrase(mancn1Model.getNrOfTxns().toString(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.NORMAL)));
								eDataCount.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
								eDataCount.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
								batchDataHeading.addCell(eDataCount);	

								PdfPCell fDataCount = new PdfPCell(new Phrase(mancn2Model.getNrOfTxns().toString(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.NORMAL)));
								fDataCount.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
								fDataCount.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
								batchDataHeading.addCell(fDataCount);
							}
		   			}
		   			document.add(batchDataHeading);
		   		
		   		}	
			}
			
		}
		
		
   		//POPULATE TOTALS PER DEBTOR BANK/REASON CODE
			PdfPTable totalTable = new PdfPTable(8);
			totalTable.setWidthPercentage(100);
			
			PdfPCell totalHeading = new PdfPCell(new Phrase("TOTALS",FontFactory.getFont(FontFactory.HELVETICA, 9, com.itextpdf.text.Font.BOLD)));
			totalHeading.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
			totalHeading.setBorder(com.itextpdf.text.Rectangle.TOP | com.itextpdf.text.Rectangle.LEFT);
			totalTable.addCell(totalHeading);
			
			
			PdfPCell nullCell001 = new PdfPCell(new Paragraph(null,FontFactory.getFont(FontFactory.HELVETICA, 8)));
			nullCell001.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
			nullCell001.setBorder(com.itextpdf.text.Rectangle.TOP );
			totalTable.addCell(nullCell001);
			
			OutstandingRespSummaryCountModel manin1ModelTotal  = new OutstandingRespSummaryCountModel();
			manin1ModelTotal = (OutstandingRespSummaryCountModel)controller.retrieverRespOutstandForOneDay("MANIN");
			OutstandingRespSummaryCountModel manin2ModelTotal  = new OutstandingRespSummaryCountModel();
				manin2ModelTotal = (OutstandingRespSummaryCountModel)controller.retrieverRespOutstandForTwoDays("MANIN");
				OutstandingRespSummaryCountModel manam1ModelTotal  = new OutstandingRespSummaryCountModel();
				manam1ModelTotal = (OutstandingRespSummaryCountModel)controller.retrieverRespOutstandForOneDay("MANAM");
				OutstandingRespSummaryCountModel manam2ModelTotal  = new OutstandingRespSummaryCountModel();
				manam2ModelTotal = (OutstandingRespSummaryCountModel)controller.retrieverRespOutstandForTwoDays("MANAM");
				OutstandingRespSummaryCountModel mancn1ModelTotal  = new OutstandingRespSummaryCountModel();
				mancn1ModelTotal = (OutstandingRespSummaryCountModel)controller.retrieverRespOutstandForOneDay("MANCN");
				OutstandingRespSummaryCountModel mancn2ModelTotal  = new OutstandingRespSummaryCountModel();
				mancn2ModelTotal = (OutstandingRespSummaryCountModel)controller.retrieverRespOutstandForTwoDays("MANCN");
				
				
			if(manin1ModelTotal.getNrOfDays()== null)
				manin1ModelTotal.setNrOfDays(BigDecimal.ZERO);
				int oneDayManinTotal=manin1ModelTotal.getNrOfDays().intValue();
				
				if(manin2ModelTotal.getNrOfDays()== null)
					manin2ModelTotal.setNrOfDays(BigDecimal.ZERO);
				int twoDayManinTotal=manin2ModelTotal.getNrOfDays().intValue();
				
				if(manam1ModelTotal.getNrOfDays()== null)
					manam1ModelTotal.setNrOfDays(BigDecimal.ZERO);
				int oneDayManamTotal=manam1ModelTotal.getNrOfDays().intValue();
				
				if(manam2ModelTotal.getNrOfDays()== null)
				manam2ModelTotal.setNrOfDays(BigDecimal.ZERO);
				int twoDayManamTotal=manam2ModelTotal.getNrOfDays().intValue();
				
				if(mancn1ModelTotal.getNrOfDays()== null)
					mancn1ModelTotal.setNrOfDays(BigDecimal.ZERO);
				int oneDayMancnTotal=mancn1ModelTotal.getNrOfDays().intValue();
				
				if(mancn2ModelTotal.getNrOfDays()== null)
					mancn2ModelTotal.setNrOfDays(BigDecimal.ZERO);
				int twoDayMancnTotal=mancn2ModelTotal.getNrOfDays().intValue();

				int grandTotal =0;
				grandTotal =oneDayManinTotal +twoDayManinTotal +oneDayManamTotal +twoDayManamTotal +oneDayMancnTotal +twoDayMancnTotal ;

				if(oneDayManinTotal > 0 ||twoDayManinTotal > 0 ||oneDayManamTotal > 0 ||twoDayManamTotal > 0 ||oneDayMancnTotal > 0 ||twoDayMancnTotal > 0 )
				{

					PdfPCell aDataCount = new PdfPCell(new Phrase(manin1ModelTotal.getNrOfDays().toString(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
					aDataCount.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
					aDataCount.setBorder(com.itextpdf.text.Rectangle.TOP | com.itextpdf.text.Rectangle.BOTTOM);
					totalTable.addCell(aDataCount);	

					PdfPCell bDataCount = new PdfPCell(new Phrase(manin2ModelTotal.getNrOfDays().toString(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
					bDataCount.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
					bDataCount.setBorder(com.itextpdf.text.Rectangle.TOP | com.itextpdf.text.Rectangle.BOTTOM);
					totalTable.addCell(bDataCount);	

					PdfPCell cDataCount = new PdfPCell(new Phrase(manam1ModelTotal.getNrOfDays().toString(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
					cDataCount.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
					cDataCount.setBorder(com.itextpdf.text.Rectangle.TOP | com.itextpdf.text.Rectangle.BOTTOM);
					totalTable.addCell(cDataCount);	

					PdfPCell dDataCount = new PdfPCell(new Phrase(manam2ModelTotal.getNrOfDays().toString(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
					dDataCount.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
					dDataCount.setBorder(com.itextpdf.text.Rectangle.TOP | com.itextpdf.text.Rectangle.BOTTOM);
					totalTable.addCell(dDataCount);	

					PdfPCell eDataCount = new PdfPCell(new Phrase(mancn1ModelTotal.getNrOfDays().toString(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
					eDataCount.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
					eDataCount.setBorder(com.itextpdf.text.Rectangle.TOP | com.itextpdf.text.Rectangle.BOTTOM);
					totalTable.addCell(eDataCount);	

					PdfPCell fDataCount = new PdfPCell(new Phrase(mancn2ModelTotal.getNrOfDays().toString(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
					fDataCount.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
					fDataCount.setBorder(com.itextpdf.text.Rectangle.TOP | com.itextpdf.text.Rectangle.BOTTOM|com.itextpdf.text.Rectangle.RIGHT);
					totalTable.addCell(fDataCount);

				}
				document.add(totalTable);	
				
				
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
   			

    	  document.close();
	}
	
	

}
