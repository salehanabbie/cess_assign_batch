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

import com.bsva.commons.model.AcOpsTransCtrlMsgModel;
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

/***
 * 
 * @author DimakatsoN
 *
 */

public class EotFileReport {
	
	public static Logger log=Logger.getLogger(EotFileReport.class);
	Date currentDate = new Date();
	private String downloaddirectory ="/home/opsjava/Delivery/Mandates/Reports/";
	Controller controller = new Controller ();
	
	List<AcOpsTransCtrlMsgModel>eotFileList = new ArrayList<AcOpsTransCtrlMsgModel>();
	
	
	
	public void generateReport(String reportNr, String reportname) throws FileNotFoundException, DocumentException
	{
	
		retrieveAllEotFiles();
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
        
        

	      PdfPTable systemData = new PdfPTable(4);
	      systemData.setWidthPercentage(100);
	      
	      
	      PdfPCell memberNo = new PdfPCell(new Phrase("Member No",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
	      memberNo.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
	      memberNo.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
	      systemData.addCell(memberNo);
	      
	      PdfPCell serviceId = new PdfPCell(new Phrase("Service ID",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
	      serviceId.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
	      serviceId.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
	      systemData.addCell(serviceId);
	      
	      PdfPCell nrOfFiles = new PdfPCell(new Phrase("Nr of Files",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
	      nrOfFiles.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
	      nrOfFiles.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
	      systemData.addCell(nrOfFiles);
	      
	      PdfPCell nrOfRecords = new PdfPCell(new Phrase("Nr of Records",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
	      nrOfRecords.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
	      nrOfRecords.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
	      systemData.addCell(nrOfRecords);
	      
	      
 	      document.add(systemData);
	  	  document.add(freeline);
	  	  
	  	  
	  	  if(eotFileList.size()> 0)
	  	  {
	  		  for(AcOpsTransCtrlMsgModel acOpsTransCtrlMsgModel : eotFileList)
	  		  {
	  			  
	  			log.info("report details ############"+eotFileList);
	  			 PdfPTable systemDataPopulation = new PdfPTable(4);
	    		  systemDataPopulation.setWidthPercentage(100);
	    		  
	    		  PdfPCell memberIdData = new PdfPCell(new Phrase(acOpsTransCtrlMsgModel.getMemberIdTo(), FontFactory.getFont(	FontFactory.HELVETICA, 8, com.itextpdf.text.Font.NORMAL)));
	    		  memberIdData.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
	    		  memberIdData.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
				  systemDataPopulation.addCell(memberIdData);
				  
				  PdfPCell serviceIdData = new PdfPCell(new Phrase(acOpsTransCtrlMsgModel.getServiceId(), FontFactory.getFont(FontFactory.HELVETICA, 8, com.itextpdf.text.Font.NORMAL)));
				  serviceIdData.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
				  serviceIdData.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
				  systemDataPopulation.addCell(serviceIdData);
				  
				  
				  PdfPCell nrofFilesData = new PdfPCell(new Phrase(acOpsTransCtrlMsgModel.getNrOfFiles().toString(),FontFactory.getFont(FontFactory.HELVETICA, 8, com.itextpdf.text.Font.NORMAL)));
				  nrofFilesData.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
				  nrofFilesData.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
				  systemDataPopulation.addCell(nrofFilesData);

				  PdfPCell nrofRecordsData = new PdfPCell(new Phrase(acOpsTransCtrlMsgModel.getNrOfRecords().toString(), FontFactory.getFont(	FontFactory.HELVETICA, 8, com.itextpdf.text.Font.NORMAL)));
				  nrofRecordsData.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
				  nrofRecordsData.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
				  systemDataPopulation.addCell(nrofRecordsData);
				  
					
				    document.add(systemDataPopulation);
				  
	    	  }
	      }
	      
	      document.close();
	      
	}
	  			  


public List<AcOpsTransCtrlMsgModel>retrieveAllEotFiles()
{
	eotFileList = (List<AcOpsTransCtrlMsgModel>)controller.retrieveAllEotFiles();
	Collections.sort(eotFileList, new serviceIdOrderSorter());
	log.info("the list has ############"+eotFileList);
	return eotFileList;
}


//Sort ServiceId in ASCENDING order
	private class serviceIdOrderSorter implements Comparator<AcOpsTransCtrlMsgModel>
	{

		@Override
		public int compare(AcOpsTransCtrlMsgModel o1, AcOpsTransCtrlMsgModel o2) {
		
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
