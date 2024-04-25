package com.bsva.panels.FileStatusDescription;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.extensions.model.AbstractCheckBoxModel;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import com.bsva.models.WebFileStatusModel;
import com.bsva.panels.CheckBoxColumn;
import com.bsva.provider.FileStatusProvider;

public class ViewFileStatusDescription  extends Panel implements Serializable
{
	/**
	 * 
	 * @author ElelwaniR
	 */
	private static final long serialVersionUID = 1L;
	private Form form;
	private Set<WebFileStatusModel> selected = new HashSet<WebFileStatusModel>();
	
	public ViewFileStatusDescription(String id) 
	{
		super(id);
		initializeComponents ();
	}
	public void initializeComponents ()
	{
		
		form = new Form("form");
		form.add(createDataTable(new FileStatusProvider()));
		add(form);
     }
	
	private DefaultDataTable createDataTable(FileStatusProvider fileStatusProvider) 
	{
		DefaultDataTable dataTable;
		List<IColumn> columns = new ArrayList<IColumn>();
		IColumn column = new CheckBoxColumn<WebFileStatusModel>(Model.of("Select"))
		{
			@Override
			protected IModel newCheckBoxModel(final IModel<WebFileStatusModel> rowModel)
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
		columns.add(new PropertyColumn(new Model<String>("Status"),"status", "status"));
		columns.add(new PropertyColumn(new Model<String>("Status Description"),"statusDescription", "statusDescription"));
		columns.add(new PropertyColumn(new Model<String>("Active Indicator"),"activeInd", "activeInd"));
		dataTable = new DefaultDataTable("dataTable", columns,fileStatusProvider, 10);
		return dataTable;
	}
}
