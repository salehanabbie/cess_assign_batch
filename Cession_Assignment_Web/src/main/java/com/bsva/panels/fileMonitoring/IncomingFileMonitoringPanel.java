package com.bsva.panels.fileMonitoring;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.log4j.Logger;
import org.apache.wicket.MarkupContainer;
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
import com.bsva.commons.model.OpsStatusDetailsModel;
import com.bsva.commons.model.OpsStatusHdrsModel;
import com.bsva.commons.model.ServicesModel;
import com.bsva.commons.model.SysCisBankModel;
import com.bsva.commons.model.SystemParameterModel;
import com.bsva.controller.Controller;
import com.bsva.models.WebOpsFileRegModel;
import com.bsva.models.WebOpsStatusDetailsModel;
import com.bsva.models.WebOpsStatusHdrsModel;
import com.bsva.models.WebServicesModel;
import com.bsva.models.WebSysCisBankModel;
import com.bsva.panels.CheckBoxColumn;
import com.bsva.panels.AC_viewErrorDetails.ViewErrorDetailsPanel;
import com.bsva.provider.IncomingFileMonitoringProvider;
import com.bsva.provider.ViewFileStatusIncomingProvider;
import com.bsva.translator.WebAdminTranslator;

/**
 * 
 * @author DimakatsoN
 *
 */
public class IncomingFileMonitoringPanel extends Panel implements Serializable
{
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(IncomingFileMonitoringPanel.class);
	private static String id;
	private Form<?> form;
	Controller controller;
	private Set<WebOpsFileRegModel> selected = new HashSet<WebOpsFileRegModel>();
	IncomingFileMonitoringPanel incomingFileMonitoringPanel;
	OutgoingFileMonitoringPanel outgoingFileMonitoringPanel;	
	ViewErrorDetailsPanel viewErrorDetailsPanel;
	private Button viewFileErrorsButton;
	private Button viewAllButton;
	private String action;
	private DropDownChoice<WebSysCisBankModel>  memberNo;
	private DropDownChoice<WebServicesModel> serviceIdIn;
	private WebSysCisBankModel webSysCisBankModel ;
	private WebServicesModel webServicesModel;
	private Button generateTableButton;
	private List<WebSysCisBankModel> instIdList = new ArrayList<WebSysCisBankModel>();
	private List<WebServicesModel> serviceIdInList = new ArrayList<WebServicesModel>();
	List<OpsStatusDetailsModel> msgDetailList = new ArrayList<OpsStatusDetailsModel>();
	List<OpsStatusHdrsModel> statusHdrsList = new ArrayList<OpsStatusHdrsModel>();
	OpsStatusHdrsModel opsStatusHdrsModel = new OpsStatusHdrsModel();
	OpsStatusDetailsModel opsStatusDetailsModel = new OpsStatusDetailsModel();
	WebOpsStatusHdrsModel webOpsStatusHdrsModel = new WebOpsStatusHdrsModel();
	List<WebOpsStatusHdrsModel> webStatusHdrsList = new ArrayList<WebOpsStatusHdrsModel>();
	List<WebOpsStatusDetailsModel> groupHdrsErrorsList = new ArrayList<WebOpsStatusDetailsModel>();
	List<WebOpsStatusDetailsModel> groupTransErrorsList = new ArrayList<WebOpsStatusDetailsModel>();
	WebOpsFileRegModel webOpsFileRegModel = new WebOpsFileRegModel();
	private DefaultDataTable incomingDataTable;
	SystemParameterModel mdtSysCtrlSysParamModel;
	String destMemberId = null;
	String serviceName = null;
	String fileName;
	String systemType;
	private DefaultDataTable inconingDataTable;
	private List<IColumn> columns;

	public IncomingFileMonitoringPanel(String id) 
	{
		super(id);
		this.id=id;
		intializeComponenets();
	}
	
	private void intializeComponenets()
	{
		columns = new ArrayList<IColumn>();
		form = new Form("form");
		controller = new Controller();
		loadData();
		memberNo = new DropDownChoice<WebSysCisBankModel>("memberNo",new Model<WebSysCisBankModel>(), instIdList,new ChoiceRenderer<WebSysCisBankModel>());
		memberNo.setRequired(true);
		if (action == "create")
		{
			if(instIdList != null && instIdList.size() > 0)
			{
				memberNo.setDefaultModelObject(memberNo.getChoices().get(0).getMemberNo());
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
			memberNo.setModelObject(memberNo.getChoices().get(sel));
		}
		form.add(memberNo);
		serviceIdIn = new DropDownChoice<WebServicesModel>("serviceIdIn",new Model<WebServicesModel>(), serviceIdInList,new ChoiceRenderer<WebServicesModel>());
		serviceIdIn.setRequired(false);
		if (action == "create")
		{
			if(serviceIdInList != null && serviceIdInList.size() > 0)
			{
				serviceIdIn.setDefaultModelObject(serviceIdIn.getChoices().get(0).getServiceIdIn());
			}
		}
		else if (action == "update") 
		{
			int sel = 0;
			for (int index = 0; index < serviceIdInList.size(); index++) 
			{
				if (webServicesModel.getServiceIdIn().equalsIgnoreCase(serviceIdInList.get(index).getServiceIdIn())) 
				{
					sel = index;
				}
			}
			serviceIdIn.setModelObject(serviceIdIn.getChoices().get(sel));
		}
		form.add(serviceIdIn);
		add(form);

		generateTableButton = new Button("generateTableButton")
		{
			private static final long serialVersionUID = 1L;
			
			public void onSubmit()
			{
				try
				{
					if((memberNo.getValue()== null ||memberNo.getValue().isEmpty()) && (serviceIdIn.getValue() == null || serviceIdIn.getValue().isEmpty()))
					{
						error("Please select Member No and Service ID to view transactions"); 
						form.add(createIncomingDataTable(new IncomingFileMonitoringProvider()));
					}
					if((memberNo.getValue() != null) && (serviceIdIn.getValue() == null || serviceIdIn.getValue().isEmpty()))
					{
						error("Please select Membe No and Service ID to view transactions");
					}
					else
					{
						String  memberId = memberNo.getChoices().get(Integer.parseInt(memberNo.getValue())).getMemberNo();
						String  serviceId = serviceIdIn.getChoices().get(Integer.parseInt(serviceIdIn.getValue())).getServiceIdIn();
						form.add(createIncomingDataTable(new IncomingFileMonitoringProvider(memberId, serviceId)));
					}
				}
				catch (Exception e)
				{
					log.error("Error onSubmit View Trasactions: " + e.getMessage());
					e.printStackTrace();
				}
			}
		};
		form.add(generateTableButton);

		viewFileErrorsButton = new Button("viewFileErrorsButton")
		{
			private static final long serialVersionUID = 1L;
				
			@Override
			public void onSubmit() 
			{
				if (selected.size() > 1) 
				{
					error("Please select only one record...");
				} 
				else if (selected.size() <= 0)
				{
					error("No record was selected...");
				}
				else 
				{
					for (WebOpsFileRegModel localModel : selected) 
					{
						String grpHdrMsgId = localModel.getGrpHdrMsgId();
						String fileName = localModel.getFileName();	
						WebOpsStatusHdrsModel weblocalModel =  new WebOpsStatusHdrsModel();
						WebOpsStatusDetailsModel webStatusModel;
						List<WebOpsStatusDetailsModel> webOpsStatusDetailsList = new ArrayList<WebOpsStatusDetailsModel>();
						List<WebOpsStatusHdrsModel> webOpsStatusHrdsList = new ArrayList<WebOpsStatusHdrsModel>();
						if(localModel.getStatus().equalsIgnoreCase("V") || localModel.getStatus().equalsIgnoreCase("FG") || localModel.getStatus().equalsIgnoreCase("LE") || localModel.getStatus().equalsIgnoreCase("LS"))
						{
							opsStatusHdrsModel = (OpsStatusHdrsModel) controller.retrieveOpsStatusHdrs(grpHdrMsgId);
							log.info("opsStatusHdrsModel********************************* " + opsStatusHdrsModel);
							weblocalModel =  new WebAdminTranslator().translateCommonsOpsStatusHdrsModelToWebOpsStatusHrdsModel(opsStatusHdrsModel);
							webOpsStatusHrdsList.add(weblocalModel);
							msgDetailList = (List<OpsStatusDetailsModel>) controller.retrieveOpsStatusDetails(weblocalModel.getSystemSeqNo());
							log.info("msgDetailList********************************* " + msgDetailList);
							if(msgDetailList != null && msgDetailList.size() > 0)
							{
							webOpsStatusDetailsList = new ArrayList<WebOpsStatusDetailsModel>();
							for (OpsStatusDetailsModel opsStatusDetailsModel : msgDetailList) 
							{
								webStatusModel = new WebOpsStatusDetailsModel();
								log.info("opsStatusDetailsModel********************************* " + opsStatusDetailsModel);
								webStatusModel= new WebAdminTranslator().translateCommonsOpsStatusDetailsModelToWebOpsStatusDetailsModel(opsStatusDetailsModel);
								webOpsStatusDetailsList.add(webStatusModel); 
								log.info("webStatusModel********************************* " + webStatusModel);	                                                                                                                                                                                                                                                                                                                                                                                                                                                                }
							}
							viewErrorDetailsPanel = new ViewErrorDetailsPanel(id,"VIEWERRORS",webOpsStatusHrdsList,webOpsStatusDetailsList);
							MarkupContainer markupContainer = form.getParent().getParent();
							if (markupContainer == null)
							{
								markupContainer=form.getParent();
							}
							markupContainer.remove(form.getParent());
							markupContainer.add(viewErrorDetailsPanel);
							viewErrorDetailsPanel.setOutputMarkupId(true);
							viewErrorDetailsPanel.setOutputMarkupPlaceholderTag(true);
//							  TemplatePage.setContent(viewErrorDetailsPanel);
							break;
						}
						else
						{
							info("File has no errors");
						}
					}
				}
			}
		};
			
			viewAllButton = new Button ("viewAllButton")
			{
				private static final long SerialVersionUID = 1L;
				
				@Override
				public void onSubmit()
				{
					viewAllButton.setEnabled(false);
					form.remove(form.get("dafaultTable"));
					form.add(createIncomingDataTable(new IncomingFileMonitoringProvider()));
				}
			};
			form.add(viewAllButton);
			incomingDataTable=(createIncomingDataTable(new IncomingFileMonitoringProvider()));
			incomingDataTable.setOutputMarkupId(true);
			form.add(incomingDataTable);		
			form.add(viewFileErrorsButton);
			viewFileErrorsButton.setDefaultFormProcessing(false);
			viewFileErrorsButton.setVisible(true);
			//Retrieve System Paramaters
			mdtSysCtrlSysParamModel = new SystemParameterModel();
			mdtSysCtrlSysParamModel = (SystemParameterModel) controller.retrieveWebActiveSysParameter();
			if(mdtSysCtrlSysParamModel != null)
			{
				systemType = mdtSysCtrlSysParamModel.getSysType();
			}
			add(form);
			add(new FeedbackPanel("feedback"));
	}
	
	private DefaultDataTable createIncomingDataTable(IncomingFileMonitoringProvider incomingFileMonitoringProvider)
	{
		DefaultDataTable dafaultTable;
		final List<IColumn> columns = new ArrayList<IColumn>();
		IColumn column = new CheckBoxColumn<WebOpsFileRegModel>(Model.of("Select")) 
		{ 
			private static final long serialVersionUID = 1L;

			@Override
            protected IModel newCheckBoxModel(final IModel<WebOpsFileRegModel> rowModel) 
            {
               return new AbstractCheckBoxModel() 
               {
            	   	private static final long serialVersionUID = 1L;

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
		columns.add(new PropertyColumn(new Model<String>("FileName"),"fileName", "fileName"));
		columns.add(new PropertyColumn(new Model<String>("Status"),"status","status"));
		columns.add(new PropertyColumn(new Model<String>("ProcessDate"),"processDate","processDate"));
		columns.add(new PropertyColumn(new Model<String>("Grp Hdr Msg ID"),"grpHdrMsgId","grpHdrMsgId"));
		dafaultTable = new DefaultDataTable("dafaultTable", columns, incomingFileMonitoringProvider, 20);
		return dafaultTable;
	}
	
	private void loadData() 
	{		
		// Generate InstId for drop down box 
		List<SysCisBankModel> instIdListFromDb = new ArrayList<SysCisBankModel>();
		instIdListFromDb = (List<SysCisBankModel>) controller.viewAllMember();
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
		/* Generate SeveritryCodes for drop down box */
		List<ServicesModel> serviceIdOutListFromDb = new ArrayList<ServicesModel>();
		serviceIdOutListFromDb = (List<ServicesModel>) controller.viewAllServices();
		Collections.sort(serviceIdOutListFromDb, new serviceIdOrderSorter());
		if (serviceIdOutListFromDb.size() > 0) 
		{
			for (ServicesModel servModel : serviceIdOutListFromDb) 
			{
				WebServicesModel webLocalModel = new WebServicesModel();
				webLocalModel = new WebAdminTranslator().translateCommonsServicesModelToWebModel(servModel);
				if(servModel.getServiceIdIn() == null)
				{
					serviceIdInList.remove(webLocalModel);
				}
				else
				{
					serviceIdInList.add(webLocalModel);	
				}
			}
		}// end of if	
	}
	// Sort ServiceId in ASCENDING order
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
	// Sort MemberName in ASCENDING order
	private class memberNameOrderSorter implements Comparator<SysCisBankModel>
	{
		@Override
		public int compare(SysCisBankModel o1, SysCisBankModel o2) 
		{
			if(o1.getMemberNo() == null && o2.getMemberNo() == null)
			{
				return 0;
			}
			else if(o1.getMemberNo() == null)
			{
				return -1;
			}
			else if(o2.getMemberNo() == null)
			{
				return 1;
			}
			return o1.getMemberNo().compareTo(o2.getMemberNo());	
		}
	}
}