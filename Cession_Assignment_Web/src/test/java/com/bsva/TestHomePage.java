package com.bsva;

import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;

/**
 * Simple test using the WicketTester
 */
public class TestHomePage
{
	private WicketTester tester;

	@Before
	public void setUp()
	{
		try {
			tester = new WicketTester(new WicketApplication());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void homepageRendersSuccessfully()
	{
		//start and render the test page
		tester.startPage(HomePage.class);

		//assert rendered page class
		tester.assertRenderedPage(HomePage.class);
	}
	
	@Test
	public void testPageTest(){
/*		//start and render the test page
		tester.startPage(TestPage.class);
		//assert rendered page class
		tester.assertRenderedPage(TestPage.class);
		
		FormTester formTester = tester.newFormTester("form", false);
		//submit form using inner component 'button' as alternate button
		formTester.submit("button");*/
	}
	
}
