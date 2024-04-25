package com.bsva.controller;

import com.bsva.commons.model.AccountTypeModel;
import com.bsva.commons.model.AdjustmentCategoryModel;
import com.bsva.commons.model.AmendmentCodesModel;
import com.bsva.commons.model.IamSessionModel;
import com.bsva.commons.model.SeverityCodesModel;
import com.bsva.commons.model.StatusReasonCodesModel;
import com.bsva.commons.model.SystemParameterModel;
import com.bsva.interfaces.AdminBeanRemote;
import com.bsva.interfaces.FileWatcherRemote;
import com.bsva.interfaces.PropertyUtilRemote;
import com.bsva.interfaces.QuartzSchedulerBeanRemote;
import com.bsva.interfaces.ReportBeanRemote;
import com.bsva.interfaces.ServiceBeanRemote;
import com.bsva.interfaces.TimerBeanRemote;
import com.bsva.interfaces.ValidationBeanRemote;
import com.bsva.utils.Util;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;

public class Controller implements Serializable {
  private static final long serialVersionUID = 1L;
  private static ServiceBeanRemote beanRemote = null;
  private static AdminBeanRemote adminBeanRemote = null;
  private static QuartzSchedulerBeanRemote quartzSchedulerRemote = null;
  private static FileWatcherRemote fileWatcherRemote = null;
  private static TimerBeanRemote timerBeanRemote = null;
  private static ValidationBeanRemote validationBeanRemote = null;
  private static PropertyUtilRemote propertyUtilRemote = null;
  private static ReportBeanRemote reportBeanRemote = null;
  private static String webProcess = "WEB";
  private static Logger log = Logger.getLogger(Controller.class);
  public static String feedbackMsg, eodFeedbackMsg, cisFeedbackMsg;

  public static List<?> viewAllLocalInstrumentCodes() {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      log.debug("adminBeanRemote after check : " + adminBeanRemote);
      list = adminBeanRemote.viewAllLocalInstrumentCodes();
    } catch (Exception e) {
      log.error("Error retrievingInstrumentCodes: " + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public List<?> retrieveAllMandatesCount() {
    contextCheck();
    List<?> list = null;
    try {
      list = beanRemote.retrieveAllMandatesCount();
      log.info("The list in the controller has this information " + list);
    } catch (Exception e) {
      e.printStackTrace();
      e.getMessage();
    }
    return list;
  }

  public List<?> viewAllSchedulers() {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.viewAllSchedulers();
    } catch (Exception e) {
      e.printStackTrace();
      e.getMessage();
    }
    return list;
  }

  public List<?> retrieveAllOpsServices() {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.viewAllOpsServices();
    } catch (Exception e) {
      e.printStackTrace();
      e.getMessage();
    }
    return list;
  }

  public List<?> retrieveTotalBalanceBroughtForward() {
    contextCheck();
    List<?> list = null;
    try {
      list = beanRemote.retrieveTotalBalanceBroughtForward();
      log.info("The list in the controller has this information " + list);
    } catch (Exception e) {
      e.printStackTrace();
      e.getMessage();
    }
    return list;
  }

  public static List<?> viewAllAuthTypes() {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      log.debug("adminBeanRemote after check : " + adminBeanRemote);
      list = adminBeanRemote.viewAllAuthTypes();
    } catch (Exception e) {
      log.error("Error retrievingAuthTypes: " + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public static List<?> viewAllSystemParameters() {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.viewAllSystemsParameters();
      if (list.size() > 0) {

      }
    } catch (Exception e) {
      log.error("Error retrievingSystemParameter: " + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public static List<?> retrieveNrOfRecords() {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.retrieveNrOfRecords();
      //			if (list.size() > 0)
      //			{
      //				System.out.print("Retrieve number of records" + list.toString());
      //			}
      //			if (list.size() > 0) {
      //				System.out
      //				.print("Retrieve number of records" + list.toString());
      //			}
    } catch (Exception e) {
      log.error("Error retrieving number of records ");
    }
    return list;
  }

  public static List<?> viewAllBranchNos() {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.viewAllBranch();
      if (list.size() > 0) {
        log.debug("viewAllBranch --> " + list.toString());
      }
    } catch (Exception e) {
      log.error("Error retrievingviewAllBranch: " + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public boolean createLocalInstrCode(Object obj) {
    contextAdminBeanCheck();
    try {
      adminBeanRemote.createLocalInstrCode(obj);
      return true;
    } catch (Exception e) {
      log.error("Error retrievingInstrumentCodes: " + e.getMessage());
      e.printStackTrace();
      return false;
    }
  }

  public boolean createAuthType(Object obj) {
    contextAdminBeanCheck();
    try {
      adminBeanRemote.createAuthtype(obj);
      return true;
    } catch (Exception e) {
      log.error("Error creating Auth Type: " + e.getMessage());
      e.printStackTrace();
      return false;
    }
  }

  public boolean UpdateForceClosureIndicator(Object obj, String action) {
    contextAdminBeanCheck();
    try {
      log.info("in the controller *************************************");
      adminBeanRemote.updateSystemParametersForceClosure(obj, action);
      return true;
    } catch (Exception e) {
      log.error("UpdateForceClosureIndicator: " + e.getMessage());
      e.printStackTrace();
      return false;
    }
  }

  public boolean createSystemParameters(Object obj) {
    contextAdminBeanCheck();
    try {
      adminBeanRemote.createSystemParameters(obj);
      return true;
    } catch (Exception e) {
      log.error("Error retrieving system parameters: " + e.getMessage());
      e.printStackTrace();
      return false;
    }
  }

  public boolean createReasonCodes(Object obj) {
    contextAdminBeanCheck();
    try {
      adminBeanRemote.createReasonCodes(obj);
      return true;
    } catch (Exception e) {
      log.error("Error retrievingReasonCodes:" + e.getMessage());
      e.printStackTrace();
      return false;
    }
  }

  public static List<?> viewAllReasonCodes() {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.viewAllReasonCodes();
    } catch (Exception e) {
      log.error("Error retrievingReasonCodes: " + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public static List<?> viewAuthByCriteria(String authType) {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.viewAuthTypeByCriteria(authType);
    } catch (Exception e) {
      log.error("Error viewAuthTypeByCriteria: " + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public static List<?> viewAllCurrencyCodes() {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.viewAllCurrencyCodes();
    } catch (Exception e) {
      log.error("Error retrievingCurrencyCodes: " + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public boolean createCurrencyCode(Object obj) {
    contextAdminBeanCheck();
    try {
      adminBeanRemote.createCurrencyCodes(obj);
      return true;
    } catch (Exception e) {
      log.error("Error retrievingCurrencyCodes: " + e.getMessage());
      e.printStackTrace();
      return false;
    }
  }

  public static List<?> viewAllSequenceTypes() {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.viewAllSequenceTypes();
    } catch (Exception e) {
      log.error("Error viewAllSequenceTypes: " + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public boolean createSequenceTypes(Object obj) {
    contextAdminBeanCheck();
    try {
      adminBeanRemote.createSequenceTypes(obj);
      return true;
    } catch (Exception e) {
      log.error("Error retrievingSequenceTypes:" + e.getMessage());
      e.printStackTrace();
      return false;
    }
  }

  public static List<?> viewAllFrequencyCodes() {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.viewAllFrequencyCodes();
    } catch (Exception e) {
      log.error("Error retrievingFrequencyCodes: " + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public static List<?> viewAllFileStatus() {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.viewAllFileStatus();
    } catch (Exception e) {
      log.error("Error retrievingAllFileStatus: " + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public boolean createFrequencyCodes(Object obj) {
    contextAdminBeanCheck();
    try {
      adminBeanRemote.createFrequencyCodes(obj);
      return true;
    } catch (Exception e) {
      log.error("Error retrievingFrequencyCodes: " + e.getMessage());
      e.printStackTrace();
      return false;
    }
  }

  public static List<?> viewAllErrorCodes(boolean order) {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.viewAllErrorCodes(order);
    } catch (Exception e) {
      log.error("Error retrievingErrorCodes: " + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public boolean createErrorCodes(Object obj) {
    contextAdminBeanCheck();
    try {
      adminBeanRemote.createErrorCodes(obj);
      return true;
    } catch (Exception e) {
      log.error("Error retrievingErrorCodes: " + e.getMessage());
      e.printStackTrace();
      return false;
    }
  }

  public static List<?> viewAllDebitValueTypes() {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.viewAllDebitValueTypes();
    } catch (Exception e) {
      log.error("Error viewAllDebitValueTypes: " + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public static List<?> viewDebitValueTypeByCriteria(String dbtValueTypeCode) {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.viewDebitValueTypeByCriteria(dbtValueTypeCode);
    } catch (Exception e) {
      log.error("Error viewDebitValueTypeByCriteria: " + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public boolean createdebValueTypeCode(Object obj) {
    contextAdminBeanCheck();
    try {
      adminBeanRemote.createdebValueTypeCode(obj);
      return true;
    } catch (Exception e) {
      log.error("Error retrievingdebValueTypeCode: " + e.getMessage());
      e.printStackTrace();
      return false;
    }
  }

  public static List<?> viewAllOpsProcessControl() {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.viewAllOpsProcessControl();
    } catch (Exception e) {
      log.error("Error viewAllOpsProcessControl: " + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public static List<?> viewAllOpsRefSeqNumber() {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.viewAllOpsRefSeqNumber();
    } catch (Exception e) {
      log.error("Error viewAllOpsRefSeqNumber: " + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public static List<?> viewAllOpsAcSotEot() {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.viewAllOpsAcSotEot();
    } catch (Exception e) {
      log.error("Error viewAllOpsAcSotEot: " + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public List<?> viewAcOpsSOTEOTByCriteria(String instId) {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.viewAcOpsSOTEOTByCriteria(instId);
    } catch (Exception e) {
      log.error("Error viewAcOpsSOTEOTByCriteria: " + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public Integer retrieveSeqNo() {
    int seqNoFromDb = 0;
    contextCheck();
    try {
      seqNoFromDb = beanRemote.retrieveSeqNo();
    } catch (Exception e) {
      log.error("Error on  retrieveSeqNo: " + e.getMessage());
      e.printStackTrace();
    }
    return seqNoFromDb;
  }

  public static List<?> viewLocalInstrumentCodesByCriteria(String localInsCode) {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.viewLocalInstrumentCodesByCriteria(localInsCode);
    } catch (Exception e) {
      log.error("Error viewLocalInstrumentCodesByCriteria: " + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public static List<?> viewReasonCodesByCriteria(String reasonCode) {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.viewReasonCodesByCriteria(reasonCode);
    } catch (Exception e) {
      log.error("Error viewReasonCodesByCriteria: " + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public static List<?> viewFrequencyCodesByCriteria(String frequencyCode) {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.viewFrequencyCodesByCriteria(frequencyCode);
    } catch (Exception e) {
      log.error("Error viewFrequencyCodesByCriteria: " + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public static List<?> viewCurrencyCodesByCriteria(String countryCode) {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.viewCurrencyCodesByCriteria(countryCode);
    } catch (Exception e) {
      log.error("Error viewCurrencyCodesByCriteria: " + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public static List<?> viewSequenceCodesByCriteria(String seqCode) {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.viewSequenceCodesByCriteria(seqCode);
    } catch (Exception e) {
      log.error("Error viewSequenceCodesByCriteria: " + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public static List<?> viewErrorCodesByCriteria(String errorCode) {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.viewErrorCodesByCriteria(errorCode);
    } catch (Exception e) {
      log.error("Error viewErrorCodesByCriteria: " + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public static List<?> viewSystemParametersByCriteria(String sysName) {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.viewSystemParametersByCriteria(sysName);
    } catch (Exception e) {
      log.error("Error viewSystemParametersByCriteria: " + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public List<?> viewAllMember() {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.viewAllMember();
    } catch (Exception e) {
      log.error("Error viewAllMember: " + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public List<?> viewAllBranch() {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.viewAllBranch();
    } catch (Exception e) {
      log.error("Error viewAllMember: " + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public static List<?> viewCustomerParametersByCriteria(String instId) {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.viewCustomerParametersByCriteria(instId);
    } catch (Exception e) {
      log.error("Error viewCustomerParametersByCriteria: " + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public boolean createInstId(Object obj) {
    contextCheck();
    try {
      adminBeanRemote.createInstId(obj);
      return true;
    } catch (Exception e) {
      log.error("Error on  createBicCode: " + e.getMessage());
      e.printStackTrace();
      return false;
    }
  }

  public static List<?> viewAllCustomerParameters() {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.viewAllCustomerParameters();
    } catch (Exception e) {
      log.error("Error retrievingCustomerParameters: " + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }
  public List<?> retrieveOpsFileRegByCriteria(String namedQuery, String property, String value) {
    List<?> list = null;
    contextCheck();
    try {
      list = beanRemote.retrieveOpsFileRegByCriteria(namedQuery, property, value);
    } catch (Exception e) {
      log.error("Error retrieveOpsFileReg: " + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public List<?> viewAllDelDelivery() {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.viewAllDelDelivery();
    } catch (Exception e) {
      log.error("Error viewAllDelDelivery: " + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public boolean createlocalWebSystemParametersModel(
      SystemParameterModel createlocalWebSystemParametersModel) {
    return false;
  }

  public List<?> viewDelDeliveryByCriteria(String fileName) {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.viewDelDeliveryByCriteria(fileName);
    } catch (Exception e) {
      log.error("Error viewDelDeliveryCriteria: " + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public static List<?> viewAllSeverityCodes() {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.viewAllSeverityCodes();
      if (list.size() > 0) {
        log.debug("list-->" + list.toString());
      }
    } catch (Exception e) {
      log.error("Error viewAllSeverityCodes: " + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public void stop_Scheduler() {
    contextTimerBeanCheck();
    timerBeanRemote.cancelTimer("info");
  }

  public void start_Scheduler() {
    contextTimerBeanCheck();
    timerBeanRemote.extractStart();
  }

  public List<?> viewSchedulerExtract() {
    contextCheck();
    List<?> list = null;
    try {
      list = beanRemote.viewSchedulerModel();
    } catch (Exception e) {
      log.error("Error viewSchedulerModel: " + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public boolean truncateTable(String tableName) {
    contextAdminBeanCheck();
    try {
      adminBeanRemote.truncateTable(tableName);
      return true;
    } catch (Exception e) {
      log.error("Error truncateTable: " + e.getMessage());
      e.printStackTrace();
      return false;
    }
  }

  public boolean runStartofDayManually() {
    contextAdminBeanCheck();
    boolean sodCheck = false;
    try {
      sodCheck = adminBeanRemote.runStartofDayManually();
      feedbackMsg = adminBeanRemote.retrieveFeedbackMsg();
    } catch (Exception e) {
      log.error("Error on Start of Day: " + e.getMessage());
      e.printStackTrace();
    }
    return sodCheck;
  }

  public boolean runEndofDayManually(String userName, String forcecloseReason) {
    contextAdminBeanCheck();
    boolean eodCheck = false;
    try {
      eodCheck = adminBeanRemote.runEndofDayManually(userName, forcecloseReason);
      eodFeedbackMsg = adminBeanRemote.retrieveEodFeedbackMsg();
      log.debug("eodFeedbackMsg---> " + eodFeedbackMsg);
    } catch (Exception e) {
      log.error("Error on End of Day: " + e.getMessage());
      e.printStackTrace();
    }
    return eodCheck;
  }

  public static List<?> viewCountryCodes() {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.viewAllCurrencyCodes();
    } catch (Exception e) {
      log.error("Error viewCountryCodes: " + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public static List<?> viewAllCompParam() {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.viewAllCompParam();
    } catch (Exception e) {
      log.error("Error viewAllCompParam: " + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public static List<?> viewAllReportsName() {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.viewAllReportsName();
    } catch (Exception e) {
      log.error("Error viewAllReportNames: " + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }


  public static List<?> viewActiveReports() {
    contextAdminBeanCheck();
    List<?> list = null;
    try {

      list = adminBeanRemote.viewActiveReports();
    } catch (Exception e) {
      log.error("Error viewActiveReports: " + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public static List<?> viewReportNamesByCriteria(String reportNames) {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.viewReportNamesByCriteria(reportNames);
    } catch (Exception e) {
      log.error("Error viewReportNamesByCriteria: " + e.getMessage());
      log.debug("in the controller");
    }
    return list;
  }

  public static List<?> viewAllRejectReasonCodes() {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.viewAllRejectReasonCodes();
    } catch (Exception e) {
      log.error("Error viewAllRejectReasonCodes: " + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public static List<?> viewRejectReasonByCriteria(String rejectReasonCode) {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.viewRejectReasonCodesByCriteria(rejectReasonCode);
    } catch (Exception e) {
      log.error("Error viewRejectReasonCodesByCriteria: " + e.getMessage());
      log.debug("in the controller");
    }
    return list;
  }

  public static List<?> viewAuditTrackingCriteria(String tableName) {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.viewAuditTrackingCriteria(tableName);
    } catch (Exception e) {
      log.error("Error viewAuditTrackingCriteria: " + e.getMessage());
      log.debug("in the controller");
    }
    return list;
  }

  public static List<?> viewAllAuditTables() {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.viewAllAuditTables();
    } catch (Exception e) {
      log.error("Error viewAuditTrackingCriteria: " + e.getMessage());
      log.debug("in the controller");
    }
    return list;
  }

  public boolean createReportNames(Object obj) {
    contextAdminBeanCheck();
    try {
      adminBeanRemote.createReportNames(obj);
      return true;
    } catch (Exception e) {
      log.error("Error retrievingReportNames: " + e.getMessage());
      e.printStackTrace();
      return false;
    }
  }

  public boolean createRejectReasonCode(Object obj) {
    contextAdminBeanCheck();
    try {
      adminBeanRemote.createRejectReasonCodes(obj);
      return true;
    } catch (Exception e) {
      log.error("Error creatingRejectReasonCodes: " + e.getMessage());
      e.printStackTrace();
      return false;
    }
  }

  // ************VALIDATION METHOD*******************//

  public Object retrieveErrorCode(String errorCode) {
    Object obj = null;
    contextCheck();
    try {
      obj = beanRemote.retrieveErrorCode(errorCode);
    } catch (Exception e) {
      log.error("Error on retrieveErrorCode: " + e.getMessage());
      e.printStackTrace();
    }
    return obj;
  }

  public Object retrieveCompanyParameters() {
    Object obj = null;
    contextCheck();
    try {
      obj = beanRemote.retrieveCompanyParameters();
      log.debug("Comp Obj--->" + obj);
    } catch (Exception e) {
      log.error("Error on retrieveCompanyParameters: " + e.getMessage());
      e.printStackTrace();
    }
    return obj;
  }

  public Object retrieveOpsCustomerParameters(String bicCode) {
    Object obj = null;
    contextCheck();
    try {
      obj = beanRemote.retrieveOpsCustomerParameters(bicCode);
    } catch (Exception e) {
      log.error("Error on retrieveOpsCustomerParameters: " + e.getMessage());
      e.printStackTrace();
    }
    return obj;
  }

  public Object updateMsgLastFileSeqNr(Object obj) {
    contextValidationBeanCheck();
    try {
      obj = validationBeanRemote.updateMsgLastFileSeqNr(obj, webProcess);
    } catch (Exception e) {
      log.error("Error on updateMsgLastFileSeqNr: " + e.getMessage());
      e.printStackTrace();
    }
    return obj;
  }

  public Object validateBicCode(String bicCode) {
    Object obj = null;
    contextValidationBeanCheck();
    try {
      obj = validationBeanRemote.validateBicCode_003(bicCode, webProcess);
    } catch (Exception e) {
      log.error("Error on validateBicCode: " + e.getMessage());
      e.printStackTrace();
    }
    return obj;
  }

  public static List<?> retrieveCreditorSummary() {
    contextCheck();
    List<?> list = null;
    try {
      list = beanRemote.retrieveCreditorSummary();
    } catch (Exception e) {
      log.error("Error retrieveCreditorSummary: " + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public static List<?> retrieveReports(String bicCode) {
    contextCheck();
    List<?> list = null;
    try {
      list = beanRemote.retrieveReports(bicCode);
    } catch (Exception e) {
      log.error("Error retrieveReports: " + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public static List<?> retrieveDebtorSummary() {
    contextCheck();
    List<?> list = null;
    try {
      list = beanRemote.retrieveDebtorSummary();
    } catch (Exception e) {
      log.error("Error retrieveDebtorSummary: " + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public Object retrieveCisMemberByCriteria(String memberNo) {
    contextCheck();
    Object obj = null;
    try {
      obj = beanRemote.retrieveCisMemberByCriteria(memberNo);
    } catch (Exception e) {
      log.error("Error retrieveCisMemberByCriteria: " + e.getMessage());
      e.printStackTrace();
    }
    return obj;
  }

  public boolean deleteFileStatus(Object obj) {
    contextAdminBeanCheck();
    try {
      adminBeanRemote.deleteFileStatus(obj);
      return true;
    } catch (Exception e) {
      log.error("Error deleteFileStatus: " + e.getMessage());
      e.printStackTrace();
      return false;
    }
  }

  public static List<?> retrieveCaptureLocalInstCodes() {
    contextCheck();
    List<?> list = null;
    try {
      list = beanRemote.retrieveCaptureLocalInstCodes();
    } catch (Exception e) {
      log.error("Error retrieveCaptureLocalInstCodes: " + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public static List<?> retrieveCisBranchByCriteria(String memberNo) {
    contextCheck();
    List<?> list = null;
    try {
      list = beanRemote.retrieveCisBranchByCriteria(memberNo);
    } catch (Exception e) {
      log.error("Error retrieveCisBranchByCriteria: " + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public static List<?> retrieveCisMemberTableByCriteria(String memberNo) {
    contextCheck();
    List<?> list = null;
    try {
      list = beanRemote.retrieveCisMemberTableByCriteria(memberNo);
    } catch (Exception e) {
      log.error("Error retrieveCreditorByCriteria: " + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public List<?> retrieveMandatesSearchCriteria(String mandateId, String debtorName,
                                                String debtorBank, String creditorName,
                                                String creditorBank) {
    contextCheck();
    List<?> list = null;
    try {
      list =
          beanRemote.retrieveMandatesSearchCriteria(mandateId, debtorName, debtorBank, creditorName,
              creditorBank);
    } catch (Exception e) {
      log.error("Error retrieveMandatesSearchCriteria: " + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public static List<?> viewAllProcessStatus() {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.viewAllProcessStatus();
    } catch (Exception e) {
      log.error("Error viewAllProcessStatus: " + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public List<?> viewProcessStatusByCriteria(String status) {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.viewProcessStatusByCriteria(status);
    } catch (Exception e) {
      log.error("Error viewProcessStatusByCriteria:" + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public boolean createProcessStatus(Object obj) {
    contextAdminBeanCheck();
    try {
      adminBeanRemote.createProcessStatus(obj);
      return true;
    } catch (Exception e) {
      log.error("Error createProcessStatus: " + e.getMessage());
      e.printStackTrace();
      return false;
    }
  }

  public void runCisDownload() {
    contextAdminBeanCheck();
    try {
      log.debug("cis in the controller");
      adminBeanRemote.runCisDownload();
    } catch (Exception e) {
      log.error("Error on Cis Download: " + e.getMessage());
      e.printStackTrace();
    }
  }

  public static List<?> viewAllServices() {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.viewAllServices();
    } catch (Exception e) {
      log.error("Error retrievingServices: " + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public List<?> viewMandatesCountOutGoingMessages() {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.viewAllMandatesMessagesPerOutGoingFiles();
    } catch (Exception e) {
      log.error("Error viewMandatesCountOutGoingMessages: " + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public List<?> viewOpsSlaTimes() {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.viewAllOpsSlaTimes();
    } catch (Exception e) {
      log.error("Error viewAllOpsSlaTimes: " + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public List<?> viewMandatesCountIncomingMessages() {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.viewAllMandatesCountPerIncomingFiles();
    } catch (Exception e) {
      log.error("Error viewAllMandatesCountPerIncomingFiles: " + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public List<?> viewAllMessages() {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.viewAllMessages();
    } catch (Exception e) {
      log.error("Error  viewAllMessages" + e.getMessage());
    }
    return list;
  }

  public Object retrieveActiveSystemParameters() {
    contextAdminBeanCheck();
    Object obj = null;
    try {
      obj = adminBeanRemote.retrieveActiveSysParameter();
    } catch (Exception e) {
      log.error("Error retrieveActiveSystemParameters: " + e.getMessage());
      e.printStackTrace();
    }
    return obj;
  }

  public List<?> viewOpsFileRegByDirection(String direction, String fileName) {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.viewOpsFileRegByDirection(direction, fileName);
    } catch (Exception e) {
      log.error("Error viewOpsFileReg: " + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public List<?> viewOpsFileRegByCriteria(String fileName) {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.viewDelDeliveryByCriteria(fileName);
    } catch (Exception e) {
      log.error("Error viewDelDeliveryCriteria: " + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public Object retrieveOpsStatusHdrs(String orgnlMsgId) {
    contextAdminBeanCheck();
    Object obj = null;
    try {
      obj = adminBeanRemote.retrieveOpsStatusHdrs(orgnlMsgId);
    } catch (Exception e) {
      log.error("Error retrieveOpsStatusHdrs: " + e.getMessage());
      e.printStackTrace();
    }
    return obj;
  }

  public List<?> retrieveOpsStatusDetails(BigDecimal statusHdrSeqNo) {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.retrieveOpsStatusDetails(statusHdrSeqNo);
    } catch (Exception e) {
      log.error("Error retrieveOpsStatusDetails: " + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public Object retrieveWebActiveSysParameter() {
    contextAdminBeanCheck();
    Object obj = null;
    try {
      obj = adminBeanRemote.retrieveWebActiveSysParameter();
      log.debug("ProcessDate Obj--->" + obj);
    } catch (Exception e) {
      log.error("Error retrieveActiveSysParams: " + e.getMessage());
      e.printStackTrace();
    }
    return obj;
  }

  public List<?> retrieveNrOfIncomingFiles() {
    contextCheck();
    List<?> list = null;
    try {
      list = beanRemote.retrieveIncomingNrOfFiles();
    } catch (Exception e) {
      log.error("Error on retrieveNrOfIncomingFiles" + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public List<?> retrieveNrOfOutgoingFiles() {
    contextCheck();
    List<?> list = null;
    try {
      list = beanRemote.retrieveOutGoingNrOfFiles();
    } catch (Exception e) {
      log.error("Error on retrieveNrOfOutgoingFiles" + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public List<?> sumIncomingMessagesPerServiceId() {
    contextCheck();
    List<?> list = null;
    try {
      list = beanRemote.sumIncomingMessagesPerServiceId();
    } catch (Exception e) {
      log.error("Error on sumIncomingMessagesPerServiceId" + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public List<?> sumOutGoingMessagesPerServiceID() {
    contextCheck();
    List<?> list = null;
    try {
      list = beanRemote.sumOutGoingMessagesPerServiceID();
    } catch (Exception e) {
      log.error("Error on sumOutGoingMessagesPerServiceID" + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public List<?> retrieveBalanceCarriedForward() {
    contextCheck();
    List<?> list = null;
    try {
      list = beanRemote.retrieveBalanceCarriedForward();
    } catch (Exception e) {
      log.error("Error on retrieveBalanceCarriedForward" + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public List<?> retrieveCustomerParameters() {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.retrieveOpsCustomerParameters();
    } catch (Exception e) {
      log.error("Error on retrieveOpsCustomerParameters" + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public List<?> viewAllOpsCustomerParameters() {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.viewAllOpsCustomerParameters();
    } catch (Exception e) {
      log.error("Error on viewAllOpsCustomerParameters() " + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public List<?> retrieveOutstandingResponses(String memberId, String onlineInd) {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.retrieveOutstandingResponses(memberId, onlineInd);
    } catch (Exception e) {
      log.error("Error on retrieveOutstandingResponses" + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public void runSOT(String destMemberId, String serviceName) {
    contextAdminBeanCheck();
    try {
      adminBeanRemote.runSOT(destMemberId, serviceName);
    } catch (Exception e) {
      log.error("Error on Cis Download: " + e.getMessage());
      e.printStackTrace();
    }
  }

  public void runEOT(String destMemberId, String serviceName) {
    contextAdminBeanCheck();
    try {
      adminBeanRemote.runEOT(destMemberId, serviceName);
    } catch (Exception e) {
      log.error("Error on Cis Download: " + e.getMessage());
      e.printStackTrace();
    }
  }

  public boolean createAdjustmentCategory(AdjustmentCategoryModel adjustmentCategoryModel) {
    contextAdminBeanCheck();
    try {
      adminBeanRemote.createAdjustmentCategory(adjustmentCategoryModel);
      return true;
    } catch (Exception e) {
      log.error("Error createAdjustmentCategory: " + e.getMessage());
      e.printStackTrace();
      return false;
    }
  }

  public List<?> viewAllAdjustmentCategory() {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.viewAllAdjustmentCategory();
    } catch (Exception e) {
      log.error("Error viewAllAdjustmentCategory: " + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public List<?> viewAdjustmentCategoryByCriteria(String adjustmentCategory) {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.viewAdjustmentCategoryByCriteria(adjustmentCategory);
    } catch (Exception e) {
      log.error("Error viewAdjustmentCategoryByCriteria:" + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public boolean createAuditTracking(Object obj) {
    contextAdminBeanCheck();
    try {
      adminBeanRemote.createAuditTracking(obj);
      return true;
    } catch (Exception e) {
      log.error("Error createAuditTracking:" + e.getMessage());
      e.printStackTrace();
      return false;
    }
  }

  // ******************************QUARTZ SCHEDULER CALLS
  // **************************************//

  // Start the entire scheduler
  public boolean startAllSchedulers() {
    contextQuartzSchedulerBeanCheck();
    boolean startAllSch = false;
    try {
      startAllSch = quartzSchedulerRemote.startAllSchedulers();
    } catch (Exception e) {
      log.error("Error on starting all schedulers: " + e.getMessage());
      e.printStackTrace();
    }
    return startAllSch;
  }

  public void shutdownScheduler() {
    contextQuartzSchedulerBeanCheck();
    try {
      quartzSchedulerRemote.shutDownScheduler();
    } catch (Exception e) {
      log.error("Error shutting scheduler " + e.getMessage());
      e.printStackTrace();
    }
  }

  public boolean stopAllSchedulers() {
    contextQuartzSchedulerBeanCheck();
    boolean stopAllSch = false;
    try {
      stopAllSch = quartzSchedulerRemote.stopAllSchedulers();
    } catch (Exception e) {
      log.error("Error on stopping all schedulers: " + e.getMessage());
      e.printStackTrace();
    }
    return stopAllSch;
  }

  public boolean rescheduleAllSchedulers(String rescheduleCron, String cronTime) {
    contextQuartzSchedulerBeanCheck();
    boolean reschAllSch = false;
    try {
      reschAllSch = quartzSchedulerRemote.rescheduleAllSchedulers(rescheduleCron, cronTime);
    } catch (Exception e) {
      log.error("Error on rescheduling all schedulers: " + e.getMessage());
      e.printStackTrace();
    }
    return reschAllSch;
  }

  // ~~~~~~~~~START specific schedulers~~~~~~~~~~~~~~//
  public boolean startPain010() {
    contextQuartzSchedulerBeanCheck();
    boolean manomChk = false;
    try {
      manomChk = quartzSchedulerRemote.startPain010();
    } catch (Exception e) {
      log.error("Error on  startPain010 " + e.getMessage());
      e.printStackTrace();
    }
    return manomChk;
  }

  public boolean startPain012() {
    contextQuartzSchedulerBeanCheck();
    boolean mancoChk = false;
    try {
      mancoChk = quartzSchedulerRemote.startPain012();
    } catch (Exception e) {
      log.error("Error on  startPain012 " + e.getMessage());
      e.printStackTrace();
    }
    return mancoChk;
  }

  public boolean startPacs002() {
    contextQuartzSchedulerBeanCheck();
    boolean pacs002Chk = false;
    try {
      pacs002Chk = quartzSchedulerRemote.startPacs002();
    } catch (Exception e) {
      log.error("Error on  startPacs002 " + e.getMessage());
      e.printStackTrace();
    }
    return pacs002Chk;
  }

  public boolean startST100() {
    contextQuartzSchedulerBeanCheck();
    boolean st100Chk = false;
    try {
      st100Chk = quartzSchedulerRemote.startST100();
    } catch (Exception e) {
      log.error("Error on  startST100 " + e.getMessage());
      e.printStackTrace();
    }
    return st100Chk;
  }

  public boolean startST102() {
    contextQuartzSchedulerBeanCheck();
    boolean st102Chk = false;
    try {
      st102Chk = quartzSchedulerRemote.startST102();
    } catch (Exception e) {
      log.error("Error on  startST102 " + e.getMessage());
      e.printStackTrace();
    }
    return st102Chk;
  }

  public boolean startST104() {
    contextQuartzSchedulerBeanCheck();
    boolean st104Chk = false;
    try {
      st104Chk = quartzSchedulerRemote.startST104();
    } catch (Exception e) {
      log.error("Error on  startST104 " + e.getMessage());
      e.printStackTrace();
    }
    return st104Chk;
  }

  public boolean startBillingExport() {
    contextQuartzSchedulerBeanCheck();
    boolean billChk = false;
    try {
      log.info("Starting Billing Export Controller--->");
      billChk = quartzSchedulerRemote.startBilling();
    } catch (Exception e) {
      log.error("Error on  startBillingExport " + e.getMessage());
      e.printStackTrace();
    }
    return billChk;
  }

  public boolean startEotExtract() {
    contextQuartzSchedulerBeanCheck();
    boolean eotExtractChk = false;
    try {
      log.debug("Starting Eot Controller Controller--->");
      eotExtractChk = quartzSchedulerRemote.startEotExtract();
    } catch (Exception e) {
      log.error("Error on  startEotExtract " + e.getMessage());
      e.printStackTrace();
    }
    return eotExtractChk;
  }

  public boolean startDlyReports() {
    contextQuartzSchedulerBeanCheck();
    boolean reportChk = false;
    try {
      reportChk = quartzSchedulerRemote.startDlyReports();
    } catch (Exception e) {
      log.error("Error on  startDlyReports " + e.getMessage());
      e.printStackTrace();
    }
    return reportChk;
  }

  public boolean unschedulePain010() {
    contextQuartzSchedulerBeanCheck();
    boolean pain010Stop = false;
    try {
      pain010Stop = quartzSchedulerRemote.unschedulePain010();
    } catch (Exception e) {
      log.error("Error on  unschedulePain010 " + e.getMessage());
      e.printStackTrace();
    }
    return pain010Stop;
  }

  public boolean unschedulePain012() {
    contextQuartzSchedulerBeanCheck();
    boolean pain012Stop = false;
    try {
      pain012Stop = quartzSchedulerRemote.unschedulePain012();
    } catch (Exception e) {
      log.error("Error on  unschedulePain012 " + e.getMessage());
      e.printStackTrace();
    }
    return pain012Stop;
  }

  public boolean unschedulePacs002() {
    contextQuartzSchedulerBeanCheck();
    boolean pacs002Stop = false;
    try {
      pacs002Stop = quartzSchedulerRemote.unschedulePacs002();
    } catch (Exception e) {
      log.error("Error on  unschedule pacs002 " + e.getMessage());
      e.printStackTrace();
    }
    return pacs002Stop;
  }

  public boolean unscheduleST100() {
    contextQuartzSchedulerBeanCheck();
    boolean st100Stop = false;
    try {
      st100Stop = quartzSchedulerRemote.unscheduleST100();
    } catch (Exception e) {
      log.error("Error on  unscheduleST100 " + e.getMessage());
      e.printStackTrace();
    }
    return st100Stop;
  }

  public boolean unscheduleST102() {
    contextQuartzSchedulerBeanCheck();
    boolean st102Stop = false;
    try {
      st102Stop = quartzSchedulerRemote.unscheduleST102();
    } catch (Exception e) {
      log.error("Error on  unscheduleST102 " + e.getMessage());
      e.printStackTrace();
    }
    return st102Stop;
  }

  public boolean unscheduleST104() {
    contextQuartzSchedulerBeanCheck();
    boolean st104Stop = false;
    try {
      st104Stop = quartzSchedulerRemote.unscheduleST104();
    } catch (Exception e) {
      log.error("Error on  unscheduleST104 " + e.getMessage());
      e.printStackTrace();
    }
    return st104Stop;
  }

  public boolean unscheduleBillingExport() {
    contextQuartzSchedulerBeanCheck();
    boolean billStop = false;
    try {
      billStop = quartzSchedulerRemote.unscheduleBilling();
    } catch (Exception e) {
      log.error("Error on  unscheduleBillingExport " + e.getMessage());
      e.printStackTrace();
    }
    return billStop;
  }

  public boolean unscheduleEotExtract() {
    contextQuartzSchedulerBeanCheck();
    boolean eotExtractStop = false;
    try {
      eotExtractStop = quartzSchedulerRemote.unscheduleEotExtract();
    } catch (Exception e) {
      log.error("Error on  unscheduleEotExtract " + e.getMessage());
      e.printStackTrace();
    }
    return eotExtractStop;
  }

  public boolean unscheduleDlyReports() {
    contextQuartzSchedulerBeanCheck();
    boolean reportsStop = false;
    try {
      reportsStop = quartzSchedulerRemote.unscheduleReportGen();
    } catch (Exception e) {
      log.error("Error on  unscheduleDlyReports " + e.getMessage());
      e.printStackTrace();
    }
    return reportsStop;

  }

  // ~~~~~~~~~RESCHEDULE specific schedulers~~~~~~~~~~~~~~//
  public boolean reschedulePain010(String rescheduleCron, String cronTime) {
    contextQuartzSchedulerBeanCheck();
    boolean reschPain010 = false;
    try {
      reschPain010 = quartzSchedulerRemote.reschedulePain010(rescheduleCron, cronTime);
    } catch (Exception e) {
      log.error("Error on  reschedulePain010: " + e.getMessage());
      e.printStackTrace();
    }
    return reschPain010;
  }
  public boolean reschedulePain012(String rescheduleCron, String cronTime) {
    contextQuartzSchedulerBeanCheck();
    boolean reschPain012 = false;
    try {
      reschPain012 = quartzSchedulerRemote.reschedulePain012(rescheduleCron, cronTime);
    } catch (Exception e) {
      log.error("Error on  reschedulePain012: " + e.getMessage());
      e.printStackTrace();
    }
    return reschPain012;
  }

  public boolean reschedulePacs002(String rescheduleCron, String cronTime) {
    contextQuartzSchedulerBeanCheck();
    boolean reschPacs002 = false;
    try {
      reschPacs002 = quartzSchedulerRemote.reschedulePacs002(rescheduleCron, cronTime);
    } catch (Exception e) {
      log.error("Error on  reschedulePacs002: " + e.getMessage());
      e.printStackTrace();
    }
    return reschPacs002;
  }

  public boolean rescheduleST100(String rescheduleCron, String cronTime) {
    contextQuartzSchedulerBeanCheck();
    boolean reschST100 = false;
    try {
      reschST100 = quartzSchedulerRemote.rescheduleST100(rescheduleCron, cronTime);
    } catch (Exception e) {
      log.error("Error on  rescheduleST100: " + e.getMessage());
      e.printStackTrace();
    }
    return reschST100;
  }

  public boolean rescheduleST102(String rescheduleCron, String cronTime) {
    contextQuartzSchedulerBeanCheck();
    boolean reschST102 = false;
    try {
      reschST102 = quartzSchedulerRemote.rescheduleST102(rescheduleCron, cronTime);
    } catch (Exception e) {
      log.error("Error on  rescheduleST102: " + e.getMessage());
      e.printStackTrace();
    }
    return reschST102;
  }

  public boolean rescheduleST104(String rescheduleCron, String cronTime) {
    contextQuartzSchedulerBeanCheck();
    boolean reschST104 = false;
    try {
      reschST104 = quartzSchedulerRemote.rescheduleST104(rescheduleCron, cronTime);
    } catch (Exception e) {
      log.error("Error on  rescheduleST104: " + e.getMessage());
      e.printStackTrace();
    }
    return reschST104;
  }

  public boolean rescheduleDlyReports(String rescheduleCron, String cronTime) {
    contextQuartzSchedulerBeanCheck();
    boolean reschReports = false;
    try {
      reschReports = quartzSchedulerRemote.rescheduleDlyReports(rescheduleCron, cronTime);
    } catch (Exception e) {
      log.error("Error on rescheduleDlyReports: " + e.getMessage());
      e.printStackTrace();
    }
    return reschReports;
  }

  // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

  public List<?> viewAllAuditTracking() {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.viewAllAuditTracking();
    } catch (Exception e) {
      log.error("Error viewAllAuditTracking: " + e.getMessage());
      e.printStackTrace();
    }
    return list;

  }

  public boolean createAccountType(AccountTypeModel accountTypeModel) {
    contextAdminBeanCheck();
    try {
      adminBeanRemote.createAccountType(accountTypeModel);
      return true;
    } catch (Exception e) {
      log.error("Error createAccountType: " + e.getMessage());
      e.printStackTrace();
      return false;
    }
  }

  public List<?> viewAllAccountType() {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.viewAllAccountType();
    } catch (Exception e) {
      log.error("Error retrievingAccountType: " + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public List<?> viewAccountTypeByCriteria(String accountType) {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.viewAccountTypeByCriteria(accountType);
    } catch (Exception e) {
      log.error("Error viewAccountTypeByCriteria:" + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public boolean createSeverityCodes(SeverityCodesModel severityCodesModel) {
    contextAdminBeanCheck();
    try {
      adminBeanRemote.createSeverityCodes(severityCodesModel);
      return true;
    } catch (Exception e) {
      log.error("Error createSeverityCodes: " + e.getMessage());
      e.printStackTrace();
    }
    return false;
  }

  public List<?> viewSeverityCodesByCriteria(String severityCode) {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      log.debug("In the viewSeverityCodesByCriteria from Controller--->: ");
      list = adminBeanRemote.viewSeverityCodesByCriteria(severityCode);
    } catch (Exception e) {
      log.error("Error on viewSeverityCodesByCriteria:" + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public List<?> viewAllSeverityCode() {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.viewAllSeverityCode();
    } catch (Exception e) {
      log.error("Error retrievingSeverityCode: " + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public List<?> viewAllStatusReasonCodes() {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.viewAllStatusReasonCodes();
    } catch (Exception e) {
      log.error("Error retrievingStatusReasonCodes: " + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public List<?> viewStatusReasonCodesByCriteria(String statusReasonCode) {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.viewStatusReasonCodesByCriteria(statusReasonCode);
    } catch (Exception e) {
      log.error("Error viewStatusReasonCodesByCriteria:" + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public boolean createStatusReasonCodes(StatusReasonCodesModel statusReasonCodesModel) {
    contextAdminBeanCheck();
    try {
      adminBeanRemote.createStatusReasonCodes(statusReasonCodesModel);
      return true;
    } catch (Exception e) {
      log.error("Error createStatusReasonCodes: " + e.getMessage());
      e.printStackTrace();
      return false;
    }
  }

  public List<?> retrieveAllSysCisBank() {
    contextCheck();
    List<?> list = null;
    try {
      list = beanRemote.retrieveAllSysCisBank();
      log.info("The list in the controller has this information " + list);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return list;
  }

  public List<?> viewAllRecordId() {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.viewAllRecordId();
    } catch (Exception e) {
      log.error("Error viewAllRecordId: " + e.getMessage());
    }
    return list;
  }

  public List<?> retrieveIncomingMandatesCount() {
    contextCheck();
    List<?> list = null;
    try {
      list = beanRemote.retrieveIncomingMandatesCount();
    } catch (Exception e) {
      log.error("Error retrieveIncomingMandatesCount: " + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public List<?> retrieveOutgoingMandatesCount() {
    contextCheck();
    List<?> list = null;
    try {
      list = beanRemote.retrieveOutgoingMandatesCount();
    } catch (Exception e) {
      log.error("Error retrieveOutgoingMandatesCount: " + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public List<?> viewRecordIdByCriteria(BigDecimal RecordId) {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.viewRecordIdByCriteria(RecordId);
    } catch (Exception e) {
      log.error("Error viewRecordIdByCriteria: " + e.getMessage());
      log.debug("in the controller");
    }
    return list;
  }

  public boolean createRecordId1(Object obj) {
    contextAdminBeanCheck();
    try {
      adminBeanRemote.createRecordId1(obj);
      return true;
    } catch (Exception e) {
      log.error("Error createRecordId: " + e.getMessage());
      e.printStackTrace();
      return false;
    }
  }

  public List<?> retriveExtractReportData() {
    contextCheck();
    List<?> list = null;
    try {
      list = beanRemote.retriveExtractReportData();
    } catch (Exception e) {
      log.error("Error retriveExtractReportData: " + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public List<?> retrieveRejectedReportData() {
    contextCheck();
    List<?> list = null;
    try {
      list = beanRemote.retrieveRejectedReportData();
    } catch (Exception e) {
      log.error("Error retriveExtractReportData: " + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public List<?> retrieveAllActiveInd() {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.retrieveAllActiveInd();
    } catch (Exception e) {
      log.error("Error on retrieveAllactiveInd" + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public List<?> retrieveAllInActiveInd() {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.retrieveAllInactiveInd();
    } catch (Exception e) {
      log.error("Error on retrieveAllactiveInd" + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public List<?> retrieveAcceptedCountData() {
    contextCheck();
    List<?> list = null;
    try {
      list = beanRemote.retrieveAcceptedCountData();
    } catch (Exception e) {
      log.error("Error retriveExtractReportData: " + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public List<?> retrieveRejectedCountData() {
    contextCheck();
    List<?> list = null;
    try {
      list = beanRemote.retrieveRejectedCountData();
    } catch (Exception e) {
      log.error("Error retriveExtractReportData: " + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public List<?> retrieveExtractedCountData() {
    contextCheck();
    List<?> list = null;
    try {
      list = beanRemote.retrieveExtractedCountData();
    } catch (Exception e) {
      log.error("Error retriveExtractReportData: " + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public List<?> retrieveMndtFileStatus() {
    contextCheck();
    List<?> list = null;
    try {
      list = beanRemote.retrieveMndtFileStatus();
    } catch (Exception e) {
      log.error("Error retrieveMndtFileStatus: " + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public List<?> retrieveSummaryTotals(String instId, String onlineInd) {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.retrieveSummaryTotals(instId, onlineInd);
    } catch (Exception e) {
      log.error("Error on retrieveSummaryTotals" + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public List<?> retrieveCountNrOfFilesStatus() {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.retrieveCountNrOfFilesStatus();
    } catch (Exception e) {
      log.error("Error on retrieveCountNrOfFilesStatus" + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public List<?> retrieveCountNrOfMsgsStatus() {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.retrieveCountNrOfMsgsStatus();
    } catch (Exception e) {
      log.error("Error on retrieveCountNrOfMsgsStatus" + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public boolean rescheduleBillingExport(String rescheduleCron, String cronTime) {
    contextQuartzSchedulerBeanCheck();
    boolean reschBill = false;
    try {
      reschBill = quartzSchedulerRemote.rescheduleBilling(rescheduleCron, cronTime);
    } catch (Exception e) {
      log.error("Error on  rescheduleBillingExport: " + e.getMessage());
      e.printStackTrace();
    }
    return reschBill;
  }

  public boolean rescheduleEotExtract(String rescheduleCron, String cronTime) {
    contextQuartzSchedulerBeanCheck();
    boolean eotExtractChk = false;
    try {
      eotExtractChk = quartzSchedulerRemote.rescheduleEotExtract(rescheduleCron, cronTime);
    } catch (Exception e) {
      log.error("Error on  rescheduleEotExtract: " + e.getMessage());
      e.printStackTrace();
    }
    return eotExtractChk;
  }

  public List<?> viewFileStatusFilter(String fileName) {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.viewFileStatusFilter(fileName);
    } catch (Exception e) {
      log.error("Error on viewFileStatusFilter" + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public List<?> viewFileStatusSearch(String memberNo, String service) {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.viewFileStatusSearch(memberNo, service);
    } catch (Exception e) {
      log.error("Error on viewFileStatusFilter" + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public List<?> viewFileStatusSearch(String fileName) {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.searchByFileName(fileName);
    } catch (Exception e) {
      log.error("Error on viewFileStatusSearch" + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public List<?> retrieveAllEotSot(String instId, String serviceName, String sotIn, String eotIn,
                                   String sotOut, String eotOut) {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.retrieveAllEotSot(instId, serviceName, sotIn, eotIn, sotOut, eotOut);
    } catch (Exception e) {
      log.error("Error on retrieveAllEotSot" + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public List<?> retrieveSotEotServiceId() {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.retrieveSotEotServiceId();
    } catch (Exception e) {
      log.error("Error on retrieveSotEotServiceId" + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public List<?> retrieveOutstandingResponsesD(String memberId, String onlineInd) {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.retrieveOutstandingResponsesD(memberId, onlineInd);
    } catch (Exception e) {
      log.error("Error on retrieveOutstandingResponsesD" + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public void compareCISwithCustPram() {
    contextAdminBeanCheck();
    try {
      adminBeanRemote.compareCISwithCustPram();
    } catch (Exception e) {
      log.error("Error on compareCISwithCustPram: " + e.getMessage());
      e.printStackTrace();
    }
  }

  // added methods form here

  public void retrieveInActiveCisDownload() {
    contextAdminBeanCheck();
    try {
      log.info("cis in the controller");
      adminBeanRemote.retrieveInActiveCisDownload();
    } catch (Exception e) {
      log.error("Error on retrive InActiveCisDownload: " + e.getMessage());
      e.printStackTrace();
    }
  }

  public void retrieveRelatedMember() {
    contextAdminBeanCheck();
    try {
      log.info("cis in the controller");
      adminBeanRemote.retrieveRelatedMember();
    } catch (Exception e) {
      log.error("Error on retrieve RelatedMember: " + e.getMessage());
      e.printStackTrace();
    }
  }

  public void retrieveSysCisBank() {
    contextAdminBeanCheck();
    try {
      log.info("cis in the controller");
      adminBeanRemote.retrieveSysCisBank();
    } catch (Exception e) {
      log.error("Error on retrieveSysCisBank: " + e.getMessage());
      e.printStackTrace();
    }
  }

  public boolean createSysCisBank(Object obj) {
    contextAdminBeanCheck();
    try {
      adminBeanRemote.createSysCisBank(obj);
      return true;
    } catch (Exception e) {
      log.error("Error createSysCisBank: " + e.getMessage());
      e.printStackTrace();
      return false;
    }
  }

  public void retrieveMndCisBranch(String memberNo) {
    contextAdminBeanCheck();
    try {
      log.info("cis in the controller");
      adminBeanRemote.retrieveMndCisBranch(memberNo);
    } catch (Exception e) {
      log.error("Error on retrieveMndCisBranch: " + e.getMessage());
      e.printStackTrace();
    }
  }

  public boolean createCisDownload(Object obj) {
    contextAdminBeanCheck();
    try {
      adminBeanRemote.createCisDownload(obj);
      return true;
    } catch (Exception e) {
      log.error("Error createCisDownload: " + e.getMessage());
      e.printStackTrace();
      return false;
    }
  }

  private static void contextFileWatcherBeanCheck() {
    if (fileWatcherRemote == null) {
      fileWatcherRemote = Util.getFileWatcherBean();
    }
  }

  public void startSOD() {
    contextQuartzSchedulerBeanCheck();
    try {
      quartzSchedulerRemote.startPain012();
    } catch (Exception e) {
      log.error("Error on  startSOD " + e.getMessage());
      e.printStackTrace();
    }
  }

  public void startEOD() {
    contextQuartzSchedulerBeanCheck();
    try {
      quartzSchedulerRemote.startPain012();
    } catch (Exception e) {
      log.error("Error on  startEOD " + e.getMessage());
      e.printStackTrace();
    }
  }

  public List<?> viewSysCntrlServiceByService(String serviceId) {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.viewSysCntrlServiceByService(serviceId);
    } catch (Exception e) {
      log.error("Error viewSysCntrlServiceByService: " + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public List<?> viewAllOpsSchedulers() {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.viewAllOpsSchedulers();
    } catch (Exception e) {
      e.printStackTrace();
      e.getMessage();
    }
    return list;
  }

  public List<?> retrieveAuditTracking(String userId, Date fromDate, Date toDate, String action) {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.retrieveAuditTracking(userId, fromDate, toDate, action);
    } catch (Exception e) {
      log.error("Error retrieveAuditTracking: " + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public List<?> viewEOTFile(String memberNo) {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.viewEOTFile(memberNo);
    } catch (Exception e) {
      log.error("Error on viewFileStviewEOTFileatusFilter" + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public List<?> retrieveAllEotFiles() {
    contextCheck();
    List<?> list = null;
    try {
      list = beanRemote.retrieveAllEotFiles();
    } catch (Exception e) {
      log.error("Error on retrieveAllEotFiles" + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public List<?> viewEotMessages() {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.viewEotMessages();
    } catch (Exception e) {
      log.error("Error viewEotMessages: " + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public boolean createAmendmentCodes(AmendmentCodesModel amendmentCodesModel) {
    contextAdminBeanCheck();
    try {
      adminBeanRemote.createAmendmentCodes(amendmentCodesModel);
      return true;
    } catch (Exception e) {
      log.error("Error createAmendmentCodes: " + e.getMessage());
      e.printStackTrace();
      return false;
    }
  }

  public List<?> viewAllAmendmentCodes() {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.viewAmendmentCodes();
    } catch (Exception e) {
      log.error("Error retrievingAmendmentCodes: " + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public List<?> viewAmendmentCodesByCriteria(String amendmentCodes) {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.viewAmendmentCodesByCriteria(amendmentCodes);
    } catch (Exception e) {
      log.error("Error viewAmendmentCodesByCriteria:" + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public String getProperty(String property) {
    contextPropertyFileCheck();
    String propertyValue = null;
    try {
      propertyValue = propertyUtilRemote.getPropValue(property);
    } catch (Exception e) {
      log.error("Error getProperty: " + e.getMessage());
      e.printStackTrace();
    }
    return propertyValue;
  }

  public boolean checkIamSession(String sessionKey) {
    contextAdminBeanCheck();
    boolean iamSessionExists = false;
    try {
      iamSessionExists = adminBeanRemote.checkIamSession(sessionKey);
    } catch (Exception e) {
      log.error("Error checkIamSession: " + e.getMessage());
      e.printStackTrace();
    }
    return iamSessionExists;
  }

  public boolean createSession(IamSessionModel iamSessionModel) {
    contextAdminBeanCheck();
    try {
      adminBeanRemote.createSession(iamSessionModel);
      return true;
    } catch (Exception e) {
      log.error("Error createSession: " + e.getMessage());
      e.printStackTrace();
      return false;
    }
  }

  public List<?> retrieveMndtRespPerBank(String memberId, String processStatus) {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.retrieveMndtRespPerBank(memberId, processStatus);
    } catch (Exception e) {
      log.error("Error on retrieveMndtRespPerBank" + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public Object retrieveAmend16Data(String memeberNo, String amendmetReasonCode, String firstDate,
                                    String lastDate) {
    Object obj = null;
    contextCheck();
    try {
      obj = beanRemote.retrieveAmend16Data(memeberNo, amendmetReasonCode, firstDate, lastDate);
      log.debug("retrieveAmend16Data Obj--->" + obj);
    } catch (Exception e) {
      log.error("Error on retrieveAmend16Data" + e.getMessage());
      e.printStackTrace();
    }
    return obj;
  }

  public List<?> retrieveAmendmendReasonCode() {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.retrieveAmendmendReasonCode();
    } catch (Exception e) {
      log.error("Error on retrieveAmendmendReasonCode" + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public Object retrieveReasonCodeTotal(String amendmetReasonCode, String firstDate,
                                        String lastDate) {
    Object obj = null;
    contextCheck();
    try {
      obj = beanRemote.retrieveReasonCodeTotal(amendmetReasonCode, firstDate, lastDate);
      log.debug("retrieveReasonCodeTotal Obj--->" + obj);
    } catch (Exception e) {
      log.error("Error on retrieveReasonCodeTotal" + e.getMessage());
      e.printStackTrace();
    }
    return obj;
  }

  public List<?> retrieveActiveCreditorBank() {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.retrieveActiveCreditorBank();
    } catch (Exception e) {
      log.error("Error on retrieveActiveCreditorBank" + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public Object retrieveRejectReasonData(String rejectReasonCode, String memberNo) {
    Object obj = null;
    contextCheck();
    try {
      obj = beanRemote.retrieveRejectReasonData(rejectReasonCode, memberNo);
      log.debug("retrieveRejectReasonData Obj--->" + obj);
    } catch (Exception e) {
      log.error("Error on retrieveRejectReasonData" + e.getMessage());
      e.printStackTrace();
    }
    return obj;
  }

  public Object retrieveSuspensionData(String suspensionCode, String memberNo) {
    Object obj = null;
    contextCheck();
    try {
      obj = beanRemote.retrieveSuspensionData(suspensionCode, memberNo);
      log.debug("retrieveSuspensionData Obj--->" + obj);
    } catch (Exception e) {
      log.error("Error on retrieveSuspensionData" + e.getMessage());
      e.printStackTrace();
    }
    return obj;
  }

  public Object retrieveReasonCodeData(String reasonCode, String memberNo) {
    Object obj = null;
    contextCheck();
    try {
      obj = beanRemote.retrieveReasonCodeData(reasonCode, memberNo);
      log.debug("retrieveReasonCodeData Obj--->" + obj);
    } catch (Exception e) {
      log.error("Error on retrieveReasonCodeData" + e.getMessage());
      e.printStackTrace();
    }
    return obj;
  }

  public Object retrieveRejectReasonCount(String memberNo) {
    Object obj = null;
    contextCheck();
    try {
      obj = beanRemote.retrieveRejectReasonCount(memberNo);
      log.debug("retrieveRejectReasonCount Obj--->" + obj);
    } catch (Exception e) {
      log.error("Error on retrieveRejectReasonCount" + e.getMessage());
      e.printStackTrace();
    }
    return obj;
  }

  public Object retrieveSuspensionCount(String memberNo) {
    Object obj = null;
    contextCheck();
    try {
      obj = beanRemote.retrieveSuspensionCount(memberNo);
      log.debug("retrieveSuspensionCount Obj--->" + obj);
    } catch (Exception e) {
      log.error("Error on retrieveSuspensionCount" + e.getMessage());
      e.printStackTrace();
    }
    return obj;
  }

  public Object retrieveReasonCodeCount(String memberNo) {
    Object obj = null;
    contextCheck();
    try {
      obj = beanRemote.retrieveReasonCodeCount(memberNo);
      log.debug("retrieveReasonCodeCount Obj--->" + obj);
    } catch (Exception e) {
      log.error("Error on retrieveReasonCodeCount" + e.getMessage());
      e.printStackTrace();
    }
    return obj;
  }

  public Object retrieverRespOutstandForOneDay(String debtorMember, String creditorMember,
                                               String serviceId) {
    contextCheck();
    Object obj = null;
    try {
      obj = beanRemote.retrieverRespOutstandForOneDay(debtorMember, creditorMember, serviceId);
      log.info("retrieverRespOutstandForOneDay obj--->" + obj);
    } catch (Exception e) {
      log.error("Error on retrieverRespOutstandForOneDay" + e.getMessage());
      e.printStackTrace();
    }
    return obj;
  }


  public Object retrieverRespOutstandForTwoDays(String debtorMember, String creditorMember,
                                                String serviceId) {
    contextCheck();
    Object obj = null;
    try {
      obj = beanRemote.retrieverRespOutstandForTwoDays(debtorMember, creditorMember, serviceId);
      log.debug("retrieverRespOutstandForTwoDays obj--->" + obj);
    } catch (Exception e) {
      log.error("Error on retrieverRespOutstandForTwoDays" + e.getMessage());
      e.printStackTrace();
    }
    return obj;
  }

  public List<?> retrieveMndtBillingTransactions(String txnType) {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.retrieveMndtBillingTransactions(txnType);
    } catch (Exception e) {
      log.error("Error on retrieveMndtBillingTransactions" + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public boolean eodCheckIfAllFilesAreExtracted(Date systemDate, String mdtLoadType) {
    contextCheck();
    boolean check = false;
    try {
      check = beanRemote.eodCheckIfAllFilesAreExtracted(systemDate, mdtLoadType);
    } catch (Exception e) {
      log.error("Error on eodCheckIfAllFilesAreExtracted" + e.getMessage());
      e.printStackTrace();
    }
    return check;
  }

  public List<?> calculateCountsReportInfo() {
    contextCheck();
    List<?> list = null;
    try {
      list = beanRemote.calculateCountsReportInfo();
    } catch (Exception e) {
      log.error("Error on calculateCountsReportInfo" + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public List<?> retrieveMndtDailyTransPerDebtor(String instId, String txnType) {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.retrieveMndtDailyTransPerDebtor(instId, txnType);
    } catch (Exception e) {
      log.error("Error on retrieveMndtDailyTransPerDebtor" + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public List<?> retrieveMndtDailyTransPerCreditor(String instId, String txnType) {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.retrieveMndtDailyTransPerCreditor(instId, txnType);
    } catch (Exception e) {
      log.error("Error on retrieveMndtDailyTransPerCreditor" + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public List<?> viewAllCisBankInfo() {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.viewAllCisBankInfo();
    } catch (Exception e) {
      log.error("Error viewAllCisBankInfo: " + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public Object viewCisBankInfoByCriteria(String memberNo) {
    contextAdminBeanCheck();
    Object obj = null;
    try {
      obj = adminBeanRemote.viewCisBanksByCriteria(memberNo);
    } catch (Exception e) {
      log.error("Error viewCisBankInfoByCriteria: " + e.getMessage());
      e.printStackTrace();
    }
    return obj;
  }

  public Object retrieveCisDownloadDate() {
    contextAdminBeanCheck();
    Object obj = null;
    try {
      obj = adminBeanRemote.retrieveCisDownloadDate();
    } catch (Exception e) {
      log.error("Error retrieveCisDownloadDate: " + e.getMessage());
      e.printStackTrace();
    }
    return obj;
  }

  public List<?> viewAllCisBranchInfo() {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.viewAllCisBranchInfo();
    } catch (Exception e) {
      log.error("Error viewAllCisBranchInfo: " + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public Object viewCisBranchInfoByCriteria(String branchNo) {
    contextAdminBeanCheck();
    Object obj = null;
    try {
      obj = adminBeanRemote.viewCisBranchByCriteria(branchNo);
    } catch (Exception e) {
      log.error("Error viewCisBranchInfoByCriteria: " + e.getMessage());
      e.printStackTrace();
    }
    return obj;
  }

  public boolean createCnfgOpsSlaTimes(Object obj) {
    contextAdminBeanCheck();
    try {
      adminBeanRemote.createCnfgOpsSlaTimes(obj);
      return true;
    } catch (Exception e) {
      log.error("Error createCnfgOpsSlaTimes: " + e.getMessage());
      e.printStackTrace();
      return false;
    }
  }

  public List<?> viewSlaTimesByCriteria(String service) {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.viewSlaTimesByCriteria(service);
    } catch (Exception e) {
      log.error("Error viewSlaTimesByCriteria:" + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public List<?> viewSysCtrlSlaTimes() {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.viewSysCtrlSlaTimes();
    } catch (Exception e) {
      log.error("Error viewSysCtrlSlaTimes: " + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public List<?> viewSysCtrlSlaTimesByCriteria(String service) {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.viewSysCtrlSlaTimesByCriteria(service);
    } catch (Exception e) {
      log.error("Error viewSysCtrlSlaTimesByCriteria:" + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public boolean createCnfgSysCtrlSlaTimes(Object obj) {
    contextAdminBeanCheck();
    try {
      adminBeanRemote.createCnfgSysCtrlSlaTimes(obj);
      return true;
    } catch (Exception e) {
      log.error("Error createCnfgSysCtrlSlaTimes: " + e.getMessage());
      e.printStackTrace();
      return false;
    }
  }

  public List<?> viewOpsFileReg(String direction, String fileName) {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.viewOpsFileRegByDirection(direction, fileName);
    } catch (Exception e) {
      log.error("Error viewOpsFileReg: " + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public List<?> retrieveOpsFileRegbyDerictionCriteria(String direction) {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.retrieveOpsFileRegbyDerictionCriteria(direction);
    } catch (Exception e) {
      log.error("Error viewOpsFileReg: " + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public Object retrieveCisSlaTime(String processType) {
    contextAdminBeanCheck();
    Object obj = null;
    try {
      obj = adminBeanRemote.retrieveCisSlaTime(processType);
    } catch (Exception e) {
      log.error("Error retrieveCisSlaTime: " + e.getMessage());
      e.printStackTrace();
    }
    return obj;
  }

  public Object retrieveMdte002RejectReasonDataPerBank(String rejectReasonCode, String memberId,
                                                       String debtorBankId, String firstDate,
                                                       String lastDate) {
    contextAdminBeanCheck();
    Object obj = null;
    try {
      obj = adminBeanRemote.retrieveMdte002RejectReasonDataPerBank(rejectReasonCode, memberId,
          debtorBankId, firstDate, lastDate);
    } catch (Exception e) {
      log.error("Error retrieveMdte002RejectReasonDataPerBank: " + e.getMessage());
      e.printStackTrace();
    }
    return obj;
  }


  public Object retrieveSuspensionDataPerBank(String suspensionCode, String debtorBank,
                                              String memberId) {
    contextAdminBeanCheck();
    Object obj = null;
    try {
      obj = adminBeanRemote.retrieveSuspensionDataPerBank(suspensionCode, debtorBank, memberId);
    } catch (Exception e) {
      log.error("Error retrieveSuspensionDataPerBank: " + e.getMessage());
      e.printStackTrace();
    }
    return obj;
  }

  public Object retrievePain012ReasonCodeDataPerBank(String reasonCode, String memberId,
                                                     String debtorBankId, String startDate,
                                                     String lastDate) {
    contextAdminBeanCheck();
    Object obj = null;
    try {
      obj = adminBeanRemote.retrievePain012ReasonCodeDataPerBank(reasonCode, memberId, debtorBankId,
          startDate, lastDate);
    } catch (Exception e) {
      log.error("Error retrievePain012ReasonCodeDataPerBank: " + e.getMessage());
      e.printStackTrace();
    }
    return obj;
  }


  public List<?> retrieveActiveDebtorBank() {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.retrieveActiveDebtorBank();
    } catch (Exception e) {
      log.error("Error on retrieveActiveDebtorBank" + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public Object retrieveRejectReasonCountPerBank(String memberId, String debtorBank) {
    contextAdminBeanCheck();
    Object obj = null;
    try {
      obj = adminBeanRemote.retrieveRejectReasonCountPerBank(memberId, debtorBank);
    } catch (Exception e) {
      log.error("Error retrieveRejectReasonCountPerBank: " + e.getMessage());
      e.printStackTrace();
    }
    return obj;
  }

  public Object retrieveSuspensionCountPerBank(String memberId, String debtorBank) {
    contextAdminBeanCheck();
    Object obj = null;
    try {
      obj = adminBeanRemote.retrieveSuspensionCountPerBank(memberId, debtorBank);
    } catch (Exception e) {
      log.error("Error retrieveSuspensionCountPerBank: " + e.getMessage());
      e.printStackTrace();
    }
    return obj;
  }

  public Object retrieveReasonCodeCountPerBank(String memberId, String debtorBank) {
    contextAdminBeanCheck();
    Object obj = null;
    try {
      obj = adminBeanRemote.retrieveReasonCodeCountPerBank(memberId, debtorBank);
    } catch (Exception e) {
      log.error("Error retrieveReasonCodeCountPerBank: " + e.getMessage());
      e.printStackTrace();
    }
    return obj;
  }

  public Object retrieveCronIntervalValue(String cronInt) {
    contextAdminBeanCheck();
    Object obj = null;
    try {
      obj = adminBeanRemote.retrieveCronIntervalValue(cronInt);
    } catch (Exception e) {
      log.error("Error retrieveCronIntervalValue: " + e.getMessage());
      e.printStackTrace();
    }
    return obj;
  }

  public boolean saveSystemAuditInfo(Object obj) {
    contextAdminBeanCheck();
    try {
      adminBeanRemote.saveSystemAuditInfo(obj);
      return true;
    } catch (Exception e) {
      log.error("Error saveSystemAuditInfo: " + e.getMessage());
      e.printStackTrace();
      return false;
    }
  }

  public Object retrieveRejectReasonDataPerBankCount(String memberId, String memberNo) {
    Object obj = null;
    contextCheck();
    try {
      obj = beanRemote.retrieveRejectReasonDataPerBankCount(memberId, memberNo);
      log.debug("retrieveRejectReasonDataPerBankCount Obj--->" + obj);
    } catch (Exception e) {
      log.error("Error on retrieveRejectReasonDataPerBankCount" + e.getMessage());
      e.printStackTrace();
    }
    return obj;
  }

  public Object retrieveReasonCodeDataPerBankCount(String memberId, String memberNo) {
    Object obj = null;
    contextCheck();
    try {
      obj = beanRemote.retrieveReasonCodeDataPerBankCount(memberId, memberNo);
      log.debug("retrieveReasonCodeDataPerBankCount Obj--->" + obj);
    } catch (Exception e) {
      log.error("Error on retrieveReasonCodeDataPerBankCount" + e.getMessage());
      e.printStackTrace();
    }
    return obj;
  }


  public List<?> retrieveRealTimeMndtBillingTxns(String txnType) {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.retrieveRealTimeMndtBillingTxns(txnType);
    } catch (Exception e) {
      log.error("Error on retrieveRealTimeMndtBillingTxns" + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public List<?> retrieveMndtDailyTransPerCreditorRealTime(String instId) {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.retrieveRealTimeMndtBillingTxnsByCreditor(instId);
    } catch (Exception e) {
      log.error("Error on retrieveMndtDailyTransPerCreditorRealTime" + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public static List<?> retrieveAllReportRejections() {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.retrieveRejectionCodesForRejectionsReport();
    } catch (Exception e) {
      log.error("Error retrieveAllReportRejections: " + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public static Object retrieveRealTimeAmendmentData(String amendReason, String memberId,
                                                     String firstDate, String lastDate) {
    contextAdminBeanCheck();
    Object obj = null;
    try {
      obj =
          adminBeanRemote.retrieveRealTimeNrOfAmendment(amendReason, memberId, firstDate, lastDate);
    } catch (Exception e) {
      log.error("Error retrieveRealTimeAmendmentData: " + e.getMessage());
      e.printStackTrace();
    }
    return obj;
  }

  public Object retrieverRespOutstandForOneDay(String serviceId) {
    contextCheck();
    Object obj = null;
    try {
      obj = beanRemote.retrieverRespOutstandForOneDay(serviceId);
    } catch (Exception e) {
      log.error("Error retrieverRespOutstandForOneDay: " + e.getMessage());
      e.printStackTrace();
    }
    return obj;
  }

  public Object retrieverRespOutstandForTwoDays(String serviceId) {
    contextCheck();
    Object obj = null;
    try {
      obj = beanRemote.retrieverRespOutstandForTwoDays(serviceId);
    } catch (Exception e) {
      log.error("Error retrieverRespOutstandForTwoDays: " + e.getMessage());
      e.printStackTrace();
    }
    return obj;
  }

  public static Object retrievePASARejectionSummaryPAIN012(String reasonCode, String crBank,
                                                           String firstDate, String lastDate) {
    contextAdminBeanCheck();
    Object obj = null;
    try {
      obj = adminBeanRemote.retrievePain012ReasonCodeDataPASA(reasonCode, crBank, firstDate,
          lastDate);
    } catch (Exception e) {

      e.printStackTrace();
    }
    return obj;
  }

  public Object retrieveMdte002RejectReasonDataPASA(String rejectReasonCode, String memberId,
                                                    String firstDate, String lastDate) {
    contextAdminBeanCheck();
    Object obj = null;
    try {
      obj =
          adminBeanRemote.retrieveMdte002RejectReasonDataPASA(rejectReasonCode, memberId, firstDate,
              lastDate);
    } catch (Exception e) {
      log.error("Error retrieveMdte002RejectReasonDataPASA: " + e.getMessage());
      e.printStackTrace();
    }
    return obj;
  }

  public Object retrieverRespOutstandMANINTotal() {
    contextCheck();
    Object obj = null;
    try {
      obj = beanRemote.retrieverRespOutstandMANINTotal();
      log.info("retrieverRespOutstandMANINTotal obj--->" + obj);
    } catch (Exception e) {
      log.error("Error on retrieverRespOutstandMANINTotal" + e.getMessage());
      e.printStackTrace();
    }
    return obj;
  }

  public Object retrieverRespOutstandMANAMTotal() {
    contextCheck();
    Object obj = null;
    try {
      obj = beanRemote.retrieverRespOutstandMANAMTotal();
      log.info("retrieverRespOutstandMANAMTotal obj--->" + obj);
    } catch (Exception e) {
      log.error("Error on retrieverRespOutstandMANAMTotal" + e.getMessage());
      e.printStackTrace();
    }
    return obj;
  }

  public static Object retrieveOnlinePASARejectionSummaryPAIN012(String reasonCode, String crBank,
                                                                 String firstDate,
                                                                 String lastDate) {
    contextAdminBeanCheck();
    Object obj = null;
    try {
      obj = adminBeanRemote.retrieveOnlinePASARejectionSummaryPAIN012(reasonCode, crBank, firstDate,
          lastDate);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return obj;
  }

  public Object retrieveOnlinePain012ReasonCodeDataPerBank(String reasonCode, String memberId,
                                                           String debtorBankId, String startDate,
                                                           String lastDate) {
    contextAdminBeanCheck();
    Object obj = null;
    try {
      obj = adminBeanRemote.retrieveOnlinePain012ReasonCodeDataPerBank(reasonCode, memberId,
          debtorBankId, startDate, lastDate);
    } catch (Exception e) {
      log.error("Error retrieveOnlinePain012ReasonCodeDataPerBank: " + e.getMessage());
      e.printStackTrace();
    }
    return obj;
  }

  public Object retrieverRespOutstandMANCNTotal() {
    contextCheck();
    Object obj = null;
    try {
      obj = beanRemote.retrieverRespOutstandMANCNTotal();
      log.info("retrieverRespOutstandMANCNTotal obj--->" + obj);
    } catch (Exception e) {
      log.error("Error on retrieverRespOutstandMANCNTotal" + e.getMessage());
      e.printStackTrace();
    }
    return obj;
  }

  public void testReportsBackEnd(String userName) {
    contextAdminBeanCheck();
    try {
      adminBeanRemote.runDailyReports(userName);
    } catch (Exception e) {
      log.error("Error on testReportsBackEnd" + e.getMessage());
      e.printStackTrace();
    }
  }

  public void testMontlyReportsBackEnd(String userName) {
    contextAdminBeanCheck();
    try {
      adminBeanRemote.runMonthlyReports(userName);
    } catch (Exception e) {
      log.error("Error on testMontlyReportsBackEnd" + e.getMessage());
      e.printStackTrace();
    }
  }

  public List<?> viewSystemStatusSearch(String memberNo, String service) {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.viewSystemStatusSearch(memberNo, service);
    } catch (Exception e) {
      log.error("Error on viewSystemStatusSearch in CONTROLLER" + e.getMessage());
      e.printStackTrace();
    }
    log.info("INSIDE METHOD IN CONTROLLER");
    return list;
  }

  public List<?> viewOpsSlaServicesWithoutCisSodEod(String cis, String sod, String eod) {
    contextAdminBeanCheck();

    List<?> list = null;
    try {
      list = adminBeanRemote.viewOpsSlaServicesWithoutCisSodEod(cis, sod, eod);
    } catch (Exception e) {
      log.error("Error viewOpsSlaServicesWithoutCisSodEod: " + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public boolean saveOrUpdateExtendSlaTimes(Object obj) {
    contextAdminBeanCheck();
    try {
      adminBeanRemote.saveOrUpdateExtendSlaTimes(obj);
      return true;
    } catch (Exception e) {
      log.error("Error extendMANAC: " + e.getMessage());
      e.printStackTrace();
      return false;
    }
  }

  public Object retrieveOpsSlaTimes(String service) {
    contextAdminBeanCheck();
    Object obj = null;
    try {
      obj = adminBeanRemote.retrieveOpsSlaTimes(service);
    } catch (Exception e) {
      log.error("Error retrieveOpsSlaTimes: " + e.getMessage());
      e.printStackTrace();
    }
    return obj;
  }

  public List<?> retrieveAllIncSotEot() {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.retrieveAllIncSotEot();
    } catch (Exception e) {
      log.error("Error on retrieveAllIncSotEot" + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public List<?> retrieveAllIncSotEotBySearch(String memberId, String serviceIdIn, String sotInInd,
                                              String eotInInd) {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list =
          adminBeanRemote.retrieveAllIncSotEotBySearch(memberId, serviceIdIn, sotInInd, eotInInd);
    } catch (Exception e) {
      log.error("Error on retrieveAllIncSotEotBySearch" + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public List<?> findByServiceIdNotNull(String namedQuery, String parameter, String value) {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.findByServiceIdNotNull(namedQuery, parameter, value);
    } catch (Exception e) {
      log.error("Error findByServiceIdNotNull: " + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public List<?> findByServiceIdOutNotNull(String namedQuery, String parameter, String value) {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.findByServiceIdOutNotNull(namedQuery, parameter, value);
    } catch (Exception e) {
      log.error("Error findByServiceIdOutNotNull: " + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public List<?> retrieveAllOutSotEotBySearch(String memberId, String serviceIdOut,
                                              String sotOutInd, String eotOutInd) {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.retrieveAllOutSotEotBySearch(memberId, serviceIdOut, sotOutInd,
          eotOutInd);
    } catch (Exception e) {
      log.error("Error on retrieveAllOutSotEotBySearch" + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public List<?> retrieveAllOutSotEot() {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.retrieveAllOutSotEot();
    } catch (Exception e) {
      log.error("Error on retrieveAllOutSotEot" + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public Object retrieveErrCodeReportName(String reportNr) {
    contextAdminBeanCheck();
    Object obj = null;
    try {
      obj = adminBeanRemote.retrieveErrCodeReportName(reportNr);
    } catch (Exception e) {
      log.error("Error retrieveErrCodeReportName: " + e.getMessage());
      e.printStackTrace();
    }
    return obj;
  }

  //PSMD04
  public void generatePasaBatchRejections(Date frontDate) {
    contextAdminBeanCheck();
    try {
      adminBeanRemote.generatePasaBatchRejections(frontDate);
    } catch (Exception e) {
      log.error("Error generatePasaBatchRejections: " + e.getMessage());
      e.printStackTrace();
    }
  }

  //PSMD06
  public void generatePasaBatchAmendmentsReport(Date frontFromDate, Date frontToDate) {
    contextAdminBeanCheck();
    try {
      adminBeanRemote.generatePasaBatchAmendmentsReport(frontFromDate, frontToDate);
    } catch (Exception e) {
      log.error("Error generatePasaBatchAmendmentsReport: " + e.getMessage());
      e.printStackTrace();
    }
  }

  //PBMD03
  public void generatePerBankBatchMandateRejections() {
    contextAdminBeanCheck();
    try {
      adminBeanRemote.generatePerBankBatchMandateRejections();
    } catch (Exception e) {
      log.error("Error generatePerBankBatchMandateRejections: " + e.getMessage());
      e.printStackTrace();
    }
  }

  //PSMD01
  public void generatePasaBatchOutstandingResponses(Date frontDate) {
    contextAdminBeanCheck();
    try {
      adminBeanRemote.generatePasaBatchOutstandingResponses(frontDate);
    } catch (Exception e) {
      log.error("Error generatePasaBatchOutstandingResponses: " + e.getMessage());
      e.printStackTrace();
    }
  }

  //PBMD01
  public void generatePerBankOutstandingResponses(Date frontEndDate) {
    contextAdminBeanCheck();
    try {
      adminBeanRemote.generatePerBankOutstandingResponses(frontEndDate);
    } catch (Exception e) {
      log.error("Error generatePerBankOutstandingRespomses: " + e.getMessage());
      e.printStackTrace();
    }
  }

  //PBMD04
  public void generateBatchBillableTxnCreditor() {
    contextAdminBeanCheck();
    try {
      adminBeanRemote.generateBatchBillableTxnCreditor();
    } catch (Exception e) {
      log.error("Error generateBatchBillableTxnCreditor: " + e.getMessage());
      e.printStackTrace();
    }
  }

  //PBMD05
  public void generateBatchBillableTxnDebtor() {
    contextAdminBeanCheck();
    try {
      adminBeanRemote.generateBatchBillableTxnDebtor();
    } catch (Exception e) {
      log.error("Error generateBatchBillableTxnDebtor: " + e.getMessage());
      e.printStackTrace();
    }
  }

  //MR018
  public void generateBatchBillableTxnReport() {
    contextAdminBeanCheck();
    try {
      adminBeanRemote.generateBatchBillableTxnReport();
    } catch (Exception e) {
      log.error("Error generateBatchBillableTxnReport: " + e.getMessage());
      e.printStackTrace();
    }
  }

  public void testArchiveProcess() {
    contextAdminBeanCheck();
    try {
      adminBeanRemote.testArchiveProcess();
    } catch (Exception e) {
      log.error("Error testArchiveProcess: " + e.getMessage());
      e.printStackTrace();
    }
  }

  public void generatePHIR02Report(Date fromDate, Date toDate) {
    contextAdminBeanCheck();
    try {
      adminBeanRemote.generatePHIR02Report(fromDate, toDate);
    } catch (Exception e) {
      log.error("Error generatePHIR02Report: " + e.getMessage());
      e.printStackTrace();
    }
  }

  public void testPanelCode() {
    contextAdminBeanCheck();
    try {
      adminBeanRemote.testPanelCode();
    } catch (Exception e) {
      log.error("Error testPanelCode: " + e.getMessage());
      e.printStackTrace();
    }
  }

  //MR020
  public void generateBatchProdVolumesReport(Date frontFromDate, Date frontToDate) {
    contextAdminBeanCheck();
    try {
      adminBeanRemote.generateBatchProdVolumesReport(frontFromDate, frontToDate);
    } catch (Exception e) {
      log.error("Error generateBatchProdVolumesReport: " + e.getMessage());
      e.printStackTrace();
    }
  }

  public List<?> findServicesByNamedQuery(String namedQuery, String parameter, String value) {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.findServicesByNamedQuery(namedQuery, parameter, value);
    } catch (Exception e) {
      log.error("Error findServicesByNamedQuery: " + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  //MR022
  public void generateDailyBatchProdVolumesReport(Date frontEndDate) {
    contextAdminBeanCheck();
    try {
      adminBeanRemote.generateDailyBatchProdVolumesReport(frontEndDate);
    } catch (Exception e) {
      log.error("Error generateDailyBatchProdVolumesReport: " + e.getMessage());
      e.printStackTrace();
    }
  }

  public void generatePerBank5DayOutstanding(Date frontEndDate) {
    contextAdminBeanCheck();
    try {
      adminBeanRemote.generate5DayOutstResp(frontEndDate);
    } catch (Exception e) {
      log.error("Error generatePerBank5DayOutstanding: " + e.getMessage());
      e.printStackTrace();
    }
  }

  //FileSizeLimit
  public List<?> viewFileSizeLimit() {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.retrieveAllOpsFileSizeLimit();
    } catch (Exception e) {
      log.error("Error retrieveAllOpsFileSizeLimit: " + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public List<?> viewFileSizeLimitSearch(String memberId, String subService) {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.viewFileSizeLimitSearch(memberId, subService);
    } catch (Exception e) {
      log.error("Error viewFileSizeLimitSearch: " + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public List<?> viewSysCtrlFileSizeLimit() {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.viewSysCtrlFileSizeLimit();
    } catch (Exception e) {
      log.error("Error viewSysCtrlFileSizeLimit: " + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public List<?> viewSysCtrlFileSizeLimitSearch(String memberId, String subService) {
    contextAdminBeanCheck();
    List<?> list = null;
    try {
      list = adminBeanRemote.viewSysCtrlFileSizeLimitSearch(memberId, subService);
    } catch (Exception e) {
      log.error("Error viewSysCtrlFileSizeLimitSearch: " + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }

  public boolean createSysCtrlFileSizeLimit(Object obj) {
    contextAdminBeanCheck();
    try {
      adminBeanRemote.createSysCtrlFileSizeLimit(obj);
      return true;
    } catch (Exception e) {
      log.error("Error retrievingSysCtrlFileSizeLimit: " + e.getMessage());
      e.printStackTrace();
      return false;
    }
  }

  //RT TXNS BILL
  public boolean generateRtTxnsBill(Date fromDate, Date toDate) {
    contextAdminBeanCheck();
    try {
      adminBeanRemote.generateRtTxnsBill(fromDate, toDate);
    } catch (Exception e) {
      log.error("Error generateRtTxnsBill: " + e.getMessage());
      e.printStackTrace();
    }
    return true;
  }

  public boolean pushRtTxnsBillToBillowner(Date fromDate, Date toDate) {
    contextAdminBeanCheck();
    try {
      adminBeanRemote.pushRtTxnsBillToBillowner(fromDate, toDate);
    } catch (Exception e) {
      log.error("Error pushRtTxnsBillToBillowner: " + e.getMessage());
      e.printStackTrace();
    }
    return true;

  }

  //Interchange Billing
  public boolean generateTT1Pain009(Date fromDate, Date toDate) {
    contextAdminBeanCheck();
    try {
      adminBeanRemote.generateTT1Pain009(fromDate, toDate);
    } catch (Exception e) {
      log.error("Error generateTT1Pain009: " + e.getMessage());
      e.printStackTrace();
    }
    return true;

  }

  public boolean generateTT1MANIRData(Date fromDate, Date toDate) {
    contextAdminBeanCheck();
    try {
      adminBeanRemote.generateTT1MANIRData(fromDate, toDate);
    } catch (Exception e) {
      log.error("Error generateTT1MANIRData: " + e.getMessage());
      e.printStackTrace();
    }
    return true;

  }


  public boolean generateTT3ManirPain009Succ(Date fromDate, Date toDate) {
    contextAdminBeanCheck();
    try {
      adminBeanRemote.generateTT3ManirPain009Succ(fromDate, toDate);
    } catch (Exception e) {
      log.error("Error generateTT3ManirPain009Succ: " + e.getMessage());
      e.printStackTrace();
    }
    return true;

  }


  public boolean generateTT1MandrPain010Succ(Date fromDate, Date toDate) {
    contextAdminBeanCheck();
    try {
      adminBeanRemote.generateTT1MandrPain010Succ(fromDate, toDate);
    } catch (Exception e) {
      log.error("Error generateTT1MandrPain010Succ: " + e.getMessage());
      e.printStackTrace();
    }
    return true;

  }

  public boolean generateTT1ManamManirData(Date fromDate, Date toDate) {
    contextAdminBeanCheck();
    try {
      adminBeanRemote.generateTT1ManamManirData(fromDate, toDate);
    } catch (Exception e) {
      log.error("Error generateTT1ManamManirData: " + e.getMessage());
      e.printStackTrace();
    }
    return true;

  }


  public boolean generateTT3ManIrPain010Succ(Date fromDate, Date toDate) {
    contextAdminBeanCheck();
    try {
      adminBeanRemote.generateTT3ManIrPain010Succ(fromDate, toDate);
    } catch (Exception e) {
      log.error("Error generateTT3ManIrPain010Succ: " + e.getMessage());
      e.printStackTrace();
    }
    return true;

  }

  public boolean generateTT1ManIrPain011Succ(Date fromDate, Date toDate) {
    contextAdminBeanCheck();
    try {
      adminBeanRemote.generateTT1ManIrPain011Succ(fromDate, toDate);
    } catch (Exception e) {
      log.error("Error generateTT1ManIrPain011Succ: " + e.getMessage());
      e.printStackTrace();
    }
    return true;

  }

  public boolean generateBillingExport(Date fromDate, Date toDate, String srinpService) {
    contextAdminBeanCheck();
    try {
      adminBeanRemote.generateBillingExport(fromDate, toDate, srinpService);
    } catch (Exception e) {
      log.error("Error generateBillingExport: " + e.getMessage());
      e.printStackTrace();
    }
    return true;


  }

  public boolean generateTT2TxnsBilling(Date fromDate, Date toDate) {
    contextAdminBeanCheck();
    try {
      adminBeanRemote.generateTT2TxnsBilling(fromDate, toDate);
    } catch (Exception e) {
      log.error("Error generateTT2TxnsBilling: " + e.getMessage());
      e.printStackTrace();
    }
    return true;
  }


  public boolean pushTT2TxnsBillToBillowner(Date fromDate, Date toDate) {
    contextAdminBeanCheck();
    try {
      adminBeanRemote.pushTT2TxnsBillToBillowner(fromDate, toDate);
    } catch (Exception e) {
      log.error("Error pushTT2TxnsBillToBillowner: " + e.getMessage());
      e.printStackTrace();
    }
    return true;

  }


  public boolean generateTT2InterchangeBill(Date fromDate, Date toDate) {
    contextAdminBeanCheck();
    try {
      adminBeanRemote.generateTT2InterchangeBill(fromDate, toDate);
    } catch (Exception e) {
      log.error("Error generateTT2InterchangeBill: " + e.getMessage());
      e.printStackTrace();
    }
    return true;
  }

  public boolean generateSRINPinterchangeBill(Date fromDate, Date toDate) {
    contextAdminBeanCheck();
    try {
      adminBeanRemote.generateSRINPinterchangeBill(fromDate, toDate);
    } catch (Exception e) {
      log.error("Error generateSRINPinterchangeBill: " + e.getMessage());
      e.printStackTrace();
    }
    return true;
  }

  public boolean generateMANRTinterchangeBill(Date fromDate, Date toDate) {
    contextAdminBeanCheck();
    try {
      adminBeanRemote.generateMANRTinterchangeBill(fromDate, toDate);
    } catch (Exception e) {
      log.error("Error generateMANRTinterchangeBill: " + e.getMessage());
      e.printStackTrace();
    }
    return true;
  }

  // PBBIL05
  public void generateBatchTxnBillMonthlyReport(Date fromFrontDate, Date toFrontDate,
                                                boolean frontEndRun) {

    contextAdminBeanCheck();
    try {
      adminBeanRemote.generateBatchTxnBillMonthlyReport(fromFrontDate, toFrontDate, frontEndRun);
    } catch (Exception e) {
      log.error("Error generateBatchTxnBillMonthlyReport: " + e.getMessage());
      e.printStackTrace();
    }
  }

  // PSMD08
  public void generateDailyFileStatsReport(Date fromFrontDate) {

    contextAdminBeanCheck();
    try {
      adminBeanRemote.generateBatchFileStatsReport(fromFrontDate);
    } catch (Exception e) {
      log.error("Error generateDailyFileStatsReport: " + e.getMessage());
      e.printStackTrace();
    }
  }

  //PBMD12
  public void generateExceptionReport(Date frontDate) {
    contextAdminBeanCheck();
    try {
      adminBeanRemote.generateExceptionReport(frontDate);
    } catch (Exception e) {
      log.error("Error generateExceptionReport: " + e.getMessage());
      e.printStackTrace();
    }

  }

//  public Object retrieveReportSeqNr(String reportNr,String memberId)
//  {
//    contextAdminBeanCheck();
//    Object obj = null;
//    try
//    {
//      obj = adminBeanRemote.retrieveReportSeqNr(reportNr,memberId);
//    }
//    catch(Exception e)
//    {
//      log.error("Error retrieveReportSeqNr: " + e.getMessage());
//      e.printStackTrace();
//    }
//    return obj;
//  }
  // ############################DO NOT CODE BELOW THIS SECTION
	// ######################################//

  private static void contextCheck() {
    if (beanRemote == null) {
      beanRemote = Util.getServiceBean();
    }
  }

  private static void contextTimerBeanCheck() {
    if (timerBeanRemote == null) {
      timerBeanRemote = Util.getTimerBean();
    }
  }

  public static void startFileListener() {
    contextCheck();
    beanRemote.startFileListener();
  }

  private static void contextAdminBeanCheck() {
    if (adminBeanRemote == null) {
      adminBeanRemote = Util.getAdminBean();
    }
  }

  private static void contextValidationBeanCheck() {
    if (validationBeanRemote == null) {
      validationBeanRemote = Util.getValidationBean();
    }

  }

  private static void contextQuartzSchedulerBeanCheck() {
    if (quartzSchedulerRemote == null) {
      quartzSchedulerRemote = Util.getQuartzSchedulerBean();
    }
  }

  private static void contextPropertyFileCheck() {
    if (propertyUtilRemote == null) {
      propertyUtilRemote = Util.getPropertyUtil();
    }
  }

  private static void contextReportBeanCheck() {
    if (reportBeanRemote == null) {
      reportBeanRemote = Util.getReportBean();
    }
  }


  // ############################DO NOT CODE BELOW THIS SECTION//
	// ######################################//
}