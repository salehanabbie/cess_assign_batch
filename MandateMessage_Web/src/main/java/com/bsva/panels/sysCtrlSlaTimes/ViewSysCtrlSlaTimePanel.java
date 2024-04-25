package com.bsva.panels.sysCtrlSlaTimes;

import java.io.Serializable;
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
import org.apache.wicket.validation.validator.PatternValidator;
import org.apache.wicket.validation.validator.StringValidator;

import com.bsva.TemplatePage;
import com.bsva.models.WebSysCtrlSlaTimeModel;
import com.bsva.panels.CheckBoxColumn;
import com.bsva.panels.errorCodes.MaintainConfgErrorCodesPanel;
import com.bsva.provider.SysCtrlSlaTimeProvider;
import com.bsva.validators.FieldNumberTextFieldValidator;
import com.bsva.validators.TextFieldValidator;

public class ViewSysCtrlSlaTimePanel  extends Panel implements Serializable 
{	
	private static final long serialVersionUID = 1L;
	public static Logger log = Logger.getLogger(ViewSysCtrlSlaTimePanel.class);
	public static String id;
	private Form form;
	private Set<WebSysCtrlSlaTimeModel> selected = new HashSet<WebSysCtrlSlaTimeModel>();
	MaintainSysCtrlSlaTimePanel maintainSysCtrlSlaTimePanel;
	private Button updateButton;
	private Button searchButton;
	private Button viewAllButton;
	private Button addButton;
	private TextField<String> searchText;
	String action;
	String textValidator = "([a-zA-Z0-9 \\-._()]+)";
	

	public ViewSysCtrlSlaTimePanel(String id) {
		super(id);
		this.id = id;
		initializeComponents ();
	}

	public void initializeComponents ()
	{
		
		form = new Form("viewSysCtrlSlaTimePanel");
		form.add(createDataTable(new SysCtrlSlaTimeProvider()));

		updateButton = new Button("updateButton")
		{
			@Override
			public void onSubmit() 
			{
				if(selected.size() > 1)
				{
					error("Please select only one record...");
				}
				else
					if(selected.size() <=0)
					{
						error("No record was selected...");
					}
					else
					{
						for(WebSysCtrlSlaTimeModel webSysCtrlSlaTimeModel: selected)
						{
							log.debug("in the view ==> webSysCtrlSlaTimeModel ==> "+ webSysCtrlSlaTimeModel);
							maintainSysCtrlSlaTimePanel = new MaintainSysCtrlSlaTimePanel(id, webSysCtrlSlaTimeModel, "update");
							MarkupContainer markupContainer = form.getParent().getParent();
							markupContainer.remove(form.getParent());
							markupContainer.add(maintainSysCtrlSlaTimePanel);
							maintainSysCtrlSlaTimePanel.setOutputMarkupId(true);
							maintainSysCtrlSlaTimePanel.setOutputMarkupPlaceholderTag(true);
							TemplatePage.setContent(maintainSysCtrlSlaTimePanel);
							break;
						}
					}
			}
		};
		updateButton.setDefaultFormProcessing(false);
		form.add(updateButton);
		
		addButton = new Button("addButton") 
		{
			@Override
			public void onSubmit() 
			{
				maintainSysCtrlSlaTimePanel = new MaintainSysCtrlSlaTimePanel(id, "create");
				MarkupContainer markupContainer = form.getParent().getParent();
				markupContainer.remove(form.getParent());
				markupContainer.add(maintainSysCtrlSlaTimePanel);
				maintainSysCtrlSlaTimePanel.setOutputMarkupId(true);
				maintainSysCtrlSlaTimePanel.setOutputMarkupPlaceholderTag(true);
				TemplatePage.setContent(maintainSysCtrlSlaTimePanel);
			}
		};
		addButton.setDefaultFormProcessing(false);
		form.add(addButton);
		
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
					form.add(createDataTable(new SysCtrlSlaTimeProvider(searchText.getValue().trim().toUpperCase())));
				}
			}
		};
		form.add(searchButton);
		
		searchText = new TextField<String>("searchText", new Model<String>());
		searchText.add(StringValidator.maximumLength(5));
		searchText.add(new PatternValidator(textValidator));
		form.add(searchText);
		
		viewAllButton = new Button ("viewAllButton")
		{
			private static final long SerialVersionUID = 1L;
			@Override
			
			public void onSubmit()
			{
				viewAllButton.setEnabled(false);
				
				form.remove(form.get("dataTable"));
				form.add(createDataTable(new SysCtrlSlaTimeProvider()));
			}
		};
		form.add(viewAllButton);
		viewAllButton.setEnabled(false); 
		 

		 add(form);
		 add(new FeedbackPanel("feedbackPanel"));
		
	}
	


	private DefaultDataTable createDataTable(SysCtrlSlaTimeProvider sysCtrlSlaTimeProvider) 
	{
		DefaultDataTable dataTable;
		
		List<IColumn> columns = new ArrayList<IColumn>();
		IColumn column = new CheckBoxColumn<WebSysCtrlSlaTimeModel>(Model.of("Select")) 
		{
            @Override
            protected IModel newCheckBoxModel(final IModel<WebSysCtrlSlaTimeModel> rowModel) 
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
		columns.add(new PropertyColumn(new Model<String>("Service"), "service","service"));
		columns.add(new PropertyColumn(new Model<String>("Start Time"),"startTime","startTime"));
		columns.add(new PropertyColumn(new Model<String>("End Time"),"endTime", "endTime"));
	
		
		dataTable = new DefaultDataTable("dataTable", columns,sysCtrlSlaTimeProvider, 10);

		return dataTable;
		
	}




}
