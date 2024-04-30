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
import com.bsva.commons.model.AccountTypeModel;
import com.bsva.commons.model.AmendmentCodesModel;
import com.bsva.controller.Controller;
import com.bsva.models.WebAmendmentCodesModel;
import com.bsva.translator.WebAdminTranslator;

public class AmendmentCodesProvider extends  SortableDataProvider 
{
	private static final long serialVersionUID = 1L;
	Controller controller = new Controller();
	
	class SortableDataProviderComparator implements Comparator<WebAmendmentCodesModel>, Serializable 
	{
		private static final long serialVersionUID = 1L;
		public int compare(final WebAmendmentCodesModel arg0, final WebAmendmentCodesModel arg1) 
		{
			PropertyModel<Comparable> model1 = new PropertyModel<Comparable>(arg0, getSort().getProperty().toString());
			PropertyModel<Comparable> model2 = new PropertyModel<Comparable>(arg1, getSort().getProperty().toString());
			int result = model1.getObject().compareTo(model2.getObject());
			if (!getSort().isAscending())
			{
				result = -result;
			}
			return result;
		}
	}
	
	private List<WebAmendmentCodesModel> webAmendmentCodesModelList;
	private List<AmendmentCodesModel> amendmentCodesModelList;
	private SortableDataProviderComparator comparator = new SortableDataProviderComparator();
	public static Logger log = Logger.getLogger(AmendmentCodesProvider.class);
	
	public AmendmentCodesProvider()
	{
         setSort("amendmentCodes", SortOrder.ASCENDING);
         amendmentCodesModelList = (List<AmendmentCodesModel>) controller.viewAllAmendmentCodes();
         webAmendmentCodesModelList = new ArrayList<WebAmendmentCodesModel>();
         if(amendmentCodesModelList.size() > 0)
         {
        	 for (AmendmentCodesModel  amendmentCodesModel : amendmentCodesModelList)
        	 {
		    	 WebAmendmentCodesModel webModel = new WebAmendmentCodesModel();
		    	 webModel = new WebAdminTranslator().translateCommonsAmendmentCodesModelToWebModel(amendmentCodesModel);
		    	 webAmendmentCodesModelList.add(webModel);
        	 }
	    }
	}

	public  AmendmentCodesProvider(String amendmentCode) 
	{
		log.debug("amendmentCode--> "+amendmentCode);
	    setSort("amendmentCodes", SortOrder.ASCENDING);
	    webAmendmentCodesModelList = new ArrayList<WebAmendmentCodesModel>();
	    amendmentCodesModelList = (List<AmendmentCodesModel>)controller.viewAmendmentCodesByCriteria(amendmentCode);
	    log.debug("amendmentCodesModelList--> "+amendmentCodesModelList);
	     
   	    if(amendmentCodesModelList.size() > 0);
	   	{
	   		for (AmendmentCodesModel localCommonModel : amendmentCodesModelList)
	   		{
		   		WebAmendmentCodesModel webModel = new WebAmendmentCodesModel();
		   		webModel = new WebAdminTranslator().translateCommonsAmendmentCodesModelToWebModel(localCommonModel);
		   		webAmendmentCodesModelList.add(webModel);
	   		}
	   	}
     } 

	@Override
	public Iterator<WebAmendmentCodesModel> iterator(long first, long count)
	{
		List<WebAmendmentCodesModel> newList = new ArrayList<WebAmendmentCodesModel>(webAmendmentCodesModelList);
		Collections.sort(newList, comparator);
		return newList.subList(Integer.parseInt(String.valueOf(first)), Integer.parseInt(String.valueOf(first + count))).iterator();
	}

	public IModel<WebAmendmentCodesModel> model(final Object object) 
	{
		return new AbstractReadOnlyModel<WebAmendmentCodesModel>() 
		{
			private static final long serialVersionUID = 1L;

			@Override
			public WebAmendmentCodesModel getObject() 
			{
				return (WebAmendmentCodesModel) object;
			}
		};
	}

	@Override
	public long size() 
	{	 
		return amendmentCodesModelList.size();
	}
}
