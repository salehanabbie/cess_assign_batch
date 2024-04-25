package com.bsva.panels.accountType;
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
import com.bsva.commons.model.AccountTypeModel;
import com.bsva.controller.Controller;
import com.bsva.models.ClientSessionModel;
import com.bsva.models.WebAccountTypeModel;
import com.bsva.panels.CheckBoxColumn;
import com.bsva.provider.AccountTypeProvider;
import com.bsva.validators.TextFieldValidator;

/**
 * 
 * @author NhlanhlaM
 *
 */
public class ViewAccountTypePanel extends Panel implements Serializable
{
	private static final long serialVersionUID = 1L;
	Controller controller;
	public static Logger log = Logger.getLogger(ViewAccountTypePanel.class);
	public static String id;
	private Form form;
	private Set<WebAccountTypeModel> selected = new HashSet<WebAccountTypeModel>();
	private MaintainAccountTypePanel maintainAccountTypePanel;
	private Button updateButton;
	private Button addButton;
	private Button searchButton;
	private Button viewAllButton;
	private TextField<String> searchText;
	String action;
	private List<WebAccountTypeModel> webAccountTypeModelList;
	private List<AccountTypeModel> accountTypeModel;
	private ClientSessionModel clientSessionModel;
	public static Session session;
	String userRole;
	
	public ViewAccountTypePanel(String id)
	{
		super(id);
		this.id = id;
        initializeComponents();
	}

	private void initializeComponents() 
	{
		Controller controller = new Controller();
		session = getSession();
		clientSessionModel = (ClientSessionModel) session.getAttribute("role");
		userRole = clientSessionModel.getUserRole();
		log.debug("controller.getProperty(Print.Msg)----> "+controller.getProperty("Print.Msg"));
		form = new Form("viewAccountTypePanel");
		form.add(createDataTable(new AccountTypeProvider()));
		
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
					for (WebAccountTypeModel webAccountTypeModel : selected) 
					{
						maintainAccountTypePanel = new MaintainAccountTypePanel(id, webAccountTypeModel, "update");
						MarkupContainer markupContainer = form.getParent().getParent();
						markupContainer.remove(form.getParent());
						markupContainer.add(maintainAccountTypePanel);
						markupContainer.setOutputMarkupId(true);
						markupContainer.setOutputMarkupPlaceholderTag(true);
						TemplatePage.setContent(maintainAccountTypePanel);
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
				maintainAccountTypePanel = new MaintainAccountTypePanel(id, "create");
				MarkupContainer markupContainer = form .getParent().getParent();
				markupContainer.remove(form.getParent());
				markupContainer.add(maintainAccountTypePanel);
				maintainAccountTypePanel.setOutputMarkupId(true);
				maintainAccountTypePanel.setOutputMarkupPlaceholderTag(true);
				TemplatePage.setContent(maintainAccountTypePanel);
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
					form.add(createDataTable(new AccountTypeProvider(searchText.getValue().trim().toUpperCase())));
				}
			}
		};
		searchText = new TextField<String>("searchText", new Model<String>());
		searchText.add(StringValidator.maximumLength(1));
		searchText.add(new TextFieldValidator());
			
		viewAllButton = new Button ("viewAllButton")
		{
			private static final long SerialVersionUID = 1L;
			
			@Override
			public void onSubmit()
			{
				viewAllButton.setEnabled(false);
				form.remove(form.get("dataTable"));
				form.add(createDataTable(new AccountTypeProvider()));
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
	
	private DefaultDataTable createDataTable (AccountTypeProvider accountTypeProvider)
	{
		DefaultDataTable dataTable;
		
		List<IColumn> columns = new ArrayList<IColumn>();
		
		IColumn column = new CheckBoxColumn<WebAccountTypeModel>(Model.of("Select"))
		{
			private static final long serialVersionUID = 1L;
			protected IModel newCheckBoxModel(final IModel<WebAccountTypeModel> rowModel)
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
		columns.add(new PropertyColumn(new Model<String>("Account Type Code"), "accountTypeCode", "accountTypeCode"));
		columns.add(new PropertyColumn(new Model<String>("Account Type Description"), "accountTypeDescription","accountTypeDescription"));
		columns.add(new PropertyColumn(new Model<String>("Active Ind"),"activeInd", "activeInd"));
		dataTable = new DefaultDataTable("dataTable", columns, accountTypeProvider, 10);
		return dataTable;		
	}
}