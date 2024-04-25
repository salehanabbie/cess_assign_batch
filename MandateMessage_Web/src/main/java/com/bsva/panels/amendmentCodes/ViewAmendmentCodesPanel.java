package com.bsva.panels.amendmentCodes;
import java.io.Serializable;
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
import org.apache.wicket.validation.validator.StringValidator;
import com.bsva.TemplatePage;
import com.bsva.commons.model.AmendmentCodesModel;
import com.bsva.controller.Controller;
import com.bsva.models.ClientSessionModel;
import com.bsva.models.WebAccountTypeModel;
import com.bsva.models.WebAmendmentCodesModel;
import com.bsva.panels.CheckBoxColumn;
import com.bsva.provider.AccountTypeProvider;
import com.bsva.provider.AmendmentCodesProvider;
import com.bsva.validators.TextFieldValidator;

public class ViewAmendmentCodesPanel extends Panel implements Serializable
{
	private static final long serialVersionUID = 1L;
	Controller controller;
	public static Logger log = Logger.getLogger(ViewAmendmentCodesPanel.class);
	public static String id;
	private Form form;
	private Set<WebAmendmentCodesModel> selected = new HashSet<WebAmendmentCodesModel>();
	private MaintainAmendmentCodesPanel maintainAmendmentCodesPanel;	
	private Button updateButton;
	private Button addButton;
	private Button searchButton;
	private Button viewAllButton;
	private TextField<String> searchText;
	String action;
	private List<WebAmendmentCodesModel> webAmendmentCodesModelList;
	private List<AmendmentCodesModel> amendmentCodesModel;
	private ClientSessionModel clientSessionModel;
	public static Session session;
	private String userRole;
	
	public ViewAmendmentCodesPanel(String id)
	{
		super(id);
		this.id = id;
	    initializeComponents();	
	}
	
	private void initializeComponents() 
	{
		controller = new Controller();
		session = getSession();
		clientSessionModel = (ClientSessionModel) session.getAttribute("role");
		userRole = clientSessionModel.getUserRole();

		form = new Form("viewAmendmentCodesPanel");
		form.add(createDataTable(new AmendmentCodesProvider()));
			
		updateButton = new Button("updateButton")
		{
			private static final long serialVersionUID = 1L;
			
			@Override
			public void onSubmit()
			{
				if(selected.size() > 1)
				{
					error("Please select only one record...");
				}
				else if (selected.size() <= 0)
				{
					error("No record was selected...");
				}
				else 
				{
					for (WebAmendmentCodesModel webAmendmentCodesModel : selected) 
					{
						maintainAmendmentCodesPanel = new MaintainAmendmentCodesPanel(id, webAmendmentCodesModel, "update");
						MarkupContainer markupContainer = form.getParent().getParent();
						markupContainer.remove(form.getParent());
						markupContainer.add(maintainAmendmentCodesPanel);
						markupContainer.setOutputMarkupId(true);
						markupContainer.setOutputMarkupPlaceholderTag(true);
						TemplatePage.setContent(maintainAmendmentCodesPanel);
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
				maintainAmendmentCodesPanel = new MaintainAmendmentCodesPanel(id, "create");
				MarkupContainer markupContainer = form .getParent().getParent();
				markupContainer.remove(form.getParent());
				markupContainer.add(maintainAmendmentCodesPanel);
				maintainAmendmentCodesPanel.setOutputMarkupId(true);
				maintainAmendmentCodesPanel.setOutputMarkupPlaceholderTag(true);
				TemplatePage.setContent(maintainAmendmentCodesPanel);
			}		
		};
		addButton.setDefaultFormProcessing(false);
		if(userRole.equalsIgnoreCase(controller.getProperty("MDT.OPS")))
        {
            addButton.setEnabled(false);
        }
				
		searchButton = new Button ("searchButton")
		{
			private static final long SerialVersionUID = 1L;
			
			@Override	
			public void onSubmit()
			{
				viewAllButton.setEnabled(true);
				log.debug("Search Text: " + searchText.getValue());
				if(searchText.getValue() == null || searchText.getValue().isEmpty())
				{
					error("Search Field is not populated.");
				}
				else
				{
					form.remove(form.get("dataTable"));
					form.add(createDataTable(new AmendmentCodesProvider(searchText.getValue().trim().toUpperCase())));
				}
			}
		};	
		searchText = new TextField<String>("searchText", new Model<String>());
		searchText.add(StringValidator.maximumLength(4));
		searchText.add(new TextFieldValidator());
				
		viewAllButton = new Button ("viewAllButton")
		{
			private static final long SerialVersionUID = 1L;
			
			@Override	
			public void onSubmit()
			{
				viewAllButton.setEnabled(false);		
				form.remove(form.get("dataTable"));
				form.add(createDataTable(new AmendmentCodesProvider()));
			}
		};
		
		form.add(updateButton);
		form.add(addButton);
		form.add(searchButton);
		form.add(searchText);
		form.add(viewAllButton);
		viewAllButton.setEnabled(false);
		add(form);	
		add(new FeedbackPanel("feedbackPanel"));
			
	}
		
	private DefaultDataTable createDataTable (AmendmentCodesProvider amendmentCodesProvider)
	{
		DefaultDataTable dataTable;	
		List<IColumn> columns = new ArrayList<IColumn>();
		IColumn column = new CheckBoxColumn<WebAmendmentCodesModel>(Model.of("Select"))
		{
			private static final long serialVersionUID = 1L;	
			protected IModel newCheckBoxModel(final IModel<WebAmendmentCodesModel> rowModel) 
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
		columns.add(new PropertyColumn(new Model<String>("Amendment Code"), "amendmentCodes", "amendmentCodes"));
		columns.add(new PropertyColumn(new Model<String>("Amendment Code  Description"), "amendmentCodesDescription","amendmentCodesDescription"));
		columns.add(new PropertyColumn(new Model<String>("Active Ind"),"activeInd", "activeInd"));
		dataTable = new DefaultDataTable("dataTable", columns, amendmentCodesProvider, 10);
		return dataTable;		
	}
}