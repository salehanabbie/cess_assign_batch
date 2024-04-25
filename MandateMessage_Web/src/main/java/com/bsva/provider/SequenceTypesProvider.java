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
import com.bsva.commons.model.SequenceTypesModel;
import com.bsva.controller.Controller;
import com.bsva.models.WebSequenceTypesModel;
import com.bsva.translator.WebAdminTranslator;

public class SequenceTypesProvider extends SortableDataProvider 
{
	Controller controller;

	class SortableDataProviderComparator implements Comparator<WebSequenceTypesModel>, Serializable 
	{
		private static final long serialVersionUID = 1L;
		
		public int compare( final WebSequenceTypesModel o1,  final WebSequenceTypesModel o2) 
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
	
	private List<WebSequenceTypesModel> webSequenceTypesModelList;
	private List<SequenceTypesModel> sequenceTypesList;
	private SortableDataProviderComparator comparator = new SortableDataProviderComparator();

	public SequenceTypesProvider() 
	{
		setSort("sequenceCode", SortOrder.ASCENDING);
		sequenceTypesList =(List<SequenceTypesModel>) controller.viewAllSequenceTypes();
		webSequenceTypesModelList = new ArrayList<WebSequenceTypesModel>();
		if (sequenceTypesList.size() > 0 ) 
		{
			for (SequenceTypesModel sequenceTypesCommonsModel : sequenceTypesList)
			{
				WebSequenceTypesModel webSeqTypesModel = new WebSequenceTypesModel();
				webSeqTypesModel = new WebAdminTranslator().translateCommonsSequenceTypeModelToWebModel(sequenceTypesCommonsModel);
				webSequenceTypesModelList.add(webSeqTypesModel);
			}
		}
		
	}

	public SequenceTypesProvider(String sequenceCode) 
	{
		setSort("sequenceCode", SortOrder.ASCENDING);
		sequenceTypesList =(List<SequenceTypesModel>) controller.viewSequenceCodesByCriteria(sequenceCode);
		webSequenceTypesModelList = new ArrayList<WebSequenceTypesModel>();
		if (sequenceTypesList.size() > 0 ) 
		{
			for (SequenceTypesModel sequenceTypesCommonsModel : sequenceTypesList)
			{
				WebSequenceTypesModel webSeqTypesModel = new WebSequenceTypesModel();
				webSeqTypesModel = new WebAdminTranslator().translateCommonsSequenceTypeModelToWebModel(sequenceTypesCommonsModel);
				webSequenceTypesModelList.add(webSeqTypesModel);
			}
		}
		
	}
	
	@Override
	public Iterator<WebSequenceTypesModel> iterator(long first, long count) 
	{
		List<WebSequenceTypesModel> newList = new ArrayList<WebSequenceTypesModel>(webSequenceTypesModelList);
		Collections.sort(newList, comparator);
		return newList.subList(Integer.parseInt(String.valueOf(first)),Integer.parseInt(String.valueOf(first + count))).iterator();
	}
	
	@Override
	public IModel<WebSequenceTypesModel> model(final Object object) 
	{
		return new AbstractReadOnlyModel<WebSequenceTypesModel>()
		{
			private static final long serialVersionUID = 1L;

			@Override
			public WebSequenceTypesModel getObject() 
			{
				return (WebSequenceTypesModel) object;
			}
		};
	}
	
	@Override
	public long size()
	{
		return sequenceTypesList.size();
	}

}