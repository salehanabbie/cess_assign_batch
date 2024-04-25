package com.bsva.panels.errorCodes;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.Session;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
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
import com.bsva.commons.model.ConfgErrorCodesModel;
import com.bsva.commons.model.ConfgSeverityCodesModel;
import com.bsva.controller.Controller;
import com.bsva.models.ClientSessionModel;
import com.bsva.models.WebAuditTrackingModel;
import com.bsva.models.WebConfgErrorCodesModel;
import com.bsva.models.WebConfgSeverityCodesModel;
import com.bsva.translator.WebAdminTranslator;
import com.bsva.validators.FieldNumberTextFieldValidator;
import com.bsva.validators.TextFieldValidator;

/**
 * 
 * @author DimakatsoN
 *
 */

public class MaintainConfgErrorCodesPanel extends Panel 
{
	private static final long serialVersionUID = 1L;
	public static Logger log = Logger.getLogger(MaintainConfgErrorCodesPanel.class);
	Controller controller;
	private String screenName;
	private ClientSessionModel clientSessionModel;
	private String userName;
	public static Session session;
	private Form form;
	public static String id;
	private Button saveOrUpdateButton;
	private Button cancelButton;
	private TextField<String> errorCode;
	private TextField<String> errorDescription;
	private DropDownChoice<String> activeInd;
	private DropDownChoice<WebConfgSeverityCodesModel> severity;
	private String selectedIndicator;
	private String action;
	Label errorCodeLabel,errorDescriptionLabel,activeIndLabel,severityLabel, legendLbl;
	private WebConfgSeverityCodesModel webConfgSeverityCodesModel;
	private List<WebConfgSeverityCodesModel> severityList;
	ViewConfgErrorCodesPanel viewerrorcodes;
	WebConfgErrorCodesModel webConfgErrorCodesModel,originalWebConfgErrorCodesModel;
	WebAuditTrackingModel webAuditTrackingModel;
	String textValidator = "([a-zA-Z0-9 \\-._()]+)";

	public MaintainConfgErrorCodesPanel(String id, String action)
	{
		super(id);
		this.action = action;
		this.id = id;
		initializeComponents();
	}

	public MaintainConfgErrorCodesPanel(String id,WebConfgErrorCodesModel webErrorCodesModel, String action)
	{
		super(id);
		this.action = action;
		this.id = id;
		this.webConfgErrorCodesModel = webErrorCodesModel;
		this.originalWebConfgErrorCodesModel = webErrorCodesModel;
		initializeComponents();
	}

	private void initializeComponents() 
	{
		controller = new Controller();
		screenName = controller.getProperty("MNTN.SCRNNAME");
		legendLbl = new Label("legend","Error Codes  - "+action.toUpperCase());
		add(legendLbl);
		form = new Form("maintainConfgErrorCodesPanel");
		
		errorCodeLabel = new Label("label1","Error Code ");
		form.add(errorCodeLabel);
		
		errorDescriptionLabel = new Label("label2","Error Code Description ");
		form.add(errorDescriptionLabel);
		
		severityLabel =new Label("label4","Severity ");
		form.add(severityLabel);
		
		activeIndLabel=new Label("label3","Active Indicator ");
		form.add(activeIndLabel);
		if (action == "create")
		{
			webConfgErrorCodesModel = new WebConfgErrorCodesModel();
		}
		loadData();

		errorCode = new TextField<String>("errorCode", new Model<String>(webConfgErrorCodesModel == null ? "": webConfgErrorCodesModel.getErrorCode()));
		errorCode.setRequired(true);
		errorCode.add(StringValidator.exactLength(6));
		errorCode.add(new PatternValidator(textValidator));
		if (action == "update") 
		{
			errorCode.setEnabled(false);
		}
		form.add(errorCode); 
		
		errorDescription = new TextField<String>("errorDescription",new Model<String>(webConfgErrorCodesModel == null ? "": webConfgErrorCodesModel.getErrorDescription()));
		errorDescription.setRequired(true);
		errorDescription.add(StringValidator.maximumLength(100));
		errorDescription.add(new PatternValidator(textValidator));
		form.add(errorDescription);

		List<String> indicators = new ArrayList<String>();
		indicators.add("ACTIVE");
		indicators.add("INACTIVE");
		if(webConfgErrorCodesModel != null)
		{
			if(webConfgErrorCodesModel.getActiveInd() != null && webConfgErrorCodesModel.getActiveInd().equals("Y"))
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
			selectedIndicator="ACTIVE";
		}
		activeInd = new DropDownChoice<String>("activeInd", new Model<String>(selectedIndicator), indicators);
		activeInd.setRequired(true);
		if (action.equalsIgnoreCase("create")) 
		{
			activeInd.setEnabled(false);
		}
		form.add(activeInd); 

		severity = new DropDownChoice<WebConfgSeverityCodesModel>("severity",new Model<WebConfgSeverityCodesModel>(), severityList,new ChoiceRenderer<WebConfgSeverityCodesModel>());
		severity.setRequired(true);
		if (action == "create")
		{
			severity.setDefaultModelObject(severity.getChoices().get(0).getSeverityCodeDesc());
		}
		else if (action == "update") 
		{
			int sel = 0;
			for (int index = 0; index < severityList.size(); index++) 
			{
				if (webConfgErrorCodesModel.getSeverity().equalsIgnoreCase(severityList.get(index).getSeverityCodeDesc()))
				{
					sel = index;
				}

			}
			severity.setModelObject(severity.getChoices().get(sel));
		}
		form.add(severity);

		saveOrUpdateButton = new Button("saveButton") 
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit() 
			{
				try 
				{
					boolean results = false;
					boolean saved = false;
					WebConfgErrorCodesModel errorWebModel = null;
					webAuditTrackingModel = new WebAuditTrackingModel();
					session = getSession();
			        clientSessionModel = (ClientSessionModel) session.getAttribute("role");
			        userName = clientSessionModel.getUsername();
					if (action.equals("update"))
					{
						errorWebModel = new WebConfgErrorCodesModel(webConfgErrorCodesModel.getErrorCode());
					} 
					else
					{
						errorWebModel = new WebConfgErrorCodesModel();
					}
					errorWebModel.setErrorCode(errorCode.getValue());
					String errCodeDescptn = StringUtils.capitalize(errorDescription.getValue());
					errorWebModel.setErrorDescription(errCodeDescptn);
					errorWebModel.setActiveInd(activeInd.getModelObject() == "ACTIVE" ? "Y": "N");
					errorWebModel.setSeverity(severity.getValue());
					if(action.equalsIgnoreCase("create"))
					{
						errorWebModel.setCreatedBy(userName);
						errorWebModel.setCreatedDate(new Date());
					}
					else
					{
						errorWebModel.setCreatedBy(webConfgErrorCodesModel.getCreatedBy());
						errorWebModel.setCreatedDate(webConfgErrorCodesModel.getCreatedDate());
					}
					errorWebModel.setModifiedBy(userName);
					errorWebModel.setModifiedDate(new Date());

					ConfgErrorCodesModel createErrorModel = new WebAdminTranslator().translateWebConfgErrorCodesModelToCommonsModel(errorWebModel);
					results = controller.createErrorCodes(createErrorModel);	
					if(originalWebConfgErrorCodesModel != null)
					{
						if(originalWebConfgErrorCodesModel.getErrorDescription() != errorWebModel.getErrorDescription())
						{
							webAuditTrackingModel.setOldValue(originalWebConfgErrorCodesModel.getErrorDescription());
							webAuditTrackingModel.setNewValue(errorWebModel.getErrorDescription());
						}
						if(originalWebConfgErrorCodesModel.getSeverity() != errorWebModel.getSeverity())
						{
							webAuditTrackingModel.setOldValue(originalWebConfgErrorCodesModel.getSeverity());
							webAuditTrackingModel.setNewValue(errorWebModel.getSeverity());
						}	
						if(originalWebConfgErrorCodesModel.getActiveInd() != errorWebModel.getActiveInd())
						{
							webAuditTrackingModel.setOldValue(originalWebConfgErrorCodesModel.getActiveInd());
							webAuditTrackingModel.setNewValue(errorWebModel.getActiveInd());
						}	
					}
					else
					{
						webAuditTrackingModel.setNewValue(errorWebModel.getErrorDescription());
						webAuditTrackingModel.setNewValue(errorWebModel.getSeverity());
						webAuditTrackingModel.setNewValue(errorWebModel.getActiveInd());
					}
					webAuditTrackingModel.setColumnName(errorDescriptionLabel.getDefaultModelObjectAsString());
					webAuditTrackingModel.setColumnName(severityLabel.getDefaultModelObjectAsString());
					webAuditTrackingModel.setColumnName(activeIndLabel.getDefaultModelObjectAsString());
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
							error("Error Codes Created Successfully.");
						}
						else
						{
							info("Error Codes Updated Successfully.");
						}
						viewerrorcodes = new ViewConfgErrorCodesPanel(id);
						MarkupContainer markupContainer = form.getParent().getParent();
						markupContainer.remove(form.getParent());
						markupContainer.add(viewerrorcodes);
						viewerrorcodes.setOutputMarkupId(true);
						viewerrorcodes.setOutputMarkupPlaceholderTag(true);
						TemplatePage.setContent(viewerrorcodes);

					} 
					else 
					{
						error("Error Codes could not be added/update...");
					}
				} 
				catch (Exception exception) 
				{
					error("Add/Update failed, " + exception.getMessage());
					log.error("Add/Update failed, ", exception);
				}
			}
		};

		cancelButton = new Button("cancelButton") 
		{
			@Override
			public void onSubmit() 
			{
				viewerrorcodes = new ViewConfgErrorCodesPanel(id);
				MarkupContainer markupContainer = form.getParent().getParent();
				markupContainer.remove(form.getParent());
				markupContainer.add(viewerrorcodes);
				viewerrorcodes.setOutputMarkupId(true);
				viewerrorcodes.setOutputMarkupPlaceholderTag(true);
				TemplatePage.setContent(viewerrorcodes);
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
		List<ConfgSeverityCodesModel> severityListFromDb = new ArrayList<ConfgSeverityCodesModel>();
		severityListFromDb = (List<ConfgSeverityCodesModel>) controller.viewAllSeverityCodes();
		if (severityListFromDb.size() > 0) 
		{
			severityList = new ArrayList<WebConfgSeverityCodesModel>();
			for (ConfgSeverityCodesModel localCommonModel : severityListFromDb) 
			{
				WebConfgSeverityCodesModel webModel = new WebConfgSeverityCodesModel();
				webModel = new WebAdminTranslator().translateCommonsConfgSeverityCodesModelToWebModel(localCommonModel);
				severityList.add(webModel);
			}
		}
	}
}