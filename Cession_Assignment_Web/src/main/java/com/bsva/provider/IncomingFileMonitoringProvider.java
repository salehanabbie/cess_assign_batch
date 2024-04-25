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

import com.bsva.commons.model.OpsFileRegModel;
import com.bsva.commons.model.SysctrlCompParamModel;
import com.bsva.controller.Controller;
import com.bsva.models.WebOpsFileRegModel;
import com.bsva.translator.WebAdminTranslator;

/**
 * 
 * @author DimakatsoN
 *
 */

public class IncomingFileMonitoringProvider extends SortableDataProvider {
	
Controller controller;

class SortableDataProviderComparator implements Comparator<WebOpsFileRegModel>,Serializable
{

	@Override
	public int compare(WebOpsFileRegModel o1, WebOpsFileRegModel o2) {
		PropertyModel<Comparable> model1 = new PropertyModel<Comparable>(o1,getSort().getProperty().toString());
		PropertyModel<Comparable> model2 = new PropertyModel<Comparable>(o1,getSort().getProperty().toString());

		int result = model1.getObject().compareTo(model2.getObject());

		if(!getSort().isAscending())
		{
			result = -result;
		}

		return result;
	}
}

	private List<WebOpsFileRegModel> webOpsFileRegList;
	private List<OpsFileRegModel> opsFileRegList;
	private SortableDataProviderComparator comparator = new SortableDataProviderComparator();
	
	public IncomingFileMonitoringProvider()
	{
		controller = new Controller();
		
		SysctrlCompParamModel companyParamModel = new SysctrlCompParamModel();
		companyParamModel = (SysctrlCompParamModel) controller.retrieveCompanyParameters();
		
		setSort("fileName", SortOrder.ASCENDING);
		opsFileRegList = (List<OpsFileRegModel>) controller.viewOpsFileRegByDirection("I",companyParamModel.getTransamissionInd()+"D");
		webOpsFileRegList = new ArrayList<WebOpsFileRegModel>();
		
		if(opsFileRegList.size() > 0)
		{
			for (OpsFileRegModel fileRegCommonModel : opsFileRegList) 
			{
					WebOpsFileRegModel webModel = new WebOpsFileRegModel();
					webModel = new WebAdminTranslator().translateCommonsOpsFileRegModelToWebModel(fileRegCommonModel);
					webOpsFileRegList.add(webModel);

			}
		}
				
	}
	
	public IncomingFileMonitoringProvider(String memberNo,String service)
	{
		controller = new Controller();
		setSort("fileName", SortOrder.ASCENDING);
		opsFileRegList=new ArrayList<OpsFileRegModel>();
		opsFileRegList = (List<OpsFileRegModel>) controller.viewFileStatusSearch(memberNo, service);
		webOpsFileRegList = new ArrayList<WebOpsFileRegModel>();
	
		if(opsFileRegList.size() > 0)
		{
			for (OpsFileRegModel fileRegCommonModel : opsFileRegList) 
			{
					WebOpsFileRegModel webModel = new WebOpsFileRegModel();
					webModel = new WebAdminTranslator().translateCommonsOpsFileRegModelToWebModel(fileRegCommonModel);
					webOpsFileRegList.add(webModel);
					
			}
		}
	}
	
	

	@Override
	public Iterator<WebOpsFileRegModel> iterator(long first, long count) {
		
		List<WebOpsFileRegModel>newList = new ArrayList<WebOpsFileRegModel>(webOpsFileRegList);
		
		Collections.sort(newList, comparator);
		
		return newList.subList(Integer.parseInt(String.valueOf(first)), Integer.parseInt(String.valueOf(first + count))).iterator();
	}


	@Override
	public IModel<WebOpsFileRegModel> model (final Object object) {
		return new AbstractReadOnlyModel<WebOpsFileRegModel>()
				{
				@Override
				public WebOpsFileRegModel getObject(){
					return (WebOpsFileRegModel) object;
				}
				};
	}
	
	@Override
	public long size() {
		// TODO Auto-generated method stub
		return opsFileRegList.size();
	}

}
