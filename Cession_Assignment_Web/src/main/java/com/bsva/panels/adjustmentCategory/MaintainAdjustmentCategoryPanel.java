package com.bsva.panels.adjustmentCategory;
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
import com.bsva.commons.model.AdjustmentCategoryModel;
import com.bsva.commons.model.AuditTrackingModel;
import com.bsva.controller.Controller;
import com.bsva.models.ClientSessionModel;
import com.bsva.models.WebAdjustmentCategoryModel;
import com.bsva.models.WebAuditTrackingModel;
import com.bsva.translator.WebAdminTranslator;
import com.bsva.validators.TextFieldValidator;

/**
 * 
 * @author NhlanhlaM
 *
 */
public class MaintainAdjustmentCategoryPanel extends Panel
{
	private static final long serialVersionUID = 1L;
	public static Logger log = Logger.getLogger(MaintainAdjustmentCategoryPanel.class);
	private DropDownChoice<String> activeInd;
	private String action;
	private static String id;
	private String screenName;
	private ClientSessionModel clientSessionModel;
	private String userName;
	public static Session session;
	private TextField<String> adjustmentCategory;
	private TextField<String> adjustmentCategoryDesc;
	private Button cancelButton;
	private String selectedIndicator;	
	private Button saveOrUpdateButton;
	Label adjustCat,adjustCatDesc,activeIndLabel, legendLbl;
	private WebAdjustmentCategoryModel webAdjustmentCategoryModel,originalWebAdjustmentCategoryModel;
	WebAuditTrackingModel webAuditTrackingModel;
	ViewAdjustmentCategoryPanel viewAdjustmentCategoryPanel;
    private Form form;
    String textValidator = "([a-zA-Z0-9 \\-._()]+)";
    Controller controller;
    
	public MaintainAdjustmentCategoryPanel(String id, String action) 
	{
		super(id);
		this.id = id;
		this.action = action;
		initialiazeComponents();
	}

	public MaintainAdjustmentCategoryPanel (String id, WebAdjustmentCategoryModel webAdjustmentCategoryModel, String action)
	{
		super(id);
		this.action = action;
		this.id = id;
		this.webAdjustmentCategoryModel = webAdjustmentCategoryModel;
		this.originalWebAdjustmentCategoryModel = webAdjustmentCategoryModel;
		initialiazeComponents();
		log.debug("Screen ID:" + action);
	}

	private void initialiazeComponents() 
	{
		controller = new Controller();
		screenName = controller.getProperty("MNTN.SCRNNAME");
		legendLbl = new Label("legend","Adjustment Category  - "+action.toUpperCase());
		add(legendLbl);
		form = new Form("maintainAdjustmentCategoryPanel");
		 
		adjustCat = new Label("label1","Adjustment Category ");
		form.add(adjustCat);
		 
		adjustCatDesc = new Label("label2","Adjustmnet Category Desc");
		form.add(adjustCatDesc);
		 
		activeIndLabel = new Label("label3","Active Indicator ");
		form.add(activeIndLabel);
		 	
		adjustmentCategory =  new TextField<String>("adjustmentCategory", new Model<String>(webAdjustmentCategoryModel == null ? "" : webAdjustmentCategoryModel.getAdjustmentCategory()));
		adjustmentCategory.setRequired(true);
		adjustmentCategory.add(StringValidator.exactLength(1));
		adjustmentCategory.add(new PatternValidator(textValidator));
		if (action == "update")
		{
			adjustmentCategory.setEnabled(false);
		}	
	    form.add(adjustmentCategory);
	    adjustmentCategoryDesc = new TextField<String>("adjustmentCategoryDesc", new Model<String>(webAdjustmentCategoryModel == null ? "" : webAdjustmentCategoryModel.getAdjustmentCategoryDesc()));
	    adjustmentCategoryDesc.setRequired(true);
	    adjustmentCategoryDesc.add(StringValidator.maximumLength(100));
	    adjustmentCategoryDesc.add(new PatternValidator(textValidator));
		form.add(adjustmentCategoryDesc);
			
		List<String> indicators = new ArrayList<String>();
		indicators.add("ACTIVE");
		indicators.add("INACTIVE");
		if (webAdjustmentCategoryModel != null) 
		{
			if (webAdjustmentCategoryModel.getActiveInd() != null && webAdjustmentCategoryModel.getActiveInd().equals("Y"))
			{
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
					WebAdjustmentCategoryModel localWebAdjustmentCategoryModel = null;
					webAuditTrackingModel = new WebAuditTrackingModel();	
					session = getSession();
				    clientSessionModel = (ClientSessionModel) session.getAttribute("role");   
				    userName = clientSessionModel.getUsername();  
					if (action.equals("update"))
					{
						localWebAdjustmentCategoryModel = new WebAdjustmentCategoryModel(webAdjustmentCategoryModel.getAdjustmentCategory());
					} 
					else 
					{
						localWebAdjustmentCategoryModel = new WebAdjustmentCategoryModel();
					}	
					localWebAdjustmentCategoryModel.setAdjustmentCategory(adjustmentCategory.getValue().toUpperCase());
					localWebAdjustmentCategoryModel.setAdjustmentCategoryDesc(adjustmentCategoryDesc.getValue().toUpperCase());
					localWebAdjustmentCategoryModel.setActiveInd(activeInd.getModelObject() == "ACTIVE" ? "Y" : "N");
						
					if(action.equalsIgnoreCase("create"))
					{
						localWebAdjustmentCategoryModel.setCreatedBy(userName);
						localWebAdjustmentCategoryModel.setCreatedDate(new Date());
					}
					else
					{
						localWebAdjustmentCategoryModel.setCreatedBy(webAdjustmentCategoryModel.getCreatedBy());
						localWebAdjustmentCategoryModel.setCreatedDate(webAdjustmentCategoryModel.getCreatedDate());
					}	
					localWebAdjustmentCategoryModel.setModifiedBy(userName);
					localWebAdjustmentCategoryModel.setModifiedDate(new Date());
						
					log.debug("Web Model: " +  localWebAdjustmentCategoryModel);
						
					AdjustmentCategoryModel adjustmentCategoryModel = new WebAdminTranslator().translateWebAdjustmentCategoryModelToCommonsModel(localWebAdjustmentCategoryModel);
					log.debug("Commons Model: " +  adjustmentCategoryModel);
					results = controller.createAdjustmentCategory(adjustmentCategoryModel);
						
				    log.debug("===============================originalWebAdjustmentCategoryModel old value =================:"+originalWebAdjustmentCategoryModel);
				    log.debug("===============================localWebAdjustmentCategoryModel new value=================:"+localWebAdjustmentCategoryModel);
				        
				    if(originalWebAdjustmentCategoryModel != null)
				    {
				        if(originalWebAdjustmentCategoryModel.getAdjustmentCategoryDesc() != localWebAdjustmentCategoryModel.getAdjustmentCategoryDesc())
				        {
							webAuditTrackingModel.setOldValue(originalWebAdjustmentCategoryModel.getAdjustmentCategoryDesc());
							webAuditTrackingModel.setNewValue(localWebAdjustmentCategoryModel.getAdjustmentCategoryDesc());
				        }	
				        if(originalWebAdjustmentCategoryModel.getActiveInd() != localWebAdjustmentCategoryModel.getActiveInd())
				        {
				        	webAuditTrackingModel.setOldValue(originalWebAdjustmentCategoryModel.getActiveInd());
				        	webAuditTrackingModel.setNewValue(localWebAdjustmentCategoryModel.getActiveInd());
				        }
				    }
				    else
				    {
				        webAuditTrackingModel.setNewValue(localWebAdjustmentCategoryModel.getAdjustmentCategoryDesc());
				        webAuditTrackingModel.setNewValue(localWebAdjustmentCategoryModel.getActiveInd());
				    }		
				    webAuditTrackingModel.setColumnName(adjustCatDesc.getDefaultModelObjectAsString());
				    webAuditTrackingModel.setColumnName(activeIndLabel.getDefaultModelObjectAsString());
				    webAuditTrackingModel.setRecordId(new BigDecimal(123));
					webAuditTrackingModel.setAction(action.toUpperCase());
					webAuditTrackingModel.setUserId(userName);
					webAuditTrackingModel.setActionDate(new Date());
					webAuditTrackingModel.setTableName(screenName);
					
					AuditTrackingModel auditTrackingModel = new WebAdminTranslator().translateWebAuditTrackingModelToCommonsModel(webAuditTrackingModel);
					saved = controller.createAuditTracking(auditTrackingModel);
					if(results)
					{
						if(action == "create")
						{
							log.debug("in the create method");
							error("Adjustment Category Created Successfully.");
						}
						else
						{
							info("Adjustment Category Updated Successfully.");
						}
						viewAdjustmentCategoryPanel = new ViewAdjustmentCategoryPanel(id);
						MarkupContainer markupContainer = form.getParent().getParent();
						markupContainer.remove(form.getParent());
						markupContainer.add(viewAdjustmentCategoryPanel);
						viewAdjustmentCategoryPanel.setOutputMarkupId(true);
						viewAdjustmentCategoryPanel.setOutputMarkupPlaceholderTag(true);
						TemplatePage.setContent(viewAdjustmentCategoryPanel);
						log.debug(adjustmentCategoryModel);
						}
					else
					{
						error("Adjustment Categorycould not be added/Updated...");
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
				viewAdjustmentCategoryPanel = new ViewAdjustmentCategoryPanel(id);
				MarkupContainer markupContainer = form.getParent().getParent();
				markupContainer.remove(form.getParent());
				markupContainer.add(viewAdjustmentCategoryPanel);
				viewAdjustmentCategoryPanel.setOutputMarkupId(true);
				viewAdjustmentCategoryPanel.setOutputMarkupPlaceholderTag(true);
				TemplatePage.setContent(viewAdjustmentCategoryPanel);
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