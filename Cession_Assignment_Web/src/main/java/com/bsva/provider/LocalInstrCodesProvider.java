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
import com.bsva.commons.model.LocalInstrumentCodesModel;
import com.bsva.controller.Controller;
import com.bsva.models.WebLocalInstrumentCodesModel;
import com.bsva.translator.WebAdminTranslator;

/**
 * @author salehas
 *
 */
public class LocalInstrCodesProvider extends SortableDataProvider
{
	Controller controller;
	
	 class SortableDataProviderComparator implements Comparator<WebLocalInstrumentCodesModel>, Serializable 
	 {  
		 public int compare(final WebLocalInstrumentCodesModel o1, final WebLocalInstrumentCodesModel o2) 
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
	private List<WebLocalInstrumentCodesModel> weblocalInstrCodesList;
	private List<LocalInstrumentCodesModel> localInstrCodesList;
	private SortableDataProviderComparator comparator = new SortableDataProviderComparator();
		
	public LocalInstrCodesProvider()
	{
		setSort("localInstrumentCode", SortOrder.ASCENDING);
		localInstrCodesList = (List<LocalInstrumentCodesModel>) controller.viewAllLocalInstrumentCodes();
		weblocalInstrCodesList = new ArrayList<WebLocalInstrumentCodesModel>();
		if(localInstrCodesList.size() > 0)
		{
			for (LocalInstrumentCodesModel localCommonModel : localInstrCodesList) 
			{
				WebLocalInstrumentCodesModel webModel = new WebLocalInstrumentCodesModel();
				webModel = new WebAdminTranslator().translateCommonsInstrumentCodesModelToWebModel(localCommonModel);
				weblocalInstrCodesList.add(webModel);
			}
		}
	}
		
	public LocalInstrCodesProvider(String localInstCode )
	{
		controller=new Controller();
		setSort("localInstrumentCode", SortOrder.ASCENDING);
		localInstrCodesList = (List<LocalInstrumentCodesModel>) controller.viewLocalInstrumentCodesByCriteria(localInstCode);
		weblocalInstrCodesList = new ArrayList<WebLocalInstrumentCodesModel>();
		if(localInstrCodesList.size() > 0)
		{
			for (LocalInstrumentCodesModel localCommonModel : localInstrCodesList) 
			{
					WebLocalInstrumentCodesModel webModel = new WebLocalInstrumentCodesModel();
					webModel = new WebAdminTranslator().translateCommonsInstrumentCodesModelToWebModel(localCommonModel);
					weblocalInstrCodesList.add(webModel);
			}
		}
	}
		
	@Override
	public void detach()
	{	
	}

	@Override
	public Iterator<WebLocalInstrumentCodesModel> iterator(long first, long count)
	{ 
		List<WebLocalInstrumentCodesModel> newList = new ArrayList<WebLocalInstrumentCodesModel>(weblocalInstrCodesList);  
		Collections.sort(newList, comparator);  
		return newList.subList(Integer.parseInt(String.valueOf(first)), Integer.parseInt(String.valueOf(first + count))).iterator();  
	}

	@Override
	public IModel<WebLocalInstrumentCodesModel> model(final Object object) 
	{
		return new AbstractReadOnlyModel<WebLocalInstrumentCodesModel>() 
		{  
			@Override  
			public WebLocalInstrumentCodesModel getObject() 
			{  
				return (WebLocalInstrumentCodesModel) object;  
			}  
		}; 
	}

	@Override
	public long size() 
	{
		return localInstrCodesList.size();	
	}

}