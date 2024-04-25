package com.bsva.panels.fileSizeLimit;

import java.util.ArrayList;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.Session;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.extensions.model.AbstractCheckBoxModel;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.validation.validator.PatternValidator;
import org.apache.wicket.validation.validator.StringValidator;

import com.bsva.TemplatePage;
import com.bsva.controller.Controller;
import com.bsva.models.ClientSessionModel;
import com.bsva.models.WebSysCtrlFileSizeLimitModel;
import com.bsva.panels.CheckBoxColumn;
import com.bsva.panels.frequencyCodes.MaintainFrequencyCodesPanel;
import com.bsva.provider.SysCtrlFileSizeLimitProvider;
import com.bsva.validators.FieldNumberTextFieldValidator;


public class ViewFileSizeLimitPanel extends Panel
{

	Controller controller;
	private static final long serialVersionUID = 1L;
	public static Logger log = Logger.getLogger(ViewFileSizeLimitPanel.class);
	public static String id;
	private Form form;
	private Set<WebSysCtrlFileSizeLimitModel> selected = new HashSet<WebSysCtrlFileSizeLimitModel>();
	private Button updateButton;
	private Button addButton;
	private Button searchButton;
	private Button viewAllButton;
	private TextField<String> searchMemberIdText,searchsubServceText;
	MaintainFileSizeLimitPanel maintainFileSizeLimitPanel;
	private ClientSessionModel clientSessionModel;
    public static Session session;
    private String userRole;
    String textValidator = "([a-zA-Z0-9 \\-._()]+)";
    
	public ViewFileSizeLimitPanel(String id) {
		super(id);
		this.id=id;
		initializeComponents();
	}

	private void initializeComponents() 
	{
		controller = new Controller();
		session = getSession();
		clientSessionModel = (ClientSessionModel) session.getAttribute("role");
		userRole = clientSessionModel.getUserRole();
		form = new Form("form");
		form.add(createDataTable(new SysCtrlFileSizeLimitProvider()));
		updateButton = new Button("updateButton") 
		{
			@Override
			public void onSubmit() 
			{
				if (selected.size() > 1) 
				{
					error("Please select only one record...");
				} 
				else if (selected.size() <= 0) 
				{
					error("No record was selected...");
				} 
				else 
				{
					for (WebSysCtrlFileSizeLimitModel webSysCtrlFileSizeLimitModel : selected) 
					{
						maintainFileSizeLimitPanel = new MaintainFileSizeLimitPanel(id, webSysCtrlFileSizeLimitModel, "update");
						MarkupContainer markupContainer = form.getParent().getParent();
						markupContainer.remove(form.getParent());
						markupContainer.add(maintainFileSizeLimitPanel);
						maintainFileSizeLimitPanel.setOutputMarkupId(true);
						maintainFileSizeLimitPanel.setOutputMarkupPlaceholderTag(true);
						TemplatePage.setContent(maintainFileSizeLimitPanel);
						break;
					}
				}
			}
		};
		updateButton.setDefaultFormProcessing(false);
		if(userRole.equalsIgnoreCase(controller.getProperty("MDT.OPS")))
        {
            updateButton.setEnabled(false);
        }
		
		addButton = new Button("addButton")
		{
			@Override
			public void onSubmit()
			{
				maintainFileSizeLimitPanel = new MaintainFileSizeLimitPanel(id, "create");
				MarkupContainer markupContainer = form.getParent().getParent();
				markupContainer.remove(form.getParent());
				markupContainer.add(maintainFileSizeLimitPanel);
				maintainFileSizeLimitPanel.setOutputMarkupId(true);
				maintainFileSizeLimitPanel.setOutputMarkupPlaceholderTag(true);
				TemplatePage.setContent(maintainFileSizeLimitPanel);
			}
		};
		addButton.setDefaultFormProcessing(false);
		if(userRole.equalsIgnoreCase(controller.getProperty("MDT.OPS")))
       {
           addButton.setEnabled(false);
       }
		

		searchButton = new Button("searchButton") 
		{
			@Override
			public void onSubmit() 
			{
				viewAllButton.setEnabled(true);
				log.debug("Search Text: " + searchMemberIdText.getValue());
				if((searchMemberIdText.getValue() == null || searchMemberIdText.getValue().isEmpty()) &&(searchsubServceText.getValue()== null || searchsubServceText.getValue().isEmpty()))
				{
					error("Please enter Member ID and Sub Service to view transactions"); 
					form.add(createDataTable(new SysCtrlFileSizeLimitProvider()));
				}
				if((searchMemberIdText.getValue() != null) && (searchsubServceText.getValue() == null || searchsubServceText.getValue().isEmpty()))
				{
					error("Please enter Member ID and Sub Service to view transactions");
				}
				else
				{
					String  memberId = searchMemberIdText.getValue();
					String  serviceId = searchsubServceText.getValue();
					form.remove(form.get("dataTable"));
					form.add(createDataTable(new SysCtrlFileSizeLimitProvider(memberId, serviceId)));
				}
			}
		};
		searchMemberIdText = new TextField<String>("searchMemberIdText", new Model<String>());
		searchMemberIdText.add(StringValidator.maximumLength(6));
		searchMemberIdText.add(new FieldNumberTextFieldValidator());
		
		searchsubServceText = new TextField<String>("searchsubServceText", new Model<String>());
		searchsubServceText.add(StringValidator.maximumLength(6));
		searchsubServceText.add(new PatternValidator(textValidator));

		viewAllButton = new Button ("viewAllButton")
		{
			private static final long SerialVersionUID = 1L;
			
			@Override
			public void onSubmit()
			{
				viewAllButton.setEnabled(false);
				form.remove(form.get("dataTable"));
				form.add(createDataTable(new SysCtrlFileSizeLimitProvider()));
			}
		};
		
		form.add(updateButton);
		form.add(addButton);
		form.add(searchButton);
		form.add(searchsubServceText);
		form.add(searchMemberIdText);
		form.add(viewAllButton);
		viewAllButton.setEnabled(false);
		add(form);
		add(new FeedbackPanel("feedbackPanel"));
	}

	private DefaultDataTable createDataTable(SysCtrlFileSizeLimitProvider sysCtrlFileSizeLimitProvider)
	{
		DefaultDataTable dataTable;
		List<IColumn> columns = new ArrayList<IColumn>();
		IColumn column = new CheckBoxColumn<WebSysCtrlFileSizeLimitModel>(Model.of("Select")) 
		{
			@Override
			protected IModel newCheckBoxModel(final IModel<WebSysCtrlFileSizeLimitModel> rowModel) 
			{
				return new AbstractCheckBoxModel() 
				{
					@Override
					public boolean isSelected() 
					{
						return selected.contains(rowModel.getObject());
					}

					@Override
					public void select()
					{
						if (selected.size() > 0) 
						{
							unselect();
						}
						selected.add(rowModel.getObject());
					}

					@Override
					public void unselect() 
					{
						selected.remove(rowModel.getObject());
					}

					@Override
					public void detach() 
					{
						rowModel.detach();
					}
				};
			}
		};
		columns.add(column);
		columns.add(new PropertyColumn(new Model<String>("Member ID"),"memberId", "memberId"));
		columns.add(new PropertyColumn(new Model<String>("Sub Service"), "subService", "subService"));
		columns.add(new PropertyColumn(new Model<String>("Limit"),"limit", "limit"));
		columns.add(new PropertyColumn(new Model<String>("Direction"),"direction", "direction"));
		columns.add(new PropertyColumn(new Model<String>("Active ID"),"activeId", "activeId"));
		dataTable = new DefaultDataTable("dataTable", columns,sysCtrlFileSizeLimitProvider, 10);
		return dataTable;
}
}
