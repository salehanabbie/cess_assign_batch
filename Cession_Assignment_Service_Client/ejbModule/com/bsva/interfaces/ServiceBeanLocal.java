package com.bsva.interfaces;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

@Local
public interface ServiceBeanLocal {
  public void startFileListener();

  public List<?> retrieveOutGoingNrOfFiles();

  public List<?> retrieveIncomingNrOfFiles();

  public List<?> retrieveAllMandatesCount();

  public boolean createCasOpsGrpHdr(Object obj);

  public List<?> getSysctrlCompParam();

  public List<?> getSysctrlCustParam();

  public void updateSysctrlCustParam(Object obj);

  public List<?> getOpsSysctrlCumParam();

  public boolean updateOpsSysctrlCumPara(Object obj);

  public List<?> viewSchedulerModel();

  public List<?> retrieveOpsFileRegByCriteria(String namedQuery, String property, String value);

  public Object retrieveErrorCode(String errorCode);

  public List<?> getSysCtrlSysParam();

  public Object retrieveCisMemberByCriteria(String memberNo);

  public List<?> retrieveCreditorSummary();

  public List<?> retrieveDebtorSummary();

  public List<?> retrieveCaptureLocalInstCodes();

  public List<?> retrieveCisBranchByCriteria(String memberNo);

  public List<?> retrieveTotalBalanceBroughtForward();

  public List<?> retrieveBalanceCarriedForward();

  public List<?> sumOutGoingMessagesPerServiceID();

  public List<?> sumIncomingMessagesPerServiceId();

  public Object retrieveCompanyParameters();

  public Object retrieveOpsCustomerParameters(String bicCode);

  public List<?> retrieveCisMemberTableByCriteria(String memberNo);

  public List<?> retrieveMandatesSearchCriteria(String mandateId, String debtorName,
                                                String debtorBank, String creditorName,
                                                String creditorBank);

  public List<?> retrieveReports(String bicCode);

  public List<?> retrieveActiveCustomerParameters();

  public Object retrieveErrorCodeDesc(String errorCode);

  public List<?> retrieveStatusHdrs();

  public boolean updateOpsStatusHdrs(Object obj);

  public boolean createProcessStatus(Object obj);

  public List<?> getStatus();

  public Object retrieveSOTEOTCntrl(String memberNo, String serviceId);

  public boolean updateSOTEOTCntrl(Object obj);

  public Object retrieveStatusErrors(BigDecimal hdrSeqNo, String errorType);

  public Object retrieveDistinctTxnErrors(BigDecimal hdrSeqNo, String errorType);

  public Object retrieveTransactionErrors(String txnId, BigDecimal hdrSeqNo);

  //	public List<?> retrieveStatusHdrsByStatus(String status);
  public Object retrievePacs002Count(String messageId);

  public boolean createLoadEOTandSOT(Object obj);

  public List<?> retrieveStatusDetailsByStatus(String instId);

  public Object retrieveStatusHdrsBySeqNo(BigDecimal seqNo);

  public Object retrieveDistinctStatusDetails(String instId);

  public List<?> retrieveStatusDetailsByCriteria(String txnId);

  public boolean updateOpsStatusDetails(Object obj);

  public List<?> retrieveAllSysCisBank();

  public List<?> retrieveIncomingMandatesCount();

  public List<?> retrieveOutgoingMandatesCount();

  public List<?> retriveExtractReportData();

  public List<?> retrieveRejectedReportData();

  public List<?> retrieveAcceptedCountData();

  public List<?> retrieveRejectedCountData();

  public List<?> retrieveExtractedCountData();

  public List<?> retrieveMndtFileStatus();

  public boolean createBillingRecords(Object obj);

  public List<?> retrieveAllEotFiles();

  public Object retrieveDistinctConfDetails(String instId, String extService);

  public List<?> retrieveConfDetails(String txnId, String extService, String orgnlMsgType);

  public Object retrieveConfHdrsBySeqNo(BigDecimal seqNo);

  public boolean updateConfDetails(Object obj);

  public boolean updateConfHdrs(Object obj);

  public List<?> retrieveConfStatusDetails(BigDecimal sysSeqNr);

  public List<?> retrieveConfDetailsByProcessStatus();

  public List<?> retrieveStatusHdrsByProcessStatus();

  //Clean Up Archive Tables

  public List<?> retrieveArcConfDtlsByArchiveDate(Date archiveDate);

  public List<?> retrieveArcStatHdrsByArchiveDate(Date archiveDate);

  public List<?> retrieveArcStatDtlsByArchiveDate(Date archiveDate);

  public boolean generateDuplicateError(String msgId, String mrti, String debtorBrNo,
                                        String crAbbShrtName, String mndtRefNo);

  public List<?> retrieveStatusHdrsByStatus(String status, String pacs002Service);

  public List<?> retrieveMndtCount();

  public boolean saveOpsDailyBilling(Object obj);

  public List<?> retrieveDailyBillingInterCngInfo();

  public Object retrieveBillingCtrls(String processName);

  public List<?> retrieveInterchangeBillingData(String currentSeqNo, String lastSeqNo);

  public boolean saveBillingCntrl(Object obj);

  public Object retrieveAmend16Data(String memeberNo, String amendmetReasonCode, String firstDate,
                                    String lastDate);

  public Object retrieveReasonCodeTotal(String amendmetReasonCode, String firstDate,
                                        String lastDate);

  public Object retrieveRejectReasonData(String rejectReasonCode, String memberNo);

  public Object retrieveSuspensionData(String suspensionCode, String memberNo);

  public Object retrieveReasonCodeData(String reasonCode, String memberNo);

  public Object retrieveRejectReasonCount(String memberNo);

  public Object retrieveSuspensionCount(String memberNo);

  public Object retrieveReasonCodeCount(String memberNo);

  public Object retrieverRespOutstandForOneDay(String debtorMember, String creditorMember,
                                               String serviceId);

  public Object retrieverRespOutstandForTwoDays(String debtorMember, String creditorMember,
                                                String serviceId);

  public boolean eodCheckIfAllFilesAreExtracted(Date systemDate, String mdtLoadType);

  public List<?> calculateCountsReportInfo();

  public List<?> retrieveDailyBillingForArchive();

  public boolean updateDailyBillingInd(Object obj);

  public Object retrieveRejectReasonDataPerBankCount(String memberId, String memberNo);

  public Object retrieveReasonCodeDataPerBankCount(String memberId, String memberNo);

  public Object retrieveDailyBillingByTxnId(String txnId, String service);

  public Object retrieverRespOutstandForOneDay(String serviceId);

  public Object retrieverRespOutstandForTwoDays(String serviceId);

  public Object retrieverRespOutstandMANINTotal();

  public Object retrieverRespOutstandMANAMTotal();

  public Object retrieverRespOutstandMANCNTotal();

  public boolean checkIfAllFilesLoaded(Date processDate, String service);

  public List<?> retrievePasaHIRMandateInfo(String fromDate, String toDate, String service,
                                            boolean expiredTxns);

  public List<?> retrieveNotRespondedMandates(String fromDate, String toDate, String service);

  public List<?> retrieveAchValidationErrors(String fromDate, String toDate, String serviceNmId);

  public List<?> retrieveDebtorValidationErrors(String fromDate, String toDate, String service,
                                                String serviceNmId);

  public List<?> retrieveAuthStatus(String mrti, String service);

  public List<?> retrievePHIRMndtSuspInfo(String fromDate, String toDate, boolean expiredTxns);

  public List<?> retrieveSuspRejections(String txnId);

  public List<?> retrieveNotRespondedMndtSusp(String fromDate, String toDate);

  public List<?> retrieveRealTimePHIRInitInfo(String fromDate, String toDate, String service);

  public List<?> retrieveRealTimePHIRAmendmentInfo(String fromDate, String toDate);

  public List<?> retrieveRealTimePHIRCancellationInfo(String fromDate, String toDate);

  public List<?> retrieveMndtCountByCreditorBanks(String fromDate, String toDate, String serviceId);

  public List<?> retrieveMndtCountByDebtorBanks(String fromDate, String toDate, String serviceId);

  public List<?> retrieveOnlineCountByCreditor(String strFromDate, String strToDate,
                                               String serviceId);

  public List<?> retrieveOnlineCountByDebtor(String strFromDate, String strToDate,
                                             String serviceId);

  public List<?> retrieveST012MndtCountByCreditorBanks(String strFromDate, String strToDate,
                                                       String serviceId);

  public List<?> retrieveOpsFileReg();

  public List<?> retrieveStatusReportsSentToCreditorsBanks(String fromDate, String toDate,
                                                           String reportNr);

  public List<?> retrieveStatusReportsSentToDebtorBanks(String fromDate, String toDate,
                                                        String reportNr);

  public List<?> retrieveRTimeCountsManinTT3(String fromDate, String toDate, String reportNr);

  public List<?> retrieveRTimeCountsExtractManinTT3(String fromDate, String toDate,
                                                    String reportNr);

  public List<?> retrieveRTimeCountsManinTT1(String fromDate, String toDate, String reportNr);

  public List<?> retrieveRTimeCountsExtractManinTT1(String fromDate, String toDate,
                                                    String reportNr);

  public List<?> retrieveRTimeCountsManam(String fromDate, String toDate, String reportNr);

  public List<?> retrieveRTimeCountsExtractManam(String fromDate, String toDate, String reportNr);

  public List<?> retrieveRTimeCountsMancn(String fromDate, String toDate, String reportNr);

  public List<?> retrieveRTimeCountsExtractMancn(String fromDate, String toDate, String reportNr);

  public List<?> retrieveRTimeCountsST012(String fromDate, String toDate, String reportNr);

  public List<?> retrieveRTimeCountsManir(String fromDate, String toDate, String reportNr);

  public List<?> retrieveRTimeCountsExtractManir(String fromDate, String toDate, String reportNr);

  public List<?> retrieveRTimeCountsStman(String fromDate, String toDate, String reportNr);

  public List<?> retrieveRTimeCountsExtractStman(String fromDate, String toDate, String reportNr);

  public List<?> retrieveRTimeCountsStmdf(String fromDate, String toDate, String reportNr);

  public List<?> retrieveRTimeCountsExtractStmdf(String fromDate, String toDate, String reportNr);

  public List<?> retrieveRTimeCountsMandr(String fromDate, String toDate, String reportNr);

  public List<?> retrieveRTimeCountsExtractMandr(String fromDate, String toDate, String reportNr);

  public List<?> retrieveMigrationCountSubmittedByCreditorBanks(String strFromDate,
                                                                String strToDate);

  public List<?> retrieveMigrationCountExtractedToDebtorBanks(String strFromDate, String strToDate);

  public List<?> retrieveMigrationCountST101SubmittedByDebtorBanks(String strFromDate,
                                                                   String strToDate,
                                                                   String service);

  public List<?> retrieveMigrationCountMANACSubmittedByDebtorBanks(String strFromDate,
                                                                   String strToDate,
                                                                   String service);

  public List<?> retrieveMigratonCountST103ExtractedToCreditorBanks(String strFromDate,
                                                                    String strToDate,
                                                                    String service);

  public List<?> retrieveMigratonCountMANOCExtractedToCreditorBanks(String strFromDate,
                                                                    String strToDate,
                                                                    String service);

  public List<?> retrieveRTimeCountsExtractST012(String strFromDate, String strToDate,
                                                 String reportNr);

  //ARCHIVE NEW METHODS
  public boolean archiveMandatesBySQL(String archiveType, String expiredDate, String archDate);

  public boolean deleteMatchedMandates(String archiveType, String expiredDate);

  public boolean archiveDailyBillingBySQL(String archDate);

  public boolean deleteDailyBilling();

  public boolean archiveConfHdrs(String archDate);

  public boolean archiveConfDtls(String archDate);

  public boolean archiveOpsErrorCodesRpt(String archDate);

  public boolean deleteOpsErrorCodesRpt();

  public boolean archiveMarkOffBySQL();

  public boolean deleteMarkOff();

  public boolean archiveSuspensionsBySQL(String archiveType, String expiredDate, String archDate);

  public boolean deleteSuspensions(String archiveType, String expiredDate);

  public boolean archiveStatusReportsBySQL(String archDate);

  public boolean deleteStatusReports();

  public boolean archiveMandateCountsBySQL();

  public boolean deleteMandateCounts();

  public boolean archiveFileRegBySQL(String archDate);

  public boolean deleteFileReg();

  public boolean archiveMdteRequestsBySQL(String archDate);

  public boolean deleteMdteRequests();

  public boolean archiveMdteResponsesBySQL(String archDate);

  public boolean deleteMdteResponses();

  public boolean archiveMdteRespMsgsBySQL(String archDate);

  public boolean deleteMdteRespMsg();

  public boolean archiveLeftOverTxnsBySQL(String expiredDate, String archDate);

  public boolean deleteLeftOverTxnsBySQL(String expiredDate);

  public boolean cleanUpArchivedTxnsBySQL(String expiredDate);

  public boolean eodCheckIfStReportExtracted(Date systemDate, String service, String memberId);

  public boolean eodCheckSt203SroutExtracted(String service, String memberId);

  public boolean saveAcOpsTxnBilling(Object obj);

  public List<?> retrievetxnsBilingToExport(String nonActInd);

  public boolean createOpsTxnBillingRecords(Object obj);

  public boolean updateOpsTxnBillingInd(Object obj);

  public boolean archiveOpsTxnBillingBySQL(String archDate);

  public boolean deleteOpsTxnBillingBySQL();

  public Object retrievePastTimeForConfData();

  public Boolean createOrUpdatePastTimeForConfData(Object obj);

  public boolean saveOpsTxnBillInfo(List<?> opsTxnsBillReportList);

  public boolean archiveOpsTxnBillRptDataBySQL(String archDate);

  public boolean deleteOpsTxnBillRptDataBySQL();

}
