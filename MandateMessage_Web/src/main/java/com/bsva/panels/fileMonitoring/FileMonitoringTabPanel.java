package com.bsva.panels.fileMonitoring;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.extensions.markup.html.tabs.TabbedPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

public class FileMonitoringTabPanel extends Panel  implements Serializable
{
	private static final long serialVersionUID = 1L;
	public static String id;

	public FileMonitoringTabPanel(String id) 
	{
		super(id);
		this.id = id;
        initializeComponents();
	}
	
	public void initializeComponents()
	{
		List<ITab> tabs = new ArrayList<ITab>();
		tabs.add(new AbstractTab(new Model("Incoming Transactions"))
		{
			private static final long serialVersionUID = 1L;
			public Panel getPanel(String panelId)
			{
				return new IncomingFileMonitoringPanel(panelId);
			}

		});
		tabs.add(new AbstractTab(new Model("Outgoing Transactions"))
		{
			private static final long serialVersionUID = 1L;
			public Panel getPanel(String panelId)
			{
				return new OutgoingFileMonitoringPanel(panelId);
			}
		});	
		add(new TabbedPanel("tabs", tabs));
	}
}