package com.bsva.panels.mandateServices;

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
import com.bsva.commons.model.SystemControlServicesModel;
import com.bsva.controller.Controller;
import com.bsva.models.WebSystemControlServicesModel;
import com.bsva.panels.CheckBoxColumn;
import com.bsva.provider.ReportNamesProvider;
import com.bsva.provider.SystemControlServicesProvider;
import com.bsva.validators.TextFieldValidator;

/**
 * 
 * @author NhlanhlaM
 *
 */

public class ViewSystemControlServicesPanel extends Panel
{	
	private static final long serialVersionUID = 1L;

	Controller controller;
	public static Logger log = Logger.getLogger(ViewSystemControlServicesPanel.class);
	public static String id;
	private Form form;
	private Set<WebSystemControlServicesModel> selected = new HashSet<WebSystemControlServicesModel>();
	private MaintainSystemControlServicesPanel maintainSystemControlServicesPanel;
	
	private Button updateButton;
	private Button addButton;
	private Button searchButton;
	private Button viewAllButton;
	private TextField<String> searchText;
	String action;
	
	private List<WebSystemControlServicesModel> webSystemControlServicesModelList;
	private List<SystemControlServicesModel> systemControlServicesModel;
	
	
	public ViewSystemControlServicesPanel(String id) 
	{
		 super(id);
		 this.id=id;
		 initializeComponents();
	}


	private void initializeComponents()
	{
		form = new Form("viewSystemControlServiceForm");
		form.add(createDataTable(new SystemControlServicesProvider()));
		
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
			
				else {
					for (WebSystemControlServicesModel webSystemControlServicesModel : selected) {
						maintainSystemControlServicesPanel = new MaintainSystemControlServicesPanel(id, webSystemControlServicesModel, "update");
						MarkupContainer markupContainer = form.getParent().getParent();
						markupContainer.remove(form.getParent());
						markupContainer.add(maintainSystemControlServicesPanel);
						markupContainer.setOutputMarkupId(true);
						markupContainer.setOutputMarkupPlaceholderTag(true);
						TemplatePage.setContent(maintainSystemControlServicesPanel);
						
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
					maintainSystemControlServicesPanel = new MaintainSystemControlServicesPanel(id, "create");
					maintainSystemControlServicesPanel.setOutputMarkupId(true);
					maintainSystemControlServicesPanel.setOutputMarkupPlaceholderTag(true);
					MarkupContainer markupContainer = form .getParent().getParent();
					markupContainer.remove(form.getParent());
					markupContainer.add(maintainSystemControlServicesPanel);
					TemplatePage.setContent(maintainSystemControlServicesPanel);
				}
				
			};
		
			addButton.setDefaultFormProcessing(false);
			
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
										form.add(createDataTable(new SystemControlServicesProvider(searchText.getValue().trim().toUpperCase())));
									}
									
							}

			    };
		
			    searchText = new TextField<String>("searchText", new Model<String>());
				searchText.add(StringValidator.maximumLength(38));
				searchText.add(new TextFieldValidator());
				
				viewAllButton = new Button ("viewAllButton")
				{
					private static final long SerialVersionUID = 1L;
					@Override
					
					public void onSubmit()
					{
						viewAllButton.setEnabled(false);
						
						form.remove(form.get("dataTable"));
						form.add(createDataTable(new SystemControlServicesProvider()));
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
		
		
private DefaultDataTable createDataTable (SystemControlServicesProvider systemControlServicesProvider)
	{
		DefaultDataTable dataTable;
					
		List<IColumn> columns = new ArrayList<IColumn>();
					
		IColumn column = new CheckBoxColumn<WebSystemControlServicesModel>(Model.of("Select"))
		{

			private static final long serialVersionUID = 1L;
						
						protected IModel newCheckBoxModel(final IModel<WebSystemControlServicesModel> rowModel)
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
					columns.add(new PropertyColumn(new Model<String>("Service Id In"),"serviceIdIn", "serviceIdIn"));
					columns.add(new PropertyColumn(new Model<String>("Service Id In Desc"),"serviceIdInDesc","serviceIdInDesc"));
					columns.add(new PropertyColumn(new Model<String>("Service Id Out"),"serviceIdOut", "serviceIdOut"));
					columns.add(new PropertyColumn(new Model<String>("Service Id Out Desc"),"serviceIdOutDesc","serviceIdOutDesc"));	
					columns.add(new PropertyColumn(new Model<String>("Active Ind"),"activeInd", "activeInd"));

					dataTable = new DefaultDataTable("dataTable", columns,systemControlServicesProvider, 10);

					return dataTable;

	}

}
