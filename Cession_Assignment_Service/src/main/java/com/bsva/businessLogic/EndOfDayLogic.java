package com.bsva.businessLogic;

import com.bsva.PropertyUtil;
import com.bsva.commons.model.AudSystemProcessModel;
import com.bsva.delivery.EndOfTransmissionExtract;
import com.bsva.entities.CasSysctrlServicesEntity;
import com.bsva.entities.CasSysctrlSysParamEntity;
import com.bsva.entities.CasOpsMndtCountPK;
import com.bsva.entities.CasOpsSchedulerEntity;
import com.bsva.entities.CasOpsSotEotCtrlEntity;
import com.bsva.entities.CasOpsCustParamEntity;
import com.bsva.entities.CasOpsServicesEntity;
import com.bsva.entities.CasOpsSlaTimesEntity;
import com.bsva.entities.ObsSystemBillingCtrlsEntity;
import com.bsva.entities.SysCisBankEntity;
import com.bsva.interfaces.AdminBeanRemote;
import com.bsva.interfaces.QuartzSchedulerBeanRemote;
import com.bsva.interfaces.ServiceBeanRemote;
import com.bsva.interfaces.ValidationBeanRemote;
import com.bsva.mandatesArchive.AC_ArchiveMessages_ST;
import com.bsva.reports.DailyReportsLogic;
import com.bsva.reports.MonthlyReportsLogic;
import com.bsva.utils.Util;
import com.bsva.utils.ZipUtil;
import java.io.File;
import java.io.IOException;
import java.nio.file.ClosedWatchServiceException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.ejb.EJB;
import org.apache.log4j.Logger;

/**
 * @author ElelwaniR
 * @author SalehaR-2019/05/10 Debug Statements & Code CleanUp
 * @author SalehaR-2020/04/28: CHG-191572 - Change Archive Path & Zip
 */
public class EndOfDayLogic {
  @EJB
  PropertyUtil propertyUtil;
  public static Logger log = Logger.getLogger(EndOfDayLogic.class);
  private static AdminBeanRemote adminBeanRemote;
  public static ServiceBeanRemote beanRemote;
  public static ValidationBeanRemote valBeanRemote;
  private static QuartzSchedulerBeanRemote quartzSchedulerBeanRemote;
  String file = null, destInstId = null, fileType = null, quartzSchedulerBeanRemoteservice = null;
  List<CasOpsCustParamEntity> custParamsList = new ArrayList<CasOpsCustParamEntity>();
  List<CasSysctrlServicesEntity> sysCntrlServicesList = new ArrayList<CasSysctrlServicesEntity>();
  // list
  String outgoingService = "MANOT";
  String processStatus = "C";
  CasOpsSlaTimesEntity casOpsSlaTimesEntity;
  Date currentDate = new Date();
  private CasOpsMndtCountPK mdtCountPk;
  public String feedbackMsg;
  boolean eodCheck = false;
  String activeInd, nonActiveInd;
  String carotServ, rcaotServ, st203Serv;
  String st200Serv, st202Serv, st204Serv, st105Serv;
  String eodProcess, mdtLoadType;
  String loggedInUser = null;
  String INPUT_FILE_PATH = null, PROCESS_FILE_PATH = null, OUTPUT_FILE_PATH = null, TEMP_FILE_PATH =
      null, ARCHIVE_INPUT_PATH = null, ARCHIVE_OUTPUT_PATH = null, ARCHIVE_OOPT_PATH = null;

  public EndOfDayLogic(String userName) {
    loggedInUser = userName;
    contextAdminBeanCheck();
    contextValidationBeanCheck();
    contextCheck();
    contextQuartzSchedulerBeanCheck();

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
      this.eodProcess = propertyUtil.getPropValue("AUD.SYSPROCESS.EOD");
      this.mdtLoadType = propertyUtil.getPropValue("MDT.LOAD.TYPE");
      INPUT_FILE_PATH = propertyUtil.getPropValue("IncomingFile.In");
      PROCESS_FILE_PATH = propertyUtil.getPropValue("ProcessingFile.In");
      OUTPUT_FILE_PATH = propertyUtil.getPropValue("ExtractOutputFile.Out");
      TEMP_FILE_PATH = propertyUtil.getPropValue("ExtractTemp.Out");
      ARCHIVE_INPUT_PATH = propertyUtil.getPropValue("Archive.Input");
      ARCHIVE_OUTPUT_PATH = propertyUtil.getPropValue("Archive.Output");
      ARCHIVE_OOPT_PATH = propertyUtil.getPropValue("Archive.Oopt");
    } catch (Exception e) {
      log.error(" EndOfDayLogic - Could not find CessionAssignment.properties in classpath");
    }
  }

  public void testArchiveFiles() {
    moveProcessedFiles();
    moveOutPutFiles();
    moveInputFiles();
  }

  public void testArchive() {
    AC_ArchiveMessages_ST ac_ArchiveMessages_ST = new AC_ArchiveMessages_ST();
    try {
      ac_ArchiveMessages_ST.archiveProcessLogic();
    } catch (ParseException e) {
     log.info("Error on Archiving Test: "+e.getMessage());
     e.printStackTrace();
    }
  }

  public boolean EndOfdayImplementation(String forcecloseReason)
      throws ParseException, IOException {
    contextAdminBeanCheck();
    contextValidationBeanCheck();
    contextCheck();
    contextQuartzSchedulerBeanCheck();

    boolean readyForArchive = false;
    boolean ospCustTruncated = false;
    boolean opsServicesTruncated = false;
    boolean opsRefSeqTruncated = false;
    boolean txnsToExtract = false;

    CasSysctrlSysParamEntity casSysctrlSysParamEntity = new CasSysctrlSysParamEntity();
    casSysctrlSysParamEntity =
        (CasSysctrlSysParamEntity) adminBeanRemote.retrieveActiveSysParameter();

    custParamsList = (List<CasOpsCustParamEntity>) adminBeanRemote.retrieveOpsCustParamTime();
    sysCntrlServicesList =
        (List<CasSysctrlServicesEntity>) adminBeanRemote.retrieveServiceControl();
    casOpsSlaTimesEntity = (CasOpsSlaTimesEntity) adminBeanRemote.retrieveEODTime();

    SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
    Calendar cal = Calendar.getInstance();
    cal.setTime(currentDate);
    //Put it back in the Date object
    currentDate = cal.getTime();
    String strrDate = parser.format(currentDate);
    log.info("Time: " + strrDate);
    Date userDate = parser.parse(strrDate);
    Date eodTime = parser.parse(casOpsSlaTimesEntity.getStartTime());
    Date endTime = parser.parse(casOpsSlaTimesEntity.getEndTime());
    if (casSysctrlSysParamEntity != null) {
      if (casSysctrlSysParamEntity.getSodRunInd().equalsIgnoreCase("Y") &&
          casSysctrlSysParamEntity.getEodRunInd().equalsIgnoreCase("N") &&
          casSysctrlSysParamEntity.getInBalanceInd().equalsIgnoreCase("N")) {
        //Checking EOD SLA Time
        if (userDate.after(eodTime) && userDate.before(endTime)) {
          //Check if no txns are sitting on '3' or 'L'
          txnsToExtract = beanRemote.eodCheckIfAllFilesAreExtracted(casSysctrlSysParamEntity.getProcessDate(), mdtLoadType);
          log.debug("txnsToExtract frin Service Bean ==> " + txnsToExtract);
          if (txnsToExtract) {
            //Scheduler Check
            List<CasOpsSchedulerEntity> opsSchedulersList = new ArrayList<CasOpsSchedulerEntity>();
            opsSchedulersList = adminBeanRemote.retrieveOpsScheduler();
            boolean activeSch = false;
            if (opsSchedulersList != null && opsSchedulersList.size() > 0) {
              for (CasOpsSchedulerEntity casOpsSchedulerEntity : opsSchedulersList) {
                if (casOpsSchedulerEntity.getActiveInd().equalsIgnoreCase("Y") &&
                    !(casOpsSchedulerEntity.getSchedulerKey().equalsIgnoreCase("b"))) {
                  activeSch = true;
                }
              }
            } else {
              activeSch = false;
            }

            //						log.info("activeSch ==>"+activeSch);
            if (activeSch) {
              log.error("All schedulers need to be stopped before End Of Day can run.");
              feedbackMsg = "All schedulers need to be stopped before End Of Day can run.";
              eodCheck = false;
            } else {

              Boolean eotCheck = false;
              //							log.info("eotCheck===> "+eotCheck);
              List<SysCisBankEntity> sysCisBankList = new ArrayList<SysCisBankEntity>();
              sysCisBankList = adminBeanRemote.retrieveSysCisBank();

              if (sysCisBankList != null && sysCisBankList.size() > 0 && sysCntrlServicesList != null && sysCntrlServicesList.size() > 0) {
                for (SysCisBankEntity sysCisBankEntity : sysCisBankList) {
                  String memberId = sysCisBankEntity.getMemberNo();
                  //									log.info("MEMBER NO ===> "+memberId);
                  for (CasSysctrlServicesEntity servicesEntity : sysCntrlServicesList) {
                    String outService = servicesEntity.getServiceIdOut();
                    //										log.info("outService ===>
                    //										"+outService);
                    CasOpsSotEotCtrlEntity casOpsSotEotCtrlEntity = new CasOpsSotEotCtrlEntity();
                    casOpsSotEotCtrlEntity =
                        (CasOpsSotEotCtrlEntity) beanRemote.retrieveSOTEOTCntrl(memberId, outService);
                    //										log.info("SOT/EOT CTRL CHECK ==>a
                    //										"+mdtAcOpsSotEotCtrlEntity);
                    if (casOpsSotEotCtrlEntity != null) {
                      if (casOpsSotEotCtrlEntity.getEotOut().equalsIgnoreCase("Y") &&
                          casOpsSotEotCtrlEntity.getSotOut().equalsIgnoreCase("Y")) {
                        eotCheck = true;
                      }
                    } else {
                      eotCheck = false;
                      log.error("Cannot proceed with EOD. EOT files not completed yet!");
                      feedbackMsg = "Cannot proceed with EOD. EOT files not completed yet!";
                      eodCheck = false;
                    }
                  }
                }
              } else {
                log.info("CIS DATA EMPTY!!! ");
              }

              if (eotCheck) {
                //Update System Audit Log Information
                AudSystemProcessModel audSystemProcessModel = new AudSystemProcessModel();
                audSystemProcessModel.setProcess(eodProcess);
                audSystemProcessModel.setProcessDate(new Date());
                audSystemProcessModel.setUserId(loggedInUser);

                adminBeanRemote.saveSystemAuditInfo(audSystemProcessModel);

                //Update EOD Ind to 'R' - Request to Close
                casSysctrlSysParamEntity.setEodRunInd("R");
                //								log.info("mdtSysCtrlSysParamEntity in EodLogic
                //								---->> " + mdtSysctrlSysParamEntity);
                boolean updateToR =
                    adminBeanRemote.updateSystemParameters(casSysctrlSysParamEntity);
                log.info("updateToR ---->> " + updateToR);

                if (updateToR) {
                  log.info("<===== End of Day Requested =====>");
                }

                try {
                  //							log.info("<<---------STOPPING FILE
                  //							WATCHER---------->");
                  //							fileWatcher.stopMonitor() ;

                  log.info("<<---------RUN DAILY REPORTS---------->");
                  DailyReportsLogic dailyReportsLogic = new DailyReportsLogic();
                  boolean allDailyReportsRun = dailyReportsLogic.generateDailyReports();

                  //This is set to true for testing purposes
                  //									allDailyReportsRun  = true;
                  //									boolean allDailyReportsRun  = true;
                  if (allDailyReportsRun) {
                    log.info("<<---------ARCHIVING MANDATES---------->");
//									Not needed for C&A
                    //Summarise Error Codes Report Data
//										boolean populateSummInfo = populateSummaryErrorCodeInfo();

                    AC_ArchiveMessages_ST ac_ArchiveMessages_ST = new AC_ArchiveMessages_ST();
                    ac_ArchiveMessages_ST.archiveProcessLogic();

                    log.info("<<---------ARCHIVING MANDATES COMPLETED---------->");

                    log.info("<<---------RUNNING PBMD08 EXPIRED TXNS REPORT---------->");
                    boolean expReport = dailyReportsLogic.generateExpiredTxnsReport();

                    //MONTHLY REPORT RUN CHECK
                    DateFormat endDateFormat = new SimpleDateFormat("ddMMyyyy");

                    //Calculate the last date of the month
                    Calendar nextNotifTime = Calendar.getInstance();
                    nextNotifTime.add(Calendar.MONTH, 1);
                    nextNotifTime.set(Calendar.DATE, 1);
                    nextNotifTime.add(Calendar.DATE, -1);
                    Date lastDate = nextNotifTime.getTime();

                    String strLastDate = endDateFormat.format(lastDate);
                    log.info("strLastDate ==> " + strLastDate);

                    String currentProcDate =
                        endDateFormat.format(casSysctrlSysParamEntity.getProcessDate());
                    log.info("currentProcDate ==> " + currentProcDate);

                    boolean allMonthlyReportsRun = false;
                    if (currentProcDate.equalsIgnoreCase(strLastDate)) {
                      log.info("Dates Match!");
                      log.info("<<---------RUN MONTHLY REPORTS---------->");
                      MonthlyReportsLogic monthlyReportsLogic = new MonthlyReportsLogic();
                      allMonthlyReportsRun = monthlyReportsLogic.generateMonthlyReports();
                    } else {
                      // It is not month end.
                      log.info("<<---------NO MONTHLY REPORTS TO RUN---------->");
                      allMonthlyReportsRun = true;
                    }
                    if (allMonthlyReportsRun) {
                      log.info("<<---------TRUNCATING TABLES---------->");
                      adminBeanRemote.truncateTable("CAMOWNER.CAS_OPS_SERVICES");
                      adminBeanRemote.truncateTable("CAMOWNER.CAS_OPS_CUST_PARAM");
                      adminBeanRemote.truncateTable("CAMOWNER.CAS_OPS_REF_SEQ_NR");
                      adminBeanRemote.truncateTable("CAMOWNER.CAS_AC_OPS_PROCESS_CONTROLS");
                      adminBeanRemote.truncateTable("CAMOWNER.CAS_OPS_SOT_EOT_CTRL");
                      adminBeanRemote.truncateTable("CAMOWNER.MDT_OPS_CRON");
                      adminBeanRemote.truncateTable("CAMOWNER.MDT_OPS_SLA_TIMES");
                      adminBeanRemote.truncateTable("CAMOWNER.CAS_OPS_SCHEDULER");
                      adminBeanRemote.truncateTable("CAMOWNER.CAS_OPS_TRANS_CTRL_MSG");
                      adminBeanRemote.truncateTable("CAMOWNER.CAS_OPS_REP_SEQ_NR");
                      adminBeanRemote.truncateTable("CAMOWNER.CAS_OPS_LAST_EXTRACT_TIMES");
                      adminBeanRemote.truncateTable("CAMOWNER.CAS_OPS_FILE_SIZE_LIMIT");
                      adminBeanRemote.truncateTable("CAMOWNER.CAS_OPS_MNDT_COUNT");
                      adminBeanRemote.truncateTable("CAMOWNER.CAS_OPS_STATUS_HDRS");
                      adminBeanRemote.truncateTable("CAMOWNER.CAS_OPS_STATUS_DETAILS");
                      adminBeanRemote.truncateTable("CAMOWNER.SYS_CIS_BANK");
                      adminBeanRemote.truncateTable("CAMOWNER.SYS_CIS_BRANCH");

                      updateEodRunInd(casSysctrlSysParamEntity, forcecloseReason);
                      updateActiveInd(casSysctrlSysParamEntity);

//											Move Input Folder										
                      moveProcessedFiles();

//											Move Output Folder
                      moveOutPutFiles();

//											Move Out of Processing Folder files
                      moveInputFiles();

//											2020/04/28 - SalehaR - Replace current method with
//											zipped folder method
//											moveProcessedFiles();
//											moveInputFiles();
//											moveOutPutFiles();
//											archiveOutputFiles();
//											archiveInputFiles();


                      //update Billing Cntrls table
                      ObsSystemBillingCtrlsEntity obsSystemBillingCtrlsEntity =
                          (ObsSystemBillingCtrlsEntity) adminBeanRemote.retrieveBillingCtrls(
                              casSysctrlSysParamEntity.getProcessDate());
                      obsSystemBillingCtrlsEntity.setProcessStatus("C");
                      obsSystemBillingCtrlsEntity.setModifiedBy("CAMOWNER");
                      obsSystemBillingCtrlsEntity.setModifiedDate(new Date());
                      adminBeanRemote.updateBillingCtrl(obsSystemBillingCtrlsEntity);

                      eodCheck = true;
                    } else {
                      eodCheck = false;
                      log.error(
                          "Cannot proceed with EOD. All MONTHLY Reports have not completed yet!");
                      feedbackMsg =
                          "Cannot proceed with EOD. All Monthly Reports have not completed yet!";
                    }
                  } else {
                    eodCheck = false;
                    log.error("Cannot proceed with EOD. All Daily Reports have not completed yet!");
                    feedbackMsg =
                        "Cannot proceed with EOD. All Daily Reports have not completed yet!";
                  }

                } catch (ClosedWatchServiceException e) {
                  log.info("The file watcher has encountered an error" + e.getMessage());
                  feedbackMsg = "An error was encountered on End Of Day : " + e.getMessage();
                  eodCheck = false;
                  e.printStackTrace();
                } catch (Exception e) {
                  log.error("An error was encountered on End Of Day : " + e.getMessage());
                  feedbackMsg = "An error was encountered on End Of Day : " + e.getMessage();
                  eodCheck = false;
                  e.printStackTrace();
                }
              } else {
                log.error(
                    "Cannot proceed with EOD. All EOT's have not been recieved! Please check " +
                        "SOT/EOT CNTRL screen for more information!");
                feedbackMsg =
                    "Cannot proceed with EOD. All EOT's have not been recieved! Please check " +
                        "SOT/EOT CNTRL screen for more information!";
                eodCheck = false;
              }
            }//end of else
          } else {
            log.error(
                "There are transactions that need to be extracted. Please start extract " +
                    "schedulers!");
            feedbackMsg =
                "There are transactions that need to be extracted. Please start extract " +
                    "schedulers!";
            eodCheck = false;
          }
        } else {
          log.error("SLA TIME for EOD is not reached, Please run from " +
              casOpsSlaTimesEntity.getStartTime() + "!");
          feedbackMsg = ("SLA TIME for EOD is not reached, Please run from " +
              casOpsSlaTimesEntity.getStartTime() + "!");
          eodCheck = false;
        }
      } else {
        log.error("End Of Day has previously run!");
        feedbackMsg = "End Of Day has previously run!";
        eodCheck = false;
      }
    } else {
      log.error("End Of Day has previously run!");
      feedbackMsg = "End Of Day previously run!";
      eodCheck = false;
    }

    return eodCheck;
  }

  public void updateEodRunInd(CasSysctrlSysParamEntity casSysctrlSysParamEntity,
                              String forcecloseReason) {
    casSysctrlSysParamEntity.setSodRunInd("N");
    casSysctrlSysParamEntity.setEodRunInd("Y");
    casSysctrlSysParamEntity.setCisDwnldInd("N");
    casSysctrlSysParamEntity.setActiveInd("N");
    casSysctrlSysParamEntity.setForcecloseReason(forcecloseReason);

    boolean saved = adminBeanRemote.updateSystemParameters(casSysctrlSysParamEntity);
    if (saved) {
      log.info("EOD run indicator has been updated...EOD completed.");
    }
  }

  public void updateActiveInd(CasSysctrlSysParamEntity casSysctrlSysParamEntity) {
    casSysctrlSysParamEntity.setActiveInd("N");
    casSysctrlSysParamEntity.setInBalanceInd("Y");
    boolean saved = adminBeanRemote.updateSystemParameters(casSysctrlSysParamEntity);

    if (saved) {
      log.info("Active indicator has been updated");
    }
  }

  private void moveProcessedFiles() {
    try {
      Date date = new Date();
      SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
      String today = sdf.format(date);

      log.info("****** Archiving " + PROCESS_FILE_PATH + " ********");
      String archiveDir = ARCHIVE_INPUT_PATH + today + ".zip";

      log.info("Input archiveDir = " + archiveDir);
      ZipUtil.zipFolder(Paths.get(PROCESS_FILE_PATH), Paths.get(archiveDir));

      //Delete Current Folder
//		    log.info("****** Deleting "+PROCESS_FILE_PATH +" ********");
//		    Files.delete(Paths.get(PROCESS_FILE_PATH));
    } catch (Exception e) {
      log.error("Error on moveProcessedFiles() : " + e.getMessage());
      e.printStackTrace();
    }

//		2020/04/28-SalehaR: CHG-191572 Zip & Change Archive Path
//		try 
//		{
//			/******* Clean out archive folder first **********/
//			//			log.info("In the moving files method ############################");
//			Date date = new Date();
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
//			String today = sdf.format(date);
//
//			//        Move files from Processing Folder to Archive
//			File processingFolder = new File
//			("/home/opsjava/Delivery/Mandates/Input/Processing/");
//			File archiveDir = new File("/home/opsjava/Delivery/Mandates/Archive/Input/" + today);
//
//			if(processingFolder.isDirectory())
//			{
//				File[] content = processingFolder.listFiles();
//				log.info("Content LIST---> "+content.length);
//				if(content != null && content.length > 0)
//				{
//					for (File file : content) 
//					{
//						log.debug("Content file---> "+file.getName());
//						FileUtils.copyFileToDirectory(file, archiveDir);
//					}
//
//					//Clean out the processing folder
//					FileUtils.cleanDirectory(processingFolder);
//				}
//			}
//		}
//		catch (Exception e) 
//		{
//			log.error("Error on clean out of processing folder: "+e.getMessage());
//			e.printStackTrace();
//		}
  }

  private void moveInputFiles() {
    try {
      Date date = new Date();
      SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
      String today = sdf.format(date);

      log.info("****** Archiving " + INPUT_FILE_PATH + " ********");
      String archiveDir = ARCHIVE_OOPT_PATH + today + ".zip";

      log.info("Input archiveDir = " + archiveDir);
      ZipUtil.zipFolder(Paths.get(INPUT_FILE_PATH), Paths.get(archiveDir));
    } catch (Exception e) {
      log.error("Error on moveProcessedFiles() : " + e.getMessage());
      e.printStackTrace();
    }

//		2020/04/28-SalehaR: CHG-191572 Zip & Change Archive Path
//		try
//		{
//			//			log.info("In the moving files method ############################");
//			Date date = new Date();
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
//			String today = sdf.format(date);
//
//			//Move files from Processing Folder to Archive
//			File inputFolder = new File("/home/opsjava/Delivery/Mandates/Input/");
//			File   archiveInput = new File("/home/opsjava/Delivery/Mandates/Archive/OOPT/" +
//			today)  ;
//
//			//			log.info("inputFolder-->"+inputFolder);
//			//			log.info("archiveInput-->"+archiveInput);
//			if(inputFolder.isDirectory())
//			{
//				File[] content = inputFolder.listFiles();
//				log.info("INPUT LIST---> "+content.length);
//				if(content != null && content.length > 0 )
//				{
//					for (File file : content) 
//					{
//						//Added file extension to exclude processing folder
//						if(file.getName().contains("xml")|| (file.getName().contains("XML")))
//						{
//							log.debug("input file---> "+file.getName());
//							FileUtils.copyFileToDirectory(file, archiveInput);
//						}
//					}
//
//					//Clean out the processing folder
//					FileUtils.cleanDirectory(inputFolder);
//				}
//			}
//		}
//		catch (Exception e) 
//		{
//			log.error("Error on clean out of processing folder: "+e.getMessage());
//			e.printStackTrace();
//		}
  }

  private void moveOutPutFiles() {
    try {
      Date date = new Date();
      SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
      String today = sdf.format(date);

      log.info("****** Archiving " + TEMP_FILE_PATH + " ********");
      String archiveDir = ARCHIVE_OUTPUT_PATH + today + ".zip";

      log.info("Output archiveDir = " + archiveDir);
      ZipUtil.zipFolder(Paths.get(TEMP_FILE_PATH), Paths.get(archiveDir));

      //Delete Current Folder
      log.info("****** Deleting " + TEMP_FILE_PATH + " ********");
      Files.delete(Paths.get(TEMP_FILE_PATH));
    } catch (Exception e) {
      log.error("Error on moveOutPutFiles(): " + e.getMessage());
      e.printStackTrace();
    }

//		2020/04/28-SalehaR: CHG-191572 Zip & Change Archive Path
//		try 
//		{
//			/******* Clean out archive folder first **********/
//
//			//			log.info("In the moving output files method ############################");
//			Date date = new Date();
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
//			String today = sdf.format(date);
//			Path source = Paths.get("/home/opsjava/Delivery/Mandates/Output/temp/");
//			Path target = Paths.get("/home/opsjava/Delivery/Mandates/Archive/Output/" + today);
//			Files.move(source, target);
//
//			File files = new File("/home/opsjava/Delivery/Mandates/Input/Processing/");
//			if (files.exists())
//			{
//				if (files.mkdir()) 
//				{
//
//				}
//				else 
//				{
//					log.info("*****************Failed to create a new processing
//					directory!*************************");
//				}
//			}
//		}
//		catch (IOException e) 
//		{
//			log.error("Error on clean out of processing folder: ");
//		}
  }

//	2020/04/28-SalehaR: CHG-191572 Zip & Change Archive Path
//	public static void cleanDirectories() throws IOException
//	{
//		Path source1 = Paths.get("/home/opsjava/Delivery/Mandates/Input/Processing/");
//		Path source2 =Paths.get("/home/opsjava/Delivery/Mandates/Input/");
//		Path source3 =Paths.get("/home/opsjava/Delivery/Mandates/Output/");
//		//		Path source4=Paths.get("/home/jboss/Mandates/Output/Delivery/StatusReports/SOT");
//		//		Path source5 =Paths.get("/home/jboss/Mandates/Output/Delivery/StatusReports/");
//		Files.delete(source1);	
//		Files.delete(source2);	
//		Files.delete(source3);	
//		//		Files.delete(source4);	
//		//		Files.delete(source5);	
//		//	Files.copy(source, target,StandardCopyOption.REPLACE_EXISTING);
//		log.info("the folder has been created ,creating a new one *******************");
//		File files = new File("/home/opsjava/Delivery/Mandates/Input/Processing/");
//		if (files.exists())
//		{
//
//
//		}
//	}

//	2020/04/28-SalehaR: CHG-191572 Zip & Change Archive Path
//	public static void deleteArchiveInputFiles() throws IOException 
//	{
//		log.info("In the deletion folder ");
//		long  daysBack = 7;
//		//Calendar cal = Calendar.getInstance();  
//		String dir ="/home/opsjava/Delivery/Mandates/Archive/Input/";
//		File directory = new File(dir);
//		log.info("The directory #######################"+directory);
//		File[] flist = directory.listFiles();
//		log.info("The directory #######################"+directory.listFiles());
//		log.info("The file list is as follows &&&&&&&&&&&&&&&&&&&&&&&&&&&"+flist.toString());
//		if (flist!=null)
//		{
//			for (File file :flist)
//			{
//				long diff = new Date().getTime()-file.lastModified();
//				log.info("the diff is #########################################"+diff);
//				long cutoff = (daysBack*(24*60*60*1000));
//				log.info("The cut off is #####################################"+cutoff);
//
//				if (diff > cutoff)
//				{
//					file.delete();
//				}
//			}
//		}
//		/*// cal.add(Calendar.DAY_OF_MONTH, daysBack * -1); 
//		 log.info("In the deletion folder " +daysBack);
//		// long purgeTime = cal.getTimeInMillis();   
//		Path source = Paths.get("/home/opsjava/Delivery/Mandates/Archive/Input/");
//		Files.delete(source);*/
//	}

//	2020/04/28-SalehaR: CHG-191572 Zip & Change Archive Path
//	public static void deleteArchiveOutPutFiles() throws IOException
//	{
//		log.info("In the deletion folder ");
//		long  daysBack = 0;
//		//Calendar cal = Calendar.getInstance();  
//		String dir ="/home/opsjava/Delivery/Mandates/Archive/Output/";
//		File directory = new File(dir);
//		log.info("The directory #######################"+directory);
//		File[] flist = directory.listFiles();
//		log.info("The directory #######################"+directory.listFiles());
//		log.info("The file list is as follows &&&&&&&&&&&&&&&&&&&&&&&&&&&"+flist.toString());
//		if (flist!=null)
//		{
//			for (File file :flist)
//			{
//				long diff = new Date().getTime()-file.lastModified();
//				log.info("the diff is #########################################"+diff);
//				long cutoff = (daysBack*(24*60*60*1000));
//				log.info("The cut off is #####################################"+cutoff);
//
//				if (diff > cutoff)
//				{
//					file.delete();
//				}
//			}
//		}
//	}


  //ELELWANI R - 2016/04/25 - This has been removed as billling requirements are not finalised.
  //	public boolean populateMdtAcOpsDailyBilling ()
  //	{
  //		boolean saved =false ;
  //		mdtAcOpsDailyBillingPK = new MdtAcOpsDailyBillingPK();
  //		mdtCountPk = new  MdtAcOpsMndtCountPK();
  //		List<MdtAcOpsMndtCountEntity> opsCountEntity = new
  //		ArrayList<MdtAcOpsMndtCountEntity>();
  //		opsCountEntity = (List<MdtAcOpsMndtCountEntity>) adminBeanRemote
  //		.retrieveOpsMndtCount();
  //
  //		List <MdtAcOpsDailyBillingEntity> opsDailyBillingList = new
  //		ArrayList<MdtAcOpsDailyBillingEntity>();
  //		opsDailyBillingList = (List<MdtAcOpsDailyBillingEntity>) adminBeanRemote
  //		.retrieveMdtAcOpsDailyBilling();
  //
  //		if (opsDailyBillingList.size()==0)
  //		{
  //			if (opsCountEntity.size()>0)
  //			{
  //				for (MdtAcOpsMndtCountEntity  mdtOpsCountEntity : opsCountEntity)
  //				{
  //					if (mdtOpsCountEntity .getMdtAcOpsMndtCountPK().getInstId()!=null)
  //					{
  //						MdtAcOpsDailyBillingEntity mdtAcOpsDailyBillingEntity = new
  //						MdtAcOpsDailyBillingEntity();
  //	                 	mdtAcOpsDailyBillingPK.setInstId(mdtOpsCountEntity
  //	                 	.getMdtAcOpsMndtCountPK().getInstId());
  //						mdtAcOpsDailyBillingPK.setServiceId(mdtOpsCountEntity
  //						.getMdtAcOpsMndtCountPK().getServiceId());
  //						mdtAcOpsDailyBillingEntity.setNrOfFiles(Short.valueOf
  //						(mdtOpsCountEntity.getNrOfFiles().toString()));
  ////						mdtAcOpsDailyBillingEntity.setNrOfMsgs(mdtOpsCountEntity.getNrOfMsgs
  // ());
  //						mdtAcOpsDailyBillingEntity.setProcessDate(mdtOpsCountEntity
  //						.getProcessDate());
  //						mdtOpsCountEntity.setMdtAcOpsMndtCountPK(mdtCountPk);
  //						mdtAcOpsDailyBillingEntity.setMdtAcOpsDailyBillingPK
  //						(mdtAcOpsDailyBillingPK);
  //						saved= adminBeanRemote.createAcOpsDailyBilling
  //						(mdtAcOpsDailyBillingEntity);
  //				}
  //			  }
  //			}
  //		}
  //		if (saved)
  //		{
  //			log.info("MdtAcOpsDailyBillingEntity  has been populated");
  //		}
  //		return saved;
  //
  //	}

  public void generateEOT(String instgAgt, String service) {
    //Retrieve SOT/EOT Ind
    //List<MdtAcOpsSotEotCtrlEntity> acopsSotEotEntityList = new
    // ArrayList<MdtAcOpsSotEotCtrlEntity>();
    //		acopsSotEotEntityList = (List<MdtAcOpsSotEotCtrlEntity>) adminBeanRemote
    //		.retrieveInactiveEOTIND();

    CasOpsSotEotCtrlEntity casOpsSotEotCtrlEntity = new CasOpsSotEotCtrlEntity();
    casOpsSotEotCtrlEntity =
        (CasOpsSotEotCtrlEntity) beanRemote.retrieveSOTEOTCntrl(instgAgt, service);

    if (casOpsSotEotCtrlEntity != null) {
      if (casOpsSotEotCtrlEntity.getEotOut().equalsIgnoreCase("N") &&
          casOpsSotEotCtrlEntity.getSotOut().equalsIgnoreCase("Y")) {
        String fileType = null;
        //Retrieve the msgType from the service Table
        CasOpsServicesEntity casOpsServicesEntity =
            (CasOpsServicesEntity) adminBeanRemote.retrieveOpsService(service);

        if (casOpsServicesEntity != null) {
          fileType = casOpsServicesEntity.getMsgTypeId();
        }
        //Remove hardcoding --retrieve from database.
        //				  if(service.equalsIgnoreCase("ST100") || service.equalsIgnoreCase
        //				  ("ST103"))
        //					  fileType = "S";
        //				  else
        //				  {
        //					  if(service.equalsIgnoreCase("MANOC"))
        //						  fileType = "A";
        //					  else
        //					  {
        //						  fileType = "M";
        //					  }
        //				  }


        EndOfTransmissionExtract endOfTransmission =
            new EndOfTransmissionExtract(instgAgt, service, fileType);
        endOfTransmission.createEndOfTransmissionFile();
        casOpsSotEotCtrlEntity.setEotOut("Y");

        boolean updated = adminBeanRemote.updateACOpsEOTSOT(casOpsSotEotCtrlEntity);

        if (updated) {
          log.debug("The End of transmission indicator has been updated ");
        }
      }

    }
  }


  public static void archiveOutputFiles() throws IOException {
    File dir = new File("/home/opsjava/Delivery/Cession_Assign/Archive/Output");
    Path filePath = dir.toPath();

    Date currentDate = new Date();
    SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/YYYY");
    String cDate = sdf1.format(currentDate);
    BasicFileAttributes attributes = null;
    try {
      attributes = Files.readAttributes(filePath, BasicFileAttributes.class);
    } catch (IOException exception) {
      log.error(
          "Exception handled when trying to get file " + "attributes:" + exception.getMessage());
    }

    long milliseconds = attributes.creationTime().to(TimeUnit.MILLISECONDS);

    if ((milliseconds > Long.MIN_VALUE) && (milliseconds < Long.MAX_VALUE)) {
      Date creationDate = new Date(attributes.creationTime().to(TimeUnit.MILLISECONDS));
      log.debug("File " + filePath.toString() + " created " +
          creationDate.getDate() + "/" +
          (creationDate.getMonth() + 1) + "/" +
          (creationDate.getYear() + 1900));
      long diff = currentDate.getTime() - creationDate.getTime();
      long diffDays = diff / (24 * 60 * 60 * 1000);
      //dir.delete();

      if (diffDays >= 7) {
        deleteDirectory(new File("/home/opsjava/Delivery/Cession_Assign/Archive/Output"));
      }
    }

  }

  public static void archiveInputFiles() throws IOException {
    File dir = new File("/home/opsjava/Delivery/Cession_Assign/Archive/Input");
    Path filePath = dir.toPath();

    Date currentDate = new Date();
    SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/YYYY");
    String cDate = sdf1.format(currentDate);
    BasicFileAttributes attributes = null;
    try {
      attributes = Files.readAttributes(filePath, BasicFileAttributes.class);
    } catch (IOException exception) {
      log.error(
          "Exception handled when trying to get file " + "attributes:" + exception.getMessage());
    }

    long milliseconds = attributes.creationTime().to(TimeUnit.MILLISECONDS);

    if ((milliseconds > Long.MIN_VALUE) && (milliseconds < Long.MAX_VALUE)) {
      Date creationDate = new Date(attributes.creationTime().to(TimeUnit.MILLISECONDS));
      log.debug("File " + filePath.toString() + " created " +
          creationDate.getDate() + "/" +
          (creationDate.getMonth() + 1) + "/" +
          (creationDate.getYear() + 1900));
      long diff = currentDate.getTime() - creationDate.getTime();
      long diffDays = diff / (24 * 60 * 60 * 1000);
      //dir.delete();

      if (diffDays >= 7) {
        deleteDirectory(new File("/home/opsjava/Delivery/Cession_Assign/Archive/Input"));
      }
    }

  }


  public static boolean deleteDirectory(File dir) {

    if (dir.isDirectory()) {
      File[] children = dir.listFiles();
      for (int i = 0; i < children.length; i++) {

        boolean success = deleteDirectory(children[i]);
        if (!success) {
          return false;
        }

      }

    }
    log.debug("removing file or directory : " + dir.getName());
    return dir.delete();


  }

  public static void contextValidationBeanCheck() {
    if (valBeanRemote == null) {
      valBeanRemote = Util.getValidationBean();
    }

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

  private static void contextQuartzSchedulerBeanCheck() {
    if (quartzSchedulerBeanRemote == null) {
      quartzSchedulerBeanRemote = Util.getQuartzSchedulerBean();
    }
  }

}