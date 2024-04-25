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
import com.bsva.commons.model.OutSotEotModel;
import com.bsva.controller.Controller;
import com.bsva.models.WebAcOpsSotEotModel;
import com.bsva.models.WebOutSotEotModel;
import com.bsva.provider.OpsAcSotEotProvider.SortableDataProviderComparator;
import com.bsva.translator.WebAdminTranslator;

public class OpsAcSotEotProvider extends SortableDataProvider
{
	private Controller controller;
	public static Logger log = Logger.getLogger(OpsAcSotEotProvider.class);
	private SortableDataProviderComparator comparator = new SortableDataProviderComparator();
	private List<WebOutSotEotModel> webSotEotModelList;
	private List<OutSotEotModel> sotEotCommonsModelList = new ArrayList<OutSotEotModel>();
	
			class SortableDataProviderComparator implements Comparator<WebOutSotEotModel>, Serializable
			{
				@Override
				public int compare(WebOutSotEotModel o1, WebOutSotEotModel o2) 
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
	
	public OpsAcSotEotProvider() 
	{
		controller = new Controller();
		setSort("serviceId", SortOrder.ASCENDING);
		sotEotCommonsModelList = (List<OutSotEotModel>) controller.retrieveAllOutSotEot();
		webSotEotModelList = new ArrayList<WebOutSotEotModel>();
		if(sotEotCommonsModelList.size() > 0)
		{
			for(OutSotEotModel localCommonModel : sotEotCommonsModelList)
			{
				WebOutSotEotModel webModel = new WebOutSotEotModel();
				webModel = new WebAdminTranslator().translateOutSotEotCommonsModelToWebModel(localCommonModel);
				webSotEotModelList.add(webModel);
			}
		}
	}
	
	public OpsAcSotEotProvider (String memberId, String serviceIdOut, String sotOutInd, String eotOutInd)
	{
		controller = new Controller();
		setSort("instId", SortOrder.ASCENDING);
		sotEotCommonsModelList = new ArrayList<OutSotEotModel>();
		sotEotCommonsModelList = (List<OutSotEotModel>) controller.retrieveAllOutSotEotBySearch(memberId, serviceIdOut, sotOutInd, eotOutInd);
		webSotEotModelList = new ArrayList<WebOutSotEotModel>();
		log.info("sotEotCommonsModelList 1 in PROVIDER ------->" + sotEotCommonsModelList);
		if(sotEotCommonsModelList.size() > 0)
		{
			for(OutSotEotModel localCommonModel : sotEotCommonsModelList)
			{
				WebOutSotEotModel webModel = new WebOutSotEotModel();
				webModel = new WebAdminTranslator().translateOutSotEotCommonsModelToWebModel(localCommonModel);
				webSotEotModelList.add(webModel);
			}
		}
	}
	
	public  OpsAcSotEotProvider(String namedQuery, String parameter,String value) 
	{
		controller = new Controller();
		setSort("instId", SortOrder.ASCENDING);
		sotEotCommonsModelList = (List<OutSotEotModel>) controller.findByServiceIdNotNull(controller.getProperty("SERVICE_OUT.NQ"),"serviceIdOut", "IGNORE" );//IGNORE is being set as there is no 'value'
		webSotEotModelList = new ArrayList<WebOutSotEotModel>();
		if(sotEotCommonsModelList.size() > 0)
		{
			for (OutSotEotModel localCommonModel : sotEotCommonsModelList) 
			{
				WebOutSotEotModel webModel = new WebOutSotEotModel();
				webModel = new WebAdminTranslator().translateOutSotEotCommonsModelToWebModel(localCommonModel);
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
		List<WebOutSotEotModel> newList = new ArrayList<WebOutSotEotModel>(webSotEotModelList);
		Collections.sort(newList, comparator);
		return newList.subList(Integer.parseInt(String.valueOf(first)),Integer.parseInt(String.valueOf(first + count))).iterator();
	}

	@Override
	public IModel<WebOutSotEotModel> model(final Object object) 
	{
		return new AbstractReadOnlyModel<WebOutSotEotModel>()
		{
			@Override
			public WebOutSotEotModel getObject()
			{
				return (WebOutSotEotModel) object;
			}
		};
	}

	@Override
	public long size() 
	{
		return sotEotCommonsModelList.size();
	}

}