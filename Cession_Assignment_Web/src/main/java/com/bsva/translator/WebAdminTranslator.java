package com.bsva.translator;

import com.bsva.commons.model.AcOpsSotEotCntrlModel;
import com.bsva.commons.model.AcOpsTransCtrlMsgModel;
import com.bsva.commons.model.AccountTypeModel;
import com.bsva.commons.model.AdjustmentCategoryModel;
import com.bsva.commons.model.AmendmentCodesModel;
import com.bsva.commons.model.AuditTrackingModel;
import com.bsva.commons.model.CisBranchModel;
import com.bsva.commons.model.CisMemberModel;
import com.bsva.commons.model.CnfgAuthTypeModel;
import com.bsva.commons.model.CnfgRejectReasonCodesModel;
import com.bsva.commons.model.CompParamModel;
import com.bsva.commons.model.ConfgErrorCodesModel;
import com.bsva.commons.model.ConfgSeverityCodesModel;
import com.bsva.commons.model.CronTimesModel;
import com.bsva.commons.model.CurrencyCodesModel;
import com.bsva.commons.model.CustomerParametersModel;
import com.bsva.commons.model.DebitValueTypeModel;
import com.bsva.commons.model.EntryClassesModel;
import com.bsva.commons.model.FileSizeLimitModel;
import com.bsva.commons.model.FileStatusCommonsModel;
import com.bsva.commons.model.FrequencyCodesModel;
import com.bsva.commons.model.IncSotEotModel;
import com.bsva.commons.model.LocalInstrumentCodesModel;
import com.bsva.commons.model.MandatesCountCommonsModel;
import com.bsva.commons.model.MandatesModel;
import com.bsva.commons.model.OpsCronTimeModel;
import com.bsva.commons.model.OpsCustParamModel;
import com.bsva.commons.model.OpsFileRegModel;
import com.bsva.commons.model.OpsProcessControlCommonsModel;
import com.bsva.commons.model.OpsRefSeqNumberCommonsModel;
import com.bsva.commons.model.OpsSlaTimesCommonsModel;
import com.bsva.commons.model.OpsStatusDetailsModel;
import com.bsva.commons.model.OpsStatusHdrsModel;
import com.bsva.commons.model.OutSotEotModel;
import com.bsva.commons.model.ProcessStatusModel;
import com.bsva.commons.model.ReasonCodesModel;
import com.bsva.commons.model.ReportsNamesModel;
import com.bsva.commons.model.SchedulerCommonsModel;
import com.bsva.commons.model.SchedulerModel;
import com.bsva.commons.model.SequenceTypesModel;
import com.bsva.commons.model.ServicesModel;
import com.bsva.commons.model.SeverityCodesModel;
import com.bsva.commons.model.StatusReasonCodesModel;
import com.bsva.commons.model.SysCisBankModel;
import com.bsva.commons.model.SysCisBranchModel;
import com.bsva.commons.model.SysCtrlFileSizeLimitModel;
import com.bsva.commons.model.SysCtrlSlaTimeModel;
import com.bsva.commons.model.SystemControlServicesModel;
import com.bsva.commons.model.SystemParameterModel;
import com.bsva.commons.model.TransCtrlMsgModel;
import com.bsva.controller.WebOpsProcessControlsModel;
import com.bsva.models.WebAcOpsSotEotModel;
import com.bsva.models.WebAcOpsTransCtrlMsgModel;
import com.bsva.models.WebAccountTypeModel;
import com.bsva.models.WebAdjustmentCategoryModel;
import com.bsva.models.WebAmendmentCodesModel;
import com.bsva.models.WebAuditTrackingModel;
import com.bsva.models.WebAuthTypeModel;
import com.bsva.models.WebCisBranchModel;
import com.bsva.models.WebCisMemberModel;
import com.bsva.models.WebComParamModel;
import com.bsva.models.WebConfgErrorCodesModel;
import com.bsva.models.WebConfgSeverityCodesModel;
import com.bsva.models.WebCountryCodesModel;
import com.bsva.models.WebCronTimeModel;
import com.bsva.models.WebCurrencyCodesModel;
import com.bsva.models.WebCustomerParametersModel;
import com.bsva.models.WebDebitValueTypeModel;
import com.bsva.models.WebEntryClassesModel;
import com.bsva.models.WebFileSizeLimitModel;
import com.bsva.models.WebFileStatusModel;
import com.bsva.models.WebFrequencyCodesModel;
import com.bsva.models.WebIncSotEotModel;
import com.bsva.models.WebLocalInstrumentCodesModel;
import com.bsva.models.WebMandateCountModel;
import com.bsva.models.WebMandateModel;
import com.bsva.models.WebOpsCronTimeModel;
import com.bsva.models.WebOpsCustomerParameters;
import com.bsva.models.WebOpsFileRegModel;
import com.bsva.models.WebOpsRefSequenceNumber;
import com.bsva.models.WebOpsServicesModel;
import com.bsva.models.WebOpsSlaTimesModel;
import com.bsva.models.WebOpsStatusDetailsModel;
import com.bsva.models.WebOpsStatusHdrsModel;
import com.bsva.models.WebOutSotEotModel;
import com.bsva.models.WebProcessStatusModel;
import com.bsva.models.WebQuartzSchedulerModel;
import com.bsva.models.WebReasonCodesModel;
import com.bsva.models.WebRejectReasonCodesModel;
import com.bsva.models.WebReportsNamesModel;
import com.bsva.models.WebSchedulerModel;
import com.bsva.models.WebSequenceTypesModel;
import com.bsva.models.WebServicesModel;
import com.bsva.models.WebSeverityCodesModel;
import com.bsva.models.WebStatusReasonCodesModel;
import com.bsva.models.WebSysCisBankModel;
import com.bsva.models.WebSysCisBranchModel;
import com.bsva.models.WebSysCtrlFileSizeLimitModel;
import com.bsva.models.WebSysCtrlSlaTimeModel;
import com.bsva.models.WebSystemControlServicesModel;
import com.bsva.models.WebSystemParameterModel;
import com.bsva.models.WebTransCtrlMsgModel;
import java.math.BigInteger;
import java.text.SimpleDateFormat;


/**
 * @author salehas
 */
public class WebAdminTranslator {

  public LocalInstrumentCodesModel translateWebInstrumentCodesModelToCommonsModel(
      WebLocalInstrumentCodesModel webLocalInstrumentCodesModel) {
    LocalInstrumentCodesModel localInstrCodeModel = new LocalInstrumentCodesModel();

    localInstrCodeModel.setLocalInstrumentCode(
        webLocalInstrumentCodesModel.getLocalInstrumentCode());
    localInstrCodeModel.setInstCodeDescription(
        webLocalInstrumentCodesModel.getInstCodeDescription());
    localInstrCodeModel.setActiveInd(webLocalInstrumentCodesModel.getActiveInd());
    localInstrCodeModel.setCreatedBy(webLocalInstrumentCodesModel.getCreatedBy());
    localInstrCodeModel.setCreatedDate(webLocalInstrumentCodesModel.getCreatedDate());
    localInstrCodeModel.setModifiedBy(webLocalInstrumentCodesModel.getModifiedBy());
    localInstrCodeModel.setModifiedDate(webLocalInstrumentCodesModel.getModifiedDate());

    return localInstrCodeModel;
  }

  public WebLocalInstrumentCodesModel translateCommonsInstrumentCodesModelToWebModel(
      LocalInstrumentCodesModel localInstrumentCodesModel) {

    WebLocalInstrumentCodesModel webInstrCodeModel = new WebLocalInstrumentCodesModel();

    webInstrCodeModel.setLocalInstrumentCode(localInstrumentCodesModel.getLocalInstrumentCode());
    webInstrCodeModel.setInstCodeDescription(localInstrumentCodesModel.getInstCodeDescription());
    webInstrCodeModel.setActiveInd(localInstrumentCodesModel.getActiveInd());
    webInstrCodeModel.setCreatedBy(localInstrumentCodesModel.getCreatedBy());
    webInstrCodeModel.setCreatedDate(localInstrumentCodesModel.getCreatedDate());
    webInstrCodeModel.setModifiedBy(localInstrumentCodesModel.getModifiedBy());
    webInstrCodeModel.setModifiedDate(localInstrumentCodesModel.getModifiedDate());

    return webInstrCodeModel;
  }

  public ReasonCodesModel translateWebReasonCodesModelToReasonCommonsModel(
      WebReasonCodesModel webReasonCodesModel) {

    ReasonCodesModel commonsReasonModel = new ReasonCodesModel();

    commonsReasonModel.setReasonCode(webReasonCodesModel.getReasonCode());
    commonsReasonModel.setReasonCodeDescription(webReasonCodesModel.getReasonCodeDescription());
    commonsReasonModel.setActiveInd(webReasonCodesModel.getActiveInd());
    commonsReasonModel.setCreatedBy(webReasonCodesModel.getCreatedBy());
    commonsReasonModel.setCreatedDate(webReasonCodesModel.getCreatedDate());
    commonsReasonModel.setModifiedBy(webReasonCodesModel.getModifiedBy());
    commonsReasonModel.setModifiedDate(webReasonCodesModel.getModifiedDate());
    commonsReasonModel.setName(webReasonCodesModel.getName());


    return commonsReasonModel;
  }

  public WebReasonCodesModel transalateCommonsReasonCodesModelToReasonWebModel(
      ReasonCodesModel reasonCodesModel) {

    WebReasonCodesModel webReasModel = new WebReasonCodesModel();

    webReasModel.setReasonCode(reasonCodesModel.getReasonCode());
    webReasModel.setReasonCodeDescription(reasonCodesModel.getReasonCodeDescription());
    webReasModel.setActiveInd(reasonCodesModel.getActiveInd());
    webReasModel.setCreatedBy(reasonCodesModel.getCreatedBy());
    webReasModel.setCreatedDate(reasonCodesModel.getCreatedDate());
    webReasModel.setModifiedBy(reasonCodesModel.getModifiedBy());
    webReasModel.setModifiedDate(reasonCodesModel.getModifiedDate());
    webReasModel.setName(reasonCodesModel.getName());

    return webReasModel;
  }

  public CurrencyCodesModel translateWebCurrencyCodesModelToCurrencyCommonsModel(
      WebCurrencyCodesModel webCurrencyCodesModel) {
    CurrencyCodesModel commonsCurrencyModel = new CurrencyCodesModel();

    commonsCurrencyModel.setAlphaCurrCode(webCurrencyCodesModel.getAlphaCurrCode());
    commonsCurrencyModel.setCountryCode(webCurrencyCodesModel.getCountryCode());
    commonsCurrencyModel.setActiveInd(webCurrencyCodesModel.getActiveInd());
    commonsCurrencyModel.setCurrCodeDesc(webCurrencyCodesModel.getCurrCodeDesc());
    commonsCurrencyModel.setNumCurrCode(webCurrencyCodesModel.getNumCurrCode());
    commonsCurrencyModel.setCreatedBy(webCurrencyCodesModel.getCreatedBy());
    commonsCurrencyModel.setCreatedDate(webCurrencyCodesModel.getCreatedDate());
    commonsCurrencyModel.setModifiedBy(webCurrencyCodesModel.getModifiedBy());
    commonsCurrencyModel.setModifiedDate(webCurrencyCodesModel.getModifiedDate());

    return commonsCurrencyModel;

  }

  public SequenceTypesModel translateWebSequenceTypesModeltoSequenceTypesCommonsModel(
      WebSequenceTypesModel webSequenceTypesModel) {
    SequenceTypesModel commonsSequenceTypesModel = new SequenceTypesModel();

    commonsSequenceTypesModel.setSequenceCode(webSequenceTypesModel.getSequenceCode());
    commonsSequenceTypesModel.setSequenceDescription(
        webSequenceTypesModel.getSequenceDescription());
    commonsSequenceTypesModel.setActiveInd(webSequenceTypesModel.getActiveInd());
    commonsSequenceTypesModel.setCreatedBy(webSequenceTypesModel.getCreatedBy());
    commonsSequenceTypesModel.setCreatedDate(webSequenceTypesModel.getCreatedDate());
    commonsSequenceTypesModel.setModifiedBy(webSequenceTypesModel.getModifiedBy());
    commonsSequenceTypesModel.setModifiedDate(webSequenceTypesModel.getModifiedDate());

    return commonsSequenceTypesModel;

  }

  public WebSequenceTypesModel translateCommonsSequenceTypeModelToWebModel(
      SequenceTypesModel sequenceTypesModel) {
    WebSequenceTypesModel webSeqTypeModel = new WebSequenceTypesModel();

    webSeqTypeModel.setSequenceCode(sequenceTypesModel.getSequenceCode());
    webSeqTypeModel.setSequenceDescription(sequenceTypesModel.getSequenceDescription());
    webSeqTypeModel.setActiveInd(sequenceTypesModel.getActiveInd());
    webSeqTypeModel.setCreatedBy(sequenceTypesModel.getCreatedBy());
    webSeqTypeModel.setCreatedDate(sequenceTypesModel.getCreatedDate());
    webSeqTypeModel.setModifiedBy(sequenceTypesModel.getModifiedBy());
    webSeqTypeModel.setModifiedDate(sequenceTypesModel.getModifiedDate());
    return webSeqTypeModel;
  }

  public CnfgAuthTypeModel translateWebAuthTypeModeltoAuthTypesCommonsModel(
      WebAuthTypeModel webAuthTypeModel) {
    CnfgAuthTypeModel authTypeModel = new CnfgAuthTypeModel();
    authTypeModel.setActiveInd(webAuthTypeModel.getActiveInd());
    authTypeModel.setAuthType(webAuthTypeModel.getAuthType());
    authTypeModel.setAuthTypeDescription(webAuthTypeModel.getAuthTypeDescription());

    return authTypeModel;

  }

  public WebAuthTypeModel translateCommonsAuthTypeModelToWebModel(CnfgAuthTypeModel authTypeModel) {
    WebAuthTypeModel webAuthTypeModel = new WebAuthTypeModel();

    webAuthTypeModel.setActiveInd(authTypeModel.getActiveInd());
    webAuthTypeModel.setAuthType(authTypeModel.getAuthType());
    webAuthTypeModel.setAuthTypeDescription(authTypeModel.getAuthTypeDescription());

    return webAuthTypeModel;
  }

  public WebFrequencyCodesModel translateCommonsFrequencyCodesModelToWebModel(
      FrequencyCodesModel frequencyCodesModel) {
    WebFrequencyCodesModel webFreqCodesModel = new WebFrequencyCodesModel();

    webFreqCodesModel.setFrequencyCode(frequencyCodesModel.getFrequencyCode());
    webFreqCodesModel.setFrequencyCodeDescription(
        frequencyCodesModel.getFrequencyCodeDescription());
    webFreqCodesModel.setActiveInd(frequencyCodesModel.getActiveInd());
    webFreqCodesModel.setCreatedBy(frequencyCodesModel.getCreatedBy());
    webFreqCodesModel.setCreatedDate(frequencyCodesModel.getCreatedDate());
    webFreqCodesModel.setModifiedBy(frequencyCodesModel.getModifiedBy());
    webFreqCodesModel.setModifiedDate(frequencyCodesModel.getModifiedDate());

    return webFreqCodesModel;
  }

  public WebCurrencyCodesModel translateCommonsCurrencyCodesModelToWebModel(
      CurrencyCodesModel currencyCodesModel) {
    WebCurrencyCodesModel webCurrCodesModel = new WebCurrencyCodesModel();

    webCurrCodesModel.setAlphaCurrCode(currencyCodesModel.getAlphaCurrCode());
    webCurrCodesModel.setCountryCode(currencyCodesModel.getCountryCode());
    webCurrCodesModel.setActiveInd(currencyCodesModel.getActiveInd());
    webCurrCodesModel.setCurrCodeDesc(currencyCodesModel.getCurrCodeDesc());
    webCurrCodesModel.setNumCurrCode(currencyCodesModel.getNumCurrCode());
    webCurrCodesModel.setCreatedBy(currencyCodesModel.getCreatedBy());
    webCurrCodesModel.setCreatedDate(currencyCodesModel.getCreatedDate());
    webCurrCodesModel.setModifiedBy(currencyCodesModel.getModifiedBy());
    webCurrCodesModel.setModifiedDate(currencyCodesModel.getModifiedDate());

    return webCurrCodesModel;
  }

  public WebDebitValueTypeModel translateCommonsDebitValueModelToWebModel(
      DebitValueTypeModel debitValueTypeModel) {
    WebDebitValueTypeModel webDebitValueTypeModel = new WebDebitValueTypeModel();

    webDebitValueTypeModel.setDebValueTypeCode(debitValueTypeModel.getDebValueTypeCode());
    webDebitValueTypeModel.setDebValueTypeDesc(debitValueTypeModel.getDebValueTypeDesc());
    webDebitValueTypeModel.setActiveInd(debitValueTypeModel.getActiveInd());
    webDebitValueTypeModel.setCreatedBy(debitValueTypeModel.getCreatedBy());
    webDebitValueTypeModel.setCreatedDate(debitValueTypeModel.getCreatedDate());
    webDebitValueTypeModel.setModifiedBy(debitValueTypeModel.getModifiedBy());
    webDebitValueTypeModel.setModifiedDate(debitValueTypeModel.getModifiedDate());

    return webDebitValueTypeModel;

  }

  public DebitValueTypeModel translateWebDebitValueModelToCommonsModel(
      WebDebitValueTypeModel webDebitValueTypeModel) {
    DebitValueTypeModel debitValueTypeModel = new DebitValueTypeModel();

    debitValueTypeModel.setDebValueTypeCode(webDebitValueTypeModel.getDebValueTypeCode());
    debitValueTypeModel.setDebValueTypeDesc(webDebitValueTypeModel.getDebValueTypeDesc());
    debitValueTypeModel.setActiveInd(webDebitValueTypeModel.getActiveInd());
    debitValueTypeModel.setCreatedBy(webDebitValueTypeModel.getCreatedBy());
    debitValueTypeModel.setCreatedDate(webDebitValueTypeModel.getCreatedDate());
    debitValueTypeModel.setModifiedBy(webDebitValueTypeModel.getModifiedBy());
    debitValueTypeModel.setModifiedDate(webDebitValueTypeModel.getModifiedDate());

    return debitValueTypeModel;
  }


  public WebCronTimeModel translateCommonsCronTimeModeToWebModel(
      CronTimesModel crontimemodel) {
    WebCronTimeModel webcrontimemodel = new WebCronTimeModel();

    webcrontimemodel.setActiveInd(crontimemodel.getActiveInd());
    webcrontimemodel.setCreatedBy(crontimemodel.getCreatedBy());
    webcrontimemodel.setCreatedDate(crontimemodel.getCreatedDate());
    webcrontimemodel.setCronTime(crontimemodel.getCronTime());
    webcrontimemodel.setModifiedBy(crontimemodel.getModifiedBy());
    webcrontimemodel.setModifiedDate(crontimemodel.getModifiedDate());
    webcrontimemodel.setProcessName(crontimemodel.getProcessName());

    return webcrontimemodel;

  }

  public CronTimesModel translateWebCronTimeModelToCommonsModel(
      WebCronTimeModel webcrontimemodel) {

    CronTimesModel crontimemodel = new CronTimesModel();


    crontimemodel.setActiveInd(webcrontimemodel.getActiveInd());
    crontimemodel.setCreatedBy(webcrontimemodel.getCreatedBy());
    crontimemodel.setCreatedDate(webcrontimemodel.getCreatedDate());
    crontimemodel.setCronTime(webcrontimemodel.getCronTime());
    crontimemodel.setModifiedBy(webcrontimemodel.getModifiedBy());
    crontimemodel.setModifiedDate(webcrontimemodel.getModifiedDate());
    crontimemodel.setProcessName(webcrontimemodel.getProcessName());

    return crontimemodel;

  }


  public FrequencyCodesModel translateWebFrequencyCodesModelToFrequencyCommonsModel(
      WebFrequencyCodesModel webFrequencyCodesModel) {
    FrequencyCodesModel frequencyCodesModel = new FrequencyCodesModel();
    frequencyCodesModel.setFrequencyCode(webFrequencyCodesModel.getFrequencyCode());
    frequencyCodesModel.setFrequencyCodeDescription(
        webFrequencyCodesModel.getFrequencyCodeDescription());
    frequencyCodesModel.setActiveInd(webFrequencyCodesModel.getActiveInd());
    frequencyCodesModel.setCreatedBy(webFrequencyCodesModel.getCreatedBy());
    frequencyCodesModel.setCreatedDate(webFrequencyCodesModel.getCreatedDate());
    frequencyCodesModel.setModifiedBy(webFrequencyCodesModel.getModifiedBy());
    frequencyCodesModel.setModifiedDate(webFrequencyCodesModel.getModifiedDate());

    return frequencyCodesModel;
  }

  public ConfgErrorCodesModel translateWebConfgErrorCodesModelToCommonsModel(
      WebConfgErrorCodesModel webErrorCodesModel) {
    ConfgErrorCodesModel errorCodesModel = new ConfgErrorCodesModel();

    errorCodesModel.setErrorCode(webErrorCodesModel.getErrorCode());
    errorCodesModel.setErrorDescription(webErrorCodesModel.getErrorDescription());
    errorCodesModel.setActiveInd(webErrorCodesModel.getActiveInd());
    errorCodesModel.setSeverity(new BigInteger(webErrorCodesModel.getSeverity()));
    errorCodesModel.setCreatedBy(webErrorCodesModel.getCreatedBy());
    errorCodesModel.setCreatedDate(webErrorCodesModel.getCreatedDate());
    errorCodesModel.setModifiedBy(webErrorCodesModel.getModifiedBy());
    errorCodesModel.setModifiedDate(webErrorCodesModel.getModifiedDate());

    return errorCodesModel;
  }

  public WebConfgErrorCodesModel translateCommonsErrorCodesModelToWebModel(
      ConfgErrorCodesModel errorCodesModel) {
    WebConfgErrorCodesModel webErrorCodesModel = new WebConfgErrorCodesModel();


    webErrorCodesModel.setErrorCode(errorCodesModel.getErrorCode());
    webErrorCodesModel.setErrorDescription(errorCodesModel.getErrorDescription());
    webErrorCodesModel.setActiveInd(errorCodesModel.getActiveInd());
    webErrorCodesModel.setSeverity(String.valueOf(errorCodesModel.getSeverity()));
    webErrorCodesModel.setCreatedBy(errorCodesModel.getCreatedBy());
    webErrorCodesModel.setCreatedDate(errorCodesModel.getCreatedDate());
    webErrorCodesModel.setModifiedBy(errorCodesModel.getModifiedBy());
    webErrorCodesModel.setModifiedDate(errorCodesModel.getModifiedDate());

    return webErrorCodesModel;
  }

  public static WebMandateModel translateCommonsMandateModelToWebModel(
      MandatesModel mandateCommonModel) {

    WebMandateModel webmandateModel = new WebMandateModel();
    webmandateModel.setMandateId(mandateCommonModel.getMandateId());
    webmandateModel.setLocalInstrumentCode(mandateCommonModel.getLocalInstrumentCode());
    webmandateModel.setSequenceType(mandateCommonModel.getSequenceType());
    webmandateModel.setFrequencyCode(mandateCommonModel.getFrequencyCode());
    webmandateModel.setCollAmtCurrency(mandateCommonModel.getCollAmtCurrency());
    webmandateModel.setMaxAmountCurrency(mandateCommonModel.getMaxAmountCurrency());
    webmandateModel.setCollectionAmt(mandateCommonModel.getCollectionAmt());
    webmandateModel.setMaximumAmt(mandateCommonModel.getMaximumAmt());
    webmandateModel.setCreditorSchemeName(mandateCommonModel.getCreditorSchemeName());
    webmandateModel.setContractNo(mandateCommonModel.getContractNo());
    webmandateModel.setCreditorId(mandateCommonModel.getCreditorId());
    webmandateModel.setCreditorName(mandateCommonModel.getCreditorName());
    webmandateModel.setCreditorContactDetail(mandateCommonModel.getCreditorContactDetail());
    webmandateModel.setCreditorBranchNo(mandateCommonModel.getCreditorBranchNo());
    webmandateModel.setCreditorBank(mandateCommonModel.getCreditorAccNo());
    webmandateModel.setCreditorAccNo(mandateCommonModel.getCreditorAccNo());
    webmandateModel.setUltimateCreditorName(mandateCommonModel.getUltimateCreditorName());
    webmandateModel.setUltimateCreditorContactDetail(
        mandateCommonModel.getUltimateCreditorContactDetail());
    webmandateModel.setCreditorBicCode(mandateCommonModel.getCreditorBicCode());
    webmandateModel.setDebtorName(mandateCommonModel.getDebtorName());
    webmandateModel.setDebtorIdNo(mandateCommonModel.getDebtorIdNo());
    webmandateModel.setDebtorAccName(mandateCommonModel.getDebtorAccName());
    webmandateModel.setDebtorAccNo(mandateCommonModel.getDebtorAccNo());
    webmandateModel.setDebtorBranchNo(mandateCommonModel.getDebtorBranchNo());
    webmandateModel.setUltimateDebtorName(mandateCommonModel.getUltimateDebtorName());
    webmandateModel.setDebtorContactDetail(mandateCommonModel.getDebtorContactDetail());
    webmandateModel.setUltimateDebtorContactDetail(
        mandateCommonModel.getUltimateDebtorContactDetail());
    webmandateModel.setFromDate(mandateCommonModel.getFromDate());
    webmandateModel.setToDate(mandateCommonModel.getToDate());
    webmandateModel.setFirstCollectionDate(mandateCommonModel.getFirstCollectionDate());
    webmandateModel.setFinalCollectionDate(mandateCommonModel.getFinalCollectionDate());
    webmandateModel.setActiveInd(mandateCommonModel.getActiveInd());
    webmandateModel.setMandateReqId(mandateCommonModel.getMandateReqId());
    webmandateModel.setMsgId(mandateCommonModel.getMsgId());
    webmandateModel.setCreatedBy(mandateCommonModel.getCreatedBy());
    webmandateModel.setModifiedBy(mandateCommonModel.getModifiedBy());
    webmandateModel.setCreatedDate(mandateCommonModel.getCreatedDate());
    webmandateModel.setModifiedBy(mandateCommonModel.getModifiedBy());
    webmandateModel.setProcessStatus(mandateCommonModel.getProcessStatus());
    webmandateModel.setModReason(mandateCommonModel.getModReason());

    return webmandateModel;
  }

  public static MandatesModel translateWebMandatesModelToCommonsMode(
      WebMandateModel webmandateModel) {
    MandatesModel mandateCommonModel = new MandatesModel();

    mandateCommonModel.setMandateId(webmandateModel.getMandateId());
    mandateCommonModel.setLocalInstrumentCode(webmandateModel.getLocalInstrumentCode());
    mandateCommonModel.setSequenceType(webmandateModel.getSequenceType());
    mandateCommonModel.setFrequencyCode(webmandateModel.getFrequencyCode());
    mandateCommonModel.setCollAmtCurrency(webmandateModel.getCollAmtCurrency());
    mandateCommonModel.setMaxAmountCurrency(webmandateModel.getMaxAmountCurrency());
    mandateCommonModel.setCollectionAmt(webmandateModel.getCollectionAmt());
    mandateCommonModel.setMaximumAmt(webmandateModel.getMaximumAmt());
    mandateCommonModel.setCreditorSchemeName(webmandateModel.getCreditorSchemeName());
    mandateCommonModel.setContractNo(webmandateModel.getContractNo());
    mandateCommonModel.setCreditorId(webmandateModel.getCreditorId());
    mandateCommonModel.setCreditorName(webmandateModel.getCreditorName());
    mandateCommonModel.setCreditorContactDetail(webmandateModel.getCreditorContactDetail());
    mandateCommonModel.setCreditorBranchNo(webmandateModel.getCreditorBranchNo());
    mandateCommonModel.setCreditorBank(webmandateModel.getCreditorAccNo());
    mandateCommonModel.setCreditorAccNo(webmandateModel.getCreditorAccNo());
    mandateCommonModel.setUltimateCreditorName(webmandateModel.getUltimateCreditorName());
    mandateCommonModel.setUltimateCreditorContactDetail(
        webmandateModel.getUltimateCreditorContactDetail());
    mandateCommonModel.setCreditorBicCode(webmandateModel.getCreditorBicCode());
    mandateCommonModel.setDebtorName(webmandateModel.getDebtorName());
    mandateCommonModel.setDebtorIdNo(webmandateModel.getDebtorIdNo());
    mandateCommonModel.setDebtorAccName(webmandateModel.getDebtorAccName());
    mandateCommonModel.setDebtorAccNo(webmandateModel.getDebtorAccNo());
    mandateCommonModel.setDebtorBranchNo(webmandateModel.getDebtorBranchNo());
    mandateCommonModel.setUltimateDebtorName(webmandateModel.getUltimateDebtorName());
    mandateCommonModel.setDebtorContactDetail(webmandateModel.getDebtorContactDetail());
    mandateCommonModel.setUltimateDebtorContactDetail(
        webmandateModel.getUltimateDebtorContactDetail());
    mandateCommonModel.setFromDate(webmandateModel.getFromDate());
    mandateCommonModel.setToDate(webmandateModel.getToDate());
    mandateCommonModel.setFirstCollectionDate(webmandateModel.getFirstCollectionDate());
    mandateCommonModel.setFinalCollectionDate(webmandateModel.getFinalCollectionDate());
    mandateCommonModel.setActiveInd(webmandateModel.getActiveInd());
    mandateCommonModel.setMandateReqId(webmandateModel.getMandateReqId());
    mandateCommonModel.setMsgId(webmandateModel.getMsgId());
    mandateCommonModel.setCreatedBy(webmandateModel.getCreatedBy());
    mandateCommonModel.setModifiedBy(webmandateModel.getModifiedBy());
    mandateCommonModel.setCreatedDate(webmandateModel.getCreatedDate());
    mandateCommonModel.setModifiedBy(webmandateModel.getModifiedBy());
    mandateCommonModel.setProcessStatus(webmandateModel.getProcessStatus());
    mandateCommonModel.setModReason(webmandateModel.getModReason());


    return mandateCommonModel;

  }

  public static WebCisMemberModel translateCommonsCisMemberModelToWebModel(
      CisMemberModel cisMemberModel) {
    // TODO Auto-generated method stub
    WebCisMemberModel webCisMemberModel = new WebCisMemberModel();

    webCisMemberModel.setAbbrevCurrency(cisMemberModel.getAbbrevCurrency());
    webCisMemberModel.setAbbrevMemberName(cisMemberModel.getAbbrevMemberName());
    webCisMemberModel.setCountry(cisMemberModel.getCountry());
    webCisMemberModel.setCurrency(cisMemberModel.getCurrency());
    webCisMemberModel.setMemberBranchEndRange(cisMemberModel.getMemberBranchEndRange());
    webCisMemberModel.setMemberBranchStartRange(cisMemberModel.getMemberBranchStartRange());
    webCisMemberModel.setMemberName(cisMemberModel.getMemberName());
    webCisMemberModel.setMemberNo(cisMemberModel.getMemberNo());
    webCisMemberModel.setMnemonicMemberName(cisMemberModel.getMnemonicMemberName());


    return webCisMemberModel;
  }

  public static WebCisBranchModel translateCommonsCisBranchModelToWebModel(
      CisBranchModel cisBranchModel) {

    WebCisBranchModel webCisBranchModel = new WebCisBranchModel();

    webCisBranchModel.setAbbrDivisionName(cisBranchModel.getAbbrDivisionName());
    webCisBranchModel.setAddressLine1(cisBranchModel.getAddressLine1());
    webCisBranchModel.setAddressLine2(cisBranchModel.getAddressLine2());
    webCisBranchModel.setAddressLine3(cisBranchModel.getAddressLine3());
    webCisBranchModel.setAgencyBranchNo(cisBranchModel.getAgencyBranchNo());
    webCisBranchModel.setBranchName(cisBranchModel.getBranchName());
    webCisBranchModel.setBranchNo(cisBranchModel.getBranchNo());
    webCisBranchModel.setDivision(cisBranchModel.getDivision());
    webCisBranchModel.setInstitutionType(cisBranchModel.getInstitutionType());
    webCisBranchModel.setMemberNo(cisBranchModel.getMemberNo());

    return webCisBranchModel;
  }

  public CustomerParametersModel translateWebCustomerParametersModelToCommonsModel(
      WebCustomerParametersModel webCustomerParametersModel) {
    CustomerParametersModel custParamModel = new CustomerParametersModel();

    custParamModel.setCasaAmdXsdNs(webCustomerParametersModel.getCasaAmdXsdNs());
    custParamModel.setCasaAccpXsdNs(webCustomerParametersModel.getCasaAccpXsdNs());
    custParamModel.setActiveInd(webCustomerParametersModel.getActiveInd());
    custParamModel.setInstId(webCustomerParametersModel.getInstId());
    custParamModel.setCasaStatusRepXsdNs(webCustomerParametersModel.getCasaStatusRepXsdNs());
    custParamModel.setCasaConfirmXsdNs(webCustomerParametersModel.getCasaConfirmXsdNs());
    custParamModel.setProcessDay(webCustomerParametersModel.getProcessDay());
    custParamModel.setCreatedBy(webCustomerParametersModel.getCreatedBy());
    custParamModel.setCreatedDate(webCustomerParametersModel.getCreatedDate());
    custParamModel.setModifiedBy(webCustomerParametersModel.getModifiedBy());
    custParamModel.setModifiedDate(webCustomerParametersModel.getModifiedDate());

    return custParamModel;
  }

  public WebCustomerParametersModel translateCommonsCustomerParametersModelToWebModel(
      CustomerParametersModel customerParametersModel) {
    WebCustomerParametersModel webcustParamModel = new WebCustomerParametersModel();

    webcustParamModel.setCasaAmdXsdNs(customerParametersModel.getCasaAmdXsdNs());
    webcustParamModel.setCasaAccpXsdNs(customerParametersModel.getCasaAccpXsdNs());
    webcustParamModel.setActiveInd(customerParametersModel.getActiveInd());
    webcustParamModel.setInstId(customerParametersModel.getInstId());
    webcustParamModel.setCasaStatusRepXsdNs(customerParametersModel.getCasaStatusRepXsdNs());
    webcustParamModel.setCasaConfirmXsdNs(customerParametersModel.getCasaConfirmXsdNs());
    webcustParamModel.setProcessDay(customerParametersModel.getProcessDay());
    webcustParamModel.setCreatedBy(customerParametersModel.getCreatedBy());
    webcustParamModel.setCreatedDate(customerParametersModel.getCreatedDate());
    webcustParamModel.setModifiedBy(customerParametersModel.getModifiedBy());
    webcustParamModel.setModifiedDate(customerParametersModel.getModifiedDate());


    return webcustParamModel;
  }


  public WebOpsFileRegModel translateCommonsOpsFileRegModelToWebModel(
      OpsFileRegModel opsfileRegModel) {

    WebOpsFileRegModel webOpsFileRegModel = new WebOpsFileRegModel();

    webOpsFileRegModel.setFileName(opsfileRegModel.getFileName());
    webOpsFileRegModel.setFilepath(opsfileRegModel.getFilepath());
    webOpsFileRegModel.setStatus(opsfileRegModel.getStatus());
    webOpsFileRegModel.setReason(opsfileRegModel.getReason());
    webOpsFileRegModel.setProcessDate(opsfileRegModel.getProcessDate());
    webOpsFileRegModel.setNameSpace(opsfileRegModel.getNameSpace());
    webOpsFileRegModel.setGrpHdrMsgId(opsfileRegModel.getGrpHdrMsgId());
    webOpsFileRegModel.setInOutInd(opsfileRegModel.getInOutInd());
    webOpsFileRegModel.setOnlineInd(opsfileRegModel.getOnlineInd());
    webOpsFileRegModel.setExtractMsgId(opsfileRegModel.getExtractMsgId());
    return webOpsFileRegModel;
  }

  public OpsFileRegModel translateWebOpsFileRegModelToCommonsModel(
      WebOpsFileRegModel webOpsFileRegModel) {
    OpsFileRegModel opsFileRegModel = new OpsFileRegModel();
    opsFileRegModel.setFileName(webOpsFileRegModel.getFileName());
    opsFileRegModel.setFilepath(webOpsFileRegModel.getFilepath());
    opsFileRegModel.setStatus(webOpsFileRegModel.getStatus());
    opsFileRegModel.setReason(webOpsFileRegModel.getReason());
    opsFileRegModel.setProcessDate(webOpsFileRegModel.getProcessDate());
    opsFileRegModel.setNameSpace(webOpsFileRegModel.getNameSpace());
    opsFileRegModel.setGrpHdrMsgId(webOpsFileRegModel.getGrpHdrMsgId());
    opsFileRegModel.setExtractMsgId(webOpsFileRegModel.getExtractMsgId());
    opsFileRegModel.setInOutInd(webOpsFileRegModel.getInOutInd());
    opsFileRegModel.setOnlineInd(webOpsFileRegModel.getOnlineInd());


    return opsFileRegModel;

  }


  public WebSystemParameterModel translateCommonsSystemParametersModelToWebModel(
      SystemParameterModel systemParameterCommonModel) {
    WebSystemParameterModel webSystemParametersModel = new WebSystemParameterModel();

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    webSystemParametersModel.setSysName(systemParameterCommonModel.getSysName());
    webSystemParametersModel.setDefCurr(systemParameterCommonModel.getDefCurr());
    webSystemParametersModel.setActiveInd(systemParameterCommonModel.getActiveInd());
    webSystemParametersModel.setInactiveDuration(systemParameterCommonModel.getInactiveDuration());
    ;
    webSystemParametersModel.setSysType(systemParameterCommonModel.getSysType());
    webSystemParametersModel.setSodRunInd(systemParameterCommonModel.getSodRunInd());
    webSystemParametersModel.setEodRunInd(systemParameterCommonModel.getEodRunInd());
    webSystemParametersModel.setCisDwnldInd(systemParameterCommonModel.getCisDwnldInd());
    webSystemParametersModel.setCisDwnldDate(systemParameterCommonModel.getCisDwnldDate());
    webSystemParametersModel.setProcessDate(systemParameterCommonModel.getProcessDate());

    if (systemParameterCommonModel.getCisDwnldDate() != null) {
      String strCisDate = sdf.format(systemParameterCommonModel.getCisDwnldDate());
      webSystemParametersModel.setStrCisDownloadDate(strCisDate);
    }


    if (systemParameterCommonModel.getProcessDate() != null) {
      String strProcDate = sdf.format(systemParameterCommonModel.getProcessDate());
      webSystemParametersModel.setStrProcessDate(strProcDate);
    }

    webSystemParametersModel.setNextMondayHolInd(systemParameterCommonModel.getNextMondayHolInd());
    webSystemParametersModel.setEasterDaysInd(systemParameterCommonModel.getEasterDaysInd());
    webSystemParametersModel.setArchivePeriod(systemParameterCommonModel.getArchivePeriod());
    webSystemParametersModel.setForcecloseReason(systemParameterCommonModel.getForcecloseReason());
    webSystemParametersModel.setInBalanceInd(systemParameterCommonModel.getInBalanceInd());
    webSystemParametersModel.setSysCloseRunInd(systemParameterCommonModel.getSysCloseRunInd());
    webSystemParametersModel.setResponsePeriod(systemParameterCommonModel.getResponsePeriod());
    webSystemParametersModel.setIamPort(systemParameterCommonModel.getIamPort());


    return webSystemParametersModel;
  }


  public SystemParameterModel translateWebSystemParametersModelToSystemParametersCommonsModel(
      WebSystemParameterModel webSystemParametersModel) {
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

    SystemParameterModel systemParametersModel = new SystemParameterModel();

    systemParametersModel.setSysName(webSystemParametersModel.getSysName());
    systemParametersModel.setDefCurr(webSystemParametersModel.getDefCurr());
    systemParametersModel.setActiveInd(webSystemParametersModel.getActiveInd());
    systemParametersModel.setInactiveDuration(webSystemParametersModel.getInactiveDuration());
    ;
    systemParametersModel.setSysType(webSystemParametersModel.getSysType());
    systemParametersModel.setSodRunInd(webSystemParametersModel.getSodRunInd());
    systemParametersModel.setEodRunInd(webSystemParametersModel.getEodRunInd());
    systemParametersModel.setCisDwnldInd(webSystemParametersModel.getCisDwnldInd());

//		try
//		{
//			Date cisDate = sdf.parse(webSystemParametersModel.getCisDwnldDate());
//			Date processDate = sdf.parse(webSystemParametersModel.getProcessDate());
//			systemParametersModel.setCisDwnldDate(cisDate);
//			systemParametersModel.setProcessDate(processDate);
//		}
//		catch(ParseException pe)
//		{
//			System.out.println("Could not convert date: "+pe.getMessage());
//			pe.printStackTrace();
//		}

    systemParametersModel.setCisDwnldDate(webSystemParametersModel.getCisDwnldDate());
    systemParametersModel.setProcessDate(webSystemParametersModel.getProcessDate());
    systemParametersModel.setNextMondayHolInd(webSystemParametersModel.getNextMondayHolInd());
    systemParametersModel.setEasterDaysInd(webSystemParametersModel.getEasterDaysInd());
    systemParametersModel.setArchivePeriod(webSystemParametersModel.getArchivePeriod());
    systemParametersModel.setForcecloseReason(webSystemParametersModel.getForcecloseReason());
    systemParametersModel.setInBalanceInd(webSystemParametersModel.getInBalanceInd());
    systemParametersModel.setSysCloseRunInd(webSystemParametersModel.getSysCloseRunInd());
    systemParametersModel.setResponsePeriod(webSystemParametersModel.getResponsePeriod());
    systemParametersModel.setIamPort(webSystemParametersModel.getIamPort());

    return systemParametersModel;
  }

  public WebConfgSeverityCodesModel translateCommonsConfgSeverityCodesModelToWebModel(
      ConfgSeverityCodesModel confgSeverityCodesModel) {


    WebConfgSeverityCodesModel webConfgSeverityCodesModel = new WebConfgSeverityCodesModel();

    webConfgSeverityCodesModel.setSeverityCode(confgSeverityCodesModel
        .getSeverityCode());
    webConfgSeverityCodesModel.setSeverityCodeDesc(confgSeverityCodesModel
        .getSeverityCodeDesc());
    webConfgSeverityCodesModel.setAction(confgSeverityCodesModel
        .getAction());
    webConfgSeverityCodesModel.setCreatedBy(confgSeverityCodesModel
        .getCreatedBy());
    webConfgSeverityCodesModel.setCreatedDate(confgSeverityCodesModel
        .getCreatedDate());
    webConfgSeverityCodesModel.setModifiedBy(confgSeverityCodesModel
        .getModifiedBy());
    webConfgSeverityCodesModel.setModifiedBy(confgSeverityCodesModel
        .getModifiedBy());

    return webConfgSeverityCodesModel;
  }

  public ConfgSeverityCodesModel translateWebConfgSeverityCodesModelToCommonsModel(
      WebConfgSeverityCodesModel webConfgSeverityCodesModel) {
    ConfgSeverityCodesModel confgSeverityCodesModel = new ConfgSeverityCodesModel();

    confgSeverityCodesModel.setSeverityCode(webConfgSeverityCodesModel
        .getSeverityCode());
    confgSeverityCodesModel.setSeverityCodeDesc(webConfgSeverityCodesModel
        .getSeverityCodeDesc());
    confgSeverityCodesModel.setAction(webConfgSeverityCodesModel
        .getAction());
    confgSeverityCodesModel.setCreatedBy(webConfgSeverityCodesModel
        .getCreatedBy());
    confgSeverityCodesModel.setCreatedDate(webConfgSeverityCodesModel
        .getCreatedDate());
    confgSeverityCodesModel.setModifiedBy(webConfgSeverityCodesModel
        .getModifiedBy());
    confgSeverityCodesModel.setModifiedBy(webConfgSeverityCodesModel
        .getModifiedBy());

    return confgSeverityCodesModel;

  }

  public WebSchedulerModel translateCommonsSchedulerModelToWebModel(
      SchedulerModel schedulerModel) {

    WebSchedulerModel webSchedulerModel = new WebSchedulerModel();

    webSchedulerModel.setCron(schedulerModel.getCron());
    webSchedulerModel.setLastExecutTime(schedulerModel.getLastExecutTime());
    webSchedulerModel.setNextExecutTime(schedulerModel.getNextExecutTime());
    webSchedulerModel.setScheduler(schedulerModel.getScheduler());
    webSchedulerModel.setStatus(schedulerModel.getStatus());

    return webSchedulerModel;
  }

  public WebCountryCodesModel translateCurrencyCodesToCountryModel(
      CurrencyCodesModel currencyCodesModel) {
    WebCountryCodesModel webCountryCodesModel = new WebCountryCodesModel();

    webCountryCodesModel.setCountryCode(currencyCodesModel.getCountryCode());
    webCountryCodesModel.setActiveInd(currencyCodesModel.getActiveInd());

    return webCountryCodesModel;
  }

  public WebReportsNamesModel translateCommonsReportNamesToWebModel(
      ReportsNamesModel reportsNamesModel) {
    WebReportsNamesModel webReportsNamesModel = new WebReportsNamesModel();

    webReportsNamesModel.setActiveInd(reportsNamesModel.getActiveInd());
    webReportsNamesModel.setReportName(reportsNamesModel.getReportName());
    webReportsNamesModel.setReportNr(reportsNamesModel.getReportNr());
    webReportsNamesModel.setInternalInd(reportsNamesModel.getInternalInd());
    webReportsNamesModel.setCreatedBy(reportsNamesModel.getCreatedBy());
    webReportsNamesModel.setCreatedDate(reportsNamesModel.getCreatedDate());
    webReportsNamesModel.setModifiedBy(reportsNamesModel.getModifiedBy());
    webReportsNamesModel.setModifiedDate(reportsNamesModel.getModifiedDate());

    return webReportsNamesModel;
  }

  public ReportsNamesModel translateWebReportNamesToCommonsModel(
      WebReportsNamesModel webReportsNamesModel) {
    ReportsNamesModel reportsNamesModel = new ReportsNamesModel();

    reportsNamesModel.setActiveInd(webReportsNamesModel.getActiveInd());
    reportsNamesModel.setReportName(webReportsNamesModel.getReportName());
    reportsNamesModel.setReportNr(webReportsNamesModel.getReportNr());
    reportsNamesModel.setInternalInd(webReportsNamesModel.getInternalInd());
    reportsNamesModel.setCreatedBy(webReportsNamesModel.getCreatedBy());
    reportsNamesModel.setCreatedDate(webReportsNamesModel.getCreatedDate());
    reportsNamesModel.setModifiedBy(webReportsNamesModel.getModifiedBy());
    reportsNamesModel.setModifiedDate(webReportsNamesModel.getModifiedDate());


    return reportsNamesModel;
  }

  public WebComParamModel translateCommonsCompParamModelToWebModel(CompParamModel compParamModel) {
    WebComParamModel webCompParamModel = new WebComParamModel();

    compParamModel.setCompId(webCompParamModel.getCompId());
    compParamModel.setCompName(webCompParamModel.getCompName());
    compParamModel.setCompAbbrevName(webCompParamModel.getCompAbbrevName());
    compParamModel.setRegistrationNr(webCompParamModel.getRegistrationNr());
    compParamModel.setActiveInd(webCompParamModel.getActiveInd());
    compParamModel.setAddress1(webCompParamModel.getAddress1());
    compParamModel.setAddress2(webCompParamModel.getAddress2());
    compParamModel.setCity(webCompParamModel.getCity());
    compParamModel.setPostCode(webCompParamModel.getPostCode());
    compParamModel.setDialCode(webCompParamModel.getDialCode());
    compParamModel.setTelNr(webCompParamModel.getTelNr());
    compParamModel.setFaxCode(webCompParamModel.getFaxNr());
    compParamModel.setEmail(webCompParamModel.getEmail());
    compParamModel.setContactName(webCompParamModel.getContactName());
    compParamModel.setLastFileNr(webCompParamModel.getLastFileNr());
    compParamModel.setTransamissionInd(webCompParamModel.getTransamissionInd());
    compParamModel.setBicCode(webCompParamModel.getBicCode());
    compParamModel.setAchId(webCompParamModel.getAchId());

    return webCompParamModel;

  }

  public CompParamModel translateWebCompParamModelToCommonsModel(
      WebComParamModel webCompParamModel) {
    CompParamModel compParamModel = new CompParamModel();

    webCompParamModel.setCompId(compParamModel.getCompId());
    webCompParamModel.setCompName(compParamModel.getCompName());
    webCompParamModel.setCompAbbrevName(compParamModel.getCompAbbrevName());
    webCompParamModel.setRegistrationNr(compParamModel.getRegistrationNr());
    webCompParamModel.setActiveInd(compParamModel.getActiveInd());
    webCompParamModel.setAddress1(compParamModel.getAddress1());
    webCompParamModel.setAddress2(compParamModel.getAddress2());
    webCompParamModel.setCity(compParamModel.getCity());
    webCompParamModel.setPostCode(compParamModel.getPostCode());
    webCompParamModel.setDialCode(compParamModel.getDialCode());
    webCompParamModel.setTelNr(compParamModel.getTelNr());
    webCompParamModel.setFaxCode(compParamModel.getFaxNr());
    webCompParamModel.setEmail(compParamModel.getEmail());
    webCompParamModel.setContactName(compParamModel.getContactName());
    webCompParamModel.setLastFileNr(compParamModel.getLastFileNr());
    webCompParamModel.setTransamissionInd(compParamModel.getTransamissionInd());
    webCompParamModel.setBicCode(compParamModel.getBicCode());
    webCompParamModel.setAchId(compParamModel.getAchId());

    return compParamModel;
  }

  public WebMandateModel translateCommonsModelToWebModel(MandatesModel mandatesModel) {

    WebMandateModel webmandatesModel = new WebMandateModel();

    mandatesModel.setActiveInd(webmandatesModel.getActiveInd());
    mandatesModel.setCollAmtCurrency(webmandatesModel.getCollAmtCurrency());
    mandatesModel.setCollectionAmt(webmandatesModel.getCollectionAmt());
    mandatesModel.setContractNo(webmandatesModel.getContractNo());
    mandatesModel.setCreatedBy(webmandatesModel.getCreatedBy());
    mandatesModel.setCreatedDate(webmandatesModel.getCreatedDate());
    mandatesModel.setCreditorAccNo(webmandatesModel.getCreditorAccNo());
    mandatesModel.setCreditorBank(webmandatesModel.getCreditorBank());
    mandatesModel.setCreditorBranchNo(webmandatesModel.getCreditorBranchNo());
    mandatesModel.setCreditorContactDetail(webmandatesModel.getCreditorContactDetail());
    mandatesModel.setCreditorId(webmandatesModel.getCreditorId());
    mandatesModel.setCreditorName(webmandatesModel.getCreditorName());
    mandatesModel.setCreditorSchemeName(webmandatesModel.getCreditorSchemeName());
    mandatesModel.setDebtorAccName(webmandatesModel.getDebtorAccName());
    mandatesModel.setDebtorAccNo(webmandatesModel.getDebtorAccNo());
    mandatesModel.setDebtorBank(webmandatesModel.getDebtorBank());
    mandatesModel.setDebtorBranchNo(webmandatesModel.getDebtorBranchNo());
    mandatesModel.setDebtorContactDetail(webmandatesModel.getDebtorContactDetail());
    mandatesModel.setDebtorIdNo(webmandatesModel.getDebtorIdNo());
    mandatesModel.setDebtorName(webmandatesModel.getDebtorName());
    mandatesModel.setFinalCollectionDate(webmandatesModel.getFinalCollectionDate());
    mandatesModel.setFirstCollectionDate(webmandatesModel.getFirstCollectionDate());
    mandatesModel.setFrequencyCode(webmandatesModel.getFrequencyCode());
    mandatesModel.setFromDate(webmandatesModel.getFromDate());
    mandatesModel.setLocalInstrumentCode(webmandatesModel.getLocalInstrumentCode());
    mandatesModel.setMandateId(webmandatesModel.getMandateId());
    mandatesModel.setMandateReqId(webmandatesModel.getMandateReqId());
    mandatesModel.setMaxAmountCurrency(webmandatesModel.getMaxAmountCurrency());
    mandatesModel.setMaximumAmt(webmandatesModel.getMaximumAmt());
    mandatesModel.setModifiedBy(webmandatesModel.getModifiedBy());
    mandatesModel.setModifiedDate(webmandatesModel.getModifiedDate());
    mandatesModel.setModReason(webmandatesModel.getModReason());
    mandatesModel.setMsgId(webmandatesModel.getMsgId());
    mandatesModel.setProcessStatus(webmandatesModel.getProcessStatus());
    mandatesModel.setSequenceType(webmandatesModel.getSequenceType());
    mandatesModel.setToDate(webmandatesModel.getToDate());
    mandatesModel.setUltimateCreditorContactDetail(
        webmandatesModel.getUltimateCreditorContactDetail());
    mandatesModel.setUltimateCreditorName(webmandatesModel.getUltimateCreditorName());
    mandatesModel.setUltimateDebtorContactDetail(webmandatesModel.getUltimateDebtorContactDetail());
    mandatesModel.setUltimateDebtorName(webmandatesModel.getUltimateDebtorName());

    return webmandatesModel;
  }

  public MandatesModel translateWebmandateModelToCommonsModel(WebMandateModel webmodel) {
    MandatesModel mandatesModel = new MandatesModel();

    webmodel.setActiveInd(mandatesModel.getActiveInd());
    webmodel.setCollAmtCurrency(mandatesModel.getCollAmtCurrency());
    webmodel.setCollectionAmt(mandatesModel.getCollectionAmt());
    webmodel.setContractNo(mandatesModel.getContractNo());
    webmodel.setCreatedBy(mandatesModel.getCreatedBy());
    webmodel.setCreatedDate(mandatesModel.getCreatedDate());
    webmodel.setCreditorAccNo(mandatesModel.getCreditorAccNo());
    webmodel.setCreditorBank(mandatesModel.getCreditorBank());
    webmodel.setCreditorBranchNo(mandatesModel.getCreditorBranchNo());
    webmodel.setCreditorContactDetail(mandatesModel.getCreditorContactDetail());
    webmodel.setCreditorId(mandatesModel.getCreditorId());
    webmodel.setCreditorName(mandatesModel.getCreditorName());
    webmodel.setCreditorSchemeName(mandatesModel.getCreditorSchemeName());
    webmodel.setDebtorAccName(mandatesModel.getDebtorAccName());
    webmodel.setDebtorAccNo(mandatesModel.getDebtorAccNo());
    webmodel.setDebtorBank(mandatesModel.getDebtorBank());
    webmodel.setDebtorBranchNo(mandatesModel.getDebtorBranchNo());
    webmodel.setDebtorContactDetail(mandatesModel.getDebtorContactDetail());
    webmodel.setDebtorIdNo(mandatesModel.getDebtorIdNo());
    webmodel.setDebtorName(mandatesModel.getDebtorName());
    webmodel.setFinalCollectionDate(mandatesModel.getFinalCollectionDate());
    webmodel.setFirstCollectionDate(mandatesModel.getFirstCollectionDate());
    webmodel.setFrequencyCode(mandatesModel.getFrequencyCode());
    webmodel.setFromDate(mandatesModel.getFromDate());
    webmodel.setLocalInstrumentCode(mandatesModel.getLocalInstrumentCode());
    webmodel.setMandateId(mandatesModel.getMandateId());
    webmodel.setMandateReqId(mandatesModel.getMandateReqId());
    webmodel.setMaxAmountCurrency(mandatesModel.getMaxAmountCurrency());
    webmodel.setMaximumAmt(mandatesModel.getMaximumAmt());
    webmodel.setModifiedBy(mandatesModel.getModifiedBy());
    webmodel.setModifiedDate(mandatesModel.getModifiedDate());
    webmodel.setModReason(mandatesModel.getModReason());
    webmodel.setMsgId(mandatesModel.getMsgId());
    webmodel.setProcessStatus(mandatesModel.getProcessStatus());
    webmodel.setSequenceType(mandatesModel.getSequenceType());
    webmodel.setToDate(mandatesModel.getToDate());
    webmodel.setUltimateCreditorContactDetail(mandatesModel.getUltimateCreditorContactDetail());
    webmodel.setUltimateCreditorName(mandatesModel.getUltimateCreditorName());
    webmodel.setUltimateDebtorContactDetail(mandatesModel.getUltimateDebtorContactDetail());
    webmodel.setUltimateDebtorName(mandatesModel.getUltimateDebtorName());

    return mandatesModel;

  }


  public WebRejectReasonCodesModel translateCommonsRejectReasonCodesToWebModel(
      CnfgRejectReasonCodesModel rejectReasonCodesModel) {
    WebRejectReasonCodesModel webRejectReasonCodesModel = new WebRejectReasonCodesModel();
    webRejectReasonCodesModel.setActiveInd(rejectReasonCodesModel.getActiveInd());
    webRejectReasonCodesModel.setCreatedBy(rejectReasonCodesModel.getCreatedBy());
    webRejectReasonCodesModel.setCreatedDate(rejectReasonCodesModel.getCreatedDate());
    webRejectReasonCodesModel.setModifiedBy(rejectReasonCodesModel.getModifiedBy());
    webRejectReasonCodesModel.setModifiedDate(rejectReasonCodesModel.getModifiedDate());
    webRejectReasonCodesModel.setRejectReasonCode(rejectReasonCodesModel.getRejectReasonCode());
    webRejectReasonCodesModel.setRejectReasonDesc(rejectReasonCodesModel.getRejectReasonDesc());

    return webRejectReasonCodesModel;
  }

  public CnfgRejectReasonCodesModel translateWebRejectReasonCodesModelToCommonsModel(
      WebRejectReasonCodesModel webRejectReasonCodesModel) {
    CnfgRejectReasonCodesModel rejectReasonCodesModel = new CnfgRejectReasonCodesModel();

    rejectReasonCodesModel.setActiveInd(webRejectReasonCodesModel.getActiveInd());
    rejectReasonCodesModel.setCreatedBy(webRejectReasonCodesModel.getCreatedBy());
    rejectReasonCodesModel.setCreatedDate(webRejectReasonCodesModel.getCreatedDate());
    rejectReasonCodesModel.setModifiedBy(webRejectReasonCodesModel.getModifiedBy());
    rejectReasonCodesModel.setModifiedDate(webRejectReasonCodesModel.getModifiedDate());
    rejectReasonCodesModel.setRejectReasonCode(webRejectReasonCodesModel.getRejectReasonCode());
    rejectReasonCodesModel.setRejectReasonDesc(webRejectReasonCodesModel.getRejectReasonDesc());

    return rejectReasonCodesModel;
  }

  public AuditTrackingModel translateWebAuditTrackingModelToCommonsModel(
      WebAuditTrackingModel webAuditTrackingModel) {
    AuditTrackingModel auditTrackingModel = new AuditTrackingModel();

    auditTrackingModel.setAction(webAuditTrackingModel.getAction());
    auditTrackingModel.setActionDate(webAuditTrackingModel.getActionDate());
    auditTrackingModel.setColumnName(webAuditTrackingModel.getColumnName());
    auditTrackingModel.setNewValue(webAuditTrackingModel.getNewValue());
    auditTrackingModel.setOldValue(webAuditTrackingModel.getOldValue());
    auditTrackingModel.setRecord(webAuditTrackingModel.getRecord());
    auditTrackingModel.setRecordId(webAuditTrackingModel.getRecordId());
    auditTrackingModel.setTableName(webAuditTrackingModel.getTableName());
    auditTrackingModel.setUserId(webAuditTrackingModel.getUserId());
    auditTrackingModel.setActionDate(webAuditTrackingModel.getActionDate());

    return auditTrackingModel;
  }

  public WebAuditTrackingModel translateCommonsAuditTrackingModelToWebModel(
      AuditTrackingModel auditTrackingModel) {

    WebAuditTrackingModel webAuditTrackingModel = new WebAuditTrackingModel();

    webAuditTrackingModel.setAction(auditTrackingModel.getAction());
    webAuditTrackingModel.setActionDate(auditTrackingModel.getActionDate());
    webAuditTrackingModel.setColumnName(auditTrackingModel.getColumnName());
    webAuditTrackingModel.setNewValue(auditTrackingModel.getNewValue());
    webAuditTrackingModel.setOldValue(auditTrackingModel.getOldValue());
    webAuditTrackingModel.setRecord(auditTrackingModel.getRecord());
    webAuditTrackingModel.setRecordId(auditTrackingModel.getRecordId());
    webAuditTrackingModel.setTableName(auditTrackingModel.getTableName());
    webAuditTrackingModel.setUserId(auditTrackingModel.getUserId());
    webAuditTrackingModel.setActionDate(auditTrackingModel.getActionDate());

    return webAuditTrackingModel;

  }


  public WebProcessStatusModel translateCommonsProcessStatusModelToWebModel(
      ProcessStatusModel processStatusModel) {

    WebProcessStatusModel webProcessStatusModel = new WebProcessStatusModel();

    webProcessStatusModel.setStatus(processStatusModel.getStatus());
    webProcessStatusModel.setStatusDescription(processStatusModel.getStatusDescription());
    webProcessStatusModel.setActiveInd(processStatusModel.getActiveInd());
    webProcessStatusModel.setCreatedBy(processStatusModel.getCreatedBy());
    webProcessStatusModel.setCreatedDate(processStatusModel.getCreatedDate());
    webProcessStatusModel.setModifiedBy(processStatusModel.getModifiedBy());
    webProcessStatusModel.setModifiedDate(processStatusModel.getModifiedDate());

    return webProcessStatusModel;
  }


  public ProcessStatusModel translateWebProcessStatusModelToFileCommonsModel(
      WebProcessStatusModel webProcessStatusModel) {
    ProcessStatusModel processStatusModel = new ProcessStatusModel();

    processStatusModel.setStatus(webProcessStatusModel.getStatus());
    processStatusModel.setStatusDescription(webProcessStatusModel.getStatusDescription());
    processStatusModel.setActiveInd(webProcessStatusModel.getActiveInd());
    processStatusModel.setCreatedBy(webProcessStatusModel.getCreatedBy());
    processStatusModel.setCreatedDate(webProcessStatusModel.getCreatedDate());
    processStatusModel.setModifiedBy(webProcessStatusModel.getModifiedBy());
    processStatusModel.setModifiedDate(webProcessStatusModel.getModifiedDate());


    return processStatusModel;
  }


  SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd");

  public WebServicesModel translateCommonsServicesModelToWebModel(ServicesModel localCommonModel) {

    WebServicesModel webServicesModel = new WebServicesModel();

    webServicesModel.setRecordId(localCommonModel.getRecordId());
    webServicesModel.setServiceIdOut(localCommonModel.getServiceIdOut());
    webServicesModel.setServiceIdIn(localCommonModel.getServiceIdIn());
    webServicesModel.setServiceIdInDesc(localCommonModel.getServiceIdInDesc());
    webServicesModel.setServiceIdOutDesc(localCommonModel.getServiceIdOutDesc());
    webServicesModel.setProcessDate(localCommonModel.getProcessDate());
    webServicesModel.setActiveInd(localCommonModel.getActiveInd());
    webServicesModel.setCreatedBy(localCommonModel.getCreatedBy());
    webServicesModel.setCreatedDate(localCommonModel.getCreatedDate());
    webServicesModel.setModifiedDate(localCommonModel.getModifiedDate());


    return webServicesModel;
  }

  public ServicesModel translateWebServicesModelToCommonsModel(WebServicesModel webServicesModel) {
    ServicesModel servicesModel = new ServicesModel();

    servicesModel.setRecordId(webServicesModel.getRecordId());
    servicesModel.setServiceIdIn(webServicesModel.getServiceIdIn());
    servicesModel.setServiceIdOut(webServicesModel.getServiceIdOut());
    servicesModel.setServiceIdInDesc(webServicesModel.getServiceIdInDesc());
    servicesModel.setServiceIdOutDesc(webServicesModel.getServiceIdOutDesc());
    servicesModel.setProcessDate(webServicesModel.getProcessDate());
    servicesModel.setActiveInd(webServicesModel.getActiveInd());
    servicesModel.setCreatedBy(webServicesModel.getCreatedBy());
    servicesModel.setCreatedDate(webServicesModel.getCreatedDate());
    servicesModel.setModifiedDate(webServicesModel.getModifiedDate());

    return servicesModel;
  }

  public EntryClassesModel translateWebEntryClassesModelToCommonsModel(
      WebEntryClassesModel webEntryClassesModel) {

    EntryClassesModel entryClassesModel = new EntryClassesModel();

    entryClassesModel.setEntryClass(webEntryClassesModel.getEntryClass());
    entryClassesModel.setEntryClassDesc(webEntryClassesModel.getEntryClassDesc());
    entryClassesModel.setActiveInd(webEntryClassesModel.getActiveInd());
    entryClassesModel.setCreatedBy(webEntryClassesModel.getCreatedBy());
    entryClassesModel.setCreatedDate(webEntryClassesModel.getCreatedDate());
    entryClassesModel.setModifiedBy(webEntryClassesModel.getModifiedBy());
    entryClassesModel.setModifiedDate(webEntryClassesModel.getModifiedDate());

    return entryClassesModel;

  }

  public WebEntryClassesModel translateCommonsEntryClassesModelToWebModel(
      EntryClassesModel entryClassesModel) {

    WebEntryClassesModel webEntryClassesModel = new WebEntryClassesModel();

    webEntryClassesModel.setEntryClass(entryClassesModel.getEntryClass());
    webEntryClassesModel.setEntryClassDesc(entryClassesModel.getEntryClassDesc());
    webEntryClassesModel.setActiveInd(entryClassesModel.getActiveInd());
    webEntryClassesModel.setCreatedBy(entryClassesModel.getCreatedBy());
    webEntryClassesModel.setCreatedDate(entryClassesModel.getCreatedDate());
    webEntryClassesModel.setModifiedBy(entryClassesModel.getModifiedBy());
    webEntryClassesModel.setModifiedDate(entryClassesModel.getModifiedDate());


    return webEntryClassesModel;
  }

  public WebAdjustmentCategoryModel translateCommonsAdjustmentCategoryModelToWebModel(
      AdjustmentCategoryModel adjustmentCategoryModel) {

    WebAdjustmentCategoryModel webAdjustmentCategoryModel = new WebAdjustmentCategoryModel();

    webAdjustmentCategoryModel.setAdjustmentCategory(
        adjustmentCategoryModel.getAdjustmentCategory());
    webAdjustmentCategoryModel.setAdjustmentCategoryDesc(
        adjustmentCategoryModel.getAdjustmentCategoryDesc());
    webAdjustmentCategoryModel.setActiveInd(adjustmentCategoryModel.getActiveInd());
    webAdjustmentCategoryModel.setCreatedBy(adjustmentCategoryModel.getCreatedBy());
    webAdjustmentCategoryModel.setCreatedDate(adjustmentCategoryModel.getCreatedDate());
    webAdjustmentCategoryModel.setModifiedBy(adjustmentCategoryModel.getModifiedBy());
    webAdjustmentCategoryModel.setModifiedDate(adjustmentCategoryModel.getModifiedDate());


    return webAdjustmentCategoryModel;
  }

  public AdjustmentCategoryModel translateWebAdjustmentCategoryModelToCommonsModel(
      WebAdjustmentCategoryModel webAdjustmentCategoryModel) {


    AdjustmentCategoryModel adjustmentCategoryModel = new AdjustmentCategoryModel();

    adjustmentCategoryModel.setAdjustmentCategory(
        webAdjustmentCategoryModel.getAdjustmentCategory());
    adjustmentCategoryModel.setAdjustmentCategoryDesc(
        webAdjustmentCategoryModel.getAdjustmentCategoryDesc());
    adjustmentCategoryModel.setActiveInd(webAdjustmentCategoryModel.getActiveInd());
    adjustmentCategoryModel.setCreatedBy(webAdjustmentCategoryModel.getCreatedBy());
    adjustmentCategoryModel.setCreatedDate(webAdjustmentCategoryModel.getCreatedDate());
    adjustmentCategoryModel.setModifiedBy(webAdjustmentCategoryModel.getModifiedBy());
    adjustmentCategoryModel.setModifiedDate(webAdjustmentCategoryModel.getModifiedDate());

    return adjustmentCategoryModel;
  }


  public MandatesCountCommonsModel translateMandatesCountCommonsModelToMandatesCountCommonsModel(
      WebMandateCountModel webMandateCountModel) {
    MandatesCountCommonsModel mandatesCountCommonsModel = new MandatesCountCommonsModel();


    mandatesCountCommonsModel.setInstId(webMandateCountModel.getInstId());
    mandatesCountCommonsModel.setMsgId(webMandateCountModel.getMsgId());
    mandatesCountCommonsModel.setServiceId(webMandateCountModel.getServiceId());
    mandatesCountCommonsModel.setFileName(webMandateCountModel.getFileName());
    mandatesCountCommonsModel.setIncoming(webMandateCountModel.getIncoming());
    mandatesCountCommonsModel.setNrOfFiles(webMandateCountModel.getNrOfFiles());
    mandatesCountCommonsModel.setNrOfMsgs(webMandateCountModel.getNrOfMsgs());
    mandatesCountCommonsModel.setOutgoing(webMandateCountModel.getOutgoing());
    mandatesCountCommonsModel.setNrMsgsRejected(webMandateCountModel.getNrMsgsRejected());
    mandatesCountCommonsModel.setProcessDate(webMandateCountModel.getProcessDate());
    mandatesCountCommonsModel.setNrMsgsAccepted(webMandateCountModel.getNrMsgsAccepted());
    mandatesCountCommonsModel.setNrMsgsExtracted(webMandateCountModel.getNrMsgsExtracted());

    return mandatesCountCommonsModel;
  }

  public WebMandateCountModel transalateCommonsMandatesCountToReasonWebModel(
      MandatesCountCommonsModel mandatesCountCommonsModel) {
    WebMandateCountModel webMandateCountModel = new WebMandateCountModel();
    webMandateCountModel.setInstId(mandatesCountCommonsModel.getInstId());
    webMandateCountModel.setMsgId(mandatesCountCommonsModel.getMsgId());
    webMandateCountModel.setServiceId(mandatesCountCommonsModel.getServiceId());
    webMandateCountModel.setFileName(mandatesCountCommonsModel.getFileName());
    webMandateCountModel.setIncoming(mandatesCountCommonsModel.getIncoming());
    webMandateCountModel.setNrOfFiles(mandatesCountCommonsModel.getNrOfFiles());
    webMandateCountModel.setNrOfMsgs(mandatesCountCommonsModel.getNrOfMsgs());
    webMandateCountModel.setOutgoing(mandatesCountCommonsModel.getOutgoing());
    webMandateCountModel.setNrMsgsRejected(mandatesCountCommonsModel.getNrMsgsRejected());
    webMandateCountModel.setProcessDate(mandatesCountCommonsModel.getProcessDate());
    webMandateCountModel.setNrMsgsAccepted(mandatesCountCommonsModel.getNrMsgsAccepted());
    webMandateCountModel.setNrMsgsExtracted(mandatesCountCommonsModel.getNrMsgsExtracted());
    return webMandateCountModel;


  }


  public WebOpsStatusHdrsModel translateCommonsOpsStatusHdrsModelToWebOpsStatusHrdsModel(
      OpsStatusHdrsModel opsStatusHdrsModel) {
    WebOpsStatusHdrsModel webOpsStatusHdrsModel = new WebOpsStatusHdrsModel();

    webOpsStatusHdrsModel.setSystemSeqNo(opsStatusHdrsModel.getSystemSeqNo());
    webOpsStatusHdrsModel.setHdrMsgId(opsStatusHdrsModel.getHdrMsgId());
    webOpsStatusHdrsModel.setCreateDateTime(opsStatusHdrsModel.getCreateDateTime());
    webOpsStatusHdrsModel.setInstdAgt(opsStatusHdrsModel.getInstdAgt());
    webOpsStatusHdrsModel.setInstgAgt(opsStatusHdrsModel.getInstgAgt());
    webOpsStatusHdrsModel.setOrgnlMsgId(opsStatusHdrsModel.getOrgnlMsgId());
    webOpsStatusHdrsModel.setOrgnlMsgName(opsStatusHdrsModel.getOrgnlMsgName());
    webOpsStatusHdrsModel.setOrgnlCntlSum(opsStatusHdrsModel.getOrgnlCntlSum());
    webOpsStatusHdrsModel.setProcessStatus(opsStatusHdrsModel.getProcessStatus());
    webOpsStatusHdrsModel.setGroupStatus(opsStatusHdrsModel.getGroupStatus());
    webOpsStatusHdrsModel.setService(opsStatusHdrsModel.getService());
    webOpsStatusHdrsModel.setVetRunNumber(opsStatusHdrsModel.getVetRunNumber());
    webOpsStatusHdrsModel.setOrgnlCreateDateTime(opsStatusHdrsModel.getOrgnlCreateDateTime());
    webOpsStatusHdrsModel.setOrgnlNoOfTxns(opsStatusHdrsModel.getOrgnlNoOfTxns());
    webOpsStatusHdrsModel.setWorkunitRefNo(opsStatusHdrsModel.getWorkunitRefNo());

    return webOpsStatusHdrsModel;
  }

  public OpsStatusHdrsModel translateWebOpsStatusHdrsModelToCommonsOpsStatusHdrsModel(
      WebOpsStatusHdrsModel webOpsStatusHdrsModel) {
    OpsStatusHdrsModel opsStatusHdrsModel = new OpsStatusHdrsModel();

    opsStatusHdrsModel.setSystemSeqNo(webOpsStatusHdrsModel.getSystemSeqNo());
    opsStatusHdrsModel.setHdrMsgId(webOpsStatusHdrsModel.getHdrMsgId());
    opsStatusHdrsModel.setCreateDateTime(webOpsStatusHdrsModel.getCreateDateTime());
    opsStatusHdrsModel.setInstdAgt(webOpsStatusHdrsModel.getInstdAgt());
    opsStatusHdrsModel.setInstgAgt(webOpsStatusHdrsModel.getInstgAgt());
    opsStatusHdrsModel.setOrgnlMsgId(webOpsStatusHdrsModel.getOrgnlMsgId());
    opsStatusHdrsModel.setOrgnlMsgName(webOpsStatusHdrsModel.getOrgnlMsgName());
    opsStatusHdrsModel.setOrgnlCntlSum(webOpsStatusHdrsModel.getOrgnlCntlSum());
    opsStatusHdrsModel.setProcessStatus(webOpsStatusHdrsModel.getProcessStatus());
    opsStatusHdrsModel.setGroupStatus(webOpsStatusHdrsModel.getGroupStatus());
    opsStatusHdrsModel.setService(webOpsStatusHdrsModel.getService());
    opsStatusHdrsModel.setVetRunNumber(webOpsStatusHdrsModel.getVetRunNumber());
    opsStatusHdrsModel.setWorkunitRefNo(webOpsStatusHdrsModel.getWorkunitRefNo());
    opsStatusHdrsModel.setOrgnlCreateDateTime(webOpsStatusHdrsModel.getOrgnlCreateDateTime());
    opsStatusHdrsModel.setOrgnlNoOfTxns(webOpsStatusHdrsModel.getOrgnlNoOfTxns());

    return opsStatusHdrsModel;


  }

  public WebOpsStatusDetailsModel translateCommonsOpsStatusDetailsModelToWebOpsStatusDetailsModel(
      OpsStatusDetailsModel opsStatusDetailsModel) {
    WebOpsStatusDetailsModel webOpsStatusDetailsModel = new WebOpsStatusDetailsModel();

    webOpsStatusDetailsModel.setSystemSeqNo(opsStatusDetailsModel.getSystemSeqNo());
    webOpsStatusDetailsModel.setStatusHdrSeqNo(opsStatusDetailsModel.getStatusHdrSeqNo());
    webOpsStatusDetailsModel.setErrorCode(opsStatusDetailsModel.getErrorCode());
    webOpsStatusDetailsModel.setTxnId(opsStatusDetailsModel.getTxnId());
    webOpsStatusDetailsModel.setEndToEndId(opsStatusDetailsModel.getEndToEndId());
    webOpsStatusDetailsModel.setTxnStatus(opsStatusDetailsModel.getTxnStatus());
    webOpsStatusDetailsModel.setErrorType(opsStatusDetailsModel.getErrorType());
    webOpsStatusDetailsModel.setRecordId(opsStatusDetailsModel.getRecordId());
    webOpsStatusDetailsModel.setOrgnlTxnSeqNo(opsStatusDetailsModel.getOrgnlTxnSeqNo());
    webOpsStatusDetailsModel.setMandateRefNumber(opsStatusDetailsModel.getMandateRefNumber());
    webOpsStatusDetailsModel.setInstId(opsStatusDetailsModel.getInstId());
    webOpsStatusDetailsModel.setProcessStatus(opsStatusDetailsModel.getProcessStatus());

    return webOpsStatusDetailsModel;
  }

  public OpsStatusDetailsModel translateWebOpsStatusDetailsModelToCommonsOpsStatusDetailsModel(
      WebOpsStatusDetailsModel webOpsStatusDetailsModel) {
    OpsStatusDetailsModel opsStatusDetailsModel = new OpsStatusDetailsModel();

    opsStatusDetailsModel.setSystemSeqNo(webOpsStatusDetailsModel.getSystemSeqNo());
    opsStatusDetailsModel.setStatusHdrSeqNo(webOpsStatusDetailsModel.getStatusHdrSeqNo());
    opsStatusDetailsModel.setErrorCode(webOpsStatusDetailsModel.getErrorCode());
    opsStatusDetailsModel.setTxnId(webOpsStatusDetailsModel.getTxnId());
    opsStatusDetailsModel.setEndToEndId(webOpsStatusDetailsModel.getEndToEndId());
    opsStatusDetailsModel.setTxnStatus(webOpsStatusDetailsModel.getTxnStatus());
    opsStatusDetailsModel.setErrorType(webOpsStatusDetailsModel.getErrorType());
    opsStatusDetailsModel.setRecordId(webOpsStatusDetailsModel.getRecordId());
    opsStatusDetailsModel.setOrgnlTxnSeqNo(webOpsStatusDetailsModel.getOrgnlTxnSeqNo());
    opsStatusDetailsModel.setMandateRefNumber(webOpsStatusDetailsModel.getMandateRefNumber());
    opsStatusDetailsModel.setInstId(webOpsStatusDetailsModel.getInstId());
    opsStatusDetailsModel.setProcessStatus(webOpsStatusDetailsModel.getProcessStatus());


    return opsStatusDetailsModel;
  }

  public AccountTypeModel translateWebAccountTypeModelToCommonsModel(
      WebAccountTypeModel localWebAccountTypeModel) {

    AccountTypeModel accountTypeModel = new AccountTypeModel();

    accountTypeModel.setAccountTypeCode(localWebAccountTypeModel.getAccountTypeCode());
    accountTypeModel.setAccountTypeDescription(
        localWebAccountTypeModel.getAccountTypeDescription());
    accountTypeModel.setActiveInd(localWebAccountTypeModel.getActiveInd());
    accountTypeModel.setCreatedBy(localWebAccountTypeModel.getCreatedBy());
    accountTypeModel.setCreatedDate(localWebAccountTypeModel.getCreatedDate());
    accountTypeModel.setModifiedBy(localWebAccountTypeModel.getModifiedBy());
    accountTypeModel.setModifiedDate(localWebAccountTypeModel.getModifiedDate());

    return accountTypeModel;
  }


  public WebAccountTypeModel translateCommonsAccountTypeModelToWebModel(
      AccountTypeModel localCommonModel) {

    WebAccountTypeModel webAccountTypeModel = new WebAccountTypeModel();

    webAccountTypeModel.setAccountTypeCode(localCommonModel.getAccountTypeCode());
    webAccountTypeModel.setAccountTypeDescription(localCommonModel.getAccountTypeDescription());
    webAccountTypeModel.setActiveInd(localCommonModel.getActiveInd());
    webAccountTypeModel.setCreatedBy(localCommonModel.getCreatedBy());
    webAccountTypeModel.setCreatedDate(localCommonModel.getCreatedDate());
    webAccountTypeModel.setModifiedBy(localCommonModel.getModifiedBy());
    webAccountTypeModel.setModifiedDate(localCommonModel.getModifiedDate());

    return webAccountTypeModel;

  }

  public SeverityCodesModel translateWebSeverityCodesModelToCommonsModel(
      WebSeverityCodesModel localWebSeverityCodesModel) {

    SeverityCodesModel severityCodesModel = new SeverityCodesModel();

    severityCodesModel.setSeverityCode(Short.valueOf(localWebSeverityCodesModel.getSeverityCode()));
    severityCodesModel.setSeverityCodeDesc(localWebSeverityCodesModel.getSeverityCodeDesc());
    severityCodesModel.setActiveInd(localWebSeverityCodesModel.getActiveInd());
    severityCodesModel.setCreatedBy(localWebSeverityCodesModel.getCreatedBy());
    severityCodesModel.setCreatedDate(localWebSeverityCodesModel.getCreatedDate());
    severityCodesModel.setModifiedBy(localWebSeverityCodesModel.getModifiedBy());
    severityCodesModel.setModifiedDate(localWebSeverityCodesModel.getModifiedDate());

    return severityCodesModel;
  }


  public WebSeverityCodesModel translateCommonsSeverityCodesModelToWebModel(
      SeverityCodesModel localCommonModel) {

    WebSeverityCodesModel webSeverityCodesModel = new WebSeverityCodesModel();

    webSeverityCodesModel.setSeverityCode(localCommonModel.getSeverityCode());
    webSeverityCodesModel.setSeverityCodeDesc(localCommonModel.getSeverityCodeDesc());
    webSeverityCodesModel.setActiveInd(localCommonModel.getActiveInd());
    webSeverityCodesModel.setCreatedBy(localCommonModel.getCreatedBy());
    webSeverityCodesModel.setCreatedDate(localCommonModel.getCreatedDate());
    webSeverityCodesModel.setModifiedBy(localCommonModel.getModifiedBy());
    webSeverityCodesModel.setModifiedDate(localCommonModel.getModifiedDate());

    return webSeverityCodesModel;
  }

  public WebStatusReasonCodesModel translateCommonsStatusReasonCodesModelToWebModel(
      StatusReasonCodesModel statusReasonCodesModel) {

    WebStatusReasonCodesModel webStatusReasonCodesModel = new WebStatusReasonCodesModel();

    webStatusReasonCodesModel.setStatusReasonCode(statusReasonCodesModel.getStatusReasonCode());
    webStatusReasonCodesModel.setStatusReasonCodeDescription(
        statusReasonCodesModel.getStatusReasonCodeDescription());
    webStatusReasonCodesModel.setActiveInd(statusReasonCodesModel.getActiveInd());
    webStatusReasonCodesModel.setCreatedBy(statusReasonCodesModel.getCreatedBy());
    webStatusReasonCodesModel.setCreatedDate(statusReasonCodesModel.getCreatedDate());
    webStatusReasonCodesModel.setModifiedBy(statusReasonCodesModel.getModifiedBy());
    webStatusReasonCodesModel.setModifiedDate(statusReasonCodesModel.getModifiedDate());

    return webStatusReasonCodesModel;
  }

  public StatusReasonCodesModel translateWebStatusReasonCodesModelToCommonsModel(
      WebStatusReasonCodesModel localWebStatusReasonCodesModel) {

    StatusReasonCodesModel statusReasonCodesModel = new StatusReasonCodesModel();

    statusReasonCodesModel.setStatusReasonCode(
        localWebStatusReasonCodesModel.getStatusReasonCode());
    statusReasonCodesModel.setStatusReasonCodeDescription(
        localWebStatusReasonCodesModel.getStatusReasonCodeDescription());
    statusReasonCodesModel.setActiveInd(localWebStatusReasonCodesModel.getActiveInd());
    statusReasonCodesModel.setCreatedBy(localWebStatusReasonCodesModel.getCreatedBy());
    statusReasonCodesModel.setCreatedDate(localWebStatusReasonCodesModel.getCreatedDate());
    statusReasonCodesModel.setModifiedBy(localWebStatusReasonCodesModel.getModifiedBy());
    statusReasonCodesModel.setModifiedDate(localWebStatusReasonCodesModel.getModifiedDate());

    return statusReasonCodesModel;
  }


  public WebAcOpsTransCtrlMsgModel translateCommonsAcOpsTransCtrlMsgModelWebModel(
      AcOpsTransCtrlMsgModel acOpsTransCtrlMsgModel) {

    WebAcOpsTransCtrlMsgModel webAcOpsTransCtrlMsgModel = new WebAcOpsTransCtrlMsgModel();

    webAcOpsTransCtrlMsgModel.setActiveInd(acOpsTransCtrlMsgModel.getActiveInd());
    webAcOpsTransCtrlMsgModel.setCtrlMsgId(acOpsTransCtrlMsgModel.getCtrlMsgId());
    webAcOpsTransCtrlMsgModel.setMemberIdFm(acOpsTransCtrlMsgModel.getMemberIdFm());
    webAcOpsTransCtrlMsgModel.setMemberIdTo(acOpsTransCtrlMsgModel.getMemberIdTo());
    webAcOpsTransCtrlMsgModel.setMsgType(acOpsTransCtrlMsgModel.getMsgType());
    webAcOpsTransCtrlMsgModel.setNrOfFiles(acOpsTransCtrlMsgModel.getNrOfFiles());
    webAcOpsTransCtrlMsgModel.setNrOfFilesReceived(acOpsTransCtrlMsgModel.getNrOfFilesReceived());
    webAcOpsTransCtrlMsgModel.setNrOfRecords(acOpsTransCtrlMsgModel.getNrOfRecords());
    webAcOpsTransCtrlMsgModel.setNrOfRecordsReceived(
        acOpsTransCtrlMsgModel.getNrOfRecordsReceived());
    webAcOpsTransCtrlMsgModel.setProcessDate(acOpsTransCtrlMsgModel.getProcessDate());
    webAcOpsTransCtrlMsgModel.setServiceId(acOpsTransCtrlMsgModel.getServiceId());
    webAcOpsTransCtrlMsgModel.setSystemStatus(acOpsTransCtrlMsgModel.getSystemStatus());
    webAcOpsTransCtrlMsgModel.setValidRecordsReceived(
        acOpsTransCtrlMsgModel.getValidRecordsReceived());
    webAcOpsTransCtrlMsgModel.setValueOfRecords(acOpsTransCtrlMsgModel.getValueOfRecords());
    webAcOpsTransCtrlMsgModel.setValueOfRecordsCurr(acOpsTransCtrlMsgModel.getValueOfRecordsCurr());


    return webAcOpsTransCtrlMsgModel;
  }

  public AcOpsTransCtrlMsgModel translateWebAcOpsTransCtrlMsgModelToCommonsModel(
      WebAcOpsTransCtrlMsgModel webAcOpsTransCtrlMsgModel) {

    AcOpsTransCtrlMsgModel acOpsTransCtrlMsgModel = new AcOpsTransCtrlMsgModel();


    acOpsTransCtrlMsgModel.setActiveInd(webAcOpsTransCtrlMsgModel.getActiveInd());
    acOpsTransCtrlMsgModel.setCtrlMsgId(webAcOpsTransCtrlMsgModel.getCtrlMsgId());
    acOpsTransCtrlMsgModel.setMemberIdFm(webAcOpsTransCtrlMsgModel.getMemberIdFm());
    acOpsTransCtrlMsgModel.setMemberIdTo(webAcOpsTransCtrlMsgModel.getMemberIdTo());
    acOpsTransCtrlMsgModel.setMsgType(webAcOpsTransCtrlMsgModel.getMsgType());
    acOpsTransCtrlMsgModel.setNrOfFiles(webAcOpsTransCtrlMsgModel.getNrOfFiles());
    acOpsTransCtrlMsgModel.setNrOfFilesReceived(webAcOpsTransCtrlMsgModel.getNrOfFilesReceived());
    acOpsTransCtrlMsgModel.setNrOfRecords(webAcOpsTransCtrlMsgModel.getNrOfRecords());
    acOpsTransCtrlMsgModel.setNrOfRecordsReceived(
        webAcOpsTransCtrlMsgModel.getNrOfRecordsReceived());
    acOpsTransCtrlMsgModel.setProcessDate(webAcOpsTransCtrlMsgModel.getProcessDate());
    acOpsTransCtrlMsgModel.setServiceId(webAcOpsTransCtrlMsgModel.getServiceId());
    acOpsTransCtrlMsgModel.setSystemStatus(webAcOpsTransCtrlMsgModel.getSystemStatus());
    acOpsTransCtrlMsgModel.setValidRecordsReceived(
        webAcOpsTransCtrlMsgModel.getValidRecordsReceived());
    acOpsTransCtrlMsgModel.setValueOfRecords(webAcOpsTransCtrlMsgModel.getValueOfRecords());
    acOpsTransCtrlMsgModel.setValueOfRecordsCurr(webAcOpsTransCtrlMsgModel.getValueOfRecordsCurr());

    return acOpsTransCtrlMsgModel;
  }

  public WebSystemControlServicesModel translateCommonsSystemControlServicesModelToWebModel(
      SystemControlServicesModel systemControlServicesModel) {
    WebSystemControlServicesModel webSystemControlServicesModel =
        new WebSystemControlServicesModel();

    webSystemControlServicesModel.setServiceIdIn(systemControlServicesModel.getServiceIdIn());
    webSystemControlServicesModel.setServiceIdOut(systemControlServicesModel.getServiceIdOut());
    webSystemControlServicesModel.setServiceIdInDesc(
        systemControlServicesModel.getServiceIdInDesc());
    webSystemControlServicesModel.setServiceIdOutDesc(
        systemControlServicesModel.getServiceIdOutDesc());
    webSystemControlServicesModel.setRecordId(systemControlServicesModel.getRecordId());
    webSystemControlServicesModel.setActiveInd(systemControlServicesModel.getActiveInd());
    webSystemControlServicesModel.setCreatedBy(systemControlServicesModel.getCreatedBy());
    webSystemControlServicesModel.setCreatedDate(systemControlServicesModel.getCreatedDate());
    webSystemControlServicesModel.setModifiedBy(systemControlServicesModel.getModifiedBy());
    webSystemControlServicesModel.setModifiedDate(systemControlServicesModel.getModifiedDate());

    return webSystemControlServicesModel;
  }

  public SystemControlServicesModel translateWebSystemControlServicesModelToCommonsModel(
      WebSystemControlServicesModel localWebModel) {
    SystemControlServicesModel systemControlServicesModel = new SystemControlServicesModel();

    systemControlServicesModel.setServiceIdIn(localWebModel.getServiceIdIn());
    systemControlServicesModel.setServiceIdOut(localWebModel.getServiceIdOut());
    systemControlServicesModel.setServiceIdInDesc(localWebModel.getServiceIdInDesc());
    systemControlServicesModel.setServiceIdOutDesc(localWebModel.getServiceIdOutDesc());
    systemControlServicesModel.setRecordId(localWebModel.getRecordId());
    systemControlServicesModel.setActiveInd(localWebModel.getActiveInd());
    systemControlServicesModel.setCreatedBy(localWebModel.getCreatedBy());
    systemControlServicesModel.setCreatedDate(localWebModel.getCreatedDate());
    systemControlServicesModel.setModifiedBy(localWebModel.getModifiedBy());
    systemControlServicesModel.setModifiedDate(localWebModel.getModifiedDate());

    return systemControlServicesModel;
  }

  public WebOpsServicesModel translateCommonsOpsServicesToWebModel(ServicesModel opsServicesModel) {
    WebOpsServicesModel webOpsServicesModel = new WebOpsServicesModel();
    webOpsServicesModel.setCreatedBy(opsServicesModel.getCreatedBy());
    webOpsServicesModel.setCreatedDate(opsServicesModel.getCreatedDate());
    webOpsServicesModel.setModifiedBy(opsServicesModel.getModifiedBy());
    webOpsServicesModel.setModifiedDate(opsServicesModel.getModifiedDate());
    webOpsServicesModel.setRecordId(opsServicesModel.getRecordId());
    webOpsServicesModel.setServiceIdIn(opsServicesModel.getServiceIdIn());
    webOpsServicesModel.setServiceIdInDesc(opsServicesModel.getServiceIdInDesc());
    webOpsServicesModel.setServiceIdOut(opsServicesModel.getServiceIdOut());
    webOpsServicesModel.setServiceIdOutDesc(opsServicesModel.getServiceIdOutDesc());
    webOpsServicesModel.setProcessDate(opsServicesModel.getProcessDate());
    webOpsServicesModel.setProcessStatus(opsServicesModel.getProcessStatus());
    webOpsServicesModel.setServiceIdOutSlaTime(opsServicesModel.getServiceIdOutSlaTime());
    webOpsServicesModel.setActiveInd(opsServicesModel.getActiveInd());
    webOpsServicesModel.setMsgTypeId(opsServicesModel.getMsgTypeId());

    return webOpsServicesModel;

  }

  public ServicesModel translateWebOpsServicesToCommonsModle(
      WebOpsServicesModel webOpsServicesModel) {
    ServicesModel servicesModel = new ServicesModel();

    servicesModel.setCreatedBy(webOpsServicesModel.getCreatedBy());
    servicesModel.setCreatedDate(webOpsServicesModel.getCreatedDate());
    servicesModel.setModifiedBy(webOpsServicesModel.getModifiedBy());
    servicesModel.setModifiedDate(webOpsServicesModel.getModifiedDate());
    servicesModel.setRecordId(webOpsServicesModel.getRecordId());
    servicesModel.setServiceIdIn(webOpsServicesModel.getServiceIdIn());
    servicesModel.setServiceIdInDesc(webOpsServicesModel.getServiceIdInDesc());
    servicesModel.setServiceIdOut(webOpsServicesModel.getServiceIdOut());
    servicesModel.setServiceIdOutDesc(webOpsServicesModel.getServiceIdOutDesc());
    servicesModel.setProcessDate(webOpsServicesModel.getProcessDate());
    servicesModel.setProcessStatus(webOpsServicesModel.getProcessStatus());
    servicesModel.setServiceIdOutSlaTime(webOpsServicesModel.getServiceIdOutSlaTime());
    servicesModel.setActiveInd(webOpsServicesModel.getActiveInd());
    servicesModel.setMsgTypeId(webOpsServicesModel.getMsgTypeId());

    return servicesModel;
  }


  public WebOpsRefSequenceNumber translateOpsRefSeqNumberCommonsModelToWebModel(
      OpsRefSeqNumberCommonsModel opsRefSeqNumberCommonsModel) {
    WebOpsRefSequenceNumber webOpsRefSequenceNumber = new WebOpsRefSequenceNumber();

    webOpsRefSequenceNumber.setCreatedBy(opsRefSeqNumberCommonsModel.getCreatedBy());
    webOpsRefSequenceNumber.setCreatedDate(opsRefSeqNumberCommonsModel.getCreatedDate());
    webOpsRefSequenceNumber.setLastFileNr(opsRefSeqNumberCommonsModel.getLastFileNr());
    webOpsRefSequenceNumber.setLastSeqNr(opsRefSeqNumberCommonsModel.getLastSeqNr());
    webOpsRefSequenceNumber.setMemberNo(opsRefSeqNumberCommonsModel.getMemberNo());
    webOpsRefSequenceNumber.setModifiedBy(opsRefSeqNumberCommonsModel.getModifiedBy());
    webOpsRefSequenceNumber.setModifiedDate(opsRefSeqNumberCommonsModel.getModifiedDate());
    webOpsRefSequenceNumber.setServiceId(opsRefSeqNumberCommonsModel.getServiceId());

    return webOpsRefSequenceNumber;

  }

  public OpsRefSeqNumberCommonsModel translateWebOpsRefSequenceNumberToCommonsModle(
      WebOpsRefSequenceNumber webOpsRefSequenceNumber) {
    OpsRefSeqNumberCommonsModel opsRefSeqNumberCommonsModel = new OpsRefSeqNumberCommonsModel();

    opsRefSeqNumberCommonsModel.setCreatedBy(webOpsRefSequenceNumber.getCreatedBy());
    opsRefSeqNumberCommonsModel.setCreatedDate(webOpsRefSequenceNumber.getCreatedDate());
    opsRefSeqNumberCommonsModel.setLastFileNr(webOpsRefSequenceNumber.getLastFileNr());
    opsRefSeqNumberCommonsModel.setLastSeqNr(webOpsRefSequenceNumber.getLastSeqNr());
    opsRefSeqNumberCommonsModel.setMemberNo(webOpsRefSequenceNumber.getMemberNo());
    opsRefSeqNumberCommonsModel.setModifiedBy(webOpsRefSequenceNumber.getModifiedBy());
    opsRefSeqNumberCommonsModel.setModifiedDate(webOpsRefSequenceNumber.getModifiedDate());
    opsRefSeqNumberCommonsModel.setServiceId(webOpsRefSequenceNumber.getServiceId());
    return opsRefSeqNumberCommonsModel;
  }

  public WebOpsProcessControlsModel translateOpsProcessControlCommonsModelToWebModel(
      OpsProcessControlCommonsModel opsProcessControlCommonsModel) {
    WebOpsProcessControlsModel webOpsProcessControlsModel = new WebOpsProcessControlsModel();
    webOpsProcessControlsModel.setActiveInd(opsProcessControlCommonsModel.getActiveInd());
    webOpsProcessControlsModel.setInstId(opsProcessControlCommonsModel.getInstId());
    webOpsProcessControlsModel.setMaxNrRecords(opsProcessControlCommonsModel.getMaxNrRecords());
    webOpsProcessControlsModel.setNrOfDaysProc(opsProcessControlCommonsModel.getNrOfDaysProc());
    webOpsProcessControlsModel.setProcessDate(opsProcessControlCommonsModel.getProcessDate());
    webOpsProcessControlsModel.setPubHolProc(opsProcessControlCommonsModel.getPubHolProc());


    return webOpsProcessControlsModel;

  }

  public OpsProcessControlCommonsModel translateWebOpsProcessControlsModelToCommonsModel(
      WebOpsProcessControlsModel webOpsProcessControlsModel) {
    OpsProcessControlCommonsModel opsProcessControlCommonsModel =
        new OpsProcessControlCommonsModel();


    opsProcessControlCommonsModel.setActiveInd(webOpsProcessControlsModel.getActiveInd());
    opsProcessControlCommonsModel.setInstId(webOpsProcessControlsModel.getInstId());
    opsProcessControlCommonsModel.setMaxNrRecords(webOpsProcessControlsModel.getMaxNrRecords());
    opsProcessControlCommonsModel.setNrOfDaysProc(webOpsProcessControlsModel.getNrOfDaysProc());
    opsProcessControlCommonsModel.setProcessDate(webOpsProcessControlsModel.getProcessDate());
    opsProcessControlCommonsModel.setPubHolProc(webOpsProcessControlsModel.getPubHolProc());

    return opsProcessControlCommonsModel;
  }


  public WebAcOpsSotEotModel translateOpsProcessControlCommonsModelToWebModel(
      AcOpsSotEotCntrlModel acOpsSotEotCntrlModel) {
    WebAcOpsSotEotModel webAcOpsSotEotModel = new WebAcOpsSotEotModel();

    webAcOpsSotEotModel.setEotIn(acOpsSotEotCntrlModel.getEotIn());
    webAcOpsSotEotModel.setEotOut(acOpsSotEotCntrlModel.getEotOut());
    webAcOpsSotEotModel.setInstId(acOpsSotEotCntrlModel.getInstId());
    webAcOpsSotEotModel.setServiceId(acOpsSotEotCntrlModel.getServiceId());
    webAcOpsSotEotModel.setSotIn(acOpsSotEotCntrlModel.getSotIn());
    webAcOpsSotEotModel.setSotOut(acOpsSotEotCntrlModel.getSotOut());

    return webAcOpsSotEotModel;

  }

  public AcOpsSotEotCntrlModel translateWebAcOpsSotEotModelToCommonsModel(
      WebAcOpsSotEotModel webAcOpsSotEotModel) {
    AcOpsSotEotCntrlModel acOpsSotEotCntrlModel = new AcOpsSotEotCntrlModel();

    acOpsSotEotCntrlModel.setEotIn(webAcOpsSotEotModel.getEotIn());
    acOpsSotEotCntrlModel.setEotOut(webAcOpsSotEotModel.getEotOut());
    acOpsSotEotCntrlModel.setInstId(webAcOpsSotEotModel.getInstId());
    acOpsSotEotCntrlModel.setServiceId(webAcOpsSotEotModel.getServiceId());
    acOpsSotEotCntrlModel.setSotIn(webAcOpsSotEotModel.getSotIn());
    acOpsSotEotCntrlModel.setSotOut(webAcOpsSotEotModel.getSotOut());

    return acOpsSotEotCntrlModel;
  }

  public WebQuartzSchedulerModel translateSchedulerCommonsModelToWebModel(
      SchedulerCommonsModel schedulerCommonsModel) {
    WebQuartzSchedulerModel webQuartzSchedulerModel = new WebQuartzSchedulerModel();
    webQuartzSchedulerModel.setActiveInd(schedulerCommonsModel.getActiveInd());
    webQuartzSchedulerModel.setRescheduleTime(schedulerCommonsModel.getRescheduleTime());
    webQuartzSchedulerModel.setSchedulerKey(schedulerCommonsModel.getSchedulerKey());
    webQuartzSchedulerModel.setSchedulerName(schedulerCommonsModel.getSchedulerName());

    return webQuartzSchedulerModel;

  }

  public SchedulerCommonsModel translateWebQuartzSchedulerModelToCommonsModel(
      WebQuartzSchedulerModel webQuartzSchedulerModel) {
    SchedulerCommonsModel schedulerCommonsModel = new SchedulerCommonsModel();


    schedulerCommonsModel.setActiveInd(webQuartzSchedulerModel.getActiveInd());
    schedulerCommonsModel.setRescheduleTime(webQuartzSchedulerModel.getRescheduleTime());
    schedulerCommonsModel.setSchedulerKey(webQuartzSchedulerModel.getSchedulerKey());
    schedulerCommonsModel.setSchedulerName(webQuartzSchedulerModel.getSchedulerName());

    return schedulerCommonsModel;
  }

  public WebFileStatusModel translateFileStatusCommonsModelToWebModel(
      FileStatusCommonsModel fileStatusCommonsModel) {
    WebFileStatusModel webFileStatusModel = new WebFileStatusModel();
    webFileStatusModel.setActiveInd(fileStatusCommonsModel.getActiveInd());
    webFileStatusModel.setStatus(fileStatusCommonsModel.getStatus());
    webFileStatusModel.setStatusDescription(fileStatusCommonsModel.getStatusDescription());


    return webFileStatusModel;

  }

  public FileStatusCommonsModel translateWebFileStatusModellToCommonsModel(
      WebFileStatusModel webFileStatusModel) {
    FileStatusCommonsModel fileStatusCommonsModel = new FileStatusCommonsModel();


    fileStatusCommonsModel.setActiveInd(webFileStatusModel.getActiveInd());
    fileStatusCommonsModel.setStatus(webFileStatusModel.getStatus());
    fileStatusCommonsModel.setStatusDescription(webFileStatusModel.getStatusDescription());

    return fileStatusCommonsModel;
  }

  public WebOpsCustomerParameters translateOpsCustParamModelToWebModel(
      OpsCustParamModel OpsCustParamModel) {
    WebOpsCustomerParameters webOpsCustomerParameters = new WebOpsCustomerParameters();

    webOpsCustomerParameters.setActiveInd(OpsCustParamModel.getActiveInd());
    webOpsCustomerParameters.setCasaAccpLstSeq(OpsCustParamModel.getCasaAccpLstSeq());
    webOpsCustomerParameters.setInstId(OpsCustParamModel.getInstId());
    webOpsCustomerParameters.setCreatedBy(OpsCustParamModel.getCreatedBy());
    webOpsCustomerParameters.setCreatedDate(OpsCustParamModel.getCreatedDate());
    webOpsCustomerParameters.setCasaAccpLastFileNr(OpsCustParamModel.getCasaAccpLastFileNr());
    webOpsCustomerParameters.setCasaAccpLastFileNr(OpsCustParamModel.getCasaAccpLastFileNr());
    webOpsCustomerParameters.setCasaAccpXsdNs(OpsCustParamModel.getCasaAccpXsdNs());
    webOpsCustomerParameters.setCasaAmdLastFileNr(OpsCustParamModel.getCasaAmdLastFileNr());
    webOpsCustomerParameters.setCasaAmdLstSeq(OpsCustParamModel.getCasaAmdLstSeq());
    webOpsCustomerParameters.setCasaAmdXsdNs(OpsCustParamModel.getCasaAmdXsdNs());
    webOpsCustomerParameters.setCasaStatusRepLastFileNr(OpsCustParamModel.getCasaStatusRepLastFileNr());
    webOpsCustomerParameters.setCasaStatusRepLstSeq(OpsCustParamModel.getCasaStatusRepLstSeq());
    webOpsCustomerParameters.setCasaStatusRepXsdNs(OpsCustParamModel.getCasaStatusRepXsdNs());
    webOpsCustomerParameters.setCasaConfirmXsdNs(OpsCustParamModel.getCasaConfirmXsdNs());
    webOpsCustomerParameters.setCasaConfirmLstSeq(OpsCustParamModel.getCasaConfirmLstSeq());
    webOpsCustomerParameters.setCasaConfirmLstFileNr(OpsCustParamModel.getCasaConfirmLstFileNr());

    return webOpsCustomerParameters;

  }

  public OpsCustParamModel translateWebOpsCustomerParametersToCommonsModel(
      WebOpsCustomerParameters webOpsCustomerParameters) {
    OpsCustParamModel opsCustParamModel = new OpsCustParamModel();


    opsCustParamModel.setActiveInd(webOpsCustomerParameters.getActiveInd());
    opsCustParamModel.setInstId(webOpsCustomerParameters.getInstId());
    opsCustParamModel.setCreatedBy(webOpsCustomerParameters.getCreatedBy());
    opsCustParamModel.setCreatedDate(webOpsCustomerParameters.getCreatedDate());
    opsCustParamModel.setCasaAccpLastFileNr(webOpsCustomerParameters.getCasaAccpLastFileNr());
    opsCustParamModel.setCasaAccpLastFileNr(webOpsCustomerParameters.getCasaAccpLastFileNr());
    opsCustParamModel.setCasaAccpLstSeq(webOpsCustomerParameters.getCasaAccpLstSeq());
    opsCustParamModel.setCasaAccpXsdNs(webOpsCustomerParameters.getCasaAccpXsdNs());
    opsCustParamModel.setCasaAmdLastFileNr(webOpsCustomerParameters.getCasaAmdLastFileNr());
    opsCustParamModel.setCasaAmdLstSeq(webOpsCustomerParameters.getCasaAmdLstSeq());
    opsCustParamModel.setCasaAmdXsdNs(webOpsCustomerParameters.getCasaAmdXsdNs());
    opsCustParamModel.setCasaStatusRepLastFileNr(
        webOpsCustomerParameters.getCasaStatusRepLastFileNr());
    opsCustParamModel.setCasaStatusRepLstSeq(webOpsCustomerParameters.getCasaStatusRepLstSeq());
    opsCustParamModel.setCasaStatusRepXsdNs(webOpsCustomerParameters.getCasaStatusRepXsdNs());
    opsCustParamModel.setCasaConfirmXsdNs(webOpsCustomerParameters.getCasaConfirmXsdNs());
    opsCustParamModel.setCasaConfirmLstSeq(webOpsCustomerParameters.getCasaConfirmLstSeq());
    opsCustParamModel.setCasaConfirmLstFileNr(webOpsCustomerParameters.getCasaConfirmLstFileNr());

    return opsCustParamModel;
  }

//		public WebQuerybyMandateModel translateCommonsQuerybyMandateModelToWebModel
//		(QuerybyMandateModel querybyMandateModel)
//		{
//			WebQuerybyMandateModel webQuerybyMandateModel = new WebQuerybyMandateModel();
//
//			webQuerybyMandateModel.setMandateReqTranId(querybyMandateModel.getMandateReqTranId());
//			webQuerybyMandateModel.setMandateRefNr(querybyMandateModel.getMandateRefNr());
//			webQuerybyMandateModel.setCreditorName(querybyMandateModel.getCreditorName());
//			webQuerybyMandateModel.setDebtorName(querybyMandateModel.getDebtorName());
//			webQuerybyMandateModel.setFirstCollDate(querybyMandateModel.getFirstCollDate());
//			webQuerybyMandateModel.setCollAmount(querybyMandateModel.getCollAmount());
//
//
//			return webQuerybyMandateModel;
//
//		}
//		public QuerybyMandateModel translateWebQuerybyMandateModelToCommonsModel
//		(WebQuerybyMandateModel  webQuerybyMandateModel)
//		{
//			QuerybyMandateModel querybyMandateModel = new QuerybyMandateModel();
//
//			querybyMandateModel.setMandateReqTranId(webQuerybyMandateModel.getMandateReqTranId());
//			querybyMandateModel.setMandateRefNr(webQuerybyMandateModel.getMandateRefNr());
//			querybyMandateModel.setCreditorName(webQuerybyMandateModel.getCreditorName());
//			querybyMandateModel.setDebtorName(webQuerybyMandateModel.getDebtorName());
//			querybyMandateModel.setFirstCollDate(webQuerybyMandateModel.getFirstCollDate());
//			querybyMandateModel.setCollAmount(webQuerybyMandateModel.getCollAmount());
//
//			return querybyMandateModel;
//			
//		}


  public WebOpsCronTimeModel translateOpsCronCommonsModelToWebModel(
      OpsCronTimeModel opsCronTimeModel) {
    WebOpsCronTimeModel webOpsCronTimeModel = new WebOpsCronTimeModel();

    webOpsCronTimeModel.setActiveInd(opsCronTimeModel.getActiveInd());
    webOpsCronTimeModel.setCreatedBy(opsCronTimeModel.getCreatedBy());
    webOpsCronTimeModel.setCreatedDate(opsCronTimeModel.getCreatedDate());
    webOpsCronTimeModel.setCronTime(opsCronTimeModel.getCronTime());
    webOpsCronTimeModel.setModifiedBy(opsCronTimeModel.getModifiedBy());
    webOpsCronTimeModel.setModifiedDate(opsCronTimeModel.getModifiedDate());
    webOpsCronTimeModel.setProcessName(opsCronTimeModel.getProcessName());

    return webOpsCronTimeModel;

  }


  public OpsCronTimeModel translateWebOpsCronTimeModelToCommonsModel(
      WebOpsCronTimeModel webOpsCronTimeModel) {
    OpsCronTimeModel opsCronTimeModel = new OpsCronTimeModel();

    opsCronTimeModel.setActiveInd(webOpsCronTimeModel.getActiveInd());
    opsCronTimeModel.setCreatedBy(webOpsCronTimeModel.getCreatedBy());
    opsCronTimeModel.setCreatedDate(webOpsCronTimeModel.getCreatedDate());
    opsCronTimeModel.setCronTime(webOpsCronTimeModel.getCronTime());
    opsCronTimeModel.setModifiedBy(webOpsCronTimeModel.getModifiedBy());
    opsCronTimeModel.setModifiedDate(webOpsCronTimeModel.getModifiedDate());
    opsCronTimeModel.setProcessName(webOpsCronTimeModel.getProcessName());
    return opsCronTimeModel;
  }


  public WebOpsSlaTimesModel translateOpsSlaTimesCommonsModelToWebModel(
      OpsSlaTimesCommonsModel opsSlaTimesCommonsModel) {
    WebOpsSlaTimesModel webOpsSlaTimesModel = new WebOpsSlaTimesModel();
    webOpsSlaTimesModel.setEndTime(opsSlaTimesCommonsModel.getEndTime());
    webOpsSlaTimesModel.setService(opsSlaTimesCommonsModel.getService());
    webOpsSlaTimesModel.setStartTime(opsSlaTimesCommonsModel.getStartTime());

    return webOpsSlaTimesModel;
  }


  public OpsSlaTimesCommonsModel translateWebOpsSlaTimesModelToCommonsModel(
      WebOpsSlaTimesModel webOpsSlaTimesModel) {

    OpsSlaTimesCommonsModel opsSlaTimesCommonsModel = new OpsSlaTimesCommonsModel();

    opsSlaTimesCommonsModel.setEndTime(webOpsSlaTimesModel.getEndTime());
    opsSlaTimesCommonsModel.setService(webOpsSlaTimesModel.getService());
    opsSlaTimesCommonsModel.setStartTime(webOpsSlaTimesModel.getStartTime());

    return opsSlaTimesCommonsModel;
  }


  public WebTransCtrlMsgModel translateTransCtrlMsgModelToWebModel(
      TransCtrlMsgModel transCtrlMsgModel) {
    WebTransCtrlMsgModel webTransCtrlMsgModel = new WebTransCtrlMsgModel();

    webTransCtrlMsgModel.setActiveInd(transCtrlMsgModel.getActiveInd());
    webTransCtrlMsgModel.setCtrlMsgId(transCtrlMsgModel.getCtrlMsgId());
    webTransCtrlMsgModel.setMemberIdFm(transCtrlMsgModel.getMemberIdFm());
    webTransCtrlMsgModel.setMemberIdTo(transCtrlMsgModel.getMemberIdTo());
    webTransCtrlMsgModel.setMsgType(transCtrlMsgModel.getMsgType());
    webTransCtrlMsgModel.setNrOfFiles(transCtrlMsgModel.getNrOfFiles());
    webTransCtrlMsgModel.setNrOfFilesReceived(transCtrlMsgModel.getNrOfFilesReceived());
    webTransCtrlMsgModel.setNrOfRecords(transCtrlMsgModel.getNrOfRecords());
    webTransCtrlMsgModel.setNrOfRecordsReceived(transCtrlMsgModel.getNrOfRecordsReceived());
    webTransCtrlMsgModel.setProcessDate(transCtrlMsgModel.getProcessDate());
    webTransCtrlMsgModel.setServiceId(transCtrlMsgModel.getServiceId());
    webTransCtrlMsgModel.setSystemStatus(transCtrlMsgModel.getSystemStatus());
    webTransCtrlMsgModel.setValidRecordsReceived(transCtrlMsgModel.getValidRecordsReceived());
    webTransCtrlMsgModel.setValueOfRecords(transCtrlMsgModel.getValueOfRecords());
    webTransCtrlMsgModel.setValueOfRecordsCurr(transCtrlMsgModel.getValueOfRecordsCurr());

    return webTransCtrlMsgModel;

  }


  public TransCtrlMsgModel translateWebTransCtrlMsgModelToCommonsModel(
      WebTransCtrlMsgModel webTransCtrlMsgModel) {
    TransCtrlMsgModel transCtrlMsgModel = new TransCtrlMsgModel();

    transCtrlMsgModel.setActiveInd(webTransCtrlMsgModel.getActiveInd());
    transCtrlMsgModel.setCtrlMsgId(webTransCtrlMsgModel.getCtrlMsgId());
    transCtrlMsgModel.setMemberIdFm(webTransCtrlMsgModel.getMemberIdFm());
    transCtrlMsgModel.setMemberIdTo(webTransCtrlMsgModel.getMemberIdTo());
    transCtrlMsgModel.setMsgType(webTransCtrlMsgModel.getMsgType());
    transCtrlMsgModel.setNrOfFiles(webTransCtrlMsgModel.getNrOfFiles());
    transCtrlMsgModel.setNrOfFilesReceived(webTransCtrlMsgModel.getNrOfFilesReceived());
    transCtrlMsgModel.setNrOfRecords(webTransCtrlMsgModel.getNrOfRecords());
    transCtrlMsgModel.setNrOfRecordsReceived(webTransCtrlMsgModel.getNrOfRecordsReceived());
    transCtrlMsgModel.setProcessDate(webTransCtrlMsgModel.getProcessDate());
    transCtrlMsgModel.setServiceId(webTransCtrlMsgModel.getServiceId());
    transCtrlMsgModel.setSystemStatus(webTransCtrlMsgModel.getSystemStatus());
    transCtrlMsgModel.setValidRecordsReceived(webTransCtrlMsgModel.getValidRecordsReceived());
    transCtrlMsgModel.setValueOfRecords(webTransCtrlMsgModel.getValueOfRecords());
    transCtrlMsgModel.setValueOfRecordsCurr(webTransCtrlMsgModel.getValueOfRecordsCurr());

    return transCtrlMsgModel;
  }


  public static WebSysCisBankModel translateCommonsSysCisBankModelToWebModel(
      SysCisBankModel sysCisBankModel) {
    WebSysCisBankModel webSysCisBankModel = new WebSysCisBankModel();

    webSysCisBankModel.setMemberName(sysCisBankModel.getMemberName());
    webSysCisBankModel.setMemberNo(sysCisBankModel.getMemberNo());
    webSysCisBankModel.setMaxNrRecords(sysCisBankModel.getMaxNrRecords());
    webSysCisBankModel.setNrOfDaysProc(sysCisBankModel.getNrOfDaysProc());
    webSysCisBankModel.setPubHolProc(sysCisBankModel.getPubHolProc());
    webSysCisBankModel.setAcCreditor(sysCisBankModel.getAcCreditor());
    webSysCisBankModel.setAcDebtor(sysCisBankModel.getAcDebtor());

    return webSysCisBankModel;
  }


  public static WebSysCisBranchModel translateCommonsSysCisBranchModelToWebModel(
      SysCisBranchModel sysCisBranchModel) {
    WebSysCisBranchModel webSysCisBranchModel = new WebSysCisBranchModel();

    webSysCisBranchModel.setAcCreditor(sysCisBranchModel.getAcCreditor());
    webSysCisBranchModel.setAcDebtor(sysCisBranchModel.getAcDebtor());
    webSysCisBranchModel.setBranchName(sysCisBranchModel.getBranchName());
    webSysCisBranchModel.setBranchNo(sysCisBranchModel.getBranchNo());
    webSysCisBranchModel.setDivision(sysCisBranchModel.getDivision());
    webSysCisBranchModel.setMemberNo(sysCisBranchModel.getMemberNo());


    return webSysCisBranchModel;

  }

  public AmendmentCodesModel translateWebAmendmentCodesModelToCommonsModel(
      WebAmendmentCodesModel localWebAmendmentCodesModel) {

    AmendmentCodesModel amendmentCodesModel = new AmendmentCodesModel();

    amendmentCodesModel.setAmendmentCodes(localWebAmendmentCodesModel.getAmendmentCodes());
    amendmentCodesModel.setAmendmentCodesDescription(
        localWebAmendmentCodesModel.getAmendmentCodesDescription());
    amendmentCodesModel.setActiveInd(localWebAmendmentCodesModel.getActiveInd());
    amendmentCodesModel.setCreatedBy(localWebAmendmentCodesModel.getCreatedBy());
    amendmentCodesModel.setCreatedDate(localWebAmendmentCodesModel.getCreatedDate());
    amendmentCodesModel.setModifiedBy(localWebAmendmentCodesModel.getModifiedBy());
    amendmentCodesModel.setModifiedDate(localWebAmendmentCodesModel.getModifiedDate());

    return amendmentCodesModel;
  }


  public WebAmendmentCodesModel translateCommonsAmendmentCodesModelToWebModel(
      AmendmentCodesModel localCommonModel) {
    WebAmendmentCodesModel webAmendmentCodesModel = new WebAmendmentCodesModel();

    webAmendmentCodesModel.setAmendmentCodes(localCommonModel.getAmendmentCodes());
    webAmendmentCodesModel.setAmendmentCodesDescription(
        localCommonModel.getAmendmentCodesDescription());
    webAmendmentCodesModel.setActiveInd(localCommonModel.getActiveInd());
    webAmendmentCodesModel.setCreatedBy(localCommonModel.getCreatedBy());
    webAmendmentCodesModel.setCreatedDate(localCommonModel.getCreatedDate());
    webAmendmentCodesModel.setModifiedBy(localCommonModel.getModifiedBy());
    webAmendmentCodesModel.setModifiedDate(localCommonModel.getModifiedDate());

    return webAmendmentCodesModel;
  }

  public WebSysCtrlSlaTimeModel translateSysCtrlSlaTimeModelToWebModel(
      SysCtrlSlaTimeModel sysCtrlSlaTimeModel) {
    WebSysCtrlSlaTimeModel webSysCtrlSlaTimeModel = new WebSysCtrlSlaTimeModel();

    webSysCtrlSlaTimeModel.setService(sysCtrlSlaTimeModel.getService());
    webSysCtrlSlaTimeModel.setStartTime(sysCtrlSlaTimeModel.getStartTime());
    webSysCtrlSlaTimeModel.setEndTime(sysCtrlSlaTimeModel.getEndTime());
    webSysCtrlSlaTimeModel.setCreatedBy(sysCtrlSlaTimeModel.getCreatedBy());
    webSysCtrlSlaTimeModel.setCreatedDate(sysCtrlSlaTimeModel.getCreatedDate());
    webSysCtrlSlaTimeModel.setModifiedBy(sysCtrlSlaTimeModel.getModifiedBy());
    webSysCtrlSlaTimeModel.setModifiedDate(sysCtrlSlaTimeModel.getModifiedDate());

    return webSysCtrlSlaTimeModel;
  }


  public SysCtrlSlaTimeModel translateWebSysCtrlSlaTimeModelToCommonsModel(
      WebSysCtrlSlaTimeModel locaWebSysCtrlSlaTimeModell) {
    SysCtrlSlaTimeModel localSysCtrlSlaTimeModel = new SysCtrlSlaTimeModel();

    localSysCtrlSlaTimeModel.setService(locaWebSysCtrlSlaTimeModell.getService());
    localSysCtrlSlaTimeModel.setStartTime(locaWebSysCtrlSlaTimeModell.getStartTime());
    localSysCtrlSlaTimeModel.setEndTime(locaWebSysCtrlSlaTimeModell.getEndTime());
    localSysCtrlSlaTimeModel.setCreatedBy(locaWebSysCtrlSlaTimeModell.getCreatedBy());
    localSysCtrlSlaTimeModel.setCreatedDate(locaWebSysCtrlSlaTimeModell.getCreatedDate());
    localSysCtrlSlaTimeModel.setModifiedBy(locaWebSysCtrlSlaTimeModell.getModifiedBy());
    localSysCtrlSlaTimeModel.setModifiedDate(locaWebSysCtrlSlaTimeModell.getModifiedDate());

    return localSysCtrlSlaTimeModel;
  }

  public WebIncSotEotModel translateIncSotEotCommonsModelToWebModel(IncSotEotModel incSotEotModel) {
    WebIncSotEotModel webIncSotEotModel = new WebIncSotEotModel();

    webIncSotEotModel.setEotIn(incSotEotModel.getEotIn());
    webIncSotEotModel.setInstId(incSotEotModel.getInstId());
    webIncSotEotModel.setServiceId(incSotEotModel.getServiceId());
    webIncSotEotModel.setSotIn(incSotEotModel.getSotIn());

    return webIncSotEotModel;
  }

  public WebOutSotEotModel translateOutSotEotCommonsModelToWebModel(OutSotEotModel outSotEotModel) {
    WebOutSotEotModel webOutSotEotModel = new WebOutSotEotModel();

    webOutSotEotModel.setEotOut(outSotEotModel.getEotOut());
    webOutSotEotModel.setInstId(outSotEotModel.getInstId());
    webOutSotEotModel.setServiceId(outSotEotModel.getServiceId());
    webOutSotEotModel.setSotOut(outSotEotModel.getSotOut());

    return webOutSotEotModel;
  }

  public WebServicesModel translateIncCommonsServicesModelToWebModel(
      ServicesModel localCommonModel) {
    WebServicesModel webServicesModel = new WebServicesModel();

    webServicesModel.setRecordId(localCommonModel.getRecordId());
    webServicesModel.setServiceIdIn(localCommonModel.getServiceIdIn());
    webServicesModel.setServiceIdInDesc(localCommonModel.getServiceIdInDesc());
    webServicesModel.setProcessDate(localCommonModel.getProcessDate());
    webServicesModel.setActiveInd(localCommonModel.getActiveInd());
    webServicesModel.setCreatedBy(localCommonModel.getCreatedBy());
    webServicesModel.setCreatedDate(localCommonModel.getCreatedDate());
    webServicesModel.setModifiedDate(localCommonModel.getModifiedDate());

    return webServicesModel;
  }

  public WebFileSizeLimitModel translateFileSizeLimitModelCommonsModelToWebModel(
      FileSizeLimitModel fileSizeLimitModel) {
    WebFileSizeLimitModel webFileSizeLimitModel = new WebFileSizeLimitModel();

    webFileSizeLimitModel.setMemberId(fileSizeLimitModel.getMemberId());
    webFileSizeLimitModel.setSubService(fileSizeLimitModel.getSubService());
    webFileSizeLimitModel.setProcessDate(fileSizeLimitModel.getProcessDate());
    webFileSizeLimitModel.setLimit(fileSizeLimitModel.getLimit());
    webFileSizeLimitModel.setDirection(fileSizeLimitModel.getDirection());
    webFileSizeLimitModel.setActiveId(fileSizeLimitModel.getActiveId());

    return webFileSizeLimitModel;


  }

  public WebSysCtrlFileSizeLimitModel translatSysCtrlFileSizeLimitModelCommonsModelToWebModel(
      SysCtrlFileSizeLimitModel sysCtrlFileSizeLimitModel) {
    WebSysCtrlFileSizeLimitModel webSysCtrlFileSizeLimitModel = new WebSysCtrlFileSizeLimitModel();

    webSysCtrlFileSizeLimitModel.setMemberId(sysCtrlFileSizeLimitModel.getMemberId());
    webSysCtrlFileSizeLimitModel.setSubService(sysCtrlFileSizeLimitModel.getSubService());
    webSysCtrlFileSizeLimitModel.setLimit(sysCtrlFileSizeLimitModel.getLimit());
    webSysCtrlFileSizeLimitModel.setDirection(sysCtrlFileSizeLimitModel.getDirection());
    webSysCtrlFileSizeLimitModel.setActiveId(sysCtrlFileSizeLimitModel.getActiveId());
    webSysCtrlFileSizeLimitModel.setCreatedBy(sysCtrlFileSizeLimitModel.getCreatedBy());
    webSysCtrlFileSizeLimitModel.setCreatedDate(sysCtrlFileSizeLimitModel.getCreatedDate());
    webSysCtrlFileSizeLimitModel.setModifiedBy(sysCtrlFileSizeLimitModel.getModifiedBy());
    webSysCtrlFileSizeLimitModel.setModifiedDate(sysCtrlFileSizeLimitModel.getModifiedDate());

    return webSysCtrlFileSizeLimitModel;
  }


  public SysCtrlFileSizeLimitModel translateWebSysCtrlFileSizeLimitModelToCommonsModel(
      WebSysCtrlFileSizeLimitModel webSysCtrlFileSizeLimitModel) {
    SysCtrlFileSizeLimitModel sysCtrlFileSizeLimitModel = new SysCtrlFileSizeLimitModel();

    sysCtrlFileSizeLimitModel.setMemberId(webSysCtrlFileSizeLimitModel.getMemberId());
    sysCtrlFileSizeLimitModel.setSubService(webSysCtrlFileSizeLimitModel.getSubService());
    sysCtrlFileSizeLimitModel.setLimit(webSysCtrlFileSizeLimitModel.getLimit());
    sysCtrlFileSizeLimitModel.setDirection(webSysCtrlFileSizeLimitModel.getDirection());
    sysCtrlFileSizeLimitModel.setActiveId(webSysCtrlFileSizeLimitModel.getActiveId());
    sysCtrlFileSizeLimitModel.setCreatedBy(webSysCtrlFileSizeLimitModel.getCreatedBy());
    sysCtrlFileSizeLimitModel.setCreatedDate(webSysCtrlFileSizeLimitModel.getCreatedDate());
    sysCtrlFileSizeLimitModel.setModifiedBy(webSysCtrlFileSizeLimitModel.getModifiedBy());
    sysCtrlFileSizeLimitModel.setModifiedDate(webSysCtrlFileSizeLimitModel.getModifiedDate());

    return sysCtrlFileSizeLimitModel;
  }


}

