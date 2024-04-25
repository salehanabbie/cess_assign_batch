package com.bsva.panels.mandateServices;

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
import com.bsva.commons.model.SystemControlServicesModel;
import com.bsva.controller.Controller;
import com.bsva.models.ClientSessionModel;
import com.bsva.models.WebAuditTrackingModel;
import com.bsva.models.WebSystemControlServicesModel;
import com.bsva.translator.WebAdminTranslator;
import com.bsva.validators.TextFieldValidator;

public class MaintainSystemControlServicesPanel extends Panel
{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static Logger log = Logger.getLogger(MaintainSystemControlServicesPanel.class);
	
	
	private String action;
	private static String id;
	
	private String screenName = "System Control Service";
	private ClientSessionModel clientSessionModel;
	private String userName;
	public static Session session;
	
	
	private TextField<String> serviceIdIn;
	private TextField<String> serviceIdInDesc;
	private TextField<String> serviceIdOut;
	private TextField<String> serviceIdOutDesc;
	private TextField<String> recordId;
	private DropDownChoice<String> activeInd;
	
	
	private Button saveOrUpdateButton;
	private Button cancelButton;
	
	private String selectedIndicator;
	
	Label serviceIdInLabel, serviceIdOutLabel,serviceIdInDescLabel,serviceIdOutDescLabel,recordIdLabel,activeIndLabel, legendLbl;

	private WebSystemControlServicesModel webSystemControlServicesModel,originaWebSystemControlServicesModel;
	WebAuditTrackingModel webAuditTrackingModel;
	
	
	ViewSystemControlServicesPanel viewSystemControlServicesPanel;
	
    private Form form;
    
    String textValidator = "([a-zA-Z0-9 \\-._()]+)";
    
    public MaintainSystemControlServicesPanel(String id, String action) 
    {	
		super(id);
		this.id = id;
		this.action = action;
		initialiazeComponents();
	}

    public MaintainSystemControlServicesPanel (String id, WebSystemControlServicesModel webSystemControlServicesModel, String action)
	{
		super(id);
		this.action = action;
		this.id = id;
		this.webSystemControlServicesModel = webSystemControlServicesModel;
		this.originaWebSystemControlServicesModel = webSystemControlServicesModel;
		initialiazeComponents();
		log.debug("Screen ID:" + action);
		
	}
    
    
	private void initialiazeComponents() 
	{
		legendLbl = new Label("legend","Services  - "+action.toUpperCase());
		add(legendLbl);
			form =  new Form("maintainSystemControlServiceForm");
		
			serviceIdInLabel = new Label("label1","Service Id IN");
			form.add(serviceIdInLabel);
			
			serviceIdInDescLabel = new Label("label3","Service Id IN Description");
			form.add(serviceIdInDescLabel);
		
			serviceIdOutLabel = new Label("label2","Service Id OUT");
			form.add(serviceIdOutLabel);
		
			serviceIdOutDescLabel= new Label("label4","Service Id OUT Description");
			form.add(serviceIdOutDescLabel);
		
//			recordIdLabel = new Label("label5","Record ID");
//			form.add(recordIdLabel);
		
			activeIndLabel=new Label("label6","Active Indicator");
			form.add(activeIndLabel);
		  
//			 recordId = new TextField<String>("recordId", new Model<String>(webSystemControlServicesModel == null ? "" : webSystemControlServicesModel.getRecordId().toString()));
//			 recordId.setRequired(true);
//			 recordId.add(StringValidator.maximumLength(38));
//			 recordId.add(new TextFieldValidator());
//			
//			if(action == "update")
//			{
//				recordId.setEnabled(false);
//			}
//			 form.add(recordId);	
			
			serviceIdIn = new TextField<String>("serviceIdIn", new Model<String>(webSystemControlServicesModel == null ? "" : webSystemControlServicesModel.getServiceIdIn()));
	     	serviceIdIn.setRequired(true);
		 	serviceIdIn.add(StringValidator.maximumLength(5));
		 	serviceIdIn.add(new PatternValidator(textValidator));
		 //   serviceIdIn.add(new TextFieldValidator());
			
			form.add(serviceIdIn);
			
			serviceIdInDesc = new TextField<String>("serviceIdInDesc", new Model<String>(webSystemControlServicesModel == null ? "" : webSystemControlServicesModel.getServiceIdInDesc()));
			serviceIdInDesc.setRequired(true);
			serviceIdInDesc.add(StringValidator.maximumLength(75));
			serviceIdInDesc.add(new PatternValidator(textValidator));
			//serviceIdInDesc.add(new TextFieldValidator());
			form.add(serviceIdInDesc);
		 
			serviceIdOut = new TextField<String>("serviceIdOut", new Model<String>(webSystemControlServicesModel == null ? "" : webSystemControlServicesModel.getServiceIdOut()));
			serviceIdOut.setRequired(true);
			serviceIdOut.add(StringValidator.maximumLength(5));
			serviceIdOut.add(new PatternValidator(textValidator));
		//	serviceIdOut.add(new TextFieldValidator());
			form.add(serviceIdOut);
			
			
			
			serviceIdOutDesc = new TextField<String>("serviceIdOutDesc", new Model<String>(webSystemControlServicesModel == null ? "" : webSystemControlServicesModel.getServiceIdOutDesc()));
			serviceIdOutDesc.setRequired(true);
			serviceIdOutDesc.add(StringValidator.maximumLength(75));
			serviceIdOutDesc.add(new PatternValidator(textValidator));
		//	serviceIdOutDesc.add(new TextFieldValidator());
			form.add(serviceIdOutDesc);
			
			List<String> indicators = new ArrayList<String>();
			indicators.add("ACTIVE");
			indicators.add("INACTIVE");
			
			if(webSystemControlServicesModel != null)
			{
				if(webSystemControlServicesModel.getActiveInd() != null && webSystemControlServicesModel.getActiveInd().equals("Y"))
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
			
			if(action.equalsIgnoreCase("create"))
			{
				activeInd.setEnabled(false);
			}
			form.add(activeInd);
		
			saveOrUpdateButton = new Button("saveButton")
			{
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
					
				public void onSubmit()
				{
					try {
						boolean results = false;
						boolean saved = false;                             
						WebSystemControlServicesModel localWebModel= null;
						webAuditTrackingModel = new WebAuditTrackingModel();
						
						session = getSession();
				        clientSessionModel = (ClientSessionModel) session.getAttribute("role");
				        
				        userName = clientSessionModel.getUsername();

				        
						if (action.equals("update"))
						{
							localWebModel = webSystemControlServicesModel;
						} else 																					
						{
							localWebModel = new WebSystemControlServicesModel();
							localWebModel.setRecordId(new BigDecimal(123));
						}
						
						
						localWebModel.setServiceIdIn(serviceIdIn.getValue());
						localWebModel.setServiceIdInDesc(serviceIdInDesc.getValue());
						localWebModel.setServiceIdOut(serviceIdOut.getValue());
						localWebModel.setServiceIdOutDesc(serviceIdOutDesc.getValue());
//						localWebModel.setRecordId(new BigDecimal (recordId.getValue()));
						localWebModel.setActiveInd(activeInd.getModelObject() == "ACTIVE" ? "Y" : "N");
						if(action.equalsIgnoreCase("create"))
						{
							localWebModel.setCreatedBy(userName);
							localWebModel.setCreatedDate(new Date());
						}
						else
						{
							localWebModel.setCreatedBy(webSystemControlServicesModel.getCreatedBy());
							localWebModel.setCreatedDate(webSystemControlServicesModel.getCreatedDate());
						}
						
						localWebModel.setModifiedBy(userName);
						localWebModel.setModifiedDate(new Date());
						
						log.info("Web Model: " +  localWebModel);
						
						SystemControlServicesModel systemControlServicesModel = new WebAdminTranslator().translateWebSystemControlServicesModelToCommonsModel(localWebModel);
						
						log.info("Commons Model: " +  systemControlServicesModel);
						Controller controller = new Controller();
						results = controller.createRecordId1(systemControlServicesModel);
						
	
												
						//Check what has changed.
				        
				        log.info("===============================originaWebSystemControlServicesModel old value =================:"+originaWebSystemControlServicesModel);
				        log.info("===============================localWebModel new value=================:"+localWebModel);
				        
				        if(originaWebSystemControlServicesModel != null)
				        {
				        	if(originaWebSystemControlServicesModel.getServiceIdIn()!= localWebModel.getServiceIdIn())
							{
								webAuditTrackingModel.setOldValue(originaWebSystemControlServicesModel.getServiceIdIn());
								webAuditTrackingModel.setNewValue(localWebModel.getServiceIdIn());
							}
							
							if(originaWebSystemControlServicesModel.getServiceIdOut()!= localWebModel.getServiceIdOut())
							{
								webAuditTrackingModel.setOldValue(originaWebSystemControlServicesModel.getServiceIdOut());
								webAuditTrackingModel.setNewValue(localWebModel.getServiceIdOut());
							}
						
							if(originaWebSystemControlServicesModel.getServiceIdInDesc()!= localWebModel.getServiceIdInDesc())
							{
								webAuditTrackingModel.setOldValue(originaWebSystemControlServicesModel.getServiceIdInDesc());
								webAuditTrackingModel.setNewValue(localWebModel.getServiceIdInDesc());
							}
							
							if(originaWebSystemControlServicesModel.getServiceIdOutDesc()!= localWebModel.getServiceIdOutDesc())
							{
								webAuditTrackingModel.setOldValue(originaWebSystemControlServicesModel.getServiceIdOutDesc());
								webAuditTrackingModel.setNewValue(localWebModel.getServiceIdOutDesc());
							}

						
							if(originaWebSystemControlServicesModel.getActiveInd()!= localWebModel.getActiveInd())
								{
								webAuditTrackingModel.setOldValue(originaWebSystemControlServicesModel.getActiveInd());
								webAuditTrackingModel.setNewValue(localWebModel.getActiveInd());
								}
				        }
				        else
				        {
				        	webAuditTrackingModel.setNewValue(localWebModel.getServiceIdIn());
				        	webAuditTrackingModel.setNewValue(localWebModel.getServiceIdOut());
				        	webAuditTrackingModel.setNewValue(localWebModel.getServiceIdInDesc());
				        	webAuditTrackingModel.setNewValue(localWebModel.getServiceIdOutDesc());
				        	webAuditTrackingModel.setNewValue(localWebModel.getActiveInd());
				        }
				        
				        	webAuditTrackingModel.setColumnName(serviceIdInLabel.getDefaultModelObjectAsString());
				        	webAuditTrackingModel.setColumnName(serviceIdOutLabel.getDefaultModelObjectAsString());
				        	webAuditTrackingModel.setColumnName(serviceIdInDescLabel.getDefaultModelObjectAsString());
				        	webAuditTrackingModel.setColumnName(serviceIdOutDescLabel.getDefaultModelObjectAsString());
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
								log.info("in the create method");
								error("System Control Services Created Successfully.");
							}
							else
							{
								info("System Control Services Updated Successfully.");
							}
						//	info("System Control Services Added/Updated...");
							viewSystemControlServicesPanel = new ViewSystemControlServicesPanel(id);
							MarkupContainer markupContainer = form.getParent().getParent();
							markupContainer.remove(form.getParent());
							markupContainer.add(viewSystemControlServicesPanel);
							viewSystemControlServicesPanel.setOutputMarkupId(true);
							viewSystemControlServicesPanel.setOutputMarkupPlaceholderTag(true);
							TemplatePage.setContent(viewSystemControlServicesPanel);
							log.debug(systemControlServicesModel);
						}else
						{
							error("System Control Services could not be added/Updated...");
						}
					}
					catch (Exception exception)
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
					viewSystemControlServicesPanel = new ViewSystemControlServicesPanel(id);
					MarkupContainer markupContainer = form.getParent().getParent();
					markupContainer.remove(form.getParent());
					markupContainer.add(viewSystemControlServicesPanel);
					viewSystemControlServicesPanel.setOutputMarkupId(true);
					viewSystemControlServicesPanel.setOutputMarkupPlaceholderTag(true);
					TemplatePage.setContent(viewSystemControlServicesPanel);
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
