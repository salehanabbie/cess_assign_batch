package com.bsva.mandatesArchive;

import com.bsva.PropertyUtil;
import com.bsva.entities.CasSysctrlSysParamEntity;
import com.bsva.interfaces.AdminBeanRemote;
import com.bsva.interfaces.FileProcessBeanRemote;
import com.bsva.interfaces.ServiceBeanRemote;
import com.bsva.interfaces.ValidationBeanRemote;
import com.bsva.utils.Util;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.ejb.EJB;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;

/**
 * @author SalehaR-2019/09/28 Aligned to SingleTable
 */
public class AC_ArchiveMessages_ST implements Serializable {
  @EJB
  PropertyUtil propertyUtil;

  private static final long serialVersionUID = 1L;
  public static Logger log = Logger.getLogger(AC_ArchiveMessages_ST.class);

  private static ServiceBeanRemote beanRemote;
  private static ValidationBeanRemote valBeanRemote;
  private static AdminBeanRemote adminBeanRemote;
  private static FileProcessBeanRemote fileProcessBeanRemote;

  CasSysctrlSysParamEntity casSysctrlSysParamEntity;
  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

  int responsePeriod = 0;
  int archivePeriod = 0;

  String archiveday;
  String cleanUpArch = "NO";
  String matchType = "MATCH";
  String acceptType = "ACCEPT";
  String expireType = "EXPIRE";

  public AC_ArchiveMessages_ST() {
    try {
      propertyUtil = new PropertyUtil();
      archiveday = propertyUtil.getPropValue("RESPONSE.PERIOD");
      cleanUpArch = propertyUtil.getPropValue("CLEAN_UP_ARCHIVE");
      contextValidationBeanCheck();
      contextAdminBeanCheck();
      contextCheck();
      contextFileProcBeanCheck();
    } catch (Exception e) {
      log.error("AC_ArchiveMessages_ST- Could not find CessionAssignment.properties in classpath");
    }
  }

  public void testArhiveConfHdrs() throws ParseException {

    Date currentDate = null;
    Date systemDate = null;

    casSysctrlSysParamEntity = new CasSysctrlSysParamEntity();
    casSysctrlSysParamEntity =
        (CasSysctrlSysParamEntity) adminBeanRemote.retrieveActiveSysParameter();

    if (casSysctrlSysParamEntity.getResponsePeriod() != null &&
        casSysctrlSysParamEntity.getActiveInd().equalsIgnoreCase("Y")) {
      currentDate = casSysctrlSysParamEntity.getProcessDate();
      systemDate = casSysctrlSysParamEntity.getProcessDate();
      responsePeriod = casSysctrlSysParamEntity.getResponsePeriod();
      archivePeriod = casSysctrlSysParamEntity.getArchivePeriod().intValue();
    } else {
      currentDate = new Date();
      systemDate = new Date();
      responsePeriod = Integer.valueOf(archiveday);
    }

    //		=========ARCHIVE OPTIMISED QUERY CALLS ==========//
    Date responseDate = calculateResponseDates(currentDate, responsePeriod);
    log.info("The expiredTxnDate  is  ===" + sdf.format(responseDate));

    Date excessDate = calculateExcessPeriodDates(currentDate, responsePeriod);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    log.info("The excessDate  is  ===" + sdf.format(excessDate));

    //		log.info("currentDate: "+currentDate);
    String archDate = sdf.format(currentDate);
    //6. Archive Mdte Responses
    log.info("XXXXXXXXXXXX~~~~~~ARCHIVING MANDATE RESPONSE FOR INFO ~~~~~XXXXXXXXXXXX");
    boolean mdteRespBool = beanRemote.archiveMdteResponsesBySQL(archDate);
//		if(mdteRespBool)
//		{
//			//					//Delete MdteResponse
//			boolean delMdteResp = beanRemote.deleteMdteResponses();
//			if(delMdteResp)
//				log.info("XXXXXXXXXXXX~~~~~~MANDATE RESPONSE FOR INFO DELETED
//				SUCCESSFULLY~~~~~XXXXXXXXXXXX");
//		}
  }

  public void archiveProcessLogic() throws ParseException {
    Date currentDate = null;
    Date systemDate = null;

    casSysctrlSysParamEntity = new CasSysctrlSysParamEntity();
    casSysctrlSysParamEntity =
        (CasSysctrlSysParamEntity) adminBeanRemote.retrieveActiveSysParameter();

    if (casSysctrlSysParamEntity.getResponsePeriod() != null &&
        casSysctrlSysParamEntity.getActiveInd().equalsIgnoreCase("Y")) {
      currentDate = casSysctrlSysParamEntity.getProcessDate();
      systemDate = casSysctrlSysParamEntity.getProcessDate();
      responsePeriod = casSysctrlSysParamEntity.getResponsePeriod();
      archivePeriod = casSysctrlSysParamEntity.getArchivePeriod().intValue();
    } else {
      currentDate = new Date();
      systemDate = new Date();
      responsePeriod = Integer.valueOf(archiveday);
    }

    //		=========ARCHIVE OPTIMISED QUERY CALLS ==========//
    Date responseDate = calculateResponseDates(currentDate, responsePeriod);
    log.info("The expiredTxnDate  is  ===" + sdf.format(responseDate));

    Date excessDate = calculateExcessPeriodDates(currentDate, responsePeriod);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    log.info("The excessDate  is  ===" + sdf.format(excessDate));

    //		log.info("currentDate: "+currentDate);
    String archDate = sdf.format(currentDate);

    //1. Archive Mandates (pain.009/010/011)
    log.info("XXXXXXXXXXXX~~~~~~ARCHIVING MATCHED CASA REQUESTS [CARIN] TXNS~~~~~XXXXXXXXXXXX");
    boolean matchedTxnsBool =
        fileProcessBeanRemote.archiveMandateTxnsBySQL(matchType, null, archDate);
    log.info("matchedTxnsBool: " + matchedTxnsBool);
    if (matchedTxnsBool) {
      //Delete Matched Mandates
      boolean delMatched = fileProcessBeanRemote.deleteMatchedMandateTxnsBySQL(matchType, null);
		if (delMatched) {
			log.info(
					"XXXXXXXXXXXX~~~~~~MATCHED CASA REQUESTS [CARIN] TXNS ARCHIVED SUCCESSFULLY~~~~~XXXXXXXXXXXX");
		}
    }

    //2. Mandate Acceptances (Pain.012)
    log.info("XXXXXXXXXXXX~~~~~~ARCHIVING CASA FINAL RESPONSES[RCAIN] TXNS~~~~~XXXXXXXXXXXX");
    boolean acceptedTxnsBool =
        fileProcessBeanRemote.archiveMandateTxnsBySQL(acceptType, null, archDate);
    log.info("acceptedTxnsBool: " + acceptedTxnsBool);
    if (acceptedTxnsBool) {
      //Delete Acceptance Mandates
      boolean delMatched = fileProcessBeanRemote.deleteMatchedMandateTxnsBySQL(acceptType, null);
		if (delMatched) {
			log.info(
					"XXXXXXXXXXXX~~~~~~MANDATE CASA FINAL RESPONSES[RCAIN] ARCHIVED SUCCESSFULLY~~~~~XXXXXXXXXXXX");
		}
    }

    //3. Mandate Expired
    log.info("XXXXXXXXXXXX~~~~~~ARCHIVING EXPIRED CASA TXNS~~~~~XXXXXXXXXXXX");
    boolean expiredTxnsBool =
        fileProcessBeanRemote.archiveMandateTxnsBySQL(expireType, sdf.format(responseDate),
            archDate);
    log.info("expiredTxnsBool: " + expiredTxnsBool);
    if (expiredTxnsBool) {
      //Delete Matched Mandates
      boolean delMatched =
          fileProcessBeanRemote.deleteMatchedMandateTxnsBySQL(expireType, sdf.format(responseDate));
		if (delMatched) {
			log.info("XXXXXXXXXXXX~~~~~~CASA EXPIRED TXNS SUCCESSFULLY~~~~~XXXXXXXXXXXX");
		}
    }

    //4. Archive Confirmation Responses
    log.info("XXXXXXXXXXXX~~~~~~ARCHIVING CASA INTERIM RESPONSES[ST201] ~~~~~XXXXXXXXXXXX");
    boolean arcConfHdrsBool = beanRemote.archiveConfHdrs(archDate);
    boolean arcConfDtlsBool = beanRemote.archiveConfDtls(archDate);
	  if (arcConfHdrsBool && arcConfDtlsBool) {
		  log.info(
				  "XXXXXXXXXXXX~~~~~~OPS CASA INTERIM RESPONSES[ST201] DELETED " +
						  "SUCCESSFULLY~~~~~XXXXXXXXXXXX");
	  }

    //6. Archive Status Reports
    log.info("XXXXXXXXXXXX~~~~~~ARCHIVING STATUS REPORTS~~~~~XXXXXXXXXXXX");
    boolean arcStsRepBool = beanRemote.archiveStatusReportsBySQL(archDate);
    if (arcStsRepBool) {
      //			//Delete Ops Status
      boolean delOpsStatus = beanRemote.deleteStatusReports();
		if (delOpsStatus) {
			log.info("XXXXXXXXXXXX~~~~~~OPS STATUS REPORTS DELETED SUCCESSFULLY~~~~~XXXXXXXXXXXX");
		}
    }

    //9. Archive Daily Biling
    log.info("XXXXXXXXXXXX~~~~~~ARCHIVING OPS DAILY BILLING ~~~~~XXXXXXXXXXXX");
    boolean dlyBillBool = beanRemote.archiveDailyBillingBySQL(archDate);
    if (dlyBillBool) {
      //Delete DailyBill
      boolean delDlyBill = beanRemote.deleteDailyBilling();
		if (delDlyBill) {
			log.info("XXXXXXXXXXXX~~~~~~OPS DAILY BILLING DELETED SUCCESSFULLY~~~~~XXXXXXXXXXXX");
		}
    }

//		//11.Archive Error Codes
//		log.info("XXXXXXXXXXXX~~~~~~ARCHIVING OPS ERROR CODES~~~~~XXXXXXXXXXXX");
//		boolean errCodeBool = beanRemote.archiveOpsErrorCodesRpt(archDate);
//		if(errCodeBool)
//		{
//			//Delete Ops Error Codes
//			boolean delErrCodes = beanRemote.deleteOpsErrorCodesRpt();
//			if(delErrCodes)
//				log.info("XXXXXXXXXXXX~~~~~~OPS ERROR CODES DELETED
//				SUCCESSFULLY~~~~~XXXXXXXXXXXX");
//		}

    //7.Archive MNDT COUNTS
    log.info("XXXXXXXXXXXX~~~~~~ARCHIVING MNDT COUNTS ~~~~~XXXXXXXXXXXX");
    boolean mndtCountBool = beanRemote.archiveMandateCountsBySQL();
    if (mndtCountBool) {
      //Delete Mndt Counts
      boolean delMndtCount = beanRemote.deleteMandateCounts();
		if (delMndtCount) {
			log.info("XXXXXXXXXXXX~~~~~~OPS MANDATE COUNTS DELETED SUCCESSFULLY~~~~~XXXXXXXXXXXX");
		}
    }

    //12.Archive File Register
    log.info("XXXXXXXXXXXX~~~~~~ARCHIVING FILE REGISTER~~~~~XXXXXXXXXXXX");
    boolean fileRegBool = beanRemote.archiveFileRegBySQL(archDate);
    if (fileRegBool) {
      //Delete File Reg
      boolean delFileReg = beanRemote.deleteFileReg();
		if (delFileReg) {
			log.info("XXXXXXXXXXXX~~~~~~OPS FILE REGISTER DELETED SUCCESSFULLY~~~~~XXXXXXXXXXXX");
		}
    }

    //				13.Archive Left Over Txns
    log.info("XXXXXXXXXXXX~~~~~~ARCHIVING LEFT OVER TXNS ~~~~~XXXXXXXXXXXX");
    boolean leftOverArc =
        fileProcessBeanRemote.archiveLeftOverTxnsBySQL(sdf.format(responseDate), archDate);
    if (leftOverArc) {
      //Delete Left Over Txns
      boolean delLeftOver = fileProcessBeanRemote.deleteLeftOverTxnsBySQL(sdf.format(responseDate));
		if (delLeftOver) {
			log.info("XXXXXXXXXXXX~~~~~~DELETING LEFT OVER TXNS SUCCESSFULLY~~~~~XXXXXXXXXXXX");
		}
    }

    //13. Remove archived txns after archive period is reached.
    log.info("Clean Up Arch property: " + cleanUpArch);
    if (cleanUpArch.equalsIgnoreCase("YES")) {
      log.info("XXXXXXXXXXXX~~~~~~CLEANING UP ARCHIVE TABLES~~~~~XXXXXXXXXXXX");
      Date archiveDate = calculateArchiveDate(currentDate, archivePeriod);
      log.debug("The archive date is  ===" + archiveDate);
      archiveDate = DateUtils.truncate(archiveDate, java.util.Calendar.DAY_OF_MONTH);
      log.info("Truncated Date ===" + archiveDate);
      fileProcessBeanRemote.cleanUpArchivedTxnsBySQL(sdf.format(archiveDate));
    }

    //Archive Txns Billing
    log.info("XXXXXXXXXXXX~~~~~~ARCHIVING OPS TXNS BILLING ~~~~~XXXXXXXXXXXX");
    boolean txnsBillBool = beanRemote.archiveOpsTxnBillingBySQL(archDate);
    if (txnsBillBool) {
      //Delete Txns Billing
      boolean delTxnsBillBool = beanRemote.deleteOpsTxnBillingBySQL();
		if (delTxnsBillBool) {
			log.info("XXXXXXXXXXXX~~~~~~DELETING OPS TXNS BILLING SUCCESSFULLY~~~~~XXXXXXXXXXXX");
		}
    }

    //Archive TT2 Report Data
    log.info("XXXXXXXXXXXX~~~~~~ARCHIVING OPS TXNS BILL REPORT DATA ~~~~~XXXXXXXXXXXX");
    boolean txnsBillDataBool = beanRemote.archiveOpsTxnBillRptDataBySQL(archDate);
    if (txnsBillDataBool) {
      //Delete Txns Billing
      boolean delTxnsBillDataBool = beanRemote.deleteOpsTxnBillRptDataBySQL();
		if (delTxnsBillDataBool) {
			log.info(
					"XXXXXXXXXXXX~~~~~~DELETING OPS TXNS BILL REPORT DATA SUCCESSFULLY~~~~~XXXXXXXXXXXX");
		}
    }
  }

  public Date calculateResponseDates(Date dt, int rspnsPeriod) throws ParseException {
    Calendar c = Calendar.getInstance();
    c.setTime(dt);
    //			c.add(Calendar.DATE, -2);
    c.add(Calendar.DATE, -rspnsPeriod);

    //		log.info("dt********:"+ dt);
    //		log.info("c********:"+ c.getTime());
    return dt = c.getTime();
  }

  public Date calculateExcessPeriodDates(Date dt, int excessPeriod) throws ParseException {
    Calendar c = Calendar.getInstance();
    c.setTime(dt);
    c.add(Calendar.DATE, -excessPeriod);

    log.debug("processDate********:" + dt);
    log.debug("excessPeriod********:" + c.getTime());
    return dt = c.getTime();
  }

  /*Method to calculate 1 year past from todays date*/
  public Date calculateArchiveDate(Date dt, int archivePeriod) throws ParseException {
    Calendar cal = Calendar.getInstance();
    cal.setTime(dt);
    cal.add(Calendar.YEAR, -archivePeriod);

    log.debug("dt********:" + dt);
    log.debug("c********:" + cal.getTime());
    return dt = cal.getTime();
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
