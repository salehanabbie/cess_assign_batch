package com.bsva.panels.statusReasonCodes;
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
import com.bsva.commons.model.StatusReasonCodesModel;
import com.bsva.controller.Controller;
import com.bsva.models.ClientSessionModel;
import com.bsva.models.WebAuditTrackingModel;
import com.bsva.models.WebStatusReasonCodesModel;
import com.bsva.translator.WebAdminTranslator;
import com.bsva.validators.TextFieldValidator;

/**
 * 
 * @author NhlanhlaM
 *
 */
public class MaintainStatusReasonCodesPanel extends Panel implements Serializable
{
	private static final long serialVersionUID = 1L;
	public static Logger log = Logger.getLogger(MaintainStatusReasonCodesPanel.class);
	private String screenName;
	private ClientSessionModel clientSessionModel;
	private String userName;
	public static Session session;
	private String action;
	private static String id;
	private DropDownChoice<String> activeInd;
	private TextField<String> statusReasonCode;
	private TextField<String> statusReasonCodeDescription;
	Label statusReasCodeLabel,statusReasDescLabel,activeIndLabel,legendLbl;
	private Button cancelButton;
	private String selectedIndicator;
	private Button saveOrUpdateButton;
	private WebStatusReasonCodesModel webStatusReasonCodesModel,originalWebStatusReasonCodesModel;
	WebAuditTrackingModel webAuditTrackingModel;
	ViewStatusReasonCodesPanel viewStatusReasonCodesPanel;
    private Form form;
    String textValidator = "([a-zA-Z0-9 \\-._()]+)";
	Controller controller;
    
	public MaintainStatusReasonCodesPanel(String id, String action)
	{
		super(id);
		this.id = id;
		this.action = action;
		initialiazeComponents(); 
	}

	public MaintainStatusReasonCodesPanel (String id, WebStatusReasonCodesModel webStatusReasonCodesModel, String action)
	{
		super(id);
		this.action = action;
		this.id = id;
		this.webStatusReasonCodesModel = webStatusReasonCodesModel;
		this.originalWebStatusReasonCodesModel =webStatusReasonCodesModel;
		initialiazeComponents();
		log.debug("Screen ID:" + action);
	}

	private void initialiazeComponents() 
	{
		controller = new Controller();
		screenName = controller.getProperty("MNTN.SCRNNAME");
		legendLbl = new Label("legend","Status Reason Codes  - "+action.toUpperCase());
		add(legendLbl);
		form = new Form("maintainStatusReasonCodesPanel");
		
		statusReasCodeLabel = new Label("label1","Status Reason Code");
		form.add(statusReasCodeLabel);
		
		statusReasDescLabel = new Label("label2","Status Reason Desc");
		form.add(statusReasDescLabel);
		
		activeIndLabel = new Label("label3","Active Ind");
		form.add(activeIndLabel);
		
		statusReasonCode =  new TextField<String>("statusReasonCode", new Model<String>(webStatusReasonCodesModel == null ? "" : webStatusReasonCodesModel.getStatusReasonCode()));
		statusReasonCode.setRequired(true);
		statusReasonCode.add(StringValidator.maximumLength(4));
		statusReasonCode.add(new PatternValidator(textValidator));
		if (action == "update")
		{
			statusReasonCode.setEnabled(false);
		}
        form.add(statusReasonCode);
		
        statusReasonCodeDescription = new TextField<String>("statusReasonCodeDescription", new Model<String>(webStatusReasonCodesModel == null ? "" : webStatusReasonCodesModel.getStatusReasonCodeDescription()));
        statusReasonCodeDescription.setRequired(true);
        statusReasonCodeDescription.add(StringValidator.maximumLength(100));
        statusReasonCodeDescription.add(new PatternValidator(textValidator));
		form.add(statusReasonCodeDescription);
		
		List<String> indicators = new ArrayList<String>();
		indicators.add("ACTIVE");
		indicators.add("INACTIVE");
		if (webStatusReasonCodesModel != null) 
		{
			if (webStatusReasonCodesModel.getActiveInd() != null && webStatusReasonCodesModel.getActiveInd().equals("Y"))
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
					WebStatusReasonCodesModel localWebStatusReasonCodesModel = null;
					webAuditTrackingModel = new WebAuditTrackingModel();
					session = getSession();
			        clientSessionModel = (ClientSessionModel) session.getAttribute("role");
			        userName = clientSessionModel.getUsername();
			        if (action.equals("update"))
					{
						localWebStatusReasonCodesModel = new WebStatusReasonCodesModel(webStatusReasonCodesModel.getStatusReasonCode());
					}
			        else 
					{
						localWebStatusReasonCodesModel = new WebStatusReasonCodesModel();
					}
					localWebStatusReasonCodesModel.setStatusReasonCode(statusReasonCode.getValue().toUpperCase());
					localWebStatusReasonCodesModel.setStatusReasonCodeDescription(statusReasonCodeDescription.getValue().toUpperCase());
					localWebStatusReasonCodesModel.setActiveInd(activeInd.getModelObject() == "ACTIVE" ? "Y" : "N");
					
					if(action.equalsIgnoreCase("create"))
					{
						localWebStatusReasonCodesModel.setCreatedBy(userName);
						localWebStatusReasonCodesModel.setCreatedDate(new Date());
					}
					else
					{
						localWebStatusReasonCodesModel.setCreatedBy(webStatusReasonCodesModel.getCreatedBy());
						localWebStatusReasonCodesModel.setCreatedDate(webStatusReasonCodesModel.getCreatedDate());
					}
					localWebStatusReasonCodesModel.setModifiedBy(userName);
					localWebStatusReasonCodesModel.setModifiedDate(new Date());
					log.debug("Web Model: " +  localWebStatusReasonCodesModel);
					
					StatusReasonCodesModel statusReasonCodesModel = new WebAdminTranslator().translateWebStatusReasonCodesModelToCommonsModel(localWebStatusReasonCodesModel);
					log.debug("Commons Model: " +  statusReasonCodesModel);
					results = controller.createStatusReasonCodes(statusReasonCodesModel);
			        log.debug("===============================originalWebStatusReasonCodesModel old value =================:"+originalWebStatusReasonCodesModel);
			        log.debug("===============================localWebStatusReasonCodesModel new value=================:"+localWebStatusReasonCodesModel);
			        if(originalWebStatusReasonCodesModel != null)
			        {
			        	 if(originalWebStatusReasonCodesModel.getStatusReasonCodeDescription()!= localWebStatusReasonCodesModel.getStatusReasonCodeDescription())
							{
								webAuditTrackingModel.setOldValue(originalWebStatusReasonCodesModel.getStatusReasonCodeDescription());
								webAuditTrackingModel.setNewValue(localWebStatusReasonCodesModel.getStatusReasonCodeDescription());
							}
							if(originalWebStatusReasonCodesModel.getActiveInd()!= localWebStatusReasonCodesModel.getActiveInd())
							{
								webAuditTrackingModel.setOldValue(originalWebStatusReasonCodesModel.getActiveInd());
								webAuditTrackingModel.setNewValue(localWebStatusReasonCodesModel.getActiveInd());
							}
			        }
			        else
			        {
			        	webAuditTrackingModel.setNewValue(localWebStatusReasonCodesModel.getStatusReasonCodeDescription());
			        	webAuditTrackingModel.setNewValue(localWebStatusReasonCodesModel.getActiveInd());
			        }
			        webAuditTrackingModel.setColumnName(statusReasDescLabel.getDefaultModelObjectAsString());
			        webAuditTrackingModel.setColumnName(activeIndLabel.getDefaultModelObjectAsString());
					webAuditTrackingModel.setRecordId(new BigDecimal(123));		
					webAuditTrackingModel.setAction(action.toUpperCase());
					webAuditTrackingModel.setUserId(userName);
					webAuditTrackingModel.setActionDate(new Date());
					webAuditTrackingModel.setTableName(screenName);
					
					AuditTrackingModel	 auditTrackingModel = new WebAdminTranslator().translateWebAuditTrackingModelToCommonsModel(webAuditTrackingModel);
					saved =controller.createAuditTracking(auditTrackingModel);
					if(results)
					{
						if(action == "create")
						{
							log.debug("in the create method");
							error("Status ReasonCodes Created Successfully.");
						}
						else
						{
							info("Status ReasonCodes Updated Successfully.");
						}
						viewStatusReasonCodesPanel = new ViewStatusReasonCodesPanel(id);
						MarkupContainer markupContainer = form.getParent().getParent();
						markupContainer.remove(form.getParent());
						markupContainer.add(viewStatusReasonCodesPanel);
						viewStatusReasonCodesPanel.setOutputMarkupId(true);
						viewStatusReasonCodesPanel.setOutputMarkupPlaceholderTag(true);
						TemplatePage.setContent(viewStatusReasonCodesPanel);
						log.debug(statusReasonCodesModel);
					}
					else
					{
						error("Status ReasonCodes could not be added/Updated...");
					}
					
				} catch (Exception exception)
				{
					error ("Add/Update failed, " + exception.getMessage());
					log.error("Add/Update failed, ", exception );
				}	
			}
		};	
					
		cancelButton = new Button("cancelButton")
		{
			private static final long serialVersionUID = 1L;
			
			@Override
			public void onSubmit()
			{
				viewStatusReasonCodesPanel = new ViewStatusReasonCodesPanel(id);
				MarkupContainer markupContainer = form.getParent().getParent();
				markupContainer.remove(form.getParent());
				markupContainer.add(viewStatusReasonCodesPanel);
				viewStatusReasonCodesPanel.setOutputMarkupId(true);
				viewStatusReasonCodesPanel.setOutputMarkupPlaceholderTag(true);
				TemplatePage.setContent(viewStatusReasonCodesPanel);
			}
		};
		cancelButton.setDefaultFormProcessing(false);
		cancelButton.setEnabled(true);
		
		form.add(saveOrUpdateButton);
		form.add(cancelButton);
		add(form);
		FeedbackPanel feedbackPanel = new FeedbackPanel("feedback_1");
		add(feedbackPanel);				
	}	

}