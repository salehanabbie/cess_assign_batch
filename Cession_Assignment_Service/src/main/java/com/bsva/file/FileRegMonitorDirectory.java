package com.bsva.file;

import com.bsva.PropertyUtil;
import com.bsva.commons.model.OpsFileRegModel;
import com.bsva.entities.CasSysctrlSysParamEntity;
import com.bsva.interfaces.AdminBeanRemote;
import com.bsva.utils.Util;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import javax.ejb.EJB;
import org.apache.log4j.Logger;

public class FileRegMonitorDirectory {

  public static Logger log = Logger.getLogger(FileRegMonitorDirectory.class);

  private static Path pathIncomingFile = null;
  private static WatchService watchService;
  private static Path pathProcessingFile = null;
  public static String mdtLoadType = null, inputPath, processPath;
  private static String fileName = null;
  private static String pathFile = "/home/opsjava/Delivery/Cession_Assign/Input/Processing/";
  public static CasSysctrlSysParamEntity casSysctrlSysParamEntity;
  private static AdminBeanRemote adminBeanRemote;
  private static String processName = "FILEWATCHER";
  private static OpsFileRegModel opsFileRegModel;
  private static String testLiveIndProp = null;
  private static String transmissionInd;

  @EJB
  static PropertyUtil propertyUtil;

  public static void monitor() {

    try {
      contextAdminBeanCheck();

      propertyUtil = new PropertyUtil();
      inputPath = propertyUtil.getPropValue("IncomingFile.In");
      log.info("INPUT PATH FROM PROPERTIES: " + inputPath);
      processPath = propertyUtil.getPropValue("ProcessingFile.In");
      log.info("PROCESS PATH FROM PROPERTIES: " + processPath);
      testLiveIndProp = propertyUtil.getPropValue("TestLiveInd");

    } catch (Exception ex) {
      log.error("FileRegMonitorDirectory - Could not find CessionAssignment.properties in classpath");
      inputPath = "/home/opsjava/Delivery/Cession_Assign/Input/";
      processPath = "/home/opsjava/Delivery/Cession_Assign/Input/Processing/";
    }

    try {
      log.info("Start OPS File Registry Watch");

      pathIncomingFile = Paths.get(inputPath);
      pathProcessingFile = Paths.get(processPath);
      watchService = FileSystems.getDefault().newWatchService();
      pathIncomingFile.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

      boolean valid = true;

      do {
        WatchKey watchKey = watchService.poll(1000, TimeUnit.DAYS);

        if (watchKey == null) {
          log.debug("There is no file to register yet. Waiting.....");
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

                  transmissionInd = fileName.substring(32, 33);

                  // Check if active day exists
                  casSysctrlSysParamEntity =
                      (CasSysctrlSysParamEntity) adminBeanRemote.retrieveActiveSysParameter();
                  log.debug("mdtSysctrlSysParamEntity in validation: " + casSysctrlSysParamEntity);

                  if (casSysctrlSysParamEntity != null &&
                      casSysctrlSysParamEntity.getActiveInd().equalsIgnoreCase("Y")) {

                    // Check if processing date equals system date.
                    String creationDate = fileName.substring(17, 25);
                    log.debug("creationDate: " + creationDate);

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                    String procDate = sdf.format(casSysctrlSysParamEntity.getProcessDate());
                    log.debug("procDate: " + procDate);

                    if (creationDate.compareTo(procDate) == 0) {

                      if (casSysctrlSysParamEntity.getEodRunInd().equalsIgnoreCase("N")) {
                        // addFile(fileName);
                          if (transmissionInd.equalsIgnoreCase("D")) {
                            generateFileOpsReg();
                            moveFile(fileName);
                          } else {
                            moveFile(fileName);
                          }
                      } else {
                        if (casSysctrlSysParamEntity.getEodRunInd().equalsIgnoreCase("Y")) {
                          log.error("File : " + fileName +
                              " cannot be processed. EOD has run for the day");
                        } else {
                          if (casSysctrlSysParamEntity.getEodRunInd().equalsIgnoreCase("R")) {
                            log.error(
                                "File : " + fileName + " cannot be processed. EOD is running.");
                          }
                        }
                      }
                    } else {
                      log.error("File : " + fileName + " has an incorrect processing date");
                    }
                  } else {
                    log.error("Files not being processed as there is no active processing day");
                  }
                } // end of check if filname length > 37
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
          } // end of watch Key
          catch (Exception e) {
            valid = true;
            e.printStackTrace();
            monitor();
          }
        }

      } while (valid);

    } catch (IOException | InterruptedException ex) {
      log.error(ex.getMessage());
    }

  }

  private static boolean generateFileOpsReg() {
    log.debug("*******************generating ops file reg record for fileloader......");
    opsFileRegModel = new OpsFileRegModel();

    Date date = new Date();

    log.debug("File Created Date" + date);
    opsFileRegModel.setFileName(fileName);
    opsFileRegModel.setFilepath(pathFile + fileName);
    opsFileRegModel.setProcessDate(date);
    opsFileRegModel.setReason(null);
    opsFileRegModel.setStatus("W");
    opsFileRegModel.setOnlineInd("N");
    opsFileRegModel.setInOutInd("I");
    String serviceId = (fileName.substring(3, 8)).trim();
    opsFileRegModel.setService(serviceId);

    Boolean result = adminBeanRemote.createOpsFileRegModel(opsFileRegModel);
    log.debug("result in generateFileOpsReg: " + result);
    return result;

  }

  private static void moveFile(String file) throws IOException {

    Path source = Paths.get("/home/opsjava/Delivery/Cession_Assign/Input/" + file);
    Path target = Paths.get("/home/opsjava/Delivery/Cession_Assign/Input/Processing/" + file);
    Files.move(source, target);
    log.debug("source fileName------>" + source);
    log.debug("target fileName------>" + target);

  }

  private static void contextAdminBeanCheck() {
    if (adminBeanRemote == null) {
      adminBeanRemote = Util.getAdminBean();
    }
  }

}
