package com.bsva.authcoll.singletable.file;

import com.bsva.PropertyUtil;
import com.bsva.authcoll.singletable.validation.AC_Pain010_Validation_ST;
import com.bsva.entities.CasOpsCessionAssignEntity;
import com.bsva.entities.CasOpsCessionAssignEntityPK;
import com.bsva.entities.CasOpsFileRegEntity;
import com.bsva.entities.CasOpsGrpHdrEntity;
import com.bsva.entities.CasOpsMndtCountEntity;
import com.bsva.entities.CasOpsMndtCountPK;
import com.bsva.entities.CasOpsStatusHdrsEntity;
import com.bsva.entities.CasOpsTxnsBillReportEntity;
import com.bsva.entities.CasOpsTxnsBillingEntity;
import com.bsva.entities.CasOpsTxnsBillingPK;
import com.bsva.entities.CasSysctrlSysParamEntity;
import com.bsva.entities.SysCisBranchEntity;
import com.bsva.interfaces.AdminBeanRemote;
import com.bsva.interfaces.FileProcessBeanRemote;
import com.bsva.interfaces.ServiceBeanRemote;
import com.bsva.interfaces.ValidationBeanRemote;
import com.bsva.utils.PainUnmarshaller;
import com.bsva.utils.Util;
import iso.std.iso._20022.tech.xsd.pain_010_001.Authorisation1Choice;
import iso.std.iso._20022.tech.xsd.pain_010_001.Document;
import iso.std.iso._20022.tech.xsd.pain_010_001.Mandate1;
import iso.std.iso._20022.tech.xsd.pain_010_001.Mandate3;
import iso.std.iso._20022.tech.xsd.pain_010_001.MandateAmendment3;
import iso.std.iso._20022.tech.xsd.pain_010_001.MandateAmendmentReason1;
import iso.std.iso._20022.tech.xsd.pain_010_001.SupplementaryData1;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.xml.datatype.XMLGregorianCalendar;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.apache.log4j.Logger;

/**
 * @author ElelwaniR
 * Modified by SalehaR - 2015/12/10 - Alignment to V2.0 of Interface Specification
 * 2017-01-06 - SalehaR - Duplicate Checking
 * @author SalehaR-2019/09/21 Align to Single Table(MDT_AC_OPS_MANDATE_TXNS)
 * @author SalehaR - 2020/08/07 - BulkInserts of CARIN
 */
public class AC_Pain010_Loader_ST {
	@EJB
	PropertyUtil propertyUtil;
	private static final long serialVersionUID = 1L;
	public static Logger log = Logger.getLogger("AC_Pain010_Loader_ST");

	public static AdminBeanRemote adminBeanRemote;
	public static ValidationBeanRemote valBeanRemote;
	public static ServiceBeanRemote beanRemote;
	public static FileProcessBeanRemote fileProcessBeanRemote;
	public static boolean result, unmarshall = false,archive = false, /*origDeleted = false,*/ grpHdrValidated = false, grpHdrCreated= false, isLoaded = false;
	public static String systemName = "CAMOWNER";
	private String pain010Schema = "/home/opsjava/Delivery/Cession_Assign/Schema/pain.010.001.03.xsd";
	private CasSysctrlSysParamEntity casSysctrlSysParamEntity = null;
	public static Date todaysDate;
	Document document;
	String fileName;

	AC_Pain010_Validation_ST ac_Pain010_Validation_ST;
	private CasOpsGrpHdrEntity casOpsGrpHdrEntity =null;
	private CasOpsCessionAssignEntity casOpsCessionAssignEntity =null;

	private List<MandateAmendment3> underLyingMandates;
	private List<Authorisation1Choice> authorisation1ChoiceList;
	//SADC - Archive Variables
	/*Archive Process Variables*/
	private String messageId = null, rdyForExtStatus, loadStatus, serviceID,tt2TxnType,tt2Succ,nonActInd,systemService,inInd;
	long startTime, endTime, duration;
	CasOpsTxnsBillingEntity casOpsTxnsBillingEntity = null;
	CasOpsTxnsBillingPK casOpsTxnsBillingPK = null;

	List<CasOpsCessionAssignEntity> acceptedMndtList =new ArrayList<CasOpsCessionAssignEntity>();
	List<CasOpsTxnsBillingEntity> txnsBillList = new ArrayList<CasOpsTxnsBillingEntity>();
	List<CasOpsTxnsBillReportEntity> opsTxnsBillReportList = new ArrayList<CasOpsTxnsBillReportEntity>();

	public AC_Pain010_Loader_ST(String filePath ,String fileName) 
	{
		startTime = System.nanoTime();
		contextCheck();
		contextAdminBeanCheck();
		contextValidationBeanCheck();
		contextFileProcBeanCheck();
		this.fileName = fileName;
		casSysctrlSysParamEntity = new CasSysctrlSysParamEntity();
		casSysctrlSysParamEntity = (CasSysctrlSysParamEntity) adminBeanRemote.retrieveActiveSysParameter();

		try
		{
			propertyUtil = new PropertyUtil();
			this.rdyForExtStatus= propertyUtil.getPropValue("ProcStatus.ReadyForExtract");
			this.loadStatus = propertyUtil.getPropValue("ProcStatus.Loaded");
			this.serviceID = propertyUtil.getPropValue("Input.Pain010");
			this.systemService = propertyUtil.getPropValue("AC.Service");
			this.tt2TxnType = propertyUtil.getPropValue("AC.TT2SubService");
			this.tt2Succ = propertyUtil.getPropValue("AC.Bill.TxnSucc");
			this.nonActInd = propertyUtil.getPropValue("NonActiveInd");
			this.inInd= propertyUtil.getPropValue("InInd");
		}
		catch (Exception e) 
		{
			log.error("AC_Pain010_Loader_ST - Could not find MandateMessageCommons.properties in classpath");
		}

		try
		{
			document = (Document) PainUnmarshaller.unmarshallFile(filePath, "iso.std.iso._20022.tech.xsd.pain_010_001", fileName, pain010Schema);

			if(document == null)
				unmarshall = false;
			else
				unmarshall = true;
		}
		catch(Exception ex)
		{
			unmarshall = false;
			log.error("Error on Unmarshal:"+ex.getMessage());
			ex.printStackTrace();
		}

		CasOpsFileRegEntity casOpsFileRegEntity = (CasOpsFileRegEntity) valBeanRemote.retrieveOpsFileReg(fileName);
		if(unmarshall)
		{
			if(casOpsFileRegEntity != null)
			{
				try {
					casOpsFileRegEntity.setStatus(propertyUtil.getPropValue("ProcStatus.Validating"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				valBeanRemote.updateOpsFileReg(casOpsFileRegEntity);
			}
			else
			{
				if(unmarshall == false && casOpsFileRegEntity != null)
				{
					casOpsFileRegEntity.setStatus("FS");
					valBeanRemote.updateOpsFileReg(casOpsFileRegEntity);
				}
			}

			// _______________________Date____________________________________
			todaysDate = new Date();

			// _______________________Mandate Models initialization_______________________
			casOpsGrpHdrEntity = new CasOpsGrpHdrEntity();
			casOpsCessionAssignEntity =new CasOpsCessionAssignEntity();

			// _______________________XSD Lists_______________________
			authorisation1ChoiceList = new ArrayList<Authorisation1Choice>();
			authorisation1ChoiceList = document.getMndtAmdmntReq().getGrpHdr().getAuthstn();

			underLyingMandates = new ArrayList<MandateAmendment3>();
			underLyingMandates = document.getMndtAmdmntReq().getUndrlygAmdmntDtls();

			//			log.info("<<<<<======PAIN.010 GROUP HEADER VAL STARTING=======>>>>>");
			// _______________________Mandate grpHdr Unmarshall_______________________
			ac_Pain010_Validation_ST = new AC_Pain010_Validation_ST(fileName);

			int grpHdrSeverity = ac_Pain010_Validation_ST.validateGroupHeader(document.getMndtAmdmntReq().getGrpHdr(), underLyingMandates.size());
			log.debug("grpHdrSeverity: "+ grpHdrSeverity);
			//			log.info("<<<<<======PAIN.010 GROUP HEADER VAL COMPLETED=======>>>>>");

			//			 grpHdrSeverity = 1;
			if(grpHdrSeverity == 0)
			{
				casOpsGrpHdrEntity = new CasOpsGrpHdrEntity();
				casOpsGrpHdrEntity.setCreateDateTime(getCovertDateTime(document.getMndtAmdmntReq().getGrpHdr().getCreDtTm()));
				casOpsGrpHdrEntity.setCreatedBy(systemName);
				casOpsGrpHdrEntity.setMsgId(document.getMndtAmdmntReq().getGrpHdr().getMsgId().trim());
				log.info("casOpsGrpHdrEntity: "+casOpsGrpHdrEntity);
//				grpHdrCreated = true;
				grpHdrCreated = beanRemote.createCasOpsGrpHdr(casOpsGrpHdrEntity);
			}
			else
			{
				try 
				{
					casOpsFileRegEntity = (CasOpsFileRegEntity) valBeanRemote.retrieveOpsFileReg(fileName);
					casOpsFileRegEntity.setStatus(propertyUtil.getPropValue("ProcStatus.FailedGroupHeader"));
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}
				valBeanRemote.updateOpsFileReg(casOpsFileRegEntity);
			}

			log.info("grpHdrSeverity: "+grpHdrSeverity);
			if ((grpHdrCreated) && (grpHdrSeverity == 0)) 
			{
				int accptCnt = 0;
				int rejCnt = 0;
				int nrOfMndtsInFile = underLyingMandates.size();

				String intParty = null;
				if(document.getMndtAmdmntReq() != null && document.getMndtAmdmntReq().getGrpHdr() != null && document.getMndtAmdmntReq().getGrpHdr().getInitgPty() != null && document.getMndtAmdmntReq().getGrpHdr().getInitgPty().getNm() != null) {
					intParty = document.getMndtAmdmntReq().getGrpHdr().getInitgPty().getNm();
				}
				generateTxnsBilling();
				long valStartTime = System.nanoTime();
				//DUPLICATE VALIDATION CHECK 
				List<MandateAmendment3> uniqueList = (List<MandateAmendment3>) ac_Pain010_Validation_ST.findDuplicateTxns(underLyingMandates);
				long duplEndTime = System.nanoTime();
				long duplDur = (duplEndTime - valStartTime) / 1000000;
				log.info("DUPLICATES IN FILE EXECUTION TIME IS "+DurationFormatUtils.formatDuration(duplDur, "HH:mm:ss.S")+" milliseconds. ");

				if(uniqueList != null && uniqueList.size() > 0) {
					rejCnt = rejCnt + (nrOfMndtsInFile - uniqueList.size());

					for (MandateAmendment3 mandAmend3 : uniqueList) 
					{
						int mandateSeverity = ac_Pain010_Validation_ST.validateMandate(mandAmend3.getMndt(),mandAmend3.getAmdmntRsn(), mandAmend3.getOrgnlMndt().getOrgnlMndt(), mandAmend3.getSplmtryData());
						//					log.info("mandateSeverity: "+ mandateSeverity);

						if(mandateSeverity == 0)
						{
							createMandate(mandAmend3.getMndt(),mandAmend3.getAmdmntRsn(), mandAmend3.getOrgnlMndt().getOrgnlMndt(), mandAmend3.getSplmtryData(), intParty);
							//determine counts based on DUPL check
							//						if(isLoaded == true)
							//						{
							//							accptCnt = accptCnt + 1;
							//						}
							//						else
							//						{
							//							rejCnt = rejCnt + 1;
							//						}
						}
						else
						{
							rejCnt = rejCnt + 1;
						}
					}
				}//end of if uniqueList
				//Reset for next file process
				grpHdrValidated = false;

				long valEndTime = System.nanoTime();
				long valDur = (valEndTime - valStartTime) / 1000000;
				log.info("VALIDATION EXECUTION TIME IS "+DurationFormatUtils.formatDuration(valDur, "HH:mm:ss.S")+" milliseconds. "); 

				if(acceptedMndtList != null && acceptedMndtList.size() > 0) {
					long loadStartTime = System.nanoTime();
					accptCnt = acceptedMndtList.size();
					saveBulkMandates();
					long loadEndTime = System.nanoTime();
					long loadDur = (loadEndTime - loadStartTime) / 1000000;
					log.info("LOAD TIME IS "+DurationFormatUtils.formatDuration(loadDur, "HH:mm:ss.S")+" milliseconds. "); 
				}

				if(rejCnt == 0)
				{
					try {
						casOpsFileRegEntity.setStatus(propertyUtil.getPropValue("ProcStatus.LoadedSuccessfully"));
					} catch (IOException e) {
						e.printStackTrace();
					}
					valBeanRemote.updateOpsFileReg(casOpsFileRegEntity);
				}
				else
				{
					try {
						casOpsFileRegEntity.setStatus(propertyUtil.getPropValue("ProcStatus.LoadedErrors"));
					} catch (IOException e) {
						e.printStackTrace();
					}
					valBeanRemote.updateOpsFileReg(casOpsFileRegEntity);
				}

				//Generate the MndtCount and Update the Pacs.002 Group Status
				generateMndtCount(accptCnt, rejCnt);		

				//Set GrpStatus based on accepted and rejected cnt
				String grpStatus = null;
				if(accptCnt == nrOfMndtsInFile)
				{
					try {
						grpStatus = propertyUtil.getPropValue("Pacs002Status.ACCP");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				else
				{
					if(rejCnt == nrOfMndtsInFile)
					{
						try {
							grpStatus = propertyUtil.getPropValue("Pacs002Status.RJCT");
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					else
					{
						if((accptCnt > 0 && accptCnt != nrOfMndtsInFile) && (rejCnt > 0 && rejCnt != nrOfMndtsInFile))
						{
							try {
								grpStatus = propertyUtil.getPropValue("Pacs002Status.PART");
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				}

				//Update Status Hdrs
				BigDecimal hdrSeqNo = ac_Pain010_Validation_ST.hdrSystemSeqNo;
				CasOpsStatusHdrsEntity casOpsStatusHdrsEntity = (CasOpsStatusHdrsEntity) beanRemote.retrieveStatusHdrsBySeqNo(hdrSeqNo);
				casOpsStatusHdrsEntity.setGroupStatus(grpStatus);
				try {
					casOpsStatusHdrsEntity.setProcessStatus(propertyUtil.getPropValue("ProcStatus.ReportToBeProd"));
				} catch (IOException e) {
					e.printStackTrace();
				}

				boolean updateStatusdHdrs = beanRemote.updateOpsStatusHdrs(casOpsStatusHdrsEntity);
				log.info("|"+fileName+" COMPLETED| TOT_TXNS "+nrOfMndtsInFile+" | ACCEPTED "+accptCnt+" | REJECTED "+rejCnt+" |");
			}//END OF IF UNMARSHALL
			else
			{
				if(casOpsFileRegEntity != null)
				{
					try 
					{
						casOpsFileRegEntity = (CasOpsFileRegEntity) valBeanRemote.retrieveOpsFileReg(fileName);
						casOpsFileRegEntity.setStatus(propertyUtil.getPropValue("ProcStatus.FailedGroupHeader"));
					} 
					catch (IOException e) 
					{
						e.printStackTrace();
					}
					valBeanRemote.updateOpsFileReg(casOpsFileRegEntity);
				}
			}
		}	

		endTime = System.nanoTime();
		duration = (endTime - startTime) / 1000000;
		log.info("|TOTAL EXECUTION TIME FOR "+fileName+" IS "+DurationFormatUtils.formatDuration(duration, "HH:mm:ss.S")+" milliseconds |"); 
		
	}

	public void createMandate(Mandate3 mandate, MandateAmendmentReason1 amdmntRsn, Mandate1 origMandate, SupplementaryData1 supplmtryData, String initParty) 
	{
		isLoaded = false;
		messageId = null;
		String credAbbShortName = null;//It is optional in Pain.010

		CasOpsCessionAssignEntityPK casOpsCessionAssignEntityPK;

		if(mandate != null)
		{
			casOpsCessionAssignEntityPK = new CasOpsCessionAssignEntityPK();
			casOpsCessionAssignEntity = new CasOpsCessionAssignEntity();

			//Set Primary Key
			if(document.getMndtAmdmntReq() != null &&  document.getMndtAmdmntReq().getGrpHdr() != null &&  document.getMndtAmdmntReq().getGrpHdr().getMsgId() !=null)
			{
				casOpsCessionAssignEntityPK.setMsgId(document.getMndtAmdmntReq().getGrpHdr().getMsgId().trim());
				messageId = document.getMndtAmdmntReq().getGrpHdr().getMsgId().trim();
			}

			if(mandate.getCdtr() != null && mandate.getCdtr().getId() != null && mandate.getCdtr().getId().getOrgId() != null && mandate.getCdtr().getId().getOrgId().getOthr() != null &&  mandate.getCdtr().getId().getOrgId().getOthr().size() > 0)
			{
				if(mandate.getCdtr().getId().getOrgId().getOthr().get(0) != null && mandate.getCdtr().getId().getOrgId().getOthr().get(0).getId() != null)
				{
					casOpsCessionAssignEntityPK.setMandateReqTranId(mandate.getCdtr().getId().getOrgId().getOthr().get(0).getId().trim());
				}
			}
			casOpsCessionAssignEntity.setCasOpsCessionAssignEntityPK(casOpsCessionAssignEntityPK);

			//			=============SETTING PROCESSING INFORMATION============= //
			if(document.getMndtAmdmntReq() != null&& document.getMndtAmdmntReq().getGrpHdr() != null&& document.getMndtAmdmntReq().getGrpHdr().getInstgAgt().getFinInstnId() != null
					&& document.getMndtAmdmntReq().getGrpHdr().getInstgAgt().getFinInstnId().getClrSysMmbId() != null&& document.getMndtAmdmntReq().getGrpHdr().getInstgAgt().getFinInstnId().getClrSysMmbId().getMmbId() != null) 
				casOpsCessionAssignEntity.setCreditorBank(document.getMndtAmdmntReq().getGrpHdr().getInstgAgt().getFinInstnId().getClrSysMmbId().getMmbId().trim());


			if(mandate.getDbtrAgt() != null && mandate.getDbtrAgt().getFinInstnId()!= null && mandate.getDbtrAgt().getFinInstnId().getClrSysMmbId()!= null && mandate.getDbtrAgt().getFinInstnId().getClrSysMmbId().getMmbId() != null)
			{
				if(ac_Pain010_Validation_ST.debtorBank != null)
					casOpsCessionAssignEntity.setDebtorBank(ac_Pain010_Validation_ST.debtorBank);
			}
			else 
			{
				if(origMandate != null && origMandate.getDbtrAgt()!= null && origMandate.getDbtrAgt().getFinInstnId()!= null && origMandate.getDbtrAgt().getFinInstnId().getClrSysMmbId()!= null && origMandate.getDbtrAgt().getFinInstnId().getClrSysMmbId().getMmbId()!= null)
				{
					SysCisBranchEntity sysCisBranchEntity = (SysCisBranchEntity) valBeanRemote.validateDebtorBranchNo(origMandate.getDbtrAgt().getFinInstnId().getClrSysMmbId().getMmbId().trim(),"Y");
					if(sysCisBranchEntity != null)
					{
						casOpsCessionAssignEntity.setDebtorBank(sysCisBranchEntity.getMemberNo());
					}
				}
			}

			casOpsCessionAssignEntity.setServiceId(serviceID);
			casOpsCessionAssignEntity.setInFileName(fileName.substring(0,37).trim());
			casOpsCessionAssignEntity.setInFileDate(getFileDate(fileName.substring(0,37).trim()));
			casOpsCessionAssignEntity.setProcessStatus(rdyForExtStatus);
			if(initParty != null && !initParty.isEmpty())
				casOpsCessionAssignEntity.setInitParty(initParty);

			//			=============SETTING MANDATE DETAILS============= //
			if(amdmntRsn != null && amdmntRsn.getRsn() != null && amdmntRsn.getRsn().getPrtry() != null)
				casOpsCessionAssignEntity.setAmendReason(amdmntRsn.getRsn().getPrtry().trim());

			if (mandate.getMndtId() != null)
				casOpsCessionAssignEntity.setMandateId(mandate.getMndtId().trim());

			if(mandate.getMndtReqId() != null)
				casOpsCessionAssignEntity.setContractRef(mandate.getMndtReqId().trim());

			if(mandate.getTp() != null && mandate.getTp().getSvcLvl() != null && mandate.getTp().getSvcLvl().getPrtry() != null)
				casOpsCessionAssignEntity.setServiceLevel(mandate.getTp().getSvcLvl().getPrtry().trim());

			if(mandate.getTp() != null && mandate.getTp().getLclInstrm() != null && mandate.getTp().getLclInstrm().getPrtry() != null)
				casOpsCessionAssignEntity.setLocalInstrCd(mandate.getTp().getLclInstrm().getPrtry().trim());

			if(mandate.getOcrncs() != null&& mandate.getOcrncs().getSeqTp() != null)
				casOpsCessionAssignEntity.setSequenceType(mandate.getOcrncs().getSeqTp().toString().trim());

			if(mandate.getOcrncs() != null&& mandate.getOcrncs().getFrqcy() != null && mandate.getOcrncs().getFrqcy() != null)
				casOpsCessionAssignEntity.setFrequency(mandate.getOcrncs().getFrqcy().toString());

			if(mandate.getOcrncs() != null && mandate.getOcrncs().getFrstColltnDt() != null)
				casOpsCessionAssignEntity.setFirstCollDate(getCovertDateTime(mandate.getOcrncs().getFrstColltnDt()));

			if(mandate.getColltnAmt() != null && mandate.getColltnAmt().getCcy() != null)
				casOpsCessionAssignEntity.setCollAmountCurr(mandate.getColltnAmt().getCcy());

			if(mandate.getColltnAmt() != null)
				casOpsCessionAssignEntity.setCollAmount(new BigDecimal(mandate.getColltnAmt().getValue().toString()));

			if(mandate.getMaxAmt() != null&& mandate.getMaxAmt().getCcy() != null)
				casOpsCessionAssignEntity.setMaxAmountCurr(mandate.getMaxAmt().getCcy().trim());

			if(mandate.getMaxAmt() != null)
				casOpsCessionAssignEntity.setMaxAmount(new BigDecimal(mandate.getMaxAmt().getValue().toString().trim()));

			//			=============SETTING CREDITOR INFORMATION ============= //
			if(mandate.getCdtrSchmeId() != null && mandate.getCdtrSchmeId().getId() != null && mandate.getCdtrSchmeId().getId().getOrgId() != null
					&& mandate.getCdtrSchmeId().getId().getOrgId().getOthr() != null)
			{
				if(mandate.getCdtrSchmeId().getId().getOrgId().getOthr().get(0) != null && mandate.getCdtrSchmeId().getId().getOrgId().getOthr().get(0).getId() != null)
					casOpsCessionAssignEntity.setCredSchemeId(mandate.getCdtrSchmeId().getId().getOrgId().getOthr().get(0).getId().trim());
			}

			if(mandate.getCdtr() != null && mandate.getCdtr().getNm() != null)
				casOpsCessionAssignEntity.setCreditorName(mandate.getCdtr().getNm().trim());

			if(mandate.getCdtr() != null&& mandate.getCdtr().getCtctDtls() != null&& mandate.getCdtr().getCtctDtls().getPhneNb() != null)
				casOpsCessionAssignEntity.setCredPhoneNr(mandate.getCdtr().getCtctDtls().getPhneNb().trim());

			if(mandate.getCdtr() != null&& mandate.getCdtr().getCtctDtls() != null&& mandate.getCdtr().getCtctDtls().getEmailAdr() != null)
				casOpsCessionAssignEntity.setCredEmailAddr(mandate.getCdtr().getCtctDtls().getEmailAdr());

			if(mandate.getCdtrAcct() != null&& mandate.getCdtrAcct().getId() != null&& mandate.getCdtrAcct().getId().getOthr() != null&& mandate.getCdtrAcct().getId().getOthr().getId() != null)
				casOpsCessionAssignEntity.setCredAccNum(mandate.getCdtrAcct().getId().getOthr().getId().trim());

			if(mandate.getCdtrAgt() != null&& mandate.getCdtrAgt().getFinInstnId() != null&& mandate.getCdtrAgt().getFinInstnId().getClrSysMmbId() != null&& mandate.getCdtrAgt().getFinInstnId().getClrSysMmbId().getMmbId() != null)
				casOpsCessionAssignEntity.setCredBranchNr(mandate.getCdtrAgt().getFinInstnId().getClrSysMmbId().getMmbId().trim());

			if(mandate.getUltmtCdtr() != null&& mandate.getUltmtCdtr().getNm() != null)
				casOpsCessionAssignEntity.setUltCredName(mandate.getUltmtCdtr().getNm());

			if(mandate.getUltmtCdtr() != null && mandate.getUltmtCdtr().getId() != null && mandate.getUltmtCdtr().getId().getOrgId() != null && mandate.getUltmtCdtr().getId().getOrgId().getOthr() != null 
					&&  mandate.getUltmtCdtr().getId().getOrgId().getOthr().size() > 0)
			{
				if(mandate.getUltmtCdtr().getId().getOrgId().getOthr().get(0) != null && mandate.getUltmtCdtr().getId().getOrgId().getOthr().get(0).getId() != null) {
					casOpsCessionAssignEntity.setCredAbbShortName(mandate.getUltmtCdtr().getId().getOrgId().getOthr().get(0).getId().trim());
					credAbbShortName = mandate.getUltmtCdtr().getId().getOrgId().getOthr().get(0).getId().trim();
				}
			}

			//			=============SETTING DEBTOR INFORMATION ============= //
			if(mandate.getDbtr() != null && mandate.getDbtr().getNm() != null)
				casOpsCessionAssignEntity.setDebtorName(mandate.getDbtr().getNm().trim());

			if(mandate.getDbtr() != null && mandate.getDbtr().getId() != null && mandate.getDbtr().getId().getPrvtId() != null && mandate.getDbtr().getId().getPrvtId().getOthr() != null)
			{
				if(mandate.getDbtr().getId().getPrvtId().getOthr().get(0) != null && mandate.getDbtr().getId().getPrvtId().getOthr().get(0).getId() != null)
					casOpsCessionAssignEntity.setDebtorId(mandate.getDbtr().getId().getPrvtId().getOthr().get(0).getId().trim());
			}

			if(mandate.getDbtr() != null&& mandate.getDbtr().getCtctDtls() != null&& mandate.getDbtr().getCtctDtls().getPhneNb() != null)
				casOpsCessionAssignEntity.setDebtPhoneNr(mandate.getDbtr().getCtctDtls().getPhneNb());

			if(mandate.getDbtr() != null&& mandate.getDbtr().getCtctDtls() != null&& mandate.getDbtr().getCtctDtls().getEmailAdr() != null)
				casOpsCessionAssignEntity.setDebtEmailAddr(mandate.getDbtr().getCtctDtls().getEmailAdr());

			if(mandate.getDbtrAcct() != null&& mandate.getDbtrAcct().getId() != null&& mandate.getDbtrAcct().getId().getOthr() != null&& mandate.getDbtrAcct().getId().getOthr().getId() != null)
				casOpsCessionAssignEntity.setDebtAccNum(mandate.getDbtrAcct().getId().getOthr().getId().trim());

			if(mandate.getDbtrAcct() != null&& mandate.getDbtrAcct().getTp() != null&& mandate.getDbtrAcct().getTp().getPrtry() != null)
				casOpsCessionAssignEntity.setDebtAccType(mandate.getDbtrAcct().getTp().getPrtry().trim());

			if(mandate.getDbtrAgt() != null && mandate.getDbtrAgt().getFinInstnId() != null && mandate.getDbtrAgt().getFinInstnId().getClrSysMmbId().getMmbId() != null) 
				casOpsCessionAssignEntity.setDebtBranchNr(mandate.getDbtrAgt().getFinInstnId().getClrSysMmbId().getMmbId().trim());

			if(mandate.getUltmtDbtr() != null&& mandate.getUltmtDbtr().getNm() != null)
				casOpsCessionAssignEntity.setUltDebtName(mandate.getUltmtDbtr().getNm());

			//			=============SETTING ORIGINAL MANDATE INFORMATION ============= //			
			if(origMandate.getMndtId() != null)
				casOpsCessionAssignEntity.setOrigMandateId(origMandate.getMndtId());

			if(origMandate.getMndtReqId() != null)
				casOpsCessionAssignEntity.setOrigContractRef(origMandate.getMndtReqId());

			if(origMandate.getCdtr() != null && origMandate.getCdtr().getNm() != null)
				casOpsCessionAssignEntity.setOrigCredName(origMandate.getCdtr().getNm());

			if(origMandate.getCdtr() != null && origMandate.getCdtr().getId() != null && origMandate.getCdtr().getId().getOrgId() != null && origMandate.getCdtr().getId().getOrgId().getOthr() != null &&  origMandate.getCdtr().getId().getOrgId().getOthr().size() > 0)
			{
				if(origMandate.getCdtr().getId().getOrgId().getOthr().get(0) != null && origMandate.getCdtr().getId().getOrgId().getOthr().get(0).getId() != null)
				{	
					casOpsCessionAssignEntity.setOrigMandReqTranId(origMandate.getCdtr().getId().getOrgId().getOthr().get(0).getId());
				}
			}

			if(origMandate.getDbtr() != null && origMandate.getDbtr().getNm() != null)
				casOpsCessionAssignEntity.setOrigDebtName(origMandate.getDbtr().getNm());

			if(origMandate.getDbtrAgt() != null && origMandate.getDbtrAgt().getFinInstnId() != null 
					&& origMandate.getDbtrAgt().getFinInstnId().getClrSysMmbId() != null && origMandate.getDbtrAgt().getFinInstnId().getClrSysMmbId().getMmbId() != null)
			{
				casOpsCessionAssignEntity.setOrigDebtBranch(origMandate.getDbtrAgt().getFinInstnId().getClrSysMmbId().getMmbId());
			}

			//			=============SETTING SUPPLEMENTARY DATA INFORMATION ============= //
			if(supplmtryData != null&& supplmtryData.getEnvlp() != null&& supplmtryData.getEnvlp().getCnts() != null&& supplmtryData.getEnvlp().getCnts().getAthntctnTp() != null)
				casOpsCessionAssignEntity.setAuthType(supplmtryData.getEnvlp().getCnts().getAthntctnTp().toString().trim());

			if(supplmtryData != null&& supplmtryData.getEnvlp() != null&& supplmtryData.getEnvlp().getCnts() != null&& supplmtryData.getEnvlp().getCnts().getCllctnDy() != null)
				casOpsCessionAssignEntity.setCollectionDay(supplmtryData.getEnvlp().getCnts().getCllctnDy().toString().trim());

			if(supplmtryData != null&& supplmtryData.getEnvlp() != null&& supplmtryData.getEnvlp().getCnts() != null&& supplmtryData.getEnvlp().getCnts().getDtAdjRl() != null)
				casOpsCessionAssignEntity.setDateAdjRuleInd(supplmtryData.getEnvlp().getCnts().getDtAdjRl().toString().trim());

			if (supplmtryData != null&& supplmtryData.getEnvlp() != null&& supplmtryData.getEnvlp().getCnts() != null&& supplmtryData.getEnvlp().getCnts().getAdjstCtgy() != null)
				casOpsCessionAssignEntity.setAdjCategory(supplmtryData.getEnvlp().getCnts().getAdjstCtgy().toString().trim());

			if(supplmtryData != null && supplmtryData.getEnvlp() != null && supplmtryData.getEnvlp().getCnts() != null && supplmtryData.getEnvlp().getCnts().getAdjstRt() != null)
				casOpsCessionAssignEntity.setAdjRate(supplmtryData.getEnvlp().getCnts().getAdjstRt());

			if (supplmtryData != null&& supplmtryData.getEnvlp() != null&& supplmtryData.getEnvlp().getCnts() != null&& supplmtryData.getEnvlp().getCnts().getAdjstAmt() != null&& supplmtryData.getEnvlp().getCnts().getAdjstAmt().getCcy() != null)
				casOpsCessionAssignEntity.setAdjAmountCurr(supplmtryData.getEnvlp().getCnts().getAdjstAmt().getCcy());

			if (supplmtryData != null && supplmtryData.getEnvlp() != null && supplmtryData.getEnvlp().getCnts() != null&& supplmtryData.getEnvlp().getCnts().getAdjstAmt() != null)
				casOpsCessionAssignEntity.setAdjAmount(supplmtryData.getEnvlp().getCnts().getAdjstAmt().getValue());

			if (supplmtryData != null && supplmtryData.getEnvlp() != null && supplmtryData.getEnvlp().getCnts() != null&& supplmtryData.getEnvlp().getCnts().getMndtRfNbr() != null)
				casOpsCessionAssignEntity.setMandateRefNr(supplmtryData.getEnvlp().getCnts().getMndtRfNbr().toString().trim());

			if(supplmtryData != null&& supplmtryData.getEnvlp() != null&& supplmtryData.getEnvlp().getCnts() != null&& supplmtryData.getEnvlp().getCnts().getFrstColltnAmt() != null&& supplmtryData.getEnvlp().getCnts().getFrstColltnAmt().getCcy() != null)
				casOpsCessionAssignEntity.setFirstCollAmtCurr(supplmtryData.getEnvlp().getCnts().getFrstColltnAmt().getCcy());

			if(supplmtryData != null&& supplmtryData.getEnvlp() != null&& supplmtryData.getEnvlp().getCnts() != null&& supplmtryData.getEnvlp().getCnts().getFrstColltnAmt() != null)
				casOpsCessionAssignEntity.setFirstCollAmt(supplmtryData.getEnvlp().getCnts().getFrstColltnAmt().getValue());

			if(supplmtryData != null&& supplmtryData.getEnvlp() != null&& supplmtryData.getEnvlp().getCnts() != null&& supplmtryData.getEnvlp().getCnts().getDbVlTp() != null)
			{
				String debitValueType = supplmtryData.getEnvlp().getCnts().getDbVlTp().toString().trim();
				if(debitValueType.equalsIgnoreCase("USAGE_BASED"))
					debitValueType = "USAGE BASED";
				casOpsCessionAssignEntity.setDebitValueType(debitValueType);
			}

			casOpsCessionAssignEntity.setCreatedBy(systemName);
			casOpsCessionAssignEntity.setCreatedDate(todaysDate);
			casOpsCessionAssignEntity.setModifiedBy(systemName);
			casOpsCessionAssignEntity.setModifiedDate(todaysDate);

			acceptedMndtList.add(casOpsCessionAssignEntity);
			
			//==========TXN BILLING TRANSACTIONS====================//
					CasOpsTxnsBillReportEntity
							casOpsTxnsBillReportEntity = new CasOpsTxnsBillReportEntity();
					
					casOpsTxnsBillReportEntity.setSystemSeqNo(new BigDecimal(123));
					
					casOpsTxnsBillReportEntity.setProcessDate(casSysctrlSysParamEntity.getProcessDate());
					
					CasOpsFileRegEntity casOpsFileRegEntity = (CasOpsFileRegEntity) valBeanRemote.retrieveOpsFileReg(fileName);
					
					if(casOpsFileRegEntity != null && casOpsFileRegEntity.getFileName() != null)
					{
						casOpsTxnsBillReportEntity.setDeliveryTime(casOpsFileRegEntity.getProcessDate());
					}

					casOpsTxnsBillReportEntity.setFileName(fileName);
					casOpsTxnsBillReportEntity.setOriginator(document.getMndtAmdmntReq().getGrpHdr().getInstgAgt().getFinInstnId().getClrSysMmbId().getMmbId().trim());
					casOpsTxnsBillReportEntity.setMandateReqTranId(mandate.getCdtr().getId().getOrgId().getOthr().get(0).getId());
					casOpsTxnsBillReportEntity.setSubService(serviceID);
					casOpsTxnsBillReportEntity.setTxnType(tt2TxnType);
					casOpsTxnsBillReportEntity.setTxnStatus("ACCP");
					opsTxnsBillReportList.add(casOpsTxnsBillReportEntity);

			//will be replaced with bulk inserts
			//			try
			//			{
			//				isLoaded = fileProcessBeanRemote.createAcOpsMandateTxns(mdtAcOpsMandateTxnsEntity, origMandate.getDbtrAgt().getFinInstnId().getClrSysMmbId().getMmbId(), credAbbShortName, supplmtryData.getEnvlp().getCnts().getMndtRfNbr().toString());
			//				log.debug("XXXXXX~~~~~~~~~~~~~~~isLoaded -----> "+isLoaded);
			//			}
			//			catch (Exception e) 
			//			{
			//				isLoaded = false;
			//				log.error("Error on createMandate: " + e.getMessage());
			//				e.printStackTrace();
			//			}

		}//end of if(mandate != null)
	}// End of CreateMandate

	public void saveBulkMandates() {
		isLoaded = false;
		long loadStart, loadEnd, loadDur;
		loadStart = System.nanoTime();
		fileProcessBeanRemote.openHibernateSession();

		try
		{	
			log.info("====================BULK INSERT CARIN TXNS ====================");
			fileProcessBeanRemote.bulkSaveMandates(acceptedMndtList);		
			log.info("====================BULK INSERT CARIN TXN BILL ====================");
			fileProcessBeanRemote.bulkSaveMandates(txnsBillList);
			loadStart = System.nanoTime();
			log.info("====================BULK INSERT CARIN TXN BILL REPORT DATA ====================");
			fileProcessBeanRemote.bulkSaveMandates(opsTxnsBillReportList);
			loadEnd = System.nanoTime();
			loadDur = (loadEnd - loadStart) / 1000000;
			log.info("|BULK SAVE BILLING REPORT DATA "+fileName+" IS "+DurationFormatUtils.formatDuration(loadDur, "HH:mm:ss.S")+" milliseconds |");
		}
		catch (Exception e) 
		{
			isLoaded = false;
			log.error("Error on saveBulkMandates: " + e.getMessage());
			e.printStackTrace();
		}
		fileProcessBeanRemote.closeHibernateSession();
		//		log.debug("<<<<<======PAIN.009 =="+mdtAcOpsMandateTxnsEntity.getMdtAcOpsMandateTxnsEntityPK().getMandateReqTranId()+"== LOAD COMPLETED======>>>>>");
	}

	private Date getFileDate(String fileName) 
	{
		try {
			String tempDate = fileName.substring(17, 25);

			DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			DateFormat oraDateFmt = new SimpleDateFormat("yyyy-MM-dd");

			Date date = null;
			Date oraDate = null;
			try 
			{
				date = dateFormat.parse(tempDate);
				String strOraDate = oraDateFmt.format(date);
				oraDate = oraDateFmt.parse(strOraDate);
			} 
			catch (ParseException e) 
			{
				e.printStackTrace();
			}

			return oraDate;

		} catch (Exception e) {

			log.error("Error file name incorrect ");
		}
		return null;
	}


	private void generateMndtCount(int acceptCount, int rejectedCount) 
	{
		todaysDate= new Date();

		boolean saved = false;
		int nrOfFile =1;
		int nrOfMsgsInFile = underLyingMandates.size();

		CasOpsMndtCountEntity casOpsMndtCountEntity = new CasOpsMndtCountEntity();
		CasOpsMndtCountPK casOpsMndtCountPk = new CasOpsMndtCountPK();

		if(document!= null && document.getMndtAmdmntReq()!=null && document.getMndtAmdmntReq().getGrpHdr() != null && document.getMndtAmdmntReq().getGrpHdr().getMsgId()!=null)
			casOpsMndtCountPk.setMsgId(document.getMndtAmdmntReq().getGrpHdr().getMsgId());
		casOpsMndtCountPk.setServiceId("CARIN");
		if(document!= null && document.getMndtAmdmntReq()!=null && document.getMndtAmdmntReq().getGrpHdr() != null && document.getMndtAmdmntReq().getGrpHdr().getMsgId()!=null)
			casOpsMndtCountPk.setInstId(document.getMndtAmdmntReq().getGrpHdr().getMsgId().toString().substring(12, 18));
		casOpsMndtCountEntity.setNrOfMsgs(nrOfMsgsInFile);
		casOpsMndtCountEntity.setNrOfFiles(nrOfFile);
		casOpsMndtCountEntity.setIncoming("Y");
		casOpsMndtCountEntity.setProcessDate(todaysDate);
		casOpsMndtCountEntity.setOutgoing("N");
		casOpsMndtCountEntity.setCasOpsMndtCountPK(casOpsMndtCountPk);
		casOpsMndtCountEntity.setNrMsgsAccepted(acceptCount);
		casOpsMndtCountEntity.setNrMsgsRejected(rejectedCount);
		casOpsMndtCountEntity.setNrMsgsExtracted(0);
		casOpsMndtCountEntity.setFileName(fileName.substring(0,37).trim());
		log.info("casOpsMndtCountEntity: "+casOpsMndtCountEntity);
		saved = valBeanRemote.saveMdtOpsMndtCount(casOpsMndtCountEntity);

		if (saved) {
			log.debug("MdtOpsCountTable has been updated");

		} else {
			log.debug("MdtOpsCountTable is not updated");
		}
	}


	private void generateTxnsBilling() {

		todaysDate= new Date();
		txnsBillList = new ArrayList<CasOpsTxnsBillingEntity>();

		boolean saved = false;
		int nrOfFile =1;
		int nrOfMsgsInFile = underLyingMandates.size();

		log.debug("# of mandates submitted ******--->" + underLyingMandates.size());

		casOpsTxnsBillingEntity = new CasOpsTxnsBillingEntity();
		casOpsTxnsBillingPK = new CasOpsTxnsBillingPK();

		//mdtAcOpsTxnsBillingPK.setSystemSeqNo(new BigDecimal(123));
		if(document.getMndtAmdmntReq() != null&& document.getMndtAmdmntReq().getGrpHdr() != null&& document.getMndtAmdmntReq().getGrpHdr().getInstgAgt().getFinInstnId() != null&& document.getMndtAmdmntReq().getGrpHdr().getInstgAgt().getFinInstnId().getClrSysMmbId() != null&& document.getMndtAmdmntReq().getGrpHdr().getInstgAgt().getFinInstnId().getClrSysMmbId().getMmbId() != null) 
			casOpsTxnsBillingEntity.setOriginator(document.getMndtAmdmntReq().getGrpHdr().getInstgAgt().getFinInstnId().getClrSysMmbId().getMmbId().trim());
		casOpsTxnsBillingEntity.setService(systemService);
		casOpsTxnsBillingEntity.setSubService(serviceID);
		casOpsTxnsBillingEntity.setTxnType(tt2TxnType);
		casOpsTxnsBillingEntity.setTxnStatus(tt2Succ);
		casOpsTxnsBillingPK.setFileName(fileName.substring(0,37).trim());
		casOpsTxnsBillingEntity.setStatus(inInd);
		casOpsTxnsBillingEntity.setVolume(Long.valueOf(nrOfMsgsInFile));
		casOpsTxnsBillingEntity.setBillExpStatus(nonActInd);
		casOpsTxnsBillingEntity.setSystemName(systemService);
		casOpsTxnsBillingEntity.setCreatedBy(systemName);
		casOpsTxnsBillingEntity.setCreatedDate(todaysDate);
		casOpsTxnsBillingEntity.setModifiedBy(systemName);
		casOpsTxnsBillingEntity.setModifiedDate(todaysDate);
		casOpsTxnsBillingPK.setActionDate(getCovertDateTime(document.getMndtAmdmntReq().getGrpHdr().getCreDtTm()));
		casOpsTxnsBillingEntity.setCasOpsTxnsBillingPK(casOpsTxnsBillingPK);

		if(casSysctrlSysParamEntity != null)
		{
			casOpsTxnsBillingEntity.setRespDate(casSysctrlSysParamEntity.getProcessDate());
		}
		else
		{
			casOpsTxnsBillingEntity.setRespDate(todaysDate);
		}

		//Save Billing
		log.info("THIS IS THE OPS TXNS BILLING ENTITY==> "+ casOpsTxnsBillingEntity);
		//beanRemote.saveAcOpsTxnBilling(mdtAcOpsTxnsBillingEntity);
		txnsBillList.add(casOpsTxnsBillingEntity);


	}

	public Date getCovertDateTime(XMLGregorianCalendar xmlGregorianCalendar) {
		Calendar cl = xmlGregorianCalendar.toGregorianCalendar();
		return cl.getTime();
	}

	private static void contextAdminBeanCheck() {
		if (adminBeanRemote == null) {
			adminBeanRemote = Util.getAdminBean();
		}
	}

	private static void contextCheck() {
		if (beanRemote == null) {
			beanRemote = Util.getServiceBean();
		}
	}

	public static void contextValidationBeanCheck() {
		if (valBeanRemote == null) {
			valBeanRemote = Util.getValidationBean();
		}
	}

	public static void contextFileProcBeanCheck() {
		if (fileProcessBeanRemote == null) {
			fileProcessBeanRemote = Util.getFileProcessBean();
		}
	}

}
