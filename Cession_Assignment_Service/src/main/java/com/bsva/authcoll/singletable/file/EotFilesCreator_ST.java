package com.bsva.authcoll.singletable.file;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import org.apache.log4j.Logger;
import org.hibernate.ObjectNotFoundException;
import com.bsva.FileWatcher;
import com.bsva.PropertyUtil;
import com.bsva.delivery.EndOfTransmissionExtract;
import com.bsva.entities.CasOpsSotEotCtrlEntity;
import com.bsva.entities.CasOpsServicesEntity;
import com.bsva.entities.CasOpsSlaTimesEntity;
import com.bsva.entities.CasSysctrlServicesEntity;
import com.bsva.entities.CasSysctrlSysParamEntity;
import com.bsva.entities.SysCisBankEntity;
import com.bsva.interfaces.AdminBeanRemote;
import com.bsva.interfaces.FileProcessBeanRemote;
import com.bsva.interfaces.QuartzSchedulerBeanRemote;
import com.bsva.interfaces.ServiceBeanRemote;
import com.bsva.interfaces.TimerBeanRemote;
import com.bsva.interfaces.ValidationBeanRemote;
import com.bsva.utils.Util;

/**
 * 
 * @author DimakatsoN
 * @author SalehaR-2019/10/12 Align to Single Table
 */
public class EotFilesCreator_ST {

	@EJB
	PropertyUtil propertyUtil;

	public static Logger log = Logger.getLogger(EotFilesCreator_ST.class);

	private static AdminBeanRemote adminBeanRemote;
	public static ServiceBeanRemote beanRemote;
	public static ValidationBeanRemote valBeanRemote;
	public static FileProcessBeanRemote fileProcessBeanRemote;
	FileWatcher fileWatcher = new FileWatcher();
	public static TimerBeanRemote timerBeanRemote;
	private static QuartzSchedulerBeanRemote quartzSchedulerBeanRemote;
	String file = null, destInstId = null, fileType = null, quartzSchedulerBeanRemoteservice = null;
	
	String carinServ, rcainServ, st201Serv;
	String carotServ, rcaotServ, st203Serv;
	String st200Serv, st202Serv, st204Serv;
	String activeInd, nonActiveInd, mdtLoadType;
	List<CasSysctrlServicesEntity> sysCntrlServicesList = new ArrayList<CasSysctrlServicesEntity>();// list
	Date currentDate = new Date();
	boolean txnsToExtract = false;
	boolean fileStatusCarin = false,  fileStatusRcain = false, fileStatusSt201 = false,
			fileStatusSt200 = false, fileStatusSt202 = false, fileStatusSt204 = false,
			fileStatusCarot = false, fileStatusRcaot = false, fileStatusSt203 = false;
	public String feedbackMsg;
	CasSysctrlSysParamEntity casSysctrlSysParamEntity = new CasSysctrlSysParamEntity();
	boolean carinToExtract = false, rcainToExtract = false, st03ToExtract = false,
			st200ToExtract = false, st202ToExtract = false, st204ToExtract = false;

	public EotFilesCreator_ST() {
		contextAdminBeanCheck();
		contextValidationBeanCheck();
		contextCheck();
		contextQuartzSchedulerBeanCheck();
		contextTimerBeanCheck();
		contextFileProcessBeanCheck();

		try {
			propertyUtil = new PropertyUtil();
			this.activeInd = propertyUtil.getPropValue("ActiveInd");
			this.nonActiveInd = propertyUtil.getPropValue("NonActiveInd");

			this.carinServ = propertyUtil.getPropValue("Input.Pain010");
			this.rcainServ = propertyUtil.getPropValue("Input.Pain012");
			this.st201Serv = propertyUtil.getPropValue("Input.Pacs002");

			this.carotServ = propertyUtil.getPropValue("Output.Pain010");
			this.rcaotServ = propertyUtil.getPropValue("Output.Pain012");
			this.st203Serv = propertyUtil.getPropValue("Output.Pacs002");

			this.st200Serv = propertyUtil.getPropValue("StatusRep.ST200");
			this.st202Serv = propertyUtil.getPropValue("StatusRep.ST202");
			this.st204Serv = propertyUtil.getPropValue("StatusRep.ST204");
		} catch (Exception e) {
			log.error("EOTFilesCreator - Could not find CessionAssignment.properties in classpath");
		}
	}

	public boolean EotImplementation() throws ParseException, IOException {
		Boolean eotCreated = false;
		// Generate EOT files

		SimpleDateFormat parser = new SimpleDateFormat("HH:mm");

		Calendar cal = Calendar.getInstance();
		cal.setTime(currentDate);

		// Put it back in the Date object
		currentDate = cal.getTime();

		String strrDate = parser.format(currentDate);
		log.debug("Time: " + strrDate);
		Date userDate = parser.parse(strrDate);

		CasSysctrlSysParamEntity casSysctrlSysParamEntity = new CasSysctrlSysParamEntity();
		casSysctrlSysParamEntity = (CasSysctrlSysParamEntity) adminBeanRemote.retrieveActiveSysParameter();

		sysCntrlServicesList = (List<CasSysctrlServicesEntity>) adminBeanRemote.retrieveServiceControl();

		log.debug("sysCntrlServicesList.size()---->" + sysCntrlServicesList.size());

		List<SysCisBankEntity> sysCisBankList = new ArrayList<SysCisBankEntity>();
		sysCisBankList = adminBeanRemote.retrieveSysCisBank();

		log.debug("sysCisBankList.size()" + sysCisBankList.size());

		if (sysCisBankList != null && sysCisBankList.size() > 0 && sysCntrlServicesList != null
				&& sysCntrlServicesList.size() > 0) {
			log.debug("<<---------GENERATING EOT---------->");
			for (SysCisBankEntity sysCisBankEntity : sysCisBankList) {
				String memberId = sysCisBankEntity.getMemberNo();
				String debtorInd = sysCisBankEntity.getAcDebtor();
				String creditorInd = sysCisBankEntity.getAcCreditor();
				
				// Check if no files are sitting on 'V' or 'R' or 'W'
				fileStatusCarin = beanRemote.checkIfAllFilesLoaded(casSysctrlSysParamEntity.getProcessDate(), carinServ);
				fileStatusRcain = beanRemote.checkIfAllFilesLoaded(casSysctrlSysParamEntity.getProcessDate(), rcainServ);
				fileStatusSt201 = beanRemote.checkIfAllFilesLoaded(casSysctrlSysParamEntity.getProcessDate(), st201Serv);
				fileStatusSt200 = beanRemote.checkIfAllFilesLoaded(casSysctrlSysParamEntity.getProcessDate(), st200Serv);
				fileStatusSt202 = beanRemote.checkIfAllFilesLoaded(casSysctrlSysParamEntity.getProcessDate(), st202Serv);
				fileStatusSt204 = beanRemote.checkIfAllFilesLoaded(casSysctrlSysParamEntity.getProcessDate(), st204Serv);
				fileStatusCarot = beanRemote.checkIfAllFilesLoaded(casSysctrlSysParamEntity.getProcessDate(), carotServ);
				fileStatusSt203 = beanRemote.checkIfAllFilesLoaded(casSysctrlSysParamEntity.getProcessDate(), st203Serv);
				fileStatusRcaot = beanRemote.checkIfAllFilesLoaded(casSysctrlSysParamEntity.getProcessDate(), rcaotServ);

				// Check if no txns are sitting on '3' or 'L'
				carinToExtract = fileProcessBeanRemote.eodCheckIfOutgoingExtracted(casSysctrlSysParamEntity.getProcessDate(), carinServ, memberId);
				rcainToExtract = fileProcessBeanRemote.eodCheckIfOutgoingExtracted(casSysctrlSysParamEntity.getProcessDate(), rcainServ, memberId);
				st200ToExtract = beanRemote.eodCheckIfStReportExtracted(casSysctrlSysParamEntity.getProcessDate(), st200Serv, memberId);
				st202ToExtract = beanRemote.eodCheckIfStReportExtracted(casSysctrlSysParamEntity.getProcessDate(), st202Serv, memberId);
				st204ToExtract = beanRemote.eodCheckIfStReportExtracted(casSysctrlSysParamEntity.getProcessDate(), st204Serv, memberId);
				st03ToExtract = beanRemote.eodCheckSt203SroutExtracted(st203Serv, memberId);

				
				log.debug("memberId ==> " + memberId);
				log.debug("creditorInd ==> " + creditorInd);
				log.debug("debtorInd ==> " + debtorInd);

				for (CasSysctrlServicesEntity servicesEntity : sysCntrlServicesList) {
					String outService = servicesEntity.getServiceIdOut();
					log.debug("outService ==> " + outService);

					// Checking close of services time
					Date endOfService = new Date();

					try {
						CasOpsSlaTimesEntity casOpsSlaTimesEntity = (CasOpsSlaTimesEntity) adminBeanRemote.retrieveEndTime(outService);
						log.debug("mdtOpsSlaTimesEntity------>" + casOpsSlaTimesEntity);
						endOfService = parser.parse(casOpsSlaTimesEntity.getEndTime());
					} catch (ObjectNotFoundException onfe) {
						log.error("No Object Exists on DB");
					} catch (Exception e) {
						log.error("Error on mdtOpsSlaTimesEntity: " + e.getMessage());
						e.printStackTrace();
					}

					// Output Debtor Services
					log.debug("userDate" + userDate);
					log.debug("endOfService" + endOfService);
					
					if (outService.equalsIgnoreCase(carotServ)) {
                        CasOpsSotEotCtrlEntity
                            casOpsSotEotCtrlEntity = retrieveSotEot(memberId,outService);
						
						if(!casOpsSotEotCtrlEntity.equals(null) && (!casOpsSotEotCtrlEntity.getEotOut().equalsIgnoreCase("Y"))) {
							if (fileStatusCarin) {

								if (carinToExtract) {

									if ((userDate.after(endOfService))
											&& (sysCisBankEntity.getAcDebtor().equalsIgnoreCase("Y"))) {
										generateEOT(memberId, outService);
									}

								} else {
									notExtractedError(eotCreated, carotServ, memberId);
								}
							} else {
								fileStatusError(eotCreated, carinServ, memberId);
							}
						}

					}

					// Output Creditor Services
					if (outService.equalsIgnoreCase(rcaotServ)) {
                        CasOpsSotEotCtrlEntity
                            casOpsSotEotCtrlEntity = retrieveSotEot(memberId,outService);
                        
						if(!casOpsSotEotCtrlEntity.equals(null) && (!casOpsSotEotCtrlEntity.getEotOut().equalsIgnoreCase("Y"))) {
							if (fileStatusRcain) {

								if (rcainToExtract) {
									if ((userDate.after(endOfService))
											&& (sysCisBankEntity.getAcCreditor().equalsIgnoreCase("Y"))) {
										generateEOT(memberId, outService);
									}
								} else {
									notExtractedError(eotCreated, rcaotServ, memberId);
								}
							} else {
								fileStatusError(eotCreated, rcainServ, memberId);
							}
						}		
					}

					
					if (outService.equalsIgnoreCase(st200Serv)) {
                        CasOpsSotEotCtrlEntity
                            casOpsSotEotCtrlEntity = retrieveSotEot(memberId,outService);
                        
						if(!casOpsSotEotCtrlEntity.equals(null) && (!casOpsSotEotCtrlEntity.getEotOut().equalsIgnoreCase("Y"))) {
							if (fileStatusSt200 && fileStatusCarin) {

								if (st200ToExtract && carinToExtract) {

									if ((userDate.after(endOfService))
											&& (sysCisBankEntity.getAcCreditor().equalsIgnoreCase("Y"))) {
										generateEOT(memberId, outService);
									}
								} else {
									notExtractedError(eotCreated, st200Serv, memberId);
								}
							} else {
								fileStatusError(eotCreated, st200Serv, memberId);
							}
						}
					}

					if (outService.equalsIgnoreCase(st202Serv)) {
                        CasOpsSotEotCtrlEntity
                            casOpsSotEotCtrlEntity = retrieveSotEot(memberId,outService);
                        
						if(!casOpsSotEotCtrlEntity.equals(null) && (!casOpsSotEotCtrlEntity.getEotOut().equalsIgnoreCase("Y"))) {
							if (fileStatusSt202) {

								if (st202ToExtract) {
									if ((userDate.after(endOfService))
											&& (sysCisBankEntity.getAcDebtor().equalsIgnoreCase("Y"))) {
										generateEOT(memberId, outService);
									}
								} else {
									notExtractedError(eotCreated, st202Serv, memberId);
								}
							} else {
								fileStatusError(eotCreated, st202Serv, memberId);
							}
						}	
					}

					if (outService.equalsIgnoreCase(st204Serv)) {
                        CasOpsSotEotCtrlEntity
                            casOpsSotEotCtrlEntity = retrieveSotEot(memberId,outService);
                        
						if(!casOpsSotEotCtrlEntity.equals(null) && (!casOpsSotEotCtrlEntity.getEotOut().equalsIgnoreCase("Y"))) {
							if (fileStatusSt204 && fileStatusRcain) {

								if (st204ToExtract && rcainToExtract) {
									if ((userDate.after(endOfService))
											&& (sysCisBankEntity.getAcDebtor().equalsIgnoreCase("Y"))) {
										generateEOT(memberId, outService);
									}
								} else {
									notExtractedError(eotCreated, st204Serv, memberId);
								}
							} else {
								fileStatusError(eotCreated, st204Serv, memberId);
							}
						}	
					}

					if (outService.equalsIgnoreCase(st203Serv)) {
                        CasOpsSotEotCtrlEntity
                            casOpsSotEotCtrlEntity = retrieveSotEot(memberId,outService);
                        
						if(!casOpsSotEotCtrlEntity.equals(null) && (!casOpsSotEotCtrlEntity.getEotOut().equalsIgnoreCase("Y"))) {
							if (fileStatusSt203) {

								if (st03ToExtract) {

									if ((userDate.after(endOfService))
											&& (sysCisBankEntity.getAcCreditor().equalsIgnoreCase("Y"))) {
										generateEOT(memberId, outService);
									}
								} else {
									notExtractedError(eotCreated, st203Serv, memberId);
								}
							} else {
								fileStatusError(eotCreated, st203Serv, memberId);
							}
						}	
					}
				}
			}
			eotCreated = true;
		} else {
			log.error("An error occurred on creating  EOT file!!");

		}

		return eotCreated;
	}

	public void generateEOT(String instgAgt, String service) {
		// Retrieve SOT/EOT Ind
		// List<MdtAcOpsSotEotCtrlEntity> acopsSotEotEntityList = new
		// ArrayList<MdtAcOpsSotEotCtrlEntity>();
		// acopsSotEotEntityList = (List<MdtAcOpsSotEotCtrlEntity>)
		// adminBeanRemote.retrieveInactiveEOTIND();

		CasOpsSotEotCtrlEntity casOpsSotEotCtrlEntity = retrieveSotEot(instgAgt,service);

		if (casOpsSotEotCtrlEntity != null) {
			if (casOpsSotEotCtrlEntity.getEotOut().equalsIgnoreCase("N")
					&& casOpsSotEotCtrlEntity.getSotOut().equalsIgnoreCase("Y")) {
				String fileType = null;
				// Retrieve the msgType from the service Table
				CasOpsServicesEntity casOpsServicesEntity = (CasOpsServicesEntity) adminBeanRemote.retrieveOpsService(service);

				if (casOpsServicesEntity != null) {
					fileType = casOpsServicesEntity.getMsgTypeId();
				}
				// Remove hardcoding --retrieve from database.
				// if(service.equalsIgnoreCase("ST100") || service.equalsIgnoreCase("ST103"))
				// fileType = "S";
				// else
				// {
				// if(service.equalsIgnoreCase("MANOC"))
				// fileType = "A";
				// else
				// {
				// fileType = "M";
				// }
				// }

				EndOfTransmissionExtract endOfTransmission = new EndOfTransmissionExtract(instgAgt, service, fileType);
				endOfTransmission.createEndOfTransmissionFile();
				casOpsSotEotCtrlEntity.setEotOut("Y");

				boolean updated = adminBeanRemote.updateACOpsEOTSOT(casOpsSotEotCtrlEntity);

				if (updated) {
					log.debug("The End of transmission indicator has been updated ");
				}
			}

		}
	}

	public void notExtractedError(boolean eotCreated, String service, String memberId) {
		log.error("There are transactions for " + memberId + " on "+service+" that need to be extracted. Please start extract schedulers!");
		feedbackMsg = "There are transactions for " + memberId + " on "+service+" that need to be extracted. Please start extract schedulers!";
		eotCreated = false;
	}

	public void fileStatusError(boolean eotCreated, String service, String memberId) {
		log.error("There are files for " +service+ " from "+ memberId + " sitting on 'W' or 'V' or 'R' status on File Register !!");
		feedbackMsg = "There are files for " +service+ " from "+ memberId + " sitting on 'W' or 'V' or 'R' status on File Register !!";
		eotCreated = false;
	}
	
	public CasOpsSotEotCtrlEntity retrieveSotEot(String instgAgt, String service) {

		CasOpsSotEotCtrlEntity casOpsSotEotCtrlEntity = new CasOpsSotEotCtrlEntity();
		casOpsSotEotCtrlEntity = (CasOpsSotEotCtrlEntity) beanRemote.retrieveSOTEOTCntrl(instgAgt, service);

		return casOpsSotEotCtrlEntity;
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

	private static void contextTimerBeanCheck()
	{
		if (timerBeanRemote == null) {
			// timerBeanRemote = Util.getTimerBean();
		}
	}
	
	private static void contextFileProcessBeanCheck() {
		if (fileProcessBeanRemote == null) {
			fileProcessBeanRemote = Util.getFileProcessBean();
		}
	}

}
