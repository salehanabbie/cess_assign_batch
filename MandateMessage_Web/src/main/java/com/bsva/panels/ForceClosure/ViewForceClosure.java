package com.bsva.panels.ForceClosure;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;

import com.bsva.TemplatePage;
import com.bsva.controller.Controller;
import com.bsva.models.WebSystemParameterModel;
import com.bsva.validators.SimpleAttributeModifier;



public class ViewForceClosure  extends Panel implements Serializable{

	/**
	 * @author ElelwaniR
	 */
	public static Logger log = Logger.getLogger(ViewForceClosure.class);
	private static final long serialVersionUID = 1L;
	public static String id;
	private String action;
	private Form form ;
	private Button forceClosureButton;

	MaintainForceClosure maintainForceClosure;
	ViewForceClosure  viewFileClosurePanel ;
	WebSystemParameterModel websystemParametersModel =new WebSystemParameterModel();

	Controller controller;
	
	public ViewForceClosure(String id)
	{
		super(id);
		this.id=id;
		initializeComponents();
		
	}

	

	private void initializeComponents ()
	{
		controller = new Controller();
		form = new Form("viewForceClosure");
		
		
		
		forceClosureButton = new Button("forceClosureButton")
		{

				/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

				@Override
				public void onSubmit() {
					

					maintainForceClosure = new MaintainForceClosure(id);
					maintainForceClosure.setOutputMarkupId(true);
					maintainForceClosure.setOutputMarkupPlaceholderTag(true);
					MarkupContainer markupContainer = form.getParent().getParent();
	                markupContainer.remove(form.getParent());
					markupContainer.add(maintainForceClosure);
					TemplatePage.setContent(maintainForceClosure);
				
				}
			};
			forceClosureButton.setDefaultFormProcessing(false);
			form.add(forceClosureButton);
			forceClosureButton.add(new SimpleAttributeModifier("onclick", "Are you sure you want to force close  the system? This action cannot be undone if you select YES"));
			
	    add(form);
    	add(new FeedbackPanel("feedbackPanel"));
	}
}
