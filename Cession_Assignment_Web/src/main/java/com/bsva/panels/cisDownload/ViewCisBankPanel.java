package com.bsva.panels.cisDownload;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
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

import com.bsva.commons.model.OpsProcessControlModel;
import com.bsva.commons.model.SysCisBankModel;
import com.bsva.controller.Controller;
import com.bsva.models.WebSysCisBankModel;
import com.bsva.panels.CheckBoxColumn;
import com.bsva.provider.CisBankProvider;
import com.bsva.validators.FieldNumberTextFieldValidator;
import com.bsva.validators.TextFieldValidator;

/**
 * @author SalehaR
 *
 */

public class ViewCisBankPanel extends Panel implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	Controller controller;
	public static Logger log = Logger.getLogger(ViewCisBankPanel.class);
	public static String id;
	private Form form;
	private Set<WebSysCisBankModel> selected = new HashSet<WebSysCisBankModel>();

	private Button searchButton;
	private Button viewAllButton;
	private TextField<String> searchText, cisDate;

	
	private List<WebSysCisBankModel> webCisBankModelList;
	private List<SysCisBankModel> sysCisBankModelList;
	private OpsProcessControlModel processControlModel;
	
	public ViewCisBankPanel(String id)
	{
		super(id);
		this.id = id;
        initializeComponents();
	}
	
	private void initializeComponents() 
	{
		Controller controller = new Controller();
		
		processControlModel = (OpsProcessControlModel) controller.retrieveCisDownloadDate();
		
		form = new Form("viewCisBankInfoPanel");
		form.add(createDataTable(new CisBankProvider()));
		
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
					error("Member number is not populated.");
				}
				else
				{
					form.remove(form.get("dataTable"));
					form.add(createDataTable(new CisBankProvider(searchText.getValue().trim())));
				}
			}
		};
		
		searchText = new TextField<String>("searchText", new Model<String>());
		searchText.add(StringValidator.maximumLength(6));
		searchText.add(new FieldNumberTextFieldValidator());
		
		
		
		
		cisDate = new TextField<String>("cisDate", new Model<String>());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(processControlModel != null && processControlModel.getProcessDate() != null)
		{
			String cisDwnDate = sdf.format(processControlModel.getProcessDate());
			cisDate.setDefaultModelObject(cisDwnDate);
		}
		cisDate.setEnabled(false);
		form.add(cisDate);
		
		viewAllButton = new Button ("viewAllButton")
		{
			private static final long SerialVersionUID = 1L;
			@Override
			
			public void onSubmit()
			{
				form.remove(form.get("searchText"));
				searchText = new TextField<String>("searchText", new Model<String>());
				searchText.add(StringValidator.maximumLength(6));
				searchText.add(new TextFieldValidator());
				form.add(searchText);
				
				form.remove(form.get("dataTable"));
				form.add(createDataTable(new CisBankProvider()));
			}
		};
	
		form.add(searchButton);
		form.add(searchText);
		form.add(viewAllButton);
		add(form);
		
		add(new FeedbackPanel("feedbackPanel"));
	}
	
	
	private DefaultDataTable createDataTable (CisBankProvider cisBankProvider)
	{
		DefaultDataTable dataTable;
		
		List<IColumn> columns = new ArrayList<IColumn>();
		
//			IColumn column = new CheckBoxColumn<WebSysCisBankModel>(Model.of("Select")){
//
//			private static final long serialVersionUID = 1L;
//			
//			protected IModel newCheckBoxModel(final IModel<WebSysCisBankModel> rowModel) {
//				
//				 return new AbstractCheckBoxModel() {
//
//					@Override
//					public boolean isSelected() {
//						
//						return selected.contains(rowModel.getObject());
//					}
//					@Override
//					public void select() 
//					{
//						if (selected.size() > 0) 
//						{
//                            unselect();
//                      }
//                      selected.add(rowModel.getObject());
//                
//						
//					}
//
//					@Override
//					public void unselect() 
//					{
//						 selected.remove(rowModel.getObject());
//						
//					}
//					@Override
//                    public void detach()
//					{
//                          rowModel.detach();
//                    }
//				 };
//			}
//			
//		};
//			
//
//		columns.add(column);
		columns.add(new PropertyColumn(new Model<String>("Member Number"), "memberNo", "memberNo"));
		columns.add(new PropertyColumn(new Model<String>("Member Name"), "memberName","memberName"));
		columns.add(new PropertyColumn(new Model<String>("File Size Limit"),"maxNrRecords", "maxNrRecords"));
		columns.add(new PropertyColumn(new Model<String>("6/7 Day Processor"),"nrOfDaysProc", "nrOfDaysProc"));
		columns.add(new PropertyColumn(new Model<String>("Creditor Bank Participant"),"acCreditor", "acCreditor"));
		columns.add(new PropertyColumn(new Model<String>("Debtor Bank Participant"),"acDebtor", "acDebtor"));

		dataTable = new DefaultDataTable("dataTable", columns,cisBankProvider, 15);
		return dataTable;
			
	}
	
	  
	  
	

}
