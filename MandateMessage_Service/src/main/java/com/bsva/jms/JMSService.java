/**
 * 
 */
package com.bsva.jms;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSession;
import javax.naming.InitialContext;

import org.apache.log4j.Logger;

import com.bsva.beans.GenericDAO;
/*import javax.enterprise.context.RequestScoped;*/


/**
 * @author jeremym
 * 
 */
/*@RequestScoped*/
public class JMSService {
	
	@Resource(mappedName = "java:jboss/exported/jms/queue/test")
	private static Queue testQueue;
	public static Logger log = Logger.getLogger(JMSService.class);

	/*
	 * The prefered way for context inject. This is a bug on wildfly WFLY-469.
	 * Logged on jira ... will probably fixed on the next wildfly release
	 * 
	 * @Inject JMSContext context;
	 */

	@Resource(name = "java:/JmsXA", type = ConnectionFactory.class)
	static
	ConnectionFactory cf;

	@EJB
	static GenericDAO genericDAO; 

	public static void sendMessage(String txt) {

		try {
			  QueueConnection conn;; 
			  QueueSession session;
			    Queue que;
			
			try {
				log.debug("genericDAO : " + genericDAO);
				 InitialContext iniCtx = new InitialContext();
			        Object tmp = iniCtx.lookup("ConnectionFactory");
			        ((JMSContext) tmp).createProducer().send(testQueue, "hi");
			        log.debug("tmp : " + tmp);
			        QueueConnectionFactory qcf = (QueueConnectionFactory) tmp;
			        conn = qcf.createQueueConnection();
			      //  que = (Queue) iniCtx.lookup("queue/testQueue");
			        session = conn.createQueueSession(false,  QueueSession.AUTO_ACKNOWLEDGE);
			        conn.start();
				
				
/*				 try {
					 
					 log.debug("********** context :  " + context);
					 context.createProducer().send(myQueue, message);

					 } 
				*/
				
				/*log.debug("********** context :  " + context);
				context.createProducer().send(testQueue, txt);*/

			} catch (Exception exc) {
				exc.printStackTrace();
			}

			

	//		context.createProducer().send(queueExample, txt);

		} catch (Exception exc) {
			exc.printStackTrace();
		}

	}
}
