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

import com.bsva.commons.model.SchedulerModel;
import com.bsva.controller.Controller;
import com.bsva.models.WebSchedulerModel;
import com.bsva.translator.WebAdminTranslator;

public class SchedulerDataProvider extends SortableDataProvider {
	
	Controller controller;

	class SortableDataProviderComparator implements Comparator<WebSchedulerModel>, Serializable 
	{  
		   public int compare(final WebSchedulerModel o1, final WebSchedulerModel o2) 
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
		private List<WebSchedulerModel> webSchedulerModelList;
		private List<SchedulerModel> schedulerModelList;
		private SortableDataProviderComparator comparator = new SortableDataProviderComparator();
		
		public SchedulerDataProvider()
		{
			controller = new Controller();
			
			setSort("scheduler", SortOrder.ASCENDING);
			
			schedulerModelList = new ArrayList<SchedulerModel>();
			
			schedulerModelList = (List<SchedulerModel>) controller.viewSchedulerExtract();
			webSchedulerModelList = new ArrayList<WebSchedulerModel>();
		
			if(schedulerModelList.size() > 0)
			{
				for (SchedulerModel schedulerModel : schedulerModelList) 
				{
					WebSchedulerModel webSchedulerModel = new WebSchedulerModel();
					webSchedulerModel = new WebAdminTranslator().translateCommonsSchedulerModelToWebModel(schedulerModel);
					webSchedulerModelList.add(webSchedulerModel);
				}
			}
		}
		
		@Override
		public void detach() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public Iterator<WebSchedulerModel> iterator(long first, long count) {
		       // Get the data  
			  List<WebSchedulerModel> newList = new ArrayList<WebSchedulerModel>(webSchedulerModelList);  
			  
			  // Sort the data  
			  Collections.sort(newList, comparator);  
			  
			  return newList.subList(Integer.parseInt(String.valueOf(first)), Integer.parseInt(String.valueOf(first + count))).iterator();  
		}

		@Override
		public IModel<WebSchedulerModel> model(final Object object) {
			return new AbstractReadOnlyModel<WebSchedulerModel>() 
					{  
				   @Override  
				   public WebSchedulerModel getObject() {  
				    return (WebSchedulerModel) object;  
				   }  
				  }; 
		}

		@Override
		public long size() {
			// TODO Auto-generated method stub
			return schedulerModelList.size();
			
		}



			 
		}

		
			 
