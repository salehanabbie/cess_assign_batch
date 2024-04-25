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
import com.bsva.commons.model.StatusReasonCodesModel;
import com.bsva.controller.Controller;
import com.bsva.models.WebStatusReasonCodesModel;
import com.bsva.translator.WebAdminTranslator;

/**
 * 
 * @author NhlanhlaM
 *
 */
public class StatusReasonCodesProvider extends SortableDataProvider 
{
	private static final long serialVersionUID = 1L;
	Controller controller = new Controller();
	
	class SortableDataProviderComparator implements Comparator<WebStatusReasonCodesModel>, Serializable 
	{
		private static final long serialVersionUID = 1L;
		public int compare(final WebStatusReasonCodesModel arg0, final WebStatusReasonCodesModel arg1) 
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
	
	private List<WebStatusReasonCodesModel> webStatusReasonCodesList;
	private List<StatusReasonCodesModel> statusReasonCodesModelList;
	private SortableDataProviderComparator comparator = new SortableDataProviderComparator();
	public static Logger log = Logger.getLogger(StatusReasonCodesProvider.class);
	
	public StatusReasonCodesProvider()
	{
         setSort("statusReasonCode", SortOrder.ASCENDING);
         statusReasonCodesModelList = (List<StatusReasonCodesModel>) controller.viewAllStatusReasonCodes();
         webStatusReasonCodesList = new ArrayList<WebStatusReasonCodesModel>();
         if(statusReasonCodesModelList.size() > 0)
         {
        	 for (StatusReasonCodesModel statusReasonCodesModel : statusReasonCodesModelList)
        	 {
        		 WebStatusReasonCodesModel webModel = new WebStatusReasonCodesModel();
        		 webModel = new WebAdminTranslator().translateCommonsStatusReasonCodesModelToWebModel(statusReasonCodesModel);
        		 webStatusReasonCodesList.add(webModel);
        	 }
         }
	}
	
	public  StatusReasonCodesProvider(String statusReasonCode) 
	{
	     setSort("statusReasonCode", SortOrder.ASCENDING);
	     statusReasonCodesModelList = (List<StatusReasonCodesModel>)controller.viewStatusReasonCodesByCriteria(statusReasonCode);
	     webStatusReasonCodesList = new ArrayList<WebStatusReasonCodesModel>();
   	     if(statusReasonCodesModelList.size() > 0);
   	     {
   		  	for (StatusReasonCodesModel localCommonModel : statusReasonCodesModelList)
   		  	{
   		  		WebStatusReasonCodesModel webModel = new WebStatusReasonCodesModel();
   		  		webModel = new WebAdminTranslator().translateCommonsStatusReasonCodesModelToWebModel(localCommonModel);
   		  		webStatusReasonCodesList.add(webModel);
   		  	}
   	  	}  
     } 
	
	 @Override
	 public void detach() 
	 { 
	 }
	
	@Override
	public Iterator<WebStatusReasonCodesModel> iterator(long first, long count)
	{
		List<WebStatusReasonCodesModel> newList = new ArrayList<WebStatusReasonCodesModel>(webStatusReasonCodesList);
		Collections.sort(newList, comparator);
		return newList.subList(Integer.parseInt(String.valueOf(first)),Integer.parseInt(String.valueOf(first + count))).iterator();
	}
	
	public IModel<WebStatusReasonCodesModel> model(final Object object) 
	{
		return new AbstractReadOnlyModel<WebStatusReasonCodesModel>() 
		{
			private static final long serialVersionUID = 1L;

			@Override
			public WebStatusReasonCodesModel getObject() 
			{
				return (WebStatusReasonCodesModel) object;
			}
		};
	}

	@Override
	public long size()
	{
		return statusReasonCodesModelList.size();
	}
}
