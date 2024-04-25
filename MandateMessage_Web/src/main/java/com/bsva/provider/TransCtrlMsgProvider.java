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


import com.bsva.commons.model.TransCtrlMsgModel;
import com.bsva.controller.Controller;
import com.bsva.models.WebTransCtrlMsgModel;
import com.bsva.translator.WebAdminTranslator;

/**
 * 
 * @author DimakatsoN
 *
 */

public class TransCtrlMsgProvider extends SortableDataProvider{
	
	Controller controller;
	
	
	class SortableDataProviderComparator implements Comparator<WebTransCtrlMsgModel>, Serializable 
	{  
		   public int compare(final WebTransCtrlMsgModel o1, final WebTransCtrlMsgModel o2) 
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
	private List<WebTransCtrlMsgModel> webTransCtrlMsgModelList;
	private List<TransCtrlMsgModel> transCtrlMsgModelList;
	private SortableDataProviderComparator comparator = new SortableDataProviderComparator();
	public static Logger log = Logger.getLogger(TransCtrlMsgProvider.class);
	
	public TransCtrlMsgProvider()
	{
		controller = new Controller();
		setSort("memberIdFm", SortOrder.ASCENDING);
		transCtrlMsgModelList = new ArrayList<TransCtrlMsgModel>();
		transCtrlMsgModelList = (List<TransCtrlMsgModel>)controller.viewEotMessages();
		webTransCtrlMsgModelList = new ArrayList<WebTransCtrlMsgModel>();
		
		if(transCtrlMsgModelList.size()> 0)
		{
			for(TransCtrlMsgModel transCtrlMsgModel : transCtrlMsgModelList)
			{
				WebTransCtrlMsgModel webTransCtrlMsgModel = new WebTransCtrlMsgModel();
				webTransCtrlMsgModel = new WebAdminTranslator().translateTransCtrlMsgModelToWebModel(transCtrlMsgModel);
				webTransCtrlMsgModelList.add(webTransCtrlMsgModel);
			}
		}
	}
		


	@Override
	public Iterator<WebTransCtrlMsgModel> iterator(long first, long count) {
		List<WebTransCtrlMsgModel> newList = new ArrayList<WebTransCtrlMsgModel>(webTransCtrlMsgModelList);  
		  
		  // Sort the data  
		  Collections.sort(newList, comparator);  
		  
		  return newList.subList(Integer.parseInt(String.valueOf(first)), Integer.parseInt(String.valueOf(first + count))).iterator();  
	}

	@Override
	public IModel<WebTransCtrlMsgModel> model(final Object object) {
		return new AbstractReadOnlyModel<WebTransCtrlMsgModel>() 
				{  
			   @Override  
			   public WebTransCtrlMsgModel getObject() {  
			    return (WebTransCtrlMsgModel) object;  
			   }  
			  }; 
	}


	@Override
	public long size() {
		// TODO Auto-generated method stub
		return transCtrlMsgModelList.size();
		
	}


}
