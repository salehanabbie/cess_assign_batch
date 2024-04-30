package com.bsva.panels.SodEodScreens;

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
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.validation.validator.PatternValidator;
import org.apache.wicket.validation.validator.StringValidator;

import com.bsva.TemplatePage;
import com.bsva.commons.model.AuditTrackingModel;
import com.bsva.commons.model.OpsSlaTimesCommonsModel;
import com.bsva.controller.Controller;
import com.bsva.models.ClientSessionModel;
import com.bsva.models.WebAuditTrackingModel;
import com.bsva.models.WebOpsSlaTimesModel;
import com.bsva.translator.WebAdminTranslator;

/***
 * 
 * @author DimakatsoN
 *
 */

public class MaintainOpsSlaTimesPanel extends Panel implements Serializable
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static Logger log = Logger.getLogger(MaintainOpsSlaTimesPanel.class);
	private ClientSessionModel clientSessionModel;
	private String userName;
	public static Session session;
	private static String id;
	Label service,time01,time02;
	private TextField<String> serviceText;
	private TextField<String> startTime;
	private TextField<String> endTime;
	private String action;
	String textValidator = "([a-zA-Z0-9 \\-._()]+)";   
	private WebOpsSlaTimesModel webOpsSlaTimesModel,originalWebOpsSlaTimesModel;
	private Form form;
	private Button saveOrUpdateButton;
	private Button cancelButton;
	ViewOpsSlaTimesPanel viewOpsSlaTimesPanel;
	WebAuditTrackingModel webAuditTrackingModel;
	private String screenName = " OPS SLA Times";



	
	public MaintainOpsSlaTimesPanel(String id,String action) {
		super(id);
		this.id = id;
		this.action = action;
		initialiazeComponents();
	}

	public MaintainOpsSlaTimesPanel (String id, WebOpsSlaTimesModel webOpsSlaTimesModel, String action)
	{
		super(id);
		this.action = action;
		this.id = id;
		this.webOpsSlaTimesModel = webOpsSlaTimesModel;
		this.originalWebOpsSlaTimesModel = webOpsSlaTimesModel;
		initialiazeComponents();
		log.debug("Screen ID:" + action);
		
	}

	private void initialiazeComponents() {
		form = new Form("MaintainOpsSlaTimesPanel");

		service = new Label("label1","Service");
		 form.add(service);
		 
		 time01 = new Label("label2","Start Time");
		 form.add(time01);
		 
		 time02 = new Label("label3","End Time");
		 form.add(time02);
		 
			serviceText =  new TextField<String>("serviceText", new Model<String>(webOpsSlaTimesModel == null ? "" : webOpsSlaTimesModel.getService()));
			serviceText.setRequired(true);
			serviceText.add(StringValidator.maximumLength(5));
			serviceText.add(new PatternValidator(textValidator));
			
				if(action == "update")
				{
					serviceText.setEnabled(false);
				}
		        form.add(serviceText);
			
		        startTime =  new TextField<String>("startTime", new Model<String>(webOpsSlaTimesModel == null ? "" : webOpsSlaTimesModel.getStartTime()));
		        startTime.setRequired(true);
		        startTime.add(StringValidator.maximumLength(6));
		      //  time01Text.add(new PatternValidator(textValidator));
		        form.add(startTime);
		        
		        endTime =  new TextField<String>("endTime", new Model<String>(webOpsSlaTimesModel == null ? "" : webOpsSlaTimesModel.getEndTime()));
		        endTime.setRequired(true);
		        endTime.add(StringValidator.maximumLength(6));
		        //time02Text.add(new PatternValidator(textValidator));
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
					WebOpsSlaTimesModel localWebOpsSlaTimesModel= null;
					webAuditTrackingModel = new WebAuditTrackingModel();
					
					session = getSession();
			        clientSessionModel = (ClientSessionModel) session.getAttribute("role");
			        
			        userName = clientSessionModel.getUsername();
			        
					if (action.equals("update"))
					{
						localWebOpsSlaTimesModel = new WebOpsSlaTimesModel(webOpsSlaTimesModel.getService());
					} else 																					
					{
						localWebOpsSlaTimesModel = new WebOpsSlaTimesModel();
					}
					
					localWebOpsSlaTimesModel.setService(serviceText.getValue().toUpperCase());
					localWebOpsSlaTimesModel.setStartTime(startTime.getValue());
					localWebOpsSlaTimesModel.setEndTime(endTime.getValue());
					
					
					/*if(action.equalsIgnoreCase("create"))
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
					*/
					log.info("Web Model: " +  localWebOpsSlaTimesModel);
					
					OpsSlaTimesCommonsModel opsSlaTimesModel = new WebAdminTranslator().translateWebOpsSlaTimesModelToCommonsModel(localWebOpsSlaTimesModel);
					log.info("Commons Model: " +  opsSlaTimesModel);
					Controller controller = new Controller();
					results = controller.createCnfgOpsSlaTimes(opsSlaTimesModel);
					log.info("results -->" +results);
					
					//Check what has changed.
			        
/*			        log.info("======================webAmendmentCodesModel=================:"+webAmendmentCodesModel);
			        log.info("======================originaWebAmendmentCodesModel=================:"+localWebAmendmentCodesModel);*/
			        
			        if(originalWebOpsSlaTimesModel != null)
			        {
			        		if(originalWebOpsSlaTimesModel.getStartTime() != localWebOpsSlaTimesModel.getStartTime())
							{
								webAuditTrackingModel.setOldValue(originalWebOpsSlaTimesModel.getStartTime());
								webAuditTrackingModel.setNewValue(localWebOpsSlaTimesModel.getStartTime());
							}
								
							if(originalWebOpsSlaTimesModel.getEndTime() != localWebOpsSlaTimesModel.getEndTime())
							{
								webAuditTrackingModel.setOldValue(originalWebOpsSlaTimesModel.getEndTime());
								webAuditTrackingModel.setNewValue(localWebOpsSlaTimesModel.getEndTime());
							}
			        }
			        else
			        {
			        	webAuditTrackingModel.setNewValue(localWebOpsSlaTimesModel.getStartTime());
			        	webAuditTrackingModel.setNewValue(localWebOpsSlaTimesModel.getEndTime());
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
							viewOpsSlaTimesPanel = new ViewOpsSlaTimesPanel(id);
							MarkupContainer markupContainer = form.getParent().getParent();
							markupContainer.remove(form.getParent());
							markupContainer.add(viewOpsSlaTimesPanel);
							viewOpsSlaTimesPanel.setOutputMarkupId(true);
							viewOpsSlaTimesPanel.setOutputMarkupPlaceholderTag(true);
							TemplatePage.setContent(viewOpsSlaTimesPanel);
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
			viewOpsSlaTimesPanel = new ViewOpsSlaTimesPanel(id);
			MarkupContainer markupContainer = form.getParent().getParent();
			markupContainer.remove(form.getParent());
			markupContainer.add(viewOpsSlaTimesPanel);
			viewOpsSlaTimesPanel.setOutputMarkupId(true);
			viewOpsSlaTimesPanel.setOutputMarkupPlaceholderTag(true);
			TemplatePage.setContent(viewOpsSlaTimesPanel);
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


	
