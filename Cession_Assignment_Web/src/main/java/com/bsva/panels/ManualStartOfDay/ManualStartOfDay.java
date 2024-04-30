package com.bsva.panels.ManualStartOfDay;

import java.io.Serializable;





import org.apache.log4j.Logger;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;


import com.bsva.controller.Controller;
import com.bsva.models.WebLocalInstrumentCodesModel;
import com.bsva.validators.SimpleAttributeModifier;

public class ManualStartOfDay extends Panel implements Serializable {

	/**
	 * @author ElelwaniR
	 */
	private static final long serialVersionUID = 1L;
	
	public static Logger log = Logger.getLogger(ManualStartOfDay.class) ;
	private Form form;
	private Button startofDayButton;
	private Button endofDayButton;
	Controller controller;
	  public static String id;

	public ManualStartOfDay(String id) {
		super(id);
		this.id=id;
      initializaComponents();
	}

	
	public void initializaComponents (){
		controller=new Controller();
		form=new Form ("form");
		add(form);
		startofDayButton =new Button ("startofDayButton"){

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			
			public void onSubmit(){
			
				try{
				//controller.compareCISwithCustPram();
				add(new SimpleAttributeModifier("onclick", "Are MEMBER NO DO NOT MATCH, Are you want to continue to run start of day ?")); 
				controller.runStartofDayManually();
				info("Start of day has run successfully");
				
			}catch (Exception exception) 
			{
				error("Add/Update failed, " + exception.getMessage());
				log.error("Add/Update failed, ", exception);
			}
				}    	
				};
	       	startofDayButton.add(new SimpleAttributeModifier("onclick", "Are you sure you want run start of day?"));  	
	       	form.add(startofDayButton);
	   
	      	form.add(new FeedbackPanel("feedbackPanel"));
	
		
	
		}
	
}
