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

import com.bsva.commons.model.MandatesCountCommonsModel;
import com.bsva.commons.model.OpsFileRegModel;
import com.bsva.controller.Controller;
import com.bsva.models.WebMandateCountModel;
import com.bsva.models.WebOpsFileRegModel;
import com.bsva.translator.WebAdminTranslator;

public class ViewFileStatusProcessIncomingProvider extends SortableDataProvider{

Controller controller;

class SortableDataProviderComparator implements Comparator<WebMandateCountModel>, Serializable 
{  
	   public int compare(final WebMandateCountModel o1, final WebMandateCountModel o2) 
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
	private List<WebMandateCountModel> webMandateCountModelList;
	private List<MandatesCountCommonsModel> mandatesCountCommonsModelList;
	private SortableDataProviderComparator comparator = new SortableDataProviderComparator();
	public static Logger log = Logger.getLogger(ViewFileStatusProcessIncomingProvider.class);
	private List<WebOpsFileRegModel> webOpsFileRegList;
	private List<OpsFileRegModel> opsFileRegList;
		
	public ViewFileStatusProcessIncomingProvider()
	{
		controller=new Controller();
		setSort("instId", SortOrder.ASCENDING);
		mandatesCountCommonsModelList=new ArrayList<MandatesCountCommonsModel>();
		mandatesCountCommonsModelList = (List<MandatesCountCommonsModel>) controller.viewMandatesCountIncomingMessages();
		
		webMandateCountModelList = new ArrayList<WebMandateCountModel>();
	
		if(mandatesCountCommonsModelList.size() > 0)
		{
			for (MandatesCountCommonsModel mandatesCountCommonsModel : mandatesCountCommonsModelList) 
			{
				    WebMandateCountModel webModel = new WebMandateCountModel();
					webModel = new WebAdminTranslator().transalateCommonsMandatesCountToReasonWebModel(mandatesCountCommonsModel);
					webMandateCountModelList.add(webModel);
			}
		}
	}
	
	public ViewFileStatusProcessIncomingProvider(String instId)
	{
		controller=new Controller();
		setSort("instId", SortOrder.ASCENDING);
		mandatesCountCommonsModelList=new ArrayList<MandatesCountCommonsModel>();
		mandatesCountCommonsModelList = (List<MandatesCountCommonsModel>) controller.viewMandatesCountIncomingMessages();
//		log.info("the list has the following information ##########################"+mandatesCountCommonsModelList);
		webMandateCountModelList = new ArrayList<WebMandateCountModel>();
		if(mandatesCountCommonsModelList.size() > 0)
		{
			for (MandatesCountCommonsModel mandatesCountCommonsModel : mandatesCountCommonsModelList) 
			{
				    WebMandateCountModel webModel = new WebMandateCountModel();
					webModel = new WebAdminTranslator().transalateCommonsMandatesCountToReasonWebModel(mandatesCountCommonsModel);
					webMandateCountModelList.add(webModel);
			}
		}
	}
	
	public ViewFileStatusProcessIncomingProvider(String memberNo,String service)
	{
		controller = new Controller();
		setSort("fileName", SortOrder.ASCENDING);
		mandatesCountCommonsModelList=new ArrayList<MandatesCountCommonsModel>();
		mandatesCountCommonsModelList = (List<MandatesCountCommonsModel>) controller.viewSystemStatusSearch(memberNo, service);
		webMandateCountModelList = new ArrayList<WebMandateCountModel>();
//		log.info("mandatesCountCommonsModelList 1 in PROVIDER ------->" + mandatesCountCommonsModelList);
		
		if(mandatesCountCommonsModelList.size() > 0)
		{
			for (MandatesCountCommonsModel fileRegCommonModel : mandatesCountCommonsModelList) 
			{
					WebMandateCountModel webModel = new WebMandateCountModel();
					webModel = new WebAdminTranslator().transalateCommonsMandatesCountToReasonWebModel(fileRegCommonModel);
					webMandateCountModelList.add(webModel);
					
			}
		}
//		log.info("mandatesCountCommonsModelList 2 in PROVIDER ------->" + mandatesCountCommonsModelList);
	}
	
//	public ViewFileStatusProcessIncomingProvider(String memberNo,String service)
//	{
//		controller = new Controller();
//		setSort("fileName", SortOrder.ASCENDING);
//		opsFileRegList=new ArrayList<OpsFileRegModel>();
//		opsFileRegList = (List<OpsFileRegModel>) controller.viewFileStatusSearch(memberNo, service);
//		webOpsFileRegList = new ArrayList<WebOpsFileRegModel>();
//	
//		if(opsFileRegList.size() > 0)
//		{
//			for (OpsFileRegModel fileRegCommonModel : opsFileRegList) 
//			{
//					WebOpsFileRegModel webModel = new WebOpsFileRegModel();
//					webModel = new WebAdminTranslator().translateCommonsOpsFileRegModelToWebModel(fileRegCommonModel);
//					webOpsFileRegList.add(webModel);
//					
//			}
//		}
//	}
	
	

	@Override
	public void detach() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Iterator<WebMandateCountModel> iterator(long first, long count) {
	       // Get the data  
		  List<WebMandateCountModel> newList = new ArrayList<WebMandateCountModel>(webMandateCountModelList);  
		  
		  // Sort the data  
		  Collections.sort(newList, comparator);  
		  
		  return newList.subList(Integer.parseInt(String.valueOf(first)), Integer.parseInt(String.valueOf(first + count))).iterator();  
	}

	@Override
	public IModel<WebMandateCountModel> model(final Object object) {
		return new AbstractReadOnlyModel<WebMandateCountModel>() 
				{  
			   @Override  
			   public WebMandateCountModel getObject() {  
			    return (WebMandateCountModel) object;  
			   }  
			  }; 
	}

	@Override
	public long size() {
		// TODO Auto-generated method stub
		return mandatesCountCommonsModelList.size();
		
	}



}
