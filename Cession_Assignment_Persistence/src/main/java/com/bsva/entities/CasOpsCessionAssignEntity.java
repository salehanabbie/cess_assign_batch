package com.bsva.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author SalehaR
 */
@Entity
@Table(name = "CAS_OPS_CESS_ASSIGN_TXNS", schema="CAMOWNER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CasOpsCessionAssignEntity.findAll", query = "SELECT m FROM CasOpsCessionAssignEntity m"),
    @NamedQuery(name = "CasOpsCessionAssignEntity.findByMsgId", query = "SELECT m FROM CasOpsCessionAssignEntity m WHERE m.casOpsCessionAssignEntityPK.msgId = :msgId"),
    @NamedQuery(name = "CasOpsCessionAssignEntity.findByMandateReqTranId", query = "SELECT m FROM CasOpsCessionAssignEntity m WHERE m.casOpsCessionAssignEntityPK.mandateReqTranId = :mandateReqTranId"),
    @NamedQuery(name = "CasOpsCessionAssignEntity.findByCreditorBank", query = "SELECT m FROM CasOpsCessionAssignEntity m WHERE m.creditorBank = :creditorBank"),
    @NamedQuery(name = "CasOpsCessionAssignEntity.findByDebtorBank", query = "SELECT m FROM CasOpsCessionAssignEntity m WHERE m.debtorBank = :debtorBank"),
    @NamedQuery(name = "CasOpsCessionAssignEntity.findByServiceId", query = "SELECT m FROM CasOpsCessionAssignEntity m WHERE m.serviceId = :serviceId"),
    @NamedQuery(name = "CasOpsCessionAssignEntity.findByProcessStatus", query = "SELECT m FROM CasOpsCessionAssignEntity m WHERE m.processStatus = :processStatus"),
    @NamedQuery(name = "CasOpsCessionAssignEntity.findByInFileName", query = "SELECT m FROM CasOpsCessionAssignEntity m WHERE m.inFileName = :inFileName"),
    @NamedQuery(name = "CasOpsCessionAssignEntity.findByInFileDate", query = "SELECT m FROM CasOpsCessionAssignEntity m WHERE m.inFileDate = :inFileDate"),
    @NamedQuery(name = "CasOpsCessionAssignEntity.findByExtractMsgId", query = "SELECT m FROM CasOpsCessionAssignEntity m WHERE m.extractMsgId = :extractMsgId"),
    @NamedQuery(name = "CasOpsCessionAssignEntity.findByExtractFileName", query = "SELECT m FROM CasOpsCessionAssignEntity m WHERE m.extractFileName = :extractFileName"),
    @NamedQuery(name = "CasOpsCessionAssignEntity.findByInitParty", query = "SELECT m FROM CasOpsCessionAssignEntity m WHERE m.initParty = :initParty"),
    @NamedQuery(name = "CasOpsCessionAssignEntity.findByMandateId", query = "SELECT m FROM CasOpsCessionAssignEntity m WHERE m.mandateId = :mandateId"),
    @NamedQuery(name = "CasOpsCessionAssignEntity.findByContractRef", query = "SELECT m FROM CasOpsCessionAssignEntity m WHERE m.contractRef = :contractRef"),
    @NamedQuery(name = "CasOpsCessionAssignEntity.findByServiceLevel", query = "SELECT m FROM CasOpsCessionAssignEntity m WHERE m.serviceLevel = :serviceLevel"),
    @NamedQuery(name = "CasOpsCessionAssignEntity.findByLocalInstrCd", query = "SELECT m FROM CasOpsCessionAssignEntity m WHERE m.localInstrCd = :localInstrCd"),
    @NamedQuery(name = "CasOpsCessionAssignEntity.findBySequenceType", query = "SELECT m FROM CasOpsCessionAssignEntity m WHERE m.sequenceType = :sequenceType"),
    @NamedQuery(name = "CasOpsCessionAssignEntity.findByFrequency", query = "SELECT m FROM CasOpsCessionAssignEntity m WHERE m.frequency = :frequency"),
    @NamedQuery(name = "CasOpsCessionAssignEntity.findByFromDate", query = "SELECT m FROM CasOpsCessionAssignEntity m WHERE m.fromDate = :fromDate"),
    @NamedQuery(name = "CasOpsCessionAssignEntity.findByFirstCollDate", query = "SELECT m FROM CasOpsCessionAssignEntity m WHERE m.firstCollDate = :firstCollDate"),
    @NamedQuery(name = "CasOpsCessionAssignEntity.findByCollAmountCurr", query = "SELECT m FROM CasOpsCessionAssignEntity m WHERE m.collAmountCurr = :collAmountCurr"),
    @NamedQuery(name = "CasOpsCessionAssignEntity.findByCollAmount", query = "SELECT m FROM CasOpsCessionAssignEntity m WHERE m.collAmount = :collAmount"),
    @NamedQuery(name = "CasOpsCessionAssignEntity.findByMaxAmountCurr", query = "SELECT m FROM CasOpsCessionAssignEntity m WHERE m.maxAmountCurr = :maxAmountCurr"),
    @NamedQuery(name = "CasOpsCessionAssignEntity.findByMaxAmount", query = "SELECT m FROM CasOpsCessionAssignEntity m WHERE m.maxAmount = :maxAmount"),
    @NamedQuery(name = "CasOpsCessionAssignEntity.findByCredSchemeId", query = "SELECT m FROM CasOpsCessionAssignEntity m WHERE m.credSchemeId = :credSchemeId"),
    @NamedQuery(name = "CasOpsCessionAssignEntity.findByCreditorName", query = "SELECT m FROM CasOpsCessionAssignEntity m WHERE m.creditorName = :creditorName"),
    @NamedQuery(name = "CasOpsCessionAssignEntity.findByCredPhoneNr", query = "SELECT m FROM CasOpsCessionAssignEntity m WHERE m.credPhoneNr = :credPhoneNr"),
    @NamedQuery(name = "CasOpsCessionAssignEntity.findByCredEmailAddr", query = "SELECT m FROM CasOpsCessionAssignEntity m WHERE m.credEmailAddr = :credEmailAddr"),
    @NamedQuery(name = "CasOpsCessionAssignEntity.findByCredAccNum", query = "SELECT m FROM CasOpsCessionAssignEntity m WHERE m.credAccNum = :credAccNum"),
    @NamedQuery(name = "CasOpsCessionAssignEntity.findByCredBranchNr", query = "SELECT m FROM CasOpsCessionAssignEntity m WHERE m.credBranchNr = :credBranchNr"),
    @NamedQuery(name = "CasOpsCessionAssignEntity.findByUltCredName", query = "SELECT m FROM CasOpsCessionAssignEntity m WHERE m.ultCredName = :ultCredName"),
    @NamedQuery(name = "CasOpsCessionAssignEntity.findByCredAbbShortName", query = "SELECT m FROM CasOpsCessionAssignEntity m WHERE m.credAbbShortName = :credAbbShortName"),
    @NamedQuery(name = "CasOpsCessionAssignEntity.findByDebtorName", query = "SELECT m FROM CasOpsCessionAssignEntity m WHERE m.debtorName = :debtorName"),
    @NamedQuery(name = "CasOpsCessionAssignEntity.findByDebtorId", query = "SELECT m FROM CasOpsCessionAssignEntity m WHERE m.debtorId = :debtorId"),
    @NamedQuery(name = "CasOpsCessionAssignEntity.findByDebtPhoneNr", query = "SELECT m FROM CasOpsCessionAssignEntity m WHERE m.debtPhoneNr = :debtPhoneNr"),
    @NamedQuery(name = "CasOpsCessionAssignEntity.findByDebtEmailAddr", query = "SELECT m FROM CasOpsCessionAssignEntity m WHERE m.debtEmailAddr = :debtEmailAddr"),
    @NamedQuery(name = "CasOpsCessionAssignEntity.findByDebtAccNum", query = "SELECT m FROM CasOpsCessionAssignEntity m WHERE m.debtAccNum = :debtAccNum"),
    @NamedQuery(name = "CasOpsCessionAssignEntity.findByDebtAccType", query = "SELECT m FROM CasOpsCessionAssignEntity m WHERE m.debtAccType = :debtAccType"),
    @NamedQuery(name = "CasOpsCessionAssignEntity.findByDebtBranchNr", query = "SELECT m FROM CasOpsCessionAssignEntity m WHERE m.debtBranchNr = :debtBranchNr"),
    @NamedQuery(name = "CasOpsCessionAssignEntity.findByUltDebtName", query = "SELECT m FROM CasOpsCessionAssignEntity m WHERE m.ultDebtName = :ultDebtName"),
    @NamedQuery(name = "CasOpsCessionAssignEntity.findByAuthType", query = "SELECT m FROM CasOpsCessionAssignEntity m WHERE m.authType = :authType"),
    @NamedQuery(name = "CasOpsCessionAssignEntity.findByCollectionDay", query = "SELECT m FROM CasOpsCessionAssignEntity m WHERE m.collectionDay = :collectionDay"),
    @NamedQuery(name = "CasOpsCessionAssignEntity.findByDateAdjRuleInd", query = "SELECT m FROM CasOpsCessionAssignEntity m WHERE m.dateAdjRuleInd = :dateAdjRuleInd"),
    @NamedQuery(name = "CasOpsCessionAssignEntity.findByAdjCategory", query = "SELECT m FROM CasOpsCessionAssignEntity m WHERE m.adjCategory = :adjCategory"),
    @NamedQuery(name = "CasOpsCessionAssignEntity.findByAdjRate", query = "SELECT m FROM CasOpsCessionAssignEntity m WHERE m.adjRate = :adjRate"),
    @NamedQuery(name = "CasOpsCessionAssignEntity.findByAdjAmountCurr", query = "SELECT m FROM CasOpsCessionAssignEntity m WHERE m.adjAmountCurr = :adjAmountCurr"),
    @NamedQuery(name = "CasOpsCessionAssignEntity.findByAdjAmount", query = "SELECT m FROM CasOpsCessionAssignEntity m WHERE m.adjAmount = :adjAmount"),
    @NamedQuery(name = "CasOpsCessionAssignEntity.findByFirstCollAmtCurr", query = "SELECT m FROM CasOpsCessionAssignEntity m WHERE m.firstCollAmtCurr = :firstCollAmtCurr"),
    @NamedQuery(name = "CasOpsCessionAssignEntity.findByFirstCollAmt", query = "SELECT m FROM CasOpsCessionAssignEntity m WHERE m.firstCollAmt = :firstCollAmt"),
    @NamedQuery(name = "CasOpsCessionAssignEntity.findByDebitValueType", query = "SELECT m FROM CasOpsCessionAssignEntity m WHERE m.debitValueType = :debitValueType"),
    @NamedQuery(name = "CasOpsCessionAssignEntity.findByAcceptedInd", query = "SELECT m FROM CasOpsCessionAssignEntity m WHERE m.acceptedInd = :acceptedInd"),
    @NamedQuery(name = "CasOpsCessionAssignEntity.findByRejectReasonCode", query = "SELECT m FROM CasOpsCessionAssignEntity m WHERE m.rejectReasonCode = :rejectReasonCode"),
    @NamedQuery(name = "CasOpsCessionAssignEntity.findByAuthStatusInd", query = "SELECT m FROM CasOpsCessionAssignEntity m WHERE m.authStatusInd = :authStatusInd"),
    @NamedQuery(name = "CasOpsCessionAssignEntity.findByAuthChannel", query = "SELECT m FROM CasOpsCessionAssignEntity m WHERE m.authChannel = :authChannel"),
    @NamedQuery(name = "CasOpsCessionAssignEntity.findByMandateRefNr", query = "SELECT m FROM CasOpsCessionAssignEntity m WHERE m.mandateRefNr = :mandateRefNr"),
    @NamedQuery(name = "CasOpsCessionAssignEntity.findByMandateAuthDate", query = "SELECT m FROM CasOpsCessionAssignEntity m WHERE m.mandateAuthDate = :mandateAuthDate"),
    @NamedQuery(name = "CasOpsCessionAssignEntity.findByAmendReason", query = "SELECT m FROM CasOpsCessionAssignEntity m WHERE m.amendReason = :amendReason"),
    @NamedQuery(name = "CasOpsCessionAssignEntity.findByOrigMandateId", query = "SELECT m FROM CasOpsCessionAssignEntity m WHERE m.origMandateId = :origMandateId"),
    @NamedQuery(name = "CasOpsCessionAssignEntity.findByOrigContractRef", query = "SELECT m FROM CasOpsCessionAssignEntity m WHERE m.origContractRef = :origContractRef"),
    @NamedQuery(name = "CasOpsCessionAssignEntity.findByOrigCredName", query = "SELECT m FROM CasOpsCessionAssignEntity m WHERE m.origCredName = :origCredName"),
    @NamedQuery(name = "CasOpsCessionAssignEntity.findByOrigMandReqTranId", query = "SELECT m FROM CasOpsCessionAssignEntity m WHERE m.origMandReqTranId = :origMandReqTranId"),
    @NamedQuery(name = "CasOpsCessionAssignEntity.findByOrigDebtName", query = "SELECT m FROM CasOpsCessionAssignEntity m WHERE m.origDebtName = :origDebtName"),
    @NamedQuery(name = "CasOpsCessionAssignEntity.findByOrigDebtBranch", query = "SELECT m FROM CasOpsCessionAssignEntity m WHERE m.origDebtBranch = :origDebtBranch"),
    @NamedQuery(name = "CasOpsCessionAssignEntity.findByCancelReason", query = "SELECT m FROM CasOpsCessionAssignEntity m WHERE m.cancelReason = :cancelReason"),
    @NamedQuery(name = "CasOpsCessionAssignEntity.findByCreatedBy", query = "SELECT m FROM CasOpsCessionAssignEntity m WHERE m.createdBy = :createdBy"),
    @NamedQuery(name = "CasOpsCessionAssignEntity.findByCreatedDate", query = "SELECT m FROM CasOpsCessionAssignEntity m WHERE m.createdDate = :createdDate"),
    @NamedQuery(name = "CasOpsCessionAssignEntity.findByModifiedBy", query = "SELECT m FROM CasOpsCessionAssignEntity m WHERE m.modifiedBy = :modifiedBy"),
    @NamedQuery(name = "CasOpsCessionAssignEntity.findByModifiedDate", query = "SELECT m FROM CasOpsCessionAssignEntity m WHERE m.modifiedDate = :modifiedDate"),
    @NamedQuery(name = "CasOpsCessionAssignEntity.findByCreatedDateTruncAndServiceIdDebtor", query = "SELECT m FROM CasOpsCessionAssignEntity m WHERE m.serviceId = :serviceId and TRUNC(m.createdDate) = TO_DATE(:createdDate,'YYYY-MM-DD')"
//    		                                                                                   + " and (m.processStatus = :processStatus1 OR m.processStatus = :processStatus2) and (m.inFileName LIKE CONCAT('%',:memberId,'%'))")})
    																						   + " and (m.processStatus = :processStatus1 OR m.processStatus = :processStatus2) and (m.debtorBank = :memberId)"),
    @NamedQuery(name = "CasOpsCessionAssignEntity.findByCreatedDateTruncAndServiceIdCreditor", query = "SELECT m FROM CasOpsCessionAssignEntity m WHERE m.serviceId = :serviceId and TRUNC(m.createdDate) = TO_DATE(:createdDate,'YYYY-MM-DD')"
    																						   + " and (m.processStatus = :processStatus1 OR m.processStatus = :processStatus2) and (m.creditorBank = :memberId)"),
    @NamedQuery(name = "CasOpsCessionAssignEntity.findByMacCode", query = "SELECT m FROM CasOpsCessionAssignEntity m WHERE m.macCode = :macCode"),
    @NamedQuery(name = "CasOpsCessionAssignEntity.matchingPain012", query = "SELECT m FROM CasOpsCessionAssignEntity m WHERE m.casOpsCessionAssignEntityPK.mandateReqTranId = :mandateReqTranId ORDER BY m.serviceId")})

public class CasOpsCessionAssignEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CasOpsCessionAssignEntityPK casOpsCessionAssignEntityPK;
    @Size(max = 6)
    @Column(name = "CREDITOR_BANK")
    private String creditorBank;
    @Size(max = 6)
    @Column(name = "DEBTOR_BANK")
    private String debtorBank;
    @Size(max = 5)
    @Column(name = "SERVICE_ID")
    private String serviceId;
    @Size(max = 6)
    @Column(name = "PROCESS_STATUS")
    private String processStatus;
    @Size(max = 40)
    @Column(name = "IN_FILE_NAME")
    private String inFileName;
    @Column(name = "IN_FILE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date inFileDate;
    @Size(max = 35)
    @Column(name = "EXTRACT_MSG_ID")
    private String extractMsgId;
    @Size(max = 40)
    @Column(name = "EXTRACT_FILE_NAME")
    private String extractFileName;
    @Size(max = 140)
    @Column(name = "INIT_PARTY")
    private String initParty;
    @Size(max = 35)
    @Column(name = "MANDATE_ID")
    private String mandateId;
    @Size(max = 35)
    @Column(name = "CONTRACT_REF")
    private String contractRef;
    @Size(max = 35)
    @Column(name = "SERVICE_LEVEL")
    private String serviceLevel;
    @Size(max = 35)
    @Column(name = "LOCAL_INSTR_CD")
    private String localInstrCd;
    @Size(max = 4)
    @Column(name = "SEQUENCE_TYPE")
    private String sequenceType;
    @Size(max = 4)
    @Column(name = "FREQUENCY")
    private String frequency;
    @Column(name = "FROM_DATE")
    @Temporal(TemporalType.DATE)
    private Date fromDate;
    @Column(name = "FIRST_COLL_DATE")
    @Temporal(TemporalType.DATE)
    private Date firstCollDate;
    @Size(max = 3)
    @Column(name = "COLL_AMOUNT_CURR")
    private String collAmountCurr;
    @Column(name = "COLL_AMOUNT")
    private BigDecimal collAmount;
    @Size(max = 3)
    @Column(name = "MAX_AMOUNT_CURR")
    private String maxAmountCurr;
    @Column(name = "MAX_AMOUNT")
    private BigDecimal maxAmount;
    @Size(max = 35)
    @Column(name = "CRED_SCHEME_ID")
    private String credSchemeId;
    @Size(max = 140)
    @Column(name = "CREDITOR_NAME")
    private String creditorName;
    @Size(max = 30)
    @Column(name = "CRED_PHONE_NR")
    private String credPhoneNr;
    @Size(max = 2048)
    @Column(name = "CRED_EMAIL_ADDR")
    private String credEmailAddr;
    @Size(max = 34)
    @Column(name = "CRED_ACC_NUM")
    private String credAccNum;
    @Size(max = 35)
    @Column(name = "CRED_BRANCH_NR")
    private String credBranchNr;
    @Size(max = 140)
    @Column(name = "ULT_CRED_NAME")
    private String ultCredName;
    @Size(max = 35)
    @Column(name = "CRED_ABB_SHORT_NAME")
    private String credAbbShortName;
    @Size(max = 140)
    @Column(name = "DEBTOR_NAME")
    private String debtorName;
    @Size(max = 35)
    @Column(name = "DEBTOR_ID")
    private String debtorId;
    @Size(max = 30)
    @Column(name = "DEBT_PHONE_NR")
    private String debtPhoneNr;
    @Size(max = 2048)
    @Column(name = "DEBT_EMAIL_ADDR")
    private String debtEmailAddr;
    @Size(max = 34)
    @Column(name = "DEBT_ACC_NUM")
    private String debtAccNum;
    @Size(max = 35)
    @Column(name = "DEBT_ACC_TYPE")
    private String debtAccType;
    @Size(max = 35)
    @Column(name = "DEBT_BRANCH_NR")
    private String debtBranchNr;
    @Size(max = 140)
    @Column(name = "ULT_DEBT_NAME")
    private String ultDebtName;
    @Size(max = 9)
    @Column(name = "AUTH_TYPE")
    private String authType;
    @Size(max = 2)
    @Column(name = "COLLECTION_DAY")
    private String collectionDay;
    @Size(max = 1)
    @Column(name = "DATE_ADJ_RULE_IND")
    private String dateAdjRuleInd;
    @Size(max = 2)
    @Column(name = "ADJ_CATEGORY")
    private String adjCategory;
    @Column(name = "ADJ_RATE")
    private BigDecimal adjRate;
    @Size(max = 3)
    @Column(name = "ADJ_AMOUNT_CURR")
    private String adjAmountCurr;
    @Column(name = "ADJ_AMOUNT")
    private BigDecimal adjAmount;
    @Size(max = 3)
    @Column(name = "FIRST_COLL_AMT_CURR")
    private String firstCollAmtCurr;
    @Column(name = "FIRST_COLL_AMT")
    private BigDecimal firstCollAmt;
    @Size(max = 11)
    @Column(name = "DEBIT_VALUE_TYPE")
    private String debitValueType;
    @Size(max = 5)
    @Column(name = "ACCEPTED_IND")
    private String acceptedInd;
    @Size(max = 4)
    @Column(name = "REJECT_REASON_CODE")
    private String rejectReasonCode;
    @Size(max = 4)
    @Column(name = "AUTH_STATUS_IND")
    private String authStatusInd;
    @Size(max = 20)
    @Column(name = "AUTH_CHANNEL")
    private String authChannel;
    @Size(max = 35)
    @Column(name = "MANDATE_REF_NR")
    private String mandateRefNr;
    @Column(name = "MANDATE_AUTH_DATE")
    @Temporal(TemporalType.DATE)
    private Date mandateAuthDate;
    @Size(max = 35)
    @Column(name = "AMEND_REASON")
    private String amendReason;
    @Size(max = 35)
    @Column(name = "ORIG_MANDATE_ID")
    private String origMandateId;
    @Size(max = 35)
    @Column(name = "ORIG_CONTRACT_REF")
    private String origContractRef;
    @Size(max = 140)
    @Column(name = "ORIG_CRED_NAME")
    private String origCredName;
    @Size(max = 35)
    @Column(name = "ORIG_MAND_REQ_TRAN_ID")
    private String origMandReqTranId;
    @Size(max = 140)
    @Column(name = "ORIG_DEBT_NAME")
    private String origDebtName;
    @Size(max = 35)
    @Column(name = "ORIG_DEBT_BRANCH")
    private String origDebtBranch;
    @Size(max = 35)
    @Column(name = "CANCEL_REASON")
    private String cancelReason;
    @Size(max = 25)
    @Column(name = "CREATED_BY")
    private String createdBy;
    @Column(name = "CREATED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Size(max = 25)
    @Column(name = "MODIFIED_BY")
    private String modifiedBy;
    @Column(name = "MODIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    @Size(max = 8)
    @Column(name = "MAC_CODE")
    private String macCode;


    public CasOpsCessionAssignEntity() {
    }

    public CasOpsCessionAssignEntity(CasOpsCessionAssignEntityPK casOpsCessionAssignEntityPK) {
        this.casOpsCessionAssignEntityPK = casOpsCessionAssignEntityPK;
    }

    public CasOpsCessionAssignEntity(String msgId, String mandateReqTranId) {
        this.casOpsCessionAssignEntityPK = new CasOpsCessionAssignEntityPK(msgId, mandateReqTranId);
    }

    public CasOpsCessionAssignEntityPK getCasOpsCessionAssignEntityPK() {
        return casOpsCessionAssignEntityPK;
    }

    public void setCasOpsCessionAssignEntityPK(
        CasOpsCessionAssignEntityPK casOpsCessionAssignEntityPK) {
        this.casOpsCessionAssignEntityPK = casOpsCessionAssignEntityPK;
    }

    public String getCreditorBank() {
        return creditorBank;
    }

    public void setCreditorBank(String creditorBank) {
        this.creditorBank = creditorBank;
    }

    public String getDebtorBank() {
        return debtorBank;
    }

    public void setDebtorBank(String debtorBank) {
        this.debtorBank = debtorBank;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(String processStatus) {
        this.processStatus = processStatus;
    }

    public String getInFileName() {
        return inFileName;
    }

    public void setInFileName(String inFileName) {
        this.inFileName = inFileName;
    }

    public Date getInFileDate() {
        return inFileDate;
    }

    public void setInFileDate(Date inFileDate) {
        this.inFileDate = inFileDate;
    }

    public String getExtractMsgId() {
        return extractMsgId;
    }

    public void setExtractMsgId(String extractMsgId) {
        this.extractMsgId = extractMsgId;
    }

    public String getExtractFileName() {
        return extractFileName;
    }

    public void setExtractFileName(String extractFileName) {
        this.extractFileName = extractFileName;
    }

    public String getInitParty() {
        return initParty;
    }

    public void setInitParty(String initParty) {
        this.initParty = initParty;
    }
    
    public String getMandateId() {
        return mandateId;
    }

    public void setMandateId(String mandateId) {
        this.mandateId = mandateId;
    }

    public String getContractRef() {
        return contractRef;
    }

    public void setContractRef(String contractRef) {
        this.contractRef = contractRef;
    }

    public String getServiceLevel() {
        return serviceLevel;
    }

    public void setServiceLevel(String serviceLevel) {
        this.serviceLevel = serviceLevel;
    }

    public String getLocalInstrCd() {
        return localInstrCd;
    }

    public void setLocalInstrCd(String localInstrCd) {
        this.localInstrCd = localInstrCd;
    }

    public String getSequenceType() {
        return sequenceType;
    }

    public void setSequenceType(String sequenceType) {
        this.sequenceType = sequenceType;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getFirstCollDate() {
        return firstCollDate;
    }

    public void setFirstCollDate(Date firstCollDate) {
        this.firstCollDate = firstCollDate;
    }

    public String getCollAmountCurr() {
        return collAmountCurr;
    }

    public void setCollAmountCurr(String collAmountCurr) {
        this.collAmountCurr = collAmountCurr;
    }

    public BigDecimal getCollAmount() {
        return collAmount;
    }

    public void setCollAmount(BigDecimal collAmount) {
        this.collAmount = collAmount;
    }

    public String getMaxAmountCurr() {
        return maxAmountCurr;
    }

    public void setMaxAmountCurr(String maxAmountCurr) {
        this.maxAmountCurr = maxAmountCurr;
    }

    public BigDecimal getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(BigDecimal maxAmount) {
        this.maxAmount = maxAmount;
    }

    public String getCredSchemeId() {
        return credSchemeId;
    }

    public void setCredSchemeId(String credSchemeId) {
        this.credSchemeId = credSchemeId;
    }

    public String getCreditorName() {
        return creditorName;
    }

    public void setCreditorName(String creditorName) {
        this.creditorName = creditorName;
    }

    public String getCredPhoneNr() {
        return credPhoneNr;
    }

    public void setCredPhoneNr(String credPhoneNr) {
        this.credPhoneNr = credPhoneNr;
    }

    public String getCredEmailAddr() {
        return credEmailAddr;
    }

    public void setCredEmailAddr(String credEmailAddr) {
        this.credEmailAddr = credEmailAddr;
    }

    public String getCredAccNum() {
        return credAccNum;
    }

    public void setCredAccNum(String credAccNum) {
        this.credAccNum = credAccNum;
    }

    public String getCredBranchNr() {
        return credBranchNr;
    }

    public void setCredBranchNr(String credBranchNr) {
        this.credBranchNr = credBranchNr;
    }

    public String getUltCredName() {
        return ultCredName;
    }

    public void setUltCredName(String ultCredName) {
        this.ultCredName = ultCredName;
    }

    public String getCredAbbShortName() {
        return credAbbShortName;
    }

    public void setCredAbbShortName(String credAbbShortName) {
        this.credAbbShortName = credAbbShortName;
    }

    public String getDebtorName() {
        return debtorName;
    }

    public void setDebtorName(String debtorName) {
        this.debtorName = debtorName;
    }

    public String getDebtorId() {
        return debtorId;
    }

    public void setDebtorId(String debtorId) {
        this.debtorId = debtorId;
    }

    public String getDebtPhoneNr() {
        return debtPhoneNr;
    }

    public void setDebtPhoneNr(String debtPhoneNr) {
        this.debtPhoneNr = debtPhoneNr;
    }

    public String getDebtEmailAddr() {
        return debtEmailAddr;
    }

    public void setDebtEmailAddr(String debtEmailAddr) {
        this.debtEmailAddr = debtEmailAddr;
    }

    public String getDebtAccNum() {
        return debtAccNum;
    }

    public void setDebtAccNum(String debtAccNum) {
        this.debtAccNum = debtAccNum;
    }

    public String getDebtAccType() {
        return debtAccType;
    }

    public void setDebtAccType(String debtAccType) {
        this.debtAccType = debtAccType;
    }

    public String getDebtBranchNr() {
        return debtBranchNr;
    }

    public void setDebtBranchNr(String debtBranchNr) {
        this.debtBranchNr = debtBranchNr;
    }

    public String getUltDebtName() {
        return ultDebtName;
    }

    public void setUltDebtName(String ultDebtName) {
        this.ultDebtName = ultDebtName;
    }

    public String getAuthType() {
        return authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }

    public String getCollectionDay() {
        return collectionDay;
    }

    public void setCollectionDay(String collectionDay) {
        this.collectionDay = collectionDay;
    }

    public String getDateAdjRuleInd() {
        return dateAdjRuleInd;
    }

    public void setDateAdjRuleInd(String dateAdjRuleInd) {
        this.dateAdjRuleInd = dateAdjRuleInd;
    }

    public String getAdjCategory() {
        return adjCategory;
    }

    public void setAdjCategory(String adjCategory) {
        this.adjCategory = adjCategory;
    }

    public BigDecimal getAdjRate() {
        return adjRate;
    }

    public void setAdjRate(BigDecimal adjRate) {
        this.adjRate = adjRate;
    }

    public String getAdjAmountCurr() {
        return adjAmountCurr;
    }

    public void setAdjAmountCurr(String adjAmountCurr) {
        this.adjAmountCurr = adjAmountCurr;
    }

    public BigDecimal getAdjAmount() {
        return adjAmount;
    }

    public void setAdjAmount(BigDecimal adjAmount) {
        this.adjAmount = adjAmount;
    }

    public String getFirstCollAmtCurr() {
        return firstCollAmtCurr;
    }

    public void setFirstCollAmtCurr(String firstCollAmtCurr) {
        this.firstCollAmtCurr = firstCollAmtCurr;
    }

    public BigDecimal getFirstCollAmt() {
        return firstCollAmt;
    }

    public void setFirstCollAmt(BigDecimal firstCollAmt) {
        this.firstCollAmt = firstCollAmt;
    }

    public String getDebitValueType() {
        return debitValueType;
    }

    public void setDebitValueType(String debitValueType) {
        this.debitValueType = debitValueType;
    }

    public String getAcceptedInd() {
        return acceptedInd;
    }

    public void setAcceptedInd(String acceptedInd) {
        this.acceptedInd = acceptedInd;
    }

    public String getRejectReasonCode() {
        return rejectReasonCode;
    }

    public void setRejectReasonCode(String rejectReasonCode) {
        this.rejectReasonCode = rejectReasonCode;
    }

    public String getAuthStatusInd() {
        return authStatusInd;
    }

    public void setAuthStatusInd(String authStatusInd) {
        this.authStatusInd = authStatusInd;
    }

    public String getAuthChannel() {
        return authChannel;
    }

    public void setAuthChannel(String authChannel) {
        this.authChannel = authChannel;
    }

    public String getMandateRefNr() {
        return mandateRefNr;
    }

    public void setMandateRefNr(String mandateRefNr) {
        this.mandateRefNr = mandateRefNr;
    }

    public Date getMandateAuthDate() {
        return mandateAuthDate;
    }

    public void setMandateAuthDate(Date mandateAuthDate) {
        this.mandateAuthDate = mandateAuthDate;
    }

    public String getAmendReason() {
        return amendReason;
    }

    public void setAmendReason(String amendReason) {
        this.amendReason = amendReason;
    }

    public String getOrigMandateId() {
        return origMandateId;
    }

    public void setOrigMandateId(String origMandateId) {
        this.origMandateId = origMandateId;
    }

    public String getOrigContractRef() {
        return origContractRef;
    }

    public void setOrigContractRef(String origContractRef) {
        this.origContractRef = origContractRef;
    }

    public String getOrigCredName() {
        return origCredName;
    }

    public void setOrigCredName(String origCredName) {
        this.origCredName = origCredName;
    }

    public String getOrigMandReqTranId() {
        return origMandReqTranId;
    }

    public void setOrigMandReqTranId(String origMandReqTranId) {
        this.origMandReqTranId = origMandReqTranId;
    }

    public String getOrigDebtName() {
        return origDebtName;
    }

    public void setOrigDebtName(String origDebtName) {
        this.origDebtName = origDebtName;
    }

    public String getOrigDebtBranch() {
        return origDebtBranch;
    }

    public void setOrigDebtBranch(String origDebtBranch) {
        this.origDebtBranch = origDebtBranch;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
    
	public String getMacCode() {
		return macCode;
	}

	public void setMacCode(String macCode) {
		this.macCode = macCode;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((acceptedInd == null) ? 0 : acceptedInd.hashCode());
		result = prime * result + ((adjAmount == null) ? 0 : adjAmount.hashCode());
		result = prime * result + ((adjAmountCurr == null) ? 0 : adjAmountCurr.hashCode());
		result = prime * result + ((adjCategory == null) ? 0 : adjCategory.hashCode());
		result = prime * result + ((adjRate == null) ? 0 : adjRate.hashCode());
		result = prime * result + ((amendReason == null) ? 0 : amendReason.hashCode());
		result = prime * result + ((authChannel == null) ? 0 : authChannel.hashCode());
		result = prime * result + ((authStatusInd == null) ? 0 : authStatusInd.hashCode());
		result = prime * result + ((authType == null) ? 0 : authType.hashCode());
		result = prime * result + ((cancelReason == null) ? 0 : cancelReason.hashCode());
		result = prime * result + ((collAmount == null) ? 0 : collAmount.hashCode());
		result = prime * result + ((collAmountCurr == null) ? 0 : collAmountCurr.hashCode());
		result = prime * result + ((collectionDay == null) ? 0 : collectionDay.hashCode());
		result = prime * result + ((contractRef == null) ? 0 : contractRef.hashCode());
		result = prime * result + ((createdBy == null) ? 0 : createdBy.hashCode());
		result = prime * result + ((createdDate == null) ? 0 : createdDate.hashCode());
		result = prime * result + ((credAbbShortName == null) ? 0 : credAbbShortName.hashCode());
		result = prime * result + ((credAccNum == null) ? 0 : credAccNum.hashCode());
		result = prime * result + ((credBranchNr == null) ? 0 : credBranchNr.hashCode());
		result = prime * result + ((credEmailAddr == null) ? 0 : credEmailAddr.hashCode());
		result = prime * result + ((credPhoneNr == null) ? 0 : credPhoneNr.hashCode());
		result = prime * result + ((credSchemeId == null) ? 0 : credSchemeId.hashCode());
		result = prime * result + ((creditorBank == null) ? 0 : creditorBank.hashCode());
		result = prime * result + ((creditorName == null) ? 0 : creditorName.hashCode());
		result = prime * result + ((dateAdjRuleInd == null) ? 0 : dateAdjRuleInd.hashCode());
		result = prime * result + ((debitValueType == null) ? 0 : debitValueType.hashCode());
		result = prime * result + ((debtAccNum == null) ? 0 : debtAccNum.hashCode());
		result = prime * result + ((debtAccType == null) ? 0 : debtAccType.hashCode());
		result = prime * result + ((debtBranchNr == null) ? 0 : debtBranchNr.hashCode());
		result = prime * result + ((debtEmailAddr == null) ? 0 : debtEmailAddr.hashCode());
		result = prime * result + ((debtPhoneNr == null) ? 0 : debtPhoneNr.hashCode());
		result = prime * result + ((debtorBank == null) ? 0 : debtorBank.hashCode());
		result = prime * result + ((debtorId == null) ? 0 : debtorId.hashCode());
		result = prime * result + ((debtorName == null) ? 0 : debtorName.hashCode());
		result = prime * result + ((extractFileName == null) ? 0 : extractFileName.hashCode());
		result = prime * result + ((extractMsgId == null) ? 0 : extractMsgId.hashCode());
		result = prime * result + ((firstCollAmt == null) ? 0 : firstCollAmt.hashCode());
		result = prime * result + ((firstCollAmtCurr == null) ? 0 : firstCollAmtCurr.hashCode());
		result = prime * result + ((firstCollDate == null) ? 0 : firstCollDate.hashCode());
		result = prime * result + ((frequency == null) ? 0 : frequency.hashCode());
		result = prime * result + ((fromDate == null) ? 0 : fromDate.hashCode());
		result = prime * result + ((inFileDate == null) ? 0 : inFileDate.hashCode());
		result = prime * result + ((inFileName == null) ? 0 : inFileName.hashCode());
		result = prime * result + ((initParty == null) ? 0 : initParty.hashCode());
		result = prime * result + ((localInstrCd == null) ? 0 : localInstrCd.hashCode());
		result = prime * result + ((macCode == null) ? 0 : macCode.hashCode());
		result = prime * result + ((mandateAuthDate == null) ? 0 : mandateAuthDate.hashCode());
		result = prime * result + ((mandateId == null) ? 0 : mandateId.hashCode());
		result = prime * result + ((mandateRefNr == null) ? 0 : mandateRefNr.hashCode());
		result = prime * result + ((maxAmount == null) ? 0 : maxAmount.hashCode());
		result = prime * result + ((maxAmountCurr == null) ? 0 : maxAmountCurr.hashCode());
		result = prime * result + ((casOpsCessionAssignEntityPK == null) ? 0 : casOpsCessionAssignEntityPK.hashCode());
		result = prime * result + ((modifiedBy == null) ? 0 : modifiedBy.hashCode());
		result = prime * result + ((modifiedDate == null) ? 0 : modifiedDate.hashCode());
		result = prime * result + ((origContractRef == null) ? 0 : origContractRef.hashCode());
		result = prime * result + ((origCredName == null) ? 0 : origCredName.hashCode());
		result = prime * result + ((origDebtBranch == null) ? 0 : origDebtBranch.hashCode());
		result = prime * result + ((origDebtName == null) ? 0 : origDebtName.hashCode());
		result = prime * result + ((origMandReqTranId == null) ? 0 : origMandReqTranId.hashCode());
		result = prime * result + ((origMandateId == null) ? 0 : origMandateId.hashCode());
		result = prime * result + ((processStatus == null) ? 0 : processStatus.hashCode());
		result = prime * result + ((rejectReasonCode == null) ? 0 : rejectReasonCode.hashCode());
		result = prime * result + ((sequenceType == null) ? 0 : sequenceType.hashCode());
		result = prime * result + ((serviceId == null) ? 0 : serviceId.hashCode());
		result = prime * result + ((serviceLevel == null) ? 0 : serviceLevel.hashCode());
		result = prime * result + ((ultCredName == null) ? 0 : ultCredName.hashCode());
		result = prime * result + ((ultDebtName == null) ? 0 : ultDebtName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CasOpsCessionAssignEntity other = (CasOpsCessionAssignEntity) obj;
		if (acceptedInd == null) {
			if (other.acceptedInd != null)
				return false;
		} else if (!acceptedInd.equals(other.acceptedInd))
			return false;
		if (adjAmount == null) {
			if (other.adjAmount != null)
				return false;
		} else if (!adjAmount.equals(other.adjAmount))
			return false;
		if (adjAmountCurr == null) {
			if (other.adjAmountCurr != null)
				return false;
		} else if (!adjAmountCurr.equals(other.adjAmountCurr))
			return false;
		if (adjCategory == null) {
			if (other.adjCategory != null)
				return false;
		} else if (!adjCategory.equals(other.adjCategory))
			return false;
		if (adjRate == null) {
			if (other.adjRate != null)
				return false;
		} else if (!adjRate.equals(other.adjRate))
			return false;
		if (amendReason == null) {
			if (other.amendReason != null)
				return false;
		} else if (!amendReason.equals(other.amendReason))
			return false;
		if (authChannel == null) {
			if (other.authChannel != null)
				return false;
		} else if (!authChannel.equals(other.authChannel))
			return false;
		if (authStatusInd == null) {
			if (other.authStatusInd != null)
				return false;
		} else if (!authStatusInd.equals(other.authStatusInd))
			return false;
		if (authType == null) {
			if (other.authType != null)
				return false;
		} else if (!authType.equals(other.authType))
			return false;
		if (cancelReason == null) {
			if (other.cancelReason != null)
				return false;
		} else if (!cancelReason.equals(other.cancelReason))
			return false;
		if (collAmount == null) {
			if (other.collAmount != null)
				return false;
		} else if (!collAmount.equals(other.collAmount))
			return false;
		if (collAmountCurr == null) {
			if (other.collAmountCurr != null)
				return false;
		} else if (!collAmountCurr.equals(other.collAmountCurr))
			return false;
		if (collectionDay == null) {
			if (other.collectionDay != null)
				return false;
		} else if (!collectionDay.equals(other.collectionDay))
			return false;
		if (contractRef == null) {
			if (other.contractRef != null)
				return false;
		} else if (!contractRef.equals(other.contractRef))
			return false;
		if (createdBy == null) {
			if (other.createdBy != null)
				return false;
		} else if (!createdBy.equals(other.createdBy))
			return false;
		if (createdDate == null) {
			if (other.createdDate != null)
				return false;
		} else if (!createdDate.equals(other.createdDate))
			return false;
		if (credAbbShortName == null) {
			if (other.credAbbShortName != null)
				return false;
		} else if (!credAbbShortName.equals(other.credAbbShortName))
			return false;
		if (credAccNum == null) {
			if (other.credAccNum != null)
				return false;
		} else if (!credAccNum.equals(other.credAccNum))
			return false;
		if (credBranchNr == null) {
			if (other.credBranchNr != null)
				return false;
		} else if (!credBranchNr.equals(other.credBranchNr))
			return false;
		if (credEmailAddr == null) {
			if (other.credEmailAddr != null)
				return false;
		} else if (!credEmailAddr.equals(other.credEmailAddr))
			return false;
		if (credPhoneNr == null) {
			if (other.credPhoneNr != null)
				return false;
		} else if (!credPhoneNr.equals(other.credPhoneNr))
			return false;
		if (credSchemeId == null) {
			if (other.credSchemeId != null)
				return false;
		} else if (!credSchemeId.equals(other.credSchemeId))
			return false;
		if (creditorBank == null) {
			if (other.creditorBank != null)
				return false;
		} else if (!creditorBank.equals(other.creditorBank))
			return false;
		if (creditorName == null) {
			if (other.creditorName != null)
				return false;
		} else if (!creditorName.equals(other.creditorName))
			return false;
		if (dateAdjRuleInd == null) {
			if (other.dateAdjRuleInd != null)
				return false;
		} else if (!dateAdjRuleInd.equals(other.dateAdjRuleInd))
			return false;
		if (debitValueType == null) {
			if (other.debitValueType != null)
				return false;
		} else if (!debitValueType.equals(other.debitValueType))
			return false;
		if (debtAccNum == null) {
			if (other.debtAccNum != null)
				return false;
		} else if (!debtAccNum.equals(other.debtAccNum))
			return false;
		if (debtAccType == null) {
			if (other.debtAccType != null)
				return false;
		} else if (!debtAccType.equals(other.debtAccType))
			return false;
		if (debtBranchNr == null) {
			if (other.debtBranchNr != null)
				return false;
		} else if (!debtBranchNr.equals(other.debtBranchNr))
			return false;
		if (debtEmailAddr == null) {
			if (other.debtEmailAddr != null)
				return false;
		} else if (!debtEmailAddr.equals(other.debtEmailAddr))
			return false;
		if (debtPhoneNr == null) {
			if (other.debtPhoneNr != null)
				return false;
		} else if (!debtPhoneNr.equals(other.debtPhoneNr))
			return false;
		if (debtorBank == null) {
			if (other.debtorBank != null)
				return false;
		} else if (!debtorBank.equals(other.debtorBank))
			return false;
		if (debtorId == null) {
			if (other.debtorId != null)
				return false;
		} else if (!debtorId.equals(other.debtorId))
			return false;
		if (debtorName == null) {
			if (other.debtorName != null)
				return false;
		} else if (!debtorName.equals(other.debtorName))
			return false;
		if (extractFileName == null) {
			if (other.extractFileName != null)
				return false;
		} else if (!extractFileName.equals(other.extractFileName))
			return false;
		if (extractMsgId == null) {
			if (other.extractMsgId != null)
				return false;
		} else if (!extractMsgId.equals(other.extractMsgId))
			return false;
		if (firstCollAmt == null) {
			if (other.firstCollAmt != null)
				return false;
		} else if (!firstCollAmt.equals(other.firstCollAmt))
			return false;
		if (firstCollAmtCurr == null) {
			if (other.firstCollAmtCurr != null)
				return false;
		} else if (!firstCollAmtCurr.equals(other.firstCollAmtCurr))
			return false;
		if (firstCollDate == null) {
			if (other.firstCollDate != null)
				return false;
		} else if (!firstCollDate.equals(other.firstCollDate))
			return false;
		if (frequency == null) {
			if (other.frequency != null)
				return false;
		} else if (!frequency.equals(other.frequency))
			return false;
		if (fromDate == null) {
			if (other.fromDate != null)
				return false;
		} else if (!fromDate.equals(other.fromDate))
			return false;
		if (inFileDate == null) {
			if (other.inFileDate != null)
				return false;
		} else if (!inFileDate.equals(other.inFileDate))
			return false;
		if (inFileName == null) {
			if (other.inFileName != null)
				return false;
		} else if (!inFileName.equals(other.inFileName))
			return false;
		if (initParty == null) {
			if (other.initParty != null)
				return false;
		} else if (!initParty.equals(other.initParty))
			return false;
		if (localInstrCd == null) {
			if (other.localInstrCd != null)
				return false;
		} else if (!localInstrCd.equals(other.localInstrCd))
			return false;
		if (macCode == null) {
			if (other.macCode != null)
				return false;
		} else if (!macCode.equals(other.macCode))
			return false;
		if (mandateAuthDate == null) {
			if (other.mandateAuthDate != null)
				return false;
		} else if (!mandateAuthDate.equals(other.mandateAuthDate))
			return false;
		if (mandateId == null) {
			if (other.mandateId != null)
				return false;
		} else if (!mandateId.equals(other.mandateId))
			return false;
		if (mandateRefNr == null) {
			if (other.mandateRefNr != null)
				return false;
		} else if (!mandateRefNr.equals(other.mandateRefNr))
			return false;
		if (maxAmount == null) {
			if (other.maxAmount != null)
				return false;
		} else if (!maxAmount.equals(other.maxAmount))
			return false;
		if (maxAmountCurr == null) {
			if (other.maxAmountCurr != null)
				return false;
		} else if (!maxAmountCurr.equals(other.maxAmountCurr))
			return false;
		if (casOpsCessionAssignEntityPK == null) {
			if (other.casOpsCessionAssignEntityPK != null)
				return false;
		} else if (!casOpsCessionAssignEntityPK.equals(other.casOpsCessionAssignEntityPK))
			return false;
		if (modifiedBy == null) {
			if (other.modifiedBy != null)
				return false;
		} else if (!modifiedBy.equals(other.modifiedBy))
			return false;
		if (modifiedDate == null) {
			if (other.modifiedDate != null)
				return false;
		} else if (!modifiedDate.equals(other.modifiedDate))
			return false;
		if (origContractRef == null) {
			if (other.origContractRef != null)
				return false;
		} else if (!origContractRef.equals(other.origContractRef))
			return false;
		if (origCredName == null) {
			if (other.origCredName != null)
				return false;
		} else if (!origCredName.equals(other.origCredName))
			return false;
		if (origDebtBranch == null) {
			if (other.origDebtBranch != null)
				return false;
		} else if (!origDebtBranch.equals(other.origDebtBranch))
			return false;
		if (origDebtName == null) {
			if (other.origDebtName != null)
				return false;
		} else if (!origDebtName.equals(other.origDebtName))
			return false;
		if (origMandReqTranId == null) {
			if (other.origMandReqTranId != null)
				return false;
		} else if (!origMandReqTranId.equals(other.origMandReqTranId))
			return false;
		if (origMandateId == null) {
			if (other.origMandateId != null)
				return false;
		} else if (!origMandateId.equals(other.origMandateId))
			return false;
		if (processStatus == null) {
			if (other.processStatus != null)
				return false;
		} else if (!processStatus.equals(other.processStatus))
			return false;
		if (rejectReasonCode == null) {
			if (other.rejectReasonCode != null)
				return false;
		} else if (!rejectReasonCode.equals(other.rejectReasonCode))
			return false;
		if (sequenceType == null) {
			if (other.sequenceType != null)
				return false;
		} else if (!sequenceType.equals(other.sequenceType))
			return false;
		if (serviceId == null) {
			if (other.serviceId != null)
				return false;
		} else if (!serviceId.equals(other.serviceId))
			return false;
		if (serviceLevel == null) {
			if (other.serviceLevel != null)
				return false;
		} else if (!serviceLevel.equals(other.serviceLevel))
			return false;
		if (ultCredName == null) {
			if (other.ultCredName != null)
				return false;
		} else if (!ultCredName.equals(other.ultCredName))
			return false;
		if (ultDebtName == null) {
			if (other.ultDebtName != null)
				return false;
		} else if (!ultDebtName.equals(other.ultDebtName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CasOpsCessionAssignEntity [casOpsCessionAssignEntityPK=" +
            casOpsCessionAssignEntityPK
				+ ", creditorBank=" + creditorBank + ", debtorBank=" + debtorBank + ", serviceId=" + serviceId
				+ ", processStatus=" + processStatus + ", inFileName=" + inFileName + ", inFileDate=" + inFileDate
				+ ", extractMsgId=" + extractMsgId + ", extractFileName=" + extractFileName + ", initParty=" + initParty
				+ ", mandateId=" + mandateId + ", contractRef=" + contractRef + ", serviceLevel=" + serviceLevel
				+ ", localInstrCd=" + localInstrCd + ", sequenceType=" + sequenceType + ", frequency=" + frequency
				+ ", fromDate=" + fromDate + ", firstCollDate=" + firstCollDate + ", collAmountCurr=" + collAmountCurr
				+ ", collAmount=" + collAmount + ", maxAmountCurr=" + maxAmountCurr + ", maxAmount=" + maxAmount
				+ ", credSchemeId=" + credSchemeId + ", creditorName=" + creditorName + ", credPhoneNr=" + credPhoneNr
				+ ", credEmailAddr=" + credEmailAddr + ", credAccNum=" + credAccNum + ", credBranchNr=" + credBranchNr
				+ ", ultCredName=" + ultCredName + ", credAbbShortName=" + credAbbShortName + ", debtorName="
				+ debtorName + ", debtorId=" + debtorId + ", debtPhoneNr=" + debtPhoneNr + ", debtEmailAddr="
				+ debtEmailAddr + ", debtAccNum=" + debtAccNum + ", debtAccType=" + debtAccType + ", debtBranchNr="
				+ debtBranchNr + ", ultDebtName=" + ultDebtName + ", authType=" + authType + ", collectionDay="
				+ collectionDay + ", dateAdjRuleInd=" + dateAdjRuleInd + ", adjCategory=" + adjCategory + ", adjRate="
				+ adjRate + ", adjAmountCurr=" + adjAmountCurr + ", adjAmount=" + adjAmount + ", firstCollAmtCurr="
				+ firstCollAmtCurr + ", firstCollAmt=" + firstCollAmt + ", debitValueType=" + debitValueType
				+ ", acceptedInd=" + acceptedInd + ", rejectReasonCode=" + rejectReasonCode + ", authStatusInd="
				+ authStatusInd + ", authChannel=" + authChannel + ", mandateRefNr=" + mandateRefNr
				+ ", mandateAuthDate=" + mandateAuthDate + ", amendReason=" + amendReason + ", origMandateId="
				+ origMandateId + ", origContractRef=" + origContractRef + ", origCredName=" + origCredName
				+ ", origMandReqTranId=" + origMandReqTranId + ", origDebtName=" + origDebtName + ", origDebtBranch="
				+ origDebtBranch + ", cancelReason=" + cancelReason + ", createdBy=" + createdBy + ", createdDate="
				+ createdDate + ", modifiedBy=" + modifiedBy + ", modifiedDate=" + modifiedDate + ", macCode=" + macCode
				+ "]";
	}


	
    
}

