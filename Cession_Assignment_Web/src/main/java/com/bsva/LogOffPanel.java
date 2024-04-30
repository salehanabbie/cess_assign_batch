package com.bsva;

import java.io.Serializable;

import org.apache.wicket.Session;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.http.handler.RedirectRequestHandler;

import com.bsva.authentication.EncryptPassword;
import com.bsva.constant.Constant;
import com.bsva.models.ClientSessionModel;

public class LogOffPanel  extends Panel implements Serializable{

	/**
	 * @author ElelwaniR
	 */
	private static final long serialVersionUID = 1L;
	private ClientSessionModel clientSessionModel;
    public static Session session;
    
    
	public LogOffPanel(String id) {
		super(id);
		logoffDetails();

	}

	private void  logoffDetails ()
	{
		
		this.getPage()
		.getRequestCycle()
		.scheduleRequestHandlerAfterCurrent(
				new RedirectRequestHandler(Constant
						.getUrlIAM()
						+ "?"
						+ EncryptPassword
								.encrypt(clientSessionModel
										.getUsername())
						+ "?"
						+ EncryptPassword.encrypt(String
								.valueOf(clientSessionModel
										.getRoleId()))
						+ "?"
						+ EncryptPassword
								.encrypt(clientSessionModel
										.getUserRole())
						+ "?"
						+ EncryptPassword
								.encrypt(clientSessionModel
										.getMemeberNumber())
						+ "?"
						+ EncryptPassword.encrypt(String
								.valueOf(clientSessionModel
										.getUserId()))
						+ "?"
						+ EncryptPassword
								.encrypt(clientSessionModel
										.getSystemName())));

session.clear();


	}
}
