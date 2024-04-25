package com.bsva.authcoll.singletable.file;

import com.bsva.PropertyUtil;
import com.bsva.commons.model.OpsFileRegModel;
import com.bsva.delivery.EndOfTransmission_FileLoader;
import com.bsva.delivery.StartOfTransmission_FileLoader;
import com.bsva.entities.MdtOpsCustParamEntity;
import com.bsva.entities.MdtOpsFileRegEntity;
import com.bsva.entities.MdtOpsServicesEntity;
import com.bsva.entities.CasSysctrlSysParamEntity;
import com.bsva.interfaces.AdminBeanRemote;
import com.bsva.interfaces.ServiceBeanRemote;
import com.bsva.interfaces.ValidationBeanRemote;
import com.bsva.utils.Util;
import java.io.Serializable;
import java.util.Date;
import javax.ejb.EJB;
import org.apache.log4j.Logger;

/**
 * @author SalehaR-2019/09/21: Call single table loader
 */
public class FileLoader_ST implements Serializable {
  @EJB
  static PropertyUtil propertyUtil;
  public static Logger log = Logger.getLogger(FileLoader_ST.class);

  public static StartOfTransmission_FileLoader startOfTransmission_FileLoader;
  public static EndOfTransmission_FileLoader endOfTransmission_FileLoader;

  //AC LOADERS (FLAT TABLE)
  public static AC_Pain012_Loader_ST acPain012_Loader_ST;
  public static AC_Pacs002_Loader_ST acPacs002_Loader_ST;
  public static AC_Pain010_Loader_ST acPain010_Loader_ST;

  private static OpsFileRegModel opsFileRegModel;
  private static AdminBeanRemote adminBeanRemote;
  public static ValidationBeanRemote valBeanRemote;
  public static ServiceBeanRemote BeanRemote;
  private static MdtOpsCustParamEntity mdtOpsCustParamEntity;
  private static String fileNr = null;
  private static String fileName = null;
  private static String trimmedFileName = null;
  private static String backEndProcess = "BACKEND";
  private static final long serialVersionUID = 1L;
  private static String pathFile = null;
  private static String sadcSystem = "SADC";
  private static String acSystem = "AC";
  private static CasSysctrlSysParamEntity casSysctrlSysParamEntity;
  private static String transmissionInd;
  private static String dataTransInd = "D";
  private static String endTransInd = "E";
  private static String startTransInd = "S";
  private static String testLiveIndProp = null;
  private String received = "R";

  public void loadFile(String fileName, String pathFile) {

    try {
      propertyUtil = new PropertyUtil();
      this.testLiveIndProp = propertyUtil.getPropValue("TestLiveInd");
      //log.info("Test Live Indicator Property: "+testLiveIndProp);
    } catch (Exception ex) {
      log.error("FileLoader_ST - Could not find MandateMessageCommons.properties in classpath");
    }

    trimmedFileName = fileName.substring(0, 37).trim();

    FileLoader_ST.fileName = fileName;
    FileLoader_ST.pathFile = pathFile;

    contextAdminBeanCheck();
    contextValidationBeanCheck();
    contextServiceBeanCheck();

    /* CHECK IF SERVICE IS ACTIVE */
    String serviceId = (fileName.substring(3, 8)).trim();
    log.debug("Service Id from File---> " + serviceId);
    String incomingBicCode = (fileName.substring(8, 16)).trim();
    fileNr = (fileName.substring(25, 31)).trim();
    transmissionInd = fileName.substring(32, 33);
    log.debug("transmissionInd : " + transmissionInd);
    MdtOpsServicesEntity mdtOpsServicesEntity =
        (MdtOpsServicesEntity) valBeanRemote.retrieveOpsServiceIn(serviceId);
    mdtOpsCustParamEntity =
        (MdtOpsCustParamEntity) valBeanRemote.retrieveOpsCustomerParameters(incomingBicCode,
            backEndProcess);

    casSysctrlSysParamEntity =
        (CasSysctrlSysParamEntity) adminBeanRemote.retrieveActiveSysParameter();

    MdtOpsFileRegEntity mdtOpsFileRegEntity =
        (MdtOpsFileRegEntity) valBeanRemote.retrieveOpsFileReg(fileName);

    if (casSysctrlSysParamEntity != null) {
      if (mdtOpsServicesEntity != null) {
        if (mdtOpsServicesEntity.getActiveInd().equalsIgnoreCase("Y")) {
          if (serviceId.equalsIgnoreCase("MANAM") &&
              transmissionInd.equalsIgnoreCase(dataTransInd)) {
            log.debug("FILE TYPE IS MANAM");
            String namespace = null;
            if (mdtOpsCustParamEntity != null) {
              namespace = mdtOpsCustParamEntity.getManAmdXsdNs();
            } else {
              namespace = "iso.std.iso._20022.tech.xsd.pain_010_001";
            }
            boolean result = updateFileOpsReg(namespace, mdtOpsFileRegEntity);

            if (result == true) {
              log.info("*************Loading & Validating File " + fileName +
                  "*************************");
              acPain010_Loader_ST = new AC_Pain010_Loader_ST(pathFile + fileName, fileName);
            } //result
            else {
              log.error("Error in adminBeanRemote.createOpsFileRegModel");
            }
          }//end of if MANAM

          if (serviceId.equalsIgnoreCase("ST101") &&
              transmissionInd.equalsIgnoreCase(dataTransInd)) {
            log.debug("FILE TYPE IS ST101");
            String namespace = null;
            if (mdtOpsCustParamEntity != null) {
              namespace = mdtOpsCustParamEntity.getManStatusRepXsdNs();
            } else {
              namespace = "iso:std:iso:20022:tech:xsd:pacs.002.001";
            }

            boolean result = updateFileOpsReg(namespace, mdtOpsFileRegEntity);

            if (result == true) {
              log.info("*************Loading & Validating File " + fileName +
                  "*************************");
              acPacs002_Loader_ST = new AC_Pacs002_Loader_ST(pathFile + fileName, fileName);
            } //result
            else {
              log.error("Error in adminBeanRemote.createOpsFileRegModel");
            }
          }


          //***********************Checking if it is MANAC
          if (serviceId.equalsIgnoreCase("MANAC") &&
              transmissionInd.equalsIgnoreCase(dataTransInd)) {
            log.debug("FILE TYPE IS MANAC");
            String namespace = null;
            if (mdtOpsCustParamEntity != null) {
              namespace = mdtOpsCustParamEntity.getManAccpXsdNs();
            } else {
              namespace = "iso.std.iso._20022.tech.xsd.pain_012_001";
            }
            boolean result = updateFileOpsReg(namespace, mdtOpsFileRegEntity);

            if (result == true) {
              log.info("*************Loading & Validating File " + fileName +
                  "*************************");
              acPain012_Loader_ST = new AC_Pain012_Loader_ST(pathFile + fileName, fileName);
            } //result
            else {
              log.error("Error in adminBeanRemote.createOpsFileRegModel");
            }
          }//end of if MANAC
        } else {
          log.error("********************" + serviceId +
              " is not in an active status. File cannot be processed.********************");
        }
      }//end of if(mdtOpsServicesEntity != null)
      else {
        log.error("********************" + serviceId +
            " is not supported. File cannot be processed.********************");
      }
      //***********************Checking if it is EOT
      if (transmissionInd.equalsIgnoreCase(endTransInd)) {

        log.debug("FILE TYPE IS EOT");
        log.info("*************Loading File " + fileName + "*************************");
        if (casSysctrlSysParamEntity.getSysType().equalsIgnoreCase(acSystem)) {
          endOfTransmission_FileLoader =
              new EndOfTransmission_FileLoader(pathFile + fileName, fileName);
        }
      }

      if (transmissionInd.equalsIgnoreCase(startTransInd)) {
        log.debug("FILE TYPE IS SOT");
        log.info("*************Loading File " + fileName + "*************************");
        if (casSysctrlSysParamEntity.getSysType().equalsIgnoreCase(acSystem)) {
          startOfTransmission_FileLoader =
              new StartOfTransmission_FileLoader(pathFile + fileName, fileName);
        }
      }
    }// end of if(mdtSysctrlSysParamEntity != null)
    else {
      log.error("The system is not in an active state. Please check system parameters data!");
    }
  }

  private boolean updateFileOpsReg(String namespace, MdtOpsFileRegEntity mdtOpsFileRegEntity) {
    boolean result = false;
    if (mdtOpsFileRegEntity != null) {
      mdtOpsFileRegEntity.setStatus(received);
      mdtOpsFileRegEntity.setNameSpace(namespace);
      result = valBeanRemote.updateOpsFileReg(mdtOpsFileRegEntity);
    }

    return result;
  }

  private static boolean generateFileOpsReg(String namespace) {
    log.debug("*******************generating ops file reg record for fileloader......");
    opsFileRegModel = new OpsFileRegModel();

    Date date = new Date();

    log.debug("File Created Date" + date);
    String newFileName = fileName;

    opsFileRegModel.setFileName(newFileName);
    opsFileRegModel.setFilepath(pathFile + fileName);
    opsFileRegModel.setNameSpace(namespace);
    opsFileRegModel.setProcessDate(date);
    opsFileRegModel.setReason(null);
    opsFileRegModel.setStatus("R");
    opsFileRegModel.setOnlineInd("N");
    opsFileRegModel.setInOutInd("I");

    Boolean result = adminBeanRemote.createOpsFileRegModel(opsFileRegModel);
    log.debug("result in generateFileOpsReg: " + result);
    return result;

  }

  private static void contextServiceBeanCheck() {
    if (BeanRemote == null) {
      BeanRemote = Util.getServiceBean();
    }
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

