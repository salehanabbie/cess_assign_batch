package com.bsva.panels.SystemStatus;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.log4j.Logger;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.Session;
import org.apache.wicket.ajax.AbstractAjaxTimerBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.AjaxLazyLoadPanel;
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
import org.apache.wicket.util.time.Duration;
import com.bsva.commons.model.AcOpsTransCtrlMsgModel;
import com.bsva.commons.model.MandatesCountCommonsModel;
import com.bsva.commons.model.OpsStatusDetailsModel;
import com.bsva.commons.model.OpsStatusHdrsModel;
import com.bsva.commons.model.OutBalanceCountReportModel;
import com.bsva.commons.model.ServicesModel;
import com.bsva.commons.model.SysCisBankModel;
import com.bsva.commons.model.SystemParameterModel;
import com.bsva.controller.Controller;
import com.bsva.models.ClientSessionModel;
import com.bsva.models.WebMandateCountModel;
import com.bsva.models.WebOpsFileRegModel;
import com.bsva.models.WebOpsStatusDetailsModel;
import com.bsva.models.WebOpsStatusHdrsModel;
import com.bsva.models.WebServicesModel;
import com.bsva.models.WebSysCisBankModel;
import com.bsva.models.WebSystemParameterModel;
import com.bsva.panels.CheckBoxColumn;
import com.bsva.panels.AC_viewErrorDetails.ViewErrorDetailsPanel;
import com.bsva.provider.ViewFileStatusProcessIncomingProvider;
import com.bsva.translator.WebAdminTranslator;

public class IncomingSystemStatusPanel extends Panel implements Serializable
{
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(IncomingSystemStatusPanel.class);
	private static String id;
	private Form<?> form;
	Controller controller;
	private Set<WebMandateCountModel> selected = new HashSet<WebMandateCountModel>();
	WebMandateCountModel webMandateCountModel;
	List<MandatesCountCommonsModel> mandatesCountListIncomingMessages = new ArrayList<MandatesCountCommonsModel>();
	List<MandatesCountCommonsModel> mandatesCountListOutGoingMessages = new ArrayList<MandatesCountCommonsModel>();
	List<MandatesCountCommonsModel> mandatesTotalMessagesList = new ArrayList<MandatesCountCommonsModel>();
	List<OutBalanceCountReportModel> rejectedFilesCountList = new ArrayList<OutBalanceCountReportModel>();
	List<AcOpsTransCtrlMsgModel> acOpsTransCtrlMsgModelList = new ArrayList<AcOpsTransCtrlMsgModel>();
	IncomingSystemStatusPanel incomingSystemStatusPanel;
	OutgoingSystemStatusPanel outgoingSystemStatusPanel;
	private DefaultDataTable   incomingDataTable;
	private AjaxLazyLoadPanel lazy;
	SystemParameterModel systemParameterModel = new SystemParameterModel();
	WebSystemParameterModel websystemParametersModel =new WebSystemParameterModel();
	private List<IColumn> columns;
	private ClientSessionModel clientSessionModel;
	public static Session session;
	private DropDownChoice<WebSysCisBankModel>  memberNo;
	private DropDownChoice<WebServicesModel> serviceIdIn;
	private WebSysCisBankModel webSysCisBankModel ;
	private WebServicesModel webServicesModel;
	private String action;
	private List<WebSysCisBankModel> instIdList = new ArrayList<WebSysCisBankModel>();
	private List<WebServicesModel> serviceIdInList = new ArrayList<WebServicesModel>();
	private Button generateTableButton;
	private Button viewAllButton;
	
	public IncomingSystemStatusPanel(String id)
	{
		super(id);
		this.id=id;
		initializeComponents();
	}

	private void initializeComponents()
	{
		controller = new Controller();
		session = getSession();
		clientSessionModel = (ClientSessionModel) session.getAttribute("role");
		loadData();
		columns = new ArrayList<IColumn>();
		form = new Form("form");
		mandatesCountListIncomingMessages = (List<MandatesCountCommonsModel>) controller.viewMandatesCountIncomingMessages();
		memberNo = new DropDownChoice<WebSysCisBankModel>("memberNo",new Model<WebSysCisBankModel>(), instIdList,new ChoiceRenderer<WebSysCisBankModel>());
		if (action == "create")
		{
			if(instIdList != null && instIdList.size() > 0)
			{
				memberNo.setDefaultModelObject(memberNo.getChoices().get(0).getMemberNo());
			}
		}
//		else if (action == "update")
//		{
//			int sel = 0;
//			for (int index = 0; index < instIdList.size(); index++)
//			{
//
//				if (webSysCisBankModel.getMemberNo().equalsIgnoreCase(instIdList.get(index).getMemberNo()))
//				{
//					sel = index;
//				}
//			}
//			memberNo.setModelObject(memberNo.getChoices().get(sel));
//		}
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
		form.add(serviceIdIn);
//		else if (action == "update") 
//		{
//			int sel = 0;
//			for (int index = 0; index < serviceIdInList.size(); index++) 
//			{
//				if (webServicesModel.getServiceIdIn().equalsIgnoreCase(serviceIdInList.get(index).getServiceIdIn())) 
//				{
//					sel = index;
//				}
//			}
//			serviceIdIn.setModelObject(serviceIdIn.getChoices().get(sel));
//		}
		
//		log.info("mandatesCountListIncomingMessages --->" + mandatesCountListIncomingMessages);
		systemParameterModel = (SystemParameterModel) controller.retrieveWebActiveSysParameter();
		if(systemParameterModel != null)
		{
			websystemParametersModel = new WebSystemParameterModel();
			websystemParametersModel = new WebAdminTranslator().translateCommonsSystemParametersModelToWebModel(systemParameterModel);
			log.debug("websystemParametersModel: "+ websystemParametersModel);
		}
		form.add(serviceIdIn);
		add(form);
		form.add(createIncomingDataTable(new ViewFileStatusProcessIncomingProvider(id)));
		form.add(incomingDataTable);
		add(new FeedbackPanel("feedback"));
		
		viewAllButton = new Button ("viewAllButton")
		{
			private static final long SerialVersionUID = 1L;
			
			@Override
			public void onSubmit()
			{
				viewAllButton.setEnabled(false);
				form.remove(form.get("incomingDataTableStatus"));
				form.add(createIncomingDataTable(new ViewFileStatusProcessIncomingProvider()));
			}
		};
		viewAllButton.setEnabled(false);
		form.add(viewAllButton);
		
		generateTableButton = new Button("generateTableButton")
		{
			private static final long serialVersionUID = 1L;
			
			public void onSubmit()
			{
				memberNo.setRequired(true);
				viewAllButton.setEnabled(true);
				try
				{
					if((memberNo.getValue()== null ||memberNo.getValue().isEmpty()) && (serviceIdIn.getValue() == null || serviceIdIn.getValue().isEmpty()))
					{
						error("Please select Member No and Service ID to view transactions"); 
						form.remove(form.get("incomingDataTableStatus"));
						form.add(createIncomingDataTable(new ViewFileStatusProcessIncomingProvider()));
					}
					if((memberNo.getValue() != null) && (serviceIdIn.getValue() == null || serviceIdIn.getValue().isEmpty()))
					{
						error("Please select Membe No and Service ID to view transactions");
					}
					else
					{
						String  memberId = memberNo.getChoices().get(Integer.parseInt(memberNo.getValue())).getMemberNo();
						String  serviceId = serviceIdIn.getChoices().get(Integer.parseInt(serviceIdIn.getValue())).getServiceIdIn();
						log.info("Member Id --->" + memberId);
						log.info("Service Id---->" + serviceId);
						form.remove(form.get("incomingDataTableStatus"));
						form.add(createIncomingDataTable(new ViewFileStatusProcessIncomingProvider(memberId, serviceId)));
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
//		viewFileErrorsButton = new Button("viewFileErrorsButton")
//		{
//			private static final long serialVersionUID = 1L;
//				
//			@Override
//			public void onSubmit() 
//			{
//				if (selected.size() > 1) 
//				{
//					error("Please select only one record...");
//				} 
//				else if (selected.size() <= 0)
//				{
//					error("No record was selected...");
//				}
//				else 
//				{
//					for (WebOpsFileRegModel localModel : selected) 
//					{
//						String grpHdrMsgId = localModel.getGrpHdrMsgId();
//						String fileName = localModel.getFileName();	
//						WebOpsStatusHdrsModel weblocalModel =  new WebOpsStatusHdrsModel();
//						WebOpsStatusDetailsModel webStatusModel;
//						List<WebOpsStatusDetailsModel> webOpsStatusDetailsList = new ArrayList<WebOpsStatusDetailsModel>();
//						List<WebOpsStatusHdrsModel> webOpsStatusHrdsList = new ArrayList<WebOpsStatusHdrsModel>();
//						if(localModel.getStatus().equalsIgnoreCase("V") || localModel.getStatus().equalsIgnoreCase("FG") || localModel.getStatus().equalsIgnoreCase("LE") || localModel.getStatus().equalsIgnoreCase("LS"))
//						{
//							opsStatusHdrsModel = (OpsStatusHdrsModel) controller.retrieveOpsStatusHdrs(grpHdrMsgId);
//							log.info("opsStatusHdrsModel********************************* " + opsStatusHdrsModel);
//							weblocalModel =  new WebAdminTranslator().translateCommonsOpsStatusHdrsModelToWebOpsStatusHrdsModel(opsStatusHdrsModel);
//							webOpsStatusHrdsList.add(weblocalModel);
//							msgDetailList = (List<OpsStatusDetailsModel>) controller.retrieveOpsStatusDetails(weblocalModel.getSystemSeqNo());
//							log.info("msgDetailList********************************* " + msgDetailList);
//							if(msgDetailList != null && msgDetailList.size() > 0)
//							{
//							webOpsStatusDetailsList = new ArrayList<WebOpsStatusDetailsModel>();
//							for (OpsStatusDetailsModel opsStatusDetailsModel : msgDetailList) 
//							{
//								webStatusModel = new WebOpsStatusDetailsModel();
//								log.info("opsStatusDetailsModel********************************* " + opsStatusDetailsModel);
//								webStatusModel= new WebAdminTranslator().translateCommonsOpsStatusDetailsModelToWebOpsStatusDetailsModel(opsStatusDetailsModel);
//								webOpsStatusDetailsList.add(webStatusModel); 
//								log.info("webStatusModel********************************* " + webStatusModel);	                                                                                                                                                                                                                                                                                                                                                                                                                                                                }
//							}
//							viewErrorDetailsPanel = new ViewErrorDetailsPanel(id,"VIEWERRORS",webOpsStatusHrdsList,webOpsStatusDetailsList);
//							MarkupContainer markupContainer = form.getParent().getParent();
//							if (markupContainer == null)
//							{
//								markupContainer=form.getParent();
//							}
//							markupContainer.remove(form.getParent());
//							markupContainer.add(viewErrorDetailsPanel);
//							viewErrorDetailsPanel.setOutputMarkupId(true);
//							viewErrorDetailsPanel.setOutputMarkupPlaceholderTag(true);
////							  TemplatePage.setContent(viewErrorDetailsPanel);
//							break;
//						}
//						else
//						{
//							info("File has no errors");
//						}
//					}
//				}
//			}
//		};
	}
	
	
	
	private DefaultDataTable createIncomingDataTable(ViewFileStatusProcessIncomingProvider viewFileStatusProcessIncomingProvider) 
	{
		DefaultDataTable dafaultTable;

		final List<IColumn> columns = new ArrayList<IColumn>();

		IColumn column = new CheckBoxColumn<WebMandateCountModel>(Model.of("Select")) 
				{

			private static final long serialVersionUID = 1L;

			@Override
			protected IModel newCheckBoxModel(final IModel<WebMandateCountModel> rowModel)
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
		//columns.add(column);
		columns.add(new PropertyColumn(new Model<String>("File Name"),"fileName", "fileName"));
		columns.add(new PropertyColumn(new Model<String>("From"), "instId","instId"));
		columns.add(new PropertyColumn(new Model<String>("Number of Mandates"),"nrOfMsgs", "nrOfMsgs"));
		columns.add(new PropertyColumn(new Model<String>("Accepted"),"nrMsgsAccepted", "nrMsgsAccepted"));
		columns.add(new PropertyColumn(new Model<String>("Rejected"),"nrMsgsRejected", "nrMsgsRejected"));
		incomingDataTable = new DefaultDataTable  ("incomingDataTableStatus",columns, viewFileStatusProcessIncomingProvider, 5); 
			
		
//		add(new AbstractAjaxTimerBehavior(Duration.seconds(10)) 
//		 {
//
//            private static final long serialVersionUID = 1L;
//     
//            @Override
//            protected void onTimer(AjaxRequestTarget target) 
//            {
//            	ViewFileStatusProcessIncomingProvider		viewFileStatusProcessIncomingProvider = new ViewFileStatusProcessIncomingProvider();
//				
//				DefaultDataTable dafaultTable = new DefaultDataTable("incomingDataTableStatus",columns, viewFileStatusProcessIncomingProvider, 5);
//				dafaultTable.setOutputMarkupId(true);
//				dafaultTable.setOutputMarkupPlaceholderTag(true);
//				incomingDataTable.replaceWith(dafaultTable);
//				incomingDataTable=dafaultTable;
//				target.add(incomingDataTable);
//				
//				System.out.println("in the timer-------------" + dafaultTable);
//            }
//
//		
//		 }); 		
		
		return incomingDataTable;

	}

	private void loadData() 
	{		
		// Generate InstId for drop down box 
		List<SysCisBankModel> instIdListFromDb = new ArrayList<SysCisBankModel>();
		instIdListFromDb = (List<SysCisBankModel>) controller.viewAllMember();
//		log.info("instIdListFromDb ===>" + instIdListFromDb);
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
//		log.info("serviceIdOutListFromDb ===>" + serviceIdOutListFromDb);
		Collections.sort(serviceIdOutListFromDb, new serviceIdOrderSorter());
		if (serviceIdOutListFromDb.size() > 0) 
		{
			for(ServicesModel servModel : serviceIdOutListFromDb) 
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
