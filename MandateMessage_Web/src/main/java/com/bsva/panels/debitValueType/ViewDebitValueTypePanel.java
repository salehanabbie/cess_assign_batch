package com.bsva.panels.debitValueType;
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
import com.bsva.controller.Controller;
import com.bsva.models.ClientSessionModel;
import com.bsva.models.WebDebitValueTypeModel;
import com.bsva.panels.CheckBoxColumn;
import com.bsva.provider.AdjustmentCategoryProvider;
import com.bsva.provider.DebitValueTypeProvider;

public class ViewDebitValueTypePanel extends Panel 
{
	Controller controller;
	private static final long serialVersionUID = 1L;
	public static Logger log = Logger.getLogger(ViewDebitValueTypePanel.class);
	public static String id;
	private Form form;
	private Set<WebDebitValueTypeModel> selected = new HashSet<WebDebitValueTypeModel>();
	private Button updateButton;
	private Button addButton;
	private Button searchButton;
	private Button viewAllButton;
	private TextField<String> searchText;
	MaintainDebitValueTypePanel maintainDebitValueTypePanel;
	private ClientSessionModel clientSessionModel;
    public static Session session;
    private String userRole;

	public ViewDebitValueTypePanel(String id) 
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
		form = new Form("viewDebitValueTypePanel");
		form.add(createDataTable(new DebitValueTypeProvider()));

		updateButton = new Button("updateButton") 
		{
			@Override
			public void onSubmit() 
			{
				if (selected.size() > 1) 
				{
					error("Please select only one record...");
				} 
				else
				{
					if (selected.size() <= 0) 
					{
						error("No record was selected...");
					}
					else 
					{
						for (WebDebitValueTypeModel webDebitValueTypeModel : selected) 
						{
							maintainDebitValueTypePanel = new MaintainDebitValueTypePanel(id, webDebitValueTypeModel, "update");
							MarkupContainer markupContainer = form.getParent().getParent();
							markupContainer.remove(form.getParent());
							markupContainer.add(maintainDebitValueTypePanel);
							maintainDebitValueTypePanel.setOutputMarkupId(true);
							maintainDebitValueTypePanel.setOutputMarkupPlaceholderTag(true);
							TemplatePage.setContent(maintainDebitValueTypePanel);
							break;
						}
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
				maintainDebitValueTypePanel = new MaintainDebitValueTypePanel(id, "create");
				MarkupContainer markupContainer = form.getParent().getParent();
				markupContainer.remove(form.getParent());
				markupContainer.add(maintainDebitValueTypePanel);
				maintainDebitValueTypePanel.setOutputMarkupId(true);
				maintainDebitValueTypePanel.setOutputMarkupPlaceholderTag(true);
				TemplatePage.setContent(maintainDebitValueTypePanel);
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
				log.debug("Search Text: "+searchText.getValue());
				if(searchText.getValue() == null || searchText.getValue().isEmpty())
				{
					error("Search Field is not populated.");
				}
				else
				{
					form.remove(form.get("dataTable"));
					form.add(createDataTable(new DebitValueTypeProvider(searchText.getValue().toUpperCase())));
				}
			}
		};
		searchText = new TextField<String>("searchText", new Model<String>());
		searchText.add(StringValidator.maximumLength(4));
	
		viewAllButton = new Button ("viewAllButton")
		{
			private static final long SerialVersionUID = 1L;
			
			@Override
			public void onSubmit()
			{
				viewAllButton.setEnabled(false);
				form.remove(form.get("dataTable"));
				form.add(createDataTable(new DebitValueTypeProvider()));
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

	private DefaultDataTable createDataTable(DebitValueTypeProvider debitValueTypeProvider) 
	{
		DefaultDataTable dataTable;
		List<IColumn> columns = new ArrayList<IColumn>();
		IColumn column = new CheckBoxColumn<WebDebitValueTypeModel>(Model.of("Select")) 
		{
			@Override
			protected IModel newCheckBoxModel(final IModel<WebDebitValueTypeModel> rowModel) 
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
		columns.add(new PropertyColumn(new Model<String>("Debit Value Type Code"), "debValueTypeCode","debValueTypeCode"));
		columns.add(new PropertyColumn(new Model<String>("Debit Value Type Description"),"debValueTypeDesc","debValueTypeDesc"));
		columns.add(new PropertyColumn(new Model<String>("Active Ind"),"activeInd", "activeInd"));
		dataTable = new DefaultDataTable("dataTable", columns,debitValueTypeProvider, 10);
		return dataTable;
	}
}