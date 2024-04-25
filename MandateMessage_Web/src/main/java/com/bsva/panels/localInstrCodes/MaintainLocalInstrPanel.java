package com.bsva.panels.localInstrCodes;
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
import com.bsva.commons.model.LocalInstrumentCodesModel;
import com.bsva.controller.Controller;
import com.bsva.models.ClientSessionModel;
import com.bsva.models.WebAuditTrackingModel;
import com.bsva.models.WebLocalInstrumentCodesModel;
import com.bsva.translator.WebAdminTranslator;
import com.bsva.validators.FieldNumberTextFieldValidator;
import com.bsva.validators.TextFieldValidator;

/**
 * @author salehas
 *
 */
public class MaintainLocalInstrPanel  extends Panel  
{
	private static final long serialVersionUID = 1L;
	public static Logger log = Logger.getLogger(MaintainLocalInstrPanel.class);
	public static String id;
	private Form form;
	private String screenName;
	private ClientSessionModel clientSessionModel;
	private String userName;
	public static Session session;
	private Button saveOrUpdateButton;
	private Button cancelButton;
	private TextField<String> localInstrCode;
	private TextField<String> localInstCodeDesc;
	ViewLocalInstrPanel viewlocalinstrumentpanel;
	private DropDownChoice<String> activeInd;
	Label instrCodeLabel,instrCodeDescLabel,activeIndLabel,legendLbl;
	private String selectedIndicator;
	private String action;
	WebLocalInstrumentCodesModel webLocalInstrumentCodesModel,originalWebLocalInstrumentCodesModel;
	WebAuditTrackingModel webAuditTrackingModel;
	String textValidator = "([a-zA-Z0-9 \\-._()]+)";
	Controller controller;
	
	public MaintainLocalInstrPanel(String id, String action) 
	{
		super(id);
		this.id=id;
		this.action = action;
		initializeComponents();
	}
	
	public MaintainLocalInstrPanel(String id, WebLocalInstrumentCodesModel webLocalInstrumentCodesModel, String action)
	{
		super(id);
		this.id=id;
		this.action = action;
		this.webLocalInstrumentCodesModel = webLocalInstrumentCodesModel;
		this.originalWebLocalInstrumentCodesModel = webLocalInstrumentCodesModel;
		initializeComponents();
		log.debug("Screen ID: "+ action);
	}
	
	private void initializeComponents()
	{
		controller = new Controller();
		screenName = controller.getProperty("MNTN.SCRNNAME");
		
		legendLbl = new Label("legend","Local Instrument Codes  - "+action.toUpperCase());
		add(legendLbl);
		form =  new Form("maintainLocalInstrCodesPanel");
		
		instrCodeLabel= new Label("label1","Local Instrument Code");
		form.add(instrCodeLabel);
		
		instrCodeDescLabel = new Label("label2","Local Instrument Description");
		form.add(instrCodeDescLabel);
		
		activeIndLabel=new Label("label3","Active Indicator");
		form.add(activeIndLabel);
	
		localInstrCode = new TextField<String>("localInstrCode", new Model<String>(webLocalInstrumentCodesModel == null ? "" : webLocalInstrumentCodesModel.getLocalInstrumentCode()));
		localInstrCode.setRequired(true);
		localInstrCode.add(StringValidator.exactLength(4));
		localInstrCode.add(new PatternValidator(textValidator));
		if(action == "update")
		{
			localInstrCode.setEnabled(false);
		}
		form.add(localInstrCode);
		
		localInstCodeDesc = new TextField<String>("localInstCodeDesc", new Model<String>(webLocalInstrumentCodesModel == null ? "" : webLocalInstrumentCodesModel.getInstCodeDescription()));
		localInstCodeDesc.setRequired(true);
		localInstCodeDesc.add(StringValidator.maximumLength(100));
		localInstCodeDesc.add(new PatternValidator(textValidator));
		form.add(localInstCodeDesc);
	
		List<String> indicators = new ArrayList<String>();
		indicators.add("ACTIVE");
		indicators.add("INACTIVE");
		if(webLocalInstrumentCodesModel != null)
		{
			if(webLocalInstrumentCodesModel.getActiveInd() != null && webLocalInstrumentCodesModel.getActiveInd().equals("Y"))
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
					WebLocalInstrumentCodesModel localWebModel = null;
					webAuditTrackingModel = new WebAuditTrackingModel();
					session = getSession();
			        clientSessionModel = (ClientSessionModel) session.getAttribute("role");
			        userName = clientSessionModel.getUsername();
					if(action.equals("update"))
					{
						localWebModel = new WebLocalInstrumentCodesModel(webLocalInstrumentCodesModel.getLocalInstrumentCode());
					}
					else
					{
						localWebModel = new WebLocalInstrumentCodesModel();
					}
					localWebModel.setLocalInstrumentCode(localInstrCode.getValue());
					localWebModel.setInstCodeDescription(localInstCodeDesc.getValue().toUpperCase());
					localWebModel.setActiveInd(activeInd.getModelObject() == "ACTIVE" ? "Y" : "N");
			
					if(action.equalsIgnoreCase("create"))
					{
						localWebModel.setCreatedBy(userName);
						localWebModel.setCreatedDate(new Date());
					}
					else
					{
						localWebModel.setCreatedBy(webLocalInstrumentCodesModel.getCreatedBy());
						localWebModel.setCreatedDate(webLocalInstrumentCodesModel.getCreatedDate());
					}
					localWebModel.setModifiedBy(userName);
					localWebModel.setModifiedDate(new Date());
					log.debug("Web Model: "+ localWebModel);
				
					LocalInstrumentCodesModel createLocalModel = new WebAdminTranslator().translateWebInstrumentCodesModelToCommonsModel(localWebModel);
					Controller controller = new Controller();
					results = controller.createLocalInstrCode(createLocalModel);
			        log.debug("===============================originalWebLocalInstrumentCodesModel old value =================:"+originalWebLocalInstrumentCodesModel);
			        log.debug("===============================localWebModel new value=================:"+localWebModel);
			        if(originalWebLocalInstrumentCodesModel != null)
			        {
			        	if(originalWebLocalInstrumentCodesModel.getInstCodeDescription()!= localWebModel.getInstCodeDescription())
						{
							webAuditTrackingModel.setOldValue(originalWebLocalInstrumentCodesModel.getInstCodeDescription());
							webAuditTrackingModel.setNewValue(localWebModel.getInstCodeDescription());
						}
					
						if(originalWebLocalInstrumentCodesModel.getActiveInd()!= localWebModel.getActiveInd())
						{
							webAuditTrackingModel.setOldValue(originalWebLocalInstrumentCodesModel.getActiveInd());
							webAuditTrackingModel.setNewValue(localWebModel.getActiveInd());
						}
			        }
			        else
			        {
			        	webAuditTrackingModel.setNewValue(localWebModel.getInstCodeDescription());
			        	webAuditTrackingModel.setNewValue(localWebModel.getActiveInd());
			        }
					webAuditTrackingModel.setColumnName(instrCodeDescLabel.getDefaultModelObjectAsString());
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
							error(" Local InstrumentCode Created Successfully.");
						}
						else
						{
							info("Local InstrumentCode Updated Successfully.");
						}
						viewlocalinstrumentpanel=new ViewLocalInstrPanel(id);
						MarkupContainer markupContainer = form.getParent().getParent();
						markupContainer.remove(form.getParent());
						markupContainer.add(viewlocalinstrumentpanel);
						viewlocalinstrumentpanel.setOutputMarkupId(true);
						viewlocalinstrumentpanel.setOutputMarkupPlaceholderTag(true);
						TemplatePage.setContent(viewlocalinstrumentpanel);
					}
					else
					{
						error("Local Instrument Code could not be added/update...");
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
				viewlocalinstrumentpanel=new ViewLocalInstrPanel(id);
				MarkupContainer markupContainer = form.getParent().getParent();
				markupContainer.remove(form.getParent());
				markupContainer.add(viewlocalinstrumentpanel);
				viewlocalinstrumentpanel.setOutputMarkupId(true);
				viewlocalinstrumentpanel.setOutputMarkupPlaceholderTag(true);
				TemplatePage.setContent(viewlocalinstrumentpanel);
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