package com.bsva;

import java.io.Serializable;

import org.apache.wicket.markup.html.panel.Panel;

public class FooterPanel extends Panel implements Serializable {

	/**
	 * @author jeremym
	 */
	private static final long serialVersionUID = 1L;

	public FooterPanel(String id) {
		super(id);
		//this.add((new Image("logo", new ContextRelativeResource("images/Logo_H_t_b_A_100.png"))));
	}

}
