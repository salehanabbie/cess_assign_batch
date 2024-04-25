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

import com.bsva.commons.model.CustomerParametersModel;
import com.bsva.commons.model.OpsCustParamModel;
import com.bsva.controller.Controller;
import com.bsva.models.WebOpsCustomerParameters;
import com.bsva.translator.WebAdminTranslator;

public class OpsCustomerParametersProvider extends SortableDataProvider{
	private static final long serialVersionUID = 1L;
	private List<WebOpsCustomerParameters> webCustomerParametersModelList;
	private List<OpsCustParamModel> opsCustParamModelList = new ArrayList<OpsCustParamModel>();
	private SortableDataProviderComparator comparator = new SortableDataProviderComparator();
	public static Logger log = Logger.getLogger(OpsCustomerParametersProvider.class);
	Controller controller;

	class SortableDataProviderComparator implements
		Comparator<WebOpsCustomerParameters>, Serializable {

		private static final long serialVersionUID = 1L;
		
		public int compare(final WebOpsCustomerParameters o1,final WebOpsCustomerParameters o2) 
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

	public OpsCustomerParametersProvider() {
		controller = new Controller();
		setSort("instId", SortOrder.ASCENDING);
		opsCustParamModelList = (List<OpsCustParamModel>) controller.viewAllOpsCustomerParameters();
		webCustomerParametersModelList = new ArrayList<WebOpsCustomerParameters>();

		if (opsCustParamModelList.size() > 0) {
			
			for (OpsCustParamModel localCommonModel : opsCustParamModelList)

			{

				WebOpsCustomerParameters webModel = new WebOpsCustomerParameters();
				webModel = new WebAdminTranslator().translateOpsCustParamModelToWebModel(localCommonModel);
				webCustomerParametersModelList.add(webModel);
			}
		}
	}
	@Override
	public void detach() {
		// TODO Auto-generated method stub

	}

	@Override
	public Iterator<WebOpsCustomerParameters> iterator(long first, long count) {
		// Get the data
		List<WebOpsCustomerParameters> newList = new ArrayList<WebOpsCustomerParameters>(
				webCustomerParametersModelList);

		// Sort the data
		Collections.sort(newList, comparator);

		return newList.subList(Integer.parseInt(String.valueOf(first)),
				Integer.parseInt(String.valueOf(first + count))).iterator();
	}

	@Override
	public IModel<WebOpsCustomerParameters> model(final Object object) {
		return new AbstractReadOnlyModel<WebOpsCustomerParameters>() {
			@Override
			public WebOpsCustomerParameters getObject() {
				return (WebOpsCustomerParameters) object;
			}
		};
	}

	@Override
	public long size()
	{

		return opsCustParamModelList.size();

	}

	
	
	
}
