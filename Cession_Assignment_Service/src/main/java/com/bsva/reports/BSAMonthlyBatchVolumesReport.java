package com.bsva.reports;

import java.io.File;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.bsva.commons.model.SysctrlCompParamModel;
import com.bsva.commons.model.SystemParameterModel;
import com.bsva.entities.CasCnfgReportNamesEntity;
import com.bsva.entities.MonthlyVolumeCountEntityModel;
import com.bsva.interfaces.AdminBeanRemote;
import com.bsva.interfaces.PropertyUtilRemote;
import com.bsva.interfaces.ServiceBeanRemote;
import com.bsva.interfaces.ValidationBeanRemote;
import com.bsva.utils.DateUtil;
import com.bsva.utils.Util;
import com.google.common.io.Files;
import com.itextpdf.text.DocumentException;

/**
 * @author SalehaR
 * @author SalehaR-2018-09-06: CHG-143989 Add Extract & Summary Data to Report
 *
 */
public class BSAMonthlyBatchVolumesReport 
{
	public static Logger log=Logger.getLogger(BSAMonthlyBatchVolumesReport.class);
	private String fileName;

	public static ServiceBeanRemote beanRemote;
	public static ValidationBeanRemote valBeanRemote;
	private static AdminBeanRemote adminBeanRemote;
	private static PropertyUtilRemote propertyUtilRemote;

	SystemParameterModel systemParameterModel;
	SysctrlCompParamModel companyParamModel;

	SimpleDateFormat monthFormat = new SimpleDateFormat("MMM-yyyy");
	String reportName, reportNr, reportDir = null, tempDir = null;
	String xlsFileName = null, mr020 = null;
	String invBank = null;
	int fileSeqNo =000;
	int rowCount = 0;

	BigDecimal gtTotal = BigDecimal.ZERO;
	BigDecimal gtAccptd = BigDecimal.ZERO;
	BigDecimal gtRejected = BigDecimal.ZERO;

	public void generateReport() throws DocumentException, IOException
	{
		contextAdminBeanCheck();
		contextCheck();
		contextValidationBeanCheck();
		contextPropertyFileCheck();

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
		}
		catch(Exception ex)
		{
			log.error("MR020- Could not find CessionAssignment.properties in classpath");
			reportDir = "/home/opsjava/Delivery/Cession_Assign/Output/Reports/";
			tempDir="/home/opsjava/Delivery/Cession_Assign/Output/temp/";
			invBank = "INVBNK";
		}

		//Retrieve Report Name
		CasCnfgReportNamesEntity reportNameEntity = new CasCnfgReportNamesEntity();
		reportNameEntity = (CasCnfgReportNamesEntity) adminBeanRemote.retrieveReportName(mr020);

		if(reportNameEntity != null)
		{
			reportNr = reportNameEntity.getReportNr();
			reportName = reportNameEntity.getReportName();
		}

		log.info("*****REPORT "+reportNr+" GENERATING*****");
		generateReportDetail(reportNr, reportName);

	}

	public void generateReportDetail(String reportNo,String reportname) throws DocumentException, IOException
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

		fileName = tempDir+reportNo+"AC"+reportDate.format(currentDate).toString()+".xlsx";
		xlsFileName  = reportNo+"AC"+reportDate.format(currentDate).toString()+".xlsx";

		String sheetName = "BSA BATCH PROD VOLUMES";//name of sheet

		strMonth = fileTimeFormat.format(systemParameterModel.getProcessDate()).substring(0,2);
		month= DateUtil.getMonthFullname(Integer.valueOf(strMonth), true);
		log.info("Process Month----->"+month);
		year = String.valueOf(DateUtil.getYear(systemParameterModel.getProcessDate()));
		log.info("year----->"+year);
		
		
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

			//				strFirstDate = "01082017";
			//				strLastDate = "31082017";
			log.info("strFromDate ==>" + strFromDate);
			log.info("strToDate ==> "+ strToDate);
		}

		Workbook wb = new XSSFWorkbook(); //or new HSSFWorkbook();
		Sheet sheet = wb.createSheet(sheetName);

		sheet.setColumnWidth(0, 5000);
		sheet.setColumnWidth(1, 5000);
		sheet.setColumnWidth(2, 5000);
		sheet.setColumnWidth(3, 5000);
		sheet.setColumnWidth(4, 5000);
		//		sheet.setColumnWidth(5, 2000);
		//		sheet.setColumnWidth(6, 5000);
		//		sheet.setColumnWidth(7, 5000);
		//		sheet.setColumnWidth(8, 5000);
		//		sheet.setColumnWidth(9, 5000);
		//		sheet.setColumnWidth(10, 5000);

		//=============================CELL STYLES & FONT STYLES==============================//
		//Title Style
		CellStyle titleCellStyle = wb.createCellStyle();
		titleCellStyle = wb.createCellStyle();
		titleCellStyle.setWrapText(true);
		titleCellStyle.setAlignment(HorizontalAlignment.CENTER_SELECTION);
		titleCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		titleCellStyle.setFillForegroundColor(IndexedColors.SEA_GREEN.getIndex());
		titleCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);

		Font titleFont = wb.createFont();
		titleFont.setFontName("Arial");
		titleFont.setBold(true);
		titleFont.setFontHeightInPoints((short) 16);
		titleFont.setColor(IndexedColors.WHITE.getIndex());

		titleCellStyle.setFont(titleFont);

		//Sub-Header Cell Style
		CellStyle subHdrCellStyle = wb.createCellStyle();
		subHdrCellStyle = wb.createCellStyle();
		subHdrCellStyle.setWrapText(true);
		subHdrCellStyle.setAlignment(HorizontalAlignment.CENTER_SELECTION);
		subHdrCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		subHdrCellStyle.setFillForegroundColor(IndexedColors.TAN.getIndex());
		subHdrCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);

		Font shFont = wb.createFont();
		shFont.setFontName("Arial");
		shFont.setBold(true);
		shFont.setFontHeightInPoints((short) 14);

		subHdrCellStyle.setFont(shFont);

		//		//Header Cell Style
		CellStyle headerCellStyle = wb.createCellStyle();
		headerCellStyle = wb.createCellStyle();
		headerCellStyle.setWrapText(true);
		headerCellStyle.setAlignment(HorizontalAlignment.CENTER_SELECTION);
		headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		headerCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		headerCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);

		headerCellStyle.setBorderBottom(BorderStyle.THICK);
		headerCellStyle.setBorderTop(BorderStyle.THICK);
		headerCellStyle.setBorderRight(BorderStyle.THICK);
		headerCellStyle.setBorderLeft(BorderStyle.THICK);

		Font headerFont = wb.createFont();
		headerFont.setFontName("Arial");
		headerFont.setBold(true);

		headerCellStyle.setFont(headerFont);

		//Service Cell Style
		CellStyle serviceCellStyle = wb.createCellStyle();
		serviceCellStyle = wb.createCellStyle();
		serviceCellStyle.setWrapText(true);
		serviceCellStyle.setAlignment(HorizontalAlignment.CENTER_SELECTION);
		serviceCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		serviceCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		serviceCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);

		serviceCellStyle.setBorderBottom(BorderStyle.THIN);
		serviceCellStyle.setBorderTop(BorderStyle.THIN);
		serviceCellStyle.setBorderRight(BorderStyle.THIN);
		serviceCellStyle.setBorderLeft(BorderStyle.THIN);

		Font serviceFont = wb.createFont();
		serviceFont.setFontName("Arial");
		serviceFont.setBold(true);
		serviceCellStyle.setFont(serviceFont);

		//Normal Cell Style
		CellStyle normalCellStyle = wb.createCellStyle();
		normalCellStyle = wb.createCellStyle();
		normalCellStyle.setWrapText(true);
		normalCellStyle.setAlignment(HorizontalAlignment.CENTER_SELECTION);
		normalCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		normalCellStyle.setBorderBottom(BorderStyle.THIN);
		normalCellStyle.setBorderTop(BorderStyle.THIN);
		normalCellStyle.setBorderRight(BorderStyle.THIN);
		normalCellStyle.setBorderLeft(BorderStyle.THIN);

		Font normalCellFont = wb.createFont();
		normalCellFont.setFontName("Arial");
		normalCellFont.setBold(false);

		normalCellStyle.setFont(normalCellFont);

		//Accepted Cell Style
		CellStyle acceptedCellStyle = wb.createCellStyle();
		acceptedCellStyle = wb.createCellStyle();
		acceptedCellStyle.setWrapText(true);
		acceptedCellStyle.setAlignment(HorizontalAlignment.CENTER_SELECTION);
		acceptedCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		acceptedCellStyle.setBorderBottom(BorderStyle.THIN);
		acceptedCellStyle.setBorderTop(BorderStyle.THIN);
		acceptedCellStyle.setBorderRight(BorderStyle.THIN);
		acceptedCellStyle.setBorderLeft(BorderStyle.THIN);

		Font accptedFont = wb.createFont();
		accptedFont.setFontName("Arial");
		accptedFont.setBold(false);
		accptedFont.setColor(IndexedColors.GREEN.getIndex());

		acceptedCellStyle.setFont(accptedFont);

		//Rejected Cell Style
		CellStyle rejectedCellStyle = wb.createCellStyle();
		rejectedCellStyle = wb.createCellStyle();
		rejectedCellStyle.setWrapText(true);
		rejectedCellStyle.setAlignment(HorizontalAlignment.CENTER_SELECTION);
		rejectedCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		rejectedCellStyle.setBorderBottom(BorderStyle.THIN);
		rejectedCellStyle.setBorderTop(BorderStyle.THIN);
		rejectedCellStyle.setBorderRight(BorderStyle.THIN);
		rejectedCellStyle.setBorderLeft(BorderStyle.THIN);

		Font rejectedFont = wb.createFont();
		rejectedFont.setFontName("Arial");
		rejectedFont.setBold(false);
		rejectedFont.setColor(IndexedColors.RED.getIndex());

		rejectedCellStyle.setFont(rejectedFont);

		//SubTotal Cell Style
		CellStyle subTotalCellStyle = wb.createCellStyle();
		subTotalCellStyle.setWrapText(true);
		subTotalCellStyle.setAlignment(HorizontalAlignment.CENTER_SELECTION);
		subTotalCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		subTotalCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		subTotalCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		subTotalCellStyle.setBorderTop(BorderStyle.THIN);
		subTotalCellStyle.setBorderRight(BorderStyle.THIN);
		subTotalCellStyle.setBorderLeft(BorderStyle.THIN);
		subTotalCellStyle.setBorderBottom(BorderStyle.DOUBLE);

		Font subTotalFont = wb.createFont();
		subTotalFont.setFontName("Arial");
		subTotalFont.setBold(true);
		//		subTotalFont.setFontHeight((short)12);
		//
		subTotalCellStyle.setFont(subTotalFont);
		//
		//Empty Cell Style
		CellStyle emptyCellStyle = wb.createCellStyle();
		emptyCellStyle.setWrapText(true);

		//Grand Total Cell Style
		CellStyle grandTotalCellStyle = wb.createCellStyle();
		grandTotalCellStyle.setWrapText(true);
		grandTotalCellStyle.setAlignment(HorizontalAlignment.CENTER_SELECTION);
		grandTotalCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		grandTotalCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		grandTotalCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		grandTotalCellStyle.setBorderTop(BorderStyle.THICK);
		grandTotalCellStyle.setBorderRight(BorderStyle.THICK);
		grandTotalCellStyle.setBorderLeft(BorderStyle.THICK);
		grandTotalCellStyle.setBorderBottom(BorderStyle.THICK);

		Font gtTotalFont = wb.createFont();
		gtTotalFont.setFontName("Arial");
		gtTotalFont.setBold(true);
		gtTotalFont.setFontHeightInPoints((short) 16);

		grandTotalCellStyle.setFont(gtTotalFont);

		//Grand Total Cell Style - TOTAL
		CellStyle gtTotalCellStyle = wb.createCellStyle();
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
		CellStyle gtAccptdCellStyle = wb.createCellStyle();
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
		CellStyle gtRjctdCellStyle = wb.createCellStyle();
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


		//=====================GENERATE REPORT==========================//

		//GENERATE TITLE ROW 
		Row titleRow = sheet.createRow(0);

		Cell titleCell = titleRow.createCell(0);
		titleCell.setCellStyle(titleCellStyle);
		titleCell.setCellValue("BANKSERVAFRICA PRODUCTION - TRANSACTION VOLUME - MANDATES BATCH - "+month.toUpperCase()+" "+year);

		Cell nullCell1 = titleRow.createCell(1);
		nullCell1.setCellStyle(titleCellStyle);
		Cell nullCell2 = titleRow.createCell(2);
		nullCell2.setCellStyle(titleCellStyle);
		Cell nullCell3 = titleRow.createCell(3);
		nullCell3.setCellStyle(titleCellStyle);
		Cell nullCell4 = titleRow.createCell(4);
		nullCell4.setCellStyle(titleCellStyle);
		//		Cell nullCell5 = titleRow.createCell(5);
		//		nullCell5.setCellStyle(titleCellStyle);
		//		Cell nullCell6 = titleRow.createCell(6);
		//		nullCell6.setCellStyle(titleCellStyle);
		//		Cell nullCell7 = titleRow.createCell(7);
		//		nullCell7.setCellStyle(titleCellStyle);
		//		Cell nullCell8 = titleRow.createCell(8);
		//		nullCell8.setCellStyle(titleCellStyle);
		//		Cell nullCell9 = titleRow.createCell(9);
		//		nullCell9.setCellStyle(titleCellStyle);
		//		Cell nullCell10 = titleRow.createCell(10);
		//		nullCell10.setCellStyle(titleCellStyle);

		Row titleRow1 = sheet.createRow(1);

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
		//		Cell nullCell51 = titleRow1.createCell(5);
		//		nullCell51.setCellStyle(titleCellStyle);
		//		Cell nullCell61 = titleRow1.createCell(6);
		//		nullCell61.setCellStyle(titleCellStyle);
		//		Cell nullCell71 = titleRow1.createCell(7);
		//		nullCell71.setCellStyle(titleCellStyle);
		//		Cell nullCell81 = titleRow1.createCell(8);
		//		nullCell81.setCellStyle(titleCellStyle);
		//		Cell nullCell91 = titleRow1.createCell(9);
		//		nullCell91.setCellStyle(titleCellStyle);
		//		Cell nullCell101 = titleRow1.createCell(10);
		//		nullCell101.setCellStyle(titleCellStyle);

		//Merge Cell 0 to 10
		sheet.addMergedRegion(new CellRangeAddress(0,2, 0, 4));

		//GENERATE SUB TITLE ROW
		Row subHdrRow = sheet.createRow(3);

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
		sheet.addMergedRegion(new CellRangeAddress(3,3, 0, 4));

		//		Cell shNull5 = subHdrRow.createCell(5);
		//		shNull5.setCellStyle(emptyCellStyle);
		//
		//		Cell subHdrDbtCell = subHdrRow.createCell(6);
		//		subHdrDbtCell.setCellStyle(subHdrCellStyle);
		//		subHdrDbtCell.setCellValue("SUBMITTED BY DEBTOR BANKS");
		//
		//		//		Merge Cell 6 to 10
		//		sheet.addMergedRegion(new CellRangeAddress(2,2, 6, 10));

		//GENERATE COLUMN HEADERS
		Row hdrRow = sheet.createRow(4);

		Cell serviceCell = hdrRow.createCell(0);
		serviceCell.setCellStyle(headerCellStyle);
		serviceCell.setCellValue("SERVICE");

		Cell bankCell = hdrRow.createCell(1);
		bankCell.setCellStyle(headerCellStyle);
		bankCell.setCellValue("BANKS");

		Cell nrOfTxnsCell = hdrRow.createCell(2);
		nrOfTxnsCell.setCellStyle(headerCellStyle);
		nrOfTxnsCell.setCellValue("TOT NR OF TXNS");

		Cell accpTxnsCells = hdrRow.createCell(3);
		accpTxnsCells.setCellStyle(headerCellStyle);
		accpTxnsCells.setCellValue("ACCEPTED TXNS");

		Cell rjctTxnsCells = hdrRow.createCell(4);
		rjctTxnsCells.setCellStyle(headerCellStyle);
		rjctTxnsCells.setCellValue("REJECTED TXNS");

		//		Cell hdrCell = hdrRow.createCell(5);
		//		hdrCell.setCellStyle(emptyCellStyle);
		//
		//		Cell serviceCellDbt = hdrRow.createCell(6);
		//		serviceCellDbt.setCellStyle(headerCellStyle);
		//		serviceCellDbt.setCellValue("SERVICE");
		//
		//		Cell bankCellDbt = hdrRow.createCell(7);
		//		bankCellDbt.setCellStyle(headerCellStyle);
		//		bankCellDbt.setCellValue("BANKS");
		//
		//		Cell nrOfTxnsCellDbt = hdrRow.createCell(8);
		//		nrOfTxnsCellDbt.setCellStyle(headerCellStyle);
		//		nrOfTxnsCellDbt.setCellValue("TOT NR OF TXNS");
		//
		//		Cell accpTxnsCellsDbt = hdrRow.createCell(9);
		//		accpTxnsCellsDbt.setCellStyle(headerCellStyle);
		//		accpTxnsCellsDbt.setCellValue("ACCEPTED TXNS");
		//
		//		Cell rjctTxnsCellsDbt = hdrRow.createCell(10);
		//		rjctTxnsCellsDbt.setCellStyle(headerCellStyle);
		//		rjctTxnsCellsDbt.setCellValue("REJECTED TXNS");


		//		========================MANIN============================//
		int rowCount = 6;

		//Retrieve MANIN Counts
		List<MonthlyVolumeCountEntityModel> maninCountList = (List<MonthlyVolumeCountEntityModel>) beanRemote.retrieveMndtCountByCreditorBanks(strFromDate,strToDate,"MANIN");

		if(maninCountList != null && maninCountList.size() > 0)
		{
			BigDecimal stTotal = BigDecimal.ZERO;
			BigDecimal stAccptd = BigDecimal.ZERO;
			BigDecimal stRejected = BigDecimal.ZERO;

			int fstCount = 0;

			for (MonthlyVolumeCountEntityModel maninCountEntity : maninCountList) 
			{
				Row maninRow = sheet.createRow(rowCount);
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

				Cell maninBank = maninRow.createCell(1);
				maninBank.setCellStyle(normalCellStyle);
				maninBank.setCellValue(maninCountEntity.getInstId());

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
			Row maninSTRow = sheet.createRow(rowCount);

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

		//SubTotal the Rows
		Row emptyRow = sheet.createRow(rowCount);
		rowCount++;

		//		========================MANAM============================//
		List<MonthlyVolumeCountEntityModel> manamCountList = (List<MonthlyVolumeCountEntityModel>) beanRemote.retrieveMndtCountByCreditorBanks(strFromDate,strToDate,"MANAM");

		if(manamCountList != null && manamCountList.size() > 0)
		{
			BigDecimal stTotal = BigDecimal.ZERO;
			BigDecimal stAccptd = BigDecimal.ZERO;
			BigDecimal stRejected = BigDecimal.ZERO;

			int firstCount = 0;
			for (MonthlyVolumeCountEntityModel manamCountEntity : manamCountList) 
			{
				Row manamRow = sheet.createRow(rowCount);
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

				Cell maninBank = manamRow.createCell(1);
				maninBank.setCellStyle(normalCellStyle);
				maninBank.setCellValue(manamCountEntity.getInstId());

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
			Row manamSTRow = sheet.createRow(rowCount);

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

		//Empty Rows
		Row emptyRow1 = sheet.createRow(rowCount);
		rowCount++;
		//		========================MANCN============================//
		List<MonthlyVolumeCountEntityModel> mancnCountList = (List<MonthlyVolumeCountEntityModel>) beanRemote.retrieveMndtCountByCreditorBanks(strFromDate,strToDate,"MANCN");

		if(mancnCountList != null && mancnCountList.size() > 0)
		{
			BigDecimal stTotal = BigDecimal.ZERO;
			BigDecimal stAccptd = BigDecimal.ZERO;
			BigDecimal stRejected = BigDecimal.ZERO;

			int firstCount = 0;
			for (MonthlyVolumeCountEntityModel mancnCountEntity : mancnCountList) 
			{
				Row mancnRow = sheet.createRow(rowCount);
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

				Cell maninBank = mancnRow.createCell(1);
				maninBank.setCellStyle(normalCellStyle);
				maninBank.setCellValue(mancnCountEntity.getInstId());

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
			Row mancnSTRow = sheet.createRow(rowCount);

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

		//Empty Rows
		Row emptyRow2 = sheet.createRow(rowCount);
		rowCount++;
		//		========================MANRI============================//
		List<MonthlyVolumeCountEntityModel> manriCountList = (List<MonthlyVolumeCountEntityModel>) beanRemote.retrieveMndtCountByCreditorBanks(strFromDate,strToDate,"MANRI");

		if(manriCountList != null && manriCountList.size() > 0)
		{
			BigDecimal stTotal = BigDecimal.ZERO;
			BigDecimal stAccptd = BigDecimal.ZERO;
			BigDecimal stRejected = BigDecimal.ZERO;

			int firstCount = 0;
			for (MonthlyVolumeCountEntityModel manriCountEntity : manriCountList) 
			{
				Row manriRow = sheet.createRow(rowCount);
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
			Row manriSTRow = sheet.createRow(rowCount);

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

		//Empty Rows
		Row emptyRow3 = sheet.createRow(rowCount);
		rowCount++;
		//		========================SRINP============================//
		List<MonthlyVolumeCountEntityModel> srinpCountList = (List<MonthlyVolumeCountEntityModel>) beanRemote.retrieveMndtCountByCreditorBanks(strFromDate,strToDate,"SRINP");

		if(srinpCountList != null && srinpCountList.size() > 0)
		{
			BigDecimal stTotal = BigDecimal.ZERO;
			BigDecimal stAccptd = BigDecimal.ZERO;
			BigDecimal stRejected = BigDecimal.ZERO;

			int firstCount = 0;
			for (MonthlyVolumeCountEntityModel srinpCountEntity : srinpCountList) 
			{
				Row srinpRow = sheet.createRow(rowCount);
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
			Row srinpSTRow = sheet.createRow(rowCount);

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

		//Empty Rows
		Row emptyRow4 = sheet.createRow(rowCount);
		rowCount++;

		//GENERATE SUB TITLE ROW
		Row subHdrRow1 = sheet.createRow(rowCount);

		Cell subHdrDbtCell = subHdrRow1.createCell(0);
		subHdrDbtCell.setCellStyle(subHdrCellStyle);
		subHdrDbtCell.setCellValue("SUBMITTED BY DEBTOR BANKS");

		Cell shNull11 = subHdrRow1.createCell(1);
		shNull11.setCellStyle(subHdrCellStyle);
		Cell shNull12 = subHdrRow1.createCell(2);
		shNull12.setCellStyle(subHdrCellStyle);
		Cell shNull13 = subHdrRow1.createCell(3);
		shNull13.setCellStyle(subHdrCellStyle);
		Cell shNull14 = subHdrRow1.createCell(4);
		shNull14.setCellStyle(subHdrCellStyle);

		//Merge Cell 0 to 4
		sheet.addMergedRegion(new CellRangeAddress(rowCount,rowCount, 0, 4));
		rowCount++;

		//GENERATE COLUMN HEADERS
		Row hdrRow1 = sheet.createRow(rowCount);

		Cell serviceCell1 = hdrRow1.createCell(0);
		serviceCell1.setCellStyle(headerCellStyle);
		serviceCell1.setCellValue("SERVICE");

		Cell bankCell1 = hdrRow1.createCell(1);
		bankCell1.setCellStyle(headerCellStyle);
		bankCell1.setCellValue("BANKS");

		Cell nrOfTxnsCell1 = hdrRow1.createCell(2);
		nrOfTxnsCell1.setCellStyle(headerCellStyle);
		nrOfTxnsCell1.setCellValue("TOT NR OF TXNS");

		Cell accpTxnsCells1 = hdrRow1.createCell(3);
		accpTxnsCells1.setCellStyle(headerCellStyle);
		accpTxnsCells1.setCellValue("ACCEPTED TXNS");

		Cell rjctTxnsCells1 = hdrRow1.createCell(4);
		rjctTxnsCells1.setCellStyle(headerCellStyle);
		rjctTxnsCells1.setCellValue("REJECTED TXNS");

		rowCount++;
		//		========================ST101============================//
		List<MonthlyVolumeCountEntityModel> st101CountList = (List<MonthlyVolumeCountEntityModel>) beanRemote.retrieveMndtCountByDebtorBanks(strFromDate,strToDate,"ST101");

		if(st101CountList != null && st101CountList.size() > 0)
		{
			BigDecimal stTotal = BigDecimal.ZERO;
			BigDecimal stAccptd = BigDecimal.ZERO;
			BigDecimal stRejected = BigDecimal.ZERO;

			int firstCount = 0;
			for (MonthlyVolumeCountEntityModel st101CountEntity : st101CountList) 
			{
				Row st101Row = sheet.createRow(rowCount);
				Cell st101ServCell = st101Row.createCell(0);

				if(firstCount == 0)
				{
					st101ServCell.setCellStyle(serviceCellStyle);
					st101ServCell.setCellValue("ST101");
					firstCount = 1;
				}
				else
				{
					st101ServCell.setCellStyle(normalCellStyle);
				}

				Cell st101Bank = st101Row.createCell(1);
				st101Bank.setCellStyle(normalCellStyle);
				st101Bank.setCellValue(st101CountEntity.getInstId());

				Cell totalNrMsgs = st101Row.createCell(2);
				totalNrMsgs.setCellStyle(normalCellStyle);
				totalNrMsgs.setCellValue(st101CountEntity.getNrOfMsgs().intValue());
				stTotal = stTotal.add(st101CountEntity.getNrOfMsgs());

				Cell accptdMsgs = st101Row.createCell(3);
				accptdMsgs.setCellStyle(acceptedCellStyle);
				accptdMsgs.setCellValue(st101CountEntity.getNrOfAccpMsgs().intValue());
				stAccptd = stAccptd.add(st101CountEntity.getNrOfAccpMsgs());

				Cell rjctdMsgs = st101Row.createCell(4);
				rjctdMsgs.setCellStyle(rejectedCellStyle);
				rjctdMsgs.setCellValue(st101CountEntity.getNrOfRjctMsgs().intValue());
				stRejected = stRejected.add(st101CountEntity.getNrOfRjctMsgs());

				rowCount++;
			}

			//SubTotal the Rows
			Row st101STRow = sheet.createRow(rowCount);

			Cell st101Serv = st101STRow.createCell(0);
			st101Serv.setCellStyle(subTotalCellStyle);

			Cell st101Bank = st101STRow.createCell(1);
			st101Bank.setCellStyle(subTotalCellStyle);
			st101Bank.setCellValue("Total");

			Cell sttotalNrMsgs = st101STRow.createCell(2);
			sttotalNrMsgs.setCellStyle(subTotalCellStyle);
			sttotalNrMsgs.setCellValue(stTotal.intValue());

			Cell staccp = st101STRow.createCell(3);
			staccp.setCellStyle(subTotalCellStyle);
			staccp.setCellValue(stAccptd.intValue());

			Cell stRjct = st101STRow.createCell(4);
			stRjct.setCellStyle(subTotalCellStyle);
			stRjct.setCellValue(stRejected.intValue());

			//Add to Grand Total
			gtTotal = gtTotal.add(stTotal);
			gtAccptd = gtAccptd.add(stAccptd);
			gtRejected = gtRejected.add(stRejected);

			rowCount++;
		}

		//Empty Rows
		Row emptyRow5 = sheet.createRow(rowCount);
		rowCount++;

		//		========================MANAC============================//
		List<MonthlyVolumeCountEntityModel> manacCountList = (List<MonthlyVolumeCountEntityModel>) beanRemote.retrieveMndtCountByDebtorBanks(strFromDate,strToDate,"MANAC");

		if(manacCountList != null && manacCountList.size() > 0)
		{
			BigDecimal stTotal = BigDecimal.ZERO;
			BigDecimal stAccptd = BigDecimal.ZERO;
			BigDecimal stRejected = BigDecimal.ZERO;

			int firstCount = 0;
			for (MonthlyVolumeCountEntityModel manacCountEntity : manacCountList) 
			{
				Row manacRow = sheet.createRow(rowCount);
				Cell manacServCell = manacRow.createCell(0);

				if(firstCount == 0)
				{
					manacServCell.setCellStyle(serviceCellStyle);
					manacServCell.setCellValue("MANAC");
					firstCount = 1;
				}
				else
				{
					manacServCell.setCellStyle(normalCellStyle);
				}

				Cell manacBank = manacRow.createCell(1);
				manacBank.setCellStyle(normalCellStyle);
				manacBank.setCellValue(manacCountEntity.getInstId());

				Cell totalNrMsgs = manacRow.createCell(2);
				totalNrMsgs.setCellStyle(normalCellStyle);
				totalNrMsgs.setCellValue(manacCountEntity.getNrOfMsgs().intValue());
				stTotal = stTotal.add(manacCountEntity.getNrOfMsgs());

				Cell accptdMsgs = manacRow.createCell(3);
				accptdMsgs.setCellStyle(acceptedCellStyle);
				accptdMsgs.setCellValue(manacCountEntity.getNrOfAccpMsgs().intValue());
				stAccptd = stAccptd.add(manacCountEntity.getNrOfAccpMsgs());

				Cell rjctdMsgs = manacRow.createCell(4);
				rjctdMsgs.setCellStyle(rejectedCellStyle);
				rjctdMsgs.setCellValue(manacCountEntity.getNrOfRjctMsgs().intValue());
				stRejected = stRejected.add(manacCountEntity.getNrOfRjctMsgs());

				rowCount++;
			}

			//SubTotal the Rows
			Row manacSTRow = sheet.createRow(rowCount);

			Cell manacServ = manacSTRow.createCell(0);
			manacServ.setCellStyle(subTotalCellStyle);

			Cell manacBank = manacSTRow.createCell(1);
			manacBank.setCellStyle(subTotalCellStyle);
			manacBank.setCellValue("Total");

			Cell sttotalNrMsgs = manacSTRow.createCell(2);
			sttotalNrMsgs.setCellStyle(subTotalCellStyle);
			sttotalNrMsgs.setCellValue(stTotal.intValue());

			Cell staccp = manacSTRow.createCell(3);
			staccp.setCellStyle(subTotalCellStyle);
			staccp.setCellValue(stAccptd.intValue());

			Cell stRjct = manacSTRow.createCell(4);
			stRjct.setCellStyle(subTotalCellStyle);
			stRjct.setCellValue(stRejected.intValue());

			//Add to Grand Total
			gtTotal = gtTotal.add(stTotal);
			gtAccptd = gtAccptd.add(stAccptd);
			gtRejected = gtRejected.add(stRejected);

			rowCount++;
		}

		//Empty Rows
		Row emptyRow6 = sheet.createRow(rowCount);
		rowCount++;

		//		========================MANRT============================//
		List<MonthlyVolumeCountEntityModel> manrtCountList = (List<MonthlyVolumeCountEntityModel>) beanRemote.retrieveMndtCountByDebtorBanks(strFromDate,strToDate,"MANRT");

		if(manrtCountList != null && manrtCountList.size() > 0)
		{
			BigDecimal stTotal = BigDecimal.ZERO;
			BigDecimal stAccptd = BigDecimal.ZERO;
			BigDecimal stRejected = BigDecimal.ZERO;

			int firstCount = 0;
			for (MonthlyVolumeCountEntityModel manrtCountEntity : manrtCountList) 
			{
				Row manrtRow = sheet.createRow(rowCount);
				Cell manrtServCell = manrtRow.createCell(0);

				if(firstCount == 0)
				{
					manrtServCell.setCellStyle(serviceCellStyle);
					manrtServCell.setCellValue("MANRT");
					firstCount = 1;
				}
				else
				{
					manrtServCell.setCellStyle(normalCellStyle);
				}

				Cell manrtBank = manrtRow.createCell(1);
				manrtBank.setCellStyle(normalCellStyle);
				manrtBank.setCellValue(manrtCountEntity.getInstId());

				Cell totalNrMsgs = manrtRow.createCell(2);
				totalNrMsgs.setCellStyle(normalCellStyle);
				totalNrMsgs.setCellValue(manrtCountEntity.getNrOfMsgs().intValue());
				stTotal = stTotal.add(manrtCountEntity.getNrOfMsgs());

				Cell accptdMsgs = manrtRow.createCell(3);
				accptdMsgs.setCellStyle(acceptedCellStyle);
				accptdMsgs.setCellValue(manrtCountEntity.getNrOfAccpMsgs().intValue());
				stAccptd = stAccptd.add(manrtCountEntity.getNrOfAccpMsgs());

				Cell rjctdMsgs = manrtRow.createCell(4);
				rjctdMsgs.setCellStyle(rejectedCellStyle);
				rjctdMsgs.setCellValue(manrtCountEntity.getNrOfRjctMsgs().intValue());
				stRejected = stRejected.add(manrtCountEntity.getNrOfRjctMsgs());

				rowCount++;
			}

			//SubTotal the Rows
			Row manrtSTRow = sheet.createRow(rowCount);

			Cell manrtServ = manrtSTRow.createCell(0);
			manrtServ.setCellStyle(subTotalCellStyle);

			Cell manrtBank = manrtSTRow.createCell(1);
			manrtBank.setCellStyle(subTotalCellStyle);
			manrtBank.setCellValue("Total");

			Cell sttotalNrMsgs = manrtSTRow.createCell(2);
			sttotalNrMsgs.setCellStyle(subTotalCellStyle);
			sttotalNrMsgs.setCellValue(stTotal.intValue());

			Cell staccp = manrtSTRow.createCell(3);
			staccp.setCellStyle(subTotalCellStyle);
			staccp.setCellValue(stAccptd.intValue());

			Cell stRjct = manrtSTRow.createCell(4);
			stRjct.setCellStyle(subTotalCellStyle);
			stRjct.setCellValue(stRejected.intValue());

			//Add to Grand Total
			gtTotal = gtTotal.add(stTotal);
			gtAccptd = gtAccptd.add(stAccptd);
			gtRejected = gtRejected.add(stRejected);

			rowCount++;
		}

		//Empty Rows
		Row emptyRow7 = sheet.createRow(rowCount);
		rowCount++;

		//		========================SPINP============================//
		List<MonthlyVolumeCountEntityModel> spinpCountList = (List<MonthlyVolumeCountEntityModel>) beanRemote.retrieveMndtCountByDebtorBanks(strFromDate,strToDate,"SPINP");

		if(spinpCountList != null && spinpCountList.size() > 0)
		{
			BigDecimal stTotal = BigDecimal.ZERO;
			BigDecimal stAccptd = BigDecimal.ZERO;
			BigDecimal stRejected = BigDecimal.ZERO;

			int firstCount = 0;
			for (MonthlyVolumeCountEntityModel spinpCountEntity : spinpCountList) 
			{
				Row spinpRow = sheet.createRow(rowCount);
				Cell spinpServCell = spinpRow.createCell(0);

				if(firstCount == 0)
				{
					spinpServCell.setCellStyle(serviceCellStyle);
					spinpServCell.setCellValue("SPINP");
					firstCount = 1;
				}
				else
				{
					spinpServCell.setCellStyle(normalCellStyle);
				}

				Cell spinpBank = spinpRow.createCell(1);
				spinpBank.setCellStyle(normalCellStyle);
				spinpBank.setCellValue(spinpCountEntity.getInstId());

				Cell totalNrMsgs = spinpRow.createCell(2);
				totalNrMsgs.setCellStyle(normalCellStyle);
				totalNrMsgs.setCellValue(spinpCountEntity.getNrOfMsgs().intValue());
				stTotal = stTotal.add(spinpCountEntity.getNrOfMsgs());

				Cell accptdMsgs = spinpRow.createCell(3);
				accptdMsgs.setCellStyle(acceptedCellStyle);
				accptdMsgs.setCellValue(spinpCountEntity.getNrOfAccpMsgs().intValue());
				stAccptd = stAccptd.add(spinpCountEntity.getNrOfAccpMsgs());

				Cell rjctdMsgs = spinpRow.createCell(4);
				rjctdMsgs.setCellStyle(rejectedCellStyle);
				rjctdMsgs.setCellValue(spinpCountEntity.getNrOfRjctMsgs().intValue());
				stRejected = stRejected.add(spinpCountEntity.getNrOfRjctMsgs());

				rowCount++;
			}

			//SubTotal the Rows
			Row spinpSTRow = sheet.createRow(rowCount);

			Cell spinpServ = spinpSTRow.createCell(0);
			spinpServ.setCellStyle(subTotalCellStyle);

			Cell spinpBank = spinpSTRow.createCell(1);
			spinpBank.setCellStyle(subTotalCellStyle);
			spinpBank.setCellValue("Total");

			Cell sttotalNrMsgs = spinpSTRow.createCell(2);
			sttotalNrMsgs.setCellStyle(subTotalCellStyle);
			sttotalNrMsgs.setCellValue(stTotal.intValue());

			Cell staccp = spinpSTRow.createCell(3);
			staccp.setCellStyle(subTotalCellStyle);
			staccp.setCellValue(stAccptd.intValue());

			Cell stRjct = spinpSTRow.createCell(4);
			stRjct.setCellStyle(subTotalCellStyle);
			stRjct.setCellValue(stRejected.intValue());

			//Add to Grand Total
			gtTotal = gtTotal.add(stTotal);
			gtAccptd = gtAccptd.add(stAccptd);
			gtRejected = gtRejected.add(stRejected);

			rowCount++;
		}

		//Empty Rows
		Row emptyRow8 = sheet.createRow(rowCount);
		rowCount++;

		Row grandTotalRow = sheet.createRow(rowCount);
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

		Row nullRow = sheet.createRow(rowCount);

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


		//Merge Cell 0 to 1
		sheet.addMergedRegion(new CellRangeAddress(rowCount-1,rowCount, 0, 1));
		sheet.addMergedRegion(new CellRangeAddress(rowCount-1,rowCount, 2, 2));
		sheet.addMergedRegion(new CellRangeAddress(rowCount-1,rowCount, 3, 3));
		sheet.addMergedRegion(new CellRangeAddress(rowCount-1,rowCount, 4, 4));

		// Write the output to a file
		FileOutputStream fileOut = new FileOutputStream(fileName);
		wb.write(fileOut);
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

	public  void copyFile(String fileName) throws IOException 
	{
		File tmpFile = new File(tempDir, fileName);
		File destFile = new File(reportDir, fileName);

		log.info("tmpFile ==> "+ tmpFile);
		log.info("destFile===> "+ destFile);

		Files.copy(tmpFile, destFile);
		log.info("Copying "+fileName+"from temp to output directory...");
	}


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



}
