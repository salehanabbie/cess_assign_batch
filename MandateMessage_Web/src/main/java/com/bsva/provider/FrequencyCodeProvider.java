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
import com.bsva.commons.model.FrequencyCodesModel;
import com.bsva.controller.Controller;
import com.bsva.models.WebFrequencyCodesModel;
import com.bsva.translator.WebAdminTranslator;

/**
 * @author salehas
 *
 */
public class FrequencyCodeProvider extends SortableDataProvider
{
	Controller controller;
	class SortableDataProviderComparator implements Comparator<WebFrequencyCodesModel>, Serializable 
	{  
		public int compare(final WebFrequencyCodesModel o1, final WebFrequencyCodesModel o2) 
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
	 private List<WebFrequencyCodesModel> webFrequencyCodesList;
	 private List<FrequencyCodesModel> frequencyCodesList;
	 private SortableDataProviderComparator comparator = new SortableDataProviderComparator();
		
	 public FrequencyCodeProvider()
	 {
		 setSort("frequencyCode", SortOrder.ASCENDING);
			
		 frequencyCodesList = (List<FrequencyCodesModel>) controller.viewAllFrequencyCodes();
		 webFrequencyCodesList = new ArrayList<WebFrequencyCodesModel>();
		 if(frequencyCodesList.size() > 0)
		 {
			 for (FrequencyCodesModel localCommonModel : frequencyCodesList) 
			 {
				 WebFrequencyCodesModel webModel = new WebFrequencyCodesModel();
				 webModel = new WebAdminTranslator().translateCommonsFrequencyCodesModelToWebModel(localCommonModel);
				 webFrequencyCodesList.add(webModel);
			 }
		 }
	 }
		
	 public FrequencyCodeProvider(String frequencyCode)
	 {
		 setSort("frequencyCode", SortOrder.ASCENDING);
		 frequencyCodesList = (List<FrequencyCodesModel>) controller.viewFrequencyCodesByCriteria(frequencyCode);
		 webFrequencyCodesList = new ArrayList<WebFrequencyCodesModel>();
		 if(frequencyCodesList.size() > 0)
		 {
			 for (FrequencyCodesModel localCommonModel : frequencyCodesList) 
			 {
				 WebFrequencyCodesModel webModel = new WebFrequencyCodesModel();
				 webModel = new WebAdminTranslator().translateCommonsFrequencyCodesModelToWebModel(localCommonModel);
				 webFrequencyCodesList.add(webModel);
			 }
		 }
	 }
		
	@Override
	public void detach()
	{
	}

	@Override
	public Iterator<WebFrequencyCodesModel> iterator(long first, long count)
	{
		List<WebFrequencyCodesModel> newList = new ArrayList<WebFrequencyCodesModel>(webFrequencyCodesList);  
	    Collections.sort(newList, comparator);  
	    return newList.subList(Integer.parseInt(String.valueOf(first)), Integer.parseInt(String.valueOf(first + count))).iterator();  
	}

	@Override
	public IModel<WebFrequencyCodesModel> model(final Object object)
	{
		return new AbstractReadOnlyModel<WebFrequencyCodesModel>() 
		{  
			@Override  
			public WebFrequencyCodesModel getObject() 
			{  
				return (WebFrequencyCodesModel) object;  
		    }  
		}; 
	}

	@Override
	public long size() 
	{
		return frequencyCodesList.size();	
	}
	
}