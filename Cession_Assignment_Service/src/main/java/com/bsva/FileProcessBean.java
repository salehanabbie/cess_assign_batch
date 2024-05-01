package com.bsva;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import org.apache.log4j.Logger;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import com.bsva.beans.GenericDAO;
import com.bsva.entities.CasOpsCessionAssignEntity;
import com.bsva.entities.CasOpsStatusDetailsEntity;
import com.bsva.entities.CasOpsStatusHdrsEntity;
import com.bsva.entities.CasOpsFileSizeLimitEntity;
import com.bsva.interfaces.FileProcessBeanLocal;
import com.bsva.interfaces.FileProcessBeanRemote;
/**
 * @author SalehaR
 *
 */
@Stateless
@Remote(FileProcessBeanRemote.class)
public class FileProcessBean implements FileProcessBeanRemote, FileProcessBeanLocal {

	@EJB
	GenericDAO genericDAO;

	public static Logger log = Logger.getLogger(FileProcessBean.class);
	//TXN Status
	String readyForExtractStatus = "3";
	String extractStatus = "4";
	String reportToBeProdStatus = "6";
	String responseRecStatus ="9";
	String matchedStatus = "M";
	String rejectedStatus = "R";
	String reportProducedStatus = "7";
	String loadStatus = "L";
	//File Status
	String receviedFileStatus = "R";
	String validatingFileStatus = "V";


	public List<?> validateOriginalMsgId(String msgId)
	{
		log.info("msgId: "+msgId);
		List<CasOpsCessionAssignEntity> mdtTxnList = (List<CasOpsCessionAssignEntity>) genericDAO.findAllByNamedQuery("CasOpsCessionAssignEntity.findByMsgId", "msgId",msgId);
		log.info("mdtTxnList: "+mdtTxnList);
		return mdtTxnList;
	}

	public List<?> validateMndtReqTranIdUnique(String mrti)
	{
		List<CasOpsCessionAssignEntity> mandateTxnList = (List<CasOpsCessionAssignEntity>) genericDAO.findAllByNamedQuery("CasOpsCessionAssignEntity.findByMandateReqTranId", "mandateReqTranId",mrti);
		return mandateTxnList;
	}

	public Object matchPain012ToOrigMandate(String manReqTransId, String messageType)
	{
		CasOpsCessionAssignEntity casOpsCessionAssignEntity = new CasOpsCessionAssignEntity();

		try 
		{
			HashMap<String, Object> parameters = new HashMap<String, Object>();

			if(manReqTransId != null && !manReqTransId.isEmpty())
				parameters.put("casOpsCessionAssignEntityPK.mandateReqTranId", manReqTransId);
			parameters.put("serviceId", messageType);

			log.debug("---------------sparameters: ------------------"+ parameters.toString());
			//			2019/10/04-SalehaR-Remove ProcessStatus from check	
			//		    mdtAcOpsMandateTxnsEntity = (CasOpsCessionAssignEntity) genericDAO.findByCriteriaIN(CasOpsCessionAssignEntity.class, parameters, "processStatus",Arrays.asList(extractStatus, responseRecStatus, rejectedStatus, matchedStatus));
			casOpsCessionAssignEntity = (CasOpsCessionAssignEntity) genericDAO.findByCriteria(
					CasOpsCessionAssignEntity.class, parameters);
			log.debug("---------------CasOpsCessionAssignEntity after findByCriteria: ------------------"+
					casOpsCessionAssignEntity);
		} 
		catch (ObjectNotFoundException onfe) 
		{
			log.debug("No Object Exists on DB");
		} 
		catch (Exception e) 
		{
			log.error("Error on matchPain012ToOrigMandate: "+ e.getMessage());
			e.printStackTrace();
		}

		return casOpsCessionAssignEntity;
	}

	public Object matchPacs002ToOrigMandate(String mndtReqTranId, String messageType)
	{
		CasOpsCessionAssignEntity casOpsCessionAssignEntity = new CasOpsCessionAssignEntity();
		try 
		{
			log.debug("mndtReqTranId: "+mndtReqTranId);

			HashMap<String, Object> parameters = new HashMap<String, Object>();

			if(mndtReqTranId != null && !mndtReqTranId.isEmpty())
				parameters.put("casOpsCessionAssignEntityPK.mandateReqTranId", mndtReqTranId);

			parameters.put("serviceId", messageType);
			log.debug("---------------sparameters: ------------------"+ parameters.toString());
			casOpsCessionAssignEntity = (CasOpsCessionAssignEntity) genericDAO.findByCriteria(
					CasOpsCessionAssignEntity.class, parameters);
			log.debug("---------------casOpsCessionAssignEntity after findByCriteria: ------------------"+
					casOpsCessionAssignEntity);

			//			2019/10/04-SalehaR-Remove ProcessStatus from check		
			//			parameters.put("serviceId", messageType);
			//			parameters.put("processStatus",extractStatus);
			//			log.debug("---------------sparameters: ------------------"+ parameters.toString());
			//			mdtAcOpsMandateTxnsEntity = (CasOpsCessionAssignEntity) genericDAO.findByCriteria(CasOpsCessionAssignEntity.class, parameters);
			//			log.debug("---------------CasOpsCessionAssignEntity after findByCriteria: ------------------"+ mdtAcOpsMandateTxnsEntity);
			//
			//			if(mdtAcOpsMandateTxnsEntity == null)
			//			{
			//				log.debug("Arrays.asList(extractStatus, matchedStatus, rejectedStatus) --->"+Arrays.asList(extractStatus, matchedStatus, rejectedStatus));
			//				mdtAcOpsMandateTxnsEntity = (CasOpsCessionAssignEntity) genericDAO.findByCriteriaIN(CasOpsCessionAssignEntity.class, parameters, "processStatus",Arrays.asList(matchedStatus, rejectedStatus, responseRecStatus));
			//				log.debug("---------------CasOpsCessionAssignEntity after findByCriteriaIN: ------------------"+ mdtAcOpsMandateTxnsEntity);
			//			}		
		} 
		catch (ObjectNotFoundException onfe) 
		{
			log.debug("No Object Exists on DB");
		} 
		catch (Exception e) 
		{
			log.error("Error on matchPacs002ToOrigMandate: "+ e.getMessage());
			e.printStackTrace();
		}

		return casOpsCessionAssignEntity;
	}

	@Override
	public boolean createAcOpsMandateTxns(Object obj, String debtorBrNo, String crAbbShrtName,String mndtRefNo) 
	{
		boolean isLoaded = false;
		try {
			if (obj instanceof CasOpsCessionAssignEntity)
			{
				CasOpsCessionAssignEntity casOpsCessionAssignEntity = (CasOpsCessionAssignEntity) obj;
				String savedmsg = genericDAO.save(casOpsCessionAssignEntity);
				log.debug("savedmsg ----> " + savedmsg);

				if (savedmsg.equalsIgnoreCase("DUPL") || savedmsg.equalsIgnoreCase("ERROR")) {
					isLoaded = false;
					log.debug("Duplicated Detected");
					// Create Duplicate Error
					generateDuplicateError(
							casOpsCessionAssignEntity.getCasOpsCessionAssignEntityPK().getMsgId(),
							casOpsCessionAssignEntity.getCasOpsCessionAssignEntityPK().getMandateReqTranId(),
							debtorBrNo,crAbbShrtName, mndtRefNo);
				} else {
					isLoaded = true;
				}
			} else {
				log.error("Unable to convert type to CasOpsCessionAssignEntity.");
				isLoaded = false;
			}
		} catch (Exception e) {
			log.error("Error on createAcOpsMandateTxns: " + e.getMessage());
			e.printStackTrace();
			isLoaded = false;
		}
		log.debug("~~~~~~~~~~~~~isLoaded-------> " + isLoaded);

		return isLoaded;
	}

	public boolean generateDuplicateError(String msgId, String txnId, String debtorBrNo, String crAbbShrtName, String mndtRefNo)
	{
		boolean saved = false;
		//Retrieve Status Hdrs Record
		log.debug("generateDuplicateError.msgId ---->"+msgId);
		log.debug("generateDuplicateError.txnId ---->"+txnId);
		log.debug("generateDuplicateError.debtorBrNo ---->"+debtorBrNo);
		log.debug("generateDuplicateError.crAbbShrtName ---->"+crAbbShrtName);
		log.debug("generateDuplicateError.mndtRefNo ---->"+mndtRefNo);

		CasOpsStatusHdrsEntity casOpsStatusHdrsEntity = (CasOpsStatusHdrsEntity) genericDAO.findByNamedQuery("CasOpsStatusHdrsEntity.findByOrgnlMsgId","orgnlMsgId", msgId);
		log.debug("mdtAcOpsStatusHdrsEntity from Duplicate Error ---->"+ casOpsStatusHdrsEntity);

		if(casOpsStatusHdrsEntity != null)
		{
			CasOpsStatusDetailsEntity opsStatusDetailsEntity=new CasOpsStatusDetailsEntity();

			opsStatusDetailsEntity.setSystemSeqNo(new BigDecimal(123));
			opsStatusDetailsEntity.setStatusHdrSeqNo(casOpsStatusHdrsEntity.getSystemSeqNo());
			opsStatusDetailsEntity.setErrorCode("902205");
			opsStatusDetailsEntity.setTxnId(txnId);
			opsStatusDetailsEntity.setTxnStatus("RJCT");
			opsStatusDetailsEntity.setErrorType("TXN");

			if(debtorBrNo != null)
				opsStatusDetailsEntity.setDebtorBranchNo(debtorBrNo);

			if(crAbbShrtName != null)
				opsStatusDetailsEntity.setCrAbbShortName(crAbbShrtName);

			if(mndtRefNo != null)
				opsStatusDetailsEntity.setMandateRefNumber(mndtRefNo);

			try 
			{
				genericDAO.save(opsStatusDetailsEntity);
				saved = true;
			} 
			catch (Exception e) 
			{
				log.error("Error on generateDuplicateError: " + e.getMessage());
				e.printStackTrace();
				saved = false;
			}
		}
		return saved;
	}

	public List<?> retrieveMandatesForExtract(boolean debtMemType, String memberId, String serviceId, String procStatus) {
		List<CasOpsCessionAssignEntity> extMandateList = new ArrayList<CasOpsCessionAssignEntity>();

		try 
		{
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("processStatus", procStatus);
			parameters.put("serviceId", serviceId);
			if(debtMemType) {
				parameters.put("debtorBank", memberId);
			} else {
				parameters.put("creditorBank", memberId);
			}
			log.debug("---------------sparameters: ------------------"+ parameters.toString());
			extMandateList = (List<CasOpsCessionAssignEntity>) genericDAO.findAllByCriteria(
					CasOpsCessionAssignEntity.class, parameters);
		} 
		catch (ObjectNotFoundException onfe) 
		{
			log.debug("No Object Exists on DB");
		} 
		catch (Exception e) {
			log.error("<FPB> Error on retrieveMandatesForExtract: " + e.getMessage());
			e.printStackTrace();
		}
		return extMandateList;
	}

	@Override
	public Boolean updateMdtOpsMandateTxns(Object obj) 
	{	
		Boolean updated = false;
		try {
			if (obj instanceof CasOpsCessionAssignEntity) {
				CasOpsCessionAssignEntity casOpsCessionAssignEntity = (CasOpsCessionAssignEntity) obj;

				genericDAO.saveOrUpdate(casOpsCessionAssignEntity);
				updated = true;
			} else {
				log.error("Unable to convert type to CasOpsCessionAssignEntity.");
				updated = false;
			}
		} catch (Exception e) {
			log.error("Error on updateMdtOpsMandateTxns: " + e.getMessage());
			e.printStackTrace();
			updated = false;
		}
		return updated;
	}

	public boolean archiveMandateTxnsBySQL(String archiveType, String expiredDate, String archDate)
	{
		log.info("Archive Type in ServiceBean ==> "+archiveType);
		boolean grpHdrBool = false, mandateBool = false;

		log.info("===============ARCHIVING "+archiveType+" GROUP HEADER===============");
		StringBuffer sbGrpHdr = new StringBuffer();
		//GROUP HEADER
		sbGrpHdr.append("INSERT INTO CAMOWNER.CAS_ARC_GRP_HDR ");
		sbGrpHdr.append("(MSG_ID ,CREATE_DATE_TIME ,AUTH_CODE ,CREATED_BY ,ARCHIVE_DATE) ");
		sbGrpHdr.append("SELECT distinct nvl(b.MSG_ID,'NF') as msgid ,b.CREATE_DATE_TIME ,b.AUTH_CODE ,b.CREATED_BY ,TO_DATE('"+archDate+"','YYYY-MM-DD') ");
		sbGrpHdr.append("FROM CAMOWNER.CAS_OPS_CESS_ASSIGN_TXNS a ");
		sbGrpHdr.append("left join CAMOWNER.CAS_OPS_GRP_HDR b on a.msg_id = b.msg_id ");

		switch(archiveType)
		{
		case "MATCH":  sbGrpHdr.append("WHERE a.PROCESS_STATUS IN ('M','R') and nvl(b.MSG_ID,'NF') <> 'NF' ");
		break;
		case "ACCEPT": sbGrpHdr.append("WHERE a.PROCESS_STATUS = '4' AND a.SERVICE_ID = 'RCAIN' ");	
		break;
		case "EXPIRE": sbGrpHdr.append("WHERE a.PROCESS_STATUS IN ('4','9') and nvl(b.MSG_ID,'NF') <> 'NF' AND TRUNC(a.CREATED_DATE) = TO_DATE('"+expiredDate+"','YYYY-MM-DD') "); 
		break;
		}

		try
		{
			String gpHdrSql = sbGrpHdr.toString();
			log.debug("gpHdrSql: " + gpHdrSql);
			genericDAO.executeNativeSQL(gpHdrSql);
			grpHdrBool = true;
		}
		catch(Exception ex)
		{
			log.error("Error on Matched CASA Archive--Group Hdr:- "+ex.getMessage());
			ex.printStackTrace();
			grpHdrBool = false;
		}

		//CESSION ASSIGNMENT TXNS
		log.info("===============ARCHIVING "+archiveType+" MANDATE===============");
		StringBuffer sbMandate = new StringBuffer();

		sbMandate.append("INSERT INTO CAMOWNER.CAS_ARC_CESS_ASSIGN_TXNS ( ");
		sbMandate.append("MSG_ID ,MANDATE_REQ_TRAN_ID ,CREDITOR_BANK ,DEBTOR_BANK ,SERVICE_ID ,PROCESS_STATUS ,IN_FILE_NAME ,IN_FILE_DATE ,EXTRACT_MSG_ID ,EXTRACT_FILE_NAME ,INIT_PARTY ,MANDATE_ID "); 
		sbMandate.append(",CONTRACT_REF ,SERVICE_LEVEL ,LOCAL_INSTR_CD ,SEQUENCE_TYPE ,FREQUENCY ,FROM_DATE ,FIRST_COLL_DATE ,COLL_AMOUNT_CURR ,COLL_AMOUNT ,MAX_AMOUNT_CURR ,MAX_AMOUNT ,CRED_SCHEME_ID ");
		sbMandate.append(",CREDITOR_NAME ,CRED_PHONE_NR ,CRED_EMAIL_ADDR ,CRED_ACC_NUM ,CRED_BRANCH_NR ,ULT_CRED_NAME ,CRED_ABB_SHORT_NAME ,DEBTOR_NAME ,DEBTOR_ID ,DEBT_PHONE_NR ,DEBT_EMAIL_ADDR ");
		sbMandate.append(",DEBT_ACC_NUM ,DEBT_ACC_TYPE ,DEBT_BRANCH_NR ,ULT_DEBT_NAME ,AUTH_TYPE ,COLLECTION_DAY ,DATE_ADJ_RULE_IND ,ADJ_CATEGORY ,ADJ_RATE ,ADJ_AMOUNT_CURR ,ADJ_AMOUNT ,FIRST_COLL_AMT_CURR ");
		sbMandate.append(",FIRST_COLL_AMT ,DEBIT_VALUE_TYPE ,ACCEPTED_IND ,REJECT_REASON_CODE ,AUTH_STATUS_IND ,AUTH_CHANNEL ,MANDATE_REF_NR ,MANDATE_AUTH_DATE ,AMEND_REASON ,ORIG_MANDATE_ID ");
		sbMandate.append(",ORIG_CONTRACT_REF ,ORIG_CRED_NAME ,ORIG_MAND_REQ_TRAN_ID ,ORIG_DEBT_NAME ,ORIG_DEBT_BRANCH ,CANCEL_REASON ,CREATED_BY ,CREATED_DATE ,MODIFIED_BY ,MODIFIED_DATE ,ARCHIVE_DATE,MAC_CODE) ");
		if(archiveType.equalsIgnoreCase("EXPIRE")) {
			//Expire Txn-Change Process Status to 8
			sbMandate.append("SELECT MSG_ID ,MANDATE_REQ_TRAN_ID ,CREDITOR_BANK ,DEBTOR_BANK ,SERVICE_ID ,'8' ,IN_FILE_NAME ,IN_FILE_DATE ,EXTRACT_MSG_ID ,EXTRACT_FILE_NAME ,INIT_PARTY ");
		}else{
			sbMandate.append("SELECT MSG_ID ,MANDATE_REQ_TRAN_ID ,CREDITOR_BANK ,DEBTOR_BANK ,SERVICE_ID ,PROCESS_STATUS ,IN_FILE_NAME ,IN_FILE_DATE ,EXTRACT_MSG_ID ,EXTRACT_FILE_NAME ,INIT_PARTY ");	
		}
		sbMandate.append(",MANDATE_ID ,CONTRACT_REF ,SERVICE_LEVEL ,LOCAL_INSTR_CD ,SEQUENCE_TYPE ,FREQUENCY ,FROM_DATE ,FIRST_COLL_DATE ,COLL_AMOUNT_CURR ,COLL_AMOUNT ,MAX_AMOUNT_CURR ,MAX_AMOUNT ");
		sbMandate.append(",CRED_SCHEME_ID ,CREDITOR_NAME ,CRED_PHONE_NR ,CRED_EMAIL_ADDR ,CRED_ACC_NUM ,CRED_BRANCH_NR ,ULT_CRED_NAME ,CRED_ABB_SHORT_NAME ,DEBTOR_NAME ,DEBTOR_ID ,DEBT_PHONE_NR ,DEBT_EMAIL_ADDR ");
		sbMandate.append(",DEBT_ACC_NUM ,DEBT_ACC_TYPE ,DEBT_BRANCH_NR ,ULT_DEBT_NAME ,AUTH_TYPE ,COLLECTION_DAY ,DATE_ADJ_RULE_IND ,ADJ_CATEGORY ,ADJ_RATE ,ADJ_AMOUNT_CURR ,ADJ_AMOUNT ,FIRST_COLL_AMT_CURR ");
		sbMandate.append(",FIRST_COLL_AMT ,DEBIT_VALUE_TYPE ,ACCEPTED_IND ,REJECT_REASON_CODE ,AUTH_STATUS_IND ,AUTH_CHANNEL ,MANDATE_REF_NR ,MANDATE_AUTH_DATE ,AMEND_REASON ,ORIG_MANDATE_ID ,ORIG_CONTRACT_REF "); 
		sbMandate.append(",ORIG_CRED_NAME ,ORIG_MAND_REQ_TRAN_ID ,ORIG_DEBT_NAME ,ORIG_DEBT_BRANCH ,CANCEL_REASON ,CREATED_BY ,CREATED_DATE ,MODIFIED_BY ,MODIFIED_DATE ,TO_DATE('"+archDate+"','YYYY-MM-DD'),MAC_CODE ");
		sbMandate.append("FROM CAMOWNER.CAS_OPS_CESS_ASSIGN_TXNS ");

		switch(archiveType)
		{
		case "MATCH"	: sbMandate.append("WHERE PROCESS_STATUS IN ('M','R') ");
		break;
		case "ACCEPT"	: sbMandate.append("WHERE PROCESS_STATUS = '4' AND SERVICE_ID = 'RCAIN' ");
		break;
		case "EXPIRE"	: sbMandate.append("WHERE PROCESS_STATUS IN ('4','9') AND TRUNC(CREATED_DATE) = TO_DATE('"+expiredDate+"','YYYY-MM-DD') ");
		break;
		}

		try
		{
			String mandateSql = sbMandate.toString();
			log.info("mandateSql: " + mandateSql);
			genericDAO.executeNativeSQL(mandateSql);
			mandateBool = true;
		}
		catch(Exception ex)
		{
			log.error("ST_Error on Matched CASA Archive--Mandate Mess:- "+ex.getMessage());
			ex.printStackTrace();
			mandateBool = false;
		}

		if(grpHdrBool && mandateBool)
			return true;
		else
			return false;
	}

	public boolean deleteMatchedMandateTxnsBySQL(String archiveType, String expiredDate)
	{
		boolean grpHdrBool = false, mndtMsgBool= false;

		log.info("===============DELETING "+archiveType+" CASA TXNS===============");
		StringBuffer sbGrpHdr = new StringBuffer();
		sbGrpHdr.append("delete from CAMOWNER.CAS_OPS_GRP_HDR b ");
		sbGrpHdr.append("where (b.msg_id) IN ");
		sbGrpHdr.append("(select a.msg_id from CAMOWNER.CAS_OPS_CESS_ASSIGN_TXNSa ");
		switch(archiveType)
		{
		case "MATCH":  sbGrpHdr.append("WHERE a.PROCESS_STATUS IN ('M','R') and nvl(b.MSG_ID,'NF') <> 'NF') ");
		break;
		case "ACCEPT": sbGrpHdr.append("WHERE a.PROCESS_STATUS = '4' AND a.SERVICE_ID = 'RCAIN') ");	
		break;
		case "EXPIRE": sbGrpHdr.append("WHERE a.PROCESS_STATUS IN ('4','9') and nvl(b.MSG_ID,'NF') <> 'NF' AND TRUNC(a.CREATED_DATE) = TO_DATE('"+expiredDate+"','YYYY-MM-DD')) "); 
		break;
		}

		try
		{
			String grpHdrSql = sbGrpHdr.toString();
			log.info("delete grpHdrSql: " + grpHdrSql);
			genericDAO.executeNativeSQL(grpHdrSql);
			grpHdrBool = true;
		}
		catch(Exception ex)
		{
			log.error("Error on Delete CASA Txns--Group Header:- "+ex.getMessage());
			ex.printStackTrace();
			grpHdrBool = false;
		}

		StringBuffer sbMndMsg = new StringBuffer();
		sbMndMsg.append("delete from CAMOWNER.CAS_OPS_CESS_ASSIGN_TXNS ");
		switch(archiveType)
		{
		case "MATCH":  sbMndMsg.append("WHERE PROCESS_STATUS IN ('M','R') ");
		break;
		case "ACCEPT": sbMndMsg.append("WHERE PROCESS_STATUS = '4' AND SERVICE_ID = 'RCAIN' ");	
		break;
		case "EXPIRE": sbMndMsg.append("WHERE PROCESS_STATUS IN ('4','9') AND TRUNC(CREATED_DATE) = TO_DATE('"+expiredDate+"','YYYY-MM-DD') "); 
		break;
		}
		try
		{
			String mndMsgSql = sbMndMsg.toString();
			log.info("delete MndtTxns: " + mndMsgSql);
			genericDAO.executeNativeSQL(mndMsgSql);
			mndtMsgBool = true;
		}
		catch(Exception ex)
		{
			log.error("Error on Delete CASA TXNS:- "+ex.getMessage());
			ex.printStackTrace();
			mndtMsgBool = false;
		}

		if(grpHdrBool && mndtMsgBool)
			return true;
		else
			return false;
	}

	public boolean archiveLeftOverTxnsBySQL(String expiredDate, String archDate)
	{
		boolean leftOverBool = false;

		log.info("===============ARCHIVING GROUP HEADER EXCESS TXNS===============");
		StringBuffer leftGHMsg = new StringBuffer();
		leftGHMsg.append("INSERT INTO CAMOWNER.CAS_ARC_GRP_HDR ");
		leftGHMsg.append("(MSG_ID ,CREATE_DATE_TIME ,AUTH_CODE ,CREATED_BY ,ARCHIVE_DATE) ");
		leftGHMsg.append("SELECT MSG_ID ,CREATE_DATE_TIME ,AUTH_CODE ,CREATED_BY ,TO_DATE('"+archDate+"','YYYY-MM-DD') ");
		leftGHMsg.append("FROM CAMOWNER.CAS_OPS_GRP_HDR ");
		leftGHMsg.append("WHERE CREATE_DATE_TIME <= TO_DATE('"+expiredDate+"','YYYY-MM-DD') ");

		try
		{
			String gHleftSQL = leftGHMsg.toString();
			log.debug("gHleftSQL: " + gHleftSQL);
			genericDAO.executeNativeSQL(gHleftSQL);
		}
		catch(Exception ex)
		{
			log.error("Error on Archive Group Hdr Excess:- "+ex.getMessage());
			ex.printStackTrace();
		}

		//Mandate Message
		log.info("===============ARCHIVING CASA EXCESS TXNS===============");
		StringBuffer sbMandate = new StringBuffer();
		sbMandate.append("INSERT INTO CAMOWNER.CAS_ARC_CESS_ASSIGN_TXNS ( ");
		sbMandate.append("MSG_ID ,MANDATE_REQ_TRAN_ID ,CREDITOR_BANK ,DEBTOR_BANK ,SERVICE_ID ,PROCESS_STATUS ,IN_FILE_NAME ,IN_FILE_DATE ,EXTRACT_MSG_ID ,EXTRACT_FILE_NAME ,INIT_PARTY ,MANDATE_ID "); 
		sbMandate.append(",CONTRACT_REF ,SERVICE_LEVEL ,LOCAL_INSTR_CD ,SEQUENCE_TYPE ,FREQUENCY ,FROM_DATE ,FIRST_COLL_DATE ,COLL_AMOUNT_CURR ,COLL_AMOUNT ,MAX_AMOUNT_CURR ,MAX_AMOUNT ,CRED_SCHEME_ID ");
		sbMandate.append(",CREDITOR_NAME ,CRED_PHONE_NR ,CRED_EMAIL_ADDR ,CRED_ACC_NUM ,CRED_BRANCH_NR ,ULT_CRED_NAME ,CRED_ABB_SHORT_NAME ,DEBTOR_NAME ,DEBTOR_ID ,DEBT_PHONE_NR ,DEBT_EMAIL_ADDR ");
		sbMandate.append(",DEBT_ACC_NUM ,DEBT_ACC_TYPE ,DEBT_BRANCH_NR ,ULT_DEBT_NAME ,AUTH_TYPE ,COLLECTION_DAY ,DATE_ADJ_RULE_IND ,ADJ_CATEGORY ,ADJ_RATE ,ADJ_AMOUNT_CURR ,ADJ_AMOUNT ,FIRST_COLL_AMT_CURR ");
		sbMandate.append(",FIRST_COLL_AMT ,DEBIT_VALUE_TYPE ,ACCEPTED_IND ,REJECT_REASON_CODE ,AUTH_STATUS_IND ,AUTH_CHANNEL ,MANDATE_REF_NR ,MANDATE_AUTH_DATE ,AMEND_REASON ,ORIG_MANDATE_ID ");
		sbMandate.append(",ORIG_CONTRACT_REF ,ORIG_CRED_NAME ,ORIG_MAND_REQ_TRAN_ID ,ORIG_DEBT_NAME ,ORIG_DEBT_BRANCH ,CANCEL_REASON ,CREATED_BY ,CREATED_DATE ,MODIFIED_BY ,MODIFIED_DATE ,ARCHIVE_DATE) ");
		sbMandate.append("SELECT MSG_ID ,MANDATE_REQ_TRAN_ID ,CREDITOR_BANK ,DEBTOR_BANK ,SERVICE_ID ,PROCESS_STATUS ,IN_FILE_NAME ,IN_FILE_DATE ,EXTRACT_MSG_ID ,EXTRACT_FILE_NAME ,INIT_PARTY ");	
		sbMandate.append(",MANDATE_ID ,CONTRACT_REF ,SERVICE_LEVEL ,LOCAL_INSTR_CD ,SEQUENCE_TYPE ,FREQUENCY ,FROM_DATE ,FIRST_COLL_DATE ,COLL_AMOUNT_CURR ,COLL_AMOUNT ,MAX_AMOUNT_CURR ,MAX_AMOUNT ");
		sbMandate.append(",CRED_SCHEME_ID ,CREDITOR_NAME ,CRED_PHONE_NR ,CRED_EMAIL_ADDR ,CRED_ACC_NUM ,CRED_BRANCH_NR ,ULT_CRED_NAME ,CRED_ABB_SHORT_NAME ,DEBTOR_NAME ,DEBTOR_ID ,DEBT_PHONE_NR ,DEBT_EMAIL_ADDR ");
		sbMandate.append(",DEBT_ACC_NUM ,DEBT_ACC_TYPE ,DEBT_BRANCH_NR ,ULT_DEBT_NAME ,AUTH_TYPE ,COLLECTION_DAY ,DATE_ADJ_RULE_IND ,ADJ_CATEGORY ,ADJ_RATE ,ADJ_AMOUNT_CURR ,ADJ_AMOUNT ,FIRST_COLL_AMT_CURR ");
		sbMandate.append(",FIRST_COLL_AMT ,DEBIT_VALUE_TYPE ,ACCEPTED_IND ,REJECT_REASON_CODE ,AUTH_STATUS_IND ,AUTH_CHANNEL ,MANDATE_REF_NR ,MANDATE_AUTH_DATE ,AMEND_REASON ,ORIG_MANDATE_ID ,ORIG_CONTRACT_REF "); 
		sbMandate.append(",ORIG_CRED_NAME ,ORIG_MAND_REQ_TRAN_ID ,ORIG_DEBT_NAME ,ORIG_DEBT_BRANCH ,CANCEL_REASON ,CREATED_BY ,CREATED_DATE ,MODIFIED_BY ,MODIFIED_DATE ,TO_DATE('"+archDate+"','YYYY-MM-DD') ");
		sbMandate.append("FROM CAMOWNER.CAS_OPS_CESS_ASSIGN_TXNS ");
		sbMandate.append("WHERE CREATED_DATE <= TO_DATE('"+expiredDate+"','YYYY-MM-DD') ");

		try
		{
			String mandLeftSQL = sbMandate.toString();
			log.info("mandLeftSQL: " + mandLeftSQL);
			genericDAO.executeNativeSQL(mandLeftSQL);
		}
		catch(Exception ex)
		{
			log.error("Error on Archive CASA LeftOver:- "+ex.getMessage());
			ex.printStackTrace();
		}

		return true;
	}

	public boolean deleteLeftOverTxnsBySQL(String expiredDate)
	{
		try
		{
			log.info("===============DELETING GROUP HEADER EXCESS TXNS===============");
			String ghLeftDel = "DELETE FROM  CAMOWNER.CAS_OPS_GRP_HDR WHERE CREATE_DATE_TIME <= TO_DATE('"+expiredDate+"','YYYY-MM-DD') ";
			log.debug("ghLeftDel: " + ghLeftDel);
			genericDAO.executeNativeSQL(ghLeftDel);

			log.info("===============DELETING CASA EXCESS TXNS===============");
			String mndtLeftDel = "DELETE FROM CAMOWNER.CAS_OPS_CESS_ASSIGN_TXNS WHERE CREATED_DATE <= TO_DATE('"+expiredDate+"','YYYY-MM-DD') ";
			log.debug("mndtLeftDel: " + mndtLeftDel);
			genericDAO.executeNativeSQL(mndtLeftDel);

			return true;
		}
		catch(Exception ex)
		{
			log.error("Error on Deleting Excess Txns:- "+ex.getMessage());
			ex.printStackTrace();
			return false;
		}
	}

	public boolean cleanUpArchivedTxnsBySQL(String expiredDate)
	{
		try
		{
			log.info("===============CLEANING UP ARC GROUP HEADER TXNS===============");
			String ghLeftDel = "DELETE FROM  CAMOWNER.CAS_ARC_GRP_HDR WHERE ARCHIVE_DATE <= TO_DATE('"+expiredDate+"','YYYY-MM-DD') ";
			log.debug("ghLeftDel: " + ghLeftDel);
			genericDAO.executeNativeSQL(ghLeftDel);

			log.info("===============CLEANING UP ARC MANDATES TXNS===============");
			String mndtLeftDel = "DELETE FROM CAMOWNER.CAS_ARC_CESS_ASSIGN_TXNS WHERE ARCHIVE_DATE <= TO_DATE('"+expiredDate+"','YYYY-MM-DD') ";
			log.debug("mndtLeftDel: " + mndtLeftDel);
			genericDAO.executeNativeSQL(mndtLeftDel);

			return true;
		}
		catch(Exception ex)
		{
			log.error("Error on clean up archive Txns:- "+ex.getMessage());
			ex.printStackTrace();
			return false;
		}
	}

	public boolean eodCheckIfOutgoingExtracted(Date systemDate, String serviceID, String memberId) {
		boolean painMsgsCheck = false;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String strSysDate = sdf.format(systemDate);
		log.debug("strSysDate ====> "+strSysDate);

		//1.Check Pain Msgs
		try
		{
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("serviceId", serviceID);
			parameters.put("createdDate", strSysDate);
			parameters.put("processStatus1", readyForExtractStatus);
			parameters.put("processStatus2", loadStatus);
			parameters.put("memberId", memberId);
			log.debug("parameters =====> "+parameters);

			List<CasOpsCessionAssignEntity> painMsgsCheckList = null;

			if(serviceID.equalsIgnoreCase("RCAIN")) {
				painMsgsCheckList = genericDAO.findAllByNQCriteria("CasOpsCessionAssignEntity.findByCreatedDateTruncAndServiceIdCreditor", parameters);
			} else {
				painMsgsCheckList = genericDAO.findAllByNQCriteria("CasOpsCessionAssignEntity.findByCreatedDateTruncAndServiceIdDebtor", parameters);	
			}

			if(painMsgsCheckList != null && painMsgsCheckList.size() > 0) {
				painMsgsCheck = false;
			}
			else
				painMsgsCheck = true;
		} 
		catch (ObjectNotFoundException onfe) 
		{
			log.debug("No Object Exists on DB");
		} 
		catch (Exception e) 
		{
			log.error("Error on eodCheckIfAllFilesAreExtracted.painMsgsCheckList: "+ e.getMessage());
			e.printStackTrace();
		}

		return painMsgsCheck;
	}

	public boolean openHibernateSession() {
		Session session;
		try {
			genericDAO.openSession();
			return true;
		}
		catch(Exception ex) {
			log.error("Could not open hibernate session: "+ex.getMessage());
			ex.printStackTrace();
			return false;
		}
	}

	public boolean closeHibernateSession() {
		try {
			genericDAO.closeSession();
			return true;
		}
		catch(Exception ex) {
			log.error("Could not close hibernate session: "+ex.getMessage());
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public void bulkSaveMandates(List<?> bulkList) 
	{	
//		log.info("In the bulkSaveMandates: bulkList.size() "+bulkList.size());
		try 
		{
			Collection<Object> collection = new ArrayList<Object>();
			collection = (Collection<Object>) bulkList;
			
			genericDAO.bulkSaveAll(collection, 700);
		} 
		catch (Exception e) {
			log.error("Error on bulkSaveMandates: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	@Override
	public void bulkUpdateMandates(List<?> bulkList) 
	{	
//		log.info("In the bulkUpdateMandates: bulkList.size() "+bulkList.size());
		try 
		{
			Collection<Object> collection = new ArrayList<Object>();
			collection = (Collection<Object>) bulkList;
			
			genericDAO.bulkUpdateAll(collection, 700);

		} catch (Exception e) {
			log.error("Error on bulkUpdateMandates: " + e.getMessage());
			e.printStackTrace();
		}
	}

	
	@Override
	public void bulkUpdateBySQL(String sqlQuery) 
	{	
		try
		{
			genericDAO.executeNativeSQL(sqlQuery);
		}
		catch(Exception ex)
		{
			log.error("Error on bulkUpdateBySQL:- "+ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	public boolean checkForDuplicateMRTI(String mrti) {
		boolean dupl = false;
		try
		{
			String sql = "SELECT COUNT(*) FROM CAMOWNER.CAS_OPS_CESS_ASSIGN_TXNS WHERE MANDATE_REQ_TRAN_ID = '"+mrti+"' HAVING COUNT(*) > 0 ";
			dupl = genericDAO.findDuplicates(sql);
			if(dupl)
				log.info("DUPLICATE RESULT = "+dupl);
		}
		catch(Exception ex)
		{
			log.error("Error on checkForDuplicateMRTI:- "+ex.getMessage());
			ex.printStackTrace();
		}

		return dupl;
	}
	
	public TreeMap optimisedMatchPain012(String manReqTransId)
	{
		List<CasOpsCessionAssignEntity> matchedList = new ArrayList<CasOpsCessionAssignEntity>();
		TreeMap<String, CasOpsCessionAssignEntity> matchedMap = new TreeMap<String, CasOpsCessionAssignEntity>();
		
		try 
		{
			matchedList = (List<CasOpsCessionAssignEntity>) genericDAO.findAllByNamedQuery("CasOpsCessionAssignEntity.matchingPain012", "mandateReqTranId",manReqTransId);
			if(matchedList != null && matchedList.size() > 0)
			{
				if(matchedList.size() > 1) {
					for (CasOpsCessionAssignEntity casOpsCessionAssignEntity : matchedList) {
						matchedMap.put(casOpsCessionAssignEntity.getServiceId(),
								casOpsCessionAssignEntity);
					}
				}
				else {
					CasOpsCessionAssignEntity casOpsCessionAssignEntity = matchedList.get(0);
					matchedMap.put(casOpsCessionAssignEntity.getServiceId(),
							casOpsCessionAssignEntity);
				}
			}
		} 
		catch (ObjectNotFoundException onfe) 
		{
			log.debug("No Object Exists on DB");
		} 
		catch (Exception e) 
		{
			log.error("Error on matchPain012ToOrigMandate: "+ e.getMessage());
			e.printStackTrace();
		}

		return matchedMap;
	}
	
	@Override
	public void saveBulkOpsDailyBillingList(List<?> bulkList) 
	{
		
		try 
		{
			Collection<Object> collection = new ArrayList<Object>();
			collection = (Collection<Object>) bulkList;
//			log.info("List Size: "+ bulkList.size());
			
			genericDAO.bulkSaveAll(collection, 700);

		} catch (Exception e) {
			log.error("Error on saveBulkOpsDailyBillingList: " + e.getMessage());
			e.printStackTrace();
		}
	}
		
		
//		try 
//		{
//			if (obj instanceof MdtAcOpsDailyBillingEntity) 
//			{
//				MdtAcOpsDailyBillingEntity opsDailyBillingEntity = (MdtAcOpsDailyBillingEntity) obj;
//				//				log.info("Entity in Save==> "+opsDailyBillingEntity);
//				genericDAO.save(opsDailyBillingEntity);
//
//				return true;
//			} 
//			else 
//			{
//				log.info("Unable to convert type to MdtAcOpsDailyBillingEntity.");
//				return false;
//			}
//		} 
//		catch (Exception e) 
//		{
//			log.error("Error on saveOpsDailyBilling: " + e.getMessage());
//			e.printStackTrace();
//			return false;
//		}

	public Map retrieveStatusReportRejections(BigDecimal hdrSeqNo, String errorType) {
		List<CasOpsStatusDetailsEntity> opsStatusDetailsList = new ArrayList<CasOpsStatusDetailsEntity>();
		Map<String, List<CasOpsStatusDetailsEntity>> statusDetailsMap = new HashMap<String, List<CasOpsStatusDetailsEntity>>();

		try {
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("statusHdrSeqNo", hdrSeqNo);
			parameters.put("errorType", errorType);

			opsStatusDetailsList = (List<CasOpsStatusDetailsEntity>) genericDAO.findAllByCriteria(
                CasOpsStatusDetailsEntity.class, parameters);
			
			if(opsStatusDetailsList != null && opsStatusDetailsList.size() > 0) 
			{
					for(int i = 0; i < opsStatusDetailsList.size(); i++)
					{	
						String txnId = opsStatusDetailsList.get(i).getTxnId();
						
						List<CasOpsStatusDetailsEntity> txnErrorList = new ArrayList<CasOpsStatusDetailsEntity>();
						
						for (CasOpsStatusDetailsEntity statusDtlsEntity : opsStatusDetailsList) {
							
							if(statusDtlsEntity.getTxnId().equalsIgnoreCase(txnId)) {
								txnErrorList.add(statusDtlsEntity);
							}
						}
						
						statusDetailsMap.put(txnId, txnErrorList);
				}
			}
		} catch (ObjectNotFoundException onfe) {
			log.error("No Object Exists on DB");
		} catch (Exception e) {
			log.error("Error on retrieveStatusReportRejections: " + e.getMessage());
			e.printStackTrace();
		}

		return statusDetailsMap;
	}
	
	public boolean tt1ManinDelayedRespInserts(String procDate,int currentSeqNo, int lastSeqNo)
	{
		boolean tt1ManinDRBool = false;

		StringBuffer sbTT1_ManinDR = new StringBuffer();
		
		sbTT1_ManinDR.append("INSERT INTO MANOWNER.MDT_AC_OPS_DAILY_BILLING ( ");
		sbTT1_ManinDR.append("CREDITOR_BANK, DEBTOR_BANK, SUB_SERVICE, TXN_TYPE, TXN_STATUS,CREATED_BY, "); 
		sbTT1_ManinDR.append("CREATED_DATE, BILL_EXP_STATUS, ACTION_DATE, TXN_ID, MNDT_REF_NUM, RESP_DATE ) ");
		sbTT1_ManinDR.append("SELECT b.INSTRUCTINGAGENTAMS,  b.INSTRUCTEDAGENTAMS, 'MANIN', 'TT1', ");
		sbTT1_ManinDR.append("TO_CHAR((case when a.ACCEPTEDINDICATORAMS = 'true' then 'S' else ");
		sbTT1_ManinDR.append("case when a.ACCEPTEDINDICATORAMS = 'false' then 'U' else null end end)),'MANOWNER', SYSDATE,'N', "); 
		sbTT1_ManinDR.append("TO_DATE(SUBSTR(b.TRANSDATETIME,0,4)||'-'||SUBSTR(b.TRANSDATETIME,5,2)||'-'||SUBSTR(b.TRANSDATETIME,7,2),'YYYY-MM-DD'), ");
		sbTT1_ManinDR.append("a.TRANSACTIONIDENTIFIERAMS, a.MANDATEREFNUMBERAMS, TO_DATE('"+procDate+"','YYYY-MM-DD') ");
		sbTT1_ManinDR.append("FROM MANOWNER.JNL_ACQ a ");
		sbTT1_ManinDR.append("INNER JOIN MANOWNER.JNL_ACQ b ON a.TRANSACTIONIDENTIFIERAMS = b.TRANSACTIONIDENTIFIERAMS "); 
		sbTT1_ManinDR.append("WHERE a.SERVICEIDAMS = 'ST012' AND a.MTI = 5506 AND a.RESULTCODE = 0 AND b.MTI = 5501 AND b.RESULTCODE = 0 AND a.REASONCODEAMS = '900000' AND b.REASONCODEAMS = '900000' AND (a.ID > "+lastSeqNo+" and a.ID <= "+currentSeqNo+") "); 
		sbTT1_ManinDR.append("ORDER BY a.TRANSACTIONIDENTIFIERAMS ASC ");

		try
		{
			String tt1ManinDRSQL = sbTT1_ManinDR.toString();
//			log.info("tt1ManinDRSQL: " + tt1ManinDRSQL);
			genericDAO.executeNativeSQL(tt1ManinDRSQL);
			tt1ManinDRBool = true;
		}
		catch(Exception ex)
		{
			log.error("Error on TT1 MANIN DR SQL:- "+ex.getMessage());
			ex.printStackTrace();
			tt1ManinDRBool = false;
		}

		return tt1ManinDRBool;
	}
	
	public boolean tt1ManinImmediateRespInserts(String procDate,int currentSeqNo, int lastSeqNo)
	{
		boolean tt1ManinIRBool = false;

		StringBuffer sbTT1_ManinIR = new StringBuffer();
		
		sbTT1_ManinIR.append("INSERT INTO MANOWNER.MDT_AC_OPS_DAILY_BILLING ( ");
		sbTT1_ManinIR.append("CREDITOR_BANK, DEBTOR_BANK, SUB_SERVICE, TXN_TYPE, TXN_STATUS,CREATED_BY, ");
		sbTT1_ManinIR.append("CREATED_DATE, BILL_EXP_STATUS, ACTION_DATE, TXN_ID, MNDT_REF_NUM, RESP_DATE) ");
		sbTT1_ManinIR.append("SELECT INSTRUCTINGAGENTAMS, INSTRUCTEDAGENTAMS, 'MANIN', 'TT1', (case when ACCEPTEDINDICATORAMS = 'true' then 'S' else 'U' end), ");
		sbTT1_ManinIR.append("'MANOWNER', SYSDATE, 'N', TO_DATE(SUBSTR(TRANSDATETIME,0,4)||'-'||SUBSTR(TRANSDATETIME,5,2)||'-'||SUBSTR(TRANSDATETIME,7,2),'YYYY-MM-DD'), ");
		sbTT1_ManinIR.append("TRANSACTIONIDENTIFIERAMS, MANDATEREFNUMBERAMS, TO_DATE('"+procDate+"','YYYY-MM-DD') ");
		sbTT1_ManinIR.append("FROM MANOWNER.JNL_ACQ ");
		sbTT1_ManinIR.append("WHERE SERVICEIDAMS = 'MANIR' AND RESULTCODE = 0 AND MTI=5501 AND (ID > "+lastSeqNo+" and ID <= "+currentSeqNo+") ");
		sbTT1_ManinIR.append("ORDER BY TRANSACTIONIDENTIFIERAMS ASC ");

		try
		{
			String tt1ManinIRSQL = sbTT1_ManinIR.toString();
//			log.info("tt1ManinIRSQL: " + tt1ManinIRSQL);
			genericDAO.executeNativeSQL(tt1ManinIRSQL);
			tt1ManinIRBool = true;
		}
		catch(Exception ex)
		{
			log.error("Error on TT1 MANIN IR SQL:- "+ex.getMessage());
			ex.printStackTrace();
			tt1ManinIRBool = false;
		}

		return tt1ManinIRBool;
	}
	
	public boolean tt3ManinInserts(String procDate,int currentSeqNo, int lastSeqNo)
	{
		boolean tt3ManinBool = false;

		StringBuffer sbTT3_Manin = new StringBuffer();
		
		sbTT3_Manin.append("INSERT INTO MANOWNER.MDT_AC_OPS_DAILY_BILLING ( ");
		sbTT3_Manin.append("CREDITOR_BANK, DEBTOR_BANK, SUB_SERVICE, TXN_TYPE, TXN_STATUS,CREATED_BY, ");
		sbTT3_Manin.append("CREATED_DATE, BILL_EXP_STATUS, ACTION_DATE, TXN_ID, MNDT_REF_NUM, RESP_DATE) ");
		sbTT3_Manin.append("SELECT INSTRUCTINGAGENTAMS, INSTRUCTEDAGENTAMS, 'MANIN', 'TT3', (case when ACCEPTEDINDICATORAMS = 'true' then 'S' else 'U' end), ");
		sbTT3_Manin.append("'MANOWNER', SYSDATE, 'N', TO_DATE(SUBSTR(TRANSDATETIME,0,4)||'-'||SUBSTR(TRANSDATETIME,5,2)||'-'||SUBSTR(TRANSDATETIME,7,2),'YYYY-MM-DD'), ");
		sbTT3_Manin.append("TRANSACTIONIDENTIFIERAMS, MANDATEREFNUMBERAMS, TO_DATE('"+procDate+"','YYYY-MM-DD') ");
		sbTT3_Manin.append("FROM MANOWNER.JNL_ACQ ");
		sbTT3_Manin.append("WHERE SERVICEIDAMS = 'MANIR' AND RESULTCODE = 0 AND MTI=5502 AND (ID > "+lastSeqNo+" and ID <= "+currentSeqNo+") ");
		sbTT3_Manin.append("ORDER BY TRANSACTIONIDENTIFIERAMS ASC ");
		
		try
		{
			String tt3ManinSQL = sbTT3_Manin.toString();
//			log.info("tt3ManinSQL: " + tt3ManinSQL);
			genericDAO.executeNativeSQL(tt3ManinSQL);
			tt3ManinBool = true;
		}
		catch(Exception ex)
		{
			log.error("Error on TT3 MANIN SQL:- "+ex.getMessage());
			ex.printStackTrace();
			tt3ManinBool = false;
		}

		return tt3ManinBool;
	}
	
	public boolean tt1ManamDelayedRespInserts(String procDate,int currentSeqNo, int lastSeqNo)
	{
		boolean tt1ManamDRBool = false;

		StringBuffer sbTT1_ManamDR = new StringBuffer();
		
		sbTT1_ManamDR.append("INSERT INTO MANOWNER.MDT_AC_OPS_DAILY_BILLING ( ");
		sbTT1_ManamDR.append("CREDITOR_BANK, DEBTOR_BANK, SUB_SERVICE, TXN_TYPE, TXN_STATUS,CREATED_BY, ");
		sbTT1_ManamDR.append("CREATED_DATE, BILL_EXP_STATUS, ACTION_DATE, TXN_ID, MNDT_REF_NUM, RESP_DATE, AUTH_CODE) ");
		sbTT1_ManamDR.append("SELECT b.INSTRUCTINGAGENTAMS, b.INSTRUCTEDAGENTAMS, 'CARIN', 'TT1', (case when a.ACCEPTEDINDICATORAMS = 'true' then 'S' else 'U' end), ");
		sbTT1_ManamDR.append("'MANOWNER', SYSDATE, 'N', TO_DATE(SUBSTR(b.TRANSDATETIME,0,4)||'-'||SUBSTR(b.TRANSDATETIME,5,2)||'-'||SUBSTR(b.TRANSDATETIME,7,2),'YYYY-MM-DD'), ");
		sbTT1_ManamDR.append("a.TRANSACTIONIDENTIFIERAMS, a.MANDATEREFNUMBERAMS, TO_DATE('"+procDate+"','YYYY-MM-DD'), ");
		sbTT1_ManamDR.append("(CASE WHEN SUBSTR(REPLACE(REPLACE(REPLACE(REPLACE(b.UNDRLYGAMDMNTDTLSBLOCKAMS, chr(10)), chr(13)), chr(9)), ' '), INSTR(REPLACE(REPLACE(REPLACE(REPLACE(b.UNDRLYGAMDMNTDTLSBLOCKAMS, chr(10)), chr(13)), chr(9)), ' '), '<LclInstrm><Prtry>',1,1)+18,4) ");
		sbTT1_ManamDR.append("NOT IN ('0226','0227','0228','0229','0230') THEN NULL ELSE SUBSTR(REPLACE(REPLACE(REPLACE(REPLACE(b.UNDRLYGAMDMNTDTLSBLOCKAMS, chr(10)), chr(13)), chr(9)), ' '), INSTR(REPLACE(REPLACE(REPLACE(REPLACE(b.UNDRLYGAMDMNTDTLSBLOCKAMS, chr(10)), chr(13)), chr(9)), ' '), '<LclInstrm><Prtry>',1,1)+18,4) END) ");
		sbTT1_ManamDR.append("FROM MANOWNER.JNL_ACQ a ");
		sbTT1_ManamDR.append("INNER JOIN MANOWNER.JNL_ACQ b ON a.TRANSACTIONIDENTIFIERAMS = b.TRANSACTIONIDENTIFIERAMS ");
		sbTT1_ManamDR.append("WHERE a.SERVICEIDAMS = 'ST012' AND a.MTI = 5506 AND a.RESULTCODE = 0 AND (a.ID > "+lastSeqNo+" and a.ID <= "+currentSeqNo+") ");
		sbTT1_ManamDR.append("AND b.MTI = 5503 AND b.RESULTCODE = 0 AND a.REASONCODEAMS = '900000' AND b.REASONCODEAMS = '900000' ");
		sbTT1_ManamDR.append("ORDER BY a.TRANSACTIONIDENTIFIERAMS ASC ");

		try
		{
			String tt1ManamDRSQL = sbTT1_ManamDR.toString();
//			log.info("tt1ManamDRSQL: " + tt1ManamDRSQL);
			genericDAO.executeNativeSQL(tt1ManamDRSQL);
			tt1ManamDRBool = true;
		}
		catch(Exception ex)
		{
			log.error("Error on TT1 CARIN DR SQL:- "+ex.getMessage());
			ex.printStackTrace();
			tt1ManamDRBool = false;
		}

		return tt1ManamDRBool;
	}

	public TreeMap retriveOutgoingService(String serviceId)
	{
		List<CasOpsFileSizeLimitEntity>
				casOpsFileSizeLimitEntityList = new ArrayList<CasOpsFileSizeLimitEntity>();
		TreeMap<String, CasOpsFileSizeLimitEntity> matchedMap = new TreeMap<String, CasOpsFileSizeLimitEntity>();
		try
		{
			casOpsFileSizeLimitEntityList = (List<CasOpsFileSizeLimitEntity>) genericDAO.findAllByNamedQuery("CasOpsFileSizeLimitEntity.findBySubService", "subService",serviceId);
			if(casOpsFileSizeLimitEntityList != null && casOpsFileSizeLimitEntityList.size() > 0)
			{
				if(casOpsFileSizeLimitEntityList.size() > 1) {
					log.info("Many Txn in Map");
					for (CasOpsFileSizeLimitEntity casOpsFileSizeLimitEntity : casOpsFileSizeLimitEntityList) {
						matchedMap.put(casOpsFileSizeLimitEntity.getCasOpsFileSizeLimitPK().getSubService(),
								casOpsFileSizeLimitEntity);
					}
				}
				else {
					log.info("one Txn in Map");
					CasOpsFileSizeLimitEntity casOpsFileSizeLimitEntity = casOpsFileSizeLimitEntityList.get(0);
					matchedMap.put(casOpsFileSizeLimitEntity.getCasOpsFileSizeLimitPK().getSubService(),
							casOpsFileSizeLimitEntity);
			}
		}
		
	}
		catch (ObjectNotFoundException onfe) 
		{
			log.debug("No Object Exists on DB");
		} 
		catch (Exception e) 
		{
			log.error("Error on retriveOutgoingService: "+ e.getMessage());
			e.printStackTrace();
		}

		return matchedMap;
	}
	
	public Object retriveOutgoingService(String outgoingService, String destInstId)
	{
		CasOpsFileSizeLimitEntity casOpsFileSizeLimitEntity = new CasOpsFileSizeLimitEntity();

		try
		{
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("casOpsFileSizeLimitPK.subService", outgoingService);
			parameters.put("casOpsFileSizeLimitPK.memberId", destInstId);

			casOpsFileSizeLimitEntity =  (CasOpsFileSizeLimitEntity)genericDAO.findByCriteria(
					CasOpsFileSizeLimitEntity.class, parameters);
		}
		catch (ObjectNotFoundException ne)
		{ 
			log.debug("No matching record found:  " + ne.getMessage());
			ne.printStackTrace(); 
		} 
		catch (Exception e)
		{
			log.debug("Error on retriveOutgoingService: " + e.getMessage()); 
			e.printStackTrace();
		}

		return casOpsFileSizeLimitEntity;
	}

	
	public boolean tt1ManamImmediateRespInserts(String procDate,int currentSeqNo, int lastSeqNo)
	{
		boolean tt1ManamIRBool = false;

		StringBuffer sbTT1_ManamIR = new StringBuffer();
		
		sbTT1_ManamIR.append("INSERT INTO MANOWNER.MDT_AC_OPS_DAILY_BILLING ( ");
		sbTT1_ManamIR.append("CREDITOR_BANK, DEBTOR_BANK, SUB_SERVICE, TXN_TYPE, TXN_STATUS,CREATED_BY, ");
		sbTT1_ManamIR.append("CREATED_DATE, BILL_EXP_STATUS, ACTION_DATE, TXN_ID, MNDT_REF_NUM, RESP_DATE, AUTH_CODE) ");
		sbTT1_ManamIR.append("SELECT INSTRUCTINGAGENTAMS, INSTRUCTEDAGENTAMS, 'CARIN', 'TT1', ");
		sbTT1_ManamIR.append("(case when ACCEPTEDINDICATORAMS = 'true' then 'S' else case when ACCEPTEDINDICATORAMS = 'false' then 'U' else 'D' end end), ");
		sbTT1_ManamIR.append("'MANOWNER', SYSDATE, 'N', TO_DATE(SUBSTR(TRANSDATETIME,0,4)||'-'||SUBSTR(TRANSDATETIME,5,2)||'-'||SUBSTR(TRANSDATETIME,7,2),'YYYY-MM-DD'), ");
		sbTT1_ManamIR.append("TRANSACTIONIDENTIFIERAMS, MANDATEREFNUMBERAMS, TO_DATE('"+procDate+"','YYYY-MM-DD'), "); 
		sbTT1_ManamIR.append("(CASE WHEN SUBSTR(REPLACE(REPLACE(REPLACE(REPLACE(UNDRLYGAMDMNTDTLSBLOCKAMS, chr(10)), chr(13)), chr(9)), ' '), INSTR(REPLACE(REPLACE(REPLACE(REPLACE(UNDRLYGAMDMNTDTLSBLOCKAMS, chr(10)), chr(13)), chr(9)), ' '), '<LclInstrm><Prtry>',1,1)+18,4) ");
		sbTT1_ManamIR.append("NOT IN ('0226','0227','0228','0229','0230') THEN NULL ELSE SUBSTR(REPLACE(REPLACE(REPLACE(REPLACE(UNDRLYGAMDMNTDTLSBLOCKAMS, chr(10)), chr(13)), chr(9)), ' '), INSTR(REPLACE(REPLACE(REPLACE(REPLACE(UNDRLYGAMDMNTDTLSBLOCKAMS, chr(10)), chr(13)), chr(9)), ' '), '<LclInstrm><Prtry>',1,1)+18,4) END)  ");
		sbTT1_ManamIR.append("FROM MANOWNER.JNL_ACQ ");
		sbTT1_ManamIR.append("WHERE SERVICEIDAMS = 'MANIR' AND RESULTCODE = 0 AND MTI=5503 AND (ID > "+lastSeqNo+" and ID <= "+currentSeqNo+") ");
		sbTT1_ManamIR.append("ORDER BY TRANSACTIONIDENTIFIERAMS ASC ");
		
		try
		{
			String tt1ManamIRSQL = sbTT1_ManamIR.toString();
//			log.info("tt1ManamIRSQL: " + tt1ManamIRSQL);
			genericDAO.executeNativeSQL(tt1ManamIRSQL);
			tt1ManamIRBool = true;
		}
		catch(Exception ex)
		{
			log.error("Error on TT1 CARIN IR SQL:- "+ex.getMessage());
			ex.printStackTrace();
			tt1ManamIRBool = false;
		}

		return tt1ManamIRBool;
	}
	
	public boolean tt3ManamInserts(String procDate,int currentSeqNo, int lastSeqNo)
	{
		boolean tt3ManamBool = false;

		StringBuffer sbTT3_Manam = new StringBuffer();
		
		sbTT3_Manam.append("INSERT INTO MANOWNER.MDT_AC_OPS_DAILY_BILLING ( ");
		sbTT3_Manam.append("CREDITOR_BANK, DEBTOR_BANK, SUB_SERVICE, TXN_TYPE, TXN_STATUS,CREATED_BY, ");
		sbTT3_Manam.append("CREATED_DATE, BILL_EXP_STATUS, ACTION_DATE, TXN_ID, MNDT_REF_NUM, RESP_DATE, AUTH_CODE) ");
		sbTT3_Manam.append("SELECT INSTRUCTINGAGENTAMS,INSTRUCTEDAGENTAMS, 'CARIN', 'TT3', ");
		sbTT3_Manam.append("(case when ACCEPTEDINDICATORAMS = 'true' then 'S' else case when ACCEPTEDINDICATORAMS = 'false' then 'U' else 'D' end end), ");
		sbTT3_Manam.append("'MANOWNER', SYSDATE, 'N', TO_DATE(SUBSTR(TRANSDATETIME,0,4)||'-'||SUBSTR(TRANSDATETIME,5,2)||'-'||SUBSTR(TRANSDATETIME,7,2),'YYYY-MM-DD'), ");
		sbTT3_Manam.append("TRANSACTIONIDENTIFIERAMS, MANDATEREFNUMBERAMS, TO_DATE('"+procDate+"','YYYY-MM-DD'), ");  
		sbTT3_Manam.append("(CASE WHEN SUBSTR(REPLACE(REPLACE(REPLACE(REPLACE(UNDRLYGAMDMNTDTLSBLOCKAMS, chr(10)), chr(13)), chr(9)), ' '), INSTR(REPLACE(REPLACE(REPLACE(REPLACE(UNDRLYGAMDMNTDTLSBLOCKAMS, chr(10)), chr(13)), chr(9)), ' '), '<LclInstrm><Prtry>',1,1)+18,4) ");
		sbTT3_Manam.append("NOT IN ('0226','0227','0228','0229','0230') THEN NULL ELSE SUBSTR(REPLACE(REPLACE(REPLACE(REPLACE(UNDRLYGAMDMNTDTLSBLOCKAMS, chr(10)), chr(13)), chr(9)), ' '), INSTR(REPLACE(REPLACE(REPLACE(REPLACE(UNDRLYGAMDMNTDTLSBLOCKAMS, chr(10)), chr(13)), chr(9)), ' '), '<LclInstrm><Prtry>',1,1)+18,4) END) ");
		sbTT3_Manam.append("FROM MANOWNER.JNL_ACQ ");
		sbTT3_Manam.append("WHERE SERVICEIDAMS = 'MANIR' AND RESULTCODE = 0 AND MTI=5504 AND (ID > "+lastSeqNo+" and ID <= "+currentSeqNo+") ");
		sbTT3_Manam.append("ORDER BY TRANSACTIONIDENTIFIERAMS ASC ");
		
		try
		{
			String tt3ManamSQL = sbTT3_Manam.toString();
//			log.info("tt3ManamSQL: " + tt3ManamSQL);
			genericDAO.executeNativeSQL(tt3ManamSQL);
			tt3ManamBool = true;
		}
		catch(Exception ex)
		{
			log.error("Error on TT3 CARIN SQL:- "+ex.getMessage());
			ex.printStackTrace();
			tt3ManamBool = false;
		}

		return tt3ManamBool;
	}
	
	public boolean tt1MancnInserts(String procDate,int currentSeqNo, int lastSeqNo)
	{
		boolean tt1MancnBool = false;

		StringBuffer sbTT1_Mancn = new StringBuffer();
		
		sbTT1_Mancn.append("INSERT INTO MANOWNER.MDT_AC_OPS_DAILY_BILLING ( ");
		sbTT1_Mancn.append("CREDITOR_BANK, DEBTOR_BANK, SUB_SERVICE, TXN_TYPE, TXN_STATUS,CREATED_BY, ");
		sbTT1_Mancn.append("CREATED_DATE, BILL_EXP_STATUS, ACTION_DATE, TXN_ID, MNDT_REF_NUM, RESP_DATE) ");
		sbTT1_Mancn.append("SELECT INSTRUCTINGAGENTAMS, INSTRUCTEDAGENTAMS, 'MANCN', 'TT1','S', 'MANOWNER', ");
		sbTT1_Mancn.append("SYSDATE, 'N', TO_DATE(SUBSTR(TRANSDATETIME,0,4)||'-'||SUBSTR(TRANSDATETIME,5,2)||'-'||SUBSTR(TRANSDATETIME,7,2),'YYYY-MM-DD'), ");
		sbTT1_Mancn.append("TRANSACTIONIDENTIFIERAMS, MANDATEREFNUMBERAMS, TO_DATE('"+procDate+"','YYYY-MM-DD') ");
		sbTT1_Mancn.append("FROM MANOWNER.JNL_ACQ ");
		sbTT1_Mancn.append("WHERE SERVICEIDAMS = 'MANIR' AND RESULTCODE = 0 AND MTI=5505 AND (ID > "+lastSeqNo+" and ID <= "+currentSeqNo+") ");
		sbTT1_Mancn.append("ORDER BY TRANSACTIONIDENTIFIERAMS ASC ");
		
		try
		{
			String tt1MancnSQL = sbTT1_Mancn.toString();
//			log.info("tt1MancnSQL: " + tt1MancnSQL);
			genericDAO.executeNativeSQL(tt1MancnSQL);
			tt1MancnBool = true;
		}
		catch(Exception ex)
		{
			log.error("Error on TT1 MANCN SQL:- "+ex.getMessage());
			ex.printStackTrace();
			tt1MancnBool = false;
		}

		return tt1MancnBool;
	}
	public boolean populateRealTimeErrorCodesInserts(int currentSeqNo, int lastSeqNo)
	{
		boolean rtErrorCodesBool = false;

		StringBuffer sbRtErrorCodes = new StringBuffer();
		
		sbRtErrorCodes.append("INSERT INTO MANOWNER.MDT_AC_OPS_ERROR_CODES_REPORT( ");
		sbRtErrorCodes.append("PROCESSING_DATE, PROCESSING_MONTH, DEBTOR_BANK, CREDITOR_BANK ,ULTIMATE_CREDITOR, ABBREV_SHORT_NAME, ");
		sbRtErrorCodes.append("TXN_ID, ERROR_CODE, ERROR_CODE_DESC, SERVICE_ID, EXTRACTED_IND, ONLINE_IND) ");
		sbRtErrorCodes.append("with temptab as ");
		sbRtErrorCodes.append("(select to_date(substr(a.TRANSDATETIME,1,8), 'YYYY-MM-DD') AS realTimeProcDate ");
		sbRtErrorCodes.append(",to_char(to_date(substr(a.TRANSDATETIME,1,8), 'YYYYMMDD'), 'Mon-YYYY') as processingMonth ");
		sbRtErrorCodes.append(",a.INSTRUCTEDAGENTAMS as debtorBank ");
		sbRtErrorCodes.append(",a.INSTRUCTINGAGENTAMS AS creditorBank ");
		sbRtErrorCodes.append(",case when msgtypeams = 'pain.009' then ");
		sbRtErrorCodes.append("NVL(EXTRACTVALUE(XMLTYPE(CONCAT(CONCAT('<A>',a.mandateblockams),'</A>')), '/A//UltmtCdtr/Nm'), ");
		sbRtErrorCodes.append("EXTRACTVALUE(XMLTYPE(CONCAT(CONCAT('<A>',a.mandateblockams),'</A>')), '/A//UltmtCdtr/Id/OrgId/Othr/Id')) ");
		sbRtErrorCodes.append("when msgtypeams = 'pain.010' then ");
		sbRtErrorCodes.append("NVL(EXTRACTVALUE(XMLTYPE(CONCAT(CONCAT('<A>',a.UNDRLYGAMDMNTDTLSBLOCKAMS),'</A>')), ");
		sbRtErrorCodes.append("'/A//UltmtCdtr/Nm'),EXTRACTVALUE(XMLTYPE(CONCAT(CONCAT('<A>',a.UNDRLYGAMDMNTDTLSBLOCKAMS),'</A>')), '/A//UltmtCdtr/Id/OrgId/Othr/Id')) ");
		sbRtErrorCodes.append("when msgtypeams = 'pain.011' then ");
		sbRtErrorCodes.append("EXTRACTVALUE(XMLTYPE(CONCAT(CONCAT('<A>',a.UNDRLYGCXLDTLSBLOCKAMS),'</A>')), '/A//UltmtCdtr/Id/OrgId/Othr/Id') ");
		sbRtErrorCodes.append("when msgtypeams = 'pain.012' then ");
		sbRtErrorCodes.append("EXTRACTVALUE(XMLTYPE(CONCAT(CONCAT('<A>',a.UNDRLYGACCPTNCDTLSBLOCKAMS),'</A>')), '/A//UltmtCdtr/Id/OrgId/Othr/Id') ");
		sbRtErrorCodes.append("end as XMLString ");
		sbRtErrorCodes.append(",a.TRANSACTIONIDENTIFIERAMS as txnId ");
		sbRtErrorCodes.append(",a.REASONCODEAMS as errorCode ");
		sbRtErrorCodes.append(",b.ERROR_CODE_DESC as errorCodeDesc ");
		sbRtErrorCodes.append(",a.SERVICEIDAMS AS serviceId ");
		sbRtErrorCodes.append("FROM MANOWNER.JNL_ACQ a ");
		sbRtErrorCodes.append("left outer join MANOWNER.MDT_CNFG_ERROR_CODES b ");
		sbRtErrorCodes.append("on a.REASONCODEAMS = b.error_code ");
		sbRtErrorCodes.append("WHERE RESULTCODE = 0 and a.paymentstatusgroupcodeams = 'RJCT' AND (a.ID > "+lastSeqNo+" and a.ID <= "+currentSeqNo+")) ");
		sbRtErrorCodes.append("Select distinct realTimeProcDate, processingMonth, debtorBank, creditorBank, XMLString as ultimateCreditor, XMLString as abbrevShortName, txnId, errorCode, errorCodeDesc, serviceId, 'N', 'Y' ");
		sbRtErrorCodes.append("from temptab ");
		
		try
		{
			String rtErrorCodesSQL = sbRtErrorCodes.toString();
			log.info("rtErrorCodesSQL: " + rtErrorCodesSQL);
			genericDAO.executeNativeSQL(rtErrorCodesSQL);
			rtErrorCodesBool = true;
		}
		catch(Exception ex)
		{
			log.error("Error on RT ERROR CODES SQL:- "+ex.getMessage());
			ex.printStackTrace();
			rtErrorCodesBool = false;
		}

		return rtErrorCodesBool;
	}

}

