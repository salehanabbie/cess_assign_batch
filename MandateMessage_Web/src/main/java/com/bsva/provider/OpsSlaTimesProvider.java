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

import com.bsva.commons.model.AmendmentCodesModel;
import com.bsva.commons.model.OpsSlaTimesCommonsModel;
import com.bsva.controller.Controller;
import com.bsva.models.WebAmendmentCodesModel;
import com.bsva.models.WebOpsSlaTimesModel;
import com.bsva.translator.WebAdminTranslator;

public class OpsSlaTimesProvider extends SortableDataProvider {
	
	/**
	 * @author ElelwaniR
	 */
	private static final long serialVersionUID = 1L;
	private List<WebOpsSlaTimesModel> webOpsSlaTimesModelList;
	private List<OpsSlaTimesCommonsModel> opsSlaTimesCommonsModelList = new ArrayList<OpsSlaTimesCommonsModel>();
	private SortableDataProviderComparator comparator = new SortableDataProviderComparator();
	public static Logger log = Logger.getLogger(OpsSlaTimesProvider.class);
	Controller controller;

	class SortableDataProviderComparator implements
		Comparator<WebOpsSlaTimesModel>, Serializable {

		private static final long serialVersionUID = 1L;
		
		public int compare(final WebOpsSlaTimesModel o1,final WebOpsSlaTimesModel o2) 
		{
			PropertyModel<Comparable> model1 = new PropertyModel<Comparable>(
					o1, getSort().getProperty().toString());
			PropertyModel<Comparable> model2 = new PropertyModel<Comparable>(
					o2, getSort().getProperty().toString());

			int result = model1.getObject().compareTo(model2.getObject());

			if (!getSort().isAscending()) {
				result = -result;
			}

			return result;
		}
	}

	public OpsSlaTimesProvider() {
		controller = new Controller();
		setSort("service", SortOrder.ASCENDING);
		opsSlaTimesCommonsModelList = (List<OpsSlaTimesCommonsModel>) controller.viewOpsSlaTimes();
		webOpsSlaTimesModelList = new ArrayList<WebOpsSlaTimesModel>();

		if (opsSlaTimesCommonsModelList.size() > 0) {
			
			for (OpsSlaTimesCommonsModel localCommonModel : opsSlaTimesCommonsModelList)

			{

				WebOpsSlaTimesModel webModel = new WebOpsSlaTimesModel();
				webModel = new WebAdminTranslator().translateOpsSlaTimesCommonsModelToWebModel(localCommonModel);
				webOpsSlaTimesModelList.add(webModel);
			}
		}
	}
	
	public  OpsSlaTimesProvider(String service) 
	{
			log.info("service--> "+service);
			controller=new Controller();
	     setSort("service", SortOrder.ASCENDING);
	     webOpsSlaTimesModelList = new ArrayList<WebOpsSlaTimesModel>();
	     opsSlaTimesCommonsModelList = (List<OpsSlaTimesCommonsModel>)controller.viewSlaTimesByCriteria(service);
	     log.info("opsSlaTimesCommonsModelList--> "+opsSlaTimesCommonsModelList);
	     
   	     if(opsSlaTimesCommonsModelList.size() > 0);
	   	  {
	   		  for (OpsSlaTimesCommonsModel localCommonModel : opsSlaTimesCommonsModelList)
	   		  {
	   			WebOpsSlaTimesModel webModel = new WebOpsSlaTimesModel();
	   			webModel = new WebAdminTranslator().translateOpsSlaTimesCommonsModelToWebModel(localCommonModel);
	   			webOpsSlaTimesModelList.add(webModel);
	   		  }
	   	  	}
     } 
	@Override
	public void detach() {
		// TODO Auto-generated method stub

	}

	@Override
	public Iterator<WebOpsSlaTimesModel> iterator(long first, long count) {
		// Get the data
		List<WebOpsSlaTimesModel> newList = new ArrayList<WebOpsSlaTimesModel>(webOpsSlaTimesModelList);

		// Sort the data
		Collections.sort(newList, comparator);

		return newList.subList(Integer.parseInt(String.valueOf(first)),
				Integer.parseInt(String.valueOf(first + count))).iterator();
	}

	@Override
	public IModel<WebOpsSlaTimesModel> model(final Object object) {
		return new AbstractReadOnlyModel<WebOpsSlaTimesModel>() {
			@Override
			public WebOpsSlaTimesModel getObject() {
				return (WebOpsSlaTimesModel) object;
			}
		};
	}

	@Override
	public long size()
	{

		return opsSlaTimesCommonsModelList.size();

	}


}
