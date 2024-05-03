package com.bsva.authcoll.singletable.file;

import com.bsva.PropertyUtil;
import com.bsva.authcoll.singletable.validation.AC_Pacs002_Validation_ST;
import com.bsva.entities.CasOpsCessionAssignEntity;
import com.bsva.entities.CasOpsConfDetailsEntity;
import com.bsva.entities.CasOpsConfHdrsEntity;
import com.bsva.entities.CasOpsFileRegEntity;
import com.bsva.entities.CasOpsMndtCountEntity;
import com.bsva.entities.CasOpsMndtCountPK;
import com.bsva.entities.CasOpsStatusHdrsEntity;
import com.bsva.entities.CasSysctrlCompParamEntity;
import com.bsva.entities.CasSysctrlSysParamEntity;
import com.bsva.interfaces.AdminBeanRemote;
import com.bsva.interfaces.FileProcessBeanRemote;
import com.bsva.interfaces.ServiceBeanRemote;
import com.bsva.interfaces.ValidationBeanRemote;
import com.bsva.utils.PainUnmarshaller;
import com.bsva.utils.Util;
import iso.std.iso._20022.tech.xsd.pacs_002_001.Document;
import iso.std.iso._20022.tech.xsd.pacs_002_001.PaymentTransaction33;
import iso.std.iso._20022.tech.xsd.pacs_002_001.StatusReasonInformation9;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.xml.datatype.XMLGregorianCalendar;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.apache.log4j.Logger;

/**
 * @author Saleha Saib
 *Modified by DimakatsoN - 2016/09/13 - Alignment to TRS 15
 *Modified by SalehaR - 2016/10/04 - Alignment to TRS 16
 *@author SalehaR-2019/05/10 Debug Statements & Code CleanUp
 *@author SalehaR-2019/09/21: Align to Single Table (MDT_AC_OPS_MANDATE_TXNS)
 */
public class AC_Pacs002_Loader_ST implements Serializable 
{
	public static Logger log = Logger.getLogger("AC_Pacs002_Loader_ST");

	private static final long serialVersionUID = 1L;

	@EJB
	PropertyUtil propertyUtil;

	public static String systemName = "CAMOWNER";
	public static Date todaysDate;
	public static AdminBeanRemote adminBeanRemote;
	public static ServiceBeanRemote beanRemote;
	public static ValidationBeanRemote valBeanRemote;
	public static FileProcessBeanRemote fileProcessBeanRemote;
	public static boolean result, unmarshall = false,grpHdrCreated= false;

	private CasSysctrlSysParamEntity casSysctrlSysParamEntity = null;
	private CasSysctrlCompParamEntity mdtSysctrlCompParamEntity = null;
	private CasOpsConfHdrsEntity confHdrEntity = null;
	private CasOpsConfDetailsEntity confDetEntity = null;

	List<PaymentTransaction33> transactionList = null;
	List<StatusReasonInformation9> grpHdrErrorList, transErrorList = null;
	List<CasOpsConfDetailsEntity> confDetailsList=null;
//	List<MdtAcOpsMandateTxnsEntity> origTxnsList = null;
	List<String> accpMRTIList = null;
	List<String> rjctMRTIList = null;

	Document document;
	AC_Pacs002_Validation_ST ac_Pacs002_Validation_ST;
	String fileName;
	BigDecimal hdrSystemSeqNo = BigDecimal.ZERO;
	int mandateSeverity = 0, grpHdrSeverity = 0;

	private String pacs002Schema = "/home/opsjava/Delivery/Cession_Assign/Schema/pacs.002.001.04.xsd";
	private String backEndProcess = "BACKEND";
	long startTime, endTime, duration;

	List<CasOpsConfDetailsEntity> acceptConfDtlsList =new ArrayList<CasOpsConfDetailsEntity>();

	public  AC_Pacs002_Loader_ST(String filepath, String fileName) 
	{
		startTime = System.nanoTime();
		//		log.debug("*************************LOADING PACS 002*****************************");
		contextCheck();
		contextAdminBeanCheck();
		contextValidationBeanCheck();
		contextFileProcBeanCheck();

		this.fileName = fileName;
		confDetailsList = new ArrayList<CasOpsConfDetailsEntity>();
//		origTxnsList = new ArrayList<MdtAcOpsMandateTxnsEntity>();
		accpMRTIList = new ArrayList<String>();
		rjctMRTIList = new ArrayList<String>();
		casSysctrlSysParamEntity = new CasSysctrlSysParamEntity();
		casSysctrlSysParamEntity = (CasSysctrlSysParamEntity) adminBeanRemote.retrieveActiveSysParameter();
		mdtSysctrlCompParamEntity = (CasSysctrlCompParamEntity) valBeanRemote.retrieveCompanyParameters(backEndProcess);

		try
		{
			propertyUtil = new PropertyUtil();
		}
		catch (Exception e) 
		{
			log.error("AC_Pacs002_Loader_ST - Could not find CessionAssignment.properties in classpath");
		}

		try
		{
			document = (Document) PainUnmarshaller.unmarshallFile(filepath, "iso.std.iso._20022.tech.xsd.pacs_002_001", fileName, pacs002Schema);

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
				casOpsFileRegEntity.setGrpHdrMsgId(document.getFIToFIPmtStsRpt().getGrpHdr().getMsgId());
				casOpsFileRegEntity.setStatus("V");
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

			// _______________________Entities_______________________

			confHdrEntity = new CasOpsConfHdrsEntity();
			confDetEntity = new CasOpsConfDetailsEntity();

			//_______________XSD LIST__________________________
			transactionList = new ArrayList<PaymentTransaction33>();
			transactionList = document.getFIToFIPmtStsRpt().getTxInfAndSts();

			grpHdrErrorList = new ArrayList<StatusReasonInformation9>();
			grpHdrErrorList = document.getFIToFIPmtStsRpt().getOrgnlGrpInfAndSts().getStsRsnInf();

			transErrorList = new ArrayList<StatusReasonInformation9>();
			acceptConfDtlsList = new ArrayList<CasOpsConfDetailsEntity>();

			// _______________________Mandate grpHdr Unmarshall_______________________
			long ghvalStartTime = System.nanoTime();
			ac_Pacs002_Validation_ST = new AC_Pacs002_Validation_ST(fileName);

			grpHdrSeverity = ac_Pacs002_Validation_ST.validateGroupHeader(document.getFIToFIPmtStsRpt(), transactionList.size());
			log.info("grpHdrSeverity: "+ grpHdrSeverity);

			if(grpHdrSeverity == 0)
			{
				int accptCnt = 0;
				int rejCnt = 0;
				int nrOfMndtsInFile = transactionList.size();

				loadConfHdr();
				long ghvalEndTime = System.nanoTime();
				long ghvalDuration = (ghvalEndTime - ghvalStartTime) / 1000000;
				//				log.info("GROUP HDR VAL/LOAD "+DurationFormatUtils.formatDuration(ghvalDuration, "HH:mm:ss.S")+" milliseconds. "); 

				if(transactionList != null && transactionList.size() > 0) 
				{
					long valStartTime = System.nanoTime();
					//					log.info("XxXxXxX VALIDATING TRANSACTIONS IN FILE "+fileName+ " XxXxXxX");
					//DUPLICATE VALIDATION CHECK 
					List<PaymentTransaction33> uniqueList = (List<PaymentTransaction33>) ac_Pacs002_Validation_ST.findDuplicateTxns(transactionList);
					long duplEndTime = System.nanoTime();
					long duplDur = (duplEndTime - valStartTime) / 1000000;
					//					log.info("DUPLICATES IN FILE EXECUTION TIME IS "+DurationFormatUtils.formatDuration(duplDur, "HH:mm:ss.S")+" milliseconds. ");

					if(uniqueList != null && uniqueList.size() > 0) {
						rejCnt = rejCnt + (nrOfMndtsInFile - uniqueList.size());

						for (PaymentTransaction33 transaction : uniqueList) 
						{
							mandateSeverity = 0;
							mandateSeverity = ac_Pacs002_Validation_ST.validateTransaction(transaction);

							if(mandateSeverity == 0) 
							{
								mapConfDetails(transaction, ac_Pacs002_Validation_ST.casOpsCessAssignTxnsEntityOriginal);
							}
							else
							{
								rejCnt = rejCnt + 1;
							}
						}
					}
					// Reset for next file process
					grpHdrCreated = false;

					long valEndTime = System.nanoTime();
					long valDuration = (valEndTime - valStartTime) / 1000000;
					log.info("VALIDATION TIME IS "+DurationFormatUtils.formatDuration(valDuration, "HH:mm:ss.S")+" milliseconds. "); 

					if(acceptConfDtlsList != null && acceptConfDtlsList.size() > 0) {
						long loadStartTime = System.nanoTime();
						accptCnt = acceptConfDtlsList.size();
						saveBulkConfirmations();
						long loadEndTime = System.nanoTime();
						long loadDuration = (loadEndTime - loadStartTime) / 1000000;
						log.info("LOAD TIME IS "+DurationFormatUtils.formatDuration(loadDuration, "HH:mm:ss.S")+" milliseconds. "); 
					}

					//					if (rejCnt == 0) 
					//					{
					//						mdtOpsFileRegEntity.setStatus("LS");
					//						valBeanRemote.updateOpsFileReg(mdtOpsFileRegEntity);
					//					} 
					//					else 
					//					{
					//						mdtOpsFileRegEntity.setStatus("LE");
					//						valBeanRemote.updateOpsFileReg(mdtOpsFileRegEntity);
					//					}

					//Generate the MndtCount and Update the Pacs.002 Group Conf
					generateMndtCount(accptCnt, rejCnt);	

					//Set GrpStatus based on accepted and rejected cnt
					//					log.info("accptCnt: "+accptCnt);
					//					log.info("nrOfMndtsInFile: "+nrOfMndtsInFile);
					//					log.info("accptCnt: "+accptCnt);
					String grpStatus = null;
					if(accptCnt == nrOfMndtsInFile)
					{
						try {
							grpStatus =  propertyUtil.getPropValue("Pacs002Status.ACCP");
							casOpsFileRegEntity.setStatus("LS");
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					else
					{
						if(rejCnt == nrOfMndtsInFile)
						{
							try {
								grpStatus =  propertyUtil.getPropValue("Pacs002Status.RJCT");
								casOpsFileRegEntity.setStatus("LE");
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
						else
						{
							if((accptCnt > 0 && accptCnt != nrOfMndtsInFile) && (rejCnt > 0 && rejCnt != nrOfMndtsInFile))
							{
								try {
									grpStatus =  propertyUtil.getPropValue("Pacs002Status.PART");
									casOpsFileRegEntity.setStatus("LE");
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						}
					}
					valBeanRemote.updateOpsFileReg(casOpsFileRegEntity);

					//Update Conf Hdrs
					BigDecimal hdrSeqNo = ac_Pacs002_Validation_ST.hdrSystemSeqNo;
					CasOpsStatusHdrsEntity casOpsStatusHdrsEntity = (CasOpsStatusHdrsEntity) beanRemote.retrieveStatusHdrsBySeqNo(hdrSeqNo);
					casOpsStatusHdrsEntity.setGroupStatus(grpStatus);
					try {
						casOpsStatusHdrsEntity.setProcessStatus( propertyUtil.getPropValue("ProcStatus.ReportToBeProd"));
					} catch (IOException e) {
						e.printStackTrace();
					}

					boolean updateStatusdHdrs = beanRemote.updateOpsStatusHdrs(
                        casOpsStatusHdrsEntity);
					log.info("|"+fileName+" COMPLETED| TOT_TXNS "+nrOfMndtsInFile+" | ACCEPTED "+accptCnt+" | REJECTED "+rejCnt+"|");

				} //if transactionsList is null
				else
				{
					//					loadPacs002(null, null);
					//Update Conf Hdrs
					BigDecimal hdrSeqNo = ac_Pacs002_Validation_ST.hdrSystemSeqNo;
					CasOpsStatusHdrsEntity casOpsStatusHdrsEntity = (CasOpsStatusHdrsEntity) beanRemote.retrieveStatusHdrsBySeqNo(hdrSeqNo);
					try {
						casOpsStatusHdrsEntity.setProcessStatus( propertyUtil.getPropValue("ProcStatus.ReportToBeProd"));
					} catch (IOException e) {
						e.printStackTrace();
					}

					boolean updateStatusdHdrs = beanRemote.updateOpsStatusHdrs(
                        casOpsStatusHdrsEntity);
				}
			}
			else
			{
				casOpsFileRegEntity.setStatus("FG");
				valBeanRemote.updateOpsFileReg(casOpsFileRegEntity);
			}

			//Reset for next file process
			hdrSystemSeqNo = BigDecimal.ZERO;
			grpHdrSeverity = 0;
			mandateSeverity = 0;
		}//END OF IF UNMARSHALL
		else
		{
			if(casOpsFileRegEntity != null)
			{
				casOpsFileRegEntity.setStatus("FG");
				valBeanRemote.updateOpsFileReg(casOpsFileRegEntity);
			}
		}
		endTime = System.nanoTime();
		duration = (endTime - startTime) / 1000000;
		log.info("|TOTAL EXECUTION TIME FOR "+fileName+" IS "+DurationFormatUtils.formatDuration(duration, "HH:mm:ss.S")+" milliseconds |");
	}

	public void loadConfHdr() {
		hdrSystemSeqNo = BigDecimal.ZERO;
		confHdrEntity.setSystemSeqNo(new BigDecimal(123));
		confHdrEntity.setHdrMsgId(document.getFIToFIPmtStsRpt().getGrpHdr().getMsgId().trim());
		confHdrEntity.setCreateDateTime(getCovertDateTime(document.getFIToFIPmtStsRpt().getGrpHdr().getCreDtTm()));
		confHdrEntity.setInstgAgt(document.getFIToFIPmtStsRpt().getGrpHdr().getInstgAgt().getFinInstnId().getClrSysMmbId().getMmbId().trim());
		confHdrEntity.setInstdAgt(document.getFIToFIPmtStsRpt().getGrpHdr().getInstdAgt().getFinInstnId().getClrSysMmbId().getMmbId().trim());
		confHdrEntity.setOrgnlMsgId(document.getFIToFIPmtStsRpt().getOrgnlGrpInfAndSts().getOrgnlMsgId().trim());
		confHdrEntity.setOrgnlMsgName(document.getFIToFIPmtStsRpt().getOrgnlGrpInfAndSts().getOrgnlMsgNmId().trim());
		confHdrEntity.setOrgnlCreateDateTime(getCovertDateTime(document.getFIToFIPmtStsRpt().getOrgnlGrpInfAndSts().getOrgnlCreDtTm()));
		confHdrEntity.setProcessStatus("C");
		confHdrEntity.setGroupStatus(document.getFIToFIPmtStsRpt().getOrgnlGrpInfAndSts().getGrpSts().value().trim());

		if(document.getFIToFIPmtStsRpt() != null && document.getFIToFIPmtStsRpt().getOrgnlGrpInfAndSts() != null && document.getFIToFIPmtStsRpt().getOrgnlGrpInfAndSts().getStsRsnInf().size() > 0)
		{
			if(document.getFIToFIPmtStsRpt().getOrgnlGrpInfAndSts().getStsRsnInf().get(0) != null && document.getFIToFIPmtStsRpt().getOrgnlGrpInfAndSts().getStsRsnInf().get(0).getRsn() != null 
					&& document.getFIToFIPmtStsRpt().getOrgnlGrpInfAndSts().getStsRsnInf().get(0).getRsn().getPrtry() != null)
			{
				confHdrEntity.setGroupError(document.getFIToFIPmtStsRpt().getOrgnlGrpInfAndSts().getStsRsnInf().get(0).getRsn().getPrtry().trim());
			}
		}
		confHdrEntity.setService("ST201");

		hdrSystemSeqNo = valBeanRemote.saveOpsConfHdrs(confHdrEntity);
	}

	public void saveBulkConfirmations() {	
		long loadStart, loadEnd, loadDur;
		loadStart = System.nanoTime();
		fileProcessBeanRemote.openHibernateSession();

		try
		{
			log.info("==================== BULK INSERT ST201 TXNS ====================");
			fileProcessBeanRemote.bulkSaveMandates(acceptConfDtlsList);
			loadEnd = System.nanoTime();
			loadDur = (loadEnd - loadStart) / 1000000;
			log.info("|BULK SAVE LOAD "+fileName+" IS "+DurationFormatUtils.formatDuration(loadDur, "HH:mm:ss.S")+" milliseconds |");
			loadStart = System.nanoTime();
			log.info("==================== BULK UPDATE MATCHED TXNS ====================");
			//			fileProcessBeanRemote.bulkUpdateMandates(origTxnsList);
			bulkUpdateViaSQL();
			loadEnd = System.nanoTime();
			loadDur = (loadEnd - loadStart) / 1000000;
			log.info("|BULK UPDATE "+fileName+" IS "+DurationFormatUtils.formatDuration(loadDur, "HH:mm:ss.S")+" milliseconds |");
		}
		catch (Exception e) 
		{
			log.error("Error on saveBulkConfirmations: " + e.getMessage());
			e.printStackTrace();
		}
		fileProcessBeanRemote.closeHibernateSession();
	}

	public void bulkUpdateViaSQL() {
		int targetSize = 1000;
		List<List<String>> partitionList = null;


		if(accpMRTIList != null && accpMRTIList.size() > 0) {
			//Partition List
			partitionList = ListUtils.partition(accpMRTIList, targetSize);
//			log.info("accp partition List size: "+partitionList.size());

			for (List<String> accpList : partitionList) {
				String joinResult = null;
				joinResult = StringUtils.join(accpList,",");
				String sqlQuery = new String("UPDATE CAMOWNER.CAS_OPS_CESS_ASSIGN_TXNS SET PROCESS_STATUS = '9' WHERE MANDATE_REQ_TRAN_ID IN ("+joinResult+")");
				fileProcessBeanRemote.bulkUpdateBySQL(sqlQuery);
			}	        
		}

		if(rjctMRTIList != null && rjctMRTIList.size() > 0) {
			//Partition List
			partitionList = ListUtils.partition(rjctMRTIList, targetSize);
//			log.info("rjct partition List size: "+partitionList.size());

			for (List<String> rjctList : partitionList) {
				String joinResult = null;
				joinResult = StringUtils.join(rjctList,",");
				String sqlQuery = new String("UPDATE CAMOWNER.CAS_OPS_CESS_ASSIGN_TXNS SET PROCESS_STATUS = 'I' WHERE MANDATE_REQ_TRAN_ID IN ("+joinResult+")");
				fileProcessBeanRemote.bulkUpdateBySQL(sqlQuery);
			}	        
		}
	}

	public void mapConfDetails(PaymentTransaction33 transaction, CasOpsCessionAssignEntity originalMandate){
		if(transaction != null)
		{
			confDetEntity=new CasOpsConfDetailsEntity();

			confDetEntity.setSystemSeqNo(new BigDecimal(123));
			confDetEntity.setConfHdrSeqNo(hdrSystemSeqNo);
			if(transaction.getStsRsnInf() != null && transaction.getStsRsnInf().get(0) != null && transaction.getStsRsnInf().get(0).getRsn() != null && transaction.getStsRsnInf().get(0).getRsn().getPrtry() != null)
				confDetEntity.setErrorCode(transaction.getStsRsnInf().get(0).getRsn().getPrtry());

			confDetEntity.setTxnId(transaction.getOrgnlTxId().trim());
			confDetEntity.setTxnStatus(transaction.getTxSts().value().trim());
			confDetEntity.setErrorType("TXN");
			String bankCode = transaction.getOrgnlTxId().substring(0,4).trim();
			confDetEntity.setInstId("21"+bankCode);
			confDetEntity.setProcessStatus("3");
			confDetEntity.setExtractService("ST203");
			confDetEntity.setOrgnlMsgType(document.getFIToFIPmtStsRpt().getOrgnlGrpInfAndSts().getOrgnlMsgNmId().trim().toLowerCase());
			confDetEntity.setLocalInstrCd(originalMandate.getLocalInstrCd());
			confDetEntity.setMsgId(document.getFIToFIPmtStsRpt().getGrpHdr().getMsgId());
			confDetEntity.setInFileName(fileName.substring(0,37).trim());

			//Change status for matched transactions ONLY 
			if(originalMandate.getProcessStatus().equalsIgnoreCase("4"))
			{

				if(transaction.getTxSts().value().equalsIgnoreCase("ACCP"))
				{
					accpMRTIList.add("'"+originalMandate.getCasOpsCessionAssignEntityPK().getMandateReqTranId()+"'");
				}
				else
				{
					rjctMRTIList.add("'"+originalMandate.getCasOpsCessionAssignEntityPK().getMandateReqTranId()+"'");
				}
			}
		}
		else
		{
			confDetEntity.setProcessStatus("E");
			confDetEntity.setErrorType("HDR");
			confDetEntity.setTxnStatus("RJCT");
		}

		acceptConfDtlsList.add(confDetEntity);
	}

	private void generateMndtCount(int acceptCount, int rejectedCount) 
	{
		todaysDate= new Date();

		boolean saved = false;
		int nrOfFile =1;
		int nrOfMsgsInFile = transactionList.size();

		log.debug("# of mandates submitted ******--->" + transactionList.size());
		log.debug("acceptedCount******--->" + acceptCount);
		log.debug("rejectedCount******--->" + rejectedCount);


		CasOpsMndtCountEntity mdtOpsMndtCountEntity = new CasOpsMndtCountEntity();
		CasOpsMndtCountPK mdtOpsMndtCountPk = new CasOpsMndtCountPK();

		if(document!= null && document.getFIToFIPmtStsRpt()!=null && document.getFIToFIPmtStsRpt().getGrpHdr() != null && document.getFIToFIPmtStsRpt().getGrpHdr().getMsgId()!=null)
			mdtOpsMndtCountPk.setMsgId(document.getFIToFIPmtStsRpt().getGrpHdr().getMsgId());
		mdtOpsMndtCountPk.setServiceId("ST201");
		if(document!= null && document.getFIToFIPmtStsRpt()!=null && document.getFIToFIPmtStsRpt().getGrpHdr() != null && document.getFIToFIPmtStsRpt().getGrpHdr().getMsgId()!=null)
			mdtOpsMndtCountPk.setInstId(document.getFIToFIPmtStsRpt().getGrpHdr().getMsgId().toString().substring(12, 18));
		mdtOpsMndtCountEntity.setCasOpsMndtCountPK(mdtOpsMndtCountPk);
		mdtOpsMndtCountEntity.setNrOfMsgs(nrOfMsgsInFile);
		mdtOpsMndtCountEntity.setNrOfFiles(nrOfFile);
		mdtOpsMndtCountEntity.setIncoming("Y");
		mdtOpsMndtCountEntity.setProcessDate(todaysDate);
		mdtOpsMndtCountEntity.setOutgoing("N");
		mdtOpsMndtCountEntity.setNrMsgsAccepted(acceptCount);
		mdtOpsMndtCountEntity.setNrMsgsRejected(rejectedCount);
		mdtOpsMndtCountEntity.setNrMsgsExtracted(0);
		mdtOpsMndtCountEntity.setFileName(fileName.substring(0,37).trim());

		saved = valBeanRemote.saveMdtOpsMndtCount(mdtOpsMndtCountEntity);

		log.debug("WRITING mdtOpsMndtCountEntity IN THE AC_Pacs002 FileLoader"+mdtOpsMndtCountEntity);


		if (saved) {
			log.debug("MdtOpsCountTable has been updated");

		} else {
			log.debug("MdtOpsCountTable is not updated");

		}


	}

	public void generateConfErrorDetails(PaymentTransaction33 paymentTransaction33, String errorCode, CasOpsCessionAssignEntity originalMandate)
	{
		confDetEntity=new CasOpsConfDetailsEntity();

		confDetEntity.setSystemSeqNo(new BigDecimal(123));
		confDetEntity.setConfHdrSeqNo(hdrSystemSeqNo);
		if(errorCode != null)
			confDetEntity.setErrorCode(errorCode);

		if(paymentTransaction33 != null)
		{
			confDetEntity.setTxnId(paymentTransaction33.getOrgnlTxId().trim());
			log.debug("getTxSts**************:"+paymentTransaction33.getTxSts().value());
			confDetEntity.setTxnStatus(paymentTransaction33.getTxSts().value().trim());
			confDetEntity.setErrorType("TXN");

			String bankCode = paymentTransaction33.getOrgnlTxId().substring(0,4).trim();
			log.debug("BANK CODE--->"+bankCode);

			confDetEntity.setInstId("21"+bankCode);
			confDetEntity.setProcessStatus("3");
			confDetEntity.setExtractService("ST203");
			confDetEntity.setOrgnlMsgType(document.getFIToFIPmtStsRpt().getOrgnlGrpInfAndSts().getOrgnlMsgNmId().trim().toLowerCase());
			confDetEntity.setLocalInstrCd(originalMandate.getLocalInstrCd());
			//			log.info("LOCAL INSTRUMENT CODE--->"+originalMandate.getLocalInstrCd());

			//###########################################################################HERE					
			//Change status for matched transactions ONLY 
			if(originalMandate.getProcessStatus().equalsIgnoreCase("4"))
			{
				if(paymentTransaction33.getTxSts().value().equalsIgnoreCase("ACCP"))
				{
					originalMandate.setProcessStatus("9");
				}
				else
				{
					originalMandate.setProcessStatus("R");
				}
				result= fileProcessBeanRemote.updateMdtOpsMandateTxns(originalMandate);
			}
		}
		else
		{
			confDetEntity.setProcessStatus("E");
			confDetEntity.setErrorType("HDR");
			confDetEntity.setTxnStatus("RJCT");
		}

		confDetailsList.add(confDetEntity);
	}

	public boolean saveConfErrorDetails()
	{
		boolean generated = false;

		if(confDetailsList.size() > 0)
		{
			for (CasOpsConfDetailsEntity localEntity : confDetailsList) {
				localEntity.setConfHdrSeqNo(hdrSystemSeqNo);
			}
			generated=valBeanRemote.saveConfDetails(confDetailsList);
		}

		if(generated)
		{
			confDetailsList.clear();
		}
		return generated;
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
