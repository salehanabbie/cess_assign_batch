package com.bsva.interfaces;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import javax.ejb.Remote;

@Remote
public interface AdminBeanRemote<EntryClassesModel, AdjustmentCategoryModel> {


  public List<?> viewAllLocalInstrumentCodes();

  public List<?> viewAcOpsSOTEOTByCriteria(String instId);

  public List<?> viewAllFileStatus();

  public List<?> viewAllMandatesMessagesPerOutGoingFiles();

  public List<?> viewAllMandatesCountPerIncomingFiles();

  public List<?> viewAllMessages();

  public List<?> viewAllOpsProcessControl();

  public List<?> viewAllOpsRefSeqNumber();

  public List<?> viewAllOpsCustomerParameters();

  public List<?> viewAllOpsAcSotEot();

  public boolean createLocalInstrCode(Object obj);

  public boolean createSotEot(Object obj);

  public boolean updateACOpsEOTSOT(Object obj);

  public List<?> retrieveACOpsSotEot();

  public List<?> retrieveMdtAcOpsDailyBilling();

  public List<?> retrieveNrOfRecords();

  public List<?> viewAllReasonCodes();

  public List<?> viewAllOpsServices();

  public List<?> retrieveOpsMndtCount();

  public boolean createReasonCodes(Object obj);

  public boolean createCustParameters(Object obj);

  public boolean updateSystemParametersForceClosure(Object obj, String action);

  //	public  Object retrieveInactiveEOTIND(String serviceId);
  public List<?> retrieveOpsCount(String instId, String serviceId);

  public List<?> viewAllSequenceTypes();

  public Object retrieveFileRecordsGroupedByID(String memberNo, String serviceId);

  public boolean createSequenceTypes(Object obj);

  public boolean createOpsRefSeqNr(Object obj);

  public boolean createOpsProcessControls(Object obj);

  public List<?> viewAllFrequencyCodes();

  public Object retrieveCisDownloadInd(Date processDate);

  public boolean createFrequencyCodes(Object obj);

  public List<?> viewAllCurrencyCodes();

  public boolean createCurrencyCodes(Object obj);

  public List<?> viewAllDebitValueTypes();

  public List<?> viewLocalInstrumentCodesByCriteria(String localInsCode);

  public List<?> viewReasonCodesByCriteria(String reasonCode);

  public List<?> viewFrequencyCodesByCriteria(String frequencyCode);

  public List<?> viewCurrencyCodesByCriteria(String currencyCode);

  public List<?> viewSequenceCodesByCriteria(String seqCode);

  //	public List<?> viewAllErrorCodes();
  public List<?> viewAllErrorCodes(boolean order);

  public boolean createErrorCodes(Object obj);

  public List<?> viewAllMember();

  public List<?> viewAllBranch();

  public List<?> retrieveAllMandates();

  public List<?> viewSystemParametersByCriteria(String sysName);

  public boolean createSystemParameters(Object obj);

  public boolean createSysParameters(Object obj);

  public List<?> viewErrorCodesByCriteria(String errorCode);

  public boolean createInstId(Object obj);

  public List<?> viewCustomerParametersByCriteria(String instId);

  public List<?> viewAllCustomerParameters();

  public Boolean createOpsFileRegModel(Object obj);

  public List<?> getopsFileRegByFileName(String fileName);

  public List<?> viewAllMandatesFiles();

  public List<?> viewAllDelDelivery();

  public List<?> viewDelDeliveryByCriteria(String fileName);

  public List<?> viewDebitValueTypeByCriteria(String dbtValueTypeCode);

  public boolean createdebValueTypeCode(Object obj);

  /* public boolean createServiceName(Object obj); */
  public boolean createOpsCustParameters(Object obj);

  public boolean createOpsServices(Object object);

  public boolean createAcOpsDailyBilling(Object obj);

  public List<?> viewAllSeverityCodes();

  public List<?> viewAllSystemsParameters();

  public boolean deleteOpsServices(Object obj);

  public boolean deleteOpsCustParam(Object obj);

  public boolean deleteOpsFileReg(Object obj);

  public void moveFileToAnotherFile();

  public void truncateTable(String tableName);

  public boolean updateSystemParameters(Object obj);

  public boolean updateOpsProcessControls(Object obj);

  public Object retrieveCustomerParameterPerMember();

  public boolean runStartofDayManually();

  public boolean runEndofDayManually(String userName, String forcecloseReason)
      throws ParseException;

  public List<?> retrieveOpsCustomerParameters();

  public Object retrieveActiveSysParameter();

  public Object retrieveCompanyParameters();

  public List<?> retrieveCustomerParameters();

  public List<?> retrieveServiceControl();

  public List<?> retrieveOpsCustParamTime();

  public List<?> retrieveOpsServicesTime();

  public List<?> retrieveOpsProcessControl();

  public List<?> retrieveOpsRefSeqNr();

  public List<?> viewReportNamesByCriteria(String reportNames);

  public boolean createReportNames(Object obj);

  public List<?> viewAllReportsName();

  public List<?> viewAllCompParam();

  public boolean deleteFileStatus(Object obj);

  public List<?> viewAllAuthTypes();

  public List<?> viewAuditTrackingCriteria(String tableName);

  public List<?> viewAuditTrackings();

  public List<?> viewAllAuditTables();

  public List<?> viewAuthTypeByCriteria(String authType);

  public boolean createAuthtype(Object obj);

  public List<?> viewAllRejectReasonCodes();

  public List<?> viewRejectReasonCodesByCriteria(String rejectReasonCode);

  public boolean createRejectReasonCodes(Object obj);

  public List<?> viewProcessStatusByCriteria(String status);

  public boolean createProcessStatus(Object obj);

  public List<?> viewAllProcessStatus();

  public List<?> retrieveActiveCisMember();

  public boolean createSysCisBank(Object obj);

  public List<?> retrieveSysCisBank();

  public List<?> retrieveSysCisBranch();

  boolean createSysCisBranch(Object obj);

  public void runCisDownload() throws ParseException;

  public List<?> retrieveActiveCisBranch(String memberNo);

  public List<?> viewAllServices();

  public Object retrieveWebActiveSysParameter();

  public List<?> viewOpsFileRegByDirection(String direction, String fileName);

  public List<?> viewAdjustmentCategoryByCriteria(String adjustmentCategory);

  public List<?> viewAllAdjustmentCategory();

  public boolean createAdjustmentCategory(Object obj);

  //public Object retrieveActiveSysParams();
  public List<?> viewAllOpsStatusHdrs();

  public List<?> viewActiveReports();

  public List<?> retrieveOutstandingResponses(String memberId, String onlineInd);

  public List<?> viewAllOpsStatusDetails();

  public List<?> retrieveOpsStatusDetails(BigDecimal statusHdrSeqNo);

  public void runSOT(String destMemberId, String serviceName);

  public void runEOT(String destMemberId, String serviceName);

  public Object retrieveOpsStatusHdrs(String orgnlMsgId);

  public boolean createAuditTracking(Object obj);

  public List<?> viewAllAuditTracking();

  public boolean createAccountType(Object obj);

  public List<?> viewAllAccountType();

  public List<?> viewAccountTypeByCriteria(String accountType);

  public boolean createSeverityCodes(Object obj);

  public List<?> viewSeverityCodesByCriteria(String severityCode);

  public List<?> viewAllSeverityCode();

  public List<?> viewAllStatusReasonCodes();

  public List<?> viewStatusReasonCodesByCriteria(String statusReasonCode);

  public boolean createStatusReasonCodes(Object obj);

  public List<?> retrieveSysCisBankMembers();

  boolean createRecordId1(Object obj);

  public List<?> viewAllRecordId();

  public List<?> viewRecordIdByCriteria(BigDecimal recordId);

  public List<?> retrieveAllActiveInd();

  public List<?> retrieveAllInactiveInd();

  public List<?> viewAllSchedulers();

  public List<?> retrieveSysCustomerParameters();

  public List<?> retrieveRelatedMember();

  public List<?> retrieveMndCisBranch(String memberNo);

  public List<?> retrieveSummaryTotals(String memberId, String onlineInd) throws ParseException;

  public List<?> retrieveInActiveCisDownload();

  public boolean createCisDownload(Object obj);

  public List<?> retrieveCountNrOfFilesStatus();

  public List<?> retrieveCountNrOfMsgsStatus();

  public List<?> retrieveSysMemberNo(String memberNo);

  public List<?> viewFileStatusFilter(String fileName);

  public List<?> retrieveOutstandingResponsesD(String memberId, String onlineInd);

  public List<?> retrieveAllEotSot(String destMemberId, String serviceName, String sotInInd,
                                   String eotInInd, String sotOutInd, String eotOutInd);

  public List<?> retrieveSotEotServiceId();

  public Object retrieveOpsService(String outgoingService);

  public void compareCISwithCustPram();

  public List<?> viewSysCntrlServiceByService(String service);

  public Object retrieveStartTime();

  public List<?> retrieveSlaTime();

  public List<?> retrieveOpsSlaTime();

  public List<?> viewAllOpsSlaTimes();

  public boolean createOpsSlaTimes(Object obj);

  public List<?> viewFileStatusSearch(String memberNo, String service);

  public String retrieveFeedbackMsg();

  public String retrieveCisFeedbackMsg();

  public List<?> retrieveSysCntrlScheduler();

  public List<?> retrieveOpsScheduler();

  public boolean createOpsScheduler(Object obj);

  public List<?> viewAllOpsSchedulers();

  public String retrieveEodFeedbackMsg();

  public List<?> retrieveAuditTracking(String userId, Date fromDate, Date toDate, String action);

  public Object retrieveCisStartTime();

  public List<?> viewEOTFile(String memberNo);

  public List<?> viewEotMessages();

  public List<?> retrieveStatusReport(String instId, String serviceId);

  public List<?> viewAmendmentCodes();

  public List<?> viewAmendmentCodesByCriteria(String amendmentCodes);

  public boolean createAmendmentCodes(Object obj);

  public List<?> retrieveAcitiveSot(String destMemberId, String serviceName, String eotOutInd);

  public boolean checkIamSession(String sessionKey);

  public boolean createSession(Object obj);

  public List<?> retrieveMndtRespPerBank(String memberId, String processStatus);

  public List<?> retrieveAmendmendReasonCode();

  public List<?> retrieveActiveCreditorBank();

  public List<?> retrieveMndtBillingTransactions(String txnType);

  public List<?> retrieveMndtDailyTransPerDebtor(String instId, String txnType);

  public List<?> retrieveMndtDailyTransPerCreditor(String instId, String txnType);

  public boolean createBillingCtrls(Object obj);

  public Object retrieveBillingCtrls(Date processDate);

  public boolean updateBillingCtrl(Object obj);

  public List<?> retrieveCisBankDebtorCreditorInfo();

  public List<?> searchByFileName(String fileName);

  public List<?> viewAllCisBankInfo();

  public Object viewCisBanksByCriteria(String memberNo);

  public Object retrieveCisDownloadDate();

  public List<?> viewAllCisBranchInfo();

  public Object viewCisBranchByCriteria(String branchNo);

  public boolean createCnfgOpsSlaTimes(Object obj);

  public List<?> viewSlaTimesByCriteria(String service);

  public List<?> viewSysCtrlSlaTimes();

  public List<?> viewSysCtrlSlaTimesByCriteria(String service);

  public boolean createCnfgSysCtrlSlaTimes(Object obj);

  public List<?> retrieveOpsFileRegbyDerictionCriteria(String direction);

  public Object retrieveEODTime();

  public Object retrieveCisSlaTime(String processType);

  public Object retrieveMdte002RejectReasonDataPerBank(String rejectReasonCode, String memberId,
                                                       String debtorBankId, String firstDate,
                                                       String lastDate);

  public Object retrieveSuspensionDataPerBank(String suspensionCode, String debtorBank,
                                              String memberId);

  //	public Object retrieveReasonCodeDataPerBank(String reasonCode,String memberId, String
  //	debtorBankId);
  public Object retrievePain012ReasonCodeDataPerBank(String reasonCode, String memberId,
                                                     String debtorBankId, String startDate,
                                                     String lastDate);

  public List<?> retrieveActiveDebtorBank();

  public Object retrieveRejectReasonCountPerBank(String memberId, String debtorBank);

  public Object retrieveSuspensionCountPerBank(String memberId, String debtorBank);

  public Object retrieveReasonCodeCountPerBank(String memberId, String debtorBank);

  public boolean saveSystemAuditInfo(Object obj);

  public Object retrieveCronIntervalValue(String cronInt);

  public List<?> retrieveRealTimeMndtBillingTxns(String txnType);

  public List<?> retrieveRealTimeMndtBillingTxnsByCreditor(String instId);

  public List<?> retrieveRealTimeMndtBillingTxnsByCreditorArcandOps(String instId, Date date);

  public List<?> retrieveRejectionCodesForRejectionsReport();

  public Object retrieveRealTimeNrOfAmendment(String amendReason, String memberId, String firstDate,
                                              String lastDate);

  public Object retrievePain012ReasonCodeDataPASA(String reasonCode, String memberId,
                                                  String startDate, String lastDate);

  public Object retrieveMdte002RejectReasonDataPASA(String rejectReasonCode, String memberId,
                                                    String firstDate, String lastDate);

  public List<?> viewOpsSlaServicesWithoutCisSodEod(String cis, String sod, String eod);

  public boolean saveOrUpdateExtendSlaTimes(Object obj);

  public Object retrieveOpsSlaTimes(String service);

  public Object retrieveOnlinePASARejectionSummaryPAIN012(String reasonCode, String crBank,
                                                          String firstDate, String lastDate);

  public Object retrieveOnlinePain012ReasonCodeDataPerBank(String reasonCode, String memberId,
                                                           String debtorBankId, String startDate,
                                                           String lastDate);

  public Object retrieveEndTime(String outService);

  public Object retrieveReportName(String reportNr);

  public void runDailyReports(String userName);

  public List<?> viewSystemStatusSearch(String memberNo, String service);

  public void runMonthlyReports(String userName);

  public List<?> retrieveOpsReportSeqNr();

  public boolean createOpsReportSeqNr(Object obj);

  public List<?> retrieveActiveReportNr();

  public Object retrieveRepSeqNr(String reportNr, String memberId);

  public boolean updateReportSeqNr(Object obj);

  public List<?> retrieveAllIncSotEot();

  public List<?> retrieveAllIncSotEotBySearch(String memberId, String serviceIdIn, String sotInInd,
                                              String eotInInd);

  public List<?> findByServiceIdNotNull(String namedQuery, String parameter, String value);

  public List<?> retrieveAllOutSotEotBySearch(String memberId, String serviceIdOut,
                                              String sotOutInd, String eotOutInd);

  public List<?> retrieveAllOutSotEot();

  public List<?> findByServiceIdOutNotNull(String namedQuery, String parameter, String value);

  public List<?> retrieveActiveErrorCodes(String activeInd);

  public Object retrieveErrCodeReportName(String reportNr);

  public void generatePasaBatchRejections(Date frontDate);

  public void generatePasaBatchAmendmentsReport(Date frontFromDate, Date frontToDate);

  public void generatePerBankBatchMandateRejections();

  public void generatePasaBatchOutstandingResponses(Date frontDate);

  public void generatePerBankOutstandingResponses(Date frontEndDate);

  public void generateBatchBillableTxnCreditor();

  public void generateBatchBillableTxnDebtor();

  public void generateBatchBillableTxnReport();

  public List<?> retrieveBranchesByDebtorCreditor(boolean creditorBranch, boolean debtorBranch);

  public void testArchiveProcess();

  public void generatePHIR02Report(Date fromDate, Date toDate);

  public void testPanelCode();

  public void generateBatchProdVolumesReport(Date frontFromDate, Date frontToDate);

  public List<?> findServicesByNamedQuery(String namedQuery, String parameter, String value);

  public Object generateRealTimeMonthlyRejections(String creditorBank, String startDate,
                                                  String lastDate);

  public List<?> retrieveActiveDebtorBankRemovingCreditorBank(String memberNo);

  public List<?> retrieveLastExtractTime();

  public boolean createOpLastExtractTimeEntiy(Object obj);

  public void generateDailyBatchProdVolumesReport(Date frontEndDate);

  public List<?> retrieveAllBanksRemovingCreditorBank(String memberNo);

  public List<?> retrieveAllCisBank();

  public void generate5DayOutstResp(Date frontEndDate);

  public List<?> retrieveSysctrlFileSizeLimit();

  public List<?> retrieveOpsFileSizeLimit();

  public boolean createOpsFileSizeLimit(Object obj);

  public List<?> retrieveAllOpsFileSizeLimit();

  public List<?> viewFileSizeLimitSearch(String memberId, String subService);

  public List<?> viewSysCtrlFileSizeLimit();

  public List<?> viewSysCtrlFileSizeLimitSearch(String memberId, String subService);

  public boolean createSysCtrlFileSizeLimit(Object obj);

  public boolean generateRtTxnsBill(Date fromDate, Date toDate);

  public boolean pushRtTxnsBillToBillowner(Date fromDate, Date toDate);

  public boolean generateTT1Pain009(Date fromDate, Date toDate);

  public boolean generateTT1MANIRData(Date fromDate, Date toDate);

  public boolean generateTT3ManirPain009Succ(Date fromDate, Date toDate);

  public boolean generateTT1MandrPain010Succ(Date fromDate, Date toDate);

  public boolean generateTT1ManamManirData(Date fromDate, Date toDate);

  public boolean generateTT3ManIrPain010Succ(Date fromDate, Date toDate);

  public boolean generateTT1ManIrPain011Succ(Date fromDate, Date toDate);

  public boolean generateBillingExport(Date fromDate, Date toDate, String srinpService);

  public boolean generateTT2TxnsBilling(Date fromDate, Date toDate);

  public boolean pushTT2TxnsBillToBillowner(Date fromDate, Date toDate);

  public boolean generateTT2InterchangeBill(Date fromDate, Date toDate);

  public boolean generateSRINPinterchangeBill(Date fromDate, Date toDate);

  public boolean generateMANRTinterchangeBill(Date fromDate, Date toDate);

  public void generateBatchTxnBillMonthlyReport(Date fromFrontDate, Date toFrontDate,
                                                boolean frontEndRun);

  public void generateBatchFileStatsReport(Date fromFrontDate);

  public void generateExceptionReport(Date frontDate);

  public List<?> retrievePBMD06RealTimeCreditorTransBilling(String creditorBank, Date reportDate);

}
