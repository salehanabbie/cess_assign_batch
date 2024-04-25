package com.bsva.panels.processStatus;

import java.io.Serializable;
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
import com.bsva.TemplatePage;
import com.bsva.commons.model.AuditTrackingModel;
import com.bsva.commons.model.ProcessStatusModel;
import com.bsva.controller.Controller;
import com.bsva.models.ClientSessionModel;
import com.bsva.models.WebAuditTrackingModel;
import com.bsva.models.WebProcessStatusModel;
import com.bsva.translator.WebAdminTranslator;
import com.bsva.validators.TextFieldValidator;

/**
 * 
 * @author NhlanhlaM
 *
 */
public class MaintainProcessStatusPanel extends Panel implements Serializable
{
	private static final long serialVersionUID = 1L;
	public static Logger log = Logger.getLogger(MaintainProcessStatusPanel.class);
	private String screenName;
	private ClientSessionModel clientSessionModel;
	private String userName;
	public static Session session;
	private DropDownChoice<String> activeInd;
	private String action;
	public static String id;
	private TextField<String> status;
	private TextField<String> statusDescription;
	private Button cancelButton;
	private Button saveOrUpdateButton;
	private String selectedIndicator;
	Label statusLabel,statusDescLabel,activeIndLabel, legendLbl;
	private WebProcessStatusModel webProcessStatusModel,originalWebProcessStatusModel;
	ViewProcessStatusPanel viewProcessStatusPanel;	
	WebAuditTrackingModel webAuditTrackingModel;
	private Form form;
	Controller controller;
	String textValidator = "([a-zA-Z0-9 \\-._()]+)";

	public MaintainProcessStatusPanel(String id, String action)
	{
		super(id);
		this.id = id;
		this.action = action;
		initializeComponents();
	}

	public MaintainProcessStatusPanel(String id, WebProcessStatusModel webProcessStatusModel, String action ) 
	{
		super(id);
		this.action = action;
		this.id = id;
		this.webProcessStatusModel = webProcessStatusModel;
		this.originalWebProcessStatusModel = webProcessStatusModel;
		initializeComponents();
		log.debug("Screen ID:" + action);
	}

	private void initializeComponents()
	{
		controller = new Controller();
		screenName = controller.getProperty("MNTN.SCRNNAME");
		legendLbl = new Label("legend","Process Status  - "+action.toUpperCase());
		add(legendLbl);
		form = new Form("maintainProcessStatusPanel");

		statusLabel= new Label("label1","Status");
		form.add(statusLabel);
		
		statusDescLabel = new Label("label2","Process Status Description");
		form.add(statusDescLabel);
		
		activeIndLabel=new Label("label3","Active Indicator");
		form.add(activeIndLabel);

		status = new TextField<String>("status",new Model<String>(webProcessStatusModel == null ? "" : webProcessStatusModel.getStatus()));
		status.setRequired(true);
		status.add(StringValidator.maximumLength(2));
		status.add(new PatternValidator(textValidator));
		form.add(status);
		if (action == "update") 
		{
			status.setEnabled(false);
		}
		form.add(status);

		statusDescription = new TextField<String>("statusDescription",new Model<String>(webProcessStatusModel == null ? " " : webProcessStatusModel.getStatusDescription()));
		statusDescription.setRequired(true);
		statusDescription.add(StringValidator.maximumLength(50));
		statusDescription.add(new PatternValidator(textValidator));
		form.add(statusDescription);

		List<String> indicators = new ArrayList<String>();
		indicators.add("ACTIVE");
		indicators.add("INACTIVE");
		if (webProcessStatusModel != null) 
		{
			if (webProcessStatusModel.getActiveInd() != null && webProcessStatusModel.getActiveInd().equals("Y")) 
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
		if (action.equalsIgnoreCase("create")) 
		{
			activeInd.setEnabled(false);
		}
		form.add(activeInd);
		
		saveOrUpdateButton = new Button("saveButton") 
		{
			@Override
			public void onSubmit() 
			{
				try 
				{
					boolean results = false;
					boolean saved =false;
					WebProcessStatusModel localWebProcessStatusModel = null;
					webAuditTrackingModel = new WebAuditTrackingModel();
					session = getSession();
					clientSessionModel = (ClientSessionModel) session.getAttribute("role");
					userName = clientSessionModel.getUsername();
					if (action.equals("update")) 
					{
						localWebProcessStatusModel = new WebProcessStatusModel(webProcessStatusModel.getStatus());
					} 
					else 
					{
						localWebProcessStatusModel = new WebProcessStatusModel();
					}
					localWebProcessStatusModel.setStatus(status.getValue().toUpperCase());
					localWebProcessStatusModel.setStatusDescription(statusDescription.getValue().toUpperCase());
					localWebProcessStatusModel.setActiveInd(activeInd.getModelObject() == "ACTIVE" ? "Y" : "N");
					if(action.equalsIgnoreCase("create"))
					{
						localWebProcessStatusModel.setCreatedBy(userName);
						localWebProcessStatusModel.setCreatedDate(new Date());
					}
					else
					{
						localWebProcessStatusModel.setCreatedBy(webProcessStatusModel.getCreatedBy());
						localWebProcessStatusModel.setCreatedDate(webProcessStatusModel.getCreatedDate());
					}
					localWebProcessStatusModel.setModifiedBy(userName);
					localWebProcessStatusModel.setModifiedDate(new Date());
					log.debug("Web Model: " + localWebProcessStatusModel);

					ProcessStatusModel processStatusModel = new WebAdminTranslator().translateWebProcessStatusModelToFileCommonsModel(localWebProcessStatusModel);
					results = controller.createProcessStatus(processStatusModel);
					log.debug("======================originalWebProcessStatusModel=================:"+originalWebProcessStatusModel);
					log.debug("======================localWebProcessStatusModel=================:"+localWebProcessStatusModel);
					if(originalWebProcessStatusModel != null)
					{
						if(originalWebProcessStatusModel.getStatusDescription()!= localWebProcessStatusModel.getStatusDescription())
						{
							webAuditTrackingModel.setOldValue(originalWebProcessStatusModel.getStatusDescription());
							webAuditTrackingModel.setNewValue(localWebProcessStatusModel.getStatusDescription());
						}
						if(originalWebProcessStatusModel.getActiveInd()!= localWebProcessStatusModel.getActiveInd())
						{
							webAuditTrackingModel.setOldValue(originalWebProcessStatusModel.getActiveInd());
							webAuditTrackingModel.setNewValue(localWebProcessStatusModel.getActiveInd());
						}
						else
						{
							webAuditTrackingModel.setNewValue(localWebProcessStatusModel.getStatusDescription());
							webAuditTrackingModel.setNewValue(localWebProcessStatusModel.getActiveInd());
						}
					}
					webAuditTrackingModel.setColumnName(activeIndLabel.getDefaultModelObjectAsString());
					webAuditTrackingModel.setColumnName(statusDescLabel.getDefaultModelObjectAsString());
					webAuditTrackingModel.setRecordId(new BigDecimal(123));
					webAuditTrackingModel.setAction(action.toUpperCase());
					webAuditTrackingModel.setUserId(userName);
					webAuditTrackingModel.setActionDate(new Date());
					webAuditTrackingModel.setTableName(screenName);

					AuditTrackingModel	 auditTrackingModel = new WebAdminTranslator().translateWebAuditTrackingModelToCommonsModel(webAuditTrackingModel);
					saved =controller.createAuditTracking(auditTrackingModel);
					if (results) 
					{
						if(action == "create")
						{
							log.debug("in the create method");
							error("File Status Created Successfully.");
						}
						else
						{
							info("File Status Updated Successfully.");
						}
						MarkupContainer markupContainer = form.getParent().getParent();
						viewProcessStatusPanel = new ViewProcessStatusPanel(id);
						markupContainer.remove(form.getParent());
						markupContainer.add(viewProcessStatusPanel);
						viewProcessStatusPanel.setOutputMarkupId(true);
						viewProcessStatusPanel.setOutputMarkupPlaceholderTag(true);
						TemplatePage.setContent(viewProcessStatusPanel);
					} 
					else 
					{
						error("File Status could not be added/updated...");
					}
				}
				catch (Exception exception) 
				{
					error("Add/Update failed, " + exception.getMessage());
					log.error("Add/Update failed, ", exception);
					exception.printStackTrace();
				}
			}

		};
		
		cancelButton = new Button("cancelButton") 
		{
			private static final long serialVersionUID = 1l;

			@Override
			public void onSubmit()
			{
				viewProcessStatusPanel = new ViewProcessStatusPanel(id);
				MarkupContainer markupContainer = form.getParent().getParent();
				markupContainer.remove(form.getParent());
				markupContainer.add(viewProcessStatusPanel);
				viewProcessStatusPanel.setOutputMarkupId(true);
				viewProcessStatusPanel.setOutputMarkupPlaceholderTag(true);
				TemplatePage.setContent(viewProcessStatusPanel);
			}

		};
		cancelButton.setDefaultFormProcessing(false);
		
		form.add(saveOrUpdateButton);
		form.add(cancelButton);
		add(form);
		FeedbackPanel feedbackPanel = new FeedbackPanel("feedback_1");
		add(feedbackPanel);
	}
}
