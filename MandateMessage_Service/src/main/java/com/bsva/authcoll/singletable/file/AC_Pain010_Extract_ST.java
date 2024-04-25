package com.bsva.authcoll.singletable.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.ejb.EJB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.apache.log4j.Logger;
import com.bsva.PropertyUtil;
import com.bsva.commons.model.OpsFileRegModel;
import com.bsva.entities.MdtAcOpsFileSizeLimitEntity;
import com.bsva.entities.MdtAcOpsGrpHdrEntity;
import com.bsva.entities.MdtAcOpsMandateTxnsEntity;
import com.bsva.entities.MdtAcOpsMndtCountEntity;
import com.bsva.entities.MdtAcOpsMndtCountPK;
import com.bsva.entities.MdtAcOpsSotEotCtrlEntity;
import com.bsva.entities.MdtOpsCustParamEntity;
import com.bsva.entities.MdtOpsRefSeqNrEntity;
import com.bsva.entities.CasSysctrlCompParamEntity;
import com.bsva.entities.CasSysctrlSysParamEntity;
import com.bsva.entities.SysCisBankEntity;
import com.bsva.interfaces.AdminBeanRemote;
import com.bsva.interfaces.FileProcessBeanRemote;
import com.bsva.interfaces.ServiceBeanRemote;
import com.bsva.interfaces.ValidationBeanRemote;
import com.bsva.utils.DateUtil;
import com.bsva.utils.Util;
import iso.std.iso._20022.tech.xsd.pain_010_001.AccountIdentification4Choice;
import iso.std.iso._20022.tech.xsd.pain_010_001.ActiveCurrencyAndAmount;
import iso.std.iso._20022.tech.xsd.pain_010_001.ActiveCurrencyAndAmount1;
import iso.std.iso._20022.tech.xsd.pain_010_001.ActiveOrHistoricCurrencyAndAmount;
import iso.std.iso._20022.tech.xsd.pain_010_001.BranchAndFinancialInstitutionIdentification5;
import iso.std.iso._20022.tech.xsd.pain_010_001.BranchData2;
import iso.std.iso._20022.tech.xsd.pain_010_001.CashAccount24;
import iso.std.iso._20022.tech.xsd.pain_010_001.CashAccountType2Choice;
import iso.std.iso._20022.tech.xsd.pain_010_001.ClearingSystemIdentification2Choice;
import iso.std.iso._20022.tech.xsd.pain_010_001.ClearingSystemMemberIdentification2;
import iso.std.iso._20022.tech.xsd.pain_010_001.ContactDetails2;
import iso.std.iso._20022.tech.xsd.pain_010_001.Contents;
import iso.std.iso._20022.tech.xsd.pain_010_001.DatePeriodDetails1;
import iso.std.iso._20022.tech.xsd.pain_010_001.Document;
import iso.std.iso._20022.tech.xsd.pain_010_001.FinancialInstitutionIdentification8;
import iso.std.iso._20022.tech.xsd.pain_010_001.Frequency6Code;
import iso.std.iso._20022.tech.xsd.pain_010_001.GenericAccountIdentification1;
import iso.std.iso._20022.tech.xsd.pain_010_001.GenericFinancialIdentification1;
import iso.std.iso._20022.tech.xsd.pain_010_001.GenericOrganisationIdentification1;
import iso.std.iso._20022.tech.xsd.pain_010_001.GenericPersonIdentification1;
import iso.std.iso._20022.tech.xsd.pain_010_001.GroupHeader47;
import iso.std.iso._20022.tech.xsd.pain_010_001.LocalInstrument2Choice;
import iso.std.iso._20022.tech.xsd.pain_010_001.Mandate1;
import iso.std.iso._20022.tech.xsd.pain_010_001.Mandate3;
import iso.std.iso._20022.tech.xsd.pain_010_001.MandateAmendment3;
import iso.std.iso._20022.tech.xsd.pain_010_001.MandateAmendmentReason1;
import iso.std.iso._20022.tech.xsd.pain_010_001.MandateAmendmentRequestV03;
import iso.std.iso._20022.tech.xsd.pain_010_001.MandateOccurrences2;
import iso.std.iso._20022.tech.xsd.pain_010_001.MandateReason1Choice;
import iso.std.iso._20022.tech.xsd.pain_010_001.MandateTypeInformation1;
import iso.std.iso._20022.tech.xsd.pain_010_001.OrganisationIdentification8;
import iso.std.iso._20022.tech.xsd.pain_010_001.OrganisationIdentification81;
import iso.std.iso._20022.tech.xsd.pain_010_001.OriginalMandate2Choice;
import iso.std.iso._20022.tech.xsd.pain_010_001.OriginalMessageInformation1;
import iso.std.iso._20022.tech.xsd.pain_010_001.Party11Choice;
import iso.std.iso._20022.tech.xsd.pain_010_001.Party11Choice1;
import iso.std.iso._20022.tech.xsd.pain_010_001.PartyIdentification43;
import iso.std.iso._20022.tech.xsd.pain_010_001.PartyIdentification431;
import iso.std.iso._20022.tech.xsd.pain_010_001.PersonIdentification5;
import iso.std.iso._20022.tech.xsd.pain_010_001.PostalAddress6;
import iso.std.iso._20022.tech.xsd.pain_010_001.SequenceType2Code;
import iso.std.iso._20022.tech.xsd.pain_010_001.ServiceLevel8Choice;
import iso.std.iso._20022.tech.xsd.pain_010_001.SupplementaryData1;
import iso.std.iso._20022.tech.xsd.pain_010_001.SupplementaryDataEnvelope1;

/*
 * @author DimakatsoN
 * Modified by SalehaR - 2015/12/10 - Alignment to V2.0 of Interface Specification
 * Modified by SalehaR - 2016/09/15 - Alignment to TRS 15
 * @author SalehaR- 2019/09/21 Aligned to Single Table(MDT_AC_OPS_MANDATE_TXNS)
 * @author SalehaR-2019/11/06 - Add Counts and Execution Time
 */
public class AC_Pain010_Extract_ST {
	private static final long serialVersionUID = 1L;

	@EJB
	PropertyUtil propertyUtil;
	private Document doc;
	String outMsgId;
	String outFileName;
	public static ServiceBeanRemote beanRemote;
	private static AdminBeanRemote adminBeanRemote;
	public static ValidationBeanRemote valBeanRemote;
	public static FileProcessBeanRemote fileProcessBeanRemote;
	public static Logger log = Logger.getLogger(AC_Pain010_Extract_ST.class);

	public List<MdtAcOpsMandateTxnsEntity> extractMandList;
	public Date todaysDate;

	public String credName, debtName, ultCredName, ultDebtName, mdtRefNo;
	public boolean crNameProvided = false, drNameProvided = false, ultCredProvided = false, ultDebtProvided = false, mdtRefNoProvided = false;
	private int parameterCount = 0;

	String backEndProcess = "BACKEND";
	String rdyToExtStatus = "3";
	String outgoingService, inwardServ;

	MdtAcOpsSotEotCtrlEntity mdtAcOpsSotEotCtrlEntity;
	CasSysctrlCompParamEntity mdtSysctrlCompParamEntity;
	CasSysctrlSysParamEntity casSysctrlSysParamEntity;
	MdtAcOpsGrpHdrEntity opsAmendGrpHdrEntity = null;
	MdtAcOpsMandateTxnsEntity mdtAcOpsMandateTxnsEntity = null;

	SimpleDateFormat sdfFileDate = new SimpleDateFormat("yyyyMMdd");
	String urn = "urn:iso:std:iso:20022:tech:xsd:pain.010.001.03";
	DecimalFormat df = new DecimalFormat("### ### ### ### ### ##0.00;-#");
	DecimalFormat df5Dec = new DecimalFormat("### ### ### ### ### ##0.00000;-#");
	int lastSeqNoUsed;
	String xmlDateFormat = "yyyy-MM-dd'T'HH:mm:ss"; 
	Date currentDate = new Date();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	long startTime, endTime, duration;
	String testLiveIndProp = null;
	List<SysCisBankEntity> sysCisBankList =null;
	List<MdtAcOpsFileSizeLimitEntity>  opsFileSizeLimitList = null;
	int nrOfMsgs = 0;
	List<MdtAcOpsMandateTxnsEntity> origTxnsList = null;
	List<MdtOpsCustParamEntity> custParamsList = null;
	List<String>extOrigMRTIList=  null;
	String processStatus = null;

	public AC_Pain010_Extract_ST() 
	{
		contextCheck();
		contextAdminBeanCheck();
		contextValidationBeanCheck();
		contextFileProcBeanCheck();

		try{
			propertyUtil = new PropertyUtil();
			this.outgoingService=propertyUtil.getPropValue("Output.Pain010");
			this.inwardServ = propertyUtil.getPropValue("Input.Pain010");
			this.testLiveIndProp = propertyUtil.getPropValue("TestLiveInd");
			this.processStatus=propertyUtil.getPropValue("ProcStatus.Extracted");
			//log.info("Test Live Indicator Property: "+testLiveIndProp);

		}catch (Exception e) {
			log.error("AC_Pain010_Extract_ST - Could not find MandateMessageCommons.properties in classpath");
			outgoingService = "MANOM";
			inwardServ = "MANAM";
			processStatus ="4";
		}
	}

	public void extract() 
	{
		mdtSysctrlCompParamEntity = (CasSysctrlCompParamEntity) valBeanRemote.retrieveCompanyParameters(backEndProcess);

		casSysctrlSysParamEntity = new CasSysctrlSysParamEntity();
		casSysctrlSysParamEntity = (CasSysctrlSysParamEntity) adminBeanRemote.retrieveActiveSysParameter();
		origTxnsList = new ArrayList<MdtAcOpsMandateTxnsEntity>();
		String destInstId = null;
		custParamsList = new ArrayList<MdtOpsCustParamEntity>();
		custParamsList = (List<MdtOpsCustParamEntity>) beanRemote.retrieveActiveCustomerParameters();

		if(custParamsList.size() > 0)
		{
			for (MdtOpsCustParamEntity custParamEntity : custParamsList) 
			{
				destInstId = custParamEntity.getInstId();

				sysCisBankList = new ArrayList<SysCisBankEntity>();
				sysCisBankList = (List<SysCisBankEntity>) adminBeanRemote.retrieveSysMemberNo(destInstId);
				if(sysCisBankList.size()>0)
				{
					//__________________Retrieve All Mandates for Extract______________________//
					extractMandList = new ArrayList<MdtAcOpsMandateTxnsEntity>();
					extractMandList = (List<MdtAcOpsMandateTxnsEntity>) fileProcessBeanRemote.retrieveMandatesForExtract(true, destInstId, inwardServ ,rdyToExtStatus);

					if(extractMandList != null && extractMandList.size() > 0)
					{
						
						MdtAcOpsFileSizeLimitEntity mappedFileSizeLimit = (MdtAcOpsFileSizeLimitEntity) fileProcessBeanRemote.retriveOutgoingService(outgoingService,destInstId);
						
						if(mappedFileSizeLimit != null && destInstId.equalsIgnoreCase(mappedFileSizeLimit.getMdtAcOpsFileSizeLimitPK().getMemberId()))
						{
							destInstId =mappedFileSizeLimit.getMdtAcOpsFileSizeLimitPK().getMemberId();
							String limit =mappedFileSizeLimit.getLimit();
							log.debug("limit---->"+limit+"");
							int fileLimit = Integer.valueOf(limit);

						List<ArrayList<MdtAcOpsMandateTxnsEntity>> chunckedList = getChunks(extractMandList,fileLimit);
						log.debug("fileLimit---->"+fileLimit+"");
						log.debug("chunckedList---->"+chunckedList.size()+"");

						for(ArrayList<MdtAcOpsMandateTxnsEntity> chunk :chunckedList)
						{
							
							extOrigMRTIList = new ArrayList<String>();
							startTime = System.nanoTime();
							nrOfMsgs = 0;
							nrOfMsgs = chunk.size();
							try
							{
								log.info("*****EXTRACTING PAIN 010(MANOM) file for "+destInstId+"*****");
								// Creating the XML Root Element
								doc = new Document();
								MandateAmendmentRequestV03 mandateAmendmentRequestV03 = new MandateAmendmentRequestV03();
								GroupHeader47 groupHeader = generateGroupHeader(destInstId, chunk.get(0).getInitParty());

								mandateAmendmentRequestV03.setGrpHdr(groupHeader);
								outFileName = createFileName(destInstId); 
								for(MdtAcOpsMandateTxnsEntity extMandate : chunk)
								{
									MandateAmendment3 mandateAmendment = generateMandate(extMandate);
									mandateAmendmentRequestV03.getUndrlygAmdmntDtls().add(mandateAmendment);

									//Update Original Mandate
									
									extOrigMRTIList.add("'"+extMandate.getMdtAcOpsMandateTxnsEntityPK().getMandateReqTranId()+"'");
//									try 
//									{
//										extMandate.setProcessStatus(propertyUtil.getPropValue("ProcStatus.Extracted"));
//										extMandate.setExtractMsgId(outMsgId);
//										extMandate.setExtractFileName(outFileName);
//										extMandate.setModifiedDate(todaysDate);
//
//										origTxnsList.add(extMandate);
//										//fileProcessBeanRemote.updateMdtOpsMandateTxns(extMandate);
//									}
//									catch (Exception ex) 
//									{
//										log.error("Error on updating MdtAcOpsMandateTxnsEntity: "+ex.getMessage());
//										ex.printStackTrace();
//									}
								}
								doc.setMndtAmdmntReq(mandateAmendmentRequestV03);

								if(doc != null)
									createPain010(doc, destInstId);
									saveBulkConfirmations();
									generateMndtCount();
								
								endTime = System.nanoTime();
								duration = (endTime - startTime) / 1000000;
								log.info("|"+outFileName+" EXTRACTED | TOT_EXTRACTED "+chunk.size()+" | EXEC TIME: "+DurationFormatUtils.formatDuration(duration, "HH:mm:ss.S")+" milliseconds |");
								
							}
							catch(Exception e)
							{
								log.error("AC_Pain010_Extract_ST: Error on generating MANOM file.... "+e.getMessage());
								e.printStackTrace();
							}
						}
					}// end if mandatesList.size > 0
				}// end of for custPramList

			}//end of if custParamsSize > 0
		}
		}

	}

	private  ArrayList<ArrayList<MdtAcOpsMandateTxnsEntity>> getChunks(List<MdtAcOpsMandateTxnsEntity> extractMandList,int chunkSize)
	{           
	            AtomicInteger counter = new AtomicInteger();

	            ArrayList<ArrayList<MdtAcOpsMandateTxnsEntity>> result = new ArrayList<ArrayList<MdtAcOpsMandateTxnsEntity>>();

	            for(MdtAcOpsMandateTxnsEntity value : extractMandList)
	            {               
	                if (counter.getAndIncrement() % chunkSize == 0)
	                {
	                    result.add(new ArrayList<MdtAcOpsMandateTxnsEntity>());
	                    
	                }
	                
	                result.get(result.size() - 1).add(value);
	            }
	            
	            return result;
	  }
	
	public void saveBulkConfirmations() {		
		log.info("xxxxx In the saveBulkConfirmations xxxxxxxx");
		fileProcessBeanRemote.openHibernateSession();

		try
		{
			log.info("==================== BULK UPDATE MANOM TXNS ====================");
			//fileProcessBeanRemote.bulkUpdateMandates(origTxnsList);
			bulkUpdateViaSQL();
		}
		catch (Exception e) 
		{
			log.error("Error on saveBulkConfirmations: " + e.getMessage());
			e.printStackTrace();
		}
		fileProcessBeanRemote.closeHibernateSession();
	}

	//public void bulkUpdateViaSQL(List<String> origidList) 
	public void bulkUpdateViaSQL() 
	{
		int targetSize = 1000;

		String extractMsgId = outMsgId; 
		String extractFileName = outFileName;
		DateFormat dateTimeFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		todaysDate = new Date();
		java.sql.Date sqlDate = new java.sql.Date(todaysDate.getTime());
		java.util.Date utilDate = new java.util.Date();
		Timestamp modDate =  new Timestamp(utilDate.getTime());
		String modifiedDate=dateTimeFormat.format(utilDate);
		log.debug("utilDate---->" +utilDate);
		log.debug("sqlDate---->" +sqlDate);
		log.debug("modifiedDate---->" +modifiedDate);
		log.debug("modDate---->" +modDate);
		
		if(extOrigMRTIList != null && extOrigMRTIList.size() > 0)
		{
			log.info("XXXXX BULK UPDATE PAIN 010 MANDATE XXXXX");
			List<List<String>> partitionList = ListUtils.partition(extOrigMRTIList, targetSize);
			log.info("Original Mandate partition List size: "+partitionList.size());

			for (List<String> mrtiToUpdateList : partitionList) {
				String joinResult = null;

				joinResult = StringUtils.join(mrtiToUpdateList,",");
				String sqlQuery = new String("UPDATE MANOWNER.MDT_AC_OPS_MANDATE_TXNS SET  PROCESS_STATUS = '"+processStatus+"', EXTRACT_MSG_ID = '"+extractMsgId+"', "
						+ " EXTRACT_FILE_NAME ='"+extractFileName+"' , MODIFIED_DATE =  TO_DATE('"+modifiedDate+"','yyyy/MM/dd HH24:MI:SS')  WHERE SERVICE_ID = '"+inwardServ+"' AND MANDATE_REQ_TRAN_ID IN ("+joinResult+")");
				log.debug("SQL query---->" +sqlQuery);

				fileProcessBeanRemote.bulkUpdateBySQL(sqlQuery);
			}	        
		}
	
	
	}

	public GroupHeader47  generateGroupHeader(String destInstId, String initgParty)
	{
		PartyIdentification43 initgPtyIdentification43;
		ContactDetails2 initgContactDetails2;

		BranchAndFinancialInstitutionIdentification5 branchFinInfo;
		FinancialInstitutionIdentification8 finInstInfo;
		ClearingSystemMemberIdentification2 clearingSystemMemberIdentification2;

		GroupHeader47 groupHeader = new GroupHeader47();
		outMsgId = generateMsgId(destInstId);
		groupHeader.setMsgId(outMsgId);
		groupHeader.setCreDtTm(DateUtil.toXMLGregorianCalendarWithFormat(new Date(), xmlDateFormat));

		if(initgParty != null && !initgParty.isEmpty())
		{
			PartyIdentification43 partyIdentification43 = new PartyIdentification43();

			partyIdentification43.setNm(initgParty);
			groupHeader.setInitgPty(partyIdentification43);
		}

		String instrtgAgnt= null;
		try {
			instrtgAgnt=propertyUtil.getPropValue("BSVA.MemberNo");
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(mdtSysctrlCompParamEntity != null)
			instrtgAgnt = mdtSysctrlCompParamEntity.getAchInstId();
		else
			try {
				instrtgAgnt=propertyUtil.getPropValue("BSVA.MemberNo");
			} catch (IOException e) {
				e.printStackTrace();
			}

		//Set Instructing Agent
		branchFinInfo =  new BranchAndFinancialInstitutionIdentification5();
		finInstInfo = new FinancialInstitutionIdentification8();
		clearingSystemMemberIdentification2 = new ClearingSystemMemberIdentification2();

		clearingSystemMemberIdentification2.setMmbId(instrtgAgnt);
		finInstInfo.setClrSysMmbId(clearingSystemMemberIdentification2);
		branchFinInfo.setFinInstnId(finInstInfo);

		groupHeader.setInstgAgt(branchFinInfo);

		//Set Instructed Agent
		branchFinInfo =  new BranchAndFinancialInstitutionIdentification5();
		finInstInfo = new FinancialInstitutionIdentification8();
		clearingSystemMemberIdentification2 = new ClearingSystemMemberIdentification2();

		clearingSystemMemberIdentification2.setMmbId(destInstId);
		finInstInfo.setClrSysMmbId(clearingSystemMemberIdentification2);
		branchFinInfo.setFinInstnId(finInstInfo);

		groupHeader.setInstdAgt(branchFinInfo);

		return groupHeader;
	}

	public MandateAmendment3 generateMandate(MdtAcOpsMandateTxnsEntity mdtAcOpsMandateTxnsEntity) 
	{
		PostalAddress6  instgPostalAddress6, instgBrPostalAddress6, instrdPostalAddress6, instrdBrPostalAddress6;
		ContactDetails2  instgContactDetails2, instrdContactDetails2;
		BranchAndFinancialInstitutionIdentification5 instgAgtBranchAndFinancialInstitutionIdentification5, instrctdBranchAndFinancialInstitutionIdentification5;
		BranchData2 instgAgtBranchData2, instrctdAgtBranchData2;
		FinancialInstitutionIdentification8 instgAgtFinancialInstitutionIdentification8, instrctdAgtFinancialInstitutionIdentification8;
		ClearingSystemMemberIdentification2 instgAgtClearingSystemMemberIdentification2, instrctdAgtClearingSystemMemberIdentification2;
		ClearingSystemIdentification2Choice instgAgtClearingSystemIdentification2Choice, instrctdAgtClearingSystemIdentification2Choice;
		GenericFinancialIdentification1 instgAgtGenericFinancialIdentification1, instrctdAgtGenericFinancialIdentification1;
		MandateAmendment3 mandateAmendment = new MandateAmendment3();
		SupplementaryData1 supplementaryData1 = new SupplementaryData1();
		OriginalMessageInformation1 originalMessageInformation1 = new OriginalMessageInformation1();
		MandateAmendmentReason1 mandateAmendmentReason1 = new MandateAmendmentReason1();
		OriginalMandate2Choice originalMandate2Choice= new OriginalMandate2Choice();

		//_______Map Mandate Information________//
		MandateTypeInformation1 mandateTypeInformation1;
		LocalInstrument2Choice localInstrument2Choice;
		MandateOccurrences2 mandateOccurrences2;
		DatePeriodDetails1 datePeriodDetails1;
		ActiveOrHistoricCurrencyAndAmount  activeCurrencyAndAmount1, activeCurrencyAndAmountMax1;
		ActiveCurrencyAndAmount activeCurrencyAndAmount, activeCurrencyAndAmountMax;
		PartyIdentification43 crSchemePartyIdentification43,  ultCreditor, debtor, ultDebtor;
		PartyIdentification431 creditor;
		PostalAddress6 crSchemePostal, creditorPostal, crAgtFiPostal, crAgtBrPostal, ultCredPostal, debtorPostal, drAgtFiPostal, drAgtBrPostal, ultDebtPostal;
		ContactDetails2 crSchemeConDetails2, creditorContactDetails2, ultCredContactDetails2, debtorContactDetails2, ultDebtContactDetails2;
		CashAccount24 credCashAccount24, debtCashAccount24;
		AccountIdentification4Choice credAccountIdentification4Choice, debtAccountIdentification4Choice;
		CashAccountType2Choice credAccountType2Choice, debtAccountType2Choice;
		BranchAndFinancialInstitutionIdentification5 crAgtBr_FinId, drAgtBr_FinId;
		FinancialInstitutionIdentification8 crAgtFiId, drAgtFiId;
		ClearingSystemMemberIdentification2 crAgtClSystemMemberIdentification2, drAgtClearingSystemMemberIdentification2;
		BranchData2 crAgtBranchData2, drAgtBranchData2;

		//********POPULATE MANDATE INFORMATION*************//
		if(mdtAcOpsMandateTxnsEntity.getAmendReason() != null)
		{
			MandateReason1Choice mandateReason1Choice = new MandateReason1Choice();

			mandateReason1Choice.setPrtry(mdtAcOpsMandateTxnsEntity.getAmendReason());
			mandateAmendmentReason1.setRsn(mandateReason1Choice);
			mandateAmendment.setAmdmntRsn(mandateAmendmentReason1);
		}

		Mandate3 mandate = new Mandate3();

		if(mdtAcOpsMandateTxnsEntity.getMandateId() != null)
			mandate.setMndtId(mdtAcOpsMandateTxnsEntity.getMandateId());
		
		if(mdtAcOpsMandateTxnsEntity.getContractRef() != null)
			mandate.setMndtReqId(mdtAcOpsMandateTxnsEntity.getContractRef());

		mandateTypeInformation1 = new MandateTypeInformation1();
		ServiceLevel8Choice serviceLevel8Choice = new ServiceLevel8Choice();
		if(mdtAcOpsMandateTxnsEntity.getServiceLevel() != null)
		{
			serviceLevel8Choice.setPrtry(mdtAcOpsMandateTxnsEntity.getServiceLevel());
			mandateTypeInformation1.setSvcLvl(serviceLevel8Choice);
		}

		if(mdtAcOpsMandateTxnsEntity.getLocalInstrCd() != null)
		{
			localInstrument2Choice = new LocalInstrument2Choice();
			localInstrument2Choice.setPrtry(mdtAcOpsMandateTxnsEntity.getLocalInstrCd());
			mandateTypeInformation1.setLclInstrm(localInstrument2Choice);
		}

		if(mdtAcOpsMandateTxnsEntity.getServiceLevel() != null || mdtAcOpsMandateTxnsEntity.getLocalInstrCd() != null)
			mandate.setTp(mandateTypeInformation1);

		mandateOccurrences2 = new MandateOccurrences2();
		if(mdtAcOpsMandateTxnsEntity.getSequenceType() != null)
			mandateOccurrences2.setSeqTp(SequenceType2Code.fromValue(mdtAcOpsMandateTxnsEntity.getSequenceType()));

		if(mdtAcOpsMandateTxnsEntity.getFrequency() != null)
			mandateOccurrences2.setFrqcy(Frequency6Code.fromValue(mdtAcOpsMandateTxnsEntity.getFrequency()));

		if(mdtAcOpsMandateTxnsEntity.getFirstCollDate() != null)
			mandateOccurrences2.setFrstColltnDt(getGregorianDateWithoutTime(mdtAcOpsMandateTxnsEntity.getFirstCollDate()));

		if(mdtAcOpsMandateTxnsEntity.getSequenceType() != null || mdtAcOpsMandateTxnsEntity.getFrequency() != null || mdtAcOpsMandateTxnsEntity.getFirstCollDate() != null)
			mandate.setOcrncs(mandateOccurrences2);

		activeCurrencyAndAmount = new ActiveCurrencyAndAmount();
		if(mdtAcOpsMandateTxnsEntity.getCollAmountCurr() != null)
			activeCurrencyAndAmount.setCcy(mdtAcOpsMandateTxnsEntity.getCollAmountCurr());

		if(mdtAcOpsMandateTxnsEntity.getCollAmount() != null)
		{
			String value = df.format(mdtAcOpsMandateTxnsEntity.getCollAmount());
			double dValue = Double.valueOf(value);
			BigDecimal bigdecimalValue = new BigDecimal(dValue);
			bigdecimalValue = bigdecimalValue.setScale(2, RoundingMode.HALF_UP);
			activeCurrencyAndAmount.setValue(bigdecimalValue);
		}

		if(mdtAcOpsMandateTxnsEntity.getCollAmountCurr() != null || mdtAcOpsMandateTxnsEntity.getCollAmount() != null)
			mandate.setColltnAmt(activeCurrencyAndAmount);

		activeCurrencyAndAmountMax = new ActiveCurrencyAndAmount();
		if(mdtAcOpsMandateTxnsEntity.getMaxAmountCurr() != null)
			activeCurrencyAndAmountMax.setCcy(mdtAcOpsMandateTxnsEntity.getMaxAmountCurr());

		if(mdtAcOpsMandateTxnsEntity.getMaxAmount() != null)
		{
			String value = df.format(mdtAcOpsMandateTxnsEntity.getMaxAmount());
			double dValue = Double.valueOf(value);
			BigDecimal bigdecimalValue = new BigDecimal(dValue);
			bigdecimalValue = bigdecimalValue.setScale(2, RoundingMode.HALF_UP);
			activeCurrencyAndAmountMax.setValue(bigdecimalValue);
		}

		if(mdtAcOpsMandateTxnsEntity.getMaxAmountCurr() != null || mdtAcOpsMandateTxnsEntity.getMaxAmount() != null)
			mandate.setMaxAmt(activeCurrencyAndAmountMax);

		//********POPULATE CREDITOR SCHEME INFORMATION*************//
		if(mdtAcOpsMandateTxnsEntity.getCredSchemeId() != null)
		{
			crSchemePartyIdentification43 = new PartyIdentification43();
			Party11Choice party11Choice = new Party11Choice();
			OrganisationIdentification8 organisationIdentification8 = new OrganisationIdentification8();
			GenericOrganisationIdentification1 genericOrganisationIdentification1 = new GenericOrganisationIdentification1();
			genericOrganisationIdentification1.setId(mdtAcOpsMandateTxnsEntity.getCredSchemeId());
			organisationIdentification8.getOthr().add(genericOrganisationIdentification1);
			party11Choice.setOrgId(organisationIdentification8);
			crSchemePartyIdentification43.setId(party11Choice);
			mandate.setCdtrSchmeId(crSchemePartyIdentification43);
		}

		//********POPULATE CREDITOR INFORMATION*************//
		creditor = new PartyIdentification431();
		if(mdtAcOpsMandateTxnsEntity.getCreditorName() != null)
			creditor.setNm(mdtAcOpsMandateTxnsEntity.getCreditorName());

		if(mdtAcOpsMandateTxnsEntity.getMdtAcOpsMandateTxnsEntityPK().getMandateReqTranId() != null)
		{
			Party11Choice1 party11Choice= new Party11Choice1();
			OrganisationIdentification81 organisationIdentification8 = new OrganisationIdentification81();
			GenericOrganisationIdentification1 genericOrganisationIdentification1 = new GenericOrganisationIdentification1();
			genericOrganisationIdentification1.setId(mdtAcOpsMandateTxnsEntity.getMdtAcOpsMandateTxnsEntityPK().getMandateReqTranId());
			organisationIdentification8.getOthr().add(genericOrganisationIdentification1);
			party11Choice.setOrgId(organisationIdentification8);
			creditor.setId(party11Choice);
		}

		if(mdtAcOpsMandateTxnsEntity.getCredPhoneNr() != null || mdtAcOpsMandateTxnsEntity.getCredEmailAddr() != null)
		{
			creditorContactDetails2 = new ContactDetails2();

			if(mdtAcOpsMandateTxnsEntity.getCredPhoneNr() != null)
				creditorContactDetails2.setPhneNb(mdtAcOpsMandateTxnsEntity.getCredPhoneNr());

			if(mdtAcOpsMandateTxnsEntity.getCredEmailAddr() != null)
				creditorContactDetails2.setEmailAdr(mdtAcOpsMandateTxnsEntity.getCredEmailAddr());

			creditor.setCtctDtls(creditorContactDetails2);
		}

		if(creditor.getNm() != null || creditor.getCtctDtls() != null || creditor.getId() != null)
			mandate.setCdtr(creditor);

		//********POPULATE CREDITOR_ACCOUNT INFORMATION*************//
		if(mdtAcOpsMandateTxnsEntity.getCredAccNum() != null)
		{	
			credCashAccount24 = new CashAccount24();
			credAccountIdentification4Choice = new AccountIdentification4Choice();
			GenericAccountIdentification1 credGenericAccountIdentification1 = new GenericAccountIdentification1();
			credGenericAccountIdentification1.setId(mdtAcOpsMandateTxnsEntity.getCredAccNum());
			credAccountIdentification4Choice.setOthr(credGenericAccountIdentification1);
			credCashAccount24.setId(credAccountIdentification4Choice);
			mandate.setCdtrAcct(credCashAccount24);
		}

		//********POPULATE CREDITOR_AGENT INFORMATION*************//
		if(mdtAcOpsMandateTxnsEntity.getCredBranchNr() != null)
		{
			crAgtBr_FinId = new  BranchAndFinancialInstitutionIdentification5();
			crAgtFiId = new FinancialInstitutionIdentification8();
			crAgtClSystemMemberIdentification2 = new ClearingSystemMemberIdentification2();

			crAgtClSystemMemberIdentification2.setMmbId(mdtAcOpsMandateTxnsEntity.getCredBranchNr());
			crAgtFiId.setClrSysMmbId(crAgtClSystemMemberIdentification2);

			crAgtBr_FinId.setFinInstnId(crAgtFiId);
			mandate.setCdtrAgt(crAgtBr_FinId);
		}

		//********POPULATE ULTIMATE CREDITOR INFORMATION*************//
		ultCreditor = new PartyIdentification43();

		if(mdtAcOpsMandateTxnsEntity.getUltCredName() != null)
			ultCreditor.setNm(mdtAcOpsMandateTxnsEntity.getUltCredName());

		if(mdtAcOpsMandateTxnsEntity.getCredAbbShortName() != null)
		{
			Party11Choice party11Choice = new Party11Choice();
			OrganisationIdentification8 organisationIdentification8 = new OrganisationIdentification8();
			GenericOrganisationIdentification1 genericOrganisationIdentification1 = new GenericOrganisationIdentification1();
			genericOrganisationIdentification1.setId(mdtAcOpsMandateTxnsEntity.getCredAbbShortName());
			organisationIdentification8.getOthr().add(genericOrganisationIdentification1);
			party11Choice.setOrgId(organisationIdentification8);
			ultCreditor.setId(party11Choice);
		}

		if(ultCreditor.getId() != null || ultCreditor.getNm() != null)
			mandate.setUltmtCdtr(ultCreditor);	

		//********POPULATE DEBTOR INFORMATION*************//
		debtor = new PartyIdentification43();
		if(mdtAcOpsMandateTxnsEntity.getDebtorName() != null)
			debtor.setNm(mdtAcOpsMandateTxnsEntity.getDebtorName());

		if(mdtAcOpsMandateTxnsEntity.getDebtorId() != null)
		{
			Party11Choice debtParty11Choice = new Party11Choice();
			PersonIdentification5 personIdentification5 = new PersonIdentification5();
			GenericPersonIdentification1 genericPersonIdentification1 = new GenericPersonIdentification1();
			genericPersonIdentification1.setId(mdtAcOpsMandateTxnsEntity.getDebtorId());
			personIdentification5.getOthr().add(genericPersonIdentification1);
			debtParty11Choice.setPrvtId(personIdentification5);
			debtor.setId(debtParty11Choice);
		}

		if(mdtAcOpsMandateTxnsEntity.getDebtPhoneNr() != null || mdtAcOpsMandateTxnsEntity.getDebtEmailAddr() != null)
		{
			debtorContactDetails2 = new ContactDetails2();

			if(mdtAcOpsMandateTxnsEntity.getDebtPhoneNr() != null)
				debtorContactDetails2.setPhneNb(mdtAcOpsMandateTxnsEntity.getDebtPhoneNr());

			if(mdtAcOpsMandateTxnsEntity.getDebtEmailAddr() != null)
				debtorContactDetails2.setEmailAdr(mdtAcOpsMandateTxnsEntity.getDebtEmailAddr());

			debtor.setCtctDtls(debtorContactDetails2);
		}

		if(debtor.getNm() != null ||debtor.getId() != null || debtor.getCtctDtls() != null)
			mandate.setDbtr(debtor);

		//********POPULATE DEBTOR_ACCOUNT INFORMATION*************//
		debtCashAccount24 = new CashAccount24();
		if(mdtAcOpsMandateTxnsEntity.getDebtAccNum() != null)
		{
			debtAccountIdentification4Choice = new AccountIdentification4Choice();
			GenericAccountIdentification1 debtGenericAccountIdentification1 = new GenericAccountIdentification1();
			debtGenericAccountIdentification1.setId(mdtAcOpsMandateTxnsEntity.getDebtAccNum());
			debtAccountIdentification4Choice.setOthr(debtGenericAccountIdentification1);
			debtCashAccount24.setId(debtAccountIdentification4Choice);
		}

		if(mdtAcOpsMandateTxnsEntity.getDebtAccType() != null)
		{
			debtAccountType2Choice = new CashAccountType2Choice();
			debtAccountType2Choice.setPrtry(mdtAcOpsMandateTxnsEntity.getDebtAccType());
			debtCashAccount24.setTp(debtAccountType2Choice);
		}

		if(debtCashAccount24.getTp() != null || debtCashAccount24.getId() != null)
			mandate.setDbtrAcct(debtCashAccount24);

		//********POPULATE DEBTOR_AGENT INFORMATION*************//
		drAgtBr_FinId = new  BranchAndFinancialInstitutionIdentification5();
		drAgtFiId = new FinancialInstitutionIdentification8();

		if(mdtAcOpsMandateTxnsEntity.getDebtBranchNr() != null)
		{
			drAgtClearingSystemMemberIdentification2 = new ClearingSystemMemberIdentification2();
			drAgtClearingSystemMemberIdentification2.setMmbId(mdtAcOpsMandateTxnsEntity.getDebtBranchNr());
			drAgtFiId.setClrSysMmbId(drAgtClearingSystemMemberIdentification2);
			drAgtBr_FinId.setFinInstnId(drAgtFiId);
			mandate.setDbtrAgt(drAgtBr_FinId);
		}

		//********POPULATE ULTIMATE DEBTOR INFORMATION*************//
		if(mdtAcOpsMandateTxnsEntity.getUltDebtName() != null)
		{
			ultDebtor = new PartyIdentification43();
			ultDebtor.setNm(mdtAcOpsMandateTxnsEntity.getUltDebtName());
			mandate.setUltmtDbtr(ultDebtor);
		}
		mandateAmendment.setMndt(mandate);

		//********POPULATE ORGNL MANDATE INFORMATION************/
		Mandate1 mandate1 = new Mandate1();

		if(mdtAcOpsMandateTxnsEntity.getOrigMandateId() != null)
			mandate1.setMndtId(mdtAcOpsMandateTxnsEntity.getOrigMandateId());

		if(mdtAcOpsMandateTxnsEntity.getOrigContractRef() != null)
			mandate1.setMndtReqId(mdtAcOpsMandateTxnsEntity.getOrigContractRef());

		if(mdtAcOpsMandateTxnsEntity.getOrigCredName() != null || mdtAcOpsMandateTxnsEntity.getOrigMandReqTranId() != null)
		{
			PartyIdentification43 partyIdentification43 = new PartyIdentification43();

			if(mdtAcOpsMandateTxnsEntity.getOrigCredName() != null)
				partyIdentification43.setNm(mdtAcOpsMandateTxnsEntity.getOrigCredName());

			if(mdtAcOpsMandateTxnsEntity.getOrigMandReqTranId() != null)
			{
				Party11Choice party11Choice= new Party11Choice();
				OrganisationIdentification8 organisationIdentification8 = new OrganisationIdentification8();
				GenericOrganisationIdentification1 genericOrganisationIdentification1 = new GenericOrganisationIdentification1();
				genericOrganisationIdentification1.setId(mdtAcOpsMandateTxnsEntity.getOrigMandReqTranId());
				organisationIdentification8.getOthr().add(genericOrganisationIdentification1);
				party11Choice.setOrgId(organisationIdentification8);
				partyIdentification43.setId(party11Choice);
			}

			mandate1.setCdtr(partyIdentification43);
		}

		if(mdtAcOpsMandateTxnsEntity.getOrigDebtName() != null)
		{
			PartyIdentification43 partyIdentification43 = new PartyIdentification43();
			partyIdentification43.setNm(mdtAcOpsMandateTxnsEntity.getOrigDebtName());

			mandate1.setDbtr(partyIdentification43);
		}

		if(mdtAcOpsMandateTxnsEntity.getOrigDebtBranch() != null)
		{
			BranchAndFinancialInstitutionIdentification5 debtorBranch = new  BranchAndFinancialInstitutionIdentification5();
			FinancialInstitutionIdentification8 financialInstitutionIdentification8 = new FinancialInstitutionIdentification8();
			ClearingSystemMemberIdentification2 clearingSystemMemberIdentification2 = new ClearingSystemMemberIdentification2();

			clearingSystemMemberIdentification2.setMmbId(mdtAcOpsMandateTxnsEntity.getOrigDebtBranch());
			financialInstitutionIdentification8.setClrSysMmbId(clearingSystemMemberIdentification2);
			debtorBranch.setFinInstnId(financialInstitutionIdentification8);

			mandate1.setDbtrAgt(debtorBranch);
		}

		originalMandate2Choice.setOrgnlMndt(mandate1);

		if(originalMandate2Choice.getOrgnlMndt() != null)
			mandateAmendment.setOrgnlMndt(originalMandate2Choice);

		//********POPULATE SUPPL_DATA INFORMATION************/
		if(mdtAcOpsMandateTxnsEntity.getAuthType() != null || mdtAcOpsMandateTxnsEntity.getCollectionDay() != null || mdtAcOpsMandateTxnsEntity.getDateAdjRuleInd() != null || mdtAcOpsMandateTxnsEntity.getAdjCategory() != null
				|| mdtAcOpsMandateTxnsEntity.getAdjRate() != null || mdtAcOpsMandateTxnsEntity.getAdjAmountCurr() != null || mdtAcOpsMandateTxnsEntity.getAdjAmount() != null 
				|| mdtAcOpsMandateTxnsEntity.getFirstCollAmt() != null || mdtAcOpsMandateTxnsEntity.getFirstCollAmtCurr() != null || mdtAcOpsMandateTxnsEntity.getDebitValueType() != null)
		{
			SupplementaryDataEnvelope1  supplementaryDataEnvelope1 = new SupplementaryDataEnvelope1();
			Contents contents = new Contents();
			ActiveCurrencyAndAmount  fstCollActiveCurrencyAndAmount;
			ActiveCurrencyAndAmount1 adjustActiveCurrencyAndAmount;

			if(mdtAcOpsMandateTxnsEntity.getAuthType() != null)
				contents.setAthntctnTp(mdtAcOpsMandateTxnsEntity.getAuthType());

			if(mdtAcOpsMandateTxnsEntity.getCollectionDay() != null)
				contents.setCllctnDy(mdtAcOpsMandateTxnsEntity.getCollectionDay());

			if(mdtAcOpsMandateTxnsEntity.getDateAdjRuleInd() != null)
				contents.setDtAdjRl(mdtAcOpsMandateTxnsEntity.getDateAdjRuleInd());

			if(mdtAcOpsMandateTxnsEntity.getAdjCategory() != null)
				contents.setAdjstCtgy(mdtAcOpsMandateTxnsEntity.getAdjCategory());

			if(mdtAcOpsMandateTxnsEntity.getAdjRate() != null)
			{
				String value = df5Dec.format(mdtAcOpsMandateTxnsEntity.getAdjRate());
				double dValue = Double.valueOf(value);
				BigDecimal bigdecimalValue = new BigDecimal(dValue);
				bigdecimalValue = bigdecimalValue.setScale(5, RoundingMode.HALF_UP);
				contents.setAdjstRt(bigdecimalValue);
			}

			adjustActiveCurrencyAndAmount = new ActiveCurrencyAndAmount1();
			if(mdtAcOpsMandateTxnsEntity.getAdjAmountCurr() != null )
				adjustActiveCurrencyAndAmount.setCcy(mdtAcOpsMandateTxnsEntity.getAdjAmountCurr());

			if(mdtAcOpsMandateTxnsEntity.getAdjAmount() != null)
			{
				String value = df.format(mdtAcOpsMandateTxnsEntity.getAdjAmount());
				double dValue = Double.valueOf(value);
				BigDecimal bigdecimalValue = new BigDecimal(dValue);
				bigdecimalValue = bigdecimalValue.setScale(2, RoundingMode.HALF_UP);
				adjustActiveCurrencyAndAmount.setValue(bigdecimalValue);
			}

			if(adjustActiveCurrencyAndAmount.getCcy() != null || adjustActiveCurrencyAndAmount.getValue() != null)
				contents.setAdjstAmt(adjustActiveCurrencyAndAmount);

			if(mdtAcOpsMandateTxnsEntity.getMandateRefNr() != null) 
				contents.setMndtRfNbr(mdtAcOpsMandateTxnsEntity.getMandateRefNr());

			fstCollActiveCurrencyAndAmount = new ActiveCurrencyAndAmount();
			if(mdtAcOpsMandateTxnsEntity.getFirstCollAmtCurr() != null)
				fstCollActiveCurrencyAndAmount.setCcy(mdtAcOpsMandateTxnsEntity.getFirstCollAmtCurr());

			if(mdtAcOpsMandateTxnsEntity.getFirstCollAmt() != null)
			{
				String value = df.format(mdtAcOpsMandateTxnsEntity.getFirstCollAmt());
				double dValue = Double.valueOf(value);
				BigDecimal bigdecimalValue = new BigDecimal(dValue);
				bigdecimalValue = bigdecimalValue.setScale(2, RoundingMode.HALF_UP);
				fstCollActiveCurrencyAndAmount.setValue(bigdecimalValue);
			}

			if(fstCollActiveCurrencyAndAmount.getCcy() != null || fstCollActiveCurrencyAndAmount.getValue() != null)
				contents.setFrstColltnAmt(fstCollActiveCurrencyAndAmount);

			if(mdtAcOpsMandateTxnsEntity.getDebitValueType() != null)
				contents.setDbVlTp(mdtAcOpsMandateTxnsEntity.getDebitValueType());

			supplementaryDataEnvelope1.setCnts(contents);

			if(supplementaryDataEnvelope1.getCnts() != null)
				supplementaryData1.setEnvlp(supplementaryDataEnvelope1);

			if(supplementaryData1.getEnvlp() != null)
				mandateAmendment.setSplmtryData(supplementaryData1);
		}
		return mandateAmendment;	

	}

	public  void createPain010(Document doc, String destInstId) 
	{
		try 
		{
			JAXBContext context = JAXBContext.newInstance(Document.class);
			//			outFileName = createFileName(destInstId); 
			log.debug("outFileName ================================"+outFileName);
			String out ="/home/opsjava/Delivery/Mandates/Output/temp/"+outFileName+".xml";
			File f = new File("/home/opsjava/Delivery/Mandates/Output/temp/" + outFileName +".xml")  ;  
			JAXBContext jaxbContext = JAXBContext.newInstance(Document.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// format the XML output
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.setProperty("com.sun.xml.bind.xmlDeclaration", Boolean.FALSE);
			jaxbMarshaller.setProperty("com.sun.xml.bind.xmlHeaders","<?xml version=\"1.0\" encoding=\"UTF-8\"?>");

			QName qName = new QName(urn, "Document");
			JAXBElement<Document> root = new JAXBElement<Document>(qName, Document.class, doc);

			jaxbMarshaller.marshal(root, f);

			createOpsFileReg(outFileName, out);

			copyFile(outFileName);

		} catch (Exception e) {
			log.error("Error on creating response file: "+e.getMessage());
			e.printStackTrace();
		}
	}

	private String createFileName(String destInstId) 
	{
		String achId, outBicCode, fileType, creationDate, fileSeqNo, testLiveInd, transInd, fileName = null;
		SimpleDateFormat sdfFileDate = new SimpleDateFormat("yyyyMMdd");
		int fileLastSeqNo = 0;
		try
		{	
			if(mdtSysctrlCompParamEntity != null)
			{
				achId = mdtSysctrlCompParamEntity.getAchId();
				testLiveInd = mdtSysctrlCompParamEntity.getTransamissionInd();
			}
			else
			{
				achId = "021";
				testLiveInd = testLiveIndProp;
			}

			fileSeqNo = String.format("%06d",lastSeqNoUsed);
			//TRS16 Processing Rules					    
			if(casSysctrlSysParamEntity != null && casSysctrlSysParamEntity.getProcessDate() != null)
			{
				creationDate = sdfFileDate.format(casSysctrlSysParamEntity.getProcessDate());
			}
			else
			{
				creationDate = sdfFileDate.format(new Date());
			}

			fileName = achId+outgoingService+"00"+destInstId+"M"+creationDate+fileSeqNo+testLiveInd+"D";
		}
		catch (Exception e) 
		{
			log.error("**** Exception generating fileName for MANAM file **** : " + e);
			e.printStackTrace();
			e.getCause();
		}

		return fileName;
	}

	public String  generateMsgId(String instId)
	{
		log.debug("In the generateMsgId()" );
		SimpleDateFormat sdfFileDate = new SimpleDateFormat("yyyyMMdd");
		String achId,  creationDate, fileSeqNo, msgId = null;

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

			MdtOpsRefSeqNrEntity  mdtOpsRefSeqNrEntity = new MdtOpsRefSeqNrEntity();
			mdtOpsRefSeqNrEntity = (MdtOpsRefSeqNrEntity) valBeanRemote.retrieveRefSeqNr(outgoingService, instId);

			if(mdtOpsRefSeqNrEntity != null)
			{
				lastSeqNoUsed = Integer.valueOf(mdtOpsRefSeqNrEntity.getLastSeqNr());
				lastSeqNoUsed = lastSeqNoUsed + 1;
			}
			else
				lastSeqNoUsed = 1;

			fileSeqNo = String.format("%06d",lastSeqNoUsed);
			mdtOpsRefSeqNrEntity.setLastSeqNr(fileSeqNo);
			valBeanRemote.updateOpsRefSeqNr(mdtOpsRefSeqNrEntity);

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

	public XMLGregorianCalendar getGregorianDateWithoutTime(Date dateToChange) {
		GregorianCalendar gCalendar = new GregorianCalendar();
		gCalendar.setTime(dateToChange);
		XMLGregorianCalendar xmlCalendar = null;
		try 
		{
			xmlCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendarDate(gCalendar.get(Calendar.YEAR), gCalendar.get(Calendar.MONDAY)+1, gCalendar.get(Calendar.DAY_OF_MONTH),DatatypeConstants.FIELD_UNDEFINED);
		} 
		catch (Exception ex) {
			ex.printStackTrace();
		}
		return xmlCalendar;
	}

	public void createOpsFileReg(String outFileName, String outFilePath)
	{
		OpsFileRegModel opsFileRegModel = new OpsFileRegModel();

		Date date = new Date();
		log.debug("File Created Date");

		BigDecimal sysGenSeqNr = new BigDecimal("99999");

		opsFileRegModel.setFileName(outFileName);
		opsFileRegModel.setFilepath(outFilePath);
		opsFileRegModel.setProcessDate(date);
		opsFileRegModel.setExtractMsgId(outMsgId);
		try {

			opsFileRegModel.setNameSpace(propertyUtil.getPropValue("NameSpace.PAIN010"));
			opsFileRegModel.setStatus(propertyUtil.getPropValue("ProcStatus.Completed"));
			opsFileRegModel.setInOutInd(propertyUtil.getPropValue("OutInd"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		Boolean result = adminBeanRemote.createOpsFileRegModel(opsFileRegModel);

		if (result == true) 
		{
			log.debug("************* EXTRACT : "+outFileName+" has been created successfully in the TEMP directory*************************");

		} 
		else 
		{
			log.error("Error on creating PAIN 010.");
		}
	}

	private void generateMndtCount() {
		todaysDate= new Date();

		boolean saved = false;
		int nrOfFile =1;
		//int nrOfMsgs = extractMandList.size();

		MdtAcOpsMndtCountEntity mdtOpsMndtCountEntity = new MdtAcOpsMndtCountEntity();
		MdtAcOpsMndtCountPK mdtOpsMndtCountPk = new MdtAcOpsMndtCountPK();

		if(doc!= null && doc.getMndtAmdmntReq()!=null && doc.getMndtAmdmntReq().getGrpHdr() != null && doc.getMndtAmdmntReq().getGrpHdr().getMsgId()!=null)
			mdtOpsMndtCountPk.setMsgId(doc.getMndtAmdmntReq().getGrpHdr().getMsgId());
		mdtOpsMndtCountPk.setServiceId(outgoingService);
		if(doc!= null && doc.getMndtAmdmntReq()!=null && doc.getMndtAmdmntReq().getGrpHdr() != null && doc.getMndtAmdmntReq().getGrpHdr().getMsgId()!=null)
			mdtOpsMndtCountPk.setInstId(doc.getMndtAmdmntReq().getGrpHdr().getMsgId().toString().substring(12, 18));
		mdtOpsMndtCountEntity.setNrOfMsgs(nrOfMsgs);
		mdtOpsMndtCountEntity.setNrOfFiles(nrOfFile);
		mdtOpsMndtCountEntity.setNrMsgsAccepted(0);
		mdtOpsMndtCountEntity.setNrMsgsRejected(0);
		mdtOpsMndtCountEntity.setNrMsgsExtracted(nrOfMsgs);
		mdtOpsMndtCountEntity.setProcessDate(new Date());
		mdtOpsMndtCountEntity.setMdtAcOpsMndtCountPK(mdtOpsMndtCountPk);
		mdtOpsMndtCountEntity.setFileName(outFileName);
		try {
			mdtOpsMndtCountEntity.setIncoming(propertyUtil.getPropValue("NonActiveInd"));
			mdtOpsMndtCountEntity.setOutgoing(propertyUtil.getPropValue("ActiveInd"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		saved = valBeanRemote.saveMdtOpsMndtCount(mdtOpsMndtCountEntity);
		if (saved) {
			log.debug("MdtOpsCountTable has been updated");

		} else {
			log.debug("MdtOpsCountTable is not updated");
		}
	}

	public  void copyFile(String fileName) throws IOException 
	{
		File tmpFile = new File("/home/opsjava/Delivery/Mandates/Output/" + fileName +".xml");
		String outputFile = "/home/opsjava/Delivery/Mandates/Output/temp/" + fileName +".xml";
		FileOutputStream fos = new FileOutputStream(tmpFile);
		Path source = Paths.get(outputFile);
		Files.copy(source, fos);
		log.info("Copying "+fileName+"from temp  to output directory...");
	}
	
	


	private static void contextCheck() {
		if (beanRemote == null) {
			beanRemote = Util.getServiceBean();
		}
	}

	private static void contextAdminBeanCheck() {
		if (adminBeanRemote == null) {
			adminBeanRemote = Util.getAdminBean();	
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
