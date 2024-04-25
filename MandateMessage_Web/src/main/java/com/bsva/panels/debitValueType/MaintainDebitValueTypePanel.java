package com.bsva.panels.debitValueType;
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
import com.bsva.commons.model.DebitValueTypeModel;
import com.bsva.controller.Controller;
import com.bsva.models.ClientSessionModel;
import com.bsva.models.WebAuditTrackingModel;
import com.bsva.models.WebDebitValueTypeModel;
import com.bsva.translator.WebAdminTranslator;
import com.bsva.validators.TextFieldValidator;

/**
 * @author nosiphos
 *
 */
public class MaintainDebitValueTypePanel extends Panel 
{
	ViewDebitValueTypePanel viewdebitpanel;
	private static final long serialVersionUID = 1L;
    public static String id;
	public static Logger log = Logger.getLogger(MaintainDebitValueTypePanel.class);
	private String screenName;
	private ClientSessionModel clientSessionModel;
	private String userName;
	public static Session session;
	private Form form;
	private Button saveOrUpdateButton;
	private Button cancelButton;
	private TextField<String> DbtValueTypeCode;
	private TextField<String> DbtValueTypeDesc;
	private DropDownChoice<String> activeInd;
	private String selectedIndicator;
	private String action;
	Label debitTypeCode,debitTypeDesc,activeIndLabel, legendLbl;
	Controller controller;
	private WebDebitValueTypeModel webDebitValueTypeModel,originalWebDebitValueTypeModel;
	WebAuditTrackingModel webAuditTrackingModel;
	String textValidator = "([a-zA-Z0-9 \\-._()]+)";

	public MaintainDebitValueTypePanel(String id, String action)
	{
		super(id);
		this.action = action;
		this.id=id;
		initializeComponents();
		log.debug("Screen ID: "+ action);
	}

	public MaintainDebitValueTypePanel(String id,WebDebitValueTypeModel webDebitValueTypeModel, String action)
	{
		super(id);
		this.action = action;
		this.id=id;
		this.webDebitValueTypeModel = webDebitValueTypeModel;
		this.originalWebDebitValueTypeModel = webDebitValueTypeModel;
		initializeComponents();
		log.debug("Screen ID: "+ action);
	}

	private void initializeComponents() 
	{
		controller = new Controller();
		screenName = controller.getProperty("MNTN.SCRNNAME");
		legendLbl = new Label("legend","Debit Value Type  - "+action.toUpperCase());
		add(legendLbl);
	
		form = new Form ("maintainDebitValueTypePanel");
		  
		debitTypeCode = new Label("label1","Debit Value Type Code ");
		form.add(debitTypeCode);
		debitTypeDesc = new Label("label2","Debit Value Type Desc ");
		form.add(debitTypeDesc);
		
		activeIndLabel=new Label("label3","Active Indicator ");
		form.add(activeIndLabel);
	
		DbtValueTypeCode = new  TextField<String>("DbtValueTypeCode",  new Model<String>(webDebitValueTypeModel ==null ? "" : webDebitValueTypeModel.getDebValueTypeCode()));
		DbtValueTypeCode.setRequired(true);
		DbtValueTypeCode.add(StringValidator.exactLength(4));
		DbtValueTypeCode.add(new PatternValidator(textValidator));
		if(action == "update")
		{
		 	DbtValueTypeCode.setEnabled(false);
		}
		form.add(DbtValueTypeCode);
			
		DbtValueTypeDesc = new TextField<String>("DbtValueTypeDesc", new Model<String>(webDebitValueTypeModel == null ? "" : webDebitValueTypeModel.getDebValueTypeDesc()));
		DbtValueTypeDesc.setRequired(true);
		DbtValueTypeDesc.add(StringValidator.maximumLength(100));
		DbtValueTypeDesc.add(new PatternValidator(textValidator));
		form.add(DbtValueTypeDesc);
			
		List<String> indicators = new ArrayList<String>();
		indicators.add("ACTIVE");
		indicators.add("INACTIVE");
		if(webDebitValueTypeModel != null)
		{
			if(webDebitValueTypeModel.getActiveInd() != null && webDebitValueTypeModel.getActiveInd().equals("Y"))
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
					boolean saved = false;
					WebDebitValueTypeModel localWebModel = null;
					webAuditTrackingModel = new WebAuditTrackingModel();
					session = getSession();
				    clientSessionModel = (ClientSessionModel) session.getAttribute("role");
				    userName = clientSessionModel.getUsername();
					if(action.equals("update"))
					{
						localWebModel = new WebDebitValueTypeModel(webDebitValueTypeModel.getDebValueTypeCode());
					}
					else
					{
						localWebModel = new WebDebitValueTypeModel();
					}
					localWebModel.setDebValueTypeCode(DbtValueTypeCode.getValue().toUpperCase());
					localWebModel.setDebValueTypeDesc(DbtValueTypeDesc.getValue().toUpperCase());
					localWebModel.setActiveInd(activeInd.getModelObject() == "ACTIVE" ? "Y" : "N");
					if(action.equalsIgnoreCase("create"))
					{
						localWebModel.setCreatedBy(userName);
						localWebModel.setCreatedDate(new Date());
					}
					else
					{
						localWebModel.setCreatedBy(webDebitValueTypeModel.getCreatedBy());
						localWebModel.setCreatedDate(webDebitValueTypeModel.getCreatedDate());
					}
					localWebModel.setModifiedBy(userName);
					localWebModel.setModifiedDate(new Date());
					
					DebitValueTypeModel createLocalModel = new WebAdminTranslator().translateWebDebitValueModelToCommonsModel(localWebModel);
					log.debug("Commons model"+ createLocalModel);
					results = controller.createdebValueTypeCode(createLocalModel);
						
				    log.debug("===============================originalWebDebitValueTypeModel old value =================:"+originalWebDebitValueTypeModel);
				    log.debug("===============================localWebModel new value=================:"+localWebModel);
				    if(originalWebDebitValueTypeModel != null)
				    {
				       	if(originalWebDebitValueTypeModel.getDebValueTypeDesc() != localWebModel.getDebValueTypeDesc())
						{
							webAuditTrackingModel.setOldValue(originalWebDebitValueTypeModel.getDebValueTypeDesc());
							webAuditTrackingModel.setNewValue(localWebModel.getDebValueTypeDesc());
						}		
						if(originalWebDebitValueTypeModel.getActiveInd() != localWebModel.getActiveInd())
						{
							webAuditTrackingModel.setOldValue(originalWebDebitValueTypeModel.getActiveInd());
							webAuditTrackingModel.setNewValue(localWebModel.getActiveInd());
						}
				    }
				    else
				    {
				       	webAuditTrackingModel.setNewValue(localWebModel.getDebValueTypeDesc());
				       	webAuditTrackingModel.setNewValue(localWebModel.getActiveInd());
				    }
				    webAuditTrackingModel.setColumnName(debitTypeDesc.getDefaultModelObjectAsString());
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
							error("Debit value TypeCode Created Successfully.");
						}
						else
						{
							info("Debit value TypeCode Updated Successfully.");
						}		
						MarkupContainer markupContainer = form.getParent().getParent();
						viewdebitpanel=new ViewDebitValueTypePanel(id);
						markupContainer.remove(form.getParent());
						markupContainer.add(viewdebitpanel);
						viewdebitpanel.setOutputMarkupId(true);
						viewdebitpanel.setOutputMarkupPlaceholderTag(true);
						TemplatePage.setContent(viewdebitpanel);
						log.debug(createLocalModel);
					}
					else
					{
						error("Debit value Type Code could not be added/update...");
					}
				}
				catch(Exception exception)
				{
					error("Add/Update failed, " + exception.getMessage());
					log.error("Add/Update failed, ", exception);
					exception.printStackTrace();
				}
			}
		};

		cancelButton = new Button("cancelButton")
		{
			@Override
			public void onSubmit()
			{
				viewdebitpanel=new ViewDebitValueTypePanel(id);
				MarkupContainer markupContainer = form.getParent().getParent();
				markupContainer.remove(form.getParent());
				markupContainer.add(viewdebitpanel);
				viewdebitpanel.setOutputMarkupId(true);
				viewdebitpanel.setOutputMarkupPlaceholderTag(true);
				TemplatePage.setContent(viewdebitpanel);
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