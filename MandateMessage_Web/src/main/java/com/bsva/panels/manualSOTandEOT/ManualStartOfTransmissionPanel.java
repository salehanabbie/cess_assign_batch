package com.bsva.panels.manualSOTandEOT;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.apache.wicket.model.PropertyModel;

import com.bsva.commons.model.AcOpsSotEotCntrlModel;
import com.bsva.commons.model.CisMemberModel;
import com.bsva.commons.model.ServicesModel;
import com.bsva.commons.model.SysCisBankModel;
import com.bsva.controller.Controller;
import com.bsva.models.WebAcOpsSotEotModel;
import com.bsva.models.WebCisMemberModel;
import com.bsva.models.WebServicesModel;
import com.bsva.models.WebSysCisBankModel;
import com.bsva.translator.WebAdminTranslator;
import com.bsva.validators.SimpleAttributeModifier;

/**
 * 
 * @author NhlanhlaM
 *
 */

public class ManualStartOfTransmissionPanel extends Panel {

	private static final long serialVersionUID = 1L;
	public static Logger log = Logger.getLogger(ManualStartOfTransmissionPanel.class);
	Controller controller;

	private Form form;
	public static String id;
	private Button generateSOTFileTButton;
	private String action;
	private DropDownChoice<WebSysCisBankModel>  instId;
	//private DropDownChoice<WebServicesModel> serviceId;

	
	private WebSysCisBankModel webSysCisBankModel;
	private List<WebSysCisBankModel> instIdList = new ArrayList<WebSysCisBankModel>();
	private WebServicesModel webServicesModel;
	private List<WebServicesModel> serviceIdInList = new ArrayList<WebServicesModel>();
	
	private DropDownChoice<WebAcOpsSotEotModel> serviceId;
	WebAcOpsSotEotModel webAcOpsSotEotModel;
	private List<WebAcOpsSotEotModel> webSotEotModelList  = new ArrayList<WebAcOpsSotEotModel>();

	public ManualStartOfTransmissionPanel(String id) 
	{
		super(id);
		this.action = action;
		this.id = id;
		initializeComponents();
	}

	public ManualStartOfTransmissionPanel(String id,WebServicesModel webServicesModel,WebSysCisBankModel webSysCisBankModel, String action) 
	{
		super(id);
		this.action = action;
		this.id = id;
		this.webServicesModel = webServicesModel;
		this.webSysCisBankModel = webSysCisBankModel;
		initializeComponents();
		log.debug("Screen ID: " + action);

	}

	private void initializeComponents()
	{
		form = new Form("manualStartOfTransmissionPanel");

		controller = new Controller();
		loadData();

		generateSOTFileTButton = new Button("generateSOTFileTButton") 
		{
			private static final long serialVersionUID = 1L;

			public void onSubmit() 
			{							
			
				String destMemberId = instId.getChoices().get(Integer.parseInt(instId.getValue())).getMemberNo();
				String serviceName = serviceId.getChoices().get(Integer.parseInt(serviceId.getValue())).getServiceId();
				controller.runSOT(destMemberId,serviceName);
				info("SOT File created successfully");
			}
		};
		//generateSOTFileTButton.add(new SimpleAttributeModifier("onclick","Are you sure you want run SOT ?"));
		form.add(generateSOTFileTButton);
		
		
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

		serviceId = new DropDownChoice<WebAcOpsSotEotModel>("serviceId",new Model<WebAcOpsSotEotModel>(), webSotEotModelList,new ChoiceRenderer<WebAcOpsSotEotModel>());
		serviceId.setRequired(true);

		if (action == "create")
		{
				if(webSotEotModelList != null && webSotEotModelList.size() > 0)
				{
					serviceId.setDefaultModelObject(serviceId.getChoices().get(0).getServiceId());
				}
		}
		else if (action == "update") 
		{
			int sel = 0;
			for (int index = 0; index < webSotEotModelList.size(); index++) 
			{

				if (webAcOpsSotEotModel.getServiceId().equalsIgnoreCase(
						webSotEotModelList.get(index).getServiceId())) 
				{
					sel = index;
				}

			}

			serviceId.setModelObject(serviceId.getChoices().get(sel));

		}
		form.add(serviceId);
		
		add(form);

		FeedbackPanel feedbackPanel = new FeedbackPanel("feedback_1");
		add(feedbackPanel);
	}

	private void loadData() 
	{		

	
	// Generate InstId for drop down box 
		List<SysCisBankModel> instIdListFromDb = new ArrayList<SysCisBankModel>();

		instIdListFromDb = (List<SysCisBankModel>) controller.viewAllMember();
		Collections.sort(instIdListFromDb, new memberNameOrderSorter());

		if (instIdListFromDb.size() > 0) 
		{
			for (SysCisBankModel localCommonModel : instIdListFromDb) 
			{
				WebSysCisBankModel webModel = new WebSysCisBankModel();
				webModel = new WebAdminTranslator().translateCommonsSysCisBankModelToWebModel(localCommonModel);
				 // controller.viewAllMember();         
				 instIdList.add(webModel);
			}
		}// end of if

		/* Generate SeveritryCodes for drop down box 
		List<ServicesModel> serviceIdOutListFromDb = new ArrayList<ServicesModel>();
		
			
		serviceIdOutListFromDb = (List<ServicesModel>) controller.viewAllServices();
		System.out.println("serviceIdOutListFromDb***********"+ serviceIdOutListFromDb);
		
		Collections.sort(serviceIdOutListFromDb, new serviceIdOrderSorter());
		
		if (serviceIdOutListFromDb.size() > 0) 
		{
			serviceIdInList = new ArrayList<WebServicesModel>();
			for (ServicesModel servModel : serviceIdOutListFromDb) 
			{
				WebServicesModel webLocalModel = new WebServicesModel();
				webLocalModel = new WebAdminTranslator().translateCommonsServicesModelToWebModel(servModel);
				serviceIdInList.add(webLocalModel);
				
				System.out.println("serviceIdInList***********"+ serviceIdInList);
			}

		}// end of if	
*/		
		
		
		List<AcOpsSotEotCntrlModel> soteotModelList = new ArrayList<AcOpsSotEotCntrlModel>();
		soteotModelList = (List<AcOpsSotEotCntrlModel>)controller.retrieveSotEotServiceId();
		Collections.sort(soteotModelList, new serviceIdOrderSorter());
		
		if (soteotModelList.size() > 0) 
		{
			for (AcOpsSotEotCntrlModel opsSotEotCntrlModel : soteotModelList) 
			{
				WebAcOpsSotEotModel webLocalModel = new WebAcOpsSotEotModel();
				webLocalModel = new WebAdminTranslator().translateOpsProcessControlCommonsModelToWebModel(opsSotEotCntrlModel);
				webSotEotModelList.add(webLocalModel);	
			}

		}// end of if	

	}
	// Sort ServiceId in ASCENDING order
		private class serviceIdOrderSorter implements Comparator<AcOpsSotEotCntrlModel>
		{

			@Override
			public int compare(AcOpsSotEotCntrlModel o1, AcOpsSotEotCntrlModel o2) {
			
				if(o1.getServiceId() == null && o2.getServiceId() == null)
				{
					return 0;
				}
				else if(o1.getServiceId() == null)
				{
					return -1;
				}
				else if(o2.getServiceId() == null)
				{
					return 1;
				}
				return o1.getServiceId().compareTo(o2.getServiceId());
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
