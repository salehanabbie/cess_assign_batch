package com.bsva.authcoll.singletable.file;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.ejb.EJB;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.apache.log4j.Logger;
import com.bsva.PropertyUtil;
import com.bsva.authcoll.singletable.validation.AC_Pain012_Validation_ST;
import com.bsva.entities.CasOpsDailyBillingEntity;
import com.bsva.entities.CasOpsGrpHdrEntity;
import com.bsva.entities.CasOpsCessionAssignEntity;
import com.bsva.entities.CasOpsCessionAssignEntityPK;
import com.bsva.entities.CasOpsMndtCountEntity;
import com.bsva.entities.CasOpsMndtCountPK;
import com.bsva.entities.CasOpsStatusHdrsEntity;
import com.bsva.entities.CasOpsFileRegEntity;
import com.bsva.entities.CasSysctrlSysParamEntity;
import com.bsva.interfaces.AdminBeanRemote;
import com.bsva.interfaces.FileProcessBeanRemote;
import com.bsva.interfaces.ServiceBeanRemote;
import com.bsva.interfaces.ValidationBeanRemote;
import com.bsva.utils.PainUnmarshaller;
import com.bsva.utils.Util;
import iso.std.iso._20022.tech.xsd.pain_012_001.AcceptanceResult6;
import iso.std.iso._20022.tech.xsd.pain_012_001.Authorisation1Choice;
import iso.std.iso._20022.tech.xsd.pain_012_001.Document;
import iso.std.iso._20022.tech.xsd.pain_012_001.Mandate1;
import iso.std.iso._20022.tech.xsd.pain_012_001.MandateAcceptance3;
import iso.std.iso._20022.tech.xsd.pain_012_001.SupplementaryData1;

/*
 * @author DimakatsoN
 * Modified By- SalehaR - 2016-09-13 - Alignment to TRS 15
 * SalehaR - 2017-01-06 - Duplicate Checking 
 * @author SalehaR-2019/05/10 Debug Statements & Code CleanUp
 * @author SalehaR-2019-09-17 Load to Single Table(MDT_AC_OPS_MANDATE_TXNS)
 */
public class AC_Pain012_Loader_ST implements Serializable {
	private static final long serialVersionUID = 1L;
	public static Logger log = Logger.getLogger("AC_Pain012_Loader_ST");

	@EJB
	PropertyUtil propertyUtil;

	private CasOpsGrpHdrEntity casOpsGrphdrEntity;
	private CasOpsCessionAssignEntity casOpsCessionAssignEntity;

	private List<MandateAcceptance3> mandateAcceptance3List;
	private List<Authorisation1Choice> authorisation1ChoiceList;
	public static String systemName = "MANOWNER";
	public  Date todaysDate;
	public static AdminBeanRemote adminBeanRemote;
	public static ServiceBeanRemote beanRemote;
	public static ValidationBeanRemote valBeanRemote;
	public static FileProcessBeanRemote fileProcessBeanRemote;
	private CasSysctrlSysParamEntity casSysctrlSysParamEntity = null;
	private String sadcSystem = "SADC";
	String xmlDateFormat = "yyyyMMdd";
	private String pain012Schema = "/home/opsjava/Delivery/Mandates/Schema/pain.012.001.03.xsd";
	private String mandateReqTranId = null, pain012Service,tt2TxnType, tt2Succ, tt2Unsucc,nonActInd, maninService, manamService, mancnService, messageId = null, rdyForExtStatus, loadStatus;
	List<CasOpsCessionAssignEntity> mandatesList;

	AC_Pain012_Validation_ST acPain012Validation_ST;

	public static boolean result, unmarshall = false,archive = false, origDeleted = false, grpHdrValidated = false, grpHdrCreated= false, isLoaded = false;

	Document document;
	String fileName;
	int mdtRejCnt =0;
	long startTime, endTime, duration;
	List<CasOpsCessionAssignEntity> acceptedMndtList =new ArrayList<CasOpsCessionAssignEntity>();
//	List<MdtAcOpsMandateTxnsEntity> matchedMndtList =new ArrayList<MdtAcOpsMandateTxnsEntity>();
	List<CasOpsDailyBillingEntity> billableTxnList = new ArrayList<CasOpsDailyBillingEntity>();
	List<String> matchedMRTIList = null;
	List<String> rejectedMRTIList = null;

	public AC_Pain012_Loader_ST(String filepath, String fileName) 
	{
		startTime = System.nanoTime();

		contextCheck();
		contextAdminBeanCheck();
		contextValidationBeanCheck();
		contextFileProcBeanCheck();
		this.fileName = fileName;
		casSysctrlSysParamEntity = new CasSysctrlSysParamEntity();
		casSysctrlSysParamEntity = (CasSysctrlSysParamEntity) adminBeanRemote.retrieveActiveSysParameter();
		mandateReqTranId = null;
		try
		{
			propertyUtil = new PropertyUtil();
			pain012Service = propertyUtil.getPropValue("Input.Pain012");
			tt2TxnType = propertyUtil.getPropValue("AC.TT2SubService");
			tt2Succ = propertyUtil.getPropValue("AC.Bill.TxnSucc");
			tt2Unsucc = propertyUtil.getPropValue("AC.Bill.TxnUnSucc");
			nonActInd = propertyUtil.getPropValue("NonActiveInd");
			maninService = propertyUtil.getPropValue("ACRT.Input.Pain009");
			manamService = propertyUtil.getPropValue("ACRT.Input.Pain010");
			mancnService = propertyUtil.getPropValue("ACRT.Input.Pain011");
			this.rdyForExtStatus= propertyUtil.getPropValue("ProcStatus.ReadyForExtract");
			this.loadStatus = propertyUtil.getPropValue("ProcStatus.Loaded");
		}
		catch (Exception e) 
		{
			log.error("AC_Pain012_Loader_ST - Could not find MandateMessageCommons.properties in classpath");
		}

		try
		{
			document = (Document) PainUnmarshaller.unmarshallFile(filepath, "iso.std.iso._20022.tech.xsd.pain_012_001", fileName, pain012Schema);
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
				casOpsFileRegEntity.setGrpHdrMsgId(document.getMndtAccptncRpt().getGrpHdr().getMsgId());
				try {
					casOpsFileRegEntity.setStatus(propertyUtil.getPropValue("ProcStatus.Validating"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				valBeanRemote.updateOpsFileReg(casOpsFileRegEntity);
			}
			else
			{
				if(casOpsFileRegEntity != null)
				{
					casOpsFileRegEntity.setStatus("FS");
					valBeanRemote.updateOpsFileReg(casOpsFileRegEntity);
				}
			}

			// _______________________Date____________________________________
			todaysDate = new Date();

			// _______________________Mandate Models_______________________
			casOpsGrphdrEntity = new CasOpsGrpHdrEntity();
			matchedMRTIList = new ArrayList<String>();
			rejectedMRTIList = new ArrayList<String>();

			// _______________________XSD Lists_______________________
			authorisation1ChoiceList = new ArrayList<Authorisation1Choice>();
			authorisation1ChoiceList = document.getMndtAccptncRpt().getGrpHdr().getAuthstn();

			mandateAcceptance3List = new ArrayList<MandateAcceptance3>();
			mandateAcceptance3List = document.getMndtAccptncRpt().getUndrlygAccptncDtls();

			// _______________________Mandate grpHdr Unmarshall_______________________
			acPain012Validation_ST = new AC_Pain012_Validation_ST(fileName);

			int grpHdrSeverity = acPain012Validation_ST.validateGroupHeader(document.getMndtAccptncRpt().getGrpHdr(), mandateAcceptance3List.size());

			/*grpHdrSeverity = 1;*/
			if(grpHdrSeverity == 0)
			{
				casOpsGrphdrEntity.setCreateDateTime(getCovertDateTime(document.getMndtAccptncRpt().getGrpHdr().getCreDtTm()));

				casOpsGrphdrEntity.setCreatedBy(systemName);
				casOpsGrphdrEntity.setMsgId(document.getMndtAccptncRpt().getGrpHdr().getMsgId().trim());

				grpHdrCreated = beanRemote.createMdtAcOpsGrpHdrEntity(casOpsGrphdrEntity);
			}
			else
			{
				try {
					casOpsFileRegEntity.setStatus(propertyUtil.getPropValue("ProcStatus.FailedGroupHeader"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				valBeanRemote.updateOpsFileReg(casOpsFileRegEntity);
			}
			long ghValEndTime = System.nanoTime();
			long ghValDur = (ghValEndTime - startTime) / 1000000;
//			log.info("GH VALIDATION "+DurationFormatUtils.formatDuration(ghValDur, "HH:mm:ss.S")+" milliseconds. ");
			log.info("grpHdrSeverity: "+grpHdrSeverity);

			if ((grpHdrCreated) && (grpHdrSeverity == 0)) 
			{
				int accptCnt = 0;
				int rejCnt = 0;
				int nrOfMndtsInFile = mandateAcceptance3List.size();
				String intParty = null;
				if(document.getMndtAccptncRpt() != null && document.getMndtAccptncRpt().getGrpHdr() != null && document.getMndtAccptncRpt().getGrpHdr().getInitgPty() != null && document.getMndtAccptncRpt().getGrpHdr().getInitgPty().getNm() != null) {
					intParty = document.getMndtAccptncRpt().getGrpHdr().getInitgPty().getNm();
				}

				long valStartTime = System.nanoTime();
//				log.info("XxXxXxX VALIDATING TRANSACTIONS IN FILE "+fileName+ " XxXxXxX");

				List<MandateAcceptance3> uniqueList = (List<MandateAcceptance3>) acPain012Validation_ST.findDuplicateTxns(mandateAcceptance3List);
				long duplEndTime = System.nanoTime();
				long duplDur = (duplEndTime - valStartTime) / 1000000;
//				log.info("DUPLICATES IN FILE EXECUTION TIME IS "+DurationFormatUtils.formatDuration(duplDur, "HH:mm:ss.S")+" milliseconds. ");

				if(uniqueList != null && uniqueList.size() > 0) {
					rejCnt = rejCnt + (nrOfMndtsInFile - uniqueList.size());

					for (MandateAcceptance3 mandateAcceptance3: uniqueList) 
					{
						int mandateSeverity = acPain012Validation_ST.validateMandate(mandateAcceptance3.getOrgnlMndt().getOrgnlMndt(),mandateAcceptance3.getAccptncRslt(), mandateAcceptance3.getSplmtryData());

//						log.info/*debug*/("mandateSeverity: "+ mandateSeverity);

						if(mandateSeverity == 0)
						{
							createMandate(mandateAcceptance3.getOrgnlMndt().getOrgnlMndt(), mandateAcceptance3.getAccptncRslt(), acPain012Validation_ST.mdtAcMandateTxnsEntityOriginal, mandateAcceptance3.getSplmtryData(), intParty);
							//							//								accptCnt = accptCnt + 1;
							//							if(isLoaded == true)
							//							{
							//								accptCnt = accptCnt + 1;
							//							}
							//							else
							//							{
							//								rejCnt = rejCnt + 1;
							//							}
						}
						else
						{
							rejCnt = rejCnt + 1;
						}
						//						  log.info("the count of accepted MANAC files is "+accptCnt);
						//						  log.info("mdtRejCnt:##################### " + rejCnt);
					}
				}

				//Reset for next file process
				grpHdrCreated = false;
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

				log.debug("$$$$$$$$$$$$CHECK DATA$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
				log.debug("nrOfMndtsInFile: "+nrOfMndtsInFile);
				log.debug("accptCnt: "+accptCnt);
				log.debug("rejCnt: "+rejCnt);

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
							log.info("propertyCache.getProperty)RJCT--->"+propertyUtil.getPropValue("Pacs002Status.RJCT"));
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						try {
							grpStatus = propertyUtil.getPropValue("Pacs002Status.RJCT");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					else
					{
						if((accptCnt > 0 && accptCnt != nrOfMndtsInFile) && (rejCnt > 0 && rejCnt != nrOfMndtsInFile))
						{
							try {
								log.debug("propertyUtil.getPropValue)PART--->"+propertyUtil.getPropValue("Pacs002Status.PART"));
							} catch (IOException e1) {
								e1.printStackTrace();
							}
							try {
								grpStatus = propertyUtil.getPropValue("Pacs002Status.PART");
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				}

				//Update Status Hdrs
				BigDecimal hdrSeqNo = acPain012Validation_ST.hdrSystemSeqNo;
				CasOpsStatusHdrsEntity casOpsStatusHdrsEntity = (CasOpsStatusHdrsEntity) beanRemote.retrieveStatusHdrsBySeqNo(hdrSeqNo);
				log.debug("GROUP STATUS---> "+grpStatus);
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
					casOpsFileRegEntity.setStatus("FG");
					valBeanRemote.updateOpsFileReg(casOpsFileRegEntity);
				}
			}
		}
		endTime = System.nanoTime();
		duration = (endTime - startTime) / 1000000;
		log.info("|TOTAL EXECUTION TIME FOR "+fileName+" IS "+DurationFormatUtils.formatDuration(duration, "HH:mm:ss.S")+" milliseconds |");

	}	

	public void createMandate(Mandate1 mandate, AcceptanceResult6 acceptanceResult6, CasOpsCessionAssignEntity originalMandate, SupplementaryData1 supplementaryData1, String initParty)
	{
		messageId = null;
		String mndtRfNum = null;
		String origBillService = null;

		// _______________________Mandate MandateRegister Unmarshall_______________________
		if(mandate.getCdtr() != null && mandate.getCdtr().getId() != null && mandate.getCdtr().getId().getOrgId() != null && mandate.getCdtr().getId().getOrgId().getOthr() != null &&  mandate.getCdtr().getId().getOrgId().getOthr().size() > 0)
		{
			if(mandate.getCdtr().getId().getOrgId().getOthr().get(0) != null && mandate.getCdtr().getId().getOrgId().getOthr().get(0).getId() != null)
			{
				mandateReqTranId = mandate.getCdtr().getId().getOrgId().getOthr().get(0).getId().trim();
			}
		}

		if(supplementaryData1 != null && supplementaryData1.getEnvlp() != null && supplementaryData1.getEnvlp().getCnts() != null && supplementaryData1.getEnvlp().getCnts().getMndtRfNbr() != null)
		{
			mndtRfNum = supplementaryData1.getEnvlp().getCnts().getMndtRfNbr().toString();
		}

		if(mandate != null)
		{

			CasOpsCessionAssignEntityPK
					casOpsCessionAssignEntityPK = new CasOpsCessionAssignEntityPK();
			casOpsCessionAssignEntity = new CasOpsCessionAssignEntity();
			//Set Primary Key
			if(document.getMndtAccptncRpt() != null &&  document.getMndtAccptncRpt().getGrpHdr() != null &&  document.getMndtAccptncRpt().getGrpHdr().getMsgId() !=null)
			{
				casOpsCessionAssignEntityPK.setMsgId(document.getMndtAccptncRpt().getGrpHdr().getMsgId().trim());
				messageId = document.getMndtAccptncRpt().getGrpHdr().getMsgId().trim();
			}

			if(mandateReqTranId != null)
				casOpsCessionAssignEntityPK.setMandateReqTranId(mandateReqTranId);

			casOpsCessionAssignEntity.setCasOpsCessionAssignEntityPK(casOpsCessionAssignEntityPK);

			//			=============SETTING PROCESSING INFORMATION============= //
			if(document.getMndtAccptncRpt() != null&& document.getMndtAccptncRpt().getGrpHdr() != null&& document.getMndtAccptncRpt().getGrpHdr().getInstgAgt().getFinInstnId() != null && 
					document.getMndtAccptncRpt().getGrpHdr().getInstgAgt().getFinInstnId().getClrSysMmbId() != null&& document.getMndtAccptncRpt().getGrpHdr().getInstgAgt().getFinInstnId().getClrSysMmbId().getMmbId() != null) 
				casOpsCessionAssignEntity.setDebtorBank(document.getMndtAccptncRpt().getGrpHdr().getInstgAgt().getFinInstnId().getClrSysMmbId().getMmbId().trim());

			if(acPain012Validation_ST.creditorBank != null)
				casOpsCessionAssignEntity.setCreditorBank(acPain012Validation_ST.creditorBank);

			casOpsCessionAssignEntity.setServiceId(pain012Service);
			casOpsCessionAssignEntity.setInFileName(fileName.substring(0,37).trim());
			casOpsCessionAssignEntity.setInFileDate(getFileDate(fileName.substring(0,37).trim()));
			casOpsCessionAssignEntity.setProcessStatus(rdyForExtStatus);
			if(initParty != null && !initParty.isEmpty())
				casOpsCessionAssignEntity.setInitParty(initParty);

			//			================ACCEPTANCE DETAILS======================//
			if(acceptanceResult6 != null)
			{
				log.debug("acceptanceResult6.getAccptd().toString(): "+ acceptanceResult6.isAccptd());
				String accepted = String.valueOf(acceptanceResult6.isAccptd());
				casOpsCessionAssignEntity.setAcceptedInd(accepted);
			}

			if(acceptanceResult6.getRjctRsn() != null && acceptanceResult6.getRjctRsn().getPrtry() != null)
				casOpsCessionAssignEntity.setRejectReasonCode(acceptanceResult6.getRjctRsn().getPrtry().toString());

			//			=============SETTING MANDATE DETAILS============= //
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

			if(mandate.getOcrncs() != null&& mandate.getOcrncs().getDrtn() != null&& mandate.getOcrncs().getDrtn().getFrDt() != null)
				casOpsCessionAssignEntity.setFromDate(getCovertDateTime(mandate.getOcrncs().getDrtn().getFrDt()));

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
				if(mandate.getUltmtCdtr().getId().getOrgId().getOthr().get(0) != null && mandate.getUltmtCdtr().getId().getOrgId().getOthr().get(0).getId() != null)
					casOpsCessionAssignEntity.setCredAbbShortName(mandate.getUltmtCdtr().getId().getOrgId().getOthr().get(0).getId().trim());
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

			//			=============SETTING REFERENCE DOC ============= //			
			if(mandate.getRfrdDoc()!= null && mandate.getRfrdDoc().getTp()!= null && mandate.getRfrdDoc().getTp().getCdOrPrtry()!= null && mandate.getRfrdDoc().getTp().getCdOrPrtry().getPrtry()!= null)
				casOpsCessionAssignEntity.setAuthStatusInd(mandate.getRfrdDoc().getTp().getCdOrPrtry().getPrtry().trim());
			
			if(mandate.getRfrdDoc()!= null && mandate.getRfrdDoc().getNb() != null)
				casOpsCessionAssignEntity.setMacCode(mandate.getRfrdDoc().getNb());
			

			//			=============SETTING SUPPLEMENTARY DATA INFORMATION ============= //
			if(supplementaryData1 != null&& supplementaryData1.getEnvlp() != null&& supplementaryData1.getEnvlp().getCnts() != null&& supplementaryData1.getEnvlp().getCnts().getAthntctnTp() != null)
				casOpsCessionAssignEntity.setAuthType(supplementaryData1.getEnvlp().getCnts().getAthntctnTp().toString().trim());

			if(supplementaryData1 != null&& supplementaryData1.getEnvlp() != null&& supplementaryData1.getEnvlp().getCnts() != null&& supplementaryData1.getEnvlp().getCnts().getCllctnDy() != null)
				casOpsCessionAssignEntity.setCollectionDay(supplementaryData1.getEnvlp().getCnts().getCllctnDy().toString().trim());

			if(supplementaryData1 != null&& supplementaryData1.getEnvlp() != null&& supplementaryData1.getEnvlp().getCnts() != null&& supplementaryData1.getEnvlp().getCnts().getDtAdjRl() != null)
				casOpsCessionAssignEntity.setDateAdjRuleInd(supplementaryData1.getEnvlp().getCnts().getDtAdjRl().toString().trim());

			if (supplementaryData1 != null&& supplementaryData1.getEnvlp() != null&& supplementaryData1.getEnvlp().getCnts() != null&& supplementaryData1.getEnvlp().getCnts().getAdjstCtgy() != null)
				casOpsCessionAssignEntity.setAdjCategory(supplementaryData1.getEnvlp().getCnts().getAdjstCtgy().toString().trim());

			if(supplementaryData1 != null && supplementaryData1.getEnvlp() != null && supplementaryData1.getEnvlp().getCnts() != null && supplementaryData1.getEnvlp().getCnts().getAdjstRt() != null)
				casOpsCessionAssignEntity.setAdjRate(supplementaryData1.getEnvlp().getCnts().getAdjstRt());

			if(supplementaryData1 != null&& supplementaryData1.getEnvlp() != null&& supplementaryData1.getEnvlp().getCnts() != null&& supplementaryData1.getEnvlp().getCnts().getAdjstAmt() != null
					&& supplementaryData1.getEnvlp().getCnts().getAdjstAmt().getCcy() != null)
				casOpsCessionAssignEntity.setAdjAmountCurr(supplementaryData1.getEnvlp().getCnts().getAdjstAmt().getCcy());

			if(supplementaryData1 != null && supplementaryData1.getEnvlp() != null && supplementaryData1.getEnvlp().getCnts() != null&& supplementaryData1.getEnvlp().getCnts().getAdjstAmt() != null)
				casOpsCessionAssignEntity.setAdjAmount(supplementaryData1.getEnvlp().getCnts().getAdjstAmt().getValue());

			if(supplementaryData1 != null && supplementaryData1.getEnvlp() != null && supplementaryData1.getEnvlp().getCnts() != null && supplementaryData1.getEnvlp().getCnts().getChnnl() != null)
				casOpsCessionAssignEntity.setAuthChannel(supplementaryData1.getEnvlp().getCnts().getChnnl().trim());

			if(supplementaryData1 != null && supplementaryData1.getEnvlp() != null && supplementaryData1.getEnvlp().getCnts() != null && supplementaryData1.getEnvlp().getCnts().getMndtRfNbr() != null)
				casOpsCessionAssignEntity.setMandateRefNr(supplementaryData1.getEnvlp().getCnts().getMndtRfNbr().toString());

			if(supplementaryData1 != null&& supplementaryData1.getEnvlp() != null&& supplementaryData1.getEnvlp().getCnts() != null&& supplementaryData1.getEnvlp().getCnts().getFrstColltnAmt() != null
					&& supplementaryData1.getEnvlp().getCnts().getFrstColltnAmt().getCcy() != null)
				casOpsCessionAssignEntity.setFirstCollAmtCurr(supplementaryData1.getEnvlp().getCnts().getFrstColltnAmt().getCcy());

			if(supplementaryData1 != null&& supplementaryData1.getEnvlp() != null&& supplementaryData1.getEnvlp().getCnts() != null&& supplementaryData1.getEnvlp().getCnts().getFrstColltnAmt() != null)
				casOpsCessionAssignEntity.setFirstCollAmt(supplementaryData1.getEnvlp().getCnts().getFrstColltnAmt().getValue());

			if(supplementaryData1 != null&& supplementaryData1.getEnvlp() != null&& supplementaryData1.getEnvlp().getCnts() != null&& supplementaryData1.getEnvlp().getCnts().getDbVlTp() != null)
				casOpsCessionAssignEntity.setDebitValueType(supplementaryData1.getEnvlp().getCnts().getDbVlTp().toString().trim());

			if(supplementaryData1!= null && supplementaryData1.getEnvlp()!= null && supplementaryData1.getEnvlp().getCnts()!= null && supplementaryData1.getEnvlp().getCnts().getMndtAthDt()!= null)
				casOpsCessionAssignEntity.setMandateAuthDate(getCovertDateTime(supplementaryData1.getEnvlp().getCnts().getMndtAthDt()));

			casOpsCessionAssignEntity.setCreatedBy(systemName);
			casOpsCessionAssignEntity.setCreatedDate(todaysDate);
			casOpsCessionAssignEntity.setModifiedBy(systemName);
			casOpsCessionAssignEntity.setModifiedDate(todaysDate);

			acceptedMndtList.add(casOpsCessionAssignEntity);
			
			if(originalMandate.getProcessStatus().equalsIgnoreCase("4") || originalMandate.getProcessStatus().equalsIgnoreCase("9"))
			{
				origBillService = originalMandate.getServiceId();
				if(acceptanceResult6 != null)
				{
					if(acceptanceResult6.isAccptd())
					{
						matchedMRTIList.add("'"+originalMandate.getCasOpsCessionAssignEntityPK().getMandateReqTranId()+"'");
//						originalMandate.setProcessStatus("M");
					}
					else
					{
//						originalMandate.setProcessStatus("R");
						rejectedMRTIList.add("'"+originalMandate.getCasOpsCessionAssignEntityPK().getMandateReqTranId()+"'");
					}
//					originalMandate.setModifiedDate(todaysDate);
					//					result = fileProcessBeanRemote.updateMdtOpsMandateTxns(originalMandate);
//					matchedMndtList.add(originalMandate);
				}
			}

			//			SalehaR-2020/08/19: Original Code
			//			==========MATCHED ORIGINAL TXNS UPDATE====================//
//			if(originalMandate.getProcessStatus().equalsIgnoreCase("4") || originalMandate.getProcessStatus().equalsIgnoreCase("9"))
//			{
//				origBillService = originalMandate.getServiceId();
//				if(acceptanceResult6 != null)
//				{
//					if(acceptanceResult6.isAccptd())
//					{
//						originalMandate.setProcessStatus("M");
//					}
//					else
//					{
//						originalMandate.setProcessStatus("R");
//					}
//					originalMandate.setModifiedDate(todaysDate);
//					//					result = fileProcessBeanRemote.updateMdtOpsMandateTxns(originalMandate);
//					matchedMndtList.add(originalMandate);
//				}
//			}

			//			==========BILLABLE TRANSACTIONS====================//
			CasOpsDailyBillingEntity casOpsDailyBillingEntity = new CasOpsDailyBillingEntity();
			casOpsDailyBillingEntity.setSystemSeqNo(new BigDecimal(123));
			casOpsDailyBillingEntity.setCreditorBank(acPain012Validation_ST.creditorBank);
			casOpsDailyBillingEntity.setDebtorBank(document.getMndtAccptncRpt().getGrpHdr().getInstgAgt().getFinInstnId().getClrSysMmbId().getMmbId().trim());
			casOpsDailyBillingEntity.setSubService(origBillService);
			casOpsDailyBillingEntity.setTxnType(tt2TxnType);
			if(acceptanceResult6 != null) {
				if(acceptanceResult6.isAccptd() == true)
				{
					casOpsDailyBillingEntity.setTxnStatus(tt2Succ);
				}
				else
				{
					casOpsDailyBillingEntity.setTxnStatus(tt2Unsucc);
				}
			}

			casOpsDailyBillingEntity.setCreatedBy(systemName);
			casOpsDailyBillingEntity.setCreatedDate(todaysDate);
			casOpsDailyBillingEntity.setBillExpStatus(nonActInd);
			casOpsDailyBillingEntity.setActionDate(originalMandate.getCreatedDate());

			if(origBillService.equalsIgnoreCase(manamService))
			{
				if(originalMandate != null && originalMandate.getLocalInstrCd() != null)
				{
					casOpsDailyBillingEntity.setAuthCode(originalMandate.getLocalInstrCd());
				}
			}

			if(origBillService.equalsIgnoreCase(maninService))
			{
				if(originalMandate != null && originalMandate.getLocalInstrCd() != null)
				{
					String localInstrCd = mandate.getTp().getLclInstrm().getPrtry();
					if (localInstrCd != null) 
					{ 
						if (localInstrCd.trim().equalsIgnoreCase("0999") || localInstrCd.trim().equalsIgnoreCase("0998") || localInstrCd.trim().equalsIgnoreCase("0997")) 
						{
							casOpsDailyBillingEntity.setAuthCode(originalMandate.getLocalInstrCd());
						}
					}
				}
			}

			casOpsDailyBillingEntity.setTxnId(mandateReqTranId);
			if(supplementaryData1!= null && supplementaryData1.getEnvlp()!= null && supplementaryData1.getEnvlp().getCnts()!= null && supplementaryData1.getEnvlp().getCnts().getMndtRfNbr()!= null) {
				casOpsDailyBillingEntity.setMndtRefNum(supplementaryData1.getEnvlp().getCnts().getMndtRfNbr().toString());
			}
			casOpsDailyBillingEntity.setExtMsgId(originalMandate.getExtractMsgId());
			if(casSysctrlSysParamEntity != null)
			{
				casOpsDailyBillingEntity.setRespDate(casSysctrlSysParamEntity.getProcessDate());
			}
			else
			{
				casOpsDailyBillingEntity.setRespDate(todaysDate);
			}

			//Save Billing
			log.debug("THIS IS THE OPS DAILY BILLING ENTITY==> "+ casOpsDailyBillingEntity);
			//beanRemote.saveOpsDailyBilling(mdtAcOpsDailyBillingEntity);
			billableTxnList.add(casOpsDailyBillingEntity);
		}

		//		isLoaded = false;
		//			try
		//			{
		//				isLoaded = fileProcessBeanRemote.createAcOpsMandateTxns(mdtAcOpsMandateTxnsEntity,mandate.getDbtrAgt().getFinInstnId().getClrSysMmbId().getMmbId(), mandate.getUltmtCdtr().getId().getOrgId().getOthr().get(0).getId().toString(), mndtRfNum);
		//			}
		//			catch (Exception e) 
		//			{
		//				isLoaded = false;
		//				log.info("Error on createAcOpsMandateTxns: " + e.getMessage());
		//				e.printStackTrace();
		//			}
		//
		//		}	
	}

	public void saveBulkMandates() {
		long loadStart, loadEnd, loadDur;
		loadStart = System.nanoTime();
		fileProcessBeanRemote.openHibernateSession();

		try
		{
			log.info("==================== BULK INSERT MANAC TXNS ====================");
			fileProcessBeanRemote.bulkSaveMandates(acceptedMndtList);
			loadEnd = System.nanoTime();
			loadDur = (loadEnd - loadStart) / 1000000;
			log.info("|BULK SAVE LOAD "+fileName+" IS "+DurationFormatUtils.formatDuration(loadDur, "HH:mm:ss.S")+" milliseconds |");
			loadStart = System.nanoTime();
			log.info("==================== BULK UPDATE MATCHED TXNS ====================");
//			fileProcessBeanRemote.bulkUpdateMandates(matchedMndtList);
			bulkUpdateViaSQL();
			loadEnd = System.nanoTime();
			loadDur = (loadEnd - loadStart) / 1000000;
			log.info("|BULK UPDATE "+fileName+" IS "+DurationFormatUtils.formatDuration(loadDur, "HH:mm:ss.S")+" milliseconds |");
			loadStart = System.nanoTime();
			log.info("==================== BULK INSERT BILLABLE TXNS ====================");
			fileProcessBeanRemote.bulkSaveMandates(billableTxnList);
			loadEnd = System.nanoTime();
			loadDur = (loadEnd - loadStart) / 1000000;
			log.info("|BULK SAVE BILLING LOAD "+fileName+" IS "+DurationFormatUtils.formatDuration(loadDur, "HH:mm:ss.S")+" milliseconds |");
		}
		catch (Exception e) 
		{
			log.error("Error on saveBulkMandates MANAC: " + e.getMessage());
			e.printStackTrace();
		}
		fileProcessBeanRemote.closeHibernateSession();
	}
	
	public void bulkUpdateViaSQL() {
		int targetSize = 1000;
		List<List<String>> partitionList = null;

		if(matchedMRTIList != null && matchedMRTIList.size() > 0) {
			//Partition List
			partitionList = ListUtils.partition(matchedMRTIList, targetSize);
//			log.info("accp partition List size: "+partitionList.size());

			for (List<String> accpList : partitionList) {
				String joinResult = null;
//				This join method can only be used on JAVA 8
//				joinResult = String.join(",", accpList);
				joinResult = StringUtils.join(accpList,",");
				String sqlQuery = new String("UPDATE MANOWNER.MDT_AC_OPS_MANDATE_TXNS SET PROCESS_STATUS = 'M' WHERE MANDATE_REQ_TRAN_ID IN ("+joinResult+")");
				fileProcessBeanRemote.bulkUpdateBySQL(sqlQuery);
			}	        
		}

		if(rejectedMRTIList != null && rejectedMRTIList.size() > 0) {
			//Partition List
			partitionList = ListUtils.partition(rejectedMRTIList, targetSize);
//			log.info("rjct partition List size: "+partitionList.size());

			for (List<String> rjctList : partitionList) {
				String joinResult = null;
//				This join method can only be used on JAVA 8
//				joinResult = String.join(",", rjctList);
				joinResult = StringUtils.join(rjctList,",");
				String sqlQuery = new String("UPDATE MANOWNER.MDT_AC_OPS_MANDATE_TXNS SET PROCESS_STATUS = 'R' WHERE MANDATE_REQ_TRAN_ID IN ("+joinResult+")");
				fileProcessBeanRemote.bulkUpdateBySQL(sqlQuery);
			}	        
		}
	}

	public XMLGregorianCalendar getGregorianDate(Date dateToChange, boolean useTimeZone) {
		GregorianCalendar gCalendar = new GregorianCalendar();
		gCalendar.setTime(dateToChange);
		XMLGregorianCalendar xmlCalendar = null;
		try {
			xmlCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gCalendar);
			if(!useTimeZone)
			{
				xmlCalendar.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return xmlCalendar;
	}

	private Date getFileDate(String fileName) {
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
		int nrOfMsgsInFile = mandateAcceptance3List.size();
		CasOpsMndtCountEntity mdtOpsMndtCountEntity = new CasOpsMndtCountEntity();
		CasOpsMndtCountPK mdtOpsMndtCountPk = new CasOpsMndtCountPK();

		if(document!= null && document.getMndtAccptncRpt()!=null && document.getMndtAccptncRpt().getGrpHdr() != null && document.getMndtAccptncRpt().getGrpHdr().getMsgId()!=null)
			mdtOpsMndtCountPk.setMsgId(document.getMndtAccptncRpt().getGrpHdr().getMsgId());
		mdtOpsMndtCountPk.setServiceId("MANAC");
		if(document!= null && document.getMndtAccptncRpt()!=null && document.getMndtAccptncRpt().getGrpHdr() != null && document.getMndtAccptncRpt().getGrpHdr().getMsgId()!=null)
			mdtOpsMndtCountPk.setInstId(document.getMndtAccptncRpt().getGrpHdr().getMsgId().toString().substring(12, 18));
		mdtOpsMndtCountEntity.setNrOfMsgs(nrOfMsgsInFile);
		mdtOpsMndtCountEntity.setNrOfFiles(nrOfFile);
		mdtOpsMndtCountEntity.setIncoming("Y");
		mdtOpsMndtCountEntity.setProcessDate(todaysDate);
		mdtOpsMndtCountEntity.setOutgoing("N");
		mdtOpsMndtCountEntity.setCasOpsMndtCountPK(mdtOpsMndtCountPk);
		mdtOpsMndtCountEntity.setNrMsgsAccepted(acceptCount);
		mdtOpsMndtCountEntity.setNrMsgsRejected(rejectedCount);
		mdtOpsMndtCountEntity.setNrMsgsExtracted(0);
		mdtOpsMndtCountEntity.setFileName(fileName);

		saved = valBeanRemote.saveOpsMndtCount(mdtOpsMndtCountEntity);
		if (saved) {
			log.debug("MdtOpsCountTable has been updated");
		} else {
			log.debug("MdtOpsCountTable is not updated");
		}
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

	public static void contextValidationBeanCheck(){
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
