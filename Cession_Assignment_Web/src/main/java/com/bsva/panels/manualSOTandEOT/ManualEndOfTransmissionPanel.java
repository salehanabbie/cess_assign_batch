package com.bsva.panels.manualSOTandEOT;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

import com.bsva.commons.model.CisMemberModel;
import com.bsva.commons.model.CustomerParametersModel;
import com.bsva.commons.model.ServicesModel;
import com.bsva.commons.model.SysCisBankModel;
import com.bsva.controller.Controller;
import com.bsva.models.WebCisMemberModel;
import com.bsva.models.WebCustomerParametersModel;
import com.bsva.models.WebOpsServicesModel;
import com.bsva.models.WebServicesModel;
import com.bsva.models.WebSysCisBankModel;
import com.bsva.translator.WebAdminTranslator;
import com.bsva.validators.SimpleAttributeModifier;

/***
 * 
 * @author DimakatsoN
 *
 */

public class ManualEndOfTransmissionPanel extends Panel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static Logger log = Logger.getLogger(ManualEndOfTransmissionPanel.class);
	Controller controller;

	private Form form;
	public static String id;
	private Button generateEOTFileTButton;
	private String action;
	private DropDownChoice<WebSysCisBankModel> instId;
	private DropDownChoice<WebOpsServicesModel> serviceIdOut;

	private WebSysCisBankModel webSysCisBankModel;
	private List<WebSysCisBankModel> instIdList = new ArrayList<WebSysCisBankModel>();

	private WebOpsServicesModel webOpsServicesModel;
	private List<WebOpsServicesModel> serviceIdOutList  = new ArrayList<WebOpsServicesModel>();

	public ManualEndOfTransmissionPanel(String id) {
		super(id);
		this.id = id;
		initializeComponents();
	}

	public ManualEndOfTransmissionPanel(String id,WebOpsServicesModel webOpsServicesModel,WebSysCisBankModel webSysCisBankModel, String action) {

		super(id);
		this.action = action;
		this.id = id;
		this.webOpsServicesModel = webOpsServicesModel;
		this.webSysCisBankModel = webSysCisBankModel;
		initializeComponents();
		log.debug("Screen ID: " + action);

	}

	private void initializeComponents()
	{
		form = new Form("form");

		controller = new Controller();
		loadData();

		generateEOTFileTButton = new Button("generateEOTFileTButton") 
		{
			private static final long serialVersionUID = 1L;

			public void onSubmit()
			{

				String destMemberId = instId.getChoices().get(Integer.parseInt(instId.getValue())).getMemberNo();
				System.out.println("*********destMemberId*********"+ destMemberId);
				String serviceName = serviceIdOut.getChoices().get(Integer.parseInt(serviceIdOut.getValue())).getServiceIdOut();
				System.out.println("*********serviceName*********"+ serviceName);
				controller.runEOT(destMemberId, serviceName);
				info("EOT File created successfully");
			}
		};
		generateEOTFileTButton.add(new SimpleAttributeModifier("onclick","Are you sure you want run EOT ?"));
		form.add(generateEOTFileTButton);

		instId = new DropDownChoice<WebSysCisBankModel>("instId",new Model<WebSysCisBankModel>(), instIdList,new ChoiceRenderer<WebSysCisBankModel>());
		instId.setRequired(true);

		if (action == "create")
		{
			if(instIdList != null && instIdList.size() > 0)
			{
				instId.setDefaultModelObject(instId.getChoices().get(0).getMemberNo());
			}
		}
		else if (action == "update") 
		{
			int sel = 0;
			for (int index = 0; index < instIdList.size(); index++) 
			{

				if (webSysCisBankModel.getMemberNo().equalsIgnoreCase(
						instIdList.get(index).getMemberNo())) 
				{
					sel = index;
				}

			}
			instId.setModelObject(instId.getChoices().get(sel));
		}

		form.add(instId);

		serviceIdOut = new DropDownChoice<WebOpsServicesModel>("serviceIdOut",new Model<WebOpsServicesModel>(), serviceIdOutList,new ChoiceRenderer<WebOpsServicesModel>());
		serviceIdOut.setRequired(true);

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
				if (webOpsServicesModel.getServiceIdOut().equalsIgnoreCase(serviceIdOutList.get(index).getServiceIdOut())) 
				{
					sel = index;
				}
		}

			serviceIdOut.setModelObject(serviceIdOut.getChoices().get(sel));

		}
		form.add(serviceIdOut);

		add(form);

		FeedbackPanel feedbackPanel = new FeedbackPanel("feedback_1");
		add(feedbackPanel);
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
		}
		
		
		/* Generate SeveritryCodes for drop down box */
		List<ServicesModel> serviceIdOutListFromDb = new ArrayList<ServicesModel>();
		serviceIdOutListFromDb = (List<ServicesModel>) controller.viewAllServices();
		
		Collections.sort(serviceIdOutListFromDb, new serviceIdOrderSorter());
		
		if (serviceIdOutListFromDb.size() > 0) 
		{
			for (ServicesModel servModel : serviceIdOutListFromDb) 
			{
				WebOpsServicesModel webLocalModel = new WebOpsServicesModel();
				webLocalModel = new WebAdminTranslator().translateCommonsOpsServicesToWebModel(servModel);
				serviceIdOutList.add(webLocalModel);

			}
		}// end of if

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
		
		// Sort MemberName in ASCENDING order
		private class memberNameOrderSorter implements Comparator<SysCisBankModel>
		{

			@Override
			public int compare(SysCisBankModel o1, SysCisBankModel o2) {
				if(o1.getMemberName() == null && o2.getMemberName() == null)
				{
					return 0;
				}
				else if(o1.getMemberName() == null)
				{
					return -1;
				}
				else if(o2.getMemberName() == null)
				{
					return 1;
				}
				return o1.getMemberName().compareTo(o2.getMemberName());
				
			}
			
		}
		

}
