package com.bsva.authcoll.singletable.validation;

import com.bsva.constants.Constants;
import com.bsva.entities.CasSysctrlCompParamEntity;
import com.bsva.entities.CasSysctrlSysParamEntity;
import com.bsva.entities.MdtAcConfigDataTimeEntity;
import com.bsva.entities.MdtAcOpsConfHdrsEntity;
import com.bsva.entities.MdtAcOpsFileSizeLimitEntity;
import com.bsva.entities.MdtAcOpsGrpHdrEntity;
import com.bsva.entities.MdtAcOpsMandateTxnsEntity;
import com.bsva.entities.MdtAcOpsTxnsBillReportEntity;
import com.bsva.entities.MdtCnfgAccountTypeEntity;
import com.bsva.entities.MdtCnfgAdjustmentCatEntity;
import com.bsva.entities.MdtCnfgAmendmentCodesEntity;
import com.bsva.entities.MdtCnfgAuthTypeEntity;
import com.bsva.entities.MdtCnfgCancellationCodesEntity;
import com.bsva.entities.MdtCnfgDebitValueTypeEntity;
import com.bsva.entities.MdtCnfgErrorCodesEntity;
import com.bsva.entities.MdtCnfgFrequencyCodesEntity;
import com.bsva.entities.MdtCnfgLocalInstrCodesEntity;
import com.bsva.entities.MdtCnfgReasonCodesEntity;
import com.bsva.entities.MdtCnfgRejectReasonCodesEntity;
import com.bsva.entities.MdtCnfgSequenceTypeEntity;
import com.bsva.entities.MdtOpsCustParamEntity;
import com.bsva.entities.MdtOpsFileRegEntity;
import com.bsva.entities.MdtOpsServicesEntity;
import com.bsva.entities.SysCisBankEntity;
import com.bsva.entities.SysCisBranchEntity;
import com.bsva.interfaces.AdminBeanRemote;
import com.bsva.interfaces.FileProcessBeanRemote;
import com.bsva.interfaces.ServiceBeanRemote;
import com.bsva.interfaces.ValidationBeanRemote;
import com.bsva.utils.Util;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.collections4.Predicate;
import org.apache.log4j.Logger;

/**
 * / * @author Saleha Saib
 *
 * @author SalehaR - 2018/12/31 - CHG-153240-Validate Msg Id Uniqueness on ST101/SPINP/SRINP
 * @author SalehaR - 2019/01/02 - CHG-153240-Validate Creation Date in Msg Id
 * @author SalehaR - 2019/09/11 - Alignment to Single Table
 * @author SalehaR - 2019/10/06 - Cache Static Data
 * @author SalehaR - 2019/10/17 File Size Limits Validation
 */

public class Validation_ST {
  public static AdminBeanRemote adminBeanRemote;
  public static ValidationBeanRemote valBeanRemote;
  public static ServiceBeanRemote beanRemote;
  public static FileProcessBeanRemote fileProcBeanRemote;
  String memberNo;
  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
  String backEndProcess = "BACKEND";
  String sadcSystem = "SADC";
  public String acSystem = "AC";
  public String sysUser = "MANOWNER";
  String branchmemberIdDebtorVal, branchmemberIdCreditorVal;
  public CasSysctrlSysParamEntity casSysctrlSysParamEntity;

  public static Logger log = Logger.getLogger("Validation_ST");

  //This must be reset at every file.
  public MdtAcOpsMandateTxnsEntity mdtAcMandateTxnsEntityOriginal = null;
  protected CasSysctrlCompParamEntity mdtSysctrlCompParamEntity = null;
  MdtAcOpsGrpHdrEntity opsGrpHdrEntity = null;
  MdtAcOpsMandateTxnsEntity mdtAcOpsMandateTxnsEntity = null;
  List<MdtAcOpsMandateTxnsEntity> mdtAcOpsMandateTxnList;
  String incomingMsgId = null;
  List<MdtCnfgLocalInstrCodesEntity> mdtCnfgLocalInstrCodesList;
  HashMap<String, String> listOfErrorCodes;
  List<MdtOpsServicesEntity> opsServicesList = new ArrayList<MdtOpsServicesEntity>();
  List<SysCisBankEntity> sysCisBankList = new ArrayList<SysCisBankEntity>();
  List<SysCisBranchEntity> sysCisBranchList = new ArrayList<SysCisBranchEntity>();
  List<MdtCnfgSequenceTypeEntity> cnfgSequenceTypeList = new ArrayList<MdtCnfgSequenceTypeEntity>();
  List<MdtCnfgFrequencyCodesEntity> cnfgFrequencyCodesList =
      new ArrayList<MdtCnfgFrequencyCodesEntity>();
  List<MdtCnfgAccountTypeEntity> cnfgAccountTypesList = new ArrayList<MdtCnfgAccountTypeEntity>();
  List<MdtCnfgAuthTypeEntity> cnfgAuthTypeList = new ArrayList<MdtCnfgAuthTypeEntity>();
  List<MdtCnfgAdjustmentCatEntity> cnfgAdjustmentCatList =
      new ArrayList<MdtCnfgAdjustmentCatEntity>();
  List<MdtCnfgDebitValueTypeEntity> cnfgDebitValueTypeList =
      new ArrayList<MdtCnfgDebitValueTypeEntity>();
  List<MdtCnfgReasonCodesEntity> cnfgReasonCodesList = new ArrayList<MdtCnfgReasonCodesEntity>();
  List<MdtCnfgAmendmentCodesEntity> cnfgAmendmentCodesList =
      new ArrayList<MdtCnfgAmendmentCodesEntity>();
  List<MdtCnfgCancellationCodesEntity> cnfgCancellationCodesList =
      new ArrayList<MdtCnfgCancellationCodesEntity>();
  List<MdtCnfgRejectReasonCodesEntity> cnfgRejectReasonCodesList =
      new ArrayList<MdtCnfgRejectReasonCodesEntity>();
  List<MdtCnfgErrorCodesEntity> cnfgErrorCodesList = new ArrayList<MdtCnfgErrorCodesEntity>();
  List<MdtAcOpsFileSizeLimitEntity> opsFileSizeLimitList =
      new ArrayList<MdtAcOpsFileSizeLimitEntity>();
  MdtAcConfigDataTimeEntity mdtAcOpsConfigDataTime = new MdtAcConfigDataTimeEntity();

  public Validation_ST() {
    contextAdminBeanCheck();
    contextValidationBeanCheck();
    contextCheck();
    contextFileProcBeanCheck();
    casSysctrlSysParamEntity =
        (CasSysctrlSysParamEntity) adminBeanRemote.retrieveActiveSysParameter();
    log.debug("mdtSysctrlSysParamEntity in validation: " + casSysctrlSysParamEntity);
    //
    //		Retrieve STATIC Data from Tables
    populateConfigTableData(false);

    //Retrieve STATIC Data from Tables
    populateErrorCodes();
  }

  public void populateConfigTableData(boolean sodBool) {
    mdtAcOpsConfigDataTime = (MdtAcConfigDataTimeEntity) beanRemote.retrievePastTimeForConfData();

    long databaseTime = 0;
    if (mdtAcOpsConfigDataTime == null) {
      databaseTime = System.currentTimeMillis();
    } else {
      databaseTime = mdtAcOpsConfigDataTime.getPastTimeInMill();
    }
    long anHourAgo = System.currentTimeMillis() - databaseTime;

    try {
      if (Constants.CONSTANT_MAP.isEmpty() || anHourAgo >= Constants.AN_HOUR || sodBool) {
        log.info("<<<<================Refreshing Config Table Data================>>>>");

        refreshConfigData();

        databaseTime = System.currentTimeMillis();
        mdtAcOpsConfigDataTime.setId("1");
        mdtAcOpsConfigDataTime.setPastTimeInMill(databaseTime);
        boolean saved = beanRemote.createOrUpdatePastTimeForConfData(mdtAcOpsConfigDataTime);

        if (saved) {
          log.info("Refresh Config time saved");
        } else {
          log.info("Error on saving config time.");
        }
      } else {
        log.info("<<<<================Retrieving Config From Map Data================>>>>");
        for (String key : Constants.CONSTANT_MAP.keySet()) {
          switch (key) {
            case "opsServicesData":
              opsServicesList = Constants.CONSTANT_MAP.get(key);
              break;
            case "cisBankData":
              sysCisBankList = Constants.CONSTANT_MAP.get(key);
              break;
            case "cisBranchData":
              sysCisBranchList = Constants.CONSTANT_MAP.get(key);
              break;
            case "sequenceTypeData":
              cnfgSequenceTypeList = Constants.CONSTANT_MAP.get(key);
              break;
            case "frequencyCodesData":
              cnfgFrequencyCodesList = Constants.CONSTANT_MAP.get(key);
              break;
            case "accountTypesData":
              cnfgAccountTypesList = Constants.CONSTANT_MAP.get(key);
              break;
            case "authTypeData":
              cnfgAuthTypeList = Constants.CONSTANT_MAP.get(key);
              break;
            case "adjustmentCatData":
              cnfgAdjustmentCatList = Constants.CONSTANT_MAP.get(key);
              break;
            case "debitValueTypeData":
              cnfgDebitValueTypeList = Constants.CONSTANT_MAP.get(key);
              break;
            case "reasonCodesData":
              cnfgReasonCodesList = Constants.CONSTANT_MAP.get(key);
              break;
            case "amendmentCodesData":
              cnfgAmendmentCodesList = Constants.CONSTANT_MAP.get(key);
              break;
            case "cancellationCodesData":
              cnfgCancellationCodesList = Constants.CONSTANT_MAP.get(key);
              break;
            case "rejectReasonCodesData":
              cnfgRejectReasonCodesList = Constants.CONSTANT_MAP.get(key);
              break;
            case "errorCodesData":
              cnfgErrorCodesList = Constants.CONSTANT_MAP.get(key);
              break;
            case "fileSizeLimitData":
              opsFileSizeLimitList = Constants.CONSTANT_MAP.get(key);
              break;
          }
        }
      }
    } catch (Exception ex) {
      System.out.println("Error loading config tables: " + ex.getMessage());
      ex.printStackTrace();
    }
  }

  private void refreshConfigData() {
    opsServicesList = (List<MdtOpsServicesEntity>) valBeanRemote.findAllOpsServices();
    Constants.CONSTANT_MAP.put("opsServicesData", opsServicesList);

    sysCisBankList = (List<SysCisBankEntity>) valBeanRemote.findAllCisBanks();
    Constants.CONSTANT_MAP.put("cisBankData", sysCisBankList);

    sysCisBranchList = (List<SysCisBranchEntity>) valBeanRemote.findAllCisBranches();
    Constants.CONSTANT_MAP.put("cisBranchData", sysCisBranchList);

    cnfgSequenceTypeList =
        (List<MdtCnfgSequenceTypeEntity>) valBeanRemote.findAllConfigSequenceTypes();
    Constants.CONSTANT_MAP.put("sequenceTypeData", cnfgSequenceTypeList);

    cnfgFrequencyCodesList =
        (List<MdtCnfgFrequencyCodesEntity>) valBeanRemote.findAllConfigFrequencyCodes();
    Constants.CONSTANT_MAP.put("frequencyCodesData", cnfgFrequencyCodesList);

    cnfgAccountTypesList =
        (List<MdtCnfgAccountTypeEntity>) valBeanRemote.findAllConfigAccountTypes();
    Constants.CONSTANT_MAP.put("accountTypesData", cnfgAccountTypesList);

    cnfgAuthTypeList = (List<MdtCnfgAuthTypeEntity>) valBeanRemote.findAllConfigAuthTypes();
    Constants.CONSTANT_MAP.put("authTypeData", cnfgAuthTypeList);

    cnfgAdjustmentCatList =
        (List<MdtCnfgAdjustmentCatEntity>) valBeanRemote.findAllConfigAdjustmentCats();
    Constants.CONSTANT_MAP.put("adjustmentCatData", cnfgAdjustmentCatList);

    cnfgDebitValueTypeList =
        (List<MdtCnfgDebitValueTypeEntity>) valBeanRemote.findAllConfigDebitValueTypes();
    Constants.CONSTANT_MAP.put("debitValueTypeData", cnfgDebitValueTypeList);

    cnfgReasonCodesList = (List<MdtCnfgReasonCodesEntity>) valBeanRemote.findAllConfigReasonCodes();
    Constants.CONSTANT_MAP.put("reasonCodesData", cnfgReasonCodesList);

    cnfgAmendmentCodesList =
        (List<MdtCnfgAmendmentCodesEntity>) valBeanRemote.findAllConfigAmendmentCodes();
    Constants.CONSTANT_MAP.put("amendmentCodesData", cnfgAmendmentCodesList);

    cnfgCancellationCodesList =
        (List<MdtCnfgCancellationCodesEntity>) valBeanRemote.findAllConfigCancellationCodes();
    Constants.CONSTANT_MAP.put("cancellationCodesData", cnfgCancellationCodesList);

    cnfgRejectReasonCodesList =
        (List<MdtCnfgRejectReasonCodesEntity>) valBeanRemote.findAllConfigRejectReasonCodes();
    Constants.CONSTANT_MAP.put("rejectReasonCodesData", cnfgRejectReasonCodesList);

    cnfgErrorCodesList = (List<MdtCnfgErrorCodesEntity>) valBeanRemote.findAllConfigErrorCodes();
    Constants.CONSTANT_MAP.put("errorCodesData", cnfgErrorCodesList);

    opsFileSizeLimitList = (List<MdtAcOpsFileSizeLimitEntity>) valBeanRemote.findAllFileSizeLimit();
    Constants.CONSTANT_MAP.put("fileSizeLimitData", opsFileSizeLimitList);
  }

  /*
   *//***
   *
   * Rule 010-000
   * Rule 011-000
   *//*
	public boolean validatePain010Matching( String mandateReqTranId)
	{
		if(mandateReqTranId != null && !mandateReqTranId.isEmpty())
		{
			mdtAcOpsMndtMsgList = new ArrayList<MdtAcOpsMndtMsgEntity>();
			 mdtAcOpsMndtMsgList = (List<MdtAcOpsMndtMsgEntity>)valBeanRemote
			 .retrieveAllOpsMndtMsg(mandateReqTranId);

			 log.debug("mdtAcOpsMndtMsgList.size(): "+ mdtAcOpsMndtMsgList.size());
			if(mdtAcOpsMndtMsgList.size()> 0)
			{
				for(MdtAcOpsMndtMsgEntity mdtAcOpsMndtMsgEntity :mdtAcOpsMndtMsgList)
				{

					if(mdtAcOpsMndtMsgEntity.getServiceId().equalsIgnoreCase("MANIN") && !
					(mdtAcOpsMndtMsgEntity.getProcessStatus().equalsIgnoreCase("4")) && !
					(mdtAcOpsMndtMsgEntity.getProcessStatus().equalsIgnoreCase("M")))
					return true;
					else

						if(mdtAcOpsMndtMsgEntity.getServiceId().equalsIgnoreCase("MANAC") && !
						(mdtAcOpsMndtMsgEntity.getServiceId().equalsIgnoreCase("4")))
						return true;

						else
							return false;
				}
		}
			return false;

		}
		return false;
		}
	  */

  /**
   * @param serviceName Rule 009_002
   *                    Rule 010_002
   *                    Rule manreq.002
   *                    Rule 012_002
   * @return
   */
  public boolean validateServiceId(String serviceName, String validService) {
	  if (serviceName != null && !serviceName.isEmpty()) {
		  MdtOpsServicesEntity opsServEntity = findOpsServices(serviceName);

		  if (opsServEntity == null) {
			  return false;
		  } else {
			  if (opsServEntity.getServiceIdIn().equalsIgnoreCase(validService)) {
				  return true;
			  } else {
				  return false;
			  }
		  }
		  //2019-10-06 SalehaR - Use Cached List
		  //			MdtOpsServicesEntity mdtOpsServicesEntity = (MdtOpsServicesEntity)
		  //			valBeanRemote.validateServiceId_002(serviceName);
		  //			if (mdtOpsServicesEntity == null)
		  //				return false;
		  //			else
		  //			{
		  //				if(mdtOpsServicesEntity.getServiceIdIn().equalsIgnoreCase(validService))
		  //					return true;
		  //				else
		  //					return false;
		  //			}
	  } else {
		  return false;
	  }
  }

  /**
   * @param bicCode Rule 009_003
   *                Rule 010_002
   *                Rule manreq.003
   *                Rule manreq.006
   *                Rule 012_003
   * @return
   */
  public boolean validateBicCode(String bicCode) {

	  if (bicCode != null && !bicCode.isEmpty()) {
		  SysCisBankEntity cisBankEntity = findCisBanks(bicCode);

		  if (cisBankEntity == null) {
			  return false;
		  } else {
			  memberNo = cisBankEntity.getMemberNo();
			  return true;
		  }

		  //			2019-10-06 SalehaR - Use Cached List
		  //			/* Get the correct Cis field for bic code */
		  //			SysCisBankEntity sysCisBankEntity = (SysCisBankEntity) valBeanRemote
		  //			.validateBicCode_003(bicCode,backEndProcess);
		  //			if (sysCisBankEntity == null)
		  //				return false;
		  //			else
		  //			{
		  //				memberNo = sysCisBankEntity.getMemberNo();
		  //				return true;
		  //			}
	  } else {
		  return false;
	  }
  }

  /**
   * @param fileNo
   * @param memberNo Rule 009_004
   *                 Rule 010_004
   *                 Rule manreq.005
   *                 Rule 012_004
   *                 File Numbering MUST be contiguous and sequential(eg: 0001,0002,0003)
   * @return
   */
  public String validateFileNumberingInMsgId(String fileNo, String memberNo, String msgType) {
    log.debug("fileNo: " + fileNo + "--------------------- bicCode: " + memberNo);
    if (fileNo != null && !fileNo.isEmpty() && memberNo != null && !memberNo.isEmpty()) {
      //MdtSysctrlCustParamEntity localEntity = (MdtSysctrlCustParamEntity) valBeanRemote
		// .validateFileNumberingInMsgId_004(memberNo);
      MdtOpsCustParamEntity localEntity =
          (MdtOpsCustParamEntity) valBeanRemote.validateFileNumberingInMsgId_004(memberNo);
      if (localEntity == null) {
        log.debug("Entity is null---------------");
        return "901004";
      } else {
        log.debug("custEntity in validate File Numbering " + localEntity);
        int msgIdLastFileNo = 0;
        if (msgType.equalsIgnoreCase("pain.009")) {
          msgIdLastFileNo = Integer.valueOf(localEntity.getManInitLstSeq());
          log.debug("msgIdLastFileNo: " + msgIdLastFileNo);
        }

        if (msgType.equalsIgnoreCase("pain.010")) {
          msgIdLastFileNo = Integer.valueOf(localEntity.getManAmdLstSeq());
          log.debug("msgIdLastFileNo: " + msgIdLastFileNo);
        }

        if (msgType.equalsIgnoreCase("pain.011")) {
          msgIdLastFileNo = Integer.valueOf(localEntity.getManCanLstSeq());
          log.debug("msgIdLastFileNo: " + msgIdLastFileNo);
        }

        if (msgType.equalsIgnoreCase("mdte.001")) {
          msgIdLastFileNo = Integer.valueOf(localEntity.getManReqLstSeq());
          log.debug("msgIdLastFileNo: " + msgIdLastFileNo);
        }
        if (msgType.equalsIgnoreCase("pain.012")) {
          msgIdLastFileNo = Integer.valueOf(localEntity.getManAccpLstSeq());
          log.debug("msgIdLastFileNo: " + msgIdLastFileNo);
        }
        if (msgType.equalsIgnoreCase("pacs.002")) {
          msgIdLastFileNo = Integer.valueOf(localEntity.getManConfirmLstSeq());
          log.debug("msgIdLastFileNo: " + msgIdLastFileNo);
        }
        if (msgType.equalsIgnoreCase("mdte.002")) {
          msgIdLastFileNo = Integer.valueOf(localEntity.getManRespLstSeq());
          log.debug("msgIdLastFileNo: " + msgIdLastFileNo);
        }

        int currentFileNo = Integer.valueOf(fileNo);
        log.debug("currentFileNo: " + currentFileNo);
        int newFileNo = msgIdLastFileNo + 1;
        log.debug("newFileNo: " + newFileNo);
        log.debug("msgIdLastFileNo: " + msgIdLastFileNo);

        if (msgIdLastFileNo == 000000 || msgIdLastFileNo == 999999) {
          log.debug("In the 901003 method==================");
          if (currentFileNo == 000001) {
            //						msgIdLastFileNo = ++msgIdLastFileNo;
            String lastSeqNr = String.format("%06d", newFileNo);
			  if (msgType.equalsIgnoreCase("pain.009")) {
				  localEntity.setManInitLstSeq(lastSeqNr);
			  } else if (msgType.equalsIgnoreCase("pain.010")) {
				  localEntity.setManAmdLstSeq(lastSeqNr);
			  } else if (msgType.equalsIgnoreCase("pain.011")) {
				  localEntity.setManCanLstSeq(lastSeqNr);
			  } else if (msgType.equalsIgnoreCase("mdte.001")) {
				  localEntity.setManReqLstSeq(lastSeqNr);
			  } else if (msgType.equalsIgnoreCase("pain.012")) {
				  localEntity.setManAccpLstSeq(lastSeqNr);
			  } else if (msgType.equalsIgnoreCase("pacs.002")) {
				  localEntity.setManConfirmLstSeq(lastSeqNr);
			  } else if (msgType.equalsIgnoreCase("mdte.002")) {
				  localEntity.setManRespLstSeq(lastSeqNr);
			  }

            log.debug("custEntity from Validation of Input SeqNo before update: " + localEntity);
            boolean updateLastSeqNr =
                valBeanRemote.updateMsgLastFileSeqNr(localEntity, backEndProcess);
            return "PASSED";
          } else {
            return "901003";
          }
        } else {
          log.debug("In the 901004 method==================");
          if (currentFileNo == newFileNo) {
            //						msgIdLastFileNo = ++msgIdLastFileNo;
            String lastSeqNr = String.format("%06d", newFileNo);

			  if (msgType.equalsIgnoreCase("pain.009")) {
				  localEntity.setManInitLstSeq(lastSeqNr);
			  } else if (msgType.equalsIgnoreCase("pain.010")) {
				  localEntity.setManAmdLstSeq(lastSeqNr);
			  } else if (msgType.equalsIgnoreCase("pain.011")) {
				  localEntity.setManCanLstSeq(lastSeqNr);
			  } else if (msgType.equalsIgnoreCase("mdte.001")) {
				  localEntity.setManReqLstSeq(lastSeqNr);
			  } else if (msgType.equalsIgnoreCase("pain.012")) {
				  localEntity.setManAccpLstSeq(lastSeqNr);
			  } else if (msgType.equalsIgnoreCase("pacs.002")) {
				  localEntity.setManConfirmLstSeq(lastSeqNr);
			  } else if (msgType.equalsIgnoreCase("mdte.002")) {
				  localEntity.setManRespLstSeq(lastSeqNr);
			  }


            log.debug("custEntity from Validation of Input SeqNo before update: " + localEntity);
            boolean updateLastSeqNr =
                valBeanRemote.updateMsgLastFileSeqNr(localEntity, backEndProcess);
            return "PASSED";
          } else {
            return "901004";
          }
        }
      }
    } else {
      log.debug("fileNo || memberNO is empty==================");
      return "901004";
    }
  }

  /**
   * @param msgId Rule 009_006
   *              Rule 010_006
   *              Rule 012_006
   * @return
   */
  public boolean validateMsgId(String msgId) {
    if (msgId != null && !msgId.isEmpty()) {
      log.debug("The File Msg id is ############" + msgId);

      log.debug("###################################################################");
      log.debug("mdtSysctrlSysParamEntity.getSysType() : " +
          casSysctrlSysParamEntity.getSysType());
      String sysTypeTmp = casSysctrlSysParamEntity.getSysType().trim();
      log.debug("mdtSysctrlSysParamEntity.getSysType().length is : " + sysTypeTmp.length());
      log.debug("acSystem :" + acSystem);
      log.debug("###################################################################");

      List<MdtAcOpsMandateTxnsEntity> mandateTxnList =
          (List<MdtAcOpsMandateTxnsEntity>) fileProcBeanRemote.
              validateOriginalMsgId(msgId);
      log.debug("the message id is *******" + msgId);

      //				log.debug("the list has the following info"+mandateTxnList.toString());

      if (mandateTxnList != null & mandateTxnList.size() > 0) {
        return false;
      } else {
        return true;
      }

    } else {
      return false;
    }
  }

  /**
   * @param creationDate
   * @param dateFormat   Applies to rules: CreationDateTime_Rule009_007
   *                     CreationDateTime_Rule009_008
   *                     Mandate Initiate Date_Rule009_015
   *                     Mandate TO Date_Rule009_016
   *                     Mandate First CollectionDate_Rule009_017
   *                     Mandate Final Collection Date_Rule009_019
   *                     Mandate Request Transaction Date_Rule009_053
   *                     __________________________________________________
   *                     Amend Rule 010_007
   *                     Amend Rule 010_008
   *                     Accept Rule 012_006
   *                     Accept Rule 012_008
   * @return
   */
  public boolean validateDate(String creationDate, String dateFormat) {
    if (creationDate == null) {
      return false;
    } else {
      SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
      try {
        Date crDate = sdf.parse(creationDate);
        Date processingDate;
		  if (casSysctrlSysParamEntity != null) {
			  processingDate = casSysctrlSysParamEntity.getProcessDate();
		  } else {
			  processingDate = new Date();
		  }


        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        String strCrDate = sdf1.format(crDate);
        String strPrDate = sdf1.format(processingDate);

        Date convCrDate = sdf1.parse(strCrDate);
        Date convPrDate = sdf1.parse(strPrDate);

        log.debug("convCrDate: " + convCrDate);
        log.debug("convPrDate: " + convPrDate);

		  if (convCrDate.after(convPrDate)) {
			  return false;
		  } else {
			  return true;
		  }
      } catch (ParseException pe) {
        return false;
      }
    }
  }

  /**
   * @param memberNo Validation Rule applies to:
   *                 Rule 009_010 Validate Instructing Agent
   *                 Rule 009_029 Validate Debtor Agent
   *                 Rule 012_010 Validate Instructing Agent
   *                 Rule manreq.006
   * @return
   */
  public boolean validateMemberNo(String memberNo) {
    log.debug("memberNo-->: " + memberNo);
	  if (memberNo != null && !memberNo.isEmpty()) {
		  SysCisBankEntity sysCisBankEntity = findCisBanks(memberNo);

		  if (sysCisBankEntity == null) {
			  return false;
		  } else {
			  if (sysCisBankEntity.getAcCreditor().equalsIgnoreCase("Y")) {
				  return true;
			  } else {
				  return false;
			  }
		  }
		  //			2019-10-06 SalehaR - Use Cached List
		  //			SysCisBankEntity sysCisBankEntity = (SysCisBankEntity) valBeanRemote
		  //			.validateCreditorBank(memberNo);
		  //			if (sysCisBankEntity == null)
		  //				return false;
		  //			else
		  //				return true;
	  } else {
		  return false;
	  }
  }

  /**
   * @param trackingInd Rule 009_011
   * @return
   */
  public boolean validateTrackingIndicator(String trackingInd) {
	  if (trackingInd != null && !trackingInd.isEmpty()) {
		  String trimTrackingInd = trackingInd.trim();
		  if (trimTrackingInd.equalsIgnoreCase("T") || trimTrackingInd.equalsIgnoreCase("F")) {
			  return true;
		  } else {
			  return false;
		  }
	  } else {
		  return false;
	  }
  }

  /**
   * @param reasonCode Rule 012_012
   * @return
   */
  public boolean validateRejectReasonCode(String reasonCode) {
	  if (reasonCode != null && !reasonCode.isEmpty()) {
		  String trimmedReasonCode = reasonCode.trim();

		  MdtCnfgReasonCodesEntity reasonCodesEntity = findReasonCodes(trimmedReasonCode);
		  if (reasonCodesEntity == null) {
			  return false;
		  } else {
			  return true;
		  }
		  //			2019-10-06 SalehaR - Use Cached List
		  //			MdtCnfgReasonCodesEntity localEntity = (MdtCnfgReasonCodesEntity)
		  //			valBeanRemote.validateRejectReasonCode(trimmedReasonCode);
		  //			if (localEntity != null)
		  //			{
		  //				if(localEntity.getActiveInd().equalsIgnoreCase("Y"))
		  //					return true;
		  //				else
		  //					return false;
		  //			}
		  //			else
		  //				return false;
	  } else {
		  return false;
	  }
  }

  /**
   * @param amendReason Reason Code
   *                    Rule 010_052
   * @return
   */
  public boolean validateAmendReasonCode(String amendReason) {
    String trimAmendReason = amendReason.trim();

	  if (trimAmendReason != null && !trimAmendReason.isEmpty() && trimAmendReason.length() != 0) {
		  MdtCnfgAmendmentCodesEntity amendCodesEntity = findAmendmentCodes(trimAmendReason);
		  if (amendCodesEntity == null) {
			  return false;
		  } else {
			  return true;
		  }
		  //			2019-10-06 SalehaR - Use Cached List
		  //			MdtCnfgAmendmentCodesEntity amendCodesEntity = (MdtCnfgAmendmentCodesEntity)
		  //			valBeanRemote.validateAmendReasonCode(trimAmendReason);
		  //			if(amendCodesEntity != null)
		  //			{
		  //				if(amendCodesEntity.getActiveInd().equalsIgnoreCase("Y"))
		  //					return true;
		  //				else
		  //					return false;
		  //			}
		  //			else
		  //				return false;
	  } else {
		  return false;
	  }
  }


  public boolean validateDebtorAuthCode(String authCode, String service) {
    log.debug("authCode: " + authCode);
	  if (authCode != null && !authCode.isEmpty()) {
		  authCode.trim();

		  if (casSysctrlSysParamEntity != null &&
				  casSysctrlSysParamEntity.getSysName().equalsIgnoreCase(sadcSystem)) {
			  if (authCode.equalsIgnoreCase("0227") || authCode.equalsIgnoreCase("0228") ||
					  authCode.equalsIgnoreCase("0230")) {
				  return true;
			  } else {
				  return false;
			  }
		  } else {

			  //Migration - 2018/08/27 //0999/0998
			  //if(authCode.equalsIgnoreCase("0226") ||authCode.equalsIgnoreCase("0227") ||authCode
			  // .equalsIgnoreCase("0228") || authCode.equalsIgnoreCase("0229")|| authCode
			  // .equalsIgnoreCase("0230"))
			  //0226 is for Pain.010 only
			  //0229/0230 is for Real Time
			  if (service.equalsIgnoreCase("MANIN")) {
				  if (authCode.equalsIgnoreCase("0227") || authCode.equalsIgnoreCase("0228") ||
						  authCode.equalsIgnoreCase("0999") || authCode.equalsIgnoreCase("0998") ||
						  authCode.equalsIgnoreCase("0997")) {
					  return true;
				  } else {
					  return false;
				  }
			  } else {
				  if (service.equalsIgnoreCase("MANAM")) {
					  if (authCode.equalsIgnoreCase("0226") || authCode.equalsIgnoreCase("0227") ||
							  authCode.equalsIgnoreCase("0228")) {
						  return true;
					  } else {
						  return false;
					  }
				  } else {
					  if (service.equalsIgnoreCase("MANRT")) {
						  if (authCode.equalsIgnoreCase("0226") ||
								  authCode.equalsIgnoreCase("0227") ||
								  authCode.equalsIgnoreCase("0228")
								  || authCode.equalsIgnoreCase("0229") ||
								  authCode.equalsIgnoreCase("0230")) {
							  return true;
						  } else {
							  return false;
						  }
					  } else {
						  return false;
					  }
				  }
			  }
		  }
	  } else {
		  return false;
	  }
  }


  /**
   * @param seqCode Rule 009_013
   *                Rule 012_016
   * @return
   */
  public boolean validateInstallmentOccurrence(String seqCode) {
	  if (seqCode != null && !seqCode.isEmpty()) {
		  String trimmedSeqCode = seqCode.trim();

		  MdtCnfgSequenceTypeEntity seqTypeEntity = findSequenceTypes(seqCode);
		  if (seqTypeEntity == null) {
			  return false;
		  } else {
			  return true;
		  }

		  //			2019-10-06 SalehaR - Use Cached List
		  //			MdtCnfgSequenceTypeEntity localEntity = (MdtCnfgSequenceTypeEntity)
		  //			valBeanRemote.validateInstallmentOccurrence_013(trimmedSeqCode);
		  //
		  //			if (localEntity != null)
		  //			{
		  //				if(localEntity.getActiveInd().equalsIgnoreCase("Y"))
		  //					return true;
		  //				else
		  //					return false;
		  //			}
		  //			else
		  //				return false;
	  } else {
		  return false;
	  }
  }

  /**
   * @param freqCode Rule 009_014
   *                 Rule 010_014
   * @return
   */
  public boolean validateInstallmentFrequency(String freqCode) {
	  if (freqCode != null && !freqCode.isEmpty()) {
		  String trimmedFreqCode = freqCode.trim();
		  MdtCnfgFrequencyCodesEntity frequencyCodeEntity = findFrequencyCodes(freqCode);

		  if (frequencyCodeEntity != null) {
			  return true;
		  } else {
			  return false;
		  }

		  //			2019-10-06 SalehaR - Use Cached List
		  //			MdtCnfgFrequencyCodesEntity localEntity = (MdtCnfgFrequencyCodesEntity)
		  //			valBeanRemote.validateInstallmentFrequency_014(trimmedFreqCode);
		  //
		  //			if (localEntity != null)
		  //				return true;
		  //			else
		  //				return false;
	  } else {
		  return false;
	  }
  }

  /**
   * @param dateToBeVal Applies to rules:
   *                    First Collection Date not in future_Rule009_018
   *                    Final Collection Date not in future_Rule009_035
   *                    __________________________________________________
   *                    Rules 010_043
   * @return
   */
  public boolean validateIfISFutureDate(Date dateToBeVal) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Date procDate;
    try {
      String formatDate = sdf.format(dateToBeVal);
      Date formatteDateToBeVal = sdf.parse(formatDate);

		if (casSysctrlSysParamEntity != null) {
			procDate = casSysctrlSysParamEntity.getProcessDate();
		} else {
			procDate = new Date();
		}


      String formatCurrDate = sdf.format(procDate);
      Date currentDate = sdf.parse(formatCurrDate);
      log.debug("validDate: " + formatDate);
		if (formatteDateToBeVal.after(currentDate)) {
			return true;
		} else {
			return false;
		}
    } catch (ParseException pe) {
      return false;
    }
  }

  /**
   * @param amount Rule 009_020
   *               Rule 010_020
   * @return
   */
  public boolean validateInstallmentAmount(Double amount) {
    log.debug("amount: " + amount);
	  if (amount != null) {
		  if (amount > 0) {
			  return true;
		  } else {
			  return false;
		  }
	  } else {
		  return false;
	  }
  }

  /**
   * @param amount Rule 009_021
   *               Rule 010_021
   * @return
   */
  public boolean validateInitialAmount(String amount) {
    try {
      Double amt = Double.parseDouble(amount);

		if (amt > 0) {
			return true;
		} else {
			return false;
		}
    } catch (NumberFormatException nfex) {
      return false;
    }
  }

  /**
   * @param finalAmt
   * @param debitValueType Rule 009_022
   *                       Rule 010_022
   * @return
   */
  public boolean validateFinalCollAmount(Integer finalAmt, String debitValueType) {
	  if (debitValueType != null && !debitValueType.isEmpty()) {
		  if (debitValueType.equals("FIXED")) {
			  if (finalAmt > 0) {
				  return true;
			  } else {
				  return false;
			  }
		  } else {
			  return true;
		  }
	  } else {
		  return false;
	  }
  }


  /**
   * @param noOfInstallments
   * @param debitValueType   Rule 009_023
   *                         Rule 010_023
   * @return
   */
  public boolean validateNumberOfInstallments(Integer noOfInstallments, String debitValueType) {
	  if (debitValueType != null && !debitValueType.isEmpty()) {
		  if (debitValueType.equals("FIXED")) {
			  if (noOfInstallments > 0) {
				  return true;
			  } else {
				  return false;
			  }
		  } else {
			  return true;
		  }
	  } else {
		  return false;
	  }
  }


  /**
   * @param maxAmount
   * @param instAmt   Rule 009_048
   * @return
   */

  public boolean validateMaximumAmount(String maxAmount, String instAmt) {
    //		2018-06-14 SALEHAR -
    //		DecimalFormat df = new DecimalFormat("#################0.00;-#");

	  if (maxAmount != null && instAmt != null) {
		  BigDecimal rate = new BigDecimal("1.5");
		  BigDecimal installmentAmt = new BigDecimal(instAmt);
		  BigDecimal maxAmt = new BigDecimal(maxAmount);

		  BigDecimal maximumAttainableAmt = installmentAmt.multiply(rate);
		  BigDecimal roundedValue = maximumAttainableAmt.setScale(2, BigDecimal.ROUND_HALF_UP);


		  //			log.debug("*********rate----------->"+rate);
		  //			log.debug("*********installmentAmt----------->"+installmentAmt);
		  //			log.debug("*********maxAmt----------->"+maxAmt);
		  //			log.debug("*********maximumAttainableAmt----------->"+maximumAttainableAmt);
		  //			log.debug("*********roundedValue----------->"+roundedValue);

		  if (maxAmt.compareTo(roundedValue) <= 0) {
			  return true;
		  } else {
			  return false;
		  }
		  //Old Code - 2018-06-14 Removed by SALEHAR
		  //			String value = df.format(maximumAttainableAmt);
		  //			log.info("value ===> "+value);
		  //			BigDecimal dValue = new BigDecimal(value);
		  //
		  //			log.info("*********maxAmout----------->"+maxAmout);
		  //			log.info("*********installmentAmt----------->"+installmentAmt);
		  //			log.info("*********maximumAttainableAmt----------->"+maximumAttainableAmt);
		  //			log.info("*********dValue----------->"+dValue);
		  //
		  //			if(maxAmoutB.compareTo(dValue) <= 0)
		  //				return true;
		  //			else
		  //				return false;

	  } else {
		  return true;
	  }

  }

  /**
   * @param bicCode SADC Rule 009 _ 025
   *                SADC Rule 009_029
   *                SADC Rule 010 _ 025
   *                SADC Rule 010_029
   * @return
   */
  public boolean validateAgentExists_Sadc(String bicCode) {
    log.debug("bicCode: " + bicCode);
	  if (bicCode != null && !bicCode.isEmpty()) {
		  //log.debug("In the validateAgentExists_025_029_Sadc..before valRemote.....");
		  SysCisBankEntity sysCisBankEntity =
				  (SysCisBankEntity) valBeanRemote.validateBicCode_003(bicCode, backEndProcess);
		  log.debug("cisEntity: " + sysCisBankEntity);
		  if (sysCisBankEntity == null) {
			  return false;
		  } else {
			  return true;
		  }
	  } else {
		  return false;
	  }
  }


  /**
   * @param debtorId Rule 009_027
   *                 Rule 010_027
   * @return
   */
  public boolean validateDebtorIdentifier(String debtorId) {
    boolean findMatch;
    log.debug("debtorId: ---> " + debtorId);
    if (debtorId != null && !debtorId.isEmpty()) {
      String trimDebtorId = debtorId.trim();
      if (trimDebtorId == null || trimDebtorId.isEmpty() || trimDebtorId.length() == 0) {
        findMatch = false;
      } else {
        try {
          String format = "[P|I|T]{1}/[A-Za-z0-9]{1,33}";
          //                      String format = "[PIT]{1}/[A-Za-z0-9]{0,33}";
          Pattern pattern = Pattern.compile(format);
          Matcher matcher = pattern.matcher(trimDebtorId);
          findMatch = matcher.find();
        } catch (Exception e) {
          log.error("Exception in validateDebtorIdentifier : " + e);
          findMatch = false;
        }
      }
    } else {
      findMatch = false;
    }

    return findMatch;
  }


  /**
   * @param collectionDay Rule_009_033
   *                      Rule_010_033
   * @return
   */
  public boolean validateCollectionDay(String collectionDay, String frequenyCode) {
    Boolean checkCollDate = false;
    String trimmedCollDay = collectionDay.trim();
    log.debug("trimmedCollDay: " + trimmedCollDay);
    log.debug("frequenyCode: " + frequenyCode);

    if (!trimmedCollDay.isEmpty() && !frequenyCode.isEmpty()) {
      try {
        int collDay = Integer.valueOf(trimmedCollDay);

        if (frequenyCode.equalsIgnoreCase("WEEK")) {
			if (collDay >= 1 && collDay <= 7) {
				checkCollDate = true;
			} else {
				checkCollDate = false;
			}
        }


        if (frequenyCode.equalsIgnoreCase("ADHO")) {
			if ((collDay >= 1 && collDay <= 14) || collDay == 99) {
				checkCollDate = true;
			} else {
				checkCollDate = false;
			}
        }

        if (frequenyCode.equalsIgnoreCase("FRTN")) {
			if (collDay >= 1 && collDay <= 14) {
				checkCollDate = true;
			} else {
				checkCollDate = false;
			}
        }

        if (frequenyCode.equalsIgnoreCase("MNTH") || frequenyCode.equalsIgnoreCase("QURT") ||
            frequenyCode.equalsIgnoreCase("MIAN") || frequenyCode.equalsIgnoreCase("YEAR")) {
			if ((collDay >= 1 && collDay <= 30) || collDay == 99) {
				checkCollDate = true;
			} else {
				checkCollDate = false;
			}
        }
      } catch (NumberFormatException nfe) {
        //Do Error Handling Here
        checkCollDate = false;
      }

    } else {
      checkCollDate = false;
    }

    return checkCollDate;
  }

  /**
   * @param creditorName Rule_009_038
   *                     Rule_010_038
   * @return
   */
  public boolean validateCreditorName(String creditorName) {
    if (creditorName == null || creditorName.isEmpty()) {
      return false;
    } else {
      String trimCreditorName = creditorName.trim();
		if (trimCreditorName == null || trimCreditorName.isEmpty() ||
				trimCreditorName.length() == 0) {
			return false;
		} else {
			return true;
		}
    }
  }


  /**
   * @param ultCreditor
   * @param creditor    Rule_009_039
   *                    Rule_010_039
   * @return
   */
  public boolean validateUltimateCreditor(String ultCreditor, String creditor) {
    log.debug("ultCr: " + ultCreditor);
    log.debug("creditor: " + creditor);
	  if (ultCreditor != null && !ultCreditor.isEmpty() && creditor != null &&
			  !creditor.isEmpty()) {
		  String trimUltCdtr = ultCreditor.trim();
		  String trimCred = creditor.trim();

		  if ((trimUltCdtr == null || trimUltCdtr.isEmpty() || trimUltCdtr.length() == 0) &&
				  (trimCred == null || trimCred.isEmpty() || trimCred.length() == 0)) {
			  return false;
		  } else {
			  if (ultCreditor.equalsIgnoreCase(creditor)) {
				  return false;
			  } else {
				  return true;
			  }
		  }
	  } else {
		  return true;
	  }
  }

  /**
   * @param debtorName Rule_009_040
   *                   Rule_010_040
   * @return
   */
  public boolean validateDebtorName(String debtorName) {

	  if (debtorName != null && !debtorName.isEmpty()) {
		  String trimmedDname = debtorName.trim();
		  if (trimmedDname.trim() == null || trimmedDname.isEmpty() || trimmedDname.length() == 0) {
			  return false;
		  } else {
			  return true;
		  }
	  } else {
		  return false;
	  }
  }

  /**
   * @param ultDebtor
   * @param debtor    Rule_009_041
   * @return
   */
  public boolean validateUltimateDebtor(String ultDebtor, String debtor) {
    String trimmedUltDebt = ultDebtor.trim();
	  if (trimmedUltDebt == null || trimmedUltDebt.isEmpty() || trimmedUltDebt.length() == 0) {
		  return false;
	  } else {
		  if (ultDebtor.equalsIgnoreCase(debtor)) {
			  return false;
		  } else {
			  return true;
		  }
	  }
  }

  /**
   * @param contractRef Rule_009_042
   *                    Rule_010_011
   * @return
   */
  public boolean validateContractReference(String contractRef) {
	  if (contractRef != null && !contractRef.isEmpty()) {
		  String cntrRef = contractRef.trim();
		  if (cntrRef == null || cntrRef.isEmpty() || cntrRef.length() == 0) {
			  return false;
		  } else {
			  return true;
		  }
	  } else {
		  return false;
	  }
  }

  /**
   * @param dateToBeVal Rule 010_048
   * @return
   */
  public boolean validateIfNotPastDate(Date dateToBeVal, Date processingDate) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    try {
      String formatDate = sdf.format(dateToBeVal);
      Date formatteDateToBeVal = sdf.parse(formatDate);

      String formatCurrDate = sdf.format(processingDate);
      Date currentDate = sdf.parse(formatCurrDate);

		if (formatteDateToBeVal.before(currentDate) || formatteDateToBeVal.equals(currentDate)) {
			return false;
		} else {
			return true;
		}
    } catch (ParseException pe) {
      return false;
    }
  }


  /**
   * @param firstCollDate
   * @param loadDate      Rule_009_044
   *                      Rule_010_044
   * @return
   */
  public boolean validateFirstCollDateIsGreaterThan3Days(Date firstCollDate, Date loadDate) {
    Calendar cal = GregorianCalendar.getInstance();
    cal.setTime(loadDate);
    cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) + 3);
    java.util.Date threeDaysFromLoad = cal.getTime();

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    try {
      String firstcdate = sdf.format(firstCollDate);
      Date formattedFirstCollDate = sdf.parse(firstcdate);

      String threedate = sdf.format(threeDaysFromLoad);
      Date formatted3LoadedDate = sdf.parse(threedate);

      log.debug("First Coll Date: " + firstcdate);
      log.debug("Load Date: " + loadDate);
      log.debug("threedate: " + threedate);

		if (formattedFirstCollDate.before(formatted3LoadedDate)) {
			return false;
		} else {
			return true;
		}
    } catch (ParseException pe) {
      return false;
    }
  }


  //	/**
  //	 * @param origMandReqId
  //	 * @return
  //	 * Cancellation 011
  //	 */
  //	public boolean validateOriginalMandateExists(String origMandReqId)
  //	{
  //		if(origMandReqId != null && !origMandReqId.isEmpty())
  //		{
  //			originalMandateRegisterEntity = (MdtOpsMandateRegisterEntity) valBeanRemote
	//			.retrieveOriginalMandate(origMandReqId);
  //
  //			if(originalMandateRegisterEntity != null)
  //			{
  //				originalMandatePartyInfoList = (List<MdtOpsPartyIdentEntity>) valBeanRemote
	//				.retrieveOriginalPartyIdentification(originalMandateRegisterEntity
	//				.getMandateReqId());
  //				return true;
  //			}
  //			else
  //				return false;
  //		}
  //		else
  //			return false;
  //	}


  //	/**
  //	 * @param status
  //	 * Rule 010_047
  //	 * Rule 011_016
  //	 * Rule 012_013
  //	 * @return
  //	 */
  //	public boolean validateMandateStatus(String origMandReqId, String msgId)
  //	{
  //		if(origMandReqId != null && !origMandReqId.isEmpty())
  //		{
  //			if(mdtSysctrlSysParamEntity.getSysType().equalsIgnoreCase(sadcSystem))
  //			{
  //				log.debug("In the MdtOpsMandateRegisterEntity section.......====>>>>>");
  //				originalMandateRegisterEntity = (MdtOpsMandateRegisterEntity) valBeanRemote
	//				.retrieveOriginalMandate(origMandReqId);
  //				if(originalMandateRegisterEntity != null)
  //				{
  //					if(originalMandateRegisterEntity.getActiveInd().equalsIgnoreCase("Y"))
  //						return true;
  //					else
  //						return false;
  //				}
  //				else
  //					return false;
  //			}
  //			else
  //			{
  //				log.debug("In the MdtAcOpsMndtMsgEntity section.......====>>>>>");
  //				MdtAcOpsMndtMsgEntity mdtAcOpsMndtMsgEntity = (MdtAcOpsMndtMsgEntity)
	//				beanRemote.retrieveAcMandate(msgId, origMandReqId);
  //
  //				if(mdtAcOpsMndtMsgEntity != null)
  //				{
  //					if(mdtAcOpsMndtMsgEntity.getActiveInd().equalsIgnoreCase("Y"))
  //						return true;
  //					else
  //						return false;
  //				}
  //				else
  //					return false;
  //			}
  //
  //		}
  //		else
  //			return false;
  //	}
  //

  /**
   * @param amendFreq
   * @param origFreq  Rule 010_051
   * @return
   */
  public boolean validateAmendOrigFrequency(String amendFreq, String origFreq) {
	  if (amendFreq != null && !amendFreq.isEmpty() && origFreq != null && !origFreq.isEmpty()) {
		  if (amendFreq.equalsIgnoreCase(origFreq)) {
			  return true;
		  } else {
			  return false;
		  }
	  } else {
		  return false;
	  }
  }

  /**
   * @param amendFromDate
   * @param origFromdate  Rule 010_052
   * @return
   */
  public boolean validateAmendOrigFromDate(Date amendFromDate, Date origFromdate) {
    String amendFmDate = sdf.format(amendFromDate);
    String origFmDate = sdf.format(origFromdate);


	  if (amendFmDate != null && !amendFmDate.isEmpty() && origFmDate != null &&
			  !origFmDate.isEmpty()) {
		  if (amendFmDate.equalsIgnoreCase(origFmDate)) {
			  return true;
		  } else {
			  return false;
		  }
	  } else {
		  return false;
	  }
  }

  /**
   * @param amendToDate
   * @param origToDate  Rule 010_053
   * @return
   */
  public boolean validateAmendOrigToDate(Date amendToDate, Date origToDate) {
    String amdToDt = sdf.format(amendToDate);
    String orgToDt = sdf.format(origToDate);

	  if (amdToDt != null && !amdToDt.isEmpty() && orgToDt != null && !orgToDt.isEmpty()) {
		  if (amdToDt.equalsIgnoreCase(orgToDt)) {
			  return true;
		  } else {
			  return false;
		  }
	  } else {
		  return false;
	  }
  }

  /**
   * @param amendMaxAmt
   * @param origMaxAmt  Rule 010_058
   *                    Rule 012_022
   * @return
   */
  public boolean validateAmendOrigMaxAmt(int amendMaxAmt, int origMaxAmt) {
    log.debug("amendMaxAmt: " + amendMaxAmt);
    log.debug("origMaxAmt: " + origMaxAmt);
	  if (amendMaxAmt != 0 && origMaxAmt != 0) {
		  boolean s = amendMaxAmt == origMaxAmt;
		  log.debug("s: - " + s);
		  if (amendMaxAmt == origMaxAmt) {
			  return true;
		  } else {
			  return false;
		  }
	  } else {
		  return false;
	  }
  }

  /*
   *
   * @param msgId
   * @return true if the MsgId is valid
   */
  public boolean validateMsgIdStructure(String msgId) {
    Boolean findMatch = false;
    try {
      log.debug("msgId.length(): " + msgId.length());
      if (msgId.length() > 34) {
        findMatch = false;
      } else {
        //REMOVED String /[A-Za-z0-9]{1}
        String format = "[A-Za-z0-9]{3}/[A-Za-z0-9]{5}/[A-Za-z0-9]{8}/[0-9]{8}/[0-9]{1,6}$";

        Pattern pattern = Pattern.compile(format);
        Matcher matcher = pattern.matcher(msgId);

        //log.debug("return matcher.find();  ===> "+ matcher.find());
        findMatch = matcher.find();
        //return msgId.matches( "[A-Za-z0-9]{3}/[A-Za-z0-9]{5}/[A-Za-z0-9]{8,
		  // 11}/[A-Za-z0-9]{1}/[0-9]{8}/[0-9]{1,4}/[A-Za-z0-9]{1}" );
      }
    } catch (Exception e) {
      log.error("Exception in validateMsgId : " + e);
      findMatch = false;
    }

    return findMatch;
  }

  public String[] splitMsgId(String msgId) {
    String[] split = msgId.split("/");

    return split;

  }

  /**
   * @param firstDate * Rule_009_046
   *                  Rule 010_055
   * @param fromDate
   * @return
   */
  public boolean validateFirstDateIsBeforeFromDate(Date firstDate, Date fromDate) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    try {
      String strFirstDate = sdf.format(firstDate);
      Date formatFirstDate = sdf.parse(strFirstDate);

      String strFromDate = sdf.format(fromDate);
      Date formatFromDate = sdf.parse(strFromDate);
      log.debug("formatFirstDate: " + formatFirstDate);
      log.debug("formatFromDate: " + formatFromDate);

			/*boolean equals = formatFirstDate.equals(formatFromDate);
			log.debug("equals: "+equals);

			boolean after = formatFirstDate.after(formatFromDate);
			log.debug("after: "+after);*/

      if (formatFirstDate.equals(formatFromDate)) {
        log.debug("equals....returning true");
        return true;
      } else {
        if (formatFirstDate.after(formatFromDate)) {
          log.debug("date is after....returning true");
          return true;
        } else {
          log.debug("date is NOT EQUAL OR AFTER....returning FALSE");
          return false;
        }
      }
    } catch (ParseException pe) {
      log.error("ParseException: " + pe.getMessage());
      return false;
    }
  }


  /**
   * @param finalDate
   * @param fromDate
   * @param toDate    * Rule_009_047
   *                  rule 010_056
   * @return
   */
  public boolean validateFinalCollDateBetweenFromAndToDate(Date finalDate, Date fromDate,
                                                           Date toDate) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    try {
      String strFinalDate = sdf.format(finalDate);
      Date formatFinalDate = sdf.parse(strFinalDate);

      String strFromDate = sdf.format(fromDate);
      Date formatFromDate = sdf.parse(strFromDate);

      String strToDate = sdf.format(toDate);
      Date formatToDate = sdf.parse(strToDate);

		if ((finalDate.equals(toDate) || finalDate.before(toDate)) &&
				(finalDate.equals(formatFromDate) || finalDate.after(formatFromDate))) {
			return true;
		} else {
			return false;
		}
    } catch (ParseException pe) {
      return false;
    }
  }

  /**
   * @param collAmt
   * @param maxAmt  Rule 009_048
   *                Rule 010_057
   * @return
   */
  public boolean validateCollAmountIsLessThanMaxAmount(Double collAmt, Double maxAmt) {
	  if (collAmt != 0 && maxAmt != 0) {
		  if (collAmt <= maxAmt) {
			  return true;
		  } else {
			  return false;
		  }
	  } else {
		  return false;
	  }
  }

  /**
   * @param amendCtryCode
   * @param origCntryCode Rule 010_059
   * @return
   */
  public boolean validateAmendOrigCountryCode(String amendCtryCode, String origCntryCode,
                                              String party) {
    if (party == "CREDITOR" || party == "DEBTOR") {
		if (amendCtryCode != null && !amendCtryCode.isEmpty() && origCntryCode != null &&
				!origCntryCode.isEmpty()) {
			if (amendCtryCode.equalsIgnoreCase(origCntryCode)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
    } else {
		if (amendCtryCode != null && !amendCtryCode.isEmpty() && origCntryCode != null &&
				!origCntryCode.isEmpty()) {
			if (amendCtryCode.equalsIgnoreCase(origCntryCode)) {
				return true;
			} else {
				return false;
			}
		} else {
			return true;
		}
    }
  }


  /**
   * @param cancelReasonCode Rule 011_010
   * @return
   */
  public boolean validateCancelReasonCode(String cancelReasonCode) {
	  if (cancelReasonCode != null && !cancelReasonCode.isEmpty()) {
		  //			2019-10-06 SalehaR - Use Cached List
		  //			MdtCnfgCancellationCodesEntity localEntity = (MdtCnfgCancellationCodesEntity)
		  //			valBeanRemote.validateCancelReasonCode(reasonCode);
		  MdtCnfgCancellationCodesEntity localEntity = findCancellationCodes(cancelReasonCode);
		  if (localEntity != null) {
			  //2019-02-05 SalehaR - CHG-155429
			  if (localEntity.getCancellationCode().equalsIgnoreCase("MICN") ||
					  localEntity.getCancellationCode().equalsIgnoreCase("MACN")) {
				  return false;
			  } else {
				  return true;
			  }
		  } else {
			  return false;
		  }
	  } else {
		  return false;
	  }
  }

  /**
   * This is an AC MMM Rule 025 / 029
   *
   * @param branchCode
   * @return
   */
  public boolean validateDebtorBranchNo(String branchCode, String memberType) {
	  if (branchCode != null && !branchCode.isEmpty() && branchCode.length() == 6) {
		  //			SysCisBranchEntity sysCisBranchEntity = findCisBranches(branchCode);
		  SysCisBranchEntity sysCisBranchEntity = findDebtorCISBranches(branchCode);
		  if (sysCisBranchEntity == null) {
			  return false;
		  } else {
			  branchmemberIdDebtorVal = sysCisBranchEntity.getMemberNo();
			  return true;

			  //				if(sysCisBranchEntity.getAcDebtor().equalsIgnoreCase("Y")){
			  //					if(memberType.equalsIgnoreCase("Debtor"))
			  //						branchmemberIdDebtorVal = sysCisBranchEntity.getMemberNo();
			  //					else
			  //						branchmemberIdCreditorVal = sysCisBranchEntity.getMemberNo();
			  //					return true;
			  //				}else {
			  //					return false;
			  //				}
		  }

		  //			2019-10-06 SalehaR - Use Cached List
		  //			log.debug("branch code: "+branchCode);
		  //			SysCisBranchEntity sysCisBranchEntity = (SysCisBranchEntity) valBeanRemote
		  //			.validateDebtorBranchNo(branchCode,"Y");
		  //			log.debug("sysCisBranchEntity: "+sysCisBranchEntity);
		  //			if(sysCisBranchEntity == null)
		  //				return false;
		  //			else
		  //			{
		  //				if(memberType.equalsIgnoreCase("Debtor"))
		  //					branchmemberIdDebtorVal = sysCisBranchEntity.getMemberNo();
		  //				else
		  //					branchmemberIdCreditorVal = sysCisBranchEntity.getMemberNo();
		  //				return true;
		  //			}
	  } else {
		  return false;
	  }
  }

  public boolean validateCreditorBranchNo(String branchCode, String memberType) {
	  if (branchCode != null && !branchCode.isEmpty() && branchCode.length() == 6) {
		  //			SysCisBranchEntity sysCisBranchEntity = findCisBranches(branchCode);
		  SysCisBranchEntity sysCisBranchEntity = findCreditorCISBranches(branchCode);
		  if (sysCisBranchEntity == null) {
			  return false;
		  } else {
			  branchmemberIdCreditorVal = sysCisBranchEntity.getMemberNo();
			  return true;
			  //				if(sysCisBranchEntity.getAcCreditor().equalsIgnoreCase("Y")){
			  //					if(memberType.equalsIgnoreCase("Debtor"))
			  //						branchmemberIdDebtorVal = sysCisBranchEntity.getMemberNo();
			  //					else
			  //						branchmemberIdCreditorVal = sysCisBranchEntity.getMemberNo();
			  //					return true;
			  //				}else {
			  //					return false;
			  //				}
		  }
		  //			2019-10-06 SalehaR - Use Cached List
		  //			log.debug("branch code: "+branchCode);
		  //			SysCisBranchEntity sysCisBranchEntity = (SysCisBranchEntity) valBeanRemote
		  //			.validateCreditorBranchNo(branchCode,"Y");
		  //			log.debug("sysCisBranchEntity: "+sysCisBranchEntity);
		  //			if(sysCisBranchEntity == null)
		  //				return false;
		  //			else
		  //			{
		  //				if(memberType.equalsIgnoreCase("Debtor"))
		  //					branchmemberIdDebtorVal = sysCisBranchEntity.getMemberNo();
		  //				else
		  //					branchmemberIdCreditorVal = sysCisBranchEntity.getMemberNo();
		  //				return true;
		  //			}
	  } else {
		  return false;
	  }
  }

  public boolean validateDebitValueType(String debitValueType) {
	  if (debitValueType != null && !debitValueType.isEmpty()) {
		  log.debug("debitValueType: " + debitValueType);

		  if (debitValueType.equalsIgnoreCase("USAGE_BASED")) {
			  debitValueType = "USAGE BASED";
		  }

		  MdtCnfgDebitValueTypeEntity debitValueTypeEntity = findDebitValTypes(debitValueType);
		  if (debitValueTypeEntity == null) {
			  return false;
		  } else {
			  return true;
		  }

		  //			2019-10-06 SalehaR - Use Cached List
		  //			MdtCnfgDebitValueTypeEntity debitValueTypeEntity =
		  //			(MdtCnfgDebitValueTypeEntity) valBeanRemote.validateDebitValueType
		  //			(debitValueType);
		  //			log.debug("debitValueTypeEntity: "+debitValueTypeEntity);
		  //			if(debitValueTypeEntity == null)
		  //				return false;
		  //			else
		  //				return true;
	  } else {
		  return false;
	  }
  }

  public boolean validateDateAdjRuleInd(String adjRuleInd) {
    log.debug("adjRuleInd: " + adjRuleInd);
	  if (adjRuleInd != null && !adjRuleInd.isEmpty()) {
		  String trimAdjRuleInd = adjRuleInd.trim();
		  if (trimAdjRuleInd.equalsIgnoreCase("Y") || trimAdjRuleInd.equalsIgnoreCase("N")) {
			  return true;
		  } else {
			  return false;
		  }
	  } else {
		  return false;
	  }
  }


  /**
   * @param adjCategory
   * @return
   */
  public boolean validateAdjustCategory(String adjCategory) {
	  if (adjCategory != null) {
		  String trimmedAdjCat = adjCategory.trim();
		  log.debug("trimmedAdjCat: " + trimmedAdjCat);

		  MdtCnfgAdjustmentCatEntity adjustmentCatEntity = findAdjustmentCat(adjCategory);
		  if (adjustmentCatEntity == null) {
			  return false;
		  } else {
			  return true;
		  }

		  //			2019-10-06 SalehaR - Use Cached List
		  //			MdtCnfgAdjustmentCatEntity adjustmentCatEntity = (MdtCnfgAdjustmentCatEntity)
		  //			valBeanRemote.validateAdjCategory(trimmedAdjCat);
		  //			log.debug("adjustmentCatEntity: "+adjustmentCatEntity);
		  //			if(adjustmentCatEntity == null)
		  //				return false;
		  //			else
		  //				return true;
	  } else {
		  return false;
	  }
  }


  public boolean validatePacs002StatusCode(String statusCode) {
	  if (statusCode != null && !statusCode.isEmpty()) {
		  if (statusCode.equalsIgnoreCase("ACCP") || statusCode.equalsIgnoreCase("RJCT") ||
				  statusCode.equalsIgnoreCase("PART")) {
			  return true;
		  } else {
			  return false;
		  }
	  } else {
		  return false;
	  }
  }

  public boolean validatePacs002TranStatus(String statusCode) {
	  if (statusCode != null && !statusCode.isEmpty()) {
		  if (statusCode.equalsIgnoreCase("ACCP") || statusCode.equalsIgnoreCase("RJCT")) {
			  return true;
		  } else {
			  return false;
		  }
	  } else {
		  return false;
	  }
  }

	/*public MdtAcOpsMndtMsgEntity matchPain012(String origMandReqId, String origMndtReqTransId)
	{
		mdtAcOpsMndtMsgEntityOriginal = new MdtAcOpsMndtMsgEntity();
		MdtAcOpsMndtMsgEntity matchedMandate= null;
		if(origMandReqId != null && !origMandReqId.isEmpty() &&  origMndtReqTransId != null &&
		!origMndtReqTransId.isEmpty())
		{

			matchedMandate = (MdtAcOpsMndtMsgEntity) valBeanRemote.matchPain012ToOrigMandate
			(origMandReqId, origMndtReqTransId,"MANCN");

			if(matchedMandate != null)
			{
				log.debug("<<<<<<<<--------"+origMandReqId+" has been matched to
				MANCN------------------>>");
				mdtAcOpsMndtMsgEntityOriginal = matchedMandate;
			}
			else
			{
				matchedMandate = (MdtAcOpsMndtMsgEntity) valBeanRemote.matchPain012ToOrigMandate
				(origMandReqId, origMndtReqTransId, "MANAM");

				if(matchedMandate != null)
				{
					log.debug("<<<<<<<<--------"+origMandReqId+" has been matched to
					MANAM------------------>>");
					mdtAcOpsMndtMsgEntityOriginal = matchedMandate;
				}
				else
				{
					matchedMandate = (MdtAcOpsMndtMsgEntity) valBeanRemote
					.matchPain012ToOrigMandate(origMandReqId, origMndtReqTransId, "MANIN");

					if(matchedMandate != null)
					{
						log.debug("<<<<<<<<--------"+origMandReqId+" has been matched to
						MANIN------------------>>");
						mdtAcOpsMndtMsgEntityOriginal = matchedMandate;
					}
				}
			}
		}
	 */

  public MdtAcOpsMandateTxnsEntity matchPain012(String origMndtReqTransId) {
    List<MdtAcOpsMandateTxnsEntity> matchedList = new ArrayList<MdtAcOpsMandateTxnsEntity>();
    TreeMap<String, MdtAcOpsMandateTxnsEntity> matchedMap =
        new TreeMap<String, MdtAcOpsMandateTxnsEntity>();

    mdtAcMandateTxnsEntityOriginal = null;
    //		MdtAcOpsMandateTxnsEntity matchedMandate= null;
    if (origMndtReqTransId != null && !origMndtReqTransId.isEmpty()) {
      matchedMap = fileProcBeanRemote.optimisedMatchPain012(origMndtReqTransId);

      if (matchedMap.size() > 0) {
        if (matchedMap.containsKey("MANCN")) {
          log.info("<<<<<<<<--------" + origMndtReqTransId +
              " has been matched to MANCN------------------>>");
          mdtAcMandateTxnsEntityOriginal = matchedMap.get("MANCN");
        } else if (matchedMap.containsKey("MANAM")) {
          log.info("<<<<<<<<--------" + origMndtReqTransId +
              " has been matched to MANAM------------------>>");
          mdtAcMandateTxnsEntityOriginal = matchedMap.get("MANAM");
        } else if (matchedMap.containsKey("MANIN")) {
          log.info("<<<<<<<<--------" + origMndtReqTransId +
              " has been matched to MANIN------------------>>");
          mdtAcMandateTxnsEntityOriginal = matchedMap.get("MANIN");
        }
      }

      //			matchedMandate = (MdtAcOpsMandateTxnsEntity) fileProcBeanRemote
		//			.matchPain012ToOrigMandate(origMndtReqTransId,"MANCN");
      //
      //			if(matchedMandate != null)
      //			{
      //				log.info("<<<<<<<<--------"+origMndtReqTransId+" has been matched to
		//				MANCN------------------>>");
      //				mdtAcMandateTxnsEntityOriginal = matchedMandate;
      //			}
      //			else
      //			{
      //				matchedMandate = (MdtAcOpsMandateTxnsEntity) fileProcBeanRemote
		//				.matchPain012ToOrigMandate(origMndtReqTransId, "MANAM");
      //
      //				if(matchedMandate != null)
      //				{
      //					log.info("<<<<<<<<--------"+origMndtReqTransId+" has been matched to
		//					MANAM------------------>>");
      //					mdtAcMandateTxnsEntityOriginal = matchedMandate;
      //				}
      //				else
      //				{
      //					matchedMandate = (MdtAcOpsMandateTxnsEntity) fileProcBeanRemote
		//					.matchPain012ToOrigMandate(origMndtReqTransId, "MANIN");
      //
      //					if(matchedMandate != null )
      //					{
      //
      //						log.info("<<<<<<<<--------"+origMndtReqTransId+" has been matched
		//						to MANIN ------------------>>");
      //						mdtAcMandateTxnsEntityOriginal = matchedMandate;
      //					}
      //
      //				}
      //			}
    }
    //		return matchedMandate;
    return mdtAcMandateTxnsEntityOriginal;


    //2016-11-12 - Matching changes -- Allow for Pain.012 before Pacs.002
    //		String processStatus = "9";
    //		if (origMandReqId != null && !origMandReqId.isEmpty() &&  origMndtReqTransId != null
	  //		&& !origMndtReqTransId.isEmpty())
    //		{
    //			mdtAcOpsMndtMsgEntityOriginal = new MdtAcOpsMndtMsgEntity();
    //			mdtAcOpsMndtMsgEntityOriginal = (MdtAcOpsMndtMsgEntity) valBeanRemote
	  //			.matchOriginalMandate(origMandReqId, origMndtReqTransId,"MANCN",
	  //			processStatus);
    //
    //			if(mdtAcOpsMndtMsgEntityOriginal != null)
    //			{
    //				log.debug("<<<<<<<<--------"+origMandReqId+" has been matched to
	  //				MANCN------------------>>");
    //				return true;
    //			}
    //			else
    //			{
    //				mdtAcOpsMndtMsgEntityOriginal = new MdtAcOpsMndtMsgEntity();
    //				mdtAcOpsMndtMsgEntityOriginal = (MdtAcOpsMndtMsgEntity) valBeanRemote
	  //				.matchOriginalMandate(origMandReqId, origMndtReqTransId, "MANAM",
	  //				processStatus);
    //
    //				if(mdtAcOpsMndtMsgEntityOriginal != null)
    //				{
    //					log.debug("<<<<<<<<--------"+origMandReqId+" has been matched to
	  //					MANAM------------------>>");
    //					return true;
    //				}
    //				else
    //				{
    //					mdtAcOpsMndtMsgEntityOriginal = new MdtAcOpsMndtMsgEntity();
    //					mdtAcOpsMndtMsgEntityOriginal = (MdtAcOpsMndtMsgEntity) valBeanRemote
	  //					.matchOriginalMandate(origMandReqId, origMndtReqTransId, "MANIN",
	  //					processStatus);
    //
    //					if(mdtAcOpsMndtMsgEntityOriginal != null)
    //					{
    //						log.debug("<<<<<<<<--------"+origMandReqId+" has been matched to
	  //						MANIN------------------>>");
    //						return true;
    //					}
    //					else
    //					{
    //						return false;
    //					}
    //				}
    //			}
    //		}
    //		else
    //			return false;
  }

  public MdtAcOpsMandateTxnsEntity matchPacs002ToOrigMandate(String mandateReqTranId,
                                                             String messageType) {
    //		log.debug("XXXXXX*******LOOKING FOR MATCH FOR ST101******XXXXXXX");
    //		log.debug("mandateReqTranId: "+mandateReqTranId);
    //		log.debug("messageType: "+messageType);

    MdtAcOpsMandateTxnsEntity matchedMandate = null;
    if (mandateReqTranId != null && !mandateReqTranId.isEmpty() && messageType != null &&
        !messageType.isEmpty()) {
      matchedMandate =
          (MdtAcOpsMandateTxnsEntity) fileProcBeanRemote.matchPacs002ToOrigMandate(mandateReqTranId,
              messageType);
      if (matchedMandate != null) {
        mdtAcMandateTxnsEntityOriginal = matchedMandate;
      }
    }

    return matchedMandate;
  }

  /**
   * @param rejectReasonCode reason Code
   *                         manres009
   */
  public boolean validateMandateRejectReasonCode(String rejectReasonCode) {
	  if (rejectReasonCode != null && !rejectReasonCode.isEmpty()) {
		  String trimmedRejReason = rejectReasonCode.trim();
		  //			2019-10-06 SalehaR - Use Cached List
		  //			MdtCnfgRejectReasonCodesEntity localEntity = (MdtCnfgRejectReasonCodesEntity)
		  //			valBeanRemote.validateMandateRejectReasonCode(trimmedRejReason);

		  MdtCnfgRejectReasonCodesEntity localEntity = findRejectReasonCodes(trimmedRejReason);
		  if (localEntity != null) {
			  return true;
		  } else {
			  return false;
		  }
	  } else {
		  return false;
	  }
  }

  public boolean validateAuthenticationType(String authType) {
	  if (authType != null) {
		  String trimmedAuthType = authType.trim();
		  MdtCnfgAuthTypeEntity cnfgAuthTypeEntity = findAuthTypes(trimmedAuthType);

		  if (cnfgAuthTypeEntity == null) {
			  return false;
		  } else {
			  return true;
		  }
		  //			2019-10-06 SalehaR - Use Cached List
		  //			MdtCnfgAuthTypeEntity localEntity = (MdtCnfgAuthTypeEntity) valBeanRemote
		  //			.validateAuthType(trimmedAuthType);
		  //
		  //			if (localEntity != null)
		  //			{
		  //				if(localEntity.getActiveInd().equalsIgnoreCase("Y"))
		  //					return true;
		  //				else
		  //					return false;
		  //			}
		  //			else
		  //				return false;
	  } else {
		  return false;
	  }
  }

  public boolean validateAuthstatusInd_029(String authenticationInd) {
	  if (authenticationInd != null) {
		  if (authenticationInd.equalsIgnoreCase("NAUT") ||
				  authenticationInd.equalsIgnoreCase("NRSP") ||
				  authenticationInd.equalsIgnoreCase("AAUT")) {
			  return true;
		  } else {
			  return false;
		  }
	  } else {
		  return false;
	  }
  }


  public boolean validateAuthenticationInd(String authenticationInd) {
	  if (authenticationInd != null) {
		  if (authenticationInd.equalsIgnoreCase("NAUT") ||
				  authenticationInd.equalsIgnoreCase("NRSP")) {
			  return true;
		  } else {
			  return false;
		  }
	  } else {
		  return false;
	  }
  }

  public boolean validateAuthId(String authInd) {
	  if (authInd != null) {
		  if (authInd.equalsIgnoreCase("AAUT")) {
			  return true;
		  } else {
			  return false;
		  }
	  } else {
		  return false;
	  }
  }

  public boolean isDateValid(String date, String dateFormat) {
    try {
      DateFormat df = new SimpleDateFormat(dateFormat);
      df.setLenient(false);
      df.parse(date);
      return true;
    } catch (ParseException e) {
      return false;
    }
  }

  public boolean validateBankNo(String bankNo) {
    log.debug("bankNo-->: " + bankNo);
	  if (bankNo != null && !bankNo.isEmpty()) {
		  //SalehaR 20151105 - Append a 21 infront to check member. Email sent to LL on
		  // 20151105@13h12 to follow up
		  bankNo = "21" + bankNo;
		  SysCisBankEntity sysCisBankEntity = findCisBanks(bankNo);

		  //			2019-10-06 SalehaR - Use Cached List
		  //			SysCisBankEntity sysCisBankEntity = (SysCisBankEntity) valBeanRemote
		  //			.validateMemberNo(bankNo);
		  if (sysCisBankEntity == null) {
			  return false;
		  } else {
			  return true;
		  }
	  } else {
		  return false;
	  }
  }


  public boolean validateCreditorAbbName(String id) {
	  if (id != null && !id.isEmpty()) {
		  String trimId = id.trim();

		  if (trimId == null || trimId.isEmpty() || trimId.length() == 0) {
			  return false;
		  } else {
			  return true;
		  }
	  } else {
		  return false;
	  }
  }

  public boolean validateMandateReqTranId(String mrti) {
    boolean findMatch;
    log.debug("mrti: ---> " + mrti);
    if (mrti != null && !mrti.isEmpty()) {
      String trimMrti = mrti.trim();
      log.debug("trimMrti---> " + trimMrti);


      if (trimMrti == null || trimMrti.isEmpty() ||
          trimMrti.length() != 23)//was checking length = 0 SalehaR 20151105
      {
        findMatch = false;
      } else {
        try {
          String format = "[0-9]{4}[0-9]{4}-[0-9]{2}-[0-9]{2}[0-9]{9}";
          Pattern pattern = Pattern.compile(format);
          Matcher matcher = pattern.matcher(trimMrti);
          findMatch = matcher.find();
        } catch (Exception e) {
          log.error("Exception in validateMandateReqTranId : " + e);
          findMatch = false;
        }
      }
    } else {
      findMatch = false;
    }
    log.debug("findMatch---> " + findMatch);

    return findMatch;
  }

  //	public boolean validateAccountTypes(String entryClass)
  //	{
  //		if (entryClass != null && !entryClass.isEmpty())
  //		{
  //			String trimmedEntryClass = entryClass.trim();
  //			MdtCnfgEntryClassesEntity localEntity = (MdtCnfgEntryClassesEntity) valBeanRemote
	//			.validateEntryClasses(trimmedEntryClass);
  //
  //			if (localEntity != null)
  //				return true;
  //			else
  //				return false;
  //		}
  //		else
  //			return false;
  //	}

  public boolean validateAccountType(String accountType) {
	  if (accountType != null && !accountType.isEmpty()) {
		  String trimmedAccType = accountType.trim();
		  //			2019-10-06 SalehaR - Use Cached List
		  //			MdtCnfgAccountTypeEntity localEntity = (MdtCnfgAccountTypeEntity)
		  //			valBeanRemote.validateAccountType(trimmedAccType.toUpperCase());
		  MdtCnfgAccountTypeEntity accTypeEntity = findAccountTypes(trimmedAccType);
		  if (accTypeEntity != null) {
			  return true;
		  } else {
			  return false;
		  }
	  } else {
		  return false;
	  }
  }

  //2019-09-11 SalehaR - Removed for single table structure
  //	public boolean retrieveOriginalMandate(String msgId,  String mandateReqTranId)
  //	{
  //		origMandate = null;
  //		opsRefDocEntity = null;
  //		opsSupplDataEntity = null;
  //		initgParty = null;
  //		creditorEntity = null;
  //		ultCreditorEntity = null;
  //		debtorEntity = null;
  //		ultDebtorEntity=null;
  //		crSchemeEntity = null;
  //		creditorCashAcc=null;
  //		debtorCashAcc=null;
  //		instgAgtFinInst=null;
  //		instdAgtFinInst=null;
  //		crAgentEntity=null;
  //		drAgentEntity=null;
  //
  //		List<MdtAcOpsPartyIdentEntity> partyIdList, initPartyList = new
	//		ArrayList<MdtAcOpsPartyIdentEntity>();
  //		List<MdtAcOpsCashAccountEntity> cashAccList = new
	//		ArrayList<MdtAcOpsCashAccountEntity>();
  //		List<MdtAcOpsFinInstEntity> finInstList = new ArrayList<MdtAcOpsFinInstEntity>();
  //
  //		log.debug("msgId--> "+msgId);
  //		log.debug("mandateReqTranId--> "+mandateReqTranId);
  //
  //		/*Original Mandate*/
  //		origMandate = (MdtAcOpsMndtMsgEntity) beanRemote.retrieveAcMandate(msgId,
	//		mandateReqTranId);
  //
  //
  //		/*Party Identification*/
  //		partyIdList = (List<MdtAcOpsPartyIdentEntity>) beanRemote
	//		.retrieveAcPartyIdentification(msgId, mandateReqTranId);
  //
  //		if(partyIdList != null && partyIdList.size() > 0)
  //		{
  //			for (MdtAcOpsPartyIdentEntity localPartyEntity : partyIdList)
  //			{
  //				String partyId = localPartyEntity.getMdtAcOpsPartyIdentPK()
	//				.getPartyIdentTypeId();
  //				log.debug("partyId: "+partyId);
  //
  //				if(partyId != null)
  //					partyId.trim();
  //
  //				if(partyId.equalsIgnoreCase("PI01"))
  //				{
  //					log.debug("initgParty: "+initgParty);
  //					initgParty = new MdtAcOpsPartyIdentEntity();
  //					initgParty = localPartyEntity;
  //				}
  //
  //
  //				if(partyId.equalsIgnoreCase("PI02"))
  //				{
  //					creditorEntity = new MdtAcOpsPartyIdentEntity();
  //					creditorEntity = localPartyEntity;
  //					log.debug("creditorEntity: "+creditorEntity);
  //				}
  //
  //
  //				if(partyId.equalsIgnoreCase("PI03"))
  //				{
  //					ultCreditorEntity = new MdtAcOpsPartyIdentEntity();
  //					ultCreditorEntity = localPartyEntity;
  //					log.debug("ultCreditorEntity: "+ultCreditorEntity);
  //				}
  //
  //
  //				if(partyId.equalsIgnoreCase("PI04"))
  //				{
  //					debtorEntity = new MdtAcOpsPartyIdentEntity();
  //					debtorEntity = localPartyEntity;
  //					log.debug("debtorEntity: "+debtorEntity);
  //				}
  //
  //
  //				if(partyId.equalsIgnoreCase("PI05"))
  //				{
  //					ultDebtorEntity = new MdtAcOpsPartyIdentEntity();
  //					ultDebtorEntity = localPartyEntity;
  //					log.debug("ultDebtorEntity: "+ultDebtorEntity);
  //				}
  //
  //
  //				if(partyId.equalsIgnoreCase("PI06"))
  //				{
  //					crSchemeEntity = new MdtAcOpsPartyIdentEntity();
  //					crSchemeEntity = localPartyEntity;
  //					log.debug("crSchemeEntity: "+crSchemeEntity);
  //				}
  //			}
  //		}
  //
  //		/*Cash Account*/
  //		cashAccList = (List<MdtAcOpsCashAccountEntity>) beanRemote.retrieveAcCashAccount
	//		(msgId, mandateReqTranId);
  //		if( cashAccList != null && cashAccList.size() > 0)
  //		{
  //			for (MdtAcOpsCashAccountEntity localCashAccEntity : cashAccList)
  //			{
  //				String partyId = localCashAccEntity.getMdtAcOpsCashAccountPK()
	//				.getPartyIdentTypeId();
  //
  //				if(partyId.equalsIgnoreCase("PI02"))
  //				{
  //					creditorCashAcc = new MdtAcOpsCashAccountEntity();
  //					creditorCashAcc = localCashAccEntity;
  //				}
  //
  //
  //				if(partyId.equalsIgnoreCase("PI04"))
  //				{
  //					debtorCashAcc = new MdtAcOpsCashAccountEntity();
  //					debtorCashAcc = localCashAccEntity;
  //				}
  //			}
  //		}
  //
  //		/*Financial Institution*/
  //		finInstList = (List<MdtAcOpsFinInstEntity>) beanRemote.retrieveAcFinInst(msgId,
	//		mandateReqTranId);
  //		if(finInstList != null && finInstList.size() > 0)
  //		{
  //			for (MdtAcOpsFinInstEntity localFinInstList : finInstList)
  //			{
  //				String finInstId = localFinInstList.getMdtAcOpsFinInstPK().getFinInstTypeId();
  //
  //				if(finInstId.equalsIgnoreCase("FI01"))
  //				{
  //					instgAgtFinInst = new MdtAcOpsFinInstEntity();
  //					instgAgtFinInst = localFinInstList;
  //				}
  //
  //				if(finInstId.equalsIgnoreCase("FI02"))
  //				{
  //					instdAgtFinInst = new MdtAcOpsFinInstEntity();
  //					instdAgtFinInst = localFinInstList;
  //				}
  //
  //
  //				if(finInstId.equalsIgnoreCase("FI03"))
  //				{
  //					crAgentEntity = new MdtAcOpsFinInstEntity();
  //					crAgentEntity = localFinInstList;
  //				}
  //
  //
  //				if(finInstId.equalsIgnoreCase("FI04"))
  //				{
  //					drAgentEntity = new MdtAcOpsFinInstEntity();
  //					drAgentEntity = localFinInstList;
  //				}
  //			}
  //		}
  //
  //		/*Ops Ref Doc*/
  //		opsRefDocEntity = (MdtAcOpsRefDocEntity) beanRemote.retrieveAcOpsRefDoc(msgId,
	//		mandateReqTranId);
  //
  //		/*Ops Supplementary Data*/
  //		opsSupplDataEntity = (MdtAcOpsSupplDataEntity)  beanRemote.retrieveAcOpsSupplData
	//		(msgId, mandateReqTranId);
  //
  //		if(origMandate != null)
  //			log.debug("origMandate: "+origMandate.toString());
  //		if(partyIdList != null)
  //			log.debug("partyIdList: "+partyIdList.toString());
  //		if(cashAccList != null)
  //			log.debug("cashAccList: "+cashAccList.toString());
  //		if(finInstList != null)
  //			log.debug("finInstList: "+finInstList.toString());
  //		if(opsRefDocEntity != null)
  //			log.debug("opsRefDocEntity: "+opsRefDocEntity.toString());
  //		if(opsSupplDataEntity != null)
  //			log.debug("opsSupplDataEntity: "+opsSupplDataEntity.toString());
  //
  //		if(origMandate != null)
  //			return true;
  //		else
  //			return false;
  //
  //	}

  public boolean validateErrorCodes(String errorCode) {
	  if (errorCode != null && !errorCode.isEmpty()) {
		  //			2019-10-06 SalehaR - Use Cached List
		  //			MdtCnfgErrorCodesEntity localEntity = (MdtCnfgErrorCodesEntity) valBeanRemote
		  //			.validateErrorCodes(errorCode);
		  MdtCnfgErrorCodesEntity localEntity = findErrorCodes(errorCode);
		  if (localEntity != null) {
			  return true;
		  } else {
			  return false;
		  }
	  } else {
		  return false;
	  }
  }

  /**
   * @param value for null value rule 999
   *              Rule 012.999
   */
  public boolean validateRule999(String value) {
	  if (value != null || !value.isEmpty()) {
		  String trimValue = value.trim();
		  if (trimValue == null || trimValue.isEmpty() || trimValue.length() == 0) {
			  return false;
		  } else {
			  return true;
		  }
	  } else {
		  return false;
	  }
  }

  /**
   * @param currency currency
   *                 Rule 012.51
   */
  public boolean validateCurrency(String currency) {
	  if (currency != null) {
		  if (currency.equalsIgnoreCase("ZAR")) {
			  return true;
		  } else {
			  return false;
		  }
	  } else {
		  return false;
	  }
  }

  public boolean validateMndtReqTranIdUnique(String mrti) {
    String trimMndtReqTranId = mrti.trim();

    //		2020/08/05 SalehaR ==  Duplicate Check Optimisation Reverted
    //		return fileProcBeanRemote.checkForDuplicateMRTI(mrti);

    List<MdtAcOpsMandateTxnsEntity> mandateTxnList =
        (List<MdtAcOpsMandateTxnsEntity>) fileProcBeanRemote.validateMndtReqTranIdUnique(
            trimMndtReqTranId);

    if (mandateTxnList != null & mandateTxnList.size() > 0) {
      return false;
    } else {
      return true;
    }
  }

  public boolean validateMndtSuspId(String mndtSuspId) {
    boolean findMatch;
    log.debug("mndtSuspId: ---> " + mndtSuspId);
    if (mndtSuspId != null && !mndtSuspId.isEmpty()) {
      String trimMndtSuspId = mndtSuspId.trim();
      log.debug("trimMndtSuspId---> " + trimMndtSuspId);


      if (trimMndtSuspId == null || trimMndtSuspId.isEmpty() || trimMndtSuspId.length() != 29) {
        findMatch = false;
      } else {
        try {
          log.debug("In the format pattern section.....");
          String format = "[A-Za-z0-9]{3}/[0-9]{4}/[0-9]{4}-[0-9]{2}-[0-9]{2}/[0-9]{9}$";

          Pattern pattern = Pattern.compile(format);
          Matcher matcher = pattern.matcher(trimMndtSuspId);
          findMatch = matcher.find();
        } catch (Exception e) {
          log.error("Exception in validateMndtSuspId : " + e);
          findMatch = false;
        }
      }
    } else {
      findMatch = false;
    }
    log.debug("findMatch---> " + findMatch);

    return findMatch;
  }


  /**
   * @param memberNo 055.017
   * @return
   */
  public boolean validateCisMemberNo(String memberNo) {
    log.debug("memberNo-->: " + memberNo);
	  if (memberNo != null && !memberNo.isEmpty()) {
		  SysCisBankEntity sysCisBankEntity = findCisBanks(memberNo);
		  //			2019-10-06 SalehaR - Use Cached List
		  //			SysCisBankEntity sysCisBankEntity = (SysCisBankEntity) valBeanRemote
		  //			.validateSysCisBankDetails(memberNo);
		  if (sysCisBankEntity == null) {
			  return false;
		  } else {
			  return true;
		  }
	  } else {
		  return false;
	  }
  }

  public boolean validateGrpStatus(String grpStatus) {
    log.debug("grpStatus: " + grpStatus);
    if (grpStatus != null && !grpStatus.isEmpty()) {
      grpStatus.trim();

		if (grpStatus.equalsIgnoreCase("ACCP") || grpStatus.equalsIgnoreCase("PART") ||
				grpStatus.equalsIgnoreCase("RJCT")) {
			return true;
		} else {
			return false;
		}

    }
    return false;
  }

  public boolean validateTransStatus(String tranStatus) {
    log.debug("tranStatus: " + tranStatus);
    if (tranStatus != null && !tranStatus.isEmpty()) {
      tranStatus.trim();

		if (tranStatus.equalsIgnoreCase("ACCP") || tranStatus.equalsIgnoreCase("PDNG") ||
				tranStatus.equalsIgnoreCase("RJCT")) {
			return true;
		} else {
			return false;
		}

    }
    return false;
  }

  public boolean validateCreditorBank(String creditorBank) {
    log.debug("creditorBank-->: " + creditorBank);
	  if (creditorBank != null && !creditorBank.isEmpty()) {
		  SysCisBankEntity sysCisBankEntity = findCisBanks(creditorBank);
		  if (sysCisBankEntity == null) {
			  return false;
		  } else {
			  if (sysCisBankEntity.getAcCreditor().equalsIgnoreCase("Y")) {
				  return true;
			  } else {
				  return false;
			  }
		  }
		  //			2019-10-06 SalehaR - Use Cached List
		  //			SysCisBankEntity sysCisBankEntity = (SysCisBankEntity) valBeanRemote
		  //			.validateCreditorBank(creditorBank);
		  //			if(sysCisBankEntity == null)
		  //				return false;
		  //			else
		  //				return true;
	  } else {
		  return false;
	  }
  }

  public boolean validateDebtorBank(String debtorBank) {
    log.debug("debtorBank-->: " + debtorBank);
	  if (debtorBank != null && !debtorBank.isEmpty()) {
		  SysCisBankEntity sysCisBankEntity = findCisBanks(debtorBank);
		  if (sysCisBankEntity == null) {
			  return false;
		  } else {
			  if (sysCisBankEntity.getAcDebtor().equalsIgnoreCase("Y")) {
				  return true;
			  } else {
				  return false;
			  }
		  }
		  //			2019-10-06 SalehaR - Use Cached List
		  //			SysCisBankEntity sysCisBankEntity = (SysCisBankEntity) valBeanRemote
		  //			.validateDebtorBank(debtorBank);
		  //			if(sysCisBankEntity == null)
		  //				return false;
		  //			else
		  //				return true;
	  } else {
		  return false;
	  }
  }


  public boolean validatePacs002MsgId(String msgId) {
	  if (msgId != null && !msgId.isEmpty()) {
		  List<MdtAcOpsConfHdrsEntity> mdtAcOpsConfHdrsList =
				  (List<MdtAcOpsConfHdrsEntity>) valBeanRemote.validatePacs002MsgId(msgId);
		  log.debug("the message id is *******************************************" + msgId);
		  if (mdtAcOpsConfHdrsList != null & mdtAcOpsConfHdrsList.size() > 0) {
			  return false;
		  } else {
			  return true;
		  }
	  } else {
		  return false;
	  }
  }

  public boolean isValidProcessingDate(String date, String dateFormat) {
    if (date == null) {
      return false;
    } else {
      SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
      try {
        Date crDate = sdf.parse(date);
        Date processingDate;
		  if (casSysctrlSysParamEntity != null) {
			  processingDate = casSysctrlSysParamEntity.getProcessDate();
		  } else {
			  processingDate = new Date();
		  }


        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        String strCrDate = sdf1.format(crDate);
        String strPrDate = sdf1.format(processingDate);

        Date convCrDate = sdf1.parse(strCrDate);
        Date convPrDate = sdf1.parse(strPrDate);

        log.debug("convCrDate: " + convCrDate);
        log.debug("convPrDate: " + convPrDate);

		  if (convCrDate.equals(convPrDate)) {
			  return true;
		  } else {
			  return false;
		  }
      } catch (ParseException pe) {
        return false;
      }
    }
  }

  public void populateErrorCodes() {
    try {
      List<MdtCnfgErrorCodesEntity> errorCodesList =
          (List<MdtCnfgErrorCodesEntity>) adminBeanRemote.retrieveActiveErrorCodes("Y");

      if (errorCodesList != null && errorCodesList.size() > 0) {
        listOfErrorCodes = new HashMap<String, String>();

        for (MdtCnfgErrorCodesEntity localEntity : errorCodesList) {
          listOfErrorCodes.put(localEntity.getErrorCode(), localEntity.getErrorCodeDesc());
        }
      }
    } catch (Exception ex) {
      log.error("Error retrieving active error codes ==> " + ex.getMessage());
      ex.printStackTrace();
    }
  }

  //	===================CACHEABLE STATIC DATA=============================//
  private MdtOpsServicesEntity findOpsServices(final String serviceIdIn) {
    if (serviceIdIn == null || opsServicesList == null || opsServicesList.isEmpty()) {
      return null;
    }

    MdtOpsServicesEntity mdtOpsServiceEntity = IterableUtils.find(opsServicesList,
        new Predicate<MdtOpsServicesEntity>() {
          public boolean evaluate(MdtOpsServicesEntity opsServicesEntity) {
            return ((serviceIdIn.equalsIgnoreCase(opsServicesEntity.getServiceIdIn())) &&
                opsServicesEntity.getActiveInd().equalsIgnoreCase("Y"));
          }
        });

    return mdtOpsServiceEntity;
  }

  public SysCisBankEntity findCisBanks(final String bankNo) {
    //		log.info("sysCisBankList in findCisBank ==> "+sysCisBankList);
    if (bankNo == null || sysCisBankList == null || sysCisBankList.isEmpty()) {
      return null;
    }

    SysCisBankEntity sysCisBankEntity = IterableUtils.find(sysCisBankList,
        new Predicate<SysCisBankEntity>() {
          public boolean evaluate(SysCisBankEntity sysCisBankEntity) {
            return (bankNo.equalsIgnoreCase(sysCisBankEntity.getMemberNo()));
          }
        });

    return sysCisBankEntity;
  }

  private SysCisBranchEntity findCisBranches(final String branchNo) {
    if (branchNo == null || sysCisBranchList == null || sysCisBranchList.isEmpty()) {
      return null;
    }

    SysCisBranchEntity sysCisBranchEntity = IterableUtils.find(sysCisBranchList,
        new Predicate<SysCisBranchEntity>() {
          public boolean evaluate(SysCisBranchEntity sysCisBranchEntity) {
            return ((branchNo.equalsIgnoreCase(sysCisBranchEntity.getBranchNo())));
          }
        });
    return sysCisBranchEntity;
  }

  public SysCisBranchEntity findCreditorCISBranches(final String branchNo) {
    if (branchNo == null || sysCisBranchList == null || sysCisBranchList.isEmpty()) {
      return null;
    }

    SysCisBranchEntity sysCisBranchEntity = IterableUtils.find(sysCisBranchList,
        new Predicate<SysCisBranchEntity>() {
          public boolean evaluate(SysCisBranchEntity sysCisBranchEntity) {
            return ((branchNo.equalsIgnoreCase(sysCisBranchEntity.getBranchNo())) &&
                sysCisBranchEntity.getAcCreditor().equalsIgnoreCase("Y"));
          }
        });
    return sysCisBranchEntity;
  }

  public SysCisBranchEntity findDebtorCISBranches(final String branchNo) {
    if (branchNo == null || sysCisBranchList == null || sysCisBranchList.isEmpty()) {
      return null;
    }

    SysCisBranchEntity sysCisBranchEntity = IterableUtils.find(sysCisBranchList,
        new Predicate<SysCisBranchEntity>() {
          public boolean evaluate(SysCisBranchEntity sysCisBranchEntity) {
            return ((branchNo.equalsIgnoreCase(sysCisBranchEntity.getBranchNo())) &&
                sysCisBranchEntity.getAcDebtor().equalsIgnoreCase("Y"));
          }
        });
    return sysCisBranchEntity;
  }

  private MdtCnfgSequenceTypeEntity findSequenceTypes(final String sequenceType) {
    if (sequenceType == null || cnfgSequenceTypeList == null || cnfgSequenceTypeList.isEmpty()) {
      return null;
    }

    MdtCnfgSequenceTypeEntity cnfgSeqTypeEntity = IterableUtils.find(cnfgSequenceTypeList,
        new Predicate<MdtCnfgSequenceTypeEntity>() {
          public boolean evaluate(MdtCnfgSequenceTypeEntity cnfgSeqTypeEntity) {
            return ((sequenceType.equalsIgnoreCase(cnfgSeqTypeEntity.getSeqTypeCode())) &&
                cnfgSeqTypeEntity.getActiveInd().equalsIgnoreCase("Y"));
          }
        });

    return cnfgSeqTypeEntity;
  }

  private MdtCnfgFrequencyCodesEntity findFrequencyCodes(final String frequencyCode) {
    if (frequencyCode == null || cnfgFrequencyCodesList == null ||
        cnfgFrequencyCodesList.isEmpty()) {
      return null;
    }

    MdtCnfgFrequencyCodesEntity cnfgFreqCodeEntity = IterableUtils.find(cnfgFrequencyCodesList,
        new Predicate<MdtCnfgFrequencyCodesEntity>() {
          public boolean evaluate(MdtCnfgFrequencyCodesEntity cnfgFreqCodeEntity) {
            return ((frequencyCode.equalsIgnoreCase(cnfgFreqCodeEntity.getFrequencyCode())) &&
                cnfgFreqCodeEntity.getActiveInd().equalsIgnoreCase("Y"));
          }
        });

    return cnfgFreqCodeEntity;
  }

  private MdtCnfgDebitValueTypeEntity findDebitValTypes(final String debitValType) {
    if (debitValType == null || cnfgDebitValueTypeList == null ||
        cnfgDebitValueTypeList.isEmpty()) {
      return null;
    }

    MdtCnfgDebitValueTypeEntity cnfgDebitValTypesEntity = IterableUtils.find(cnfgDebitValueTypeList,
        new Predicate<MdtCnfgDebitValueTypeEntity>() {
          public boolean evaluate(MdtCnfgDebitValueTypeEntity debitValTypesEntity) {
            return (
                (debitValType.equalsIgnoreCase(debitValTypesEntity.getDebValueTypeDescription())) &&
                    debitValTypesEntity.getActiveInd().equalsIgnoreCase("Y"));
          }
        });
    return cnfgDebitValTypesEntity;
  }

  private MdtCnfgAdjustmentCatEntity findAdjustmentCat(final String adjCategory) {
    if (adjCategory == null || cnfgAdjustmentCatList == null || cnfgAdjustmentCatList.isEmpty()) {
      return null;
    }

    MdtCnfgAdjustmentCatEntity cnfgAdjCatEntity = IterableUtils.find(cnfgAdjustmentCatList,
        new Predicate<MdtCnfgAdjustmentCatEntity>() {
          public boolean evaluate(MdtCnfgAdjustmentCatEntity cnfgAdjCatEntity) {
            return ((adjCategory.equalsIgnoreCase(cnfgAdjCatEntity.getAdjustmentCategory())) &&
                cnfgAdjCatEntity.getActiveInd().equalsIgnoreCase("Y"));
          }
        });
    return cnfgAdjCatEntity;
  }

  private MdtCnfgAuthTypeEntity findAuthTypes(final String authType) {
    if (authType == null || cnfgAuthTypeList == null || cnfgAuthTypeList.isEmpty()) {
      return null;
    }

    MdtCnfgAuthTypeEntity cnfgAuthTypeEntity = IterableUtils.find(cnfgAuthTypeList,
        new Predicate<MdtCnfgAuthTypeEntity>() {
          public boolean evaluate(MdtCnfgAuthTypeEntity cnfgAuthTypeEntity) {
            return ((authType.equalsIgnoreCase(cnfgAuthTypeEntity.getAuthType())) &&
                cnfgAuthTypeEntity.getActiveInd().equalsIgnoreCase("Y"));
          }
        });
    return cnfgAuthTypeEntity;
  }

  private MdtCnfgAccountTypeEntity findAccountTypes(final String accType) {
    if (accType == null || cnfgAccountTypesList == null || cnfgAccountTypesList.isEmpty()) {
      return null;
    }

    MdtCnfgAccountTypeEntity cnfgAccTypesEntity = IterableUtils.find(cnfgAccountTypesList,
        new Predicate<MdtCnfgAccountTypeEntity>() {
          public boolean evaluate(MdtCnfgAccountTypeEntity cnfgAccTypesEntity) {
            return ((accType.equalsIgnoreCase(cnfgAccTypesEntity.getAccountTypeDescription())) &&
                cnfgAccTypesEntity.getActiveInd().equalsIgnoreCase("Y"));
          }
        });
    return cnfgAccTypesEntity;
  }

  private MdtCnfgReasonCodesEntity findReasonCodes(final String reasonCode) {
    if (reasonCode == null || cnfgReasonCodesList == null || cnfgReasonCodesList.isEmpty()) {
      return null;
    }

    MdtCnfgReasonCodesEntity cnfgReasonCodesEntity = IterableUtils.find(cnfgReasonCodesList,
        new Predicate<MdtCnfgReasonCodesEntity>() {
          public boolean evaluate(MdtCnfgReasonCodesEntity cnfgReasonCodesEntity) {
            return ((reasonCode.equalsIgnoreCase(cnfgReasonCodesEntity.getReasonCode())) &&
                cnfgReasonCodesEntity.getActiveInd().equalsIgnoreCase("Y"));
          }
        });
    return cnfgReasonCodesEntity;
  }

  private MdtCnfgAmendmentCodesEntity findAmendmentCodes(final String amendmentCode) {
    if (amendmentCode == null || cnfgAmendmentCodesList == null ||
        cnfgAmendmentCodesList.isEmpty()) {
      return null;
    }

    MdtCnfgAmendmentCodesEntity cnfgAmendmentCodesEntity =
        IterableUtils.find(cnfgAmendmentCodesList,
            new Predicate<MdtCnfgAmendmentCodesEntity>() {
              public boolean evaluate(MdtCnfgAmendmentCodesEntity cnfgAmendmentCodesEntity) {
                return ((amendmentCode.equalsIgnoreCase(
                    cnfgAmendmentCodesEntity.getAmendmentCodes())) &&
                    cnfgAmendmentCodesEntity.getActiveInd().equalsIgnoreCase("Y"));
              }
            });
    return cnfgAmendmentCodesEntity;
  }

  private MdtCnfgCancellationCodesEntity findCancellationCodes(final String cancellationCode) {
    if (cancellationCode == null || cnfgCancellationCodesList == null ||
        cnfgCancellationCodesList.isEmpty()) {
      return null;
    }

    MdtCnfgCancellationCodesEntity cnfgCancellationCodesEntity =
        IterableUtils.find(cnfgCancellationCodesList,
            new Predicate<MdtCnfgCancellationCodesEntity>() {
              public boolean evaluate(MdtCnfgCancellationCodesEntity cnfgCancellationCodesEntity) {
                return ((cancellationCode.equalsIgnoreCase(
                    cnfgCancellationCodesEntity.getCancellationCode())) &&
                    cnfgCancellationCodesEntity.getActiveInd().equalsIgnoreCase("Y"));
              }
            });
    return cnfgCancellationCodesEntity;
  }

  private MdtCnfgRejectReasonCodesEntity findRejectReasonCodes(final String rejectReasonCode) {
    if (rejectReasonCode == null || cnfgRejectReasonCodesList == null ||
        cnfgRejectReasonCodesList.isEmpty()) {
      return null;
    }

    MdtCnfgRejectReasonCodesEntity cnfgRejectReasonCodesEntity =
        IterableUtils.find(cnfgRejectReasonCodesList,
            new Predicate<MdtCnfgRejectReasonCodesEntity>() {
              public boolean evaluate(MdtCnfgRejectReasonCodesEntity cnfgRejectReasonCodesEntity) {
                return ((rejectReasonCode.equalsIgnoreCase(
                    cnfgRejectReasonCodesEntity.getRejectReasonCode())) &&
                    cnfgRejectReasonCodesEntity.getActiveInd().equalsIgnoreCase("Y"));
              }
            });
    return cnfgRejectReasonCodesEntity;
  }

  private MdtCnfgErrorCodesEntity findErrorCodes(final String errorCode) {
    if (errorCode == null || cnfgErrorCodesList == null || cnfgErrorCodesList.isEmpty()) {
      return null;
    }

    MdtCnfgErrorCodesEntity cnfgErrorCodesEntity = IterableUtils.find(cnfgErrorCodesList,
        new Predicate<MdtCnfgErrorCodesEntity>() {
          public boolean evaluate(MdtCnfgErrorCodesEntity cnfgErrorCodesEntity) {
            return ((errorCode.equalsIgnoreCase(cnfgErrorCodesEntity.getErrorCode())) &&
                cnfgErrorCodesEntity.getActiveInd().equalsIgnoreCase("Y"));
          }
        });
    return cnfgErrorCodesEntity;
  }

  public boolean validateFileSizeLimit(String serviceName, String memberNo,
                                       Integer inwardFileSize) {
	  if (memberNo != null && !memberNo.isEmpty() ||
			  serviceName != null && !serviceName.isEmpty() ||
			  inwardFileSize > 0) {
		  MdtAcOpsFileSizeLimitEntity mdtAcOpsFileSizeLimitEntity =
				  findFileSizeLimt(serviceName, memberNo, inwardFileSize);

		  if (mdtAcOpsFileSizeLimitEntity == null) {
			  return false;
		  } else {

			  if (inwardFileSize <= Integer.valueOf(mdtAcOpsFileSizeLimitEntity.getLimit())) {
				  return true;
			  } else {
				  return false;
			  }
		  }
	  } else {
		  return false;
	  }
  }


  public MdtAcOpsFileSizeLimitEntity findFileSizeLimt(final String serviceName,
                                                      final String memberNo,
                                                      final Integer inwardFileSize) {
    if (memberNo == null || serviceName == null || inwardFileSize < 0 ||
        opsFileSizeLimitList == null || opsFileSizeLimitList.isEmpty()) {
      return null;
    }
    MdtAcOpsFileSizeLimitEntity mdtAcOpsFileSizeLimitEntity =
        IterableUtils.find(opsFileSizeLimitList, new Predicate<MdtAcOpsFileSizeLimitEntity>() {

          @Override
          public boolean evaluate(MdtAcOpsFileSizeLimitEntity mdtAcOpsFileSizeLimitEntity) {
            // TODO Auto-generated method stub
            return
                ((serviceName.equalsIgnoreCase(
                    mdtAcOpsFileSizeLimitEntity.getMdtAcOpsFileSizeLimitPK().getSubService())) &&
                    memberNo.equalsIgnoreCase(
                        mdtAcOpsFileSizeLimitEntity.getMdtAcOpsFileSizeLimitPK().getMemberId()) &&
                    inwardFileSize <= Integer.valueOf(mdtAcOpsFileSizeLimitEntity.getLimit()));
          }

        });
    return mdtAcOpsFileSizeLimitEntity;

  }

  //Search for specific Creditor/Debtor Banks
  public SysCisBankEntity findCreditorCISBanks(final String memberNo) {
    if (memberNo == null || sysCisBankList == null || sysCisBankList.isEmpty()) {
      return null;
    }
    SysCisBankEntity sysCisBankEntity =
        IterableUtils.find(sysCisBankList, new Predicate<SysCisBankEntity>() {

          @Override
          public boolean evaluate(SysCisBankEntity sysCisBankEntity) {
            // TODO Auto-generated method stub
            return
                ((memberNo.equalsIgnoreCase(sysCisBankEntity.getMemberNo())) &&
                    sysCisBankEntity.getAcCreditor().equalsIgnoreCase("Y"));
          }

        });
    return sysCisBankEntity;
  }


  public SysCisBankEntity findDebtorCISBanks(final String memberNo) {
    if (memberNo == null || sysCisBankList == null || sysCisBankList.isEmpty()) {
      return null;
    }
    SysCisBankEntity sysCisBankEntity =
        IterableUtils.find(sysCisBankList, new Predicate<SysCisBankEntity>() {

          @Override
          public boolean evaluate(SysCisBankEntity sysCisBankEntity) {
            // TODO Auto-generated method stub
            return
                ((memberNo.equalsIgnoreCase(sysCisBankEntity.getMemberNo())) &&
                    sysCisBankEntity.getAcDebtor().equalsIgnoreCase("Y"));
          }

        });
    return sysCisBankEntity;
  }


  public Object generateTxnsReportData(String creditorBank, String txnId, String service,
                                       String fileName) {
    //Populate Ops Txn Bill Table

    MdtAcOpsTxnsBillReportEntity opsTxnsBillReport = new MdtAcOpsTxnsBillReportEntity();

    opsTxnsBillReport.setSystemSeqNo(new BigDecimal(123));

    opsTxnsBillReport.setProcessDate(casSysctrlSysParamEntity.getProcessDate());

    MdtOpsFileRegEntity mdtOpsFileRegEntity =
        (MdtOpsFileRegEntity) valBeanRemote.retrieveOpsFileReg(fileName);

    if (mdtOpsFileRegEntity != null && mdtOpsFileRegEntity.getFileName() != null) {
      opsTxnsBillReport.setDeliveryTime(mdtOpsFileRegEntity.getProcessDate());
    }

    opsTxnsBillReport.setFileName(fileName);
    opsTxnsBillReport.setOriginator(creditorBank);
    opsTxnsBillReport.setMandateReqTranId(txnId);
    opsTxnsBillReport.setSubService(service);
    opsTxnsBillReport.setTxnType("TT2");
    opsTxnsBillReport.setTxnStatus("RJCT");


    return opsTxnsBillReport;
  }

  public static void contextAdminBeanCheck() {
    if (adminBeanRemote == null) {
      adminBeanRemote = Util.getAdminBean();
    }
  }

  public static void contextValidationBeanCheck() {
    if (valBeanRemote == null) {
      valBeanRemote = Util.getValidationBean();
    }

  }

  public static void contextCheck() {
    if (beanRemote == null) {
      beanRemote = Util.getServiceBean();
    }
  }

  public static void contextFileProcBeanCheck() {
    if (fileProcBeanRemote == null) {
      fileProcBeanRemote = Util.getFileProcessBean();
    }
  }

}