package com.bsva.utils;

/**
 * @author SalehaR
 *
 */
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;

import org.apache.log4j.Logger;

public class UnmarshallEventHandler implements ValidationEventHandler
{
	public String error;
	public static Logger log = Logger.getLogger(UnmarshallEventHandler.class);
	

	    public boolean handleEvent(ValidationEvent event) 
	    {
	    	
	    	log.debug("\nEVENT");
	    	log.debug("SEVERITY:  " + event.getSeverity());
	    	log.debug("MESSAGE:  " + event.getMessage());
	        log.debug("LINKED EXCEPTION:  " + event.getLinkedException());
	        log.debug("LOCATOR");
	        log.debug(" LINE NUMBER:  " + event.getLocator().getLineNumber());
	        log.debug(" COLUMN NUMBER:  " + event.getLocator().getColumnNumber());
	        log.debug(" OFFSET:  " + event.getLocator().getOffset());
	        log.debug(" OBJECT:  " + event.getLocator().getObject());
	        log.debug(" NODE:  " + event.getLocator().getNode());
	        log.debug(" URL:  " + event.getLocator().getURL());
	        
	        error = "Line: "+event.getLocator().getLineNumber() + ", Col: "+event.getLocator().getColumnNumber() + ", "+ event.getMessage();
	        
	        PainUnmarshaller painUn = new PainUnmarshaller();
	        painUn.pubErrorCode = error;
	        painUn.setError(error);
	        
	        return true;
	    }
	    
	    public String getError()
	    {
	    	return error;
	    }

}
	
	

