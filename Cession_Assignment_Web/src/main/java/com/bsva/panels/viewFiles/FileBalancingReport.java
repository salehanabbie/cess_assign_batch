package com.bsva.panels.viewFiles;
import java.io.File;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
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
import com.bsva.commons.model.OpsFileRegModel;
import com.bsva.commons.model.OutBalanceCountReportModel;
import com.bsva.commons.model.SysCisBankModel;
import com.bsva.commons.model.SysctrlCompParamModel;
import com.bsva.commons.model.SystemParameterModel;
import com.bsva.controller.Controller;
import com.bsva.interfaces.PropertyUtilRemote;
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
 * 
 * @author DimakatsoN
 *
 */
public class FileBalancingReport 
{
	public static Logger log=Logger.getLogger(FileBalancingReport.class);
	Date currentDate = new Date();
	private String downloaddirectory ="/home/opsjava/Delivery/Cession_Assign/Reports/";
	private List<OpsFileRegModel> incomingFilesList = new ArrayList<OpsFileRegModel>();
	private List<OpsFileRegModel> outgoingFilesList = new ArrayList<OpsFileRegModel>();
	Controller controller  = new Controller();
	private static PropertyUtilRemote propertyUtilRemote;
	String tempDir, reportDir,pdfFileName,files;
	
	public void generateReport(String reportNr, String reportname) throws FileNotFoundException, DocumentException
	{
		log.info("in the generate report method ");
		contextPropertyFileCheck();
		incomingFiles();
		outgoingFiles();

		
		try
		{
			tempDir = propertyUtilRemote.getPropValue("ExtractTemp.Out");
			reportDir= propertyUtilRemote.getPropValue("Reports.Output");
			log.debug("tempDir ==> "+tempDir);
			log.debug("reportDir ==> "+reportDir);
			//Retrieve Report Name here

		}
		catch(Exception ex)
		{
			log.error("mr017- Could not find MandateMessageCommons.properties in classpath");	
			reportDir = "/home/opsjava/Delivery/Mandates/Output/Reports/";
			tempDir="/home/opsjava/Delivery/Mandates/Output/temp/";
			
		}
		
		generateReportColumns( reportNr,reportname);
	}
	
	public void generateReportColumns(String reportNo,String reportname ) throws FileNotFoundException, DocumentException
	{
		log.info("The report columns method ****************");
		DecimalFormat df = new DecimalFormat("### ### ### ### ### ##");
		DateFormat dateTimeFormat = new SimpleDateFormat("yyyyMMdd_HH:mm:ss");
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat fileTimeFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
		Date currentDate = new Date();
		int pageNo = 1;
	     files =tempDir+reportNo+"-"+reportname.toUpperCase()+"_"+fileTimeFormat.format(currentDate).toString()+".pdf";
	     pdfFileName = reportNo+"-"+reportname.toUpperCase()+"_"+fileTimeFormat.format(currentDate).toString()+".pdf";
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
		try 
		{
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

		PdfPCell cell1 = new PdfPCell(new Paragraph(reportNo+ "-"+ reportname.toUpperCase(), FontFactory.getFont( FontFactory.HELVETICA, 9, com.itextpdf.text.Font.BOLD)));
		cell1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		cell1.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
		thirdHeading.addCell(cell1);

		document.add(thirdHeading);
		document.add(freeline);
		
		PdfPTable reportHeading = new PdfPTable(3);
		reportHeading.setWidthPercentage(100);
		
		PdfPCell incomingFileName  = new PdfPCell(new Paragraph("Incoming File Name",FontFactory.getFont(FontFactory.HELVETICA, 9,com.itextpdf.text.Font.BOLD)));
		incomingFileName.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		incomingFileName.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
		reportHeading.addCell(incomingFileName);
		
		PdfPCell incomingFileStatus = new PdfPCell(new Paragraph("Status",FontFactory.getFont(FontFactory.HELVETICA, 9,com.itextpdf.text.Font.BOLD)));
		incomingFileStatus.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		incomingFileStatus.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
		reportHeading.addCell(incomingFileStatus);
		
		PdfPCell incomingFileDate = new PdfPCell(new Paragraph("Process Date and Time",FontFactory.getFont(FontFactory.HELVETICA, 9,com.itextpdf.text.Font.BOLD)));
		incomingFileDate.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		incomingFileDate.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
		reportHeading.addCell(incomingFileDate);
		
		document.add(reportHeading);
		document.add(freeline);
		
		int totalInFiles;
		int totalOutFiles;
		if(incomingFilesList!= null && incomingFilesList.size() > 0)
		{
			for(OpsFileRegModel incomingFileModel : incomingFilesList)
			{	
				PdfPTable incomingFileDataTableInfo = new PdfPTable(3);
				incomingFileDataTableInfo.setWidthPercentage(100);
				
				PdfPCell incomingFileNameData = new PdfPCell(new Phrase(incomingFileModel.getFileName(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.NORMAL)));
				incomingFileNameData.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
				incomingFileNameData.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
				incomingFileDataTableInfo.addCell(incomingFileNameData);

				PdfPCell incomingFileStatusData = new PdfPCell(new Phrase(incomingFileModel.getStatus(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.NORMAL)));
				incomingFileStatusData.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
				incomingFileStatusData.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
				incomingFileDataTableInfo.addCell(incomingFileStatusData);
				
				PdfPCell incomingFileDateData = new PdfPCell(new Paragraph(dateTimeFormat.format(incomingFileModel.getProcessDate()),FontFactory.getFont(FontFactory.HELVETICA, 9,com.itextpdf.text.Font.BOLD)));
				incomingFileDateData.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
				incomingFileDateData.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
				incomingFileDataTableInfo.addCell(incomingFileDateData);
				
				document.add(incomingFileDataTableInfo);
				document.add(freeline);
			}
		}
	
		PdfPTable totals = new PdfPTable(2);
		totals.setWidthPercentage(100);
			
		PdfPCell totalData = new PdfPCell(new Phrase("TOTAL INCOMING FILES",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		totalData.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		totalData.setBorder(com.itextpdf.text.Rectangle.TOP);
		totals.addCell(totalData);
	
		totalInFiles=incomingFilesList.size();

		PdfPCell totalNrMsgsCount = new PdfPCell(new Phrase(String.valueOf(totalInFiles),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		totalNrMsgsCount.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		totalNrMsgsCount.setBorder(com.itextpdf.text.Rectangle.TOP);
		totals.addCell(totalNrMsgsCount);
		
		document.add(totals);
		document.add(freeline);
		
		PdfPTable reportHeading01 = new PdfPTable(3);
		reportHeading01.setWidthPercentage(100);
		
		PdfPCell outgoingFileName  = new PdfPCell(new Paragraph("Outgoing File Name",FontFactory.getFont(FontFactory.HELVETICA, 9,com.itextpdf.text.Font.BOLD)));
		outgoingFileName.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		outgoingFileName.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
		reportHeading01.addCell(outgoingFileName);
		
		PdfPCell outgoingFileStatus = new PdfPCell(new Paragraph("Status",FontFactory.getFont(FontFactory.HELVETICA, 9,com.itextpdf.text.Font.BOLD)));
		outgoingFileStatus.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		outgoingFileStatus.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
		reportHeading01.addCell(outgoingFileStatus);
		
		PdfPCell outgoingFileDate = new PdfPCell(new Paragraph("Process Date and Time",FontFactory.getFont(FontFactory.HELVETICA, 9,com.itextpdf.text.Font.BOLD)));
		outgoingFileDate.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		outgoingFileDate.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
		reportHeading01.addCell(outgoingFileDate);
		
		document.add(reportHeading01);
		document.add(freeline);
		if(outgoingFilesList!= null && outgoingFilesList.size() > 0)
		{
			for(OpsFileRegModel outgoingFileModel : outgoingFilesList)
			{	
				PdfPTable outgoingFileDataTableInfo = new PdfPTable(3);
				outgoingFileDataTableInfo.setWidthPercentage(100);
				
				PdfPCell outgoingFileNameData = new PdfPCell(new Phrase(outgoingFileModel.getFileName(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.NORMAL)));
				outgoingFileNameData.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
				outgoingFileNameData.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
				outgoingFileDataTableInfo.addCell(outgoingFileNameData);

				PdfPCell outgoingFileStatusData = new PdfPCell(new Phrase(outgoingFileModel.getStatus(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.NORMAL)));
				outgoingFileStatusData.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
				outgoingFileStatusData.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
				outgoingFileDataTableInfo.addCell(outgoingFileStatusData);
				
				PdfPCell incomingFileDateData = new PdfPCell(new Paragraph(dateTimeFormat.format(outgoingFileModel.getProcessDate()),FontFactory.getFont(FontFactory.HELVETICA, 9,com.itextpdf.text.Font.BOLD)));
				incomingFileDateData.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
				incomingFileDateData.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
				outgoingFileDataTableInfo.addCell(incomingFileDateData);
				
				document.add(outgoingFileDataTableInfo);
				document.add(freeline);
			}
		}
			PdfPTable totals01 = new PdfPTable(2);
			totals01.setWidthPercentage(100);
				
			PdfPCell totalincomingData = new PdfPCell(new Phrase("TOTAL OUTGOING FILES",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
			totalincomingData.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
			totalincomingData.setBorder(com.itextpdf.text.Rectangle.TOP);
			totals01.addCell(totalincomingData);
			
			totalOutFiles=outgoingFilesList.size();

			PdfPCell totalIncomingCount = new PdfPCell(new Phrase(String.valueOf(totalOutFiles),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
			totalIncomingCount.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
			totalIncomingCount.setBorder(com.itextpdf.text.Rectangle.TOP);
			totals01.addCell(totalIncomingCount);
				
			document.add(totals01);
			document.add(freeline);
			document.close();
			
			try
			{
				copyFile(pdfFileName);
			}
			catch(IOException ioe)
			{
				log.error("Error on copying MR017 report to temp "+ioe.getMessage());
				ioe.printStackTrace();
			}
			catch(Exception ex)
			{
				log.error("Error on copying MR017 report to temp "+ex.getMessage());
				ex.printStackTrace();
			}
	}
	
	public List<OpsFileRegModel> incomingFiles()
	{
		incomingFilesList  = (List<OpsFileRegModel>) controller.retrieveOpsFileRegbyDerictionCriteria("I");
		//Collections.sort(outgoingFilesList, new fileNameOrderSorter());
		//Collections.sort(incomingFilesList, new fileTypeOrderSorter());
		return incomingFilesList;
	}
	
	public List<OpsFileRegModel> outgoingFiles()
	{
		outgoingFilesList = (List<OpsFileRegModel>) controller.retrieveOpsFileRegbyDerictionCriteria("O");
		//Collections.sort(outgoingFilesList, new fileNameOrderSorter());
		//Collections.sort(outgoingFilesList, new fileTypeOrderSorter());
		return outgoingFilesList;
	}
	
//	private class fileNameOrderSorter implements Comparator<OpsFileRegModel>
//	{
//		@Override
//		public int compare(OpsFileRegModel o1, OpsFileRegModel o2)
//		{
//			if(o1.getFileName().substring(10, 17) == null && o2.getFileName().substring(10, 17) == null)
//			{
//				log.info("fileName memberNo---->"+o1.getFileName().substring(10, 17 ));
//				return 0;
//			}
//			else if(o1.getFileName().substring(10, 17) == null)
//			{
//				return -1;
//			}
//			else if(o2.getFileName().substring(10, 17) == null)
//			{
//				return 1;
//			}
//			return o1.getFileName().substring(10, 17).compareTo(o2.getFileName().substring(10, 17));
//		}
//	}
//	
//	private class fileTypeOrderSorter implements Comparator<OpsFileRegModel>
//	{
//		@Override
//		public int compare(OpsFileRegModel o1, OpsFileRegModel o2)
//		{
//			if(o1.getFileName().substring(0, 8) == null && o2.getFileName().substring(0, 8) == null)
//			{
//				log.info("fileName memberNo---->"+o1.getFileName().substring(0, 8));
//				return 0;
//			}
//			else if(o1.getFileName().substring(0, 8) == null)
//			{
//				return -1;
//			}
//			else if(o2.getFileName().substring(0, 8) == null)
//			{
//				return 1;
//			}
//			return o1.getFileName().substring(0, 8).compareTo(o2.getFileName().substring(0, 8));
//		}
//	}
	
	
	public  void copyFile(String fileName) throws IOException 
	{
		File tmpFile = new File(tempDir, fileName);
		File destFile = new File(reportDir, fileName);
		
		log.info("tmpFile ==> "+ tmpFile);
		log.info("destFile===> "+ destFile);
		
		Files.copy(tmpFile, destFile);
		log.info("Copying "+fileName+" from temp to output directory...");
	}
	
	private static void contextPropertyFileCheck() 
	{
		if (propertyUtilRemote == null) 
		{
			propertyUtilRemote = Util.getPropertyUtil();
		}
	}
}
