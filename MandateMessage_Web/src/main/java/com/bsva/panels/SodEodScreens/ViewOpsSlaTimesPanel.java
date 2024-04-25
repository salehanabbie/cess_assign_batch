package com.bsva.panels.SodEodScreens;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.Session;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.extensions.model.AbstractCheckBoxModel;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.validation.validator.PatternValidator;
import org.apache.wicket.validation.validator.StringValidator;
import com.bsva.TemplatePage;
import com.bsva.commons.model.OpsSlaTimesCommonsModel;
import com.bsva.controller.Controller;
import com.bsva.models.ClientSessionModel;
import com.bsva.models.WebAccountTypeModel;
import com.bsva.models.WebAmendmentCodesModel;
import com.bsva.models.WebAuditTrackingModel;
import com.bsva.models.WebLocalInstrumentCodesModel;
import com.bsva.models.WebOpsSlaTimesModel;
import com.bsva.panels.CheckBoxColumn;
import com.bsva.panels.amendmentCodes.MaintainAmendmentCodesPanel;
import com.bsva.panels.localInstrCodes.MaintainLocalInstrPanel;
import com.bsva.provider.AmendmentCodesProvider;
import com.bsva.provider.IncomingFileMonitoringProvider;
import com.bsva.provider.OpsSlaTimesProvider;
import com.bsva.provider.ViewIncomingFilesProvider;
import com.bsva.translator.WebAdminTranslator;
import com.bsva.validators.TextFieldValidator;

public class ViewOpsSlaTimesPanel  extends Panel implements Serializable 
{
	/**
	 * @author ElelwaniR
	 */
	private static final long serialVersionUID = 1L;
	public static Logger log = Logger.getLogger(ViewOpsSlaTimesPanel.class);
	public static String id;
	private Form form;
	private Set<WebOpsSlaTimesModel> selected = new HashSet<WebOpsSlaTimesModel>();
	MaintainOpsSlaTimesPanel maintainOpsSlaTimesPanel;
	private Button updateButton;
	private Button searchButton;
	private Button viewAllButton;
	private TextField<String> searchText;
	String action;
	String textValidator = "([a-zA-Z0-9 \\-._()]+)";
	Controller controller;
	private DropDownChoice<String> extendedHour;
	private Button extendSlaTimeButton;
	private String selectedHour;
	private String hour1, hour2;
	DropDownChoice<WebOpsSlaTimesModel > services;
	private WebOpsSlaTimesModel webOpsSlaTimesModel;
	private List<WebOpsSlaTimesModel> webSlaTimesList = new ArrayList<WebOpsSlaTimesModel>();
	private List<WebOpsSlaTimesModel> serviceList = new ArrayList<WebOpsSlaTimesModel>();
	//private List<String> serviceList = new ArrayList<String>();
	String cis, sod, eod;
	public static Session session;
	private ClientSessionModel clientSessionModel;
	private String userName;
	private Date startTimeDate = null;
	private Date endTimeDate = null;
	private Date startTime = null;
	private String endTime = null;
	private SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
	private Date currentDate = new Date(); 
	private String endTimeString;
	private DateFormat timeInstance ;
	private int result;
	
	public ViewOpsSlaTimesPanel(String id) 
	{
		super(id);
		this.id = id;
		initializeComponents ();
	}
	
	
	public void initializeComponents ()
	{
		controller = new Controller();
		form = new Form("viewOpsSlaTimesPanel");
		form.add(createDataTable(new OpsSlaTimesProvider()));
		loadData();
	
		updateButton = new Button("updateButton")
		{
			@Override
			public void onSubmit() 
			{
				if(selected.size() > 1)
				{
					error("Please select only one record...");
				}
				else if(selected.size() <=0)
				{
					error("No record was selected...");
				}
				else
				{
					for(WebOpsSlaTimesModel webOpsSlaTimesModel: selected)
					{
						maintainOpsSlaTimesPanel = new MaintainOpsSlaTimesPanel(id, webOpsSlaTimesModel, "update");
						MarkupContainer markupContainer = form.getParent().getParent();
						markupContainer.remove(form.getParent());
						markupContainer.add(maintainOpsSlaTimesPanel);
						maintainOpsSlaTimesPanel.setOutputMarkupId(true);
						maintainOpsSlaTimesPanel.setOutputMarkupPlaceholderTag(true);
						TemplatePage.setContent(maintainOpsSlaTimesPanel);
						break;
					}
				}
			}
		};
		updateButton.setDefaultFormProcessing(false);
		
		searchButton = new Button ("searchButton")
		{
			private static final long SerialVersionUID = 1L;
			@Override
			
			public void onSubmit()
			{
				viewAllButton.setEnabled(true);
				
				log.info("Search Text: " + searchText.getValue());
				if(searchText.getValue() == null || searchText.getValue().isEmpty())
				{
					error("Search Field is not populated.");
				}
				else
				{
					form.remove(form.get("dataTable"));
					form.add(createDataTable(new OpsSlaTimesProvider(searchText.getValue().trim().toUpperCase())));
				}
			}
		};
		searchText = new TextField<String>("searchText", new Model<String>());
		searchText.add(StringValidator.maximumLength(5));
		searchText.add(new PatternValidator(textValidator));
		
		viewAllButton = new Button ("viewAllButton")
		{
			private static final long SerialVersionUID = 1L;
			
			@Override
			public void onSubmit()
			{
				viewAllButton.setEnabled(false);
				form.remove(form.get("dataTable"));
				form.add(createDataTable(new OpsSlaTimesProvider()));
			}
		};
		
		services = new DropDownChoice<WebOpsSlaTimesModel>("services", new Model<WebOpsSlaTimesModel>(), serviceList, new ChoiceRenderer<WebOpsSlaTimesModel>());
//		services.setRequired(true);
		if (action == "create")
		{
			if(serviceList != null && serviceList.size() > 0)
			{
				services.setDefaultModelObject(services.getChoices().get(0).getService());
			}
		}
		
		List<String> extendedBy = new ArrayList<String>();
		extendedBy.add("0");
		extendedBy.add("1");
		extendedBy.add("2");
		extendedBy.add("3");
		extendedHour = new DropDownChoice<String>("extendedBy", new Model<String>(selectedHour), extendedBy);
		
		extendSlaTimeButton = new Button ("extendSlaTime")
		{
			private static final long SerialVersionUID = 1L;
			
			@Override
			public void onSubmit()
			{
					services.setRequired(true);
					extendedHour.setRequired(true);
					extendSlaTimeButton.setEnabled(true);
//					timeInstance = SimpleDateFormat.getTimeInstance();
//					String systemTime = timeInstance.format(Calendar.getInstance().getTime());
//					log.info("~~~~~~~~~~~~~~~~~~~~~~SYSTEM TIME~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" + systemTime);
					try
					{
						Calendar cal = Calendar.getInstance();
						cal.setTime(currentDate);
						currentDate = cal.getTime();
						String strrDate = parser.format(currentDate);
						log.info("SystemTime: "+strrDate);
						Date userDate = parser.parse(strrDate);
						if((services.getValue() == null ||services.getValue().isEmpty()) && (extendedHour.getValue() == null || extendedHour.getValue().isEmpty()))
						{
							error("Please select Service and Extended By to extend SLA Time"); 
							form.remove(form.get("dataTable"));
							form.add(createDataTable(new OpsSlaTimesProvider()));
						}
						if((services.getValue() != null) && (extendedHour.getValue() == null || extendedHour.getValue().isEmpty()))
						{
							error("Please select Service and Extended By to extend SLA Time");
						}
						else
						{
							String  service = services.getChoices().get(Integer.parseInt(services.getValue())).getService();
							String endTimeDB = services.getChoices().get(Integer.parseInt(services.getValue())).getEndTime();
							log.info("Service =======>>>>>" + service + "  End Time ==========>>>>>" + endTimeDB);
								if((services.getValue() != null) && (extendedHour.getValue().equals("1")))
								{
									OpsSlaTimesCommonsModel commonSlaModel = (OpsSlaTimesCommonsModel) controller.retrieveOpsSlaTimes(service);
									log.info("commonSlaModel IN VIEW PANEL ---->" + commonSlaModel);
									log.info("SERVICE of COMMONSLAMODEL:: --->" + commonSlaModel.getService());
									startTimeDate = parser.parse(commonSlaModel.getStartTime());
									endTimeString = commonSlaModel.getEndTime();
									log.info("End Time in String format .. :" +endTimeString);
									endTimeDate = parser.parse(endTimeString);
									cal = Calendar.getInstance();
									cal.setTime(endTimeDate);
									endTime = parser.format(endTimeDate);
									Date endT = DateUtils.addHours(cal.getTime(),1 );
									String newEndT = parser.format(endT);
									log.info("START TIME ::" +  startTimeDate);
									log.info("End Time Date ::" +  parser.parse(commonSlaModel.getEndTime()));
									log.info("End Time ---->" + endTime );
									log.info("END T ----------->>>" + endT);
									log.info("NEW END TIME ---->" + newEndT);
									log.info("newCommonsModel =" + commonSlaModel);
//									result = strrDate.compareTo(newEndT);
//									log.info("RESULT OF STRING COMPARE ------>" + result);
//									if(result == 0)
//									{
//										error("Service already closed, cannot amend");
//									}
//									else if(result < 0)
//									{
//										error("Service already closed, cannot amend");
//									}
//									else
//									{
										commonSlaModel.setEndTime(newEndT);
										controller.saveOrUpdateExtendSlaTimes(commonSlaModel);
										log.info("CommonsSlaModel after saveorUpdate ====>" + commonSlaModel);
										form.remove(form.get("dataTable"));
										form.add(createDataTable(new OpsSlaTimesProvider(service)));
										viewAllButton.setEnabled(true);
//									}
								}
								else if((services.getValue() != null) && (extendedHour.getValue().equals("2")))
								{
									OpsSlaTimesCommonsModel commonSlaModel = (OpsSlaTimesCommonsModel) controller.retrieveOpsSlaTimes(service);
									log.info("commonSlaModel IN VIEW PANEL ---->" + commonSlaModel);
									log.info("SERVICE of COMMONSLAMODEL:: --->" + commonSlaModel.getService());
									startTimeDate = parser.parse(commonSlaModel.getStartTime());
									endTimeString = commonSlaModel.getEndTime();
									log.info("End Time in String format .. :" +endTimeString);
									endTimeDate = parser.parse(endTimeString);
									cal = Calendar.getInstance();
									cal.setTime(endTimeDate);
									endTime = parser.format(endTimeDate);
									Date endT = DateUtils.addHours(cal.getTime(),2 );
									String newEndT = parser.format(endT);
									log.info("START TIME ::" +  startTimeDate);
									log.info("End Time Date ::" +  parser.parse(commonSlaModel.getEndTime()));
									log.info("End Time ---->" + endTime );
									log.info("END T ----------->>>" + endT);
									log.info("NEW END TIME ---->" + newEndT);
									log.info("newCommonsModel =" + commonSlaModel);
//									if(result == 0)
//									{
//										error("Service already closed, cannot amend");
//									}
//									else if(result < 0)
//									{
//										error("Service already closed, cannot amend");
//									}
//									else
//									{
										commonSlaModel.setEndTime(newEndT);
										controller.saveOrUpdateExtendSlaTimes(commonSlaModel);
										log.info("CommonsSlaModel after saveorUpdate ====>" + commonSlaModel);
										form.remove(form.get("dataTable"));
										form.add(createDataTable(new OpsSlaTimesProvider(service)));
										viewAllButton.setEnabled(true);
//									}
								}
								else if((services.getValue() != null) && (extendedHour.getValue().equals("3")))
								{
									OpsSlaTimesCommonsModel commonSlaModel = (OpsSlaTimesCommonsModel) controller.retrieveOpsSlaTimes(service);
									log.info("commonSlaModel IN VIEW PANEL ---->" + commonSlaModel);
									log.info("SERVICE of COMMONSLAMODEL:: --->" + commonSlaModel.getService());
									startTimeDate = parser.parse(commonSlaModel.getStartTime());
									endTimeString = commonSlaModel.getEndTime();
									log.info("End Time in String format .. :" +endTimeString);
									endTimeDate = parser.parse(endTimeString);
									cal = Calendar.getInstance();
									cal.setTime(endTimeDate);
									endTime = parser.format(endTimeDate);
									Date endT = DateUtils.addHours(cal.getTime(),3 );
									String newEndT = parser.format(endT);
									log.info("START TIME ::" +  startTimeDate);
									log.info("End Time Date ::" +  parser.parse(commonSlaModel.getEndTime()));
									log.info("End Time ---->" + endTime );
									log.info("END T ----------->>>" + endT);
									log.info("NEW END TIME ---->" + newEndT);
									log.info("newCommonsModel =" + commonSlaModel);
//									if(result == 0)
//									{
//										error("Service already closed, cannot amend");
//									}
//									else if(result < 0)
//									{
//										error("Service already closed, cannot amend");
//									}
//									else
//									{
										commonSlaModel.setEndTime(newEndT);
										controller.saveOrUpdateExtendSlaTimes(commonSlaModel);
										log.info("CommonsSlaModel after saveorUpdate ====>" + commonSlaModel);
										form.remove(form.get("dataTable"));
										form.add(createDataTable(new OpsSlaTimesProvider(service)));
										viewAllButton.setEnabled(true);
//									}
								}

						}
					}
					catch (Exception e)
					{
						log.error("Error onSubmit extend SLA Times: " + e.getMessage());
						e.printStackTrace();
					}
			}
		};

		 form.add(updateButton);
		 form.add(searchButton);
		 form.add(searchText);
		 form.add(viewAllButton);
		 form.add(services);
		 form.add(extendedHour);
		 form.add(extendSlaTimeButton);
		 viewAllButton.setEnabled(false);
		 add(form);
		 add(new FeedbackPanel("feedbackPanel"));
	}
	
	private DefaultDataTable createDataTable(OpsSlaTimesProvider opsSlaTimesProvider) 
	{
		DefaultDataTable dataTable;
		
		List<IColumn> columns = new ArrayList<IColumn>();
		IColumn column = new CheckBoxColumn<WebOpsSlaTimesModel>(Model.of("Select")) 
		{
            @Override
            protected IModel newCheckBoxModel(final IModel<WebOpsSlaTimesModel> rowModel) 
            {
               return new AbstractCheckBoxModel() 
               {
                    @Override
                    public boolean isSelected() 
                    {
                       return selected.contains(rowModel.getObject());
                    }

                    @Override
                    public void select() 
                    {
                    	if(selected.size() > 0)
                    	{
                    		unselect();
                    	}
                        selected.add(rowModel.getObject());
                    }

                    @Override
                    public void unselect() 
                    {
                        selected.remove(rowModel.getObject());
                    }
                    
                    @Override
                    public void detach()
                    {
                        rowModel.detach();
                    }
                };
            }
        };
		columns.add(column);
		columns.add(new PropertyColumn(new Model<String>("Service"), "service","service"));
		columns.add(new PropertyColumn(new Model<String>("Start Time"),"startTime","startTime"));
		columns.add(new PropertyColumn(new Model<String>("End Time"),"endTime", "endTime"));
		dataTable = new DefaultDataTable("dataTable", columns,opsSlaTimesProvider, 10);

		return dataTable;
	}
	
	private void loadData()
	{
		//log.info("INSIDE LOAD DATA METHOD-------------------------.........>>>>>>>>>>>>>>>>>*************************");
		List<OpsSlaTimesCommonsModel> serviceListFromDB = new ArrayList<OpsSlaTimesCommonsModel>();
		cis = controller.getProperty("VIEW.CIS");
		sod = controller.getProperty("VIEW.SOD");
		eod = controller.getProperty("VIEW.EOD");
		serviceListFromDB = (List<OpsSlaTimesCommonsModel>) controller.viewOpsSlaServicesWithoutCisSodEod(cis, sod, eod);
		//log.info("serviceListFromDB ===========================" + serviceListFromDB);
		if(serviceListFromDB.size() > 0)
		{
			for(OpsSlaTimesCommonsModel localCommModel : serviceListFromDB)
			{
				WebOpsSlaTimesModel webModel = new WebOpsSlaTimesModel();
				webModel = new WebAdminTranslator().translateOpsSlaTimesCommonsModelToWebModel(localCommModel);
				//String services = new WebOpsSlaTimesModel().getService();
				serviceList.add(webModel);
//				log.info("WebModel***********************"+webModel);
//				log.info("ServiceList***********************"+serviceList);
			}
		}
	}

}
