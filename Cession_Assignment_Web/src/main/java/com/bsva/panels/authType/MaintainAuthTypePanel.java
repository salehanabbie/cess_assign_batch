package com.bsva.panels.authType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.validation.validator.StringValidator;

import com.bsva.TemplatePage;
import com.bsva.commons.model.CnfgAuthTypeModel;
import com.bsva.controller.Controller;
import com.bsva.models.WebAuthTypeModel;
import com.bsva.translator.WebAdminTranslator;
import com.bsva.validators.TextFieldValidator;


public class MaintainAuthTypePanel  extends Panel implements Serializable {

	/**
	 * @author ElelwaniR
	 */
	private static final long serialVersionUID = 1L;
	private String action;
	public static String id;
	public static Logger log = Logger.getLogger(MaintainAuthTypePanel.class);
	
	// form and relevant components 
	private Form form;
	private Button saveButton;
	private Button cancelButton;
	private TextField< String>authType;
	private TextField <String>authTypeDesc;
	private DropDownChoice<String>activeInd;
	private String selectedIndicator;
    ViewAuthTypePanel viewAuthTypePanel;
	WebAuthTypeModel  webAuthTypeModel;
	   
	public MaintainAuthTypePanel(String id,String action) {
		super(id);
		this.id=id;
		this.action=action;
		initializeComponents();
	}

	
	
	public MaintainAuthTypePanel(String id,WebAuthTypeModel webAuthTypeModel, String action)
	{
		super(id);
		this.action = action;
		this.id=id;
		this.webAuthTypeModel = webAuthTypeModel;
		initializeComponents();
		
	}
	
	private void initializeComponents()
	{
		
	 form = new Form ("MaintainAuthTypePanelForm"); 
	 add(form);
		  
		
	 authType = new  TextField<String>("authType",  new Model<String>(webAuthTypeModel ==null ? "" : webAuthTypeModel.getAuthType()));
	 authType.setRequired(true);
	 authType.add(StringValidator.exactLength(4));
	 authType.add(new TextFieldValidator());
	 
	 if(action == "update")
		{
		 authType.setEnabled(false);
		}
		form.add(authType);
		
		authTypeDesc = new TextField<String>("authTypeDesc", new Model<String>(webAuthTypeModel == null ? "" : webAuthTypeModel.getAuthTypeDescription()));
		authTypeDesc.setRequired(true);
		authTypeDesc.add(StringValidator.maximumLength(100));
		authTypeDesc.add(new TextFieldValidator());
		form.add(authTypeDesc);
		
		List<String> indicators = new ArrayList<String>();
		indicators.add("ACTIVE");
		indicators.add("INACTIVE");
		
		if(webAuthTypeModel != null)
		{
			if(webAuthTypeModel.getActiveInd() != null && webAuthTypeModel.getActiveInd().equals("Y"))
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
		
		
		
		saveButton = new Button("saveButton")
		{
			@Override
			public void onSubmit() 
			{
				try
				{
					boolean results = false;
					WebAuthTypeModel localWebModel = null;
					
					if(action.equals("update"))
					{
						localWebModel = new WebAuthTypeModel(webAuthTypeModel.getAuthType());
					}
					else
					{
						localWebModel = new WebAuthTypeModel();
					}
					
					localWebModel.setAuthType(authType.getValue().toUpperCase());
					localWebModel.setAuthTypeDescription(authTypeDesc.getValue());
					localWebModel.setActiveInd(activeInd.getModelObject() == "ACTIVE" ? "Y" : "N");
					
					log.info("Web Model: "+ localWebModel);
				
					CnfgAuthTypeModel  createLocalModel = new WebAdminTranslator().translateWebAuthTypeModeltoAuthTypesCommonsModel(localWebModel);
					log.info("Commons model"+ createLocalModel);
					Controller controller = new Controller();
					results = controller.createAuthType(createLocalModel);
					viewAuthTypePanel=new ViewAuthTypePanel(id);
				
					if(results)
					{
						info("Auth  Type  Added/Updated...");
						MarkupContainer markupContainer = form.getParent().getParent();
						markupContainer.remove(form.getParent());
						markupContainer.add(viewAuthTypePanel);
						viewAuthTypePanel.setOutputMarkupId(true);
						viewAuthTypePanel.setOutputMarkupPlaceholderTag(true);
						TemplatePage.setContent(viewAuthTypePanel);
					log.debug(createLocalModel);
					}
					else
					{
						error("Auth Type Code could not be added/update...");
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
				viewAuthTypePanel=new ViewAuthTypePanel(id);
				MarkupContainer markupContainer = form.getParent().getParent();
				markupContainer.remove(form.getParent());
				markupContainer.add(viewAuthTypePanel);
				viewAuthTypePanel.setOutputMarkupId(true);
				viewAuthTypePanel.setOutputMarkupPlaceholderTag(true);
				TemplatePage.setContent(viewAuthTypePanel);
			}
		};
		cancelButton.setDefaultFormProcessing(false);
		cancelButton.setEnabled(true);

		form.add(saveButton);
		form.add(cancelButton);
		add(form);
		
		
		FeedbackPanel feedbackPanel = new FeedbackPanel("feedback_1");
        add(feedbackPanel);
	}
		
	}
