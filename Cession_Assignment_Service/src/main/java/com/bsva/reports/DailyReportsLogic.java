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
			pbmd04Report = false,
			bsca01Report = false,
			pbmd08PdfReport = false,
			pbmd08CsvReport = false;




	public DailyReportsLogic()
	{
		contextAdminBeanCheck();

		casSysctrlSysParamEntity = (CasSysctrlSysParamEntity) adminBeanRemote.retrieveActiveSysParameter();

		allReportsProduced = false;
		expiredReportProduced = false;
		pbmd04Report = false;
		bsca01Report = false;


	}

	public boolean generateDailyReports()
	{
		try 
		{
			log.info("***********Generating BSACA02 Report*****************");
			DailyBatchVolumeReport dailyBatchVolumeReport = new DailyBatchVolumeReport();
			dailyBatchVolumeReport.generateReport(false,null);
			bsca01Report = true;
			log.info("***********BSACA02 Report Completed*****************");
		} 
		catch (FileNotFoundException e) 
		{
			bsca01Report = false;
			log.error("<FE> Error on populating BSBSACA02 Report :"+e.getMessage());
			e.printStackTrace();
		} 
		catch (DocumentException e) 
		{
			bsca01Report = false;
			log.error("<DE> Error on populating BSBSACA02 Report :"+e.getMessage());
			e.printStackTrace();
		}
		catch (Exception e) 
		{
			bsca01Report = false;
			log.error("<EX> Error on populating BSBSACA02 Report :"+e.getMessage());
			e.printStackTrace();
		}

//
//		//PBMD04 - Daily Batch Billable Transaction Report Per Creditor
//		try
//		{
//			log.info("***********Generating PBMD04 Report*****************");
//			DailyBatchBillableTxnCreditorReport dailyBatchBillableTxnCreditorReport = new DailyBatchBillableTxnCreditorReport();
//			dailyBatchBillableTxnCreditorReport.generateMandateDailyTransCreditorReport();
//			pbmd04Report = true;
//			log.info("***********PBMD04 Report Completed*****************");
//		}
//		catch (Exception e)
//		{
//			pbmd04Report = false;
//			log.error("<EX> Error on populating PBMD04 Report :"+e.getMessage());
//			e.printStackTrace();
//		}


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
		log.info("pbmd04Report = "+pbmd04Report);
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
//		}else if(sunday && bsca01Report && pbmd04Report){
//			allReportsProduced = true;
//		}

		if(bsca01Report /*&& pbmd04Report && psmd08Report*/)
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
