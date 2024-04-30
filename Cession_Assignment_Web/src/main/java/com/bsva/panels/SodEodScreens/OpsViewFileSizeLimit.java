package com.bsva.panels.SodEodScreens;

import java.io.Serializable;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
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

import com.bsva.commons.model.OpsProcessControlModel;
import com.bsva.commons.model.OpsSlaTimesCommonsModel;
import com.bsva.commons.model.ServicesModel;
import com.bsva.commons.model.SysCisBankModel;
import com.bsva.controller.Controller;
import com.bsva.models.WebFileSizeLimitModel;
import com.bsva.models.WebFileStatusModel;
import com.bsva.models.WebOpsRefSequenceNumber;
import com.bsva.models.WebOpsSlaTimesModel;
import com.bsva.models.WebServicesModel;
import com.bsva.models.WebSysCisBankModel;
import com.bsva.panels.CheckBoxColumn;
import com.bsva.provider.FileSizeLimitProvider;
import com.bsva.provider.IncomingFileMonitoringProvider;
import com.bsva.provider.OpsRefSeqNumberProvider;
import com.bsva.translator.WebAdminTranslator;


/***
 * 
 * @author DimakatsoN
 *
 */

public class OpsViewFileSizeLimit extends Panel implements Serializable  {
	
	public static Logger log = Logger.getLogger(OpsViewFileSizeLimit.class);
	public static String id;
	private Form form;
	private Set<WebFileSizeLimitModel> selected = new HashSet<WebFileSizeLimitModel>();
	private Controller controller;
	private Button searchButton;
	private Button viewAllButton;
	private TextField<String>  cisDate;
	private DropDownChoice<WebOpsSlaTimesModel> serviceId;
	private DropDownChoice<WebSysCisBankModel> instId;
	private OpsProcessControlModel processControlModel;
	private String action;
	private WebSysCisBankModel webSysCisBankModel;
	private WebOpsSlaTimesModel webOpsSlaTimesModel;
	private List<WebSysCisBankModel> instIdList = new ArrayList<WebSysCisBankModel>();
	private List<WebOpsSlaTimesModel> serviceList = new ArrayList<WebOpsSlaTimesModel>();
	String cis, sod, eod;
	
	public OpsViewFileSizeLimit(String id) 
	{
		super(id);
		this.id = id;
		initializeComponents ();
	}
	
	
/*	public ViewFileSizeLimit(String id,WebSysCisBankModel webSysCisBankModel,WebServicesModel webServicesModel) {
		super(id);
		this.action = action;
		this.id = id;
		this.webServicesModel = webServicesModel;
		this.webSysCisBankModel = webSysCisBankModel;
		initializeComponents ();
	}*/
	public void initializeComponents ()
	{
		controller = new Controller();
		loadData();
		form = new Form("form");
		form.add(createDataTable(new FileSizeLimitProvider()));

		cisDate = new TextField<String>("cisDate", new Model<String>());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(processControlModel != null && processControlModel.getProcessDate() != null)
		{
			String cisDwnDate = sdf.format(processControlModel.getProcessDate());
			cisDate.setDefaultModelObject(cisDwnDate);
		}
		cisDate.setEnabled(false);
		form.add(cisDate);
		
		instId = new DropDownChoice<WebSysCisBankModel>("instId",new Model<WebSysCisBankModel>(), instIdList,new ChoiceRenderer<WebSysCisBankModel>());
		instId.setRequired(true);
		if (action == "create")
		{
			if(instIdList != null && instIdList.size() > 0)
			{
				instId.setDefaultModelObject(instId.getChoices().get(0).getMemberNo());
			}
		}

		form.add(instId);
		
		serviceId = new DropDownChoice<WebOpsSlaTimesModel>("serviceId", new Model<WebOpsSlaTimesModel>(), serviceList, new ChoiceRenderer<WebOpsSlaTimesModel>());
		serviceId.setRequired(true);
		if (action == "create")
		{
			if(serviceList != null && serviceList.size() > 0)
			{
				serviceId.setDefaultModelObject(serviceId.getChoices().get(0).getService());
			}
		}
		form.add(serviceId);
		
		
		searchButton = new Button("searchButton")
		{
			private static final long serialVersionUID = 1L;
			
			public void onSubmit()
			{
				try
				{
					if((instId.getValue()== null ||instId.getValue().isEmpty()) && (serviceId.getValue() == null || serviceId.getValue().isEmpty()))
					{
						error("Please select InstId and SubService to view transactions"); 
						form.add(createDataTable(new FileSizeLimitProvider()));
					}
					if((instId.getValue() != null) && (serviceId.getValue() == null || serviceId.getValue().isEmpty()))
					{
						error("Please select InstId and SubService to view transactions");
					}
					else
					{
						String  memberId = instId.getChoices().get(Integer.parseInt(instId.getValue())).getMemberNo();
						String  subService = serviceId.getChoices().get(Integer.parseInt(serviceId.getValue())).getService();
						form.remove(form.get("dataTable"));
						form.add(createDataTable(new FileSizeLimitProvider(memberId, subService)));
					}
				}
				catch (Exception e)
				{
					log.error("Error onSubmit File Size Limit: " + e.getMessage());
					e.printStackTrace();
				}
			}
		};
		form.add(searchButton);
		
		viewAllButton = new Button ("viewAllButton")
		{
			private static final long SerialVersionUID = 1L;
			
			@Override
			public void onSubmit()
			{
				viewAllButton.setEnabled(false);
				form.remove(form.get("dafaultTable"));
				form.add(createDataTable(new FileSizeLimitProvider()));
			}
		};

		form.add(viewAllButton);
		add(form);
		 add(new FeedbackPanel("feedbackPanel"));


	}
	private DefaultDataTable createDataTable(FileSizeLimitProvider fileSizeLimitProvider) 
	{
		DefaultDataTable dataTable;

		List<IColumn> columns = new ArrayList<IColumn>();
		IColumn column = new CheckBoxColumn<WebFileSizeLimitModel>(Model.of("Select"))
				{
			@Override
			protected IModel newCheckBoxModel(final IModel<WebFileSizeLimitModel> rowModel)
			{
				return new AbstractCheckBoxModel() {

					@Override
					public boolean isSelected() {
						return selected.contains(rowModel.getObject());
					}

					@Override 
					public void select() {
						if (selected.size() > 0) {
							unselect();
						}
						selected.add(rowModel.getObject());
					}

					@Override
					public void unselect() {
						selected.remove(rowModel.getObject());
					}

					@Override
					public void detach() {
						rowModel.detach();
					}
				};
			}
		};

		//columns.add(column);
		columns.add(new PropertyColumn(new Model<String>("MEMBER ID"), "memberId","memberId"));
		columns.add(new PropertyColumn(new Model<String>("SUB SERVICE"),"subService","subService"));
		columns.add(new PropertyColumn(new Model<String>("LIMIT"),"limit", "limit"));
		columns.add(new PropertyColumn(new Model<String>("DIRECTION"),"direction", "direction"));
		columns.add(new PropertyColumn(new Model<String>("ACTIVE ID"),"activeId", "activeId"));
		columns.add(new PropertyColumn(new Model<String>("PRPCESS DATE"),"processDate", "processDate"));
		
		dataTable = new DefaultDataTable("dataTable", columns,fileSizeLimitProvider, 10);

		return dataTable;
		
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
				//controller.viewAllMember();           
				instIdList.add(webModel);
			}
		}// end of if

		List<OpsSlaTimesCommonsModel> serviceListFromDB = new ArrayList<OpsSlaTimesCommonsModel>();
		cis = controller.getProperty("VIEW.CIS");
		sod = controller.getProperty("VIEW.SOD");
		eod = controller.getProperty("VIEW.EOD");
		serviceListFromDB = (List<OpsSlaTimesCommonsModel>) controller.viewOpsSlaServicesWithoutCisSodEod(cis, sod, eod);
		Collections.sort(serviceListFromDB, new serviceIdOrderSorter());
		//log.info("serviceListFromDB ===========================" + serviceListFromDB);
		if(serviceListFromDB.size() > 0)
		{
			for(OpsSlaTimesCommonsModel localCommModel : serviceListFromDB)
			{
				WebOpsSlaTimesModel webModel = new WebOpsSlaTimesModel();
				webModel = new WebAdminTranslator().translateOpsSlaTimesCommonsModelToWebModel(localCommModel);
				//String services = new WebOpsSlaTimesModel().getService();
				serviceList.add(webModel);
//				log.info("WebModel***********************"+webModel);
//				log.info("ServiceList***********************"+serviceList);
			}
		}
	}

		private class serviceIdOrderSorter implements Comparator<OpsSlaTimesCommonsModel>
		{
			@Override
			public int compare(OpsSlaTimesCommonsModel o1, OpsSlaTimesCommonsModel o2) 
			{
				if(o1.getService() == null && o2.getService() == null)
				{
					return 0;
				}
				else if(o1.getService() == null)
				{
					return -1;
				}
				else if(o2.getService() == null)
				{
					return 1;
				}
				return o1.getService().compareTo(o2.getService());
			}
		}
		// Sort MemberName in ASCENDING order
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
