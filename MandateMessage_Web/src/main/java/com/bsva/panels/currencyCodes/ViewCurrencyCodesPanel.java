package com.bsva.panels.currencyCodes;

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
import com.bsva.commons.model.CurrencyCodesModel;
import com.bsva.controller.Controller;
import com.bsva.models.WebCurrencyCodesModel;
import com.bsva.panels.CheckBoxColumn;
import com.bsva.provider.CurrencyCodesProvider;
import com.bsva.validators.TextFieldValidator;

/**
 * 
 * @author DimakatsoN
 *
 */

public class ViewCurrencyCodesPanel extends Panel {
	
	Controller controller;

	public static String id;
	
	private static final long serialVersionUID = 1L;
	
	public static Logger log = Logger.getLogger(ViewCurrencyCodesPanel.class);

	private Form form;
	
	private Set<WebCurrencyCodesModel> selected = new HashSet<WebCurrencyCodesModel>();
	
	private MaintainCurrencyCodesPanel maintainCurrencyCodesPanel;

	private Button updateButton;
	private Button addButton;
	private Button searchButton;
	private Button viewAllButton;
	
	private TextField<String> searchText;

	private List<WebCurrencyCodesModel> webCurrencyCodesModelList;
	private List<CurrencyCodesModel> currencyCodesList;


	public ViewCurrencyCodesPanel(String id) 
	{
		super(id);
		this.id = id;
		initializeComponents();	
	}
	
	  private void initializeComponents() 
	  {
		form = new Form("viewCurencyCodesPanel");

		form.add(createDataTable(new CurrencyCodesProvider()));

		updateButton = new Button("updateButton") 
		{
			/**
			 * 
			 */
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
					for (WebCurrencyCodesModel currencyCodesModel : selected)
					{
						maintainCurrencyCodesPanel = new MaintainCurrencyCodesPanel(id,currencyCodesModel, "update");
						MarkupContainer markupContainer = form.getParent().getParent();
                         markupContainer.remove(form.getParent());
						markupContainer.add(maintainCurrencyCodesPanel);
						TemplatePage.setContent(maintainCurrencyCodesPanel);
						
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

				maintainCurrencyCodesPanel = new MaintainCurrencyCodesPanel(id,"create");
				maintainCurrencyCodesPanel.setOutputMarkupId(true);
				maintainCurrencyCodesPanel.setOutputMarkupPlaceholderTag(true);
				MarkupContainer markupContainer = form.getParent().getParent();
                markupContainer.remove(form.getParent());
				markupContainer.add(maintainCurrencyCodesPanel);
				TemplatePage.setContent(maintainCurrencyCodesPanel);
			
			}
		};
		addButton.setDefaultFormProcessing(false);

		searchButton = new Button("searchButton") 
		{
			/**
			 * 
			 */
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
					form.add(createDataTable(new CurrencyCodesProvider(searchText.getValue().toUpperCase())));
				}
			}
		};

		searchText = new TextField<String>("searchText", new Model<String>());
		searchText.add(StringValidator.exactLength(2));
		searchText.add(new TextFieldValidator());
		
		viewAllButton = new Button ("viewAllButton")
		{
			private static final long SerialVersionUID = 1L;
			@Override
			
			public void onSubmit()
			{
				viewAllButton.setEnabled(false);
				
				form.remove(form.get("dataTable"));
				form.add(createDataTable(new CurrencyCodesProvider()));
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



	private DefaultDataTable createDataTable(CurrencyCodesProvider currencyCodesProvider)
	{
		DefaultDataTable dataTable;
		
		List<IColumn> columns = new ArrayList<IColumn>();
		IColumn column = new CheckBoxColumn<WebCurrencyCodesModel>(Model.of("Select")) 
		{
            @Override
            protected IModel newCheckBoxModel(final IModel<WebCurrencyCodesModel> rowModel) 
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
		columns.add(new PropertyColumn(new Model<String>("Country Code"),"countryCode", "countryCode"));
		columns.add(new PropertyColumn(new Model<String>("Alpha Currency Code"),"alphaCurrCode","alphaCurrCode"));
		columns.add(new PropertyColumn(new Model<String>("Numeric Currency Code"),"numCurrCode","numCurrCode"));
		columns.add(new PropertyColumn(new Model<String>("Country Code Description"),"currCodeDesc","currCodeDesc"));
		columns.add(new PropertyColumn(new Model<String>("Active Ind"),"activeInd", "activeInd"));
		
		dataTable = new DefaultDataTable("dataTable", columns, currencyCodesProvider, 10);
		return dataTable;
	}


}
