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
import com.bsva.commons.model.AdjustmentCategoryModel;
import com.bsva.controller.Controller;
import com.bsva.models.WebAdjustmentCategoryModel;
import com.bsva.translator.WebAdminTranslator;

/**
 * 
 * @author NhlanhlaM
 *
 */
public class AdjustmentCategoryProvider extends SortableDataProvider
{
	private static final long serialVersionUID = 1L;
	Controller controller = new Controller();
	
	class SortableDataProviderComparator implements Comparator<WebAdjustmentCategoryModel>, Serializable 
	{
		private static final long serialVersionUID = 1L;
		public int compare(final WebAdjustmentCategoryModel arg0, WebAdjustmentCategoryModel arg1)
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
	
	private List<WebAdjustmentCategoryModel> webAdjustmentCategoryList;
	private List<AdjustmentCategoryModel> adjustmentCategoryList;
	private SortableDataProviderComparator comparator = new SortableDataProviderComparator();
	public static Logger log = Logger.getLogger(AdjustmentCategoryProvider.class);
	
	public AdjustmentCategoryProvider()
	{
		setSort("adjustmentCategory", SortOrder.ASCENDING);
		adjustmentCategoryList = (List<AdjustmentCategoryModel>) controller.viewAllAdjustmentCategory();
		webAdjustmentCategoryList = new ArrayList<WebAdjustmentCategoryModel>();
	    if(adjustmentCategoryList.size() > 0)
	    {
	    	for (AdjustmentCategoryModel adjustmentCategoryModel : adjustmentCategoryList)
	    	{
	    		WebAdjustmentCategoryModel webModel = new WebAdjustmentCategoryModel();
	    		webModel = new WebAdminTranslator().translateCommonsAdjustmentCategoryModelToWebModel(adjustmentCategoryModel);
	    		webAdjustmentCategoryList.add(webModel);
	    	}
	    }
	}
	
	public AdjustmentCategoryProvider(String adjustmentCategory)
    {
   	    setSort("adjustmentCategory", SortOrder.ASCENDING);
   	    adjustmentCategoryList = (List<AdjustmentCategoryModel>)controller.viewAdjustmentCategoryByCriteria(adjustmentCategory);
   	    webAdjustmentCategoryList = new ArrayList<WebAdjustmentCategoryModel>();
   	    if(adjustmentCategoryList.size() > 0);
   	    {
   	    	for (AdjustmentCategoryModel localCommonModel : adjustmentCategoryList)
   	    	{
   	    		WebAdjustmentCategoryModel webModel = new WebAdjustmentCategoryModel();
   	    		webModel = new WebAdminTranslator().translateCommonsAdjustmentCategoryModelToWebModel(localCommonModel);
   	    		webAdjustmentCategoryList.add(webModel);
   	    	}
   	    }
    }  
	
	@Override
	public void detach() 
	{
	}
	
	@Override
	public Iterator<WebAdjustmentCategoryModel> iterator(long first, long count) 
	{ 
		List<WebAdjustmentCategoryModel> newList = new ArrayList<WebAdjustmentCategoryModel>(webAdjustmentCategoryList);   
		Collections.sort(newList, comparator);  
		return newList.subList(Integer.parseInt(String.valueOf(first)), Integer.parseInt(String.valueOf(first + count))).iterator();  
	}
	
	public IModel <WebAdjustmentCategoryModel> model(final Object object)
	{
		return new AbstractReadOnlyModel<WebAdjustmentCategoryModel>()
		{
			@Override
			public WebAdjustmentCategoryModel getObject()
			{
				return (WebAdjustmentCategoryModel) object;
			}
		};
	}
	
	
	@Override
	public long size() 
	{	
		return adjustmentCategoryList.size();
	}
		
}