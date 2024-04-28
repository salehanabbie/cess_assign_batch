/**
 * 
 */
package com.bsva;

import org.apache.wicket.util.tester.BaseWicketTester;
import org.junit.Test;

import com.bsva.models.MandateModel;
import com.bsva.panels.MandatePanel;
import com.bsva.panels.localInstrCodes.ViewLocalInstrPanel;

/**
 * @author jeremym
 *
 */
public class TestPanels {

	@Test
	public void testMandatesPanel(){
		
		BaseWicketTester baseWicketTester = new BaseWicketTester(); 
		baseWicketTester.startComponentInPage(new MandatePanel("mandatePanel", new MandateModel()));
		/*baseWicketTester.startComponentInPage(new ViewLocalInstrPanel("viewLocalInstPanel"));*/
		
		
	}
	
}
