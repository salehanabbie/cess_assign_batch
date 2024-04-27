

package com.bsva.billing;

import com.bsva.PropertyUtil;
import com.bsva.entities.CasOpsBillingCtrlsEntity;
import com.bsva.entities.CasSysctrlSysParamEntity;
import com.bsva.entities.InterchgBillingDataModel;
import com.bsva.entities.CasOpsDailyBillingEntity;
import com.bsva.entities.CasOpsTxnsBillingEntity;
import com.bsva.entities.ObsBillingStagingEntity;
import com.bsva.entities.ObsTxnsBillStagingEntity;
import com.bsva.interfaces.AdminBeanRemote;
import com.bsva.interfaces.FileProcessBeanRemote;
import com.bsva.interfaces.ServiceBeanRemote;
import com.bsva.translator.ServiceTranslator;
import com.bsva.utils.Util;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;


/**
 * @author SalehaR
 * @author SalehaR-2019/05/06 Debug Statements & Code CleanUp
 */
public class BillingExport implements Serializable {
  private static final long serialVersionUID = 1L;

  public static Logger log = Logger.getLogger(BillingExport.class);

  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

  @EJB
  PropertyUtil propertyUtil;

  public static ServiceBeanRemote beanRemote;
  public static AdminBeanRemote adminBeanRemote;
  public static FileProcessBeanRemote fileProcessBeanRemote;

  CasSysctrlSysParamEntity systemParamsEntity;

  List<ObsBillingStagingEntity> obsBillingStagingList;
  List<CasOpsDailyBillingEntity> dailyBillingListToExport;

  List<ObsTxnsBillStagingEntity> obsTxnsBillStagingList;
  List<CasOpsTxnsBillingEntity> txnsBillingList;
  List<CasOpsTxnsBillingEntity> orgnTxnsBillingList = null;

  List<String> serviceList = new ArrayList<String>();

  Date actionDate;
  String nonActInd, pain012Service, mdte002Service, srinpService;
  BigDecimal lastBillSeqNo;

  public BillingExport() {
    contextCheck();
    contextAdminBeanCheck();
    contextFileProcBeanCheck();

    try {
      //this.propertyCache = PropertyCacheFile.getInstance();
      propertyUtil = new PropertyUtil();

      pain012Service = propertyUtil.getPropValue("Input.Pain012");
      mdte002Service = propertyUtil.getPropValue("Input.Mdte002");
      srinpService = propertyUtil.getPropValue("Input.Pacs002Resp");

      //			tt2TxnType = propertyUtil.getPropValue("AC.TT2SubService");
      //			tt2Succ = propertyUtil.getPropValue("AC.Bill.TxnSucc");
      //			tt2Unsucc = propertyUtil.getPropValue("AC.Bill.TxnUnSucc");
      nonActInd = propertyUtil.getPropValue("NonActiveInd");
    } catch (Exception e) {
      log.error("BillingExport - Could not find MandateMessageCommons.properties in classpath");
    }

    //System Date
    systemParamsEntity = (CasSysctrlSysParamEntity) adminBeanRemote.retrieveActiveSysParameter();
  }


  public void exportInterchangeBilling() {
    lastBillSeqNo = BigDecimal.ZERO;
    //		if(systemParamsEntity != null)
    //		{
    //			actionDate = systemParamsEntity.getProcessDate();
    //		}
    //		else
    //		{
    //			actionDate = new Date();
    //		}

    //Retrieve Interchange Data to be exported
    //2017-07-10 - SalehaR - This must return the list first then update the seqNo
    //		BigDecimal lastBillSeqNo = beanRemote.retrieveDailyBillingInterCngInfo();
    //		log.info("lastBillSeqNo DATA ==> "+lastBillSeqNo);

    dailyBillingListToExport = new ArrayList<CasOpsDailyBillingEntity>();
    dailyBillingListToExport =
        (List<CasOpsDailyBillingEntity>) beanRemote.retrieveDailyBillingInterCngInfo();

    if (dailyBillingListToExport != null && dailyBillingListToExport.size() > 0) {
      CasOpsDailyBillingEntity acOpsDailyBillingEntity =
          dailyBillingListToExport.get(dailyBillingListToExport.size() - 1);
      lastBillSeqNo = acOpsDailyBillingEntity.getSystemSeqNo();
    }

    //New Entity Info
    CasOpsBillingCtrlsEntity billCntrlEntity = new CasOpsBillingCtrlsEntity();
    billCntrlEntity = (CasOpsBillingCtrlsEntity) beanRemote.retrieveBillingCtrls("BILLING");

    //Retrieve Interchange Billing Data
    List<InterchgBillingDataModel> intChgBillingList = new ArrayList<InterchgBillingDataModel>();
    String currentSeqNo = String.valueOf(billCntrlEntity.getCurrentSeqNo());
    String lastSeqNo = String.valueOf(billCntrlEntity.getLastSeqNo());
    log.info("currentSeqNo==> " + currentSeqNo);
    log.info("lastSeqNo==> " + lastSeqNo);

    intChgBillingList =
        (List<InterchgBillingDataModel>) beanRemote.retrieveInterchangeBillingData(currentSeqNo,
            lastBillSeqNo.toString());
    //		log.debug("intChgBillingList DATA ==> "+intChgBillingList);
    //		log.debug("intChgBillingList DATA.SIZE ==> "+intChgBillingList.size());

    if (intChgBillingList != null && intChgBillingList.size() > 0) {
      obsBillingStagingList = new ArrayList<ObsBillingStagingEntity>();
      String windowNo = null;
      windowNo = String.valueOf(billCntrlEntity.getBillingWindow() + 1);

      for (InterchgBillingDataModel interchgBillingDataModel : intChgBillingList) {
        //Translate billing to obs staging billing
        ObsBillingStagingEntity obsBillingStagingEntity =
            new ServiceTranslator().translateIntchgBillingModelToObsBillingStaging(
                interchgBillingDataModel, windowNo);
        obsBillingStagingList.add(obsBillingStagingEntity);
      }

      //			log.debug("obsBillingStagingList DATA ==> "+obsBillingStagingList);
      //			log.debug("obsBillingStagingList DATA.SIZE ==> "+obsBillingStagingList.size());
      //Save the Billing Txns
      if (obsBillingStagingList != null && obsBillingStagingList.size() > 0) {
        //				2021/04/20-SalehaR- Convert to BulkInserts
        //				for(ObsBillingStagingEntity obsBillingStagingEntity :
		  //				obsBillingStagingList)
        //				{
        //					beanRemote.createBillingRecords(obsBillingStagingEntity);
        //				}

        saveBulkInterchangeBilling(obsBillingStagingList);

        //Update Billing Entries once pushed
        log.info("======UPDATING OPS DAILY BILLING INDICATOR=======");
        List<BigDecimal> dailyBillSeqNoList = new ArrayList<BigDecimal>();
        for (CasOpsDailyBillingEntity dailyBillEntity : dailyBillingListToExport) {
          dailyBillSeqNoList.add(dailyBillEntity.getSystemSeqNo());
        }

        int targetSize = 1000;
        List<List<BigDecimal>> partitionList = null;

        if (dailyBillSeqNoList != null && dailyBillSeqNoList.size() > 0) {
          //Partition List
          partitionList = ListUtils.partition(dailyBillSeqNoList, targetSize);
//					log.info("Daily billing partition List size: "+partitionList.size());

          for (List<BigDecimal> accpList : partitionList) {
            String joinResult = null;
//						This join method can only be used on JAVA 8
//						joinResult = String.join(",", accpList);
            joinResult = StringUtils.join(accpList, ",");
            String sqlQuery = new String(
                "UPDATE MANOWNER.MDT_AC_OPS_DAILY_BILLING SET BILL_EXP_STATUS = 'Y' WHERE " +
						"SYSTEM_SEQ_NO IN (" +
                    joinResult + ")");
            fileProcessBeanRemote.bulkUpdateBySQL(sqlQuery);
          }
        }

        //				2021/04/20-SalehaR: Add bulk update for MDT_OPS_DAILY_BILLING
        //				for (MdtAcOpsDailyBillingEntity dailyBillEntity :
		  //				dailyBillingListToExport)
        //				{
        //					dailyBillEntity.setBillExpStatus("Y");
        //					beanRemote.updateDailyBillingInd(dailyBillEntity);
        //				}


        //Update SeqNo's
        billCntrlEntity.setLastSeqNo(billCntrlEntity.getCurrentSeqNo());
        billCntrlEntity.setCurrentSeqNo(lastBillSeqNo);
        billCntrlEntity.setBillingWindow(Short.valueOf(windowNo));
        beanRemote.saveBillingCntrl(billCntrlEntity);
        log.info("Interchange Billing data has been pushed succsesfully ...");

      }
    }
  }


  public void exportTxnBilling() {
    txnsBillingList = new ArrayList<CasOpsTxnsBillingEntity>();
    txnsBillingList =
        (List<CasOpsTxnsBillingEntity>) beanRemote.retrievetxnsBilingToExport(nonActInd);
    //log.info("txnsBillingList DATA.SIZE ==> "+txnsBillingList.size());
    orgnTxnsBillingList = new ArrayList<CasOpsTxnsBillingEntity>();

    if (txnsBillingList != null && txnsBillingList.size() > 0) {
      obsTxnsBillStagingList = new ArrayList<ObsTxnsBillStagingEntity>();

      for (CasOpsTxnsBillingEntity casOpsTxnsBillingEntity : txnsBillingList) {

        ObsTxnsBillStagingEntity obsTxnsBillStagingEntity =
            new ServiceTranslator().translateOpsTxnsBillingModelToObsTxnsBillingStaging(
                casOpsTxnsBillingEntity);
        obsTxnsBillStagingList.add(obsTxnsBillStagingEntity);

        //log.info("obsTxnsBillStagingList DATA.SIZE ==> "+obsTxnsBillStagingList.size());
      }

      if (obsTxnsBillStagingList != null && obsTxnsBillStagingList.size() > 0) {
        //				2021/04/20-SalehaR- Convert to BulkInserts
        //				for(ObsTxnsBillStagingEntity obsTxnsBillStagingEntity
		  //				:obsTxnsBillStagingList)
        //				{
        //					beanRemote.createOpsTxnBillingRecords(obsTxnsBillStagingEntity);
        //				}

        saveBulkTxnBilling(obsTxnsBillStagingList);

        //Update Billing Entries once pushed
        log.info("======UPDATING OPS DAILY TXNS BILL INDICATOR=======");
        List<BigDecimal> dlyTxnsBillSeqNoList = new ArrayList<BigDecimal>();
        for (CasOpsTxnsBillingEntity txnsBillingEntity : txnsBillingList) {
          dlyTxnsBillSeqNoList.add(
              txnsBillingEntity.getCasOpsTxnsBillingPK().getSystemSeqNo());
        }

        int targetSize = 1000;
        List<List<BigDecimal>> partitionList = null;

        if (dlyTxnsBillSeqNoList != null && dlyTxnsBillSeqNoList.size() > 0) {
          //Partition List
          partitionList = ListUtils.partition(dlyTxnsBillSeqNoList, targetSize);
//					log.info("Txns Billing partition List size: "+partitionList.size());

          for (List<BigDecimal> accpList : partitionList) {
            String joinResult = null;
//						This join method can only be used on JAVA 8
//						joinResult = String.join(",", accpList);
            joinResult = StringUtils.join(accpList, ",");
            String sqlQuery = new String(
                "UPDATE MANOWNER.MDT_AC_OPS_TXNS_BILLING SET BILL_EXP_STATUS = 'Y' WHERE " +
						"SYSTEM_SEQ_NO IN (" +
                    joinResult + ")");
            fileProcessBeanRemote.bulkUpdateBySQL(sqlQuery);
          }
        }


//				2021-04-20: SalehaR - Bulk Update to MDT_AC_OPS_TXNS_BILLING
//				//Update Billing Entries once pushed
//				log.info("Updating Daily Billing Indicator");
//				for (MdtAcOpsTxnsBillingEntity acOpsTxnsBillingEntity : txnsBillingList) 
//				{
//					acOpsTxnsBillingEntity.setBillExpStatus("Y");
//					beanRemote.updateOpsTxnBillingInd(acOpsTxnsBillingEntity);
//					//orgnTxnsBillingList.add(acOpsTxnsBillingEntity);
//				}

        //saveBulkConfirmations();
        log.info("Batch Transactional Billing data has been pushed succsesfully ...");
      }
    }
  }

  public void saveBulkConfirmations() {
    log.info("xxxxx In the saveBulkConfirmations xxxxxxxx");
    //log. info("orgnTxnsBillingList size--->"+orgnTxnsBillingList.size());
    fileProcessBeanRemote.openHibernateSession();

    try {
      fileProcessBeanRemote.bulkUpdateMandates(orgnTxnsBillingList);
    } catch (Exception e) {
      log.error("Error on saveBulkConfirmations: " + e.getMessage());
      e.printStackTrace();
    }
    fileProcessBeanRemote.closeHibernateSession();
  }

  public void saveBulkInterchangeBilling(List<ObsBillingStagingEntity> bulkList) {
    fileProcessBeanRemote.openHibernateSession();

    try {
      log.info("====================BULK INSERT INTERCHANGE BILLING DATA====================");
      fileProcessBeanRemote.bulkSaveMandates(bulkList);
    } catch (Exception e) {
      log.error("Error on saveBulkInterchangeBilling: " + e.getMessage());
      e.printStackTrace();
    }
    fileProcessBeanRemote.closeHibernateSession();
  }

  public void saveBulkTxnBilling(List<ObsTxnsBillStagingEntity> bulkTxnList) {
    fileProcessBeanRemote.openHibernateSession();

    try {
      log.info("====================BULK INSERT TXN BILLING DATA====================");
      fileProcessBeanRemote.bulkSaveMandates(bulkTxnList);
    } catch (Exception e) {
      log.error("Error on saveBulkTxnBilling: " + e.getMessage());
      e.printStackTrace();
    }
    fileProcessBeanRemote.closeHibernateSession();
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

  public static void contextFileProcBeanCheck() {
    if (fileProcessBeanRemote == null) {
      fileProcessBeanRemote = Util.getFileProcessBean();
    }
  }

}

