package com.bsva.panels.ManualEndofDay;
import java.io.Serializable;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import com.bsva.controller.Controller;

public class EndofDayPanel extends Panel implements Serializable 
{
	/**
	 * @author ElelwaniR
	 */
	private static final long serialVersionUID = 1L;
	private Form form;
	private Button endofDayButton;
	Controller controller;
	public static String id;

	public EndofDayPanel(String id) 
	{
		super(id);
		this.id=id;
		initializeComponents();
	}

	private void initializeComponents ()
	{
		
		controller=new Controller();
		form = new Form ("endofDayform");
		add(form);
		
		endofDayButton =new Button ("endofDayButton")
		{
			private static final long serialVersionUID = 1L;

			public void onSubmit()
			{
				controller.runEndofDayManually(null, null);
				//controller.runArchive();
				info("End  of day has run successfully");
			}
		};
	   	form.add(new FeedbackPanel("feedbackPanel"));
      	form.add(endofDayButton);
	}
}
