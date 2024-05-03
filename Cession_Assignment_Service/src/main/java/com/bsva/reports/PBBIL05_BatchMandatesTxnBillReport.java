package com.bsva.reports;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.time.DurationFormatUtils;
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
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.bsva.commons.model.CreditorBankModel;
import com.bsva.commons.model.SystemParameterModel;
import com.bsva.entities.BatchTxnBillReportEntity;
import com.bsva.entities.CasCnfgReportNamesEntity;
import com.bsva.entities.CasOpsRepSeqNrEntity;
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
 *
 */
public class PBBIL05_BatchMandatesTxnBillReport {
	public static Logger log=Logger.getLogger("PBBIL05-BatchTxnBillReport");
	long startTime = System.nanoTime();
	private String fileName;

	public static ServiceBeanRemote beanRemote;
	public static ReportBeanRemote reportBeanRemote;
	private static AdminBeanRemote adminBeanRemote;
	private static PropertyUtilRemote propertyUtilRemote;

	SystemParameterModel systemParameterModel;

	String reportName, reportNr, reportDir = null, tempDir = null;
	int fileSeqNo =000;
	String xlsFileName = null, pbbil05 = null;
	Date reportDate = null;

	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	DateFormat fileTimeFormat = new SimpleDateFormat("MM-yyyy");
	
	int lastSeqNoUsed;
	String strMonth = null;
	String month = null;
	String year = null;
	String strMonthFront = null;
	String monthFront = null;
	String yearFront = null;
	Date firstDate, lastDate = null;
	String strFromDate = null, strToDate = null;
	boolean reportRunFromFrontEnd = false;

	List<BatchTxnBillReportEntity> batchMndtTxnBillList;
	List<CreditorBankModel> creditorBankModelList;
	
	public TreeMap<String, List<BatchTxnBillReportEntity>> pbbil05Map;
	
	public void generateReport(Date fromDate, Date toDate, boolean frontEndRun) throws FileNotFoundException, DocumentException
	{
		contextAdminBeanCheck();
		contextCheck();
		contextReportBeanCheck();
		contextPropertyFileCheck();

		systemParameterModel = new SystemParameterModel();
		systemParameterModel =(SystemParameterModel) adminBeanRemote.retrieveWebActiveSysParameter();
		
		this.reportRunFromFrontEnd = frontEndRun;
		
		try
		{
			tempDir = propertyUtilRemote.getPropValue("ExtractTemp.Out");
			reportDir= propertyUtilRemote.getPropValue("Reports.Output");

			pbbil05 = propertyUtilRemote.getPropValue("RPT.BANK.BATCH.TXNBILL");
			log.info("pbbil05 from properties: "+pbbil05);
		}
		catch(Exception ex)
		{
			log.error("pbbil05- Could not find CessionAssignment.properties in classpath");
			reportDir = "/home/opsjava/Delivery/Cession_Assign/Output/Reports/";
			tempDir="/home/opsjava/Delivery/Cession_Assign/Output/temp/";
		}

		//Retrieve Report Name
		CasCnfgReportNamesEntity reportNameEntity = new CasCnfgReportNamesEntity();
		reportNameEntity = (CasCnfgReportNamesEntity) adminBeanRemote.retrieveReportName(pbbil05);

		if (reportNameEntity != null) {
			if (reportNameEntity.getActiveInd().equalsIgnoreCase("Y")) {
				reportNr = reportNameEntity.getReportNr();
				reportName = reportNameEntity.getReportName();
				
				if(reportRunFromFrontEnd)
				{
					strFromDate = dateFormat.format(fromDate);
					strToDate = dateFormat.format(toDate);
				}
				else
				{
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

//						strFromDate = "2018-06-01";
//						strToDate = "2018-06-30";
						log.info("strFromDate ==>" + strFromDate);
						log.info("strToDate ==> "+ strToDate);
					}
				}
				
				creditorBankModelList = new ArrayList<CreditorBankModel>();
				creditorBankModelList = (List<CreditorBankModel>) adminBeanRemote.retrieveActiveCreditorBank();
				
				if(creditorBankModelList != null && creditorBankModelList.size() > 0)
				{
					//Retrieve data
					loadData();

					for (CreditorBankModel creditorBankModel : creditorBankModelList) 
					{
						startTime = System.nanoTime();
						List<BatchTxnBillReportEntity> crBatchTxnBillList = pbbil05Map.get(creditorBankModel.getMemberNo());

						log.info("GENERATING REPORT PBBIL05 FOR "+creditorBankModel.getMemberNo());

						if(crBatchTxnBillList!= null && crBatchTxnBillList.size() > 0)
						{
							try
							{
								log.info("crBatchTxnBillList Size: "+crBatchTxnBillList.size());
								generateReportDetail(reportNr, reportName, creditorBankModel.getMemberNo(), crBatchTxnBillList);
							}
							catch(DocumentException | IOException ex)
							{
								log.error("Error PBBIL05:  generatePBBIL05Report"+ ex.getMessage());
								ex.printStackTrace();
							}
						}
						long endTime = System.nanoTime();
						long duration = (endTime - startTime) / 500000;
						log.info("[PBBIL05 Report Duration: "+DurationFormatUtils.formatDuration(duration, "HH:mm:ss.S")+" milliseconds]");
					}
				}
				
				
				
				
				
//				log.info("*****REPORT " + reportNr + " GENERATING*****");
//
//				if(batchMndtTxnBillList != null && batchMndtTxnBillList.size() > 0) 
//				{
//					try
//					{
//						generateReportDetail(reportNr, reportName);
//					}
//					catch(Exception ex)
//					{
//						log.error("Error on generating PBBIL05 report: "+ex.getMessage());
//						ex.printStackTrace();
//					}
//				}
			}
		}
//
//		long endTime = System.nanoTime();
//		long duration = (endTime - startTime) / 500000;
//		log.info("[PBBIL05 Report Duration: "+DurationFormatUtils.formatDuration(duration, "HH:mm:ss.S")+" milliseconds]");
	}
	
	public void generateReportDetail(String reportNo,String reportname,String crBankNo, List<BatchTxnBillReportEntity> crTxnBillEntityList) throws DocumentException, IOException
	{
		String strSeqNo; 
		
		String bankId= crBankNo.substring(2, 6);
		log.debug("bankId---->"+bankId);

		SimpleDateFormat rptNameFormat = new SimpleDateFormat("ddMMyyyy");
		String currentDate = rptNameFormat.format(systemParameterModel.getProcessDate());
		
		CasOpsRepSeqNrEntity casOpsRepSeqNrEntity = new CasOpsRepSeqNrEntity();
		casOpsRepSeqNrEntity = (CasOpsRepSeqNrEntity)adminBeanRemote.retrieveRepSeqNr(reportNr,crBankNo);

		if(casOpsRepSeqNrEntity != null)
		{
			lastSeqNoUsed = Integer.valueOf(casOpsRepSeqNrEntity.getLastSeqNo());
			lastSeqNoUsed = lastSeqNoUsed + 1;
		}
		else
			lastSeqNoUsed = 1;

		strSeqNo = String.format("%06d",lastSeqNoUsed);
		casOpsRepSeqNrEntity.setLastSeqNo(strSeqNo);
		adminBeanRemote.updateReportSeqNr(casOpsRepSeqNrEntity);

		String reportSeqNo = strSeqNo.substring(3,6);
		
		fileName = tempDir+bankId+"AC"+reportNr+currentDate+"."+reportSeqNo+".xlsx";
		xlsFileName = bankId+"AC"+reportNr+currentDate+"."+reportSeqNo+".xlsx";
		
		//XSSF used for MS Excel 2007(.xlsx) and later
		//HSSF used for MS Excel before 2007 (.xls) and earlier
		
		Workbook wb = new XSSFWorkbook(); //or new HSSFWorkbook();
		Sheet sheet = wb.createSheet("TT2_Trans_Data_1");

		sheet.setColumnWidth(0, 3500);
		sheet.setColumnWidth(1, 3000);
		sheet.setColumnWidth(2, 5000);
		sheet.setColumnWidth(3, 8000);
		sheet.setColumnWidth(4, 10000);
		
		Font normalFont = wb.createFont();
		normalFont.setFontName("Arial");
		normalFont.setFontHeightInPoints((short) 10);
		
		CellStyle titleCellStyle = wb.createCellStyle();
		titleCellStyle.setWrapText(true);
		titleCellStyle.setAlignment(HorizontalAlignment.CENTER_SELECTION);
		titleCellStyle.setVerticalAlignment(VerticalAlignment.TOP);
		titleCellStyle.setFont(normalFont);
		titleCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		titleCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		titleCellStyle.setBorderBottom(BorderStyle.THIN);
		titleCellStyle.setBorderTop(BorderStyle.THIN);
		titleCellStyle.setBorderRight(BorderStyle.THIN);
		titleCellStyle.setBorderLeft(BorderStyle.THIN);
		
		CellStyle normalCellStyle = wb.createCellStyle();
		normalCellStyle.setVerticalAlignment(VerticalAlignment.TOP);
		normalCellStyle.setFont(normalFont);
		
		//GENERATE FIRST ROW
		Row titleRow = sheet.createRow(0);
		
		Cell procDateCell = titleRow.createCell(0);
		procDateCell.setCellStyle(titleCellStyle);
		procDateCell.setCellValue("Process Date");
		
		Cell serviceCell = titleRow.createCell(1);
		serviceCell.setCellStyle(titleCellStyle);
		serviceCell.setCellValue("Service");

		Cell TxnTypeCell = titleRow.createCell(2);
		TxnTypeCell.setCellStyle(titleCellStyle);
		TxnTypeCell.setCellValue("Transaction Type");
		
		Cell mrtiCell = titleRow.createCell(3);
		mrtiCell.setCellStyle(titleCellStyle);
		mrtiCell.setCellValue("MRTI");
		
		Cell fileNameCell = titleRow.createCell(4);
		fileNameCell.setCellStyle(titleCellStyle);
		fileNameCell.setCellValue("File name");
		
		List<List<BatchTxnBillReportEntity>> splitTxnList = splitTxnList(crTxnBillEntityList);
		
		for(int txnCount = 0; txnCount < splitTxnList.size(); txnCount++)
		{
			Sheet newSheet = null;
			log.info("txnCount ==> "+txnCount);
			//Create a new Sheet
			if(txnCount != 0)
			{
				int sheetNr = txnCount+1;
				newSheet = wb.createSheet("TT2_Trans_Data_"+sheetNr);
				
				//GENERATE FIRST ROW
				Row newTitleRow = newSheet.createRow(0);
				
				newSheet.setColumnWidth(0, 3500);
				newSheet.setColumnWidth(1, 3000);
				newSheet.setColumnWidth(2, 5000);
				newSheet.setColumnWidth(3, 8000);
				newSheet.setColumnWidth(4, 10000);
				
				Cell newProcDateCell = newTitleRow.createCell(0);
				newProcDateCell.setCellStyle(titleCellStyle);
				newProcDateCell.setCellValue("Process Date");
				
				Cell newServiceCell = newTitleRow.createCell(1);
				newServiceCell.setCellStyle(titleCellStyle);
				newServiceCell.setCellValue("Service");

				Cell newTxnTypeCell = newTitleRow.createCell(2);
				newTxnTypeCell.setCellStyle(titleCellStyle);
				newTxnTypeCell.setCellValue("Transaction Type");
				
				Cell newMrtiCell = newTitleRow.createCell(3);
				newMrtiCell.setCellStyle(titleCellStyle);
				newMrtiCell.setCellValue("MRTI");
				
				Cell newFileNameCell = newTitleRow.createCell(4);
				newFileNameCell.setCellStyle(titleCellStyle);
				newFileNameCell.setCellValue("File name");
			}
			
			int rowCount = 0;
			List<BatchTxnBillReportEntity> tt2TxnList = splitTxnList.get(txnCount);
			
			for (BatchTxnBillReportEntity batchTxnBillEntity : tt2TxnList) {
				rowCount++;
				Row dataRow = null;
				if(txnCount == 0) {
					 dataRow = sheet.createRow(rowCount);
				}else
				{
					 dataRow = newSheet.createRow(rowCount);
				}
				
				
				Cell procDateCell_data = dataRow.createCell(0);
				procDateCell_data.setCellStyle(normalCellStyle);
				procDateCell_data.setCellValue(batchTxnBillEntity.getProcessDate());
				
				Cell serviceCell_data = dataRow.createCell(1);
				serviceCell_data.setCellStyle(normalCellStyle);
				serviceCell_data.setCellValue(batchTxnBillEntity.getServiceId());
				
				Cell txnTypeCell_data = dataRow.createCell(2);
				txnTypeCell_data.setCellStyle(normalCellStyle);
				txnTypeCell_data.setCellValue(batchTxnBillEntity.getTxnType());
				
				Cell mrtiCell_data = dataRow.createCell(3);
				mrtiCell_data.setCellStyle(normalCellStyle);
				mrtiCell_data.setCellValue(batchTxnBillEntity.getMrti());
				
				Cell fileNameCell_data = dataRow.createCell(4);
				fileNameCell_data.setCellStyle(normalCellStyle);
				fileNameCell_data.setCellValue(batchTxnBillEntity.getFileName());
			}
		}
		

//		for (BatchTxnBillReportEntity batchTxnBillEntity : batchMndtTxnBillList) {
//			rowCount++;
//			Row dataRow = sheet.createRow(rowCount);
//			
//			Cell procDateCell_data = dataRow.createCell(0);
//			procDateCell_data.setCellStyle(normalCellStyle);
//			procDateCell_data.setCellValue(batchTxnBillEntity.getProcessDate());
//			
//			Cell serviceCell_data = dataRow.createCell(1);
//			serviceCell_data.setCellStyle(normalCellStyle);
//			serviceCell_data.setCellValue(batchTxnBillEntity.getServiceId());
//			
//			Cell txnTypeCell_data = dataRow.createCell(2);
//			txnTypeCell_data.setCellStyle(normalCellStyle);
//			txnTypeCell_data.setCellValue(batchTxnBillEntity.getTxnType());
//			
//			Cell mrtiCell_data = dataRow.createCell(3);
//			mrtiCell_data.setCellStyle(normalCellStyle);
//			mrtiCell_data.setCellValue(batchTxnBillEntity.getMrti());
//			
//			Cell fileNameCell_data = dataRow.createCell(4);
//			fileNameCell_data.setCellStyle(normalCellStyle);
//			fileNameCell_data.setCellValue(batchTxnBillEntity.getFileName());
//		}
		
		// Write the output to a file
		FileOutputStream fileOut = new FileOutputStream(fileName);
		wb.write(fileOut);
		fileOut.close();
		
		// Copy the report to the Output reports folder
		try {
			copyFile(xlsFileName);
		} catch (IOException ioe) {
			log.error("Error on copying PBBIL05 report to temp " + ioe.getMessage());
			ioe.printStackTrace();
		} catch (Exception ex) {
			log.error("Error on copying PBBIL05 report to temp " + ex.getMessage());
			ex.printStackTrace();
		}

	}

	public void loadData()
	{
		long startTime = System.nanoTime();
		pbbil05Map = new TreeMap<String, List<BatchTxnBillReportEntity>>();
		batchMndtTxnBillList = new ArrayList<BatchTxnBillReportEntity>();

		//Retrieve File Data from DB
		batchMndtTxnBillList = (List<BatchTxnBillReportEntity>) reportBeanRemote.retrievePBBIL05BatchTxnsBillData(strFromDate, strToDate);
		
		List<BatchTxnBillReportEntity> dataPerCreditorBank;

		if(batchMndtTxnBillList != null && batchMndtTxnBillList.size() > 0) {
			for (CreditorBankModel creditorBankModel : creditorBankModelList) {
				dataPerCreditorBank = new ArrayList<BatchTxnBillReportEntity>();

				for (BatchTxnBillReportEntity batchTxnRptEntity : batchMndtTxnBillList) {
					if(batchTxnRptEntity.getOriginator().equalsIgnoreCase(creditorBankModel.getMemberNo())) {
						dataPerCreditorBank.add(batchTxnRptEntity);
					}
				}
				pbbil05Map.put(creditorBankModel.getMemberNo(), dataPerCreditorBank);
			}   
		}		
		log.info("pbbil05Map size===> "+pbbil05Map.size());
		long endTime = System.nanoTime();
		long duration = (endTime - startTime) / 500000;
		log.info("[PBBIL05 Data Retrieval time: "+DurationFormatUtils.formatDuration(duration, "HH:mm:ss.S")+" milliseconds]");
		
	}

	public List<List<BatchTxnBillReportEntity>> splitTxnList(List<BatchTxnBillReportEntity> crTxnBillEntityList) {
		
		int targetSize = 1000000;
		
		List<List<BatchTxnBillReportEntity>> partitionList = null;
		//Partition List
		partitionList = ListUtils.partition(crTxnBillEntityList, targetSize);
		log.info("Partition List Size: "+partitionList.size());
		
		return partitionList;
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
			if (adminBeanRemote == null) {
				adminBeanRemote = Util.getAdminBean();	
			}
		}

		private static void contextPropertyFileCheck() 
		{
			if (propertyUtilRemote == null) {
				propertyUtilRemote = Util.getPropertyUtil();
			}
		}

		private static void contextReportBeanCheck() {
			if (reportBeanRemote == null) {
				reportBeanRemote = Util.getReportBean();
			}
		}
}
