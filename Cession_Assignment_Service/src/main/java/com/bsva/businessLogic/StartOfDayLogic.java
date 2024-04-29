package com.bsva.businessLogic;

import com.bsva.FileWatcher;
import com.bsva.PropertyUtil;
import com.bsva.authcoll.singletable.validation.Validation_ST;
import com.bsva.delivery.StartOfTransmissionExtract;
import com.bsva.entities.CasOpsBillingCtrlsEntity;
import com.bsva.entities.CasSysctrlCustParamEntity;
import com.bsva.entities.CasSysctrlFileSizeLimitEntity;
import com.bsva.entities.CasSysctrlSchedulerEntity;
import com.bsva.entities.CasSysctrlServicesEntity;
import com.bsva.entities.CasSysctrlSlaTimesEntity;
import com.bsva.entities.CasSysctrlSysParamEntity;
import com.bsva.entities.CasOpsFileSizeLimitEntity;
import com.bsva.entities.CasOpsFileSizeLimitPK;
import com.bsva.entities.CasOpsProcessControlsEntity;
import com.bsva.entities.CasOpsSchedulerEntity;
import com.bsva.entities.CasOpsSotEotCtrlEntity;
import com.bsva.entities.CasOpsSotEotCtrlPK;
import com.bsva.entities.CasCnfgReportNamesEntity;
import com.bsva.entities.CasOpsCustParamEntity;
import com.bsva.entities.CasOpsLastExtractTimesEntity;
import com.bsva.entities.CasOpsRefSeqNrEntity;
import com.bsva.entities.CasOpsRefSeqNrPK;
import com.bsva.entities.CasOpsRepSeqNrEntity;
import com.bsva.entities.CasOpsRepSeqNrPK;
import com.bsva.entities.CasOpsServicesEntity;
import com.bsva.entities.CasOpsSlaTimesEntity;
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
  private String systemName = "CAMOWNER";
  List<CasSysctrlCustParamEntity> sysCntrlCustList = new ArrayList<CasSysctrlCustParamEntity>();
  FileWatcher fileWatcher = new FileWatcher();
  Date processingDate = null;
  List<SysCisBankEntity> sysCisBankEntityList = new ArrayList<SysCisBankEntity>();
  Date currentDate = new Date();
  CasSysctrlSysParamEntity casSysctrlSysParamEntity, casSysParamEntity;
  CasSysctrlSlaTimesEntity casSysctrlSlaTimesEntity;
  CasOpsProcessControlsEntity casOpsProcessControlsEntity = new CasOpsProcessControlsEntity();
  List<CasSysctrlFileSizeLimitEntity> casSysctrlFileSizeLimitEntityList =
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
  String carotServ, rcaotServ, st203Serv;
  String st200Serv, st202Serv, st204Serv, st105Serv, iamport, transactionLimit;
  String archiveday;


  public StartOfDayLogic() {
    try {
      propertyUtil = new PropertyUtil();
      this.activeInd = propertyUtil.getPropValue("ActiveInd");

      this.nonActiveInd = propertyUtil.getPropValue("NonActiveInd");
      this.carotServ = propertyUtil.getPropValue("Output.Pain010");
      this.rcaotServ = propertyUtil.getPropValue("Output.Pain012");
      this.st203Serv = propertyUtil.getPropValue("Output.Pacs002");

      this.st200Serv = propertyUtil.getPropValue("StatusRep.ST200");
      this.st202Serv = propertyUtil.getPropValue("StatusRep.ST202");
      this.st204Serv = propertyUtil.getPropValue("StatusRep.ST204");
      archiveday = propertyUtil.getPropValue("RESPONSE.PERIOD");
      iamport = propertyUtil.getPropValue("MDT.IAM_PORT");
      transactionLimit = propertyUtil.getPropValue("AC.FILE.TRANSACTION.LIMIT");

    } catch (Exception e) {
      log.error(" StartOfDayLogic - Could not find CessionAssignProperties.properties in classpath");
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
    casOpsProcessControlsEntity =
        (CasOpsProcessControlsEntity) adminBeanRemote.retrieveCisDownloadInd(currDate);

    if (casOpsProcessControlsEntity != null) {
      if (casOpsProcessControlsEntity.getCisDownloadInd() != null &&
          casOpsProcessControlsEntity.getCisDownloadInd().equalsIgnoreCase("N")) {
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
          CasOpsBillingCtrlsEntity billingCtrlsEntity =
              (CasOpsBillingCtrlsEntity) serviceBeanRemote.retrieveBillingCtrls("BILLING");
          if (billingCtrlsEntity != null) {
            billingCtrlsEntity.setBillingWindow(Short.valueOf("0"));
            serviceBeanRemote.saveBillingCntrl(billingCtrlsEntity);
            log.info("CasOpsBillingCtrls populated");
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
    List<CasOpsCustParamEntity> opsCustParam = new ArrayList<CasOpsCustParamEntity>();
    opsCustParam = (List<CasOpsCustParamEntity>) adminBeanRemote.retrieveOpsCustParamTime();


    if (opsCustParam.size() == 0) {
      if (sysCntrlCustList.size() > 0) {
        for (CasSysctrlCustParamEntity syscntrCustEntity : sysCntrlCustList) {
          CasOpsCustParamEntity opsEntity = new CasOpsCustParamEntity();
          opsEntity.setInstId(syscntrCustEntity.getInstId());
          opsEntity.setCasaAmdXsdNs(syscntrCustEntity.getCasaAmdXsdNs());
          opsEntity.setCasaAmdLstSeq(seqNo);
          opsEntity.setCasaAmdLastFileNr(seqNo);
          opsEntity.setCasaAccpXsdNs(syscntrCustEntity.getCasaAccpXsdNs());
          opsEntity.setCasaAccpLstSeq(seqNo);
          opsEntity.setCasaAccpLastFileNr(seqNo);
          opsEntity.setActiveInd(syscntrCustEntity.getActiveInd());
          opsEntity.setCreatedBy(systemName);
          opsEntity.setCreatedDate(new Date());
          opsEntity.setCasaStatusRepXsdNs(syscntrCustEntity.getCasaStatusRepXsdNs());
          opsEntity.setCasaStatusRepLstSeq(seqNo);
          opsEntity.setCasaStatusRepLastFileNr(seqNo);
          opsEntity.setCasaConfirmXsdNs(syscntrCustEntity.getCasaConfirmXsdNs());
          opsEntity.setCasaConfirmLstSeq(seqNo);
          opsEntity.setCasaConfirmLstFileNr(seqNo);

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

    //Date time01 = parser.parse(casSysctrlSlaTimesEntity.getStartTime());
    Date time02 = parser.parse(casSysctrlSlaTimesEntity.getEndTime());


    casSysParamEntity = new CasSysctrlSysParamEntity();

    processingDate = currentDate;
    responsePeriodVal = String.valueOf(archiveday);

    if (userDate.before(time02)) {
      casSysParamEntity.setProcessDate(processingDate);
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
        casSysParamEntity.setProcessDate(currDate);
        log.info("currDate-->" + currDate);
      }
    }

    //casSysParamEntity.setProcessDate(currDate);
    casSysParamEntity.setSysName("CESS_ASSIGN");
    casSysParamEntity.setDefCurr("ZAR");
    short inactiveDuration = 1;
    BigInteger archivePeriod = new BigInteger(archivePeriodVal);
    casSysParamEntity.setInactiveDuration(inactiveDuration);
    casSysParamEntity.setSysType("AC");
    casSysParamEntity.setArchivePeriod(archivePeriod);
    casSysParamEntity.setActiveInd("Y");
    casSysParamEntity.setCreatedBy("");
    casSysParamEntity.setInBalanceInd("N");
    casSysParamEntity.setCreatedDate(currentDate);
    casSysParamEntity.setModifiedBy(null);
    casSysParamEntity.setModifiedDate(null);
    casSysParamEntity.setSodRunInd("Y");
    casSysParamEntity.setEodRunInd("N");
    casSysParamEntity.setNextMondayHolInd("Y");
    casSysParamEntity.setEasterDaysInd("Y");
    casSysParamEntity.setSysCloseRunInd("N");
    casSysParamEntity.setCisDwnldInd("Y");
    casSysParamEntity.setCisDwnldDate(currDate);
    casSysParamEntity.setResponsePeriod(Short.valueOf(responsePeriodVal));
    casSysParamEntity.setIamPort(Short.valueOf(iamport));
    casSysParamEntity.setFileTransactionLimit(new BigInteger(transactionLimit));


    log.info("casSysParamEntity" + casSysParamEntity);
    saved = adminBeanRemote.createSysParameters(casSysParamEntity);
    log.info("the saved information is #####################" + saved);

    if (saved) {
      log.info("System parameters has been populated ....");
    } else {
      if (saved && casSysParamEntity.getActiveInd().equalsIgnoreCase("Y") &&
          casSysParamEntity.getProcessDate().equals(currentDate)) {
        log.info("The Table has been populated for the day *****************");
      }
    }
    return saved;
  }

//	public boolean populatesOpsCronTable() 
//	{
//		boolean saved = false;
//
//		List<MdtSysctrlCronEntity> casSysctrlCronEntityList = new
//		ArrayList<MdtSysctrlCronEntity>();
//		casSysctrlCronEntityList = (List<MdtSysctrlCronEntity>) adminBeanRemote.retrieveCronTime();
//
//		List<MdtOpsCronEntity> casOpsCronEntityList = new ArrayList<MdtOpsCronEntity>();
//		casOpsCronEntityList = (List<MdtOpsCronEntity>) adminBeanRemote.retrieveOpsCronTime();
//
//		if (casOpsCronEntityList.size() == 0) {
//			if (casSysctrlCronEntityList.size() > 0) {
//				for (MdtSysctrlCronEntity casSysctrlCronEntity : casSysctrlCronEntityList) {
//					MdtOpsCronEntity casOpsCronEntity = new MdtOpsCronEntity();
//
//					casOpsCronEntity.setActiveInd(casSysctrlCronEntity.getActiveInd());
//					casOpsCronEntity.setCreatedBy(casSysctrlCronEntity.getCreatedBy());
//					casOpsCronEntity.setCreatedDate(casSysctrlCronEntity.getCreatedDate());
//					casOpsCronEntity.setCronTime(casSysctrlCronEntity.getCronTime());
//					casOpsCronEntity.setModifiedBy(casSysctrlCronEntity.getModifiedBy());
//					casOpsCronEntity.setModifiedDate(casSysctrlCronEntity.getModifiedDate());
//					casOpsCronEntity.setProcessName(casSysctrlCronEntity.getProcessName());
//					saved = adminBeanRemote.createOpsCron(casOpsCronEntity);
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

    List<CasSysctrlSlaTimesEntity> casSysctrlSlaTimesEntityList =
        new ArrayList<CasSysctrlSlaTimesEntity>();
    casSysctrlSlaTimesEntityList =
        (List<CasSysctrlSlaTimesEntity>) adminBeanRemote.retrieveSlaTime();

    List<CasOpsSlaTimesEntity> casOpsSlaTimesEntityList = new ArrayList<CasOpsSlaTimesEntity>();

    casOpsSlaTimesEntityList = (List<CasOpsSlaTimesEntity>) adminBeanRemote.retrieveOpsSlaTime();
    log.debug("The sla times has ##############################" + casSysctrlSlaTimesEntityList);
    if (casOpsSlaTimesEntityList != null && casOpsSlaTimesEntityList.size() == 0) {
      if (casSysctrlSlaTimesEntityList.size() > 0) {
        for (CasSysctrlSlaTimesEntity casSysctrlSlaTimesEntity : casSysctrlSlaTimesEntityList) {

          CasOpsSlaTimesEntity casOpsSlaTimesEntity = new CasOpsSlaTimesEntity();

          casOpsSlaTimesEntity.setEndTime(casSysctrlSlaTimesEntity.getEndTime());
          casOpsSlaTimesEntity.setService(casSysctrlSlaTimesEntity.getService());
          casOpsSlaTimesEntity.setStartTime(casSysctrlSlaTimesEntity.getStartTime());
          saved = adminBeanRemote.createOpsSlaTimes(casOpsSlaTimesEntity);
          log.debug("After saving we have the following data " + casOpsSlaTimesEntity);
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
    List<CasOpsRefSeqNrEntity> opsRefSeqNrList = new ArrayList<CasOpsRefSeqNrEntity>();
    opsRefSeqNrList = (List<CasOpsRefSeqNrEntity>) adminBeanRemote.retrieveOpsRefSeqNr();

    if (opsRefSeqNrList.size() == 0) {
      if (sysCntrlServicesList.size() > 0) {
        for (CasSysctrlServicesEntity syscntrServiceEntity : sysCntrlServicesList) {
          for (CasSysctrlCustParamEntity casSysctrlCustParamEntity : sysCntrlCustList) {
            CasOpsRefSeqNrEntity opsRefSeqNr = new CasOpsRefSeqNrEntity();
            CasOpsRefSeqNrPK opsRefSeqPkEntity = new CasOpsRefSeqNrPK();

            opsRefSeqPkEntity.setServiceId(syscntrServiceEntity.getServiceIdOut());
            opsRefSeqPkEntity.setMemberNo(casSysctrlCustParamEntity.getInstId());
            opsRefSeqNr.setCasOpsRefSeqNrPK(opsRefSeqPkEntity);
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

			casSysctrlCustParamEntity = new MdtSysctrlCustParamEntity();
		casSysctrlCustParamEntity.setInstId(sysCisBankEntity.getMemberNo());
		saved =adminBeanRemote.createCustParameters(casSysctrlCustParamEntity);

	}
	 */
//
//	public boolean populatePublicHoliday ()
//	{
//		boolean saved = false;
//		List<MdtSysctrlPubholEntity> casSysctrlPubholEntityList = new
//		ArrayList<MdtSysctrlPubholEntity>();
//		casSysctrlPubholEntityList = adminBeanRemote.retrievePublicHoliday();
//
//
//		List<MdtAcOpsPubholEntity> casAcOpsPubholEntityList = new
//		ArrayList<MdtAcOpsPubholEntity>();
//		casAcOpsPubholEntityList = adminBeanRemote.retrieveSysPubHoliday();
//
//		if (casAcOpsPubholEntityList.size()==0)
//		{
//			if (casSysctrlPubholEntityList.size()>0)
//			{
//				for (MdtSysctrlPubholEntity  casSysctrlPubholEntity :casSysctrlPubholEntityList )
//				{
//					MdtAcOpsPubholEntity  casAcOpsPubholEntity = new MdtAcOpsPubholEntity();
//
//					casAcOpsPubholEntity.setActiveInd(casSysctrlPubholEntity.getActiveInd());
//					casAcOpsPubholEntity.setPubHolDate(casSysctrlPubholEntity.getPubHolDate());
//					casAcOpsPubholEntity.setPubHolidayDesc(casSysctrlPubholEntity
//					.getPubHolidayDesc());
//					saved =adminBeanRemote.createAcOpsPublicNHoliday(casAcOpsPubholEntity);
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

    List<CasOpsSotEotCtrlEntity> casOpsSotEotCtrlEntityList =
        new ArrayList<CasOpsSotEotCtrlEntity>();
    casOpsSotEotCtrlEntityList =
        (List<CasOpsSotEotCtrlEntity>) adminBeanRemote.retrieveACOpsSotEot();
    log.debug("<<<<<<<<<<<<<<-----casAcOpsSotEotCtrlEntityList------->>>>>>>>>>>>>>" +
        casOpsSotEotCtrlEntityList);

    sysCntrlCustList =
        (List<CasSysctrlCustParamEntity>) adminBeanRemote.retrieveCustomerParameters();
    log.debug("<<<<<<<<<<<<<<-----sysCntrlCustList------->>>>>>>>>>>>>>" + sysCntrlCustList);

    if (casOpsSotEotCtrlEntityList != null && casOpsSotEotCtrlEntityList.size() == 0) {
      if (sysCntrlServicesList != null && sysCntrlServicesList.size() > 0) {
        //POPULATE INPUT SERVICES
        for (CasSysctrlServicesEntity casSysctrlServicesEntity : sysCntrlServicesList) {
          for (CasSysctrlCustParamEntity casSysctrlCustParamEntity : sysCntrlCustList) {
            if (casSysctrlServicesEntity.getServiceIdIn() != null) {
              log.debug("SERVICE ID IN ~~~~~ :" + casSysctrlServicesEntity.getServiceIdIn());
              log.debug("MEMBER ~~~~~ :" + casSysctrlCustParamEntity.getInstId());

              CasOpsSotEotCtrlEntity casOpsSotEotCtrlEntity = new CasOpsSotEotCtrlEntity();
              CasOpsSotEotCtrlPK casOpsSotEotCtrlPK = new CasOpsSotEotCtrlPK();
              casOpsSotEotCtrlPK.setInstId(casSysctrlCustParamEntity.getInstId());
              casOpsSotEotCtrlPK.setServiceId(casSysctrlServicesEntity.getServiceIdIn());
              casOpsSotEotCtrlEntity.setCasOpsSotEotCtrlPK(casOpsSotEotCtrlPK);
              casOpsSotEotCtrlEntity.setEotIn("N");
              casOpsSotEotCtrlEntity.setSotIn("N");
              casOpsSotEotCtrlEntity.setEotOut("N");
              casOpsSotEotCtrlEntity.setSotOut("N");

              saved = adminBeanRemote.createSotEot(casOpsSotEotCtrlEntity);
            }
          }
        }

        //POPULATE OUTPUT SERVICES
        for (CasSysctrlCustParamEntity casSysctrlCustParamEntity : sysCntrlCustList) {
          for (CasSysctrlServicesEntity casSysctrlServicesEntity : sysCntrlServicesList) {
            if (casSysctrlServicesEntity.getServiceIdOut() != null) {
              log.debug("SERVICE ID OUT ~~~~~ :" + casSysctrlServicesEntity.getServiceIdOut());
              log.debug("MEMBER ~~~~~ :" + casSysctrlCustParamEntity.getInstId());

              CasOpsSotEotCtrlEntity casOpsSotEotCtrlEntity = new CasOpsSotEotCtrlEntity();
              CasOpsSotEotCtrlPK casOpsSotEotCtrlPK = new CasOpsSotEotCtrlPK();
              casOpsSotEotCtrlPK.setInstId(casSysctrlCustParamEntity.getInstId());
              casOpsSotEotCtrlPK.setServiceId(casSysctrlServicesEntity.getServiceIdOut());
              casOpsSotEotCtrlEntity.setCasOpsSotEotCtrlPK(casOpsSotEotCtrlPK);
              casOpsSotEotCtrlEntity.setEotIn("N");
              casOpsSotEotCtrlEntity.setSotIn("N");
              casOpsSotEotCtrlEntity.setEotOut("N");
              casOpsSotEotCtrlEntity.setSotOut("N");

              saved = adminBeanRemote.createSotEot(casOpsSotEotCtrlEntity);
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

    List<CasOpsServicesEntity> opsServicesList = new ArrayList<CasOpsServicesEntity>();

    //opsServicesList = (List<MdtOpsServicesEntity>) adminBeanRemote.retrieveOpsServicesTime();
    opsServicesList = (List<CasOpsServicesEntity>) adminBeanRemote.retrieveOpsServicesTime();

    if (opsServicesList.size() == 0) {

      if (sysCntrlServicesList.size() > 0) {

        for (CasSysctrlServicesEntity syscntrlservice : sysCntrlServicesList) {
          if (syscntrlservice.getActiveInd().equalsIgnoreCase("Y")) {
            CasOpsServicesEntity opsServiceEntity = new CasOpsServicesEntity();

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

    List<CasOpsSchedulerEntity> opsSchedulerList = new ArrayList<CasOpsSchedulerEntity>();
    opsSchedulerList = (List<CasOpsSchedulerEntity>) adminBeanRemote.retrieveOpsScheduler();

    if (opsSchedulerList != null && opsSchedulerList.size() == 0) {
      if (sysctrlSchedulerList != null && sysctrlSchedulerList.size() > 0) {
        for (CasSysctrlSchedulerEntity syscntrSchedulerEntity : sysctrlSchedulerList) {
          CasOpsSchedulerEntity casOpsSchedulerEntity = new CasOpsSchedulerEntity();
          casOpsSchedulerEntity.setSchedulerKey(syscntrSchedulerEntity.getSchedulerKey());
          casOpsSchedulerEntity.setSchedulerName(syscntrSchedulerEntity.getSchedulerName());
          casOpsSchedulerEntity.setRescheduleTime(syscntrSchedulerEntity.getRescheduleTime());

			if (syscntrSchedulerEntity.getSchedulerName().equalsIgnoreCase("Start Of Day")) {
				casOpsSchedulerEntity.setActiveInd("Y");
			} else {
				casOpsSchedulerEntity.setActiveInd("N");
			}

          casOpsSchedulerEntity.setCreatedBy(systemName);
          casOpsSchedulerEntity.setModifiedBy(systemName);
          casOpsSchedulerEntity.setCreatedDate(new Date());
          casOpsSchedulerEntity.setModifiedDate(new Date());

          saved = adminBeanRemote.createOpsScheduler(casOpsSchedulerEntity);
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
    obsSystemBillingCtrlsPK.setSystemName("CESSION_ASSIGN");
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
    List<CasCnfgReportNamesEntity> reportNamesList =
        new ArrayList<CasCnfgReportNamesEntity>();// list
    reportNamesList = (List<CasCnfgReportNamesEntity>) adminBeanRemote.retrieveActiveReportNr();
    log.debug("reportNamesList --> " + reportNamesList);
    List<CasOpsRepSeqNrEntity> opsReportSeqNrList = new ArrayList<CasOpsRepSeqNrEntity>();
    opsReportSeqNrList = (List<CasOpsRepSeqNrEntity>) adminBeanRemote.retrieveOpsReportSeqNr();
    log.debug("opsReportSeqNrList --> " + opsReportSeqNrList);
    if (opsReportSeqNrList.size() == 0) {
      log.debug("INSIDE FIRST IF!!!!");
      if (reportNamesList.size() > 0) {
        log.debug("INSIDE SECOND  IF!!!!");
        for (CasCnfgReportNamesEntity casCnfgReportNamesEntity : reportNamesList) {

          log.debug("Current Report Name ==> " + casCnfgReportNamesEntity.getReportNr());
          //PASA Report
          if (casCnfgReportNamesEntity.getReportNr().startsWith("PS")) {
            CasOpsRepSeqNrEntity casOpsRepSeqNr = new CasOpsRepSeqNrEntity();
            CasOpsRepSeqNrPK opsReportSeqPk = new CasOpsRepSeqNrPK();
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
            opsReportSeqPk.setReportNo(casCnfgReportNamesEntity.getReportNr());
            //						opsReportSeqPk.setMemberNo(casSysctrlCustParamEntity.getInstId
			  //						());
            opsReportSeqPk.setProcessDate(currDate);
            log.debug("currDate 1 ----> " + currDate);
            casOpsRepSeqNr.setCasOpsRepSeqNrEntityPK(opsReportSeqPk);
            //opsRefSeqNr.setServiceId(syscntrServiceEntity.getServiceIdOut());
            casOpsRepSeqNr.setCreatedDate(new Date());
            casOpsRepSeqNr.setCreatedBy(systemName);
            casOpsRepSeqNr.setLastSeqNo(seqNo);
            casOpsRepSeqNr.setModifiedBy(systemName);
            casOpsRepSeqNr.setModifiedDate(new Date());
            saved = adminBeanRemote.createOpsReportSeqNr(casOpsRepSeqNr);
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
              CasOpsRepSeqNrEntity opsReportSeqNr = new CasOpsRepSeqNrEntity();
              CasOpsRepSeqNrPK opsReportSeqPkEntity = new CasOpsRepSeqNrPK();

              opsReportSeqPkEntity.setReportNo(casCnfgReportNamesEntity.getReportNr());
              opsReportSeqPkEntity.setMemberNo(casSysctrlCustParamEntity.getInstId());
              opsReportSeqPkEntity.setProcessDate(currDate);
              log.debug("currDate 2 ----> " + currDate);
              opsReportSeqNr.setCasOpsRepSeqNrEntityPK(opsReportSeqPkEntity);
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

    List<CasOpsLastExtractTimesEntity> opsLastExtractTimesList =
        new ArrayList<CasOpsLastExtractTimesEntity>();


    opsLastExtractTimesList =
        (List<CasOpsLastExtractTimesEntity>) adminBeanRemote.retrieveLastExtractTime();

    if (opsLastExtractTimesList.size() == 0) {

      if (sysCntrlServicesList.size() > 0) {

        for (CasSysctrlServicesEntity syscntrlservice : sysCntrlServicesList) {
          if (syscntrlservice.getActiveInd().equalsIgnoreCase("Y")) {
            CasOpsLastExtractTimesEntity casOpsLastExtractTimes =
                new CasOpsLastExtractTimesEntity();

            casOpsLastExtractTimes.setServiceIdOut(syscntrlservice.getServiceIdOut());
            casOpsLastExtractTimes.setFileTransactionLimit(new BigInteger(transactionLimit));
            casOpsLastExtractTimes.setLastExtractTime(new Date());
            casOpsLastExtractTimes.setCreatedBy(systemName);
            casOpsLastExtractTimes.setCreatedDate(new Date());
            casOpsLastExtractTimes.setModifiedBy(systemName);
            casOpsLastExtractTimes.setModifiedDate(new Date());

            saved = adminBeanRemote.createOpLastExtractTimeEntiy(casOpsLastExtractTimes);
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

    List<CasSysctrlFileSizeLimitEntity> casSysctrlFileSizeLimitEntityList =
        new ArrayList<CasSysctrlFileSizeLimitEntity>();


    casSysctrlFileSizeLimitEntityList =
        (List<CasSysctrlFileSizeLimitEntity>) adminBeanRemote.retrieveSysctrlFileSizeLimit();

    List<CasOpsFileSizeLimitEntity> casOpsFileSizeLimitEntityList =
        new ArrayList<CasOpsFileSizeLimitEntity>();


    casOpsFileSizeLimitEntityList =
        (List<CasOpsFileSizeLimitEntity>) adminBeanRemote.retrieveOpsFileSizeLimit();

    log.debug("The file size limit has ##############################" +
        casSysctrlFileSizeLimitEntityList);
    if (casOpsFileSizeLimitEntityList != null && casOpsFileSizeLimitEntityList.size() == 0) {

      if (casSysctrlFileSizeLimitEntityList.size() > 0) {

        for (CasSysctrlFileSizeLimitEntity casSysctrlFileSizeLimitEntity :
				casSysctrlFileSizeLimitEntityList) {

          CasOpsFileSizeLimitEntity casOpsFileSizeLimitEntity =
              new CasOpsFileSizeLimitEntity();
          CasOpsFileSizeLimitPK casOpsFileSizeLimitPK = new CasOpsFileSizeLimitPK();

          casOpsFileSizeLimitPK.setMemberId(
              casSysctrlFileSizeLimitEntity.getMdtSysctrlFileSizeLimitPK().getMemberId());
          casOpsFileSizeLimitPK.setSubService(
              casSysctrlFileSizeLimitEntity.getMdtSysctrlFileSizeLimitPK().getSubService());
          casOpsFileSizeLimitEntity.setProcessDate(new Date());
          casOpsFileSizeLimitEntity.setLimit(casSysctrlFileSizeLimitEntity.getLimit());
          casOpsFileSizeLimitEntity.setDirection(casSysctrlFileSizeLimitEntity.getDirection());
          casOpsFileSizeLimitEntity.setActiveId(casSysctrlFileSizeLimitEntity.getActiveId());
          casOpsFileSizeLimitEntity.setCasOpsFileSizeLimitPK(casOpsFileSizeLimitPK);

          saved = adminBeanRemote.createOpsFileSizeLimit(casOpsFileSizeLimitEntity);
          log.debug("After saving we have the following data " + casOpsFileSizeLimitEntity);


        }
      }
    }

    if (saved) {
      log.info("Ac Ops File Size Table  has been populated...");
    }

    return saved;
  }


  public void updateSodRunInd() {

    CasSysctrlSysParamEntity cassysEnt = new CasSysctrlSysParamEntity();
    cassysEnt = (CasSysctrlSysParamEntity) adminBeanRemote.retrieveActiveSysParameter();
    if (cassysEnt != null) {
      cassysEnt.setSodRunInd("Y");
      cassysEnt.setEodRunInd("N");

      boolean saved = adminBeanRemote.updateSystemParameters(cassysEnt);

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
          if (outService.equalsIgnoreCase(carotServ) ||
              outService.equalsIgnoreCase(st202Serv) ||
              outService.equalsIgnoreCase(st204Serv)) {
            if (sysCisBankEntity.getAcDebtor().equalsIgnoreCase("Y")) {
              generateSOT(memberId, outService);
            }
          }

          //Output Creditor Services
          if (outService.equalsIgnoreCase(st200Serv) ||
              outService.equalsIgnoreCase(st203Serv) ||
              outService.equalsIgnoreCase(rcaotServ)) {
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
    CasOpsSotEotCtrlEntity casOpsSotEotCtrlEntity = new CasOpsSotEotCtrlEntity();
    casOpsSotEotCtrlEntity =
        (CasOpsSotEotCtrlEntity) serviceBeanRemote.retrieveSOTEOTCntrl(memberNo, service);
    log.debug("casAcOpsSotEotCtrlEntity: " + casOpsSotEotCtrlEntity);

    if (casOpsSotEotCtrlEntity != null) {
      try {
        if (casOpsSotEotCtrlEntity.getSotOut().equalsIgnoreCase(nonActiveInd)) {
          StartOfTransmissionExtract startOfTransmissionExtract =
              new StartOfTransmissionExtract(memberNo, service, "S");
          startOfTransmissionExtract.createStartOfTransmissionFile();

          casOpsSotEotCtrlEntity.setSotOut(activeInd);

          boolean updated = serviceBeanRemote.updateSOTEOTCntrl(casOpsSotEotCtrlEntity);
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
    File files = new File("/home/opsjava/Delivery/Cession_Assign/Output/temp");
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
    File files = new File("/home/opsjava/Delivery/Cession_Assign/Input/Processing");

    if (!files.exists()) {
		if (files.mkdir()) {
			log.info("Processing folder created successfully ************************");
		}
    } else {
      log.info("The folder already exists");
    }
  }

  private static void createArchiveOutPutFolder() throws IOException {
    File files = new File("/home/opsjava/Delivery/Cession_Assign/Archive/Output");

    if (!files.exists()) {
		if (files.mkdir()) {
			log.info("Processing folder created successfully ************************");
		}
    } else {
      log.info("The folder already exists");
    }
  }


  private static void createArchiveInPutFolder() throws IOException {
    File files = new File("/home/opsjava/Delivery/Cession_Assign/Archive/Input");

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