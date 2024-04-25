package com.bsva;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public abstract class BasePage extends WebPage {
	private static final long serialVersionUID = 1L;
	// Wicket component
/*	private ProgressBar progressBar;*/
	
	public BasePage(final PageParameters parameters) {
		super(parameters);
    }

	public String getAjaxIndicatorMarkupId() {
		// TODO Auto-generated method stub
		return null;
	}

}
