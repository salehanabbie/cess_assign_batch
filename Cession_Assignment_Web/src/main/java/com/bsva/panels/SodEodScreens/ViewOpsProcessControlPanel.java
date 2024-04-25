package com.bsva.panels.SodEodScreens;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.extensions.model.AbstractCheckBoxModel;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.bsva.controller.WebOpsProcessControlsModel;
import com.bsva.panels.CheckBoxColumn;
import com.bsva.provider.OpsProcessControlProvider;

public class ViewOpsProcessControlPanel extends Panel implements Serializable {

	/**
	 * @author ElelwaniR
	 */
	private static final long serialVersionUID = 1L;
	public static Logger log = Logger.getLogger(ViewOpsProcessControlPanel.class);
	public static String id;
	private Form form;
	private Set<WebOpsProcessControlsModel> selected = new HashSet<WebOpsProcessControlsModel>();
	
	public ViewOpsProcessControlPanel(String id) {
		super(id);
		initializeComponents ();
	}

	public void initializeComponents ()
	{
		
		form = new Form("form");
		form.add(createDataTable(new OpsProcessControlProvider()));
		add(form);
     }
	
	
	private DefaultDataTable createDataTable(OpsProcessControlProvider opsProcessControlProvider) 
	{
		DefaultDataTable dataTable;

		List<IColumn> columns = new ArrayList<IColumn>();
		IColumn column = new CheckBoxColumn<WebOpsProcessControlsModel>(Model.of("Select"))
				{
			@Override
			protected IModel newCheckBoxModel(final IModel<WebOpsProcessControlsModel> rowModel)
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

		//columns.add(column);
		columns.add(new PropertyColumn(new Model<String>("Process Name"), "processName","processName"));
		columns.add(new PropertyColumn(new Model<String>("Cron Time"),"cronTime","cronTime"));
		columns.add(new PropertyColumn(new Model<String>("Active Ind"),"activeInd", "activeInd"));
	
		
		dataTable = new DefaultDataTable("dataTable", columns,opsProcessControlProvider, 10);

		return dataTable;
		
	}

}
