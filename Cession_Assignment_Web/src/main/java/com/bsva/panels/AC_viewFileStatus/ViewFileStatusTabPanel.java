package com.bsva.panels.AC_viewFileStatus;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.Session;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.extensions.ajax.markup.html.tabs.AjaxTabbedPanel;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.extensions.markup.html.tabs.TabbedPanel;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

import com.bsva.models.ClientSessionModel;
import com.bsva.panels.SystemParameters.ViewActivePanel;
import com.bsva.panels.SystemParameters.ViewInActivePanel;
import com.cooldatasoft.horizontal.dropdown.sunrisegloss.SunriseGlossDropDownMenu;

/**
 * 
 * @author DimakatsoN
 *
 */

public class ViewFileStatusTabPanel extends Panel implements Serializable  {
	
	
	private static final long serialVersionUID = 1L;

	public static String id;
   
    private String action;

      public ViewFileStatusTabPanel(String id)
      {
            super(id);
            this.id = id;
            initializeComponents();
            
      }
      
    private void initializeComponents() 
    
      {
                  

      List<ITab> tabs = new ArrayList<ITab>();
      tabs.add(new AbstractTab(new Model("Incoming Transactions")) {
        
        public Panel getPanel(String panelId)
        {
        	return  new ViewFileStatusPanel(panelId);
        }
        
        });
       
        tabs.add(new AbstractTab(new Model("Outgoing Transactions")) {
      
       public Panel getPanel(String panelId)
        {
        return new OutgoingFileStatusPanel(panelId);
        }
        
        });
        
        add(new TabbedPanel("tabs", tabs));
       
      }

}
