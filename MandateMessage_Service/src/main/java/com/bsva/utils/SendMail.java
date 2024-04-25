package com.bsva.utils;

import java.io.Serializable;
import java.util.Date;

import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Message;
import javax.mail.Transport;

import org.apache.log4j.Logger;





public class SendMail implements Serializable {
	
	
	/**
	 * @author ElelwaniR
	 */
	private static final long serialVersionUID = 1L;

	private String from = "NonRespond@bankservafrica.com";
	private String subject;
	public static Logger log = Logger.getLogger(SendMail.class);
	
	private String messageBody;
	Session session ;
	
	private String Disclaimer = "BankservAfrica is a BBBEE level 3 procurement contributor"+ "\n" +
			
"This e-mail and its attachments, if any, are subject to BankservAfrica's e-mail disclaimer which is available on" + "\n"+

"http://www.bankservafrica.com/Contactus/EmailDisclaimeraspx"+"\n" + 

"Please do not reply to this email as it is auto generated and this mailbox does not receive return mail.";
   
		

		
	
	
	public SendMail(String recipient,String action , String username){
		


        /**
         * Utility method to send simple HTML email
         * @param session
         * @param toEmail
         * @param subject
         * @param body
         */
		

    	
		if (action=="Capture"){
			
			subject = "Capture Mandate Online";
    		messageBody = "Dear Sir/Madam, " + username + "\n"  + "\n" + "Kindly note that your Mandate Acceptance Request Messages are ready for downloading .Please sign on to the Mandates System to retrieve them " + "\n" + "\n" + "Username : " + username +  "\n"  + "\n" + "Regards" + "\n" + "Mandates System Administrator"+ "\n"  + "\n" + Disclaimer;  
			
		}
		
		else {
			
			subject="Update Mandates Online";
			messageBody = "Dear " + username + "\n"  + "\n" + "Kindly note that your Mandate Acceptance Request Messages are ready for downloading .Please sign on to the Mandates System to retrieve them." + "\n" + "\n" + "Username : " + username +  "\n"  + "\n" + "Regards" + "\n" + "Mandates System Administrator"+ "\n"  + "\n" + Disclaimer;  
		}
		try {
			
			MimeMessage msg = new MimeMessage(session);
	          //set message headers
	          msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
	          msg.addHeader("format", "flowed");
	          msg.addHeader("Content-Transfer-Encoding", "8bit");
	 
	          msg.setFrom(new InternetAddress(from, "NoReply-JD"));
	 
	          msg.setReplyTo(InternetAddress.parse(from, false));
	 
	          msg.setSubject(subject, "UTF-8");
	          
	          msg.setText(messageBody);
	 
	          msg.setSentDate(new Date());
	 
	          msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient, false));
	          Transport.send(msg); 
	 
	          log.debug("********************This email has been successfully sent*******************");
	        }
	        catch (Exception e) {
	          e.printStackTrace();
	        }

			
		}
	}



