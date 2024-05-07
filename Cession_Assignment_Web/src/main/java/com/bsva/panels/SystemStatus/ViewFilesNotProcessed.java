package com.bsva.panels.SystemStatus;
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
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import com.bsva.controller.Controller;
import com.bsva.models.ViewDeleteInputModel;
import com.bsva.models.WebMandateCountModel;
import com.bsva.panels.CheckBoxColumn;
import com.bsva.provider.ViewFilesNotProcessedProvider;

public class ViewFilesNotProcessed  extends Panel implements Serializable
{
	private static final long serialVersionUID = 1L;
	Controller controller;
	public static Logger log = Logger.getLogger(ViewFilesNotProcessed.class);
	public static String id;
	private Form form;
	private List<IColumn> columns;
	String directoryName = "/home/opsjava/Delivery/Cession_Assign/Input/";
	private String fileName;
	String name;
	String action;
	private Set<ViewDeleteInputModel> selected = new HashSet<ViewDeleteInputModel>();
	List<ViewDeleteInputModel> viewDeleteInputModelList;
	
	public ViewFilesNotProcessed(String id)
	{
		super(id);
		this.id = id;
        initializeComponents();
	}
	
	private void initializeComponents()
	{
		form = new Form("viewFilesNotProcessedPanel");
		form.add(createDataTable(new ViewFilesNotProcessedProvider()));
		columns = new ArrayList<IColumn>();
		
		add(form);
		add(new FeedbackPanel("feedbackPanel"));
	}
	
	private DefaultDataTable createDataTable(ViewFilesNotProcessedProvider viewFilesNotProcessedProvider)
	{
		DefaultDataTable dataTable;
		List<IColumn> columns = new ArrayList<IColumn>();
		IColumn column = new CheckBoxColumn<ViewDeleteInputModel>(Model.of("Select")) 
		{
			private static final long serialVersionUID = 1L;
			@Override
			protected IModel newCheckBoxModel(final IModel<ViewDeleteInputModel> rowModel)
			{
				return new AbstractCheckBoxModel()
				{
					private static final long serialVersionUID = 1L;

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
		columns.add(new PropertyColumn(new Model<String>("File Name"),"fileName", "fileName"));
		columns.add(new PropertyColumn(new Model<String>("Date & Time"), "modifiedDate", "modifiedDate"));
		columns.add(new PropertyColumn<>(new Model<String>("File Type"), "fileType", "fileType"));
		dataTable = new DefaultDataTable("dataTable", columns,	viewFilesNotProcessedProvider, 10);
		return dataTable;
	}
	
}
