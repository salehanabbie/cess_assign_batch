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
import com.bsva.translator.WebAdminTranslator;
import com.bsva.validators.SimpleAttributeModifier;
import com.bsva.validators.TextFieldValidator;
import com.itextpdf.text.DocumentException;

public class OutgoingSystemStatusPanel extends Panel implements Serializable
{
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(OutgoingSystemStatusPanel.class);
	public static String id;
	private Form<?> form;
	Controller controller;
	private Set<WebMandateCountModel> selected = new HashSet<WebMandateCountModel>();
	WebMandateCountModel webMandateCountModel;
	List<MandatesCountCommonsModel> mandatesCountListIncomingMessages = new ArrayList<MandatesCountCommonsModel>();
	List<MandatesCountCommonsModel> mandatesCountListOutGoingMessages = new ArrayList<MandatesCountCommonsModel>();
	List<MandatesCountCommonsModel> mandatesTotalMessagesList = new ArrayList<MandatesCountCommonsModel>();
	List<OutBalanceCountReportModel> rejectedFilesCountList = new ArrayList<OutBalanceCountReportModel>();
	List<AcOpsTransCtrlMsgModel> acOpsTransCtrlMsgModelList = new ArrayList<AcOpsTransCtrlMsgModel>();
	IncomingSystemStatusPanel incomingSystemStatusPanel;
	OutgoingSystemStatusPanel outgoingSystemStatusPanel;
	private DefaultDataTable   outgoingDataTable;
	private AjaxLazyLoadPanel lazy;
	SystemParameterModel systemParameterModel = new SystemParameterModel();
	WebSystemParameterModel websystemParametersModel =new WebSystemParameterModel();
	private List<IColumn> columns;
	private ClientSessionModel clientSessionModel;
	public static Session session;
	private List<WebReportsNamesModel> webReportsNamesList;
	private TextField<Integer> totalReceived;
	private TextField<Integer> totalAccepted;
	private TextField<Integer> totalRejected;
	private TextField<Integer> totalExtracted;
	private TextField<Integer> totalDifference;
	private Button generateReport;
	private Button eodButton;
	private Button runSystemClosure;
	int totalReceivedMsgs, totalRejectedMsgs ,totalExtractedMsgs = 0;
	int totalDifferenceCalculate;
	private String reportNr = "MR008", reportname = "System Balance Report";
	private TextArea<String> forceClosure ;
	MaintainForceClosure maintainForceClosure;
	ViewForceClosure  viewFileClosurePanel ;
	private String   WebRequestCycle;
    private int counter = 0;  
    private String inBalanceind="Y";
    private DropDownChoice<WebSysCisBankModel>  memberNo;
	private WebSysCisBankModel webSysCisBankModel;
	private WebSystemControlServicesModel webOpsServicesModel;
	private List<WebSystemControlServicesModel> serviceIdOutList  = new ArrayList<WebSystemControlServicesModel>();
	private DropDownChoice<WebSystemControlServicesModel> serviceIdOut;
	private List<WebSysCisBankModel> instIdList = new ArrayList<WebSysCisBankModel>();
	private String action;
	private Button generateTableButton, viewAllButton;
	
	public OutgoingSystemStatusPanel(String id)
	{
		super(id);
		this.id = id;
		initializeComponents();
	}

	private void initializeComponents()
	{
		session = getSession();
		clientSessionModel = (ClientSessionModel) session.getAttribute("role");
		columns = new ArrayList<IColumn>();
		form = new Form("form");
		controller = new Controller();
		loadData();
		add(form);
		memberNo = new DropDownChoice<WebSysCisBankModel>("memberNo",new Model<WebSysCisBankModel>(), instIdList,new ChoiceRenderer<WebSysCisBankModel>());
		memberNo.setRequired(false);
		if (action == "create")
		{
			if(instIdList != null && instIdList.size() > 0)
			{
				memberNo.setDefaultModelObject(memberNo.getChoices().get(0).getMemberNo());
			}
		}
		form.add(memberNo);
		serviceIdOut = new DropDownChoice<WebSystemControlServicesModel>("serviceIdOut",new Model<WebSystemControlServicesModel>(), serviceIdOutList,new ChoiceRenderer<WebSystemControlServicesModel>());
		serviceIdOut.setRequired(false);
		if (action == "create")
		{
			if(serviceIdOutList != null && serviceIdOutList.size() > 0)
			{
				serviceIdOut.setDefaultModelObject(serviceIdOut.getChoices().get(0).getServiceIdOut());
			}
		}
		form.add(serviceIdOut);
		generateTableButton = new Button("generateTableButton")
		{
			private static final long serialVersionUID = 1L;
			
			public void onSubmit()
			{
				try
				{
					viewAllButton.setEnabled(true);
					if((memberNo.getValue() == null ||memberNo.getValue().isEmpty()) && (serviceIdOut.getValue() == null || serviceIdOut.getValue().isEmpty()))
					{
						error("Please select MemberNo and ServiceID to view transactions"); 
						form.add(createOutgoingDataTable(new ViewFileProcessStatusProviderOutgoing()));
					}
					if((memberNo.getValue() != null) && (serviceIdOut.getValue() == null || serviceIdOut.getValue().isEmpty()))
					{
						error("Please select MemberNo and ServiceID to view transactions");
					}
					else
					{
						String  memberId = memberNo.getChoices().get(Integer.parseInt(memberNo.getValue())).getMemberNo();
						String  serviceId = serviceIdOut.getChoices().get(Integer.parseInt(serviceIdOut.getValue())).getServiceIdOut();
						form.add(createOutgoingDataTable(new ViewFileProcessStatusProviderOutgoing(memberId, serviceId)));
					}
				}
				catch (Exception e)
				{
					log.error("Error onSubmit View Trasactions: " + e.getMessage());
					e.printStackTrace();
				}
			}
		};
		form.add(generateTableButton);
		viewAllButton = new Button ("viewAllButton")
		{
			private static final long SerialVersionUID = 1L;
			
			@Override
			public void onSubmit()
			{
				viewAllButton.setEnabled(false);
				form.remove(form.get("outgoingDataTableStatus"));
				form.add(createOutgoingDataTable(new ViewFileProcessStatusProviderOutgoing()));
			}
		};
		viewAllButton.setEnabled(false);
		form.add(viewAllButton);
		form.add(createOutgoingDataTable(new ViewFileProcessStatusProviderOutgoing()));
//		acOpsTransCtrlMsgModelList = (List<AcOpsTransCtrlMsgModel>) controller.retrieveNrOfRecords();
//		mandatesTotalMessagesList = (List<MandatesCountCommonsModel>) controller.viewAllMessages();
//		mandatesCountListIncomingMessages = (List<MandatesCountCommonsModel>) controller.viewMandatesCountIncomingMessages();
//		forceClosure = new TextArea<String>("forceClosureTextField",new Model<String>());
//		forceClosure.add(StringValidator.maximumLength(500));
//		forceClosure.add(new TextFieldValidator());
//		systemParameterModel = (SystemParameterModel) controller.retrieveWebActiveSysParameter();
//		if(systemParameterModel != null)
//		{
//			websystemParametersModel = new WebSystemParameterModel();
//			websystemParametersModel = new WebAdminTranslator().translateCommonsSystemParametersModelToWebModel(systemParameterModel);
//			log.debug("websystemParametersModel: "+ websystemParametersModel);
//		}
//		if (mandatesCountListIncomingMessages.size() > 0) 
//		{
//			for (MandatesCountCommonsModel localModel : mandatesCountListIncomingMessages)
//			{
//				if (localModel.getNrMsgsAccepted() != null) 
//				{
//					totalReceivedMsgs = totalReceivedMsgs + localModel.getNrOfMsgs();
//					localModel.setNrMsgsAccepted(totalReceivedMsgs);
//				}
//			}
//		}
//		totalReceived = new TextField<Integer>("totalReceived",new Model<Integer>(totalReceivedMsgs));
//		totalReceived.setEnabled(false);
//		mandatesCountListOutGoingMessages = (List<MandatesCountCommonsModel>) controller.viewMandatesCountOutGoingMessages();
//		if (mandatesCountListOutGoingMessages.size() > 0) 
//		{
//			for (MandatesCountCommonsModel outgoingLocalModel : mandatesCountListOutGoingMessages) 
//			{
//				if (outgoingLocalModel.getNrMsgsExtracted() != null) 
//				{
//					totalExtractedMsgs = totalExtractedMsgs + outgoingLocalModel.getNrMsgsExtracted();
//					outgoingLocalModel.setNrMsgsExtracted(totalExtractedMsgs);
//				}
//			}
//		}
//		rejectedFilesCountList = (List<OutBalanceCountReportModel>) controller.retrieveRejectedCountData();
//		if (rejectedFilesCountList.size()>0) 
//		{
//			for (OutBalanceCountReportModel rejectedModel :rejectedFilesCountList) 
//			{
//				if (rejectedModel.getNrMsgsRejected()!=null)
//				{
//					totalRejectedMsgs =totalRejectedMsgs +rejectedModel.getNrMsgsRejected().intValue();
//				}
//			}
//		}
//		totalDifferenceCalculate = totalReceivedMsgs - (totalExtractedMsgs + totalRejectedMsgs);
//		totalExtracted = new TextField<Integer>("totalExtracted",new Model<Integer>(totalExtractedMsgs));
//		totalExtracted.setEnabled(false);
//		totalDifference = new TextField<>("totalDifference",new Model<Integer>(totalDifferenceCalculate));
//		totalDifference.setEnabled(false);
//        totalRejected = new   TextField<>("totalRejected",new Model<Integer>(totalRejectedMsgs));
//        totalRejected.setEnabled(false);
//        generateReport = new Button("generateReport") 
//		{
//			private static final long serialVersionUID = 1L;
//
//			public void onSubmit() 
//			{
//					OutOfBalanceReport outOfBalanceReport = new OutOfBalanceReport();
//					try 
//					{
//						outOfBalanceReport.generateReport(reportNr, reportname);
//					} 
//					catch (FileNotFoundException e) 
//					{
//						e.printStackTrace();
//					} 
//					catch (DocumentException e) 
//					{
//						e.printStackTrace();
//					}
//			}
//		};
//		eodButton = new Button("endOfDayButton") 
//		{
//			private static final long serialVersionUID = 1L;
//
//			public void onSubmit() 
//			{
//				if (totalDifferenceCalculate == 0) 
//				{
//					boolean eodCheck = controller.runEndofDayManually(clientSessionModel.getUsername());
//					
//					if(eodCheck)
//					{
//						info("End  of day has ran successfully");
//					}
//					else
//					{
//						log.info("controller.eodFeedbackMsg :"+controller.eodFeedbackMsg);
//						error(controller.eodFeedbackMsg);
//					}
//				} 
//				else 
//				{
//					error("The system is not in balance , generate out of balance report.");
//				}
//			}
//		};
//		if(totalDifferenceCalculate !=0)
//		{
//			eodButton.setEnabled(false);
//		}
//		else
//		{
//			eodButton.setEnabled(true);
//		}
//		form.add(eodButton);
//		eodButton.add(new SimpleAttributeModifier("onclick","Are you sure you want run End  of Day?"));	
//		
//		runSystemClosure= new Button("forceClosureButton") 
//		{
//			private static final long serialVersionUID = 1L;
//
//			@Override
//			public void onSubmit() 
//			{
//				maintainForceClosure = new MaintainForceClosure(id);
//				maintainForceClosure.setOutputMarkupId(true);
//				maintainForceClosure.setOutputMarkupPlaceholderTag(true);
//				MarkupContainer markupContainer = form.getParent().getParent();
//				markupContainer.remove(form.getParent());
//				markupContainer.add(maintainForceClosure);
//				TemplatePage.setContent(maintainForceClosure);
//			}
//		};
//		
//		if(totalDifferenceCalculate==0)
//		{
//			runSystemClosure.setEnabled(false);
//		}
//		else
//		{
//			runSystemClosure.setEnabled(true);
//		}
//		runSystemClosure.setDefaultFormProcessing(false);
//		runSystemClosure.add(new SimpleAttributeModifier("onclick", "Are you sure you want to force close  the system? This action cannot be undone if you select YES"));
//		form.add(generateReport);
//		form.add(totalExtracted);
//		form.add(totalReceived);
//		form.add(totalRejected);
//		form.add(totalDifference);
//		form.add(runSystemClosure);
		add(new FeedbackPanel("feedback"));
	}
	private DefaultDataTable  createOutgoingDataTable(ViewFileProcessStatusProviderOutgoing viewFileProcessStatusProviderOutgoing) 
	{
		final DefaultDataTable dafaultTable = null;
		final List<IColumn> columns = new ArrayList<IColumn>();
		IColumn column = new CheckBoxColumn<WebMandateCountModel>(Model.of("Select")) 
		{

			private static final long serialVersionUID = 1L;

			@Override
			protected IModel newCheckBoxModel(final IModel<WebMandateCountModel> rowModel) 
			{
				return new AbstractCheckBoxModel() 
				{
					private static final long serialVersionUID = 1L;

					@Override
					public boolean isSelected() 
					{
						return selected.contains(rowModel.getObject());
					}

					@Override
					public void select()
					{
						if (selected.size() > 0) 
						{
							unselect();
						}
						selected.add(rowModel.getObject());
					}

					@Override
					public void unselect() 
					{
						selected.remove(rowModel.getObject());
					}

					@Override
					public void detach() 
					{
						rowModel.detach();
					}
				};
			}
		};
		columns.add(new PropertyColumn(new Model<String>("File Name"),"fileName", "fileName"));
		columns.add(new PropertyColumn(new Model<String>("To"), "instId","instId"));
		columns.add(new PropertyColumn(new Model<String>("Number of Mandates Extracted"),"nrMsgsExtracted", "nrMsgsExtracted"));
		outgoingDataTable = new DefaultDataTable ("outgoingDataTableStatus",columns, viewFileProcessStatusProviderOutgoing, 5);
			
		add(new AbstractAjaxTimerBehavior(Duration.seconds(10)) 
		 {
            private static final long serialVersionUID = 1L;

            @Override
            protected void onTimer(AjaxRequestTarget target) 
            {
           	 	ViewFileProcessStatusProviderOutgoing		viewFileProcessStatusProviderOutgoing = new ViewFileProcessStatusProviderOutgoing();
				DefaultDataTable dafaultTable = new DefaultDataTable("outgoingDataTableStatus",columns, viewFileProcessStatusProviderOutgoing, 5);
				dafaultTable.setOutputMarkupId(true);
				dafaultTable.setOutputMarkupPlaceholderTag(true);
				outgoingDataTable.replaceWith(dafaultTable);
				outgoingDataTable=dafaultTable;
				target.add(outgoingDataTable);
				 log.debug(" ****** in timer *******  " + dafaultTable);
            }
		 });
		return outgoingDataTable;		
	}
	
//	public void updateInBalanceInd()
//	{
//		try
//		{
//			boolean saved = false;
//			WebSystemParameterModel localModel = websystemParametersModel;
//			localModel.setInBalanceInd("Y");
//			localModel.setSysCloseRunInd("Y");
//			saved = controller.createSystemParameters(localModel);
//		}
//		catch (Exception e)
//		{
//			log.error("Error updating indicators " + e.getMessage());
//		}
//	}
//
//	public void forceClosureLogic()
//	{
//		WebSystemParameterModel localModel = websystemParametersModel;
//		localModel.setInBalanceInd("Y");
//		controller.createSystemParameters(localModel);
//		eodButton.setEnabled(true);
//	}
	
	private void loadData() 
	{	
		List<SysCisBankModel> instIdListFromDb = new ArrayList<SysCisBankModel>();
		instIdListFromDb = (List<SysCisBankModel>) controller.viewAllMember();
		Collections.sort(instIdListFromDb, new memberNameOrderSorter());
		if (instIdListFromDb.size() > 0) 
		{
			for (SysCisBankModel localCommonModel : instIdListFromDb) 
			{
				WebSysCisBankModel webModel = new WebSysCisBankModel();
				webModel = new WebAdminTranslator().translateCommonsSysCisBankModelToWebModel(localCommonModel);           
				instIdList.add(webModel);
			}
		}// end of if
		List<SystemControlServicesModel> serviceIdOutListFromDb = new ArrayList<SystemControlServicesModel>();
		serviceIdOutListFromDb = (List<SystemControlServicesModel>) controller.viewAllRecordId();
		Collections.sort(serviceIdOutListFromDb, new serviceIdOrderSorter());
		if (serviceIdOutListFromDb.size() > 0) 
		{
			for (SystemControlServicesModel servModel : serviceIdOutListFromDb) 
			{
				WebSystemControlServicesModel webLocalModel = new WebSystemControlServicesModel();
				webLocalModel = new WebAdminTranslator().translateCommonsSystemControlServicesModelToWebModel(servModel);
				serviceIdOutList.add(webLocalModel);
			}
		}// end of if
	}
	
		private class serviceIdOrderSorter implements Comparator<SystemControlServicesModel>
		{
			@Override
			public int compare(SystemControlServicesModel o1, SystemControlServicesModel o2)
			{
				if(o1.getServiceIdOut() == null && o2.getServiceIdOut() == null)
				{
					return 0;
				}
				else if(o1.getServiceIdOut() == null)
				{
					return -1;
				}
				else if(o2.getServiceIdOut() == null)
				{
					return 1;
				}
				return o1.getServiceIdOut().compareTo(o2.getServiceIdOut());
			}
		}

	private class memberNameOrderSorter implements Comparator<SysCisBankModel>
	{
		@Override
		public int compare(SysCisBankModel o1, SysCisBankModel o2)
		{
			if(o1.getMemberNo() == null && o2.getMemberNo() == null)
			{
				return 0;
			}
			else if(o1.getMemberNo() == null)
			{
				return -1;
			}
			else if(o2.getMemberNo() == null)
			{
				return 1;
			}
			return o1.getMemberNo().compareTo(o2.getMemberNo());
		}
	}
}
