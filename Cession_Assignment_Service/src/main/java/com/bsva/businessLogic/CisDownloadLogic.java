
package com.bsva.businessLogic;

import com.bsva.cis.ejb.controller.CisServiceBean;
import com.bsva.cis.persistence.dto.ACMemberDTO;
import com.bsva.cis.persistence.dto.BranchDTO;
import com.bsva.cis.persistence.dto.MemberDTO;
import com.bsva.entities.CasSysctrlSlaTimesEntity;
import com.bsva.entities.CasOpsProcessControlsEntity;
import com.bsva.entities.CasOpsSlaTimesEntity;
import com.bsva.entities.SysCisBankEntity;
import com.bsva.entities.SysCisBranchEntity;
import com.bsva.interfaces.AdminBeanRemote;
import com.bsva.interfaces.ServiceBeanRemote;
import com.bsva.utils.Util;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;


/**
 * @author DimakatsoN
 */
public class CisDownloadLogic {


  public static Logger log = Logger.getLogger(CisDownloadLogic.class);
  public static AdminBeanRemote adminBeanRemote;
  public static ServiceBeanRemote serviceBeanRemote;
  public static CisServiceBean cisServiceBean;
  private String systemName = "MANOWNER";
  private String memberNo;
  Date processingDate;
  SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
  Date currentDate = new Date();
  String actualDate = dateFormat.format(currentDate).toString();
  List<MemberDTO> memberDTOList;
  SysCisBranchEntity sysCisBranchEntity = new SysCisBranchEntity();
  String activeInd;
  CasSysctrlSlaTimesEntity casSysctrlSlaTimesEntity;
  public String cisFeedbackMsg;
  boolean cisCheck = false;
  CasOpsSlaTimesEntity casOpsSlaTimesEntity;


  public CisDownloadLogic() {
    contextAdminBeanCheck();
    contextServiceBeanCheck();
  }

  public boolean cisDownloadImplementation() throws ParseException {
    casSysctrlSlaTimesEntity = (CasSysctrlSlaTimesEntity) adminBeanRemote.retrieveCisStartTime();
    SimpleDateFormat parser = new SimpleDateFormat("HH:mm");

    Calendar cal = Calendar.getInstance();
    cal.setTime(currentDate);

    //Put it back in the Date object
    currentDate = cal.getTime();

    String strrDate = parser.format(currentDate);
    log.info("Time: " + strrDate);
    Date userDate = parser.parse(strrDate);

    Date eodTime = parser.parse(casSysctrlSlaTimesEntity.getStartTime());
    Date endTime = parser.parse(casSysctrlSlaTimesEntity.getEndTime());

    if (userDate.after(eodTime) && userDate.before(endTime)) {
      boolean cisBankSaved = false;
      boolean cisBranchSaved = false;
      boolean cisBranchCDVParams = false;
      boolean cisDwnExists = false;

      List<CasOpsProcessControlsEntity> mdtAcOpsProcessControlsList =
          new ArrayList<CasOpsProcessControlsEntity>();
      CasOpsProcessControlsEntity casOpsProcessControlsEntity =
          new CasOpsProcessControlsEntity();

      mdtAcOpsProcessControlsList =
          (List<CasOpsProcessControlsEntity>) adminBeanRemote.retrieveInActiveCisDownload();
      log.debug("mdtAcOpsProcessControlsListin cisDowloadLogic: " + mdtAcOpsProcessControlsList);


      if (mdtAcOpsProcessControlsList.size() > 0) {
        for (CasOpsProcessControlsEntity localEnity : mdtAcOpsProcessControlsList) {
          if (localEnity.getProcessDate().equals(currentDate)) {
            cisDwnExists = true;
            //						log.debug("mdtAcOpsProcessControlsListin date: "+ localEnity
            //						.getProcessDate().equals(currentDate));
            cisFeedbackMsg =
                "CIS Download already exists for processing date: " + localEnity.getProcessDate();
            log.info(
                "CIS Download already exists for processing date: " + localEnity.getProcessDate());
          }
        }
      }

      log.debug("cisDwnExists" + cisDwnExists);
      if (cisDwnExists = true) {
        cisBankSaved = populatesMdtAcCisMember();
        cisBranchSaved = populatesMdtAcCisBranch();

        if (cisBankSaved && cisBranchSaved) {
          cisCheck = true;
          cisFeedbackMsg = "CIS Download has completed successfully !";
          updateCisDownloadIndAndDate(casOpsProcessControlsEntity);
          //cisfeedbackMsg = "CIS Download has ran successfully";
        }
      }
    } else {
      cisFeedbackMsg = "Verify SLA TIMES for CIS to run";
    }

    if (cisCheck) {
      System.out.println("cisCheckxxxxx");
      ;
      cisFeedbackMsg = "CIS Download has completed successfully!";
    }

    return cisCheck;
  }

  public boolean populatesMdtAcCisMember() {
    log.info("<===== Populating SYS CIS BANK Table =====>");
    boolean saved = false;
    memberDTOList = new ArrayList<MemberDTO>();
    memberDTOList = (List<MemberDTO>) adminBeanRemote.retrieveRelatedMember();

    List<SysCisBankEntity> sysCisBankList = new ArrayList<SysCisBankEntity>();

    sysCisBankList = (List<SysCisBankEntity>) adminBeanRemote.retrieveSysCisBank();

    List<ACMemberDTO> cisBankCrDrInfoList =
        (List<ACMemberDTO>) adminBeanRemote.retrieveCisBankDebtorCreditorInfo();
//		log.info("cisBankCrDrInfoList.size ==> "+cisBankCrDrInfoList.size());

    if (sysCisBankList.size() == 0) {
      if (memberDTOList != null && memberDTOList.size() > 0 && cisBankCrDrInfoList != null &&
          cisBankCrDrInfoList.size() > 0) {
        //				log.debug("the memberDTOList is SIZE************" + memberDTOList.size());
        for (MemberDTO memberDTO : memberDTOList) {
          SysCisBankEntity sysCisBankEntity = new SysCisBankEntity();
          if (memberDTO != null && memberDTO.getMemberNo() != null) ;
          {
            sysCisBankEntity.setMemberNo(memberDTO.getMemberNo());
            for (ACMemberDTO cisBankCrDrInfoEntity : cisBankCrDrInfoList) {
              if (cisBankCrDrInfoEntity.getMemberNo().equalsIgnoreCase(memberDTO.getMemberNo())) {
                sysCisBankEntity.setAcCreditor(
                    String.valueOf(cisBankCrDrInfoEntity.getAcCreditor()));
                sysCisBankEntity.setAcDebtor(String.valueOf(cisBankCrDrInfoEntity.getAcDebtor()));
              }
            }
          }

          if (memberDTO != null && memberDTO.getMemberName() != null) ;
          sysCisBankEntity.setMemberName(memberDTO.getMemberName());

          sysCisBankEntity.setMaxNrRecords(BigDecimal.valueOf(memberDTO.getAcMdtMaxNrRecords()));
          sysCisBankEntity.setNrOfDaysProc(BigDecimal.valueOf(memberDTO.getAcMdtNrOfDaysProc()));
          if (memberDTO != null && memberDTO.getAcMdtPubHolProc() != null) ;
          sysCisBankEntity.setPubHolProc(memberDTO.getAcMdtPubHolProc());

          saved = adminBeanRemote.createSysCisBank(sysCisBankEntity);
        }
      }

      if (saved) {
        log.info("<===== SYS CIS BANK Table has been populated =====>");
      } else {
        log.info("<===== Error populating SYS CIS BANK Table =====>");
      }
    }
    return saved;
  }

  public boolean populatesMdtAcCisBranch() {
    log.info("<===== Populating SYS CIS BRANCH Table =====>");
    boolean saved = false;

    //		log.debug("In populatesMdtAcCisBranch() . . . ");
    List<MemberDTO> memberDTOList = new ArrayList<MemberDTO>();

    memberDTOList = (List<MemberDTO>) adminBeanRemote.retrieveRelatedMember();

    List<BranchDTO> branchDTOList = new ArrayList<BranchDTO>();

    List<SysCisBranchEntity> mdtAcCisBranchList = new ArrayList<SysCisBranchEntity>();


    if (memberDTOList != null && memberDTOList.size() > 0) {
      for (MemberDTO memberDTO : memberDTOList) {
        if (memberDTO.getAcMdtInd().equalsIgnoreCase("Y")) {
          //1. retrieve CIS Branch by member
          branchDTOList =
              (List<BranchDTO>) adminBeanRemote.retrieveMndCisBranch(memberDTO.getMemberNo());

          //					if(branchDTOList != null)
          //						if(branchDTOList.size() > 0)
          //							log.info("branchDTOList
          //							size*********************************"+ branchDTOList.size
          //							());
          if (branchDTOList != null && branchDTOList.size() > 0) {
            for (BranchDTO branchDTO : branchDTOList) {
              SysCisBranchEntity sysCisBranchEntity = new SysCisBranchEntity();

              if (branchDTO != null && branchDTO.getBranchNo() != null) {
                sysCisBranchEntity.setBranchNo(branchDTO.getBranchNo());
              }
              if (branchDTO != null && branchDTO.getBranchName() != null) {
                sysCisBranchEntity.setBranchName(branchDTO.getBranchName());
              }
              if (branchDTO != null && branchDTO.getDivision() != null) {
                sysCisBranchEntity.setDivision(branchDTO.getDivision());
              }
              if (branchDTO != null && branchDTO.getMemberNo() != null) {
                sysCisBranchEntity.setMemberNo(branchDTO.getMemberNo());
              }
              if (branchDTO != null && branchDTO.getAcCreditor() != null) {
                sysCisBranchEntity.setAcCreditor(branchDTO.getAcCreditor());
              }
              if (branchDTO != null && branchDTO.getAcCreditor() != null) {
                sysCisBranchEntity.setAcDebtor(branchDTO.getAcDebtor());
              }

              saved = adminBeanRemote.createSysCisBranch(sysCisBranchEntity);
            }
          }
        }
      }
    }
    if (saved) {
      log.info("<===== SYS CIS BRANCH Table has been populated =====>");
    } else {
      log.info("<===== Error populating SYS CIS BRANCH Table =====>");
    }
    return saved;
  }

  public void updateCisDownloadIndAndDate(
      CasOpsProcessControlsEntity casOpsProcessControlsEntity) {
    casOpsProcessControlsEntity = new CasOpsProcessControlsEntity();
    // mdtSysctrlSysParamEntity= (MdtAcOpsProcessControlsEntity) adminBeanRemote
    // .retrieveActiveSysParameter();
    if (casOpsProcessControlsEntity != null) {
      casOpsProcessControlsEntity.setCisDownloadInd("Y");

      Calendar cal = Calendar.getInstance();
      cal.setTime(currentDate);

      //Set Time Fields to zero
      cal.set(Calendar.HOUR_OF_DAY, 0);
      cal.set(Calendar.MINUTE, 0);
      cal.set(Calendar.SECOND, 0);
      cal.set(Calendar.MILLISECOND, 0);
      cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH));
      //Put it back in the Date object
      currentDate = cal.getTime();

      casOpsProcessControlsEntity.setProcessDate(currentDate);

      boolean saved = adminBeanRemote.createCisDownload(casOpsProcessControlsEntity);
      if (saved) {
        log.info("<===== CIS download indicator and date has been updated =====>");
      }
    }
  }

  private static void contextAdminBeanCheck() {
    if (adminBeanRemote == null) {
      adminBeanRemote = Util.getAdminBean();
    }
  }

  private static void contextServiceBeanCheck() {
    if (serviceBeanRemote == null) {
      serviceBeanRemote = Util.getServiceBean();
    }
  }


}
