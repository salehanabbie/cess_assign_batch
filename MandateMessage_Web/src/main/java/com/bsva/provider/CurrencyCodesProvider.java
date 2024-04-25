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

import com.bsva.commons.model.CurrencyCodesModel;
import com.bsva.controller.Controller;
import com.bsva.models.WebCurrencyCodesModel;
import com.bsva.translator.WebAdminTranslator;

public class CurrencyCodesProvider extends SortableDataProvider 
{
	
	private static final long serialVersionUID = 1L;
	Controller controller;

	class SortableDataProviderComparator implements Comparator<WebCurrencyCodesModel>, Serializable {

		private static final long serialVersionUID = 1L;

		public int compare( final WebCurrencyCodesModel o1,  final WebCurrencyCodesModel o2) {
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
	
	
	
	private List<WebCurrencyCodesModel> webCurrencyCodesList;
	private List<CurrencyCodesModel> currencyCodesList;
	private SortableDataProviderComparator comparator = new SortableDataProviderComparator();

	public CurrencyCodesProvider() 
	{
		setSort("countryCode", SortOrder.ASCENDING);
		currencyCodesList =(List<CurrencyCodesModel>) controller.viewAllCurrencyCodes();
		webCurrencyCodesList = new ArrayList<WebCurrencyCodesModel>();
		//currencyCodesList=new ArrayList<CurrencyCodesModel>();

		if (currencyCodesList.size() > 0 ) 
		{
			for (CurrencyCodesModel currencyCommonsModel : currencyCodesList)
			{
				WebCurrencyCodesModel webCurrencyModel = new WebCurrencyCodesModel();
				webCurrencyModel = new WebAdminTranslator().translateCommonsCurrencyCodesModelToWebModel(currencyCommonsModel);
				webCurrencyCodesList.add(webCurrencyModel);
			}
		}
		
	}
	
	public CurrencyCodesProvider(String countryCode)
	{
		setSort("countryCode", SortOrder.ASCENDING);
		currencyCodesList = (List<CurrencyCodesModel>) controller.viewCurrencyCodesByCriteria(countryCode);
		webCurrencyCodesList = new ArrayList<WebCurrencyCodesModel>();
		if(currencyCodesList.size() > 0)
		{
			for (CurrencyCodesModel localCommonModel : currencyCodesList) 
			{
				WebCurrencyCodesModel webModel = new WebCurrencyCodesModel();
					webModel = new WebAdminTranslator().translateCommonsCurrencyCodesModelToWebModel(localCommonModel);
					webCurrencyCodesList.add(webModel);
			}
		}
	}

	
	
		

	@Override
	public Iterator<WebCurrencyCodesModel> iterator(long first, long count) {
		// Get the data
		List<WebCurrencyCodesModel> newList = new ArrayList<WebCurrencyCodesModel>(webCurrencyCodesList);

		// Sort the data
		Collections.sort(newList, comparator);

		return newList.subList(Integer.parseInt(String.valueOf(first)),
				Integer.parseInt(String.valueOf(first + count))).iterator();
	}
	@Override
	public IModel<WebCurrencyCodesModel> model(final Object object) {

		return new AbstractReadOnlyModel<WebCurrencyCodesModel>() {

	/**
	 * 
	 */
			private static final long serialVersionUID = 1L;

			@Override
			public WebCurrencyCodesModel getObject() {

				return (WebCurrencyCodesModel) object;
			}

		};
	}
	
	@Override
	public long size() {
		// TODO Auto-generated method stub
		return currencyCodesList.size();
	}

	

}
