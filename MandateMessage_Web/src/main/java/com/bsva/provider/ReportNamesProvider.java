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

import com.bsva.commons.model.ReportsNamesModel;
import com.bsva.controller.Controller;
import com.bsva.models.WebReportsNamesModel;
import com.bsva.translator.WebAdminTranslator;

public class ReportNamesProvider extends SortableDataProvider 
{
	Controller controller;

	class SortableDataProviderComparator implements Comparator<WebReportsNamesModel>, Serializable
	{
		@Override
		public int compare(final WebReportsNamesModel o1,final WebReportsNamesModel o2) 
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

	private static final long serialVersionUID = 1L;
	//Controller controller;
	private List<WebReportsNamesModel> webReportsNamesModelList;
	private List<ReportsNamesModel> reportsNamesModelList;
	private SortableDataProviderComparator comparator = new SortableDataProviderComparator();

	public ReportNamesProvider() {

		setSort("reportName", SortOrder.ASCENDING);
		
		reportsNamesModelList = (List<ReportsNamesModel>) controller.viewAllReportsName();
		webReportsNamesModelList = new ArrayList<WebReportsNamesModel>();
		
		if (reportsNamesModelList.size() > 0) 
		{
			for (ReportsNamesModel reportsNamesModel : reportsNamesModelList) 
			{
				WebReportsNamesModel webReportsNamesModel = new WebReportsNamesModel();
				webReportsNamesModel = new WebAdminTranslator().translateCommonsReportNamesToWebModel(reportsNamesModel);
				webReportsNamesModelList.add(webReportsNamesModel);
			}
		}
	}

	public ReportNamesProvider(String reportName) {
		
		
		controller = new Controller();
		
		setSort("reportName", SortOrder.ASCENDING);
		
		reportsNamesModelList = (List<ReportsNamesModel>) controller.viewReportNamesByCriteria(reportName);

		webReportsNamesModelList = new ArrayList<WebReportsNamesModel>();

		if (reportsNamesModelList.size() > 0)
		{
			
			for (ReportsNamesModel reportsNamesModel : reportsNamesModelList) {
				WebReportsNamesModel webReportsNamesModel = new WebReportsNamesModel();
				webReportsNamesModel = new WebAdminTranslator().translateCommonsReportNamesToWebModel(reportsNamesModel);
				webReportsNamesModelList.add(webReportsNamesModel);
			}

		}

	}

	@Override
	public void detach() {

	}

	@Override
	public Iterator<WebReportsNamesModel> iterator(long first, long count) {
		List<WebReportsNamesModel> newList = new ArrayList<WebReportsNamesModel>(webReportsNamesModelList);

		Collections.sort(newList, comparator);
		return newList.subList(Integer.parseInt(String.valueOf(first)), Integer.parseInt(String.valueOf(first + count))).iterator();
	}

	@Override
	public IModel<WebReportsNamesModel> model(final Object object) {
		return new AbstractReadOnlyModel<WebReportsNamesModel>() {

			private static final long serialVersionUID = 1L;

			@Override
			public WebReportsNamesModel getObject() {
				return (WebReportsNamesModel) object;
			}
		};

	}

	@Override
	public long size() {

		return reportsNamesModelList.size();
	}

}
