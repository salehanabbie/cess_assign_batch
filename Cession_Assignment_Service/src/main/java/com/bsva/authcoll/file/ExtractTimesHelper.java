package com.bsva.authcoll.file;

import com.bsva.PropertyUtil;
import com.bsva.entities.MdtOpsLastExtractTimesEntity;
import com.bsva.entities.MdtOpsServicesEntity;
import com.bsva.entities.CasSysctrlSysParamEntity;
import com.bsva.interfaces.AdminBeanRemote;
import com.bsva.interfaces.ServiceBeanRemote;
import com.bsva.interfaces.ValidationBeanRemote;
import com.bsva.utils.Util;
import javax.ejb.EJB;
import org.jboss.logging.Logger;

/**
 * @author DimakatsoN
 */

public class ExtractTimesHelper {

  @EJB
  PropertyUtil propertyUtil;
  private Logger log = Logger.getLogger(ExtractTimesHelper.class);
  private MdtOpsLastExtractTimesEntity opsLastExtractTimesEntity;
  public static ServiceBeanRemote beanRemote;
  private static AdminBeanRemote adminBeanRemote;
  public static ValidationBeanRemote valBeanRemote;
  private String extractStatus = "3";
  private String outgoingService;
  private String extractType = null;
  public static final String EXTRACT_ALL = "ALL";
  public static final String EXTRACT_SELECTIVE = "SELECTIVE";
  public static final String EXTRACT_NONE = "NONE";
  private CasSysctrlSysParamEntity casSysctrlSysParamEntity = null;
  private MdtOpsServicesEntity opsServiceEntity;


  public ExtractTimesHelper() {

    casSysctrlSysParamEntity = new CasSysctrlSysParamEntity();
    casSysctrlSysParamEntity =
        (CasSysctrlSysParamEntity) adminBeanRemote.retrieveActiveSysParameter();
    opsLastExtractTimesEntity = new MdtOpsLastExtractTimesEntity();
    opsServiceEntity = new MdtOpsServicesEntity();


  }


  private boolean isExtractTime(MdtOpsServicesEntity opsServiceEntity, int nrOfRecordsFileLimit) {
    return hasReachedExtractTime(opsServiceEntity.getServiceIdOut()) ||
        hasReachedMaxExtractVolume(opsServiceEntity, nrOfRecordsFileLimit);
  }


  private boolean hasReachedMaxExtractVolume(MdtOpsServicesEntity opsServiceEntity,
                                             int nrOfRecordsFileLimit) {
    if (opsServiceEntity == null) {
      extractType = EXTRACT_NONE;
      log.error("Failed to determine ExtractTime. Object MdtOpsServicesEntity is null.");
      return false;
    }
    return false;


  }


  private boolean hasReachedExtractTime(String serviceIdOut) {
    // TODO Auto-generated method stub
    return false;
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


  public static void contextValidationBeanCheck() {
    if (valBeanRemote == null) {
      valBeanRemote = Util.getValidationBean();
    }
  }

}
