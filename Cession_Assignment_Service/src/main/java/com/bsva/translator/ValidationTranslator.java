package com.bsva.translator;

import com.bsva.entities.CasArcConfDetailsEntity;
import com.bsva.entities.CasArcConfHdrsEntity;
import com.bsva.entities.CasArcDailyBillingEntity;
import com.bsva.entities.CasArcMndtCountEntity;
import com.bsva.entities.CasArcStatusDetailsEntity;
import com.bsva.entities.CasArcStatusHdrsEntity;
import com.bsva.entities.CasOpsFileRegEntity;
import com.bsva.entities.CasArcFileRegEntity;
import com.bsva.entities.CasArcGrpHdrEntity;
import com.bsva.entities.CasArcMndtCountPK;
import com.bsva.entities.CasOpsConfDetailsEntity;
import com.bsva.entities.CasOpsConfHdrsEntity;
import com.bsva.entities.CasOpsDailyBillingEntity;
import com.bsva.entities.CasOpsGrpHdrEntity;
import com.bsva.entities.CasOpsMndtCountEntity;
import com.bsva.entities.CasOpsStatusDetailsEntity;
import com.bsva.entities.CasOpsStatusHdrsEntity;
import java.math.BigDecimal;
import java.util.Date;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;

/**
 * @author Saleha Saib
 */
public class ValidationTranslator {
  public static Logger log = Logger.getLogger(ValidationTranslator.class);

  public CasArcStatusDetailsEntity translateAcOpsStsDtlsToArchiveStatusDetails(
      CasOpsStatusDetailsEntity casOpsStatusDetailsEntity, Date systemDate) {
    CasArcStatusDetailsEntity casArcStatusDetailsEntity = new CasArcStatusDetailsEntity();

    casArcStatusDetailsEntity.setSystemSeqNo(
        casOpsStatusDetailsEntity.getSystemSeqNo().longValue());
    casArcStatusDetailsEntity.setStatusHdrSeqNo(
        casOpsStatusDetailsEntity.getStatusHdrSeqNo().longValue());
    casArcStatusDetailsEntity.setErrorCode(casOpsStatusDetailsEntity.getErrorCode());
    casArcStatusDetailsEntity.setTxnId(casOpsStatusDetailsEntity.getTxnId());
    casArcStatusDetailsEntity.setTxnStatus(casOpsStatusDetailsEntity.getTxnStatus());
    casArcStatusDetailsEntity.setErrorType(casOpsStatusDetailsEntity.getErrorType());
    casArcStatusDetailsEntity.setMandateRefNumber(
        casArcStatusDetailsEntity.getMandateRefNumber());
    casArcStatusDetailsEntity.setInstId(casOpsStatusDetailsEntity.getInstId());
    casArcStatusDetailsEntity.setProcessStatus(casOpsStatusDetailsEntity.getProcessStatus());
    casArcStatusDetailsEntity.setDebtorBranchNo(casOpsStatusDetailsEntity.getDebtorBranchNo());
    casArcStatusDetailsEntity.setCrAbbShortName(casOpsStatusDetailsEntity.getCrAbbShortName());
//		mdtAcArcStatusDetailsEntity.setArchiveDate(DateUtils.truncate(new Date(), java.util
//		.Calendar.DAY_OF_MONTH));
    casArcStatusDetailsEntity.setArchiveDate(
        DateUtils.truncate(systemDate, java.util.Calendar.DAY_OF_MONTH));

    return casArcStatusDetailsEntity;
  }

  public CasOpsStatusDetailsEntity translateMdtAcArcStatusDetailsEntityToAcOpsStatusDetails(
      CasArcStatusDetailsEntity casArcStatusDetailsEntity) {
    CasOpsStatusDetailsEntity casOpsStatusDetailsEntity = new CasOpsStatusDetailsEntity();

    casOpsStatusDetailsEntity.setEndToEndId(casArcStatusDetailsEntity.getEndToEndId());
    casOpsStatusDetailsEntity.setErrorCode(casArcStatusDetailsEntity.getErrorCode());
    casOpsStatusDetailsEntity.setErrorType(casArcStatusDetailsEntity.getErrorType());
    casOpsStatusDetailsEntity.setInstId(casArcStatusDetailsEntity.getInstId());
    casOpsStatusDetailsEntity.setMandateRefNumber(
        casArcStatusDetailsEntity.getMandateRefNumber());
    casOpsStatusDetailsEntity.setOrgnlTxnSeqNo(
        new BigDecimal(casArcStatusDetailsEntity.getOrgnlTxnSeqNo()));
    casOpsStatusDetailsEntity.setProcessStatus(casArcStatusDetailsEntity.getProcessStatus());
    casOpsStatusDetailsEntity.setRecordId(casArcStatusDetailsEntity.getRecordId());
    casOpsStatusDetailsEntity.setStatusHdrSeqNo(
        new BigDecimal(casArcStatusDetailsEntity.getStatusHdrSeqNo()));
    casOpsStatusDetailsEntity.setSystemSeqNo(
        new BigDecimal(casArcStatusDetailsEntity.getSystemSeqNo()));
    casOpsStatusDetailsEntity.setTxnId(casArcStatusDetailsEntity.getTxnId());
    casOpsStatusDetailsEntity.setTxnStatus(casArcStatusDetailsEntity.getTxnStatus());

    return casOpsStatusDetailsEntity;
  }


  public CasArcStatusHdrsEntity translateMdtAcOpsStatusHrdsToArchiveStatusHdrs(
      CasOpsStatusHdrsEntity casOpsStatusHdrsEntity, Date systemDate) {
    CasArcStatusHdrsEntity casArcStatusHdrsEntity = new CasArcStatusHdrsEntity();

    casArcStatusHdrsEntity.setCreateDateTime(casOpsStatusHdrsEntity.getCreateDateTime());
    casArcStatusHdrsEntity.setGroupStatus(casOpsStatusHdrsEntity.getGroupStatus());
    casArcStatusHdrsEntity.setHdrMsgId(casOpsStatusHdrsEntity.getHdrMsgId());
    casArcStatusHdrsEntity.setInstdAgt(casOpsStatusHdrsEntity.getInstdAgt());
    casArcStatusHdrsEntity.setInstgAgt(casOpsStatusHdrsEntity.getInstgAgt());
    casArcStatusHdrsEntity.setOrgnlCntlSum(casOpsStatusHdrsEntity.getOrgnlCntlSum());
    casArcStatusHdrsEntity.setOrgnlCreateDateTime(
        casOpsStatusHdrsEntity.getOrgnlCreateDateTime());
    casArcStatusHdrsEntity.setOrgnlMsgId(casOpsStatusHdrsEntity.getOrgnlMsgId());
    casArcStatusHdrsEntity.setOrgnlMsgName(casOpsStatusHdrsEntity.getOrgnlMsgName());
    casArcStatusHdrsEntity.setOrgnlNoOfTxns(casOpsStatusHdrsEntity.getOrgnlNoOfTxns());
    casArcStatusHdrsEntity.setProcessStatus(casOpsStatusHdrsEntity.getProcessStatus());
    casArcStatusHdrsEntity.setService(casOpsStatusHdrsEntity.getService());
    casArcStatusHdrsEntity.setSystemSeqNo(casOpsStatusHdrsEntity.getSystemSeqNo());
    casArcStatusHdrsEntity.setVetRunNumber(casOpsStatusHdrsEntity.getVetRunNumber());
    casArcStatusHdrsEntity.setWorkunitRefNo(casOpsStatusHdrsEntity.getWorkunitRefNo());
//	mdtAcArcStatusHdrsEntity.setArchiveDate(DateUtils.truncate(new Date(), java.util.Calendar
//	.DAY_OF_MONTH));
//	mdtAcArcStatusHdrsEntity.setArchiveDate(systemDate);
    casArcStatusHdrsEntity.setArchiveDate(
        DateUtils.truncate(systemDate, java.util.Calendar.DAY_OF_MONTH));

    return casArcStatusHdrsEntity;

  }

// AC Archive translation.************************************************


  public CasArcGrpHdrEntity translateMdtAcOpsGrpHdrEntityToAcArcGrpHdr(
      CasOpsGrpHdrEntity casOpsGrpHdrEntity, Date systemDate) {

    CasArcGrpHdrEntity mdtAcArcOpsGrpHdrEntity = new CasArcGrpHdrEntity();

    if (casOpsGrpHdrEntity.getAuthCode() != null) {
      mdtAcArcOpsGrpHdrEntity.setAuthCode(casOpsGrpHdrEntity.getAuthCode());
    }
    mdtAcArcOpsGrpHdrEntity.setCreateDateTime(casOpsGrpHdrEntity.getCreateDateTime());
    mdtAcArcOpsGrpHdrEntity.setMsgId(casOpsGrpHdrEntity.getMsgId());
    mdtAcArcOpsGrpHdrEntity.setCreatedBy(casOpsGrpHdrEntity.getCreatedBy());
//		mdtAcArcOpsGrpHdrEntity.setArchiveDate(DateUtils.truncate(new Date(), java.util.Calendar
//		.DAY_OF_MONTH));
    mdtAcArcOpsGrpHdrEntity.setArchiveDate(
        DateUtils.truncate(systemDate, java.util.Calendar.DAY_OF_MONTH));

    return mdtAcArcOpsGrpHdrEntity;

  }


  public CasArcConfDetailsEntity transalteConfStatusDetailsToArcConfStatusDetsils(
      CasOpsConfDetailsEntity mdtAcOpsConfDetailsEntity, Date systemDate) {
    CasArcConfDetailsEntity casArcConfDetailsEntity = new CasArcConfDetailsEntity();

    casArcConfDetailsEntity.setSystemSeqNo(mdtAcOpsConfDetailsEntity.getSystemSeqNo());
    casArcConfDetailsEntity.setConfHdrSeqNo(
        mdtAcOpsConfDetailsEntity.getConfHdrSeqNo().longValue());
    casArcConfDetailsEntity.setErrorCode(mdtAcOpsConfDetailsEntity.getErrorCode());
    casArcConfDetailsEntity.setTxnId(mdtAcOpsConfDetailsEntity.getTxnId());
    casArcConfDetailsEntity.setTxnStatus(mdtAcOpsConfDetailsEntity.getTxnStatus());
    casArcConfDetailsEntity.setErrorType(mdtAcOpsConfDetailsEntity.getErrorType());
    casArcConfDetailsEntity.setRecordId(mdtAcOpsConfDetailsEntity.getRecordId());
    casArcConfDetailsEntity.setMandateRefNumber(mdtAcOpsConfDetailsEntity.getMandateRefNumber());
    casArcConfDetailsEntity.setInstId(mdtAcOpsConfDetailsEntity.getInstId());
    casArcConfDetailsEntity.setProcessStatus(mdtAcOpsConfDetailsEntity.getProcessStatus());
    casArcConfDetailsEntity.setExtractService(mdtAcOpsConfDetailsEntity.getExtractService());
//			mdtAcArcConfDetailsEntity.setArchiveDate(DateUtils.truncate(new Date(), java.util
//			.Calendar.DAY_OF_MONTH));
    casArcConfDetailsEntity.setArchiveDate(
        DateUtils.truncate(systemDate, java.util.Calendar.DAY_OF_MONTH));
    casArcConfDetailsEntity.setOrgnlMsgType(mdtAcOpsConfDetailsEntity.getOrgnlMsgType());
    casArcConfDetailsEntity.setExtractMsgId(mdtAcOpsConfDetailsEntity.getExtractMsgId());
    casArcConfDetailsEntity.setLocalInstrCd(mdtAcOpsConfDetailsEntity.getLocalInstrCd());

    return casArcConfDetailsEntity;
  }

  public CasArcConfHdrsEntity transalteConfSHdrsToArcConfHdrs(
      CasOpsConfHdrsEntity casOpsConfHdrsEntity, Date systemDate) {
    CasArcConfHdrsEntity casArcConfHdrsEntity = new CasArcConfHdrsEntity();

    casArcConfHdrsEntity.setSystemSeqNo(casOpsConfHdrsEntity.getSystemSeqNo());
    casArcConfHdrsEntity.setHdrMsgId(casOpsConfHdrsEntity.getHdrMsgId());
    casArcConfHdrsEntity.setCreateDateTime(casOpsConfHdrsEntity.getCreateDateTime());
    casArcConfHdrsEntity.setInstgAgt(casOpsConfHdrsEntity.getInstgAgt());
    casArcConfHdrsEntity.setInstdAgt(casOpsConfHdrsEntity.getInstdAgt());
    casArcConfHdrsEntity.setOrgnlMsgId(casOpsConfHdrsEntity.getOrgnlMsgId());
    casArcConfHdrsEntity.setOrgnlMsgName(casOpsConfHdrsEntity.getOrgnlMsgName());
    casArcConfHdrsEntity.setOrgnlCreateDateTime(casOpsConfHdrsEntity.getCreateDateTime());
    casArcConfHdrsEntity.setProcessStatus(casOpsConfHdrsEntity.getProcessStatus());
    casArcConfHdrsEntity.setService(casOpsConfHdrsEntity.getService());
    casArcConfHdrsEntity.setGroupStatus(casOpsConfHdrsEntity.getGroupStatus());
    casArcConfHdrsEntity.setGroupError(casOpsConfHdrsEntity.getGroupError());
//			mdtAcArcConfHdrsEntity.setArchiveDate(DateUtils.truncate(new Date(), java.util
//			.Calendar.DAY_OF_MONTH));
    casArcConfHdrsEntity.setArchiveDate(
        DateUtils.truncate(systemDate, java.util.Calendar.DAY_OF_MONTH));

    return casArcConfHdrsEntity;
  }

  public CasArcMndtCountEntity translateAcOpsMndtCountEntityToAcArcMndtCount(
      CasOpsMndtCountEntity casOpsMndtCountEntity, Date systemDate) {
    CasArcMndtCountEntity casArcMndtCountEntity = new CasArcMndtCountEntity();
    CasArcMndtCountPK casArcMndtCountPK = new CasArcMndtCountPK();

    casArcMndtCountPK.setInstId(casOpsMndtCountEntity.getCasOpsMndtCountPK().getInstId());
    casArcMndtCountPK.setMsgId(casOpsMndtCountEntity.getCasOpsMndtCountPK().getMsgId());
    casArcMndtCountPK.setServiceId(
        casOpsMndtCountEntity.getCasOpsMndtCountPK().getServiceId());

    casArcMndtCountEntity.setMdtAcArcMndtCountPK(casArcMndtCountPK);

    casArcMndtCountEntity.setFileName(casOpsMndtCountEntity.getFileName());
    casArcMndtCountEntity.setIncoming(casOpsMndtCountEntity.getIncoming());
    casArcMndtCountEntity.setNrMsgsAccepted(casOpsMndtCountEntity.getNrMsgsAccepted());
    casArcMndtCountEntity.setNrMsgsExtracted(casOpsMndtCountEntity.getNrMsgsExtracted());
    casArcMndtCountEntity.setNrMsgsRejected(casOpsMndtCountEntity.getNrMsgsRejected());
    casArcMndtCountEntity.setNrOfFiles(casOpsMndtCountEntity.getNrOfFiles());
    casArcMndtCountEntity.setNrOfMsgs(casOpsMndtCountEntity.getNrOfMsgs());
    casArcMndtCountEntity.setOutgoing(casOpsMndtCountEntity.getOutgoing());
//			mdtAcArcMndtCountEntity.setProcessDate(DateUtils.truncate(new Date(), java.util
//			.Calendar.DAY_OF_MONTH));
    casArcMndtCountEntity.setProcessDate(
        DateUtils.truncate(systemDate, java.util.Calendar.DAY_OF_MONTH));

    return casArcMndtCountEntity;
  }

  public CasArcDailyBillingEntity translateAcOpsDailyBillingToArcDailyBilling(
      CasOpsDailyBillingEntity casOpsDailyBillingEntity, Date systemDate) {
    CasArcDailyBillingEntity casArcDailyBillingEntity = new CasArcDailyBillingEntity();

    casArcDailyBillingEntity.setSystemSeqNo(casOpsDailyBillingEntity.getSystemSeqNo());
    casArcDailyBillingEntity.setCreditorBank(casOpsDailyBillingEntity.getCreditorBank());
    casArcDailyBillingEntity.setDebtorBank(casOpsDailyBillingEntity.getDebtorBank());
    casArcDailyBillingEntity.setSubService(casOpsDailyBillingEntity.getSubService());
    casArcDailyBillingEntity.setTxnType(casOpsDailyBillingEntity.getTxnType());
    casArcDailyBillingEntity.setTxnStatus(casOpsDailyBillingEntity.getTxnStatus());
    casArcDailyBillingEntity.setCreatedBy(casOpsDailyBillingEntity.getCreatedBy());
    casArcDailyBillingEntity.setCreatedDate(casOpsDailyBillingEntity.getCreatedDate());
    casArcDailyBillingEntity.setBillExpStatus(casOpsDailyBillingEntity.getBillExpStatus());
    casArcDailyBillingEntity.setActionDate(casOpsDailyBillingEntity.getActionDate());
    casArcDailyBillingEntity.setAuthCode(casOpsDailyBillingEntity.getAuthCode());
//			mdtAcArcDailyBillingEntity.setArchiveDate(DateUtils.truncate(new Date(), java.util
//			.Calendar.DAY_OF_MONTH));
    casArcDailyBillingEntity.setArchiveDate(
        DateUtils.truncate(systemDate, java.util.Calendar.DAY_OF_MONTH));
    casArcDailyBillingEntity.setTxnId(casOpsDailyBillingEntity.getTxnId());
    casArcDailyBillingEntity.setMndtRefNum(casOpsDailyBillingEntity.getMndtRefNum());
    casArcDailyBillingEntity.setExtMsgId(casOpsDailyBillingEntity.getExtMsgId());
    casArcDailyBillingEntity.setRespDate(casOpsDailyBillingEntity.getRespDate());

    return casArcDailyBillingEntity;
  }
  public CasArcFileRegEntity translateOpsFileRegEntityToAcArcFileRegEntity(
      CasOpsFileRegEntity casOpsFileRegEntity, Date systemDate) {
    CasArcFileRegEntity casArcFileRegEntity = new CasArcFileRegEntity();

    casArcFileRegEntity.setExtractMsgId(casOpsFileRegEntity.getExtractMsgId());
    casArcFileRegEntity.setFileName(casOpsFileRegEntity.getFileName());
    casArcFileRegEntity.setFilepath(casOpsFileRegEntity.getGrpHdrMsgId());
    casArcFileRegEntity.setGrpHdrMsgId(casOpsFileRegEntity.getGrpHdrMsgId());
    casArcFileRegEntity.setInOutInd(casOpsFileRegEntity.getInOutInd());
    casArcFileRegEntity.setNameSpace(casOpsFileRegEntity.getNameSpace());
    casArcFileRegEntity.setOnlineInd(casOpsFileRegEntity.getOnlineInd());
    casArcFileRegEntity.setProcessDate(casOpsFileRegEntity.getProcessDate());
    casArcFileRegEntity.setReason(casOpsFileRegEntity.getReason());
    casArcFileRegEntity.setStatus(casOpsFileRegEntity.getStatus());
    casArcFileRegEntity.setArchiveDate(
        DateUtils.truncate(systemDate, java.util.Calendar.DAY_OF_MONTH));

    return casArcFileRegEntity;
  }


}
