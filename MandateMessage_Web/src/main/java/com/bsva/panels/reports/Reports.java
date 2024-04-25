package com.bsva.panels.reports;

import java.text.ParseException;

import org.apache.log4j.Logger;
import org.apache.wicket.ajax.IAjaxIndicatorAware;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;


public class Reports extends Panel implements IAjaxIndicatorAware 
{
	public static String id;
	public static Logger logger = Logger.getLogger(Reports.class);

	private Form form;

	public Reports(String id) {
		super(id);
		this.id = id;
		
		try {
			initializeComponents();
		}catch (ParseException pe){
			pe.printStackTrace();
		}
	}
	
	private void initializeComponents() throws ParseException
	
	{
	
	}


	@Override
	public String getAjaxIndicatorMarkupId() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
