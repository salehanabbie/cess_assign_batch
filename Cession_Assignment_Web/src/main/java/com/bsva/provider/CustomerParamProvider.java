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

import com.bsva.commons.model.CustomerParametersModel;
import com.bsva.controller.Controller;
import com.bsva.models.WebCustomerParametersModel;
import com.bsva.translator.WebAdminTranslator;

/**
 * @author salehas
 *
 */
public class CustomerParamProvider extends SortableDataProvider {
	Controller controller;
	
	 class SortableDataProviderComparator implements Comparator<WebCustomerParametersModel>, Serializable 
	 {  
		   public int compare(final WebCustomerParametersModel o1, final WebCustomerParametersModel o2) 
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
		private List<WebCustomerParametersModel> webcustParamsList;
		private List<CustomerParametersModel> custParamsList;
		private SortableDataProviderComparator comparator = new SortableDataProviderComparator();
		public static Logger log = Logger.getLogger(CustomerParamProvider.class);
		
		public CustomerParamProvider()
		{
			setSort("instId", SortOrder.ASCENDING);
		
			custParamsList = (List<CustomerParametersModel>) controller.viewAllCustomerParameters();
			webcustParamsList = new ArrayList<WebCustomerParametersModel>();
            //custParamsList=new ArrayList<CustomerParametersModel>();


			if(custParamsList.size() > 0)
			{
	
				for (CustomerParametersModel customerParametersModel : custParamsList) 
				{
						WebCustomerParametersModel webCustomerParametersModel = new WebCustomerParametersModel();
						webCustomerParametersModel = new WebAdminTranslator().translateCommonsCustomerParametersModelToWebModel(customerParametersModel);
						webcustParamsList.add(webCustomerParametersModel);
		
				}
			}
		}
		
		public CustomerParamProvider(String  instId)
		{
			setSort("instId", SortOrder.ASCENDING);
			custParamsList = (List<CustomerParametersModel>) controller.viewCustomerParametersByCriteria(instId);
			webcustParamsList = new ArrayList<WebCustomerParametersModel>();
			if(custParamsList.size() > 0)
			{
				for (CustomerParametersModel customerParametersModel : custParamsList) 
				{
						WebCustomerParametersModel webCustomerParametersModel = new WebCustomerParametersModel();
						webCustomerParametersModel = new WebAdminTranslator().translateCommonsCustomerParametersModelToWebModel(customerParametersModel);
						webcustParamsList.add(webCustomerParametersModel);
				}
			}
		}
		
		@Override
		public void detach() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public Iterator<WebCustomerParametersModel> iterator(long first, long count) 
		{
		       // Get the data  
			  List<WebCustomerParametersModel> newList = new ArrayList<WebCustomerParametersModel>(webcustParamsList);  
			  
			  // Sort the data  
			  Collections.sort(newList, comparator);  
			  
			  return newList.subList(Integer.parseInt(String.valueOf(first)), Integer.parseInt(String.valueOf(first + count))).iterator();  
		}

		@Override
		public IModel<WebCustomerParametersModel> model(final Object object) {
			return new AbstractReadOnlyModel<WebCustomerParametersModel>() 
					{  
				   @Override  
				   public WebCustomerParametersModel getObject() {  
				    return (WebCustomerParametersModel) object;  
				   }  
				  }; 
		}

		@Override
		public long size() {
			// TODO Auto-generated method stub
			return custParamsList.size();
			
		}
}
