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
import com.bsva.commons.model.ProcessStatusModel;
import com.bsva.commons.model.ReasonCodesModel;
import com.bsva.controller.Controller;
import com.bsva.models.WebProcessStatusModel;
import com.bsva.models.WebReasonCodesModel;
import com.bsva.translator.WebAdminTranslator;

public class ProcessStatusProvider extends SortableDataProvider
{
	Controller controller = new Controller();
	class SortableDataProviderComparator implements Comparator<WebProcessStatusModel>, Serializable 
	{  
		private static final long serialVersionUID = 1L;
		public int compare(final WebProcessStatusModel o1, final WebProcessStatusModel o2) 
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
	 
	private List<WebProcessStatusModel> webprocessStatusList;
	private List<ProcessStatusModel> processStatusList;
	private SortableDataProviderComparator comparator = new SortableDataProviderComparator();
	public static Logger log = Logger.getLogger(ProcessStatusProvider.class);

	public ProcessStatusProvider()
	{			 
		setSort("status", SortOrder.ASCENDING);	
		processStatusList = (List<ProcessStatusModel>) controller.viewAllProcessStatus();
		webprocessStatusList = new ArrayList<WebProcessStatusModel>();
		if(processStatusList.size() > 0)
		{
			for (ProcessStatusModel processStatusModel : processStatusList) 
			{
				WebProcessStatusModel webModel = new WebProcessStatusModel();
				webModel = new WebAdminTranslator().translateCommonsProcessStatusModelToWebModel(processStatusModel);
				webprocessStatusList.add(webModel);		
			}
		}
	}
		
	public ProcessStatusProvider(String status)
	{
		setSort("status", SortOrder.ASCENDING);
		processStatusList = (List<ProcessStatusModel>) controller.viewProcessStatusByCriteria(status);
		webprocessStatusList = new ArrayList<WebProcessStatusModel>();
		if(processStatusList.size() > 0)	
		{
			for (ProcessStatusModel localCommonModel : processStatusList) 
			{
				WebProcessStatusModel webModel = new WebProcessStatusModel();
				webModel = new WebAdminTranslator().translateCommonsProcessStatusModelToWebModel(localCommonModel);
				webprocessStatusList.add(webModel);
			}
		}
	}
		
	@Override
	public void detach() 
	{
	}
		
	@Override
	public Iterator<WebProcessStatusModel> iterator(long first, long count) 
	{
		List<WebProcessStatusModel> newList = new ArrayList<WebProcessStatusModel>(webprocessStatusList);  
		Collections.sort(newList, comparator);  
		return newList.subList(Integer.parseInt(String.valueOf(first)), Integer.parseInt(String.valueOf(first + count))).iterator();  
	}

	@Override
	public IModel<WebProcessStatusModel> model(final Object object)
	{
		return new AbstractReadOnlyModel<WebProcessStatusModel>() 
		{  
			@Override  
			public WebProcessStatusModel getObject() 
			{  
				return (WebProcessStatusModel) object;  
			}  
		}; 
	}

	@Override
	public long size()
	{
		return processStatusList.size();	
	}
}