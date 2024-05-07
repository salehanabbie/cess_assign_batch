package com.bsva;

import com.bsva.beans.GenericDAO;
import com.bsva.businessLogic.OpsFileRegLogic;
import com.bsva.commons.model.BatchAmendmentModel;
import com.bsva.commons.model.BatchOustandingResponseModel;
import com.bsva.commons.model.BatchOustandingResponseReportModel;
import com.bsva.commons.model.BatchRejectedTransactionModel;
import com.bsva.commons.model.MandateAmendModel;
import com.bsva.commons.model.MandateRejectionModel;
import com.bsva.commons.model.MandateResponseOutstandingPerBankModel;
import com.bsva.commons.model.OpsFileRegModel;
import com.bsva.commons.model.OutstandingRespSummaryCountModel;
import com.bsva.entities.BatchAmendmentEntityModel;
import com.bsva.entities.BatchFileStatsReportEntityModel;
import com.bsva.entities.BatchOustandingResponseEntityModel;
import com.bsva.entities.BatchOustandingResponseEntityReportModel;
import com.bsva.entities.BatchRejectedTransactionEntityModel;
import com.bsva.entities.BatchTxnBillReportEntity;
import com.bsva.entities.CasArcCessAssignTxnsEntity;
import com.bsva.entities.CasOpsFileRegEntity;
import com.bsva.entities.CasSysctrlSysParamEntity;
import com.bsva.entities.ExceptionReportEntityModel;
import com.bsva.entities.MandateAmendEntityModel;
import com.bsva.entities.MandateRejectionEntityModel;
import com.bsva.entities.MandateResponseOutstandingPerBankEntityModel;
import com.bsva.entities.MonthlyVolumeCountEntityModel;
import com.bsva.entities.OutstandingRespSummaryCountEntityModel;
import com.bsva.entities.PasaMandateReportEntityModel;
import com.bsva.interfaces.ReportBeanLocal;
import com.bsva.interfaces.ReportBeanRemote;
import com.bsva.translator.ServiceTranslator;
import com.bsva.utils.DateUtil;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import org.apache.log4j.Logger;
import org.hibernate.ObjectNotFoundException;

/**
 * @author SalehaR-2019/09/30
 */
@Stateless
@Remote(ReportBeanRemote.class)
public class ReportBean implements ReportBeanRemote, ReportBeanLocal {
  @EJB
  GenericDAO genericDAO;
  public static Logger log = Logger.getLogger(ReportBean.class);

  @Override
  public List<?> retrieveMndtRespPerBank(String memberId, Date procDate) {
    List<MandateResponseOutstandingPerBankEntityModel> mndtRespOutstandingEntityList =
        new ArrayList<MandateResponseOutstandingPerBankEntityModel>();
    MandateResponseOutstandingPerBankModel mndtRespOutstandingPerBankModel = null;
    List<MandateResponseOutstandingPerBankModel> mndtRespOutstandingModelList =
        new ArrayList<MandateResponseOutstandingPerBankModel>();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");

    Date currentDate;

    if (procDate != null) {
      currentDate = procDate;
    } else {
      currentDate = new Date();
    }

    StringBuffer sb = new StringBuffer();
    //BATCH
    sb.append(
        "SELECT b.MEMBER_NAME AS crdMemberName, a.CREDITOR_BANK AS memberNo, SUBSTR(a" +
            ".IN_FILE_NAME,1,33) AS fileName,'TT2' AS transType, ");
    sb.append("TO_DATE('" + currentDate +
        "','YYYY-MM-DD') - TRUNC(a.CREATED_DATE) AS nrDaysOutstanding, ");
    sb.append("a.MANDATE_REQ_TRAN_ID as mandateReqTransId,a.SERVICE_ID as serviceId ");
    sb.append("FROM CAMOWNER.CAS_OPS_CESS_ASSIGN_TXNS a ");
    sb.append("INNER JOIN CAMOWNER.SYS_CIS_BANK b ON a.CREDITOR_BANK = b.MEMBER_NO ");
    sb.append("WHERE a.PROCESS_STATUS IN ('4','9') ");
    sb.append("AND a.DEBTOR_BANK = '" + memberId + "' ");
    sb.append("AND TRUNC(a.CREATED_DATE) = TO_DATE('" + currentDate + "','YYYY-MM-DD')-1 ");
    sb.append("UNION ");
    sb.append(
        "SELECT b.MEMBER_NAME AS crdMemberName, a.CREDITOR_BANK AS memberNo, SUBSTR(a" +
            ".IN_FILE_NAME,1,33) AS fileName,'TT2' AS transType, ");
    sb.append("TO_DATE('" + currentDate +
        "','YYYY-MM-DD') - TRUNC(a.CREATED_DATE) AS nrDaysOutstanding, ");
    sb.append("a.MANDATE_REQ_TRAN_ID as mandateReqTransId,a.SERVICE_ID as serviceId ");
    sb.append("FROM CAMOWNER.CAS_OPS_CESS_ASSIGN_TXNS a ");
    sb.append("INNER JOIN CAMOWNER.SYS_CIS_BANK b ON a.CREDITOR_BANK = b.MEMBER_NO ");
    sb.append("WHERE a.PROCESS_STATUS IN ('4','9') ");
    sb.append("AND a.DEBTOR_BANK = '" + memberId + "' ");
    sb.append("AND TRUNC(a.CREATED_DATE) = TO_DATE('" + currentDate + "','YYYY-MM-DD')-2 ");
    //REAL TIME
    sb.append("UNION ");
    sb.append(
        "SELECT c.MEMBER_NAME AS crdMemberName,a.INSTRUCTINGAGENTAMS AS memberNo,a" +
            ".ORIGINALMSGIDAMS AS fileName,'TT1' AS transType,TRUNC(current_date) - TO_DATE" +
            "(SUBSTR(a" +
            ".TRANSDATETIME, 1, 8), 'YYYYMMDD') AS nrDaysOutstanding, ");
    sb.append(
        "a.TRANSACTIONIDENTIFIERAMS AS mandateReqTransId,SUBSTR(a.ORIGINALMSGIDAMS,5,5)  AS " +
            "serviceId ");
    sb.append("FROM CAMOWNER.JNL_ACQ a ");
    sb.append("LEFT OUTER JOIN CAMOWNER.JNL_ACQ b ");
    sb.append("ON a.TRANSACTIONIDENTIFIERAMS = b.TRANSACTIONIDENTIFIERAMS ");
    sb.append("and a.MSGTYPEAMS = 'pain.009' and b.msgtypeams = 'pain.012' ");
    sb.append("INNER JOIN CAMOWNER.SYS_CIS_BANK c ");
    sb.append("ON  a.INSTRUCTINGAGENTAMS = c.MEMBER_NO ");
    sb.append(
        "WHERE a.PAYMENTSTATUSGROUPCODEAMS = 'ACCP' and a.msgtypeams = 'pain.009' AND  a" +
            ".INSTRUCTEDAGENTAMS ='" +
            memberId + "' ");
    sb.append(
        "AND NVL(b.TRANSACTIONIDENTIFIERAMS,'not found') = 'not found'AND TO_DATE(SUBSTR(a" +
            ".TRANSDATETIME, 1, 8), 'YYYYMMDD')  = TRUNC(current_date)- 1 ");
    sb.append("UNION ");
    sb.append(
        "SELECT c.MEMBER_NAME AS crdMemberName,a.INSTRUCTINGAGENTAMS AS memberNo,a" +
            ".ORIGINALMSGIDAMS AS fileName,'TT1' AS transType,TRUNC(current_date) - TO_DATE" +
            "(SUBSTR(a" +
            ".TRANSDATETIME, 1, 8), 'YYYYMMDD') AS nrDaysOutstanding, ");
    sb.append(
        "a.TRANSACTIONIDENTIFIERAMS AS mandateReqTransId,SUBSTR(a.ORIGINALMSGIDAMS,5,5)  AS " +
            "serviceId ");
    sb.append("FROM CAMOWNER.JNL_ACQ a ");
    sb.append("LEFT OUTER JOIN CAMOWNER.JNL_ACQ b ");
    sb.append("ON a.TRANSACTIONIDENTIFIERAMS = b.TRANSACTIONIDENTIFIERAMS ");
    sb.append("AND a.MSGTYPEAMS = 'pain.010' AND b.msgtypeams = 'pain.012' ");
    sb.append("INNER JOIN CAMOWNER.SYS_CIS_BANK c ");
    sb.append("ON  a.INSTRUCTINGAGENTAMS = c.MEMBER_NO ");
    sb.append(
        "WHERE a.PAYMENTSTATUSGROUPCODEAMS = 'ACCP' AND a.msgtypeams = 'pain.010' AND  a" +
            ".INSTRUCTEDAGENTAMS ='" +
            memberId + "' ");
    sb.append(
        "AND NVL(b.TRANSACTIONIDENTIFIERAMS,'not found') = 'not found'AND TO_DATE(SUBSTR(a" +
            ".TRANSDATETIME, 1, 8), 'YYYYMMDD')  = TRUNC(current_date)- 1 ");
    sb.append("UNION ");
    sb.append(
        "SELECT c.MEMBER_NAME AS crdMemberName,a.INSTRUCTINGAGENTAMS AS memberNo,a" +
            ".ORIGINALMSGIDAMS AS fileName,'TT1' AS transType,TRUNC(current_date) - TO_DATE" +
            "(SUBSTR(a" +
            ".TRANSDATETIME, 1, 8), 'YYYYMMDD') AS nrDaysOutstanding, ");
    sb.append(
        "a.TRANSACTIONIDENTIFIERAMS AS mandateReqTransId,SUBSTR(a.ORIGINALMSGIDAMS,5,5)  AS " +
            "serviceId ");
    sb.append("FROM CAMOWNER.JNL_ACQ a ");
    sb.append("LEFT OUTER JOIN CAMOWNER.JNL_ACQ b ");
    sb.append("ON a.TRANSACTIONIDENTIFIERAMS = b.TRANSACTIONIDENTIFIERAMS ");
    sb.append("AND a.MSGTYPEAMS = 'pain.011' AND b.msgtypeams = 'pain.012' ");
    sb.append("INNER JOIN CAMOWNER.SYS_CIS_BANK c ");
    sb.append("ON  a.INSTRUCTINGAGENTAMS = c.MEMBER_NO ");
    sb.append(
        "WHERE a.PAYMENTSTATUSGROUPCODEAMS = 'ACCP' AND a.msgtypeams = 'pain.011' AND  a" +
            ".INSTRUCTEDAGENTAMS ='" +
            memberId + "' ");
    sb.append(
        "AND NVL(b.TRANSACTIONIDENTIFIERAMS,'not found') = 'not found'AND TO_DATE(SUBSTR(a" +
            ".TRANSDATETIME, 1, 8), 'YYYYMMDD')  = TRUNC(current_date)- 1 ");

    String sqlQuery = sb.toString();
    log.debug("The Sql query is  ***************" + sqlQuery);
    List<String> scalarList = new ArrayList<String>();
    scalarList.add("crdMemberName");
    scalarList.add("memberNo");
    scalarList.add("fileName");
    scalarList.add("transType");
    scalarList.add("nrDaysOutstanding");
    scalarList.add("mandateReqTransId");
    scalarList.add("serviceId");


    try {
      mndtRespOutstandingEntityList = genericDAO.findBySql(sqlQuery, scalarList,
          MandateResponseOutstandingPerBankEntityModel.class);

      if (mndtRespOutstandingEntityList != null && mndtRespOutstandingEntityList.size() > 0) {
        for (MandateResponseOutstandingPerBankEntityModel localEntityModel :
            mndtRespOutstandingEntityList) {
          MandateResponseOutstandingPerBankModel localModel =
              new MandateResponseOutstandingPerBankModel();
          localModel =
              new ServiceTranslator().translateMndtResponseOutstandingEntityModelToCommonsModel(
                  localEntityModel);
          mndtRespOutstandingModelList.add(localModel);
        }
      }

    } catch (ObjectNotFoundException onfe) {
      log.error("No Object Exists on DB");
    } catch (Exception e) {
      log.error("Error on retrieveMndtRespPerBank :" + e.getMessage());
      e.printStackTrace();
    }

    log.debug("mndtRespOutstandingModelList------->" + mndtRespOutstandingModelList);
    return mndtRespOutstandingModelList;
  }

  @Override
  public Object retrieverRespOutstandForOneDay(String debtorMember, String creditorMember,
                                               String serviceId) {
    List<BatchOustandingResponseModel> oustandingResponseSummaryModelList =
        new ArrayList<BatchOustandingResponseModel>();
    List<BatchOustandingResponseEntityModel> oustandingResponseSummaryEntityList =
        new ArrayList<BatchOustandingResponseEntityModel>();
    BatchOustandingResponseModel oustandingResponseSummaryModel =
        new BatchOustandingResponseModel();

    SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
    Date date = null;

    CasSysctrlSysParamEntity casSysctrlSysParamEntity =
        (CasSysctrlSysParamEntity) genericDAO.findByNamedQuery(
            "CasSysctrlSysParamEntity.findByActiveInd", "activeInd", "Y");
    Date currentDate;

    if (casSysctrlSysParamEntity != null && casSysctrlSysParamEntity.getProcessDate() != null) {
      currentDate = casSysctrlSysParamEntity.getProcessDate();
    } else {
      currentDate = new Date();
    }

    try {
      date = DateUtil.addOneDay(currentDate);
      log.debug("finalCurrentDate**************" + currentDate);
    } catch (ParseException e1) {
      e1.printStackTrace();
    }

    String stringDate = DateUtil.convertDateToString(date, "ddMMYYYY");
    log.debug("stringDate*******:" + stringDate);

    StringBuffer sb = new StringBuffer();
    //		sb.append("SELECT SUBSTR(a.EXTRACT_MSG_ID,13,6) AS debtorBank,c.MEMBER_NAME  as
    //		debtorName,SUBSTR(a.MSG_ID,13,6) AS creditorBank,b.MEMBER_NAME AS creditorName,a
    //		.SERVICE_ID as serviceId, COUNT(*) AS nrOfTxns ");
    //		sb.append("FROM CAMOWNER.MDT_AC_OPS_MNDT_MSG a ");
    //		sb.append("LEFT OUTER JOIN CAMOWNER.SYS_CIS_BANK b ON  SUBSTR(a.MSG_ID,13,6) = b
    //		.MEMBER_NO ");
    //		sb.append("INNER JOIN CAMOWNER.SYS_CIS_BANK c ON SUBSTR(a.EXTRACT_MSG_ID,13,6) = c
    //		.MEMBER_NO ");
    //		sb.append("WHERE a.PROCESS_STATUS IN ('4','9') AND SUBSTR(a.EXTRACT_MSG_ID,13,6) =
    //		'"+debtorMember+"' AND a.SERVICE_ID = '"+serviceId+"' ");
    //		sb.append("AND SUBSTR(a.MSG_ID,13,6)= '"+creditorMember+"' AND TO_CHAR(a.CREATED_DATE,
    //		'ddMMYYYY') ='"+stringDate+"' ");
    //		sb.append("GROUP BY SUBSTR(a.MSG_ID,13,6) ,SUBSTR(a.EXTRACT_MSG_ID,13,6),b.MEMBER_NAME
    //		,a.SERVICE_ID,c.MEMBER_NAME ");

    sb.append(
        "SELECT a.DEBTOR_BANK AS debtorBank, c.MEMBER_NAME  as debtorName, a.CREDITOR_BANK AS " +
            "creditorBank,b.MEMBER_NAME AS creditorName,a.SERVICE_ID as serviceId, COUNT(*) AS " +
            "nrOfTxns ");
    sb.append("FROM CAMOWNER.CAS_OPS_CESS_ASSIGN_TXNS a ");
    sb.append("LEFT OUTER JOIN CAMOWNER.SYS_CIS_BANK b ON CREDITOR_BANK = b.MEMBER_NO ");
    sb.append("INNER JOIN CAMOWNER.SYS_CIS_BANK c ON DEBTOR_BANK = c.MEMBER_NO ");
    sb.append("WHERE a.PROCESS_STATUS IN ('4','9') AND a.DEBTOR_BANK = '" + debtorMember +
        "' AND a.CREDITOR_BANK = '" + creditorMember + "' ");
    sb.append("AND a.SERVICE_ID = '" + serviceId + "' ");
    sb.append("AND TO_CHAR(a.CREATED_DATE, 'ddMMYYYY') ='" + stringDate + "' ");
    sb.append(
        "GROUP BY a.CREDITOR_BANK , a.DEBTOR_BANK, b.MEMBER_NAME ,a.SERVICE_ID,c.MEMBER_NAME ");

    String sqlQuery = sb.toString();
    log.debug("sqlQuery: " + sqlQuery);

    List<String> scalarList = new ArrayList<String>();

    scalarList.add("debtorBank");
    scalarList.add("creditorBank");
    scalarList.add("nrOfTxns");
    scalarList.add("creditorName");
    scalarList.add("serviceId");
    scalarList.add("debtorName");

    oustandingResponseSummaryEntityList =
        genericDAO.findBySql(sqlQuery, scalarList, BatchOustandingResponseEntityModel.class);

    if (oustandingResponseSummaryEntityList != null &&
        oustandingResponseSummaryEntityList.size() > 0) {
      for (BatchOustandingResponseEntityModel oustandingResponseEntityModel :
          oustandingResponseSummaryEntityList) {
        oustandingResponseSummaryModel = new BatchOustandingResponseModel();
        oustandingResponseSummaryModel =
            new ServiceTranslator().translateOustandingResponseSummaryEntityModelToCommonsModel(
                oustandingResponseEntityModel);
        oustandingResponseSummaryModelList.add(oustandingResponseSummaryModel);
      }
    }

    return oustandingResponseSummaryModel;
  }

  @Override
  public Object retrieverRespOutstandForTwoDays(String debtorMember, String creditorMember,
                                                String serviceId) {
    List<BatchOustandingResponseModel> oustandingResponseSummaryModelList =
        new ArrayList<BatchOustandingResponseModel>();
    List<BatchOustandingResponseEntityModel> oustandingResponseSummaryEntityList =
        new ArrayList<BatchOustandingResponseEntityModel>();
    BatchOustandingResponseModel oustandingResponseSummaryModel =
        new BatchOustandingResponseModel();

    SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
    Date currentDate = new Date();
    Date date = null;

    try {

      date = DateUtil.addNoOfDays(currentDate);
      log.debug("finalCurrentDate**************" + currentDate);
    } catch (ParseException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }

    String stringDate = DateUtil.convertDateToString(date, "ddMMYYYY");
    log.debug("stringDate*******:" + stringDate);

    StringBuffer sb = new StringBuffer();
    //		sb.append("SELECT SUBSTR(a.EXTRACT_MSG_ID,13,6) AS debtorBank,c.MEMBER_NAME  as
    //		debtorName,SUBSTR(a.MSG_ID,13,6) AS creditorBank,b.MEMBER_NAME AS creditorName,a
    //		.SERVICE_ID as serviceId, COUNT(*) AS nrOfTxns ");
    //		sb.append("FROM CAMOWNER.MDT_AC_OPS_MNDT_MSG a ");
    //		sb.append("LEFT OUTER JOIN CAMOWNER.SYS_CIS_BANK b ON  SUBSTR(a.MSG_ID,13,6) = b
    //		.MEMBER_NO ");
    //		sb.append("INNER JOIN CAMOWNER.SYS_CIS_BANK c ON SUBSTR(a.EXTRACT_MSG_ID,13,6) = c
    //		.MEMBER_NO ");
    //		sb.append("WHERE a.PROCESS_STATUS IN('4','9') AND SUBSTR(a.EXTRACT_MSG_ID,13,6) =
    //		'"+debtorMember+"' AND a.SERVICE_ID = '"+serviceId+"' ");
    //		sb.append("AND SUBSTR(a.MSG_ID,13,6)= '"+creditorMember+"' AND  TO_CHAR(a
    //		.CREATED_DATE, 'ddMMYYYY') = '"+stringDate+"' ");
    //		sb.append("GROUP BY SUBSTR(a.MSG_ID,13,6) ,SUBSTR(a.EXTRACT_MSG_ID,13,6),b.MEMBER_NAME
    //		,a.SERVICE_ID,c.MEMBER_NAME ");

    sb.append(
        "SELECT a.DEBTOR_BANK AS debtorBank, c.MEMBER_NAME  as debtorName, a.CREDITOR_BANK AS " +
            "creditorBank,b.MEMBER_NAME AS creditorName,a.SERVICE_ID as serviceId, COUNT(*) AS " +
            "nrOfTxns ");
    sb.append("FROM CAMOWNER.CAS_OPS_CESS_ASSIGN_TXNS a ");
    sb.append("LEFT OUTER JOIN CAMOWNER.SYS_CIS_BANK b ON CREDITOR_BANK = b.MEMBER_NO ");
    sb.append("INNER JOIN CAMOWNER.SYS_CIS_BANK c ON DEBTOR_BANK = c.MEMBER_NO ");
    sb.append("WHERE a.PROCESS_STATUS IN ('4','9') AND a.DEBTOR_BANK = '" + debtorMember +
        "' AND a.CREDITOR_BANK = '" + creditorMember + "' ");
    sb.append("AND a.SERVICE_ID = '" + serviceId + "' ");
    sb.append("AND TO_CHAR(a.CREATED_DATE, 'ddMMYYYY') ='" + stringDate + "' ");
    sb.append(
        "GROUP BY a.CREDITOR_BANK , a.DEBTOR_BANK, b.MEMBER_NAME ,a.SERVICE_ID,c.MEMBER_NAME ");

    String sqlQuery = sb.toString();
    log.debug("sqlQuery: " + sqlQuery);

    List<String> scalarList = new ArrayList<String>();
    scalarList.add("debtorBank");
    scalarList.add("creditorBank");
    scalarList.add("nrOfTxns");
    scalarList.add("creditorName");
    scalarList.add("serviceId");
    scalarList.add("debtorName");

    oustandingResponseSummaryEntityList =
        genericDAO.findBySql(sqlQuery, scalarList, BatchOustandingResponseEntityModel.class);

    if (oustandingResponseSummaryEntityList != null &&
        oustandingResponseSummaryEntityList.size() > 0) {
      for (BatchOustandingResponseEntityModel oustandingResponseEntityModel :
          oustandingResponseSummaryEntityList) {
        oustandingResponseSummaryModel = new BatchOustandingResponseModel();
        oustandingResponseSummaryModel =
            new ServiceTranslator().translateOustandingResponseSummaryEntityModelToCommonsModel(
                oustandingResponseEntityModel);
        oustandingResponseSummaryModelList.add(oustandingResponseSummaryModel);
      }
    }
    return oustandingResponseSummaryModel;
  }

  //PBMD03 REPORT
  @Override
  public Object retrievePain012ReasonCodeDataPerBank(String reasonCode, String creditorBank,
                                                     String debtorBank, String startDate,
                                                     String lastDate) {
    List<MandateRejectionModel> mandateReasonCodeModelList = new ArrayList<MandateRejectionModel>();
    List<MandateRejectionEntityModel> mandateReasonCodeEntityList =
        new ArrayList<MandateRejectionEntityModel>();
    MandateRejectionModel mandateReasonCodeModel = new MandateRejectionModel();

    StringBuffer sb = new StringBuffer();

    //		sb.append("SELECT b.MEMBER_ID,c.MEMBER_ID, a.REJECT_REASON_CODE, COUNT(*) AS
    //		rejectReasonCodeCount ");
    //		sb.append("FROM CAMOWNER.MDT_AC_ARC_MNDT_MSG a ");
    //		sb.append("LEFT JOIN CAMOWNER.MDT_AC_ARC_FIN_INST b ON a.MSG_ID = b.MSG_ID AND a
    //		.MANDATE_REQ_TRAN_ID = b.MANDATE_REQ_TRAN_ID ");
    //		sb.append("LEFT JOIN CAMOWNER.MDT_AC_ARC_FIN_INST c ON a.MSG_ID = c.MSG_ID AND a
    //		.MANDATE_REQ_TRAN_ID = c.MANDATE_REQ_TRAN_ID ");
    //		sb.append("WHERE b.FIN_INST_TYPE_ID = 'FI03' AND  c.FIN_INST_TYPE_ID = 'FI04' AND b
    //		.MEMBER_ID = '"+memberId+"' AND c.MEMBER_ID ='"+debtorBankId+"' ");
    //		sb.append("AND a.REJECT_REASON_CODE ='"+reasonCode+"' AND a.SERVICE_ID ='MANAC' AND
    //		TRUNC(a.CREATED_DATE) BETWEEN TO_DATE('"+startDate+"','DDMMYYYY') AND TO_DATE
    //		('"+lastDate+"','DDMMYYYY') ");
    //		sb.append("GROUP BY  b.MEMBER_ID,c.MEMBER_ID,a.REJECT_REASON_CODE ");
    sb.append(
        "SELECT CREDITOR_BANK, DEBTOR_BANK, REJECT_REASON_CODE, COUNT(*) AS rejectReasonCodeCount" +
            " ");
    sb.append("FROM CAMOWNER.MDT_AC_ARC_MANDATE_TXNS ");
    sb.append(
        "WHERE CREDITOR_BANK = '" + creditorBank + "' AND DEBTOR_BANK ='" + debtorBank + "' ");
    sb.append("AND REJECT_REASON_CODE ='" + reasonCode + "' AND SERVICE_ID ='MANAC' ");
    sb.append(
        "AND TRUNC(CREATED_DATE) BETWEEN TO_DATE('" + startDate + "','DDMMYYYY') AND TO_DATE('" +
            lastDate + "','DDMMYYYY') ");
    sb.append("GROUP BY CREDITOR_BANK, DEBTOR_BANK, REJECT_REASON_CODE ");

    String sqlQuery = sb.toString();
    log.debug("sqlQuery MANAC--->: " + sqlQuery);

    List<String> scalarList = new ArrayList<String>();
    scalarList.add("rejectReasonCodeCount");

    mandateReasonCodeEntityList =
        genericDAO.findBySql(sqlQuery, scalarList, MandateRejectionEntityModel.class);

    if (mandateReasonCodeEntityList != null && mandateReasonCodeEntityList.size() > 0) {
      for (MandateRejectionEntityModel mandateReasonCodeEntityModel : mandateReasonCodeEntityList) {
        mandateReasonCodeModel = new MandateRejectionModel();
        mandateReasonCodeModel =
            new ServiceTranslator().translateMandateRejectionEntityModelToCommonsModel(
                mandateReasonCodeEntityModel);
        mandateReasonCodeModelList.add(mandateReasonCodeModel);
      }
    }

    return mandateReasonCodeModel;
  }

  //PSMD04 REPORT
  @Override
  public Object retrievePain012ReasonCodeDataPASA(String reasonCode, String creditorBank,
                                                  String startDate, String lastDate) {
    List<MandateRejectionModel> mandateReasonCodeModelList = new ArrayList<MandateRejectionModel>();
    List<MandateRejectionEntityModel> mandateReasonCodeEntityList =
        new ArrayList<MandateRejectionEntityModel>();
    MandateRejectionModel mandateReasonCodeModel = new MandateRejectionModel();

    StringBuffer sb = new StringBuffer();

    //		sb.append("SELECT b.MEMBER_ID, a.REJECT_REASON_CODE, COUNT(*) AS rejectReasonCodeCount
    //		");
    //		sb.append("FROM CAMOWNER.MDT_AC_ARC_MNDT_MSG a ");
    //		sb.append("LEFT JOIN CAMOWNER.MDT_AC_ARC_FIN_INST b ON a.MSG_ID = b.MSG_ID AND a
    //		.MANDATE_REQ_TRAN_ID = b.MANDATE_REQ_TRAN_ID ");
    //		sb.append("WHERE b.FIN_INST_TYPE_ID = 'FI03' AND b.MEMBER_ID = '"+memberId+"' ");
    //		sb.append("AND a.REJECT_REASON_CODE ='"+reasonCode+"' AND a.SERVICE_ID ='MANAC' AND
    //		TRUNC(a.CREATED_DATE) BETWEEN TO_DATE('"+startDate+"','DDMMYYYY') AND TO_DATE
    //		('"+lastDate+"','DDMMYYYY') ");
    //		sb.append("GROUP BY  b.MEMBER_ID,a.REJECT_REASON_CODE ");

    sb.append("SELECT CREDITOR_BANK, REJECT_REASON_CODE, COUNT(*) AS rejectReasonCodeCount ");
    sb.append("FROM CAMOWNER.MDT_AC_ARC_MANDATE_TXNS ");
    sb.append(
        "WHERE CREDITOR_BANK = '" + creditorBank + "' AND REJECT_REASON_CODE ='" + reasonCode +
            "' AND SERVICE_ID ='MANAC' ");
    sb.append(
        "AND TRUNC(CREATED_DATE) BETWEEN TO_DATE('" + startDate + "','DDMMYYYY') AND TO_DATE('" +
            lastDate + "','DDMMYYYY') ");
    sb.append("GROUP BY CREDITOR_BANK ,REJECT_REASON_CODE ");


    String sqlQuery = sb.toString();
    log.debug("sqlQuery MANAC--->: " + sqlQuery);

    List<String> scalarList = new ArrayList<String>();
    scalarList.add("rejectReasonCodeCount");

    mandateReasonCodeEntityList =
        genericDAO.findBySql(sqlQuery, scalarList, MandateRejectionEntityModel.class);

    if (mandateReasonCodeEntityList != null && mandateReasonCodeEntityList.size() > 0) {
      for (MandateRejectionEntityModel mandateReasonCodeEntityModel : mandateReasonCodeEntityList) {
        mandateReasonCodeModel = new MandateRejectionModel();
        mandateReasonCodeModel =
            new ServiceTranslator().translateMandateRejectionEntityModelToCommonsModel(
                mandateReasonCodeEntityModel);
        mandateReasonCodeModelList.add(mandateReasonCodeModel);
      }
    }

    return mandateReasonCodeModel;
  }

  //PSMD06 REPORT
  @Override
  public Object retrieveAmendReportData(String creditorBank, String amendmetReasonCode,
                                        String firstDate, String lastDate) {
    log.debug("retrieving incoming files using sql statement *************************");
    List<MandateAmendModel> mandateAmendModelList = new ArrayList<MandateAmendModel>();
    List<MandateAmendEntityModel> mandateAmendEntityModelList =
        new ArrayList<MandateAmendEntityModel>();
    MandateAmendModel mandateAmendModel = new MandateAmendModel();

    StringBuffer sb = new StringBuffer();

    //		sb.append("SELECT b.MEMBER_NAME ,SUBSTR(a.msg_id,13,6), a.AMEND_REASON_CODE,COUNT(*)
    //		AS amendReasonCodeCount ");
    //		sb.append("FROM  CAMOWNER.MDT_AC_ARC_MNDT_MSG a  ");
    //		sb.append("LEFT JOIN CAMOWNER.SYS_CIS_BANK b ON SUBSTR(a.msg_id,13,6)= b.MEMBER_NO ");
    //		sb.append("WHERE SUBSTR(a.msg_id,13,6) = '"+memberNo+"' AND a.AMEND_REASON_CODE =
    //		'"+amendmetReasonCode+"'  ");
    //		sb.append("AND TRUNC(a.CREATED_DATE) BETWEEN TO_DATE('"+firstDate+"','DDMMYYYY') AND
    //		TO_DATE('"+lastDate+"','DDMMYYYY') AND a.SERVICE_ID ='MANAM' ");
    //		sb.append("GROUP BY AMEND_REASON_CODE, SUBSTR(a.msg_id,13,6), MEMBER_NAME ");

    sb.append(
        "SELECT b.MEMBER_NAME, a.CREDITOR_BANK, a.AMEND_REASON, COUNT(*) AS amendReasonCodeCount ");
    sb.append("FROM  CAMOWNER.MDT_AC_ARC_MANDATE_TXNS a ");
    sb.append("LEFT JOIN CAMOWNER.SYS_CIS_BANK b ON a.CREDITOR_BANK = b.MEMBER_NO ");
    sb.append("WHERE a.CREDITOR_BANK = '" + creditorBank + "' AND a.AMEND_REASON = '" +
        amendmetReasonCode + "' AND a.SERVICE_ID ='MANAM' ");
    sb.append(
        "AND TRUNC(a.CREATED_DATE) BETWEEN TO_DATE('" + firstDate + "','DDMMYYYY') AND TO_DATE('" +
            lastDate + "','DDMMYYYY') ");
    sb.append("GROUP BY a.AMEND_REASON, a.CREDITOR_BANK, b.MEMBER_NAME ");

    String sqlQuery = sb.toString();
    log.debug("sqlQuery: " + sqlQuery);

    List<String> scalarList = new ArrayList<String>();

    scalarList.add("amendReasonCodeCount");

    mandateAmendEntityModelList =
        genericDAO.findBySql(sqlQuery, scalarList, MandateAmendEntityModel.class);

    if (mandateAmendEntityModelList.size() > 0) {

      for (MandateAmendEntityModel localModelEntity : mandateAmendEntityModelList) {
        mandateAmendModel = new MandateAmendModel();
        mandateAmendModel =
            new ServiceTranslator().translateMandateAmend16EntityModelToCommonsModel(
                localModelEntity);
        mandateAmendModelList.add(mandateAmendModel);
        //localModel = mandateAmendModel;
        log.debug("The List list has this information #####################" +
            mandateAmendEntityModelList);
      }
    }

    log.debug("The  Amendmodel has this information #####################" + mandateAmendModel);
    return mandateAmendModel;
  }

  @Override
  public Object retrieveReasonCodeTotal(String amendmetReasonCode, String firstDate,
                                        String lastDate) {
    log.debug("retrieving incoming files using sql statement *************************");
    List<MandateAmendModel> mandateAmendModelList = new ArrayList<MandateAmendModel>();
    List<MandateAmendEntityModel> mandateAmendEntityModelList =
        new ArrayList<MandateAmendEntityModel>();
    MandateAmendModel mandateAmendModel = new MandateAmendModel();

    StringBuffer sb = new StringBuffer();

    sb.append("SELECT SUM(amendReasonCodeCount) AS amendReasonCodeCount FROM ");
    sb.append(
        "(SELECT b.MEMBER_NAME ,a.CREDITOR_BANK, a.AMEND_REASON,COUNT(*) AS amendReasonCodeCount ");
    sb.append("FROM  CAMOWNER.MDT_AC_ARC_MANDATE_TXNS a  ");
    sb.append("LEFT JOIN CAMOWNER.SYS_CIS_BANK b ON a.CREDITOR_BANK = b.MEMBER_NO ");
    sb.append("WHERE a.AMEND_REASON = '" + amendmetReasonCode + "'  ");
    sb.append(
        "AND TRUNC(a.CREATED_DATE) BETWEEN TO_DATE('" + firstDate + "','DDMMYYYY') AND TO_DATE('" +
            lastDate + "','DDMMYYYY') AND a.SERVICE_ID ='MANAM' ");
    sb.append("GROUP BY AMEND_REASON, a.CREDITOR_BANK, MEMBER_NAME) ");


    String sqlQuery = sb.toString();
    log.info("sqlQuery: " + sqlQuery);

    List<String> scalarList = new ArrayList<String>();

    scalarList.add("amendReasonCodeCount");

    mandateAmendEntityModelList =
        genericDAO.findBySql(sqlQuery, scalarList, MandateAmendEntityModel.class);

    if (mandateAmendEntityModelList.size() > 0) {

      for (MandateAmendEntityModel localModelEntity : mandateAmendEntityModelList) {
        mandateAmendModel = new MandateAmendModel();
        mandateAmendModel =
            new ServiceTranslator().translateMandateAmend16EntityModelToCommonsModel(
                localModelEntity);
        mandateAmendModelList.add(mandateAmendModel);
        //localModel = mandateAmendModel;
        log.debug("The List list has this information #####################" +
            mandateAmendEntityModelList);

      }
    }

    log.debug("The  Amendmodel has this information #####################" + mandateAmendModel);
    return mandateAmendModel;
  }

  public List<?> retrievePasaHIRMandateInfo(String fromDate, String toDate, String service,
                                            boolean expiredTxns) {
    log.debug("fromDate ==> " + fromDate);
    log.debug("toDate ==> " + toDate);
    List<PasaMandateReportEntityModel> pasaInitInfoList =
        new ArrayList<PasaMandateReportEntityModel>();

    StringBuffer sb = new StringBuffer();

    if (expiredTxns) {
      sb.append(
          "SELECT MANDATE_REQ_TRAN_ID AS mrti, MSG_ID AS msgId, CREDITOR_BANK AS creditorBank, " +
              "DEBTOR_BANK AS debtorBank, TO_CHAR(TRUNC(ARCHIVE_DATE), 'YYYY-MM-DD') AS " +
              "creationDate, ");
    } else {
      sb.append(
          "SELECT MANDATE_REQ_TRAN_ID AS mrti, MSG_ID AS msgId, CREDITOR_BANK AS creditorBank, " +
              "DEBTOR_BANK AS debtorBank, TO_CHAR(TRUNC(CREATED_DATE), 'YYYY-MM-DD') AS " +
              "creationDate, ");
    }

    sb.append(
        "AUTH_TYPE AS authType, LOCAL_INSTR_CD AS dbtrAuthReq, SEQUENCE_TYPE AS instOcc, " +
            "CRED_ABB_SHORT_NAME as cdtrAbbShtNm, PROCESS_STATUS as processStatus,'ACH' AS " +
            "dataSource ");

    if (service.equalsIgnoreCase("MANAM")) {
      sb.append(",AMEND_REASON as reason, CONTRACT_REF AS contRefNum ");
    } else {
      sb.append(",CONTRACT_REF AS contRefNum ");
    }

    if (service.equalsIgnoreCase("MANCN")) {
      sb.append(", CANCEL_REASON as reason ");
    }

    sb.append("FROM CAMOWNER.MDT_AC_ARC_MANDATE_TXNS ");

    if (expiredTxns) {
      sb.append("WHERE SERVICE_ID = '" + service +
          "' AND PROCESS_STATUS = '8' AND TRUNC(ARCHIVE_DATE) BETWEEN TO_DATE('" + fromDate +
          "', 'YYYY-MM-DD') AND TO_DATE('" + toDate + "', 'YYYY-MM-DD') ");
    } else {
      sb.append("WHERE SERVICE_ID = '" + service + "' AND TRUNC(CREATED_DATE) BETWEEN TO_DATE('" +
          fromDate + "', 'YYYY-MM-DD') AND TO_DATE('" + toDate + "', 'YYYY-MM-DD') ");
    }

    sb.append(
        "ORDER BY creationDate, creditorBank, debtorBank, authType, contRefNum, dbtrAuthReq, " +
            "instOcc, cdtrAbbShtNm ");

    String sqlQuery = sb.toString();
    log.debug(service + " Mandates sqlQuery: " + sqlQuery);

    List<String> scalarList = new ArrayList<String>();
    scalarList.add("mrti");
    scalarList.add("msgId");
    scalarList.add("creditorBank");
    scalarList.add("debtorBank");
    scalarList.add("creationDate");
    scalarList.add("authType");
    scalarList.add("contRefNum");
    scalarList.add("dbtrAuthReq");
    scalarList.add("instOcc");
    scalarList.add("cdtrAbbShtNm");
    scalarList.add("processStatus");
    scalarList.add("dataSource");
    if (service.equalsIgnoreCase("MANAM") || service.equalsIgnoreCase("MANCN")) {
      scalarList.add("reason");
    }
    log.debug("scalarList: " + scalarList);

    pasaInitInfoList =
        genericDAO.findBySql(sqlQuery, scalarList, PasaMandateReportEntityModel.class);

    return pasaInitInfoList;
  }

  public List<?> retrieveAuthStatus(String mrti, String service) {
    List<PasaMandateReportEntityModel> matchedManacList =
        new ArrayList<PasaMandateReportEntityModel>();

    StringBuffer sb = new StringBuffer();

    if (service.equalsIgnoreCase("MANAM")) {
      sb.append(
          "SELECT REJECT_REASON_CODE as reason, LOCAL_INSTR_CD as authType, AUTH_STATUS_IND as " +
              "authStatus, TO_CHAR(CREATED_DATE, 'YYYY-MM-DD') as creationDate ");
    } else {
      sb.append(
          "SELECT REJECT_REASON_CODE as reason, AUTH_STATUS_IND as authStatus, TO_CHAR" +
              "(CREATED_DATE, 'YYYY-MM-DD') as creationDate ");
    }
    sb.append("FROM CAMOWNER.MDT_AC_ARC_MANDATE_TXNS a ");
    sb.append("WHERE MANDATE_REQ_TRAN_ID = '" + mrti + "' AND SERVICE_ID = 'MANAC' ");

    String sqlQuery = sb.toString();
    log.debug("sqlQuery: " + sqlQuery);

    List<String> scalarList = new ArrayList<String>();
    scalarList.add("reason");
    scalarList.add("authStatus");
    scalarList.add("creationDate");
    if (service.equalsIgnoreCase("MANAM")) {
      scalarList.add("authType");
    }


    log.debug("scalarList: " + scalarList);

    matchedManacList =
        genericDAO.findBySql(sqlQuery, scalarList, PasaMandateReportEntityModel.class);

    return matchedManacList;
  }

  public Object retrievePain012FromArchive(String serviceId, String mrti, String accepted) {
    CasArcCessAssignTxnsEntity pain012Entity = new CasArcCessAssignTxnsEntity();

    try {
      HashMap<String, Object> parameters = new HashMap<String, Object>();
      parameters.put("mdtAcArcMandateTxnsEntityPK.mandateReqTranId", mrti);
      parameters.put("serviceId", serviceId);
      parameters.put("acceptedInd", accepted);

      log.debug("---------------sparameters: ------------------" + parameters.toString());
      pain012Entity =
          (CasArcCessAssignTxnsEntity) genericDAO.findByCriteria(CasArcCessAssignTxnsEntity.class,
              parameters);
    } catch (NullPointerException npe) {
      log.error("NullPointer exception :" + npe.getMessage());
    } catch (ObjectNotFoundException onfe) {
      log.error("No Object Exists on DB");
    } catch (Exception e) {
      log.error("Error on retrievePain012FromArchive: " + e.getMessage());
      e.printStackTrace();
    }

    return pain012Entity;
  }

  public List<?> retrieveDebtorValidationErrors(String fromDate, String toDate, String service,
                                                String serviceNmId) {
    List<PasaMandateReportEntityModel> pasaInitInfoList =
        new ArrayList<PasaMandateReportEntityModel>();

    StringBuffer sb = new StringBuffer();

    log.debug("service ===> " + service);
    log.debug("serviceNmId ===> " + serviceNmId);

    //		if(service.equalsIgnoreCase("MANAM"))
    //		{
    //			sb.append("SELECT a.TXN_ID AS mrti, a.INST_ID as creditorBank, b.INSTG_AGT as
    //			debtorBank, TO_CHAR(a.ARCHIVE_DATE, 'YYYY-MM-DD') AS creationDate,c.AUTH_TYPE AS
    //			authType, 'VALIDATION_FAILURE' AS status, ");
    //			sb.append("a.ERROR_CODE AS errorCode, 'ACH' AS dataSource, c.CONTRACT_REF AS
    //			contRefNum,c.LOCAL_INSTR_CD AS dbtrAuthReq, c.SEQUENCE_TYPE AS instOcc, c
    //			.CRED_ABB_SHORT_NAME as cdtrAbbShtNm ");
    //			sb.append("FROM CAMOWNER.MDT_AC_ARC_CONF_DETAILS a ");
    //			sb.append("JOIN CAMOWNER.MDT_AC_ARC_CONF_HDRS b ON a.CONF_HDR_SEQ_NO = b
    //			.SYSTEM_SEQ_NO ");
    //			sb.append("JOIN CAMOWNER.MDT_AC_ARC_MANDATE_TXNS c ON c.MANDATE_REQ_TRAN_ID = a
    //			.TXN_ID ");
    //			sb.append("WHERE TRUNC(a.ARCHIVE_DATE) BETWEEN TO_DATE('"+fromDate+"',
    //			'YYYY-MM-DD') AND TO_DATE('"+toDate+"', 'YYYY-MM-DD') ");
    //			sb.append("AND a.ORGNL_MSG_TYPE = '"+serviceNmId+"' AND a.ERROR_TYPE = 'TXN'AND a
    //			.TXN_STATUS = 'RJCT' and c.SERVICE_ID = '"+service+"' ");
    //			sb.append("ORDER BY creationDate, mrti, creditorBank, debtorBank, authType,
    //			contRefNum, dbtrAuthReq, instOcc, cdtrAbbShtNm,errorCode ");
    //		}
    //		else
    //		{
    sb.append(
        "SELECT a.TXN_ID AS mrti, a.INST_ID as creditorBank, b.INSTG_AGT as debtorBank, TO_CHAR(a" +
            ".ARCHIVE_DATE, 'YYYY-MM-DD') AS creationDate,c.AUTH_TYPE AS authType," +
            "'VALIDATION_FAILURE' AS status, ");
    sb.append(
        "a.ERROR_CODE AS errorCode, 'ACH' AS dataSource,c.CONTRACT_REF AS contRefNum,c" +
            ".LOCAL_INSTR_CD AS dbtrAuthReq, c.SEQUENCE_TYPE AS instOcc, c.CRED_ABB_SHORT_NAME as" +
            " " +
            "cdtrAbbShtNm ");
    sb.append("FROM CAMOWNER.MDT_AC_ARC_CONF_DETAILS a ");
    sb.append("JOIN CAMOWNER.MDT_AC_ARC_CONF_HDRS b ON a.CONF_HDR_SEQ_NO = b.SYSTEM_SEQ_NO ");
    sb.append("JOIN CAMOWNER.MDT_AC_ARC_MANDATE_TXNS c ON c.MANDATE_REQ_TRAN_ID = a.TXN_ID ");
    sb.append("WHERE TRUNC(a.ARCHIVE_DATE) BETWEEN TO_DATE('" + fromDate +
        "', 'YYYY-MM-DD') AND TO_DATE('" + toDate + "', 'YYYY-MM-DD') ");
    sb.append("AND a.ORGNL_MSG_TYPE = '" + serviceNmId +
        "' AND a.ERROR_TYPE = 'TXN'AND a.TXN_STATUS = 'RJCT' AND c.SERVICE_ID = '" + service +
        "' ");
    sb.append(
        "ORDER BY creationDate, mrti, creditorBank, debtorBank, authType, contRefNum, " +
            "dbtrAuthReq, instOcc, cdtrAbbShtNm,errorCode ");
    //		}

    String sqlQuery = sb.toString();
    log.debug(service + " Debtor Validations sqlQuery: " + sqlQuery);

    List<String> scalarList = new ArrayList<String>();
    scalarList.add("mrti");
    scalarList.add("creditorBank");
    scalarList.add("debtorBank");
    scalarList.add("creationDate");
    scalarList.add("authType");
    scalarList.add("contRefNum");
    scalarList.add("dbtrAuthReq");
    scalarList.add("instOcc");
    scalarList.add("cdtrAbbShtNm");
    scalarList.add("status");
    scalarList.add("errorCode");
    scalarList.add("dataSource");
    log.debug("scalarList: " + scalarList);

    pasaInitInfoList =
        genericDAO.findBySql(sqlQuery, scalarList, PasaMandateReportEntityModel.class);
    log.debug("pasaInitInfoList ==> " + pasaInitInfoList);

    return pasaInitInfoList;
  }

  @Override
  public List<?> retrievePBMD01Data(String procDate) {
    List<MandateResponseOutstandingPerBankEntityModel> mndtRespOutstandingEntityList =
        new ArrayList<MandateResponseOutstandingPerBankEntityModel>();

    StringBuffer sb = new StringBuffer();

    sb.append(
        "SELECT b.MEMBER_NAME AS crdMemberName ,a.CREDITOR_BANK AS crdMemberNo ,c.member_name as " +
            "dbtrMemberName ,a.debtor_bank as dbtrMemberNo ");
//		2020/05/12-SalehaR: CHG-196717: Use Extract File Name
//		sb.append(",SUBSTR(a.IN_FILE_NAME,1,33) AS fileName ,'TT2' AS transType ,TO_DATE
//		('"+procDate+"','YYYY-MM-DD') - TRUNC(a.CREATED_DATE) AS nrDaysOutstanding ");
    sb.append(
        ",SUBSTR(a.EXTRACT_FILE_NAME,1,33) AS fileName ,'TT2' AS transType ,TO_DATE('" + procDate +
            "','YYYY-MM-DD') - TRUNC(a.CREATED_DATE) AS nrDaysOutstanding ");
    sb.append(",a.MANDATE_REQ_TRAN_ID as mandateReqTransId ,a.SERVICE_ID as serviceId ");
    sb.append("FROM CAMOWNER.CAS_OPS_CESS_ASSIGN_TXNS a ");
    sb.append("INNER JOIN CAMOWNER.SYS_CIS_BANK b ON a.CREDITOR_BANK = b.MEMBER_NO ");
    sb.append("INNER JOIN CAMOWNER.SYS_CIS_BANK c on A.DEBTOR_BANK = c.member_no ");
    sb.append("left outer join manowner.jnl_acq d ");
    sb.append(
        "on a.mandate_req_tran_id = d.transactionidentifierams and cancellationreasonams in" +
            "('MICN', 'MACN') ");
    sb.append(
        "WHERE a.PROCESS_STATUS IN ('4','9') AND NVL(d.transactionidentifierams,'NF') = 'NF' AND " +
            "(TRUNC(a.CREATED_DATE) = TO_DATE('" +
            procDate + "','YYYY-MM-DD')-1 OR TRUNC(a.CREATED_DATE) = TO_DATE('" + procDate +
            "','YYYY-MM-DD')-2) ");

    String sqlQuery = sb.toString();
    log.debug("PBMD01 Query " + sqlQuery);
    List<String> scalarList = new ArrayList<String>();
    scalarList.add("crdMemberName");
    scalarList.add("crdMemberNo");
    scalarList.add("dbtrMemberName");
    scalarList.add("dbtrMemberNo");
    scalarList.add("fileName");
    scalarList.add("transType");
    scalarList.add("nrDaysOutstanding");
    scalarList.add("mandateReqTransId");
    scalarList.add("serviceId");

    try {
      mndtRespOutstandingEntityList = genericDAO.findBySql(sqlQuery, scalarList,
          MandateResponseOutstandingPerBankEntityModel.class);
    } catch (ObjectNotFoundException onfe) {
      log.error("No Object Exists on DB");
    } catch (Exception e) {
      log.error("Error on retrievePBMD01Data :" + e.getMessage());
      e.printStackTrace();
    }
    return mndtRespOutstandingEntityList;
  }

  @Override
  public List<?> retrievePBMD09Data(String procDate, String fromProcDate, String time) {
    List<MandateResponseOutstandingPerBankEntityModel> mndtRespOutstandingEntityList =
        new ArrayList<MandateResponseOutstandingPerBankEntityModel>();

    StringBuffer sb = new StringBuffer();
    //String time = "22:30:00";

//		sb.append("with pain910 as ");
//		sb.append("(select distinct INSTRUCTEDAGENTAMS as DEBTOR_MEMBER_NO, ");
//		sb.append("INSTRUCTINGAGENTAMS as CREDITOR_MEMBER_NO, ");
//		sb.append("originalmsgidams as MESSAGEID, ");
//		sb.append("substr(originalmsgidams,5,5) as TXNTP, ");
//		sb.append("TO_DATE(SUBSTR(TRANSDATETIME),'YYYY-MM-DD HH24:MI:SS') AS TXNDTE, ");
//		sb.append("TRANSACTIONIDENTIFIERAMS as MNDTREQTRANSID, ");
//		sb.append("SERVICEIDAMS AS SERVICE_ID, ");
//		sb.append("'pain910' as MSGTYPEAMS,paymentstatusgroupcodeams  ");
//		sb.append("from manowner.JNL_ACQ ");
//		sb.append("where TO_DATE(SUBSTR(TRANSDATETIME),'YYYYMMDDHH24MISS') between TO_DATE
//		('"+procDate+" "+time+"' ,'YYYY-MM-DD HH24:MI:SS')-1 and TO_DATE('"+procDate+" "+time+"',
//		'YYYY-MM-DD HH24:MI:SS') and (MSGTYPEAMS = 'pain.009' and resultcode = 0 and MTI in (5501,
//		5503) and serviceidams not in ('MANIR','STMDF','STMVF') or (msgtypeams = 'pain.010' and
//		MTI = 5503 and resultcode = 0 and serviceidams not in ('MANIR','STMDF','STMVF'))) ");
//		sb.append("and PAYMENTSTATUSGROUPCODEAMS <> 'RJCT') ");
//		sb.append("pain11 as ");
//		sb.append("(select distinct TRANSACTIONIDENTIFIERAMS, ");
//		sb.append("INSTRUCTEDAGENTAMS, ");
//		sb.append("MSGTYPEAMS,paymentstatusgroupcodeams ");
//		sb.append("from manowner.JNL_ACQ ");
//		sb.append("where TO_DATE(SUBSTR(TRANSDATETIME),'YYYYMMDDHH24MISS') between TO_DATE
//		('"+procDate+" " +time+"','YYYY-MM-DD HH24:MI:SS')-1 and TO_DATE('"+procDate+" "+time+"',
//		'YYYY-MM-DD HH24:MI:SS') and MSGTYPEAMS = 'pain.011' and cancellationreasonams in ('MICN',
//		'MACN')), ");
//		sb.append("pain12 as ");
//		sb.append("(select distinct TRANSACTIONIDENTIFIERAMS, ");
//		sb.append("INSTRUCTEDAGENTAMS, ");
//		sb.append("MSGTYPEAMS ");
//		sb.append("from manowner.JNL_ACQ ");
//		sb.append("where TO_DATE(SUBSTR(TRANSDATETIME),'YYYYMMDDHH24MISS') between TO_DATE
//		('"+procDate+ " "+time+"','YYYY-MM-DD HH24:MI:SS') -1 and TO_DATE('"+procDate+" "+time+"',
//		'YYYY-MM-DD HH24:MI:SS') and MSGTYPEAMS = 'pain.012') ");
//		sb.append("select d.member_name as crdMemberName, a.DEBTOR_MEMBER_NO AS dbtrMemberNo, ");
//		sb.append("a.CREDITOR_MEMBER_NO AS crdMemberNo, ");
//		sb.append("a.MESSAGEID AS fileName,'TT1' as transType, ");
//		sb.append("a.TXNTP AS serviceId, ");
//		sb.append("a.TXNDTE, ");
//		sb.append("a.MNDTREQTRANSID AS mandateReqTransId, ");
//		sb.append("a.SERVICE_ID ");
//		sb.append("from pain910 a ");
//		sb.append("left outer join pain11 b on ");
//		sb.append("a.MNDTREQTRANSID = b.TRANSACTIONIDENTIFIERAMS ");
//		sb.append("left outer join pain12 c on ");
//		sb.append("a.MNDTREQTRANSID = c.TRANSACTIONIDENTIFIERAMS ");
//		sb.append("left outer join manowner.sys_cis_bank d ");
//		sb.append("on a.creditor_member_no = d.member_no ");
//		sb.append("where nvl(c.TRANSACTIONIDENTIFIERAMS,'nopain12') = 'nopain12' ");
//		sb.append("and nvl(b.TRANSACTIONIDENTIFIERAMS,'nopain11') = 'nopain11' and a
//		.paymentstatusgroupcodeams <> 'RJCT' ");


    sb.append("with pain910 as ");
    sb.append("(select distinct INSTRUCTEDAGENTAMS as DEBTOR_MEMBER_NO, ");
    sb.append("INSTRUCTINGAGENTAMS as CREDITOR_MEMBER_NO, ");
    sb.append("originalmsgidams as MESSAGEID, ");
    sb.append("substr(originalmsgidams,5,5) as TXNTP, ");
    sb.append("TO_DATE(TRANSDATETIME,'YYYYMMDDHH24MISS') AS TXNDTE, ");
    sb.append("TRANSACTIONIDENTIFIERAMS as MNDTREQTRANSID, ");
    sb.append("SERVICEIDAMS AS SERVICE_ID, 'pain910' as MSGTYPEAMS ");
    sb.append("from manowner.JNL_ACQ ");
    sb.append(
        "where to_date(transdatetime, 'YYYYMMDDHH24MISS') between TO_DATE('" + procDate + " " +
            time + "','YYYY-MM-DD HH24:MI:SS') - 1 and TO_DATE('" + procDate + " " + time +
            "','YYYY-MM-DD HH24:MI:SS') and (MSGTYPEAMS = 'pain.009' and resultcode = 0 and MTI " +
            "in (5501, 5503) and serviceidams not in ('MANIR','STMDF','STMVF') or (msgtypeams = " +
            "'pain.010' and MTI = 5503 and resultcode = 0 and serviceidams not in ('MANIR'," +
            "'STMDF','STMVF'))) ");
    sb.append("and PAYMENTSTATUSGROUPCODEAMS <> 'RJCT'), ");
    sb.append("pain11 as ");
    sb.append("(select distinct TRANSACTIONIDENTIFIERAMS,INSTRUCTEDAGENTAMS,MSGTYPEAMS ");
    sb.append("from manowner.JNL_ACQ ");
    sb.append(
        "where to_date(transdatetime, 'YYYYMMDDHH24MISS') between TO_DATE('" + procDate + " " +
            time + "','YYYY-MM-DD HH24:MI:SS') - 1 and TO_DATE('" + procDate + " " + time +
            "','YYYY-MM-DD HH24:MI:SS') and MSGTYPEAMS = 'pain.011' and cancellationreasonams in " +
            "('MICN', 'MACN') ");
    sb.append("and PAYMENTSTATUSGROUPCODEAMS <> 'RJCT'), ");
    sb.append("pain12 as ");
    sb.append("(select distinct TRANSACTIONIDENTIFIERAMS, ");
    sb.append("INSTRUCTEDAGENTAMS, ");
    sb.append("MSGTYPEAMS ");
    sb.append("from manowner.JNL_ACQ ");
    sb.append(
        "where to_date(transdatetime, 'YYYYMMDDHH24MISS') between TO_DATE('" + procDate + " " +
            time + "','YYYY-MM-DD HH24:MI:SS') - 1 and TO_DATE('" + procDate + " " + time +
            "','YYYY-MM-DD HH24:MI:SS') and MSGTYPEAMS = 'pain.012' and serviceidams <> 'STMAN') ");
    sb.append("select d.member_name as crdMemberName, a.DEBTOR_MEMBER_NO AS dbtrMemberNo, ");
    sb.append("a.CREDITOR_MEMBER_NO AS crdMemberNo, ");
    sb.append("a.MESSAGEID AS fileName,'TT1' as transType, ");
    sb.append("a.TXNTP AS serviceId, ");
    sb.append("a.TXNDTE, ");
    sb.append("a.MNDTREQTRANSID AS mandateReqTransId, ");
    sb.append("a.SERVICE_ID ");
    sb.append("from pain910 a ");
    sb.append("left outer join pain11 b on ");
    sb.append("a.MNDTREQTRANSID = b.TRANSACTIONIDENTIFIERAMS ");
    sb.append("left outer join pain12 c on ");
    sb.append("a.MNDTREQTRANSID = c.TRANSACTIONIDENTIFIERAMS ");
    sb.append("left outer join manowner.sys_cis_bank d ");
    sb.append("on a.creditor_member_no = d.member_no ");
    sb.append("where nvl(c.TRANSACTIONIDENTIFIERAMS,'nopain12') = 'nopain12' ");
    sb.append("and nvl(b.TRANSACTIONIDENTIFIERAMS,'nopain11') = 'nopain11' ");
    sb.append("order by txndte ");


    String sqlQuery = sb.toString();
    log.info("PBMD09 Query " + sqlQuery);
    List<String> scalarList = new ArrayList<String>();
    scalarList.add("crdMemberName");
    scalarList.add("crdMemberNo");
    scalarList.add("dbtrMemberNo");
    scalarList.add("fileName");
    scalarList.add("transType");
    scalarList.add("mandateReqTransId");
    scalarList.add("serviceId");

    try {
      mndtRespOutstandingEntityList = genericDAO.findBySql(sqlQuery, scalarList,
          MandateResponseOutstandingPerBankEntityModel.class);
      if (mndtRespOutstandingEntityList != null && mndtRespOutstandingEntityList.size() > 0) {
        log.info("PBMD09 results: " + mndtRespOutstandingEntityList.size());
      }
    } catch (ObjectNotFoundException onfe) {
      log.error("No Object Exists on DB");
    } catch (Exception e) {
      log.error("Error on retrievePBMD09Data :" + e.getMessage());
      e.printStackTrace();
    }
    return mndtRespOutstandingEntityList;
  }

  public List<?> retrieveMarkOffFiles(String direction) {
    List<OpsFileRegModel> markoffFilesList = new ArrayList<OpsFileRegModel>();

    StringBuffer sb = new StringBuffer();

    sb.append("SELECT a.FILE_NAME AS fileName, a.PROCESS_DATE as processDate, ");
    sb.append(
        "CASE WHEN a.STATUS = 'LE' THEN a.STATUS||'-PARTIALLY LOADED' ELSE a.STATUS||'-'||b" +
            ".STATUS_DESCRIPTION END AS status ");
    sb.append("FROM CAMOWNER.CAS_OPS_FILE_REG a ");
    sb.append("JOIN CAMOWNER.CAS_SYSCTRL_FILE_STATUS b ON b.STATUS = a.STATUS ");
    if (direction.equalsIgnoreCase("I")) {
      sb.append("WHERE (SUBSTR(a.FILE_NAME, 4,5) = 'MANDB') ");
    } else {
      sb.append(
          "WHERE (SUBSTR(a.FILE_NAME, 4,5) = 'ST994') OR (SUBSTR(a.FILE_NAME, 4,5) = 'MANDC') ");
    }
    sb.append("ORDER BY a.FILE_NAME ");

    String sqlQuery = sb.toString();
    log.info("sqlQuery: " + sqlQuery);

    List<String> scalarList = new ArrayList<String>();
    scalarList.add("fileName");
    scalarList.add("processDate");
    scalarList.add("status");

    List<CasOpsFileRegEntity> markoffFilesEntityList =
        genericDAO.findBySql(sqlQuery, scalarList, CasOpsFileRegEntity.class);

    if (markoffFilesEntityList != null && markoffFilesEntityList.size() > 0) {
      OpsFileRegLogic opsFileRegLogic = new OpsFileRegLogic();

      for (CasOpsFileRegEntity localEntity : markoffFilesEntityList) {
        OpsFileRegModel opsFileRegModel = new OpsFileRegModel();
        opsFileRegModel = opsFileRegLogic.retrieveDelDelivery(localEntity);

        markoffFilesList.add(opsFileRegModel);
      }
    }

    return markoffFilesList;
  }

  @Override
  public List<?> retrieveExpiredTxnData(String procDate) {
    List<MandateResponseOutstandingPerBankEntityModel> mndtRespOutstandingEntityList =
        new ArrayList<MandateResponseOutstandingPerBankEntityModel>();

    StringBuffer sb = new StringBuffer();

    sb.append(
        "SELECT b.MEMBER_NAME AS crdMemberName ,a.CREDITOR_BANK AS crdMemberNo ,c.member_name as " +
            "dbtrMemberName ,a.debtor_bank as dbtrMemberNo ");
    sb.append(",SUBSTR(a.IN_FILE_NAME,1,33) AS fileName ,'TT2' AS transType ");
    sb.append(",a.MANDATE_REQ_TRAN_ID as mandateReqTransId ,a.SERVICE_ID as serviceId ");
    sb.append("FROM CAMOWNER.CAS_ARC_CESS_ASSIGN_TXNS a ");
    sb.append("INNER JOIN CAMOWNER.SYS_CIS_BANK b ON a.CREDITOR_BANK = b.MEMBER_NO ");
    sb.append("INNER JOIN CAMOWNER.SYS_CIS_BANK c on A.DEBTOR_BANK = c.member_no ");
    sb.append("WHERE a.PROCESS_STATUS = '8' AND TRUNC(a.ARCHIVE_DATE) = TO_DATE('" + procDate + "','YYYY-MM-DD') ");
    sb.append("ORDER BY c.member_name ");

    String sqlQuery = sb.toString();
    log.debug("PBMD01 Query " + sqlQuery);
    List<String> scalarList = new ArrayList<String>();
    scalarList.add("crdMemberName");
    scalarList.add("crdMemberNo");
    scalarList.add("dbtrMemberName");
    scalarList.add("dbtrMemberNo");
    scalarList.add("fileName");
    scalarList.add("transType");
    scalarList.add("mandateReqTransId");
    scalarList.add("serviceId");

    try {
      mndtRespOutstandingEntityList = genericDAO.findBySql(sqlQuery, scalarList,
          MandateResponseOutstandingPerBankEntityModel.class);
    } catch (ObjectNotFoundException onfe) {
      log.error("No Object Exists on DB");
    } catch (Exception e) {
      log.error("Error on retrievePBMD08Data :" + e.getMessage());
      e.printStackTrace();
    }
    return mndtRespOutstandingEntityList;
  }

  @Override
  public Object retrieverRespOutstandForOneDay(String serviceId) {
    List<OutstandingRespSummaryCountModel> outstandingRespSummaryCountModelList =
        new ArrayList<OutstandingRespSummaryCountModel>();
    List<OutstandingRespSummaryCountEntityModel> outstandingRespSummaryEntityList =
        new ArrayList<OutstandingRespSummaryCountEntityModel>();
    OutstandingRespSummaryCountModel outstandingRespSummaryCountModel =
        new OutstandingRespSummaryCountModel();

    Date currentDate = new Date();
    Date date = null;

    try {

      date = DateUtil.addOneDay(currentDate);
      log.debug("finalCurrentDate**************" + currentDate);
    } catch (ParseException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }

    String stringDate = DateUtil.convertDateToString(date, "ddMMYYYY");

    log.debug("stringDate*******:" + stringDate);


    StringBuffer sb = new StringBuffer();


    sb.append("SELECT SUM(nrOfDays) AS nrOfDays FROM ");
    sb.append(
        "(SELECT a.DEBTOR_BANK AS debtorBank,c.MEMBER_NAME  as debtorName,a.CREDITOR_BANK AS " +
            "creditorBank,b.MEMBER_NAME AS creditorName,a.SERVICE_ID as serviceId, COUNT(*) AS " +
            "nrOfDays ");
    sb.append("FROM CAMOWNER.CAS_OPS_CESS_ASSIGN_TXNS a ");
    sb.append("LEFT OUTER JOIN CAMOWNER.SYS_CIS_BANK b ON a.CREDITOR_BANK = b.MEMBER_NO ");
    sb.append("INNER JOIN CAMOWNER.SYS_CIS_BANK c ON a.DEBTOR_BANK = c.MEMBER_NO ");
    sb.append("WHERE a.PROCESS_STATUS ='9' AND a.SERVICE_ID = '" + serviceId + "' ");
    sb.append("AND TO_CHAR(a.CREATED_DATE,'ddMMYYYY')= '" + stringDate + "' ");
    sb.append(
        "GROUP BY a.CREDITOR_BANK ,a.DEBTOR_BANK,b.MEMBER_NAME ,a.SERVICE_ID,c.MEMBER_NAME) ");

    String sqlQuery = sb.toString();
    log.debug("sqlQuery: " + sqlQuery);

    List<String> scalarList = new ArrayList<String>();
    scalarList.add("nrOfDays");


    outstandingRespSummaryEntityList =
        genericDAO.findBySql(sqlQuery, scalarList, OutstandingRespSummaryCountEntityModel.class);

    if (outstandingRespSummaryEntityList != null && outstandingRespSummaryEntityList.size() > 0) {
      for (OutstandingRespSummaryCountEntityModel outstandingRespSummaryCountEntityModel :
          outstandingRespSummaryEntityList) {
        outstandingRespSummaryCountModel = new OutstandingRespSummaryCountModel();
        outstandingRespSummaryCountModel =
            new ServiceTranslator().translateOutstandingRespSummaryCountEntityModelToCommonsModel(
                outstandingRespSummaryCountEntityModel);
        outstandingRespSummaryCountModelList.add(outstandingRespSummaryCountModel);
      }
    }

    return outstandingRespSummaryCountModel;
  }

  @Override
  public Object retrieverRespOutstandForTwoDays(String serviceId) {

    List<OutstandingRespSummaryCountModel> outstandingRespSummaryCountModelList =
        new ArrayList<OutstandingRespSummaryCountModel>();
    List<OutstandingRespSummaryCountEntityModel> outstandingRespSummaryEntityList =
        new ArrayList<OutstandingRespSummaryCountEntityModel>();
    OutstandingRespSummaryCountModel outstandingRespSummaryCountModel =
        new OutstandingRespSummaryCountModel();

    SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");

    Date currentDate = new Date();
    Date date = null;

    try {

      date = DateUtil.addNoOfDays(currentDate);
      log.debug("finalCurrentDate**************" + currentDate);
    } catch (ParseException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }

    String stringDate = DateUtil.convertDateToString(date, "ddMMYYYY");

    log.debug("stringDate*******:" + stringDate);

    StringBuffer sb = new StringBuffer();

    sb.append("SELECT SUM(nrOfTxns) AS nrOfTxns FROM ");
    sb.append(
        "(SELECT a.DEBTOR_BANK AS debtorBank,c.MEMBER_NAME  as debtorName,a.CREDITOR_BANK AS " +
            "creditorBank,b.MEMBER_NAME AS creditorName,a.SERVICE_ID as serviceId, COUNT(*) AS " +
            "nrOfTxns ");
    sb.append("FROM CAMOWNER.CAS_OPS_CESS_ASSIGN_TXNS a ");
    sb.append("LEFT OUTER JOIN CAMOWNER.SYS_CIS_BANK b ON a.CREDITOR_BANK = b.MEMBER_NO ");
    sb.append("INNER JOIN CAMOWNER.SYS_CIS_BANK c ON a.DEBTOR_BANK = c.MEMBER_NO ");
    sb.append("WHERE a.PROCESS_STATUS ='9' AND a.SERVICE_ID = '" + serviceId + "' ");
    sb.append("AND TO_CHAR(a.CREATED_DATE,'ddMMYYYY') ='" + stringDate + "' ");
    sb.append(
        "GROUP BY a.CREDITOR_BANK, a.DEBTOR_BANK,b.MEMBER_NAME ,a.SERVICE_ID,c.MEMBER_NAME) ");

    String sqlQuery = sb.toString();
    log.debug("sqlQuery: " + sqlQuery);

    List<String> scalarList = new ArrayList<String>();
    scalarList.add("nrOfTxns");


    outstandingRespSummaryEntityList =
        genericDAO.findBySql(sqlQuery, scalarList, OutstandingRespSummaryCountEntityModel.class);

    if (outstandingRespSummaryEntityList != null && outstandingRespSummaryEntityList.size() > 0) {
      for (OutstandingRespSummaryCountEntityModel outstandingRespSummaryCountEntityModel :
          outstandingRespSummaryEntityList) {
        outstandingRespSummaryCountModel = new OutstandingRespSummaryCountModel();
        outstandingRespSummaryCountModel =
            new ServiceTranslator().translateOutstandingRespSummaryCountEntityModelToCommonsModel(
                outstandingRespSummaryCountEntityModel);
        outstandingRespSummaryCountModelList.add(outstandingRespSummaryCountModel);
      }
    }
    return outstandingRespSummaryCountModel;
  }

  @Override
  public List<?> retrieverRespOutstand(String processingDate) {
    List<BatchOustandingResponseReportModel> oustandingResponseSummaryModelList =
        new ArrayList<BatchOustandingResponseReportModel>();
    List<BatchOustandingResponseEntityReportModel> oustandingResponseSummaryEntityList =
        new ArrayList<BatchOustandingResponseEntityReportModel>();
    BatchOustandingResponseReportModel oustandingResponseSummaryModel =
        new BatchOustandingResponseReportModel();

    StringBuffer sb = new StringBuffer();

    sb.append("with temptbl as ");
    sb.append(
        "(SELECT a.DEBTOR_BANK AS debtorBank,c.MEMBER_NAME as debtorName,a.CREDITOR_BANK AS " +
            "creditorBank,b.MEMBER_NAME AS creditorName,a.SERVICE_ID as serviceId,A.CREATED_DATE " +
            "as " +
            "creatdte ");
    sb.append(",nvl(d.transactionidentifierams,'NF') as jnlacqmrti ");
    sb.append(",nvl(d.cancellationreasonams,'NF') as canreason ");
    sb.append("FROM CAMOWNER.CAS_OPS_CESS_ASSIGN_TXNS a ");
    sb.append("LEFT OUTER JOIN CAMOWNER.SYS_CIS_BANK b ");
    sb.append("ON CREDITOR_BANK = b.MEMBER_NO ");
    sb.append("INNER JOIN CAMOWNER.SYS_CIS_BANK c ");
    sb.append("ON DEBTOR_BANK = c.MEMBER_NO ");
    sb.append("AND a.PROCESS_STATUS IN ('4','9') ");
    sb.append("left outer join manowner.jnl_acq d ");
    sb.append(
        "on a.mandate_req_tran_id = d.transactionidentifierams and cancellationreasonams in " +
            "('MICN', 'MACN') ");
    sb.append("where Trunc(a.CREATED_DATE) = TO_DATE('" + processingDate +
        "','YYYY-MM-DD') - 1 or Trunc(a.CREATED_DATE) = TO_DATE('" + processingDate +
        "','YYYY-MM-DD') - 2) ");
    sb.append(
        "select debtorName as debtorName,creditorName as creditorName,serviceId as serviceId," +
            "debtorBank as debtorBank, ");
    sb.append("sum(case when trunc(creatdte) = TO_DATE('" + processingDate +
        "','YYYY-MM-DD') - 1 and serviceId = 'MANIN' then 1 else 0 end) as maninOneDay, ");
    sb.append("sum(case when trunc(creatdte) = TO_DATE('" + processingDate +
        "','YYYY-MM-DD') - 2 and serviceId = 'MANIN' then 1 else 0 end) as maninTwoDay, ");
    sb.append("sum(case when trunc(creatdte) = TO_DATE('" + processingDate +
        "','YYYY-MM-DD') - 1 and serviceId = 'MANAM' then 1 else 0 end) as manamOneDay, ");
    sb.append("sum(case when trunc(creatdte) = TO_DATE('" + processingDate +
        "','YYYY-MM-DD') - 2 and serviceId = 'MANAM' then 1 else 0 end) as manamTwoDay, ");
    sb.append("sum(case when trunc(creatdte) = TO_DATE('" + processingDate +
        "','YYYY-MM-DD') - 1 and serviceId = 'MANCN' then 1 else 0 end) as mancnOneDay, ");
    sb.append("sum(case when trunc(creatdte) = TO_DATE('" + processingDate +
        "','YYYY-MM-DD') - 2 and serviceId = 'MANCN' then 1 else 0 end) as mancnTwoDay ");
    sb.append("from temptbl ");
    sb.append("where jnlacqmrti = 'NF' ");
    sb.append("group by debtorName, creditorName, serviceId,debtorBank ");
    sb.append("order by debtorName, creditorName, serviceId,debtorBank");

    String sqlQuery = sb.toString();
    log.debug("sqlQuery: " + sqlQuery);

    List<String> scalarList = new ArrayList<String>();

    scalarList.add("debtorName");
    scalarList.add("creditorName");
    scalarList.add("serviceId");
    scalarList.add("debtorBank");
    scalarList.add("maninOneDay");
    scalarList.add("maninTwoDay");
    scalarList.add("manamOneDay");
    scalarList.add("manamTwoDay");
    scalarList.add("mancnOneDay");
    scalarList.add("mancnTwoDay");

    oustandingResponseSummaryEntityList =
        genericDAO.findBySql(sqlQuery, scalarList, BatchOustandingResponseEntityReportModel.class);

    if (oustandingResponseSummaryEntityList != null &&
        oustandingResponseSummaryEntityList.size() > 0) {
      for (BatchOustandingResponseEntityReportModel oustandingResponseEntityModel :
          oustandingResponseSummaryEntityList) {
        oustandingResponseSummaryModel = new BatchOustandingResponseReportModel();
        oustandingResponseSummaryModel =
            new ServiceTranslator().translateOustandingResponseEntityReportToCommonsModel(
                oustandingResponseEntityModel);
        oustandingResponseSummaryModelList.add(oustandingResponseSummaryModel);
      }
    }

    return oustandingResponseSummaryModelList;
  }

  @Override
  public List<?> retrieveAllBatchRejectedTnxs(String date) {

    List<BatchRejectedTransactionModel> batchRejectedTransactionModelList = new ArrayList<>();
    List<BatchRejectedTransactionEntityModel> batchRejectedTransactionEntityModelList =
        new ArrayList<>();

    StringBuffer sb = new StringBuffer();

    sb.append("with tmptbl as ");
    sb.append("(select reject_reason_code as RejResCd, ");
    sb.append("substr(msg_id,13,6) as member, ");
    sb.append("sum(case when NVL(reject_reason_code,'NF') = 'NF' then 0 else 1 end) as NrofTxns ");
    sb.append("from CAMOWNER.MDT_AC_ARC_MANDATE_TXNS ");

    if (date != null) {
      sb.append(
          "where service_id = 'MANAC' and substr(to_char(TRUNC(CREATED_DATE), 'YYYYMMDD'), 1, 6) " +
              "= '" +
              date + "' ");
    } else {
      sb.append(
          "where service_id = 'MANAC' and substr(to_char(TRUNC(CREATED_DATE), 'YYYYMMDD'), 1, 6) " +
              "= (select to_char(sysdate,'YYYYMM') from dual) ");
    }

    sb.append("group by reject_reason_code, substr(msg_id,13,6) ");
    sb.append("union all ");
    sb.append("select reject_reason as RejResCd,INSTR_AGENT as member, ");
    sb.append("sum(case when NVL(reject_reason,'NF') = 'NF' then 0 else 1 end) as NrofTxns ");
    sb.append("from manowner.mdt_ac_arc_mdte_resp ");
    sb.append("group by reject_reason, instr_agent), ");
    sb.append("tmptbl2 as ");
    sb.append(
        "(select a.reason_code as rescd, A.REASON_CODE_DESCRIPTION as rescddesc, b.member_no,b" +
            ".member_name ");
    sb.append("from CAMOWNER.CAS_CNFG_REASON_CODES a, CAMOWNER.SYS_CIS_BANK b ");
    sb.append("union all ");
    sb.append(
        "select a.reject_reason_code as rescd, A.reject_REASON_DESC as rescddesc, b.member_no, b" +
            ".member_name ");
    sb.append("from CAMOWNER.CAS_CNFG_REJECT_REASON_CODES a, CAMOWNER.SYS_CIS_BANK b ");
    sb.append("where a.reject_reason_code = 'NMTC') ");
    sb.append(
        "select aa.rescd as rescode,aa.rescddesc as mndtRejectionReason,aa.member_no as " +
            "memberNumber, ");
    sb.append("NVL(bb.NrofTxns, 0) NoTxns ");
    sb.append("from tmptbl2 aa ");
    sb.append("left outer join tmptbl bb ");
    sb.append("on aa.rescd = bb.RejResCd ");
    sb.append("and AA.MEMBER_NO = bb.member ");
    sb.append("order by aa.rescd, aa.member_name ");

    String sqlQuery = sb.toString();
    log.debug("sqlQuery: " + sqlQuery);

    List<String> scalarList = new ArrayList<String>();

    scalarList.add("rescode");
    scalarList.add("mndtRejectionReason");
    scalarList.add("memberNumber");
    scalarList.add("NoTxns");

    try {
      batchRejectedTransactionEntityModelList =
          genericDAO.findBySql(sqlQuery, scalarList, BatchRejectedTransactionEntityModel.class);

      if (batchRejectedTransactionEntityModelList != null &&
          batchRejectedTransactionEntityModelList.size() > 0) {
        for (BatchRejectedTransactionEntityModel batchRejectedTransactionEntityModel :
            batchRejectedTransactionEntityModelList) {
          BatchRejectedTransactionModel batchRejectedTransactionModel =
              new BatchRejectedTransactionModel();
          batchRejectedTransactionModel =
              new ServiceTranslator().translateBatchRejectedEntityReportToCommonsModel(
                  batchRejectedTransactionEntityModel);
          batchRejectedTransactionModelList.add(batchRejectedTransactionModel);
        }
      }

    } catch (ObjectNotFoundException onfe) {
      log.error("No Object Exists on DB");
    } catch (Exception e) {
      log.error("Error on retrieve:" + e.getMessage());
      e.printStackTrace();
    }

    return batchRejectedTransactionModelList;
  }

  @Override
  public List<?> retrieveBatchAmendmentTrans(String firstDate, String lastDate) {
    List<BatchAmendmentModel> batchAmendmentModelList = new ArrayList<>();
    List<BatchAmendmentEntityModel> batchAmendmentEntityModelList = new ArrayList<>();

    StringBuffer sb = new StringBuffer();

    sb.append("select b.member_name as creditor, ");
    sb.append("sum(case when a.AMEND_REASON = 'MD16' then 1 else 0 end) as reqByCustomer, ");
    sb.append("sum(case when a.AMEND_REASON = 'MD17' then 1 else 0 end) as canAmndReqByInitPrty, ");
    sb.append("sum(case when a.AMEND_REASON = 'MD19' then 1 else 0 end) as unspndMndtWithChngs, ");
    sb.append("sum(case when a.AMEND_REASON = 'MD20' then 1 else 0 end) as unspndUnchgMndt, ");
    sb.append("sum(case when a.AMEND_REASON = 'MD21' then 1 else 0 end) as rsnNotSpcByCust ");
    sb.append("FROM  CAMOWNER.MDT_AC_ARC_MANDATE_TXNS a ");
    sb.append("LEFT JOIN CAMOWNER.SYS_CIS_BANK b ");
    sb.append("on a.creditor_bank = b.MEMBER_NO ");
    sb.append("where service_id = 'MANAM' and trunc(a.created_date) between to_date('" + firstDate +
        "', 'DDMMYYYY') and to_date('" + lastDate + "', 'DDMMYYYY') ");
    sb.append("group by b.member_name ");
    sb.append("order by b.member_name ");


    String sqlQuery = sb.toString();
    log.debug("sqlQuery --->: " + sqlQuery);

    List<String> scalarList = new ArrayList<String>();

    scalarList.add("creditor");
    scalarList.add("reqByCustomer");
    scalarList.add("canAmndReqByInitPrty");
    scalarList.add("unspndMndtWithChngs");
    scalarList.add("unspndUnchgMndt");
    scalarList.add("rsnNotSpcByCust");

    try {
      batchAmendmentEntityModelList =
          genericDAO.findBySql(sqlQuery, scalarList, BatchAmendmentEntityModel.class);

      if (batchAmendmentEntityModelList != null && batchAmendmentEntityModelList.size() > 0) {
        for (BatchAmendmentEntityModel batchAmendmentEntityModel : batchAmendmentEntityModelList) {
          BatchAmendmentModel bacthAmendmentModel = new BatchAmendmentModel();
          bacthAmendmentModel =
              new ServiceTranslator().translateBatchAmendmentEntityReportToCommonsModel(
                  batchAmendmentEntityModel);
          batchAmendmentModelList.add(bacthAmendmentModel);
        }

      }

    } catch (ObjectNotFoundException onfe) {
      log.error("No Object Exists on DB");
    } catch (Exception e) {
      log.error("Error on retrieve:" + e.getMessage());
      e.printStackTrace();
    }


    return batchAmendmentModelList;
  }

  public List<?> retrievePHIRAmendmentMandateInfo(String fromDate, String toDate) {

    List<PasaMandateReportEntityModel> pasaMandateReportEntityModelList = new ArrayList<>();

    StringBuffer sb = new StringBuffer();

    sb.append("with tmptbl as ");
    sb.append(
        "(SELECT a.MANDATE_REQ_TRAN_ID AS mrti,nvl(b.MANDATE_REQ_TRAN_ID, 'NF') as mrtib,c" +
            ".TXN_STATUS as txnStatus,a.MSG_ID AS msgId,a.CREDITOR_BANK AS creditorBank,a" +
            ".DEBTOR_BANK AS debtorBank, ");
    sb.append("TO_CHAR(TRUNC(a.CREATED_DATE), 'YYYY-MM-DD') AS creationDate, ");
    sb.append(
        "a.AUTH_TYPE AS authType,a.LOCAL_INSTR_CD AS dbtrAuthReq,a.SEQUENCE_TYPE AS instOcc,a" +
            ".CRED_ABB_SHORT_NAME as cdtrAbbShtNm,b.auth_status_ind as authstsind,a" +
            ".PROCESS_STATUS " +
            "as processStatus, ");
    sb.append(
        "a.CONTRACT_REF AS contRefNum,b.ACCEPTED_IND as accptind,a.REJECT_REASON_CODE as " +
            "rjctrsnCode,c.ERROR_CODE as confErr,b.LOCAL_INSTR_CD as lclInstr,a.AMEND_REASON as " +
            "reason ");
    sb.append("FROM CAMOWNER.MDT_AC_ARC_MANDATE_TXNS a ");
    sb.append("left join manowner.MDT_AC_ARC_MANDATE_TXNS b ");
    sb.append("on a.MANDATE_REQ_TRAN_ID = b.MANDATE_REQ_TRAN_ID ");
    sb.append("and a.service_id = 'MANAM' and b.service_id = 'MANAC' ");
    sb.append("left join manowner.MDT_AC_ARC_CONF_DETAILS c ");
    sb.append("on a.MANDATE_REQ_TRAN_ID = c.TXN_ID ");
    sb.append(
        "WHERE a.SERVICE_ID = 'MANAM' AND TRUNC(a.CREATED_DATE) BETWEEN TO_DATE('" + fromDate +
            "', 'YYYY-MM-DD') AND TO_DATE('" + toDate + "', 'YYYY-MM-DD') ");
    sb.append("order by a.MANDATE_REQ_TRAN_ID) ");
    sb.append(
        "select mrti,creditorBank,debtorBank,creationDate,reason,authType,contRefNum,dbtrAuthReq," +
            "instOcc,cdtrAbbShtNm, ");
    sb.append("case when lclInstr = '0226' then 'NOTIFICATIONS' ");
    sb.append("when authstsind = 'AAUT' then 'AUTHENTICATED' ");
    sb.append("when authstsind = 'NAUT' then 'DECLINED' ");
    sb.append("when authstsind = 'NRSP' then 'NO_RESPONSE' ");
    sb.append(
        "when mrtib = 'NF' and processStatus in ('8', '9', '4') then 'RESPONSE_FILE_NOT_RECEIVED'" +
            " ");
    sb.append("when txnStatus = 'RJCT' or accptind = 'false' then 'VALIDATION_FAILURE' ");
    sb.append("end as STATUS, ");
    sb.append("case when accptind = 'false' then rjctrsnCode ");
    sb.append("when txnStatus = 'RJCT' then confErr ");
    sb.append("end as errorcode, ");
    sb.append("'ACH' AS dataSource ");
    sb.append("from tmptbl ");
    sb.append("union all ");
    sb.append(
        "select nvl(b.txn_id,'NF') as mrti,a.INSTG_AGT as creditorBank,c.member_no as debtorBank," +
            " ");
    sb.append("TO_CHAR(trunc(A.CREATE_DATE_TIME), 'YYYY-MM-DD')  as creationDate, ");
    sb.append(
        "'' as reason,'' as authType,'' as contRefNum,'' as dbtrAuthReq,'' as instOcc,b" +
            ".CR_ABB_SHORT_NAME as cdtrAbbShtNm,'VALIDATION_FAILURE' as status,B.ERROR_CODE as " +
            "errorCode,'ACH' as dataSource ");
    sb.append("from CAMOWNER.MDT_AC_ARC_STATUS_HDRS a ");
    sb.append("left join CAMOWNER.MDT_AC_ARC_STATUS_DETAILS b ");
    sb.append("on a.SYSTEM_SEQ_NO = b.STATUS_HDR_SEQ_NO ");
    sb.append("left join CAMOWNER.SYS_CIS_BRANCH c ");
    sb.append("on B.DEBTOR_BRANCH_NO = C.MEMBER_NO ");
    sb.append("where trunc(a.CREATE_DATE_TIME) BETWEEN TO_DATE('" + fromDate +
        "', 'YYYY-MM-DD') AND TO_DATE('" + toDate + "', 'YYYY-MM-DD') ");
    sb.append(
        "and a.SERVICE = 'ST100' and a.ORGNL_MSG_NAME = 'pain.010' and nvl(b.txn_id,'NF') <> 'NF'" +
            " ");
    sb.append(
        "ORDER BY creationDate, creditorBank, debtorBank, authType, contRefNum, dbtrAuthReq, " +
            "instOcc, cdtrAbbShtNm ");

    String sqlQuery = sb.toString();
    log.debug("sqlQuery --->: " + sqlQuery);

    List<String> scalarList = new ArrayList<String>();

    scalarList.add("mrti");
    scalarList.add("creditorBank");
    scalarList.add("debtorBank");
    scalarList.add("creationDate");
    scalarList.add("reason");
    scalarList.add("authType");
    scalarList.add("contRefNum");
    scalarList.add("dbtrAuthReq");
    scalarList.add("instOcc");
    scalarList.add("cdtrAbbShtNm");
    scalarList.add("status");
    scalarList.add("errorCode");
    scalarList.add("dataSource");

    try {
      pasaMandateReportEntityModelList =
          genericDAO.findBySql(sqlQuery, scalarList, PasaMandateReportEntityModel.class);

    } catch (ObjectNotFoundException obj) {
      log.error("No Object Exists on DB");
    } catch (Exception ex) {
      log.error("Error on retrieve:" + ex.getMessage());
      ex.printStackTrace();
    }

    return pasaMandateReportEntityModelList;
  }

  @Override
  public List<?> retrieveBatchMonthlyTransFromCreditor(String fromDate, String toDate) {

    List<MonthlyVolumeCountEntityModel> monthlyVolumeCountEntityModel = new ArrayList<>();

    StringBuffer sb = new StringBuffer();

    sb.append(
        "WITH TEMPTBL AS (SELECT a.MEMBER_NO AS cr_memno  ,a.MEMBER_NAME AS cr_memname  ,c" +
            ".SERVICE_ID_IN AS inService ");
    sb.append("FROM  CAMOWNER.SYS_CIS_BANK a  ,CAMOWNER.CAS_SYSCTRL_SERVICES c ");
    sb.append("WHERE c.ACTIVE_IND = 'Y' AND c.SERVICE_ID_IN IS NOT NULL) ");
    sb.append("SELECT aa.inService as service, aa.cr_memno AS instId, ");
    sb.append(
        "SUM(NVL(bb.NR_OF_MSGS, 0)) as nrOfMsgs, SUM(NVL(bb.NR_MSGS_ACCEPTED,0)) as nrOfAccpMsgs," +
            " SUM(NVL(bb.NR_MSGS_REJECTED,0)) as nrOfRjctMsgs ");
    sb.append("FROM TEMPTBL aa ");
    sb.append(
        "LEFT OUTER JOIN CAMOWNER.MDT_AC_ARC_MNDT_COUNT bb ON aa.cr_memno = bb.INST_ID AND aa" +
            ".inService = bb.SERVICE_ID ");
    sb.append("AND bb.PROCESS_DATE BETWEEN TO_DATE('" + fromDate + "','YYYY-MM-DD') AND TO_DATE('" +
        toDate + "','YYYY-MM-DD') ");
    sb.append("WHERE aa.inService IN ('MANIN', 'MANAM', 'MANCN', 'MANRI', 'SRINP') ");
    sb.append("GROUP BY aa.inService, aa.cr_memno ");
    sb.append("ORDER BY aa.inService, aa.cr_memno ");

    String sqlQuery = sb.toString();

    List<String> scalarList = new ArrayList<String>();

    scalarList.add("service");
    scalarList.add("instId");
    scalarList.add("nrOfMsgs");
    scalarList.add("nrOfAccpMsgs");
    scalarList.add("nrOfRjctMsgs");

    try {
      monthlyVolumeCountEntityModel =
          genericDAO.findBySql(sqlQuery, scalarList, MonthlyVolumeCountEntityModel.class);

    } catch (ObjectNotFoundException e) {
      log.error("No Object Exists on DB");
    } catch (Exception e) {
      log.error("Error on retrieve:" + e.getMessage());
      e.printStackTrace();
    }

    return monthlyVolumeCountEntityModel;
  }

  @Override
  public List<?> batchMonthlyTransExtractedToDebtorBank(String fromDate, String toDate) {

    List<MonthlyVolumeCountEntityModel> monthlyVolumeCountEntityModel = new ArrayList<>();

    StringBuffer sb = new StringBuffer();

    sb.append(
        "WITH TEMPTBL AS (SELECT a.MEMBER_NO AS dr_memno  ,a.MEMBER_NAME AS dr_memname  ,c" +
            ".SERVICE_ID_OUT AS outService ");
    sb.append("FROM  CAMOWNER.SYS_CIS_BANK a  ,CAMOWNER.CAS_SYSCTRL_SERVICES c ");
    sb.append("WHERE c.ACTIVE_IND = 'Y' AND c.SERVICE_ID_IN IS NOT NULL) ");
    sb.append("SELECT aa.outService as service, aa.dr_memno AS instId, ");
    sb.append("SUM(NVL(bb.NR_MSGS_EXTRACTED, 0)) as nrOfExtMsgs ");
    sb.append("FROM TEMPTBL aa ");
    sb.append(
        "LEFT OUTER JOIN CAMOWNER.MDT_AC_ARC_MNDT_COUNT bb ON aa.dr_memno = bb.INST_ID AND aa" +
            ".outService = bb.SERVICE_ID ");
    sb.append("AND bb.PROCESS_DATE BETWEEN TO_DATE('" + fromDate + "','YYYY-MM-DD') AND TO_DATE('" +
        toDate + "','YYYY-MM-DD') ");
    sb.append("WHERE aa.outService IN ('MANOT', 'MANOM', 'MANCO', 'MANRO', 'SROUT') ");
    sb.append("GROUP BY aa.outService, aa.dr_memno ");
    sb.append("ORDER BY aa.outService, aa.dr_memno ");

    String sqlQuery = sb.toString();

    List<String> scalarList = new ArrayList<String>();

    scalarList.add("service");
    scalarList.add("instId");
    scalarList.add("nrOfExtMsgs");

    try {
      monthlyVolumeCountEntityModel =
          genericDAO.findBySql(sqlQuery, scalarList, MonthlyVolumeCountEntityModel.class);

    } catch (ObjectNotFoundException e) {
      log.error("No Object Exists on DB");
    } catch (Exception e) {
      log.error("Error on retrieve:" + e.getMessage());
      e.printStackTrace();
    }

    return monthlyVolumeCountEntityModel;
  }

  @Override
  public List<?> retrieveBatchMonthlyTransFromDebtor(String fromDate, String toDate) {

    List<MonthlyVolumeCountEntityModel> monthlyVolumeCountEntityModel = new ArrayList<>();

    StringBuffer sb = new StringBuffer();

    sb.append(
        "WITH TEMPTBL AS (SELECT a.MEMBER_NO AS dr_memno ,a.MEMBER_NAME AS dr_memname ,c" +
            ".SERVICE_ID_IN AS inService ");
    sb.append("FROM  CAMOWNER.SYS_CIS_BANK a ,CAMOWNER.CAS_SYSCTRL_SERVICES c ");
    sb.append("WHERE c.ACTIVE_IND = 'Y' AND c.SERVICE_ID_IN IS NOT NULL) ");
    sb.append("SELECT aa.inService as service, aa.dr_memno AS instId, ");
    sb.append(
        "SUM(NVL(bb.NR_OF_MSGS, 0)) as nrOfMsgs, SUM(NVL(bb.NR_MSGS_ACCEPTED,0)) as nrOfAccpMsgs," +
            " SUM(NVL(bb.NR_MSGS_REJECTED,0)) as nrOfRjctMsgs ");
    sb.append("FROM TEMPTBL aa ");
    sb.append(
        "LEFT OUTER JOIN CAMOWNER.MDT_AC_ARC_MNDT_COUNT bb ON aa.dr_memno = bb.INST_ID AND aa" +
            ".inService = bb.SERVICE_ID ");
    sb.append("AND bb.PROCESS_DATE BETWEEN TO_DATE('" + fromDate + "','YYYY-MM-DD') AND TO_DATE('" +
        toDate + "','YYYY-MM-DD') ");
    sb.append("WHERE aa.inService IN ('ST101', 'MANAC', 'MANRT', 'SPINP') ");
    sb.append("GROUP BY aa.inService, aa.dr_memno ");
    sb.append("ORDER BY aa.inService, aa.dr_memno ");


    String sqlQuery = sb.toString();

    List<String> scalarList = new ArrayList<String>();

    scalarList.add("service");
    scalarList.add("instId");
    scalarList.add("nrOfMsgs");
    scalarList.add("nrOfAccpMsgs");
    scalarList.add("nrOfRjctMsgs");

    try {
      monthlyVolumeCountEntityModel =
          genericDAO.findBySql(sqlQuery, scalarList, MonthlyVolumeCountEntityModel.class);

    } catch (ObjectNotFoundException e) {
      log.error("No Object Exists on DB");
    } catch (Exception e) {
      log.error("Error on retrieve:" + e.getMessage());
      e.printStackTrace();
    }

    return monthlyVolumeCountEntityModel;
  }

  @Override
  public List<?> batchMonthlyTransExtractedToCreditorBank(String fromDate, String toDate) {

    List<MonthlyVolumeCountEntityModel> monthlyVolumeCountEntityModel = new ArrayList<>();

    StringBuffer sb = new StringBuffer();

    sb.append(
        "WITH TEMPTBL AS (SELECT a.MEMBER_NO AS cr_memno ,a.MEMBER_NAME AS cr_memname ,c" +
            ".SERVICE_ID_OUT AS outService ");
    sb.append("FROM  CAMOWNER.SYS_CIS_BANK a  ,CAMOWNER.CAS_SYSCTRL_SERVICES c ");
    sb.append("WHERE c.ACTIVE_IND = 'Y' AND c.SERVICE_ID_IN IS NOT NULL) ");
    sb.append("SELECT aa.outService as service, aa.cr_memno AS instId, ");
    sb.append("SUM(NVL(bb.NR_MSGS_EXTRACTED, 0)) as nrOfExtMsgs ");
    sb.append("FROM TEMPTBL aa ");
    sb.append(
        "LEFT OUTER JOIN CAMOWNER.MDT_AC_ARC_MNDT_COUNT bb ON aa.cr_memno = bb.INST_ID AND aa" +
            ".outService = bb.SERVICE_ID  ");
    sb.append("AND bb.PROCESS_DATE BETWEEN TO_DATE('" + fromDate + "','YYYY-MM-DD') AND TO_DATE('" +
        toDate + "','YYYY-MM-DD') ");
    sb.append("WHERE aa.outService IN ('ST103', 'MANOC', 'MANRF', 'SPOUT') ");
    sb.append("GROUP BY aa.outService, aa.cr_memno ");
    sb.append("ORDER BY aa.outService, aa.cr_memno ");

    String sqlQuery = sb.toString();

    List<String> scalarList = new ArrayList<String>();

    scalarList.add("service");
    scalarList.add("instId");
    scalarList.add("nrOfExtMsgs");

    try {
      monthlyVolumeCountEntityModel =
          genericDAO.findBySql(sqlQuery, scalarList, MonthlyVolumeCountEntityModel.class);

    } catch (ObjectNotFoundException e) {
      log.error("No Object Exists on DB");
    } catch (Exception e) {
      log.error("Error on retrieve:" + e.getMessage());
      e.printStackTrace();
    }

    return monthlyVolumeCountEntityModel;
  }

  @Override
  public List<?> retrieveBatchMonthlyStReportCreditor(String fromDate, String toDate) {

    List<MonthlyVolumeCountEntityModel> monthlyVolumeCountEntityModel = new ArrayList<>();

    StringBuffer sb = new StringBuffer();

    sb.append("WITH TEMPTBL AS ( ");
    sb.append(
        "SELECT a.MEMBER_NO AS cr_memno,a.MEMBER_NAME AS cr_memname,c.SERVICE_ID_OUT AS " +
            "outService ");
    sb.append("FROM  CAMOWNER.SYS_CIS_BANK a, ");
    sb.append("CAMOWNER.CAS_SYSCTRL_SERVICES c ");
    sb.append("WHERE c.ACTIVE_IND = 'Y' AND c.SERVICE_ID_IN IS NULL) ");
    sb.append("SELECT aa.outService as service, aa.cr_memno AS instId, ");
    sb.append("COUNT(bb.SERVICE) AS nrOfMsgs ");
    sb.append("FROM TEMPTBL aa  ");
    sb.append("LEFT OUTER JOIN CAMOWNER.MDT_AC_ARC_STATUS_HDRS bb ON aa.cr_memno = bb.INSTG_AGT ");
    sb.append("AND bb.ARCHIVE_DATE BETWEEN TO_DATE('" + fromDate + "','YYYY-MM-DD') AND TO_DATE('" +
        toDate + "','YYYY-MM-DD') ");
    sb.append("AND aa.outService = bb.SERVICE ");
    sb.append("WHERE aa.outService IN ('ST100', 'ST105', 'ST007') ");
    sb.append("GROUP BY aa.outService, aa.cr_memno ");
    sb.append("ORDER BY aa.outService, aa.cr_memno ");

    String sqlQuery = sb.toString();

    List<String> scalarList = new ArrayList<String>();

    scalarList.add("service");
    scalarList.add("instId");
    scalarList.add("nrOfMsgs");

    try {
      monthlyVolumeCountEntityModel =
          genericDAO.findBySql(sqlQuery, scalarList, MonthlyVolumeCountEntityModel.class);

    } catch (ObjectNotFoundException e) {
      log.error("No Object Exists on DB");
    } catch (Exception e) {
      log.error("Error on retrieve:" + e.getMessage());
      e.printStackTrace();
    }

    return monthlyVolumeCountEntityModel;
  }

  @Override
  public List<?> retrieveBatchMonthlyStReportDebtor(String fromDate, String toDate) {

    List<MonthlyVolumeCountEntityModel> monthlyVolumeCountEntityModel = new ArrayList<>();

    StringBuffer sb = new StringBuffer();

    sb.append("WITH TEMPTBL AS ( ");
    sb.append(
        "SELECT a.MEMBER_NO AS dr_memno,a.MEMBER_NAME AS dr_memname,c.SERVICE_ID_OUT AS " +
            "outService ");
    sb.append("FROM  CAMOWNER.SYS_CIS_BANK a, ");
    sb.append("CAMOWNER.CAS_SYSCTRL_SERVICES c ");
    sb.append("WHERE c.ACTIVE_IND = 'Y' AND c.SERVICE_ID_IN IS NULL) ");
    sb.append("SELECT aa.outService as service, aa.dr_memno AS instId, ");
    sb.append("COUNT(bb.SERVICE) AS nrOfMsgs ");
    sb.append("FROM TEMPTBL aa ");
    sb.append("LEFT OUTER JOIN CAMOWNER.MDT_AC_ARC_STATUS_HDRS bb ON aa.dr_memno = bb.INSTG_AGT ");
    sb.append("AND bb.ARCHIVE_DATE BETWEEN TO_DATE('" + fromDate + "','YYYY-MM-DD') AND TO_DATE('" +
        toDate + "','YYYY-MM-DD') ");
    sb.append("AND aa.outService = bb.SERVICE ");
    sb.append("WHERE aa.outService IN ('ST102', 'ST104', 'ST106', 'ST008') ");
    sb.append("GROUP BY aa.outService, aa.dr_memno ");
    sb.append("ORDER BY aa.outService, aa.dr_memno ");

    String sqlQuery = sb.toString();

    List<String> scalarList = new ArrayList<String>();

    scalarList.add("service");
    scalarList.add("instId");
    scalarList.add("nrOfMsgs");

    try {
      monthlyVolumeCountEntityModel =
          genericDAO.findBySql(sqlQuery, scalarList, MonthlyVolumeCountEntityModel.class);

    } catch (ObjectNotFoundException e) {
      log.error("No Object Exists on DB");
    } catch (Exception e) {
      log.error("Error on retrieve:" + e.getMessage());
      e.printStackTrace();
    }

    return monthlyVolumeCountEntityModel;
  }

  @Override
  public List<?> retrieveBatchDailyTransFromCreditor(String currentDate) {

    List<MonthlyVolumeCountEntityModel> monthlyVolumeCountEntityModel = new ArrayList<>();

    StringBuffer sb = new StringBuffer();

    sb.append(
        "WITH TEMPTBL AS (SELECT a.MEMBER_NO AS cr_memno  ,a.MEMBER_NAME AS cr_memname  ,c" +
            ".SERVICE_ID_IN AS inService ");
    sb.append("FROM  CAMOWNER.SYS_CIS_BANK a  ,CAMOWNER.CAS_SYSCTRL_SERVICES c ");
    sb.append("WHERE c.ACTIVE_IND = 'Y' AND c.SERVICE_ID_IN IS NOT NULL) ");
    sb.append("SELECT aa.inService as service, aa.cr_memno AS instId, ");
    sb.append(
        "SUM(NVL(bb.NR_OF_MSGS, 0)) as nrOfMsgs, SUM(NVL(bb.NR_MSGS_ACCEPTED,0)) as nrOfAccpMsgs," +
            " SUM(NVL(bb.NR_MSGS_REJECTED,0)) as nrOfRjctMsgs ");
    sb.append("FROM TEMPTBL aa ");
    sb.append(
        "LEFT OUTER JOIN CAMOWNER.CAS_OPS_MNDT_COUNT bb ON aa.cr_memno = bb.INST_ID AND aa" +
            ".inService = bb.SERVICE_ID ");
    sb.append("AND bb.PROCESS_DATE = TO_DATE('" + currentDate + "','YYYY-MM-DD') ");
    sb.append("WHERE aa.inService = 'CARIN' ");
    sb.append("GROUP BY aa.inService, aa.cr_memno ");
    sb.append("ORDER BY aa.inService, aa.cr_memno ");

    String sqlQuery = sb.toString();

    List<String> scalarList = new ArrayList<String>();

    scalarList.add("service");
    scalarList.add("instId");
    scalarList.add("nrOfMsgs");
    scalarList.add("nrOfAccpMsgs");
    scalarList.add("nrOfRjctMsgs");

    try {
      monthlyVolumeCountEntityModel =
          genericDAO.findBySql(sqlQuery, scalarList, MonthlyVolumeCountEntityModel.class);

    } catch (ObjectNotFoundException e) {
      log.error("No Object Exists on DB");
    } catch (Exception e) {
      log.error("Error on retrieve:" + e.getMessage());
      e.printStackTrace();
    }

    return monthlyVolumeCountEntityModel;
  }

  @Override
  public List<?> batchDailyTransExtractedToDebtorBank(String currentDate) {

    List<MonthlyVolumeCountEntityModel> monthlyVolumeCountEntityModel = new ArrayList<>();

    StringBuffer sb = new StringBuffer();

    sb.append(
        "WITH TEMPTBL AS (SELECT a.MEMBER_NO AS dr_memno  ,a.MEMBER_NAME AS dr_memname  ,c" +
            ".SERVICE_ID_OUT AS outService ");
    sb.append("FROM  CAMOWNER.SYS_CIS_BANK a  ,CAMOWNER.CAS_SYSCTRL_SERVICES c ");
    sb.append("WHERE c.ACTIVE_IND = 'Y' AND c.SERVICE_ID_IN IS NOT NULL) ");
    sb.append("SELECT aa.outService as service, aa.dr_memno AS instId, ");
    sb.append("SUM(NVL(bb.NR_MSGS_EXTRACTED, 0)) as nrOfExtMsgs ");
    sb.append("FROM TEMPTBL aa ");
    sb.append(
        "LEFT OUTER JOIN CAMOWNER.CAS_OPS_MNDT_COUNT bb ON aa.dr_memno = bb.INST_ID AND aa" +
            ".outService = bb.SERVICE_ID ");
    sb.append("AND bb.PROCESS_DATE = TO_DATE('" + currentDate + "','YYYY-MM-DD') ");
    sb.append("WHERE aa.outService = 'CAROT' ");
    sb.append("GROUP BY aa.outService, aa.dr_memno ");
    sb.append("ORDER BY aa.outService, aa.dr_memno ");

    String sqlQuery = sb.toString();

    List<String> scalarList = new ArrayList<String>();

    scalarList.add("service");
    scalarList.add("instId");
    scalarList.add("nrOfExtMsgs");

    try {
      monthlyVolumeCountEntityModel =
          genericDAO.findBySql(sqlQuery, scalarList, MonthlyVolumeCountEntityModel.class);

    } catch (ObjectNotFoundException e) {
      log.error("No Object Exists on DB");
    } catch (Exception e) {
      log.error("Error on retrieve:" + e.getMessage());
      e.printStackTrace();
    }

    return monthlyVolumeCountEntityModel;

  }

  @Override
  public List<?> retrieveBatchDailyTransFromDebtor(String currentDate) {

    List<MonthlyVolumeCountEntityModel> monthlyVolumeCountEntityModel = new ArrayList<>();

    StringBuffer sb = new StringBuffer();

    sb.append(
        "WITH TEMPTBL AS (SELECT a.MEMBER_NO AS dr_memno ,a.MEMBER_NAME AS dr_memname ,c" +
            ".SERVICE_ID_IN AS inService ");
    sb.append("FROM  CAMOWNER.SYS_CIS_BANK a ,CAMOWNER.CAS_SYSCTRL_SERVICES c ");
    sb.append("WHERE c.ACTIVE_IND = 'Y' AND c.SERVICE_ID_IN IS NOT NULL) ");
    sb.append("SELECT aa.inService as service, aa.dr_memno AS instId, ");
    sb.append(
        "SUM(NVL(bb.NR_OF_MSGS, 0)) as nrOfMsgs, SUM(NVL(bb.NR_MSGS_ACCEPTED,0)) as nrOfAccpMsgs," +
            " SUM(NVL(bb.NR_MSGS_REJECTED,0)) as nrOfRjctMsgs ");
    sb.append("FROM TEMPTBL aa ");
    sb.append(
        "LEFT OUTER JOIN CAMOWNER.CAS_OPS_MNDT_COUNT bb ON aa.dr_memno = bb.INST_ID AND aa" +
            ".inService = bb.SERVICE_ID ");
    sb.append("AND bb.PROCESS_DATE = TO_DATE('" + currentDate + "','YYYY-MM-DD') ");
    sb.append("WHERE aa.inService IN ('ST201', 'RCAIN') ");
    sb.append("GROUP BY aa.inService, aa.dr_memno ");
    sb.append("ORDER BY aa.inService, aa.dr_memno ");

    String sqlQuery = sb.toString();

    List<String> scalarList = new ArrayList<String>();

    scalarList.add("service");
    scalarList.add("instId");
    scalarList.add("nrOfMsgs");
    scalarList.add("nrOfAccpMsgs");
    scalarList.add("nrOfRjctMsgs");

    try {
      monthlyVolumeCountEntityModel =
          genericDAO.findBySql(sqlQuery, scalarList, MonthlyVolumeCountEntityModel.class);

    } catch (ObjectNotFoundException e) {
      log.error("No Object Exists on DB");
    } catch (Exception e) {
      log.error("Error on retrieve:" + e.getMessage());
      e.printStackTrace();
    }

    return monthlyVolumeCountEntityModel;
  }

  @Override
  public List<?> batchDailyTransExtractedToCreditorBank(String currentDate) {

    List<MonthlyVolumeCountEntityModel> monthlyVolumeCountEntityModel = new ArrayList<>();

    StringBuffer sb = new StringBuffer();

    sb.append(
        "WITH TEMPTBL AS (SELECT a.MEMBER_NO AS cr_memno ,a.MEMBER_NAME AS cr_memname ,c" +
            ".SERVICE_ID_OUT AS outService ");
    sb.append("FROM  CAMOWNER.SYS_CIS_BANK a  ,CAMOWNER.CAS_SYSCTRL_SERVICES c ");
    sb.append("WHERE c.ACTIVE_IND = 'Y' AND c.SERVICE_ID_IN IS NOT NULL) ");
    sb.append("SELECT aa.outService as service, aa.cr_memno AS instId, ");
    sb.append("SUM(NVL(bb.NR_MSGS_EXTRACTED, 0)) as nrOfExtMsgs ");
    sb.append("FROM TEMPTBL aa ");
    sb.append(
        "LEFT OUTER JOIN CAMOWNER.CAS_OPS_MNDT_COUNT bb ON aa.cr_memno = bb.INST_ID AND aa" +
            ".outService = bb.SERVICE_ID ");
    sb.append("AND bb.PROCESS_DATE = TO_DATE('" + currentDate + "','YYYY-MM-DD') ");
    sb.append("WHERE aa.outService IN ('ST203', 'RCAOT') ");
    sb.append("GROUP BY aa.outService, aa.cr_memno ");
    sb.append("ORDER BY aa.outService, aa.cr_memno ");

    String sqlQuery = sb.toString();

    List<String> scalarList = new ArrayList<String>();

    scalarList.add("service");
    scalarList.add("instId");
    scalarList.add("nrOfExtMsgs");

    try {
      monthlyVolumeCountEntityModel =
          genericDAO.findBySql(sqlQuery, scalarList, MonthlyVolumeCountEntityModel.class);

    } catch (ObjectNotFoundException e) {
      log.error("No Object Exists on DB");
    } catch (Exception e) {
      log.error("Error on retrieve:" + e.getMessage());
      e.printStackTrace();
    }

    return monthlyVolumeCountEntityModel;
  }

  @Override
  public List<?> retrieveBatchDailyStReportCreditor(String currentDate) {

    List<MonthlyVolumeCountEntityModel> monthlyVolumeCountEntityModel = new ArrayList<>();

    StringBuffer sb = new StringBuffer();

    sb.append("WITH TEMPTBL AS ( ");
    sb.append(
        "SELECT a.MEMBER_NO AS cr_memno,a.MEMBER_NAME AS cr_memname,c.SERVICE_ID_OUT AS " +
            "outService ");
    sb.append("FROM  CAMOWNER.SYS_CIS_BANK a, ");
    sb.append("CAMOWNER.CAS_SYSCTRL_SERVICES c ");
    sb.append("WHERE c.ACTIVE_IND = 'Y' AND c.SERVICE_ID_IN IS NULL) ");
    sb.append("SELECT aa.outService as service, aa.cr_memno AS instId, ");
    sb.append("COUNT(bb.SERVICE) AS nrOfMsgs ");
    sb.append("FROM TEMPTBL aa  ");
    sb.append("LEFT OUTER JOIN CAMOWNER.CAS_OPS_STATUS_HDRS bb ON aa.cr_memno = bb.INSTG_AGT ");
    sb.append(
        "AND substr(bb.CREATE_DATE_TIME,1,10) = TO_DATE('" + currentDate + "','YYYY-MM-DD') ");
    sb.append("AND aa.outService = bb.SERVICE ");
    sb.append("WHERE aa.outService = 'ST200' ");
    sb.append("GROUP BY aa.outService, aa.cr_memno ");
    sb.append("ORDER BY aa.outService, aa.cr_memno ");

    String sqlQuery = sb.toString();

    List<String> scalarList = new ArrayList<String>();

    scalarList.add("service");
    scalarList.add("instId");
    scalarList.add("nrOfMsgs");

    try {
      monthlyVolumeCountEntityModel =
          genericDAO.findBySql(sqlQuery, scalarList, MonthlyVolumeCountEntityModel.class);

    } catch (ObjectNotFoundException e) {
      log.error("No Object Exists on DB");
    } catch (Exception e) {
      log.error("Error on retrieve:" + e.getMessage());
      e.printStackTrace();
    }

    return monthlyVolumeCountEntityModel;
  }

  @Override
  public List<?> retrieveBatchDailyStReportDebtor(String currentDate) {

    List<MonthlyVolumeCountEntityModel> monthlyVolumeCountEntityModel = new ArrayList<>();

    StringBuffer sb = new StringBuffer();

    sb.append("WITH TEMPTBL AS ( ");
    sb.append(
        "SELECT a.MEMBER_NO AS dr_memno,a.MEMBER_NAME AS dr_memname,c.SERVICE_ID_OUT AS " +
            "outService ");
    sb.append("FROM  CAMOWNER.SYS_CIS_BANK a, ");
    sb.append("CAMOWNER.CAS_SYSCTRL_SERVICES c ");
    sb.append("WHERE c.ACTIVE_IND = 'Y' AND c.SERVICE_ID_IN IS NULL) ");
    sb.append("SELECT aa.outService as service, aa.dr_memno AS instId, ");
    sb.append("COUNT(bb.SERVICE) AS nrOfMsgs ");
    sb.append("FROM TEMPTBL aa ");
    sb.append("LEFT OUTER JOIN CAMOWNER.CAS_OPS_STATUS_HDRS bb ON aa.dr_memno = bb.INSTG_AGT ");
    sb.append(
        "AND substr(bb.CREATE_DATE_TIME,1,10) = TO_DATE('" + currentDate + "','YYYY-MM-DD') ");
    sb.append("AND aa.outService = bb.SERVICE ");
    sb.append("WHERE aa.outService IN ('ST202', 'ST204') ");
    sb.append("GROUP BY aa.outService, aa.dr_memno ");
    sb.append("ORDER BY aa.outService, aa.dr_memno ");

    String sqlQuery = sb.toString();

    List<String> scalarList = new ArrayList<String>();

    scalarList.add("service");
    scalarList.add("instId");
    scalarList.add("nrOfMsgs");

    try {
      monthlyVolumeCountEntityModel =
          genericDAO.findBySql(sqlQuery, scalarList, MonthlyVolumeCountEntityModel.class);

    } catch (ObjectNotFoundException e) {
      log.error("No Object Exists on DB");
    } catch (Exception e) {
      log.error("Error on retrieve:" + e.getMessage());
      e.printStackTrace();
    }

    return monthlyVolumeCountEntityModel;
  }

  @Override
  public List<?> retrievePBBIL05BatchTxnsBillData(String fromDate, String toDate) {

    List<BatchTxnBillReportEntity> batchTxnBillList = new ArrayList<BatchTxnBillReportEntity>();

    StringBuffer sb = new StringBuffer();


    sb.append(
        "SELECT DISTINCT(MANDATE_REQ_TRAN_ID) as mrti, ORIGINATOR as originator,TO_CHAR(TRUNC" +
            "(PROCESS_DATE),'YYYY-MM-DD') as processDate,TO_CHAR(DELIVERY_TIME,'HH24:MI:SS') as " +
            "delTime,SUB_SERVICE as serviceId,TXN_TYPE as txnType,FILE_NAME as fileName ");
    sb.append("from CAMOWNER.MDT_AC_ARC_TXNS_BILL_REPORT ");
//		2023/04/03 SalehaN - NOT IN statement is expensive when processing in DB
    sb.append(
        "where TXN_TYPE = 'TT2' and (SUB_SERVICE = 'MANIN' OR SUB_SERVICE = 'MANAM' OR " +
            "SUB_SERVICE = 'MANCN' OR SUB_SERVICE = 'MANRI') ");
    sb.append(
        "AND PROCESS_DATE between TO_DATE('" + fromDate + "','YYYY-MM-DD') and TO_DATE('" + toDate +
            "','YYYY-MM-DD') ");
//		sb.append("where TXN_TYPE = 'TT2' and SUB_SERVICE NOT IN('SPINP') AND PROCESS_DATE between
//		TO_DATE('"+fromDate+"','YYYY-MM-DD') and TO_DATE('"+toDate+"','YYYY-MM-DD') ");
    sb.append("ORDER BY processDate, serviceId, fileName, mrti ");

    //2022/10/13-DimakatsoN: Pulling data from one table
//		sb.append("with tmptbl1 as ");
//		sb.append("(SELECT distinct b.INSTG_AGT as originator ");
//		sb.append("        ,TO_CHAR(Trunc(b.ARCHIVE_DATE),'YYYY-MM-DD') as processDate ");
//		sb.append("        ,substr(b.ORGNL_MSG_ID,5,5) as serviceId ,'TT2' as txnType ");
//		sb.append("        ,substr(replace(b.ORGNL_MSG_ID, '/'),1,16) || 'M' || substr(replace(b
//		.ORGNL_MSG_ID, '/'),17,14) || (select TRANSAMISSION_IND from manowner
//		.MDT_SYSCTRL_COMP_PARAM) || 'D.xml' as fileName  ");
//		sb.append("        ,NVL(a.TXN_ID,'NF') as mrti  ");
//		sb.append("FROM CAMOWNER.MDT_AC_ARC_STATUS_DETAILS a ");
//		sb.append("LEFT OUTER JOIN CAMOWNER.MDT_AC_ARC_STATUS_HDRS b ");
//		sb.append("ON B.SYSTEM_SEQ_NO = A.STATUS_HDR_SEQ_NO ");
//		sb.append("WHERE a.error_type = 'TXN' and a.error_code <> '902205' and NVL(a.TXN_ID,'NF')
//		<> 'NF' ");
//		sb.append("and B.ORGNL_MSG_NAME IN ('pain.009', 'pain.010', 'pain.011','mdte.001') ");
//		sb.append("AND a.ARCHIVE_DATE between TO_DATE('"+fromDate+"','YYYY-MM-DD') and TO_DATE
//		('"+toDate+"','YYYY-MM-DD') ");
//		sb.append("UNION ALL  ");
//		sb.append("SELECT b.INSTG_AGT as originator ");
//		sb.append("       ,TO_CHAR(Trunc(b.ARCHIVE_DATE),'YYYY-MM-DD') as processDate ");
//		sb.append("       ,substr(b.ORGNL_MSG_ID,5,5) as serviceId ");
//		sb.append("       ,'TT2' as txnType  ");
//		sb.append("       ,substr(replace(b.ORGNL_MSG_ID, '/'),1,16) || 'M' || substr(replace(b
//		.ORGNL_MSG_ID, '/'),17,14) || (select TRANSAMISSION_IND from manowner
//		.MDT_SYSCTRL_COMP_PARAM) || 'D.xml' as fileName  ");
//		sb.append("       ,NVL(a.TXN_ID,'NF') as mrti  ");
//		sb.append("FROM CAMOWNER.MDT_AC_ARC_STATUS_DETAILS a  ");
//		sb.append("LEFT OUTER JOIN CAMOWNER.MDT_AC_ARC_STATUS_HDRS b ON B.SYSTEM_SEQ_NO = A
//		.STATUS_HDR_SEQ_NO  ");
//		sb.append("WHERE a.error_type = 'TXN' and a.error_code = '902205' and NVL(a.TXN_ID,'NF')
//		<> 'NF'  ");
//		sb.append("and B.ORGNL_MSG_NAME IN ('pain.009', 'pain.010', 'pain.011','mdte.001')  ");
//		sb.append("AND a.ARCHIVE_DATE between TO_DATE('"+fromDate+"','YYYY-MM-DD') and TO_DATE
//		('"+toDate+"','YYYY-MM-DD'))  ");
//
//		sb.append("select aa.originator  ");
//		sb.append("        ,aa.processDate  ");
//		sb.append("        ,aa.serviceId  ");
//		sb.append("        ,aa.txnType  ");
//		sb.append("        ,aa.fileName  ");
//		sb.append("        ,aa.mrti  ");
//		sb.append("        ,TO_CHAR(BB.DELIVERY_TIME,'HH24:MI:SS') as delTime  ");
//		sb.append("from tmptbl1 aa  ");
//		sb.append("LEFT OUTER JOIN CAMOWNER.MDT_AC_ARC_TXNS_BILLING  bb on aa.fileName = bb
//		.file_name  ");
//		sb.append("UNION ALL  ");
//		sb.append("SELECT ORIGINATOR AS originator ");
//		sb.append("       ,TO_CHAR(TRUNC(a.ACTION_DATE),'YYYY-MM-DD') as processDate ");
//		sb.append("       ,a.SUB_SERVICE as serviceId ");
//		sb.append("       ,a.TXN_TYPE as txnType ");
//		sb.append("       ,a.FILE_NAME as fileName   ");
//		sb.append("       ,CASE WHEN a.SUB_SERVICE = 'MANRI' THEN c.MDT_INF_REQ_ID ");
//		sb.append("             WHEN a.sub_service <> 'MANRI' then nvl(b.MANDATE_REQ_TRAN_ID, d
//		.mandate_req_tran_id) ");
//		sb.append("         END as mrti ");
//		sb.append("       ,TO_CHAR(A.DELIVERY_TIME,'HH24:MI:SS') as delTime  ");
//		sb.append("FROM CAMOWNER.MDT_AC_ARC_TXNS_BILLING a  ");
//		sb.append("left outer JOIN CAMOWNER.MDT_AC_ARC_MANDATE_TXNS b ON a.FILE_NAME = b
//		.IN_FILE_NAME  ");
//		sb.append("left outer JOIN CAMOWNER.MDT_AC_ARC_MDTE_REQUEST c ON A.FILE_NAME = c
//		.IN_FILE_NAME  ");
//		sb.append("left outer JOIN CAMOWNER.CAS_OPS_CESS_ASSIGN_TXNS d ON A.FILE_NAME = d
//		.IN_FILE_NAME  ");
//		sb.append("WHERE a.SUB_SERVICE IN ('MANIN','MANAM','MANCN', 'MANRI')  ");
//		sb.append("and trunc(ACTION_DATE) between TO_DATE('"+fromDate+"','YYYY-MM-DD') and TO_DATE
//		('"+toDate+"','YYYY-MM-DD') ");
//		sb.append("ORDER BY processDate, serviceId, fileName, mrti ");

    //2022/08/12-SalehaR: Include Data from OPS tables for TXN Billing
//		sb.append("with tmptbl1 as ");
//		sb.append("(SELECT distinct b.INSTG_AGT as originator ,TO_CHAR(Trunc(b.ARCHIVE_DATE),
//		'YYYY-MM-DD') as processDate ,substr(b.ORGNL_MSG_ID,5,5) as serviceId ,'TT2' as txnType ");
//		sb.append(",substr(replace(b.ORGNL_MSG_ID, '/'),1,16) || 'M' || substr(replace(b
//		.ORGNL_MSG_ID, '/'),17,14) || (select TRANSAMISSION_IND from manowner
//		.MDT_SYSCTRL_COMP_PARAM) || 'D.xml' as fileName ");
//		sb.append(",NVL(a.TXN_ID,'NF') as mrti ");
//		sb.append("FROM CAMOWNER.MDT_AC_ARC_STATUS_DETAILS a ");
//		sb.append("LEFT OUTER JOIN CAMOWNER.MDT_AC_ARC_STATUS_HDRS b ON B.SYSTEM_SEQ_NO = A
//		.STATUS_HDR_SEQ_NO ");
//		sb.append("WHERE a.error_type = 'TXN' and a.error_code <> '902205' and NVL(a.TXN_ID,'NF')
//		<> 'NF' ");
//		sb.append("and B.ORGNL_MSG_NAME IN ('pain.009', 'pain.010', 'pain.011','mdte.001') ");
//		sb.append("AND a.ARCHIVE_DATE between TO_DATE('"+fromDate+"','YYYY-MM-DD') and TO_DATE
//		('"+toDate+"','YYYY-MM-DD') ");
//		sb.append("UNION ALL ");
//		sb.append("SELECT b.INSTG_AGT as originator ,TO_CHAR(Trunc(b.ARCHIVE_DATE),'YYYY-MM-DD')
//		as processDate ,substr(b.ORGNL_MSG_ID,5,5) as serviceId ,'TT2' as txnType ");
//		sb.append(",substr(replace(b.ORGNL_MSG_ID, '/'),1,16) || 'M' || substr(replace(b
//		.ORGNL_MSG_ID, '/'),17,14) || (select TRANSAMISSION_IND from manowner
//		.MDT_SYSCTRL_COMP_PARAM) || 'D.xml' as fileName ");
//		sb.append(",NVL(a.TXN_ID,'NF') as mrti ");
//		sb.append("FROM CAMOWNER.MDT_AC_ARC_STATUS_DETAILS a ");
//		sb.append("LEFT OUTER JOIN CAMOWNER.MDT_AC_ARC_STATUS_HDRS b ON B.SYSTEM_SEQ_NO = A
//		.STATUS_HDR_SEQ_NO ");
//		sb.append("WHERE a.error_type = 'TXN' and a.error_code = '902205' and NVL(a.TXN_ID,'NF')
//		<> 'NF' ");
//		sb.append("and B.ORGNL_MSG_NAME IN ('pain.009', 'pain.010', 'pain.011','mdte.001') ");
//		sb.append("AND a.ARCHIVE_DATE between TO_DATE('"+fromDate+"','YYYY-MM-DD') and TO_DATE
//		('"+toDate+"','YYYY-MM-DD')) ");
//
//		sb.append("select aa.originator ");
//		sb.append("        ,aa.processDate ");
//		sb.append("        ,aa.serviceId ");
//		sb.append("        ,aa.txnType ");
//		sb.append("        ,aa.fileName ");
//		sb.append("        ,aa.mrti ");
//		sb.append("        ,TO_CHAR(BB.DELIVERY_TIME,'HH24:MI:SS') as delTime ");
//		sb.append("from tmptbl1 aa ");
//		sb.append("LEFT OUTER JOIN CAMOWNER.MDT_AC_ARC_TXNS_BILLING  bb on aa.fileName = bb
//		.file_name ");
//		sb.append("UNION ALL ");
//		sb.append("SELECT ORIGINATOR AS originator ");
//		sb.append("        ,TO_CHAR(TRUNC(a.ACTION_DATE),'YYYY-MM-DD') as processDate ");
//		sb.append("        ,a.SUB_SERVICE as serviceId ");
//		sb.append("        ,a.TXN_TYPE as txnType ");
//		sb.append("        ,a.FILE_NAME as fileName ");
//		sb.append("        ,CASE WHEN a.SUB_SERVICE = 'MANRI' THEN nvl(c.MDT_INF_REQ_ID,'NF') ELSE
//		nvl(b.MANDATE_REQ_TRAN_ID,'NF') END as mrti ");
//		sb.append("        ,TO_CHAR(A.DELIVERY_TIME,'HH24:MI:SS') as delTime ");
//		sb.append("FROM CAMOWNER.MDT_AC_ARC_TXNS_BILLING a ");
//		sb.append("left outer JOIN CAMOWNER.MDT_AC_ARC_MANDATE_TXNS b ON a.FILE_NAME = b
//		.IN_FILE_NAME ");
//		sb.append("left outer JOIN CAMOWNER.MDT_AC_ARC_MDTE_REQUEST c ON A.FILE_NAME = c
//		.IN_FILE_NAME ");
//		sb.append("WHERE a.SUB_SERVICE IN ('MANIN','MANAM','MANCN', 'MANRI') ");
//		sb.append("AND (nvl(b.MANDATE_REQ_TRAN_ID,'NF') <> 'NF' or nvl(c.MDT_INF_REQ_ID,'NF') <>
//		'NF') ");
//		sb.append("and trunc(ACTION_DATE) between TO_DATE('"+fromDate+"','YYYY-MM-DD') and TO_DATE
//		('"+toDate+"','YYYY-MM-DD') ");
//		sb.append("ORDER BY processDate, serviceId, fileName, mrti ");


    String sqlQuery = sb.toString();
    log.info("PBBIL05 sqlQuery: " + sqlQuery);

    List<String> scalarList = new ArrayList<String>();
    scalarList.add("originator");
    scalarList.add("processDate");
    scalarList.add("serviceId");
    scalarList.add("txnType");
    scalarList.add("fileName");
    scalarList.add("mrti");
    scalarList.add("delTime");

    log.debug("scalarList: " + scalarList);

    batchTxnBillList = genericDAO.findBySql(sqlQuery, scalarList, BatchTxnBillReportEntity.class);

    return batchTxnBillList;
  }

  public List<?> retrieveBatchFileStats(String processDate) {
    List<BatchFileStatsReportEntityModel> fileStatsList =
        new ArrayList<BatchFileStatsReportEntityModel>();

    StringBuffer sb = new StringBuffer();

    sb.append("WITH TMPTBL AS ");
    sb.append(
        "(SELECT a.MEMBER_NO AS memberNo ,a.MEMBER_NAME AS memName  ,c.SERVICE_ID_IN AS inService" +
            " ");
    sb.append(
        "FROM  CAMOWNER.SYS_CIS_BANK a ,CAMOWNER.CAS_SYSCTRL_SERVICES c WHERE c.SERVICE_ID_IN IS " +
            "NOT NULL) ");
    sb.append(",tmptbl2 as ");
    sb.append("(SELECT inst_id as InstId, SERVICE_ID AS service ");
    sb.append(" ,case when NR_OF_MSGS = 1 then SUM(NVL(nr_of_files,0)) end AS NR_OF_FILES_1_TXN ");
    sb.append(
        " ,case when NR_OF_MSGS between 2 and 10 then SUM(NVL(nr_of_files,0)) end AS " +
            "NR_OF_FILES_2_TO_10_TXN ");
    sb.append(
        " ,case when NR_OF_MSGS between 11 and 50 then SUM(NVL(nr_of_files,0)) end AS " +
            "NR_OF_FILES_11_TO_50_TXN ");
    sb.append(
        " ,case when NR_OF_MSGS between 51 and 100 then SUM(NVL(nr_of_files,0)) end AS " +
            "NR_OF_FILES_51_TO_100_TXN ");
    sb.append(
        " ,case when NR_OF_MSGS between 101 and 500 then SUM(NVL(nr_of_files,0)) end AS " +
            "NR_OF_FILES_101_TO_500_TXN ");
    sb.append(
        " ,case when NR_OF_MSGS between 501 and 1000 then SUM(NVL(nr_of_files,0)) end AS " +
            "NR_OF_FILES_501_TO_1000_TXN ");
    sb.append(
        " ,case when NR_OF_MSGS between 1001 and 5000 then SUM(NVL(nr_of_files,0)) end AS " +
            "NR_OF_FILES_1001_TO_5000_TXN ");
    sb.append(
        " ,case when NR_OF_MSGS between 5001 and 10000 then SUM(NVL(nr_of_files,0)) end AS " +
            "NR_OF_FILES_5001_TO_10000_TXN ");
    sb.append(
        " ,case when NR_OF_MSGS between 10001 and 20000 then SUM(NVL(nr_of_files,0)) end AS " +
            "NR_OF_FILES_10001_TO_20000_TXN ");
    sb.append(
        " ,case when NR_OF_MSGS > 20000 then SUM(NVL(nr_of_files,0)) end AS " +
            "NR_OF_FILES_over_20000_TXN ");
    sb.append("FROM CAMOWNER.CAS_OPS_MNDT_COUNT ");
    sb.append(
        "WHERE INCOMING = 'Y' AND PROCESS_DATE = TO_DATE('" + processDate + "','YYYY-MM-DD') ");
    sb.append("group by inst_id, SERVICE_ID, NR_OF_MSGS, nr_of_files ");
    sb.append("order by inst_id) ");
    sb.append("select tt1.memberNo, tt1.memName, tt1.inService ");
    sb.append(
        " ,CASE WHEN tt1.inService IN ('MANIN', 'MANAM', 'MANCN', 'SPINP', 'MANRI') THEN " +
            "'REQUEST' ");
    sb.append("       WHEN tt1.inService IN ('ST101', 'MANAC', 'SRINP', 'MANRT') THEN 'RESPONSE' ");
    sb.append("       ELSE 'NOT APPLICABLE' END AS serviceType ");
    sb.append(" ,sum(NVL(tt2.NR_OF_FILES_1_TXN,0)) as nrOfFiles_1 ");
    sb.append(" ,sum(NVL(tt2.NR_OF_FILES_2_TO_10_TXN,0)) as nrOfFiles_2to10 ");
    sb.append(" ,sum(NVL(tt2.NR_OF_FILES_11_TO_50_TXN,0)) as nrOfFiles_11to50 ");
    sb.append(" ,sum(NVL(tt2.NR_OF_FILES_51_TO_100_TXN,0)) as nrOfFiles_51to100 ");
    sb.append(" ,sum(NVL(tt2.NR_OF_FILES_101_TO_500_TXN,0)) as nrOfFiles_101to500 ");
    sb.append(" ,sum(NVL(tt2.NR_OF_FILES_501_TO_1000_TXN,0)) as nrOfFiles_501to1000 ");
    sb.append(" ,sum(NVL(tt2.NR_OF_FILES_1001_TO_5000_TXN,0)) as nrOfFiles_1001to5000 ");
    sb.append(" ,sum(NVL(tt2.NR_OF_FILES_5001_TO_10000_TXN,0)) as nrOfFiles_5001to10000 ");
    sb.append(" ,sum(NVL(tt2.NR_OF_FILES_10001_TO_20000_TXN,0)) as nrOfFiles_10001to20000 ");
    sb.append(" ,sum(NVL(tt2.NR_OF_FILES_over_20000_TXN,0)) as nrOfFiles_grtr_20000 ");
    sb.append("from tmptbl tt1 ");
    sb.append(
        "left outer join tmptbl2 tt2 on tt1.memberNo = tt2.InstId AND tt1.inService = tt2.service" +
            " ");
    sb.append("group by tt1.memberNo, tt1.memName, tt1.inService ");
    sb.append("order by tt1.memberNo, ");
    sb.append("case when tt1.inService = 'MANIN' THEN 1 ");
    sb.append("     when tt1.inService = 'MANAM' THEN 2 ");
    sb.append("     when tt1.inService = 'MANCN' THEN 3 ");
    sb.append("     when tt1.inService = 'ST101' THEN 4 ");
    sb.append("     when tt1.inService = 'MANAC' THEN 5 ");
    sb.append("     when tt1.inService = 'SPINP' THEN 6 ");
    sb.append("     when tt1.inService = 'SRINP' THEN 7 ");
    sb.append("     when tt1.inService = 'MANDB' THEN 8 ");
    sb.append("     when tt1.inService = 'MANRI' THEN 9 ");
    sb.append("     when tt1.inService = 'MANRT' THEN 10 ");
    sb.append("END ");

    String sqlQuery = sb.toString();
    log.info("retrieveOneTxnFiles sqlQuery: " + sqlQuery);

    List<String> scalarList = new ArrayList<String>();
    scalarList.add("memberNo");
    scalarList.add("memName");
    scalarList.add("inService");
    scalarList.add("serviceType");
    scalarList.add("nrOfFiles_1");
    scalarList.add("nrOfFiles_2to10");
    scalarList.add("nrOfFiles_11to50");
    scalarList.add("nrOfFiles_51to100");
    scalarList.add("nrOfFiles_101to500");
    scalarList.add("nrOfFiles_501to1000");
    scalarList.add("nrOfFiles_1001to5000");
    scalarList.add("nrOfFiles_5001to10000");
    scalarList.add("nrOfFiles_10001to20000");
    scalarList.add("nrOfFiles_grtr_20000");


    fileStatsList =
        genericDAO.findBySql(sqlQuery, scalarList, BatchFileStatsReportEntityModel.class);
//		log.info("fileStatsList: "+fileStatsList.toString());

    return fileStatsList;
  }

  @Override
  public List<?> retrieveRjctTransactios(String frontDate, boolean frontEnd) {
    List<ExceptionReportEntityModel> rjctList = new ArrayList<ExceptionReportEntityModel>();

    StringBuffer sb = new StringBuffer();

    sb.append(
        "SELECT  SUBSTR(b.ORGNL_FILE_NAME, 4, 5) AS serviceId, b.INSTG_AGT AS instructingAgent,b" +
            ".ORGNL_FILE_NAME AS fileName ,a.ERROR_CODE AS errorCode,a.TXN_ID AS mrti, ");
    sb.append(
        "a.MANDATE_REF_NUMBER AS mndtRefNr,a.CR_ABB_SHORT_NAME AS credAbbShrtNm ,a" +
            ".DEBTOR_BRANCH_NO AS drBranchNr ");
    sb.append("FROM CAMOWNER.MDT_AC_ARC_STATUS_DETAILS a ");
    sb.append(
        "LEFT OUTER JOIN CAMOWNER.MDT_AC_ARC_STATUS_HDRS b ON a.STATUS_HDR_SEQ_NO = b" +
            ".SYSTEM_SEQ_NO ");
    if (frontEnd) {
      sb.append(
          "WHERE  (SUBSTR(a.TXN_ID, 0, 4) ='0019' OR a.DEBTOR_BRANCH_NO ='431010') and a" +
              ".ARCHIVE_DATE = TO_DATE('" +
              frontDate + "','YYYY-MM-DD') ");
    } else {
      sb.append(
          "WHERE  (SUBSTR(a.TXN_ID, 0, 4) ='0019' OR a.DEBTOR_BRANCH_NO ='431010') and a" +
              ".ARCHIVE_DATE = TO_DATE('" +
              frontDate + "','YYYY-MM-DD') ");
    }
    String sqlQuery = sb.toString();
    log.info("PBMD12 sqlQuery: " + sqlQuery);

    List<String> scalarList = new ArrayList<String>();

    scalarList.add("serviceId");
    scalarList.add("instructingAgent");
    scalarList.add("fileName");
    scalarList.add("errorCode");
    scalarList.add("mrti");
    scalarList.add("mndtRefNr");
    scalarList.add("credAbbShrtNm");
    scalarList.add("drBranchNr");
    log.debug("scalarList: " + scalarList);

    rjctList = genericDAO.findBySql(sqlQuery, scalarList, ExceptionReportEntityModel.class);


    return rjctList;
  }

}
