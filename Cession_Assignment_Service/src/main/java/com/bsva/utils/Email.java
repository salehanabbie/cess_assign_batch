package com.bsva.utils;

import java.io.Serializable;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Email implements Serializable {
	private static final long serialVersionUID = 1L;

	public void SendEmails(String host, final String userName,
			final String password, String toAddress, String subject,
			String message) throws AddressException, MessagingException {

		// Get the session object
		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", host);
		Session session = Session.getDefaultInstance(properties);

		// creates a new e-mail message
		Message msg = new MimeMessage(session);

		// set message headers
		msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
		msg.addHeader("format", "flowed");
		msg.addHeader("Content-Transfer-Encoding", "8bit");

		// msg.setFrom(new InternetAddress(from, "NoReply-JD"));
		msg.setFrom(new InternetAddress(userName));
		InternetAddress[] toAddresses = { new InternetAddress(toAddress) };
		msg.setRecipients(Message.RecipientType.TO, toAddresses);

		msg.setSubject(subject);
		msg.setSentDate(new Date());
		// set plain text message
		msg.setContent(message, "text/html");
		


		// sends the e-mail
		Transport.send(msg);

	}

	public static void main(String[] args) {
		// SMTP server information
		String host = "172.16.27.40";// or IP address

		String mailFrom = "NonRespond@bankservafrica.com";
		String password = "your-email-password";

		// outgoing message information
		String mailTo = "nosiphos@bankservafrica.com";
		String subject = "Mandate Online";

		// message contains HTML markups
		String message = "<i>Dear Sir/Madam,</i><br>";
		message += "Kindly note that your Mandate Acceptance Request Messages are ready for downloading .Please sign on to the Mandates System to retrieve them<br>";
		message += "<b>Mandates System Administrator</b><br>";

		Email mailer = new Email();

		try {
			mailer.SendEmails(host, mailFrom, password, mailTo, subject,
					message);
			System.out.println("Email is successfully send.");
		} catch (Exception ex) {
			System.out.println("Failed to sent email.");
			ex.printStackTrace();
		}

	}

}
