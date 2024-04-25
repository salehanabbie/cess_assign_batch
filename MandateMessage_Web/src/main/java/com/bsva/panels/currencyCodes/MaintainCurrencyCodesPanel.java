package com.bsva.panels.currencyCodes;

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
import org.apache.wicket.validation.validator.PatternValidator;
import org.apache.wicket.validation.validator.StringValidator;

import com.bsva.TemplatePage;
import com.bsva.commons.model.AuditTrackingModel;
import com.bsva.commons.model.CurrencyCodesModel;
import com.bsva.controller.Controller;
import com.bsva.models.ClientSessionModel;
import com.bsva.models.WebAuditTrackingModel;
import com.bsva.models.WebAuthTypeModel;
import com.bsva.models.WebCurrencyCodesModel;
import com.bsva.translator.WebAdminTranslator;
import com.bsva.validators.TextFieldValidator;

/**
 * 
 * @author DimakatsoN
 *
 */

public class MaintainCurrencyCodesPanel extends Panel {

	private static final long serialVersionUID = 1L;

	public static Logger log = Logger.getLogger(MaintainCurrencyCodesPanel.class);
	private String screenName = "Currency Codes";
	private ClientSessionModel clientSessionModel;
	private String userName;
	public static Session session;
	private Form form;
	public static String id;
	private Button saveOrUpdateButton;
	private Button cancelButton;
	private TextField<String> countryCode;
	private TextField<String> alphaCurrCode;
	private TextField<String> numCurrCode;
	private TextField<String> currencyCodeDescription;
	private DropDownChoice<String> activeInd;
	Label countryCodeLabel,currDescLabel,alphaCurrCodeLabel,numCurrCodeLabel,activeIndLabel, legendLbl;
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	private String selectedIndicator;
	private String action;

	WebCurrencyCodesModel webCurrencyCodesModel, originalWebCurrencyCodesModel;
	ViewCurrencyCodesPanel viewcurrencycodes;
	WebAuditTrackingModel webAuditTrackingModel;
	
	String textValidator = "([a-zA-Z \\-._()]+)";
	String numericValidator = "([0-9 .]+)";

	public MaintainCurrencyCodesPanel(String id, String action) 
	{
		super(id);
		this.id = id;
		this.action = action;
		initializeComponents();

	}

	public MaintainCurrencyCodesPanel(String id,WebCurrencyCodesModel webcurrencyCodesModel, String action) 
	{
		super(id);
		this.action = action;
		this.id = id;
		this.webCurrencyCodesModel = webcurrencyCodesModel;
		this.originalWebCurrencyCodesModel = webcurrencyCodesModel;
		initializeComponents();
		log.debug("Screen ID:" + action);
	}

	private void initializeComponents() {
		legendLbl = new Label("legend","Currency Codes  - "+action.toUpperCase());
		add(legendLbl);
		
		form = new Form("maintainCurrencyCodesPanel");

		countryCodeLabel = new Label("label1","Country Code");
		form.add(countryCodeLabel);
		currDescLabel =new Label("label2","Description");
		form.add(currDescLabel);
		alphaCurrCodeLabel= new Label("label3","Alpha Currency Code");
		form.add(alphaCurrCodeLabel);
		numCurrCodeLabel = new Label("label4","Numeric Currency Code");
		form.add(numCurrCodeLabel);
		activeIndLabel=new Label("label5","Active Indicator");
		form.add(activeIndLabel);
		
		countryCode = new TextField<String>("countryCode", new Model<String>(webCurrencyCodesModel == null ? "": webCurrencyCodesModel.getCountryCode()));
		countryCode.setRequired(true);
		countryCode.add(StringValidator.maximumLength(2));
		countryCode.add(new PatternValidator(textValidator));
	//	countryCode.add(new TextFieldValidator());
		if (action == "update") {
			countryCode.setEnabled(false);
		}
		form.add(countryCode);

		currencyCodeDescription = new TextField<String>("currencyCodeDescription", new Model<String>(webCurrencyCodesModel == null ? "": webCurrencyCodesModel.getCurrCodeDesc()));
		currencyCodeDescription.setRequired(true);
		currencyCodeDescription.add(StringValidator.maximumLength(100));
		currencyCodeDescription.add(new PatternValidator(textValidator));
		//currencyCodeDescription.add(new TextFieldValidator());
		form.add(currencyCodeDescription);

		alphaCurrCode = new TextField<String>("alphaCurrCode",new Model<String>(webCurrencyCodesModel == null ? "": webCurrencyCodesModel.getAlphaCurrCode()));
		alphaCurrCode.setRequired(true);
		alphaCurrCode.add(StringValidator.maximumLength(3));
		alphaCurrCode.add(new PatternValidator(textValidator));
	//	alphaCurrCode.add(new TextFieldValidator());
		form.add(alphaCurrCode);

		numCurrCode = new TextField<String>("numCurrCode", new Model<String>(webCurrencyCodesModel == null ? "": webCurrencyCodesModel.getNumCurrCode()));
		numCurrCode.setRequired(true);
		numCurrCode.add(StringValidator.maximumLength(3));
		numCurrCode.add(new PatternValidator(numericValidator));
	//	numCurrCode.add(new TextFieldValidator());
		form.add(numCurrCode);

		List<String> indicators = new ArrayList<String>();
		indicators.add("ACTIVE");
		indicators.add("INACTIVE");

		if (webCurrencyCodesModel != null) {
			if (webCurrencyCodesModel.getActiveInd() != null&& webCurrencyCodesModel.getActiveInd().equals("Y"))
			{
				selectedIndicator = "ACTIVE";
			} else {
				selectedIndicator = "INACTIVE";
			}
		} else {
			selectedIndicator = "ACTIVE";
		}

		activeInd = new DropDownChoice<String>("activeInd", new Model<String>(selectedIndicator), indicators);
		activeInd.setRequired(true);

		if (action.equalsIgnoreCase("create")) {
			activeInd.setEnabled(false);
		}

		form.add(activeInd);

		saveOrUpdateButton = new Button("saveButton") {
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
					WebCurrencyCodesModel webCurrCodesModel = null;
					webAuditTrackingModel = new WebAuditTrackingModel();
					
					session = getSession();
			        clientSessionModel = (ClientSessionModel) session.getAttribute("role");
			        
			        userName = clientSessionModel.getUsername();

					if (action.equals("update")) {
						webCurrCodesModel = new WebCurrencyCodesModel(webCurrencyCodesModel.getCountryCode());
					} else 
					{
						webCurrCodesModel = new WebCurrencyCodesModel();
					}
					webCurrCodesModel.setCountryCode(countryCode.getValue().toUpperCase());
					webCurrCodesModel.setAlphaCurrCode(alphaCurrCode.getValue().toUpperCase());
					webCurrCodesModel.setNumCurrCode(numCurrCode.getValue().toUpperCase());
					webCurrCodesModel.setCurrCodeDesc(currencyCodeDescription.getValue().toUpperCase());
					webCurrCodesModel.setActiveInd(activeInd.getModelObject() == "ACTIVE" ? "Y": "N");
					if(action.equalsIgnoreCase("create"))
					{
						webCurrCodesModel.setCreatedBy(userName);
						webCurrCodesModel.setCreatedDate(new Date());
					}
					else
					{
						webCurrCodesModel.setCreatedBy(webCurrencyCodesModel.getCreatedBy());
						webCurrCodesModel.setCreatedDate(webCurrencyCodesModel.getCreatedDate());
					}
					
					webCurrCodesModel.setModifiedBy(userName);
					webCurrCodesModel.setModifiedDate(new Date());
					
					log.info("Web Model: " + webCurrCodesModel);

					CurrencyCodesModel createCurrencyModel = new WebAdminTranslator().translateWebCurrencyCodesModelToCurrencyCommonsModel(webCurrCodesModel);
					Controller controller = new Controller();
					results = controller.createCurrencyCode(createCurrencyModel);
					
					//Check what has changed.
			        if(originalWebCurrencyCodesModel != null)
			        {
			        	if(originalWebCurrencyCodesModel.getCurrCodeDesc()!= webCurrCodesModel.getCurrCodeDesc())
						{
							webAuditTrackingModel.setOldValue(originalWebCurrencyCodesModel.getCurrCodeDesc());
							webAuditTrackingModel.setNewValue(webCurrCodesModel.getCurrCodeDesc());
						}
					
						if(originalWebCurrencyCodesModel.getAlphaCurrCode()!= webCurrCodesModel.getAlphaCurrCode())
						{
							webAuditTrackingModel.setOldValue(originalWebCurrencyCodesModel.getAlphaCurrCode());
							webAuditTrackingModel.setNewValue(webCurrCodesModel.getAlphaCurrCode());
						}
					
						if(originalWebCurrencyCodesModel.getNumCurrCode()!= webCurrCodesModel.getNumCurrCode())
						{
							webAuditTrackingModel.setOldValue(originalWebCurrencyCodesModel.getNumCurrCode());
							webAuditTrackingModel.setNewValue(webCurrCodesModel.getNumCurrCode());
						}
						if(originalWebCurrencyCodesModel.getActiveInd()!= webCurrCodesModel.getActiveInd())
							{
							webAuditTrackingModel.setOldValue(originalWebCurrencyCodesModel.getActiveInd());
							webAuditTrackingModel.setNewValue(webCurrCodesModel.getActiveInd());
							}
			        }
			        else
			        {
			        	webAuditTrackingModel.setNewValue(webCurrCodesModel.getCurrCodeDesc());
			        	webAuditTrackingModel.setNewValue(webCurrCodesModel.getAlphaCurrCode());
			        	webAuditTrackingModel.setNewValue(webCurrCodesModel.getNumCurrCode());
			        	webAuditTrackingModel.setNewValue(webCurrCodesModel.getActiveInd());
			        }
						
			        	webAuditTrackingModel.setColumnName(currDescLabel.getDefaultModelObjectAsString());
			        	webAuditTrackingModel.setColumnName(alphaCurrCodeLabel.getDefaultModelObjectAsString());
			        	webAuditTrackingModel.setColumnName(numCurrCodeLabel.getDefaultModelObjectAsString());
			        	webAuditTrackingModel.setColumnName(activeIndLabel.getDefaultModelObjectAsString());
						webAuditTrackingModel.setRecordId(new BigDecimal(123));		
						webAuditTrackingModel.setAction(action.toUpperCase());
						webAuditTrackingModel.setUserId(userName);
						webAuditTrackingModel.setActionDate(new Date());
						webAuditTrackingModel.setTableName(screenName);
						
					AuditTrackingModel	 auditTrackingModel = new WebAdminTranslator().translateWebAuditTrackingModelToCommonsModel(webAuditTrackingModel);
					 saved =controller.createAuditTracking(auditTrackingModel);
					
					if (results)
					{
						if(action == "create")
						{
							log.info("in the create method");
							error("Currency Code Created Successfully.");
						}
						else
						{
							info("Currency Code Updated Successfully.");
						}
						//info("Currency Code Added/Updated...");
						viewcurrencycodes = new ViewCurrencyCodesPanel(id);
						MarkupContainer markupContainer = form.getParent().getParent();
						markupContainer.remove(form.getParent());
						markupContainer.add(viewcurrencycodes);
						viewcurrencycodes.setOutputMarkupId(true);
						viewcurrencycodes.setOutputMarkupPlaceholderTag(true);
						TemplatePage.setContent(viewcurrencycodes);
					} else {
						error("Currency Code could not be added/update...");
					}
				} catch (Exception exception) {
					error("Add/Update failed, " + exception.getMessage());
					log.error("Add/Update failed, ", exception);

				}
			}
		};
		cancelButton = new Button("cancelButton") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit() {
				viewcurrencycodes = new ViewCurrencyCodesPanel(id);
				MarkupContainer markupContainer = form.getParent().getParent();
				markupContainer.remove(form.getParent());
				markupContainer.add(viewcurrencycodes);
				viewcurrencycodes.setOutputMarkupId(true);
				viewcurrencycodes.setOutputMarkupPlaceholderTag(true);
				TemplatePage.setContent(viewcurrencycodes);
			}
		};
		cancelButton.setDefaultFormProcessing(false);

		form.add(saveOrUpdateButton);
		form.add(cancelButton);
		add(form);
		// activeInd.setEnabled(false);
		FeedbackPanel feedbackPanel = new FeedbackPanel("feedback_1");
		add(feedbackPanel);
	}

}