package com.bsva.panels.SodEodScreens;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.extensions.markup.html.tabs.TabbedPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

public class ViewOpsAcSotEotPanel extends Panel implements Serializable
{
	private static final long serialVersionUID = 1L;
	public static String id;
	
	public ViewOpsAcSotEotPanel(String id)
	{
		super(id);
		this.id = id;
		initializeComponents();
	}
	
	public void initializeComponents()
	{
		List<ITab> tabs = new ArrayList<ITab>();
		tabs.add(new AbstractTab(new Model("Incoming"))
		{
			private static final long serialVersionUID = 1L;
			public Panel getPanel(String panelId)
			{
				return new IncomingSotEotPanel(panelId);
			}
		});
		
		tabs.add(new AbstractTab(new Model("Outgoing"))
		{
			private static final long serialVersionUID = 1L;
			public Panel getPanel(String panelId)
			{
				 return new OutgoingSotEotPanel(panelId);
			}
		});
		add(new TabbedPanel("tabs", tabs));
	}
	
}