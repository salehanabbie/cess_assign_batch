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



import com.bsva.commons.model.SysCtrlSlaTimeModel;
import com.bsva.controller.Controller;
import com.bsva.models.WebOpsSlaTimesModel;
import com.bsva.models.WebSysCtrlSlaTimeModel;
import com.bsva.translator.WebAdminTranslator;


public class SysCtrlSlaTimeProvider extends SortableDataProvider{
	
	
	private List<WebSysCtrlSlaTimeModel> webSysCtrlSlaTimeList;
	private List<SysCtrlSlaTimeModel> sysCtrlSlaTimeList = new ArrayList<SysCtrlSlaTimeModel>();
	private SortableDataProviderComparator comparator = new SortableDataProviderComparator();
	public static Logger log = Logger.getLogger(SysCtrlSlaTimeProvider.class);
	Controller controller;
	
	
	class SortableDataProviderComparator implements
	Comparator<WebSysCtrlSlaTimeModel>, Serializable {


	private static final long serialVersionUID = 1L;
	
	public int compare(final WebSysCtrlSlaTimeModel o1,final WebSysCtrlSlaTimeModel o2) 
	{
		PropertyModel<Comparable> model1 = new PropertyModel<Comparable>(
				o1, getSort().getProperty().toString());
		PropertyModel<Comparable> model2 = new PropertyModel<Comparable>(
				o2, getSort().getProperty().toString());

		int result = model1.getObject().compareTo(model2.getObject());

		if (!getSort().isAscending()) {
			result = -result;
		}

		return result;
	}


	
}

public SysCtrlSlaTimeProvider() 
{
	controller = new Controller();
	setSort("service", SortOrder.ASCENDING);
	sysCtrlSlaTimeList = (List<SysCtrlSlaTimeModel>) controller.viewSysCtrlSlaTimes();
	webSysCtrlSlaTimeList = new ArrayList<WebSysCtrlSlaTimeModel>();

	if (sysCtrlSlaTimeList.size() > 0) {
		
		for (SysCtrlSlaTimeModel localCommonModel : sysCtrlSlaTimeList)
		{

			WebSysCtrlSlaTimeModel webModel = new WebSysCtrlSlaTimeModel();
			webModel = new WebAdminTranslator().translateSysCtrlSlaTimeModelToWebModel(localCommonModel);
			webSysCtrlSlaTimeList.add(webModel);
		}
	}
}

public  SysCtrlSlaTimeProvider(String service) 
{
		log.info("service--> "+service);
		controller=new Controller();
     setSort("service", SortOrder.ASCENDING);
     webSysCtrlSlaTimeList = new ArrayList<WebSysCtrlSlaTimeModel>();
     sysCtrlSlaTimeList = (List<SysCtrlSlaTimeModel>)controller.viewSysCtrlSlaTimesByCriteria(service);
     log.info("sysCtrlSlaTimeList--> "+sysCtrlSlaTimeList);
     
	     if(sysCtrlSlaTimeList.size() > 0);
   	  {
   		  for (SysCtrlSlaTimeModel localCommonModel : sysCtrlSlaTimeList)
   		  {
   			WebSysCtrlSlaTimeModel webModel = new WebSysCtrlSlaTimeModel();
   			webModel = new WebAdminTranslator().translateSysCtrlSlaTimeModelToWebModel(localCommonModel);
   			webSysCtrlSlaTimeList.add(webModel);
   		  }
   	  	}
 } 
@Override
public void detach() {
	// TODO Auto-generated method stub

}

@Override
public Iterator<WebSysCtrlSlaTimeModel> iterator(long first, long count) {
	// Get the data
	List<WebSysCtrlSlaTimeModel> newList = new ArrayList<WebSysCtrlSlaTimeModel>(webSysCtrlSlaTimeList);

	// Sort the data
	Collections.sort(newList, comparator);

	return newList.subList(Integer.parseInt(String.valueOf(first)),
			Integer.parseInt(String.valueOf(first + count))).iterator();
}

@Override
public IModel<WebSysCtrlSlaTimeModel> model(final Object object) {
	return new AbstractReadOnlyModel<WebSysCtrlSlaTimeModel>() {
		@Override
		public WebSysCtrlSlaTimeModel getObject() {
			return (WebSysCtrlSlaTimeModel) object;
		}
	};
}

@Override
public long size()
{

	return sysCtrlSlaTimeList.size();

}


	}
