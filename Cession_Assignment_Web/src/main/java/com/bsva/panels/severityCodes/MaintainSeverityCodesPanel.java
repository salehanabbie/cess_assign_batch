package com.bsva.panels.severityCodes;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import org.apache.wicket.validation.validator.StringValidator;
import com.bsva.TemplatePage;
import com.bsva.commons.model.AuditTrackingModel;
import com.bsva.commons.model.SeverityCodesModel;
import com.bsva.controller.Controller;
import com.bsva.models.ClientSessionModel;
import com.bsva.models.WebAuditTrackingModel;
import com.bsva.models.WebSeverityCodesModel;
import com.bsva.translator.WebAdminTranslator;
import com.bsva.validators.TextFieldValidator;

/**
 * 
 * @author NhlanhlaM
 *
 */
public class MaintainSeverityCodesPanel extends Panel 
{
	private static final long serialVersionUID = 1L;
	public static Logger log = Logger.getLogger(MaintainSeverityCodesPanel.class);
	private String action;
	private static String id;
	private String screenName;
	private ClientSessionModel clientSessionModel;
	private String userName;
	public static Session session;
	private TextField<String> severityCode;
	private TextField<String> severityCodeDesc;
	private Button cancelButton;
	private Button saveOrUpdateButton;
	private String selectedIndicator;
	private DropDownChoice<String> activeInd;
	private WebSeverityCodesModel webSeverityCodesModel,originalWebSeverityCodesModel;
	Label severityCodeLabel,sevCodeDescLabel,sevActionLabel, activeIndLabel,legendLbl;
	ViewSeverityCodesPanel viewSeverityCodesPanel;
	WebAuditTrackingModel webAuditTrackingModel;
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private Form form;
	Controller controller;
	
	public MaintainSeverityCodesPanel(String id, String action)
	{
		super(id);
		this.id = id;
		this.action = action;
		initialiazeComponents(); 
	}
	
	public MaintainSeverityCodesPanel (String id, WebSeverityCodesModel webSeverityCodesModel, String action)
	{
		super(id);
		this.action = action;
		this.id = id;
		this.webSeverityCodesModel = webSeverityCodesModel;
		this.originalWebSeverityCodesModel = webSeverityCodesModel;
		initialiazeComponents();
		log.debug("Screen ID:" + action);
	}

	private void initialiazeComponents() 
	{
		controller = new Controller();
		screenName = controller.getProperty("MNTN.SCRNNAME");
		legendLbl = new Label("legend","Severity Codes  - "+action.toUpperCase());
		add(legendLbl);
		form = new Form("MaintainSeverityCodesPanel");
		
		severityCodeLabel = new Label("label1","Severity Code");
		form.add(severityCodeLabel);
		
		sevCodeDescLabel = new Label("label2","Severity Code Desc");
		form.add(sevCodeDescLabel);
		
		activeIndLabel=new Label("label3","Active Indicator ");
		form.add(activeIndLabel);
		
		severityCode = new TextField<String>("severityCode", new Model<String>(webSeverityCodesModel ==null ? "" : String.valueOf(webSeverityCodesModel.getSeverityCode())));
		severityCode.setRequired(true);
		severityCode.add(StringValidator.maximumLength(2));
		severityCode.add(new TextFieldValidator());
		if (action == "update")
		{
			severityCode.setEnabled(false);
		}
		form.add(severityCode);  
	    severityCodeDesc = new TextField<String>("severityCodeDesc", new Model<String>(webSeverityCodesModel == null ? "" : webSeverityCodesModel.getSeverityCodeDesc()));
	    severityCodeDesc.setRequired(true);
	    severityCodeDesc.add(StringValidator.maximumLength(50));
	    severityCodeDesc.add(new TextFieldValidator());
	    form.add(severityCodeDesc);
			
		List<String> indicators = new ArrayList<String>();
		indicators.add("ACTIVE");
		indicators.add("INACTIVE");	
		if(webSeverityCodesModel != null)
		{
			if(webSeverityCodesModel.getActiveInd() != null && webSeverityCodesModel.getActiveInd().equals("Y"))
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
					WebSeverityCodesModel localWebSeverityCodesModel = null;
					webAuditTrackingModel = new WebAuditTrackingModel();
					session = getSession();
				    clientSessionModel = (ClientSessionModel) session.getAttribute("role");
				    userName = clientSessionModel.getUsername();
					if (action.equals("update"))
					{
						localWebSeverityCodesModel = new WebSeverityCodesModel(webSeverityCodesModel.getSeverityCode());
					} 
					else 
					{
						localWebSeverityCodesModel = new WebSeverityCodesModel();
					}
					localWebSeverityCodesModel.setSeverityCode(Short.valueOf(severityCode.getValue()));
					localWebSeverityCodesModel.setSeverityCodeDesc(severityCodeDesc.getValue().toUpperCase());
					localWebSeverityCodesModel.setActiveInd(activeInd.getModelObject() == "ACTIVE" ? "Y" : "N");
					if(action.equalsIgnoreCase("create"))
					{
						localWebSeverityCodesModel.setCreatedBy(userName);
						localWebSeverityCodesModel.setCreatedDate(new Date());   
					}
					else
					{
						localWebSeverityCodesModel.setCreatedBy(webSeverityCodesModel.getCreatedBy());
						localWebSeverityCodesModel.setCreatedDate(webSeverityCodesModel.getCreatedDate());
					}	
					localWebSeverityCodesModel.setModifiedBy(userName);
					localWebSeverityCodesModel.setModifiedDate(new Date());
					log.debug("Web Model: " +  localWebSeverityCodesModel);
						
					SeverityCodesModel severityCodesModel = new WebAdminTranslator().translateWebSeverityCodesModelToCommonsModel(localWebSeverityCodesModel);
					log.debug("Commons Model: " +  severityCodesModel);
					results = controller.createSeverityCodes(severityCodesModel);
						
				    log.debug("===============================originalWebSeverityCodesModel old value =================:"+originalWebSeverityCodesModel);
				    log.debug("===============================localWebSeverityCodesModel new value=================:"+localWebSeverityCodesModel);
				    if(originalWebSeverityCodesModel != null)
				    {
				    	if(originalWebSeverityCodesModel.getSeverityCodeDesc()!= localWebSeverityCodesModel.getSeverityCodeDesc())
					    {
				    		webAuditTrackingModel.setOldValue(originalWebSeverityCodesModel.getSeverityCodeDesc());
					        webAuditTrackingModel.setNewValue(localWebSeverityCodesModel.getSeverityCodeDesc());
					    }	
				        if(originalWebSeverityCodesModel.getActiveInd() != localWebSeverityCodesModel.getActiveInd())
						{
							webAuditTrackingModel.setOldValue(originalWebSeverityCodesModel.getActiveInd());
							webAuditTrackingModel.setNewValue(localWebSeverityCodesModel.getActiveInd());
						}
				    }
				    else
				    {
				        webAuditTrackingModel.setNewValue(localWebSeverityCodesModel.getSeverityCodeDesc());
				        webAuditTrackingModel.setNewValue(localWebSeverityCodesModel.getActiveInd());
				    }  
				    webAuditTrackingModel.setColumnName(sevCodeDescLabel.getDefaultModelObjectAsString());
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
							error("Severity Codes Created Successfully.");
						}
						else
						{
							info("Severity Codes Updated Successfully.");
						}
						viewSeverityCodesPanel = new ViewSeverityCodesPanel(id);
						MarkupContainer markupContainer = form.getParent().getParent();
						markupContainer.remove(form.getParent());
						markupContainer.add(viewSeverityCodesPanel);
						viewSeverityCodesPanel.setOutputMarkupId(true);
						viewSeverityCodesPanel.setOutputMarkupPlaceholderTag(true);
						TemplatePage.setContent(viewSeverityCodesPanel);
						log.debug(severityCodesModel);
					}
					else
					{
						error("Severity Codes could not be added/Updated...");
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
				viewSeverityCodesPanel = new ViewSeverityCodesPanel(id);
				MarkupContainer markupContainer = form.getParent().getParent();
				markupContainer.remove(form.getParent());
				markupContainer.add(viewSeverityCodesPanel);
				viewSeverityCodesPanel.setOutputMarkupId(true);
				viewSeverityCodesPanel.setOutputMarkupPlaceholderTag(true);
				TemplatePage.setContent(viewSeverityCodesPanel);
			}
		};
		cancelButton.setDefaultFormProcessing(false);
		cancelButton.setEnabled(true);
	
		form.add(saveOrUpdateButton);
		form.add(cancelButton);
		add(form);
		FeedbackPanel feedbackPanel = new FeedbackPanel("feedbackPanel");
		add(feedbackPanel);						    
	}
}