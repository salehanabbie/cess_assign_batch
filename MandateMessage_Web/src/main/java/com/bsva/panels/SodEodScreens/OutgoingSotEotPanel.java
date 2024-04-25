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
import com.bsva.commons.model.ServicesModel;
import com.bsva.commons.model.SysCisBankModel;
import com.bsva.commons.model.SystemControlServicesModel;
import com.bsva.controller.Controller;
import com.bsva.models.WebOutSotEotModel;
import com.bsva.models.WebServicesModel;
import com.bsva.models.WebSysCisBankModel;
import com.bsva.models.WebSystemControlServicesModel;
import com.bsva.panels.CheckBoxColumn;
import com.bsva.provider.OpsAcSotEotProvider;
import com.bsva.translator.WebAdminTranslator;

public class OutgoingSotEotPanel extends Panel implements Serializable
{
	public static Logger log = Logger.getLogger(IncomingSotEotPanel.class);
	public static String id;
	private Form form;
	private Controller controller;
	private String action;
	private String selectedIndicator;
	private Set<WebOutSotEotModel> selected = new HashSet<WebOutSotEotModel>();
	private Button searchButton, viewAllButton;
	//private List<WebServicesModel> serviceIdOutList = new ArrayList<WebServicesModel>();
	//private DropDownChoice<WebServicesModel> serviceId;
	private DropDownChoice<WebSystemControlServicesModel> serviceIdOut;
	private DropDownChoice<String > sotOut;
	private DropDownChoice<String > eotOut;
	private DropDownChoice<WebSysCisBankModel> instId;
	private WebServicesModel webServicesModel;
	private WebOutSotEotModel webOutSotEotModel;
	private List<WebOutSotEotModel> webSotEotModelList =  new ArrayList<WebOutSotEotModel>();
	private WebSysCisBankModel webSysCisBankModel;
	private List<WebSysCisBankModel> instIdList = new ArrayList<WebSysCisBankModel>();
	private List<WebServicesModel> webServicesModelList =  new ArrayList<WebServicesModel>();
	private WebSystemControlServicesModel webSystemServices;
	private List<WebSystemControlServicesModel> serviceIdOutList  = new ArrayList<WebSystemControlServicesModel>();
	
	public OutgoingSotEotPanel(String id)
	{
		super(id);
		this.id = id;
		initializeComponents();
	}
	
	public OutgoingSotEotPanel(String id,WebSysCisBankModel webSysCisBankModel,WebServicesModel webServicesModel,WebOutSotEotModel webOutSotEotModel,String action )
	{
		super(id);
		this.action = action;
		this.id = id;
		this.webServicesModel = webServicesModel;
		this.webSysCisBankModel = webSysCisBankModel;
		this.webOutSotEotModel = webOutSotEotModel;
		this.webServicesModel = webServicesModel;
		this.webSystemServices = webSystemServices;
		initializeComponents();
		log.debug("Screen ID: " + action);
	}
	
	public void initializeComponents()
	{
		controller = new Controller();
		loadData();
		form = new Form("form");
		form.add(createOutgoingDataTable(new OpsAcSotEotProvider()));
		
			searchButton = new Button("searchButton") 
			{
				@Override
				public void onSubmit()
				{
					instId.setRequired(true);
					//serviceId.setRequired(true);
					serviceIdOut.setRequired(true);
					try
					{
						if((instId.getValue() == null || instId.getValue().isEmpty()) && (serviceIdOut.getValue() == null || serviceIdOut.getValue().isEmpty()))
						{
							error("Please select Inst Id & Service Id to search");
							form.remove(form.get("outgoingDataTableStatus"));
							form.add(createOutgoingDataTable(new OpsAcSotEotProvider()));
						}
						if((instId.getValue() != null) && (serviceIdOut.getValue() == null || serviceIdOut.getValue().isEmpty()))
						{
							error("Please select Inst Id & Service Id to search");
						}
						else
						{
							String memberId = instId.getChoices().get(Integer.parseInt(instId.getValue())).getMemberNo();
							String serviceIdOutg = serviceIdOut.getChoices().get(Integer.parseInt(serviceIdOut.getValue())).getServiceIdOut();
							String sotOutInd = sotOut.getChoices().get(Integer.parseInt(sotOut.getValue()));
							String eotOutInd = eotOut.getChoices().get(Integer.parseInt(eotOut.getValue()));
							form.remove(form.get("outgoingDataTableStatus"));
							form.add(createOutgoingDataTable(new OpsAcSotEotProvider(memberId, serviceIdOutg, sotOutInd, eotOutInd)));
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
					form.remove(form.get("outgoingDataTableStatus"));
					form.add(createOutgoingDataTable(new OpsAcSotEotProvider()));
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
	   
	   serviceIdOut = new DropDownChoice<WebSystemControlServicesModel>("serviceIdOut",new Model<WebSystemControlServicesModel>(), serviceIdOutList,new ChoiceRenderer<WebSystemControlServicesModel>());
		serviceIdOut.setRequired(false);
		if (action == "create")
		{
			log.info("serviceIdOutList at dropdown ====> " + serviceIdOutList);
			if(serviceIdOutList != null && serviceIdOutList.size() > 0)
			{
				serviceIdOut.setDefaultModelObject(serviceIdOut.getChoices().get(0).getServiceIdOut());
			}
			else
			{
				log.error("serviceIdInList is Null" + serviceIdOutList);
			}
		}
	   form.add(serviceIdOut);
	   
	   List<String> indicators = new ArrayList<String>();
		indicators.add("Y");
		indicators.add("N");
		if(webOutSotEotModel != null)
		{
			if(webOutSotEotModel.getSotOut() != null && webOutSotEotModel.getSotOut().equals("Y"))
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
		
		sotOut = new DropDownChoice<String>("sotOut", new Model<String>(selectedIndicator), indicators);
		sotOut.setRequired(true);		
		form.add(sotOut);
		if(webOutSotEotModel != null)
		{
			if(webOutSotEotModel.getEotOut() != null && webOutSotEotModel.getEotOut().equals("Y"))
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
		
		eotOut = new DropDownChoice<String>("eotOut", new Model<String>(selectedIndicator), indicators);
		eotOut.setRequired(true);
		form.add(eotOut);	
		if(webOutSotEotModel != null)
		{
			if(webOutSotEotModel.getSotOut() != null && webOutSotEotModel.getSotOut().equals("Y"))
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
	   	
	private DefaultDataTable createOutgoingDataTable(OpsAcSotEotProvider outgoingSotEotProvider) 
	{
		DefaultDataTable outgoingDataTableStatus;
		final List<IColumn> columns = new ArrayList<IColumn>();

				IColumn column = new CheckBoxColumn<WebOutSotEotModel>(Model.of("Select")) 
				{
					@Override
					protected IModel newCheckBoxModel(final IModel<WebOutSotEotModel> rowModel) 
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
				columns.add(new PropertyColumn(new Model<String>("SotOut"),"sotOut", "sotOut"));
				columns.add(new PropertyColumn(new Model<String>("EotOut"),"eotOut", "eotOut"));
				outgoingDataTableStatus = new DefaultDataTable("outgoingDataTableStatus", columns,outgoingSotEotProvider, 10);

				return outgoingDataTableStatus;
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
		
		List<SystemControlServicesModel> serviceIdOutListFromDb = new ArrayList<SystemControlServicesModel>();
		serviceIdOutListFromDb = (List<SystemControlServicesModel>) controller.viewAllRecordId();
		Collections.sort(serviceIdOutListFromDb, new serviceIdOrderSorter());
		if (serviceIdOutListFromDb.size() > 0) 
		{
			for (SystemControlServicesModel servModel : serviceIdOutListFromDb) 
			{
				WebSystemControlServicesModel webLocalModel = new WebSystemControlServicesModel();
				webLocalModel = new WebAdminTranslator().translateCommonsSystemControlServicesModelToWebModel(servModel);
				serviceIdOutList.add(webLocalModel);
			}
		}// end of if
	}

	private class serviceIdOrderSorter implements Comparator<SystemControlServicesModel>
	{
		@Override
		public int compare(SystemControlServicesModel o1, SystemControlServicesModel o2) 
		{
			if(o1.getServiceIdOut() == null && o2.getServiceIdOut() == null)
			{
				return 0;
			}
			else if(o1.getServiceIdOut() == null)
			{
				return -1;
			}
			else if(o2.getServiceIdOut() == null)
			{
				return 1;
			}
			return o1.getServiceIdOut().compareTo(o2.getServiceIdOut());
		}
	}
	
	private class serviceIdOutOrderSorter implements Comparator<ServicesModel>
	{
		@Override
		public int compare(ServicesModel o1, ServicesModel o2) 
		{
			if(o1.getServiceIdOut() == null && o2.getServiceIdOut() == null)
			{
				return 0;
			}
			else if(o1.getServiceIdOut() == null)
			{
				return -1;
			}
			else if(o2.getServiceIdOut() == null)
			{
				return 1;
			}
			return o1.getServiceIdOut().compareTo(o2.getServiceIdOut());
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
