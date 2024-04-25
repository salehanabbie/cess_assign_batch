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
import com.bsva.commons.model.SysCisBankModel;
import com.bsva.commons.model.SystemControlServicesModel;
import com.bsva.controller.Controller;
import com.bsva.models.WebOpsFileRegModel;
import com.bsva.models.WebServicesModel;
import com.bsva.models.WebSysCisBankModel;
import com.bsva.models.WebSystemControlServicesModel;
import com.bsva.panels.CheckBoxColumn;
import com.bsva.provider.ViewIncomingFilesProvider;
import com.bsva.provider.ViewOutgoingFilesProvider;
import com.bsva.translator.WebAdminTranslator;
import com.bsva.validators.TextFieldValidator;
import com.itextpdf.text.DocumentException;

/**
 * @author SalehaR
 *
 */
public class ViewOutgoingFiles extends Panel implements Serializable 
{
	private static Logger log = Logger.getLogger(ViewOutgoingFiles.class);
	private Set<WebOpsFileRegModel> selected  = new HashSet<WebOpsFileRegModel>();
	public static String id;
	Controller controller;
	private List<IColumn> columns;
	private DropDownChoice<WebSysCisBankModel>  memberNo;
	private DropDownChoice<WebSystemControlServicesModel> serviceIdOut;
	private WebSysCisBankModel webSysCisBankModel;
	private WebServicesModel webServicesModel;
	private List<WebSysCisBankModel> instIdList  = new ArrayList<WebSysCisBankModel>();
	private List<WebSystemControlServicesModel> serviceIdOutList  = new ArrayList<WebSystemControlServicesModel>();
	private Form<?> form;
	private String action;
	private DefaultDataTable outgoingFileTable;
	private ViewOutgoingFilesProvider outgoingFilesProvider;
	private Button viewFileButton,generateTableButton, searchButton,viewAllButton, generateReport;
	private TextField<String> searchText;
	private String fileDir = "OUT";
	private String reportNr = "MR017", reportname = "File Balancing Report";
	
	public ViewOutgoingFiles(String id) 
	{
		super(id);
		this.id = id;
		initializeComponents();
	}
	
	private void initializeComponents() 
	{
		columns = new  ArrayList<IColumn>();
		form = new Form("form");
		controller = new Controller();
		loadData();
		
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
		
		serviceIdOut = new DropDownChoice<WebSystemControlServicesModel>("serviceIdOut",new Model<WebSystemControlServicesModel>(), serviceIdOutList,new ChoiceRenderer<WebSystemControlServicesModel>());
		serviceIdOut.setRequired(false);
		if (action == "create")
		{
			if(serviceIdOutList != null && serviceIdOutList.size() > 0)
			{
				serviceIdOut.setDefaultModelObject(serviceIdOut.getChoices().get(0).getServiceIdOut());
			}
		}
		else if (action == "update") 
		{
			int sel = 0;
			for (int index = 0; index < serviceIdOutList.size(); index++) 
			{

				if (webServicesModel.getServiceIdOut().equalsIgnoreCase(serviceIdOutList.get(index).getServiceIdOut())) 
				{
					sel = index;
				}
			}
			serviceIdOut.setModelObject(serviceIdOut.getChoices().get(sel));

		}
		form.add(serviceIdOut);
		
		searchButton = new Button ("searchButton")
		{
			private static final long SerialVersionUID = 1L;
			
			@Override
			public void onSubmit()
			{
				log.info("Search Text: " + searchText.getValue());
				if(searchText.getValue() == null || searchText.getValue().isEmpty())
				{
					error("Search Field is not populated.");
				}
				else
				{
					form.remove(form.get("defaultTable"));
					form.add(createOutgoingFilesTable(new ViewOutgoingFilesProvider(searchText.getValue().trim().toUpperCase())));
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
				form.remove(form.get("searchText"));
				searchText = new TextField<String>("searchText", new Model<String>());
				searchText.add(StringValidator.maximumLength(37));
				searchText.add(new TextFieldValidator());
				form.add(searchText);
				form.remove(form.get("defaultTable"));
				form.add(createOutgoingFilesTable(new ViewOutgoingFilesProvider()));
			}
		};
		viewAllButton.setDefaultFormProcessing(true);
		viewAllButton.setEnabled(true);
		form.add(searchButton);
		form.add(searchText);
		form.add(viewAllButton);
		outgoingFileTable=(createOutgoingFilesTable(new ViewOutgoingFilesProvider()));
		outgoingFileTable.setOutputMarkupId(true);
		form.add(outgoingFileTable);		
		
		generateTableButton = new Button("generateTableButton") 
		{
			private static final long serialVersionUID = 1L;

			public void onSubmit() 
			{							
				try
				{
					if((memberNo.getValue() == null || memberNo.getValue().isEmpty()) && (serviceIdOut.getValue() == null || serviceIdOut.getValue().isEmpty()))
					{
						error("Please select Member Number OR Service Id to view files."); 
						form.remove(form.get("defaultTable"));
						form.add(createOutgoingFilesTable(new ViewOutgoingFilesProvider()));
					}
					else
					{	
						String selMemberId = null, selServiceId = null;	
						if((memberNo.getValue() != null && !memberNo.getValue().isEmpty()))
						{
							selMemberId = memberNo.getChoices().get(Integer.parseInt(memberNo.getValue())).getMemberNo();
						}
						if((serviceIdOut.getValue() != null && !serviceIdOut.getValue().isEmpty()))
						{
							selServiceId = serviceIdOut.getChoices().get(Integer.parseInt(serviceIdOut.getValue())).getServiceIdOut();
						}	
						form.remove(form.get("defaultTable"));
						form.add(createOutgoingFilesTable(new ViewOutgoingFilesProvider(selMemberId, selServiceId)));
					}
				}
				catch (Exception e) 
				{
					log.error("Error occurred on ViewOutgoingFiles: " + e.getMessage());
					e.printStackTrace();
					error("Error occurred on ViewOutgoingFiles: " + e.getMessage());
				}
			}
		};
		form.add(generateTableButton);
		
//		generateReport = new Button("generateReport") 
//		{
//			private static final long serialVersionUID = 1L;
//
//			public void onSubmit() 
//			{
//				FileBalancingReport balancingReport = new FileBalancingReport();
//					try 
//					{
//						balancingReport.generateReport(reportNr, reportname);
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
//			
//		};
//		form.add(generateReport);
		add(form);
		add(new FeedbackPanel("feedback"));
	}
	
	private DefaultDataTable createOutgoingFilesTable(ViewOutgoingFilesProvider viewOutgoingFilesProvider)
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
		defaultTable = new DefaultDataTable("defaultTable", columns, viewOutgoingFilesProvider, 20);
		return defaultTable;
	}
	
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
		}
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
		}
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
