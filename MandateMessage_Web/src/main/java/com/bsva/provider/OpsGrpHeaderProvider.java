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

import com.bsva.commons.model.OpsStatusHdrsModel;
import com.bsva.controller.Controller;
import com.bsva.models.WebOpsStatusHdrsModel;

/***
 * 
 * @author DimakatsoN
 *
 */
public class OpsGrpHeaderProvider extends SortableDataProvider {
	

Controller controller;

class SortableDataProviderComparator implements Comparator<WebOpsStatusHdrsModel>, Serializable 
{  
	   public int compare(final WebOpsStatusHdrsModel o1, final WebOpsStatusHdrsModel o2) 
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
private List<WebOpsStatusHdrsModel> webOpsStatusHrdsList = new ArrayList<WebOpsStatusHdrsModel>();
private List<OpsStatusHdrsModel> opsStatusHrdsList ;
private SortableDataProviderComparator comparator = new SortableDataProviderComparator();
public static Logger log = Logger.getLogger(OpsGrpHeaderProvider.class);
	

public OpsGrpHeaderProvider(List<WebOpsStatusHdrsModel> webOpsStatusHrdsList)
{
	controller = new Controller();
	log.debug("In the provider constructor");
	setSort("orgnlMsgId", SortOrder.ASCENDING);
	 this.webOpsStatusHrdsList =webOpsStatusHrdsList;
	/*opsStatusHrdsList=new ArrayList<OpsStatusHdrsModel>();
	opsStatusHrdsList = (List<OpsStatusHdrsModel>) controller.viewAllOpsStatusHdrs();
	webOpsStatusHrdsList = new ArrayList<WebOpsStatusHdrsModel>();

	if(opsStatusHrdsList.size() > 0)
	{
		for (OpsStatusHdrsModel statusHrdsModel : opsStatusHrdsList) 
		{
			WebOpsStatusHdrsModel webModel = new WebOpsStatusHdrsModel();
				webModel = new WebAdminTranslator().translateCommonsOpsStatusHdrsModelToWebOpsStatusHrdsModel(statusHrdsModel);
				webOpsStatusHrdsList.add(webModel);
		}
	}*/
}

@Override
public Iterator<WebOpsStatusHdrsModel> iterator(long first, long count) {
	
	
	  List<WebOpsStatusHdrsModel> newList = new ArrayList<WebOpsStatusHdrsModel>(webOpsStatusHrdsList);

	// Sort the data  
	  Collections.sort(newList, comparator);  
	  
	  return newList.subList(Integer.parseInt(String.valueOf(first)), Integer.parseInt(String.valueOf(first + count))).iterator();  
}


@Override
public IModel<WebOpsStatusHdrsModel> model(final Object object) {
	return new AbstractReadOnlyModel<WebOpsStatusHdrsModel>() 
			{  
		   @Override  
		   public WebOpsStatusHdrsModel getObject() {  
		    return (WebOpsStatusHdrsModel) object;  
		   }  
		  }; 
}

@Override
public long size() {
	// TODO Auto-generated method stub
	return webOpsStatusHrdsList.size();
	
}

}
