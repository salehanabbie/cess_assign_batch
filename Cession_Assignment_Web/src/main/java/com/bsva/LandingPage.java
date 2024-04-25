

package com.bsva;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;

import org.apache.wicket.Session;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.http.handler.RedirectRequestHandler;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.bsva.authentication.EncryptPassword;
import com.bsva.models.ClientSessionModel;

/**
 * @author jeremym
 * 
 */
public class LandingPage extends TemplatePage implements Serializable {

	/**
	 * 
	 */
	public static ClientSessionModel clientSessionModel;
	public static Session session;
	private static final long serialVersionUID = 1L;
	TemplatePage templatePage;

	/**
	 * @param parameters
	 * @throws UnsupportedEncodingException
	 */
	public LandingPage(final PageParameters parameters)
			throws UnsupportedEncodingException {
		super(parameters);

       
	/*	String searchURL = null;


		try {
			String url = RequestCycle.get().getRequest().getOriginalUrl().toString();

			int i = url.indexOf("?");

			if (i > -1) {
				searchURL = url.substring(url.indexOf("?") + 1);

				System.out.println("URL: " + searchURL);
			}

		} catch (Exception e) {
			this.getPage().getRequestCycle().scheduleRequestHandlerAfterCurrent(new RedirectRequestHandler(Constant.getUrlIAM()));
		}

		initMap(searchURL);

	}
	
	private void initMap(String url) {

		try {
			String deter = "\\+" + "\\+" + "\\%3F";
			String params[];

			if (url.contains(deter)) {
			} else {
				deter = "\\%0D" + "\\%0A" + "\\%3F";
				if (url.contains(deter)) {
				} else {
					deter = "\\%3F";
				}
			}
			params = url.split(deter);

			clientSessionModel = new ClientSessionModel();

			clientSessionModel.setUsername(EncryptPassword.decrypt(params[0].replace("%3D", "=")));

			BigDecimal roleId = new BigDecimal(EncryptPassword.decrypt(params[1].replace("%3D", "=")));
			clientSessionModel.setRoleId(roleId);
			clientSessionModel.setUserRole(EncryptPassword.decrypt(params[2].replace("%3D", "=")));
			clientSessionModel.setMemeberNumber(EncryptPassword.decrypt(params[3].replace("%3D", "=")));
			BigDecimal userId = new BigDecimal(EncryptPassword.decrypt(params[4].replace("%3D", "=")));
			clientSessionModel.setUserId(userId);
			clientSessionModel.setSystemName(EncryptPassword.decrypt(params[5].replace("%3D", "=")));

			Session session = getSession();
			session.setAttribute("role", clientSessionModel);

		} catch (Exception e) {
			getRequestCycle().scheduleRequestHandlerAfterCurrent(new RedirectRequestHandler(Constant.getUrlIAM()));

		}

		session = getSession();
		clientSessionModel = (ClientSessionModel) session.getAttribute("role");

		try {

			clientSessionModel.getUsername();

		} catch (Exception e) {
			session.clear();
			getRequestCycle().scheduleRequestHandlerAfterCurrent(
					new RedirectRequestHandler(Constant.getUrlIAM()));
		}
*/
	}


}

