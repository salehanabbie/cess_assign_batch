package com.bsva.panels.reports;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
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
import com.bsva.commons.model.MandatesCountCommonsModel;
import com.bsva.commons.model.MandatesExtractReportModel;
import com.bsva.commons.model.MandatesRejectedReportModel;
import com.bsva.commons.model.OpsFileRegModel;
import com.bsva.commons.model.OutBalanceCountReportModel;
import com.bsva.commons.model.OutofBalanceModel;
import com.bsva.commons.model.SysctrlCompParamModel;
import com.bsva.commons.model.SystemParameterModel;
import com.bsva.controller.Controller;
import com.bsva.interfaces.PropertyUtilRemote;
import com.bsva.utils.Util;
import com.google.common.io.Files;

/**
 * @author ElelwaniR
 */
public class OutOfBalanceReport


{

	public static Logger log=Logger.getLogger(OutOfBalanceReport.class);

	Date currentDate = new Date();
	private String downloaddirectory ="/home/opsjava/Delivery/Mandates/Reports/";
	private static PropertyUtilRemote propertyUtilRemote;
	String tempDir, reportDir,files,pdfFileName;


	Controller controller  = new Controller();
	
	List<OutBalanceCountReportModel> countsReportInfoList = new ArrayList<OutBalanceCountReportModel>();


	List<MandatesCountCommonsModel> extractedList = new ArrayList<MandatesCountCommonsModel>();
	List<MandatesCountCommonsModel> receivedList = new ArrayList<MandatesCountCommonsModel>();
	List<OpsFileRegModel> OutofBalanceModelOutGoingList = new ArrayList<OpsFileRegModel>();
	List<OutofBalanceModel> OutofBalanceModelIncomingList = new ArrayList<OutofBalanceModel>();
	List<MandatesExtractReportModel> mandatsReportModelList = new ArrayList<MandatesExtractReportModel>();
	List<MandatesRejectedReportModel> mandatesRejectedModelList = new ArrayList<MandatesRejectedReportModel>();
	List<OutBalanceCountReportModel> outBalanceCountReportModelList = new ArrayList<OutBalanceCountReportModel>();
	List<OutBalanceCountReportModel> rejectCountDataList = new ArrayList<OutBalanceCountReportModel>();
	List<OutBalanceCountReportModel> extractedCountDataList = new ArrayList<OutBalanceCountReportModel>();

	public void generateReport(String reportNr, String reportname) throws FileNotFoundException, DocumentException
	{
		log.info("in the generate report method ");
		contextPropertyFileCheck();
		calculateCountsReportInfo();
		
		try
		{
			tempDir = propertyUtilRemote.getPropValue("ExtractTemp.Out");
			reportDir= propertyUtilRemote.getPropValue("Reports.Output");
			log.info("tempDir ==> "+tempDir);
			log.info("reportDir ==> "+reportDir);
		}
		catch(Exception ex)
		{
			log.error("mr008- Could not find MandateMessageCommons.properties in classpath");	
			reportDir = "/home/opsjava/Delivery/Mandates/Output/Reports/";
			tempDir="/home/opsjava/Delivery/Mandates/Output/temp/";
			
		}
		generateReportColumns(reportNr,reportname);
	}




	public void generateReportColumns(String reportNo,String reportname ) throws FileNotFoundException, DocumentException
	{
		log.info("The report cloumns method ****************");
		DecimalFormat df = new DecimalFormat("### ### ### ### ### ##0.00");
		DateFormat dateTimeFormat = new SimpleDateFormat("yyyyMMdd_HH:mm:ss");
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat fileTimeFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
		Date currentDate = new Date();


		int pageNo = 1;

	     files = tempDir+reportNo+"-"+reportname.toUpperCase()+"_"+fileTimeFormat.format(currentDate).toString()+".pdf";
	     pdfFileName =reportNo+"-"+reportname.toUpperCase()+"_"+fileTimeFormat.format(currentDate).toString()+".pdf";
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

		PdfPCell cell1 = new PdfPCell(new Paragraph(reportNo+ "-"+ reportname.toUpperCase(), FontFactory.getFont( FontFactory.HELVETICA, 9, com.itextpdf.text.Font.BOLD)));
		cell1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		cell1.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
		thirdHeading.addCell(cell1);

		document.add(thirdHeading);
		document.add(freeline);


		//Out of balance incoming files details 


		PdfPTable reportHeading = new PdfPTable(7);
		reportHeading.setWidthPercentage(100);

		PdfPCell serviceIdIn  = new PdfPCell(new Paragraph("Service Id In",FontFactory.getFont(FontFactory.HELVETICA, 9,com.itextpdf.text.Font.BOLD)));
		serviceIdIn.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		serviceIdIn.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
		reportHeading.addCell(serviceIdIn);
		
		PdfPCell totalNrOfMsgs = new PdfPCell(new Paragraph("Total Nr of Txns",FontFactory.getFont(FontFactory.HELVETICA, 9,com.itextpdf.text.Font.BOLD)));
		totalNrOfMsgs.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		totalNrOfMsgs.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
		reportHeading.addCell(totalNrOfMsgs);
		
		PdfPCell totalAccptd = new PdfPCell(new Paragraph("Accepted Txns",FontFactory.getFont(FontFactory.HELVETICA, 9,com.itextpdf.text.Font.BOLD)));
		totalAccptd.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		totalAccptd.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
		reportHeading.addCell(totalAccptd);
		
		PdfPCell totalRjctd= new PdfPCell(new Paragraph("Rejected Txns",FontFactory.getFont(FontFactory.HELVETICA, 9,com.itextpdf.text.Font.BOLD)));
		totalRjctd.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		totalRjctd.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
		reportHeading.addCell(totalRjctd);
		
		PdfPCell serviceIdOut= new PdfPCell(new Paragraph("Service Id Out",FontFactory.getFont(FontFactory.HELVETICA, 9,com.itextpdf.text.Font.BOLD)));
		serviceIdOut.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		serviceIdOut.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
		reportHeading.addCell(serviceIdOut);
		
		PdfPCell totalExtd= new PdfPCell(new Paragraph("Extracted Txns",FontFactory.getFont(FontFactory.HELVETICA, 9,com.itextpdf.text.Font.BOLD)));
		totalExtd.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		totalExtd.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
		reportHeading.addCell(totalExtd);
		
		PdfPCell diff = new PdfPCell(new Paragraph("Difference",FontFactory.getFont(FontFactory.HELVETICA, 9,com.itextpdf.text.Font.BOLD)));
		diff.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		diff.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
		reportHeading.addCell(diff);
		
		document.add(reportHeading);
		document.add(freeline);
		
		BigDecimal totalNrMsgs = BigDecimal.ZERO;
		BigDecimal totalAccepted = BigDecimal.ZERO;
		BigDecimal totalRejected = BigDecimal.ZERO;
		BigDecimal totalExtracted = BigDecimal.ZERO;
		BigDecimal totalDiff = BigDecimal.ZERO;
		
		//Populate the Counts Report Info
		if(countsReportInfoList != null && countsReportInfoList.size() > 0)
		{
			for (OutBalanceCountReportModel countsModel : countsReportInfoList) 
			{
				log.debug("countsModel ==> "+ countsModel);
				
				PdfPTable incomingFileDataTableInfo = new PdfPTable(7);
				incomingFileDataTableInfo.setWidthPercentage(100);

				PdfPCell serviceIdInData = new PdfPCell(new Phrase(countsModel.getInServiceId(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.NORMAL)));
				serviceIdInData.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
				serviceIdInData.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
				incomingFileDataTableInfo.addCell(serviceIdInData);

				totalNrMsgs = totalNrMsgs.add(countsModel.getTotalNrOfMsgs());
				
				PdfPCell totalNrMsgsData = new PdfPCell(new Phrase(countsModel.getTotalNrOfMsgs().toString(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.NORMAL)));
				totalNrMsgsData.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
				totalNrMsgsData.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
				incomingFileDataTableInfo.addCell(totalNrMsgsData);
				
				totalAccepted = totalAccepted.add(countsModel.getNrMsgsAccepted());
				
				PdfPCell accpMsgsData = new PdfPCell(new Phrase(countsModel.getNrMsgsAccepted().toString(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.NORMAL)));
				accpMsgsData.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
				accpMsgsData.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
				incomingFileDataTableInfo.addCell(accpMsgsData);
				
				totalRejected = totalRejected.add(countsModel.getNrMsgsRejected());
				
				PdfPCell rejMsgsData = new PdfPCell(new Phrase(countsModel.getNrMsgsRejected().toString(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.NORMAL)));
				rejMsgsData.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
				rejMsgsData.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
				incomingFileDataTableInfo.addCell(rejMsgsData);
				
				String outServiceId = null;
				if(countsModel.getExtServiceId() != null)
					outServiceId = countsModel.getExtServiceId();
				else
					outServiceId = countsModel.getOutServiceId();
				
				PdfPCell serviceIdOutData = new PdfPCell(new Phrase(outServiceId,FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.NORMAL)));
				serviceIdOutData.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
				serviceIdOutData.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
				incomingFileDataTableInfo.addCell(serviceIdOutData);
				
				totalExtracted = totalExtracted.add(countsModel.getNrMsgsExtracted());
				
				PdfPCell extMsgsData = new PdfPCell(new Phrase(countsModel.getNrMsgsExtracted().toString(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.NORMAL)));
				extMsgsData.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
				extMsgsData.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
				incomingFileDataTableInfo.addCell(extMsgsData);
				
				totalDiff = totalDiff.add(countsModel.getDifference());
				
				PdfPCell differenceData = new PdfPCell(new Phrase(countsModel.getDifference().toString(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
				differenceData.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
				differenceData.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
				incomingFileDataTableInfo.addCell(differenceData);
				
				document.add(incomingFileDataTableInfo);
				
			}
		}
		
		document.add(freeline); 
		document.add(freeline);


		PdfPTable totalsRow = new PdfPTable(7);
		totalsRow.setWidthPercentage(100);
		

		PdfPCell totalData = new PdfPCell(new Phrase("TOTALS",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		totalData.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		totalData.setBorder(com.itextpdf.text.Rectangle.TOP);
		totalsRow.addCell(totalData);

		PdfPCell totalNrMsgsCount = new PdfPCell(new Phrase(String.valueOf(totalNrMsgs),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		totalNrMsgsCount.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
		totalNrMsgsCount.setBorder(com.itextpdf.text.Rectangle.TOP);
		totalsRow.addCell(totalNrMsgsCount);
		
		PdfPCell totalAccpData = new PdfPCell(new Phrase(String.valueOf(totalAccepted),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		totalAccpData.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
		totalAccpData.setBorder(com.itextpdf.text.Rectangle.TOP);
		totalsRow.addCell(totalAccpData);
		
		PdfPCell totalRejData = new PdfPCell(new Phrase(String.valueOf(totalRejected),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		totalRejData.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
		totalRejData.setBorder(com.itextpdf.text.Rectangle.TOP);
		totalsRow.addCell(totalRejData);

		PdfPCell totalServiceOut = new PdfPCell(new Phrase(" ",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		totalServiceOut.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
		totalServiceOut.setBorder(com.itextpdf.text.Rectangle.TOP);
		totalsRow.addCell(totalServiceOut);
		
		PdfPCell totalExtData = new PdfPCell(new Phrase(String.valueOf(totalExtracted),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		totalExtData.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
		totalExtData.setBorder(com.itextpdf.text.Rectangle.TOP);
		totalsRow.addCell(totalExtData);
		
		PdfPCell totalDiffData = new PdfPCell(new Phrase(String.valueOf(totalDiff),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		totalDiffData.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
		totalDiffData.setBorder(com.itextpdf.text.Rectangle.TOP);
		totalsRow.addCell(totalDiffData);
		
		document.add(totalsRow);
		document.add(freeline);
		
		
////				PdfPTable totSubHeading = new PdfPTable(1);
////				totSubHeading.setWidthPercentage(100);
////
////				PdfPCell totalSubHeadingFiles = new PdfPCell(new Phrase("Total system sums the nr of records as : ", FontFactory.getFont(FontFactory.HELVETICA, 9, com.itextpdf.text.Font.BOLD)));
////				totalSubHeadingFiles.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
////				totalSubHeadingFiles.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
////				totSubHeading.addCell(totalSubHeadingFiles);
////
////				document.add(totSubHeading);
////
////				document.add(freeline);
////				
////				PdfPTable totnrofIncomingFilesHeading = new PdfPTable(2);
////				totnrofIncomingFilesHeading.setWidthPercentage(100);
////
////				PdfPCell totalReceivedFiles = new PdfPCell(new Phrase("Total Accepted: ", FontFactory.getFont(FontFactory.HELVETICA, 9, com.itextpdf.text.Font.BOLD)));
////				totalReceivedFiles.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
////				totalReceivedFiles.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
////				totnrofIncomingFilesHeading.addCell(totalReceivedFiles);
////
////				if(outBalanceCountReportModelList.size()> 0)
////				{
////					for( OutBalanceCountReportModel outBalanceCountReportModel :outBalanceCountReportModelList)
////					{
////						if(outBalanceCountReportModel.getNrMsgsAccepted()== null)
////						{
////							outBalanceCountReportModel.setNrMsgsAccepted(new BigDecimal("0"));
////						}
////						else
////						{
////							PdfPCell totalIncomingFiles = new PdfPCell(new Phrase(String.valueOf(outBalanceCountReportModel.getNrMsgsAccepted()),FontFactory.getFont(FontFactory.HELVETICA,9, com.itextpdf.text.Font.BOLD)));
////							totalIncomingFiles.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
////							totalIncomingFiles.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
////							totnrofIncomingFilesHeading.addCell(totalIncomingFiles);
////						}
////					}
////				}
////				document.add(totnrofIncomingFilesHeading);
////				document.add(freeline);
////
////
////				
////				PdfPTable totnrofOutOfGoingFilesHeading = new PdfPTable(2);
////				totnrofOutOfGoingFilesHeading.setWidthPercentage(100);
////
////				PdfPCell totalExtractedFiles = new PdfPCell(new Phrase("Total Extracted: ", FontFactory.getFont(FontFactory.HELVETICA, 9, com.itextpdf.text.Font.BOLD)));
////				totalExtractedFiles.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
////				totalExtractedFiles.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
////				totnrofOutOfGoingFilesHeading.addCell(totalExtractedFiles);
////
////				if(extractedCountDataList.size()> 0)
////				{
////					for( OutBalanceCountReportModel outBalanceCountReportModel :extractedCountDataList)
////					{
////						if(outBalanceCountReportModel.getNrMsgsExtracted()== null)
////						{
////							outBalanceCountReportModel.setNrMsgsExtracted(new BigDecimal("0"));
////						}
////						else
////						{
////				
////						PdfPCell totalExtractedValueFiles = new PdfPCell(new Phrase(String.valueOf(outBalanceCountReportModel.getNrMsgsExtracted()),FontFactory.getFont(FontFactory.HELVETICA,9, com.itextpdf.text.Font.BOLD)));
////						totalExtractedValueFiles.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
////						totalExtractedValueFiles.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
////						totnrofOutOfGoingFilesHeading.addCell(totalExtractedValueFiles);
////						}
////					}
////				}
////					document.add(totnrofOutOfGoingFilesHeading);
////					document.add(freeline);
////
////				
////
////
////				PdfPTable totnrofRejectFilesHeading = new PdfPTable(2);
////				totnrofRejectFilesHeading.setWidthPercentage(100);
////
////				PdfPCell totalRejectFiles = new PdfPCell(new Phrase("Total Rejected: ", FontFactory.getFont(FontFactory.HELVETICA, 9, com.itextpdf.text.Font.BOLD)));
////				totalRejectFiles .setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
////				totalRejectFiles.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
////				totnrofRejectFilesHeading.addCell(totalRejectFiles);
////				
////				if(rejectCountDataList.size()> 0)
////				{
////					for( OutBalanceCountReportModel outBalanceCountReportModel :rejectCountDataList)
////					{
////
////						if(outBalanceCountReportModel.getNrMsgsRejected()== null)
////						{
////							outBalanceCountReportModel.setNrMsgsRejected(new BigDecimal("0"));
////						}
////						else{
////
////						PdfPCell totalRejectCoutFiles = new PdfPCell(new Phrase(String.valueOf(outBalanceCountReportModel.getNrMsgsRejected()),FontFactory.getFont(FontFactory.HELVETICA,9, com.itextpdf.text.Font.BOLD)));
////						totalRejectCoutFiles.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
////						totalRejectCoutFiles.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
////						totnrofRejectFilesHeading.addCell(totalRejectCoutFiles);
////						
////						}
////						
////					}
////
////					
////				}
////				document.add(totnrofRejectFilesHeading);
////				document.add(freeline);
////				
////				
////
////				int difference = 0;
////				int totalExtracted = 0; 
////				int totalReceived = 0 , totalRejected = 0;
////
////
////				if(outBalanceCountReportModelList.size()> 0)
////				{
////					for( OutBalanceCountReportModel outBalanceCountReportModel :outBalanceCountReportModelList)
////					{
////						if(outBalanceCountReportModel != null && outBalanceCountReportModel.getNrMsgsAccepted() != null)
////						totalReceived = outBalanceCountReportModel.getNrMsgsAccepted().intValue();
////					}
////				}
////
////				if(extractedCountDataList.size()> 0)
////				{
////					for( OutBalanceCountReportModel outBalanceCountReportModel :extractedCountDataList)
////					{
////						if(outBalanceCountReportModel != null && outBalanceCountReportModel.getNrMsgsExtracted() != null)
////						totalExtracted= outBalanceCountReportModel.getNrMsgsExtracted().intValue();
////						
////					}
////
////				}
////
////				if(rejectCountDataList.size()> 0)
////				{
////					for( OutBalanceCountReportModel outBalanceCountReportModel :rejectCountDataList)
////					{
////						if(outBalanceCountReportModel != null && outBalanceCountReportModel.getNrMsgsRejected() != null)
////						totalRejected =outBalanceCountReportModel.getNrMsgsRejected().intValue();
////					}
////
////				}
////
////				difference = totalReceived -(totalExtracted + totalRejected);
////				PdfPTable differenceHeading = new PdfPTable(2);
////				differenceHeading.setWidthPercentage(100);
////
////				PdfPCell DifferenceLabel = new PdfPCell(new Phrase("Difference: ", FontFactory.getFont(FontFactory.HELVETICA, 9, com.itextpdf.text.Font.BOLD)));
////				DifferenceLabel                .setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
////				DifferenceLabel.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
////				differenceHeading.addCell(DifferenceLabel);
////
////				PdfPCell differenceValue = new PdfPCell(new Phrase(String.valueOf(difference),FontFactory.getFont(FontFactory.HELVETICA,9, com.itextpdf.text.Font.BOLD)));
////				differenceValue.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
////				differenceValue.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
////				differenceHeading.addCell(differenceValue);
////				document.add(differenceHeading);
//
//				document.add(freeline);
//				document.add(freeline);
				document.close();
				
				try
				{
					copyFile(pdfFileName);
				}
				catch(IOException ioe)
				{
					log.error("Error on copying MR008 report to temp "+ioe.getMessage());
					ioe.printStackTrace();
				}
				catch(Exception ex)
				{
					log.error("Error on copying MR008 report to temp "+ex.getMessage());
					ex.printStackTrace();
				}
	}   

//	public List<OutofBalanceModel> retrieveIncomingMessages ()
//	{
//		log.info("##############################Retrieving incoming files");
//		OutofBalanceModelIncomingList=(List<OutofBalanceModel>) controller.retrieveNrOfIncomingFiles();
//		log.info("The list of incoming messages has the following information *****************************"+OutofBalanceModelIncomingList);
//		return OutofBalanceModelIncomingList;
//	}
//
//	public List<MandatesExtractReportModel> retriveExtractReportData()
//	{
//
//		mandatsReportModelList =(List<MandatesExtractReportModel>) controller.retriveExtractReportData();
//		log.info("The list of extracted messages has the following information *****************************"+mandatsReportModelList);
//		return mandatsReportModelList;
//	}
//
//	public List<MandatesRejectedReportModel> retrieveRejectedReportData()
//	{
//		mandatesRejectedModelList = (List<MandatesRejectedReportModel>)controller.retrieveRejectedReportData();
//		log.info("The list of rejected messages has the following information *****************************"+mandatsReportModelList);
//		return mandatesRejectedModelList;
//	}
//
//	public List<OutBalanceCountReportModel> retrieveAcceptedCountData()
//
//	{
//		outBalanceCountReportModelList =(List<OutBalanceCountReportModel>)controller.retrieveAcceptedCountData();
//		return outBalanceCountReportModelList;
//	}
//
//	public List<OutBalanceCountReportModel> retrieveRejectedCountData()
//
//	{
//		rejectCountDataList =(List<OutBalanceCountReportModel>)controller.retrieveRejectedCountData();
//		return rejectCountDataList;
//	}
//
//
//	public List<OutBalanceCountReportModel> retrieveExtractedCountData()
//
//	{
//		extractedCountDataList =(List<OutBalanceCountReportModel>)controller.retrieveExtractedCountData();
//		return extractedCountDataList;
//	}
//
//
//	public List<OpsFileRegModel> retrieveOutGoingMessages ()
//	{
//		log.info("Retrieving outgoing files ######################################");
//		OutofBalanceModelOutGoingList=(List<OpsFileRegModel>) controller.retrieveNrOfOutgoingFiles();
//		log.info("The list of outgpong messages  has the following information*****************************"+OutofBalanceModelOutGoingList);
//		return OutofBalanceModelOutGoingList;
//	}
//
//	public List<MandatesCountCommonsModel> retrieveTotalReceived()
//	{
//		receivedList = (List<MandatesCountCommonsModel>) controller.viewMandatesCountIncomingMessages();
//		return receivedList; 
//	}
//
//
//
//	public List<MandatesCountCommonsModel> retrieveTotalExtracted()
//	{
//		extractedList =(List<MandatesCountCommonsModel>) controller.viewMandatesCountOutGoingMessages();
//		return extractedList; 
//	}


	public List<OutBalanceCountReportModel> calculateCountsReportInfo()
	{
		countsReportInfoList=(List<OutBalanceCountReportModel>) controller.calculateCountsReportInfo();
		log.debug("countsReportInfoList ==> "+countsReportInfoList);
		return countsReportInfoList;
	}
	
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



