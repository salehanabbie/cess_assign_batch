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

import com.bsva.commons.model.OpsStatusDetailsModel;
import com.bsva.controller.Controller;
import com.bsva.models.WebOpsStatusDetailsModel;


public class OpsStatusDetailsProvider extends SortableDataProvider{

	
	

Controller controller;

class SortableDataProviderComparator implements Comparator<WebOpsStatusDetailsModel>, Serializable 
{  
	   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public int compare(final WebOpsStatusDetailsModel o1, final WebOpsStatusDetailsModel o2) 
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
private List<WebOpsStatusDetailsModel> webOpsStatusDetailsList = new ArrayList<WebOpsStatusDetailsModel>();
private List<OpsStatusDetailsModel> opsStatusDetailsList;
private SortableDataProviderComparator comparator = new SortableDataProviderComparator();
public static Logger log = Logger.getLogger(OpsStatusDetailsProvider.class);
	

public OpsStatusDetailsProvider(List<WebOpsStatusDetailsModel> webOpsStatusDetailsList)
{
	
	
	controller = new Controller();
	log.debug("In the provider constructor");
	setSort("txnId", SortOrder.ASCENDING);
	 this.webOpsStatusDetailsList= webOpsStatusDetailsList;
	/*opsStatusDetailsList=new ArrayList<OpsStatusDetailsModel>();
	opsStatusDetailsList = (List<OpsStatusDetailsModel>) controller.viewAllOpsStatusDetails();
	webOpsStatusDetailsList = new ArrayList<WebOpsStatusDetailsModel>();

	if(opsStatusDetailsList.size() > 0)
	{
		for (OpsStatusDetailsModel opsStatusDetailsModel : opsStatusDetailsList) 
		{
			WebOpsStatusDetailsModel webModel = new WebOpsStatusDetailsModel();
				webModel = new WebAdminTranslator().translateCommonsOpsStatusDetailsModelToWebOpsStatusDetailsModel(opsStatusDetailsModel);
				webOpsStatusDetailsList.add(webModel);
		}
	}*/
}


@Override
public Iterator<WebOpsStatusDetailsModel> iterator(long first, long count) {
	
	
	  List<WebOpsStatusDetailsModel> newList = new ArrayList<WebOpsStatusDetailsModel>(webOpsStatusDetailsList);

	// Sort the data  
	  Collections.sort(newList, comparator);  
	  
	  return newList.subList(Integer.parseInt(String.valueOf(first)), Integer.parseInt(String.valueOf(first + count))).iterator();  
}


@Override
public IModel<WebOpsStatusDetailsModel> model(final Object object) {
	return new AbstractReadOnlyModel<WebOpsStatusDetailsModel>() 
			{  
		   @Override  
		   public WebOpsStatusDetailsModel getObject() {  
		    return (WebOpsStatusDetailsModel) object;  
		   }  
		  }; 
}

@Override
public long size() {
	// TODO Auto-generated method stub
	return webOpsStatusDetailsList.size();
	
}

}
