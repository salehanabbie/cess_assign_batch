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

import com.bsva.controller.Controller;
import com.bsva.models.WebMandateModel;
import com.bsva.models.WebOpsFileRegModel;

/**
 * 
 * @author DimakatsoN
 *
 */
public class ViewFileMandatesProvider extends SortableDataProvider {
	
	Controller controller;

	class SortableDataProviderComparator implements Comparator<WebMandateModel>, Serializable 
	{  
		   public int compare(final WebOpsFileRegModel o1, final WebOpsFileRegModel o2) 
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

		@Override
		public int compare(WebMandateModel arg0, WebMandateModel arg1) {
			// TODO Auto-generated method stub
			return 0;
		}  
	}

		private static final long serialVersionUID = 1L;
		private List<WebMandateModel> webMandatesList;
		
		private SortableDataProviderComparator comparator = new SortableDataProviderComparator();
		
		
		public ViewFileMandatesProvider(List<WebMandateModel> webMandatesList) 
		{	
			setSort("mandateId", SortOrder.ASCENDING);	
			this.webMandatesList = webMandatesList;
		}
		
		/*public ViewFileMandatesProvider()
		{
			controller = new Controller();
			("In the provider constructor");
			setSort("MandateId", SortOrder.ASCENDING);
			
			//mandatesList = (List<MandatesModel>) controller.viewAllFileMandates();
			webMandatesList = new ArrayList<WebMandateModel>();
		
			if(mandatesList.size() > 0)
			{
				for (MandatesModel mandateCommonModel : mandatesList) 
				{
					WebMandateModel webModel = new WebMandateModel();
						webModel = new WebAdminTranslator().translateCommonsMandateModelToWebModel(mandateCommonModel);
						webMandatesList.add(webModel);
				}
			}
		}*/
	
		
		@Override
		public void detach() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public Iterator<WebMandateModel> iterator(long first, long count) {
		       // Get the data  
			  List<WebMandateModel> newList = new ArrayList<WebMandateModel>(webMandatesList);  
			  
			  // Sort the data  
			  Collections.sort(newList, comparator);  
			  
			  return newList.subList(Integer.parseInt(String.valueOf(first)), Integer.parseInt(String.valueOf(first + count))).iterator();  
		}

		@Override
		public IModel<WebMandateModel> model(final Object object) {
			return new AbstractReadOnlyModel<WebMandateModel>() 
					{  
				   @Override  
				   public WebMandateModel getObject() {  
				    return (WebMandateModel) object;  
				   }  
				  }; 
		}

		@Override
		public long size() {
			// TODO Auto-generated method stub
			return webMandatesList.size();
			
		}


}
