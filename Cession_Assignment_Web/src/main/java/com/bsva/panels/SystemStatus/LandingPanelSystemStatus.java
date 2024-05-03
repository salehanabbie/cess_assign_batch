package com.bsva.panels.SystemStatus;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.log4j.Logger;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.Session;
import org.apache.wicket.ajax.AbstractAjaxTimerBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.AjaxLazyLoadPanel;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.extensions.model.AbstractCheckBoxModel;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.time.Duration;
import org.apache.wicket.validation.validator.StringValidator;
import com.bsva.TemplatePage;
import com.bsva.commons.model.AcOpsTransCtrlMsgModel;
import com.bsva.commons.model.MandatesCountCommonsModel;
import com.bsva.commons.model.OutBalanceCountReportModel;
import com.bsva.commons.model.SysCisBankModel;
import com.bsva.commons.model.SystemControlServicesModel;
import com.bsva.commons.model.SystemParameterModel;
import com.bsva.controller.Controller;
import com.bsva.models.ClientSessionModel;
import com.bsva.models.WebMandateCountModel;
import com.bsva.models.WebReportsNamesModel;
import com.bsva.models.WebSysCisBankModel;
import com.bsva.models.WebSystemControlServicesModel;
import com.bsva.models.WebSystemParameterModel;
import com.bsva.panels.CheckBoxColumn;
import com.bsva.panels.ForceClosure.MaintainForceClosure;
import com.bsva.panels.ForceClosure.ViewForceClosure;
import com.bsva.panels.reports.OutOfBalanceReport;
import com.bsva.provider.ViewFileProcessStatusProviderOutgoing;
import com.bsva.validators.SimpleAttributeModifier;
import com.bsva.translator.WebAdminTranslator;
import com.bsva.validators.SimpleAttributeModifier;
import com.bsva.validators.TextFieldValidator;
import com.itextpdf.text.DocumentException;

public class LandingPanelSystemStatus extends Panel implements Serializable
{
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(LandingPanelSystemStatus.class);
	public static String id;
	Controller controller;
	private Form<?> form;
	private ClientSessionModel clientSessionModel;
	public static Session session;
	private String userName;
	private TextField<Integer> totalReceived;
	private TextField<Integer> totalAccepted;
	private TextField<Integer> totalRejected;
	private TextField<Integer> totalExtracted;
	private TextField<Integer> totalDifference;
	private Button eodButton;
	private Button runSystemClosure, generateReport;
	int totalReceivedMsgs, totalRejectedMsgs ,totalExtractedMsgs = 0;
	int totalDifferenceCalculate;
	private String reportNr = "MR008", reportname = "System Balance Report";
	private TextArea<String> forceClosure ;
	private int counter = 0;  
	private String inBalanceind="Y";
	private Set<WebMandateCountModel> selected = new HashSet<WebMandateCountModel>();
	WebMandateCountModel webMandateCountModel;
	List<MandatesCountCommonsModel> mandatesCountListIncomingMessages = new ArrayList<MandatesCountCommonsModel>();
	List<MandatesCountCommonsModel> mandatesCountListOutGoingMessages = new ArrayList<MandatesCountCommonsModel>();
	List<MandatesCountCommonsModel> mandatesTotalMessagesList = new ArrayList<MandatesCountCommonsModel>();
	List<OutBalanceCountReportModel> rejectedFilesCountList = new ArrayList<OutBalanceCountReportModel>();
	List<AcOpsTransCtrlMsgModel> acOpsTransCtrlMsgModelList = new ArrayList<AcOpsTransCtrlMsgModel>();
	MaintainForceClosure maintainForceClosure;
	ViewForceClosure  viewFileClosurePanel;
	private AjaxLazyLoadPanel lazy;
	SystemParameterModel systemParameterModel = new SystemParameterModel();
	WebSystemParameterModel websystemParametersModel =new WebSystemParameterModel();

	public LandingPanelSystemStatus(String id)
	{
		super(id);
		this.id = id;
		initializeComponents();
	}

	private void initializeComponents()
	{
		session = getSession();
		clientSessionModel = (ClientSessionModel) session.getAttribute("role");
		userName = clientSessionModel.getUsername(); 
		form = new Form("form");
		controller = new Controller();
		add(form);

		acOpsTransCtrlMsgModelList = (List<AcOpsTransCtrlMsgModel>) controller.retrieveNrOfRecords();
		mandatesTotalMessagesList = (List<MandatesCountCommonsModel>) controller.viewAllMessages();
		mandatesCountListIncomingMessages = (List<MandatesCountCommonsModel>) controller.viewMandatesCountIncomingMessages();
		forceClosure = new TextArea<String>("forceClosureTextField",new Model<String>());
		forceClosure.add(StringValidator.maximumLength(500));
		forceClosure.add(new TextFieldValidator());
		systemParameterModel = (SystemParameterModel) controller.retrieveWebActiveSysParameter();
		if(systemParameterModel != null)
		{
			websystemParametersModel = new WebSystemParameterModel();
			websystemParametersModel = new WebAdminTranslator().translateCommonsSystemParametersModelToWebModel(systemParameterModel);
			log.debug("websystemParametersModel: "+ websystemParametersModel);
		}
		if (mandatesCountListIncomingMessages.size() > 0) 
		{
			for (MandatesCountCommonsModel localModel : mandatesCountListIncomingMessages)
			{
				if (localModel.getNrMsgsAccepted() != null) 
				{
					totalReceivedMsgs = totalReceivedMsgs + localModel.getNrOfMsgs();
					localModel.setNrMsgsAccepted(totalReceivedMsgs);
				}
			}
		}
		totalReceived = new TextField<Integer>("totalReceived",new Model<Integer>(totalReceivedMsgs));
		totalReceived.setEnabled(false);
		mandatesCountListOutGoingMessages = (List<MandatesCountCommonsModel>) controller.viewMandatesCountOutGoingMessages();
		if (mandatesCountListOutGoingMessages.size() > 0) 
		{
			for (MandatesCountCommonsModel outgoingLocalModel : mandatesCountListOutGoingMessages) 
			{
				if (outgoingLocalModel.getNrMsgsExtracted() != null) 
				{
					totalExtractedMsgs = totalExtractedMsgs + outgoingLocalModel.getNrMsgsExtracted();
					outgoingLocalModel.setNrMsgsExtracted(totalExtractedMsgs);
				}
			}
		}
		rejectedFilesCountList = (List<OutBalanceCountReportModel>) controller.retrieveRejectedCountData();
		if (rejectedFilesCountList.size()>0) 
		{
			for (OutBalanceCountReportModel rejectedModel :rejectedFilesCountList) 
			{
				if (rejectedModel.getNrMsgsRejected()!=null)
				{
					totalRejectedMsgs =totalRejectedMsgs +rejectedModel.getNrMsgsRejected().intValue();
				}
			}
		}
		totalDifferenceCalculate = totalReceivedMsgs - (totalExtractedMsgs + totalRejectedMsgs);
		//log.info.("totalDifferenceCalculate------->" +totalDifferenceCalculate);
		totalExtracted = new TextField<Integer>("totalExtracted",new Model<Integer>(totalExtractedMsgs));
		totalExtracted.setEnabled(false);
		totalDifference = new TextField<>("totalDifference",new Model<Integer>(totalDifferenceCalculate));
		totalDifference.setEnabled(false);
		totalRejected = new   TextField<>("totalRejected",new Model<Integer>(totalRejectedMsgs));
		totalRejected.setEnabled(false);

		generateReport = new Button("generateReport") 
		{
			private static final long serialVersionUID = 1L;

			public void onSubmit() 
			{
				log.info("["+userName+" REQUESTED OUT OF BALANCE REPORT]");
				OutOfBalanceReport outOfBalanceReport = new OutOfBalanceReport();
				try 
				{
					outOfBalanceReport.generateReport(reportNr, reportname);
				} 
				catch (FileNotFoundException e) 
				{
					e.printStackTrace();
				} 
				catch (DocumentException e) 
				{
					e.printStackTrace();
				}
			}
		};
		eodButton = new Button("endOfDayButton") 
		{
			private static final long serialVersionUID = 1L;

			public void onSubmit() 
			{
				log.info("["+userName+" REQUESTED END OF DAY]");
				if (totalDifferenceCalculate == 0) 
				{
					boolean eodCheck = controller.runEndofDayManually(clientSessionModel.getUsername(), null);

					if(eodCheck)
					{
						info("End of day ran successfully!");
						log.info("<===== End of day ran successfully! =====>");
					}
					else
					{
						log.error("<===== "+controller.eodFeedbackMsg+" =====>");
						error(controller.eodFeedbackMsg);
					}
				} 
				else 
				{
					error("The system is not in balance. Please generate out of balance report.");
					log.error("<===== The system is not in balance. Please generate out of balance report. =====>");
				}
			}
		};
		if(totalDifferenceCalculate !=0)
		{
			eodButton.setEnabled(false);
		}
		else
		{
			eodButton.setEnabled(true);
		}
		form.add(eodButton);
		eodButton.add(new SimpleAttributeModifier("onclick","Are you sure you want run End  of Day?"));	

		runSystemClosure= new Button("forceClosureButton") 
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit() 
			{
				log.info("["+userName+" REQUESTED FORCE CLOSURE]");
				maintainForceClosure = new MaintainForceClosure(id);
				maintainForceClosure.setOutputMarkupId(true);
				maintainForceClosure.setOutputMarkupPlaceholderTag(true);
				MarkupContainer markupContainer = form.getParent().getParent();
				markupContainer.remove(form.getParent());
				markupContainer.add(maintainForceClosure);
				TemplatePage.setContent(maintainForceClosure);
				runSystemClosure.setEnabled(true);
			}
		};

		if(totalDifferenceCalculate==0)
		{
			runSystemClosure.setEnabled(false);
		}
		else
		{
			runSystemClosure.setEnabled(true);
		}
		runSystemClosure.setDefaultFormProcessing(false);
		runSystemClosure.add(new SimpleAttributeModifier("onclick", "Are you sure you want to force close  the system? This action cannot be undone if you select YES"));
		form.add(totalExtracted);
		form.add(totalReceived);
		form.add(totalRejected);
		form.add(generateReport);
		form.add(totalDifference);
		form.add(runSystemClosure);
		add(new FeedbackPanel("feedback"));
	}

	public void updateInBalanceInd()
	{
		try
		{
			boolean saved = false;
			WebSystemParameterModel localModel = websystemParametersModel;
			localModel.setInBalanceInd("Y");
			localModel.setSysCloseRunInd("Y");
			saved = controller.createSystemParameters(localModel);
		}
		catch (Exception e)
		{
			log.error("Error updating indicators " + e.getMessage());
		}
	}

	public void forceClosureLogic()
	{
		WebSystemParameterModel localModel = websystemParametersModel;
		localModel.setInBalanceInd("Y");
		controller.createSystemParameters(localModel);
		eodButton.setEnabled(true);
	}
}
