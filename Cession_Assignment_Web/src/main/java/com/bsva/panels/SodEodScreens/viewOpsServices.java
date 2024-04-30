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

import com.bsva.models.WebAcopsPublicModel;
import com.bsva.models.WebOpsServicesModel;
import com.bsva.panels.CheckBoxColumn;
import com.bsva.provider.OpsServicesProvider;


public class viewOpsServices extends Panel implements Serializable {

	
	/**
	 * @author ElelwaniR
	 */
	private static final long serialVersionUID = 1L;
	public static Logger log = Logger.getLogger(viewOpsServices.class);
	public static String id;
	private Form form;
	private Set<WebOpsServicesModel> selected = new HashSet<WebOpsServicesModel>();

	
	public viewOpsServices(String id)
	{
		super(id);
		initializeComponents ();
	}

	public void initializeComponents ()
	{
		
		form = new Form("form");
		form.add(createDataTable(new OpsServicesProvider()));
		add(form);
     }
	
	
	private DefaultDataTable createDataTable(OpsServicesProvider opsServicesProvider) 
	{
		DefaultDataTable dataTable;

		List<IColumn> columns = new ArrayList<IColumn>();
		IColumn column = new CheckBoxColumn<WebOpsServicesModel>(Model.of("Select"))
				{
			@Override
			protected IModel newCheckBoxModel(final IModel<WebOpsServicesModel> rowModel)
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
		
		columns.add(new PropertyColumn(new Model<String>("Service Id In"),"serviceIdIn","serviceIdIn"));
		columns.add(new PropertyColumn(new Model<String>("Service Id In Desc"),"serviceIdInDesc", "serviceIdInDesc"));
		columns.add(new PropertyColumn(new Model<String>("Service Id Out"),"serviceIdOut", "serviceIdOut"));
		columns.add(new PropertyColumn(new Model<String>("Service Id Out Desc"),"serviceIdOutDesc", "serviceIdOutDesc"));
		//columns.add(new PropertyColumn(new Model<String>("Msg ID Type"), "msgTypeId","msgTypeId"));
		columns.add(new PropertyColumn(new Model<String>("Process Date"),"processDate", "processDate"));
		//columns.add(new PropertyColumn(new Model<String>("SLA Times"),"serviceIdOutSlaTime", "serviceIdOutSlaTime"));
	
		
		dataTable = new DefaultDataTable("dataTable", columns,opsServicesProvider, 20);

		return dataTable;
		
	}

}
