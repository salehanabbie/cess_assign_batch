package com.bsva.authcoll.singletable.validation;

import com.bsva.PropertyUtil;
import com.bsva.entities.CasSysctrlCompParamEntity;
import com.bsva.entities.CasOpsStatusDetailsEntity;
import com.bsva.entities.CasOpsStatusHdrsEntity;
import com.bsva.entities.CasOpsTxnsBillReportEntity;
import com.bsva.entities.CasCnfgErrorCodesEntity;
import com.bsva.entities.CasOpsCustParamEntity;
import com.bsva.entities.CasOpsFileRegEntity;
import com.bsva.entities.CasOpsRefSeqNrEntity;
import com.bsva.entities.SysCisBranchEntity;
import iso.std.iso._20022.tech.xsd.pain_010_001.GroupHeader47;
import iso.std.iso._20022.tech.xsd.pain_010_001.Mandate1;
import iso.std.iso._20022.tech.xsd.pain_010_001.Mandate3;
import iso.std.iso._20022.tech.xsd.pain_010_001.MandateAmendment3;
import iso.std.iso._20022.tech.xsd.pain_010_001.MandateAmendmentReason1;
import iso.std.iso._20022.tech.xsd.pain_010_001.SupplementaryData1;
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
 * Modified by SalehaR - 2015/12/10 - Alignment to V2.0 of Interface Specification
 * Modified by SalehaR - 06/03/2016 - Alignment to Int Spec v3.1
 * Modified by SalehaR - 2016/09/14 - Alignment to TRS 15
 * Modified by SalehaR - 2018/03/09 - Aligned to TRS 17
 * Modified by SalehaR - 2019/01/02 - CHG-153240-Validate Creation Date in Msg Id
 * @author SalehaR - 2019/09/21 Align to Single Table (CAS_OPS_CESS_ASSIGN_TXNS)
 * @author SalehaR-2019/10/17 File Size Limit Validation
 * @author SalehaR-2020/07/23-Remove CDV validation as per TDA
 * @author SalehaR-2020/08/07-Optimise Validation and remove SADC validation
 */
public class AC_Pain010_Validation_ST extends Validation_ST {
  public static Logger log = Logger.getLogger("PAIN_010_VALIDATION_ST");

  @EJB
  PropertyUtil propertyUtil;

  public String branchmemberIdDebtor, branchmemberIdCreditor;
  SimpleDateFormat sdfDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
  SimpleDateFormat sdfFileDate = new SimpleDateFormat("yyyyMMdd");
  String branchmemberIdDebtorVal, branchmemberIdCreditorVal;
  int grpHdrSeverity = 0;
  int mandateSeverity = 0;

  List<CasOpsStatusHdrsEntity> opsStatusHdrsList = null;
  List<CasOpsStatusDetailsEntity> opsStatusDetailsList = null;

  //Ac ops Txn Report List
  List<CasOpsTxnsBillReportEntity> opsTxnsBillReportList = null;

  //Ac Entities declaration
  CasOpsStatusHdrsEntity opsStatusHdrsEntity = null;
  CasOpsStatusDetailsEntity opsStatusDetailsEntity = null;
  public BigDecimal hdrSystemSeqNo = BigDecimal.ZERO;
  CasSysctrlCompParamEntity casSysctrlCompParamEntity;

  boolean bicCodeValid = false;
  String msgId, msgCreateDate, incomingBicCode, mandateReqId, fileName, bicfi, orgnlMsgId,
      orgnlMsgNameId, mndReqTrnId;
  String outMsgId, incInstid;
  String statusReasonInfo, txnStatus, orgnlTxnId, reason, additonalInfo;
  Date convCreationDateTime;
  int fileNum = 0;
  public static String systemName = "CAMOWNER";
  String backEndProcess = "BACKEND";
  String hdrErrorType = "HDR";
  String txnErrorType = "TXN";

  GroupHeader47 groupHdr;
  private String systemType;
  String mandateRefNo = null;
  String debtorBranchNo = null;
  String crAbbShortName = null;
  String fileSizeLimitStr = null;
  Integer fileSizeLimit;

  String achId = null;

  String stsHdrInstgAgt = null;
  //Populate Error Codes Report Data
  public String debtorBank = null;
  String creditorBank = null, ultCreditor = null, abbShortName = null, carinService = null;

  public AC_Pain010_Validation_ST(String fileName) {
    this.fileName = fileName;
    try {
      propertyUtil = new PropertyUtil();
      this.carinService = propertyUtil.getPropValue("Input.Pain010");
      fileSizeLimitStr = propertyUtil.getPropValue("AC.FILE.TRANSACTION.LIMIT");
      //			log.info("fileSizeLimit ==> "+fileSizeLimitStr);
      fileSizeLimit = Integer.valueOf(fileSizeLimitStr);
    } catch (Exception e) {
      log.error(
          "AC_Pain010_Validation_ST - Could not find CessionAssignment.properties in " +
				  "classpath");
      carinService = "CARIN";
      fileSizeLimit = 50000;
    }

    opsStatusHdrsList = new ArrayList<CasOpsStatusHdrsEntity>();
    opsStatusDetailsList = new ArrayList<CasOpsStatusDetailsEntity>();
    opsTxnsBillReportList = new ArrayList<CasOpsTxnsBillReportEntity>();

    hdrSystemSeqNo = BigDecimal.ZERO;
    bicCodeValid = false;
    contextValidationBeanCheck();
    contextAdminBeanCheck();
    //		log.debug("casSysctrlSysParamEntity: "+ casSysctrlSysParamEntity);
    systemType = casSysctrlSysParamEntity.getSysType();
    casSysctrlCompParamEntity =
        (CasSysctrlCompParamEntity) valBeanRemote.retrieveCompanyParameters(backEndProcess);
  }

  public int validateGroupHeader(iso.std.iso._20022.tech.xsd.pain_010_001.GroupHeader47 groupHeader,
                                 Integer inwardFileSize) {
    groupHdr = new GroupHeader47();
    groupHdr = groupHeader;

    debtorBank = null;
    creditorBank = null;
    debtorBank = casSysctrlCompParamEntity.getAchInstId();

    msgId = groupHeader.getMsgId();
    log.debug("grpHdr: " + groupHdr.getMsgId());

    stsHdrInstgAgt = null;

    if (!validateMsgIdStructure(msgId)) {
      log.debug("Write to GrpHdr ..MsgId fails....");
      incInstid = fileName.substring(8, 16);
      stsHdrInstgAgt = fileName.substring(10, 16);
      incInstid = StringUtils.stripStart(incInstid, "0");
      outMsgId = generateStatusMsgId(incInstid);
      creditorBank = incInstid;

      grpHdrSeverity++;
      generateStatusErrorDetailsList("902134", null, hdrErrorType);
    } else {
      /*validate msgIdFormat*/
      String[] splitMsgId = splitMsgId(msgId);

      achId =
          splitMsgId[0];                                                                                                                                /* msgId.substring(0, 3);*/
      String serviceId =
          splitMsgId[1];                                                                                                                        /*msgId.substring(4,9);*/
      incInstid =
          splitMsgId[2];                                                                                                                                        /*msgId.substring(10,18);*/
      incInstid = StringUtils.stripStart(incInstid, "0");
      creditorBank = incInstid;

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

      if (!validateMemberNo(incInstid)) {
        incInstid = fileName.substring(8, 16);
        incInstid = StringUtils.stripStart(incInstid, "0");

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
        outMsgId = generateStatusMsgId(incInstid);
      } else {
        stsHdrInstgAgt = fileMmId;
        outMsgId = generateStatusMsgId(fileMmId);
      }

      //____________________________Validate Service Id_002_____________________________________//
      if (!validateServiceId(serviceId, "CARIN")) {
        generateStatusErrorDetailsList("901045", null, hdrErrorType);
        grpHdrSeverity++;
        log.info("******************validateServiceId_002 - FAILED.******************");
      } else {
        log.debug("******************validateServiceId_002 - PASSED.******************");
        grpHdrSeverity = grpHdrSeverity + 0;
      }

      //____________________________Validate MsgId Creation Date
		// Time_007_____________________________________//
//			if(!isDateValid(msgCreationDate, "yyyyMMdd"))
//			{
//				generateStatusErrorDetailsList("901006", null, hdrErrorType);
//				grpHdrSeverity++;
//				log.info("******************validateDate_007 - FAILED.******************");
//			}
//			else
//			{
//				msgCreateDate = msgCreationDate;
//				grpHdrSeverity = grpHdrSeverity+0;
//				log.debug("******************validateDate_007 - PASSED.******************");
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

      //____________________________Validate Message Id Uniqueness 010_006
		// _____________________________________//
      if (!validateMsgId(msgId)) {
        generateStatusErrorDetailsList("901005", null, hdrErrorType);
        grpHdrSeverity++;
        log.info("******************validateMsgId 010_006 - FAILED.******************");
      } else {
        grpHdrSeverity = grpHdrSeverity + 0;
        log.debug("******************validateMsgId 010_006 - PASSED.******************");
      }

      //____________________________Validate GrpHdr Creation Date Time 010_008
      // _____________________________________//
//			if(groupHeader.getCreDtTm() != null)
//			{
//				convCreationDateTime = getCovertDateTime(groupHeader.getCreDtTm());
//
//				if(convCreationDateTime != null)
//				{
//					String strCreationDate = sdfDateTime.format(convCreationDateTime);
//					if(!validateDate(strCreationDate, "yyyy-MM-dd HH:mm:ss"))
//					{
//						generateStatusErrorDetailsList("901007", null, hdrErrorType);
//						grpHdrSeverity++;
//						log.info("******************validateDate_008 - Failed.******************");
//					}
//					else
//					{
//						grpHdrSeverity = grpHdrSeverity+0;
//						log.debug("******************validateDate_008 - Passed
//						.******************");
//					}
//				}
//				else
//				{
//					generateStatusErrorDetailsList("901007", null, hdrErrorType);
//					grpHdrSeverity++;
//					log.info("******************validateDate_008 - Failed.******************");
//				}
//			}
//			else
//			{
//				generateStatusErrorDetailsList("901007", null, hdrErrorType);
//				grpHdrSeverity++;
//				log.info("******************validateDate_008 - Failed.******************");
//			}

      //____________________________Validate Instructing Agent 010_010
		// _____________________________________//
      if (!validateMemberNo(
          groupHeader.getInstgAgt().getFinInstnId().getClrSysMmbId().getMmbId())) {
        generateStatusErrorDetailsList("901017", null, hdrErrorType);
        grpHdrSeverity++;
        log.info("******************validateInstructingAgent_010 - Failed.******************");
      } else {
        grpHdrSeverity = grpHdrSeverity + 0;
        log.debug("******************validateInstructingAgent_010 - Passed.******************");
      }

      //____________________________Validate Instructed Agent_Rule 010_013
      // _____________________________________//
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
          log.info("******************validateInstructed Agent_013 - Failed.******************");
        } else {
          grpHdrSeverity = grpHdrSeverity + 0;
          log.debug("******************validateInstructed Agent_013- Passed.******************");
        }
      } else {
        generateStatusErrorDetailsList("901079", null, hdrErrorType);
        grpHdrSeverity++;
        log.info("******************validateInstructingAgent_013 - Failed.******************");
      }

      //____________________________Validate Initiating Party_Rule 010_062
      // _____________________________________//
      if (groupHeader.getInitgPty() != null && groupHeader.getInitgPty().getNm() != null) {
        String trimInitPrty = groupHeader.getInitgPty().getNm().trim();

        if (trimInitPrty.isEmpty() || trimInitPrty == null || trimInitPrty.length() == 0) {
          generateStatusErrorDetailsList("901085", null, hdrErrorType);
          grpHdrSeverity++;
          log.info("******************validateInitiatingParty_062 - Failed.******************");
        }
      }
      //This is an optional field

      if (!validateFileSizeLimit(carinService, instgAgt, inwardFileSize)) {
        generateStatusErrorDetailsList("902206", null, hdrErrorType);
        grpHdrSeverity++;
        log.info("******************validateFileSizeLimit - Failed.******************");
      } else {
        grpHdrSeverity = grpHdrSeverity + 0;
        log.debug("******************validateFileSizeLimit- Passed.******************");
      }

    }//else MsgId Lenght > 34

    if (grpHdrSeverity == 0) {
      log.debug(
          "~~~~~~~~~~~The file has passed @ Group Header Level...Continue to Transaction " +
           "Validation~~~~~~~~~~~~");
      hdrSystemSeqNo = generateStatusReportGrpHdr(groupHeader, "ACCP", stsHdrInstgAgt);
    } else {
      hdrSystemSeqNo = generateStatusReportGrpHdr(groupHeader, "RJCT", stsHdrInstgAgt);
      saveStatusErrorDetails();
      opsStatusDetailsList.clear();
    }

    CasOpsFileRegEntity casOpsFileRegEntity =
        (CasOpsFileRegEntity) valBeanRemote.retrieveOpsFileReg(fileName);
    if (casOpsFileRegEntity != null) {
      casOpsFileRegEntity.setGrpHdrMsgId(msgId);
      casOpsFileRegEntity.setExtractMsgId(outMsgId);
      valBeanRemote.updateOpsFileReg(casOpsFileRegEntity);
    }

    return grpHdrSeverity;
  }

  public List<?> findDuplicateTxns(List<MandateAmendment3> mandatesList) {
    mandateSeverity = 0;
    log.info("XXXX----FINDING DUPLICATES WITHIN FILE -----XXX");
    HashSet<String> uniqueTxnSet = new HashSet<String>();
    List<MandateAmendment3> duplicateList = new ArrayList<MandateAmendment3>();
    List<MandateAmendment3> uniqueList = new ArrayList<MandateAmendment3>();

    for (MandateAmendment3 mandAmend3 : mandatesList) {
      Mandate3 mandate = mandAmend3.getMndt();

      if (mandate.getCdtr() != null && mandate.getCdtr().getId() != null &&
          mandate.getCdtr().getId().getOrgId() != null &&
          mandate.getCdtr().getId().getOrgId().getOthr() != null) {
        if (mandate.getCdtr().getId().getOrgId().getOthr().get(0) != null &&
            mandate.getCdtr().getId().getOrgId().getOthr().get(0).getId() != null) {
          if (!uniqueTxnSet.add(mandate.getCdtr().getId().getOrgId().getOthr().get(0).getId())) {
            log.info(
                "DUPL MRTI ==> " + mandate.getCdtr().getId().getOrgId().getOthr().get(0).getId());
            mandateSeverity++;
            duplicateList.add(mandAmend3);
          } else {
            //						log.info("UNIQ MRTI");
            uniqueList.add(mandAmend3);
          }
        }
      }
    }

    //Create Duplicate records
    if (duplicateList != null && duplicateList.size() > 0) {
      String mrti = null;
      for (MandateAmendment3 mandAmend3 : duplicateList) {
        Mandate3 mandate = mandAmend3.getMndt();

        if (mandate.getCdtr() != null && mandate.getCdtr().getId() != null &&
            mandate.getCdtr().getId().getOrgId() != null &&
            mandate.getCdtr().getId().getOrgId().getOthr() != null) {
          if (mandate.getCdtr().getId().getOrgId().getOthr().get(0) != null &&
              mandate.getCdtr().getId().getOrgId().getOthr().get(0).getId() != null) {
            mrti = mandate.getCdtr().getId().getOrgId().getOthr().get(0).getId();
          }
        }

        if (mandate.getUltmtCdtr() != null) {
          // Ultimate Creditor
          if (mandate.getUltmtCdtr().getNm() != null) {
            ultCreditor = mandate.getUltmtCdtr().getNm().toString();
          }

          if (mandate.getUltmtCdtr().getId() != null &&
              mandate.getUltmtCdtr().getId().getOrgId() != null &&
              mandate.getUltmtCdtr().getId().getOrgId().getOthr() != null &&
              mandate.getUltmtCdtr().getId().getOrgId().getOthr().size() > 0) {
            if (mandate.getUltmtCdtr().getId().getOrgId().getOthr().get(0) != null &&
                mandate.getUltmtCdtr().getId().getOrgId().getOthr().get(0).getId() != null) {
              abbShortName =
                  mandate.getUltmtCdtr().getId().getOrgId().getOthr().get(0).getId().toString();
            }
          }
        }

        // Populate Debtor Bank
        if (mandate.getDbtrAgt() != null && mandate.getDbtrAgt().getFinInstnId() != null &&
            mandate.getDbtrAgt().getFinInstnId().getClrSysMmbId() != null &&
            mandate.getDbtrAgt().getFinInstnId().getClrSysMmbId().getMmbId() != null) {
          String debtorBrnch = mandate.getDbtrAgt().getFinInstnId().getClrSysMmbId().getMmbId();

          SysCisBranchEntity sysCisBranchEntity =
              (SysCisBranchEntity) valBeanRemote.validateDebtorBranchNo(debtorBrnch, "Y");

          if (sysCisBranchEntity != null) {
            debtorBank = sysCisBranchEntity.getMemberNo();
            debtorBranchNo = mandate.getDbtrAgt().getFinInstnId().getClrSysMmbId().getMmbId();
          } else {
            debtorBranchNo = debtorBrnch;
          }
        }

        //Generate Duplicate Error
        generateStatusErrorDetailsList("902205", mrti, txnErrorType);
      }
      saveStatusErrorDetails();
      saveTxnBillReportData();
    }
    return uniqueList;
  }

  public int validateMandate(Mandate3 mandate, MandateAmendmentReason1 amdmntRsn,
                             Mandate1 origMandate, SupplementaryData1 supplementaryData1) {
//		long valStartTime,valEndTime, valDur;
//		valStartTime = System.nanoTime();
    ultCreditor = null;
    abbShortName = null;
    mandateSeverity = 0;
    //		log.debug("%%%%%%%%%%%%%%%PAIN 010 VALIDATING MANDATE%%%%%%%%%%%%%%%%%%");

    if (mandate.getCdtr() != null && mandate.getCdtr().getId() != null &&
        mandate.getCdtr().getId().getOrgId() != null &&
        mandate.getCdtr().getId().getOrgId().getOthr() != null) {
      if (mandate.getCdtr().getId().getOrgId().getOthr().get(0) != null &&
          mandate.getCdtr().getId().getOrgId().getOthr().get(0).getId() != null) {
        mndReqTrnId = mandate.getCdtr().getId().getOrgId().getOthr().get(0).getId();
        log.info("=== <VALIDATING PAIN.010> " + mndReqTrnId + " ===");
      }
    }

    if (mandate.getUltmtCdtr() != null) {
      //Ultimate Creditor
      if (mandate.getUltmtCdtr().getNm() != null) {
        ultCreditor = mandate.getUltmtCdtr().getNm().toString();
      }

      if (mandate.getUltmtCdtr().getId() != null &&
          mandate.getUltmtCdtr().getId().getOrgId() != null &&
          mandate.getUltmtCdtr().getId().getOrgId().getOthr() != null &&
          mandate.getUltmtCdtr().getId().getOrgId().getOthr().size() > 0) {
        if (mandate.getUltmtCdtr().getId().getOrgId().getOthr().get(0) != null &&
            mandate.getUltmtCdtr().getId().getOrgId().getOthr().get(0).getId() != null) {
          abbShortName =
              mandate.getUltmtCdtr().getId().getOrgId().getOthr().get(0).getId().toString();
        }
      }
    }

//		valEndTime = System.nanoTime();
//		valDur = (valEndTime - valStartTime) / 1000000;
//		if(valDur > 0)
//			log.info("01 Duration "+DurationFormatUtils.formatDuration(valDur, "HH:mm:ss.S")+"
//			milliseconds. ");
//		valStartTime = System.nanoTime();

    //Populate Debtor Bank
    if (mandate.getDbtrAgt() != null && mandate.getDbtrAgt().getFinInstnId() != null &&
        mandate.getDbtrAgt().getFinInstnId().getClrSysMmbId() != null &&
        mandate.getDbtrAgt().getFinInstnId().getClrSysMmbId().getMmbId() != null) {

      String debtorBrnch = mandate.getDbtrAgt().getFinInstnId().getClrSysMmbId().getMmbId();

      //			SysCisBranchEntity sysCisBranchEntity = (SysCisBranchEntity) valBeanRemote
      //			.validateDebtorBranchNo(debtorBrnch,"Y");
      SysCisBranchEntity sysCisBranchEntity =
          (SysCisBranchEntity) findDebtorCISBranches(debtorBrnch);

      if (sysCisBranchEntity != null) {
        debtorBank = sysCisBranchEntity.getMemberNo();
        debtorBranchNo = debtorBrnch;
      } else {
        debtorBranchNo = debtorBrnch;
      }
    }

//		valEndTime = System.nanoTime();
//		valDur = (valEndTime - valStartTime) / 1000000;
//		if(valDur > 0)
//			log.info("02 Duration "+DurationFormatUtils.formatDuration(valDur, "HH:mm:ss.S")+"
//			milliseconds. ");

    //		mandateReqId = mandate.getMndtReqId();

//		valStartTime = System.nanoTime();
    //____________________________Validate RejectReason Code _rule
    // 010_052_____________________________________//
    if (amdmntRsn.getRsn() != null && amdmntRsn.getRsn().getPrtry() != null &&
        amdmntRsn.getRsn().getPrtry().toString() != null) {
      if (!validateAmendReasonCode(amdmntRsn.getRsn().getPrtry().toString())) {
        generateStatusErrorDetailsList("901159", mndReqTrnId, txnErrorType);
        mandateSeverity++;
        log.info("******************validateAmendReasonCode_010_052 - Failed.******************");
      } else {
        mandateSeverity = mandateSeverity + 0;
        log.debug("******************validateAmendReasonCode_010_052 - Passed.******************");
      }
    } else {
      generateStatusErrorDetailsList("901159", mndReqTrnId, txnErrorType);
      mandateSeverity++;
      log.info("******************validateAmendReasonCode_010_052 - Failed.******************");
    }
//		valEndTime = System.nanoTime();
//		valDur = (valEndTime - valStartTime) / 1000000;
//		if(valDur > 0)
//			log.info("validateAmendReasonCode_010_052 Duration "+DurationFormatUtils
//			.formatDuration(valDur, "HH:mm:ss.S")+" milliseconds. ");
//		valStartTime = System.nanoTime();

    //2018-04-03 - SalehaR - Rule removed from TRS 17 as XSD makes field mandatory
    //		//____________________________Validate MandateId_rule 010
    //		.999_____________________________________//
    //		if(mandate.getMndtId() != null)
    //		{
    //			if(validateRule999(mandate.getMndtId()))
    //			{
    //				log.debug("******************validateMndtId_010.999 - Passed
    //				.******************");
    //				mandateSeverity = mandateSeverity+0;
    //			}
    //			else
    //			{
    //				generateStatusErrorDetailsList("910099", mndReqTrnId, txnErrorType);
    //				mandateSeverity++;
    //				log.info("******************validateMndtId_010.999 - Failed
    //				.******************");
    //			}
    //		}
    //		else
    //		{
    //			generateStatusErrorDetailsList("910099", mndReqTrnId, txnErrorType);
    //			mandateSeverity++;
    //			log.info("******************validateMndtId_010.999 - Failed.******************");
    //		}

    //____________________________Validate Tracking Ind _rule
    // 010_051_____________________________________//
    if (mandate.getTp() != null && mandate.getTp().getSvcLvl() != null &&
        mandate.getTp().getSvcLvl().getPrtry() != null) {
      if (!validateTrackingIndicator(mandate.getTp().getSvcLvl().getPrtry())) {
        generateStatusErrorDetailsList("901100", mndReqTrnId, txnErrorType);
        mandateSeverity++;
        log.info("******************validateTrackingInd_010_051 - Failed.******************");
      } else {
        mandateSeverity = mandateSeverity + 0;
        log.debug("******************validateTrackingInd_010_051 - Passed.******************");
      }
    }
    //This is optional in TRS 15

//		valEndTime = System.nanoTime();
//		valDur = (valEndTime - valStartTime) / 1000000;
//		if(valDur > 0)
//			log.info("validateTrackingInd_010_051 Duration "+DurationFormatUtils.formatDuration
//			(valDur, "HH:mm:ss.S")+" milliseconds. ");
//		valStartTime = System.nanoTime();
    //____________________________Validate Debtor
    // Authentication_012_____________________________________//
    if (mandate.getTp() != null && mandate.getTp().getLclInstrm() != null &&
        mandate.getTp().getLclInstrm().getPrtry() != null) {
      if (!validateDebtorAuthCode(mandate.getTp().getLclInstrm().getPrtry(), "CARIN")) {
        generateStatusErrorDetailsList("901101", mndReqTrnId, txnErrorType);
        mandateSeverity++;
        log.info("******************validateDebtorAuthCode 010_012 - FAILED.******************");
      } else {
        mandateSeverity = mandateSeverity + 0;
        log.debug("******************validateDebtorAuthCode 010_012 - PASSED.******************");
      }
    } else {
      generateStatusErrorDetailsList("901101", mndReqTrnId, txnErrorType);
      mandateSeverity++;
      log.info("******************validateDebtorAuthCode 010_012 - FAILED.******************");
    }
//		valEndTime = System.nanoTime();
//		valDur = (valEndTime - valStartTime) / 1000000;
//		if(valDur > 0)
//			log.info("validateDebtorAuthCode_010.012 Duration "+DurationFormatUtils.formatDuration
//			(valDur, "HH:mm:ss.S")+" milliseconds. ");
//		valStartTime = System.nanoTime();

    //____________________________Validate Sequence Type _010
    // .067_____________________________________//

    if (mandate.getOcrncs() != null && mandate.getOcrncs().getSeqTp() != null) {
      if (!validateInstallmentOccurrence(mandate.getOcrncs().getSeqTp().toString())) {
        generateStatusErrorDetailsList("901102", mndReqTrnId, txnErrorType);
        mandateSeverity++;
        log.info("******************validateSeqtype_010.067 - Failed.******************");
      } else {
        mandateSeverity = mandateSeverity + 0;
        log.debug("******************validateSeqtype_010.067 - Passed.******************");
      }
    } else {
      generateStatusErrorDetailsList("901102", mndReqTrnId, txnErrorType);
      mandateSeverity++;
      log.info("******************validateSeqtype_010.067 - Failed.******************");
    }
//		valEndTime = System.nanoTime();
//		valDur = (valEndTime - valStartTime) / 1000000;
//		if(valDur > 0)
//			log.info("validateSeqtype_010.067 Duration "+DurationFormatUtils.formatDuration
//			(valDur, "HH:mm:ss.S")+" milliseconds. ");
//		valStartTime = System.nanoTime();


    //____________________________Validate Frequency Code_014_____________________________________//
    if (mandate.getOcrncs() != null && mandate.getOcrncs().getFrqcy() != null) {
      if (!validateInstallmentFrequency(mandate.getOcrncs().getFrqcy().toString())) {
        generateStatusErrorDetailsList("901103", mndReqTrnId, txnErrorType);
        mandateSeverity++;
        log.info("******************validateInstallmentFrequency_014 - FAILED.******************");
      } else {
        mandateSeverity = mandateSeverity + 0;
        log.debug("******************validateInstallmentFrequency_014 - PASSED.******************");
      }
    }
    //This is optional in TRS 15
//		valEndTime = System.nanoTime();
//		valDur = (valEndTime - valStartTime) / 1000000;
//		if(valDur > 0)
//			log.info("validateInstallmentFrequency_014 Duration "+DurationFormatUtils
//			.formatDuration(valDur, "HH:mm:ss.S")+" milliseconds. ");
//		valStartTime = System.nanoTime();

    Date firstDate = null;
    //____________________________Validate First Collection Date
    // _rule010_017_____________________________________//
    if (mandate.getOcrncs() != null && mandate.getOcrncs().getFrstColltnDt() != null) {
      firstDate = getCovertDateTime(mandate.getOcrncs().getFrstColltnDt());
      String strFirstDate = sdf.format(firstDate);
      if (!isDateValid(strFirstDate, "yyyy-MM-dd")) {
        generateStatusErrorDetailsList("901106", mndReqTrnId, txnErrorType);
        mandateSeverity++;
        log.info("******************validateFrstCollDt_010.017 - Failed.******************");
      } else {
        mandateSeverity = mandateSeverity + 0;
        log.debug("******************validateFrstCollDt_010.017 - Passed.******************");
      }
    }
    //This is optional in TRS 15
//		valEndTime = System.nanoTime();
//		valDur = (valEndTime - valStartTime) / 1000000;
//		if(valDur > 0)
//			log.info("validateInstallmentFrequency_014 Duration "+DurationFormatUtils
//			.formatDuration(valDur, "HH:mm:ss.S")+" milliseconds. ");
//		valStartTime = System.nanoTime();

    //____________________________Validate First Collection Date _rule
    // 010_018______________________________________//

    if (firstDate != null) {
      if (!validateIfISFutureDate(firstDate)) {
        generateStatusErrorDetailsList("901072", mndReqTrnId, txnErrorType);
        mandateSeverity++;
        log.info(
            "******************validateFirstCollDateIsInFuture_010.018 - Failed" +
             ".******************");
      } else {
        mandateSeverity = mandateSeverity + 0;
        log.debug(
            "******************validateFirstCollDateIsInFuture_010.018 - Passed" +
             ".******************");
      }
    }
    //This is optional in TRS 15
//		valEndTime = System.nanoTime();
//		valDur = (valEndTime - valStartTime) / 1000000;
//		if(valDur > 0)
//			log.info("validateFirstCollDateIsInFuture_010.018 Duration "+DurationFormatUtils
//			.formatDuration(valDur, "HH:mm:ss.S")+" milliseconds. ");
//		valStartTime = System.nanoTime();

    //____________________________Validate First Collection Date _rule
    // 010_048______________________________________//
    if (firstDate != null) {
      if (!validateIfNotPastDate(firstDate, casSysctrlSysParamEntity.getProcessDate())) {
        generateStatusErrorDetailsList("901141", mndReqTrnId, txnErrorType);
        mandateSeverity++;
        log.info("******************validateIfNotPastDate_010.048 - FAILED.******************");
      } else {
        mandateSeverity = mandateSeverity + 0;
        log.debug("******************validateIfNotPastDate_010.048 - PASSED.******************");
      }
    }
    //This field is optional in TRS 15
//		valEndTime = System.nanoTime();
//		valDur = (valEndTime - valStartTime) / 1000000;
//		if(valDur > 0)
//			log.info("validateIfNotPastDate_010.048 Duration "+DurationFormatUtils.formatDuration
//			(valDur, "HH:mm:ss.S")+" milliseconds. ");
//		valStartTime = System.nanoTime();

    //____________________________Validate Installment Amount_rule
    // 010_020______________________________________//
    if (supplementaryData1 != null) {
      if (supplementaryData1.getEnvlp() != null &&
          supplementaryData1.getEnvlp().getCnts() != null &&
          supplementaryData1.getEnvlp().getCnts().getDbVlTp() != null) {
        String debitValueType = supplementaryData1.getEnvlp().getCnts().getDbVlTp().toString();

        if (debitValueType.equalsIgnoreCase("FIXED") ||
            debitValueType.equalsIgnoreCase("VARIABLE")) {
          if (mandate.getColltnAmt() != null && mandate.getColltnAmt().getValue() != null) {
            Double collAmt = Double.valueOf(mandate.getColltnAmt().getValue().toString());
            if (!validateInstallmentAmount(collAmt)) {
              generateStatusErrorDetailsList("901108", mndReqTrnId, txnErrorType);
              mandateSeverity++;
              log.info("******************validateInstAmt_020 - Failed.******************");
            } else {
              mandateSeverity = mandateSeverity + 0;
              log.debug("******************validateInstAmt_020 - Passed.******************");
            }

          }//Coll amt is empty
          else {
            generateStatusErrorDetailsList("901108", mndReqTrnId, txnErrorType);
            mandateSeverity++;
            log.info("******************validateInstAmt_020 - Failed.******************");
          }

//					valEndTime = System.nanoTime();
//					valDur = (valEndTime - valStartTime) / 1000000;
//					if(valDur > 0)
//						log.info("validateInstAmt_020 Duration "+DurationFormatUtils
//						.formatDuration(valDur, "HH:mm:ss.S")+" milliseconds. ");
//					valStartTime = System.nanoTime();

          if (mandate.getColltnAmt() != null && mandate.getColltnAmt().getCcy() != null) {
            if (validateCurrency(mandate.getColltnAmt().getCcy())) {
              mandateSeverity = mandateSeverity + 0;
              log.debug(
                  "******************validateCollAmtCurr_010.075 - Passed.******************");
            } else {
              generateStatusErrorDetailsList("901198", mndReqTrnId, txnErrorType);
              mandateSeverity++;
              log.info("******************validateCollAmtCurr_010.075 - Failed.******************");
            }
          }//Coll amt is empty
          else {
            generateStatusErrorDetailsList("901198", mndReqTrnId, txnErrorType);
            mandateSeverity++;
            log.info("******************validateCollAmtCurr_010.075 - Failed.******************");
          }

//					valEndTime = System.nanoTime();
//					valDur = (valEndTime - valStartTime) / 1000000;
//					if(valDur > 0)
//						log.info("validateCollAmtCurr_010.075 Duration "+DurationFormatUtils
//						.formatDuration(valDur, "HH:mm:ss.S")+" milliseconds. ");
//					valStartTime = System.nanoTime();

          //____________________________Validate Installment
          // Amount_021_____________________________________//
          if (mandate.getColltnAmt() != null && mandate.getColltnAmt().getValue() != null) {
            if (!validateInitialAmount(mandate.getColltnAmt().getValue().toString())) {
              generateStatusErrorDetailsList("901109", mndReqTrnId, txnErrorType);
              mandateSeverity++;
              log.info(
                  "******************validateInstallmentAmount_021 - FAILED.******************");
            } else {
              mandateSeverity = mandateSeverity + 0;
              log.debug(
                  "******************validateInstallmentAmount_021 - PASSED.******************");
            }
          }

//					valEndTime = System.nanoTime();
//					valDur = (valEndTime - valStartTime) / 1000000;
//					if(valDur > 0)
//						log.info("validateInstallmentAmount_021 Duration "+DurationFormatUtils
//						.formatDuration(valDur, "HH:mm:ss.S")+" milliseconds. ");
//					valStartTime = System.nanoTime();

          //____________________________Validate collection Amoutn_rule
          // 010-050_____________________________________//
          if (mandate.getMaxAmt() != null && mandate.getMaxAmt().getValue() != null) {
            if (!validateCollAmountIsLessThanMaxAmount(
                mandate.getColltnAmt().getValue().doubleValue(),
                mandate.getMaxAmt().getValue().doubleValue())) {
              generateStatusErrorDetailsList("901111", mndReqTrnId, txnErrorType);
              mandateSeverity++;
              log.info(
                  "******************validateCollAmountIsLessThanMaxAmount_050 - Failed" +
                   ".******************");
            } else {
              mandateSeverity = mandateSeverity + 0;
              log.debug(
                  "******************validateCollAmountIsLessThanMaxAmount_050 - Passed" +
                   ".******************");
            }
          } else {
            generateStatusErrorDetailsList("901111", mndReqTrnId, txnErrorType);
            mandateSeverity++;
            log.info(
                "******************validateCollAmountIsLessThanMaxAmount_050 - Failed" +
                 ".******************");
          }
//					valEndTime = System.nanoTime();
//					valDur = (valEndTime - valStartTime) / 1000000;
//					if(valDur > 0)
//						log.info("validateCollAmountIsLessThanMaxAmount_050 Duration
//						"+DurationFormatUtils.formatDuration(valDur, "HH:mm:ss.S")+" milliseconds.
//						");
//					valStartTime = System.nanoTime();
        }
      } else {
        mandateSeverity = mandateSeverity + 0;
        log.debug("******************validateInstAmt_020 - Passed.******************");
//				valEndTime = System.nanoTime();
//				valDur = (valEndTime - valStartTime) / 1000000;
//				if(valDur > 0)
//					log.info("validateInstAmt_020 Duration "+DurationFormatUtils.formatDuration
//					(valDur, "HH:mm:ss.S")+" milliseconds. ");
//				valStartTime = System.nanoTime();
      }
    }

    //This is conditional as per TRS 15
    if (mandate.getMaxAmt() != null && mandate.getMaxAmt().getCcy() != null) {
      if (validateCurrency(mandate.getMaxAmt().getCcy())) {
        mandateSeverity = mandateSeverity + 0;
        log.debug("******************validateMaxAmtCurr_010.075 - Passed.******************");
      } else {
        generateStatusErrorDetailsList("901198", mndReqTrnId, txnErrorType);
        mandateSeverity++;
        log.info("******************validateMaxAmtCurr_010.075 - Failed.******************");
      }
    }
    //This is optional as per TRS 15
//		valEndTime = System.nanoTime();
//		valDur = (valEndTime - valStartTime) / 1000000;
//		if(valDur > 0)
//			log.info("validateMaxAmtCurr_010.075 Duration "+DurationFormatUtils.formatDuration
//			(valDur, "HH:mm:ss.S")+" milliseconds. ");
//		valStartTime = System.nanoTime();

    //____________________________Validate Mandate Request Transaction
    // Id_Rule010_058_____________________________________//
    if (mandate.getCdtr() != null && mandate.getCdtr().getId() != null &&
        mandate.getCdtr().getId().getOrgId() != null &&
        mandate.getCdtr().getId().getOrgId().getOthr() != null) {
      if (mandate.getCdtr().getId().getOrgId().getOthr().get(0) != null &&
          mandate.getCdtr().getId().getOrgId().getOthr().get(0).getId() != null) {
        String id = mandate.getCdtr().getId().getOrgId().getOthr().get(0).getId().trim();

        if (validateMandateReqTranId(id)) {
          log.debug("******************validateManReqTranId_058 - Passed.******************");
          mandateSeverity = mandateSeverity + 0;

          //__________________Validate Rule 010.059 /010.60 / 010.061_______________________//
          validateMandateRefNumber59_60_61(id);
        } else {
          generateStatusErrorDetailsList("902141", mndReqTrnId, txnErrorType);
          mandateSeverity++;
          log.info("******************validateManReqTranId_058 - Failed.******************");
        }
      } else {
        generateStatusErrorDetailsList("902141", mndReqTrnId, txnErrorType);
        mandateSeverity++;
        log.info("******************validateManReqTranId_058 - Failed.******************");
      }
    } else {
      generateStatusErrorDetailsList("902141", mndReqTrnId, txnErrorType);
      mandateSeverity++;
      log.info("******************validateManReqTranId_058- Failed.******************");
    }
//		valEndTime = System.nanoTime();
//		valDur = (valEndTime - valStartTime) / 1000000;
//		if(valDur > 0)
//			log.info("validateManReqTranId_058 Duration "+DurationFormatUtils.formatDuration
//			(valDur, "HH:mm:ss.S")+" milliseconds. ");
//		valStartTime = System.nanoTime();

    //SalehaR - 2016/09/14 CDV for Creditor Account no longer valid
    //____________________________Validate  Creditor CDV Rule_rule
    // 010_026_____________________________________//
		/*if(mandate.getCdtrAcct() != null && mandate.getCdtrAcct().getId() != null && mandate
		.getCdtrAcct().getId().getOthr() != null && mandate.getCdtrAcct().getId().getOthr().getId
		() != null &&
						mandate.getCdtrAcct() != null && mandate.getCdtrAcct().getTp() != null &&
						 mandate.getCdtrAcct().getTp().getPrtry() != null)
				{
					log.info("IN THE CREDITOR ACCOUNT VALIDATION");
					String creditorBranchNo = null;
					String creditorAccNo = null;
					String creditorAccTypeDesc = null;
					String creditorAccType = null;
					 cdvCheck = false;

					if(mandate.getCdtrAgt() != null && mandate.getCdtrAgt().getFinInstnId() !=
					null && mandate.getCdtrAgt().getFinInstnId().getClrSysMmbId() != null &&
					mandate.getCdtrAgt().getFinInstnId().getClrSysMmbId().getMmbId() != null)
						creditorBranchNo = mandate.getCdtrAgt().getFinInstnId().getClrSysMmbId()
						.getMmbId() ;

					if(mandate.getCdtrAcct() != null && mandate.getCdtrAcct().getId() != null &&
					mandate.getCdtrAcct().getId().getOthr() != null && mandate.getCdtrAcct().getId
					().getOthr().getId() != null)
						creditorAccNo = mandate.getCdtrAcct().getId().getOthr().getId();

					creditorAccTypeDesc = mandate.getCdtrAcct().getTp().getPrtry();

					MdtCnfgAccountTypeEntity casCnfgAccountTypeEntity = (MdtCnfgAccountTypeEntity)
					 valBeanRemote.validateAccountType(creditorAccTypeDesc);
					if(casCnfgAccountTypeEntity != null && casCnfgAccountTypeEntity
					.getAccountTypeCode() != null)
						creditorAccType = casCnfgAccountTypeEntity.getAccountTypeCode();

					if(creditorBranchNo != null && creditorAccNo != null && creditorAccType !=
					null)
					{
						cdvModel = new CdvModel();
						cdvModel.setBranch(creditorBranchNo);
						cdvModel.setAccountNumber(creditorAccNo);
						cdvModel.setAccounttype(creditorAccType);

//						log.info("cdvModel--->:"+cdvModel.toString());
						cdvValidation = new CdvValidation();
						boolean cdvCheck = cdvValidation.checkCdv(cdvModel);

						if(cdvCheck)
						{
							mandateSeverity = mandateSeverity+0;
							log.info("******************CDVCreditorAccNo - 026 -
							Passed******************");
						}
						else
						{
							if(systemType.equalsIgnoreCase(sadcSystem))
								populateMdtErrorList("901114",msgId,mandate.getMndtId());
							else
							generateStatusErrorDetailsList("901114", mndReqTrnId, txnErrorType);
							mandateSeverity++;
							log.info("******************CDVCreditorAccNo - 026 - Failed
							.******************");
						}
					}
					else
					{
						if(systemType.equalsIgnoreCase(sadcSystem))
							populateMdtErrorList("901114",msgId,mandate.getMndtId());
						else
							generateStatusErrorDetailsList("901114", mndReqTrnId, txnErrorType);
						mandateSeverity++;
						log.info("******************CDVCreditorAccNo - 026 - Failed
						.******************");
					}
				}*/
    //If Creditor is not populated then do not validate as it is optional
    //if no creditor account type - do not validate the account number

    //____________________________Validate  Creditor Acc Num _rule
    // 010_999_____________________________________//
    if (mandate.getCdtrAcct() != null && mandate.getCdtrAcct().getId() != null &&
        mandate.getCdtrAcct().getId().getOthr() != null &&
        mandate.getCdtrAcct().getId().getOthr().getId() != null) {
      if (validateRule999(mandate.getCdtrAcct().getId().getOthr().getId())) {
        log.debug("******************validateCdtrAcctNo_010.999 - Passed.******************");
        mandateSeverity = mandateSeverity + 0;
      } else {
        generateStatusErrorDetailsList("910099", mndReqTrnId, txnErrorType);
        mandateSeverity++;
        log.info("******************validateCdtrAcctNo_010.999 - Failed.******************");
      }
    }
    //This is optional in TRS 15
//		valEndTime = System.nanoTime();
//		valDur = (valEndTime - valStartTime) / 1000000;
//		if(valDur > 0)
//			log.info("validateCdtrAcctNo_010.999 Duration "+DurationFormatUtils.formatDuration
//			(valDur, "HH:mm:ss.S")+" milliseconds. ");
//		valStartTime = System.nanoTime();

    //____________________________Validate  Creditor Agent_rule
    // 010_025_____________________________________//
    if (mandate.getCdtrAgt() != null && mandate.getCdtrAgt().getFinInstnId() != null &&
        mandate.getCdtrAgt().getFinInstnId().getClrSysMmbId() != null &&
        mandate.getCdtrAgt().getFinInstnId().getClrSysMmbId().getMmbId() != null) {
      if (!validateCreditorBranchNo(
          mandate.getCdtrAgt().getFinInstnId().getClrSysMmbId().getMmbId(), "Creditor")) {
        generateStatusErrorDetailsList("901113", mndReqTrnId, txnErrorType);
        mandateSeverity++;
        log.info("******************validateCreditorAgent - 025 - Failed.******************");
      } else {
        this.branchmemberIdCreditor = branchmemberIdCreditorVal;
        mandateSeverity = mandateSeverity + 0;
        log.debug("******************validateCreditorAgent - 025 - Passed******************");

      }
    }
    //This is optional in TRS 15
//		valEndTime = System.nanoTime();
//		valDur = (valEndTime - valStartTime) / 1000000;
//		if(valDur > 0)
//			log.info("validateCreditorAgent_010.025 Duration "+DurationFormatUtils.formatDuration
//			(valDur, "HH:mm:ss.S")+" milliseconds. ");
//		valStartTime = System.nanoTime();

    //____________________________Validate Cred Abb Short
    // Name_____________________________________//
    if (mandate.getUltmtCdtr() != null && mandate.getUltmtCdtr().getId() != null &&
        mandate.getUltmtCdtr().getId().getOrgId() != null &&
        mandate.getUltmtCdtr().getId().getOrgId().getOthr() != null &&
        mandate.getUltmtCdtr().getId().getOrgId().getOthr().size() > 0) {
      if (mandate.getUltmtCdtr().getId().getOrgId().getOthr().get(0) != null &&
          mandate.getUltmtCdtr().getId().getOrgId().getOthr().get(0).getId() != null) {
        crAbbShortName =
            mandate.getUltmtCdtr().getId().getOrgId().getOthr().get(0).getId().toString();
        if (!validateRule999(mandate.getUltmtCdtr().getId().getOrgId().getOthr().get(0).getId())) {
          generateStatusErrorDetailsList("910099", mndReqTrnId, txnErrorType);
          mandateSeverity++;
          log.info("******************validateCrAbbShortName_010.999 - Failed.******************");
        } else {
          log.debug("******************validateCrAbbShortName_010.999 - Passed.******************");
          mandateSeverity = mandateSeverity + 0;
        }
      } else {
        generateStatusErrorDetailsList("910099", mndReqTrnId, txnErrorType);
        mandateSeverity++;
        log.info("******************validateCrAbbShortName_010.999 - Failed.******************");
      }
    } else {
      generateStatusErrorDetailsList("910099", mndReqTrnId, txnErrorType);
      mandateSeverity++;
      log.info("******************validateCrAbbShortName_010.999 - Failed.******************");
    }
//		valEndTime = System.nanoTime();
//		valDur = (valEndTime - valStartTime) / 1000000;
//		if(valDur > 0)
//			log.info("validateCrAbbShortName_010.999 Duration "+DurationFormatUtils.formatDuration
//			(valDur, "HH:mm:ss.S")+" milliseconds. ");
//		valStartTime = System.nanoTime();

    //____________________________Validate Debtor Name_rule
    // 010_040______________________________________//
    if (mandate.getDbtr() != null && mandate.getDbtr().getNm() != null) {
      if (!validateDebtorName(mandate.getDbtr().getNm())) {
        generateStatusErrorDetailsList("901147", mndReqTrnId, txnErrorType);
        mandateSeverity++;
        log.info("******************validateDebtorName_040 - Failed.******************");
      } else {
        mandateSeverity = mandateSeverity + 0;
        log.debug("******************validateDebtorName_040 - Passed.******************");
      }
    }
    //This is an optional as per TRS 15
//		valEndTime = System.nanoTime();
//		valDur = (valEndTime - valStartTime) / 1000000;
//		if(valDur > 0)
//			log.info("validateDebtorName_040 Duration "+DurationFormatUtils.formatDuration(valDur,
//			"HH:mm:ss.S")+" milliseconds. ");
//		valStartTime = System.nanoTime();

    //____________________________Validate Debtor Id Num 010
    // .027______________________________________//
    if (mandate.getDbtr() != null && mandate.getDbtr().getId() != null &&
        mandate.getDbtr().getId().getPrvtId() != null &&
        mandate.getDbtr().getId().getPrvtId().getOthr() != null) {
      if (mandate.getDbtr().getId().getPrvtId().getOthr().get(0) != null &&
          mandate.getDbtr().getId().getPrvtId().getOthr().get(0).getId() != null) {
        if (validateDebtorIdentifier(
            mandate.getDbtr().getId().getPrvtId().getOthr().get(0).getId().toString())) {
          mandateSeverity = mandateSeverity + 0;
          log.debug(
              "******************validateDebtorIdentifier_010.027 - Passed.******************");
        } else {
          generateStatusErrorDetailsList("901122", mndReqTrnId, txnErrorType);
          mandateSeverity++;
          log.info(
              "******************validateDebtorIdentifier_010.027 - Failed.******************");
        }
      }
      //This is an optional in TRS 15
    }
    //	This is an optional in TRS 15
//		valEndTime = System.nanoTime();
//		valDur = (valEndTime - valStartTime) / 1000000;
//		if(valDur > 0)
//			log.info("validateDebtorIdentifier_010.027 Duration "+DurationFormatUtils
//			.formatDuration(valDur, "HH:mm:ss.S")+" milliseconds. ");
//		valStartTime = System.nanoTime();


//		SALEHAR-2020/07/23 - Remove CDV as per TDA		
//		//____________________________Validate Debtor Account
//		Number_Rule010_028_____________________________________//
//		if(mandate.getDbtrAcct() != null && mandate.getDbtrAcct().getId() != null && mandate
//		.getDbtrAcct().getId().getOthr() != null && mandate.getDbtrAcct().getId().getOthr().getId
//		() != null)
//		{
//			//String debtorBranchNo = null;
//			String debtorAccNo = null;
//			String debtorAccTypeDesc = null;
//			String debtorAccType = null;
//			cdvCheck = false;
//
//			debtorAccNo = mandate.getDbtrAcct().getId().getOthr().getId();
//
//			if(mandate.getDbtrAgt() != null && mandate.getDbtrAgt().getFinInstnId() != null &&
//			mandate.getDbtrAgt().getFinInstnId().getClrSysMmbId() != null && mandate.getDbtrAgt()
//			.getFinInstnId().getClrSysMmbId().getMmbId() != null)
//				debtorBranchNo = mandate.getDbtrAgt().getFinInstnId().getClrSysMmbId().getMmbId() ;
//
//			if(mandate.getDbtrAcct() != null && mandate.getDbtrAcct().getTp() != null &&  mandate
//			.getDbtrAcct().getTp().getPrtry() != null)
//			{
//				debtorAccTypeDesc = mandate.getDbtrAcct().getTp().getPrtry();
//
//				MdtCnfgAccountTypeEntity casCnfgAccountTypeEntity = (MdtCnfgAccountTypeEntity)
//				valBeanRemote.validateAccountType(debtorAccTypeDesc);
//				if(casCnfgAccountTypeEntity != null && casCnfgAccountTypeEntity.getAccountTypeCode
//				() != null)
//					debtorAccType = casCnfgAccountTypeEntity.getAccountTypeCode();
//			}
//
//
//			if(debtorBranchNo != null && debtorAccNo != null && debtorAccType != null)
//			{
//				cdvModel = new CommonsCDVModel();
//				cdvModel.setBranch(debtorBranchNo);
//				cdvModel.setAccountNumber(debtorAccNo);
//				cdvModel.setAccountType(debtorAccType);
//
//				//2016/12/20 - SalehaR	 - Call CDV Directly from Bean						
//				//						cdvValidation = new CdvValidation();
//				//						boolean cdvCheck = cdvValidation.checkCdv(cdvModel);
//				boolean cdvCheck = validateCDVAccount(cdvModel);
//
//				if(cdvCheck)
//				{
//					mandateSeverity = mandateSeverity+0;
//					log.debug("******************CDVDebtorAccNo _028 - Passed******************");
//				}
//				else
//				{
//					if(systemType.equalsIgnoreCase(sadcSystem))
//						populateMdtErrorList("901115",msgId,mandate.getMndtId());
//					else
//						generateStatusErrorDetailsList("901115", mndReqTrnId, txnErrorType);
//					mandateSeverity++;
//					log.info("******************CDVDebtorAccNo _028 - Failed.******************");
//				}
//			}
//			else
//			{
//				if(systemType.equalsIgnoreCase(sadcSystem))
//					populateMdtErrorList("901115",msgId,mandate.getMndtId());
//				else
//					generateStatusErrorDetailsList("901115", mndReqTrnId, txnErrorType);
//				mandateSeverity++;
//				log.info("******************CDVDebtorAccNo _028_Branch,Acc,AccType is empty -
//				Failed.******************");
//			}
//		}
//		//This field is optional in TRS 15


    if (mandate.getDbtrAcct() != null && mandate.getDbtrAcct().getId() != null &&
        mandate.getDbtrAcct().getId().getOthr() != null &&
        mandate.getDbtrAcct().getId().getOthr().getId() != null) {
      if (mandate.getDbtrAcct() != null && mandate.getDbtrAcct().getTp() != null &&
          mandate.getDbtrAcct().getTp().getPrtry() != null) {
        log.debug("******************vaidateDbtrAccRltnshp_010.064 - Passed.******************");
        mandateSeverity = mandateSeverity + 0;
      } else {
        generateStatusErrorDetailsList("901186", mndReqTrnId, txnErrorType);
        mandateSeverity++;
        log.info("******************vaidateDbtrAccRltnshp_010.064 - Failed.******************");
      }
    }
//		valEndTime = System.nanoTime();
//		valDur = (valEndTime - valStartTime) / 1000000;
//		if(valDur > 0)
//			log.info("vaidateDbtrAccRltnshp_010.064 Duration "+DurationFormatUtils.formatDuration
//			(valDur, "HH:mm:ss.S")+" milliseconds. ");
//		valStartTime = System.nanoTime();


//		SALEHAR-2020/08/06 - Remove CDV as per TDA (SCR264)
//		//____________________________Validate Debtor Account
//		Type_Rule010_055_____________________________________//
//		if(mandate.getDbtrAcct() != null && mandate.getDbtrAcct().getTp() != null && mandate
//		.getDbtrAcct().getTp().getPrtry() != null)
//		{
//			if(!validateAccountType(mandate.getDbtrAcct().getTp().getPrtry().toString()))
//			{
//				generateStatusErrorDetailsList("901068", mndReqTrnId, txnErrorType);
//				mandateSeverity++;
//				log.info("******************validateDebtorAccRule_055 - Failed
//				.******************");
//			}
//			else
//			{
//				log.debug("******************validateDebtorAccRule_055 - Passed
//				.******************");
//				mandateSeverity = mandateSeverity+0;
//			}
//		}//This is optional in TRS 15
//		valEndTime = System.nanoTime();
//		valDur = (valEndTime - valStartTime) / 1000000;
//		if(valDur > 0)
//			log.info("validateDebtorAccType_055 Duration "+DurationFormatUtils.formatDuration
//			(valDur, "HH:mm:ss.S")+" milliseconds. ");
//		valStartTime = System.nanoTime();


    //____________________________Validate Debtor Agent(BranchNo)
    // 010_029_________________________________________________________//
    if (mandate.getDbtrAgt() != null && mandate.getDbtrAgt().getFinInstnId() != null &&
        mandate.getDbtrAgt().getFinInstnId().getClrSysMmbId() != null &&
        mandate.getDbtrAgt().getFinInstnId().getClrSysMmbId().getMmbId() != null) {
      debtorBranchNo = mandate.getDbtrAgt().getFinInstnId().getClrSysMmbId().getMmbId();
      log.debug("debtorBranchNo*****************" + debtorBranchNo);
      if (!validateDebtorBranchNo(mandate.getDbtrAgt().getFinInstnId().getClrSysMmbId().getMmbId(),
          "Debtor")) {
        generateStatusErrorDetailsList("901116", mndReqTrnId, txnErrorType);
        mandateSeverity++;
        log.info("******************validateDebtorAgent - 029 - Failed.******************");
      } else {
        this.branchmemberIdDebtor = branchmemberIdDebtorVal;
        mandateSeverity = mandateSeverity + 0;
        log.debug("******************validateDebtorAgent - 029- Passed******************");
      }
      //This is conditional in TRS 15
    }
//		valEndTime = System.nanoTime();
//		valDur = (valEndTime - valStartTime) / 1000000;
//		if(valDur > 0)
//			log.info("validateDebtorAgent_029 Duration "+DurationFormatUtils.formatDuration
//			(valDur, "HH:mm:ss.S")+" milliseconds. ");
//		valStartTime = System.nanoTime();

    //____________________________Validate Debtor Agent/DbtrAcct_Rltnship_010
    // .070_________________________________________________________//
    if (mandate.getDbtrAcct() != null && mandate.getDbtrAcct().getId() != null &&
        mandate.getDbtrAcct().getId().getOthr() != null &&
        mandate.getDbtrAcct().getId().getOthr().getId() != null) {
      if (mandate.getDbtrAcct() != null && mandate.getDbtrAgt() != null &&
          mandate.getDbtrAgt().getFinInstnId() != null &&
          mandate.getDbtrAgt().getFinInstnId().getClrSysMmbId() != null &&
          mandate.getDbtrAgt().getFinInstnId().getClrSysMmbId().getMmbId() != null) {
        log.debug(
            "******************vaidateDbtrAccDbtrAgtRltnshp_010.070 - Passed.******************");
        mandateSeverity = mandateSeverity + 0;
      } else {
        generateStatusErrorDetailsList("901191", mndReqTrnId, txnErrorType);
        mandateSeverity++;
        log.info(
            "******************vaidateDbtrAccDbtrAgtRltnshp_010.070 - Failed.******************");
      }
    }
//		valEndTime = System.nanoTime();
//		valDur = (valEndTime - valStartTime) / 1000000;
//		if(valDur > 0)
//			log.info("vaidateDbtrAccDbtrAgtRltnshp_010.070 Duration "+DurationFormatUtils
//			.formatDuration(valDur, "HH:mm:ss.S")+" milliseconds. ");
//		valStartTime = System.nanoTime();

    //This is conditional in TRS 15
    //2018-03-12-SalehaR-This is applicable to TT3 only (See TRS17 comments)
    //			if(mandate.getTp() != null && mandate.getTp().getLclInstrm() != null && mandate
    //			.getTp().getLclInstrm().getPrtry() != null)
    //			{
    //				if(mandate.getTp().getLclInstrm().getPrtry().equalsIgnoreCase("0228"))
    //				{
    //					if(mandate.getRfrdDoc() != null && mandate.getRfrdDoc().getNb() != null)
    //					{
    //						String macCode = mandate.getRfrdDoc().getNb().trim();
    //
    //						if(macCode == null && macCode.isEmpty() && macCode.length() == 0)
    //						{
    //							generateStatusErrorDetailsList("901187", mndReqTrnId,
    //							txnErrorType);
    //							mandateSeverity++;
    //							log.info("******************validateMacCode_010.065 - Failed
    //							.******************");
    //						}
    //						else
    //						{
    //							log.debug("******************validateMacCode_010.065 - Passed
    //							.******************");
    //							mandateSeverity = mandateSeverity+0;
    //						}
    //					}
    //					else
    //					{
    //						generateStatusErrorDetailsList("901187", mndReqTrnId, txnErrorType);
    //						mandateSeverity++;
    //						log.info("******************validateMacCode_010.065 - Failed
    //						.******************");
    //					}
    //				}
    //			}
    //			else
    //			{
    //				generateStatusErrorDetailsList("901187", mndReqTrnId, txnErrorType);
    //				mandateSeverity++;
    //				log.info("******************validateMacCode_010.065 - Failed
    //				.******************");
    //			}

    //***********************************VALIDATION ORIGNL MANDATE
    // INFORMATION****************************************************************//


    //____________________________Validate MandateId_010.999______________________________________//
    if (origMandate.getMndtId() != null) {
      if (validateRule999(origMandate.getMndtId())) {
        log.debug("******************validateMndtId_010.999 - Passed.******************");
        mandateSeverity = mandateSeverity + 0;
      } else {
        generateStatusErrorDetailsList("910099", mndReqTrnId, txnErrorType);
        mandateSeverity++;
        log.info("******************validateMndtId_010.999 - Failed.******************");
      }
    } else {
      generateStatusErrorDetailsList("910099", mndReqTrnId, txnErrorType);
      mandateSeverity++;
      log.info("******************validateMndtId_010.999 - Failed.******************");
    }
//		valEndTime = System.nanoTime();
//		valDur = (valEndTime - valStartTime) / 1000000;
//		if(valDur > 0)
//			log.info("validateMndtId_010.999 Duration "+DurationFormatUtils.formatDuration(valDur,
//			"HH:mm:ss.S")+" milliseconds. ");
//		valStartTime = System.nanoTime();

    //____________________________Validate Contract Reference _rule
    // 010_042_____________________________________//
    if (origMandate.getMndtReqId() != null) {
      if (!validateContractReference(origMandate.getMndtReqId())) {
        generateStatusErrorDetailsList("901131", mndReqTrnId, txnErrorType);
        mandateSeverity++;
        log.info("******************validateContractReference_042 - Failed.******************");
      } else {
        log.debug("******************validateContractReference_042 - Passed.******************");
        mandateSeverity = mandateSeverity + 0;
      }
    }
    //This field is optional in TRS 15
//		valEndTime = System.nanoTime();
//		valDur = (valEndTime - valStartTime) / 1000000;
//		if(valDur > 0)
//			log.info("validateContractReference_042 Duration "+DurationFormatUtils.formatDuration
//			(valDur, "HH:mm:ss.S")+" milliseconds. ");
//		valStartTime = System.nanoTime();

    //____________________________Validate Creditor Name_010
    // .099_____________________________________//
    if (origMandate.getCdtr() != null && origMandate.getCdtr().getNm() != null) {
      //				log .info("the creditor is ***************************"+ origMandate
      //				.getCdtr().getNm());

      if (validateRule999(origMandate.getCdtr().getNm())) {
        log.debug("******************validateCdtrNm_010.999 - Passed.******************");
        mandateSeverity = mandateSeverity + 0;
      } else {
        generateStatusErrorDetailsList("910099", mndReqTrnId, txnErrorType);
        mandateSeverity++;
        log.info("******************validateCdtrNm_010.999 - Failed.******************");
      }
    } else {
      generateStatusErrorDetailsList("910099", mndReqTrnId, txnErrorType);
      mandateSeverity++;
      log.info("******************validateCdtrNm_010.999 - Failed.******************");
    }
//		valEndTime = System.nanoTime();
//		valDur = (valEndTime - valStartTime) / 1000000;
//		if(valDur > 0)
//			log.info("validateCdtrNm_010.999 Duration "+DurationFormatUtils.formatDuration(valDur,
//			"HH:mm:ss.S")+" milliseconds. ");
//		valStartTime = System.nanoTime();


    //____________________________Validate MRTI_010.099_____________________________________//
    if (origMandate.getCdtr() != null && origMandate.getCdtr().getId() != null &&
        origMandate.getCdtr().getId().getOrgId() != null &&
        origMandate.getCdtr().getId().getOrgId().getOthr() != null) {
      if (origMandate.getCdtr().getId().getOrgId().getOthr().get(0) != null &&
          origMandate.getCdtr().getId().getOrgId().getOthr().get(0).getId() != null) {
        if (validateRule999(origMandate.getCdtr().getId().getOrgId().getOthr().get(0).getId())) {
          log.debug("******************validateMRTI_010.999 - Passed.******************");
          mandateSeverity = mandateSeverity + 0;
        } else {
          generateStatusErrorDetailsList("910099", mndReqTrnId, txnErrorType);
          mandateSeverity++;
          log.info("******************validateMRTI_010.999 - Failed.******************");
        }
      } else {
        generateStatusErrorDetailsList("910099", mndReqTrnId, txnErrorType);
        mandateSeverity++;
        log.info("******************validateMRTI_010.999 - Failed.******************");
      }
    } else {
      generateStatusErrorDetailsList("910099", mndReqTrnId, txnErrorType);
      mandateSeverity++;
      log.info("******************validateMRTI_010.999 - Failed.******************");
    }
//		valEndTime = System.nanoTime();
//		valDur = (valEndTime - valStartTime) / 1000000;
//		if(valDur > 0)
//			log.info("validateMRTI_010.999 Duration "+DurationFormatUtils.formatDuration(valDur,
//			"HH:mm:ss.S")+" milliseconds. ");
//		valStartTime = System.nanoTime();

    //____________________________Validate Debtor Name_010
    // .099_____________________________________//
    if (origMandate.getDbtr() != null && origMandate.getDbtr().getNm() != null) {
      if (validateRule999(origMandate.getDbtr().getNm())) {
        log.debug("******************validateDbtrNm_010.999 - Passed.******************");
        mandateSeverity = mandateSeverity + 0;
      } else {
        generateStatusErrorDetailsList("910099", mndReqTrnId, txnErrorType);
        mandateSeverity++;
        log.info("******************validateDbtrNm_010.999 - Failed.******************");
      }
    } else {
      generateStatusErrorDetailsList("910099", mndReqTrnId, txnErrorType);
      mandateSeverity++;
      log.info("******************validateDbtrNm_010.999 - Failed.******************");
    }
//		valEndTime = System.nanoTime();
//		valDur = (valEndTime - valStartTime) / 1000000;
//		if(valDur > 0)
//			log.info("validateDbtrNm_010.999 Duration "+DurationFormatUtils.formatDuration(valDur,
//			"HH:mm:ss.S")+" milliseconds. ");
//		valStartTime = System.nanoTime();

    //____________________________Validate Debtor Bank_010
    // .099_____________________________________//
    if (origMandate.getDbtrAgt() != null && origMandate.getDbtrAgt().getFinInstnId() != null &&
        origMandate.getDbtrAgt().getFinInstnId().getClrSysMmbId() != null &&
        origMandate.getDbtrAgt().getFinInstnId().getClrSysMmbId().getMmbId() != null) {

      if (validateRule999(origMandate.getDbtrAgt().getFinInstnId().getClrSysMmbId().getMmbId())) {
        log.debug("******************validateDbtrAgt_010.999 - Passed.******************");
        mandateSeverity = mandateSeverity + 0;

        if (!validateDebtorBranchNo(
            origMandate.getDbtrAgt().getFinInstnId().getClrSysMmbId().getMmbId(), "Debtor")) {
          generateStatusErrorDetailsList("901116", mndReqTrnId, txnErrorType);
          mandateSeverity++;
          log.info(
              "******************validateOrgnlMndt_DebtorAgentAsADebtor_010.999 - Failed" +
               ".******************");
        }
      } else {
        generateStatusErrorDetailsList("910099", mndReqTrnId, txnErrorType);
        mandateSeverity++;
        log.info("******************validateDbtrAgt_010.999 - Failed.******************");
      }
    } else {
      generateStatusErrorDetailsList("910099", mndReqTrnId, txnErrorType);
      mandateSeverity++;
      log.info("******************validateDbtrAgt_010.999 - Failed.******************");
    }
//		valEndTime = System.nanoTime();
//		valDur = (valEndTime - valStartTime) / 1000000;
//		if(valDur > 0)
//			log.info("validateDbtrAgt_010.999 Duration "+DurationFormatUtils.formatDuration
//			(valDur, "HH:mm:ss.S")+" milliseconds. ");
//		valStartTime = System.nanoTime();

    //____________________________Validate Authentication Type
    // 010_031______________________________________//
    if (supplementaryData1 != null && supplementaryData1.getEnvlp() != null &&
        supplementaryData1.getEnvlp().getCnts() != null &&
        supplementaryData1.getEnvlp().getCnts().getAthntctnTp() != null) {
      if (!validateAuthenticationType(
          supplementaryData1.getEnvlp().getCnts().getAthntctnTp().toString())) {
        generateStatusErrorDetailsList("901118", mndReqTrnId, txnErrorType);
        mandateSeverity++;
        log.info("******************validateAuthType_031 - Failed.******************");
      } else {
        mandateSeverity = mandateSeverity + 0;
        log.debug("******************validateAuthType_031 - Passed******************");
      }
    } else {
      generateStatusErrorDetailsList("901118", mndReqTrnId, txnErrorType);
      mandateSeverity++;
      log.info("******************validateAuthType_031 - Failed.******************");
    }
//		valEndTime = System.nanoTime();
//		valDur = (valEndTime - valStartTime) / 1000000;
//		if(valDur > 0)
//			log.info("validateAuthType_031 Duration "+DurationFormatUtils.formatDuration(valDur,
//			"HH:mm:ss.S")+" milliseconds. ");
//		valStartTime = System.nanoTime();


    //____________________________Validate Authentication Type Relationship_010
    // .066______________________________________//
    if (supplementaryData1 != null && supplementaryData1.getEnvlp() != null &&
        supplementaryData1.getEnvlp().getCnts() != null
        && supplementaryData1.getEnvlp().getCnts().getAthntctnTp() != null) {

      String authType = supplementaryData1.getEnvlp().getCnts().getAthntctnTp().trim();
      String lclInstCd = null;
		if (mandate.getTp() != null && mandate.getTp().getLclInstrm() != null &&
				mandate.getTp().getLclInstrm().getPrtry() != null) {
			lclInstCd = mandate.getTp().getLclInstrm().getPrtry().toString();
		}
      boolean boolAchId = false, boolLclInst = false, boolAuthType = false;

      if (achId != null && lclInstCd != null && authType != null) {
        lclInstCd = lclInstCd.trim();

		  if (achId.equalsIgnoreCase("021")) {
			  boolAchId = true;
		  }

		  if (lclInstCd.trim().equalsIgnoreCase("0231") ||
              lclInstCd.trim().equalsIgnoreCase("0232") ||
              lclInstCd.trim().equalsIgnoreCase("0233") ||
              lclInstCd.trim().equalsIgnoreCase("0234")) {
			  boolLclInst = true;
		  }

		  if (authType.equalsIgnoreCase("BATCH")) {
			  boolAuthType = true;
		  }

        if (boolAchId && boolLclInst && boolAuthType) {
          mandateSeverity = mandateSeverity + 0;
          log.debug(
              "******************validateAuthTypeRelationship_010.066 - Passed******************");
        } else {
          generateStatusErrorDetailsList("902400", mndReqTrnId, txnErrorType);
          mandateSeverity++;
          log.info(
              "******************validateAuthTypeRelationship_010.066 - Failed.******************");
        }
      } else {
        generateStatusErrorDetailsList("902400", mndReqTrnId, txnErrorType);
        mandateSeverity++;
        log.info(
            "******************validateAuthTypeRelationship_010.066 - Failed.******************");
      }
    } else {
      generateStatusErrorDetailsList("902400", mndReqTrnId, txnErrorType);
      mandateSeverity++;
      log.info(
          "******************validateAuthTypeRelationship_010.066 - Failed.******************");
    }
//		valEndTime = System.nanoTime();
//		valDur = (valEndTime - valStartTime) / 1000000;
//		if(valDur > 0)
//			log.info("validateAuthTypeRelationship_010.066 Duration "+DurationFormatUtils
//			.formatDuration(valDur, "HH:mm:ss.S")+" milliseconds. ");
//		valStartTime = System.nanoTime();

    //Author DimakatsoN -removed due to IN:5403 UAT Internal  IN:5403 - Interbank 08/05/2017
		/*	//____________________________Validate Collection Day
		010_033______________________________________//
			   	if(supplementaryData1 != null && supplementaryData1.getEnvlp() != null &&
			   	supplementaryData1.getEnvlp().getCnts() != null &&
			   			supplementaryData1.getEnvlp().getCnts().getCllctnDy() != null && mandate
			   			.getOcrncs() != null  && mandate.getOcrncs().getFrqcy() != null  &&
			   			mandate.getOcrncs().getFrqcy() != null)
				{

					if(!validateCollectionDay(supplementaryData1.getEnvlp().getCnts().getCllctnDy
					().toString(), mandate.getOcrncs().getFrqcy().toString()))
					{
						generateStatusErrorDetailsList("901120", mndReqTrnId, txnErrorType);
						mandateSeverity++;
						log.info("******************validateCollectionDay_033 - Failed
						.******************");
					}
					else
					{
						mandateSeverity = mandateSeverity+0;
						log.info("******************validateCollectionDay_033 -
						Passed******************");
					}
				}
			   	else
			   	{
			   		generateStatusErrorDetailsList("901120", mndReqTrnId, txnErrorType);
					mandateSeverity++;
					log.info("******************validateCollectionDay_033 - Failed
					.******************");
			   	}*/

    //____________________________Validate Adjustment Rule Indicator
    // 010_034______________________________________//
    if (supplementaryData1 != null && supplementaryData1.getEnvlp() != null &&
        supplementaryData1.getEnvlp().getCnts() != null &&
        supplementaryData1.getEnvlp().getCnts().getDtAdjRl() != null) {
      log.debug("DTADJRL: " + supplementaryData1.getEnvlp().getCnts().getDtAdjRl().toString());
      if (!validateDateAdjRuleInd(
          supplementaryData1.getEnvlp().getCnts().getDtAdjRl().toString())) {
        generateStatusErrorDetailsList("901121", mndReqTrnId, txnErrorType);
        mandateSeverity++;
        log.info("******************validateAdjtRuleInd_034 - Failed.******************");
      } else {
        mandateSeverity = mandateSeverity + 0;
        log.debug("******************validateAdjtRuleInd_034 - Passed******************");
      }
    }
    //This is optional in TRS 15
//		valEndTime = System.nanoTime();
//		valDur = (valEndTime - valStartTime) / 1000000;
//		if(valDur > 0)
//			log.info("validateAdjtRuleInd_034 Duration "+DurationFormatUtils.formatDuration
//			(valDur, "HH:mm:ss.S")+" milliseconds. ");
//		valStartTime = System.nanoTime();

    //____________________________Validate Adjustment Category
    // 010_035_____________________________ _________//
    if (supplementaryData1 != null && supplementaryData1.getEnvlp() != null &&
        supplementaryData1.getEnvlp().getCnts() != null &&
        supplementaryData1.getEnvlp().getCnts().getAdjstCtgy() != null) {
      if (!validateAdjustCategory(
          supplementaryData1.getEnvlp().getCnts().getAdjstCtgy().toString())) {
        generateStatusErrorDetailsList("901125", mndReqTrnId, txnErrorType);
        mandateSeverity++;
        log.info("******************validateAdjtCategory_035 - Failed.******************");
      } else {
        mandateSeverity = mandateSeverity + 0;
        log.debug("******************validateAdjtCategory_035 - Passed******************");
      }
    }
    //This is optional in TRS 15
//		valEndTime = System.nanoTime();
//		valDur = (valEndTime - valStartTime) / 1000000;
//		if(valDur > 0)
//			log.info("validateAdjtCategory_035 Duration "+DurationFormatUtils.formatDuration
//			(valDur, "HH:mm:ss.S")+" milliseconds. ");
//		valStartTime = System.nanoTime();


    //____________________________Validate Adjustment Category
    // 010_039_____________________________ _________//
    if (supplementaryData1 != null && supplementaryData1.getEnvlp() != null &&
        supplementaryData1.getEnvlp().getCnts() != null
        && supplementaryData1.getEnvlp().getCnts().getAdjstCtgy() != null &&
        supplementaryData1.getEnvlp().getCnts().getDbVlTp() != null) {
      if (supplementaryData1.getEnvlp().getCnts().getDbVlTp().trim().equalsIgnoreCase("FIXED")) {
        if (supplementaryData1.getEnvlp().getCnts().getAdjstCtgy().trim().equalsIgnoreCase("N")) {
          mandateSeverity = mandateSeverity + 0;
          log.debug("******************validateAdjtCategory_010.039 - Passed******************");
        } else {

          generateStatusErrorDetailsList("901193", mndReqTrnId, txnErrorType);
          mandateSeverity++;
          log.info("******************validateAdjtCategory_010.039 - Failed.******************");
        }
      }
    }
    //This is optional in TRS 15
//		valEndTime = System.nanoTime();
//		valDur = (valEndTime - valStartTime) / 1000000;
//		if(valDur > 0)
//			log.info("validateAdjtCategory_010.039 Duration "+DurationFormatUtils.formatDuration
//			(valDur, "HH:mm:ss.S")+" milliseconds. ");
//		valStartTime = System.nanoTime();

    //1. If adjustment category is populated and not equal to “N” or “R” and adjustment rate is
    // populated, the adjustment amount must not be populated.
    //2. If adjust category is populated and not equal to “N” or “R” and adjustment rate is not
    // populated, then adjustment amount must be populated

    //____________________________Validate Adjustment Rate _rule
    // 010_036_____________________________________//
    if (supplementaryData1 != null && supplementaryData1.getEnvlp() != null &&
        supplementaryData1.getEnvlp().getCnts() != null &&
        supplementaryData1.getEnvlp().getCnts().getAdjstCtgy() != null) {
      String adjCategory = supplementaryData1.getEnvlp().getCnts().getAdjstCtgy().toString();
      Double adjRate = null;
      Double adjAmt = null;

		if (supplementaryData1.getEnvlp().getCnts().getAdjstRt() != null) {
			adjRate = supplementaryData1.getEnvlp().getCnts().getAdjstRt().doubleValue();
		}

		if (supplementaryData1.getEnvlp().getCnts().getAdjstAmt() != null &&
				supplementaryData1.getEnvlp().getCnts().getAdjstAmt().getValue() != null) {
			adjAmt = supplementaryData1.getEnvlp().getCnts().getAdjstAmt().getValue().doubleValue();
		}

      log.debug("adjCategory STR: " + adjCategory);
      log.debug("supplementaryData1.getEnvlp().getCnts().getAdjstCtgy(): " +
          supplementaryData1.getEnvlp().getCnts().getAdjstCtgy());
      log.debug("adjRate: " + adjRate);
      log.debug("adjAmt: " + adjAmt);

      //Check Currency
      if (supplementaryData1.getEnvlp() != null &&
          supplementaryData1.getEnvlp().getCnts() != null &&
          supplementaryData1.getEnvlp().getCnts().getAdjstAmt() != null &&
          supplementaryData1.getEnvlp().getCnts().getAdjstAmt().getCcy() != null) {
        if (validateCurrency(supplementaryData1.getEnvlp().getCnts().getAdjstAmt().getCcy())) {
          mandateSeverity = mandateSeverity + 0;
          log.debug("******************validateAdjAmtCurr_010.075 - Passed.******************");
        } else {
          generateStatusErrorDetailsList("901198", mndReqTrnId, txnErrorType);
          mandateSeverity++;
          log.info("******************validateAdjAmtCurr_010.075 - Failed.******************");
        }
      }
//			valEndTime = System.nanoTime();
//			valDur = (valEndTime - valStartTime) / 1000000;
//			if(valDur > 0)
//				log.info("validateAdjAmtCurr_010.075 Duration "+DurationFormatUtils.formatDuration
//				(valDur, "HH:mm:ss.S")+" milliseconds. ");
//			valStartTime = System.nanoTime();

      //Rule 010.069 Only if equal to N remove R
      //Rule 010.069 uncommented by DimakatsoN for SRC258(CHG-191424)
      if (adjCategory.equalsIgnoreCase("N") || adjCategory.equalsIgnoreCase("R")) {
        //if(adjRate != null || adjAmt != null)
        if ((adjRate != null && adjRate != 0)) {
          generateStatusErrorDetailsList("901190", mndReqTrnId, txnErrorType);
          mandateSeverity++;
          log.info("******************validategetAdjstRate_036 - Failed.******************");
        } else {
          mandateSeverity = mandateSeverity + 0;
          log.debug("******************validategetAdjstRate_036 - Passed******************");
        }
      } else {
        if (adjRate != null && adjAmt != null) {
          generateStatusErrorDetailsList("901190", mndReqTrnId, txnErrorType);
          mandateSeverity++;
          log.info("******************validategetAdjstRate_036 - Failed.******************");
        } else {
          // If Adjustment Rate is populated, then adjustment amount must NOT be populated.
          if (adjRate != null) {
            //Adjustment Amount must NOT be populated.
            if (adjAmt == null) {
              mandateSeverity = mandateSeverity + 0;
              log.debug("******************validategetAdjstRate_036 - Passed******************");
            } else {
              generateStatusErrorDetailsList("901190", mndReqTrnId, txnErrorType);
              mandateSeverity++;
              log.info("******************validategetAdjstRate_036 - Failed.******************");
            }
          } else {
            // If Adjustment Rate is NOT populated, adjustment amount MUST be populated
            if (adjAmt != null) {
              mandateSeverity = mandateSeverity + 0;
              log.debug("******************validategetAdjstRate_036 - Passed******************");
            } else {
              generateStatusErrorDetailsList("901190", mndReqTrnId, txnErrorType);
              mandateSeverity++;
              log.info("******************validategetAdjstRate_036 - Failed.******************");
            }
          }
        }
      }
//			valEndTime = System.nanoTime();
//			valDur = (valEndTime - valStartTime) / 1000000;
//			if(valDur > 0)
//				log.info("validategetAdjstRate_036 Duration "+DurationFormatUtils.formatDuration
//				(valDur, "HH:mm:ss.S")+" milliseconds. ");
//			valStartTime = System.nanoTime();
    }
    //As per email with Martin S. - 2015-11-10 - This will not fail if the Mandate Category is
    // null.
    //				else
    //				{
    //					generateStatusErrorDetailsList("901190", mndReqTrnId, txnErrorType);
    //					mandateSeverity++;
    //					log.info("******************validategetAdjstRate_036 - Failed
    //					.******************");
    //				}

    //1. If adjustment category is populated and not equal to “N” or “R” and adjustment rate is
    // populated, the adjustment amount must not be populated.
    //2. If adjust category is populated and not equal to “N” or “R” and adjustment rate is not
    // populated, then adjustment amount must be populated

    //____________________________Validate Adjustment Rate _rule
    // 010_037_____________________________________//
    if (supplementaryData1 != null && supplementaryData1.getEnvlp() != null &&
        supplementaryData1.getEnvlp().getCnts() != null &&
        supplementaryData1.getEnvlp().getCnts().getAdjstCtgy() != null) {
      String adjCategory = supplementaryData1.getEnvlp().getCnts().getAdjstCtgy().toString();
      Double adjRate = null;
      Double adjAmt = null;

		if (supplementaryData1.getEnvlp().getCnts().getAdjstRt() != null) {
			adjRate = supplementaryData1.getEnvlp().getCnts().getAdjstRt().doubleValue();
		}

		if (supplementaryData1.getEnvlp().getCnts().getAdjstAmt() != null &&
				supplementaryData1.getEnvlp().getCnts().getAdjstAmt().getValue() != null) {
			adjAmt = supplementaryData1.getEnvlp().getCnts().getAdjstAmt().getValue().doubleValue();
		}

      //Check Currency
      if (supplementaryData1.getEnvlp() != null &&
          supplementaryData1.getEnvlp().getCnts() != null &&
          supplementaryData1.getEnvlp().getCnts().getAdjstAmt() != null &&
          supplementaryData1.getEnvlp().getCnts().getAdjstAmt().getCcy() != null) {
        if (validateCurrency(supplementaryData1.getEnvlp().getCnts().getAdjstAmt().getCcy())) {
          mandateSeverity = mandateSeverity + 0;
          log.debug("******************validateAdjAmtCurr_010.075 - Passed.******************");
        } else {
          generateStatusErrorDetailsList("901198", mndReqTrnId, txnErrorType);
          mandateSeverity++;
          log.info("******************validateAdjAmtCurr_010.075 - Failed.******************");
        }
      }
//			valEndTime = System.nanoTime();
//			valDur = (valEndTime - valStartTime) / 1000000;
//			if(valDur > 0)
//				log.info("validategetAdjstRate_036 Duration "+DurationFormatUtils.formatDuration
//				(valDur, "HH:mm:ss.S")+" milliseconds. ");
//			valStartTime = System.nanoTime();

      //Rule 010.069 Only if equal to N remove R
      //Rule 010.069 uncommented by DimakatsoN for SRC258(CHG-191424)
      if (adjCategory.equalsIgnoreCase("N") || adjCategory.equalsIgnoreCase("R")) {
        //if(adjRate != null || adjAmt != null)
        if ((adjAmt != null && adjAmt != 0)) {
          generateStatusErrorDetailsList("901190", mndReqTrnId, txnErrorType);
          mandateSeverity++;
          log.info("******************validategetAdjstAmt_069 - Failed.******************");
        } else {
          mandateSeverity = mandateSeverity + 0;
          log.debug("******************validategetAdjstAmt_036 - Passed******************");
        }
      } else {
        if (adjRate != null && adjAmt != null) {
          generateStatusErrorDetailsList("901190", mndReqTrnId, txnErrorType);
          mandateSeverity++;
          log.info("******************validategetAdjstAmt_037 - Failed.******************");
        } else {
          // If Adjustment Amount is populated, then adjustment rate must NOT be populated.
          if (adjAmt != null) {
            //Adjustment Amount must NOT be populated.
            if (adjRate == null) {
              mandateSeverity = mandateSeverity + 0;
              log.debug("******************validategetAdjstAmt_037 - Passed******************");
            } else {
              generateStatusErrorDetailsList("901190", mndReqTrnId, txnErrorType);
              mandateSeverity++;
              log.info("******************validategetAdjstAmt_037 - Failed.******************");
            }
          } else {
            // If Adjustment Amount is NOT populated, adjustment rate MUST be populated
            if (adjRate != null) {
              mandateSeverity = mandateSeverity + 0;
              log.debug("******************validategetAdjstAmt_037 - Passed******************");
            } else {
              generateStatusErrorDetailsList("901190", mndReqTrnId, txnErrorType);
              mandateSeverity++;
              log.info("******************validategetAdjstAmt_037 - Failed.******************");
            }
          }
        }
      }
//			valEndTime = System.nanoTime();
//			valDur = (valEndTime - valStartTime) / 1000000;
//			if(valDur > 0)
//				log.info("validategetAdjstAmt_037 Duration "+DurationFormatUtils.formatDuration
//				(valDur, "HH:mm:ss.S")+" milliseconds. ");
//			valStartTime = System.nanoTime();
    }

    //____________________________Validate MandateRefNo_010
    // .099_____________________________________//
    if (supplementaryData1 != null && supplementaryData1.getEnvlp() != null &&
        supplementaryData1.getEnvlp().getCnts() != null &&
        supplementaryData1.getEnvlp().getCnts().getMndtRfNbr() != null) {
      mandateRefNo = supplementaryData1.getEnvlp().getCnts().getMndtRfNbr();

      log.debug("***************mandateRefNo" + mandateRefNo);
      //log.info("***************mandateRefNumber"+mandateRefNumber);

      if (validateRule999(supplementaryData1.getEnvlp().getCnts().getMndtRfNbr())) {
        log.debug("******************validateMndtRefNo_010.999 - Passed.******************");

        mandateSeverity = mandateSeverity + 0;
      } else {
        generateStatusErrorDetailsList("910099", mndReqTrnId, txnErrorType);
        mandateSeverity++;
        log.info("******************validateMndtRefNo_010.999 - Failed.******************");
      }
    } else {
      generateStatusErrorDetailsList("910099", mndReqTrnId, txnErrorType);
      mandateSeverity++;
      log.info("******************validateMndtRefNo_010.999 - Failed.******************");
    }

//		valEndTime = System.nanoTime();
//		valDur = (valEndTime - valStartTime) / 1000000;
//		if(valDur > 0)
//			log.info("validateMndtRefNo_010.999 Duration "+DurationFormatUtils.formatDuration
//			(valDur, "HH:mm:ss.S")+" milliseconds. ");
//		valStartTime = System.nanoTime();

    //____________________________Validate First Collection Amount_rule 010
    // .075/072______________________________________//
    if (supplementaryData1 != null && supplementaryData1.getEnvlp() != null &&
        supplementaryData1.getEnvlp().getCnts() != null &&
        supplementaryData1.getEnvlp().getCnts().getFrstColltnAmt() != null) {
      log.debug("supplementaryData1.getEnvlp().getCnts().getFrstColltnAmt(): " +
          supplementaryData1.getEnvlp().getCnts().getFrstColltnAmt().getValue());

      //Check Currency _rule 010.075
      if (validateCurrency(supplementaryData1.getEnvlp().getCnts().getFrstColltnAmt().getCcy())) {
        mandateSeverity = mandateSeverity + 0;
        log.debug("******************validateFrstCollAmtCurr_010.075 - Passed.******************");
      } else {
        generateStatusErrorDetailsList("901198", mndReqTrnId, txnErrorType);
        mandateSeverity++;
        log.info("******************validateFrstCollAmtCurr_010.075 - Failed.******************");
      }

      //Check relationship between first collection date / first collection date
      if (mandate.getOcrncs().getFrstColltnDt() != null) {
        mandateSeverity = mandateSeverity + 0;
        log.debug(
            "******************validateFrstCollDt/FrstCollAmtReltnshp_010.072- Passed" +
		  ".******************");
      } else {
        generateStatusErrorDetailsList("901195", mndReqTrnId, txnErrorType);
        mandateSeverity++;
        log.info(
            "******************validateFrstCollDt/FrstCollAmtReltnshp_010.072 - Failed" +
             ".******************");
      }
    }
    //This is optional field
//		valEndTime = System.nanoTime();
//		valDur = (valEndTime - valStartTime) / 1000000;
//		if(valDur > 0)
//			log.info("validateFrstCollDt/FrstCollAmtReltnshp_010.072 Duration
//			"+DurationFormatUtils.formatDuration(valDur, "HH:mm:ss.S")+" milliseconds. ");
//		valStartTime = System.nanoTime();

    //____________________________Validate Debit Value Type 010
    // .053______________________________________//
    if (supplementaryData1 != null && supplementaryData1.getEnvlp() != null &&
        supplementaryData1.getEnvlp().getCnts() != null &&
        supplementaryData1.getEnvlp().getCnts().getDbVlTp() != null) {
      log.debug("debt value type is *********************: " +
          supplementaryData1.getEnvlp().getCnts().getDbVlTp());
      if (!validateDebitValueType(supplementaryData1.getEnvlp().getCnts().getDbVlTp().toString())) {
        generateStatusErrorDetailsList("901119", mndReqTrnId, txnErrorType);
        mandateSeverity++;
        log.info("******************validateDebitValueType_032 - Failed.******************");
      } else {
        mandateSeverity = mandateSeverity + 0;
        log.debug("******************validateDebitValueType_032 - Passed******************");
      }
    }
    //This is optional in TRS 15
//		valEndTime = System.nanoTime();
//		valDur = (valEndTime - valStartTime) / 1000000;
//		if(valDur > 0)
//			log.info("validateDebitValueType_032 Duration "+DurationFormatUtils.formatDuration
//			(valDur, "HH:mm:ss.S")+" milliseconds. ");
//		valStartTime = System.nanoTime();


    if (mandateSeverity != 0) {
      log.info("Mandate " + mndReqTrnId + " has failed validation");
      saveStatusErrorDetails();
      saveTxnBillReportData();
    }

    //		if(mandateSeverity == 0)
    //		{
    //			log.debug("Mandate: +"+mndReqTrnId+" has passed validation.......");
    //		}
    //		else
    //		{
    //			log.info("Mandate "+mndReqTrnId+" has failed validation");
    //			saveStatusErrorDetails();
    //			saveErrorCodesReportData();
    //		}

    return mandateSeverity;
  }

  public void validateMandateRefNumber59_60_61(String id) {
    long valStartTime, valEndTime, valDur;
    valStartTime = System.nanoTime();
    //____________________________Validate Mandate Request Transaction
    // Id_Rule010_059_____________________________________//
    String trimId = id.trim();

    if (trimId != null || trimId.isEmpty() || trimId.length() == 0) {
      log.debug("******************validateManReqTranId_059 - Passed.******************");
      mandateSeverity = mandateSeverity + 0;
    } else {
      generateStatusErrorDetailsList("901163", mndReqTrnId, txnErrorType);
      mandateSeverity++;
      log.info("******************validateManReqTranId_059 - Failed.******************");
    }
//		valEndTime = System.nanoTime();
//		valDur = (valEndTime - valStartTime) / 1000000;
//		if(valDur > 0)
//			log.info("validateManReqTranId_051 "+DurationFormatUtils.formatDuration(valDur,
//			"HH:mm:ss.S")+" milliseconds. ");
//		valStartTime = System.nanoTime();

    //____________________________Validate Mandate Request Transaction Id- Bank
    // Number_Rule010_060_____________________________________//

    if (trimId != null) {
      if (trimId.length() >= 23) {
        String bank = trimId.substring(0, 4);
        log.debug("bank: " + bank);

        log.debug("if(!validateBankNo(bank)) ====>" + validateBankNo(bank));

        if (!validateBankNo(bank)) {
          generateStatusErrorDetailsList("901164", mndReqTrnId, txnErrorType);
          mandateSeverity++;
          log.info("******************validateBankNo_060 - Failed.******************");
        } else {
          log.debug("******************validateBankNo_060 - Passed.******************");
          mandateSeverity = mandateSeverity + 0;
        }
      } else {
        generateStatusErrorDetailsList("901164", mndReqTrnId, txnErrorType);
        mandateSeverity++;
        log.info("******************validateBankNo_060 - Failed.******************");
      }
    } else {
      generateStatusErrorDetailsList("901164", mndReqTrnId, txnErrorType);
      mandateSeverity++;
      log.info("******************validateManReqTranId_060 - Failed.******************");
    }
//		valEndTime = System.nanoTime();
//		valDur = (valEndTime - valStartTime) / 1000000;
//		if(valDur > 0)
//			log.info("validateBankNo_052 "+DurationFormatUtils.formatDuration(valDur, "HH:mm:ss
//			.S")+" milliseconds. ");
//		valStartTime = System.nanoTime();

    //____________________________Validate Mandate Request Transaction Id- Bank
    // Number_Rule010_060&061_____________________________________//
    if (id.trim() != null) {
      if (id.length() >= 23) {
        String bank = id.substring(0, 4);
        String origDate = id.substring(4, 14);


        log.debug("bank: " + bank);
        log.debug("origDate: " + origDate);

        if (!isDateValid(origDate, "yyyy-MM-dd")) {
          generateStatusErrorDetailsList("901165", mndReqTrnId, txnErrorType);
          mandateSeverity++;
          log.info("******************validateManReqTranDate_061 - Failed.******************");
        } else {
          log.debug("******************validateManReqTranDate_061 - Passed.******************");
          mandateSeverity = mandateSeverity + 0;
        }
      } else {
        generateStatusErrorDetailsList("901165", mndReqTrnId, txnErrorType);
        mandateSeverity++;
        log.info("******************validateManReqTranDate_061 - Failed.******************");
      }
    } else {
      generateStatusErrorDetailsList("901165", mndReqTrnId, txnErrorType);
      mandateSeverity++;
      log.info("******************validateManReqTranDate_061 - Failed.******************");
    }

//		valEndTime = System.nanoTime();
//		valDur = (valEndTime - valStartTime) / 1000000;
//		if(valDur > 0)
//			log.info("validateManReqTranDate_061 "+DurationFormatUtils.formatDuration(valDur,
//			"HH:mm:ss.S")+" milliseconds. ");
//		valStartTime = System.nanoTime();

    //SCR 253 ADDED 04/07/2019 Author by DimakatsoN
    // Match 3 --> Match InstgAgt to MRTI

    if (trimId != null) {
      if (id.length() >= 23) {
        String bank = id.substring(0, 4);

        String instgAgt = groupHdr.getInstgAgt().getFinInstnId().getClrSysMmbId().getMmbId();
        String memberNo = "21" + bank;
        boolean match3 = false;
        log.debug("memberNo : " + bank);
        log.debug("bank: " + memberNo);
        log.debug("instgAgt: " + instgAgt);

        if (!instgAgt.equalsIgnoreCase(memberNo)) {
          match3 = false;
          generateStatusErrorDetailsList("901020", mndReqTrnId, txnErrorType);
          mandateSeverity++;
          log.info("******************validateInstgAgtToMRTImemberNo - Failed.******************");
        } else {
          match3 = true;
          log.debug("******************validateInstgAgtToMRTImemberNo - Passed.******************");
          mandateSeverity = mandateSeverity + 0;
        }
      }
    }
//		valEndTime = System.nanoTime();
//		valDur = (valEndTime - valStartTime) / 1000000;
//		if(valDur > 0)
//			log.info("validateInstgAgtToMRTImemberNo "+DurationFormatUtils.formatDuration(valDur,
//			"HH:mm:ss.S")+" milliseconds. ");
//		valStartTime = System.nanoTime();

  }

  public String generateStatusMsgId(String instId) {
    int lastSeqNoUsed;

    SimpleDateFormat sdfFileDate = new SimpleDateFormat("yyyyMMdd");
    String achId, creationDate, fileSeqNo, msgId = null;
    String outgoingService = "ST200";

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

      //			creationDate = sdfFileDate.format(new Date());
      //TRS16 Processing Rules
      if (casSysctrlSysParamEntity != null && casSysctrlSysParamEntity.getProcessDate() != null) {
        creationDate = sdfFileDate.format(casSysctrlSysParamEntity.getProcessDate());
      } else {
        creationDate = sdfFileDate.format(new Date());
      }

      msgId = achId + "/" + outgoingService + "/" + "00" + instId + "/" + creationDate + "/" +
          fileSeqNo;
    } catch (Exception e) {
      log.error("**** Exception generateStatusMsgId**** : " + e);
      e.printStackTrace();
      e.getCause();
    }

    return msgId;
  }

  public CasCnfgErrorCodesEntity retrieveErrorCode(String errCode) {
    /*log.debug("valBeanRemote: "+ valBeanRemote);*/
    CasCnfgErrorCodesEntity casCnfgErrorCodesEntity =
        (CasCnfgErrorCodesEntity) valBeanRemote.retrieveErrorCode(errCode);
    log.debug("casCnfgErrorCodesEntity: " + casCnfgErrorCodesEntity);
    return casCnfgErrorCodesEntity;
  }

  public Date getCovertDateTime(XMLGregorianCalendar xmlGregorianCalendar) {

    Calendar cl = xmlGregorianCalendar.toGregorianCalendar();

    return cl.getTime();
  }

  public void generateStatusErrorDetailsList(String errorCode, String txnId, String errorType) {
    opsStatusDetailsEntity = new CasOpsStatusDetailsEntity();

    opsStatusDetailsEntity.setSystemSeqNo(new BigDecimal(123));
    opsStatusDetailsEntity.setErrorCode(errorCode);
    opsStatusDetailsEntity.setTxnId(txnId);
    opsStatusDetailsEntity.setTxnStatus("RJCT");
    opsStatusDetailsEntity.setErrorType(errorType);
    opsStatusDetailsEntity.setMandateRefNumber(mandateRefNo);
    log.debug("mandateRefNo---------->:" + opsStatusDetailsEntity.getMandateRefNumber());
    opsStatusDetailsEntity.setDebtorBranchNo(debtorBranchNo);
    log.debug("debtorBranchNo---------->:" + opsStatusDetailsEntity.getDebtorBranchNo());
    opsStatusDetailsEntity.setCrAbbShortName(abbShortName);
    log.debug("crAbbShortName---------->:" + opsStatusDetailsEntity.getCrAbbShortName());

    opsStatusDetailsList.add(opsStatusDetailsEntity);

    //Populate Txn Bill Report Data
    CasOpsTxnsBillReportEntity opsTxnsBillReportEntity =
        (CasOpsTxnsBillReportEntity) generateTxnsReportData(creditorBank, txnId, carinService,
            fileName);
    if (opsTxnsBillReportEntity != null) {
      opsTxnsBillReportList.add(opsTxnsBillReportEntity);
      log.info("*********In the opsTxnsBillReportList*******");
    }

  }

  public boolean saveStatusErrorDetails() {
    boolean generated = false;

    if (opsStatusDetailsList.size() > 0) {
      for (CasOpsStatusDetailsEntity localEntity : opsStatusDetailsList) {
        localEntity.setStatusHdrSeqNo(hdrSystemSeqNo);
      }

      generated = valBeanRemote.saveOpsStatusDetails(opsStatusDetailsList);
    }

    if (generated) {
      opsStatusDetailsList.clear();
    }
    return generated;
  }

  public boolean saveTxnBillReportData() {
    log.info("*********In the saveTxnBillReportData*******");
    boolean generated = false;

    if (opsTxnsBillReportList.size() > 0) {
      generated = beanRemote.saveOpsTxnBillInfo(opsTxnsBillReportList);
    }

    if (generated) {
      opsTxnsBillReportList.clear();
    }
    return generated;
  }

  public BigDecimal generateStatusReportGrpHdr(GroupHeader47 groupHeader, String groupStatus,
                                               String instgAgt) {
    opsStatusHdrsEntity = new CasOpsStatusHdrsEntity();
    opsStatusHdrsEntity.setSystemSeqNo(new BigDecimal(999999));
    opsStatusHdrsEntity.setHdrMsgId(outMsgId);
    opsStatusHdrsEntity.setCreateDateTime(getCovertDateTime(groupHeader.getCreDtTm()));
    opsStatusHdrsEntity.setInstgAgt(instgAgt);
	  if (groupHeader.getInstdAgt() != null && groupHeader.getInstdAgt().getFinInstnId() != null &&
			  groupHeader.getInstdAgt().getFinInstnId().getClrSysMmbId() != null
			  && groupHeader.getInstdAgt().getFinInstnId().getClrSysMmbId().getMmbId() != null) {
		  opsStatusHdrsEntity.setInstdAgt(
				  groupHeader.getInstdAgt().getFinInstnId().getClrSysMmbId().getMmbId());
	  }
    opsStatusHdrsEntity.setOrgnlMsgId(groupHeader.getMsgId());
    opsStatusHdrsEntity.setOrgnlMsgName("pain.010");
    opsStatusHdrsEntity.setOrgnlCreateDateTime(getCovertDateTime(groupHeader.getCreDtTm()));
	  if (groupStatus.equalsIgnoreCase("ACCP")) {
		  opsStatusHdrsEntity.setProcessStatus("1");
	  } else {
		  opsStatusHdrsEntity.setProcessStatus("6");
	  }
    opsStatusHdrsEntity.setGroupStatus(groupStatus);
    opsStatusHdrsEntity.setService("ST200");
    opsStatusHdrsEntity.setOrgnlFileName(fileName);

    hdrSystemSeqNo = valBeanRemote.saveOpsStatusHdrs(opsStatusHdrsEntity);
    return hdrSystemSeqNo;
  }
}
