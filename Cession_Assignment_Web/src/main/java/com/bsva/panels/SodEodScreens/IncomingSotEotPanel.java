package com.bsva.panels.SodEodScreens;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.log4j.Logger;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.extensions.model.AbstractCheckBoxModel;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import com.bsva.commons.model.IncSotEotModel;
import com.bsva.commons.model.ServicesModel;
import com.bsva.commons.model.SysCisBankModel;
import com.bsva.controller.Controller;
import com.bsva.models.WebIncSotEotModel;
import com.bsva.models.WebServicesModel;
import com.bsva.models.WebSysCisBankModel;
import com.bsva.panels.CheckBoxColumn;
import com.bsva.provider.IncomingSotEotProvider;
import com.bsva.translator.WebAdminTranslator;

public class IncomingSotEotPanel extends Panel implements Serializable
{
	public static Logger log = Logger.getLogger(IncomingSotEotPanel.class);
	public static String id;
	private Form form;
	private Controller controller;
	private String action;
	private String selectedIndicator;
	private Set<WebIncSotEotModel> selected = new HashSet<WebIncSotEotModel>();
	private Button searchButton, viewAllButton;
	private List<WebServicesModel> serviceIdInList = new ArrayList<WebServicesModel>();
	private DropDownChoice<WebServicesModel> serviceId;
	private DropDownChoice<String > sotIn;
	private DropDownChoice<String > eotIn;
	private DropDownChoice<WebSysCisBankModel> instId;
	private WebServicesModel webServicesModel;
	private WebIncSotEotModel webIncSotEotModel;
	private List<WebIncSotEotModel> webSotEotModelList =  new ArrayList<WebIncSotEotModel>();
	private WebSysCisBankModel webSysCisBankModel;
	private List<WebSysCisBankModel> instIdList = new ArrayList<WebSysCisBankModel>();
	private List<WebServicesModel> webServicesModelList =  new ArrayList<WebServicesModel>();
	
	public IncomingSotEotPanel(String id) 
	{
		super(id);
		this.id = id;
		initializeComponents ();
	}
	
	public IncomingSotEotPanel(String id,WebSysCisBankModel webSysCisBankModel,WebServicesModel webServicesModel,WebIncSotEotModel webIncSotEotModel,String action )
	{
		super(id);
		this.action = action;
		this.id = id;
		this.webServicesModel = webServicesModel;
		this.webSysCisBankModel = webSysCisBankModel;
		this.webIncSotEotModel = webIncSotEotModel;
		this.webServicesModel = webServicesModel;
		initializeComponents();
		log.debug("Screen ID: " + action);
	}
	
	public void initializeComponents ()
	{
		controller = new Controller();
		loadData();
		form = new Form("form");
		form.add(createIncomingDataTable(new IncomingSotEotProvider()));
		
			searchButton = new Button("searchButton") 
			{
				@Override
				public void onSubmit()
				{
					instId.setRequired(true);
					serviceId.setRequired(true);
					try
					{
						if((instId.getValue() == null || instId.getValue().isEmpty()) && (serviceId.getValue() == null || serviceId.getValue().isEmpty()))
						{
							error("Please select Inst Id & Service Id to search");
							form.remove(form.get("incomingDataTableStatus"));
							form.add(createIncomingDataTable(new IncomingSotEotProvider()));
						}
						if((instId.getValue() != null) && (serviceId.getValue() == null || serviceId.getValue().isEmpty()))
						{
							error("Please select Inst Id & Service Id to search");
						}
						else
						{
							String memberId = instId.getChoices().get(Integer.parseInt(instId.getValue())).getMemberNo();
							String serviceIdInc = serviceId.getChoices().get(Integer.parseInt(serviceId.getValue())).getServiceIdIn();
							String sotInInd = sotIn.getChoices().get(Integer.parseInt(sotIn.getValue()));
							String eotInInd = eotIn.getChoices().get(Integer.parseInt(eotIn.getValue()));
							form.remove(form.get("incomingDataTableStatus"));
							form.add(createIncomingDataTable(new IncomingSotEotProvider(memberId, serviceIdInc, sotInInd, eotInInd)));
						}
					}
					catch(Exception e)
					{
						log.error("Error onSubmit search: " + e.getMessage());
						e.printStackTrace();
					}
					
//					form.remove(form.get("incomingDataTableStatus"));
//					form.add(createIncomingDataTable(new IncomingSotEotProvider()));
					//form.add(createIncomingDataTable(new IncomingSotEotProvider(memberId,serviceIdInc,sotInInd,eotInInd)));
					viewAllButton.setEnabled(true);
				}
			};
			form.add(searchButton);
	    	
	    	viewAllButton = new Button("viewAllButton")
	    	{
	    		@Override
				public void onSubmit()
				{
	    			viewAllButton.setEnabled(false);
					form.remove(form.get("incomingDataTableStatus"));
					form.add(createIncomingDataTable(new IncomingSotEotProvider()));
				}
	    	};
	    	viewAllButton.setEnabled(false);
	    	form.add(viewAllButton);
	    	
	   instId = new DropDownChoice<WebSysCisBankModel>("instId",new Model<WebSysCisBankModel>(), instIdList,new ChoiceRenderer<WebSysCisBankModel>()); 
	   instId.setRequired(true);
	   if (action == "create")
	   {
		   if(instIdList != null && instIdList.size() > 0)
		   {
			   instId.setDefaultModelObject(instId.getChoices().get(0).getMemberNo());
		   }
	   }
	   else if (action == "update") 
	   {
		   int sel = 0;
		   for (int index = 0; index < instIdList.size(); index++) 
		   {
			   if (webSysCisBankModel.getMemberNo().equalsIgnoreCase(instIdList.get(index).getMemberNo())) 
			   {
				   sel = index;
			   }
		   }
		   instId.setModelObject(instId.getChoices().get(sel));
	   }
	   form.add(instId);
	   
	   	serviceId = new DropDownChoice<WebServicesModel>("serviceId",new Model<WebServicesModel>(), serviceIdInList,new ChoiceRenderer<WebServicesModel>());
		serviceId.setRequired(false);
		if (action == "create")
		{
			log.info("serviceIdInList at dropdown ====> " + serviceIdInList);
			if(serviceIdInList != null && serviceIdInList.size() > 0)
			{
				serviceId.setDefaultModelObject(serviceId.getChoices().get(0).getServiceIdIn());
			}
			else
			{
				log.error("serviceIdInList is Null" + serviceIdInList);
			}
//			 if (action == "create")
//			   {
//				   if(serviceIdInList != null && serviceIdInList.size() > 0)
//				   {
//					   serviceId.setDefaultModelObject(serviceId.getChoices().get(0).getServiceIdIn());
//				   }
//			   }
//			   else if (action == "update") 
//			   {
//				   int sel = 0;
//				   for (int index = 0; index < serviceIdInList.size(); index++) 
//				   {
//					   if (webServicesModel.getServiceIdIn().equalsIgnoreCase(serviceIdInList.get(index).getServiceIdIn())) 
//					   {
//						   sel = index;
//					   }
//				   }
//				   serviceId.setModelObject(serviceId.getChoices().get(sel));
//			   }
		}
	   form.add(serviceId);
	   
	   List<String> indicators = new ArrayList<String>();
		indicators.add("Y");
		indicators.add("N");
		if(webIncSotEotModel != null)
		{
			if(webIncSotEotModel.getSotIn() != null && webIncSotEotModel.getSotIn().equals("Y"))
			{
				selectedIndicator = "Y";
			}
			else
			{
				selectedIndicator = "N";
			}
		}
		else
		{
			selectedIndicator="Y";
		}
		
		sotIn = new DropDownChoice<String>("sotIn", new Model<String>(selectedIndicator), indicators);
		sotIn.setRequired(true);		
		form.add(sotIn);
		if(webIncSotEotModel != null)
		{
			if(webIncSotEotModel.getEotIn() != null && webIncSotEotModel.getEotIn().equals("Y"))
			{
				selectedIndicator = "Y";
			}
			else
			{
				selectedIndicator = "N";
			}
		}
		else
		{
			selectedIndicator="Y";
		}
		
		eotIn = new DropDownChoice<String>("eotIn", new Model<String>(selectedIndicator), indicators);
		eotIn.setRequired(true);
		form.add(eotIn);	
		if(webIncSotEotModel != null)
		{
			if(webIncSotEotModel.getSotIn() != null && webIncSotEotModel.getSotIn().equals("Y"))
			{
				selectedIndicator = "Y";
			}
			else
			{
				selectedIndicator = "N";
			}
		}
		else
		{
			selectedIndicator="Y";
		}
		add(form);
    }
	   	
	private DefaultDataTable createIncomingDataTable(IncomingSotEotProvider incomingSotEotProvider) 
	{
		DefaultDataTable incomingDataTableStatus;
		final List<IColumn> columns = new ArrayList<IColumn>();

				IColumn column = new CheckBoxColumn<WebIncSotEotModel>(Model.of("Select")) 
				{
					@Override
					protected IModel newCheckBoxModel(final IModel<WebIncSotEotModel> rowModel) 
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
										if (selected.size() > 0)
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
				
				columns.add(new PropertyColumn(new Model<String>("InstId"),"instId", "instId"));
				columns.add(new PropertyColumn(new Model<String>("ServiceId"),"serviceId", "serviceId"));
				columns.add(new PropertyColumn(new Model<String>("SotIn"),"sotIn", "sotIn"));
				columns.add(new PropertyColumn(new Model<String>("EotIn"),"eotIn", "eotIn"));
				incomingDataTableStatus = new DefaultDataTable("incomingDataTableStatus", columns,incomingSotEotProvider, 10);

				return incomingDataTableStatus;
	}
	
	private void loadData()
	{
		List<SysCisBankModel> instIdListFromDb = new ArrayList<SysCisBankModel>();
		instIdListFromDb = (List<SysCisBankModel>) controller.viewAllMember();
		log.info("instIdListFromDb ===>" + instIdListFromDb);
		Collections.sort(instIdListFromDb, new memberNameOrderSorter());
		if (instIdListFromDb.size() > 0) 
		{
			for (SysCisBankModel localCommonModel : instIdListFromDb) 
			{
				WebSysCisBankModel webModel = new WebSysCisBankModel();
				webModel = new WebAdminTranslator().translateCommonsSysCisBankModelToWebModel(localCommonModel);
				//controller.viewAllMember();           
				instIdList.add(webModel);
			}
		}// end of if
		
		List<ServicesModel> serviceIdOutListFromDb = new ArrayList<ServicesModel>();
		log.info("serviceIdOutListFromDb 1 ===>" + serviceIdOutListFromDb);
		log.info("controller.getProperty(\"SERVICE_IN.NQ\") ===>" + controller.getProperty("SERVICE_IN.NQ"));
		serviceIdOutListFromDb = (List<ServicesModel>) controller.findByServiceIdNotNull(controller.getProperty("SERVICE_IN.NQ"), "serviceIdIn", "IGNORE");//IGNORE is being set as there is no 'value'
		//serviceIdOutListFromDb = (List<ServicesModel>) new IncomingSotEotProvider(controller.getProperty("SERVICE_IN.NQ"), "serviceIdIn", null);
		log.info("serviceIdOutListFromDb 2 ===>" + serviceIdOutListFromDb);
		Collections.sort(serviceIdOutListFromDb, new serviceIdOrderSorter());
		if (serviceIdOutListFromDb.size() > 0) 
		{
			for (ServicesModel servModel : serviceIdOutListFromDb) 
			{
				WebServicesModel webLocalModel = new WebServicesModel();
				webLocalModel = new WebAdminTranslator().translateCommonsServicesModelToWebModel(servModel);
				log.info("serviceIdInList load data method 1 -->" + serviceIdInList);
				if(servModel.getServiceIdIn() == null)
				{
					serviceIdInList.remove(webLocalModel);
				}
				else
				{
					serviceIdInList.add(webLocalModel);	
				}
				log.info("serviceIdInList 2 load data method-->" + serviceIdInList);
			}
		}// end of if
	}

	private class serviceIdOrderSorter implements Comparator<ServicesModel>
	{
		@Override
		public int compare(ServicesModel o1, ServicesModel o2) 
		{
			if(o1.getServiceIdIn() == null && o2.getServiceIdIn() == null)
			{
				return 0;
			}
			else if(o1.getServiceIdIn() == null)
			{
				return -1;
			}
			else if(o2.getServiceIdIn() == null)
			{
				return 1;
			}
			return o1.getServiceIdIn().compareTo(o2.getServiceIdIn());
		}
	}
	
	private class serviceIdIncOrderSorter implements Comparator<ServicesModel>
	{
		@Override
		public int compare(ServicesModel o1, ServicesModel o2) 
		{
			if(o1.getServiceIdIn() == null && o2.getServiceIdIn() == null)
			{
				return 0;
			}
			else if(o1.getServiceIdIn() == null)
			{
				return -1;
			}
			else if(o2.getServiceIdIn() == null)
			{
				return 1;
			}
			return o1.getServiceIdIn().compareTo(o2.getServiceIdIn());
		}
	}
	
	// Sort MemberName in ASCENDING order
	private class memberNameOrderSorter implements Comparator<SysCisBankModel>
	{
		@Override
		public int compare(SysCisBankModel o1, SysCisBankModel o2) 
		{
			if(o1.getMemberName() == null && o2.getMemberName() == null)
			{
				return 0;
			}
			else if(o1.getMemberName() == null)
			{
				return -1;
			}
			else if(o2.getMemberName() == null)
			{
				return 1;
			}
			return o1.getMemberName().compareTo(o2.getMemberName());	
		}
	}

}
