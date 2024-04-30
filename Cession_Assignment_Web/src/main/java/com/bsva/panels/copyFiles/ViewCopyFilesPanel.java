package com.bsva.panels.copyFiles;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.log4j.Logger;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.Session;
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

import com.bsva.commons.model.AuditTrackingModel;
import com.bsva.commons.model.ServicesModel;
import com.bsva.commons.model.SysCisBankModel;
import com.bsva.controller.Controller;
import com.bsva.models.ClientSessionModel;
import com.bsva.models.WebAuditTrackingModel;
import com.bsva.models.WebCopyFilesModel;
import com.bsva.models.WebServicesModel;
import com.bsva.models.WebSysCisBankModel;
import com.bsva.panels.CheckBoxColumn;
import com.bsva.provider.CopyFilesProvider;
import com.bsva.translator.WebAdminTranslator;
import com.bsva.validators.TextFieldValidator;

/**
 * @author SalehaR - 2018-06-14
 *
 */
public class ViewCopyFilesPanel  extends Panel implements Serializable
{
	public static Logger log = Logger.getLogger(ViewCopyFilesPanel.class);

	Controller controller;
	private Form form;

	private List<IColumn> columns;
	private DefaultDataTable outgoingFileTable;
	private Set<WebCopyFilesModel> selected = new HashSet<WebCopyFilesModel>();
	private List<WebSysCisBankModel> instIdList  = new ArrayList<WebSysCisBankModel>();
	private List<WebServicesModel> serviceIdList = new ArrayList<WebServicesModel>();

	List<WebCopyFilesModel> webCopyFilesModelList;

	private DropDownChoice<WebSysCisBankModel>  memberNo;
	private DropDownChoice<WebServicesModel> serviceIdOut;

	private TextField<String> searchText;
	private Button searchButton_1, searchButton_2, viewAllButton, copyButton, cancelButton;

	public static String id;
	private String name;
	private String action;
	private String screenName;
	private ClientSessionModel clientSessionModel;
	private String userName;
	public static Session session;

	public ViewCopyFilesPanel(String id)
	{
		super(id);
		this.id = id;
		
		controller = new Controller();
		initializeComponents();
	}

	private void initializeComponents()
	{
		loadData();

		form = new Form("viewCopyFilesPanel");
		columns = new ArrayList<IColumn>();

		memberNo = new DropDownChoice<WebSysCisBankModel>("memberNo",new Model<WebSysCisBankModel>(), instIdList,new ChoiceRenderer<WebSysCisBankModel>());
		memberNo.setRequired(false);
		if(instIdList != null && instIdList.size() > 0)
		{
			memberNo.setDefaultModelObject(memberNo.getChoices().get(0).getMemberNo());
		}
		form.add(memberNo);

		
		log.info("serviceIdList.size ==> "+serviceIdList.size());
		serviceIdOut = new DropDownChoice<WebServicesModel>("serviceIdOut",new Model<WebServicesModel>(), serviceIdList,new ChoiceRenderer<WebServicesModel>());
		serviceIdOut.setRequired(false);
		if(serviceIdList  != null && serviceIdList.size() > 0)
		{
			serviceIdOut.setDefaultModelObject(serviceIdOut.getChoices().get(0).getServiceIdOut());
		}
		form.add(serviceIdOut);

		searchButton_1 = new Button ("searchButton_1")
		{
			private static final long SerialVersionUID = 1L;
			@Override

			public void onSubmit()
			{
				//				log.info("Search Text: " + searchText.getValue());
				//				if(searchText.getValue() == null || searchText.getValue().isEmpty())
				//				{
				String selMemberId = null, selServiceId = null;

				if((memberNo.getValue() != null && !memberNo.getValue().isEmpty()))
				{
					selMemberId = memberNo.getChoices().get(Integer.parseInt(memberNo.getValue())).getMemberNo();
				}

				if((serviceIdOut.getValue() != null && !serviceIdOut.getValue().isEmpty()))
				{
					selServiceId = serviceIdOut.getChoices().get(Integer.parseInt(serviceIdOut.getValue())).getServiceIdOut();
				}

				form.remove(form.get("dataTable"));
				form.add(createDataTable(new CopyFilesProvider(selMemberId, selServiceId)));
				//				}
				//				else
				//				{
				//					log.info("In the search button....");
				//					form.remove(form.get("dataTable"));
				//					form.add(createDataTable(new CopyFilesProvider(searchText.getValue().trim())));
				//				}
			}
		};

		searchText = new TextField<String>("searchText", new Model<String>());
		searchText.add(StringValidator.maximumLength(37));
		searchText.add(new TextFieldValidator());

		searchButton_2 = new Button ("searchButton_2")
		{
			private static final long SerialVersionUID = 1L;
			@Override

			public void onSubmit()
			{
				log.info("Search Text: " + searchText.getValue());

				log.info("In the search button....");
				form.remove(form.get("dataTable"));
				form.add(createDataTable(new CopyFilesProvider(searchText.getValue().trim())));
			}
		};

		viewAllButton = new Button ("viewAllButton")
		{
			private static final long SerialVersionUID = 1L;

			@Override
			public void onSubmit()
			{
				form.remove(form.get("searchText"));
				searchText = new TextField<String>("searchText", new Model<String>());
				searchText.add(StringValidator.maximumLength(37));
				searchText.add(new TextFieldValidator());
				form.add(searchText);	

				form.remove(form.get("dataTable"));
				form.add(createDataTable(new CopyFilesProvider()));
			}
		};
		viewAllButton.setDefaultFormProcessing(true);

		form.add(createDataTable(new CopyFilesProvider()));

		copyButton = new Button("copyButton")
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit()
			{
				boolean fileCopied = false;

				if(selected.size() > 1)
				{
					error("Please select ONE file...");
				}
				else if (selected.size() <= 0)
				{
					error("No file was selected...");
				}
				else
				{
					log.info("SELECTED RECORD-----> "+selected);

					try
					{
						for (WebCopyFilesModel copyFilesModel : selected) 
						{
							copyFile(copyFilesModel.getOutputFileName());
							
							
							
						}
					}
					catch(IOException ioe)
					{
						log.error("Could not copy file...."+ioe.getMessage());
						ioe.printStackTrace();
					}
				}
			}
		};
		copyButton.setDefaultFormProcessing(false);

		cancelButton = new Button("cancelButton")
		{
			@Override
			public  void onSubmit()
			{
				MarkupContainer markupContainer = form.getParent().getParent();
				markupContainer.remove(form.getParent());
				markupContainer.add(new ViewCopyFilesPanel(ViewCopyFilesPanel.id));
			}
		};

		form.add(searchButton_1);
		form.add(searchButton_2);
		form.add(searchText);
		form.add(viewAllButton);
		form.add(copyButton);
		form.add(cancelButton);

		add(form);
		add(new FeedbackPanel("feedbackPanel"));
	}

	private DefaultDataTable createDataTable(CopyFilesProvider copyFilesProvider)
	{
		DefaultDataTable dataTable;
		List<IColumn> columns = new ArrayList<IColumn>();
		IColumn column = new CheckBoxColumn<WebCopyFilesModel>(Model.of("Select"))
		{
			private static final long serialVersionUID = 1L;
			protected IModel newCheckBoxModel(final IModel<WebCopyFilesModel> rowModel)
			{
				return new AbstractCheckBoxModel() 
				{
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
		columns.add(column);
		columns.add(new PropertyColumn(new Model<String>("Output File Name"),"outputFileName", "outputFileName"));
		columns.add(new PropertyColumn(new Model<String>("File Timestamp"), "timestamp", "timestamp"));
		columns.add(new PropertyColumn<>(new Model<String>("File Type"), "fileType", "fileType"));
		dataTable = new DefaultDataTable("dataTable", columns,	copyFilesProvider, 10);
		return dataTable;
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
		serviceIdList = new ArrayList<WebServicesModel>();
		serviceIdOutListFromDb = (List<ServicesModel>) controller.findServicesByNamedQuery("MdtOpsServicesEntity.findByServiceIdOutAscend", "serviceIdOut", "IGNORE");				
		log.info("serviceIdOutListFromDb ==> "+serviceIdOutListFromDb);

		Collections.sort(serviceIdOutListFromDb, new serviceIdOrderSorter());

		if (serviceIdOutListFromDb != null && serviceIdOutListFromDb.size() > 0) 
		{
			for (ServicesModel servModel : serviceIdOutListFromDb) 
			{
				WebServicesModel webLocalModel = new WebServicesModel();
				webLocalModel = new WebAdminTranslator().translateCommonsServicesModelToWebModel(servModel);
				serviceIdList.add(webLocalModel);	
			}
		}// end of if	

		if(instIdList != null && instIdList.size() > 0)
			memPop = true;

		if(serviceIdList != null && serviceIdList.size() > 0)
		{
			for (WebServicesModel localModel : serviceIdList) {
				log.info("OUTSERVICE ==> "+localModel.getServiceIdOut());
			}
			servPop = true;
		}
			
		if(memPop && servPop)
			return true;
		else
			return false;
	}

	public  void copyFile(String fileName) throws IOException 
	{
		boolean fileCopied = false;

		File tmpFile = new File("/home/opsjava/Delivery/Mandates/Output/" + fileName);
		String outputFile = "/home/opsjava/Delivery/Mandates/Output/temp/" + fileName;
		FileOutputStream fos = new FileOutputStream(tmpFile);
		Path source = Paths.get(outputFile);
		Files.copy(source, fos);
		log.info("Copying "+fileName+" from temp to output directory...");
		info(fileName + " copied to Output directory!");
		
		session = getSession();
		clientSessionModel = (ClientSessionModel) session.getAttribute("role");
		userName = clientSessionModel.getUsername();
		
		WebAuditTrackingModel webAuditTrackingModel = new WebAuditTrackingModel();
		webAuditTrackingModel.setRecordId(new BigDecimal(123));
		webAuditTrackingModel.setUserId(userName);
		webAuditTrackingModel.setAction("COPY");
		webAuditTrackingModel.setTableName("Copy Output Files");
		webAuditTrackingModel.setOldValue(fileName);
		webAuditTrackingModel.setNewValue("File re-copied to Output directory");
		webAuditTrackingModel.setActionDate(new Date());
		
	
		AuditTrackingModel	 auditTrackingModel = new WebAdminTranslator().translateWebAuditTrackingModelToCommonsModel(webAuditTrackingModel);
		controller.createAuditTracking(auditTrackingModel);

		form.remove(form.get("searchText"));
		searchText = new TextField<String>("searchText", new Model<String>());
		searchText.add(StringValidator.maximumLength(37));
		searchText.add(new TextFieldValidator());
		form.add(searchText);	

		form.remove(form.get("dataTable"));
		form.add(createDataTable(new CopyFilesProvider()));
		selected.clear();
	}



	// Sort ServiceId in ASCENDING order
	private class serviceIdOrderSorter implements Comparator<ServicesModel>
	{

		@Override
		public int compare(ServicesModel o1, ServicesModel o2) {

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
