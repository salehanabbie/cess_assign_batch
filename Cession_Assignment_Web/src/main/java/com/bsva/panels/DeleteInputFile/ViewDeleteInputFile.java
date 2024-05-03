package com.bsva.panels.DeleteInputFile;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.extensions.model.AbstractCheckBoxModel;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.validation.validator.StringValidator;

import com.bsva.controller.Controller;
import com.bsva.models.ViewDeleteInputModel;
import com.bsva.panels.CheckBoxColumn;
import com.bsva.provider.ViewDeleteInputFileProvider;


/***
 * 
 * @author DimakatsoN
 *
 */

public class ViewDeleteInputFile extends Panel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Controller controller;
	public static Logger log = Logger.getLogger(ViewDeleteInputFile.class);
	public static String id;
	private Form form;
	private Set<ViewDeleteInputModel> selected = new HashSet<ViewDeleteInputModel>();
	private Button deleteButton;
	private Button cancelButton;
	private Button searchButton;
	private TextField<String> searchText;
	String directoryName = "/home/opsjava/Delivery/Cession_Assign/Input/";
	//"/home/opsjava/Delivery/Mandates/Input/"
	private String fileName;
	String name;
//	List<String> results= new ArrayList<String>();
	List<ViewDeleteInputModel> viewDeleteInputModelList;
	
	String action;
	

	public ViewDeleteInputFile(String id) {
		super(id);
		this.id = id;
        initializeComponents();
	}

	private void initializeComponents() {

	
		form = new Form("deleteInputFilePanel");
		form.add(createDataTable(new ViewDeleteInputFileProvider()));
		deleteButton = new Button("deleteButton")
		{
			private static final long serialVersionUID = 1L;
			
			@Override
			public void onSubmit()
			{
				boolean deleted = false;
//				File directory   = new File(directoryName);
				//File[] files = file.listFiles();
				
				if(selected.size() > 1)
				{
					error("Please select only one record...");
				}
				else if (selected.size() <= 0)
				{
					error("No record was selected...");
				}

				else
				{
					
					log.info("SELECTED RECORD-----> "+selected);
					
					try
					{
						for (ViewDeleteInputModel viewDeleteInputModel : selected) 
						{
							deleted = moveDeletedFile(viewDeleteInputModel.getFileName());

						}
					}
					catch(IOException ioe)
					{
						log.error("Could not delete file...."+ioe.getMessage());
						ioe.printStackTrace();
					}
					
					if(deleted)
					{

						info("File deleted successfully!");
						form.remove(form.get("dataTable"));
						form.add(createDataTable(new ViewDeleteInputFileProvider()));
						selected.clear();
					}
					
					
					
//						try {
//							for(File files : selected)
//							{
//								moveDeletedFile(files.getName());
//							}
//						}
//							catch (IOException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
						
					/*else
						info("No record was selected");*/
					
				}
			}
			
		};
		deleteButton.setDefaultFormProcessing(false);
		
		searchButton = new Button("searchButton") 
		{
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit() 
			{
				
				log.debug("Search Text: "+searchText.getValue());
				if(searchText.getValue() == null || searchText.getValue().isEmpty())
				{
					form.remove(form.get("dataTable"));
					form.add(createDataTable(new ViewDeleteInputFileProvider()));
				}
				else{
					form.remove(form.get("dataTable"));
					form.add(createDataTable(new ViewDeleteInputFileProvider(searchText.getValue())));
				}
				

			
			}
		};
		searchText = new TextField<String>("searchText", new Model<String>());
		searchText.add(StringValidator.maximumLength(40));
		
		cancelButton = new Button("cancelButton")
		{
			@Override
			public  void onSubmit()
			{
				MarkupContainer markupContainer = form.getParent().getParent();
				markupContainer.remove(form.getParent());
				markupContainer.add(new ViewDeleteInputFile(ViewDeleteInputFile.id));
			}
		};
		form.add(cancelButton);
		
		
		form.add(deleteButton);
		form.add(cancelButton);
		form.add(searchButton);
		form.add(searchText);
		
		add(form);
		
		add(new FeedbackPanel("feedbackPanel"));
		
		
	}
	
	private DefaultDataTable createDataTable(ViewDeleteInputFileProvider  viewDeleteInputFileProvider) 
	{
		DefaultDataTable dataTable;

		List<IColumn> columns = new ArrayList<IColumn>();

		IColumn column = new CheckBoxColumn<ViewDeleteInputModel>(Model.of("Select")) {
		
			private static final long serialVersionUID = 1L;

			@Override
			protected IModel newCheckBoxModel(
					final IModel<ViewDeleteInputModel> rowModel) {
				return new AbstractCheckBoxModel() {

					/**
				 * 
				 */
					private static final long serialVersionUID = 1L;

					@Override
					public boolean isSelected() {
						return selected.contains(rowModel.getObject());
					}

					@Override
					public void select() {
						if (selected.size() > 0) {
							unselect();
						}
						selected.add(rowModel.getObject());
					}

					@Override
					public void unselect() {
						selected.remove(rowModel.getObject());
					}

					@Override
					public void detach() {
						rowModel.detach();
					}
				};
			}
		};
		columns.add(column);
		columns.add(new PropertyColumn(new Model<String>("File Name"),"fileName", "fileName"));
		dataTable = new DefaultDataTable("dataTable", columns,	viewDeleteInputFileProvider, 10);
		return dataTable;
	}
	
	
	/*private DefaultDataTable createDataTable(ViewDeleteInputFileProvider viewDeleteInputFileProvider)
	{
		DefaultDataTable dataTable;
	
//		String name = null;
		List<IColumn> columns = new ArrayList<IColumn>();
		
		IColumn column = new CheckBoxColumn<File[]>(Model.of("Select")) 
		{
          
			private static final long serialVersionUID = 1L;

			@Override
            protected IModel newCheckBoxModel(final IModel<File[]> rowModel) 
            {
               return new AbstractCheckBoxModel() 
               {
            	   			private static final long serialVersionUID = 1L;

						@Override
	                    public boolean isSelected() 
	                    {
	                       return selected.contains(rowModel.getObject());
	                    }

	                    @Override
	                    public void select() 
	                    {
	                    	if(selected.size() > 0)
	                    	{
	                    		unselect();
	                    	}
	                       // selected.add(rowModel.getObject());
	                    }

	                    @Override
	                    public void unselect() 
	                    {
	                        selected.remove(rowModel.getObject());
	                    }
	                    
	                    @Override
	                    public void detach()
	                    {
	                        rowModel.detach();
	                    }
	                };
            }
        };
		columns.add(column);
		
		columns.add(new PropertyColumn(new Model<String>("FileName"), "fileName", "fileName"));
		
		dataTable = new DefaultDataTable("dataTable", columns,viewDeleteInputFileProvider,10);
		
		return dataTable;

	}*/

	
	
/*	private static void moveDeletedFile(String file)
	{
	
		log.info("In the moving files method ############################");
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String today = sdf.format(date); 
		Path source = Paths.get("/home/opsjava/Delivery/Mandates/Input/" + file);
		Path target = Paths.get("/home/opsjava/Delivery/Mandates/Archive/Input/" +today);
		
		log.info("From source ##################to Target"+source);
		log.info("To target #######################################"+target);
		
		File newFolder = new File("/home/opsjava/Delivery/Mandates/Archive/Input/"+today);
		if(!newFolder.exists())
		{
			try 
			{
				newFolder.mkdir();
				
				if(newFolder.exists())
				{
					try
					{
						Files.move(source, target);
					}
					catch (IOException e) 
					{
						log.error("Error on deleting files: "+e.getMessage());
						e.printStackTrace();
					}
				}
				else
				{
					log.info("*****************Failed to create a new Archive directory!*************************");
				}
			} 
			catch(Exception e)
			{
				log.info("*****************Failed to create a new Archive directory!*************************");
			}
		}
		else 
		{
			try
			{
				Files.move(source, target);
			}
			catch (IOException e) 
			{
				log.error("Error on deleting files: "+e.getMessage());
				e.printStackTrace();
			}
		}
	}
	*/
	
	

    private boolean moveDeletedFile(String fileName) throws IOException {

    	
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String today = sdf.format(date); 

        File file = new File("/home/opsjava/Delivery/Cession_Assign/Input/" + fileName);
        
        File dest = new File("/home/opsjava/Delivery/Cession_Assign/Archive/Deleted Files/"+ today +File.separator);
        
        if(!dest.exists()){
        	
        	dest.mkdir();
        }

        	File destination;
              if (file.isFile()) 
              {
                    destination = new File(dest + file.getName());

                    file.renameTo(new File(dest + File.separator + file.getName()));
                    return true;
              }
              else
              {
            	  return false;
              }
        }
  

	


}
