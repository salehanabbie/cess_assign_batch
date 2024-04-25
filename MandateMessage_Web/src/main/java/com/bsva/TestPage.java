package com.bsva;

import com.bsva.panels.localInstrCodes.ViewLocalInstrPanel;
import java.io.Serializable;
import org.apache.log4j.Logger;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import com.bsva.controller.Controller;

/**
 * Test Page.
 * @author jeremym
 *
 */
public class TestPage extends BasePage implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Logger log = Logger.getLogger(TestPage.class);

	
	
	public TestPage(PageParameters parameters) 
	{
		super(parameters);
		ViewLocalInstrPanel viewPanel = new ViewLocalInstrPanel("viewLocalInstrPanel");
		add(viewPanel);
		Form form = new Form("form") {
	        protected void onSubmit() {
	        }
	    };


	    Button button = new Button("button") {
	        public void onSubmit() {
	        	log.info("Test"); 
	            info("button.onSubmit executed");
			            Controller.startFileListener();
	        }
	    };
	    form.add(button);
		
		add(form);
		
	}

}
