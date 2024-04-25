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

import com.bsva.commons.model.AccountTypeModel;
import com.bsva.controller.Controller;
import com.bsva.models.WebAccountTypeModel;
import com.bsva.models.WebDwnldReqTypeModel;
import com.bsva.translator.WebAdminTranslator;

/**
 * 
 * @author NhlanhlaM
 *
 */

public class AccountTypeProvider extends  SortableDataProvider 
{
	private static final long serialVersionUID = 1L;
	Controller controller = new Controller();
	
	class SortableDataProviderComparator implements Comparator<WebAccountTypeModel>, Serializable 
	{
		private static final long serialVersionUID = 1L;
		public int compare(final WebAccountTypeModel arg0, final WebAccountTypeModel arg1) {
			
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
	
		private List<WebAccountTypeModel> webAccountTypeModelList;
		private List<AccountTypeModel> accountTypeModelList;
		private SortableDataProviderComparator comparator = new SortableDataProviderComparator();
		public static Logger log = Logger.getLogger(AccountTypeProvider.class);
	
	public AccountTypeProvider()
	{
         setSort("accountTypeCode", SortOrder.ASCENDING);
		
         accountTypeModelList = (List<AccountTypeModel>) controller.viewAllAccountType();
         webAccountTypeModelList = new ArrayList<WebAccountTypeModel>();
	   
		
	    if(accountTypeModelList.size() > 0)
	    {
	    	for (AccountTypeModel accountTypeModel : accountTypeModelList)
	    	{
	    		WebAccountTypeModel webModel = new WebAccountTypeModel();
	    		webModel = new WebAdminTranslator().translateCommonsAccountTypeModelToWebModel(accountTypeModel);
	    		webAccountTypeModelList.add(webModel);
	    	}
	    }
	}

	
	public  AccountTypeProvider(String accountTypeCode) 
	{
	     setSort("accountTypeCode", SortOrder.ASCENDING);
	     accountTypeModelList = (List<AccountTypeModel>)controller.viewAccountTypeByCriteria(accountTypeCode);
	     webAccountTypeModelList = new ArrayList<WebAccountTypeModel>();
   	     
   	     if(accountTypeModelList.size() > 0);
	   	  {
	   		  	for (AccountTypeModel localCommonModel : accountTypeModelList)
	   		  {
	   			WebAccountTypeModel webModel = new WebAccountTypeModel();
	   			webModel = new WebAdminTranslator().translateCommonsAccountTypeModelToWebModel(localCommonModel);
	   			webAccountTypeModelList.add(webModel);
	   		  }
	   	  	}
     } 

	@Override
	public Iterator<WebAccountTypeModel> iterator(long first, long count)
	{
		// Get the data
		List<WebAccountTypeModel> newList = new ArrayList<WebAccountTypeModel>(webAccountTypeModelList);
		// Sort the data
		Collections.sort(newList, comparator);

		return newList.subList(Integer.parseInt(String.valueOf(first)),
				Integer.parseInt(String.valueOf(first + count))).iterator();
	}

	public IModel<WebAccountTypeModel> model(final Object object) 
	{
			return new AbstractReadOnlyModel<WebAccountTypeModel>() {

			private static final long serialVersionUID = 1L;

			@Override
			public WebAccountTypeModel getObject() 
			{
				return (WebAccountTypeModel) object;
			}
		};
	}

	
	@Override
	public long size() 
	{	 
		return accountTypeModelList.size();
	}


}
