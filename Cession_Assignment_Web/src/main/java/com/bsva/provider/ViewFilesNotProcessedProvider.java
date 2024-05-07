package com.bsva.provider;
import java.io.File;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import com.bsva.models.ViewDeleteInputModel;
import com.bsva.panels.SystemStatus.ViewFilesNotProcessed;
import com.bsva.provider.ViewDeleteInputFileProvider.SortableDataProviderComparator;

public class ViewFilesNotProcessedProvider extends SortableDataProvider
{
	private static final long serialVersionUID = 1L;
	public static Logger log = Logger.getLogger(ViewFilesNotProcessedProvider.class);
	
	class SortableDataProviderComparator implements Comparator<ViewDeleteInputModel>,Serializable
	{
		public int compare(ViewDeleteInputModel o1, ViewDeleteInputModel o2)
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
	
	public class CustomDateComparator implements Comparator<ViewDeleteInputModel>
	{
		@Override
		public int compare(ViewDeleteInputModel viewDeleteInputModel1, ViewDeleteInputModel viewDeleteInputModel2) 
		{
			Date date1 = null;
			Date date2 = null;
			try 
			{
				date1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss ").parse(viewDeleteInputModel1.getmodifiedDate());
				date2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss ").parse(viewDeleteInputModel2.getmodifiedDate());
			} 
			catch (ParseException e)
			{
				e.printStackTrace();
			}
			return date1.compareTo(date2);		
		}
	}
	
	private SortableDataProviderComparator comparator = new SortableDataProviderComparator();
	private List<ViewDeleteInputModel> viewDeleteInputModelList;
	List<String> results;
	String directoryName = "/home/opsjava/Delivery/Cession_Assign/Input/";
	File fileNames = new File(directoryName);
	String name = null;
	
	public ViewFilesNotProcessedProvider()
	{
		File file   = new File(directoryName);
		File[] files = file.listFiles();
		viewDeleteInputModelList = new ArrayList<ViewDeleteInputModel>();

		if (files.length > 0) 
		{
			for (File fileNames : files)
			{
				if (fileNames.getName().equalsIgnoreCase("Processing")) 
				{
					//Do not do anything here
				} 
				else 
				{
					ViewDeleteInputModel viewDeleteInputModel = new ViewDeleteInputModel();
					
					viewDeleteInputModel.setFileName(fileNames.getName());
					long lastmodif = fileNames.lastModified();
					Date lastModifiedDate = new Date(lastmodif);
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss ");
					String dateModStr = sdf.format(lastModifiedDate);
					viewDeleteInputModel.setmodifiedDate(dateModStr);
					name = fileNames.getName();
					String fileType = StringUtils.substringBefore(name, ".");
					if (fileType.endsWith("D")) 
					{
						viewDeleteInputModel.setFileType("Data File");
					} 
					else if (fileType.endsWith("S"))
					{
						viewDeleteInputModel.setFileType("Start Of Transmission");
					} 
					else if (fileType.endsWith("E")) 
					{
						viewDeleteInputModel.setFileType("End Of Transmission");
					}
					viewDeleteInputModelList.add(viewDeleteInputModel);
				}
			}
			
		}
		Collections.sort(viewDeleteInputModelList, new CustomDateComparator());
	}
	
	@Override
	public Iterator<ViewDeleteInputModel> iterator(long first, long count)
	{
		List<ViewDeleteInputModel> newList = new ArrayList<ViewDeleteInputModel>(viewDeleteInputModelList);
		return newList.subList(Integer.parseInt(String.valueOf(first)), Integer.parseInt(String.valueOf(first + count))).iterator();
	}
	
	@Override
	public long size() 
	{
		return viewDeleteInputModelList.size();
	}
	
	@Override
	public IModel <ViewDeleteInputModel> model(final Object object)
	{
		return new AbstractReadOnlyModel<ViewDeleteInputModel>()
		{
			private static final long serialVersionUID = 1L;
			   @Override
			   public ViewDeleteInputModel getObject()
			   {
				   return(ViewDeleteInputModel) object;
			   }
		};
	}
}
