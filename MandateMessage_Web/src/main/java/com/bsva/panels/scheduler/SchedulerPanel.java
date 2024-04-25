package com.bsva.panels.scheduler;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.wicket.ajax.AbstractAjaxTimerBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.time.Duration;

import com.bsva.controller.Controller;
import com.bsva.provider.SchedulerDataProvider;

/**
 * 
 * @author DimakatsoN
 * 
 */

public class SchedulerPanel extends Panel implements Serializable {

	private DefaultDataTable dataTable;

	private SchedulerDataProvider schedulerDataProvider;

	private Controller controller;
	
	private List<String> schedulers;
	
	 private Label schedulerLabel;
	
    private Label startLabel;
    
    private Label stopLabel;
    
    private Label rescheduleLabel;
	
	private List<String> durationInSeconds;
	
	private DropDownChoice<String> scheduleList;
	
	private Button startButton;
	
	private Button stopButton;
	
	public static String id;
	
	public Form form;
	
	public SchedulerPanel(String id) {
		super(id);
		this.id = id;
		
		form = new Form("form");
		
		controller = new Controller();
		
		dataTable = createDataTable(new SchedulerDataProvider());
		
		schedulers = Arrays.asList(new String[]{"All","SOD","EOD","PAIN 012 Extract","Mdte.002 Extract","Pain.009 Extract","Pacs.002 Extract"});
		
		durationInSeconds = Arrays.asList(new String[]{"30", "40", "50", "60", "120", "180", "240", "300", "360"});
		
        schedulerLabel = new Label("schedulerLabel");
        scheduleList = new DropDownChoice<String>("scheduleList", schedulers);
        form.add(schedulerLabel);
        form.add(scheduleList);

		
		
		startButton = new Button("startButton") {
		@Override
		public void onSubmit() {
			
				controller.start_Scheduler();

		}
		};
		
		stopButton = new Button("stopButton") {
		@Override
		public void onSubmit() {
			
			controller.stop_Scheduler();
			}
	};

		final AbstractAjaxTimerBehavior timer = new AbstractAjaxTimerBehavior(
				Duration.seconds(2)) {

			/**
	 * 
	 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void onTimer(AjaxRequestTarget target) {
				schedulerDataProvider = new SchedulerDataProvider();
				DefaultDataTable defaultDataTable = createDataTable(new SchedulerDataProvider());
				defaultDataTable.setOutputMarkupId(true);

				dataTable.replaceWith(defaultDataTable);
				dataTable = defaultDataTable;
				target.add(dataTable);

			}
		};
    	form.add(stopButton);
		form.add(startButton);
		form.add(dataTable);
		form.add(timer);
		add(form);
		add(new FeedbackPanel("feedback"));
	}

	private DefaultDataTable createDataTable(
			SchedulerDataProvider schedulerDataProvider)

	{
		DefaultDataTable dafaultTable;

		List<IColumn> columns = new ArrayList<IColumn>();
		
		columns.add(new PropertyColumn(new Model<String>("Status"),
				"status"));
		columns.add(new PropertyColumn(new Model<String>("Scheduler"),
				"scheduler"));
		columns.add(new PropertyColumn(
				new Model<String>("Last Execution Time "), "lastExecutTime"));
		columns.add(new PropertyColumn(
				new Model<String>("Next Execution Time"), "nextExecutTime"));
		columns.add(new PropertyColumn(
				new Model<String>("Cron-time"), "cron"));
		
		dafaultTable = new DefaultDataTable("dataTable", columns,
				schedulerDataProvider, 5);

		return dafaultTable;

	}
}
