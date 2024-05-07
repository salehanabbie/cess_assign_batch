package com.bsva.reports;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import org.apache.commons.lang3.time.DurationFormatUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Footer;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.bsva.commons.model.CreditorBankModel;
import com.bsva.commons.model.DebtorBankModel;
import com.bsva.commons.model.ServicesModel;
import com.bsva.commons.model.SysctrlCompParamModel;
import com.bsva.commons.model.SystemParameterModel;
import com.bsva.entities.CasCnfgReportNamesEntity;
import com.bsva.entities.MonthlyVolumeCountEntityModel;
import com.bsva.interfaces.AdminBeanRemote;
import com.bsva.interfaces.PropertyUtilRemote;
import com.bsva.interfaces.ReportBeanRemote;
import com.bsva.interfaces.ServiceBeanRemote;
import com.bsva.interfaces.ValidationBeanRemote;
import com.bsva.utils.DateUtil;
import com.bsva.utils.Util;
import com.google.common.io.Files;
import com.itextpdf.text.DocumentException;

/**
 * @author SalehaR
 * @author SalehaR-2020-03-20: CHG-191677 remove PDF versions
 * @author SalehaR: 2020/04/19: Make population of summary sheet dynamic (acc to CIS)
 */
public class DailyBatchVolumeReport 
{
	long startTime = System.nanoTime();
	public static Logger log=Logger.getLogger(DailyBatchVolumeReport.class);
	private String fileName;

	public static ServiceBeanRemote beanRemote;
	public static ValidationBeanRemote valBeanRemote;
	private static AdminBeanRemote adminBeanRemote;
	private static PropertyUtilRemote propertyUtilRemote;
	private static ReportBeanRemote reportBeanRemote;

	List<CreditorBankModel> creditorBankModelList;
	CreditorBankModel creditorBankModel;
	List<DebtorBankModel> debtorBankModelList;
	DebtorBankModel debtorBankModel;
	List<ServicesModel> servicesList;

	List<MonthlyVolumeCountEntityModel> inputFromCreditorCountList;
	List<MonthlyVolumeCountEntityModel> outputToDebtorCountList;
	List<MonthlyVolumeCountEntityModel> inputFromDebtorCountList;
	List<MonthlyVolumeCountEntityModel> outputToCreditorCountList;
	List<MonthlyVolumeCountEntityModel> statusReportToCreditorsCountList;
	List<MonthlyVolumeCountEntityModel> statusReportToDebtorsCountList;

	TreeMap<String, List<MonthlyVolumeCountEntityModel>> inputFromCreditorBanksMap;
	TreeMap<String, List<MonthlyVolumeCountEntityModel>> outputToDebtorBanksMap;
	TreeMap<String, List<MonthlyVolumeCountEntityModel>> inputFromDebtorBankMap;
	TreeMap<String, List<MonthlyVolumeCountEntityModel>> outputToCreditorBankMap;
	TreeMap<String, List<MonthlyVolumeCountEntityModel>> statusReportToCreditorsMap;
	TreeMap<String, List<MonthlyVolumeCountEntityModel>> statusReportToDebtorsMap;
	TreeMap<String, MonthlyVolumeCountEntityModel> summaryTotalsMap;

	SystemParameterModel systemParameterModel;
	SysctrlCompParamModel companyParamModel;

	SimpleDateFormat monthFormat = new SimpleDateFormat("MMM-yyyy");
	String reportName,recipientNr, reportNr, reportDir = null, tempDir = null;
	String xlsFileName = null, BSACA02 = null;
	String invBank = null;
	int fileSeqNo =000;
	int rowCount = 0;

	Workbook workBook = null;

	Font titleFont;
	Font shFont;
	Font headerFont;
	Font serviceFont; 
	Font normalCellFont;
	Font accptedFont;
	Font rejectedFont; 
	Font subTotalFont;
	Font gtTotalFont;
	Font summSTFont;

	CellStyle titleCellStyle;
	CellStyle subHdrCellStyle;
	CellStyle subTitleCellStyle;
	CellStyle headerCellStyle;
	CellStyle serviceCellStyle;
	CellStyle normalCellStyle;
	CellStyle normalPercCellStyle;
	CellStyle acceptedCellStyle;
	CellStyle rejectedCellStyle;
	CellStyle subTotalCellStyle;
	CellStyle emptyCellStyle;
	CellStyle grandTotalCellStyle;
	CellStyle gtTotalCellStyle;
	CellStyle gtAccptdCellStyle;
	CellStyle gtRjctdCellStyle;
	CellStyle summSubtotalCellStyle;

	BigDecimal gtTotal = BigDecimal.ZERO;
	BigDecimal gtAccptd = BigDecimal.ZERO;
	BigDecimal gtRejected = BigDecimal.ZERO;
	BigDecimal gtExtracted = BigDecimal.ZERO;

	BigDecimal gtCredStatusTotal = BigDecimal.ZERO;
	BigDecimal gtDebtStatusTotal = BigDecimal.ZERO;
	BigDecimal gtTotalDebt = BigDecimal.ZERO;
	BigDecimal gtAccptdDebt = BigDecimal.ZERO;
	BigDecimal gtRejectedDebt = BigDecimal.ZERO;
	BigDecimal gtExtractedDebt = BigDecimal.ZERO;
	
	String activeIndicator = "Y";

	public void generateReport(boolean frontEnd,Date frontEndDate) throws DocumentException, IOException
	{
		contextAdminBeanCheck();
		contextCheck();
		contextValidationBeanCheck();
		contextPropertyFileCheck();
		contextReportBeanCheck();

		systemParameterModel = new SystemParameterModel();
		systemParameterModel =(SystemParameterModel) adminBeanRemote.retrieveWebActiveSysParameter();

		companyParamModel = new SysctrlCompParamModel();
		companyParamModel = (SysctrlCompParamModel) beanRemote.retrieveCompanyParameters();

		try
		{
			log.info("Date : "+ frontEndDate);
			tempDir = propertyUtilRemote.getPropValue("ExtractTemp.Out");
			log.debug("tempDir ==> "+tempDir);
			reportDir= propertyUtilRemote.getPropValue("Reports.Output");
			log.debug("reportDir ==> "+reportDir);
			//Retrieve Report Name here
			BSACA02 = propertyUtilRemote.getPropValue("RPT.DAILY.BATCH.VOLUMES");
			invBank = propertyUtilRemote.getPropValue("ERROR_CODES_REPORT_INV_BANK");
			recipientNr = propertyUtilRemote.getPropValue("AC.ACH.RPT.RECIPIENT.NUMBER");
		}
		catch(Exception ex)
		{
			log.error("BSACA02- Could not find CessionAssignment.properties in classpath");
			reportDir = "/home/opsjava/Delivery/Cession_Assign/Output/Reports/";
			tempDir="/home/opsjava/Delivery/Cession_Assign/Output/temp/";
			invBank = "INVBNK";
		}

		//Retrieve Report Name
		CasCnfgReportNamesEntity reportNameEntity = new CasCnfgReportNamesEntity();
		reportNameEntity = (CasCnfgReportNamesEntity) adminBeanRemote.retrieveReportName(BSACA02);

		if(reportNameEntity != null)
		{
			reportNr = reportNameEntity.getReportNr();
			reportName = reportNameEntity.getReportName();
			
			if(reportNameEntity.getActiveInd().equalsIgnoreCase(activeIndicator)) {
				log.info("*****REPORT "+reportNr+" GENERATING*****");
				generateReportDetail(reportNr, reportName,frontEnd,frontEndDate);
				
				long endTime = System.nanoTime();
				long duration = (endTime - startTime) / 1000000;
				log.info("[BSACA02 Report Duration: "+DurationFormatUtils.formatDuration(duration, "HH:mm:ss.S")+" milliseconds |");
			}
		}
	}

	public void generateReportDetail(String reportNo,String reportname,boolean frontEnd,Date frontEndDate) throws DocumentException, IOException
	{
		DecimalFormat df = new DecimalFormat("### ### ### ### ### ##0.00");
		DateFormat dateTimeFormat = new SimpleDateFormat("yyyyMMdd_HH:mm:ss");
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat fileTimeFormat = new SimpleDateFormat("MM-yyyy");
		DateFormat endDateFormat = new SimpleDateFormat("ddMMyyyy");
		DateFormat reportDate = new SimpleDateFormat("ddMMyyyy");
		DateFormat transDateFormat = new SimpleDateFormat("yyyyMMdd");

		Date currentDate = new Date();
		fileSeqNo =fileSeqNo + 1;
		String strSeqNo;
		int pageNo = 1;
		int lastSeqNoUsed;
		String strMonth = null;
		String month = null;
		String year = null;
		Date firstDate, lastDate = null;
		String strFromDate = null, strToDate = null;
		rowCount = 0;

		if(frontEnd) {
			fileName = tempDir+recipientNr+reportNo+"AC"+reportDate.format(frontEndDate).toString()+".xlsx";
			xlsFileName  =recipientNr+reportNo+"AC"+reportDate.format(frontEndDate).toString()+".xlsx";
		}else {
			fileName = tempDir+recipientNr+reportNo+"AC"+reportDate.format(currentDate).toString()+".xlsx";
			xlsFileName  =recipientNr+reportNo+"AC"+reportDate.format(currentDate).toString()+".xlsx";
		}

		String inpFromCreditorsSheetName = "CASA BATCH PROD VOLUMES";//name of inpFromCreditorsSheet

		strMonth = fileTimeFormat.format(systemParameterModel.getProcessDate()).substring(0,2);
		month= DateUtil.getMonthFullname(Integer.valueOf(strMonth), true);
		//		log.info("Process Month----->"+month);
		year = String.valueOf(DateUtil.getYear(systemParameterModel.getProcessDate()));
		//		log.info("year----->"+year);


		if(systemParameterModel.getProcessDate() != null)
		{
			strFromDate = dateFormat.format(systemParameterModel.getProcessDate());
			strToDate = dateFormat.format(systemParameterModel.getProcessDate());

			//			log.info("strFromDate ==>" + strFromDate);
			//			log.info("strToDate ==> "+ strToDate);
		}

		//Load Data
		if(frontEnd) {
			loadVolumeData(dateFormat.format(frontEndDate).toString());
		}else {
			loadVolumeData(strFromDate);
		}

		workBook = new XSSFWorkbook(); //or new HSSFWorkbook();
		Sheet summaryInfoSheet = workBook.createSheet("Summary Information");
		Sheet inpFromCreditorsSheet = workBook.createSheet("Creditor Bank Input");
		Sheet inpFromDebtorsSheet = workBook.createSheet("Debtor Bank Input");
		Sheet statusReportsSheet = workBook.createSheet("Status Report");

		//=============================CELL STYLES & FONT STYLES==============================//
		//Title Style
		titleCellStyle = workBook.createCellStyle();
		titleCellStyle = workBook.createCellStyle();
		titleCellStyle.setWrapText(true);
		titleCellStyle.setAlignment(HorizontalAlignment.CENTER_SELECTION);
		titleCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		titleCellStyle.setFillForegroundColor(IndexedColors.SEA_GREEN.getIndex());
		titleCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);

		titleFont = workBook.createFont();
		titleFont.setFontName("Arial");
		titleFont.setBold(true);
		titleFont.setFontHeightInPoints((short) 16);
		titleFont.setColor(IndexedColors.WHITE.getIndex());

		titleCellStyle.setFont(titleFont);

		//Sub-Header Cell Style
		subHdrCellStyle = workBook.createCellStyle();
		subHdrCellStyle = workBook.createCellStyle();
		subHdrCellStyle.setWrapText(true);
		subHdrCellStyle.setAlignment(HorizontalAlignment.CENTER_SELECTION);
		subHdrCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		subHdrCellStyle.setFillForegroundColor(IndexedColors.TAN.getIndex());
		subHdrCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);

		shFont = workBook.createFont();
		shFont.setFontName("Arial");
		shFont.setBold(true);
		shFont.setFontHeightInPoints((short) 14);

		subHdrCellStyle.setFont(shFont);

		//Sub-Title Cell Style
		subTitleCellStyle = workBook.createCellStyle();
		subTitleCellStyle = workBook.createCellStyle();
		subTitleCellStyle.setWrapText(true);
		subTitleCellStyle.setAlignment(HorizontalAlignment.CENTER_SELECTION);
		subTitleCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		subTitleCellStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
		subTitleCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);

		subTitleCellStyle.setFont(shFont);

		//		//Header Cell Style
		headerCellStyle = workBook.createCellStyle();
		headerCellStyle = workBook.createCellStyle();
		headerCellStyle.setWrapText(true);
		headerCellStyle.setAlignment(HorizontalAlignment.CENTER_SELECTION);
		headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		headerCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		headerCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);

		headerCellStyle.setBorderBottom(BorderStyle.THICK);
		headerCellStyle.setBorderTop(BorderStyle.THICK);
		headerCellStyle.setBorderRight(BorderStyle.THICK);
		headerCellStyle.setBorderLeft(BorderStyle.THICK);

		headerFont = workBook.createFont();
		headerFont.setFontName("Arial");
		headerFont.setBold(true);

		headerCellStyle.setFont(headerFont);

		//Service Cell Style
		serviceCellStyle = workBook.createCellStyle();
		serviceCellStyle.setWrapText(true);
		serviceCellStyle.setAlignment(HorizontalAlignment.CENTER_SELECTION);
		serviceCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		serviceCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		serviceCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);

		serviceCellStyle.setBorderBottom(BorderStyle.THIN);
		serviceCellStyle.setBorderTop(BorderStyle.THIN);
		serviceCellStyle.setBorderRight(BorderStyle.THIN);
		serviceCellStyle.setBorderLeft(BorderStyle.THIN);

		serviceFont = workBook.createFont();
		serviceFont.setFontName("Arial");
		serviceFont.setBold(true);
		serviceCellStyle.setFont(serviceFont);

		//Normal Cell Style
		normalCellStyle = workBook.createCellStyle();
		normalCellStyle = workBook.createCellStyle();
		normalCellStyle.setWrapText(true);
		normalCellStyle.setAlignment(HorizontalAlignment.CENTER_SELECTION);
		normalCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		normalCellStyle.setBorderBottom(BorderStyle.THIN);
		normalCellStyle.setBorderTop(BorderStyle.THIN);
		normalCellStyle.setBorderRight(BorderStyle.THIN);
		normalCellStyle.setBorderLeft(BorderStyle.THIN);

		normalCellFont = workBook.createFont();
		normalCellFont.setFontName("Arial");
		normalCellFont.setBold(false);

		normalCellStyle.setFont(normalCellFont);

		//Normal Cell Style
		normalPercCellStyle = workBook.createCellStyle();
		normalPercCellStyle = workBook.createCellStyle();
		normalPercCellStyle.setWrapText(true);
		normalPercCellStyle.setAlignment(HorizontalAlignment.CENTER_SELECTION);
		normalPercCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		normalPercCellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0%"));

		normalPercCellStyle.setBorderBottom(BorderStyle.THIN);
		normalPercCellStyle.setBorderTop(BorderStyle.THIN);
		normalPercCellStyle.setBorderRight(BorderStyle.THIN);
		normalPercCellStyle.setBorderLeft(BorderStyle.THIN);
		normalPercCellStyle.setFont(normalCellFont);

		//Accepted Cell Style
		acceptedCellStyle = workBook.createCellStyle();
		acceptedCellStyle = workBook.createCellStyle();
		acceptedCellStyle.setWrapText(true);
		acceptedCellStyle.setAlignment(HorizontalAlignment.CENTER_SELECTION);
		acceptedCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		acceptedCellStyle.setBorderBottom(BorderStyle.THIN);
		acceptedCellStyle.setBorderTop(BorderStyle.THIN);
		acceptedCellStyle.setBorderRight(BorderStyle.THIN);
		acceptedCellStyle.setBorderLeft(BorderStyle.THIN);

		accptedFont = workBook.createFont();
		accptedFont.setFontName("Arial");
		accptedFont.setBold(false);
		accptedFont.setColor(IndexedColors.GREEN.getIndex());

		acceptedCellStyle.setFont(accptedFont);

		//Rejected Cell Style
		rejectedCellStyle = workBook.createCellStyle();
		rejectedCellStyle = workBook.createCellStyle();
		rejectedCellStyle.setWrapText(true);
		rejectedCellStyle.setAlignment(HorizontalAlignment.CENTER_SELECTION);
		rejectedCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		rejectedCellStyle.setBorderBottom(BorderStyle.THIN);
		rejectedCellStyle.setBorderTop(BorderStyle.THIN);
		rejectedCellStyle.setBorderRight(BorderStyle.THIN);
		rejectedCellStyle.setBorderLeft(BorderStyle.THIN);

		rejectedFont = workBook.createFont();
		rejectedFont.setFontName("Arial");
		rejectedFont.setBold(false);
		rejectedFont.setColor(IndexedColors.RED.getIndex());

		rejectedCellStyle.setFont(rejectedFont);

		//SubTotal Cell Style
		subTotalCellStyle = workBook.createCellStyle();
		subTotalCellStyle.setWrapText(true);
		subTotalCellStyle.setAlignment(HorizontalAlignment.CENTER_SELECTION);
		subTotalCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		subTotalCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		subTotalCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		subTotalCellStyle.setBorderTop(BorderStyle.THIN);
		subTotalCellStyle.setBorderRight(BorderStyle.THIN);
		subTotalCellStyle.setBorderLeft(BorderStyle.THIN);
		subTotalCellStyle.setBorderBottom(BorderStyle.DOUBLE);

		subTotalFont = workBook.createFont();
		subTotalFont.setFontName("Arial");

		subTotalFont.setBold(true);
		subTotalCellStyle.setFont(subTotalFont);

		//Empty Cell Style
		emptyCellStyle = workBook.createCellStyle();
		emptyCellStyle.setWrapText(true);

		//Grand Total Cell Style
		grandTotalCellStyle = workBook.createCellStyle();
		grandTotalCellStyle.setWrapText(true);
		grandTotalCellStyle.setAlignment(HorizontalAlignment.CENTER_SELECTION);
		grandTotalCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		grandTotalCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		grandTotalCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		grandTotalCellStyle.setBorderTop(BorderStyle.THICK);
		grandTotalCellStyle.setBorderRight(BorderStyle.THICK);
		grandTotalCellStyle.setBorderLeft(BorderStyle.THICK);
		grandTotalCellStyle.setBorderBottom(BorderStyle.THICK);

		gtTotalFont = workBook.createFont();
		gtTotalFont.setFontName("Arial");
		gtTotalFont.setBold(true);
		gtTotalFont.setFontHeightInPoints((short) 16);

		grandTotalCellStyle.setFont(gtTotalFont);

		//Grand Total Cell Style - TOTAL
		gtTotalCellStyle = workBook.createCellStyle();
		gtTotalCellStyle.setWrapText(true);
		gtTotalCellStyle.setAlignment(HorizontalAlignment.CENTER_SELECTION);
		gtTotalCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		gtTotalCellStyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());
		gtTotalCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		gtTotalCellStyle.setBorderTop(BorderStyle.THICK);
		gtTotalCellStyle.setBorderRight(BorderStyle.THICK);
		gtTotalCellStyle.setBorderLeft(BorderStyle.THICK);
		gtTotalCellStyle.setBorderBottom(BorderStyle.THICK);

		gtTotalCellStyle.setFont(gtTotalFont);

		//Grand Total Cell Style - ACCEPTED
		gtAccptdCellStyle = workBook.createCellStyle();
		gtAccptdCellStyle.setWrapText(true);
		gtAccptdCellStyle.setAlignment(HorizontalAlignment.CENTER_SELECTION);
		gtAccptdCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		gtAccptdCellStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		gtAccptdCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		gtAccptdCellStyle.setBorderTop(BorderStyle.THICK);
		gtAccptdCellStyle.setBorderRight(BorderStyle.THICK);
		gtAccptdCellStyle.setBorderLeft(BorderStyle.THICK);
		gtAccptdCellStyle.setBorderBottom(BorderStyle.THICK);

		gtAccptdCellStyle.setFont(gtTotalFont);

		//Grand Total Cell Style - REJECTED
		gtRjctdCellStyle = workBook.createCellStyle();
		gtRjctdCellStyle.setWrapText(true);
		gtRjctdCellStyle.setAlignment(HorizontalAlignment.CENTER_SELECTION);
		gtRjctdCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		gtRjctdCellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
		gtRjctdCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		gtRjctdCellStyle.setBorderTop(BorderStyle.THICK);
		gtRjctdCellStyle.setBorderRight(BorderStyle.THICK);
		gtRjctdCellStyle.setBorderLeft(BorderStyle.THICK);
		gtRjctdCellStyle.setBorderBottom(BorderStyle.THICK);

		gtRjctdCellStyle.setFont(gtTotalFont);

		//SubTotal Summary Cell Style
		summSubtotalCellStyle = workBook.createCellStyle();
		summSubtotalCellStyle.setWrapText(true);
		summSubtotalCellStyle.setAlignment(HorizontalAlignment.CENTER_SELECTION);
		summSubtotalCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		summSubtotalCellStyle.setBorderBottom(BorderStyle.THIN);
		summSubtotalCellStyle.setBorderTop(BorderStyle.THIN);
		summSubtotalCellStyle.setBorderRight(BorderStyle.THIN);
		summSubtotalCellStyle.setBorderLeft(BorderStyle.THIN);

		summSTFont = workBook.createFont();
		summSTFont.setFontName("Arial");
		summSTFont.setBold(true);
		summSTFont.setColor(IndexedColors.BLUE.getIndex());
		summSubtotalCellStyle.setFont(summSTFont);

		summaryTotalsMap = new TreeMap<String, MonthlyVolumeCountEntityModel>();

		//=====================GENERATE REPORT==========================//

		//_______________________SHEET 2 - INPUT FROM CREDITOR BANKS__________________________________//
		generateCreditorInfoSheet(inpFromCreditorsSheet, dateFormat, reportNo, reportName,frontEndDate);

		//_______________________SHEET 3 - INPUT FROM DEBTOR BANKS__________________________________//
		generateDebtorInfoSheet(inpFromDebtorsSheet, transDateFormat, reportNo, reportName,frontEndDate);

		//_______________________SHEET 4 - STATUS REPORT INFORMATION__________________________________//
		generateStatusReportInfoSheet(statusReportsSheet, transDateFormat, reportNo, reportName,frontEndDate);

		//_______________________SHEET 1 - SUMMARY INFORMATION__________________________________//
		generateSummarySheet(summaryInfoSheet, transDateFormat, reportNo, reportName,frontEndDate);

		// Write the output to a file
		FileOutputStream fileOut = new FileOutputStream(fileName);
		workBook.write(fileOut);
		fileOut.close();

		//Copy the report to the Output reports folder 
		try
		{
			copyFile(xlsFileName);
		}
		catch(IOException ioe)
		{
			log.error("Error on copying BSACA02 report to temp "+ioe.getMessage());
			ioe.printStackTrace();
		}
		catch(Exception ex)
		{
			log.error("Error on copying BSACA02 report to temp "+ex.getMessage());
			ex.printStackTrace();
		}
	}

	public void generateCreditorInfoSheet(Sheet inpFromCreditorsSheet, DateFormat dateFormat, String reportNo, String reportName,Date frontEndDate)
	{
		//Sheet 2(Input from Creditor Bank Details)
		inpFromCreditorsSheet.setPrintRowAndColumnHeadings(true);
		inpFromCreditorsSheet.setAutobreaks(true);
		inpFromCreditorsSheet.setFitToPage(true);

		PrintSetup ps = inpFromCreditorsSheet.getPrintSetup();

		ps.setPaperSize(PrintSetup.A4_PAPERSIZE);
		//Scale - Fit All Columns to one page
		ps.setFitHeight((short) 0);
		ps.setFitWidth((short) 1);

		inpFromCreditorsSheet.setColumnWidth(0, 5000);
		inpFromCreditorsSheet.setColumnWidth(1, 5000);
		inpFromCreditorsSheet.setColumnWidth(2, 5000);
		inpFromCreditorsSheet.setColumnWidth(3, 5000);
		inpFromCreditorsSheet.setColumnWidth(4, 5000);
		inpFromCreditorsSheet.setColumnWidth(5, 2000);
		inpFromCreditorsSheet.setColumnWidth(6, 5000);
		inpFromCreditorsSheet.setColumnWidth(7, 5000);
		inpFromCreditorsSheet.setColumnWidth(8, 5000);

		Footer credFooter = inpFromCreditorsSheet.getFooter();
		credFooter.setLeft("Date Created:  &D   &T");
		credFooter.setRight("Page Number:  &P of  &N");

		//GENERATE TITLE ROW 
		Row titleRow = inpFromCreditorsSheet.createRow(0);

		//TITLE ROW
		Cell titleCell = titleRow.createCell(0);
		titleCell.setCellStyle(titleCellStyle);
		
		if (frontEndDate != null) {
			titleCell.setCellValue(reportNo + " - " + reportName + " - " + dateFormat.format(frontEndDate));
		} else {
			titleCell.setCellValue(reportNo + " - " + reportName + " - " + dateFormat.format(systemParameterModel.getProcessDate()));
		}

		Cell nullCell1 = titleRow.createCell(1);
		nullCell1.setCellStyle(titleCellStyle);
		Cell nullCell2 = titleRow.createCell(2);
		nullCell2.setCellStyle(titleCellStyle);
		Cell nullCell3 = titleRow.createCell(3);
		nullCell3.setCellStyle(titleCellStyle);
		Cell nullCell4 = titleRow.createCell(4);
		nullCell4.setCellStyle(titleCellStyle);
		Cell nullCell5 = titleRow.createCell(5);
		nullCell5.setCellStyle(titleCellStyle);
		Cell nullCell6 = titleRow.createCell(6);
		nullCell6.setCellStyle(titleCellStyle);
		Cell nullCell7 = titleRow.createCell(7);
		nullCell7.setCellStyle(titleCellStyle);
		Cell nullCell8 = titleRow.createCell(8);
		nullCell8.setCellStyle(titleCellStyle);

		//HEADER ROW
		Row titleRow1 = inpFromCreditorsSheet.createRow(1);

		Cell titleCell10 = titleRow1.createCell(0);
		titleCell10.setCellStyle(titleCellStyle);

		Cell nullCell11 = titleRow1.createCell(1);
		nullCell11.setCellStyle(titleCellStyle);
		Cell nullCell21 = titleRow1.createCell(2);
		nullCell21.setCellStyle(titleCellStyle);
		Cell nullCell31 = titleRow1.createCell(3);
		nullCell31.setCellStyle(titleCellStyle);
		Cell nullCell41 = titleRow1.createCell(4);
		nullCell41.setCellStyle(titleCellStyle);
		Cell nullCell51 = titleRow1.createCell(5);
		nullCell51.setCellStyle(titleCellStyle);
		Cell nullCell61 = titleRow1.createCell(6);
		nullCell61.setCellStyle(titleCellStyle);
		Cell nullCell71 = titleRow1.createCell(7);
		nullCell71.setCellStyle(titleCellStyle);
		Cell nullCell81 = titleRow1.createCell(8);
		nullCell81.setCellStyle(titleCellStyle);

		//Merge Cell 0 to 10
		//		inpFromCreditorsSheet.addMergedRegion(rowFrom,rowTo,colFrom,colTo);
		inpFromCreditorsSheet.addMergedRegion(new CellRangeAddress(0,2, 0,8));

		//GENERATE SUB TITLE ROW
		Row subHdrRow = inpFromCreditorsSheet.createRow(3);

		Cell subHdrCrdCell = subHdrRow.createCell(0);
		subHdrCrdCell.setCellStyle(subHdrCellStyle);
		subHdrCrdCell.setCellValue("SUBMITTED BY CREDITOR BANKS");

		Cell shNull1 = subHdrRow.createCell(1);
		shNull1.setCellStyle(subHdrCellStyle);
		Cell shNull2 = subHdrRow.createCell(2);
		shNull2.setCellStyle(subHdrCellStyle);
		Cell shNull3 = subHdrRow.createCell(3);
		shNull3.setCellStyle(subHdrCellStyle);
		Cell shNull4 = subHdrRow.createCell(4);
		shNull4.setCellStyle(subHdrCellStyle);

		//Merge Cell 0 to 4
		inpFromCreditorsSheet.addMergedRegion(new CellRangeAddress(3,3, 0, 4));

		Cell shNull5 = subHdrRow.createCell(5);
		shNull5.setCellStyle(emptyCellStyle);

		Cell subHdrDbtExtCell = subHdrRow.createCell(6);
		subHdrDbtExtCell.setCellStyle(subHdrCellStyle);
		subHdrDbtExtCell.setCellValue("EXTRACTED TO DEBTOR BANKS");

		//		Merge Cell 6 to 10
		inpFromCreditorsSheet.addMergedRegion(new CellRangeAddress(3,3, 6, 8));

		//GENERATE COLUMN HEADERS
		Row hdrRow = inpFromCreditorsSheet.createRow(4);

		Cell serviceCell1 = hdrRow.createCell(0);
		serviceCell1.setCellStyle(headerCellStyle);
		serviceCell1.setCellValue("SERVICE");

		Cell bankCell1 = hdrRow.createCell(1);
		bankCell1.setCellStyle(headerCellStyle);
		bankCell1.setCellValue("BANKS");

		Cell nrOfTxnsCell1 = hdrRow.createCell(2);
		nrOfTxnsCell1.setCellStyle(headerCellStyle);
		nrOfTxnsCell1.setCellValue("TOT NR OF TXNS");

		Cell accpTxnsCells1 = hdrRow.createCell(3);
		accpTxnsCells1.setCellStyle(headerCellStyle);
		accpTxnsCells1.setCellValue("ACCEPTED TXNS");

		Cell rjctTxnsCells1 = hdrRow.createCell(4);
		rjctTxnsCells1.setCellStyle(headerCellStyle);
		rjctTxnsCells1.setCellValue("REJECTED TXNS");

		Cell hdrNullCell5 = hdrRow.createCell(5);
		hdrNullCell5.setCellStyle(emptyCellStyle);

		Cell outServiceCell = hdrRow.createCell(6);
		outServiceCell.setCellStyle(headerCellStyle);
		outServiceCell.setCellValue("SERVICE");

		Cell outBankCell = hdrRow.createCell(7);
		outBankCell.setCellStyle(headerCellStyle);
		outBankCell.setCellValue("BANKS");

		Cell nrOfExtTxnsCell = hdrRow.createCell(8);
		nrOfExtTxnsCell.setCellStyle(headerCellStyle);
		nrOfExtTxnsCell.setCellValue("EXTRACTED TXNS");


		//		========================CARIN============================//
		int rowCount = 6;
		int debtorCount = 6;

		//Retrieve CARIN Counts
		//		2018-09-07 SalehaR - Removed as it is replaced by loadCreditorData()
		//		List<MonthlyVolumeCountEntityModel> carinCountList = (List<MonthlyVolumeCountEntityModel>) beanRemote.retrieveMndtCountByCreditorBanks(strFromDate,strToDate,"CARIN");

		if(inputFromCreditorCountList != null && inputFromCreditorCountList.size() > 0)
		{
			BigDecimal stTotal = BigDecimal.ZERO;
			BigDecimal stAccptd = BigDecimal.ZERO;
			BigDecimal stRejected = BigDecimal.ZERO;
			BigDecimal stExtracted= BigDecimal.ZERO;

			int fstCount = 0;
			int outfstCount = 0;
			Row carinRow = null;
			//			//Retrieve the carinCount
			List<MonthlyVolumeCountEntityModel> carinCountList = inputFromCreditorBanksMap.get("CARIN");

			if(carinCountList != null && carinCountList.size() > 0)
			{	
				for (MonthlyVolumeCountEntityModel carinCountEntity : carinCountList) 
				{
					carinRow = inpFromCreditorsSheet.createRow(rowCount);
					Cell carinServCell = carinRow.createCell(0);

					if(fstCount == 0)
					{
						carinServCell.setCellStyle(serviceCellStyle);
						carinServCell.setCellValue("CARIN");
						fstCount = 1;
					}
					else
					{
						carinServCell.setCellStyle(normalCellStyle);
					}

					Cell creditorBank = carinRow.createCell(1);
					creditorBank.setCellStyle(normalCellStyle);
					creditorBank.setCellValue(carinCountEntity.getInstId());

					Cell totalNrMsgs = carinRow.createCell(2);
					totalNrMsgs.setCellStyle(normalCellStyle);
					totalNrMsgs.setCellValue(carinCountEntity.getNrOfMsgs().intValue());
					stTotal = stTotal.add(carinCountEntity.getNrOfMsgs());

					Cell accptdMsgs = carinRow.createCell(3);
					accptdMsgs.setCellStyle(acceptedCellStyle);
					accptdMsgs.setCellValue(carinCountEntity.getNrOfAccpMsgs().intValue());
					stAccptd = stAccptd.add(carinCountEntity.getNrOfAccpMsgs());

					Cell rjctdMsgs = carinRow.createCell(4);
					rjctdMsgs.setCellStyle(rejectedCellStyle);
					rjctdMsgs.setCellValue(carinCountEntity.getNrOfRjctMsgs().intValue());
					stRejected = stRejected.add(carinCountEntity.getNrOfRjctMsgs());

					//Move to next row
					rowCount++;
				}

				//SubTotal the Rows
				Row carinSTRow = inpFromCreditorsSheet.createRow(rowCount);

				Cell carinServ = carinSTRow.createCell(0);
				carinServ.setCellStyle(subTotalCellStyle);

				Cell carinBank = carinSTRow.createCell(1);
				carinBank.setCellStyle(subTotalCellStyle);
				carinBank.setCellValue("Total");

				Cell sttotalNrMsgs = carinSTRow.createCell(2);
				sttotalNrMsgs.setCellStyle(subTotalCellStyle);
				sttotalNrMsgs.setCellValue(stTotal.intValue());

				Cell staccp = carinSTRow.createCell(3);
				staccp.setCellStyle(subTotalCellStyle);
				staccp.setCellValue(stAccptd.intValue());

				Cell stRjct = carinSTRow.createCell(4);
				stRjct.setCellStyle(subTotalCellStyle);
				stRjct.setCellValue(stRejected.intValue());

				//Add to Grand Total
				gtTotal = gtTotal.add(stTotal);
				gtAccptd = gtAccptd.add(stAccptd);
				gtRejected = gtRejected.add(stRejected);

				rowCount++;
			}	


			//			Retrieve the carotCount
			List<MonthlyVolumeCountEntityModel> carotCountList = outputToDebtorBanksMap.get("CAROT");
			//			log.info("carotCountList.size() ===>"+carotCountList.size()); 
			if(carotCountList != null && carotCountList.size() > 0)
			{	
				for (MonthlyVolumeCountEntityModel carotCountEntity : carotCountList) 
				{
					Row carotRow = inpFromCreditorsSheet.getRow(debtorCount);
					Cell carotServCell = carotRow.createCell(6);
					if(outfstCount == 0)
					{
						carotServCell.setCellStyle(serviceCellStyle);
						carotServCell.setCellValue("CAROT");
						outfstCount = 1;
					}
					else
					{
						carotServCell.setCellStyle(normalCellStyle);
					}

					Cell debtorBank = carotRow.createCell(7);
					debtorBank.setCellStyle(normalCellStyle);
					debtorBank.setCellValue(carotCountEntity.getInstId());

					Cell totalNrExtrctdMsgs = carotRow.createCell(8);
					totalNrExtrctdMsgs.setCellStyle(normalCellStyle);
					totalNrExtrctdMsgs.setCellValue(carotCountEntity.getNrOfExtMsgs().intValue());
					stExtracted = stExtracted.add(carotCountEntity.getNrOfExtMsgs());
					//Move to next row
					debtorCount++;
				}

				//SubTotal the Rows
				Row extStRow = inpFromCreditorsSheet.getRow(rowCount-1);

				Cell carotServ = extStRow.createCell(6);
				carotServ.setCellStyle(subTotalCellStyle);

				Cell carotBank = extStRow.createCell(7);
				carotBank.setCellStyle(subTotalCellStyle);
				carotBank.setCellValue("Total");

				Cell sttotalNrExtMsgs = extStRow.createCell(8);
				sttotalNrExtMsgs.setCellStyle(subTotalCellStyle);
				sttotalNrExtMsgs.setCellValue(stExtracted.intValue());

				//Add to Grand Total
				gtExtracted = gtExtracted.add(stExtracted);
			}
			//POPULATE SUMMARY TOTALS
			MonthlyVolumeCountEntityModel carinTotalEntity = new MonthlyVolumeCountEntityModel();
			carinTotalEntity.setService("CARIN");
			carinTotalEntity.setTotalNrOfMsgs(stTotal);
			carinTotalEntity.setTotalAccpMsgs(stAccptd);
			carinTotalEntity.setTotalRjctdMsgs(stRejected);
			carinTotalEntity.setTotalNrOfExtMsgs(stExtracted);

			summaryTotalsMap.put("CARIN", carinTotalEntity);
		}

		//Empty Rows
		Row emptyRow8 = inpFromCreditorsSheet.createRow(rowCount);
		rowCount++;

		Row grandTotalRow = inpFromCreditorsSheet.createRow(rowCount);
		rowCount++;

		Cell grdTotalCell = grandTotalRow.createCell(0);
		grdTotalCell.setCellStyle(grandTotalCellStyle);
		grdTotalCell.setCellValue("GRAND TOTAL");

		Cell grdTotalCell1 = grandTotalRow.createCell(1);
		grdTotalCell1.setCellStyle(grandTotalCellStyle);

		Cell gtTotalCell = grandTotalRow.createCell(2);
		gtTotalCell.setCellStyle(gtTotalCellStyle);
		gtTotalCell.setCellValue(gtTotal.toString());

		Cell gtAccpCell = grandTotalRow.createCell(3);
		gtAccpCell.setCellStyle(gtAccptdCellStyle);
		gtAccpCell.setCellValue(gtAccptd.toString());

		Cell gtRjctCell = grandTotalRow.createCell(4);
		gtRjctCell.setCellStyle(gtRjctdCellStyle);
		gtRjctCell.setCellValue(gtRejected.toString());

		Cell gtNullCell = grandTotalRow.createCell(5);

		Cell gtTotalExtCell = grandTotalRow.createCell(6);
		gtTotalExtCell.setCellStyle(grandTotalCellStyle);
		gtTotalExtCell.setCellValue("GRAND TOTAL");

		Cell gtTotalExtCell1 = grandTotalRow.createCell(7);
		gtTotalExtCell1.setCellStyle(grandTotalCellStyle);

		Cell gtExtCell = grandTotalRow.createCell(8);
		gtExtCell.setCellStyle(gtAccptdCellStyle);
		gtExtCell.setCellValue(gtExtracted.toString());

		Row nullRow = inpFromCreditorsSheet.createRow(rowCount);

		Cell gtNull1 = nullRow.createCell(0);
		gtNull1.setCellStyle(grandTotalCellStyle);

		Cell gtNull2 = nullRow.createCell(1);
		gtNull2.setCellStyle(grandTotalCellStyle);

		Cell gtNull3 = nullRow.createCell(2);
		gtNull3.setCellStyle(gtTotalCellStyle);

		Cell gtNull4= nullRow.createCell(3);
		gtNull4.setCellStyle(gtAccptdCellStyle);

		Cell gtNull5 = nullRow.createCell(4);
		gtNull5.setCellStyle(gtRjctdCellStyle);

		Cell gtNull6 = nullRow.createCell(5);

		Cell gtNull7 = nullRow.createCell(6);
		gtNull7.setCellStyle(grandTotalCellStyle);

		Cell gtNull8 = nullRow.createCell(7);
		gtNull8.setCellStyle(grandTotalCellStyle);

		Cell gtNull9 = nullRow.createCell(8);
		gtNull9.setCellStyle(gtAccptdCellStyle);

		//Merge Cell 0 to 1
		inpFromCreditorsSheet.addMergedRegion(new CellRangeAddress(rowCount-1,rowCount, 0, 1));
		inpFromCreditorsSheet.addMergedRegion(new CellRangeAddress(rowCount-1,rowCount, 2, 2));
		inpFromCreditorsSheet.addMergedRegion(new CellRangeAddress(rowCount-1,rowCount, 3, 3));
		inpFromCreditorsSheet.addMergedRegion(new CellRangeAddress(rowCount-1,rowCount, 4, 4));
		inpFromCreditorsSheet.addMergedRegion(new CellRangeAddress(rowCount-1,rowCount, 6, 7));
		inpFromCreditorsSheet.addMergedRegion(new CellRangeAddress(rowCount-1,rowCount, 8, 8));

	}

	public void generateDebtorInfoSheet(Sheet inpFromDebtorsSheet, DateFormat dateFormat, String reportNo, String reportName,Date frontEndDate)
	{
		//Sheet 3(Input from Debtor Bank Details)		
		inpFromDebtorsSheet.setPrintRowAndColumnHeadings(true);
		inpFromDebtorsSheet.setAutobreaks(true);
		inpFromDebtorsSheet.setFitToPage(true);

		PrintSetup ps3 = inpFromDebtorsSheet.getPrintSetup();

		ps3.setPaperSize(PrintSetup.A4_PAPERSIZE);
		//Scale - Fit All Columns to one page
		ps3.setFitHeight((short) 0);
		ps3.setFitWidth((short) 1);

		inpFromDebtorsSheet.setColumnWidth(0, 5000);
		inpFromDebtorsSheet.setColumnWidth(1, 5000);
		inpFromDebtorsSheet.setColumnWidth(2, 5000);
		inpFromDebtorsSheet.setColumnWidth(3, 5000);
		inpFromDebtorsSheet.setColumnWidth(4, 5000);
		inpFromDebtorsSheet.setColumnWidth(5, 2000);
		inpFromDebtorsSheet.setColumnWidth(6, 5000);
		inpFromDebtorsSheet.setColumnWidth(7, 5000);
		inpFromDebtorsSheet.setColumnWidth(8, 5000);

		Footer debtFooter = inpFromDebtorsSheet.getFooter();
		debtFooter.setLeft("Date Created:  &D   &T");
		debtFooter.setRight("Page Number:  &P of  &N");

		//GENERATE TITLE ROW 
		Row titleRow3 = inpFromDebtorsSheet.createRow(0);

		//TITLE ROW
		Cell titleCell3 = titleRow3.createCell(0);
		titleCell3.setCellStyle(titleCellStyle);
		
		if (frontEndDate != null) {
			titleCell3.setCellValue(reportNo + " - " + reportName + " - " + dateFormat.format(frontEndDate));
		} else {
			titleCell3.setCellValue(reportNo + " - " + reportName + " - " + dateFormat.format(systemParameterModel.getProcessDate()));
		}

		Cell nullCell13 = titleRow3.createCell(1);
		nullCell13.setCellStyle(titleCellStyle);
		Cell nullCell23 = titleRow3.createCell(2);
		nullCell23.setCellStyle(titleCellStyle);
		Cell nullCell33 = titleRow3.createCell(3);
		nullCell33.setCellStyle(titleCellStyle);
		Cell nullCell43 = titleRow3.createCell(4);
		nullCell43.setCellStyle(titleCellStyle);
		Cell nullCell53 = titleRow3.createCell(5);
		nullCell53.setCellStyle(titleCellStyle);
		Cell nullCell63 = titleRow3.createCell(6);
		nullCell63.setCellStyle(titleCellStyle);
		Cell nullCell73 = titleRow3.createCell(7);
		nullCell73.setCellStyle(titleCellStyle);
		Cell nullCell83 = titleRow3.createCell(8);
		nullCell83.setCellStyle(titleCellStyle);

		//HEADER ROW
		Row titleRow13 = inpFromDebtorsSheet.createRow(1);

		Cell titleCell103= titleRow13.createCell(0);
		titleCell103.setCellStyle(titleCellStyle);

		Cell nullCell113 = titleRow13.createCell(1);
		nullCell113.setCellStyle(titleCellStyle);
		Cell nullCell213 = titleRow13.createCell(2);
		nullCell213.setCellStyle(titleCellStyle);
		Cell nullCell313 = titleRow13.createCell(3);
		nullCell313.setCellStyle(titleCellStyle);
		Cell nullCell413 = titleRow13.createCell(4);
		nullCell413.setCellStyle(titleCellStyle);
		Cell nullCell513 = titleRow13.createCell(5);
		nullCell513.setCellStyle(titleCellStyle);
		Cell nullCell613 = titleRow13.createCell(6);
		nullCell613.setCellStyle(titleCellStyle);
		Cell nullCell713 = titleRow13.createCell(7);
		nullCell713.setCellStyle(titleCellStyle);
		Cell nullCell813 = titleRow13.createCell(8);
		nullCell813.setCellStyle(titleCellStyle);

		//Merge Cell 0 to 10
		//		inpFromCreditorsSheet.addMergedRegion(rowFrom,rowTo,colFrom,colTo);
		inpFromDebtorsSheet.addMergedRegion(new CellRangeAddress(0,2, 0,8));

		//GENERATE SUB TITLE ROW
		Row subHdrRow3= inpFromDebtorsSheet.createRow(3);

		Cell subHdrCrdCell3 = subHdrRow3.createCell(0);
		subHdrCrdCell3.setCellStyle(subHdrCellStyle);
		subHdrCrdCell3.setCellValue("SUBMITTED BY DEBTOR BANKS");

		Cell shNull13 = subHdrRow3.createCell(1);
		shNull13.setCellStyle(subHdrCellStyle);
		Cell shNull23 = subHdrRow3.createCell(2);
		shNull23.setCellStyle(subHdrCellStyle);
		Cell shNull33 = subHdrRow3.createCell(3);
		shNull33.setCellStyle(subHdrCellStyle);
		Cell shNull43 = subHdrRow3.createCell(4);
		shNull43.setCellStyle(subHdrCellStyle);

		//Merge Cell 0 to 4
		inpFromDebtorsSheet.addMergedRegion(new CellRangeAddress(3,3, 0, 4));

		Cell shNull53 = subHdrRow3.createCell(5);
		shNull53.setCellStyle(emptyCellStyle);

		Cell subHdrDbtExtCell3 = subHdrRow3.createCell(6);
		subHdrDbtExtCell3.setCellStyle(subHdrCellStyle);
		subHdrDbtExtCell3.setCellValue("EXTRACTED TO CREDITOR BANKS");

		//		Merge Cell 6 to 10
		inpFromDebtorsSheet.addMergedRegion(new CellRangeAddress(3,3, 6, 8));

		//GENERATE COLUMN HEADERS
		Row hdrRow3 = inpFromDebtorsSheet.createRow(4);

		Cell serviceCell3 = hdrRow3.createCell(0);
		serviceCell3.setCellStyle(headerCellStyle);
		serviceCell3.setCellValue("SERVICE");

		Cell bankCell3 = hdrRow3.createCell(1);
		bankCell3.setCellStyle(headerCellStyle);
		bankCell3.setCellValue("BANKS");

		Cell nrOfTxnsCell3 = hdrRow3.createCell(2);
		nrOfTxnsCell3.setCellStyle(headerCellStyle);
		nrOfTxnsCell3.setCellValue("TOT NR OF TXNS");

		Cell accpTxnsCells3 = hdrRow3.createCell(3);
		accpTxnsCells3.setCellStyle(headerCellStyle);
		accpTxnsCells3.setCellValue("ACCEPTED TXNS");

		Cell rjctTxnsCells3 = hdrRow3.createCell(4);
		rjctTxnsCells3.setCellStyle(headerCellStyle);
		rjctTxnsCells3.setCellValue("REJECTED TXNS");

		Cell hdrNullCell53 = hdrRow3.createCell(5);
		hdrNullCell53.setCellStyle(emptyCellStyle);

		Cell outServiceCell3 = hdrRow3.createCell(6);
		outServiceCell3.setCellStyle(headerCellStyle);
		outServiceCell3.setCellValue("SERVICE");

		Cell outBankCell3 = hdrRow3.createCell(7);
		outBankCell3.setCellStyle(headerCellStyle);
		outBankCell3.setCellValue("BANKS");

		Cell nrOfExtTxnsCell3 = hdrRow3.createCell(8);
		nrOfExtTxnsCell3.setCellStyle(headerCellStyle);
		nrOfExtTxnsCell3.setCellValue("EXTRACTED TXNS");

		int rowCount_3 = 6;
		int debtorCount = 6;

		//		========================ST201============================//
		//		2018-09-13 SalehaR - Removed as it is replaced by loadVolumeData()
		//				List<MonthlyVolumeCountEntityModel> st101CountList = (List<MonthlyVolumeCountEntityModel>) beanRemote.retrieveMndtCountByDebtorBanks(strFromDate,strToDate,"ST201");
		if(inputFromDebtorCountList != null && inputFromDebtorCountList.size() > 0)
		{
			BigDecimal stTotal = BigDecimal.ZERO;
			BigDecimal stAccptd = BigDecimal.ZERO;
			BigDecimal stRejected = BigDecimal.ZERO;
			BigDecimal stExtracted= BigDecimal.ZERO;

			int fstCount = 0;
			int outfstCount = 0;

			//			//Retrieve the st101Count
			List<MonthlyVolumeCountEntityModel> st101CountList = inputFromDebtorBankMap.get("ST201");

			if(st101CountList != null && st101CountList.size() > 0)
			{	
				for (MonthlyVolumeCountEntityModel st101CountEntity : st101CountList) 
				{
					Row outServiceRow = inpFromDebtorsSheet.createRow(rowCount_3);
					Cell extServiceCell = outServiceRow.createCell(0);

					if(fstCount == 0)
					{
						extServiceCell.setCellStyle(serviceCellStyle);
						extServiceCell.setCellValue("ST201");
						fstCount = 1;
					}
					else
					{
						extServiceCell.setCellStyle(normalCellStyle);
					}

					Cell debtorBank = outServiceRow.createCell(1);
					debtorBank.setCellStyle(normalCellStyle);
					debtorBank.setCellValue(st101CountEntity.getInstId());

					Cell totalNrMsgs = outServiceRow.createCell(2);
					totalNrMsgs.setCellStyle(normalCellStyle);
					totalNrMsgs.setCellValue(st101CountEntity.getNrOfMsgs().intValue());
					stTotal = stTotal.add(st101CountEntity.getNrOfMsgs());

					Cell accptdMsgs = outServiceRow.createCell(3);
					accptdMsgs.setCellStyle(acceptedCellStyle);
					accptdMsgs.setCellValue(st101CountEntity.getNrOfAccpMsgs().intValue());
					stAccptd = stAccptd.add(st101CountEntity.getNrOfAccpMsgs());

					Cell rjctdMsgs = outServiceRow.createCell(4);
					rjctdMsgs.setCellStyle(rejectedCellStyle);
					rjctdMsgs.setCellValue(st101CountEntity.getNrOfRjctMsgs().intValue());
					stRejected = stRejected.add(st101CountEntity.getNrOfRjctMsgs());

					//Move to next row
					rowCount_3++;
				}

				//SubTotal the Rows
				Row extRow3 = inpFromDebtorsSheet.createRow(rowCount_3);

				Cell extServ = extRow3.createCell(0);
				extServ.setCellStyle(subTotalCellStyle);

				Cell extBank = extRow3.createCell(1);
				extBank.setCellStyle(subTotalCellStyle);
				extBank.setCellValue("Total");

				Cell sttotalNrMsgs = extRow3.createCell(2);
				sttotalNrMsgs.setCellStyle(subTotalCellStyle);
				sttotalNrMsgs.setCellValue(stTotal.intValue());

				Cell staccp = extRow3.createCell(3);
				staccp.setCellStyle(subTotalCellStyle);
				staccp.setCellValue(stAccptd.intValue());

				Cell stRjct = extRow3.createCell(4);
				stRjct.setCellStyle(subTotalCellStyle);
				stRjct.setCellValue(stRejected.intValue());

				//Add to Grand Total
				gtTotalDebt = gtTotalDebt.add(stTotal);
				gtAccptdDebt = gtAccptdDebt.add(stAccptd);
				gtRejectedDebt = gtRejectedDebt.add(stRejected);

				rowCount_3++;
			}	


			//			Retrieve the st103Count
			List<MonthlyVolumeCountEntityModel> st103CountList = outputToCreditorBankMap.get("ST203");
			//			log.info("st103CountList.size() ===>"+st103CountList.size()); 
			if(st103CountList != null && st103CountList.size() > 0)
			{	
				for (MonthlyVolumeCountEntityModel st103CountEntity : st103CountList) 
				{
					//					if(rowCount_3 == debtorCount)
					//					{
					//						debtorCount++;
					//					}

					Row extRow33 = inpFromDebtorsSheet.getRow(debtorCount);
					Cell extServCell = extRow33.createCell(6);
					if(outfstCount == 0)
					{
						extServCell.setCellStyle(serviceCellStyle);
						extServCell.setCellValue("ST203");
						outfstCount = 1;
					}
					else
					{
						extServCell.setCellStyle(normalCellStyle);
					}

					Cell debtorBank = extRow33.createCell(7);
					debtorBank.setCellStyle(normalCellStyle);
					debtorBank.setCellValue(st103CountEntity.getInstId());

					Cell totalNrExtrctdMsgs = extRow33.createCell(8);
					totalNrExtrctdMsgs.setCellStyle(normalCellStyle);
					totalNrExtrctdMsgs.setCellValue(st103CountEntity.getNrOfExtMsgs().intValue());
					stExtracted = stExtracted.add(st103CountEntity.getNrOfExtMsgs());
					//Move to next row
					debtorCount++;
				}

				//				log.info("creditorCount before SUBTOTAL ===>"+creditorCount);
				//SubTotal the Rows
				Row extStRow = inpFromDebtorsSheet.getRow(debtorCount);

				Cell st103Serv = extStRow.createCell(6);
				st103Serv.setCellStyle(subTotalCellStyle);

				Cell st103Bank = extStRow.createCell(7);
				st103Bank.setCellStyle(subTotalCellStyle);
				st103Bank.setCellValue("Total");

				Cell sttotalNrExtMsgs = extStRow.createCell(8);
				sttotalNrExtMsgs.setCellStyle(subTotalCellStyle);
				sttotalNrExtMsgs.setCellValue(stExtracted.intValue());

				//Add to Grand Total
				gtExtractedDebt = gtExtractedDebt.add(stExtracted);
			}
			//POPULATE SUMMARY TOTALS
			MonthlyVolumeCountEntityModel st101TotalEntity = new MonthlyVolumeCountEntityModel();
			st101TotalEntity.setService("ST201");
			st101TotalEntity.setTotalNrOfMsgs(stTotal);
			st101TotalEntity.setTotalAccpMsgs(stAccptd);
			st101TotalEntity.setTotalRjctdMsgs(stRejected);
			st101TotalEntity.setTotalNrOfExtMsgs(stExtracted);

			summaryTotalsMap.put("ST201", st101TotalEntity);
		}

		//		log.info("rowCount_3 AFTER ==> "+rowCount_3);
		//		log.info("creditorCount AFTER ==> "+creditorCount);

		//SubTotal the Rows
		debtorCount++;
		Row emptyRow33 = inpFromDebtorsSheet.createRow(debtorCount);
		debtorCount++;
		rowCount_3 = debtorCount;

		//		log.info("rowCount_3 BEFORE RCAIN ==> "+rowCount_3);
		//		log.info("creditorCount BEFORE RCAIN ==> "+creditorCount);


		//		========================RCAIN============================//

		if(inputFromDebtorCountList != null && inputFromDebtorCountList.size() > 0)
		{
			BigDecimal stTotal = BigDecimal.ZERO;
			BigDecimal stAccptd = BigDecimal.ZERO;
			BigDecimal stRejected = BigDecimal.ZERO;
			BigDecimal stExtracted= BigDecimal.ZERO;

			int fstCount = 0;
			int outfstCount = 0;

			//			//Retrieve the manacCount
			List<MonthlyVolumeCountEntityModel> manacCountList = inputFromDebtorBankMap.get("RCAIN");

			if(manacCountList != null && manacCountList.size() > 0)
			{	
				for (MonthlyVolumeCountEntityModel manacCountEntity : manacCountList) 
				{
					Row outServiceRow = inpFromDebtorsSheet.createRow(rowCount_3);
					Cell extServiceCell = outServiceRow.createCell(0);

					if(fstCount == 0)
					{
						extServiceCell.setCellStyle(serviceCellStyle);
						extServiceCell.setCellValue("RCAIN");
						fstCount = 1;
					}
					else
					{
						extServiceCell.setCellStyle(normalCellStyle);
					}

					Cell debtorBank = outServiceRow.createCell(1);
					debtorBank.setCellStyle(normalCellStyle);
					debtorBank.setCellValue(manacCountEntity.getInstId());

					Cell totalNrMsgs = outServiceRow.createCell(2);
					totalNrMsgs.setCellStyle(normalCellStyle);
					totalNrMsgs.setCellValue(manacCountEntity.getNrOfMsgs().intValue());
					stTotal = stTotal.add(manacCountEntity.getNrOfMsgs());

					Cell accptdMsgs = outServiceRow.createCell(3);
					accptdMsgs.setCellStyle(acceptedCellStyle);
					accptdMsgs.setCellValue(manacCountEntity.getNrOfAccpMsgs().intValue());
					stAccptd = stAccptd.add(manacCountEntity.getNrOfAccpMsgs());

					Cell rjctdMsgs = outServiceRow.createCell(4);
					rjctdMsgs.setCellStyle(rejectedCellStyle);
					rjctdMsgs.setCellValue(manacCountEntity.getNrOfRjctMsgs().intValue());
					stRejected = stRejected.add(manacCountEntity.getNrOfRjctMsgs());

					//Move to next row
					rowCount_3++;
				}

				//SubTotal the Rows
				Row extRow3 = inpFromDebtorsSheet.createRow(rowCount_3);

				Cell extServ = extRow3.createCell(0);
				extServ.setCellStyle(subTotalCellStyle);

				Cell extBank = extRow3.createCell(1);
				extBank.setCellStyle(subTotalCellStyle);
				extBank.setCellValue("Total");

				Cell sttotalNrMsgs = extRow3.createCell(2);
				sttotalNrMsgs.setCellStyle(subTotalCellStyle);
				sttotalNrMsgs.setCellValue(stTotal.intValue());

				Cell staccp = extRow3.createCell(3);
				staccp.setCellStyle(subTotalCellStyle);
				staccp.setCellValue(stAccptd.intValue());

				Cell stRjct = extRow3.createCell(4);
				stRjct.setCellStyle(subTotalCellStyle);
				stRjct.setCellValue(stRejected.intValue());

				//Add to Grand Total
				gtTotalDebt = gtTotalDebt.add(stTotal);
				gtAccptdDebt = gtAccptdDebt.add(stAccptd);
				gtRejectedDebt = gtRejectedDebt.add(stRejected);

				rowCount_3++;
			}	


			//			Retrieve the manocCount
			List<MonthlyVolumeCountEntityModel> manocCountList = outputToCreditorBankMap.get("RCAOT");
			//			log.info("manocCountList.size() ===>"+manocCountList.size()); 
			if(manocCountList != null && manocCountList.size() > 0)
			{	
				for (MonthlyVolumeCountEntityModel manocCountEntity : manocCountList) 
				{
					//					if(rowCount_3 == debtorCount)
					//					{
					//						debtorCount++;
					//					}

					Row extRow33 = inpFromDebtorsSheet.getRow(debtorCount);
					Cell extServCell = extRow33.createCell(6);
					if(outfstCount == 0)
					{
						extServCell.setCellStyle(serviceCellStyle);
						extServCell.setCellValue("RCAOT");
						outfstCount = 1;
					}
					else
					{
						extServCell.setCellStyle(normalCellStyle);
					}

					Cell debtorBank = extRow33.createCell(7);
					debtorBank.setCellStyle(normalCellStyle);
					debtorBank.setCellValue(manocCountEntity.getInstId());

					Cell totalNrExtrctdMsgs = extRow33.createCell(8);
					totalNrExtrctdMsgs.setCellStyle(normalCellStyle);
					totalNrExtrctdMsgs.setCellValue(manocCountEntity.getNrOfExtMsgs().intValue());
					stExtracted = stExtracted.add(manocCountEntity.getNrOfExtMsgs());
					//Move to next row
					debtorCount++;
				}

				//				log.info("creditorCount before SUBTOTAL ===>"+creditorCount);
				//SubTotal the Rows
				Row extStRow = inpFromDebtorsSheet.getRow(debtorCount);

				Cell st103Serv = extStRow.createCell(6);
				st103Serv.setCellStyle(subTotalCellStyle);

				Cell st103Bank = extStRow.createCell(7);
				st103Bank.setCellStyle(subTotalCellStyle);
				st103Bank.setCellValue("Total");

				Cell sttotalNrExtMsgs = extStRow.createCell(8);
				sttotalNrExtMsgs.setCellStyle(subTotalCellStyle);
				sttotalNrExtMsgs.setCellValue(stExtracted.intValue());

				//Add to Grand Total
				gtExtractedDebt = gtExtractedDebt.add(stExtracted);
			}
			//POPULATE SUMMARY TOTALS
			MonthlyVolumeCountEntityModel manacTotalEntity = new MonthlyVolumeCountEntityModel();
			manacTotalEntity.setService("RCAIN");
			manacTotalEntity.setTotalNrOfMsgs(stTotal);
			manacTotalEntity.setTotalAccpMsgs(stAccptd);
			manacTotalEntity.setTotalRjctdMsgs(stRejected);
			manacTotalEntity.setTotalNrOfExtMsgs(stExtracted);

			summaryTotalsMap.put("RCAIN", manacTotalEntity);
		}

		//		log.info("rowCount_3 AFTER RCAOT ==> "+rowCount_3);
		//		log.info("creditorCount AFTER RCAOT==> "+creditorCount);

		//SubTotal the Rows
		debtorCount++;
		Row emptyRow34 = inpFromDebtorsSheet.createRow(debtorCount);
		debtorCount++;
		rowCount_3 = debtorCount;

		//		log.info("rowCount_3 BEFORE MANRT ==> "+rowCount_3);
		//		log.info("creditorCount BEFORE MANRT ==> "+creditorCount);

		Row grandTotalRow3 = inpFromDebtorsSheet.createRow(rowCount_3);
		rowCount_3++;

		Cell grdTotalCell3 = grandTotalRow3.createCell(0);
		grdTotalCell3.setCellStyle(grandTotalCellStyle);
		grdTotalCell3.setCellValue("GRAND TOTAL");

		Cell grdTotalCell13 = grandTotalRow3.createCell(1);
		grdTotalCell13.setCellStyle(grandTotalCellStyle);

		Cell gtTotalCell3 = grandTotalRow3.createCell(2);
		gtTotalCell3.setCellStyle(gtTotalCellStyle);
		gtTotalCell3.setCellValue(gtTotalDebt.toString());

		Cell gtAccpCell3 = grandTotalRow3.createCell(3);
		gtAccpCell3.setCellStyle(gtAccptdCellStyle);
		gtAccpCell3.setCellValue(gtAccptdDebt.toString());

		Cell gtRjctCell3 = grandTotalRow3.createCell(4);
		gtRjctCell3.setCellStyle(gtRjctdCellStyle);
		gtRjctCell3.setCellValue(gtRejectedDebt.toString());

		Cell gtNullCell3 = grandTotalRow3.createCell(5);

		Cell gtTotalExtCell3 = grandTotalRow3.createCell(6);
		gtTotalExtCell3.setCellStyle(grandTotalCellStyle);
		gtTotalExtCell3.setCellValue("GRAND TOTAL");

		Cell gtTotalExtCell13 = grandTotalRow3.createCell(7);
		gtTotalExtCell13.setCellStyle(grandTotalCellStyle);

		Cell gtExtCell3 = grandTotalRow3.createCell(8);
		gtExtCell3.setCellStyle(gtAccptdCellStyle);
		gtExtCell3.setCellValue(gtExtractedDebt.toString());

		Row nullRow3 = inpFromDebtorsSheet.createRow(rowCount_3);

		Cell gtNull13 = nullRow3.createCell(0);
		gtNull13.setCellStyle(grandTotalCellStyle);

		Cell gtNull23 = nullRow3.createCell(1);
		gtNull23.setCellStyle(grandTotalCellStyle);

		Cell gtNull33 = nullRow3.createCell(2);
		gtNull33.setCellStyle(gtTotalCellStyle);

		Cell gtNull43= nullRow3.createCell(3);
		gtNull43.setCellStyle(gtAccptdCellStyle);

		Cell gtNull53 = nullRow3.createCell(4);
		gtNull53.setCellStyle(gtRjctdCellStyle);

		Cell gtNull63 = nullRow3.createCell(5);

		Cell gtNull73 = nullRow3.createCell(6);
		gtNull73.setCellStyle(grandTotalCellStyle);

		Cell gtNull83 = nullRow3.createCell(7);
		gtNull83.setCellStyle(grandTotalCellStyle);

		Cell gtNull93 = nullRow3.createCell(8);
		gtNull93.setCellStyle(gtAccptdCellStyle);

		//Merge Cell 0 to 1
		inpFromDebtorsSheet.addMergedRegion(new CellRangeAddress(rowCount_3-1,rowCount_3, 0, 1));
		inpFromDebtorsSheet.addMergedRegion(new CellRangeAddress(rowCount_3-1,rowCount_3, 2, 2));
		inpFromDebtorsSheet.addMergedRegion(new CellRangeAddress(rowCount_3-1,rowCount_3, 3, 3));
		inpFromDebtorsSheet.addMergedRegion(new CellRangeAddress(rowCount_3-1,rowCount_3, 4, 4));
		inpFromDebtorsSheet.addMergedRegion(new CellRangeAddress(rowCount_3-1,rowCount_3, 6, 7));
		inpFromDebtorsSheet.addMergedRegion(new CellRangeAddress(rowCount_3-1,rowCount_3, 8, 8));

	}

	public void generateStatusReportInfoSheet(Sheet statusReportsSheet, DateFormat dateFormat, String reportNo, String reportName,Date frontEndDate)
	{
		//Sheet 4(Status Report Details)		
		statusReportsSheet.setPrintRowAndColumnHeadings(true);
		statusReportsSheet.setAutobreaks(true);
		statusReportsSheet.setFitToPage(true);

		PrintSetup ps4 = statusReportsSheet.getPrintSetup();

		ps4.setPaperSize(PrintSetup.A4_PAPERSIZE);
		//Scale - Fit All Columns to one page
		ps4.setFitHeight((short) 0);
		ps4.setFitWidth((short) 1);

		statusReportsSheet.setColumnWidth(0, 5000);
		statusReportsSheet.setColumnWidth(1, 5000);
		statusReportsSheet.setColumnWidth(2, 5000);
		statusReportsSheet.setColumnWidth(3, 1000);
		statusReportsSheet.setColumnWidth(4, 5000);
		statusReportsSheet.setColumnWidth(5, 5000);
		statusReportsSheet.setColumnWidth(6, 5000);

		Footer stsFooter = statusReportsSheet.getFooter();
		stsFooter.setLeft("Date Created:  &D   &T");
		stsFooter.setRight("Page Number:  &P of  &N");

		//GENERATE TITLE ROW 
		Row titleRow14 = statusReportsSheet.createRow(0);

		//TITLE ROW
		Cell titleCell14 = titleRow14.createCell(0);
		titleCell14.setCellStyle(titleCellStyle);
		
		if(frontEndDate != null) {
			titleCell14.setCellValue(reportNo+" - "+reportName+" - "+dateFormat.format(frontEndDate));
		}else {
			titleCell14.setCellValue(reportNo+" - "+reportName+" - "+dateFormat.format(systemParameterModel.getProcessDate()));
		}
		
		//Merge Cell 0 to 10
		//		inpFromCreditorsSheet.addMergedRegion(rowFrom,rowTo,colFrom,colTo);
		statusReportsSheet.addMergedRegion(new CellRangeAddress(0,2, 0,6));

		//GENERATE SUB TITLE ROW
		Row subHdrRow14 = statusReportsSheet.createRow(3);

		Cell subHdrCrdCell14 = subHdrRow14.createCell(0);
		subHdrCrdCell14.setCellStyle(subHdrCellStyle);
		subHdrCrdCell14.setCellValue("STATUS REPORT INFORMATION");

		Cell shNull14 = subHdrRow14.createCell(1);
		shNull14.setCellStyle(subHdrCellStyle);
		Cell shNull24 = subHdrRow14.createCell(2);
		shNull24.setCellStyle(subHdrCellStyle);
		Cell shNull34 = subHdrRow14.createCell(3);
		shNull34.setCellStyle(subHdrCellStyle);
		Cell shNull44 = subHdrRow14.createCell(4);
		shNull44.setCellStyle(subHdrCellStyle);

		//Merge Cell 0 to 4
		statusReportsSheet.addMergedRegion(new CellRangeAddress(3,3, 0, 6));

		//GENERATE COLUMN HEADERS
		Row stsRptHdrRow = statusReportsSheet.createRow(4);

		Cell stsCrServiceCell = stsRptHdrRow.createCell(0);
		stsCrServiceCell.setCellStyle(headerCellStyle);
		stsCrServiceCell.setCellValue("SERVICE");

		Cell crsStsBankCell = stsRptHdrRow.createCell(1);
		crsStsBankCell.setCellStyle(headerCellStyle);
		crsStsBankCell.setCellValue("BANKS");

		Cell crNrOfStsRptsCell = stsRptHdrRow.createCell(2);
		crNrOfStsRptsCell.setCellStyle(headerCellStyle);
		crNrOfStsRptsCell.setCellValue("TOT NR OF STATUS REPORTS");

		Cell stsDrServiceCell = stsRptHdrRow.createCell(4);
		stsDrServiceCell.setCellStyle(headerCellStyle);
		stsDrServiceCell.setCellValue("SERVICE");

		Cell drsStsBankCell = stsRptHdrRow.createCell(5);
		drsStsBankCell.setCellStyle(headerCellStyle);
		drsStsBankCell.setCellValue("BANKS");

		Cell drNrOfStsRptsCell = stsRptHdrRow.createCell(6);
		drNrOfStsRptsCell.setCellStyle(headerCellStyle);
		drNrOfStsRptsCell.setCellValue("TOT NR OF STATUS REPORTS");

		Row crBanksRow14 = statusReportsSheet.createRow(5);
		Cell crBanksCell14 = crBanksRow14.createCell(0);
		crBanksCell14.setCellStyle(subTitleCellStyle);
		crBanksCell14.setCellValue("Creditor Banks");

		statusReportsSheet.addMergedRegion(new CellRangeAddress(5,5, 0, 2));

		Cell drBanksCell14 = crBanksRow14.createCell(4);
		drBanksCell14.setCellStyle(subTitleCellStyle);
		drBanksCell14.setCellValue("Debtor Banks");

		statusReportsSheet.addMergedRegion(new CellRangeAddress(5,5, 4, 6));

		int rowCount_4 = 6;
		int stsCount = 6;
		log.debug("rowCount4: "+rowCount_4);
		log.debug("stsCount: "+stsCount);
		//Retrieve Status Reports to Creditor Details
		//ST200 Counts
		if(statusReportToCreditorsCountList != null && statusReportToCreditorsCountList.size() > 0)
		{
			BigDecimal stTotal = BigDecimal.ZERO;

			int firstCount = 0;

			List<MonthlyVolumeCountEntityModel> st100CountList = statusReportToCreditorsMap.get("ST200");
			if(st100CountList != null && st100CountList.size() > 0)
			{
				for (MonthlyVolumeCountEntityModel statusReportEntity : st100CountList) 
				{
					Row statusRowCr1 = statusReportsSheet.createRow(rowCount_4);
					Cell stServCell = statusRowCr1.createCell(0);

					if(firstCount == 0)
					{
						stServCell.setCellStyle(serviceCellStyle);
						stServCell.setCellValue("ST200");
						firstCount = 1;
					}
					else
					{
						stServCell.setCellStyle(normalCellStyle);
					}

					Cell statusBank1 = statusRowCr1.createCell(1);
					statusBank1.setCellStyle(normalCellStyle);
					statusBank1.setCellValue(statusReportEntity.getInstId());

					Cell statusTotalNrMsgs = statusRowCr1.createCell(2);
					statusTotalNrMsgs.setCellStyle(normalCellStyle);
					statusTotalNrMsgs.setCellValue(statusReportEntity.getNrOfMsgs().intValue());
					stTotal = stTotal.add(statusReportEntity.getNrOfMsgs());

					rowCount_4++;
				}
				log.debug("rowCount4 create: "+rowCount_4);
				log.debug("stsCount: "+stsCount);
				//SubTotal the Rows
				Row statusTotalRow = statusReportsSheet.createRow(rowCount_4);

				Cell totServCell = statusTotalRow.createCell(0);
				totServCell.setCellStyle(subTotalCellStyle);

				Cell totStsBankCell = statusTotalRow.createCell(1);
				totStsBankCell.setCellStyle(subTotalCellStyle);
				totStsBankCell.setCellValue("Total");

				Cell sttotalNrMsgs = statusTotalRow.createCell(2);
				sttotalNrMsgs.setCellStyle(subTotalCellStyle);
				sttotalNrMsgs.setCellValue(stTotal.intValue());

				//Add to Grand Total
				gtCredStatusTotal = gtCredStatusTotal.add(stTotal);

				rowCount_4++;
			}
			//POPULATE SUMMARY TOTALS
			MonthlyVolumeCountEntityModel st100TotalEntity = new MonthlyVolumeCountEntityModel();
			st100TotalEntity.setService("ST200");
			st100TotalEntity.setTotalNrOfMsgs(stTotal);

			summaryTotalsMap.put("ST200", st100TotalEntity);
		}

		//ST202 Counts
		if(statusReportToDebtorsCountList != null && statusReportToDebtorsCountList.size() > 0)
		{
			BigDecimal stTotal = BigDecimal.ZERO;

			int firstCount = 0;

			List<MonthlyVolumeCountEntityModel> st202CountList = statusReportToDebtorsMap.get("ST202");
			if(st202CountList != null && st202CountList.size() > 0)
			{

				for (MonthlyVolumeCountEntityModel statusReportEntity : st202CountList) 
				{
					Row statusRowCr1 = statusReportsSheet.getRow(stsCount);
					Cell stServCell = statusRowCr1.createCell(4);

					if(firstCount == 0)
					{
						stServCell.setCellStyle(serviceCellStyle);
						stServCell.setCellValue("ST202");
						firstCount = 1;
					}
					else
					{
						stServCell.setCellStyle(normalCellStyle);
					}

					Cell statusBank1 = statusRowCr1.createCell(5);
					statusBank1.setCellStyle(normalCellStyle);
					statusBank1.setCellValue(statusReportEntity.getInstId());

					Cell statusTotalNrMsgs = statusRowCr1.createCell(6);
					statusTotalNrMsgs.setCellStyle(normalCellStyle);
					statusTotalNrMsgs.setCellValue(statusReportEntity.getNrOfMsgs().intValue());
					stTotal = stTotal.add(statusReportEntity.getNrOfMsgs());

					stsCount++;
				}

				//SubTotal the Rows
				Row statusTotalRow = statusReportsSheet.getRow(stsCount);

				Cell totServCell = statusTotalRow.createCell(4);
				totServCell.setCellStyle(subTotalCellStyle);

				Cell totStsBankCell = statusTotalRow.createCell(5);
				totStsBankCell.setCellStyle(subTotalCellStyle);
				totStsBankCell.setCellValue("Total");

				Cell sttotalNrMsgs = statusTotalRow.createCell(6);
				sttotalNrMsgs.setCellStyle(subTotalCellStyle);
				sttotalNrMsgs.setCellValue(stTotal.intValue());

				//Add to Grand Total
				gtDebtStatusTotal = gtDebtStatusTotal.add(stTotal);

				stsCount++;
			}
			//POPULATE SUMMARY TOTALS
			MonthlyVolumeCountEntityModel st102TotalEntity = new MonthlyVolumeCountEntityModel();
			st102TotalEntity.setService("ST202");
			st102TotalEntity.setTotalNrOfMsgs(stTotal);

			summaryTotalsMap.put("ST202", st102TotalEntity);
		}

				log.debug("rowCount_4 ==> "+rowCount_4);
				log.debug("stsCount ==> "+stsCount);

		//SubTotal the Rows
		Row statusNullRow = statusReportsSheet.createRow(rowCount_4);
		rowCount_4++;

		stsCount = rowCount_4;
		log.debug("rowCount_4 ==> "+rowCount_4);
		log.debug("stsCount ==> "+stsCount);
		//ST204 Counts
		if(statusReportToDebtorsCountList != null && statusReportToDebtorsCountList.size() > 0)
		{
			BigDecimal stTotal = BigDecimal.ZERO;

			int firstCount = 0;

			List<MonthlyVolumeCountEntityModel> st204CountList = statusReportToDebtorsMap.get("ST204");
			if(st204CountList != null && st204CountList.size() > 0)
			{
				for (MonthlyVolumeCountEntityModel statusReportEntity : st204CountList) 
				{
					Row statusRowCr1 = statusReportsSheet.createRow(rowCount_4);
					Cell stServCell = statusRowCr1.createCell(4);

					if(firstCount == 0)
					{
						stServCell.setCellStyle(serviceCellStyle);
						stServCell.setCellValue("ST204");
						firstCount = 1;
					}
					else
					{
						stServCell.setCellStyle(normalCellStyle);
					}

					Cell statusBank1 = statusRowCr1.createCell(5);
					statusBank1.setCellStyle(normalCellStyle);
					statusBank1.setCellValue(statusReportEntity.getInstId());

					Cell statusTotalNrMsgs = statusRowCr1.createCell(6);
					statusTotalNrMsgs.setCellStyle(normalCellStyle);
					statusTotalNrMsgs.setCellValue(statusReportEntity.getNrOfMsgs().intValue());
					stTotal = stTotal.add(statusReportEntity.getNrOfMsgs());

					rowCount_4++;
				}

				//SubTotal the Rows
				Row statusTotalRow = statusReportsSheet.createRow(rowCount_4);

				Cell totServCell = statusTotalRow.createCell(4);
				totServCell.setCellStyle(subTotalCellStyle);

				Cell totStsBankCell = statusTotalRow.createCell(5);
				totStsBankCell.setCellStyle(subTotalCellStyle);
				totStsBankCell.setCellValue("Total");

				Cell sttotalNrMsgs = statusTotalRow.createCell(6);
				sttotalNrMsgs.setCellStyle(subTotalCellStyle);
				sttotalNrMsgs.setCellValue(stTotal.intValue());

				//Add to Grand Total
				gtDebtStatusTotal = gtDebtStatusTotal.add(stTotal);

				rowCount_4++;
			}
			//POPULATE SUMMARY TOTALS
			MonthlyVolumeCountEntityModel st204TotalEntity = new MonthlyVolumeCountEntityModel();
			st204TotalEntity.setService("ST204");
			st204TotalEntity.setTotalNrOfMsgs(stTotal);
			summaryTotalsMap.put("ST204", st204TotalEntity);	
		}

		Row statusNullRow1 = statusReportsSheet.createRow(rowCount_4);
		rowCount_4++;

		log.debug("rowCount_4 ==> "+rowCount_4);
		log.debug("stsCount ==> "+stsCount);

		//Grand Total
		Row statusGtRow = statusReportsSheet.createRow(rowCount_4);

		Cell grdStsTotalCell = statusGtRow.createCell(0);
		grdStsTotalCell.setCellStyle(grandTotalCellStyle);
		grdStsTotalCell.setCellValue("GRAND TOTAL");

		Cell grdStsTotalCell1 = statusGtRow.createCell(1);
		grdStsTotalCell1.setCellStyle(grandTotalCellStyle);

		Cell gtStsTotalCell = statusGtRow.createCell(2);
		gtStsTotalCell.setCellStyle(gtTotalCellStyle);
		gtStsTotalCell.setCellValue(gtCredStatusTotal.toString());

		Cell gtStsNullCell = statusGtRow.createCell(3);
		Cell gtStsNullCell2 = statusGtRow.createCell(4);
		Cell gtStsNullCell3 = statusGtRow.createCell(5);

		Cell gtStsDebtTotalCell = statusGtRow.createCell(6);
		gtStsDebtTotalCell.setCellStyle(gtTotalCellStyle);
		gtStsDebtTotalCell.setCellValue(gtDebtStatusTotal.toString());

		//Merge Cell 0 to 1
		statusReportsSheet.addMergedRegion(new CellRangeAddress(rowCount_4,rowCount_4, 0, 1));

	}

	public void generateSummarySheet(Sheet summaryInfoSheet, DateFormat dateFormat, String reportNo, String reportName,Date frontEndDate)
	{
		//Sheet 1(Summary Information)		
		summaryInfoSheet.setPrintRowAndColumnHeadings(true);
		summaryInfoSheet.setAutobreaks(true);
		summaryInfoSheet.setFitToPage(true);

		PrintSetup ps1 = summaryInfoSheet.getPrintSetup();

		ps1.setPaperSize(PrintSetup.A4_PAPERSIZE);
		//Scale - Fit All Columns to one page
		ps1.setFitHeight((short) 0);
		ps1.setFitWidth((short) 1);

		summaryInfoSheet.setColumnWidth(0, 5000);
		summaryInfoSheet.setColumnWidth(1, 5000);
		summaryInfoSheet.setColumnWidth(2, 5000);
		summaryInfoSheet.setColumnWidth(3, 5000);
		summaryInfoSheet.setColumnWidth(4, 5000);
		summaryInfoSheet.setColumnWidth(5, 5000);

		Footer summFooter = summaryInfoSheet.getFooter();
		summFooter.setLeft("Date Created:  &D   &T");
		summFooter.setRight("Page Number:  &P of  &N");

		//GENERATE TITLE ROW 
		Row titleRow11 = summaryInfoSheet.createRow(0);

		//TITLE ROW
		Cell titleCell1 = titleRow11.createCell(0);
		titleCell1.setCellStyle(titleCellStyle);
		
		if(frontEndDate != null) {
			titleCell1.setCellValue(reportNo+" - "+reportName+" - "+dateFormat.format(frontEndDate));
		}else {
			titleCell1.setCellValue(reportNo+" - "+reportName+" - "+dateFormat.format(systemParameterModel.getProcessDate()));
		}
		
		//Merge Cell 0 to 10
		//		inpFromCreditorsSheet.addMergedRegion(rowFrom,rowTo,colFrom,colTo);
		summaryInfoSheet.addMergedRegion(new CellRangeAddress(0,2, 0,5));

		//GENERATE SUB TITLE ROW
		Row subHdrRow11 = summaryInfoSheet.createRow(3);

		Cell subHdrCrdCell11 = subHdrRow11.createCell(0);
		subHdrCrdCell11.setCellStyle(subHdrCellStyle);
		subHdrCrdCell11.setCellValue("SUMMARY INFORMATION");

		Cell shNull11 = subHdrRow11.createCell(1);
		shNull11.setCellStyle(subHdrCellStyle);
		Cell shNull21 = subHdrRow11.createCell(2);
		shNull21.setCellStyle(subHdrCellStyle);
		Cell shNull31 = subHdrRow11.createCell(3);
		shNull31.setCellStyle(subHdrCellStyle);
		Cell shNull41 = subHdrRow11.createCell(4);
		shNull41.setCellStyle(subHdrCellStyle);

		//Merge Cell 0 to 4
		summaryInfoSheet.addMergedRegion(new CellRangeAddress(3,3, 0, 5));

		//GENERATE COLUMN HEADERS
		Row summHdrRow = summaryInfoSheet.createRow(4);

		Cell inputServiceCell = summHdrRow.createCell(0);
		inputServiceCell.setCellStyle(headerCellStyle);
		inputServiceCell.setCellValue("INPUT SERVICE");

		Cell nrOfTxnsCell = summHdrRow.createCell(1);
		nrOfTxnsCell.setCellStyle(headerCellStyle);
		nrOfTxnsCell.setCellValue("TOT NR OF TXNS");

		Cell accpTxnsCells = summHdrRow.createCell(2);
		accpTxnsCells.setCellStyle(headerCellStyle);
		accpTxnsCells.setCellValue("ACCEPTED TXNS");

		Cell rjctTxnsCells = summHdrRow.createCell(3);
		rjctTxnsCells.setCellStyle(headerCellStyle);
		rjctTxnsCells.setCellValue("REJECTED TXNS");

		Cell extServiceCell = summHdrRow.createCell(4);
		extServiceCell.setCellStyle(headerCellStyle);
		extServiceCell.setCellValue("EXTRACT SERVICE");

		Cell extTxnCell = summHdrRow.createCell(5);
		extTxnCell.setCellStyle(headerCellStyle);
		extTxnCell.setCellValue("EXTRACTED TXNS");

		//Populate Summary Information
		Row crBanksRow = summaryInfoSheet.createRow(5);
		Cell crBanksCell = crBanksRow.createCell(0);
		crBanksCell.setCellStyle(subTitleCellStyle);
		crBanksCell.setCellValue("Creditor Banks");

		summaryInfoSheet.addMergedRegion(new CellRangeAddress(5,5, 0, 1));

		Row carinRow = summaryInfoSheet.createRow(6);
		//CARIN Data
		Cell carinSummCell = carinRow.createCell(0);
		carinSummCell.setCellStyle(serviceCellStyle);
		carinSummCell.setCellValue("CARIN");

		Cell carinTotalCell = carinRow.createCell(1);
		carinTotalCell.setCellStyle(normalCellStyle);
		carinTotalCell.setCellValue(summaryTotalsMap.get("CARIN").getTotalNrOfMsgs().intValue());

		Cell carinAccpCell = carinRow.createCell(2);
		carinAccpCell.setCellStyle(normalCellStyle);
		carinAccpCell.setCellValue(summaryTotalsMap.get("CARIN").getTotalAccpMsgs().intValue());

		Cell carinRejCell = carinRow.createCell(3);
		carinRejCell.setCellStyle(normalCellStyle);
		carinRejCell.setCellValue(summaryTotalsMap.get("CARIN").getTotalRjctdMsgs().intValue());

		Cell carinExtServCell = carinRow.createCell(4);
		carinExtServCell.setCellStyle(serviceCellStyle);
		carinExtServCell.setCellValue("CAROT");

		Cell carotExtCell = carinRow.createCell(5);
		carotExtCell.setCellStyle(normalCellStyle);
		carotExtCell.setCellValue(summaryTotalsMap.get("CARIN").getTotalNrOfExtMsgs().intValue());


		//Subtotal Creditor Banks
		//SubTotal Data
		Row crSubTotalRow = summaryInfoSheet.createRow(7);

		Cell crStSummCell = crSubTotalRow.createCell(0);
		crStSummCell.setCellStyle(summSubtotalCellStyle);
		crStSummCell.setCellValue("Sub Total");

		Cell subTotalNrOfMsgsCell = crSubTotalRow.createCell(1);
		subTotalNrOfMsgsCell.setCellStyle(summSubtotalCellStyle);
		subTotalNrOfMsgsCell.setCellType(CellType.FORMULA);
		subTotalNrOfMsgsCell.setCellFormula("SUM(B7)");

		Cell subTotAccpCell = crSubTotalRow.createCell(2);
		subTotAccpCell.setCellStyle(summSubtotalCellStyle);
		subTotAccpCell.setCellType(CellType.FORMULA);
		subTotAccpCell.setCellFormula("SUM(C7)");

		Cell subTotalRejCell = crSubTotalRow.createCell(3);
		subTotalRejCell.setCellStyle(summSubtotalCellStyle);
		subTotalRejCell.setCellType(CellType.FORMULA);
		subTotalRejCell.setCellFormula("SUM(D7)");

		Cell nullCell4 = crSubTotalRow.createCell(4);
		nullCell4.setCellStyle(summSubtotalCellStyle);
		nullCell4.setCellValue("Sub Total");

		Cell subTotalExtMsgsCell = crSubTotalRow.createCell(5);
		subTotalExtMsgsCell.setCellStyle(summSubtotalCellStyle);
		subTotalExtMsgsCell.setCellType(CellType.FORMULA);
		subTotalExtMsgsCell.setCellFormula("SUM(F7)");

		Row drBanksRow = summaryInfoSheet.createRow(8);
		Cell drBanksCell = drBanksRow.createCell(0);
		drBanksCell.setCellStyle(subTitleCellStyle);
		drBanksCell.setCellValue("Debtor Banks");

		summaryInfoSheet.addMergedRegion(new CellRangeAddress(8,8, 0, 1));

		//ST201 Data
		Row st101Row = summaryInfoSheet.createRow(9);
		Cell st101SummCell = st101Row.createCell(0);
		st101SummCell.setCellStyle(serviceCellStyle);
		st101SummCell.setCellValue("ST201");

		Cell st101TotalCell = st101Row.createCell(1);
		st101TotalCell.setCellStyle(normalCellStyle);
		st101TotalCell.setCellValue(summaryTotalsMap.get("ST201").getTotalNrOfMsgs().intValue());

		Cell st101AccpCell = st101Row.createCell(2);
		st101AccpCell.setCellStyle(normalCellStyle);
		st101AccpCell.setCellValue(summaryTotalsMap.get("ST201").getTotalAccpMsgs().intValue());

		Cell st101RejCell = st101Row.createCell(3);
		st101RejCell.setCellStyle(normalCellStyle);
		st101RejCell.setCellValue(summaryTotalsMap.get("ST201").getTotalRjctdMsgs().intValue());

		Cell st101ExtServCell = st101Row.createCell(4);
		st101ExtServCell.setCellStyle(serviceCellStyle);
		st101ExtServCell.setCellValue("ST203");

		Cell st103ExtCell = st101Row.createCell(5);
		st103ExtCell.setCellStyle(normalCellStyle);
		st103ExtCell.setCellValue(summaryTotalsMap.get("ST201").getTotalNrOfExtMsgs().intValue());

		//RCAIN Data
		Row manacRow = summaryInfoSheet.createRow(10);
		Cell manacSummCell = manacRow.createCell(0);
		manacSummCell.setCellStyle(serviceCellStyle);
		manacSummCell.setCellValue("RCAIN");

		Cell manacTotalCell = manacRow.createCell(1);
		manacTotalCell.setCellStyle(normalCellStyle);
		manacTotalCell.setCellValue(summaryTotalsMap.get("RCAIN").getTotalNrOfMsgs().intValue());

		Cell manacAccpCell = manacRow.createCell(2);
		manacAccpCell.setCellStyle(normalCellStyle);
		manacAccpCell.setCellValue(summaryTotalsMap.get("RCAIN").getTotalAccpMsgs().intValue());

		Cell manacRejCell = manacRow.createCell(3);
		manacRejCell.setCellStyle(normalCellStyle);
		manacRejCell.setCellValue(summaryTotalsMap.get("RCAIN").getTotalRjctdMsgs().intValue());

		Cell manacExtServCell = manacRow.createCell(4);
		manacExtServCell.setCellStyle(serviceCellStyle);
		manacExtServCell.setCellValue("RCAOT");

		Cell manacExtCell = manacRow.createCell(5);
		manacExtCell.setCellStyle(normalCellStyle);
		manacExtCell.setCellValue(summaryTotalsMap.get("RCAIN").getTotalNrOfExtMsgs().intValue());


		//Subtotal Debtor Banks
		//SubTotal Data
		Row drSubTotalRow = summaryInfoSheet.createRow(11);

		Cell drStSummCell = drSubTotalRow.createCell(0);
		drStSummCell.setCellStyle(summSubtotalCellStyle);
		drStSummCell.setCellValue("Sub Total");

		Cell drsubTotalNrOfMsgsCell = drSubTotalRow.createCell(1);
		drsubTotalNrOfMsgsCell.setCellStyle(summSubtotalCellStyle);
		drsubTotalNrOfMsgsCell.setCellType(CellType.FORMULA);
		drsubTotalNrOfMsgsCell.setCellFormula("SUM(B9:B10)");

		Cell drsubTotAccpCell = drSubTotalRow.createCell(2);
		drsubTotAccpCell.setCellStyle(summSubtotalCellStyle);
		drsubTotAccpCell.setCellType(CellType.FORMULA);
		drsubTotAccpCell.setCellFormula("SUM(C9:C10)");

		Cell drsubTotalRejCell = drSubTotalRow.createCell(3);
		drsubTotalRejCell.setCellStyle(summSubtotalCellStyle);
		drsubTotalRejCell.setCellType(CellType.FORMULA);
		drsubTotalRejCell.setCellFormula("SUM(D9:D10)");

		Cell nullCell1 = drSubTotalRow.createCell(4);
		nullCell1.setCellStyle(summSubtotalCellStyle);
		nullCell1.setCellValue("Sub Total");

		Cell drsubTotalExtMsgsCell = drSubTotalRow.createCell(5);
		drsubTotalExtMsgsCell.setCellStyle(summSubtotalCellStyle);
		drsubTotalExtMsgsCell.setCellType(CellType.FORMULA);
		drsubTotalExtMsgsCell.setCellFormula("SUM(F9:F10)");

		//TOTAL Data
		Row totalRow = summaryInfoSheet.createRow(12);
		Cell totalSummCell = totalRow.createCell(0);
		totalSummCell.setCellStyle(serviceCellStyle);
		totalSummCell.setCellValue("Total Processed");

		Cell nrOfMsgsTotalCell = totalRow.createCell(1);
		nrOfMsgsTotalCell.setCellStyle(serviceCellStyle);
		nrOfMsgsTotalCell.setCellType(CellType.FORMULA);
		nrOfMsgsTotalCell.setCellFormula("SUM(B7,B11)");

		Cell accpMsgsCell = totalRow.createCell(2);
		accpMsgsCell.setCellStyle(serviceCellStyle);
		accpMsgsCell.setCellType(CellType.FORMULA);
		accpMsgsCell.setCellFormula("SUM(C7,C11)");

		Cell rejMsgsCell = totalRow.createCell(3);
		rejMsgsCell.setCellStyle(serviceCellStyle);
		rejMsgsCell.setCellType(CellType.FORMULA);
		rejMsgsCell.setCellFormula("SUM(D7,D11)");

		Cell nullCell = totalRow.createCell(4);
		nullCell.setCellStyle(serviceCellStyle);
		nullCell.setCellValue("Total Extracted");

		Cell extMsgsCell = totalRow.createCell(5);
		extMsgsCell.setCellStyle(serviceCellStyle);
		extMsgsCell.setCellType(CellType.FORMULA);
		extMsgsCell.setCellFormula("SUM(F7,F11)");

		//Percentage Data
		Row totalPrecRow = summaryInfoSheet.createRow(14);
		Cell totalPercCell = totalPrecRow.createCell(0);
		totalPercCell.setCellStyle(serviceCellStyle);
		totalPercCell.setCellValue("Acceptance %");

		Cell nullPercCell = totalPrecRow.createCell(1);

		Cell accpPercCell = totalPrecRow.createCell(2);
		accpPercCell.setCellStyle(normalPercCellStyle);
		accpPercCell.setCellType(CellType.FORMULA);
		accpPercCell.setCellFormula("IF(B12>0,C12/B12,0)");

		Cell rejPercCell = totalPrecRow.createCell(3);
		rejPercCell.setCellStyle(normalPercCellStyle);
		rejPercCell.setCellType(CellType.FORMULA);
		rejPercCell.setCellFormula("IF(B12>0,D12/B12,0)");

		//Status Report Summary
		//GENERATE SUB TITLE ROW
		Row subHdrRow12 = summaryInfoSheet.createRow(15);

		Cell subHdrCrdCell12 = subHdrRow12.createCell(0);
		subHdrCrdCell12.setCellStyle(subHdrCellStyle);
		subHdrCrdCell12.setCellValue("STATUS REPORTS SUMMARY INFORMATION");

		//Merge Cell 0 to 4
		summaryInfoSheet.addMergedRegion(new CellRangeAddress(15,15, 0, 5));

		//GENERATE COLUMN HEADERS
		Row stsSummHdrRow = summaryInfoSheet.createRow(16);

		Cell stsRepCell = stsSummHdrRow.createCell(0);
		stsRepCell.setCellStyle(headerCellStyle);
		stsRepCell.setCellValue("INPUT SERVICE");;

		Cell stsNrOfTxnsCell = stsSummHdrRow.createCell(1);
		stsNrOfTxnsCell.setCellStyle(headerCellStyle);
		stsNrOfTxnsCell.setCellValue("TOT NR OF STATUS REPORTS");

		//Populate Summary Information
		Row stsCrBanksRow = summaryInfoSheet.createRow(17);
		Cell stsCrBanksCell = stsCrBanksRow.createCell(0);
		stsCrBanksCell.setCellStyle(subTitleCellStyle);
		stsCrBanksCell.setCellValue("Creditor Banks");

		summaryInfoSheet.addMergedRegion(new CellRangeAddress(17,17, 0, 1));

		Row st100Row = summaryInfoSheet.createRow(18);
		Cell st100SummCell = st100Row.createCell(0);
		st100SummCell.setCellStyle(serviceCellStyle);
		st100SummCell.setCellValue("ST200");

		Cell st100TotalCell = st100Row.createCell(1);
		st100TotalCell.setCellStyle(normalCellStyle);
		log.info("ST200: "+ summaryTotalsMap.get("ST200").getTotalNrOfMsgs().toString());
		st100TotalCell.setCellValue(summaryTotalsMap.get("ST200").getTotalNrOfMsgs().intValue());


		//Subtotal Creditor Banks Status Reports
		Row crStsSubTotRow = summaryInfoSheet.createRow(19);

		Cell crStsSubTotalCell = crStsSubTotRow.createCell(0);
		crStsSubTotalCell.setCellStyle(summSubtotalCellStyle);
		crStsSubTotalCell.setCellValue("Sub Total");

		Cell crStsSubTotNrOfMsgsCell = crStsSubTotRow.createCell(1);
		crStsSubTotNrOfMsgsCell.setCellStyle(summSubtotalCellStyle);
		crStsSubTotNrOfMsgsCell.setCellType(CellType.FORMULA);
		crStsSubTotNrOfMsgsCell.setCellFormula("SUM(B18)");

		//Populate Summary Information
		Row stsDrBanksRow = summaryInfoSheet.createRow(20);
		Cell stsDrBanksCell = stsDrBanksRow.createCell(0);
		stsDrBanksCell.setCellStyle(subTitleCellStyle);
		stsDrBanksCell.setCellValue("Debtor Banks");

		summaryInfoSheet.addMergedRegion(new CellRangeAddress(20,20, 0, 1));

		Row st102Row = summaryInfoSheet.createRow(21);
		Cell st102SummCell = st102Row.createCell(0);
		st102SummCell.setCellStyle(serviceCellStyle);
		st102SummCell.setCellValue("ST202");

		Cell st102TotalCell = st102Row.createCell(1);
		st102TotalCell.setCellStyle(normalCellStyle);
		st102TotalCell.setCellValue(summaryTotalsMap.get("ST202").getTotalNrOfMsgs().intValue());

		Row st104Row = summaryInfoSheet.createRow(22);
		Cell st104SummCell = st104Row.createCell(0);
		st104SummCell.setCellStyle(serviceCellStyle);
		st104SummCell.setCellValue("ST204");

		Cell st104TotalCell = st104Row.createCell(1);
		st104TotalCell.setCellStyle(normalCellStyle);
		st104TotalCell.setCellValue(summaryTotalsMap.get("ST204").getTotalNrOfMsgs().intValue());


		//Subtotal Debtor Banks Status Reports
		Row drStsSubTotRow = summaryInfoSheet.createRow(23);

		Cell drStsSubTotalCell = drStsSubTotRow.createCell(0);
		drStsSubTotalCell.setCellStyle(summSubtotalCellStyle);
		drStsSubTotalCell.setCellValue("Sub Total");

		Cell drStsSubTotNrOfMsgsCell = drStsSubTotRow.createCell(1);
		drStsSubTotNrOfMsgsCell.setCellStyle(summSubtotalCellStyle);
		drStsSubTotNrOfMsgsCell.setCellType(CellType.FORMULA);
		drStsSubTotNrOfMsgsCell.setCellFormula("SUM(B21:B22)");

		//STATUS REPORT TOTAL Data
		Row stsTotalRow = summaryInfoSheet.createRow(24);
		Cell totalProcessCell = stsTotalRow.createCell(0);
		totalProcessCell.setCellStyle(serviceCellStyle);
		totalProcessCell.setCellValue("Total Processed");

		Cell totTxnsCell = stsTotalRow.createCell(1);
		totTxnsCell.setCellStyle(serviceCellStyle);
		totTxnsCell.setCellType(CellType.FORMULA);
		totTxnsCell.setCellFormula("SUM(B19,B23)");
	}

	public void loadVolumeData(String strFromDate)
	{
		inputFromCreditorBanksMap = new TreeMap<String, List<MonthlyVolumeCountEntityModel>>();
		outputToDebtorBanksMap = new TreeMap<String, List<MonthlyVolumeCountEntityModel>>();
		inputFromDebtorBankMap =  new TreeMap<String, List<MonthlyVolumeCountEntityModel>>(); 
		outputToCreditorBankMap =  new TreeMap<String, List<MonthlyVolumeCountEntityModel>>(); 
		statusReportToCreditorsMap =  new TreeMap<String, List<MonthlyVolumeCountEntityModel>>(); 
		statusReportToDebtorsMap =  new TreeMap<String, List<MonthlyVolumeCountEntityModel>>(); 

		creditorBankModelList = new ArrayList<CreditorBankModel>();
		creditorBankModelList=(List<CreditorBankModel>)  adminBeanRemote.retrieveActiveCreditorBank();

		debtorBankModelList = new ArrayList<DebtorBankModel>();
		debtorBankModelList = (List<DebtorBankModel>) adminBeanRemote.retrieveActiveDebtorBank();

		servicesList = new ArrayList<ServicesModel>();
		servicesList = adminBeanRemote.viewAllServices();	


		//INPUT FROM CREDITOR BANKS
		inputFromCreditorCountList = new ArrayList<MonthlyVolumeCountEntityModel>();
		inputFromCreditorCountList = (List<MonthlyVolumeCountEntityModel>) reportBeanRemote.retrieveBatchDailyTransFromCreditor(strFromDate); 
		//		log.info("inputFromCreditorCountList.size() ==> "+inputFromCreditorCountList.size());

		if(inputFromCreditorCountList != null && inputFromCreditorCountList.size() > 0)
		{
			for (ServicesModel servicesModel : servicesList) 
			{
				//				log.info("servicesModel ===>"+servicesModel);
				if(servicesModel.getServiceIdIn() != null && !servicesModel.getServiceIdIn().isEmpty()) 
				{
					List<MonthlyVolumeCountEntityModel>  inputBankDataPerCreditorList = new ArrayList<MonthlyVolumeCountEntityModel>();
					for (MonthlyVolumeCountEntityModel creditorVolumeEntity : inputFromCreditorCountList) 
					{
						if(creditorVolumeEntity.getService().equalsIgnoreCase(servicesModel.getServiceIdIn()))
						{
							inputBankDataPerCreditorList.add(creditorVolumeEntity);
						}
					}
					inputFromCreditorBanksMap.put(servicesModel.getServiceIdIn(), inputBankDataPerCreditorList);
				}
			}
		}

		//OUTPUT TO DEBTOR BANKS
		outputToDebtorCountList = new ArrayList<MonthlyVolumeCountEntityModel>();
		outputToDebtorCountList = (List<MonthlyVolumeCountEntityModel>) reportBeanRemote.batchDailyTransExtractedToDebtorBank(strFromDate);
		//		log.info("outputToDebtorCountList.size() ==> "+outputToDebtorCountList.size());

		if(outputToDebtorCountList != null && outputToDebtorCountList.size() > 0)
		{
			for (ServicesModel servicesModel : servicesList) 
			{
				//				log.info("servicesModel ===>"+servicesModel);
				if(servicesModel.getServiceIdOut() != null && !servicesModel.getServiceIdOut().isEmpty()) 
				{
					List<MonthlyVolumeCountEntityModel>  outputBankDataPerCreditorList = new ArrayList<MonthlyVolumeCountEntityModel>();
					for (MonthlyVolumeCountEntityModel debtorVolumeEntity : outputToDebtorCountList) 
					{
						if(debtorVolumeEntity.getService().equalsIgnoreCase(servicesModel.getServiceIdOut()))
						{
							outputBankDataPerCreditorList.add(debtorVolumeEntity);
						}
					}
					outputToDebtorBanksMap.put(servicesModel.getServiceIdOut(), outputBankDataPerCreditorList);
				}
			}
		}

		//INPUT FROM DEBTOR BANKS
		inputFromDebtorCountList = new ArrayList<MonthlyVolumeCountEntityModel>();
		inputFromDebtorCountList = (List<MonthlyVolumeCountEntityModel>) reportBeanRemote.retrieveBatchDailyTransFromDebtor(strFromDate);
		//		log.info("inputFromDebtorCountList.size() ==> "+inputFromDebtorCountList.size());

		if(inputFromDebtorCountList != null && inputFromDebtorCountList.size() > 0)
		{
			for (ServicesModel servicesModel : servicesList) 
			{
				//				log.info("servicesModel ===>"+servicesModel);
				if(servicesModel.getServiceIdIn() != null && !servicesModel.getServiceIdIn().isEmpty()) 
				{
					List<MonthlyVolumeCountEntityModel>  inputBankDataPerDebtorList = new ArrayList<MonthlyVolumeCountEntityModel>();
					for (MonthlyVolumeCountEntityModel debtorVolumeEntity : inputFromDebtorCountList) 
					{
						if(debtorVolumeEntity.getService().equalsIgnoreCase(servicesModel.getServiceIdIn()))
						{
							inputBankDataPerDebtorList.add(debtorVolumeEntity);
						}
					}
					inputFromDebtorBankMap.put(servicesModel.getServiceIdIn(), inputBankDataPerDebtorList);
				}
			}
		}

		//OUTPUT TO CREDITOR BANKS
		outputToCreditorCountList = new ArrayList<MonthlyVolumeCountEntityModel>();
		outputToCreditorCountList = (List<MonthlyVolumeCountEntityModel>) reportBeanRemote.batchDailyTransExtractedToCreditorBank(strFromDate);
		//		log.info("outputToCreditorCountList.size() ==> "+outputToCreditorCountList.size());

		if(outputToCreditorCountList != null && outputToCreditorCountList.size() > 0)
		{
			for (ServicesModel servicesModel : servicesList) 
			{
				//				log.info("servicesModel ===>"+servicesModel);
				if(servicesModel.getServiceIdOut() != null && !servicesModel.getServiceIdOut().isEmpty()) 
				{
					List<MonthlyVolumeCountEntityModel>  outputBankDataPerCreditorList = new ArrayList<MonthlyVolumeCountEntityModel>();
					for (MonthlyVolumeCountEntityModel creditorVolumeEntity : outputToCreditorCountList) 
					{
						if(creditorVolumeEntity.getService().equalsIgnoreCase(servicesModel.getServiceIdOut()))
						{
							outputBankDataPerCreditorList.add(creditorVolumeEntity);
						}
					}
					outputToCreditorBankMap.put(servicesModel.getServiceIdOut(), outputBankDataPerCreditorList);
				}
			}
		}

		//STATUS TO CREDITOR BANKS
		statusReportToCreditorsCountList = new ArrayList<MonthlyVolumeCountEntityModel>();
		statusReportToCreditorsCountList = (List<MonthlyVolumeCountEntityModel>) reportBeanRemote.retrieveBatchDailyStReportCreditor(strFromDate);
		//		log.info("statusReportToCreditorsCountList.size() ==> "+statusReportToCreditorsCountList.size());

		if(statusReportToCreditorsCountList != null && statusReportToCreditorsCountList.size() > 0)
		{
			for (ServicesModel servicesModel : servicesList) 
			{
				//				log.info("servicesModel ===>"+servicesModel);
				if(servicesModel.getServiceIdOut() != null && !servicesModel.getServiceIdOut().isEmpty()) 
				{
					List<MonthlyVolumeCountEntityModel>  statusReportCrCountList = new ArrayList<MonthlyVolumeCountEntityModel>();
					for (MonthlyVolumeCountEntityModel statusCredCountEntity : statusReportToCreditorsCountList) 
					{
						if(statusCredCountEntity.getService().equalsIgnoreCase(servicesModel.getServiceIdOut()))
						{
							statusReportCrCountList.add(statusCredCountEntity);
						}
					}

					if(statusReportCrCountList != null && statusReportCrCountList.size() > 0)
						statusReportToCreditorsMap.put(servicesModel.getServiceIdOut(), statusReportCrCountList);
				}
			}
		}

		//STATUS TO DEBTOR BANKS
		statusReportToDebtorsCountList = new ArrayList<MonthlyVolumeCountEntityModel>();
		statusReportToDebtorsCountList = (List<MonthlyVolumeCountEntityModel>) reportBeanRemote.retrieveBatchDailyStReportDebtor(strFromDate);
		//		log.info("statusReportToDebtorsCountList.size() ==> "+statusReportToDebtorsCountList.size());

		if(statusReportToDebtorsCountList != null && statusReportToDebtorsCountList.size() > 0)
		{
			for (ServicesModel servicesModel : servicesList) 
			{
				//				log.info("servicesModel ===>"+servicesModel);
				if(servicesModel.getServiceIdOut() != null && !servicesModel.getServiceIdOut().isEmpty()) 
				{
					List<MonthlyVolumeCountEntityModel>  statusReportDrCountList = new ArrayList<MonthlyVolumeCountEntityModel>();
					for (MonthlyVolumeCountEntityModel statusDebtCountEntity : statusReportToDebtorsCountList) 
					{
						if(statusDebtCountEntity.getService().equalsIgnoreCase(servicesModel.getServiceIdOut()))
						{
							statusReportDrCountList.add(statusDebtCountEntity);
						}
					}

					if(statusReportDrCountList != null && statusReportDrCountList.size() > 0)
						statusReportToDebtorsMap.put(servicesModel.getServiceIdOut(), statusReportDrCountList);
				}
			}
		}
	}

	public  void copyFile(String fileName) throws IOException 
	{
		File tmpFile = new File(tempDir, fileName);
		File destFile = new File(reportDir, fileName);

		//		log.info("tmpFile ==> "+ tmpFile);
		//		log.info("destFile===> "+ destFile);

		Files.copy(tmpFile, destFile);
		log.info("Copying "+fileName+" from temp to output directory...");
		//2020/03/20-CHG-191677 - remove pdf versions
		//		convertToPdf(tmpFile);
	}

	//	public void convertToPdf(File tmpFile) throws IOException
	//	{
	//		log.info("GENERATING "+reportNr+" PDF REPORT  !!!!! ");
	//
	//		FileInputStream input_document = new FileInputStream(tmpFile);
	//		// Read workbook into HSSFWorkbook
	//		XSSFWorkbook my_xls_workbook = new XSSFWorkbook(input_document); 
	//
	//		DailyBatchVolumeReportPDF mr022PdfReport = new DailyBatchVolumeReportPDF();
	//		try 
	//		{
	//			mr022PdfReport.generateReport(my_xls_workbook);
	//		} 
	//		catch (DocumentException e) 
	//		{
	//			log.info("Error on converting to PDF: "+e.getMessage());
	//			e.printStackTrace();
	//		}
	//	}

	//CONTEXT LOOKUPS//
	public static void contextValidationBeanCheck() 
	{
		if (valBeanRemote == null) {
			valBeanRemote = Util.getValidationBean();
		}
	}

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
