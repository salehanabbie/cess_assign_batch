package com.bsva.interfaces;

import java.util.Date;
import java.util.List;
import java.util.TreeMap;
import javax.ejb.Local;

/**
 * @author SalehaR
 *
 */
@Local
public interface ReportBeanLocal {
	public List<?> retrieveMndtRespPerBank(String memberId, Date procDate);
	public Object retrieverRespOutstandForOneDay(String debtorMember,String creditorMember,String serviceId);
	public Object retrieverRespOutstandForTwoDays(String debtorMember,String creditorMember,String serviceId);
	public Object retrievePain012ReasonCodeDataPerBank(String reasonCode,String creditorBank, String debtorBank, String startDate, String lastDate);
	public Object retrievePain012ReasonCodeDataPASA(String reasonCode, String creditorBank, String startDate, String lastDate);
	public Object retrieveAmendReportData(String creditorBank,String amendmetReasonCode,String firstDate, String lastDate);
	public Object retrieveReasonCodeTotal(String amendmetReasonCode,String firstDate, String lastDate);
	public List<?> retrievePasaHIRMandateInfo(String fromDate, String toDate, String service, boolean expiredTxns);
	public List<?> retrieveAuthStatus(String mrti,String service);
	public Object retrievePain012FromArchive(String serviceId, String mrti, String accepted);
	public List<?> retrieveDebtorValidationErrors(String fromDate, String toDate,String service, String serviceNmId);
	public Object retrieverRespOutstandForOneDay(String serviceId);
	public Object retrieverRespOutstandForTwoDays(String serviceId);

	//=====OPTIMISED QUERIES=======//
	public List<?> retrievePBMD01Data(String procDate);
	public List<?> retrievePBMD09Data(String procDate,String fromProcDate,String time);
	public List<?> retrievePBMD08Data(String procDate);
	public List<?> retrieverRespOutstand(String processingDate);
	public List<?> retrieveAllBatchRejectedTnxs(String date);
	public List<?> retrieveBatchAmendmentTrans(String firstDate, String lastDate);
	public List<?> retrieveBatchMonthlyTransFromCreditor(String fromDate, String toDate);
	public List<?> batchMonthlyTransExtractedToDebtorBank(String fromDate, String toDate);
	public List<?> retrieveBatchMonthlyTransFromDebtor(String fromDate, String toDate);
	public List<?> batchMonthlyTransExtractedToCreditorBank(String fromDate, String toDate);
	public List<?> retrieveBatchMonthlyStReportCreditor(String fromDate, String toDate);
	public List<?> retrieveBatchMonthlyStReportDebtor(String fromDate, String toDate);

	public List<?> retrieveBatchDailyTransFromCreditor(String currentDate);
	public List<?> batchDailyTransExtractedToDebtorBank(String currentDate);
	public List<?> retrieveBatchDailyTransFromDebtor(String currentDate);
	public List<?> batchDailyTransExtractedToCreditorBank(String currentDate);
	public List<?> retrieveBatchDailyStReportCreditor(String currentDate);
	public List<?> retrieveBatchDailyStReportDebtor(String currentDate);
	public List<?> retrievePHIRAmendmentMandateInfo(String fromDate, String toDate);
	public List<?> retrievePBBIL05BatchTxnsBillData(String fromDate, String toDate);
	public List<?> retrieveBatchFileStats(String processDate);
	public List<?> retrieveRjctTransactios(String frontDate,boolean frontEnd);

}
