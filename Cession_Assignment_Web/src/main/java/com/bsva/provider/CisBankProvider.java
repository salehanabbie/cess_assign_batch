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
import com.bsva.commons.model.SysCisBankModel;
import com.bsva.controller.Controller;
import com.bsva.models.WebSysCisBankModel;
import com.bsva.translator.WebAdminTranslator;

/**
 * @author SalehaR
 *
 */
public class CisBankProvider extends SortableDataProvider  
{
	public static Logger log = Logger.getLogger(CisBankProvider.class);
	private static final long serialVersionUID = 1L;
	Controller controller = new Controller();

	class SortableDataProviderComparator implements Comparator<WebSysCisBankModel>, Serializable {

		private static final long serialVersionUID = 1L;

		public int compare( final WebSysCisBankModel o1,  final WebSysCisBankModel o2) {
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
	
	private List<WebSysCisBankModel> webCisBankList;
	private List<SysCisBankModel>cisBankList;
	private SortableDataProviderComparator comparator = new SortableDataProviderComparator();

	public CisBankProvider() 
	{
			setSort("memberNo", SortOrder.ASCENDING);
			cisBankList =(List<SysCisBankModel>) controller.viewAllCisBankInfo();
			webCisBankList = new ArrayList<WebSysCisBankModel>();
	
	
			if (cisBankList != null && cisBankList.size() > 0 ) 
			{
				for (SysCisBankModel sysCisBankModel : cisBankList)
				{
					WebSysCisBankModel  webCisBankModel = new WebSysCisBankModel();
					webCisBankModel = new WebAdminTranslator().translateCommonsSysCisBankModelToWebModel(sysCisBankModel);
					webCisBankList.add(webCisBankModel);
				}
			}
	}
	
	
	public CisBankProvider (String memberNo)
	{
		log.info("memberNo in provider ==> "+memberNo);
		cisBankList =(List<SysCisBankModel>) controller.viewAllCisBankInfo();
		setSort("memberNo", SortOrder.ASCENDING);
		SysCisBankModel sysCisBankModel = (SysCisBankModel) controller.viewCisBankInfoByCriteria(memberNo);
		log.info("sysCisBankModel in provider ==> "+memberNo);
		WebSysCisBankModel webModel;
		
		if(sysCisBankModel != null)
		{
			webCisBankList = new ArrayList<WebSysCisBankModel>();
			webModel = new WebSysCisBankModel();
			webModel = new WebAdminTranslator().translateCommonsSysCisBankModelToWebModel(sysCisBankModel);
			webCisBankList.add(webModel);
		}
	}

	@Override
	public Iterator<WebSysCisBankModel> iterator(long first, long count) 
	{
		// Get the data
		List<WebSysCisBankModel> newList = new ArrayList<WebSysCisBankModel>(webCisBankList);

		// Sort the data
		Collections.sort(newList, comparator);

		return newList.subList(Integer.parseInt(String.valueOf(first)), Integer.parseInt(String.valueOf(first + count))).iterator();
	}
	
	@Override
	public IModel<WebSysCisBankModel> model(final Object object) 
	{
		return new AbstractReadOnlyModel<WebSysCisBankModel>() 
		{	
			private static final long serialVersionUID = 1L;

			@Override
			public WebSysCisBankModel getObject() 
			{
				return (WebSysCisBankModel) object;
			}
		};
	}
	
	@Override
	public long size() {
		// TODO Auto-generated method stub
		return webCisBankList.size();
	}

}
