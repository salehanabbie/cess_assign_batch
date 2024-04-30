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

import com.bsva.commons.model.CnfgAuthTypeModel;
import com.bsva.controller.Controller;
import com.bsva.models.WebAuthTypeModel;
import com.bsva.translator.WebAdminTranslator;

public class AuthtypeProvider extends SortableDataProvider {

	Controller controller;	
	
	
	class SortableDataProviderComparator implements Comparator<WebAuthTypeModel>, Serializable 
	 {  
		   public int compare(final WebAuthTypeModel o1, final WebAuthTypeModel o2) 
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
	 
		private List<WebAuthTypeModel> webAuthTypeModelList;
		private List<CnfgAuthTypeModel> authTypeModelList;
		private SortableDataProviderComparator comparator = new SortableDataProviderComparator();
	
public AuthtypeProvider()
		
		{
			controller=new Controller();
			setSort("authType", SortOrder.ASCENDING);
			
			authTypeModelList = (List<CnfgAuthTypeModel>) controller.viewAllAuthTypes();
			webAuthTypeModelList = new ArrayList<WebAuthTypeModel>();

			//errorCodesList=new ArrayList<ConfgErrorCodesModel>();

			if(authTypeModelList.size() > 0)
			{
				for (CnfgAuthTypeModel  authTypeCommonModel : authTypeModelList) 
				{
					WebAuthTypeModel webModel = new WebAuthTypeModel();
						webModel = new WebAdminTranslator().translateCommonsAuthTypeModelToWebModel(authTypeCommonModel);
						webAuthTypeModelList.add(webModel);
				}
			}
		}



public AuthtypeProvider(String authType )
{
	
	controller=new Controller();
	setSort("authType", SortOrder.ASCENDING);
	authTypeModelList = (List<CnfgAuthTypeModel>) controller.viewAuthByCriteria(authType);
	webAuthTypeModelList = new ArrayList<WebAuthTypeModel>();

	if(authTypeModelList.size() > 0)
	{
		for (CnfgAuthTypeModel  authTypeCommonModel : authTypeModelList) 
		{
				WebAuthTypeModel  webModel = new WebAuthTypeModel();
				webModel = new WebAdminTranslator().translateCommonsAuthTypeModelToWebModel(authTypeCommonModel);
				webAuthTypeModelList.add(webModel);
		}
	}
}

@Override
public void detach() {
	// TODO Auto-generated method stub
	
}

@Override
public Iterator<WebAuthTypeModel> iterator(long first, long count) {
       // Get the data  
	  List<WebAuthTypeModel> newList = new ArrayList<WebAuthTypeModel>(webAuthTypeModelList);  
	  
	  // Sort the data  
	  Collections.sort(newList, comparator);  
	  
	  return newList.subList(Integer.parseInt(String.valueOf(first)), Integer.parseInt(String.valueOf(first + count))).iterator();  
}

@Override
public IModel<WebAuthTypeModel> model(final Object object) {
	return new AbstractReadOnlyModel<WebAuthTypeModel>() 
			{  
		   @Override  
		   public WebAuthTypeModel getObject() {  
		    return (WebAuthTypeModel) object;  
		   }  
		  }; 
}

@Override
public long size() {
	// TODO Auto-generated method stub
	return authTypeModelList.size();
	
}


}
