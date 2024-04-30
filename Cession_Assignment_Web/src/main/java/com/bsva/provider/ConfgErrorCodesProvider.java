package com.bsva.provider;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import com.bsva.commons.model.ConfgErrorCodesModel;
import com.bsva.controller.Controller;
import com.bsva.models.WebConfgErrorCodesModel;
import com.bsva.translator.WebAdminTranslator;

/**
 * 
 * @author DimakatsoN
 *
 */
public class ConfgErrorCodesProvider extends SortableDataProvider
{
	Controller controller;
	
	 class SortableDataProviderComparator implements Comparator<WebConfgErrorCodesModel>, Serializable 
	 {  
		   public int compare(final WebConfgErrorCodesModel o1, final WebConfgErrorCodesModel o2) 
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
		private List<WebConfgErrorCodesModel> webErrorCodesList;
		private List<ConfgErrorCodesModel> errorCodesList;
		private SortableDataProviderComparator comparator = new SortableDataProviderComparator();
		
		public ConfgErrorCodesProvider()
		{
			controller=new Controller();
			setSort("errorCode", SortOrder.ASCENDING);	
			errorCodesList = (List<ConfgErrorCodesModel>) controller.viewAllErrorCodes(true);
			webErrorCodesList = new ArrayList<WebConfgErrorCodesModel>();
			if(errorCodesList.size() > 0)
			{
				for (ConfgErrorCodesModel errorCommonModel : errorCodesList) 
				{
					WebConfgErrorCodesModel webModel = new WebConfgErrorCodesModel();
					webModel = new WebAdminTranslator().translateCommonsErrorCodesModelToWebModel(errorCommonModel);
					webErrorCodesList.add(webModel);
				}
			}
		}
		
		public ConfgErrorCodesProvider(String errorCode )
		{
			controller=new Controller();
			setSort("errorCode", SortOrder.ASCENDING);
			errorCodesList = (List<ConfgErrorCodesModel>) controller.viewErrorCodesByCriteria(errorCode);
			webErrorCodesList = new ArrayList<WebConfgErrorCodesModel>();
			if(errorCodesList.size() > 0)
			{
				for (ConfgErrorCodesModel errorCommonModel : errorCodesList) 
				{
					WebConfgErrorCodesModel webModel = new WebConfgErrorCodesModel();
					webModel = new WebAdminTranslator().translateCommonsErrorCodesModelToWebModel(errorCommonModel);
					webErrorCodesList.add(webModel);
				}
			}
		}
		
		@Override
		public void detach() 
		{
		}

		@Override
		public Iterator<WebConfgErrorCodesModel> iterator(long first, long count) 
		{ 
			 List<WebConfgErrorCodesModel> newList = new ArrayList<WebConfgErrorCodesModel>(webErrorCodesList);   
			 Collections.sort(newList, comparator);  
			 return newList.subList(Integer.parseInt(String.valueOf(first)), Integer.parseInt(String.valueOf(first + count))).iterator();  
		}

		@Override
		public IModel<WebConfgErrorCodesModel> model(final Object object) 
		{
			return new AbstractReadOnlyModel<WebConfgErrorCodesModel>() 
			{  
				  @Override  
				  public WebConfgErrorCodesModel getObject()
				  {  
					  return (WebConfgErrorCodesModel) object;  
				  }  
			}; 
		}

		@Override
		public long size()
		{
			return errorCodesList.size();
		}
}