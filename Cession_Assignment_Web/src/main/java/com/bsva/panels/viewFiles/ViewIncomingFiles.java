package com.bsva.panels.viewFiles;

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
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.extensions.model.AbstractCheckBoxModel;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.validation.validator.StringValidator;

import com.bsva.commons.model.ServicesModel;
import com.bsva.commons.model.SysCisBankModel;
import com.bsva.controller.Controller;
import com.bsva.models.WebOpsFileRegModel;
import com.bsva.models.WebServicesModel;
import com.bsva.models.WebSysCisBankModel;
import com.bsva.panels.CheckBoxColumn;
import com.bsva.panels.reports.OutOfBalanceReport;
import com.bsva.provider.AccountTypeProvider;
import com.bsva.provider.ViewIncomingFilesProvider;
import com.bsva.translator.WebAdminTranslator;
import com.bsva.validators.TextFieldValidator;
import com.itextpdf.text.DocumentException;

/**
 * @author SalehaR
 *
 */
public class ViewIncomingFiles extends Panel implements Serializable 
{

	private static Logger log = Logger.getLogger(ViewIncomingFiles.class);

	private Set<WebOpsFileRegModel> selected  = new HashSet<WebOpsFileRegModel>();

	public static String id;
	Controller controller;

	private List<IColumn> columns;
	private DropDownChoice<WebSysCisBankModel>  memberNo;
	private DropDownChoice<WebServicesModel> serviceIdIn;
	private WebSysCisBankModel webSysCisBankModel;
	private WebServicesModel webServicesModel;
	private List<WebSysCisBankModel> instIdList  = new ArrayList<WebSysCisBankModel>();
	private List<WebServicesModel> serviceIdInList = new ArrayList<WebServicesModel>();
	private String action;
	private Form<?> form;
	private DefaultDataTable incomingFileTable, outgoingFileTable;
	private ViewIncomingFilesProvider incomingFilesProvider;
	ViewFilesTabPanel viewFilesTabPanel;
	private String fileDir = "INC";
	private Button generateReport, generateMarkoffFileBalance;
	private Button viewFileButton,generateTableButton, searchButton,viewAllButton;
	private TextField<String> searchText;
	private String reportNr = "MR017", reportname = "File Balancing Report";
	private String markOffReportNr = "MR026", markOffReportName = "Mark Off Services File Balancing Report";
	boolean dataLoadedCheck = false;

	public ViewIncomingFiles(String id) 
	{
		super(id);
		this.id = id;
		initializeComponents();

		//		controller = new Controller();
		//		dataLoadedCheck = loadData();
		//		
		//		if(dataLoadedCheck == true)
		//		{
		//			initializeComponents();
		//		}

	}

	private void initializeComponents() 
	{
		columns = new  ArrayList<IColumn>();

		form = new Form("form");

		controller = new Controller();
		loadData();
		//		
		//		dataLoadedCheck = 

		//		if(dataLoadedCheck == true)
		//		{


		memberNo = new DropDownChoice<WebSysCisBankModel>("memberNo",new Model<WebSysCisBankModel>(), instIdList,new ChoiceRenderer<WebSysCisBankModel>());
		memberNo.setRequired(false);
		if (action == "create")
		{
			if(instIdList != null && instIdList.size() > 0)
			{
				memberNo.setDefaultModelObject(memberNo.getChoices().get(0).getMemberNo());
			}
		}
		else if (action == "update")
		{
			int sel = 0;
			for (int index = 0; index < instIdList.size(); index++)
			{

				if (webSysCisBankModel.getMemberNo().equalsIgnoreCase(instIdList.get(index).getMemberNo()))
				{
					sel = index;
				}
			}
			memberNo.setModelObject(memberNo.getChoices().get(sel));
		}
		form.add(memberNo);

		serviceIdIn = new DropDownChoice<WebServicesModel>("serviceIdIn",new Model<WebServicesModel>(), serviceIdInList,new ChoiceRenderer<WebServicesModel>());
		serviceIdIn.setRequired(false);
		if (action == "create")
		{
			if(serviceIdInList != null && serviceIdInList.size() > 0)
			{
				serviceIdIn.setDefaultModelObject(serviceIdIn.getChoices().get(0).getServiceIdIn());
			}
		}
		else if (action == "update") 
		{
			int sel = 0;
			for (int index = 0; index < serviceIdInList.size(); index++) 
			{
				if (webServicesModel.getServiceIdIn().equalsIgnoreCase(serviceIdInList.get(index).getServiceIdIn())) 
				{
					sel = index;
				}
			}
			serviceIdIn.setModelObject(serviceIdIn.getChoices().get(sel));
		}
		form.add(serviceIdIn);
		add(form);

		searchButton = new Button ("searchButton")
		{
			private static final long SerialVersionUID = 1L;
			@Override

			public void onSubmit()
			{
				//					viewAllButton.setEnabled(true);

				log.info("Search Text: " + searchText.getValue());
				if(searchText.getValue() == null || searchText.getValue().isEmpty())
				{
					error("Search Field is not populated.");
				}
				else
				{
					form.remove(form.get("defaultTable"));
					form.add(createIncomingFilesTable(new ViewIncomingFilesProvider(searchText.getValue().trim().toUpperCase())));
				}
			}
		};

		searchText = new TextField<String>("searchText", new Model<String>());
		searchText.add(StringValidator.maximumLength(37));
		searchText.add(new TextFieldValidator());

		viewAllButton = new Button ("viewAllButton")
		{
			private static final long SerialVersionUID = 1L;

			@Override
			public void onSubmit()
			{
				//					viewAllButton.setEnabled(false);

				form.remove(form.get("searchText"));
				searchText = new TextField<String>("searchText", new Model<String>());
				searchText.add(StringValidator.maximumLength(37));
				searchText.add(new TextFieldValidator());
				form.add(searchText);

				form.remove(form.get("defaultTable"));
				form.add(createIncomingFilesTable(new ViewIncomingFilesProvider()));
			}
		};
		viewAllButton.setDefaultFormProcessing(true);

		generateReport = new Button("generateReport") 
		{
			private static final long serialVersionUID = 1L;

			public void onSubmit() 
			{

				FileBalancingReport balancingReport = new FileBalancingReport();
				try 
				{
					balancingReport.generateReport(reportNr, reportname);
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


		form.add(searchButton);
		form.add(searchText);
		form.add(viewAllButton);
		form.add(generateReport);
		//			viewAllButton.setEnabled(false);

		incomingFileTable=(createIncomingFilesTable(new ViewIncomingFilesProvider()));
		incomingFileTable.setOutputMarkupId(true);
		form.add(incomingFileTable);		

		generateTableButton = new Button("generateTableButton") 
		{
			private static final long serialVersionUID = 1L;

			public void onSubmit() 
			{							

				try
				{
					if((memberNo.getValue() == null || memberNo.getValue().isEmpty()) && (serviceIdIn.getValue() == null || serviceIdIn.getValue().isEmpty()))
					{
						error("Please select Member Number OR Service Id to view files."); 
						form.remove(form.get("defaultTable"));
						form.add(createIncomingFilesTable(new ViewIncomingFilesProvider()));
					}
					else
					{

						String selMemberId = null, selServiceId = null;

						if((memberNo.getValue() != null && !memberNo.getValue().isEmpty()))
						{
							selMemberId = memberNo.getChoices().get(Integer.parseInt(memberNo.getValue())).getMemberNo();
						}

						if((serviceIdIn.getValue() != null && !serviceIdIn.getValue().isEmpty()))
						{
							selServiceId = serviceIdIn.getChoices().get(Integer.parseInt(serviceIdIn.getValue())).getServiceIdIn();
						}

						form.remove(form.get("defaultTable"));
						form.add(createIncomingFilesTable(new ViewIncomingFilesProvider(selMemberId, selServiceId)));
					}
				}
				catch (Exception e) 
				{
					log.error("Error occurred on ViewIncomingFiles: " + e.getMessage());
					e.printStackTrace();
					error("Error occurred on ViewIncomingFiles: " + e.getMessage());
				}
			}
		};
		form.add(generateTableButton);

		//2017-06-29 SalehaR - Removed as per DEMO - not allowed to show file data contains Debtor Info			
		//			viewFileButton = new Button("viewFileButton")
		//			{
		//				@Override
		//				public  void onSubmit()
		//				{
		//					if (selected.size() > 1) 
		//					{
		//						log.error("Please select only one record...");
		//					} 
		//					else if (selected.size() <= 0) 
		//					{
		//						log.error("No record was selected...");
		//					} 
		//					else 
		//					{
		//						for (WebOpsFileRegModel webOpsFileRegModel : selected) 
		//						{
		//							MarkupContainer markupContainer = form.getParent().getParent();
		//							markupContainer.remove(form.getParent());
		//							markupContainer.add(new ViewFile(id, webOpsFileRegModel.getFileName(), fileDir));
		//		
		//							break;
		//					}
		//				}
		//
		//			}
		//		};
		//		viewFileButton.setDefaultFormProcessing(false);
		//
		//			form.add(viewFileButton);

		add(form);
		add(new FeedbackPanel("feedback"));

		//		}//end of dataLoadedCheck
		//		else
		//		{
		//			error("Please run CIS Download and Start Of Day to enable screen!");
		//			log.error("Please run CIS Download and Start Of Day to enable screen!");
		//		}
	}

	private DefaultDataTable createIncomingFilesTable(ViewIncomingFilesProvider viewIncomingFilesProvider)
	{
		DefaultDataTable defaultTable;
		final List<IColumn> columns = new ArrayList<IColumn>();
		IColumn column = new CheckBoxColumn<WebOpsFileRegModel>(Model.of("Select")) 
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected IModel newCheckBoxModel(final IModel<WebOpsFileRegModel> rowModel) 
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
						if(selected.size() > 0)
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
		columns.add(new PropertyColumn(new Model<String>("FileName"),"fileName", "fileName"));
		columns.add(new PropertyColumn(new Model<String>("Status"),"status","status"));
		defaultTable = new DefaultDataTable("defaultTable", columns, viewIncomingFilesProvider, 20);
		return defaultTable;
	}

	private boolean loadData() 
	{		
		boolean memPop = false, servPop = false;

		// Generate InstId for drop down box 
		List<SysCisBankModel> instIdListFromDb = new ArrayList<SysCisBankModel>();

		instIdListFromDb = (List<SysCisBankModel>) controller.viewAllMember();
		Collections.sort(instIdListFromDb, new memberNoOrderSorter());

		if (instIdListFromDb.size() > 0) 
		{

			for (SysCisBankModel localCommonModel : instIdListFromDb) 
			{
				WebSysCisBankModel webModel = new WebSysCisBankModel();
				webModel = new WebAdminTranslator().translateCommonsSysCisBankModelToWebModel(localCommonModel);
				//controller.viewAllMember();           
				instIdList.add(webModel);
			}
		}// end of if

		/* Generate SeveritryCodes for drop down box */
		List<ServicesModel> serviceIdOutListFromDb = new ArrayList<ServicesModel>();
		serviceIdInList = new ArrayList<WebServicesModel>();
		serviceIdOutListFromDb = (List<ServicesModel>) controller.viewAllServices();

		Collections.sort(serviceIdOutListFromDb, new serviceIdOrderSorter());

		if (serviceIdOutListFromDb.size() > 0) 
		{
			for (ServicesModel servModel : serviceIdOutListFromDb) 
			{
				WebServicesModel webLocalModel = new WebServicesModel();
				webLocalModel = new WebAdminTranslator().translateCommonsServicesModelToWebModel(servModel);
				if(servModel.getServiceIdIn() == null){
					serviceIdInList.remove(webLocalModel);
				}
				else
					serviceIdInList.add(webLocalModel);	
			}


		}// end of if	

		if(instIdList != null && instIdList.size() > 0)
			memPop = true;

		if(serviceIdInList != null && serviceIdInList.size() > 0)
			servPop = true;

		if(memPop && servPop)
			return true;
		else
			return false;
	}

	// Sort ServiceId in ASCENDING order
	private class serviceIdOrderSorter implements Comparator<ServicesModel>
	{

		@Override
		public int compare(ServicesModel o1, ServicesModel o2) {

			if(o1.getServiceIdIn() == null && o2.getServiceIdIn() == null)
			{
				return 0;
			}
			else if(o1.getServiceIdIn() == null)
			{
				return -1;
			}
			else if(o2.getServiceIdIn() == null)
			{
				return 1;
			}
			return o1.getServiceIdIn().compareTo(o2.getServiceIdIn());
		}

	}

	// Sort MemberNO in ASCENDING order
	private class memberNoOrderSorter implements Comparator<SysCisBankModel>
	{

		@Override
		public int compare(SysCisBankModel o1, SysCisBankModel o2) {
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


