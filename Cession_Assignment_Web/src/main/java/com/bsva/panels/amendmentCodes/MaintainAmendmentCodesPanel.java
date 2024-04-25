package com.bsva.panels.amendmentCodes;
import java.io.Serializable;
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
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.validation.validator.PatternValidator;
import org.apache.wicket.validation.validator.StringValidator;
import com.bsva.TemplatePage;
import com.bsva.commons.model.AccountTypeModel;
import com.bsva.commons.model.AmendmentCodesModel;
import com.bsva.commons.model.AuditTrackingModel;
import com.bsva.controller.Controller;
import com.bsva.models.ClientSessionModel;
import com.bsva.models.WebAccountTypeModel;
import com.bsva.models.WebAmendmentCodesModel;
import com.bsva.models.WebAuditTrackingModel;
import com.bsva.translator.WebAdminTranslator;
import com.bsva.validators.TextFieldValidator;

/**
 * @author SalehaR
 *
 */
public class MaintainAmendmentCodesPanel extends Panel implements Serializable
{
	private static final long serialVersionUID = 1L;
	public static Logger log = Logger.getLogger(MaintainAmendmentCodesPanel.class);
	private DropDownChoice<String> activeInd;
	private String action;
	private static String id;
	private String screenName;
	private ClientSessionModel clientSessionModel;
	private String userName;
	public static Session session;
	private TextField<String> amendmentCodes;
	private TextField<String> amendmentCodesDescription;
	private Button saveOrUpdateButton;
	private Button cancelButton;
	private String selectedIndicator;
	Label amdCode,amdDesc,activeIndLabel,legendLbl;
	private WebAmendmentCodesModel webAmendmentCodesModel,originaWebAmendmentCodesModel;
	WebAuditTrackingModel webAuditTrackingModel;
	ViewAmendmentCodesPanel viewAmendmentCodesPanel;
    private Form form;
    String textValidator = "([a-zA-Z0-9 \\-._()]+)";   
    Controller controller;

	public MaintainAmendmentCodesPanel(String id, String action)
	{
		super(id);
		this.id = id;
		this.action = action;
		initialiazeComponents();
	}

	public MaintainAmendmentCodesPanel (String id, WebAmendmentCodesModel webAmendmentCodesModel, String action)
	{
		super(id);
		this.action = action;
		this.id = id;
		this.webAmendmentCodesModel = webAmendmentCodesModel;
		this.originaWebAmendmentCodesModel = webAmendmentCodesModel;
		initialiazeComponents();
		log.debug("Screen ID:" + action);
	}

	private void initialiazeComponents() 
	{
		controller = new Controller();
		legendLbl = new Label("legend","Amendment Codes  - "+action.toUpperCase());
		add(legendLbl);
		form = new Form("MaintainAmendmentCodesPanel");
		
		amdCode = new Label("label1","Amendment Codes");
		form.add(amdCode);
		 
		amdDesc = new Label("label2","Amendment Codes Description");
		form.add(amdDesc);
		 
		activeIndLabel = new Label("label3","Active Indicator ");
		form.add(activeIndLabel);
		
		amendmentCodes =  new TextField<String>("amendmentCodes", new Model<String>(webAmendmentCodesModel == null ? "" : webAmendmentCodesModel.getAmendmentCodes()));
		amendmentCodes.setRequired(true);
		amendmentCodes.add(StringValidator.maximumLength(4));
		amendmentCodes.add(new PatternValidator(textValidator));
		if(action == "update")
		{
			amendmentCodes.setEnabled(false);
		}
	    form.add(amendmentCodes);
		
	    amendmentCodesDescription =  new TextField<String>("amendmentCodesDescription", new Model<String>(webAmendmentCodesModel == null ? "" : webAmendmentCodesModel.getAmendmentCodesDescription()));
	    amendmentCodesDescription.setRequired(true);
	    amendmentCodesDescription.add(StringValidator.maximumLength(35));
	    amendmentCodesDescription.add(new PatternValidator(textValidator));
	    form.add(amendmentCodesDescription);
	        
	    List<String> indicators = new ArrayList<String>();
	    indicators.add("ACTIVE");
		indicators.add("INACTIVE");	
		if (webAmendmentCodesModel != null) 
		{
			if (webAmendmentCodesModel.getActiveInd() != null && webAmendmentCodesModel.getActiveInd().equals("Y"))
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
					WebAmendmentCodesModel localWebAmendmentCodesModel= null;
					webAuditTrackingModel = new WebAuditTrackingModel();
					session = getSession();
				    clientSessionModel = (ClientSessionModel) session.getAttribute("role"); 
				    userName = clientSessionModel.getUsername();
					if (action.equals("update"))
					{
						localWebAmendmentCodesModel = new WebAmendmentCodesModel(webAmendmentCodesModel.getAmendmentCodes());
					} 
					else 																					
					{
						localWebAmendmentCodesModel = new WebAmendmentCodesModel();
					}
					localWebAmendmentCodesModel.setAmendmentCodes(amendmentCodes.getValue().toUpperCase());
					String acctTypDsc = StringUtils.capitalize(amendmentCodesDescription.getValue());
					localWebAmendmentCodesModel.setAmendmentCodesDescription(acctTypDsc);	
					localWebAmendmentCodesModel.setActiveInd(activeInd.getModelObject() == "ACTIVE" ? "Y" : "N");	
					if(action.equalsIgnoreCase("create"))
					{
						localWebAmendmentCodesModel.setCreatedBy(userName);
						localWebAmendmentCodesModel.setCreatedDate(new Date());
					}
					else
					{
						localWebAmendmentCodesModel.setCreatedBy(webAmendmentCodesModel.getCreatedBy());
						localWebAmendmentCodesModel.setCreatedDate(webAmendmentCodesModel.getCreatedDate());
					}
					localWebAmendmentCodesModel.setModifiedBy(userName);
					localWebAmendmentCodesModel.setModifiedDate(new Date());	
					log.debug("Web Model: " +  localWebAmendmentCodesModel);
						
					AmendmentCodesModel amendmentCodesModel = new WebAdminTranslator().translateWebAmendmentCodesModelToCommonsModel(localWebAmendmentCodesModel);
					log.debug("Commons Model: " +  amendmentCodesModel);
					results = controller.createAmendmentCodes(amendmentCodesModel);
					log.debug("results -->" +results);
				        
				    log.debug("======================webAmendmentCodesModel=================:"+webAmendmentCodesModel);
				    log.debug("======================originaWebAmendmentCodesModel=================:"+localWebAmendmentCodesModel); 
				    if(originaWebAmendmentCodesModel != null)
				    {
				       	if(originaWebAmendmentCodesModel.getAmendmentCodesDescription() != localWebAmendmentCodesModel.getAmendmentCodesDescription())
						{
							webAuditTrackingModel.setOldValue(originaWebAmendmentCodesModel.getAmendmentCodesDescription());
							webAuditTrackingModel.setNewValue(localWebAmendmentCodesModel.getAmendmentCodesDescription());
						}			
						if(originaWebAmendmentCodesModel.getActiveInd() != localWebAmendmentCodesModel.getActiveInd())
						{
							webAuditTrackingModel.setOldValue(originaWebAmendmentCodesModel.getActiveInd());
							webAuditTrackingModel.setNewValue(localWebAmendmentCodesModel.getActiveInd());
						}
				    }
				    else
				    {
				       	webAuditTrackingModel.setNewValue(localWebAmendmentCodesModel.getAmendmentCodesDescription());
				       	webAuditTrackingModel.setNewValue(localWebAmendmentCodesModel.getActiveInd());
				    }
				    webAuditTrackingModel.setColumnName(activeIndLabel.getDefaultModelObjectAsString());
				    webAuditTrackingModel.setColumnName(amdDesc.getDefaultModelObjectAsString());				
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
							error("Amendment Codes Created Successfully.");
						}
						else
						{
							info("Amendment Codes Updated Successfully.");
						}
						viewAmendmentCodesPanel = new ViewAmendmentCodesPanel(id);
						MarkupContainer markupContainer = form.getParent().getParent();
						markupContainer.remove(form.getParent());
						markupContainer.add(viewAmendmentCodesPanel);
						viewAmendmentCodesPanel.setOutputMarkupId(true);
						viewAmendmentCodesPanel.setOutputMarkupPlaceholderTag(true);
						TemplatePage.setContent(viewAmendmentCodesPanel);
						log.debug(amendmentCodesModel);	
					}
					else
					{
						error("Amendment Codes could not be added/Updated...");
					}
				}
				catch (Exception exception)
				{
					error ("Add/Update failed, " + exception.getMessage());
					log.error("Add/Update failed, ", exception );
					exception.printStackTrace();
				}	
			}
		};
		
		cancelButton = new Button("cancelButton")
		{
			private static final long serialVersionUID = 1L;
		
			@Override
			public void onSubmit() 
			{
				viewAmendmentCodesPanel = new ViewAmendmentCodesPanel(id);
				MarkupContainer markupContainer = form.getParent().getParent();
				markupContainer.remove(form.getParent());
				markupContainer.add(viewAmendmentCodesPanel);
				viewAmendmentCodesPanel.setOutputMarkupId(true);
				viewAmendmentCodesPanel.setOutputMarkupPlaceholderTag(true);
				TemplatePage.setContent(viewAmendmentCodesPanel);
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
