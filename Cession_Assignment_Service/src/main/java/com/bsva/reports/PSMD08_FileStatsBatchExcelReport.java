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
public class PSMD08_FileStatsBatchExcelReport {
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
			log.error("PSMD08- Could not find MandateMessageCommons.properties in classpath");	
			reportDir = "/home/opsjava/Delivery/Mandates/Output/Reports/";
			tempDir="/home/opsjava/Delivery/Mandates/Output/temp/";
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
		sheet.setColumnWidth(1, 8000);
		sheet.setColumnWidth(2, 4500);
		sheet.setColumnWidth(3, 3500);
		sheet.setColumnWidth(4, 3500);
		sheet.setColumnWidth(5, 3500);
		sheet.setColumnWidth(6, 3500);
		sheet.setColumnWidth(7, 3500);
		sheet.setColumnWidth(8, 3500);
		sheet.setColumnWidth(9, 3500);
		sheet.setColumnWidth(10, 3500);
		sheet.setColumnWidth(11,3500);
		sheet.setColumnWidth(12,3500);
		sheet.setColumnWidth(13,3500);
		sheet.setColumnWidth(14,3500);

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

		CellStyle normalFontStyle = wb.createCellStyle();
		normalFontStyle.setWrapText(true);
		normalFontStyle.setVerticalAlignment(VerticalAlignment.TOP);
		normalFontStyle.setFont(normalFont);
		normalFontStyle.setBorderBottom(BorderStyle.THIN);
		normalFontStyle.setBorderTop(BorderStyle.THIN);
		normalFontStyle.setBorderRight(BorderStyle.THIN);
		normalFontStyle.setBorderLeft(BorderStyle.THIN);

		//GENERATE FIRST ROW
		Row titleRow = sheet.createRow(0);

		Cell createdDateCell = titleRow.createCell(0);
		createdDateCell.setCellStyle(headerCellStyle);

		String day = DateUtil.getDayFullname(reportDate.getDay());
		createdDateCell.setCellValue("BATCH MANDATE FILE STATS FOR PROCESSING DATE: "+rptDateFormat.format(reportDate));

		Cell nullCell_2= titleRow.createCell(1);
		nullCell_2.setCellStyle(headerCellStyle);
		Cell nullCell_3= titleRow.createCell(2);
		nullCell_3.setCellStyle(headerCellStyle);
		Cell nullCell_4= titleRow.createCell(3);
		nullCell_4.setCellStyle(headerCellStyle);
		Cell nullCell_5= titleRow.createCell(4);
		nullCell_5.setCellStyle(headerCellStyle);
		Cell nullCell_6= titleRow.createCell(5);
		nullCell_6.setCellStyle(headerCellStyle);
		Cell nullCell_7= titleRow.createCell(6);
		nullCell_7.setCellStyle(headerCellStyle);
		Cell nullCell_8= titleRow.createCell(7);
		nullCell_8.setCellStyle(headerCellStyle);
		Cell nullCell_9= titleRow.createCell(8);
		nullCell_9.setCellStyle(headerCellStyle);
		Cell nullCell_10= titleRow.createCell(9);
		nullCell_10.setCellStyle(headerCellStyle);
		Cell nullCell_11= titleRow.createCell(10);
		nullCell_11.setCellStyle(headerCellStyle);
		Cell nullCell_12= titleRow.createCell(11);
		nullCell_12.setCellStyle(headerCellStyle);
		Cell nullCell_13= titleRow.createCell(12);
		nullCell_13.setCellStyle(headerCellStyle);
		Cell nullCell_14= titleRow.createCell(13);
		nullCell_14.setCellStyle(headerCellStyle);
		Cell nullCell_15= titleRow.createCell(14);
		nullCell_15.setCellStyle(headerCellStyle);

		//Merge Cell 0 and 1
		sheet.addMergedRegion(new CellRangeAddress(0,0, 0, 14));

		//GENERATE SUB TITLE 
		Row subTitleRow = sheet.createRow(1);

		Cell memberNoSTCell = subTitleRow.createCell(0);
		memberNoSTCell.setCellStyle(subTitleCellStyle);
		memberNoSTCell.setCellValue("MEMBER NO");

		Cell memberNameSTCell = subTitleRow.createCell(1);
		memberNameSTCell.setCellStyle(subTitleCellStyle);
		memberNameSTCell.setCellValue("MEMBER NAME");

		Cell reqRespSTCell = subTitleRow.createCell(2);
		reqRespSTCell.setCellStyle(subTitleCellStyle);
		reqRespSTCell.setCellValue("REQ/RESP");

		Cell serviceIdSTCell = subTitleRow.createCell(3);
		serviceIdSTCell.setCellStyle(subTitleCellStyle);
		serviceIdSTCell.setCellValue("SERVICE ID");

		Cell oneTxnSTCell = subTitleRow.createCell(4);
		oneTxnSTCell.setCellStyle(subTitleCellStyle);
		oneTxnSTCell.setCellValue("1 TRAN");

		Cell two10TxnSTCell = subTitleRow.createCell(5);
		two10TxnSTCell.setCellStyle(subTitleCellStyle);
		two10TxnSTCell.setCellValue("2-10 TRANS");

		Cell eleven50TxnSTCell = subTitleRow.createCell(6);
		eleven50TxnSTCell.setCellStyle(subTitleCellStyle);
		eleven50TxnSTCell.setCellValue("11-50 TRANS");

		Cell fifty100TxnSTCell = subTitleRow.createCell(7);
		fifty100TxnSTCell.setCellStyle(subTitleCellStyle);
		fifty100TxnSTCell.setCellValue("51-100 TRANS");

		Cell hundred500TxnSTCell = subTitleRow.createCell(8);
		hundred500TxnSTCell.setCellStyle(subTitleCellStyle);
		hundred500TxnSTCell.setCellValue("101-500 TRANS");

		Cell fiveHun1000TxnSTCell = subTitleRow.createCell(9);
		fiveHun1000TxnSTCell.setCellStyle(subTitleCellStyle);
		fiveHun1000TxnSTCell.setCellValue("501-1000 TRANS");

		Cell thousand5000TxnSTCell = subTitleRow.createCell(10);
		thousand5000TxnSTCell.setCellStyle(subTitleCellStyle);
		thousand5000TxnSTCell.setCellValue("1001-5000 TRANS");

		Cell five10000TxnSTCell = subTitleRow.createCell(11);
		five10000TxnSTCell.setCellStyle(subTitleCellStyle);
		five10000TxnSTCell.setCellValue("5001-10000 TRANS");

		Cell tenThou20000TxnSTCell = subTitleRow.createCell(12);
		tenThou20000TxnSTCell.setCellStyle(subTitleCellStyle);
		tenThou20000TxnSTCell.setCellValue("10001-20000 TRANS");

		Cell greater20000TxnSTCell = subTitleRow.createCell(13);
		greater20000TxnSTCell.setCellStyle(subTitleCellStyle);
		greater20000TxnSTCell.setCellValue("> 20000 TRANS");

		Cell subTotalSTCell = subTitleRow.createCell(14);
		subTotalSTCell.setCellStyle(subTitleCellStyle);
		subTotalSTCell.setCellValue("SUB TOTAL");


		//Populate Data
		int rowCount=1;
		BigDecimal oneTxnCount = BigDecimal.ZERO,
				tenTxnCount = BigDecimal.ZERO,
				fiftyTxnCount = BigDecimal.ZERO,
				hundredTxnCount = BigDecimal.ZERO,
				fiveHundTxnCount = BigDecimal.ZERO,
				thousandTxnCount = BigDecimal.ZERO,
				fiveThouTxnCount = BigDecimal.ZERO,
				tenThouTxnCount = BigDecimal.ZERO,
				twentyThouTxnCount = BigDecimal.ZERO,
				grtrTwentyTxnCount = BigDecimal.ZERO;


		for (BatchFileStatsReportEntityModel fileStatsEntity : fileStatsList) {
			BigDecimal subTotal = BigDecimal.ZERO;

			rowCount++;
			Row dataRow = sheet.createRow(rowCount);

			Cell memberNoCell = dataRow.createCell(0);
			memberNoCell.setCellStyle(memberFontStyle);
			memberNoCell.setCellValue(fileStatsEntity.getMemberNo());

			Cell memberNameCell = dataRow.createCell(1); 
			memberNameCell.setCellStyle(normalCenterFontStyle);
			memberNameCell.setCellValue(fileStatsEntity.getMemName());

			Cell reqRespCell = dataRow.createCell(2); 
			reqRespCell.setCellStyle(normalFontStyle);
			reqRespCell.setCellValue(fileStatsEntity.getServiceType());

			Cell serviceIdCell = dataRow.createCell(3); 
			serviceIdCell.setCellStyle(memberFontStyle);
			serviceIdCell.setCellValue(fileStatsEntity.getInService());

			Cell oneTxnCell =  dataRow.createCell(4);
			oneTxnCell.setCellStyle(normalCenterFontStyle);
			oneTxnCell.setCellType(CellType.NUMERIC);
			oneTxnCell.setCellValue(fileStatsEntity.getNrOfFiles_1().intValue());

			oneTxnCount = oneTxnCount.add(fileStatsEntity.getNrOfFiles_1());
			subTotal = subTotal.add(fileStatsEntity.getNrOfFiles_1());

			Cell _10TxnCell =  dataRow.createCell(5);
			_10TxnCell.setCellStyle(normalCenterFontStyle);
			_10TxnCell.setCellType(CellType.NUMERIC);
			_10TxnCell.setCellValue(fileStatsEntity.getNrOfFiles_2to10().intValue());

			tenTxnCount = tenTxnCount.add(fileStatsEntity.getNrOfFiles_2to10());
			subTotal = subTotal.add(fileStatsEntity.getNrOfFiles_2to10());

			Cell _50TxnCell =  dataRow.createCell(6);
			_50TxnCell.setCellStyle(normalCenterFontStyle);
			_50TxnCell.setCellType(CellType.NUMERIC);
			_50TxnCell.setCellValue(fileStatsEntity.getNrOfFiles_11to50().intValue());

			fiftyTxnCount = fiftyTxnCount.add(fileStatsEntity.getNrOfFiles_11to50());
			subTotal = subTotal.add(fileStatsEntity.getNrOfFiles_11to50());

			Cell _100TxnCell =  dataRow.createCell(7);
			_100TxnCell.setCellStyle(normalCenterFontStyle);
			_100TxnCell.setCellType(CellType.NUMERIC);
			_100TxnCell.setCellValue(fileStatsEntity.getNrOfFiles_51to100().intValue());

			hundredTxnCount = hundredTxnCount.add(fileStatsEntity.getNrOfFiles_51to100());
			subTotal = subTotal.add(fileStatsEntity.getNrOfFiles_51to100());

			Cell _500TxnCell =  dataRow.createCell(8);
			_500TxnCell.setCellStyle(normalCenterFontStyle);
			_500TxnCell.setCellType(CellType.NUMERIC);
			_500TxnCell.setCellValue(fileStatsEntity.getNrOfFiles_101to500().intValue());

			fiveHundTxnCount = fiveHundTxnCount.add(fileStatsEntity.getNrOfFiles_101to500());
			subTotal = subTotal.add(fileStatsEntity.getNrOfFiles_101to500());

			Cell _1000TxnCell =  dataRow.createCell(9);
			_1000TxnCell.setCellStyle(normalCenterFontStyle);
			_1000TxnCell.setCellType(CellType.NUMERIC);
			_1000TxnCell.setCellValue(fileStatsEntity.getNrOfFiles_501to1000().intValue());

			thousandTxnCount = thousandTxnCount.add(fileStatsEntity.getNrOfFiles_501to1000());
			subTotal = subTotal.add(fileStatsEntity.getNrOfFiles_501to1000());

			Cell _5000TxnCell =  dataRow.createCell(10);
			_5000TxnCell.setCellStyle(normalCenterFontStyle);
			_5000TxnCell.setCellType(CellType.NUMERIC);
			_5000TxnCell.setCellValue(fileStatsEntity.getNrOfFiles_1001to5000().intValue());

			fiveThouTxnCount = fiveThouTxnCount.add(fileStatsEntity.getNrOfFiles_1001to5000());
			subTotal = subTotal.add(fileStatsEntity.getNrOfFiles_1001to5000());

			Cell _10000TxnCell =  dataRow.createCell(11);
			_10000TxnCell.setCellStyle(normalCenterFontStyle);
			_10000TxnCell.setCellType(CellType.NUMERIC);
			_10000TxnCell.setCellValue(fileStatsEntity.getNrOfFiles_5001to10000().intValue());

			tenThouTxnCount = tenThouTxnCount.add(fileStatsEntity.getNrOfFiles_5001to10000());
			subTotal = subTotal.add(fileStatsEntity.getNrOfFiles_5001to10000());

			Cell _20000TxnCell =  dataRow.createCell(12);
			_20000TxnCell.setCellStyle(normalCenterFontStyle);
			_20000TxnCell.setCellType(CellType.NUMERIC);
			_20000TxnCell.setCellValue(fileStatsEntity.getNrOfFiles_10001to20000().intValue());

			twentyThouTxnCount = twentyThouTxnCount.add(fileStatsEntity.getNrOfFiles_10001to20000());
			subTotal = subTotal.add(fileStatsEntity.getNrOfFiles_10001to20000());

			Cell greater20000TxnCell =  dataRow.createCell(13);
			greater20000TxnCell.setCellStyle(normalCenterFontStyle);
			greater20000TxnCell.setCellType(CellType.NUMERIC);
			greater20000TxnCell.setCellValue(fileStatsEntity.getNrOfFiles_grtr_20000().intValue());

			grtrTwentyTxnCount = grtrTwentyTxnCount.add(fileStatsEntity.getNrOfFiles_grtr_20000());
			subTotal = subTotal.add(fileStatsEntity.getNrOfFiles_grtr_20000());

			Cell subTotalCell =  dataRow.createCell(14);
			subTotalCell.setCellStyle(memberFontStyle);
			subTotalCell.setCellType(CellType.NUMERIC);
			subTotalCell.setCellValue(subTotal.intValue());	
		}

		//Subtotal Row
		rowCount++;
		Row totalRow = sheet.createRow(rowCount);

		Cell stTitleCell = totalRow.createCell(0);
		stTitleCell.setCellStyle(subTitleCellStyle2);
		stTitleCell.setCellValue("SUB TOTAL");

		Cell subTotalnullCell = totalRow.createCell(1);
		subTotalnullCell.setCellStyle(subTitleCellStyle2);

		Cell subTotalnullCell_2 = totalRow.createCell(2);
		subTotalnullCell_2.setCellStyle(subTitleCellStyle2);

		Cell subTotalnullCell_3 = totalRow.createCell(3);
		subTotalnullCell_3.setCellStyle(subTitleCellStyle2);

		//Merge Cell 0 and 1
		sheet.addMergedRegion(new CellRangeAddress(rowCount,rowCount, 0, 3));

		Cell st_1TxnCell =  totalRow.createCell(4);
		st_1TxnCell.setCellStyle(memberFontStyle);
		st_1TxnCell.setCellType(CellType.NUMERIC);
		st_1TxnCell.setCellValue(oneTxnCount.intValue());

		Cell st_10TxnCell =  totalRow.createCell(5);
		st_10TxnCell.setCellStyle(memberFontStyle);
		st_10TxnCell.setCellType(CellType.NUMERIC);
		st_10TxnCell.setCellValue(tenTxnCount.intValue());

		Cell st_50TxnCell =  totalRow.createCell(6);
		st_50TxnCell.setCellStyle(memberFontStyle);
		st_50TxnCell.setCellType(CellType.NUMERIC);
		st_50TxnCell.setCellValue(fiftyTxnCount.intValue());

		Cell st_100TxnCell =  totalRow.createCell(7);
		st_100TxnCell.setCellStyle(memberFontStyle);
		st_100TxnCell.setCellType(CellType.NUMERIC);
		st_100TxnCell.setCellValue(hundredTxnCount.intValue());

		Cell st_500TxnCell =  totalRow.createCell(8);
		st_500TxnCell.setCellStyle(memberFontStyle);
		st_500TxnCell.setCellType(CellType.NUMERIC);
		st_500TxnCell.setCellValue(fiveHundTxnCount.intValue());

		Cell st_1000TxnCell =  totalRow.createCell(9);
		st_1000TxnCell.setCellStyle(memberFontStyle);
		st_1000TxnCell.setCellType(CellType.NUMERIC);
		st_1000TxnCell.setCellValue(thousandTxnCount.intValue());

		Cell st_5000TxnCell =  totalRow.createCell(10);
		st_5000TxnCell.setCellStyle(memberFontStyle);
		st_5000TxnCell.setCellType(CellType.NUMERIC);
		st_5000TxnCell.setCellValue(fiveThouTxnCount.intValue());

		Cell st_10000TxnCell =  totalRow.createCell(11);
		st_10000TxnCell.setCellStyle(memberFontStyle);
		st_10000TxnCell.setCellType(CellType.NUMERIC);
		st_10000TxnCell.setCellValue(tenThouTxnCount.intValue());

		Cell st_20000TxnCell =  totalRow.createCell(12);
		st_20000TxnCell.setCellStyle(memberFontStyle);
		st_20000TxnCell.setCellType(CellType.NUMERIC);
		st_20000TxnCell.setCellValue(twentyThouTxnCount.intValue());

		Cell st_grtrTxnCell =  totalRow.createCell(13);
		st_grtrTxnCell.setCellStyle(memberFontStyle);
		st_grtrTxnCell.setCellType(CellType.NUMERIC);
		st_grtrTxnCell.setCellValue(grtrTwentyTxnCount.intValue());

		Cell subTotalCell =  totalRow.createCell(14);
		subTotalCell.setCellStyle(memberFontStyle);
		subTotalCell.setCellType(CellType.FORMULA);
		subTotalCell.setCellFormula("SUM(O3:O122)");

		//Grand Total
		//Subtotal Row
		rowCount++;
		Row grandTotalRow = sheet.createRow(rowCount);

		Cell gtTitleCell = grandTotalRow.createCell(0);
		gtTitleCell.setCellStyle(subTitleCellStyle2);
		gtTitleCell.setCellValue("GRAND TOTAL");

		Cell grandTotalnullCell = grandTotalRow.createCell(1);
		grandTotalnullCell.setCellStyle(subTitleCellStyle2);

		Cell grandTotalnullCell_2 = grandTotalRow.createCell(2);
		grandTotalnullCell_2.setCellStyle(subTitleCellStyle2);

		Cell grandTotalnullCell_3 = grandTotalRow.createCell(3);
		grandTotalnullCell_3.setCellStyle(subTitleCellStyle2);

		//Merge Cell 0 and 1
		sheet.addMergedRegion(new CellRangeAddress(rowCount,rowCount, 0, 3));

		Cell gtTxnCell =  grandTotalRow.createCell(4);
		gtTxnCell.setCellStyle(memberFontStyle);
		gtTxnCell.setCellType(CellType.FORMULA);
		gtTxnCell.setCellFormula("SUM(E123:N123)");

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
