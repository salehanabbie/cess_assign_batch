package com.bsva.translator;

import com.bsva.commons.model.AcOpsSotEotCntrlModel;
import com.bsva.commons.model.AuditTrackingSqlModel;
import com.bsva.commons.model.BatchAmendmentModel;
import com.bsva.commons.model.BatchOustandingResponseModel;
import com.bsva.commons.model.BatchOustandingResponseReportModel;
import com.bsva.commons.model.BatchRejectedTransactionModel;
import com.bsva.commons.model.CisMemberModel;
import com.bsva.commons.model.CreditorBankModel;
import com.bsva.commons.model.DebtorBankModel;
import com.bsva.commons.model.EndOfDayReportCommonsModel;
import com.bsva.commons.model.FileStatusReportModel;
import com.bsva.commons.model.IncSotEotModel;
import com.bsva.commons.model.MandateAmendModel;
import com.bsva.commons.model.MandateDailyTransModel;
import com.bsva.commons.model.MandateRejectionModel;
import com.bsva.commons.model.MandateResponseOutstandingPerBankModel;
import com.bsva.commons.model.MandatesDebtorReportModel;
import com.bsva.commons.model.MandatesExtractReportModel;
import com.bsva.commons.model.MandatesRejectedReportModel;
import com.bsva.commons.model.MandatesSearchModel;
import com.bsva.commons.model.MandatesSummaryModel;
import com.bsva.commons.model.MndtSummaryTotalsModel;
import com.bsva.commons.model.OpsFileRegModel;
import com.bsva.commons.model.OutBalanceCountReportModel;
import com.bsva.commons.model.OutSotEotModel;
import com.bsva.commons.model.OutofBalanceModel;
import com.bsva.commons.model.OutstandingRespSummaryCountModel;
import com.bsva.commons.model.OutstandingResponsesDebtorModel;
import com.bsva.commons.model.ProcessStatusModel;
import com.bsva.commons.model.SysCisBankModel;
import com.bsva.commons.model.SystemParameterReportModel;
import com.bsva.entities.AuditTrackingEntityModel;
import com.bsva.entities.BatchAmendmentEntityModel;
import com.bsva.entities.BatchOustandingResponseEntityModel;
import com.bsva.entities.BatchOustandingResponseEntityReportModel;
import com.bsva.entities.BatchRejectedTransactionEntityModel;
import com.bsva.entities.CasOpsSotEotEntityModel;
import com.bsva.entities.CasSysctrlProcessStatusEntity;
import com.bsva.entities.CisMemberEntity;
import com.bsva.entities.CreditorBankEntityModel;
import com.bsva.entities.DebtorBankEntityModel;
import com.bsva.entities.EndOfDayReportsEntityModel;
import com.bsva.entities.FileStatusReportEntityModel;
import com.bsva.entities.IncSotEotEntityModel;
import com.bsva.entities.InterchgBillingDataModel;
import com.bsva.entities.MandateAmendEntityModel;
import com.bsva.entities.MandateDailyTransEntityModel;
import com.bsva.entities.MandateRejectionEntityModel;
import com.bsva.entities.MandateResponseOutstandingPerBankEntityModel;
import com.bsva.entities.MandatesExtractReportEntityModel;
import com.bsva.entities.MandatesRejectedReportEntityModel;
import com.bsva.entities.MandatesReportsEntityModel;
import com.bsva.entities.MandatesSearchEntityModel;
import com.bsva.entities.MandatesSummaryReportModel;
import com.bsva.entities.MdtAcOpsSotEotCtrlEntity;
import com.bsva.entities.MdtAcOpsSotEotCtrlPK;
import com.bsva.entities.MdtAcOpsTxnsBillingEntity;
import com.bsva.entities.MdtOpsFileRegEntity;
import com.bsva.entities.MndtSummaryTotalsEntityModel;
import com.bsva.entities.ObsBillingStagingEntity;
import com.bsva.entities.ObsTxnsBillStagingEntity;
import com.bsva.entities.OutBalanceCountReportEntityModel;
import com.bsva.entities.OutOfBalanceReportModel;
import com.bsva.entities.OutSotEotEntityModel;
import com.bsva.entities.OutstandingRespSummaryCountEntityModel;
import com.bsva.entities.OutstandingResponsesDebtorModelEntity;
import com.bsva.entities.SysCisBankEntity;
import com.bsva.entities.SystemParamReportEntityModel;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.log4j.Logger;


/**
 * @author salehas
 */
public class ServiceTranslator {
  SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  public static Logger log = Logger.getLogger(ServiceTranslator.class);

  public OpsFileRegModel translateOpsFileRegEntityToCommonsModel(
      MdtOpsFileRegEntity opsFileRegEntity) {

    OpsFileRegModel opsFileRegModel = new OpsFileRegModel();

    opsFileRegModel.setFileName(opsFileRegEntity.getFileName());
    opsFileRegModel.setFilepath(opsFileRegEntity.getFilepath());
    opsFileRegModel.setNameSpace(opsFileRegEntity.getNameSpace());
    opsFileRegModel.setProcessDate(opsFileRegEntity.getProcessDate());
    opsFileRegModel.setReason(opsFileRegEntity.getReason());
    opsFileRegModel.setStatus(opsFileRegEntity.getStatus());
    opsFileRegModel.setGrpHdrMsgId(opsFileRegEntity.getGrpHdrMsgId());

    return opsFileRegModel;
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

  //Translates the Model Object from Entities to Commons Model so we can work with data on front end
  public MandatesSummaryModel translateMandatesSummaryModelEntityToCommonsModel(
      MandatesSummaryReportModel manReportEntityModel) {
    MandatesSummaryModel mandatesSummModel = new MandatesSummaryModel();
    mandatesSummModel.setFinInstName(manReportEntityModel.getFinInstName());
    mandatesSummModel.setNrofMandates(manReportEntityModel.getNrofMandates().intValue());
    mandatesSummModel.setValofMandates(manReportEntityModel.getValofMandates().doubleValue());

    return mandatesSummModel;
  }

  public EndOfDayReportCommonsModel translateEndOfDayEntityModelToCommonsModel(
      EndOfDayReportsEntityModel endOfDayReportsModel) {
    EndOfDayReportCommonsModel endOfDayReportCommonsModel = new EndOfDayReportCommonsModel();
    endOfDayReportCommonsModel.setBalanceBrghtForward(
        endOfDayReportsModel.getBalanceBrghtForward());
    endOfDayReportCommonsModel.setBalanceCarriedForward(
        endOfDayReportsModel.getBalanceCarriedForward());
    endOfDayReportCommonsModel.setIncoming(endOfDayReportsModel.getIncoming());
    endOfDayReportCommonsModel.setOutgoing(endOfDayReportsModel.getOutgoing());
    endOfDayReportCommonsModel.setServiceID(endOfDayReportsModel.getServiceID());

    return endOfDayReportCommonsModel;


  }

  //Translates the Model Object from Entities to Commons Model so we can work with data on front end
  public MandatesDebtorReportModel translateMandatesReportsModelEntityToCommonsModel(
      MandatesReportsEntityModel manReportEntityModel) {
    MandatesDebtorReportModel mandatesDebtorReportModel = new MandatesDebtorReportModel();

    mandatesDebtorReportModel.setMandateId(manReportEntityModel.getMandateId());
    mandatesDebtorReportModel.setMandateReqId(manReportEntityModel.getMandateReqId());
    mandatesDebtorReportModel.setFromDate(manReportEntityModel.getFromDate());
    mandatesDebtorReportModel.setToDate(manReportEntityModel.getToDate());
    mandatesDebtorReportModel.setCollAmount(manReportEntityModel.getCollAmount());
    mandatesDebtorReportModel.setFinInstName(manReportEntityModel.getFinInstName());


    log.debug(
        "**********************checking what the " +
				"mandatesDebtorReportModel**************************" +
            mandatesDebtorReportModel);
    return mandatesDebtorReportModel;
  }

  public OutofBalanceModel translateOutOfBalanceReportModelEntityToCommonsModel(
      OutOfBalanceReportModel outOfBalanceReportModel) {
    OutofBalanceModel outofBalanceModel = new OutofBalanceModel();


    outofBalanceModel.setServiceId(outOfBalanceReportModel.getServiceId());
    outofBalanceModel.setNrMsgsAccepted(outOfBalanceReportModel.getNrMsgsAccepted());
    return outofBalanceModel;


  }

  public OutOfBalanceReportModel translateOuOfBalanceModelToEntity(
      OutofBalanceModel outOfBalanceModel) {
    OutOfBalanceReportModel localModel = new OutOfBalanceReportModel();


    localModel.setNrMsgsAccepted(outOfBalanceModel.getNrMsgsAccepted());
    localModel.setServiceId(outOfBalanceModel.getServiceId());

    return localModel;


  }

  public MandatesSearchModel translateMandatesSearchModelEntityToCommonsModel(
      MandatesSearchEntityModel mandatesSearchEntityModel) {
    MandatesSearchModel mandatesSearchModel = new MandatesSearchModel();
    mandatesSearchModel.setMandateId(mandatesSearchEntityModel.getMandateId());
    mandatesSearchModel.setName(mandatesSearchEntityModel.getName());
    mandatesSearchModel.setFiName(mandatesSearchEntityModel.getFiName());
    mandatesSearchModel.setFinId(mandatesSearchEntityModel.getFinId());
    mandatesSearchModel.setPartyId(mandatesSearchEntityModel.getPartyId());

    return mandatesSearchModel;
  }

  public ProcessStatusModel translateMandatesSearchModelEntityToCommonsModel(
      CasSysctrlProcessStatusEntity casSysctrlProcessStatusEntity) {
    ProcessStatusModel processStatusModel = new ProcessStatusModel();

    processStatusModel.setActiveInd(casSysctrlProcessStatusEntity.getActiveInd());
    processStatusModel.setStatus(casSysctrlProcessStatusEntity.getStatus());
    processStatusModel.setStatusDescription(casSysctrlProcessStatusEntity.getStatusDescription());

    return processStatusModel;
  }

  public MandatesExtractReportModel translateMandatesExtractReportEntityModelToCommonsModel(
      MandatesExtractReportEntityModel mandatesExtractReportEntityModel) {
    MandatesExtractReportModel mandatesExtractReportModel = new MandatesExtractReportModel();

    mandatesExtractReportModel.setServiceId(mandatesExtractReportEntityModel.getServiceId());
    mandatesExtractReportModel.setNrMsgsExtracted(
        mandatesExtractReportEntityModel.getNrMsgsExtracted());

    return mandatesExtractReportModel;
  }

  public MandatesExtractReportEntityModel translateMandatesExtractReportModelToEntity(
      MandatesExtractReportModel mandatesExtractReportModel) {
    MandatesExtractReportEntityModel mandatesExtractReportEntityModel =
        new MandatesExtractReportEntityModel();

    mandatesExtractReportEntityModel.setNrMsgsExtracted(
        mandatesExtractReportModel.getNrMsgsExtracted());
    mandatesExtractReportEntityModel.setServiceId(mandatesExtractReportModel.getServiceId());

    return mandatesExtractReportEntityModel;
  }


  public SystemParameterReportModel translateSystemParamReportEntityModelToCommonsModel(
      SystemParamReportEntityModel systemParamReportEntityModel) {
    SystemParameterReportModel systemParameterReportModel = new SystemParameterReportModel();

    systemParameterReportModel.setMemberNo(systemParamReportEntityModel.getMemberNo());
    systemParameterReportModel.setMemberName(systemParamReportEntityModel.getMemberName());
    systemParameterReportModel.setMaxNrRecords(systemParamReportEntityModel.getMaxNrRecords());
    systemParameterReportModel.setDebtorBank(systemParamReportEntityModel.getDebtorBank());
    systemParameterReportModel.setCreditorBank(systemParamReportEntityModel.getCreditorBank());
    systemParameterReportModel.setNrOfDaysProc(systemParamReportEntityModel.getNrOfDaysProc());

    return systemParameterReportModel;

  }

  public MandatesRejectedReportModel translateMandatesRejectedReportEntityModelToCommonsModel(
      MandatesRejectedReportEntityModel mandatesRejectedReportEntityModel) {
    MandatesRejectedReportModel mandatesRejectedReportModel = new MandatesRejectedReportModel();


    mandatesRejectedReportModel.setServiceId(mandatesRejectedReportEntityModel.getServiceId());
    mandatesRejectedReportModel.setNrMsgsRejected(
        mandatesRejectedReportEntityModel.getNrMsgsRejected());

    return mandatesRejectedReportModel;

  }

  public OutBalanceCountReportModel translateOutBalanceCountReportEntityModelToCommonsModel(
      OutBalanceCountReportEntityModel outBalanceCountReportEntityModel) {
    OutBalanceCountReportModel outBalanceCountReportModel = new OutBalanceCountReportModel();

    outBalanceCountReportModel.setInServiceId(outBalanceCountReportEntityModel.getInServiceId());
    outBalanceCountReportModel.setTotalNrOfMsgs(
        outBalanceCountReportEntityModel.getTotalNrOfMsgs());
    outBalanceCountReportModel.setNrMsgsAccepted(
        outBalanceCountReportEntityModel.getNrMsgsAccepted());
    outBalanceCountReportModel.setNrMsgsRejected(
        outBalanceCountReportEntityModel.getNrMsgsRejected());
    outBalanceCountReportModel.setNrMsgsExtracted(
        outBalanceCountReportEntityModel.getNrMsgsExtracted());
    outBalanceCountReportModel.setOutServiceId(outBalanceCountReportEntityModel.getOutServiceId());
    outBalanceCountReportModel.setExtServiceId(outBalanceCountReportEntityModel.getExtServiceId());
    outBalanceCountReportModel.setNrMsgsExtracted(
        outBalanceCountReportEntityModel.getNrMsgsExtracted());
    outBalanceCountReportModel.setDifference(outBalanceCountReportEntityModel.getDifference());

    return outBalanceCountReportModel;
  }


  public MndtSummaryTotalsModel translateMndtSummaryTotalsEntityModelToCommonsModel(
      MndtSummaryTotalsEntityModel mndtSummaryTotalsEntityModel) {
    MndtSummaryTotalsModel mndtSummaryTotalsModel = new MndtSummaryTotalsModel();

    mndtSummaryTotalsModel.setServiceId(mndtSummaryTotalsEntityModel.getServiceId());
    mndtSummaryTotalsModel.setMemberId(mndtSummaryTotalsEntityModel.getMemberId());
    mndtSummaryTotalsModel.setMemberName(mndtSummaryTotalsEntityModel.getMemberName());
    mndtSummaryTotalsModel.setCreatedDate(mndtSummaryTotalsEntityModel.getCreatedDate());

    return mndtSummaryTotalsModel;


  }

  public FileStatusReportModel translateFileStatusReportEntityModelToCommonsModel(
      FileStatusReportEntityModel fileStatusReportEntityModel) {
    FileStatusReportModel fileStatusReportModel = new FileStatusReportModel();

    fileStatusReportModel.setFileName(fileStatusReportEntityModel.getFileName());
    fileStatusReportModel.setInstId(fileStatusReportEntityModel.getInstId());
    fileStatusReportModel.setServiceId(fileStatusReportEntityModel.getServiceId());
    fileStatusReportModel.setNrOfMsgs(fileStatusReportEntityModel.getNrOfMsgs());
    fileStatusReportModel.setStatus(fileStatusReportEntityModel.getStatus());

    return fileStatusReportModel;

  }

  public AcOpsSotEotCntrlModel translateAcOpsSotEotEntityModelToCommonsModel(
      CasOpsSotEotEntityModel acOpsSotEotEntityModel) {
    AcOpsSotEotCntrlModel acOpsSotEotCntrlModel = new AcOpsSotEotCntrlModel();


    acOpsSotEotCntrlModel.setEotIn(acOpsSotEotEntityModel.getEotIn());
    acOpsSotEotCntrlModel.setEotOut(acOpsSotEotEntityModel.getEotOut());
    acOpsSotEotCntrlModel.setInstId(acOpsSotEotEntityModel.getInstId());
    acOpsSotEotCntrlModel.setServiceId(acOpsSotEotEntityModel.getServiceId());
    acOpsSotEotCntrlModel.setSotIn(acOpsSotEotEntityModel.getSotIn());
    acOpsSotEotCntrlModel.setSotOut(acOpsSotEotEntityModel.getSotOut());


    return acOpsSotEotCntrlModel;


  }

  public MdtAcOpsSotEotCtrlEntity translateAcOpsSotEotCntrlModelToEntity(
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


  public AuditTrackingSqlModel translateAuditTrackingEntityModelToCommonsModel(
      AuditTrackingEntityModel auditTrackingEntityModel) {
    AuditTrackingSqlModel auditTrackingSqlModel = new AuditTrackingSqlModel();


    auditTrackingSqlModel.setUserId(auditTrackingEntityModel.getUserId());
    auditTrackingSqlModel.setAction(auditTrackingEntityModel.getAction());
    auditTrackingSqlModel.setActionDate(auditTrackingEntityModel.getActionDate());
    auditTrackingSqlModel.setColumnName(auditTrackingEntityModel.getColumnName());
    auditTrackingSqlModel.setRecord(auditTrackingEntityModel.getRecord());
    auditTrackingSqlModel.setNewValue(auditTrackingEntityModel.getNewValue());
    auditTrackingSqlModel.setOldValue(auditTrackingEntityModel.getOldValue());
    auditTrackingSqlModel.setTableName(auditTrackingEntityModel.getTableName());

    return auditTrackingSqlModel;

  }


  public OutstandingResponsesDebtorModel translateOutstandingResponsesDebtorEntityToCommonsModel(
      OutstandingResponsesDebtorModelEntity outEntity) {
    OutstandingResponsesDebtorModel outstandingResponsesModel =
        new OutstandingResponsesDebtorModel();

    outstandingResponsesModel.setMandateReqTranId(outEntity.getMandateReqTranId());
    outstandingResponsesModel.setMandateRefNr(outEntity.getMandateRefNr());
    outstandingResponsesModel.setMandateReqId(outEntity.getMandateReqId());
    outstandingResponsesModel.setServiceId(outEntity.getServiceId());
    outstandingResponsesModel.setDebtorMemberNo(outEntity.getDebtorMemberNo());
    outstandingResponsesModel.setFileName(outEntity.getFileName());
    outstandingResponsesModel.setBankName(outEntity.getBankName());

    return outstandingResponsesModel;
  }

  public MandateResponseOutstandingPerBankModel translateMndtResponseOutstandingEntityModelToCommonsModel(
      MandateResponseOutstandingPerBankEntityModel localEntity) {
    MandateResponseOutstandingPerBankModel localModel =
        new MandateResponseOutstandingPerBankModel();

    localModel.setCrdMemberName(localEntity.getCrdMemberName());
    localModel.setFileName(localEntity.getFileName());
    localModel.setMandateReqTransId(localEntity.getMandateReqTransId());
    localModel.setCrdMemberNo(localEntity.getCrdMemberNo());
    localModel.setNrDaysOutstanding(localEntity.getNrDaysOutstanding());
    localModel.setServiceId(localEntity.getServiceId());
    localModel.setTransType(localEntity.getTransType());
    localModel.setCreatedDate(localEntity.getCreatedDate());

    return localModel;


  }

  public MandateAmendModel translateMandateAmend16EntityModelToCommonsModel(
      MandateAmendEntityModel localModelEntity) {

    MandateAmendModel mandateAmend16Model = new MandateAmendModel();

    mandateAmend16Model.setAmendReasonCodeCount(localModelEntity.getAmendReasonCodeCount());

    return mandateAmend16Model;


  }


  public CreditorBankModel translateCreditorBankEntityModelToCommonsModel(
      CreditorBankEntityModel creditorBankEntityModel) {
    CreditorBankModel creditorBankModel = new CreditorBankModel();

    creditorBankModel.setMemberName(creditorBankEntityModel.getMemberName());
    creditorBankModel.setMemberNo(creditorBankEntityModel.getMemberNo());

    return creditorBankModel;
  }


  public DebtorBankModel translateDebtorBankEntityModelToCommonsModel(
      DebtorBankEntityModel debtorBankEntityModel) {
    DebtorBankModel debtorBankModel = new DebtorBankModel();

    debtorBankModel.setMemberName(debtorBankEntityModel.getMemberName());
    debtorBankModel.setMemberNo(debtorBankEntityModel.getMemberNo());

    return debtorBankModel;
  }

  public MandateRejectionModel translateMandateRejectionEntityModelToCommonsModel(
      MandateRejectionEntityModel mandateRejectionEntityModel) {
    MandateRejectionModel mandateRejectionModel = new MandateRejectionModel();

    mandateRejectionModel.setRejectReasonCodeCount(
        mandateRejectionEntityModel.getRejectReasonCodeCount());

    return mandateRejectionModel;
  }


  public ObsBillingStagingEntity translateIntchgBillingModelToObsBillingStaging(
      InterchgBillingDataModel intBillModel, String windowNo) {
    ObsBillingStagingEntity billingStagingEntity = new ObsBillingStagingEntity();

    billingStagingEntity.setRecordId(new BigDecimal(123));

//		if(intBillModel.getRespDate() != null)
//			billingStagingEntity.setActionDate(intBillModel.getRespDate());

    if (intBillModel.getTxnType().equalsIgnoreCase("TT2")) {
      billingStagingEntity.setActionDate(intBillModel.getRespDate());
    } else {
      billingStagingEntity.setActionDate(intBillModel.getActionDate());
    }

//		if(intBillModel.getActionDate() != null)
//			billingStagingEntity.setActionDate(intBillModel.getActionDate());

    if (intBillModel != null && intBillModel.getSubService().equalsIgnoreCase("SRINP")) {
		if (intBillModel.getDebtorBank() != null) {
			billingStagingEntity.setOriginator(new BigDecimal(intBillModel.getDebtorBank()));
		}

		if (intBillModel.getCreditorBank() != null) {
			billingStagingEntity.setDestination(new BigDecimal(intBillModel.getCreditorBank()));
		}
    } else {
		if (intBillModel.getCreditorBank() != null) {
			billingStagingEntity.setOriginator(new BigDecimal(intBillModel.getCreditorBank()));
		}

		if (intBillModel.getDebtorBank() != null) {
			billingStagingEntity.setDestination(new BigDecimal(intBillModel.getDebtorBank()));
		}
    }

	  if (intBillModel.getVolume() != null) {
		  billingStagingEntity.setVolume(intBillModel.getVolume());
	  }

    billingStagingEntity.setService("MANDATES");

	  if (intBillModel.getSubService() != null) {
		  billingStagingEntity.setSubService(intBillModel.getSubService());
	  }

	  if (intBillModel.getTxnType() != null) {
		  billingStagingEntity.setTrxnType(intBillModel.getTxnType());
	  }

	  if (intBillModel.getTxnStatus() != null) {
		  billingStagingEntity.setTrxnStatus(intBillModel.getTxnStatus());
	  }

    billingStagingEntity.setCreatedBy("MANOWNER");
    billingStagingEntity.setCreatedDate(new Date());

    billingStagingEntity.setTrackingCode("0");
    billingStagingEntity.setAuthInd("N");
    billingStagingEntity.setSystemName("MANDATES");
    billingStagingEntity.setStatus("I");
    billingStagingEntity.setBillingWindow(windowNo);


	  if (intBillModel.getAuthCode() != null) {
		  billingStagingEntity.setAuthCode(intBillModel.getAuthCode());
	  }


    return billingStagingEntity;
  }


  public BatchOustandingResponseModel translateOustandingResponseSummaryEntityModelToCommonsModel(
      BatchOustandingResponseEntityModel oustandingResponseEntityModel) {
    BatchOustandingResponseModel oustandingResponseSummaryModel =
        new BatchOustandingResponseModel();

    oustandingResponseSummaryModel.setDebtorBank(oustandingResponseEntityModel.getDebtorBank());
    oustandingResponseSummaryModel.setNrOfTxns(oustandingResponseEntityModel.getNrOfTxns());
    oustandingResponseSummaryModel.setCreditorBank(oustandingResponseEntityModel.getCreditorBank());
    oustandingResponseSummaryModel.setCreditorName(oustandingResponseEntityModel.getCreditorName());
    oustandingResponseSummaryModel.setNrOfTxns(oustandingResponseEntityModel.getNrOfTxns());
    oustandingResponseSummaryModel.setServiceId(oustandingResponseEntityModel.getServiceId());


    return oustandingResponseSummaryModel;
  }

  public MandateDailyTransModel translateOustandingMandateDailyTransEntityModelToCommonsModel(
      MandateDailyTransEntityModel mandateDailyTransEntityModel) {
    MandateDailyTransModel mandateDailyTransModel = new MandateDailyTransModel();

    mandateDailyTransModel.setCreditorBank(mandateDailyTransEntityModel.getCreditorBank());
    mandateDailyTransModel.setMndtRefNumber(mandateDailyTransEntityModel.getMndtRefNumber());
    mandateDailyTransModel.setMndtReqId(mandateDailyTransEntityModel.getMndtReqId());
    mandateDailyTransModel.setMndtReqTransId(mandateDailyTransEntityModel.getMndtReqTransId());
    mandateDailyTransModel.setExtractMsgId(mandateDailyTransEntityModel.getExtractMsgId());
    mandateDailyTransModel.setActionDate(mandateDailyTransEntityModel.getActionDate());
    mandateDailyTransModel.setServiceId(mandateDailyTransEntityModel.getServiceId());
    mandateDailyTransModel.setTrxnStatus(mandateDailyTransEntityModel.getTrxnStatus());
    mandateDailyTransModel.setAuthCode(mandateDailyTransEntityModel.getAuthCode());
    mandateDailyTransModel.setDebtorBank(mandateDailyTransEntityModel.getDebtorBank());


    return mandateDailyTransModel;
  }

  public OutstandingRespSummaryCountModel translateOutstandingRespSummaryCountEntityModelToCommonsModel(
      OutstandingRespSummaryCountEntityModel outstandingRespSummaryCountEntityModel) {
    OutstandingRespSummaryCountModel outstandingRespSummaryCountModel =
        new OutstandingRespSummaryCountModel();

    outstandingRespSummaryCountModel.setNrOfDays(
        outstandingRespSummaryCountEntityModel.getNrOfDays());
    return outstandingRespSummaryCountModel;
  }

  public IncSotEotModel translateIncAcOpsSotEotEntityModelToCommonsModel(
      IncSotEotEntityModel acOpsSotEotEntityModel) {
    IncSotEotModel acOpsSotEotCntrlModel = new IncSotEotModel();

    acOpsSotEotCntrlModel.setEotIn(acOpsSotEotEntityModel.getEotIn());
    acOpsSotEotCntrlModel.setInstId(acOpsSotEotEntityModel.getInstId());
    acOpsSotEotCntrlModel.setServiceId(acOpsSotEotEntityModel.getServiceId());
    acOpsSotEotCntrlModel.setSotIn(acOpsSotEotEntityModel.getSotIn());

    return acOpsSotEotCntrlModel;
  }

  public OutSotEotModel translateOutAcOpsSotEotEntityModelToCommonsModel(
      OutSotEotEntityModel acOpsSotEotEntityModel) {
    OutSotEotModel acOpsSotEotCntrlModel = new OutSotEotModel();

    acOpsSotEotCntrlModel.setEotOut(acOpsSotEotEntityModel.getEotOut());
    acOpsSotEotCntrlModel.setInstId(acOpsSotEotEntityModel.getInstId());
    acOpsSotEotCntrlModel.setServiceId(acOpsSotEotEntityModel.getServiceId());
    acOpsSotEotCntrlModel.setSotOut(acOpsSotEotEntityModel.getSotOut());

    return acOpsSotEotCntrlModel;
  }

  public SysCisBankModel translateSysCisBankEntityToCommonsModel(
      SysCisBankEntity sysCisBankEntity) {

    SysCisBankModel sysCisBankModel = new SysCisBankModel();

    sysCisBankModel.setMemberName(sysCisBankEntity.getMemberName());
    sysCisBankModel.setMaxNrRecords(sysCisBankEntity.getMaxNrRecords());
    sysCisBankModel.setNrOfDaysProc(sysCisBankEntity.getNrOfDaysProc());
    sysCisBankModel.setPubHolProc(sysCisBankEntity.getPubHolProc());
    sysCisBankModel.setAcCreditor(sysCisBankEntity.getAcCreditor());
    sysCisBankModel.setMemberNo(sysCisBankEntity.getMemberNo());
    sysCisBankModel.setAcDebtor(sysCisBankEntity.getAcDebtor());

    return sysCisBankModel;
  }

  public BatchOustandingResponseReportModel translateOustandingResponseEntityReportToCommonsModel(
      BatchOustandingResponseEntityReportModel oustandingResponseEntityModel) {
    BatchOustandingResponseReportModel oustandingResponseSummaryModel =
        new BatchOustandingResponseReportModel();

    oustandingResponseSummaryModel.setCreditorName(oustandingResponseEntityModel.getCreditorName());
    oustandingResponseSummaryModel.setDebtorName(oustandingResponseEntityModel.getDebtorName());
    oustandingResponseSummaryModel.setServiceId(oustandingResponseEntityModel.getServiceId());
    oustandingResponseSummaryModel.setDebtorBank(oustandingResponseEntityModel.getDebtorBank());
    oustandingResponseSummaryModel.setManinOneDay(oustandingResponseEntityModel.getManinOneDay());
    oustandingResponseSummaryModel.setManinTwoDay(oustandingResponseEntityModel.getManinTwoDay());
    oustandingResponseSummaryModel.setManamOneDay(oustandingResponseEntityModel.getManamOneDay());
    oustandingResponseSummaryModel.setManamTwoDay(oustandingResponseEntityModel.getManamTwoDay());
    oustandingResponseSummaryModel.setMancnOneDay(oustandingResponseEntityModel.getMancnOneDay());
    oustandingResponseSummaryModel.setMancnTwoDay(oustandingResponseEntityModel.getMancnTwoDay());


    return oustandingResponseSummaryModel;
  }

  public BatchRejectedTransactionModel translateBatchRejectedEntityReportToCommonsModel(
      BatchRejectedTransactionEntityModel batchRejectedTransactionEntityModel) {

    BatchRejectedTransactionModel batchRejectedTransactionModel =
        new BatchRejectedTransactionModel();

    batchRejectedTransactionModel.setRescode(batchRejectedTransactionEntityModel.getRescode());
    batchRejectedTransactionModel.setMemberNumber(
        batchRejectedTransactionEntityModel.getMemberNumber());
    batchRejectedTransactionModel.setMndtRejectionReason(
        batchRejectedTransactionEntityModel.getMndtRejectionReason());
    batchRejectedTransactionModel.setNoTxns(batchRejectedTransactionEntityModel.getNoTxns());

    return batchRejectedTransactionModel;

  }

  public BatchAmendmentModel translateBatchAmendmentEntityReportToCommonsModel(
      BatchAmendmentEntityModel batchAmendmentEntityModel) {

    BatchAmendmentModel batchAmendmentModel = new BatchAmendmentModel();

    batchAmendmentModel.setCreditor(batchAmendmentEntityModel.getCreditor());
    batchAmendmentModel.setCanAmndReqByInitPrty(
        batchAmendmentEntityModel.getCanAmndReqByInitPrty());
    batchAmendmentModel.setReqByCustomer(batchAmendmentEntityModel.getReqByCustomer());
    batchAmendmentModel.setRsnNotSpcByCust(batchAmendmentEntityModel.getRsnNotSpcByCust());
    batchAmendmentModel.setUnspndMndtWithChngs(batchAmendmentEntityModel.getUnspndMndtWithChngs());
    batchAmendmentModel.setUnspndUnchgMndt(batchAmendmentEntityModel.getUnspndUnchgMndt());

    return batchAmendmentModel;

  }

  public ObsTxnsBillStagingEntity translateOpsTxnsBillingModelToObsTxnsBillingStaging(
      MdtAcOpsTxnsBillingEntity mdtAcOpsTxnsBillingEntity) {
    ObsTxnsBillStagingEntity obsTxnsBillStagingEntity = new ObsTxnsBillStagingEntity();

    obsTxnsBillStagingEntity.setRecordId(new BigDecimal(123));
    obsTxnsBillStagingEntity.setActionDate(mdtAcOpsTxnsBillingEntity.getRespDate());
    obsTxnsBillStagingEntity.setService(mdtAcOpsTxnsBillingEntity.getService());
    obsTxnsBillStagingEntity.setSubService(mdtAcOpsTxnsBillingEntity.getSubService());
    obsTxnsBillStagingEntity.setSystemName(mdtAcOpsTxnsBillingEntity.getSystemName());
    obsTxnsBillStagingEntity.setOriginator(new Integer(mdtAcOpsTxnsBillingEntity.getOriginator()));
    obsTxnsBillStagingEntity.setStatus(mdtAcOpsTxnsBillingEntity.getStatus());
    obsTxnsBillStagingEntity.setTrxnStatus(mdtAcOpsTxnsBillingEntity.getTxnStatus());
    obsTxnsBillStagingEntity.setTrxnType(mdtAcOpsTxnsBillingEntity.getTxnType());
    obsTxnsBillStagingEntity.setVolume(mdtAcOpsTxnsBillingEntity.getVolume());
    obsTxnsBillStagingEntity.setFilename(
        mdtAcOpsTxnsBillingEntity.getMdtAcOpsTxnsBillingPK().getFileName());
    obsTxnsBillStagingEntity.setCreatedBy("MANOWNER");
    obsTxnsBillStagingEntity.setCreatedDate(new Date());
    obsTxnsBillStagingEntity.setModifiedBy("MANOWNER");
    obsTxnsBillStagingEntity.setModifiedDate(new Date());

    return obsTxnsBillStagingEntity;

  }

}
