package com.bsva.provider;

import java.io.Serializable;
import java.math.BigDecimal;
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

import com.bsva.commons.model.SystemControlServicesModel;
import com.bsva.controller.Controller;
import com.bsva.models.WebOpsFileRegModel;
import com.bsva.models.WebSystemControlServicesModel;
import com.bsva.translator.WebAdminTranslator;

/**
 * 
 * @author NhlanhlaM
 *
 */


public class SystemControlServicesProvider extends SortableDataProvider
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Controller controller = new Controller();
	
	class SortableDataProviderComparator implements Comparator<WebSystemControlServicesModel>, Serializable 
	{
		private static final long serialVersionUID = 1L;
		public int compare(final WebSystemControlServicesModel arg0, final WebSystemControlServicesModel arg1) 
		{
			
			PropertyModel<Comparable> model1 = new PropertyModel<Comparable>(arg0, getSort().getProperty().toString());
			PropertyModel<Comparable> model2 = new PropertyModel<Comparable>(arg1, getSort().getProperty().toString());

			int result = model1.getObject().compareTo(model2.getObject());
			
			if (!getSort().isAscending())
			{
				result = -result;
			}

			return result;
			
		}
	
	}

	private List<WebSystemControlServicesModel> webSystemControlServicesModelList;
	private List<SystemControlServicesModel> systemControlServicesModelList;
	private SortableDataProviderComparator comparator = new SortableDataProviderComparator();
	public static Logger log = Logger.getLogger(SystemControlServicesProvider.class);
	
	
	public SystemControlServicesProvider()
	{
         setSort("RecordId", SortOrder.ASCENDING);
		
         systemControlServicesModelList = (List<SystemControlServicesModel>) controller.viewAllRecordId();
         webSystemControlServicesModelList = new ArrayList<WebSystemControlServicesModel>();
	   
		
	    if(systemControlServicesModelList.size() > 0)
	    {
	    	for (SystemControlServicesModel systemControlServicesModel : systemControlServicesModelList)
	    	{
	    		WebSystemControlServicesModel webModel = new WebSystemControlServicesModel();
	    		webModel = new WebAdminTranslator().translateCommonsSystemControlServicesModelToWebModel(systemControlServicesModel);
	    		webSystemControlServicesModelList.add(webModel);
	    	}
	    }
	}
	
	public  SystemControlServicesProvider(BigDecimal RecordId) 
	{
	     setSort("RecordId", SortOrder.ASCENDING);
	     systemControlServicesModelList = (List<SystemControlServicesModel>)controller.viewRecordIdByCriteria(RecordId);
	     webSystemControlServicesModelList = new ArrayList<WebSystemControlServicesModel>();
   	     
   	     if(systemControlServicesModelList.size() > 0);
   	  
   	  {
   		  	for (SystemControlServicesModel localCommonModel : systemControlServicesModelList)
   		  {
   		  	WebSystemControlServicesModel webModel = new WebSystemControlServicesModel();
   			webModel = new WebAdminTranslator().translateCommonsSystemControlServicesModelToWebModel(localCommonModel);
   			webSystemControlServicesModelList.add(webModel);
   		  }
   	  	}
   	  
     } 
	
	public  SystemControlServicesProvider(String serviceId) 
	{
	     setSort("RecordId", SortOrder.ASCENDING);
	     controller = new Controller();
	     systemControlServicesModelList = (List<SystemControlServicesModel>)controller.viewSysCntrlServiceByService(serviceId);
	     webSystemControlServicesModelList = new ArrayList<WebSystemControlServicesModel>();
   	     
   	     if(systemControlServicesModelList.size() > 0);
   	  
   	  {
   		  	for (SystemControlServicesModel localCommonModel : systemControlServicesModelList)
   		  {
   		  	WebSystemControlServicesModel webModel = new WebSystemControlServicesModel();
   			webModel = new WebAdminTranslator().translateCommonsSystemControlServicesModelToWebModel(localCommonModel);
   			webSystemControlServicesModelList.add(webModel);
   		  }
   	  	}
   	  
     } 
	

	
	@Override
	public Iterator<WebSystemControlServicesModel> iterator(long first, long count)
	{
		// Get the data
		List<WebSystemControlServicesModel> newList = new ArrayList<WebSystemControlServicesModel>(webSystemControlServicesModelList);

		// Sort the data
				Collections.sort(newList, comparator);
				
				for (WebSystemControlServicesModel webSystemControlServicesModel : newList)
				{
					if(webSystemControlServicesModel.getServiceIdIn()== null && webSystemControlServicesModel.getServiceIdInDesc() == null)
					{
						
						webSystemControlServicesModel.setServiceIdIn("N/A");
						webSystemControlServicesModel.setServiceIdInDesc("N/A");
					}
				}

				return newList.subList(Integer.parseInt(String.valueOf(first)),Integer.parseInt(String.valueOf(first + count))).iterator();
			}


	@Override
	public void detach() {
		// TODO Auto-generated method stub

	}



		
	
	
	public IModel<WebSystemControlServicesModel> model(final Object object)
	{

		return new AbstractReadOnlyModel<WebSystemControlServicesModel>() 
		{

	/**
	 * 
	 */
			private static final long serialVersionUID = 1L;


			@Override
			public WebSystemControlServicesModel getObject() 
			{

				return (WebSystemControlServicesModel) object;
			}

		};
	}
	
	
	@Override
	public long size()
	{
		return systemControlServicesModelList.size();
	}

	

}
