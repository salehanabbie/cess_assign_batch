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
import com.bsva.commons.model.OpsRefSeqNumberCommonsModel;
import com.bsva.controller.Controller;
import com.bsva.models.WebOpsRefSequenceNumber;
import com.bsva.translator.WebAdminTranslator;

public class OpsRefSeqNumberProvider extends SortableDataProvider{

	
	/**
	 * @author ElelwaniR
	 */
	private static final long serialVersionUID = 1L;
	private List<WebOpsRefSequenceNumber> webOpsRefSequenceNumberList;
	private List<OpsRefSeqNumberCommonsModel> opsRefSeqNumberCommonsModelList = new ArrayList<OpsRefSeqNumberCommonsModel>();
	private SortableDataProviderComparator comparator = new SortableDataProviderComparator();
	public static Logger log = Logger.getLogger(OpsRefSeqNumberProvider.class);
	Controller controller;

	class SortableDataProviderComparator implements
		Comparator<WebOpsRefSequenceNumber>, Serializable {

		private static final long serialVersionUID = 1L;
		
		public int compare(final WebOpsRefSequenceNumber o1,final WebOpsRefSequenceNumber o2) 
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

	public OpsRefSeqNumberProvider() 
	{
		controller = new Controller();
		setSort("serviceId", SortOrder.ASCENDING);
		opsRefSeqNumberCommonsModelList = (List<OpsRefSeqNumberCommonsModel>) controller.viewAllOpsRefSeqNumber();
		webOpsRefSequenceNumberList = new ArrayList<WebOpsRefSequenceNumber>();

		if (opsRefSeqNumberCommonsModelList.size() > 0) {
			
			for (OpsRefSeqNumberCommonsModel localCommonModel : opsRefSeqNumberCommonsModelList)

			{

				WebOpsRefSequenceNumber webModel = new WebOpsRefSequenceNumber();
				webModel = new WebAdminTranslator().translateOpsRefSeqNumberCommonsModelToWebModel(localCommonModel);
				webOpsRefSequenceNumberList.add(webModel);
			}
		}
	}
	@Override
	public void detach() {
		// TODO Auto-generated method stub

	}

	@Override
	public Iterator<WebOpsRefSequenceNumber> iterator(long first, long count) {
		// Get the data
		List<WebOpsRefSequenceNumber> newList = new ArrayList<WebOpsRefSequenceNumber>(
				webOpsRefSequenceNumberList);

		// Sort the data
		Collections.sort(newList, comparator);

		return newList.subList(Integer.parseInt(String.valueOf(first)),
				Integer.parseInt(String.valueOf(first + count))).iterator();
	}

	@Override
	public IModel<WebOpsRefSequenceNumber> model(final Object object) {
		return new AbstractReadOnlyModel<WebOpsRefSequenceNumber>() {
			@Override
			public WebOpsRefSequenceNumber getObject() {
				return (WebOpsRefSequenceNumber) object;
			}
		};
	}

	@Override
	public long size()
	{

		return opsRefSeqNumberCommonsModelList.size();

	}

	
	
}
