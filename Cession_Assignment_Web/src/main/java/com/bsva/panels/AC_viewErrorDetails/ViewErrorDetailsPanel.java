package com.bsva.panels.AC_viewErrorDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

import com.bsva.TemplatePage;
import com.bsva.commons.model.OpsStatusDetailsModel;
import com.bsva.commons.model.OpsStatusHdrsModel;
import com.bsva.controller.Controller;
import com.bsva.interfaces.AdminBeanRemote;
import com.bsva.models.WebOpsStatusDetailsModel;
import com.bsva.models.WebOpsStatusHdrsModel;
import com.bsva.panels.AC_viewFileStatus.ViewFileStatusPanel;
import com.bsva.panels.AC_viewFileStatus.ViewFileStatusTabPanel;
import com.bsva.panels.SystemStatus.SystemStatusTabPanel;
import com.bsva.panels.accountType.MaintainAccountTypePanel;
import com.bsva.panels.viewFiles.ViewIncomingFiles;
import com.bsva.provider.OpsGrpHeaderProvider;
import com.bsva.provider.OpsStatusDetailsProvider;
import com.bsva.provider.ViewFileStatusIncomingProvider;
import com.bsva.provider.ViewFileStatusOutgoingProvider;


/***
 * 
 * @author DimakatsoN
 *
 */

public class ViewErrorDetailsPanel extends Panel implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static Logger log = Logger.getLogger(ViewErrorDetailsPanel.class);
	
	private static AdminBeanRemote adminBeanRemote;
	public static String id;
	
	String orgnlMsgId; 
	private String action;
	
	private Form<?> form;
	
	Controller controller;
	
	private Button cancelButton;
	
	private DefaultDataTable opsHeadersDataTable, opsStatusDetailDataTable;
	List<OpsStatusDetailsModel> msgDetailList = new ArrayList<OpsStatusDetailsModel>();
	List<OpsStatusHdrsModel> statusHdrsList = new ArrayList<OpsStatusHdrsModel>();
	OpsStatusHdrsModel opsStatusHdrsModel;
	List<WebOpsStatusHdrsModel> groupHdrsErrorsList;
	//List<WebOpsStatusDetailsModel> groupHdrsErrorsList = new ArrayList<WebOpsStatusDetailsModel>();
	List<WebOpsStatusDetailsModel> groupTransErrorsList;	
	ViewFileStatusPanel viewFileStatusPanel;

	
	public ViewErrorDetailsPanel(String id,String action,List<WebOpsStatusHdrsModel> groupHdrsErrorsList, List<WebOpsStatusDetailsModel> groupTransErrorsList) 
	{
		super(id);
		this.id = id;
		this.action = action;
		this.groupHdrsErrorsList = groupHdrsErrorsList;
		this.groupTransErrorsList = groupTransErrorsList;
		initializeComponents();

	}

	private void initializeComponents() {

		form = new Form("form");
	
		controller = new Controller();
		opsHeadersDataTable = createOpsHeadersDataTable(new OpsGrpHeaderProvider(groupHdrsErrorsList));
		opsStatusDetailDataTable = createOpsStatusDetailDataTable(new OpsStatusDetailsProvider(groupTransErrorsList));
		/*opsHeadersDataTable.setOutputMarkupId(true);
		opsStatusDetailDataTable.setOutputMarkupId(true);*/
		
		cancelButton = new Button("cancelButton")
		{
			@Override
			public  void onSubmit()
			{
				/*MarkupContainer markupContainer = form.getParent().getParent();
				markupContainer.remove(form.getParent());
				markupContainer.add(new ViewFileStatusPanel(ViewFileStatusPanel.id));*/
			
				viewFileStatusPanel = new ViewFileStatusPanel(id);
				viewFileStatusPanel.setOutputMarkupId(true);
				viewFileStatusPanel.setOutputMarkupPlaceholderTag(true);
				MarkupContainer markupContainer = form.getParent().getParent();
				markupContainer.remove(form.getParent());
				markupContainer.add(viewFileStatusPanel);
				//TemplatePage.setContent(viewFileStatusPanel);	
			
			}
		};
		

		opsHeadersDataTable.setOutputMarkupId(true);
		opsStatusDetailDataTable.setOutputMarkupId(true);
		form.add(cancelButton);
		form.add(opsHeadersDataTable);
		form.add(opsStatusDetailDataTable);
		add(form);
		add(new FeedbackPanel("feedback"));
		
	
		
		
	}
	
	
	private DefaultDataTable createOpsHeadersDataTable(OpsGrpHeaderProvider opsGrpHeaderProvider)
	{
		DefaultDataTable dataTable;
		
		List<IColumn> columns = new ArrayList<IColumn>();
		
		columns.add(new PropertyColumn(new Model<String>("Status Report Msg Id"),"hdrMsgId", "hdrMsgId"));
		columns.add(new PropertyColumn(new Model<String>("Incoming File Msg Id"),"orgnlMsgId","orgnlMsgId"));
		columns.add(new PropertyColumn(new Model<String>("Instructing Agent"),"instgAgt","instgAgt"));
		columns.add(new PropertyColumn(new Model<String>("Instructed Agent"),"instdAgt","instdAgt"));
		columns.add(new PropertyColumn(new Model<String>("Process Status"),"processStatus","processStatus"));
		
		dataTable = new DefaultDataTable("dataTable", columns, opsGrpHeaderProvider, 20);
		
		return dataTable;
		
	}
	
	
	
	private DefaultDataTable createOpsStatusDetailDataTable(OpsStatusDetailsProvider opsStatusDetailsProvider)
	{
		DefaultDataTable dataTable1;
		
		List<IColumn> columns = new ArrayList<IColumn>();
		
		
		columns.add(new PropertyColumn(new Model<String>("Transaction ID "),"txnId","txnId"));
		columns.add(new PropertyColumn(new Model<String>("Transaction Status"),"txnStatus", "txnStatus"));
		columns.add(new PropertyColumn(new Model<String>("Error Code"),"errorCode","errorCode"));
		//columns.add(new PropertyColumn(new Model<String>("Error Description"),"errorDescription","errorDescription"));
		columns.add(new PropertyColumn(new Model<String>("Error Type"),"errorType","errorType"));
		
		dataTable1 = new DefaultDataTable("dataTable1", columns, opsStatusDetailsProvider, 20);
		
		return dataTable1;
		
	}
}



