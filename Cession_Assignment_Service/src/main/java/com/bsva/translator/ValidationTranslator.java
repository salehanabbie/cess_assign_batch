package com.bsva.translator;

import com.bsva.entities.CasOpsFileRegEntity;
import com.bsva.entities.MdtAcArcConfDetailsEntity;
import com.bsva.entities.MdtAcArcConfHdrsEntity;
import com.bsva.entities.MdtAcArcDailyBillingEntity;
import com.bsva.entities.MdtAcArcFileRegEntity;
import com.bsva.entities.MdtAcArcGrpHdrEntity;
import com.bsva.entities.MdtAcArcMndtCountEntity;
import com.bsva.entities.MdtAcArcMndtCountPK;
import com.bsva.entities.MdtAcArcStatusDetailsEntity;
import com.bsva.entities.MdtAcArcStatusHdrsEntity;
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

  public MdtAcArcStatusDetailsEntity translateAcOpsStsDtlsToArchiveStatusDetails(
      CasOpsStatusDetailsEntity casOpsStatusDetailsEntity, Date systemDate) {
    MdtAcArcStatusDetailsEntity mdtAcArcStatusDetailsEntity = new MdtAcArcStatusDetailsEntity();

    mdtAcArcStatusDetailsEntity.setSystemSeqNo(
        casOpsStatusDetailsEntity.getSystemSeqNo().longValue());
    mdtAcArcStatusDetailsEntity.setStatusHdrSeqNo(
        casOpsStatusDetailsEntity.getStatusHdrSeqNo().longValue());
    mdtAcArcStatusDetailsEntity.setErrorCode(casOpsStatusDetailsEntity.getErrorCode());
    mdtAcArcStatusDetailsEntity.setTxnId(casOpsStatusDetailsEntity.getTxnId());
    mdtAcArcStatusDetailsEntity.setTxnStatus(casOpsStatusDetailsEntity.getTxnStatus());
    mdtAcArcStatusDetailsEntity.setErrorType(casOpsStatusDetailsEntity.getErrorType());
    mdtAcArcStatusDetailsEntity.setMandateRefNumber(
        mdtAcArcStatusDetailsEntity.getMandateRefNumber());
    mdtAcArcStatusDetailsEntity.setInstId(casOpsStatusDetailsEntity.getInstId());
    mdtAcArcStatusDetailsEntity.setProcessStatus(casOpsStatusDetailsEntity.getProcessStatus());
    mdtAcArcStatusDetailsEntity.setDebtorBranchNo(casOpsStatusDetailsEntity.getDebtorBranchNo());
    mdtAcArcStatusDetailsEntity.setCrAbbShortName(casOpsStatusDetailsEntity.getCrAbbShortName());
//		mdtAcArcStatusDetailsEntity.setArchiveDate(DateUtils.truncate(new Date(), java.util
//		.Calendar.DAY_OF_MONTH));
    mdtAcArcStatusDetailsEntity.setArchiveDate(
        DateUtils.truncate(systemDate, java.util.Calendar.DAY_OF_MONTH));

    return mdtAcArcStatusDetailsEntity;
  }

  public CasOpsStatusDetailsEntity translateMdtAcArcStatusDetailsEntityToAcOpsStatusDetails(
      MdtAcArcStatusDetailsEntity mdtAcArcStatusDetailsEntity) {
    CasOpsStatusDetailsEntity casOpsStatusDetailsEntity = new CasOpsStatusDetailsEntity();

    casOpsStatusDetailsEntity.setEndToEndId(mdtAcArcStatusDetailsEntity.getEndToEndId());
    casOpsStatusDetailsEntity.setErrorCode(mdtAcArcStatusDetailsEntity.getErrorCode());
    casOpsStatusDetailsEntity.setErrorType(mdtAcArcStatusDetailsEntity.getErrorType());
    casOpsStatusDetailsEntity.setInstId(mdtAcArcStatusDetailsEntity.getInstId());
    casOpsStatusDetailsEntity.setMandateRefNumber(
        mdtAcArcStatusDetailsEntity.getMandateRefNumber());
    casOpsStatusDetailsEntity.setOrgnlTxnSeqNo(
        new BigDecimal(mdtAcArcStatusDetailsEntity.getOrgnlTxnSeqNo()));
    casOpsStatusDetailsEntity.setProcessStatus(mdtAcArcStatusDetailsEntity.getProcessStatus());
    casOpsStatusDetailsEntity.setRecordId(mdtAcArcStatusDetailsEntity.getRecordId());
    casOpsStatusDetailsEntity.setStatusHdrSeqNo(
        new BigDecimal(mdtAcArcStatusDetailsEntity.getStatusHdrSeqNo()));
    casOpsStatusDetailsEntity.setSystemSeqNo(
        new BigDecimal(mdtAcArcStatusDetailsEntity.getSystemSeqNo()));
    casOpsStatusDetailsEntity.setTxnId(mdtAcArcStatusDetailsEntity.getTxnId());
    casOpsStatusDetailsEntity.setTxnStatus(mdtAcArcStatusDetailsEntity.getTxnStatus());

    return casOpsStatusDetailsEntity;
  }


  public MdtAcArcStatusHdrsEntity translateMdtAcOpsStatusHrdsToArchiveStatusHdrs(
      CasOpsStatusHdrsEntity casOpsStatusHdrsEntity, Date systemDate) {
    MdtAcArcStatusHdrsEntity mdtAcArcStatusHdrsEntity = new MdtAcArcStatusHdrsEntity();

    mdtAcArcStatusHdrsEntity.setCreateDateTime(casOpsStatusHdrsEntity.getCreateDateTime());
    mdtAcArcStatusHdrsEntity.setGroupStatus(casOpsStatusHdrsEntity.getGroupStatus());
    mdtAcArcStatusHdrsEntity.setHdrMsgId(casOpsStatusHdrsEntity.getHdrMsgId());
    mdtAcArcStatusHdrsEntity.setInstdAgt(casOpsStatusHdrsEntity.getInstdAgt());
    mdtAcArcStatusHdrsEntity.setInstgAgt(casOpsStatusHdrsEntity.getInstgAgt());
    mdtAcArcStatusHdrsEntity.setOrgnlCntlSum(casOpsStatusHdrsEntity.getOrgnlCntlSum());
    mdtAcArcStatusHdrsEntity.setOrgnlCreateDateTime(
        casOpsStatusHdrsEntity.getOrgnlCreateDateTime());
    mdtAcArcStatusHdrsEntity.setOrgnlMsgId(casOpsStatusHdrsEntity.getOrgnlMsgId());
    mdtAcArcStatusHdrsEntity.setOrgnlMsgName(casOpsStatusHdrsEntity.getOrgnlMsgName());
    mdtAcArcStatusHdrsEntity.setOrgnlNoOfTxns(casOpsStatusHdrsEntity.getOrgnlNoOfTxns());
    mdtAcArcStatusHdrsEntity.setProcessStatus(casOpsStatusHdrsEntity.getProcessStatus());
    mdtAcArcStatusHdrsEntity.setService(casOpsStatusHdrsEntity.getService());
    mdtAcArcStatusHdrsEntity.setSystemSeqNo(casOpsStatusHdrsEntity.getSystemSeqNo().longValue());
    mdtAcArcStatusHdrsEntity.setVetRunNumber(casOpsStatusHdrsEntity.getVetRunNumber());
    mdtAcArcStatusHdrsEntity.setWorkunitRefNo(casOpsStatusHdrsEntity.getWorkunitRefNo());
//	mdtAcArcStatusHdrsEntity.setArchiveDate(DateUtils.truncate(new Date(), java.util.Calendar
//	.DAY_OF_MONTH));
//	mdtAcArcStatusHdrsEntity.setArchiveDate(systemDate);
    mdtAcArcStatusHdrsEntity.setArchiveDate(
        DateUtils.truncate(systemDate, java.util.Calendar.DAY_OF_MONTH));

    return mdtAcArcStatusHdrsEntity;

  }

// AC Archive translation.************************************************


  public MdtAcArcGrpHdrEntity translateMdtAcOpsGrpHdrEntityToAcArcGrpHdr(
      CasOpsGrpHdrEntity casOpsGrpHdrEntity, Date systemDate) {

    MdtAcArcGrpHdrEntity mdtAcArcOpsGrpHdrEntity = new MdtAcArcGrpHdrEntity();

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


  public MdtAcArcConfDetailsEntity transalteConfStatusDetailsToArcConfStatusDetsils(
      CasOpsConfDetailsEntity mdtAcOpsConfDetailsEntity, Date systemDate) {
    MdtAcArcConfDetailsEntity mdtAcArcConfDetailsEntity = new MdtAcArcConfDetailsEntity();

    mdtAcArcConfDetailsEntity.setSystemSeqNo(mdtAcOpsConfDetailsEntity.getSystemSeqNo());
    mdtAcArcConfDetailsEntity.setConfHdrSeqNo(
        mdtAcOpsConfDetailsEntity.getConfHdrSeqNo().longValue());
    mdtAcArcConfDetailsEntity.setErrorCode(mdtAcOpsConfDetailsEntity.getErrorCode());
    mdtAcArcConfDetailsEntity.setTxnId(mdtAcOpsConfDetailsEntity.getTxnId());
    mdtAcArcConfDetailsEntity.setTxnStatus(mdtAcOpsConfDetailsEntity.getTxnStatus());
    mdtAcArcConfDetailsEntity.setErrorType(mdtAcOpsConfDetailsEntity.getErrorType());
    mdtAcArcConfDetailsEntity.setRecordId(mdtAcOpsConfDetailsEntity.getRecordId());
    mdtAcArcConfDetailsEntity.setMandateRefNumber(mdtAcOpsConfDetailsEntity.getMandateRefNumber());
    mdtAcArcConfDetailsEntity.setInstId(mdtAcOpsConfDetailsEntity.getInstId());
    mdtAcArcConfDetailsEntity.setProcessStatus(mdtAcOpsConfDetailsEntity.getProcessStatus());
    mdtAcArcConfDetailsEntity.setExtractService(mdtAcOpsConfDetailsEntity.getExtractService());
//			mdtAcArcConfDetailsEntity.setArchiveDate(DateUtils.truncate(new Date(), java.util
//			.Calendar.DAY_OF_MONTH));
    mdtAcArcConfDetailsEntity.setArchiveDate(
        DateUtils.truncate(systemDate, java.util.Calendar.DAY_OF_MONTH));
    mdtAcArcConfDetailsEntity.setOrgnlMsgType(mdtAcOpsConfDetailsEntity.getOrgnlMsgType());
    mdtAcArcConfDetailsEntity.setExtractMsgId(mdtAcOpsConfDetailsEntity.getExtractMsgId());
    mdtAcArcConfDetailsEntity.setLocalInstrCd(mdtAcOpsConfDetailsEntity.getLocalInstrCd());

    return mdtAcArcConfDetailsEntity;
  }

  public MdtAcArcConfHdrsEntity transalteConfSHdrsToArcConfHdrs(
      CasOpsConfHdrsEntity casOpsConfHdrsEntity, Date systemDate) {
    MdtAcArcConfHdrsEntity mdtAcArcConfHdrsEntity = new MdtAcArcConfHdrsEntity();

    mdtAcArcConfHdrsEntity.setSystemSeqNo(casOpsConfHdrsEntity.getSystemSeqNo());
    mdtAcArcConfHdrsEntity.setHdrMsgId(casOpsConfHdrsEntity.getHdrMsgId());
    mdtAcArcConfHdrsEntity.setCreateDateTime(casOpsConfHdrsEntity.getCreateDateTime());
    mdtAcArcConfHdrsEntity.setInstgAgt(casOpsConfHdrsEntity.getInstgAgt());
    mdtAcArcConfHdrsEntity.setInstdAgt(casOpsConfHdrsEntity.getInstdAgt());
    mdtAcArcConfHdrsEntity.setOrgnlMsgId(casOpsConfHdrsEntity.getOrgnlMsgId());
    mdtAcArcConfHdrsEntity.setOrgnlMsgName(casOpsConfHdrsEntity.getOrgnlMsgName());
    mdtAcArcConfHdrsEntity.setOrgnlCreateDateTime(casOpsConfHdrsEntity.getCreateDateTime());
    mdtAcArcConfHdrsEntity.setProcessStatus(casOpsConfHdrsEntity.getProcessStatus());
    mdtAcArcConfHdrsEntity.setService(casOpsConfHdrsEntity.getService());
    mdtAcArcConfHdrsEntity.setGroupStatus(casOpsConfHdrsEntity.getGroupStatus());
    mdtAcArcConfHdrsEntity.setGroupError(casOpsConfHdrsEntity.getGroupError());
//			mdtAcArcConfHdrsEntity.setArchiveDate(DateUtils.truncate(new Date(), java.util
//			.Calendar.DAY_OF_MONTH));
    mdtAcArcConfHdrsEntity.setArchiveDate(
        DateUtils.truncate(systemDate, java.util.Calendar.DAY_OF_MONTH));

    return mdtAcArcConfHdrsEntity;
  }

  public MdtAcArcMndtCountEntity translateAcOpsMndtCountEntityToAcArcMndtCount(
      CasOpsMndtCountEntity casOpsMndtCountEntity, Date systemDate) {
    MdtAcArcMndtCountEntity mdtAcArcMndtCountEntity = new MdtAcArcMndtCountEntity();
    MdtAcArcMndtCountPK mdtAcArcMndtCountPK = new MdtAcArcMndtCountPK();

    mdtAcArcMndtCountPK.setInstId(casOpsMndtCountEntity.getCasOpsMndtCountPK().getInstId());
    mdtAcArcMndtCountPK.setMsgId(casOpsMndtCountEntity.getCasOpsMndtCountPK().getMsgId());
    mdtAcArcMndtCountPK.setServiceId(
        casOpsMndtCountEntity.getCasOpsMndtCountPK().getServiceId());

    mdtAcArcMndtCountEntity.setMdtAcArcMndtCountPK(mdtAcArcMndtCountPK);

    mdtAcArcMndtCountEntity.setFileName(casOpsMndtCountEntity.getFileName());
    mdtAcArcMndtCountEntity.setIncoming(casOpsMndtCountEntity.getIncoming());
    mdtAcArcMndtCountEntity.setNrMsgsAccepted(casOpsMndtCountEntity.getNrMsgsAccepted());
    mdtAcArcMndtCountEntity.setNrMsgsExtracted(casOpsMndtCountEntity.getNrMsgsExtracted());
    mdtAcArcMndtCountEntity.setNrMsgsRejected(casOpsMndtCountEntity.getNrMsgsRejected());
    mdtAcArcMndtCountEntity.setNrOfFiles(casOpsMndtCountEntity.getNrOfFiles());
    mdtAcArcMndtCountEntity.setNrOfMsgs(casOpsMndtCountEntity.getNrOfMsgs());
    mdtAcArcMndtCountEntity.setOutgoing(casOpsMndtCountEntity.getOutgoing());
//			mdtAcArcMndtCountEntity.setProcessDate(DateUtils.truncate(new Date(), java.util
//			.Calendar.DAY_OF_MONTH));
    mdtAcArcMndtCountEntity.setProcessDate(
        DateUtils.truncate(systemDate, java.util.Calendar.DAY_OF_MONTH));

    return mdtAcArcMndtCountEntity;
  }

  public MdtAcArcDailyBillingEntity translateAcOpsDailyBillingToArcDailyBilling(
      CasOpsDailyBillingEntity casOpsDailyBillingEntity, Date systemDate) {
    MdtAcArcDailyBillingEntity mdtAcArcDailyBillingEntity = new MdtAcArcDailyBillingEntity();

    mdtAcArcDailyBillingEntity.setSystemSeqNo(casOpsDailyBillingEntity.getSystemSeqNo());
    mdtAcArcDailyBillingEntity.setCreditorBank(casOpsDailyBillingEntity.getCreditorBank());
    mdtAcArcDailyBillingEntity.setDebtorBank(casOpsDailyBillingEntity.getDebtorBank());
    mdtAcArcDailyBillingEntity.setSubService(casOpsDailyBillingEntity.getSubService());
    mdtAcArcDailyBillingEntity.setTxnType(casOpsDailyBillingEntity.getTxnType());
    mdtAcArcDailyBillingEntity.setTxnStatus(casOpsDailyBillingEntity.getTxnStatus());
    mdtAcArcDailyBillingEntity.setCreatedBy(casOpsDailyBillingEntity.getCreatedBy());
    mdtAcArcDailyBillingEntity.setCreatedDate(casOpsDailyBillingEntity.getCreatedDate());
    mdtAcArcDailyBillingEntity.setBillExpStatus(casOpsDailyBillingEntity.getBillExpStatus());
    mdtAcArcDailyBillingEntity.setActionDate(casOpsDailyBillingEntity.getActionDate());
    mdtAcArcDailyBillingEntity.setAuthCode(casOpsDailyBillingEntity.getAuthCode());
//			mdtAcArcDailyBillingEntity.setArchiveDate(DateUtils.truncate(new Date(), java.util
//			.Calendar.DAY_OF_MONTH));
    mdtAcArcDailyBillingEntity.setArchiveDate(
        DateUtils.truncate(systemDate, java.util.Calendar.DAY_OF_MONTH));
    mdtAcArcDailyBillingEntity.setTxnId(casOpsDailyBillingEntity.getTxnId());
    mdtAcArcDailyBillingEntity.setMndtRefNum(casOpsDailyBillingEntity.getMndtRefNum());
    mdtAcArcDailyBillingEntity.setExtMsgId(casOpsDailyBillingEntity.getExtMsgId());
    mdtAcArcDailyBillingEntity.setRespDate(casOpsDailyBillingEntity.getRespDate());

    return mdtAcArcDailyBillingEntity;
  }
  public MdtAcArcFileRegEntity translateOpsFileRegEntityToAcArcFileRegEntity(
      CasOpsFileRegEntity casOpsFileRegEntity, Date systemDate) {
    MdtAcArcFileRegEntity mdtAcArcFileRegEntity = new MdtAcArcFileRegEntity();

    mdtAcArcFileRegEntity.setExtractMsgId(casOpsFileRegEntity.getExtractMsgId());
    mdtAcArcFileRegEntity.setFileName(casOpsFileRegEntity.getFileName());
    mdtAcArcFileRegEntity.setFilepath(casOpsFileRegEntity.getGrpHdrMsgId());
    mdtAcArcFileRegEntity.setGrpHdrMsgId(casOpsFileRegEntity.getGrpHdrMsgId());
    mdtAcArcFileRegEntity.setInOutInd(casOpsFileRegEntity.getInOutInd());
    mdtAcArcFileRegEntity.setNameSpace(casOpsFileRegEntity.getNameSpace());
    mdtAcArcFileRegEntity.setOnlineInd(casOpsFileRegEntity.getOnlineInd());
    mdtAcArcFileRegEntity.setProcessDate(casOpsFileRegEntity.getProcessDate());
    mdtAcArcFileRegEntity.setReason(casOpsFileRegEntity.getReason());
    mdtAcArcFileRegEntity.setStatus(casOpsFileRegEntity.getStatus());
    mdtAcArcFileRegEntity.setArchiveDate(
        DateUtils.truncate(systemDate, java.util.Calendar.DAY_OF_MONTH));

    return mdtAcArcFileRegEntity;
  }


}
