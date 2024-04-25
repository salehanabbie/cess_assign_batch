package com.bsva.panels.SystemParameters;

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
import com.bsva.models.WebSystemParameterModel;
import com.bsva.panels.CheckBoxColumn;
import com.bsva.provider.SysParmActiveProvider;

public class ViewActivePanel extends Panel implements Serializable {

	
	private static final long serialVersionUID = 1L;
	Controller controller;
	public static Logger log = Logger.getLogger(ViewActivePanel.class);
	public static String id;
	private Form form;
	
	private Set<WebSystemParameterModel> selected = new HashSet<WebSystemParameterModel>();
	
	public ViewActivePanel(String id) 
	{
		super(id);
		this.id = id;
		initializeComponents();
	}
	public void initializeComponents ()
	{
		form = new Form("ViewActivePanel");
		form.add(createDataTable(new SysParmActiveProvider()));		
		
		add(form);	
		add(new FeedbackPanel("feedbackPanel"));
    }
	private DefaultDataTable createDataTable(SysParmActiveProvider sysParmActiveProvider)
	{
		DefaultDataTable dataTable;
		
		List<IColumn> columns = new ArrayList<IColumn>();

//		IColumn column = new CheckBoxColumn<WebSystemParameterModel>(
//				Model.of("Select")) 
//		{
//
//			private static final long serialVersionUID = 1L;
//
//			@Override
//			protected IModel newCheckBoxModel(
//					final IModel<WebSystemParameterModel> rowModel) 
//			{
//				return new AbstractCheckBoxModel() 
//				{
//					private static final long serialVersionUID = 1L;
//
//					@Override
//					public boolean isSelected() 					{
//						return selected.contains(rowModel.getObject());
//					}
//
//					@Override
//					public void select() 
//					{
//						if (selected.size() > 0) 
//						{
//							unselect();
//						}
//						selected.add(rowModel.getObject());
//					}
//
//					@Override
//					public void unselect() 
//					{
//						selected.remove(rowModel.getObject());
//					}
//
//					@Override
//					public void detach()
//					{
//						rowModel.detach();
//					}
//				};
//			}
//		};
//		
//		columns.add(column);
		//columns.add(new PropertyColumn(new Model<String>("Action"),"action", "action"));
		columns.add(new PropertyColumn(new Model<String>("System Name"), "sysName","sysName"));
//		columns.add(new PropertyColumn(new Model<String>("DefCurr"),"defCurr", "defCurr"));
		columns.add(new PropertyColumn(new Model<String>("System Type"),"sysType", "sysType"));
		columns.add(new PropertyColumn(new Model<String>("Process Date"),"strProcessDate", "strProcessDate"));
		columns.add(new PropertyColumn(new Model<String>("Active Ind"), "activeInd","activeInd"));
		columns.add(new PropertyColumn(new Model<String>("SOD Run Ind"),"sodRunInd", "sodRunInd"));
		columns.add(new PropertyColumn(new Model<String>("EOD Run Ind"),"eodRunInd", "eodRunInd"));
		columns.add(new PropertyColumn(new Model<String>("CIS Download Ind"),"cisDwnldInd", "cisDwnldInd"));		
//		columns.add(new PropertyColumn(new Model<String>("Cis Dwnld Date"), "cisDwnldDate","cisDwnldDate"));
//		columns.add(new PropertyColumn(new Model<String>("Process Date"),"processDate", "processDate"));
		columns.add(new PropertyColumn(new Model<String>("CIS Download Date"), "strCisDownloadDate","strCisDownloadDate"));
		
//		columns.add(new PropertyColumn(new Model<String>("Next Monday HolI nd"),"nextMondayHolInd", "nextMondayHolInd"));
//		columns.add(new PropertyColumn(new Model<String>("Easter Days Ind"),"easterDaysInd", "easterDaysInd"));
		columns.add(new PropertyColumn(new Model<String>("Archive Period"),"archivePeriod", "archivePeriod"));
//		columns.add(new PropertyColumn(new Model<String>("InBalance Ind"), "inBalanceInd","inBalanceInd"));
//		columns.add(new PropertyColumn(new Model<String>("Forceclose Reason"),"forcecloseReason", "forcecloseReason"));
//		columns.add(new PropertyColumn(new Model<String>("Easter Days Ind"),"easterDaysInd", "easterDaysInd"));
		columns.add(new PropertyColumn(new Model<String>("Response Period"), "responsePeriod", "responsePeriod")); 
		columns.add(new PropertyColumn(new Model<String>("Inactive Duration"),"inactiveDuration", "inactiveDuration"));
		
		dataTable = new DefaultDataTable("dataTable",columns, sysParmActiveProvider, 10);
		return dataTable;
		
	}
	
	
}
