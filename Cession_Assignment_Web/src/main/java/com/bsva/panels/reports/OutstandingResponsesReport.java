package com.bsva.panels.reports;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.handler.resource.ResourceStreamRequestHandler;
import org.apache.wicket.util.file.File;
import org.apache.wicket.util.resource.FileResourceStream;

import com.bsva.commons.model.CustomerParametersModel;
import com.bsva.commons.model.OutstandingResponsesDebtorModel;
import com.bsva.commons.model.OutstandingResponsesModel;
import com.bsva.commons.model.ReportsNamesModel;
import com.bsva.commons.model.SysctrlCompParamModel;
import com.bsva.commons.model.SystemParameterModel;
import com.bsva.controller.Controller;
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
 *
 */
public class OutstandingResponsesReport 

{

	String reportName, reportNr;
	
	private String downloaddirectory ="/home/opsjava/Delivery/Mandates/Reports/";
	public static Logger log=Logger.getLogger(OutstandingResponsesReport.class);
	Controller controller = new Controller();
	
	List<CustomerParametersModel> custParamsModelList;
	List<OutstandingResponsesDebtorModel> outstandingRespListD;
	CustomerParametersModel customerParametersModel = new CustomerParametersModel();
	
	public OutstandingResponsesReport(String reportNr, String reportName)
	{
		this.reportNr = reportNr;
		this.reportName = reportName;
		//this.creditorName = creditorName;
	}
	
	
		
/*public void generateOustandingDebotrReport()  
{
	custParamsModelList = new ArrayList<CustomerParametersModel>();
	custParamsModelList = (List<CustomerParametersModel>) controller.retrieveCustomerParameters();
	
	
	if(custParamsModelList.size() > 0)
	{
	//1. Loop though all members
	for (CustomerParametersModel customerParametersModel : custParamsModelList) 
	{
		//Retrieve outstanding responses per member
		List<OutstandingResponsesDebtorModel> outstRespListDebtorList;
		//Batch Report
		if(reportNr.equalsIgnoreCase("MR006"))
			outstRespListDebtorList = (List<OutstandingResponsesDebtorModel>) controller.retrieveOutstandingResponsesD(customerParametersModel.getInstId(), "N");
		else
			outstRespListDebtorList = (List<OutstandingResponsesDebtorModel>) controller.retrieveOutstandingResponsesD(customerParametersModel.getInstId(), "Y");
		
		if(outstRespListDebtorList != null && outstRespListDebtorList.size() > 0)
		{
			//3. Generate report per member.
			try
			{
				generateDebtorReportDetail(customerParametersModel.getInstId(), outstRespListDebtorList);
			}
			catch(DocumentException | FileNotFoundException ex)
			{
				log.error("Error generating outstanding responses report "+ ex.getMessage());
				ex.printStackTrace();
			}
		}
	}
	}	
	
}
*/



	public void generateOustandingCreditorReport()  
	{
		//String memberNo = "210003";
		ReportsNamesModel reportsModel =new ReportsNamesModel();
		
		
		
		custParamsModelList = new ArrayList<CustomerParametersModel>();
		custParamsModelList = (List<CustomerParametersModel>) controller.retrieveCustomerParameters();
		
		outstandingRespListD = (List<OutstandingResponsesDebtorModel>)controller.retrieveOutstandingResponsesD(customerParametersModel.getInstId(),"N");
	
		if(custParamsModelList.size() > 0)
		{
		//1. Loop though all members
		for (CustomerParametersModel customerParametersModel : custParamsModelList) 
		{
			//Retrieve outstanding responses per member
			List<OutstandingResponsesModel> outstandingRespList;
			//Batch Report
			if(reportNr.equalsIgnoreCase("MR006"))
				outstandingRespList = (List<OutstandingResponsesModel>) controller.retrieveOutstandingResponses(customerParametersModel.getInstId(), "N");
			else
				outstandingRespList = (List<OutstandingResponsesModel>) controller.retrieveOutstandingResponses(customerParametersModel.getInstId(), "Y");
			
			if(outstandingRespList != null && outstandingRespList.size() > 0)
			{
				//3. Generate report per member.
				try
				{
					generateCreditorReportDetail(customerParametersModel.getInstId(), outstandingRespList);
				}
				catch(DocumentException | FileNotFoundException ex)
				{
					log.error("Error generating outstanding responses report "+ ex.getMessage());
					ex.printStackTrace();
				}
			}
		}
			
		
	}
	}
	
	

	public void generateCreditorReportDetail(String memberId, List<OutstandingResponsesModel> outstRespList) throws DocumentException, FileNotFoundException
	{
		
	       DecimalFormat df = new DecimalFormat("### ### ### ### ### ##0.00");
           DateFormat dateTimeFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
           DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
           Date currentDate = new Date();

           
           int pageNo = 1;

           String files = downloaddirectory +reportNr+reportName+memberId+dateFormat.format(currentDate).toString() + ".pdf";

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
		systemParameterModel = (SystemParameterModel) controller.retrieveWebActiveSysParameter(); 
       
           
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

   			PdfPCell processDateCell = new PdfPCell(new Paragraph("Process Date: "+dateFormat.format(systemParameterModel.getProcessDate()),FontFactory.getFont(FontFactory.HELVETICA, 8)));
   			processDateCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
   			processDateCell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
   			secondheading.addCell(processDateCell);

   			PdfPCell nullCell02 = new PdfPCell(new Paragraph("REG.NO."+ companyParamModel.getRegistrationNr(), FontFactory.getFont(FontFactory.HELVETICA, 8)));
   			nullCell02.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
   			nullCell02.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
   			secondheading.addCell(nullCell02);

   			PdfPCell nullCell03 = new PdfPCell(new Paragraph(null,FontFactory.getFont(FontFactory.HELVETICA, 8)));
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

           PdfPCell cell1 = new PdfPCell(new Paragraph(reportNr+ "-"+ reportName, FontFactory.getFont( FontFactory.HELVETICA, 9, com.itextpdf.text.Font.BOLD)));
           cell1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
           cell1.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
           thirdHeading.addCell(cell1);

           document.add(thirdHeading);
           document.add(freeline);

       	// Batch Report Detail
   		PdfPTable batchHeading = new PdfPTable(1);
   		batchHeading.setWidthPercentage(100);
   		
   		String batchMemTitle;
   		if(reportNr.equalsIgnoreCase("MR006"))
   			 batchMemTitle = "Batch Detail for ";
   		else
   			batchMemTitle = "Online Detail for ";

   		PdfPCell batchMemberTitle = new PdfPCell(new Paragraph(batchMemTitle+memberId,	FontFactory.getFont(FontFactory.HELVETICA, 9,com.itextpdf.text.Font.BOLD)));
   		batchMemberTitle.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
   		batchMemberTitle.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
   		batchHeading.addCell(batchMemberTitle);

   		document.add(batchHeading);
   		document.add(freeline);

   		PdfPTable batchDetailsHeading = new PdfPTable(1);
   		batchDetailsHeading.setWidthPercentage(100);

        PdfPCell CreditorCell = new PdfPCell(new Phrase("Creditor Bank",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.UNDERLINE)));
        CreditorCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
        CreditorCell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
        batchDetailsHeading.addCell(CreditorCell);
        document.add(batchDetailsHeading);
        document.add(freeline);                    
                           
        PdfPTable batchDetailsSubHeadings = new PdfPTable(6);
        batchDetailsSubHeadings.setWidthPercentage(100);

   		PdfPCell mandateReqTransIdCell = new PdfPCell(new Phrase("Mandate Requested Transaction Id",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.UNDERLINE)));
   		mandateReqTransIdCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
   		mandateReqTransIdCell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
   		batchDetailsSubHeadings.addCell(mandateReqTransIdCell);

   		PdfPCell manReqIdCell = new PdfPCell(new Phrase("Mandate Requested Id", FontFactory.getFont(FontFactory.HELVETICA,8, com.itextpdf.text.Font.UNDERLINE)));
   		manReqIdCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
   		manReqIdCell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
   		batchDetailsSubHeadings.addCell(manReqIdCell);
   		
   		PdfPCell manRefNrCell = new PdfPCell(new Phrase("Mandate Reference Number", FontFactory.getFont(FontFactory.HELVETICA,8, com.itextpdf.text.Font.UNDERLINE)));
   		manRefNrCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
   		manRefNrCell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
   		batchDetailsSubHeadings.addCell(manRefNrCell);
   		
   		
   		PdfPCell serviceIdCell = new PdfPCell(new Phrase("Service Id",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.UNDERLINE)));
   		serviceIdCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
   		serviceIdCell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
   		batchDetailsSubHeadings.addCell(serviceIdCell);
   		  		
   		  		
   		PdfPCell crMemId = new PdfPCell(new Phrase("Creditor Bank",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.UNDERLINE)));
   		crMemId.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
   		crMemId.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
   		batchDetailsSubHeadings.addCell(crMemId);
   		

   		PdfPCell fileNameCell = new PdfPCell(new Phrase("File Name",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.UNDERLINE)));
   		fileNameCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
   		fileNameCell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
   		batchDetailsSubHeadings.addCell(fileNameCell);
   				
   		document.add(batchDetailsSubHeadings);
   		document.add(freeline);

   		//Creditor Bank Information
   		if(outstRespList.size() > 0)
   		{
   			for (OutstandingResponsesModel outRespModel : outstRespList) 
   			{
   				PdfPTable batchDetails = new PdfPTable(6);
   				batchDetails.setWidthPercentage(100);
   	   			
   				
   				PdfPCell manReqTransIdCell = new PdfPCell(new Phrase(outRespModel.getMandateReqTranId(),FontFactory.getFont(FontFactory.HELVETICA,8, com.itextpdf.text.Font.NORMAL)));
   				manReqTransIdCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
   				manReqTransIdCell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
   	   	   		batchDetails.addCell(manReqTransIdCell);

   	   	   		PdfPCell manReqIdDataCell = new PdfPCell(new Phrase(outRespModel.getMandateReqId(),FontFactory.getFont(FontFactory.HELVETICA,8, com.itextpdf.text.Font.NORMAL)));
   	   	   		manReqIdDataCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
   	   	   		manReqIdDataCell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
   	   	   		batchDetails.addCell(manReqIdDataCell);
   	   	   		
   	   	   		PdfPCell manRefNoCell = new PdfPCell(new Phrase(outRespModel.getMandateRefNr(),FontFactory.getFont(FontFactory.HELVETICA,8,com.itextpdf.text.Font.NORMAL)));
   	   	   		manRefNoCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
   	   	   		manRefNoCell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
	   	   		batchDetails.addCell(manRefNoCell);
   	   	   		
   	   	   		
   	   	   		PdfPCell serviceIdDataCell = new PdfPCell(new Phrase(outRespModel.getServiceId(),FontFactory.getFont(FontFactory.HELVETICA,8,com.itextpdf.text.Font.NORMAL)));
   	   	   		serviceIdDataCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
   	   	   		serviceIdDataCell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
	   	   		batchDetails.addCell(serviceIdDataCell);
	   	   		
	   	   		PdfPCell credMemberDataCell = new PdfPCell(new Phrase(outRespModel.getCreditorMemberNo(),FontFactory.getFont(FontFactory.HELVETICA,8, com.itextpdf.text.Font.NORMAL)));
	   	   		credMemberDataCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
	   	   		credMemberDataCell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
	   	   		batchDetails.addCell(credMemberDataCell);
	   	   		
	   	   		PdfPCell fileNameDataCell = new PdfPCell(new Phrase(outRespModel.getFileName(),FontFactory.getFont(FontFactory.HELVETICA,8, com.itextpdf.text.Font.NORMAL)));
	   	   		fileNameDataCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
	   	   		fileNameDataCell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
	   	   		batchDetails.addCell(fileNameDataCell);
   	   	   		
   	   	   		
//   	   	   		//Calculate Totals of Mandates.
//   	   	   		 totalCredNoOfMan = totalCredNoOfMan + crSummModel.getNrofMandates();
//   	   	   		 totalCredValue = totalCredValue + crSummModel.getValofMandates();
   	   	   		 
   	   	   		document.add(batchDetails);
			}
   			document.add(freeline);
   			
   		}
	
   	  	PdfPTable DebtorHeading = new PdfPTable(1);
   	   	DebtorHeading.setWidthPercentage(100);
   	   		
	   		
	    PdfPCell DebtorCell = new PdfPCell(new Phrase("Debtor Bank",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.UNDERLINE)));
	    DebtorCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
	    DebtorCell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
	    DebtorHeading.addCell(DebtorCell);
	    document.add(DebtorHeading);
	    document.add(freeline);                    
	                       
	    PdfPTable DebtorHeadingSubHeadings = new PdfPTable(6);
	    DebtorHeadingSubHeadings.setWidthPercentage(100);

		PdfPCell debtorMandateReqTransIdCell = new PdfPCell(new Phrase("Mandate Requested Transaction Id",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.UNDERLINE)));
		debtorMandateReqTransIdCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		debtorMandateReqTransIdCell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
		DebtorHeadingSubHeadings.addCell(debtorMandateReqTransIdCell);
		
		PdfPCell debtormanReqIdCell = new PdfPCell(new Phrase("Mandate Requested Id", FontFactory.getFont(FontFactory.HELVETICA,8, com.itextpdf.text.Font.UNDERLINE)));
		debtormanReqIdCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		debtormanReqIdCell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
		DebtorHeadingSubHeadings.addCell(debtormanReqIdCell);
			
		PdfPCell debtormanRefNrCell = new PdfPCell(new Phrase("Mandate Reference Number", FontFactory.getFont(FontFactory.HELVETICA,8, com.itextpdf.text.Font.UNDERLINE)));
		debtormanRefNrCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		debtormanRefNrCell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
		DebtorHeadingSubHeadings.addCell(debtormanRefNrCell);
			
			
		PdfPCell debtorserviceIdCell = new PdfPCell(new Phrase("Service Id",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.UNDERLINE)));
		debtorserviceIdCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		debtorserviceIdCell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
		DebtorHeadingSubHeadings.addCell(debtorserviceIdCell);
	   		
		PdfPCell debtorMemId = new PdfPCell(new Phrase("Debtor Bank",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.UNDERLINE)));
		debtorMemId.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		debtorMemId.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
		DebtorHeadingSubHeadings.addCell(debtorMemId);
			
		PdfPCell debtorfileNameCell = new PdfPCell(new Phrase("File Name",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.UNDERLINE)));
		debtorfileNameCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		debtorfileNameCell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
		DebtorHeadingSubHeadings.addCell(debtorfileNameCell);	
	   		
		document.add(DebtorHeadingSubHeadings);
		document.add(freeline);
	   		
	   		//Debtor Bank Information
		if(outstandingRespListD.size() > 0)
			{
				for (OutstandingResponsesDebtorModel outRespDebtorModel : outstandingRespListD) 
				{
	                         
	       PdfPTable BATCHDetails = new PdfPTable(6);
	       BATCHDetails.setWidthPercentage(100);
	       
	            PdfPCell DebtormanReqTransIdCell = new PdfPCell(new Phrase(outRespDebtorModel.getMandateReqTranId(),FontFactory.getFont(FontFactory.HELVETICA,8, com.itextpdf.text.Font.NORMAL)));
	            DebtormanReqTransIdCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
	            DebtormanReqTransIdCell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
	            BATCHDetails.addCell(DebtormanReqTransIdCell);

	 	   		PdfPCell DebtormanReqIdDataCell = new PdfPCell(new Phrase(outRespDebtorModel.getMandateReqId(),FontFactory.getFont(FontFactory.HELVETICA,8, com.itextpdf.text.Font.NORMAL)));
	 	     	DebtormanReqIdDataCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
	 	        DebtormanReqIdDataCell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
	 	     	BATCHDetails.addCell(DebtormanReqIdDataCell);
	 	   		
	 	   		PdfPCell DebtormanRefNoCell = new PdfPCell(new Phrase(outRespDebtorModel.getMandateRefNr(),FontFactory.getFont(FontFactory.HELVETICA,8, com.itextpdf.text.Font.NORMAL)));
	 	   	    DebtormanRefNoCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
	 	        DebtormanRefNoCell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
	 	   	    BATCHDetails.addCell(DebtormanRefNoCell);
	 	   		
	 	   		
	 	   		PdfPCell DebtorserviceIdDataCell = new PdfPCell(new Phrase(outRespDebtorModel.getServiceId(),FontFactory.getFont(FontFactory.HELVETICA,8, com.itextpdf.text.Font.NORMAL)));
	 	      	DebtorserviceIdDataCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
	 	        DebtorserviceIdDataCell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
	 	   	    BATCHDetails.addCell(DebtorserviceIdDataCell);
	 	   	    
	 	   	    PdfPCell DebtorMemberDataCell = new PdfPCell(new Phrase(outRespDebtorModel.getDebtorMemberNo(),FontFactory.getFont(FontFactory.HELVETICA,8, com.itextpdf.text.Font.NORMAL)));
	 	   	    DebtorMemberDataCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
	 	   	    DebtorMemberDataCell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
		   		BATCHDetails.addCell(DebtorMemberDataCell);
		   		
		   		PdfPCell DebtorfileNameDataCell = new PdfPCell(new Phrase(outRespDebtorModel.getFileName(),FontFactory.getFont(FontFactory.HELVETICA,8, com.itextpdf.text.Font.NORMAL)));
		   		DebtorfileNameDataCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		   		DebtorfileNameDataCell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
		   		BATCHDetails.addCell(DebtorfileNameDataCell);
	 	   	    
		   		document.add(BATCHDetails);
					}
		   			document.add(freeline);
		   		}
		   		
	   		
	   		/*else
	   		{
	   			
	   			System.out.println("THE OUTSTANDING RESPONSE LIST IS EMPTY!!!!!!!!");
	   		}
	   		
	   		*/
	  
		  document.close();	
		}
}
