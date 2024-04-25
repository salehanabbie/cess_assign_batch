package com.bsva.panels.reasonCodes;
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
import com.bsva.commons.model.MandatesModel;
import com.bsva.commons.model.ReasonCodesModel;
import com.bsva.controller.Controller;
import com.bsva.models.ClientSessionModel;
import com.bsva.models.MandateModel;
import com.bsva.models.WebReasonCodesModel;
import com.bsva.panels.CheckBoxColumn;
import com.bsva.provider.LocalInstrCodesProvider;
import com.bsva.provider.ReasonCodesProvider;
import com.bsva.validators.TextFieldValidator;

/**
 * 
 * @author DimakatsoN
 *
 */
public class ViewReasonCodesPanel extends Panel 
{
	public static String id;
	private static final long serialVersionUID = 1L;
	public static Logger log = Logger.getLogger(ViewReasonCodesPanel.class);
	private Form form;
	private Set<WebReasonCodesModel> selected = new HashSet<WebReasonCodesModel>();
	private MaintainReasonCodesPanel maintainReasonCodesPanel;
	private Button updateButton;
	private Button addButton;
	private Button searchButton;
	private Button viewAllButton;
	private TextField<String> searchText;
	String action;
	private List<WebReasonCodesModel> webReasonCodesModelList;
	private List<ReasonCodesModel> reasonCodesList;
	private List<MandatesModel> mandatesList;
	private List<MandateModel> webmandatesList;
	private ClientSessionModel clientSessionModel;
    public static Session session;
    private String userRole;
    Controller controller;
    
	public ViewReasonCodesPanel(String id) 
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
		form = new Form("viewReasonCodesPanel");
		form.add(createDataTable(new ReasonCodesProvider()));
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
					for (WebReasonCodesModel reasonCodesModel : selected) 
					{
						maintainReasonCodesPanel = new MaintainReasonCodesPanel(id, reasonCodesModel, "update");
						MarkupContainer markupContainer = form.getParent().getParent();
						markupContainer.remove(form.getParent());
						markupContainer.add(maintainReasonCodesPanel);
						maintainReasonCodesPanel.setOutputMarkupId(true);
						maintainReasonCodesPanel.setOutputMarkupPlaceholderTag(true);
						TemplatePage.setContent(maintainReasonCodesPanel);
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
			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit() 
			{
				maintainReasonCodesPanel = new MaintainReasonCodesPanel(id,"create");
				MarkupContainer markupContainer = form.getParent().getParent();
				markupContainer.remove(form.getParent());
				markupContainer.add(maintainReasonCodesPanel);
				maintainReasonCodesPanel.setOutputMarkupId(true);
				maintainReasonCodesPanel.setOutputMarkupPlaceholderTag(true);
				TemplatePage.setContent(maintainReasonCodesPanel);
			}
		};
		addButton.setDefaultFormProcessing(true);
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
					form.add(createDataTable(new ReasonCodesProvider(searchText.getValue().toUpperCase())));
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
				form.add(createDataTable(new ReasonCodesProvider()));
			}
		};
	
		form.add(updateButton);
		form.add(addButton);
		form.add(searchButton);
		form.add(searchText);
		form.add(viewAllButton);
		viewAllButton.setEnabled(false);
		add(new FeedbackPanel("feedbackPanel"));
		add(form);
	}

	private DefaultDataTable createDataTable(ReasonCodesProvider reasonCodesProvider)
	{
		DefaultDataTable dataTable;
		List<IColumn> columns = new ArrayList<IColumn>();
		IColumn column = new CheckBoxColumn<WebReasonCodesModel>(Model.of("Select"))
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected IModel newCheckBoxModel(final IModel<WebReasonCodesModel> rowModel) 
			{
				return new AbstractCheckBoxModel() 
				{
					private static final long serialVersionUID = 1L;

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
		columns.add(new PropertyColumn(new Model<String>("Reason Code"),"reasonCode", "reasonCode"));
		columns.add(new PropertyColumn(new Model<String>("Reason Code Description"),"reasonCodeDescription","reasonCodeDescription"));
		columns.add(new PropertyColumn(new Model<String>("Active Ind"),"activeInd", "activeInd"));
		dataTable = new DefaultDataTable("dataTable", columns,reasonCodesProvider, 10);
		return dataTable;
	}

}