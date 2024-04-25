package com.bsva;

import java.io.Serializable;

import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.resource.ContextRelativeResource;

public class HeaderPanel extends Panel implements Serializable {

	/**
	 * @author jeremym
	 */
	private static final long serialVersionUID = 1L;

	public HeaderPanel(String id) {
		super(id);
		this.add((new Image("logo", new ContextRelativeResource("images/Logo_H_t_b_A_100.png"))));
//		this.add((new Image("logo", new ContextRelativeResource("images/EFTLogoHeader.png"))));
	}

}
