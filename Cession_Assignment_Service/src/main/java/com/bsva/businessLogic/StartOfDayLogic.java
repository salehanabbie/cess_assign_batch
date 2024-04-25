package com.bsva.businessLogic;

import com.bsva.FileWatcher;
import com.bsva.PropertyUtil;
import com.bsva.authcoll.singletable.validation.Validation_ST;
import com.bsva.delivery.StartOfTransmissionExtract;
import com.bsva.entities.CasSysctrlCustParamEntity;
import com.bsva.entities.CasSysctrlFileSizeLimitEntity;
import com.bsva.entities.CasSysctrlSchedulerEntity;
import com.bsva.entities.CasSysctrlServicesEntity;
import com.bsva.entities.CasSysctrlSlaTimesEntity;
import com.bsva.entities.CasSysctrlSysParamEntity;
import com.bsva.entities.MdtAcOpsBillingCtrlsEntity;
import com.bsva.entities.MdtAcOpsFileSizeLimitEntity;
import com.bsva.entities.MdtAcOpsFileSizeLimitPK;
import com.bsva.entities.MdtAcOpsProcessControlsEntity;
import com.bsva.entities.MdtAcOpsSchedulerEntity;
import com.bsva.entities.MdtAcOpsSotEotCtrlEntity;
import com.bsva.entities.MdtAcOpsSotEotCtrlPK;
import com.bsva.entities.MdtCnfgReportNamesEntity;
import com.bsva.entities.MdtOpsCustParamEntity;
import com.bsva.entities.MdtOpsLastExtractTimesEntity;
import com.bsva.entities.MdtOpsProcessControlsEntity;
import com.bsva.entities.MdtOpsRefSeqNrEntity;
import com.bsva.entities.MdtOpsRefSeqNrPK;
import com.bsva.entities.MdtOpsRepSeqNrEntity;
import com.bsva.entities.MdtOpsRepSeqNrPK;
import com.bsva.entities.MdtOpsServicesEntity;
import com.bsva.entities.MdtOpsSlaTimesEntity;
import com.bsva.entities.ObsSystemBillingCtrlsEntity;
import com.bsva.entities.ObsSystemBillingCtrlsPK;
import com.bsva.entities.SysCisBankEntity;
import com.bsva.interfaces.AdminBeanRemote;
import com.bsva.interfaces.QuartzSchedulerBeanRemote;
import com.bsva.interfaces.ServiceBeanRemote;
import com.bsva.reports.PasaMandateAmendmentReport;
import com.bsva.utils.Util;
import com.itextpdf.text.DocumentException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import javax.ejb.EJB;
import org.apache.log4j.Logger;

/**
 * @author ElelwaniR
 * @author SalehaR - 2018-12-18 [CHG-148253/144056] Run PHIR Reports as part of SOD on Sunday
 */

public class StartOfDayLogic {

  @EJB
  PropertyUtil propertyUtil;

  public static Logger log = Logger.getLogger(StartOfDayLogic.class);
  private static AdminBeanRemote adminBeanRemote;
  private static QuartzSchedulerBeanRemote quartzSchedulerBeanRemote;
  private static ServiceBeanRemote serviceBeanRemote;
  private String systemName = "MANOWNER";
  List<CasSysctrlCustParamEntity> sysCntrlCustList = new ArrayList<CasSysctrlCustParamEntity>();
  FileWatcher fileWatcher = new FileWatcher();
  Date processingDate = null;
  List<SysCisBankEntity> sysCisBankEntityList = new ArrayList<SysCisBankEntity>();
  Date currentDate = new Date();
  CasSysctrlSysParamEntity casSysctrlSysParamEntity, mdtSysParamEntity;
  CasSysctrlSlaTimesEntity casSysctrlSlaTimesEntity;
  MdtOpsProcessControlsEntity mdtOpsProcessControlsEntity = new MdtOpsProcessControlsEntity();
  MdtAcOpsProcessControlsEntity mdtAcOpsProcessControlsEntity = new MdtAcOpsProcessControlsEntity();
  List<CasSysctrlFileSizeLimitEntity> mdtSysctrlFileSizeLimitEntityList =
      new ArrayList<CasSysctrlFileSizeLimitEntity>();
  int lastSeqNo = 0000;
  String seqNo = String.format("%06d", lastSeqNo);
  SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
  SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
  Date currDate = null;
  boolean sodCheck = false;
  public String feedbackMsg;
  String archivePeriodVal = "1";
  String responsePeriodVal;
  List<CasSysctrlServicesEntity> sysCntrlServicesList = null;
  String activeInd, nonActiveInd;
  String manotServ, manomServ, mancoServ, manocServ, st103Serv, manroServ, manrfServ, spoutServ,
      sroutServ, mandcServ;
  String st100Serv, st102Serv, st104Serv, st105Serv, st106Serv, st007Serv, st008Serv, st994Serv,
      iamport, itemLimit, transactionLimit;
  String archiveday;


  public StartOfDayLogic() {
    try {
      propertyUtil = new PropertyUtil();
      this.activeInd = propertyUtil.getPropValue("ActiveInd");

      this.nonActiveInd = propertyUtil.getPropValue("NonActiveInd");
      this.manomServ = propertyUtil.getPropValue("Output.Pain010");
      this.manocServ = propertyUtil.getPropValue("Output.Pain012");
      this.st103Serv = propertyUtil.getPropValue("Output.Pacs002");

      this.st100Serv = propertyUtil.getPropValue("StatusRep.ST100");
      this.st102Serv = propertyUtil.getPropValue("StatusRep.ST102");
      this.st104Serv = propertyUtil.getPropValue("StatusRep.ST104");
      archiveday = propertyUtil.getPropValue("RESPONSE.PERIOD");
      iamport = propertyUtil.getPropValue("MDT.IAM_PORT");
      itemLimit = propertyUtil.getPropValue("AC.MIGRATION.ITEM.LIMIT");
      transactionLimit = propertyUtil.getPropValue("AC.FILE.TRANSACTION.LIMIT");

    } catch (Exception e) {
      log.error(" StartOfDayLogic - Could not find MandateMessageCommons.properties in classpath");
    }

  }

  //	public boolean testSod()
  //	{
  //		contextAdminBeanCheck();
  //		contextQuartzSchedulerBeanCheck ();
  //		contextServiceBeanRemoteCheck ();
  //
  //		String cDate = sdf1.format(currentDate);
  //		String msgResults;
  //
  //		try {
  //			 currDate = sdf1.parse(cDate);
  //		} catch (ParseException e) {
  //			// TODO Auto-generated catch block
  //			e.printStackTrace();
  //		}
  //		//Populate Billing Table.
  //		boolean  populateBilling = populateBillingCntrlInfo();
  //		return populateBilling;
  //	}
  //

  //public void testSotFiles()
  //{
  //	contextAdminBeanCheck();
  //	contextQuartzSchedulerBeanCheck ();
  //	contextServiceBeanRemoteCheck ();
  //
  ////	feedbackMsg = null;
  //
  //
  //	boolean custParSaved = false;
  //	boolean servSaved = false;
  //	boolean opsSlaTimesSaved = false;
  //	boolean refLastSeq=false;
  //	boolean acOpsSotEotSaved =false;
  //	boolean sysParamSaved =false ;
  //	boolean populateCustParamSaved=false;
  //	boolean sysCustparamSaved= false;
  //	boolean acOpsPublicHolidayPouplated = false ;
  //	boolean opsCronTimeSaved;
  //	boolean populateSysCntrlCustParam = false;
  //	boolean populateOpsScheduler = false;
  //	boolean populateBilling = false;
  //	boolean populateSOTFiles = false;
  //
  //	acOpsSotEotSaved=populateAcOpsSotEot();
  //	servSaved=populatesOpsServices();
  //	populateSOTFiles = populateAllSOTFiles();
  //}

  public boolean SodImplementation() throws ParseException {

    contextAdminBeanCheck();
    contextQuartzSchedulerBeanCheck();
    contextServiceBeanRemoteCheck();

    //		feedbackMsg = null;


    boolean custParSaved = false;
    boolean servSaved = false;
    boolean opsSlaTimesSaved = false;
    boolean refLastSeq = false;
    boolean acOpsSotEotSaved = false;
    boolean sysParamSaved = false;
    boolean populateCustParamSaved = false;
    boolean sysCustparamSaved = false;
    boolean acOpsPublicHolidayPouplated = false;
    boolean opsCronTimeSaved;
    boolean populateSysCntrlCustParam = false;
    boolean populateOpsScheduler = false;
    boolean populateBilling = false;
    boolean populateSOTFiles = false;
    boolean populateReportSeqNr = false;
    boolean populateCisMembersToCustParamSaved = false;
    boolean populateOpsLastExtractTime = false;
    boolean populateFileSizeLimit = false;


    String cDate = sdf1.format(currentDate);
    String msgResults;

    try {
      currDate = sdf1.parse(cDate);
    } catch (ParseException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    mdtAcOpsProcessControlsEntity =
        (MdtAcOpsProcessControlsEntity) adminBeanRemote.retrieveCisDownloadInd(currDate);

    if (mdtAcOpsProcessControlsEntity != null) {
      if (mdtAcOpsProcessControlsEntity.getCisDownloadInd() != null &&
          mdtAcOpsProcessControlsEntity.getCisDownloadInd().equalsIgnoreCase("N")) {
        sodCheck = false;
        log.info(
            "CIS download not done for current day. Please run the CIS download in order to " +
					"continue  ");
        feedbackMsg =
            "CIS download not done for current day. Please run the CIS download in order to " +
					"continue  ";
				/*	CisDownlaodLogic cisDownlaodLogic = new CisDownlaodLogic();
					cisDownlaodLogic.CisDownloadImplementation();*/
      } else {
        casSysctrlSysParamEntity =
            (CasSysctrlSysParamEntity) adminBeanRemote.retrieveActiveSysParameter();

        if (casSysctrlSysParamEntity != null &&
            casSysctrlSysParamEntity.getActiveInd().equalsIgnoreCase("Y")) {
          sodCheck = false;
          feedbackMsg = "An active day exists,cannot run start of day! ";
          log.info("An active day exists,cannot process start of day ");
        } else {
          sysParamSaved = populateSysParameters();
          acOpsSotEotSaved = populateAcOpsSotEot();
          servSaved = populatesOpsServices();
          populateCisMembersToCustParamSaved = populateCisMembersToCustParam();
          custParSaved = populateOpsCustomerParameters();
//					Not needed for C&A
//					opsCronTimeSaved=populatesOpsCronTable(); 
//					acOpsPublicHolidayPouplated =populatePublicHoliday();
          refLastSeq = populateOpsRefSeqNr();
          opsSlaTimesSaved = populatesOpsSlaTimesTable();
          populateOpsScheduler = populateOpsScheduler();
          populateReportSeqNr = populateReportSeqNr();
          populateOpsLastExtractTime = populateOpsLastExtractTime();
          populateFileSizeLimit = populateFileSizeLimit();

          updateSodRunInd();

          //Populate Billing Table.
          populateBilling = populateBillingCntrlInfo();
          //Reset Billing Window number
          MdtAcOpsBillingCtrlsEntity billingCtrlsEntity =
              (MdtAcOpsBillingCtrlsEntity) serviceBeanRemote.retrieveBillingCtrls("BILLING");
          if (billingCtrlsEntity != null) {
            billingCtrlsEntity.setBillingWindow(Short.valueOf("0"));
            serviceBeanRemote.saveBillingCntrl(billingCtrlsEntity);
          }

          //Call the config data method - Constructor calls the method.
          Validation_ST valSt = new Validation_ST();
          valSt.populateConfigTableData(true);

          try {
            //								createNewProcessingFolder();
            createNewTempFolder();
            createArchiveInPutFolder();
            createArchiveOutPutFolder();
            populateSOTFiles = populateAllSOTFiles();
            log.info("<<---------STARTING FILE WATCHER---------->");
            //								fileWatcher.startMonitor();
            //								MonitorDirectory.monitor();
          } catch (Exception e) {
            e.printStackTrace();
            log.error("Error on Folder or FileWatcher " + e.getMessage());
          }

          log.info("sysParamSaved === " + sysParamSaved);
          log.info("acOpsSotEotSaved === " + acOpsSotEotSaved);
          log.info("servSaved === " + servSaved);
          log.info("custParSaved === " + custParSaved);
          log.info("acOpsPublicHolidayPouplated === " + acOpsPublicHolidayPouplated);
          log.info("refLastSeq === " + refLastSeq);
          log.info("populateOpsScheduler === " + populateOpsScheduler);
          log.info("populateReportSeqNr === " + populateReportSeqNr);
          log.info("populateOpsLastExtractTime ===" + populateOpsLastExtractTime);
          log.info("populateFileSizeLimit ===" + populateFileSizeLimit);
          if (sysParamSaved && /*populateCustParamSaved &&*/ acOpsSotEotSaved && servSaved &&
              custParSaved && acOpsPublicHolidayPouplated && refLastSeq && populateOpsScheduler
              && populateReportSeqNr/*&&opsCronTimeSaved*/ && populateOpsLastExtractTime) {
            sodCheck = true;
            feedbackMsg = "Start of day has run successfully !";
            log.info("Start of day has run successfully !");
          }
        }
      }
    } else {
      //			error("CIS download not done for current day. Please run the CIS download in
		//			order to continue");
      sodCheck = false;
      feedbackMsg =
          "******CIS download not done for current day. Please run the CIS download in order to " +
				  "continue******";
      log.info(
          "******CIS download not done for current day. Please run the CIS download in order to " +
				  "continue******");
    }
    log.info("*************************FEEDBACK MSG = " + feedbackMsg);

	  if (sodCheck) {
		  feedbackMsg = "Start of day has run successfully !";
	  }
    return sodCheck;
  }


  public boolean populateOpsCustomerParameters() {
    boolean saved = false;
    sysCntrlCustList =
        (List<CasSysctrlCustParamEntity>) adminBeanRemote.retrieveCustomerParameters();
    sysCisBankEntityList = (List<SysCisBankEntity>) adminBeanRemote.retrieveSysCisBank();
    List<MdtOpsCustParamEntity> opsCustParam = new ArrayList<MdtOpsCustParamEntity>();
    opsCustParam = (List<MdtOpsCustParamEntity>) adminBeanRemote.retrieveOpsCustParamTime();


    if (opsCustParam.size() == 0) {
      if (sysCntrlCustList.size() > 0) {
        for (CasSysctrlCustParamEntity syscntrCustEntity : sysCntrlCustList) {
          MdtOpsCustParamEntity opsEntity = new MdtOpsCustParamEntity();
          opsEntity.setInstId(syscntrCustEntity.getInstId());
          opsEntity.setManInitXsdNs(syscntrCustEntity.getManInitXsdNs());
          opsEntity.setManInitLstSeq(seqNo);
          opsEntity.setManInitLastFileNr(seqNo);
          opsEntity.setManAmdXsdNs(syscntrCustEntity.getManAmdXsdNs());
          opsEntity.setManAmdLstSeq(seqNo);
          opsEntity.setManAmdLastFileNr(seqNo);
          opsEntity.setManCanXsdNs(syscntrCustEntity.getManCanXsdNs());
          opsEntity.setManCanLstSeq(seqNo);
          opsEntity.setManCanLastFileNr(seqNo);
          opsEntity.setManAccpXsdNs(syscntrCustEntity.getManAccpXsdNs());
          opsEntity.setManAccpLstSeq(seqNo);
          opsEntity.setManAccpLastFileNr(seqNo);
          opsEntity.setActiveInd(syscntrCustEntity.getActiveInd());
          opsEntity.setCreatedBy(systemName);
          opsEntity.setCreatedDate(new Date());
          opsEntity.setMdtReqIdReuseInd(syscntrCustEntity.getMdtReqIdReuseInd());
          opsEntity.setManRespXsdNs(syscntrCustEntity.getMdteRespXsdNs());
          opsEntity.setManReqXsdNs(syscntrCustEntity.getMdteReqXsdNs());
          opsEntity.setManReqLastFileNr(seqNo);
          opsEntity.setManReqLstSeq(seqNo);
          opsEntity.setManRespLastFileNr(seqNo);
          opsEntity.setManRespLstSeq(seqNo);
          opsEntity.setManStatusRepXsdNs(syscntrCustEntity.getManStatusRepXsdNs());
          opsEntity.setManStatusRepLstSeq(seqNo);
          opsEntity.setManStatusRepLastFileNr(seqNo);
          opsEntity.setManConfirmXsdNs(syscntrCustEntity.getManConfirmXsdNs());
          opsEntity.setManConfirmLstSeq(seqNo);
          opsEntity.setManConfirmLstFileNr(seqNo);

          saved = adminBeanRemote.createOpsCustParameters(opsEntity);
        }
      } else {
        if (opsCustParam.size() > 0) {
          log.info("The table still has data ,please run End of day to clean it out");
        }
      }

      if (saved) {
        log.info("Ops Customer Parameters Table has been populated...");
      } else {
        log.error("Cannot proceed with start of day,");
      }
    }
    return saved;
  }

  public boolean populateOpsProcessControl() {
    boolean saved = false;

    sysCisBankEntityList = (List<SysCisBankEntity>) adminBeanRemote.retrieveSysCisBank();

    List<MdtOpsProcessControlsEntity> opsProcessCntrlList =
        new ArrayList<MdtOpsProcessControlsEntity>();
    opsProcessCntrlList =
        (List<MdtOpsProcessControlsEntity>) adminBeanRemote.retrieveOpsProcessControl();

    if (opsProcessCntrlList.size() == 0) {
      if (sysCisBankEntityList.size() > 0) {
        for (SysCisBankEntity sysCisBankEntity : sysCisBankEntityList) {
          MdtOpsProcessControlsEntity mdtOpsProcessCntrl = new MdtOpsProcessControlsEntity();

          mdtOpsProcessCntrl.setActiveInd("Y");
          mdtOpsProcessCntrl.setInstId(sysCisBankEntity.getMemberNo());
          mdtOpsProcessCntrl.setMaxNrRecords(sysCisBankEntity.getMaxNrRecords());
          mdtOpsProcessCntrl.setNrOfDaysProc(String.valueOf(sysCisBankEntity.getNrOfDaysProc()));
          mdtOpsProcessCntrl.setPubHolProc(sysCisBankEntity.getPubHolProc());

          saved = adminBeanRemote.createOpsProcessControls(mdtOpsProcessCntrl);
        }
      } else {
        if (opsProcessCntrlList.size() > 0) {
          log.info("The table still has data ,please run End of day to clean it out");
        }
      }
    }

    if (saved) {
      log.info("Ops Process Controls Table  has been populated...");
    }
    return false;


  }

  public boolean populateSysParameters() throws ParseException {

    boolean saved = false;
    casSysctrlSlaTimesEntity = (CasSysctrlSlaTimesEntity) adminBeanRemote.retrieveStartTime();

    SimpleDateFormat parser = new SimpleDateFormat("HH:mm");

    Calendar cal = Calendar.getInstance();
    cal.setTime(currentDate);

    //Put it back in the Date object
    currentDate = cal.getTime();

    String strrDate = parser.format(currentDate);
    log.info("Date: " + strrDate);
    Date userDate = parser.parse(strrDate);

    //Date time01 = parser.parse(mdtSysctrlSlaTimesEntity.getStartTime());
    Date time02 = parser.parse(casSysctrlSlaTimesEntity.getEndTime());


    mdtSysParamEntity = new CasSysctrlSysParamEntity();

    processingDate = currentDate;
    responsePeriodVal = String.valueOf(archiveday);

    if (userDate.before(time02)) {
      mdtSysParamEntity.setProcessDate(processingDate);
      log.info("processingDate-->" + processingDate);
    } else {
      if (userDate.after(time02)) {

        currDate = calculateOneDayExtra();
        String cDate = sdf1.format(currDate);
        String msgResults;

        try {
          currDate = sdf1.parse(cDate);
        } catch (ParseException e) {
          e.printStackTrace();
        }
        mdtSysParamEntity.setProcessDate(currDate);
        log.info("currDate-->" + currDate);
      }
    }

    //mdtSysParamEntity.setProcessDate(currDate);
    mdtSysParamEntity.setSysName("MANDATES");
    mdtSysParamEntity.setDefCurr("ZAR");
    short inactiveDuration = 1;
    BigInteger archivePeriod = new BigInteger(archivePeriodVal);
    mdtSysParamEntity.setInactiveDuration(inactiveDuration);
    mdtSysParamEntity.setSysType("AC");
    mdtSysParamEntity.setArchivePeriod(archivePeriod);
    mdtSysParamEntity.setActiveInd("Y");
    mdtSysParamEntity.setCreatedBy("");
    mdtSysParamEntity.setInBalanceInd("N");
    mdtSysParamEntity.setCreatedDate(currentDate);
    mdtSysParamEntity.setModifiedBy(null);
    mdtSysParamEntity.setModifiedDate(null);
    mdtSysParamEntity.setSodRunInd("Y");
    mdtSysParamEntity.setEodRunInd("N");
    mdtSysParamEntity.setNextMondayHolInd("Y");
    mdtSysParamEntity.setEasterDaysInd("Y");
    mdtSysParamEntity.setSysCloseRunInd("N");
    mdtSysParamEntity.setCisDwnldInd("Y");
    mdtSysParamEntity.setCisDwnldDate(currDate);
    mdtSysParamEntity.setResponsePeriod(Short.valueOf(responsePeriodVal));
    mdtSysParamEntity.setIamPort(Short.valueOf(iamport));
    mdtSysParamEntity.setItemLimit(new BigInteger(itemLimit));
    mdtSysParamEntity.setFileTransactionLimit(new BigInteger(transactionLimit));


    log.info("mdtSysParamEntity" + mdtSysParamEntity);
    saved = adminBeanRemote.createSysParameters(mdtSysParamEntity);
    log.info("the saved information is #####################" + saved);

    if (saved) {
      log.info("System parameters has been populated ....");
    } else {
      if (saved && mdtSysParamEntity.getActiveInd().equalsIgnoreCase("Y") &&
          mdtSysParamEntity.getProcessDate().equals(currentDate)) {
        log.info("The Table has been populated for the day *****************");
      }
    }
    return saved;
  }

//	public boolean populatesOpsCronTable() 
//	{
//		boolean saved = false;
//
//		List<MdtSysctrlCronEntity> mdtSysctrlCronEntityList = new
//		ArrayList<MdtSysctrlCronEntity>();
//		mdtSysctrlCronEntityList = (List<MdtSysctrlCronEntity>) adminBeanRemote.retrieveCronTime();
//
//		List<MdtOpsCronEntity> mdtOpsCronEntityList = new ArrayList<MdtOpsCronEntity>();
//		mdtOpsCronEntityList = (List<MdtOpsCronEntity>) adminBeanRemote.retrieveOpsCronTime();
//
//		if (mdtOpsCronEntityList.size() == 0) {
//			if (mdtSysctrlCronEntityList.size() > 0) {
//				for (MdtSysctrlCronEntity mdtSysctrlCronEntity : mdtSysctrlCronEntityList) {
//					MdtOpsCronEntity mdtOpsCronEntity = new MdtOpsCronEntity();
//
//					mdtOpsCronEntity.setActiveInd(mdtSysctrlCronEntity.getActiveInd());
//					mdtOpsCronEntity.setCreatedBy(mdtSysctrlCronEntity.getCreatedBy());
//					mdtOpsCronEntity.setCreatedDate(mdtSysctrlCronEntity.getCreatedDate());
//					mdtOpsCronEntity.setCronTime(mdtSysctrlCronEntity.getCronTime());
//					mdtOpsCronEntity.setModifiedBy(mdtSysctrlCronEntity.getModifiedBy());
//					mdtOpsCronEntity.setModifiedDate(mdtSysctrlCronEntity.getModifiedDate());
//					mdtOpsCronEntity.setProcessName(mdtSysctrlCronEntity.getProcessName());
//					saved = adminBeanRemote.createOpsCron(mdtOpsCronEntity);
//				}
//			}
//		}
//
//		if (saved)
//		{
//			log.info("Ops Cron Table  has been populated...");
//		}
//
//		return saved;
//	}

  public boolean populatesOpsSlaTimesTable() {
    boolean saved = false;

    List<CasSysctrlSlaTimesEntity> mdtSysctrlSlaTimesEntityList =
        new ArrayList<CasSysctrlSlaTimesEntity>();
    mdtSysctrlSlaTimesEntityList =
        (List<CasSysctrlSlaTimesEntity>) adminBeanRemote.retrieveSlaTime();

    List<MdtOpsSlaTimesEntity> mdtOpsSlaTimesEntityList = new ArrayList<MdtOpsSlaTimesEntity>();

    mdtOpsSlaTimesEntityList = (List<MdtOpsSlaTimesEntity>) adminBeanRemote.retrieveOpsSlaTime();
    log.debug("The sla times has ##############################" + mdtSysctrlSlaTimesEntityList);
    if (mdtOpsSlaTimesEntityList != null && mdtOpsSlaTimesEntityList.size() == 0) {
      if (mdtSysctrlSlaTimesEntityList.size() > 0) {
        for (CasSysctrlSlaTimesEntity casSysctrlSlaTimesEntity : mdtSysctrlSlaTimesEntityList) {

          MdtOpsSlaTimesEntity mdtOpsSlaTimesEntity = new MdtOpsSlaTimesEntity();

          mdtOpsSlaTimesEntity.setEndTime(casSysctrlSlaTimesEntity.getEndTime());
          mdtOpsSlaTimesEntity.setService(casSysctrlSlaTimesEntity.getService());
          mdtOpsSlaTimesEntity.setStartTime(casSysctrlSlaTimesEntity.getStartTime());
          saved = adminBeanRemote.createOpsSlaTimes(mdtOpsSlaTimesEntity);
          log.debug("After saving we have the following data " + mdtOpsSlaTimesEntity);
        }
      }
    }

    if (saved) {
      log.info("Ops Sla Times Table  has been populated...");
    }

    return saved;
  }

  public boolean populateOpsRefSeqNr() {
    boolean saved = false;

    List<CasSysctrlServicesEntity> sysCntrlServicesList =
        new ArrayList<CasSysctrlServicesEntity>();// list

    sysCntrlServicesList =
        (List<CasSysctrlServicesEntity>) adminBeanRemote.retrieveServiceControl();
    List<MdtOpsRefSeqNrEntity> opsRefSeqNrList = new ArrayList<MdtOpsRefSeqNrEntity>();
    opsRefSeqNrList = (List<MdtOpsRefSeqNrEntity>) adminBeanRemote.retrieveOpsRefSeqNr();

    if (opsRefSeqNrList.size() == 0) {
      if (sysCntrlServicesList.size() > 0) {
        for (CasSysctrlServicesEntity syscntrServiceEntity : sysCntrlServicesList) {
          for (CasSysctrlCustParamEntity casSysctrlCustParamEntity : sysCntrlCustList) {
            MdtOpsRefSeqNrEntity opsRefSeqNr = new MdtOpsRefSeqNrEntity();
            MdtOpsRefSeqNrPK opsRefSeqPkEntity = new MdtOpsRefSeqNrPK();

            opsRefSeqPkEntity.setServiceId(syscntrServiceEntity.getServiceIdOut());
            opsRefSeqPkEntity.setMemberNo(casSysctrlCustParamEntity.getInstId());
            opsRefSeqNr.setMdtOpsRefSeqNrPK(opsRefSeqPkEntity);
            //opsRefSeqNr.setServiceId(syscntrServiceEntity.getServiceIdOut());
            opsRefSeqNr.setCreatedDate(new Date());
            opsRefSeqNr.setCreatedBy(systemName);
            opsRefSeqNr.setLastFileNr(seqNo);
            opsRefSeqNr.setLastSeqNr(seqNo);
            opsRefSeqNr.setModifiedBy(systemName);
            opsRefSeqNr.setModifiedDate(new Date());
            saved = adminBeanRemote.createOpsRefSeqNr(opsRefSeqNr);
          }
        }
      }
    }
    if (saved) {
      log.info("Ops RefSequence Number Table  has been populated...");
    }
    return saved;
  }

  public boolean populateCisMembersToCustParam() {
    boolean saved = false;
    try {
      sysCisBankEntityList = adminBeanRemote.retrieveSysCisBankMembers();
      sysCntrlCustList = adminBeanRemote.retrieveSysCustomerParameters();

      Collection<List<SysCisBankEntity>> listOne = Arrays.asList(sysCisBankEntityList);
      Collection<List<CasSysctrlCustParamEntity>> listTwo = Arrays.asList(sysCntrlCustList);

      Collection<List> similar = new HashSet<List>(listOne);
      Collection<List> different = new HashSet<List>();
      different.addAll(listOne);
      different.addAll(listTwo);

      similar.retainAll(listTwo);
      different.removeAll(similar);

      //			System.out.printf("One:%s%nTwo:%s%nSimilar:%s%nDifferent:%s%n", listOne,
		//			listTwo, similar, different);

      if (sysCisBankEntityList.size() == sysCntrlCustList.size()) {
        saved = true;
        return saved;

      } else {
        for (SysCisBankEntity sysCisBankEntity : sysCisBankEntityList) {
          for (CasSysctrlCustParamEntity casSysctrlCustParamEntity : sysCntrlCustList) {
            if (sysCisBankEntity.getMemberNo() != casSysctrlCustParamEntity.getInstId()) {
              log.info(sysCisBankEntity.getMemberNo() + " is not equals " +
                  casSysctrlCustParamEntity.getInstId());
              log.info("difrrent meberNo" + different);
              ;
              saved = false;
              break;
            }
          }

          return saved;
        }
      }
    } catch (NullPointerException e) {
      e.printStackTrace();
    }
    return saved;
  }

	/*
	public boolean saveCisMembersToCustParam()
	{
		boolean saved = false;

		List<SysCisBankEntity> sysCisBankList = new ArrayList<SysCisBankEntity>();
		List<MdtSysctrlCustParamEntity>  custParamList = new
		ArrayList<MdtSysctrlCustParamEntity>();
		sysCisBankList =adminBeanRemote.retrieveSysCisBankMembers();


		if ( sysCisBankList.size() > 0)
		{
			for(SysCisBankEntity sysCisBankEntity :sysCisBankList)
			{
				custParamList = adminBeanRemote.retrieveSysCustomerParameters();
				if(custParamList.size() > 0)
				{
					for
				}
			}
		}

			mdtSysctrlCustParamEntity = new MdtSysctrlCustParamEntity();
		mdtSysctrlCustParamEntity.setInstId(sysCisBankEntity.getMemberNo());
		saved =adminBeanRemote.createCustParameters(mdtSysctrlCustParamEntity);

	}
	 */
//
//	public boolean populatePublicHoliday ()
//	{
//		boolean saved = false;
//		List<MdtSysctrlPubholEntity> mdtSysctrlPubholEntityList = new
//		ArrayList<MdtSysctrlPubholEntity>();
//		mdtSysctrlPubholEntityList = adminBeanRemote.retrievePublicHoliday();
//
//
//		List<MdtAcOpsPubholEntity> mdtAcOpsPubholEntityList = new
//		ArrayList<MdtAcOpsPubholEntity>();
//		mdtAcOpsPubholEntityList = adminBeanRemote.retrieveSysPubHoliday();
//
//		if (mdtAcOpsPubholEntityList.size()==0)
//		{
//			if (mdtSysctrlPubholEntityList.size()>0)
//			{
//				for (MdtSysctrlPubholEntity  mdtSysctrlPubholEntity :mdtSysctrlPubholEntityList )
//				{
//					MdtAcOpsPubholEntity  mdtAcOpsPubholEntity = new MdtAcOpsPubholEntity();
//
//					mdtAcOpsPubholEntity.setActiveInd(mdtSysctrlPubholEntity.getActiveInd());
//					mdtAcOpsPubholEntity.setPubHolDate(mdtSysctrlPubholEntity.getPubHolDate());
//					mdtAcOpsPubholEntity.setPubHolidayDesc(mdtSysctrlPubholEntity
//					.getPubHolidayDesc());
//					saved =adminBeanRemote.createAcOpsPublicNHoliday(mdtAcOpsPubholEntity);
//				}
//			}
//		}
//
//		if (saved)
//		{
//			log.info("Ops Public Holiday  Table  has been populated...");
//		}
//		return saved;
//
//
//
//	}

  public boolean populateSysCntrlCustParam() {
    boolean saved = false;
    sysCisBankEntityList = (List<SysCisBankEntity>) adminBeanRemote.retrieveSysCisBank();

    sysCntrlCustList =
        (List<CasSysctrlCustParamEntity>) adminBeanRemote.retrieveCustomerParameters();


    if (sysCntrlCustList.size() == 0) {
      for (SysCisBankEntity sysCisBankEntity : sysCisBankEntityList) {
        CasSysctrlCustParamEntity casSysctrlCustParamEntity = new CasSysctrlCustParamEntity();
        casSysctrlCustParamEntity.setInstId(sysCisBankEntity.getMemberNo());
      }

    }

    if (saved) {
      log.info("Customer Parameters has been populated");

    }
    return saved;
  }

  public boolean populateAcOpsSotEot() {
    log.info("in thepopulateAcOpsSotEot ");

    boolean saved = false;


    List<CasSysctrlServicesEntity> sysCntrlServicesList =
        new ArrayList<CasSysctrlServicesEntity>();// list
    sysCntrlServicesList =
        (List<CasSysctrlServicesEntity>) adminBeanRemote.retrieveServiceControl();
    log.debug(
        "<<<<<<<<<<<<<<-----sysCntrlServicesList------->>>>>>>>>>>>>>" + sysCntrlServicesList);

    List<MdtAcOpsSotEotCtrlEntity> mdtAcOpsSotEotCtrlEntityList =
        new ArrayList<MdtAcOpsSotEotCtrlEntity>();
    mdtAcOpsSotEotCtrlEntityList =
        (List<MdtAcOpsSotEotCtrlEntity>) adminBeanRemote.retrieveACOpsSotEot();
    log.debug("<<<<<<<<<<<<<<-----mdtAcOpsSotEotCtrlEntityList------->>>>>>>>>>>>>>" +
        mdtAcOpsSotEotCtrlEntityList);

    sysCntrlCustList =
        (List<CasSysctrlCustParamEntity>) adminBeanRemote.retrieveCustomerParameters();
    log.debug("<<<<<<<<<<<<<<-----sysCntrlCustList------->>>>>>>>>>>>>>" + sysCntrlCustList);

    if (mdtAcOpsSotEotCtrlEntityList != null && mdtAcOpsSotEotCtrlEntityList.size() == 0) {
      if (sysCntrlServicesList != null && sysCntrlServicesList.size() > 0) {
        //POPULATE INPUT SERVICES
        for (CasSysctrlServicesEntity casSysctrlServicesEntity : sysCntrlServicesList) {
          for (CasSysctrlCustParamEntity casSysctrlCustParamEntity : sysCntrlCustList) {
            if (casSysctrlServicesEntity.getServiceIdIn() != null) {
              log.debug("SERVICE ID IN ~~~~~ :" + casSysctrlServicesEntity.getServiceIdIn());
              log.debug("MEMBER ~~~~~ :" + casSysctrlCustParamEntity.getInstId());

              MdtAcOpsSotEotCtrlEntity mdtAcOpsSotEotCtrlEntity = new MdtAcOpsSotEotCtrlEntity();
              MdtAcOpsSotEotCtrlPK mdtAcOpsSotEotCtrlPK = new MdtAcOpsSotEotCtrlPK();
              mdtAcOpsSotEotCtrlPK.setInstId(casSysctrlCustParamEntity.getInstId());
              mdtAcOpsSotEotCtrlPK.setServiceId(casSysctrlServicesEntity.getServiceIdIn());
              mdtAcOpsSotEotCtrlEntity.setMdtAcOpsSotEotCtrlPK(mdtAcOpsSotEotCtrlPK);
              mdtAcOpsSotEotCtrlEntity.setEotIn("N");
              mdtAcOpsSotEotCtrlEntity.setSotIn("N");
              mdtAcOpsSotEotCtrlEntity.setEotOut("N");
              mdtAcOpsSotEotCtrlEntity.setSotOut("N");

              saved = adminBeanRemote.createSotEot(mdtAcOpsSotEotCtrlEntity);
            }
          }
        }

        //POPULATE OUTPUT SERVICES
        for (CasSysctrlCustParamEntity casSysctrlCustParamEntity : sysCntrlCustList) {
          for (CasSysctrlServicesEntity casSysctrlServicesEntity : sysCntrlServicesList) {
            if (casSysctrlServicesEntity.getServiceIdOut() != null) {
              log.debug("SERVICE ID OUT ~~~~~ :" + casSysctrlServicesEntity.getServiceIdOut());
              log.debug("MEMBER ~~~~~ :" + casSysctrlCustParamEntity.getInstId());

              MdtAcOpsSotEotCtrlEntity mdtAcOpsSotEotCtrlEntity = new MdtAcOpsSotEotCtrlEntity();
              MdtAcOpsSotEotCtrlPK mdtAcOpsSotEotCtrlPK = new MdtAcOpsSotEotCtrlPK();
              mdtAcOpsSotEotCtrlPK.setInstId(casSysctrlCustParamEntity.getInstId());
              mdtAcOpsSotEotCtrlPK.setServiceId(casSysctrlServicesEntity.getServiceIdOut());
              mdtAcOpsSotEotCtrlEntity.setMdtAcOpsSotEotCtrlPK(mdtAcOpsSotEotCtrlPK);
              mdtAcOpsSotEotCtrlEntity.setEotIn("N");
              mdtAcOpsSotEotCtrlEntity.setSotIn("N");
              mdtAcOpsSotEotCtrlEntity.setEotOut("N");
              mdtAcOpsSotEotCtrlEntity.setSotOut("N");

              saved = adminBeanRemote.createSotEot(mdtAcOpsSotEotCtrlEntity);
            }
          }
        }
      }
    }

    if (saved) {
      log.info("Ops Ac SOT EOT   Table has been populated...");
    }
    return saved;
  }

  public boolean populatesOpsServices() {
    boolean saved = false;
    sysCntrlServicesList = new ArrayList<CasSysctrlServicesEntity>();// list

    sysCntrlServicesList =
        (List<CasSysctrlServicesEntity>) adminBeanRemote.retrieveServiceControl();

    List<MdtOpsServicesEntity> opsServicesList = new ArrayList<MdtOpsServicesEntity>();

    //opsServicesList = (List<MdtOpsServicesEntity>) adminBeanRemote.retrieveOpsServicesTime();
    opsServicesList = (List<MdtOpsServicesEntity>) adminBeanRemote.retrieveOpsServicesTime();

    if (opsServicesList.size() == 0) {

      if (sysCntrlServicesList.size() > 0) {

        for (CasSysctrlServicesEntity syscntrlservice : sysCntrlServicesList) {
          if (syscntrlservice.getActiveInd().equalsIgnoreCase("Y")) {
            MdtOpsServicesEntity opsServiceEntity = new MdtOpsServicesEntity();

            opsServiceEntity.setServiceIdIn(syscntrlservice.getServiceIdIn());
            opsServiceEntity.setServiceIdInDesc(syscntrlservice.getServiceIdInDesc());
            opsServiceEntity.setServiceIdOut(syscntrlservice.getServiceIdOut());
            opsServiceEntity.setServiceIdOutDesc(syscntrlservice.getServiceIdOutDesc());
            opsServiceEntity.setActiveInd(syscntrlservice.getActiveInd());
            opsServiceEntity.setCreatedBy(systemName);
            opsServiceEntity.setCreatedDate(new Date());
            opsServiceEntity.setModifiedBy(systemName);
            opsServiceEntity.setModifiedDate(new Date());
            opsServiceEntity.setProcessDate(new Date());
            opsServiceEntity.setProcessStatus("P");
            opsServiceEntity.setMsgTypeId(syscntrlservice.getMsgTypeId());
            saved = adminBeanRemote.createOpsServices(opsServiceEntity);
          }
        }
      }
    } else {
      if (opsServicesList.size() > 0) {
        log.info("The table still has data ,please run End of day to clean it out");
      }
    }


    if (saved) {
      log.info("Ops Services Table has been populated...");
    }

    return saved;
  }

  public boolean populateOpsScheduler() {
    boolean saved = false;

    List<CasSysctrlSchedulerEntity> sysctrlSchedulerList =
        new ArrayList<CasSysctrlSchedulerEntity>();

    sysctrlSchedulerList =
        (List<CasSysctrlSchedulerEntity>) adminBeanRemote.retrieveSysCntrlScheduler();

    List<MdtAcOpsSchedulerEntity> opsSchedulerList = new ArrayList<MdtAcOpsSchedulerEntity>();
    opsSchedulerList = (List<MdtAcOpsSchedulerEntity>) adminBeanRemote.retrieveOpsScheduler();

    if (opsSchedulerList != null && opsSchedulerList.size() == 0) {
      if (sysctrlSchedulerList != null && sysctrlSchedulerList.size() > 0) {
        for (CasSysctrlSchedulerEntity syscntrSchedulerEntity : sysctrlSchedulerList) {
          MdtAcOpsSchedulerEntity mdtAcOpsSchedulerEntity = new MdtAcOpsSchedulerEntity();
          mdtAcOpsSchedulerEntity.setSchedulerKey(syscntrSchedulerEntity.getSchedulerKey());
          mdtAcOpsSchedulerEntity.setSchedulerName(syscntrSchedulerEntity.getSchedulerName());
          mdtAcOpsSchedulerEntity.setRescheduleTime(syscntrSchedulerEntity.getRescheduleTime());

			if (syscntrSchedulerEntity.getSchedulerName().equalsIgnoreCase("Start Of Day")) {
				mdtAcOpsSchedulerEntity.setActiveInd("Y");
			} else {
				mdtAcOpsSchedulerEntity.setActiveInd("N");
			}

          mdtAcOpsSchedulerEntity.setCreatedBy(systemName);
          mdtAcOpsSchedulerEntity.setModifiedBy(systemName);
          mdtAcOpsSchedulerEntity.setCreatedDate(new Date());
          mdtAcOpsSchedulerEntity.setModifiedDate(new Date());

          saved = adminBeanRemote.createOpsScheduler(mdtAcOpsSchedulerEntity);
          log.info("OPS SCH SAVED---> " + saved);
        }
      }
    }

    if (saved) {
      log.info("Ops Scheduler Table  has been populated...");
    }

    return saved;
  }

  public boolean populateBillingCntrlInfo() {
    boolean saved = false;

    ObsSystemBillingCtrlsEntity obsSystemBillingCtrlsEntity = new ObsSystemBillingCtrlsEntity();
    ObsSystemBillingCtrlsPK obsSystemBillingCtrlsPK = new ObsSystemBillingCtrlsPK();

    obsSystemBillingCtrlsPK.setProcessDate(currDate);
    obsSystemBillingCtrlsPK.setSystemName("MANDATES");
    obsSystemBillingCtrlsEntity.setObsSystemBillingCtrlsPK(obsSystemBillingCtrlsPK);
    obsSystemBillingCtrlsEntity.setProcessStatus("A");
    obsSystemBillingCtrlsEntity.setCreatedBy(systemName);
    obsSystemBillingCtrlsEntity.setModifiedBy(systemName);
    obsSystemBillingCtrlsEntity.setCreatedDate(new Date());
    obsSystemBillingCtrlsEntity.setModifiedDate(new Date());

    saved = adminBeanRemote.createBillingCtrls(obsSystemBillingCtrlsEntity);
    log.info("OBS BillingCntrl SAVED---> " + saved);

    return saved;
  }

  public boolean populateReportSeqNr() {
    contextAdminBeanCheck();

    boolean saved = false;
    List<MdtCnfgReportNamesEntity> reportNamesList =
        new ArrayList<MdtCnfgReportNamesEntity>();// list
    reportNamesList = (List<MdtCnfgReportNamesEntity>) adminBeanRemote.retrieveActiveReportNr();
    log.debug("reportNamesList --> " + reportNamesList);
    List<MdtOpsRepSeqNrEntity> opsReportSeqNrList = new ArrayList<MdtOpsRepSeqNrEntity>();
    opsReportSeqNrList = (List<MdtOpsRepSeqNrEntity>) adminBeanRemote.retrieveOpsReportSeqNr();
    log.debug("opsReportSeqNrList --> " + opsReportSeqNrList);
    if (opsReportSeqNrList.size() == 0) {
      log.debug("INSIDE FIRST IF!!!!");
      if (reportNamesList.size() > 0) {
        log.debug("INSIDE SECOND  IF!!!!");
        for (MdtCnfgReportNamesEntity mdtCnfgReportNamesEntity : reportNamesList) {

          log.debug("Current Report Name ==> " + mdtCnfgReportNamesEntity.getReportNr());
          //PASA Report
          if (mdtCnfgReportNamesEntity.getReportNr().startsWith("PS")) {
            MdtOpsRepSeqNrEntity mdtOpsRepSeqNr = new MdtOpsRepSeqNrEntity();
            MdtOpsRepSeqNrPK opsReportSeqPk = new MdtOpsRepSeqNrPK();
            //						try
            //						{
            //							String cDate = sdf1.format(currentDate);
            //							currDate = sdf1.parse(cDate);
            //						}
            //						catch (ParseException pe)
            //						{
            //							pe.printStackTrace();
            //						}
            log.debug("Inside If of 1st For");
            opsReportSeqPk.setMemberNo("0000");
            opsReportSeqPk.setReportNo(mdtCnfgReportNamesEntity.getReportNr());
            //						opsReportSeqPk.setMemberNo(mdtSysctrlCustParamEntity.getInstId
			  //						());
            opsReportSeqPk.setProcessDate(currDate);
            log.debug("currDate 1 ----> " + currDate);
            mdtOpsRepSeqNr.setMdtOpsRepSeqNrEntityPK(opsReportSeqPk);
            //opsRefSeqNr.setServiceId(syscntrServiceEntity.getServiceIdOut());
            mdtOpsRepSeqNr.setCreatedDate(new Date());
            mdtOpsRepSeqNr.setCreatedBy(systemName);
            mdtOpsRepSeqNr.setLastSeqNo(seqNo);
            mdtOpsRepSeqNr.setModifiedBy(systemName);
            mdtOpsRepSeqNr.setModifiedDate(new Date());
            saved = adminBeanRemote.createOpsReportSeqNr(mdtOpsRepSeqNr);
          } else {
            //Bank Report
            log.debug("Inside else");
            //						sysCntrlCustList = adminBeanRemote
			  //						.retrieveSysCustomerParameters();
            for (CasSysctrlCustParamEntity casSysctrlCustParamEntity : sysCntrlCustList) {
              //		                	try
              //							{
              //								String cDate = sdf1.format(currentDate);
              //								currDate = sdf1.parse(cDate);
              //							}
              //							catch (ParseException pe)
              //							{
              //								pe.printStackTrace();
              //							}
              log.debug("Inside 2nd for");
              MdtOpsRepSeqNrEntity opsReportSeqNr = new MdtOpsRepSeqNrEntity();
              MdtOpsRepSeqNrPK opsReportSeqPkEntity = new MdtOpsRepSeqNrPK();

              opsReportSeqPkEntity.setReportNo(mdtCnfgReportNamesEntity.getReportNr());
              opsReportSeqPkEntity.setMemberNo(casSysctrlCustParamEntity.getInstId());
              opsReportSeqPkEntity.setProcessDate(currDate);
              log.debug("currDate 2 ----> " + currDate);
              opsReportSeqNr.setMdtOpsRepSeqNrEntityPK(opsReportSeqPkEntity);
              //opsRefSeqNr.setServiceId(syscntrServiceEntity.getServiceIdOut());
              opsReportSeqNr.setCreatedDate(new Date());
              opsReportSeqNr.setCreatedBy(systemName);
              opsReportSeqNr.setLastSeqNo(seqNo);
              opsReportSeqNr.setModifiedBy(systemName);
              opsReportSeqNr.setModifiedDate(new Date());
              saved = adminBeanRemote.createOpsReportSeqNr(opsReportSeqNr);
            }
          }
        }
      }
    }
    if (saved) {
      log.info("Ops Report Seq Number Table  has been populated...");
    }
    return saved;
  }


  public boolean populateOpsLastExtractTime() {
    boolean saved = false;
    sysCntrlServicesList = new ArrayList<CasSysctrlServicesEntity>();// list

    sysCntrlServicesList =
        (List<CasSysctrlServicesEntity>) adminBeanRemote.retrieveServiceControl();

    List<MdtOpsLastExtractTimesEntity> opsLastExtractTimesList =
        new ArrayList<MdtOpsLastExtractTimesEntity>();


    opsLastExtractTimesList =
        (List<MdtOpsLastExtractTimesEntity>) adminBeanRemote.retrieveLastExtractTime();

    if (opsLastExtractTimesList.size() == 0) {

      if (sysCntrlServicesList.size() > 0) {

        for (CasSysctrlServicesEntity syscntrlservice : sysCntrlServicesList) {
          if (syscntrlservice.getActiveInd().equalsIgnoreCase("Y")) {
            MdtOpsLastExtractTimesEntity mdtOpsLastExtractTimes =
                new MdtOpsLastExtractTimesEntity();

            mdtOpsLastExtractTimes.setServiceIdOut(syscntrlservice.getServiceIdOut());
            mdtOpsLastExtractTimes.setFileTransactionLimit(new BigInteger(transactionLimit));
            mdtOpsLastExtractTimes.setLastExtractTime(new Date());
            mdtOpsLastExtractTimes.setCreatedBy(systemName);
            mdtOpsLastExtractTimes.setCreatedDate(new Date());
            mdtOpsLastExtractTimes.setModifiedBy(systemName);
            mdtOpsLastExtractTimes.setModifiedDate(new Date());

            saved = adminBeanRemote.createOpLastExtractTimeEntiy(mdtOpsLastExtractTimes);
          }
        }
      }
    }
    if (saved) {
      log.info("Ops Last Extract Times Table has been populated...");
    }

    return saved;
  }


  private boolean populateFileSizeLimit() {


    boolean saved = false;

    List<CasSysctrlFileSizeLimitEntity> mdtSysctrlFileSizeLimitEntityList =
        new ArrayList<CasSysctrlFileSizeLimitEntity>();


    mdtSysctrlFileSizeLimitEntityList =
        (List<CasSysctrlFileSizeLimitEntity>) adminBeanRemote.retrieveSysctrlFileSizeLimit();

    List<MdtAcOpsFileSizeLimitEntity> mdtAcOpsFileSizeLimitEntityList =
        new ArrayList<MdtAcOpsFileSizeLimitEntity>();


    mdtAcOpsFileSizeLimitEntityList =
        (List<MdtAcOpsFileSizeLimitEntity>) adminBeanRemote.retrieveOpsFileSizeLimit();

    log.debug("The file size limit has ##############################" +
        mdtSysctrlFileSizeLimitEntityList);
    if (mdtAcOpsFileSizeLimitEntityList != null && mdtAcOpsFileSizeLimitEntityList.size() == 0) {

      if (mdtSysctrlFileSizeLimitEntityList.size() > 0) {

        for (CasSysctrlFileSizeLimitEntity casSysctrlFileSizeLimitEntity :
				mdtSysctrlFileSizeLimitEntityList) {

          MdtAcOpsFileSizeLimitEntity mdtAcOpsFileSizeLimitEntity =
              new MdtAcOpsFileSizeLimitEntity();
          MdtAcOpsFileSizeLimitPK mdtAcOpsFileSizeLimitPK = new MdtAcOpsFileSizeLimitPK();

          mdtAcOpsFileSizeLimitPK.setMemberId(
              casSysctrlFileSizeLimitEntity.getMdtSysctrlFileSizeLimitPK().getMemberId());
          mdtAcOpsFileSizeLimitPK.setSubService(
              casSysctrlFileSizeLimitEntity.getMdtSysctrlFileSizeLimitPK().getSubService());
          mdtAcOpsFileSizeLimitEntity.setProcessDate(new Date());
          mdtAcOpsFileSizeLimitEntity.setLimit(casSysctrlFileSizeLimitEntity.getLimit());
          mdtAcOpsFileSizeLimitEntity.setDirection(casSysctrlFileSizeLimitEntity.getDirection());
          mdtAcOpsFileSizeLimitEntity.setActiveId(casSysctrlFileSizeLimitEntity.getActiveId());
          mdtAcOpsFileSizeLimitEntity.setMdtAcOpsFileSizeLimitPK(mdtAcOpsFileSizeLimitPK);

          saved = adminBeanRemote.createOpsFileSizeLimit(mdtAcOpsFileSizeLimitEntity);
          log.debug("After saving we have the following data " + mdtAcOpsFileSizeLimitEntity);


        }
      }
    }

    if (saved) {
      log.info("Ac Ops File Size Table  has been populated...");
    }

    return saved;
  }


  public void updateSodRunInd() {

    CasSysctrlSysParamEntity mdtsysEnt = new CasSysctrlSysParamEntity();
    mdtsysEnt = (CasSysctrlSysParamEntity) adminBeanRemote.retrieveActiveSysParameter();
    if (mdtsysEnt != null) {
      mdtsysEnt.setSodRunInd("Y");
      mdtsysEnt.setEodRunInd("N");

      boolean saved = adminBeanRemote.updateSystemParameters(mdtsysEnt);

		if (saved) {
			log.info("SOD run indicator has been updated...");
		}
    }
  }

  public static boolean isWeekend(String ts) {

    int year = Integer.parseInt(ts.substring(0, 4));
    int month = Integer.parseInt(ts.substring(4, 6));
    int day = Integer.parseInt(ts.substring(6, 8));
    Calendar cal = new GregorianCalendar(year, month - 1, day);
    int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
    return (Calendar.SUNDAY == dayOfWeek || Calendar.SATURDAY == dayOfWeek);
  }

  public void updateActiveIndProcessControl(
      MdtOpsProcessControlsEntity mdtOpsProcessControlsEntity) {

    boolean dateCheck = false;
    String procDate = sdf.format(processingDate);
    dateCheck = isWeekend(procDate);

    sysCntrlCustList =
        (List<CasSysctrlCustParamEntity>) adminBeanRemote.retrieveCustomerParameters();


    sysCisBankEntityList = (List<SysCisBankEntity>) adminBeanRemote.retrieveSysCisBank();


    for (SysCisBankEntity sysbankEntity : sysCisBankEntityList) {

      for (CasSysctrlCustParamEntity custParaEntity : sysCntrlCustList) {
        if (sysbankEntity != null) {

          if (custParaEntity != null) {

            if (sysbankEntity.getMemberNo() == custParaEntity.getInstId()) {
              if (custParaEntity.getProcessDay().equals("6") ||
                  (custParaEntity.getProcessDay().equals("7")) ||
                  (custParaEntity.getProcessDay().equals("5")) && (dateCheck)) {

                mdtOpsProcessControlsEntity.setActiveInd("N");
                log.info("it is the weekend ,we cannot process");

                boolean saved =
                    adminBeanRemote.updateOpsProcessControls(mdtOpsProcessControlsEntity);

                if (saved) {
                  log.info("Active indicator updated");

                }

              }

            }
          }
        }
      }
    }

  }

  public Date calculateOneDayExtra() throws ParseException {
    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.DATE,
        +1); // to get 2 previous days------> this has changed to now being the next 2 days.
    Date oneDay = cal.getTime();
    return oneDay;
  }

  public boolean populateAllSOTFiles() {
    boolean saved = false;

    List<SysCisBankEntity> sysCisBankList = new ArrayList<SysCisBankEntity>();
    sysCisBankList = adminBeanRemote.retrieveSysCisBank();

    if (sysCisBankList != null && sysCisBankList.size() > 0 && sysCntrlServicesList != null &&
        sysCntrlServicesList.size() > 0) {
      for (SysCisBankEntity sysCisBankEntity : sysCisBankList) {
        String memberId = sysCisBankEntity.getMemberNo();
        String debtorInd = sysCisBankEntity.getAcDebtor();
        String creditorInd = sysCisBankEntity.getAcCreditor();

        log.info("memberId ==> " + memberId);
        log.info("creditorInd ==> " + creditorInd);
        log.info("debtorInd ==> " + debtorInd);

        for (CasSysctrlServicesEntity servicesEntity : sysCntrlServicesList) {
          String outService = servicesEntity.getServiceIdOut();
          log.info("outService ==> " + outService);

          //Output Debtor Services
          if (outService.equalsIgnoreCase(manotServ) || outService.equalsIgnoreCase(manomServ) ||
              outService.equalsIgnoreCase(mancoServ) ||
              outService.equalsIgnoreCase(manroServ) || outService.equalsIgnoreCase(sroutServ) ||
              outService.equalsIgnoreCase(st102Serv) ||
              outService.equalsIgnoreCase(st104Serv) || outService.equalsIgnoreCase(st106Serv) ||
              outService.equalsIgnoreCase(st008Serv) ||
              outService.equalsIgnoreCase(st994Serv)) {
            if (sysCisBankEntity.getAcDebtor().equalsIgnoreCase("Y")) {
              generateSOT(memberId, outService);
            }
          }

          //Output Creditor Services
          if (outService.equalsIgnoreCase(manocServ) || outService.equalsIgnoreCase(st103Serv) ||
              outService.equalsIgnoreCase(manrfServ) || outService.equalsIgnoreCase(spoutServ) ||
              outService.equalsIgnoreCase(st100Serv) || outService.equalsIgnoreCase(st105Serv) ||
              outService.equalsIgnoreCase(st007Serv) || outService.equalsIgnoreCase(mandcServ)) {
            if (sysCisBankEntity.getAcCreditor().equalsIgnoreCase("Y")) {
              generateSOT(memberId, outService);
            }
          }
        }
      }
      log.info("SOT File Generation Completed.....");
      return true;
    } else {
      return false;
    }
  }

  public void generateSOT(String memberNo, String service) {
    //Retrieve SOT/EOT Ind
    MdtAcOpsSotEotCtrlEntity mdtAcOpsSotEotCtrlEntity = new MdtAcOpsSotEotCtrlEntity();
    mdtAcOpsSotEotCtrlEntity =
        (MdtAcOpsSotEotCtrlEntity) serviceBeanRemote.retrieveSOTEOTCntrl(memberNo, service);
    log.debug("mdtAcOpsSotEotCtrlEntity: " + mdtAcOpsSotEotCtrlEntity);

    if (mdtAcOpsSotEotCtrlEntity != null) {
      try {
        if (mdtAcOpsSotEotCtrlEntity.getSotOut().equalsIgnoreCase(nonActiveInd)) {
          StartOfTransmissionExtract startOfTransmissionExtract =
              new StartOfTransmissionExtract(memberNo, service, "S");
          startOfTransmissionExtract.createStartOfTransmissionFile();

          mdtAcOpsSotEotCtrlEntity.setSotOut(activeInd);

          boolean updated = serviceBeanRemote.updateSOTEOTCntrl(mdtAcOpsSotEotCtrlEntity);
        }
      } catch (Exception e) {
        log.error(
            "Error generation SOT Files at SOD for bank " + memberNo + " and service: " + service +
                ". Error: " + e.getMessage());
        e.printStackTrace();
      }
    }
  }


  private static void contextAdminBeanCheck() {
    if (adminBeanRemote == null) {
      adminBeanRemote = Util.getAdminBean();
    }
  }


  private static void contextQuartzSchedulerBeanCheck() {
    if (quartzSchedulerBeanRemote == null) {
      quartzSchedulerBeanRemote = Util.getQuartzSchedulerBean();
    }
  }


  private static void contextServiceBeanRemoteCheck() {
    if (serviceBeanRemote == null) {
      serviceBeanRemote = Util.getServiceBean();
    }
  }


  private static void createNewTempFolder() throws IOException {
    File files = new File("/home/opsjava/Delivery/Mandates/Output/temp");
    if (!files.exists()) {
      if (files.mkdir()) {
        log.info("A temporary  folder has been created succesfully ");
      } else {
        log.info(
            "*****************Failed to create a new processing " +
					"directory!*************************");
      }
    }
  }

  private static void createNewProcessingFolder() throws IOException {
    File files = new File("/home/opsjava/Delivery/Mandates/Input/Processing");

    if (!files.exists()) {
		if (files.mkdir()) {
			log.info("Processing folder created successfully ************************");
		}
    } else {
      log.info("The folder already exists");
    }
  }

  private static void createArchiveOutPutFolder() throws IOException {
    File files = new File("/home/opsjava/Delivery/Mandates/Archive/Output");

    if (!files.exists()) {
		if (files.mkdir()) {
			log.info("Processing folder created successfully ************************");
		}
    } else {
      log.info("The folder already exists");
    }
  }


  private static void createArchiveInPutFolder() throws IOException {
    File files = new File("/home/opsjava/Delivery/Mandates/Archive/Input");

    if (!files.exists()) {
		if (files.mkdir()) {
			log.info("Processing folder created successfully ************************");
		}
    } else {
      log.info("The folder already exists");
    }
  }

  public void generatePHIReports() {

    try {
      // 2018-12-18: SalehaR: CHG-148253/144056 Run PHI Reports on Sunday Morning as of SOD
      // Amendment Report
      PasaMandateAmendmentReport pasaMandateAmendmentReport = new PasaMandateAmendmentReport();
      pasaMandateAmendmentReport.generateReport(true, null, null);

    } catch (FileNotFoundException nfe) {
      log.error("<FNFE>Error on generating PHI Reports :" + nfe.getMessage());
      nfe.printStackTrace();
    } catch (DocumentException de) {
      log.error("<DE> Error on generating PHI Reports :" + de.getMessage());
      de.printStackTrace();
    } catch (Exception ex) {
      log.error("<EX> Error on generating PHI Reports :" + ex.getMessage());
      ex.printStackTrace();
    }
  }
}