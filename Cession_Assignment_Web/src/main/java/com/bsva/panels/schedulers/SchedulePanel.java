package com.bsva.panels.schedulers;

import com.bsva.commons.model.AudSystemProcessModel;
import com.bsva.commons.model.SchedulerCommonsModel;
import com.bsva.commons.model.SchedulerCronModel;
import com.bsva.commons.model.SysCtrlSlaTimeModel;
import com.bsva.commons.model.SystemParameterModel;
import com.bsva.controller.Controller;
import com.bsva.models.ClientSessionModel;
import com.bsva.models.WebQuartzSchedulerModel;
import com.bsva.models.WebSystemParameterModel;
import com.bsva.panels.CheckBoxColumn;
import com.bsva.provider.SchedulerProvider;
import com.bsva.translator.WebAdminTranslator;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.log4j.Logger;
import org.apache.wicket.Session;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.extensions.model.AbstractCheckBoxModel;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class SchedulePanel extends Panel implements Serializable {

  /**
   * @author ElelwaniR  & NhlanhlaM
   * @author SalehaR
   * @author SalehaR-2019/05/06 Debug Statements & Code CleanUp
   */
  private static final long serialVersionUID = 1L;
  private Controller controller;
  public static Logger log = Logger.getLogger(SchedulePanel.class);

  private Set<WebQuartzSchedulerModel> selected = new HashSet<WebQuartzSchedulerModel>();

  private Form form;
  private Button stopButton;
  private Button startButton;
  private Button resumeButton;
  private Button rescheduleButton;
  //private WebQuartzSchedulerModel schedulersList;
  private List<String> durationInSeconds;
  private List<WebQuartzSchedulerModel> schedulersList;
  private String rescheduleInSecondsList;
  int seconds = 30;
  public static String id;

  private DropDownChoice<WebQuartzSchedulerModel> schedulers;
  private DropDownChoice<String> rescheduleInSeconds;
  private DropDownChoice<String> timeList;

  private String cron30Sec = "0/30 * * * * ?";
  private String cron45Sec = "0/45 * * * * ?";
  private String cron60Sec = "0 0/1 * 1/1 * ? *";
  private String cron2Mins = " 0 0/2 * 1/1 * ? *";
  private String cron3Mins = " 0 0/3 * 1/1 * ? *";
  private String cron4Mins = " 0 0/4 * 1/1 * ? *";
  private String cron5Mins = " 0 0/5 * 1/1 * ? *";
  private String cron10Mins = "0 0/10 * 1/1 * ? *";
  private String cron15Min = "0 0/15 * 1/1 * ? *";
  private String cron30Min = "0 0/30 * 1/1 * ? *";
  private String cronHourly = "0 0 0/1 1/1 * ? *";
  private String cron2Hours = "0 0 0/2 1/1 * ? *";
  private String cron3Hours = "0 0 0/3 1/1 * ? *";

  WebSystemParameterModel webSystemParameterModel;
  SchedulerProvider schedulerProvider;

  //Session Info
  private ClientSessionModel clientSessionModel;
  private String userName;
  public static Session session;

  Date currentDate = new Date();
  SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
  Date startTime = null;
  Date endTime = null;

  public SchedulePanel(String id) {
    super(id);
    this.id = id;
    initializeComponents();
  }

  private void initializeComponents() {
    controller = new Controller();
    form = new Form("Form");

    retrieveSessionInfo();

    loadData();
    form.add(createDataTable(new SchedulerProvider(false)));


    schedulers = new DropDownChoice<WebQuartzSchedulerModel>("schedulers",
        new Model<WebQuartzSchedulerModel>(), schedulersList,
        new ChoiceRenderer<WebQuartzSchedulerModel>());
    schedulers.setDefaultModelObject(schedulers.getChoices().get(0).getSchedulerName());
    schedulers.setRequired(false);
    form.add(schedulers);

    List<String> rescheduleInsecondList = new ArrayList<String>();
    rescheduleInsecondList.add("30 seconds");
    rescheduleInsecondList.add("45 seconds");
    rescheduleInsecondList.add("60 seconds");
    rescheduleInsecondList.add("2 minutes");
    rescheduleInsecondList.add("3 minutes");
    rescheduleInsecondList.add("4 minutes");
    rescheduleInsecondList.add("5 minutes");
    rescheduleInsecondList.add("10 minutes");
    rescheduleInsecondList.add("15 minutes");
    rescheduleInsecondList.add("30 minutes");
    rescheduleInsecondList.add("1 hour");
    rescheduleInsecondList.add("2 hours");
    rescheduleInsecondList.add("3 hours");


    //		rescheduleInsecondList.add("30");
    //		rescheduleInsecondList.add("40");
    //		rescheduleInsecondList.add("50");
    //		rescheduleInsecondList.add("60");
    //		rescheduleInsecondList.add("120");
    //		rescheduleInsecondList.add("180");
    //		rescheduleInsecondList.add("240");
    //		rescheduleInsecondList.add("300");
    //		rescheduleInsecondList.add("360");
    //		durationInSeconds = Arrays.asList(new String[]{"30", "40", "50", "60", "120", "180",
	  //		"240", "300", "360"});
    rescheduleInSeconds =
        new DropDownChoice<String>("rescheduleTimes", new Model<String>(), rescheduleInsecondList,
            new ChoiceRenderer<String>());
    rescheduleInSeconds.setRequired(false);
    rescheduleInSeconds.setDefaultModelObject(rescheduleInSeconds.getChoices().get(0));
    form.add(rescheduleInSeconds);


    startButton = new Button("startButton") {
      private static final long serialVersionUID = 1L;

      @Override
      public void onSubmit() {
        try {
          Calendar cal = Calendar.getInstance();
          cal.setTime(currentDate);

          //Put it back in the Date object
          currentDate = cal.getTime();

          String strrDate = parser.format(currentDate);
          log.info("Current Time: " + strrDate);

          Date userDate = parser.parse(strrDate);


          String choice = schedulers.getChoices().get(Integer.parseInt(schedulers.getValue()))
              .getSchedulerName();
          log.info("STARTING SCHEDULER----> " + choice);

          if (choice.equalsIgnoreCase("Start of Day")) {
            log.info("[" + userName + " REQUESTED START OF DAY]");
            SysCtrlSlaTimeModel sysCtrlSlaTimeModel =
                (SysCtrlSlaTimeModel) controller.retrieveCisSlaTime("SOD");
            log.debug("sysCtrlSlaTimeModel ===> " + sysCtrlSlaTimeModel);

            startTime = parser.parse(sysCtrlSlaTimeModel.getStartTime());
            endTime = parser.parse(sysCtrlSlaTimeModel.getEndTime());

            String activeInd =
                schedulers.getChoices().get(Integer.parseInt(schedulers.getValue())).getActiveInd();

            if (activeInd.equalsIgnoreCase("Y")) {
              error("Start of Day is active. Please check System Parameters Screen!");
              log.error("Start of Day is active. Please check System Parameters Screen!");
            } else if (userDate.after(startTime) && userDate.before(endTime)) {
              //Update System Audit Log Information
              AudSystemProcessModel audSystemProcessModel = new AudSystemProcessModel();
              audSystemProcessModel.setProcess(controller.getProperty("AUD.SYSPROCESS.SOD"));
              audSystemProcessModel.setProcessDate(new Date());
              audSystemProcessModel.setUserId(userName);

              controller.saveSystemAuditInfo(audSystemProcessModel);

              boolean sodCheck = controller.runStartofDayManually();
			  log.info("[SchedulePanel] sodCheck: "+sodCheck);
              if (sodCheck) {
                info(controller.feedbackMsg);
                //info("Start of Day ran successfully...!");
                form.remove(form.get("dataTable"));
                form.add(createDataTable(new SchedulerProvider(true)));
              }
							/*else
									{
										info(controller.feedbackMsg);
									}*/


            } else {
              log.error("SLA TIME for SOD is not reached, Please run from" +
                  sysCtrlSlaTimeModel.getStartTime() + "!");
              error("SLA TIME for SOD is not reached, Please run from " +
                  sysCtrlSlaTimeModel.getStartTime() + "!");
            }

          } else {
            //Check if active day exists//

            SystemParameterModel systemParameterModel =
                (SystemParameterModel) controller.retrieveWebActiveSysParameter();
            log.debug("systemParameterModel : " + systemParameterModel);

            if (systemParameterModel != null && systemParameterModel.getActiveInd() != null) {
              webSystemParameterModel = new WebSystemParameterModel();
              webSystemParameterModel =
                  new WebAdminTranslator().translateCommonsSystemParametersModelToWebModel(
                      systemParameterModel);
              log.debug("webSystemParameterModel :" + webSystemParameterModel);
              if (webSystemParameterModel.getActiveInd() != null &&
                  webSystemParameterModel.getActiveInd().equalsIgnoreCase("Y")) {
                if (choice.equals("All")) {
                  boolean startAllSch = controller.startAllSchedulers();
					if (startAllSch) {
						form.remove(form.get("dataTable"));
						form.remove(form.get("schedulers"));
						loadData();
						schedulers = new DropDownChoice<WebQuartzSchedulerModel>("schedulers",
								new Model<WebQuartzSchedulerModel>(), schedulersList,
								new ChoiceRenderer<WebQuartzSchedulerModel>());
						schedulers.setDefaultModelObject(
								schedulers.getChoices().get(0).getSchedulerName());
						schedulers.setRequired(false);
						form.add(schedulers);
						form.add(createDataTable(new SchedulerProvider(true)));
						info("ALL Schedulers have been started successfully");

						//Save Audit Information
						AudSystemProcessModel audSystemProcessModel = new AudSystemProcessModel();
						audSystemProcessModel.setProcess(
								controller.getProperty("AUD.SCH.ALL") + " - " +
										controller.getProperty("AUD.SCH.START"));
						audSystemProcessModel.setProcessDate(new Date());
						audSystemProcessModel.setUserId(userName);

						controller.saveSystemAuditInfo(audSystemProcessModel);
						//
						//													for
						//													(WebQuartzSchedulerModel scheduler : schedulersList)
						//													{
						//														log.info("Scheduler
						//														after start ALlL ~
						//														Name== > "+
						//														scheduler
						//														.getSchedulerName
						//														());
						//														log.info("Scheduler
						//														after start ALlL ~
						//														Active Ind  == >
						//														"+ scheduler
						//														.getActiveInd());
						//														log.info("Scheduler
						//														after start ALlL ~
						//														Seconds  == > "+
						//														scheduler
						//														.getRescheduleTime
						//														());
						//													}
					} else {
						error("An error occurred when trying to start ALL schedulers!");
					}
                } else if (choice.equalsIgnoreCase("MANOM")) {
                  boolean manomChk = controller.startPain010();

					if (manomChk) {
						form.remove(form.get("dataTable"));
						form.add(createDataTable(new SchedulerProvider(true)));
						info("MANOM scheduler started successfully ");

						//Save Audit Information
						AudSystemProcessModel audSystemProcessModel = new AudSystemProcessModel();
						audSystemProcessModel.setProcess(
								controller.getProperty("AUD.SCH.MANOM") + " - " +
										controller.getProperty("AUD.SCH.START"));
						audSystemProcessModel.setProcessDate(new Date());
						audSystemProcessModel.setUserId(userName);

						controller.saveSystemAuditInfo(audSystemProcessModel);
					} else {
						error("An error occurred on starting up  MANOM scheduler!");
					}
                } else if (choice.equalsIgnoreCase("MANOC")) {
                  boolean manocChk = controller.startPain012();

					if (manocChk) {
						form.remove(form.get("dataTable"));
						form.add(createDataTable(new SchedulerProvider(true)));
						info("MANOC scheduler started successfully ");

						//Save Audit Information
						AudSystemProcessModel audSystemProcessModel = new AudSystemProcessModel();
						audSystemProcessModel.setProcess(
								controller.getProperty("AUD.SCH.MANOC") + " - " +
										controller.getProperty("AUD.SCH.START"));
						audSystemProcessModel.setProcessDate(new Date());
						audSystemProcessModel.setUserId(userName);

						controller.saveSystemAuditInfo(audSystemProcessModel);
					} else {
						error("An error occurred on starting up  MANOC scheduler!");
					}

                } else if (choice.equalsIgnoreCase("ST103")) {
                  boolean st103Chk = controller.startPacs002();

					if (st103Chk) {
						form.remove(form.get("dataTable"));
						form.add(createDataTable(new SchedulerProvider(true)));
						info("ST103 scheduler started successfully ");

						//Save Audit Information
						AudSystemProcessModel audSystemProcessModel = new AudSystemProcessModel();
						audSystemProcessModel.setProcess(
								controller.getProperty("AUD.SCH.ST103") + " - " +
										controller.getProperty("AUD.SCH.START"));
						audSystemProcessModel.setProcessDate(new Date());
						audSystemProcessModel.setUserId(userName);

						controller.saveSystemAuditInfo(audSystemProcessModel);
					} else {
						error("An error occurred on starting up  ST103 scheduler!");
					}
                } else if (choice.equalsIgnoreCase("ST100")) {
                  boolean st100Chk = controller.startST100();

					if (st100Chk) {
						form.remove(form.get("dataTable"));
						form.add(createDataTable(new SchedulerProvider(true)));
						info("ST100 scheduler started successfully ");

						//Save Audit Information
						AudSystemProcessModel audSystemProcessModel = new AudSystemProcessModel();
						audSystemProcessModel.setProcess(
								controller.getProperty("AUD.SCH.ST100") + " - " +
										controller.getProperty("AUD.SCH.START"));
						audSystemProcessModel.setProcessDate(new Date());
						audSystemProcessModel.setUserId(userName);

						controller.saveSystemAuditInfo(audSystemProcessModel);
					} else {
						error("An error occurred on starting up  ST100 scheduler!");
					}
                } else if (choice.equalsIgnoreCase("ST102")) {
                  boolean st102Chk = controller.startST102();
					if (st102Chk) {
						form.remove(form.get("dataTable"));
						form.add(createDataTable(new SchedulerProvider(true)));
						info("ST102 scheduler started successfully ");

						//Save Audit Information
						AudSystemProcessModel audSystemProcessModel = new AudSystemProcessModel();
						audSystemProcessModel.setProcess(
								controller.getProperty("AUD.SCH.ST102") + " - " +
										controller.getProperty("AUD.SCH.START"));
						audSystemProcessModel.setProcessDate(new Date());
						audSystemProcessModel.setUserId(userName);

						controller.saveSystemAuditInfo(audSystemProcessModel);
					} else {
						error("An error occurred on starting up  ST102 scheduler!");
					}
                } else if (choice.equalsIgnoreCase("ST104")) {
                  boolean st104Chk = controller.startST104();
					if (st104Chk) {
						form.remove(form.get("dataTable"));
						form.add(createDataTable(new SchedulerProvider(true)));
						info("ST104 scheduler started successfully ");

						//Save Audit Information
						AudSystemProcessModel audSystemProcessModel = new AudSystemProcessModel();
						audSystemProcessModel.setProcess(
								controller.getProperty("AUD.SCH.ST104") + " - " +
										controller.getProperty("AUD.SCH.START"));
						audSystemProcessModel.setProcessDate(new Date());
						audSystemProcessModel.setUserId(userName);

						controller.saveSystemAuditInfo(audSystemProcessModel);
					} else {
						error("An error occurred on starting up  ST104 scheduler!");
					}
                } else if (choice.equalsIgnoreCase("Billing Export")) {

                  boolean billChk = controller.startBillingExport();
					if (billChk) {
						form.remove(form.get("dataTable"));
						form.add(createDataTable(new SchedulerProvider(true)));
						info("Billing Export scheduler started successfully ");

						//Save Audit Information
						AudSystemProcessModel audSystemProcessModel = new AudSystemProcessModel();
						audSystemProcessModel.setProcess(
								controller.getProperty("AUD.SCH.BILLEXP") + " - " +
										controller.getProperty("AUD.SCH.START"));
						audSystemProcessModel.setProcessDate(new Date());
						audSystemProcessModel.setUserId(userName);

						controller.saveSystemAuditInfo(audSystemProcessModel);
					} else {
						error("An error occurred on starting up Billing Export scheduler!");
					}
                } else if (choice.equalsIgnoreCase("Eot Extract")) {

                  boolean eotExtractChk = controller.startEotExtract();
					if (eotExtractChk) {
						form.remove(form.get("dataTable"));
						form.add(createDataTable(new SchedulerProvider(true)));
						info("Eot Extract scheduler started successfully ");

						//Save Audit Information
						AudSystemProcessModel audSystemProcessModel = new AudSystemProcessModel();
						audSystemProcessModel.setProcess(
								controller.getProperty("AUD.SCH.EOTEXT") + " - " +
										controller.getProperty("AUD.SCH.START"));
						audSystemProcessModel.setProcessDate(new Date());
						audSystemProcessModel.setUserId(userName);

						controller.saveSystemAuditInfo(audSystemProcessModel);
					} else {
						error("An error occurred on starting up Eot Extract scheduler!");
					}
                } else if (choice.equalsIgnoreCase("Reports")) {
                  boolean reportsChk = controller.startDlyReports();

					if (reportsChk) {
						form.remove(form.get("dataTable"));
						form.add(createDataTable(new SchedulerProvider(true)));
						info("Daily Reports scheduler started successfully ");

						//Save Audit Information
						AudSystemProcessModel audSystemProcessModel = new AudSystemProcessModel();
						audSystemProcessModel.setProcess(
								controller.getProperty("AUD.SCH.REPORTS") + " - " +
										controller.getProperty("AUD.SCH.START"));
						audSystemProcessModel.setProcessDate(new Date());
						audSystemProcessModel.setUserId(userName);

						controller.saveSystemAuditInfo(audSystemProcessModel);
					} else {
						error("An error occurred on starting up DLY Reports scheduler!");
					}
                }
              } else {
                error("An active day does not exist. Please run start of day!");
              }
            } else {
              error("An active day does not exist. Please run start of day!");
            }
          }
        } catch (NumberFormatException e) {
          log.error("<NFE>An error occurred on starting up scheduler: " + e.getMessage());
          e.printStackTrace();
        } catch (Exception ex) {
          log.error("<EX>An error occurred on starting up scheduler: " + ex.getMessage());
          ex.printStackTrace();
        }
      }
    };

    stopButton = new Button("stopButton") {
      @Override
      public void onSubmit() {
        try {

          String choice = schedulers.getChoices().get(Integer.parseInt(schedulers.getValue()))
              .getSchedulerName();

          SystemParameterModel systemParameterModel =
              (SystemParameterModel) controller.retrieveWebActiveSysParameter();
          log.debug("systemParameterModel : " + systemParameterModel);

          if (systemParameterModel != null && systemParameterModel.getActiveInd() != null) {
            webSystemParameterModel = new WebSystemParameterModel();
            webSystemParameterModel =
                new WebAdminTranslator().translateCommonsSystemParametersModelToWebModel(
                    systemParameterModel);
            log.debug("webSystemParameterModel :" + webSystemParameterModel);
            if (webSystemParameterModel.getActiveInd() != null &&
                webSystemParameterModel.getActiveInd().equalsIgnoreCase("Y")) {
              log.info("STOPPING SCHEDULER----> " + choice);

              if (choice.equals("All")) {
                boolean stopAllSch = controller.stopAllSchedulers();

				  if (stopAllSch) {
					  form.remove(form.get("dataTable"));
					  form.add(createDataTable(new SchedulerProvider(true)));
					  info("Scheduler has stopped succesfully");

					  //Save Audit Information
					  AudSystemProcessModel audSystemProcessModel = new AudSystemProcessModel();
					  audSystemProcessModel.setProcess(
							  controller.getProperty("AUD.SCH.ALL") + " - " +
									  controller.getProperty("AUD.SCH.STOP"));
					  audSystemProcessModel.setProcessDate(new Date());
					  audSystemProcessModel.setUserId(userName);

					  controller.saveSystemAuditInfo(audSystemProcessModel);
				  } else {
					  error("An error occurred when trying to stop ALL schedulers!");
				  }
              } else if (choice.equalsIgnoreCase("MANOM")) {
                boolean manomChk = controller.unschedulePain010();

				  if (manomChk) {
					  form.remove(form.get("dataTable"));
					  form.add(createDataTable(new SchedulerProvider(true)));
					  info("MANOM scheduler stopped successfully ");

					  //Save Audit Information
					  AudSystemProcessModel audSystemProcessModel = new AudSystemProcessModel();
					  audSystemProcessModel.setProcess(
							  controller.getProperty("AUD.SCH.MANOM") + " - " +
									  controller.getProperty("AUD.SCH.STOP"));
					  audSystemProcessModel.setProcessDate(new Date());
					  audSystemProcessModel.setUserId(userName);

					  controller.saveSystemAuditInfo(audSystemProcessModel);
				  } else {
					  error("An error occurred when stopping  MANOM scheduler!");
				  }
              } else if (choice.equalsIgnoreCase("MANOC")) {
                boolean manocChk = controller.unschedulePain012();
				  if (manocChk) {
					  form.remove(form.get("dataTable"));
					  form.add(createDataTable(new SchedulerProvider(true)));
					  info("MANOC scheduler stopped successfully ");

					  //Save Audit Information
					  AudSystemProcessModel audSystemProcessModel = new AudSystemProcessModel();
					  audSystemProcessModel.setProcess(
							  controller.getProperty("AUD.SCH.MANOC") + " - " +
									  controller.getProperty("AUD.SCH.STOP"));
					  audSystemProcessModel.setProcessDate(new Date());
					  audSystemProcessModel.setUserId(userName);

					  controller.saveSystemAuditInfo(audSystemProcessModel);
				  } else {
					  error("An error occurred when stopping  MANOC scheduler!");
				  }
              } else if (choice.equalsIgnoreCase("ST103")) {
                boolean st103Chk = controller.unschedulePacs002();
				  if (st103Chk) {
					  form.remove(form.get("dataTable"));
					  form.add(createDataTable(new SchedulerProvider(true)));
					  info("ST103 scheduler stopped successfully ");

					  //Save Audit Information
					  AudSystemProcessModel audSystemProcessModel = new AudSystemProcessModel();
					  audSystemProcessModel.setProcess(
							  controller.getProperty("AUD.SCH.ST103") + " - " +
									  controller.getProperty("AUD.SCH.STOP"));
					  audSystemProcessModel.setProcessDate(new Date());
					  audSystemProcessModel.setUserId(userName);

					  controller.saveSystemAuditInfo(audSystemProcessModel);
				  } else {
					  error("An error occurred when stopping  ST103 scheduler!");
				  }
              } else if (choice.equalsIgnoreCase("ST100")) {
                boolean st100Chk = controller.unscheduleST100();
				  if (st100Chk) {
					  form.remove(form.get("dataTable"));
					  form.add(createDataTable(new SchedulerProvider(true)));
					  info("ST100 scheduler stopped successfully ");

					  //Save Audit Information
					  AudSystemProcessModel audSystemProcessModel = new AudSystemProcessModel();
					  audSystemProcessModel.setProcess(
							  controller.getProperty("AUD.SCH.ST100") + " - " +
									  controller.getProperty("AUD.SCH.STOP"));
					  audSystemProcessModel.setProcessDate(new Date());
					  audSystemProcessModel.setUserId(userName);

					  controller.saveSystemAuditInfo(audSystemProcessModel);
				  } else {
					  error("An error occurred when stopping  ST100 scheduler!");
				  }
              } else if (choice.equalsIgnoreCase("ST102")) {
                boolean st102Chk = controller.unscheduleST102();
				  if (st102Chk) {
					  form.remove(form.get("dataTable"));
					  form.add(createDataTable(new SchedulerProvider(true)));
					  info("ST102 scheduler stopped successfully ");

					  //Save Audit Information
					  AudSystemProcessModel audSystemProcessModel = new AudSystemProcessModel();
					  audSystemProcessModel.setProcess(
							  controller.getProperty("AUD.SCH.ST102") + " - " +
									  controller.getProperty("AUD.SCH.STOP"));
					  audSystemProcessModel.setProcessDate(new Date());
					  audSystemProcessModel.setUserId(userName);

					  controller.saveSystemAuditInfo(audSystemProcessModel);
				  } else {
					  error("An error occurred when stopping  ST102 scheduler!");
				  }
              } else if (choice.equalsIgnoreCase("ST104")) {
                boolean st104Chk = controller.unscheduleST104();
				  if (st104Chk) {
					  form.remove(form.get("dataTable"));
					  form.add(createDataTable(new SchedulerProvider(true)));
					  info("ST104 scheduler stopped successfully ");

					  //Save Audit Information
					  AudSystemProcessModel audSystemProcessModel = new AudSystemProcessModel();
					  audSystemProcessModel.setProcess(
							  controller.getProperty("AUD.SCH.ST104") + " - " +
									  controller.getProperty("AUD.SCH.STOP"));
					  audSystemProcessModel.setProcessDate(new Date());
					  audSystemProcessModel.setUserId(userName);

					  controller.saveSystemAuditInfo(audSystemProcessModel);
				  } else {
					  error("An error occurred when stopping  ST104 scheduler!");
				  }
              } else if (choice.equalsIgnoreCase("Billing Export")) {
                boolean billChk = controller.unscheduleBillingExport();
				  if (billChk) {
					  form.remove(form.get("dataTable"));
					  form.add(createDataTable(new SchedulerProvider(true)));
					  info("Billing Export scheduler stopped successfully ");

					  //Save Audit Information
					  AudSystemProcessModel audSystemProcessModel = new AudSystemProcessModel();
					  audSystemProcessModel.setProcess(
							  controller.getProperty("AUD.SCH.BILLEXP") + " - " +
									  controller.getProperty("AUD.SCH.STOP"));
					  audSystemProcessModel.setProcessDate(new Date());
					  audSystemProcessModel.setUserId(userName);

					  controller.saveSystemAuditInfo(audSystemProcessModel);
				  } else {
					  error("An error occurred when stopping Billing Export scheduler!");
				  }
              } else if (choice.equalsIgnoreCase("Eot Extract")) {
                boolean eotExtractChk = controller.unscheduleEotExtract();
				  if (eotExtractChk) {
					  form.remove(form.get("dataTable"));
					  form.add(createDataTable(new SchedulerProvider(true)));
					  info("Eot Extract scheduler stopped successfully ");

					  //Save Audit Information
					  AudSystemProcessModel audSystemProcessModel = new AudSystemProcessModel();
					  audSystemProcessModel.setProcess(
							  controller.getProperty("AUD.SCH.EOTEXT") + " - " +
									  controller.getProperty("AUD.SCH.STOP"));
					  audSystemProcessModel.setProcessDate(new Date());
					  audSystemProcessModel.setUserId(userName);

					  controller.saveSystemAuditInfo(audSystemProcessModel);
				  } else {
					  error("An error occurred when stopping Eot Extract sacheduler!");
				  }
              } else if (choice.equalsIgnoreCase("Reports")) {
                boolean reportsChk = controller.unscheduleDlyReports();
				  if (reportsChk) {
					  form.remove(form.get("dataTable"));
					  form.add(createDataTable(new SchedulerProvider(true)));
					  info("Daily Reports scheduler stopped successfully ");

					  //Save Audit Information
					  AudSystemProcessModel audSystemProcessModel = new AudSystemProcessModel();
					  audSystemProcessModel.setProcess(
							  controller.getProperty("AUD.SCH.REPORTS") + " - " +
									  controller.getProperty("AUD.SCH.STOP"));
					  audSystemProcessModel.setProcessDate(new Date());
					  audSystemProcessModel.setUserId(userName);

					  controller.saveSystemAuditInfo(audSystemProcessModel);
				  } else {
					  error("An error occurred when stopping Dly Reports scheduler!");
				  }
              }
            } else {
              error("An active day does not exist. Please run start of day!");
            }
          } else {
            error("An active day does not exist. Please run start of day!");
          }
        } catch (NumberFormatException e) {
          log.error("<NFE>An error occurred when stopping scheduler: " + e.getMessage());
          e.printStackTrace();
        } catch (Exception ex) {
          log.error("<EX>An error occurred when stopping scheduler: " + ex.getMessage());
          ex.printStackTrace();
        }
      }
    };

    rescheduleButton = new Button("rescheduleButton") {
      @Override
      public void onSubmit() {
        try {
          String choice = schedulers.getChoices().get(Integer.parseInt(schedulers.getValue()))
              .getSchedulerName();
          log.info("RESCHEDULE SCHEDULER----> " + choice);
          log.info("ACTIVE IND ----> " +
              schedulers.getChoices().get(Integer.parseInt(schedulers.getValue())).getActiveInd());

          //Retrieve Scheduler Status
          if (schedulers.getChoices().get(Integer.parseInt(schedulers.getValue())).getActiveInd()
              .equalsIgnoreCase("Y")) {
            String value = rescheduleInSeconds.getModelObject();
            log.debug("value ---> " + value);

            String subValue = value.substring(0, 2);
            log.debug("subValue ---> " + subValue);

            String rescheduleCron = null;

            //							    	log.info("CRON STRING ----> "+rescheduleCron);
            SchedulerCronModel schedulerCronModel =
                (SchedulerCronModel) controller.retrieveCronIntervalValue(value);
            rescheduleCron = schedulerCronModel.getCronValue();
            log.debug("Cron Interval ==> " + schedulerCronModel.getSchedulerCronInterval());
            log.debug("Cron String ==> " + schedulerCronModel.getCronValue());


            //							    	if(subValue.equalsIgnoreCase("30"))
            //							    	{
            //							    		String secMin = value.substring(3,4);
            //							    		log.info("secMin: "+secMin);
            //							    		if(secMin.equalsIgnoreCase("s"))
            //							    		{
            //							    			rescheduleCron = cron30Sec;
            //							    		}
            //							    		else
            //							    		{
            //							    			rescheduleCron = cron30Min;
            //							    		}
            //							    	}
            //							    	else if(subValue.equalsIgnoreCase("45"))
            //							    	{
            //							    		rescheduleCron = cron45Sec;
            //							    	}
            //							    	else if(subValue.equalsIgnoreCase("60"))
            //							    	{
            //							    		rescheduleCron = cron60Sec;
            //							    	}
            //							    	else if(subValue.equalsIgnoreCase("15"))
            //							    	{
            //							    		rescheduleCron = cron15Min;
            //							    	}
            //							    	else if(subValue.equalsIgnoreCase("ho"))
            //							    	{
            //							    		rescheduleCron = cronHourly;
            //							    	}


            if (choice.equals("All")) {
              boolean reschAllSch = controller.rescheduleAllSchedulers(rescheduleCron, value);

				if (reschAllSch) {
					form.remove(form.get("dataTable"));
					form.add(createDataTable(new SchedulerProvider(true)));
					info("ALL schedulers have been rescheduled successfully!");

					//Save Audit Information
					AudSystemProcessModel audSystemProcessModel = new AudSystemProcessModel();
					audSystemProcessModel.setProcess(controller.getProperty("AUD.SCH.ALL") + " - " +
							controller.getProperty("AUD.SCH.RESCH") + " " + value);
					audSystemProcessModel.setProcessDate(new Date());
					audSystemProcessModel.setUserId(userName);

					controller.saveSystemAuditInfo(audSystemProcessModel);
				} else {
					error("An error on rescheduling ALL schedulers!!");
				}
            } else if (choice.equalsIgnoreCase("MANOM")) {
              boolean manomChk = controller.reschedulePain010(rescheduleCron, value);
				if (manomChk) {
					form.remove(form.get("dataTable"));
					form.add(createDataTable(new SchedulerProvider(true)));
					info("MANOM scheduler rescheduled successfully ");

					//Save Audit Information
					AudSystemProcessModel audSystemProcessModel = new AudSystemProcessModel();
					audSystemProcessModel.setProcess(
							controller.getProperty("AUD.SCH.MANOM") + " - " +
									controller.getProperty("AUD.SCH.RESCH") + " " + value);
					audSystemProcessModel.setProcessDate(new Date());
					audSystemProcessModel.setUserId(userName);

					controller.saveSystemAuditInfo(audSystemProcessModel);
				} else {
					error("An error occurred when rescheduling MANOM scheduler!");
				}
            } else if (choice.equalsIgnoreCase("MANOC")) {
              boolean manocChk = controller.reschedulePain012(rescheduleCron, value);
				if (manocChk) {
					form.remove(form.get("dataTable"));
					form.add(createDataTable(new SchedulerProvider(true)));
					info("MANOC scheduler stopped successfully ");

					//Save Audit Information
					AudSystemProcessModel audSystemProcessModel = new AudSystemProcessModel();
					audSystemProcessModel.setProcess(
							controller.getProperty("AUD.SCH.MANOC") + " - " +
									controller.getProperty("AUD.SCH.RESCH") + " " + value);
					audSystemProcessModel.setProcessDate(new Date());
					audSystemProcessModel.setUserId(userName);

					controller.saveSystemAuditInfo(audSystemProcessModel);
				} else {
					error("An error occurred when rescheduling MANOC scheduler!");
				}
            } else if (choice.equalsIgnoreCase("ST103")) {
              boolean st103Chk = controller.reschedulePacs002(rescheduleCron, value);
				if (st103Chk) {
					form.remove(form.get("dataTable"));
					form.add(createDataTable(new SchedulerProvider(true)));
					info("ST103 scheduler rescheduled successfully ");

					//Save Audit Information
					AudSystemProcessModel audSystemProcessModel = new AudSystemProcessModel();
					audSystemProcessModel.setProcess(
							controller.getProperty("AUD.SCH.ST103") + " - " +
									controller.getProperty("AUD.SCH.RESCH") + " " + value);
					audSystemProcessModel.setProcessDate(new Date());
					audSystemProcessModel.setUserId(userName);

					controller.saveSystemAuditInfo(audSystemProcessModel);
				} else {
					error("An error occurred when rescheduling ST103 scheduler!");
				}
            } else if (choice.equalsIgnoreCase("ST100")) {
              boolean st100Chk = controller.rescheduleST100(rescheduleCron, value);
				if (st100Chk) {
					form.remove(form.get("dataTable"));
					form.add(createDataTable(new SchedulerProvider(true)));
					info("ST100 scheduler rescheduled successfully ");

					//Save Audit Information
					AudSystemProcessModel audSystemProcessModel = new AudSystemProcessModel();
					audSystemProcessModel.setProcess(
							controller.getProperty("AUD.SCH.ST100") + " - " +
									controller.getProperty("AUD.SCH.RESCH") + " " + value);
					audSystemProcessModel.setProcessDate(new Date());
					audSystemProcessModel.setUserId(userName);

					controller.saveSystemAuditInfo(audSystemProcessModel);
				} else {
					error("An error occurred when rescheduling ST100 scheduler!");
				}
            } else if (choice.equalsIgnoreCase("ST102")) {
              boolean st102Chk = controller.rescheduleST102(rescheduleCron, value);
				if (st102Chk) {
					form.remove(form.get("dataTable"));
					form.add(createDataTable(new SchedulerProvider(true)));
					info("ST102 scheduler rescheduled successfully ");

					//Save Audit Information
					AudSystemProcessModel audSystemProcessModel = new AudSystemProcessModel();
					audSystemProcessModel.setProcess(
							controller.getProperty("AUD.SCH.ST102") + " - " +
									controller.getProperty("AUD.SCH.RESCH") + " " + value);
					audSystemProcessModel.setProcessDate(new Date());
					audSystemProcessModel.setUserId(userName);

					controller.saveSystemAuditInfo(audSystemProcessModel);
				} else {
					error("An error occurred when rescheduling ST102 scheduler!");
				}
            } else if (choice.equalsIgnoreCase("ST104")) {
              boolean st104Chk = controller.rescheduleST104(rescheduleCron, value);
				if (st104Chk) {
					form.remove(form.get("dataTable"));
					form.add(createDataTable(new SchedulerProvider(true)));
					info("ST104 scheduler rescheduled successfully ");

					//Save Audit Information
					AudSystemProcessModel audSystemProcessModel = new AudSystemProcessModel();
					audSystemProcessModel.setProcess(
							controller.getProperty("AUD.SCH.ST104") + " - " +
									controller.getProperty("AUD.SCH.RESCH") + " " + value);
					audSystemProcessModel.setProcessDate(new Date());
					audSystemProcessModel.setUserId(userName);

					controller.saveSystemAuditInfo(audSystemProcessModel);
				} else {
					error("An error occurred when rescheduling ST104 scheduler!");
				}
            } else if (choice.equalsIgnoreCase("Billing Export")) {
              boolean billChk = controller.rescheduleBillingExport(rescheduleCron, value);
				if (billChk) {
					form.remove(form.get("dataTable"));
					form.add(createDataTable(new SchedulerProvider(true)));
					info("Billing Export scheduler rescheduled successfully ");

					//Save Audit Information
					AudSystemProcessModel audSystemProcessModel = new AudSystemProcessModel();
					audSystemProcessModel.setProcess(
							controller.getProperty("AUD.SCH.BILLEXP") + " - " +
									controller.getProperty("AUD.SCH.RESCH") + " " + value);
					audSystemProcessModel.setProcessDate(new Date());
					audSystemProcessModel.setUserId(userName);

					controller.saveSystemAuditInfo(audSystemProcessModel);
				} else {
					error("An error occurred when rescheduling Billing Export scheduler!");
				}
            } else if (choice.equalsIgnoreCase("Eot Extract")) {
              boolean eotExtractChk = controller.rescheduleEotExtract(rescheduleCron, value);
				if (eotExtractChk) {
					form.remove(form.get("dataTable"));
					form.add(createDataTable(new SchedulerProvider(true)));
					info("Eot Extract scheduler rescheduled successfully ");

					//Save Audit Information
					AudSystemProcessModel audSystemProcessModel = new AudSystemProcessModel();
					audSystemProcessModel.setProcess(
							controller.getProperty("AUD.SCH.EOTEXT") + " - " +
									controller.getProperty("AUD.SCH.RESCH") + " " + value);
					audSystemProcessModel.setProcessDate(new Date());
					audSystemProcessModel.setUserId(userName);

					controller.saveSystemAuditInfo(audSystemProcessModel);
				} else {
					error("An error occurred when rescheduling Eot Extract scheduler!");
				}
            } else if (choice.equalsIgnoreCase("Reports")) {
              boolean reportsChk = controller.rescheduleDlyReports(rescheduleCron, value);
				if (reportsChk) {
					form.remove(form.get("dataTable"));
					form.add(createDataTable(new SchedulerProvider(true)));
					info("Daily Reports scheduler rescheduled successfully ");

					//Save Audit Information
					AudSystemProcessModel audSystemProcessModel = new AudSystemProcessModel();
					audSystemProcessModel.setProcess(
							controller.getProperty("AUD.SCH.REPORTS") + " - " +
									controller.getProperty("AUD.SCH.RESCH") + " " + value);
					audSystemProcessModel.setProcessDate(new Date());
					audSystemProcessModel.setUserId(userName);

					controller.saveSystemAuditInfo(audSystemProcessModel);
				} else {
					error("An error occurred when rescheduling Dly Reports scheduler!");
				}
            }
          } else {
            error("Scheduler is not started. Please start scheduler before rescheduling!");
          }
        } catch (NumberFormatException e) {
          log.error("<NFE>An error occurred when rescheduling scheduler: " + e.getMessage());
          e.printStackTrace();
        } catch (Exception ex) {
          log.error("<EX>An error occurred when rescheduling scheduler: " + ex.getMessage());
          ex.printStackTrace();
        }
      }
    };


    form.add(stopButton);
    form.add(startButton);
    form.add(rescheduleButton);

    add(form);
    add(new FeedbackPanel("feedbackPanel"));
  }

  private DefaultDataTable createDataTable(SchedulerProvider schedulerProvider) {
    DefaultDataTable dataTable;

    List<IColumn> columns = new ArrayList<IColumn>();

    CheckBoxColumn<WebQuartzSchedulerModel> column =
        new CheckBoxColumn<WebQuartzSchedulerModel>(Model.of("Select")) {

          private static final long serialVersionUID = 1L;

          protected IModel newCheckBoxModel(final IModel<WebQuartzSchedulerModel> rowModel) {

            return new AbstractCheckBoxModel() {

              @Override
              public boolean isSelected() {

                return selected.contains(rowModel.getObject());
              }

              @Override
              public void select() {
                if (selected.size() > 0) {
                  unselect();
                }
                selected.add(rowModel.getObject());


              }

              @Override
              public void unselect() {
                selected.remove(rowModel.getObject());

              }

              @Override
              public void detach() {
                rowModel.detach();
              }
            };
          }


        };

    // columns.add(column);

    //columns.add(new PropertyColumn(new Model<String>("Scheduler Key"),"schedulerKey",
	  // "schedulerKey"));
    columns.add(
        new PropertyColumn(new Model<String>("Scheduler Name"), "schedulerName", "schedulerName"));
    columns.add(new PropertyColumn(new Model<String>("Reschedule Time"), "rescheduleTime",
        "rescheduleTime"));
    columns.add(new PropertyColumn(new Model<String>("Active Ind"), "activeInd", "activeInd"));

    dataTable = new DefaultDataTable("dataTable", columns, schedulerProvider, 20);

    return dataTable;
  }

  public void loadData() {
    List<SchedulerCommonsModel> schedulerCommonsModelListfromDB =
        new ArrayList<SchedulerCommonsModel>();
    //Check for active day
    SystemParameterModel systemParameterModel =
        (SystemParameterModel) controller.retrieveWebActiveSysParameter();
    log.debug("systemParameterModel : " + systemParameterModel);

    if (systemParameterModel != null && systemParameterModel.getActiveInd() != null) {
      if (systemParameterModel.getActiveInd().equalsIgnoreCase("Y")) {
        schedulerCommonsModelListfromDB =
            (List<SchedulerCommonsModel>) controller.viewAllOpsSchedulers();
      } else {
        schedulerCommonsModelListfromDB =
            (List<SchedulerCommonsModel>) controller.viewAllSchedulers();
      }

      if (schedulerCommonsModelListfromDB != null && schedulerCommonsModelListfromDB.size() > 0) {
        schedulersList = new ArrayList<WebQuartzSchedulerModel>();

        for (SchedulerCommonsModel localCommonsModel : schedulerCommonsModelListfromDB) {
          WebQuartzSchedulerModel webQuartzSchedulerModel = new WebQuartzSchedulerModel();
          webQuartzSchedulerModel =
              new WebAdminTranslator().translateSchedulerCommonsModelToWebModel(localCommonsModel);
          schedulersList.add(webQuartzSchedulerModel);
        }
      }
    } else {
      schedulerCommonsModelListfromDB =
          (List<SchedulerCommonsModel>) controller.viewAllSchedulers();
      if (schedulerCommonsModelListfromDB != null && schedulerCommonsModelListfromDB.size() > 0) {
        schedulersList = new ArrayList<WebQuartzSchedulerModel>();

        for (SchedulerCommonsModel localCommonsModel : schedulerCommonsModelListfromDB) {
          WebQuartzSchedulerModel webQuartzSchedulerModel = new WebQuartzSchedulerModel();
          webQuartzSchedulerModel =
              new WebAdminTranslator().translateSchedulerCommonsModelToWebModel(localCommonsModel);
          schedulersList.add(webQuartzSchedulerModel);
        }
      }
    }
  }

  public void retrieveSessionInfo() {
    session = getSession();
    clientSessionModel = (ClientSessionModel) session.getAttribute("role");
    userName = clientSessionModel.getUsername();
    log.debug("clientSessionModel ==> " + clientSessionModel);
  }

}
