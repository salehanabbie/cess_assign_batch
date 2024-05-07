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
			pbca01Report = false,
			bsca01Report = false,
			campb01PdfReport = false,
			campb01CsvReport = false;




	public DailyReportsLogic()
	{
		contextAdminBeanCheck();

		casSysctrlSysParamEntity = (CasSysctrlSysParamEntity) adminBeanRemote.retrieveActiveSysParameter();

		allReportsProduced = false;
		expiredReportProduced = false;
		pbca01Report = false;
		bsca01Report = false;


	}

	public boolean generateDailyReports()
	{
		//PBCA01 - Daily Batch Billable Transaction Report Per Creditor
		try
		{
			log.info("***********Generating PBCA01 Report*****************");
			DailyBatchBillableTxnCreditorReport dailyBatchBillableTxnCreditorReport = new DailyBatchBillableTxnCreditorReport();
			dailyBatchBillableTxnCreditorReport.generateMandateDailyTransCreditorReport();
			pbca01Report = true;
			log.info("***********PBCA01 Report Completed*****************");
		}
		catch (Exception e)
		{
			pbca01Report = false;
			log.error("<EX> Error on populating PBCA01 Report :"+e.getMessage());
			e.printStackTrace();
		}

		try 
		{
			log.info("***********Generating BSACA01 Report*****************");
			DailyBatchVolumeReport dailyBatchVolumeReport = new DailyBatchVolumeReport();
			dailyBatchVolumeReport.generateReport(false,null);
			bsca01Report = true;
			log.info("***********BSACA01 Report Completed*****************");
		} 
		catch (FileNotFoundException e) 
		{
			bsca01Report = false;
			log.error("<FE> Error on populating BSACA01 Report :"+e.getMessage());
			e.printStackTrace();
		} 
		catch (DocumentException e) 
		{
			bsca01Report = false;
			log.error("<DE> Error on populating BSACA01 Report :"+e.getMessage());
			e.printStackTrace();
		}
		catch (Exception e) 
		{
			bsca01Report = false;
			log.error("<EX> Error on populating BSACA01 Report :"+e.getMessage());
			e.printStackTrace();
		}


//		//PSMD08 - Daily File Stats Report
//		try {
//
//			log.info("***********Generating PSMD08 Report*****************");
//			//			2021/10/29 SalehaR NCH-252230 PSMD08 Report v2.0
//			//			BatchFileStatsReport batchFileStatsReport = new BatchFileStatsReport();
//			//			batchFileStatsReport.generateReport(null);
//
//			PSMD08_FileStatsBatchExcelReport psmd08FileStatsRpt = new PSMD08_FileStatsBatchExcelReport();
//			psmd08FileStatsRpt.generateReport(null);
//			psmd08Report = true;
//			log.info("***********PSMD08 Report Completed*****************");
//		}
//		catch (Exception e)
//		{
//			psmd08Report = false;
//			log.error("<EX> Error on populating PSMD08 Report :"+e.getMessage());
//			e.printStackTrace();
//		}



		log.info("allReportsProduced = "+allReportsProduced);
		log.info("bsca01Report = "+bsca01Report);
		log.info("pbca01Report = "+pbca01Report);
//		log.info("psmd08Report = "+psmd08Report);

//		Enable before go live on C/A
//		//Calc Day Of Week
//		Calendar calend = Calendar.getInstance();
//		calend.setTime(casSysctrlSysParamEntity.getProcessDate());
//		boolean saturday = calend.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY;
//		log.info("IS TODAY SATURDAY ? ==>"+saturday);
//
//		boolean sunday = calend.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
//		log.info("IS TODAY SUNDAY ? ==>"+sunday);
//
//		if(saturday && bsca01Report){
//			allReportsProduced = true;
//		}else if(sunday && bsca01Report && pbca01Report){
//			allReportsProduced = true;
//		}

		if(bsca01Report && pbca01Report)
		{
			allReportsProduced = true;
		}

		return allReportsProduced;
	}

	public boolean generateExpiredTxnsReport() {
		TreeMap<String, List<MandateResponseOutstandingPerBankEntityModel>> campb01Map = null;
		//CAMPB01 - Daily Outstanding Mandate 5Day Responses Per Bank - PDF
		try 
		{
			log.info("***********Generating CAMPB01 Report*****************");
			CAMPB01_ExpiredTransPDF CAMPB01ReportPDF = new CAMPB01_ExpiredTransPDF();
			CAMPB01ReportPDF.generateExpiredTxnRepPerBank(false,null);
			campb01PdfReport = true;
			log.info("***********CAMPB01 Report Completed*****************");
			campb01Map = CAMPB01ReportPDF.campb01Map;
		} 
		catch (Exception e) 
		{
			campb01PdfReport = false;
			log.error("<EX> Error on populating CAMPB01 Report :"+e.getMessage());
			e.printStackTrace();
		}
		//CAMPB01 - Daily Outstanding Mandate 5Day Responses Per Bank - CSV
		try 
		{
			log.info("***********Generating CAMPB01 CSV Report*****************");
			CAMPB01_ExpiredTransCSV CAMPB01ReportCSV = new CAMPB01_ExpiredTransCSV(campb01Map);
			CAMPB01ReportCSV.generateMandateResponseOutstandingPerBankCsv(false,null);
			campb01CsvReport = true;
			log.info("***********CAMPB01 CSV Report Completed*****************");
		} 
		catch (Exception e) 
		{
			campb01CsvReport = false;
			log.error("<EX> Error on populating CAMPB01 CSV Report :"+e.getMessage());
			e.printStackTrace();
		}

		if(campb01PdfReport && campb01CsvReport)
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
