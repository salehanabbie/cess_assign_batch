package com.bsva.translator;

import com.bsva.entities.MdtAcArcConfDetailsEntity;
import com.bsva.entities.MdtAcArcConfHdrsEntity;
import com.bsva.entities.MdtAcArcDailyBillingEntity;
import com.bsva.entities.MdtAcArcFileRegEntity;
import com.bsva.entities.MdtAcArcGrpHdrEntity;
import com.bsva.entities.MdtAcArcMndtCountEntity;
import com.bsva.entities.MdtAcArcMndtCountPK;
import com.bsva.entities.MdtAcArcStatusDetailsEntity;
import com.bsva.entities.MdtAcArcStatusHdrsEntity;
import com.bsva.entities.MdtAcOpsConfDetailsEntity;
import com.bsva.entities.MdtAcOpsConfHdrsEntity;
import com.bsva.entities.MdtAcOpsDailyBillingEntity;
import com.bsva.entities.MdtAcOpsGrpHdrEntity;
import com.bsva.entities.MdtAcOpsMndtCountEntity;
import com.bsva.entities.MdtAcOpsStatusDetailsEntity;
import com.bsva.entities.MdtAcOpsStatusHdrsEntity;
import com.bsva.entities.MdtOpsFileRegEntity;
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
      MdtAcOpsStatusDetailsEntity mdtAcOpsStatusDetailsEntity, Date systemDate) {
    MdtAcArcStatusDetailsEntity mdtAcArcStatusDetailsEntity = new MdtAcArcStatusDetailsEntity();

    mdtAcArcStatusDetailsEntity.setSystemSeqNo(
        mdtAcOpsStatusDetailsEntity.getSystemSeqNo().longValue());
    mdtAcArcStatusDetailsEntity.setStatusHdrSeqNo(
        mdtAcOpsStatusDetailsEntity.getStatusHdrSeqNo().longValue());
    mdtAcArcStatusDetailsEntity.setErrorCode(mdtAcOpsStatusDetailsEntity.getErrorCode());
    mdtAcArcStatusDetailsEntity.setTxnId(mdtAcOpsStatusDetailsEntity.getTxnId());
    mdtAcArcStatusDetailsEntity.setTxnStatus(mdtAcOpsStatusDetailsEntity.getTxnStatus());
    mdtAcArcStatusDetailsEntity.setErrorType(mdtAcOpsStatusDetailsEntity.getErrorType());
    mdtAcArcStatusDetailsEntity.setMandateRefNumber(
        mdtAcArcStatusDetailsEntity.getMandateRefNumber());
    mdtAcArcStatusDetailsEntity.setInstId(mdtAcOpsStatusDetailsEntity.getInstId());
    mdtAcArcStatusDetailsEntity.setProcessStatus(mdtAcOpsStatusDetailsEntity.getProcessStatus());
    mdtAcArcStatusDetailsEntity.setDebtorBranchNo(mdtAcOpsStatusDetailsEntity.getDebtorBranchNo());
    mdtAcArcStatusDetailsEntity.setCrAbbShortName(mdtAcOpsStatusDetailsEntity.getCrAbbShortName());
//		mdtAcArcStatusDetailsEntity.setArchiveDate(DateUtils.truncate(new Date(), java.util
//		.Calendar.DAY_OF_MONTH));
    mdtAcArcStatusDetailsEntity.setArchiveDate(
        DateUtils.truncate(systemDate, java.util.Calendar.DAY_OF_MONTH));

    return mdtAcArcStatusDetailsEntity;
  }

  public MdtAcOpsStatusDetailsEntity translateMdtAcArcStatusDetailsEntityToAcOpsStatusDetails(
      MdtAcArcStatusDetailsEntity mdtAcArcStatusDetailsEntity) {
    MdtAcOpsStatusDetailsEntity mdtAcOpsStatusDetailsEntity = new MdtAcOpsStatusDetailsEntity();

    mdtAcOpsStatusDetailsEntity.setEndToEndId(mdtAcArcStatusDetailsEntity.getEndToEndId());
    mdtAcOpsStatusDetailsEntity.setErrorCode(mdtAcArcStatusDetailsEntity.getErrorCode());
    mdtAcOpsStatusDetailsEntity.setErrorType(mdtAcArcStatusDetailsEntity.getErrorType());
    mdtAcOpsStatusDetailsEntity.setInstId(mdtAcArcStatusDetailsEntity.getInstId());
    mdtAcOpsStatusDetailsEntity.setMandateRefNumber(
        mdtAcArcStatusDetailsEntity.getMandateRefNumber());
    mdtAcOpsStatusDetailsEntity.setOrgnlTxnSeqNo(
        new BigDecimal(mdtAcArcStatusDetailsEntity.getOrgnlTxnSeqNo()));
    mdtAcOpsStatusDetailsEntity.setProcessStatus(mdtAcArcStatusDetailsEntity.getProcessStatus());
    mdtAcOpsStatusDetailsEntity.setRecordId(mdtAcArcStatusDetailsEntity.getRecordId());
    mdtAcOpsStatusDetailsEntity.setStatusHdrSeqNo(
        new BigDecimal(mdtAcArcStatusDetailsEntity.getStatusHdrSeqNo()));
    mdtAcOpsStatusDetailsEntity.setSystemSeqNo(
        new BigDecimal(mdtAcArcStatusDetailsEntity.getSystemSeqNo()));
    mdtAcOpsStatusDetailsEntity.setTxnId(mdtAcArcStatusDetailsEntity.getTxnId());
    mdtAcOpsStatusDetailsEntity.setTxnStatus(mdtAcArcStatusDetailsEntity.getTxnStatus());

    return mdtAcOpsStatusDetailsEntity;
  }


  public MdtAcArcStatusHdrsEntity translateMdtAcOpsStatusHrdsToArchiveStatusHdrs(
      MdtAcOpsStatusHdrsEntity mdtAcOpsStatusHdrsEntity, Date systemDate) {
    MdtAcArcStatusHdrsEntity mdtAcArcStatusHdrsEntity = new MdtAcArcStatusHdrsEntity();

    mdtAcArcStatusHdrsEntity.setCreateDateTime(mdtAcOpsStatusHdrsEntity.getCreateDateTime());
    mdtAcArcStatusHdrsEntity.setGroupStatus(mdtAcOpsStatusHdrsEntity.getGroupStatus());
    mdtAcArcStatusHdrsEntity.setHdrMsgId(mdtAcOpsStatusHdrsEntity.getHdrMsgId());
    mdtAcArcStatusHdrsEntity.setInstdAgt(mdtAcOpsStatusHdrsEntity.getInstdAgt());
    mdtAcArcStatusHdrsEntity.setInstgAgt(mdtAcOpsStatusHdrsEntity.getInstgAgt());
    mdtAcArcStatusHdrsEntity.setOrgnlCntlSum(mdtAcOpsStatusHdrsEntity.getOrgnlCntlSum());
    mdtAcArcStatusHdrsEntity.setOrgnlCreateDateTime(
        mdtAcOpsStatusHdrsEntity.getOrgnlCreateDateTime());
    mdtAcArcStatusHdrsEntity.setOrgnlMsgId(mdtAcOpsStatusHdrsEntity.getOrgnlMsgId());
    mdtAcArcStatusHdrsEntity.setOrgnlMsgName(mdtAcOpsStatusHdrsEntity.getOrgnlMsgName());
    mdtAcArcStatusHdrsEntity.setOrgnlNoOfTxns(mdtAcOpsStatusHdrsEntity.getOrgnlNoOfTxns());
    mdtAcArcStatusHdrsEntity.setProcessStatus(mdtAcOpsStatusHdrsEntity.getProcessStatus());
    mdtAcArcStatusHdrsEntity.setService(mdtAcOpsStatusHdrsEntity.getService());
    mdtAcArcStatusHdrsEntity.setSystemSeqNo(mdtAcOpsStatusHdrsEntity.getSystemSeqNo().longValue());
    mdtAcArcStatusHdrsEntity.setVetRunNumber(mdtAcOpsStatusHdrsEntity.getVetRunNumber());
    mdtAcArcStatusHdrsEntity.setWorkunitRefNo(mdtAcOpsStatusHdrsEntity.getWorkunitRefNo());
//	mdtAcArcStatusHdrsEntity.setArchiveDate(DateUtils.truncate(new Date(), java.util.Calendar
//	.DAY_OF_MONTH));
//	mdtAcArcStatusHdrsEntity.setArchiveDate(systemDate);
    mdtAcArcStatusHdrsEntity.setArchiveDate(
        DateUtils.truncate(systemDate, java.util.Calendar.DAY_OF_MONTH));

    return mdtAcArcStatusHdrsEntity;

  }

// AC Archive translation.************************************************


  public MdtAcArcGrpHdrEntity translateMdtAcOpsGrpHdrEntityToAcArcGrpHdr(
      MdtAcOpsGrpHdrEntity mdtAcOpsGrpHdrEntity, Date systemDate) {

    MdtAcArcGrpHdrEntity mdtAcArcOpsGrpHdrEntity = new MdtAcArcGrpHdrEntity();

    if (mdtAcOpsGrpHdrEntity.getAuthCode() != null) {
      mdtAcArcOpsGrpHdrEntity.setAuthCode(mdtAcOpsGrpHdrEntity.getAuthCode());
    }
    mdtAcArcOpsGrpHdrEntity.setCreateDateTime(mdtAcOpsGrpHdrEntity.getCreateDateTime());
    mdtAcArcOpsGrpHdrEntity.setMsgId(mdtAcOpsGrpHdrEntity.getMsgId());
    mdtAcArcOpsGrpHdrEntity.setCreatedBy(mdtAcOpsGrpHdrEntity.getCreatedBy());
//		mdtAcArcOpsGrpHdrEntity.setArchiveDate(DateUtils.truncate(new Date(), java.util.Calendar
//		.DAY_OF_MONTH));
    mdtAcArcOpsGrpHdrEntity.setArchiveDate(
        DateUtils.truncate(systemDate, java.util.Calendar.DAY_OF_MONTH));

    return mdtAcArcOpsGrpHdrEntity;

  }


  public MdtAcArcConfDetailsEntity transalteConfStatusDetailsToArcConfStatusDetsils(
      MdtAcOpsConfDetailsEntity mdtAcOpsConfDetailsEntity, Date systemDate) {
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
      MdtAcOpsConfHdrsEntity mdtAcOpsConfHdrsEntity, Date systemDate) {
    MdtAcArcConfHdrsEntity mdtAcArcConfHdrsEntity = new MdtAcArcConfHdrsEntity();

    mdtAcArcConfHdrsEntity.setSystemSeqNo(mdtAcOpsConfHdrsEntity.getSystemSeqNo());
    mdtAcArcConfHdrsEntity.setHdrMsgId(mdtAcOpsConfHdrsEntity.getHdrMsgId());
    mdtAcArcConfHdrsEntity.setCreateDateTime(mdtAcOpsConfHdrsEntity.getCreateDateTime());
    mdtAcArcConfHdrsEntity.setInstgAgt(mdtAcOpsConfHdrsEntity.getInstgAgt());
    mdtAcArcConfHdrsEntity.setInstdAgt(mdtAcOpsConfHdrsEntity.getInstdAgt());
    mdtAcArcConfHdrsEntity.setOrgnlMsgId(mdtAcOpsConfHdrsEntity.getOrgnlMsgId());
    mdtAcArcConfHdrsEntity.setOrgnlMsgName(mdtAcOpsConfHdrsEntity.getOrgnlMsgName());
    mdtAcArcConfHdrsEntity.setOrgnlCreateDateTime(mdtAcOpsConfHdrsEntity.getCreateDateTime());
    mdtAcArcConfHdrsEntity.setProcessStatus(mdtAcOpsConfHdrsEntity.getProcessStatus());
    mdtAcArcConfHdrsEntity.setService(mdtAcOpsConfHdrsEntity.getService());
    mdtAcArcConfHdrsEntity.setGroupStatus(mdtAcOpsConfHdrsEntity.getGroupStatus());
    mdtAcArcConfHdrsEntity.setGroupError(mdtAcOpsConfHdrsEntity.getGroupError());
//			mdtAcArcConfHdrsEntity.setArchiveDate(DateUtils.truncate(new Date(), java.util
//			.Calendar.DAY_OF_MONTH));
    mdtAcArcConfHdrsEntity.setArchiveDate(
        DateUtils.truncate(systemDate, java.util.Calendar.DAY_OF_MONTH));

    return mdtAcArcConfHdrsEntity;
  }

  public MdtAcArcMndtCountEntity translateAcOpsMndtCountEntityToAcArcMndtCount(
      MdtAcOpsMndtCountEntity mdtAcOpsMndtCountEntity, Date systemDate) {
    MdtAcArcMndtCountEntity mdtAcArcMndtCountEntity = new MdtAcArcMndtCountEntity();
    MdtAcArcMndtCountPK mdtAcArcMndtCountPK = new MdtAcArcMndtCountPK();

    mdtAcArcMndtCountPK.setInstId(mdtAcOpsMndtCountEntity.getMdtAcOpsMndtCountPK().getInstId());
    mdtAcArcMndtCountPK.setMsgId(mdtAcOpsMndtCountEntity.getMdtAcOpsMndtCountPK().getMsgId());
    mdtAcArcMndtCountPK.setServiceId(
        mdtAcOpsMndtCountEntity.getMdtAcOpsMndtCountPK().getServiceId());

    mdtAcArcMndtCountEntity.setMdtAcArcMndtCountPK(mdtAcArcMndtCountPK);

    mdtAcArcMndtCountEntity.setFileName(mdtAcOpsMndtCountEntity.getFileName());
    mdtAcArcMndtCountEntity.setIncoming(mdtAcOpsMndtCountEntity.getIncoming());
    mdtAcArcMndtCountEntity.setNrMsgsAccepted(mdtAcOpsMndtCountEntity.getNrMsgsAccepted());
    mdtAcArcMndtCountEntity.setNrMsgsExtracted(mdtAcOpsMndtCountEntity.getNrMsgsExtracted());
    mdtAcArcMndtCountEntity.setNrMsgsRejected(mdtAcOpsMndtCountEntity.getNrMsgsRejected());
    mdtAcArcMndtCountEntity.setNrOfFiles(mdtAcOpsMndtCountEntity.getNrOfFiles());
    mdtAcArcMndtCountEntity.setNrOfMsgs(mdtAcOpsMndtCountEntity.getNrOfMsgs());
    mdtAcArcMndtCountEntity.setOutgoing(mdtAcOpsMndtCountEntity.getOutgoing());
//			mdtAcArcMndtCountEntity.setProcessDate(DateUtils.truncate(new Date(), java.util
//			.Calendar.DAY_OF_MONTH));
    mdtAcArcMndtCountEntity.setProcessDate(
        DateUtils.truncate(systemDate, java.util.Calendar.DAY_OF_MONTH));

    return mdtAcArcMndtCountEntity;
  }

  public MdtAcArcDailyBillingEntity translateAcOpsDailyBillingToArcDailyBilling(
      MdtAcOpsDailyBillingEntity mdtAcOpsDailyBillingEntity, Date systemDate) {
    MdtAcArcDailyBillingEntity mdtAcArcDailyBillingEntity = new MdtAcArcDailyBillingEntity();

    mdtAcArcDailyBillingEntity.setSystemSeqNo(mdtAcOpsDailyBillingEntity.getSystemSeqNo());
    mdtAcArcDailyBillingEntity.setCreditorBank(mdtAcOpsDailyBillingEntity.getCreditorBank());
    mdtAcArcDailyBillingEntity.setDebtorBank(mdtAcOpsDailyBillingEntity.getDebtorBank());
    mdtAcArcDailyBillingEntity.setSubService(mdtAcOpsDailyBillingEntity.getSubService());
    mdtAcArcDailyBillingEntity.setTxnType(mdtAcOpsDailyBillingEntity.getTxnType());
    mdtAcArcDailyBillingEntity.setTxnStatus(mdtAcOpsDailyBillingEntity.getTxnStatus());
    mdtAcArcDailyBillingEntity.setCreatedBy(mdtAcOpsDailyBillingEntity.getCreatedBy());
    mdtAcArcDailyBillingEntity.setCreatedDate(mdtAcOpsDailyBillingEntity.getCreatedDate());
    mdtAcArcDailyBillingEntity.setBillExpStatus(mdtAcOpsDailyBillingEntity.getBillExpStatus());
    mdtAcArcDailyBillingEntity.setActionDate(mdtAcOpsDailyBillingEntity.getActionDate());
    mdtAcArcDailyBillingEntity.setAuthCode(mdtAcOpsDailyBillingEntity.getAuthCode());
//			mdtAcArcDailyBillingEntity.setArchiveDate(DateUtils.truncate(new Date(), java.util
//			.Calendar.DAY_OF_MONTH));
    mdtAcArcDailyBillingEntity.setArchiveDate(
        DateUtils.truncate(systemDate, java.util.Calendar.DAY_OF_MONTH));
    mdtAcArcDailyBillingEntity.setTxnId(mdtAcOpsDailyBillingEntity.getTxnId());
    mdtAcArcDailyBillingEntity.setMndtRefNum(mdtAcOpsDailyBillingEntity.getMndtRefNum());
    mdtAcArcDailyBillingEntity.setExtMsgId(mdtAcOpsDailyBillingEntity.getExtMsgId());
    mdtAcArcDailyBillingEntity.setRespDate(mdtAcOpsDailyBillingEntity.getRespDate());

    return mdtAcArcDailyBillingEntity;
  }
  public MdtAcArcFileRegEntity translateOpsFileRegEntityToAcArcFileRegEntity(
      MdtOpsFileRegEntity mdtOpsFileRegEntity, Date systemDate) {
    MdtAcArcFileRegEntity mdtAcArcFileRegEntity = new MdtAcArcFileRegEntity();

    mdtAcArcFileRegEntity.setExtractMsgId(mdtOpsFileRegEntity.getExtractMsgId());
    mdtAcArcFileRegEntity.setFileName(mdtOpsFileRegEntity.getFileName());
    mdtAcArcFileRegEntity.setFilepath(mdtOpsFileRegEntity.getGrpHdrMsgId());
    mdtAcArcFileRegEntity.setGrpHdrMsgId(mdtOpsFileRegEntity.getGrpHdrMsgId());
    mdtAcArcFileRegEntity.setInOutInd(mdtOpsFileRegEntity.getInOutInd());
    mdtAcArcFileRegEntity.setNameSpace(mdtOpsFileRegEntity.getNameSpace());
    mdtAcArcFileRegEntity.setOnlineInd(mdtOpsFileRegEntity.getOnlineInd());
    mdtAcArcFileRegEntity.setProcessDate(mdtOpsFileRegEntity.getProcessDate());
    mdtAcArcFileRegEntity.setReason(mdtOpsFileRegEntity.getReason());
    mdtAcArcFileRegEntity.setStatus(mdtOpsFileRegEntity.getStatus());
    mdtAcArcFileRegEntity.setArchiveDate(
        DateUtils.truncate(systemDate, java.util.Calendar.DAY_OF_MONTH));

    return mdtAcArcFileRegEntity;
  }


}
