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

import com.bsva.commons.model.AcOpsSotEotCntrlModel;
import com.bsva.commons.model.IncSotEotModel;
import com.bsva.controller.Controller;
import com.bsva.models.WebAcOpsSotEotModel;
import com.bsva.models.WebIncSotEotModel;
import com.bsva.provider.OpsAcSotEotProvider.SortableDataProviderComparator;
import com.bsva.translator.WebAdminTranslator;

public class IncomingSotEotProvider extends SortableDataProvider
{
	private Controller controller;
	public static Logger log = Logger.getLogger(OpsAcSotEotProvider.class);
	private SortableDataProviderComparator comparator = new SortableDataProviderComparator();
	private List<WebIncSotEotModel> webSotEotModelList;
	private List<IncSotEotModel> sotEotCommonsModelList = new ArrayList<IncSotEotModel>();
	
			class SortableDataProviderComparator implements Comparator<WebIncSotEotModel>, Serializable
			{
				@Override
				public int compare(WebIncSotEotModel o1, WebIncSotEotModel o2) 
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
	
	public IncomingSotEotProvider() 
	{
		controller = new Controller();
		setSort("serviceId", SortOrder.ASCENDING);
		sotEotCommonsModelList = (List<IncSotEotModel>) controller.retrieveAllIncSotEot();
		webSotEotModelList = new ArrayList<WebIncSotEotModel>();
		if(sotEotCommonsModelList.size() > 0)
		{
			for(IncSotEotModel localCommonModel : sotEotCommonsModelList)
			{
				WebIncSotEotModel webModel = new WebIncSotEotModel();
				webModel = new WebAdminTranslator().translateIncSotEotCommonsModelToWebModel(localCommonModel);
				webSotEotModelList.add(webModel);
			}
		}
	}
	
	public IncomingSotEotProvider (String memberId, String serviceIdIn, String sotInInd, String eotInInd)
	{
		controller = new Controller();
		setSort("instId", SortOrder.ASCENDING);
		sotEotCommonsModelList = new ArrayList<IncSotEotModel>();
		sotEotCommonsModelList = (List<IncSotEotModel>) controller.retrieveAllIncSotEotBySearch(memberId, serviceIdIn, sotInInd, eotInInd);
		webSotEotModelList = new ArrayList<WebIncSotEotModel>();
		log.info("sotEotCommonsModelList 1 in PROVIDER ------->" + sotEotCommonsModelList);
		if(sotEotCommonsModelList.size() > 0)
		{
			for(IncSotEotModel localCommonModel : sotEotCommonsModelList)
			{
				WebIncSotEotModel webModel = new WebIncSotEotModel();
				webModel = new WebAdminTranslator().translateIncSotEotCommonsModelToWebModel(localCommonModel);
				webSotEotModelList.add(webModel);
			}
		}
	}
	
	public  IncomingSotEotProvider(String namedQuery, String parameter,String value) 
	{
		controller = new Controller();
		setSort("instId", SortOrder.ASCENDING);
		sotEotCommonsModelList = (List<IncSotEotModel>) controller.findByServiceIdNotNull(controller.getProperty("SERVICE_IN.NQ"),"serviceIdIn", "IGNORE" );//IGNORE is being set as there is no 'value'
		webSotEotModelList = new ArrayList<WebIncSotEotModel>();
		if(sotEotCommonsModelList.size() > 0)
		{
			for (IncSotEotModel localCommonModel : sotEotCommonsModelList) 
			{
				WebIncSotEotModel webModel = new WebIncSotEotModel();
				webModel = new WebAdminTranslator().translateIncSotEotCommonsModelToWebModel(localCommonModel);
				webSotEotModelList.add(webModel);
			}
		}
	}
	
	@Override
	public void detach()
	{
	}
	
	@Override
	public Iterator iterator(long first, long count) 
	{
		List<WebIncSotEotModel> newList = new ArrayList<WebIncSotEotModel>(webSotEotModelList);
		Collections.sort(newList, comparator);
		return newList.subList(Integer.parseInt(String.valueOf(first)),Integer.parseInt(String.valueOf(first + count))).iterator();
	}

	@Override
	public IModel<WebIncSotEotModel> model(final Object object) 
	{
		return new AbstractReadOnlyModel<WebIncSotEotModel>()
		{
			@Override
			public WebIncSotEotModel getObject()
			{
				return (WebIncSotEotModel) object;
			}
		};
	}

	@Override
	public long size() 
	{
		return sotEotCommonsModelList.size();
	}

}
