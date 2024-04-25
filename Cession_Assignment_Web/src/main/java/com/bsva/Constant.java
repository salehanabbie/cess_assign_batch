package com.bsva;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.http.WebRequest;

public class Constant implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
    public static String getUrlIAM()
    {
           WebRequest req = (WebRequest) RequestCycle.get().getRequest();
           HttpServletRequest httpReq = (HttpServletRequest) req
                        .getContainerRequest();
           String clientAddress = httpReq.getServerName();

                        
           String protocol1 = httpReq.getRequestURL().toString();
           
           String [] split =  protocol1.split(":");
           String port = String.valueOf(httpReq.getServerPort());
           String protocol = split[0];
           
           String url = protocol+"://" + clientAddress + ":"+port+"/IAM/";
           
           return url;
           
    }

}
