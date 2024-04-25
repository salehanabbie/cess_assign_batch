package com.bsva;

import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.naming.InitialContext;

import com.bsva.jms.SendRecvClient.ExListener;

/**
 * Session Bean implementation class SyncReciever
 */
@Stateless
@LocalBean
public class SyncReciever {

    QueueConnection conn;
    QueueSession session;
    Queue que;
	private Object tmp;
	
	
	public SyncReciever(){
		
        try {
			InitialContext iniCtx = new InitialContext();
			 tmp = iniCtx.lookup("ConnectionFactory");
			QueueConnectionFactory qcf = (QueueConnectionFactory) tmp;
			conn = qcf.createQueueConnection();
			que = (Queue) iniCtx.lookup("queue/testQueue");
			session = conn.createQueueSession(false,
					QueueSession.AUTO_ACKNOWLEDGE);
			conn.start(); 
		} catch (Exception e) {
			e.printStackTrace(); 
		}
		
		
	}
	
	
	 public String startReceiver() {
	   /*String message = ((JMSContext) tmp).createConsumer(que).receiveBody(String.class);*/
		 
		 ExListener exListener = new ExListener(); 
/*	        QueueReceiver recv = session.createReceiver(que);
	        recv.setMessageListener(exListener);*/
	 //  System.out.println("message : " + exListener.onMessage(""));
	   return "";
	 }
}

















