package com.bsva.panels.reports;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.log4j.Logger;
import org.apache.wicket.MarkupContainer;
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
import com.bsva.models.WebReportsNamesModel;
import com.bsva.panels.CheckBoxColumn;
import com.bsva.provider.ProcessStatusProvider;
import com.bsva.provider.ReportNamesProvider;

public class ViewReportConfgScreen extends Panel
{
	public static Logger log = Logger.getLogger(ViewReportConfgScreen.class);
	private static final long serialVersionUID = 1L;
	private Button updateButton;
	private Button addButton;
	private Button cancelButton;
	private Button searchButton;
	private Button viewAllButton;
	public static String id;
	private Form form;
	private TextField<String> searchText;
	private Set<WebReportsNamesModel> selected = new HashSet<WebReportsNamesModel>();
	MaintainReportCnfgScreen maintainReportCnfgScreen;

	public ViewReportConfgScreen(String id) 
	{
		super(id);
		this.id = id;
		initializeComponents();
	}

	private void initializeComponents() 
	{
		form = new Form("ReportsNamesForm");
		form.add(createDataTable(new ReportNamesProvider()));
		
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
					for (WebReportsNamesModel webReportsNamesModel : selected) 
					{
						maintainReportCnfgScreen = new MaintainReportCnfgScreen(id, webReportsNamesModel, "update");
						MarkupContainer markupContainer = form.getParent().getParent();
						markupContainer.remove(form.getParent());
						markupContainer.add(maintainReportCnfgScreen);
						maintainReportCnfgScreen.setOutputMarkupId(true);
						maintainReportCnfgScreen.setOutputMarkupPlaceholderTag(true);
						TemplatePage.setContent(maintainReportCnfgScreen);
						break;
					}
				}
			}
		};
		updateButton.setDefaultFormProcessing(false);
		
		addButton = new Button("addButton") 
		{
			@Override
			public void onSubmit() 
			{
				maintainReportCnfgScreen = new MaintainReportCnfgScreen(id, "create");
				MarkupContainer markupContainer = form.getParent().getParent();
				markupContainer.remove(form.getParent());
				markupContainer.add(maintainReportCnfgScreen);
				maintainReportCnfgScreen.setOutputMarkupId(true);
				maintainReportCnfgScreen.setOutputMarkupPlaceholderTag(true);
				TemplatePage.setContent(maintainReportCnfgScreen);
			}
		};
		addButton.setDefaultFormProcessing(false);	
		
		searchButton = new Button("searchButton") 
		{
			@Override
			public void onSubmit() 
			{
				viewAllButton.setEnabled(true);
				log.debug("Search Text: " + searchText.getValue());
				if(searchText.getValue() == null || searchText.getValue().isEmpty())
				{
					error("Search Field is not populated");
				}
				else
				{
					form.remove(form.get("dataTable"));
					form.add(createDataTable(new ReportNamesProvider(searchText.getValue().toUpperCase())));
				}
			}
		};
		searchText = new TextField<String>("searchText", new Model<String>());
		searchText.add(StringValidator.maximumLength(6));
		
		viewAllButton = new Button ("viewAllButton")
		{
			private static final long SerialVersionUID = 1L;
			@Override
			public void onSubmit()
			{
				viewAllButton.setEnabled(false);
				form.remove(form.get("dataTable"));
				form.add(createDataTable(new ReportNamesProvider()));
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

	private DefaultDataTable createDataTable(ReportNamesProvider reportNamesProvider)
	{
		DefaultDataTable dataTable;

		List<IColumn> columns = new ArrayList<IColumn>();
		IColumn column = new CheckBoxColumn<WebReportsNamesModel>(Model.of("Select")) 
		{
			@Override
			protected IModel newCheckBoxModel(final IModel<WebReportsNamesModel> rowModel) 
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
		columns.add(new PropertyColumn(new Model<String>("Report Number"),"reportNr","reportNr"));
		columns.add(new PropertyColumn(new Model<String>("Report Name"), "reportName","reportName"));
		columns.add(new PropertyColumn(new Model<String>("Active Ind"),"activeInd", "activeInd"));
		columns.add(new PropertyColumn(new Model<String>("Internal Ind"),"internalInd", "internalInd"));
		dataTable = new DefaultDataTable("dataTable", columns,reportNamesProvider, 10);
		return dataTable;
	 }	
}


