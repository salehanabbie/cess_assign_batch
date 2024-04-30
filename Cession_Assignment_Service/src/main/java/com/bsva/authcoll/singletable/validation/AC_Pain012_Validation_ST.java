package com.bsva.authcoll.singletable.validation;

import com.bsva.entities.CasOpsFileRegEntity;
import iso.std.iso._20022.tech.xsd.pain_012_001.AcceptanceResult6;
import iso.std.iso._20022.tech.xsd.pain_012_001.GroupHeader47;
import iso.std.iso._20022.tech.xsd.pain_012_001.Mandate1;
import iso.std.iso._20022.tech.xsd.pain_012_001.MandateAcceptance3;
import iso.std.iso._20022.tech.xsd.pain_012_001.SupplementaryData1;
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
import com.bsva.PropertyUtil;
import com.bsva.entities.CasOpsCessionAssignEntity;
import com.bsva.entities.CasCnfgErrorCodesEntity;
import com.bsva.entities.CasOpsCustParamEntity;
import com.bsva.entities.CasOpsRefSeqNrEntity;
import com.bsva.entities.CasSysctrlCompParamEntity;
import com.bsva.entities.CasOpsStatusDetailsEntity;
import com.bsva.entities.CasOpsStatusHdrsEntity;
import com.bsva.entities.SysCisBranchEntity;

/* 
 * @author DimakatsoN
 * Modified by SalehaR - 12/11/2015 - Alignment to Int Spec v2.0
 * Modified by SalehaR - 06/03/2016 - Alignment to Int Spec v3.1
 * Modified by SalehaR - 12/09/2016 - Alignment to TRS 15
 * Modified by SalehaR - 2018/03/13 - Aligned to TRS 17
 * Modified by SalehaR - 2019/01/02 - CHG-153240-Validate Creation Date in Msg Id
 * @author SalehaR-2019/05/10 Debug Statements & Code CleanUp
 * @author SalehaR-2019-09-18: Align to use Single Table
 * @author SalehaR-2019/10/17 File Size Limit Validation
 */

public class AC_Pain012_Validation_ST extends Validation_ST
{
	public static Logger log = Logger.getLogger("AC_Pain012_Validation_ST");

	@EJB
	PropertyUtil propertyUtil;

	SimpleDateFormat sdfDateTime = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
	SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd");
	SimpleDateFormat sdfFileDate = new SimpleDateFormat("yyyyMMdd");
	int grpHdrSeverity = 0;
	int mandateSeverity = 0;

	/*_______________AC tables __________________*/
	List<CasOpsStatusHdrsEntity>opsStatusHdrsList=null;
	List<CasOpsStatusDetailsEntity>opsStatusDetailsList=null;

	//Ac Entities declaration
	CasOpsStatusHdrsEntity opsStatusHdrsEntity=null;
	CasOpsStatusDetailsEntity opsStatusDetailsEntity=null;
	public BigDecimal hdrSystemSeqNo = BigDecimal.ZERO;
	CasSysctrlCompParamEntity mdtSysctrlCompParamEntity;

	public String branchmemberIdDebtor,branchmemberIdCreditor;
	boolean bicCodeValid = false;
	String msgId,msgCreateDate, incInstid, fileName, outMsgId, mandateReqId,bicfi,orgnlMsgId,orgnlMsgNameId,orgnlCtrlSum,grpStatus,accptInstAmt, mndtReqTransId;
	String trimmedManRfNo = null;
	String trimCredAbbName = null;
	String debtorBranchNo = null;
	//Objects for mdtAcOpsStatusRepDet
	String statusReasonInfo,txnStatus,orgnlTxnId,reason,additonalInfo;
	//Long orgnlNrOfTrans;
	Date convCreationDateTime;
	Date originalCreateDateTime;
	int fileNum = 0;
	String backEndProcess = "BACKEND";
	public static String systemName = "CAMOWNER";
	String hdrErrorType = "HDR";
	String txnErrorType = "TXN";
	String extractStatus = "4";
	String matchedStatus = "M";
	String rejectedStatus = "R";
	String responseRecStatus = "9";

	iso.std.iso._20022.tech.xsd.pain_012_001.GroupHeader47 groupHdr;
	private String systemType;
	private String origServiceId = null;
	String stsHdrInstgAgt = null;
	String fileSizeLimitStr = null;
	Integer fileSizeLimit;

	//Populate Error Codes Report Data
	public String creditorBank = null;
	String debtorBank = null, ultCreditor = null, abbShortName = null, manacService = null;

	public AC_Pain012_Validation_ST (String fileName)
	{
		this.fileName = fileName;

		try
		{
			propertyUtil = new PropertyUtil();
			this.manacService = propertyUtil.getPropValue("Input.Pain012");
			fileSizeLimitStr = propertyUtil.getPropValue("AC.FILE.TRANSACTION.LIMIT");
//			log.info("fileSizeLimit ==> "+fileSizeLimitStr);
			fileSizeLimit = Integer.valueOf(fileSizeLimitStr);
		}
		catch (Exception e) 
		{
			log.error("AC_Pain012_Validation_ST - Could not find MandateMessageCommons.properties in classpath");	
			manacService = "RCAIN";
			fileSizeLimit = 50000;
		}

		opsStatusHdrsList = new ArrayList<CasOpsStatusHdrsEntity>();
		opsStatusDetailsList = new ArrayList<CasOpsStatusDetailsEntity>();

		hdrSystemSeqNo = BigDecimal.ZERO;
		bicCodeValid = false;
		contextValidationBeanCheck();
		contextCheck();
		log.debug("mdtSysctrlSysParamEntity: "+ casSysctrlSysParamEntity);
		systemType = casSysctrlSysParamEntity.getSysType();
		mdtSysctrlCompParamEntity = (CasSysctrlCompParamEntity) valBeanRemote.retrieveCompanyParameters(backEndProcess);
	}

	public int validateGroupHeader(GroupHeader47 groupHeader, Integer inwardFileSize) 
	{
		debtorBank = null; 
		creditorBank = null;
		creditorBank = mdtSysctrlCompParamEntity.getAchInstId();

		groupHdr = new GroupHeader47();
		groupHdr = groupHeader;
		origServiceId = null;
		msgId = groupHeader.getMsgId();
		log.debug("grpHdr: "+groupHdr.getMsgId());
		stsHdrInstgAgt = null;

		if(!validateMsgIdStructure(msgId))
		{
			incInstid = fileName.substring(8,16);
			stsHdrInstgAgt = fileName.substring(10,16);
			incInstid = StringUtils.stripStart(incInstid,"0");

			outMsgId = generateStatusHdrMsgId(incInstid);
			debtorBank = incInstid;
			log.debug("Write to GrpHdr ..MsgId fails....");

			grpHdrSeverity++;
			generateStatusErrorDetailsList("902134", null, hdrErrorType);
		}
		else
		{
			/*validate msgIdFormat*/
			String [] splitMsgId = splitMsgId(msgId);

			String achId = splitMsgId[0];																																/* msgId.substring(0, 3);*/
			String serviceId = splitMsgId[1];																														/*msgId.substring(4,9);*/
			incInstid = splitMsgId[2]; 																																		/*msgId.substring(10,18);*/
			incInstid = StringUtils.stripStart(incInstid,"0");
			debtorBank = incInstid;

			String msgCreationDate = splitMsgId[3];																										/*msgId.substring(19, 27);*/
			String fileNo = splitMsgId[4];		
			fileNum = Integer.valueOf(fileNo);
			/*msgId.substring(28,32);*/
			log.debug("achId: "+achId);
			log.debug("serviceId: "+serviceId);
			log.debug("inst id: "+incInstid);
			log.debug("msgCreationDate: "+msgCreationDate);
			log.debug("fileNo: "+fileNo);

			//____________________________Validate BicCode_Rule 012_003 _____________________________________//
			if(!validateDebtorBank(incInstid))
			{
				incInstid = fileName.substring(8,16);
				incInstid = StringUtils.stripStart(incInstid,"0");

				generateStatusErrorDetailsList("901001", null, hdrErrorType);
				grpHdrSeverity++;
				bicCodeValid = false;
				log.info("******************validateMemberId_003 - Failed.******************");
			}
			else
			{
				log.debug("******************validateMemberId_003 - Passed.******************");
				bicCodeValid = true;
				grpHdrSeverity = grpHdrSeverity+0;
			}

			//Check MemberNumberMatchs
			String instgAgt = groupHeader.getInstgAgt().getFinInstnId().getClrSysMmbId().getMmbId();
			String msgMmId = incInstid;
			String fileMmId = fileName.substring(10,16);
			boolean match1 = false, match2 = false;

			log.debug("instgAgt ----XXX :"+instgAgt);
			log.debug("msgMmId ----XXX :"+msgMmId);
			log.debug("fileMmId ----XXX :"+fileMmId);
			//MATCH 1 --> Match InstgAgt to MsgId
			if(instgAgt.equalsIgnoreCase(msgMmId))
			{
				log.debug("******************validateInstgAgtToMsgIdMemberId - Passed.******************");
				grpHdrSeverity = grpHdrSeverity+0;
				match1 = true;
			}
			else
			{
				match1 = false;
				generateStatusErrorDetailsList("901017", null, hdrErrorType);
				grpHdrSeverity++;
				log.info("******************validateInstgAgtToMsgIdMemberId - Failed.******************");
			}


			//Match 2 --> Match InstgAgt to FileName
			if(instgAgt.equalsIgnoreCase(fileMmId))
			{
				match2 = true;
				log.debug("******************validateInstgAgtToFileMemberId - Passed.******************");
				grpHdrSeverity = grpHdrSeverity+0;
			}
			else
			{
				match2 = false;
				generateStatusErrorDetailsList("902124", null, hdrErrorType);
				grpHdrSeverity++;
				log.info("******************validateInstgAgtToFileMemberId - Failed.******************");
			}

			if(match1 && match2)
			{
				stsHdrInstgAgt = incInstid;
				outMsgId = generateStatusHdrMsgId(incInstid);
			}
			else
			{
				stsHdrInstgAgt = fileMmId;
				outMsgId = generateStatusHdrMsgId(fileMmId);
			}

			//____________________________Validate Service Id _rule 012_002_____________________________________//
			/*serviceId = "TEST";*/
			if(!validateServiceId(serviceId, "RCAIN"))
			{
				generateStatusErrorDetailsList("901045", null, hdrErrorType);
				grpHdrSeverity++;
				log.info("******************validateServiceId_002 - Failed.******************");
			}
			else
			{
				log.debug("******************validateServiceId_002 - Passed.******************");
				grpHdrSeverity = grpHdrSeverity+0;
			}

			//____________________________Validate MsgId Creation Date Time_Rule012_007_____________________________________//
			//This does not appear in the Message Layout on IS v3.1
//			if(!isDateValid(msgCreationDate, "yyyyMMdd"))
//			{
//				generateStatusErrorDetailsList("901006", null, hdrErrorType);
//				grpHdrSeverity++;
//				log.info("******************validateDate_Rule009_007 - Failed.******************");
//			}
//			else
//			{
//				msgCreateDate = msgCreationDate;
//				grpHdrSeverity = grpHdrSeverity+0;
//				log.debug("******************validateDate_Rule009_007 - Passed.******************");
//			}

			//____________________________Validate MsgId Creation Date Time_____________________________________//
			if(!isValidProcessingDate(msgCreationDate, "yyyyMMdd"))
			{
				generateStatusErrorDetailsList("902009", null, hdrErrorType);
				grpHdrSeverity++;
				log.info("******************isValidProcessingDate_007 - Failed.******************");
			}
			else
			{
				grpHdrSeverity = grpHdrSeverity+0;
				log.debug("******************isValidProcessingDate_007 - Passed.******************");
			}

			//SALEHAR - 20151207 - This has been removed as per email by MS.				
			//____________________________Validate File Number_Rule012_004(Dependant on BicCode) _____________________________________//
			//				if(bicCodeValid)
			//				{
			//					log.debug("incomingBicCode: "+ incInstid);
			//					String val004 = validateFileNumberingInMsgId(fileNo,incInstid, "pain.012");
			//					//String val004 = validateFileNumberingInMsgId_004(fileNo, memberNo);
			//					log.info("the file no is *********************************************"+fileNo);
			//
			//					if(val004 == "901003")
			//					{
			//						
			//						if(systemType.equalsIgnoreCase(sadcSystem))
			//							populateMdtGrpHdrErrorList("901003", msgId);
			//						else
			//						generateStatusErrorDetailsList("901003", null, hdrErrorType);
			//						grpHdrSeverity++;
			//						log.info("******************validateFileNumber_Rule009_004 - Failed.******************");
			//					}
			//					
			//					if(val004 == "901004")
			//					{
			//						
			//						if(systemType.equalsIgnoreCase(sadcSystem))
			//							populateMdtGrpHdrErrorList("901004", msgId);
			//						else
			//						generateStatusErrorDetailsList("901004", null, hdrErrorType);
			//						grpHdrSeverity++;
			//						log.info("******************validateFileNumber_Rule009_004 - Failed.******************");
			//					}
			//					
			//					if(val004 == "PASSED")
			//					{
			//						grpHdrSeverity = grpHdrSeverity+0;
			//						log.info("******************validateFileNumber_Rule009_004 - Passed.******************");
			//					}
			//				}
			//				else
			//				{
			//					
			//					if(systemType.equalsIgnoreCase(sadcSystem))
			//						populateMdtGrpHdrErrorList("902113", msgId);
			//					else
			//					generateStatusErrorDetailsList("902113", null, hdrErrorType);
			//					grpHdrSeverity++;
			//					log.info("******************validateFileNumber_BIC Invalid - Failed.******************");
			//				}


			//____________________________Validate Message Id Uniqueness_Rule012_006 _____________________________________//

			if(!validateMsgId(msgId))
			{
				generateStatusErrorDetailsList("901005", null, hdrErrorType);
				grpHdrSeverity++;
				log.debug("the message Id is **************************"+msgId);
				log.info("******************validateMsgId_006 - Failed.******************");
			}
			else
			{
				grpHdrSeverity = grpHdrSeverity+0;
				log.debug("******************validateMsgId_006 - Passed.******************");
			}

			//____________________________Validate GrpHdr Creation Date Time_Rule012_008 _____________________________________//
			if(groupHeader.getCreDtTm() != null)
			{
				convCreationDateTime = getCovertDateTime(groupHeader.getCreDtTm());

				if(convCreationDateTime != null)
				{
					String strCreationDate = sdfDateTime.format(convCreationDateTime);
					if(!validateDate(strCreationDate, "yyyy-MM-dd HH:mm:ss"))
					{
						generateStatusErrorDetailsList("901007", null, hdrErrorType);
						grpHdrSeverity++;
						log.info("******************validateDate_008 - Failed.******************");
					}
					else
					{
						grpHdrSeverity = grpHdrSeverity+0;
						log.debug("******************validateDate_008 - Passed.******************");
					}
				}
				else
				{
					generateStatusErrorDetailsList("901007", null, hdrErrorType);
					grpHdrSeverity++;
					log.info("******************validateDate_008 - Failed.******************");

				}
			}
			else
			{
				generateStatusErrorDetailsList("901007", null, hdrErrorType);
				grpHdrSeverity++;
				log.info("******************validateDate_008 - Failed.******************");
			}

			//____________________________Validate Instructing Agent_Rule012_010 _____________________________________//
			String instrgAgt =groupHeader.getInstgAgt().getFinInstnId().getClrSysMmbId().getMmbId(); 
			log.debug("The member id is *****************" + instrgAgt);
			if(!validateDebtorBank(instrgAgt))
			{
				generateStatusErrorDetailsList("901017", null, hdrErrorType);
				grpHdrSeverity++;
				log.info("******************validateInstructingAgent_010 - Failed.******************");
			}
			else
			{
				grpHdrSeverity = grpHdrSeverity+0;
				log.debug("******************validateInstructingAgent_010 - Passed.******************");
			}

			//____________________________Validate Instructed Agent_Rule 012-011 _____________________________________//
			if(groupHeader.getInstdAgt() != null && groupHeader.getInstdAgt().getFinInstnId() != null && groupHeader.getInstdAgt().getFinInstnId().getClrSysMmbId() != null && groupHeader.getInstdAgt().getFinInstnId().getClrSysMmbId().getMmbId() != null)
			{
				String instdAgt = groupHeader.getInstdAgt().getFinInstnId().getClrSysMmbId().getMmbId();
				String achInstId;

				log.debug("the achI d is ******" + mdtSysctrlCompParamEntity.getAchInstId());
				if(mdtSysctrlCompParamEntity != null)
					achInstId = mdtSysctrlCompParamEntity.getAchInstId();
				else
					achInstId = "210000";

				if(!instdAgt.equalsIgnoreCase(achInstId))
				{
					generateStatusErrorDetailsList("901079", null, hdrErrorType);
					grpHdrSeverity++;
					log.info("******************validateInstructedAgent_011 - Failed.******************");
				}
				else
				{
					grpHdrSeverity = grpHdrSeverity+0;
					log.debug("****************** validateInstructedAgent_011- Passed.******************");
				}
			}	
			else
			{
				generateStatusErrorDetailsList("901079", null, hdrErrorType);
				grpHdrSeverity++;
				log.info("******************validateInstructingAgent_011 - Failed.******************");
			}

			if(!validateFileSizeLimit(manacService,instgAgt,inwardFileSize)) {
				generateStatusErrorDetailsList("902206", null, hdrErrorType);
				grpHdrSeverity++;
				log.info("******************validateFileSizeLimit - Failed.******************");
			}else {
				grpHdrSeverity = grpHdrSeverity + 0;
				log.debug("******************validateFileSizeLimit- Passed.******************");
			}


		}//else MsgId Lenght > 34

		log.debug("grpHdrSeverity: "+ grpHdrSeverity);

		if(grpHdrSeverity == 0)
		{
			hdrSystemSeqNo = generateStatusReportGrpHdr(groupHeader, "ACCP", stsHdrInstgAgt);
		}
		else
		{
			log.info(fileName+" failed Group Header Validation.");
			hdrSystemSeqNo = generateStatusReportGrpHdr(groupHeader,  "RJCT", stsHdrInstgAgt);
			saveStatusErrorDetails();
			opsStatusDetailsList.clear();
		}

		CasOpsFileRegEntity casOpsFileRegEntity = (CasOpsFileRegEntity) valBeanRemote.retrieveOpsFileReg(fileName);
		if(casOpsFileRegEntity != null)
		{
			casOpsFileRegEntity.setGrpHdrMsgId(outMsgId);
			valBeanRemote.updateOpsFileReg(casOpsFileRegEntity);
		}

		return grpHdrSeverity;
	}

	public List<?> findDuplicateTxns(List<MandateAcceptance3> mandatesList)
	{
		mandateSeverity = 0;
		log.info("XXXX----FINDING DUPLICATES WITHIN FILE -----XXX");
		HashSet<String> uniqueTxnSet = new HashSet<String>();
		List<MandateAcceptance3> duplicateList = new ArrayList<MandateAcceptance3>();
		List<MandateAcceptance3> uniqueList = new ArrayList<MandateAcceptance3>();

		for (MandateAcceptance3 mandateAccp : mandatesList) {
			Mandate1 mandate = new Mandate1();
			mandate = mandateAccp.getOrgnlMndt().getOrgnlMndt();
			if (mandate.getCdtr() != null && mandate.getCdtr().getId() != null && mandate.getCdtr().getId().getOrgId() != null && mandate.getCdtr().getId().getOrgId().getOthr() != null)  
			{
				if (mandate.getCdtr().getId().getOrgId().getOthr().get(0) != null && mandate.getCdtr().getId().getOrgId().getOthr().get(0).getId() != null) 
				{
					if(!uniqueTxnSet.add(mandate.getCdtr().getId().getOrgId().getOthr().get(0).getId())) {
						log.info("DUPL MRTI ==> "+mandate.getCdtr().getId().getOrgId().getOthr().get(0).getId());
						mandateSeverity++;
						duplicateList.add(mandateAccp);
					}else {
						//						log.info("UNIQ MRTI");						
						uniqueList.add(mandateAccp);
					}			
				}
			}
		}

		//Create Duplicate records
		if(duplicateList != null && duplicateList.size() > 0)
		{
			String mrti = null;
			for (MandateAcceptance3 mandateAccp : duplicateList) {

				Mandate1 mandate = new Mandate1();
				mandate = mandateAccp.getOrgnlMndt().getOrgnlMndt();

				if (mandate.getCdtr() != null && mandate.getCdtr().getId() != null && mandate.getCdtr().getId().getOrgId() != null && mandate.getCdtr().getId().getOrgId().getOthr() != null)  
				{
					if (mandate.getCdtr().getId().getOrgId().getOthr().get(0) != null && mandate.getCdtr().getId().getOrgId().getOthr().get(0).getId() != null) 
					{
						mrti = mandate.getCdtr().getId().getOrgId().getOthr().get(0).getId();
					}
				}

				if (mandate.getUltmtCdtr() != null) 
				{
					// Ultimate Creditor
					if (mandate.getUltmtCdtr().getNm() != null)
					{
						ultCreditor = mandate.getUltmtCdtr().getNm().toString();
					}

					if (mandate.getUltmtCdtr().getId() != null && mandate.getUltmtCdtr().getId().getOrgId() != null && mandate.getUltmtCdtr().getId().getOrgId().getOthr() != null && mandate.getUltmtCdtr().getId().getOrgId().getOthr().size() > 0) 
					{
						if (mandate.getUltmtCdtr().getId().getOrgId().getOthr().get(0) != null && mandate.getUltmtCdtr().getId().getOrgId().getOthr().get(0).getId() != null) 
						{
							abbShortName = mandate.getUltmtCdtr().getId().getOrgId().getOthr().get(0).getId().toString();
						}
					}
				}

				// Populate Debtor Bank
				if (mandate.getDbtrAgt() != null && mandate.getDbtrAgt().getFinInstnId() != null&& mandate.getDbtrAgt().getFinInstnId().getClrSysMmbId() != null&& mandate.getDbtrAgt().getFinInstnId().getClrSysMmbId().getMmbId() != null) 
				{
					String debtorBrnch = mandate.getDbtrAgt().getFinInstnId().getClrSysMmbId().getMmbId();

					SysCisBranchEntity sysCisBranchEntity = (SysCisBranchEntity) valBeanRemote.validateDebtorBranchNo(debtorBrnch, "Y");

					if (sysCisBranchEntity != null) 
					{
						debtorBank = sysCisBranchEntity.getMemberNo();
						debtorBranchNo = mandate.getDbtrAgt().getFinInstnId().getClrSysMmbId().getMmbId();
					}
					else {
						debtorBranchNo = debtorBrnch;
					}
				}
				//Generate Duplicate Error
				generateStatusErrorDetailsList("902205", mrti, txnErrorType);
			}
			saveStatusErrorDetails();
		}
		return uniqueList;
	}

	public int validateMandate(Mandate1 mandate, AcceptanceResult6 accptResult, SupplementaryData1 supplementaryData1)
	{
		ultCreditor = null; abbShortName = null;
		mandateSeverity = 0;

		if(mandate.getCdtr() != null && mandate.getCdtr().getId() != null && mandate.getCdtr().getId().getOrgId() != null && mandate.getCdtr().getId().getOrgId().getOthr() != null)
		{
			if(mandate.getCdtr().getId().getOrgId().getOthr().get(0) != null && mandate.getCdtr().getId().getOrgId().getOthr().get(0).getId() != null)
			{
				mndtReqTransId = mandate.getCdtr().getId().getOrgId().getOthr().get(0).getId();
				log.info("=== <VALIDATING PAIN.012> " + mndtReqTransId + " ===");
			}
		}

		if(mandate.getUltmtCdtr() != null)
		{
			//Ultimate Creditor
			if(mandate.getUltmtCdtr().getNm() != null)
			{
				ultCreditor = mandate.getUltmtCdtr().getNm().toString();
			}

			if(mandate.getUltmtCdtr().getId() != null && mandate.getUltmtCdtr().getId().getOrgId() != null && mandate.getUltmtCdtr().getId().getOrgId().getOthr() != null&& mandate.getUltmtCdtr().getId().getOrgId().getOthr().size() > 0)
			{
				if(mandate.getUltmtCdtr().getId().getOrgId().getOthr().get(0) != null && mandate.getUltmtCdtr().getId().getOrgId().getOthr().get(0).getId() != null)
				{
					abbShortName = mandate.getUltmtCdtr().getId().getOrgId().getOthr().get(0).getId().toString();
				}
			}
		}

		//Populate Creditor Bank
		if(mandate.getCdtrAgt() != null && mandate.getCdtrAgt().getFinInstnId() != null && mandate.getCdtrAgt().getFinInstnId().getClrSysMmbId() != null && mandate.getCdtrAgt().getFinInstnId().getClrSysMmbId().getMmbId() != null)
		{
			String creditorBrnch = mandate.getCdtrAgt().getFinInstnId().getClrSysMmbId().getMmbId();

			SysCisBranchEntity sysCisBranchEntity = (SysCisBranchEntity) valBeanRemote.validateCreditorBranchNo(creditorBrnch,"Y");

			if(sysCisBranchEntity != null)
			{
				creditorBank = sysCisBranchEntity.getMemberNo();
			}
		}


		if(supplementaryData1 != null && supplementaryData1.getEnvlp()!= null && supplementaryData1.getEnvlp().getCnts()!= null && supplementaryData1.getEnvlp().getCnts().getMndtRfNbr()!= null)
		{
			trimmedManRfNo = supplementaryData1.getEnvlp().getCnts().getMndtRfNbr().toString();
		}else {
			trimmedManRfNo = null;
		}

		if(mandate.getDbtrAgt() != null && mandate.getDbtrAgt().getFinInstnId() != null && mandate.getDbtrAgt().getFinInstnId().getClrSysMmbId() != null && mandate.getDbtrAgt().getFinInstnId().getClrSysMmbId().getMmbId() != null)
		{
			debtorBranchNo = mandate.getDbtrAgt().getFinInstnId().getClrSysMmbId().getMmbId();
		}

		if(mandate.getUltmtCdtr() != null && mandate.getUltmtCdtr().getId() != null && mandate.getUltmtCdtr().getId().getOrgId() != null && mandate.getUltmtCdtr().getId().getOrgId().getOthr() != null)
		{
			if(mandate.getUltmtCdtr().getId().getOrgId().getOthr().get(0) != null && mandate.getUltmtCdtr().getId().getOrgId().getOthr().get(0).getId() != null)
			{
				trimCredAbbName = mandate.getUltmtCdtr().getId().getOrgId().getOthr().get(0).getId().trim();
			}
		}


		if(mandate.getMndtReqId()!= null)
		{
			mandateReqId = mandate.getMndtReqId();
			log.debug("mandateReqId: "+mandateReqId);
		}

		// ADDED 0999 & 0998 FOR MIGRATION on 2018/09/11 by DimakatsoN
		//____________________________Validate Accept Ind _rule 012_013_____________________________________//
		if(mandate!= null && mandate.getTp() != null && mandate.getTp().getLclInstrm() != null && mandate.getTp().getLclInstrm().getPrtry() != null/* && casOpsCessAssignTxnsEntityOriginal!= null && casOpsCessAssignTxnsEntityOriginal.getLocalInstrCd() != null*/)
		{
			String lclInstCd = mandate.getTp().getLclInstrm().getPrtry();
			//			log.info("Local Instrument"+lclInstCd);

			if(lclInstCd.equalsIgnoreCase("0999")|| lclInstCd.equalsIgnoreCase("0998"))
			{
				if(accptResult != null)
				{
					//					log.info("Accptd Ind"+String.valueOf(accptResult.isAccptd()));

					if(accptResult.isAccptd() == true)
					{
						mandateSeverity = mandateSeverity+0;
						log.debug("******************validateAcceptIndicatorAEDO/NAEDO_013 - Passed.******************");
					}
					else 
					{
						generateStatusErrorDetailsList("901087", mndtReqTransId, txnErrorType);
						mandateSeverity++;
						log.info("******************validateAcceptIndicatorAEDO/NAEDO_013 - Failed.******************");
					}
				}
			}
			else
			{
				log.debug("The Local inst code is NOT for Migration!");
			}
		}
		else 
		{
			if(accptResult != null)
			{
				if(accptResult.isAccptd() !=  true && accptResult.isAccptd()  != false )
				{
					generateStatusErrorDetailsList("901087", mndtReqTransId, txnErrorType);
					mandateSeverity++;
					log.info("******************validateAcceptIndicator_013 - Failed.******************");
				}
				else
				{
					mandateSeverity = mandateSeverity+0;
					log.debug("******************validateAcceptIndicator_013 - Passed.******************");
				}
			}
			else
			{
				generateStatusErrorDetailsList("901087", mndtReqTransId, txnErrorType);
				mandateSeverity++;
				log.info("******************validateAcceptIndicator_013 - Failed.******************");
			}	
		}

		//____________________________Validate RejectReason Code _rule 012_014_____________________________________//
		if(accptResult.getRjctRsn()!= null && accptResult.getRjctRsn().getPrtry()!=null)		
		{
			if(!validateRejectReasonCode(accptResult.getRjctRsn().getPrtry().toString()))
			{
				generateStatusErrorDetailsList("901086", mndtReqTransId, txnErrorType);
				mandateSeverity++;
				log.info("******************validateRejectReasonCode012_014 - Failed.******************");
			}
			else
			{
				mandateSeverity = mandateSeverity+0;
				log.debug("******************validateRejectReasonCode012_014 - Passed.******************");
			}
		}
		//This field is optional in TRS 15




		if(mandate.getMndtId() != null)
		{
			if(!validateRule999(mandate.getMndtId()))
			{
				generateStatusErrorDetailsList("910099", mndtReqTransId, txnErrorType);
				mandateSeverity++;
				log.info("******************validateMndtIdRule999 - Failed.******************");
			}
			else
			{
				mandateSeverity = mandateSeverity+0;
				log.debug("******************validateMndtIdRule999 - Passed.******************");
			}
		}
		else
		{
			generateStatusErrorDetailsList("910099", mndtReqTransId, txnErrorType);
			mandateSeverity++;
			log.info("******************validateMndtIdRule999 - Failed.******************");
		}

		//07/12/2017-DimakatsoN-Remove validation as per SCR 225v4
		//____________________________Validate Contract Reference_012_014_____________________________________//

		//if(contractNum != null && mndtReqTransId != null)
		if(mndtReqTransId != null)
		{	
			CasOpsCessionAssignEntity matchedMandate = matchPain012(mndtReqTransId);

			if(matchedMandate != null)
			{
				String processStatus = matchedMandate.getProcessStatus();
				//				log.info("processStatus : "+processStatus);
				//2017-02-13-SalehaR-Remove extract status as per SCR
				if(/*processStatus.equalsIgnoreCase(extractStatus) ||*/ processStatus.equalsIgnoreCase(responseRecStatus))
				{
					origServiceId = matchedMandate.getServiceId();
					log.debug("******************Validate Contract Ref_015 - PASSED.******************");
					mandateSeverity = mandateSeverity+0;
					int mandateSev = validateAgainstOriginalMandate(mandate, supplementaryData1, accptResult.isAccptd());
					mandateSeverity = mandateSeverity + mandateSev;
				}
				else
				{
					if(processStatus.equalsIgnoreCase(rejectedStatus))
					{
						generateStatusErrorDetailsList("902028", mndtReqTransId, txnErrorType);
						mandateSeverity++;
						log.info("******************Matching Failed_015-Previously Rejected - FAILED.******************");
					}
					else
					{
						if(processStatus.equalsIgnoreCase(matchedStatus))
						{
							generateStatusErrorDetailsList("902028", mndtReqTransId, txnErrorType);
							mandateSeverity++;
							log.info("******************Matching Failed_015-Previously Matched - FAILED.******************");
						}
						else
						{
							if(processStatus.equalsIgnoreCase(extractStatus))
							{
								generateStatusErrorDetailsList("901095", mndtReqTransId, txnErrorType);
								mandateSeverity++;
								log.info("******************Matching Failed_015-In Extract Status - FAILED.******************");
							}
							else
							{
								generateStatusErrorDetailsList("901185", mndtReqTransId, txnErrorType);
								mandateSeverity++;
								log.info("******************Matching Failed_015-No Match Found - FAILED.******************");
							}
						}
					}
				}
			}
			else
			{
				//generateStatusErrorDetailsList("901093", mndtReqTransId, txnErrorType);
				generateStatusErrorDetailsList("901185", mndtReqTransId, txnErrorType);
				mandateSeverity++;
				log.info("******************Matching Failed_015-No Match Found - FAILED.******************");
			}

			//2016/11/12-SalehaR-Allow for Pain.012 before ST101					
			//					if(!matchPain012(contractNum, mndtReqTransId))
			//					{
			//						if(systemType.equalsIgnoreCase(sadcSystem))
			//							populateMdtErrorList("901093",msgId,mandate.getMndtId());
			//						else
			//						{
			//							generateStatusErrorDetailsList("901093", mndtReqTransId, txnErrorType);
			//						}
			//						mandateSeverity++;
			//						log.info("******************Matching Failed_015 - FAILED.******************");
			//					}
			//					else
			//					{
			//						
			//						//SalehaR 2015/11/12 - Retrieve the rest of the mandate for the other rules//
			//						String orgnlMsgId = mdtAcOpsMndtMsgEntityOriginal.getMdtAcOpsMndtMsgPK().getMsgId();
			//						origServiceId = mdtAcOpsMndtMsgEntityOriginal.getServiceId();
			//
			//						System.out.println("origServiceId---->"+origServiceId);
			//
			//						boolean retMandate = retrieveOriginalMandate(orgnlMsgId, mndtReqTransId);
			//						System.out.println("retMandate---->"+retMandate);
			//						if(retMandate)
			//						{
			//							log.info("******************Validate Contract Ref_015 - PASSED.******************");
			//							mandateSeverity = mandateSeverity+0;
			//							int mandateSev = validateAgainstOriginalMandate(mandate, supplementaryData1);
			//							mandateSeverity = mandateSeverity + mandateSev;
			//						}
			//						else
			//						{
			//							if(systemType.equalsIgnoreCase(sadcSystem))
			//								populateMdtErrorList("901185",msgId,mandate.getMndtId());
			//							else
			//							{
			//								generateStatusErrorDetailsList("901185", mndtReqTransId, txnErrorType);
			//							}
			//							mandateSeverity++;

			//							log.info("******************match mandateReqTranId_012.049 - FAILED.******************");
			//						}
			//					}
		}
		else
		{
			if(origServiceId.equalsIgnoreCase("MANIN"))
			{
				generateStatusErrorDetailsList("901144", mndtReqTransId, txnErrorType);
				mandateSeverity++;
				log.info("******************validate Contract Ref_015 - FAILED.******************");
			}
			//This is optional for CARIN
		}

		if(mandateSeverity == 0)
		{
			log.debug("Mandate: +"+mndtReqTransId+" has passed validation.......");
		}
		else
		{
			saveStatusErrorDetails();
			log.info("Mandate: +"+mndtReqTransId+" has failed validation.......");
		}

		log.debug("MANDATE SEVERITY--->>> "+mandateSeverity);
		return mandateSeverity;
	}

	public int validateAgainstOriginalMandate(Mandate1 mandate, SupplementaryData1 supplementaryData1, boolean acceptedResult)
	{
		/*
		 * 07/12/2017 -DimakatsoN - Removed as per SRC 225v4
		//____________________________Validate Tracking Ind _rule 009_016_____________________________________//
		if(mandate.getTp() != null && mandate.getTp().getSvcLvl() != null && mandate.getTp().getSvcLvl().getPrtry() != null)
		{
			if(!validateTrackingIndicator(mandate.getTp().getSvcLvl().getPrtry()))
			{
				if(systemType.equalsIgnoreCase(sadcSystem))
					populateMdtErrorList("901100",msgId,mandate.getMndtId());
				else{
					generateStatusErrorDetailsList("901100", mndtReqTransId, txnErrorType);
				}
				mandateSeverity++;
				log.info("******************validateTrackingInd_016 - Failed.******************");
			}
			else
			{
				mandateSeverity = mandateSeverity+0;
				log.debug("******************validateTrackingInd_016 - Passed.******************");
			}
		}
		else
		{
			if(origServiceId.equalsIgnoreCase("MANIN") || origServiceId.equalsIgnoreCase("MANCN"))
			{
				if(systemType.equalsIgnoreCase(sadcSystem))
					populateMdtErrorList("901100",msgId,mandate.getMndtId());
				else{
					generateStatusErrorDetailsList("901100", mndtReqTransId, txnErrorType);
				}
				mandateSeverity++;
				log.info("******************validateTrackingInd_016- Failed.******************");
			}
			//This if field is optional in PAIN.012 for CARIN
		}*/
		// Commented out as per EMC 116383


		/*		
		 * 07/12/2017 -DimakatsoN - Removed as per SCR 225v4
		//____________________________Validate Debtor Authentication _rule 012_017_____________________________________//
		String origServiceId = mdtAcOpsMndtMsgEntityOriginal.getServiceId();

		if(!(origServiceId.equalsIgnoreCase("MANCN")))
		{
			if(mandate.getTp() != null && mandate.getTp().getLclInstrm() != null && mandate.getTp().getLclInstrm().getPrtry() != null && casOpsCessAssignTxnsEntityOriginal!= null && casOpsCessAssignTxnsEntityOriginal.getLocalInstrCd() != null)
			{
				log.debug("PAIN.012 LclInstCd: "+mandate.getTp().getLclInstrm().getPrtry().toString());
				log.debug("MANIN DB LclInsCd: "+casOpsCessAssignTxnsEntityOriginal.getLocalInstrCd());
				if(casOpsCessAssignTxnsEntityOriginal.getLocalInstrCd().equalsIgnoreCase(mandate.getTp().getLclInstrm().getPrtry().toString()))
				{
					mandateSeverity = mandateSeverity+0;
					log.debug("******************validateDebtorAuthCode_017 - Passed.******************");
				}
				else
				{
					if(systemType.equalsIgnoreCase(sadcSystem))
						populateMdtErrorList("901101",msgId,mandate.getMndtId());
					else
						generateStatusErrorDetailsList("901101", mndtReqTransId, txnErrorType);
					mandateSeverity++;
					log.info("******************validateDebtorAuthCode_017 - Failed.******************");
				}
			}
			else
			{
				if(systemType.equalsIgnoreCase(sadcSystem))
					populateMdtErrorList("901101",msgId,mandate.getMndtId());
				else
					generateStatusErrorDetailsList("901101", mndtReqTransId, txnErrorType);
				mandateSeverity++;
				log.info("******************validateDebtorAuthCode_017 - Failed.******************");
			}		
		}//This rule does not apply to MANCN as per TRS 15
		 */
		//____________________________Validate Sequence Type _rule 012_018_____________________________________//

		if(origServiceId.equalsIgnoreCase("MANIN"))
		{
			if(mandate.getOcrncs() != null && mandate.getOcrncs().getSeqTp() != null && casOpsCessAssignTxnsEntityOriginal != null && casOpsCessAssignTxnsEntityOriginal.getSequenceType() != null)
			{
				if(casOpsCessAssignTxnsEntityOriginal.getSequenceType().equalsIgnoreCase(mandate.getOcrncs().getSeqTp().toString()))
				{
					mandateSeverity = mandateSeverity+0;
					log.debug("******************validateInstallmentOccurrence_012_018 - Passed.******************");
				}
				else
				{
					generateStatusErrorDetailsList("901102", mndtReqTransId, txnErrorType);
					mandateSeverity++;
					log.info("******************validateInstallmentOccurrence_012_018 - Failed.******************");
				}
			}
			else
			{
				generateStatusErrorDetailsList("901102", mndtReqTransId, txnErrorType);
				mandateSeverity++;
				log.info("******************validateInstallmentOccurrence_012_018 - Failed.******************");
			}
		}

		if(origServiceId.equalsIgnoreCase("CARIN"))
		{
			//____________________________Validate Sequence Type_012.999_____________________________________//
			if(mandate.getOcrncs() != null && mandate.getOcrncs().getSeqTp() != null)
			{
				if(validateRule999(mandate.getOcrncs().getSeqTp().toString()))
				{
					mandateSeverity = mandateSeverity+0;
					log.debug("******************validateSeqType_012.999 - Passed.******************");
				}
				else
				{
					generateStatusErrorDetailsList("910099", mndtReqTransId, txnErrorType);
					mandateSeverity++;
					log.info("******************validateSeqType_012.999 - Failed.******************");
				}
			}
			//This field is optional in TRS 15
		}

		/*
		 * 07/12/2017 -Dimakatson- Removed as per SCR 225v4
		//____________________________Validate Frequency Code _rule 012_019_____________________________________//
		if(mandate.getOcrncs() != null && mandate.getOcrncs().getFrqcy() != null && mandate.getOcrncs().getFrqcy() !=null && casOpsCessAssignTxnsEntityOriginal!= null && casOpsCessAssignTxnsEntityOriginal.getFrequency() != null)		
		{
			if(casOpsCessAssignTxnsEntityOriginal.getFrequency().equalsIgnoreCase(mandate.getOcrncs().getFrqcy().toString()))
			{
				mandateSeverity = mandateSeverity+0;
				log.debug("******************validateInstallmentFrequency_012_019 - Passed.******************");

			}
			else
			{
				if(systemType.equalsIgnoreCase(sadcSystem))
					populateMdtErrorList("901103",msgId,mandate.getMndtId());
				else
					generateStatusErrorDetailsList("901103", mndtReqTransId, txnErrorType);
				mandateSeverity++;
				log.info("******************validateInstallmentFrequency_012_019 - Failed.******************");
			}
		}
		else
		{
			if(origServiceId.equalsIgnoreCase("MANIN"))
			{
				if(systemType.equalsIgnoreCase(sadcSystem))
					populateMdtErrorList("901103",msgId,mandate.getMndtId());
				else
					generateStatusErrorDetailsList("901103", mndtReqTransId, txnErrorType);
				mandateSeverity++;
				log.info("******************validateInstallmentFrequency_012_019 - Failed.******************");
			}
			//This field is optional for pain.012 for CARIN
		}

		 */
		/*
		 * 07/12/2017 -DimakatsoN - Removed as per SRC225v4
		Date fromDate = null;
		//____________________________Validate From Date _rule 012_020_____________________________________//
		//2016-04-06 - SALEHAR - This validation has been removed for Pain.011 as per MS email
		//			if(!(origServiceId.equalsIgnoreCase("MANCN")))
		//			{
		if(mandate.getOcrncs()!= null && mandate.getOcrncs().getDrtn() != null && mandate.getOcrncs().getDrtn().getFrDt()!=null &&casOpsCessAssignTxnsEntityOriginal!= null && casOpsCessAssignTxnsEntityOriginal.getFromDate() != null)
		{
			fromDate = getCovertDateTime(mandate.getOcrncs().getDrtn().getFrDt());
			String strPain012FromDate = sdf.format(fromDate);
			String strOrgnlMsgFromDate = sdf.format(casOpsCessAssignTxnsEntityOriginal.getFromDate());
			if(strPain012FromDate.equalsIgnoreCase(strOrgnlMsgFromDate))
			{
				log.debug("******************validateDate_rule_012_020 - Passed.******************");
				mandateSeverity = mandateSeverity+0;	
			}
			else
			{
				if(systemType.equalsIgnoreCase(sadcSystem))
					populateMdtErrorList("901104",msgId,mandate.getMndtId());
				else
					generateStatusErrorDetailsList("901104", mndtReqTransId, txnErrorType);
				mandateSeverity++;
				log.info("******************validateDate_rule_012_020 - Failed.******************");
			}
		}
		else
		{
			if(origServiceId.equalsIgnoreCase("MANIN"))
			{
				if(systemType.equalsIgnoreCase(sadcSystem))
					populateMdtErrorList("901104",msgId,mandate.getMndtId());
				else
					generateStatusErrorDetailsList("901104", mndtReqTransId, txnErrorType);
				mandateSeverity++;
				log.info("******************validateDate_rule_012_020 - Failed.******************");
			}
		}
		//			}
		 */


		//07/12/2017 -DimakatsoN -Removed as per SCR 225v4
		/*	Date firstDate = null;
		//____________________________Validate First Collection Date _rule 012_021_____________________________________//
		if(mandate.getOcrncs()!= null && mandate.getOcrncs().getFrstColltnDt() != null && casOpsCessAssignTxnsEntityOriginal!= null && casOpsCessAssignTxnsEntityOriginal.getFirstCollDate() != null)
		{
			firstDate = getCovertDateTime(mandate.getOcrncs().getFrstColltnDt());
			String strPain012FirstDate = sdf.format(firstDate);
			String strOrgnlMsgFirstDate = sdf.format(casOpsCessAssignTxnsEntityOriginal.getFirstCollDate());

			if(strPain012FirstDate.equalsIgnoreCase(strOrgnlMsgFirstDate))
			{
				mandateSeverity = mandateSeverity+0;
				log.debug("******************validateFirstCollectionDate_rule_012_021 - Passed.******************");
			}
			else
			{
				if(systemType.equalsIgnoreCase(sadcSystem))
					populateMdtErrorList("901106",msgId,mandate.getMndtId());
				else
					generateStatusErrorDetailsList("901106", mndtReqTransId, txnErrorType);
				mandateSeverity++;
				log.info("******************validateFirstCollectionDate_rule_012_021 - Failed.******************");

			}
		}
		//This is optional in TRS 15
		 */

		/*// ADDED 0999 & 0998 FOR MIGRATION on 2018/09/11 by DimakatsoN
		//____________________________Validate First Collection Date _rule 012_073_____________________________________//

		Date firstDate = null;

		if (mandate.getOcrncs() != null && mandate.getOcrncs().getFrstColltnDt() != null) 
		{
			String lclInstCd = mandate.getTp().getLclInstrm().getPrtry();
			firstDate = getCovertDateTime(mandate.getOcrncs().getFrstColltnDt());
			String strFirstDate = sdf.format(firstDate);

			if(lclInstCd !=null)
			{
				if(lclInstCd.equalsIgnoreCase("0999")||lclInstCd.equalsIgnoreCase("0998"))
				{

					if (strFirstDate== null || strFirstDate.isEmpty() |strFirstDate.length() == 0) 
					{
						mandateSeverity = mandateSeverity + 0;
						log.info("******************validateFirstCollectionDate_rule_NAEDO/AEDO012_073 - Passed.******************");
					}
					else
					{
						generateStatusErrorDetailsList("902421", mndtReqTransId, txnErrorType);
						mandateSeverity++;
						log.info("******************validateFirstCollectionDate_rule_NAEDO/AEDO012_073 - Failed.******************");
					}
				}
			}
		}*/
		/*
		 *  07/12/2017 - DimakatsoN- Removed as per SRC 225v4

		//This field is not validated for MANCN as per TRS 15
		if(!(origServiceId.equalsIgnoreCase("MANCN")))
		{
			if(supplementaryData1 != null && supplementaryData1.getEnvlp() != null && supplementaryData1.getEnvlp().getCnts() != null && 
					supplementaryData1.getEnvlp().getCnts().getDbVlTp() != null)
			{
				if(supplementaryData1.getEnvlp().getCnts().getDbVlTp().equalsIgnoreCase("FIXED") || supplementaryData1.getEnvlp().getCnts().getDbVlTp().equalsIgnoreCase("VARIABLE"))
				{
					//____________________________Validate Installment Amount Currency _ Rule 012.051______________________________________//

					if(mandate.getColltnAmt() != null && mandate.getColltnAmt().getCcy() != null)
					{
						if(validateCurrency(mandate.getColltnAmt().getCcy().trim()))
						{
							mandateSeverity = mandateSeverity+0;
							log.debug("******************validatecurrency_012_051- PASSED.******************");
						}
						else
						{
							if(systemType.equalsIgnoreCase(sadcSystem))
								populateMdtErrorList("901198",msgId,mandate.getMndtId());
							else
								generateStatusErrorDetailsList("901198", mndtReqTransId, txnErrorType);
							mandateSeverity++;
							log.info("******************validatecurrency_012_051- FAILED.******************");
						}
					}
					else
					{
						if(systemType.equalsIgnoreCase(sadcSystem))
							populateMdtErrorList("901198",msgId,mandate.getMndtId());
						else
							generateStatusErrorDetailsList("901198", mndtReqTransId, txnErrorType);
						mandateSeverity++;
						log.info("******************validatecurrency_012_051- FAILED.******************");
					}


				 // 07/12/2017 - DimakatsoN- Removed as per SRC 225v4
					//____________________________Validate Installment Amount_rule 012_022______________________________________//
					if(mandate.getColltnAmt() != null && mandate.getColltnAmt().getValue() != null && casOpsCessAssignTxnsEntityOriginal!= null && casOpsCessAssignTxnsEntityOriginal.getCollAmount() != null)
					{
						if(mandate.getColltnAmt().getValue().compareTo(casOpsCessAssignTxnsEntityOriginal.getCollAmount()) == 0)
						{
							mandateSeverity = mandateSeverity+0;
							log.debug("******************validateInitialAmount_012_022- PASSED.******************");
						}
						else
						{
							if(systemType.equalsIgnoreCase(sadcSystem))
								populateMdtErrorList("901108",msgId,mandate.getMndtId());
							else
								generateStatusErrorDetailsList("901108", mndtReqTransId, txnErrorType);
							mandateSeverity++;
							log.info("******************validateInitialAmount_012_021- FAILED.******************");
						}
					}
					else
					{
						if(systemType.equalsIgnoreCase(sadcSystem))
							populateMdtErrorList("901108",msgId,mandate.getMndtId());
						else
							generateStatusErrorDetailsList("901108", mndtReqTransId, txnErrorType);
						mandateSeverity++;
						log.info("******************validateInitialAmount_012_021- FAILED.******************");
					}
				}
			}	

	}
		 */

		/*
		 * 07/12/2017 -DimakatsoN -Removed as per SCR 225v4

		//____________________________Validate Maximum Amount Currency _ Rule 012.051______________________________________//

		if(mandate.getMaxAmt() != null && mandate.getMaxAmt().getCcy() != null)
		{
			if(validateCurrency(mandate.getMaxAmt().getCcy().trim()))
			{
				mandateSeverity = mandateSeverity+0;
				log.debug("******************validatecurrency_012_051- PASSED.******************");
			}
			else
			{
				if(systemType.equalsIgnoreCase(sadcSystem))
					populateMdtErrorList("901198",msgId,mandate.getMndtId());
				else
					generateStatusErrorDetailsList("901198", mndtReqTransId, txnErrorType);
				mandateSeverity++;
				log.info("******************validateMaxcurrency_012_051- FAILED.******************");
			}
		}
		else
		{
			if(origServiceId.equalsIgnoreCase("MANIN"))
			{
				if(systemType.equalsIgnoreCase(sadcSystem))
					populateMdtErrorList("901198",msgId,mandate.getMndtId());
				else
					generateStatusErrorDetailsList("901198", mndtReqTransId, txnErrorType);
				mandateSeverity++;
				log.info("******************validateMaxcurrency_012_051- FAILED.******************");
			}
		}
		 */

		/*
		 * 		07/12/2017 -DimakatsoN -Removed as per SCR225v4
		//____________________________Validate Maximum Amount 012_023_____________________________________//
		if(!(origServiceId.equalsIgnoreCase("MANCN")))
			{
		if(mandate.getMaxAmt() != null && mandate.getMaxAmt().getValue() != null && casOpsCessAssignTxnsEntityOriginal != null && casOpsCessAssignTxnsEntityOriginal.getMaxAmount() != null)
		{
			if(mandate.getMaxAmt().getValue().compareTo(casOpsCessAssignTxnsEntityOriginal.getMaxAmount()) == 0)
			{
				mandateSeverity = mandateSeverity+0;
				log.debug("******************validateMaxAmt_012_023_ - PASSED.******************");
			}
			else
			{
				if(systemType.equalsIgnoreCase(sadcSystem))
					populateMdtErrorList("901112",msgId,mandate.getMndtId());
				else
					generateStatusErrorDetailsList("901112", mndtReqTransId, txnErrorType);
				mandateSeverity++;
				log.info("******************validateMaxAmt_012_023 - FAILED.******************");
			}
		}
		else
		{
			if(origServiceId.equalsIgnoreCase("MANIN"))
			{
				if(systemType.equalsIgnoreCase(sadcSystem))
					populateMdtErrorList("901112",msgId,mandate.getMndtId());
				else
					generateStatusErrorDetailsList("901112", mndtReqTransId, txnErrorType);
				mandateSeverity++;
				log.info("******************validateMaxAmt_012_022_ - FAILED.******************");
			}
		}
		//}
		 */
		//_________________validate Creditor Name_rule010.999____________________________________//
		//2018-03-13-SalehaR-MANIN rule has been removed from TRS17
		if(/*origServiceId.equalsIgnoreCase("MANIN") ||*/ origServiceId.equalsIgnoreCase("MANCN"))
		{
			if(mandate.getCdtr() != null && mandate.getCdtr().getNm() != null)
			{
				if(validateRule999(mandate.getCdtr().getNm()))
				{
					mandateSeverity = mandateSeverity+0;
					log.debug("******************validateCdtrNm_012.999 - PASSED.******************");
				}
				else
				{
					generateStatusErrorDetailsList("910099", mndtReqTransId, txnErrorType);
					mandateSeverity++;
					log.info("******************validateCdtrNm_012.999 - FAILED.******************");
				}
			}
			else
			{
				generateStatusErrorDetailsList("910099", mndtReqTransId, txnErrorType);
				mandateSeverity++;
				log.info("******************validateCdtrNm_012.999 - FAILED.******************");
			}
		}
		//This field is optional in pain.012 for CARIN


		if(validateRule999(mndtReqTransId))
		{
			mandateSeverity = mandateSeverity+0;
			log.debug("******************validateMandReqTranId_012.046 - PASSED.******************");
		}
		else
		{
			generateStatusErrorDetailsList("901163", mndtReqTransId, txnErrorType);
			mandateSeverity++;
			log.info("******************validateMandReqTranId_012.046 - FAILED.******************");
		}

		if(mndtReqTransId != null && casOpsCessAssignTxnsEntityOriginal.getCasOpsCessionAssignEntityPK().getMandateReqTranId() != null)
		{
			if(mndtReqTransId.equalsIgnoreCase(casOpsCessAssignTxnsEntityOriginal.getCasOpsCessionAssignEntityPK().getMandateReqTranId()))
			{
				mandateSeverity = mandateSeverity+0;
				log.debug("******************validateMandReqTranId_012.049 - PASSED.******************");
			}
			else
			{
				generateStatusErrorDetailsList("901185", mndtReqTransId, txnErrorType);
				mandateSeverity++;
				log.info("******************validateMandReqTranId_012.049 - FAILED.******************");
			}
		}
		else
		{
			generateStatusErrorDetailsList("901185", mndtReqTransId, txnErrorType);
			mandateSeverity++;
			log.info("******************validateMandReqTranId_012.049 - FAILED.******************");
		}

		/*
		 * 07/12/2017 -DimakatsoN -Removed as per SCR 225v4
		//____________________________Validate  Creditor Telephone Num _rule 012_042_____________________________________//
		if(mandate.getCdtr() != null && mandate.getCdtr().getCtctDtls() != null && mandate.getCdtr().getCtctDtls().getPhneNb() != null && creditorEntity != null && creditorEntity.getPhoneNr() != null)
		{
			if(mandate.getCdtr().getCtctDtls().getPhneNb().trim().equalsIgnoreCase(creditorEntity.getPhoneNr().trim()))
			{
				log.debug("******************validateCreditorTelNo_012_042 - Passed.******************");
				mandateSeverity = mandateSeverity+0;
			}
			else
			{
				if(systemType.equalsIgnoreCase(sadcSystem))
					populateMdtErrorList("901083",msgId,mandate.getMndtId());
				else
					generateStatusErrorDetailsList("901083", mndtReqTransId, txnErrorType);
				mandateSeverity++;
				log.info("******************validateCreditorTelNo_012_042 - Failed.******************");
			}
		}
		//This is optional in TRS 15
		 */

		/*
		 * 17/12/2017 -DimakatsoN -Removed as per SCR 225v4
		//____________________________Validate  Creditor Account Number_rule 012_025_____________________________________//
		if(mandate.getCdtrAcct() != null && mandate.getCdtrAcct().getId() != null && mandate.getCdtrAcct().getId().getOthr() != null && mandate.getCdtrAcct().getId().getOthr().getId() != null
				&& creditorCashAcc != null && creditorCashAcc.getAccountNumber() != null)
		{

			BigDecimal cdtrAcct=new BigDecimal(mandate.getCdtrAcct().getId().getOthr().getId());
			BigDecimal accNumber = new BigDecimal(creditorCashAcc.getAccountNumber());
			log.debug("cdtrAcct----------->"+cdtrAcct);
			log.debug("accNumber----------->"+accNumber);
			if(cdtrAcct.equals(accNumber))
			{
				log.debug("******************validateCreditorAccountNo_012_025 - Passed.******************");
				mandateSeverity = mandateSeverity+0;
			}
			else
			{
				if(systemType.equalsIgnoreCase(sadcSystem))
					populateMdtErrorList("901114",msgId,mandate.getMndtId());
				else
					generateStatusErrorDetailsList("901114", mndtReqTransId, txnErrorType);
				mandateSeverity++;
				log.info("******************validateCreditorAccountNo_012_025 - Failed.******************");
			}
		}
		else
		{
			if(origServiceId.equalsIgnoreCase("MANIN"))
			{
				generateStatusErrorDetailsList("901114", mndtReqTransId, txnErrorType);
				mandateSeverity++;
				log.info("******************validateCreditorAccountNo_012_025 - Failed.******************");
			}
			//This field is optional in pain.012 for CARIN/MANCN
		}
		 */
		//____________________________Validate  Creditor Branch Number_rule 012_024_____________________________________//
		if(mandate.getCdtrAgt() != null && mandate.getCdtrAgt().getFinInstnId() != null && mandate.getCdtrAgt().getFinInstnId().getClrSysMmbId() != null && 
				mandate.getCdtrAgt().getFinInstnId().getClrSysMmbId().getMmbId() != null && casOpsCessAssignTxnsEntityOriginal != null && casOpsCessAssignTxnsEntityOriginal.getCredBranchNr() != null)
		{
			if(mandate.getCdtrAgt().getFinInstnId().getClrSysMmbId().getMmbId().trim().equalsIgnoreCase(casOpsCessAssignTxnsEntityOriginal.getCredBranchNr().trim()))
			{
				log.debug("******************validateCreditorBranchNo_012_024 - Passed.******************");
				mandateSeverity = mandateSeverity+0;
			}
			else
			{
				generateStatusErrorDetailsList("901113", mndtReqTransId, txnErrorType);
				mandateSeverity++;
				log.info("******************validateCreditorBranchNo_012_024 - Failed.******************");
			}
		}
		else
		{
			if(origServiceId.equalsIgnoreCase("MANIN"))
			{
				generateStatusErrorDetailsList("901113", mndtReqTransId, txnErrorType);
				mandateSeverity++;
				log.info("******************validateCreditorBranchNo_012_024 - Failed.******************");
			}
			//This field is optional in pain.012 for CARIN/MANCN
		}

		/*
		 * 07/12/2017 -DimakatsoN -Removed as per SCR 225v4
		//____________________________Validate Creditor Abb ShortName_rule 012_047______________________________________//
		if(mandate.getUltmtCdtr() != null && mandate.getUltmtCdtr().getId() != null && mandate.getUltmtCdtr().getId().getOrgId() != null && mandate.getUltmtCdtr().getId().getOrgId().getOthr() != null)
		{
			if(mandate.getUltmtCdtr().getId().getOrgId().getOthr().get(0) != null && mandate.getUltmtCdtr().getId().getOrgId().getOthr().get(0).getId() != null)
			{
				trimCredAbbName = mandate.getUltmtCdtr().getId().getOrgId().getOthr().get(0).getId().trim();
				if(trimCredAbbName.isEmpty() || trimCredAbbName == null || trimCredAbbName.length() == 0)
				{
					if(systemType.equalsIgnoreCase(sadcSystem))
						populateMdtErrorList("901170",msgId,mandate.getMndtId());
					else
						generateStatusErrorDetailsList("901170", mndtReqTransId, txnErrorType);
					mandateSeverity++;
					log.info("******************validateCrAbbShortName_012.047 - Failed.******************");
				}
				else
				{
					mandateSeverity = mandateSeverity+0;
					log.debug("******************validateCrAbbShortName_012.047 - Passed.******************");
				}
			}
		}
		else
		{
			if(origServiceId.equalsIgnoreCase("MANIN"))
			{
				if(systemType.equalsIgnoreCase(sadcSystem))
					populateMdtErrorList("901170",msgId,mandate.getMndtId());
				else
					generateStatusErrorDetailsList("901170", mndtReqTransId, txnErrorType);
				mandateSeverity++;
				log.info("******************validateCrAbbShortName_012.047 - Failed.******************");
			}
			//This field is optional in pain.012 for CARIN/MANCN
		}
		 */


		//_________________validate Debtor Name_rule02.999____________________________________//
		//		2018-03-13-SalehaR-This rule has been removed from TRS17
		if(/*origServiceId.equalsIgnoreCase("MANIN") ||*/ origServiceId.equalsIgnoreCase("MANCN"))
		{
			if(mandate.getDbtr() != null && mandate.getDbtr().getNm() != null)
			{
				if(validateRule999(mandate.getDbtr().getNm()))
				{
					mandateSeverity = mandateSeverity+0;
					log.debug("******************validategetDbtrNm_012.999 - PASSED.******************");
				}
				else
				{
					generateStatusErrorDetailsList("910099", mndtReqTransId, txnErrorType);
					mandateSeverity++;
					log.info("******************validategetDbtrNm_012.999 - FAILED.******************");
				}
			}
			else
			{
				generateStatusErrorDetailsList("910099", mndtReqTransId, txnErrorType);
				mandateSeverity++;
				log.info("******************validategetDbtrNm_012.999 - FAILED.******************");
			}
		}
		//This field is optional in pain.012 for CARIN

		/*
		 * 07/12/2017 -DimakatsoN -Removed as per SCR 225v4
		//____________________________Validate Debtor Id Num 012_026______________________________________//
		if(origServiceId.equalsIgnoreCase("MANIN") || origServiceId.equalsIgnoreCase("CARIN"))
		{
			if(mandate != null && mandate.getDbtr() != null && mandate.getDbtr().getId() != null && mandate.getDbtr().getId().getPrvtId() != null && mandate.getDbtr().getId().getPrvtId().getOthr() != null)
			{

				if(mandate.getDbtr().getId().getPrvtId().getOthr().get(0) != null && mandate.getDbtr().getId().getPrvtId().getOthr().get(0).getId() != null && debtorEntity != null && debtorEntity.getId() != null)
				{
					log.debug("File DEBTOR ID----> "+debtorEntity.getId());
					log.debug("RCAIN DEBTOR ID----> "+mandate.getDbtr().getId().getPrvtId().getOthr().get(0).getId());

					if(mandate.getDbtr().getId().getPrvtId().getOthr().get(0).getId().trim().equalsIgnoreCase(debtorEntity.getId().trim()))
					{
						mandateSeverity = mandateSeverity+0;
						log.debug("******************validateDebtorIdentifier_026 - Passed.******************");
					}
					else
					{
						if(systemType.equalsIgnoreCase(sadcSystem))
							populateMdtErrorList("901122",msgId,mandate.getMndtId());
						else
							generateStatusErrorDetailsList("901122", mndtReqTransId, txnErrorType);
						mandateSeverity++;
						log.info("******************validateDebtorIdentifier_026 - Failed.******************");
					}
				}
			}
			else
			{
				if(origServiceId.equalsIgnoreCase("MANIN"))
				{
					generateStatusErrorDetailsList("901122", mndtReqTransId, txnErrorType);
					mandateSeverity++;
					log.info("******************validateDebtorIdentifier_026 - Failed.******************");
				}
				//This field is optional in pain.012 for CARIN
			}
		}
		//This field is not validated in MANCN
		 */

		/*
		 * 07/12/2017 -DimakatsoN -Removed as per SCR 225v4
		//____________________________Validate  Debtor Account Number_rule 012_027_____________________________________//
		if(mandate.getDbtrAcct() != null && mandate.getDbtrAcct().getId() != null && mandate.getDbtrAcct().getId().getOthr() != null && mandate.getDbtrAcct().getId().getOthr().getId() != null
				&& debtorCashAcc != null && debtorCashAcc.getAccountNumber() != null)
		{
			if(mandate.getDbtrAcct().getId().getOthr().getId() .trim().equalsIgnoreCase(debtorCashAcc.getAccountNumber().trim()))
			{
				log.debug("******************validateDebtorAccountNo_012_027 - Passed.******************");
				mandateSeverity = mandateSeverity+0;
			}
			else
			{
				if(systemType.equalsIgnoreCase(sadcSystem))
					populateMdtErrorList("901115",msgId,mandate.getMndtId());
				else
					generateStatusErrorDetailsList("901115", mndtReqTransId, txnErrorType);
				mandateSeverity++;
				log.info("******************validateDebtorAccountNo_012_027 - Failed.******************");
			}
		}
		else
		{
			if(origServiceId.equalsIgnoreCase("MANIN"))
			{
				if(systemType.equalsIgnoreCase(sadcSystem))
					populateMdtErrorList("901115",msgId,mandate.getMndtId());
				else
					generateStatusErrorDetailsList("901115", mndtReqTransId, txnErrorType);
				mandateSeverity++;
				log.info("******************validateDebtorAccountNo_012_027 - Failed.******************");
			}
			//This field is optional in pain.012 for CARIN
		}

		 */
		if(mandate.getDbtrAcct() != null && mandate.getDbtrAcct().getTp() != null && mandate.getDbtrAcct().getTp().getPrtry() != null)
		{
			if(validateRule999(mandate.getDbtrAcct().getTp().getPrtry().trim()))
			{
				mandateSeverity = mandateSeverity+0;
				log.debug("******************validateAccountType_012.999 - PASSED.******************");
			}
			else
			{
				generateStatusErrorDetailsList("910099", mndtReqTransId, txnErrorType);
				mandateSeverity++;
				log.info("******************validateAccountType_012.999 - FAILED.******************");
			}
		}
		else
		{
			if(origServiceId.equalsIgnoreCase("MANIN"))
			{
				generateStatusErrorDetailsList("910099", mndtReqTransId, txnErrorType);
				mandateSeverity++;
				log.info("******************validateAccountType_012.999 - FAILED.******************");
			}
			//This field is optional in pain.012 for CARIN
		}

		/*
		 * 07/12/2017 -DimakatsoN -Removed as per SCR 225v4
		//____________________________Validate  Debtor Branch Number_rule 012_028_____________________________________//
		if(mandate.getDbtrAgt() != null && mandate.getDbtrAgt().getFinInstnId() != null && mandate.getDbtrAgt().getFinInstnId().getClrSysMmbId() != null && mandate.getDbtrAgt().getFinInstnId().getClrSysMmbId().getMmbId() != null && drAgentEntity != null && drAgentEntity.getBranchId() != null)
		{
			debtorBranchNo = mandate.getDbtrAgt().getFinInstnId().getClrSysMmbId().getMmbId();
			if(mandate.getDbtrAgt().getFinInstnId().getClrSysMmbId().getMmbId().trim().equalsIgnoreCase(drAgentEntity.getBranchId().trim()))
			{
				log.debug("******************validateDebtorBranchNo_012_028 - Passed.******************");
				mandateSeverity = mandateSeverity+0;
			}
			else
			{
				if(systemType.equalsIgnoreCase(sadcSystem))
					populateMdtErrorList("901116",msgId,mandate.getMndtId());
				else
					generateStatusErrorDetailsList("901116", mndtReqTransId, txnErrorType);
				mandateSeverity++;
				log.info("******************validateDebtorBranchNo_012_028 - Failed.******************");
			}
		}
		else
		{
			if(origServiceId.equalsIgnoreCase("MANIN"))
			{
				if(systemType.equalsIgnoreCase(sadcSystem))
					populateMdtErrorList("901116",msgId,mandate.getMndtId());
				else
					generateStatusErrorDetailsList("901116", mndtReqTransId, txnErrorType);
				mandateSeverity++;
				log.info("******************validateDebtorBranchNo_012_028 - Failed.******************");
			}
			//This field is optional in pain.012 for CARIN
		}
		 */

		/*
		 * 07/12/2017 -DimakatsoN -Removed as per SCR 225v4
		//____________________________Validate AuthType_ 012.048______________________________________//
		if(supplementaryData1 != null && supplementaryData1.getEnvlp() != null && supplementaryData1.getEnvlp().getCnts() != null && 
				supplementaryData1.getEnvlp().getCnts().getAthntctnTp() != null)
		{

			if(validateAuthenticationType(supplementaryData1.getEnvlp().getCnts().getAthntctnTp().trim()))
			{
				mandateSeverity = mandateSeverity+0;
				log.debug("******************validateAuthType_012.048 - Passed******************");
			}
			else
			{
				if(systemType.equalsIgnoreCase(sadcSystem))
					populateMdtErrorList("901118",msgId,mandate.getMndtId());
				else
					generateStatusErrorDetailsList("901118", mndtReqTransId, txnErrorType);
				mandateSeverity++;
				log.info("******************validateAuthType_012.048 - Failed.******************");
			}
		}
		else
		{
			if(systemType.equalsIgnoreCase(sadcSystem))
				populateMdtErrorList("901118",msgId,mandate.getMndtId());
			else
				generateStatusErrorDetailsList("901118", mndtReqTransId, txnErrorType);
			mandateSeverity++;
			log.info("******************validateAuthType_012.048 - Failed.******************");
		}
		 */

		if(origServiceId.equalsIgnoreCase("MANIN") || origServiceId.equalsIgnoreCase("CARIN"))
		{


			/*
			 * 07/12/2017 -DimakatsoN -Removed as per SCR 225v4
			  //____________________________Validate Authentication_Status_Id 012.029______________________________________//


				////This field is optional in pain.012 for MANCN

				AcceptanceResult6 accptResult = new AcceptanceResult6();
				if(accptResult != null)
				{
					if(accptResult.isAccptd()==true)
					{
						log.info("accptResult.isAccptd----------->"+accptResult.isAccptd());

						if(mandate.getRfrdDoc() != null && mandate.getRfrdDoc().getTp() != null && mandate.getRfrdDoc().getTp().getCdOrPrtry() != null && mandate.getRfrdDoc().getTp().getCdOrPrtry().getPrtry() != null)
						{
							log.info("validateAuthenticationInd  ----------->"+mandate.getRfrdDoc().getTp().getCdOrPrtry().getPrtry());
							if(!validateAuthId(mandate.getRfrdDoc().getTp().getCdOrPrtry().getPrtry()))
							{
								if(systemType.equalsIgnoreCase(sadcSystem))
									populateMdtErrorList("901182",msgId,mandate.getMndtId());
								else
									generateStatusErrorDetailsList("901182", mndtReqTransId, txnErrorType);
								mandateSeverity++;
								log.info("******************validateAuthstatusInd_029 - Failed.******************");
							}
							else
							{
								mandateSeverity = mandateSeverity+0;
								log.info("******************validateAuthstatusInd_029 - Passed******************");
							}
						}
					}
					else 
					{
						//log.info("accptResult.isAccptd(true)==----------->"+accptResult.isAccptd());
						if(mandate.getRfrdDoc() != null && mandate.getRfrdDoc().getTp() != null && mandate.getRfrdDoc().getTp().getCdOrPrtry() != null && mandate.getRfrdDoc().getTp().getCdOrPrtry().getPrtry() != null)
						{
							log.info("validateAuthenticationInd ----------->"+mandate.getRfrdDoc().getTp().getCdOrPrtry().getPrtry());
							if(!validateAuthenticationInd(mandate.getRfrdDoc().getTp().getCdOrPrtry().getPrtry()))
							{
								if(systemType.equalsIgnoreCase(sadcSystem))
									populateMdtErrorList("901182",msgId,mandate.getMndtId());
								else
									generateStatusErrorDetailsList("901182", mndtReqTransId, txnErrorType);
								mandateSeverity++;
								log.info("******************validateAuthstatusInd_029 - Failed.******************");
							}
							else
							{
								mandateSeverity = mandateSeverity+0;
								log.info("******************validateAuthstatusInd_029 - Passed******************");
							}
						}
					}
				}
			 */


			/*
			 * 07/12/2017 -DimakatsoN -Removed as per SCR 225v4
			 * 
			////This field is optional in pain.012 for MANCN
			if(mandate.getRfrdDoc() != null && mandate.getRfrdDoc().getTp() != null && mandate.getRfrdDoc().getTp().getCdOrPrtry() != null && mandate.getRfrdDoc().getTp().getCdOrPrtry().getPrtry() != null)
			{
				if(!validateAuthstatusInd_029(mandate.getRfrdDoc().getTp().getCdOrPrtry().getPrtry()))
				{
					if(systemType.equalsIgnoreCase(sadcSystem))
						populateMdtErrorList("902148",msgId,mandate.getMndtId());
					else
						generateStatusErrorDetailsList("902148", mndtReqTransId, txnErrorType);
					mandateSeverity++;
					log.info("******************validateAuthstatusInd_029 - Failed.******************");
				}
				else
				{
					mandateSeverity = mandateSeverity+0;
					log.debug("******************validateAuthstatusInd_029 - Passed******************");


					if(acceptedResult == true)
					{
						if(!validateAuthId(mandate.getRfrdDoc().getTp().getCdOrPrtry().getPrtry()))
						{
							if(systemType.equalsIgnoreCase(sadcSystem))
								populateMdtErrorList("901182",msgId,mandate.getMndtId());
							else
								generateStatusErrorDetailsList("901182", mndtReqTransId, txnErrorType);
							mandateSeverity++;
							log.info("******************validateAuthstatusInd_029_1a- Failed.******************");
						}
						else
						{
							mandateSeverity = mandateSeverity+0;
							log.debug("******************validateAuthstatusInd_029_1a - Passed******************");
						}
					}
					else
					{
						if(!validateAuthenticationInd(mandate.getRfrdDoc().getTp().getCdOrPrtry().getPrtry()))
						{
							if(systemType.equalsIgnoreCase(sadcSystem))
								populateMdtErrorList("901182",msgId,mandate.getMndtId());
							else
								generateStatusErrorDetailsList("901182", mndtReqTransId, txnErrorType);
							mandateSeverity++;
							log.info("******************validateAuthstatusInd_029_1b- Failed.******************");
						}
						else
						{
							mandateSeverity = mandateSeverity+0;
							log.debug("******************validateAuthstatusInd_029_1b- Passed******************");
						}
					}
				}
			}
			else
			{
				if(systemType.equalsIgnoreCase(sadcSystem))
					populateMdtErrorList("902148",msgId,mandate.getMndtId());
				else
					generateStatusErrorDetailsList("902148", mndtReqTransId, txnErrorType);
				mandateSeverity++;
				log.info("******************validateAuthstatusInd_029 - Failed.******************");
			}
			 */
			//____________________________Validate Collection Day 012_031______________________________________//
			if(supplementaryData1 != null && supplementaryData1.getEnvlp() != null && supplementaryData1.getEnvlp().getCnts() != null && 
					supplementaryData1.getEnvlp().getCnts().getCllctnDy() != null && casOpsCessAssignTxnsEntityOriginal != null && casOpsCessAssignTxnsEntityOriginal.getCollectionDay() != null)
			{
				int CllctnDy = Integer.valueOf(supplementaryData1.getEnvlp().getCnts().getCllctnDy().trim());
				int collDay= Integer.valueOf(casOpsCessAssignTxnsEntityOriginal.getCollectionDay().trim());

				log.debug("CllctnDy----->"+CllctnDy);
				log.debug("collDay------>"+collDay);
				//if(supplementaryData1.getEnvlp().getCnts().getCllctnDy().trim().equalsIgnoreCase(opsSupplDataEntity.getCollDay().trim()))
				if(CllctnDy==collDay)
				{
					mandateSeverity = mandateSeverity+0;
					log.debug("******************validateCollectionDay_031 - Passed******************");
				}
				else
				{
					generateStatusErrorDetailsList("901120", mndtReqTransId, txnErrorType);
					mandateSeverity++;
					log.info("******************validateCollectionDay_031 - Failed.******************");
				}
			}
			else
			{
				generateStatusErrorDetailsList("901120", mndtReqTransId, txnErrorType);
				mandateSeverity++;
				log.info("******************validateCollectionDay_031 - Failed.******************");
			}

			//____________________________Validate Date Adjustement Rule Indicator 012_032______________________________________//

			if(supplementaryData1 != null && supplementaryData1.getEnvlp() != null && supplementaryData1.getEnvlp().getCnts() != null && 
					supplementaryData1.getEnvlp().getCnts().getDtAdjRl() != null && casOpsCessAssignTxnsEntityOriginal != null && casOpsCessAssignTxnsEntityOriginal.getDateAdjRuleInd() != null)
			{

				if(supplementaryData1.getEnvlp().getCnts().getDtAdjRl().trim().equalsIgnoreCase(casOpsCessAssignTxnsEntityOriginal.getDateAdjRuleInd().trim()))
				{
					mandateSeverity = mandateSeverity+0;
					log.debug("******************validategetAdjstCtgy_032 - Passed******************");
				}
				else
				{
					generateStatusErrorDetailsList("901121", mndtReqTransId, txnErrorType);
					mandateSeverity++;
					log.info("******************validateAdjtRuleInd_032 - Failed.******************");
				}
			}
			else
			{
				if(origServiceId.equalsIgnoreCase("MANIN"))
				{
					generateStatusErrorDetailsList("901121", mndtReqTransId, txnErrorType);
					mandateSeverity++;
					log.info("******************validateAdjtRuleInd_032 - Failed.******************");
				}
				//This field is optional in pain.012 for CARIN
			}


			//____________________________Validate Adjustment Category 012_033_____________________________ _________//
			if(supplementaryData1 != null && supplementaryData1.getEnvlp() != null && supplementaryData1.getEnvlp().getCnts() != null && 
					supplementaryData1.getEnvlp().getCnts().getAdjstCtgy() != null && casOpsCessAssignTxnsEntityOriginal != null && casOpsCessAssignTxnsEntityOriginal.getAdjCategory() != null)
			{

				if(supplementaryData1.getEnvlp().getCnts().getAdjstCtgy().toString().trim().equalsIgnoreCase(casOpsCessAssignTxnsEntityOriginal.getAdjCategory().trim()))
				{
					mandateSeverity = mandateSeverity+0;
					log.debug("******************validategetAdjstCtgy_033 - Passed.******************");
				}
				else
				{
					generateStatusErrorDetailsList("901125", mndtReqTransId, txnErrorType);
					mandateSeverity++;
					log.info("******************validategetAdjstCtgy_033 - Failed.******************");
				}
			}
			else
			{
				if(origServiceId.equalsIgnoreCase("MANIN"))
				{
					generateStatusErrorDetailsList("901125", mndtReqTransId, txnErrorType);
					mandateSeverity++;
					log.info("******************validategetAdjstCtgy_033 - Failed.******************");
				}
				//This field is optional in pain.012 for CARIN
			}


			/*
			 * 07/12/2017 -DimakatsoN -Removed as per SCR 225v4
			//____________________________Validate Adjustment Rate 012_034_____________________________ _________//
			if(supplementaryData1 != null && supplementaryData1.getEnvlp() != null && supplementaryData1.getEnvlp().getCnts() != null && 
					supplementaryData1.getEnvlp().getCnts().getAdjstRt() != null &&  opsSupplDataEntity != null && opsSupplDataEntity.getAdjustRate() != null)
			{

				if(supplementaryData1.getEnvlp().getCnts().getAdjstRt().compareTo(opsSupplDataEntity.getAdjustRate()) == 0)
				{
					mandateSeverity = mandateSeverity+0;
					log.debug("******************validategetAdjstRate_034 - Passed.******************");
				}
				else
				{
					generateStatusErrorDetailsList("901126", mndtReqTransId, txnErrorType);
					mandateSeverity++;
					log.info("******************validategetAdjstRate_034 - Failed.******************");
				}
			}
			//This is optional as per TRS 15
			 */

			//2018-03-13-SalehaR- This has been removed as per SCR225			
			//			//____________________________Validate Adjustment  Amount 012_051_____________________________ _________//
			//			if(supplementaryData1 != null && supplementaryData1.getEnvlp() != null && supplementaryData1.getEnvlp().getCnts() != null && 
			//					supplementaryData1.getEnvlp().getCnts().getAdjstAmt() != null && supplementaryData1.getEnvlp().getCnts().getAdjstAmt().getCcy() != null)
			//			{
			//				if(validateCurrency(supplementaryData1.getEnvlp().getCnts().getAdjstAmt().getCcy()))
			//				{
			//					mandateSeverity = mandateSeverity+0;
			//					log.debug("******************validateAdjAmtCurr_012.051 - Passed.******************");
			//				}
			//				else
			//				{
			//					generateStatusErrorDetailsList("901198", mndtReqTransId, txnErrorType);
			//					mandateSeverity++;
			//					log.info("******************validateAdjAmtCurr_012.051 - Failed.******************");
			//				}
			//			}
			////This is optional as per TRS 15

			/*
			 * 07/12/2017 -DimakatsoN -Removed as per SCR 225v4
			//____________________________Validate Adjustment  Amount 012_035_____________________________ _________//
			if(supplementaryData1 != null && supplementaryData1.getEnvlp() != null && supplementaryData1.getEnvlp().getCnts() != null && 
					supplementaryData1.getEnvlp().getCnts().getAdjstAmt() != null && supplementaryData1.getEnvlp().getCnts().getAdjstAmt().getValue() != null 
					&& opsSupplDataEntity != null && opsSupplDataEntity.getAdjustAmt() != null)
			{

				if(supplementaryData1.getEnvlp().getCnts().getAdjstAmt().getValue().compareTo(opsSupplDataEntity.getAdjustAmt()) == 0)
				{
					mandateSeverity = mandateSeverity+0;
					log.debug("******************validateAdjAmount_035 - Passed.******************");
				}
				else
				{
					generateStatusErrorDetailsList("901127", mndtReqTransId, txnErrorType);
					mandateSeverity++;
					log.info("******************validateAdjAmount_035 - Failed.******************");
				}
			}
			////This is optional as per TRS 15
			 */

			//____________________________Validate Mandate Authentication Channel 012.999_____________________________ _________//
			if(supplementaryData1 != null && supplementaryData1.getEnvlp() != null && supplementaryData1.getEnvlp().getCnts() != null && 
					supplementaryData1.getEnvlp().getCnts().getChnnl() != null)
			{
				if(validateRule999(supplementaryData1.getEnvlp().getCnts().getChnnl().trim()))
				{
					mandateSeverity = mandateSeverity+0;
					log.debug("******************validateAccountType_012.999 - Passed.******************");
				}
				else
				{
					generateStatusErrorDetailsList("910099", mndtReqTransId, txnErrorType);
					mandateSeverity++;
					log.info("******************validateAccountType_012.999 - FAILED.******************");
				}
			}
			else
			{
				generateStatusErrorDetailsList("910099", mndtReqTransId, txnErrorType);
				mandateSeverity++;
				log.info("******************validateAccountType_012.999 - FAILED.******************");
			}

			//2018-03-13-SalehaR-This has been removed as per SCR225			
			//____________________________Validate FrstColl  Amount 012_051_____________________________ _________//
			//			if(supplementaryData1 != null && supplementaryData1.getEnvlp() != null && supplementaryData1.getEnvlp().getCnts() != null && 
			//					supplementaryData1.getEnvlp().getCnts().getFrstColltnAmt() != null && supplementaryData1.getEnvlp().getCnts().getFrstColltnAmt().getCcy() != null)
			//			{
			//				if(validateCurrency(supplementaryData1.getEnvlp().getCnts().getFrstColltnAmt().getCcy()))
			//				{
			//					mandateSeverity = mandateSeverity+0;
			//					log.debug("******************validateFrstAmtCurr_012.051 - Passed.******************");
			//				}
			//				else
			//				{
			//					generateStatusErrorDetailsList("901198", mndtReqTransId, txnErrorType);
			//					mandateSeverity++;
			//					log.info("******************validateFrstAmtCurr_012.051 - Failed.******************");
			//				}
			//			}
			////This is optional as per TRS 15

			/*
			 * 07/12/2017 -DimakatsoN -Removed as per SCR 225v4

			//____________________________Validate First Collection Amount 012_037_____________________________ _________//
			if(supplementaryData1 != null && supplementaryData1.getEnvlp() != null && supplementaryData1.getEnvlp().getCnts() != null && 
					supplementaryData1.getEnvlp().getCnts().getFrstColltnAmt() != null && supplementaryData1.getEnvlp().getCnts().getFrstColltnAmt().getValue() != null 
					&& opsSupplDataEntity != null && opsSupplDataEntity.getFirstCollAmt() != null)
			{

				if(supplementaryData1.getEnvlp().getCnts().getFrstColltnAmt().getValue().compareTo(opsSupplDataEntity.getFirstCollAmt()) == 0)
				{
					mandateSeverity = mandateSeverity+0;
					log.debug("******************validategeFrstCollAmt_037 - Passed.******************");
				}
				else
				{
					generateStatusErrorDetailsList("901166", mndtReqTransId, txnErrorType);
					mandateSeverity++;
					log.info("******************validategeFrstCollAmt_037 - Failed.******************");
				}
			}
			//This is optional as per TRS 15
			 */

			/*if(supplementaryData1 != null && supplementaryData1.getEnvlp() != null && supplementaryData1.getEnvlp().getCnts() != null && supplementaryData1.getEnvlp().getCnts().getFrstColltnAmt() != null && supplementaryData1.getEnvlp().getCnts().getFrstColltnAmt().getValue() != null 
					&& opsSupplDataEntity != null && opsSupplDataEntity.getFirstCollAmt() != null)
			{


				if(mandate.getTp() != null && mandate.getTp().getLclInstrm() != null && mandate.getTp().getLclInstrm().getPrtry() != null && casOpsCessAssignTxnsEntityOriginal!= null && casOpsCessAssignTxnsEntityOriginal.getLocalInstrCd() != null)
				{

					String localInstrCd = mandate.getTp().getLclInstrm().getPrtry();
					 String maxAmount =  String.valueOf(supplementaryData1.getEnvlp().getCnts().getFrstColltnAmt().getValue().intValue());

					if (localInstrCd != null) 
					{ 
						if (!localInstrCd.trim().equalsIgnoreCase("0999") || !localInstrCd.trim().equalsIgnoreCase("0998")) 
						{

							 if(maxAmount == null ||maxAmount.toString().isEmpty()|| maxAmount.toString().length() == 0)
							 {
								 mandateSeverity = mandateSeverity + 0;
									log.info("******************validategeFrstCollAmt_rule_NAEDO/AEDO009_073 - Passed.******************");
							 }
							 else 
							 {

								 generateStatusErrorDetailsList("902421", mndtReqTransId, txnErrorType);
									mandateSeverity++;
									log.info("******************validategeFrstCollAmt_rule_NAEDO/AEDO009_073 - Failed.******************");
							 }

						}
					}
				}
			}*/

		}

		/*
		 *07/12/2017 -DimakatsoN -Removed as per SCR 225v4
		//____________________________Validate Mandate Reference Number 012_036_____________________________ _________//
		if(mandate.getRfrdDoc() != null && mandate.getRfrdDoc().getTp() != null && mandate.getRfrdDoc().getTp().getCdOrPrtry() != null && mandate.getRfrdDoc().getTp().getCdOrPrtry().getPrtry() != null)
		{
			if(mandate.getRfrdDoc().getTp().getCdOrPrtry().getPrtry().toString().trim().equalsIgnoreCase("AAUT"))
			{
				if(supplementaryData1 != null && supplementaryData1.getEnvlp() != null && supplementaryData1.getEnvlp().getCnts() != null && supplementaryData1.getEnvlp().getCnts().getMndtRfNbr() != null)
				{
					trimmedManRfNo = supplementaryData1.getEnvlp().getCnts().getMndtRfNbr().toString().trim();

					if(trimmedManRfNo == null || trimmedManRfNo.isEmpty() || trimmedManRfNo.length() == 0)
					{
						generateStatusErrorDetailsList("901162", mndtReqTransId, txnErrorType);
						mandateSeverity++;
						log.info("******************validateManRefNo_012_036 - Failed.******************");
					}
					else
					{
						mandateSeverity = mandateSeverity+0;
						log.debug("******************validateManRefNo_012_036 - Passed.******************");
					}
				}
				else
				{
					generateStatusErrorDetailsList("901162", mndtReqTransId, txnErrorType);
					mandateSeverity++;
					log.info("******************validateManRefNo_012_036 - Failed.******************");
				}
			}
		}
		 */



		//____________________________Validate Debit Value Type_ 012_030_____________________________ _________//
		if(supplementaryData1 != null && supplementaryData1.getEnvlp() != null && supplementaryData1.getEnvlp().getCnts() != null && 
				supplementaryData1.getEnvlp().getCnts().getDbVlTp() != null && casOpsCessAssignTxnsEntityOriginal != null && casOpsCessAssignTxnsEntityOriginal.getDebitValueType() != null)
		{
			if(origServiceId.equalsIgnoreCase("MANIN"))
			{
				log.info("******************validateDebitValType_030 - Passed.******************");
				mandateSeverity = mandateSeverity+0;
			}

//			if(supplementaryData1.getEnvlp().getCnts().getDbVlTp().toString().trim().equalsIgnoreCase(casOpsCessAssignTxnsEntityOriginal.getDebitValueType().trim()))
//			{
//				mandateSeverity = mandateSeverity+0;
//				log.debug("******************validateDebitValType_030 - Passed.******************");
//			}
//			else
//			{
//				generateStatusErrorDetailsList("901084", mndtReqTransId, txnErrorType);
//				mandateSeverity++;
//				log.info("******************validateDebitValType_030 - Failed.******************");
//			}
		}
		else
		{
			if(origServiceId.equalsIgnoreCase("MANIN"))
			{
				generateStatusErrorDetailsList("901084", mndtReqTransId, txnErrorType);
				mandateSeverity++;
				log.info("******************validateDebitValType_030 - Failed.******************");
			}
			//This field is optional in pain.012 for CARIN/MANCN
		}

		/*
		 * 07/12/2017 -DimakatsoN -Removed as per SCR 225v4

		//____________________________Validate Mandate Authentication Date_ 012_043_____________________________ _________//
		if(supplementaryData1 != null && supplementaryData1.getEnvlp() != null && supplementaryData1.getEnvlp().getCnts() != null && supplementaryData1.getEnvlp().getCnts().getMndtAthDt() != null)
		{
			Date manAuthDt = getCovertDateTime(supplementaryData1.getEnvlp().getCnts().getMndtAthDt());

			if(manAuthDt != null)
			{
				String strCreationDate = sdf.format(manAuthDt);
				if(!isDateValid(strCreationDate, "yyyy-MM-dd"))
				{
					if(systemType.equalsIgnoreCase(sadcSystem))
						populateMdtGrpHdrErrorList("902112", msgId);
					else{
						generateStatusErrorDetailsList("902112", null, hdrErrorType);
					}
					mandateSeverity++;
					log.info("******************validateManAuthDt_043 - Failed.******************");
				}
				else
				{
					mandateSeverity = mandateSeverity+0;
					log.debug("******************validateManAuthDt_043 - Passed.******************");
				}
			}
			else
			{
				if(systemType.equalsIgnoreCase(sadcSystem))
					populateMdtGrpHdrErrorList("902112", msgId);
				else{
					generateStatusErrorDetailsList("902112", null, hdrErrorType);
				}
				mandateSeverity++;
				log.info("******************validateManAuthDt_043 - Failed.******************");
			}
		}
		else
		{
			if(systemType.equalsIgnoreCase(sadcSystem))
				populateMdtGrpHdrErrorList("902112", msgId);
			else{
				generateStatusErrorDetailsList("902112", null, hdrErrorType);
			}
			mandateSeverity++;
			log.info("******************validateManAuthDt_043 - Failed.******************");
		}
		 */

		return mandateSeverity;
	}

	public String  generateStatusHdrMsgId(String instId)
	{
		int lastSeqNoUsed;

		SimpleDateFormat sdfFileDate = new SimpleDateFormat("yyyyMMdd");
		String achId,  creationDate, fileSeqNo,  msgId = null;
		String outgoingService = "ST204";

		CasOpsCustParamEntity casOpsCustParamEntity = (CasOpsCustParamEntity) valBeanRemote.retrieveOpsCustomerParameters(instId, backEndProcess);

		try
		{

			if(mdtSysctrlCompParamEntity != null)
			{
				achId = mdtSysctrlCompParamEntity.getAchId();
			}
			else
			{
				achId = "021";
			}

			CasOpsRefSeqNrEntity casOpsRefSeqNrEntity = new CasOpsRefSeqNrEntity();
			casOpsRefSeqNrEntity = (CasOpsRefSeqNrEntity) valBeanRemote.retrieveRefSeqNr(outgoingService, instId);

			if(casOpsRefSeqNrEntity != null)
			{
				lastSeqNoUsed = Integer.valueOf(casOpsRefSeqNrEntity.getLastSeqNr());
				lastSeqNoUsed++;
			}
			else
				lastSeqNoUsed = 1;

			log.debug("lastSeqNoUsed---->: "+lastSeqNoUsed);
			fileSeqNo = String.format("%06d",lastSeqNoUsed);
			log.debug("fileSeqNo---->: "+fileSeqNo);
			casOpsRefSeqNrEntity.setLastSeqNr(fileSeqNo);
			valBeanRemote.updateOpsRefSeqNr(casOpsRefSeqNrEntity);

			//			creationDate = sdfFileDate.format(new Date());
			//TRS16 Processing Rules					    
			if(casSysctrlSysParamEntity != null && casSysctrlSysParamEntity.getProcessDate() != null)
			{
				creationDate = sdfFileDate.format(casSysctrlSysParamEntity.getProcessDate());
			}
			else
			{
				creationDate = sdfFileDate.format(new Date());
			}

			msgId = achId+"/"+outgoingService+"/"+"00"+instId+"/"+creationDate+"/"+fileSeqNo;
		}
		catch (Exception e) {
			log.error("**** Exception generating MsgId **** : " + e);
			e.printStackTrace();
			e.getCause();
		}

		return msgId;
	}

	public CasCnfgErrorCodesEntity retrieveErrorCode(String errCode)
	{
		/*log.debug("valBeanRemote: "+ valBeanRemote);*/
		CasCnfgErrorCodesEntity casCnfgErrorCodesEntity = (CasCnfgErrorCodesEntity) valBeanRemote.retrieveErrorCode(errCode);
		return casCnfgErrorCodesEntity;
	}

	public Date getCovertDateTime(XMLGregorianCalendar xmlGregorianCalendar) {

		Calendar cl;
		Date convDate = null;
		try
		{
			cl = xmlGregorianCalendar.toGregorianCalendar();
			convDate = cl.getTime();
		}
		catch(Exception ex)
		{
			log.error("Could not convert XMLGregorianCalender: "+ex.getMessage());
			ex.printStackTrace();
		}

		return convDate;
	}

	public void generateStatusErrorDetailsList(String errorCode, String txnId, String errorType)
	{
		opsStatusDetailsEntity=new CasOpsStatusDetailsEntity();

		opsStatusDetailsEntity.setSystemSeqNo(new BigDecimal(123));
		opsStatusDetailsEntity.setErrorCode(errorCode);
		opsStatusDetailsEntity.setTxnId(txnId);
		opsStatusDetailsEntity.setTxnStatus("RJCT");
		opsStatusDetailsEntity.setErrorType(errorType);

		if(debtorBranchNo != null)
			opsStatusDetailsEntity.setDebtorBranchNo(debtorBranchNo);
		log.debug("debtorBranchNo---------->:"+opsStatusDetailsEntity.getDebtorBranchNo());

		if(trimmedManRfNo!= null)
			opsStatusDetailsEntity.setMandateRefNumber(trimmedManRfNo);
		log.debug("mandateRefNumber---------->:"+opsStatusDetailsEntity.getMandateRefNumber());

		if(trimCredAbbName != null)	
			opsStatusDetailsEntity.setCrAbbShortName(trimCredAbbName);
		log.debug("mandateRefNumber---------->:"+opsStatusDetailsEntity.getCrAbbShortName());

		opsStatusDetailsList.add(opsStatusDetailsEntity);
	}

	public boolean saveStatusErrorDetails()
	{
		boolean generated = false;
	
		if(opsStatusDetailsList.size() > 0)
		{
			for (CasOpsStatusDetailsEntity localEntity : opsStatusDetailsList) {
				localEntity.setStatusHdrSeqNo(hdrSystemSeqNo);
			}
		
			generated=valBeanRemote.saveOpsStatusDetails(opsStatusDetailsList);
		}

		if(generated)
		{
			opsStatusDetailsList.clear();
		}
		return generated;
	}

	public BigDecimal generateStatusReportGrpHdr(GroupHeader47 groupHeader,  String groupStatus, String instgAgt)
	{
		opsStatusHdrsEntity=new CasOpsStatusHdrsEntity();
		opsStatusHdrsEntity.setSystemSeqNo(new BigDecimal(999999));
		opsStatusHdrsEntity.setHdrMsgId(outMsgId);
		opsStatusHdrsEntity.setCreateDateTime(getCovertDateTime(groupHeader.getCreDtTm()));
		opsStatusHdrsEntity.setInstgAgt(instgAgt);
		if(groupHeader.getInstdAgt() != null && groupHeader.getInstdAgt().getFinInstnId() != null && groupHeader.getInstdAgt().getFinInstnId().getClrSysMmbId() != null 
				&& groupHeader.getInstdAgt().getFinInstnId().getClrSysMmbId().getMmbId() != null)
			opsStatusHdrsEntity.setInstdAgt(groupHeader.getInstdAgt().getFinInstnId().getClrSysMmbId().getMmbId());
		opsStatusHdrsEntity.setOrgnlMsgId(groupHeader.getMsgId());
		opsStatusHdrsEntity.setOrgnlMsgName("pain.012");
		//opsStatusHdrsEntity.setOrgnlNoOfTxns(Long.valueOf(originalGroupHeader1.getOrgnlNbOfTxs()));
		opsStatusHdrsEntity.setOrgnlCreateDateTime(getCovertDateTime(groupHeader.getCreDtTm()));
		if(groupStatus.equalsIgnoreCase("ACCP"))
			opsStatusHdrsEntity.setProcessStatus("1");
		else
			opsStatusHdrsEntity.setProcessStatus("6");
		opsStatusHdrsEntity.setGroupStatus(groupStatus);
		opsStatusHdrsEntity.setService("ST204");
		opsStatusHdrsEntity.setOrgnlFileName(fileName);

		hdrSystemSeqNo = valBeanRemote.saveOpsStatusHdrs(opsStatusHdrsEntity);
		log.debug("hdrSystemSeqNo in pacs 002 validation =====>>>>: "+ hdrSystemSeqNo);
		return hdrSystemSeqNo;
	}
}
