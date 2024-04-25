package com.bsva.panels.sequenceTypes;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.Session;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.validation.validator.PatternValidator;
import org.apache.wicket.validation.validator.StringValidator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import com.bsva.TemplatePage;
import com.bsva.commons.model.AuditTrackingModel;
import com.bsva.commons.model.SequenceTypesModel;
import com.bsva.controller.Controller;
import com.bsva.models.ClientSessionModel;
import com.bsva.models.WebAuditTrackingModel;
import com.bsva.models.WebSequenceTypesModel;
import com.bsva.provider.SequenceTypesProvider;
import com.bsva.translator.WebAdminTranslator;
import com.bsva.validators.TextFieldValidator;

/**
 * 
 * @author DimakatsoN
 *
 */
public class MaintainSequenceTypesPanel extends Panel 
{
	private static final long serialVersionUID = 1L;
	public static Logger log = Logger.getLogger(MaintainSequenceTypesPanel.class);
	private Form form;
	private String screenName;
	private ClientSessionModel clientSessionModel;
	private String userName;
	public static Session session;
	public static String id;
	private Button saveOrUpdateButton;
	private Button cancelButton;
	private TextField<String> seqCodeField;
	private TextField<String> seqCodeDescription;
	Label seqTypesCodeLabel,seqTypeDescLabel,activeIndLabel, legendLbl;
	private DropDownChoice<String> activeInd;
	private String selectedIndicator;
	private String action;
	ViewSequenceTypesPanel viewsequencepanel;
	private WebSequenceTypesModel webSequenceTypesModel,originaWebSequenceTypesModel;
	SequenceTypesProvider sequenceTypesProvider;
	WebAuditTrackingModel webAuditTrackingModel;
	String textValidator = "([a-zA-Z0-9 \\-._()]+)";
	Controller controller;
	
	public MaintainSequenceTypesPanel(String id, String action) 
	{
		super(id);
		this.action = action;
		MaintainSequenceTypesPanel.id=id;
		initializeComponents();
	}

	public MaintainSequenceTypesPanel(String id, WebSequenceTypesModel webSequenceTypesModel, String action)
	{
		super(id);
		this.action = action;
		MaintainSequenceTypesPanel.id=id;
		this.webSequenceTypesModel = webSequenceTypesModel;
		this.originaWebSequenceTypesModel = webSequenceTypesModel;
		initializeComponents();
		log.debug("Screen ID:" + action);
	}
	
	private void initializeComponents()
	{
		controller = new Controller();
		screenName = controller.getProperty("MNTN.SCRNNAME");
		legendLbl = new Label("legend","Sequence Types  - "+action.toUpperCase());
		add(legendLbl);
		form =  new Form("maintainSequenceTypesPanel");
		
		seqTypesCodeLabel= new Label("label1","Sequence Types Code");
		form.add(seqTypesCodeLabel);
		
		seqTypeDescLabel = new Label("label2","Sequence Types Description");
		form.add(seqTypeDescLabel);
		
		activeIndLabel=new Label("label3","Active Indicator");
		form.add(activeIndLabel);
		
		seqCodeField = new TextField<String>("seqCodeField", new Model<String>(webSequenceTypesModel == null ? "" : webSequenceTypesModel.getSequenceCode()));
		seqCodeField.setRequired(true);
		seqCodeField.add(StringValidator.maximumLength(4));
		seqCodeField.add(new PatternValidator(textValidator));
		if(action == "update")
		{
			seqCodeField.setEnabled(false);
		}
		form.add(seqCodeField);	

		seqCodeDescription = new TextField<String>("seqCodeDescription", new Model<String>(webSequenceTypesModel == null ? "" : webSequenceTypesModel.getSequenceDescription()));
		seqCodeDescription.setRequired(true);
		seqCodeDescription.add(StringValidator.maximumLength(45));
		seqCodeDescription.add(new PatternValidator(textValidator));
		form.add(seqCodeDescription);
		
		List<String> indicators = new ArrayList<String>();
		indicators.add("ACTIVE");
		indicators.add("INACTIVE");
		if(webSequenceTypesModel !=null)
		{
			if(webSequenceTypesModel.getActiveInd() !=null && webSequenceTypesModel.getActiveInd().equals("Y"))
			{
				selectedIndicator = "ACTIVE";
			}
			else
			{
				selectedIndicator = "INACTIVE";
			}
		}
		else
		{
			selectedIndicator = "ACTIVE";
		}
		activeInd = new DropDownChoice<String>("activeInd", new Model<String>(selectedIndicator), indicators);
		activeInd.setRequired(true);
		if(action.equalsIgnoreCase("create"))
		{
			activeInd.setEnabled(false);
		}
		form.add(activeInd);
		
		saveOrUpdateButton = new Button("saveButton")
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit()
			{ 
				try
				{
					boolean results = false;
					boolean saved =false;
					WebSequenceTypesModel webSeqTypesModel = null;
					webAuditTrackingModel = new WebAuditTrackingModel();
					session = getSession();
			        clientSessionModel = (ClientSessionModel) session.getAttribute("role");
			        userName = clientSessionModel.getUsername();
					if(action.equals("update"))
					{
						webSeqTypesModel = new WebSequenceTypesModel(webSequenceTypesModel.getSequenceCode());
					}
					else
					{
						webSeqTypesModel = new WebSequenceTypesModel();
					}
					webSeqTypesModel.setSequenceCode(seqCodeField.getValue().toUpperCase());
					webSeqTypesModel.setSequenceDescription(seqCodeDescription.getValue().toUpperCase());
					webSeqTypesModel.setActiveInd(activeInd.getModelObject() == "ACTIVE" ? "Y" : "N");
					if(action.equalsIgnoreCase("create"))
					{
						webSeqTypesModel.setCreatedBy(userName);
						webSeqTypesModel.setCreatedDate(new Date());
					}
					else
					{
						webSeqTypesModel.setCreatedBy(webSequenceTypesModel.getCreatedBy());
						webSeqTypesModel.setCreatedDate(webSequenceTypesModel.getCreatedDate());
					}
					webSeqTypesModel.setModifiedBy(userName);
					webSeqTypesModel.setModifiedDate(new Date());
					log.debug ("Web Model: "+ webSeqTypesModel);
					
					SequenceTypesModel createSequenceTypesModel = new  WebAdminTranslator().translateWebSequenceTypesModeltoSequenceTypesCommonsModel(webSeqTypesModel);
					results = controller.createSequenceTypes(createSequenceTypesModel);
			        log.debug("===============================originaWebSequenceTypesModel old value =================:"+originaWebSequenceTypesModel);
			        log.debug("===============================webSeqTypesModel new value=================:"+webSeqTypesModel);
			        if(originaWebSequenceTypesModel != null)
			        {
			        	if(originaWebSequenceTypesModel.getSequenceDescription()!= webSeqTypesModel.getSequenceDescription())
						{
							webAuditTrackingModel.setOldValue(originaWebSequenceTypesModel.getSequenceDescription());
							webAuditTrackingModel.setNewValue(webSeqTypesModel.getSequenceDescription());						
						}
						if(originaWebSequenceTypesModel.getActiveInd()!= webSeqTypesModel.getActiveInd())
						{
							webAuditTrackingModel.setOldValue(originaWebSequenceTypesModel.getActiveInd());
							webAuditTrackingModel.setNewValue(webSeqTypesModel.getActiveInd());
						}
			        }
			        else
			        {
			        	webAuditTrackingModel.setNewValue(webSeqTypesModel.getSequenceDescription());			
			        	webAuditTrackingModel.setNewValue(webSeqTypesModel.getActiveInd());
			        }
					webAuditTrackingModel.setColumnName(seqTypeDescLabel.getDefaultModelObjectAsString());
					webAuditTrackingModel.setColumnName(activeIndLabel.getDefaultModelObjectAsString());
					webAuditTrackingModel.setRecordId(new BigDecimal(123));		
					webAuditTrackingModel.setAction(action.toUpperCase());
					webAuditTrackingModel.setUserId(userName);
					webAuditTrackingModel.setActionDate(new Date());
					webAuditTrackingModel.setTableName(screenName);
						
					AuditTrackingModel	 auditTrackingModel = new WebAdminTranslator().translateWebAuditTrackingModelToCommonsModel(webAuditTrackingModel);
					saved =controller.createAuditTracking(auditTrackingModel);
					
					viewsequencepanel =new ViewSequenceTypesPanel(id);
					if(results)
					{
						if(action == "create")
						{
							log.debug("in the create method");
							error("Sequence TypeCode Created Successfully.");
						}
						else
						{
							info("Sequence TypeCode Updated Successfully.");
						}
						MarkupContainer markupContainer = form.getParent().getParent();
						markupContainer.remove(form.getParent());
						markupContainer.add(viewsequencepanel);
						viewsequencepanel.setOutputMarkupId(true);
						viewsequencepanel.setOutputMarkupPlaceholderTag(true);
						TemplatePage.setContent(viewsequencepanel);
					}
					else
					{
						error("Sequence Type Code could not be added/update...");
					}
				}
				catch(Exception exception)
				{
					error("Add/Update failed, " + exception.getMessage());
					log.error("Add/Update failed, ", exception);
				}
			}
		};
	
		cancelButton = new Button("cancelButton")
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit() 
			{
				viewsequencepanel=new ViewSequenceTypesPanel(id);
				MarkupContainer markupContainer = form.getParent().getParent();
				markupContainer.remove(form.getParent());
				markupContainer.add(viewsequencepanel);
				viewsequencepanel.setOutputMarkupId(true);
				viewsequencepanel.setOutputMarkupPlaceholderTag(true);
				TemplatePage.setContent(viewsequencepanel);
			}
		};
		cancelButton.setDefaultFormProcessing(false);
		
		form.add(saveOrUpdateButton);
		form.add(cancelButton);
		add(form);
		FeedbackPanel feedbackPanel = new FeedbackPanel("feedback_1");
        add(feedbackPanel);
	}

	private void loadData() 
	{
	}
	
}