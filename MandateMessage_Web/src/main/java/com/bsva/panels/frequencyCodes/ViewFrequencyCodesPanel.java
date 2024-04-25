package com.bsva.panels.frequencyCodes;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.extensions.model.AbstractCheckBoxModel;
import org.apache.wicket.validation.validator.StringValidator;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import com.bsva.TemplatePage;
import com.bsva.controller.Controller;
import com.bsva.models.ClientSessionModel;
import com.bsva.models.MandateModel;
import com.bsva.models.WebFrequencyCodesModel;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.Session;

import com.bsva.panels.CheckBoxColumn;
import com.bsva.provider.CurrencyCodesProvider;
import com.bsva.provider.FrequencyCodeProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import com.bsva.models.WebMandateModel;
import org.apache.log4j.Logger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author DimakatsoN
 *
 */
public class ViewFrequencyCodesPanel extends Panel
{
	Controller controller;
	private static final long serialVersionUID = 1L;
	public static Logger log = Logger.getLogger(ViewFrequencyCodesPanel.class);
	public static String id;
	private Form form;
	private Set<WebFrequencyCodesModel> selected = new HashSet<WebFrequencyCodesModel>();
	private List<WebMandateModel> webMandatesList;
	private List<MandateModel> mandatesList;
	private MaintainFrequencyCodesPanel maintainFrequencyCodesPanel;
	private Button updateButton;
	private Button addButton;
	private Button searchButton;
	private Button viewAllButton;
	private TextField<String> searchText;
	private ClientSessionModel clientSessionModel;
    public static Session session;
    private String userRole;

	public ViewFrequencyCodesPanel(String id) 
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
		form = new Form("viewFrequencyCodesPanel");
		form.add(createDataTable(new FrequencyCodeProvider()));

		updateButton = new Button("updateButton")
		{
			private static final long serialVersionUID = 1L;

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
					for (WebFrequencyCodesModel webFrequencyCodesModel : selected) 
					{
						maintainFrequencyCodesPanel = new MaintainFrequencyCodesPanel(id, webFrequencyCodesModel, "update");
						MarkupContainer markupContainer = form.getParent().getParent();
						markupContainer.remove(form.getParent());
						markupContainer.add(maintainFrequencyCodesPanel);
						maintainFrequencyCodesPanel.setOutputMarkupId(true);
						maintainFrequencyCodesPanel.setOutputMarkupPlaceholderTag(true);
						TemplatePage.setContent(maintainFrequencyCodesPanel);
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
				maintainFrequencyCodesPanel = new MaintainFrequencyCodesPanel(id, "create");
				MarkupContainer markupContainer = form.getParent().getParent();
				markupContainer.remove(form.getParent());
				markupContainer.add(maintainFrequencyCodesPanel);
				maintainFrequencyCodesPanel.setOutputMarkupId(true);
				maintainFrequencyCodesPanel.setOutputMarkupPlaceholderTag(true);
				TemplatePage.setContent(maintainFrequencyCodesPanel);
			}
		};
		addButton.setDefaultFormProcessing(false);
		if(userRole.equalsIgnoreCase(controller.getProperty("MDT.OPS")))
        {
            addButton.setEnabled(false);
        }

		searchButton = new Button("searchButton") 
		{
			private static final long serialVersionUID = 1L;

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
					form.add(createDataTable(new FrequencyCodeProvider(searchText.getValue().toUpperCase())));
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
				form.add(createDataTable(new FrequencyCodeProvider()));
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

	private DefaultDataTable createDataTable(FrequencyCodeProvider frequencyCodeProvider)
	{
		DefaultDataTable dataTable;
		List<IColumn> columns = new ArrayList<IColumn>();
		IColumn column = new CheckBoxColumn<WebFrequencyCodesModel>(Model.of("Select"))
		{
			@Override
			protected IModel newCheckBoxModel(final IModel<WebFrequencyCodesModel> rowModel) 
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
		columns.add(new PropertyColumn(new Model<String>(" Frequency Code"),"frequencyCode", "frequencyCode"));
		columns.add(new PropertyColumn(new Model<String>("Frequency Code Description"),"frequencyCodeDescription", "frequencyCodeDescription"));
		columns.add(new PropertyColumn(new Model<String>("Active Ind"),"activeInd", "activeInd"));
		dataTable = new DefaultDataTable("dataTable", columns,frequencyCodeProvider, 10);
		return dataTable;
	}
}