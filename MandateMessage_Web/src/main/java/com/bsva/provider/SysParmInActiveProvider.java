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

import com.bsva.commons.model.SystemParameterModel;
import com.bsva.controller.Controller;
import com.bsva.models.WebSystemParameterModel;
import com.bsva.translator.WebAdminTranslator;

/**
 * 
 * @author NhlanhlaM
 *
 */

public class SysParmInActiveProvider extends SortableDataProvider 
{	
	
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	
	Controller controller = new Controller();
	class SortableDataProviderComparator implements Comparator<WebSystemParameterModel>, Serializable 
	{
		private static final long serialVersionUID = 1L;
		public int compare (final WebSystemParameterModel o1, final WebSystemParameterModel o2)
		{
			PropertyModel<Comparable> model1 = new PropertyModel<Comparable>(o1, getSort().getProperty().toString());
			PropertyModel<Comparable> model2 = new PropertyModel<Comparable>(o1, getSort().getProperty().toString());
			
			int result = model1.getObject().compareTo(model2.getObject());
			
			if (!getSort().isAscending())
			{
				result = -result;
			}
			
			return result;
		}
	}
	
	private List<WebSystemParameterModel> websystemParametersModelList;
	private List<SystemParameterModel> systemParameterModelList;
	private SortableDataProviderComparator comparator = new SortableDataProviderComparator();
	public Logger log = Logger.getLogger(SysParmActiveProvider.class);
	
	public SysParmInActiveProvider() 
	{
		controller = new Controller();
		setSort("processDate", SortOrder.ASCENDING);
		systemParameterModelList = (List<SystemParameterModel>) controller.retrieveAllInActiveInd();
		websystemParametersModelList = new ArrayList<WebSystemParameterModel>();

		if (systemParameterModelList.size() > 0) 
		{
			for (SystemParameterModel systemParametersCommonsModel : systemParameterModelList) 
			{
				WebSystemParameterModel webSystemParametersModel = new WebSystemParameterModel();
				webSystemParametersModel = new WebAdminTranslator().translateCommonsSystemParametersModelToWebModel(systemParametersCommonsModel);
				websystemParametersModelList.add(webSystemParametersModel);
			}
		}

	}
	@Override
	public Iterator<WebSystemParameterModel> iterator(long first, long count) 
	{
		// Get the data
		List<WebSystemParameterModel> newList = new ArrayList<WebSystemParameterModel>(websystemParametersModelList);

		// Sort the data
		Collections.sort(newList, comparator);

		return newList.subList(Integer.parseInt(String.valueOf(first)),Integer.parseInt(String.valueOf(first + count))).iterator();
	}
	
	
	@Override
	public IModel<WebSystemParameterModel> model(final Object object) 
	{

		return new AbstractReadOnlyModel<WebSystemParameterModel>() 
		{

			private static final long serialVersionUID = 1L;

			@Override
			public WebSystemParameterModel getObject() 
			{

				return (WebSystemParameterModel) object;
			}

		};
	}

	@Override
	public long size()
	{
		// TODO Auto-generated method stub
		return websystemParametersModelList.size();
	}


}
