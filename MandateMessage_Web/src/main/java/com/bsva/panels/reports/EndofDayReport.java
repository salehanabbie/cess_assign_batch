package com.bsva.panels.reports;

import java.io.File;
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
import org.apache.wicket.util.resource.FileResourceStream;

import com.bsva.commons.model.EndOfDayReportCommonsModel;
import com.bsva.commons.model.SysctrlCompParamModel;
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
 * @author ElelwaniR
 */
public class EndofDayReport 

{
	public static Logger log=Logger.getLogger(EndofDayReport.class);
	Date currentDate = new Date();
	private String downloaddirectory ="/home/jboss/Mandates/Reports/";
	private String serviceId = "MANIN";
	Controller controller = new Controller ();
	
	
	List<EndOfDayReportCommonsModel> endofDayCommonsModelList = new ArrayList<EndOfDayReportCommonsModel>();
	List<EndOfDayReportCommonsModel> sumIncomingMsgsList = new ArrayList<EndOfDayReportCommonsModel>();
	List<EndOfDayReportCommonsModel> sumOutGoingingMsgsList = new ArrayList<EndOfDayReportCommonsModel>();
	List<EndOfDayReportCommonsModel>retrieveBalanceCarriedForwardList= new ArrayList<EndOfDayReportCommonsModel>();
	
	public void generateReport(String reportNr, String reportname) throws FileNotFoundException, DocumentException
	{
	    log.info("in the generate report method ");
	    retrieveBalanceBroughtForward();
	    sumIncomingMessagesPerServiceId();
	    sumOutGoingMessagesPerServiceID();
	    retrieveBalanceCarried();
		generateReportColumns( reportNr,reportname);
	}
	public void generateReportColumns(String reportNo,String reportname ) throws FileNotFoundException, DocumentException
	
	{
		
		DecimalFormat df = new DecimalFormat("### ### ### ### ### ##0.00");
        DateFormat dateTimeFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate = new Date();

        
        int pageNo = 1;
        
        String files = downloaddirectory +reportNo+reportname+ dateFormat.format(currentDate).toString() + ".pdf";
        
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

          PdfPCell cell1 = new PdfPCell(new Paragraph(reportNo+ "-"+ reportname, FontFactory.getFont( FontFactory.HELVETICA, 9, com.itextpdf.text.Font.BOLD)));
          cell1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
          cell1.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
          thirdHeading.addCell(cell1);

          document.add(thirdHeading);
          document.add(freeline);
          
          
          //Mandate initiation requests details.
          
          
          PdfPTable initiationDetailsHeading = new PdfPTable(1);
          initiationDetailsHeading.setWidthPercentage(100);

          
      	  PdfPCell initiationSubHeading  = new PdfPCell(new Paragraph("Mandate Initiation Requests",FontFactory.getFont(FontFactory.HELVETICA, 9,com.itextpdf.text.Font.BOLD)));
      	  initiationSubHeading.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
      	  initiationSubHeading.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
  		  initiationDetailsHeading.addCell(initiationSubHeading);
     	  document.add(initiationDetailsHeading);
	      document.add(freeline);
	    
          
	      // The balance details for mandate initiation
	      
	      PdfPTable intiationDetails = new PdfPTable(3);
	      intiationDetails.setWidthPercentage(100);

  		PdfPCell cel1 = new PdfPCell(new Phrase("", FontFactory.getFont(	FontFactory.HELVETICA, 8, com.itextpdf.text.Font.NORMAL)));
  		cel1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
  		cel1.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
  		intiationDetails.addCell(cel1);
  		
  		
  		
        PdfPCell volumeDetails = new PdfPCell(new Phrase("Volume", FontFactory.getFont(	FontFactory.HELVETICA, 8, com.itextpdf.text.Font.NORMAL)));
        volumeDetails.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
        volumeDetails.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
  		intiationDetails.addCell(volumeDetails);
  		
  		PdfPCell cel3 = new PdfPCell(new Phrase("", FontFactory.getFont(	FontFactory.HELVETICA, 8, com.itextpdf.text.Font.NORMAL)));
  		cel3.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
  		cel3.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
  		intiationDetails.addCell(cel3);
  		document.add(intiationDetails);
  		document.add(freeline);
  		
  		
  		
  		PdfPTable balanceBroughtForwardDetails = new PdfPTable(3);
  		balanceBroughtForwardDetails.setWidthPercentage(100);
  		
  		PdfPCell balanceBroughtForward = new PdfPCell(new Phrase ("Balance brought forward",FontFactory.getFont(FontFactory.HELVETICA, 8, com.itextpdf.text.Font.NORMAL)));
  		balanceBroughtForward.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
  		balanceBroughtForward.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
  		balanceBroughtForwardDetails.addCell(balanceBroughtForward);
  	
  		
  	   	if (endofDayCommonsModelList.size()>0)
     	{
     		
     	for (EndOfDayReportCommonsModel localModel :endofDayCommonsModelList)
     	{
  		
     		if ( localModel.getServiceID()!=null &&localModel.getServiceID().equalsIgnoreCase("MANIN"));
				
     			PdfPCell balancebrghtforwd = new PdfPCell(new Phrase (String.valueOf(localModel.getBalanceBrghtForward()),FontFactory.getFont(FontFactory.HELVETICA, 8, com.itextpdf.text.Font.NORMAL)));
     			balancebrghtforwd.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
     			balancebrghtforwd.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
     			balanceBroughtForwardDetails.addCell(balancebrghtforwd);
     		}
     	}
  	   	
     
  	   	
  	   	
  		PdfPCell initiationEmptyCell = new PdfPCell(new Phrase ("",FontFactory.getFont(FontFactory.HELVETICA, 8, com.itextpdf.text.Font.NORMAL)));
  		initiationEmptyCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
  		initiationEmptyCell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
  		balanceBroughtForwardDetails.addCell(initiationEmptyCell);
  	
  	   	
  		document.add(balanceBroughtForwardDetails);
/*     	
	
  		document.add(freeline);*/
  		
  		
  		
  		PdfPTable IncomingDetails = new PdfPTable(3);
  		IncomingDetails.setWidthPercentage(100);
  		
  		PdfPCell incoming = new PdfPCell(new Phrase ("Incoming",FontFactory.getFont(FontFactory.HELVETICA, 8, com.itextpdf.text.Font.NORMAL)));
  		incoming.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
  		incoming.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
  		IncomingDetails.addCell(incoming);
  	
  		
  		if (sumIncomingMsgsList.size()>0)
  		{
  			for (EndOfDayReportCommonsModel  localModelIncoming: sumIncomingMsgsList)
  			{
  				if ( localModelIncoming.getServiceID()!=null &&localModelIncoming.getServiceID().equalsIgnoreCase("MANIN"));
  				
  					PdfPCell dataForIncoming = new PdfPCell(new Phrase (String.valueOf(localModelIncoming.getIncoming()),FontFactory.getFont(FontFactory.HELVETICA, 8, com.itextpdf.text.Font.NORMAL)));
  					dataForIncoming.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
  					dataForIncoming.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
  					IncomingDetails.addCell(dataForIncoming);
  					
  	     		}
  			}
  		
  		
  		PdfPCell emptyIncomingCell = new PdfPCell(new Phrase ("",FontFactory.getFont(FontFactory.HELVETICA, 8, com.itextpdf.text.Font.NORMAL)));
  		emptyIncomingCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
  		emptyIncomingCell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
  		IncomingDetails.addCell(emptyIncomingCell);
  	
  		
  		document.add(IncomingDetails);
  		
  		
  		PdfPTable OutGoingDetails = new PdfPTable(3);
  		OutGoingDetails.setWidthPercentage(100);
  		
  		PdfPCell outgoing = new PdfPCell(new Phrase ("Outgoing",FontFactory.getFont(FontFactory.HELVETICA, 8, com.itextpdf.text.Font.NORMAL)));
  		outgoing.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
  		outgoing.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
  		OutGoingDetails.addCell(outgoing);
  		
  		if (sumOutGoingingMsgsList.size()>0)
  		{
  			
  		for (EndOfDayReportCommonsModel outGoingLocalModel :sumOutGoingingMsgsList)
  		{
  			
  			if ( outGoingLocalModel.getServiceID()!=null &&outGoingLocalModel.getServiceID().equalsIgnoreCase("MANIN"));
  			
  			PdfPCell outgoingDataCell = new PdfPCell(new Phrase(String.valueOf(outGoingLocalModel.getOutgoing()),FontFactory.getFont(FontFactory.HELVETICA, 8, com.itextpdf.text.Font.NORMAL)));
  			outgoingDataCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
  			outgoingDataCell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
  			OutGoingDetails.addCell(outgoingDataCell);
  		
  			
  				}
  					}
  		
  		PdfPCell outgoingEmptyCell = new PdfPCell(new Phrase ("",FontFactory.getFont(FontFactory.HELVETICA, 8, com.itextpdf.text.Font.NORMAL)));
  		outgoingEmptyCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
  		outgoingEmptyCell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
  		OutGoingDetails.addCell(outgoingEmptyCell);
  		
  		
    	document.add(OutGoingDetails);
  		

  		PdfPTable balanceCarriedForwardDetails = new PdfPTable(3);
  		balanceCarriedForwardDetails.setWidthPercentage(100);
  		
  		PdfPCell balanceCarriedForward = new PdfPCell(new Phrase ("Balance carried forward",FontFactory.getFont(FontFactory.HELVETICA, 8, com.itextpdf.text.Font.NORMAL)));
  		balanceCarriedForward.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
  		balanceCarriedForward.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
  		balanceCarriedForwardDetails.addCell(balanceCarriedForward);
  		
  		
  		if (retrieveBalanceCarriedForwardList.size()>0)
  		{
  			
  			for (EndOfDayReportCommonsModel balanceCarriedForwardLocalModel :retrieveBalanceCarriedForwardList )
  			{
  				
  	  			if ( balanceCarriedForwardLocalModel.getServiceID()!=null &&balanceCarriedForwardLocalModel.getServiceID().equalsIgnoreCase("MANIN"));
  	  			
  				PdfPCell balanceCarriedForwrdDataInitiation = new PdfPCell(new Phrase (String.valueOf(balanceCarriedForwardLocalModel.getBalanceCarriedForward()),FontFactory.getFont(FontFactory.HELVETICA, 8, com.itextpdf.text.Font.NORMAL)));
  				balanceCarriedForwrdDataInitiation.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
  				balanceCarriedForwrdDataInitiation.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
  				balanceCarriedForwardDetails.addCell(balanceCarriedForwrdDataInitiation);
  				
  			
  			}
  		}
  		
  		PdfPCell emptyBalanceCarriedForwardCell = new PdfPCell(new Phrase ("",FontFactory.getFont(FontFactory.HELVETICA, 8, com.itextpdf.text.Font.NORMAL)));
  		emptyBalanceCarriedForwardCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
  		emptyBalanceCarriedForwardCell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
  		balanceCarriedForwardDetails.addCell(emptyBalanceCarriedForwardCell);
  		
  		document.add(balanceCarriedForwardDetails);
     	document.add(freeline);
  		
     	//Retrieving data for intiation details 
        
  
     	//details for mandate amendment 
        
        PdfPTable amendmentDetailsHeading = new PdfPTable(1);
        amendmentDetailsHeading.setWidthPercentage(100);

        
    	  PdfPCell amendmentSubHeading  = new PdfPCell(new Paragraph("Mandate Amendment Requests",FontFactory.getFont(FontFactory.HELVETICA, 9,com.itextpdf.text.Font.BOLD)));
    	  amendmentSubHeading.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
    	  amendmentSubHeading.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
    	  amendmentDetailsHeading.addCell(amendmentSubHeading);
   	      document.add(amendmentDetailsHeading);
	      document.add(freeline);
	    
        
	      // The balance details for mandate Amendment
	      PdfPTable amendmentDetails = new PdfPTable(3);
	      amendmentDetails.setWidthPercentage(100);

		PdfPCell cel2 = new PdfPCell(new Phrase("", FontFactory.getFont(	FontFactory.HELVETICA, 8, com.itextpdf.text.Font.NORMAL)));
		cel2.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		cel2.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
		amendmentDetails.addCell(cel2);
		
		
		
		PdfPCell amendvolumeDetails = new PdfPCell(new Phrase("Volume", FontFactory.getFont(	FontFactory.HELVETICA, 8, com.itextpdf.text.Font.NORMAL)));
		amendvolumeDetails.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		amendvolumeDetails.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
		amendmentDetails.addCell(amendvolumeDetails);
		

		PdfPCell emptyCell = new PdfPCell(new Phrase("", FontFactory.getFont(	FontFactory.HELVETICA, 8, com.itextpdf.text.Font.NORMAL)));
		emptyCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		emptyCell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
		amendmentDetails.addCell(emptyCell);

		document.add(amendmentDetails);
		
		 PdfPTable amendmentBalanceBroughtForwardDetails = new PdfPTable(3);
		 amendmentBalanceBroughtForwardDetails.setWidthPercentage(100);
		 
		PdfPCell AmendbalanceBroughtForward = new PdfPCell(new Phrase ("Balance brought forward",FontFactory.getFont(FontFactory.HELVETICA, 8, com.itextpdf.text.Font.NORMAL)));
		AmendbalanceBroughtForward.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		AmendbalanceBroughtForward.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
		amendmentBalanceBroughtForwardDetails.addCell(AmendbalanceBroughtForward);
		
	
		if (endofDayCommonsModelList.size()>0)
     	{
     		
     	for (EndOfDayReportCommonsModel localModel :endofDayCommonsModelList)
     	{
  		

	  		if ( localModel.getServiceID()!=null &&localModel.getServiceID().equalsIgnoreCase("MANAM"));
	  			
     		PdfPCell balancebrghtforwdAmendment = new PdfPCell(new Phrase (String.valueOf(localModel.getBalanceBrghtForward()),FontFactory.getFont(FontFactory.HELVETICA, 8, com.itextpdf.text.Font.NORMAL)));
     		balancebrghtforwdAmendment.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
     		balancebrghtforwdAmendment.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
     		amendmentBalanceBroughtForwardDetails.addCell(balancebrghtforwdAmendment);
     		
     		
     		}
     	}	
  	   	
  		PdfPCell amendMentBalbrghtforwaEmptyCell = new PdfPCell(new Phrase ("",FontFactory.getFont(FontFactory.HELVETICA, 8, com.itextpdf.text.Font.NORMAL)));
  		amendMentBalbrghtforwaEmptyCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
  		amendMentBalbrghtforwaEmptyCell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
  		amendmentBalanceBroughtForwardDetails.addCell(amendMentBalbrghtforwaEmptyCell);
  	
		
		document.add(amendmentBalanceBroughtForwardDetails);
		

		 PdfPTable amendmentIncomingDetails = new PdfPTable(3);
		 amendmentIncomingDetails.setWidthPercentage(100);
		 
		PdfPCell amendincoming = new PdfPCell(new Phrase ("Incoming",FontFactory.getFont(FontFactory.HELVETICA, 8, com.itextpdf.text.Font.NORMAL)));
		amendincoming.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		amendincoming.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
		amendmentIncomingDetails.addCell(amendincoming);

		if (sumIncomingMsgsList.size()>0)
  		{
  			for (EndOfDayReportCommonsModel  localModelIncoming: sumIncomingMsgsList)
  			{
  				

  		  		if ( localModelIncoming.getServiceID()!=null &&localModelIncoming.getServiceID().equalsIgnoreCase("MANAM"));
  		  			
  					PdfPCell dataForIncoming = new PdfPCell(new Phrase (String.valueOf(localModelIncoming.getIncoming()),FontFactory.getFont(FontFactory.HELVETICA, 8, com.itextpdf.text.Font.NORMAL)));
  					dataForIncoming.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
  					dataForIncoming.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
  					amendmentIncomingDetails.addCell(dataForIncoming);
  			
  			}
  		}
		
  		PdfPCell emptyIncomingAmendCell = new PdfPCell(new Phrase ("",FontFactory.getFont(FontFactory.HELVETICA, 8, com.itextpdf.text.Font.NORMAL)));
  		emptyIncomingAmendCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
  		emptyIncomingAmendCell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
  		amendmentIncomingDetails.addCell(emptyIncomingAmendCell);
  	
		document.add(amendmentIncomingDetails);
		
		
		PdfPTable amendmentOutgoingDetails = new PdfPTable(3);
		amendmentOutgoingDetails.setWidthPercentage(100);
		 
		PdfPCell Amendoutgoing = new PdfPCell(new Phrase ("Outgoing",FontFactory.getFont(FontFactory.HELVETICA, 8, com.itextpdf.text.Font.NORMAL)));
		Amendoutgoing.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		Amendoutgoing.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
		amendmentOutgoingDetails.addCell(Amendoutgoing);
		
		
		if (sumOutGoingingMsgsList.size()>0)
  		{
  			
  		for (EndOfDayReportCommonsModel outGoingLocalModel :sumOutGoingingMsgsList)
  		{
  			
  			if ( outGoingLocalModel.getServiceID()!=null &&outGoingLocalModel.getServiceID().equalsIgnoreCase("MANAM"));
	  			
  				PdfPCell outgoingDataCell = new PdfPCell(new Phrase(String.valueOf(outGoingLocalModel.getOutgoing()),FontFactory.getFont(FontFactory.HELVETICA, 8, com.itextpdf.text.Font.NORMAL)));
  				outgoingDataCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
  				outgoingDataCell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
  				amendmentOutgoingDetails.addCell(outgoingDataCell);
  				
  			}
  		}
		
		
  		PdfPCell outgoingAmendEmptyCell = new PdfPCell(new Phrase ("",FontFactory.getFont(FontFactory.HELVETICA, 8, com.itextpdf.text.Font.NORMAL)));
  		outgoingAmendEmptyCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
  		outgoingAmendEmptyCell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
  		amendmentOutgoingDetails.addCell(outgoingAmendEmptyCell);
  		
		document.add(amendmentOutgoingDetails);
		

		PdfPTable amendmentbalCarriedForwardDetails = new PdfPTable(3);
		amendmentbalCarriedForwardDetails.setWidthPercentage(100);
		
		
		PdfPCell AmendbalanceCarriedForward = new PdfPCell(new Phrase ("Balance carried forward",FontFactory.getFont(FontFactory.HELVETICA, 8, com.itextpdf.text.Font.NORMAL)));
		AmendbalanceCarriedForward.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		AmendbalanceCarriedForward.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
		amendmentbalCarriedForwardDetails.addCell(AmendbalanceCarriedForward);
	
		
		
		if (retrieveBalanceCarriedForwardList.size()>0)
  		{
  			
  			for (EndOfDayReportCommonsModel balanceCarriedForwardLocalModel :retrieveBalanceCarriedForwardList )
  			{
  				if ( balanceCarriedForwardLocalModel.getServiceID()!=null &&balanceCarriedForwardLocalModel.getServiceID().equalsIgnoreCase("MANAM"));
	  			
  					PdfPCell balanceCarriedForwrdDataInitiation = new PdfPCell(new Phrase (String.valueOf(balanceCarriedForwardLocalModel.getBalanceCarriedForward()),FontFactory.getFont(FontFactory.HELVETICA, 8, com.itextpdf.text.Font.NORMAL)));
  					balanceCarriedForwrdDataInitiation.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
  					balanceCarriedForwrdDataInitiation.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
  					amendmentbalCarriedForwardDetails.addCell(balanceCarriedForwrdDataInitiation);
  				
  			
  			}
  		}
  		
  		PdfPCell emptyBalanceCarriedForwardCellAmendment = new PdfPCell(new Phrase ("",FontFactory.getFont(FontFactory.HELVETICA, 8, com.itextpdf.text.Font.NORMAL)));
  		emptyBalanceCarriedForwardCellAmendment.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
  		emptyBalanceCarriedForwardCellAmendment.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
  		amendmentbalCarriedForwardDetails.addCell(emptyBalanceCarriedForwardCellAmendment);
  		document.add(amendmentbalCarriedForwardDetails);
   	    document.add(freeline);
		
		//Amendment data details -----------End
   	    
   	    
   	    // Mandate Cancellation details 
          PdfPTable cancelDetailsHeading = new PdfPTable(1);
          cancelDetailsHeading.setWidthPercentage(100);

        
    	  PdfPCell cancelSubHeading  = new PdfPCell(new Paragraph("Mandate Cancellation Requests",FontFactory.getFont(FontFactory.HELVETICA, 9,com.itextpdf.text.Font.BOLD)));
    	  cancelSubHeading.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
    	  cancelSubHeading.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
    	  cancelDetailsHeading.addCell(cancelSubHeading);
   	      document.add(cancelDetailsHeading);
	      document.add(freeline);
	    
        
	      // The balance details for mandate initiation
	      PdfPTable cancellationDetails = new PdfPTable(3);
	      cancellationDetails.setWidthPercentage(100);

		  PdfPCell celCancelation = new PdfPCell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 8, com.itextpdf.text.Font.NORMAL)));
		  celCancelation.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		  celCancelation.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
		  cancellationDetails.addCell(celCancelation);
		
		
		
		PdfPCell cancellationvolumeDetails = new PdfPCell(new Phrase("Volume", FontFactory.getFont(	FontFactory.HELVETICA, 8, com.itextpdf.text.Font.NORMAL)));
		cancellationvolumeDetails.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		cancellationvolumeDetails.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
		cancellationDetails.addCell(cancellationvolumeDetails);
		
		PdfPCell emptyCell2 = new PdfPCell(new Phrase("", FontFactory.getFont(	FontFactory.HELVETICA, 8, com.itextpdf.text.Font.NORMAL)));
		emptyCell2.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		emptyCell2.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
		cancellationDetails.addCell(emptyCell2);
		  
		document.add(cancellationDetails);
		
		
		 PdfPTable cancellationBalanceBroughtForwardDetails = new PdfPTable(3);
		 cancellationBalanceBroughtForwardDetails.setWidthPercentage(100);
		   
		   
		PdfPCell cancellationbalanceBroughtForward = new PdfPCell(new Phrase ("Balance brought forward",FontFactory.getFont(FontFactory.HELVETICA, 8, com.itextpdf.text.Font.NORMAL)));
		cancellationbalanceBroughtForward.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		cancellationbalanceBroughtForward.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
		cancellationBalanceBroughtForwardDetails.addCell(cancellationbalanceBroughtForward);
		
		if (endofDayCommonsModelList.size()>0)
     	{
     		
     	for (EndOfDayReportCommonsModel localModel :endofDayCommonsModelList)
     	{
  		
     		if ( localModel.getServiceID()!=null &&localModel.getServiceID().equalsIgnoreCase("MANCN"));
  			
     			PdfPCell balancebrghtforwdAmendment = new PdfPCell(new Phrase (String.valueOf(localModel.getBalanceBrghtForward()),FontFactory.getFont(FontFactory.HELVETICA, 8, com.itextpdf.text.Font.NORMAL)));
     			balancebrghtforwdAmendment.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
     			balancebrghtforwdAmendment.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
     			cancellationBalanceBroughtForwardDetails.addCell(balancebrghtforwdAmendment);
     		
     	}
  	   	
     }
		
  		PdfPCell cancelBalbrghtforwaEmptyCell = new PdfPCell(new Phrase ("",FontFactory.getFont(FontFactory.HELVETICA, 8, com.itextpdf.text.Font.NORMAL)));
  		cancelBalbrghtforwaEmptyCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
  		cancelBalbrghtforwaEmptyCell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
  		cancellationBalanceBroughtForwardDetails.addCell(cancelBalbrghtforwaEmptyCell);
  	
		
		document.add(cancellationBalanceBroughtForwardDetails);
		
		PdfPTable cancellationIncomingDetails = new PdfPTable(3);
		cancellationIncomingDetails.setWidthPercentage(100);
		   
		PdfPCell cancellationincoming = new PdfPCell(new Phrase ("Incoming",FontFactory.getFont(FontFactory.HELVETICA, 8, com.itextpdf.text.Font.NORMAL)));
		cancellationincoming.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		cancellationincoming.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
		cancellationIncomingDetails.addCell(cancellationincoming);
		
		
		if (sumIncomingMsgsList.size()>0)
  		{
  			for (EndOfDayReportCommonsModel  localModelIncoming: sumIncomingMsgsList)
  			{
  		 		if ( localModelIncoming.getServiceID()!=null &&localModelIncoming.getServiceID().equalsIgnoreCase("MANCN"));
  	  			
  					PdfPCell dataForIncoming = new PdfPCell(new Phrase (String.valueOf(localModelIncoming.getIncoming()),FontFactory.getFont(FontFactory.HELVETICA, 8, com.itextpdf.text.Font.NORMAL)));
  					dataForIncoming.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
  					dataForIncoming.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
  					cancellationIncomingDetails.addCell(dataForIncoming);
	  		
	  		
  				}
  					}
		
  		PdfPCell emptyIncomingCancelCell = new PdfPCell(new Phrase ("",FontFactory.getFont(FontFactory.HELVETICA, 8, com.itextpdf.text.Font.NORMAL)));
  		emptyIncomingCancelCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
  		emptyIncomingCancelCell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
  		cancellationIncomingDetails.addCell(emptyIncomingCancelCell);
		document.add(cancellationIncomingDetails);
		
		
		PdfPTable cancellationOutGoingDetails = new PdfPTable(3);
		cancellationOutGoingDetails.setWidthPercentage(100);
		
		PdfPCell cancellationoutgoing = new PdfPCell(new Phrase ("Outgoing",FontFactory.getFont(FontFactory.HELVETICA, 8, com.itextpdf.text.Font.NORMAL)));
		cancellationoutgoing.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		cancellationoutgoing.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
		cancellationOutGoingDetails.addCell(cancellationoutgoing);
		
		

		if (sumOutGoingingMsgsList.size()>0)
  		{
  			
  		for (EndOfDayReportCommonsModel outGoingLocalModel :sumOutGoingingMsgsList)
  		{

		 		if ( outGoingLocalModel.getServiceID()!=null &&outGoingLocalModel.getServiceID().equalsIgnoreCase("MANCN"));
	  			
  				PdfPCell outgoingDataCell = new PdfPCell(new Phrase(String.valueOf(outGoingLocalModel.getOutgoing()),FontFactory.getFont(FontFactory.HELVETICA, 8, com.itextpdf.text.Font.NORMAL)));
  				outgoingDataCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
  				outgoingDataCell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
  				cancellationOutGoingDetails.addCell(outgoingDataCell);
  		
  			
  				}
  					}
		
  		PdfPCell outgoingCancelCell = new PdfPCell(new Phrase ("",FontFactory.getFont(FontFactory.HELVETICA, 8, com.itextpdf.text.Font.NORMAL)));
  		outgoingCancelCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
  		outgoingCancelCell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
  		cancellationOutGoingDetails.addCell(outgoingCancelCell);
		document.add(cancellationOutGoingDetails);
		
		PdfPTable cancellationBalanceCarriedForwardDetails = new PdfPTable(3);
		cancellationBalanceCarriedForwardDetails.setWidthPercentage(100);
		
		
		PdfPCell cancellationbalanceCarriedForward = new PdfPCell(new Phrase ("Balance carried forward",FontFactory.getFont(FontFactory.HELVETICA, 8, com.itextpdf.text.Font.NORMAL)));
		cancellationbalanceCarriedForward.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		cancellationbalanceCarriedForward.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
		cancellationBalanceCarriedForwardDetails.addCell(cancellationbalanceCarriedForward);
		
	
		if (retrieveBalanceCarriedForwardList.size()>0)
  		{
  			
  			for (EndOfDayReportCommonsModel balanceCarriedForwardLocalModel :retrieveBalanceCarriedForwardList )
  			{

		 		if ( balanceCarriedForwardLocalModel.getServiceID()!=null &&balanceCarriedForwardLocalModel.getServiceID().equalsIgnoreCase("MANCN"));
	  			
  					PdfPCell balanceCarriedForwrdDataInitiation = new PdfPCell(new Phrase (String.valueOf(balanceCarriedForwardLocalModel.getBalanceCarriedForward()),FontFactory.getFont(FontFactory.HELVETICA, 8, com.itextpdf.text.Font.NORMAL)));
  					balanceCarriedForwrdDataInitiation.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
  					balanceCarriedForwrdDataInitiation.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
  					cancellationBalanceCarriedForwardDetails.addCell(balanceCarriedForwrdDataInitiation);
  				
  			
  			
  				}
  					}
		
  		PdfPCell emptyBalanceCarriedForwardCellCancellation = new PdfPCell(new Phrase ("",FontFactory.getFont(FontFactory.HELVETICA, 8, com.itextpdf.text.Font.NORMAL)));
  		emptyBalanceCarriedForwardCellCancellation.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
  		emptyBalanceCarriedForwardCellCancellation.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
  		cancellationBalanceCarriedForwardDetails.addCell(emptyBalanceCarriedForwardCellCancellation);
  	    document.add(cancellationBalanceCarriedForwardDetails);
   	    document.add(freeline);
		
   	    // Cancellation dat retrieval 
  	    
		document.close();
		
	}
	 
	public List<EndOfDayReportCommonsModel> retrieveBalanceBroughtForward()
	{
		endofDayCommonsModelList = (List<EndOfDayReportCommonsModel>) controller.retrieveTotalBalanceBroughtForward();
		log.info("Balance brought forwardis ****************************"+endofDayCommonsModelList );
		
		return endofDayCommonsModelList;
	}
	
	public List<EndOfDayReportCommonsModel> sumIncomingMessagesPerServiceId()
	{
		sumIncomingMsgsList = (List<EndOfDayReportCommonsModel>) controller.sumIncomingMessagesPerServiceId();
		log.info("Balance brought forwardis ****************************"+sumIncomingMsgsList );
		
		return sumIncomingMsgsList;
	}
	
	
	
	public List<EndOfDayReportCommonsModel> sumOutGoingMessagesPerServiceID()
	{
		sumOutGoingingMsgsList = (List<EndOfDayReportCommonsModel>) controller.sumOutGoingMessagesPerServiceID();
		log.info("Balance brought forwardis ****************************"+sumOutGoingingMsgsList);
		
		return sumOutGoingingMsgsList;
	}
	public List<EndOfDayReportCommonsModel> retrieveBalanceCarried()
	{
	retrieveBalanceCarriedForwardList=(List<EndOfDayReportCommonsModel>) controller.retrieveBalanceCarriedForward();
	
	return retrieveBalanceCarriedForwardList;
	}

}