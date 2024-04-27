
package com.bsva.translator;


import com.bsva.commons.model.AcOpsSotEotCntrlModel;
import com.bsva.commons.model.AcOpsTransCtrlMsgModel;
import com.bsva.commons.model.AccountTypeModel;
import com.bsva.commons.model.AdjustmentCategoryModel;
import com.bsva.commons.model.AmendmentCodesModel;
import com.bsva.commons.model.AudReportsModel;
import com.bsva.commons.model.AudSystemProcessModel;
import com.bsva.commons.model.AuditTrackingModel;
import com.bsva.commons.model.CisBranchModel;
import com.bsva.commons.model.CisMemberModel;
import com.bsva.commons.model.CnfgAuthTypeModel;
import com.bsva.commons.model.CnfgRejectReasonCodesModel;
import com.bsva.commons.model.CompParamModel;
import com.bsva.commons.model.ConfgErrorCodesModel;
import com.bsva.commons.model.ConfgSeverityCodesModel;
import com.bsva.commons.model.CurrencyCodesModel;
import com.bsva.commons.model.CustomerParametersModel;
import com.bsva.commons.model.DebitValueTypeModel;
import com.bsva.commons.model.FileSizeLimitModel;
import com.bsva.commons.model.FileStatusCommonsModel;
import com.bsva.commons.model.FrequencyCodesModel;
import com.bsva.commons.model.IamSessionModel;
import com.bsva.commons.model.IncSotEotModel;
import com.bsva.commons.model.LocalInstrumentCodesModel;
import com.bsva.commons.model.MandateDailyTransModel;
import com.bsva.commons.model.MandatesCountCommonsModel;
import com.bsva.commons.model.MdtSysCtrlSysParamModel;
import com.bsva.commons.model.OpsCustParamModel;
import com.bsva.commons.model.OpsFileRegModel;
import com.bsva.commons.model.OpsProcessControlCommonsModel;
import com.bsva.commons.model.OpsProcessControlModel;
import com.bsva.commons.model.OpsRefSeqNumberCommonsModel;
import com.bsva.commons.model.OpsSlaTimesCommonsModel;
import com.bsva.commons.model.OpsStatusDetailsModel;
import com.bsva.commons.model.OpsStatusHdrsModel;
import com.bsva.commons.model.OutSotEotModel;
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
import com.bsva.entities.AudReportsEntity;
import com.bsva.entities.AudSystemProcessEntity;
import com.bsva.entities.AudTrackingEntity;
import com.bsva.entities.CasSysctrlCompParamEntity;
import com.bsva.entities.CasSysctrlCustParamEntity;
import com.bsva.entities.CasSysctrlFileSizeLimitEntity;
import com.bsva.entities.CasSysctrlFileSizeLimitPK;
import com.bsva.entities.CasSysctrlFileStatusEntity;
import com.bsva.entities.CasSysctrlProcessStatusEntity;
import com.bsva.entities.CasSysctrlSchedulerCronEntity;
import com.bsva.entities.CasSysctrlSchedulerEntity;
import com.bsva.entities.CasSysctrlServicesEntity;
import com.bsva.entities.CasSysctrlSlaTimesEntity;
import com.bsva.entities.CasSysctrlSysParamEntity;
import com.bsva.entities.CisBranchEntity;
import com.bsva.entities.CisMemberEntity;
import com.bsva.entities.IamSessionEntity;
import com.bsva.entities.IncSotEotEntityModel;
import com.bsva.entities.MdtAcArcDailyBillingEntity;
import com.bsva.entities.CasOpsDailyBillingEntity;
import com.bsva.entities.CasOpsFileSizeLimitEntity;
import com.bsva.entities.CasOpsFileSizeLimitPK;
import com.bsva.entities.CasOpsMndtCountEntity;
import com.bsva.entities.CasOpsMndtCountPK;
import com.bsva.entities.CasOpsProcessControlsEntity;
import com.bsva.entities.CasOpsSchedulerEntity;
import com.bsva.entities.CasOpsSotEotCtrlEntity;
import com.bsva.entities.CasOpsSotEotCtrlPK;
import com.bsva.entities.CasOpsStatusDetailsEntity;
import com.bsva.entities.CasOpsStatusHdrsEntity;
import com.bsva.entities.CasOpsTransCtrlMsgEntity;
import com.bsva.entities.CasCnfgAccountTypeEntity;
import com.bsva.entities.CasCnfgAdjustmentCatEntity;
import com.bsva.entities.CasCnfgAmendmentCodesEntity;
import com.bsva.entities.CasCnfgAuthTypeEntity;
import com.bsva.entities.CasCnfgCurrencyCodesEntity;
import com.bsva.entities.CasCnfgDebitValueTypeEntity;
import com.bsva.entities.CasCnfgErrorCodesEntity;
import com.bsva.entities.CasCnfgFrequencyCodesEntity;
import com.bsva.entities.CasCnfgLocalInstrCodesEntity;
import com.bsva.entities.CasCnfgReasonCodesEntity;
import com.bsva.entities.CasCnfgReportNamesEntity;
import com.bsva.entities.CasCnfgSequenceTypeEntity;
import com.bsva.entities.CasCnfgSeverityCodesEntity;
import com.bsva.entities.CasOpsCustParamEntity;
import com.bsva.entities.CasOpsFileRegEntity;
import com.bsva.entities.CasOpsRefSeqNrEntity;
import com.bsva.entities.CasOpsRefSeqNrPK;
import com.bsva.entities.CasOpsServicesEntity;
import com.bsva.entities.CasOpsSlaTimesEntity;
import com.bsva.entities.OutSotEotEntityModel;
import com.bsva.entities.SysCisBankEntity;
import com.bsva.entities.SysCisBranchEntity;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import org.apache.log4j.Logger;

/**
 * @author salehas
 */
public class AdminTranslator {
  public static Logger log = Logger.getLogger(AdminTranslator.class);

  public CasCnfgLocalInstrCodesEntity translateCommonsInstrumentModelToEntity(
      LocalInstrumentCodesModel localInstrumentCodesModel) {
    CasCnfgLocalInstrCodesEntity mdtLocalInstrEntity = new CasCnfgLocalInstrCodesEntity();

    mdtLocalInstrEntity.setLocalInstrumentCode(localInstrumentCodesModel.getLocalInstrumentCode());
    mdtLocalInstrEntity.setLocalInstrumentDescription(
        localInstrumentCodesModel.getInstCodeDescription());
    mdtLocalInstrEntity.setActiveInd(localInstrumentCodesModel.getActiveInd());
    mdtLocalInstrEntity.setCreatedBy(localInstrumentCodesModel.getCreatedBy());
    mdtLocalInstrEntity.setCreatedDate(localInstrumentCodesModel.getCreatedDate());
    mdtLocalInstrEntity.setModifiedBy(localInstrumentCodesModel.getModifiedBy());
    mdtLocalInstrEntity.setModifiedDate(localInstrumentCodesModel.getModifiedDate());

    return mdtLocalInstrEntity;
  }

  public LocalInstrumentCodesModel translateLocalInstrumentCodesEntityToCommonsModel(
      CasCnfgLocalInstrCodesEntity mdtLocalInstrumentCodesEntity) {
    LocalInstrumentCodesModel localInstrumentCodesModel = new LocalInstrumentCodesModel();

    localInstrumentCodesModel.setLocalInstrumentCode(
        mdtLocalInstrumentCodesEntity.getLocalInstrumentCode());
    localInstrumentCodesModel.setInstCodeDescription(
        mdtLocalInstrumentCodesEntity.getLocalInstrumentDescription());
    localInstrumentCodesModel.setActiveInd(mdtLocalInstrumentCodesEntity.getActiveInd());
    localInstrumentCodesModel.setCreatedBy(mdtLocalInstrumentCodesEntity.getCreatedBy());
    localInstrumentCodesModel.setCreatedDate(mdtLocalInstrumentCodesEntity.getCreatedDate());
    localInstrumentCodesModel.setModifiedBy(mdtLocalInstrumentCodesEntity.getModifiedBy());
    localInstrumentCodesModel.setModifiedDate(mdtLocalInstrumentCodesEntity.getModifiedDate());

    return localInstrumentCodesModel;
  }

  public CasCnfgReasonCodesEntity transalateCommonsReasonCodesModelToEntity(
      ReasonCodesModel reasonCodesModel) {
    CasCnfgReasonCodesEntity mdtReasCodes = new CasCnfgReasonCodesEntity();

    mdtReasCodes.setReasonCode(reasonCodesModel.getReasonCode());
    mdtReasCodes.setReasonCodeDescription(reasonCodesModel.getReasonCodeDescription());
    mdtReasCodes.setActiveInd(reasonCodesModel.getActiveInd());
    mdtReasCodes.setCreatedBy(reasonCodesModel.getCreatedBy());
    mdtReasCodes.setCreatedDate(reasonCodesModel.getCreatedDate());
    mdtReasCodes.setModifiedBy(reasonCodesModel.getModifiedBy());
    mdtReasCodes.setModifiedDate(reasonCodesModel.getModifiedDate());
    mdtReasCodes.setName(reasonCodesModel.getName());

    return mdtReasCodes;
  }


  public ReasonCodesModel transalateReasCodesToCommonsReasonModel(
      CasCnfgReasonCodesEntity mdtReasonCodes) {
    ReasonCodesModel reasonCodesModel = new ReasonCodesModel();

    reasonCodesModel.setReasonCode(mdtReasonCodes.getReasonCode());
    reasonCodesModel.setReasonCodeDescription(mdtReasonCodes.getReasonCodeDescription());
    reasonCodesModel.setActiveInd(mdtReasonCodes.getActiveInd());
    reasonCodesModel.setCreatedBy(mdtReasonCodes.getCreatedBy());
    reasonCodesModel.setCreatedDate(mdtReasonCodes.getCreatedDate());
    reasonCodesModel.setModifiedBy(mdtReasonCodes.getModifiedBy());
    reasonCodesModel.setModifiedDate(mdtReasonCodes.getModifiedDate());
    reasonCodesModel.setName(mdtReasonCodes.getName());

    return reasonCodesModel;
  }

  public CasCnfgSequenceTypeEntity translateCommonsSequenceTypesModelToEntity(
      SequenceTypesModel sequenceTypesModel) {
    CasCnfgSequenceTypeEntity mdtSeqTypeEntity = new CasCnfgSequenceTypeEntity();

    mdtSeqTypeEntity.setSeqTypeCode(sequenceTypesModel.getSequenceCode());
    mdtSeqTypeEntity.setSeqTypeDesc(sequenceTypesModel.getSequenceDescription());
    mdtSeqTypeEntity.setActiveInd(sequenceTypesModel.getActiveInd());
    mdtSeqTypeEntity.setCreatedBy(sequenceTypesModel.getCreatedBy());
    mdtSeqTypeEntity.setCreatedDate(sequenceTypesModel.getCreatedDate());
    mdtSeqTypeEntity.setModifiedBy(sequenceTypesModel.getModifiedBy());
    mdtSeqTypeEntity.setModifiedDate(sequenceTypesModel.getModifiedDate());
    return mdtSeqTypeEntity;
  }


  public SequenceTypesModel translateSequenceTypeEntityToSequenceTypesCommonsModel(
      CasCnfgSequenceTypeEntity mdtSequenceTypeEntity) {
    SequenceTypesModel sequenceTypesModel = new SequenceTypesModel();

    sequenceTypesModel.setSequenceCode(mdtSequenceTypeEntity.getSeqTypeCode());
    sequenceTypesModel.setSequenceDescription(mdtSequenceTypeEntity.getSeqTypeDesc());
    sequenceTypesModel.setActiveInd(mdtSequenceTypeEntity.getActiveInd());
    sequenceTypesModel.setCreatedBy(mdtSequenceTypeEntity.getCreatedBy());
    sequenceTypesModel.setCreatedDate(mdtSequenceTypeEntity.getCreatedDate());
    sequenceTypesModel.setModifiedBy(mdtSequenceTypeEntity.getModifiedBy());
    sequenceTypesModel.setModifiedDate(mdtSequenceTypeEntity.getModifiedDate());
    return sequenceTypesModel;
  }

  public CurrencyCodesModel translateCurrencyCodesEntityToCommonsModel(
      CasCnfgCurrencyCodesEntity mdtCurrencyCodesEntity) {
    CurrencyCodesModel currencyCodesModel = new CurrencyCodesModel();

    currencyCodesModel.setAlphaCurrCode(mdtCurrencyCodesEntity.getAlphaCurrCode());
    currencyCodesModel.setCountryCode(mdtCurrencyCodesEntity.getCountryCode());
    currencyCodesModel.setActiveInd(mdtCurrencyCodesEntity.getActiveInd());
    currencyCodesModel.setCurrCodeDesc(mdtCurrencyCodesEntity.getCurrCodeDesc());
    currencyCodesModel.setNumCurrCode(mdtCurrencyCodesEntity.getNumCurrCode());
    currencyCodesModel.setCreatedBy(mdtCurrencyCodesEntity.getCreatedBy());
    currencyCodesModel.setCreatedDate(mdtCurrencyCodesEntity.getCreatedDate());
    currencyCodesModel.setModifiedBy(mdtCurrencyCodesEntity.getModifiedBy());
    currencyCodesModel.setModifiedDate(mdtCurrencyCodesEntity.getModifiedDate());

    return currencyCodesModel;
  }

  public DebitValueTypeModel translateDebitValueTypeEntityToCommonsModel(
      CasCnfgDebitValueTypeEntity mdtDebitValueTypeEntity) {
    DebitValueTypeModel debitValueTypeModel = new DebitValueTypeModel();

    debitValueTypeModel.setDebValueTypeCode(mdtDebitValueTypeEntity.getDebValueTypeCode());
    debitValueTypeModel.setDebValueTypeDesc(mdtDebitValueTypeEntity.getDebValueTypeDescription());
    debitValueTypeModel.setActiveInd(mdtDebitValueTypeEntity.getActiveInd());
    debitValueTypeModel.setCreatedBy(mdtDebitValueTypeEntity.getCreatedBy());
    debitValueTypeModel.setCreatedDate(mdtDebitValueTypeEntity.getCreatedDate());
    debitValueTypeModel.setModifiedBy(mdtDebitValueTypeEntity.getModifiedBy());
    debitValueTypeModel.setModifiedDate(mdtDebitValueTypeEntity.getModifiedDate());

    return debitValueTypeModel;
  }

  public CasCnfgDebitValueTypeEntity translateCommonsDebitValueModelToEntity(
      DebitValueTypeModel debitValueTypeModel) {
    CasCnfgDebitValueTypeEntity mdtDebitValueTypeEntity = new CasCnfgDebitValueTypeEntity();

    mdtDebitValueTypeEntity.setDebValueTypeCode(debitValueTypeModel.getDebValueTypeCode());
    mdtDebitValueTypeEntity.setDebValueTypeDescription(debitValueTypeModel.getDebValueTypeDesc());
    mdtDebitValueTypeEntity.setActiveInd(debitValueTypeModel.getActiveInd());
    mdtDebitValueTypeEntity.setCreatedBy(debitValueTypeModel.getCreatedBy());
    mdtDebitValueTypeEntity.setCreatedDate(debitValueTypeModel.getCreatedDate());
    mdtDebitValueTypeEntity.setModifiedBy(debitValueTypeModel.getModifiedBy());
    mdtDebitValueTypeEntity.setModifiedDate(debitValueTypeModel.getModifiedDate());

    return mdtDebitValueTypeEntity;
  }

  public CasOpsTransCtrlMsgEntity translateCommonsAcOpsTransCtrlModelToEntity(
      AcOpsTransCtrlMsgModel acOpsTransCtrlMsgModel) {

    CasOpsTransCtrlMsgEntity casOpsTransCtrlMsgEntity = new CasOpsTransCtrlMsgEntity();
    casOpsTransCtrlMsgEntity.setActiveInd(acOpsTransCtrlMsgModel.getActiveInd());
    casOpsTransCtrlMsgEntity.setCtrlMsgId(acOpsTransCtrlMsgModel.getCtrlMsgId());
    casOpsTransCtrlMsgEntity.setMemberIdFm(acOpsTransCtrlMsgModel.getMemberIdFm());
    casOpsTransCtrlMsgEntity.setMemberIdTo(acOpsTransCtrlMsgModel.getMemberIdTo());
    casOpsTransCtrlMsgEntity.setMsgType(acOpsTransCtrlMsgModel.getMsgType());
    casOpsTransCtrlMsgEntity.setNrOfFiles(acOpsTransCtrlMsgModel.getNrOfFiles());
    casOpsTransCtrlMsgEntity.setNrOfFilesReceived(acOpsTransCtrlMsgModel.getNrOfFilesReceived());
    casOpsTransCtrlMsgEntity.setNrOfRecords(acOpsTransCtrlMsgModel.getNrOfRecords());
    casOpsTransCtrlMsgEntity.setProcessDate(acOpsTransCtrlMsgModel.getProcessDate());
    casOpsTransCtrlMsgEntity.setServiceId(acOpsTransCtrlMsgModel.getServiceId());
    casOpsTransCtrlMsgEntity.setSystemStatus(acOpsTransCtrlMsgModel.getSystemStatus());
    casOpsTransCtrlMsgEntity.setValidRecordsReceived(
        acOpsTransCtrlMsgModel.getValidRecordsReceived());
    casOpsTransCtrlMsgEntity.setValueOfRecords(acOpsTransCtrlMsgModel.getValueOfRecords());
    casOpsTransCtrlMsgEntity.setValueOfRecordsCurr(
        acOpsTransCtrlMsgModel.getValueOfRecordsCurr());
    return casOpsTransCtrlMsgEntity;


  }

  public AcOpsTransCtrlMsgModel translateMdtAcOpsTransCtrlMsgEntityToCommons(
      CasOpsTransCtrlMsgEntity casOpsTransCtrlMsgEntity) {
    AcOpsTransCtrlMsgModel acOpsTransCtrlMsgModel = new AcOpsTransCtrlMsgModel();

    acOpsTransCtrlMsgModel.setActiveInd(casOpsTransCtrlMsgEntity.getActiveInd());
    acOpsTransCtrlMsgModel.setCtrlMsgId(casOpsTransCtrlMsgEntity.getCtrlMsgId());
    acOpsTransCtrlMsgModel.setMemberIdFm(casOpsTransCtrlMsgEntity.getMemberIdFm());
    acOpsTransCtrlMsgModel.setMemberIdTo(casOpsTransCtrlMsgEntity.getMemberIdTo());
    acOpsTransCtrlMsgModel.setMsgType(casOpsTransCtrlMsgEntity.getMsgType());
    acOpsTransCtrlMsgModel.setNrOfFiles(casOpsTransCtrlMsgEntity.getNrOfFiles());
    acOpsTransCtrlMsgModel.setNrOfFilesReceived(casOpsTransCtrlMsgEntity.getNrOfFilesReceived());
    acOpsTransCtrlMsgModel.setNrOfRecords(casOpsTransCtrlMsgEntity.getNrOfRecords());
    acOpsTransCtrlMsgModel.setNrOfRecordsReceived(
        casOpsTransCtrlMsgEntity.getNrOfRecordsReceived());
    acOpsTransCtrlMsgModel.setProcessDate(casOpsTransCtrlMsgEntity.getProcessDate());
    acOpsTransCtrlMsgModel.setServiceId(casOpsTransCtrlMsgEntity.getServiceId());
    acOpsTransCtrlMsgModel.setSystemStatus(casOpsTransCtrlMsgEntity.getSystemStatus());
    acOpsTransCtrlMsgModel.setValidRecordsReceived(
        casOpsTransCtrlMsgEntity.getValidRecordsReceived());
    acOpsTransCtrlMsgModel.setValueOfRecords(casOpsTransCtrlMsgEntity.getValueOfRecords());
    acOpsTransCtrlMsgModel.setValueOfRecordsCurr(
        casOpsTransCtrlMsgEntity.getValueOfRecordsCurr());

    return acOpsTransCtrlMsgModel;
  }


  public SystemParameterModel translateSystemParameterEntityToCommonsModel(
      CasSysctrlSysParamEntity mdtsystemParameterEntity) {
    SystemParameterModel systemparameterModel = new SystemParameterModel();


    systemparameterModel.setSysName(mdtsystemParameterEntity.getSysName());
    systemparameterModel.setDefCurr(mdtsystemParameterEntity.getDefCurr());
    systemparameterModel.setActiveInd(mdtsystemParameterEntity.getActiveInd());
    systemparameterModel.setInactiveDuration(mdtsystemParameterEntity.getInactiveDuration());
    systemparameterModel.setSysType(mdtsystemParameterEntity.getSysType());
    systemparameterModel.setSodRunInd(mdtsystemParameterEntity.getSodRunInd());
    systemparameterModel.setEodRunInd(mdtsystemParameterEntity.getEodRunInd());
    systemparameterModel.setCisDwnldInd(mdtsystemParameterEntity.getCisDwnldInd());
    systemparameterModel.setCisDwnldDate(mdtsystemParameterEntity.getCisDwnldDate());
    systemparameterModel.setProcessDate(mdtsystemParameterEntity.getProcessDate());
    systemparameterModel.setNextMondayHolInd(mdtsystemParameterEntity.getNextMondayHolInd());
    systemparameterModel.setEasterDaysInd(mdtsystemParameterEntity.getEasterDaysInd());
    systemparameterModel.setArchivePeriod(mdtsystemParameterEntity.getArchivePeriod());
    systemparameterModel.setForcecloseReason(mdtsystemParameterEntity.getForcecloseReason());
    systemparameterModel.setSysCloseRunInd(mdtsystemParameterEntity.getSysCloseRunInd());
    systemparameterModel.setInBalanceInd(mdtsystemParameterEntity.getInBalanceInd());
    systemparameterModel.setResponsePeriod(mdtsystemParameterEntity.getResponsePeriod());
    systemparameterModel.setIamPort(mdtsystemParameterEntity.getIamPort());
    systemparameterModel.setItemLimit(mdtsystemParameterEntity.getItemLimit());

    return systemparameterModel;
  }

  public CasSysctrlSysParamEntity translateCommonsSystemParameterModelToEntity(
      SystemParameterModel systemParameterModel) {
    CasSysctrlSysParamEntity mdtSystemParameterEntity = new CasSysctrlSysParamEntity();


    mdtSystemParameterEntity.setSysType(systemParameterModel.getSysType());


    mdtSystemParameterEntity.setSysName(systemParameterModel.getSysName());
    mdtSystemParameterEntity.setDefCurr(systemParameterModel.getDefCurr());
    mdtSystemParameterEntity.setActiveInd(systemParameterModel.getActiveInd());
    mdtSystemParameterEntity.setInactiveDuration(systemParameterModel.getInactiveDuration());
    mdtSystemParameterEntity.setSysType(systemParameterModel.getSysType());
    mdtSystemParameterEntity.setSodRunInd(systemParameterModel.getSodRunInd());
    mdtSystemParameterEntity.setEodRunInd(systemParameterModel.getEodRunInd());
    mdtSystemParameterEntity.setCisDwnldInd(systemParameterModel.getCisDwnldInd());
    mdtSystemParameterEntity.setCisDwnldDate(systemParameterModel.getCisDwnldDate());
    mdtSystemParameterEntity.setProcessDate(systemParameterModel.getProcessDate());
    mdtSystemParameterEntity.setNextMondayHolInd(systemParameterModel.getNextMondayHolInd());
    mdtSystemParameterEntity.setEasterDaysInd(systemParameterModel.getEasterDaysInd());
    mdtSystemParameterEntity.setArchivePeriod(systemParameterModel.getArchivePeriod());
    mdtSystemParameterEntity.setForcecloseReason(systemParameterModel.getForcecloseReason());
    mdtSystemParameterEntity.setSysCloseRunInd(systemParameterModel.getSysCloseRunInd());
    mdtSystemParameterEntity.setInBalanceInd(systemParameterModel.getInBalanceInd());
    mdtSystemParameterEntity.setResponsePeriod(systemParameterModel.getResponsePeriod());
    mdtSystemParameterEntity.setIamPort(systemParameterModel.getIamPort());
    mdtSystemParameterEntity.setItemLimit(systemParameterModel.getItemLimit());

    return mdtSystemParameterEntity;


  }

  public ConfgSeverityCodesModel translateCnfgSeverityCodesEntityToWebModel(
      CasCnfgSeverityCodesEntity casCnfgSeverityCodesEntity) {

    ConfgSeverityCodesModel confgSeverityCodesModel = new ConfgSeverityCodesModel();

    confgSeverityCodesModel.setSeverityCode(casCnfgSeverityCodesEntity.getSeverityCode());
    confgSeverityCodesModel.setSeverityCodeDesc(casCnfgSeverityCodesEntity.getSeverityCodeDesc());
    confgSeverityCodesModel.setCreatedBy(casCnfgSeverityCodesEntity.getCreatedBy());
    confgSeverityCodesModel.setCreatedDate(casCnfgSeverityCodesEntity.getCreatedDate());
    confgSeverityCodesModel.setModifiedBy(casCnfgSeverityCodesEntity.getModifiedBy());
    confgSeverityCodesModel.setModifiedBy(casCnfgSeverityCodesEntity.getModifiedBy());

    log.debug("Translator CnfgSeverity: " + confgSeverityCodesModel);
    return confgSeverityCodesModel;
  }


  public CasCnfgSeverityCodesEntity translateCommonsConfgSeverityCodesModelToEntity(
      ConfgSeverityCodesModel confgSeverityCodesModel) {

    CasCnfgSeverityCodesEntity casCnfgSeverityCodesEntity = new CasCnfgSeverityCodesEntity();

    casCnfgSeverityCodesEntity.setSeverityCode(confgSeverityCodesModel.getSeverityCode());
    casCnfgSeverityCodesEntity.setSeverityCodeDesc(confgSeverityCodesModel.getSeverityCodeDesc());
    casCnfgSeverityCodesEntity.setCreatedBy(confgSeverityCodesModel.getCreatedBy());
    casCnfgSeverityCodesEntity.setCreatedDate(confgSeverityCodesModel.getCreatedDate());
    casCnfgSeverityCodesEntity.setModifiedBy(confgSeverityCodesModel.getModifiedBy());
    casCnfgSeverityCodesEntity.setModifiedBy(confgSeverityCodesModel.getModifiedBy());

    return casCnfgSeverityCodesEntity;


  }


  public CasCnfgFrequencyCodesEntity translateCommnsFrequencyCodeModelToEntity(
      FrequencyCodesModel frequencyCodesModel) {
    CasCnfgFrequencyCodesEntity mdtFrequencyCodesEntity = new CasCnfgFrequencyCodesEntity();

    mdtFrequencyCodesEntity.setFrequencyCode(frequencyCodesModel.getFrequencyCode());
    mdtFrequencyCodesEntity.setFrequencyCodeDescription(
        frequencyCodesModel.getFrequencyCodeDescription());
    mdtFrequencyCodesEntity.setActiveInd(frequencyCodesModel.getActiveInd());
    mdtFrequencyCodesEntity.setCreatedBy(frequencyCodesModel.getCreatedBy());
    mdtFrequencyCodesEntity.setCreatedDate(frequencyCodesModel.getCreatedDate());
    mdtFrequencyCodesEntity.setModifiedBy(frequencyCodesModel.getModifiedBy());
    mdtFrequencyCodesEntity.setModifiedDate(frequencyCodesModel.getModifiedDate());

    return mdtFrequencyCodesEntity;
  }


  public FrequencyCodesModel translateFrequencyCodesEntityToCommonsModel(
      CasCnfgFrequencyCodesEntity mdtFrequencyCodesEntity) {
    FrequencyCodesModel frequencyCodesModel = new FrequencyCodesModel();

    frequencyCodesModel.setFrequencyCode(mdtFrequencyCodesEntity.getFrequencyCode());
    frequencyCodesModel.setFrequencyCodeDescription(
        mdtFrequencyCodesEntity.getFrequencyCodeDescription());
    frequencyCodesModel.setActiveInd(mdtFrequencyCodesEntity.getActiveInd());
    frequencyCodesModel.setCreatedBy(mdtFrequencyCodesEntity.getCreatedBy());
    frequencyCodesModel.setCreatedDate(mdtFrequencyCodesEntity.getCreatedDate());
    frequencyCodesModel.setModifiedBy(mdtFrequencyCodesEntity.getModifiedBy());
    frequencyCodesModel.setModifiedDate(mdtFrequencyCodesEntity.getModifiedDate());

    return frequencyCodesModel;

  }

  public CurrencyCodesModel translateCurrCodesToCommonsCurrencyModel(
      CasCnfgCurrencyCodesEntity currencyEntity) {
    CurrencyCodesModel currencyCodesModel = new CurrencyCodesModel();
    currencyCodesModel.setAlphaCurrCode(currencyEntity.getAlphaCurrCode());
    currencyCodesModel.setCountryCode(currencyEntity.getCountryCode());
    currencyCodesModel.setActiveInd(currencyEntity.getActiveInd());
    currencyCodesModel.setCurrCodeDesc(currencyEntity.getCurrCodeDesc());
    currencyCodesModel.setNumCurrCode(currencyEntity.getNumCurrCode());
    currencyCodesModel.setCreatedBy(currencyEntity.getCreatedBy());
    currencyCodesModel.setCreatedDate(currencyEntity.getCreatedDate());
    currencyCodesModel.setModifiedBy(currencyEntity.getModifiedBy());
    currencyCodesModel.setModifiedDate(currencyEntity.getModifiedDate());

    return currencyCodesModel;
  }

  public CasCnfgCurrencyCodesEntity translateCommonsCurrencyCodesModelToEntity(
      CurrencyCodesModel currencyCodesModel) {

    CasCnfgCurrencyCodesEntity mdtCurrencyCodesEntity = new CasCnfgCurrencyCodesEntity();

    mdtCurrencyCodesEntity.setAlphaCurrCode(currencyCodesModel.getAlphaCurrCode());
    mdtCurrencyCodesEntity.setCountryCode(currencyCodesModel.getCountryCode());
    mdtCurrencyCodesEntity.setActiveInd(currencyCodesModel.getActiveInd());
    mdtCurrencyCodesEntity.setCurrCodeDesc(currencyCodesModel.getCurrCodeDesc());
    mdtCurrencyCodesEntity.setNumCurrCode(currencyCodesModel.getNumCurrCode());
    mdtCurrencyCodesEntity.setCreatedBy(currencyCodesModel.getCreatedBy());
    mdtCurrencyCodesEntity.setCreatedDate(currencyCodesModel.getCreatedDate());
    mdtCurrencyCodesEntity.setModifiedBy(currencyCodesModel.getModifiedBy());
    mdtCurrencyCodesEntity.setModifiedDate(currencyCodesModel.getModifiedDate());

    return mdtCurrencyCodesEntity;
  }


  public CasCnfgErrorCodesEntity translateCommonsErrorModelToEntity(
      ConfgErrorCodesModel errorCodesModel) {
    CasCnfgErrorCodesEntity mdtErrorEntity = new CasCnfgErrorCodesEntity();

    mdtErrorEntity.setErrorCode(errorCodesModel.getErrorCode());
    mdtErrorEntity.setErrorCodeDesc(errorCodesModel.getErrorDescription());
    mdtErrorEntity.setActiveInd(errorCodesModel.getActiveInd());
    mdtErrorEntity.setSeverity(errorCodesModel.getSeverity());
    mdtErrorEntity.setCreatedBy(errorCodesModel.getCreatedBy());
    mdtErrorEntity.setCreatedDate(errorCodesModel.getCreatedDate());
    mdtErrorEntity.setModifiedBy(errorCodesModel.getModifiedBy());
    mdtErrorEntity.setModifiedDate(errorCodesModel.getModifiedDate());

    return mdtErrorEntity;
  }


  public ConfgErrorCodesModel translateErrorCodesEntityToCommonsModel(
      CasCnfgErrorCodesEntity mdtErrorCodesEntity) {
    ConfgErrorCodesModel errorCodesModel = new ConfgErrorCodesModel();

    errorCodesModel.setErrorCode(mdtErrorCodesEntity.getErrorCode());
    errorCodesModel.setErrorDescription(mdtErrorCodesEntity.getErrorCodeDesc());
    errorCodesModel.setActiveInd(mdtErrorCodesEntity.getActiveInd());
    errorCodesModel.setSeverity(mdtErrorCodesEntity.getSeverity());
    errorCodesModel.setCreatedBy(mdtErrorCodesEntity.getCreatedBy());
    errorCodesModel.setCreatedDate(mdtErrorCodesEntity.getCreatedDate());
    errorCodesModel.setModifiedBy(mdtErrorCodesEntity.getModifiedBy());
    errorCodesModel.setModifiedDate(mdtErrorCodesEntity.getModifiedDate());

    return errorCodesModel;
  }

  public ReasonCodesModel translateReasonCodesEntityToCommonsModel(
      CasCnfgReasonCodesEntity mdtReasonCodesEntity) {
    ReasonCodesModel reasonCodeModel = new ReasonCodesModel();

    reasonCodeModel.setReasonCode(mdtReasonCodesEntity.getReasonCode());
    reasonCodeModel.setReasonCodeDescription(mdtReasonCodesEntity.getReasonCodeDescription());
    reasonCodeModel.setActiveInd(mdtReasonCodesEntity.getActiveInd());
    reasonCodeModel.setCreatedBy(mdtReasonCodesEntity.getCreatedBy());
    reasonCodeModel.setCreatedDate(mdtReasonCodesEntity.getCreatedDate());
    reasonCodeModel.setModifiedBy(mdtReasonCodesEntity.getModifiedBy());
    reasonCodeModel.setModifiedDate(mdtReasonCodesEntity.getModifiedDate());
    reasonCodeModel.setName(mdtReasonCodesEntity.getName());

    return reasonCodeModel;
  }

  public CisMemberModel translateCisMemberEntityToCommonsModel(CisMemberEntity localEntity) {

    CisMemberModel cisMemberModel = new CisMemberModel();

    cisMemberModel.setAbbrevCurrency(localEntity.getAbbrevCurrency());
    cisMemberModel.setAbbrevMemberName(localEntity.getAbbrevMemberName());
    cisMemberModel.setCountry(localEntity.getCountry());
    cisMemberModel.setCurrency(localEntity.getCurrency());
    cisMemberModel.setMemberBranchEndRange(localEntity.getMemberBranchEndRange());
    cisMemberModel.setMemberBranchStartRange(localEntity.getMemberBranchStartRange());
    cisMemberModel.setMemberName(localEntity.getMemberName());
    cisMemberModel.setMemberNo(localEntity.getMemberNo());
    cisMemberModel.setMnemonicMemberName(localEntity.getMnemonicMemberName());
    cisMemberModel.setSamosBicCodeLive(localEntity.getSamosBicCodeLive());

    return cisMemberModel;
  }

  public CisBranchModel translateCisBranchEntityToCommonsModel(CisBranchEntity localEntity) {

    CisBranchModel cisBranchModel = new CisBranchModel();

    cisBranchModel.setAbbrDivisionName(localEntity.getAbbrDivisionName());
    cisBranchModel.setAddressLine1(localEntity.getAddressLine1());
    cisBranchModel.setAddressLine2(localEntity.getAddressLine2());
    cisBranchModel.setAddressLine3(localEntity.getAddressLine3());
    cisBranchModel.setAgencyBranchNo(localEntity.getAgencyBranchNo());
    cisBranchModel.setBranchName(localEntity.getBranchName());
    cisBranchModel.setBranchNo(localEntity.getBranchNo());
    cisBranchModel.setDivision(localEntity.getDivision());
    cisBranchModel.setInstitutionType(localEntity.getInstitutionType());
    cisBranchModel.setMemberNo(localEntity.getMemberNo());


    // TODO Auto-generated method stub
    return cisBranchModel;
  }

  public CustomerParametersModel translateCustomerParametersEntityToCommonsModel(
      CasSysctrlCustParamEntity custParamEntity) {
    CustomerParametersModel custParamModel = new CustomerParametersModel();

    custParamModel.setManAmdXsdNs(custParamEntity.getManAmdXsdNs());
    custParamModel.setManCanXsdNs(custParamEntity.getManCanXsdNs());
    custParamModel.setManAccpXsdNs(custParamEntity.getManAccpXsdNs());
    custParamModel.setActiveInd(custParamEntity.getActiveInd());
    custParamModel.setInstId(custParamEntity.getInstId());
    custParamModel.setManInitXsdNs(custParamEntity.getManInitXsdNs());
    custParamModel.setMdtReqIdReuseInd(custParamEntity.getMdtReqIdReuseInd());
    custParamModel.setMdteReqXsdNs(custParamEntity.getMdteReqXsdNs());
    custParamModel.setMdteRespXsdNs(custParamEntity.getMdteRespXsdNs());
    custParamModel.setManStatusRepXsdNs(custParamEntity.getManStatusRepXsdNs());
    custParamModel.setManConfirmXsdNs(custParamEntity.getManConfirmXsdNs());
    custParamModel.setProcessDay(custParamEntity.getProcessDay());
    custParamModel.setCreatedBy(custParamEntity.getCreatedBy());
    custParamModel.setCreatedDate(custParamEntity.getCreatedDate());
    custParamModel.setModifiedBy(custParamEntity.getModifiedBy());
    custParamModel.setModifiedDate(custParamEntity.getModifiedDate());
    custParamModel.setCisDwnldDate(custParamEntity.getCisDwnldDate());
    custParamModel.setCisDwnldInd(custParamEntity.getCisDwnldInd());
    return custParamModel;
  }


  public CustomerParametersModel translateCustomerParametersEntityToCommonsModel(
      CasOpsCustParamEntity custParamEntity) {
    CustomerParametersModel custParamModel = new CustomerParametersModel();

    custParamModel.setActiveInd(custParamEntity.getActiveInd());
    custParamModel.setInstId(custParamEntity.getInstId());
    custParamModel.setManInitXsdNs(custParamEntity.getManInitXsdNs());
    custParamModel.setManAmdXsdNs(custParamEntity.getManAmdXsdNs());
    custParamModel.setManCanXsdNs(custParamEntity.getManCanXsdNs());
    custParamModel.setManAccpXsdNs(custParamEntity.getManAccpXsdNs());
    custParamModel.setManStatusRepXsdNs(custParamEntity.getManStatusRepXsdNs());

    return custParamModel;
  }


  public CasSysctrlCustParamEntity transalateCommonsCustomerParametersModelToEntity(
      CustomerParametersModel custParamModel) {
    // TODO Auto-generated method stub

    CasSysctrlCustParamEntity custParamEntity = new CasSysctrlCustParamEntity();

    custParamEntity.setManAmdXsdNs(custParamModel.getManAmdXsdNs());
    custParamEntity.setManCanXsdNs(custParamModel.getManCanXsdNs());
    custParamEntity.setManAccpXsdNs(custParamModel.getManAccpXsdNs());
    custParamEntity.setActiveInd(custParamModel.getActiveInd());
    custParamEntity.setInstId(custParamModel.getInstId());
    custParamEntity.setManInitXsdNs(custParamModel.getManInitXsdNs());
    custParamEntity.setMdtReqIdReuseInd(custParamModel.getMdtReqIdReuseInd());
    custParamEntity.setMdteReqXsdNs(custParamModel.getMdteReqXsdNs());
    custParamEntity.setMdteRespXsdNs(custParamModel.getMdteRespXsdNs());
    custParamEntity.setManStatusRepXsdNs(custParamModel.getManStatusRepXsdNs());
    custParamEntity.setManConfirmXsdNs(custParamModel.getManConfirmXsdNs());
    custParamEntity.setProcessDay(custParamModel.getProcessDay());
    custParamEntity.setCreatedBy(custParamModel.getCreatedBy());
    custParamEntity.setCreatedDate(custParamModel.getCreatedDate());
    custParamEntity.setModifiedBy(custParamModel.getModifiedBy());
    custParamEntity.setModifiedDate(custParamModel.getModifiedDate());
    custParamEntity.setCisDwnldDate(custParamModel.getCisDwnldDate());
    custParamEntity.setCisDwnldInd(custParamModel.getCisDwnldInd());

    return custParamEntity;
  }


  public CasOpsFileRegEntity translateCommonsOpsFileRegModelToEntity(
      OpsFileRegModel opsFileRegModel) {

    CasOpsFileRegEntity casOpsFileRegEntity = new CasOpsFileRegEntity();

    casOpsFileRegEntity.setFileName(opsFileRegModel.getFileName());
    casOpsFileRegEntity.setFilepath(opsFileRegModel.getFilepath());
    casOpsFileRegEntity.setNameSpace(opsFileRegModel.getNameSpace());
    casOpsFileRegEntity.setProcessDate(opsFileRegModel.getProcessDate());
    casOpsFileRegEntity.setReason(opsFileRegModel.getReason());
    casOpsFileRegEntity.setStatus(opsFileRegModel.getStatus());
    casOpsFileRegEntity.setGrpHdrMsgId(opsFileRegModel.getGrpHdrMsgId());
    casOpsFileRegEntity.setExtractMsgId(opsFileRegModel.getExtractMsgId());
    casOpsFileRegEntity.setInOutInd(opsFileRegModel.getInOutInd());
    casOpsFileRegEntity.setOnlineInd(opsFileRegModel.getOnlineInd());


    return casOpsFileRegEntity;
  }

  public OpsFileRegModel translateCommonsToOpsFileRegEntity(
      CasOpsFileRegEntity casOpsFileRegEntity) {
    OpsFileRegModel opsFileRegModel = new OpsFileRegModel();

    opsFileRegModel.setFileName(casOpsFileRegEntity.getFileName());
    opsFileRegModel.setFilepath(casOpsFileRegEntity.getFilepath());
    opsFileRegModel.setNameSpace(casOpsFileRegEntity.getNameSpace());
    opsFileRegModel.setProcessDate(casOpsFileRegEntity.getProcessDate());
    opsFileRegModel.setReason(casOpsFileRegEntity.getReason());
    opsFileRegModel.setStatus(casOpsFileRegEntity.getStatus());
    opsFileRegModel.setGrpHdrMsgId(casOpsFileRegEntity.getGrpHdrMsgId());
    opsFileRegModel.setExtractMsgId(casOpsFileRegEntity.getExtractMsgId());
    opsFileRegModel.setInOutInd(casOpsFileRegEntity.getInOutInd());
    opsFileRegModel.setOnlineInd(casOpsFileRegEntity.getOnlineInd());

    return opsFileRegModel;
  }

  public SysctrlCompParamModel translateSysctrlCompParamEntitytoCommonsModel(
      CasSysctrlCompParamEntity localEntity) {

    SysctrlCompParamModel sysctrlCompParamModel = new SysctrlCompParamModel();

    sysctrlCompParamModel.setActiveInd(localEntity.getActiveInd());
    sysctrlCompParamModel.setAddress1(localEntity.getAddress1());
    sysctrlCompParamModel.setAddress2(localEntity.getAddress2());
    sysctrlCompParamModel.setCity(localEntity.getCity());
    sysctrlCompParamModel.setCompAbbrevName(localEntity.getCompAbbrevName());
    sysctrlCompParamModel.setCompId(localEntity.getCompId());
    sysctrlCompParamModel.setCompName(localEntity.getCompName());
    sysctrlCompParamModel.setContactName(localEntity.getContactName());
    sysctrlCompParamModel.setDialCode(localEntity.getDialCode());
    sysctrlCompParamModel.setEmail(localEntity.getEmail());
    sysctrlCompParamModel.setFaxCode(localEntity.getFaxCode());
    sysctrlCompParamModel.setFaxNr(localEntity.getFaxNr());
    sysctrlCompParamModel.setPostCode(localEntity.getPostCode());
    sysctrlCompParamModel.setRegistrationNr(localEntity.getRegistrationNr());
    sysctrlCompParamModel.setTelNr(localEntity.getTelNr());
    sysctrlCompParamModel.setTransamissionInd(localEntity.getTransamissionInd());
    sysctrlCompParamModel.setAchId(localEntity.getAchId());
    sysctrlCompParamModel.setAchInstId(localEntity.getAchInstId());
    return sysctrlCompParamModel;
  }

//	NOt needed for C&A
//	public CronTimesModel translateMdtSysctrlCronEntitytoCommonsModel(	MdtSysctrlCronEntity
//	localEntity)
//	{
//		
//		CronTimesModel sysctrlCronTimeModel = new CronTimesModel();
//		sysctrlCronTimeModel.setActiveInd(localEntity.getActiveInd());
//		sysctrlCronTimeModel.setCreatedBy(localEntity.getCreatedBy());
//		sysctrlCronTimeModel.setCreatedDate(localEntity.getCreatedDate());
//		sysctrlCronTimeModel.setCronTime(localEntity.getCronTime());
//		sysctrlCronTimeModel.setModifiedBy(localEntity.getModifiedBy());
//		sysctrlCronTimeModel.setModifiedDate(localEntity.getModifiedDate());
//		sysctrlCronTimeModel.setProcessName(localEntity.getProcessName());
//        
//	
//		return sysctrlCronTimeModel;
//	}
//	
//	public MdtSysctrlCronEntity translateCommonsCronTimeModetoCommonsEntity(CronTimesModel
//	sysctrlCronTimeModel)
//	{
//		
//		MdtSysctrlCronEntity mdtSysctrlCronTimeEntity = new MdtSysctrlCronEntity();
//		
//       mdtSysctrlCronTimeEntity.setActiveInd(sysctrlCronTimeModel.getActiveInd());
//       mdtSysctrlCronTimeEntity.setCreatedBy(sysctrlCronTimeModel.getCreatedBy());
//       mdtSysctrlCronTimeEntity.setCreatedDate(sysctrlCronTimeModel.getCreatedDate());
//       mdtSysctrlCronTimeEntity.setCronTime(sysctrlCronTimeModel.getCronTime());
//       mdtSysctrlCronTimeEntity.setModifiedBy(sysctrlCronTimeModel.getModifiedBy());
//       mdtSysctrlCronTimeEntity.setModifiedDate(sysctrlCronTimeModel.getModifiedDate());
//       mdtSysctrlCronTimeEntity.setProcessName(sysctrlCronTimeModel.getProcessName());
//       
//
//		return mdtSysctrlCronTimeEntity;
//	}
//	

//	public MdtOpsCronEntity translateCommonsOpsCronTimeModelTosEntity(OpsCronTimeModel
//	opsCronTimeModel)
//	{
//
//		MdtOpsCronEntity mdtOpsCronEntity = new MdtOpsCronEntity();
//
//		mdtOpsCronEntity.setActiveInd(opsCronTimeModel.getActiveInd());
//		mdtOpsCronEntity.setCreatedBy(opsCronTimeModel.getCreatedBy());
//		mdtOpsCronEntity.setCreatedDate(opsCronTimeModel.getCreatedDate());
//		mdtOpsCronEntity.setCronTime(opsCronTimeModel.getCronTime());
//		mdtOpsCronEntity.setModifiedBy(opsCronTimeModel.getModifiedBy());
//		mdtOpsCronEntity.setModifiedDate(opsCronTimeModel.getModifiedDate());
//		mdtOpsCronEntity.setProcessName(opsCronTimeModel.getProcessName());
//
//
//		return mdtOpsCronEntity;
//	}
//
//
//	public OpsCronTimeModel translateOpsScronEmtityToCommonsModel(MdtOpsCronEntity
//	mdtOpsCronEntity)
//	{
//
//		OpsCronTimeModel opsCronTimeModel = new OpsCronTimeModel();
//
//		opsCronTimeModel.setActiveInd(mdtOpsCronEntity.getActiveInd());
//		opsCronTimeModel.setCreatedBy(mdtOpsCronEntity.getCreatedBy());
//		opsCronTimeModel.setCreatedDate(mdtOpsCronEntity.getCreatedDate());
//		opsCronTimeModel.setCronTime(mdtOpsCronEntity.getCronTime());
//		opsCronTimeModel.setModifiedBy(mdtOpsCronEntity.getModifiedBy());
//		opsCronTimeModel.setModifiedDate(mdtOpsCronEntity.getModifiedDate());
//		opsCronTimeModel.setProcessName(mdtOpsCronEntity.getProcessName());
//
//
//		return opsCronTimeModel;
//	}

  public OpsCustParamModel translateMdtOpsCustParamEntityToCommonsModel(
      CasOpsCustParamEntity localEntity) {

    OpsCustParamModel opsCustParaModel = new OpsCustParamModel();


    opsCustParaModel.setActiveInd(localEntity.getActiveInd());
    opsCustParaModel.setManAccpLstSeq(localEntity.getManAccpLstSeq());
    opsCustParaModel.setInstId(localEntity.getInstId());
    opsCustParaModel.setCreatedBy(localEntity.getCreatedBy());
    opsCustParaModel.setCreatedDate(localEntity.getCreatedDate());
    opsCustParaModel.setManAccpLastFileNr(localEntity.getManAccpLastFileNr());
    opsCustParaModel.setManAccpLastFileNr(localEntity.getManAccpLastFileNr());
    opsCustParaModel.setManAccpXsdNs(localEntity.getManAccpXsdNs());
    opsCustParaModel.setManAmdLastFileNr(localEntity.getManAmdLastFileNr());
    opsCustParaModel.setManAmdLstSeq(localEntity.getManAmdLstSeq());
    opsCustParaModel.setManAmdXsdNs(localEntity.getManAmdXsdNs());
    opsCustParaModel.setManCanLastFileNr(localEntity.getManCanLastFileNr());
    opsCustParaModel.setManCanLstSeq(localEntity.getManCanLstSeq());
    opsCustParaModel.setManCanXsdNs(localEntity.getManCanXsdNs());
    opsCustParaModel.setManInitLastFileNr(localEntity.getManInitLastFileNr());
    opsCustParaModel.setManInitLstSeq(localEntity.getManInitLstSeq());
    opsCustParaModel.setManInitXsdNs(localEntity.getManInitXsdNs());
    opsCustParaModel.setManStatusRepLastFileNr(localEntity.getManStatusRepLastFileNr());
    opsCustParaModel.setManStatusRepLstSeq(localEntity.getManStatusRepLstSeq());
    opsCustParaModel.setManStatusRepXsdNs(localEntity.getManStatusRepXsdNs());
    opsCustParaModel.setManConfirmXsdNs(localEntity.getManConfirmXsdNs());
    opsCustParaModel.setManConfirmLstSeq(localEntity.getManConfirmLstSeq());
    opsCustParaModel.setManConfirmLstFileNr(localEntity.getManConfirmLstFileNr());
    opsCustParaModel.setManReqXsdNs(localEntity.getManReqXsdNs());
    opsCustParaModel.setManReqLastFileNr(localEntity.getManReqLastFileNr());
    opsCustParaModel.setManReqLstSeq(localEntity.getManReqLstSeq());
    opsCustParaModel.setManRespXsdNs(localEntity.getManRespXsdNs());
    opsCustParaModel.setManRespLstSeq(localEntity.getManRespLstSeq());
    opsCustParaModel.setManRespLastFileNr(localEntity.getManRespLastFileNr());

    return opsCustParaModel;


  }

  public CasOpsCustParamEntity translateOpsCustParamToEntity(OpsCustParamModel opsCustParaModel) {
    CasOpsCustParamEntity casOpsCustParamEntity = new CasOpsCustParamEntity();


    casOpsCustParamEntity.setActiveInd(opsCustParaModel.getActiveInd());
    casOpsCustParamEntity.setManAccpLstSeq(opsCustParaModel.getManAccpLstSeq());
    casOpsCustParamEntity.setInstId(opsCustParaModel.getInstId());
    casOpsCustParamEntity.setCreatedBy(opsCustParaModel.getCreatedBy());
    casOpsCustParamEntity.setCreatedDate(opsCustParaModel.getCreatedDate());
    casOpsCustParamEntity.setManAccpLastFileNr(opsCustParaModel.getManAccpLastFileNr());
    casOpsCustParamEntity.setManAccpLastFileNr(opsCustParaModel.getManAccpLastFileNr());
    casOpsCustParamEntity.setManAccpXsdNs(opsCustParaModel.getManAccpXsdNs());
    casOpsCustParamEntity.setManAmdLastFileNr(opsCustParaModel.getManAmdLastFileNr());
    casOpsCustParamEntity.setManAmdLstSeq(opsCustParaModel.getManAmdLstSeq());
    casOpsCustParamEntity.setManAmdXsdNs(opsCustParaModel.getManAmdXsdNs());
    casOpsCustParamEntity.setManCanLastFileNr(opsCustParaModel.getManCanLastFileNr());
    casOpsCustParamEntity.setManCanLstSeq(opsCustParaModel.getManCanLstSeq());
    casOpsCustParamEntity.setManCanXsdNs(opsCustParaModel.getManCanXsdNs());
    casOpsCustParamEntity.setManInitLastFileNr(opsCustParaModel.getManInitLastFileNr());
    casOpsCustParamEntity.setManInitLstSeq(opsCustParaModel.getManInitLstSeq());
    casOpsCustParamEntity.setManInitXsdNs(opsCustParaModel.getManInitXsdNs());
    casOpsCustParamEntity.setManStatusRepLastFileNr(opsCustParaModel.getManStatusRepLastFileNr());
    casOpsCustParamEntity.setManStatusRepLstSeq(opsCustParaModel.getManStatusRepLstSeq());
    casOpsCustParamEntity.setManStatusRepXsdNs(opsCustParaModel.getManStatusRepXsdNs());
    casOpsCustParamEntity.setManConfirmXsdNs(opsCustParaModel.getManConfirmXsdNs());
    casOpsCustParamEntity.setManConfirmLstSeq(opsCustParaModel.getManConfirmLstSeq());
    casOpsCustParamEntity.setManConfirmLstFileNr(opsCustParaModel.getManConfirmLstFileNr());
    casOpsCustParamEntity.setManReqXsdNs(opsCustParaModel.getManReqXsdNs());
    casOpsCustParamEntity.setManReqLastFileNr(opsCustParaModel.getManReqLastFileNr());
    casOpsCustParamEntity.setManReqLstSeq(opsCustParaModel.getManReqLstSeq());
    casOpsCustParamEntity.setManRespXsdNs(opsCustParaModel.getManRespXsdNs());
    casOpsCustParamEntity.setManRespLstSeq(opsCustParaModel.getManRespLstSeq());
    casOpsCustParamEntity.setManRespLastFileNr(opsCustParaModel.getManRespLastFileNr());

    return casOpsCustParamEntity;
  }

  public MdtSysCtrlSysParamModel translateMdtSysCtrlSysParamEntityToCommonsModel(
      CasSysctrlSysParamEntity localEntity) {

    MdtSysCtrlSysParamModel sysCtrlSysParamModel = new MdtSysCtrlSysParamModel();

    sysCtrlSysParamModel.setActiveInd(localEntity.getActiveInd());
    sysCtrlSysParamModel.setArchivePeriod(localEntity.getArchivePeriod());
    sysCtrlSysParamModel.setCreatedBy(localEntity.getCreatedBy());
    sysCtrlSysParamModel.setCreatedDate(localEntity.getCreatedDate());
    sysCtrlSysParamModel.setDefCurr(localEntity.getDefCurr());
    sysCtrlSysParamModel.setEodRunInd(localEntity.getEodRunInd());
    sysCtrlSysParamModel.setInactiveDuration(localEntity.getInactiveDuration());
    sysCtrlSysParamModel.setModifiedBy(localEntity.getModifiedBy());
    sysCtrlSysParamModel.setModifiedDate(localEntity.getModifiedDate());
    sysCtrlSysParamModel.setSodRunInd(localEntity.getSodRunInd());
    sysCtrlSysParamModel.setSysName(localEntity.getSysName());

    sysCtrlSysParamModel.setSysType(localEntity.getSysType());

    return sysCtrlSysParamModel;
  }

  public ReportsNamesModel translateReportNamesEntityToCommonsModel(
      CasCnfgReportNamesEntity casCnfgReportNamesEntity) {
    ReportsNamesModel reportsNamesModel = new ReportsNamesModel();

    reportsNamesModel.setActiveInd(casCnfgReportNamesEntity.getActiveInd());
    reportsNamesModel.setReportName(casCnfgReportNamesEntity.getReportName());
    reportsNamesModel.setReportNr(casCnfgReportNamesEntity.getReportNr());
    reportsNamesModel.setInternalInd(casCnfgReportNamesEntity.getInternalInd());
    reportsNamesModel.setCreatedBy(casCnfgReportNamesEntity.getCreatedBy());
    reportsNamesModel.setCreatedDate(casCnfgReportNamesEntity.getCreatedDate());
    reportsNamesModel.setModifiedBy(casCnfgReportNamesEntity.getModifiedBy());
    reportsNamesModel.setModifiedDate(casCnfgReportNamesEntity.getModifiedDate());

    return reportsNamesModel;
  }

  public CasCnfgReportNamesEntity translateCommnsReportNamesModelToEntity(
      ReportsNamesModel reportsNamesModel) {
    CasCnfgReportNamesEntity casCnfgReportNamesEntity = new CasCnfgReportNamesEntity();

    casCnfgReportNamesEntity.setActiveInd(reportsNamesModel.getActiveInd());
    casCnfgReportNamesEntity.setReportName(reportsNamesModel.getReportName());
    casCnfgReportNamesEntity.setReportNr(reportsNamesModel.getReportNr());
    casCnfgReportNamesEntity.setInternalInd(reportsNamesModel.getInternalInd());
    casCnfgReportNamesEntity.setCreatedBy(reportsNamesModel.getCreatedBy());
    casCnfgReportNamesEntity.setCreatedDate(reportsNamesModel.getCreatedDate());
    casCnfgReportNamesEntity.setModifiedBy(reportsNamesModel.getModifiedBy());
    casCnfgReportNamesEntity.setModifiedDate(reportsNamesModel.getModifiedDate());

    return casCnfgReportNamesEntity;
  }

  public CasSysctrlCompParamEntity translateSysctrlCompEntityToCommonsModel(
      CompParamModel compParamModel) {
    CasSysctrlCompParamEntity mdtSysctrlCompParamEntity = new CasSysctrlCompParamEntity();

    mdtSysctrlCompParamEntity.setCompId(compParamModel.getCompId());
    mdtSysctrlCompParamEntity.setCompName(compParamModel.getCompName());
    mdtSysctrlCompParamEntity.setCompAbbrevName(compParamModel.getCompAbbrevName());
    mdtSysctrlCompParamEntity.setRegistrationNr(compParamModel.getRegistrationNr());
    mdtSysctrlCompParamEntity.setActiveInd(compParamModel.getActiveInd());
    mdtSysctrlCompParamEntity.setAddress1(compParamModel.getAddress1());
    mdtSysctrlCompParamEntity.setAddress2(compParamModel.getAddress2());
    mdtSysctrlCompParamEntity.setCity(compParamModel.getCity());
    mdtSysctrlCompParamEntity.setPostCode(compParamModel.getPostCode());
    mdtSysctrlCompParamEntity.setDialCode(compParamModel.getDialCode());
    mdtSysctrlCompParamEntity.setTelNr(compParamModel.getTelNr());
    mdtSysctrlCompParamEntity.setFaxCode(compParamModel.getFaxNr());
    mdtSysctrlCompParamEntity.setEmail(compParamModel.getEmail());
    mdtSysctrlCompParamEntity.setContactName(compParamModel.getContactName());
    mdtSysctrlCompParamEntity.setLastFileNr(compParamModel.getLastFileNr());
    mdtSysctrlCompParamEntity.setTransamissionInd(compParamModel.getTransamissionInd());
    mdtSysctrlCompParamEntity.setAchInstId(compParamModel.getBicCode());
    mdtSysctrlCompParamEntity.setAchId(compParamModel.getAchId());

    return mdtSysctrlCompParamEntity;
  }

  public AudReportsModel translateAudReportsEntityToCommonsModel(
      AudReportsEntity audReportsEntity) {
    AudReportsModel audReportsModel = new AudReportsModel();

    audReportsModel.setActiveInd(audReportsEntity.getActiveInd());
    audReportsModel.setCreatedBy(audReportsEntity.getCreatedBy());
    audReportsModel.setCreatedDate(audReportsEntity.getCreatedDate());
    audReportsModel.setModifiedBy(audReportsEntity.getModifiedBy());
    audReportsModel.setModifiedDate(audReportsEntity.getModifiedDate());
    audReportsModel.setReportName(audReportsEntity.getReportName());
    audReportsModel.setReportNr(audReportsEntity.getReportNr());
    audReportsModel.setReportRecord(audReportsEntity.getReportRecord());

    return audReportsModel;
  }

  public AudReportsEntity translateCommnsAudReportsEntity(AudReportsModel audReportsModel) {

    AudReportsEntity audReportsEntity = new AudReportsEntity();

    audReportsEntity.setActiveInd(audReportsModel.getActiveInd());
    audReportsEntity.setCreatedBy(audReportsModel.getCreatedBy());
    audReportsEntity.setCreatedDate(audReportsModel.getCreatedDate());
    audReportsEntity.setModifiedBy(audReportsModel.getModifiedBy());
    audReportsEntity.setModifiedDate(audReportsModel.getModifiedDate());
    audReportsEntity.setReportName(audReportsModel.getReportName());
    audReportsEntity.setReportNr(audReportsModel.getReportNr());
    audReportsEntity.setReportRecord(audReportsModel.getReportRecord());
    return audReportsEntity;
  }


  public AuditTrackingModel translateAudTrackingEntityToCommonsModel(
      AudTrackingEntity audTrackingEntity) {
    AuditTrackingModel auditTrackingModel = new AuditTrackingModel();

    auditTrackingModel.setAction(audTrackingEntity.getAction());
    auditTrackingModel.setActionDate(audTrackingEntity.getActionDate());
    auditTrackingModel.setColumnName(audTrackingEntity.getColumnName());
    auditTrackingModel.setNewValue(audTrackingEntity.getNewValue());
    auditTrackingModel.setOldValue(audTrackingEntity.getOldValue());
    auditTrackingModel.setRecord(audTrackingEntity.getRecord());
    auditTrackingModel.setRecordId(audTrackingEntity.getRecordId());
    auditTrackingModel.setTableName(audTrackingEntity.getTableName());
    auditTrackingModel.setUserId(audTrackingEntity.getUserId());

    return auditTrackingModel;
  }

  public AudTrackingEntity translateCommnsAudTrackingEntity(AuditTrackingModel auditTrackingModel) {

    AudTrackingEntity audTrackingEntity = new AudTrackingEntity();

    audTrackingEntity.setAction(auditTrackingModel.getAction());
    audTrackingEntity.setActionDate(auditTrackingModel.getActionDate());
    audTrackingEntity.setColumnName(auditTrackingModel.getColumnName());
    audTrackingEntity.setNewValue(auditTrackingModel.getNewValue());
    audTrackingEntity.setOldValue(auditTrackingModel.getOldValue());
    audTrackingEntity.setRecord(auditTrackingModel.getRecord());
    audTrackingEntity.setRecordId(auditTrackingModel.getRecordId());
    audTrackingEntity.setTableName(auditTrackingModel.getTableName());
    audTrackingEntity.setUserId(auditTrackingModel.getUserId());
    return audTrackingEntity;
  }

  public CompParamModel translateMdtSysctrlCompParamEntityToCommonsModel(
      CasSysctrlCompParamEntity mdtSysctrlCompParamEntity) {
    CompParamModel comParamModel = new CompParamModel();

    comParamModel.setAchId(mdtSysctrlCompParamEntity.getAchId());
    comParamModel.setActiveInd(mdtSysctrlCompParamEntity.getActiveInd());
    comParamModel.setAddress1(mdtSysctrlCompParamEntity.getAddress1());
    comParamModel.setAddress2(mdtSysctrlCompParamEntity.getAddress2());
    comParamModel.setBicCode(mdtSysctrlCompParamEntity.getAchInstId());
    comParamModel.setCity(mdtSysctrlCompParamEntity.getCity());
    comParamModel.setCompAbbrevName(mdtSysctrlCompParamEntity.getCompAbbrevName());
    comParamModel.setCompId(mdtSysctrlCompParamEntity.getCompId());
    comParamModel.setCompName(mdtSysctrlCompParamEntity.getCompName());
    comParamModel.setContactName(mdtSysctrlCompParamEntity.getContactName());
    comParamModel.setDialCode(mdtSysctrlCompParamEntity.getDialCode());
    comParamModel.setEmail(mdtSysctrlCompParamEntity.getEmail());
    comParamModel.setFaxCode(mdtSysctrlCompParamEntity.getFaxCode());
    comParamModel.setFaxNr(mdtSysctrlCompParamEntity.getFaxNr());
    comParamModel.setLastFileNr(mdtSysctrlCompParamEntity.getLastFileNr());
    comParamModel.setPostCode(mdtSysctrlCompParamEntity.getPostCode());
    comParamModel.setRegistrationNr(mdtSysctrlCompParamEntity.getRegistrationNr());
    comParamModel.setTelNr(mdtSysctrlCompParamEntity.getTelNr());
    comParamModel.setTransamissionInd(mdtSysctrlCompParamEntity.getTransamissionInd());

    return comParamModel;
  }

  public CasSysctrlCompParamEntity translateCommnsMdtSysctrlCompParamEntity(
      CompParamModel comParamModel) {

    CasSysctrlCompParamEntity mdtSysctrlCompParamEntity = new CasSysctrlCompParamEntity();

    mdtSysctrlCompParamEntity.setAchId(comParamModel.getAchId());
    mdtSysctrlCompParamEntity.setActiveInd(comParamModel.getActiveInd());
    mdtSysctrlCompParamEntity.setAddress1(comParamModel.getAddress1());
    mdtSysctrlCompParamEntity.setAddress2(comParamModel.getAddress2());
    mdtSysctrlCompParamEntity.setAchInstId(comParamModel.getBicCode());
    mdtSysctrlCompParamEntity.setCity(comParamModel.getCity());
    mdtSysctrlCompParamEntity.setCompAbbrevName(comParamModel.getCompAbbrevName());
    mdtSysctrlCompParamEntity.setCompId(comParamModel.getCompId());
    mdtSysctrlCompParamEntity.setCompName(comParamModel.getCompName());
    mdtSysctrlCompParamEntity.setContactName(comParamModel.getContactName());
    mdtSysctrlCompParamEntity.setDialCode(comParamModel.getDialCode());
    mdtSysctrlCompParamEntity.setEmail(comParamModel.getEmail());
    mdtSysctrlCompParamEntity.setFaxCode(comParamModel.getFaxCode());
    mdtSysctrlCompParamEntity.setFaxNr(comParamModel.getFaxNr());
    mdtSysctrlCompParamEntity.setLastFileNr(comParamModel.getLastFileNr());
    mdtSysctrlCompParamEntity.setPostCode(comParamModel.getPostCode());
    mdtSysctrlCompParamEntity.setRegistrationNr(comParamModel.getRegistrationNr());
    mdtSysctrlCompParamEntity.setTelNr(comParamModel.getTelNr());
    mdtSysctrlCompParamEntity.setTransamissionInd(comParamModel.getTransamissionInd());


    return mdtSysctrlCompParamEntity;
  }

  public CnfgAuthTypeModel translateCnfgAuthTypeEntityToCommonsModel(
      CasCnfgAuthTypeEntity casCnfgAuthTypeEntity) {
    CnfgAuthTypeModel authTypeModel = new CnfgAuthTypeModel();

    authTypeModel.setActiveInd(casCnfgAuthTypeEntity.getActiveInd());
    authTypeModel.setAuthType(casCnfgAuthTypeEntity.getAuthType());
    authTypeModel.setAuthTypeDescription(casCnfgAuthTypeEntity.getAuthTypeDescription());

    return authTypeModel;
  }

  public CasCnfgAuthTypeEntity translateCommnsAuthtypeModelToEntity(
      CnfgAuthTypeModel CnfgAuthTypeModel) {
    CasCnfgAuthTypeEntity casCnfgAuthTypeEntity = new CasCnfgAuthTypeEntity();

    casCnfgAuthTypeEntity.setActiveInd(CnfgAuthTypeModel.getActiveInd());
    casCnfgAuthTypeEntity.setAuthType(CnfgAuthTypeModel.getAuthType());
    casCnfgAuthTypeEntity.setAuthTypeDescription(CnfgAuthTypeModel.getAuthTypeDescription());
    return casCnfgAuthTypeEntity;
  }


  public ProcessStatusModel translateMdtSysctrlProcessStatusEntityToCommonsModel(
      CasSysctrlProcessStatusEntity casSysctrlProcessStatusEntity) {
    ProcessStatusModel processStatusModel = new ProcessStatusModel();

    processStatusModel.setStatus(casSysctrlProcessStatusEntity.getStatus());
    processStatusModel.setStatusDescription(casSysctrlProcessStatusEntity.getStatusDescription());
    processStatusModel.setActiveInd(casSysctrlProcessStatusEntity.getActiveInd());
    processStatusModel.setCreatedBy(casSysctrlProcessStatusEntity.getCreatedBy());
    processStatusModel.setCreatedDate(casSysctrlProcessStatusEntity.getCreatedDate());
    processStatusModel.setModifiedBy(casSysctrlProcessStatusEntity.getModifiedBy());
    processStatusModel.setModifiedDate(casSysctrlProcessStatusEntity.getModifiedDate());


    return processStatusModel;
  }


  public CasSysctrlProcessStatusEntity translateCommnsProcessStatusModelToEntity(
      ProcessStatusModel processStatusModel) {
    CasSysctrlProcessStatusEntity casSysctrlProcessStatusEntity =
        new CasSysctrlProcessStatusEntity();

    casSysctrlProcessStatusEntity.setStatus(processStatusModel.getStatus());
    casSysctrlProcessStatusEntity.setStatusDescription(processStatusModel.getStatusDescription());
    casSysctrlProcessStatusEntity.setActiveInd(processStatusModel.getActiveInd());
    casSysctrlProcessStatusEntity.setCreatedBy(processStatusModel.getCreatedBy());
    casSysctrlProcessStatusEntity.setCreatedDate(processStatusModel.getCreatedDate());
    casSysctrlProcessStatusEntity.setModifiedBy(processStatusModel.getModifiedBy());
    casSysctrlProcessStatusEntity.setModifiedDate(processStatusModel.getModifiedDate());

    return casSysctrlProcessStatusEntity;
  }

//		Not needed for C&A
//		public MdtSysctrlPubholEntity translateCommonsPublicHolidayModelToEntity
//		(PublicHolidayModel publicHolidayModel)
//		{
//			MdtSysctrlPubholEntity mdtSysctrlPubholEntity = new MdtSysctrlPubholEntity();
//			
//			mdtSysctrlPubholEntity.setPubHolDate(publicHolidayModel.getPubHolDate());
//			mdtSysctrlPubholEntity.setPubHolidayDesc(publicHolidayModel.getPubHolidayDesc());
//			mdtSysctrlPubholEntity.setActiveInd(publicHolidayModel.getActiveInd());
//			log.debug("Translator mdtSysctrlPubholEntity*******************************"
//			+mdtSysctrlPubholEntity);
//			
//			return mdtSysctrlPubholEntity;
//		}
//		
//		public PublicHolidayModel translateMdtAcOpsPubholEntityToCommonsModel
//		(MdtSysctrlPubholEntity mdtSysctrlPubholEntity)
//		{
//			PublicHolidayModel publicHolidayModel = new  PublicHolidayModel();
//			
//			publicHolidayModel.setPubHolDate(mdtSysctrlPubholEntity.getPubHolDate());
//			publicHolidayModel.setPubHolidayDesc(mdtSysctrlPubholEntity.getPubHolidayDesc());
//			publicHolidayModel.setActiveInd(mdtSysctrlPubholEntity.getActiveInd());
//			
//			log.debug("Translator publicHolidayModel*******************************"
//			+publicHolidayModel);
//			
//			return publicHolidayModel;
//			
//			
//		}


  public CasOpsServicesEntity transalateCommonsServicesModelToEntity(ServicesModel servicesModel) {

    CasOpsServicesEntity servicesEntity = new CasOpsServicesEntity();

    servicesEntity.setCreatedBy(servicesModel.getCreatedBy());
    servicesEntity.setCreatedDate(servicesModel.getCreatedDate());
    servicesEntity.setModifiedBy(servicesModel.getModifiedBy());
    servicesEntity.setModifiedDate(servicesModel.getModifiedDate());
    servicesEntity.setRecordId(servicesModel.getRecordId());
    servicesEntity.setServiceIdIn(servicesModel.getServiceIdIn());
    servicesEntity.setServiceIdInDesc(servicesModel.getServiceIdInDesc());
    servicesEntity.setServiceIdOut(servicesModel.getServiceIdOut());
    servicesEntity.setServiceIdOutDesc(servicesModel.getServiceIdOutDesc());
    servicesEntity.setProcessDate(servicesModel.getProcessDate());
    servicesEntity.setProcessStatus(servicesModel.getProcessStatus());
    servicesEntity.setServiceIdOutSlaTime(servicesModel.getServiceIdOutSlaTime());
    servicesEntity.setActiveInd(servicesModel.getActiveInd());
    servicesEntity.setMsgTypeId(servicesModel.getMsgTypeId());


    return servicesEntity;
  }


  public ServicesModel translateMdtOpsServicesEntityToCommonsModel(
      CasOpsServicesEntity casOpsServicesEntity) {

    ServicesModel servicesModel = new ServicesModel();

    servicesModel.setCreatedBy(casOpsServicesEntity.getCreatedBy());
    servicesModel.setCreatedDate(casOpsServicesEntity.getCreatedDate());
    servicesModel.setModifiedBy(casOpsServicesEntity.getModifiedBy());
    servicesModel.setModifiedDate(casOpsServicesEntity.getModifiedDate());
    servicesModel.setRecordId(casOpsServicesEntity.getRecordId());
    servicesModel.setServiceIdIn(casOpsServicesEntity.getServiceIdIn());
    servicesModel.setServiceIdInDesc(casOpsServicesEntity.getServiceIdInDesc());
    servicesModel.setServiceIdOut(casOpsServicesEntity.getServiceIdOut());
    servicesModel.setServiceIdOutDesc(casOpsServicesEntity.getServiceIdOutDesc());
    servicesModel.setProcessDate(casOpsServicesEntity.getProcessDate());
    servicesModel.setProcessStatus(casOpsServicesEntity.getProcessStatus());
    servicesModel.setServiceIdOutSlaTime(casOpsServicesEntity.getServiceIdOutSlaTime());
    servicesModel.setActiveInd(casOpsServicesEntity.getActiveInd());
    servicesModel.setMsgTypeId(casOpsServicesEntity.getMsgTypeId());

    return servicesModel;
  }

  public CasOpsStatusHdrsEntity translateCommonsOpsStatusHdrsModelToEntity(
      OpsStatusHdrsModel opsStatusHdrsModel) {
    CasOpsStatusHdrsEntity opsStatusHdrsEntity = new CasOpsStatusHdrsEntity();

    opsStatusHdrsEntity.setSystemSeqNo(opsStatusHdrsModel.getSystemSeqNo());
    opsStatusHdrsEntity.setHdrMsgId(opsStatusHdrsModel.getHdrMsgId());
    opsStatusHdrsEntity.setCreateDateTime(opsStatusHdrsModel.getCreateDateTime());
    opsStatusHdrsEntity.setInstdAgt(opsStatusHdrsModel.getInstdAgt());
    opsStatusHdrsEntity.setInstgAgt(opsStatusHdrsModel.getInstgAgt());
    opsStatusHdrsEntity.setOrgnlMsgId(opsStatusHdrsModel.getOrgnlMsgId());
    opsStatusHdrsEntity.setOrgnlMsgName(opsStatusHdrsModel.getOrgnlMsgName());
    opsStatusHdrsEntity.setOrgnlCntlSum(opsStatusHdrsModel.getOrgnlCntlSum());
    opsStatusHdrsEntity.setProcessStatus(opsStatusHdrsModel.getProcessStatus());
    opsStatusHdrsEntity.setGroupStatus(opsStatusHdrsModel.getGroupStatus());
    opsStatusHdrsEntity.setService(opsStatusHdrsModel.getService());
    opsStatusHdrsEntity.setVetRunNumber(opsStatusHdrsModel.getVetRunNumber());
    opsStatusHdrsEntity.setWorkunitRefNo(opsStatusHdrsModel.getWorkunitRefNo());
    opsStatusHdrsEntity.setOrgnlCreateDateTime(opsStatusHdrsModel.getOrgnlCreateDateTime());
    opsStatusHdrsEntity.setOrgnlNoOfTxns(opsStatusHdrsModel.getOrgnlNoOfTxns());

    return opsStatusHdrsEntity;

  }


  public OpsStatusHdrsModel translateOpsStatusHdrsEntityToCommonsModel(
      CasOpsStatusHdrsEntity opsStatusHdrsEntity) {

    OpsStatusHdrsModel opsStatusHdrsModel = new OpsStatusHdrsModel();

    opsStatusHdrsModel.setSystemSeqNo(opsStatusHdrsEntity.getSystemSeqNo());
    opsStatusHdrsModel.setHdrMsgId(opsStatusHdrsEntity.getHdrMsgId());
    opsStatusHdrsModel.setCreateDateTime(opsStatusHdrsEntity.getCreateDateTime());
    opsStatusHdrsModel.setInstdAgt(opsStatusHdrsEntity.getInstdAgt());
    opsStatusHdrsModel.setInstgAgt(opsStatusHdrsEntity.getInstgAgt());
    opsStatusHdrsModel.setOrgnlMsgId(opsStatusHdrsEntity.getOrgnlMsgId());
    opsStatusHdrsModel.setOrgnlMsgName(opsStatusHdrsEntity.getOrgnlMsgName());
    opsStatusHdrsModel.setOrgnlCntlSum(opsStatusHdrsEntity.getOrgnlCntlSum());
    opsStatusHdrsModel.setProcessStatus(opsStatusHdrsEntity.getProcessStatus());
    opsStatusHdrsModel.setGroupStatus(opsStatusHdrsEntity.getGroupStatus());
    opsStatusHdrsModel.setService(opsStatusHdrsEntity.getService());
    opsStatusHdrsModel.setVetRunNumber(opsStatusHdrsEntity.getVetRunNumber());
    opsStatusHdrsModel.setWorkunitRefNo(opsStatusHdrsEntity.getWorkunitRefNo());
    opsStatusHdrsModel.setOrgnlCreateDateTime(opsStatusHdrsEntity.getOrgnlCreateDateTime());
    opsStatusHdrsModel.setOrgnlNoOfTxns(opsStatusHdrsEntity.getOrgnlNoOfTxns());


    return opsStatusHdrsModel;
  }

  public CasOpsStatusDetailsEntity translateCommonsOpsStatusDetailsModelToEntity(
      OpsStatusDetailsModel opsStatusDetailsModel) {
    CasOpsStatusDetailsEntity opsStatusDetailsEntity = new CasOpsStatusDetailsEntity();

    opsStatusDetailsEntity.setSystemSeqNo(opsStatusDetailsModel.getSystemSeqNo());
    opsStatusDetailsEntity.setStatusHdrSeqNo(opsStatusDetailsModel.getStatusHdrSeqNo());
    opsStatusDetailsEntity.setErrorCode(opsStatusDetailsModel.getErrorCode());
    opsStatusDetailsEntity.setTxnId(opsStatusDetailsModel.getTxnId());
    opsStatusDetailsEntity.setEndToEndId(opsStatusDetailsModel.getEndToEndId());
    opsStatusDetailsEntity.setTxnStatus(opsStatusDetailsModel.getTxnStatus());
    opsStatusDetailsEntity.setErrorType(opsStatusDetailsModel.getErrorType());
    opsStatusDetailsEntity.setRecordId(opsStatusDetailsModel.getRecordId());
    opsStatusDetailsEntity.setOrgnlTxnSeqNo(opsStatusDetailsModel.getOrgnlTxnSeqNo());
    opsStatusDetailsEntity.setMandateRefNumber(opsStatusDetailsModel.getMandateRefNumber());
    opsStatusDetailsEntity.setInstId(opsStatusDetailsModel.getInstId());
    opsStatusDetailsEntity.setProcessStatus(opsStatusDetailsModel.getProcessStatus());


    return opsStatusDetailsEntity;


  }


  public OpsStatusDetailsModel translateOpsStatusDetailsEntityToCommonsModel(
      CasOpsStatusDetailsEntity opsStatusDetailsEntity) {
    OpsStatusDetailsModel opsStatusDetailsModel = new OpsStatusDetailsModel();

    opsStatusDetailsModel.setSystemSeqNo(opsStatusDetailsEntity.getSystemSeqNo());
    opsStatusDetailsModel.setStatusHdrSeqNo(opsStatusDetailsEntity.getStatusHdrSeqNo());
    opsStatusDetailsModel.setErrorCode(opsStatusDetailsEntity.getErrorCode());
    opsStatusDetailsModel.setTxnId(opsStatusDetailsEntity.getTxnId());
    opsStatusDetailsModel.setEndToEndId(opsStatusDetailsEntity.getEndToEndId());
    opsStatusDetailsModel.setTxnStatus(opsStatusDetailsEntity.getTxnStatus());
    opsStatusDetailsModel.setErrorType(opsStatusDetailsEntity.getErrorType());
    opsStatusDetailsModel.setRecordId(opsStatusDetailsEntity.getRecordId());
    opsStatusDetailsModel.setOrgnlTxnSeqNo(opsStatusDetailsEntity.getOrgnlTxnSeqNo());
    opsStatusDetailsModel.setMandateRefNumber(opsStatusDetailsEntity.getMandateRefNumber());
    opsStatusDetailsModel.setInstId(opsStatusDetailsEntity.getInstId());
    opsStatusDetailsModel.setProcessStatus(opsStatusDetailsEntity.getProcessStatus());

    return opsStatusDetailsModel;
  }

  public CasOpsMndtCountEntity translateCommonsMdtModelCountToEntity(
      MandatesCountCommonsModel mandateCountModel) {

    CasOpsMndtCountEntity casOpsMndtCountEntity = new CasOpsMndtCountEntity();
    CasOpsMndtCountPK casOpsMndtCountPK = new CasOpsMndtCountPK();
    casOpsMndtCountPK.setInstId(mandateCountModel.getInstId());
    casOpsMndtCountPK.setMsgId(mandateCountModel.getMsgId());
    casOpsMndtCountPK.setServiceId(mandateCountModel.getServiceId());
    casOpsMndtCountEntity.setCasOpsMndtCountPK(casOpsMndtCountPK);
    casOpsMndtCountEntity.setFileName(mandateCountModel.getFileName());
    casOpsMndtCountEntity.setIncoming(mandateCountModel.getIncoming());
    casOpsMndtCountEntity.setNrOfFiles(mandateCountModel.getNrOfFiles());
    casOpsMndtCountEntity.setNrOfMsgs(mandateCountModel.getNrOfMsgs());
    casOpsMndtCountEntity.setOutgoing(mandateCountModel.getOutgoing());
    casOpsMndtCountEntity.setNrMsgsRejected(mandateCountModel.getNrMsgsRejected());
    casOpsMndtCountEntity.setProcessDate(mandateCountModel.getProcessDate());
    casOpsMndtCountEntity.setNrMsgsAccepted(mandateCountModel.getNrMsgsAccepted());
    casOpsMndtCountEntity.setNrMsgsExtracted(mandateCountModel.getNrMsgsExtracted());
    return casOpsMndtCountEntity;


  }


  public MandatesCountCommonsModel translateMdtOpsMndtCountEntityToCommonsModel(
      CasOpsMndtCountEntity mdtOpsMndtCountEntity) {

    MandatesCountCommonsModel mandatesCountCommonsModel = new MandatesCountCommonsModel();

    mandatesCountCommonsModel.setFileName(mdtOpsMndtCountEntity.getFileName());
    mandatesCountCommonsModel.setProcessDate(mdtOpsMndtCountEntity.getProcessDate());
    mandatesCountCommonsModel.setInstId(mdtOpsMndtCountEntity.getCasOpsMndtCountPK().getInstId());
    mandatesCountCommonsModel.setServiceId(
        mdtOpsMndtCountEntity.getCasOpsMndtCountPK().getServiceId());
    mandatesCountCommonsModel.setMsgId(mdtOpsMndtCountEntity.getCasOpsMndtCountPK().getMsgId());
    mandatesCountCommonsModel.setIncoming(mdtOpsMndtCountEntity.getIncoming());
    mandatesCountCommonsModel.setNrOfFiles(mdtOpsMndtCountEntity.getNrOfFiles());
    mandatesCountCommonsModel.setNrOfMsgs(mdtOpsMndtCountEntity.getNrOfMsgs());
    mandatesCountCommonsModel.setOutgoing(mdtOpsMndtCountEntity.getOutgoing());
    mandatesCountCommonsModel.setNrMsgsRejected(mdtOpsMndtCountEntity.getNrMsgsRejected());
    mandatesCountCommonsModel.setNrMsgsAccepted(mdtOpsMndtCountEntity.getNrMsgsAccepted());
    mandatesCountCommonsModel.setNrMsgsExtracted(mdtOpsMndtCountEntity.getNrMsgsExtracted());


    return mandatesCountCommonsModel;


  }

  public AdjustmentCategoryModel translateMdtCnfgAdjustmentCatEntityToCommonsModel(
      CasCnfgAdjustmentCatEntity localEntity) {

    AdjustmentCategoryModel adjustmentCategoryModel = new AdjustmentCategoryModel();

    adjustmentCategoryModel.setAdjustmentCategory(localEntity.getAdjustmentCategory());
    adjustmentCategoryModel.setAdjustmentCategoryDesc(localEntity.getAdjustmentCategoryDesc());
    adjustmentCategoryModel.setActiveInd(localEntity.getActiveInd());
    adjustmentCategoryModel.setCreatedBy(localEntity.getCreatedBy());
    adjustmentCategoryModel.setCreatedDate(localEntity.getCreatedDate());
    adjustmentCategoryModel.setModifiedBy(localEntity.getModifiedBy());
    adjustmentCategoryModel.setModifiedDate(localEntity.getModifiedDate());

    return adjustmentCategoryModel;
  }

  public CasCnfgAdjustmentCatEntity translateCommnsAdjustmentCategoryModelToEntity(
      AdjustmentCategoryModel adjustmentCategoryModel) {

    CasCnfgAdjustmentCatEntity casCnfgAdjustmentCatEntity = new CasCnfgAdjustmentCatEntity();

    casCnfgAdjustmentCatEntity.setAdjustmentCategory(
        adjustmentCategoryModel.getAdjustmentCategory());
    casCnfgAdjustmentCatEntity.setAdjustmentCategoryDesc(
        adjustmentCategoryModel.getAdjustmentCategoryDesc());
    casCnfgAdjustmentCatEntity.setActiveInd(adjustmentCategoryModel.getActiveInd());
    casCnfgAdjustmentCatEntity.setCreatedBy(adjustmentCategoryModel.getCreatedBy());
    casCnfgAdjustmentCatEntity.setCreatedDate(adjustmentCategoryModel.getCreatedDate());
    casCnfgAdjustmentCatEntity.setModifiedBy(adjustmentCategoryModel.getModifiedBy());
    casCnfgAdjustmentCatEntity.setModifiedDate(adjustmentCategoryModel.getModifiedDate());

    return casCnfgAdjustmentCatEntity;
  }

  public CasCnfgAccountTypeEntity translateCommnsAccountTypeModelToEntity(
      AccountTypeModel accountTypeModel) {

    CasCnfgAccountTypeEntity casCnfgAccountTypeEntity = new CasCnfgAccountTypeEntity();

    casCnfgAccountTypeEntity.setAccountTypeCode(accountTypeModel.getAccountTypeCode());
    casCnfgAccountTypeEntity.setAccountTypeDescription(
        accountTypeModel.getAccountTypeDescription());
    casCnfgAccountTypeEntity.setActiveInd(accountTypeModel.getActiveInd());
    casCnfgAccountTypeEntity.setCreatedBy(accountTypeModel.getCreatedBy());
    casCnfgAccountTypeEntity.setCreatedDate(accountTypeModel.getCreatedDate());
    casCnfgAccountTypeEntity.setModifiedBy(accountTypeModel.getModifiedBy());
    casCnfgAccountTypeEntity.setModifiedDate(accountTypeModel.getModifiedDate());

    return casCnfgAccountTypeEntity;


  }

  public AccountTypeModel translateMdtCnfgAccountTypeEntityToCommonsModel(
      CasCnfgAccountTypeEntity localEntity) {

    AccountTypeModel accountTypeModel = new AccountTypeModel();

    accountTypeModel.setAccountTypeCode(localEntity.getAccountTypeCode());
    accountTypeModel.setAccountTypeDescription(localEntity.getAccountTypeDescription());
    accountTypeModel.setActiveInd(localEntity.getActiveInd());
    accountTypeModel.setCreatedBy(localEntity.getCreatedBy());
    accountTypeModel.setCreatedDate(localEntity.getCreatedDate());
    accountTypeModel.setModifiedBy(localEntity.getModifiedBy());
    accountTypeModel.setModifiedDate(localEntity.getModifiedDate());

    return accountTypeModel;
  }

  public SeverityCodesModel translateMdtCnfgSeverityCodesEntityTocommonsModel(
      CasCnfgSeverityCodesEntity localEntity) {
    log.debug("Admin Translator Entity---> " + localEntity);
    SeverityCodesModel severityCodesModel = new SeverityCodesModel();

    // fndBankDetailsModel.setInstitution(Integer.valueOf(fndBankDetailsEntity.getInstitution()));
//			severityCodesModel.setSeverityCode(Short.valueOf(localEntity.getSeverityCode()));
    severityCodesModel.setSeverityCode(localEntity.getSeverityCode());
    //(Integer.valueOf(localEntity.getSeverityCode()));
    //(localEntity.getSeverityCode());
    severityCodesModel.setSeverityCodeDesc(localEntity.getSeverityCodeDesc());
    severityCodesModel.setCreatedBy(localEntity.getCreatedBy());
    severityCodesModel.setCreatedDate(localEntity.getCreatedDate());
    severityCodesModel.setModifiedBy(localEntity.getModifiedBy());
    severityCodesModel.setModifiedDate(localEntity.getModifiedDate());
    severityCodesModel.setActiveInd(localEntity.getActiveInd());

    return severityCodesModel;
  }

  public CasCnfgSeverityCodesEntity translateCommnsSeverityCodesModelToEntity(
      SeverityCodesModel severityCodesModel) {

    CasCnfgSeverityCodesEntity casCnfgSeverityCodesEntity = new CasCnfgSeverityCodesEntity();


    casCnfgSeverityCodesEntity.setSeverityCode(severityCodesModel.getSeverityCode());
    casCnfgSeverityCodesEntity.setSeverityCodeDesc(severityCodesModel.getSeverityCodeDesc());
    casCnfgSeverityCodesEntity.setCreatedBy(severityCodesModel.getCreatedBy());
    casCnfgSeverityCodesEntity.setCreatedDate(severityCodesModel.getCreatedDate());
    casCnfgSeverityCodesEntity.setModifiedBy(severityCodesModel.getModifiedBy());
    casCnfgSeverityCodesEntity.setModifiedDate(severityCodesModel.getModifiedDate());
    casCnfgSeverityCodesEntity.setActiveInd(severityCodesModel.getActiveInd());

    return casCnfgSeverityCodesEntity;
  }


  public CasSysctrlServicesEntity translateCommonsSystemControlServicesModelToEntity(
      SystemControlServicesModel systemControlServicesModel) {
    CasSysctrlServicesEntity casSysctrlServicesEntity = new CasSysctrlServicesEntity();

    casSysctrlServicesEntity.setServiceIdIn(systemControlServicesModel.getServiceIdIn());
    casSysctrlServicesEntity.setServiceIdOut(systemControlServicesModel.getServiceIdOut());
    casSysctrlServicesEntity.setServiceIdInDesc(systemControlServicesModel.getServiceIdInDesc());
    casSysctrlServicesEntity.setServiceIdOutDesc(systemControlServicesModel.getServiceIdOutDesc());
    casSysctrlServicesEntity.setRecordId(systemControlServicesModel.getRecordId());
    casSysctrlServicesEntity.setActiveInd(systemControlServicesModel.getActiveInd());
    casSysctrlServicesEntity.setCreatedBy(systemControlServicesModel.getCreatedBy());
    casSysctrlServicesEntity.setCreatedDate(systemControlServicesModel.getCreatedDate());
    casSysctrlServicesEntity.setModifiedBy(systemControlServicesModel.getModifiedBy());
    casSysctrlServicesEntity.setModifiedDate(systemControlServicesModel.getModifiedDate());

    return casSysctrlServicesEntity;
  }

  public SystemControlServicesModel translateMdtSysctrlServicesEntityToCommonsModel(
      CasSysctrlServicesEntity casSysctrlServicesEntity) {
    SystemControlServicesModel systemControlServicesModel = new SystemControlServicesModel();

    systemControlServicesModel.setServiceIdIn(casSysctrlServicesEntity.getServiceIdIn());
    systemControlServicesModel.setServiceIdOut(casSysctrlServicesEntity.getServiceIdOut());
    systemControlServicesModel.setServiceIdInDesc(casSysctrlServicesEntity.getServiceIdInDesc());
    systemControlServicesModel.setServiceIdOutDesc(casSysctrlServicesEntity.getServiceIdOutDesc());
    systemControlServicesModel.setRecordId(casSysctrlServicesEntity.getRecordId());
    systemControlServicesModel.setActiveInd(casSysctrlServicesEntity.getActiveInd());
    systemControlServicesModel.setCreatedBy(casSysctrlServicesEntity.getCreatedBy());
    systemControlServicesModel.setCreatedDate(casSysctrlServicesEntity.getCreatedDate());
    systemControlServicesModel.setModifiedBy(casSysctrlServicesEntity.getModifiedBy());
    systemControlServicesModel.setModifiedDate(casSysctrlServicesEntity.getModifiedDate());

    return systemControlServicesModel;
  }


  public SysCisBankModel translateSysCisBankEntityToCommonsModel(
      SysCisBankEntity sysCisBankEntity) {
    SysCisBankModel sysCisBankModel = new SysCisBankModel();

    sysCisBankModel.setMemberNo(sysCisBankEntity.getMemberNo());
    sysCisBankModel.setMemberName(sysCisBankEntity.getMemberName());
    sysCisBankModel.setMaxNrRecords(sysCisBankEntity.getMaxNrRecords());
    sysCisBankModel.setNrOfDaysProc(sysCisBankEntity.getNrOfDaysProc());
    sysCisBankModel.setPubHolProc(sysCisBankEntity.getPubHolProc());
    sysCisBankModel.setAcCreditor(sysCisBankEntity.getAcCreditor());
    sysCisBankModel.setAcDebtor(sysCisBankEntity.getAcDebtor());

    return sysCisBankModel;
  }


  public SysCisBankEntity translateCommonsSysCisBankModelToEntity(SysCisBankModel sysCisBankModel) {

    SysCisBankEntity sysCisBankEntity = new SysCisBankEntity();


    sysCisBankEntity.setMemberNo(sysCisBankModel.getMemberNo());
    sysCisBankEntity.setMemberName(sysCisBankModel.getMemberName());
    sysCisBankEntity.setMaxNrRecords(sysCisBankModel.getMaxNrRecords());
    sysCisBankEntity.setNrOfDaysProc(sysCisBankModel.getNrOfDaysProc());
    sysCisBankEntity.setPubHolProc(sysCisBankModel.getPubHolProc());
    sysCisBankEntity.setAcCreditor(sysCisBankModel.getAcCreditor());
    sysCisBankEntity.setAcDebtor(sysCisBankModel.getAcDebtor());

    return sysCisBankEntity;
  }

  public CasOpsRefSeqNrEntity translateMdtOpsRefSeqNrEntityToComonsModel(
      OpsRefSeqNumberCommonsModel opsRefSeqNumberCommonsModel) {
    CasOpsRefSeqNrEntity casOpsRefSeqNrEntity = new CasOpsRefSeqNrEntity();
    CasOpsRefSeqNrPK casOpsRefSeqNrPK = new CasOpsRefSeqNrPK();

    casOpsRefSeqNrEntity.setCreatedBy(opsRefSeqNumberCommonsModel.getCreatedBy());
    casOpsRefSeqNrEntity.setCreatedDate(opsRefSeqNumberCommonsModel.getCreatedDate());
    casOpsRefSeqNrEntity.setLastFileNr(opsRefSeqNumberCommonsModel.getLastFileNr());
    casOpsRefSeqNrEntity.setLastSeqNr(opsRefSeqNumberCommonsModel.getLastSeqNr());
    casOpsRefSeqNrEntity.setModifiedBy(opsRefSeqNumberCommonsModel.getModifiedBy());
    casOpsRefSeqNrEntity.setModifiedDate(opsRefSeqNumberCommonsModel.getModifiedDate());
    casOpsRefSeqNrPK.setMemberNo(opsRefSeqNumberCommonsModel.getMemberNo());
    casOpsRefSeqNrPK.setServiceId(opsRefSeqNumberCommonsModel.getServiceId());
    casOpsRefSeqNrEntity.setCasOpsRefSeqNrPK(casOpsRefSeqNrPK);


    return casOpsRefSeqNrEntity;

  }

  public OpsRefSeqNumberCommonsModel translateOpsRefSeqNumberCommonsModelToEntity(
      CasOpsRefSeqNrEntity casOpsRefSeqNrEntity) {

    OpsRefSeqNumberCommonsModel opsRefSeqNumberCommonsModel = new OpsRefSeqNumberCommonsModel();
    opsRefSeqNumberCommonsModel.setCreatedBy(casOpsRefSeqNrEntity.getCreatedBy());
    opsRefSeqNumberCommonsModel.setCreatedDate(casOpsRefSeqNrEntity.getCreatedDate());
    opsRefSeqNumberCommonsModel.setLastFileNr(casOpsRefSeqNrEntity.getLastFileNr());
    opsRefSeqNumberCommonsModel.setLastSeqNr(casOpsRefSeqNrEntity.getLastSeqNr());
    opsRefSeqNumberCommonsModel.setMemberNo(casOpsRefSeqNrEntity.getCasOpsRefSeqNrPK().getMemberNo());
    opsRefSeqNumberCommonsModel.setModifiedBy(casOpsRefSeqNrEntity.getModifiedBy());
    opsRefSeqNumberCommonsModel.setModifiedDate(casOpsRefSeqNrEntity.getModifiedDate());
    opsRefSeqNumberCommonsModel.setServiceId(casOpsRefSeqNrEntity.getCasOpsRefSeqNrPK().getServiceId());
    return opsRefSeqNumberCommonsModel;

  }

  public CasOpsSotEotCtrlEntity translateMdtAcOpsSotEotCtrlEntityToModel(
      AcOpsSotEotCntrlModel acOpsSotEotCntrlModel) {
    CasOpsSotEotCtrlEntity casOpsSotEotCtrlEntity = new CasOpsSotEotCtrlEntity();

    CasOpsSotEotCtrlPK casOpsSotEotCtrlPK = new CasOpsSotEotCtrlPK();
    casOpsSotEotCtrlPK.setInstId(acOpsSotEotCntrlModel.getInstId());
    casOpsSotEotCtrlPK.setServiceId(acOpsSotEotCntrlModel.getServiceId());

    casOpsSotEotCtrlEntity.setCasOpsSotEotCtrlPK(casOpsSotEotCtrlPK);
    casOpsSotEotCtrlEntity.setEotIn(acOpsSotEotCntrlModel.getEotIn());
    casOpsSotEotCtrlEntity.setEotOut(acOpsSotEotCntrlModel.getEotOut());
    casOpsSotEotCtrlEntity.setSotIn(acOpsSotEotCntrlModel.getSotIn());
    casOpsSotEotCtrlEntity.setSotOut(acOpsSotEotCntrlModel.getSotOut());


    return casOpsSotEotCtrlEntity;

  }


  public AcOpsSotEotCntrlModel translateAcOpsSotEotCntrlModelToEntity(
      CasOpsSotEotCtrlEntity casOpsSotEotCtrlEntity) {
    AcOpsSotEotCntrlModel acOpsSotEotCntrlModel = new AcOpsSotEotCntrlModel();

    acOpsSotEotCntrlModel.setEotIn(casOpsSotEotCtrlEntity.getEotIn());
    acOpsSotEotCntrlModel.setEotOut(casOpsSotEotCtrlEntity.getEotOut());
    acOpsSotEotCntrlModel.setInstId(casOpsSotEotCtrlEntity.getCasOpsSotEotCtrlPK().getInstId());
    acOpsSotEotCntrlModel.setServiceId(
        casOpsSotEotCtrlEntity.getCasOpsSotEotCtrlPK().getServiceId());
    acOpsSotEotCntrlModel.setSotIn(casOpsSotEotCtrlEntity.getSotIn());
    acOpsSotEotCntrlModel.setSotOut(casOpsSotEotCtrlEntity.getSotOut());


    return acOpsSotEotCntrlModel;

  }


  public CasSysctrlSchedulerEntity translateSchedulerCommonsModelToEntity(
      SchedulerCommonsModel schedulerCommonsModel) {

    CasSysctrlSchedulerEntity casSysctrlSchedulerEntity = new CasSysctrlSchedulerEntity();
    casSysctrlSchedulerEntity.setActiveInd(schedulerCommonsModel.getActiveInd());
    casSysctrlSchedulerEntity.setRescheduleTime(schedulerCommonsModel.getRescheduleTime());
    casSysctrlSchedulerEntity.setSchedulerKey(schedulerCommonsModel.getSchedulerKey());
    casSysctrlSchedulerEntity.setSchedulerName(schedulerCommonsModel.getSchedulerName());

    return casSysctrlSchedulerEntity;


  }

  public SchedulerCommonsModel translateMdtSysctrlSchedulerEntityToCommonsModel(
      CasSysctrlSchedulerEntity casSysctrlSchedulerEntity) {
    SchedulerCommonsModel schedulerCommonsModel = new SchedulerCommonsModel();
    schedulerCommonsModel.setActiveInd(casSysctrlSchedulerEntity.getActiveInd());
    schedulerCommonsModel.setRescheduleTime(casSysctrlSchedulerEntity.getRescheduleTime());
    schedulerCommonsModel.setSchedulerKey(casSysctrlSchedulerEntity.getSchedulerKey());
    schedulerCommonsModel.setSchedulerName(casSysctrlSchedulerEntity.getSchedulerName());

    return schedulerCommonsModel;
  }


  public SchedulerCommonsModel translateMdtOpsSchedulerEntityToCommonsModel(
      CasOpsSchedulerEntity casOpsSchedulerEntity) {
    SchedulerCommonsModel schedulerCommonsModel = new SchedulerCommonsModel();
    schedulerCommonsModel.setActiveInd(casOpsSchedulerEntity.getActiveInd());
    schedulerCommonsModel.setRescheduleTime(casOpsSchedulerEntity.getRescheduleTime());
    schedulerCommonsModel.setSchedulerKey(casOpsSchedulerEntity.getSchedulerKey());
    schedulerCommonsModel.setSchedulerName(casOpsSchedulerEntity.getSchedulerName());

    return schedulerCommonsModel;
  }

  public CasOpsProcessControlsEntity translateOpsProcessControlModelToEntity(
      OpsProcessControlModel opsProcessControlModel) {
    CasOpsProcessControlsEntity casOpsProcessControlsEntity =
        new CasOpsProcessControlsEntity();

    casOpsProcessControlsEntity.setCisDownloadInd(opsProcessControlModel.getCisDownloadInd());
    casOpsProcessControlsEntity.setProcessDate(opsProcessControlModel.getProcessDate());

    return casOpsProcessControlsEntity;
  }

  public OpsProcessControlModel translateMdtAcOpsProcessControlsEntityToCommonsModel(
      CasOpsProcessControlsEntity casOpsProcessControlsEntity) {
    OpsProcessControlModel opsProcessControlModel = new OpsProcessControlModel();

    opsProcessControlModel.setCisDownloadInd(casOpsProcessControlsEntity.getCisDownloadInd());
    opsProcessControlModel.setProcessDate(casOpsProcessControlsEntity.getProcessDate());

    return opsProcessControlModel;
  }


  public CasSysctrlFileStatusEntity translateFileStatusCommonsModelToEntity(
      FileStatusCommonsModel fileStatusCommonsModel) {
    CasSysctrlFileStatusEntity casSysctrlFileStatusEntity = new CasSysctrlFileStatusEntity();
    casSysctrlFileStatusEntity.setActiveInd(fileStatusCommonsModel.getActiveInd());
    casSysctrlFileStatusEntity.setStatus(fileStatusCommonsModel.getStatus());
    casSysctrlFileStatusEntity.setStatusDescription(fileStatusCommonsModel.getStatusDescription());

    return casSysctrlFileStatusEntity;
  }

  public FileStatusCommonsModel translateMdtSysctrlFileStatusEntityToCommonsModel(
      CasSysctrlFileStatusEntity casSysctrlFileStatusEntity) {

    FileStatusCommonsModel fileStatusCommonsModel = new FileStatusCommonsModel();

    fileStatusCommonsModel.setActiveInd(casSysctrlFileStatusEntity.getActiveInd());
    fileStatusCommonsModel.setStatus(casSysctrlFileStatusEntity.getStatus());
    fileStatusCommonsModel.setStatusDescription(casSysctrlFileStatusEntity.getStatusDescription());

    return fileStatusCommonsModel;
  }


  public CasOpsSlaTimesEntity translateOpsSlaTimesCommonsModelToEntity(
      OpsSlaTimesCommonsModel opsSlaTimesCommonsModel) {
    CasOpsSlaTimesEntity casOpsSlaTimesEntity = new CasOpsSlaTimesEntity();
    casOpsSlaTimesEntity.setEndTime(opsSlaTimesCommonsModel.getEndTime());
    casOpsSlaTimesEntity.setService(opsSlaTimesCommonsModel.getService());
    casOpsSlaTimesEntity.setStartTime(opsSlaTimesCommonsModel.getStartTime());

    return casOpsSlaTimesEntity;
  }


  public OpsSlaTimesCommonsModel translateMdtOpsSlaTimesEntityToCommonsModel(
      CasOpsSlaTimesEntity casOpsSlaTimesEntity) {

    OpsSlaTimesCommonsModel opsSlaTimesCommonsModel = new OpsSlaTimesCommonsModel();

    opsSlaTimesCommonsModel.setEndTime(casOpsSlaTimesEntity.getEndTime());
    opsSlaTimesCommonsModel.setService(casOpsSlaTimesEntity.getService());
    opsSlaTimesCommonsModel.setStartTime(casOpsSlaTimesEntity.getStartTime());

    return opsSlaTimesCommonsModel;
  }


  public CasOpsTransCtrlMsgEntity translateTransCtrlMsgModelToEntity(
      TransCtrlMsgModel transCtrlMsgModel) {
    CasOpsTransCtrlMsgEntity casOpsTransCtrlMsgEntity = new CasOpsTransCtrlMsgEntity();

    casOpsTransCtrlMsgEntity.setActiveInd(transCtrlMsgModel.getActiveInd());
    casOpsTransCtrlMsgEntity.setCtrlMsgId(transCtrlMsgModel.getCtrlMsgId());
    casOpsTransCtrlMsgEntity.setMemberIdFm(transCtrlMsgModel.getMemberIdFm());
    casOpsTransCtrlMsgEntity.setMemberIdTo(transCtrlMsgModel.getMemberIdTo());
    casOpsTransCtrlMsgEntity.setMsgType(transCtrlMsgModel.getMsgType());
    casOpsTransCtrlMsgEntity.setNrOfFiles(transCtrlMsgModel.getNrOfFiles());
    casOpsTransCtrlMsgEntity.setNrOfFilesReceived(transCtrlMsgModel.getNrOfFilesReceived());
    casOpsTransCtrlMsgEntity.setNrOfRecords(transCtrlMsgModel.getNrOfRecords());
    casOpsTransCtrlMsgEntity.setNrOfRecordsReceived(transCtrlMsgModel.getNrOfRecordsReceived());
    casOpsTransCtrlMsgEntity.setProcessDate(transCtrlMsgModel.getProcessDate());
    casOpsTransCtrlMsgEntity.setServiceId(transCtrlMsgModel.getServiceId());
    casOpsTransCtrlMsgEntity.setSystemStatus(transCtrlMsgModel.getSystemStatus());
    casOpsTransCtrlMsgEntity.setValidRecordsReceived(transCtrlMsgModel.getValidRecordsReceived());
    casOpsTransCtrlMsgEntity.setValueOfRecords(transCtrlMsgModel.getValueOfRecords());
    casOpsTransCtrlMsgEntity.setValueOfRecordsCurr(transCtrlMsgModel.getValueOfRecordsCurr());

    return casOpsTransCtrlMsgEntity;


  }


  public TransCtrlMsgModel translateMdtAcOpsTransCtrlMsgEntityToCommonsModel(
      CasOpsTransCtrlMsgEntity casOpsTransCtrlMsgEntity) {
    TransCtrlMsgModel transCtrlMsgModel = new TransCtrlMsgModel();

    transCtrlMsgModel.setActiveInd(casOpsTransCtrlMsgEntity.getActiveInd());
    transCtrlMsgModel.setCtrlMsgId(casOpsTransCtrlMsgEntity.getCtrlMsgId());
    transCtrlMsgModel.setMemberIdFm(casOpsTransCtrlMsgEntity.getMemberIdFm());
    transCtrlMsgModel.setMemberIdTo(casOpsTransCtrlMsgEntity.getMemberIdTo());
    transCtrlMsgModel.setMsgType(casOpsTransCtrlMsgEntity.getMsgType());
    transCtrlMsgModel.setNrOfFiles(casOpsTransCtrlMsgEntity.getNrOfFiles());
    transCtrlMsgModel.setNrOfFilesReceived(casOpsTransCtrlMsgEntity.getNrOfFilesReceived());
    transCtrlMsgModel.setNrOfRecords(casOpsTransCtrlMsgEntity.getNrOfRecords());
    transCtrlMsgModel.setNrOfRecordsReceived(casOpsTransCtrlMsgEntity.getNrOfRecordsReceived());
    transCtrlMsgModel.setProcessDate(casOpsTransCtrlMsgEntity.getProcessDate());
    transCtrlMsgModel.setServiceId(casOpsTransCtrlMsgEntity.getServiceId());
    transCtrlMsgModel.setSystemStatus(casOpsTransCtrlMsgEntity.getSystemStatus());
    transCtrlMsgModel.setValidRecordsReceived(casOpsTransCtrlMsgEntity.getValidRecordsReceived());
    transCtrlMsgModel.setValueOfRecords(casOpsTransCtrlMsgEntity.getValueOfRecords());
    transCtrlMsgModel.setValueOfRecordsCurr(casOpsTransCtrlMsgEntity.getValueOfRecordsCurr());

    return transCtrlMsgModel;
  }


  public SysCisBranchModel translateSysCisBranchEntityToCommonsModel(
      SysCisBranchEntity sysCisBranchEntity) {
    SysCisBranchModel sysCisBranchModel = new SysCisBranchModel();

    sysCisBranchModel.setMemberNo(sysCisBranchEntity.getMemberNo());
    sysCisBranchModel.setAcCreditor(sysCisBranchEntity.getAcCreditor());
    sysCisBranchModel.setAcDebtor(sysCisBranchEntity.getAcDebtor());
    sysCisBranchModel.setBranchName(sysCisBranchEntity.getBranchName());
    sysCisBranchModel.setBranchNo(sysCisBranchEntity.getBranchNo());
    sysCisBranchModel.setDivision(sysCisBranchEntity.getDivision());

    return sysCisBranchModel;


  }


  public CasCnfgAmendmentCodesEntity translateCommonsAmendmentCodesModelToEntity(
      AmendmentCodesModel amendmentCodesModel) {

    CasCnfgAmendmentCodesEntity casCnfgAmendmentCodesEntity = new CasCnfgAmendmentCodesEntity();

    casCnfgAmendmentCodesEntity.setAmendmentCodes(amendmentCodesModel.getAmendmentCodes());
    casCnfgAmendmentCodesEntity.setAmendmentCodesDescription(
        amendmentCodesModel.getAmendmentCodesDescription());
    casCnfgAmendmentCodesEntity.setActiveInd(amendmentCodesModel.getActiveInd());
    casCnfgAmendmentCodesEntity.setCreatedBy(amendmentCodesModel.getCreatedBy());
    casCnfgAmendmentCodesEntity.setCreatedDate(amendmentCodesModel.getCreatedDate());
    casCnfgAmendmentCodesEntity.setModifiedBy(amendmentCodesModel.getModifiedBy());
    casCnfgAmendmentCodesEntity.setModifiedDate(amendmentCodesModel.getModifiedDate());

    return casCnfgAmendmentCodesEntity;


  }

  public AmendmentCodesModel translateMdtCnfgAmendmentCodesEntityToCommonsModel(
      CasCnfgAmendmentCodesEntity localEntity) {

    AmendmentCodesModel amendmentCodesModel = new AmendmentCodesModel();

    amendmentCodesModel.setAmendmentCodes(localEntity.getAmendmentCodes());
    amendmentCodesModel.setAmendmentCodesDescription(localEntity.getAmendmentCodesDescription());
    amendmentCodesModel.setActiveInd(localEntity.getActiveInd());
    amendmentCodesModel.setCreatedBy(localEntity.getCreatedBy());
    amendmentCodesModel.setCreatedDate(localEntity.getCreatedDate());
    amendmentCodesModel.setModifiedBy(localEntity.getModifiedBy());
    amendmentCodesModel.setModifiedDate(localEntity.getModifiedDate());

    return amendmentCodesModel;
  }

  public IamSessionEntity translateModelToIamSessionEntity(IamSessionModel iamSessionModel) {
    IamSessionEntity iamSessionEntity = new IamSessionEntity();

    iamSessionEntity.setSystemSeqNo(new BigDecimal(123));
    iamSessionEntity.setSessionKey(iamSessionModel.getSessionKey());
    iamSessionEntity.setSessionDate(iamSessionModel.getSessionDate());
    iamSessionEntity.setUserName(iamSessionModel.getUserName());
    iamSessionEntity.setUserRole(iamSessionModel.getUserRole());
    log.debug("iamSessionModel.getUseRole()------->" + iamSessionModel.getUserRole());

    return iamSessionEntity;
  }

  public IamSessionModel translateEntityToIamSessionModel(IamSessionEntity iamSessionEntity) {
    IamSessionModel iamSessionModel = new IamSessionModel();

    iamSessionModel.setSystemSeqNo(new BigDecimal(123));
    iamSessionModel.setSessionKey(iamSessionEntity.getSessionKey());
    iamSessionModel.setSessionDate(iamSessionEntity.getSessionDate());
    iamSessionModel.setUserName(iamSessionEntity.getUserName());
    iamSessionModel.setUserRole(iamSessionEntity.getUserRole());
    log.debug("iamSessionEntity.getUseRole()------->" + iamSessionEntity.getUserRole());

    return iamSessionModel;
  }

  public CasSysctrlSlaTimesEntity translateModelToMdtSysctrlSlaTimesEntity(
      SysCtrlSlaTimeModel sysCtrlSlaTimeModel) {
    CasSysctrlSlaTimesEntity casSysctrlSlaTimesEntity = new CasSysctrlSlaTimesEntity();

    casSysctrlSlaTimesEntity.setService(sysCtrlSlaTimeModel.getService());
    casSysctrlSlaTimesEntity.setEndTime(sysCtrlSlaTimeModel.getEndTime());
    casSysctrlSlaTimesEntity.setStartTime(sysCtrlSlaTimeModel.getStartTime());
    casSysctrlSlaTimesEntity.setCreatedBy(sysCtrlSlaTimeModel.getCreatedBy());
    casSysctrlSlaTimesEntity.setCreatedDate(sysCtrlSlaTimeModel.getCreatedDate());
    casSysctrlSlaTimesEntity.setModifiedBy(sysCtrlSlaTimeModel.getModifiedBy());
    casSysctrlSlaTimesEntity.setModifiedDate(sysCtrlSlaTimeModel.getModifiedDate());

    return casSysctrlSlaTimesEntity;
  }


  public SysCtrlSlaTimeModel translateMdtSysctrlSlaTimesEntityToCommonsModel(
      CasSysctrlSlaTimesEntity casSysctrlSlaTimesEntity) {
    SysCtrlSlaTimeModel sysCtrlSlaTimeModel = new SysCtrlSlaTimeModel();

    sysCtrlSlaTimeModel.setService(casSysctrlSlaTimesEntity.getService());
    sysCtrlSlaTimeModel.setEndTime(casSysctrlSlaTimesEntity.getEndTime());
    sysCtrlSlaTimeModel.setStartTime(casSysctrlSlaTimesEntity.getStartTime());
    sysCtrlSlaTimeModel.setCreatedBy(casSysctrlSlaTimesEntity.getCreatedBy());
    sysCtrlSlaTimeModel.setCreatedDate(casSysctrlSlaTimesEntity.getCreatedDate());
    sysCtrlSlaTimeModel.setModifiedBy(casSysctrlSlaTimesEntity.getModifiedBy());
    sysCtrlSlaTimeModel.setModifiedDate(casSysctrlSlaTimesEntity.getModifiedDate());

    return sysCtrlSlaTimeModel;

  }

  public SysCtrlSlaTimeModel translateEntityToSysCtrlSlaTimeModel(
      CasSysctrlSlaTimesEntity casSysctrlSlaTimesEntity) {
    SysCtrlSlaTimeModel sysCtrlSlaTimeModel = new SysCtrlSlaTimeModel();

    sysCtrlSlaTimeModel.setEndTime(casSysctrlSlaTimesEntity.getEndTime());
    sysCtrlSlaTimeModel.setService(casSysctrlSlaTimesEntity.getService());
    sysCtrlSlaTimeModel.setStartTime(casSysctrlSlaTimesEntity.getStartTime());
    sysCtrlSlaTimeModel.setCreatedBy(casSysctrlSlaTimesEntity.getCreatedBy());
    sysCtrlSlaTimeModel.setCreatedDate(casSysctrlSlaTimesEntity.getCreatedDate());
    sysCtrlSlaTimeModel.setModifiedBy(casSysctrlSlaTimesEntity.getModifiedBy());
    sysCtrlSlaTimeModel.setModifiedDate(casSysctrlSlaTimesEntity.getModifiedDate());

    return sysCtrlSlaTimeModel;

  }

  public AudSystemProcessEntity translateAudSystemModelToEntity(
      AudSystemProcessModel audSystemModel) {
    AudSystemProcessEntity systemEntity = new AudSystemProcessEntity();

    systemEntity.setSystemSeqNo(new BigDecimal(123));
    systemEntity.setProcess(audSystemModel.getProcess());
    systemEntity.setProcessDate(audSystemModel.getProcessDate());
    systemEntity.setUserId(audSystemModel.getUserId());

    return systemEntity;
  }


  public SchedulerCronModel translateSchedulerCronEntityToModel(
      CasSysctrlSchedulerCronEntity schedulerCronEntity) {
    SchedulerCronModel schedulerCronModel = new SchedulerCronModel();

    schedulerCronModel.setSystemSeqNo(schedulerCronEntity.getSystemSeqNo());
    schedulerCronModel.setSchedulerCronInterval(schedulerCronEntity.getSchedulerCronInterval());
    schedulerCronModel.setCronValue(schedulerCronEntity.getCronValue());
    schedulerCronModel.setActiveInd(schedulerCronEntity.getCronValue());

    return schedulerCronModel;
  }

  public MandateDailyTransModel translateDailyBillingEntityToModel(
      CasOpsDailyBillingEntity casOpsDailyBillingEntity) {
    MandateDailyTransModel mandateDailyTransModel = new MandateDailyTransModel();

    mandateDailyTransModel.setCreditorBank(casOpsDailyBillingEntity.getCreditorBank());
    mandateDailyTransModel.setDebtorBank(casOpsDailyBillingEntity.getDebtorBank());
    mandateDailyTransModel.setServiceId(casOpsDailyBillingEntity.getSubService());
    mandateDailyTransModel.setTxnType(casOpsDailyBillingEntity.getTxnType());
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    mandateDailyTransModel.setActionDate(sdf.format(casOpsDailyBillingEntity.getActionDate()));
    mandateDailyTransModel.setExtractMsgId(casOpsDailyBillingEntity.getExtMsgId());
    mandateDailyTransModel.setMndtReqTransId(casOpsDailyBillingEntity.getTxnId());
    mandateDailyTransModel.setMndtRefNumber(casOpsDailyBillingEntity.getMndtRefNum());
    mandateDailyTransModel.setAuthCode(casOpsDailyBillingEntity.getAuthCode());
    mandateDailyTransModel.setTrxnStatus(casOpsDailyBillingEntity.getTxnStatus());
    mandateDailyTransModel.setRespDate(sdf.format(casOpsDailyBillingEntity.getRespDate()));

    return mandateDailyTransModel;
  }

  public MandateDailyTransModel translateDailyBillingArcEntityToModel(
      MdtAcArcDailyBillingEntity mdtAcArcDailyBillingEntity) {
    MandateDailyTransModel mandateDailyTransModel = new MandateDailyTransModel();

    mandateDailyTransModel.setCreditorBank(mdtAcArcDailyBillingEntity.getCreditorBank());
    mandateDailyTransModel.setDebtorBank(mdtAcArcDailyBillingEntity.getDebtorBank());
    mandateDailyTransModel.setServiceId(mdtAcArcDailyBillingEntity.getSubService());
    mandateDailyTransModel.setTxnType(mdtAcArcDailyBillingEntity.getTxnType());
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    mandateDailyTransModel.setActionDate(sdf.format(mdtAcArcDailyBillingEntity.getActionDate()));
    mandateDailyTransModel.setExtractMsgId(mdtAcArcDailyBillingEntity.getExtMsgId());
    mandateDailyTransModel.setMndtReqTransId(mdtAcArcDailyBillingEntity.getTxnId());
    mandateDailyTransModel.setMndtRefNumber(mdtAcArcDailyBillingEntity.getMndtRefNum());
    mandateDailyTransModel.setAuthCode(mdtAcArcDailyBillingEntity.getAuthCode());
    mandateDailyTransModel.setTrxnStatus(mdtAcArcDailyBillingEntity.getTxnStatus());
    mandateDailyTransModel.setRespDate(sdf.format(mdtAcArcDailyBillingEntity.getRespDate()));

    return mandateDailyTransModel;
  }


  public MandatesCountCommonsModel translateCommonsToSystemStatusEntity(
      CasOpsMndtCountEntity mdtOpsMndtCountEntity) {
    MandatesCountCommonsModel mandatesCountCommonsModel = new MandatesCountCommonsModel();

    mandatesCountCommonsModel.setFileName(mdtOpsMndtCountEntity.getFileName());
    mandatesCountCommonsModel.setProcessDate(mdtOpsMndtCountEntity.getProcessDate());
    mandatesCountCommonsModel.setInstId(mdtOpsMndtCountEntity.getCasOpsMndtCountPK().getInstId());
    mandatesCountCommonsModel.setServiceId(
        mdtOpsMndtCountEntity.getCasOpsMndtCountPK().getServiceId());
    mandatesCountCommonsModel.setMsgId(mdtOpsMndtCountEntity.getCasOpsMndtCountPK().getMsgId());
    mandatesCountCommonsModel.setIncoming(mdtOpsMndtCountEntity.getIncoming());
    mandatesCountCommonsModel.setNrOfFiles(mdtOpsMndtCountEntity.getNrOfFiles());
    mandatesCountCommonsModel.setNrOfMsgs(mdtOpsMndtCountEntity.getNrOfMsgs());
    mandatesCountCommonsModel.setOutgoing(mdtOpsMndtCountEntity.getOutgoing());
    mandatesCountCommonsModel.setNrMsgsRejected(mdtOpsMndtCountEntity.getNrMsgsRejected());
    mandatesCountCommonsModel.setNrMsgsAccepted(mdtOpsMndtCountEntity.getNrMsgsAccepted());
    mandatesCountCommonsModel.setNrMsgsExtracted(mdtOpsMndtCountEntity.getNrMsgsExtracted());

    return mandatesCountCommonsModel;
  }

  public CasOpsMndtCountEntity translateCommonstoEntitySystemStatus(
      MandatesCountCommonsModel mandateCountModel) {
    CasOpsMndtCountEntity mdtOpsMndtCountEntity = new CasOpsMndtCountEntity();
    CasOpsMndtCountPK mdtOpsMndtCountPK = new CasOpsMndtCountPK();

    mdtOpsMndtCountPK.setInstId(mandateCountModel.getInstId());
    mdtOpsMndtCountPK.setMsgId(mandateCountModel.getMsgId());
    mdtOpsMndtCountPK.setServiceId(mandateCountModel.getServiceId());
    mdtOpsMndtCountEntity.setCasOpsMndtCountPK(mdtOpsMndtCountPK);
    mdtOpsMndtCountEntity.setFileName(mandateCountModel.getFileName());
    mdtOpsMndtCountEntity.setIncoming(mandateCountModel.getIncoming());
    mdtOpsMndtCountEntity.setNrOfFiles(mandateCountModel.getNrOfFiles());
    mdtOpsMndtCountEntity.setNrOfMsgs(mandateCountModel.getNrOfMsgs());
    mdtOpsMndtCountEntity.setOutgoing(mandateCountModel.getOutgoing());
    mdtOpsMndtCountEntity.setNrMsgsRejected(mandateCountModel.getNrMsgsRejected());
    mdtOpsMndtCountEntity.setProcessDate(mandateCountModel.getProcessDate());
    mdtOpsMndtCountEntity.setNrMsgsAccepted(mandateCountModel.getNrMsgsAccepted());
    mdtOpsMndtCountEntity.setNrMsgsExtracted(mandateCountModel.getNrMsgsExtracted());

    return mdtOpsMndtCountEntity;
  }

  public IncSotEotModel translateIncSotEotEntityToCommonsModel(IncSotEotEntityModel entityModel) {
    IncSotEotModel commonsModel = new IncSotEotModel();

    commonsModel.setInstId(entityModel.getInstId());
    commonsModel.setServiceId(entityModel.getServiceId());
    commonsModel.setSotIn(entityModel.getSotIn());
    commonsModel.setEotIn(entityModel.getEotIn());

    return commonsModel;
  }

  public ServicesModel translateIncomingMdtOpsServicesEntityToCommonsModel(
      CasOpsServicesEntity casOpsServicesEntity) {
    ServicesModel servicesModel = new ServicesModel();

    servicesModel.setCreatedBy(casOpsServicesEntity.getCreatedBy());
    servicesModel.setCreatedDate(casOpsServicesEntity.getCreatedDate());
    servicesModel.setModifiedBy(casOpsServicesEntity.getModifiedBy());
    servicesModel.setModifiedDate(casOpsServicesEntity.getModifiedDate());
    servicesModel.setRecordId(casOpsServicesEntity.getRecordId());
    servicesModel.setServiceIdIn(casOpsServicesEntity.getServiceIdIn());
    servicesModel.setServiceIdInDesc(casOpsServicesEntity.getServiceIdInDesc());
    servicesModel.setProcessDate(casOpsServicesEntity.getProcessDate());
    servicesModel.setProcessStatus(casOpsServicesEntity.getProcessStatus());
    servicesModel.setServiceIdOutSlaTime(casOpsServicesEntity.getServiceIdOutSlaTime());
    servicesModel.setActiveInd(casOpsServicesEntity.getActiveInd());
    servicesModel.setMsgTypeId(casOpsServicesEntity.getMsgTypeId());

    return servicesModel;
  }

  public ServicesModel translateOutgoingMdtOpsServicesEntityToCommonsModel(
      CasOpsServicesEntity casOpsServicesEntity) {
    ServicesModel servicesModel = new ServicesModel();

    servicesModel.setCreatedBy(casOpsServicesEntity.getCreatedBy());
    servicesModel.setCreatedDate(casOpsServicesEntity.getCreatedDate());
    servicesModel.setModifiedBy(casOpsServicesEntity.getModifiedBy());
    servicesModel.setModifiedDate(casOpsServicesEntity.getModifiedDate());
    servicesModel.setRecordId(casOpsServicesEntity.getRecordId());
    servicesModel.setServiceIdOut(casOpsServicesEntity.getServiceIdOut());
    servicesModel.setServiceIdOutDesc(casOpsServicesEntity.getServiceIdOutDesc());
    servicesModel.setProcessDate(casOpsServicesEntity.getProcessDate());
    servicesModel.setProcessStatus(casOpsServicesEntity.getProcessStatus());
    servicesModel.setServiceIdOutSlaTime(casOpsServicesEntity.getServiceIdOutSlaTime());
    servicesModel.setActiveInd(casOpsServicesEntity.getActiveInd());
    servicesModel.setMsgTypeId(casOpsServicesEntity.getMsgTypeId());

    return servicesModel;
  }

  public IncSotEotModel translateIncSotEotCommonsModelToEntity(
      CasOpsSotEotCtrlEntity casOpsSotEotCtrlEntity) {
    IncSotEotModel acOpsSotEotCntrlModel = new IncSotEotModel();

    acOpsSotEotCntrlModel.setEotIn(casOpsSotEotCtrlEntity.getEotIn());
    acOpsSotEotCntrlModel.setInstId(casOpsSotEotCtrlEntity.getCasOpsSotEotCtrlPK().getInstId());
    acOpsSotEotCntrlModel.setServiceId(
        casOpsSotEotCtrlEntity.getCasOpsSotEotCtrlPK().getServiceId());
    acOpsSotEotCntrlModel.setSotIn(casOpsSotEotCtrlEntity.getSotIn());

    return acOpsSotEotCntrlModel;
  }

  public IncSotEotModel translateIncCommonsToIncSotEotEntityModel(
      IncSotEotEntityModel incEntityModel) {
    IncSotEotModel sotEotCommonsModel = new IncSotEotModel();

    sotEotCommonsModel.setInstId(incEntityModel.getInstId());
    sotEotCommonsModel.setServiceId(incEntityModel.getServiceId());
    sotEotCommonsModel.setSotIn(incEntityModel.getSotIn());
    sotEotCommonsModel.setEotIn(incEntityModel.getEotIn());

    return sotEotCommonsModel;
  }

  public OutSotEotModel translateOutCommonsToIncSotEotEntityModel(
      OutSotEotEntityModel outEntityModel) {
    OutSotEotModel sotEotCommonsModel = new OutSotEotModel();

    sotEotCommonsModel.setInstId(outEntityModel.getInstId());
    sotEotCommonsModel.setServiceId(outEntityModel.getServiceId());
    sotEotCommonsModel.setSotOut(outEntityModel.getSotOut());
    sotEotCommonsModel.setEotOut(outEntityModel.getEotOut());

    return sotEotCommonsModel;
  }

  public FileSizeLimitModel translateMdtAcOpsFileSizeLimitEntityToCommonsModel(
      CasOpsFileSizeLimitEntity casOpsFileSizeLimitEntity) {

    FileSizeLimitModel fileSizeLimitModel = new FileSizeLimitModel();
    CasOpsFileSizeLimitPK casOpsFileSizeLimitPK = new CasOpsFileSizeLimitPK();

    fileSizeLimitModel.setMemberId(
        casOpsFileSizeLimitEntity.getCasOpsFileSizeLimitPK().getMemberId());
    fileSizeLimitModel.setSubService(
        casOpsFileSizeLimitEntity.getCasOpsFileSizeLimitPK().getSubService());

    fileSizeLimitModel.setProcessDate(casOpsFileSizeLimitEntity.getProcessDate());
    fileSizeLimitModel.setLimit(casOpsFileSizeLimitEntity.getLimit());
    fileSizeLimitModel.setDirection(casOpsFileSizeLimitEntity.getDirection());
    fileSizeLimitModel.setActiveId(casOpsFileSizeLimitEntity.getActiveId());

    return fileSizeLimitModel;

  }

  public CasOpsFileSizeLimitEntity translateOpsFileSizeLimitCommonsModelToEntity(
      FileSizeLimitModel fileSizeLimitModel) {

    CasOpsFileSizeLimitEntity casOpsFileSizeLimitEntity = new CasOpsFileSizeLimitEntity();
    CasOpsFileSizeLimitPK casOpsFileSizeLimitPK = new CasOpsFileSizeLimitPK();

    casOpsFileSizeLimitPK.setMemberId(fileSizeLimitModel.getMemberId());
    casOpsFileSizeLimitPK.setSubService(fileSizeLimitModel.getSubService());
    casOpsFileSizeLimitEntity.setLimit(fileSizeLimitModel.getLimit());
    casOpsFileSizeLimitEntity.setDirection(fileSizeLimitModel.getDirection());
    casOpsFileSizeLimitEntity.setProcessDate(fileSizeLimitModel.getProcessDate());
    casOpsFileSizeLimitEntity.setActiveId(fileSizeLimitModel.getActiveId());
    casOpsFileSizeLimitEntity.setCasOpsFileSizeLimitPK(casOpsFileSizeLimitPK);

    return casOpsFileSizeLimitEntity;

  }


  public CasSysctrlFileSizeLimitEntity translateSysCtrlFileSizeLimitCommonsModelToEntity(
      SysCtrlFileSizeLimitModel sysCtrlFileSizeLimitModel) {
    CasSysctrlFileSizeLimitEntity casSysctrlFileSizeLimitEntity =
        new CasSysctrlFileSizeLimitEntity();
    CasSysctrlFileSizeLimitPK casSysctrlFileSizeLimitPK = new CasSysctrlFileSizeLimitPK();

    casSysctrlFileSizeLimitPK.setMemberId(sysCtrlFileSizeLimitModel.getMemberId());
    casSysctrlFileSizeLimitPK.setSubService(sysCtrlFileSizeLimitModel.getSubService());
    casSysctrlFileSizeLimitEntity.setLimit(sysCtrlFileSizeLimitModel.getLimit());
    casSysctrlFileSizeLimitEntity.setDirection(sysCtrlFileSizeLimitModel.getDirection());
    casSysctrlFileSizeLimitEntity.setActiveId(sysCtrlFileSizeLimitModel.getActiveId());
    casSysctrlFileSizeLimitEntity.setCreatedBy(sysCtrlFileSizeLimitModel.getCreatedBy());
    casSysctrlFileSizeLimitEntity.setCreatedDate(sysCtrlFileSizeLimitModel.getCreatedDate());
    casSysctrlFileSizeLimitEntity.setModifiedBy(sysCtrlFileSizeLimitModel.getModifiedBy());
    casSysctrlFileSizeLimitEntity.setModifiedDate(sysCtrlFileSizeLimitModel.getModifiedDate());
    casSysctrlFileSizeLimitEntity.setMdtSysctrlFileSizeLimitPK(casSysctrlFileSizeLimitPK);

    return casSysctrlFileSizeLimitEntity;

  }

  public SysCtrlFileSizeLimitModel translateMdtSysctrlFileSizeLimitEntityToCommonsModel(
      CasSysctrlFileSizeLimitEntity casSysctrlFileSizeLimitEntity) {
    SysCtrlFileSizeLimitModel sysCtrlFileSizeLimitModel = new SysCtrlFileSizeLimitModel();
    CasSysctrlFileSizeLimitPK casSysctrlFileSizeLimitPK = new CasSysctrlFileSizeLimitPK();

    sysCtrlFileSizeLimitModel.setMemberId(
        casSysctrlFileSizeLimitEntity.getMdtSysctrlFileSizeLimitPK().getMemberId());
    sysCtrlFileSizeLimitModel.setSubService(
        casSysctrlFileSizeLimitEntity.getMdtSysctrlFileSizeLimitPK().getSubService());

    sysCtrlFileSizeLimitModel.setCreatedDate(casSysctrlFileSizeLimitEntity.getCreatedDate());
    sysCtrlFileSizeLimitModel.setCreatedBy(casSysctrlFileSizeLimitEntity.getCreatedBy());
    sysCtrlFileSizeLimitModel.setModifiedBy(casSysctrlFileSizeLimitEntity.getModifiedBy());
    sysCtrlFileSizeLimitModel.setModifiedDate(casSysctrlFileSizeLimitEntity.getModifiedDate());
    sysCtrlFileSizeLimitModel.setLimit(casSysctrlFileSizeLimitEntity.getLimit());
    sysCtrlFileSizeLimitModel.setDirection(casSysctrlFileSizeLimitEntity.getDirection());
    sysCtrlFileSizeLimitModel.setActiveId(casSysctrlFileSizeLimitEntity.getActiveId());
    casSysctrlFileSizeLimitEntity.setMdtSysctrlFileSizeLimitPK(casSysctrlFileSizeLimitPK);

    return sysCtrlFileSizeLimitModel;
  }

}
