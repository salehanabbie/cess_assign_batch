
package com.bsva.authcoll.singletable.validation;

import com.bsva.PropertyUtil;
import com.bsva.entities.CasCnfgErrorCodesEntity;
import com.bsva.entities.CasOpsCessionAssignEntity;
import com.bsva.entities.CasOpsCustParamEntity;
import com.bsva.entities.CasOpsFileRegEntity;
import com.bsva.entities.CasOpsRefSeqNrEntity;
import com.bsva.entities.CasOpsStatusDetailsEntity;
import com.bsva.entities.CasOpsStatusHdrsEntity;
import com.bsva.entities.CasSysctrlCompParamEntity;
import iso.std.iso._20022.tech.xsd.pacs_002_001.FIToFIPaymentStatusReportV04;
import iso.std.iso._20022.tech.xsd.pacs_002_001.GroupHeader53;
import iso.std.iso._20022.tech.xsd.pacs_002_001.OriginalGroupHeader1;
import iso.std.iso._20022.tech.xsd.pacs_002_001.PaymentTransaction33;
import iso.std.iso._20022.tech.xsd.pacs_002_001.StatusReasonInformation9;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import javax.ejb.EJB;
import javax.xml.datatype.XMLGregorianCalendar;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author Saleha Saib
 * 2016/11/01 - Added extra val rule for TxnInfAndSts
 * @author SalehaR - Aligned to TRS 17 - 2018/03/09
 * @author SalehaR - 2018/12/31 - CHG-153240-Validate Msg Id Uniqueness on ST201/SPINP/SRINP
 * Modified by SalehaR - 2019/01/02 - CHG-153240-Validate Creation Date in Msg Id
 * @author SalehaR-2019/05/10 Debug Statements & Code CleanUp
 * @author SalehaR-2019/09/21 Aligned to Single Table(MDT_AC_OPS_MANDATE_TXNS)
 * @author SalehaR-2019/10/17 File Size Limit Validation
 */
public class AC_Pacs002_Validation_ST extends Validation_ST {
  @EJB
  PropertyUtil propertyUtil;

  public static Logger log = Logger.getLogger("AC_Pacs002_Validation_ST");

  SimpleDateFormat sdfDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
  SimpleDateFormat sdfFileDate = new SimpleDateFormat("yyyyMMdd");
  int grpHdrSeverity = 0;
  int mandateSeverity = 0;

  public static String systemName = "CAMOWNER";
  //Ac ops Status Lists
  List<CasOpsStatusHdrsEntity> opsStatusHdrsList = null;
  List<CasOpsStatusDetailsEntity> opsStatusDetailsList = null;

  //Ac Entities declaration
  CasOpsStatusHdrsEntity opsStatusHdrsEntity = null;
  CasOpsStatusDetailsEntity opsStatusDetailsEntity = null;

  boolean bicCodeValid = false;
  String msgId, msgCreateDate, incInstid, fileName, outMsgId, mandateReqId, bicfi, orgnlMsgId,
      orgnlMsgNameId, orgnlCtrlSum, grpStatus, orgnlTxnId, messageType;

  Date convCreationDateTime;
  Date originalCreateDateTime;
  int fileNum = 0;
  String backEndProcess = "BACKEND";
  String hdrErrorType = "HDR";
  String txnErrorType = "TXN";
  String extractStatus = "4";
  String matchedStatus = "M";
  String rejectedStatus = "R";
  String responseRecStatus = "9";

  GroupHeader53 groupHeader;
  OriginalGroupHeader1 origGrpInfStats;
  private String systemType;
  String stsHdrInstgAgt = null;
  public BigDecimal hdrSystemSeqNo = BigDecimal.ZERO;

  String fileSizeLimitStr = null;
  Integer fileSizeLimit;

  //Populate Error Codes Report Data
  String debtorBank = null, creditorBank = null, ultCreditor = null, abbShortName = null,
      st201Service = null;

  public AC_Pacs002_Validation_ST(String fileName) {
    this.fileName = fileName;

    try {
      propertyUtil = new PropertyUtil();
      this.st201Service = propertyUtil.getPropValue("Input.Pacs002");
      fileSizeLimitStr = propertyUtil.getPropValue("AC.FILE.TRANSACTION.LIMIT");
//			log.info("fileSizeLimit ==> "+fileSizeLimitStr);
      fileSizeLimit = Integer.valueOf(fileSizeLimitStr);
    } catch (Exception e) {
      log.error(
          "AC_Pacs002_Validation_ST - Could not find MandateMessageCommons.properties in " +
              "classpath");
      st201Service = "ST201";
      fileSizeLimit = 50000;
    }

    opsStatusHdrsList = new ArrayList<CasOpsStatusHdrsEntity>();
    opsStatusDetailsList = new ArrayList<CasOpsStatusDetailsEntity>();

    bicCodeValid = false;
    contextValidationBeanCheck();
    contextAdminBeanCheck();
    hdrSystemSeqNo = BigDecimal.ZERO;
    log.debug("casSysctrlSysParamEntity: " + casSysctrlSysParamEntity);
    systemType = casSysctrlSysParamEntity.getSysType();
    casSysctrlCompParamEntity =
        (CasSysctrlCompParamEntity) valBeanRemote.retrieveCompanyParameters(backEndProcess);
  }

  public int validateGroupHeader(FIToFIPaymentStatusReportV04 fiPaymentStatusReportV04,
                                 Integer inwardFileSize) {
    debtorBank = null;
    creditorBank = null;
    creditorBank = casSysctrlCompParamEntity.getAchInstId();

    groupHeader = new GroupHeader53();
    groupHeader = fiPaymentStatusReportV04.getGrpHdr();

    origGrpInfStats = new OriginalGroupHeader1();
    origGrpInfStats = fiPaymentStatusReportV04.getOrgnlGrpInfAndSts();

    msgId = groupHeader.getMsgId();
    log.debug("groupHeader: " + groupHeader.getMsgId());

    stsHdrInstgAgt = null;

    if (!validateMsgIdStructure(msgId)) {
      log.debug("Write to GrpHdr ..MsgId fails....");
      incInstid = fileName.substring(8, 16);
      stsHdrInstgAgt = fileName.substring(10, 16);
      incInstid = StringUtils.stripStart(incInstid, "0");
      outMsgId = generateMsgId(incInstid);
      debtorBank = incInstid;
      grpHdrSeverity++;
      generateStatusErrorDetailsList("902134", null, hdrErrorType);
    } else {
      /*validate msgIdFormat*/
      String[] splitMsgId = splitMsgId(msgId);

      String achId =
          splitMsgId[0];                                                                                                                                /* msgId.substring(0, 3);*/
      String serviceId =
          splitMsgId[1];                                                                                                                        /*msgId.substring(4,9);*/
      incInstid =
          splitMsgId[2];                                                                                                                                        /*msgId.substring(10,18);*/
      incInstid = StringUtils.stripStart(incInstid, "0");
      debtorBank = incInstid;
      String msgCreationDate =
          splitMsgId[3];                                                                                                        /*msgId.substring(19, 27);*/
      String fileNo = splitMsgId[4];
      fileNum = Integer.valueOf(
          fileNo);                                                                                                                    /*msgId.substring(28,32);*/
      log.debug("achId: " + achId);
      log.debug("serviceId: " + serviceId);
      log.debug("inst id: " + incInstid);
      log.debug("msgCreationDate: " + msgCreationDate);
      log.debug("fileNo: " + fileNo);

      if (!validateDebtorBank(incInstid)) {
        incInstid = fileName.substring(8, 16);
        incInstid = StringUtils.stripStart(incInstid, "0");
        log.debug("inst id from File Name-->: " + incInstid);

        generateStatusErrorDetailsList("901001", null, hdrErrorType);
        grpHdrSeverity++;
        bicCodeValid = false;
        log.info("******************validateMemberId_003 - Failed.******************");
      } else {
        log.debug("******************validateMemberId_003 - Passed.******************");
        bicCodeValid = true;
        grpHdrSeverity = grpHdrSeverity + 0;
      }

      //Check MemberNumberMatchs
      String instgAgt = groupHeader.getInstgAgt().getFinInstnId().getClrSysMmbId().getMmbId();
      String msgMmId = incInstid;
      String fileMmId = fileName.substring(10, 16);
      boolean match1 = false, match2 = false;

      log.debug("instgAgt ----XXX :" + instgAgt);
      log.debug("msgMmId ----XXX :" + msgMmId);
      log.debug("fileMmId ----XXX :" + fileMmId);
      //MATCH 1 --> Match InstgAgt to MsgId
      if (instgAgt.equalsIgnoreCase(msgMmId)) {
        log.debug("******************validateInstgAgtToMsgIdMemberId - Passed.******************");
        grpHdrSeverity = grpHdrSeverity + 0;
        match1 = true;
      } else {
        match1 = false;
        generateStatusErrorDetailsList("901017", null, hdrErrorType);
        grpHdrSeverity++;
        log.info("******************validateInstgAgtToMsgIdMemberId - Failed.******************");
      }

      //Match 2 --> Match InstgAgt to FileName
      if (instgAgt.equalsIgnoreCase(fileMmId)) {
        match2 = true;
        log.debug("******************validateInstgAgtToFileMemberId - Passed.******************");
        grpHdrSeverity = grpHdrSeverity + 0;
      } else {
        match2 = false;
        generateStatusErrorDetailsList("902124", null, hdrErrorType);
        grpHdrSeverity++;
        log.info("******************validateInstgAgtToFileMemberId - Failed.******************");
      }

      if (match1 && match2) {
        stsHdrInstgAgt = incInstid;
        outMsgId = generateMsgId(incInstid);
      } else {
        stsHdrInstgAgt = fileMmId;
        outMsgId = generateMsgId(fileMmId);
      }

      //____________________________Validate Service Id _rule
      // 009_002_____________________________________//
      if (!validateServiceId(serviceId, "ST201")) {
        grpHdrSeverity++;
        generateStatusErrorDetailsList("901045", null, hdrErrorType);
        log.info("******************validateServiceId_mcon002 - Failed.******************");
      } else {
        log.debug("******************validateServiceId_mcon002 - Passed.******************");
        grpHdrSeverity = grpHdrSeverity + 0;
      }

      //____________________________Validate MsgId Creation Date
      // Time_Rule009_007_____________________________________//
//			if(!isDateValid(msgCreationDate, "yyyyMMdd"))
//			{
//				grpHdrSeverity++;
//				generateStatusErrorDetailsList("901006", null, hdrErrorType);
//				log.info("******************validateDate_Rule009_007 - Failed.******************");
//			}
//			else
//			{
//				msgCreateDate = msgCreationDate;
//				grpHdrSeverity = grpHdrSeverity+0;
//				log.debug("******************validateDate_Rule009_007 - Passed
//				.******************");
//			}

      //____________________________Validate MsgId Creation Date
      // Time_____________________________________//
      if (!isValidProcessingDate(msgCreationDate, "yyyyMMdd")) {
        generateStatusErrorDetailsList("902009", null, hdrErrorType);
        grpHdrSeverity++;
        log.info("******************isValidProcessingDate_007 - Failed.******************");
      } else {
        grpHdrSeverity = grpHdrSeverity + 0;
        log.debug("******************isValidProcessingDate_007 - Passed.******************");
      }

      //____________________________Validate Message Id Uniqueness_Rule009_006
      // _____________________________________//
      if (!validatePacs002MsgId(msgId)) {
        grpHdrSeverity++;
        generateStatusErrorDetailsList("901005", null, hdrErrorType);
        log.debug("the message Id is **************************" + msgId);
        log.info("******************validateMsgId_006 - Failed.******************");
      } else {
        grpHdrSeverity = grpHdrSeverity + 0;
        log.debug("******************validateMsgId_006 - Passed.******************");
      }

      //____________________________Validate Instructing Agent_Rule009_010
      // _____________________________________//
      if (!validateDebtorBank(
          groupHeader.getInstgAgt().getFinInstnId().getClrSysMmbId().getMmbId())) {
        grpHdrSeverity++;
        generateStatusErrorDetailsList("901017", null, hdrErrorType);
        log.info("******************validateInstructingAgent_010 - Failed.******************");
      } else {
        grpHdrSeverity = grpHdrSeverity + 0;
        log.debug("******************validateInstructingAgent_010 - Passed.******************");
      }

      //____________________________Validate Instructed Agent_Rule rjct
      // .016______________________________________//
      if (groupHeader.getInstdAgt() != null && groupHeader.getInstdAgt().getFinInstnId() != null &&
          groupHeader.getInstdAgt().getFinInstnId().getClrSysMmbId() != null &&
          groupHeader.getInstdAgt().getFinInstnId().getClrSysMmbId().getMmbId() != null) {
        String instdAgt = groupHeader.getInstdAgt().getFinInstnId().getClrSysMmbId().getMmbId();
        String achInstId;

        log.debug("the achI d is ******" + casSysctrlCompParamEntity.getAchId());
        if (casSysctrlCompParamEntity != null) {
          achInstId = casSysctrlCompParamEntity.getAchInstId();
        } else {
          achInstId = "210000";
        }

        if (!instdAgt.equalsIgnoreCase(achInstId)) {
          generateStatusErrorDetailsList("901079", null, hdrErrorType);
          grpHdrSeverity++;
          log.info(
              "******************validateInstructed Agent_rjct.016 - Failed.******************");
        } else {
          grpHdrSeverity = grpHdrSeverity + 0;
          log.debug(
              "******************validateInstructed Agent_rjct.016- Passed.******************");
        }
      } else {
        generateStatusErrorDetailsList("901079", null, hdrErrorType);
        grpHdrSeverity++;
        log.info("******************validateInstructed Agent_rjct.016 - Failed.******************");
      }

      //____________________________Validate Original Message Id -
      // Rjct999_____________________________________//
      if (origGrpInfStats.getOrgnlMsgId() != null) {
        if (validateRule999(origGrpInfStats.getOrgnlMsgId().toString())) {
          log.debug("******************validateOrgnlMsgId_002.999 - Passed.******************");
          mandateSeverity = mandateSeverity + 0;
        } else {
          generateStatusErrorDetailsList("910099", null, hdrErrorType);
          grpHdrSeverity++;
          log.info("******************validateOrgnlMsgId_002.999 - Failed.******************");
        }
      } else {
        generateStatusErrorDetailsList("910099", null, hdrErrorType);
        grpHdrSeverity++;
        log.info("******************validateOrgnlMsgId_002.999 - Failed.******************");
      }

      //____________________________Validate Original Message Type - Rjct
      // 012_____________________________________//
      if (origGrpInfStats.getOrgnlMsgNmId() != null) {
        orgnlMsgNameId = origGrpInfStats.getOrgnlMsgNmId().trim();
        boolean msgNmId = false;
        messageType = null;

        if (orgnlMsgNameId.equalsIgnoreCase("pain.010")) {
          msgNmId = true;
          messageType = "CARIN";
        }

        if (msgNmId == true) {
          grpHdrSeverity = grpHdrSeverity + 0;
          log.debug("******************validateMsgNmId.012 - Passed.******************");
        } else {
          grpHdrSeverity++;
          generateStatusErrorDetailsList("901080", null, hdrErrorType);
          log.info("******************validateMsgNmId.012 - Failed.******************");
        }
      } else {
        grpHdrSeverity++;
        generateStatusErrorDetailsList("901080", null, hdrErrorType);
        log.info("******************validateMsgNmId.012 - Failed.******************");
      }

      //____________________________Original Creation Date and
      // time_999_____________________________________//
      if (origGrpInfStats.getOrgnlCreDtTm() != null) {
        if (validateRule999(origGrpInfStats.getOrgnlCreDtTm().toString())) {
          log.debug("******************validateOrgnlCreDtTm_002.999 - Passed.******************");
          mandateSeverity = mandateSeverity + 0;
        } else {
          generateStatusErrorDetailsList("910099", null, hdrErrorType);
          grpHdrSeverity++;
          log.info("******************validateOrgnlCreDtTm_002.999 - Failed.******************");
        }
      } else {
        generateStatusErrorDetailsList("910099", null, hdrErrorType);
        grpHdrSeverity++;
        log.info("******************validateOrgnlCreDtTm_002.999 - Failed.******************");
      }

      //____________________________Validate Transaction
      // Status_013_____________________________________//
      if (origGrpInfStats.getGrpSts() != null) {
        if (!validatePacs002StatusCode(origGrpInfStats.getGrpSts().toString())) {
          grpHdrSeverity++;
          generateStatusErrorDetailsList("901081", null, hdrErrorType);
          log.info("******************validateGrpStatus_013 - Failed.******************");
        } else {
          grpHdrSeverity = grpHdrSeverity + 0;
          log.debug("******************validateGrpStatus_013 - Passed.******************");
        }
      } else {
        grpHdrSeverity++;
        generateStatusErrorDetailsList("901081", null, hdrErrorType);
        log.info("******************validateGrpStatus_013 - Failed.******************");
      }

      //Check if TxnInfAndSts is populated - else fail file
      if (fiPaymentStatusReportV04.getTxInfAndSts() == null ||
          fiPaymentStatusReportV04.getTxInfAndSts().size() <= 0) {
        grpHdrSeverity++;
        generateStatusErrorDetailsList("902121", null, hdrErrorType);
        log.info("******************TxnInfAndStatusNotPopulated - Failed.******************");
      } else {
        boolean orgnlTxIdIsEmpty = false;
        for (PaymentTransaction33 transaction : fiPaymentStatusReportV04.getTxInfAndSts()) {
          if (transaction.getOrgnlTxId() == null || transaction.getOrgnlTxId().isEmpty() ||
              transaction.getOrgnlTxId().trim() == null) {
            orgnlTxIdIsEmpty = true;
          }
        }

        if (orgnlTxIdIsEmpty) {
          grpHdrSeverity++;
          generateStatusErrorDetailsList("902121", null, hdrErrorType);
          log.info("******************OrgnlTxIdNotPopulated - Failed.******************");
        } else {
          grpHdrSeverity = grpHdrSeverity + 0;
          log.debug("******************OrgnlTxIdNotPopulated - Passed.******************");
        }
      }

      //____________________________Validate Group Status_002
      // .999_____________________________________//
      if (origGrpInfStats.getStsRsnInf() != null && origGrpInfStats.getStsRsnInf().size() > 0) {
        if (origGrpInfStats.getStsRsnInf().get(0) != null &&
            origGrpInfStats.getStsRsnInf().get(0).getRsn() != null &&
            origGrpInfStats.getStsRsnInf().get(0).getRsn().getPrtry() != null) {
          if (validateRule999(origGrpInfStats.getStsRsnInf().get(0).getRsn().getPrtry())) {
            log.debug("******************validateStatusReason_002.999 - Passed.******************");
            mandateSeverity = mandateSeverity + 0;
          } else {
            generateStatusErrorDetailsList("910099", null, hdrErrorType);
            grpHdrSeverity++;
            log.info("******************validateStatusReason_002.999 - Failed.******************");
          }
        } else {
          generateStatusErrorDetailsList("910099", null, hdrErrorType);
          grpHdrSeverity++;
          log.info("******************validateStatusReason_002.999 - Failed.******************");
        }
      } else {
        generateStatusErrorDetailsList("910099", null, hdrErrorType);
        grpHdrSeverity++;
        log.info("******************validateStatusReason_002.999 - Failed.******************");
      }


      if (!validateFileSizeLimit(st201Service, instgAgt, inwardFileSize)) {

        generateStatusErrorDetailsList("902206", null, hdrErrorType);
        grpHdrSeverity++;
        log.info("******************validateFileSizeLimit - Failed.******************");
      } else {
        grpHdrSeverity = grpHdrSeverity + 0;
        log.debug("******************validateFileSizeLimit- Passed.******************");
      }
    }//else MsgId Lenght > 34

    if (grpHdrSeverity == 0) {
      hdrSystemSeqNo =
          generateStatusReportGrpHdr(groupHeader, origGrpInfStats, "ACCP", stsHdrInstgAgt);
    } else {
      log.info(fileName + " failed Group Header Validation.");
      hdrSystemSeqNo =
          generateStatusReportGrpHdr(groupHeader, origGrpInfStats, "RJCT", stsHdrInstgAgt);
      saveStatusErrorDetails();
      opsStatusDetailsList.clear();
    }

    CasOpsFileRegEntity casOpsFileRegEntity =
        (CasOpsFileRegEntity) valBeanRemote.retrieveOpsFileReg(fileName);
    if (casOpsFileRegEntity != null) {
      casOpsFileRegEntity.setGrpHdrMsgId(outMsgId);
      valBeanRemote.updateOpsFileReg(casOpsFileRegEntity);
    }

    return grpHdrSeverity;
  }

  public List<?> findDuplicateTxns(List<PaymentTransaction33> confirmationList) {
    mandateSeverity = 0;
    log.info("XXXX----FINDING DUPLICATES WITHIN FILE -----XXX");
    HashSet<String> uniqueTxnSet = new HashSet<String>();
    List<PaymentTransaction33> duplicateList = new ArrayList<PaymentTransaction33>();
    List<PaymentTransaction33> uniqueList = new ArrayList<PaymentTransaction33>();

    for (PaymentTransaction33 paymentTxn33 : confirmationList) {
      String txnId = null;

      if (paymentTxn33.getOrgnlTxId() != null) {
        txnId = paymentTxn33.getOrgnlTxId();

        if (!uniqueTxnSet.add(txnId)) {
          log.info("DUPL MRTI ==> " + txnId);
          mandateSeverity++;
          duplicateList.add(paymentTxn33);
        } else {
          //						log.info("UNIQ MRTI");
          uniqueList.add(paymentTxn33);
        }
      }
    }

    //Create Duplicate records
    if (duplicateList != null && duplicateList.size() > 0) {
      String mrti = null;
      for (PaymentTransaction33 duplTxn : duplicateList) {

        if (duplTxn.getOrgnlTxId() != null) {
          mrti = duplTxn.getOrgnlTxId();
        }

        //Generate Duplicate Error
        generateStatusErrorDetailsList("902205", mrti, txnErrorType);
      }
      saveStatusErrorDetails();
    }
    return uniqueList;
  }

  public int validateTransaction(PaymentTransaction33 paymentTransaction33) {
    long valStartTime, valEndTime, valDur;
    mandateSeverity = 0;

    orgnlTxnId = paymentTransaction33.getOrgnlTxId();
    log.info("=== <VALIDATING ST201> " + orgnlTxnId + " ===");

    //Populate Debtor Bank
    if (orgnlTxnId != null && !orgnlTxnId.isEmpty()) {
      creditorBank = "21" + orgnlTxnId.substring(0, 4).trim();
    }

    //		valStartTime = System.nanoTime();
    //____________________________Validate Mandate Request Transaction Identifier
    // .015_____________________________________//
    if (orgnlTxnId != null) {
      CasOpsCessionAssignEntity matchedMandate = matchPacs002ToOrigMandate(orgnlTxnId, messageType);
      log.info("matchedMandate: " + matchedMandate);
      if (matchedMandate != null) {
        String processStatus = matchedMandate.getProcessStatus();
        log.info("PROCESS STATUS =====>>>> " + processStatus);

        if (processStatus.equalsIgnoreCase(extractStatus)) {
          mandateSeverity = mandateSeverity + 0;
          log.debug("******************validateMandateReqTrn_015 - Passed.******************");
        } else {
          if (processStatus.equalsIgnoreCase(matchedStatus)) {
            mandateSeverity++;
            generateStatusErrorDetailsList("902028", orgnlTxnId, txnErrorType);
            log.info(
                "******************validateMandateReqTrn_015 -- Mandate Previously Matched - " +
                    "Failed.******************");
          } else {
            if (processStatus.equalsIgnoreCase(rejectedStatus)) {
              mandateSeverity++;
              generateStatusErrorDetailsList("902028", orgnlTxnId, txnErrorType);
              log.info(
                  "******************validateMandateReqTrn_015 -- Mandate Previously Rejected - " +
                      "Failed.******************");
            } else {
              if (processStatus.equalsIgnoreCase(responseRecStatus)) {
                //Duplicate
                mandateSeverity++;
                generateStatusErrorDetailsList("902028", orgnlTxnId, txnErrorType);
                log.info(
                    "******************validateMandateReqTrn_015 -- Duplicate - Mandate Already " +
                        "Responded on- Failed.******************");
              } else {
                mandateSeverity++;
                generateStatusErrorDetailsList("901144", orgnlTxnId, txnErrorType);
                log.info(
                    "******************validateMandateReqTrn_015___None of the statuses Match - " +
                        "Failed.******************");
              }
            }
          }
        }
      } else {
        mandateSeverity++;
        generateStatusErrorDetailsList("901144", orgnlTxnId, txnErrorType);
        log.info(
            "******************validateMandateReqTrn_015_NoMatchFound - Failed.******************");
      }
    } else {
      mandateSeverity++;
      generateStatusErrorDetailsList("901144", orgnlTxnId, txnErrorType);
      log.info(
          "******************validateMandateReqTrn_015_tagIsEmpty - Failed.******************");
    }

    //		valEndTime = System.nanoTime();
    //		valDur = (valEndTime - valStartTime) / 1000000;
    //		log.info("015_Txn Matching "+DurationFormatUtils.formatDuration(valDur, "HH:mm:ss.S")
    //		+" milliseconds. ");
    //		valStartTime = System.nanoTime();
    //____________________________Validate Transaction
    // Status_013_____________________________________//

    if (paymentTransaction33 != null && paymentTransaction33.getTxSts() != null) {
      if (!validateRule999(paymentTransaction33.getTxSts().value())) {
        mandateSeverity++;
        generateStatusErrorDetailsList("910099", orgnlTxnId, txnErrorType);
        log.info("******************validate rjct.999 - Failed.******************");
      } else {
        mandateSeverity = mandateSeverity + 0;
        log.debug("******************validate rjct.999 - Passed.******************");
      }

      //			SALEHAR - 2018-03-19 - As per TRS 17 The rule to be applied is rjct.999.
      //			if(!validatePacs002StatusCode(paymentTransaction33.getTxSts().value()))
      //			{
      //				mandateSeverity++;
      //				generateStatusErrorDetailsList("901081", orgnlTxnId, txnErrorType);
      //				log.info("******************validate - Failed.******************");
      //			}
      //			else
      //			{
      //				mandateSeverity = mandateSeverity+0;
      //				log.debug("******************validateTransactionStatusCode.013 - Passed
      //				.******************");
      //			}
    } else {
      mandateSeverity++;
      generateStatusErrorDetailsList("910099", orgnlTxnId, txnErrorType);
      log.info("******************validate rjct.999 - Failed.******************");
    }
    //		valEndTime = System.nanoTime();
    //		valDur = (valEndTime - valStartTime) / 1000000;
    //		log.info("rjct.999 txn Status "+DurationFormatUtils.formatDuration(valDur, "HH:mm:ss
    //		.S")+" milliseconds. ");
    //		valStartTime = System.nanoTime();


    //			if(paymentTransaction33.getTxSts().toString().equalsIgnoreCase("RJCT"))
    //			{
    if (paymentTransaction33.getStsRsnInf() != null &&
        paymentTransaction33.getStsRsnInf().size() > 0) {
      for (StatusReasonInformation9 statusReasonInformation9 :
          paymentTransaction33.getStsRsnInf()) {
        if (statusReasonInformation9.getRsn() != null &&
            statusReasonInformation9.getRsn().getPrtry() != null) {
          if (!validateErrorCodes(statusReasonInformation9.getRsn().getPrtry().toString())) {
            mandateSeverity++;
            generateStatusErrorDetailsList("901082", orgnlTxnId, txnErrorType);
            log.info("******************validateRejReason_014 - Failed.******************");
          } else {
            mandateSeverity = mandateSeverity + 0;
            log.debug("******************validateRejReason_014 - Passed.******************");
          }
        } else {
          mandateSeverity++;
          generateStatusErrorDetailsList("901082", orgnlTxnId, txnErrorType);
          log.info("******************validateRejReason_014 - Failed.******************");
        }
      }
    } else {
      mandateSeverity++;
      generateStatusErrorDetailsList("902153", orgnlTxnId, txnErrorType);
      log.info("******************validateRejReason_014 - Failed.******************");
    }
    //			}
    //If it ACCP it will NOT have Errors.
    //		valEndTime = System.nanoTime();
    //		valDur = (valEndTime - valStartTime) / 1000000;
    //		log.info("validateRejReason_014 Duration "+DurationFormatUtils.formatDuration(valDur,
    //		"HH:mm:ss.S")+" milliseconds. ");
    //		valStartTime = System.nanoTime();

    if (mandateSeverity == 0) {
      log.debug("Mandate: +" + orgnlTxnId + " has passed validation.......");
    } else {
      log.info("Mandate: +" + orgnlTxnId + " has failed validation.......");
      saveStatusErrorDetails();
    }

    return mandateSeverity;
  }

  public BigDecimal generateStatusReportGrpHdr(GroupHeader53 groupHeader53,
                                               OriginalGroupHeader1 originalGroupHeader1,
                                               String groupStatus, String stsHdrInstgAgt) {
    log.debug("********************generating ops_status_hdrs record**********************");

    opsStatusHdrsEntity = new CasOpsStatusHdrsEntity();
    opsStatusHdrsEntity.setSystemSeqNo(new BigDecimal(999999));
    opsStatusHdrsEntity.setHdrMsgId(outMsgId);
    opsStatusHdrsEntity.setCreateDateTime(getCovertDateTime(groupHeader53.getCreDtTm()));
    opsStatusHdrsEntity.setInstgAgt(stsHdrInstgAgt);
    opsStatusHdrsEntity.setInstdAgt(
        groupHeader53.getInstdAgt().getFinInstnId().getClrSysMmbId().getMmbId());
    opsStatusHdrsEntity.setOrgnlMsgId(groupHeader53.getMsgId());
    opsStatusHdrsEntity.setOrgnlMsgName("pacs.002");
    opsStatusHdrsEntity.setOrgnlCreateDateTime(getCovertDateTime(groupHeader53.getCreDtTm()));
    if (groupStatus.equalsIgnoreCase("ACCP")) {
      opsStatusHdrsEntity.setProcessStatus("1");
    } else {
      opsStatusHdrsEntity.setProcessStatus("6");
    }
    opsStatusHdrsEntity.setGroupStatus(groupStatus);
    opsStatusHdrsEntity.setService("ST202");
    opsStatusHdrsEntity.setOrgnlFileName(fileName);

    log.debug("opsStatusHdrsEntity in pacs 002 validation =====>>>>: " + opsStatusHdrsEntity);
    hdrSystemSeqNo = valBeanRemote.saveOpsStatusHdrs(opsStatusHdrsEntity);
    log.debug("hdrSystemSeqNo in pacs 002 validation =====>>>>: " + hdrSystemSeqNo);
    return hdrSystemSeqNo;
  }

  public void generateStatusErrorDetailsList(String errorCode, String txnId, String errorType) {
    log.debug("************* Generating Status Details Entry *****");

    opsStatusDetailsEntity = new CasOpsStatusDetailsEntity();

    opsStatusDetailsEntity.setSystemSeqNo(new BigDecimal(123));
    opsStatusDetailsEntity.setErrorCode(errorCode);
    opsStatusDetailsEntity.setTxnId(txnId);
    opsStatusDetailsEntity.setTxnStatus("RJCT");
    opsStatusDetailsEntity.setErrorType(errorType);

    opsStatusDetailsList.add(opsStatusDetailsEntity);
    log.debug("opsStatusDetailsList size---------->:" + opsStatusDetailsList.size());
  }

  public boolean saveStatusErrorDetails() {
    log.debug("*********In the saveStatusErrorDetails*******");
    boolean generated = false;
    log.debug("casSysctrlSysParamEntity:" + casSysctrlSysParamEntity);

    if (opsStatusDetailsList.size() > 0) {
      log.debug("Status Error List Size --> " + opsStatusDetailsList.size());
      for (CasOpsStatusDetailsEntity localEntity : opsStatusDetailsList) {
        localEntity.setStatusHdrSeqNo(hdrSystemSeqNo);
      }
      log.debug("Status Error details: " + opsStatusDetailsList.toString());
      generated = valBeanRemote.saveOpsStatusDetails(opsStatusDetailsList);
    }

    if (generated) {
      opsStatusDetailsList.clear();
    }
    return generated;
  }

  public String generateMsgId(String instId) {
    int lastSeqNoUsed;

    log.debug("In the generateMsgId()");
    SimpleDateFormat sdfFileDate = new SimpleDateFormat("yyyyMMdd");
    String achId, creationDate, fileSeqNo, msgId = null;
    String outgoingService = "ST202";

    CasOpsCustParamEntity casOpsCustParamEntity =
        (CasOpsCustParamEntity) valBeanRemote.retrieveOpsCustomerParameters(instId, backEndProcess);

    try {
      if (casSysctrlCompParamEntity != null) {
        achId = casSysctrlCompParamEntity.getAchId();
      } else {
        achId = "021";
      }

      CasOpsRefSeqNrEntity casOpsRefSeqNrEntity = new CasOpsRefSeqNrEntity();
      casOpsRefSeqNrEntity =
          (CasOpsRefSeqNrEntity) valBeanRemote.retrieveRefSeqNr(outgoingService, instId);

      if (casOpsRefSeqNrEntity != null) {
        lastSeqNoUsed = Integer.valueOf(casOpsRefSeqNrEntity.getLastSeqNr());
        lastSeqNoUsed++;
      } else {
        lastSeqNoUsed = 1;
      }

      log.debug("lastSeqNoUsed---->: " + lastSeqNoUsed);
      fileSeqNo = String.format("%06d", lastSeqNoUsed);
      log.debug("fileSeqNo---->: " + fileSeqNo);
      casOpsRefSeqNrEntity.setLastSeqNr(fileSeqNo);
      valBeanRemote.updateOpsRefSeqNr(casOpsRefSeqNrEntity);

      //TRS16 Processing Rules
      if (casSysctrlSysParamEntity != null && casSysctrlSysParamEntity.getProcessDate() != null) {
        creationDate = sdfFileDate.format(casSysctrlSysParamEntity.getProcessDate());
      } else {
        creationDate = sdfFileDate.format(new Date());
      }

      msgId = achId + "/" + outgoingService + "/" + "00" + instId + "/" + creationDate + "/" +
          fileSeqNo;
    } catch (Exception e) {
      log.error("**** Exception generating MsgId **** : " + e);
      e.printStackTrace();
      e.getCause();
    }

    return msgId;
  }

  public CasCnfgErrorCodesEntity retrieveErrorCode(String errCode) {
    CasCnfgErrorCodesEntity casCnfgErrorCodesEntity =
        (CasCnfgErrorCodesEntity) valBeanRemote.retrieveErrorCode(errCode);
    log.debug("casCnfgErrorCodesEntity: " + casCnfgErrorCodesEntity);
    return casCnfgErrorCodesEntity;
  }

  public Date getCovertDateTime(XMLGregorianCalendar xmlGregorianCalendar) {
    Calendar cl = xmlGregorianCalendar.toGregorianCalendar();
    return cl.getTime();
  }
}
