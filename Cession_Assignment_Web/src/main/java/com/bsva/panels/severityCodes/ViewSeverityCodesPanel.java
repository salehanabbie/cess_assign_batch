package com.bsva.panels.severityCodes;
import java.text.ParseException;
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
import com.bsva.commons.model.SeverityCodesModel;
import com.bsva.controller.Controller;
import com.bsva.models.ClientSessionModel;
import com.bsva.models.WebSeverityCodesModel;
import com.bsva.panels.CheckBoxColumn;
import com.bsva.provider.RejectReasonProvider;
import com.bsva.provider.SeverityCodesProvider;
import com.bsva.validators.FieldNumberTextFieldValidator;
import com.bsva.validators.TextFieldValidator;

/**
 * 
 * @author NhlanhlaM
 *
 */
public class ViewSeverityCodesPanel extends Panel
{
	private static final long serialVersionUID = 1L;
	Controller controller;
	public static Logger log = Logger.getLogger(ViewSeverityCodesPanel.class);
	public static String id;
	private Form form;
	private Set<WebSeverityCodesModel> selected = new HashSet<WebSeverityCodesModel>();
	private MaintainSeverityCodesPanel maintainSeverityCodesPanel;
	private Button updateButton;
	private Button addButton;
	private Button searchButton;
	private Button viewAllButton;
	private TextField<String> searchText;
	String action;
	private List<WebSeverityCodesModel> webSeverityCodesModelList;
	private List<SeverityCodesModel> severityCodesModelList;
	private ClientSessionModel clientSessionModel;
    public static Session session;
    private String userRole;
	
	public ViewSeverityCodesPanel(String id) 
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
		form = new Form("viewSeverityCodesPanel");
		form.add(createDataTable(new SeverityCodesProvider()));
		
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
					for (WebSeverityCodesModel webSeverityCodesModel : selected) 
					{
						maintainSeverityCodesPanel = new MaintainSeverityCodesPanel(id, webSeverityCodesModel, "update");
						MarkupContainer markupContainer = form.getParent().getParent();
						markupContainer.remove(form.getParent());
						markupContainer.add(maintainSeverityCodesPanel);
						markupContainer.setOutputMarkupId(true);
						markupContainer.setOutputMarkupPlaceholderTag(true);
						TemplatePage.setContent(maintainSeverityCodesPanel);	
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
				maintainSeverityCodesPanel = new MaintainSeverityCodesPanel(id, "create");
				maintainSeverityCodesPanel.setOutputMarkupId(true);
				maintainSeverityCodesPanel.setOutputMarkupPlaceholderTag(true);
				MarkupContainer markupContainer = form .getParent().getParent();
				markupContainer.remove(form.getParent());
				markupContainer.add(maintainSeverityCodesPanel);
				TemplatePage.setContent(maintainSeverityCodesPanel);
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
				log.info("Search Text: " + searchText.getValue());
				if(searchText.getValue() == null || searchText.getValue().isEmpty())
				{
					error("Search Field is not populated.");
				}
				else
				{
					form.remove(form.get("dataTable"));
					form.add(createDataTable(new SeverityCodesProvider(searchText.getValue())));
				}
			}
		};			
		searchText = new TextField<String>("searchText", new Model<String>());
		searchText.add(StringValidator.maximumLength(2));
		searchText.add(new FieldNumberTextFieldValidator());
					
		viewAllButton = new Button ("viewAllButton")
		{
			private static final long SerialVersionUID = 1L;
			
			@Override		
			public void onSubmit()
			{
				viewAllButton.setEnabled(false);	
				form.remove(form.get("dataTable"));
				form.add(createDataTable(new SeverityCodesProvider()));
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
			
	private DefaultDataTable createDataTable(SeverityCodesProvider severityCodesProvider) 
	{
		DefaultDataTable dataTable;
		List<IColumn> columns = new ArrayList<IColumn>();
		IColumn column = new CheckBoxColumn<WebSeverityCodesModel>(Model.of("Select"))
		{
			private static final long serialVersionUID = 1L;
				
			@Override
			protected IModel newCheckBoxModel(final IModel<WebSeverityCodesModel> rowModel) 
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
		columns.add(new PropertyColumn(new Model<String>("Severity Code"), "severityCode", "severityCode"));
		columns.add(new PropertyColumn(new Model<String>("Severity Code Descscription"), "severityCodeDesc", "severityCodeDesc"));
		columns.add(new PropertyColumn(new Model<String>("Active Ind"),	"activeInd", "activeInd"));
		dataTable = new DefaultDataTable("dataTable", columns, severityCodesProvider, 10);
		return dataTable;		
	}
			
}