package com.bsva.interfaces;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.ejb.Local;

/**
 * @author SalehaR
 *
 */
@Local
public interface FileProcessBeanLocal {

	public List<?> validateOriginalMsgId(String msgId);
	public List<?> validateMndtReqTranIdUnique(String mrti);
	public Object matchPain012ToOrigMandate(String manReqTransId, String messageType);
	public Object matchPacs002ToOrigMandate(String mndtReqTranId, String messageType);
	public boolean createAcOpsMandateTxns(Object obj, String debtorBrNo, String crAbbShrtName,String mndtRefNo);
	public List<?> retrieveMandatesForExtract(boolean debtMemType, String memberId, String serviceId, String procStatus);
	public Boolean updateMdtOpsMandateTxns(Object obj);
	public boolean archiveMandateTxnsBySQL(String archiveType, String expiredDate, String archDate);
	public boolean deleteMatchedMandateTxnsBySQL(String archiveType, String expiredDate);
	public boolean archiveLeftOverTxnsBySQL(String expiredDate, String archDate);
	public boolean deleteLeftOverTxnsBySQL(String expiredDate);
	public boolean cleanUpArchivedTxnsBySQL(String expiredDate);
	public boolean eodCheckIfOutgoingExtracted(Date systemDate, String serviceID, String memberId);

	//Session Methods
	public boolean openHibernateSession();
	public boolean closeHibernateSession();
	public void bulkSaveMandates(List<?> bulkList);
	public void bulkUpdateMandates(List<?> bulkList);
	public void bulkUpdateBySQL(String sqlQuery);
	public boolean checkForDuplicateMRTI(String mrti);
	//	public List<?> optimisedMatchPain012(String manReqTransId);
	public TreeMap optimisedMatchPain012(String manReqTransId);
	public void saveBulkOpsDailyBillingList(List<?> bulkList);
	public Map retrieveStatusReportRejections(BigDecimal hdrSeqNo, String errorType);
	public Object retriveOutgoingService(String outgoingService, String destInstId);

}
