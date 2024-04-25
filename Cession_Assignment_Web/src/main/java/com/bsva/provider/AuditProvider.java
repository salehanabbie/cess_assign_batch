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

import com.bsva.commons.model.AuditTrackingModel;
import com.bsva.controller.Controller;
import com.bsva.models.WebAuditTrackingModel;
import com.bsva.panels.debitValueType.ViewDebitValueTypePanel;
import com.bsva.translator.WebAdminTranslator;

/**
 * @author nosiphos
 *
 */
public class AuditProvider extends SortableDataProvider
{
	
	Controller controller;
	public static Logger log = Logger.getLogger(AuditProvider.class);
	
	 class SortableDataProviderComparator implements Comparator<WebAuditTrackingModel>, Serializable 
	 {  
		
		public int compare(final WebAuditTrackingModel o1, final WebAuditTrackingModel o2) 
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
		private List<WebAuditTrackingModel> webAuditTrackingList;
		private List<AuditTrackingModel> auditTrackingList;
		private SortableDataProviderComparator comparator = new SortableDataProviderComparator();
		

		
		public AuditProvider()
		{
			setSort("tableName", SortOrder.ASCENDING);
			
			auditTrackingList = (List<AuditTrackingModel>) controller.viewAllAuditTables();
			webAuditTrackingList = new ArrayList<WebAuditTrackingModel>();
			
			if(auditTrackingList.size() > 0)
			{
				for (AuditTrackingModel auditTrackingModel : auditTrackingList) 
				{
					WebAuditTrackingModel webAuditTrackingModel = new WebAuditTrackingModel();
					webAuditTrackingModel = new WebAdminTranslator().translateCommonsAuditTrackingModelToWebModel(auditTrackingModel);
					webAuditTrackingList.add(webAuditTrackingModel);
				}
			}
		}
		
		
		public AuditProvider(String tableName)
		{
			controller=new Controller();
			setSort("tableName", SortOrder.ASCENDING);
			
			auditTrackingList = (List<AuditTrackingModel>) controller.viewAuditTrackingCriteria(tableName);
			log.info("auditTrackingList***********************"+auditTrackingList);
			webAuditTrackingList = new ArrayList<WebAuditTrackingModel>();
			
			if(auditTrackingList.size() > 0)
			{
				for (AuditTrackingModel auditTrackingModel : auditTrackingList) 
				{
					WebAuditTrackingModel webModel = new WebAuditTrackingModel();
						webModel = new WebAdminTranslator().translateCommonsAuditTrackingModelToWebModel(auditTrackingModel);
						webAuditTrackingList.add(webModel);
						log.info("webAuditTrackingList***********************"+webAuditTrackingList);
						
				}
			}
		}
		
		@Override
		public void detach() {
			// TODO Auto-generated method stub
			
		}
		

		@Override
		public Iterator<WebAuditTrackingModel> iterator(long first, long count) {
		       // Get the data  
			  List<WebAuditTrackingModel> newList = new ArrayList<WebAuditTrackingModel>(webAuditTrackingList);  
			  
			  // Sort the data  
			  Collections.sort(newList, comparator);  
			  
			  return newList.subList(Integer.parseInt(String.valueOf(first)), Integer.parseInt(String.valueOf(first + count))).iterator();  
		}
		@Override
		public IModel<WebAuditTrackingModel> model(final Object object) {
			return new AbstractReadOnlyModel<WebAuditTrackingModel>() 
					{  
				   @Override  
				   public WebAuditTrackingModel getObject() {  
				    return (WebAuditTrackingModel) object;  
				   
				   }  
				   
				  }; 
		     }
		

		@Override
		public long size() {
			
			return auditTrackingList.size();
			
		}
	 

}
