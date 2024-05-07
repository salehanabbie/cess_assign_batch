package com.bsva.reports;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DurationFormatUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.bsva.commons.model.SystemParameterModel;
import com.bsva.entities.BatchFileStatsReportEntityModel;
import com.bsva.entities.CasCnfgReportNamesEntity;
import com.bsva.interfaces.AdminBeanRemote;
import com.bsva.interfaces.PropertyUtilRemote;
import com.bsva.interfaces.ReportBeanRemote;
import com.bsva.interfaces.ServiceBeanRemote;
import com.bsva.utils.DateUtil;
import com.bsva.utils.Util;
import com.google.common.io.Files;
import com.itextpdf.text.DocumentException;

/**
 * @author SalehaR
 * Date: 18/06/2021
 */
public class BatchFileStatsReport {
	public static Logger log=Logger.getLogger("PSMD08-BatchFileStatsReport");
	long startTime = System.nanoTime();
	private String fileName;

	public static ServiceBeanRemote beanRemote;
	public static ReportBeanRemote reportBeanRemote;
	private static AdminBeanRemote adminBeanRemote;
	private static PropertyUtilRemote propertyUtilRemote;

	SystemParameterModel systemParameterModel;

	String reportName, reportNr, reportDir = null, tempDir = null;
	int fileSeqNo =000;
	String xlsFileName = null, psmd08 = null;
	Date reportDate = null;

	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	List<BatchFileStatsReportEntityModel> fileStatsList;

	public void generateReport(Date frontEndDate) throws FileNotFoundException, DocumentException
	{
		contextAdminBeanCheck();
		contextCheck();
		contextReportBeanCheck();
		contextPropertyFileCheck();

		systemParameterModel = new SystemParameterModel();
		systemParameterModel =(SystemParameterModel) adminBeanRemote.retrieveWebActiveSysParameter();
		
		if(frontEndDate == null){
			reportDate = systemParameterModel.getProcessDate();
		} else {
			reportDate = frontEndDate;
		}

		try
		{
			tempDir = propertyUtilRemote.getPropValue("ExtractTemp.Out");
			reportDir= propertyUtilRemote.getPropValue("Reports.Output");

			psmd08 = propertyUtilRemote.getPropValue("RPT.PASA.BATCH.FILESTATS");
			log.info("psmd08 from properties: "+psmd08);
		}
		catch(Exception ex)
		{
			log.error("PSMD08- Could not find CessionAssignment.properties in classpath");
			reportDir = "/home/opsjava/Delivery/Cession_Assign/Output/Reports/";
			tempDir="/home/opsjava/Delivery/Cession_Assign/Output/temp/";
		}

		//Retrieve Report Name
		CasCnfgReportNamesEntity reportNameEntity = new CasCnfgReportNamesEntity();
		reportNameEntity = (CasCnfgReportNamesEntity) adminBeanRemote.retrieveReportName(psmd08);

		if (reportNameEntity != null) {
			if (reportNameEntity.getActiveInd().equalsIgnoreCase("Y")) {
				reportNr = reportNameEntity.getReportNr();
				reportName = reportNameEntity.getReportName();

				loadData();

				log.info("*****REPORT " + reportNr + " GENERATING*****");

				if(fileStatsList != null && fileStatsList.size() > 0) {
					try
					{
						generateReportDetail(reportNr, reportName);
					}
					catch(Exception ex)
					{
						log.error("Error on generating PSMD08 report: "+ex.getMessage());
						ex.printStackTrace();
					}
				}
			}
		}

		long endTime = System.nanoTime();
		long duration = (endTime - startTime) / 500000;
		log.info("[PSMD08 Report Duration: "+DurationFormatUtils.formatDuration(duration, "HH:mm:ss.S")+" milliseconds |");
	}

	public void generateReportDetail(String reportNo,String reportname) throws DocumentException, IOException
	{
		fileSeqNo =fileSeqNo + 1;
		String strSeqNo = String.format("%03d",fileSeqNo);

		SimpleDateFormat rptNameFormat = new SimpleDateFormat("ddMMyyyy");
		SimpleDateFormat rptDateFormat = new SimpleDateFormat("EEEEE dd MMMMM yyyy");
		String currentDate = rptNameFormat.format(reportDate);
		//Filenames
		fileName = tempDir+"0000AC"+reportNo+currentDate+"."+strSeqNo + ".xlsx";
		xlsFileName = "0000AC"+reportNo+currentDate+"."+strSeqNo + ".xlsx";

		//XSSF used for MS Excel 2007(.xlxs) and later
		//HSSF used for MS Excel before 2007 (.xls) and earlier

		Workbook wb = new XSSFWorkbook(); //or new HSSFWorkbook();
		Sheet sheet = wb.createSheet("Batch File Stats");

		sheet.setColumnWidth(0, 3500);
		sheet.setColumnWidth(1, 3000);
		sheet.setColumnWidth(2, 500);
		sheet.setColumnWidth(3, 3500);
		sheet.setColumnWidth(4, 3000);
		sheet.setColumnWidth(5, 500);
		sheet.setColumnWidth(6, 3500);
		sheet.setColumnWidth(7, 3000);
		sheet.setColumnWidth(8, 500);
		sheet.setColumnWidth(9, 3500);
		sheet.setColumnWidth(10,3000);

		CellStyle headerCellStyle = wb.createCellStyle();
		headerCellStyle = wb.createCellStyle();
		headerCellStyle.setWrapText(true);
		headerCellStyle.setAlignment(HorizontalAlignment.CENTER_SELECTION);
		headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		headerCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		headerCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		headerCellStyle.setBorderBottom(BorderStyle.THIN);
		headerCellStyle.setBorderTop(BorderStyle.THIN);
		headerCellStyle.setBorderRight(BorderStyle.THIN);
		headerCellStyle.setBorderLeft(BorderStyle.THIN);

		Font headerFont = wb.createFont();
		headerFont.setFontName("Arial");
		headerFont.setBold(true);

		headerCellStyle.setFont(headerFont);


		CellStyle subTitleCellStyle = wb.createCellStyle();
		subTitleCellStyle.setWrapText(true);
		subTitleCellStyle.setAlignment(HorizontalAlignment.CENTER_SELECTION);
		subTitleCellStyle.setVerticalAlignment(VerticalAlignment.TOP);
		subTitleCellStyle.setFont(headerFont);
		subTitleCellStyle.setFillForegroundColor(IndexedColors.LIME.getIndex());
		subTitleCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		subTitleCellStyle.setBorderBottom(BorderStyle.THIN);
		subTitleCellStyle.setBorderTop(BorderStyle.THIN);
		subTitleCellStyle.setBorderRight(BorderStyle.THIN);
		subTitleCellStyle.setBorderLeft(BorderStyle.THIN);


		Font stFont2 = wb.createFont();
		stFont2.setFontName("Arial");
		stFont2.setBold(true);
		stFont2.setFontHeightInPoints((short) 10);
		
		
		CellStyle subTitleCellStyle2 = wb.createCellStyle();
		subTitleCellStyle2.setWrapText(true);
		subTitleCellStyle2.setAlignment(HorizontalAlignment.CENTER_SELECTION);
		subTitleCellStyle2.setVerticalAlignment(VerticalAlignment.TOP);
		subTitleCellStyle2.setFont(headerFont);
		subTitleCellStyle2.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		subTitleCellStyle2.setFillPattern(CellStyle.SOLID_FOREGROUND);
		subTitleCellStyle2.setFont(stFont2);
		subTitleCellStyle2.setBorderBottom(BorderStyle.THIN);
		subTitleCellStyle2.setBorderTop(BorderStyle.THIN);
		subTitleCellStyle2.setBorderRight(BorderStyle.THIN);
		subTitleCellStyle2.setBorderLeft(BorderStyle.THIN);

		Font normalFont = wb.createFont();
		normalFont.setFontName("Arial");
		normalFont.setFontHeightInPoints((short) 10);

		CellStyle normalCenterFontStyle = wb.createCellStyle();
		normalCenterFontStyle.setWrapText(true);
		normalCenterFontStyle.setAlignment(HorizontalAlignment.CENTER_SELECTION);
		normalCenterFontStyle.setVerticalAlignment(VerticalAlignment.TOP);
		normalCenterFontStyle.setFont(normalFont);
		normalCenterFontStyle.setBorderBottom(BorderStyle.THIN);
		normalCenterFontStyle.setBorderTop(BorderStyle.THIN);
		normalCenterFontStyle.setBorderRight(BorderStyle.THIN);
		normalCenterFontStyle.setBorderLeft(BorderStyle.THIN);

		CellStyle memberFontStyle = wb.createCellStyle();
		memberFontStyle.setWrapText(true);
		memberFontStyle.setAlignment(HorizontalAlignment.CENTER_SELECTION);
		memberFontStyle.setVerticalAlignment(VerticalAlignment.TOP);
		memberFontStyle.setFont(stFont2);
		memberFontStyle.setBorderBottom(BorderStyle.THIN);
		memberFontStyle.setBorderTop(BorderStyle.THIN);
		memberFontStyle.setBorderRight(BorderStyle.THIN);
		memberFontStyle.setBorderLeft(BorderStyle.THIN);

		//GENERATE FIRST ROW
		Row titleRow = sheet.createRow(0);
		
		Cell createdDateCell = titleRow.createCell(0);
		createdDateCell.setCellStyle(headerCellStyle);
		
	    
		String day = DateUtil.getDayFullname(reportDate.getDay());
		createdDateCell.setCellValue("BATCH MANDATE FILE STATS FOR PROCESSING DATE: "+rptDateFormat.format(reportDate));
		
		Cell nullCell_1= titleRow.createCell(1);
		nullCell_1.setCellStyle(headerCellStyle);
		Cell nullCell_2= titleRow.createCell(2);
		nullCell_1.setCellStyle(headerCellStyle);
		Cell nullCell_3= titleRow.createCell(3);
		nullCell_1.setCellStyle(headerCellStyle);
		Cell nullCell_4= titleRow.createCell(4);
		nullCell_1.setCellStyle(headerCellStyle);
		Cell nullCell_5= titleRow.createCell(5);
		nullCell_5.setCellStyle(headerCellStyle);
		Cell nullCell_6= titleRow.createCell(6);
		nullCell_6.setCellStyle(headerCellStyle);
		Cell nullCell_7= titleRow.createCell(7);
		nullCell_7.setCellStyle(headerCellStyle);
		Cell nullCell_8= titleRow.createCell(8);
		nullCell_8.setCellStyle(headerCellStyle);
		Cell nullCell_9= titleRow.createCell(9);
		nullCell_9.setCellStyle(headerCellStyle);
		Cell nullCell_10= titleRow.createCell(10);
		nullCell_10.setCellStyle(headerCellStyle);
		
		//Merge Cell 0 and 1
		sheet.addMergedRegion(new CellRangeAddress(0,0, 0, 10));
		
		
		//GENERATE SUB TITLE 
		Row subTitleRow = sheet.createRow(1);
		Cell oneTxnSTCell = subTitleRow.createCell(0);
		oneTxnSTCell.setCellStyle(subTitleCellStyle);
		oneTxnSTCell.setCellValue("Files with 1 Tran");
		
		//Merge Cell 0 and 1
//		sheet.addMergedRegion(rowFrom,rowTo,colFrom,colTo)
		sheet.addMergedRegion(new CellRangeAddress(1,1, 0, 1));
		
		Cell twoToHunTxnSTCell = subTitleRow.createCell(3);
		twoToHunTxnSTCell.setCellStyle(subTitleCellStyle);
		twoToHunTxnSTCell.setCellValue("Files with 2 to 100 Tran");
		
		//Merge Cell 0 and 1
		sheet.addMergedRegion(new CellRangeAddress(1,1, 3, 4));
		
		Cell hunToThousandTxnSTCell = subTitleRow.createCell(6);
		hunToThousandTxnSTCell.setCellStyle(subTitleCellStyle);
		hunToThousandTxnSTCell.setCellValue("Files with 101 to 1000 Tran");
		
		//Merge Cell 0 and 1
		sheet.addMergedRegion(new CellRangeAddress(1,1, 6, 7));
		
		Cell greaterThousandTxnSTCell = subTitleRow.createCell(9);
		greaterThousandTxnSTCell.setCellStyle(subTitleCellStyle);
		greaterThousandTxnSTCell.setCellValue("Files with > 1000 Tran");
		
		//Merge Cell 0 and 1
		sheet.addMergedRegion(new CellRangeAddress(1,1, 9, 10));
		
		//Create Subtitle 2
		Row stRow2 = sheet.createRow(2);
		Cell memNoCell_1 = stRow2.createCell(0);
		memNoCell_1.setCellStyle(subTitleCellStyle2);
		memNoCell_1.setCellValue("MEMBER NO");
		
		Cell nrOfFilesCell_1 = stRow2.createCell(1);
		nrOfFilesCell_1.setCellStyle(subTitleCellStyle2);
		nrOfFilesCell_1.setCellValue("NR OF FILES");
		
		Cell memNoCell_2 = stRow2.createCell(3);
		memNoCell_2.setCellStyle(subTitleCellStyle2);
		memNoCell_2.setCellValue("MEMBER NO");
		
		Cell nrOfFilesCell_2 = stRow2.createCell(4);
		nrOfFilesCell_2.setCellStyle(subTitleCellStyle2);
		nrOfFilesCell_2.setCellValue("NR OF FILES");
		
		Cell memNoCell_3 = stRow2.createCell(6);
		memNoCell_3.setCellStyle(subTitleCellStyle2);
		memNoCell_3.setCellValue("MEMBER NO");
		
		Cell nrOfFilesCell_3 = stRow2.createCell(7);
		nrOfFilesCell_3.setCellStyle(subTitleCellStyle2);
		nrOfFilesCell_3.setCellValue("NR OF FILES");
		
		Cell memNoCell_4 = stRow2.createCell(9);
		memNoCell_4.setCellStyle(subTitleCellStyle2);
		memNoCell_4.setCellValue("MEMBER NO");
		
		Cell nrOfFilesCell_4 = stRow2.createCell(10);
		nrOfFilesCell_4.setCellStyle(subTitleCellStyle2);
		nrOfFilesCell_4.setCellValue("NR OF FILES");
		
		
		//Populate Data
		int rowCount=2;
		BigDecimal oneTxnFileCount = BigDecimal.ZERO,
				   twoToHundCount = BigDecimal.ZERO,
				   hundToThsdCount = BigDecimal.ZERO,
				   grtrThsdCount = BigDecimal.ZERO;
		
		for (BatchFileStatsReportEntityModel fileStatsEntity : fileStatsList) {
			rowCount++;
			Row dataRow = sheet.createRow(rowCount);
			
			//One Txn Files
			Cell memberDataCell_0 = dataRow.createCell(0);
			memberDataCell_0.setCellStyle(memberFontStyle);
			memberDataCell_0.setCellValue(fileStatsEntity.getMemberNo());
			
			oneTxnFileCount = oneTxnFileCount.add(fileStatsEntity.getNrOfFiles_1());
			
			Cell nrFilesCell_1 =  dataRow.createCell(1);
			nrFilesCell_1.setCellStyle(normalCenterFontStyle);
			nrFilesCell_1.setCellType(CellType.NUMERIC);
			nrFilesCell_1.setCellValue(fileStatsEntity.getNrOfFiles_1().intValue());

			
//			NCH-252230 Changed to 
//			//Two to Hundred Txn Files
//			Cell memberDataCell_3 = dataRow.createCell(3);
//			memberDataCell_3.setCellStyle(memberFontStyle);
//			memberDataCell_3.setCellValue(fileStatsEntity.getMemberNo());
//			
//			twoToHundCount = twoToHundCount.add(fileStatsEntity.getNrOfFiles_2to100());
//			
//			Cell nrFilesCell_4 =  dataRow.createCell(4);
//			nrFilesCell_4.setCellStyle(normalCenterFontStyle);
//			nrFilesCell_4.setCellType(CellType.NUMERIC);
//			nrFilesCell_4.setCellValue(fileStatsEntity.getNrOfFiles_2to100().intValue());
//			
//			//101 to 1000 Txn Files
//			Cell memberDataCell_6 = dataRow.createCell(6);
//			memberDataCell_6.setCellStyle(memberFontStyle);
//			memberDataCell_6.setCellValue(fileStatsEntity.getMemberNo());
//			
//			hundToThsdCount = hundToThsdCount.add(fileStatsEntity.getNrOfFiles_101to1000());
//			
//			Cell nrFilesCell_7 =  dataRow.createCell(7);
//			nrFilesCell_7.setCellStyle(normalCenterFontStyle);
//			nrFilesCell_7.setCellType(CellType.NUMERIC);
//			nrFilesCell_7.setCellValue(fileStatsEntity.getNrOfFiles_101to1000().intValue());
//			
//			//Greater than 1000 Txn Files
//			Cell memberDataCell_9 = dataRow.createCell(9);
//			memberDataCell_9.setCellStyle(memberFontStyle);
//			memberDataCell_9.setCellValue(fileStatsEntity.getMemberNo());
//			
//			grtrThsdCount = grtrThsdCount.add(fileStatsEntity.getNrOfFiles_grtr_1000());
//			
//			Cell nrFilesCell_10 =  dataRow.createCell(10);
//			nrFilesCell_10.setCellStyle(normalCenterFontStyle);
//			nrFilesCell_10.setCellType(CellType.NUMERIC);
//			nrFilesCell_10.setCellValue(fileStatsEntity.getNrOfFiles_grtr_1000().intValue());
		}

		//Create Totals Row
		rowCount++;
		Row totalRow = sheet.createRow(rowCount);
		
		//One Txn Files
		Cell totalCell0 = totalRow.createCell(0);
		totalCell0.setCellStyle(subTitleCellStyle2);
		totalCell0.setCellValue("Total");
		
		Cell nrFilesCell_1 =  totalRow.createCell(1);
		nrFilesCell_1.setCellStyle(memberFontStyle);
		nrFilesCell_1.setCellType(CellType.NUMERIC);
		nrFilesCell_1.setCellValue(oneTxnFileCount.intValue());
		
		//Two to Hundred Txn Files
		Cell totalCell3 = totalRow.createCell(3);
		totalCell3.setCellStyle(subTitleCellStyle2);
		totalCell3.setCellValue("Total");
		
		Cell nrFilesCell_4 =  totalRow.createCell(4);
		nrFilesCell_4.setCellStyle(memberFontStyle);
		nrFilesCell_4.setCellType(CellType.NUMERIC);
		nrFilesCell_4.setCellValue(twoToHundCount.intValue());
		
		//101 to 1000 Txn Files
		Cell totalCell6 = totalRow.createCell(6);
		totalCell6.setCellStyle(subTitleCellStyle2);
		totalCell6.setCellValue("Total");
		
		Cell nrFilesCell_7 =  totalRow.createCell(7);
		nrFilesCell_7.setCellStyle(memberFontStyle);
		nrFilesCell_7.setCellType(CellType.NUMERIC);
		nrFilesCell_7.setCellValue(hundToThsdCount.intValue());
		
		//Greater than 1000 Txn Files
		Cell totalCell9 = totalRow.createCell(9);
		totalCell9.setCellStyle(subTitleCellStyle2);
		totalCell9.setCellValue("Total");
		
		Cell nrFilesCell_10 =  totalRow.createCell(10);
		nrFilesCell_10.setCellStyle(memberFontStyle);
		nrFilesCell_10.setCellType(CellType.NUMERIC);
		nrFilesCell_10.setCellValue(grtrThsdCount.intValue());
		
		// Write the output to a file
		FileOutputStream fileOut = new FileOutputStream(fileName);
		wb.write(fileOut);
		fileOut.close();
		
		// Copy the report to the Output reports folder
		try {
			copyFile(xlsFileName);
		} catch (IOException ioe) {
			log.error("Error on copying PSMD08 report to temp " + ioe.getMessage());
			ioe.printStackTrace();
		} catch (Exception ex) {
			log.error("Error on copying PSMD08 report to temp " + ex.getMessage());
			ex.printStackTrace();
		}

	}

	public void loadData()
	{
		fileStatsList = new ArrayList<BatchFileStatsReportEntityModel>();

		String procDate = dateFormat.format(reportDate);
		//Retrieve File Data from DB
		fileStatsList = (List<BatchFileStatsReportEntityModel>) reportBeanRemote.retrieveBatchFileStats(procDate);

	}
	
	public  void copyFile(String fileName) throws IOException 
	{
		File tmpFile = new File(tempDir, fileName);
		File destFile = new File(reportDir, fileName);

		Files.copy(tmpFile, destFile);
		log.info("Copying "+fileName+" from temp to output directory...");
	}
	
	//CONTEXT LOOKUPS//
	private static void contextCheck() {
		if (beanRemote == null) {
			beanRemote = Util.getServiceBean();
		}
	}

	private static void contextAdminBeanCheck() 
	{
		if (adminBeanRemote == null) 
		{
			adminBeanRemote = Util.getAdminBean();	
		}
	}

	private static void contextPropertyFileCheck() 
	{
		if (propertyUtilRemote == null) 
		{
			propertyUtilRemote = Util.getPropertyUtil();
		}
	}

	private static void contextReportBeanCheck() {
		if (reportBeanRemote == null) {
			reportBeanRemote = Util.getReportBean();
		}
	}

}
