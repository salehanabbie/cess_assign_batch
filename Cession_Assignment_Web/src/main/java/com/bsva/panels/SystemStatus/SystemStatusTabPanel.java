package com.bsva.panels.SystemStatus;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.extensions.markup.html.tabs.TabbedPanel;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

public class SystemStatusTabPanel extends Panel implements Serializable
{
	private static final long serialVersionUID = 1L;
	public static String id;
	
	public SystemStatusTabPanel(String id)
	{
		super(id);
		this.id = id;
		initializeComponents();
	}
	
	public void initializeComponents()
	{
		List<ITab> tabs = new ArrayList<ITab>();
		tabs.add(new AbstractTab(new Model("System Status")) 
		{
			private static final long serialVersionUID = 1L;
			public Panel getPanel(String panelId)
			{
				return new LandingPanelSystemStatus(panelId);
			}
		});
		tabs.add(new AbstractTab(new Model("Files Not Processed"))
		{
			private static final long serialVersionUID = 1L;
			public Panel getPanel(String panelId)
			{
				return new ViewFilesNotProcessed(panelId);
			}
		});
		tabs.add(new AbstractTab(new Model("Incoming"))
		{
			private static final long serialVersionUID = 1L;
			public Panel getPanel(String panelId)
			{
				return new IncomingSystemStatusPanel(panelId);
			}
		});
		tabs.add(new AbstractTab(new Model("Outgoing"))
		{
			private static final long serialVersionUID = 1L;
			public Panel getPanel(String panelId)
			{
				 return new OutgoingSystemStatusPanel(panelId);
			}
		});
		add(new TabbedPanel("tabs", tabs));
	}
}
