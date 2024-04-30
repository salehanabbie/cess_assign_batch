package com.bsva.panels.processStatus;
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
import com.bsva.commons.model.ProcessStatusModel;
import com.bsva.controller.Controller;
import com.bsva.models.ClientSessionModel;
import com.bsva.models.WebProcessStatusModel;
import com.bsva.panels.CheckBoxColumn;
import com.bsva.provider.ProcessStatusProvider;
import com.bsva.validators.TextFieldValidator;

/**
 * @author NhlanhlaM
 * 
 */
public class ViewProcessStatusPanel extends Panel implements Serializable 
{
	private static final long serialVersionUID = 1L;
	Controller controller;
    public static Logger log = Logger.getLogger(ViewProcessStatusPanel.class);
	public static String id;
	private Form form;
	private Set<WebProcessStatusModel> selected = new HashSet<WebProcessStatusModel>();
	private MaintainProcessStatusPanel maintainProcessStatusPanel;
	private Button updateButton;
	private Button addButton;
	private Button searchButton;
	private Button viewAllButton;
	private TextField<String> searchText;
	private List<WebProcessStatusModel> webProcessStatusList;
    private List<ProcessStatusModel> processStatusList;
    private ClientSessionModel clientSessionModel;
    public static Session session;
    private String userRole;

	public ViewProcessStatusPanel(String id) 
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
		form = new Form("viewProcessStatusPanel");
		form.add(createDataTable(new ProcessStatusProvider()));
		
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
                	for (WebProcessStatusModel webProcessStatusModel : selected)
                	{
                		maintainProcessStatusPanel = new MaintainProcessStatusPanel(id, webProcessStatusModel, "update");
                        MarkupContainer markupContainer = form.getParent().getParent();
                        markupContainer.remove(form.getParent());
                        markupContainer.add(maintainProcessStatusPanel);
                        maintainProcessStatusPanel.setOutputMarkupId(true);
                        maintainProcessStatusPanel.setOutputMarkupPlaceholderTag(true);
                        TemplatePage.setContent(maintainProcessStatusPanel);
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
            	    maintainProcessStatusPanel = new MaintainProcessStatusPanel(id, "create");
                    MarkupContainer markupContainer = form.getParent().getParent();
                    markupContainer.remove(form.getParent());
                    markupContainer.add(maintainProcessStatusPanel);
                    maintainProcessStatusPanel.setOutputMarkupId(true);
                    maintainProcessStatusPanel.setOutputMarkupPlaceholderTag(true);
                    TemplatePage.setContent(maintainProcessStatusPanel);
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
                    error("Search Field is not populated");
                 }
                 else
                 {
                     form.remove(form.get("dataTable"));
                     form.add(createDataTable(new ProcessStatusProvider(searchText.getValue().toUpperCase())));
                 }
             }
         };
        searchText = new TextField<String>("searchText", new Model<String>());
        searchText.add(StringValidator.maximumLength(2));
     	searchText.add(new TextFieldValidator());
      
     	viewAllButton = new Button ("viewAllButton")
    	{
    		private static final long SerialVersionUID = 1L;
    		
    		@Override
    		public void onSubmit()
    		{
    			viewAllButton.setEnabled(false);	
    			form.remove(form.get("dataTable"));
    			form.add(createDataTable(new ProcessStatusProvider()));
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

  private DefaultDataTable createDataTable(ProcessStatusProvider processStatusProvider) 
  {
        DefaultDataTable dataTable;
        List<IColumn> columns = new ArrayList<IColumn>();
        IColumn column = new CheckBoxColumn<WebProcessStatusModel>(Model.of("Select"))
        {    
        	private static final long serialVersionUID = 1L;
        	
             @Override
             protected IModel newCheckBoxModel(final IModel<WebProcessStatusModel> rowModel)
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
       columns.add(new PropertyColumn(new Model<String>("Status"), "status","status"));
       columns.add(new PropertyColumn(new Model<String>("Status Description"), "statusDescription","statusDescription"));
       columns.add(new PropertyColumn(new Model<String>("Active Ind"), "activeInd", "activeInd"));
       dataTable = new DefaultDataTable("dataTable", columns, processStatusProvider, 10);
       return dataTable;	
	}
}