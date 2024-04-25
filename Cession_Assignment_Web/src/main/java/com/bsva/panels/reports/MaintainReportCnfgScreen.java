package com.bsva.panels.reports;
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
import com.bsva.commons.model.ReportsNamesModel;
import com.bsva.controller.Controller;
import com.bsva.models.ClientSessionModel;
import com.bsva.models.WebAuditTrackingModel;
import com.bsva.models.WebReportsNamesModel;
import com.bsva.translator.WebAdminTranslator;
import com.bsva.validators.TextFieldValidator;

/**
 * @author nosiphos
 *
 */
public class MaintainReportCnfgScreen extends Panel 
{
	ViewReportConfgScreen viewReportConfgScreen;
	private static final long serialVersionUID = 1L;
    public static String id;
	public static Logger log = Logger.getLogger(MaintainReportCnfgScreen.class);
	private String screenName = "Report Names";
	private ClientSessionModel clientSessionModel;
	private String userName;
	public static Session session;
	private Form form;
	private Button saveOrUpdateButton;
	private Button cancelButton;
	private TextField<String> reportName;
	private TextField<String> reportNr;
	private DropDownChoice<String> activeInd;
	private DropDownChoice<String> internalInd;
	private String selectedIndicator;
	private String selectedIndicator1;
	private String action;
	private WebReportsNamesModel webReportsNamesModel,originalWebReportNamesModel;
	Label reportNameLabel,reportNumberLabel,activeIndLabel,internalIndLabel, legendLbl;
	WebAuditTrackingModel webAuditTrackingModel;
	String textValidator = "([a-zA-Z0-9 \\-._()]+)";
	
	public MaintainReportCnfgScreen(String id, String action) 
	{
		super(id);
		this.action = action;
		this.id=id;
		initializeComponents();
		log.debug("Screen ID: "+ action);
	}

	public MaintainReportCnfgScreen(String id,WebReportsNamesModel webReportsNamesModel, String action) 
	{
		super(id);
		this.action = action;
		this.id=id;
		this.webReportsNamesModel = webReportsNamesModel;
		this.originalWebReportNamesModel = webReportsNamesModel;
		initializeComponents();
		log.debug("Screen ID: "+ action);
	}

	private void initializeComponents() 
	{
		legendLbl = new Label("legend","Report Types  - "+action.toUpperCase());
		add(legendLbl);
		form = new Form("maintainReportCnfgScreen");
		
		reportNameLabel =new Label("reportNameLbl","Report Name");
		form.add(reportNameLabel);
		reportNumberLabel= new Label("reportNoLbl","Report Number");
		form.add(reportNumberLabel);
		activeIndLabel = new Label("label3","Active Indicator");
		form.add(activeIndLabel);
		internalIndLabel=new Label("label4","Internal Indicator");
		form.add(internalIndLabel);
		
			reportNr = new  TextField<String>("reportNr",  new Model<String>(webReportsNamesModel ==null ? "" : webReportsNamesModel.getReportNr()));
			reportNr.setRequired(true);
			reportNr.add(StringValidator.maximumLength(6));
			reportNr.add(new PatternValidator(textValidator));	
			if(action == "update")
			{
				 reportNr.setEnabled(false);
			}
			form.add(reportNr);
			
			reportName = new  TextField<String>("reportName",  new Model<String>(webReportsNamesModel ==null ? "" : webReportsNamesModel.getReportName()));
			reportName.setRequired(true);
			reportName.add(StringValidator.maximumLength(60));
			reportName.add(new PatternValidator(textValidator));
			form.add(reportName);
				
			List<String> indicators = new ArrayList<String>();
			indicators.add("ACTIVE");
			indicators.add("INACTIVE");
			if(webReportsNamesModel != null)
			{
				if(webReportsNamesModel.getActiveInd() != null && webReportsNamesModel.getActiveInd().equals("Y"))
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
			
			List<String> indicator1 = new ArrayList<String>();
			indicator1.add("ACTIVE");
			indicator1.add("INACTIVE");
			if(webReportsNamesModel != null)
			{
				if(webReportsNamesModel.getInternalInd() != null && webReportsNamesModel.getInternalInd().equals("Y"))
				{
					selectedIndicator1 = "ACTIVE";
				}
				else
				{
					selectedIndicator1 = "INACTIVE";
				}
			}
			else
			{
				selectedIndicator1="ACTIVE";
			}
			internalInd = new DropDownChoice<String>("internalInd", new Model<String>(selectedIndicator1), indicator1);
			internalInd.setRequired(true);
			if(action.equalsIgnoreCase("create"))
			{
				internalInd.setEnabled(false);
			}
			form.add(internalInd);
			
			saveOrUpdateButton = new Button("saveButton")
			{
				@Override
				public void onSubmit() 
				{
					try
					{
						boolean results = false;
						boolean saved = false;
						WebReportsNamesModel localWebModel = null;
						webAuditTrackingModel = new WebAuditTrackingModel();
						session = getSession();
				        clientSessionModel = (ClientSessionModel) session.getAttribute("role");
				        userName = clientSessionModel.getUsername();
						if(action.equals("update"))
						{
							localWebModel = new WebReportsNamesModel(webReportsNamesModel.getReportName());
						}
						else
						{
							localWebModel = new WebReportsNamesModel();
						}
						localWebModel.setReportName(reportName.getValue().toUpperCase());
						localWebModel.setReportNr  (reportNr.getValue().toUpperCase());
						localWebModel.setActiveInd(activeInd.getModelObject() == "ACTIVE" ? "Y" : "N");
						localWebModel.setInternalInd(internalInd.getModelObject() == "ACTIVE" ? "Y" : "N");
						if(action.equalsIgnoreCase("create"))
						{
							localWebModel.setCreatedBy(userName);
							localWebModel.setCreatedDate(new Date());
						}
						else
						{
							localWebModel.setCreatedBy(webReportsNamesModel.getCreatedBy());
							localWebModel.setCreatedDate(webReportsNamesModel.getCreatedDate());
						}
						localWebModel.setModifiedBy(userName);
						localWebModel.setModifiedDate(new Date());
						
						ReportsNamesModel createLocalModel = new WebAdminTranslator().translateWebReportNamesToCommonsModel(localWebModel);
						log.debug("Commons model"+ createLocalModel);
						Controller controller = new Controller();
						results = controller.createReportNames(createLocalModel);
				        log.debug("===============================originalWebReportNamesModel old value =================:"+originalWebReportNamesModel);
				        log.debug("===============================localWebModel new value=================:"+localWebModel);
				        if(originalWebReportNamesModel != null)
				        {
				        	if(originalWebReportNamesModel.getReportNr()!= localWebModel.getReportNr())
							{
								webAuditTrackingModel.setOldValue(originalWebReportNamesModel.getReportNr());
								webAuditTrackingModel.setNewValue(localWebModel.getReportNr());
							}
							if(originalWebReportNamesModel.getActiveInd()!= localWebModel.getActiveInd())
							{
								webAuditTrackingModel.setOldValue(originalWebReportNamesModel.getActiveInd());
								webAuditTrackingModel.setNewValue(localWebModel.getActiveInd());
							}
							if(originalWebReportNamesModel.getInternalInd()!= localWebModel.getInternalInd())
							{
								webAuditTrackingModel.setOldValue(originalWebReportNamesModel.getInternalInd());
								webAuditTrackingModel.setNewValue(localWebModel.getInternalInd());
							}
				        }
				        webAuditTrackingModel.setColumnName(reportNumberLabel.getDefaultModelObjectAsString());
				        webAuditTrackingModel.setColumnName(activeIndLabel.getDefaultModelObjectAsString());
				        webAuditTrackingModel.setColumnName(internalIndLabel.getDefaultModelObjectAsString());
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
								error("Report Name Created Successfully.");
							}
							else
							{
								info("Report Name Updated Successfully.");
							}
							MarkupContainer markupContainer = form.getParent().getParent();
							viewReportConfgScreen=new ViewReportConfgScreen(id);
							markupContainer.remove(form.getParent());
							markupContainer.add(viewReportConfgScreen);
							viewReportConfgScreen.setOutputMarkupId(true);
							viewReportConfgScreen.setOutputMarkupPlaceholderTag(true);
							TemplatePage.setContent(viewReportConfgScreen);
							log.debug(createLocalModel);
						}
						else
						{
							error("Report Name could not be added/update...");
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
				public void onSubmit() {
					viewReportConfgScreen=new ViewReportConfgScreen(id);
					MarkupContainer markupContainer = form.getParent().getParent();
					markupContainer.remove(form.getParent());
					markupContainer.add(viewReportConfgScreen);
					viewReportConfgScreen.setOutputMarkupId(true);
					viewReportConfgScreen.setOutputMarkupPlaceholderTag(true);
					TemplatePage.setContent(viewReportConfgScreen);
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
