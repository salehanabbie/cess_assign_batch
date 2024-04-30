package com.bsva.panels.reasonCodes;
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
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.validation.validator.PatternValidator;
import org.apache.wicket.validation.validator.StringValidator;
import com.bsva.TemplatePage;
import com.bsva.commons.model.AuditTrackingModel;
import com.bsva.commons.model.ReasonCodesModel;
import com.bsva.controller.Controller;
import com.bsva.models.ClientSessionModel;
import com.bsva.models.WebAuditTrackingModel;
import com.bsva.models.WebReasonCodesModel;
import com.bsva.translator.WebAdminTranslator;
import com.bsva.validators.TextFieldValidator;

/**
 * *
 * 
 * @author DimakatsoN
 * 
 */
public class MaintainReasonCodesPanel extends Panel 
{
	private static final long serialVersionUID = 1L;
	public static Logger log = Logger.getLogger(MaintainReasonCodesPanel.class);
	private String screenName;
	private ClientSessionModel clientSessionModel;
	private String userName;
	public static Session session;
	private Form form;
	private Button saveOrUpdateButton;
	public static String id;
	private Button cancelButton;
	ViewReasonCodesPanel viewreasoncodes;
	private TextField<String> reasonCodeField;
	private TextField<String> reasonCodeDescription;
	Label reasonCodeLabel,reasonDescLabel,activeIndLabel,legendLbl;
	private DropDownChoice<String> activeInd;
	private String selectedIndicator;
	private String action;
	WebReasonCodesModel webReasonCodesModel,originalWebReasonCodesModel;
	WebAuditTrackingModel webAuditTrackingModel;
	String textValidator = "([a-zA-Z0-9 \\-._()]+)";
	Controller controller;

	public MaintainReasonCodesPanel(String id, String action)
	{
		super(id);
		this.action = action;
		this.id = id;
		initializeComponents();
	}

	public MaintainReasonCodesPanel(String id,WebReasonCodesModel webReasonCodesModel, String action)
	{
		super(id);
		this.action = action;
		this.id = id;
		this.webReasonCodesModel = webReasonCodesModel;
		this.originalWebReasonCodesModel = webReasonCodesModel;
		initializeComponents();
		log.debug("Screen ID:" + action);
	}

	private void initializeComponents() 
	{
		controller = new Controller();
		screenName = controller.getProperty("MNTN.SCRNNAME");

		legendLbl = new Label("legend","Reason Codes  - "+action.toUpperCase());
		add(legendLbl);
		form = new Form("maintainReasonCodesPanel");	
		
		reasonCodeLabel= new Label("label1","Reason Code");
		form.add(reasonCodeLabel);
		
		reasonDescLabel = new Label("label2","Reason Code Description");
		form.add(reasonDescLabel);
		
		activeIndLabel=new Label("label3","Active Indicator");
		form.add(activeIndLabel);
		
		reasonCodeField = new TextField<String>("reasonCodeField",new Model<String>(webReasonCodesModel == null ? "": webReasonCodesModel.getReasonCode()));
		reasonCodeField.setRequired(true);
		reasonCodeField.add(StringValidator.exactLength(4));
		reasonCodeField.add(new PatternValidator(textValidator));
		form.add(reasonCodeField);
		if (action == "update") 
		{
			reasonCodeField.setEnabled(false);
		}
		form.add(reasonCodeField);

		reasonCodeDescription = new TextField<String>("reasonCodeDescription",new Model<String>(webReasonCodesModel == null ? "": webReasonCodesModel.getReasonCodeDescription()));
		reasonCodeDescription.setRequired(true);
		reasonCodeDescription.add(StringValidator.maximumLength(100));
		reasonCodeDescription.add(new PatternValidator(textValidator));
		form.add(reasonCodeDescription);

		List<String> indicators = new ArrayList<String>();
		indicators.add("ACTIVE");
		indicators.add("INACTIVE");
		if (webReasonCodesModel != null)
		{
			if (webReasonCodesModel.getActiveInd() != null && webReasonCodesModel.getActiveInd().equals("Y")) 
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
					boolean saved = false;
					WebReasonCodesModel webReasCodesModel = null;
					webAuditTrackingModel = new WebAuditTrackingModel();
					session = getSession();
			        clientSessionModel = (ClientSessionModel) session.getAttribute("role");
			        userName = clientSessionModel.getUsername();
					if (action.equals("update")) 
					{
						webReasCodesModel = new WebReasonCodesModel(webReasonCodesModel.getReasonCode());
					} 
					else 
					{
						webReasCodesModel = new WebReasonCodesModel();
					}
					webReasCodesModel.setReasonCode(reasonCodeField.getValue());
					webReasCodesModel.setReasonCodeDescription(reasonCodeDescription.getValue().toUpperCase());
					webReasCodesModel.setActiveInd(activeInd.getModelObject() == "ACTIVE" ? "Y": "N");

					if(action.equalsIgnoreCase("create"))
					{
						webReasCodesModel.setCreatedBy(userName);
						webReasCodesModel.setCreatedDate(new Date());
					}
					else
					{
						webReasCodesModel.setCreatedBy(webReasonCodesModel.getCreatedBy());
						webReasCodesModel.setCreatedDate(webReasonCodesModel.getCreatedDate());
					}
					webReasCodesModel.setModifiedBy(userName);
					webReasCodesModel.setModifiedDate(new Date());
					log.debug("Web Model: " + webReasCodesModel);

					ReasonCodesModel createReasModel = new WebAdminTranslator().translateWebReasonCodesModelToReasonCommonsModel(webReasCodesModel);
					Controller controller = new Controller();
					results = controller.createReasonCodes(createReasModel);
			        log.debug("===============================originalWebReasonCodesModel old value =================:"+originalWebReasonCodesModel);
			        log.debug("===============================webReasCodesModel new value=================:"+webReasCodesModel);
			        if(originalWebReasonCodesModel != null)
			        {
			    		if(originalWebReasonCodesModel.getReasonCodeDescription()!= webReasCodesModel.getReasonCodeDescription())
						{
							webAuditTrackingModel.setOldValue(webReasonCodesModel.getReasonCodeDescription());
							webAuditTrackingModel.setNewValue(webReasCodesModel.getReasonCodeDescription());
						}
						if(originalWebReasonCodesModel.getActiveInd()!= webReasCodesModel.getActiveInd())
						{
							webAuditTrackingModel.setOldValue(originalWebReasonCodesModel.getActiveInd());
							webAuditTrackingModel.setNewValue(webReasCodesModel.getActiveInd());
						}
			        }
			       else
			       {
			    	   webAuditTrackingModel.setNewValue(webReasCodesModel.getReasonCodeDescription());
			    	   webAuditTrackingModel.setNewValue(webReasCodesModel.getActiveInd());
			       }
			       webAuditTrackingModel.setColumnName(reasonDescLabel.getDefaultModelObjectAsString());
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
						   error("Reason Code Created Successfully.");
					   }
					   else
					   {
						   info("Reason Code Updated Successfully.");
					   }
					   viewreasoncodes = new ViewReasonCodesPanel(id);
					   MarkupContainer markupContainer = form.getParent().getParent();
					   markupContainer.remove(form.getParent());
					   markupContainer.add(viewreasoncodes);
					   viewreasoncodes.setOutputMarkupId(true);
					   viewreasoncodes.setOutputMarkupPlaceholderTag(true);
					   TemplatePage.setContent(viewreasoncodes);
					}
				   	else 
				   	{
						error("Reason Code could not be added/update...");
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
			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit() 
			{
				viewreasoncodes = new ViewReasonCodesPanel(id);
				MarkupContainer markupContainer = form.getParent().getParent();
				markupContainer.remove(form.getParent());
				markupContainer.add(viewreasoncodes);
				viewreasoncodes.setOutputMarkupId(true);
				viewreasoncodes.setOutputMarkupPlaceholderTag(true);
				TemplatePage.setContent(viewreasoncodes);
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