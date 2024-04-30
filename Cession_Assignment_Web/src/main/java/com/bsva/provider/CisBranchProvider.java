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
import com.bsva.commons.model.SysCisBranchModel;
import com.bsva.commons.model.SysCisBranchModel;
import com.bsva.controller.Controller;
import com.bsva.models.WebSysCisBankModel;
import com.bsva.models.WebSysCisBranchModel;
import com.bsva.models.WebSysCisBranchModel;
import com.bsva.translator.WebAdminTranslator;

/**
 * @author SalehaR
 *
 */
public class CisBranchProvider extends SortableDataProvider  
{
	public static Logger log = Logger.getLogger(CisBranchProvider.class);
	private static final long serialVersionUID = 1L;
	

	class SortableDataProviderComparator implements Comparator<WebSysCisBranchModel>, Serializable {

		private static final long serialVersionUID = 1L;

		public int compare( final WebSysCisBranchModel o1,  final WebSysCisBranchModel o2) {
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
	
	private List<WebSysCisBranchModel> webCisBranchList;
	private List<SysCisBranchModel>cisBranchList;
	private SortableDataProviderComparator comparator = new SortableDataProviderComparator();
	Controller controller = new Controller();
	
	public CisBranchProvider() 
	{
			setSort("memberNo", SortOrder.ASCENDING);
			cisBranchList =(List<SysCisBranchModel>) controller.viewAllCisBranchInfo();
			webCisBranchList = new ArrayList<WebSysCisBranchModel>();
	
	
			if (cisBranchList.size() > 0 ) 
			{
				for (SysCisBranchModel sysCisBranchModel : cisBranchList)
				{
					WebSysCisBranchModel  webCisBranchModel = new WebSysCisBranchModel();
					webCisBranchModel = new WebAdminTranslator().translateCommonsSysCisBranchModelToWebModel(sysCisBranchModel);
					webCisBranchList.add(webCisBranchModel);
				}
			}
	}
	
	public CisBranchProvider (String branchNo)
	{
		log.info("branchNo in provider ==> "+branchNo);
//		cisBranchList =(List<SysCisBranchModel>) controller.viewAllCisBranchInfo();
		
		setSort("memberNo", SortOrder.ASCENDING);
		SysCisBranchModel sysCisBranchModel = (SysCisBranchModel) controller.viewCisBranchInfoByCriteria(branchNo);
		log.info("sysCisBranchModel in provider ==> "+sysCisBranchModel);
		WebSysCisBranchModel webModel;
		
		if(sysCisBranchModel != null)
		{
			webCisBranchList = new ArrayList<WebSysCisBranchModel>();
			webModel = new WebSysCisBranchModel();
			webModel = new WebAdminTranslator().translateCommonsSysCisBranchModelToWebModel(sysCisBranchModel);
			webCisBranchList.add(webModel);
		}
	}
	
//	public CisBranchProvider (String branchNo, String memberNo)
//	{
//		log.info("branchNo in provider ==> "+branchNo);
//		log.info("memberNo in provider ==> "+memberNo);
//		
//		setSort("memberNo", SortOrder.ASCENDING);
//		cisBranchList = (List<SysCisBranchModel>) controller.viewCisBranchInfoByCriteria(branchNo, memberNo);
//		log.info("cisBranchList in provider ==> "+cisBranchList);
//		WebSysCisBranchModel webModel;
//		
//		webCisBranchList = new ArrayList<WebSysCisBranchModel>();
//		
//		if(cisBranchList != null && cisBranchList.size() > 0)
//		{
//			for (SysCisBranchModel localBranchModel : cisBranchList) 
//			{
//				webModel = new WebSysCisBranchModel();
//				webModel = new WebAdminTranslator().translateCommonsSysCisBranchModelToWebModel(localBranchModel);
//				webCisBranchList.add(webModel);
//			}
//		}
//	}

	@Override
	public Iterator<WebSysCisBranchModel> iterator(long first, long count) 
	{
		// Get the data
		List<WebSysCisBranchModel> newList = new ArrayList<WebSysCisBranchModel>(webCisBranchList);

		// Sort the data
		Collections.sort(newList, comparator);

		return newList.subList(Integer.parseInt(String.valueOf(first)), Integer.parseInt(String.valueOf(first + count))).iterator();
	}
	
	@Override
	public IModel<WebSysCisBranchModel> model(final Object object) 
	{
		return new AbstractReadOnlyModel<WebSysCisBranchModel>() 
		{	
			private static final long serialVersionUID = 1L;

			@Override
			public WebSysCisBranchModel getObject() 
			{
				return (WebSysCisBranchModel) object;
			}
		};
	}
	
	@Override
	public long size() {
		// TODO Auto-generated method stub
		return webCisBranchList.size();
	}

}
