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

import com.bsva.commons.model.OpsProcessControlCommonsModel;
import com.bsva.controller.Controller;
import com.bsva.controller.WebOpsProcessControlsModel;
import com.bsva.translator.WebAdminTranslator;

public class OpsProcessControlProvider extends SortableDataProvider {
	
	/**
	 * @author ElelwaniR
	 */
	private static final long serialVersionUID = 1L;
	private List<WebOpsProcessControlsModel> webOpsProcessControlsModelList;
	private List<OpsProcessControlCommonsModel> opsProcessControlCommonsModelList = new ArrayList<OpsProcessControlCommonsModel>();
	private SortableDataProviderComparator comparator = new SortableDataProviderComparator();
	public static Logger log = Logger.getLogger(OpsProcessControlProvider.class);
	Controller controller;

	class SortableDataProviderComparator implements
		Comparator<WebOpsProcessControlsModel>, Serializable {

		private static final long serialVersionUID = 1L;
		
		public int compare(final WebOpsProcessControlsModel o1,final WebOpsProcessControlsModel o2) 
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

	public OpsProcessControlProvider() {
		controller = new Controller();
		setSort("instId", SortOrder.ASCENDING);
		opsProcessControlCommonsModelList = (List<OpsProcessControlCommonsModel>) controller.viewAllOpsProcessControl();;
		webOpsProcessControlsModelList = new ArrayList<WebOpsProcessControlsModel>();

		if (opsProcessControlCommonsModelList.size() > 0) {
			
			for (OpsProcessControlCommonsModel localCommonModel : opsProcessControlCommonsModelList)

			{

				WebOpsProcessControlsModel webModel = new WebOpsProcessControlsModel();
				webModel = new WebAdminTranslator().translateOpsProcessControlCommonsModelToWebModel(localCommonModel);
				webOpsProcessControlsModelList.add(webModel);
			}
		}
	}
	@Override
	public void detach() {
		// TODO Auto-generated method stub

	}

	@Override
	public Iterator<WebOpsProcessControlsModel> iterator(long first, long count) {
		// Get the data
		List<WebOpsProcessControlsModel> newList = new ArrayList<WebOpsProcessControlsModel>(
				webOpsProcessControlsModelList);

		// Sort the data
		Collections.sort(newList, comparator);

		return newList.subList(Integer.parseInt(String.valueOf(first)),
				Integer.parseInt(String.valueOf(first + count))).iterator();
	}

	@Override
	public IModel<WebOpsProcessControlsModel> model(final Object object) {
		return new AbstractReadOnlyModel<WebOpsProcessControlsModel>() {
			@Override
			public WebOpsProcessControlsModel getObject() {
				return (WebOpsProcessControlsModel) object;
			}
		};
	}

	@Override
	public long size()
	{

		return opsProcessControlCommonsModelList.size();

	}


}
