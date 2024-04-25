package com.bsva.auditprocess.auditTrail;
import org.apache.log4j.Logger;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import com.bsva.controller.Controller;
import com.bsva.models.WebAuditTrackingModel;
import com.bsva.panels.CheckBoxColumn;
import com.bsva.provider.AuditProvider;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.extensions.model.AbstractCheckBoxModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.validation.validator.StringValidator;

public class ViewAuditTrackingScreenPanel extends Panel 
{
	Controller controller;
	private static final long serialVersionUID = 1L;
	public static Logger log = Logger.getLogger(ViewAuditTrackingScreenPanel.class);
	public static String id;
	private Form form;
	private Button searchButton;
	private TextField<String> searchText;
	private Set<WebAuditTrackingModel> selected = new HashSet<WebAuditTrackingModel>();

	public ViewAuditTrackingScreenPanel(String id)
	{
		super(id);
		this.id = id;
		initializeComponents();
	}
	
	private void initializeComponents() 
	{
		form = new Form("viewAuditTrackingScreenPanel");
		form.add(createDataTable(new AuditProvider()));
		searchButton = new Button("searchButton") 
		{
			private static final long serialVersionUID = 1L;
			@Override
			public void onSubmit() 
			{
				form.remove(form.get("dataTable"));
				log.debug("Search Text: " + searchText.getValue());
				if(searchText.getValue() == null || searchText.getValue().isEmpty())
				{
					form.add(createDataTable(new AuditProvider()));
				}
				else
				{
					form.add(createDataTable(new AuditProvider(searchText.getValue())));
				}
			}
		};
		searchText = new TextField<String>("searchText", new Model<String>());
		searchText.add(StringValidator.maximumLength(30));
		form.add(searchButton);
		form.add(searchText);
		add(form);
		add(new FeedbackPanel("feedbackPanel"));
	}

	private DefaultDataTable createDataTable(AuditProvider auditProvider) 
	{
		DefaultDataTable dataTable;
		List<IColumn> columns = new ArrayList<IColumn>();
		columns.add(new PropertyColumn(new Model<String>("User Id"), "userId","userId"));
		columns.add(new PropertyColumn(new Model<String>("Table Name"),"tableName","tableName"));
		columns.add(new PropertyColumn(new Model<String>("Column Name"),"columnName", "columnName"));
		columns.add(new PropertyColumn(new Model<String>("Old Value"),"oldValue", "oldValue"));
		columns.add(new PropertyColumn(new Model<String>("New Value"),"newValue", "newValue"));
		columns.add(new PropertyColumn(new Model<String>("Action"),"action", "action"));
		columns.add(new PropertyColumn(new Model<String>("Action Date"),"actionDate","actionDate"));
		dataTable = new DefaultDataTable("dataTable", columns,auditProvider, 5);
		return dataTable;
	}
}