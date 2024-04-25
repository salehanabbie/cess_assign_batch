
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
import com.bsva.entities.MdtAcOpsDailyBillingEntity;
import com.bsva.entities.MdtAcOpsFileSizeLimitEntity;
import com.bsva.entities.MdtAcOpsFileSizeLimitPK;
import com.bsva.entities.MdtAcOpsMndtCountEntity;
import com.bsva.entities.MdtAcOpsMndtCountPK;
import com.bsva.entities.MdtAcOpsProcessControlsEntity;
import com.bsva.entities.MdtAcOpsSchedulerEntity;
import com.bsva.entities.MdtAcOpsSotEotCtrlEntity;
import com.bsva.entities.MdtAcOpsSotEotCtrlPK;
import com.bsva.entities.MdtAcOpsStatusDetailsEntity;
import com.bsva.entities.MdtAcOpsStatusHdrsEntity;
import com.bsva.entities.MdtAcOpsTransCtrlMsgEntity;
import com.bsva.entities.MdtCnfgAccountTypeEntity;
import com.bsva.entities.MdtCnfgAdjustmentCatEntity;
import com.bsva.entities.MdtCnfgAmendmentCodesEntity;
import com.bsva.entities.MdtCnfgAuthTypeEntity;
import com.bsva.entities.MdtCnfgCancellationCodesEntity;
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
import com.bsva.entities.MdtOpsProcessControlsEntity;
import com.bsva.entities.MdtOpsRefSeqNrEntity;
import com.bsva.entities.MdtOpsRefSeqNrPK;
import com.bsva.entities.MdtOpsServicesEntity;
import com.bsva.entities.MdtOpsSlaTimesEntity;
import com.bsva.entities.MdtSysctrlBillingParamEntity;
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

  public MdtCnfgLocalInstrCodesEntity translateCommonsInstrumentModelToEntity(
      LocalInstrumentCodesModel localInstrumentCodesModel) {
    MdtCnfgLocalInstrCodesEntity mdtLocalInstrEntity = new MdtCnfgLocalInstrCodesEntity();

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
      MdtCnfgLocalInstrCodesEntity mdtLocalInstrumentCodesEntity) {
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

  public MdtCnfgReasonCodesEntity transalateCommonsReasonCodesModelToEntity(
      ReasonCodesModel reasonCodesModel) {
    MdtCnfgReasonCodesEntity mdtReasCodes = new MdtCnfgReasonCodesEntity();

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
      MdtCnfgReasonCodesEntity mdtReasonCodes) {
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

  public MdtCnfgSequenceTypeEntity translateCommonsSequenceTypesModelToEntity(
      SequenceTypesModel sequenceTypesModel) {
    MdtCnfgSequenceTypeEntity mdtSeqTypeEntity = new MdtCnfgSequenceTypeEntity();

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
      MdtCnfgSequenceTypeEntity mdtSequenceTypeEntity) {
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
      MdtCnfgCurrencyCodesEntity mdtCurrencyCodesEntity) {
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
      MdtCnfgDebitValueTypeEntity mdtDebitValueTypeEntity) {
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

  public MdtCnfgDebitValueTypeEntity translateCommonsDebitValueModelToEntity(
      DebitValueTypeModel debitValueTypeModel) {
    MdtCnfgDebitValueTypeEntity mdtDebitValueTypeEntity = new MdtCnfgDebitValueTypeEntity();

    mdtDebitValueTypeEntity.setDebValueTypeCode(debitValueTypeModel.getDebValueTypeCode());
    mdtDebitValueTypeEntity.setDebValueTypeDescription(debitValueTypeModel.getDebValueTypeDesc());
    mdtDebitValueTypeEntity.setActiveInd(debitValueTypeModel.getActiveInd());
    mdtDebitValueTypeEntity.setCreatedBy(debitValueTypeModel.getCreatedBy());
    mdtDebitValueTypeEntity.setCreatedDate(debitValueTypeModel.getCreatedDate());
    mdtDebitValueTypeEntity.setModifiedBy(debitValueTypeModel.getModifiedBy());
    mdtDebitValueTypeEntity.setModifiedDate(debitValueTypeModel.getModifiedDate());

    return mdtDebitValueTypeEntity;
  }

  public MdtAcOpsTransCtrlMsgEntity translateCommonsAcOpsTransCtrlModelToEntity(
      AcOpsTransCtrlMsgModel acOpsTransCtrlMsgModel) {

    MdtAcOpsTransCtrlMsgEntity mdtAcOpsTransCtrlMsgEntity = new MdtAcOpsTransCtrlMsgEntity();
    mdtAcOpsTransCtrlMsgEntity.setActiveInd(acOpsTransCtrlMsgModel.getActiveInd());
    mdtAcOpsTransCtrlMsgEntity.setCtrlMsgId(acOpsTransCtrlMsgModel.getCtrlMsgId());
    mdtAcOpsTransCtrlMsgEntity.setMemberIdFm(acOpsTransCtrlMsgModel.getMemberIdFm());
    mdtAcOpsTransCtrlMsgEntity.setMemberIdTo(acOpsTransCtrlMsgModel.getMemberIdTo());
    mdtAcOpsTransCtrlMsgEntity.setMsgType(acOpsTransCtrlMsgModel.getMsgType());
    mdtAcOpsTransCtrlMsgEntity.setNrOfFiles(acOpsTransCtrlMsgModel.getNrOfFiles());
    mdtAcOpsTransCtrlMsgEntity.setNrOfFilesReceived(acOpsTransCtrlMsgModel.getNrOfFilesReceived());
    mdtAcOpsTransCtrlMsgEntity.setNrOfRecords(acOpsTransCtrlMsgModel.getNrOfRecords());
    mdtAcOpsTransCtrlMsgEntity.setProcessDate(acOpsTransCtrlMsgModel.getProcessDate());
    mdtAcOpsTransCtrlMsgEntity.setServiceId(acOpsTransCtrlMsgModel.getServiceId());
    mdtAcOpsTransCtrlMsgEntity.setSystemStatus(acOpsTransCtrlMsgModel.getSystemStatus());
    mdtAcOpsTransCtrlMsgEntity.setValidRecordsReceived(
        acOpsTransCtrlMsgModel.getValidRecordsReceived());
    mdtAcOpsTransCtrlMsgEntity.setValueOfRecords(acOpsTransCtrlMsgModel.getValueOfRecords());
    mdtAcOpsTransCtrlMsgEntity.setValueOfRecordsCurr(
        acOpsTransCtrlMsgModel.getValueOfRecordsCurr());
    return mdtAcOpsTransCtrlMsgEntity;


  }

  public AcOpsTransCtrlMsgModel translateMdtAcOpsTransCtrlMsgEntityToCommons(
      MdtAcOpsTransCtrlMsgEntity mdtAcOpsTransCtrlMsgEntity) {
    AcOpsTransCtrlMsgModel acOpsTransCtrlMsgModel = new AcOpsTransCtrlMsgModel();

    acOpsTransCtrlMsgModel.setActiveInd(mdtAcOpsTransCtrlMsgEntity.getActiveInd());
    acOpsTransCtrlMsgModel.setCtrlMsgId(mdtAcOpsTransCtrlMsgEntity.getCtrlMsgId());
    acOpsTransCtrlMsgModel.setMemberIdFm(mdtAcOpsTransCtrlMsgEntity.getMemberIdFm());
    acOpsTransCtrlMsgModel.setMemberIdTo(mdtAcOpsTransCtrlMsgEntity.getMemberIdTo());
    acOpsTransCtrlMsgModel.setMsgType(mdtAcOpsTransCtrlMsgEntity.getMsgType());
    acOpsTransCtrlMsgModel.setNrOfFiles(mdtAcOpsTransCtrlMsgEntity.getNrOfFiles());
    acOpsTransCtrlMsgModel.setNrOfFilesReceived(mdtAcOpsTransCtrlMsgEntity.getNrOfFilesReceived());
    acOpsTransCtrlMsgModel.setNrOfRecords(mdtAcOpsTransCtrlMsgEntity.getNrOfRecords());
    acOpsTransCtrlMsgModel.setNrOfRecordsReceived(
        mdtAcOpsTransCtrlMsgEntity.getNrOfRecordsReceived());
    acOpsTransCtrlMsgModel.setProcessDate(mdtAcOpsTransCtrlMsgEntity.getProcessDate());
    acOpsTransCtrlMsgModel.setServiceId(mdtAcOpsTransCtrlMsgEntity.getServiceId());
    acOpsTransCtrlMsgModel.setSystemStatus(mdtAcOpsTransCtrlMsgEntity.getSystemStatus());
    acOpsTransCtrlMsgModel.setValidRecordsReceived(
        mdtAcOpsTransCtrlMsgEntity.getValidRecordsReceived());
    acOpsTransCtrlMsgModel.setValueOfRecords(mdtAcOpsTransCtrlMsgEntity.getValueOfRecords());
    acOpsTransCtrlMsgModel.setValueOfRecordsCurr(
        mdtAcOpsTransCtrlMsgEntity.getValueOfRecordsCurr());

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
      MdtCnfgSeverityCodesEntity mdtCnfgSeverityCodesEntity) {

    ConfgSeverityCodesModel confgSeverityCodesModel = new ConfgSeverityCodesModel();

    confgSeverityCodesModel.setSeverityCode(mdtCnfgSeverityCodesEntity.getSeverityCode());
    confgSeverityCodesModel.setSeverityCodeDesc(mdtCnfgSeverityCodesEntity.getSeverityCodeDesc());
    confgSeverityCodesModel.setCreatedBy(mdtCnfgSeverityCodesEntity.getCreatedBy());
    confgSeverityCodesModel.setCreatedDate(mdtCnfgSeverityCodesEntity.getCreatedDate());
    confgSeverityCodesModel.setModifiedBy(mdtCnfgSeverityCodesEntity.getModifiedBy());
    confgSeverityCodesModel.setModifiedBy(mdtCnfgSeverityCodesEntity.getModifiedBy());

    log.debug("Translator CnfgSeverity: " + confgSeverityCodesModel);
    return confgSeverityCodesModel;
  }


  public MdtCnfgSeverityCodesEntity translateCommonsConfgSeverityCodesModelToEntity(
      ConfgSeverityCodesModel confgSeverityCodesModel) {

    MdtCnfgSeverityCodesEntity mdtCnfgSeverityCodesEntity = new MdtCnfgSeverityCodesEntity();

    mdtCnfgSeverityCodesEntity.setSeverityCode(confgSeverityCodesModel.getSeverityCode());
    mdtCnfgSeverityCodesEntity.setSeverityCodeDesc(confgSeverityCodesModel.getSeverityCodeDesc());
    mdtCnfgSeverityCodesEntity.setCreatedBy(confgSeverityCodesModel.getCreatedBy());
    mdtCnfgSeverityCodesEntity.setCreatedDate(confgSeverityCodesModel.getCreatedDate());
    mdtCnfgSeverityCodesEntity.setModifiedBy(confgSeverityCodesModel.getModifiedBy());
    mdtCnfgSeverityCodesEntity.setModifiedBy(confgSeverityCodesModel.getModifiedBy());

    return mdtCnfgSeverityCodesEntity;


  }


  public MdtCnfgFrequencyCodesEntity translateCommnsFrequencyCodeModelToEntity(
      FrequencyCodesModel frequencyCodesModel) {
    MdtCnfgFrequencyCodesEntity mdtFrequencyCodesEntity = new MdtCnfgFrequencyCodesEntity();

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
      MdtCnfgFrequencyCodesEntity mdtFrequencyCodesEntity) {
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
      MdtCnfgCurrencyCodesEntity currencyEntity) {
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

  public MdtCnfgCurrencyCodesEntity translateCommonsCurrencyCodesModelToEntity(
      CurrencyCodesModel currencyCodesModel) {

    MdtCnfgCurrencyCodesEntity mdtCurrencyCodesEntity = new MdtCnfgCurrencyCodesEntity();

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


  public MdtCnfgErrorCodesEntity translateCommonsErrorModelToEntity(
      ConfgErrorCodesModel errorCodesModel) {
    MdtCnfgErrorCodesEntity mdtErrorEntity = new MdtCnfgErrorCodesEntity();

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
      MdtCnfgErrorCodesEntity mdtErrorCodesEntity) {
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
      MdtCnfgReasonCodesEntity mdtReasonCodesEntity) {
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
      MdtOpsCustParamEntity custParamEntity) {
    CustomerParametersModel custParamModel = new CustomerParametersModel();

    custParamModel.setActiveInd(custParamEntity.getActiveInd());
    custParamModel.setInstId(custParamEntity.getInstId());
    custParamModel.setManInitXsdNs(custParamEntity.getManInitXsdNs());
    custParamModel.setManAmdXsdNs(custParamEntity.getManAmdXsdNs());
    custParamModel.setManCanXsdNs(custParamEntity.getManCanXsdNs());
    custParamModel.setManAccpXsdNs(custParamEntity.getManAccpXsdNs());
    // custParamModel.setManReqXsdNs(custParamEntity.getManReqXsdNs());
    custParamModel.setMdtReqIdReuseInd(custParamEntity.getMdtReqIdReuseInd());
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


  public MdtOpsFileRegEntity translateCommonsOpsFileRegModelToEntity(
      OpsFileRegModel opsFileRegModel) {

    MdtOpsFileRegEntity mdtOpsFileRegEntity = new MdtOpsFileRegEntity();

    mdtOpsFileRegEntity.setFileName(opsFileRegModel.getFileName());
    mdtOpsFileRegEntity.setFilepath(opsFileRegModel.getFilepath());
    mdtOpsFileRegEntity.setNameSpace(opsFileRegModel.getNameSpace());
    mdtOpsFileRegEntity.setProcessDate(opsFileRegModel.getProcessDate());
    mdtOpsFileRegEntity.setReason(opsFileRegModel.getReason());
    mdtOpsFileRegEntity.setStatus(opsFileRegModel.getStatus());
    mdtOpsFileRegEntity.setGrpHdrMsgId(opsFileRegModel.getGrpHdrMsgId());
    mdtOpsFileRegEntity.setExtractMsgId(opsFileRegModel.getExtractMsgId());
    mdtOpsFileRegEntity.setInOutInd(opsFileRegModel.getInOutInd());
    mdtOpsFileRegEntity.setOnlineInd(opsFileRegModel.getOnlineInd());


    return mdtOpsFileRegEntity;
  }

  public OpsFileRegModel translateCommonsToOpsFileRegEntity(
      MdtOpsFileRegEntity mdtOpsFileRegEntity) {
    OpsFileRegModel opsFileRegModel = new OpsFileRegModel();

    opsFileRegModel.setFileName(mdtOpsFileRegEntity.getFileName());
    opsFileRegModel.setFilepath(mdtOpsFileRegEntity.getFilepath());
    opsFileRegModel.setNameSpace(mdtOpsFileRegEntity.getNameSpace());
    opsFileRegModel.setProcessDate(mdtOpsFileRegEntity.getProcessDate());
    opsFileRegModel.setReason(mdtOpsFileRegEntity.getReason());
    opsFileRegModel.setStatus(mdtOpsFileRegEntity.getStatus());
    opsFileRegModel.setGrpHdrMsgId(mdtOpsFileRegEntity.getGrpHdrMsgId());
    opsFileRegModel.setExtractMsgId(mdtOpsFileRegEntity.getExtractMsgId());
    opsFileRegModel.setInOutInd(mdtOpsFileRegEntity.getInOutInd());
    opsFileRegModel.setOnlineInd(mdtOpsFileRegEntity.getOnlineInd());

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
      MdtOpsCustParamEntity localEntity) {

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
    opsCustParaModel.setMdtReqIdReuseInd(localEntity.getMdtReqIdReuseInd());
    opsCustParaModel.setManInitLastFileNr(localEntity.getManInitLastFileNr());
    opsCustParaModel.setManInitLstSeq(localEntity.getManInitLstSeq());
    opsCustParaModel.setManInitXsdNs(localEntity.getManInitXsdNs());
    opsCustParaModel.setMdtReqIdReuseInd(localEntity.getMdtReqIdReuseInd());
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

  public MdtOpsCustParamEntity translateOpsCustParamToEntity(OpsCustParamModel opsCustParaModel) {
    MdtOpsCustParamEntity mdtOpsCustParamEntity = new MdtOpsCustParamEntity();


    mdtOpsCustParamEntity.setActiveInd(opsCustParaModel.getActiveInd());
    mdtOpsCustParamEntity.setManAccpLstSeq(opsCustParaModel.getManAccpLstSeq());
    mdtOpsCustParamEntity.setInstId(opsCustParaModel.getInstId());
    mdtOpsCustParamEntity.setCreatedBy(opsCustParaModel.getCreatedBy());
    mdtOpsCustParamEntity.setCreatedDate(opsCustParaModel.getCreatedDate());
    mdtOpsCustParamEntity.setManAccpLastFileNr(opsCustParaModel.getManAccpLastFileNr());
    mdtOpsCustParamEntity.setManAccpLastFileNr(opsCustParaModel.getManAccpLastFileNr());
    mdtOpsCustParamEntity.setManAccpXsdNs(opsCustParaModel.getManAccpXsdNs());
    mdtOpsCustParamEntity.setManAmdLastFileNr(opsCustParaModel.getManAmdLastFileNr());
    mdtOpsCustParamEntity.setManAmdLstSeq(opsCustParaModel.getManAmdLstSeq());
    mdtOpsCustParamEntity.setManAmdXsdNs(opsCustParaModel.getManAmdXsdNs());
    mdtOpsCustParamEntity.setManCanLastFileNr(opsCustParaModel.getManCanLastFileNr());
    mdtOpsCustParamEntity.setManCanLstSeq(opsCustParaModel.getManCanLstSeq());
    mdtOpsCustParamEntity.setManCanXsdNs(opsCustParaModel.getManCanXsdNs());
    mdtOpsCustParamEntity.setMdtReqIdReuseInd(opsCustParaModel.getMdtReqIdReuseInd());
    mdtOpsCustParamEntity.setManInitLastFileNr(opsCustParaModel.getManInitLastFileNr());
    mdtOpsCustParamEntity.setManInitLstSeq(opsCustParaModel.getManInitLstSeq());
    mdtOpsCustParamEntity.setManInitXsdNs(opsCustParaModel.getManInitXsdNs());
    mdtOpsCustParamEntity.setMdtReqIdReuseInd(opsCustParaModel.getMdtReqIdReuseInd());
    mdtOpsCustParamEntity.setManStatusRepLastFileNr(opsCustParaModel.getManStatusRepLastFileNr());
    mdtOpsCustParamEntity.setManStatusRepLstSeq(opsCustParaModel.getManStatusRepLstSeq());
    mdtOpsCustParamEntity.setManStatusRepXsdNs(opsCustParaModel.getManStatusRepXsdNs());
    mdtOpsCustParamEntity.setManConfirmXsdNs(opsCustParaModel.getManConfirmXsdNs());
    mdtOpsCustParamEntity.setManConfirmLstSeq(opsCustParaModel.getManConfirmLstSeq());
    mdtOpsCustParamEntity.setManConfirmLstFileNr(opsCustParaModel.getManConfirmLstFileNr());
    mdtOpsCustParamEntity.setManReqXsdNs(opsCustParaModel.getManReqXsdNs());
    mdtOpsCustParamEntity.setManReqLastFileNr(opsCustParaModel.getManReqLastFileNr());
    mdtOpsCustParamEntity.setManReqLstSeq(opsCustParaModel.getManReqLstSeq());
    mdtOpsCustParamEntity.setManRespXsdNs(opsCustParaModel.getManRespXsdNs());
    mdtOpsCustParamEntity.setManRespLstSeq(opsCustParaModel.getManRespLstSeq());
    mdtOpsCustParamEntity.setManRespLastFileNr(opsCustParaModel.getManRespLastFileNr());

    return mdtOpsCustParamEntity;
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
      MdtCnfgReportNamesEntity mdtCnfgReportNamesEntity) {
    ReportsNamesModel reportsNamesModel = new ReportsNamesModel();

    reportsNamesModel.setActiveInd(mdtCnfgReportNamesEntity.getActiveInd());
    reportsNamesModel.setReportName(mdtCnfgReportNamesEntity.getReportName());
    reportsNamesModel.setReportNr(mdtCnfgReportNamesEntity.getReportNr());
    reportsNamesModel.setInternalInd(mdtCnfgReportNamesEntity.getInternalInd());
    reportsNamesModel.setCreatedBy(mdtCnfgReportNamesEntity.getCreatedBy());
    reportsNamesModel.setCreatedDate(mdtCnfgReportNamesEntity.getCreatedDate());
    reportsNamesModel.setModifiedBy(mdtCnfgReportNamesEntity.getModifiedBy());
    reportsNamesModel.setModifiedDate(mdtCnfgReportNamesEntity.getModifiedDate());

    return reportsNamesModel;
  }

  public MdtCnfgReportNamesEntity translateCommnsReportNamesModelToEntity(
      ReportsNamesModel reportsNamesModel) {
    MdtCnfgReportNamesEntity mdtCnfgReportNamesEntity = new MdtCnfgReportNamesEntity();

    mdtCnfgReportNamesEntity.setActiveInd(reportsNamesModel.getActiveInd());
    mdtCnfgReportNamesEntity.setReportName(reportsNamesModel.getReportName());
    mdtCnfgReportNamesEntity.setReportNr(reportsNamesModel.getReportNr());
    mdtCnfgReportNamesEntity.setInternalInd(reportsNamesModel.getInternalInd());
    mdtCnfgReportNamesEntity.setCreatedBy(reportsNamesModel.getCreatedBy());
    mdtCnfgReportNamesEntity.setCreatedDate(reportsNamesModel.getCreatedDate());
    mdtCnfgReportNamesEntity.setModifiedBy(reportsNamesModel.getModifiedBy());
    mdtCnfgReportNamesEntity.setModifiedDate(reportsNamesModel.getModifiedDate());

    return mdtCnfgReportNamesEntity;
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

  public MdtCnfgRejectReasonCodesEntity transalateCommonsRejectReasonCodesModelToEntity(
      CnfgRejectReasonCodesModel rejectReasonCodesModel) {
    MdtCnfgRejectReasonCodesEntity mdtCnfgRejectReasonCodesEntity =
        new MdtCnfgRejectReasonCodesEntity();
    mdtCnfgRejectReasonCodesEntity.setRejectReasonCode(
        rejectReasonCodesModel.getRejectReasonCode());
    mdtCnfgRejectReasonCodesEntity.setRejectReasonDesc(
        rejectReasonCodesModel.getRejectReasonDesc());
    mdtCnfgRejectReasonCodesEntity.setActiveInd(rejectReasonCodesModel.getActiveInd());
    mdtCnfgRejectReasonCodesEntity.setCreatedBy(rejectReasonCodesModel.getCreatedBy());
    mdtCnfgRejectReasonCodesEntity.setCreatedDate(rejectReasonCodesModel.getCreatedDate());
    mdtCnfgRejectReasonCodesEntity.setModifiedBy(rejectReasonCodesModel.getModifiedBy());
    mdtCnfgRejectReasonCodesEntity.setModifiedDate(rejectReasonCodesModel.getModifiedDate());

    return mdtCnfgRejectReasonCodesEntity;
  }


  public CnfgRejectReasonCodesModel transalateCnfgRejectReasonCodesEntityToCommonsRejectReasonModel(
      MdtCnfgRejectReasonCodesEntity mdtCnfgRejectReasonCodesEntity) {
    CnfgRejectReasonCodesModel rejectReasonCodesModel = new CnfgRejectReasonCodesModel();

    rejectReasonCodesModel.setRejectReasonCode(
        mdtCnfgRejectReasonCodesEntity.getRejectReasonCode());
    rejectReasonCodesModel.setRejectReasonDesc(
        mdtCnfgRejectReasonCodesEntity.getRejectReasonDesc());
    rejectReasonCodesModel.setActiveInd(mdtCnfgRejectReasonCodesEntity.getActiveInd());
    rejectReasonCodesModel.setCreatedBy(mdtCnfgRejectReasonCodesEntity.getCreatedBy());
    rejectReasonCodesModel.setCreatedDate(mdtCnfgRejectReasonCodesEntity.getCreatedDate());
    rejectReasonCodesModel.setModifiedBy(mdtCnfgRejectReasonCodesEntity.getModifiedBy());
    rejectReasonCodesModel.setModifiedDate(mdtCnfgRejectReasonCodesEntity.getModifiedDate());

    return rejectReasonCodesModel;

  }

  public CnfgAuthTypeModel translateCnfgAuthTypeEntityToCommonsModel(
      MdtCnfgAuthTypeEntity mdtCnfgAuthTypeEntity) {
    CnfgAuthTypeModel authTypeModel = new CnfgAuthTypeModel();

    authTypeModel.setActiveInd(mdtCnfgAuthTypeEntity.getActiveInd());
    authTypeModel.setAuthType(mdtCnfgAuthTypeEntity.getAuthType());
    authTypeModel.setAuthTypeDescription(mdtCnfgAuthTypeEntity.getAuthTypeDescription());

    return authTypeModel;
  }

  public MdtCnfgAuthTypeEntity translateCommnsAuthtypeModelToEntity(
      CnfgAuthTypeModel CnfgAuthTypeModel) {
    MdtCnfgAuthTypeEntity mdtCnfgAuthTypeEntity = new MdtCnfgAuthTypeEntity();

    mdtCnfgAuthTypeEntity.setActiveInd(CnfgAuthTypeModel.getActiveInd());
    mdtCnfgAuthTypeEntity.setAuthType(CnfgAuthTypeModel.getAuthType());
    mdtCnfgAuthTypeEntity.setAuthTypeDescription(CnfgAuthTypeModel.getAuthTypeDescription());
    return mdtCnfgAuthTypeEntity;
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


  public MdtOpsServicesEntity transalateCommonsServicesModelToEntity(ServicesModel servicesModel) {

    MdtOpsServicesEntity servicesEntity = new MdtOpsServicesEntity();

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
      MdtOpsServicesEntity mdtOpsServicesEntity) {

    ServicesModel servicesModel = new ServicesModel();

    servicesModel.setCreatedBy(mdtOpsServicesEntity.getCreatedBy());
    servicesModel.setCreatedDate(mdtOpsServicesEntity.getCreatedDate());
    servicesModel.setModifiedBy(mdtOpsServicesEntity.getModifiedBy());
    servicesModel.setModifiedDate(mdtOpsServicesEntity.getModifiedDate());
    servicesModel.setRecordId(mdtOpsServicesEntity.getRecordId());
    servicesModel.setServiceIdIn(mdtOpsServicesEntity.getServiceIdIn());
    servicesModel.setServiceIdInDesc(mdtOpsServicesEntity.getServiceIdInDesc());
    servicesModel.setServiceIdOut(mdtOpsServicesEntity.getServiceIdOut());
    servicesModel.setServiceIdOutDesc(mdtOpsServicesEntity.getServiceIdOutDesc());
    servicesModel.setProcessDate(mdtOpsServicesEntity.getProcessDate());
    servicesModel.setProcessStatus(mdtOpsServicesEntity.getProcessStatus());
    servicesModel.setServiceIdOutSlaTime(mdtOpsServicesEntity.getServiceIdOutSlaTime());
    servicesModel.setActiveInd(mdtOpsServicesEntity.getActiveInd());
    servicesModel.setMsgTypeId(mdtOpsServicesEntity.getMsgTypeId());

    return servicesModel;
  }

  public MdtAcOpsStatusHdrsEntity translateCommonsOpsStatusHdrsModelToEntity(
      OpsStatusHdrsModel opsStatusHdrsModel) {
    MdtAcOpsStatusHdrsEntity opsStatusHdrsEntity = new MdtAcOpsStatusHdrsEntity();

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
      MdtAcOpsStatusHdrsEntity opsStatusHdrsEntity) {

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

  public MdtAcOpsStatusDetailsEntity translateCommonsOpsStatusDetailsModelToEntity(
      OpsStatusDetailsModel opsStatusDetailsModel) {
    MdtAcOpsStatusDetailsEntity opsStatusDetailsEntity = new MdtAcOpsStatusDetailsEntity();

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
      MdtAcOpsStatusDetailsEntity opsStatusDetailsEntity) {
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

  public MdtAcOpsMndtCountEntity translateCommonsMdtModelCountToEntity(
      MandatesCountCommonsModel mandateCountModel) {

    MdtAcOpsMndtCountEntity mdtOpsMndtCountEntity = new MdtAcOpsMndtCountEntity();
    MdtAcOpsMndtCountPK mdtOpsMndtCountPK = new MdtAcOpsMndtCountPK();
    mdtOpsMndtCountPK.setInstId(mandateCountModel.getInstId());
    mdtOpsMndtCountPK.setMsgId(mandateCountModel.getMsgId());
    mdtOpsMndtCountPK.setServiceId(mandateCountModel.getServiceId());
    mdtOpsMndtCountEntity.setMdtAcOpsMndtCountPK(mdtOpsMndtCountPK);
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


  public MandatesCountCommonsModel translateMdtOpsMndtCountEntityToCommonsModel(
      MdtAcOpsMndtCountEntity mdtOpsMndtCountEntity) {

    MandatesCountCommonsModel mandatesCountCommonsModel = new MandatesCountCommonsModel();

    mandatesCountCommonsModel.setFileName(mdtOpsMndtCountEntity.getFileName());
    mandatesCountCommonsModel.setProcessDate(mdtOpsMndtCountEntity.getProcessDate());
    mandatesCountCommonsModel.setInstId(mdtOpsMndtCountEntity.getMdtAcOpsMndtCountPK().getInstId());
    mandatesCountCommonsModel.setServiceId(
        mdtOpsMndtCountEntity.getMdtAcOpsMndtCountPK().getServiceId());
    mandatesCountCommonsModel.setMsgId(mdtOpsMndtCountEntity.getMdtAcOpsMndtCountPK().getMsgId());
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
      MdtCnfgAdjustmentCatEntity localEntity) {

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

  public MdtCnfgAdjustmentCatEntity translateCommnsAdjustmentCategoryModelToEntity(
      AdjustmentCategoryModel adjustmentCategoryModel) {

    MdtCnfgAdjustmentCatEntity mdtCnfgAdjustmentCatEntity = new MdtCnfgAdjustmentCatEntity();

    mdtCnfgAdjustmentCatEntity.setAdjustmentCategory(
        adjustmentCategoryModel.getAdjustmentCategory());
    mdtCnfgAdjustmentCatEntity.setAdjustmentCategoryDesc(
        adjustmentCategoryModel.getAdjustmentCategoryDesc());
    mdtCnfgAdjustmentCatEntity.setActiveInd(adjustmentCategoryModel.getActiveInd());
    mdtCnfgAdjustmentCatEntity.setCreatedBy(adjustmentCategoryModel.getCreatedBy());
    mdtCnfgAdjustmentCatEntity.setCreatedDate(adjustmentCategoryModel.getCreatedDate());
    mdtCnfgAdjustmentCatEntity.setModifiedBy(adjustmentCategoryModel.getModifiedBy());
    mdtCnfgAdjustmentCatEntity.setModifiedDate(adjustmentCategoryModel.getModifiedDate());

    return mdtCnfgAdjustmentCatEntity;
  }

  public MdtCnfgAccountTypeEntity translateCommnsAccountTypeModelToEntity(
      AccountTypeModel accountTypeModel) {

    MdtCnfgAccountTypeEntity mdtCnfgAccountTypeEntity = new MdtCnfgAccountTypeEntity();

    mdtCnfgAccountTypeEntity.setAccountTypeCode(accountTypeModel.getAccountTypeCode());
    mdtCnfgAccountTypeEntity.setAccountTypeDescription(
        accountTypeModel.getAccountTypeDescription());
    mdtCnfgAccountTypeEntity.setActiveInd(accountTypeModel.getActiveInd());
    mdtCnfgAccountTypeEntity.setCreatedBy(accountTypeModel.getCreatedBy());
    mdtCnfgAccountTypeEntity.setCreatedDate(accountTypeModel.getCreatedDate());
    mdtCnfgAccountTypeEntity.setModifiedBy(accountTypeModel.getModifiedBy());
    mdtCnfgAccountTypeEntity.setModifiedDate(accountTypeModel.getModifiedDate());

    return mdtCnfgAccountTypeEntity;


  }

  public AccountTypeModel translateMdtCnfgAccountTypeEntityToCommonsModel(
      MdtCnfgAccountTypeEntity localEntity) {

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
      MdtCnfgSeverityCodesEntity localEntity) {
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

  public MdtCnfgSeverityCodesEntity translateCommnsSeverityCodesModelToEntity(
      SeverityCodesModel severityCodesModel) {

    MdtCnfgSeverityCodesEntity mdtCnfgSeverityCodesEntity = new MdtCnfgSeverityCodesEntity();


    mdtCnfgSeverityCodesEntity.setSeverityCode(severityCodesModel.getSeverityCode());
    mdtCnfgSeverityCodesEntity.setSeverityCodeDesc(severityCodesModel.getSeverityCodeDesc());
    mdtCnfgSeverityCodesEntity.setCreatedBy(severityCodesModel.getCreatedBy());
    mdtCnfgSeverityCodesEntity.setCreatedDate(severityCodesModel.getCreatedDate());
    mdtCnfgSeverityCodesEntity.setModifiedBy(severityCodesModel.getModifiedBy());
    mdtCnfgSeverityCodesEntity.setModifiedDate(severityCodesModel.getModifiedDate());
    mdtCnfgSeverityCodesEntity.setActiveInd(severityCodesModel.getActiveInd());


    return mdtCnfgSeverityCodesEntity;
  }

  public StatusReasonCodesModel translateMdtCnfgStatusReasonCodesEntityToCommonsModel(
      MdtCnfgStatusReasonCodesEntity localEntity) {

    StatusReasonCodesModel statusReasonCodesModel = new StatusReasonCodesModel();

    statusReasonCodesModel.setStatusReasonCode(localEntity.getStatusReasonCode());
    statusReasonCodesModel.setStatusReasonCodeDescription(
        localEntity.getStatusReasonCodeDescription());
    statusReasonCodesModel.setActiveInd(localEntity.getActiveInd());
    statusReasonCodesModel.setCreatedBy(localEntity.getCreatedBy());
    statusReasonCodesModel.setCreatedDate(localEntity.getCreatedDate());
    statusReasonCodesModel.setModifiedBy(localEntity.getModifiedBy());
    statusReasonCodesModel.setModifiedDate(localEntity.getModifiedDate());

    return statusReasonCodesModel;
  }

  public MdtCnfgStatusReasonCodesEntity translateCommnsStatusReasonCodesModelToEntity(
      StatusReasonCodesModel statusReasonCodesModel) {

    MdtCnfgStatusReasonCodesEntity mdtCnfgStatusReasonCodesEntity =
        new MdtCnfgStatusReasonCodesEntity();

    mdtCnfgStatusReasonCodesEntity.setStatusReasonCode(
        statusReasonCodesModel.getStatusReasonCode());
    mdtCnfgStatusReasonCodesEntity.setStatusReasonCodeDescription(
        statusReasonCodesModel.getStatusReasonCodeDescription());
    mdtCnfgStatusReasonCodesEntity.setActiveInd(statusReasonCodesModel.getActiveInd());
    mdtCnfgStatusReasonCodesEntity.setCreatedBy(statusReasonCodesModel.getCreatedBy());
    mdtCnfgStatusReasonCodesEntity.setCreatedDate(statusReasonCodesModel.getCreatedDate());
    mdtCnfgStatusReasonCodesEntity.setModifiedBy(statusReasonCodesModel.getModifiedBy());
    mdtCnfgStatusReasonCodesEntity.setModifiedDate(statusReasonCodesModel.getModifiedDate());

    return mdtCnfgStatusReasonCodesEntity;
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


  public MdtOpsProcessControlsEntity translateCommonsMdtOpsProcessControlsModelToEntity(
      OpsProcessControlCommonsModel opsProcessControlCommonsModel) {

    MdtOpsProcessControlsEntity mdtOpsProcessControlsEntity = new MdtOpsProcessControlsEntity();
    mdtOpsProcessControlsEntity.setActiveInd(opsProcessControlCommonsModel.getActiveInd());
    mdtOpsProcessControlsEntity.setInstId(opsProcessControlCommonsModel.getInstId());
    mdtOpsProcessControlsEntity.setMaxNrRecords(opsProcessControlCommonsModel.getMaxNrRecords());
    mdtOpsProcessControlsEntity.setNrOfDaysProc(opsProcessControlCommonsModel.getNrOfDaysProc());
    mdtOpsProcessControlsEntity.setProcessDate(opsProcessControlCommonsModel.getProcessDate());
    mdtOpsProcessControlsEntity.setPubHolProc(opsProcessControlCommonsModel.getPubHolProc());

    return mdtOpsProcessControlsEntity;

  }

  public OpsProcessControlCommonsModel translateMdtOpsProcessControlsEntityToModel(
      MdtOpsProcessControlsEntity mdtOpsProcessControlsEntity) {

    OpsProcessControlCommonsModel opsProcessControlCommonsModel =
        new OpsProcessControlCommonsModel();
    opsProcessControlCommonsModel.setActiveInd(mdtOpsProcessControlsEntity.getActiveInd());
    opsProcessControlCommonsModel.setInstId(mdtOpsProcessControlsEntity.getInstId());
    opsProcessControlCommonsModel.setMaxNrRecords(mdtOpsProcessControlsEntity.getMaxNrRecords());
    opsProcessControlCommonsModel.setNrOfDaysProc(mdtOpsProcessControlsEntity.getNrOfDaysProc());
    opsProcessControlCommonsModel.setProcessDate(mdtOpsProcessControlsEntity.getProcessDate());
    opsProcessControlCommonsModel.setPubHolProc(mdtOpsProcessControlsEntity.getPubHolProc());

    return opsProcessControlCommonsModel;


  }


  public MdtOpsRefSeqNrEntity translateMdtOpsRefSeqNrEntityToComonsModel(
      OpsRefSeqNumberCommonsModel opsRefSeqNumberCommonsModel) {
    MdtOpsRefSeqNrEntity mdtOpsRefSeqNrEntity = new MdtOpsRefSeqNrEntity();
    MdtOpsRefSeqNrPK mdtOpsRefSeqNrPK = new MdtOpsRefSeqNrPK();

    mdtOpsRefSeqNrEntity.setCreatedBy(opsRefSeqNumberCommonsModel.getCreatedBy());
    mdtOpsRefSeqNrEntity.setCreatedDate(opsRefSeqNumberCommonsModel.getCreatedDate());
    mdtOpsRefSeqNrEntity.setLastFileNr(opsRefSeqNumberCommonsModel.getLastFileNr());
    mdtOpsRefSeqNrEntity.setLastSeqNr(opsRefSeqNumberCommonsModel.getLastSeqNr());
    mdtOpsRefSeqNrEntity.setModifiedBy(opsRefSeqNumberCommonsModel.getModifiedBy());
    mdtOpsRefSeqNrEntity.setModifiedDate(opsRefSeqNumberCommonsModel.getModifiedDate());
    mdtOpsRefSeqNrPK.setMemberNo(opsRefSeqNumberCommonsModel.getMemberNo());
    mdtOpsRefSeqNrPK.setServiceId(opsRefSeqNumberCommonsModel.getServiceId());
    mdtOpsRefSeqNrEntity.setMdtOpsRefSeqNrPK(mdtOpsRefSeqNrPK);


    return mdtOpsRefSeqNrEntity;

  }

  public OpsRefSeqNumberCommonsModel translateOpsRefSeqNumberCommonsModelToEntity(
      MdtOpsRefSeqNrEntity mdtOpsRefSeqNrEntity) {
    MdtOpsRefSeqNrPK mdtOpsRefSeqNrPK = new MdtOpsRefSeqNrPK();
    OpsRefSeqNumberCommonsModel opsRefSeqNumberCommonsModel = new OpsRefSeqNumberCommonsModel();
    opsRefSeqNumberCommonsModel.setCreatedBy(mdtOpsRefSeqNrEntity.getCreatedBy());
    opsRefSeqNumberCommonsModel.setCreatedDate(mdtOpsRefSeqNrEntity.getCreatedDate());
    opsRefSeqNumberCommonsModel.setLastFileNr(mdtOpsRefSeqNrEntity.getLastFileNr());
    opsRefSeqNumberCommonsModel.setLastSeqNr(mdtOpsRefSeqNrEntity.getLastSeqNr());
    opsRefSeqNumberCommonsModel.setMemberNo(
        mdtOpsRefSeqNrEntity.getMdtOpsRefSeqNrPK().getMemberNo());
    opsRefSeqNumberCommonsModel.setModifiedBy(mdtOpsRefSeqNrEntity.getModifiedBy());
    opsRefSeqNumberCommonsModel.setModifiedDate(mdtOpsRefSeqNrEntity.getModifiedDate());
    opsRefSeqNumberCommonsModel.setServiceId(
        mdtOpsRefSeqNrEntity.getMdtOpsRefSeqNrPK().getServiceId());
    mdtOpsRefSeqNrEntity.setMdtOpsRefSeqNrPK(mdtOpsRefSeqNrPK);
    return opsRefSeqNumberCommonsModel;


  }

  public MdtAcOpsSotEotCtrlEntity translateMdtAcOpsSotEotCtrlEntityToModel(
      AcOpsSotEotCntrlModel acOpsSotEotCntrlModel) {
    MdtAcOpsSotEotCtrlEntity mdtAcOpsSotEotCtrlEntity = new MdtAcOpsSotEotCtrlEntity();

    MdtAcOpsSotEotCtrlPK mdtAcOpsSotEotCtrlPK = new MdtAcOpsSotEotCtrlPK();
    mdtAcOpsSotEotCtrlPK.setInstId(acOpsSotEotCntrlModel.getInstId());
    mdtAcOpsSotEotCtrlPK.setServiceId(acOpsSotEotCntrlModel.getServiceId());

    mdtAcOpsSotEotCtrlEntity.setMdtAcOpsSotEotCtrlPK(mdtAcOpsSotEotCtrlPK);
    mdtAcOpsSotEotCtrlEntity.setEotIn(acOpsSotEotCntrlModel.getEotIn());
    mdtAcOpsSotEotCtrlEntity.setEotOut(acOpsSotEotCntrlModel.getEotOut());
    mdtAcOpsSotEotCtrlEntity.setSotIn(acOpsSotEotCntrlModel.getSotIn());
    mdtAcOpsSotEotCtrlEntity.setSotOut(acOpsSotEotCntrlModel.getSotOut());


    return mdtAcOpsSotEotCtrlEntity;

  }


  public AcOpsSotEotCntrlModel translateAcOpsSotEotCntrlModelToEntity(
      MdtAcOpsSotEotCtrlEntity mdtAcOpsSotEotCtrlEntity) {
    AcOpsSotEotCntrlModel acOpsSotEotCntrlModel = new AcOpsSotEotCntrlModel();
    MdtAcOpsSotEotCtrlPK mdtAcOpsSotEotCtrlPK = new MdtAcOpsSotEotCtrlPK();
    acOpsSotEotCntrlModel.setEotIn(mdtAcOpsSotEotCtrlEntity.getEotIn());
    acOpsSotEotCntrlModel.setEotOut(mdtAcOpsSotEotCtrlEntity.getEotOut());
    acOpsSotEotCntrlModel.setInstId(mdtAcOpsSotEotCtrlEntity.getMdtAcOpsSotEotCtrlPK().getInstId());
    acOpsSotEotCntrlModel.setServiceId(
        mdtAcOpsSotEotCtrlEntity.getMdtAcOpsSotEotCtrlPK().getServiceId());
    acOpsSotEotCntrlModel.setSotIn(mdtAcOpsSotEotCtrlEntity.getSotIn());
    acOpsSotEotCntrlModel.setSotOut(mdtAcOpsSotEotCtrlEntity.getSotOut());
    mdtAcOpsSotEotCtrlEntity.setMdtAcOpsSotEotCtrlPK(mdtAcOpsSotEotCtrlPK);

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
      MdtAcOpsSchedulerEntity mdtAcOpsSchedulerEntity) {
    SchedulerCommonsModel schedulerCommonsModel = new SchedulerCommonsModel();
    schedulerCommonsModel.setActiveInd(mdtAcOpsSchedulerEntity.getActiveInd());
    schedulerCommonsModel.setRescheduleTime(mdtAcOpsSchedulerEntity.getRescheduleTime());
    schedulerCommonsModel.setSchedulerKey(mdtAcOpsSchedulerEntity.getSchedulerKey());
    schedulerCommonsModel.setSchedulerName(mdtAcOpsSchedulerEntity.getSchedulerName());

    return schedulerCommonsModel;
  }

  public MdtAcOpsProcessControlsEntity translateOpsProcessControlModelToEntity(
      OpsProcessControlModel opsProcessControlModel) {
    MdtAcOpsProcessControlsEntity mdtAcOpsProcessControlsEntity =
        new MdtAcOpsProcessControlsEntity();

    mdtAcOpsProcessControlsEntity.setCisDownloadInd(opsProcessControlModel.getCisDownloadInd());
    mdtAcOpsProcessControlsEntity.setProcessDate(opsProcessControlModel.getProcessDate());

    return mdtAcOpsProcessControlsEntity;
  }

  public OpsProcessControlModel translateMdtAcOpsProcessControlsEntityToCommonsModel(
      MdtAcOpsProcessControlsEntity mdtAcOpsProcessControlsEntity) {
    OpsProcessControlModel opsProcessControlModel = new OpsProcessControlModel();

    opsProcessControlModel.setCisDownloadInd(mdtAcOpsProcessControlsEntity.getCisDownloadInd());
    opsProcessControlModel.setProcessDate(mdtAcOpsProcessControlsEntity.getProcessDate());

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


  public MdtOpsSlaTimesEntity translateOpsSlaTimesCommonsModelToEntity(
      OpsSlaTimesCommonsModel opsSlaTimesCommonsModel) {
    MdtOpsSlaTimesEntity mdtOpsSlaTimesEntity = new MdtOpsSlaTimesEntity();
    mdtOpsSlaTimesEntity.setEndTime(opsSlaTimesCommonsModel.getEndTime());
    mdtOpsSlaTimesEntity.setService(opsSlaTimesCommonsModel.getService());
    mdtOpsSlaTimesEntity.setStartTime(opsSlaTimesCommonsModel.getStartTime());

    return mdtOpsSlaTimesEntity;
  }


  public OpsSlaTimesCommonsModel translateMdtOpsSlaTimesEntityToCommonsModel(
      MdtOpsSlaTimesEntity mdtOpsSlaTimesEntity) {

    OpsSlaTimesCommonsModel opsSlaTimesCommonsModel = new OpsSlaTimesCommonsModel();

    opsSlaTimesCommonsModel.setEndTime(mdtOpsSlaTimesEntity.getEndTime());
    opsSlaTimesCommonsModel.setService(mdtOpsSlaTimesEntity.getService());
    opsSlaTimesCommonsModel.setStartTime(mdtOpsSlaTimesEntity.getStartTime());

    return opsSlaTimesCommonsModel;
  }


  public MdtAcOpsTransCtrlMsgEntity translateTransCtrlMsgModelToEntity(
      TransCtrlMsgModel transCtrlMsgModel) {
    MdtAcOpsTransCtrlMsgEntity mdtAcOpsTransCtrlMsgEntity = new MdtAcOpsTransCtrlMsgEntity();

    mdtAcOpsTransCtrlMsgEntity.setActiveInd(transCtrlMsgModel.getActiveInd());
    mdtAcOpsTransCtrlMsgEntity.setCtrlMsgId(transCtrlMsgModel.getCtrlMsgId());
    mdtAcOpsTransCtrlMsgEntity.setMemberIdFm(transCtrlMsgModel.getMemberIdFm());
    mdtAcOpsTransCtrlMsgEntity.setMemberIdTo(transCtrlMsgModel.getMemberIdTo());
    mdtAcOpsTransCtrlMsgEntity.setMsgType(transCtrlMsgModel.getMsgType());
    mdtAcOpsTransCtrlMsgEntity.setNrOfFiles(transCtrlMsgModel.getNrOfFiles());
    mdtAcOpsTransCtrlMsgEntity.setNrOfFilesReceived(transCtrlMsgModel.getNrOfFilesReceived());
    mdtAcOpsTransCtrlMsgEntity.setNrOfRecords(transCtrlMsgModel.getNrOfRecords());
    mdtAcOpsTransCtrlMsgEntity.setNrOfRecordsReceived(transCtrlMsgModel.getNrOfRecordsReceived());
    mdtAcOpsTransCtrlMsgEntity.setProcessDate(transCtrlMsgModel.getProcessDate());
    mdtAcOpsTransCtrlMsgEntity.setServiceId(transCtrlMsgModel.getServiceId());
    mdtAcOpsTransCtrlMsgEntity.setSystemStatus(transCtrlMsgModel.getSystemStatus());
    mdtAcOpsTransCtrlMsgEntity.setValidRecordsReceived(transCtrlMsgModel.getValidRecordsReceived());
    mdtAcOpsTransCtrlMsgEntity.setValueOfRecords(transCtrlMsgModel.getValueOfRecords());
    mdtAcOpsTransCtrlMsgEntity.setValueOfRecordsCurr(transCtrlMsgModel.getValueOfRecordsCurr());

    return mdtAcOpsTransCtrlMsgEntity;


  }


  public TransCtrlMsgModel translateMdtAcOpsTransCtrlMsgEntityToCommonsModel(
      MdtAcOpsTransCtrlMsgEntity mdtAcOpsTransCtrlMsgEntity) {
    TransCtrlMsgModel transCtrlMsgModel = new TransCtrlMsgModel();

    transCtrlMsgModel.setActiveInd(mdtAcOpsTransCtrlMsgEntity.getActiveInd());
    transCtrlMsgModel.setCtrlMsgId(mdtAcOpsTransCtrlMsgEntity.getCtrlMsgId());
    transCtrlMsgModel.setMemberIdFm(mdtAcOpsTransCtrlMsgEntity.getMemberIdFm());
    transCtrlMsgModel.setMemberIdTo(mdtAcOpsTransCtrlMsgEntity.getMemberIdTo());
    transCtrlMsgModel.setMsgType(mdtAcOpsTransCtrlMsgEntity.getMsgType());
    transCtrlMsgModel.setNrOfFiles(mdtAcOpsTransCtrlMsgEntity.getNrOfFiles());
    transCtrlMsgModel.setNrOfFilesReceived(mdtAcOpsTransCtrlMsgEntity.getNrOfFilesReceived());
    transCtrlMsgModel.setNrOfRecords(mdtAcOpsTransCtrlMsgEntity.getNrOfRecords());
    transCtrlMsgModel.setNrOfRecordsReceived(mdtAcOpsTransCtrlMsgEntity.getNrOfRecordsReceived());
    transCtrlMsgModel.setProcessDate(mdtAcOpsTransCtrlMsgEntity.getProcessDate());
    transCtrlMsgModel.setServiceId(mdtAcOpsTransCtrlMsgEntity.getServiceId());
    transCtrlMsgModel.setSystemStatus(mdtAcOpsTransCtrlMsgEntity.getSystemStatus());
    transCtrlMsgModel.setValidRecordsReceived(mdtAcOpsTransCtrlMsgEntity.getValidRecordsReceived());
    transCtrlMsgModel.setValueOfRecords(mdtAcOpsTransCtrlMsgEntity.getValueOfRecords());
    transCtrlMsgModel.setValueOfRecordsCurr(mdtAcOpsTransCtrlMsgEntity.getValueOfRecordsCurr());

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


  public MdtCnfgAmendmentCodesEntity translateCommonsAmendmentCodesModelToEntity(
      AmendmentCodesModel amendmentCodesModel) {

    MdtCnfgAmendmentCodesEntity mdtCnfgAmendmentCodesEntity = new MdtCnfgAmendmentCodesEntity();

    mdtCnfgAmendmentCodesEntity.setAmendmentCodes(amendmentCodesModel.getAmendmentCodes());
    mdtCnfgAmendmentCodesEntity.setAmendmentCodesDescription(
        amendmentCodesModel.getAmendmentCodesDescription());
    mdtCnfgAmendmentCodesEntity.setActiveInd(amendmentCodesModel.getActiveInd());
    mdtCnfgAmendmentCodesEntity.setCreatedBy(amendmentCodesModel.getCreatedBy());
    mdtCnfgAmendmentCodesEntity.setCreatedDate(amendmentCodesModel.getCreatedDate());
    mdtCnfgAmendmentCodesEntity.setModifiedBy(amendmentCodesModel.getModifiedBy());
    mdtCnfgAmendmentCodesEntity.setModifiedDate(amendmentCodesModel.getModifiedDate());

    return mdtCnfgAmendmentCodesEntity;


  }

  public AmendmentCodesModel translateMdtCnfgAmendmentCodesEntityToCommonsModel(
      MdtCnfgAmendmentCodesEntity localEntity) {

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
      MdtAcOpsDailyBillingEntity mdtAcOpsDailyBillingEntity) {
    MandateDailyTransModel mandateDailyTransModel = new MandateDailyTransModel();

    mandateDailyTransModel.setCreditorBank(mdtAcOpsDailyBillingEntity.getCreditorBank());
    mandateDailyTransModel.setDebtorBank(mdtAcOpsDailyBillingEntity.getDebtorBank());
    mandateDailyTransModel.setServiceId(mdtAcOpsDailyBillingEntity.getSubService());
    mandateDailyTransModel.setTxnType(mdtAcOpsDailyBillingEntity.getTxnType());
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    mandateDailyTransModel.setActionDate(sdf.format(mdtAcOpsDailyBillingEntity.getActionDate()));
    mandateDailyTransModel.setExtractMsgId(mdtAcOpsDailyBillingEntity.getExtMsgId());
    mandateDailyTransModel.setMndtReqTransId(mdtAcOpsDailyBillingEntity.getTxnId());
    mandateDailyTransModel.setMndtRefNumber(mdtAcOpsDailyBillingEntity.getMndtRefNum());
    mandateDailyTransModel.setAuthCode(mdtAcOpsDailyBillingEntity.getAuthCode());
    mandateDailyTransModel.setTrxnStatus(mdtAcOpsDailyBillingEntity.getTxnStatus());
    mandateDailyTransModel.setRespDate(sdf.format(mdtAcOpsDailyBillingEntity.getRespDate()));

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

  public ReasonCodesModel translateReportReasonCodesMdte002ToCommonsModel(
      MdtCnfgRejectReasonCodesEntity mdtCnfgRejectReasonCodesEntity) {
    ReasonCodesModel reasonCodeModel = new ReasonCodesModel();

    reasonCodeModel.setReasonCode(mdtCnfgRejectReasonCodesEntity.getRejectReasonCode());
    reasonCodeModel.setReasonCodeDescription(mdtCnfgRejectReasonCodesEntity.getRejectReasonDesc());
    reasonCodeModel.setActiveInd(mdtCnfgRejectReasonCodesEntity.getActiveInd());
    reasonCodeModel.setCreatedBy(mdtCnfgRejectReasonCodesEntity.getCreatedBy());
    reasonCodeModel.setCreatedDate(mdtCnfgRejectReasonCodesEntity.getCreatedDate());
    reasonCodeModel.setModifiedBy(mdtCnfgRejectReasonCodesEntity.getModifiedBy());
    reasonCodeModel.setModifiedDate(mdtCnfgRejectReasonCodesEntity.getModifiedDate());
    reasonCodeModel.setName(mdtCnfgRejectReasonCodesEntity.getRejectReasonDesc());

    return reasonCodeModel;
  }

  public ReasonCodesModel translateReportReasonCodesCancToCommonsModel(
      MdtCnfgCancellationCodesEntity mdtCnfgCancellationCodesEntity) {
    ReasonCodesModel reasonCodeModel = new ReasonCodesModel();

    reasonCodeModel.setReasonCode(mdtCnfgCancellationCodesEntity.getCancellationCode());
    reasonCodeModel.setReasonCodeDescription(
        mdtCnfgCancellationCodesEntity.getCancellationCodeDescription());
    reasonCodeModel.setActiveInd(mdtCnfgCancellationCodesEntity.getActiveInd());
    reasonCodeModel.setCreatedBy(mdtCnfgCancellationCodesEntity.getCreatedBy());
    reasonCodeModel.setCreatedDate(mdtCnfgCancellationCodesEntity.getCreatedDate());
    reasonCodeModel.setModifiedBy(mdtCnfgCancellationCodesEntity.getModifiedBy());
    reasonCodeModel.setModifiedDate(mdtCnfgCancellationCodesEntity.getModifiedDate());
    reasonCodeModel.setName(mdtCnfgCancellationCodesEntity.getCancellationCodeDescription());

    return reasonCodeModel;
  }

  public MandatesCountCommonsModel translateCommonsToSystemStatusEntity(
      MdtAcOpsMndtCountEntity mdtOpsMndtCountEntity) {
    MandatesCountCommonsModel mandatesCountCommonsModel = new MandatesCountCommonsModel();

    mandatesCountCommonsModel.setFileName(mdtOpsMndtCountEntity.getFileName());
    mandatesCountCommonsModel.setProcessDate(mdtOpsMndtCountEntity.getProcessDate());
    mandatesCountCommonsModel.setInstId(mdtOpsMndtCountEntity.getMdtAcOpsMndtCountPK().getInstId());
    mandatesCountCommonsModel.setServiceId(
        mdtOpsMndtCountEntity.getMdtAcOpsMndtCountPK().getServiceId());
    mandatesCountCommonsModel.setMsgId(mdtOpsMndtCountEntity.getMdtAcOpsMndtCountPK().getMsgId());
    mandatesCountCommonsModel.setIncoming(mdtOpsMndtCountEntity.getIncoming());
    mandatesCountCommonsModel.setNrOfFiles(mdtOpsMndtCountEntity.getNrOfFiles());
    mandatesCountCommonsModel.setNrOfMsgs(mdtOpsMndtCountEntity.getNrOfMsgs());
    mandatesCountCommonsModel.setOutgoing(mdtOpsMndtCountEntity.getOutgoing());
    mandatesCountCommonsModel.setNrMsgsRejected(mdtOpsMndtCountEntity.getNrMsgsRejected());
    mandatesCountCommonsModel.setNrMsgsAccepted(mdtOpsMndtCountEntity.getNrMsgsAccepted());
    mandatesCountCommonsModel.setNrMsgsExtracted(mdtOpsMndtCountEntity.getNrMsgsExtracted());

    return mandatesCountCommonsModel;
  }

  public MdtAcOpsMndtCountEntity translateCommonstoEntitySystemStatus(
      MandatesCountCommonsModel mandateCountModel) {
    MdtAcOpsMndtCountEntity mdtOpsMndtCountEntity = new MdtAcOpsMndtCountEntity();
    MdtAcOpsMndtCountPK mdtOpsMndtCountPK = new MdtAcOpsMndtCountPK();

    mdtOpsMndtCountPK.setInstId(mandateCountModel.getInstId());
    mdtOpsMndtCountPK.setMsgId(mandateCountModel.getMsgId());
    mdtOpsMndtCountPK.setServiceId(mandateCountModel.getServiceId());
    mdtOpsMndtCountEntity.setMdtAcOpsMndtCountPK(mdtOpsMndtCountPK);
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
      MdtOpsServicesEntity mdtOpsServicesEntity) {
    ServicesModel servicesModel = new ServicesModel();

    servicesModel.setCreatedBy(mdtOpsServicesEntity.getCreatedBy());
    servicesModel.setCreatedDate(mdtOpsServicesEntity.getCreatedDate());
    servicesModel.setModifiedBy(mdtOpsServicesEntity.getModifiedBy());
    servicesModel.setModifiedDate(mdtOpsServicesEntity.getModifiedDate());
    servicesModel.setRecordId(mdtOpsServicesEntity.getRecordId());
    servicesModel.setServiceIdIn(mdtOpsServicesEntity.getServiceIdIn());
    servicesModel.setServiceIdInDesc(mdtOpsServicesEntity.getServiceIdInDesc());
    servicesModel.setProcessDate(mdtOpsServicesEntity.getProcessDate());
    servicesModel.setProcessStatus(mdtOpsServicesEntity.getProcessStatus());
    servicesModel.setServiceIdOutSlaTime(mdtOpsServicesEntity.getServiceIdOutSlaTime());
    servicesModel.setActiveInd(mdtOpsServicesEntity.getActiveInd());
    servicesModel.setMsgTypeId(mdtOpsServicesEntity.getMsgTypeId());

    return servicesModel;
  }

  public ServicesModel translateOutgoingMdtOpsServicesEntityToCommonsModel(
      MdtOpsServicesEntity mdtOpsServicesEntity) {
    ServicesModel servicesModel = new ServicesModel();

    servicesModel.setCreatedBy(mdtOpsServicesEntity.getCreatedBy());
    servicesModel.setCreatedDate(mdtOpsServicesEntity.getCreatedDate());
    servicesModel.setModifiedBy(mdtOpsServicesEntity.getModifiedBy());
    servicesModel.setModifiedDate(mdtOpsServicesEntity.getModifiedDate());
    servicesModel.setRecordId(mdtOpsServicesEntity.getRecordId());
    servicesModel.setServiceIdOut(mdtOpsServicesEntity.getServiceIdOut());
    servicesModel.setServiceIdOutDesc(mdtOpsServicesEntity.getServiceIdOutDesc());
    servicesModel.setProcessDate(mdtOpsServicesEntity.getProcessDate());
    servicesModel.setProcessStatus(mdtOpsServicesEntity.getProcessStatus());
    servicesModel.setServiceIdOutSlaTime(mdtOpsServicesEntity.getServiceIdOutSlaTime());
    servicesModel.setActiveInd(mdtOpsServicesEntity.getActiveInd());
    servicesModel.setMsgTypeId(mdtOpsServicesEntity.getMsgTypeId());

    return servicesModel;
  }

  public IncSotEotModel translateIncSotEotCommonsModelToEntity(
      MdtAcOpsSotEotCtrlEntity mdtAcOpsSotEotCtrlEntity) {
    IncSotEotModel acOpsSotEotCntrlModel = new IncSotEotModel();
    MdtAcOpsSotEotCtrlPK mdtAcOpsSotEotCtrlPK = new MdtAcOpsSotEotCtrlPK();

    acOpsSotEotCntrlModel.setEotIn(mdtAcOpsSotEotCtrlEntity.getEotIn());
    acOpsSotEotCntrlModel.setInstId(mdtAcOpsSotEotCtrlEntity.getMdtAcOpsSotEotCtrlPK().getInstId());
    acOpsSotEotCntrlModel.setServiceId(
        mdtAcOpsSotEotCtrlEntity.getMdtAcOpsSotEotCtrlPK().getServiceId());
    acOpsSotEotCntrlModel.setSotIn(mdtAcOpsSotEotCtrlEntity.getSotIn());
    mdtAcOpsSotEotCtrlEntity.setMdtAcOpsSotEotCtrlPK(mdtAcOpsSotEotCtrlPK);

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
      MdtAcOpsFileSizeLimitEntity mdtAcOpsFileSizeLimitEntity) {

    FileSizeLimitModel fileSizeLimitModel = new FileSizeLimitModel();
    MdtAcOpsFileSizeLimitPK mdtAcOpsFileSizeLimitPK = new MdtAcOpsFileSizeLimitPK();

    fileSizeLimitModel.setMemberId(
        mdtAcOpsFileSizeLimitEntity.getMdtAcOpsFileSizeLimitPK().getMemberId());
    fileSizeLimitModel.setSubService(
        mdtAcOpsFileSizeLimitEntity.getMdtAcOpsFileSizeLimitPK().getSubService());

    fileSizeLimitModel.setProcessDate(mdtAcOpsFileSizeLimitEntity.getProcessDate());
    fileSizeLimitModel.setLimit(mdtAcOpsFileSizeLimitEntity.getLimit());
    fileSizeLimitModel.setDirection(mdtAcOpsFileSizeLimitEntity.getDirection());
    fileSizeLimitModel.setActiveId(mdtAcOpsFileSizeLimitEntity.getActiveId());
    mdtAcOpsFileSizeLimitEntity.setMdtAcOpsFileSizeLimitPK(mdtAcOpsFileSizeLimitPK);

    return fileSizeLimitModel;

  }

  public MdtAcOpsFileSizeLimitEntity translateOpsFileSizeLimitCommonsModelToEntity(
      FileSizeLimitModel fileSizeLimitModel) {

    MdtAcOpsFileSizeLimitEntity mdtAcOpsFileSizeLimitEntity = new MdtAcOpsFileSizeLimitEntity();
    MdtAcOpsFileSizeLimitPK mdtAcOpsFileSizeLimitPK = new MdtAcOpsFileSizeLimitPK();

    mdtAcOpsFileSizeLimitPK.setMemberId(fileSizeLimitModel.getMemberId());
    mdtAcOpsFileSizeLimitPK.setSubService(fileSizeLimitModel.getSubService());
    mdtAcOpsFileSizeLimitEntity.setLimit(fileSizeLimitModel.getLimit());
    mdtAcOpsFileSizeLimitEntity.setDirection(fileSizeLimitModel.getDirection());
    mdtAcOpsFileSizeLimitEntity.setProcessDate(fileSizeLimitModel.getProcessDate());
    mdtAcOpsFileSizeLimitEntity.setActiveId(fileSizeLimitModel.getActiveId());
    mdtAcOpsFileSizeLimitEntity.setMdtAcOpsFileSizeLimitPK(mdtAcOpsFileSizeLimitPK);

    return mdtAcOpsFileSizeLimitEntity;

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
