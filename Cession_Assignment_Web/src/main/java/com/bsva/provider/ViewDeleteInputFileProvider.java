package com.bsva.provider;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

import com.bsva.models.ViewDeleteInputModel;

/***
 * 
 * @author DimakatsoN
 *
 */

public class ViewDeleteInputFileProvider extends SortableDataProvider  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static Logger log = Logger.getLogger(ViewDeleteInputFileProvider.class);
	class SortableDataProviderComparator implements Comparator<ViewDeleteInputModel>,Serializable{

		public int compare(ViewDeleteInputModel o1,ViewDeleteInputModel o2) {
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
	private List<ViewDeleteInputModel> viewDeleteInputModelList;
	List<String> results;
	String directoryName = "/home/opsjava/Cession_Assign/Mandates/Input/";
	File fileNames = new File(directoryName);
	String name = null;
	
	public ViewDeleteInputFileProvider()
	{
		
		
		File file   = new File(directoryName);
		File[] files = file.listFiles();
		viewDeleteInputModelList = new ArrayList<ViewDeleteInputModel>();
		//get all the files  from a directory
		
		if(files.length > 0)
		{
			for (File fileNames : files)
			{	
				if(fileNames.getName().equalsIgnoreCase("Processing"))
				{
					fileNames.delete();
				}
				else
				{
					
					ViewDeleteInputModel viewDeleteInputModel = new ViewDeleteInputModel();
					viewDeleteInputModel.setFileName(fileNames.getName());
					viewDeleteInputModelList.add(viewDeleteInputModel);
					//results.add(fileNames.getName());
					name =fileNames.getName();
					log.info("FileNames*******************:"+name);
				}
			}	
		}
			
	}
	
	
	public ViewDeleteInputFileProvider(String fileName)
	{
//			for (File fileNames : files)
//			{	
//				if(fileNames.getName().equalsIgnoreCase("Processing"))
//				{
//					fileNames.delete();
//				}
//				else
//				{
//					results.add(fileNames.getName());
//					name =fileNames.getName();
//					log.info("FileNames*******************:"+name);
//				}
//			}
			
			
			File file   = new File(directoryName);
			File[] files = file.listFiles();
			viewDeleteInputModelList = new ArrayList<ViewDeleteInputModel>();
			//get all the files  from a directory
			
			if(files.length > 0)
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
							ViewDeleteInputModel viewDeleteInputModel = new ViewDeleteInputModel();
							viewDeleteInputModel.setFileName(fileNames.getName());
							viewDeleteInputModelList.add(viewDeleteInputModel);
							//results.add(fileNames.getName());
							name =fileNames.getName();
							log.info("FileNames*******************:"+name);
						}
					}
				}	
			}
	}
	
	@Override
	public Iterator<ViewDeleteInputModel> iterator(long first, long count) {
		// TODO Auto-generated method stub
		
		 List<ViewDeleteInputModel> newList = new ArrayList<ViewDeleteInputModel>(viewDeleteInputModelList);
		 
		// Collections.sort(newList, comparator);
		 return newList.subList(Integer.parseInt(String.valueOf(first)), Integer.parseInt(String.valueOf(first + count))).iterator();
	}
	@Override
	public long size() {
		// TODO Auto-generated method stub
		return viewDeleteInputModelList.size();

}
	@Override
	public IModel <ViewDeleteInputModel> model(final Object object) {
		return new AbstractReadOnlyModel<ViewDeleteInputModel>() 
			{  
		   /**
				 * 
				 */
				private static final long serialVersionUID = 1L;
			
             @Override
		   public ViewDeleteInputModel getObject() { 
		/*	
			  List<String> object = results;
				log.info("object*******************:"+object.toString());*/

		    return(ViewDeleteInputModel) object;  
		}
		 }; 
		   
	}	


}
	
