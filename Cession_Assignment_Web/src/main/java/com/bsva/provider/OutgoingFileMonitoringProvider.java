package com.bsva.provider;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

import com.bsva.commons.model.OpsFileRegModel;
import com.bsva.commons.model.SysctrlCompParamModel;
import com.bsva.controller.Controller;
import com.bsva.models.WebOpsFileRegModel;
import com.bsva.translator.WebAdminTranslator;

public class OutgoingFileMonitoringProvider extends SortableDataProvider{
	

	Controller controller;
	

	class SortableDataProviderComparator implements Comparator<WebOpsFileRegModel>, Serializable 
	{  
		public int compare(final WebOpsFileRegModel o1, final WebOpsFileRegModel o2) 
		{  
			PropertyModel<Comparable> model1 = new PropertyModel<Comparable>(o1, getSort().getProperty().toString());  
			PropertyModel<Comparable> model2 = new PropertyModel<Comparable>(o2, getSort().getProperty().toString());  

			int result = model1.getObject().compareTo(model2.getObject());  

			if (!getSort().isAscending()) 
			{  
				result = -result;  
			}  

			return result;  
		}  
	}

	private static final long serialVersionUID = 1L;
	private List<WebOpsFileRegModel> webOpsFileRegList;
	private List<OpsFileRegModel> opsFileRegList;
	private SortableDataProviderComparator comparator = new SortableDataProviderComparator();
	public static Logger log = Logger.getLogger(ViewFileStatusOutgoingProvider.class);


	public OutgoingFileMonitoringProvider()
	{
		
		controller = new Controller();
	
		
		SysctrlCompParamModel companyParamModel = new SysctrlCompParamModel();
		companyParamModel = (SysctrlCompParamModel) controller.retrieveCompanyParameters();
		
		log.debug("In the provider constructor");
		setSort("fileName", SortOrder.ASCENDING);
		//opsFileRegList=new ArrayList<OpsFileRegModel>();
		opsFileRegList = (List<OpsFileRegModel>) controller.viewOpsFileRegByDirection("O",companyParamModel.getTransamissionInd()+"D");
		webOpsFileRegList = new ArrayList<WebOpsFileRegModel>();

		if(opsFileRegList != null && opsFileRegList.size() > 0)
		{
			for (OpsFileRegModel fileRegCommonModel : opsFileRegList) 
			{
				WebOpsFileRegModel webModel = new WebOpsFileRegModel();
				webModel = new WebAdminTranslator().translateCommonsOpsFileRegModelToWebModel(fileRegCommonModel);
				webOpsFileRegList.add(webModel);

			
			}
		}
	}

	
	public OutgoingFileMonitoringProvider(String fileName)
	{
		
		controller = new Controller();
		setSort("fileName", SortOrder.ASCENDING);
		opsFileRegList=new ArrayList<OpsFileRegModel>();
		opsFileRegList = (List<OpsFileRegModel>) controller.viewFileStatusFilter(fileName);
		webOpsFileRegList = new ArrayList<WebOpsFileRegModel>();
	
		if(opsFileRegList.size() > 0)
		{
			for (OpsFileRegModel fileRegCommonModel : opsFileRegList) 
			{
					WebOpsFileRegModel webModel = new WebOpsFileRegModel();
					webModel = new WebAdminTranslator().translateCommonsOpsFileRegModelToWebModel(fileRegCommonModel);
					webOpsFileRegList.add(webModel);
					
			}
		}
		
	}
	
	public OutgoingFileMonitoringProvider(String memberNo, String service)
	{
		controller = new Controller();
		setSort("fileName", SortOrder.ASCENDING);
		opsFileRegList=new ArrayList<OpsFileRegModel>();
		opsFileRegList = (List<OpsFileRegModel>) controller.viewFileStatusSearch(memberNo, service);
		webOpsFileRegList = new ArrayList<WebOpsFileRegModel>();
	
		if(opsFileRegList.size() > 0)
		{
			for (OpsFileRegModel fileRegCommonModel : opsFileRegList) 
			{
					WebOpsFileRegModel webModel = new WebOpsFileRegModel();
					webModel = new WebAdminTranslator().translateCommonsOpsFileRegModelToWebModel(fileRegCommonModel);
					webOpsFileRegList.add(webModel);
					
			}
		}
		
	}

	@Override
	public void detach() {
		// TODO Auto-generated method stub

	}

	@Override
	public Iterator<WebOpsFileRegModel> iterator(long first, long count) {
		// Get the data  
		List<WebOpsFileRegModel> newList = new ArrayList<WebOpsFileRegModel>(webOpsFileRegList);  
		// Sort the data  
		Collections.sort(newList, comparator);

		for (WebOpsFileRegModel webOpsFileRegModel : newList)
		{
			if(webOpsFileRegModel.getExtractMsgId()== null)
			{
				
				webOpsFileRegModel.setExtractMsgId("N/A");
			}
		}
		return newList.subList(Integer.parseInt(String.valueOf(first)), Integer.parseInt(String.valueOf(first + count))).iterator();  
	}

	@Override
	public IModel<WebOpsFileRegModel> model(final Object object) {
		return new AbstractReadOnlyModel<WebOpsFileRegModel>() 
				{  
			@Override  
			public WebOpsFileRegModel getObject() {  
				return (WebOpsFileRegModel) object;  
			}  
				}; 
	}

	@Override
	public long size() {
		// TODO Auto-generated method stub
		return opsFileRegList.size();

	}



}
