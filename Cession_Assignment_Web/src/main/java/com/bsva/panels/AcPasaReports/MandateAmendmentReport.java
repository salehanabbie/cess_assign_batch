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

import com.bsva.commons.model.AmendmentCodesModel;
import com.bsva.commons.model.CreditorBankModel;
import com.bsva.commons.model.CustomerParametersModel;
import com.bsva.commons.model.MandateAmendModel;
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
 *
 */

public class MandateAmendmentReport {
	
	public static Logger log=Logger.getLogger(MandateAmendmentReport.class);

	Date currentDate = new Date();
	private String downloaddirectory ="/home/opsjava/Delivery/Cession_Assign/Reports/";
	Controller controller  = new Controller();

	List<CreditorBankModel> creditorBankModelList;
	CreditorBankModel creditorBankModel = new CreditorBankModel();
	int fileSeqNo =000;
	String feedbackMsg = null;

	public String generateReport(String reportNr, String reportname) throws FileNotFoundException, DocumentException
	{
		if(reportNr.equalsIgnoreCase("PSMD06")) 
		{
			try{
				log.info("in the generate report method ");
				generateReportColumns( reportNr,reportname);
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

			feedbackMsg = reportNr +" generated successfully";
		}

		return feedbackMsg;
	}
	
	

	public void generateReportColumns(String reportNo,String reportname ) throws FileNotFoundException, DocumentException
	{
		
		log.info("The report cloumns method ****************");
		DecimalFormat df = new DecimalFormat("### ### ### ### ### ##0.00");
		DateFormat dateTimeFormat = new SimpleDateFormat("yyyyMMdd_HH:mm:ss");
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat fileTimeFormat = new SimpleDateFormat("MM-yyyy");
		DateFormat yearFormat = new SimpleDateFormat("-yyyy");
		Date currentDate = new Date();
        DateFormat endDateFormat = new SimpleDateFormat("ddMMyyyy");
	


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
			    log.info("strFirstDate ==> "+ strFirstDate);
			    log.info("strLastDate ==> "+ strLastDate);
			}
			PdfPCell nullCell001 = new PdfPCell(new Paragraph(null,FontFactory.getFont(FontFactory.HELVETICA, 8)));
			nullCell001.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
			nullCell001.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
			secondheading.addCell(nullCell001);

			
			PdfPCell nullCell02 = new PdfPCell(new Paragraph("REG.NO."+ companyParamModel.getRegistrationNr(), FontFactory.getFont(FontFactory.HELVETICA, 8)));
			nullCell02.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			nullCell02.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
			secondheading.addCell(nullCell02);
			
	         PdfPCell nullCell03 = new PdfPCell(new Paragraph("Process Month: " + month+ " " + year,  FontFactory.getFont(FontFactory.HELVETICA, 8)));
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

		PdfPCell cell1 = new PdfPCell(new Paragraph(reportNo+"-"+reportname, FontFactory.getFont( FontFactory.HELVETICA, 9, com.itextpdf.text.Font.BOLD)));
		cell1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		cell1.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
		thirdHeading.addCell(cell1);

		document.add(thirdHeading);
		document.add(freeline);
		
  		PdfPTable batchDetailsHeading = new PdfPTable(6);
   		batchDetailsHeading.setWidthPercentage(100);
		
		
   		PdfPCell credBank = new PdfPCell(new Phrase("Creditor Bank",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
   		credBank.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		credBank.setUseVariableBorders(true);
		credBank.setBorderColorTop(BaseColor.BLACK);
		credBank.setBorderColorBottom(BaseColor.BLACK);
		credBank.setBorderColorLeft(BaseColor.WHITE);
		credBank.setBorderColorRight(BaseColor.WHITE);
   		batchDetailsHeading.addCell(credBank);
   		
		PdfPCell md16 = new PdfPCell(new Phrase("Request By Customer",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		md16.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		md16.setUseVariableBorders(true);
		md16.setBorderColorTop(BaseColor.BLACK);
		md16.setBorderColorBottom(BaseColor.BLACK);
		md16.setBorderColorLeft(BaseColor.WHITE);
		md16.setBorderColorRight(BaseColor.WHITE);
   		batchDetailsHeading.addCell(md16);

		PdfPCell md17 = new PdfPCell(new Phrase("Cancellation/amendment requested By Initiating Party",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		md17.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		md17.setUseVariableBorders(true);
		md17.setBorderColorTop(BaseColor.BLACK);
		md17.setBorderColorBottom(BaseColor.BLACK);
		md17.setBorderColorLeft(BaseColor.WHITE);
		md17.setBorderColorRight(BaseColor.WHITE);
   		batchDetailsHeading.addCell(md17);
   		
   		PdfPCell md19 = new PdfPCell(new Phrase("Unsuspend a Mandate with changes",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
   		md19.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		md19.setUseVariableBorders(true);
		md19.setBorderColorTop(BaseColor.BLACK);
		md19.setBorderColorBottom(BaseColor.BLACK);
		md19.setBorderColorLeft(BaseColor.WHITE);
		md19.setBorderColorRight(BaseColor.WHITE);
   		batchDetailsHeading.addCell(md19);
   		
   		PdfPCell md20 = new PdfPCell(new Phrase("Unsuspend an unchanged Mandate",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
   		md20.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
   		md20.setUseVariableBorders(true);
   		md20.setBorderColorTop(BaseColor.BLACK);
   		md20.setBorderColorBottom(BaseColor.BLACK);
   		md20.setBorderColorLeft(BaseColor.WHITE);
   		md20.setBorderColorRight(BaseColor.WHITE);
   		batchDetailsHeading.addCell(md20);
   		
   		
   		PdfPCell ms02 = new PdfPCell(new Phrase("Reason has not been specified by Customer (Customer rejects Mandate)",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
   		ms02.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
   		ms02.setUseVariableBorders(true);
   		ms02.setBorderColorTop(BaseColor.BLACK);
   		ms02.setBorderColorBottom(BaseColor.BLACK);
   		ms02.setBorderColorLeft(BaseColor.WHITE);
   		ms02.setBorderColorRight(BaseColor.WHITE);
   		batchDetailsHeading.addCell(ms02);
   		
   		
   		document.add(batchDetailsHeading);
   		document.add(freeline);
   		
 
   		
   		
   		
   		//1. Retrieve all creditor banks in a list
   		//2. Loop thru bank list and set member number
   		//3. run each query per reason code per member number
   		creditorBankModelList = new ArrayList<CreditorBankModel>();
   		creditorBankModelList = (List<CreditorBankModel>) controller.retrieveActiveCreditorBank();
		log.debug("creditorBankModelList------->"+creditorBankModelList);
		
		if(creditorBankModelList != null && creditorBankModelList.size() > 0)
		{
			for(CreditorBankModel creditorBankModel : creditorBankModelList)
			{
				PdfPTable batchDataHeading = new PdfPTable(6);
		   		batchDataHeading.setWidthPercentage(100);
				log.debug("creditorBankModel------->"+creditorBankModel);
		   		String creditorName= creditorBankModel.getMemberName();
		   		log.info("creditorName------->"+creditorBankModel.getMemberName());
		   		
		   		String creditorMember = creditorBankModel.getMemberNo();
		   		log.debug("creditorMember------->"+creditorBankModel.getMemberNo());
		   		
				PdfPCell credBankData = new PdfPCell(new Phrase(creditorName,FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
				credBankData.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
				credBankData.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
				batchDataHeading.addCell(credBankData);	
				
				
				
				//Query to retrieve Count for MD16
				MandateAmendModel mandateAmend16Model= new MandateAmendModel();
				
				mandateAmend16Model = (MandateAmendModel)controller.retrieveAmend16Data(creditorMember,"MD16",strFirstDate,strLastDate);
		  		log.debug("mandateAmend16Model1------->"+mandateAmend16Model);
				
		  		if(mandateAmend16Model.getAmendReasonCodeCount()== null)
					mandateAmend16Model.setAmendReasonCodeCount(new BigDecimal(0));
				PdfPCell md16DataCount = new PdfPCell(new Phrase(mandateAmend16Model.getAmendReasonCodeCount().toString(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.NORMAL)));
				md16DataCount.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
				md16DataCount.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
				batchDataHeading.addCell(md16DataCount);	

				//Query to retrieve Count for MD17
				mandateAmend16Model= new MandateAmendModel();
				mandateAmend16Model = (MandateAmendModel)controller.retrieveAmend16Data(creditorMember,"MD17",strFirstDate,strLastDate);
				
				if(mandateAmend16Model.getAmendReasonCodeCount()== null)
					mandateAmend16Model.setAmendReasonCodeCount(new BigDecimal(0));
				PdfPCell md17DataCount = new PdfPCell(new Phrase(mandateAmend16Model.getAmendReasonCodeCount().toString(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.NORMAL)));
				md17DataCount.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
				md17DataCount.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
				batchDataHeading.addCell(md17DataCount);	
				
				//Query to retrieve Count for MD19
				mandateAmend16Model= new MandateAmendModel();
				mandateAmend16Model = (MandateAmendModel)controller.retrieveAmend16Data(creditorMember,"MD19",strFirstDate,strLastDate);
				
				if(mandateAmend16Model.getAmendReasonCodeCount()== null)
					mandateAmend16Model.setAmendReasonCodeCount(new BigDecimal(0));
				PdfPCell md19DataCount = new PdfPCell(new Phrase(mandateAmend16Model.getAmendReasonCodeCount().toString(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.NORMAL)));
				md19DataCount.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
				md19DataCount.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
				batchDataHeading.addCell(md19DataCount);	
				
				//Query to retrieve Count for MD20
				mandateAmend16Model= new MandateAmendModel();
				mandateAmend16Model = (MandateAmendModel)controller.retrieveAmend16Data(creditorMember,"MD20",strFirstDate,strLastDate);
				
				if(mandateAmend16Model.getAmendReasonCodeCount()== null)
					mandateAmend16Model.setAmendReasonCodeCount(new BigDecimal(0));
				PdfPCell md20DataCount = new PdfPCell(new Phrase(mandateAmend16Model.getAmendReasonCodeCount().toString(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.NORMAL)));
				md20DataCount.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
				md20DataCount.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
				batchDataHeading.addCell(md20DataCount);	
				
				
				//Query to retrieve Count for MS02
				mandateAmend16Model= new MandateAmendModel();
				mandateAmend16Model = (MandateAmendModel)controller.retrieveAmend16Data(creditorMember,"MS02",strFirstDate,strLastDate);
				
				if(mandateAmend16Model.getAmendReasonCodeCount()== null)
					mandateAmend16Model.setAmendReasonCodeCount(new BigDecimal(0));
				PdfPCell mS02DataCount = new PdfPCell(new Phrase(mandateAmend16Model.getAmendReasonCodeCount().toString(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.NORMAL)));
				mS02DataCount.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
				mS02DataCount.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
				batchDataHeading.addCell(mS02DataCount);	

				
				document.add(batchDataHeading);

			}
		}

   		
		PdfPTable batchTotalHeading = new PdfPTable(6);
  		batchTotalHeading.setWidthPercentage(100);
   		
   		
   		PdfPCell totals = new PdfPCell(new Phrase("TOTALS",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
   		totals.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
   		totals.setBorder(com.itextpdf.text.Rectangle.LEFT|com.itextpdf.text.Rectangle.TOP);
   		batchTotalHeading.addCell(totals);
   		
   		
   		MandateAmendModel mandateAmend16Model= new MandateAmendModel();
   		
   		
   		//Query to retrieve total Count for MD16
		mandateAmend16Model = (MandateAmendModel)controller.retrieveReasonCodeTotal("MD16",strFirstDate,strLastDate);
  		log.debug("mandateAmend16Model1------->"+mandateAmend16Model);
  		
  		if(mandateAmend16Model.getAmendReasonCodeCount()== null)
			mandateAmend16Model.setAmendReasonCodeCount(new BigDecimal(0));
  		int md16Count = mandateAmend16Model.getAmendReasonCodeCount().intValue();
  		
   		PdfPCell totalMd16Count = new PdfPCell(new Phrase(mandateAmend16Model.getAmendReasonCodeCount().toString(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
   		totalMd16Count.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
   		totalMd16Count.setBorder(com.itextpdf.text.Rectangle.TOP );
   		batchTotalHeading.addCell(totalMd16Count);
   		
   		
   	
   		//Query to retrieve Count for MD17
		mandateAmend16Model= new MandateAmendModel();
		mandateAmend16Model = (MandateAmendModel)controller.retrieveReasonCodeTotal("MD17",strFirstDate,strLastDate);
		
		if(mandateAmend16Model.getAmendReasonCodeCount()== null)
			mandateAmend16Model.setAmendReasonCodeCount(new BigDecimal(0));
		int md17Count = mandateAmend16Model.getAmendReasonCodeCount().intValue();
		
   		PdfPCell totalMd17Count = new PdfPCell(new Phrase(mandateAmend16Model.getAmendReasonCodeCount().toString(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
   		totalMd17Count.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
   		totalMd17Count.setBorder(com.itextpdf.text.Rectangle.TOP | com.itextpdf.text.Rectangle.BOTTOM);
   		batchTotalHeading.addCell(totalMd17Count);
   		
   	
   		
   		//Query to retrieve Count for MD19
		mandateAmend16Model= new MandateAmendModel();
		mandateAmend16Model = (MandateAmendModel)controller.retrieveReasonCodeTotal("MD19",strFirstDate,strLastDate);
		
		if(mandateAmend16Model.getAmendReasonCodeCount()== null)
			mandateAmend16Model.setAmendReasonCodeCount(new BigDecimal(0));
		int md19Count = mandateAmend16Model.getAmendReasonCodeCount().intValue();
		
   		PdfPCell totalMd19Count = new PdfPCell(new Phrase(mandateAmend16Model.getAmendReasonCodeCount().toString(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
   		totalMd19Count.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
   		totalMd19Count.setBorder(com.itextpdf.text.Rectangle.TOP | com.itextpdf.text.Rectangle.BOTTOM);
   		batchTotalHeading.addCell(totalMd19Count);
   		
   		//Query to retrieve Count for MD20
		mandateAmend16Model= new MandateAmendModel();
		mandateAmend16Model = (MandateAmendModel)controller.retrieveReasonCodeTotal("MD20",strFirstDate,strLastDate);
		
		if(mandateAmend16Model.getAmendReasonCodeCount()== null)
			mandateAmend16Model.setAmendReasonCodeCount(new BigDecimal(0));
		int md20Count = mandateAmend16Model.getAmendReasonCodeCount().intValue();
		
   		PdfPCell totalMd20Count = new PdfPCell(new Phrase(mandateAmend16Model.getAmendReasonCodeCount().toString(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
   		totalMd20Count.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
   		totalMd20Count.setBorder(com.itextpdf.text.Rectangle.TOP | com.itextpdf.text.Rectangle.BOTTOM);
   		batchTotalHeading.addCell(totalMd20Count);
   		
   		
   		//Query to retrieve Count for MS02
		mandateAmend16Model= new MandateAmendModel();
		mandateAmend16Model = (MandateAmendModel)controller.retrieveReasonCodeTotal("MS02",strFirstDate,strLastDate);

		
		if(mandateAmend16Model.getAmendReasonCodeCount()== null)
			mandateAmend16Model.setAmendReasonCodeCount(new BigDecimal(0));
		int ms02Count = mandateAmend16Model.getAmendReasonCodeCount().intValue();
		
   		PdfPCell totalMS02Count = new PdfPCell(new Phrase(mandateAmend16Model.getAmendReasonCodeCount().toString(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
   		totalMS02Count.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
   		totalMS02Count.setBorder(com.itextpdf.text.Rectangle.TOP | com.itextpdf.text.Rectangle.BOTTOM |com.itextpdf.text.Rectangle.RIGHT);
   		batchTotalHeading.addCell(totalMS02Count);
   		
		
   		document.add(batchTotalHeading);
   	
		PdfPTable batchGrandTotalHeading = new PdfPTable(6);
		batchGrandTotalHeading.setWidthPercentage(100);
		
		int grandTotal= md16Count+ md17Count +md19Count +md20Count + ms02Count;
   		
   		PdfPCell grandTotals =new PdfPCell(new Phrase("GRAND TOTAL          "+String.valueOf(grandTotal),FontFactory.getFont(FontFactory.HELVETICA, 9, com.itextpdf.text.Font.BOLD)));
   		grandTotals.setColspan(2);
   		grandTotals.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
   		grandTotals.setBorder(com.itextpdf.text.Rectangle.BOTTOM | com.itextpdf.text.Rectangle.LEFT |com.itextpdf.text.Rectangle.RIGHT);
   		batchGrandTotalHeading.addCell(grandTotals);
   		
   		PdfPCell nullCell001 = new PdfPCell(new Paragraph(null,FontFactory.getFont(FontFactory.HELVETICA, 8)));
		nullCell001.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		nullCell001.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
		batchGrandTotalHeading.addCell(nullCell001);
		
		PdfPCell nullCell002 = new PdfPCell(new Paragraph(null,FontFactory.getFont(FontFactory.HELVETICA, 8)));
		nullCell002.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		nullCell002.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
		batchGrandTotalHeading.addCell(nullCell002);
		
		PdfPCell nullCell003 = new PdfPCell(new Paragraph(null,FontFactory.getFont(FontFactory.HELVETICA, 8)));
		nullCell003.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		nullCell003.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
		batchGrandTotalHeading.addCell(nullCell003);
		
		PdfPCell nullCell004 = new PdfPCell(new Paragraph(null,FontFactory.getFont(FontFactory.HELVETICA, 8)));
		nullCell004.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		nullCell004.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
		batchGrandTotalHeading.addCell(nullCell004);
		
		PdfPCell nullCell005 = new PdfPCell(new Paragraph(null,FontFactory.getFont(FontFactory.HELVETICA, 8)));
		nullCell005.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		nullCell005.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
		batchGrandTotalHeading.addCell(nullCell005);
		
		PdfPCell nullCell006 = new PdfPCell(new Paragraph(null,FontFactory.getFont(FontFactory.HELVETICA, 8)));
		nullCell006.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		nullCell006.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
		batchGrandTotalHeading.addCell(nullCell006);

   		


   		document.add(batchGrandTotalHeading);
   		
   	
    	document.close();
		
	}
	
	
	

	


	

}
