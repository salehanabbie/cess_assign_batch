package com.bsva.panels.reports;

import java.io.File;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.handler.resource.ResourceStreamRequestHandler;
import org.apache.wicket.util.resource.FileResourceStream;

import com.bsva.commons.model.MandatesCountCommonsModel;
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

public class MandatesMessagingReport
{

	public static Logger log=Logger.getLogger(MandatesMessagingReport.class);
	Date currentDate = new Date();
	private String downloaddirectory ="/home/jboss/Mandates/Reports/";
	private String serviceId = "MANIN";
	Controller controller = new Controller ();
	List<MandatesCountCommonsModel>retrieveAllMandatesDetailsList = new ArrayList<MandatesCountCommonsModel>();
	List<MandatesCountCommonsModel>retrieveIncomingMandatesCountList = new ArrayList<MandatesCountCommonsModel>();
	List<MandatesCountCommonsModel>retrieveOutgoingMandatesCountList = new ArrayList<MandatesCountCommonsModel>();
	
	MandatesCountCommonsModel  mandatesCountCommonsModel;
	
	public void generateReport(String reportNr, String reportname) throws FileNotFoundException, DocumentException
	{	retrieveOutgoingMandatesCount();
		retrieveIncomingMandatesCount();
		//retrieveAllMandatesCount();
		generateReportColumns(reportNr, reportname);
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
        
        
        PdfPTable serviceIncomingHeading = new PdfPTable(1);
        serviceIncomingHeading.setWidthPercentage(100);

        
    	  PdfPCell byServiceIncomingSubHeading  = new PdfPCell(new Paragraph("By Service Incoming",FontFactory.getFont(FontFactory.HELVETICA, 9,com.itextpdf.text.Font.BOLD)));
    	  byServiceIncomingSubHeading.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
    	  byServiceIncomingSubHeading.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
    	  serviceIncomingHeading.addCell(byServiceIncomingSubHeading);
   	      document.add(serviceIncomingHeading);
	      document.add(freeline);
	      
	      PdfPTable IncomingDetails = new PdfPTable(3);
	      IncomingDetails.setWidthPercentage(100);

  		PdfPCell serviceId = new PdfPCell(new Phrase("Service ID", FontFactory.getFont(	FontFactory.HELVETICA, 8, com.itextpdf.text.Font.NORMAL)));
  		serviceId.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
  		serviceId.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
  		IncomingDetails.addCell(serviceId);
  		
  		
  		
        PdfPCell memberIdDetails = new PdfPCell(new Phrase("Member ID ", FontFactory.getFont(	FontFactory.HELVETICA, 8, com.itextpdf.text.Font.NORMAL)));
        memberIdDetails.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
        memberIdDetails.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
        IncomingDetails.addCell(memberIdDetails);
  		
  		PdfPCell volume = new PdfPCell(new Phrase("Volume", FontFactory.getFont(	FontFactory.HELVETICA, 8, com.itextpdf.text.Font.NORMAL)));
  		volume.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
  		volume.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
  		IncomingDetails.addCell(volume);
  		document.add(IncomingDetails);
  		document.add(freeline);
  		
  		// populate Data for service incoming
	      
  		if (retrieveIncomingMandatesCountList.size()>0)
  		{
  			for (MandatesCountCommonsModel localModel :retrieveIncomingMandatesCountList)
  			{
  				
  					PdfPTable IncomingDataPopulation = new PdfPTable(3);
  					IncomingDataPopulation.setWidthPercentage(100);
  			    
  			    
  					PdfPCell serviceIdData = new PdfPCell(new Phrase(localModel.getServiceId(), FontFactory.getFont(	FontFactory.HELVETICA, 8, com.itextpdf.text.Font.NORMAL)));
  					serviceIdData.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
  					serviceIdData.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
  					IncomingDataPopulation.addCell(serviceIdData);
  	  		
  	  		
  	  		
  					PdfPCell memberIdData = new PdfPCell(new Phrase(localModel.getInstId(), FontFactory.getFont(	FontFactory.HELVETICA, 8, com.itextpdf.text.Font.NORMAL)));
  					memberIdData.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
  					memberIdData.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
  					IncomingDataPopulation.addCell(memberIdData);
  	  		
  					PdfPCell volumeData = new PdfPCell(new Phrase(localModel.getNrOfMsgs().toString(), FontFactory.getFont(	FontFactory.HELVETICA, 8, com.itextpdf.text.Font.NORMAL)));
  					volumeData.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
  					volumeData.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
  					IncomingDataPopulation.addCell(volumeData);
  			      
  					document.add(IncomingDataPopulation);
  			      
  				}
  			}
  	
	      
  		
  		 
  		PdfPTable serviceOutgoingHeading = new PdfPTable(1);
        serviceOutgoingHeading.setWidthPercentage(100);

        
    	  PdfPCell byServiceOutgoingSubHeading  = new PdfPCell(new Paragraph("By Service Outgoing",FontFactory.getFont(FontFactory.HELVETICA, 9,com.itextpdf.text.Font.BOLD)));
    	  byServiceOutgoingSubHeading.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
    	  byServiceOutgoingSubHeading.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
    	  serviceOutgoingHeading.addCell(byServiceOutgoingSubHeading);
   	      document.add(serviceOutgoingHeading);
	      document.add(freeline);
	      
	      PdfPTable OutgoingDetails = new PdfPTable(3);
	      OutgoingDetails.setWidthPercentage(100);

  			PdfPCell serviceIdout = new PdfPCell(new Phrase("Service ID", FontFactory.getFont(	FontFactory.HELVETICA, 8, com.itextpdf.text.Font.NORMAL)));
  			serviceIdout.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
  			serviceIdout.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
  			OutgoingDetails.addCell(serviceIdout);
  		
  		
  		
        PdfPCell memberIdDetailsOut = new PdfPCell(new Phrase("Member ID ", FontFactory.getFont(	FontFactory.HELVETICA, 8, com.itextpdf.text.Font.NORMAL)));
        memberIdDetailsOut.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
        memberIdDetailsOut.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
        OutgoingDetails.addCell(memberIdDetailsOut);
  		
  		PdfPCell volumeOut = new PdfPCell(new Phrase("Volume", FontFactory.getFont(	FontFactory.HELVETICA, 8, com.itextpdf.text.Font.NORMAL)));
  		volumeOut.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
  		volumeOut.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
  		OutgoingDetails.addCell(volumeOut);
  		document.add(OutgoingDetails);
  		document.add(freeline);
  		
  		
  	// populate Data for service incoming
	      
  		if (retrieveOutgoingMandatesCountList.size()>0)
  		{
  			for (MandatesCountCommonsModel localModelOutgoing :retrieveOutgoingMandatesCountList)
  			{
  		      	PdfPTable OutgoingDataPopulation = new PdfPTable(3);
  					OutgoingDataPopulation.setWidthPercentage(100);
  			    
  			    
  					PdfPCell serviceIdOutData = new PdfPCell(new Phrase(localModelOutgoing.getServiceId(), FontFactory.getFont(	FontFactory.HELVETICA, 8, com.itextpdf.text.Font.NORMAL)));
  					serviceIdOutData.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
  					serviceIdOutData.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
  					OutgoingDataPopulation.addCell(serviceIdOutData);
  	  		
  	  		
  	  		
  					PdfPCell memberIdOutData = new PdfPCell(new Phrase(localModelOutgoing.getInstId(), FontFactory.getFont(	FontFactory.HELVETICA, 8, com.itextpdf.text.Font.NORMAL)));
  					memberIdOutData.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
  					memberIdOutData.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
  					OutgoingDataPopulation.addCell(memberIdOutData);
  	  		
  					PdfPCell volumeOutData = new PdfPCell(new Phrase(localModelOutgoing.getNrOfMsgs().toString(), FontFactory.getFont(	FontFactory.HELVETICA, 8, com.itextpdf.text.Font.NORMAL)));
  					volumeOutData.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
  					volumeOutData.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
  					OutgoingDataPopulation.addCell(volumeOutData);
  			      
  					document.add(OutgoingDataPopulation);
  			      
  				}
  			}
  	
	      
	      document.close();

	}

/*public List<MandatesCountCommonsModel> retrieveAllMandatesCount()

{
	retrieveAllMandatesDetailsList = (List<MandatesCountCommonsModel>) controller.retrieveAllMandatesCount();
	log.info("the list has ###############################################"+retrieveAllMandatesDetailsList);
	return retrieveAllMandatesDetailsList;
	
}
*/
public List<MandatesCountCommonsModel>retrieveIncomingMandatesCount()
{
	retrieveIncomingMandatesCountList.clear();
	retrieveIncomingMandatesCountList=(List<MandatesCountCommonsModel>)controller.retrieveIncomingMandatesCount();
	Collections.sort(retrieveIncomingMandatesCountList, new serviceIdOrderSorter());
	log.info("the list has ###################"+retrieveIncomingMandatesCountList);
	
	return retrieveIncomingMandatesCountList;
	
}

public  List<MandatesCountCommonsModel>retrieveOutgoingMandatesCount()
{
	retrieveOutgoingMandatesCountList.clear();
	retrieveOutgoingMandatesCountList=(List<MandatesCountCommonsModel>)controller.retrieveOutgoingMandatesCount();
	Collections.sort(retrieveOutgoingMandatesCountList, new serviceIdOrderSorter());
	
	return retrieveOutgoingMandatesCountList;
}

//Sort ServiceId in ASCENDING order for incoming and outgoing list
	private class serviceIdOrderSorter implements Comparator<MandatesCountCommonsModel>
	{

		@Override
		public int compare(MandatesCountCommonsModel o1, MandatesCountCommonsModel o2) {
		
			if(o1.getServiceId() == null && o2.getServiceId() == null)
			{
				return 0;
			}
			else if(o1.getServiceId() == null)
			{
				return -1;
			}
			else if(o2.getServiceId() == null)
			{
				return 1;
			}
			return o1.getServiceId().compareTo(o2.getServiceId());
		}
		
	}
}
