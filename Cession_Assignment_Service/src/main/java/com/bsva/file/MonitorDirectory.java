package com.bsva.file;

import com.bsva.PropertyUtil;
import com.bsva.authcoll.singletable.file.FileLoader_ST;
import com.bsva.commons.model.OpsFileRegModel;
import com.bsva.entities.CasSysctrlCompParamEntity;
import com.bsva.entities.CasSysctrlSysParamEntity;
import com.bsva.entities.CasOpsStatusDetailsEntity;
import com.bsva.entities.CasOpsStatusHdrsEntity;
import com.bsva.entities.CasOpsCustParamEntity;
import com.bsva.entities.CasOpsRefSeqNrEntity;
import com.bsva.interfaces.AdminBeanRemote;
import com.bsva.interfaces.ValidationBeanRemote;
import com.bsva.utils.Util;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.ejb.EJB;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author jeremym
 * @author SalehaR-2019/05/06 Debug Statements & Code CleanUp
 * @author SalehaR - 2019-09-11 Add Single/Multiple Table property & load
 * @author SalehaR-2019/10/14 Read Paths from properties file
 */
public class MonitorDirectory {

  public static Logger log = Logger.getLogger(MonitorDirectory.class);
  // To be DB Driven
  private static Path pathIncomingFile = null;
  private static WatchService watchService;
  private static Path pathProcessingFile = null;
  private static String pathFile = "/home/opsjava/Delivery/Cession_Assign/Input/Processing/";
  private static Map<String, String> map = new HashMap<>();
  private static String fileName = null;
  private static OpsFileRegModel opsFileRegModel;
  private static AdminBeanRemote adminBeanRemote;
  public static ValidationBeanRemote valBeanRemote;
  private static String fileNr = null;
  private static CasOpsCustParamEntity casOpsCustParamEntity;
  public static String pubErrorCode;
  private static String processName = "FILEWATCHER";
  public static CasSysctrlSysParamEntity casSysctrlSysParamEntity;
  public static CasSysctrlCompParamEntity mdtSysctrlCompParamEntity = null;
  public static String backEndProcess = "BACKEND";
  public static String trimFileName = null;
  public static String mdtLoadType = null, inputPath, processPath;

  @EJB
  static PropertyUtil propertyUtil;

  public static void monitor() {

// 2019/10/14-SalehaR: Read values from Properties file

    // 2019/10/14-SalehaR: Read values from Properties file
    try {
      propertyUtil = new PropertyUtil();
      mdtLoadType = propertyUtil.getPropValue("MDT.LOAD.TYPE");
      log.info("MDT LOAD TYPE FROM PROPERTIES: " + mdtLoadType);
      inputPath = propertyUtil.getPropValue("IncomingFile.In");
      log.info("INPUT PATH FROM PROPERTIES: " + inputPath);
      processPath = propertyUtil.getPropValue("ProcessingFile.In");
      log.info("PROCESS PATH FROM PROPERTIES: " + processPath);
    } catch (Exception ex) {
      log.error("MonitorDirectory - Could not find MandateMessageCommons.properties in classpath");
      inputPath = "/home/opsjava/Delivery/Mandates/Input/";
      processPath = "/home/opsjava/Delivery/Mandates/Input/Processing/";
    }

    try {
      contextAdminBeanCheck();
      log.info("Start Watch");

//			2019/10/14 SalehaR-Read paths from Properties 
//			pathIncomingFile = Paths.get("/home/opsjava/Delivery/Mandates/Input/");
//			pathProcessingFile = Paths.get("/home/opsjava/Delivery/Mandates/Input/Processing/");
      pathIncomingFile = Paths.get(inputPath);
      pathProcessingFile = Paths.get(processPath);
      watchService = FileSystems.getDefault().newWatchService();
      pathProcessingFile.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

      /*
       * pathIncomingFile.register(watchService,
       * StandardWatchEventKinds.ENTRY_DELETE);
       * pathIncomingFile.register(watchService,
       * StandardWatchEventKinds.ENTRY_MODIFY);
       * pathIncomingFile.register(watchService,
       * StandardWatchEventKinds.OVERFLOW);
       */

      boolean valid = true;
      do {
        /* WatchKey watchKey = watchService.take(); */
        WatchKey watchKey = watchService.poll(1000, TimeUnit.DAYS);

        // WatchKey watchKey = watchService.poll(5, TimeUnit.MINUTES);
        if (watchKey == null) {
          log.debug("There is no file to process yet. Waiting.....");
          continue;
        } else {
          try {
            for (WatchEvent event : watchKey.pollEvents()) {
              WatchEvent.Kind kind = event.kind();
              // Pass to asyno ejb create
              if (StandardWatchEventKinds.ENTRY_CREATE.equals(event.kind())) {
                fileName = event.context().toString();
                log.info("fileName---->" + fileName);
                log.debug("fileName--->length" + fileName.length());
                log.debug("File Created:" + fileName);
                log.debug("Path File" + pathFile);

                if (fileName.length() == 37) {
                  //Check if active day exists
                  casSysctrlSysParamEntity =
                      (CasSysctrlSysParamEntity) adminBeanRemote.retrieveActiveSysParameter();
                  log.debug("<MONITOR_DIR> SysParamEntity : " + casSysctrlSysParamEntity);

                  if (casSysctrlSysParamEntity != null &&
                      casSysctrlSysParamEntity.getActiveInd().equalsIgnoreCase("Y")) {
                    //Check if processing date equals system date.
                    String creationDate = fileName.substring(17, 25);
                    log.debug("creationDate: " + creationDate);

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                    String procDate = sdf.format(casSysctrlSysParamEntity.getProcessDate());
                    log.debug("procDate: " + procDate);

                    if (creationDate.compareTo(procDate) == 0) {
                      if (casSysctrlSysParamEntity.getEodRunInd().equalsIgnoreCase("N")) {
                        //moveFile(fileName);
                        addFile(fileName);

                        //Single Table
                        FileLoader_ST fileLoaderST = new FileLoader_ST();
                        fileLoaderST.loadFile(fileName, pathFile);
                        //log.debug("MonitorDirectory.fileloader()");

                      } else {
                        if (casSysctrlSysParamEntity.getEodRunInd()
                            .equalsIgnoreCase("Y")) {
                          log.error("File : " + fileName +
                              " cannot be processed. EOD has run for the day");
                        } else {
                          if (casSysctrlSysParamEntity.getEodRunInd()
                              .equalsIgnoreCase("R")) {
                            log.error(
                                "File : " + fileName +
                                    " cannot be processed. EOD is running.");
                          }
                        }
                      }
                    } else {
                      log.error(
                          "File : " + fileName + " has an incorrect processing date");
                    }
                  } else {
                    log.error(
                        "Files not being processed as their is no active processing day");
                  }
                }//end of check if filname length > 37
                else {
                  log.error("File Name is not equal to 37!!!!");
                }
              }
              // end of if eventKind()
              else {
                valid = false;
              }
            }
            valid = watchKey.reset();
          }// end of watch Key
          catch (Exception fae) {
            String transmissionInd = fileName.substring(32, 33);
            log.info("transmissionInd : " + transmissionInd);

            if (transmissionInd.equalsIgnoreCase("D")) {
              valid = true;
              log.error("Error on File Watcher  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" +
                  fae.getMessage());
              log.error("<JAXB>Error on unmarshall : " + fae.getMessage());

              contextValidationBeanCheck();
              contextAdminBeanCheck();
              casSysctrlSysParamEntity =
                  (CasSysctrlSysParamEntity) adminBeanRemote.retrieveActiveSysParameter();

              mdtSysctrlCompParamEntity =
                  (CasSysctrlCompParamEntity) valBeanRemote.retrieveCompanyParameters(
                      backEndProcess);

              List<CasOpsStatusDetailsEntity> opsStatusDetailsList =
                  new ArrayList<CasOpsStatusDetailsEntity>();

              String achId = fileName.substring(0, 3);
              String service = fileName.substring(3, 8);
              String instId = fileName.substring(8, 16);
              instId = StringUtils.stripStart(instId, "0");

              String creationDate = fileName.substring(17, 25);
              String fileNo = fileName.substring(25, 31);
              //							String transInd = fileName.substring(31,32);
              log.debug("achId: " + achId);
              log.debug("service: " + service);
              log.debug("instId: " + instId);
              log.debug("creationDate: " + creationDate);
              log.debug("fileNo: " + fileNo);

              String msgId = achId + "/" + service + "/" + "00" + instId + "/" +
                  creationDate + "/" + fileNo;
              log.debug("msgId: " + msgId);

              log.error("pubErrorCode" + pubErrorCode);
              BigDecimal hdrSystemSeqNo = BigDecimal.ZERO;
              CasOpsStatusHdrsEntity opsStatusHdrsEntity =
                  new CasOpsStatusHdrsEntity();
              String statusReportService = "ST200";
              String msgName = null;
             if (service.equalsIgnoreCase("CARIN")) {
                msgName = "pain.010";
                statusReportService = "ST200";
              } else if (service.equalsIgnoreCase("RCAIN")) {
                msgName = "pain.012";
                statusReportService = "ST204";
              } else if (service.equalsIgnoreCase("ST201")) {
                opsStatusHdrsEntity.setService("ST202");
                msgName = "pacs.002";
                statusReportService = "ST202";
              }


              String outMsgId = generateMsgId(instId, statusReportService);
              log.debug("outMsgId: " + outMsgId);
              opsStatusHdrsEntity.setSystemSeqNo(new BigDecimal(999999));
              opsStatusHdrsEntity.setHdrMsgId(outMsgId);
              opsStatusHdrsEntity.setInstgAgt(instId);
              opsStatusHdrsEntity.setOrgnlMsgId(msgId);
              opsStatusHdrsEntity.setService(statusReportService);
              opsStatusHdrsEntity.setOrgnlMsgName(msgName);
              opsStatusHdrsEntity.setProcessStatus("6");
              opsStatusHdrsEntity.setGroupStatus("RJCT");

              log.debug("opsStatusHdrsEntity in pacs 002 validation =====>>>>: " +
                  opsStatusHdrsEntity);
              hdrSystemSeqNo = valBeanRemote.saveOpsStatusHdrs(opsStatusHdrsEntity);

              //Generate the Status Details
              CasOpsStatusDetailsEntity opsStatusDetailsEntity =
                  new CasOpsStatusDetailsEntity();
              opsStatusDetailsEntity.setSystemSeqNo(new BigDecimal(123));
              opsStatusDetailsEntity.setStatusHdrSeqNo(hdrSystemSeqNo);
              opsStatusDetailsEntity.setErrorCode("902205");
              opsStatusDetailsEntity.setTxnStatus("RJCT");
              opsStatusDetailsEntity.setErrorType("HDR");

              opsStatusDetailsList.add(opsStatusDetailsEntity);
              valBeanRemote.saveOpsStatusDetails(opsStatusDetailsList);
            }
            fae.printStackTrace();

          }

          //Re-start Monitor Regardless
          monitor();
        }
//					catch (Exception e)
//					{
//						valid = true;
//						e.printStackTrace();
//						monitor();
//					}

      }
      while (valid);
      log.debug("End Watch ");
    } catch (IOException | InterruptedException ex) {
      log.error(ex.getMessage());
    }
  }

  private static void addFile(String fileName) {
    map.put(fileName, "/home/opsjava/Delivery/Cession_Assign/Input/Processing/");
    log.debug("addfile FileName----->" + fileName);
  }

  private static void deleteFile(String fileName) {
    map.remove(fileName);
  }

  public static boolean stopWatch() {
    Boolean result = false;

    try {
      watchService.close();
      result = true;
    } catch (Exception e) {
      log.error(e.getMessage());
      result = false;
    }

    return result;
  }

  public static Map<?, ?> getWork() {
    return map;
  }

  private static void moveFile(String file) throws IOException {
    Path source = Paths.get("/home/opsjava/Delivery/Cession_Assign/Input/" + file);
    Path target = Paths.get("/home/opsjava/Delivery/Cession_Assign/Input/Processing/" + file);
    Files.move(source, target);
    log.debug("source fileName------>" + source);
    log.debug("target fileName------>" + target);
  }

  public static String generateMsgId(String destInstId, String statusRepService) {
    int lastSeqNoUsed;
    log.debug("In the generateMsgId()");
    SimpleDateFormat sdfFileDate = new SimpleDateFormat("yyyyMMdd");
    String achId, achBicCode, creationDate, fileSeqNo, liveTestInd, msgId = null;
    String outgoingService = statusRepService;/*"ST100"*/
    try {

      if (mdtSysctrlCompParamEntity != null) {
        achId = mdtSysctrlCompParamEntity.getAchId();
        liveTestInd = mdtSysctrlCompParamEntity.getTransamissionInd();
      } else {
        achId = "021";
      }

      CasOpsRefSeqNrEntity casOpsRefSeqNrEntity = new CasOpsRefSeqNrEntity();
      casOpsRefSeqNrEntity =
          (CasOpsRefSeqNrEntity) valBeanRemote.retrieveRefSeqNr(outgoingService, destInstId);

      if (casOpsRefSeqNrEntity != null) {
        lastSeqNoUsed = Integer.valueOf(casOpsRefSeqNrEntity.getLastSeqNr());
        lastSeqNoUsed++;
      } else {
        lastSeqNoUsed = 1;
      }

      fileSeqNo = String.format("%06d", lastSeqNoUsed);
      casOpsRefSeqNrEntity.setLastSeqNr(fileSeqNo);
      valBeanRemote.updateOpsRefSeqNr(casOpsRefSeqNrEntity);


      creationDate = sdfFileDate.format(new Date());
      msgId = achId + "/" + outgoingService + "/" + "00" + destInstId + "/" + creationDate + "/" +
          fileSeqNo;
      log.debug("OutMsgId: " + msgId);
    } catch (Exception e) {
      log.error("**** Exception generating MsgId **** : " + e);
      e.printStackTrace();
      e.getCause();
    }

    return msgId;
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
}