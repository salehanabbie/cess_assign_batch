package com.bsva.panels.viewFiles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.extensions.markup.html.tabs.TabbedPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

import com.bsva.panels.AC_viewFileStatus.OutgoingFileStatusPanel;
import com.bsva.panels.AC_viewFileStatus.ViewFileStatusPanel;

/**
 * @author SalehaR
 *
 */
public class ViewFilesTabPanel extends Panel implements Serializable  
{

	public static String id;
   
    private String action;

      public ViewFilesTabPanel(String id)
      {
            super(id);
            this.id = id;
            initializeComponents();
      }
      
      
    private void initializeComponents() 
    {
      List<ITab> tabs = new ArrayList<ITab>();
      
	      tabs.add(new AbstractTab(new Model("Incoming Files")) 
	      {
	        public Panel getPanel(String panelId)
	        {
	        	return  new ViewIncomingFiles(panelId);
	        }
	      });
       
	      tabs.add(new AbstractTab(new Model("Outgoing Files")) 
	      {
		       public Panel getPanel(String panelId)
		       {
		    	   return new ViewOutgoingFiles(panelId);
		       }
		  });
	        
	      add(new TabbedPanel("tabs", tabs));
      }

}
