package com.bsva.panels.authType;

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
import org.apache.wicket.validation.validator.StringValidator;
import com.bsva.TemplatePage;
import com.bsva.models.WebAuthTypeModel;
import com.bsva.panels.CheckBoxColumn;
import com.bsva.provider.AuthtypeProvider;


public class ViewAuthTypePanel extends Panel  implements Serializable {

	/**
	 * @author ElelwaniR
	 */
	private static final long serialVersionUID = 1L;
	public static String id;
	private Form form;
	private TextField <String> searchText;
	private Button searchButton ;
	private Button updateButton;
	private Button addButton;
	public static Logger log = Logger.getLogger(ViewAuthTypePanel.class);

	private Set<WebAuthTypeModel> selected = new HashSet<WebAuthTypeModel>();
	MaintainAuthTypePanel maintainAuthTypePanel;
	
	
	public ViewAuthTypePanel(String id) {
		super(id);
		this.id=id;
      initializeComponents();
		
		
	}
	
	private void initializeComponents ()
	{
		
		form = new Form("form");
		form.add(createDataTable(new AuthtypeProvider()));
		add(form);
		
		updateButton = new Button("updateButton") {

			@Override
			public void onSubmit() {
				if (selected.size() > 1) {
					error("Please select only one record...");
				} else if (selected.size() <= 0) {
					error("No record was selected...");
				}

				else {
					for (WebAuthTypeModel webAuthTypeModel : selected) {
						maintainAuthTypePanel = new MaintainAuthTypePanel(	id, webAuthTypeModel, "update");
						MarkupContainer markupContainer = form.getParent().getParent();
						markupContainer.remove(form.getParent());
						markupContainer.add(maintainAuthTypePanel);
						maintainAuthTypePanel.setOutputMarkupId(true);
						maintainAuthTypePanel.setOutputMarkupPlaceholderTag(true);
						TemplatePage.setContent(maintainAuthTypePanel);
						break;
					}
				}
			}
		};

		updateButton.setDefaultFormProcessing(false);
		
		
		addButton = new Button("addButton") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit() {

				maintainAuthTypePanel = new MaintainAuthTypePanel(	id, "create");
				MarkupContainer markupContainer = form.getParent().getParent();
				markupContainer.remove(form.getParent());
				markupContainer.add(maintainAuthTypePanel);
				maintainAuthTypePanel.setOutputMarkupId(true);
				maintainAuthTypePanel.setOutputMarkupPlaceholderTag(true);
				TemplatePage.setContent(maintainAuthTypePanel);

			}
		};

		addButton.setDefaultFormProcessing(false);
 
		

		searchButton = new Button("searchButton") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit() {
				form.remove(form.get("dataTable"));
				log.debug("Search Text: "+searchText.getValue());
				form.add(createDataTable(new AuthtypeProvider(searchText.getValue().toUpperCase())));

			
			}
		};
		searchText = new TextField<String>("searchText", new Model<String>());
		searchText.add(StringValidator.exactLength(4));
		
	form.add(updateButton);
	form.add(addButton);
	form.add(searchButton);
	form.add(searchText);
	add(new FeedbackPanel("feedbackPanel"));
}	

private DefaultDataTable createDataTable(AuthtypeProvider authtypeProvider) 
{
	DefaultDataTable dataTable;

	List<IColumn> columns = new ArrayList<IColumn>();
	IColumn column = new CheckBoxColumn<WebAuthTypeModel>(Model.of("Select"))
			{
		@Override
		protected IModel newCheckBoxModel(final IModel<WebAuthTypeModel> rowModel)
		{
			return new AbstractCheckBoxModel() {

				@Override
				public boolean isSelected() {
					return selected.contains(rowModel.getObject());
				}

				@Override
				public void select() {
					if (selected.size() > 0) {
						unselect();
					}
					selected.add(rowModel.getObject());
				}

				@Override
				public void unselect() {
					selected.remove(rowModel.getObject());
				}

				@Override
				public void detach() {
					rowModel.detach();
				}
			};
		}
	};

	columns.add(column);
	columns.add(new PropertyColumn(new Model<String>("Auth Type"), "authType","authType"));
	columns.add(new PropertyColumn(new Model<String>("Auth Type Description"),"authTypeDescription"));
	columns.add(new PropertyColumn(new Model<String>("Active Ind"),	"activeInd", "activeInd"));

	dataTable = new DefaultDataTable("dataTable", columns,authtypeProvider, 10);

	return dataTable;
	
}

}
