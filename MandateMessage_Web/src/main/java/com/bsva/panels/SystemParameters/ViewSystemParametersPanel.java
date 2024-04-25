package com.bsva.panels.SystemParameters;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.extensions.markup.html.tabs.TabbedPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;


public class ViewSystemParametersPanel extends Panel implements Serializable 
{     
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static String id;

	private String action;

	public ViewSystemParametersPanel(String id)
	{
		super(id);
		this.id = id;
		initializeComponents();

	}
	private void initializeComponents() 
	{


		List<ITab> tabs = new ArrayList<ITab>();
		tabs.add(new AbstractTab(new Model("Active")) {

			public Panel getPanel(String panelId)
			{
				return  new ViewActivePanel(panelId);
			}

		});

		tabs.add(new AbstractTab(new Model("InActive")) {

			public Panel getPanel(String panelId)
			{
				return new ViewInActivePanel(panelId);
			}

		});

		add(new TabbedPanel("tabs", tabs));

	}   


}

