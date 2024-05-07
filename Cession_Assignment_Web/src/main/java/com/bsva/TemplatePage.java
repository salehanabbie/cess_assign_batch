package com.bsva;

import com.bsva.auditprocess.auditTrail.ViewAuditTrackingScreenPanel;
import com.bsva.auditprocess.auditTrail.auditTrackingReportPanel;
import com.bsva.authentication.EncryptPassword;
import com.bsva.commons.model.AudSystemProcessModel;
import com.bsva.commons.model.IamSessionModel;
import com.bsva.commons.model.SystemParameterModel;
import com.bsva.constant.Constant;
import com.bsva.controller.Controller;
import com.bsva.models.ClientSessionModel;
import com.bsva.panels.DeleteEOTFile.ViewDeleteEOTFile;
import com.bsva.panels.DeleteInputFile.ViewDeleteInputFile;
import com.bsva.panels.FileStatusDescription.ViewFileStatusDescription;
import com.bsva.panels.ForceClosure.ViewForceClosure;
import com.bsva.panels.ManualBilling.ManualBilling;
import com.bsva.panels.ManualEndofDay.EndofDayPanel;
import com.bsva.panels.ManualStartOfDay.ManualStartOfDay;
import com.bsva.panels.PHIReports.ViewPHIReportsPanel;
import com.bsva.panels.SodEodScreens.OpsViewFileSizeLimit;
import com.bsva.panels.SodEodScreens.ViewOpsAcSotEotPanel;
import com.bsva.panels.SodEodScreens.ViewOpsCustomerParameters;
import com.bsva.panels.SodEodScreens.ViewOpsRefSeqPanel;
import com.bsva.panels.SodEodScreens.ViewOpsSlaTimesPanel;
import com.bsva.panels.SodEodScreens.viewOpsServices;
import com.bsva.panels.SystemParameters.ViewInActivePanel;
import com.bsva.panels.SystemParameters.ViewSystemParametersPanel;
import com.bsva.panels.SystemStatus.IncomingSystemStatusPanel;
import com.bsva.panels.SystemStatus.OutgoingSystemStatusPanel;
import com.bsva.panels.SystemStatus.SystemStatusTabPanel;
import com.bsva.panels.accountType.ViewAccountTypePanel;
import com.bsva.panels.adjustmentCategory.ViewAdjustmentCategoryPanel;
import com.bsva.panels.amendmentCodes.ViewAmendmentCodesPanel;
import com.bsva.panels.cisDownload.CISDownloadPanel;
import com.bsva.panels.cisDownload.ViewCisBankPanel;
import com.bsva.panels.cisDownload.ViewCisBranchPanel;
import com.bsva.panels.copyFiles.ViewCopyFilesPanel;
import com.bsva.panels.currencyCodes.ViewCurrencyCodesPanel;
import com.bsva.panels.debitValueType.ViewDebitValueTypePanel;
import com.bsva.panels.download.DownloadPanel;
import com.bsva.panels.errorCodes.ViewConfgErrorCodesPanel;
import com.bsva.panels.fileMonitoring.FileMonitoringTabPanel;
import com.bsva.panels.fileSizeLimit.ViewFileSizeLimitPanel;
import com.bsva.panels.frequencyCodes.ViewFrequencyCodesPanel;
import com.bsva.panels.localInstrCodes.ViewLocalInstrPanel;
import com.bsva.panels.mandateServices.ViewSystemControlServicesPanel;
import com.bsva.panels.manualSOTandEOT.ManualStartOfTransmissionPanel;
import com.bsva.panels.processStatus.ViewProcessStatusPanel;
import com.bsva.panels.reasonCodes.ViewReasonCodesPanel;
import com.bsva.panels.reports.ReportPanel;
import com.bsva.panels.reports.ViewReportConfgScreen;
import com.bsva.panels.scheduler.SchedulerPanel;
import com.bsva.panels.schedulers.SchedulePanel;
import com.bsva.panels.sequenceTypes.ViewSequenceTypesPanel;
import com.bsva.panels.serverLogs.ViewServerLogsPanel;
import com.bsva.panels.severityCodes.ViewSeverityCodesPanel;
import com.bsva.panels.sysCtrlSlaTimes.ViewSysCtrlSlaTimePanel;
import com.bsva.panels.systemInfo.DelDeliveryPanel;
import com.bsva.panels.systemInfo.SystemMonitorPanel;
import com.bsva.panels.testPanel.TestPanel;
import com.bsva.panels.uploadFile.UploadMandateForm;
import com.bsva.panels.viewFiles.ViewFilesTabPanel;
import com.cooldatasoft.common.MenuItem;
import com.cooldatasoft.horizontal.dropdown.sunrisegloss.SunriseGlossDropDownMenu;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import org.apache.wicket.Session;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.http.handler.RedirectRequestHandler;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class TemplatePage extends BasePage /* implements IAjaxIndicatorAware */ implements Serializable{

	private static final long serialVersionUID = 1L;
	public static Logger log = Logger.getLogger(TemplatePage.class);
	private HeaderPanel headerPanel;
	private MenuPanel menuPanel;
	private ClientSessionModel clientSessionModel;
	public static Session session;
	Controller controller;

	private SystemParameterModel systemParameterModel =  null;

	/* private ContentPanel contentPanel; */
	private FooterPanel footerPanel;
	private static WebMarkupContainer ajaxPanel;
	public TemplatePage(PageParameters parameters)  throws UnsupportedEncodingException  
	{
		super(parameters);
		String searchURL = null;
		clientSessionModel = new ClientSessionModel();

		try 
		{
			String url = RequestCycle.get().getRequest().getOriginalUrl() .toString();
			int i = url.indexOf("?");
			if (i > -1) 
			{
				searchURL = url.substring(url.indexOf("?") + 1);
				System.out.println("URL: " + searchURL);
			}
		} 
		catch (Exception e) 
		{
			session.clear();
			//this.getPage() .getRequestCycle().scheduleRequestHandlerAfterCurrent(new RedirectRequestHandler(Constant.getUrlIAM()));
			getRequestCycle().scheduleRequestHandlerAfterCurrent(new RedirectRequestHandler(Constant.getUrlIAM()));
		}
		initMap(searchURL);
	}

	private void initMap(String url) 
	{
		controller = new Controller();
		systemParameterModel = (SystemParameterModel) controller.retrieveWebActiveSysParameter();
		log.debug("<<<<<IsystemParameterModel>>>>>"+systemParameterModel);



		log.debug("<<<<<IN THE initMap>>>>>");

		try 
		{
			String deter = "&";
			String params[];

			/*	if (url.contains(deter)) {
			} else {
				deter = "\\%0D" + "\\%0A" + "\\%3F";
				if (url.contains(deter)) {
				} else {
					deter = "\\%3F";
				}
			}*/
			params = url.split(deter);
			log.debug("params----->"+params);
			log.debug(Arrays.toString(params));
			clientSessionModel = new ClientSessionModel();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			IamSessionModel iamSessionModel = new IamSessionModel();

			String value1 = EncryptPassword.decrypt(URLDecoder.decode(params[0].replace("value1=", ""), "UTF-8"));
			String value2 = EncryptPassword.decrypt(URLDecoder.decode(params[1].replace("value2=", ""), "UTF-8"));
			String value3 =EncryptPassword.decrypt(URLDecoder.decode(params[2].replace("value3=", ""), "UTF-8"));
			String value4 =EncryptPassword.decrypt(URLDecoder.decode(params[3].replace("value4=", ""), "UTF-8"));
			String value5 = EncryptPassword.decrypt(URLDecoder.decode(params[4].replace("value5=", ""), "UTF-8"));
			String value6 = EncryptPassword.decrypt(URLDecoder.decode(params[5].replace("value6=", ""), "UTF-8"));
			String value7 = EncryptPassword.decrypt(URLDecoder.decode(params[6].replace("value7=", ""), "UTF-8"));
			log.debug("params[0] VALUE 1 <<<<----->>>>>>>>>>"+value1);
			log.debug("params[1] VALUE 2 <<<<----->>>>>>>>>>"+value2);
			log.debug("params[2] VALUE 3 <<<<----->>>>>>>>>>"+value3);
			log.debug("params[3] VALUE 4 <<<<----->>>>>>>>>>"+value4);
			log.debug("params[4] VALUE 5 <<<<----->>>>>>>>>>"+value5);
			log.debug("params[5] VALUE 6 <<<<----->>>>>>>>>>"+value6);
			log.debug("params[6] VALUE 7 <<<<----->>>>>>>>>>"+value7);

			boolean sessionExists = controller.checkIamSession(value1);
			log.debug("sessionExists -----> "+sessionExists);
			if(sessionExists)
			{
				getRequestCycle().scheduleRequestHandlerAfterCurrent( new RedirectRequestHandler(Constant.getUrlIAM()));
				return;
			}

			try 
			{
				iamSessionModel.setSessionKey (EncryptPassword.decrypt(URLDecoder.decode(params[0].replace("value1=", ""), "UTF-8")));
				iamSessionModel.setSessionDate (sdf.parse(EncryptPassword.decrypt(URLDecoder.decode(params[1].replace("value2=", ""), "UTF-8"))));
				iamSessionModel.setUserName (EncryptPassword.decrypt(URLDecoder.decode(params[2].replace("value3=", ""), "UTF-8")));
				iamSessionModel.setUserRole(EncryptPassword.decrypt(URLDecoder.decode(params[6].replace("value7=", ""), "UTF-8")));
				controller.createSession(iamSessionModel);
			} 
			catch (Exception e) 
			{
				log.error("MenuPanel : initMap() : Error encountered while saving the session : "+ e.getMessage(), e);
				getRequestCycle().scheduleRequestHandlerAfterCurrent(new RedirectRequestHandler(Constant.getUrlIAM()));
			}


			clientSessionModel.setSessionKey(EncryptPassword.decrypt(URLDecoder.decode(params[0].replace("value1=", ""), "UTF-8")));
			clientSessionModel.setDate(sdf.parse(EncryptPassword.decrypt(URLDecoder.decode(params[1].replace("value2=", ""), "UTF-8"))));
			clientSessionModel.setUsername(EncryptPassword.decrypt(URLDecoder.decode(params[2].replace("value3=", ""), "UTF-8")));
			clientSessionModel.setPermissions(EncryptPassword.decrypt(URLDecoder.decode(params[3].replace("value4=", ""), "UTF-8")));
			clientSessionModel.setSystemName(EncryptPassword.decrypt(URLDecoder.decode(params[4].replace("value5=", ""), "UTF-8")));
			clientSessionModel.setMemeberNumber(EncryptPassword.decrypt(URLDecoder.decode(params[5].replace("value6=", ""), "UTF-8")));
			clientSessionModel.setUserRole(EncryptPassword.decrypt(URLDecoder.decode(params[6].replace("value7=", ""), "UTF-8")));

			log.debug("SessionKey---------->"+clientSessionModel.getSessionKey());
			log.info("UserRole---------->"+clientSessionModel.getUserRole());
			log.info("Username---------->"+clientSessionModel.getUsername());



			Session session = getSession();
			log.debug("session==============> "+session);
			session.setAttribute("role", clientSessionModel);

			//Update System Audit Log Information
			AudSystemProcessModel audSystemProcessModel = new AudSystemProcessModel();
			audSystemProcessModel.setProcess(controller.getProperty("AUD.SYSPROCESS.LOGIN"));
			audSystemProcessModel.setProcessDate(new Date());
			audSystemProcessModel.setUserId(clientSessionModel.getUsername());

			controller.saveSystemAuditInfo(audSystemProcessModel);


			log.info("****************************** " + EncryptPassword.decrypt(URLDecoder.decode(params[2].replace("value3=", ""), "UTF-8")) + " is Logged onto Mandates System ******************************");
		} 
		catch (Exception e) 
		{
			getRequestCycle().scheduleRequestHandlerAfterCurrent(new RedirectRequestHandler(Constant.getUrlIAM()));
		}
		//SalehaR - 20170308 - this Not in Collections 
		session = getSession();
		clientSessionModel = (ClientSessionModel) session.getAttribute("role");

		try {

			clientSessionModel.getUsername();

		} catch (Exception e) {
			session.clear();
			getRequestCycle().scheduleRequestHandlerAfterCurrent(
					new RedirectRequestHandler(Constant.getUrlIAM()));
		}

		List<MenuItem> primaryMenuList = buildMenu();

		ajaxPanel = new WelcomePanel("content");
		ajaxPanel.setOutputMarkupId(true);
		ajaxPanel.setOutputMarkupPlaceholderTag(true);

		//		FeedbackPanel feedback = new FeedbackPanel("feedbackPanel");
		//		FeedbackPanel feedbackPanel = new FeedbackPanel("feedbackPanel");
		//		feedbackPanel.setOutputMarkupId(true);
		//		feedbackPanel.setFilter(new IFeedbackMessageFilter() 
		//		{
		//			@Override
		//			public boolean accept(FeedbackMessage aMessage)
		//			{
		//				Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		//				String username = auth != null ? auth.getName() : "UNKNOWN";
		//				if (aMessage.isFatal()) {
		//					LOG.fatal(username + ": " + aMessage.getMessage());
		//				}
		//				else if (aMessage.isError()) {
		//					LOG.error(username + ": " + aMessage.getMessage());
		//				}
		//				else if (aMessage.isWarning()) {
		//					LOG.warn(username + ": " + aMessage.getMessage());
		//				}
		//				else if (aMessage.isInfo()) {
		//					LOG.info(username + ": " + aMessage.getMessage());
		//				}
		//				else if (aMessage.isDebug()) {
		//					LOG.debug(username + ": " + aMessage.getMessage());
		//				}
		//				return true;
		//			}
		//		});
		//		

		// ajaxPanel.add(new Label("label", "test"));

		headerPanel = new HeaderPanel("headerPanel");
		menuPanel = new MenuPanel("menuPanel");
		footerPanel = new FooterPanel("footerPanel");

		add(headerPanel);
		add(menuPanel);
		add(footerPanel);
		add(new SunriseGlossDropDownMenu("sunriseGlossMenu", primaryMenuList));
		add(ajaxPanel);
		//		add(feedbackPanel);
		// System.out.println("Ajax Panel loaded");

	}
	/**
	 * @return
	 */
	private List<MenuItem> buildMenu() {


		// define the AjaxFallbackLink

		AjaxFallbackLink<Void> errorCodeAjaxFallbacklink = new AjaxFallbackLink<Void>("subMenuLink") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) 
			{
				ViewConfgErrorCodesPanel viewConfgErrorCodesPanel = new ViewConfgErrorCodesPanel("content");
				viewConfgErrorCodesPanel.setOutputMarkupId(true);
				viewConfgErrorCodesPanel.setOutputMarkupPlaceholderTag(true);
				ajaxPanel.replaceWith(viewConfgErrorCodesPanel);
				ajaxPanel = viewConfgErrorCodesPanel;
				target.add(ajaxPanel);

			}

		};

		AjaxFallbackLink<Void> startofdayFallbackLink = new AjaxFallbackLink<Void>(
				"subMenuLink") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {

				ManualStartOfDay startofDayPanel = new ManualStartOfDay(   "content");
				startofDayPanel.setOutputMarkupId(true);
				startofDayPanel.setOutputMarkupPlaceholderTag(true);
				ajaxPanel.replaceWith(startofDayPanel);
				ajaxPanel = startofDayPanel;
				target.add(startofDayPanel);
			}
		};


		AjaxFallbackLink<Void> opsServicesFallbackLink = new AjaxFallbackLink<Void>("subMenuLink") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {

				viewOpsServices viewOpsServicesPanel = new viewOpsServices("content");
				viewOpsServicesPanel.setOutputMarkupId(true);
				viewOpsServicesPanel.setOutputMarkupPlaceholderTag(true);
				ajaxPanel.replaceWith(viewOpsServicesPanel);
				ajaxPanel = viewOpsServicesPanel;
				target.add(viewOpsServicesPanel);

			}

		};
		AjaxFallbackLink<Void> endofdayFallbackLink = new AjaxFallbackLink<Void>("subMenuLink") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {

				EndofDayPanel endofDayPanel = new EndofDayPanel(
						"content");
				endofDayPanel.setOutputMarkupId(true);
				endofDayPanel.setOutputMarkupPlaceholderTag(true);
				ajaxPanel.replaceWith(endofDayPanel);
				ajaxPanel = endofDayPanel;
				target.add(endofDayPanel);

			}

		};


		/*	AjaxFallbackLink<Void> namePrefixFallbackLink = new AjaxFallbackLink<Void>(
				"subMenuLink") {
			private static final long serialVersionUID = 1L; 

			@Override
			public void onClick(AjaxRequestTarget target) {

				ViewNamePrefixPanel namePrefixPanel = new ViewNamePrefixPanel(
						"content");
				namePrefixPanel.setOutputMarkupId(true);
				namePrefixPanel.setOutputMarkupPlaceholderTag(true);
				ajaxPanel.replaceWith(namePrefixPanel);
				ajaxPanel = namePrefixPanel;
				target.add(namePrefixPanel);

			}

		};
		 */


		AjaxFallbackLink<Void> currencyCodesFallbackLink = new AjaxFallbackLink<Void>(
				"subMenuLink") {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void onClick(AjaxRequestTarget target) {

				ViewCurrencyCodesPanel currencycodesPanel = new ViewCurrencyCodesPanel(
						"content");
				currencycodesPanel.setOutputMarkupId(true);
				currencycodesPanel.setOutputMarkupPlaceholderTag(true);
				ajaxPanel.replaceWith(currencycodesPanel);
				ajaxPanel = currencycodesPanel;
				target.add(ajaxPanel);
			}
		};



		AjaxFallbackLink<Void> viewFileStatusDescriptionLink  = new AjaxFallbackLink<Void>("subMenuLink")
		{
			private static final long serialVersionUID = 1L;

			public void onClick(AjaxRequestTarget target) {

				ViewFileStatusDescription  viewFileStatusDescription = new ViewFileStatusDescription("content");
				viewFileStatusDescription.setOutputMarkupId(true);
				viewFileStatusDescription.setOutputMarkupPlaceholderTag(true);
				ajaxPanel.replaceWith(viewFileStatusDescription);
				ajaxPanel = viewFileStatusDescription;
				target.add(ajaxPanel);
			}
		};

		AjaxFallbackLink<Void> viewopsSlaTimes  = new AjaxFallbackLink<Void>("subMenuLink")
		{
			private static final long serialVersionUID = 1L;

			public void onClick(AjaxRequestTarget target) {

				ViewOpsSlaTimesPanel  viewOpsSlaTimesPanel = new ViewOpsSlaTimesPanel("content");
				viewOpsSlaTimesPanel.setOutputMarkupId(true);
				viewOpsSlaTimesPanel.setOutputMarkupPlaceholderTag(true);
				ajaxPanel.replaceWith(viewOpsSlaTimesPanel);
				ajaxPanel = viewOpsSlaTimesPanel;
				target.add(ajaxPanel);
			}
		};


		/*								AjaxFallbackLink<Void> authTypeLink = new AjaxFallbackLink<Void>("subMenuLink") 
										{

		 *//***
		 * 
		 *//*
									private static final long serialVersionUID = 1L;

									public void onClick(AjaxRequestTarget target) {

										ViewAuthTypePanel  authTypePanel = new ViewAuthTypePanel("content");
										authTypePanel.setOutputMarkupId(true);
										authTypePanel.setOutputMarkupPlaceholderTag(true);
										ajaxPanel.replaceWith(authTypePanel);
										ajaxPanel = authTypePanel;
										target.add(ajaxPanel);
									}
										};
		  */


		AjaxFallbackLink<Void> reasonCodesAjaxFallbackLink = new AjaxFallbackLink<Void>(
				"subMenuLink") {

			/**
			 * 
			 */

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {

				ViewReasonCodesPanel viewReasonCodesPanel = new ViewReasonCodesPanel(
						"content");
				viewReasonCodesPanel.setOutputMarkupId(true);
				viewReasonCodesPanel.setOutputMarkupPlaceholderTag(true);

				ajaxPanel.replaceWith(viewReasonCodesPanel);

				ajaxPanel = viewReasonCodesPanel;

				target.add(ajaxPanel);
			}
		};

		AjaxFallbackLink<Void> debitValueTypeAjaxFallbackLink = new AjaxFallbackLink<Void>(
				"subMenuLink") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {

				ViewDebitValueTypePanel viewDebitValueTypePanel = new ViewDebitValueTypePanel(
						"content");
				viewDebitValueTypePanel.setOutputMarkupId(true);
				viewDebitValueTypePanel.setOutputMarkupPlaceholderTag(true);
				ajaxPanel.replaceWith(viewDebitValueTypePanel);
				ajaxPanel = viewDebitValueTypePanel;
				target.add(ajaxPanel);
			}
		};


		AjaxFallbackLink<Void> localintsrumentsCodesAjaxFallbackLink = new AjaxFallbackLink<Void>(
				"subMenuLink") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {

				ViewLocalInstrPanel viewlocalinstrumentCodesPanel = new ViewLocalInstrPanel(
						"content");
				viewlocalinstrumentCodesPanel.setOutputMarkupId(true);
				viewlocalinstrumentCodesPanel               .setOutputMarkupPlaceholderTag(true);
				ajaxPanel.replaceWith(viewlocalinstrumentCodesPanel);
				ajaxPanel = viewlocalinstrumentCodesPanel;
				target.add(ajaxPanel);
			}
		};

		AjaxFallbackLink<Void> downloadMandatesAjaxFallbackLink = new AjaxFallbackLink<Void>(
				"subMenuLink") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {

				DownloadPanel downloadPanel = new DownloadPanel("content");
				downloadPanel.setOutputMarkupId(true);
				downloadPanel.setOutputMarkupPlaceholderTag(true);
				ajaxPanel.replaceWith(downloadPanel);
				ajaxPanel = downloadPanel;

				target.add(ajaxPanel);
			}
		};


		AjaxFallbackLink<Void> uploadMandatesAjaxFallbackLink = new AjaxFallbackLink<Void>(
				"subMenuLink") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {

				UploadMandateForm uploadPanel = new UploadMandateForm("content");
				uploadPanel.setOutputMarkupId(true);
				uploadPanel.setOutputMarkupPlaceholderTag(true);
				ajaxPanel.replaceWith(uploadPanel);
				ajaxPanel = uploadPanel;
				target.add(ajaxPanel);
			}
		};

		AjaxFallbackLink<Void> delDeliveryAjaxFallbackLink = new AjaxFallbackLink<Void>(
				"subMenuLink") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {

				DelDeliveryPanel delDeliveryPanel = new DelDeliveryPanel("content");
				delDeliveryPanel.setOutputMarkupId(true);
				delDeliveryPanel.setOutputMarkupPlaceholderTag(true);
				ajaxPanel.replaceWith(delDeliveryPanel);
				ajaxPanel = delDeliveryPanel;
				target.add(ajaxPanel);

			}
		};

		AjaxFallbackLink<Void> fileStatusAjaxFallbackLink = new AjaxFallbackLink<Void>(
				"subMenuLink") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {

				FileMonitoringTabPanel fileMonitoringTabPanel = new FileMonitoringTabPanel("content");
				fileMonitoringTabPanel.setOutputMarkupId(true);
				fileMonitoringTabPanel.setOutputMarkupPlaceholderTag(true);
				ajaxPanel.replaceWith(fileMonitoringTabPanel);
				ajaxPanel = fileMonitoringTabPanel;
				target.add(ajaxPanel);

			}
		};


		AjaxFallbackLink<Void> serverLogsAjaxFallbackLink = new AjaxFallbackLink<Void>(
				"subMenuLink") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {

				ViewServerLogsPanel viewServerLogsPanel = new ViewServerLogsPanel("content");

				viewServerLogsPanel.setOutputMarkupId(true);
				viewServerLogsPanel.setOutputMarkupPlaceholderTag(true);
				ajaxPanel.replaceWith(viewServerLogsPanel);
				ajaxPanel = viewServerLogsPanel;
				target.add(ajaxPanel);

			}
		};

		AjaxFallbackLink<Void> viewCopyFilesPanelAjaxFallbackLink = new AjaxFallbackLink<Void>("subMenuLink") 
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) 
			{

				ViewCopyFilesPanel viewCopyFilesPanel = new ViewCopyFilesPanel("content");

				viewCopyFilesPanel.setOutputMarkupId(true);
				viewCopyFilesPanel.setOutputMarkupPlaceholderTag(true);
				ajaxPanel.replaceWith(viewCopyFilesPanel);
				ajaxPanel = viewCopyFilesPanel;
				target.add(ajaxPanel);
			}
		};


		AjaxFallbackLink<Void> viewForceClosureAjaxFallBackLink = new AjaxFallbackLink<Void>("subMenuLink")
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) 
			{
				ViewForceClosure viewForceClosurePanel = new ViewForceClosure("content");
				viewForceClosurePanel.setOutputMarkupId(true);
				viewForceClosurePanel.setOutputMarkupPlaceholderTag(true);
				ajaxPanel.replaceWith(viewForceClosurePanel);
				ajaxPanel=viewForceClosurePanel;
				target.add(ajaxPanel);
			}
		};

		AjaxFallbackLink<Void> reportsPanelAjaxFallbackLink = new AjaxFallbackLink<Void>("subMenuLink") 
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) 
			{
				try 
				{
					ReportPanel reportPanel = new ReportPanel("content");
					reportPanel = new ReportPanel("content");

					reportPanel.setOutputMarkupId(true);
					reportPanel.setOutputMarkupPlaceholderTag(true);
					ajaxPanel.replaceWith(reportPanel);
					ajaxPanel = reportPanel;
					target.add(ajaxPanel);

				} 
				catch (ParseException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};

		AjaxFallbackLink<Void> sysMonitorAjaxFallbackLink = new AjaxFallbackLink<Void>("subMenuLink") 
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) 
			{
				SystemMonitorPanel sysMonitorPanel = new SystemMonitorPanel("content");
				sysMonitorPanel.setOutputMarkupId(true);
				sysMonitorPanel.setOutputMarkupPlaceholderTag(true);
				ajaxPanel.replaceWith(sysMonitorPanel);
				ajaxPanel = sysMonitorPanel;
				target.add(ajaxPanel);

			}
		};
		AjaxFallbackLink<Void> systemControlServiceAjaxFallbackLink = new AjaxFallbackLink<Void>(
				"subMenuLink") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				ViewSystemControlServicesPanel viewSystemControlServicesPanel = new ViewSystemControlServicesPanel("content");
				viewSystemControlServicesPanel.setOutputMarkupId(true);
				viewSystemControlServicesPanel            .setOutputMarkupPlaceholderTag(true);
				ajaxPanel.replaceWith(viewSystemControlServicesPanel);
				ajaxPanel = viewSystemControlServicesPanel;
				target.add(ajaxPanel);

			}

		};

		AjaxFallbackLink<Void> frequencyCodesAjaxFallbackLink = new AjaxFallbackLink<Void>(
				"subMenuLink") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {

				ViewFrequencyCodesPanel frequencyCodesPanel = new ViewFrequencyCodesPanel("content");
				frequencyCodesPanel.setOutputMarkupId(true);
				frequencyCodesPanel.setOutputMarkupPlaceholderTag(true);
				ajaxPanel.replaceWith(frequencyCodesPanel);
				ajaxPanel = frequencyCodesPanel;
				target.add(ajaxPanel);

			}
		};
		AjaxFallbackLink<Void> reportCnfgScreenAjaxFallbackLink = new AjaxFallbackLink<Void>("subMenuLink") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				ViewReportConfgScreen viewReportConfgScreen = new ViewReportConfgScreen("content");
				viewReportConfgScreen.setOutputMarkupId(true);
				viewReportConfgScreen.setOutputMarkupPlaceholderTag(true);
				ajaxPanel.replaceWith(viewReportConfgScreen);
				ajaxPanel = viewReportConfgScreen;
				target.add(ajaxPanel);
			}
		};
		AjaxFallbackLink<Void> sequenceTypesAjaxFallbackLink = new AjaxFallbackLink<Void>(
				"subMenuLink") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				ViewSequenceTypesPanel sequenceTypes = new ViewSequenceTypesPanel("content");
				sequenceTypes.setOutputMarkupId(true);
				sequenceTypes.setOutputMarkupPlaceholderTag(true);
				ajaxPanel.replaceWith(sequenceTypes);
				ajaxPanel = sequenceTypes;
				target.add(ajaxPanel);

			}
		};

		AjaxFallbackLink<Void> auditTrackingReportAjaxFallbackLink = new AjaxFallbackLink<Void>("subMenuLink")
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target)  {
				auditTrackingReportPanel audittrackingReportPanel = new auditTrackingReportPanel("content");
				audittrackingReportPanel = new auditTrackingReportPanel("content");

				audittrackingReportPanel.setOutputMarkupId(true);
				audittrackingReportPanel.setOutputMarkupPlaceholderTag(true);
				ajaxPanel.replaceWith(audittrackingReportPanel);
				ajaxPanel = audittrackingReportPanel;
				target.add(ajaxPanel);
			} 
		};


		final AjaxFallbackLink<Void> shedularAjaxFallbackLink = new AjaxFallbackLink<Void>("subMenuLink") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {

				SchedulerPanel schedulerPanel = new SchedulerPanel("content");
				schedulerPanel.setOutputMarkupId(true);
				schedulerPanel.setOutputMarkupPlaceholderTag(true);
				ajaxPanel.replaceWith(schedulerPanel);
				ajaxPanel = schedulerPanel;
				target.add(ajaxPanel);
			}
		};

		final AjaxFallbackLink<Void> viewAuditTrackingScreenPanelAjaxFallbackLink = new AjaxFallbackLink<Void>(               "subMenuLink") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {

				ViewAuditTrackingScreenPanel viewAuditTrackingScreenPanel = new ViewAuditTrackingScreenPanel(               "content");
				viewAuditTrackingScreenPanel.setOutputMarkupId(true);
				viewAuditTrackingScreenPanel.setOutputMarkupPlaceholderTag(true);
				ajaxPanel.replaceWith(viewAuditTrackingScreenPanel);
				ajaxPanel = viewAuditTrackingScreenPanel;
				target.add(ajaxPanel);

			}
		};


		final AjaxFallbackLink<Void> viewProcessStatusPanelAjaxFallbackLink = new AjaxFallbackLink<Void>(                "subMenuLink") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {

				ViewProcessStatusPanel viewProcessStatusPanel = new ViewProcessStatusPanel(                "content");
				viewProcessStatusPanel.setOutputMarkupId(true);
				viewProcessStatusPanel.setOutputMarkupPlaceholderTag(true);
				ajaxPanel.replaceWith(viewProcessStatusPanel);
				ajaxPanel = viewProcessStatusPanel;
				target.add(ajaxPanel);

			}
		};

		AjaxFallbackLink<Void> cisDownloadFallbackLink = new AjaxFallbackLink<Void>(
				"subMenuLink") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {

				CISDownloadPanel cisDownloadPanel = new CISDownloadPanel("content");
				cisDownloadPanel.setOutputMarkupId(true);
				cisDownloadPanel.setOutputMarkupPlaceholderTag(true);
				ajaxPanel.replaceWith(cisDownloadPanel);
				ajaxPanel = cisDownloadPanel;
				target.add(cisDownloadPanel);

			}

		};


		AjaxFallbackLink<Void> manualStartOfTransmissionFallbackLink = new AjaxFallbackLink<Void>(
				"subMenuLink") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {

				ManualStartOfTransmissionPanel manualStartOfTransmissionPanel = new ManualStartOfTransmissionPanel(           "content");
				manualStartOfTransmissionPanel.setOutputMarkupId(true);
				manualStartOfTransmissionPanel.setOutputMarkupPlaceholderTag(true);
				ajaxPanel.replaceWith(manualStartOfTransmissionPanel);
				ajaxPanel = manualStartOfTransmissionPanel;
				target.add(manualStartOfTransmissionPanel);

			}
		};

		AjaxFallbackLink<Void> SystemStatusTabPanelLink = new AjaxFallbackLink<Void>("subMenuLink") 
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target)
			{
//				log.info("ajaxPanel in SystemStatus ==> "+ajaxPanel);
				SystemStatusTabPanel systemStatusTabPanel = new SystemStatusTabPanel("content");
				systemStatusTabPanel.setOutputMarkupId(true);
				systemStatusTabPanel.setOutputMarkupPlaceholderTag(true);
				ajaxPanel.replaceWith(systemStatusTabPanel);
				ajaxPanel=systemStatusTabPanel;
				target.add(systemStatusTabPanel);

			}
		};

		AjaxFallbackLink<Void> viewSystemStatusIncomingLink = new AjaxFallbackLink<Void>("subMenuLink") 
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target)
			{
//				log.info("ajaxPanel in viewSystemStatusIncomingLink ==> "+ajaxPanel);
				IncomingSystemStatusPanel incomingSystemStatusPanel = new IncomingSystemStatusPanel("content");
				incomingSystemStatusPanel.setOutputMarkupId(true);
				incomingSystemStatusPanel.setOutputMarkupPlaceholderTag(true);
				ajaxPanel.replaceWith(incomingSystemStatusPanel);
				ajaxPanel=incomingSystemStatusPanel;
				target.add(incomingSystemStatusPanel);

			}
		};

		AjaxFallbackLink<Void> viewSystemStatusOutgoingLink = new AjaxFallbackLink<Void>("subMenuLink") 
		{
			private static final long serialVersionUID = 1L;
			@Override
			public void onClick(AjaxRequestTarget target)
			{
//				log.info("ajaxPanel in viewSystemStatusOutgoingLink ==> "+ajaxPanel);
				OutgoingSystemStatusPanel outgoingSystemStatusPanel = new OutgoingSystemStatusPanel("content");
				outgoingSystemStatusPanel.setOutputMarkupId(true);
				outgoingSystemStatusPanel.setOutputMarkupPlaceholderTag(true);
				ajaxPanel.replaceWith(outgoingSystemStatusPanel);
				ajaxPanel=outgoingSystemStatusPanel;
				target.add(outgoingSystemStatusPanel);

			}
		};

		AjaxFallbackLink<Void> viewAdjustmentCategoryPanelAjaxFallbackLink = new AjaxFallbackLink<Void>(               "subMenuLink") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {

				ViewAdjustmentCategoryPanel viewAdjustmentCategoryPanel = new ViewAdjustmentCategoryPanel("content");
				viewAdjustmentCategoryPanel.setOutputMarkupId(true);
				viewAdjustmentCategoryPanel.setOutputMarkupPlaceholderTag(true);
				ajaxPanel.replaceWith(viewAdjustmentCategoryPanel);
				ajaxPanel = viewAdjustmentCategoryPanel;
				target.add(viewAdjustmentCategoryPanel);

			}

		};


		AjaxFallbackLink<Void> viewAccountTypePanelAjaxFallbackLink = new AjaxFallbackLink<Void>(               "subMenuLink") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {

				ViewAccountTypePanel viewAccountTypePanel = new ViewAccountTypePanel("content");
				viewAccountTypePanel.setOutputMarkupId(true);
				viewAccountTypePanel.setOutputMarkupPlaceholderTag(true);
				ajaxPanel.replaceWith(viewAccountTypePanel);
				ajaxPanel = viewAccountTypePanel;
				target.add(viewAccountTypePanel);

			}

		};

		AjaxFallbackLink<Void> viewSeverityCodesPanelAjaxFallbackLink = new AjaxFallbackLink<Void>(               "subMenuLink") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {

				ViewSeverityCodesPanel viewSeverityCodesPanel = new ViewSeverityCodesPanel("content");
				viewSeverityCodesPanel.setOutputMarkupId(true);
				viewSeverityCodesPanel.setOutputMarkupPlaceholderTag(true);
				ajaxPanel.replaceWith(viewSeverityCodesPanel);
				ajaxPanel = viewSeverityCodesPanel;
				target.add(viewSeverityCodesPanel);

			}

		};

		AjaxFallbackLink<Void> logOffAjaxFallbackLink = new AjaxFallbackLink<Void>("subMenuLink") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) 
			{
				//Update System Audit Log Information
				AudSystemProcessModel audSystemProcessModel = new AudSystemProcessModel();
				audSystemProcessModel.setProcess(controller.getProperty("AUD.SYSPROCESS.LOGOUT"));
				audSystemProcessModel.setProcessDate(new Date());
				audSystemProcessModel.setUserId(clientSessionModel.getUsername());

				controller.saveSystemAuditInfo(audSystemProcessModel);


				this.getPage().getRequestCycle().scheduleRequestHandlerAfterCurrent(new RedirectRequestHandler(Constant.getUrlIAM()));

				session.clear();
				target.add(ajaxPanel);

			}
		};

		AjaxFallbackLink<Void> schedulePanelFallbackLink = new AjaxFallbackLink<Void>(                "subMenuLink") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {

				SchedulePanel schedulePanel = new SchedulePanel("content");
				schedulePanel.setOutputMarkupId(true);
				schedulePanel.setOutputMarkupPlaceholderTag(true);
				ajaxPanel.replaceWith(schedulePanel);
				ajaxPanel = schedulePanel;
				target.add(schedulePanel);

			}

		};

		AjaxFallbackLink<Void> systemParametersAjaxFallbackLink = new AjaxFallbackLink<Void>("subMenuLink")
		{
			private static final long serialVersionUID = 1L;


			@Override
			public void onClick(AjaxRequestTarget target) 
			{              
				ViewSystemParametersPanel systemParametersPanel = new ViewSystemParametersPanel("content");
				systemParametersPanel.setOutputMarkupId(true);
				systemParametersPanel.setOutputMarkupPlaceholderTag(true);
				ajaxPanel.replaceWith(systemParametersPanel);
				ajaxPanel = systemParametersPanel;
				target.add(ajaxPanel);


			}
		}; 

		AjaxFallbackLink<Void> viewInActivePanelAjaxFallbackLink = new AjaxFallbackLink<Void>("subMenuLink")
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) 
			{              
				ViewInActivePanel viewInActivePanel = new ViewInActivePanel("content");
				viewInActivePanel.setOutputMarkupId(true);
				viewInActivePanel.setOutputMarkupPlaceholderTag(true);
				ajaxPanel.replaceWith(viewInActivePanel);
				ajaxPanel = viewInActivePanel;
				target.add(ajaxPanel);


			}
		};

		AjaxFallbackLink<Void> viewSystemControlServicesPanelAjaxFallbackLink = new AjaxFallbackLink<Void>(               "subMenuLink") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {

				ViewSystemControlServicesPanel viewSystemControlServicesPanel = new ViewSystemControlServicesPanel("content");
				viewSystemControlServicesPanel.setOutputMarkupId(true);
				viewSystemControlServicesPanel.setOutputMarkupPlaceholderTag(true);
				ajaxPanel.replaceWith(viewSystemControlServicesPanel);
				ajaxPanel = viewSystemControlServicesPanel;
				target.add(viewSystemControlServicesPanel);

			}

		};


		AjaxFallbackLink<Void> opsCustomerParametersFallbackLink = new AjaxFallbackLink<Void>(               "subMenuLink") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {

				ViewOpsCustomerParameters viewOpsCustomerParametersPanel = new ViewOpsCustomerParameters("content");
				viewOpsCustomerParametersPanel.setOutputMarkupId(true);
				viewOpsCustomerParametersPanel.setOutputMarkupPlaceholderTag(true);
				ajaxPanel.replaceWith(viewOpsCustomerParametersPanel);
				ajaxPanel = viewOpsCustomerParametersPanel;
				target.add(viewOpsCustomerParametersPanel);

			}

		};



		AjaxFallbackLink<Void> opsAcSotEotFallbackLink  = new AjaxFallbackLink<Void>(               "subMenuLink") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {

				ViewOpsAcSotEotPanel viewOpsAcSotEotPanel = new ViewOpsAcSotEotPanel("content");
				viewOpsAcSotEotPanel.setOutputMarkupId(true);
				viewOpsAcSotEotPanel.setOutputMarkupPlaceholderTag(true);
				ajaxPanel.replaceWith(viewOpsAcSotEotPanel);
				ajaxPanel = viewOpsAcSotEotPanel;
				target.add(viewOpsAcSotEotPanel);

			}

		};




		AjaxFallbackLink<Void> opsRefSeqFallbackLink  = new AjaxFallbackLink<Void>(     "subMenuLink") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {

				ViewOpsRefSeqPanel  viewOpsRefSeqPanel = new ViewOpsRefSeqPanel("content");
				viewOpsRefSeqPanel.setOutputMarkupId(true);
				viewOpsRefSeqPanel.setOutputMarkupPlaceholderTag(true);
				ajaxPanel.replaceWith(viewOpsRefSeqPanel);
				ajaxPanel = viewOpsRefSeqPanel;
				target.add(viewOpsRefSeqPanel);

			}

		};                                                                                                             

		AjaxFallbackLink<Void> deleteInputFileFallbackLink  = new AjaxFallbackLink<Void>(        "subMenuLink") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {

				ViewDeleteInputFile  viewDeleteInputFile = new ViewDeleteInputFile("content");
				viewDeleteInputFile.setOutputMarkupId(true);
				viewDeleteInputFile.setOutputMarkupPlaceholderTag(true);
				ajaxPanel.replaceWith(viewDeleteInputFile);
				ajaxPanel = viewDeleteInputFile;
				target.add(viewDeleteInputFile);

			}

		};                             


		AjaxFallbackLink<Void> deleteEOTFileFallbackLink  = new AjaxFallbackLink<Void>(           "subMenuLink") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {

				ViewDeleteEOTFile  viewDeleteEOTFile = new ViewDeleteEOTFile("content");
				viewDeleteEOTFile.setOutputMarkupId(true);
				viewDeleteEOTFile.setOutputMarkupPlaceholderTag(true);
				ajaxPanel.replaceWith(viewDeleteEOTFile);
				ajaxPanel = viewDeleteEOTFile;
				target.add(viewDeleteEOTFile);

			}

		};                                                             

		AjaxFallbackLink<Void> viewAmendmentCodesPanelAjaxFallbackLink = new AjaxFallbackLink<Void>(                "subMenuLink") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {

				ViewAmendmentCodesPanel viewAmendmentodesPanel = new ViewAmendmentCodesPanel("content");
				viewAmendmentodesPanel.setOutputMarkupId(true);
				viewAmendmentodesPanel.setOutputMarkupPlaceholderTag(true);
				ajaxPanel.replaceWith(viewAmendmentodesPanel);
				ajaxPanel = viewAmendmentodesPanel;
				target.add(viewAmendmentodesPanel);

			}

		};                                                             

		AjaxFallbackLink<Void> testPageAjaxFallbackLink = new AjaxFallbackLink<Void>( "subMenuLink") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {

				TestPanel testPanel = new TestPanel("content");
				testPanel.setOutputMarkupId(true);
				testPanel.setOutputMarkupPlaceholderTag(true);
				ajaxPanel.replaceWith(testPanel);
				ajaxPanel = testPanel;
				target.add(testPanel);
			}
		};   

		AjaxFallbackLink<Void> viewFilesAjaxFallbackLink = new AjaxFallbackLink<Void>( "subMenuLink") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {

				ViewFilesTabPanel viewFilesTabPanel = new ViewFilesTabPanel("content");
				viewFilesTabPanel.setOutputMarkupId(true);
				viewFilesTabPanel.setOutputMarkupPlaceholderTag(true);
				ajaxPanel.replaceWith(viewFilesTabPanel);
				ajaxPanel = viewFilesTabPanel;
				target.add(viewFilesTabPanel);
			}
		};  

		//																						AjaxFallbackLink<Void> viewFilesAjaxFallbackLink = new AjaxFallbackLink<Void>( "subMenuLink") 
		//																						{
		//																							private static final long serialVersionUID = 1L;
		//
		//																							@Override
		//																							public void onClick(AjaxRequestTarget target) 
		//																							{
		//																								log.info("systemParameterModel "+systemParameterModel);
		//																								if(systemParameterModel != null)
		//																								{
		//																									if(systemParameterModel.getActiveInd() != null && systemParameterModel.getActiveInd().equalsIgnoreCase("Y"))
		//																									{
		//																										ViewFilesTabPanel viewFilesTabPanel = new ViewFilesTabPanel("content");
		//																										viewFilesTabPanel.setOutputMarkupId(true);
		//																										viewFilesTabPanel.setOutputMarkupPlaceholderTag(true);
		//																										ajaxPanel.replaceWith(viewFilesTabPanel);
		//																										ajaxPanel = viewFilesTabPanel;
		//																										target.add(viewFilesTabPanel);
		//																									}
		//																									else
		//																									{
		//																										FeedbackPanel feedbackPanel = new FeedbackPanel("feedbackPanel");
		//																										feedbackPanel.setOutputMarkupId(true); 
		//																										
		//																										ajaxPanel.replaceWith(feedbackPanel);
		//																										ajaxPanel = feedbackPanel;
		//																										target.add(feedbackPanel);
		//																										error("Please run Start of Day in order to view this screen!");
		//																										new SimpleAttributeModifier("onclick", "Please run Start of Day in order to view this screen!");
		//																									}
		//																								}
		//																								else
		//																								{
		//																									FeedbackPanel feedbackPanel = new FeedbackPanel("feedbackPanel");
		//																									feedbackPanel.setOutputMarkupId(true); 
		//																									
		//																									ajaxPanel.replaceWith(feedbackPanel);
		//																									ajaxPanel = feedbackPanel;
		//																									target.add(feedbackPanel);
		//																									ajaxPanel.add(new SimpleAttributeModifier("onclick", "Please run Start of Day in order to view this screen!"));
		////																									new SimpleAttributeModifier("onclick", "Please run Start of Day in order to view this screen!");
		//																									error("Please run Start of Day in order to view this screen!");
		//																								}
		//																							}
		//																						};            

		AjaxFallbackLink<Void> cisBankInfoAjaxFallbackLink = new AjaxFallbackLink<Void>( "subMenuLink") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {

				ViewCisBankPanel viewCisBankPanel = new ViewCisBankPanel("content");
				viewCisBankPanel.setOutputMarkupId(true);
				viewCisBankPanel.setOutputMarkupPlaceholderTag(true);
				ajaxPanel.replaceWith(viewCisBankPanel);
				ajaxPanel = viewCisBankPanel;
				target.add(viewCisBankPanel);
			}
		};          



		AjaxFallbackLink<Void> viewsSysCtrlSlaTimeAjaxFallbackLink = new AjaxFallbackLink<Void>( "subMenuLink") 
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {

				ViewSysCtrlSlaTimePanel viewSysCtrlSlaTimePanel = new ViewSysCtrlSlaTimePanel("content");
				viewSysCtrlSlaTimePanel.setOutputMarkupId(true);
				viewSysCtrlSlaTimePanel.setOutputMarkupPlaceholderTag(true);
				ajaxPanel.replaceWith(viewSysCtrlSlaTimePanel);
				ajaxPanel = viewSysCtrlSlaTimePanel;
				target.add(viewSysCtrlSlaTimePanel);
			}
		};            

		AjaxFallbackLink<Void> cisBranchInfoAjaxFallbackLink = new AjaxFallbackLink<Void>( "subMenuLink") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {

				ViewCisBranchPanel viewCisBranchPanel = new ViewCisBranchPanel("content");
				viewCisBranchPanel.setOutputMarkupId(true);
				viewCisBranchPanel.setOutputMarkupPlaceholderTag(true);
				ajaxPanel.replaceWith(viewCisBranchPanel);
				ajaxPanel = viewCisBranchPanel;
				target.add(viewCisBranchPanel);
			}
		};          


		AjaxFallbackLink<Void> viewPHIReportsAjaxFallbackLink = new AjaxFallbackLink<Void>( "subMenuLink") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {

				ViewPHIReportsPanel viewPHIReportsPanel = new ViewPHIReportsPanel("content");
				viewPHIReportsPanel.setOutputMarkupId(true);
				viewPHIReportsPanel.setOutputMarkupPlaceholderTag(true);
				ajaxPanel.replaceWith(viewPHIReportsPanel);
				ajaxPanel = viewPHIReportsPanel;
				target.add(viewPHIReportsPanel);
			}

		}; 
		
		AjaxFallbackLink<Void> viewFileSizeLimitAjaxFallbackLink = new AjaxFallbackLink<Void>( "subMenuLink") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {

				OpsViewFileSizeLimit viewOpsFileSizeLimit = new OpsViewFileSizeLimit("content");
				viewOpsFileSizeLimit.setOutputMarkupId(true);
				viewOpsFileSizeLimit.setOutputMarkupPlaceholderTag(true);
				ajaxPanel.replaceWith(viewOpsFileSizeLimit);
				ajaxPanel = viewOpsFileSizeLimit;
				target.add(viewOpsFileSizeLimit);
			}
		};  

		AjaxFallbackLink<Void> sysViewFileSizeLimitAjaxFallbackLink = new AjaxFallbackLink<Void>( "subMenuLink") {
			private static final long serialVersionUID = 1L;

			public void onClick(AjaxRequestTarget target) {


				ViewFileSizeLimitPanel viewFileSizeLimitPanel = new ViewFileSizeLimitPanel("content");
				viewFileSizeLimitPanel.setOutputMarkupId(true);
				viewFileSizeLimitPanel.setOutputMarkupPlaceholderTag(true);
				ajaxPanel.replaceWith(viewFileSizeLimitPanel);
				ajaxPanel = viewFileSizeLimitPanel;
				target.add(viewFileSizeLimitPanel);
			}
		};  

		
		AjaxFallbackLink<Void> viewManualBillingAjaxFallbackLink = new AjaxFallbackLink<Void>( "subMenuLink") {

		private static final long serialVersionUID = 1L;

		public void onClick(AjaxRequestTarget target) {

			ManualBilling manualBillingPanel = new ManualBilling("content");
			manualBillingPanel.setOutputMarkupId(true);
			manualBillingPanel.setOutputMarkupPlaceholderTag(true);
			ajaxPanel.replaceWith(manualBillingPanel);
			ajaxPanel = manualBillingPanel;
			target.add(manualBillingPanel);
		}
	};  
		


		// _____________________________________PRIMARY MENU ITEMS__________________________________________________//

		MenuItem seperatorMenuItem = new MenuItem(true);
		MenuItem systemMaintPM = new MenuItem("System Maintenance");
		MenuItem systemCodesPM = new MenuItem("System Codes");
		MenuItem systemInfoPM = new MenuItem("System Information");
		MenuItem schedulerPM = new MenuItem("Scheduler");
		MenuItem SODEODPM = new MenuItem("SOD-EOD");
		MenuItem operationsPM = new MenuItem("Operations");
		MenuItem reportPM = new MenuItem("Reports");
		MenuItem LogOffPM = new MenuItem("Log Off");
		MenuItem uploadMandatesPanel = new MenuItem("Upload Mandate",uploadMandatesAjaxFallbackLink);
		MenuItem downloadMandatesPanel = new MenuItem("Download Mandate",downloadMandatesAjaxFallbackLink);
		MenuItem logOffMenu = new MenuItem("Home",logOffAjaxFallbackLink);
		LogOffPM.getSubMenuItemList().add(logOffMenu);

		// Report information submenus
		MenuItem ReportPanel = new MenuItem("Reports",reportsPanelAjaxFallbackLink);
		MenuItem auditProcessPanel=new MenuItem("Audit Tracking Report",auditTrackingReportAjaxFallbackLink);


		/***** Add these submenus to the Maintain Mandates Primary menu *****/
		//mandatesInfoPM.getSubMenuItemList().add(downloadMandatesPanel);
		//mandatesInfoPM.getSubMenuItemList().add(seperatorMenuItem);
		//mandatesInfoPM.getSubMenuItemList().add(viewMandatesPanel);
		//mandatesInfoPM.getSubMenuItemList().add(seperatorMenuItem);
		//mandatesInfoPM.getSubMenuItemList().add(uploadMandatesPanel);


		// _____________________________________SUB MENU ITEMS__________________________________________________//

		// System Maintenance subMenus
		MenuItem viewAccountTypePanel = new MenuItem ("Account Type",viewAccountTypePanelAjaxFallbackLink );
		MenuItem viewAmendmentCodesPanel = new MenuItem ("Amendment Codes",viewAmendmentCodesPanelAjaxFallbackLink );
		MenuItem currencyCodesPanel = new MenuItem("Currency Codes",currencyCodesFallbackLink);
		MenuItem debitValuePanel = new MenuItem("Debit Value Types",debitValueTypeAjaxFallbackLink);
		MenuItem errorCodesPanel = new MenuItem("Error Codes",errorCodeAjaxFallbacklink);
		MenuItem frequencyCodesPanel = new MenuItem("Frequency Codes",frequencyCodesAjaxFallbackLink);
		MenuItem localInstCodesPanel = new MenuItem("Local Instrument Codes",localintsrumentsCodesAjaxFallbackLink);
		MenuItem reasonCodesPanel = new MenuItem("Reason Codes",reasonCodesAjaxFallbackLink);
		MenuItem seqTypesPanel = new MenuItem("Sequence Types",sequenceTypesAjaxFallbackLink);
		MenuItem viewReportConfgScreen = new MenuItem("Report Types",reportCnfgScreenAjaxFallbackLink);
		MenuItem viewAuditTrackingScreenPanel = new MenuItem("Audit Tracking" ,viewAuditTrackingScreenPanelAjaxFallbackLink);
		MenuItem viewSeverityCodesPanel = new MenuItem("Severity Codes", viewSeverityCodesPanelAjaxFallbackLink);
		MenuItem viewProcessStatusPanel  = new MenuItem("Transaction Process Status" ,viewProcessStatusPanelAjaxFallbackLink);
		MenuItem viewSystemControlServicesPanel = new MenuItem ("Services", systemControlServiceAjaxFallbackLink );
		MenuItem viewAdjustmentCategoryPanel = new MenuItem ("Adjustment Category",viewAdjustmentCategoryPanelAjaxFallbackLink );
		MenuItem viewSysCtrlSlaTimePanel = new MenuItem ("SLA Times", viewsSysCtrlSlaTimeAjaxFallbackLink);
		MenuItem viewPHIReportsPanel = new MenuItem("Admin Reports ",viewPHIReportsAjaxFallbackLink);
		MenuItem viewFileSizeLimitPanel = new MenuItem("File Size Limit ",sysViewFileSizeLimitAjaxFallbackLink);
		MenuItem viewManaulBillingPanel = new MenuItem("Manual Billing",viewManualBillingAjaxFallbackLink);

		// Sub menu 2 - paramateres
		MenuItem sysParamsPanel = new MenuItem("System Parameters",systemParametersAjaxFallbackLink);
		MenuItem systemMonitorPanel = new MenuItem("System Monitor",sysMonitorAjaxFallbackLink);

		// Sub menus for for Operations
		MenuItem schedulerPanel = new MenuItem("Scheduler", shedularAjaxFallbackLink);
		MenuItem delDeliveryPanel = new MenuItem("View File Status", delDeliveryAjaxFallbackLink);
		MenuItem viewServerLogsPanel = new MenuItem("System Logs", serverLogsAjaxFallbackLink);
		MenuItem viewCopyFilesPanel = new MenuItem("Copy Output Files", viewCopyFilesPanelAjaxFallbackLink);
		MenuItem startofDayPanel = new MenuItem("Start of Day", startofdayFallbackLink);
		MenuItem endofDayPanel = new MenuItem("End of Day", endofdayFallbackLink);
		MenuItem cisDownloadPanel = new MenuItem("CIS Download", cisDownloadFallbackLink);
		MenuItem SchedulePanel  = new MenuItem("Scheduler", schedulePanelFallbackLink);
		MenuItem manualStartOfTransmissionPanel = new MenuItem ("Manual SOT", manualStartOfTransmissionFallbackLink );
		MenuItem fileMonitoringTabPanel = new MenuItem("Monitor File Processing",fileStatusAjaxFallbackLink);
		MenuItem viewFilestatusDescription = new MenuItem("File Processing Statuses",viewFileStatusDescriptionLink);
//		MenuItem manualEndOfTransmissionPanel = new MenuItem ("Manual EOT",manualEndOfTransmissionFallbackLink );
		MenuItem viewFileProcessingPanel = new MenuItem("System Status",SystemStatusTabPanelLink);
		//MenuItem viewFileClosurePanel = new MenuItem("System Force Closure",viewForceClosureAjaxFallBackLink);
		MenuItem viewDeleteInputFile = new MenuItem("Delete Input File",deleteInputFileFallbackLink);
		MenuItem viewDeleteEOTFile = new MenuItem("Delete EOT File",deleteEOTFileFallbackLink); 
		MenuItem viewFiles = new MenuItem("View Files",viewFilesAjaxFallbackLink);

		//Start of day and End of Day tables 
		MenuItem viewOpsServicesPanel = new MenuItem("Ops Services ",opsServicesFallbackLink);
		MenuItem viewOpsCustomerParameters = new MenuItem("Ops Customer Parameters",opsCustomerParametersFallbackLink);
		MenuItem viewAcOpsSotEot = new MenuItem("Monitor SOT/EOT",opsAcSotEotFallbackLink);
		MenuItem viewOpsRefSeq = new MenuItem("Output File Sequence Nrs",opsRefSeqFallbackLink);
		MenuItem viewOpsSlaTimes = new MenuItem("Ops SLA Times",viewopsSlaTimes);
		//MenuItem viewAcOosPublicHoliday = new MenuItem("Ops Public Holiday",opsPublicHolidayLink);
//		SalehaR-2020/07/28 - Remove CDV as per TDA
//		MenuItem cdvMenuItem = new MenuItem("Check Digit Verification ",cdvPanelAjaxFallbackLink);
		MenuItem testPanelMenuItem = new MenuItem("Test Page ",testPageAjaxFallbackLink);
		MenuItem cisBankInfoMenuItem = new MenuItem("CIS Member Information ",cisBankInfoAjaxFallbackLink);
		MenuItem cisBranchInfoMenuItem = new MenuItem("CIS Branch Information ",cisBranchInfoAjaxFallbackLink);
		MenuItem fileSizeLimitInfoMenuItem = new MenuItem("Ops File Size Limit",viewFileSizeLimitAjaxFallbackLink);
		

		//SODEOD menus arrangement 
		//SODEODPM.getSubMenuItemList().add(viewAcOosPublicHoliday);
		SODEODPM.getSubMenuItemList().add(cisBankInfoMenuItem);
		SODEODPM.getSubMenuItemList().add(seperatorMenuItem);
		SODEODPM.getSubMenuItemList().add(cisBranchInfoMenuItem);
		SODEODPM.getSubMenuItemList().add(seperatorMenuItem);
		//SODEODPM.getSubMenuItemList().add(viewAcOosPublicHoliday);
		//SODEODPM.getSubMenuItemList().add(seperatorMenuItem);
		SODEODPM.getSubMenuItemList().add(viewOpsServicesPanel);
		SODEODPM.getSubMenuItemList().add(seperatorMenuItem);
		SODEODPM.getSubMenuItemList().add(viewOpsCustomerParameters);
		SODEODPM.getSubMenuItemList().add(seperatorMenuItem);
		SODEODPM.getSubMenuItemList().add(viewAcOpsSotEot);
		SODEODPM.getSubMenuItemList().add(seperatorMenuItem);
		SODEODPM.getSubMenuItemList().add(viewOpsRefSeq);
		//SODEODPM.getSubMenuItemList().add(seperatorMenuItem);
		//SODEODPM.getSubMenuItemList().add(viewOpsCronTime);
		SODEODPM.getSubMenuItemList().add(seperatorMenuItem);
		SODEODPM.getSubMenuItemList().add(viewOpsSlaTimes);
		SODEODPM.getSubMenuItemList().add(seperatorMenuItem);
		SODEODPM.getSubMenuItemList().add(fileSizeLimitInfoMenuItem);
		

		// sub menus for system information .


		systemInfoPM.getSubMenuItemList().add(sysParamsPanel);																					
		systemInfoPM.getSubMenuItemList().add(seperatorMenuItem);
		/*	systemInfoPM.getSubMenuItemList().add(systemMonitorPanel);
																						systemInfoPM.getSubMenuItemList().add(seperatorMenuItem);*/
		systemInfoPM.getSubMenuItemList().add(viewAuditTrackingScreenPanel);
		systemInfoPM.getSubMenuItemList().add(seperatorMenuItem);


		// adding submenus to the report menu
		reportPM.getSubMenuItemList().add(auditProcessPanel);
		reportPM.getSubMenuItemList().add(seperatorMenuItem);
		reportPM.getSubMenuItemList().add(ReportPanel);
		reportPM.getSubMenuItemList().add(seperatorMenuItem);
		reportPM.getSubMenuItemList().add(testPanelMenuItem);

		// adding submenus to the operations menu.

		operationsPM.getSubMenuItemList().add(cisDownloadPanel);																			
		operationsPM.getSubMenuItemList().add(seperatorMenuItem);
		operationsPM.getSubMenuItemList().add(SchedulePanel);
		operationsPM.getSubMenuItemList().add(seperatorMenuItem);																																											
		operationsPM.getSubMenuItemList().add(viewFileProcessingPanel);
		operationsPM.getSubMenuItemList().add(seperatorMenuItem);
		operationsPM.getSubMenuItemList().add(fileMonitoringTabPanel);
		operationsPM.getSubMenuItemList().add(seperatorMenuItem);
		operationsPM.getSubMenuItemList().add(viewFiles);
		operationsPM.getSubMenuItemList().add(seperatorMenuItem);
		operationsPM.getSubMenuItemList().add(viewServerLogsPanel);
		operationsPM.getSubMenuItemList().add(seperatorMenuItem);
		operationsPM.getSubMenuItemList().add(viewCopyFilesPanel);
		/*operationsPM.getSubMenuItemList().add(seperatorMenuItem);
																						operationsPM.getSubMenuItemList().add(easterHolidayPanel);*/
		operationsPM.getSubMenuItemList().add(seperatorMenuItem);
		//operationsPM.getSubMenuItemList().add(manualEndOfTransmissionPanel);
//		operationsPM.getSubMenuItemList().add(seperatorMenuItem);
		operationsPM.getSubMenuItemList().add(manualStartOfTransmissionPanel);
		operationsPM.getSubMenuItemList().add(seperatorMenuItem);																					
		operationsPM.getSubMenuItemList().add(viewDeleteInputFile);
		operationsPM.getSubMenuItemList().add(seperatorMenuItem);
		operationsPM.getSubMenuItemList().add(viewDeleteEOTFile);


		/*operationsPM.getSubMenuItemList().add(seperatorMenuItem);
																						operationsPM.getSubMenuItemList().add(viewFileClosurePanel);
																						operationsPM.getSubMenuItemList().add(seperatorMenuItem);
																						operationsPM.getSubMenuItemList().add(crontimePanel);
																						operationsPM.getSubMenuItemList().add(testPanelMenuItem);																						
																						operationsPM.getSubMenuItemList().add(seperatorMenuItem);*/

//		SalehaR-2020/07/28 - Remove CDV as per TDA
//		systemMaintPM.getSubMenuItemList().add(cdvMenuItem);
//		systemMaintPM.getSubMenuItemList().add(seperatorMenuItem);
		systemMaintPM.getSubMenuItemList().add(viewReportConfgScreen);
		systemMaintPM.getSubMenuItemList().add(seperatorMenuItem);									
		systemMaintPM.getSubMenuItemList().add(viewSysCtrlSlaTimePanel);
		systemMaintPM.getSubMenuItemList().add(seperatorMenuItem);
		systemMaintPM.getSubMenuItemList().add(viewSystemControlServicesPanel);
		systemMaintPM.getSubMenuItemList().add(seperatorMenuItem);
		systemMaintPM.getSubMenuItemList().add(viewPHIReportsPanel);
		systemMaintPM.getSubMenuItemList().add(seperatorMenuItem);	
		systemMaintPM.getSubMenuItemList().add(viewFileSizeLimitPanel);
//		systemMaintPM.getSubMenuItemList().add(seperatorMenuItem);
//		systemMaintPM.getSubMenuItemList().add(viewManaulBillingPanel);



		//All the codes are now on the system codes primary menu.




		systemCodesPM.getSubMenuItemList().add(viewAccountTypePanel);
		systemCodesPM.getSubMenuItemList().add(seperatorMenuItem);
		systemCodesPM.getSubMenuItemList().add(viewAdjustmentCategoryPanel);
		systemCodesPM.getSubMenuItemList().add(seperatorMenuItem);
		systemCodesPM.getSubMenuItemList().add(viewAmendmentCodesPanel);
		systemCodesPM.getSubMenuItemList().add(seperatorMenuItem);
		/*systemCodesPM.getSubMenuItemList().add(currencyCodesPanel);
																						systemCodesPM.getSubMenuItemList().add(seperatorMenuItem);*/
		systemCodesPM.getSubMenuItemList().add(debitValuePanel);
		systemCodesPM.getSubMenuItemList().add(seperatorMenuItem);
		systemCodesPM.getSubMenuItemList().add(errorCodesPanel);
		systemCodesPM.getSubMenuItemList().add(seperatorMenuItem);																		
		systemCodesPM.getSubMenuItemList().add(viewFilestatusDescription);
		systemCodesPM.getSubMenuItemList().add(seperatorMenuItem);
		systemCodesPM.getSubMenuItemList().add(frequencyCodesPanel);
		systemCodesPM.getSubMenuItemList().add(seperatorMenuItem);
		systemCodesPM.getSubMenuItemList().add(localInstCodesPanel);
		systemCodesPM.getSubMenuItemList().add(seperatorMenuItem);
		systemCodesPM.getSubMenuItemList().add(reasonCodesPanel);
		systemCodesPM.getSubMenuItemList().add(seperatorMenuItem);
		systemCodesPM.getSubMenuItemList().add(seqTypesPanel);
		systemCodesPM.getSubMenuItemList().add(seperatorMenuItem);
		systemCodesPM.getSubMenuItemList().add(viewSeverityCodesPanel);
		systemCodesPM.getSubMenuItemList().add(seperatorMenuItem);
		systemCodesPM.getSubMenuItemList().add(viewProcessStatusPanel);


		// Adding submenus for system information
		systemInfoPM.getSubMenuItemList().add(systemMonitorPanel);

		// List which contains the primary menus items in it
		List<MenuItem> primaryMenuList = new ArrayList<MenuItem>();

		session = getSession();
		clientSessionModel = (ClientSessionModel) session.getAttribute("role");
		log.debug("user role ------>>>" + clientSessionModel.getUserRole());
		log.debug(controller.getProperty("MDT.IAM_ADMIN"));
		log.debug(controller.getProperty("MDT.OPS"));
		log.debug(controller.getProperty("MDT.SUSER"));
		log.debug(controller.getProperty("MDT.SVISOR"));
		log.debug(controller.getProperty("MDT.ADMIN"));

		if (clientSessionModel.getUserRole().equalsIgnoreCase(controller.getProperty("MDT.IAM_ADMIN")))
		{
			primaryMenuList.add(systemMaintPM);
			primaryMenuList.add(systemCodesPM);
			primaryMenuList.add(systemInfoPM);
			primaryMenuList.add(operationsPM);
			primaryMenuList.add(SODEODPM);
			primaryMenuList.add(reportPM);
			primaryMenuList.add(LogOffPM);
		}

		else if (clientSessionModel.getUserRole().equalsIgnoreCase(controller.getProperty("MDT.OPS")))
		{
			primaryMenuList.add(systemCodesPM);
			primaryMenuList.add(systemInfoPM);
			primaryMenuList.add(operationsPM);
			systemMaintPM.getSubMenuItemList().add(sysParamsPanel);
			primaryMenuList.add(SODEODPM);
			primaryMenuList.add(reportPM);
			primaryMenuList.add(LogOffPM);
		}
		else if (clientSessionModel.getUserRole().equalsIgnoreCase(controller.getProperty("MDT.SUSER")))
		{              
			primaryMenuList.add(reportPM);
			primaryMenuList.add(LogOffPM);
		}
		else if (clientSessionModel.getUserRole().equalsIgnoreCase(controller.getProperty("MDT.SVISOR")))
		{              
			primaryMenuList.add(systemMaintPM);
			primaryMenuList.add(systemCodesPM);
			primaryMenuList.add(systemInfoPM);
			primaryMenuList.add(operationsPM);
			primaryMenuList.add(reportPM);
			primaryMenuList.add(SODEODPM);
			primaryMenuList.add(LogOffPM);
		}
		else if (clientSessionModel.getUserRole().equalsIgnoreCase(controller.getProperty("MDT.ADMIN")))
		{
			primaryMenuList.add(systemMaintPM);
			primaryMenuList.add(systemCodesPM);
			primaryMenuList.add(systemInfoPM);
			primaryMenuList.add(operationsPM);
			primaryMenuList.add(SODEODPM);
			primaryMenuList.add(reportPM);
			primaryMenuList.add(LogOffPM);
		}

		return primaryMenuList;

	}

	public static void setContent(WebMarkupContainer object) 
	{
		log.info("WebMarkUpContainer in TemplatePage ==> "+object);
		ajaxPanel = object;
	}


}

