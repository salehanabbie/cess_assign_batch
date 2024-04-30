
package com.bsva;

import java.io.Serializable;

import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.resource.ContextRelativeResource;


public class WelcomePanel extends Panel implements Serializable {

	public static final String CONTENT_ID = "content";
	
	 public WelcomePanel(String id) {
		super(id);
		
//    	this.add((new Image("Welcome", new ContextRelativeResource("images/Welcome2.png"))));
		this.add((new Image("Welcome", new ContextRelativeResource("images/DC_Mndt_Bck.png"))));
    	
    	
	
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
