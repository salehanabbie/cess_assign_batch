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
import com.bsva.commons.model.SysCisBranchModel;
import com.bsva.controller.Controller;
import com.bsva.models.WebSysCisBankModel;
import com.bsva.models.WebSysCisBranchModel;
import com.bsva.panels.CheckBoxColumn;
import com.bsva.provider.CisBankProvider;
import com.bsva.provider.CisBranchProvider;
import com.bsva.validators.FieldNumberTextFieldValidator;
import com.bsva.validators.TextFieldValidator;

/**
 * @author SalehaR
 *
 */

public class ViewCisBranchPanel extends Panel implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	Controller controller;
	public static Logger log = Logger.getLogger(ViewCisBranchPanel.class);
	public static String id;
	private Form form;
	private Set<WebSysCisBranchModel> selected = new HashSet<WebSysCisBranchModel>();

	private Button searchButton;
	private Button viewAllButton;
	private TextField<String>  branchNoText, cisDate;

	private List<WebSysCisBranchModel> webCisBranchModelList;
	private List<SysCisBranchModel> sysCisBranchModelList;
	private OpsProcessControlModel processControlModel;
	
	public ViewCisBranchPanel(String id)
	{
		super(id);
		this.id = id;
        initializeComponents();
	}
	
	private void initializeComponents() 
	{
		Controller controller = new Controller();
		
		processControlModel = (OpsProcessControlModel) controller.retrieveCisDownloadDate();
		
		form = new Form("viewCisBranchInfoPanel");
		form.add(createDataTable(new CisBranchProvider()));
		
		searchButton = new Button ("searchButton")
		{
			private static final long SerialVersionUID = 1L;
			@Override
			
			public void onSubmit()
			{
				viewAllButton.setEnabled(true);
				
				log.info("Branch No Text: " + branchNoText.getValue());
				
				if(branchNoText.getValue() == null || branchNoText.getValue().isEmpty())
				{
					error("Branch number is not populated.");
				}
				else
				{
					form.remove(form.get("dataTable"));
					String branchNo = branchNoText.getValue();
					
					form.add(createDataTable(new CisBranchProvider(branchNo)));
				}
			}
		};
		
		branchNoText = new TextField<String>("branchNoText", new Model<String>());
		branchNoText.add(StringValidator.maximumLength(6));
		branchNoText.add(new FieldNumberTextFieldValidator());
		
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
				form.remove(form.get("branchNoText"));
				branchNoText = new TextField<String>("branchNoText", new Model<String>());
				branchNoText.add(StringValidator.maximumLength(6));
				branchNoText.add(new FieldNumberTextFieldValidator());
				form.add(branchNoText);
				
				form.remove(form.get("dataTable"));
				form.add(createDataTable(new CisBranchProvider()));
			}
		};
	
		form.add(searchButton);
		form.add(branchNoText);
		form.add(viewAllButton);
		add(form);
		
		add(new FeedbackPanel("feedbackPanel"));
	}
	
	
	private DefaultDataTable createDataTable (CisBranchProvider cisBranchProvider)
	{
		DefaultDataTable dataTable;
		
		List<IColumn> columns = new ArrayList<IColumn>();
		
//			IColumn column = new CheckBoxColumn<WebSysCisBranchModel>(Model.of("Select")){
//
//			private static final long serialVersionUID = 1L;
//			
//			protected IModel newCheckBoxModel(final IModel<WebSysCisBranchModel> rowModel) {
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
		columns.add(new PropertyColumn(new Model<String>("Branch Number"), "branchNo", "branchNo"));
		columns.add(new PropertyColumn(new Model<String>("Branch Name"), "branchName","branchName"));
		columns.add(new PropertyColumn(new Model<String>("Member Number"),"memberNo", "memberNo"));
		columns.add(new PropertyColumn(new Model<String>("Member Bank"),"division", "division"));
		columns.add(new PropertyColumn(new Model<String>("Creditor Bank Participant"),"acCreditor", "acCreditor"));
		columns.add(new PropertyColumn(new Model<String>("Debtor Bank Participant"),"acDebtor", "acDebtor"));

		
		dataTable = new DefaultDataTable("dataTable", columns,cisBranchProvider, 30);
		return dataTable;
			
	}
	
	  
	  
	

}
