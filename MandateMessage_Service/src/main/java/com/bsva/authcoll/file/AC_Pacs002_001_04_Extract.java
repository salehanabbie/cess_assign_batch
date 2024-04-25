package com.bsva.authcoll.file;

import java.io.File;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;
import javax.ejb.EJB;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.apache.log4j.Logger;
import com.bsva.PropertyUtil;
import com.bsva.commons.model.OpsFileRegModel;
import com.bsva.delivery.StartOfTransmissionExtract;
import com.bsva.entities.MdtAcOpsConfHdrsEntity;
import com.bsva.entities.MdtAcOpsFileSizeLimitEntity;
import com.bsva.entities.MdtAcOpsMandateTxnsEntity;
import com.bsva.entities.MdtAcOpsConfDetailsEntity;
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
import com.bsva.utils.UnmarshallEventHandler;
import com.bsva.utils.Util;
import iso.std.iso._20022.tech.xsd.pacs_002_001.BranchAndFinancialInstitutionIdentification5;
import iso.std.iso._20022.tech.xsd.pacs_002_001.ClearingSystemMemberIdentification2;
import iso.std.iso._20022.tech.xsd.pacs_002_001.Document;
import iso.std.iso._20022.tech.xsd.pacs_002_001.FIToFIPaymentStatusReportV04;
import iso.std.iso._20022.tech.xsd.pacs_002_001.FinancialInstitutionIdentification8;
import iso.std.iso._20022.tech.xsd.pacs_002_001.GroupHeader53;
import iso.std.iso._20022.tech.xsd.pacs_002_001.MandateRelatedInformation8;
import iso.std.iso._20022.tech.xsd.pacs_002_001.OriginalGroupHeader1;
import iso.std.iso._20022.tech.xsd.pacs_002_001.OriginalTransactionReference16;
import iso.std.iso._20022.tech.xsd.pacs_002_001.PaymentTransaction33;
import iso.std.iso._20022.tech.xsd.pacs_002_001.StatusReason6Choice;
import iso.std.iso._20022.tech.xsd.pacs_002_001.StatusReasonInformation9;
import iso.std.iso._20022.tech.xsd.pacs_002_001.TransactionGroupStatus3Code;
import iso.std.iso._20022.tech.xsd.pacs_002_001.TransactionIndividualStatus3Code;

/**
 * @author Saleha Saib
 *Modified By: SalehaR - 2016/10/04 - Aligned to TRS 16
 *@author SalehaR-2019/11/06 - Add Counts and Execution Time
 */
public class AC_Pacs002_001_04_Extract {

	@EJB
	PropertyUtil propertyUtil;
	
	
	private Logger log = Logger.getLogger("AC_Pacs002_Extract");

	public static ServiceBeanRemote beanRemote;
	private static AdminBeanRemote adminBeanRemote;
	public static ValidationBeanRemote valBeanRemote;
	public static FileProcessBeanRemote fileProcessBeanRemote;

	String backEndProcess = "BACKEND";
	String readyForExtractStatus = "3";
	String extractedStatus = "4";
	String messgId;
	public Date todaysDate;
	CasSysctrlCompParamEntity mdtSysctrlCompParamEntity = null;
	CasSysctrlSysParamEntity casSysctrlSysParamEntity;
	MdtOpsRefSeqNrEntity mdtOpsRefSeqNrEntity = null;
	String serviceId, mdtReqId, instdIdFileName, instIdMsgId;
	List<MdtAcOpsConfDetailsEntity> transErrorList, statusDetailsList;
	List<MdtOpsCustParamEntity> custParamsList = null;
	List<SysCisBankEntity> sysCisBankList = null;

	String pacs002ServiceName = null, outputFileBic = null;
	int lastSeqNoUsed;
	String groupStatus = null;
	boolean populateOpi = false, populateOad = false;
	MdtAcOpsSotEotCtrlEntity mdtAcOpsSotEotCtrlEntity;

	private String pacs002Schema = "/home/opsjava/Delivery/Mandates/Schema/pacs.002.001.04.xsd";
	private String urn = "urn:iso:std:iso:20022:tech:xsd:pacs.002.001.04";
	private String outgoingService = "ST103";
	private Document pacsDocument;
	String outFileName,tableOutFileName;
	int nrOfMsgs = 0;
	String xmlDateFormat = "yyyy-MM-dd'T'HH:mm:ss"; 
	Date currentDate = new Date();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	int accpTxns = 0, rejTxns = 0;
	String nonrefErrorCode = "NONREF", rjctErrorCode="902203";
	List<String> txnIdList = null;
	List<MdtAcOpsConfDetailsEntity> orgnConfDetailTxnList = null;
	List<String>extOrigMRTIList=  null;

	List<String> extractServiceList = new ArrayList<String>();
	Date procDate = null;
	long startTime, endTime, duration;
	String testLiveIndProp = null;

	

	public AC_Pacs002_001_04_Extract()
	{
		contextCheck();
		contextAdminBeanCheck();
		contextValidationBeanCheck();
		contextFileProcBeanCheck();
		extractServiceList.clear();
		extractServiceList.add("pain.009");
		extractServiceList.add("pain.010");
		extractServiceList.add("pain.011");
		//		extractServiceList.add("camt.055");
		
		try{
			propertyUtil = new PropertyUtil();
			this.testLiveIndProp = propertyUtil.getPropValue("TestLiveInd");
			//log.info("Test Live Indicator Property: "+testLiveIndProp);

		}catch (Exception e) {
			log.error("AC_Pacs002_001_04_Extract - Could not find MandateMessageCommons.properties in classpath");
		}

	}

	public void generatePacs002Report() throws Exception
	{
		log.debug("Extracting Pacs 002 File (ST103)");

		mdtSysctrlCompParamEntity = (CasSysctrlCompParamEntity) valBeanRemote.retrieveCompanyParameters(backEndProcess);
		log.debug("mdtSysctrlCompParamEntity in FileExtract: "+mdtSysctrlCompParamEntity);

		casSysctrlSysParamEntity = new CasSysctrlSysParamEntity();
		casSysctrlSysParamEntity = (CasSysctrlSysParamEntity) adminBeanRemote.retrieveActiveSysParameter();
		log.debug("PAIN009 - mdtSysctrlSysParamEntity in FileExtract: "+casSysctrlSysParamEntity);

		String destInstId = null;
		txnIdList = new ArrayList<String>();



		custParamsList = new ArrayList<MdtOpsCustParamEntity>();
		custParamsList = (List<MdtOpsCustParamEntity>) beanRemote.retrieveActiveCustomerParameters();

		if(custParamsList.size() > 0)
		{
			for (MdtOpsCustParamEntity custParamEntity : custParamsList) 
			{
				destInstId = custParamEntity.getInstId();


				sysCisBankList = new ArrayList<SysCisBankEntity>();
				sysCisBankList = (List<SysCisBankEntity>)adminBeanRemote.retrieveSysMemberNo(destInstId);	
				//log.info("sysCisBankList********* in the pain 009 extract" + sysCisBankList);
				if(sysCisBankList.size() > 0)
				{

					for (String extractService : extractServiceList) 
					{
						//						log.info("~~~EXT SERVICE = "+extractService);
						// List<String> txnIdList = new ArrayList<String>();
						txnIdList = (List<String>) beanRemote.retrieveDistinctConfDetails(destInstId, extractService);
						log.debug("txnIdList ==> "+txnIdList);					

						if(txnIdList != null && txnIdList.size() > 0)
						{

							MdtAcOpsFileSizeLimitEntity mappedFileSizeLimit = (MdtAcOpsFileSizeLimitEntity) fileProcessBeanRemote.retriveOutgoingService(outgoingService,destInstId);
							
							if(mappedFileSizeLimit != null && destInstId.equalsIgnoreCase(mappedFileSizeLimit.getMdtAcOpsFileSizeLimitPK().getMemberId()))
							{
								destInstId =mappedFileSizeLimit.getMdtAcOpsFileSizeLimitPK().getMemberId();
								String limit =mappedFileSizeLimit.getLimit();
								log.debug("limit---->"+limit+"");
								int fileLimit = Integer.valueOf(limit);

								List<ArrayList<String>> chunckedList = getChunks(txnIdList,fileLimit);
								orgnConfDetailTxnList =  new ArrayList<MdtAcOpsConfDetailsEntity>();
								log.debug("fileLimit---->"+fileLimit+"");


								for(ArrayList<String> chunk :chunckedList)
								{
									extOrigMRTIList = new ArrayList<String>();
									startTime = System.nanoTime();
									nrOfMsgs = 0;
									nrOfMsgs = chunk.size();
									try
									{
										log.info("********EXTRACTING PACS002(ST103) file for "+destInstId+"********");
										accpTxns = 0; rejTxns = 0;
										FIToFIPaymentStatusReportV04 fiToFiPmtStsRep04;

										String transId = chunk.get(0);
										String bankCode = transId.substring(0, 4);
										log.debug("bankCode: "+bankCode);

										//								generateSOT("21"+bankCode, outgoingService);

										pacsDocument = new Document();
										fiToFiPmtStsRep04 = new FIToFIPaymentStatusReportV04();

										GroupHeader53 groupHeader = new GroupHeader53();
										groupHeader = populateGroupHdr(destInstId);

										//Retrieve Ops_Status_Hdrs Entity
										//Retrieve the Hdr Record
										statusDetailsList = new ArrayList<MdtAcOpsConfDetailsEntity>();
										statusDetailsList = (List<MdtAcOpsConfDetailsEntity>) beanRemote.retrieveConfDetails(transId, outgoingService, extractService);
										//								log.info("statusDetailsList: "+statusDetailsList);
										MdtAcOpsConfDetailsEntity confDetailsEntity = statusDetailsList.get(0);

										OriginalGroupHeader1 originalGrpHdrStsInf = new OriginalGroupHeader1(); 
										originalGrpHdrStsInf = populateOriginalGrpHdrInfAndSts(confDetailsEntity.getConfHdrSeqNo(), confDetailsEntity.getOrgnlMsgType()); 


										//_________Retrieve Transactions Errors_______//


										log.info("Populating Transaction Errors Information....."+ chunk.size());

										for (String txnid : chunk) 
										{
											PaymentTransaction33 paymentInfo = new PaymentTransaction33();
											paymentInfo = generateTransErrors(txnid, extractService);
											

											//_______Add Transaction Info and Status____//
											if(paymentInfo != null)
												fiToFiPmtStsRep04.getTxInfAndSts().add(paymentInfo);
										}	

										//_______Add Groupd Header & Original GrpHdr Info and Status____//
										log.debug("accpTxns: "+accpTxns);
										log.debug("rejTxns: "+rejTxns);

										StatusReasonInformation9 statRsnInfo = new StatusReasonInformation9();
										StatusReason6Choice stRsnChoice = new StatusReason6Choice();

										if(accpTxns > 0 && rejTxns > 0)
										{
											log.debug("Group Status is :"+TransactionGroupStatus3Code.PART);
											originalGrpHdrStsInf.setGrpSts(TransactionGroupStatus3Code.PART);
											stRsnChoice.setPrtry(nonrefErrorCode);
											statRsnInfo.setRsn(stRsnChoice);
											originalGrpHdrStsInf.getStsRsnInf().add(statRsnInfo);
										}
										else
										{
											if(accpTxns > 0 && rejTxns == 0)
											{

												log.debug("Group Status is :"+TransactionGroupStatus3Code.ACCP);
												log.debug("originalGrpHdrStsInf: "+originalGrpHdrStsInf);
												originalGrpHdrStsInf.setGrpSts(TransactionGroupStatus3Code.ACCP);
												stRsnChoice.setPrtry(nonrefErrorCode);
												statRsnInfo.setRsn(stRsnChoice);
												originalGrpHdrStsInf.getStsRsnInf().add(statRsnInfo);
											}
											else
											{
												if(accpTxns == 0 && (rejTxns > 0 || rejTxns == 0))
												{
													log.debug("Group Status is :"+TransactionGroupStatus3Code.RJCT);
													originalGrpHdrStsInf.setGrpSts(TransactionGroupStatus3Code.RJCT);
													stRsnChoice.setPrtry(rjctErrorCode);
													statRsnInfo.setRsn(stRsnChoice);
													originalGrpHdrStsInf.getStsRsnInf().add(statRsnInfo);
												} 
											}
										}
										fiToFiPmtStsRep04.setGrpHdr(groupHeader);
										fiToFiPmtStsRep04.setOrgnlGrpInfAndSts(originalGrpHdrStsInf);

										//_______Add FiToFiPmtSts to pacsDoc____//
										pacsDocument.setFIToFIPmtStsRpt(fiToFiPmtStsRep04);	  

										if(pacsDocument != null)
											createPacs002(pacsDocument, outgoingService);
										saveBulkConfirmations();
										generateMndtCount();	
										endTime = System.nanoTime();
										duration = (endTime - startTime) / 1000000;
										log.info("|"+outFileName+" EXTRACTED | TOT_EXTRACTED "+chunk.size()+" | EXEC TIME: "+DurationFormatUtils.formatDuration(duration, "HH:mm:ss.S")+" milliseconds |");

									}//end of try
									catch (Exception e) 
									{
										log.error("**** Exception Creating Pacs 002 Extract File (ST103) **** : " + e);
										e.printStackTrace();
										e.getCause();
										throw new Exception(e);
									}
								}
							}
							else
								log.debug("**** No Entry In Ops Status Report Details --- PACS 002 File (ST103) will not be produced ---");
						}
					}	
				}

			}
		}
	}

		private  ArrayList<ArrayList<String>> getChunks(List<String> extractMandList,int chunkSize)
		{           
		            AtomicInteger counter = new AtomicInteger();

		            ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();

		            for(String value : extractMandList)
		            {               
		                if (counter.getAndIncrement() % chunkSize == 0)
		                {
		                    result.add(new ArrayList<String>());
		                    
		                }
		                
		                result.get(result.size() - 1).add(value);
		            }
		            
		            return result;
		  }

		

	public  void createPacs002(Document doc, String serviceId) 
	{
		try 
		{
			outFileName = generatePacs002FileName(serviceId); 

			String out ="/home/opsjava/Delivery/Mandates/Output/temp/"+outFileName+".xml";
			File f = new File("/home/opsjava/Delivery/Mandates/Output/temp/" + outFileName +".xml")  ;  

			SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = sf.newSchema(new File(pacs002Schema));

			JAXBContext jaxbContext = JAXBContext.newInstance(Document.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setSchema(schema);
			jaxbMarshaller.setEventHandler(new UnmarshallEventHandler());
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.setProperty("com.sun.xml.bind.xmlDeclaration", Boolean.FALSE);
			jaxbMarshaller.setProperty("com.sun.xml.bind.xmlHeaders","<?xml version=\"1.0\" encoding=\"UTF-8\"?>");

			QName qName = new QName(urn, "Document");
			JAXBElement<Document> root = new JAXBElement<Document>(qName, Document.class, doc);

			jaxbMarshaller.marshal(root, f);

			createOpsFileReg(outFileName, out);
			copyFile(outFileName);
		} 
		catch (Exception e) 
		{
			log.error("Error on creating createPacs002 extract file: "+e.getMessage());
			e.printStackTrace();
		}
	}


	public String generateMsgId(String instId)
	{
		log.debug("In the generateMsgId()" );
		SimpleDateFormat sdfFileDate = new SimpleDateFormat("yyyyMMdd");
		String achId, creationDate, fileSeqNo, liveTestInd, msgId = null;
		outgoingService = "ST103";
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


			//			    creationDate = sdfFileDate.format(new Date());
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
			log.error("**** Exception generating ST103 MsgId **** : " + e);
			e.printStackTrace();
			e.getCause();
		}

		return msgId;
	}

	public String generatePacs002FileName(String service)
	{
		String achId, creationDate, fileSeqNo, testLiveInd, fileName = null;
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
			log.debug("PACS 002 File No To be used====. :"+fileSeqNo);

			//					creationDate = sdfFileDate.format(new Date());
			//TRS16 Processing Rules					    
			if(casSysctrlSysParamEntity != null && casSysctrlSysParamEntity.getProcessDate() != null)
			{
				creationDate = sdfFileDate.format(casSysctrlSysParamEntity.getProcessDate());
			}
			else
			{
				creationDate = sdfFileDate.format(new Date());
			}

			fileName = achId+service+"00"+instdIdFileName+"S"+creationDate+fileSeqNo+testLiveInd+"D";
		}
		catch (Exception e) 
		{
			log.error("**** Exception generating ST103 FileName **** : " + e);
			e.printStackTrace();
			e.getCause();
		}

		return fileName;

	}

	public XMLGregorianCalendar getGregorianDate(Date dateToChange) {

		GregorianCalendar gCalendar = new GregorianCalendar();
		gCalendar.setTime(dateToChange);
		XMLGregorianCalendar xmlCalendar = null;
		try {
			xmlCalendar = DatatypeFactory.newInstance()
					.newXMLGregorianCalendar(gCalendar);
		} catch (Exception ex) {
			// Logger.getLogger(StringReplace.class.getName()).log(Level.SEVERE,
			// null, ex);
			ex.printStackTrace();
		}
		return xmlCalendar;
	}

	public GroupHeader53 populateGroupHdr(String destInstId)
	{
		log.debug("-------------populating the Group Hdr--------------");
		BranchAndFinancialInstitutionIdentification5 branchFinInfo;
		FinancialInstitutionIdentification8 finInstInfo;
		ClearingSystemMemberIdentification2 clearingSystemMemberIdentification2;


		//_______________POPULATE GRPHDR INFO________________// 
		GroupHeader53 grpHeader = new GroupHeader53();

		messgId = generateMsgId(destInstId);
		grpHeader.setMsgId(messgId);

		procDate = new Date();

		if(casSysctrlSysParamEntity != null)
		{
			procDate = casSysctrlSysParamEntity.getProcessDate();

			Calendar calDateThen = Calendar.getInstance();
			Calendar calTimeNow = Calendar.getInstance();

			calDateThen.setTime(procDate);
			calDateThen.set(Calendar.HOUR_OF_DAY, calTimeNow.get(Calendar.HOUR_OF_DAY));
			calDateThen.set(Calendar.MINUTE, calTimeNow.get(Calendar.MINUTE));
			calDateThen.set(Calendar.SECOND, calTimeNow.get(Calendar.SECOND));
			procDate = calDateThen.getTime();
			//					log.debug("----> calTimeNow.getTimeInMillis() "+calTimeNow.getTimeInMillis());
			//					log.debug("----> procDate with Time "+procDate);
			procDate.setTime(calTimeNow.getTimeInMillis());
			log.debug("PROC DATE AFTER TIME SET ----> "+ procDate);
		}

		grpHeader.setCreDtTm(DateUtil.toXMLGregorianCalendarWithFormat(procDate, xmlDateFormat));

		//	Set Instructing Agent - This will be BSVA
		String instrtgAgnt = "210000";
		if(mdtSysctrlCompParamEntity != null)
			instrtgAgnt = mdtSysctrlCompParamEntity.getAchInstId();
		else
			instrtgAgnt="210000";

		//Set Instructing Agent
		branchFinInfo =  new BranchAndFinancialInstitutionIdentification5();
		finInstInfo = new FinancialInstitutionIdentification8();
		clearingSystemMemberIdentification2 = new ClearingSystemMemberIdentification2();

		clearingSystemMemberIdentification2.setMmbId(instrtgAgnt);
		finInstInfo.setClrSysMmbId(clearingSystemMemberIdentification2);
		branchFinInfo.setFinInstnId(finInstInfo);

		grpHeader.setInstgAgt(branchFinInfo);


		//Set Instructed Agent
		branchFinInfo = new BranchAndFinancialInstitutionIdentification5();
		finInstInfo = new FinancialInstitutionIdentification8();
		clearingSystemMemberIdentification2  = new ClearingSystemMemberIdentification2();

		clearingSystemMemberIdentification2.setMmbId(destInstId);
		finInstInfo.setClrSysMmbId(clearingSystemMemberIdentification2);
		branchFinInfo.setFinInstnId(finInstInfo);

		grpHeader.setInstdAgt(branchFinInfo);


		instdIdFileName = destInstId;
		//		}


		log.debug("-------------finish populating the Group Hdr--------------");
		return grpHeader;		
	}

	public OriginalGroupHeader1 populateOriginalGrpHdrInfAndSts(BigDecimal hdrSeqNo, String extService) 
	{
		log.debug("-------------populating the Group Hdr inf and status--------------");
		OriginalGroupHeader1 orgGrpHdr = null;

		//		//Retrieve Status Hdr Record
		//		MdtAcOpsConfHdrsEntity mdtAcOpsConfHdrsEntity = (MdtAcOpsConfHdrsEntity) beanRemote.retrieveConfHdrsBySeqNo(hdrSeqNo);
		//		log.info("mdtAcOpsConfHdrsEntity---> "+mdtAcOpsConfHdrsEntity);
		//		
		//		if(mdtAcOpsConfHdrsEntity != null)
		//		{
		//_______________POPULATE ORGNL GRP INFO & STATUS________________// 
		orgGrpHdr = new OriginalGroupHeader1();

		if(messgId != null)
			//2017-01-11 - SalehaR	- SCR 131/132		    	   
			//		    	   orgGrpHdr.setOrgnlMsgId(mdtAcOpsConfHdrsEntity.getOrgnlMsgId());
			orgGrpHdr.setOrgnlMsgId(messgId);

		if(extService != null)
			orgGrpHdr.setOrgnlMsgNmId(extService);


		if(procDate == null)
		{
			procDate = new Date();
		}

		//		    	   orgGrpHdr.setOrgnlCreDtTm(DateUtil.toXMLGregorianCalendarWithFormat(mdtAcOpsConfHdrsEntity.getOrgnlCreateDateTime(), xmlDateFormat));
		orgGrpHdr.setOrgnlCreDtTm(DateUtil.toXMLGregorianCalendarWithFormat(procDate, xmlDateFormat));




		//		}

		return orgGrpHdr;
	}
	


	public PaymentTransaction33 generateTransErrors(String txnId, String orgnlMsgType) 
	{
		log.debug("TXN ID ----> "+txnId);
		PaymentTransaction33 transInfo = null;
		transInfo = new PaymentTransaction33();
		
	
		if(txnId != null)
			transInfo.setOrgnlTxId(txnId);

		transErrorList = (List<MdtAcOpsConfDetailsEntity>) beanRemote.retrieveConfDetails(txnId, outgoingService, orgnlMsgType);

		if(transErrorList != null && transErrorList.size() > 0)
		{	
			String mandRefNo = transErrorList.get(0).getMandateRefNumber();
			if(mandRefNo != null)
			{
				OriginalTransactionReference16 originalTransactionReference16 = new OriginalTransactionReference16();
				MandateRelatedInformation8 mandateRelatedInformation8 = new MandateRelatedInformation8();
				mandateRelatedInformation8.setMndtId(mandRefNo);
				originalTransactionReference16.setMndtRltdInf(mandateRelatedInformation8);
				transInfo.setOrgnlTxRef(originalTransactionReference16);
			}


			for (MdtAcOpsConfDetailsEntity txnErrorEntity : transErrorList) 
			{

				if(txnErrorEntity.getTxnStatus().equalsIgnoreCase("ACCP"))
				{
					transInfo.setTxSts(TransactionIndividualStatus3Code.ACCP);
					accpTxns = accpTxns + 1;
				}
				else 
				{
					if(txnErrorEntity.getTxnStatus().equalsIgnoreCase("RJCT"))
					{
						transInfo.setTxSts(TransactionIndividualStatus3Code.RJCT);
						rejTxns = rejTxns + 1;
					}
				}

				StatusReasonInformation9 statusReasonInformation9 = new StatusReasonInformation9();
				StatusReason6Choice statusReason6Choice = new StatusReason6Choice();

				statusReason6Choice.setPrtry(txnErrorEntity.getErrorCode());
				statusReasonInformation9.setRsn(statusReason6Choice);

				transInfo.getStsRsnInf().add(statusReasonInformation9);

				if(txnErrorEntity.getProcessStatus().equalsIgnoreCase(readyForExtractStatus))
				{

					tableOutFileName = generatePacs002FileName(outgoingService);
					extOrigMRTIList.add("'"+txnErrorEntity.getTxnId()+"'");
//					txnErrorEntity.setExtractMsgId(messgId); 
//					txnErrorEntity.setProcessStatus(extractedStatus);
//					txnErrorEntity.setExtractFileName(tableOutFileName);
//					log.info("Status Details after process status change: "+ txnErrorEntity);
//					beanRemote.updateConfDetails(txnErrorEntity);
//					orgnConfDetailTxnList.add(txnErrorEntity);
				}
				
			}
				
		}

		return transInfo;
		}

	
	

	public void createOpsFileReg(String outFileName, String outFilePath)
	{
		OpsFileRegModel opsFileRegModel = new OpsFileRegModel();

		Date date = new Date();
		log.debug("File Created Date");

		opsFileRegModel.setFileName(outFileName);
		opsFileRegModel.setFilepath(outFilePath);
		opsFileRegModel.setNameSpace("iso:std:iso:20022:tech:xsd:pacs.002.001.04");
		opsFileRegModel.setProcessDate(date);
		opsFileRegModel.setStatus("C");
		opsFileRegModel.setInOutInd("O");
		opsFileRegModel.setExtractMsgId(messgId);

		Boolean result = adminBeanRemote.createOpsFileRegModel(opsFileRegModel);

		if (result == true) 
		{
			log.debug("************* EXTRACT : "+outFileName+" has been created successfully in TEMP directory.*************************");

		} 
		else 
		{
			log.error("Error on creating PACS 002 Extract.");
		}
	}

	//20170602 - SalehaR - SOT/EOT created at SOD and EOD
//	public void generateSOT(String instgAgt, String service)
//	{
//		//Retrieve SOT/EOT Ind
//		mdtAcOpsSotEotCtrlEntity = new MdtAcOpsSotEotCtrlEntity();
//		mdtAcOpsSotEotCtrlEntity =  (MdtAcOpsSotEotCtrlEntity) beanRemote.retrieveSOTEOTCntrl(instgAgt, service);
//		log.debug("mdtAcOpsSotEotCtrlEntity: "+mdtAcOpsSotEotCtrlEntity);
//
//		if(mdtAcOpsSotEotCtrlEntity != null)
//		{
//			if(mdtAcOpsSotEotCtrlEntity.getSotOut().equalsIgnoreCase("N"))
//			{
//				StartOfTransmissionExtract startOfTransmissionExtract = new StartOfTransmissionExtract(instgAgt, service, "S");
//				startOfTransmissionExtract.createStartOfTransmissionFile();
//
//				mdtAcOpsSotEotCtrlEntity.setSotOut("Y");
//
//				boolean updated = beanRemote.updateSOTEOTCntrl(mdtAcOpsSotEotCtrlEntity);
//
//			}
//		}
//	}


	public  void copyFile(String fileName) throws IOException 
	{
		File tmpFile = new File("/home/opsjava/Delivery/Mandates/Output/" + fileName +".xml");
		String outputFile = "/home/opsjava/Delivery/Mandates/Output/temp/" + fileName +".xml";
		FileOutputStream fos = new FileOutputStream(tmpFile);
		Path source = Paths.get(outputFile);
		Files.copy(source, fos);
		log.info("Copying "+fileName+"from temp  to output directory...");
	}

	private void generateMndtCount() {

		todaysDate= new Date();

		boolean saved = false;
		int nrOfFile =1;
		//int nrOfMsgs = txnIdList.size();
		//		int nrOfRejectedMsg = extractDataList.size();
		//		int countRejectedMsgs=Integer.valueOf(nrOfRejectedMsg);
		//		int countMsg = Integer.valueOf(nrOfMsg);
		//		nrOfFile = nrOfFile +1;

		log.debug("# of mandates submitted ******--->" + nrOfMsgs);

		MdtAcOpsMndtCountEntity mdtOpsMndtCountEntity = new MdtAcOpsMndtCountEntity();
		MdtAcOpsMndtCountPK mdtOpsMndtCountPk = new MdtAcOpsMndtCountPK();

		if(pacsDocument!= null && pacsDocument.getFIToFIPmtStsRpt()!=null && pacsDocument.getFIToFIPmtStsRpt().getGrpHdr() != null && pacsDocument.getFIToFIPmtStsRpt().getGrpHdr().getMsgId()!=null)
			mdtOpsMndtCountPk.setMsgId(pacsDocument.getFIToFIPmtStsRpt().getGrpHdr().getMsgId());
		mdtOpsMndtCountPk.setServiceId(outgoingService);
		if(pacsDocument!= null && pacsDocument.getFIToFIPmtStsRpt()!=null && pacsDocument.getFIToFIPmtStsRpt().getGrpHdr() != null && pacsDocument.getFIToFIPmtStsRpt().getGrpHdr().getMsgId()!=null)
			mdtOpsMndtCountPk.setInstId(pacsDocument.getFIToFIPmtStsRpt().getGrpHdr().getMsgId().toString().substring(12, 18));
		mdtOpsMndtCountEntity.setNrOfMsgs(nrOfMsgs);
		mdtOpsMndtCountEntity.setNrOfFiles(nrOfFile);
		mdtOpsMndtCountEntity.setNrMsgsAccepted(0);
		mdtOpsMndtCountEntity.setNrMsgsRejected(0);
		mdtOpsMndtCountEntity.setNrMsgsExtracted(nrOfMsgs);
		mdtOpsMndtCountEntity.setIncoming("N");
		mdtOpsMndtCountEntity.setProcessDate(todaysDate);
		mdtOpsMndtCountEntity.setOutgoing("Y");
		mdtOpsMndtCountEntity.setMdtAcOpsMndtCountPK(mdtOpsMndtCountPk);
		mdtOpsMndtCountEntity.setFileName(outFileName);

		saved = valBeanRemote.saveMdtOpsMndtCount(mdtOpsMndtCountEntity);

		log.debug("WRITING mdtOpsMndtCountEntity IN THE AC_Pacs002FileExtract"+mdtOpsMndtCountEntity);


		if (saved) {
			log.debug("MdtOpsCountTable has been updated");

		} else {
			log.debug("MdtOpsCountTable is not updated");

		}


	}


	public static boolean isWeekend(String ts)
	{

		int year = Integer.parseInt(ts.substring(0, 4));
		int month = Integer.parseInt(ts.substring(4, 6));
		int day = Integer.parseInt(ts.substring(6, 8));
		Calendar cal = new GregorianCalendar(year, month - 1, day);
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		return (Calendar.SATURDAY == dayOfWeek || Calendar.SUNDAY == dayOfWeek);
	}

	
	public void saveBulkConfirmations() {		
		log.info("xxxxx In the saveBulkConfirmations xxxxxxxx");
		log.info("orgnConfDetailTxnListxxxxx "+orgnConfDetailTxnList.size());
		
		fileProcessBeanRemote.openHibernateSession();

		try
		{
			log.info("==================== BULK UPDATE ST103 TXNS ====================");
			fileProcessBeanRemote.bulkUpdateMandates(orgnConfDetailTxnList);
			bulkUpdateViaSQL();
		}
		catch (Exception e) 
		{
			log.error("Error on saveBulkConfirmations: " + e.getMessage());
			e.printStackTrace();
		}
		
		fileProcessBeanRemote.closeHibernateSession();
	}
	
	public void bulkUpdateViaSQL() 
	{
		int targetSize = 1000;

		String extractMsgId = messgId; 
		String extractFileName = tableOutFileName;
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
			log.info("XXXXX BULK UPDATE ST103 MANDATE XXXXX");
			List<List<String>> partitionList = ListUtils.partition(extOrigMRTIList, targetSize);
			log.info("Original Mandate partition List size: "+partitionList.size());

			for (List<String> mrtiToUpdateList : partitionList) {
				String joinResult = null;

				joinResult = StringUtils.join(mrtiToUpdateList,",");
				String sqlQuery = new String("UPDATE MANOWNER.MDT_AC_OPS_CONF_DETAILS SET  PROCESS_STATUS = '"+extractedStatus+"',EXTRACT_MSG_ID = '"+extractMsgId+"',EXTRACT_FILE_NAME ='"+extractFileName+"'  WHERE TXN_ID IN ("+joinResult+")");
				log.debug("SQL query---->" +sqlQuery);

				fileProcessBeanRemote.bulkUpdateBySQL(sqlQuery);
			}	        
		}
	}

	
	

	private static void contextCheck() {
		if (beanRemote == null) {
			beanRemote = Util.getServiceBean();
		}
	}

	private static void contextAdminBeanCheck() 
	{
		if (adminBeanRemote == null) 
		{
			adminBeanRemote = Util.getAdminBean();	
		}
	}


	public static void contextValidationBeanCheck() 
	{
		if (valBeanRemote == null) {
			valBeanRemote = Util.getValidationBean();
		}				
	}
	
	public static void contextFileProcBeanCheck()
	{
		if (fileProcessBeanRemote == null) {
			fileProcessBeanRemote = Util.getFileProcessBean();
		}
	}

}
