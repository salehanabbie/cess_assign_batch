package com.bsva.panels.accountType;
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
import com.bsva.commons.model.AuditTrackingModel;
import com.bsva.controller.Controller;
import com.bsva.models.ClientSessionModel;
import com.bsva.models.WebAccountTypeModel;
import com.bsva.models.WebAuditTrackingModel;
import com.bsva.translator.WebAdminTranslator;
import com.bsva.validators.TextFieldValidator;

/**
 * 
 * @author NhlanhlaM
 *
 */
public class MaintainAccountTypePanel extends Panel implements Serializable
{
	private static final long serialVersionUID = 1L;
	public static Logger log = Logger.getLogger(MaintainAccountTypePanel.class);
	private DropDownChoice<String> activeInd;
	private String action;
	private static String id;
	private String screenName;
	private ClientSessionModel clientSessionModel;
	private String userName;
	public static Session session;
	private TextField<String> accountTypeCode;
	private TextField<String> accountTypeDescription;
	private Button saveOrUpdateButton;
	private Button cancelButton;
	private String selectedIndicator;
	Label accTypCode,accTypDesc,activeIndLabel,legendLbl;
	private WebAccountTypeModel webAccountTypeModel,originaWebAccountTypeModel;
	WebAuditTrackingModel webAuditTrackingModel;
	ViewAccountTypePanel viewAccountTypePanel;
	private Form form;
	String textValidator = "([a-zA-Z0-9 \\-._()]+)";    
	Controller controller;

	public MaintainAccountTypePanel(String id, String action)
	{
		super(id);
		this.id = id;
		this.action = action;
		initialiazeComponents();
	}

	public MaintainAccountTypePanel (String id, WebAccountTypeModel webAccountTypeModel, String action)
	{
		super(id);
		this.action = action;
		this.id = id;
		this.webAccountTypeModel = webAccountTypeModel;
		this.originaWebAccountTypeModel = webAccountTypeModel;
		initialiazeComponents();
		log.debug("Screen ID:" + action);
	}

	private void initialiazeComponents() 
	{
		controller = new Controller();
		screenName = controller.getProperty("MNTN.SCRNNAME");

		legendLbl = new Label("legend","Account Type  - "+action.toUpperCase());
		add(legendLbl);
		form = new Form("MaintainAccountTypePanel");

		accTypCode = new Label("label1","Account Type Code");
		form.add(accTypCode);

		accTypDesc = new Label("label2","Account Type Description");
		form.add(accTypDesc);

		activeIndLabel = new Label("label3","Active Indicator ");
		form.add(activeIndLabel);

		accountTypeCode =  new TextField<String>("accountTypeCode", new Model<String>(webAccountTypeModel == null ? "" : webAccountTypeModel.getAccountTypeCode()));
		accountTypeCode.setRequired(true);
		accountTypeCode.add(StringValidator.exactLength(1));
		accountTypeCode.add(new PatternValidator(textValidator));
		if(action == "update")
		{
			accountTypeCode.setEnabled(false);
		}
		form.add(accountTypeCode);

		accountTypeDescription =  new TextField<String>("accountTypeDescription", new Model<String>(webAccountTypeModel == null ? "" : webAccountTypeModel.getAccountTypeDescription()));
		accountTypeDescription.setRequired(true);
		accountTypeDescription.add(StringValidator.maximumLength(35));
		accountTypeDescription.add(new PatternValidator(textValidator));
		form.add(accountTypeDescription);

		List<String> indicators = new ArrayList<String>();
		indicators.add("ACTIVE");
		indicators.add("INACTIVE");
		if (webAccountTypeModel != null) 
		{
			if (webAccountTypeModel.getActiveInd() != null && webAccountTypeModel.getActiveInd().equals("Y"))
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
					WebAccountTypeModel localWebAccountTypeModel= null;
					webAuditTrackingModel = new WebAuditTrackingModel();
					session = getSession();
					clientSessionModel = (ClientSessionModel) session.getAttribute("role");
					userName = clientSessionModel.getUsername();
					if (action.equals("update"))
					{
						localWebAccountTypeModel = new WebAccountTypeModel(webAccountTypeModel.getAccountTypeCode());
					}
					else 																					
					{
						localWebAccountTypeModel = new WebAccountTypeModel();
					}
					localWebAccountTypeModel.setAccountTypeCode(accountTypeCode.getValue().toUpperCase());
					String acctTypDsc = StringUtils.capitalize(accountTypeDescription.getValue());
					localWebAccountTypeModel.setAccountTypeDescription(acctTypDsc);	
					localWebAccountTypeModel.setActiveInd(activeInd.getModelObject() == "ACTIVE" ? "Y" : "N");
					if(action.equalsIgnoreCase("create"))
					{
						localWebAccountTypeModel.setCreatedBy(userName);
						localWebAccountTypeModel.setCreatedDate(new Date());
					}
					else
					{
						localWebAccountTypeModel.setCreatedBy(webAccountTypeModel.getCreatedBy());
						localWebAccountTypeModel.setCreatedDate(webAccountTypeModel.getCreatedDate());
					}
					localWebAccountTypeModel.setModifiedBy(userName);
					localWebAccountTypeModel.setModifiedDate(new Date());
					log.debug("Web Model: " +  localWebAccountTypeModel);

					AccountTypeModel accountTypeModel = new WebAdminTranslator().translateWebAccountTypeModelToCommonsModel(localWebAccountTypeModel);
					log.debug("Commons Model: " +  accountTypeModel);

					results = controller.createAccountType(accountTypeModel);
					log.info("results -->" +results);

					log.info("======================webAccountTypeModel=================:"+webAccountTypeModel);
					log.info("======================originaWebAccountTypeModel=================:"+localWebAccountTypeModel);
					if(originaWebAccountTypeModel != null)
					{
						if(originaWebAccountTypeModel.getAccountTypeDescription() != localWebAccountTypeModel.getAccountTypeDescription())
						{
							webAuditTrackingModel.setOldValue(originaWebAccountTypeModel.getAccountTypeDescription());
							webAuditTrackingModel.setNewValue(localWebAccountTypeModel.getAccountTypeDescription());
						}
						if(originaWebAccountTypeModel.getActiveInd() != localWebAccountTypeModel.getActiveInd())
						{
							webAuditTrackingModel.setOldValue(originaWebAccountTypeModel.getActiveInd());
							webAuditTrackingModel.setNewValue(localWebAccountTypeModel.getActiveInd());
						}
					}
					else
					{
						webAuditTrackingModel.setNewValue(localWebAccountTypeModel.getAccountTypeDescription());
						webAuditTrackingModel.setNewValue(localWebAccountTypeModel.getActiveInd());
					}
					webAuditTrackingModel.setColumnName(activeIndLabel.getDefaultModelObjectAsString());
					webAuditTrackingModel.setColumnName(accTypDesc.getDefaultModelObjectAsString());
					webAuditTrackingModel.setRecordId(new BigDecimal(123));
					webAuditTrackingModel.setAction(action.toUpperCase());
					webAuditTrackingModel.setUserId(userName);
					webAuditTrackingModel.setActionDate(new Date());
					webAuditTrackingModel.setTableName(screenName);

					AuditTrackingModel	 auditTrackingModel = new WebAdminTranslator().translateWebAuditTrackingModelToCommonsModel(webAuditTrackingModel);
					saved = controller.createAuditTracking(auditTrackingModel);
					if(results)
					{
						if(action == "create")
						{
							log.debug("in the create method");
							error("Account Type Created Successfully.");
						}
						else
						{
							info("Account Type Updated Successfully.");
						}
						viewAccountTypePanel = new ViewAccountTypePanel(id);
						MarkupContainer markupContainer = form.getParent().getParent();
						markupContainer.remove(form.getParent());
						markupContainer.add(viewAccountTypePanel);
						viewAccountTypePanel.setOutputMarkupId(true);
						viewAccountTypePanel.setOutputMarkupPlaceholderTag(true);
						TemplatePage.setContent(viewAccountTypePanel);
						log.debug(accountTypeModel);
					}
					else
					{
						error("Account Type could not be added/Updated...");
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
				viewAccountTypePanel = new ViewAccountTypePanel(id);
				MarkupContainer markupContainer = form.getParent().getParent();
				markupContainer.remove(form.getParent());
				markupContainer.add(viewAccountTypePanel);
				viewAccountTypePanel.setOutputMarkupId(true);
				viewAccountTypePanel.setOutputMarkupPlaceholderTag(true);
				TemplatePage.setContent(viewAccountTypePanel);
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