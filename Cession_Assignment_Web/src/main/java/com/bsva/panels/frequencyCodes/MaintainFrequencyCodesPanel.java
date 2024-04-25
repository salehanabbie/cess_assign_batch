package com.bsva.panels.frequencyCodes;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.validation.validator.PatternValidator;
import org.apache.wicket.validation.validator.StringValidator;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.Session;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import com.bsva.TemplatePage;
import com.bsva.commons.model.AuditTrackingModel;
import com.bsva.commons.model.FrequencyCodesModel;
import com.bsva.controller.Controller;
import com.bsva.models.ClientSessionModel;
import com.bsva.models.WebAuditTrackingModel;
import com.bsva.models.WebFrequencyCodesModel;
import com.bsva.translator.WebAdminTranslator;
import com.bsva.validators.TextFieldValidator;

/**
 * 
 * @author DimakatsoN
 *
 */
public class MaintainFrequencyCodesPanel extends Panel
{
	private static final long serialVersionUID = 1L;
	public static Logger log = Logger.getLogger(MaintainFrequencyCodesPanel.class);
	private String screenName;
	private ClientSessionModel clientSessionModel;
	private String userName;
	public static Session session;
	private DropDownChoice<String> activeInd;
	private String action;
	public static String id;
	private TextField<String> frequencyCodeField;
	private TextField<String> frequencyCodeDescription;
	private Button cancelButton;
	private String selectedIndicator;
	private Button saveOrUpdateButton;
	private WebFrequencyCodesModel webFrequencyCodesModel,orginalWebFrequencyCodesModel;
	ViewFrequencyCodesPanel viewfrequencycodes;
	WebAuditTrackingModel webAuditTrackingModel;
	String textValidator = "([a-zA-Z0-9 \\-._()]+)";
	Label frequencyCodeLabel,frequencyDescLabel,activeIndLabel, legendLbl;
	private Form form;
	Controller controller;

	public MaintainFrequencyCodesPanel(String id, String action)
	{
		super(id);
		this.id = id;
		this.action = action;
		intializeComponents();
	}

	public MaintainFrequencyCodesPanel(String id, WebFrequencyCodesModel webFrequencyCodesModel, String action) 
	{
		super(id);
		this.action = action;
		this.id = id;
		this.webFrequencyCodesModel = webFrequencyCodesModel;
		this.orginalWebFrequencyCodesModel=webFrequencyCodesModel;
		intializeComponents();
		log.debug("Screen ID:" + action);
	}

	private void intializeComponents() 
	{
		controller = new Controller();
		screenName = controller.getProperty("MNTN.SCRNNAME");
		legendLbl = new Label("legend","Frequency Codes  - "+action.toUpperCase());
		add(legendLbl);
		
		form = new Form("maintainFrequencyCodesPanel");
		
		frequencyCodeLabel = new Label("label1","Frequency Code");
		form.add(frequencyCodeLabel);
		
		frequencyDescLabel =new Label("label2","Frequecy Code Description");
		form.add(frequencyDescLabel);
		
		activeIndLabel=new Label("label3","Active Indicator");
		form.add(activeIndLabel);

		frequencyCodeField = new TextField<String>("frequencyCodeField",new Model<String>(webFrequencyCodesModel == null ? "": webFrequencyCodesModel.getFrequencyCode()));
		frequencyCodeField.setRequired(true);
		frequencyCodeField.add(StringValidator.maximumLength(4));
		frequencyCodeField.add(new PatternValidator(textValidator));
		if (action == "update") 
		{
			frequencyCodeField.setEnabled(false);
		}
		form.add(frequencyCodeField);

		frequencyCodeDescription = new TextField<String>("frequencyCodeDescription", new Model<String>(webFrequencyCodesModel == null ? "": webFrequencyCodesModel.getFrequencyCodeDescription()));
		frequencyCodeDescription.setRequired(true);
		frequencyCodeDescription.add(StringValidator.maximumLength(100));
		frequencyCodeDescription.add(new PatternValidator(textValidator));
		form.add(frequencyCodeDescription);

		List<String> indicators = new ArrayList<String>();
		indicators.add("ACTIVE");
		indicators.add("INACTIVE");
		if (webFrequencyCodesModel != null) 
		{
			if (webFrequencyCodesModel.getActiveInd() != null && webFrequencyCodesModel.getActiveInd().equals("Y")) 
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
					WebFrequencyCodesModel localWebFrequencyCodesModel = null;
					webAuditTrackingModel = new WebAuditTrackingModel();
					session = getSession();
			        clientSessionModel = (ClientSessionModel) session.getAttribute("role");
			        userName = clientSessionModel.getUsername();
					if (action.equals("update")) 
					{
						localWebFrequencyCodesModel = new WebFrequencyCodesModel(webFrequencyCodesModel.getFrequencyCode());
					}
					else 
					{
						localWebFrequencyCodesModel = new WebFrequencyCodesModel();

					}
					localWebFrequencyCodesModel.setFrequencyCode(frequencyCodeField.getValue()									.toUpperCase());
					localWebFrequencyCodesModel.setFrequencyCodeDescription(frequencyCodeDescription.getValue().toUpperCase());
					localWebFrequencyCodesModel.setActiveInd(activeInd.getModelObject() == "ACTIVE" ? "Y" : "N");
					
					if(action.equalsIgnoreCase("create"))
					{
						localWebFrequencyCodesModel.setCreatedBy(userName);
						localWebFrequencyCodesModel.setCreatedDate(new Date());
					}
					else
					{
						localWebFrequencyCodesModel.setCreatedBy(webFrequencyCodesModel.getCreatedBy());
						localWebFrequencyCodesModel.setCreatedDate(webFrequencyCodesModel.getCreatedDate());
					}
					localWebFrequencyCodesModel.setModifiedBy(userName);
					localWebFrequencyCodesModel.setModifiedDate(new Date());
					log.debug(" Web Model:" + localWebFrequencyCodesModel);

					FrequencyCodesModel createlocalWebFrequencyCodesModel = new WebAdminTranslator().translateWebFrequencyCodesModelToFrequencyCommonsModel(localWebFrequencyCodesModel);
					results = controller.createFrequencyCodes(createlocalWebFrequencyCodesModel);
			        log.debug("===============================orginalWebFrequencyCodesModel old value =================:"+orginalWebFrequencyCodesModel);
			        log.debug("===============================localWebFrequencyCodesModel new value=================:"+localWebFrequencyCodesModel);
			        if(orginalWebFrequencyCodesModel != null)
			        {
			        	if(orginalWebFrequencyCodesModel.getFrequencyCodeDescription()!= localWebFrequencyCodesModel.getFrequencyCodeDescription())
						{
							webAuditTrackingModel.setOldValue(orginalWebFrequencyCodesModel.getFrequencyCodeDescription());
							webAuditTrackingModel.setNewValue(localWebFrequencyCodesModel.getFrequencyCodeDescription());
						}
						if(orginalWebFrequencyCodesModel.getActiveInd()!= localWebFrequencyCodesModel.getActiveInd())
						{
							webAuditTrackingModel.setOldValue(orginalWebFrequencyCodesModel.getActiveInd());
							webAuditTrackingModel.setNewValue(localWebFrequencyCodesModel.getActiveInd());
						}
			        }
			        else
			        {
			        	webAuditTrackingModel.setNewValue(localWebFrequencyCodesModel.getFrequencyCodeDescription());
			        	webAuditTrackingModel.setNewValue(localWebFrequencyCodesModel.getActiveInd());
			        }
			        webAuditTrackingModel.setColumnName(activeIndLabel.getDefaultModelObjectAsString());
			        webAuditTrackingModel.setColumnName(frequencyDescLabel.getDefaultModelObjectAsString());
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
							error("Frequency Codes Created Successfully.");
						}
						else
						{
							info("Frequency Codes Updated Successfully.");
						}
						viewfrequencycodes = new ViewFrequencyCodesPanel(id);
						MarkupContainer markupContainer = form.getParent().getParent();
						markupContainer.remove(form.getParent());
						markupContainer.add(viewfrequencycodes);
						viewfrequencycodes.setOutputMarkupId(true);
						viewfrequencycodes.setOutputMarkupPlaceholderTag(true);
						TemplatePage.setContent(viewfrequencycodes);
					}
					else 
					{
						error("Frequency Code could not be added/update...");
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
				viewfrequencycodes = new ViewFrequencyCodesPanel(id);
				MarkupContainer markupContainer = form.getParent().getParent();
				markupContainer.remove(form.getParent());
				markupContainer.add(viewfrequencycodes);
				viewfrequencycodes.setOutputMarkupId(true);
				viewfrequencycodes.setOutputMarkupPlaceholderTag(true);
				TemplatePage.setContent(viewfrequencycodes);
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