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
import com.bsva.commons.model.SysCtrlFileSizeLimitModel;
import com.bsva.controller.Controller;
import com.bsva.models.WebFileSizeLimitModel;
import com.bsva.models.WebLocalInstrumentCodesModel;
import com.bsva.models.WebSysCtrlFileSizeLimitModel;
import com.bsva.provider.FileSizeLimitProvider.SortableDataProviderComparator;
import com.bsva.translator.WebAdminTranslator;

/**
 * 
 * @author DimakatsoN
 *
 */
public class SysCtrlFileSizeLimitProvider extends SortableDataProvider
{

	private List<WebSysCtrlFileSizeLimitModel> webSysCtrlFileSizeLimitModelList;
	private List<SysCtrlFileSizeLimitModel> sysCtrlFileSizeLimitModelList = new ArrayList<SysCtrlFileSizeLimitModel>();
	private SortableDataProviderComparator comparator = new SortableDataProviderComparator();
	public static Logger log = Logger.getLogger(FileSizeLimitProvider.class);
	Controller controller;
	
	 class SortableDataProviderComparator implements Comparator<WebSysCtrlFileSizeLimitModel>, Serializable 
	 {  
		 public int compare(final WebSysCtrlFileSizeLimitModel o1, final WebSysCtrlFileSizeLimitModel o2) 
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
	 

		public SysCtrlFileSizeLimitProvider() {
			controller = new Controller();
			setSort("subService", SortOrder.ASCENDING);
			sysCtrlFileSizeLimitModelList = (List<SysCtrlFileSizeLimitModel>) controller.viewSysCtrlFileSizeLimit();
			webSysCtrlFileSizeLimitModelList = new ArrayList<WebSysCtrlFileSizeLimitModel>();

			if (sysCtrlFileSizeLimitModelList.size() > 0) {
				
				for (SysCtrlFileSizeLimitModel localCommonModel : sysCtrlFileSizeLimitModelList)

				{

					WebSysCtrlFileSizeLimitModel webModel = new WebSysCtrlFileSizeLimitModel();
					webModel = new WebAdminTranslator().translatSysCtrlFileSizeLimitModelCommonsModelToWebModel(localCommonModel);
					webSysCtrlFileSizeLimitModelList.add(webModel);
				}
			}
		}

		public SysCtrlFileSizeLimitProvider(String memberId, String subService) {
			controller = new Controller();
			setSort("subService", SortOrder.ASCENDING);
			sysCtrlFileSizeLimitModelList = (List<SysCtrlFileSizeLimitModel>) controller.viewSysCtrlFileSizeLimitSearch(memberId,subService);
			webSysCtrlFileSizeLimitModelList = new ArrayList<WebSysCtrlFileSizeLimitModel>();

			if (sysCtrlFileSizeLimitModelList.size() > 0) {
				
				for (SysCtrlFileSizeLimitModel localCommonModel : sysCtrlFileSizeLimitModelList)

				{

					WebSysCtrlFileSizeLimitModel webModel = new WebSysCtrlFileSizeLimitModel();
					webModel = new WebAdminTranslator().translatSysCtrlFileSizeLimitModelCommonsModelToWebModel(localCommonModel);
					webSysCtrlFileSizeLimitModelList.add(webModel);
				}
			}
		}

		@Override
		public Iterator<WebSysCtrlFileSizeLimitModel> iterator(long first, long count) {
			// Get the data
			List<WebSysCtrlFileSizeLimitModel> newList = new ArrayList<WebSysCtrlFileSizeLimitModel>(webSysCtrlFileSizeLimitModelList);

			// Sort the data
			Collections.sort(newList, comparator);

			return newList.subList(Integer.parseInt(String.valueOf(first)),
					Integer.parseInt(String.valueOf(first + count))).iterator();
		}

		@Override
		public long size() {
			// TODO Auto-generated method stub
			return sysCtrlFileSizeLimitModelList.size();
		}

		@Override
		public IModel<WebSysCtrlFileSizeLimitModel> model(final Object object) {
			return new AbstractReadOnlyModel<WebSysCtrlFileSizeLimitModel>() {
				@Override
				public WebSysCtrlFileSizeLimitModel getObject() {
					return (WebSysCtrlFileSizeLimitModel) object;
				}
			};
		}
}
