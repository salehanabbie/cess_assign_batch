package com.bsva.reports;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
public class MR020_BSAMonthlyBatchVolumesReport 
{
	long startTime = System.nanoTime();
	public static Logger log=Logger.getLogger(MR020_BSAMonthlyBatchVolumesReport.class);
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
	String reportName, reportNr,recipientNr, reportDir = null, tempDir = null;
	String xlsFileName = null, mr020 = null;
	String invBank = null;
	int fileSeqNo =000;
	int rowCount = 0;
	int nrOfBanks = 0;
	
	String activeIndicator = "Y";

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

	public void generateReport(Date frontFromDate,Date frontToDate) throws DocumentException, IOException
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
			tempDir = propertyUtilRemote.getPropValue("ExtractTemp.Out");
			log.debug("tempDir ==> "+tempDir);
			reportDir= propertyUtilRemote.getPropValue("Reports.Output");
			log.debug("reportDir ==> "+reportDir);
			//Retrieve Report Name here
			mr020 = propertyUtilRemote.getPropValue("RPT.MONTHLY.BATCH.VOLUMES");
			invBank = propertyUtilRemote.getPropValue("ERROR_CODES_REPORT_INV_BANK");
			recipientNr = propertyUtilRemote.getPropValue("AC.ACH.RPT.RECIPIENT.NUMBER");
		}
		catch(Exception ex)
		{
			log.error("MR020- Could not find MandateMessageCommons.properties in classpath");	
			reportDir = "/home/opsjava/Delivery/Mandates/Output/Reports/";
			tempDir="/home/opsjava/Delivery/Mandates/Output/temp/";
			invBank = "INVBNK";
		}

		//Retrieve Report Name
		CasCnfgReportNamesEntity reportNameEntity = new CasCnfgReportNamesEntity();
		reportNameEntity = (CasCnfgReportNamesEntity) adminBeanRemote.retrieveReportName(mr020);

		if(reportNameEntity != null)
		{
			if(reportNameEntity.getActiveInd().equalsIgnoreCase(activeIndicator)) {
				reportNr = reportNameEntity.getReportNr();
				reportName = reportNameEntity.getReportName();
				
				log.info("*****REPORT "+reportNr+" GENERATING*****");
				generateReportDetail(reportNr, reportName,frontFromDate,frontToDate);

				long endTime = System.nanoTime();
				long duration = (endTime - startTime) / 1000000;
				log.info("[MR020 Report Duration: "+DurationFormatUtils.formatDuration(duration, "HH:mm:ss.S")+" milliseconds |");
			}
		}
	}

	public void generateReportDetail(String reportNo,String reportname,Date frontFromDate,Date frontToDate) throws DocumentException, IOException
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
		String strMonthFront = null;
		String monthFront = null;
		String yearFront = null;
		Date firstDate, lastDate = null;
		String strFromDate = null, strToDate = null;
		rowCount = 0;

		if(frontFromDate != null && frontToDate != null) {
			fileName = tempDir+recipientNr+reportNo+"AC"+reportDate.format(frontToDate).toString()+".xlsx";
			xlsFileName  = recipientNr+reportNo+"AC"+reportDate.format(frontToDate).toString()+".xlsx";
		}else {
			fileName = tempDir+recipientNr+reportNo+"AC"+reportDate.format(currentDate).toString()+".xlsx";
			xlsFileName  = recipientNr+reportNo+"AC"+reportDate.format(currentDate).toString()+".xlsx";
		}

		String inpFromCreditorsSheetName = "BSA BATCH PROD VOLUMES";//name of inpFromCreditorsSheet

//		strMonth = fileTimeFormat.format(systemParameterModel.getProcessDate()).substring(0,2);
//		month= DateUtil.getMonthFullname(Integer.valueOf(strMonth), true);
//		//		log.info("Process Month----->"+month);
//		year = String.valueOf(DateUtil.getYear(systemParameterModel.getProcessDate()));
//		//		log.info("year----->"+year);
		
		if (frontToDate != null) {
			strMonthFront = fileTimeFormat.format(frontToDate).substring(0, 2);
			monthFront = DateUtil.getMonthFullname(Integer.valueOf(strMonthFront), true);
			yearFront = String.valueOf(DateUtil.getYear(frontToDate));
		}
		
		if(systemParameterModel.getProcessDate() != null)
		{
			strMonth =fileTimeFormat.format(systemParameterModel.getProcessDate()).substring(0,2);
			log.debug("strMonth ==> "+strMonth);
			month= DateUtil.getMonthFullname(Integer.valueOf(strMonth), true);
			log.debug("Process Month----->"+month);
			year = String.valueOf(DateUtil.getYear(systemParameterModel.getProcessDate()));
			log.debug("year----->"+year);

			//Calculate the last date of the month
			Calendar nextNotifTime = Calendar.getInstance();
			nextNotifTime.add(Calendar.MONTH, 1);
			nextNotifTime.set(Calendar.DATE, 1);
			nextNotifTime.add(Calendar.DATE, -1);
			lastDate = nextNotifTime.getTime();

			//strFirstDate = "01"+strMonth+year;
			strFromDate=year+"-"+strMonth+"-01";
			strToDate = dateFormat.format(lastDate);

//			strFromDate = "2018-06-01";
//			strToDate = "2018-06-30";
//			log.info("strFromDate ==>" + strFromDate);
//			log.info("strToDate ==> "+ strToDate);
		}

		//Load Data
		if(frontFromDate != null && frontToDate != null) {
			log.info("strFromDate ==>" + dateFormat.format(frontFromDate));
			log.info("strToDate ==> "+ dateFormat.format(frontToDate));
			loadVolumeData(dateFormat.format(frontFromDate), dateFormat.format(frontToDate));
		}else {
			log.info("strFromDate ==>" + strFromDate);
			log.info("strToDate ==> "+ strToDate);
			loadVolumeData(strFromDate, strToDate);
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
		
		if(frontFromDate != null && frontToDate != null) {
			//_______________________SHEET 2 - INPUT FROM CREDITOR BANKS__________________________________//
			generateCreditorInfoSheet(inpFromCreditorsSheet, reportNo,reportName, monthFront, yearFront);

			//_______________________SHEET 3 - INPUT FROM DEBTOR BANKS___________________________________//
			generateDebtorInfoSheet(inpFromDebtorsSheet, reportNo,reportName, monthFront, yearFront);

			//_______________________SHEET 4 - STATUS REPORT INFORMATION________________________________//
			generateStatusReportInfoSheet(statusReportsSheet, reportNo,reportName, monthFront, yearFront);

			//_______________________SHEET 1 - SUMMARY INFORMATION______________________________________//
			generateSummarySheet(summaryInfoSheet, reportNo,reportName, monthFront, yearFront);
		}else {
			//_______________________SHEET 2 - INPUT FROM CREDITOR BANKS__________________________________//
			generateCreditorInfoSheet(inpFromCreditorsSheet, reportNo,reportName, month, year);

			//_______________________SHEET 3 - INPUT FROM DEBTOR BANKS___________________________________//
			generateDebtorInfoSheet(inpFromDebtorsSheet, reportNo,reportName, month, year);

			//_______________________SHEET 4 - STATUS REPORT INFORMATION________________________________//
			generateStatusReportInfoSheet(statusReportsSheet, reportNo,reportName, month, year);

			//_______________________SHEET 1 - SUMMARY INFORMATION______________________________________//
			generateSummarySheet(summaryInfoSheet, reportNo,reportName, month, year);
		}

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
			log.error("Error on copying MR020 report to temp "+ioe.getMessage());
			ioe.printStackTrace();
		}
		catch(Exception ex)
		{
			log.error("Error on copying MR020 report to temp "+ex.getMessage());
			ex.printStackTrace();
		}
	}

	public void generateCreditorInfoSheet(Sheet inpFromCreditorsSheet, String reportNo,String reportName, String month, String year)
	{
		//Sheet 2(Input from Creditor Bank Details)
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


		//GENERATE TITLE ROW 
		Row titleRow = inpFromCreditorsSheet.createRow(0);

		//TITLE ROW
		Cell titleCell = titleRow.createCell(0);
		titleCell.setCellStyle(titleCellStyle);
//		titleCell.setCellValue(reportNo+" - BANKSERVAFRICA PRODUCTION - TRANSACTION VOLUME - MANDATES BATCH - "+month.toUpperCase()+" "+year);
		titleCell.setCellValue(reportNo+" - "+reportName+" - "+month.toUpperCase()+" "+year);

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


		//		========================MANIN============================//
		int rowCount = 6;
		int debtorCount = 6;

		//Retrieve MANIN Counts
		//		2018-09-07 SalehaR - Removed as it is replaced by loadCreditorData()
		//		List<MonthlyVolumeCountEntityModel> maninCountList = (List<MonthlyVolumeCountEntityModel>) beanRemote.retrieveMndtCountByCreditorBanks(strFromDate,strToDate,"MANIN");

		if(inputFromCreditorCountList != null && inputFromCreditorCountList.size() > 0)
		{	
			BigDecimal stTotal = BigDecimal.ZERO;
			BigDecimal stAccptd = BigDecimal.ZERO;
			BigDecimal stRejected = BigDecimal.ZERO;
			BigDecimal stExtracted= BigDecimal.ZERO;

			int fstCount = 0;
			int outfstCount = 0;
			Row maninRow = null;
			//			//Retrieve the maninCount
			List<MonthlyVolumeCountEntityModel> maninCountList = inputFromCreditorBanksMap.get("MANIN");

			if(maninCountList != null && maninCountList.size() > 0)
			{	
				for (MonthlyVolumeCountEntityModel maninCountEntity : maninCountList) 
				{
					maninRow = inpFromCreditorsSheet.createRow(rowCount);
					Cell maninServCell = maninRow.createCell(0);

					if(fstCount == 0)
					{
						maninServCell.setCellStyle(serviceCellStyle);
						maninServCell.setCellValue("MANIN");
						fstCount = 1;
					}
					else
					{
						maninServCell.setCellStyle(normalCellStyle);
					}

					Cell creditorBank = maninRow.createCell(1);
					creditorBank.setCellStyle(normalCellStyle);
					creditorBank.setCellValue(maninCountEntity.getInstId());

					Cell totalNrMsgs = maninRow.createCell(2);
					totalNrMsgs.setCellStyle(normalCellStyle);
					totalNrMsgs.setCellValue(maninCountEntity.getNrOfMsgs().intValue());
					stTotal = stTotal.add(maninCountEntity.getNrOfMsgs());

					Cell accptdMsgs = maninRow.createCell(3);
					accptdMsgs.setCellStyle(acceptedCellStyle);
					accptdMsgs.setCellValue(maninCountEntity.getNrOfAccpMsgs().intValue());
					stAccptd = stAccptd.add(maninCountEntity.getNrOfAccpMsgs());

					Cell rjctdMsgs = maninRow.createCell(4);
					rjctdMsgs.setCellStyle(rejectedCellStyle);
					rjctdMsgs.setCellValue(maninCountEntity.getNrOfRjctMsgs().intValue());
					stRejected = stRejected.add(maninCountEntity.getNrOfRjctMsgs());

					//Move to next row
					rowCount++;
				}

				//SubTotal the Rows
				Row maninSTRow = inpFromCreditorsSheet.createRow(rowCount);

				Cell maninServ = maninSTRow.createCell(0);
				maninServ.setCellStyle(subTotalCellStyle);

				Cell maninBank = maninSTRow.createCell(1);
				maninBank.setCellStyle(subTotalCellStyle);
				maninBank.setCellValue("Total");

				Cell sttotalNrMsgs = maninSTRow.createCell(2);
				sttotalNrMsgs.setCellStyle(subTotalCellStyle);
				sttotalNrMsgs.setCellValue(stTotal.intValue());

				Cell staccp = maninSTRow.createCell(3);
				staccp.setCellStyle(subTotalCellStyle);
				staccp.setCellValue(stAccptd.intValue());

				Cell stRjct = maninSTRow.createCell(4);
				stRjct.setCellStyle(subTotalCellStyle);
				stRjct.setCellValue(stRejected.intValue());

				//Add to Grand Total
				gtTotal = gtTotal.add(stTotal);
				gtAccptd = gtAccptd.add(stAccptd);
				gtRejected = gtRejected.add(stRejected);
				
				rowCount++;
			}	


			//			Retrieve the manotCount
			List<MonthlyVolumeCountEntityModel> manotCountList = outputToDebtorBanksMap.get("MANOT");
			//			log.info("manotCountList.size() ===>"+manotCountList.size()); 
			if(manotCountList != null && manotCountList.size() > 0)
			{	
				for (MonthlyVolumeCountEntityModel manotCountEntity : manotCountList) 
				{
					Row manotRow = inpFromCreditorsSheet.getRow(debtorCount);
					Cell manotServCell = manotRow.createCell(6);
					if(outfstCount == 0)
					{
						manotServCell.setCellStyle(serviceCellStyle);
						manotServCell.setCellValue("MANOT");
						outfstCount = 1;
					}
					else
					{
						manotServCell.setCellStyle(normalCellStyle);
					}

					Cell debtorBank = manotRow.createCell(7);
					debtorBank.setCellStyle(normalCellStyle);
					debtorBank.setCellValue(manotCountEntity.getInstId());

					Cell totalNrExtrctdMsgs = manotRow.createCell(8);
					totalNrExtrctdMsgs.setCellStyle(normalCellStyle);
					totalNrExtrctdMsgs.setCellValue(manotCountEntity.getNrOfExtMsgs().intValue());
					stExtracted = stExtracted.add(manotCountEntity.getNrOfExtMsgs());
					//Move to next row
					debtorCount++;
				}

				//SubTotal the Rows
				Row extStRow = inpFromCreditorsSheet.getRow(rowCount-1);

				Cell manotServ = extStRow.createCell(6);
				manotServ.setCellStyle(subTotalCellStyle);

				Cell manotBank = extStRow.createCell(7);
				manotBank.setCellStyle(subTotalCellStyle);
				manotBank.setCellValue("Total");

				Cell sttotalNrExtMsgs = extStRow.createCell(8);
				sttotalNrExtMsgs.setCellStyle(subTotalCellStyle);
				sttotalNrExtMsgs.setCellValue(stExtracted.intValue());

				//Add to Grand Total
				gtExtracted = gtExtracted.add(stExtracted);
				
			}
			
			//POPULATE SUMMARY TOTALS
			MonthlyVolumeCountEntityModel maninTotalEntity = new MonthlyVolumeCountEntityModel();
			maninTotalEntity.setService("MANIN");
			maninTotalEntity.setTotalNrOfMsgs(stTotal);
			maninTotalEntity.setTotalAccpMsgs(stAccptd);
			maninTotalEntity.setTotalRjctdMsgs(stRejected);
			maninTotalEntity.setTotalNrOfExtMsgs(stExtracted);

			summaryTotalsMap.put("MANIN", maninTotalEntity);
		}

		//		log.info("rowCount ==> "+rowCount);
		//		log.info("initCount ==> "+debtorCount);

		//SubTotal the Rows
		Row emptyRow = inpFromCreditorsSheet.createRow(rowCount);
		rowCount++;
		debtorCount = rowCount;


		//		========================MANAM============================//
		//		2018-09-07 SalehaR - Removed as it is replaced by loadCreditorData()
		//		List<MonthlyVolumeCountEntityModel> manamCountList = (List<MonthlyVolumeCountEntityModel>) beanRemote.retrieveMndtCountByCreditorBanks(strFromDate,strToDate,"MANAM");

		if(inputFromCreditorCountList != null && inputFromCreditorCountList.size() > 0)
		{
			BigDecimal stTotal = BigDecimal.ZERO;
			BigDecimal stAccptd = BigDecimal.ZERO;
			BigDecimal stRejected = BigDecimal.ZERO;
			BigDecimal stExtracted= BigDecimal.ZERO;

			int firstCount = 0;
			int outfstCount = 0;
			//			//Retrieve the manamCount
			List<MonthlyVolumeCountEntityModel> manamCountList = inputFromCreditorBanksMap.get("MANAM");
			if(manamCountList != null && manamCountList.size() > 0)
			{
				for (MonthlyVolumeCountEntityModel manamCountEntity : manamCountList) 
				{
					Row manamRow = inpFromCreditorsSheet.createRow(rowCount);
					Cell manamServCell = manamRow.createCell(0);

					if(firstCount == 0)
					{
						manamServCell.setCellStyle(serviceCellStyle);
						manamServCell.setCellValue("MANAM");
						firstCount = 1;
					}
					else
					{
						manamServCell.setCellStyle(normalCellStyle);
					}

					Cell creditorBank = manamRow.createCell(1);
					creditorBank.setCellStyle(normalCellStyle);
					creditorBank.setCellValue(manamCountEntity.getInstId());

					Cell totalNrMsgs = manamRow.createCell(2);
					totalNrMsgs.setCellStyle(normalCellStyle);
					totalNrMsgs.setCellValue(manamCountEntity.getNrOfMsgs().intValue());
					stTotal = stTotal.add(manamCountEntity.getNrOfMsgs());

					Cell accptdMsgs = manamRow.createCell(3);
					accptdMsgs.setCellStyle(acceptedCellStyle);
					accptdMsgs.setCellValue(manamCountEntity.getNrOfAccpMsgs().intValue());
					stAccptd = stAccptd.add(manamCountEntity.getNrOfAccpMsgs());

					Cell rjctdMsgs = manamRow.createCell(4);
					rjctdMsgs.setCellStyle(rejectedCellStyle);
					rjctdMsgs.setCellValue(manamCountEntity.getNrOfRjctMsgs().intValue());
					stRejected = stRejected.add(manamCountEntity.getNrOfRjctMsgs());

					rowCount++;
				}
				//SubTotal the Rows
				Row manamSTRow = inpFromCreditorsSheet.createRow(rowCount);

				Cell manamServ = manamSTRow.createCell(0);
				manamServ.setCellStyle(subTotalCellStyle);

				Cell manamBank = manamSTRow.createCell(1);
				manamBank.setCellStyle(subTotalCellStyle);
				manamBank.setCellValue("Total");

				Cell sttotalNrMsgs = manamSTRow.createCell(2);
				sttotalNrMsgs.setCellStyle(subTotalCellStyle);
				sttotalNrMsgs.setCellValue(stTotal.intValue());

				Cell staccp = manamSTRow.createCell(3);
				staccp.setCellStyle(subTotalCellStyle);
				staccp.setCellValue(stAccptd.intValue());

				Cell stRjct = manamSTRow.createCell(4);
				stRjct.setCellStyle(subTotalCellStyle);
				stRjct.setCellValue(stRejected.intValue());

				//Add to Grand Total
				gtTotal = gtTotal.add(stTotal);
				gtAccptd = gtAccptd.add(stAccptd);
				gtRejected = gtRejected.add(stRejected);

				rowCount++;
			}

			//			Retrieve the manomCount
			List<MonthlyVolumeCountEntityModel> manomCountList = outputToDebtorBanksMap.get("MANOM");
			//			log.info("manomCountList.size() ===>"+manomCountList.size()); 

			if(manomCountList != null && manomCountList.size() > 0)
			{	
				for (MonthlyVolumeCountEntityModel manotCountEntity : manomCountList) 
				{
					Row manomRow = inpFromCreditorsSheet.getRow(debtorCount);
					Cell manomServCell = manomRow.createCell(6);
					if(outfstCount == 0)
					{
						manomServCell.setCellStyle(serviceCellStyle);
						manomServCell.setCellValue("MANOM");
						outfstCount = 1;
					}
					else
					{
						manomServCell.setCellStyle(normalCellStyle);
					}

					Cell debtorBank = manomRow.createCell(7);
					debtorBank.setCellStyle(normalCellStyle);
					debtorBank.setCellValue(manotCountEntity.getInstId());

					Cell totalNrExtrctdMsgs = manomRow.createCell(8);
					totalNrExtrctdMsgs.setCellStyle(normalCellStyle);
					totalNrExtrctdMsgs.setCellValue(manotCountEntity.getNrOfExtMsgs().intValue());
					stExtracted = stExtracted.add(manotCountEntity.getNrOfExtMsgs());
					//Move to next row
					debtorCount++;
				}

				//SubTotal the Rows
				Row extStRow = inpFromCreditorsSheet.getRow(rowCount-1);

				Cell manotServ = extStRow.createCell(6);
				manotServ.setCellStyle(subTotalCellStyle);

				Cell manotBank = extStRow.createCell(7);
				manotBank.setCellStyle(subTotalCellStyle);
				manotBank.setCellValue("Total");

				Cell sttotalNrExtMsgs = extStRow.createCell(8);
				sttotalNrExtMsgs.setCellStyle(subTotalCellStyle);
				sttotalNrExtMsgs.setCellValue(stExtracted.intValue());

				//Add to Grand Total
				gtExtracted = gtExtracted.add(stExtracted);
			}
			
			//POPULATE SUMMARY TOTALS
			MonthlyVolumeCountEntityModel manamTotalEntity = new MonthlyVolumeCountEntityModel();
			manamTotalEntity.setService("MANAM");
			manamTotalEntity.setTotalNrOfMsgs(stTotal);
			manamTotalEntity.setTotalAccpMsgs(stAccptd);
			manamTotalEntity.setTotalRjctdMsgs(stRejected);
			manamTotalEntity.setTotalNrOfExtMsgs(stExtracted);

			summaryTotalsMap.put("MANAM", manamTotalEntity);
		}

		//Empty Rows
		Row emptyRow1 = inpFromCreditorsSheet.createRow(rowCount);
		rowCount++;
		debtorCount = rowCount;
		//		========================MANCN============================//
		//		2018-09-07 SalehaR - Removed as it is replaced by loadCreditorData()
		//		List<MonthlyVolumeCountEntityModel> mancnCountList = (List<MonthlyVolumeCountEntityModel>) beanRemote.retrieveMndtCountByCreditorBanks(strFromDate,strToDate,"MANCN");

		if(inputFromCreditorCountList != null && inputFromCreditorCountList.size() > 0)
		{
			BigDecimal stTotal = BigDecimal.ZERO;
			BigDecimal stAccptd = BigDecimal.ZERO;
			BigDecimal stRejected = BigDecimal.ZERO;
			BigDecimal stExtracted= BigDecimal.ZERO;

			int firstCount = 0;
			int outfstCount = 0;

			List<MonthlyVolumeCountEntityModel> mancnCountList = inputFromCreditorBanksMap.get("MANCN");

			if(mancnCountList != null && mancnCountList.size() > 0)
			{
				for (MonthlyVolumeCountEntityModel mancnCountEntity : mancnCountList) 
				{
					Row mancnRow = inpFromCreditorsSheet.createRow(rowCount);
					Cell mancnServCell = mancnRow.createCell(0);

					if(firstCount == 0)
					{
						mancnServCell.setCellStyle(serviceCellStyle);
						mancnServCell.setCellValue("MANCN");
						firstCount = 1;
					}
					else
					{
						mancnServCell.setCellStyle(normalCellStyle);
					}

					Cell creditorBank = mancnRow.createCell(1);
					creditorBank.setCellStyle(normalCellStyle);
					creditorBank.setCellValue(mancnCountEntity.getInstId());

					Cell totalNrMsgs = mancnRow.createCell(2);
					totalNrMsgs.setCellStyle(normalCellStyle);
					totalNrMsgs.setCellValue(mancnCountEntity.getNrOfMsgs().intValue());
					stTotal = stTotal.add(mancnCountEntity.getNrOfMsgs());

					Cell accptdMsgs = mancnRow.createCell(3);
					accptdMsgs.setCellStyle(acceptedCellStyle);
					accptdMsgs.setCellValue(mancnCountEntity.getNrOfAccpMsgs().intValue());
					stAccptd = stAccptd.add(mancnCountEntity.getNrOfAccpMsgs());

					Cell rjctdMsgs = mancnRow.createCell(4);
					rjctdMsgs.setCellStyle(rejectedCellStyle);
					rjctdMsgs.setCellValue(mancnCountEntity.getNrOfRjctMsgs().intValue());
					stRejected = stRejected.add(mancnCountEntity.getNrOfRjctMsgs());

					rowCount++;
				}

				//SubTotal the Rows
				Row mancnSTRow = inpFromCreditorsSheet.createRow(rowCount);

				Cell mancnServ = mancnSTRow.createCell(0);
				mancnServ.setCellStyle(subTotalCellStyle);

				Cell mancnBank = mancnSTRow.createCell(1);
				mancnBank.setCellStyle(subTotalCellStyle);
				mancnBank.setCellValue("Total");

				Cell sttotalNrMsgs = mancnSTRow.createCell(2);
				sttotalNrMsgs.setCellStyle(subTotalCellStyle);
				sttotalNrMsgs.setCellValue(stTotal.intValue());

				Cell staccp = mancnSTRow.createCell(3);
				staccp.setCellStyle(subTotalCellStyle);
				staccp.setCellValue(stAccptd.intValue());

				Cell stRjct = mancnSTRow.createCell(4);
				stRjct.setCellStyle(subTotalCellStyle);
				stRjct.setCellValue(stRejected.intValue());

				//Add to Grand Total
				gtTotal = gtTotal.add(stTotal);
				gtAccptd = gtAccptd.add(stAccptd);
				gtRejected = gtRejected.add(stRejected);

				rowCount++;
			}
			//			Retrieve the mancoCount
			List<MonthlyVolumeCountEntityModel> mancoCountList = outputToDebtorBanksMap.get("MANCO");
			//			log.info("mancoCountList.size() ===>"+mancoCountList.size()); 

			if(mancoCountList != null && mancoCountList.size() > 0)
			{	
				for (MonthlyVolumeCountEntityModel manotCountEntity : mancoCountList) 
				{
					Row mancoRow = inpFromCreditorsSheet.getRow(debtorCount);
					Cell mancoServCell = mancoRow.createCell(6);
					if(outfstCount == 0)
					{
						mancoServCell.setCellStyle(serviceCellStyle);
						mancoServCell.setCellValue("MANCO");
						outfstCount = 1;
					}
					else
					{
						mancoServCell.setCellStyle(normalCellStyle);
					}

					Cell debtorBank = mancoRow.createCell(7);
					debtorBank.setCellStyle(normalCellStyle);
					debtorBank.setCellValue(manotCountEntity.getInstId());

					Cell totalNrExtrctdMsgs = mancoRow.createCell(8);
					totalNrExtrctdMsgs.setCellStyle(normalCellStyle);
					totalNrExtrctdMsgs.setCellValue(manotCountEntity.getNrOfExtMsgs().intValue());
					stExtracted = stExtracted.add(manotCountEntity.getNrOfExtMsgs());
					//Move to next row
					debtorCount++;
				}

				//SubTotal the Rows
				Row extStRow = inpFromCreditorsSheet.getRow(rowCount-1);

				Cell mancoServ = extStRow.createCell(6);
				mancoServ.setCellStyle(subTotalCellStyle);

				Cell mancoBank = extStRow.createCell(7);
				mancoBank.setCellStyle(subTotalCellStyle);
				mancoBank.setCellValue("Total");

				Cell sttotalNrExtMsgs = extStRow.createCell(8);
				sttotalNrExtMsgs.setCellStyle(subTotalCellStyle);
				sttotalNrExtMsgs.setCellValue(stExtracted.intValue());

				//Add to Grand Total
				gtExtracted = gtExtracted.add(stExtracted);
			}
			
			//POPULATE SUMMARY TOTALS
			MonthlyVolumeCountEntityModel mancnTotalEntity = new MonthlyVolumeCountEntityModel();
			mancnTotalEntity.setService("MANCN");
			mancnTotalEntity.setTotalNrOfMsgs(stTotal);
			mancnTotalEntity.setTotalAccpMsgs(stAccptd);
			mancnTotalEntity.setTotalRjctdMsgs(stRejected);
			mancnTotalEntity.setTotalNrOfExtMsgs(stExtracted);

			summaryTotalsMap.put("MANCN", mancnTotalEntity);
		}

		//Empty Rows
		Row emptyRow2 = inpFromCreditorsSheet.createRow(rowCount);
		rowCount++;
		debtorCount = rowCount;
		//		========================MANRI============================//
		//		2018-09-07 SalehaR - Removed as it is replaced by loadCreditorData()
		//				List<MonthlyVolumeCountEntityModel> manriCountList = (List<MonthlyVolumeCountEntityModel>) beanRemote.retrieveMndtCountByCreditorBanks(strFromDate,strToDate,"MANRI");

		if(inputFromCreditorCountList != null && inputFromCreditorCountList.size() > 0)
		{
			BigDecimal stTotal = BigDecimal.ZERO;
			BigDecimal stAccptd = BigDecimal.ZERO;
			BigDecimal stRejected = BigDecimal.ZERO;
			BigDecimal stExtracted= BigDecimal.ZERO;

			int firstCount = 0;
			int outfstCount = 0;

			//			//Retrieve the maninCount
			List<MonthlyVolumeCountEntityModel> manriCountList = inputFromCreditorBanksMap.get("MANRI");
			if(manriCountList != null && manriCountList.size() > 0)
			{

				for (MonthlyVolumeCountEntityModel manriCountEntity : manriCountList) 
				{
					Row manriRow = inpFromCreditorsSheet.createRow(rowCount);
					Cell manriServCell = manriRow.createCell(0);

					if(firstCount == 0)
					{
						manriServCell.setCellStyle(serviceCellStyle);
						manriServCell.setCellValue("MANRI");
						firstCount = 1;
					}
					else
					{
						manriServCell.setCellStyle(normalCellStyle);
					}

					Cell maninBank = manriRow.createCell(1);
					maninBank.setCellStyle(normalCellStyle);
					maninBank.setCellValue(manriCountEntity.getInstId());

					Cell totalNrMsgs = manriRow.createCell(2);
					totalNrMsgs.setCellStyle(normalCellStyle);
					totalNrMsgs.setCellValue(manriCountEntity.getNrOfMsgs().intValue());
					stTotal = stTotal.add(manriCountEntity.getNrOfMsgs());

					Cell accptdMsgs = manriRow.createCell(3);
					accptdMsgs.setCellStyle(acceptedCellStyle);
					accptdMsgs.setCellValue(manriCountEntity.getNrOfAccpMsgs().intValue());
					stAccptd = stAccptd.add(manriCountEntity.getNrOfAccpMsgs());

					Cell rjctdMsgs = manriRow.createCell(4);
					rjctdMsgs.setCellStyle(rejectedCellStyle);
					rjctdMsgs.setCellValue(manriCountEntity.getNrOfRjctMsgs().intValue());
					stRejected = stRejected.add(manriCountEntity.getNrOfRjctMsgs());

					rowCount++;
				}

				//SubTotal the Rows
				Row manriSTRow = inpFromCreditorsSheet.createRow(rowCount);

				Cell manriServ = manriSTRow.createCell(0);
				manriServ.setCellStyle(subTotalCellStyle);

				Cell manriBank = manriSTRow.createCell(1);
				manriBank.setCellStyle(subTotalCellStyle);
				manriBank.setCellValue("Total");

				Cell sttotalNrMsgs = manriSTRow.createCell(2);
				sttotalNrMsgs.setCellStyle(subTotalCellStyle);
				sttotalNrMsgs.setCellValue(stTotal.intValue());

				Cell staccp = manriSTRow.createCell(3);
				staccp.setCellStyle(subTotalCellStyle);
				staccp.setCellValue(stAccptd.intValue());

				Cell stRjct = manriSTRow.createCell(4);
				stRjct.setCellStyle(subTotalCellStyle);
				stRjct.setCellValue(stRejected.intValue());

				//Add to Grand Total
				gtTotal = gtTotal.add(stTotal);
				gtAccptd = gtAccptd.add(stAccptd);
				gtRejected = gtRejected.add(stRejected);

				rowCount++;
			}

			//			Retrieve the manroCount
			List<MonthlyVolumeCountEntityModel> manroCountList = outputToDebtorBanksMap.get("MANRO");
			//			log.info("manroCountList.size() ===>"+manroCountList.size()); 

			if(manroCountList != null && manroCountList.size() > 0)
			{	
				for (MonthlyVolumeCountEntityModel manotCountEntity : manroCountList) 
				{
					Row manroRow = inpFromCreditorsSheet.getRow(debtorCount);
					Cell manroServCell = manroRow.createCell(6);
					if(outfstCount == 0)
					{
						manroServCell.setCellStyle(serviceCellStyle);
						manroServCell.setCellValue("MANRO");
						outfstCount = 1;
					}
					else
					{
						manroServCell.setCellStyle(normalCellStyle);
					}

					Cell debtorBank = manroRow.createCell(7);
					debtorBank.setCellStyle(normalCellStyle);
					debtorBank.setCellValue(manotCountEntity.getInstId());

					Cell totalNrExtrctdMsgs = manroRow.createCell(8);
					totalNrExtrctdMsgs.setCellStyle(normalCellStyle);
					totalNrExtrctdMsgs.setCellValue(manotCountEntity.getNrOfExtMsgs().intValue());
					stExtracted = stExtracted.add(manotCountEntity.getNrOfExtMsgs());
					//Move to next row
					debtorCount++;
				}

				//SubTotal the Rows
				Row extStRow = inpFromCreditorsSheet.getRow(rowCount-1);

				Cell manroServ = extStRow.createCell(6);
				manroServ.setCellStyle(subTotalCellStyle);

				Cell manroBank = extStRow.createCell(7);
				manroBank.setCellStyle(subTotalCellStyle);
				manroBank.setCellValue("Total");

				Cell sttotalNrExtMsgs = extStRow.createCell(8);
				sttotalNrExtMsgs.setCellStyle(subTotalCellStyle);
				sttotalNrExtMsgs.setCellValue(stExtracted.intValue());

				//Add to Grand Total
				gtExtracted = gtExtracted.add(stExtracted);
			}
			//POPULATE SUMMARY TOTALS
			MonthlyVolumeCountEntityModel manriTotalEntity = new MonthlyVolumeCountEntityModel();
			manriTotalEntity.setService("MANRI");
			manriTotalEntity.setTotalNrOfMsgs(stTotal);
			manriTotalEntity.setTotalAccpMsgs(stAccptd);
			manriTotalEntity.setTotalRjctdMsgs(stRejected);
			manriTotalEntity.setTotalNrOfExtMsgs(stExtracted);

			summaryTotalsMap.put("MANRI", manriTotalEntity);
		}

		//Empty Rows
		Row emptyRow3 = inpFromCreditorsSheet.createRow(rowCount);
		rowCount++;
		debtorCount = rowCount;
		//		========================SRINP============================//
		//		2018-09-07 SalehaR - Removed as it is replaced by loadCreditorData()
		//				List<MonthlyVolumeCountEntityModel> srinpCountList = (List<MonthlyVolumeCountEntityModel>) beanRemote.retrieveMndtCountByCreditorBanks(strFromDate,strToDate,"SRINP");

		if(inputFromCreditorCountList != null && inputFromCreditorCountList.size() > 0)
		{
			BigDecimal stTotal = BigDecimal.ZERO;
			BigDecimal stAccptd = BigDecimal.ZERO;
			BigDecimal stRejected = BigDecimal.ZERO;
			BigDecimal stExtracted= BigDecimal.ZERO;

			int firstCount = 0;
			int outfstCount = 0;

			List<MonthlyVolumeCountEntityModel> srinpCountList = inputFromCreditorBanksMap.get("SRINP");
			if(srinpCountList != null && srinpCountList.size() > 0)
			{

				for (MonthlyVolumeCountEntityModel srinpCountEntity : srinpCountList) 
				{
					Row srinpRow = inpFromCreditorsSheet.createRow(rowCount);
					Cell srinpServCell = srinpRow.createCell(0);

					if(firstCount == 0)
					{
						srinpServCell.setCellStyle(serviceCellStyle);
						srinpServCell.setCellValue("SRINP");
						firstCount = 1;
					}
					else
					{
						srinpServCell.setCellStyle(normalCellStyle);
					}

					Cell srinpBank = srinpRow.createCell(1);
					srinpBank.setCellStyle(normalCellStyle);
					srinpBank.setCellValue(srinpCountEntity.getInstId());

					Cell totalNrMsgs = srinpRow.createCell(2);
					totalNrMsgs.setCellStyle(normalCellStyle);
					totalNrMsgs.setCellValue(srinpCountEntity.getNrOfMsgs().intValue());
					stTotal = stTotal.add(srinpCountEntity.getNrOfMsgs());

					Cell accptdMsgs = srinpRow.createCell(3);
					accptdMsgs.setCellStyle(acceptedCellStyle);
					accptdMsgs.setCellValue(srinpCountEntity.getNrOfAccpMsgs().intValue());
					stAccptd = stAccptd.add(srinpCountEntity.getNrOfAccpMsgs());

					Cell rjctdMsgs = srinpRow.createCell(4);
					rjctdMsgs.setCellStyle(rejectedCellStyle);
					rjctdMsgs.setCellValue(srinpCountEntity.getNrOfRjctMsgs().intValue());
					stRejected = stRejected.add(srinpCountEntity.getNrOfRjctMsgs());

					rowCount++;
				}

				//SubTotal the Rows
				Row srinpSTRow = inpFromCreditorsSheet.createRow(rowCount);

				Cell srinpServ = srinpSTRow.createCell(0);
				srinpServ.setCellStyle(subTotalCellStyle);

				Cell mancnBank = srinpSTRow.createCell(1);
				mancnBank.setCellStyle(subTotalCellStyle);
				mancnBank.setCellValue("Total");

				Cell sttotalNrMsgs = srinpSTRow.createCell(2);
				sttotalNrMsgs.setCellStyle(subTotalCellStyle);
				sttotalNrMsgs.setCellValue(stTotal.intValue());

				Cell staccp = srinpSTRow.createCell(3);
				staccp.setCellStyle(subTotalCellStyle);
				staccp.setCellValue(stAccptd.intValue());

				Cell stRjct = srinpSTRow.createCell(4);
				stRjct.setCellStyle(subTotalCellStyle);
				stRjct.setCellValue(stRejected.intValue());

				//Add to Grand Total
				gtTotal = gtTotal.add(stTotal);
				gtAccptd = gtAccptd.add(stAccptd);
				gtRejected = gtRejected.add(stRejected);

				rowCount++;
			}

			//			Retrieve the sroutCount
			List<MonthlyVolumeCountEntityModel> sroutCountList = outputToDebtorBanksMap.get("SROUT");
			//			log.info("sroutCountList.size() ===>"+sroutCountList.size()); 

			if(sroutCountList != null && sroutCountList.size() > 0)
			{	
				for (MonthlyVolumeCountEntityModel sroutCountEntity : sroutCountList) 
				{
					Row sroutRow = inpFromCreditorsSheet.getRow(debtorCount);
					Cell sroutServCell = sroutRow.createCell(6);
					if(outfstCount == 0)
					{
						sroutServCell.setCellStyle(serviceCellStyle);
						sroutServCell.setCellValue("SROUT");
						outfstCount = 1;
					}
					else
					{
						sroutServCell.setCellStyle(normalCellStyle);
					}

					Cell debtorBank = sroutRow.createCell(7);
					debtorBank.setCellStyle(normalCellStyle);
					debtorBank.setCellValue(sroutCountEntity.getInstId());

					Cell totalNrExtrctdMsgs = sroutRow.createCell(8);
					totalNrExtrctdMsgs.setCellStyle(normalCellStyle);
					totalNrExtrctdMsgs.setCellValue(sroutCountEntity.getNrOfExtMsgs().intValue());
					stExtracted = stExtracted.add(sroutCountEntity.getNrOfExtMsgs());
					//Move to next row
					debtorCount++;
				}

				//SubTotal the Rows
				Row extStRow = inpFromCreditorsSheet.getRow(rowCount-1);

				Cell sroutServ = extStRow.createCell(6);
				sroutServ.setCellStyle(subTotalCellStyle);

				Cell sroutBank = extStRow.createCell(7);
				sroutBank.setCellStyle(subTotalCellStyle);
				sroutBank.setCellValue("Total");

				Cell sttotalNrExtMsgs = extStRow.createCell(8);
				sttotalNrExtMsgs.setCellStyle(subTotalCellStyle);
				sttotalNrExtMsgs.setCellValue(stExtracted.intValue());

				//Add to Grand Total
				gtExtracted = gtExtracted.add(stExtracted);
			}
			//POPULATE SUMMARY TOTALS
			MonthlyVolumeCountEntityModel srinpTotalEntity = new MonthlyVolumeCountEntityModel();
			srinpTotalEntity.setService("SRINP");
			srinpTotalEntity.setTotalNrOfMsgs(stTotal);
			srinpTotalEntity.setTotalAccpMsgs(stAccptd);
			srinpTotalEntity.setTotalRjctdMsgs(stRejected);
			srinpTotalEntity.setTotalNrOfExtMsgs(stExtracted);

			summaryTotalsMap.put("SRINP", srinpTotalEntity);
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

	public void generateDebtorInfoSheet(Sheet inpFromDebtorsSheet, String reportNo,String reportName, String month, String year)
	{
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
//		titleCell3.setCellValue(reportNo+" - BANKSERVAFRICA PRODUCTION - TRANSACTION VOLUME - MANDATES BATCH - "+month.toUpperCase()+" "+year);
		titleCell3.setCellValue(reportNo+" - "+reportName+" - "+month.toUpperCase()+" "+year);

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
		int creditorCount = 6;

		//		========================ST101============================//
		//		2018-09-13 SalehaR - Removed as it is replaced by loadVolumeData()
		//				List<MonthlyVolumeCountEntityModel> st101CountList = (List<MonthlyVolumeCountEntityModel>) beanRemote.retrieveMndtCountByDebtorBanks(strFromDate,strToDate,"ST101");
		if(inputFromDebtorCountList != null && inputFromDebtorCountList.size() > 0)
		{
			BigDecimal stTotal = BigDecimal.ZERO;
			BigDecimal stAccptd = BigDecimal.ZERO;
			BigDecimal stRejected = BigDecimal.ZERO;
			BigDecimal stExtracted= BigDecimal.ZERO;

			int fstCount = 0;
			int outfstCount = 0;

			//			//Retrieve the st101Count
			List<MonthlyVolumeCountEntityModel> st101CountList = inputFromDebtorBankMap.get("ST101");

			if(st101CountList != null && st101CountList.size() > 0)
			{	
				for (MonthlyVolumeCountEntityModel st101CountEntity : st101CountList) 
				{
					Row outServiceRow = inpFromDebtorsSheet.createRow(rowCount_3);
					Cell extServiceCell = outServiceRow.createCell(0);

					if(fstCount == 0)
					{
						extServiceCell.setCellStyle(serviceCellStyle);
						extServiceCell.setCellValue("ST101");
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
			List<MonthlyVolumeCountEntityModel> st103CountList = outputToCreditorBankMap.get("ST103");
			//			log.info("st103CountList.size() ===>"+st103CountList.size()); 
			if(st103CountList != null && st103CountList.size() > 0)
			{	
				for (MonthlyVolumeCountEntityModel st103CountEntity : st103CountList) 
				{
//					if(rowCount == creditorCount)
//					{
//						creditorCount++;
//					}

					Row extRow33 = inpFromDebtorsSheet.getRow(creditorCount);
					Cell extServCell = extRow33.createCell(6);
					if(outfstCount == 0)
					{
						extServCell.setCellStyle(serviceCellStyle);
						extServCell.setCellValue("ST103");
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
					creditorCount++;
				}

				//				log.info("creditorCount before SUBTOTAL ===>"+creditorCount);
				//SubTotal the Rows
				Row extStRow = inpFromDebtorsSheet.getRow(creditorCount);

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
			st101TotalEntity.setService("ST101");
			st101TotalEntity.setTotalNrOfMsgs(stTotal);
			st101TotalEntity.setTotalAccpMsgs(stAccptd);
			st101TotalEntity.setTotalRjctdMsgs(stRejected);
			st101TotalEntity.setTotalNrOfExtMsgs(stExtracted);

			summaryTotalsMap.put("ST101", st101TotalEntity);
		}

		//		log.info("rowCount_3 AFTER ==> "+rowCount_3);
		//		log.info("creditorCount AFTER ==> "+creditorCount);

		//SubTotal the Rows
		creditorCount++;
		Row emptyRow33 = inpFromDebtorsSheet.createRow(creditorCount);
		creditorCount++;
		rowCount_3 = creditorCount;

		//		log.info("rowCount_3 BEFORE MANAC ==> "+rowCount_3);
		//		log.info("creditorCount BEFORE MANAC ==> "+creditorCount);


		//		========================MANAC============================//

		if(inputFromDebtorCountList != null && inputFromDebtorCountList.size() > 0)
		{
			BigDecimal stTotal = BigDecimal.ZERO;
			BigDecimal stAccptd = BigDecimal.ZERO;
			BigDecimal stRejected = BigDecimal.ZERO;
			BigDecimal stExtracted= BigDecimal.ZERO;

			int fstCount = 0;
			int outfstCount = 0;

			//			//Retrieve the manacCount
			List<MonthlyVolumeCountEntityModel> manacCountList = inputFromDebtorBankMap.get("MANAC");

			if(manacCountList != null && manacCountList.size() > 0)
			{	
				for (MonthlyVolumeCountEntityModel manacCountEntity : manacCountList) 
				{
					Row outServiceRow = inpFromDebtorsSheet.createRow(rowCount_3);
					Cell extServiceCell = outServiceRow.createCell(0);

					if(fstCount == 0)
					{
						extServiceCell.setCellStyle(serviceCellStyle);
						extServiceCell.setCellValue("MANAC");
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
			List<MonthlyVolumeCountEntityModel> manocCountList = outputToCreditorBankMap.get("MANOC");
			//			log.info("manocCountList.size() ===>"+manocCountList.size()); 
			if(manocCountList != null && manocCountList.size() > 0)
			{	
				for (MonthlyVolumeCountEntityModel manocCountEntity : manocCountList) 
				{
//					if(rowCount == creditorCount)
//					{
//						creditorCount++;
//					}

					Row extRow33 = inpFromDebtorsSheet.getRow(creditorCount);
					Cell extServCell = extRow33.createCell(6);
					if(outfstCount == 0)
					{
						extServCell.setCellStyle(serviceCellStyle);
						extServCell.setCellValue("MANOC");
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
					creditorCount++;
				}

				//				log.info("creditorCount before SUBTOTAL ===>"+creditorCount);
				//SubTotal the Rows
				Row extStRow = inpFromDebtorsSheet.getRow(creditorCount);

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
			manacTotalEntity.setService("MANAC");
			manacTotalEntity.setTotalNrOfMsgs(stTotal);
			manacTotalEntity.setTotalAccpMsgs(stAccptd);
			manacTotalEntity.setTotalRjctdMsgs(stRejected);
			manacTotalEntity.setTotalNrOfExtMsgs(stExtracted);

			summaryTotalsMap.put("MANAC", manacTotalEntity);
		}

		//		log.info("rowCount_3 AFTER MANOC ==> "+rowCount_3);
		//		log.info("creditorCount AFTER MANOC==> "+creditorCount);

		//SubTotal the Rows
		creditorCount++;
		Row emptyRow34 = inpFromDebtorsSheet.createRow(creditorCount);
		creditorCount++;
		rowCount_3 = creditorCount;

		//		log.info("rowCount_3 BEFORE MANRT ==> "+rowCount_3);
		//		log.info("creditorCount BEFORE MANRT ==> "+creditorCount);


		//		========================MANRT============================//

		if(inputFromDebtorCountList != null && inputFromDebtorCountList.size() > 0)
		{
			BigDecimal stTotal = BigDecimal.ZERO;
			BigDecimal stAccptd = BigDecimal.ZERO;
			BigDecimal stRejected = BigDecimal.ZERO;
			BigDecimal stExtracted= BigDecimal.ZERO;

			int fstCount = 0;
			int outfstCount = 0;

			//			//Retrieve the manacCount
			List<MonthlyVolumeCountEntityModel> manrtCountList = inputFromDebtorBankMap.get("MANRT");

			if(manrtCountList != null && manrtCountList.size() > 0)
			{	
				for (MonthlyVolumeCountEntityModel manrtCountEntity : manrtCountList) 
				{
					Row outServiceRow = inpFromDebtorsSheet.createRow(rowCount_3);
					Cell extServiceCell = outServiceRow.createCell(0);

					if(fstCount == 0)
					{
						extServiceCell.setCellStyle(serviceCellStyle);
						extServiceCell.setCellValue("MANRT");
						fstCount = 1;
					}
					else
					{
						extServiceCell.setCellStyle(normalCellStyle);
					}

					Cell debtorBank = outServiceRow.createCell(1);
					debtorBank.setCellStyle(normalCellStyle);
					debtorBank.setCellValue(manrtCountEntity.getInstId());

					Cell totalNrMsgs = outServiceRow.createCell(2);
					totalNrMsgs.setCellStyle(normalCellStyle);
					totalNrMsgs.setCellValue(manrtCountEntity.getNrOfMsgs().intValue());
					stTotal = stTotal.add(manrtCountEntity.getNrOfMsgs());

					Cell accptdMsgs = outServiceRow.createCell(3);
					accptdMsgs.setCellStyle(acceptedCellStyle);
					accptdMsgs.setCellValue(manrtCountEntity.getNrOfAccpMsgs().intValue());
					stAccptd = stAccptd.add(manrtCountEntity.getNrOfAccpMsgs());

					Cell rjctdMsgs = outServiceRow.createCell(4);
					rjctdMsgs.setCellStyle(rejectedCellStyle);
					rjctdMsgs.setCellValue(manrtCountEntity.getNrOfRjctMsgs().intValue());
					stRejected = stRejected.add(manrtCountEntity.getNrOfRjctMsgs());

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
			List<MonthlyVolumeCountEntityModel> manrfCountList = outputToCreditorBankMap.get("MANRF");
			//			log.info("manrfCountList.size() ===>"+manrfCountList.size()); 
			if(manrfCountList != null && manrfCountList.size() > 0)
			{	
				for (MonthlyVolumeCountEntityModel manrfCountEntity : manrfCountList) 
				{
//					if(rowCount == creditorCount)
//					{
//						creditorCount++;
//					}

					Row extRow33 = inpFromDebtorsSheet.getRow(creditorCount);
					Cell extServCell = extRow33.createCell(6);
					if(outfstCount == 0)
					{
						extServCell.setCellStyle(serviceCellStyle);
						extServCell.setCellValue("MANRF");
						outfstCount = 1;
					}
					else
					{
						extServCell.setCellStyle(normalCellStyle);
					}

					Cell debtorBank = extRow33.createCell(7);
					debtorBank.setCellStyle(normalCellStyle);
					debtorBank.setCellValue(manrfCountEntity.getInstId());

					Cell totalNrExtrctdMsgs = extRow33.createCell(8);
					totalNrExtrctdMsgs.setCellStyle(normalCellStyle);
					totalNrExtrctdMsgs.setCellValue(manrfCountEntity.getNrOfExtMsgs().intValue());
					stExtracted = stExtracted.add(manrfCountEntity.getNrOfExtMsgs());
					//Move to next row
					creditorCount++;
				}

				//				log.info("creditorCount before SUBTOTAL ===>"+creditorCount);
				//SubTotal the Rows
				Row extStRow = inpFromDebtorsSheet.getRow(creditorCount);

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
			MonthlyVolumeCountEntityModel manrtTotalEntity = new MonthlyVolumeCountEntityModel();
			manrtTotalEntity.setService("MANRT");
			manrtTotalEntity.setTotalNrOfMsgs(stTotal);
			manrtTotalEntity.setTotalAccpMsgs(stAccptd);
			manrtTotalEntity.setTotalRjctdMsgs(stRejected);
			manrtTotalEntity.setTotalNrOfExtMsgs(stExtracted);

			summaryTotalsMap.put("MANRT", manrtTotalEntity);
		}

		//		log.info("rowCount_3 AFTER MANRF ==> "+rowCount_3);
		//		log.info("creditorCount AFTER MANRF==> "+creditorCount);

		//SubTotal the Rows
		creditorCount++;
		Row emptyRow35 = inpFromDebtorsSheet.createRow(creditorCount);
		creditorCount++;
		rowCount_3 = creditorCount;

		//		log.info("rowCount_3 BEFORE SPINP ==> "+rowCount_3);
		//		log.info("creditorCount BEFORE SPINP ==> "+creditorCount);

		//		========================SPINP============================//
		if(inputFromDebtorCountList != null && inputFromDebtorCountList.size() > 0)
		{
			BigDecimal stTotal = BigDecimal.ZERO;
			BigDecimal stAccptd = BigDecimal.ZERO;
			BigDecimal stRejected = BigDecimal.ZERO;
			BigDecimal stExtracted= BigDecimal.ZERO;

			int fstCount = 0;
			int outfstCount = 0;

			//			//Retrieve the manacCount
			List<MonthlyVolumeCountEntityModel> spinpCountList = inputFromDebtorBankMap.get("SPINP");

			if(spinpCountList != null && spinpCountList.size() > 0)
			{	
				for (MonthlyVolumeCountEntityModel spinpCountEntity : spinpCountList) 
				{
					Row outServiceRow = inpFromDebtorsSheet.createRow(rowCount_3);
					Cell extServiceCell = outServiceRow.createCell(0);

					if(fstCount == 0)
					{
						extServiceCell.setCellStyle(serviceCellStyle);
						extServiceCell.setCellValue("SPINP");
						fstCount = 1;
					}
					else
					{
						extServiceCell.setCellStyle(normalCellStyle);
					}

					Cell debtorBank = outServiceRow.createCell(1);
					debtorBank.setCellStyle(normalCellStyle);
					debtorBank.setCellValue(spinpCountEntity.getInstId());

					Cell totalNrMsgs = outServiceRow.createCell(2);
					totalNrMsgs.setCellStyle(normalCellStyle);
					totalNrMsgs.setCellValue(spinpCountEntity.getNrOfMsgs().intValue());
					stTotal = stTotal.add(spinpCountEntity.getNrOfMsgs());

					Cell accptdMsgs = outServiceRow.createCell(3);
					accptdMsgs.setCellStyle(acceptedCellStyle);
					accptdMsgs.setCellValue(spinpCountEntity.getNrOfAccpMsgs().intValue());
					stAccptd = stAccptd.add(spinpCountEntity.getNrOfAccpMsgs());

					Cell rjctdMsgs = outServiceRow.createCell(4);
					rjctdMsgs.setCellStyle(rejectedCellStyle);
					rjctdMsgs.setCellValue(spinpCountEntity.getNrOfRjctMsgs().intValue());
					stRejected = stRejected.add(spinpCountEntity.getNrOfRjctMsgs());

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
			List<MonthlyVolumeCountEntityModel> spoutCountList = outputToCreditorBankMap.get("SPOUT");
			//			log.info("spoutCountList.size() ===>"+spoutCountList.size()); 
			if(spoutCountList != null && spoutCountList.size() > 0)
			{	
				for (MonthlyVolumeCountEntityModel spoutCountEntity : spoutCountList) 
				{
//					if(rowCount == creditorCount)
//					{
//						creditorCount++;
//					}

					Row extRow33 = inpFromDebtorsSheet.getRow(creditorCount);
					Cell extServCell = extRow33.createCell(6);
					if(outfstCount == 0)
					{
						extServCell.setCellStyle(serviceCellStyle);
						extServCell.setCellValue("SPOUT");
						outfstCount = 1;
					}
					else
					{
						extServCell.setCellStyle(normalCellStyle);
					}

					Cell debtorBank = extRow33.createCell(7);
					debtorBank.setCellStyle(normalCellStyle);
					debtorBank.setCellValue(spoutCountEntity.getInstId());

					Cell totalNrExtrctdMsgs = extRow33.createCell(8);
					totalNrExtrctdMsgs.setCellStyle(normalCellStyle);
					totalNrExtrctdMsgs.setCellValue(spoutCountEntity.getNrOfExtMsgs().intValue());
					stExtracted = stExtracted.add(spoutCountEntity.getNrOfExtMsgs());
					//Move to next row
					creditorCount++;
				}

				//				log.info("creditorCount before SUBTOTAL ===>"+creditorCount);
				//SubTotal the Rows
				Row extStRow = inpFromDebtorsSheet.getRow(creditorCount);

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
			MonthlyVolumeCountEntityModel spinpTotalEntity = new MonthlyVolumeCountEntityModel();
			spinpTotalEntity.setService("SPINP");
			spinpTotalEntity.setTotalNrOfMsgs(stTotal);
			spinpTotalEntity.setTotalAccpMsgs(stAccptd);
			spinpTotalEntity.setTotalRjctdMsgs(stRejected);
			spinpTotalEntity.setTotalNrOfExtMsgs(stExtracted);

			summaryTotalsMap.put("SPINP", spinpTotalEntity);
		}

		//		log.info("rowCount_3 AFTER SPOUT ==> "+rowCount_3);
		//		log.info("creditorCount AFTER SPOUT==> "+creditorCount);

		//SubTotal the Rows
		creditorCount++;
		Row emptyRow36 = inpFromDebtorsSheet.createRow(creditorCount);
		creditorCount++;
		rowCount_3 = creditorCount;

		//		log.info("rowCount_3 BEFORE GRAND TOTAL ==> "+rowCount_3);
		//		log.info("creditorCount BEFORE GRAND TOTAL ==> "+creditorCount);


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

	public void generateStatusReportInfoSheet(Sheet statusReportsSheet, String reportNo, String reportName, String month, String year)
	{
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
		titleCell14.setCellValue(reportNo+" - "+reportName+" - "+month.toUpperCase()+" "+year);
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

		//Retrieve Status Reports to Creditor Details
		//ST100 Counts
		if(statusReportToCreditorsCountList != null && statusReportToCreditorsCountList.size() > 0)
		{
			BigDecimal stTotal = BigDecimal.ZERO;

			int firstCount = 0;

			List<MonthlyVolumeCountEntityModel> st100CountList = statusReportToCreditorsMap.get("ST100");
			if(st100CountList != null && st100CountList.size() > 0)
			{
				for (MonthlyVolumeCountEntityModel statusReportEntity : st100CountList) 
				{
					Row statusRowCr1 = statusReportsSheet.createRow(rowCount_4);
					Cell stServCell = statusRowCr1.createCell(0);

					if(firstCount == 0)
					{
						stServCell.setCellStyle(serviceCellStyle);
						stServCell.setCellValue("ST100");
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
			st100TotalEntity.setService("ST100");
			st100TotalEntity.setTotalNrOfMsgs(stTotal);

			summaryTotalsMap.put("ST100", st100TotalEntity);
		}

		//ST102 Counts
		if(statusReportToDebtorsCountList != null && statusReportToDebtorsCountList.size() > 0)
		{
			BigDecimal stTotal = BigDecimal.ZERO;

			int firstCount = 0;

			List<MonthlyVolumeCountEntityModel> st102CountList = statusReportToDebtorsMap.get("ST102");
			if(st102CountList != null && st102CountList.size() > 0)
			{

				for (MonthlyVolumeCountEntityModel statusReportEntity : st102CountList) 
				{
					Row statusRowCr1 = statusReportsSheet.getRow(stsCount);
					Cell stServCell = statusRowCr1.createCell(4);

					if(firstCount == 0)
					{
						stServCell.setCellStyle(serviceCellStyle);
						stServCell.setCellValue("ST102");
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
			st102TotalEntity.setService("ST102");
			st102TotalEntity.setTotalNrOfMsgs(stTotal);

			summaryTotalsMap.put("ST102", st102TotalEntity);
		}

		//		log.info("rowCount_4 ==> "+rowCount_4);
		//		log.info("stsCount ==> "+stsCount);

		//SubTotal the Rows
		Row statusNullRow = statusReportsSheet.createRow(rowCount_4);
		rowCount_4++;

		stsCount = rowCount_4;
		//ST105 Counts
		if(statusReportToCreditorsCountList != null && statusReportToCreditorsCountList.size() > 0)
		{
			BigDecimal stTotal = BigDecimal.ZERO;

			int firstCount = 0;

			List<MonthlyVolumeCountEntityModel> st105CountList = statusReportToCreditorsMap.get("ST105");
			if(st105CountList != null && st105CountList.size() > 0)
			{
				for (MonthlyVolumeCountEntityModel statusReportEntity : st105CountList) 
				{
					Row statusRowCr1 = statusReportsSheet.createRow(rowCount_4);
					Cell stServCell = statusRowCr1.createCell(0);

					if(firstCount == 0)
					{
						stServCell.setCellStyle(serviceCellStyle);
						stServCell.setCellValue("ST105");
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
			MonthlyVolumeCountEntityModel st105TotalEntity = new MonthlyVolumeCountEntityModel();
			st105TotalEntity.setService("ST105");
			st105TotalEntity.setTotalNrOfMsgs(stTotal);

			summaryTotalsMap.put("ST105", st105TotalEntity);
		}

		//ST104 Counts
		if(statusReportToDebtorsCountList != null && statusReportToDebtorsCountList.size() > 0)
		{
			BigDecimal stTotal = BigDecimal.ZERO;

			int firstCount = 0;

			List<MonthlyVolumeCountEntityModel> st104CountList = statusReportToDebtorsMap.get("ST104");
			if(st104CountList != null && st104CountList.size() > 0)
			{
				for (MonthlyVolumeCountEntityModel statusReportEntity : st104CountList) 
				{
					Row statusRowCr1 = statusReportsSheet.getRow(stsCount);
					Cell stServCell = statusRowCr1.createCell(4);

					if(firstCount == 0)
					{
						stServCell.setCellStyle(serviceCellStyle);
						stServCell.setCellValue("ST104");
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
			MonthlyVolumeCountEntityModel st104TotalEntity = new MonthlyVolumeCountEntityModel();
			st104TotalEntity.setService("ST104");
			st104TotalEntity.setTotalNrOfMsgs(stTotal);

			summaryTotalsMap.put("ST104", st104TotalEntity);
		}

		//SubTotal the Rows
		Row statusNullRow1 = statusReportsSheet.createRow(rowCount_4);
		rowCount_4++;
		stsCount = rowCount_4;

		//ST008Counts
		if(statusReportToCreditorsCountList != null && statusReportToCreditorsCountList.size() > 0)
		{
			BigDecimal stTotal = BigDecimal.ZERO;

			int firstCount = 0;

			List<MonthlyVolumeCountEntityModel> st008CountList = statusReportToCreditorsMap.get("ST007");
			if(st008CountList != null && st008CountList.size() > 0)
			{

				for (MonthlyVolumeCountEntityModel statusReportEntity : st008CountList) 
				{
					Row statusRowCr1 = statusReportsSheet.createRow(rowCount_4);
					Cell stServCell = statusRowCr1.createCell(0);

					if(firstCount == 0)
					{
						stServCell.setCellStyle(serviceCellStyle);
						stServCell.setCellValue("ST007");
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
			MonthlyVolumeCountEntityModel st007TotalEntity = new MonthlyVolumeCountEntityModel();
			st007TotalEntity.setService("ST007");
			st007TotalEntity.setTotalNrOfMsgs(stTotal);

			summaryTotalsMap.put("ST007", st007TotalEntity);
		}

		//ST106 Counts
		if(statusReportToDebtorsCountList != null && statusReportToDebtorsCountList.size() > 0)
		{
			BigDecimal stTotal = BigDecimal.ZERO;

			int firstCount = 0;

			List<MonthlyVolumeCountEntityModel> st106CountList = statusReportToDebtorsMap.get("ST106");
			if(st106CountList != null && st106CountList.size() > 0)
			{
				for (MonthlyVolumeCountEntityModel statusReportEntity : st106CountList) 
				{
					Row statusRowCr1 = statusReportsSheet.getRow(stsCount);
					Cell stServCell = statusRowCr1.createCell(4);

					if(firstCount == 0)
					{
						stServCell.setCellStyle(serviceCellStyle);
						stServCell.setCellValue("ST106");
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
			MonthlyVolumeCountEntityModel st106TotalEntity = new MonthlyVolumeCountEntityModel();
			st106TotalEntity.setService("ST106");
			st106TotalEntity.setTotalNrOfMsgs(stTotal);

			summaryTotalsMap.put("ST106", st106TotalEntity);

		}

		//SubTotal the Rows
		Row statusNullRow2 = statusReportsSheet.createRow(rowCount_4);
		rowCount_4++;
		stsCount = rowCount_4;

		//ST008 Counts
		if(statusReportToDebtorsCountList != null && statusReportToDebtorsCountList.size() > 0)
		{
			BigDecimal stTotal = BigDecimal.ZERO;

			int firstCount = 0;

			List<MonthlyVolumeCountEntityModel> st008CountList = statusReportToDebtorsMap.get("ST008");
			if(st008CountList != null && st008CountList.size() > 0)
			{
				for (MonthlyVolumeCountEntityModel statusReportEntity : st008CountList) 
				{
					Row statusRowCr1 = statusReportsSheet.createRow(stsCount);
					Cell stServCell = statusRowCr1.createCell(4);

					if(firstCount == 0)
					{
						stServCell.setCellStyle(serviceCellStyle);
						stServCell.setCellValue("ST008");
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
				Row statusTotalRow = statusReportsSheet.createRow(stsCount);

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
			MonthlyVolumeCountEntityModel st008TotalEntity = new MonthlyVolumeCountEntityModel();
			st008TotalEntity.setService("ST008");
			st008TotalEntity.setTotalNrOfMsgs(stTotal);

			summaryTotalsMap.put("ST008", st008TotalEntity);
		}

		//SubTotal the Rows
		Row statusNullRow3 = statusReportsSheet.createRow(stsCount);
		stsCount++;
		rowCount_4 = stsCount;

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

	public void generateSummarySheet(Sheet summaryInfoSheet, String reportNo, String reportName, String month, String year)
	{
		//Sheet 1(Summary Information)	
		summaryInfoSheet.setAutobreaks(true);
		summaryInfoSheet.setFitToPage(true);
		summaryInfoSheet.setPrintRowAndColumnHeadings(true);

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

		//_______________________SHEET 1 - SUMMARY INFORMATION__________________________________//
		//GENERATE TITLE ROW 
		Row titleRow11 = summaryInfoSheet.createRow(0);

		//TITLE ROW
		Cell titleCell1 = titleRow11.createCell(0);
		titleCell1.setCellStyle(titleCellStyle);
		titleCell1.setCellValue(reportNo+" - "+reportName+" -  "+month.toUpperCase()+" "+year);
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

		Row maninRow = summaryInfoSheet.createRow(6);
		//MANIN Data
		Cell maninSummCell = maninRow.createCell(0);
		maninSummCell.setCellStyle(serviceCellStyle);
		maninSummCell.setCellValue("MANIN");

		Cell maninTotalCell = maninRow.createCell(1);
		maninTotalCell.setCellStyle(normalCellStyle);
		maninTotalCell.setCellValue(summaryTotalsMap.get("MANIN").getTotalNrOfMsgs().intValue());

		Cell maninAccpCell = maninRow.createCell(2);
		maninAccpCell.setCellStyle(normalCellStyle);
		maninAccpCell.setCellValue(summaryTotalsMap.get("MANIN").getTotalAccpMsgs().intValue());

		Cell maninRejCell = maninRow.createCell(3);
		maninRejCell.setCellStyle(normalCellStyle);
		maninRejCell.setCellValue(summaryTotalsMap.get("MANIN").getTotalRjctdMsgs().intValue());

		Cell maninExtServCell = maninRow.createCell(4);
		maninExtServCell.setCellStyle(serviceCellStyle);
		maninExtServCell.setCellValue("MANOT");

		Cell manotExtCell = maninRow.createCell(5);
		manotExtCell.setCellStyle(normalCellStyle);
		manotExtCell.setCellValue(summaryTotalsMap.get("MANIN").getTotalNrOfExtMsgs().intValue());

		//MANAM Data
		Row manamRow = summaryInfoSheet.createRow(7);
		Cell manamSummCell = manamRow.createCell(0);
		manamSummCell.setCellStyle(serviceCellStyle);
		manamSummCell.setCellValue("MANAM");

		Cell manamTotalCell = manamRow.createCell(1);
		manamTotalCell.setCellStyle(normalCellStyle);
		manamTotalCell.setCellValue(summaryTotalsMap.get("MANAM").getTotalNrOfMsgs().intValue());

		Cell manamAccpCell = manamRow.createCell(2);
		manamAccpCell.setCellStyle(normalCellStyle);
		manamAccpCell.setCellValue(summaryTotalsMap.get("MANAM").getTotalAccpMsgs().intValue());

		Cell manamRejCell = manamRow.createCell(3);
		manamRejCell.setCellStyle(normalCellStyle);
		manamRejCell.setCellValue(summaryTotalsMap.get("MANAM").getTotalRjctdMsgs().intValue());

		Cell manamExtServCell = manamRow.createCell(4);
		manamExtServCell.setCellStyle(serviceCellStyle);
		manamExtServCell.setCellValue("MANOM");

		Cell manomExtCell = manamRow.createCell(5);
		manomExtCell.setCellStyle(normalCellStyle);
		manomExtCell.setCellValue(summaryTotalsMap.get("MANAM").getTotalNrOfExtMsgs().intValue());

		//MANCN Data
		Row mancnRow = summaryInfoSheet.createRow(8);
		Cell mancnSummCell = mancnRow.createCell(0);
		mancnSummCell.setCellStyle(serviceCellStyle);
		mancnSummCell.setCellValue("MANCN");

		Cell mancnTotalCell = mancnRow.createCell(1);
		mancnTotalCell.setCellStyle(normalCellStyle);
		mancnTotalCell.setCellValue(summaryTotalsMap.get("MANCN").getTotalNrOfMsgs().intValue());

		Cell mancnAccpCell = mancnRow.createCell(2);
		mancnAccpCell.setCellStyle(normalCellStyle);
		mancnAccpCell.setCellValue(summaryTotalsMap.get("MANCN").getTotalAccpMsgs().intValue());

		Cell mancnRejCell = mancnRow.createCell(3);
		mancnRejCell.setCellStyle(normalCellStyle);
		mancnRejCell.setCellValue(summaryTotalsMap.get("MANCN").getTotalRjctdMsgs().intValue());

		Cell mancnExtServCell = mancnRow.createCell(4);
		mancnExtServCell.setCellStyle(serviceCellStyle);
		mancnExtServCell.setCellValue("MANCO");

		Cell mancoExtCell = mancnRow.createCell(5);
		mancoExtCell.setCellStyle(normalCellStyle);
		mancoExtCell.setCellValue(summaryTotalsMap.get("MANCN").getTotalNrOfExtMsgs().intValue());

		//MANRI Data
		Row manriRow = summaryInfoSheet.createRow(9);
		Cell manriSummCell = manriRow.createCell(0);
		manriSummCell.setCellStyle(serviceCellStyle);
		manriSummCell.setCellValue("MANRI");

		Cell manriTotalCell = manriRow.createCell(1);
		manriTotalCell.setCellStyle(normalCellStyle);
		manriTotalCell.setCellValue(summaryTotalsMap.get("MANRI").getTotalNrOfMsgs().intValue());

		Cell manriAccpCell = manriRow.createCell(2);
		manriAccpCell.setCellStyle(normalCellStyle);
		manriAccpCell.setCellValue(summaryTotalsMap.get("MANRI").getTotalAccpMsgs().intValue());

		Cell manriRejCell = manriRow.createCell(3);
		manriRejCell.setCellStyle(normalCellStyle);
		manriRejCell.setCellValue(summaryTotalsMap.get("MANRI").getTotalRjctdMsgs().intValue());

		Cell manriExtServCell = manriRow.createCell(4);
		manriExtServCell.setCellStyle(serviceCellStyle);
		manriExtServCell.setCellValue("MANRO");

		Cell manriExtCell = manriRow.createCell(5);
		manriExtCell.setCellStyle(normalCellStyle);
		manriExtCell.setCellValue(summaryTotalsMap.get("MANRI").getTotalNrOfExtMsgs().intValue());

		//SRINP Data
		Row srinpRow = summaryInfoSheet.createRow(10);
		Cell srinpSummCell = srinpRow.createCell(0);
		srinpSummCell.setCellStyle(serviceCellStyle);
		srinpSummCell.setCellValue("SRINP");

		Cell srinpTotalCell = srinpRow.createCell(1);
		srinpTotalCell.setCellStyle(normalCellStyle);
		srinpTotalCell.setCellValue(summaryTotalsMap.get("SRINP").getTotalNrOfMsgs().intValue());

		Cell srinpAccpCell = srinpRow.createCell(2);
		srinpAccpCell.setCellStyle(normalCellStyle);
		srinpAccpCell.setCellValue(summaryTotalsMap.get("SRINP").getTotalAccpMsgs().intValue());

		Cell srinpRejCell = srinpRow.createCell(3);
		srinpRejCell.setCellStyle(normalCellStyle);
		srinpRejCell.setCellValue(summaryTotalsMap.get("SRINP").getTotalRjctdMsgs().intValue());

		Cell srinpExtServCell = srinpRow.createCell(4);
		srinpExtServCell.setCellStyle(serviceCellStyle);
		srinpExtServCell.setCellValue("SROUT");

		Cell srinpExtCell = srinpRow.createCell(5);
		srinpExtCell.setCellStyle(normalCellStyle);
		srinpExtCell.setCellValue(summaryTotalsMap.get("SRINP").getTotalNrOfExtMsgs().intValue());

		//Subtotal Creditor Banks
		//SubTotal Data
		Row crSubTotalRow = summaryInfoSheet.createRow(11);

		Cell crStSummCell = crSubTotalRow.createCell(0);
		crStSummCell.setCellStyle(summSubtotalCellStyle);
		crStSummCell.setCellValue("Sub Total");

		Cell subTotalNrOfMsgsCell = crSubTotalRow.createCell(1);
		subTotalNrOfMsgsCell.setCellStyle(summSubtotalCellStyle);
		subTotalNrOfMsgsCell.setCellType(CellType.FORMULA);
		subTotalNrOfMsgsCell.setCellFormula("SUM(B7:B11)");

		Cell subTotAccpCell = crSubTotalRow.createCell(2);
		subTotAccpCell.setCellStyle(summSubtotalCellStyle);
		subTotAccpCell.setCellType(CellType.FORMULA);
		subTotAccpCell.setCellFormula("SUM(C7:C11)");

		Cell subTotalRejCell = crSubTotalRow.createCell(3);
		subTotalRejCell.setCellStyle(summSubtotalCellStyle);
		subTotalRejCell.setCellType(CellType.FORMULA);
		subTotalRejCell.setCellFormula("SUM(D7:D11)");

		Cell nullCell4 = crSubTotalRow.createCell(4);
		nullCell4.setCellStyle(summSubtotalCellStyle);
		nullCell4.setCellValue("Sub Total");

		Cell subTotalExtMsgsCell = crSubTotalRow.createCell(5);
		subTotalExtMsgsCell.setCellStyle(summSubtotalCellStyle);
		subTotalExtMsgsCell.setCellType(CellType.FORMULA);
		subTotalExtMsgsCell.setCellFormula("SUM(F7:F11)");

		Row drBanksRow = summaryInfoSheet.createRow(12);
		Cell drBanksCell = drBanksRow.createCell(0);
		drBanksCell.setCellStyle(subTitleCellStyle);
		drBanksCell.setCellValue("Debtor Banks");

		summaryInfoSheet.addMergedRegion(new CellRangeAddress(12,12, 0, 1));

		//ST101 Data
		Row st101Row = summaryInfoSheet.createRow(13);
		Cell st101SummCell = st101Row.createCell(0);
		st101SummCell.setCellStyle(serviceCellStyle);
		st101SummCell.setCellValue("ST101");

		Cell st101TotalCell = st101Row.createCell(1);
		st101TotalCell.setCellStyle(normalCellStyle);
		st101TotalCell.setCellValue(summaryTotalsMap.get("ST101").getTotalNrOfMsgs().intValue());

		Cell st101AccpCell = st101Row.createCell(2);
		st101AccpCell.setCellStyle(normalCellStyle);
		st101AccpCell.setCellValue(summaryTotalsMap.get("ST101").getTotalAccpMsgs().intValue());

		Cell st101RejCell = st101Row.createCell(3);
		st101RejCell.setCellStyle(normalCellStyle);
		st101RejCell.setCellValue(summaryTotalsMap.get("ST101").getTotalRjctdMsgs().intValue());

		Cell st101ExtServCell = st101Row.createCell(4);
		st101ExtServCell.setCellStyle(serviceCellStyle);
		st101ExtServCell.setCellValue("ST103");

		Cell st103ExtCell = st101Row.createCell(5);
		st103ExtCell.setCellStyle(normalCellStyle);
		st103ExtCell.setCellValue(summaryTotalsMap.get("ST101").getTotalNrOfExtMsgs().intValue());

		//MANAC Data
		Row manacRow = summaryInfoSheet.createRow(14);
		Cell manacSummCell = manacRow.createCell(0);
		manacSummCell.setCellStyle(serviceCellStyle);
		manacSummCell.setCellValue("MANAC");

		Cell manacTotalCell = manacRow.createCell(1);
		manacTotalCell.setCellStyle(normalCellStyle);
		manacTotalCell.setCellValue(summaryTotalsMap.get("MANAC").getTotalNrOfMsgs().intValue());

		Cell manacAccpCell = manacRow.createCell(2);
		manacAccpCell.setCellStyle(normalCellStyle);
		manacAccpCell.setCellValue(summaryTotalsMap.get("MANAC").getTotalAccpMsgs().intValue());

		Cell manacRejCell = manacRow.createCell(3);
		manacRejCell.setCellStyle(normalCellStyle);
		manacRejCell.setCellValue(summaryTotalsMap.get("MANAC").getTotalRjctdMsgs().intValue());

		Cell manacExtServCell = manacRow.createCell(4);
		manacExtServCell.setCellStyle(serviceCellStyle);
		manacExtServCell.setCellValue("MANOC");

		Cell manacExtCell = manacRow.createCell(5);
		manacExtCell.setCellStyle(normalCellStyle);
		manacExtCell.setCellValue(summaryTotalsMap.get("MANAC").getTotalNrOfExtMsgs().intValue());

		//MANRT Data
		Row manrtRow = summaryInfoSheet.createRow(15);
		Cell manrtSummCell = manrtRow.createCell(0);
		manrtSummCell.setCellStyle(serviceCellStyle);
		manrtSummCell.setCellValue("MANRT");

		Cell manrtTotalCell = manrtRow.createCell(1);
		manrtTotalCell.setCellStyle(normalCellStyle);
		manrtTotalCell.setCellValue(summaryTotalsMap.get("MANRT").getTotalNrOfMsgs().intValue());

		Cell manrtAccpCell = manrtRow.createCell(2);
		manrtAccpCell.setCellStyle(normalCellStyle);
		manrtAccpCell.setCellValue(summaryTotalsMap.get("MANRT").getTotalAccpMsgs().intValue());

		Cell manrtRejCell = manrtRow.createCell(3);
		manrtRejCell.setCellStyle(normalCellStyle);
		manrtRejCell.setCellValue(summaryTotalsMap.get("MANRT").getTotalRjctdMsgs().intValue());

		Cell manrtExtServCell = manrtRow.createCell(4);
		manrtExtServCell.setCellStyle(serviceCellStyle);
		manrtExtServCell.setCellValue("MANRF");

		Cell manrtExtCell = manrtRow.createCell(5);
		manrtExtCell.setCellStyle(normalCellStyle);
		manrtExtCell.setCellValue(summaryTotalsMap.get("MANRT").getTotalNrOfExtMsgs().intValue());

		//SPINP Data
		Row spinpRow = summaryInfoSheet.createRow(16);
		Cell spinpSummCell = spinpRow.createCell(0);
		spinpSummCell.setCellStyle(serviceCellStyle);
		spinpSummCell.setCellValue("SPINP");

		Cell spinpTotalCell = spinpRow.createCell(1);
		spinpTotalCell.setCellStyle(normalCellStyle);
		spinpTotalCell.setCellValue(summaryTotalsMap.get("SPINP").getTotalNrOfMsgs().intValue());

		Cell spinpAccpCell = spinpRow.createCell(2);
		spinpAccpCell.setCellStyle(normalCellStyle);
		spinpAccpCell.setCellValue(summaryTotalsMap.get("SPINP").getTotalAccpMsgs().intValue());

		Cell spinpRejCell = spinpRow.createCell(3);
		spinpRejCell.setCellStyle(normalCellStyle);
		spinpRejCell.setCellValue(summaryTotalsMap.get("SPINP").getTotalRjctdMsgs().intValue());

		Cell spinpExtServCell = spinpRow.createCell(4);
		spinpExtServCell.setCellStyle(serviceCellStyle);
		spinpExtServCell.setCellValue("SPOUT");

		Cell spinpExtCell = spinpRow.createCell(5);
		spinpExtCell.setCellStyle(normalCellStyle);
		spinpExtCell.setCellValue(summaryTotalsMap.get("SPINP").getTotalNrOfExtMsgs().intValue());

		//Subtotal Debtor Banks
		//SubTotal Data
		Row drSubTotalRow = summaryInfoSheet.createRow(17);

		Cell drStSummCell = drSubTotalRow.createCell(0);
		drStSummCell.setCellStyle(summSubtotalCellStyle);
		drStSummCell.setCellValue("Sub Total");

		Cell drsubTotalNrOfMsgsCell = drSubTotalRow.createCell(1);
		drsubTotalNrOfMsgsCell.setCellStyle(summSubtotalCellStyle);
		drsubTotalNrOfMsgsCell.setCellType(CellType.FORMULA);
		drsubTotalNrOfMsgsCell.setCellFormula("SUM(B14:B17)");

		Cell drsubTotAccpCell = drSubTotalRow.createCell(2);
		drsubTotAccpCell.setCellStyle(summSubtotalCellStyle);
		drsubTotAccpCell.setCellType(CellType.FORMULA);
		drsubTotAccpCell.setCellFormula("SUM(C14:C17)");

		Cell drsubTotalRejCell = drSubTotalRow.createCell(3);
		drsubTotalRejCell.setCellStyle(summSubtotalCellStyle);
		drsubTotalRejCell.setCellType(CellType.FORMULA);
		drsubTotalRejCell.setCellFormula("SUM(D14:D17)");

		Cell nullCell1 = drSubTotalRow.createCell(4);
		nullCell1.setCellStyle(summSubtotalCellStyle);
		nullCell1.setCellValue("Sub Total");

		Cell drsubTotalExtMsgsCell = drSubTotalRow.createCell(5);
		drsubTotalExtMsgsCell.setCellStyle(summSubtotalCellStyle);
		drsubTotalExtMsgsCell.setCellType(CellType.FORMULA);
		drsubTotalExtMsgsCell.setCellFormula("SUM(F14:F17)");

		//TOTAL Data
		Row totalRow = summaryInfoSheet.createRow(18);
		Cell totalSummCell = totalRow.createCell(0);
		totalSummCell.setCellStyle(serviceCellStyle);
		totalSummCell.setCellValue("Total Processed");

		Cell nrOfMsgsTotalCell = totalRow.createCell(1);
		nrOfMsgsTotalCell.setCellStyle(serviceCellStyle);
		nrOfMsgsTotalCell.setCellType(CellType.FORMULA);
		nrOfMsgsTotalCell.setCellFormula("SUM(B7:B11,B13:B17)");

		Cell accpMsgsCell = totalRow.createCell(2);
		accpMsgsCell.setCellStyle(serviceCellStyle);
		accpMsgsCell.setCellType(CellType.FORMULA);
		accpMsgsCell.setCellFormula("SUM(C7:C11,C13:C17)");

		Cell rejMsgsCell = totalRow.createCell(3);
		rejMsgsCell.setCellStyle(serviceCellStyle);
		rejMsgsCell.setCellType(CellType.FORMULA);
		rejMsgsCell.setCellFormula("SUM(D7:D11,D13:D17)");

		Cell nullCell_2 = totalRow.createCell(4);
		nullCell_2.setCellStyle(serviceCellStyle);
		nullCell_2.setCellValue("Total Extracted");

		Cell extMsgsCell = totalRow.createCell(5);
		extMsgsCell.setCellStyle(serviceCellStyle);
		extMsgsCell.setCellType(CellType.FORMULA);
		extMsgsCell.setCellFormula("SUM(F7:F11,F13:F17)");

		//Percentage Data
		Row totalPrecRow = summaryInfoSheet.createRow(20);
		Cell totalPercCell = totalPrecRow.createCell(0);
		totalPercCell.setCellStyle(serviceCellStyle);
		totalPercCell.setCellValue("Acceptance %");

		Cell nullPercCell = totalPrecRow.createCell(1);

		Cell accpPercCell = totalPrecRow.createCell(2);
		accpPercCell.setCellStyle(normalPercCellStyle);
		accpPercCell.setCellType(CellType.FORMULA);
		accpPercCell.setCellFormula("IF(B19>0,C19/B19,0)");

		Cell rejPercCell = totalPrecRow.createCell(3);
		rejPercCell.setCellStyle(normalPercCellStyle);
		rejPercCell.setCellType(CellType.FORMULA);
		rejPercCell.setCellFormula("IF(B19>0,D19/B19,0)");

		//Status Report Summary
		//GENERATE SUB TITLE ROW
		Row subHdrRow12 = summaryInfoSheet.createRow(21);

		Cell subHdrCrdCell12 = subHdrRow12.createCell(0);
		subHdrCrdCell12.setCellStyle(subHdrCellStyle);
		subHdrCrdCell12.setCellValue("STATUS REPORTS SUMMARY INFORMATION");

		//Merge Cell 0 to 4
		summaryInfoSheet.addMergedRegion(new CellRangeAddress(21,21, 0, 5));

		//GENERATE COLUMN HEADERS
		Row stsSummHdrRow = summaryInfoSheet.createRow(22);

		Cell stsRepCell = stsSummHdrRow.createCell(0);
		stsRepCell.setCellStyle(headerCellStyle);
		stsRepCell.setCellValue("INPUT SERVICE");;

		Cell stsNrOfTxnsCell = stsSummHdrRow.createCell(1);
		stsNrOfTxnsCell.setCellStyle(headerCellStyle);
		stsNrOfTxnsCell.setCellValue("TOT NR OF TXNS");

		//Populate Summary Information
		Row stsCrBanksRow = summaryInfoSheet.createRow(23);
		Cell stsCrBanksCell = stsCrBanksRow.createCell(0);
		stsCrBanksCell.setCellStyle(subTitleCellStyle);
		stsCrBanksCell.setCellValue("Creditor Banks");

		summaryInfoSheet.addMergedRegion(new CellRangeAddress(23,23, 0, 1));

		Row st100Row = summaryInfoSheet.createRow(24);
		Cell st100SummCell = st100Row.createCell(0);
		st100SummCell.setCellStyle(serviceCellStyle);
		st100SummCell.setCellValue("ST100");

		Cell st100TotalCell = st100Row.createCell(1);
		st100TotalCell.setCellStyle(normalCellStyle);
		st100TotalCell.setCellValue(summaryTotalsMap.get("ST100").getTotalNrOfMsgs().intValue());

		Row st105Row = summaryInfoSheet.createRow(25);
		Cell st105SummCell = st105Row.createCell(0);
		st105SummCell.setCellStyle(serviceCellStyle);
		st105SummCell.setCellValue("ST105");

		Cell st105TotalCell = st105Row.createCell(1);
		st105TotalCell.setCellStyle(normalCellStyle);
		st105TotalCell.setCellValue(summaryTotalsMap.get("ST105").getTotalNrOfMsgs().intValue());

		Row st007Row = summaryInfoSheet.createRow(26);
		Cell st007SummCell = st007Row.createCell(0);
		st007SummCell.setCellStyle(serviceCellStyle);
		st007SummCell.setCellValue("ST007");

		Cell st007TotalCell = st007Row.createCell(1);
		st007TotalCell.setCellStyle(normalCellStyle);
		st007TotalCell.setCellValue(summaryTotalsMap.get("ST007").getTotalNrOfMsgs().intValue());

		//Subtotal Creditor Banks Status Reports
		Row crStsSubTotRow = summaryInfoSheet.createRow(27);

		Cell crStsSubTotalCell = crStsSubTotRow.createCell(0);
		crStsSubTotalCell.setCellStyle(summSubtotalCellStyle);
		crStsSubTotalCell.setCellValue("Sub Total");

		Cell crStsSubTotNrOfMsgsCell = crStsSubTotRow.createCell(1);
		crStsSubTotNrOfMsgsCell.setCellStyle(summSubtotalCellStyle);
		crStsSubTotNrOfMsgsCell.setCellType(CellType.FORMULA);
		crStsSubTotNrOfMsgsCell.setCellFormula("SUM(B25:B27)");

		//Populate Summary Information
		Row stsDrBanksRow = summaryInfoSheet.createRow(28);
		Cell stsDrBanksCell = stsDrBanksRow.createCell(0);
		stsDrBanksCell.setCellStyle(subTitleCellStyle);
		stsDrBanksCell.setCellValue("Debtor Banks");

		summaryInfoSheet.addMergedRegion(new CellRangeAddress(28,28, 0, 1));

		Row st102Row = summaryInfoSheet.createRow(29);
		Cell st102SummCell = st102Row.createCell(0);
		st102SummCell.setCellStyle(serviceCellStyle);
		st102SummCell.setCellValue("ST102");

		Cell st102TotalCell = st102Row.createCell(1);
		st102TotalCell.setCellStyle(normalCellStyle);
		st102TotalCell.setCellValue(summaryTotalsMap.get("ST102").getTotalNrOfMsgs().intValue());

		Row st104Row = summaryInfoSheet.createRow(30);
		Cell st104SummCell = st104Row.createCell(0);
		st104SummCell.setCellStyle(serviceCellStyle);
		st104SummCell.setCellValue("ST104");

		Cell st104TotalCell = st104Row.createCell(1);
		st104TotalCell.setCellStyle(normalCellStyle);
		st104TotalCell.setCellValue(summaryTotalsMap.get("ST104").getTotalNrOfMsgs().intValue());

		Row st106Row = summaryInfoSheet.createRow(31);
		Cell st106SummCell = st106Row.createCell(0);
		st106SummCell.setCellStyle(serviceCellStyle);
		st106SummCell.setCellValue("ST106");

		Cell st106TotalCell = st106Row.createCell(1);
		st106TotalCell.setCellStyle(normalCellStyle);
		st106TotalCell.setCellValue(summaryTotalsMap.get("ST106").getTotalNrOfMsgs().intValue());

		Row st008Row = summaryInfoSheet.createRow(32);
		Cell st008SummCell = st008Row.createCell(0);
		st008SummCell.setCellStyle(serviceCellStyle);
		st008SummCell.setCellValue("ST008");

		Cell st008TotalCell = st008Row.createCell(1);
		st008TotalCell.setCellStyle(normalCellStyle);
		st008TotalCell.setCellValue(summaryTotalsMap.get("ST008").getTotalNrOfMsgs().intValue());

		//Subtotal Debtor Banks Status Reports
		Row drStsSubTotRow = summaryInfoSheet.createRow(33);

		Cell drStsSubTotalCell = drStsSubTotRow.createCell(0);
		drStsSubTotalCell.setCellStyle(summSubtotalCellStyle);
		drStsSubTotalCell.setCellValue("Sub Total");

		Cell drStsSubTotNrOfMsgsCell = drStsSubTotRow.createCell(1);
		drStsSubTotNrOfMsgsCell.setCellStyle(summSubtotalCellStyle);
		drStsSubTotNrOfMsgsCell.setCellType(CellType.FORMULA);
		drStsSubTotNrOfMsgsCell.setCellFormula("SUM(B30:B33)");

		//STATUS REPORT TOTAL Data
		Row stsTotalRow = summaryInfoSheet.createRow(34);
		Cell totalProcessCell = stsTotalRow.createCell(0);
		totalProcessCell.setCellStyle(serviceCellStyle);
		totalProcessCell.setCellValue("Total Processed");

		Cell totTxnsCell = stsTotalRow.createCell(1);
		totTxnsCell.setCellStyle(serviceCellStyle);
		totTxnsCell.setCellType(CellType.FORMULA);
		totTxnsCell.setCellFormula("SUM(B25:B27,B30:B33)");
	}

	public void loadVolumeData(String strFromDate, String strToDate)
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
		
		if(creditorBankModelList != null && creditorBankModelList.size() > 0) {
			nrOfBanks = creditorBankModelList.size();
		}else {
			if(debtorBankModelList != null && debtorBankModelList.size() > 0) {
				nrOfBanks = debtorBankModelList.size();
			}
		}
		
		log.info("======== NR OF BANKS : "+nrOfBanks+" ========");

		servicesList = new ArrayList<ServicesModel>();
		servicesList = adminBeanRemote.viewAllServices();	


		//INPUT FROM CREDITOR BANKS
		inputFromCreditorCountList = new ArrayList<MonthlyVolumeCountEntityModel>();
		inputFromCreditorCountList = (List<MonthlyVolumeCountEntityModel>) reportBeanRemote.retrieveBatchMonthlyTransFromCreditor(strFromDate, strToDate);
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
		outputToDebtorCountList = (List<MonthlyVolumeCountEntityModel>) reportBeanRemote.batchMonthlyTransExtractedToDebtorBank(strFromDate, strToDate);
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
		inputFromDebtorCountList = (List<MonthlyVolumeCountEntityModel>) reportBeanRemote.retrieveBatchMonthlyTransFromDebtor(strFromDate, strToDate);
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
		outputToCreditorCountList = (List<MonthlyVolumeCountEntityModel>) reportBeanRemote.batchMonthlyTransExtractedToCreditorBank(strFromDate, strToDate);
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
		statusReportToCreditorsCountList = (List<MonthlyVolumeCountEntityModel>) reportBeanRemote.retrieveBatchMonthlyStReportCreditor(strFromDate, strToDate);
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
		statusReportToDebtorsCountList = (List<MonthlyVolumeCountEntityModel>) reportBeanRemote.retrieveBatchMonthlyStReportDebtor(strFromDate, strToDate);
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

		log.info("tmpFile ==> "+ tmpFile);
		log.info("destFile===> "+ destFile);

		Files.copy(tmpFile, destFile);
		log.info("Copying "+fileName+" from temp to output directory...");
//		2020/03/20-CHG-191677 - remove pdf versions
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
//		MR020_BSAMonthlyBatchVolumesReportPDF mr020PdfReport = new MR020_BSAMonthlyBatchVolumesReportPDF();
//		try {
//			mr020PdfReport.generateReport(my_xls_workbook);
//		} catch (DocumentException e) {
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
