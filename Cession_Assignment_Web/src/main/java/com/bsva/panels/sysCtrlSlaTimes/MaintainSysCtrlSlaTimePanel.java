package com.bsva.panels.sysCtrlSlaTimes;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.Session;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.validation.validator.PatternValidator;
import org.apache.wicket.validation.validator.StringValidator;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;

import com.bsva.TemplatePage;
import com.bsva.commons.model.AuditTrackingModel;
import com.bsva.commons.model.SysCtrlSlaTimeModel;
import com.bsva.controller.Controller;
import com.bsva.models.ClientSessionModel;
import com.bsva.models.WebAuditTrackingModel;
import com.bsva.models.WebSysCtrlSlaTimeModel;
import com.bsva.translator.WebAdminTranslator;
import com.bsva.validators.FieldNumberTextFieldValidator;

/**
 * 
 * @author DimakatsoN
 *
 */

public class MaintainSysCtrlSlaTimePanel extends Panel implements Serializable {



	private static final long serialVersionUID = 1L;
	public static Logger log = Logger.getLogger(MaintainSysCtrlSlaTimePanel.class);
	private ClientSessionModel clientSessionModel;
	private String userName;
	public static Session session;
	private static String id;
	Label service,time01,time02;
	private TextField<String> serviceText;
	private TextField<String> startTime;
	private TextField<String> endTime;
	private String action;
	String timeValidator = "([01]?[0-9]|2[0-3]):[0-5][0-9]";   
	private WebSysCtrlSlaTimeModel webSysCtrlSlaTimeModel,originalWebSysCtrlSlaTimeModel;
	private Form form;
	private Button saveOrUpdateButton;
	private Button cancelButton;
	ViewSysCtrlSlaTimePanel viewSysCtrlSlaTimePanel;
	WebAuditTrackingModel webAuditTrackingModel;
	private String screenName = "SLA Times";
	Controller controller;
	private Label legendLbl; 

	public MaintainSysCtrlSlaTimePanel(String id,String action) {
		super(id);
		this.id = id;
		this.action = action;
		initialiazeComponents();
	}

	public MaintainSysCtrlSlaTimePanel (String id, WebSysCtrlSlaTimeModel webSysCtrlSlaTimeModel, String action)
	{
		super(id);
		this.action = action;
		this.id = id;
		this.webSysCtrlSlaTimeModel = webSysCtrlSlaTimeModel;
		this.originalWebSysCtrlSlaTimeModel = webSysCtrlSlaTimeModel;
		initialiazeComponents();
		log.debug("Screen ID:" + action);
		log.info("webSysCtrlSlaTimeModel ==> "+ webSysCtrlSlaTimeModel);
	}
	


	private void initialiazeComponents() 
	{
		controller = new Controller();
		form = new Form("maintainSysCtrlSlaTimePanel");
		legendLbl = new Label("legend", "SLA Times - " +action.toUpperCase());
		add(legendLbl);
		service = new Label("label1","Service");
		form.add(service);
		 
		time01 = new Label("label2","Start Time");
		form.add(time01);
		 
		time02 = new Label("label3","End Time");
		form.add(time02);
		 
			serviceText =  new TextField<String>("serviceText", new Model<String>(webSysCtrlSlaTimeModel == null ? "" : webSysCtrlSlaTimeModel.getService()));
			serviceText.setRequired(true);
			serviceText.add(StringValidator.maximumLength(5));
			//serviceText.add(new PatternValidator(textValidator));
			
				if(action == "update")
				{
					serviceText.setEnabled(false);
				}
		        form.add(serviceText);
			
		        startTime =  new TextField<String>("startTime", new Model<String>(webSysCtrlSlaTimeModel == null ? "" : webSysCtrlSlaTimeModel.getStartTime()));
		        startTime.setRequired(true);
		        startTime.add(StringValidator.maximumLength(6));
		        startTime.add(new PatternValidator(timeValidator));
				
		        form.add(startTime);
		        
		        endTime =  new TextField<String>("endTime", new Model<String>(webSysCtrlSlaTimeModel == null ? "" : webSysCtrlSlaTimeModel.getEndTime()));
		        endTime.setRequired(true);
		        endTime.add(StringValidator.maximumLength(6));
		        endTime.add(new PatternValidator(timeValidator));
				
		        form.add(endTime);
		        
		 saveOrUpdateButton = new Button("saveOrUpdateButton")
		{
			private static final long serialVersionUID = 1L;
		
			@Override
			public void onSubmit()
			{
				try 
				{
					boolean results = false;
					boolean saved = false;
					WebSysCtrlSlaTimeModel localWebSysCtrlSlaTimeModel= null;
					webAuditTrackingModel = new WebAuditTrackingModel();
					
					session = getSession();
			        clientSessionModel = (ClientSessionModel) session.getAttribute("role");
			        
			        userName = clientSessionModel.getUsername();
			        
					if (action.equals("update"))
					{
						localWebSysCtrlSlaTimeModel = new WebSysCtrlSlaTimeModel(webSysCtrlSlaTimeModel.getService());
					} else 																					
					{
						localWebSysCtrlSlaTimeModel = new WebSysCtrlSlaTimeModel();
					}
					
					localWebSysCtrlSlaTimeModel.setService(serviceText.getValue().toUpperCase());
					localWebSysCtrlSlaTimeModel.setStartTime(startTime.getValue());
					localWebSysCtrlSlaTimeModel.setEndTime(endTime.getValue());
					
				
					if(action.equalsIgnoreCase("create"))
					{
						localWebSysCtrlSlaTimeModel.setCreatedBy(userName);
						localWebSysCtrlSlaTimeModel.setCreatedDate(new Date());
					}
					else
					{
						localWebSysCtrlSlaTimeModel.setCreatedBy(webSysCtrlSlaTimeModel.getCreatedBy());
						localWebSysCtrlSlaTimeModel.setCreatedDate(webSysCtrlSlaTimeModel.getCreatedDate());
					}
					
//					localWebSysCtrlSlaTimeModel.setCreatedBy(webSysCtrlSlaTimeModel.getCreatedBy());
//					localWebSysCtrlSlaTimeModel.setCreatedDate(webSysCtrlSlaTimeModel.getCreatedDate());
					localWebSysCtrlSlaTimeModel.setModifiedBy(userName);
					localWebSysCtrlSlaTimeModel.setModifiedDate(new Date());
					
					log.debug("Web Model: " +  localWebSysCtrlSlaTimeModel);
					
					SysCtrlSlaTimeModel sysCtrlSlaTimeModel = new WebAdminTranslator().translateWebSysCtrlSlaTimeModelToCommonsModel(localWebSysCtrlSlaTimeModel);
					log.debug("Commons Model: " +  sysCtrlSlaTimeModel);
					results = controller.createCnfgSysCtrlSlaTimes(sysCtrlSlaTimeModel);
					log.debug("results -->" +results);
					
					//Check what has changed.
			        
/*			        log.info("======================webAmendmentCodesModel=================:"+webAmendmentCodesModel);
			        log.info("======================originaWebAmendmentCodesModel=================:"+localWebAmendmentCodesModel);*/
			        
			        if(originalWebSysCtrlSlaTimeModel != null)
			        {
			        		if(originalWebSysCtrlSlaTimeModel.getStartTime() != localWebSysCtrlSlaTimeModel.getStartTime())
							{
								webAuditTrackingModel.setOldValue(originalWebSysCtrlSlaTimeModel.getStartTime());
								webAuditTrackingModel.setNewValue(localWebSysCtrlSlaTimeModel.getStartTime());
							}
								
							if(originalWebSysCtrlSlaTimeModel.getEndTime() != localWebSysCtrlSlaTimeModel.getEndTime())
							{
								webAuditTrackingModel.setOldValue(originalWebSysCtrlSlaTimeModel.getEndTime());
								webAuditTrackingModel.setNewValue(localWebSysCtrlSlaTimeModel.getEndTime());
							}
			        }
			        else
			        {
			        	webAuditTrackingModel.setNewValue(localWebSysCtrlSlaTimeModel.getStartTime());
			        	webAuditTrackingModel.setNewValue(localWebSysCtrlSlaTimeModel.getEndTime());
			        }
			        
			        	webAuditTrackingModel.setColumnName(time01.getDefaultModelObjectAsString());
			        	webAuditTrackingModel.setColumnName(time02.getDefaultModelObjectAsString());				
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
								error("	SLA Times Created Successfully.");
							}
							else
							{
								info("SLA Times Updated Successfully.");
							}
							viewSysCtrlSlaTimePanel = new ViewSysCtrlSlaTimePanel(id);
							MarkupContainer markupContainer = form.getParent().getParent();
							markupContainer.remove(form.getParent());
							markupContainer.add(viewSysCtrlSlaTimePanel);
							viewSysCtrlSlaTimePanel.setOutputMarkupId(true);
							viewSysCtrlSlaTimePanel.setOutputMarkupPlaceholderTag(true);
							TemplatePage.setContent(viewSysCtrlSlaTimePanel);
							//log.debug(amendmentCodesModel);
						
					}
					else
					{
						error("SLA TIME could not be added/Updated...");
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
	form.add(saveOrUpdateButton);
	
	
	cancelButton = new Button("cancelButton")
	{

		private static final long serialVersionUID = 1L;

		@Override
		public void onSubmit() 
		{
			viewSysCtrlSlaTimePanel = new ViewSysCtrlSlaTimePanel(id);
			MarkupContainer markupContainer = form.getParent().getParent();
			markupContainer.remove(form.getParent());
			markupContainer.add(viewSysCtrlSlaTimePanel);
			viewSysCtrlSlaTimePanel.setOutputMarkupId(true);
			viewSysCtrlSlaTimePanel.setOutputMarkupPlaceholderTag(true);
			TemplatePage.setContent(viewSysCtrlSlaTimePanel);
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
