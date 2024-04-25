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
import com.bsva.commons.model.CnfgRejectReasonCodesModel;
import com.bsva.controller.Controller;
import com.bsva.models.WebRejectReasonCodesModel;
import com.bsva.translator.WebAdminTranslator;

public class RejectReasonProvider    extends SortableDataProvider  
{
	private static final long serialVersionUID = 1L;
	Controller controller;

	class SortableDataProviderComparator implements Comparator<WebRejectReasonCodesModel>, Serializable 
	{
		private static final long serialVersionUID = 1L;

		public int compare( final WebRejectReasonCodesModel o1,  final WebRejectReasonCodesModel o2) 
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
	
	private List<WebRejectReasonCodesModel> webRejectReasonCodesModelList;
	private List<CnfgRejectReasonCodesModel>rejectReasonCodesList;
	private SortableDataProviderComparator comparator = new SortableDataProviderComparator();

	public RejectReasonProvider () 
	{
		setSort("rejectReasonCode", SortOrder.ASCENDING);
		rejectReasonCodesList =(List<CnfgRejectReasonCodesModel>) controller.viewAllRejectReasonCodes();
		webRejectReasonCodesModelList = new ArrayList<WebRejectReasonCodesModel>();
		if (rejectReasonCodesList.size() > 0 ) 
		{
			for (CnfgRejectReasonCodesModel rejectReasonCodesModel : rejectReasonCodesList)
			{
				WebRejectReasonCodesModel  webRejectReasonCodesModel = new WebRejectReasonCodesModel();
				webRejectReasonCodesModel = new WebAdminTranslator().translateCommonsRejectReasonCodesToWebModel(rejectReasonCodesModel);
				webRejectReasonCodesModelList.add(webRejectReasonCodesModel);
			}
		}
	}
	
	public RejectReasonProvider (String rejectReasonCode)
	{
		setSort("rejectReasonCode", SortOrder.ASCENDING);
		rejectReasonCodesList = (List<CnfgRejectReasonCodesModel>) controller.viewRejectReasonByCriteria(rejectReasonCode);
		webRejectReasonCodesModelList = new ArrayList<WebRejectReasonCodesModel>();
		if(rejectReasonCodesList.size() > 0)
		{
			for (CnfgRejectReasonCodesModel localCommonModel : rejectReasonCodesList) 
			{
				WebRejectReasonCodesModel webModel = new WebRejectReasonCodesModel();
				webModel = new WebAdminTranslator().translateCommonsRejectReasonCodesToWebModel(localCommonModel);
				webRejectReasonCodesModelList.add(webModel);
			}
		}
	}

	@Override
	public Iterator<WebRejectReasonCodesModel> iterator(long first, long count)
	{
		List<WebRejectReasonCodesModel> newList = new ArrayList<WebRejectReasonCodesModel>(webRejectReasonCodesModelList);
		Collections.sort(newList, comparator);
		return newList.subList(Integer.parseInt(String.valueOf(first)), Integer.parseInt(String.valueOf(first + count))).iterator();
	}
	
	@Override
	public IModel<WebRejectReasonCodesModel> model(final Object object) 
	{
		return new AbstractReadOnlyModel<WebRejectReasonCodesModel>() 
		{
			private static final long serialVersionUID = 1L;

			@Override
			public WebRejectReasonCodesModel getObject() 
			{
				return (WebRejectReasonCodesModel) object;
			}

		};
	}
	
	@Override
	public long size() 
	{
		return rejectReasonCodesList.size();
	}
}