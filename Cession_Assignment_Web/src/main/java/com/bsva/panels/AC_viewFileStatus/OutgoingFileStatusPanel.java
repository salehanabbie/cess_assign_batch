package com.bsva.panels.AC_viewFileStatus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AbstractAjaxTimerBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.extensions.model.AbstractCheckBoxModel;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.time.Duration;
import org.apache.wicket.validation.validator.StringValidator;

import com.bsva.TemplatePage;
import com.bsva.commons.model.OpsFileRegModel;
import com.bsva.commons.model.OpsStatusDetailsModel;
import com.bsva.commons.model.OpsStatusHdrsModel;
import com.bsva.commons.model.ServicesModel;
import com.bsva.commons.model.SysCisBankModel;
import com.bsva.commons.model.SystemControlServicesModel;
import com.bsva.commons.model.SystemParameterModel;
import com.bsva.controller.Controller;
import com.bsva.interfaces.AdminBeanRemote;
import com.bsva.models.WebOpsAccptMsgDetailModel;
import com.bsva.models.WebOpsFileRegModel;
import com.bsva.models.WebOpsServicesModel;
import com.bsva.models.WebOpsStatusDetailsModel;
import com.bsva.models.WebOpsStatusHdrsModel;
import com.bsva.models.WebServicesModel;
import com.bsva.models.WebSysCisBankModel;
import com.bsva.models.WebSystemControlServicesModel;
import com.bsva.panels.CheckBoxColumn;
import com.bsva.panels.AC_viewErrorDetails.ViewErrorDetailsPanel;
import com.bsva.provider.ViewFileStatusIncomingProvider;
import com.bsva.provider.ViewFileStatusOutgoingProvider;
import com.bsva.provider.ViewIncomingFilesProvider;
import com.bsva.translator.WebAdminTranslator;
import com.bsva.validators.SimpleAttributeModifier;
import com.bsva.validators.TextFieldValidator;

/***
 * 
 * @author DimakatsoN
 *
 */

public class OutgoingFileStatusPanel extends Panel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(ViewFileStatusPanel.class);

	private static AdminBeanRemote adminBeanRemote;
	public static String id;
	
	private Form<?> form;
	
	Controller controller;
	
	private Set<WebOpsFileRegModel> selected  = new HashSet<WebOpsFileRegModel>();
	
	private  List<WebOpsFileRegModel> webOpsFileReglList;
	
	private  List<OpsFileRegModel> opsFileRegList;
	ViewFileStatusPanel viewFileStatusPanel;
	ViewErrorDetailsPanel viewErrorDetailsPanel;
	OutgoingFileStatusPanel outgoingFileStatusPanel;
	
	private DefaultDataTable incomingDataTable, outgoingDataTable;
	private ViewFileStatusIncomingProvider viewFileStatusProvider;
	
	private Button viewFileErrorsButton;
	private Button backButton;
	private String action;
	private Button viewAllButton;
	
	private DropDownChoice<WebSysCisBankModel>  memberNo;
	private WebSysCisBankModel webSysCisBankModel;
	private Button generateTableButton;
	private List<WebSysCisBankModel> instIdList = new ArrayList<WebSysCisBankModel>();

	
	List<OpsStatusDetailsModel> msgDetailList = new ArrayList<OpsStatusDetailsModel>();
	List<OpsStatusHdrsModel> statusHdrsList = new ArrayList<OpsStatusHdrsModel>();
	OpsStatusHdrsModel opsStatusHdrsModel = new OpsStatusHdrsModel();
	OpsStatusDetailsModel opsStatusDetailsModel = new OpsStatusDetailsModel();
	WebOpsStatusHdrsModel webOpsStatusHdrsModel = new WebOpsStatusHdrsModel();
	List<WebOpsStatusHdrsModel> webStatusHdrsList = new ArrayList<WebOpsStatusHdrsModel>();
	List<WebOpsStatusDetailsModel> groupHdrsErrorsList = new ArrayList<WebOpsStatusDetailsModel>();
	List<WebOpsStatusDetailsModel> groupTransErrorsList = new ArrayList<WebOpsStatusDetailsModel>();
	

	private WebSystemControlServicesModel webOpsServicesModel;
	private List<WebSystemControlServicesModel> serviceIdOutList  = new ArrayList<WebSystemControlServicesModel>();
	private DropDownChoice<WebSystemControlServicesModel> serviceIdOut;

	
	

	SystemParameterModel mdtSysCtrlSysParamModel;

	String systemType;
	private String sadcSystem = "SADC";
	
	public OutgoingFileStatusPanel(String id) 
	{
		super(id);
		this.id = id;
		initializeComponents();

	}
	
	public OutgoingFileStatusPanel(String id, WebSysCisBankModel webSysCisBankModel,WebSystemControlServicesModel webOpsServicesModel,String action )
	{
		super(id);
		this.action = action;
		this.id = id;
		this.webOpsServicesModel = webOpsServicesModel;
		this.webSysCisBankModel = webSysCisBankModel;
		initializeComponents();
		log.debug("Screen ID: " + action);

	}

	private void initializeComponents() 
	{
		
		form = new Form("form");

		controller = new Controller();
		loadData();
		

			
	
		memberNo = new DropDownChoice<WebSysCisBankModel>("memberNo",new Model<WebSysCisBankModel>(), instIdList,new ChoiceRenderer<WebSysCisBankModel>());
		memberNo.setRequired(false);
		if(instIdList != null && instIdList.size() > 0)
		{
			memberNo.setDefaultModelObject(memberNo.getChoices().get(0).getMemberNo());
		}

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

				if (webSysCisBankModel.getMemberNo().equalsIgnoreCase(
						instIdList.get(index).getMemberNo()))
				{
					sel = index;
				}

			}
			memberNo.setModelObject(memberNo.getChoices().get(sel));
		}

		form.add(memberNo);

		serviceIdOut = new DropDownChoice<WebSystemControlServicesModel>("serviceIdOut",new Model<WebSystemControlServicesModel>(), serviceIdOutList,new ChoiceRenderer<WebSystemControlServicesModel>());
		serviceIdOut.setRequired(false);
		if(serviceIdOutList != null && serviceIdOutList.size() > 0)
		{
			serviceIdOut.setDefaultModelObject(serviceIdOut.getChoices().get(0).getServiceIdOut());
		}

		if (action == "create")
		{
			if(serviceIdOutList != null && serviceIdOutList.size() > 0)
			{
				serviceIdOut.setDefaultModelObject(serviceIdOut.getChoices().get(0).getServiceIdOut());
			}
		}
		else if (action == "update") 
		{
			int sel = 0;
			for (int index = 0; index < serviceIdOutList.size(); index++) 
			{

				if (webOpsServicesModel.getServiceIdOut().equalsIgnoreCase(serviceIdOutList.get(index).getServiceIdOut())) 
				{
					sel = index;
				}

		}

			serviceIdOut.setModelObject(serviceIdOut.getChoices().get(sel));

		}

		form.add(serviceIdOut);


		
		generateTableButton = new Button("generateTableButton") 
		{
			private static final long serialVersionUID = 1L;

			public void onSubmit() 
			{							

			
				try{
					
					if((memberNo.getValue() == null ||memberNo.getValue().isEmpty()) && (serviceIdOut.getValue() == null || serviceIdOut.getValue().isEmpty()))
					{
						error("Please select MemberNo and ServiceID to view transactions"); 
						form.remove(form.get("dafaultTable1"));
						form.add(createOutgoingDataTable(new ViewFileStatusOutgoingProvider()));
					}
					
					if((memberNo.getValue() != null) && (serviceIdOut.getValue() == null || serviceIdOut.getValue().isEmpty()))
					{
						error("Please select MemberNo and ServiceID to view transactions");
					}
					else
					{
						
						String  memberId = memberNo.getChoices().get(Integer.parseInt(memberNo.getValue())).getMemberNo();
						String  serviceId = serviceIdOut.getChoices().get(Integer.parseInt(serviceIdOut.getValue())).getServiceIdOut();
						
						
						form.remove(form.get("dafaultTable1"));
						form.add(createOutgoingDataTable(new ViewFileStatusOutgoingProvider(memberId, serviceId)));

					}
					}
					catch (Exception e) {
					log.error("Error onSubmit View Trasactions: " + e.getMessage());
					e.printStackTrace();
				}

			}


		};


		form.add(generateTableButton);

		viewAllButton = new Button ("viewAllButton")
		{
			private static final long SerialVersionUID = 1L;
			@Override
			
			public void onSubmit()
			{
				viewAllButton.setEnabled(false);
				
				form.remove(form.get("dafaultTable1"));
				form.add(createOutgoingDataTable(new ViewFileStatusOutgoingProvider()));
			}
		};
		form.add(viewAllButton);
		

		/*backButton = new Button("backButton")
		{
			@Override
			public  void onSubmit()
			{

				
				outgoingFileStatusPanel = new OutgoingFileStatusPanel(id);
				outgoingFileStatusPanel.setOutputMarkupId(true);
				outgoingFileStatusPanel.setOutputMarkupPlaceholderTag(true);
				MarkupContainer markupContainer = form .getParent().getParent();
				markupContainer.remove(form.getParent());
				markupContainer.add(outgoingFileStatusPanel);
				TemplatePage.setContent(outgoingFileStatusPanel);
			}
		};
		form.add(backButton);*/
		
		
		
	
			
		outgoingDataTable=(createOutgoingDataTable(new ViewFileStatusOutgoingProvider()));
		outgoingDataTable.setOutputMarkupId(true);
		form.add(outgoingDataTable);
		add(form);
		add(new FeedbackPanel("feedback"));
	

}
	
		public void populateFileStatusInformation(String grpHdrMsgId, String fileName)
		{
			System.out.println("grpHdrMsgId: "+grpHdrMsgId + "       fileName: "+fileName);
			OpsStatusHdrsModel opsStatusHdrsModel = new OpsStatusHdrsModel();
			WebOpsStatusHdrsModel webOpsStatusHdrsModel = new WebOpsStatusHdrsModel();
			
			opsStatusHdrsModel = (OpsStatusHdrsModel) controller.retrieveOpsStatusHdrs(grpHdrMsgId);

			List<WebOpsAccptMsgDetailModel> webAccptGrpHdrList = new ArrayList<WebOpsAccptMsgDetailModel>();
			List<WebOpsAccptMsgDetailModel> webAccptMandateList = new ArrayList<WebOpsAccptMsgDetailModel>();
			
			WebOpsAccptMsgDetailModel localWebModel;

		}

		private DefaultDataTable createOutgoingDataTable(ViewFileStatusOutgoingProvider viewFileStatusOutgoingProvider)
		{
			DefaultDataTable dafaultTable1;
			
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
			//columns.add(column);
			
			columns.add(new PropertyColumn(new Model<String>("FileName"),"fileName", "fileName"));
			columns.add(new PropertyColumn(new Model<String>("Status"),"status","status"));
			columns.add(new PropertyColumn(new Model<String>("ProcessDate"),"processDate","processDate"));
			columns.add(new PropertyColumn(new Model<String>("Extract Msg ID"),"extractMsgId","extractMsgId"));
			dafaultTable1 = new DefaultDataTable("dafaultTable1", columns, viewFileStatusOutgoingProvider, 20);

  
			add(new AbstractAjaxTimerBehavior(Duration.seconds(10)) 
			{

				private static final long serialVersionUID = 1L;

				@Override
				protected void onTimer(AjaxRequestTarget target) 
				{
					ViewFileStatusOutgoingProvider  viewFileStatusOutgoingProvider = new ViewFileStatusOutgoingProvider();

					DefaultDataTable dafaultTable1 = new DefaultDataTable("dafaultTable1",columns, viewFileStatusOutgoingProvider, 5);
					dafaultTable1.setOutputMarkupId(true);
					dafaultTable1.setOutputMarkupPlaceholderTag(true);
					target.add(dafaultTable1);
				}


			});

			return dafaultTable1;
			
		}
		
	
//		public List<OpsFileRegModel> retrieveOpsFileReg(String grpHdrMsgId)
//		{
//			List<OpsFileRegModel> opsFileRegList = new ArrayList<OpsFileRegModel>();
//			opsFileRegList = (List<OpsFileRegModel>)controller.retrieveMsgDetailByCriteria("MdtOpsAccptMsgDetailEntity.findByOrigMessageId", "origMessageId",grpHdrMsgId);
//			System.out.println("grpHdrMsgId List===>"+opsFileRegList.toString());
//			return opsFileRegList;
//
//		}

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
		
		// Sort ServiceId in ASCENDING order
			private class serviceIdOrderSorter implements Comparator<SystemControlServicesModel>
			{

				@Override
				public int compare(SystemControlServicesModel o1, SystemControlServicesModel o2) {
				
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
			public int compare(SysCisBankModel o1, SysCisBankModel o2) {
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
