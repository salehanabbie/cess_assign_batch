
package com.bsva;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;

/**
 * Application object for your web application.
 * If you want to run this application without deploying, run the Start class.
 * 
 * @see com.bsva.Start#main(String[])
 */
public class WicketApplication extends WebApplication
{
	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	@Override
	public Class<? extends WebPage> getHomePage()
	{
		return LandingPage.class;

	}

	/**
	 * @see org.apache.wicket.Application#init()
	 */
	@Override
	public void init()
	{
		
		
		super.init();
		getDebugSettings().setAjaxDebugModeEnabled(false); 
		
		mountPage("/testpage/", TestPage.class);
		mountPage("/mandates/", LandingPage.class);
	}
}
