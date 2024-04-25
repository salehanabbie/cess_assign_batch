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
import org.eclipse.jetty.util.log.Log;

import com.bsva.commons.model.SchedulerCommonsModel;
import com.bsva.commons.model.SystemParameterModel;
import com.bsva.controller.Controller;
import com.bsva.models.WebQuartzSchedulerModel;
import com.bsva.panels.schedulers.SchedulePanel;
import com.bsva.translator.WebAdminTranslator;

public class SchedulerProvider extends SortableDataProvider {
	

	Controller controller;
	public static Logger log = Logger.getLogger(SchedulerProvider.class);
//	public boolean sodRun = false;
	
	 class SortableDataProviderComparator implements Comparator<WebQuartzSchedulerModel>, Serializable 
	 {  
		   public int compare(final WebQuartzSchedulerModel o1, final WebQuartzSchedulerModel o2) 
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
		private List<WebQuartzSchedulerModel> webQuartzSchedulerModelList;
		private List<SchedulerCommonsModel> schedulerCommonsModelList;
		private SortableDataProviderComparator comparator = new SortableDataProviderComparator();
		
		public SchedulerProvider(boolean sodRun)
		{
//		     log.debug("in the provider ************************************");
		     controller = new Controller();
			setSort("schedulerKey", SortOrder.ASCENDING);
			SystemParameterModel systemParameterModel = (SystemParameterModel) controller.retrieveWebActiveSysParameter(); 
			log.debug("systemParameterModel : "+systemParameterModel);
			
			if(systemParameterModel != null && systemParameterModel.getActiveInd() != null)
			{
				if(systemParameterModel.getActiveInd().equalsIgnoreCase("Y"))
				{
					schedulerCommonsModelList = (List<SchedulerCommonsModel>) controller.viewAllOpsSchedulers();
				}
				else
				{
					schedulerCommonsModelList = (List<SchedulerCommonsModel>) controller.viewAllSchedulers();
				}
			}
			else
			{
				schedulerCommonsModelList = (List<SchedulerCommonsModel>) controller.viewAllSchedulers();
			}
			
//			System.out.println("schedulerCommonsModelList: "+schedulerCommonsModelList.toString());
			webQuartzSchedulerModelList = new ArrayList<WebQuartzSchedulerModel>();
			if(schedulerCommonsModelList.size() > 0)
			{
				for (SchedulerCommonsModel schedulerCommonsModel : schedulerCommonsModelList) 
				{
					WebQuartzSchedulerModel webModel = new WebQuartzSchedulerModel();
						webModel = new WebAdminTranslator().translateSchedulerCommonsModelToWebModel(schedulerCommonsModel);
						webQuartzSchedulerModelList.add(webModel);
				}
			}
		}
		
		
		
		@Override
		public void detach() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public Iterator<WebQuartzSchedulerModel> iterator(long first, long count) {
		       // Get the data  
			  List<WebQuartzSchedulerModel> newList = new ArrayList<WebQuartzSchedulerModel>(webQuartzSchedulerModelList);  
			  
			  // Sort the data  
			  Collections.sort(newList, comparator);  
			  
			  return newList.subList(Integer.parseInt(String.valueOf(first)), Integer.parseInt(String.valueOf(first + count))).iterator();  
		}

		@Override
		public IModel<WebQuartzSchedulerModel> model(final Object object) {
			return new AbstractReadOnlyModel<WebQuartzSchedulerModel>() 
					{  
				   @Override  
				   public WebQuartzSchedulerModel getObject() {  
				    return (WebQuartzSchedulerModel) object;  
				   }  
				  }; 
		}

		@Override
		public long size() {
			// TODO Auto-generated method stub
			return webQuartzSchedulerModelList.size();
			
		}

}
