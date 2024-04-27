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
	
	String maninServ, manamServ, mancnServ, manacServ, manrtServ, manriServ, spinpServ, srinpServ, mandbServ;
	String manotServ, manomServ, mancoServ, manocServ, st103Serv, manroServ, manrfServ, spoutServ, sroutServ, mandcServ;
	String st100Serv, st102Serv, st104Serv, st105Serv, st106Serv, st007Serv, st008Serv, st994Serv;
	String activeInd, nonActiveInd, mdtLoadType;
	List<CasSysctrlServicesEntity> sysCntrlServicesList = new ArrayList<CasSysctrlServicesEntity>();// list
	Date currentDate = new Date();
	boolean txnsToExtract = false;
	boolean fileStatusManin = false, fileStatusManam = false, fileStatusMancn = false, fileStatusManac = false,
			fileStatusSpinp = false, fileStatusSt100 = false, fileStatusSt102 = false, fileStatusSt104 = false,
			fileStatusSt007 = false, fileStatusSt008 = false, fileStatusManrt = false, fileStatusSt106 = false,
			fileStatusManri = false, fileStatusSt105 = false, fileStatusSt103 = false, fileStatusSrinp = false, fileStatusSt994 = false, fileStatusMandc = false;
	public String feedbackMsg;
	CasSysctrlSysParamEntity casSysctrlSysParamEntity = new CasSysctrlSysParamEntity();
	boolean maninToExtract = false, manamToExtract = false, mancnToExtract = false, manacToExtract = false,
			st103Extract = false, sroutExtract = false, st105ToExtract = false, manriToExtract = false,
			manrtToExtract = false, st106ToExtract = false, spoutToExtract = false, st100ToExtract = false,
			st102ToExtract = false, st104ToExtract = false, st007ToExtract = false, st008ToExtract = false, st994ToExtract = false, mandcToExtract = false;

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
			this.manotServ = propertyUtil.getPropValue("Output.Pain009");
			this.manomServ = propertyUtil.getPropValue("Output.Pain010");
			this.mancoServ = propertyUtil.getPropValue("Output.Pain011");
			this.manocServ = propertyUtil.getPropValue("Output.Pain012");
			this.st103Serv = propertyUtil.getPropValue("Output.Pacs002");
			this.manroServ = propertyUtil.getPropValue("Output.Mdte001");
			this.manrfServ = propertyUtil.getPropValue("Output.Mdte002");
			this.spoutServ = propertyUtil.getPropValue("Output.Camt055");
			this.sroutServ = propertyUtil.getPropValue("Output.Pacs002Resp");
			this.mandcServ =propertyUtil.getPropValue("Output.MarkOffFile");

			this.st100Serv = propertyUtil.getPropValue("StatusRep.ST100");
			this.st102Serv = propertyUtil.getPropValue("StatusRep.ST102");
			this.st104Serv = propertyUtil.getPropValue("StatusRep.ST104");
			this.st105Serv = propertyUtil.getPropValue("StatusRep.ST105");
			this.st106Serv = propertyUtil.getPropValue("StatusRep.ST106");
			this.st007Serv = propertyUtil.getPropValue("StatusRep.ST007");
			this.st008Serv = propertyUtil.getPropValue("StatusRep.ST008");
			this.st994Serv = propertyUtil.getPropValue("StatusRep.ST994");

			this.maninServ = propertyUtil.getPropValue("Input.Pain009");
			this.manamServ = propertyUtil.getPropValue("Input.Pain010");
			this.mancnServ = propertyUtil.getPropValue("Input.Pain011");
			this.manacServ = propertyUtil.getPropValue("Input.Pain012");
			this.manrtServ = propertyUtil.getPropValue("Input.Mdte002");
			this.manriServ = propertyUtil.getPropValue("Input.Mdte001");
			this.spinpServ = propertyUtil.getPropValue("Input.Camt055");
			this.srinpServ = propertyUtil.getPropValue("Input.Pacs002Resp");
			this.mandbServ =propertyUtil.getPropValue("Input.MarkOffFile");

		} catch (Exception e) {
			log.error("EOTFilesCreator - Could not find MandateMessageCommons.properties in classpath");
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
				fileStatusManin = beanRemote.checkIfAllFilesLoaded(casSysctrlSysParamEntity.getProcessDate(), maninServ);
				fileStatusManam = beanRemote.checkIfAllFilesLoaded(casSysctrlSysParamEntity.getProcessDate(), manamServ);
				fileStatusMancn = beanRemote.checkIfAllFilesLoaded(casSysctrlSysParamEntity.getProcessDate(), mancnServ);
				fileStatusManac = beanRemote.checkIfAllFilesLoaded(casSysctrlSysParamEntity.getProcessDate(), manacServ);
				fileStatusSpinp = beanRemote.checkIfAllFilesLoaded(casSysctrlSysParamEntity.getProcessDate(), spinpServ);
				fileStatusSt100 = beanRemote.checkIfAllFilesLoaded(casSysctrlSysParamEntity.getProcessDate(), st100Serv);
				fileStatusSt102 = beanRemote.checkIfAllFilesLoaded(casSysctrlSysParamEntity.getProcessDate(), st102Serv);
				fileStatusSt104 = beanRemote.checkIfAllFilesLoaded(casSysctrlSysParamEntity.getProcessDate(), st104Serv);
				fileStatusSt007 = beanRemote.checkIfAllFilesLoaded(casSysctrlSysParamEntity.getProcessDate(), st007Serv);
				fileStatusSt008 = beanRemote.checkIfAllFilesLoaded(casSysctrlSysParamEntity.getProcessDate(), st008Serv);
				fileStatusManrt = beanRemote.checkIfAllFilesLoaded(casSysctrlSysParamEntity.getProcessDate(), manrtServ);
				fileStatusSt106 = beanRemote.checkIfAllFilesLoaded(casSysctrlSysParamEntity.getProcessDate(), st106Serv);
				fileStatusManri = beanRemote.checkIfAllFilesLoaded(casSysctrlSysParamEntity.getProcessDate(), manriServ);
				fileStatusSt105 = beanRemote.checkIfAllFilesLoaded(casSysctrlSysParamEntity.getProcessDate(), st105Serv);
				fileStatusSt103 = beanRemote.checkIfAllFilesLoaded(casSysctrlSysParamEntity.getProcessDate(), st103Serv);
				fileStatusSrinp = beanRemote.checkIfAllFilesLoaded(casSysctrlSysParamEntity.getProcessDate(), srinpServ);
				fileStatusSt994 = beanRemote.checkIfAllFilesLoaded(casSysctrlSysParamEntity.getProcessDate(), st994Serv);
				fileStatusMandc = beanRemote.checkIfAllFilesLoaded(casSysctrlSysParamEntity.getProcessDate(), mandbServ);

				/*
				 * log.info("fileStatus frin Service Bean ==> "+fileStatusManin);
				 * log.info("fileStatus frin Service Bean ==> "+fileStatusManam);
				 * log.info("fileStatus frin Service Bean ==> "+fileStatusMancn);
				 * log.info("fileStatus frin Service Bean ==> "+fileStatusManac);
				 * log.info("fileStatus frin Service Bean ==> "+fileStatusSpinp);
				 * log.info("fileStatus frin Service Bean ==> "+fileStatusSt100);
				 * log.info("fileStatus frin Service Bean ==> "+fileStatusSt102);
				 * log.info("fileStatus frin Service Bean ==> "+fileStatusSt104);
				 * log.info("fileStatus frin Service Bean ==> "+fileStatusSt007);
				 * log.info("fileStatus frin Service Bean ==> "+fileStatusSt008);
				 * log.info("fileStatus frin Service Bean ==> "+fileStatusManrt);
				 * log.info("fileStatus frin Service Bean ==> "+fileStatusSt106);
				 * log.info("fileStatus frin Service Bean ==> "+fileStatusManri);
				 * log.info("fileStatus frin Service Bean ==> "+fileStatusSt105);
				 * log.info("fileStatus frin Service Bean ==> "+fileStatusSt103);
				 * log.info("fileStatus frin Service Bean ==> "+fileStatusSrinp);
				 */
				
				// Check if no txns are sitting on '3' or 'L'
				maninToExtract = fileProcessBeanRemote.eodCheckIfOutgoingExtracted(casSysctrlSysParamEntity.getProcessDate(), maninServ, memberId);
				manamToExtract = fileProcessBeanRemote.eodCheckIfOutgoingExtracted(casSysctrlSysParamEntity.getProcessDate(), manamServ, memberId);
				mancnToExtract = fileProcessBeanRemote.eodCheckIfOutgoingExtracted(casSysctrlSysParamEntity.getProcessDate(), mancnServ, memberId);
				manacToExtract = fileProcessBeanRemote.eodCheckIfOutgoingExtracted(casSysctrlSysParamEntity.getProcessDate(), manacServ, memberId);
				st100ToExtract = beanRemote.eodCheckIfStReportExtracted(casSysctrlSysParamEntity.getProcessDate(), st100Serv, memberId);
				st102ToExtract = beanRemote.eodCheckIfStReportExtracted(casSysctrlSysParamEntity.getProcessDate(), st102Serv, memberId);
				st104ToExtract = beanRemote.eodCheckIfStReportExtracted(casSysctrlSysParamEntity.getProcessDate(), st104Serv, memberId);
				st007ToExtract = beanRemote.eodCheckIfStReportExtracted(casSysctrlSysParamEntity.getProcessDate(), st007Serv, memberId);
				st008ToExtract = beanRemote.eodCheckIfStReportExtracted(casSysctrlSysParamEntity.getProcessDate(), st008Serv, memberId);
				st103Extract = beanRemote.eodCheckSt103SroutExtracted(st103Serv, memberId);
				sroutExtract = beanRemote.eodCheckSt103SroutExtracted(sroutServ, memberId);
				st105ToExtract = beanRemote.eodCheckIfStReportExtracted(casSysctrlSysParamEntity.getProcessDate(), st105Serv, memberId);
				st106ToExtract = beanRemote.eodCheckIfStReportExtracted(casSysctrlSysParamEntity.getProcessDate(), st106Serv, memberId);
				st994ToExtract = beanRemote.eodCheckIfStReportExtracted(casSysctrlSysParamEntity.getProcessDate(), st994Serv, memberId);
				/*
				 * log.info("manotToExtract is =====> " + manotToExtract);
				 * log.info("st100ToExtract is =====> " + st100ToExtract);
				 * log.info("st102ToExtract is =====> " + st102ToExtract);
				 * log.info("st104ToExtract is =====> " + st104ToExtract);
				 * log.info("st007ToExtract is =====> " + st007ToExtract);
				 * log.info("st008ToExtract is =====> " + st008ToExtract);
				 * log.info("spoutToExtract is =====> " + spoutToExtract);
				 * log.info("manriToExtract is =====> " + manriToExtract);
				 * log.info("st105ToExtract is =====> " + st105ToExtract);
				 * log.info("manrtToExtract is =====> " + manrtToExtract);
				 * log.info("st106ToExtract is =====> " + st106ToExtract);
				 * log.info("sroutExtract is =====> " + sroutExtract);
				 * log.info("st103Extract is =====> " + st103Extract);
				 */

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
					if (outService.equalsIgnoreCase(manotServ)) {
						CasOpsSotEotCtrlEntity
                            casOpsSotEotCtrlEntity = retrieveSotEot(memberId,outService);
						
						if(!casOpsSotEotCtrlEntity.equals(null) && (!casOpsSotEotCtrlEntity.getEotOut().equalsIgnoreCase("Y"))) {
							if (fileStatusManin) {
								if (maninToExtract) {
									if ((userDate.after(endOfService))
											&& (sysCisBankEntity.getAcDebtor().equalsIgnoreCase("Y"))) {

										log.debug("Inside MANOT EOTS");
										generateEOT(memberId, outService);
										log.debug("EOTS for MANIN Created");
									}
								} else {
									notExtractedError(eotCreated, manotServ, memberId);
								}

							} else {
								fileStatusError(eotCreated, maninServ, memberId);
							}
						}
					}

					if (outService.equalsIgnoreCase(manomServ)) {
                        CasOpsSotEotCtrlEntity
                            casOpsSotEotCtrlEntity = retrieveSotEot(memberId,outService);
						
						if(!casOpsSotEotCtrlEntity.equals(null) && (!casOpsSotEotCtrlEntity.getEotOut().equalsIgnoreCase("Y"))) {
							if (fileStatusManam) {

								if (manamToExtract) {

									if ((userDate.after(endOfService))
											&& (sysCisBankEntity.getAcDebtor().equalsIgnoreCase("Y"))) {
										generateEOT(memberId, outService);
									}

								} else {
									notExtractedError(eotCreated, manomServ, memberId);
								}
							} else {
								fileStatusError(eotCreated, manamServ, memberId);
							}
						}

					}

					if (outService.equalsIgnoreCase(mancoServ)) {
                        CasOpsSotEotCtrlEntity
                            casOpsSotEotCtrlEntity = retrieveSotEot(memberId,outService);
						
						if(!casOpsSotEotCtrlEntity.equals(null) && (!casOpsSotEotCtrlEntity.getEotOut().equalsIgnoreCase("Y"))) {
							if (fileStatusMancn) {

								if (mancnToExtract) {
									if ((userDate.after(endOfService))
											&& (sysCisBankEntity.getAcDebtor().equalsIgnoreCase("Y"))) {
										generateEOT(memberId, outService);
									}
								} else {
									notExtractedError(eotCreated, mancoServ, memberId);
								}
							} else {
								fileStatusError(eotCreated, mancnServ, memberId);
							}
						}
					}

					// Output Creditor Services
					if (outService.equalsIgnoreCase(manocServ)) {
                        CasOpsSotEotCtrlEntity
                            casOpsSotEotCtrlEntity = retrieveSotEot(memberId,outService);
                        
						if(!casOpsSotEotCtrlEntity.equals(null) && (!casOpsSotEotCtrlEntity.getEotOut().equalsIgnoreCase("Y"))) {
							if (fileStatusManac) {

								if (manacToExtract) {
									if ((userDate.after(endOfService))
											&& (sysCisBankEntity.getAcCreditor().equalsIgnoreCase("Y"))) {
										generateEOT(memberId, outService);
									}
								} else {
									notExtractedError(eotCreated, manocServ, memberId);
								}
							} else {
								fileStatusError(eotCreated, manacServ, memberId);
							}
						}		
					}

					if (outService.equalsIgnoreCase(spoutServ)) {
                        CasOpsSotEotCtrlEntity
                            casOpsSotEotCtrlEntity = retrieveSotEot(memberId,outService);
                        
						if(!casOpsSotEotCtrlEntity.equals(null) && (!casOpsSotEotCtrlEntity.getEotOut().equalsIgnoreCase("Y"))) {
							if (fileStatusSpinp) {

								if (spoutToExtract) {
									if ((userDate.after(endOfService))
											&& (sysCisBankEntity.getAcCreditor().equalsIgnoreCase("Y"))) {
										generateEOT(memberId, outService);
									}
								} else {
									notExtractedError(eotCreated, spoutServ, memberId);
								}
							} else {
								fileStatusError(eotCreated, spinpServ, memberId);
							}
						}
					}

					if (outService.equalsIgnoreCase(st100Serv)) {
                        CasOpsSotEotCtrlEntity
                            casOpsSotEotCtrlEntity = retrieveSotEot(memberId,outService);
                        
						if(!casOpsSotEotCtrlEntity.equals(null) && (!casOpsSotEotCtrlEntity.getEotOut().equalsIgnoreCase("Y"))) {
							if (fileStatusSt100 && fileStatusManin && fileStatusManam && fileStatusMancn) {

								if (st100ToExtract && maninToExtract && manamToExtract && mancnToExtract) {

									if ((userDate.after(endOfService))
											&& (sysCisBankEntity.getAcCreditor().equalsIgnoreCase("Y"))) {
										generateEOT(memberId, outService);
									}
								} else {
									notExtractedError(eotCreated, st100Serv, memberId);
								}
							} else {
								fileStatusError(eotCreated, st100Serv, memberId);
							}
						}
					}

					if (outService.equalsIgnoreCase(st102Serv)) {
                        CasOpsSotEotCtrlEntity
                            casOpsSotEotCtrlEntity = retrieveSotEot(memberId,outService);
                        
						if(!casOpsSotEotCtrlEntity.equals(null) && (!casOpsSotEotCtrlEntity.getEotOut().equalsIgnoreCase("Y"))) {
							if (fileStatusSt102) {

								if (st102ToExtract) {
									if ((userDate.after(endOfService))
											&& (sysCisBankEntity.getAcDebtor().equalsIgnoreCase("Y"))) {
										generateEOT(memberId, outService);
									}
								} else {
									notExtractedError(eotCreated, st102Serv, memberId);
								}
							} else {
								fileStatusError(eotCreated, st102Serv, memberId);
							}
						}	
					}

					if (outService.equalsIgnoreCase(st104Serv)) {
                        CasOpsSotEotCtrlEntity
                            casOpsSotEotCtrlEntity = retrieveSotEot(memberId,outService);
                        
						if(!casOpsSotEotCtrlEntity.equals(null) && (!casOpsSotEotCtrlEntity.getEotOut().equalsIgnoreCase("Y"))) {
							if (fileStatusSt104 && fileStatusManac) {

								if (st104ToExtract && manacToExtract) {
									if ((userDate.after(endOfService))
											&& (sysCisBankEntity.getAcDebtor().equalsIgnoreCase("Y"))) {
										generateEOT(memberId, outService);
									}
								} else {
									notExtractedError(eotCreated, st104Serv, memberId);
								}
							} else {
								fileStatusError(eotCreated, st104Serv, memberId);
							}
						}	
					}

					if (outService.equalsIgnoreCase(st007Serv)) {
                        CasOpsSotEotCtrlEntity
                            casOpsSotEotCtrlEntity = retrieveSotEot(memberId,outService);
                        
						if(!casOpsSotEotCtrlEntity.equals(null) && (!casOpsSotEotCtrlEntity.getEotOut().equalsIgnoreCase("Y"))) {
							if (fileStatusSt007) {

								if (st007ToExtract) {
									if ((userDate.after(endOfService))
											&& (sysCisBankEntity.getAcCreditor().equalsIgnoreCase("Y"))) {
										generateEOT(memberId, outService);
									}

								} else {
									notExtractedError(eotCreated, st007Serv, memberId);
								}
							} else {
								fileStatusError(eotCreated, st007Serv, memberId);
							}
						}
					}

					if (outService.equalsIgnoreCase(st008Serv)) {
                        CasOpsSotEotCtrlEntity
                            casOpsSotEotCtrlEntity = retrieveSotEot(memberId,outService);
                        
						if(!casOpsSotEotCtrlEntity.equals(null) && (!casOpsSotEotCtrlEntity.getEotOut().equalsIgnoreCase("Y"))) {
							if (fileStatusSt008) {

								if (st008ToExtract) {
									if ((userDate.after(endOfService))
											&& (sysCisBankEntity.getAcDebtor().equalsIgnoreCase("Y"))) {
										generateEOT(memberId, outService);
									}
								} else {
									notExtractedError(eotCreated, st008Serv, memberId);
								}
							} else {
								fileStatusError(eotCreated, st008Serv, memberId);
							}
						}
					}

					if (outService.equalsIgnoreCase(manrfServ)) {
                        CasOpsSotEotCtrlEntity
                            casOpsSotEotCtrlEntity = retrieveSotEot(memberId,outService);
                        
						if(!casOpsSotEotCtrlEntity.equals(null) && (!casOpsSotEotCtrlEntity.getEotOut().equalsIgnoreCase("Y"))) {
							if (fileStatusManrt) {

								if (manrtToExtract) {
									if ((userDate.after(endOfService))
											&& (sysCisBankEntity.getAcCreditor().equalsIgnoreCase("Y"))) {
										generateEOT(memberId, outService);
									}
								} else {
									notExtractedError(eotCreated, manrfServ, memberId);
								}
							} else {
								fileStatusError(eotCreated, manrtServ, memberId);
							}
						}	
					}

					if (outService.equalsIgnoreCase(st106Serv)) {
                        CasOpsSotEotCtrlEntity
                            casOpsSotEotCtrlEntity = retrieveSotEot(memberId,outService);
                        
						if(!casOpsSotEotCtrlEntity.equals(null) && (!casOpsSotEotCtrlEntity.getEotOut().equalsIgnoreCase("Y"))) {
							if (fileStatusSt106 && fileStatusManrt) {

								if (st106ToExtract && manrtToExtract) {
									if ((userDate.after(endOfService))
											&& (sysCisBankEntity.getAcDebtor().equalsIgnoreCase("Y"))) {
										generateEOT(memberId, outService);
									}
								} else {
									notExtractedError(eotCreated, st106Serv, memberId);
								}
							} else {
								fileStatusError(eotCreated, st106Serv, memberId);
							}
						}
					}

					if (outService.equalsIgnoreCase(manroServ)) {
                        CasOpsSotEotCtrlEntity
                            casOpsSotEotCtrlEntity = retrieveSotEot(memberId,outService);
                        
						if(!casOpsSotEotCtrlEntity.equals(null) && (!casOpsSotEotCtrlEntity.getEotOut().equalsIgnoreCase("Y"))) {
							if (fileStatusManri) {

								if (manriToExtract) {

									if ((userDate.after(endOfService))
											&& (sysCisBankEntity.getAcDebtor().equalsIgnoreCase("Y"))) {
										generateEOT(memberId, outService);
									}
								} else {
									notExtractedError(eotCreated, manroServ, memberId);
								}
							} else {
								fileStatusError(eotCreated, manriServ, memberId);
							}
						}
					}

					if (outService.equalsIgnoreCase(st105Serv)) {
                        CasOpsSotEotCtrlEntity
                            casOpsSotEotCtrlEntity = retrieveSotEot(memberId,outService);
                        
						if(!casOpsSotEotCtrlEntity.equals(null) && (!casOpsSotEotCtrlEntity.getEotOut().equalsIgnoreCase("Y"))) {
							if (fileStatusSt105 && fileStatusManri) {

								if (st105ToExtract && manriToExtract) {

									if ((userDate.after(endOfService))
											&& (sysCisBankEntity.getAcCreditor().equalsIgnoreCase("Y"))) {
										generateEOT(memberId, outService);
									}
								} else {
									notExtractedError(eotCreated, st105Serv, memberId);
								}
							} else {
								fileStatusError(eotCreated, st105Serv, memberId);
							}
						}
					}

					if (outService.equalsIgnoreCase(st103Serv)) {
                        CasOpsSotEotCtrlEntity
                            casOpsSotEotCtrlEntity = retrieveSotEot(memberId,outService);
                        
						if(!casOpsSotEotCtrlEntity.equals(null) && (!casOpsSotEotCtrlEntity.getEotOut().equalsIgnoreCase("Y"))) {
							if (fileStatusSt103) {

								if (st103Extract) {

									if ((userDate.after(endOfService))
											&& (sysCisBankEntity.getAcCreditor().equalsIgnoreCase("Y"))) {
										generateEOT(memberId, outService);
									}
								} else {
									notExtractedError(eotCreated, st103Serv, memberId);
								}
							} else {
								fileStatusError(eotCreated, st103Serv, memberId);
							}
						}	
					}

					if (outService.equalsIgnoreCase(sroutServ)) {
                        CasOpsSotEotCtrlEntity
                            casOpsSotEotCtrlEntity = retrieveSotEot(memberId,outService);
                        
						if(!casOpsSotEotCtrlEntity.equals(null) && (!casOpsSotEotCtrlEntity.getEotOut().equalsIgnoreCase("Y"))) {
							if (fileStatusSrinp) {

								if (sroutExtract) {

									if ((userDate.after(endOfService))
											&& (sysCisBankEntity.getAcDebtor().equalsIgnoreCase("Y"))) {
										generateEOT(memberId, outService);
									}
								} else {
									notExtractedError(eotCreated, sroutServ, memberId);
								}
							} else {
								fileStatusError(eotCreated, sroutServ, memberId);
							}
						}
					}

					if (outService.equalsIgnoreCase(mandcServ)) {
                        CasOpsSotEotCtrlEntity
                            casOpsSotEotCtrlEntity = retrieveSotEot(memberId,outService);
                        
						if(!casOpsSotEotCtrlEntity.equals(null) && (!casOpsSotEotCtrlEntity.getEotOut().equalsIgnoreCase("Y"))) {
							if (fileStatusMandc) {

								if (mandcToExtract) {
									if ((userDate.after(endOfService)) && (sysCisBankEntity.getAcCreditor().equalsIgnoreCase("Y"))) {
										generateEOT(memberId, outService);
									}
								} else {
									notExtractedError(eotCreated, mandcServ, memberId);
								}
							} else {
								fileStatusError(eotCreated, mandbServ, memberId);
							}
						}		
					}
					
					if (outService.equalsIgnoreCase(st994Serv)) {
                        CasOpsSotEotCtrlEntity
                            casOpsSotEotCtrlEntity = retrieveSotEot(memberId,outService);
                        
						if(!casOpsSotEotCtrlEntity.equals(null) && (!casOpsSotEotCtrlEntity.getEotOut().equalsIgnoreCase("Y"))) {
							if (fileStatusSt994 && fileStatusMandc) {

								if (st994ToExtract && mandcToExtract) {

									if ((userDate.after(endOfService)) && (sysCisBankEntity.getAcDebtor().equalsIgnoreCase("Y"))) {
										generateEOT(memberId, outService);
									}
								} else {
									notExtractedError(eotCreated, st994Serv, memberId);
								}
							} else {
								fileStatusError(eotCreated, st994Serv, memberId);
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
