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
		columns.add(new PropertyColumn(new Model<String>("Mandate Initiation XSD NS"),"manInitXsdNs","manInitXsdNs"));
		columns.add(new PropertyColumn(new Model<String>("Mandate Initiation Last Seq "),"manInitLstSeq", "manInitLstSeq"));
		columns.add(new PropertyColumn(new Model<String>("Mandate Initiation Last File Nr"),"manInitLastFileNr", "manInitLastFileNr"));
		columns.add(new PropertyColumn(new Model<String>("Mandate Amendment XSD NS"),"manAmdXsdNs", "manAmdXsdNs"));
		columns.add(new PropertyColumn(new Model<String>("Mandate Amendment Last Seq"),"manAmdLstSeq", "manAmdLstSeq"));
		columns.add(new PropertyColumn(new Model<String>("Mandate Amendment Last File Nr"),"manAmdLastFileNr", "manAmdLastFileNr"));
		columns.add(new PropertyColumn(new Model<String>("Mandate Cancellation XSD NS"),"manCanXsdNs", "manCanXsdNs"));
		columns.add(new PropertyColumn(new Model<String>("Mandate Cancellation Last Seq"),"manCanLstSeq", "manCanLstSeq"));
		columns.add(new PropertyColumn(new Model<String>("Mandate Cancellation Last File Nr"),"manCanLastFileNr", "manCanLastFileNr"));
		columns.add(new PropertyColumn(new Model<String>("Mandate Acceptance XSD NS"),"manAccpXsdNs", "manAccpXsdNs"));
		columns.add(new PropertyColumn(new Model<String>("Mandate Acceptance Last Seq"),"manAccpLstSeq", "manAccpLstSeq"));
		columns.add(new PropertyColumn(new Model<String>("Mandate Acceptance Last File Nr"),"manAccpLastFileNr", "manAccpLastFileNr"));
		columns.add(new PropertyColumn(new Model<String>("Active Ind"),"activeInd", "activeInd"));
		columns.add(new PropertyColumn(new Model<String>("Mandate Req Id Re-use Ind"),"mdtReqIdReuseInd", "mdtReqIdReuseInd"));
		columns.add(new PropertyColumn(new Model<String>("Mandate Status Report XSD NS"),"manStatusRepXsdNs", "manStatusRepXsdNs"));
		columns.add(new PropertyColumn(new Model<String>("Mandate Status Report Last Seq"),"manStatusRepLstSeq", "manStatusRepLstSeq"));
		columns.add(new PropertyColumn(new Model<String>("Mandate Status Report Last File Nr"),"manStatusRepLastFileNr", "manStatusRepLastFileNr"));
		columns.add( new PropertyColumn(new Model<String>("Mandate Request XSD NS"),"manReqXsdNs","manReqXsdNs"));
		columns.add( new PropertyColumn(new Model<String>("Mandate Request Last Seq"),"manReqLstSeq","manReqLstSeq"));
		columns.add( new PropertyColumn(new Model<String>("Mandate Request Last File Nr"),"manReqLastFileNr","manReqLastFileNr"));
		columns.add( new PropertyColumn(new Model<String>("Mandate Response XSD NS"),"manRespXsdNs","manRespXsdNs"));
		columns.add( new PropertyColumn(new Model<String>("Mandate Response Last Seq"),"manRespLstSeq","manRespLstSeq"));
		columns.add( new PropertyColumn(new Model<String>("Mandate Response Last File Nr"),"manRespLastFileNr","manRespLastFileNr"));
		columns.add(new PropertyColumn(new Model<String>("Mandate Confirm XSD NS"),"manConfirmXsdNs", "manConfirmXsdNs"));
		columns.add(new PropertyColumn(new Model<String>("Mandate Confirm Last Seq"),"manConfirmLstSeq", "manConfirmLstSeq"));
		columns.add(new PropertyColumn(new Model<String>("Mandate Confirm Last File Nr"),"manConfirmLstFileNr", "manConfirmLstFileNr"));
		
		dataTable = new DefaultDataTable("dataTable", columns,opsCustomerParametersProvider, 10);

		return dataTable;
		
	}

}
