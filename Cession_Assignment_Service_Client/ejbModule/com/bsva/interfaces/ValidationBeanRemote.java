package com.bsva.interfaces;

import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Remote;

/**
 * @author Saleha Saib
 */
@Remote
public interface ValidationBeanRemote {
  public Object validateServiceId_002(String serviceName);

  public Object validateBicCode_003(String bicCode, String process);

  public Object validateFileNumberingInMsgId_004(String instId);

  public Object retrieveSystemParameters(String activeInd);

  public Object validateMemberNo(String memberNo);

  public Object validateInstallmentOccurrence_013(String seqCode);

  public Object validateInstallmentFrequency_014(String freqCode);

  public Object validateDebtorBranchNo(String branchNo, String activeInd);

  public Object validateCreditorBranchNo(String branchNo, String activeInd);

  public Object retrieveErrorCode(String errorCode);

  public Object retrieveStatusDetails(BigDecimal systemSeqNo);

  public boolean deleteOriginalACStatusDetails(Object obj);

  public boolean deleteOriginalACStatusHdrs(Object obj);

  public Object retrieveServiceID(String service);

  public Object retrieveOpsCustParam(String bicCode);

  public boolean createACArchiveStatusDetails(Object obj);

  public boolean createACArchiveStatusHdrs(Object obj);

  /*Generate AC Archive Mandate Records*/
  public boolean createArcConfStatusDetails(Object obj);

  public boolean createArcConfHdrsEntity(Object obj);

  public boolean deleteAcOpsConfStatusDetails(Object obj);

  public boolean deleteAcOpsConfStatusHdrs(Object obj);

  public Object retrieveCompanyParameters(String process);

  public boolean updateCompFileNo(Object obj);

  public Object retrieveOpsServiceIn(String inService);

  public boolean updateMsgLastFileSeqNr(Object obj, String process);

  public Object retrieveOpsCustomerParameters(String bicCode, String process);

  public boolean updateOpsFileReg(Object obj);

  public Object retrieveOpsFileReg(String fileName);

  public Object validateDebitValueType(String debitValueType);

  public Object retrieveRefSeqNr(String serviceId, String instId);

  public boolean updateOpsRefSeqNr(Object obj);

  public BigDecimal saveOpsStatusHdrs(Object obj);

  public boolean saveOpsStatusDetails(List<?> opsStatusDetailsList);

  public boolean saveOpsStatusDetailsRecord(Object obj);

  public List<?> findOpsStatusDetByCriteria(String namedQuery, String fieldName, BigDecimal value,
                                            String txnId);

  public Object validateAdjustmentCategory(String adjCat);

  public Object validateExternalStatusReasonCode(String statusReasonCode);

  public boolean saveOpsMndtCount(Object obj);

  public Object validateAccountType(String accountType);

  public Object validateAdjCategory(String adjCategory);

  public Object validateAuthType(String authType);

  public Object validateErrorCodes(String errorCode);

  //	public Object matchOriginalMandate(String findByParam, String findByValue, String
  //	messageType);
  public Object findByValidationRuleNr(String valRuleNr);

  public List<?> retrieveLocalInstrument(String localInstrCode);

  public Object validateAmendReasonCode(String amendReason);

  public Object validateSysCisBankDetails(String memberNo);

  public BigDecimal saveOpsConfHdrs(Object obj);

  public boolean saveConfDetails(List<?> confDetailsList);

  public boolean createArArcMndtCout(Object obj);

  public boolean deleteAcOpsMndtCount(Object obj);

  public boolean createArchiveDailyBilling(Object obj);

  public boolean deleteOriginalACDailyBilling(Object obj);

  public Object validateCreditorBank(String creditorBank);

  public Object validateDebtorBank(String debtorBank);

  public boolean createAcArcFileReg(Object obj);

  public boolean deleteOpsFileReg(Object obj);

  public List<?> validatePacs002MsgId(String msgId);

  public Object retrieveAcOpsFileSizeLimits(String serviceName, String memberNo);

  //Cacheable List
  public List<?> findAllOpsServices();

  public List<?> findAllCisBanks();

  public List<?> findAllCisBranches();

  public List<?> findAllConfigSequenceTypes();

  public List<?> findAllConfigFrequencyCodes();

  public List<?> findAllConfigAccountTypes();

  public List<?> findAllConfigAuthTypes();

  public List<?> findAllConfigAdjustmentCats();

  public List<?> findAllConfigDebitValueTypes();

  public List<?> findAllConfigReasonCodes();

  public List<?> findAllConfigAmendmentCodes();

  public List<?> findAllConfigErrorCodes();

  public List<?> findAllFileSizeLimit();

  public List<?> retrieveFileSizeLimit(String destInstId);

  public List<?> retriveOutgoingService(String serviceId);

}