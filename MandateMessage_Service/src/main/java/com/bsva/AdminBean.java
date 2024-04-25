package com.bsva;

import com.bsva.beans.GenericDAO;
import com.bsva.businessLogic.AcOpsSotEotLogic;
import com.bsva.businessLogic.AcOpsTransCtrlMsgLogic;
import com.bsva.businessLogic.AccountTypeLogic;
import com.bsva.businessLogic.AdjustmentCategoryLogic;
import com.bsva.businessLogic.AmendmentCodesLogic;
import com.bsva.businessLogic.AuditTrackingLogic;
import com.bsva.businessLogic.AuthTypeLogic;
import com.bsva.businessLogic.CisDownloadLogic;
import com.bsva.businessLogic.CnfgRejectReasonCodesLogic;
import com.bsva.businessLogic.CompanyParametersLogic;
import com.bsva.businessLogic.CurrencyCodeLogic;
import com.bsva.businessLogic.CustParamLogic;
import com.bsva.businessLogic.DebitValueTypeLogic;
import com.bsva.businessLogic.EndOfDayLogic;
import com.bsva.businessLogic.ErrorCodesLogic;
import com.bsva.businessLogic.FileStatusLogic;
import com.bsva.businessLogic.FrequencyCodesLogic;
import com.bsva.businessLogic.IamSessionLogic;
import com.bsva.businessLogic.LocalInstrumentCodesLogic;
import com.bsva.businessLogic.OpsCustParamLogic;
import com.bsva.businessLogic.OpsFileRegLogic;
import com.bsva.businessLogic.OpsFileSizeLimitLogic;
import com.bsva.businessLogic.OpsProcessControlLogic;
import com.bsva.businessLogic.OpsRefSeqNumberLogic;
import com.bsva.businessLogic.OpsServicesLogic;
import com.bsva.businessLogic.OpsSlaTimesLogic;
import com.bsva.businessLogic.OpsStatusDetailsLogic;
import com.bsva.businessLogic.OpsStatusHdrsLogic;
import com.bsva.businessLogic.ReasonCodesLogic;
import com.bsva.businessLogic.ReportsLogic;
import com.bsva.businessLogic.SchedulerScreenLogic;
import com.bsva.businessLogic.SequenceTypeLogic;
import com.bsva.businessLogic.ServicesLogic;
import com.bsva.businessLogic.SeverityCodes2Logic;
import com.bsva.businessLogic.SeverityCodesLogic;
import com.bsva.businessLogic.StartOfDayLogic;
import com.bsva.businessLogic.StatusReasonCodesLogic;
import com.bsva.businessLogic.SysCisBankLogic;
import com.bsva.businessLogic.SysCisBranchLogic;
import com.bsva.businessLogic.SysCtrlFileSizeLimitLogic;
import com.bsva.businessLogic.SysCtrlSlaTimesLogic;
import com.bsva.businessLogic.SysctrlProcessStatusLogic;
import com.bsva.businessLogic.SystemControlServicesLogic;
import com.bsva.businessLogic.SystemStatusLogic;
import com.bsva.businessLogic.SystemsParametersLogic;
import com.bsva.businessLogic.ViewFileStatusLogic;
import com.bsva.cis.common.exceptions.DAOException;
import com.bsva.cis.ejb.controller.CisServiceBean;
import com.bsva.cis.ejb.interfaces.CisServiceRemote;
import com.bsva.cis.persistence.dto.ACMemberDTO;
import com.bsva.cis.persistence.dto.BranchDTO;
import com.bsva.cis.persistence.dto.MemberDTO;
import com.bsva.commons.model.AcOpsSotEotCntrlModel;
import com.bsva.commons.model.AcOpsTransCtrlMsgModel;
import com.bsva.commons.model.AccountTypeModel;
import com.bsva.commons.model.AdjustmentCategoryModel;
import com.bsva.commons.model.AmendmentCodesModel;
import com.bsva.commons.model.AudSystemProcessModel;
import com.bsva.commons.model.AuditTrackingModel;
import com.bsva.commons.model.AuditTrackingSqlModel;
import com.bsva.commons.model.CnfgAuthTypeModel;
import com.bsva.commons.model.CnfgRejectReasonCodesModel;
import com.bsva.commons.model.ConfgErrorCodesModel;
import com.bsva.commons.model.ConfgSeverityCodesModel;
import com.bsva.commons.model.CreditorBankModel;
import com.bsva.commons.model.CurrencyCodesModel;
import com.bsva.commons.model.CustomerParametersModel;
import com.bsva.commons.model.DebitValueTypeModel;
import com.bsva.commons.model.DebtorBankModel;
import com.bsva.commons.model.FileSizeLimitModel;
import com.bsva.commons.model.FileStatusCommonsModel;
import com.bsva.commons.model.FileStatusReportModel;
import com.bsva.commons.model.FrequencyCodesModel;
import com.bsva.commons.model.IamSessionModel;
import com.bsva.commons.model.IncSotEotModel;
import com.bsva.commons.model.LocalInstrumentCodesModel;
import com.bsva.commons.model.MandateAmendModel;
import com.bsva.commons.model.MandateDailyTransModel;
import com.bsva.commons.model.MandateRejectionModel;
import com.bsva.commons.model.MandateResponseOutstandingPerBankModel;
import com.bsva.commons.model.MandatesCountCommonsModel;
import com.bsva.commons.model.MndtSummaryTotalsModel;
import com.bsva.commons.model.OpsCustParamModel;
import com.bsva.commons.model.OpsFileRegModel;
import com.bsva.commons.model.OpsProcessControlCommonsModel;
import com.bsva.commons.model.OpsProcessControlModel;
import com.bsva.commons.model.OpsRefSeqNumberCommonsModel;
import com.bsva.commons.model.OpsSlaTimesCommonsModel;
import com.bsva.commons.model.OpsStatusDetailsModel;
import com.bsva.commons.model.OpsStatusHdrsModel;
import com.bsva.commons.model.OutSotEotModel;
import com.bsva.commons.model.OutstandingResponsesDebtorModel;
import com.bsva.commons.model.OutstandingResponsesModel;
import com.bsva.commons.model.ProcessStatusModel;
import com.bsva.commons.model.ReasonCodesModel;
import com.bsva.commons.model.ReportsNamesModel;
import com.bsva.commons.model.SchedulerCommonsModel;
import com.bsva.commons.model.SchedulerCronModel;
import com.bsva.commons.model.SequenceTypesModel;
import com.bsva.commons.model.ServicesModel;
import com.bsva.commons.model.SeverityCodesModel;
import com.bsva.commons.model.StatusReasonCodesModel;
import com.bsva.commons.model.SysCisBankModel;
import com.bsva.commons.model.SysCisBranchModel;
import com.bsva.commons.model.SysCtrlFileSizeLimitModel;
import com.bsva.commons.model.SysCtrlSlaTimeModel;
import com.bsva.commons.model.SysctrlCompParamModel;
import com.bsva.commons.model.SystemControlServicesModel;
import com.bsva.commons.model.SystemParameterModel;
import com.bsva.commons.model.TransCtrlMsgModel;
import com.bsva.delivery.EndOfTransmissionExtract;
import com.bsva.delivery.StartOfTransmissionExtract;
import com.bsva.entities.AudSystemProcessEntity;
import com.bsva.entities.AudTrackingEntity;
import com.bsva.entities.AuditTrackingEntityModel;
import com.bsva.entities.CasOpsSotEotEntityModel;
import com.bsva.entities.CasSysctrlCompParamEntity;
import com.bsva.entities.CasSysctrlCustParamEntity;
import com.bsva.entities.CasSysctrlFileSizeLimitEntity;
import com.bsva.entities.CasSysctrlFileStatusEntity;
import com.bsva.entities.CasSysctrlProcessStatusEntity;
import com.bsva.entities.CasSysctrlSchedulerCronEntity;
import com.bsva.entities.CasSysctrlSchedulerEntity;
import com.bsva.entities.CasSysctrlServicesEntity;
import com.bsva.entities.CasSysctrlSlaTimesEntity;
import com.bsva.entities.CasSysctrlSysParamEntity;
import com.bsva.entities.CisMemberEntity;
import com.bsva.entities.CreditorBankEntityModel;
import com.bsva.entities.DebtorBankEntityModel;
import com.bsva.entities.FileStatusReportEntityModel;
import com.bsva.entities.IamSessionEntity;
import com.bsva.entities.IncSotEotEntityModel;
import com.bsva.entities.MandateAmendEntityModel;
import com.bsva.entities.MandateDailyTransEntityModel;
import com.bsva.entities.MandateRejectionEntityModel;
import com.bsva.entities.MandateResponseOutstandingPerBankEntityModel;
import com.bsva.entities.MandatesCountCommonsModelEntity;
import com.bsva.entities.MdtAcArcDailyBillingEntity;
import com.bsva.entities.MdtAcOpsDailyBillingEntity;
import com.bsva.entities.MdtAcOpsFileSizeLimitEntity;
import com.bsva.entities.MdtAcOpsMndtCountEntity;
import com.bsva.entities.MdtAcOpsProcessControlsEntity;
import com.bsva.entities.MdtAcOpsSchedulerEntity;
import com.bsva.entities.MdtAcOpsSotEotCtrlEntity;
import com.bsva.entities.MdtAcOpsStatusDetailsEntity;
import com.bsva.entities.MdtAcOpsStatusHdrsEntity;
import com.bsva.entities.MdtAcOpsTransCtrlMsgEntity;
import com.bsva.entities.MdtCnfgAccountTypeEntity;
import com.bsva.entities.MdtCnfgAdjustmentCatEntity;
import com.bsva.entities.MdtCnfgAmendmentCodesEntity;
import com.bsva.entities.MdtCnfgAuthTypeEntity;
import com.bsva.entities.MdtCnfgCurrencyCodesEntity;
import com.bsva.entities.MdtCnfgDebitValueTypeEntity;
import com.bsva.entities.MdtCnfgErrorCodesEntity;
import com.bsva.entities.MdtCnfgFrequencyCodesEntity;
import com.bsva.entities.MdtCnfgLocalInstrCodesEntity;
import com.bsva.entities.MdtCnfgReasonCodesEntity;
import com.bsva.entities.MdtCnfgRejectReasonCodesEntity;
import com.bsva.entities.MdtCnfgReportNamesEntity;
import com.bsva.entities.MdtCnfgSequenceTypeEntity;
import com.bsva.entities.MdtCnfgSeverityCodesEntity;
import com.bsva.entities.MdtCnfgStatusReasonCodesEntity;
import com.bsva.entities.MdtOpsCustParamEntity;
import com.bsva.entities.MdtOpsFileRegEntity;
import com.bsva.entities.MdtOpsLastExtractTimesEntity;
import com.bsva.entities.MdtOpsProcessControlsEntity;
import com.bsva.entities.MdtOpsRefSeqNrEntity;
import com.bsva.entities.MdtOpsRepSeqNrEntity;
import com.bsva.entities.MdtOpsServicesEntity;
import com.bsva.entities.MdtOpsSlaTimesEntity;
import com.bsva.entities.MndtSummaryTotalsEntityModel;
import com.bsva.entities.ObsSystemBillingCtrlsEntity;
import com.bsva.entities.OutSotEotEntityModel;
import com.bsva.entities.OutstandingResponsesDebtorModelEntity;
import com.bsva.entities.OutstandingResponsesModelEntity;
import com.bsva.entities.StatusReportEotModelEntity;
import com.bsva.entities.SysCisBankEntity;
import com.bsva.entities.SysCisBranchEntity;
import com.bsva.interfaces.AdminBeanLocal;
import com.bsva.interfaces.AdminBeanRemote;
import com.bsva.mandatesArchive.AC_ArchiveMessages_ST;
import com.bsva.reports.DailyBatchBillableTxnCreditorReport;
import com.bsva.reports.DailyBatchBillableTxnDebtorReport;
import com.bsva.reports.DailyBatchBillableTxnReport;
import com.bsva.reports.DailyBatchVolumeReport;
import com.bsva.reports.DailyReportsLogic;
import com.bsva.reports.Exception_Report_CSV;
import com.bsva.reports.MR020_BSAMonthlyBatchVolumesReport;
import com.bsva.reports.MonthlyReportsLogic;
import com.bsva.reports.PBBIL05_CSV_BatchMandatesTxnsBillReport;
import com.bsva.reports.PBMD01_DailyOutstandingResponsesReportCSV;
import com.bsva.reports.PBMD01_DailyOutstandingResponsesReportPDF;
import com.bsva.reports.PBMD08_DailyFiveDayOutstRespCSV;
import com.bsva.reports.PBMD08_DailyFiveDayOutstRespPDF;
import com.bsva.reports.PSMD08_FileStatsBatchExcelReport;
import com.bsva.reports.PartBanksBatchRejectionsReport;
import com.bsva.reports.PasaBatchAmendmentReport;
import com.bsva.reports.PasaBatchOutstandingResponsesReport;
import com.bsva.reports.PasaBatchRejectionsReport;
import com.bsva.reports.PasaMandateAmendmentReport;
import com.bsva.translator.AdminTranslator;
import com.bsva.translator.ServiceTranslator;
import com.bsva.utils.DateUtil;
import com.bsva.utils.Util;
import com.itextpdf.text.DocumentException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import org.apache.log4j.Logger;
import org.hibernate.ObjectNotFoundException;

/**
 * Session Bean implementation class AdminBean
 *
 * @author SalehaR-2019/05/06 Debug Statements & Code CleanUp
 */
@Stateless
@Remote(AdminBeanRemote.class)
public class AdminBean implements AdminBeanRemote, AdminBeanLocal {
  @EJB
  GenericDAO genericDAO;

  DAOException daoException;
  public static Logger log = Logger.getLogger(AdminBean.class);
  //	public static    CisServiceRemote cisServiceRemote ;
  public static String feedbackMsg = null;
  public static String eodfeedbackMsg = null;
  public static String cisFeedbackMsg = null;
  String manin = "MANIN";
  String manam = "MANAM";
  String mancn = "MANCN";
  String manrt = "MANRT";
  String srinp = "SRINP";
  String tt2TxnType = "TT2";
  String tt1TxnType = "TT1";
  String tt3TxnType = "TT3";

  Date startDate, endDate;

  public static CisServiceRemote cisServiceRemote;

  /**
   * git bash
   * Default constructor.
   */

  public AdminBean() {
    // TODO Auto-generated constructor stub
    contextCISServiceBeanCheck();
  }


  @Override
  public List<?> viewAllLocalInstrumentCodes() {
    List<LocalInstrumentCodesModel> allLocalInstrCodes = new ArrayList<LocalInstrumentCodesModel>();
    List<MdtCnfgLocalInstrCodesEntity> allMdtLocalInstrCodesEntityList =
        new ArrayList<MdtCnfgLocalInstrCodesEntity>();
    allMdtLocalInstrCodesEntityList = genericDAO.findAll(MdtCnfgLocalInstrCodesEntity.class);


    if (allMdtLocalInstrCodesEntityList.size() > 0) {
      LocalInstrumentCodesLogic localInsLogic = new LocalInstrumentCodesLogic();
      allLocalInstrCodes =
          localInsLogic.retrieveAllLocalInstrumentCodes(allMdtLocalInstrCodesEntityList);
    }

    return allLocalInstrCodes;
  }


  @Override
  public List<?> viewAllSchedulers() {
    List<SchedulerCommonsModel> allSchedulerList = new ArrayList<SchedulerCommonsModel>();
    List<CasSysctrlSchedulerEntity> allMdtSysctrlSchedulerEntityList =
        new ArrayList<CasSysctrlSchedulerEntity>();

    //		allMdtSysctrlSchedulerEntityList = genericDAO.findAll(MdtSysctrlSchedulerEntity.class);
    allMdtSysctrlSchedulerEntityList =
        genericDAO.findAllOrdered(CasSysctrlSchedulerEntity.class, "schedulerKey ASC ");

    log.debug("The entity list has the following information ##############" +
        allMdtSysctrlSchedulerEntityList);
    if (allMdtSysctrlSchedulerEntityList.size() > 0) {
      SchedulerScreenLogic schedulerScreenLogic = new SchedulerScreenLogic();
      allSchedulerList =
          schedulerScreenLogic.retrieveAllSchedulers(allMdtSysctrlSchedulerEntityList);

      log.debug(
          "The commons model list has this information %%%%%%%%%%%%%%%%%%%%%%" + allSchedulerList);
    }


    return allSchedulerList;

  }


  public boolean createLocalInstrCode(Object obj) {
    try {

      if (obj instanceof LocalInstrumentCodesModel) {
        LocalInstrumentCodesModel localInstrCodesModel = (LocalInstrumentCodesModel) obj;
        LocalInstrumentCodesLogic localInsLogic = new LocalInstrumentCodesLogic();
        MdtCnfgLocalInstrCodesEntity mdtLocalInstrumentCodesEntity =
            new MdtCnfgLocalInstrCodesEntity();

        mdtLocalInstrumentCodesEntity = localInsLogic.addLocalInstrumentCode(localInstrCodesModel);
        genericDAO.saveOrUpdate(mdtLocalInstrumentCodesEntity);
        return true;
      } else {
        log.debug("Unable to convert type to LocalInstrumentCodesModel.");
        return false;
      }
    } catch (Exception e) {
      log.error("Error on createLocalInstrCode: " + e.getMessage());
      e.printStackTrace();
      return false;
    }

  }


  @Override
  public boolean updateACOpsEOTSOT(Object obj) {
    try {

      if (obj instanceof MdtAcOpsSotEotCtrlEntity) {
        MdtAcOpsSotEotCtrlEntity mdtAcOpsSotEotCtrlEntity = (MdtAcOpsSotEotCtrlEntity) obj;
        genericDAO.saveOrUpdate(mdtAcOpsSotEotCtrlEntity);
        return true;
      } else {
        log.debug("Unable to convert type to MdtAcOpsSotEotCtrlEntity .");
        return false;
      }
    } catch (Exception e) {
      log.error("Error on updateACOpsEOTSOT: " + e.getMessage());
      e.printStackTrace();
      return false;
    }

  }

  @Override
  public List<?> retrieveOpsCount(String instId, String serviceId) {

    log.debug("instId---> " + instId);
    log.debug("serviceId---> " + serviceId);
    List<MandatesCountCommonsModelEntity> mdtList =
        new ArrayList<MandatesCountCommonsModelEntity>();
    MandatesCountCommonsModelEntity mndtCountCommonsModel = null;

    StringBuffer sb = new StringBuffer();
    sb.append(
        "SELECT  INST_ID as instId, SERVICE_ID as serviceId,  COUNT(NR_OF_FILES) AS nrOfFiles, " +
            "SUM(NR_OF_MSGS) as nrOfMsgs  ");
    sb.append("FROM MANOWNER.MDT_AC_OPS_MNDT_COUNT  ");
    sb.append("WHERE INST_ID = '" + instId + "' AND SERVICE_ID = '" + serviceId + "' ");
    sb.append("GROUP BY  INST_ID , SERVICE_ID");

    String sqlQuery = sb.toString();
    log.debug("The Sql query is  ***************" + sqlQuery);
    List<String> scalarList = new ArrayList<String>();
    scalarList.add("instId");
    scalarList.add("serviceId");
    scalarList.add("nrOfFiles");
    scalarList.add("nrOfMsgs");


    mdtList = genericDAO.findBySql(sqlQuery, scalarList, MandatesCountCommonsModelEntity.class);
    log.debug("mndtCountCommonsModel from Db----> " + mdtList.toString());
    return mdtList;

  }


  @Override
  public Object retrieveFileRecordsGroupedByID(String memberNo, String serviceId) {
    MdtAcOpsMndtCountEntity mdtOpsMndtCountEntity = new MdtAcOpsMndtCountEntity();
    try {
      log.debug("memberNo: " + memberNo);
      log.debug("serviceId: " + serviceId);

      HashMap<String, Object> parameters = new HashMap<String, Object>();
      parameters.put("mdtOpsMndtCountPK.instId", memberNo);
      parameters.put("mdtOpsMndtCountPK.serviceId", serviceId);
      log.debug("---------------sparameters: ------------------" + parameters.toString());
      mdtOpsMndtCountEntity =
          (MdtAcOpsMndtCountEntity) genericDAO.findByCriteria(MdtAcOpsMndtCountEntity.class,
              parameters);
      log.debug("---------------mdtOpsMndtCountEntity after findByCriteria: ------------------" +
          mdtOpsMndtCountEntity);
    } catch (ObjectNotFoundException onfe) {
      log.debug("No Object Exists on DB");
    } catch (Exception e) {
      log.error("Error on retrieveSOTEOTCntrl: " + e.getMessage());
      e.printStackTrace();
    }

    return mdtOpsMndtCountEntity;
  }


  @Override
  public List<?> viewAllAuthTypes() {
    List<CnfgAuthTypeModel> allAuthTypes = new ArrayList<CnfgAuthTypeModel>();
    List<MdtCnfgAuthTypeEntity> allMdtCnfgAuthTypeEntityList =
        new ArrayList<MdtCnfgAuthTypeEntity>();

    allMdtCnfgAuthTypeEntityList = genericDAO.findAll(MdtCnfgAuthTypeEntity.class);

    if (allMdtCnfgAuthTypeEntityList.size() > 0) {
      AuthTypeLogic authTypeLogic = new AuthTypeLogic();
      allAuthTypes = authTypeLogic.retrieveAllAuthTypes(allMdtCnfgAuthTypeEntityList);
    }

    return allAuthTypes;
  }

	/*public List<?> viewAllMandatesCountPerIncomingFiles()

                {
                                List<MandatesCountCommonsModel> mandatesCountList = new
                                ArrayList<MandatesCountCommonsModel>();
                                List<MdtOpsMndtCountEntity> mdtOpsCountList = new
                                ArrayList<MdtOpsMndtCountEntity>();

                                MdtOpsMndtCountPK localEntityPK = new MdtOpsMndtCountPK();

                                StringBuffer sb = new StringBuffer();
                                sb.append("SELECT FILE_NAME as fileName,INST_ID as
                                mdtOpsMndtCountPK.instId,NR_OF_MSGS as nrOfMsgs,NR_MSGS_REJECTED
                                as nrMsgsRejected, INCOMING AS incoming");
                                sb.append("FROM MDT_OPS_MNDT_COUNT ");
                                sb.append("WHERE INCOMING='Y'");

                                String sqlQuery = sb.toString();
                                log.info("sqlQuery: " + sqlQuery);

                                List<String> scalarList = new ArrayList<String>();
                                scalarList.add("fileName");
                                scalarList.add("mdtOpsMndtCountPK.instId");
                                scalarList.add("nrOfMsgs");
                                scalarList.add("nrMsgsRejected");
                                scalarList.add("incoming");


                                mdtOpsCountList = genericDAO.findBySql(sqlQuery, scalarList,
                                MdtOpsMndtCountEntity.class);

                                log.info("the list has this info ***************"+mdtOpsCountList);
                                if (mdtOpsCountList.size()>0)
                                {

          for (MdtOpsMndtCountEntity localEntity :mdtOpsCountList)
          {

                                  MandatesCountCommonsModel   localModel = new
                                  MandatesCountCommonsModel();
                                  localModel = new AdminTranslator()
                                  .translateMdtOpsMndtCountEntityToCommonsModel(localEntity);
                                  mandatesCountList.add(localModel);
                  }
          }

                                return mandatesCountList;

}*/

  public List<?> viewAllMandatesCountPerIncomingFiles() {
    List<MdtAcOpsMndtCountEntity> mdtOpsCountEntityList = new ArrayList<MdtAcOpsMndtCountEntity>();
    List<MandatesCountCommonsModel> mandatesCountList = new ArrayList<MandatesCountCommonsModel>();

    mdtOpsCountEntityList = (List<MdtAcOpsMndtCountEntity>) genericDAO.findAllByNamedQuery(
        "MdtAcOpsMndtCountEntity.findByIncoming", "incoming", "Y");


    if (mdtOpsCountEntityList.size() > 0) {

      for (MdtAcOpsMndtCountEntity localEntity : mdtOpsCountEntityList) {

        MandatesCountCommonsModel localModel = new MandatesCountCommonsModel();
        localModel =
            new AdminTranslator().translateMdtOpsMndtCountEntityToCommonsModel(localEntity);
        mandatesCountList.add(localModel);
      }
    }

    return mandatesCountList;


  }


  public List<?> viewAllMessages() {
    List<MdtAcOpsMndtCountEntity> mdtOpsCountEntityList = new ArrayList<MdtAcOpsMndtCountEntity>();
    List<MandatesCountCommonsModel> mandateCountList = new ArrayList<MandatesCountCommonsModel>();

    mdtOpsCountEntityList = genericDAO.findAll(MdtAcOpsMndtCountEntity.class);

    if (mdtOpsCountEntityList.size() > 0) {
      for (MdtAcOpsMndtCountEntity localEntity : mdtOpsCountEntityList) {
        MandatesCountCommonsModel localModel = new MandatesCountCommonsModel();
        localModel =
            new AdminTranslator().translateMdtOpsMndtCountEntityToCommonsModel(localEntity);
        mandateCountList.add(localModel);
      }
    }

    return mandateCountList;
  }

  public List<?> viewAllMandatesMessagesPerOutGoingFiles() {
    List<MdtAcOpsMndtCountEntity> mdtOpsCountEntityList = new ArrayList<MdtAcOpsMndtCountEntity>();

    List<MandatesCountCommonsModel> mandateCountList = new ArrayList<MandatesCountCommonsModel>();

    mdtOpsCountEntityList = (List<MdtAcOpsMndtCountEntity>) genericDAO.findAllByNamedQuery(
        "MdtAcOpsMndtCountEntity.findByOutgoing", "outgoing", "Y");


    if (mdtOpsCountEntityList.size() > 0) {
      for (MdtAcOpsMndtCountEntity localEntity : mdtOpsCountEntityList) {
        MandatesCountCommonsModel localModel = new MandatesCountCommonsModel();
        localModel =
            new AdminTranslator().translateMdtOpsMndtCountEntityToCommonsModel(localEntity);
        mandateCountList.add(localModel);
      }

    }

    return mandateCountList;

  }


  public boolean createAuthtype(Object obj) {
    try {

      if (obj instanceof CnfgAuthTypeModel) {
        CnfgAuthTypeModel localCnfgAuthTypesModel = (CnfgAuthTypeModel) obj;
        AuthTypeLogic authTypeLogic = new AuthTypeLogic();
        MdtCnfgAuthTypeEntity mdtCnfgAuthTypeEntity = new MdtCnfgAuthTypeEntity();

        mdtCnfgAuthTypeEntity = authTypeLogic.addAuthType(localCnfgAuthTypesModel);
        genericDAO.saveOrUpdate(mdtCnfgAuthTypeEntity);
        return true;
      } else {
        log.debug("Unable to convert type to Auth type.");
        return false;
      }
    } catch (Exception e) {
      log.error("Error on createAuthType: " + e.getMessage());
      e.printStackTrace();
      return false;
    }

  }

  @Override
  public List<?> viewAuthTypeByCriteria(String authType) {

    List<CnfgAuthTypeModel> allAuthType = new ArrayList<CnfgAuthTypeModel>();

    try {

      List<MdtCnfgAuthTypeEntity> mdtCnfgAuthTypeEntityList = genericDAO
          .findAllByNamedQuery("MdtCnfgAuthTypeEntity.findByAuthType", "authType",
              authType.toUpperCase());

      if (mdtCnfgAuthTypeEntityList.size() > 0) {

        AuthTypeLogic authTypeLogic = new AuthTypeLogic();
        for (MdtCnfgAuthTypeEntity mdtCnfgAuthTypeEntity : mdtCnfgAuthTypeEntityList) {
          CnfgAuthTypeModel authTypeModel = new CnfgAuthTypeModel();
          authTypeModel = authTypeLogic.retrieveAuthType(mdtCnfgAuthTypeEntity);
          allAuthType.add(authTypeModel);
        }
      }

    } catch (ObjectNotFoundException onfe) {
      log.debug("No Object Exists on DB");
    } catch (Exception e) {

      log.error("Error on viewAuthTypeByCriteria: " + e.getMessage());
      e.printStackTrace();
    }

    return allAuthType;
  }

  @Override
  public List<?> viewAllSequenceTypes() {
    List<SequenceTypesModel> allSequenceTypes = new ArrayList<SequenceTypesModel>();

    List<MdtCnfgSequenceTypeEntity> allMdtSeqTypeEntityList =
        new ArrayList<MdtCnfgSequenceTypeEntity>();

    allMdtSeqTypeEntityList = genericDAO.findAll(MdtCnfgSequenceTypeEntity.class);

    if (allMdtSeqTypeEntityList.size() > 0) {
      SequenceTypeLogic sequenceLogic = new SequenceTypeLogic();
      allSequenceTypes = sequenceLogic.retrieveAllSequenceTypes(allMdtSeqTypeEntityList);
    }

    return allSequenceTypes;
  }

  @Override
  public boolean createSequenceTypes(Object obj) {
    try {

      if (obj instanceof SequenceTypesModel) {
        SequenceTypesModel sequenceTypesModel = (SequenceTypesModel) obj;
        SequenceTypeLogic sequenceTypeLogic = new SequenceTypeLogic();
        MdtCnfgSequenceTypeEntity mdtSequenceTypeEntity = new MdtCnfgSequenceTypeEntity();
        mdtSequenceTypeEntity = sequenceTypeLogic.addSequenceTypeCode(sequenceTypesModel);
        genericDAO.saveOrUpdate(mdtSequenceTypeEntity);
        return true;
      } else {
        log.debug("Unable to convert type to SequenceTypesModel.");
        return false;
      }
    } catch (Exception e) {
      log.error("Error on createSequenceTypes: " + e.getMessage());
      e.printStackTrace();
      return false;
    }
  }

  @Override
  public List<?> viewAllCurrencyCodes() {
    List<CurrencyCodesModel> allCurrencyCodes = new ArrayList<CurrencyCodesModel>();
    List<MdtCnfgCurrencyCodesEntity> allMdtCurrencyCodesEntityList =
        new ArrayList<MdtCnfgCurrencyCodesEntity>();
    allMdtCurrencyCodesEntityList = genericDAO.findAll(MdtCnfgCurrencyCodesEntity.class);

    if (allMdtCurrencyCodesEntityList.size() > 0) {
      CurrencyCodeLogic currencyCodeLogic = new CurrencyCodeLogic();
      allCurrencyCodes = currencyCodeLogic.retrieveAllCurrencyCodes(allMdtCurrencyCodesEntityList);

    }

    return allCurrencyCodes;
  }

  @Override
  public List<?> viewAllDebitValueTypes() {
    List<DebitValueTypeModel> allDebitValueTypes = new ArrayList<DebitValueTypeModel>();
    List<MdtCnfgDebitValueTypeEntity> allMdtDebitValueTypesEntityList =
        new ArrayList<MdtCnfgDebitValueTypeEntity>();
    allMdtDebitValueTypesEntityList = genericDAO.findAll(MdtCnfgDebitValueTypeEntity.class);

    if (allMdtDebitValueTypesEntityList.size() > 0) {
      DebitValueTypeLogic debitValueTypeLogic = new DebitValueTypeLogic();
      allDebitValueTypes =
          debitValueTypeLogic.retrieveAllDebitValueType(allMdtDebitValueTypesEntityList);
    }
    log.debug("allDebitValueTypes-->" + allDebitValueTypes.toString());
    return allDebitValueTypes;
  }

  @Override
  public List<?> viewAllReasonCodes() {

    List<ReasonCodesModel> allReasonCodes = new ArrayList<ReasonCodesModel>();
    List<MdtCnfgReasonCodesEntity> allMdtReasonCodesList =
        new ArrayList<MdtCnfgReasonCodesEntity>();
    allMdtReasonCodesList = genericDAO.findAll(MdtCnfgReasonCodesEntity.class);

    if (allMdtReasonCodesList.size() > 0) {
      ReasonCodesLogic reasonCodesLogic = new ReasonCodesLogic();
      allReasonCodes = reasonCodesLogic.retreiveAllReasonCodes(allMdtReasonCodesList);

    }

    return allReasonCodes;
  }

  @Override
  public boolean createReasonCodes(Object obj) {

    try {

      if (obj instanceof ReasonCodesModel) {
        ReasonCodesModel reasonCodesModel = (ReasonCodesModel) obj;
        ReasonCodesLogic reasonCodesLogic = new ReasonCodesLogic();
        MdtCnfgReasonCodesEntity mdtReasonCodesEntity = new MdtCnfgReasonCodesEntity();

        mdtReasonCodesEntity = reasonCodesLogic.addReasonCodes(reasonCodesModel);
        genericDAO.saveOrUpdate(mdtReasonCodesEntity);
        return true;
      } else {
        log.debug("Unable to convert type to ReasonCodesModel.");
        return false;
      }
    } catch (Exception e) {
      log.error("Error on createReasonCode: " + e.getMessage());
      e.printStackTrace();
      return false;
    }
  }

  @Override
  public boolean createCurrencyCodes(Object obj) {
    try {

      if (obj instanceof CurrencyCodesModel) {
        CurrencyCodesModel currencyCodesModel = (CurrencyCodesModel) obj;
        CurrencyCodeLogic currencyCodeLogic = new CurrencyCodeLogic();
        MdtCnfgCurrencyCodesEntity mdtCurrencyCodesEntity = new MdtCnfgCurrencyCodesEntity();

        mdtCurrencyCodesEntity = currencyCodeLogic.addCurrencyCodes(currencyCodesModel);
        genericDAO.saveOrUpdate(mdtCurrencyCodesEntity);
        return true;
      } else {
        log.debug("Unable to convert type to CurrencyCodesModel.");
        return false;
      }
    } catch (Exception e) {
      log.error("Error on createCurrencyCodes: " + e.getMessage());
      e.printStackTrace();
      return false;
    }
  }

  @Override
  public boolean createFrequencyCodes(Object obj) {
    try {

      if (obj instanceof FrequencyCodesModel) {
        FrequencyCodesModel frequencyCodesModel = (FrequencyCodesModel) obj;
        FrequencyCodesLogic frequencyCodesLogic = new FrequencyCodesLogic();
        MdtCnfgFrequencyCodesEntity mdtFrequencyCodesEntity = new MdtCnfgFrequencyCodesEntity();

        mdtFrequencyCodesEntity = frequencyCodesLogic.addFrequencyCodes(frequencyCodesModel);
        genericDAO.saveOrUpdate(mdtFrequencyCodesEntity);
        return true;
      } else {
        log.debug("Unable to convert type to FrequencyCodesModel.");
        return false;
      }
    } catch (Exception e) {
      log.error("Error on createFrequencyCode: " + e.getMessage());
      e.printStackTrace();
      return false;
    }

  }

  @Override
  public boolean createdebValueTypeCode(Object obj) {
    try {

      if (obj instanceof DebitValueTypeModel) {
        DebitValueTypeModel debitValueTypeModel = (DebitValueTypeModel) obj;
        DebitValueTypeLogic debitValueTypeLogic = new DebitValueTypeLogic();
        MdtCnfgDebitValueTypeEntity mdtDebitValueTypeEntity = new MdtCnfgDebitValueTypeEntity();

        mdtDebitValueTypeEntity = debitValueTypeLogic.adddebValueTypeCode(debitValueTypeModel);
        genericDAO.saveOrUpdate(mdtDebitValueTypeEntity);
        return true;
      } else {
        log.debug("Unable to convert type to DebitValueTypeModel.");
        return false;
      }
    } catch (Exception e) {
      log.error("Error on createDebitValueTypeCode: " + e.getMessage());
      e.printStackTrace();
      return false;
    }

  }

  @Override
  public List<?> viewAllFrequencyCodes() {

    List<FrequencyCodesModel> allFrequencyCodes = new ArrayList<FrequencyCodesModel>();
    List<MdtCnfgFrequencyCodesEntity> allMdtFrequencyCodesEntityList =
        new ArrayList<MdtCnfgFrequencyCodesEntity>();
    allMdtFrequencyCodesEntityList = genericDAO.findAll(MdtCnfgFrequencyCodesEntity.class);

    if (allMdtFrequencyCodesEntityList.size() > 0) {
      FrequencyCodesLogic frequencyCodesLogic = new FrequencyCodesLogic();
      allFrequencyCodes =
          frequencyCodesLogic.retreiveAllFrequencyCodes(allMdtFrequencyCodesEntityList);

    }

    return allFrequencyCodes;
  }

  public List<?> viewLocalInstrumentCodesByCriteria(String localInstrumentCode) {

    List<LocalInstrumentCodesModel> allLocalInstrCodes = new ArrayList<LocalInstrumentCodesModel>();

    try {

      List<MdtCnfgLocalInstrCodesEntity> mdtLocalInstrCodesList = genericDAO.findAllByNamedQuery(
          "MdtCnfgLocalInstrCodesEntity.findByLocalInstrumentCodeLIKE", "localInstrumentCode",
          localInstrumentCode + "%");

      if (mdtLocalInstrCodesList.size() > 0) {
        LocalInstrumentCodesLogic localInsLogic = new LocalInstrumentCodesLogic();
        for (MdtCnfgLocalInstrCodesEntity localEntity : mdtLocalInstrCodesList) {
          LocalInstrumentCodesModel localModel = new LocalInstrumentCodesModel();
          localModel = localInsLogic.retrieveLocalInstrumentCode(localEntity);
          allLocalInstrCodes.add(localModel);
        }
      }
    } catch (ObjectNotFoundException onfe) {
      log.debug("No Object Exists on DB");
    } catch (Exception e) {
      log.error("Error on retrieveLocalInstrumentCodesByCriteria: " + e.getMessage());
      e.printStackTrace();
    }
    return allLocalInstrCodes;
  }

//Not needed for C&A
//  public List<?> viewSystemControlCronTimesCodesByCriteria(String processName) {
//
//    List<CronTimesModel> allCronTimes = new ArrayList<CronTimesModel>();
//
//    try {
//
//      List<MdtSysctrlCronEntity> mdtCronTimesList = genericDAO.findAllByNamedQuery(
//          "MdtSysctrlCronEntity.findByProcessName", "processName", processName.toUpperCase());
//
//      if (mdtCronTimesList.size() > 0) {
//        CronTimesLogic cronTimesLogic = new CronTimesLogic();
//        for (MdtSysctrlCronEntity cronTimeEntity : mdtCronTimesList) {
//          CronTimesModel cronTimesModel = new CronTimesModel();
//          cronTimesModel = cronTimesLogic.retrieveCronTimeCode(cronTimeEntity);
//          allCronTimes.add(cronTimesModel);
//        }
//      }
//
//    } catch (ObjectNotFoundException onfe) {
//      log.debug("No Object Exists on DB");
//    } catch (Exception e) {
//      log.error("Error on retrievingCronTimeByCriteria: " + e.getMessage());
//      e.printStackTrace();
//    }
//    return allCronTimes;
//  }

  public List<?> viewReasonCodesByCriteria(String reasonCode) {

    List<ReasonCodesModel> allReasonCodes = new ArrayList<ReasonCodesModel>();

    try {

      List<MdtCnfgReasonCodesEntity> mdtReasonCodesList =
          genericDAO.findAllByNamedQuery("MdtCnfgReasonCodesEntity.findByReasonCodeLIKE",
              "reasonCode", reasonCode.toUpperCase() + "%");

      if (mdtReasonCodesList.size() > 0) {
        ReasonCodesLogic reasonCodesLogic = new ReasonCodesLogic();
        for (MdtCnfgReasonCodesEntity reasonEntity : mdtReasonCodesList) {
          ReasonCodesModel reasonModel = new ReasonCodesModel();
          reasonModel = reasonCodesLogic.retrieveReasonCode(reasonEntity);
          allReasonCodes.add(reasonModel);
        }
      }
    } catch (ObjectNotFoundException onfe) {
      log.debug("No Object Exists on DB");
    } catch (Exception e) {
      log.error("Error on retrieveReasonCodesByCriteria: " + e.getMessage());
      e.printStackTrace();
    }
    return allReasonCodes;
  }


  @Override
  public List<?> retrieveNrOfRecords() {
    List<AcOpsTransCtrlMsgModel> acOpsTransCtrlMsgModelList =
        new ArrayList<AcOpsTransCtrlMsgModel>();

    try {

      List<MdtAcOpsTransCtrlMsgEntity> mdtAcOpsTransCtrlMsgEntity =
          genericDAO.findAllByNamedQuery("MdtAcOpsTransCtrlMsgEntity.findByMsgType", "msgType",
              "EOT");

      if (mdtAcOpsTransCtrlMsgEntity.size() > 0) {
        AcOpsTransCtrlMsgLogic acOpsTransCtrlMsgLogic = new AcOpsTransCtrlMsgLogic();
        for (MdtAcOpsTransCtrlMsgEntity localEntity : mdtAcOpsTransCtrlMsgEntity) {

          if (localEntity.getActiveInd().equals("Y")) {
            AcOpsTransCtrlMsgModel localModel = new AcOpsTransCtrlMsgModel();
            localModel = acOpsTransCtrlMsgLogic.retrieveMsgType(localEntity);
            acOpsTransCtrlMsgModelList.add(localModel);

          }
        }
      }
    } catch (Exception e) {
      log.debug("Error on retrieve ");
      e.getMessage();
    }

    return acOpsTransCtrlMsgModelList;


  }

  public List<?> viewFrequencyCodesByCriteria(String frequencyCode) {

    List<FrequencyCodesModel> allFrequencyCodes = new ArrayList<FrequencyCodesModel>();

    try {

      List<MdtCnfgFrequencyCodesEntity> mdtFrequencyCodesList =
          genericDAO.findAllByNamedQuery("MdtCnfgFrequencyCodesEntity.findByFrequencyCodeLIKE",
              "frequencyCode", frequencyCode.toUpperCase() + "%");

      if (mdtFrequencyCodesList.size() > 0) {
        FrequencyCodesLogic frequencyLogic = new FrequencyCodesLogic();
        for (MdtCnfgFrequencyCodesEntity frequencyEntity : mdtFrequencyCodesList) {
          FrequencyCodesModel frequencyModel = new FrequencyCodesModel();
          frequencyModel = frequencyLogic.retrieveFrequencyCode(frequencyEntity);
          allFrequencyCodes.add(frequencyModel);
        }
      }
    } catch (ObjectNotFoundException onfe) {
      log.debug("No Object Exists on DB");
    } catch (Exception e) {
      log.error("Error on retrieveFrequencyCodesByCriteria: " + e.getMessage());
      e.printStackTrace();
    }
    return allFrequencyCodes;
  }

  public List<?> viewDebitValueTypeByCriteria(String dbtValueTypeCode) {

    List<DebitValueTypeModel> allDbtValueTypeCodes = new ArrayList<DebitValueTypeModel>();

    log.debug("IN the view All debit value type--->");
    try {

      List<MdtCnfgDebitValueTypeEntity> debitValueTypeEntityList =
          genericDAO.findAllByNamedQuery("MdtCnfgDebitValueTypeEntity.findByDebValueTypeCodeLIKE",
              "debValueTypeCode", dbtValueTypeCode.toUpperCase() + "%");

      if (debitValueTypeEntityList != null && debitValueTypeEntityList.size() > 0) {
        log.debug("debitValueTypeEntityList: " + debitValueTypeEntityList.size());
        DebitValueTypeLogic debitValueTypeLogic = new DebitValueTypeLogic();
        for (MdtCnfgDebitValueTypeEntity localEntity : debitValueTypeEntityList) {
          DebitValueTypeModel localModel = new DebitValueTypeModel();
          localModel = debitValueTypeLogic.retrievedebValueTypeCode(localEntity);
          allDbtValueTypeCodes.add(localModel);
        }
      }
    } catch (ObjectNotFoundException onfe) {
      log.debug("No Object Exists on DB");
    } catch (Exception e) {
      log.error("Error on retrievedebValueTypeCodeByCriteria: " + e.getMessage());
      e.printStackTrace();
    }
    return allDbtValueTypeCodes;
  }

  public List<?> viewCurrencyCodesByCriteria(String countryCode) {
    List<CurrencyCodesModel> allCurrencyCodes = new ArrayList<CurrencyCodesModel>();
    try {
      List<MdtCnfgCurrencyCodesEntity> mdtCurrencyCodesList =
          genericDAO.findAllByNamedQuery("MdtCnfgCurrencyCodesEntity.findByCountryCodeLIKE",
              "countryCode", countryCode.toUpperCase() + "%");

      if (mdtCurrencyCodesList.size() > 0) {
        CurrencyCodeLogic currencyLogic = new CurrencyCodeLogic();
        for (MdtCnfgCurrencyCodesEntity localEntity : mdtCurrencyCodesList) {
          CurrencyCodesModel localModel = new CurrencyCodesModel();
          localModel = currencyLogic.retrieveCurrencyCode(localEntity);
          allCurrencyCodes.add(localModel);
        }

      }
    } catch (ObjectNotFoundException onfe) {
      log.debug("No Object Exists on DB");
    } catch (Exception e) {
      log.error("Error on retrieveCurrencyCodeByCriteria: " + e.getMessage());
      e.printStackTrace();
    }
    return allCurrencyCodes;
  }

  @Override
  public List<?> viewSequenceCodesByCriteria(String seqCode) {

    List<SequenceTypesModel> allSequenceCodes = new ArrayList<SequenceTypesModel>();

    try {

      List<MdtCnfgSequenceTypeEntity> mdtSeqTypesList =
          genericDAO.findAllByNamedQuery("MdtCnfgSequenceTypeEntity.findBySeqTypeCodeLIKE",
              "seqTypeCode", seqCode.toUpperCase() + "%");

      if (mdtSeqTypesList.size() > 0) {

        SequenceTypeLogic seqLogic = new SequenceTypeLogic();
        for (MdtCnfgSequenceTypeEntity seqTypeEntity : mdtSeqTypesList) {
          SequenceTypesModel seqTypeModel = new SequenceTypesModel();
          seqTypeModel = seqLogic.retrieveSequenceCode(seqTypeEntity);
          allSequenceCodes.add(seqTypeModel);
        }
      }

    } catch (ObjectNotFoundException onfe) {
      log.debug("No Object Exists on DB");
    } catch (Exception e) {

      log.error("Error on retrieveSequenceCodeByCriteria: " + e.getMessage());
      e.printStackTrace();
    }

    return allSequenceCodes;
  }

//  Not needed for C&A
//  @Override
//  public List<?> viewBillingByCriteria(String memberId) {
//
//    List<SysctrlBillingParamModel> billingByCriteria = new ArrayList<SysctrlBillingParamModel>();
//
//    try {
//
//      List<MdtSysctrlBillingParamEntity> mdtSysctrlBillingParamEntityList =
//          genericDAO.findAllByNamedQuery("MdtSysctrlBillingParamEntity.findByMemberId",
//          "memberId",
//              memberId);
//
//      if (mdtSysctrlBillingParamEntityList.size() > 0) {
//
//        SysCntrlBillingLogic billingLogic = new SysCntrlBillingLogic();
//
//        for (MdtSysctrlBillingParamEntity mdtSysctrlBillingParamEntity :
//				mdtSysctrlBillingParamEntityList) {
//          SysctrlBillingParamModel sysctrlBillingParamModel = new SysctrlBillingParamModel();
//          sysctrlBillingParamModel =
//              billingLogic.retreiveBillingExtract(mdtSysctrlBillingParamEntity);
//          billingByCriteria.add(sysctrlBillingParamModel);
//        }
//      }
//
//    } catch (ObjectNotFoundException onfe) {
//      log.debug("No Object Exists on DB");
//    } catch (Exception e) {
//
//      log.error("Error on retrieveBillingByCriteria: " + e.getMessage());
//      e.printStackTrace();
//    }
//
//    return billingByCriteria;
//  }

  @Override
  public List<?> viewAllErrorCodes(boolean order) {
    List<ConfgErrorCodesModel> allErrorCodes = new ArrayList<ConfgErrorCodesModel>();
    List<MdtCnfgErrorCodesEntity> allMdtErrorCodesEntityList = null;

    if (order == true) {
      allMdtErrorCodesEntityList =
          genericDAO.findAllOrdered(MdtCnfgErrorCodesEntity.class, "errorCode ASC ");
    } else {
      allMdtErrorCodesEntityList = genericDAO.findAll(MdtCnfgErrorCodesEntity.class);
    }


    if (allMdtErrorCodesEntityList.size() > 0) {
      ErrorCodesLogic errorCodesLogic = new ErrorCodesLogic();
      allErrorCodes = errorCodesLogic.retrieveAllErrorCodes(allMdtErrorCodesEntityList);
    }

    return allErrorCodes;

  }

  @Override
  public List<?> viewAllOpsProcessControl() {
    List<OpsProcessControlCommonsModel> allOpsProcessControlList =
        new ArrayList<OpsProcessControlCommonsModel>();

    List<MdtOpsProcessControlsEntity> allMdtOpsProcessControlsEntityList =
        genericDAO.findAll(MdtOpsProcessControlsEntity.class);

    if (allMdtOpsProcessControlsEntityList.size() > 0) {
      OpsProcessControlLogic opsProcessControlLogic = new OpsProcessControlLogic();
      allOpsProcessControlList = opsProcessControlLogic.retrieveAllOpsProcessControlCommonsModel(
          allMdtOpsProcessControlsEntityList);
    }

    return allOpsProcessControlList;

  }

  @Override
  public List<?> viewAllOpsRefSeqNumber() {
    List<OpsRefSeqNumberCommonsModel> allOpsRefSeqNumberCommonsModelList =
        new ArrayList<OpsRefSeqNumberCommonsModel>();

    List<MdtOpsRefSeqNrEntity> allMdtOpsRefSeqNrEntityList =
        genericDAO.findAll(MdtOpsRefSeqNrEntity.class);

    if (allMdtOpsRefSeqNrEntityList.size() > 0) {
      OpsRefSeqNumberLogic opsRefSeqNumberLogic = new OpsRefSeqNumberLogic();
      allOpsRefSeqNumberCommonsModelList =
          opsRefSeqNumberLogic.retrieveAllOpsRefSeqNumber(allMdtOpsRefSeqNrEntityList);
    }

    return allOpsRefSeqNumberCommonsModelList;

  }

  @Override
  public List<?> viewAllOpsCustomerParameters() {
    List<OpsCustParamModel> allOpsCustParamModelList = new ArrayList<OpsCustParamModel>();

    List<MdtOpsCustParamEntity> allMdtOpsCustParamEntityList =
        genericDAO.findAll(MdtOpsCustParamEntity.class);

    if (allMdtOpsCustParamEntityList.size() > 0) {
      OpsCustParamLogic opsCustParamLogic = new OpsCustParamLogic();
      allOpsCustParamModelList =
          opsCustParamLogic.retrieveAllOpsCustParam(allMdtOpsCustParamEntityList);
    }

    return allOpsCustParamModelList;

  }

  @Override
  public List<?> viewAllOpsAcSotEot() {
    List<AcOpsSotEotCntrlModel> allAcOpsSotEotCntrlModelList =
        new ArrayList<AcOpsSotEotCntrlModel>();

    List<MdtAcOpsSotEotCtrlEntity> allMdtAcOpsSotEotCtrlEntityList =
        genericDAO.findAll(MdtAcOpsSotEotCtrlEntity.class);

    if (allMdtAcOpsSotEotCtrlEntityList.size() > 0) {
      AcOpsSotEotLogic acOpsSotEotLogic = new AcOpsSotEotLogic();
      allAcOpsSotEotCntrlModelList =
          acOpsSotEotLogic.retrieveAllMdtAcOpsSotEotCtrlEntityList(allMdtAcOpsSotEotCtrlEntityList);
    }

    return allAcOpsSotEotCntrlModelList;

  }

  @Override
  public List<?> viewAcOpsSOTEOTByCriteria(String instId) {

    List<AcOpsSotEotCntrlModel> acOpsSotEotCntrlModelList = new ArrayList<AcOpsSotEotCntrlModel>();

    try {

      List<MdtAcOpsSotEotCtrlEntity> mdtAcOpsSotEotCtrlEntityList =
          genericDAO.findAllByNamedQuery("MdtAcOpsSotEotCtrlEntity.findByInstId", "instId", instId);

      if (mdtAcOpsSotEotCtrlEntityList.size() > 0) {

        AcOpsSotEotLogic acOpsSotEotLogic = new AcOpsSotEotLogic();

        for (MdtAcOpsSotEotCtrlEntity mdtAcOpsSotEotCtrlEntity : mdtAcOpsSotEotCtrlEntityList) {
          AcOpsSotEotCntrlModel acOpsSotEotCntrlModel = new AcOpsSotEotCntrlModel();
          acOpsSotEotCntrlModel =
              acOpsSotEotLogic.retrieveMdtAcOpsSotEotCtrlEntity(mdtAcOpsSotEotCtrlEntity);
          acOpsSotEotCntrlModelList.add(acOpsSotEotCntrlModel);
        }
      }

    } catch (ObjectNotFoundException onfe) {
      log.debug("No Object Exists on DB");
    } catch (Exception e) {

      log.error("Error on viewAcOpsSOTEOTByCriteria: " + e.getMessage());
      e.printStackTrace();

    }
    return acOpsSotEotCntrlModelList;

  }

  public List<?> viewAllMember() {

    List<SysCisBankModel> sysCisBankModel = new ArrayList<SysCisBankModel>();
    List<SysCisBankEntity> sysCisBankEntityList = new ArrayList<SysCisBankEntity>();
    sysCisBankEntityList = genericDAO.findAllOrdered(SysCisBankEntity.class, "memberNo ASC ");

    if (sysCisBankEntityList.size() > 0) {
      SysCisBankLogic sysCisBankLogic = new SysCisBankLogic();
      sysCisBankModel = sysCisBankLogic.retrieveAllSysCisBankData(sysCisBankEntityList);
    }

    return sysCisBankModel;
  }


  @Override
  public boolean createErrorCodes(Object obj) {
    try {

      if (obj instanceof ConfgErrorCodesModel) {
        ConfgErrorCodesModel errorCodesModel = (ConfgErrorCodesModel) obj;
        ErrorCodesLogic errorCodesLogic = new ErrorCodesLogic();
        MdtCnfgErrorCodesEntity mdtErrorCodesEntity = new MdtCnfgErrorCodesEntity();

        mdtErrorCodesEntity = errorCodesLogic.addErrorCode(errorCodesModel);
        genericDAO.saveOrUpdate(mdtErrorCodesEntity);
        return true;
      } else {
        log.debug("Unable to convert type to ErrorCodesModel.");
        return false;
      }
    } catch (Exception e) {
      log.error("Error on createErrorCode: " + e.getMessage());
      e.printStackTrace();
      return false;
    }
  }

  public List<?> viewAllBranch() {

    List<SysCisBranchModel> sysCisBranchModel = new ArrayList<SysCisBranchModel>();
    List<SysCisBranchEntity> sysCisBranchEntityList = new ArrayList<SysCisBranchEntity>();
    sysCisBranchEntityList = genericDAO.findAll(SysCisBranchEntity.class);

    if (sysCisBranchEntityList.size() > 0) {
      SysCisBranchLogic sysCisBranchLogic = new SysCisBranchLogic();
      sysCisBranchModel = sysCisBranchLogic.retrieveAllSysCisBranchData(sysCisBranchEntityList);
    }

    return sysCisBranchModel;
  }

  public List<?> viewErrorCodesByCriteria(String errorCode) {

    List<ConfgErrorCodesModel> allErrorCodes = new ArrayList<ConfgErrorCodesModel>();

    try {

      List<MdtCnfgErrorCodesEntity> mdtErrorCodesList =
          genericDAO.findAllByNamedQuery("MdtCnfgErrorCodesEntity.findByErrorCodeLIKE", "errorCode",
              errorCode + "%");

      if (mdtErrorCodesList.size() > 0) {

        ErrorCodesLogic errorCodesLogic = new ErrorCodesLogic();
        for (MdtCnfgErrorCodesEntity errorEntity : mdtErrorCodesList) {
          ConfgErrorCodesModel errorModel = new ConfgErrorCodesModel();
          errorModel = errorCodesLogic.retrieveErrorCode(errorEntity);
          allErrorCodes.add(errorModel);
        }
      }
    } catch (ObjectNotFoundException onfe) {
      log.debug("No Object Exists on DB");
    } catch (Exception e) {

      log.error("Error on retrieveErrorCodeByCriteria: " + e.getMessage());
      e.printStackTrace();
    }

    return allErrorCodes;
  }

  @Override
  public boolean createInstId(Object obj) {
    {
      try {

        if (obj instanceof CustomerParametersModel) {

          CustomerParametersModel custParamModel = (CustomerParametersModel) obj;
          CustParamLogic custParamLogic = new CustParamLogic();
          CasSysctrlCustParamEntity casSysctrlCustParamEntity = new CasSysctrlCustParamEntity();

          casSysctrlCustParamEntity = custParamLogic.addCustomerParameter(custParamModel);
          genericDAO.saveOrUpdate(casSysctrlCustParamEntity);
          return true;
        } else {
          log.error("Unable to convert type to CustomerParametersModel.");
          return false;
        }
      } catch (Exception e) {
        log.error("Error on createBicCode: " + e.getMessage());
        e.printStackTrace();
        return false;
      }
    }
  }

  @Override
  public List<?> viewCustomerParametersByCriteria(String instId) {

    List<CustomerParametersModel> allCustomerParameter = new ArrayList<CustomerParametersModel>();

    try {

      List<CasSysctrlCustParamEntity> mdtSysCustParamList = genericDAO
          .findAllByNamedQuery("MdtSysctrlCustParamEntity.findByInstId", "instId", instId);

      if (mdtSysCustParamList.size() > 0) {

        CustParamLogic custParamLogic = new CustParamLogic();
        for (CasSysctrlCustParamEntity casSysctrlCustParamEntity : mdtSysCustParamList) {

          CustomerParametersModel custParamModel = new CustomerParametersModel();
          custParamModel = custParamLogic.retrieveCustomerParameter(casSysctrlCustParamEntity);
          allCustomerParameter.add(custParamModel);
        }
      }
    } catch (ObjectNotFoundException onfe) {
      log.error("No Object Exists on DB");
    } catch (Exception e) {
    }
    return allCustomerParameter;
  }

  /*
   * @Override public List<?> viewAllLocalInstrumentCodes() {
   * List<LocalInstrumentCodesModel> allLocalInstrCodes = null;
   *
   * List<MdtLocalInstrumentCodesEntity> allMdtLocalInstrCodesEntityList =
   * genericDAO.findAll(MdtLocalInstrumentCodesEntity.class);
   *
   * allLocalInstrCodes = new ArrayList<LocalInstrumentCodesModel>();
   *
   * LocalInstrumentCodesLogic localInsLogic = new
   * LocalInstrumentCodesLogic();
   *
   * log.debug("allMdtLocalInstrCodesEntityList " +
   * allMdtLocalInstrCodesEntityList.toString());
   * if(allMdtLocalInstrCodesEntityList.size() > 0) {
   *
   * allLocalInstrCodes = localInsLogic.retrieveAllLocalInstrumentCodes(
   * allMdtLocalInstrCodesEntityList );
   *
   * log.debug("allLocalInstrCodes " + allLocalInstrCodes); }
   *
   * return allLocalInstrCodes; }
   */

  /*
   * @Override public List<?> viewLocalInstrumentCodesByCriteria(String
   * localInsCode) { List<LocalInstrumentCodesModel> allLocalInstrCodes =
   * null;
   *
   *
   * MdtLocalInstrumentCodesEntity localEntity =
   * (MdtLocalInstrumentCodesEntity) genericDAO
   * .findAllByNamedQuery("MdtLocalInstrumentCodesEntity.findByLocalInsCode"
   * ,"localInsCode", localInsCode);
   *
   * if (localEntity != null) { LocalInstrumentCodesLogic localInsLogic = new
   * LocalInstrumentCodesLogic(); allLocalInstrCodes = new
   * ArrayList<LocalInstrumentCodesModel>();
   *
   * LocalInstrumentCodesModel localModel = new LocalInstrumentCodesModel();
   * localModel = localInsLogic.retrieveLocalInstrumentCode(localEntity);
   * allLocalInstrCodes.add(localModel); }
   *
   * return allLocalInstrCodes; }
   */

  @Override
  public List<?> viewAllCustomerParameters() {
    List<CustomerParametersModel> custParamsList = new ArrayList<CustomerParametersModel>();
    List<CasSysctrlCustParamEntity> allMdtSysctrlCustParamEntityList =
        new ArrayList<CasSysctrlCustParamEntity>();
    allMdtSysctrlCustParamEntityList = genericDAO.findAll(CasSysctrlCustParamEntity.class);

    if (allMdtSysctrlCustParamEntityList.size() > 0) {
      CustParamLogic custParamLogic = new CustParamLogic();
      custParamsList =
          custParamLogic.retrieveAllCustomerParameter(allMdtSysctrlCustParamEntityList);

      log.debug("Customer model from Bean --> " + custParamsList.toString());
    }

    return custParamsList;
  }


  @Override
  public List<?> retrieveCustomerParameters() {

    List<CasSysctrlCustParamEntity> customerParameterList =
        new ArrayList<CasSysctrlCustParamEntity>();

    //
    customerParameterList = genericDAO.findAll(CasSysctrlCustParamEntity.class);
    return customerParameterList;
  }


  @Override
  public List<?> retrieveServiceControl() {

    List<CasSysctrlServicesEntity> serviceControlList = new ArrayList<CasSysctrlServicesEntity>();

    serviceControlList = genericDAO.findAll(CasSysctrlServicesEntity.class);

    return serviceControlList;
  }

//  Not needed for C&A
//  @Override
//  public List<?> retrieveCronTime() {
//
//    List<MdtSysctrlCronEntity> cronTimeList = new ArrayList<MdtSysctrlCronEntity>();
//
//    cronTimeList = genericDAO.findAll(MdtSysctrlCronEntity.class);
//
//    return cronTimeList;
//  }

  @Override
  public List<?> retrieveSlaTime() {

    List<CasSysctrlSlaTimesEntity> mdtSysctrlSlaTimesEntityList =
        new ArrayList<CasSysctrlSlaTimesEntity>();

    mdtSysctrlSlaTimesEntityList = genericDAO.findAll(CasSysctrlSlaTimesEntity.class);

    return mdtSysctrlSlaTimesEntityList;
  }

  @Override
  public List<?> retrieveOpsSlaTime() {

    List<MdtOpsSlaTimesEntity> mdtOpsSlaTimesEntityList = new ArrayList<MdtOpsSlaTimesEntity>();

    mdtOpsSlaTimesEntityList = genericDAO.findAll(MdtOpsSlaTimesEntity.class);

    return mdtOpsSlaTimesEntityList;
  }

  @Override
  public List<?> retrieveACOpsSotEot() {

    List<MdtAcOpsSotEotCtrlEntity> mdtAcOpsSotEotCtrlEntityList =
        new ArrayList<MdtAcOpsSotEotCtrlEntity>();

    mdtAcOpsSotEotCtrlEntityList = genericDAO.findAll(MdtAcOpsSotEotCtrlEntity.class);

    return mdtAcOpsSotEotCtrlEntityList;
  }

//Not needed for C&A
//  @Override
//  public List<?> viewAllOpsCron() {
//
//    List<OpsCronTimeModel> opsCronTimeModel = new ArrayList<OpsCronTimeModel>();
//    List<MdtOpsCronEntity> allMdtOpsCronEntityList = new ArrayList<MdtOpsCronEntity>();
//    allMdtOpsCronEntityList = genericDAO.findAll(MdtOpsServicesEntity.class);
//
//
//    if (allMdtOpsCronEntityList.size() > 0) {
//      OpsCronTimeLogic opsCronTimeLogic = new OpsCronTimeLogic();
//      opsCronTimeModel = opsCronTimeLogic.retrieveAllOpsCronTimeModel(allMdtOpsCronEntityList);
//
//    }
//    return opsCronTimeModel;
//
//
//  }

  @Override
  public List<?> retrieveOpsCustParamTime() {
    List<MdtOpsCustParamEntity> opscustParamList = new ArrayList<MdtOpsCustParamEntity>();
    opscustParamList = genericDAO.findAll(MdtOpsCustParamEntity.class);
    return opscustParamList;
  }

  @Override
  public List<?> retrieveOpsMndtCount() {

    List<MdtAcOpsMndtCountEntity> opsMndtList = new ArrayList<MdtAcOpsMndtCountEntity>();
    opsMndtList =
        genericDAO.findAllByNamedQuery("MdtAcOpsMndtCountEntity.findByIncoming", "incoming", "Y");
    return opsMndtList;

  }

  @Override
  public List<?> retrieveMdtAcOpsDailyBilling() {
    List<MdtAcOpsDailyBillingEntity> opsfDailyBillingList =
        new ArrayList<MdtAcOpsDailyBillingEntity>();
    opsfDailyBillingList = genericDAO.findAll(MdtAcOpsDailyBillingEntity.class);

    return opsfDailyBillingList;
  }

  @Override
  public List<?> retrieveOpsServicesTime() {
    List<MdtOpsServicesEntity> opsServicesList = new ArrayList<MdtOpsServicesEntity>();
    opsServicesList = genericDAO.findAll(MdtOpsServicesEntity.class);
    return opsServicesList;
  }

  public List<?> retrieveOpsRefSeqNr() {

    List<MdtOpsRefSeqNrEntity> opsRefSeqNrList = new ArrayList<MdtOpsRefSeqNrEntity>();
    opsRefSeqNrList = genericDAO.findAll(MdtOpsRefSeqNrEntity.class);
    return opsRefSeqNrList;
  }


  public List<?> retrieveOpsProcessControl() {

    List<MdtOpsProcessControlsEntity> opsProcessCntrlList =
        new ArrayList<MdtOpsProcessControlsEntity>();
    opsProcessCntrlList = genericDAO.findAll(MdtOpsProcessControlsEntity.class);

    return opsProcessCntrlList;

  }
  /*
   * @Override public List<?> viewAllDelDelivery() {
   *
   * List<OpsFileRegModel> opsFileRegModel = new ArrayList<OpsFileRegModel>();
   *
   * List<MdtOpsFileRegEntity> mdtOpsFileRegList = new
   * ArrayList<MdtOpsFileRegEntity>();
   *
   * mdtOpsFileRegList = genericDAO.findAll(MdtOpsFileRegEntity.class);
   *
   * System.out .println("mdtOpsFileRegList: " +
   * mdtOpsFileRegList.toString());
   *
   * if (mdtOpsFileRegList.size() > 0) { OpsFileRegLogic opsFileRegLogic = new
   * OpsFileRegLogic(); opsFileRegModel = opsFileRegLogic
   * .retrieveAllDelDelivery(mdtOpsFileRegList); } return opsFileRegModel; }
   */

  @Override
  public Boolean createOpsFileRegModel(Object obj) {
    try {

      if (obj instanceof OpsFileRegModel) {
        OpsFileRegModel opsFileRegModel = (OpsFileRegModel) obj;
        OpsFileRegLogic opsFileRegLogic = new OpsFileRegLogic();
        MdtOpsFileRegEntity mdtOpsFileRegEntity = new MdtOpsFileRegEntity();

        mdtOpsFileRegEntity = opsFileRegLogic.addOpsFileReg(opsFileRegModel);
        genericDAO.saveOrUpdate(mdtOpsFileRegEntity);
        return true;
      } else {
        log.error("Unable to convert type to OpsFileRegModel.");
        return false;
      }
    } catch (Exception e) {

      log.error("Error on addOpsFileReg: " + e.getMessage());
      e.printStackTrace();
      return false;
    }

  }

  @Override
  public List<?> viewAllMandatesFiles() {

    List<OpsFileRegModel> opsFileRegModel = new ArrayList<OpsFileRegModel>();
    List<MdtOpsFileRegEntity> mdtOpsFileRegList = new ArrayList<MdtOpsFileRegEntity>();

    mdtOpsFileRegList = genericDAO.findAll(MdtOpsFileRegEntity.class);

    if (mdtOpsFileRegList.size() > 0) {
      OpsFileRegLogic opsFileRegLogic = new OpsFileRegLogic();
      opsFileRegModel = opsFileRegLogic.retrieveAllDelDelivery(mdtOpsFileRegList);
    }
    return opsFileRegModel;
  }

  /*
   * @Override public boolean createServiceName(Object obj) {
   *
   * try {
   *
   * if (obj instanceof SystemControlServiceModel) { SystemControlServiceModel
   * systemControlServiceModel = (SystemControlServiceModel) obj;
   * SystemControlServiceLogic systemControlServiceLogic = new
   * SystemControlServiceLogic(); MdtSysctrlServicesEntity
   * mdtSysctrlServicesEntity = new MdtSysctrlServicesEntity();
   *
   * mdtSysctrlServicesEntity = systemControlServiceLogic
   * .addServiceName(systemControlServiceModel);
   * genericDAO.saveOrUpdate(mdtSysctrlServicesEntity); return true; } else {
   * System.out .println(
   * "Unable to convert type to SystemControlServiceModel."); return false; }
   * } catch (Exception e) { log.debug("Error on createserviceName: " +
   * e.getMessage()); e.printStackTrace(); return false; } }
   */

  /*
   * @Override public List<?> viewAllSystemControlCronTimes() {
   * List<CronTimesModel> systemCntrolCronTimeModel = new
   * ArrayList<CronTimesModel>(); List<MdtSysctrlCronEntity>
   * mdtSysctrlCronTimesEntityList = new ArrayList<MdtSysctrlCronEntity>();
   *
   * mdtSysctrlCronTimesEntityList = genericDAO
   * .findAll(MdtSysctrlCronEntity.class);
   *
   * if (mdtSysctrlCronTimesEntityList.size() > 0) { CronTimesLogic
   * systemCntrCronTimeLogic = new CronTimesLogic(); systemCntrolCronTimeModel
   * = systemCntrCronTimeLogic
   * .retrieveAllCronTimes(mdtSysctrlCronTimesEntityList); }
   *
   * return systemCntrolCronTimeModel;
   *
   * }
   */

  /*
   * public List<?> viewSysCntrolCronTimeByCriteria(String processName) {
   *
   * List<CronTimesModel> allCronTimes = new ArrayList<CronTimesModel>();
   *
   * try { log.debug("processName --> " + processName);
   * List<MdtSysctrlCronEntity> mdtSysCntrlCronTimeList = genericDAO
   * .findAllByNamedQuery( "MdtSysctrlCronEntity.findByProcessName",
   * "processName", processName.toUpperCase()); log.debug(
   * "mdtSysCntrlCronTimeList --> " + mdtSysCntrlCronTimeList.toString()); if
   * (mdtSysCntrlCronTimeList.size() > 0) { CronTimesLogic cronTimeLogic = new
   * CronTimesLogic(); for (MdtSysctrlCronEntity localEntity :
   * mdtSysCntrlCronTimeList) { CronTimesModel cronTimeModel = new
   * CronTimesModel(); cronTimeModel = cronTimeLogic
   * .retrieveCronTimeCode(localEntity); allCronTimes.add(cronTimeModel); } }
   * } catch (ObjectNotFoundException onfe) { log.debug(
   * "No Object Exists on DB"); } catch (Exception e) {
   *
   *
   * log.debug("Error on retrieveSystemParametersByCriteria: " +
   * e.getMessage()); e.printStackTrace(); } return allCronTimes; }
   */

  /*
   * public Object retrieveActiveSysParameter() { MdtSysctrlSysParamEntity
   * mdtSysctrlSysParamEntity; mdtSysctrlSysParamEntity =
   * (MdtSysctrlSysParamEntity) genericDAO
   * .findByNamedQuery("MdtSysctrlSysParamEntity.findByActiveInd",
   * "activeInd", "Y");
   *
   * if (!(mdtSysctrlSysParamEntity != null)) { mdtSysctrlSysParamEntity = new
   * MdtSysctrlSysParamEntity(); }
   *
   * return mdtSysctrlSysParamEntity; }
   */

  /*
   * @Override public boolean createServiceName(Object obj) {
   *
   * try {
   *
   * if (obj instanceof SystemControlServiceModel) { SystemControlServiceModel
   * systemControlServiceModel = (SystemControlServiceModel) obj;
   * SystemControlServiceLogic systemControlServiceLogic = new
   * SystemControlServiceLogic(); MdtSysctrlServicesEntity
   * mdtSysctrlServicesEntity = new MdtSysctrlServicesEntity();
   *
   * mdtSysctrlServicesEntity = systemControlServiceLogic
   * .addServiceName(systemControlServiceModel);
   * genericDAO.saveOrUpdate(mdtSysctrlServicesEntity); return true; } else {
   * System.out .println(
   * "Unable to convert type to SystemControlServiceModel."); return false; }
   * } catch (Exception e) { log.debug("Error on createserviceName: " +
   * e.getMessage()); e.printStackTrace(); return false; } }
   */

//  Not needed for C&A
//  @Override
//  public List<?> viewAllSystemControlCronTimes() {
//    List<CronTimesModel> systemCntrolCronTimeModel = new ArrayList<CronTimesModel>();
//    List<MdtSysctrlCronEntity> mdtSysctrlCronTimesEntityList =
//        new ArrayList<MdtSysctrlCronEntity>();
//
//    mdtSysctrlCronTimesEntityList = genericDAO.findAll(MdtSysctrlCronEntity.class);
//
//    if (mdtSysctrlCronTimesEntityList.size() > 0) {
//      CronTimesLogic systemCntrCronTimeLogic = new CronTimesLogic();
//      systemCntrolCronTimeModel =
//          systemCntrCronTimeLogic.retrieveAllCronTimes(mdtSysctrlCronTimesEntityList);
//    }
//
//    return systemCntrolCronTimeModel;
//
//  }
//
//  public List<?> viewSysCntrolCronTimeByCriteria(String processName) {
//
//    List<CronTimesModel> allCronTimes = new ArrayList<CronTimesModel>();
//
//    try {
//
//      List<MdtSysctrlCronEntity> mdtSysCntrlCronTimeList = genericDAO.findAllByNamedQuery(
//          "MdtSysctrlCronEntity.findByProcessName", "processName", processName.toUpperCase());
//      if (mdtSysCntrlCronTimeList.size() > 0) {
//        CronTimesLogic cronTimeLogic = new CronTimesLogic();
//        for (MdtSysctrlCronEntity localEntity : mdtSysCntrlCronTimeList) {
//          CronTimesModel cronTimeModel = new CronTimesModel();
//          cronTimeModel = cronTimeLogic.retrieveCronTimeCode(localEntity);
//          allCronTimes.add(cronTimeModel);
//        }
//      }
//    } catch (ObjectNotFoundException onfe) {
//      log.debug("No Object Exists on DB");
//    } catch (Exception e) {
//      log.error("Error on retrieveSystemParametersByCriteria: " + e.getMessage());
//      e.printStackTrace();
//    }
//    return allCronTimes;
//  }

  public Object retrieveCustomerParameterPerMember() {

    CasSysctrlCustParamEntity casSysctrlCustParamEntity;
    casSysctrlCustParamEntity =
        (CasSysctrlCustParamEntity) genericDAO.findAll(CasSysctrlCustParamEntity.class);

    return casSysctrlCustParamEntity;
  }

  public Object retrieveActiveSysParameter() {
    CasSysctrlSysParamEntity casSysctrlSysParamEntity;
    casSysctrlSysParamEntity = (CasSysctrlSysParamEntity) genericDAO.findByNamedQuery(
        "MdtSysctrlSysParamEntity.findByActiveInd", "activeInd", "Y");
    return casSysctrlSysParamEntity;
  }


  public Object retrieveStartTime() {
    CasSysctrlSlaTimesEntity casSysctrlSlaTimesEntity;
    casSysctrlSlaTimesEntity = (CasSysctrlSlaTimesEntity) genericDAO.findByNamedQuery(
        "MdtSysctrlSlaTimesEntity.findByService", "service", "SOD");

    return casSysctrlSlaTimesEntity;
  }

  public Object retrieveCisDownloadInd(Date processDate) {

    MdtAcOpsProcessControlsEntity mdtAcOpsProcessControlsEntity =
        new MdtAcOpsProcessControlsEntity();

    try {
      HashMap<String, Object> parameters = new HashMap<String, Object>();
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

      // Get Calendar object set to the date and time of the given Date object
      Calendar cal = Calendar.getInstance();
      cal.setTime(processDate);

      // Set time fields to zero
      cal.set(Calendar.HOUR_OF_DAY, 0);
      cal.set(Calendar.MINUTE, 0);
      cal.set(Calendar.SECOND, 0);
      cal.set(Calendar.MILLISECOND, 0);

      // Put it back in the Date object
      processDate = cal.getTime();

      parameters.put("processDate", processDate);


      log.debug("---------------sparameters: ------------------" + parameters.toString());
      mdtAcOpsProcessControlsEntity = (MdtAcOpsProcessControlsEntity) genericDAO.findByCriteria(
          MdtAcOpsProcessControlsEntity.class, parameters);
      log.debug("---------------opsStatusDetailsList after findAllByCriteria: ------------------" +
          mdtAcOpsProcessControlsEntity);

    } catch (NullPointerException npe) {
      log.error("NullPointer exception :" + npe.getMessage());
    } catch (ObjectNotFoundException onfe) {
      log.error("No Object Exists on DB");
    } catch (Exception e) {
      log.error("Error on retrieveCisDownloadInd: " + e.getMessage());
      e.printStackTrace();
    }

    return mdtAcOpsProcessControlsEntity;

    //                                            List<MdtAcOpsProcessControlsEntity>
    //                                            processControlsList = genericDAO
    //                                            .findAllByNamedQuery
    //                                            ("MdtAcOpsProcessControlsEntity
    //                                            .findByProcessDate", "processDate", processDate);
    //
    //                                            if(processControlsList.size() > 0)
    //                                            {
    //                                                            mdtAcOpsProcessControlsEntity =
    //                                                            processControlsList.get(0);
    //                                            }
  }



  /*
   * public Object checkSODTime() { MdtSysctrlCronEntity syscntrlEntity = new
   * MdtSysctrlCronEntity(); try { Calendar cal = Calendar.getInstance();
   * cal.getTime(); SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
   * String currentTime = sdf.format(cal.getTime()); log.debug("currentTime: "
   * + sdf.format(cal.getTime())); syscntrlEntity = (MdtSysctrlCronEntity)
   * genericDAO.findByNamedQuery("MdtSysctrlCronEntity.findByCronTime",
   * "cronTime", currentTime); }
   *
   * catch (ObjectNotFoundException ne)
   *
   * { log.debug("No matching record found:  " + ne.getMessage());
   * ne.printStackTrace(); } catch (Exception e)
   *
   * { log.debug("Error on SOD time " + e.getMessage()); e.printStackTrace();
   * }
   *
   * return syscntrlEntity; }
   */

//Not needed for C&A
//  public Object checkSODTime() {
//
//    MdtSysctrlCronEntity syscntrlEntity = new MdtSysctrlCronEntity();
//    try {
//      Calendar cal = Calendar.getInstance();
//      cal.getTime();
//      SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
//      String currentTime = sdf.format(cal.getTime());
//      log.debug("currentTime: " + sdf.format(cal.getTime()));
//
//
//      syscntrlEntity =
//          (MdtSysctrlCronEntity) genericDAO.findByNamedQuery("MdtSysctrlCronEntity
//          .findByCronTime",
//              "cronTime", currentTime);
//
//    } catch (ObjectNotFoundException ne) {
//      log.debug("No matching record found:  " + ne.getMessage());
//      ne.printStackTrace();
//    } catch (Exception e) {
//      log.debug("Error on SOD time " + e.getMessage());
//      e.printStackTrace();
//    }
//
//    return syscntrlEntity;
//  }


//  @Override
//  public Object checkEODTime() {
//
//    // Redundant code as Scheduler will read cron time and run.
//    MdtSysctrlCronEntity syscntrlEntity = new MdtSysctrlCronEntity();
//    try {
//
//      Calendar cal = Calendar.getInstance();
//      cal.getTime();
//      SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
//      String currentTime = sdf.format(cal.getTime());
//      log.debug("currentTime: " + sdf.format(cal.getTime()));
//
//      syscntrlEntity =
//          (MdtSysctrlCronEntity) genericDAO.findByNamedQuery("MdtSysctrlCronEntity
//          .findByCronTime",
//              "cronTime", currentTime);
//
//    } catch (Exception e) {
//      log.error("Error on EOD time " + e.getMessage());
//      e.printStackTrace();
//    }
//    return syscntrlEntity;
//  }

  @Override
  public List<?> viewAllDelDelivery() {

    List<OpsFileRegModel> opsFileRegModel = new ArrayList<OpsFileRegModel>();

    List<MdtOpsFileRegEntity> mdtOpsFileRegList = new ArrayList<MdtOpsFileRegEntity>();

    mdtOpsFileRegList = genericDAO.findAll(MdtOpsFileRegEntity.class);

    if (mdtOpsFileRegList.size() > 0) {
      OpsFileRegLogic opsFileRegLogic = new OpsFileRegLogic();
      opsFileRegModel = opsFileRegLogic.retrieveAllDelDelivery(mdtOpsFileRegList);
    }
    return opsFileRegModel;
  }

  @Override
  public List<?> viewDelDeliveryByCriteria(String fileName) {

    List<OpsFileRegModel> allDelDelivery = new ArrayList<OpsFileRegModel>();

    try {

      List<MdtOpsFileRegEntity> mdtOpsFileRegList = genericDAO
          .findAllByNamedQuery("MdtOpsFileRegEntity.findByFileName", "fileName",
              fileName.toUpperCase());

      if (mdtOpsFileRegList.size() > 0) {

        OpsFileRegLogic opsFileRegLogic = new OpsFileRegLogic();
        for (MdtOpsFileRegEntity opsFileRegEntity : mdtOpsFileRegList) {

          OpsFileRegModel opsFileRegModel = new OpsFileRegModel();
          opsFileRegModel = opsFileRegLogic.retrieveOpsFileRegModel(opsFileRegEntity);
          allDelDelivery.add(opsFileRegModel);
        }
      }
    } catch (ObjectNotFoundException onfe) {
      log.debug("No Object Exists on DB");
    } catch (Exception e) {

      log.error("Error on retrieveDelDeliveryByCriteria: " + e.getMessage());
      e.printStackTrace();
    }

    return allDelDelivery;
  }

  public List<?> viewAllSystemsParameters() {

    List<SystemParameterModel> systemParametersModelList = new ArrayList<SystemParameterModel>();

    List<CasSysctrlSysParamEntity> mdtsystemParametersList =
        genericDAO.findAll(CasSysctrlSysParamEntity.class);

    if (mdtsystemParametersList.size() > 0) {
      SystemsParametersLogic systemParameterLogic = new SystemsParametersLogic();
      systemParametersModelList =
          systemParameterLogic.retrieveAllSystemsParameters(mdtsystemParametersList);
    }
    return systemParametersModelList;
  }

  public List<?> viewSystemParametersByCriteria(String sysName) {

    List<SystemParameterModel> allSystemsParameters = new ArrayList<SystemParameterModel>();

    try {
      log.debug("sysName --> " + sysName);
      List<CasSysctrlSysParamEntity> mdtSysParamsList =
          genericDAO.findAllByNamedQuery("MdtSysctrlSysParamEntity.findBySysName", "sysName",
              sysName.toUpperCase());
      if (mdtSysParamsList.size() > 0) {
        SystemsParametersLogic systemParameterLogic = new SystemsParametersLogic();
        for (CasSysctrlSysParamEntity localEntity : mdtSysParamsList) {
          SystemParameterModel systModel = new SystemParameterModel();
          systModel = systemParameterLogic.retrieveSystemParameters(localEntity);
          allSystemsParameters.add(systModel);
        }
      }
    } catch (ObjectNotFoundException onfe) {
      log.error("No Object Exists on DB");
    } catch (Exception e) {
      log.error("Error on retrieveSystemParametersByCriteria: " + e.getMessage());
      e.printStackTrace();
    }
    return allSystemsParameters;
  }

  public boolean createSystemParameters(Object obj) {

    try {

      if (obj instanceof SystemParameterModel) {
        SystemParameterModel systemParametersModel = (SystemParameterModel) obj;
        SystemsParametersLogic systemsParamatersLogic = new SystemsParametersLogic();
        CasSysctrlSysParamEntity mdtSystemsParametersEntity = new CasSysctrlSysParamEntity();

        mdtSystemsParametersEntity =
            systemsParamatersLogic.addSystemsParameters(systemParametersModel);
        genericDAO.saveOrUpdate(mdtSystemsParametersEntity);
        return true;
      } else {
        log.error("Unable to convert type to SystemParameters Model.");
        return false;
      }
    } catch (Exception e) {
      log.error("Error on createSystemsParamaters: " + e.getMessage());
      e.printStackTrace();
      return false;
    }

  }

  @Override
  public List<?> retrieveAllMandates() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<?> getopsFileRegByFileName(String fileName) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<?> viewAllSeverityCodes() {
    List<ConfgSeverityCodesModel> confgSeverityCodesModelList =
        new ArrayList<ConfgSeverityCodesModel>();
    List<MdtCnfgSeverityCodesEntity> mdtCnfgSeverityCodesList =
        new ArrayList<MdtCnfgSeverityCodesEntity>();
    mdtCnfgSeverityCodesList = genericDAO.findAll(MdtCnfgSeverityCodesEntity.class);
    if (mdtCnfgSeverityCodesList.size() > 0) {
      SeverityCodesLogic severityCodesLogic = new SeverityCodesLogic();
      confgSeverityCodesModelList =
          severityCodesLogic.retrieveAllSeverityCodes(mdtCnfgSeverityCodesList);
    }
    return confgSeverityCodesModelList;
  }

  @Override
  public boolean createOpsCustParameters(Object obj) {
    if (obj instanceof MdtOpsCustParamEntity) {
      MdtOpsCustParamEntity mdtOpsCustParamEntity = (MdtOpsCustParamEntity) obj;
      try {
        genericDAO.save(mdtOpsCustParamEntity);
        return true;
      } catch (Exception ex) {
        log.error("Error on createOpsCustParameters: " + ex.getMessage());
        return false;
      }
    } else {
      return false;
    }
  }

  @Override
  public boolean createAcOpsDailyBilling(Object obj) {
    if (obj instanceof MdtAcOpsDailyBillingEntity) {
      MdtAcOpsDailyBillingEntity mdtAcOpsDailyBillingEntity = (MdtAcOpsDailyBillingEntity) obj;
      try {
        genericDAO.save(mdtAcOpsDailyBillingEntity);
        return true;
      } catch (Exception ex) {
        log.error("Error on createAcOpsDailyBilling: " + ex.getMessage());
        return false;
      }
    } else {
      return false;
    }
  }

  @Override
  public boolean createSysParameters(Object obj) {
    if (obj instanceof CasSysctrlSysParamEntity) {
      CasSysctrlSysParamEntity casSysctrlSysParamEntity = (CasSysctrlSysParamEntity) obj;

      try {
        genericDAO.saveOrUpdate(casSysctrlSysParamEntity);
        return true;
      } catch (Exception ex) {
        log.error("Error on  createSysParameters: " + ex.getMessage());
        ex.printStackTrace();
        return false;
      }
    } else {
      return false;
    }
  }

  @Override
  public boolean createOpsRefSeqNr(Object obj) {
    if (obj instanceof MdtOpsRefSeqNrEntity) {
      MdtOpsRefSeqNrEntity mdtOpsRefSeqNrEntity = (MdtOpsRefSeqNrEntity) obj;
      try {
        genericDAO.save(mdtOpsRefSeqNrEntity);
        return true;
      } catch (Exception ex) {
        log.error("Error on createOpsRefSeqNrEntity: " + ex.getMessage());
        return false;
      }
    } else {
      return false;
    }
  }


  @Override

  public boolean createCustParameters(Object obj) {
    if (obj instanceof CasSysctrlCustParamEntity) {
      CasSysctrlCustParamEntity casSysctrlCustParamEntity = (CasSysctrlCustParamEntity) obj;
      try {
        genericDAO.save(casSysctrlCustParamEntity);
        return true;
      } catch (Exception ex) {
        log.error("Error on createCustParameters: " + ex.getMessage());
        return false;
      }
    } else {
      return false;
    }
  }

  @Override

  public boolean createSotEot(Object obj) {

    if (obj instanceof MdtAcOpsSotEotCtrlEntity) {
      MdtAcOpsSotEotCtrlEntity mdtAcOpsSotEotCtrlEntity = (MdtAcOpsSotEotCtrlEntity) obj;


      try {
        genericDAO.save(mdtAcOpsSotEotCtrlEntity);
        return true;
      } catch (Exception ex) {

        log.error("Error on createAcOpsotEot:" + ex.getMessage());
        return false;
      }
    } else {
      return false;
    }
  }


  @Override
  public boolean createOpsProcessControls(Object obj) {
    if (obj instanceof MdtOpsProcessControlsEntity) {
      MdtOpsProcessControlsEntity mdtOpsProcessControlsEntity =
          (MdtOpsProcessControlsEntity) obj;
      try {
        genericDAO.save(mdtOpsProcessControlsEntity);
        return true;
      } catch (Exception ex) {
        log.error("Error on createOpsProcessControlsEntity: "
            + ex.getMessage());
        return false;
      }
    } else {
      return false;
    }
  }

//Not needed for C&A
//  @Override
//  public boolean createCronTime(Object obj) {
//    try {
//
//      if (obj instanceof CronTimesModel) {
//        CronTimesModel cronTimesModel = (CronTimesModel) obj;
//        CronTimesLogic cronTimesLogic = new CronTimesLogic();
//        MdtSysctrlCronEntity mdtSysctrlCronEntity = new MdtSysctrlCronEntity();
//        mdtSysctrlCronEntity = cronTimesLogic.addCronTimeTypeCode(cronTimesModel);
//        genericDAO.saveOrUpdate(mdtSysctrlCronEntity);
//        return true;
//      } else {
//        log.error("Unable to convert type tocronTimesModel");
//        return false;
//      }
//    } catch (Exception e) {
//
//      log.error("Error on cronTimesModel: " + e.getMessage());
//      e.printStackTrace();
//      return false;
//    }
//
//  }

  @Override
  public boolean createOpsServices(Object obj) {

    if (obj instanceof MdtOpsServicesEntity) {
      MdtOpsServicesEntity mdtOpsServicesEntity = (MdtOpsServicesEntity) obj;
      mdtOpsServicesEntity.setRecordId(new BigDecimal(99999));
      try {
        genericDAO.save(mdtOpsServicesEntity);
        return true;
      } catch (Exception ex) {
        log.error("Error on createOpsServices: " + ex.getMessage());
        return false;
      }
    } else {
      return false;
    }
  }

  @Override
  public boolean updateSystemParameters(Object obj) {
    if (obj instanceof CasSysctrlSysParamEntity) {
      CasSysctrlSysParamEntity casSysctrlSysParamEntity = (CasSysctrlSysParamEntity) obj;
      try {
        //				log.info("mdtSysctrlSysParamEntity in update method in admin bean --->> "
        //				+ mdtSysctrlSysParamEntity) ;
        genericDAO.saveOrUpdate(casSysctrlSysParamEntity);
        return true;
      } catch (Exception ex) {
        log.error("Error on updateSystemParameters: " + ex.getMessage());
        return false;
      }
    } else {
      return false;
    }
  }

  @Override
  public boolean updateOpsProcessControls(Object obj) {
    if (obj instanceof MdtOpsProcessControlsEntity) {
      MdtOpsProcessControlsEntity mdtOpsProcessControlsEntity =
          (MdtOpsProcessControlsEntity) obj;

      try {
        genericDAO.saveOrUpdate(mdtOpsProcessControlsEntity);
        return true;
      } catch (Exception ex) {
        log.error("Error on updateOpsProcessControlEntity: " + ex.getMessage());
        return false;
      }
    } else {
      return false;
    }


  }

//	Not needed for C&A
//  @Override
//  public boolean updateCronTimes(Object obj) {
//	  if (obj instanceof MdtSysctrlCronEntity) {
//		  MdtSysctrlCronEntity mdtSysctrlCronEntity = (MdtSysctrlCronEntity) obj;
//		  try {
//			  genericDAO.saveOrUpdate(mdtSysctrlCronEntity);
//			  return true;
//		  } catch (Exception ex) {
//			  log.error("Error on updateSystemParameters: " + ex.getMessage());
//			  return false;
//		  }
//	  } else {
//		  return false;
//	  }
//  }


  @Override
  public boolean deleteOpsServices(Object obj) {

    try {
      if (obj instanceof MdtOpsServicesEntity) {

        MdtOpsServicesEntity mdtopservice = new MdtOpsServicesEntity();

        genericDAO.delete(mdtopservice);
        return true;

      } else {
        log.error("Unable to delete ops Service");

        return false;

      }

    } catch (Exception e) {
      log.error("Error on ops service " + e.getMessage());
      e.printStackTrace();
      return false;

    }
  }

  @Override
  public boolean deleteOpsCustParam(Object obj) {

    try {
      if (obj instanceof MdtOpsCustParamEntity) {

        MdtOpsCustParamEntity mdtopsCustParam = new MdtOpsCustParamEntity();
        genericDAO.delete(mdtopsCustParam);
        return true;

      } else {
        log.error("Unable to delete table");

        return false;

      }

    } catch (Exception e) {
      log.error("Error on ops service " + e.getMessage());
      e.printStackTrace();
      return false;

    }
  }

  @Override
  public boolean deleteOpsFileReg(Object obj) {
    try {
      if (obj instanceof MdtOpsFileRegEntity) {

        MdtOpsFileRegEntity mdtopsFileReg = new MdtOpsFileRegEntity();
        genericDAO.delete(mdtopsFileReg);
        return true;

      } else {
        log.debug("Unable to delete table");

        return false;

      }

    } catch (Exception e) {
      log.debug("Error on ops service " + e.getMessage());
      e.printStackTrace();
      return false;

    }
  }

  public void moveFileToAnotherFile() {
    try {
      File newfile = new File("C:\\home\\newdev\\input\\processing\\ Afile.txt");

      if (newfile.renameTo(
          new File("C:\\home\\newdev\\Archive\\outputfile.file2txt" + newfile.getName()))) {
        log.debug("File is moved successful!");
      } else {
        log.error("File has  failed to move!");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  public void truncateTable(String tableName) {
    log.debug("<<---------TRUNCATING TABLE--> " + tableName + "---------->");
    try {
      genericDAO.truncateTable(tableName);

      log.info("********** " + tableName + " has been truncated**********");

    } catch (Exception e) {
      log.error("Error on testTruncateTable " + e.getMessage());
      e.printStackTrace();
    }
  }

  public boolean runStartofDayManually() {
    StartOfDayLogic startofDayLogic = new StartOfDayLogic();
    boolean sodCheck = false;
    try {
      sodCheck = startofDayLogic.SodImplementation();
      //			startofDayLogic.testSotFiles();
      feedbackMsg = startofDayLogic.feedbackMsg;
      log.debug("feedbackMsg from AdminBean--------------?????" + feedbackMsg);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return sodCheck;
  }

  public String retrieveFeedbackMsg() {
    return feedbackMsg;
  }


  public void runCisDownload() throws ParseException {

    log.debug("in the cis download ************************************new CisDownlaodLogic()");
    CisDownloadLogic startofDayLogic = new CisDownloadLogic();
    startofDayLogic.cisDownloadImplementation();
  }


  public String retrieveCisFeedbackMsg() {
    return cisFeedbackMsg;
  }


  public String retrieveEodFeedbackMsg() {
    return eodfeedbackMsg;
  }

  public boolean runEndofDayManually(String userName, String forcecloseReason)
      throws ParseException {

    EndOfDayLogic endofDay = new EndOfDayLogic(userName);
    boolean eodCheck = false;
    try {
      eodCheck = endofDay.EndOfdayImplementation(forcecloseReason);
      eodfeedbackMsg = endofDay.feedbackMsg;
      log.debug("eodfeedbackMsg from AdminBean--------------?????" + eodfeedbackMsg);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      eodCheck = false;
    }
    return eodCheck;
  }

  public Object retrieveCompanyParameters() {
    SysctrlCompParamModel compParamModel = new SysctrlCompParamModel();
    CasSysctrlCompParamEntity mdtSysctrlCompParamEntity =
        (CasSysctrlCompParamEntity) genericDAO.findAll(CasSysctrlCompParamEntity.class);

    if (mdtSysctrlCompParamEntity != null) {
      CompanyParametersLogic companyParametersLogic = new CompanyParametersLogic();
      compParamModel = companyParametersLogic.retrieveCompanyParameters(mdtSysctrlCompParamEntity);
    }

    return compParamModel;
  }

  public List<?> retrieveCompParam() {

    List<CasSysctrlCompParamEntity> compList = new ArrayList<CasSysctrlCompParamEntity>();

    return compList;

  }

  @Override
  public List<?> viewAllReportsName() {
    List<ReportsNamesModel> allReportNames = new ArrayList<ReportsNamesModel>();
    List<MdtCnfgReportNamesEntity> allMdtCnfgReportNamesList =
        new ArrayList<MdtCnfgReportNamesEntity>();
    allMdtCnfgReportNamesList = genericDAO.findAll(MdtCnfgReportNamesEntity.class);

    if (allMdtCnfgReportNamesList.size() > 0) {
      ReportsLogic reportsLogic = new ReportsLogic();
      allReportNames = reportsLogic.retrieveAllReportNames(allMdtCnfgReportNamesList);
    }

    return allReportNames;
  }

  @Override
  public List<?> viewActiveReports() {
    List<ReportsNamesModel> allReportNames = new ArrayList<ReportsNamesModel>();

    try {

      List<MdtCnfgReportNamesEntity> mdtCnfgReportNamesEntityList =
          genericDAO.findAllByNamedQuery("MdtCnfgReportNamesEntity.findByActiveInd", "activeInd",
              "Y");


      if (mdtCnfgReportNamesEntityList.size() > 0) {
        ReportsLogic reportsLogic = new ReportsLogic();

        for (MdtCnfgReportNamesEntity localEntity : mdtCnfgReportNamesEntityList) {
          ReportsNamesModel localModel = new ReportsNamesModel();
          localModel = reportsLogic.retrieveReportNames(localEntity);
          allReportNames.add(localModel);
        }
      }
    } catch (ObjectNotFoundException onfe) {
      log.error("No Object Exists on DB");

    } catch (Exception e) {
      log.error("Error viewActiveReports: " + e.getMessage());
      e.printStackTrace();
    }
    return allReportNames;
  }


  @Override
  public List<?> viewReportNamesByCriteria(String reportName) {
    List<ReportsNamesModel> allReportNames = new ArrayList<ReportsNamesModel>();

    try {

      List<MdtCnfgReportNamesEntity> mdtCnfgReportNamesEntityList =
          genericDAO.findAllByNamedQuery("MdtCnfgReportNamesEntity.findByReportNrLIKE", "reportNr",
              reportName + "%");

      if (mdtCnfgReportNamesEntityList.size() > 0) {
        ReportsLogic reportsLogic = new ReportsLogic();

        for (MdtCnfgReportNamesEntity localEntity : mdtCnfgReportNamesEntityList) {
          ReportsNamesModel localModel = new ReportsNamesModel();
          localModel = reportsLogic.retrieveReportNames(localEntity);
          allReportNames.add(localModel);
        }
      }
    } catch (ObjectNotFoundException onfe) {
      log.error("No Object Exists on DB");

    } catch (Exception e) {
      log.error("Error onretrieveReportNamesByCriteria: " + e.getMessage());
      e.printStackTrace();
    }
    return allReportNames;
  }

  @Override
  public boolean createReportNames(Object obj) {
    try {

      if (obj instanceof ReportsNamesModel) {
        ReportsNamesModel reportsNamesModel = (ReportsNamesModel) obj;
        ReportsLogic reportsLogic = new ReportsLogic();
        MdtCnfgReportNamesEntity mdtCnfgReportNamesEntity = new MdtCnfgReportNamesEntity();

        mdtCnfgReportNamesEntity = reportsLogic.addReportNames(reportsNamesModel);
        genericDAO.saveOrUpdate(mdtCnfgReportNamesEntity);
        return true;
      } else {
        log.debug("Unable to convert type to Report Names.");
        return false;
      }
    } catch (Exception e) {
      log.error("Error on createReportNames: " + e.getMessage());

      e.printStackTrace();
      return false;
    }

  }


  @Override
  public List<?> viewAllCompParam() {
    // TODO Auto-generated method stub
    return null;
  }

//  Not needed for C&A
//  @Override
//  public List<?> retrieveOpsCronTime() {
//
//    List<OpsCronTimeModel> opsCronTimeModelList = new ArrayList<OpsCronTimeModel>();
//    List<MdtOpsCronEntity> mdtOpsCronEntityList = new ArrayList<MdtOpsCronEntity>();
//    mdtOpsCronEntityList = genericDAO.findAll(MdtOpsCronEntity.class);
//    if (mdtOpsCronEntityList.size() > 0) {
//      OpsCronTimeLogic opsCronTimeLogic = new OpsCronTimeLogic();
//      opsCronTimeModelList = opsCronTimeLogic.retrieveAllOpsCronTimeModel(mdtOpsCronEntityList);
//
//    }
//
//    return opsCronTimeModelList;
//  }

  @Override
  public boolean deleteFileStatus(Object obj) {

    try {
      if (obj instanceof OpsFileRegModel) {
        OpsFileRegModel opsFileRegModel = (OpsFileRegModel) obj;
        OpsFileRegLogic fileRegLogic = new OpsFileRegLogic();
        MdtOpsFileRegEntity mdtOpsFileRegEntity = new MdtOpsFileRegEntity();
        mdtOpsFileRegEntity = fileRegLogic.addOpsFileReg(opsFileRegModel);
        genericDAO.delete(mdtOpsFileRegEntity);

        return true;
      } else {
        log.error("Unable to delete table");

        return false;
      }
    } catch (Exception e) {
      log.error("Error on deleteFileStatus " + e.getMessage());
      e.printStackTrace();
      return false;
    }

  }

  // @Override
  public void addmdteReqDwnId(Object obj) {
    // TODO Auto-generated method stub

  }

  @Override
  public List<?> viewAuditTrackings() {
    List<AuditTrackingModel> allAuditTrackingList = new ArrayList<AuditTrackingModel>();
    List<AudTrackingEntity> allAuditTrackingEntityList = new ArrayList<AudTrackingEntity>();
    allAuditTrackingEntityList = genericDAO.findAll(AudTrackingEntity.class);

    if (allAuditTrackingEntityList.size() > 0) {
      AuditTrackingLogic auditTrackingLogic = new AuditTrackingLogic();
      allAuditTrackingList = auditTrackingLogic.retrieveAllRecordId(allAuditTrackingEntityList);
    }

    return allAuditTrackingList;
  }

  @Override
  public List<?> viewAuditTrackingCriteria(String tableName) {
    List<AuditTrackingModel> allAuditTrackingList = new ArrayList<AuditTrackingModel>();
    List<AudTrackingEntity> allAuditTrackingEntityList = new ArrayList<AudTrackingEntity>();

    try {

      allAuditTrackingEntityList = (List<AudTrackingEntity>) genericDAO.findAllByNamedQuery(
          "AudTrackingEntity.findByTableName", "tableName", tableName);
      log.debug("allAuditTrackingEntityList*********************" + allAuditTrackingEntityList);
      if (allAuditTrackingEntityList.size() > 0) {
        AuditTrackingLogic auditTrackingLogic = new AuditTrackingLogic();

        for (AudTrackingEntity audTrackingEntity : allAuditTrackingEntityList) {

          AuditTrackingModel auditTrackingModel = new AuditTrackingModel();
          auditTrackingModel = auditTrackingLogic.retrievedrecordId(audTrackingEntity);
          allAuditTrackingList.add(auditTrackingModel);
          log.debug("allAuditTrackingList*********************" + allAuditTrackingList);

        }
      }
    } catch (ObjectNotFoundException onfe) {
      log.error("No Object Exists on DB");

    } catch (Exception e) {
      log.error("Error onretrieveviewAuditTrackingCriteria: " + e.getMessage());
      e.printStackTrace();
    }
    return allAuditTrackingList;

  }


  @Override
  public boolean createRecordId(Object obj) {
    try {

      if (obj instanceof AuditTrackingModel) {
        AuditTrackingModel auditTrackingModel = (AuditTrackingModel) obj;
        AuditTrackingLogic auditTrackingLogic = new AuditTrackingLogic();
        AudTrackingEntity audTrackingEntity = new AudTrackingEntity();

        audTrackingEntity = auditTrackingLogic.addrecordId(auditTrackingModel);
        genericDAO.saveOrUpdate(audTrackingEntity);
        return true;
      } else {
        log.error("Unable to convert type to AuditTrackingModel.");
        return false;
      }
    } catch (Exception e) {
      log.error("Error on createRecordId: " + e.getMessage());
      e.printStackTrace();
      return false;
    }
  }

  @Override
  public List<?> viewAllAuditTables() {
    List<AuditTrackingModel> allAuditTrackingList = new ArrayList<AuditTrackingModel>();
    List<AudTrackingEntity> allAuditTrackingEntityList = new ArrayList<AudTrackingEntity>();
    allAuditTrackingEntityList = genericDAO.findAll(AudTrackingEntity.class);

    if (allAuditTrackingEntityList.size() > 0) {
      AuditTrackingLogic auditTrackingLogic = new AuditTrackingLogic();
      allAuditTrackingList = auditTrackingLogic.retrieveAllRecordId(allAuditTrackingEntityList);
    }

    return allAuditTrackingList;
  }

  @Override
  public List<?> viewAllRejectReasonCodes() {
    List<CnfgRejectReasonCodesModel> rejectReasonCodesModelList =
        new ArrayList<CnfgRejectReasonCodesModel>();
    List<MdtCnfgRejectReasonCodesEntity> mdtCnfgRejectReasonCodesEntityList =
        new ArrayList<MdtCnfgRejectReasonCodesEntity>();
    mdtCnfgRejectReasonCodesEntityList = genericDAO.findAll(MdtCnfgRejectReasonCodesEntity.class);

    if (mdtCnfgRejectReasonCodesEntityList.size() > 0) {
      CnfgRejectReasonCodesLogic rejectReasonCodesLogic = new CnfgRejectReasonCodesLogic();
      rejectReasonCodesModelList = rejectReasonCodesLogic
          .retrieveAllRejectReasonsCodes(mdtCnfgRejectReasonCodesEntityList);
    }
    return rejectReasonCodesModelList;
  }

  @Override
  public List<?> viewRejectReasonCodesByCriteria(String rejectReasonCode) {
    List<CnfgRejectReasonCodesModel> allRejectReasonCodes =
        new ArrayList<CnfgRejectReasonCodesModel>();

    try {
      List<MdtCnfgRejectReasonCodesEntity> mdtCnfgRejectReasonCodesEntityList =
          genericDAO.findAllByNamedQuery(
              "MdtCnfgRejectReasonCodesEntity.findByRejectReasonCodeLIKE", "rejectReasonCode",
              rejectReasonCode.toUpperCase() + "%");

      if (mdtCnfgRejectReasonCodesEntityList.size() > 0) {

        CnfgRejectReasonCodesLogic rejectReasonCodesLogic = new CnfgRejectReasonCodesLogic();
        for (MdtCnfgRejectReasonCodesEntity mdtCnfgRejectReasonCodesEntity :
            mdtCnfgRejectReasonCodesEntityList) {

          CnfgRejectReasonCodesModel rejectReasonCodesModel = new CnfgRejectReasonCodesModel();
          rejectReasonCodesModel = rejectReasonCodesLogic
              .retrieveCnfgRejectReasonCodesModel(mdtCnfgRejectReasonCodesEntity);
          allRejectReasonCodes.add(rejectReasonCodesModel);
        }
      }
    } catch (ObjectNotFoundException onfe) {
      log.error("No Object Exists on DB");
    } catch (Exception e) {

      log.error("Error on viewRejectReasonCodesByCriteria: " + e.getMessage());
      e.printStackTrace();
    }

    return allRejectReasonCodes;
  }

  @Override
  public boolean createRejectReasonCodes(Object obj) {

    try {
      if (obj instanceof CnfgRejectReasonCodesModel) {
        CnfgRejectReasonCodesModel rejectReasonCodesModel = (CnfgRejectReasonCodesModel) obj;
        CnfgRejectReasonCodesLogic rejectReasonCodesLogic = new CnfgRejectReasonCodesLogic();
        MdtCnfgRejectReasonCodesEntity mdtCnfgRejectReasonCodesEntity =
            new MdtCnfgRejectReasonCodesEntity();

        mdtCnfgRejectReasonCodesEntity =
            rejectReasonCodesLogic.addRejectReasonsCodes(rejectReasonCodesModel);
        genericDAO.saveOrUpdate(mdtCnfgRejectReasonCodesEntity);
        return true;
      } else {
        log.debug("Unable to convert type to rejectReasonCodes.");
        return false;
      }
    } catch (Exception e) {
      log.error("Error on createRejectReasonCodes: " + e.getMessage());

      e.printStackTrace();
      return false;
    }
  }

  @Override
  public List<?> viewAllProcessStatus() {
    List<ProcessStatusModel> allProcessStatusList = new ArrayList<ProcessStatusModel>();

    List<CasSysctrlProcessStatusEntity> allMdtSysctrlProcessStatusEntityList =
        new ArrayList<CasSysctrlProcessStatusEntity>();

    allMdtSysctrlProcessStatusEntityList = genericDAO.findAll(CasSysctrlProcessStatusEntity.class);
    if (allMdtSysctrlProcessStatusEntityList.size() > 0) {
      SysctrlProcessStatusLogic processStatusLogic = new SysctrlProcessStatusLogic();
      allProcessStatusList =
          processStatusLogic.retreiveAllProcessStatus(allMdtSysctrlProcessStatusEntityList);
    }
    return allProcessStatusList;
  }

  @Override
  public boolean createProcessStatus(Object obj) {
    try {
      if (obj instanceof ProcessStatusModel) {
        ProcessStatusModel processStatusModel = (ProcessStatusModel) obj;
        SysctrlProcessStatusLogic sysctrlProcesStatusLogic = new SysctrlProcessStatusLogic();
        CasSysctrlProcessStatusEntity casSysctrlProcessStatusEntity =
            new CasSysctrlProcessStatusEntity();

        casSysctrlProcessStatusEntity =
            sysctrlProcesStatusLogic.addprocessStatus(processStatusModel);
        genericDAO.saveOrUpdate(casSysctrlProcessStatusEntity);
        return true;
      } else {
        log.debug("Unable to convert type to processStatus.");

        return false;
      }
    } catch (Exception e) {
      log.error("Error on createProcessStatus:" + e.getMessage());
      e.printStackTrace();

      return false;
    }

  }

// Not needed for C&A
//  @Override
//  public boolean createOpsCron(Object obj) {
//
//	  if (obj instanceof MdtOpsCronEntity) {
//		  MdtOpsCronEntity mdtOpsCronEntity = (MdtOpsCronEntity) obj;
//
//		  try {
//			  genericDAO.save(mdtOpsCronEntity);
//			  return true;
//		  } catch (Exception ex) {
//			  log.error("Error on createOpsCron: " + ex.getMessage());
//			  return false;
//		  }
//	  } else {
//		  return false;
//	  }
//  }


  @Override
  public boolean createOpsSlaTimes(Object obj) {

    if (obj instanceof MdtOpsSlaTimesEntity) {
      MdtOpsSlaTimesEntity mdtOpsSlaTimesEntity = (MdtOpsSlaTimesEntity) obj;

      try {
        genericDAO.save(mdtOpsSlaTimesEntity);
        return true;
      } catch (Exception ex) {
        log.error("Error on createOpsSlaTimes: " + ex.getMessage());
        return false;
      }
    } else {
      return false;
    }
  }


  @Override
  public List<?> viewProcessStatusByCriteria(String status) {
    List<ProcessStatusModel> allProcessStatus = new ArrayList<ProcessStatusModel>();

    try {

      List<CasSysctrlProcessStatusEntity> mdtSysctrlProcessStatusEntityList =
          genericDAO.findAllByNamedQuery("MdtSysctrlProcessStatusEntity.findByStatus", "status",
              status.toUpperCase());

      if (mdtSysctrlProcessStatusEntityList.size() > 0) {
        SysctrlProcessStatusLogic sysctrlProcessStatusLogic = new SysctrlProcessStatusLogic();

        for (CasSysctrlProcessStatusEntity localEntity : mdtSysctrlProcessStatusEntityList) {
          ProcessStatusModel processStatusModel = new ProcessStatusModel();
          processStatusModel = SysctrlProcessStatusLogic.retreiveProcessStatus(localEntity);
          allProcessStatus.add(processStatusModel);


        }
      }

    } catch (ObjectNotFoundException onfe) {
      log.error("No object Exists on DB");
    } catch (Exception e) {
      log.error("Error viewProcessStatusByCriteria: " + e.getMessage());
      e.printStackTrace();
    }

    return allProcessStatus;

  }

//  Not needed for C&A
//  @Override
//  public Object retrieveActiveFileWatcher(String processName) {
//    MdtOpsCronEntity mdtOpsCronEntity;
//    mdtOpsCronEntity =
//        (MdtOpsCronEntity) genericDAO.findByNamedQuery("MdtOpsCronEntity.findByProcessName",
//            "processName", "FILEWATCHER");
//
//    return mdtOpsCronEntity;
//  }


  @Override
  public List<?> retrieveActiveCisMember() {
    List<CisMemberEntity> cisMemberList = new ArrayList<CisMemberEntity>();
    cisMemberList =
        genericDAO.findAllByNamedQuery("CisMemberEntity.findByAcMdtInd", "acMdtInd", "Y");
    return cisMemberList;

  }

  public List<?> retrieveRelatedMember() {
//		log.debug("In retrieveRelatedMember() . . . ");

    List<MemberDTO> memberDTOList = new ArrayList<MemberDTO>();
    MemberDTO memberDTO = new MemberDTO();
    memberDTO.setAcMdtInd("Y");

    try {
//			log.debug("Calling CIS Service Bean***************>>>>>");
      //	cisServiceBean.initialize();
      memberDTOList = (List<MemberDTO>) cisServiceRemote.retrieveRelatedMember(memberDTO);
      if (memberDTOList != null && memberDTOList.size() > 0) {
        log.debug("memberDTOList********************:" + memberDTOList.size()); //11
      }
    } catch (DAOException e) {
      log.error("Error on retrieveRelatedMember :" + e.getMessage());
      e.printStackTrace();
    }

    return memberDTOList;
  }


  public Object retrieveSysParamEntity() {
    CasSysctrlSysParamEntity mdtsysParamEntity;
    mdtsysParamEntity = (CasSysctrlSysParamEntity) genericDAO.findByNamedQuery(
        "MdtSysctrlSysParamEntity.findByActiveInd", "activeInd", "Y");
    return mdtsysParamEntity;

  }


  @Override
  public List<?> retrieveSysCisBank() {
    List<SysCisBankEntity> sysCisBankList = new ArrayList<SysCisBankEntity>();
    //		sysCisBankList   =genericDAO.findAll(SysCisBankEntity.class);
    sysCisBankList = genericDAO.findAllOrdered(SysCisBankEntity.class, "memberNo ASC ");
    return sysCisBankList;
  }


  @Override
  public boolean createSysCisBank(Object obj) {
    if (obj instanceof SysCisBankEntity) {
      SysCisBankEntity SysCisBankEntity = (SysCisBankEntity) obj;
      try {
        genericDAO.save(SysCisBankEntity);
        return true;
      } catch (Exception ex) {
        log.error("Error on createSysCisBank :" + ex.getMessage());
        return false;
      }
    } else {
      return false;
    }
  }


  @Override
  public List<?> retrieveSysCisBranch() {
    List<SysCisBranchEntity> cisBranchList = new ArrayList<SysCisBranchEntity>();
    cisBranchList = genericDAO.findAll(SysCisBranchEntity.class);
    return cisBranchList;

  }


  //            @Override
  //            public Object retrieveInactiveEOTIND(String destId, String serviceId)
  //            {
  //                            MdtAcOpsSotEotCtrlEntity mdtAcOpsSotEotCtrlEntity = null;
  //
  //                            try
  //                            {
  //                                            HashMap<String, Object> parameters = new
  //                                            HashMap<String, Object>();
  //                                            parameters.put("instId", destId);
  //                                            parameters.put("errorType", serviceId);
  //
  //
  //                                            log.info("---------------sparameters:
  //                                            ------------------"+ parameters.toString());
  //                                            opsStatusDetailsList =
  //                                            (List<OpsStatusDetailsEntity>) genericDAO
  //                                            .findDistinctByCriteria(OpsStatusDetailsEntity
  //                                            .class, parameters, "txnId");
  //                                            log.info("---------------opsStatusDetailsList
  //                                            after findAllByCriteria: ------------------"+
  //                                            opsStatusDetailsList);
  //                            } catch (ObjectNotFoundException onfe) {
  //                                            log.info("No Object Exists on DB");
  //                            } catch (Exception e) {
  //                                            log.error("Error on retrieveDistinctTxnErrors: "+
  //                                            e.getMessage());
  //                                            e.printStackTrace();
  //                            }
  //
  //
  //
  //                            mdtAcOpsSotEotCtrlEntity = (MdtAcOpsSotEotCtrlEntity) genericDAO
  //                            .findByNamedQuery("MdtAcOpsSotEotCtrlEntity.findByServiceId",
  //                            "serviceId", serviceId);
  //
  //                            return mdtAcOpsSotEotCtrlEntity;
  //            }

  @Override
  public boolean createSysCisBranch(Object obj) {
    if (obj instanceof SysCisBranchEntity) {
      SysCisBranchEntity sysCisBranchEntity = (SysCisBranchEntity) obj;
      try {
        genericDAO.save(sysCisBranchEntity);
        return true;
      } catch (Exception ex) {
        log.error("Error on createSysCisBranch :" + ex.getMessage());
        return false;
      }
    } else {
      return false;
    }
  }


  public List<?> retrieveActiveCisBranch(String memberNo) {
    List<SysCisBranchEntity> cisBranchEntityList = new ArrayList<SysCisBranchEntity>();

    cisBranchEntityList = (List<SysCisBranchEntity>) genericDAO.findAllByNamedQuery(
        "SysCisBranchEntity.findByMemberNo", "memberNo", memberNo);

    return cisBranchEntityList;
  }

  public List<?> retrieveMndCisBranch(String memberNo) {
    List<BranchDTO> branchDTOList = new ArrayList<BranchDTO>();
    BranchDTO branchDTO = new BranchDTO();
    branchDTO.setMemberNo(memberNo);
    try {
      CisServiceBean cisServiceBean = new CisServiceBean();
      cisServiceBean.initialize();
      branchDTOList = cisServiceBean.retrieveRelatedBranch(branchDTO);
      log.debug("branchDTOList Bean********************:" + branchDTOList.size());
    } catch (DAOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return branchDTOList;
  }

  @Override
  public List<?> retrieveSysCustomerParameters() {
    List<CasSysctrlCustParamEntity> mdtSysctrlCustParamEntityList =
        new ArrayList<CasSysctrlCustParamEntity>();

    try {
      mdtSysctrlCustParamEntityList = genericDAO.findAll(CasSysctrlCustParamEntity.class);
    } catch (NullPointerException e) {
      e.printStackTrace();
    }
    return mdtSysctrlCustParamEntityList;
  }

  @Override
  public List<?> retrieveSysCisBankMembers() {
    List<SysCisBankEntity> sysCisBankEntityList = new ArrayList<SysCisBankEntity>();
    try {
      sysCisBankEntityList = genericDAO.findAll(SysCisBankEntity.class);
    } catch (NullPointerException e) {
      e.printStackTrace();
    }
    return sysCisBankEntityList;
  }

//  Not needed for C&A
//  @Override
//  public boolean updateOpsCronTime(Object obj) {
//	  if (obj instanceof MdtOpsCronEntity) {
//		  MdtOpsCronEntity mdtOpsCronEntity = (MdtOpsCronEntity) obj;
//		  try {
//			  genericDAO.saveOrUpdate(mdtOpsCronEntity);
//			  return true;
//		  } catch (Exception ex) {
//			  log.error("Error on updateOpsCronTime: " + ex.getMessage());
//			  return false;
//		  }
//	  } else {
//		  return false;
//	  }
//  }


  @Override
  public List<?> viewAllServices() {
    List<ServicesModel> allServicesModel = new ArrayList<ServicesModel>();
    List<MdtOpsServicesEntity> allMdtOpsServicesEntityList = new ArrayList<MdtOpsServicesEntity>();
    allMdtOpsServicesEntityList =
        genericDAO.findAllOrdered(MdtOpsServicesEntity.class, "serviceIdIn ASC ");
    log.debug("allMdtOpsServicesEntityList**********" + allMdtOpsServicesEntityList);

    if (allMdtOpsServicesEntityList.size() > 0) {
      ServicesLogic servicesLogic = new ServicesLogic();
      allServicesModel = ServicesLogic.retreiveAllServices(allMdtOpsServicesEntityList);
    }
    return allServicesModel;
  }

  public Object retrieveWebActiveSysParameter() {
    CasSysctrlSysParamEntity casSysctrlSysParamEntity = null;
    SystemParameterModel systemParameterModel = new SystemParameterModel();
    try {
      casSysctrlSysParamEntity = (CasSysctrlSysParamEntity) genericDAO.findByNamedQuery(
          "MdtSysctrlSysParamEntity.findByActiveInd", "activeInd", "Y");
      if (casSysctrlSysParamEntity != null) {
        SystemsParametersLogic systemParameterLogic = new SystemsParametersLogic();
        systemParameterModel =
            systemParameterLogic.retrieveSystemParameters(casSysctrlSysParamEntity);
      }
    } catch (Exception ex) {
      log.error("Error on retrieveWebActiveSysParameter: " + ex.getMessage());
    }

    return systemParameterModel;
  }

  public List<?> viewOpsFileRegByDirection(String direction, String fileName) {
    List<OpsFileRegModel> fileRegList = new ArrayList<OpsFileRegModel>();
    List<MdtOpsFileRegEntity> mdtOpsFileRegEntityList = new ArrayList<MdtOpsFileRegEntity>();

    try {
      HashMap<String, Object> parameters = new HashMap<String, Object>();
      parameters.put("inOutInd", direction);
      parameters.put("fileName", "%" + fileName + "%");

      log.debug("parameters---> " + parameters.toString());
			/*	mdtOpsFileRegEntityList =(List<MdtOpsFileRegEntity>)genericDAO.findAllByCriteria
			(MdtOpsFileRegEntity.class, parameters);
			log.info("---------------mdtOpsFileRegEntityList after findAllByCriteria:
			------------------" + mdtOpsFileRegEntityList);*/
      mdtOpsFileRegEntityList =
          genericDAO.findAllByNQCriteria("MdtOpsFileRegEntity.findByFileNameLike3", parameters);
      log.debug(
          "---------------mdtOpsFileRegEntityList after findAllByNQCriteria: ------------------" +
              mdtOpsFileRegEntityList);


      if (mdtOpsFileRegEntityList != null && mdtOpsFileRegEntityList.size() > 0) {
        OpsFileRegLogic opsFileRegLogic = new OpsFileRegLogic();

        for (MdtOpsFileRegEntity localEntity : mdtOpsFileRegEntityList) {

          OpsFileRegModel opsFileRegModel = new OpsFileRegModel();
          opsFileRegModel = opsFileRegLogic.retrieveDelDelivery(localEntity);

          fileRegList.add(opsFileRegModel);
          //fileRegList = opsFileRegLogic.retrieveAllDelDelivery(mdtOpsFileRegEntityList);
        }
      }
    } catch (Exception ex) {
      log.error("Error on viewOpsFileRegByDirection: " + ex.getMessage());
    }

    return fileRegList;
  }

  public List<?> viewAllAuditTracking() {

    List<AuditTrackingModel> auditTrackingModelList = new ArrayList<AuditTrackingModel>();
    List<AudTrackingEntity> audTrackingEntityList = new ArrayList<AudTrackingEntity>();
    audTrackingEntityList = genericDAO.findAll(AudTrackingEntity.class);
    if (audTrackingEntityList.size() > 0) {
      AuditTrackingLogic auditTrackingLogic = new AuditTrackingLogic();
      auditTrackingModelList = auditTrackingLogic.retrieveAllRecordId(audTrackingEntityList);
    }

    return auditTrackingModelList;
  }






	/*           public List<?> retrieveOpsStatusDetails(BigDecimal statusHdrSeqNo)
                {
                                List<OpsStatusDetailsModel> opsStatusDetailsList = new
                                ArrayList<OpsStatusDetailsModel>();
                                List<MdtAcOpsStatusDetailsEntity> opsStatusDetailsEntityList =
                                new ArrayList<MdtAcOpsStatusDetailsEntity>();
                                try
                                {

                                                HashMap<String, Object> parameters = new
                                                HashMap<String, Object>();
                                                parameters.put("statusHdrSeqNo", statusHdrSeqNo);
                                                opsStatusDetailsEntityList =
                                                (List<MdtAcOpsStatusDetailsEntity>) genericDAO
                                                .findAllByCriteria(MdtAcOpsStatusDetailsEntity
                                                .class,parameters,false);
                                                log.debug
                                                ("---------------opsStatusDetailsEntityList after
                                                 findAllByCriteria: ------------------"+
                                                 opsStatusDetailsEntityList);
                                                                                if
                                                                                (opsStatusDetailsEntityList != null)
                                                                                {
                                                                                                ViewFileStatusLogic viewFileStatusLogic = new ViewFileStatusLogic();

                                                                                                for (MdtAcOpsStatusDetailsEntity localEntity : opsStatusDetailsEntityList)
                                                                                                {
                                                                                                                OpsStatusDetailsModel opsStatusDetailsModel = new OpsStatusDetailsModel();
                                                                                                                opsStatusDetailsModel = viewFileStatusLogic.translateOpsStatusDetEntityToCommonsModel(localEntity);
                                                                                                                opsStatusDetailsList.add(opsStatusDetailsModel);
                                                                                }
                                                                                }
                                } catch (ObjectNotFoundException onfe) {
                                                log.error("No object Exists on DB");
                                } catch (Exception e) {
                                                log.error("Error retrieveOpsStatusDetails: " + e
                                                .getMessage());
                                                e.printStackTrace();
                                }


                                return opsStatusDetailsList;

                }*/

	/*public Object retrieveOpsStatusHdr(String orgnlMsgId)
    {
                    OpsStatusHdrsModel opsStatusHdrsModel = new OpsStatusHdrsModel();

                    try
                    {
                                    MdtAcOpsStatusHdrsEntity opsStatusHdrsEntity =
                                    (MdtAcOpsStatusHdrsEntity) genericDAO.findByNamedQuery
                                    ("MdtAcOpsStatusHdrsEntity.findByOrgnlMsgId", "orgnlMsgId",
                                    orgnlMsgId);
                                    if(opsStatusHdrsEntity != null)
                                    {
                                                    ViewFileStatusLogic viewFileStatusLogic = new
                                                     ViewFileStatusLogic();
                                                    opsStatusHdrsModel = viewFileStatusLogic
                                                    .translateEntityToCommonsModel
                                                    (opsStatusHdrsEntity);
                                    }
                    }
                    catch(Exception ex)
                    {
                                    log.error("Error on retrieveOpsStatusHdr: " + ex.getMessage());
                    }

                    log.info("opsStatusHdrsModel********************************* " +
                    opsStatusHdrsModel);
                    return opsStatusHdrsModel;
    }*/

  @Override
  public List<?> viewAdjustmentCategoryByCriteria(String adjustmentCategory) {
    List<AdjustmentCategoryModel> alladjustmentCategoryModel =
        new ArrayList<AdjustmentCategoryModel>();

    try {
      List<MdtCnfgAdjustmentCatEntity> allMdtCnfgAdjustmentCatEntityList =
          genericDAO.findAllByNamedQuery("MdtCnfgAdjustmentCatEntity.findByAdjustmentCategory",
              "adjustmentCategory", adjustmentCategory);

      if (allMdtCnfgAdjustmentCatEntityList.size() > 0) {
        AdjustmentCategoryLogic adjustmentCategoryLogic = new AdjustmentCategoryLogic();

        for (MdtCnfgAdjustmentCatEntity localEntity : allMdtCnfgAdjustmentCatEntityList) {
          AdjustmentCategoryModel adjustmentCategoryModel = new AdjustmentCategoryModel();
          adjustmentCategoryModel = adjustmentCategoryLogic.retreiveAdjustmentCategory(localEntity);
          alladjustmentCategoryModel.add(adjustmentCategoryModel);
        }
      }
    } catch (ObjectNotFoundException onfe) {
      log.error("No object Exists on DB");
    } catch (Exception e) {
      log.error("Error viewAdjustmentCategoryByCriteria: " + e.getMessage());
      e.printStackTrace();
    }

    return alladjustmentCategoryModel;
  }

  @Override
  public List<?> viewAllAdjustmentCategory() {
    List<AdjustmentCategoryModel> allAdjustmentCategoryList =
        new ArrayList<AdjustmentCategoryModel>();

    List<MdtCnfgAdjustmentCatEntity> allMdtCnfgAdjustmentCatEntityList =
        new ArrayList<MdtCnfgAdjustmentCatEntity>();

    allMdtCnfgAdjustmentCatEntityList = genericDAO.findAll(MdtCnfgAdjustmentCatEntity.class);
    if (allMdtCnfgAdjustmentCatEntityList.size() > 0) {
      AdjustmentCategoryLogic adjustmentCategoryLogic = new AdjustmentCategoryLogic();
      allAdjustmentCategoryList =
          adjustmentCategoryLogic.retreiveAllAdjustmentCategory(allMdtCnfgAdjustmentCatEntityList);
    }

    log.debug("allAdjustmentCategoryList*******************" + allAdjustmentCategoryList);
    return allAdjustmentCategoryList;
  }

  @Override
  public boolean updateSystemParametersForceClosure(Object obj, String action) {

    try {
      if (obj instanceof SystemParameterModel) {
        SystemParameterModel sysParamModel = (SystemParameterModel) obj;
        SystemsParametersLogic sysParamLogic = new SystemsParametersLogic();
        CasSysctrlSysParamEntity casSysctrlSysParamEntity = new CasSysctrlSysParamEntity();
        casSysctrlSysParamEntity = sysParamLogic.addSystemsParameters(sysParamModel);
        genericDAO.saveOrUpdate(casSysctrlSysParamEntity);

        return true;
      } else {
        log.debug("Unable to update ind balance indicator");
        return false;

      }

    } catch (Exception e) {
      log.error("Error updating force closure :" + e.getMessage());
      e.printStackTrace();

      return false;
    }

  }


  @Override
  public boolean createAdjustmentCategory(Object obj) {
    try {
      if (obj instanceof AdjustmentCategoryModel) {
        AdjustmentCategoryModel adjustmentCategoryModel = (AdjustmentCategoryModel) obj;
        AdjustmentCategoryLogic adjustmentCategoryLogic = new AdjustmentCategoryLogic();
        MdtCnfgAdjustmentCatEntity mdtCnfgAdjustmentCatEntity = new MdtCnfgAdjustmentCatEntity();

        mdtCnfgAdjustmentCatEntity =
            adjustmentCategoryLogic.addAdjustmentCategory(adjustmentCategoryModel);
        genericDAO.saveOrUpdate(mdtCnfgAdjustmentCatEntity);

        return true;
      } else {
        log.debug("Unable to convert type to AdjustmentCategory.");

        return false;
      }
    } catch (Exception e) {
      log.error("Error on createAdjustmentCategory:" + e.getMessage());
      e.printStackTrace();

      return false;
    }
  }


  public List<?> retrieveOpsStatusDetails(BigDecimal statusHdrSeqNo) {
    List<OpsStatusDetailsModel> opsStatusDetailsList = new ArrayList<OpsStatusDetailsModel>();
    List<MdtAcOpsStatusDetailsEntity> opsStatusDetailsEntityList =
        new ArrayList<MdtAcOpsStatusDetailsEntity>();
    try {
      HashMap<String, Object> parameters = new HashMap<String, Object>();
      parameters.put("statusHdrSeqNo", statusHdrSeqNo);
      opsStatusDetailsEntityList = (List<MdtAcOpsStatusDetailsEntity>) genericDAO.findAllByCriteria(
          MdtAcOpsStatusDetailsEntity.class, parameters);
      log.debug(
          "---------------opsStatusDetailsEntityList after findAllByCriteria: ------------------" +
              opsStatusDetailsEntityList);
      if (opsStatusDetailsEntityList != null) {
        ViewFileStatusLogic viewFileStatusLogic = new ViewFileStatusLogic();

        for (MdtAcOpsStatusDetailsEntity localEntity : opsStatusDetailsEntityList) {
          OpsStatusDetailsModel opsStatusDetailsModel = new OpsStatusDetailsModel();
          opsStatusDetailsModel =
              viewFileStatusLogic.translateOpsStatusDetEntityToCommonsModel(localEntity);
          opsStatusDetailsList.add(opsStatusDetailsModel);
        }
      }
    } catch (ObjectNotFoundException onfe) {
      log.error("No object Exists on DB");
    } catch (Exception e) {
      log.error("Error retrieveOpsStatusDetails: " + e.getMessage());
      e.printStackTrace();
    }


    return opsStatusDetailsList;
  }

  @Override
  public Object retrieveOpsStatusHdrs(String orgnlMsgId) {

    OpsStatusHdrsModel opsStatusHdrsModel = new OpsStatusHdrsModel();

    try {
      MdtAcOpsStatusHdrsEntity opsStatusHdrsEntity =
          (MdtAcOpsStatusHdrsEntity) genericDAO.findByNamedQuery(
              "MdtAcOpsStatusHdrsEntity.findByOrgnlMsgId", "orgnlMsgId", orgnlMsgId);
      if (opsStatusHdrsEntity != null) {
        ViewFileStatusLogic viewFileStatusLogic = new ViewFileStatusLogic();
        opsStatusHdrsModel = viewFileStatusLogic.translateEntityToCommonsModel(opsStatusHdrsEntity);
        log.debug("opsStatusHdrsEntity********************************* " + opsStatusHdrsEntity);

      }
    } catch (Exception ex) {
      log.error("Error on retrieveOpsStatusHdr: " + ex.getMessage());
    }

    log.debug("opsStatusHdrsModel********************************* " + opsStatusHdrsModel);

    return opsStatusHdrsModel;
  }


  @Override
  public List<?> viewAllOpsStatusHdrs() {

    List<OpsStatusHdrsModel> opsStatusHdrsModelList = new ArrayList<OpsStatusHdrsModel>();

    List<MdtAcOpsStatusHdrsEntity> opsStatusHdrsEntityList =
        new ArrayList<MdtAcOpsStatusHdrsEntity>();

    opsStatusHdrsEntityList = genericDAO.findAll(MdtAcOpsStatusHdrsEntity.class);

    if (opsStatusHdrsEntityList.size() > 0) {
      OpsStatusHdrsLogic opsStatusHdrsLogic = new OpsStatusHdrsLogic();
      opsStatusHdrsModelList = opsStatusHdrsLogic.retrieveAllOpsStatusHdrs(opsStatusHdrsEntityList);
      //opsStatusHdrsEntityList.add(opsStatusHdrsModel);
    }
    return opsStatusHdrsModelList;
  }

  @Override
  public List<?> viewAllOpsStatusDetails() {
    List<OpsStatusDetailsModel> opsStatusDetailsModelList = new ArrayList<OpsStatusDetailsModel>();
    List<MdtAcOpsStatusDetailsEntity> opsStatusDetailsEntityList =
        new ArrayList<MdtAcOpsStatusDetailsEntity>();
    opsStatusDetailsEntityList = genericDAO.findAll(MdtAcOpsStatusDetailsEntity.class);

    if (opsStatusDetailsEntityList.size() > 0) {
      OpsStatusDetailsLogic opsStatusDetailsLogic = new OpsStatusDetailsLogic();
      opsStatusDetailsModelList =
          opsStatusDetailsLogic.retrieveAllOpsStatusDetails(opsStatusDetailsEntityList);

    }
    return opsStatusDetailsModelList;


  }


  @Override
  public List<?> retrieveOpsCustomerParameters() {
    List<CustomerParametersModel> custParamsList = new ArrayList<CustomerParametersModel>();
    List<MdtOpsCustParamEntity> allMdtOpsCustParamEntityList =
        new ArrayList<MdtOpsCustParamEntity>();
    allMdtOpsCustParamEntityList = genericDAO.findAll(MdtOpsCustParamEntity.class);

    if (allMdtOpsCustParamEntityList.size() > 0) {
      CustParamLogic custParamLogic = new CustParamLogic();
      custParamsList = custParamLogic.translateOpsCustParametersEntityToCommonsModel(
          allMdtOpsCustParamEntityList);

      log.debug("Customer model from Bean --> " + custParamsList.toString());
    }

    return custParamsList;
  }


  @Override

  public List<?> retrieveOutstandingResponses(String memberId, String onlineInd) {
    List<OutstandingResponsesModelEntity> outsReportModelEntityList =
        new ArrayList<OutstandingResponsesModelEntity>();
    OutstandingResponsesModel outstandingResponsesModel = null;
    //MdtAcOpsFinInstEntity debtorEntity;
    List<OutstandingResponsesModel> outstandingResponsesModelsList =
        new ArrayList<OutstandingResponsesModel>();

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


    sb.append(
        " SELECT a.MANDATE_REQ_TRAN_ID as mandateReqTranId, a.MANDATE_REQ_ID as mandateReqId,a" +
            ".SERVICE_ID as serviceId,a.FILE_NAME as fileName, c.MANDATE_REF_NR as mandateRefNr,b" +
            ".MEMBER_ID as creditorMemberNo ");
    sb.append(
        "	FROM MANOWNER.MDT_AC_OPS_MNDT_MSG a, MANOWNER.MDT_AC_OPS_FIN_INST b, MANOWNER" +
            ".MDT_AC_OPS_SUPPL_DATA c ");
    sb.append("	WHERE a.MSG_ID = b.MSG_ID AND a.MSG_ID = c.MSG_Id AND ");
    sb.append(
        "	a.MANDATE_REQ_TRAN_ID = b.MANDATE_REQ_TRAN_ID AND a.MANDATE_REQ_TRAN_ID = c" +
            ".MANDATE_REQ_TRAN_ID AND ");
    sb.append("b.FIN_INST_TYPE_ID = 'FI03' AND a.ONLINE_IND = '" + onlineInd +
        "' AND a.PROCESS_STATUS = '4' AND TO_CHAR(a.CREATED_DATE, 'ddMMYYYY') < '" + stringDate +
        "' ");


		/*		sb.append(" SELECT a.MANDATE_REQ_TRAN_ID as mandateReqTranId, a.MANDATE_REQ_ID as
		mandateReqId ,a.SERVICE_ID as serviceId, a.FILE_NAME as fileName,a.MANDATE_REF_NR as
		mandateRefNr, b.MEMBER_ID as creditorMemberNo ");
		sb.append(" FROM MDT_AC_OPS_MNDT_MSG a  ");
		sb.append(" LEFT OUTER JOIN MDT_AC_OPS_FIN_INST b ON a.MANDATE_REQ_TRAN_ID = b
 .MANDATE_REQ_TRAN_ID ");
		sb.append(" LEFT OUTER JOIN MDT_OPS_FILE_REG c ON a.FILE_NAME = c.FILE_NAME ");
		sb.append(" WHERE a.ONLINE_IND = '"+onlineInd+"' AND a.PROCESS_STATUS = '4' AND   b
		.FIN_INST_TYPE_ID = 'FI03' ") ;  */

    String sqlQuery = sb.toString();
    log.debug("The Sql query is  ***************" + sqlQuery);
    List<String> scalarList = new ArrayList<String>();
    scalarList.add("mandateReqTranId");
    scalarList.add("mandateReqId");
    scalarList.add("mandateRefNr");
    scalarList.add("serviceId");
    scalarList.add("creditorMemberNo");
    //    scalarList.add("debtorMemberNo");

    scalarList.add("fileName");

    outsReportModelEntityList =
        genericDAO.findBySql(sqlQuery, scalarList, OutstandingResponsesModelEntity.class);
    if (outsReportModelEntityList != null && outsReportModelEntityList.size() > 0) {
      for (OutstandingResponsesModelEntity outstRespEntity : outsReportModelEntityList) {

        //Translate from entity's to common's model
        ReportsLogic reportsLogic = new ReportsLogic();
        outstandingResponsesModel = new OutstandingResponsesModel();
        outstandingResponsesModel =
            reportsLogic.translateOutstandingResponsesEntityToCommonsModel(outstRespEntity);
        outstandingResponsesModelsList.add(outstandingResponsesModel);
      }
    }


    return outstandingResponsesModelsList;
  }

  @Override
  public void runSOT(String destMemberId, String serviceName) {
    StartOfTransmissionExtract StartOfTransmissionExtract =
        new StartOfTransmissionExtract(destMemberId, serviceName, "S");
    StartOfTransmissionExtract.createManualStartOfTransmissionFile(destMemberId, serviceName);

  }

  @Override
  public void runEOT(String destMemberId, String serviceName) {
    EndOfTransmissionExtract endOfTransmissionExtract =
        new EndOfTransmissionExtract(destMemberId, serviceName, "S");
    endOfTransmissionExtract.createManualEndOfTransmissionFile(destMemberId, serviceName);

  }


  @Override
  public boolean createAuditTracking(Object obj) {
    try {

      if (obj instanceof AuditTrackingModel) {
        log.info("AuditTracking  model obj*********" + obj);
        AuditTrackingModel auditTrackingModel = (AuditTrackingModel) obj;
        AuditTrackingLogic auditTrackingLogic = new AuditTrackingLogic();
        AudTrackingEntity audTrackingEntity = new AudTrackingEntity();
        audTrackingEntity = auditTrackingLogic.addrecordId(auditTrackingModel);
        genericDAO.save(audTrackingEntity);

        return true;
      } else {
        log.debug("Unable to convert type to AuditTrackingModel.");
        return false;
      }
    } catch (Exception e) {
      log.error("Error on createAuditTracking: " + e.getMessage());
      e.printStackTrace();
      return false;
    }
  }

  @Override
  public boolean createAccountType(Object obj) {
    try {
      if (obj instanceof AccountTypeModel) {
        AccountTypeModel accountTypeModel = (AccountTypeModel) obj;
        AccountTypeLogic accountTypeLogic = new AccountTypeLogic();
        MdtCnfgAccountTypeEntity mdtCnfgAccountTypeEntity = new MdtCnfgAccountTypeEntity();

        mdtCnfgAccountTypeEntity = accountTypeLogic.addAccountType(accountTypeModel);
        genericDAO.saveOrUpdate(mdtCnfgAccountTypeEntity);

        return true;
      } else {
        log.debug("Unable to convert type to AccountType.");

        return false;
      }
    } catch (Exception e) {
      log.error("Error on createAccountType:" + e.getMessage());
      e.printStackTrace();

      return false;
    }

  }


  @Override
  public List<?> viewAllAccountType() {
    List<AccountTypeModel> allAccountTypeList = new ArrayList<AccountTypeModel>();

    List<MdtCnfgAccountTypeEntity> allMdtCnfgAccountTypeEntityList =
        new ArrayList<MdtCnfgAccountTypeEntity>();

    allMdtCnfgAccountTypeEntityList = genericDAO.findAll(MdtCnfgAccountTypeEntity.class);
    if (allMdtCnfgAccountTypeEntityList.size() > 0) {
      AccountTypeLogic accountTypeLogic = new AccountTypeLogic();
      allAccountTypeList = accountTypeLogic.retreiveAllAccountType(allMdtCnfgAccountTypeEntityList);
    }

    return allAccountTypeList;
  }


  @Override
  public List<?> viewAccountTypeByCriteria(String accountTypeCode) {
    List<AccountTypeModel> allAccountTypeModel = new ArrayList<AccountTypeModel>();

    try {
      List<MdtCnfgAccountTypeEntity> allMdtCnfgAccountTypeEntity =
          genericDAO.findAllByNamedQuery("MdtCnfgAccountTypeEntity.findByAccountTypeCode",
              "accountTypeCode", accountTypeCode);


      if (allMdtCnfgAccountTypeEntity.size() > 0) {
        AccountTypeLogic accountTypeLogic = new AccountTypeLogic();

        for (MdtCnfgAccountTypeEntity localEntity : allMdtCnfgAccountTypeEntity) {
          AccountTypeModel accountTypeModel = new AccountTypeModel();
          accountTypeModel = accountTypeLogic.retreiveAccountType(localEntity);
          allAccountTypeModel.add(accountTypeModel);
        }
      }
    } catch (ObjectNotFoundException onfe) {
      log.error("No object Exists on DB");
    } catch (Exception e) {
      log.error("Error retrieveAccountTypeByCriteria: " + e.getMessage());
      e.printStackTrace();
    }

    return allAccountTypeModel;
  }

  @Override
  public boolean createSeverityCodes(Object obj) {
    try {
      if (obj instanceof SeverityCodesModel) {
        SeverityCodesModel severityCodesModel = (SeverityCodesModel) obj;
        SeverityCodes2Logic severity2CodesLogic = new SeverityCodes2Logic();
        MdtCnfgSeverityCodesEntity mdtCnfgSeverityCodesEntity = new MdtCnfgSeverityCodesEntity();

        mdtCnfgSeverityCodesEntity = severity2CodesLogic.addSeverityCodes(severityCodesModel);
        genericDAO.saveOrUpdate(mdtCnfgSeverityCodesEntity);

        return true;

      } else {
        log.debug("Unable to convert type to SeverityCodes.");

        return false;
      }
    } catch (Exception e) {
      log.error("Error on createSeverityCodes:" + e.getMessage());
      e.printStackTrace();

      return false;
    }

  }

  @Override
  public List viewSeverityCodesByCriteria(String severityCode) {

    List<SeverityCodesModel> allSeverityCodesModel = new ArrayList<SeverityCodesModel>();
    List<MdtCnfgSeverityCodesEntity> allMdtCnfgSeverityCodesEntity =
        new ArrayList<MdtCnfgSeverityCodesEntity>();

    try {
      log.info("String Value---> " + severityCode);
      Short sevCode = Short.parseShort(severityCode);
      log.info("Short Value---> " + sevCode);

      MdtCnfgSeverityCodesEntity mdtCnfgSeverityCodesEntity =
          (MdtCnfgSeverityCodesEntity) genericDAO.findByNamedQueryShort(
              "MdtCnfgSeverityCodesEntity.findBySeverityCode", "severityCode", sevCode);

      //			MdtCnfgSeverityCodesEntity mdtCnfgSeverityCodesEntity  =
      //			(MdtCnfgSeverityCodesEntity) genericDAO.find(MdtCnfgSeverityCodesEntity.class,
      //			sevCode);
      log.info("mdtCnfgSeverityCodesEntity: " + mdtCnfgSeverityCodesEntity);


      if (mdtCnfgSeverityCodesEntity != null) {
        SeverityCodes2Logic severity2CodesLogic = new SeverityCodes2Logic();
        SeverityCodesModel severityCodesModel = new SeverityCodesModel();
        severityCodesModel = severity2CodesLogic.retreiveSeverityCodes(mdtCnfgSeverityCodesEntity);
        allSeverityCodesModel.add(severityCodesModel);
      }
    } catch (ObjectNotFoundException onfe) {
      log.error("No object Exixts on DB");
    } catch (NumberFormatException nfe) {
      log.error("<NFE>Error on  retrieveSeverityCodesByCriteria : " + nfe.getMessage());
      nfe.printStackTrace();

    } catch (Exception e) {
      e.printStackTrace();
      log.error("Error on  retrieveSeverityCodesByCriteria : " + e.getMessage());
    }

    return allSeverityCodesModel;
  }

  //	@Override
  //    public List<?> viewSeverityCodesByCriteria(String severityCode)
  //	{
  //		List<SeverityCodesModel> allSeverityCodesModel = new ArrayList<SeverityCodesModel>();
  //
  //				try
  //				{
  //					log.debug("String Value---> "+severityCode);
  //					Short sevCode = Short.parseShort(severityCode);
  //					log.debug("Short Value---> "+sevCode);
  //					//				List<MdtCnfgSeverityCodesEntity>
  //					allMdtCnfgSeverityCodesEntity = genericDAO.findAllByNamedQuery
  //					("MdtCnfgSeverityCodesEntity.findBySeverityCode","severityCode",
  //					severityCode);
  //					MdtCnfgSeverityCodesEntity mdtCnfgSeverityCodesEntity  =
  //					(MdtCnfgSeverityCodesEntity) genericDAO.find(MdtCnfgSeverityCodesEntity
  //					.class, sevCode);
  //					log.info("mdtCnfgSeverityCodesEntity: "+mdtCnfgSeverityCodesEntity);
  //
  //          if(severityCodesEntityList != null && severityCodesEntityList.size()> 0)
  //          {
  //                for(MdtCnfgSeverityCodesEntity severityEntityModel : severityCodesEntityList)
  //                {
  //                	severityCodesModel = new SeverityCodesModel();
  //                	severityCodesModel = new AdminTranslator()
  //                	.translateMdtCnfgSeverityCodesEntityTocommonsModel(severityEntityModel);
  //                	severityCodesModelList.add(severityCodesModel);
  //                }
  //          }
  //          return severityCodesModel;
  //    }


  public List<?> viewAllSeverityCode() {
    List<SeverityCodesModel> allSeverityCodesList = new ArrayList<SeverityCodesModel>();

    List<MdtCnfgSeverityCodesEntity> allMdtCnfgSeverityCodesEntityList =
        new ArrayList<MdtCnfgSeverityCodesEntity>();

    allMdtCnfgSeverityCodesEntityList = genericDAO.findAll(MdtCnfgSeverityCodesEntity.class);
    if (allMdtCnfgSeverityCodesEntityList.size() > 0) {
      SeverityCodes2Logic severityCodes2Logic = new SeverityCodes2Logic();
      allSeverityCodesList =
          severityCodes2Logic.retreiveAllSeverityCode(allMdtCnfgSeverityCodesEntityList);
    }

    return allSeverityCodesList;
  }

  @Override
  public List<?> viewAllStatusReasonCodes() {
    List<StatusReasonCodesModel> statusReasonCodesList = new ArrayList<StatusReasonCodesModel>();

    List<MdtCnfgStatusReasonCodesEntity> mdtCnfgStatusReasonCodesEntityList =
        new ArrayList<MdtCnfgStatusReasonCodesEntity>();

    mdtCnfgStatusReasonCodesEntityList = genericDAO.findAll(MdtCnfgStatusReasonCodesEntity.class);
    if (mdtCnfgStatusReasonCodesEntityList.size() > 0) {
      StatusReasonCodesLogic statusReasonCodesLogic = new StatusReasonCodesLogic();
      statusReasonCodesList =
          statusReasonCodesLogic.retreiveStatusReasonCodes(mdtCnfgStatusReasonCodesEntityList);
    }

    return statusReasonCodesList;
  }

  @Override
  public List<?> viewAllFileStatus() {
    List<FileStatusCommonsModel> fileStatusCommonsModelList =
        new ArrayList<FileStatusCommonsModel>();

    List<CasSysctrlFileStatusEntity> mdtSysctrlFileStatusEntityList =
        new ArrayList<CasSysctrlFileStatusEntity>();

    mdtSysctrlFileStatusEntityList = genericDAO.findAll(CasSysctrlFileStatusEntity.class);
    if (mdtSysctrlFileStatusEntityList.size() > 0) {
      FileStatusLogic fileStatusLogic = new FileStatusLogic();
      fileStatusCommonsModelList =
          fileStatusLogic.retrieveAllMdtSysctrlFileStatusEntityList(mdtSysctrlFileStatusEntityList);
    }

    return fileStatusCommonsModelList;
  }

  @Override
  public List<?> viewAllOpsServices() {


    List<ServicesModel> servicesModelList = new ArrayList<ServicesModel>();

    List<MdtOpsServicesEntity> mdtOpsServicesEntityList = new ArrayList<MdtOpsServicesEntity>();

    mdtOpsServicesEntityList = genericDAO.findAll(MdtOpsServicesEntity.class);

    if (mdtOpsServicesEntityList.size() > 0) {
      OpsServicesLogic opsServicesLogic = new OpsServicesLogic();
      servicesModelList = opsServicesLogic.retrieveAllOpsServices(mdtOpsServicesEntityList);

    }

    return servicesModelList;
  }


  @Override
  public List<?> viewAllOpsSlaTimes() {


    List<OpsSlaTimesCommonsModel> opsSlaTimesCommonsModelList =
        new ArrayList<OpsSlaTimesCommonsModel>();

    List<MdtOpsSlaTimesEntity> mdtOpsSlaTimesEntityList = new ArrayList<MdtOpsSlaTimesEntity>();

    mdtOpsSlaTimesEntityList = genericDAO.findAll(MdtOpsSlaTimesEntity.class);
    if (mdtOpsSlaTimesEntityList.size() > 0) {
      OpsSlaTimesLogic opsSlaTimesLogic = new OpsSlaTimesLogic();
      opsSlaTimesCommonsModelList =
          opsSlaTimesLogic.retrieveAllOpsSlaTimes(mdtOpsSlaTimesEntityList);
    }

    return opsSlaTimesCommonsModelList;
  }

  @Override
  public List<?> viewStatusReasonCodesByCriteria(String statusReasonCode) {
    List<StatusReasonCodesModel> allstatusReasonCodeModel = new ArrayList<StatusReasonCodesModel>();

    try {
      List<MdtCnfgStatusReasonCodesEntity> mdtCnfgStatusReasonCodesEntity =
          genericDAO.findAllByNamedQuery(
              "MdtCnfgStatusReasonCodesEntity.findByStatusReasonCodeLIKE", "statusReasonCode",
              statusReasonCode + "%");


      if (mdtCnfgStatusReasonCodesEntity.size() > 0) {
        StatusReasonCodesLogic statusReasonCodesLogic = new StatusReasonCodesLogic();

        for (MdtCnfgStatusReasonCodesEntity localEntity : mdtCnfgStatusReasonCodesEntity) {
          StatusReasonCodesModel statusReasonCodeModel = new StatusReasonCodesModel();
          statusReasonCodeModel = statusReasonCodesLogic.retreiveStatusReasonCodes(localEntity);
          allstatusReasonCodeModel.add(statusReasonCodeModel);
        }
      }
    } catch (ObjectNotFoundException onfe) {
      log.error("No object Exists on DB");
    } catch (Exception e) {
      log.error("Error retreiveStatusReasonCodesTypeByCriteria: " + e.getMessage());
      e.printStackTrace();
    }
    return allstatusReasonCodeModel;
  }


  @Override
  public boolean createStatusReasonCodes(Object obj) {

    try {
      if (obj instanceof StatusReasonCodesModel) {
        StatusReasonCodesModel statusReasonCodesModel = (StatusReasonCodesModel) obj;
        StatusReasonCodesLogic statusReasonCodesLogic = new StatusReasonCodesLogic();
        MdtCnfgStatusReasonCodesEntity mdtCnfgStatusReasonCodesEntity =
            new MdtCnfgStatusReasonCodesEntity();

        mdtCnfgStatusReasonCodesEntity =
            statusReasonCodesLogic.addStatusReasonCodes(statusReasonCodesModel);
        genericDAO.saveOrUpdate(mdtCnfgStatusReasonCodesEntity);

        return true;
      } else {
        log.debug("Unable to convert type to StatusReasonCodes.");

        return false;
      }
    } catch (Exception e) {
      log.error("Error on createStatusReasonCodes:" + e.getMessage());
      e.printStackTrace();

      return false;
    }

  }

  @Override
  public List viewAllRecordId() {
    List<SystemControlServicesModel> allSystemControlServicesList =
        new ArrayList<SystemControlServicesModel>();
    List<CasSysctrlServicesEntity> allMdtSysctrlServicesEntityList =
        new ArrayList<CasSysctrlServicesEntity>();

    allMdtSysctrlServicesEntityList =
        genericDAO.findAllOrdered(CasSysctrlServicesEntity.class, "recordId ASC");

    if (allMdtSysctrlServicesEntityList.size() > 0) {
      SystemControlServicesLogic systemControlServicesLogic = new SystemControlServicesLogic();
      allSystemControlServicesList =
          systemControlServicesLogic.retrieveAllRecordId(allMdtSysctrlServicesEntityList);
    }

    return allSystemControlServicesList;
  }


  @Override
  public List viewRecordIdByCriteria(BigDecimal recordId) {
    List<SystemControlServicesModel> allSystemControlServicesList =
        new ArrayList<SystemControlServicesModel>();

    try {
      List<CasSysctrlServicesEntity> allMdtSysctrlServicesEntityList =
          genericDAO.findAllByNamedQuery("MdtSysctrlServicesEntity.findByRecordId", "recordId",
              recordId);

      if (allMdtSysctrlServicesEntityList.size() > 0) {
        SystemControlServicesLogic systemControlServicesLogic = new SystemControlServicesLogic();
        for (CasSysctrlServicesEntity casSysctrlServicesEntity : allMdtSysctrlServicesEntityList) {

          SystemControlServicesModel systemControlServicesModel = new SystemControlServicesModel();
          systemControlServicesModel =
              systemControlServicesLogic.retrieveRecordId(casSysctrlServicesEntity);
          allSystemControlServicesList.add(systemControlServicesModel);
        }
      }
    } catch (ObjectNotFoundException onfe) {
      log.error("No Object Exists on DB");
    } catch (Exception e) {
      log.error("Error on viewServiceIdInByCriteria: "
          + e.getMessage());
      e.printStackTrace();
    }

    return allSystemControlServicesList;
  }

  @Override
  public boolean createRecordId1(Object obj) {
    {

      try {
        if (obj instanceof SystemControlServicesModel) {
          SystemControlServicesModel systemControlServicesModel = (SystemControlServicesModel) obj;
          SystemControlServicesLogic systemControlServicesLogic = new SystemControlServicesLogic();
          CasSysctrlServicesEntity casSysctrlServicesEntity = new CasSysctrlServicesEntity();

          casSysctrlServicesEntity =
              systemControlServicesLogic.addRecordId1(systemControlServicesModel);
          genericDAO.saveOrUpdate(casSysctrlServicesEntity);

          return true;
        } else {
          log.debug("Unable to convert type to createTargetNs.");
          return false;
        }
      } catch (Exception e) {
        log.error("Error on createRejectReasonCodes: " + e.getMessage());

        e.printStackTrace();
        return false;
      }

    }

  }


  @Override

  public List<?> retrieveAllActiveInd() {
    List<CasSysctrlSysParamEntity> mdtSysParamsList = new ArrayList<CasSysctrlSysParamEntity>();

    List<SystemParameterModel> systemParameterModels = new ArrayList<SystemParameterModel>();


    mdtSysParamsList = (List<CasSysctrlSysParamEntity>) genericDAO.findAllByNamedQuery(
        "MdtSysctrlSysParamEntity.findByActiveInd", "activeInd", "Y");


    if (mdtSysParamsList.size() > 0) {

      for (CasSysctrlSysParamEntity localEntity : mdtSysParamsList) {

        SystemParameterModel localModel = new SystemParameterModel();
        localModel =
            new AdminTranslator().translateSystemParameterEntityToCommonsModel(localEntity);
        systemParameterModels.add(localModel);
      }
    }


    return systemParameterModels;

  }

  @Override
  public List<?> retrieveAllInactiveInd() {
    List<CasSysctrlSysParamEntity> mdtSysParamsList = new ArrayList<CasSysctrlSysParamEntity>();
    // SystemParameterModel systemParameterModel = null;

    List<SystemParameterModel> systemParameterModels = new ArrayList<SystemParameterModel>();

    mdtSysParamsList = (List<CasSysctrlSysParamEntity>) genericDAO.findAllByNamedQuery(
        "MdtSysctrlSysParamEntity.findByActiveInd", "activeInd", "N");

    if (mdtSysParamsList.size() > 0) {

      for (CasSysctrlSysParamEntity localEntity : mdtSysParamsList) {

        SystemParameterModel localModel = new SystemParameterModel();
        localModel =
            new AdminTranslator().translateSystemParameterEntityToCommonsModel(localEntity);
        systemParameterModels.add(localModel);
      }
    }

    return systemParameterModels;

  }


  @Override
  public List retrieveSummaryTotals(String memberId, String onlineInd) throws ParseException {


    List<MndtSummaryTotalsEntityModel> summaryTotalsEntityList =
        new ArrayList<MndtSummaryTotalsEntityModel>();
    MndtSummaryTotalsModel mndtSummaryTotalsModel = null;
    List<MndtSummaryTotalsModel> summaTotalsModelList = new ArrayList<MndtSummaryTotalsModel>();
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


    sb.append(
        "SELECT a.SERVICE_ID AS serviceId,b.MEMBER_ID AS memberId,c.MEMBER_NAME AS memberName, a" +
            ".CREATED_DATE AS createdDate ");
    sb.append("FROM MANOWNER.MDT_AC_OPS_MNDT_MSG a ");
    sb.append(
        "LEFT JOIN MANOWNER.MDT_AC_OPS_FIN_INST b ON a.MANDATE_REQ_TRAN_ID = b" +
            ".MANDATE_REQ_TRAN_ID ");
    sb.append("LEFT JOIN MANOWNER.SYS_CIS_BANK c ON b.MEMBER_ID = c.MEMBER_NO ");
    sb.append("WHERE ONLINE_IND= '" + onlineInd + "' AND b.MEMBER_ID = '" + memberId +
        "'  AND a.PROCESS_STATUS = '4' AND  b.FIN_INST_TYPE_ID ='FI04' AND TO_CHAR(a" +
        ".CREATED_DATE, 'ddMMYYYY') < '" +
        stringDate + "' ");


    String sqlQuery = sb.toString();
    log.debug("The Sql query is  ***************" + sqlQuery);
    List<String> scalarList = new ArrayList<String>();
    scalarList.add("serviceId");
    scalarList.add("memberId");
    scalarList.add("memberName");
    scalarList.add("createdDate");

    try {
      summaryTotalsEntityList =
          genericDAO.findBySql(sqlQuery, scalarList, MndtSummaryTotalsEntityModel.class);
      log.debug("outsReportModelEntityList FROM DB----> " + summaryTotalsEntityList);

      if (summaryTotalsEntityList != null && summaryTotalsEntityList.size() > 0) {
        for (MndtSummaryTotalsEntityModel mndtSummaryTotalsEntityModel : summaryTotalsEntityList) {
          MndtSummaryTotalsModel localModel = new MndtSummaryTotalsModel();
          localModel = new ServiceTranslator().translateMndtSummaryTotalsEntityModelToCommonsModel(
              mndtSummaryTotalsEntityModel);
          summaTotalsModelList.add(localModel);
        }
      }
    } catch (ObjectNotFoundException onfe) {
      log.error("No Object Exists on DB");
    } catch (Exception e) {
      log.error("Error on retrieveSummaryTotals :" + e.getMessage());
      e.printStackTrace();
    }

    return summaTotalsModelList;

  }


  @Override
  public List<?> retrieveInActiveCisDownload() {
    List<MdtAcOpsProcessControlsEntity> mdtAcOpsProcessControlsList =
        new ArrayList<MdtAcOpsProcessControlsEntity>();

    mdtAcOpsProcessControlsList = genericDAO.findAll(MdtAcOpsProcessControlsEntity.class);
    log.debug(
        "mdtAcOpsProcessControlsList.size()in adminBean: " + mdtAcOpsProcessControlsList.size());
    log.debug("mdtAcOpsProcessControlsListin adminBean: " + mdtAcOpsProcessControlsList);
    return mdtAcOpsProcessControlsList;
  }


  @Override
  public boolean createCisDownload(Object obj) {
    if (obj instanceof MdtAcOpsProcessControlsEntity) {
      MdtAcOpsProcessControlsEntity mdtAcOpsProcessControlsEntity =
          (MdtAcOpsProcessControlsEntity) obj;
      try {
        genericDAO.save(mdtAcOpsProcessControlsEntity);
        return true;
      } catch (Exception ex) {
        log.error("Error on createCisDownload :" + ex.getMessage());
        return false;
      }
    } else {
      return false;
    }
  }


  @Override
  public List<?> retrieveCountNrOfFilesStatus() {

    List<FileStatusReportModel> fileStatusReportList = new ArrayList<FileStatusReportModel>();
    List<FileStatusReportEntityModel> fileStatusReportEntityList =
        new ArrayList<FileStatusReportEntityModel>();


    StringBuffer sb = new StringBuffer();

    sb.append("SELECT SUM(a.NR_OF_FILES) as nrOfFiles ");
    sb.append("FROM MANOWNER.MDT_AC_OPS_MNDT_COUNT a ");
    sb.append("LEFT OUTER JOIN MANOWNER.MDT_OPS_FILE_REG b ");
    sb.append("ON a.FILE_NAME = b.FILE_NAME ");


    String sqlQuery = sb.toString();
    log.debug("sqlQuery:" + sqlQuery);

    List<String> scalarList = new ArrayList<String>();

    scalarList.add("nrOfFiles");
    log.debug("scalarList: " + scalarList);

    try {
      fileStatusReportEntityList =
          genericDAO.findBySql(sqlQuery, scalarList, FileStatusReportEntityModel.class);

      if (fileStatusReportEntityList.size() > 0) {
        for (FileStatusReportEntityModel localEntity : fileStatusReportEntityList) {
          FileStatusReportModel localModel = new FileStatusReportModel();
          localModel = new ServiceTranslator().translateFileStatusReportEntityModelToCommonsModel(
              localEntity);
          fileStatusReportList.add(localModel);
        }
      }
    } catch (ObjectNotFoundException onfe) {
      log.error("No Object Exists on DB");
    } catch (Exception e) {
      log.error("Error on retrieveCountNrOfFilesStatus :" + e.getMessage());
      e.printStackTrace();
    }

    log.debug("fileStatusReportEntityList COUNT nrOfFiles: " + fileStatusReportEntityList);
    return fileStatusReportList;


  }

  @Override
  public List<?> retrieveCountNrOfMsgsStatus() {
    List<FileStatusReportModel> fileStatusReportList = new ArrayList<FileStatusReportModel>();
    List<FileStatusReportEntityModel> fileStatusReportEntityList =
        new ArrayList<FileStatusReportEntityModel>();


    StringBuffer sb = new StringBuffer();

    sb.append("SELECT SUM (a.NR_OF_MSGS) as nrOfMsgs ");
    sb.append("FROM MANOWNER.MDT_AC_OPS_MNDT_COUNT a ");
    sb.append("LEFT OUTER JOIN MANOWNER.MDT_OPS_FILE_REG b ");
    sb.append("ON a.FILE_NAME = b.FILE_NAME ");

    String sqlQuery = sb.toString();
    log.debug("sqlQuery:" + sqlQuery);

    List<String> scalarList = new ArrayList<String>();
    scalarList.add("nrOfMsgs");
    log.debug("scalarList: " + scalarList);


    try {
      fileStatusReportEntityList =
          genericDAO.findBySql(sqlQuery, scalarList, FileStatusReportEntityModel.class);

      if (fileStatusReportEntityList.size() > 0) {
        for (FileStatusReportEntityModel localEntity : fileStatusReportEntityList) {
          FileStatusReportModel localModel = new FileStatusReportModel();
          localModel = new ServiceTranslator().translateFileStatusReportEntityModelToCommonsModel(
              localEntity);
          fileStatusReportList.add(localModel);
        }
      }
    } catch (ObjectNotFoundException onfe) {
      log.error("No Object Exists on DB");
    } catch (Exception e) {
      log.error("Error on retrieveCountNrOfMsgsStatus :" + e.getMessage());
      e.printStackTrace();
    }

    log.debug("fileStatusReportEntityList COUNT NrOfMsgs: " + fileStatusReportEntityList);
    return fileStatusReportList;

  }

  @Override
  public List retrieveSysMemberNo(String memberNo) {

    List<SysCisBankEntity> cisBankEntityList =
        (List<SysCisBankEntity>) genericDAO.findAllByNamedQuery("SysCisBankEntity.findByMemberNo",
            "memberNo", memberNo);

    return cisBankEntityList;
  }


  @Override
  public List<?> retrieveOutstandingResponsesY(String instId, String string) {
    List<OutstandingResponsesModelEntity> outsReportModelEntityList =
        new ArrayList<OutstandingResponsesModelEntity>();
    OutstandingResponsesModel outstandingResponsesModel = null;
    //MdtAcOpsFinInstEntity debtorEntity;
    List<OutstandingResponsesModel> outstandingResponsesModelsList =
        new ArrayList<OutstandingResponsesModel>();

    StringBuffer sb = new StringBuffer();

    sb.append(
        " SELECT a.MANDATE_REQ_TRAN_ID as mandateReqTranId, a.MANDATE_REQ_ID as mandateReqId ,a" +
            ".SERVICE_ID as serviceId, a.FILE_NAME as fileName,b.MEMBER_ID as creditorMemberNo, b" +
            ".MEMBER_ID as debtorMemberNo ");
    sb.append(" FROM MANOWNER.MDT_AC_OPS_MNDT_MSG a ");
    sb.append(
        " LEFT OUTER JOIN MANOWNER.MDT_AC_OPS_FIN_INST b ON a.MANDATE_REQ_TRAN_ID = b" +
            ".MANDATE_REQ_TRAN_ID ");
    sb.append(" LEFT OUTER JOIN MANOWNER.MDT_OPS_FILE_REG c ON a.FILE_NAME = c.FILE_NAME ");
    sb.append(
        " WHERE a.ONLINE_IND = 'Y' AND a.PROCESS_STATUS = '4' AND  b.FIN_INST_TYPE_ID = 'FI04'  " +
            "Or b.FIN_INST_TYPE_ID = 'FI03' ");

    String sqlQuery = sb.toString();
    log.debug("The Sql query is  ***************" + sqlQuery);
    List<String> scalarList = new ArrayList<String>();
    scalarList.add("mandateReqTranId");
    scalarList.add("mandateReqId");
    //   scalarList.add("mandateRefNr");
    scalarList.add("serviceId");
    scalarList.add("creditorMemberNo");
    scalarList.add("debtorMemberNo");
    scalarList.add("fileName");

    outsReportModelEntityList =
        genericDAO.findBySql(sqlQuery, scalarList, OutstandingResponsesModelEntity.class);
    log.debug("outsReportModelEntityList FROM DB----> " + outsReportModelEntityList);
    if (outsReportModelEntityList != null && outsReportModelEntityList.size() > 0) {
      for (OutstandingResponsesModelEntity outstRespEntity : outsReportModelEntityList) {
        log.debug("In the translation method----------> ");
        //Translate from entity's to common's model
        ReportsLogic reportsLogic = new ReportsLogic();
        outstandingResponsesModel = new OutstandingResponsesModel();
        outstandingResponsesModel =
            reportsLogic.translateOutstandingResponsesEntityToCommonsModel(outstRespEntity);
        outstandingResponsesModelsList.add(outstandingResponsesModel);
      }
    }
    log.debug("outstandingResponsesModelsList ----> " + outstandingResponsesModelsList.toString());
    return outstandingResponsesModelsList;
  }

  @Override
  public List<?> retrieveOutstandingResponsesN(String instId, String string) {
    List<OutstandingResponsesModelEntity> outsReportModelEntityList =
        new ArrayList<OutstandingResponsesModelEntity>();
    OutstandingResponsesModel outstandingResponsesModel = null;
    //MdtAcOpsFinInstEntity debtorEntity;

    List<OutstandingResponsesModel> outstandingResponsesModelsList =
        new ArrayList<OutstandingResponsesModel>();

    StringBuffer sb = new StringBuffer();

    sb.append(
        " SELECT a.MANDATE_REQ_TRAN_ID as mandateReqTranId, a.MANDATE_REQ_ID as mandateReqId ,a" +
            ".SERVICE_ID as serviceId, a.FILE_NAME as fileName,b.MEMBER_ID as creditorMemberNo, b" +
            ".MEMBER_ID as debtorMemberNo  ");
    sb.append(" FROM MANOWNER.MDT_AC_OPS_MNDT_MSG a           ");
    sb.append(
        " LEFT OUTER JOIN MANOWNER.MDT_AC_OPS_FIN_INST b ON a.MANDATE_REQ_TRAN_ID = b" +
            ".MANDATE_REQ_TRAN_ID ");
    sb.append(" LEFT OUTER JOIN MANOWNER.MDT_OPS_FILE_REG c ON a.FILE_NAME = c.FILE_NAME ");
    sb.append(
        " WHERE a.ONLINE_IND = 'N' AND a.PROCESS_STATUS = '4' AND  b.FIN_INST_TYPE_ID = 'FI04'  " +
            "Or b.FIN_INST_TYPE_ID = 'FI03'");

    String sqlQuery = sb.toString();
    log.debug("The Sql query is  ***************" + sqlQuery);
    List<String> scalarList = new ArrayList<String>();
    scalarList.add("mandateReqTranId");
    scalarList.add("mandateReqId");
    //  scalarList.add("mandateRefNr");
    scalarList.add("serviceId");
    scalarList.add("creditorMemberNo");
    scalarList.add("debtorMemberNo");
    scalarList.add("fileName");

    outsReportModelEntityList =
        genericDAO.findBySql(sqlQuery, scalarList, OutstandingResponsesModelEntity.class);
    log.debug("outsReportModelEntityList FROM DB----> " + outsReportModelEntityList);
    if (outsReportModelEntityList != null && outsReportModelEntityList.size() > 0) {
      for (OutstandingResponsesModelEntity outstRespEntity : outsReportModelEntityList) {
        log.debug("In the translation method----------> ");
        //Translate from entity's to common's model
        ReportsLogic reportsLogic = new ReportsLogic();
        outstandingResponsesModel = new OutstandingResponsesModel();
        outstandingResponsesModel =
            reportsLogic.translateOutstandingResponsesEntityToCommonsModel(outstRespEntity);
        outstandingResponsesModelsList.add(outstandingResponsesModel);
      }
    }
    log.debug("outstandingResponsesModelsList ----> " + outstandingResponsesModelsList.toString());
    return outstandingResponsesModelsList;
  }

  @Override
  public List<?> viewFileStatusFilter(String fileName) {

    //List<MdtOpsFileRegEntity> opsFileRegEntityList = new ArrayList<MdtOpsFileRegEntity>();
    List<OpsFileRegModel> opsFileRegModelList = new ArrayList<OpsFileRegModel>();

    try {

      List<MdtOpsFileRegEntity> opsFileRegEntityList =
          (List<MdtOpsFileRegEntity>) genericDAO.findAllByNamedQuery(
              "MdtOpsFileRegEntity.findByFileName", "fileName", fileName);
      //log.info("---------------opsFileRegEntityList: ------------------"+ opsFileRegEntityList);

      if (opsFileRegEntityList.size() > 0) {
        OpsFileRegLogic opsFileRegLogic = new OpsFileRegLogic();
        for (MdtOpsFileRegEntity localEntity : opsFileRegEntityList) {
          OpsFileRegModel localModel = new OpsFileRegModel();

          localModel = opsFileRegLogic.retrieveDelDelivery(localEntity);

          opsFileRegModelList.add(localModel);
        }

      }
    } catch (ObjectNotFoundException onfe) {
      log.error("No Object Exists on DB");
    } catch (Exception e) {
      log.error("Error on viewFileStatusFilter :" + e.getMessage());
      e.printStackTrace();
    }
    //log.info("In the ADMIN BEAN********* :"+opsFileRegModelList);

    return opsFileRegModelList;


  }

  @Override
  public List<?> viewFileStatusSearch(String memberNo, String service) {
    List<OpsFileRegModel> searchedFilesList = new ArrayList<OpsFileRegModel>();
    List<MdtOpsFileRegEntity> opsFileRegList = new ArrayList<MdtOpsFileRegEntity>();
    try {

      HashMap<String, Object> parameters = new HashMap<String, Object>();
      parameters.put("fileName", "%" + service + "%");
      parameters.put("fileName2", "%" + memberNo + "%");

      //log.info("parameters---> "+parameters.toString());
      opsFileRegList =
          genericDAO.findAllByNQCriteria("MdtOpsFileRegEntity.findByFileNameLike2", parameters);
      //log.info("---------------opsFileRegList after findAllByCriteria: ------------------" +
      // opsFileRegList);


      if (opsFileRegList != null && opsFileRegList.size() > 0) {
        OpsFileRegLogic opsFileRegLogic = new OpsFileRegLogic();
        for (MdtOpsFileRegEntity localEntity : opsFileRegList) {
          OpsFileRegModel localModel = new OpsFileRegModel();

          localModel = opsFileRegLogic.retrieveDelDelivery(localEntity);

          searchedFilesList.add(localModel);
        }
      }
    } catch (ObjectNotFoundException onfe) {
      log.error("No Object Exists on DB");
    } catch (Exception e) {
      log.error("Error on viewFileStatusSearch: " + e.getMessage());
      e.printStackTrace();
    }

    return searchedFilesList;
  }

  public List<?> searchByFileName(String fileName) {
    List<OpsFileRegModel> searchedFilesList = new ArrayList<OpsFileRegModel>();
    List<MdtOpsFileRegEntity> opsFileRegList = new ArrayList<MdtOpsFileRegEntity>();
    try {

      HashMap<String, Object> parameters = new HashMap<String, Object>();
      parameters.put("fileName", "%" + fileName + "%");


      log.info("parameters---> " + parameters.toString());
      opsFileRegList =
          genericDAO.findAllByNQCriteria("MdtOpsFileRegEntity.findByFileNameLike1", parameters);
      log.info("---------------opsFileRegList after findAllByCriteria: ------------------" +
          opsFileRegList);


      if (opsFileRegList != null && opsFileRegList.size() > 0) {
        OpsFileRegLogic opsFileRegLogic = new OpsFileRegLogic();
        for (MdtOpsFileRegEntity localEntity : opsFileRegList) {
          OpsFileRegModel localModel = new OpsFileRegModel();

          localModel = opsFileRegLogic.retrieveDelDelivery(localEntity);

          searchedFilesList.add(localModel);
        }
      }
    } catch (ObjectNotFoundException onfe) {
      log.error("No Object Exists on DB");
    } catch (Exception e) {
      log.error("Error on viewFileStatusSearch: " + e.getMessage());
      e.printStackTrace();
    }

    return searchedFilesList;
  }

  @Override
  public List retrieveOutstandingResponsesD(String memberId, String onlineInd) {

    List<OutstandingResponsesDebtorModelEntity> outsReportModelEntityList =
        new ArrayList<OutstandingResponsesDebtorModelEntity>();
    OutstandingResponsesDebtorModel outstandingResponsesModel = null;
    //MdtAcOpsFinInstEntity debtorEntity;
    List<OutstandingResponsesDebtorModel> outstandingResponsesModelsList =
        new ArrayList<OutstandingResponsesDebtorModel>();


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


    sb.append(
        " SELECT a.MANDATE_REQ_TRAN_ID as mandateReqTranId, a.MANDATE_REQ_ID as mandateReqId,a" +
            ".SERVICE_ID as serviceId,a.FILE_NAME as fileName, c.MANDATE_REF_NR as mandateRefNr,b" +
            ".MEMBER_ID as debtorMemberNo ");
    sb.append(
        "	FROM MANOWNER.MDT_AC_OPS_MNDT_MSG a, MANOWNER.MDT_AC_OPS_FIN_INST b, MANOWNER" +
            ".MDT_AC_OPS_SUPPL_DATA c ");
    sb.append("	WHERE a.MSG_ID = b.MSG_ID AND a.MSG_ID = c.MSG_Id AND ");
    sb.append(
        "	a.MANDATE_REQ_TRAN_ID = b.MANDATE_REQ_TRAN_ID AND a.MANDATE_REQ_TRAN_ID = c" +
            ".MANDATE_REQ_TRAN_ID AND ");
    sb.append("b.FIN_INST_TYPE_ID = 'FI04' AND a.ONLINE_IND = '" + onlineInd +
        "' AND a.PROCESS_STATUS = '4' AND TO_CHAR(a.CREATED_DATE, 'ddMMYYYY') < '" + stringDate +
        "' ");

		/*
		sb.append(" SELECT a.MANDATE_REQ_TRAN_ID as mandateReqTranId, a.MANDATE_REQ_ID as
		mandateReqId ,a.MANDATE_REF_NR as mandateRefNr,a.SERVICE_ID as serviceId, b.MEMBER_ID as
		debtorMemberNo,a.FILE_NAME as fileName ");
		sb.append(" FROM MDT_AC_OPS_MNDT_MSG a ");
		sb.append(" LEFT JOIN MDT_AC_OPS_FIN_INST b ON a.MANDATE_REQ_TRAN_ID = b
		.MANDATE_REQ_TRAN_ID ");
		sb.append(" LEFT JOIN SYS_CIS_BANK c ON b.MEMBER_ID = c.MEMBER_NO ");
		sb.append(" WHERE ONLINE_IND= 'N' AND a.PROCESS_STATUS = '4' AND  b.FIN_INST_TYPE_ID
		='FI04'");

		 */
    String sqlQuery = sb.toString();
    log.debug("The Sql query is  ***************" + sqlQuery);
    List<String> scalarList = new ArrayList<String>();
    scalarList.add("mandateReqTranId");
    scalarList.add("mandateReqId");
    scalarList.add("mandateRefNr");
    scalarList.add("serviceId");
    scalarList.add("debtorMemberNo");
    scalarList.add("fileName");

    outsReportModelEntityList =
        genericDAO.findBySql(sqlQuery, scalarList, OutstandingResponsesDebtorModelEntity.class);
    log.debug("retrieveOutstandingResponsesD FROM DB----> " + outsReportModelEntityList);
    if (outsReportModelEntityList != null && outsReportModelEntityList.size() > 0) {
      for (OutstandingResponsesDebtorModelEntity outstRespEntity : outsReportModelEntityList) {
        //Translate from entity's to common's model
        outstandingResponsesModel = new OutstandingResponsesDebtorModel();
        outstandingResponsesModel =
            new ServiceTranslator().translateOutstandingResponsesDebtorEntityToCommonsModel(
                outstRespEntity);
        outstandingResponsesModelsList.add(outstandingResponsesModel);
      }
    }

    log.debug("outstandingResponsesModelsList for Debtor ----> " +
        outstandingResponsesModelsList.toString());
    return outstandingResponsesModelsList;


  }


  @Override
  public List retrieveAllEotSot(String destMemberId, String serviceName, String sotInInd,
                                String eotInInd, String sotOutInd, String eotOutInd) {
    List<MdtAcOpsSotEotCtrlEntity> opsSotEotEntityList = new ArrayList<MdtAcOpsSotEotCtrlEntity>();
    List<AcOpsSotEotCntrlModel> opsSotEotModelList = new ArrayList<AcOpsSotEotCntrlModel>();

    try {
      log.debug("instId: " + destMemberId);
      log.debug("serviceId: " + serviceName);
      log.debug("sotIn: " + sotInInd);
      log.debug("eotIn: " + eotInInd);
      log.debug("sotOut: " + sotOutInd);
      log.debug("eotOut: " + eotOutInd);

      HashMap<String, Object> parameters = new HashMap<String, Object>();
      parameters.put("mdtAcOpsSotEotCtrlPK.instId", destMemberId);
      parameters.put("mdtAcOpsSotEotCtrlPK.serviceId", serviceName);
      parameters.put("sotIn", sotInInd);
      parameters.put("eotIn", eotInInd);
      parameters.put("sotOut", sotOutInd);
      parameters.put("eotOut", eotOutInd);
      opsSotEotEntityList = (List<MdtAcOpsSotEotCtrlEntity>) genericDAO.findAllByCriteria(
          MdtAcOpsSotEotCtrlEntity.class, parameters);


      if (opsSotEotEntityList.size() > 0) {


        AcOpsSotEotLogic acOpsSotEotLogic = new AcOpsSotEotLogic();
        for (MdtAcOpsSotEotCtrlEntity localEntity : opsSotEotEntityList) {
          AcOpsSotEotCntrlModel localModel = new AcOpsSotEotCntrlModel();
          localModel = acOpsSotEotLogic.retrieveMdtAcOpsSotEotCtrlEntity(localEntity);
          opsSotEotModelList.add(localModel);

        }

      }

    } catch (ObjectNotFoundException onfe) {
      log.error("No Object Exists on DB");
    } catch (Exception e) {
      log.error("Error on retrieveAllEotSot :" + e.getMessage());
      e.printStackTrace();
    }

    log.debug("---------------opsSotEotModelList: ------------------" + opsSotEotModelList);
    return opsSotEotModelList;
  }

  @Override
  public List retrieveSotEotServiceId() {

    List<AcOpsSotEotCntrlModel> soteotModelList = new ArrayList<AcOpsSotEotCntrlModel>();
    List<CasOpsSotEotEntityModel> sotEotEntityList = new ArrayList<CasOpsSotEotEntityModel>();

    StringBuffer sb = new StringBuffer();

    sb.append("SELECT SERVICE_ID as serviceId ");
    sb.append("FROM MANOWNER.MDT_AC_OPS_SOT_EOT_CTRL ");
    sb.append("GROUP BY SERVICE_ID ");

    String sqlQuery = sb.toString();
    log.debug("sqlQuery:" + sqlQuery);

    List<String> scalarList = new ArrayList<String>();
    scalarList.add("serviceId");
    log.debug("scalarList: " + scalarList);

    try {

      sotEotEntityList = genericDAO.findBySql(sqlQuery, scalarList, CasOpsSotEotEntityModel.class);

      if (sotEotEntityList.size() > 0) {

        for (CasOpsSotEotEntityModel localEntity : sotEotEntityList) {
          AcOpsSotEotCntrlModel localModel = new AcOpsSotEotCntrlModel();
          localModel =
              new ServiceTranslator().translateAcOpsSotEotEntityModelToCommonsModel(localEntity);
          soteotModelList.add(localModel);

        }
      }
    } catch (ObjectNotFoundException onfe) {
      log.error("No Object Exists on DB");
    } catch (Exception e) {
      log.error("Error on retrieveSotEotServiceId :" + e.getMessage());
      e.printStackTrace();
    }

    log.debug("retrieveSotEotServiceId: " + soteotModelList);
    return soteotModelList;

  }


  public Object retrieveOpsService(String outgoingService) {
    MdtOpsServicesEntity mdtOpsServicesEntity = new MdtOpsServicesEntity();

    try {
      mdtOpsServicesEntity = (MdtOpsServicesEntity) genericDAO.findByNamedQuery(
          "MdtOpsServicesEntity.findByServiceIdOut", "serviceIdOut", outgoingService);
    } catch (ObjectNotFoundException ne) {
      log.debug("No matching record found:  " + ne.getMessage());
      ne.printStackTrace();
    } catch (Exception e) {
      log.debug("Error on retrieveOpsService: " + e.getMessage());
      e.printStackTrace();
    }

    return mdtOpsServicesEntity;
  }

  @Override
  public void compareCISwithCustPram() {
    StartOfDayLogic startofDayLogic = new StartOfDayLogic();
    startofDayLogic.populateCisMembersToCustParam();
  }


  @Override
  public List<?> viewSysCntrlServiceByService(String service) {
    List<SystemControlServicesModel> allSystemControlServicesList =
        new ArrayList<SystemControlServicesModel>();

    try {

      HashMap<String, Object> parameters = new HashMap<String, Object>();
      parameters.put("serviceIdIn", service);
      parameters.put("serviceIdOut", service);

      log.debug("parameters---> " + parameters);
      List<CasSysctrlServicesEntity> allMdtSysctrlServicesEntityList =
          (List<CasSysctrlServicesEntity>) genericDAO.findAllByCriteriaUsingOr(
              CasSysctrlServicesEntity.class, parameters);
      log.debug("allMdtSysctrlServicesEntityList: " + allMdtSysctrlServicesEntityList);

      if (allMdtSysctrlServicesEntityList != null && allMdtSysctrlServicesEntityList.size() > 0) {
        SystemControlServicesLogic systemControlServicesLogic = new SystemControlServicesLogic();
        for (CasSysctrlServicesEntity casSysctrlServicesEntity : allMdtSysctrlServicesEntityList) {
          SystemControlServicesModel systemControlServicesModel = new SystemControlServicesModel();
          systemControlServicesModel =
              systemControlServicesLogic.retrieveRecordId(casSysctrlServicesEntity);
          allSystemControlServicesList.add(systemControlServicesModel);
        }
      }
    } catch (ObjectNotFoundException onfe) {
      log.error("No Object Exists on DB");
    } catch (Exception e) {
      log.error("Error on viewSysCntrlServiceByService: " + e.getMessage());
      e.printStackTrace();
    }

    return allSystemControlServicesList;
  }

  public List<?> retrieveSysCntrlScheduler() {
    List<CasSysctrlSchedulerEntity> schedulerEntityList =
        new ArrayList<CasSysctrlSchedulerEntity>();
    schedulerEntityList = genericDAO.findAll(CasSysctrlSchedulerEntity.class);

    return schedulerEntityList;
  }

  @Override
  public List<?> retrieveOpsScheduler() {
    List<MdtAcOpsSchedulerEntity> opsSchedulerList = new ArrayList<MdtAcOpsSchedulerEntity>();
    opsSchedulerList = genericDAO.findAll(MdtAcOpsSchedulerEntity.class);
    return opsSchedulerList;
  }

  @Override
  public boolean createOpsScheduler(Object obj) {
    boolean saved = false;
    if (obj instanceof MdtAcOpsSchedulerEntity) {
      MdtAcOpsSchedulerEntity mdtAcOpsSchedulerEntity = (MdtAcOpsSchedulerEntity) obj;
      try {
        genericDAO.saveOrUpdate(mdtAcOpsSchedulerEntity);
        saved = true;
      } catch (Exception ex) {
        log.error("Error on createOpsScheduler: " + ex.getMessage());
        saved = false;
      }
    } else {
      saved = false;
    }
    return saved;
  }


  @Override
  public List<?> retrieveAuditTracking(String userId, Date fromDate, Date toDate, String action) {
    List<AuditTrackingEntityModel> auditTrackingEntityList =
        new ArrayList<AuditTrackingEntityModel>();
    List<AuditTrackingSqlModel> auditTrackingModelList = new ArrayList<AuditTrackingSqlModel>();

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String strFromDate = sdf.format(fromDate);
    String strToDate = sdf.format(toDate);

    StringBuffer sb = new StringBuffer();
    sb.append(
        "SELECT USER_ID  as userId, ACTION_DATE as actionDate,  TABLE_NAME as tableName, " +
            "COLUMN_NAME as columnName, ");
    sb.append("OLD_VALUE as oldValue, NEW_VALUE as newValue, ACTION as action ");
    sb.append("FROM MANOWNER.AUD_TRACKING ");
    sb.append("WHERE USER_ID ='" + userId + "' AND ACTION_DATE BETWEEN TO_DATE('" + strToDate +
        "','yyyy-MM-dd') AND TO_DATE('" + strFromDate + "','yyyy-MM-dd') AND  ACTION = '" + action +
        "' ");


    String sqlQuery = sb.toString();
    log.debug("sqlQuery: " + sqlQuery);

    List<String> scalarList = new ArrayList<String>();
    log.debug("scalarList: " + scalarList);
    scalarList.add("actionDate");
    scalarList.add("columnName");
    scalarList.add("oldValue");
    scalarList.add("newValue");
    scalarList.add("action");
    scalarList.add("userId");
    scalarList.add("tableName");

    try {


      auditTrackingEntityList =
          genericDAO.findBySql(sqlQuery, scalarList, AuditTrackingEntityModel.class);


      if (auditTrackingEntityList.size() > 0) {


        AuditTrackingLogic auditTrackingLogic = new AuditTrackingLogic();
        for (AuditTrackingEntityModel localEntity : auditTrackingEntityList) {
          AuditTrackingSqlModel localModel = new AuditTrackingSqlModel();
          localModel =
              new ServiceTranslator().translateAuditTrackingEntityModelToCommonsModel(localEntity);
          auditTrackingModelList.add(localModel);

        }

      }

    } catch (ObjectNotFoundException onfe) {
      log.error("No Object Exists on DB");
    } catch (Exception e) {
      log.error("Error on retrieveAuditTracking :" + e.getMessage());
      e.printStackTrace();
    }


    return auditTrackingModelList;
  }

  private static void contextCISServiceBeanCheck() {
    if (cisServiceRemote == null) {
      cisServiceRemote = Util.getCisServiceRemote();
    }

  }

  @Override
  public List<?> viewAllOpsSchedulers() {
    List<SchedulerCommonsModel> allSchedulerList = new ArrayList<SchedulerCommonsModel>();
    List<MdtAcOpsSchedulerEntity> allAcOpsSchedulerList = new ArrayList<MdtAcOpsSchedulerEntity>();

    allAcOpsSchedulerList =
        genericDAO.findAllOrdered(MdtAcOpsSchedulerEntity.class, "schedulerKey ASC ");

    log.debug(
        "The entity list has the following information ##############" + allAcOpsSchedulerList);
    if (allAcOpsSchedulerList != null && allAcOpsSchedulerList.size() > 0) {
      SchedulerScreenLogic schedulerScreenLogic = new SchedulerScreenLogic();
      allSchedulerList = schedulerScreenLogic.retrieveAllOpsSchedulers(allAcOpsSchedulerList);

      log.debug(
          "The commons model list has this information %%%%%%%%%%%%%%%%%%%%%%" + allSchedulerList);
    }
    return allSchedulerList;
  }


  public Object retrieveCisStartTime() {
    CasSysctrlSlaTimesEntity casSysctrlSlaTimesEntity;
    casSysctrlSlaTimesEntity = (CasSysctrlSlaTimesEntity) genericDAO.findByNamedQuery(
        "MdtSysctrlSlaTimesEntity.findByService", "service", "CIS");


    return casSysctrlSlaTimesEntity;
  }

  @Override
  public List<?> viewEOTFile(String memberNo) {
    List<OpsFileRegModel> eotFileList = new ArrayList<OpsFileRegModel>();
    List<MdtOpsFileRegEntity> opsFileRegEntityList = new ArrayList<MdtOpsFileRegEntity>();
    try {

      HashMap<String, Object> parameters = new HashMap<String, Object>();
      parameters.put("fileName2", "%" + memberNo + "%");
      log.debug("parameters---> " + parameters.toString());
      opsFileRegEntityList =
          genericDAO.findAllByNQCriteria("MdtOpsFileRegEntity.findByFileNameLike", parameters);
      log.debug("---------------opsFileRegList after findAllByCriteria: ------------------" +
          opsFileRegEntityList);


      if (opsFileRegEntityList != null && opsFileRegEntityList.size() > 0) {
        OpsFileRegLogic opsFileRegLogic = new OpsFileRegLogic();
        for (MdtOpsFileRegEntity localEntity : opsFileRegEntityList) {
          OpsFileRegModel localModel = new OpsFileRegModel();

          localModel = opsFileRegLogic.retrieveDelDelivery(localEntity);

          eotFileList.add(localModel);
        }
      }
    } catch (ObjectNotFoundException onfe) {
      log.error("No Object Exists on DB");
    } catch (Exception e) {
      log.error("Error on eotFileList: " + e.getMessage());
      e.printStackTrace();
    }

    return eotFileList;
  }

  @Override
  public List<?> viewEotMessages() {
    List<MdtAcOpsTransCtrlMsgEntity> mdtAcOpsTransCtrlMsgEntityList =
        new ArrayList<MdtAcOpsTransCtrlMsgEntity>();

    List<TransCtrlMsgModel> transCtrlMsgModelList = new ArrayList<TransCtrlMsgModel>();

    mdtAcOpsTransCtrlMsgEntityList =
        (List<MdtAcOpsTransCtrlMsgEntity>) genericDAO.findAllByNamedQuery(
            "MdtAcOpsTransCtrlMsgEntity.findByMsgType", "msgType", "EOT");

    if (mdtAcOpsTransCtrlMsgEntityList.size() > 0) {
      for (MdtAcOpsTransCtrlMsgEntity localEntity : mdtAcOpsTransCtrlMsgEntityList) {
        TransCtrlMsgModel localModel = new TransCtrlMsgModel();
        localModel =
            new AdminTranslator().translateMdtAcOpsTransCtrlMsgEntityToCommonsModel(localEntity);
        transCtrlMsgModelList.add(localModel);
      }
    }
    return transCtrlMsgModelList;

  }


  @Override
  public List<?> retrieveStatusReport(String instId, String serviceId) {

    log.debug("instId---> " + instId);
    log.debug("serviceId---> " + serviceId);
    List<StatusReportEotModelEntity> statusReportEotModelEntityList =
        new ArrayList<StatusReportEotModelEntity>();
    StatusReportEotModelEntity statusReportEotModelEntity = null;

    StringBuffer sb = new StringBuffer();
    sb.append(
        "SELECT  INSTG_AGT as instrAgnt, SERVICE as serviceId,  COUNT(SERVICE) AS numberOfFiles ");
    sb.append("FROM MANOWNER.MDT_AC_OPS_STATUS_HDRS ");
    sb.append("WHERE INSTG_AGT = '" + instId + "' AND SERVICE = '" + serviceId + "' ");
    sb.append("GROUP BY  INSTG_AGT , SERVICE ");

    String sqlQuery = sb.toString();
    log.debug("The Sql query is  ***************" + sqlQuery);
    List<String> scalarList = new ArrayList<String>();
    scalarList.add("numberOfFiles");
    scalarList.add("instrAgnt");
    scalarList.add("serviceId");

    statusReportEotModelEntityList =
        genericDAO.findBySql(sqlQuery, scalarList, StatusReportEotModelEntity.class);
    log.debug(
        "StatusReportEotModelEntity from Db----> " + statusReportEotModelEntityList.toString());
    return statusReportEotModelEntityList;

  }


  @Override
  public boolean createAmendmentCodes(Object obj) {
    try {
      if (obj instanceof AmendmentCodesModel) {
        AmendmentCodesModel amendmentCodesModel = (AmendmentCodesModel) obj;
        AmendmentCodesLogic amendmentCodesLogic = new AmendmentCodesLogic();
        MdtCnfgAmendmentCodesEntity mdtCnfgAmendmentCodesEntity = new MdtCnfgAmendmentCodesEntity();

        mdtCnfgAmendmentCodesEntity = amendmentCodesLogic.addAmendmentCodes(amendmentCodesModel);
        genericDAO.saveOrUpdate(mdtCnfgAmendmentCodesEntity);

        return true;
      } else {
        log.debug("Unable to convert type to AmendmentCodes.");

        return false;
      }
    } catch (Exception e) {
      log.error("Error on createAmendmentCodes:" + e.getMessage());
      e.printStackTrace();

      return false;
    }

  }


  @Override
  public List<?> viewAmendmentCodes() {
    List<AmendmentCodesModel> allAmendmentCodesList = new ArrayList<AmendmentCodesModel>();

    List<MdtCnfgAmendmentCodesEntity> allMdtCnfgAmendmentCodesEntityList =
        new ArrayList<MdtCnfgAmendmentCodesEntity>();

    allMdtCnfgAmendmentCodesEntityList = genericDAO.findAll(MdtCnfgAmendmentCodesEntity.class);
    if (allMdtCnfgAmendmentCodesEntityList.size() > 0) {
      AmendmentCodesLogic amendmentCodesLogic = new AmendmentCodesLogic();
      allAmendmentCodesList =
          amendmentCodesLogic.retreiveAllAmendmentCodes(allMdtCnfgAmendmentCodesEntityList);
    }

    return allAmendmentCodesList;
  }


  @Override
  public List<?> viewAmendmentCodesByCriteria(String amendmentCodes) {
    List<AmendmentCodesModel> allAmendmentCodesModel = new ArrayList<AmendmentCodesModel>();

    try {
      List<MdtCnfgAmendmentCodesEntity> allMdtCnfgAmendmentCodesEntity =
          genericDAO.findAllByNamedQuery("MdtCnfgAmendmentCodesEntity.findByAmendmentCodesLIKE",
              "amendmentCodes", amendmentCodes + "%");
      log.debug("allMdtCnfgAmendmentCodesEntity: " + allMdtCnfgAmendmentCodesEntity);

      if (allMdtCnfgAmendmentCodesEntity.size() > 0) {
        AmendmentCodesLogic amendmentCodesLogic = new AmendmentCodesLogic();

        for (MdtCnfgAmendmentCodesEntity localEntity : allMdtCnfgAmendmentCodesEntity) {
          AmendmentCodesModel amendmentCodesModel = new AmendmentCodesModel();
          amendmentCodesModel = amendmentCodesLogic.retreiveAmendmentCodes(localEntity);
          allAmendmentCodesModel.add(amendmentCodesModel);
        }
      }
    } catch (ObjectNotFoundException onfe) {
      log.error("No object Exists on DB");
    } catch (Exception e) {
      log.error("Error retrieveAccountTypeByCriteria: " + e.getMessage());
      e.printStackTrace();
    }

    return allAmendmentCodesModel;
  }

  @Override
  public List<?> retrieveAcitiveSot(String destMemberId, String serviceName, String eotOutInd) {
    List<MdtAcOpsSotEotCtrlEntity> opsSotEotCtrlList = new ArrayList<MdtAcOpsSotEotCtrlEntity>();
    try {
      HashMap<String, Object> parameters = new HashMap<String, Object>();
      parameters.put("mdtAcOpsSotEotCtrlPK.instId", destMemberId);
      parameters.put("mdtAcOpsSotEotCtrlPK.serviceId", serviceName);
      parameters.put("sotOut", eotOutInd);
      log.debug("eot parameters:--------->" + parameters.toString());

      opsSotEotCtrlList = (List<MdtAcOpsSotEotCtrlEntity>) genericDAO.findAllByCriteria(
          MdtAcOpsSotEotCtrlEntity.class, parameters);
      log.debug("opsSotEotCtrlList after findAllByCriteria:------>" + opsSotEotCtrlList);
    } catch (ObjectNotFoundException onfe) {
      log.error("No Object Exists on DB");
    } catch (Exception e) {
      log.error("Error on opsSotEotCtrlListDetails: " + e.getMessage());
      e.printStackTrace();
    }

    return opsSotEotCtrlList;

  }

  @Override
  public boolean checkIamSession(String sessionKey) {
    if (sessionKey != null && sessionKey.isEmpty()) {
      List<IamSessionEntity> iamSessionEntityList =
          (List<IamSessionEntity>) genericDAO.findAllByNamedQuery(
              "IamSessionEntity.findBySessionKey", "sessionKey", sessionKey);
      if (iamSessionEntityList != null && iamSessionEntityList.size() > 0) {
        return true;
      } else {
        return false;
      }
    }

    return false;
  }

  @Override
  public boolean createSession(Object obj) {
    try {
      if (obj instanceof IamSessionModel) {
        IamSessionModel iamSessionModel = (IamSessionModel) obj;
        IamSessionLogic iamSessionLogic = new IamSessionLogic();
        IamSessionEntity iamSessionEntity = new IamSessionEntity();

        iamSessionEntity = iamSessionLogic.addIamSession(iamSessionModel);
        genericDAO.saveOrUpdate(iamSessionEntity);

        return true;
      } else {
        log.debug("Unable to convert type to IamSessionModel.");
        return false;
      }
    } catch (Exception e) {
      log.error("Error on createSession:" + e.getMessage());
      e.printStackTrace();
      return false;
    }
  }

  @Override
  public List<?> retrieveMndtRespPerBank(String memberId, String processStatus) {
    List<MandateResponseOutstandingPerBankEntityModel> mndtRespOutstandingEntityList =
        new ArrayList<MandateResponseOutstandingPerBankEntityModel>();
    MandateResponseOutstandingPerBankModel mndtRespOutstandingPerBankModel = null;
    List<MandateResponseOutstandingPerBankModel> mndtRespOutstandingModelList =
        new ArrayList<MandateResponseOutstandingPerBankModel>();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");

    CasSysctrlSysParamEntity casSysctrlSysParamEntity =
        (CasSysctrlSysParamEntity) retrieveActiveSysParameter();
    Date currentDate;

    if (casSysctrlSysParamEntity != null && casSysctrlSysParamEntity.getProcessDate() != null) {
      currentDate = casSysctrlSysParamEntity.getProcessDate();
    } else {
      currentDate = new Date();
    }

    Date date = null;
    //Minus two Days

    try {
      date = DateUtil.addNoOfDays(currentDate);
      log.debug("finalCurrentDate**************" + currentDate);
    } catch (ParseException e1) {
      e1.printStackTrace();
    }

    String stringDate2 = DateUtil.convertDateToString(date, "ddMMYYYY");
    //		log.info("stringDate2*******:"+stringDate2);

    //Minus One day
    try {
      date = DateUtil.addOneDay(currentDate);
      log.debug("finalCurrentDate**************" + currentDate);
    } catch (ParseException e1) {
      e1.printStackTrace();
    }

    String stringDate1 = DateUtil.convertDateToString(date, "ddMMYYYY");
    //		log.info("stringDate1*******:"+stringDate1);

    StringBuffer sb = new StringBuffer();

		/*		sb.append("SELECT b.MEMBER_NAME AS crdMemberName,SUBSTR(a.MSG_ID,13,6) AS
		memberNo,a.EXTRACT_MSG_ID AS fileName,'TT2' AS transType,TO_DATE('"+currentDate+"',
		'YYYY-MM-DD') - TRUNC(a.CREATED_DATE) AS nrDaysOutstanding, a.MANDATE_REQ_TRAN_ID as
		mandateReqTransId,a.SERVICE_ID as serviceId ");
		sb.append("FROM MANOWNER.MDT_AC_OPS_MNDT_MSG a ");
		sb.append("INNER JOIN MANOWNER.SYS_CIS_BANK b ON SUBSTR(a.MSG_ID,13,6) = b.MEMBER_NO ");
		sb.append("WHERE a.PROCESS_STATUS IN ('4','9') AND SUBSTR(a.EXTRACT_MSG_ID,13,6)
		='"+memberId+"' AND  TRUNC(a.CREATED_DATE) = TO_DATE('"+currentDate+"','YYYY-MM-DD') -1
		 ");
		sb.append("UNION ");
		sb.append("SELECT b.MEMBER_NAME AS crdMemberName,SUBSTR(a.MSG_ID,13,6) AS memberNo,a
		.EXTRACT_MSG_ID AS fileName,'TT2' AS transType,TRUNC(current_date) - TRUNC(CREATED_DATE)
		AS nrDaysOutstanding, a.MANDATE_REQ_TRAN_ID as mandateReqTransId,a.SERVICE_ID as serviceId
		 ");
		sb.append("FROM MANOWNER.MDT_AC_OPS_MNDT_MSG a ");
		sb.append("INNER JOIN MANOWNER.SYS_CIS_BANK b ON SUBSTR(a.MSG_ID,13,6) = b.MEMBER_NO ");
		sb.append("WHERE a.PROCESS_STATUS IN('4','9') AND SUBSTR(a.EXTRACT_MSG_ID,13,6)
		='"+memberId+"' AND  TRUNC(a.CREATED_DATE) = TO_DATE('"+currentDate+"','YYYY-MM-DD') -2
		 ");
		sb.append("UNION ");
		sb.append("SELECT crdMemberName,memberNo,fileName,transType,nrDaysOutstanding,
		mandateReqTransId,serviceId FROM ");
		sb.append("(SELECT b.MEMBER_NAME as crdMemberName,a.INSTRUCTINGAGENTAMS as memberNo,a
		.ORIGINALMSGIDAMS as fileName,'TT1' as transType, ");
		sb.append("TRUNC(current_date) - TO_DATE(SUBSTR(TRANSDATETIME, 1, 8), 'YYYYMMDD') as
		nrDaysOutstanding,a.TRANSACTIONIDENTIFIERAMS as mandateReqTransId,SUBSTR(a
		.ORIGINALMSGIDAMS,5,5) as serviceId ");
		sb.append("FROM MANOWNER.JNL_ACQ a ");
		sb.append("INNER JOIN MANOWNER.SYS_CIS_BANK b ON a.INSTRUCTINGAGENTAMS = b.MEMBER_NO ");
		sb.append("WHERE EXTRACTVALUE(XMLTYPE(CONCAT(CONCAT('<A>',a.MANDATEBLOCKAMS),'</A>')),
		'/A/Tp/LclInstrm/Prtry') = '0227' AND a.INSTRUCTEDAGENTAMS='"+memberId+"' AND ");
		sb.append("a.REASONCODEAMS = '900000' AND a.msgtypeams = 'pain.009' AND a.RESULTCODE = '0'
		 AND a.serviceidams = 'STMAN' AND ");
		sb.append("TO_DATE(SUBSTR(a.TRANSDATETIME, 1, 8), 'YYYYMMDD') = TRUNC(current_date) -1  ");
		sb.append("minus ");
		sb.append("SELECT c.MEMBER_NAME as crdMemberName,b.INSTRUCTINGAGENTAMS as memberNo,b
		.ORIGINALMSGIDAMS as fileName,'TT1' as transType, ");
		sb.append("TRUNC(current_date) - TO_DATE(SUBSTR(b.TRANSDATETIME, 1, 8), 'YYYYMMDD') as
		nrDaysOutstanding,a.TRANSACTIONIDENTIFIERAMS as mandateReqTransId,SUBSTR(a
		.ORIGINALMSGIDAMS,5,5) as serviceId ");
		sb.append("FROM MANOWNER.JNL_ACQ a ");
		sb.append("INNER JOIN MANOWNER.JNL_ACQ b ON a.TRANSACTIONIDENTIFIERAMS = b
		.TRANSACTIONIDENTIFIERAMS ");
		sb.append("INNER JOIN MANOWNER.SYS_CIS_BANK c ON  a.INSTRUCTINGAGENTAMS = c.MEMBER_NO ");
		sb.append("WHERE a.SERVICEIDAMS = 'ST012' AND a.MTI = 5506 AND a.RESULTCODE = 0 AND b.MTI
		= 5501 AND b.RESULTCODE = 0 AND a.REASONCODEAMS = '900000' AND ");
		sb.append("b.REASONCODEAMS = '900000' AND b.INSTRUCTEDAGENTAMS='"+memberId+"' AND TO_DATE
		(SUBSTR(a.TRANSDATETIME, 1, 8), 'YYYYMMDD') = TRUNC(current_date)-1) ");
		sb.append("UNION ");
		sb.append("SELECT crdMemberName,memberNo,fileName,transType,nrDaysOutstanding,
		mandateReqTransId,serviceId FROM ");
		sb.append("(SELECT b.MEMBER_NAME as crdMemberName,a.INSTRUCTINGAGENTAMS as memberNo,a
		.ORIGINALMSGIDAMS as fileName,'TT1' as transType, ");
		sb.append("TRUNC(current_date) - TO_DATE(SUBSTR(a.TRANSDATETIME, 1, 8), 'YYYYMMDD')as
		nrDaysOutstanding,a.TRANSACTIONIDENTIFIERAMS as mandateReqTransId,SUBSTR(a
		.ORIGINALMSGIDAMS,5,5) as serviceId ");
		sb.append("FROM MANOWNER.JNL_ACQ a ");
		sb.append("INNER JOIN MANOWNER.SYS_CIS_BANK b ON  a.INSTRUCTINGAGENTAMS = b.MEMBER_NO ");
		sb.append("WHERE SUBSTR(REPLACE(REPLACE(REPLACE(REPLACE(UNDRLYGAMDMNTDTLSBLOCKAMS, chr(10)
		), chr(13)), chr(9)), ' '), INSTR(REPLACE(REPLACE(REPLACE(REPLACE
		(UNDRLYGAMDMNTDTLSBLOCKAMS, chr(10)), chr(13)), chr(9)), ' '), '<LclInstrm><Prtry>',1,1)
		+18,4) = '0227' ");
		sb.append("AND a.REASONCODEAMS = '900000' AND a.msgtypeams = 'pain.010' AND a.RESULTCODE =
		 '0' AND a.serviceidams = 'STMAN' AND a.INSTRUCTEDAGENTAMS='"+memberId+"' AND ");
		sb.append(" TO_DATE(SUBSTR(a.TRANSDATETIME, 1, 8), 'YYYYMMDD') = TRUNC(sysdate) -1  ");
		sb.append("minus ");
		sb.append("SELECT c.MEMBER_NAME as crdMemberName,b.INSTRUCTINGAGENTAMS as memberNo,b
		.ORIGINALMSGIDAMS as fileName,'TT1' as transType, ");
		sb.append("TRUNC(current_date) - TO_DATE(SUBSTR(b.TRANSDATETIME, 1, 8), 'YYYYMMDD') as
		nrDaysOutstanding,b.TRANSACTIONIDENTIFIERAMS as mandateReqTransId,SUBSTR(b
		.ORIGINALMSGIDAMS,5,5) as serviceId ");
		sb.append("FROM  MANOWNER.JNL_ACQ  a ");
		sb.append("INNER JOIN MANOWNER.JNL_ACQ b ON a.TRANSACTIONIDENTIFIERAMS = b
		.TRANSACTIONIDENTIFIERAMS ");
		sb.append("INNER JOIN MANOWNER.SYS_CIS_BANK c ON  b.INSTRUCTINGAGENTAMS = c.MEMBER_NO ");
		sb.append("WHERE a.SERVICEIDAMS = 'ST012' AND a.MTI = 5506 AND a.RESULTCODE = 0 ");
		sb.append("AND b.MTI = 5503 AND b.RESULTCODE = 0 AND a.REASONCODEAMS = '900000' AND b
		.REASONCODEAMS = '900000' AND  b.INSTRUCTEDAGENTAMS='"+memberId+"' ");
		sb.append("AND TO_DATE(SUBSTR(a.TRANSDATETIME, 1, 8), 'YYYYMMDD')  = TRUNC(current_date)-
		1)  ");
		sb.append("UNION ");
		sb.append("SELECT crdMemberName,memberNo,fileName,transType,nrDaysOutstanding,
		mandateReqTransId,serviceId FROM ");
		sb.append("(SELECT b.MEMBER_NAME as crdMemberName,a.INSTRUCTINGAGENTAMS as memberNo,a
		.ORIGINALMSGIDAMS as fileName,'TT1' as transType, ");
		sb.append("TRUNC(current_date) - TO_DATE(SUBSTR(a.TRANSDATETIME, 1, 8), 'YYYYMMDD')as
		nrDaysOutstanding,a.TRANSACTIONIDENTIFIERAMS as mandateReqTransId,SUBSTR(a
		.ORIGINALMSGIDAMS,5,5) as serviceId ");
		sb.append("FROM MANOWNER.JNL_ACQ a ");
		sb.append("INNER JOIN MANOWNER.SYS_CIS_BANK b ON  a.INSTRUCTINGAGENTAMS = b.MEMBER_NO ");
		sb.append("WHERE SUBSTR(REPLACE(REPLACE(REPLACE(REPLACE(UNDRLYGAMDMNTDTLSBLOCKAMS, chr(10)
		), chr(13)), chr(9)), ' '), INSTR(REPLACE(REPLACE(REPLACE(REPLACE
		(UNDRLYGAMDMNTDTLSBLOCKAMS, chr(10)), chr(13)), chr(9)), ' '), '<LclInstrm><Prtry>',1,1)
		+18,4) = '0227' ");
		sb.append("AND a.REASONCODEAMS = '900000' AND a.msgtypeams = 'pain.011' AND a.RESULTCODE =
		 '0' AND a.serviceidams IN('MANIR', 'STMDF') AND a.INSTRUCTEDAGENTAMS='"+memberId+"' AND
		  ");
		sb.append("TO_DATE(SUBSTR(a.TRANSDATETIME, 1, 8), 'YYYYMMDD') =TRUNC(current_date) -1  ");
		sb.append("minus ");
		sb.append("SELECT c.MEMBER_NAME as crdMemberName,b.INSTRUCTINGAGENTAMS as memberNo,b
		.ORIGINALMSGIDAMS as fileName,'TT1' as transType, ");
		sb.append("TRUNC(current_date) - TO_DATE(SUBSTR(b.TRANSDATETIME, 1, 8), 'YYYYMMDD') as
		nrDaysOutstanding,b.TRANSACTIONIDENTIFIERAMS as mandateReqTransId,SUBSTR(b
		.ORIGINALMSGIDAMS,5,5)  as serviceId  ");
		sb.append("FROM  MANOWNER.JNL_ACQ  a " );
		sb.append("INNER JOIN MANOWNER.JNL_ACQ b ON a.TRANSACTIONIDENTIFIERAMS = b
		.TRANSACTIONIDENTIFIERAMS ");
		sb.append("INNER JOIN MANOWNER.SYS_CIS_BANK c ON  b.INSTRUCTINGAGENTAMS = c.MEMBER_NO ");
		sb.append("WHERE a.SERVICEIDAMS = 'MANIR' AND a.MTI = 5505 AND a.RESULTCODE = 0 ");
		sb.append("AND b.MTI = 5505 AND b.RESULTCODE = 0 AND a.REASONCODEAMS = '900000' AND b
		.REASONCODEAMS = '900000' AND  b.INSTRUCTEDAGENTAMS='"+memberId+"' ");
		sb.append("AND TO_DATE(SUBSTR(a.TRANSDATETIME, 1, 8), 'YYYYMMDD')  = TRUNC(current_date)-
		1) ") ;
		 */

    sb.append(
        "SELECT b.MEMBER_NAME AS crdMemberName,SUBSTR(a.MSG_ID,13,6) AS memberNo,a.EXTRACT_MSG_ID" +
            " AS fileName,'TT2' AS transType,TO_DATE('" +
            currentDate +
            "','YYYY-MM-DD') - TRUNC(a.CREATED_DATE) AS nrDaysOutstanding, a.MANDATE_REQ_TRAN_ID " +
            "as mandateReqTransId,a.SERVICE_ID as serviceId ");
    sb.append("FROM MANOWNER.MDT_AC_OPS_MNDT_MSG a ");
    sb.append("INNER JOIN MANOWNER.SYS_CIS_BANK b ON SUBSTR(a.MSG_ID,13,6) = b.MEMBER_NO ");
    sb.append("WHERE a.PROCESS_STATUS IN ('4','9') AND SUBSTR(a.EXTRACT_MSG_ID,13,6)='" + memberId +
        "' AND  TRUNC(a.CREATED_DATE) = TO_DATE('" + currentDate + "','YYYY-MM-DD') -1  ");
    sb.append("UNION ");
    sb.append(
        "SELECT b.MEMBER_NAME AS crdMemberName,SUBSTR(a.MSG_ID,13,6) AS memberNo,a.EXTRACT_MSG_ID" +
            " AS fileName,'TT2' AS transType,TRUNC(current_date) - TRUNC(CREATED_DATE) AS " +
            "nrDaysOutstanding, a.MANDATE_REQ_TRAN_ID as mandateReqTransId,a.SERVICE_ID as " +
            "serviceId ");
    sb.append("FROM MANOWNER.MDT_AC_OPS_MNDT_MSG a ");
    sb.append("INNER JOIN MANOWNER.SYS_CIS_BANK b ON SUBSTR(a.MSG_ID,13,6) = b.MEMBER_NO ");
    sb.append("WHERE a.PROCESS_STATUS IN('4','9') AND SUBSTR(a.EXTRACT_MSG_ID,13,6)='" + memberId +
        "' AND  TRUNC(a.CREATED_DATE) = TO_DATE('" + currentDate + "','YYYY-MM-DD') -2   ");
    sb.append("UNION ");
    sb.append(
        "SELECT c.MEMBER_NAME AS crdMemberName,a.INSTRUCTINGAGENTAMS AS memberNo,a" +
            ".ORIGINALMSGIDAMS AS fileName,'TT1' AS transType,TRUNC(current_date) - TO_DATE" +
            "(SUBSTR(a" +
            ".TRANSDATETIME, 1, 8), 'YYYYMMDD') AS nrDaysOutstanding, ");
    sb.append(
        "a.TRANSACTIONIDENTIFIERAMS AS mandateReqTransId,SUBSTR(a.ORIGINALMSGIDAMS,5,5)  AS " +
            "serviceId ");
    sb.append("FROM MANOWNER.JNL_ACQ a ");
    sb.append("LEFT OUTER JOIN MANOWNER.JNL_ACQ b ");
    sb.append("ON a.TRANSACTIONIDENTIFIERAMS = b.TRANSACTIONIDENTIFIERAMS ");
    sb.append("and a.MSGTYPEAMS = 'pain.009' and b.msgtypeams = 'pain.012' ");
    sb.append("INNER JOIN MANOWNER.SYS_CIS_BANK c ");
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
    sb.append("FROM MANOWNER.JNL_ACQ a ");
    sb.append("LEFT OUTER JOIN MANOWNER.JNL_ACQ b ");
    sb.append("ON a.TRANSACTIONIDENTIFIERAMS = b.TRANSACTIONIDENTIFIERAMS ");
    sb.append("AND a.MSGTYPEAMS = 'pain.010' AND b.msgtypeams = 'pain.012' ");
    sb.append("INNER JOIN MANOWNER.SYS_CIS_BANK c ");
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
    sb.append("FROM MANOWNER.JNL_ACQ a ");
    sb.append("LEFT OUTER JOIN MANOWNER.JNL_ACQ b ");
    sb.append("ON a.TRANSACTIONIDENTIFIERAMS = b.TRANSACTIONIDENTIFIERAMS ");
    sb.append("AND a.MSGTYPEAMS = 'pain.011' AND b.msgtypeams = 'pain.012' ");
    sb.append("INNER JOIN MANOWNER.SYS_CIS_BANK c ");
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
  public List<?> retrieveAmendmendReasonCode() {

    List<AmendmentCodesModel> amendmentCodesList = new ArrayList<AmendmentCodesModel>();
    List<MdtCnfgAmendmentCodesEntity> amendmentCodesEntityList =
        new ArrayList<MdtCnfgAmendmentCodesEntity>();
    amendmentCodesEntityList = genericDAO.findAll(MdtCnfgAmendmentCodesEntity.class);

    if (amendmentCodesEntityList.size() > 0) {
      AmendmentCodesLogic amendmentCodesLogic = new AmendmentCodesLogic();
      amendmentCodesList = amendmentCodesLogic.retreiveAllAmendmentCodes(amendmentCodesEntityList);

      log.debug("Amendment model from Bean --> " + amendmentCodesList.toString());
    }

    return amendmentCodesList;
  }

  @Override
  public List<?> retrieveActiveCreditorBank() {
    List<CreditorBankModel> creditorBankModelList = new ArrayList<CreditorBankModel>();
    List<CreditorBankEntityModel> creditorBankList = new ArrayList<CreditorBankEntityModel>();

    try {

      StringBuffer sb = new StringBuffer();

			/*  select a.member_name, b.inst_id
			  from sys_cis_bank a
			  left join mdt_ops_cust_param b  on b.inst_id = a.member_no*/

      sb.append("SELECT MEMBER_NAME as memberName ,MEMBER_NO as memberNo ");
      sb.append("FROM MANOWNER.SYS_CIS_BANK a ");
      sb.append("WHERE AC_CREDITOR = 'Y' ");
      sb.append("ORDER BY MEMBER_NAME ASC ");

      String sqlQuery = sb.toString();
      log.debug("The Sql query is  ***************" + sqlQuery);
      List<String> scalarList = new ArrayList<String>();

      scalarList.add("memberName");
      scalarList.add("memberNo");

      try {

        creditorBankList =
            genericDAO.findBySql(sqlQuery, scalarList, CreditorBankEntityModel.class);

        if (creditorBankList != null && creditorBankList.size() > 0) {
          for (CreditorBankEntityModel localEntityModel : creditorBankList) {
            CreditorBankModel creditorBankModel = new CreditorBankModel();
            creditorBankModel =
                new ServiceTranslator().translateCreditorBankEntityModelToCommonsModel(
                    localEntityModel);
            creditorBankModelList.add(creditorBankModel);
          }
        }
      } catch (ObjectNotFoundException onfe) {
        log.error("No Object Exists on DB");
      }

    } catch (Exception e) {
      log.error("Error on retrieveActiveCreditorBank :" + e.getMessage());
      e.printStackTrace();
    }
    return creditorBankModelList;
  }

  @Override
  public List<?> retrieveMndtBillingTransactions(String txnType) {
    List<MandateDailyTransModel> mndtDailyTransList = new ArrayList<MandateDailyTransModel>();
    List<MandateDailyTransEntityModel> mndtDailyTransEntityList =
        new ArrayList<MandateDailyTransEntityModel>();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    Date currentDate = new Date();
    //		log.info("currentDate---->"+currentDate);

    try {

      String stringDate = sdf.format(currentDate);
      //			log.debug("stringDate---->"+stringDate);


      //Retrieve Billable Transactions
      List<MdtAcOpsDailyBillingEntity> dailyBillingList =
          new ArrayList<MdtAcOpsDailyBillingEntity>();
      dailyBillingList =
          genericDAO.findAllByNamedQuery("MdtAcOpsDailyBillingEntity.findByTxnType", "txnType",
              txnType);

      if (dailyBillingList != null && dailyBillingList.size() > 0) {

        for (MdtAcOpsDailyBillingEntity localEntity : dailyBillingList) {
          String subService = localEntity.getSubService();
          //					log.info("Billing SubService ==> "+subService);
          //					log.debug("Billing txn status ==> "+localEntity.getTxnStatus());
          //					log.info("Txn Id ==> "+localEntity.getTxnId());

          MandateDailyTransModel localModel = new MandateDailyTransModel();
          localModel = new AdminTranslator().translateDailyBillingEntityToModel(localEntity);
          //					log.debug("localModel ==> "+localModel);
          mndtDailyTransList.add(localModel);
        }

        //2017-09-05 SalehaR - This has been removed as all data is stored on the
        // MDT_AC_OPS_DAILY_BILLING table
        //					if(subService.equalsIgnoreCase(manin) || subService.equalsIgnoreCase
        //					(manam) || subService.equalsIgnoreCase(mancn))
        //					{
        //						//Retrieve Mandate Info
        //						MdtAcOpsMndtMsgEntity mdtAcOpsMndtMsgEntity;
        //						MdtAcOpsSupplDataEntity supplDataEntity = null;
        //
        //						HashMap<String, Object> parameters = new HashMap<String, Object>();
        //						parameters.put("mdtAcOpsMndtMsgPK.mandateReqTranId", localEntity
        //						.getTxnId());
        //						parameters.put("serviceId", subService);
        //
        //						mdtAcOpsMndtMsgEntity = (MdtAcOpsMndtMsgEntity) genericDAO
        //						.findByCriteria(MdtAcOpsMndtMsgEntity.class, parameters);
        //						log.info("bill mdtAcOpsMndtMsgEntity ==> "+mdtAcOpsMndtMsgEntity);
        //
        //
        //						2017-08-31 SalehaR - Read MNDT_REF_NO from Daily Billing Table
        //						if(mdtAcOpsMndtMsgEntity != null)
        //						{
        //							//Retrieve Supplementary Data
        //
        //							HashMap<String, Object> parameters1 = new HashMap<String,
        //							Object>();
        //							parameters1.put("mdtAcOpsSupplDataPK.mandateReqTranId",
        //							mdtAcOpsMndtMsgEntity.getMdtAcOpsMndtMsgPK()
        //							.getMandateReqTranId());
        //							parameters1.put("mdtAcOpsSupplDataPK.msgId",
        //							mdtAcOpsMndtMsgEntity.getMdtAcOpsMndtMsgPK().getMsgId());
        //
        //							supplDataEntity = (MdtAcOpsSupplDataEntity) genericDAO
        //							.findByCriteria(MdtAcOpsSupplDataEntity.class, parameters1);
        //							log.debug("bill mdtAcOpsMndtMsgEntity ==>
        //							"+mdtAcOpsMndtMsgEntity);
        //
        //							if(supplDataEntity == null)
        //								supplDataEntity = new MdtAcOpsSupplDataEntity();
        //						}
        //
        //						String extMsgId = null;
        //						if(mdtAcOpsMndtMsgEntity != null)
        //						{
        //							extMsgId = mdtAcOpsMndtMsgEntity.getExtractMsgId();
        //						}
        //
        //						MandateDailyTransModel localModel = new MandateDailyTransModel();
        //						localModel = new AdminTranslator()
        //						.translateDailyBillingEntityToModel(localEntity);
        //						log.info("localModel ==> "+localModel);
        //						mndtDailyTransList .add(localModel);
        //
        //					}
        //					else
        //					{
        //						if(subService.equalsIgnoreCase(manrt))
        //						{
        //							//Retrieve Manrt Info
        //							MdtAcOpsMdteRespEntity respEntity = null;
        //							MdtAcOpsMdteRespMsgEntity responseMsgEntity = new
        //							MdtAcOpsMdteRespMsgEntity();
        //
        //							respEntity = (MdtAcOpsMdteRespEntity) genericDAO
        //							.findByNamedQuery("MdtAcOpsMdteRespEntity.findByMdtInfReqId",
        //							"mdtInfReqId",localEntity.getTxnId());
        //							log.info("respEntity ==> "+ respEntity);
        //							if(respEntity != null)
        //							{
        //								if(respEntity.getAccptInd().equalsIgnoreCase("true"))
        //								{
        //									//Retrieve Mandate Information
        //									HashMap<String, Object> manrtParams = new
        //									HashMap<String, Object>();
        //									manrtParams.put("mdtAcOpsMdteRespMsgPK.msgId",
        //									respEntity.getMdteMsgId());
        //									manrtParams.put("mdtAcOpsMdteRespMsgPK.mdtInfReqId",
        //									respEntity.getMdtInfReqId());
        //
        //									responseMsgEntity = (MdtAcOpsMdteRespMsgEntity)
        //									genericDAO.findByCriteria(MdtAcOpsMdteRespMsgEntity
        //									.class, manrtParams);
        //									log.debug("bill responseMsgEntity ==>
        //									"+responseMsgEntity);
        //
        //									if(responseMsgEntity == null)
        //										responseMsgEntity = new
        //										MdtAcOpsMdteRespMsgEntity();
        //								}
        //							}
        //
        //							//Translate to billingModel
        //							MandateDailyTransModel localModel = new
        //							MandateDailyTransModel();
        //							localModel = new AdminTranslator()
        //							.translateDailyBillingEntityToModel(localEntity);
        //							log.info("localModel ==> "+localModel);
        //							mndtDailyTransList .add(localModel);
        //						}
        //						else
        //						{
        //							if(subService.equalsIgnoreCase(srinp))
        //							{
        //								//Retrieve SRINP Information
        //								MdtAcOpsConfDetailsEntity confDtlsEntity = null;
        //
        //								HashMap<String, Object> srinpParams = new HashMap<String,
        //								Object>();
        //								srinpParams.put("txnId", localEntity.getTxnId());
        //								srinpParams.put("extractService","SROUT");
        //
        //								confDtlsEntity = (MdtAcOpsConfDetailsEntity) genericDAO
        //								.findByCriteria(MdtAcOpsConfDetailsEntity.class,
        //								srinpParams);
        //
        //								if(confDtlsEntity == null)
        //									confDtlsEntity = new MdtAcOpsConfDetailsEntity();
        //
        //								//Translate to billingModel
        //								MandateDailyTransModel localModel = new
        //								MandateDailyTransModel();
        //								localModel = new AdminTranslator()
        //								.translateDailyBillingEntityToModel(localEntity,
        //								confDtlsEntity.getExtractMsgId());
        //								log.debug("localModel ==> "+localModel);
        //								mndtDailyTransList .add(localModel);
        //							}
        //						}
        //					}
        //				}
      }
    } catch (ObjectNotFoundException onfe) {
      log.error("No Object Exists on DB");
    } catch (Exception e) {
      log.error("Error on retrieveMndtDailyTransPerBank :" + e.getMessage());
      e.printStackTrace();
    }

    return mndtDailyTransList;
  }


  @Override
  public List<?> retrieveMndtDailyTransPerCreditor(String instId, String txnType) {
    List<MandateDailyTransModel> mndtDailyTransList = new ArrayList<MandateDailyTransModel>();
    List<MandateDailyTransEntityModel> mndtDailyTransEntityList =
        new ArrayList<MandateDailyTransEntityModel>();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    Date currentDate = new Date();
    log.debug("currentDate---->" + currentDate);

    String stringDate = sdf.format(currentDate);
    log.debug("stringDate---->" + stringDate);


    try {
      //Retrieve Billable Transactions
      List<MdtAcOpsDailyBillingEntity> dailyBillingList =
          new ArrayList<MdtAcOpsDailyBillingEntity>();

      HashMap<String, Object> dailyBillParams = new HashMap<String, Object>();
      dailyBillParams.put("txnType", txnType);
      dailyBillParams.put("creditorBank", instId);

      dailyBillingList = (List<MdtAcOpsDailyBillingEntity>) genericDAO.findAllByCriteria(
          MdtAcOpsDailyBillingEntity.class, dailyBillParams);

      if (dailyBillingList != null && dailyBillingList.size() > 0) {
        for (MdtAcOpsDailyBillingEntity localEntity : dailyBillingList) {
          String subService = localEntity.getSubService();
          log.debug("subService ==> " + subService);

          if (subService.equalsIgnoreCase(manin) || subService.equalsIgnoreCase(manam) ||
              subService.equalsIgnoreCase(mancn) || subService.equalsIgnoreCase(manrt)) {
            MandateDailyTransModel localModel = new MandateDailyTransModel();
            localModel = new AdminTranslator().translateDailyBillingEntityToModel(localEntity);
            log.debug("localModel ==> " + localModel);
            mndtDailyTransList.add(localModel);
          }
        }

        //2017-09-05 SalehaR - This has been removed as all data is stored on the
        // MDT_AC_OPS_DAILY_BILLING table
        //						if(subService.equalsIgnoreCase(manin) || subService
        //						.equalsIgnoreCase(manam) || subService.equalsIgnoreCase(mancn))
        //						{
        //							//Retrieve Mandate Info
        //							MdtAcOpsMndtMsgEntity mdtAcOpsMndtMsgEntity;
        //							MdtAcOpsSupplDataEntity supplDataEntity = null;
        //
        //							HashMap<String, Object> parameters = new HashMap<String,
        //							Object>();
        //
        //							parameters.put("mdtAcOpsMndtMsgPK.mandateReqTranId",
        //							localEntity.getTxnId());
        //							parameters.put("serviceId", subService);
        //
        //							mdtAcOpsMndtMsgEntity = (MdtAcOpsMndtMsgEntity) genericDAO
        //							.findByCriteria(MdtAcOpsMndtMsgEntity.class, parameters);
        //							log.debug("bill mdtAcOpsMndtMsgEntity ==>
        //							"+mdtAcOpsMndtMsgEntity);
        ////							2017-08-31 SalehaR - Read MNDT_REF_NO from Daily Billing
        // Table
        ////							if(mdtAcOpsMndtMsgEntity != null)
        ////							{
        ////								//Retrieve Supplementary Data
        ////
        ////								HashMap<String, Object> parameters1 = new
        // HashMap<String, Object>();
        ////								parameters1.put("mdtAcOpsSupplDataPK
        // .mandateReqTranId", mdtAcOpsMndtMsgEntity.getMdtAcOpsMndtMsgPK().getMandateReqTranId());
        ////								parameters1.put("mdtAcOpsSupplDataPK.msgId",
        // mdtAcOpsMndtMsgEntity.getMdtAcOpsMndtMsgPK().getMsgId());
        ////
        ////								supplDataEntity = (MdtAcOpsSupplDataEntity) genericDAO
        // .findByCriteria(MdtAcOpsSupplDataEntity.class, parameters1);
        ////								log.debug("bill mdtAcOpsMndtMsgEntity ==>
        // "+mdtAcOpsMndtMsgEntity);
        ////
        ////								if(supplDataEntity == null)
        ////									supplDataEntity = new MdtAcOpsSupplDataEntity();
        ////							}
        //
        //							MandateDailyTransModel localModel = new
        //							MandateDailyTransModel();
        //							String extMsgId = null;
        //
        //							if(mdtAcOpsMndtMsgEntity != null)
        //							{
        //								extMsgId = mdtAcOpsMndtMsgEntity.getExtractMsgId();
        //							}
        //
        //							localModel = new AdminTranslator()
        //							.translateDailyBillingEntityToModel(localEntity,extMsgId);
        //							log.debug("localModel ==> "+localModel);
        //							mndtDailyTransList .add(localModel);
        //
        //						}
        //						else
        //						{
        //							if(subService.equalsIgnoreCase(manrt))
        //							{
        //								//Retrieve Manrt Info
        //								MdtAcOpsMdteRespEntity respEntity = null;
        //								MdtAcOpsMdteRespMsgEntity responseMsgEntity = new
        //								MdtAcOpsMdteRespMsgEntity();
        //
        //								respEntity = (MdtAcOpsMdteRespEntity) genericDAO
        //								.findByNamedQuery("MdtAcOpsMdteRespEntity
        //								.findByMdtInfReqId", "mdtInfReqId",localEntity.getTxnId());
        //								log.info("respEntity ==> "+ respEntity);
        //								if(respEntity != null)
        //								{
        //									if(respEntity.getAccptInd().equalsIgnoreCase("true"))
        //									{
        //										//Retrieve Mandate Information
        //										HashMap<String, Object> manrtParams = new
        //										HashMap<String, Object>();
        //										manrtParams.put("mdtAcOpsMdteRespMsgPK.msgId",
        //										respEntity.getMdteMsgId());
        //										manrtParams.put("mdtAcOpsMdteRespMsgPK
        //										.mdtInfReqId",respEntity.getMdtInfReqId());
        //
        //										responseMsgEntity = (MdtAcOpsMdteRespMsgEntity)
        //										genericDAO.findByCriteria
        //										(MdtAcOpsMdteRespMsgEntity.class, manrtParams);
        //										log.debug("bill responseMsgEntity ==>
        //										"+responseMsgEntity);
        //
        //										if(responseMsgEntity == null)
        //											responseMsgEntity = new
        //											MdtAcOpsMdteRespMsgEntity();
        //									}
        //								}
        //
        //								//Translate to billingModel
        //								MandateDailyTransModel localModel = new
        //								MandateDailyTransModel();
        //								localModel = new AdminTranslator()
        //								.translateDailyBillingEntityToModel(localEntity,
//								responseMsgEntity.getExtractMsgId());
        //								log.info("localModel ==> "+localModel);
        //								mndtDailyTransList .add(localModel);
        //							}
        //						}
        //					}
      }
    } catch (ObjectNotFoundException onfe) {
      log.error("No Object Exists on DB");
    } catch (Exception e) {
      log.error("Error on retrieveMndtDailyTransPerCreditor :" + e.getMessage());
      e.printStackTrace();
    }

    return mndtDailyTransList;
  }


  public boolean createBillingCtrls(Object obj) {
    try {
      if (obj instanceof ObsSystemBillingCtrlsEntity) {
        ObsSystemBillingCtrlsEntity obsSystemBillingCtrlsEntity = (ObsSystemBillingCtrlsEntity) obj;

        genericDAO.save(obsSystemBillingCtrlsEntity);

        return true;
      } else {
        log.debug("Unable to convert type to ObsSystemBillingCtrlsEntity.");

        return false;
      }
    } catch (Exception e) {
      log.error("Error on createBillingCtrls:" + e.getMessage());
      e.printStackTrace();
      return false;
    }
  }

  public Object retrieveBillingCtrls(Date processDate) {
    ObsSystemBillingCtrlsEntity obsSystemBillingCtrlsEntity = new ObsSystemBillingCtrlsEntity();

    try {
      HashMap<String, Object> parameters = new HashMap<String, Object>();
      parameters.put("obsSystemBillingCtrlsPK.processDate", processDate);
      parameters.put("obsSystemBillingCtrlsPK.systemName", "MANDATES");

      obsSystemBillingCtrlsEntity =
          (ObsSystemBillingCtrlsEntity) genericDAO.findByCriteria(ObsSystemBillingCtrlsEntity.class,
              parameters);
    } catch (ObjectNotFoundException onfe) {
      log.debug("No Object Exists on DB");
    } catch (Exception e) {
      log.error("Error on retrieveBillingCtrls: " + e.getMessage());
      e.printStackTrace();
    }

    return obsSystemBillingCtrlsEntity;
  }

  @Override
  public boolean updateBillingCtrl(Object obj) {
    try {
      if (obj instanceof ObsSystemBillingCtrlsEntity) {
        ObsSystemBillingCtrlsEntity obsSystemBillingCtrlsEntity = (ObsSystemBillingCtrlsEntity) obj;
        genericDAO.saveOrUpdate(obsSystemBillingCtrlsEntity);

        return true;
      } else {
        log.debug("Unable to convert type to ObsSystemBillingCtrlsEntity.");

        return false;
      }
    } catch (Exception e) {
      log.error("Error on updateBillingCtrl:" + e.getMessage());
      e.printStackTrace();
      return false;
    }
  }

  @Override
  public List<?> retrieveCisBankDebtorCreditorInfo() {
    //		List<SystemParamReportEntityModel> sysPramReportEntityList = new
    //		ArrayList<SystemParamReportEntityModel>();
    //
    //		StringBuffer sb = new StringBuffer();

    //		sb.append("select member_no as memberNo, MEMBER_NAME as memberName, ");
    //		sb.append("case when AC_DEBTOR > 0 then 'Y' else 'N' end as debtorBank, ");
    //		sb.append("case when AC_CREDITOR > 0 then 'Y' else 'N' end as creditorBank  ");
    //		sb.append("from  ( ");
    //		sb.append("SELECT a.MEMBER_NO ,a.MEMBER_NAME, ");
    //		sb.append("sum(case when nvl(b.AC_DEBTOR,'N') = 'Y' then 1 else 0 end) as AC_DEBTOR,
    //		");
    //		sb.append("sum(case when nvl(b.AC_CREDITOR,'N') = 'Y' then 1 else 0 end) as
    //		AC_CREDITOR ");
    //		sb.append("FROM CISOWNER.MEMBER a ");
    //		sb.append("LEFT OUTER  JOIN CISOWNER.BRANCH b ");
    //		sb.append("ON a.member_no = b.member_no ");
    //		sb.append("where a.AC_MDT_IND = 'Y' ");
    //		sb.append("group by a.MEMBER_NO,a.MEMBER_NAME ) ");
    //		sb.append("order by MEMBER_NO ");
    //
    //		String sqlQuery = sb.toString();
    //		log.debug("sqlQuery: " + sqlQuery);
    //
    //		List<String> scalarList = new ArrayList<String>();
    //		log.debug("scalarList: " + scalarList);
    //		scalarList.add("memberNo");
    //		scalarList.add("memberName");
    //		scalarList.add("debtorBank");
    //		scalarList.add("creditorBank");
    //
    //
    //		sysPramReportEntityList = genericDAO.findBySql(sqlQuery, scalarList,
    //		SystemParamReportEntityModel.class);
    //
    //		return sysPramReportEntityList;


    List<ACMemberDTO> memberDTOList = new ArrayList<ACMemberDTO>();
    ACMemberDTO memberDTO = new ACMemberDTO();

    try {
      log.debug("Calling CIS Service Bean***************>>>>>");

      //	cisServiceBean.initialize();
      memberDTOList =
          (List<ACMemberDTO>) cisServiceRemote.retrieveCisBankDebtorCreditorInfo(memberDTO);
      if (memberDTOList != null && memberDTOList.size() > 0) {

        log.info("memberDTOList********************:" + memberDTOList.size()); //11
      }
    } catch (DAOException e) {
      log.error("Error on retrieveCisBankDebtorCreditorInfo :" + e.getMessage());
      e.printStackTrace();
    }

    return memberDTOList;

  }

  @Override
  public List<?> viewAllCisBankInfo() {
    List<SysCisBankModel> cisBankList = new ArrayList<SysCisBankModel>();
    List<SysCisBankEntity> cisBankEntityList = new ArrayList<SysCisBankEntity>();

    cisBankEntityList = genericDAO.findAll(SysCisBankEntity.class);

    if (cisBankEntityList != null && cisBankEntityList.size() > 0) {
      SysCisBankLogic sysCisBankLogic = new SysCisBankLogic();
      cisBankList = sysCisBankLogic.retrieveAllSysCisBankData(cisBankEntityList);
    }

    log.info("cisBankList ==> " + cisBankList);
    return cisBankList;


  }

  @Override
  public Object viewCisBanksByCriteria(String memberNo) {
    SysCisBankModel cisBankModel = new SysCisBankModel();
    SysCisBankEntity sysCisBankEntity = new SysCisBankEntity();

    try {
      sysCisBankEntity =
          (SysCisBankEntity) genericDAO.findByNamedQuery("SysCisBankEntity.findByMemberNo",
              "memberNo", memberNo);
      log.info("sysCisBankEntity ==> " + sysCisBankEntity);
      if (sysCisBankEntity != null) {
        SysCisBankLogic sysCisBankLogic = new SysCisBankLogic();
        cisBankModel = sysCisBankLogic.retrieveSysCisBank(sysCisBankEntity);
        log.info("cisBankModel ==> " + cisBankModel);
      }
    } catch (ObjectNotFoundException onfe) {
      log.error("No Object Exists on DB");
    } catch (Exception e) {
      log.error("Error on viewCisBanksByCriteria: " + e.getMessage());
      e.printStackTrace();
    }

    return cisBankModel;
  }

  @Override
  public boolean createCnfgOpsSlaTimes(Object obj) {
    try {
      if (obj instanceof OpsSlaTimesCommonsModel) {
        OpsSlaTimesCommonsModel amendmentCodesModel = (OpsSlaTimesCommonsModel) obj;
        OpsSlaTimesLogic opsSlaTimesLogic = new OpsSlaTimesLogic();
        MdtOpsSlaTimesEntity mdtOpsSlaTimesEntity = new MdtOpsSlaTimesEntity();

        mdtOpsSlaTimesEntity = opsSlaTimesLogic.addOpsSlaTimes(amendmentCodesModel);
        genericDAO.saveOrUpdate(mdtOpsSlaTimesEntity);

        return true;
      } else {
        log.debug("Unable to convert type to SlaTimes.");

        return false;
      }
    } catch (Exception e) {
      log.error("Error on createCnfgOpsSlaTimes:" + e.getMessage());
      e.printStackTrace();

      return false;
    }
  }


  @Override
  public Object retrieveCisDownloadDate() {
    OpsProcessControlModel opsProcessCtrlsModel = null; /*= new OpsProcessControlModel();*/
    List<MdtAcOpsProcessControlsEntity> mdtAcOpsProcessControlsEntityList =
        new ArrayList<MdtAcOpsProcessControlsEntity>();

    mdtAcOpsProcessControlsEntityList = genericDAO.findAll(MdtAcOpsProcessControlsEntity.class);

    if (mdtAcOpsProcessControlsEntityList != null && mdtAcOpsProcessControlsEntityList.size() > 0) {
      MdtAcOpsProcessControlsEntity localEntity = mdtAcOpsProcessControlsEntityList.get(0);
      opsProcessCtrlsModel = new OpsProcessControlModel();
      opsProcessCtrlsModel =
          new AdminTranslator().translateMdtAcOpsProcessControlsEntityToCommonsModel(localEntity);
    }
    return opsProcessCtrlsModel;
  }

  @Override
  public List<?> viewAllCisBranchInfo() {
    List<SysCisBranchModel> cisBranchList = new ArrayList<SysCisBranchModel>();
    List<SysCisBranchEntity> cisBranchEntityList = new ArrayList<SysCisBranchEntity>();

    cisBranchEntityList = genericDAO.findAll(SysCisBranchEntity.class);

    if (cisBranchEntityList != null && cisBranchEntityList.size() > 0) {
      SysCisBranchLogic sysCisBranchLogic = new SysCisBranchLogic();
      cisBranchList = sysCisBranchLogic.retrieveAllSysCisBranchData(cisBranchEntityList);
    }
    return cisBranchList;
  }

  @Override
  public Object viewCisBranchByCriteria(String branchNo) {
    SysCisBranchModel cisBranchModel = new SysCisBranchModel();
    SysCisBranchEntity sysCisBranchEntity = new SysCisBranchEntity();

    //		try
    //		{
    //			HashMap<String, Object> parameters = new HashMap<String, Object>();
    //			parameters.put("branchNo", branchNo);
    //
    //
    //			log.debug("---------------sparameters: ------------------"+ parameters.toString());
    //			cisBranchList =  (List<SysCisBranchEntity>) genericDAO.findByCriteria
    //			(SysCisBranchEntity.class, parameters);
    //			log.info("---------------cisBranchList after findByCriteria: ------------------"+
    //			cisBranchList);
    //
    //			if (cisBranchList != null && cisBranchList.size() > 0)
    //			{
    //				for (SysCisBranchEntity localEntity : cisBranchList)
    //				{
    //					SysCisBranchModel cisBranchModel = new SysCisBranchModel();
    //
    //					 SysCisBranchLogic sysCisBranchLogic = new SysCisBranchLogic();
    //					 cisBranchModel = sysCisBranchLogic.retrieveSysCisBranch(localEntity);
    //					log.info("cisBranchModel ==> "+cisBranchModel);
    //					cisBranchModelList.add(cisBranchModel);
    //				}
    //			}


    try {
      sysCisBranchEntity =
          (SysCisBranchEntity) genericDAO.findByNamedQuery("SysCisBranchEntity.findByBranchNo",
              "branchNo", branchNo);
      log.info("sysCisBranchEntity ==> " + sysCisBranchEntity);
      if (sysCisBranchEntity != null) {
        SysCisBranchLogic sysCisBranchLogic = new SysCisBranchLogic();
        cisBranchModel = sysCisBranchLogic.retrieveSysCisBranch(sysCisBranchEntity);
        log.info("cisBranchModel ==> " + cisBranchModel);
      }
    } catch (ObjectNotFoundException onfe) {
      log.error("No Object Exists on DB");
    } catch (Exception e) {
      log.error("Error on viewCisBranchByCriteria: " + e.getMessage());
      e.printStackTrace();
    }

    return cisBranchModel;
  }

  @Override
  public List<?> viewSlaTimesByCriteria(String service) {
    List<OpsSlaTimesCommonsModel> opsSlaTimesCommonsModelList =
        new ArrayList<OpsSlaTimesCommonsModel>();

    try {
      List<MdtOpsSlaTimesEntity> mdtOpsSlaTimesEntity =
          genericDAO.findAllByNamedQuery("MdtOpsSlaTimesEntity.findByService", "service", service);
      log.info("mdtOpsSlaTimesEntity: " + mdtOpsSlaTimesEntity);

      if (mdtOpsSlaTimesEntity.size() > 0) {
        OpsSlaTimesLogic opsSlaTimesLogic = new OpsSlaTimesLogic();

        for (MdtOpsSlaTimesEntity localEntity : mdtOpsSlaTimesEntity) {
          OpsSlaTimesCommonsModel opsSlaTimesCommonsModel = new OpsSlaTimesCommonsModel();
          opsSlaTimesCommonsModel = opsSlaTimesLogic.retrieveOpsSlaTimes(localEntity);
          opsSlaTimesCommonsModelList.add(opsSlaTimesCommonsModel);
          log.info("opsSlaTimesCommonsModelList: " + opsSlaTimesCommonsModelList);
        }
      }
    } catch (ObjectNotFoundException onfe) {
      log.error("No object Exists on DB");
    } catch (Exception e) {
      log.error("Error retrieveAccountTypeByCriteria: " + e.getMessage());
      e.printStackTrace();
    }

    return opsSlaTimesCommonsModelList;

  }

  @Override
  public List<?> viewSysCtrlSlaTimes() {
    List<SysCtrlSlaTimeModel> sysCtrlSlaTimeModelList = new ArrayList<SysCtrlSlaTimeModel>();

    List<CasSysctrlSlaTimesEntity> mdtSysctrlSlaTimesEntityList =
        new ArrayList<CasSysctrlSlaTimesEntity>();

    mdtSysctrlSlaTimesEntityList = genericDAO.findAll(CasSysctrlSlaTimesEntity.class);
    if (mdtSysctrlSlaTimesEntityList.size() > 0) {
      SysCtrlSlaTimesLogic sysCtrlSlaTimesLogic = new SysCtrlSlaTimesLogic();
      sysCtrlSlaTimeModelList =
          sysCtrlSlaTimesLogic.retrieveAllSysCtrlSlaTimes(mdtSysctrlSlaTimesEntityList);
    }

    return sysCtrlSlaTimeModelList;
  }

  @Override
  public List<?> viewSysCtrlSlaTimesByCriteria(String service) {
    List<SysCtrlSlaTimeModel> sysCtrlSlaTimeModelList = new ArrayList<SysCtrlSlaTimeModel>();

    try {
      List<CasSysctrlSlaTimesEntity> casSysctrlSlaTimesEntity =
          genericDAO.findAllByNamedQuery("MdtSysctrlSlaTimesEntity.findByService", "service",
              service);
      log.info("mdtSysctrlSlaTimesEntity: " + casSysctrlSlaTimesEntity);

      if (casSysctrlSlaTimesEntity.size() > 0) {
        SysCtrlSlaTimesLogic sysCtrlSlaTimesLogic = new SysCtrlSlaTimesLogic();

        for (CasSysctrlSlaTimesEntity localEntity : casSysctrlSlaTimesEntity) {
          SysCtrlSlaTimeModel sysCtrlSlaTimeModel = new SysCtrlSlaTimeModel();
          sysCtrlSlaTimeModel = sysCtrlSlaTimesLogic.retrieveSysCtrlSlaTimes(localEntity);
          sysCtrlSlaTimeModelList.add(sysCtrlSlaTimeModel);
          log.info("sysCtrlSlaTimeModelList: " + sysCtrlSlaTimeModelList);
        }
      }
    } catch (ObjectNotFoundException onfe) {
      log.error("No object Exists on DB");
    } catch (Exception e) {
      log.error("Error viewSysCtrlSlaTimesByCriteria: " + e.getMessage());
      e.printStackTrace();
    }

    return sysCtrlSlaTimeModelList;

  }


  @Override
  public boolean createCnfgSysCtrlSlaTimes(Object obj) {
    try {
      if (obj instanceof SysCtrlSlaTimeModel) {
        SysCtrlSlaTimeModel sysCtrlSlaTimeModel = (SysCtrlSlaTimeModel) obj;
        SysCtrlSlaTimesLogic sysCtrlSlaTimesLogic = new SysCtrlSlaTimesLogic();
        CasSysctrlSlaTimesEntity casSysctrlSlaTimesEntity = new CasSysctrlSlaTimesEntity();

        casSysctrlSlaTimesEntity = sysCtrlSlaTimesLogic.addSysCtrlSlaTimes(sysCtrlSlaTimeModel);
        genericDAO.saveOrUpdate(casSysctrlSlaTimesEntity);

        return true;
      } else {
        log.debug("Unable to convert type to SlaTimes.");

        return false;
      }
    } catch (Exception e) {
      log.error("Error on createCnfgSysCtrlSlaTimes:" + e.getMessage());
      e.printStackTrace();

      return false;
    }
  }


  public List<?> retrieveOpsFileRegbyDerictionCriteria(String direction) {
    List<OpsFileRegModel> fileRegList = new ArrayList<OpsFileRegModel>();
    //List<MdtOpsFileRegEntity> mdtOpsFileRegEntityList = null;

    try {
      List<MdtOpsFileRegEntity> mdtOpsFileRegEntityList =
          (List<MdtOpsFileRegEntity>) genericDAO.findAllByNamedQuery(
              "MdtOpsFileRegEntity.findByInOutIndASC", "inOutInd", direction);

      if (mdtOpsFileRegEntityList != null && mdtOpsFileRegEntityList.size() > 0) {
        OpsFileRegLogic opsFileRegLogic = new OpsFileRegLogic();

        for (MdtOpsFileRegEntity localEntity : mdtOpsFileRegEntityList) {

          OpsFileRegModel opsFileRegModel = new OpsFileRegModel();
          opsFileRegModel = opsFileRegLogic.retrieveDelDelivery(localEntity);

          fileRegList.add(opsFileRegModel);
          //fileRegList = opsFileRegLogic.retrieveAllDelDelivery(mdtOpsFileRegEntityList);
        }
      }
    } catch (Exception ex) {
      log.error("Error on retrieveOpsFileRegbyCriteria: " + ex.getMessage());
    }

    //log.info("fileRegList---->"+fileRegList);
    return fileRegList;

  }

  @Override
  public Object retrieveEODTime() {

    MdtOpsSlaTimesEntity mdtOpsSlaTimesEntity;
    mdtOpsSlaTimesEntity =
        (MdtOpsSlaTimesEntity) genericDAO.findByNamedQuery("MdtOpsSlaTimesEntity.findByService",
            "service", "EOD");

    return mdtOpsSlaTimesEntity;
  }


  @Override
  public Object retrieveCisSlaTime(String processType) {

    SysCtrlSlaTimeModel sysCtrlSlaTimeModel = new SysCtrlSlaTimeModel();
    //MdtSysctrlSlaTimesEntity sysctrlSlaTimesEntity = new MdtSysctrlSlaTimesEntity();

    try {
      CasSysctrlSlaTimesEntity sysctrlSlaTimesEntity =
          (CasSysctrlSlaTimesEntity) genericDAO.findByNamedQuery(
              "MdtSysctrlSlaTimesEntity.findByService", "service", processType);
//			log.info("sysctrlSlaTimesEntity ==> "+sysctrlSlaTimesEntity);
      if (sysctrlSlaTimesEntity != null) {
        SysCtrlSlaTimesLogic sysCtrlSlaTimesLogic = new SysCtrlSlaTimesLogic();
        sysCtrlSlaTimeModel = sysCtrlSlaTimesLogic.retrieveSysCtrlSlaTimes(sysctrlSlaTimesEntity);
//				log.info("sysCtrlSlaTimeModel ==> "+sysCtrlSlaTimeModel);

      }
    } catch (ObjectNotFoundException onfe) {
      log.error("No Object Exists on DB");
    } catch (Exception e) {
      log.error("Error on retrieveCisSlaTime: " + e.getMessage());
      e.printStackTrace();
    }

    return sysCtrlSlaTimeModel;
  }

  @Override
  public List<?> retrieveMndtDailyTransPerDebtor(String instId, String txnType) {
    List<MandateDailyTransModel> mndtDailyTransList = new ArrayList<MandateDailyTransModel>();
    List<MandateDailyTransEntityModel> mndtDailyTransEntityList =
        new ArrayList<MandateDailyTransEntityModel>();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    Date currentDate = new Date();
    log.debug("currentDate---->" + currentDate);

    String stringDate = sdf.format(currentDate);
    log.debug("stringDate---->" + stringDate);

    try {

      //Retrieve Billable Transactions
      List<MdtAcOpsDailyBillingEntity> dailyBillingList =
          new ArrayList<MdtAcOpsDailyBillingEntity>();

      HashMap<String, Object> dailyBillParams = new HashMap<String, Object>();
      dailyBillParams.put("txnType", txnType);
      dailyBillParams.put("debtorBank", instId);

      dailyBillingList = (List<MdtAcOpsDailyBillingEntity>) genericDAO.findAllByCriteria(
          MdtAcOpsDailyBillingEntity.class, dailyBillParams);

      if (dailyBillingList != null && dailyBillingList.size() > 0) {
        for (MdtAcOpsDailyBillingEntity localEntity : dailyBillingList) {
          String subService = localEntity.getSubService();
          log.debug("subService ==> " + subService);

          if (subService.equalsIgnoreCase(srinp)) {
            //Translate to billingModel
            MandateDailyTransModel localModel = new MandateDailyTransModel();
            localModel = new AdminTranslator().translateDailyBillingEntityToModel(localEntity);
            log.debug("localModel ==> " + localModel);
            mndtDailyTransList.add(localModel);

            //							2017-09-05 SalehaR - This has been removed as all data is
            //							stored on the MDT_AC_OPS_DAILY_BILLING table

            //							//Retrieve SRINP Information
            //							MdtAcOpsConfDetailsEntity confDtlsEntity = null;
            //
            //							HashMap<String, Object> srinpParams = new HashMap<String,
            //							Object>();
            //							srinpParams.put("txnId", localEntity.getTxnId());
            //							srinpParams.put("extractService","SROUT");
            //
            //							confDtlsEntity = (MdtAcOpsConfDetailsEntity) genericDAO
            //							.findByCriteria(MdtAcOpsConfDetailsEntity.class,
            //							srinpParams);
            //
            //							if(confDtlsEntity == null)
            //								confDtlsEntity = new MdtAcOpsConfDetailsEntity();
            //
            //							//Translate to billingModel
            //							MandateDailyTransModel localModel = new
            //							MandateDailyTransModel();
            //							localModel = new AdminTranslator()
            //							.translateDailyBillingEntityToModel(localEntity,
            //							confDtlsEntity.getExtractMsgId());
            //							log.debug("localModel ==> "+localModel);
            //							mndtDailyTransList .add(localModel);
          }
        }
      }
    } catch (ObjectNotFoundException onfe) {
      log.error("No Object Exists on DB");
    } catch (Exception e) {
      log.error("Error on retrieveMndtDailyTransPerDebtor :" + e.getMessage());
      e.printStackTrace();
    }

    return mndtDailyTransList;
  }

  @Override
  public Object retrieveMdte002RejectReasonDataPerBank(String rejectReasonCode, String memberId,
                                                       String debtorBankId, String firstDate,
                                                       String lastDate) {
    List<MandateRejectionModel> mandateRejectionModelList = new ArrayList<MandateRejectionModel>();
    List<MandateRejectionEntityModel> mandateRejectionEntityList =
        new ArrayList<MandateRejectionEntityModel>();
    MandateRejectionModel mandateRejectionModel = new MandateRejectionModel();


    //			SimpleDateFormat sdf = new SimpleDateFormat("ddMMYYYY");
    //
    //			Date currentDate = new Date();
    //			Date  date = null;
    //
    //			try
    //			{
    //				date= DateUtil.addOneDay(currentDate);
    //				log.debug("finalCurrentDate**************"+ currentDate);
    //			}
    //			catch (ParseException e1)
    //			{
    //				// TODO Auto-generated catch block
    //				e1.printStackTrace();
    //			}
    //
    //			String stringDate = DateUtil.convertDateToString(date, "ddMMYYYY");
    //
    //			String dayOne = stringDate.substring(2,8);
    //			String firstDateOfMonth ="01"+dayOne;
    //			log.debug("firsDateOfMonth**************"+ firstDateOfMonth);

    StringBuffer sb = new StringBuffer();

		/*	select DR_BANK_MEMBER_ID,REJECT_REASON, count(*) as Count
			from MDT_AC_ARC_MDTE_RESP
			where DR_BANK_MEMBER_ID = '210044' AND REJECT_REASON ='MDNF'
			GROUP BY DR_BANK_MEMBER_ID,REJECT_REASON*/

    sb.append(
        "SELECT DR_BANK_MEMBER_ID,CR_BANK_MEMBER_ID,  REJECT_REASON, COUNT(*) AS " +
            "rejectReasonCodeCount ");
    sb.append("FROM MANOWNER.MDT_AC_ARC_MDTE_RESP ");
    sb.append(
        "WHERE DR_BANK_MEMBER_ID = '" + debtorBankId + "' AND CR_BANK_MEMBER_ID ='" + memberId +
            "' AND ");
    sb.append(
        "REJECT_REASON = '" + rejectReasonCode + "' AND TRUNC(CREATE_DATE_TIME) BETWEEN TO_DATE('" +
            firstDate + "','DDMMYYYY') AND TO_DATE('" + lastDate + "','DDMMYYYY') ");
    sb.append("GROUP BY DR_BANK_MEMBER_ID , CR_BANK_MEMBER_ID,REJECT_REASON");

    String sqlQuery = sb.toString();
    log.debug("sqlQuery  mdte002--->: " + sqlQuery);

    List<String> scalarList = new ArrayList<String>();

    scalarList.add("rejectReasonCodeCount");
    log.debug("rejectReasonCodeCount----->" + scalarList);

    mandateRejectionEntityList =
        genericDAO.findBySql(sqlQuery, scalarList, MandateRejectionEntityModel.class);
    if (mandateRejectionEntityList != null && mandateRejectionEntityList.size() > 0) {
      for (MandateRejectionEntityModel mandateRejectionEntityModel : mandateRejectionEntityList) {
        mandateRejectionModel = new MandateRejectionModel();
        mandateRejectionModel =
            new ServiceTranslator().translateMandateRejectionEntityModelToCommonsModel(
                mandateRejectionEntityModel);
        mandateRejectionModelList.add(mandateRejectionModel);
      }
    }

    log.debug("mandateRejectionModel----->" + mandateRejectionModel);
    return mandateRejectionModel;

  }

  @Override
  public Object retrieveSuspensionDataPerBank(String suspensionCode, String debtorBank,
                                              String memberId) {
    List<MandateRejectionModel> mandateSuspModelList = new ArrayList<MandateRejectionModel>();
    List<MandateRejectionEntityModel> mandateSuspEntityList =
        new ArrayList<MandateRejectionEntityModel>();
    MandateRejectionModel mandateSuspModel = new MandateRejectionModel();

    StringBuffer sb = new StringBuffer();

		/*select b.ASSIGNER, a.CREDITOR_BANK ,a.REASON_CODE, count(*) as Count
	     from MDT_AC_ARC_SUSP_MSG a
	     LEFT JOIN MDT_AC_ARC_SUSP_GRP_HDR b ON a.ASSIGNMENT_ID =b.assignment_id
	      where ASSIGNER = '210055' AND  a.CREDITOR_BANK= '210009' AND REASON_CODE ='CTCA'
	      GROUP BY ASSIGNER,REASON_CODE,CREDITOR_BANK */

    sb.append(
        "SELECT b.ASSIGNER,a.CREDITOR_BANK , a.REASON_CODE, COUNT(*)AS rejectReasonCodeCount ");
    sb.append("FROM MANOWNER.MDT_AC_ARC_SUSP_MSG a ");
    sb.append("LEFT JOIN MANOWNER.MDT_AC_ARC_SUSP_GRP_HDR b ON a.ASSIGNMENT_ID = b.ASSIGNMENT_ID ");
    sb.append("WHERE b.ASSIGNER = '" + debtorBank + "' AND a.CREDITOR_BANK ='" + memberId +
        "' AND a.REASON_CODE = '" + suspensionCode + "' ");
    sb.append("GROUP BY b.ASSIGNER,a.REASON_CODE,a.CREDITOR_BANK ");


    String sqlQuery = sb.toString();
    log.debug("sqlQuery SPINP--->: " + sqlQuery);

    List<String> scalarList = new ArrayList<String>();

    scalarList.add("rejectReasonCodeCount");

    mandateSuspEntityList =
        genericDAO.findBySql(sqlQuery, scalarList, MandateRejectionEntityModel.class);

    if (mandateSuspEntityList != null && mandateSuspEntityList.size() > 0) {
      for (MandateRejectionEntityModel mandateSuspEntityModel : mandateSuspEntityList) {
        mandateSuspModel = new MandateRejectionModel();
        mandateSuspModel =
            new ServiceTranslator().translateMandateRejectionEntityModelToCommonsModel(
                mandateSuspEntityModel);
        mandateSuspModelList.add(mandateSuspModel);
      }
    }

    return mandateSuspModel;

  }

  @Override
  public Object retrievePain012ReasonCodeDataPerBank(String reasonCode, String memberId,
                                                     String debtorBankId, String startDate,
                                                     String lastDate) {
    List<MandateRejectionModel> mandateReasonCodeModelList = new ArrayList<MandateRejectionModel>();
    List<MandateRejectionEntityModel> mandateReasonCodeEntityList =
        new ArrayList<MandateRejectionEntityModel>();
    MandateRejectionModel mandateReasonCodeModel = new MandateRejectionModel();

    StringBuffer sb = new StringBuffer();

    sb.append(
        "SELECT b.MEMBER_ID,c.MEMBER_ID, a.REJECT_REASON_CODE, COUNT(*) AS rejectReasonCodeCount ");
    sb.append("FROM MANOWNER.MDT_AC_ARC_MNDT_MSG a ");
    sb.append(
        "LEFT JOIN MANOWNER.MDT_AC_ARC_FIN_INST b ON a.MSG_ID = b.MSG_ID AND a" +
            ".MANDATE_REQ_TRAN_ID = b.MANDATE_REQ_TRAN_ID ");
    sb.append(
        "LEFT JOIN MANOWNER.MDT_AC_ARC_FIN_INST c ON a.MSG_ID = c.MSG_ID AND a" +
            ".MANDATE_REQ_TRAN_ID = c.MANDATE_REQ_TRAN_ID ");
    sb.append(
        "WHERE b.FIN_INST_TYPE_ID = 'FI03' AND  c.FIN_INST_TYPE_ID = 'FI04' AND b.MEMBER_ID = '" +
            memberId + "' AND c.MEMBER_ID ='" + debtorBankId + "' ");
    sb.append("AND a.REJECT_REASON_CODE ='" + reasonCode +
        "' AND a.SERVICE_ID ='MANAC' AND TRUNC(a.CREATED_DATE) BETWEEN TO_DATE('" + startDate +
        "','DDMMYYYY') AND TO_DATE('" + lastDate + "','DDMMYYYY') ");
    sb.append("GROUP BY  b.MEMBER_ID,c.MEMBER_ID,a.REJECT_REASON_CODE ");


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

  @Override
  public boolean saveSystemAuditInfo(Object obj) {
    try {
      if (obj instanceof AudSystemProcessModel) {
        AudSystemProcessModel audSystemProcessModel = (AudSystemProcessModel) obj;
        AuditTrackingLogic auditTrackingLogic = new AuditTrackingLogic();

        AudSystemProcessEntity audSystemProcessEntity = new AudSystemProcessEntity();

        audSystemProcessEntity =
            auditTrackingLogic.translateAudSystemModelToEntity(audSystemProcessModel);
        genericDAO.save(audSystemProcessEntity);

        return true;
      } else {
        log.debug("Unable to convert type to AudSystemProcessModel.");
        return false;
      }
    } catch (Exception e) {
      log.error("Error on saveSystemAuditInfo:" + e.getMessage());
      e.printStackTrace();
      return false;
    }
  }


  @Override
  public List<?> retrieveActiveDebtorBank() {
    List<DebtorBankModel> debtorBankModelModelList = new ArrayList<DebtorBankModel>();
    List<DebtorBankEntityModel> debtorBankList = new ArrayList<DebtorBankEntityModel>();

    try {

      StringBuffer sb = new StringBuffer();

			/*  select a.member_name, b.inst_id
			  from sys_cis_bank a
			  left join mdt_ops_cust_param b  on b.inst_id = a.member_no*/

      sb.append("SELECT MEMBER_NAME as memberName ,MEMBER_NO as memberNo ");
      sb.append("FROM MANOWNER.SYS_CIS_BANK a ");
      sb.append("WHERE AC_DEBTOR = 'Y' ");
      sb.append("ORDER BY MEMBER_NAME ASC ");

      String sqlQuery = sb.toString();
      log.debug("The Sql query is  ***************" + sqlQuery);
      List<String> scalarList = new ArrayList<String>();

      scalarList.add("memberName");
      scalarList.add("memberNo");

      try {

        debtorBankList = genericDAO.findBySql(sqlQuery, scalarList, DebtorBankEntityModel.class);

        if (debtorBankList != null && debtorBankList.size() > 0) {
          for (DebtorBankEntityModel localEntityModel : debtorBankList) {
            DebtorBankModel debtorBankModel = new DebtorBankModel();
            debtorBankModel = new ServiceTranslator().translateDebtorBankEntityModelToCommonsModel(
                localEntityModel);
            debtorBankModelModelList.add(debtorBankModel);
          }
        }
      } catch (ObjectNotFoundException onfe) {
        log.error("No Object Exists on DB");
      }

    } catch (Exception e) {
      log.error("Error on retrieveActiveDebtorBank :" + e.getMessage());
      e.printStackTrace();
    }
    return debtorBankModelModelList;
  }

  @Override
  public Object retrieveRejectReasonCountPerBank(String memberId, String debtorBank) {


    List<MandateRejectionModel> mandateRejectCountModelList =
        new ArrayList<MandateRejectionModel>();
    List<MandateRejectionEntityModel> mandateRejectCountEntityList =
        new ArrayList<MandateRejectionEntityModel>();
    MandateRejectionModel mandateRejectCountModel = new MandateRejectionModel();

    StringBuffer sb = new StringBuffer();

		/*select DR_BANK_MEMBER_ID,CR_BANK_MEMBER_ID,REJECT_REASON, count(*) as count
		from MDT_AC_ARC_MDTE_RESP
		where  DR_BANK_MEMBER_ID ='210044'and CR_BANK_MEMBER_ID ='210007' AND REJECT_REASON is not
null
		GROUP BY REJECT_REASON,DR_BANK_MEMBER_ID,CR_BANK_MEMBER_ID*/


    sb.append(
        "SELECT DR_BANK_MEMBER_ID,CR_BANK_MEMBER_ID,REJECT_REASON, COUNT(*) AS " +
            "rejectReasonCodeCount ");
    sb.append("FROM MANOWNER.MDT_AC_ARC_MDTE_RESP ");
    sb.append("WHERE DR_BANK_MEMBER_ID ='" + debtorBank + "' AND CR_BANK_MEMBER_ID = '" + memberId +
        "' AND REJECT_REASON is not null ");
    sb.append("GROUP BY DR_BANK_MEMBER_ID,CR_BANK_MEMBER_ID,REJECT_REASON ");

    String sqlQuery = sb.toString();
    log.debug("sqlQuery: " + sqlQuery);

    List<String> scalarList = new ArrayList<String>();

    scalarList.add("rejectReasonCodeCount");

    mandateRejectCountEntityList =
        genericDAO.findBySql(sqlQuery, scalarList, MandateRejectionEntityModel.class);

    if (mandateRejectCountEntityList != null && mandateRejectCountEntityList.size() > 0) {
      for (MandateRejectionEntityModel mandateRejectReasonCountEntityModel :
          mandateRejectCountEntityList) {
        mandateRejectCountModel = new MandateRejectionModel();
        mandateRejectCountModel =
            new ServiceTranslator().translateMandateRejectionEntityModelToCommonsModel(
                mandateRejectReasonCountEntityModel);
        mandateRejectCountModelList.add(mandateRejectCountModel);
      }
    }
    return mandateRejectCountModel;

  }

  @Override
  public Object retrieveSuspensionCountPerBank(String memberId, String debtorBank) {
    List<MandateRejectionModel> mandateSuspensionCountModelList =
        new ArrayList<MandateRejectionModel>();
    List<MandateRejectionEntityModel> mandateSuspensionCountEntityList =
        new ArrayList<MandateRejectionEntityModel>();
    MandateRejectionModel mandateSuspensionCountModel = new MandateRejectionModel();

    StringBuffer sb = new StringBuffer();

		/*select SUBSTR(b.ASSIGNMENT_ID, 13, 6),a.CREDITOR_BANK ,a.REASON_CODE, count(*) as Count
		from MANOWNER.MDT_AC_ARC_SUSP_MSG a
		LEFT JOIN MANOWNER.MDT_AC_ARC_SUSP_GRP_HDR b ON a.ASSIGNMENT_ID =b.ASSIGNMENT_ID
		where SUBSTR(b.ASSIGNMENT_ID, 13, 6) = '210016' AND a.CREDITOR_BANK = '210002' AND a
.REASON_CODE is not null
		GROUP BY SUBSTR(b.ASSIGNMENT_ID, 13, 6),a.CREDITOR_BANK,a.REASON_CODE*/

    sb.append(
        "SELECT SUBSTR(b.ASSIGNMENT_ID, 13, 6),a.CREDITOR_BANK , a.REASON_CODE, COUNT(*) AS " +
            "rejectReasonCodeCount ");
    sb.append("FROM MANOWNER.MDT_AC_ARC_SUSP_MSG a ");
    sb.append("LEFT JOIN MANOWNER.MDT_AC_ARC_SUSP_GRP_HDR b ON a.ASSIGNMENT_ID =b.ASSIGNMENT_ID ");
    sb.append(
        "WHERE SUBSTR(b.ASSIGNMENT_ID, 13, 6) = '" + debtorBank + "' AND a.CREDITOR_BANK = '" +
            memberId + "' AND a.REASON_CODE is not null ");
    sb.append("GROUP BY SUBSTR(b.ASSIGNMENT_ID, 13, 6),a.CREDITOR_BANK , a.REASON_CODE ");


    String sqlQuery = sb.toString();
    log.debug("sqlQuery: " + sqlQuery);

    List<String> scalarList = new ArrayList<String>();

    scalarList.add("rejectReasonCodeCount");

    mandateSuspensionCountEntityList =
        genericDAO.findBySql(sqlQuery, scalarList, MandateRejectionEntityModel.class);

    if (mandateSuspensionCountEntityList != null && mandateSuspensionCountEntityList.size() > 0) {
      for (MandateRejectionEntityModel suspCountEntityModel : mandateSuspensionCountEntityList) {
        mandateSuspensionCountModel = new MandateRejectionModel();
        mandateSuspensionCountModel =
            new ServiceTranslator().translateMandateRejectionEntityModelToCommonsModel(
                suspCountEntityModel);
        mandateSuspensionCountModelList.add(mandateSuspensionCountModel);
      }
    }
    return mandateSuspensionCountModel;
  }

  @Override
  public Object retrieveReasonCodeCountPerBank(String memberId, String debtorBank) {
    List<MandateRejectionModel> mandateReasonCodeCountModelList =
        new ArrayList<MandateRejectionModel>();
    List<MandateRejectionEntityModel> mandateReasonCodeCountEntityList =
        new ArrayList<MandateRejectionEntityModel>();
    MandateRejectionModel mandateReasonCodeCountModel = new MandateRejectionModel();

    StringBuffer sb = new StringBuffer();

		/*SELECT b.MEMBER_ID,c.MEMBER_ID, a.REJECT_REASON_CODE, COUNT(*) AS COUNT
		FROM MDT_AC_ARC_MNDT_MSG a
		LEFT JOIN MANOWNER.MDT_AC_ARC_FIN_INST b ON a.MSG_ID = b.MSG_ID AND a.MANDATE_REQ_TRAN_ID
		= b.MANDATE_REQ_TRAN_ID
		LEFT JOIN MANOWNER.MDT_AC_ARC_FIN_INST c ON a.MSG_ID = c.MSG_ID AND a.MANDATE_REQ_TRAN_ID
				= c.MANDATE_REQ_TRAN_ID
		WHERE b.FIN_INST_TYPE_ID = 'FI03' AND  c.FIN_INST_TYPE_ID = 'FI04'AND  b.MEMBER_ID =
		'210003' AND a.SERVICE_ID ='MANAC'
		GROUP BY  b.MEMBER_ID,  c.MEMBER_ID,a.REJECT_REASON_CODE*/


    sb.append(
        "SELECT b.MEMBER_ID,c.MEMBER_ID, a.REJECT_REASON_CODE, COUNT(*) AS rejectReasonCodeCount ");
    sb.append("FROM MANOWNER.MDT_AC_ARC_MNDT_MSG a ");
    sb.append(
        "LEFT JOIN MANOWNER.MDT_AC_ARC_FIN_INST b ON a.MSG_ID = b.MSG_ID AND a" +
            ".MANDATE_REQ_TRAN_ID = b.MANDATE_REQ_TRAN_ID ");
    sb.append(
        "LEFT JOIN MANOWNER.MDT_AC_ARC_FIN_INST c ON a.MSG_ID = c.MSG_ID AND a" +
            ".MANDATE_REQ_TRAN_ID = c.MANDATE_REQ_TRAN_ID ");
    sb.append(
        "WHERE b.FIN_INST_TYPE_ID = 'FI03' AND  c.FIN_INST_TYPE_ID = 'FI04' AND b.MEMBER_ID = '" +
            memberId + "' AND c.MEMBER_ID ='" + debtorBank + "' ");
    sb.append("AND a.SERVICE_ID ='MANAC'  AND REJECT_REASON_CODE IS NOT NULL ");
    sb.append("GROUP BY  b.MEMBER_ID,c.MEMBER_ID,a.REJECT_REASON_CODE ");


    String sqlQuery = sb.toString();
    log.debug("sqlQuery: " + sqlQuery);

    List<String> scalarList = new ArrayList<String>();

    scalarList.add("rejectReasonCodeCount");

    mandateReasonCodeCountEntityList =
        genericDAO.findBySql(sqlQuery, scalarList, MandateRejectionEntityModel.class);

    if (mandateReasonCodeCountEntityList != null && mandateReasonCodeCountEntityList.size() > 0) {
      for (MandateRejectionEntityModel reasonCountEntityModel : mandateReasonCodeCountEntityList) {
        mandateReasonCodeCountModel = new MandateRejectionModel();
        mandateReasonCodeCountModel =
            new ServiceTranslator().translateMandateRejectionEntityModelToCommonsModel(
                reasonCountEntityModel);
        mandateReasonCodeCountModelList.add(mandateReasonCodeCountModel);
      }
    }
    return mandateReasonCodeCountModel;
  }


  @Override
  public Object retrieveCronIntervalValue(String cronInt) {
    SchedulerCronModel schedulerCronModel = null;

    CasSysctrlSchedulerCronEntity casSysctrlSchedulerCronEntity =
        new CasSysctrlSchedulerCronEntity();
    casSysctrlSchedulerCronEntity = (CasSysctrlSchedulerCronEntity) genericDAO.findByNamedQuery(
        "MdtSysctrlSchedulerCronEntity.findBySchedulerCronInterval", "schedulerCronInterval",
        cronInt);

    if (casSysctrlSchedulerCronEntity != null) {
      schedulerCronModel = new SchedulerCronModel();
      schedulerCronModel =
          new AdminTranslator().translateSchedulerCronEntityToModel(casSysctrlSchedulerCronEntity);
    }

    return schedulerCronModel;
  }

  @Override
  public List<?> retrieveRealTimeMndtBillingTxns(String txnType) {
    List<MandateDailyTransModel> mndtDailyTransList = new ArrayList<MandateDailyTransModel>();
    List<MandateDailyTransEntityModel> mndtDailyTransEntityList =
        new ArrayList<MandateDailyTransEntityModel>();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    Date currentDate = new Date();
    log.debug("currentDate---->" + currentDate);

    try {
      String stringDate = sdf.format(currentDate);
      log.debug("stringDate---->" + stringDate);


//			//Retrieve Billable Transactions
      List<MdtAcOpsDailyBillingEntity> dailyBillingList =
          new ArrayList<MdtAcOpsDailyBillingEntity>();
//			2023/04/04 SalehaN  - Change inequality statement due to index being skipped.
//			HashMap<String, Object> parameters = new HashMap<String, Object>();
//			parameters.put("txnType",txnType);
//			dailyBillingList = (List<MdtAcOpsDailyBillingEntity>) genericDAO
//			.findByCriteriaNotEqual(MdtAcOpsDailyBillingEntity.class, parameters);

      HashMap<String, Object> parameters = new HashMap<String, Object>();
      parameters.put("txnType1", "TT1");
      parameters.put("txnType2", "TT3");

      dailyBillingList =
          genericDAO.findAllByNQCriteria("MdtAcOpsDailyBillingEntity.findByRealTimebyTxnType",
              parameters);

      //Retrieve TT1
      if (dailyBillingList != null && dailyBillingList.size() > 0) {
        log.debug("dailyBillingList.size() ==>" + dailyBillingList.size());
        for (MdtAcOpsDailyBillingEntity localEntity : dailyBillingList) {
          String subService = localEntity.getSubService();
          //					log.debug("Billing SubService ==> "+subService);
          //					log.debug("Billing txn status ==> "+localEntity.getTxnStatus());

          MandateDailyTransModel localModel = new MandateDailyTransModel();
          localModel = new AdminTranslator().translateDailyBillingEntityToModel(localEntity);
          //					log.debug("localModel ==> "+localModel);
          mndtDailyTransList.add(localModel);
        }
      }
    } catch (ObjectNotFoundException onfe) {
      log.error("No Object Exists on DB");
    } catch (Exception e) {
      log.error("Error on retrieveMndtDailyTransPerBank :" + e.getMessage());
      e.printStackTrace();
    }


    return mndtDailyTransList;
  }


  @Override
  public List<?> retrieveRealTimeMndtBillingTxnsByCreditor(String instId) {
    List<MandateDailyTransModel> mndtDailyTransList = new ArrayList<MandateDailyTransModel>();
    List<MandateDailyTransEntityModel> mndtDailyTransEntityList =
        new ArrayList<MandateDailyTransEntityModel>();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    Date currentDate = new Date();
    log.debug("currentDate---->" + currentDate);

    try {
      String stringDate = sdf.format(currentDate);
      log.debug("stringDate---->" + stringDate);


      //Retrieve Billable Transactions
      List<MdtAcOpsDailyBillingEntity> dailyBillingList =
          new ArrayList<MdtAcOpsDailyBillingEntity>();
      HashMap<String, Object> parameters = new HashMap<String, Object>();
      parameters.put("creditorBank", instId);

      dailyBillingList = (List<MdtAcOpsDailyBillingEntity>) genericDAO.findAllByOrderCriteria(
          MdtAcOpsDailyBillingEntity.class, parameters, true, "actionDate");

      //Retrieve TT1
      if (dailyBillingList != null && dailyBillingList.size() > 0) {
        for (MdtAcOpsDailyBillingEntity localEntity : dailyBillingList) {
          String subService = localEntity.getSubService();
          //					log.debug("Billing SubService ==> "+subService);
          //					log.debug("Billing txn status ==> "+localEntity.getTxnStatus());

          if (!(localEntity.getTxnType().equalsIgnoreCase(tt2TxnType))) {
            MandateDailyTransModel localModel = new MandateDailyTransModel();
            localModel = new AdminTranslator().translateDailyBillingEntityToModel(localEntity);
            //						log.debug("localModel ==> "+localModel);
            mndtDailyTransList.add(localModel);
          }
        }
      }
    } catch (ObjectNotFoundException onfe) {
      log.error("No Object Exists on DB");
    } catch (Exception e) {
      log.error("Error on retrieveRealTimeMndtBillingTxnsByCreditor :" + e.getMessage());
      e.printStackTrace();
    }


    return mndtDailyTransList;
  }

  @Override
  public List<?> retrieveRealTimeMndtBillingTxnsByCreditorArcandOps(String instId, Date date) {
    List<MandateDailyTransModel> mndtDailyTransList = new ArrayList<MandateDailyTransModel>();

    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    Date currentDate = new Date();
    log.debug("currentDate---->" + currentDate);

    try {
      String stringDate = sdf.format(currentDate);
      log.debug("stringDate---->" + stringDate);


      //Retrieve Billable Transactions
      List<MdtAcOpsDailyBillingEntity> dailyBillingList =
          new ArrayList<MdtAcOpsDailyBillingEntity>();
      List<MdtAcArcDailyBillingEntity> dailyBillingArcList =
          new ArrayList<MdtAcArcDailyBillingEntity>();
      HashMap<String, Object> parameters = new HashMap<String, Object>();
      parameters.put("creditorBank", instId);
      parameters.put("actionDate", date);


      dailyBillingList = (List<MdtAcOpsDailyBillingEntity>) genericDAO.findAllByNQCriteria(
          "MdtAcOpsDailyBillingEntity.findByCreatedDateSubSTR", parameters);

      dailyBillingArcList = (List<MdtAcArcDailyBillingEntity>) genericDAO.findAllByNQCriteria(
          "MdtAcArcDailyBillingEntity.findByCreatedDateSubSTR", parameters);

      //log.info("dailyBillingList ==> " + dailyBillingList.size());
      //log.info("dailyBillingArcList ==> " + dailyBillingArcList.size());

      //Retrieve TT1
      if (dailyBillingList != null && dailyBillingList.size() > 0) {
        for (MdtAcOpsDailyBillingEntity localEntity : dailyBillingList) {
          String subService = localEntity.getSubService();
          //					log.debug("Billing SubService ==> "+subService);
          //					log.debug("Billing txn status ==> "+localEntity.getTxnStatus());

          if (!(localEntity.getTxnType().equalsIgnoreCase(tt2TxnType))) {
            MandateDailyTransModel localModel = new MandateDailyTransModel();
            localModel = new AdminTranslator().translateDailyBillingEntityToModel(localEntity);
            //						log.debug("localModel ==> "+localModel);
            mndtDailyTransList.add(localModel);
          }
        }
      }

      //Retrieve TT1 From Archive
      if (dailyBillingArcList != null && dailyBillingArcList.size() > 0) {
        for (MdtAcArcDailyBillingEntity localEntity : dailyBillingArcList) {

          if (!(localEntity.getTxnType().equalsIgnoreCase(tt2TxnType))) {
            MandateDailyTransModel localModel = new MandateDailyTransModel();
            localModel = new AdminTranslator().translateDailyBillingArcEntityToModel(localEntity);
            mndtDailyTransList.add(localModel);
          }
        }
      }
    } catch (ObjectNotFoundException onfe) {
      log.error("No Object Exists on DB");
    } catch (Exception e) {
      log.error("Error on retrieveRealTimeMndtBillingTxnsByCreditor :" + e.getMessage());
      e.printStackTrace();
    }


    return mndtDailyTransList;
  }

  @Override
  public List<?> retrieveRejectionCodesForRejectionsReport() {
    List<ReasonCodesModel> reportReasonCodesList = new ArrayList<ReasonCodesModel>();
    ReasonCodesLogic reasonCodesLogic = new ReasonCodesLogic();

    try {
      //Retrieve Pain.012 Codes
      List<MdtCnfgReasonCodesEntity> mdtReasonCodesList =
          genericDAO.findAll(MdtCnfgReasonCodesEntity.class);

      if (mdtReasonCodesList != null && mdtReasonCodesList.size() > 0) {

        for (MdtCnfgReasonCodesEntity reasonEntity : mdtReasonCodesList) {
          ReasonCodesModel reasonModel = new ReasonCodesModel();
          reasonModel = reasonCodesLogic.retrieveReasonCode(reasonEntity);
          reportReasonCodesList.add(reasonModel);
        }
      }


      //Retrieve Mdte.002 Code
      MdtCnfgRejectReasonCodesEntity mdtCnfgRejectReasonCodesEntity =
          (MdtCnfgRejectReasonCodesEntity) genericDAO.findByNamedQuery(
              "MdtCnfgRejectReasonCodesEntity.findByRejectReasonCode", "rejectReasonCode", "NMTC");
      if (mdtCnfgRejectReasonCodesEntity != null) {
        //Translate
        ReasonCodesModel reasonModel = new ReasonCodesModel();
        reasonModel =
            reasonCodesLogic.retrieveReportReasonCode_mdte002(mdtCnfgRejectReasonCodesEntity);
        reportReasonCodesList.add(reasonModel);
      }
    } catch (ObjectNotFoundException onfe) {
      log.debug("No Object Exists on DB");
    } catch (Exception e) {
      log.error("Error on retrieveRejectionCodesForRejectionsReport: " + e.getMessage());
      e.printStackTrace();
    }

    return reportReasonCodesList;
  }


  @Override
  public Object retrieveRealTimeNrOfAmendment(String amendReason, String memberId, String firstDate,
                                              String lastDate) {
    List<AmendmentCodesModel> amendmentCodeList = new ArrayList<AmendmentCodesModel>();
    List<MandateAmendEntityModel> amendmentEntityList = new ArrayList<MandateAmendEntityModel>();
    MandateAmendModel amendmentCodesModel = null;

    StringBuffer sb = new StringBuffer();

    sb.append("SELECT COUNT(*) as amendReasonCodeCount ");
    sb.append("FROM MANOWNER.JNL_ACQ a ");
    sb.append("left outer join MANOWNER.MDT_CNFG_AMENDMENT_CODES b ");
    sb.append("on a.AMENDMENTREASONAMS = b.AMENDMENT_CODES ");
    sb.append("WHERE a.RESULTCODE = 0 AND a.MSGTYPEAMS = 'pain.010' ");
    sb.append(
        "AND TO_DATE(SUBSTR(a.TRANSDATETIME, 1, 8),'YYYYMMDD') BETWEEN TO_DATE('" + firstDate +
            "', 'DDMMYYYY') AND TO_DATE('" + lastDate + "', 'DDMMYYYY') ");
    sb.append(
        "AND INSTRUCTINGAGENTAMS = '" + memberId + "' AND b.AMENDMENT_CODES  = '" + amendReason +
            "' ");


    String sqlQuery = sb.toString();
    log.debug("sqlQuery --->: " + sqlQuery);

    List<String> scalarList = new ArrayList<String>();

    scalarList.add("amendReasonCodeCount");

    amendmentEntityList = genericDAO.findBySql(sqlQuery, scalarList, MandateAmendEntityModel.class);
    if (amendmentEntityList != null && amendmentEntityList.size() > 0) {
      for (MandateAmendEntityModel mandateAmendEntity : amendmentEntityList) {
        amendmentCodesModel = new MandateAmendModel();
        amendmentCodesModel.setAmendReasonCodeCount(mandateAmendEntity.getAmendReasonCodeCount());
      }
    }

    return amendmentCodesModel;
  }


  @Override
  public Object retrievePain012ReasonCodeDataPASA(String reasonCode, String memberId,
                                                  String startDate, String lastDate) {
    List<MandateRejectionModel> mandateReasonCodeModelList = new ArrayList<MandateRejectionModel>();
    List<MandateRejectionEntityModel> mandateReasonCodeEntityList =
        new ArrayList<MandateRejectionEntityModel>();
    MandateRejectionModel mandateReasonCodeModel = new MandateRejectionModel();

    StringBuffer sb = new StringBuffer();

    sb.append("SELECT b.MEMBER_ID, a.REJECT_REASON_CODE, COUNT(*) AS rejectReasonCodeCount ");
    sb.append("FROM MANOWNER.MDT_AC_ARC_MNDT_MSG a ");
    sb.append(
        "LEFT JOIN MANOWNER.MDT_AC_ARC_FIN_INST b ON a.MSG_ID = b.MSG_ID AND a" +
            ".MANDATE_REQ_TRAN_ID = b.MANDATE_REQ_TRAN_ID ");
    sb.append("WHERE b.FIN_INST_TYPE_ID = 'FI03' AND b.MEMBER_ID = '" + memberId + "' ");
    sb.append("AND a.REJECT_REASON_CODE ='" + reasonCode +
        "' AND a.SERVICE_ID ='MANAC' AND TRUNC(a.CREATED_DATE) BETWEEN TO_DATE('" + startDate +
        "','DDMMYYYY') AND TO_DATE('" + lastDate + "','DDMMYYYY') ");
    sb.append("GROUP BY  b.MEMBER_ID,a.REJECT_REASON_CODE ");
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

  @Override
  public Object retrieveMdte002RejectReasonDataPASA(String rejectReasonCode, String memberId,
                                                    String firstDate, String lastDate) {
    List<MandateRejectionModel> mandateRejectionModelList = new ArrayList<MandateRejectionModel>();
    List<MandateRejectionEntityModel> mandateRejectionEntityList =
        new ArrayList<MandateRejectionEntityModel>();
    MandateRejectionModel mandateRejectionModel = new MandateRejectionModel();


    StringBuffer sb = new StringBuffer();

    sb.append(
        "SELECT DR_BANK_MEMBER_ID,CR_BANK_MEMBER_ID,  REJECT_REASON, COUNT(*) AS " +
            "rejectReasonCodeCount ");
    sb.append("FROM MANOWNER.MDT_AC_ARC_MDTE_RESP ");
    sb.append("WHERE CR_BANK_MEMBER_ID ='" + memberId + "' AND ");
    sb.append(
        "REJECT_REASON = '" + rejectReasonCode + "' AND TRUNC(CREATE_DATE_TIME) BETWEEN TO_DATE('" +
            firstDate + "','DDMMYYYY') AND TO_DATE('" + lastDate + "','DDMMYYYY') ");
    sb.append("GROUP BY DR_BANK_MEMBER_ID , CR_BANK_MEMBER_ID,REJECT_REASON");

    String sqlQuery = sb.toString();
    log.debug("sqlQuery  mdte002--->: " + sqlQuery);

    List<String> scalarList = new ArrayList<String>();

    scalarList.add("rejectReasonCodeCount");
    log.debug("rejectReasonCodeCount----->" + scalarList);

    mandateRejectionEntityList =
        genericDAO.findBySql(sqlQuery, scalarList, MandateRejectionEntityModel.class);
    if (mandateRejectionEntityList != null && mandateRejectionEntityList.size() > 0) {
      for (MandateRejectionEntityModel mandateRejectionEntityModel : mandateRejectionEntityList) {
        mandateRejectionModel = new MandateRejectionModel();
        mandateRejectionModel =
            new ServiceTranslator().translateMandateRejectionEntityModelToCommonsModel(
                mandateRejectionEntityModel);
        mandateRejectionModelList.add(mandateRejectionModel);
      }
    }

    log.debug("mandateRejectionModel----->" + mandateRejectionModel);
    return mandateRejectionModel;
  }

  @Override
  public List<?> viewOpsSlaServicesWithoutCisSodEod(String cis, String sod, String eod) {
    List<OpsSlaTimesCommonsModel> opsSlaTimesCommonsList = new ArrayList<OpsSlaTimesCommonsModel>();
    List<MdtOpsSlaTimesEntity> slaTimesEntityList = new ArrayList<MdtOpsSlaTimesEntity>();
    try {
      HashMap<String, Object> parameters = new HashMap<String, Object>();
      parameters.put("service1", cis);
      parameters.put("service2", sod);
      parameters.put("service3", eod);

      //			log.info("*****slaTimesEntityList before
      //			findAllByNQCriteria********-------------------->>> " + slaTimesEntityList);
      //			log.info("parameters---> "+parameters.toString());
      //			slaTimesEntityList = genericDAO.findAllByNQCriteria(MdtOpsSlaTimesEntity
      //			.class, parameters);
      slaTimesEntityList =
          genericDAO.findAllByNQCriteria("MdtOpsSlaTimesEntity.findByManyServicesNotIn",
              parameters);
      //			log.info("---------------slaTimesEntityList after findAllByNQCriteria:
      //			------------------" + slaTimesEntityList);
      if (slaTimesEntityList != null && slaTimesEntityList.size() > 0) {
        OpsSlaTimesLogic opsSlaTimesLogic = new OpsSlaTimesLogic();
        for (MdtOpsSlaTimesEntity localEntity : slaTimesEntityList) {
          //					log.info("Inside Method in AdminBean inside For LOOP
          //					--------------------------------------------------********************");
          OpsSlaTimesCommonsModel opsSlaTimesCommonsModel = new OpsSlaTimesCommonsModel();
          opsSlaTimesCommonsModel = opsSlaTimesLogic.retrieveOpsSlaTimes(localEntity);
          opsSlaTimesCommonsList.add(opsSlaTimesCommonsModel);
          //					log.info("Commons model inside
//					AdminBean-------------------->>>>>>>>>>>>>>>>>>>>>>" +
          //					opsSlaTimesCommonsModel);
        }
      }
    } catch (Exception e) {
      log.error(
          "--------------------Error on " +
              "viewOpsSlaServicesWithoutCisSodEod------------------------->>> : " +
              e.getMessage());
      e.printStackTrace();
    }
    return opsSlaTimesCommonsList;
  }


  @Override
  public boolean saveOrUpdateExtendSlaTimes(Object obj) {
    try {
      if (obj instanceof OpsSlaTimesCommonsModel) {
        OpsSlaTimesCommonsModel slaTimesModel = (OpsSlaTimesCommonsModel) obj;
        OpsSlaTimesLogic slaTimesLogic = new OpsSlaTimesLogic();
        MdtOpsSlaTimesEntity slaTimesEntity = new MdtOpsSlaTimesEntity();
        slaTimesEntity = slaTimesLogic.addOpsSlaTimes(slaTimesModel);
        genericDAO.saveOrUpdate(slaTimesEntity);
        return true;
      } else {
        log.debug("Unable to convert type to OpsSlaTimesCommonsModel.");
        return false;
      }
    } catch (Exception e) {
      log.error("Error on saveOrUpdateExtendSlaTimes: " + e.getMessage());
      e.printStackTrace();
      return false;
    }
  }

  @Override
  public Object retrieveOpsSlaTimes(String service) {
    OpsSlaTimesCommonsModel opsSlaTimesCommonsModel = new OpsSlaTimesCommonsModel();
    MdtOpsSlaTimesEntity opsSlaTimesEntity = new MdtOpsSlaTimesEntity();
    try {
      opsSlaTimesEntity =
          (MdtOpsSlaTimesEntity) genericDAO.findByNamedQuery("MdtOpsSlaTimesEntity.findByService",
              "service", service);
      if (opsSlaTimesEntity != null) {
        OpsSlaTimesLogic opsSlaTimesLogic = new OpsSlaTimesLogic();
        opsSlaTimesCommonsModel = opsSlaTimesLogic.retrieveOpsSlaTimes(opsSlaTimesEntity);
        log.info("opsSlaTimesCommonsModel (Admin Bean) ==> " + opsSlaTimesCommonsModel);
      }
    } catch (ObjectNotFoundException onfe) {
      log.error("No Object Exists on DB");
    } catch (Exception e) {
      log.error("Error on retrieveOpsSlaTimes: " + e.getMessage());
      e.printStackTrace();
    }
    return opsSlaTimesCommonsModel;
  }

  @Override
  public Object retrieveOnlinePASARejectionSummaryPAIN012(String reasonCode, String crBank,
                                                          String firstDate, String lastDate) {

    List<MandateRejectionModel> mandateReasonCodeModelList = new ArrayList<MandateRejectionModel>();
    List<MandateRejectionEntityModel> mandateReasonCodeEntityList =
        new ArrayList<MandateRejectionEntityModel>();
    MandateRejectionModel mandateReasonCodeModel = new MandateRejectionModel();

    StringBuffer sb = new StringBuffer();

    sb.append(
        "SELECT a.INSTRUCTINGAGENTAMS  ,a.rejectedreasoncodeams ,count(*) as " +
            "rejectReasonCodeCount ");
    sb.append("from manowner.jnl_acq a ");
    sb.append("left join manowner.mdt_cnfg_reason_codes b ");
    sb.append("on a.rejectedreasoncodeams = b.reason_code ");
    //2018-12-20-SalehaR - CHG-152647: Remove MsgType check
    //		sb.append("where a.rejectedreasoncodeams > '0' and a.msgtypeams not in('pain.009',
    //		'pain.011', 'pain.010') and a.INSTRUCTINGAGENTAMS ='"+crBank+"' and ");
    sb.append(
        "where a.rejectedreasoncodeams > '0' and a.INSTRUCTINGAGENTAMS ='" + crBank + "' and ");
    sb.append("b.reason_code ='" + reasonCode +
        "' and to_date(substr(a.TRANSDATETIME,1,8), 'yyyymmdd') between to_date('" + firstDate +
        "', 'yyyymmdd') and to_date('" + lastDate + "', 'yyyymmdd') ");
    sb.append("group by a.INSTRUCTINGAGENTAMS, a.rejectedreasoncodeams ");
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

  @Override
  public Object retrieveOnlinePain012ReasonCodeDataPerBank(String reasonCode, String memberId,
                                                           String debtorBankId, String startDate,
                                                           String lastDate) {
    List<MandateRejectionModel> mandateReasonCodeModelList = new ArrayList<MandateRejectionModel>();
    List<MandateRejectionEntityModel> mandateReasonCodeEntityList =
        new ArrayList<MandateRejectionEntityModel>();
    MandateRejectionModel mandateReasonCodeModel = new MandateRejectionModel();

    StringBuffer sb = new StringBuffer();

    sb.append(
        "select a.INSTRUCTINGAGENTAMS,a.INSTRUCTEDAGENTAMS,b.reason_code,count(*) AS " +
            "rejectReasonCodeCount ");
    sb.append("from manowner.jnl_acq a ");
    sb.append("left outer join manowner.MDT_CNFG_REASON_CODES b ");
    sb.append("on a.rejectedreasoncodeams = b.reason_CODE ");
    sb.append(
        "where acceptedindicatorams = 'false' and rejectedreasoncodeams > '0' and a" +
            ".INSTRUCTINGAGENTAMS ='" +
            memberId + "' and a.INSTRUCTEDAGENTAMS = '" + debtorBankId + "' and ");
    sb.append("b.reason_CODE = '" + reasonCode +
        "' and TO_DATE(SUBSTR(a.TRANSDATETIME, 1, 8), 'YYYYMMDD') BETWEEN TO_DATE('" + startDate +
        "', 'DDMMYYYY') AND TO_DATE('" + lastDate + "', 'DDMMYYYY') ");
    sb.append("group by a.INSTRUCTINGAGENTAMS,a.INSTRUCTEDAGENTAMS,b.reason_code ");

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

  public Object retrieveReportName(String reportNr) {
    MdtCnfgReportNamesEntity reportNameEntity = null;

    try {
      reportNameEntity = (MdtCnfgReportNamesEntity) genericDAO.findByNamedQuery(
          "MdtCnfgReportNamesEntity.findByReportNr", "reportNr", reportNr);
    } catch (Exception ex) {
      log.error("Error on retrieveReportName: " + ex.getMessage());
    }

    return reportNameEntity;
  }


  @Override
  public Object retrieveEndTime(String outService) {


    MdtOpsSlaTimesEntity mdtOpsSlaTimesEntity;
    mdtOpsSlaTimesEntity =
        (MdtOpsSlaTimesEntity) genericDAO.findByNamedQuery("MdtOpsSlaTimesEntity.findByService",
            "service", outService);

    return mdtOpsSlaTimesEntity;

		/*
		OpsSlaTimesCommonsModel opsSlaTimesCommonsModel = new OpsSlaTimesCommonsModel();
		MdtOpsSlaTimesEntity mdtOpsSlaTimesEntity = new MdtOpsSlaTimesEntity();

		try{
			mdtOpsSlaTimesEntity = (MdtOpsSlaTimesEntity) genericDAO.findByNamedQuery
			("MdtOpsSlaTimesEntity.findByService","service",outService);

		if(mdtOpsSlaTimesEntity!= null)
		{
			OpsSlaTimesLogic  opsSlaTimesLogic = new OpsSlaTimesLogic();
			opsSlaTimesCommonsModel = opsSlaTimesLogic.retrieveOpsSlaTimes(mdtOpsSlaTimesEntity);
			log.info("OpsSlaTimesCommonsModel ==> "+opsSlaTimesCommonsModel);

		}
		}
		catch (ObjectNotFoundException onfe)
		{
			log.error("No Object Exists on DB");
		}
		catch (Exception e)
		{
			log.error("Error on retrieveEndTime: " + e.getMessage());
			e.printStackTrace();
		}

		return opsSlaTimesCommonsModel ;
	}
		 */

  }

  public void runDailyReports(String userName) {
    DailyReportsLogic dailyReportLogic = new DailyReportsLogic();

    try {
      dailyReportLogic.generateDailyReports();
    } catch (Exception e) {
      log.error("Error on population of reports " + e.getMessage());
      e.printStackTrace();
    }
  }

  public void runMonthlyReports(String userName) {
    MonthlyReportsLogic monthlyReportLogic = new MonthlyReportsLogic();

    try {
      monthlyReportLogic.generateMonthlyReports();
    } catch (Exception e) {
      log.error("Error on population of monthly reports " + e.getMessage());
      e.printStackTrace();
    }
  }


  @Override
  public List<?> viewSystemStatusSearch(String memberNo, String service) {
    List<MandatesCountCommonsModel> searchedFileList = new ArrayList<MandatesCountCommonsModel>();
    List<MdtAcOpsMndtCountEntity> mdtAcCountEntityList = new ArrayList<MdtAcOpsMndtCountEntity>();
    log.info("mdtAcCountEntityList in ADMINBEAN BEFORE NAMED QUERY ----->" + mdtAcCountEntityList);
    log.info("searchedFileList in ADMINBEAN BEFORE NAMED QUERY ----->" + searchedFileList);
    try {
      HashMap<String, Object> parameters = new HashMap<String, Object>();
      parameters.put("fileName", "%" + service + "%");
      parameters.put("fileName2", "%" + memberNo + "%");
      mdtAcCountEntityList =
          genericDAO.findAllByNQCriteria("MdtAcOpsMndtCountEntity.findByFileNameLike2", parameters);
      log.info("mdtAcCountEntityList in ADMINBEAN AFTER NAMED QUERY ----->" + mdtAcCountEntityList);
      log.info("searchedFileList in ADMINBEAN AFTER NAMED QUERY ----->" + searchedFileList);
      if (mdtAcCountEntityList != null && mdtAcCountEntityList.size() > 0) {
        SystemStatusLogic systemStatusLogic = new SystemStatusLogic();
        for (MdtAcOpsMndtCountEntity localEntity : mdtAcCountEntityList) {
          MandatesCountCommonsModel localModel = new MandatesCountCommonsModel();
          localModel = systemStatusLogic.retrieveSystemStatusFile(localEntity);
          searchedFileList.add(localModel);
          //					log.info("mdtAcCountEntityList in ADMINBEAN AFTER NAMED QUERY
          //					----->" + mdtAcCountEntityList);
          //					log.info("searchedFileList in ADMINBEAN AFTER NAMED QUERY ----->"
          //					+ searchedFileList);
        }
      }
    } catch (ObjectNotFoundException onfe) {
      log.error("No Object Exists on DB");
    } catch (Exception e) {
      log.error("Error on viewSystemStatusSearch in ADMINBEAN " + e.getMessage());
      e.printStackTrace();
    }
    return searchedFileList;
  }

  public List<?> retrieveOpsReportSeqNr() {
    List<MdtOpsRepSeqNrEntity> opsReportSeqNrList = new ArrayList<MdtOpsRepSeqNrEntity>();
    try {
      opsReportSeqNrList = genericDAO.findAll(MdtOpsRepSeqNrEntity.class);
    } catch (Exception ex) {
      log.error("Error on retrieveOpsReportSeqNr, " + ex.getMessage());
      ex.printStackTrace();

    }

    return opsReportSeqNrList;
  }

  @Override
  public boolean createOpsReportSeqNr(Object obj) {
    if (obj instanceof MdtOpsRepSeqNrEntity) {
      MdtOpsRepSeqNrEntity mdtOpsReportSeqNrEntity = (MdtOpsRepSeqNrEntity) obj;
      try {
        genericDAO.save(mdtOpsReportSeqNrEntity);
        return true;
      } catch (Exception ex) {
        log.error("Error on createOpsReportSeqNr: " + ex.getMessage());
        return false;
      }
    } else {
      return false;
    }
  }

  public List<?> retrieveActiveReportNr() {
    List<MdtCnfgReportNamesEntity> reportNrEntityList = new ArrayList<MdtCnfgReportNamesEntity>();
    reportNrEntityList = (List<MdtCnfgReportNamesEntity>) genericDAO.findAllByNamedQuery(
        "MdtCnfgReportNamesEntity.findByActiveInd", "activeInd", "Y");
    log.debug("reportNrEntityList in AdminBean ---> " + reportNrEntityList);

    return reportNrEntityList;
  }


  @Override
  public Object retrieveRepSeqNr(String reportNr, String memberId) {
    MdtOpsRepSeqNrEntity mdtOpsRepSeqNrEntity = null;

    try {
      //			log.info("reportNo in repSeqNr: "+reportNr);
      //			log.info("memberNo in repSeqNr: "+memberId);

      HashMap<String, Object> parameters = new HashMap<String, Object>();
      parameters.put("mdtOpsRepSeqNrPK.memberNo", memberId);
      parameters.put("mdtOpsRepSeqNrPK.reportNo", reportNr);
      log.debug("---------------sparameters: ------------------" + parameters.toString());
      mdtOpsRepSeqNrEntity =
          (MdtOpsRepSeqNrEntity) genericDAO.findByCriteria(MdtOpsRepSeqNrEntity.class, parameters);
      log.debug("---------------MdtOpsRefSeqNrEntity after findByCriteria: ------------------" +
          mdtOpsRepSeqNrEntity);

      //mdtOpsFileSeqNrEntity = (MdtOpsRefSeqNrEntity) genericDAO.findByNamedQuery
      // ("MdtOpsRefSeqNrEntity.findByServiceId","mdtOpsRefSeqNrPK.serviceId", serviceId);
    } catch (ObjectNotFoundException onfe) {
      log.error("No Object Found:" + onfe.getMessage());
    } catch (Exception e) {
      log.error("Error on retrieveFileSeqNo:" + e.getMessage());
    }

    return mdtOpsRepSeqNrEntity;

  }

  @Override
  public boolean updateReportSeqNr(Object obj) {
    if (obj instanceof MdtOpsRepSeqNrEntity) {
      MdtOpsRepSeqNrEntity mdtOpsRepSeqNrEntity = (MdtOpsRepSeqNrEntity) obj;
      try {
        genericDAO.saveOrUpdate(mdtOpsRepSeqNrEntity);
        return true;
      } catch (Exception ex) {
        log.error("Error on updateReportSeqNr: " + ex.getMessage());
        return false;
      }
    } else {
      return false;
    }
  }


  public List<?> retrieveActiveErrorCodes(String activeInd) {
    List<MdtCnfgErrorCodesEntity> errorCodesList = null;

    try {
      errorCodesList =
          genericDAO.findAllByNamedQuery("MdtCnfgErrorCodesEntity.findByActiveInd", "activeInd",
              activeInd);
    } catch (ObjectNotFoundException onfe) {
      log.error("No Object Exists on DB");
    } catch (Exception e) {
      log.error("Error on retrieveErrorCodeByCriteria: " + e.getMessage());
      e.printStackTrace();
    }
    return errorCodesList;
  }


  @Override
  public List<?> retrieveAllIncSotEot() {
    List<IncSotEotModel> soteotModelList = new ArrayList<IncSotEotModel>();
    List<IncSotEotEntityModel> sotEotEntityList = new ArrayList<IncSotEotEntityModel>();

    StringBuffer sb = new StringBuffer();
    sb.append(
        "select a.service_id as serviceId, a.inst_id as instId, a.sot_in as sotIn, a.eot_in as " +
            "eotIn ");
    sb.append("from mdt_ac_ops_sot_eot_ctrl a, mdt_ops_services b ");
    sb.append("where a.service_id = b.service_id_in ");
    String sqlQuery = sb.toString();
    log.debug("sqlQuery:" + sqlQuery);
    log.info("sqlQuery (ADMINBEAN):" + sqlQuery);
    List<String> scalarList = new ArrayList<String>();
    scalarList.add("serviceId");
    scalarList.add("instId");
    scalarList.add("sotIn");
    scalarList.add("eotIn");
    log.debug("scalarList: " + scalarList);
    log.info("scalarList (ADMINBEAN): " + scalarList);
    try {
      sotEotEntityList = genericDAO.findBySql(sqlQuery, scalarList, IncSotEotEntityModel.class);
      log.info("sotEotEntityList.size in retrieveIncSotEotServiceId (ADMINBEAN)----->" +
          sotEotEntityList.size());
      if (sotEotEntityList.size() > 0) {
        for (IncSotEotEntityModel localEntity : sotEotEntityList) {
          IncSotEotModel localModel = new IncSotEotModel();
          localModel =
              new ServiceTranslator().translateIncAcOpsSotEotEntityModelToCommonsModel(localEntity);
          soteotModelList.add(localModel);
        }
      }
    } catch (ObjectNotFoundException onfe) {
      log.error("No Object Exists on DB");
    } catch (Exception e) {
      log.error("Error on retrieveIncSotEotServiceId (ADMINBEAN) :" + e.getMessage());
      e.printStackTrace();
    }
    log.debug("retrieveIncSotEotServiceId (ADMINBEAN): " + soteotModelList);
    return soteotModelList;
  }

  @Override
  public List<?> findByServiceIdNotNull(String namedQuery, String parameter, String value) {
    List<ServicesModel> servModel = new ArrayList<ServicesModel>();
    try {
      log.info("namedQuery in AdminBean ==> " + namedQuery);
      log.info("parameter in AdminBean ==> " + parameter);
      log.info("value in AdminBean ==> " + value);

      List<MdtOpsServicesEntity> servEntity =
          genericDAO.findAllByNamedQuery(namedQuery, parameter, value);
      if (servEntity.size() > 0) {
        ServicesLogic servLogic = new ServicesLogic();
        servModel = servLogic.retrieveIncomingServices(servEntity);
      }
    } catch (NullPointerException npe) {
      log.error("NullPointer exception :" + npe.getMessage());
    } catch (ObjectNotFoundException onfe) {
      log.error("No Object Exists on DB");
    } catch (Exception e) {
      log.error("Error on findByServiceIdNotNull: " + e.getMessage());
      e.printStackTrace();
    }
    return servModel;
  }

  @Override
  public List<?> findByServiceIdOutNotNull(String namedQuery, String parameter, String value) {
    List<ServicesModel> servModel = new ArrayList<ServicesModel>();
    try {
      log.info("namedQuery in AdminBean ==> " + namedQuery);
      log.info("parameter in AdminBean ==> " + parameter);
      log.info("value in AdminBean ==> " + value);

      List<MdtOpsServicesEntity> servEntity =
          genericDAO.findAllByNamedQuery(namedQuery, parameter, value);
      if (servEntity.size() > 0) {
        ServicesLogic servLogic = new ServicesLogic();
        servModel = servLogic.retrieveOutgoingServices(servEntity);
      }
    } catch (NullPointerException npe) {
      log.error("NullPointer exception :" + npe.getMessage());
    } catch (ObjectNotFoundException onfe) {
      log.error("No Object Exists on DB");
    } catch (Exception e) {
      log.error("Error on findByServiceIdNotNull: " + e.getMessage());
      e.printStackTrace();
    }
    return servModel;
  }

  public List<?> retrieveAllIncSotEotBySearch(String instId, String serviceId, String sotInInd,
                                              String eotInInd) {
    List<IncSotEotModel> soteotModelList = new ArrayList<IncSotEotModel>();
    List<IncSotEotEntityModel> sotEotEntityList = new ArrayList<IncSotEotEntityModel>();

    StringBuffer sb = new StringBuffer();
    sb.append(
        "select a.service_id as serviceId, a.inst_id as instId, a.sot_in as sotIn, a.eot_in as " +
            "eotIn ");
    sb.append("from mdt_ac_ops_sot_eot_ctrl a ");

    if (instId != null || serviceId != null || sotInInd != null || eotInInd != null) {
      sb.append("where ");
      int pos = 0;
      //Build up search criteria in Query

      if (instId != null) {
        sb.append("a.inst_id = '" + instId + "' ");
        pos++;
      }

      if (serviceId != null) {
        if (pos != 0) {
          sb.append("AND ");
        }
        sb.append("a.service_id = '" + serviceId + "' ");
        pos++;
      }

      if (sotInInd != null) {
        if (pos != 0) {
          sb.append("AND ");
        }
        sb.append("a.sot_in = '" + sotInInd + "' ");
        pos++;
      }

      if (eotInInd != null) {
        if (pos != 0) {
          sb.append("AND ");
        }
        sb.append("a.eot_in = '" + eotInInd + "' ");
        pos++;
      }
    } else {
      sb.append("where a.service_id = b.service_id_in ");
    }

    String sqlQuery = sb.toString();
    log.debug("sqlQuery:" + sqlQuery);
    log.info("sqlQuery (ADMINBEAN):" + sqlQuery);
    List<String> scalarList = new ArrayList<String>();
    scalarList.add("serviceId");
    scalarList.add("instId");
    scalarList.add("sotIn");
    scalarList.add("eotIn");
    log.debug("scalarList: " + scalarList);
    log.info("scalarList (ADMINBEAN): " + scalarList);
    try {
      sotEotEntityList = genericDAO.findBySql(sqlQuery, scalarList, IncSotEotEntityModel.class);
      log.info("sotEotEntityList.size in retrieveIncSotEotServiceId (ADMINBEAN)----->" +
          sotEotEntityList.size());
      if (sotEotEntityList.size() > 0) {
        for (IncSotEotEntityModel localEntity : sotEotEntityList) {
          IncSotEotModel localModel = new IncSotEotModel();
          localModel =
              new ServiceTranslator().translateIncAcOpsSotEotEntityModelToCommonsModel(localEntity);
          soteotModelList.add(localModel);
        }
      }
    } catch (ObjectNotFoundException onfe) {
      log.error("No Object Exists on DB");
    } catch (Exception e) {
      log.error("Error on retrieveIncSotEotServiceId (ADMINBEAN) :" + e.getMessage());
      e.printStackTrace();
    }
    log.debug("retrieveIncSotEotServiceId (ADMINBEAN): " + soteotModelList);
    return soteotModelList;
  }

  public List<?> retrieveAllOutSotEotBySearch(String instId, String serviceId, String sotOutInd,
                                              String eotOutInd) {
    List<OutSotEotModel> soteotModelList = new ArrayList<OutSotEotModel>();
    List<OutSotEotEntityModel> sotEotEntityList = new ArrayList<OutSotEotEntityModel>();

    StringBuffer sb = new StringBuffer();
    sb.append(
        "select a.service_id as serviceId, a.inst_id as instId, a.sot_out as sotOut, a.eot_out as" +
            " eotOut ");
    sb.append("from mdt_ac_ops_sot_eot_ctrl a ");

    if (instId != null || serviceId != null || sotOutInd != null || eotOutInd != null) {
      sb.append("where ");
      int pos = 0;
      //Build up search criteria in Query

      if (instId != null) {
        sb.append("a.inst_id = '" + instId + "' ");
        pos++;
      }

      if (serviceId != null) {
        if (pos != 0) {
          sb.append("AND ");
        }
        sb.append("a.service_id = '" + serviceId + "' ");
        pos++;
      }

      if (sotOutInd != null) {
        if (pos != 0) {
          sb.append("AND ");
        }
        sb.append("a.sot_out = '" + sotOutInd + "' ");
        pos++;
      }

      if (eotOutInd != null) {
        if (pos != 0) {
          sb.append("AND ");
        }
        sb.append("a.eot_out = '" + eotOutInd + "' ");
        pos++;
      }
    } else {
      sb.append("where a.service_id = b.service_id_in ");
    }

    String sqlQuery = sb.toString();
    log.debug("sqlQuery:" + sqlQuery);
    log.info("sqlQuery (ADMINBEAN):" + sqlQuery);
    List<String> scalarList = new ArrayList<String>();
    scalarList.add("serviceId");
    scalarList.add("instId");
    scalarList.add("sotOut");
    scalarList.add("eotOut");
    log.debug("scalarList: " + scalarList);
    log.info("scalarList (ADMINBEAN): " + scalarList);
    try {
      sotEotEntityList = genericDAO.findBySql(sqlQuery, scalarList, OutSotEotEntityModel.class);
      log.info("sotEotEntityList.size in retrieveAllOutSotEotBySearch (ADMINBEAN)----->" +
          sotEotEntityList.size());
      if (sotEotEntityList.size() > 0) {
        for (OutSotEotEntityModel localEntity : sotEotEntityList) {
          OutSotEotModel localModel = new OutSotEotModel();
          localModel =
              new ServiceTranslator().translateOutAcOpsSotEotEntityModelToCommonsModel(localEntity);
          soteotModelList.add(localModel);
        }
      }
    } catch (ObjectNotFoundException onfe) {
      log.error("No Object Exists on DB");
    } catch (Exception e) {
      log.error("Error on retrieveAllOutSotEotBySearch (ADMINBEAN) :" + e.getMessage());
      e.printStackTrace();
    }
    log.debug("retrieveAllOutSotEotBySearch (ADMINBEAN): " + soteotModelList);
    return soteotModelList;
  }

  @Override
  public List<?> retrieveAllOutSotEot() {
    List<OutSotEotModel> soteotModelList = new ArrayList<OutSotEotModel>();
    List<OutSotEotEntityModel> sotEotEntityList = new ArrayList<OutSotEotEntityModel>();

    StringBuffer sb = new StringBuffer();
    sb.append(
        "select a.service_id as serviceId, a.inst_id as instId, a.sot_out as sotOut, a.eot_out as" +
            " eotOut ");
    sb.append("from mdt_ac_ops_sot_eot_ctrl a, mdt_ops_services b ");
    sb.append("where a.service_id = b.service_id_out ");
    String sqlQuery = sb.toString();
    log.debug("sqlQuery:" + sqlQuery);
    log.info("sqlQuery (ADMINBEAN):" + sqlQuery);
    List<String> scalarList = new ArrayList<String>();
    scalarList.add("serviceId");
    scalarList.add("instId");
    scalarList.add("sotOut");
    scalarList.add("eotOut");
    log.debug("scalarList: " + scalarList);
    log.info("scalarList (ADMINBEAN): " + scalarList);
    try {
      sotEotEntityList = genericDAO.findBySql(sqlQuery, scalarList, OutSotEotEntityModel.class);
      log.info("sotEotEntityList.size in retrieveOutSotEotServiceId (ADMINBEAN)----->" +
          sotEotEntityList.size());
      if (sotEotEntityList != null && sotEotEntityList.size() > 0) {
        for (OutSotEotEntityModel localEntity : sotEotEntityList) {
          OutSotEotModel localModel = new OutSotEotModel();
          localModel =
              new ServiceTranslator().translateOutAcOpsSotEotEntityModelToCommonsModel(localEntity);
          soteotModelList.add(localModel);
        }
      }
    } catch (ObjectNotFoundException onfe) {
      log.error("No Object Exists on DB");
    } catch (Exception e) {
      log.error("Error on retrieveOutSotEotServiceId (ADMINBEAN) :" + e.getMessage());
      e.printStackTrace();
    }
    log.debug("retrieveOutSotEotServiceId (ADMINBEAN): " + soteotModelList);
    return soteotModelList;
  }

  @Override
  public Object retrieveErrCodeReportName(String reportNr) {
    ReportsNamesModel reportsNamesModel = new ReportsNamesModel();
    MdtCnfgReportNamesEntity mdtCnfgReportNamesEntity = new MdtCnfgReportNamesEntity();
    try {
      mdtCnfgReportNamesEntity = (MdtCnfgReportNamesEntity) genericDAO.findByNamedQuery(
          "MdtCnfgReportNamesEntity.findByReportNr", "reportNr", reportNr);
      if (mdtCnfgReportNamesEntity != null) {
        ReportsLogic reportsLogic = new ReportsLogic();
        reportsNamesModel = reportsLogic.retrieveReportNames(mdtCnfgReportNamesEntity);
        log.info("reportsNamesModel (Admin Bean) ==> " + reportsNamesModel);
      }
    } catch (ObjectNotFoundException onfe) {
      log.error("No Object Exists on DB");
    } catch (Exception e) {
      log.error("Error on retrieveErrCodeReportName: " + e.getMessage());
      e.printStackTrace();
    }
    return reportsNamesModel;
  }

  public void generatePasaBatchRejections(Date frontDate) {
    //PSMD04 - PASA BATCH REJECTIONS	REPORTS
    try {
      log.info("***********Generating PSMD04 Report*****************");
      PasaBatchRejectionsReport pasaBatchRejectionsReport = new PasaBatchRejectionsReport();
      pasaBatchRejectionsReport.generateReport(frontDate);
      log.info("***********PSMD04 Report Completed*****************");
    } catch (FileNotFoundException e) {
      log.error("<FE> Error on populating PSMD04 Report :" + e.getMessage());
      e.printStackTrace();
    } catch (DocumentException e) {
      log.error("<DE> Error on populating PSMD04 Report :" + e.getMessage());
      e.printStackTrace();
    } catch (Exception e) {
      log.error("<EX> Error on populating PSMD04 Report :" + e.getMessage());
      e.printStackTrace();
    }
  }

  public void generatePasaBatchAmendmentsReport(Date frontFromDate, Date frontToDate) {
    //		PSMD06 - PASA BATCH AMENDMENT REPORTS
    try {
      log.info("***********Generating PSMD06 Report*****************");
      PasaBatchAmendmentReport pasaBatchAmendmentReport = new PasaBatchAmendmentReport();
      pasaBatchAmendmentReport.generateReport(frontFromDate, frontToDate);
      log.info("***********PSMD06 Report Completed*****************");
    } catch (FileNotFoundException e) {
      log.error("<FE> Error on populating PSMD06 Report :" + e.getMessage());
      e.printStackTrace();
    } catch (DocumentException e) {
      log.error("<DE> Error on populating PSMD06 Report :" + e.getMessage());
      e.printStackTrace();
    } catch (Exception e) {
      log.error("<EX> Error on populating PSMD06 Report :" + e.getMessage());
      e.printStackTrace();
    }
  }

  public void generatePerBankBatchMandateRejections() {
    try {
      log.info("***********Generating PBMD03 Report*****************");
      PartBanksBatchRejectionsReport pasaBanksBatchRejectionsReport =
          new PartBanksBatchRejectionsReport();
      pasaBanksBatchRejectionsReport.generateMndRejectBatchPerBankReport();
      log.info("***********PBMD03 Report Completed*****************");
    } catch (Exception e) {
      log.error("<EX> Error on populating PBMD03 Report :" + e.getMessage());
      e.printStackTrace();
    }
  }

  public void generatePasaBatchOutstandingResponses(Date frontDate) {
    //PSMD01 - Daily BATCH Outstanding Responses PASA
    try {
      log.info("***********Generating PSMD01 Report*****************");
      PasaBatchOutstandingResponsesReport pasaBatchOutstandingResponsesReports =
          new PasaBatchOutstandingResponsesReport();
      pasaBatchOutstandingResponsesReports.generateReport(frontDate);
      log.info("***********PSMD01 Report Completed*****************");
    } catch (Exception e) {
      log.error("<EX> Error on populating PSMD01 Report :" + e.getMessage());
      e.printStackTrace();
    }
  }

  public void generatePerBankOutstandingResponses(Date frontEndDate) {
    TreeMap<String, List<MandateResponseOutstandingPerBankEntityModel>> pbmd01Map = null;
    //PBMD01 - Daily Outstanding Responses Per Bank - PDF
    try {
      log.info("***********Generating PBMD01 PDF Report*****************");
      PBMD01_DailyOutstandingResponsesReportPDF dailyOutstandingResponsesReportPDF =
          new PBMD01_DailyOutstandingResponsesReportPDF();
      dailyOutstandingResponsesReportPDF.generateMandateResponseOutstandingPerBank(frontEndDate);
      log.info("***********PBMD01 PDF Report Completed*****************");
      pbmd01Map = dailyOutstandingResponsesReportPDF.pbmd01Map;
    } catch (Exception e) {
      log.error("<EX> Error on populating PBMD01 PDF Report :" + e.getMessage());
      e.printStackTrace();
    }

    //PBMD01 - Daily Outstanding Responses Per Bank - CSV
    try {
      log.info("***********Generating PBMD01 CSV Report*****************");
      PBMD01_DailyOutstandingResponsesReportCSV dailyOutstandingResponsesReportCSV =
          new PBMD01_DailyOutstandingResponsesReportCSV(pbmd01Map);
      dailyOutstandingResponsesReportCSV.generateMandateResponseOutstandingPerBankCsv(frontEndDate);
      log.info("***********PBMD01 CSV Report Completed*****************");
    } catch (Exception e) {
      log.error("<EX> Error on populating PBMD01 CSV Report :" + e.getMessage());
      e.printStackTrace();
    }
  }


  public void generateBatchBillableTxnCreditor() {
    //PBMD04 - Daily Batch Billable Transaction Report Per Creditor
    try {
      log.info("***********Generating PBMD04 Report*****************");
      DailyBatchBillableTxnCreditorReport dailyBatchBillableTxnCreditorReport =
          new DailyBatchBillableTxnCreditorReport();
      dailyBatchBillableTxnCreditorReport.generateMandateDailyTransCreditorReport();
      log.info("***********PBMD04 Report Completed*****************");
    } catch (Exception e) {
      log.error("<EX> Error on populating PBMD04 Report :" + e.getMessage());
      e.printStackTrace();
    }
  }

  public void generateBatchBillableTxnDebtor() {
    try {
      log.info("***********Generating PBMD05 Report*****************");
      DailyBatchBillableTxnDebtorReport dailyBatchBillableTxnDebtorReport =
          new DailyBatchBillableTxnDebtorReport();
      dailyBatchBillableTxnDebtorReport.generateMandateDailyTransDebtorReport();
      log.info("***********PBMD05 Report Completed*****************");
    } catch (Exception e) {
      log.error("<EX> Error on populating PBMD05 Report :" + e.getMessage());
      e.printStackTrace();
    }
  }

  public void generateBatchBillableTxnReport() {
    //MR018 - Daily Batch Billable Transaction Report
    try {
      log.info("***********Generating MR018 Report*****************");
      DailyBatchBillableTxnReport dailyBatchBillableTxnReport = new DailyBatchBillableTxnReport();
      dailyBatchBillableTxnReport.generateReport();
      log.info("***********MR018 Report Completed*****************");
    } catch (FileNotFoundException e) {
      log.error("<FE> Error on populating MR018 Report :" + e.getMessage());
      e.printStackTrace();
    } catch (DocumentException e) {
      log.error("<DE> Error on populating MR018 Report :" + e.getMessage());
      e.printStackTrace();
    } catch (Exception e) {
      log.error("<EX> Error on populating MR018 Report :" + e.getMessage());
      e.printStackTrace();
    }
  }

  public List<?> retrieveBranchesByDebtorCreditor(boolean creditorBranch, boolean debtorBranch) {
    List<SysCisBranchEntity> cisBranchEntityList = new ArrayList<SysCisBranchEntity>();

    HashMap<String, Object> parameters = new HashMap<String, Object>();
    if (creditorBranch) {
      parameters.put("acCreditor", "Y");
    }

    if (debtorBranch) {
      parameters.put("acDebtor", "Y");
    }

    cisBranchEntityList =
        (List<SysCisBranchEntity>) genericDAO.findAllByCriteria(SysCisBranchEntity.class,
            parameters);

    return cisBranchEntityList;
  }

  public void testArchiveProcess() {
    //Archive Transactions
    AC_ArchiveMessages_ST archiveSingleTableMandates = new AC_ArchiveMessages_ST();
    try {
      archiveSingleTableMandates.testArhiveConfHdrs();
    } catch (ParseException e) {
      e.printStackTrace();
    }

    //		Archive Files
    //		EndOfDayLogic eodLogic = new EndOfDayLogic("MANOWNER");
    //		eodLogic.testArchiveFiles();
  }

  public void generatePHIR02Report(Date fromDate, Date toDate) {
    try {
      log.info("***********Generating PHIR02 Report*****************");
      PasaMandateAmendmentReport pasaMandateAmendmentReport = new PasaMandateAmendmentReport();
      pasaMandateAmendmentReport.generateReport(true, fromDate, toDate);
      log.info("***********PHIR02 Report Completed*****************");
    } catch (FileNotFoundException e) {
      log.error("<FE> Error on populating PHIR02 Report :" + e.getMessage());
      e.printStackTrace();
    } catch (DocumentException e) {
      log.error("<DE> Error on populating PHIR02 Report :" + e.getMessage());
      e.printStackTrace();
    } catch (Exception e) {
      log.error("<EX> Error on populating PHIR02 Report :" + e.getMessage());
      e.printStackTrace();
    }
  }

  public void testPanelCode() {
    try {
      StartOfDayLogic sodLogic = new StartOfDayLogic();
      sodLogic.populateReportSeqNr();
    } catch (Exception ex) {
      log.error("<EX> Error on populating testPanelCode  :" + ex.getMessage());
      ex.printStackTrace();
    }
  }

  public void generateBatchProdVolumesReport(Date frontFromDate, Date frontToDate) {
    try {
      log.info("***********Generating MR020 Report - XLS*****************");
      //			Old Report
      //			BSAMonthlyBatchVolumesReport bsaMonthlyBatchVolumesReport = new
      //			BSAMonthlyBatchVolumesReport();
      //			bsaMonthlyBatchVolumesReport.generateReport();

      //			New Report-20180907
      MR020_BSAMonthlyBatchVolumesReport mr020_BSAMonthlyBatchVolumesReport =
          new MR020_BSAMonthlyBatchVolumesReport();
      mr020_BSAMonthlyBatchVolumesReport.generateReport(frontFromDate, frontToDate);
      log.info("***********MR020 XLS Report Completed*****************");
    } catch (FileNotFoundException e) {
      log.error("<FE> Error on populating MR020 Report :" + e.getMessage());
      e.printStackTrace();
    } catch (DocumentException e) {
      log.error("<DE> Error on populating MR020 Report :" + e.getMessage());
      e.printStackTrace();
    } catch (Exception e) {
      log.error("<EX> Error on populating MR020 Report :" + e.getMessage());
      e.printStackTrace();
    }
  }

  @Override
  public List<?> findServicesByNamedQuery(String namedQuery, String parameter, String value) {
    List<ServicesModel> servModelList = new ArrayList<ServicesModel>();
    try {
      log.info("namedQuery in AdminBean ==> " + namedQuery);
      log.info("parameter in AdminBean ==> " + parameter);
      log.info("value in AdminBean ==> " + value);

      List<MdtOpsServicesEntity> opsServEntityList =
          genericDAO.findAllByNamedQuery(namedQuery, parameter, value);
      log.info("opsServEntityList ==>" + opsServEntityList.size());
      if (opsServEntityList.size() > 0) {
        ServicesLogic servLogic = new ServicesLogic();
        servModelList = servLogic.retrieveOutgoingServices(opsServEntityList);
      }
    } catch (NullPointerException npe) {
      log.error("NullPointer exception :" + npe.getMessage());
    } catch (ObjectNotFoundException onfe) {
      log.error("No Object Exists on DB");
    } catch (Exception e) {
      log.error("Error on findByServiceIdNotNull: " + e.getMessage());
      e.printStackTrace();
    }
    return servModelList;
  }


  @Override
  public Object generateRealTimeMonthlyRejections(String creditorBank, String startDate,
                                                  String lastDate) {
    List<MandateRejectionEntityModel> mandateReasonCodeEntityList =
        new ArrayList<MandateRejectionEntityModel>();

    StringBuffer sb = new StringBuffer();

    sb.append("WITH TEMPTBL AS ( ");
    sb.append("SELECT a.MEMBER_NO AS cr_memno ");
    sb.append(",b.MEMBER_NO AS dr_memno ");
    sb.append(",a.MEMBER_NAME AS cr_memname ");
    sb.append(",b.MEMBER_NAME AS dr_memname ");
    sb.append(",c.REASON_CODE ");
    sb.append(",c.NAME ");
    sb.append("FROM  MANOWNER.SYS_CIS_BANK a ");
    sb.append(",MANOWNER.SYS_CIS_BANK b ");
    sb.append(",MANOWNER.MDT_CNFG_REASON_CODES c ");
    //		2018-12-20 SalehaR: CHG-152647 - Report on all Banks (Remove AC_DEBTOR = Y)
    //		sb.append("WHERE a.MEMBER_NO <> b.MEMBER_NO AND a.AC_CREDITOR = 'Y' AND b.AC_DEBTOR =
    //		'Y') ");
    sb.append("WHERE a.MEMBER_NO <> b.MEMBER_NO AND a.AC_CREDITOR = 'Y' ) ");
    sb.append("SELECT aa.cr_memname AS creditorBankName ");
    sb.append(",aa.dr_memname AS debtorBankName ");
    sb.append(",aa.REASON_CODE AS reasonCode ");
    sb.append(",aa.NAME AS reasonName ");
    sb.append(
        ",SUM(CASE WHEN bb.REJECTEDREASONCODEAMS IS NOT NULL THEN 1 ELSE 0 END) AS " +
            "rejectReasonCodeCount ");
    sb.append("FROM TEMPTBL aa ");
    sb.append(
        "LEFT OUTER JOIN MANOWNER.JNL_ACQ bb ON aa.cr_memno = bb.INSTRUCTINGAGENTAMS AND aa" +
            ".dr_memno = bb.INSTRUCTEDAGENTAMS ");
    sb.append(
        "AND aa.REASON_CODE = bb.REJECTEDREASONCODEAMS AND SUBSTR(bb.TRANSDATETIME,1,8) BETWEEN '" +
            startDate + "' AND '" + lastDate + "' ");
    sb.append("WHERE aa.cr_memno = '" + creditorBank + "' ");
    sb.append("GROUP BY aa.cr_memname, aa.dr_memname, aa.REASON_CODE, aa.NAME ");
    sb.append("ORDER BY aa.cr_memname, aa.dr_memname, aa.REASON_CODE ");

    String sqlQuery = sb.toString();
    log.debug("sqlQuery --->: " + sqlQuery);

    List<String> scalarList = new ArrayList<String>();
    scalarList.add("creditorBankName");
    scalarList.add("debtorBankName");
    scalarList.add("reasonCode");
    scalarList.add("reasonName");
    scalarList.add("rejectReasonCodeCount");

    mandateReasonCodeEntityList =
        genericDAO.findBySql(sqlQuery, scalarList, MandateRejectionEntityModel.class);
    //
    //		if(mandateReasonCodeEntityList != null && mandateReasonCodeEntityList.size()> 0)
    //		{
    //			for(MandateRejectionEntityModel mandateReasonCodeEntityModel :
    //			mandateReasonCodeEntityList)
    //			{
    //				mandateReasonCodeModel = new MandateRejectionModel();
    //				mandateReasonCodeModel = new ServiceTranslator()
    //				.translateMandateRejectionEntityModelToCommonsModel
    //				(mandateReasonCodeEntityModel);
    //				mandateReasonCodeModelList.add(mandateReasonCodeModel);
    //			}
    //		}

    return mandateReasonCodeEntityList;
  }


  @Override
  public List<?> retrieveActiveDebtorBankRemovingCreditorBank(String memberNo) {
    List<DebtorBankModel> debtorBankModelModelList = new ArrayList<DebtorBankModel>();
    List<DebtorBankEntityModel> debtorBankList = new ArrayList<DebtorBankEntityModel>();

    try {

      StringBuffer sb = new StringBuffer();

			/*  select a.member_name, b.inst_id
			  from sys_cis_bank a
			  left join mdt_ops_cust_param b  on b.inst_id = a.member_no*/

      sb.append("SELECT MEMBER_NAME as memberName ,MEMBER_NO as memberNo ");
      sb.append("FROM MANOWNER.SYS_CIS_BANK a ");
      sb.append("WHERE AC_DEBTOR = 'Y' AND MEMBER_NO <> '" + memberNo + "' ");
      sb.append("ORDER BY MEMBER_NAME ASC ");

      String sqlQuery = sb.toString();
      log.debug("The Sql query is  ***************" + sqlQuery);
      List<String> scalarList = new ArrayList<String>();

      scalarList.add("memberName");
      scalarList.add("memberNo");

      try {

        debtorBankList = genericDAO.findBySql(sqlQuery, scalarList, DebtorBankEntityModel.class);

        if (debtorBankList != null && debtorBankList.size() > 0) {
          for (DebtorBankEntityModel localEntityModel : debtorBankList) {
            DebtorBankModel debtorBankModel = new DebtorBankModel();
            debtorBankModel = new ServiceTranslator().translateDebtorBankEntityModelToCommonsModel(
                localEntityModel);
            debtorBankModelModelList.add(debtorBankModel);
          }
        }
      } catch (ObjectNotFoundException onfe) {
        log.error("No Object Exists on DB");
      }

    } catch (Exception e) {
      log.error("Error on retrieveActiveDebtorBank :" + e.getMessage());
      e.printStackTrace();
    }
    return debtorBankModelModelList;
  }


  @Override
  public List retrieveLastExtractTime() {
    List<MdtOpsLastExtractTimesEntity> opsLastExtractTimeList =
        new ArrayList<MdtOpsLastExtractTimesEntity>();
    opsLastExtractTimeList = genericDAO.findAll(MdtOpsLastExtractTimesEntity.class);

    return opsLastExtractTimeList;
  }

  @Override
  public boolean createOpLastExtractTimeEntiy(Object obj) {
    if (obj instanceof MdtOpsLastExtractTimesEntity) {
      MdtOpsLastExtractTimesEntity mdtOpsLastExtractTimesEntity =
          (MdtOpsLastExtractTimesEntity) obj;
      try {
        genericDAO.save(mdtOpsLastExtractTimesEntity);
        return true;
      } catch (Exception ex) {
        log.error("Error on createOpLastExtractTimeEntiy: " + ex.getMessage());
        return false;
      }
    } else {
      return false;
    }
  }

  public void generateDailyBatchProdVolumesReport(Date frontEndDate) {
    try {
      log.info("***********Generating MR022 Report*****************");
      DailyBatchVolumeReport dailyBatchVolumeReport = new DailyBatchVolumeReport();
      dailyBatchVolumeReport.generateReport(true, frontEndDate);
      log.info("***********MR022 Report Completed*****************");
    } catch (FileNotFoundException e) {
      log.error("<FE> Error on populating MR022 Report :" + e.getMessage());
      e.printStackTrace();
    } catch (DocumentException e) {
      log.error("<DE> Error on populating MR022 Report :" + e.getMessage());
      e.printStackTrace();
    } catch (Exception e) {
      log.error("<EX> Error on populating MR022 Report :" + e.getMessage());
      e.printStackTrace();
    }
  }

  @Override
  public List<?> retrieveAllBanksRemovingCreditorBank(String memberNo) {
    List<DebtorBankModel> debtorBankModelModelList = new ArrayList<DebtorBankModel>();
    List<DebtorBankEntityModel> allBanksList = new ArrayList<DebtorBankEntityModel>();

    try {
      StringBuffer sb = new StringBuffer();

      sb.append("SELECT MEMBER_NAME as memberName ,MEMBER_NO as memberNo ");
      sb.append("FROM MANOWNER.SYS_CIS_BANK a ");
      sb.append("WHERE MEMBER_NO <> '" + memberNo + "' ");
      sb.append("ORDER BY MEMBER_NAME ASC ");

      String sqlQuery = sb.toString();
      log.debug("The Sql query is  ***************" + sqlQuery);
      List<String> scalarList = new ArrayList<String>();

      scalarList.add("memberName");
      scalarList.add("memberNo");

      try {

        allBanksList = genericDAO.findBySql(sqlQuery, scalarList, DebtorBankEntityModel.class);

        if (allBanksList != null && allBanksList.size() > 0) {
          for (DebtorBankEntityModel localEntityModel : allBanksList) {
            DebtorBankModel debtorBankModel = new DebtorBankModel();
            debtorBankModel = new ServiceTranslator().translateDebtorBankEntityModelToCommonsModel(
                localEntityModel);
            debtorBankModelModelList.add(debtorBankModel);
          }
        }
      } catch (ObjectNotFoundException onfe) {
        log.error("No Object Exists on DB");
      }

    } catch (Exception e) {
      log.error("Error on retrieveActiveDebtorBank :" + e.getMessage());
      e.printStackTrace();
    }
    return debtorBankModelModelList;
  }


  @Override
  public List<?> retrieveAllCisBank() {
    List<SysCisBankModel> sysCisBankModelList = new ArrayList<SysCisBankModel>();
    List<SysCisBankEntity> sysCisBankEntityList = new ArrayList<SysCisBankEntity>();

    sysCisBankEntityList = genericDAO.findAll(SysCisBankEntity.class);
    if (sysCisBankEntityList != null && sysCisBankEntityList.size() > 0) {
      for (SysCisBankEntity sysCisBankEntity : sysCisBankEntityList) {
        SysCisBankModel sysCisBankLocalModel = new SysCisBankModel();
        sysCisBankLocalModel =
            new ServiceTranslator().translateSysCisBankEntityToCommonsModel(sysCisBankEntity);
        sysCisBankModelList.add(sysCisBankLocalModel);
      }
    }
    return sysCisBankModelList;
  }

  @Override
  public void generate5DayOutstResp(Date frontEndDate) {
    TreeMap<String, List<MandateResponseOutstandingPerBankEntityModel>> pbmd08Map = null;
    //PBMD08 - Daily Outstanding Mandate 5Day Responses Per Bank - PDF
    try {
      log.info("***********Generating PBMD08 Report*****************");
      PBMD08_DailyFiveDayOutstRespPDF PBMD08ReportPDF = new PBMD08_DailyFiveDayOutstRespPDF();
      PBMD08ReportPDF.generate5DayOutstRespPerBank(true, frontEndDate);
      log.info("***********PBMD08 Report Completed*****************");
      pbmd08Map = PBMD08ReportPDF.pbmd08Map;
    } catch (Exception e) {
      log.error("<EX> Error on populating PBMD08 Report :" + e.getMessage());
      e.printStackTrace();
    }
    //PBMD08 - Daily Outstanding Mandate 5Day Responses Per Bank - CSV
    try {
      log.info("***********Generating PBMD08 CSV Report*****************");
      PBMD08_DailyFiveDayOutstRespCSV PBMD08ReportCSV =
          new PBMD08_DailyFiveDayOutstRespCSV(pbmd08Map);
      PBMD08ReportCSV.generateMandateResponseOutstandingPerBankCsv(true, frontEndDate);
      log.info("***********PBMD08 CSV Report Completed*****************");
    } catch (Exception e) {
      log.error("<EX> Error on populating PBMD08 CSV Report :" + e.getMessage());
      e.printStackTrace();
    }
  }

  public List<?> retrieveSysctrlFileSizeLimit() {

    List<CasSysctrlFileSizeLimitEntity> mdtSysctrlFileSizeLimitEntityList =
        new ArrayList<CasSysctrlFileSizeLimitEntity>();

    mdtSysctrlFileSizeLimitEntityList = genericDAO.findAll(CasSysctrlFileSizeLimitEntity.class);

    return mdtSysctrlFileSizeLimitEntityList;
  }

  @Override
  public List<?> retrieveOpsFileSizeLimit() {


    List<MdtAcOpsFileSizeLimitEntity> mdtAcOpsFileSizeLimitEntityList =
        new ArrayList<MdtAcOpsFileSizeLimitEntity>();

    mdtAcOpsFileSizeLimitEntityList = genericDAO.findAll(MdtAcOpsFileSizeLimitEntity.class);

    return mdtAcOpsFileSizeLimitEntityList;
  }

  @Override
  public boolean createOpsFileSizeLimit(Object obj) {
    if (obj instanceof MdtAcOpsFileSizeLimitEntity) {
      MdtAcOpsFileSizeLimitEntity mdtAcOpsFileSizeLimitEntity =
          (MdtAcOpsFileSizeLimitEntity) obj;

      try {
        genericDAO.save(mdtAcOpsFileSizeLimitEntity);
        return true;
      } catch (Exception ex) {
        log.error("Error on createOpsFileSizeLimit: " + ex.getMessage());
        return false;
      }
    } else {
      return false;
    }
  }

  @Override
  public boolean generateRtTxnsBill(Date fromDate, Date toDate) {
    boolean rtTxnsBillBool = false;
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    this.startDate = fromDate;
    this.endDate = toDate;
    String strFromDate = null, strToDate = null;

    if (startDate != null && endDate != null) {

      strFromDate = dateFormat.format(startDate);
      strToDate = dateFormat.format(endDate);
    }

    log.info("strFromDate-->>>>> " + strFromDate);
    log.info("strToDate-->>>>> " + strToDate);

    StringBuffer manualRtTnxsBill = new StringBuffer();

    manualRtTnxsBill.append("INSERT INTO MANOWNER.MDT_AC_OPS_RT_TXNS_BILL ");
    manualRtTnxsBill.append(
        "(JNL_ID,TRANSMISSION_NO,ORIGINATOR,SERVICE,SUB_SERVICE,TXN_TYPE,TXN_STATUS, ");
    manualRtTnxsBill.append(
        "FILE_NAME,STATUS,VOLUME,BILL_EXP_STATUS,SYSTEM_NAME,CREATED_BY,CREATED_DATE, ");
    manualRtTnxsBill.append("MODIFIED_BY, MODIFIED_DATE,ACTION_DATE, RESP_DATE) ");
    manualRtTnxsBill.append("SELECT ");
    manualRtTnxsBill.append(
        "ID,(CASE WHEN TRANSMISSIONNUMBERAMS IS NULL THEN '9' ELSE TRANSMISSIONNUMBERAMS END)," +
            "INSTRUCTINGAGENTAMS,'MANDATES', ");
    manualRtTnxsBill.append(
        "(CASE WHEN MSGTYPEAMS = 'pain.009' OR (MSGTYPEAMS IS NULL AND ORIGINALMSGTYPEAMS = 'pain" +
            ".009') THEN 'MANIN' ");
    manualRtTnxsBill.append(
        "WHEN MSGTYPEAMS = 'pain.010' OR (MSGTYPEAMS IS NULL AND ORIGINALMSGTYPEAMS = 'pain.010')" +
            " THEN 'MANAM' ");
    manualRtTnxsBill.append(
        "WHEN MSGTYPEAMS = 'pain.011' OR (MSGTYPEAMS IS NULL AND ORIGINALMSGTYPEAMS = 'pain.011')" +
            " THEN 'MANCN' END), ");
    manualRtTnxsBill.append("(CASE WHEN (MTI = '5501' OR MTI = '5503') THEN 'TT1' ");
    manualRtTnxsBill.append(
        "WHEN (MTI = '5502' OR MTI = '5504' OR MTI = '5505') THEN 'TT3' END), ");
    manualRtTnxsBill.append(
        "'S',TRANSACTIONIDENTIFIERAMS,'I',1,'Y','MANDATES','MANOWNER',SYSDATE,'MANOWNER',SYSDATE," +
            " ");
    manualRtTnxsBill.append(
        "TO_DATE(SUBSTR(TRANSDATETIME,1,8),'YYYY-MM-DD'),TO_DATE(SUBSTR(TRANSDATETIME,1,8)," +
            "'YYYY-MM-DD') ");
    manualRtTnxsBill.append("FROM MANOWNER.JNL_ACQ ");
    manualRtTnxsBill.append(
        "WHERE (MTI = '5501' OR MTI = '5502' OR MTI = '5503' OR MTI = '5504' OR MTI = '5505') ");
    manualRtTnxsBill.append(
        "AND TO_DATE(SUBSTR(TRANSDATETIME,1,8),'YYYY-MM-DD') BETWEEN TO_DATE('" + strFromDate +
            "','YYYY-MM-DD') AND TO_DATE('" + strToDate + "','YYYY-MM-DD') ");

    try {
      String rTtxnsBillSQL = manualRtTnxsBill.toString();
      log.info("txnsBillSQL: " + rTtxnsBillSQL);
      genericDAO.executeNativeSQL(rTtxnsBillSQL);
      rtTxnsBillBool = true;
    } catch (Exception ex) {
      log.error("Error on Manual RT_TXNS_BILL:- " + ex.getMessage());
      ex.printStackTrace();
      rtTxnsBillBool = false;
    }
    return rtTxnsBillBool;

  }

  @Override
  public boolean pushRtTxnsBillToBillowner(Date fromDate, Date toDate) {

    boolean billOwnerTxns = false;

    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    this.startDate = fromDate;
    this.endDate = toDate;
    String strFromDate = null, strToDate = null;

    if (startDate != null && endDate != null) {

      strFromDate = dateFormat.format(startDate);
      strToDate = dateFormat.format(endDate);
    }

    //	log.info("strFromDate-->>>>> "+strFromDate);
    //	log.info("strToDate-->>>>> "+strToDate);

    StringBuffer manualBillOwnerTxns = new StringBuffer();

    manualBillOwnerTxns.append("INSERT INTO BILLOWNER.OBS_TXNS_BILL_STAGING ");
    manualBillOwnerTxns.append(
        "(RECORD_ID,ACTION_DATE,SERVICE,SUB_SERVICE,SYSTEM_NAME,ORIGINATOR,STATUS,TRXN_STATUS," +
            "TRXN_TYPE, ");
    manualBillOwnerTxns.append(
        "VOLUME,AMOUNT, FILENAME,BILLING_WINDOW,CREATED_BY,CREATED_DATE,MODIFIED_BY," +
            "MODIFIED_DATE) ");
    manualBillOwnerTxns.append("SELECT ");
    manualBillOwnerTxns.append(
        "BILLOWNER.OBS_TXNS_BILL_STAGING_SEQ.NEXTVAL,TO_DATE(SUBSTR(TRANSDATETIME,1,8)," +
            "'YYYY-MM-DD'),'MANDATES', ");
    manualBillOwnerTxns.append(
        "(CASE WHEN MSGTYPEAMS = 'pain.009' OR (MSGTYPEAMS IS NULL AND ORIGINALMSGTYPEAMS = 'pain" +
            ".009') THEN 'MANIN' ");
    manualBillOwnerTxns.append(
        "WHEN MSGTYPEAMS = 'pain.010' OR (MSGTYPEAMS IS NULL AND ORIGINALMSGTYPEAMS = 'pain.010')" +
            " THEN 'MANAM' ");
    manualBillOwnerTxns.append(
        "WHEN MSGTYPEAMS = 'pain.011' OR (MSGTYPEAMS IS NULL AND ORIGINALMSGTYPEAMS = 'pain.011')" +
            " THEN 'MANCN' END), ");
    manualBillOwnerTxns.append(
        "'MANDATES',INSTRUCTINGAGENTAMS,'I','S',(CASE WHEN (MTI = '5501' OR MTI = '5503') THEN " +
            "'TT1'WHEN (MTI = '5502' OR MTI = '5504' OR MTI = '5505') THEN 'TT3' END), ");
    manualBillOwnerTxns.append(
        "1,'',TRANSACTIONIDENTIFIERAMS,1,'MANOWNER',SYSDATE,'MANOWNER',SYSDATE ");
    manualBillOwnerTxns.append("FROM MANOWNER.JNL_ACQ ");
    manualBillOwnerTxns.append(
        "WHERE (MTI = '5501' OR MTI = '5502' OR MTI = '5503' OR MTI = '5504' OR MTI = '5505') ");
    manualBillOwnerTxns.append("AND TO_DATE(SUBSTR(TRANSDATETIME,1,8),'YYYY-MM-DD') ");
    manualBillOwnerTxns.append(
        "BETWEEN TO_DATE('" + strFromDate + "','YYYY-MM-DD') AND TO_DATE('" + strToDate +
            "','YYYY-MM-DD') ");

    try {
      String billOwnerTxnsSQL = manualBillOwnerTxns.toString();
      log.info("txnsBillSQL: " + billOwnerTxnsSQL);
      genericDAO.executeNativeSQL(billOwnerTxnsSQL);
      billOwnerTxns = true;
    } catch (Exception ex) {
      log.error("Error on Manual PUSH RT_TXNS_BILL TO BILLOWNER:- " + ex.getMessage());
      ex.printStackTrace();
      billOwnerTxns = false;
    }
    return billOwnerTxns;


  }

  @Override
  public boolean generateTT1Pain009(Date fromDate, Date toDate) {
    boolean tt1Pain009BillBool = false;
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    this.startDate = fromDate;
    this.endDate = toDate;
    String strFromDate = null, strToDate = null;

    if (startDate != null && endDate != null) {

      strFromDate = dateFormat.format(startDate);
      strToDate = dateFormat.format(endDate);
    }

    //log.info("strFromDate-->>>>> "+strFromDate);
    //log.info("strToDate-->>>>> "+strToDate);

    StringBuffer manualTT1Pain009Bill = new StringBuffer();

    manualTT1Pain009Bill.append("INSERT INTO MANOWNER.MDT_AC_OPS_DAILY_BILLING ( ");
    manualTT1Pain009Bill.append(
        "CREDITOR_BANK,DEBTOR_BANK,SUB_SERVICE,TXN_TYPE,TXN_STATUS,CREATED_BY,CREATED_DATE, ");
    manualTT1Pain009Bill.append(
        "BILL_EXP_STATUS,ACTION_DATE,AUTH_CODE,TXN_ID,MNDT_REF_NUM,EXT_MSG_ID,RESP_DATE) ");
    manualTT1Pain009Bill.append(
        "SELECT b.INSTRUCTINGAGENTAMS,b.INSTRUCTEDAGENTAMS,'MANIN','TT1', ");
    manualTT1Pain009Bill.append(
        "TO_CHAR((case when a.ACCEPTEDINDICATORAMS = 'true' then 'S' else case when a" +
            ".ACCEPTEDINDICATORAMS = 'false' then 'U' else null end end)), ");
    manualTT1Pain009Bill.append(
        "'MANOWNER',SYSDATE,'Y',TO_DATE(SUBSTR(b.TRANSDATETIME,1,8),'YYYY-MM-DD'), ");
    manualTT1Pain009Bill.append(
        "'',a.TRANSACTIONIDENTIFIERAMS,a.MANDATEREFNUMBERAMS,'',TO_DATE(SUBSTR(b.TRANSDATETIME,1," +
            "8),'YYYY-MM-DD') ");
    manualTT1Pain009Bill.append("FROM MANOWNER.JNL_ACQ a ");
    manualTT1Pain009Bill.append(
        "INNER JOIN MANOWNER.JNL_ACQ b ON a.TRANSACTIONIDENTIFIERAMS = b.TRANSACTIONIDENTIFIERAMS" +
            " ");
    manualTT1Pain009Bill.append(
        "WHERE a.SERVICEIDAMS = 'ST012' AND a.MTI = 5506 AND a.RESULTCODE = 0 AND b.MTI = 5501 " +
            "AND b.RESULTCODE = 0 AND a.REASONCODEAMS = '900000' AND b.REASONCODEAMS = '900000' ");
    manualTT1Pain009Bill.append(
        "AND b.ID  > (SELECT MAX(ID) FROM MANOWNER.JNL_ACQ WHERE TO_DATE(SUBSTR(TRANSDATETIME,1," +
            "8),'YYYY-MM-DD') = TO_DATE('" +
            strFromDate + "','YYYY-MM-DD') -1) AND ");
    manualTT1Pain009Bill.append(
        "b.ID <= (SELECT MAX(ID) FROM MANOWNER.JNL_ACQ WHERE TO_DATE(SUBSTR(TRANSDATETIME,1,8)," +
            "'YYYY-MM-DD') = TO_DATE('" +
            strToDate + "','YYYY-MM-DD')) ");
    manualTT1Pain009Bill.append("ORDER BY a.TRANSACTIONIDENTIFIERAMS ASC ");

    try {
      String tt1Pain009BillSQL = manualTT1Pain009Bill.toString();
      log.info("tt1Pain009BillSQL: " + tt1Pain009BillSQL);
      genericDAO.executeNativeSQL(tt1Pain009BillSQL);
      tt1Pain009BillBool = true;
    } catch (Exception ex) {
      log.error("Error on Manual BILLING FOR TT1 MANIN DELAYED RESPONSE:- " + ex.getMessage());
      ex.printStackTrace();
      tt1Pain009BillBool = false;
    }
    return tt1Pain009BillBool;
  }

  @Override
  public boolean generateTT1MANIRData(Date fromDate, Date toDate) {
    boolean tt1MANIRDataBillBool = false;
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    this.startDate = fromDate;
    this.endDate = toDate;
    String strFromDate = null, strToDate = null;

    if (startDate != null && endDate != null) {

      strFromDate = dateFormat.format(startDate);
      strToDate = dateFormat.format(endDate);
    }

    //log.info("strFromDate-->>>>> "+strFromDate);
    //log.info("strToDate-->>>>> "+strToDate);

    StringBuffer manualTT1MANIRDataBill = new StringBuffer();

    manualTT1MANIRDataBill.append("INSERT INTO MANOWNER.MDT_AC_OPS_DAILY_BILLING( ");
    manualTT1MANIRDataBill.append(
        "CREDITOR_BANK,DEBTOR_BANK,SUB_SERVICE,TXN_TYPE,TXN_STATUS,CREATED_BY,CREATED_DATE," +
            "BILL_EXP_STATUS, ");
    manualTT1MANIRDataBill.append(
        "ACTION_DATE,AUTH_CODE,TXN_ID,MNDT_REF_NUM,EXT_MSG_ID,RESP_DATE) ");
    manualTT1MANIRDataBill.append("SELECT INSTRUCTINGAGENTAMS,INSTRUCTEDAGENTAMS,'MANIN','TT1', ");
    manualTT1MANIRDataBill.append(
        "TO_CHAR((case when ACCEPTEDINDICATORAMS = 'true' then 'S' else case when  " +
            "ACCEPTEDINDICATORAMS = 'false' then 'U' else null end end)), ");
    manualTT1MANIRDataBill.append(
        "'MANOWNER',SYSDATE,'Y',TO_DATE(SUBSTR(TRANSDATETIME,1,8),'YYYY-MM-DD'),''," +
            "TRANSACTIONIDENTIFIERAMS,MANDATEREFNUMBERAMS,'',TO_DATE(SUBSTR(TRANSDATETIME,1,8)," +
            "'YYYY-MM-DD') ");
    manualTT1MANIRDataBill.append("FROM MANOWNER.JNL_ACQ ");
    manualTT1MANIRDataBill.append("WHERE SERVICEIDAMS = 'MANIR' AND RESULTCODE = 0 AND MTI=5501 ");
    manualTT1MANIRDataBill.append(
        "AND ID > (SELECT MAX(ID) FROM MANOWNER.JNL_ACQ WHERE TO_DATE(SUBSTR(TRANSDATETIME,1,8)," +
            "'YYYY-MM-DD') = TO_DATE('" +
            strFromDate + "','YYYY-MM-DD') -1) AND ");
    manualTT1MANIRDataBill.append(
        "ID <= (SELECT MAX(ID) FROM MANOWNER.JNL_ACQ WHERE TO_DATE(SUBSTR(TRANSDATETIME,1,8)," +
            "'YYYY-MM-DD') = TO_DATE('" +
            strToDate + "','YYYY-MM-DD'))");
    manualTT1MANIRDataBill.append("ORDER BY TRANSACTIONIDENTIFIERAMS ASC ");

    try {
      String tt1MANIRDataSQL = manualTT1MANIRDataBill.toString();
      log.info("tt1MANIRDataSQL: " + tt1MANIRDataSQL);
      genericDAO.executeNativeSQL(tt1MANIRDataSQL);
      tt1MANIRDataBillBool = true;
    } catch (Exception ex) {
      log.error("Error on Manual BILLING FOR TT1 MANIN IMMEDIATE RESPONSE:- " + ex.getMessage());
      ex.printStackTrace();
      tt1MANIRDataBillBool = false;
    }
    return tt1MANIRDataBillBool;

  }

  @Override
  public boolean generateTT3ManirPain009Succ(Date fromDate, Date toDate) {
    boolean tt3ManirPain009BillBool = false;
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    this.startDate = fromDate;
    this.endDate = toDate;
    String strFromDate = null, strToDate = null;

    if (startDate != null && endDate != null) {

      strFromDate = dateFormat.format(startDate);
      strToDate = dateFormat.format(endDate);
    }

    //log.info("strFromDate-->>>>> "+strFromDate);
    //log.info("strToDate-->>>>> "+strToDate);

    StringBuffer manualTT3ManirPain009Bill = new StringBuffer();

    manualTT3ManirPain009Bill.append("INSERT INTO MANOWNER.MDT_AC_OPS_DAILY_BILLING( ");
    manualTT3ManirPain009Bill.append(
        "CREDITOR_BANK,DEBTOR_BANK,SUB_SERVICE,TXN_TYPE,TXN_STATUS,CREATED_BY,CREATED_DATE, ");
    manualTT3ManirPain009Bill.append(
        "BILL_EXP_STATUS,ACTION_DATE,AUTH_CODE,TXN_ID,MNDT_REF_NUM,EXT_MSG_ID,RESP_DATE) ");
    manualTT3ManirPain009Bill.append(
        "SELECT INSTRUCTINGAGENTAMS,INSTRUCTEDAGENTAMS,'MANIN','TT3', ");
    manualTT3ManirPain009Bill.append(
        "TO_CHAR((case when ACCEPTEDINDICATORAMS = 'true' then 'S' else  case when  " +
            "ACCEPTEDINDICATORAMS = 'false' then 'U' else null end end)), ");
    manualTT3ManirPain009Bill.append(
        "'MANOWNER',SYSDATE,'Y',TO_DATE(SUBSTR(TRANSDATETIME,1,8),'YYYY-MM-DD'),''," +
            "TRANSACTIONIDENTIFIERAMS,MANDATEREFNUMBERAMS,'',TO_DATE(SUBSTR(TRANSDATETIME,1,8)," +
            "'YYYY-MM-DD') ");
    manualTT3ManirPain009Bill.append("FROM MANOWNER.JNL_ACQ ");
    manualTT3ManirPain009Bill.append(
        "WHERE SERVICEIDAMS = 'MANIR' AND RESULTCODE = 0 AND MTI=5502 ");
    manualTT3ManirPain009Bill.append(
        "AND ID > (SELECT MAX(ID) FROM MANOWNER.JNL_ACQ WHERE TO_DATE(SUBSTR(TRANSDATETIME,1,8)," +
            "'YYYY-MM-DD') = TO_DATE('" +
            strFromDate + "','YYYY-MM-DD') -1) AND ");
    manualTT3ManirPain009Bill.append(
        "ID <= (SELECT MAX(ID) FROM MANOWNER.JNL_ACQ WHERE TO_DATE(SUBSTR(TRANSDATETIME,1,8)," +
            "'YYYY-MM-DD') = TO_DATE('" +
            strToDate + "','YYYY-MM-DD'))");
    manualTT3ManirPain009Bill.append("ORDER BY TRANSACTIONIDENTIFIERAMS ASC ");

    try {
      String tt3ManirPain009SQL = manualTT3ManirPain009Bill.toString();
      log.info("tt3ManirPain009SQL: " + tt3ManirPain009SQL);
      genericDAO.executeNativeSQL(tt3ManirPain009SQL);
      tt3ManirPain009BillBool = true;
    } catch (Exception ex) {
      log.error("Error on Manual BILLING FOR BILLING FOR TT3  MANIN:- " + ex.getMessage());
      ex.printStackTrace();
      tt3ManirPain009BillBool = false;
    }
    return tt3ManirPain009BillBool;

  }

  @Override
  public boolean generateTT1MandrPain010Succ(Date fromDate, Date toDate) {
    boolean tt1MandrPain010BillBool = false;
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    this.startDate = fromDate;
    this.endDate = toDate;
    String strFromDate = null, strToDate = null;

    if (startDate != null && endDate != null) {

      strFromDate = dateFormat.format(startDate);
      strToDate = dateFormat.format(endDate);
    }

    //log.info("strFromDate-->>>>> "+strFromDate);
    //log.info("strToDate-->>>>> "+strToDate);

    StringBuffer manualTT1MandrPain010Bill = new StringBuffer();

    manualTT1MandrPain010Bill.append("INSERT INTO MANOWNER.MDT_AC_OPS_DAILY_BILLING ");
    manualTT1MandrPain010Bill.append(
        "(CREDITOR_BANK,DEBTOR_BANK,SUB_SERVICE,TXN_TYPE,TXN_STATUS,CREATED_BY,CREATED_DATE, ");
    manualTT1MandrPain010Bill.append(
        "BILL_EXP_STATUS,ACTION_DATE,AUTH_CODE,TXN_ID,MNDT_REF_NUM,EXT_MSG_ID,RESP_DATE) ");
    manualTT1MandrPain010Bill.append(
        "SELECT b.INSTRUCTINGAGENTAMS,b.INSTRUCTEDAGENTAMS,'MANAM','TT1', ");
    manualTT1MandrPain010Bill.append(
        "TO_CHAR((case when a.ACCEPTEDINDICATORAMS = 'true' then 'S' else ");
    manualTT1MandrPain010Bill.append(
        "case when  a.ACCEPTEDINDICATORAMS = 'false' then 'U' else null end end)), ");
    manualTT1MandrPain010Bill.append(
        "'MANOWNER',SYSDATE,'Y',TO_DATE(SUBSTR(b.TRANSDATETIME,1,8),'YYYY-MM-DD'), ");
    manualTT1MandrPain010Bill.append(
        "(CASE WHEN SUBSTR(REPLACE(REPLACE(REPLACE(REPLACE(b.UNDRLYGAMDMNTDTLSBLOCKAMS, chr(10))," +
            " chr(13)), chr(9)), ' '), INSTR(REPLACE(REPLACE(REPLACE(REPLACE(b" +
            ".UNDRLYGAMDMNTDTLSBLOCKAMS, chr(10)), chr(13)), chr(9)), ' '), '<LclInstrm><Prtry>'," +
            "1," +
            "1)+18,4) ");
    manualTT1MandrPain010Bill.append(
        "NOT IN ('0226','0227','0228','0229','0230') THEN NULL ELSE SUBSTR(REPLACE(REPLACE" +
            "(REPLACE(REPLACE(b.UNDRLYGAMDMNTDTLSBLOCKAMS, chr(10)), chr(13)), chr(9)), ' '), " +
            "INSTR" +
            "(REPLACE(REPLACE(REPLACE(REPLACE(b.UNDRLYGAMDMNTDTLSBLOCKAMS, chr(10)), chr(13)), " +
            "chr" +
            "(9)), ' '), '<LclInstrm><Prtry>',1,1)+18,4) END), ");
    manualTT1MandrPain010Bill.append(
        "a.TRANSACTIONIDENTIFIERAMS,a.MANDATEREFNUMBERAMS,'',TO_DATE(SUBSTR(b.TRANSDATETIME,1,8)," +
            "'YYYY-MM-DD') ");
    manualTT1MandrPain010Bill.append("FROM MANOWNER.JNL_ACQ a ");
    manualTT1MandrPain010Bill.append(
        "INNER JOIN MANOWNER.JNL_ACQ b ON a.TRANSACTIONIDENTIFIERAMS = b.TRANSACTIONIDENTIFIERAMS" +
            " ");
    manualTT1MandrPain010Bill.append(
        "WHERE a.SERVICEIDAMS = 'ST012' AND a.MTI = 5506 AND a.RESULTCODE = 0  AND b.MTI = 5503 " +
            "AND b.RESULTCODE = 0 AND a.REASONCODEAMS = '900000' AND b.REASONCODEAMS = '900000' ");
    manualTT1MandrPain010Bill.append(
        "AND b.ID > (SELECT MAX(ID) FROM MANOWNER.JNL_ACQ WHERE TO_DATE(SUBSTR(TRANSDATETIME,1,8)" +
            ",'YYYY-MM-DD') = TO_DATE('" +
            strFromDate + "','YYYY-MM-DD') -1) AND ");
    manualTT1MandrPain010Bill.append(
        "b.ID <= (SELECT MAX(ID) FROM MANOWNER.JNL_ACQ WHERE TO_DATE(SUBSTR(TRANSDATETIME,1,8)," +
            "'YYYY-MM-DD') = TO_DATE('" +
            strToDate + "','YYYY-MM-DD'))");
    manualTT1MandrPain010Bill.append("ORDER BY a.TRANSACTIONIDENTIFIERAMS ASC ");

    try {
      String tt1MandrPain010SQL = manualTT1MandrPain010Bill.toString();
      log.info("tt1MandrPain010SQL: " + tt1MandrPain010SQL);
      genericDAO.executeNativeSQL(tt1MandrPain010SQL);
      tt1MandrPain010BillBool = true;
    } catch (Exception ex) {
      log.error("Error on Manual BILLING FOR TT1 MANAM DELAYED RESPONSE:- " + ex.getMessage());
      ex.printStackTrace();
      tt1MandrPain010BillBool = false;
    }
    return tt1MandrPain010BillBool;
  }

  @Override
  public boolean generateTT1ManamManirData(Date fromDate, Date toDate) {
    boolean tt1ManamManirBillBool = false;
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    this.startDate = fromDate;
    this.endDate = toDate;
    String strFromDate = null, strToDate = null;

    if (startDate != null && endDate != null) {

      strFromDate = dateFormat.format(startDate);
      strToDate = dateFormat.format(endDate);
    }

    //log.info("strFromDate-->>>>> "+strFromDate);
    //log.info("strToDate-->>>>> "+strToDate);

    StringBuffer manualTT11ManamManirBill = new StringBuffer();

    manualTT11ManamManirBill.append("INSERT INTO MANOWNER.MDT_AC_OPS_DAILY_BILLING ");
    manualTT11ManamManirBill.append(
        "(CREDITOR_BANK,DEBTOR_BANK,SUB_SERVICE,TXN_TYPE,TXN_STATUS,CREATED_BY,CREATED_DATE, ");
    manualTT11ManamManirBill.append(
        "BILL_EXP_STATUS,ACTION_DATE,AUTH_CODE,TXN_ID,MNDT_REF_NUM,EXT_MSG_ID,RESP_DATE) ");
    manualTT11ManamManirBill.append(
        "SELECT INSTRUCTINGAGENTAMS,INSTRUCTEDAGENTAMS,'MANAM','TT1', ");
    manualTT11ManamManirBill.append(
        "TO_CHAR((case when ACCEPTEDINDICATORAMS = 'true' then 'S' else ");
    manualTT11ManamManirBill.append(
        "case when  ACCEPTEDINDICATORAMS = 'false' then 'U' else null end end)), ");
    manualTT11ManamManirBill.append(
        "'MANOWNER',SYSDATE,'Y',TO_DATE(SUBSTR(TRANSDATETIME,1,8),'YYYY-MM-DD'), ");
    manualTT11ManamManirBill.append(
        "(CASE WHEN SUBSTR(REPLACE(REPLACE(REPLACE(REPLACE(UNDRLYGAMDMNTDTLSBLOCKAMS, chr(10)), " +
            "chr(13)), chr(9)), ' '), INSTR(REPLACE(REPLACE(REPLACE(REPLACE" +
            "(UNDRLYGAMDMNTDTLSBLOCKAMS, chr(10)), chr(13)), chr(9)), ' '), '<LclInstrm><Prtry>'," +
            "1," +
            "1)+18,4) ");
    manualTT11ManamManirBill.append(
        "NOT IN ('0226','0227','0228','0229','0230') THEN NULL ELSE SUBSTR(REPLACE(REPLACE" +
            "(REPLACE(REPLACE(UNDRLYGAMDMNTDTLSBLOCKAMS, chr(10)), chr(13)), chr(9)), ' '), INSTR" +
            "(REPLACE(REPLACE(REPLACE(REPLACE(UNDRLYGAMDMNTDTLSBLOCKAMS, chr(10)), chr(13)), chr" +
            "(9)" +
            "), ' '), '<LclInstrm><Prtry>',1,1)+18,4) END), ");
    manualTT11ManamManirBill.append(
        "TRANSACTIONIDENTIFIERAMS,MANDATEREFNUMBERAMS,'',TO_DATE(SUBSTR(TRANSDATETIME,1,8)," +
            "'YYYY-MM-DD') ");
    manualTT11ManamManirBill.append("FROM MANOWNER.JNL_ACQ ");
    manualTT11ManamManirBill.append(
        "WHERE SERVICEIDAMS = 'MANIR' AND RESULTCODE = 0 AND MTI=5503 ");
    manualTT11ManamManirBill.append(
        "AND ID > (SELECT MAX(ID) FROM MANOWNER.JNL_ACQ WHERE TO_DATE(SUBSTR(TRANSDATETIME,1,8)," +
            "'YYYY-MM-DD') = TO_DATE('" +
            strFromDate + "','YYYY-MM-DD') -1) AND ");
    manualTT11ManamManirBill.append(
        "ID <= (SELECT MAX(ID) FROM MANOWNER.JNL_ACQ WHERE TO_DATE(SUBSTR(TRANSDATETIME,1,8)," +
            "'YYYY-MM-DD') = TO_DATE('" +
            strToDate + "','YYYY-MM-DD')) ");
    manualTT11ManamManirBill.append("ORDER BY TRANSACTIONIDENTIFIERAMS ASC ");

    try {
      String tt1ManamManirSQL = manualTT11ManamManirBill.toString();
      log.info("tt1ManamManirSQL: " + tt1ManamManirSQL);
      genericDAO.executeNativeSQL(tt1ManamManirSQL);
      tt1ManamManirBillBool = true;
    } catch (Exception ex) {
      log.error("Error on Manual BILLING FOR TT1 MANAM IMMEDIATE RESPONSE:- " + ex.getMessage());
      ex.printStackTrace();
      tt1ManamManirBillBool = false;
    }
    return tt1ManamManirBillBool;
  }

  @Override
  public boolean generateTT3ManIrPain010Succ(Date fromDate, Date toDate) {
    boolean tt3ManIrPain010SuccBool = false;
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    this.startDate = fromDate;
    this.endDate = toDate;
    String strFromDate = null, strToDate = null;

    if (startDate != null && endDate != null) {

      strFromDate = dateFormat.format(startDate);
      strToDate = dateFormat.format(endDate);
    }

    //log.info("strFromDate-->>>>> "+strFromDate);
    //log.info("strToDate-->>>>> "+strToDate);

    StringBuffer manualTT3ManIrPain010SuccBill = new StringBuffer();


    manualTT3ManIrPain010SuccBill.append("INSERT INTO MANOWNER.MDT_AC_OPS_DAILY_BILLING ");
    manualTT3ManIrPain010SuccBill.append(
        "(CREDITOR_BANK,DEBTOR_BANK,SUB_SERVICE,TXN_TYPE,TXN_STATUS, ");
    manualTT3ManIrPain010SuccBill.append(
        "CREATED_BY,CREATED_DATE,BILL_EXP_STATUS,ACTION_DATE,AUTH_CODE, ");
    manualTT3ManIrPain010SuccBill.append("TXN_ID,MNDT_REF_NUM,EXT_MSG_ID,RESP_DATE) ");
    manualTT3ManIrPain010SuccBill.append(
        "SELECT INSTRUCTINGAGENTAMS,INSTRUCTEDAGENTAMS,'MANAM','TT3', ");
    manualTT3ManIrPain010SuccBill.append(
        "TO_CHAR((case when ACCEPTEDINDICATORAMS = 'true' then 'S' else ");
    manualTT3ManIrPain010SuccBill.append(
        "case when  ACCEPTEDINDICATORAMS = 'false' then 'U' else null end end)), ");
    manualTT3ManIrPain010SuccBill.append(
        "'MANOWNER',SYSDATE,'Y',TO_DATE(SUBSTR(TRANSDATETIME,1,8),'YYYY-MM-DD'), ");
    manualTT3ManIrPain010SuccBill.append(
        "(CASE WHEN SUBSTR(REPLACE(REPLACE(REPLACE(REPLACE(UNDRLYGAMDMNTDTLSBLOCKAMS, chr(10)), " +
            "chr(13)), chr(9)), ' '), INSTR(REPLACE(REPLACE(REPLACE(REPLACE" +
            "(UNDRLYGAMDMNTDTLSBLOCKAMS, chr(10)), chr(13)), chr(9)), ' '), '<LclInstrm><Prtry>'," +
            "1," +
            "1)+18,4) ");
    manualTT3ManIrPain010SuccBill.append(
        "NOT IN ('0226','0227','0228','0229','0230') THEN NULL ELSE SUBSTR(REPLACE(REPLACE" +
            "(REPLACE(REPLACE(UNDRLYGAMDMNTDTLSBLOCKAMS, chr(10)), chr(13)), chr(9)), ' '), INSTR" +
            "(REPLACE(REPLACE(REPLACE(REPLACE(UNDRLYGAMDMNTDTLSBLOCKAMS, chr(10)), chr(13)), chr" +
            "(9)" +
            "), ' '), '<LclInstrm><Prtry>',1,1)+18,4) END), ");
    manualTT3ManIrPain010SuccBill.append(
        "TRANSACTIONIDENTIFIERAMS,MANDATEREFNUMBERAMS,'',TO_DATE(SUBSTR(TRANSDATETIME,1,8)," +
            "'YYYY-MM-DD') ");
    manualTT3ManIrPain010SuccBill.append("FROM MANOWNER.JNL_ACQ ");
    manualTT3ManIrPain010SuccBill.append(
        "WHERE SERVICEIDAMS = 'MANIR' AND RESULTCODE = 0 AND MTI=5504 ");
    manualTT3ManIrPain010SuccBill.append(
        "AND ID > (SELECT MAX(ID) FROM MANOWNER.JNL_ACQ WHERE TO_DATE(SUBSTR(TRANSDATETIME,1,8)," +
            "'YYYY-MM-DD') = TO_DATE('" +
            strFromDate + "','YYYY-MM-DD') -1) AND ");
    manualTT3ManIrPain010SuccBill.append(
        "ID <= (SELECT MAX(ID) FROM MANOWNER.JNL_ACQ WHERE TO_DATE(SUBSTR(TRANSDATETIME,1,8)," +
            "'YYYY-MM-DD') = TO_DATE('" +
            strToDate + "','YYYY-MM-DD'))");
    manualTT3ManIrPain010SuccBill.append("ORDER BY TRANSACTIONIDENTIFIERAMS ASC ");

    try {
      String tt3ManIrPain010SuccSQL = manualTT3ManIrPain010SuccBill.toString();
      log.info("tt3ManIrPain010SuccSQL: " + tt3ManIrPain010SuccSQL);
      genericDAO.executeNativeSQL(tt3ManIrPain010SuccSQL);
      tt3ManIrPain010SuccBool = true;
    } catch (Exception ex) {
      log.error("Error on Manual BILLING  FOR TT3 MANAM:- " + ex.getMessage());
      ex.printStackTrace();
      tt3ManIrPain010SuccBool = false;
    }
    return tt3ManIrPain010SuccBool;
  }

  @Override
  public boolean generateTT1ManIrPain011Succ(Date fromDate, Date toDate) {
    boolean tt1ManIrPain011SuccBool = false;
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    this.startDate = fromDate;
    this.endDate = toDate;
    String strFromDate = null, strToDate = null;

    if (startDate != null && endDate != null) {

      strFromDate = dateFormat.format(startDate);
      strToDate = dateFormat.format(endDate);
    }

    //log.info("strFromDate-->>>>> "+strFromDate);
    //log.info("strToDate-->>>>> "+strToDate);

    StringBuffer manualTT1ManIrPain011SuccBill = new StringBuffer();

    manualTT1ManIrPain011SuccBill.append("INSERT INTO MANOWNER.MDT_AC_OPS_DAILY_BILLING ");
    manualTT1ManIrPain011SuccBill.append(
        "(CREDITOR_BANK,DEBTOR_BANK,SUB_SERVICE,TXN_TYPE,TXN_STATUS, ");
    manualTT1ManIrPain011SuccBill.append(
        "CREATED_BY,CREATED_DATE,BILL_EXP_STATUS,ACTION_DATE,AUTH_CODE, ");
    manualTT1ManIrPain011SuccBill.append("TXN_ID,MNDT_REF_NUM,EXT_MSG_ID,RESP_DATE) ");
    manualTT1ManIrPain011SuccBill.append(
        "SELECT INSTRUCTINGAGENTAMS,INSTRUCTEDAGENTAMS,'MANCN','TT1', ");
    manualTT1ManIrPain011SuccBill.append(
        "TO_CHAR((case when ACCEPTEDINDICATORAMS = 'true' then 'S' else ");
    manualTT1ManIrPain011SuccBill.append(
        "case when  ACCEPTEDINDICATORAMS = 'false' then 'U' else null end end)), ");
    manualTT1ManIrPain011SuccBill.append(
        "'MANOWNER',SYSDATE,'Y',TO_DATE(SUBSTR(TRANSDATETIME,1,8),'YYYY-MM-DD'),'', ");
    manualTT1ManIrPain011SuccBill.append(
        "TRANSACTIONIDENTIFIERAMS,MANDATEREFNUMBERAMS,'',TO_DATE(SUBSTR(TRANSDATETIME,1,8)," +
            "'YYYY-MM-DD') ");
    manualTT1ManIrPain011SuccBill.append("FROM MANOWNER.JNL_ACQ ");
    manualTT1ManIrPain011SuccBill.append(
        "WHERE SERVICEIDAMS = 'MANIR' AND RESULTCODE = 0 AND MTI=5505 AND ");
    manualTT1ManIrPain011SuccBill.append(
        "ID > (SELECT MAX(ID) FROM MANOWNER.JNL_ACQ WHERE TO_DATE(SUBSTR(TRANSDATETIME,1,8)," +
            "'YYYY-MM-DD') = TO_DATE('" +
            strFromDate + "','YYYY-MM-DD') -1) AND ");
    manualTT1ManIrPain011SuccBill.append(
        "ID <= (SELECT MAX(ID) FROM MANOWNER.JNL_ACQ WHERE TO_DATE(SUBSTR(TRANSDATETIME,1,8)," +
            "'YYYY-MM-DD') = TO_DATE('" +
            strToDate + "','YYYY-MM-DD'))");
    manualTT1ManIrPain011SuccBill.append("ORDER BY TRANSACTIONIDENTIFIERAMS ASC ");

    try {
      String tt1ManIrPain011SuccSQL = manualTT1ManIrPain011SuccBill.toString();
      log.info("tt1ManIrPain011SuccSQL: " + tt1ManIrPain011SuccSQL);
      genericDAO.executeNativeSQL(tt1ManIrPain011SuccSQL);
      tt1ManIrPain011SuccBool = true;
    } catch (Exception ex) {
      log.error("Error on Manual BILLING  FOR TT1  MANCN :- " + ex.getMessage());
      ex.printStackTrace();
      tt1ManIrPain011SuccBool = false;
    }
    return tt1ManIrPain011SuccBool;
  }

  @Override
  public boolean generateBillingExport(Date fromDate, Date toDate, String srinpService) {

    boolean billingExportBool = false;
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    this.startDate = fromDate;
    this.endDate = toDate;
    String strFromDate = null, strToDate = null;

    if (startDate != null && endDate != null) {

      strFromDate = dateFormat.format(startDate);
      strToDate = dateFormat.format(endDate);
    }

    //log.info("strFromDate-->>>>> "+strFromDate);
    //log.info("strToDate-->>>>> "+strToDate);

    StringBuffer manualBillingExport = new StringBuffer();

    manualBillingExport.append("INSERT INTO BILLOWNER.OBS_BILLING_STAGING ");
    manualBillingExport.append(
        "(ACTION_DATE,ORIGINATOR,DESTINATION,VOLUME,SERVICE,SUB_SERVICE,TRXN_TYPE,TRXN_STATUS, ");
    manualBillingExport.append(
        "CREATED_BY,CREATED_DATE,TRACKING_CODE,AUTH_IND,SYSTEM_NAME,STATUS,BILLING_WINDOW," +
            "AUTH_CODE) ");
    if (srinpService.equalsIgnoreCase("SRINP")) {
      manualBillingExport.append(
          "SELECT RESP_DATE,DEBTOR_BANK,CREDITOR_BANK,COUNT(*),'MANDATES',SUB_SERVICE,TXN_TYPE, ");
    } else {

      manualBillingExport.append(
          "SELECT RESP_DATE,CREDITOR_BANK,DEBTOR_BANK,COUNT(*),'MANDATES',SUB_SERVICE,TXN_TYPE, ");
    }
    manualBillingExport.append("TXN_STATUS,'MANOWNER',SYSDATE,'0','N','MANDATES','I',1 ");
    manualBillingExport.append("FROM MANOWNER.MDT_AC_OPS_DAILY_BILLING ");
    manualBillingExport.append(
        "WHERE BILL_EXP_STATUS = 'Y'  AND RESP_DATE BETWEEN  TO_DATE('" + strFromDate +
            "','YYYY-MM-DD') AND TO_DATE('" + strToDate + "','YYYY-MM-DD') ");
    manualBillingExport.append(
        "GROUP BY CREDITOR_BANK, SUB_SERVICE,TXN_TYPE, DEBTOR_BANK, TXN_STATUS, ACTION_DATE, " +
            "AUTH_CODE, RESP_DATE ");

    try {
      String billingExportSQL = manualBillingExport.toString();
      log.info("billingExportSQL: " + billingExportSQL);
      genericDAO.executeNativeSQL(billingExportSQL);
      billingExportBool = true;
    } catch (Exception ex) {
      log.error("Error on Manual INTERCHANGE BILLING EXPORT  :- " + ex.getMessage());
      ex.printStackTrace();
      billingExportBool = false;
    }
    return billingExportBool;


  }

  @Override
  public boolean generateTT2TxnsBilling(Date fromDate, Date toDate) {
    boolean tt2TxnsBillingBool = false;
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    this.startDate = fromDate;
    this.endDate = toDate;
    String strFromDate = null, strToDate = null;

    if (startDate != null && endDate != null) {

      strFromDate = dateFormat.format(startDate);
      strToDate = dateFormat.format(endDate);
    }

    //log.info("strFromDate-->>>>> "+strFromDate);
    //log.info("strToDate-->>>>> "+strToDate);

    StringBuffer manualTT2TxnsBilling = new StringBuffer();

    manualTT2TxnsBilling.append("INSERT INTO  MANOWNER.MDT_AC_OPS_TXNS_BILLING ");
    manualTT2TxnsBilling.append(
        "(SYSTEM_SEQ_NO, ORIGINATOR,SERVICE,SUB_SERVICE,TXN_TYPE,TXN_STATUS,FILE_NAME, ");
    manualTT2TxnsBilling.append(
        "STATUS,VOLUME,BILL_EXP_STATUS,SYSTEM_NAME,CREATED_BY,CREATED_DATE,MODIFIED_BY, ");
    manualTT2TxnsBilling.append("MODIFIED_DATE,ACTION_DATE,RESP_DATE) ");
    manualTT2TxnsBilling.append("SELECT MANOWNER.MDT_AC_OPS_TXNS_BILLING_SEQ.NEXTVAL, ");
    manualTT2TxnsBilling.append(
        "INST_ID,'MANDATES',SERVICE_ID,'TT2','S',FILE_NAME,'I',NR_OF_MSGS,'Y','MANDATES', ");
    manualTT2TxnsBilling.append("'MANOWNER',SYSDATE,'MANOWNER',SYSDATE,PROCESS_DATE,PROCESS_DATE ");
    manualTT2TxnsBilling.append("FROM MANOWNER.MDT_AC_ARC_MNDT_COUNT ");
    manualTT2TxnsBilling.append(
        "WHERE PROCESS_DATE BETWEEN TO_DATE('" + strFromDate + "','YYYY-MM-DD') AND TO_DATE('" +
            strToDate + "','YYYY-MM-DD') ");
    manualTT2TxnsBilling.append("AND SERVICE_ID IN ('MANIN', 'MANAM', 'MANCN', 'MANRI', 'SPINP') ");

    try {
      String tt2TxnsBillingSQL = manualTT2TxnsBilling.toString();
      log.info("tt2TxnsBillingSQL: " + tt2TxnsBillingSQL);
      genericDAO.executeNativeSQL(tt2TxnsBillingSQL);
      tt2TxnsBillingBool = true;
    } catch (Exception ex) {
      log.error("Error on Manual TT2 TXN BILLING :- " + ex.getMessage());
      ex.printStackTrace();
      tt2TxnsBillingBool = false;
    }
    return tt2TxnsBillingBool;

  }

  @Override
  public boolean pushTT2TxnsBillToBillowner(Date fromDate, Date toDate) {
    boolean tt2TxnsBillToBillownergBool = false;
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    this.startDate = fromDate;
    this.endDate = toDate;
    String strFromDate = null, strToDate = null;

    if (startDate != null && endDate != null) {

      strFromDate = dateFormat.format(startDate);
      strToDate = dateFormat.format(endDate);
    }

    //log.info("strFromDate-->>>>> "+strFromDate);
    //log.info("strToDate-->>>>> "+strToDate);

    StringBuffer manualTT2TxnsBillToBillowner = new StringBuffer();

    manualTT2TxnsBillToBillowner.append("INSERT INTO BILLOWNER.OBS_TXNS_BILL_STAGING ");
    manualTT2TxnsBillToBillowner.append(
        "(RECORD_ID,ACTION_DATE,SERVICE,SUB_SERVICE,SYSTEM_NAME,ORIGINATOR,STATUS,TRXN_STATUS, ");
    manualTT2TxnsBillToBillowner.append(
        "TRXN_TYPE,VOLUME,AMOUNT,FILENAME,BILLING_WINDOW,CREATED_BY,CREATED_DATE,MODIFIED_BY," +
            "MODIFIED_DATE) ");
    manualTT2TxnsBillToBillowner.append("SELECT BILLOWNER.OBS_TXNS_BILL_STAGING_SEQ.NEXTVAL, ");
    manualTT2TxnsBillToBillowner.append(
        "PROCESS_DATE,'MANDATES',SERVICE_ID,'MANDATES',INST_ID,'I','S','TT2', ");
    manualTT2TxnsBillToBillowner.append(
        "NR_OF_MSGS,'',FILE_NAME,'','MANOWNER',SYSDATE,'MANOWNER',SYSDATE ");
    manualTT2TxnsBillToBillowner.append("FROM MANOWNER.MDT_AC_ARC_MNDT_COUNT ");
    manualTT2TxnsBillToBillowner.append(
        "WHERE PROCESS_DATE BETWEEN TO_DATE('" + strFromDate + "','YYYY-MM-DD') AND TO_DATE('" +
            strToDate + "','YYYY-MM-DD') ");
    manualTT2TxnsBillToBillowner.append(
        "AND SERVICE_ID IN ('MANIN', 'MANAM', 'MANCN', 'MANRI', 'SPINP') ");

    try {
      String tt2TxnsBillToBillownergSQL = manualTT2TxnsBillToBillowner.toString();
      log.info("tt2TxnsBillToBillownergSQL: " + tt2TxnsBillToBillownergSQL);
      genericDAO.executeNativeSQL(tt2TxnsBillToBillownergSQL);
      tt2TxnsBillToBillownergBool = true;
    } catch (Exception ex) {
      log.error("Error on Manual PUSH TT2_TXNS_BILL TO BILLOWNER:- " + ex.getMessage());
      ex.printStackTrace();
      tt2TxnsBillToBillownergBool = false;
    }
    return tt2TxnsBillToBillownergBool;


  }

  @Override
  public boolean generateTT2InterchangeBill(Date fromDate, Date toDate) {
    boolean tt2InterBillgBool = false;
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    this.startDate = fromDate;
    this.endDate = toDate;
    String strFromDate = null, strToDate = null;

    if (startDate != null && endDate != null) {

      strFromDate = dateFormat.format(startDate);
      strToDate = dateFormat.format(endDate);
    }

    //log.info("strFromDate-->>>>> "+strFromDate);
    //log.info("strToDate-->>>>> "+strToDate);

    StringBuffer manualTT2InterBill = new StringBuffer();

    manualTT2InterBill.append("INSERT INTO MANOWNER.MDT_AC_OPS_DAILY_BILLING ");
    manualTT2InterBill.append(
        "(CREDITOR_BANK,DEBTOR_BANK,SUB_SERVICE,TXN_TYPE,TXN_STATUS,CREATED_BY,CREATED_DATE, ");
    manualTT2InterBill.append(
        "BILL_EXP_STATUS,ACTION_DATE,AUTH_CODE,TXN_ID,MNDT_REF_NUM,EXT_MSG_ID,RESP_DATE) ");
    manualTT2InterBill.append(
        "SELECT a.CREDITOR_BANK,a.DEBTOR_BANK,a.SERVICE_ID,'TT2',(case when b.ACCEPTED_IND = " +
            "'true' then 'S' else 'U' end), ");
    manualTT2InterBill.append(
        "'MANOWNER',SYSDATE,'Y',a.CREATED_DATE,a.LOCAL_INSTR_CD,a.MANDATE_REQ_TRAN_ID,b" +
            ".MANDATE_REF_NR,a.EXTRACT_MSG_ID,a.IN_FILE_DATE ");
    manualTT2InterBill.append("FROM MANOWNER.MDT_AC_ARC_MANDATE_TXNS a ");
    manualTT2InterBill.append(
        "INNER JOIN MANOWNER.MDT_AC_ARC_MANDATE_TXNS b ON a.MANDATE_REQ_TRAN_ID = b" +
            ".MANDATE_REQ_TRAN_ID ");
    manualTT2InterBill.append(
        "WHERE a.PROCESS_STATUS IN('M','R') AND a.SERVICE_ID IN('MANIN','MANAM','MANCN') AND b" +
            ".PROCESS_STATUS = '4' ");
    manualTT2InterBill.append(
        "AND b.SERVICE_ID = 'MANAC'AND a.IN_FILE_DATE BETWEEN TO_DATE('" + strFromDate +
            "','YYYY-MM-DD') AND TO_DATE('" + strToDate + "','YYYY-MM-DD') ");
    manualTT2InterBill.append("ORDER BY a.MANDATE_REQ_TRAN_ID ASC ");

    try {
      String tt2InterBillSQL = manualTT2InterBill.toString();
      log.info("tt2InterBillSQL: " + tt2InterBillSQL);
      genericDAO.executeNativeSQL(tt2InterBillSQL);
      tt2InterBillgBool = true;
    } catch (Exception ex) {
      log.error("Error on Manual TT2 INTERCHANGE BILLING :- " + ex.getMessage());
      ex.printStackTrace();
      tt2InterBillgBool = false;
    }
    return tt2InterBillgBool;

  }

  @Override
  public boolean generateSRINPinterchangeBill(Date fromDate, Date toDate) {
    boolean srinpInterBillgBool = false;
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    this.startDate = fromDate;
    this.endDate = toDate;
    String strFromDate = null, strToDate = null;

    if (startDate != null && endDate != null) {

      strFromDate = dateFormat.format(startDate);
      strToDate = dateFormat.format(endDate);
    }

    //log.info("strFromDate-->>>>> "+strFromDate);
    //log.info("strToDate-->>>>> "+strToDate);

    StringBuffer manualSrinpInterBill = new StringBuffer();

    manualSrinpInterBill.append("INSERT INTO MANOWNER.MDT_AC_OPS_DAILY_BILLING ");
    manualSrinpInterBill.append(
        "(CREDITOR_BANK,DEBTOR_BANK,SUB_SERVICE,TXN_TYPE,TXN_STATUS,CREATED_BY,CREATED_DATE, ");
    manualSrinpInterBill.append(
        "BILL_EXP_STATUS,ACTION_DATE,AUTH_CODE,TXN_ID,MNDT_REF_NUM,EXT_MSG_ID,RESP_DATE) ");
    manualSrinpInterBill.append(
        "SELECT a.INSTG_AGT,b.INST_ID,a.SERVICE,'TT2',(case when b.TXN_STATUS = 'ACCP' then 'S' " +
            "else 'U' end), ");
    manualSrinpInterBill.append(
        "'MANOWNER',SYSDATE,'Y',a.CREATE_DATE_TIME,'',b.TXN_ID,b.MANDATE_REF_NUMBER,b" +
            ".EXTRACT_MSG_ID,a.CREATE_DATE_TIME ");
    manualSrinpInterBill.append("FROM MANOWNER.MDT_AC_ARC_CONF_HDRS a ");
    manualSrinpInterBill.append(
        "INNER JOIN MANOWNER.MDT_AC_ARC_CONF_DETAILS b ON a.SYSTEM_SEQ_NO = b.CONF_HDR_SEQ_NO ");
    manualSrinpInterBill.append(
        "WHERE b.EXTRACT_SERVICE = 'SROUT' AND TRUNC(a.CREATE_DATE_TIME) BETWEEN TO_DATE('" +
            strFromDate + "','YYYY-MM-DD') AND TO_DATE('" + strToDate + "','YYYY-MM-DD') ");
    manualSrinpInterBill.append("ORDER BY b.TXN_ID ASC ");


    try {
      String srinpInterBillSQL = manualSrinpInterBill.toString();
      log.info("srinpInterBillSQL: " + srinpInterBillSQL);
      genericDAO.executeNativeSQL(srinpInterBillSQL);
      srinpInterBillgBool = true;
    } catch (Exception ex) {
      log.error("Error on Manual SRINP INTERCHANGE BILLING :- " + ex.getMessage());
      ex.printStackTrace();
      srinpInterBillgBool = false;
    }
    return srinpInterBillgBool;

  }

  @Override
  public boolean generateMANRTinterchangeBill(Date fromDate, Date toDate) {
    boolean manrtInterBillgBool = false;
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    this.startDate = fromDate;
    this.endDate = toDate;
    String strFromDate = null, strToDate = null;

    if (startDate != null && endDate != null) {

      strFromDate = dateFormat.format(startDate);
      strToDate = dateFormat.format(endDate);
    }

    //log.info("strFromDate-->>>>> "+strFromDate);
    //log.info("strToDate-->>>>> "+strToDate);

    StringBuffer manualManrtInterBill = new StringBuffer();

    manualManrtInterBill.append("INSERT INTO MANOWNER.MDT_AC_OPS_DAILY_BILLING ");
    manualManrtInterBill.append(
        "(CREDITOR_BANK,DEBTOR_BANK,SUB_SERVICE,TXN_TYPE,TXN_STATUS,CREATED_BY,CREATED_DATE, ");
    manualManrtInterBill.append(
        "BILL_EXP_STATUS,ACTION_DATE,AUTH_CODE,TXN_ID,MNDT_REF_NUM,EXT_MSG_ID,RESP_DATE) ");
    manualManrtInterBill.append(
        "SELECT CREDITOR_BANK,DEBTOR_BANK,SERVICE_ID,'TT2',(case when ACCEPTED_IND = 'true' then " +
            "'S' else 'U' end), ");
    manualManrtInterBill.append(
        "'MANOWNER',SYSDATE,'Y',IN_FILE_DATE,'',MDT_INF_REQ_ID,MANDATE_REF_NR,EXTRACT_MSG_ID," +
            "IN_FILE_DATE ");
    manualManrtInterBill.append("FROM MANOWNER.MDT_AC_ARC_MDTE_RSP_TXNS ");
    manualManrtInterBill.append(
        "WHERE IN_FILE_DATE BETWEEN TO_DATE('" + strFromDate + "','YYYY-MM-DD') AND TO_DATE('" +
            strToDate + "','YYYY-MM-DD') ");

    try {
      String manrtInterBillSQL = manualManrtInterBill.toString();
      log.info("manrtInterBillSQL: " + manrtInterBillSQL);
      genericDAO.executeNativeSQL(manrtInterBillSQL);
      manrtInterBillgBool = true;
    } catch (Exception ex) {
      log.error("Error on Manual MANRT INTERCHANGE BILLING :- " + ex.getMessage());
      ex.printStackTrace();
      manrtInterBillgBool = false;
    }
    return manrtInterBillgBool;


  }


  @Override
  public List<?> retrieveAllOpsFileSizeLimit() {

    List<FileSizeLimitModel> fileSizeLimitModelList = new ArrayList<FileSizeLimitModel>();

    List<MdtAcOpsFileSizeLimitEntity> mdtAcOpsFileSizeLimitEntityList =
        new ArrayList<MdtAcOpsFileSizeLimitEntity>();

    mdtAcOpsFileSizeLimitEntityList = genericDAO.findAll(MdtAcOpsFileSizeLimitEntity.class);
    if (mdtAcOpsFileSizeLimitEntityList.size() > 0) {
      OpsFileSizeLimitLogic opsFileSizeLimitLogic = new OpsFileSizeLimitLogic();
      fileSizeLimitModelList =
          opsFileSizeLimitLogic.retrieveAllOpsFileSizeLimit(mdtAcOpsFileSizeLimitEntityList);
    }

    return fileSizeLimitModelList;
  }

  @Override
  public List viewFileSizeLimitSearch(String memberId, String subService) {
    List<FileSizeLimitModel> fileSizeModelList = new ArrayList<FileSizeLimitModel>();
    List<MdtAcOpsFileSizeLimitEntity> fileSizeEnList = new ArrayList<MdtAcOpsFileSizeLimitEntity>();
    try {

      HashMap<String, Object> parameters = new HashMap<String, Object>();
      parameters.put("mdtAcOpsFileSizeLimitPK.memberId", memberId);
      parameters.put("mdtAcOpsFileSizeLimitPK.subService", subService);

      log.debug("parameters---> " + parameters.toString());
      fileSizeEnList = (List<MdtAcOpsFileSizeLimitEntity>) genericDAO.findAllByCriteria(
          MdtAcOpsFileSizeLimitEntity.class, parameters);
      log.debug("---------------fileSizeEnList after findAllByCriteria: ------------------" +
          fileSizeEnList);


      if (fileSizeEnList != null && fileSizeEnList.size() > 0) {
        OpsFileSizeLimitLogic opsFileSizeLimitLogic = new OpsFileSizeLimitLogic();
        for (MdtAcOpsFileSizeLimitEntity localEntity : fileSizeEnList) {
          FileSizeLimitModel localModel = new FileSizeLimitModel();

          localModel = opsFileSizeLimitLogic.retrieveOpsFileSizeLimit(localEntity);

          fileSizeModelList.add(localModel);
        }
      }
    } catch (ObjectNotFoundException onfe) {
      log.error("No Object Exists on DB");
    } catch (Exception e) {
      log.error("Error on viewFileSizeLimitSearch: " + e.getMessage());
      e.printStackTrace();
    }

    return fileSizeModelList;
  }


  @Override
  public List<?> viewSysCtrlFileSizeLimit() {
    List<SysCtrlFileSizeLimitModel> sysCtrlFileSizeLimitModelList =
        new ArrayList<SysCtrlFileSizeLimitModel>();

    List<CasSysctrlFileSizeLimitEntity> mdtSysctrlFileSizeLimitEntityList =
        new ArrayList<CasSysctrlFileSizeLimitEntity>();

    mdtSysctrlFileSizeLimitEntityList = genericDAO.findAll(CasSysctrlFileSizeLimitEntity.class);
    if (mdtSysctrlFileSizeLimitEntityList.size() > 0) {
      SysCtrlFileSizeLimitLogic sysCtrlFileSizeLimitLogic = new SysCtrlFileSizeLimitLogic();
      sysCtrlFileSizeLimitModelList = sysCtrlFileSizeLimitLogic.retrieveAllSysctrlFileSizeLimit(
          mdtSysctrlFileSizeLimitEntityList);
    }

    return sysCtrlFileSizeLimitModelList;
  }

  @Override
  public List viewSysCtrlFileSizeLimitSearch(String memberId, String subService) {
    List<SysCtrlFileSizeLimitModel> sysFileSizeModelList =
        new ArrayList<SysCtrlFileSizeLimitModel>();
    List<CasSysctrlFileSizeLimitEntity> sysFileSizeEnList =
        new ArrayList<CasSysctrlFileSizeLimitEntity>();
    try {

      HashMap<String, Object> parameters = new HashMap<String, Object>();
      parameters.put("mdtSysctrlFileSizeLimitPK.memberId", memberId);
      parameters.put("mdtSysctrlFileSizeLimitPK.subService", subService);

      log.debug("parameters---> " + parameters.toString());
      sysFileSizeEnList = (List<CasSysctrlFileSizeLimitEntity>) genericDAO.findAllByCriteria(
          CasSysctrlFileSizeLimitEntity.class, parameters);
      log.debug("---------------sysFileSizeEnList after findAllByCriteria: ------------------" +
          sysFileSizeEnList);


      if (sysFileSizeEnList != null && sysFileSizeEnList.size() > 0) {
        SysCtrlFileSizeLimitLogic sysFileSizeLimitLogic = new SysCtrlFileSizeLimitLogic();
        for (CasSysctrlFileSizeLimitEntity localEntity : sysFileSizeEnList) {
          SysCtrlFileSizeLimitModel sysCtrlFileSizeLimitModel = new SysCtrlFileSizeLimitModel();

          sysCtrlFileSizeLimitModel =
              sysFileSizeLimitLogic.retrieveSysctrlFileSizeLimit(localEntity);

          sysFileSizeModelList.add(sysCtrlFileSizeLimitModel);
        }
      }
    } catch (ObjectNotFoundException onfe) {
      log.error("No Object Exists on DB");
    } catch (Exception e) {
      log.error("Error on viewSysCtrlFileSizeLimitSearch: " + e.getMessage());
      e.printStackTrace();
    }

    return sysFileSizeModelList;
  }


  public boolean createSysCtrlFileSizeLimit(Object obj) {
    try {

      if (obj instanceof SysCtrlFileSizeLimitModel) {
        SysCtrlFileSizeLimitModel sysCtrlFileSizeLimitModel = (SysCtrlFileSizeLimitModel) obj;
        SysCtrlFileSizeLimitLogic sysCtrlFileSizeLimitLogic = new SysCtrlFileSizeLimitLogic();
        CasSysctrlFileSizeLimitEntity casSysctrlFileSizeLimitEntity =
            new CasSysctrlFileSizeLimitEntity();

        casSysctrlFileSizeLimitEntity =
            sysCtrlFileSizeLimitLogic.addSysctrlFileSizeLimit(sysCtrlFileSizeLimitModel);
        genericDAO.saveOrUpdate(casSysctrlFileSizeLimitEntity);
        return true;
      } else {
        log.debug("Unable to convert type to SysCtrlFileSizeLimitModel.");
        return false;
      }
    } catch (Exception e) {
      log.error("Error on createSysCtrlFileSizeLimit: " + e.getMessage());
      e.printStackTrace();
      return false;
    }

  }

  public void generateBatchFileStatsReport(Date fromFrontDate) {
    try {
      log.info("***********Generating PSMD08 Report*****************");
//			2021/10/29 SalehaR NCH-252230 PSMD08 Report v2.0
//			BatchFileStatsReport batchFileStatsReport = new BatchFileStatsReport();
//			batchFileStatsReport.generateReport(fromFrontDate);

      PSMD08_FileStatsBatchExcelReport psmd08FileStatsRpt = new PSMD08_FileStatsBatchExcelReport();
      psmd08FileStatsRpt.generateReport(fromFrontDate);
      log.info("***********PSMD08 Report Completed*****************");
    } catch (Exception e) {
      log.error("<EX> Error on populating PSMD08 Report :" + e.getMessage());
      e.printStackTrace();
    }
  }


  public void generateBatchTxnBillMonthlyReport(Date fromFrontDate, Date toFrontDate,
                                                boolean frontEndRun) {
    try {
      log.info("***********Generating PBBIl05 Report*****************");

      PBBIL05_CSV_BatchMandatesTxnsBillReport pbbil05report =
          new PBBIL05_CSV_BatchMandatesTxnsBillReport();
      pbbil05report.generateReport(fromFrontDate, toFrontDate, frontEndRun);

//			2022/07/04-SalehaR-Old version of report (excel) replaced by CSV
//			PBBIL05_BatchMandatesTxnBillReport pbbil05report = new
//			PBBIL05_BatchMandatesTxnBillReport();
//			pbbil05report.generateReport(fromFrontDate,toFrontDate, frontEndRun);
      log.info("***********PBBIl05 Report Completed*****************");
    } catch (Exception e) {
      log.error("<EX> Error on populating PBBIl05 Report :" + e.getMessage());
      e.printStackTrace();
    }
  }

  @Override
  public List<?> retrievePBMD06RealTimeCreditorTransBilling(String creditorBank, Date reportDate) {
    List<MandateDailyTransModel> mndtDailyTransList = new ArrayList<MandateDailyTransModel>();
    List<MdtAcOpsDailyBillingEntity> dailyBillingList = new ArrayList<MdtAcOpsDailyBillingEntity>();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String repDate = sdf.format(reportDate);

    try {
      StringBuilder sb = new StringBuilder();
      sb.append(
          "SELECT CREDITOR_BANK as creditorBank, DEBTOR_BANK as debtorBank, SUB_SERVICE as " +
              "subService, TXN_TYPE as txnType, ACTION_DATE as actionDate, ");
      sb.append(
          "EXT_MSG_ID as extMsgId, TXN_ID as txnId, MNDT_REF_NUM as mndtRefNum, AUTH_CODE as " +
              "authCode, TXN_STATUS as txnStatus, RESP_DATE as respDate ");
      sb.append("FROM MANOWNER.MDT_AC_OPS_DAILY_BILLING ");
      sb.append(
          "WHERE (TXN_TYPE = 'TT1' OR TXN_TYPE = 'TT3') AND CREDITOR_BANK = '" + creditorBank +
              "' AND TRUNC(ACTION_DATE) = TO_DATE('" + repDate + "','YYYY-MM-DD') ");
      sb.append("UNION ALL ");
      sb.append(
          "SELECT CREDITOR_BANK as creditorBank, DEBTOR_BANK as debtorBank, SUB_SERVICE as " +
              "subService, TXN_TYPE as txnType, ACTION_DATE as actionDate, ");
      sb.append(
          "EXT_MSG_ID as extMsgId, TXN_ID as txnId, MNDT_REF_NUM as mndtRefNum, AUTH_CODE as " +
              "authCode, TXN_STATUS as txnStatus, RESP_DATE as respDate ");
      sb.append("FROM MANOWNER.MDT_AC_ARC_DAILY_BILLING ");
      sb.append(
          "WHERE (TXN_TYPE = 'TT1' OR TXN_TYPE = 'TT3') AND CREDITOR_BANK = '" + creditorBank +
              "' AND TRUNC(ACTION_DATE) = TO_DATE('" + repDate + "','YYYY-MM-DD') ");

      String sqlQuery = sb.toString();
      log.info("PBMD06 sqlQuery: " + sqlQuery);

      List<String> scalarList = new ArrayList<String>();
      scalarList.add("creditorBank");
      scalarList.add("debtorBank");
      scalarList.add("subService");
      scalarList.add("txnType");
      scalarList.add("actionDate");
      scalarList.add("extMsgId");
      scalarList.add("txnId");
      scalarList.add("mndtRefNum");
      scalarList.add("authCode");
      scalarList.add("txnStatus");
      scalarList.add("respDate");

      log.debug("scalarList: " + scalarList);

      dailyBillingList =
          genericDAO.findBySql(sqlQuery, scalarList, MdtAcOpsDailyBillingEntity.class);

      if (dailyBillingList != null && dailyBillingList.size() > 0) {
        for (MdtAcOpsDailyBillingEntity localEntity : dailyBillingList) {
          MandateDailyTransModel localModel = new MandateDailyTransModel();
          localModel = new AdminTranslator().translateDailyBillingEntityToModel(localEntity);
          //						log.debug("localModel ==> "+localModel);
          mndtDailyTransList.add(localModel);
        }
      }
    } catch (ObjectNotFoundException onfe) {
      log.error("No Object Exists on DB");
    } catch (Exception e) {
      log.error("Error on retrieveRealTimeMndtBillingTxnsByCreditor :" + e.getMessage());
      e.printStackTrace();
    }


    return mndtDailyTransList;
  }


  @Override
  public void generateExceptionReport(Date frontDate) {
    try {
      log.info("***********Generating PBMD12 Report*****************");

      Exception_Report_CSV exceptionReport = new Exception_Report_CSV();
      exceptionReport.generateReport(frontDate, true);
      log.info("***********PBMD12 Report Completed*****************");
    } catch (Exception e) {
      log.error("<EX> Error on populating PBMD12 Report :" + e.getMessage());
      e.printStackTrace();
    }
  }

}











