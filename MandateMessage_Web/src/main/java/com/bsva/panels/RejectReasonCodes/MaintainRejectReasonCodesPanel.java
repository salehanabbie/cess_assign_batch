package com.bsva.panels.RejectReasonCodes;
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
import com.bsva.commons.model.CnfgRejectReasonCodesModel;
import com.bsva.controller.Controller;
import com.bsva.models.ClientSessionModel;
import com.bsva.models.WebAuditTrackingModel;
import com.bsva.models.WebRejectReasonCodesModel;
import com.bsva.translator.WebAdminTranslator;
import com.bsva.validators.TextFieldValidator;

public class MaintainRejectReasonCodesPanel  extends Panel  implements Serializable
{
	/**
	 * @author ElelwaniR
	 */
	private static final long serialVersionUID = 1L;
	public static Logger log = Logger.getLogger(MaintainRejectReasonCodesPanel.class);
	private String screenName;
	private ClientSessionModel clientSessionModel;
	private String userName;
	public static Session session;
	private WebRejectReasonCodesModel webRejectReasonCodesModel,originalWebRejectReasonCodesModel;
    public static String id;
	private Form form;
    private  TextField<String>rejectReasonDesc;
    private  TextField<String> rejectReasonCode;
	private Button saveOrUpdateButton;
	private Button cancelButton;
	private DropDownChoice<String> activeInd;
	private String selectedIndicator;
	private String action;
	ViewRejectReasoCodesPanel viewRejectReasonPanel;
	WebAuditTrackingModel webAuditTrackingModel;
	Label rejectReasCodeLabel,rejectReasonDescLabel,activeIndLabel,legendLbl;
	Controller controller;
	
	public MaintainRejectReasonCodesPanel(String id, String action)
	{
		super(id);
         this.id=id;
         this.action=action;
         initializeComponents();
	}
	
	public MaintainRejectReasonCodesPanel(String id, WebRejectReasonCodesModel   webRejectReasonCodesModel,String action)
	{
		super(id);
		this.id=id;
		this.action=action;
		this.webRejectReasonCodesModel=webRejectReasonCodesModel;
		this.originalWebRejectReasonCodesModel=webRejectReasonCodesModel;
		initializeComponents();		  
	}

	private void initializeComponents() 
	{
		controller = new Controller();
		screenName = controller.getProperty("MNTN.SCRNNAME");
		legendLbl = new Label("legend","Reject Reason Codes  - "+action.toUpperCase());
		add(legendLbl);
		form = new Form ("MaintainRejectReasonForm");
		  
		rejectReasCodeLabel= new Label("label1","Reject Reason Code");
		form.add(rejectReasCodeLabel);
		
		rejectReasonDescLabel = new Label("label2","Reject Reason Description");
		form.add(rejectReasonDescLabel);
		
		activeIndLabel=new Label("label3","Active Indicator");
		form.add(activeIndLabel);
			
		rejectReasonCode = new  TextField<String>("rejectReasonCode",  new Model<String>(webRejectReasonCodesModel ==null ? "" : webRejectReasonCodesModel.getRejectReasonCode()));
		rejectReasonCode.setRequired(true);
		rejectReasonCode.add(StringValidator.exactLength(4));
		rejectReasonCode.add(new TextFieldValidator()); 
		if(action == "update")
		{
			rejectReasonCode.setEnabled(false);
		}
		form.add(rejectReasonCode);
		rejectReasonDesc = new TextField<String>("rejectReasonDesc", new Model<String>(webRejectReasonCodesModel == null ? "" : webRejectReasonCodesModel.getRejectReasonDesc()));
		rejectReasonDesc.setRequired(true);
		rejectReasonDesc.add(StringValidator.maximumLength(100));
		rejectReasonDesc.add(new TextFieldValidator());
		form.add(rejectReasonDesc);
			
		List<String> indicators = new ArrayList<String>();
		indicators.add("ACTIVE");
		indicators.add("INACTIVE");	
		if(webRejectReasonCodesModel != null)
		{
			if(webRejectReasonCodesModel.getActiveInd() != null && webRejectReasonCodesModel.getActiveInd().equals("Y"))
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
			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit() 
			{
				try
				{
					boolean results = false;
					boolean saved = false;
					WebRejectReasonCodesModel  localWebModel = null;
					webAuditTrackingModel = new WebAuditTrackingModel();	
					session = getSession();
				    clientSessionModel = (ClientSessionModel) session.getAttribute("role"); 
				    userName = clientSessionModel.getUsername();
					if(action.equals("update"))
					{
						localWebModel = new WebRejectReasonCodesModel(webRejectReasonCodesModel.getRejectReasonCode());
					}
					else
					{
						localWebModel = new WebRejectReasonCodesModel();
					}
					localWebModel.setRejectReasonCode(rejectReasonCode.getValue().toUpperCase());
					localWebModel.setRejectReasonDesc(rejectReasonDesc.getValue().toUpperCase());
					localWebModel.setActiveInd(activeInd.getModelObject() == "ACTIVE" ? "Y" : "N");
					
					if(action.equalsIgnoreCase("create"))
					{
						localWebModel.setCreatedBy(userName);
						localWebModel.setCreatedDate(new Date());
					}
					else
					{
						localWebModel.setCreatedBy(webRejectReasonCodesModel.getCreatedBy());
						localWebModel.setCreatedDate(webRejectReasonCodesModel.getCreatedDate());
					}
					localWebModel.setModifiedBy(userName);
					localWebModel.setModifiedDate(new Date());	
					log.debug("Web Model: "+ localWebModel);
					
					CnfgRejectReasonCodesModel   createLocalModel = new WebAdminTranslator().translateWebRejectReasonCodesModelToCommonsModel(localWebModel);
					log.debug("Commons model"+ createLocalModel);
					results = controller.createRejectReasonCode(createLocalModel);
				    log.debug("===============================originalWebRejectReasonCodesModel old value =================:"+originalWebRejectReasonCodesModel);
				    log.debug("===============================localWebModel new value=================:"+localWebModel);   
				    if(originalWebRejectReasonCodesModel != null)
				    {
				    	if(originalWebRejectReasonCodesModel.getRejectReasonDesc()!= localWebModel.getRejectReasonDesc())
						{
							webAuditTrackingModel.setOldValue(originalWebRejectReasonCodesModel.getRejectReasonDesc());
							webAuditTrackingModel.setNewValue(localWebModel.getRejectReasonDesc());
						}
						if(originalWebRejectReasonCodesModel.getActiveInd()!= localWebModel.getActiveInd())
						{
							webAuditTrackingModel.setOldValue(originalWebRejectReasonCodesModel.getActiveInd());
							webAuditTrackingModel.setNewValue(localWebModel.getActiveInd());
						}		
				    }
				    else
				    {
				       	webAuditTrackingModel.setNewValue(localWebModel.getRejectReasonDesc());
				        webAuditTrackingModel.setNewValue(localWebModel.getActiveInd());
				    }
				    webAuditTrackingModel.setColumnName(rejectReasonDescLabel.getDefaultModelObjectAsString());
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
							error("Reject ReasonCodes Created Successfully.");
						}
						else
						{
							info("Reject ReasonCodes Updated Successfully.");
						}
						viewRejectReasonPanel=new ViewRejectReasoCodesPanel(id);
						MarkupContainer markupContainer = form.getParent().getParent();
						markupContainer.remove(form.getParent());
						markupContainer.add(viewRejectReasonPanel);
						viewRejectReasonPanel.setOutputMarkupId(true);
						viewRejectReasonPanel.setOutputMarkupPlaceholderTag(true);
						TemplatePage.setContent(viewRejectReasonPanel);
						log.debug(createLocalModel);
						}
						else
						{
							error("Reject Reason  Code could not be added/update...");
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
			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit() 
			{
				viewRejectReasonPanel=new ViewRejectReasoCodesPanel(id);
				MarkupContainer markupContainer = form.getParent().getParent();
				markupContainer.remove(form.getParent());
				markupContainer.add(viewRejectReasonPanel);
				viewRejectReasonPanel.setOutputMarkupId(true);
				viewRejectReasonPanel.setOutputMarkupPlaceholderTag(true);
				TemplatePage.setContent(viewRejectReasonPanel);
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
