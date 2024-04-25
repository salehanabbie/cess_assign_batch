package com.bsva.provider;

import java.io.File;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

import com.bsva.commons.model.OpsFileRegModel;
import com.bsva.controller.Controller;
import com.bsva.models.ViewDeleteInputModel;
import com.bsva.models.WebCopyFilesModel;
import com.bsva.models.WebOpsFileRegModel;
import com.bsva.provider.ViewFilesNotProcessedProvider.CustomDateComparator;
import com.bsva.provider.ViewFilesNotProcessedProvider.SortableDataProviderComparator;
import com.bsva.translator.WebAdminTranslator;

/**
 * @author SalehaR
 *
 */
public class CopyFilesProvider  extends SortableDataProvider 
{
	private static final long serialVersionUID = 1L;
	public static Logger log = Logger.getLogger(CopyFilesProvider.class);
	
	class SortableDataProviderComparator implements Comparator<WebCopyFilesModel>,Serializable
	{
		public int compare(WebCopyFilesModel o1, WebCopyFilesModel o2)
		{
			PropertyModel<Comparable> model1 = new PropertyModel<Comparable>(o1, getSort().getProperty().toString());  
			PropertyModel<Comparable> model2 = new PropertyModel<Comparable>(o2, getSort().getProperty().toString());  
		
			int result = model1.getObject().compareTo(model2.getObject());  
			if (!getSort().isAscending()) 
			{  
				result = +result;  
			}  
			return result;  
		}
	}
	
	
	public class CustomDateComparator implements Comparator<WebCopyFilesModel>
	{
		@Override
		public int compare(WebCopyFilesModel webCopyFilesModel1, WebCopyFilesModel webCopyFilesModel2) 
		{
			Date date1 = null;
			Date date2 = null;
			try 
			{
				date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ").parse(webCopyFilesModel1.getTimestamp());
				date2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ").parse(webCopyFilesModel2.getTimestamp());
			} 
			catch (ParseException e)
			{
				e.printStackTrace();
			}
			return date2.compareTo(date1);		
		}
	}
	
	
	private SortableDataProviderComparator comparator = new SortableDataProviderComparator();
	private List<WebCopyFilesModel> webCopyFilesModelList;
	List<String> results;
	String directoryName = "/home/opsjava/Delivery/Mandates/Output/temp/";
	File outputFiles = new File(directoryName);
	String name = null;
	
	public CopyFilesProvider()
	{
		File file   = new File(directoryName);
		File[] files = file.listFiles();
		webCopyFilesModelList = new ArrayList<WebCopyFilesModel>();

		if (files.length > 0) 
		{
			for (File fileNames : files)
			{
					WebCopyFilesModel webCopyFilesModel = new WebCopyFilesModel();
					
					webCopyFilesModel.setOutputFileName(fileNames.getName());
					
					long lastmodif = fileNames.lastModified();
					Date lastModifiedDate = new Date(lastmodif);
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
					String dateModStr = sdf.format(lastModifiedDate);
					
					webCopyFilesModel.setTimestamp(dateModStr);
					name = fileNames.getName();
					String fileType = StringUtils.substringBefore(name, ".");
					if (fileType.endsWith("D")) 
					{
						webCopyFilesModel.setFileType("Data File");
					} 
					else if (fileType.endsWith("S"))
					{
						webCopyFilesModel.setFileType("Start Of Transmission");
					} 
					else if (fileType.endsWith("E")) 
					{
						webCopyFilesModel.setFileType("End Of Transmission");
					}
					webCopyFilesModelList.add(webCopyFilesModel);
			}
			
		}
		Collections.sort(webCopyFilesModelList, new CustomDateComparator());
		
	}
	
	
	public CopyFilesProvider(String fileName)
	{	
		log.info("Search: File Name ==> "+fileName);
		
		File file   = new File(directoryName);
		File[] files = file.listFiles();
		webCopyFilesModelList = new ArrayList<WebCopyFilesModel>();

		if (files.length > 0) 
		{
			for (File fileNames : files)
			{
//				log.info("fileNames ==> "+fileNames);s
//				log.info("IF STATEMENT ==> "+fileNames.getName().contains(fileName));
//				log.info("IF STMT -1 ==> "+fileNames.getName().indexOf(fileName));
				
//				if(fileNames.getName().indexOf(fileName) != -1)
				if(fileNames.getName().contains(fileName))
				{
					WebCopyFilesModel webCopyFilesModel = new WebCopyFilesModel();
					
					webCopyFilesModel.setOutputFileName(fileNames.getName());
					
					long lastmodif = fileNames.lastModified();
					Date lastModifiedDate = new Date(lastmodif);
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
					String dateModStr = sdf.format(lastModifiedDate);
					
					webCopyFilesModel.setTimestamp(dateModStr);
					name = fileNames.getName();
					String fileType = StringUtils.substringBefore(name, ".");
					if (fileType.endsWith("D")) 
					{
						webCopyFilesModel.setFileType("Data File");
					} 
					else if (fileType.endsWith("S"))
					{
						webCopyFilesModel.setFileType("Start Of Transmission");
					} 
					else if (fileType.endsWith("E")) 
					{
						webCopyFilesModel.setFileType("End Of Transmission");
					}
					webCopyFilesModelList.add(webCopyFilesModel);
				}
			}
		}
		Collections.sort(webCopyFilesModelList, new CustomDateComparator());
		
	}
	
	public CopyFilesProvider(String memberNo, String serviceId)
	{	
		log.info("Search: Member No ==> "+memberNo);
		log.info("Search: ServiceId ==> "+serviceId);
		
		File file   = new File(directoryName);
		File[] files = file.listFiles();
		webCopyFilesModelList = new ArrayList<WebCopyFilesModel>();

		if (files.length > 0) 
		{
			for (File fileNames : files)
			{
//				log.info("fileNames ==> "+fileNames);s
				log.info("IF STATEMENT member==> "+fileNames.getName().contains(memberNo));
				log.info("IF STATEMENT serviceId ==> "+fileNames.getName().contains(serviceId));
//				log.info("IF STMT -1 ==> "+fileNames.getName().indexOf(fileName));
				
//				if(fileNames.getName().indexOf(fileName) != -1)
				if(fileNames.getName().contains(memberNo) && fileNames.getName().contains(serviceId))
				{
					WebCopyFilesModel webCopyFilesModel = new WebCopyFilesModel();
					
					webCopyFilesModel.setOutputFileName(fileNames.getName());
					
					long lastmodif = fileNames.lastModified();
					Date lastModifiedDate = new Date(lastmodif);
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
					String dateModStr = sdf.format(lastModifiedDate);
					
					webCopyFilesModel.setTimestamp(dateModStr);
					name = fileNames.getName();
					String fileType = StringUtils.substringBefore(name, ".");
					if (fileType.endsWith("D")) 
					{
						webCopyFilesModel.setFileType("Data File");
					} 
					else if (fileType.endsWith("S"))
					{
						webCopyFilesModel.setFileType("Start Of Transmission");
					} 
					else if (fileType.endsWith("E")) 
					{
						webCopyFilesModel.setFileType("End Of Transmission");
					}
					webCopyFilesModelList.add(webCopyFilesModel);
				}
			}
		}
		Collections.sort(webCopyFilesModelList, new CustomDateComparator());
		
	}
	
	
	@Override
	public Iterator<WebCopyFilesModel> iterator(long first, long count)
	{
		List<WebCopyFilesModel> newList = new ArrayList<WebCopyFilesModel>(webCopyFilesModelList);
		return newList.subList(Integer.parseInt(String.valueOf(first)), Integer.parseInt(String.valueOf(first + count))).iterator();
	}
	
	@Override
	public long size() 
	{
		return webCopyFilesModelList.size();
	}
	
	@Override
	public IModel <WebCopyFilesModel> model(final Object object)
	{
		return new AbstractReadOnlyModel<WebCopyFilesModel>()
		{
			private static final long serialVersionUID = 1L;
			   @Override
			   public WebCopyFilesModel getObject()
			   {
				   return(WebCopyFilesModel) object;
			   }
		};
	}
}
