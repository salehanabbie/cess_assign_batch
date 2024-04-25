package com.bsva.constant;
import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.http.WebRequest;
import com.bsva.controller.Controller;

public class Constant implements Serializable
{
	
	static Controller controller;
	public static Logger log = Logger.getLogger(Constant.class);

	
	private static final long serialVersionUID = 1L;
	public static String getUrlIAM() {
	
		controller = new Controller();
		String iamPort =controller.getProperty("MDT.IAM_PORT");
		log.info("iamPort:"+iamPort);
		
		WebRequest req = (WebRequest) RequestCycle.get().getRequest();
		HttpServletRequest httpReq = (HttpServletRequest) req.getContainerRequest();
		String clientAddress = httpReq.getServerName();	
		String protocol1 = httpReq.getRequestURL().toString();
		String [] split =  protocol1.split(":");
		String port = String.valueOf(httpReq.getServerPort());
		String protocol = split[0];
		String url = protocol+"://" + clientAddress + ":"+iamPort+"/IAM/";
		return url;
	}
}
