package com.bsva.provider;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

import com.bsva.commons.model.OpsFileRegModel;
import com.bsva.controller.Controller;
import com.bsva.models.ViewDeleteEOTFileModel;
import com.bsva.models.ViewDeleteInputModel;
import com.bsva.models.WebOpsFileRegModel;
import com.bsva.translator.WebAdminTranslator;

public class ViewDeleteEOTFileProvider extends SortableDataProvider {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	Controller controller;
	public static Logger log = Logger.getLogger(ViewDeleteEOTFileProvider.class);
	class SortableDataProviderComparator implements Comparator<ViewDeleteEOTFileModel>,Serializable{

		public int compare(ViewDeleteEOTFileModel o1,ViewDeleteEOTFileModel o2) {
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
	
	private SortableDataProviderComparator comparator = new SortableDataProviderComparator();
	private List<ViewDeleteEOTFileModel> viewDeleteEOTFileModelList;
	List<String> results;
	String directoryName = "/home/opsjava/Delivery/Mandates/Input/Processing/";
	File fileNames = new File(directoryName);
	String name = null;
	private List<WebOpsFileRegModel> webOpsFileRegList;
	private List<OpsFileRegModel> opsFileRegList;
	
	
	
	public ViewDeleteEOTFileProvider(){
		
		
		File file   = new File(directoryName);
		File[] files = file.listFiles();
		viewDeleteEOTFileModelList = new ArrayList<ViewDeleteEOTFileModel>();
		//get all the files  from a directory
		
		if(files != null && files.length > 0)
		{
			for (File fileNames : files)
			{	
				if(fileNames.getName().equalsIgnoreCase("Processing"))
				{
					fileNames.delete();
				}
				else
				{
					
					ViewDeleteEOTFileModel viewDeleteEOTFileModel = new ViewDeleteEOTFileModel();
					viewDeleteEOTFileModel.setFileName(fileNames.getName());
					viewDeleteEOTFileModelList.add(viewDeleteEOTFileModel);
					//results.add(fileNames.getName());
					name =fileNames.getName();
					log.info("FileNames*******************:"+name);
				}
			}	
		}
		
	}
	
	public ViewDeleteEOTFileProvider(String fileName)
	{
		
		File file   = new File(directoryName);
		File[] files = file.listFiles();
		viewDeleteEOTFileModelList = new ArrayList<ViewDeleteEOTFileModel>();
		//get all the files  from a directory
		
		if(files != null && files.length > 0)
		{
			for (File fileNames : files)
			{	
				if(fileNames.getName().equalsIgnoreCase("Processing"))
				{
					fileNames.delete();
				}
				else
				{
					if(fileNames.getName().equalsIgnoreCase(fileName))
					{
						ViewDeleteEOTFileModel viewDeleteEOTFileModel = new ViewDeleteEOTFileModel();
						viewDeleteEOTFileModel.setFileName(fileNames.getName());
						viewDeleteEOTFileModelList.add(viewDeleteEOTFileModel);
						//results.add(fileNames.getName());
						name =fileNames.getName();
						log.info("FileNames*******************:"+name);
					}
				}
			}	
		}
	}
	
	
	public  void ViewDeleteEOTFileProvider(String memberNo)
	{
		controller = new Controller();
		setSort("fileName", SortOrder.ASCENDING);
		opsFileRegList=new ArrayList<OpsFileRegModel>();
		opsFileRegList = (List<OpsFileRegModel>) controller.viewEOTFile(memberNo);
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
	public Iterator<ViewDeleteEOTFileModel> iterator(long first, long count) {
		// TODO Auto-generated method stub
		
		 List<ViewDeleteEOTFileModel> newList = new ArrayList<ViewDeleteEOTFileModel>(viewDeleteEOTFileModelList);
		 
		// Collections.sort(newList, comparator);
		 return newList.subList(Integer.parseInt(String.valueOf(first)), Integer.parseInt(String.valueOf(first + count))).iterator();
	}
	public long size() {
		// TODO Auto-generated method stub
		return viewDeleteEOTFileModelList.size();

}

	
	
	@Override
	public IModel <ViewDeleteEOTFileModel> model(final Object object) {
		return new AbstractReadOnlyModel<ViewDeleteEOTFileModel>() 
			{  
		   /**
				 * 
				 */
				private static final long serialVersionUID = 1L;
			
             @Override
		   public ViewDeleteEOTFileModel getObject() { 
		/*	
			  List<String> object = results;
				log.info("object*******************:"+object.toString());*/

		    return(ViewDeleteEOTFileModel) object;  
		}
		 }; 
		   
	}	

}
