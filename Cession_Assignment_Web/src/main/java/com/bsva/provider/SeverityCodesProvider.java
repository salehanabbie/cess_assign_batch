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
import com.bsva.commons.model.SeverityCodesModel;
import com.bsva.controller.Controller;
import com.bsva.models.WebAccountTypeModel;
import com.bsva.models.WebSeverityCodesModel;
import com.bsva.translator.WebAdminTranslator;

/**
 * 
 * @author NhlanhlaM
 *
 */
public class SeverityCodesProvider extends SortableDataProvider
{
	private static final long serialVersionUID = 1L;
	Controller controller; // = new Controller();
	
	class SortableDataProviderComparator implements Comparator<WebSeverityCodesModel>, Serializable 
	{
		private static final long serialVersionUID = 1L;
		public int compare(final WebSeverityCodesModel arg0, final WebSeverityCodesModel arg1)
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
	
	private List<WebSeverityCodesModel> webSeverityCodesModelList;
	private List<SeverityCodesModel> severityCodesModelList;
	private SortableDataProviderComparator comparator = new SortableDataProviderComparator();
	public static Logger log = Logger.getLogger(SeverityCodesProvider.class);
	
	public SeverityCodesProvider()
	{
		 controller = new Controller();
         setSort("severityCode", SortOrder.ASCENDING);
		
         severityCodesModelList = (List<SeverityCodesModel>) controller.viewAllSeverityCode();
         webSeverityCodesModelList = new ArrayList<WebSeverityCodesModel>();
	   
	    if(severityCodesModelList.size() > 0)
	    {
	    	for (SeverityCodesModel severityCodesModel : severityCodesModelList)
	    	{
	    		WebSeverityCodesModel webModel = new WebSeverityCodesModel();
	    		webModel = new WebAdminTranslator().translateCommonsSeverityCodesModelToWebModel(severityCodesModel);
	    		webSeverityCodesModelList.add(webModel);
	    	}
	    }
	}

	public  SeverityCodesProvider(String severityCode) 
	{
		log.info("severityCode in provider ===> "+severityCode);
		 controller = new Controller();
	     setSort("severityCode", SortOrder.ASCENDING);
	     severityCodesModelList = (List<SeverityCodesModel>)controller.viewSeverityCodesByCriteria(severityCode);
	     webSeverityCodesModelList = new ArrayList<WebSeverityCodesModel>();
   	     
   	  if(severityCodesModelList != null && severityCodesModelList.size() > 0);
   	  {
   			for (SeverityCodesModel localCommonModel : severityCodesModelList)
   		  {
   		  	WebSeverityCodesModel webModel = new WebSeverityCodesModel();
   			webModel = new WebAdminTranslator().translateCommonsSeverityCodesModelToWebModel(localCommonModel);
   			webSeverityCodesModelList.add(webModel);
   		  }
   	  	}
   	  
     } 
	
	
	
	@Override
	public void detach() 
	{
	 
	}

	@Override
 		public Iterator<WebSeverityCodesModel> iterator(long first, long count)
		{
		List<WebSeverityCodesModel> newList = new ArrayList<WebSeverityCodesModel>(webSeverityCodesModelList);
		Collections.sort(newList, comparator);
		return newList.subList(Integer.parseInt(String.valueOf(first)),Integer.parseInt(String.valueOf(first + count))).iterator();
		}

		@Override
		public IModel<WebSeverityCodesModel> model(final Object object)
		{

			return new AbstractReadOnlyModel<WebSeverityCodesModel>() 
			{
				private static final long serialVersionUID = 1L;

		@Override
		public WebSeverityCodesModel getObject() 
		{
			return (WebSeverityCodesModel) object;
		}

	};
}

	@Override
	public long size()
	{
		return webSeverityCodesModelList.size();
	}
}