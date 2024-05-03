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

import com.bsva.commons.model.FileStatusReportModel;
import com.bsva.commons.model.OutBalanceCountReportModel;
import com.bsva.commons.model.ServicesModel;
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
 * 
 * @author DimakatsoN
 *
 */

public class FileStatusReport {

	public static Logger log=Logger.getLogger(FileStatusReport.class);
	Date currentDate = new Date();
	private String downloaddirectory ="/home/opsjava/Delivery/Cession_Assign/Reports/";
	Controller controller = new Controller ();
	List<FileStatusReportModel> fileStatusReportList = new ArrayList<FileStatusReportModel>();
	//List<FileStatusReportModel> countNrOfFilesList = new ArrayList<FileStatusReportModel>();
	List<FileStatusReportModel> countNrOfMsgsList = new ArrayList<FileStatusReportModel>();
	
	public void generateReport(String reportNr, String reportname) throws FileNotFoundException, DocumentException
	{
		retrieveMndtFileStatus();
		retrieveCountNrOfMsgsStatus();
		//retrieveCountNrOfFilesStatus();
		generateReportColumns(reportNr, reportname);
	}
	
	
public void generateReportColumns(String reportNo,String reportname ) throws FileNotFoundException, DocumentException
	
	{
	
	DecimalFormat df = new DecimalFormat("### ### ### ### ### ##0.00");
    DateFormat dateTimeFormat = new SimpleDateFormat("yyyyMMdd_HH:mm:ss");
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    DateFormat fileTimeFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
    Date currentDate = new Date();
    
    int pageNo = 1;
    
    String files = downloaddirectory+reportNo+"-"+reportname+"_"+fileTimeFormat.format(currentDate).toString()+".pdf";
    
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
/*
			PdfPCell nullCell03 = new PdfPCell(new Paragraph(null,FontFactory.getFont(FontFactory.HELVETICA, 8)));
			nullCell03.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
			nullCell03.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
			secondheading.addCell(nullCell03);*/
			String strProcDate = null;
			if(systemParameterModel.getProcessDate() != null)
			{
				 strProcDate = dateFormat.format(systemParameterModel.getProcessDate());
			}
				
	          PdfPCell nullCell03 = new PdfPCell(new Paragraph("ProcessDate: " + strProcDate,  FontFactory.getFont(FontFactory.HELVETICA, 8)));
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
        
        PdfPTable systemData = new PdfPTable(5);
	      systemData.setWidthPercentage(100);
	      
	      PdfPCell fileName = new PdfPCell(new Phrase("File Name", FontFactory.getFont(	FontFactory.HELVETICA, 8, com.itextpdf.text.Font.BOLD)));
	      fileName.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
	      fileName.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
	      systemData.addCell(fileName);
	      
	      PdfPCell InstrAgent = new PdfPCell(new Phrase("Instructing Agent", FontFactory.getFont(	FontFactory.HELVETICA, 8, com.itextpdf.text.Font.BOLD)));
	      InstrAgent.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
	      InstrAgent.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
	      systemData.addCell(InstrAgent);
	      
	      PdfPCell nrOfFiles = new PdfPCell(new Phrase("Service ID", FontFactory.getFont(	FontFactory.HELVETICA, 8, com.itextpdf.text.Font.BOLD)));
	      nrOfFiles.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
	      nrOfFiles.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
	      systemData.addCell(nrOfFiles);
	  
	      PdfPCell nrOfTrans = new PdfPCell(new Phrase("Nr Of Transactions", FontFactory.getFont(	FontFactory.HELVETICA, 8, com.itextpdf.text.Font.BOLD)));
	      nrOfTrans.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
	      nrOfTrans.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
	      systemData.addCell(nrOfTrans);
	      
	      PdfPCell status= new PdfPCell(new Phrase("File Status", FontFactory.getFont(	FontFactory.HELVETICA, 8, com.itextpdf.text.Font.BOLD)));
	      status.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
	      status.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
	      systemData.addCell(status); 

	      document.add(systemData);
	  	  document.add(freeline);
	  	  
	  	// populate Data for File Status Report
	  	  
	      if(fileStatusReportList.size() >0)
	      {
	    	  for(FileStatusReportModel localModel :fileStatusReportList)
	    	  {
	    		  PdfPTable systemDataPopulation = new PdfPTable(5);
	    		  systemDataPopulation.setWidthPercentage(100);
	    		  
	    		  PdfPCell fileNameData = new PdfPCell(new Phrase(localModel.getFileName(), FontFactory.getFont(FontFactory.HELVETICA, 8, com.itextpdf.text.Font.NORMAL)));
	    		  fileNameData.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
	    		  fileNameData.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
				  systemDataPopulation.addCell(fileNameData);
				  
				  PdfPCell InstrAgentData = new PdfPCell(new Phrase(localModel.getInstId(), FontFactory.getFont(FontFactory.HELVETICA, 8, com.itextpdf.text.Font.NORMAL)));
				  InstrAgentData.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
				  InstrAgentData.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
				  systemDataPopulation.addCell(InstrAgentData);
				  
				  PdfPCell nrOfFilesData = new PdfPCell(new Phrase(localModel.getServiceId(), FontFactory.getFont(FontFactory.HELVETICA, 8, com.itextpdf.text.Font.NORMAL)));
				  nrOfFilesData.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
				  nrOfFilesData.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
				  systemDataPopulation.addCell(nrOfFilesData);

				  PdfPCell nrOfTransData = new PdfPCell(new Phrase(String.valueOf(localModel.getNrOfMsgs()), FontFactory.getFont(FontFactory.HELVETICA, 8, com.itextpdf.text.Font.NORMAL)));
				  nrOfTransData.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
				  nrOfTransData.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
				  systemDataPopulation.addCell(nrOfTransData);
				  
				  PdfPCell statusData = new PdfPCell(new Phrase(String.valueOf(localModel.getStatus()), FontFactory.getFont(FontFactory.HELVETICA, 8, com.itextpdf.text.Font.NORMAL)));
				  statusData.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
				  statusData.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
				  systemDataPopulation.addCell(statusData);
	  
				  document.add(systemDataPopulation);
				  document.add(freeline);
				  document.add(freeline);
				  
	    	  }
	      }
	      
/*	      
	      if(countNrOfFilesList.size() >0)
	      {
	    	  	for(FileStatusReportModel localModel :countNrOfFilesList)
	    	  		{
	    		  PdfPTable totalNrOfFilesHeading = new PdfPTable(2);
	    		  totalNrOfFilesHeading.setWidthPercentage(50);

	    		  PdfPCell totalNrOfFiles = new PdfPCell(new Phrase("Total Nr Of Files: ", FontFactory.getFont(FontFactory.HELVETICA, 9, com.itextpdf.text.Font.BOLD)));
	    		  totalNrOfFiles.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
	    		  totalNrOfFiles.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
	    		  totalNrOfFilesHeading.addCell(totalNrOfFiles);

	    		  PdfPCell totalNrOfFilesData = new PdfPCell(new Phrase(String.valueOf(localModel.getNrOfFiles()),FontFactory.getFont(FontFactory.HELVETICA,9, com.itextpdf.text.Font.BOLD)));
	    		  totalNrOfFilesData.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
	    		  totalNrOfFilesData.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
	    		  totalNrOfFilesHeading.addCell(totalNrOfFilesData);

	    		  document.add(totalNrOfFilesHeading);
  
	    	  		}
 
	    	  		document.add(freeline);
  				}*/
	      
	      
	      if(countNrOfMsgsList.size() >0)
	      {
	    	  	for(FileStatusReportModel localModel :countNrOfMsgsList)
	    	  		{
	    		  PdfPTable totalNrOfMsgsHeading = new PdfPTable(2);
	    		  totalNrOfMsgsHeading.setWidthPercentage(50);

	    		  PdfPCell totalNrOfMsgs = new PdfPCell(new Phrase("Total Nr Of Transactions: ", FontFactory.getFont(FontFactory.HELVETICA, 9, com.itextpdf.text.Font.BOLD)));
	    		  totalNrOfMsgs.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
	    		  totalNrOfMsgs.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
	    		  totalNrOfMsgsHeading.addCell(totalNrOfMsgs);

	    		  PdfPCell totalNrOfMsgsData = new PdfPCell(new Phrase(String.valueOf(localModel.getNrOfMsgs()),FontFactory.getFont(FontFactory.HELVETICA,9, com.itextpdf.text.Font.BOLD)));
	    		  totalNrOfMsgsData.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
	    		  totalNrOfMsgsData.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
	    		  totalNrOfMsgsHeading.addCell(totalNrOfMsgsData);

	    		  document.add(totalNrOfMsgsHeading);
  
	    	  		}
 
	    	  		document.add(freeline);
  				}
	      
	      document.close();
        
	}


	public List<FileStatusReportModel>retrieveMndtFileStatus()
	{
		fileStatusReportList = (List<FileStatusReportModel>)controller.retrieveMndtFileStatus();
		Collections.sort(fileStatusReportList, new fileNameOrderSorter());
		return fileStatusReportList;
	}
/*	
	public List<FileStatusReportModel>retrieveCountNrOfFilesStatus()
	{
		countNrOfFilesList = (List<FileStatusReportModel>)controller.retrieveCountNrOfFilesStatus();
		
		return countNrOfFilesList;
	}
	*/
	public List<FileStatusReportModel>retrieveCountNrOfMsgsStatus()
	{
		countNrOfMsgsList = (List<FileStatusReportModel>)controller.retrieveCountNrOfMsgsStatus();
		
		return countNrOfMsgsList;
	}
	
	
	// Sort fileName in ASCENDING order
	private class fileNameOrderSorter implements Comparator<FileStatusReportModel>
	{

		@Override
		public int compare(FileStatusReportModel o1, FileStatusReportModel o2) {
		
			if(o1.getFileName() == null && o2.getFileName() == null)
			{
				return 0;
			}
			else if(o1.getFileName() == null)
			{
				return -1;
			}
			else if(o2.getFileName() == null)
			{
				return 1;
			}
			return o1.getFileName().compareTo(o2.getFileName());
		}
		
	}
}
