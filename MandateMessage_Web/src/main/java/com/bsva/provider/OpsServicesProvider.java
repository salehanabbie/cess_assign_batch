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

import com.bsva.commons.model.ServicesModel;
import com.bsva.controller.Controller;
import com.bsva.models.WebServicesModel;
import com.bsva.translator.WebAdminTranslator;


/**
 * @author ElelwaniR
 */

public class OpsServicesProvider extends SortableDataProvider {
	
	private static Logger log = Logger.getLogger(OpsServicesProvider.class);

	Controller controller;

	
	 class SortableDataProviderComparator implements Comparator<WebServicesModel>, Serializable 
	 {  
		   public int compare(final WebServicesModel o1, final WebServicesModel o2) 
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
	 
	
	private SortableDataProviderComparator comparator = new SortableDataProviderComparator();
	private List<WebServicesModel> webOpsServicesModelList;
	private List<ServicesModel> opsServicesModelList;

	public OpsServicesProvider() 
	{
		controller = new Controller();

		setSort("serviceIdIn", SortOrder.ASCENDING);
		opsServicesModelList = (List<ServicesModel>) controller.retrieveAllOpsServices();
		
		webOpsServicesModelList = new ArrayList<WebServicesModel>();
		

		if (opsServicesModelList.size() > 0) 
		{

			for (ServicesModel localCommonModel : opsServicesModelList)
			{
				
				WebServicesModel webModel = new WebServicesModel();
				webModel = new WebAdminTranslator().translateCommonsServicesModelToWebModel(localCommonModel);
				webOpsServicesModelList.add(webModel);
			
				

			}
		}
	}

	
	@Override
	public Iterator<WebServicesModel> iterator(long first, long count) {
		List<WebServicesModel> newList = new ArrayList<WebServicesModel>(webOpsServicesModelList);
		for(WebServicesModel webServicesModel :newList)
		{
			if (webServicesModel.getServiceIdIn() == null && webServicesModel.getServiceIdInDesc() == null)
			{
				webServicesModel.setServiceIdIn("N/A");
				webServicesModel.setServiceIdInDesc("N/A");
			}
		}
		Collections.sort(newList, comparator);

		return newList.subList(Integer.parseInt(String.valueOf(first)),Integer.parseInt(String.valueOf(first + count))).iterator();
	}


	@Override
	public IModel<WebServicesModel> model(final Object object) {
		return new AbstractReadOnlyModel<WebServicesModel>() {
			@Override
			public WebServicesModel getObject() {
				return (WebServicesModel) object;
			}
		};
	}


	@Override
	public long size()
	{
		return opsServicesModelList.size();

	}






}
