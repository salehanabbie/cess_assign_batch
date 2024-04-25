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
import com.bsva.commons.model.FileStatusCommonsModel;
import com.bsva.controller.Controller;
import com.bsva.models.WebFileStatusModel;
import com.bsva.provider.FileStatusProvider.SortableDataProviderComparator;
import com.bsva.translator.WebAdminTranslator;

public class FileStatusProvider  extends SortableDataProvider 
{
	/**
	 * @author ElelwaniR
	 */
	private static final long serialVersionUID = 1L;
	private List<WebFileStatusModel> webFileStatusModelList;
	private List<FileStatusCommonsModel> fileStatusCommonsModelList = new ArrayList<FileStatusCommonsModel>();
	private SortableDataProviderComparator comparator = new SortableDataProviderComparator();
	public static Logger log = Logger.getLogger(FileStatusProvider.class);
	Controller controller;

	class SortableDataProviderComparator implements Comparator<WebFileStatusModel>, Serializable
	{
		private static final long serialVersionUID = 1L;
		public int compare(final WebFileStatusModel o1,final WebFileStatusModel o2) 
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

	public FileStatusProvider()
	{
		controller = new Controller();
		setSort("status", SortOrder.ASCENDING);
		fileStatusCommonsModelList = (List<FileStatusCommonsModel>) controller.viewAllFileStatus();
		webFileStatusModelList = new ArrayList<WebFileStatusModel>();
		if (fileStatusCommonsModelList.size() > 0)
		{	
			for (FileStatusCommonsModel localCommonModel : fileStatusCommonsModelList)
			{
				WebFileStatusModel webModel = new WebFileStatusModel();
				webModel = new WebAdminTranslator().translateFileStatusCommonsModelToWebModel(localCommonModel);
				webFileStatusModelList.add(webModel);
			}
		}
	}
	@Override
	public void detach() 
	{
	}

	@Override
	public Iterator<WebFileStatusModel> iterator(long first, long count)
	{
		List<WebFileStatusModel> newList = new ArrayList<WebFileStatusModel>(webFileStatusModelList);
		Collections.sort(newList, comparator);
		return newList.subList(Integer.parseInt(String.valueOf(first)), Integer.parseInt(String.valueOf(first + count))).iterator();
	}

	@Override
	public IModel<WebFileStatusModel> model(final Object object)
	{
		return new AbstractReadOnlyModel<WebFileStatusModel>() 
		{
			@Override
			public WebFileStatusModel getObject() 
			{
				return (WebFileStatusModel) object;
			}
		};
	}

	@Override
	public long size()
	{
		return fileStatusCommonsModelList.size();
	}

}