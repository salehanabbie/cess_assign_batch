package com.bsva.panels.fileMonitoring;

import com.bsva.commons.model.OpsStatusDetailsModel;
import com.bsva.commons.model.OpsStatusHdrsModel;
import com.bsva.commons.model.SysCisBankModel;
import com.bsva.commons.model.SystemControlServicesModel;
import com.bsva.controller.Controller;
import com.bsva.models.WebOpsFileRegModel;
import com.bsva.models.WebOpsStatusDetailsModel;
import com.bsva.models.WebOpsStatusHdrsModel;
import com.bsva.models.WebSysCisBankModel;
import com.bsva.models.WebSystemControlServicesModel;
import com.bsva.panels.CheckBoxColumn;
import com.bsva.provider.OutgoingFileMonitoringProvider;
import com.bsva.translator.WebAdminTranslator;
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
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 * 
 * @author DimakatsoN
 *
 */
public class OutgoingFileMonitoringPanel extends Panel implements Serializable 
{
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(OutgoingFileMonitoringPanel.class);
	public static String id;
	private Form<?> form;
	Controller controller;
	private Set<WebOpsFileRegModel> selected  = new HashSet<WebOpsFileRegModel>();
	IncomingFileMonitoringPanel incomingFileMonitoringPanel;
	private String action;
	private Button viewAllButton;
	private DropDownChoice<WebSysCisBankModel>  memberNo;
	private WebSysCisBankModel webSysCisBankModel;
	private Button generateTableButton;
	private List<WebSysCisBankModel> instIdList = new ArrayList<WebSysCisBankModel>();
	private WebSystemControlServicesModel webOpsServicesModel;
	private List<WebSystemControlServicesModel> serviceIdOutList  = new ArrayList<WebSystemControlServicesModel>();
	private DropDownChoice<WebSystemControlServicesModel> serviceIdOut;
	private DefaultDataTable  outgoingDataTable;
	List<OpsStatusDetailsModel> msgDetailList = new ArrayList<OpsStatusDetailsModel>();
	List<OpsStatusHdrsModel> statusHdrsList = new ArrayList<OpsStatusHdrsModel>();
	OpsStatusHdrsModel opsStatusHdrsModel = new OpsStatusHdrsModel();
	OpsStatusDetailsModel opsStatusDetailsModel = new OpsStatusDetailsModel();
	WebOpsStatusHdrsModel webOpsStatusHdrsModel = new WebOpsStatusHdrsModel();
	List<WebOpsStatusHdrsModel> webStatusHdrsList = new ArrayList<WebOpsStatusHdrsModel>();
	List<WebOpsStatusDetailsModel> groupHdrsErrorsList = new ArrayList<WebOpsStatusDetailsModel>();
	List<WebOpsStatusDetailsModel> groupTransErrorsList = new ArrayList<WebOpsStatusDetailsModel>();
	
	public OutgoingFileMonitoringPanel(String id) 
	{
		super(id);
		this.id = id;
		initializeComponents();
	}
	
	public OutgoingFileMonitoringPanel(String id, WebSysCisBankModel webSysCisBankModel,WebSystemControlServicesModel webOpsServicesModel,String action )
	{
		super(id);
		this.action = action;
		this.id = id;
		this.webOpsServicesModel = webOpsServicesModel;
		this.webSysCisBankModel = webSysCisBankModel;
		initializeComponents();
		log.debug("Screen ID: " + action);
	}
	
	public void initializeComponents()
	{
		form = new Form("form");
		controller = new Controller();
		loadData();
		memberNo = new DropDownChoice<WebSysCisBankModel>("memberNo",new Model<WebSysCisBankModel>(), instIdList,new ChoiceRenderer<WebSysCisBankModel>());
		memberNo.setRequired(false);
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

		serviceIdOut = new DropDownChoice<WebSystemControlServicesModel>("serviceIdOut",new Model<WebSystemControlServicesModel>(), serviceIdOutList,new ChoiceRenderer<WebSystemControlServicesModel>());
		serviceIdOut.setRequired(false);
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
				try
				{
				if((memberNo.getValue() == null ||memberNo.getValue().isEmpty()) && (serviceIdOut.getValue() == null || serviceIdOut.getValue().isEmpty()))
				{
					error("Please select MemberNo and ServiceID to view transactions"); 
					form.add(createOutgoingDataTable(new OutgoingFileMonitoringProvider()));
				}
				if((memberNo.getValue() != null) && (serviceIdOut.getValue() == null || serviceIdOut.getValue().isEmpty()))
				{
					error("Please select MemberNo and ServiceID to view transactions");
				}
				else
				{
					String  memberId = memberNo.getChoices().get(Integer.parseInt(memberNo.getValue())).getMemberNo();
					String  serviceId = serviceIdOut.getChoices().get(Integer.parseInt(serviceIdOut.getValue())).getServiceIdOut();
					form.add(createOutgoingDataTable(new OutgoingFileMonitoringProvider(memberId, serviceId)));
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

	viewAllButton = new Button ("viewAllButton")
	{
		private static final long SerialVersionUID = 1L;
		
		@Override
		public void onSubmit()
		{
			viewAllButton.setEnabled(false);
			form.remove(form.get("dafaultTable1"));
			form.add(createOutgoingDataTable(new OutgoingFileMonitoringProvider()));
		}
	};
	form.add(viewAllButton);
	
	outgoingDataTable=(createOutgoingDataTable(new OutgoingFileMonitoringProvider()));
	outgoingDataTable.setOutputMarkupId(true);
	form.add(outgoingDataTable);
	add(form);
	add(new FeedbackPanel("feedback"));
	}
	
	private DefaultDataTable createOutgoingDataTable(OutgoingFileMonitoringProvider outgoingFileMonitoringProvider)
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
		columns.add(new PropertyColumn(new Model<String>("FileName"),"fileName", "fileName"));
		columns.add(new PropertyColumn(new Model<String>("Status"),"status","status"));
		columns.add(new PropertyColumn(new Model<String>("ProcessDate"),"processDate","processDate"));
		columns.add(new PropertyColumn(new Model<String>("Extract Msg ID"),"extractMsgId","extractMsgId"));
		dafaultTable1 = new DefaultDataTable("dafaultTable1", columns, outgoingFileMonitoringProvider, 20);
		return dafaultTable1;
	}
	
//	public List<OpsFileRegModel> retrieveOpsFileReg(String grpHdrMsgId)
//	{
//		List<OpsFileRegModel> opsFileRegList = new ArrayList<OpsFileRegModel>();
//		opsFileRegList = (List<OpsFileRegModel>)controller.retrieveMsgDetailByCriteria("MdtOpsAccptMsgDetailEntity.findByOrigMessageId", "origMessageId",grpHdrMsgId);
//		System.out.println("grpHdrMsgId List===>"+opsFileRegList.toString());
//		return opsFileRegList;
//	}

	private void loadData() 
	{	
		List<SysCisBankModel> instIdListFromDb = new ArrayList<SysCisBankModel>();
		instIdListFromDb = (List<SysCisBankModel>) controller.viewAllMember();
		Collections.sort(instIdListFromDb, new memberNameOrderSorter());
		if (instIdListFromDb.size() > 0) 
		{
			for (SysCisBankModel localCommonModel : instIdListFromDb) 
			{
				WebSysCisBankModel webModel = new WebSysCisBankModel();
				webModel = new WebAdminTranslator().translateCommonsSysCisBankModelToWebModel(localCommonModel);           
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