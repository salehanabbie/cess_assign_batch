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

import com.bsva.commons.model.FileSizeLimitModel;
import com.bsva.commons.model.OpsFileRegModel;
import com.bsva.commons.model.OpsSlaTimesCommonsModel;
import com.bsva.controller.Controller;
import com.bsva.models.WebFileSizeLimitModel;
import com.bsva.models.WebOpsFileRegModel;
import com.bsva.models.WebOpsSlaTimesModel;
import com.bsva.provider.OpsSlaTimesProvider.SortableDataProviderComparator;
import com.bsva.translator.WebAdminTranslator;

/**
 * 
 * @author DimakatsoN
 *
 */

public class FileSizeLimitProvider extends SortableDataProvider
{
	private static final long serialVersionUID = 1L;
	private List<WebFileSizeLimitModel> webFileSizeLimitModelList;
	private List<FileSizeLimitModel> fileSizeLimitModelList = new ArrayList<FileSizeLimitModel>();
	private SortableDataProviderComparator comparator = new SortableDataProviderComparator();
	public static Logger log = Logger.getLogger(FileSizeLimitProvider.class);
	Controller controller;

	class SortableDataProviderComparator implements
		Comparator<WebFileSizeLimitModel>, Serializable {

		private static final long serialVersionUID = 1L;
		
		public int compare(final WebFileSizeLimitModel o1,final WebFileSizeLimitModel o2) 
		{
			PropertyModel<Comparable> model1 = new PropertyModel<Comparable>(o1, getSort().getProperty().toString());
			PropertyModel<Comparable> model2 = new PropertyModel<Comparable>(o2, getSort().getProperty().toString());

			int result = model1.getObject().compareTo(model2.getObject());

			if (!getSort().isAscending()) {
				result = -result;
			}

			return result;
		}
	}
	
	public FileSizeLimitProvider() {
		controller = new Controller();
		setSort("subService", SortOrder.ASCENDING);
		fileSizeLimitModelList = (List<FileSizeLimitModel>) controller.viewFileSizeLimit();
		webFileSizeLimitModelList = new ArrayList<WebFileSizeLimitModel>();

		if (fileSizeLimitModelList.size() > 0) {
			
			for (FileSizeLimitModel localCommonModel : fileSizeLimitModelList)

			{

				WebFileSizeLimitModel webModel = new WebFileSizeLimitModel();
				webModel = new WebAdminTranslator().translateFileSizeLimitModelCommonsModelToWebModel(localCommonModel);
				webFileSizeLimitModelList.add(webModel);
			}
		}
	}

	public FileSizeLimitProvider(String memberId, String subService) {
		controller = new Controller();
		setSort("subService", SortOrder.ASCENDING);
		fileSizeLimitModelList = (List<FileSizeLimitModel>) controller.viewFileSizeLimitSearch(memberId,subService);
		webFileSizeLimitModelList = new ArrayList<WebFileSizeLimitModel>();

		if (fileSizeLimitModelList.size() > 0) {
			
			for (FileSizeLimitModel localCommonModel : fileSizeLimitModelList)

			{

				WebFileSizeLimitModel webModel = new WebFileSizeLimitModel();
				webModel = new WebAdminTranslator().translateFileSizeLimitModelCommonsModelToWebModel(localCommonModel);
				webFileSizeLimitModelList.add(webModel);
			}
		}
	}

	@Override
	public Iterator<WebFileSizeLimitModel> iterator(long first, long count) {
		// Get the data
		List<WebFileSizeLimitModel> newList = new ArrayList<WebFileSizeLimitModel>(webFileSizeLimitModelList);

		// Sort the data
		Collections.sort(newList, comparator);

		return newList.subList(Integer.parseInt(String.valueOf(first)),
				Integer.parseInt(String.valueOf(first + count))).iterator();
	}

	@Override
	public long size() {
		// TODO Auto-generated method stub
		return fileSizeLimitModelList.size();
	}

	@Override
	public IModel<WebFileSizeLimitModel> model(final Object object) {
		return new AbstractReadOnlyModel<WebFileSizeLimitModel>() {
			@Override
			public WebFileSizeLimitModel getObject() {
				return (WebFileSizeLimitModel) object;
			}
		};
	}
}
