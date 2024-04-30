package com.bsva.reports;

import java.io.FileNotFoundException;

import java.util.Calendar;
import java.util.List;
import java.util.TreeMap;
import org.apache.log4j.Logger;
import com.bsva.entities.MandateResponseOutstandingPerBankEntityModel;
import com.bsva.entities.CasSysctrlSysParamEntity;
import com.bsva.interfaces.AdminBeanRemote;
import com.bsva.utils.Util;
import com.itextpdf.text.DocumentException;

/**
 * @author SalehaR
 * @author SalehaR-2019/10/09 Remove Obsolete Reports(As guided by TDA)
 * @author SalehaR-2020/05/28 DailyReportsAfterSOD 
 */
public class DailyReportsLogic 
{
	public static Logger log = Logger.getLogger(DailyReportsLogic.class);

	public static AdminBeanRemote adminBeanRemote;
	CasSysctrlSysParamEntity casSysctrlSysParamEntity = new CasSysctrlSysParamEntity();

	boolean allReportsProduced = false,
			expiredReportProduced = false,
			mr018Report = false,
			mr019Report = false,
			pbmd04Report = false,
			pbmd05Report = false,
			//pbmd06Report = false, 
			pbmd01PdfReport = false,
			pbmd01CsvReport = false,
			psmd01Report = false,
			//psmd02Report = false,
			mr022Report = false,
			pbmd07PdfReport = false,
			pbmd07CSVReport = false,
			psmd07Report=false,
			pbmd08PdfReport = false,
			pbmd08CsvReport = false,
			//pbmd09PdfReport = false,
			//pbmd09CsvReport = false;
			mr026Report = false,
			psmd08Report=false,
			pbmd12Report = false;



	public DailyReportsLogic()
	{
		contextAdminBeanCheck();

		casSysctrlSysParamEntity = (CasSysctrlSysParamEntity) adminBeanRemote.retrieveActiveSysParameter();

		allReportsProduced = false;
		expiredReportProduced = false;
		mr018Report = false;
		mr019Report = false;
		pbmd04Report = false;
		pbmd05Report = false;
		//pbmd06Report = false; 
		pbmd01PdfReport = false;
		pbmd01CsvReport = false;
		psmd01Report = false;
		//psmd02Report = false;
		mr022Report =false;
		pbmd07PdfReport = false;
		pbmd07CSVReport = false;
		psmd07Report=false;
		pbmd08PdfReport = false;
		pbmd08CsvReport = false;
		//pbmd09PdfReport = false;
		//pbmd09CsvReport = false;
		psmd08Report=false;

	}

	public boolean generateDailyReports()
	{
		//MR018 - Daily Batch Billable Transaction Report
		try 
		{
			log.info("***********Generating MR018 Report*****************");
			DailyBatchBillableTxnReport dailyBatchBillableTxnReport = new DailyBatchBillableTxnReport();
			dailyBatchBillableTxnReport.generateReport();
			mr018Report = true;
			log.info("***********MR018 Report Completed*****************");
		} 
		catch (FileNotFoundException e) 
		{
			mr018Report = false;
			log.error("<FE> Error on populating MR018 Report :"+e.getMessage());
			e.printStackTrace();
		} 
		catch (DocumentException e) 
		{
			mr018Report = false;
			log.error("<DE> Error on populating MR018 Report :"+e.getMessage());
			e.printStackTrace();
			mr018Report = false;
		}
		catch (Exception e) 
		{
			mr018Report = false;
			log.error("<EX> Error on populating MR018 Report :"+e.getMessage());
			e.printStackTrace();
		}

		try 
		{
			log.info("***********Generating MR022 Report*****************");
			DailyBatchVolumeReport dailyBatchVolumeReport = new DailyBatchVolumeReport();
			dailyBatchVolumeReport.generateReport(false,null);
			mr022Report = true;
			log.info("***********MR022 Report Completed*****************");
		} 
		catch (FileNotFoundException e) 
		{
			mr022Report = false;
			log.error("<FE> Error on populating MR022 Report :"+e.getMessage());
			e.printStackTrace();
		} 
		catch (DocumentException e) 
		{
			mr022Report = false;
			log.error("<DE> Error on populating MR022 Report :"+e.getMessage());
			e.printStackTrace();
		}
		catch (Exception e) 
		{
			mr022Report = false;
			log.error("<EX> Error on populating MR022 Report :"+e.getMessage());
			e.printStackTrace();
		}


		//PBMD01 - Daily Outstanding Responses Per Bank - PDF 
		TreeMap<String, List<MandateResponseOutstandingPerBankEntityModel>> pbmd01Map = null;
		try 
		{
			log.info("***********Generating PBMD01 PDF Report*****************");
			PBMD01_DailyOutstandingResponsesReportPDF dailyOutstandingResponsesReportPDF = new PBMD01_DailyOutstandingResponsesReportPDF();
			dailyOutstandingResponsesReportPDF.generateMandateResponseOutstandingPerBank(null);
			pbmd01PdfReport = true;
			log.info("***********PBMD01 PDF Report Completed*****************");
			pbmd01Map = dailyOutstandingResponsesReportPDF.pbmd01Map;
		} 
		catch (Exception e) 
		{
			pbmd01PdfReport = false;
			log.error("<EX> Error on populating PBMD01 PDF Report :"+e.getMessage());
			e.printStackTrace();
		}

		//PBMD01 - Daily Outstanding Responses Per Bank - CSV
		try 
		{
			log.info("***********Generating PBMD01 CSV Report*****************");
			PBMD01_DailyOutstandingResponsesReportCSV dailyOutstandingResponsesReportCSV = new PBMD01_DailyOutstandingResponsesReportCSV(pbmd01Map);
			dailyOutstandingResponsesReportCSV.generateMandateResponseOutstandingPerBankCsv(null);
			pbmd01CsvReport = true;
			log.info("***********PBMD01 CSV Report Completed*****************");
		} 
		catch (Exception e) 
		{
			pbmd01CsvReport = false;
			log.error("<EX> Error on populating PBMD01 CSV Report :"+e.getMessage());
			e.printStackTrace();
		}

		//		TreeMap<String, List<MandateResponseOutstandingPerBankEntityModel>> pbmd09Map = null;
		//		//PBMD09 - Daily Outstanding Mandate TT1 delayed Responses Per Bank - PDF
		//		try 
		//		{
		//			log.info("***********Generating PBMD09 Report*****************");
		//			PBMD09_RealTimeOutstRespReportPDF PBMD09ReportPDF = new PBMD09_RealTimeOutstRespReportPDF();
		//			PBMD09ReportPDF.generateMandateResponseOutstandingPerBank();
		//			pbmd09PdfReport = true;
		//			log.info("***********PBMD09 Report Completed*****************");
		//			pbmd09Map = PBMD09ReportPDF.pbmd09Map;
		//		} 
		//		catch (Exception e) 
		//		{
		//			pbmd09PdfReport = false;
		//			log.error("<EX> Error on populating PBMD09 Report :"+e.getMessage());
		//			e.printStackTrace();
		//		}
		//		//PBMD09 - Daily Outstanding Mandate TT1 delayed Responses Per Bank - CSV
		//		try 
		//		{
		//			log.info("***********Generating PBMD09 CSV Report*****************");
		//			PBMD09_RealTimeOutstRespReportCSV PBMD09ReportCSV = new PBMD09_RealTimeOutstRespReportCSV(pbmd09Map);
		//			PBMD09ReportCSV.generateMandateResponseOutstandingPerBankCsv();
		//			pbmd09CsvReport = true;
		//			log.info("***********PBMD09 CSV Report Completed*****************");
		//		} 
		//		catch (Exception e) 
		//		{
		//			pbmd09CsvReport = false;
		//			log.error("<EX> Error on populating PBMD09 CSV Report :"+e.getMessage());
		//			e.printStackTrace();
		//		}

		//PSMD01 - Daily BATCH Outstanding Responses PASA
		try 
		{
			log.info("***********Generating PSMD01 Report*****************");
			PasaBatchOutstandingResponsesReport pasaBatchOutstandingResponsesReports = new PasaBatchOutstandingResponsesReport();
			pasaBatchOutstandingResponsesReports.generateReport(null);

			psmd01Report = true;
			log.info("***********PSMD01 Report Completed*****************");
		} 
		catch (Exception e) 
		{
			psmd01Report = false;
			log.error("<EX> Error on populating PSMD01 Report :"+e.getMessage());
			e.printStackTrace();
		}

		//PSMD02 - Daily Real Time Outstanding Responses PASA
		//		try 
		//		{
		//			log.info("***********Generating PSMD02 Report*****************");
		//			PasaRealTimeOutstandingResponsesReport pasaRealTimeOutstandingResponsesReport = new PasaRealTimeOutstandingResponsesReport();
		//			pasaRealTimeOutstandingResponsesReport.generateReport();
		//
		//			psmd02Report = true;
		//			log.info("***********PSMD02 Report Completed*****************");
		//		} 
		//		catch (Exception e) 
		//		{
		//			psmd02Report = false;
		//			log.error("<EX> Error on populating PSMD02 Report :"+e.getMessage());
		//			e.printStackTrace();
		//		} 
		//
		//PBMD04 - Daily Batch Billable Transaction Report Per Creditor
		try 
		{
			log.info("***********Generating PBMD04 Report*****************");
			DailyBatchBillableTxnCreditorReport dailyBatchBillableTxnCreditorReport = new DailyBatchBillableTxnCreditorReport();
			dailyBatchBillableTxnCreditorReport.generateMandateDailyTransCreditorReport();
			pbmd04Report = true;
			log.info("***********PBMD04 Report Completed*****************");
		} 
		catch (Exception e) 
		{
			pbmd04Report = false;
			log.error("<EX> Error on populating PBMD04 Report :"+e.getMessage());
			e.printStackTrace();
		}

		//PBMD05 - Daily Batch Billable Transaction Report Per Debtor
		try 
		{
			log.info("***********Generating PBMD05 Report*****************");
			DailyBatchBillableTxnDebtorReport dailyBatchBillableTxnDebtorReport = new DailyBatchBillableTxnDebtorReport();
			dailyBatchBillableTxnDebtorReport.generateMandateDailyTransDebtorReport();
			pbmd05Report = true;
			log.info("***********PBMD05 Report Completed*****************");
		} 
		catch (Exception e) 
		{
			pbmd05Report = false;
			log.error("<EX> Error on populating PBMD05 Report :"+e.getMessage());
			e.printStackTrace();
		}

		//PBMD06 - Daily Real Time Billable Transaction Report Per Creditor
		//		try 
		//		{
		//			log.info("***********Generating PBMD06 Report*****************");
		//			DailyRealTimeBillableTxnCreditorReport dailyRealTimeBillableTxnCreditorReport = new DailyRealTimeBillableTxnCreditorReport();
		//			dailyRealTimeBillableTxnCreditorReport.generateMandateDailyTransCreditorReport();
		//			pbmd06Report = true;
		//			log.info("***********PBMD06 Report Completed*****************");
		//		} 
		//		catch (Exception e) 
		//		{
		//			pbmd06Report = false;
		//			log.error("<EX> Error on populating PBMD06 Report :"+e.getMessage());
		//			e.printStackTrace();
		//		}


		//PSMD08 - Daily File Stats Report
		try {

			log.info("***********Generating PSMD08 Report*****************");
			//			2021/10/29 SalehaR NCH-252230 PSMD08 Report v2.0
			//			BatchFileStatsReport batchFileStatsReport = new BatchFileStatsReport();
			//			batchFileStatsReport.generateReport(null);

			PSMD08_FileStatsBatchExcelReport psmd08FileStatsRpt = new PSMD08_FileStatsBatchExcelReport();
			psmd08FileStatsRpt.generateReport(null);
			psmd08Report = true;
			log.info("***********PSMD08 Report Completed*****************");
		}
		catch (Exception e) 
		{
			psmd08Report = false;
			log.error("<EX> Error on populating PSMD08 Report :"+e.getMessage());
			e.printStackTrace();
		}
		//PBMD12 - Daily African Bank Exception 
		try {

			log.info("***********Generating PBMD12 Report*****************");
			Exception_Report_CSV  exceptionReport = new Exception_Report_CSV();
			exceptionReport.generateReport(null,false);
			pbmd12Report = true;
			log.info("***********PBMD12 Report Completed*****************");
		}
		catch (Exception e) 
		{
			pbmd12Report = false;
			log.error("<EX> Error on populating PBMD12 Report :"+e.getMessage());
			e.printStackTrace();
		}



		log.info("allReportsProduced = "+allReportsProduced);
		log.info("mr018Report = "+mr018Report);
		log.info("mr019Report ="+mr019Report);
		log.info("pbmd04Report = "+pbmd04Report);
		log.info("pbmd05Report = "+pbmd05Report);
		//log.info("pbmd06Report = "+pbmd06Report);
		log.info("pbmd01PdfReport = "+pbmd01PdfReport);
		log.info("pbmd01CsvReport = "+pbmd01CsvReport);
		log.info("psmd01Report = "+psmd01Report);
		//log.info("psmd02Report = "+psmd02Report);
		//log.info("pbmd09PdfReport = "+pbmd09PdfReport);
		//log.info("pbmd09CsvReport = "+pbmd09CsvReport);
		//		log.info("pbmd07PdfReport = "+pbmd07PdfReport);
		//		log.info("pbmd07CSVReport ="+pbmd07CSVReport);
		log.info("mr026Report = "+mr026Report);
		log.info("psmd08Report = "+psmd08Report);

		if(mr018Report && mr019Report && pbmd01PdfReport && pbmd01CsvReport && pbmd04Report && pbmd05Report /*&& pbmd06Report*/ && psmd01Report /*&& psmd02Report*/ && psmd07Report && mr026Report && psmd08Report/*&& pbmd09CsvReport && pbmd09PdfReport*/)
			//2019/10/09-Remove Obsolete Reports
			//		if(mr018Report && mr019Report && pbmd01PdfReport && pbmd01CsvReport && pbmd04Report && pbmd05Report && pbmd06Report && psmd01Report && psmd02Report && pbmd07PdfReport && pbmd07CSVReport && psmd07Report)  
			//			&& mr024Report)
		{
			allReportsProduced = true;
		}

		return allReportsProduced;
	}

	public boolean generateExpiredTxnsReport() {
		TreeMap<String, List<MandateResponseOutstandingPerBankEntityModel>> pbmd08Map = null;
		//PBMD08 - Daily Outstanding Mandate 5Day Responses Per Bank - PDF
		try 
		{
			log.info("***********Generating PBMD08 Report*****************");
			PBMD08_DailyFiveDayOutstRespPDF PBMD08ReportPDF = new PBMD08_DailyFiveDayOutstRespPDF();
			PBMD08ReportPDF.generate5DayOutstRespPerBank(false,null);
			pbmd08PdfReport = true;
			log.info("***********PBMD08 Report Completed*****************");
			pbmd08Map = PBMD08ReportPDF.pbmd08Map;
		} 
		catch (Exception e) 
		{
			pbmd08PdfReport = false;
			log.error("<EX> Error on populating PBMD08 Report :"+e.getMessage());
			e.printStackTrace();
		}
		//PBMD08 - Daily Outstanding Mandate 5Day Responses Per Bank - CSV
		try 
		{
			log.info("***********Generating PBMD08 CSV Report*****************");
			PBMD08_DailyFiveDayOutstRespCSV PBMD08ReportCSV = new PBMD08_DailyFiveDayOutstRespCSV(pbmd08Map);
			PBMD08ReportCSV.generateMandateResponseOutstandingPerBankCsv(false,null);
			pbmd08CsvReport = true;
			log.info("***********PBMD08 CSV Report Completed*****************");
		} 
		catch (Exception e) 
		{
			pbmd08CsvReport = false;
			log.error("<EX> Error on populating PBMD08 CSV Report :"+e.getMessage());
			e.printStackTrace();
		}

		if(pbmd08PdfReport && pbmd08CsvReport)
		{
			expiredReportProduced = true;
		}

		return expiredReportProduced;
	}

	public void generateDailyReportsAfterSOD() {

		log.debug("***********RUNNING DAILY REPORTS*****************");
		log.debug("***********DAILY REPORTS COMPLETED*****************");
	}

	public boolean checkSunday() {

		// Calc Day Of Wee
		Calendar calend = Calendar.getInstance();
		calend.setTime(casSysctrlSysParamEntity.getProcessDate());
		boolean sunday = calend.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
		log.info("IS TODAY SUNDAY ? ==>" + sunday);

		return sunday;
	}

	private static void contextAdminBeanCheck() {
		if (adminBeanRemote == null) {
			adminBeanRemote = Util.getAdminBean();
		}
	}

}
