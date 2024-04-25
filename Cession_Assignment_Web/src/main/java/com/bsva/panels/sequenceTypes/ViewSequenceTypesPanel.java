package com.bsva.panels.sequenceTypes;
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
import com.bsva.commons.model.SequenceTypesModel;
import com.bsva.controller.Controller;
import com.bsva.models.ClientSessionModel;
import com.bsva.models.WebSequenceTypesModel;
import com.bsva.panels.CheckBoxColumn;
import com.bsva.provider.SequenceTypesProvider;
import com.bsva.provider.SystemControlServicesProvider;
import com.bsva.validators.TextFieldValidator;

/**
 * 
 * @author DimakatsoN
 *
 */
public class ViewSequenceTypesPanel extends Panel
{
	Controller controller;
	public static String id;
	private static final long serialVersionUID = 1L;
	public static Logger log = Logger.getLogger(ViewSequenceTypesPanel.class);
	private Form form;
	private Set<WebSequenceTypesModel> selected = new HashSet<WebSequenceTypesModel>();
	ViewSequenceTypesPanel viewSequenceTypesPanel;
	private MaintainSequenceTypesPanel maintainSequenceTypesPanel;
	private Button updateButton;
	private Button addButton;
	private Button searchButton;
	private Button viewAllButton;
	private TextField<String> searchText;
	private List<WebSequenceTypesModel> webSequenceTypesModelList;
	private List<SequenceTypesModel> sequenceTypesList;
	private ClientSessionModel clientSessionModel;
    public static Session session;
    private String userRole;

	public ViewSequenceTypesPanel(String id) 
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
		form = new Form("viewSequenceTypesPanel");
		form.add(createDataTable(new SequenceTypesProvider()));

		updateButton = new Button("updateButton")
		{
			@Override
			public void onSubmit() 
			{
				if(selected.size() > 1)
				{
					error("Please select only one record...");
				}
				else if(selected.size() <=0)
				{
					error("No record was selected...");
				}
				else
				{
					for(WebSequenceTypesModel webSeqModel: selected)
					{
						maintainSequenceTypesPanel = new MaintainSequenceTypesPanel(id, webSeqModel, "update");
						MarkupContainer markupContainer = form.getParent().getParent();	
						markupContainer.remove(form.getParent());
						markupContainer.add(maintainSequenceTypesPanel);
						maintainSequenceTypesPanel.setOutputMarkupId(true);
						maintainSequenceTypesPanel.setOutputMarkupPlaceholderTag(true);
						TemplatePage.setContent(maintainSequenceTypesPanel);
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
				maintainSequenceTypesPanel = new MaintainSequenceTypesPanel(id,"create");
				MarkupContainer markupContainer = form.getParent().getParent();
             	markupContainer.remove(form.getParent());
				markupContainer.add(maintainSequenceTypesPanel);
				maintainSequenceTypesPanel.setOutputMarkupId(true);
				maintainSequenceTypesPanel.setOutputMarkupPlaceholderTag(true);
				TemplatePage.setContent(maintainSequenceTypesPanel);
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
				log.debug("Search Text: "+searchText.getValue());
				if(searchText.getValue() == null || searchText.getValue().isEmpty())
				{
					error("Search Field is not populated.");
				}
				else
				{
					form.remove(form.get("dataTable"));
					form.add(createDataTable(new SequenceTypesProvider(searchText.getValue().toUpperCase())));
				}
			}
		};
		searchText = new TextField<String>("searchText", new Model<String>());
		searchText.add(StringValidator.exactLength(4));
		searchText.add(new TextFieldValidator());
		
		viewAllButton = new Button ("viewAllButton")
		{
			private static final long SerialVersionUID = 1L;
			@Override
			
			public void onSubmit()
			{
				viewAllButton.setEnabled(false);	
				form.remove(form.get("dataTable"));
				form.add(createDataTable(new SequenceTypesProvider()));
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
	
	private DefaultDataTable createDataTable(SequenceTypesProvider sequenceTypesProvider)
	{
		DefaultDataTable dataTable;
		List<IColumn> columns = new ArrayList<IColumn>();
		IColumn column = new CheckBoxColumn<WebSequenceTypesModel>(Model.of("Select")) 
		{
            @Override
            protected IModel newCheckBoxModel(final IModel<WebSequenceTypesModel> rowModel) 
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
                    	if(selected.size() > 0)
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
		columns.add(new PropertyColumn(new Model<String>("Sequence Types Code"),"sequenceCode", "sequenceCode"));
		columns.add(new PropertyColumn(new Model<String>("Sequence Types Description"),"sequenceDescription","sequenceDescription"));
		columns.add(new PropertyColumn(new Model<String>("Active Ind"),"activeInd", "activeInd"));
		dataTable = new DefaultDataTable("dataTable", columns, sequenceTypesProvider, 10);
		return dataTable;
	}

}