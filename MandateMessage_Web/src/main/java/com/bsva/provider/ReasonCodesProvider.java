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
import com.bsva.commons.model.ReasonCodesModel;
import com.bsva.controller.Controller;
import com.bsva.models.WebReasonCodesModel;
import com.bsva.translator.WebAdminTranslator;

/**
 * @author salehas
 *
 */
public class ReasonCodesProvider extends SortableDataProvider
{
	Controller controller;
	
	class SortableDataProviderComparator implements Comparator<WebReasonCodesModel>, Serializable 
	{  
		public int compare(final WebReasonCodesModel o1, final WebReasonCodesModel o2) 
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
	private List<WebReasonCodesModel> webReasonCodesList;
	private List<ReasonCodesModel> reasonCodesList;
	private SortableDataProviderComparator comparator = new SortableDataProviderComparator();
			
	public ReasonCodesProvider()
	{
		setSort("reasonCode", SortOrder.ASCENDING);
		reasonCodesList = (List<ReasonCodesModel>) controller.viewAllReasonCodes();
		webReasonCodesList = new ArrayList<WebReasonCodesModel>();
		if(reasonCodesList.size() > 0)
		{
			for (ReasonCodesModel reasonCodesModel : reasonCodesList) 
			{
				WebReasonCodesModel webModel = new WebReasonCodesModel();
				webModel = new WebAdminTranslator().transalateCommonsReasonCodesModelToReasonWebModel(reasonCodesModel);
				webReasonCodesList.add(webModel);
			}
		}
	}
		
	public ReasonCodesProvider(String reasonCode )
	{
		setSort("reasonCode", SortOrder.ASCENDING);
		reasonCodesList = (List<ReasonCodesModel>) controller.viewReasonCodesByCriteria(reasonCode);
		webReasonCodesList = new ArrayList<WebReasonCodesModel>();
		if(reasonCodesList.size() > 0)
		{
			for (ReasonCodesModel localCommonModel : reasonCodesList) 
			{
				WebReasonCodesModel webModel = new WebReasonCodesModel();
				webModel = new WebAdminTranslator().transalateCommonsReasonCodesModelToReasonWebModel(localCommonModel);
				webReasonCodesList.add(webModel);
			}
		}
	}
		
	@Override
	public void detach()
	{	
	}

	@Override
	public Iterator<WebReasonCodesModel> iterator(long first, long count) 
	{
		List<WebReasonCodesModel> newList = new ArrayList<WebReasonCodesModel>(webReasonCodesList);  
		Collections.sort(newList, comparator);  	  
		return newList.subList(Integer.parseInt(String.valueOf(first)), Integer.parseInt(String.valueOf(first + count))).iterator();  
	}

	@Override
	public IModel<WebReasonCodesModel> model(final Object object)
	{
		return new AbstractReadOnlyModel<WebReasonCodesModel>() 
		{  
			@Override  
			public WebReasonCodesModel getObject()
			{  
				return (WebReasonCodesModel) object;  
			}  
		}; 
	}

	@Override
	public long size()
	{
		return reasonCodesList.size();		
	}

}