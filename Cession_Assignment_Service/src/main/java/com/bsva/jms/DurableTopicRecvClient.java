package com.bsva.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSubscriber;
import javax.jms.TopicSession;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;



/**
 *  A JMS client example program that synchronously receives a message a Topic
 *     
 *  @author Scott.Stark@jboss.org
 *  @version $Revision: 1.9 $
 */
public class DurableTopicRecvClient
{
    TopicConnection conn = null;
    TopicSession session = null;
    Topic topic = null;
	public static Logger log = Logger.getLogger(DurableTopicRecvClient.class);
    
    public void setupPubSub()
        throws JMSException, NamingException
    {
        InitialContext iniCtx = new InitialContext();
        Object tmp = iniCtx.lookup("ConnectionFactory");

        TopicConnectionFactory tcf = (TopicConnectionFactory) tmp;
        conn = tcf.createTopicConnection();
        topic = (Topic) iniCtx.lookup("topic/testTopic");

        session = conn.createTopicSession(false,
                                          TopicSession.AUTO_ACKNOWLEDGE);
        conn.start();
    }
    
    public void recvSync()
        throws JMSException, NamingException
    {
        log.debug("Begin recvSync");
        // Setup the pub/sub connection, session
        setupPubSub();
        // Wait upto 5 seconds for the message
        TopicSubscriber recv = session.createDurableSubscriber(topic, "jms-ex1dtps");
        Message msg = recv.receive(5000);
        if (msg == null) {
            log.debug("Timed out waiting for msg");
        } else {
            log.debug("DurableTopicRecvClient.recv, msgt=" + msg);
        } 
    }
    
    public void stop() 
        throws JMSException
    {
        conn.stop();
        session.close();
        conn.close();
    }
    
    public static void main(String args[]) 
        throws Exception
    {
        log.debug("Begin DurableTopicRecvClient, now=" + 
                           System.currentTimeMillis());
        DurableTopicRecvClient client = new DurableTopicRecvClient();
        client.recvSync();
        client.stop();
        log.debug("End DurableTopicRecvClient");
        System.exit(0);
    }
    
}