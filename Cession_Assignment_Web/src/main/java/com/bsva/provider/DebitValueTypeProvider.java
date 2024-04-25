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
import com.bsva.commons.model.DebitValueTypeModel;
import com.bsva.controller.Controller;
import com.bsva.models.WebDebitValueTypeModel;
import com.bsva.translator.WebAdminTranslator;

/**
 * @author nosiphos
 *
 */
public class DebitValueTypeProvider extends SortableDataProvider
{
	Controller controller;
	
	class SortableDataProviderComparator implements Comparator<WebDebitValueTypeModel>, Serializable 
	{  
		private static final long serialVersionUID = 1L;
		public int compare(final WebDebitValueTypeModel o1, final WebDebitValueTypeModel o2) 
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
	private List<WebDebitValueTypeModel> webDebitValueTypeList;
	private List<DebitValueTypeModel> debitValueTypeList;
	private SortableDataProviderComparator comparator = new SortableDataProviderComparator();
		
	public DebitValueTypeProvider()
	{
		setSort("debValueTypeCode", SortOrder.ASCENDING);	
		debitValueTypeList = (List<DebitValueTypeModel>) controller.viewAllDebitValueTypes();
		webDebitValueTypeList = new ArrayList<WebDebitValueTypeModel>();
		if(debitValueTypeList.size() > 0)
		{
			for (DebitValueTypeModel localCommonModel : debitValueTypeList) 
			{
					WebDebitValueTypeModel webModel = new WebDebitValueTypeModel();
					webModel = new WebAdminTranslator().translateCommonsDebitValueModelToWebModel(localCommonModel);
					webDebitValueTypeList.add(webModel);
			}
			}
		}
		
	public DebitValueTypeProvider(String debValueTypeCode)
	{
		setSort("debValueTypeCode", SortOrder.ASCENDING);	
		debitValueTypeList = (List<DebitValueTypeModel>) controller.viewDebitValueTypeByCriteria(debValueTypeCode);
		webDebitValueTypeList = new ArrayList<WebDebitValueTypeModel>();
		if(debitValueTypeList.size() > 0)
		{
			for (DebitValueTypeModel localCommonModel : debitValueTypeList) 
			{
				WebDebitValueTypeModel webModel = new WebDebitValueTypeModel();
				webModel = new WebAdminTranslator().translateCommonsDebitValueModelToWebModel(localCommonModel);
				webDebitValueTypeList.add(webModel);
			}
		}
	}

	@Override
	public void detach() 
	{	
	}

	@Override
	public Iterator<WebDebitValueTypeModel> iterator(long first, long count) 
	{
		List<WebDebitValueTypeModel> newList = new ArrayList<WebDebitValueTypeModel>(webDebitValueTypeList);   
		Collections.sort(newList, comparator);  
		return newList.subList(Integer.parseInt(String.valueOf(first)), Integer.parseInt(String.valueOf(first + count))).iterator();  
	}

	@Override
	public IModel<WebDebitValueTypeModel> model(final Object object)
	{
		return new AbstractReadOnlyModel<WebDebitValueTypeModel>() 
		{  
			@Override  
			public WebDebitValueTypeModel getObject() 
			{  
				return (WebDebitValueTypeModel) object;  
			}  
		}; 
	}

	@Override
	public long size() 
	{
		return debitValueTypeList.size();
			
	}
}