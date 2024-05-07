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
import com.bsva.models.WebOpsCustomerParameters;
import com.bsva.models.WebOpsServicesModel;
import com.bsva.panels.CheckBoxColumn;
import com.bsva.provider.OpsCustomerParametersProvider;
import com.bsva.provider.OpsServicesProvider;

public class ViewOpsCustomerParameters extends Panel implements Serializable{

	/**
	 * @author ElelwaniR
	 */
	private static final long serialVersionUID = 1L;
	public static Logger log = Logger.getLogger(viewOpsServices.class);
	public static String id;
	private Form form;
	private Set<WebOpsCustomerParameters> selected = new HashSet<WebOpsCustomerParameters>();
	
	
	public ViewOpsCustomerParameters(String id) {
		super(id);
		initializeComponents ();
	}
	public void initializeComponents ()
	{
		
		form = new Form("form");
		form.add(createDataTable(new OpsCustomerParametersProvider()));
		add(form);
     }
	private DefaultDataTable createDataTable(OpsCustomerParametersProvider opsCustomerParametersProvider) 
	{
		DefaultDataTable dataTable;

		List<IColumn> columns = new ArrayList<IColumn>();
		IColumn column = new CheckBoxColumn<WebOpsCustomerParameters>(Model.of("Select"))
				{
			@Override
			protected IModel newCheckBoxModel(final IModel<WebOpsCustomerParameters> rowModel)
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
		columns.add(new PropertyColumn(new Model<String>("Inst ID"), "instId","instId"));
		columns.add(new PropertyColumn(new Model<String>("CASA Amendment XSD NS"),"casaAmdXsdNs", "casaAmdXsdNs"));
		columns.add(new PropertyColumn(new Model<String>("CASA Amendment Last Seq"),"casaAmdLstSeq", "casaAmdLstSeq"));
		columns.add(new PropertyColumn(new Model<String>("CASA Amendment Last File Nr"),"casaAmdLastFileNr", "casaAmdLastFileNr"));
		columns.add(new PropertyColumn(new Model<String>("CASA Acceptance XSD NS"),"casaAccpXsdNs", "casaAccpXsdNs"));
		columns.add(new PropertyColumn(new Model<String>("CASA Acceptance Last Seq"),"casaAccpLstSeq", "casaAccpLstSeq"));
		columns.add(new PropertyColumn(new Model<String>("CASA Acceptance Last File Nr"),"casaAccpLastFileNr", "casaAccpLastFileNr"));
		columns.add(new PropertyColumn(new Model<String>("Active Ind"),"activeInd", "activeInd"));
		columns.add(new PropertyColumn(new Model<String>("CASA Status Report XSD NS"),"casaStatusRepXsdNs", "casaStatusRepXsdNs"));
		columns.add(new PropertyColumn(new Model<String>("CASA Status Report Last Seq"),"casaStatusRepLstSeq", "casaStatusRepLstSeq"));
		columns.add(new PropertyColumn(new Model<String>("CASA Status Report Last File Nr"),"casaStatusRepLastFileNr", "casaStatusRepLastFileNr"));
		columns.add(new PropertyColumn(new Model<String>("CASA Confirm XSD NS"),"casaConfirmXsdNs", "casaConfirmXsdNs"));
		columns.add(new PropertyColumn(new Model<String>("CASA Confirm Last Seq"),"casaConfirmLstSeq", "casaConfirmLstSeq"));
		columns.add(new PropertyColumn(new Model<String>("CASA Confirm Last File Nr"),"casaConfirmLstFileNr", "casaConfirmLstFileNr"));
		
		dataTable = new DefaultDataTable("dataTable", columns,opsCustomerParametersProvider, 10);

		return dataTable;
		
	}

}
