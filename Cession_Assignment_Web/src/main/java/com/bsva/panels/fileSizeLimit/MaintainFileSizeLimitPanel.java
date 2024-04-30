package com.bsva.panels.fileSizeLimit;

import com.bsva.TemplatePage;
import com.bsva.commons.model.AuditTrackingModel;
import com.bsva.commons.model.SysCtrlFileSizeLimitModel;
import com.bsva.controller.Controller;
import com.bsva.models.ClientSessionModel;
import com.bsva.models.WebAuditTrackingModel;
import com.bsva.models.WebSysCtrlFileSizeLimitModel;
import com.bsva.translator.WebAdminTranslator;
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
/**
 * 
 * @author DimakatsoN
 *
 */

public class MaintainFileSizeLimitPanel extends Panel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static Logger log = Logger.getLogger(MaintainFileSizeLimitPanel.class);
	public static String id;
	private Form form;
	private String screenName;
	private ClientSessionModel clientSessionModel;
	private String userName;
	public static Session session;
	private Button saveOrUpdateButton;
	private Button cancelButton;
	private TextField<String> memberId;
	private TextField<String> subService;
	private TextField<String> limit;
	ViewFileSizeLimitPanel viewFileSizeLimitPanel;
	private DropDownChoice<String> activeInd;
	private DropDownChoice<String> direction;
	Label memberIdLabel,subServiceLabel,activeIndLabel,legendLbl,directionLabel,limitLabel;
	private String selectedIndicator;
	private String selectedDirection;
	private String action;
	WebSysCtrlFileSizeLimitModel webSysCtrlFileSizeLimitModel,originalWebSysCtrlFileSizeLimitModell;
	WebAuditTrackingModel webAuditTrackingModel;
	String textValidator = "([a-zA-Z0-9 \\-._()]+)";
	Controller controller;
	

	
	
	
	public MaintainFileSizeLimitPanel(String id, String action) {
		super(id);
		this.id=id;
		this.action = action;
		initializeComponents();
	}
	
	public MaintainFileSizeLimitPanel(String id, WebSysCtrlFileSizeLimitModel webSysCtrlFileSizeLimitModel, String action)
	{
		super(id);
		this.id=id;
		this.action = action;
		this.webSysCtrlFileSizeLimitModel = webSysCtrlFileSizeLimitModel;
		this.originalWebSysCtrlFileSizeLimitModell = webSysCtrlFileSizeLimitModel;
		initializeComponents();
		log.info("Screen ID: "+ action);
	}
	
	private void initializeComponents()
	{
		controller = new Controller();
		form =  new Form("maintainFileSizeLimitPanel");
		screenName = controller.getProperty("MNTN.SCRNNAME");
		
		legendLbl = new Label("legend","File Size Limit  - "+action.toUpperCase());
		add(legendLbl);
		
		
		memberIdLabel= new Label("label1","Member ID");
		form.add(memberIdLabel);
		
		subServiceLabel = new Label("label2","Sub Service");
		form.add(subServiceLabel);
		
		limitLabel=new Label("label3","Limt");
		form.add(limitLabel);
		
		directionLabel=new Label("label4","Direction");
		form.add(directionLabel);
		
		activeIndLabel=new Label("label5","Active Ind");
		form.add(activeIndLabel);
		
		memberId = new TextField<String>("memberId", new Model<String>(webSysCtrlFileSizeLimitModel == null ? "" : webSysCtrlFileSizeLimitModel.getMemberId()));
		memberId.setRequired(true);
		memberId.add(StringValidator.exactLength(6));
		memberId.add(new PatternValidator(textValidator));
		if(action == "update")
		{
			memberId.setEnabled(false);
		}
		form.add(memberId);
		
		subService = new TextField<String>("subService", new Model<String>(webSysCtrlFileSizeLimitModel == null ? "" : webSysCtrlFileSizeLimitModel.getSubService()));
		subService.setRequired(true);
		subService.add(StringValidator.exactLength(5));
		subService.add(new PatternValidator(textValidator));
		if(action == "update")
		{
			subService.setEnabled(false);
		}
		form.add(subService);
		
		limit = new TextField<String>("limit", new Model<String>(webSysCtrlFileSizeLimitModel == null ? "" : webSysCtrlFileSizeLimitModel.getLimit()));
		limit.setRequired(true);
		limit.add(StringValidator.maximumLength(5));
		limit.add(new PatternValidator(textValidator));
		if(action == "update")
		{
			limit.setEnabled(true);
		}
		form.add(limit);
		
		List<String> indicatorDirection = new ArrayList<String>();
		indicatorDirection.add("INPUT");
		indicatorDirection.add("OUTPUT");
		if(webSysCtrlFileSizeLimitModel != null)
		{
			if(webSysCtrlFileSizeLimitModel.getDirection() != null && webSysCtrlFileSizeLimitModel.getDirection().equals("I"))
			{
				selectedDirection = "INPUT";
			}
			else
			{
				selectedDirection = "OUTPUT";
			}
		}
		else
		{
			selectedDirection="INPUT";
		}
		direction = new DropDownChoice<String>("direction", new Model<String>(selectedDirection), indicatorDirection);
		direction.setRequired(true);
		if(action.equalsIgnoreCase("create"))
		{
			direction.setEnabled(true);
		}
		form.add(direction);
		
		List<String> indicators = new ArrayList<String>();
		indicators.add("ACTIVE");
		indicators.add("INACTIVE");
		if(webSysCtrlFileSizeLimitModel != null)
		{
			if(webSysCtrlFileSizeLimitModel.getActiveId() != null && webSysCtrlFileSizeLimitModel.getActiveId().equals("Y"))
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
			@Override
			public void onSubmit() 
			{
				try
				{
					boolean results = false;
					boolean saved =false;
					WebSysCtrlFileSizeLimitModel localWebModel = null;
					webAuditTrackingModel = new WebAuditTrackingModel();
					session = getSession();
			        clientSessionModel = (ClientSessionModel) session.getAttribute("role");
			        userName = clientSessionModel.getUsername();
					if(action.equals("update"))
					{
						localWebModel = new WebSysCtrlFileSizeLimitModel(webSysCtrlFileSizeLimitModel.getMemberId(),webSysCtrlFileSizeLimitModel.getSubService());
						
					}
					else
					{
						localWebModel = new WebSysCtrlFileSizeLimitModel();
					}
					
					localWebModel.setLimit(limit.getValue());
					localWebModel.setDirection(direction.getModelObject()== "INPUT" ? "I" : "O");
					localWebModel.setActiveId(activeInd.getModelObject() == "ACTIVE" ? "Y" : "N");
			
					if(action.equalsIgnoreCase("create"))
					{
						localWebModel.setCreatedBy(userName);
						localWebModel.setCreatedDate(new Date());
					}
					else
					{
						localWebModel.setCreatedBy(webSysCtrlFileSizeLimitModel.getCreatedBy());
						localWebModel.setCreatedDate(webSysCtrlFileSizeLimitModel.getCreatedDate());
					}
					localWebModel.setModifiedBy(userName);
					localWebModel.setModifiedDate(new Date());
					log.debug("Web Model: "+ localWebModel);
				
					SysCtrlFileSizeLimitModel createLocalModel = new WebAdminTranslator().translateWebSysCtrlFileSizeLimitModelToCommonsModel(localWebModel);
					Controller controller = new Controller();
					results = controller.createSysCtrlFileSizeLimit(createLocalModel);
			        log.debug("===============================originalWebSysCtrlFileSizeLimitModell old value =================:"+originalWebSysCtrlFileSizeLimitModell);
			        log.debug("===============================localWebModel new value=================:"+localWebModel);
			        if(originalWebSysCtrlFileSizeLimitModell != null)
			        {
			        	if(originalWebSysCtrlFileSizeLimitModell.getMemberId()!= localWebModel.getMemberId())
						{
							webAuditTrackingModel.setOldValue(originalWebSysCtrlFileSizeLimitModell.getMemberId());
							webAuditTrackingModel.setNewValue(localWebModel.getMemberId());
						}
					
			        	if(originalWebSysCtrlFileSizeLimitModell.getSubService()!= localWebModel.getSubService())
			        	{
			        		webAuditTrackingModel.setOldValue(originalWebSysCtrlFileSizeLimitModell.getSubService());
						webAuditTrackingModel.setNewValue(localWebModel.getSubService());
			        	}
			        	
			           	if(originalWebSysCtrlFileSizeLimitModell.getDirection()!= localWebModel.getDirection())
			        	{
			        		webAuditTrackingModel.setOldValue(originalWebSysCtrlFileSizeLimitModell.getDirection());
							webAuditTrackingModel.setNewValue(localWebModel.getDirection());
			        	}
			           	
			          	if(originalWebSysCtrlFileSizeLimitModell.getLimit()!= localWebModel.getLimit())
			        	{
			        		webAuditTrackingModel.setOldValue(originalWebSysCtrlFileSizeLimitModell.getLimit().toString());
							webAuditTrackingModel.setNewValue(localWebModel.getLimit().toString());
			        	}
			           	
		
						if(originalWebSysCtrlFileSizeLimitModell.getActiveId()!= localWebModel.getActiveId())
						{
							webAuditTrackingModel.setOldValue(originalWebSysCtrlFileSizeLimitModell.getActiveId());
							webAuditTrackingModel.setNewValue(localWebModel.getActiveId());
						}
									       
			        }
			        else
		        {
						webAuditTrackingModel.setNewValue(localWebModel.getMemberId());
			        	webAuditTrackingModel.setNewValue(localWebModel.getSubService());
			        	webAuditTrackingModel.setNewValue(localWebModel.getDirection());
			        	webAuditTrackingModel.setNewValue(localWebModel.getLimit());
			        	webAuditTrackingModel.setNewValue(localWebModel.getActiveId());
			        }
					webAuditTrackingModel.setColumnName(memberIdLabel.getDefaultModelObjectAsString());
					webAuditTrackingModel.setColumnName(limitLabel.getDefaultModelObjectAsString());
					webAuditTrackingModel.setColumnName(directionLabel.getDefaultModelObjectAsString());
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
							info(" File Size Limit Created Successfully.");
						}
						else
						{
							info("File Size Limit Updated Successfully.");
						}
						viewFileSizeLimitPanel=new ViewFileSizeLimitPanel(id);
						MarkupContainer markupContainer = form.getParent().getParent();
						markupContainer.remove(form.getParent());
						markupContainer.add(viewFileSizeLimitPanel);
						viewFileSizeLimitPanel.setOutputMarkupId(true);
						viewFileSizeLimitPanel.setOutputMarkupPlaceholderTag(true);
						TemplatePage.setContent(viewFileSizeLimitPanel);
					}
					else
					{
						error("File Size Limit could not be added/update...");
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
			@Override
			public void onSubmit() 
			{
				viewFileSizeLimitPanel=new ViewFileSizeLimitPanel(id);
				MarkupContainer markupContainer = form.getParent().getParent();
				markupContainer.remove(form.getParent());
				markupContainer.add(viewFileSizeLimitPanel);
				viewFileSizeLimitPanel.setOutputMarkupId(true);
				viewFileSizeLimitPanel.setOutputMarkupPlaceholderTag(true);
				TemplatePage.setContent(viewFileSizeLimitPanel);
			}
		};
		cancelButton.setDefaultFormProcessing(false);
		
		form.add(saveOrUpdateButton);
		form.add(cancelButton);
		add(form);
		FeedbackPanel feedbackPanel = new FeedbackPanel("feedbackPanel");
        add(feedbackPanel);
	}
	
}
