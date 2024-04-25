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

import com.bsva.commons.model.SystemParameterModel;
import com.bsva.controller.Controller;
import com.bsva.models.WebSystemParameterModel;
import com.bsva.translator.WebAdminTranslator;

public class SystemParametersProvider extends SortableDataProvider {

	Controller controller;

	class SortableDataProviderComparator implements Comparator<WebSystemParameterModel>, Serializable 
	{
		private static final long serialVersionUID = 1L;

		public int compare(final WebSystemParameterModel o1,final WebSystemParameterModel o2) 
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

	public List<WebSystemParameterModel> webSystemsParametersList;
	private List<SystemParameterModel> systemParametersList;
   private SortableDataProviderComparator comparator = new SortableDataProviderComparator();

	public SystemParametersProvider() 
	{
		controller = new Controller();
		setSort("sysName", SortOrder.ASCENDING);
		systemParametersList = new ArrayList<SystemParameterModel>();
		systemParametersList = (List<SystemParameterModel>) controller.viewAllSystemParameters();
		webSystemsParametersList = new ArrayList<WebSystemParameterModel>();

		if (systemParametersList.size() > 0) 
		{
			for (SystemParameterModel systemParameterCommonsModel : systemParametersList) 
			{
				WebSystemParameterModel webSystemParameterModel = new WebSystemParameterModel();
				webSystemParameterModel = new WebAdminTranslator().translateCommonsSystemParametersModelToWebModel(systemParameterCommonsModel);
				webSystemsParametersList.add(webSystemParameterModel);
			}
		}

	}

	public SystemParametersProvider(String sysName) 
	{
		controller = new Controller();
		setSort("sysName", SortOrder.ASCENDING);
		systemParametersList = (List<SystemParameterModel>) controller.viewSystemParametersByCriteria(sysName);
		webSystemsParametersList = new ArrayList<WebSystemParameterModel>();

		if (systemParametersList.size() > 0) {
			for (SystemParameterModel systemParametersCommonsModel : systemParametersList) {
				WebSystemParameterModel webSystemParametersModel = new WebSystemParameterModel();
				webSystemParametersModel = new WebAdminTranslator().translateCommonsSystemParametersModelToWebModel(systemParametersCommonsModel);
				webSystemsParametersList.add(webSystemParametersModel);
			}
		}

	}

	@Override 
	public void detach(){
		
	}
	
	@Override
	public Iterator<WebSystemParameterModel> iterator(long first, long count) {
		// Get the data
		List<WebSystemParameterModel> newList = new ArrayList<WebSystemParameterModel>(
				webSystemsParametersList);

		// Sort the data
		Collections.sort(newList, comparator);

		return newList.subList(Integer.parseInt(String.valueOf(first)),
				Integer.parseInt(String.valueOf(first + count))).iterator();
	}

	@Override
	public IModel<WebSystemParameterModel> model(final Object object) {

		return new AbstractReadOnlyModel<WebSystemParameterModel>() {

			private static final long serialVersionUID = 1L;

			@Override
			public WebSystemParameterModel getObject() {

				return (WebSystemParameterModel) object;
			}

		};
	}

	@Override
	public long size() {
		// TODO Auto-generated method stub
		return webSystemsParametersList.size();
	}

}
