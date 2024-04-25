package com.bsva;

import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;

import org.apache.log4j.Logger;

import com.bsva.commons.model.ObsStagingModel;


/**
 * Session Bean implementation class EJBJMSSender
 */
//@Stateless
//@LocalBean
public class EJBJMSSender {

	private Logger log = Logger.getLogger(EJBJMSSender.class);

//	@Inject
//	private JMSContext jcontext;
//
//	@Resource(lookup = "java:jboss/exported/jms/queue/BillingQueue")
//	private Destination d;
//
//	public void send(ObsStagingModel objMessage) {
//		log.info("<-------------------------Sending message to queue---------------------->");
//		JMSProducer jp = jcontext.createProducer();
//
//		jp.send(d, objMessage);
//		
//		log.info("Message Successfully Sent to the Queue"); 
//
//	}
}
