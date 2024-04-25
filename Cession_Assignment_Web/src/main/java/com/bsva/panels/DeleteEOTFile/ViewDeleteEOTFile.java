package com.bsva.panels.DeleteEOTFile;

import java.io.File;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.validation.validator.StringValidator;
import com.bsva.commons.model.SysCisBankModel;
import com.bsva.controller.Controller;
import com.bsva.models.ViewDeleteEOTFileModel;
import com.bsva.models.WebAcOpsSotEotModel;
import com.bsva.models.WebServicesModel;
import com.bsva.models.WebSysCisBankModel;
import com.bsva.panels.CheckBoxColumn;
import com.bsva.provider.ViewDeleteEOTFileProvider;
import com.bsva.provider.ViewDeleteInputFileProvider;
import com.bsva.translator.WebAdminTranslator;

/***
 * 
 * @author DimakatsoN
 *
 */

public class ViewDeleteEOTFile extends Panel implements Serializable{
	
	
	Controller controller;
	public static Logger log = Logger.getLogger(ViewDeleteEOTFile.class);
	public static String id;
	private Form form;
	private Set<ViewDeleteEOTFileModel> selected = new HashSet<ViewDeleteEOTFileModel>();
	private Button deleteButton;
	private Button cancelButton;
	private Button searchButton;
	private TextField<String> searchText;
	String directoryName = "/home/opsjava/Delivery/Mandates/Input/Processing/";
	//"/home/opsjava/Delivery/Mandates/Input/"
	private String fileName;
	String name;
//	List<String> results= new ArrayList<String>();
	List<ViewDeleteEOTFileModel> viewDeleteEOTFileModelList;
	private WebSysCisBankModel webSysCisBankModel;
	private List<WebSysCisBankModel> instIdList = new ArrayList<WebSysCisBankModel>();
	private DropDownChoice<WebSysCisBankModel> instId;
	
	String action;
	

	public ViewDeleteEOTFile(String id) {
		super(id);
		this.id = id;
        initializeComponents();
	}
	
	public ViewDeleteEOTFile(String id,WebSysCisBankModel webSysCisBankModel,String action )
	{
		super(id);
		this.action = action;
		this.id = id;
		this.webSysCisBankModel = webSysCisBankModel;
		initializeComponents();
		log.debug("Screen ID: " + action);

	}


	private void initializeComponents() {
	
		controller = new Controller();
		form = new Form("deleteEOTFilePanel");
		loadData();
		form.add(createDataTable(new ViewDeleteEOTFileProvider()));
		
		
		
		instId = new DropDownChoice<WebSysCisBankModel>("instId",new Model<WebSysCisBankModel>(), instIdList,new ChoiceRenderer<WebSysCisBankModel>());
		instId.setRequired(false);

		if (action == "create")
		{
			if(instIdList != null && instIdList.size() > 0)
			{
				instId.setDefaultModelObject(instId.getChoices().get(0).getMemberNo());
			}
		}
		else if (action == "update") 
		{
			int sel = 0;
			for (int index = 0; index < instIdList.size(); index++) 
			{

				if (webSysCisBankModel.getMemberNo().equalsIgnoreCase(
						instIdList.get(index).getMemberNo())) 
				{
					sel = index;
				}

			}
			instId.setModelObject(instId.getChoices().get(sel));
		}

		form.add(instId);

		
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
						for (ViewDeleteEOTFileModel viewDeleteEOTFileModel : selected) 
						{
							deleted = moveDeletedFile(viewDeleteEOTFileModel.getFileName());

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
						form.add(createDataTable(new ViewDeleteEOTFileProvider()));
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
				if((searchText.getValue() == null || searchText.getValue().isEmpty())&& (instId.getValue() == null || instId.getValue().isEmpty()))
				{
					form.remove(form.get("dataTable"));
					form.add(createDataTable(new ViewDeleteEOTFileProvider()));
				}
				else if(instId != null)
				{
					String memberNo =  instId.getChoices().get(Integer.parseInt(instId.getValue())).getMemberNo();
					form.remove(form.get("dataTable"));
					form.add(createDataTable(new ViewDeleteEOTFileProvider(memberNo)));
				}
				
				else{
					form.remove(form.get("dataTable"));
					form.add(createDataTable(new ViewDeleteEOTFileProvider(searchText.getValue())));
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
				markupContainer.add(new ViewDeleteEOTFile(ViewDeleteEOTFile.id));
			}
		};
		form.add(cancelButton);
		
		
		form.add(deleteButton);
		form.add(searchButton);
		form.add(searchText);
		
		add(form);
		
		add(new FeedbackPanel("feedbackPanel"));
		
	}
	
	private DefaultDataTable createDataTable(ViewDeleteEOTFileProvider  viewDeleteEOTFileProvider) 
	{
		DefaultDataTable dataTable;

		List<IColumn> columns = new ArrayList<IColumn>();

		IColumn column = new CheckBoxColumn<ViewDeleteEOTFileModel>(Model.of("Select")) {
		
			private static final long serialVersionUID = 1L;

			@Override
			protected IModel newCheckBoxModel(
					final IModel<ViewDeleteEOTFileModel> rowModel) {
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
		dataTable = new DefaultDataTable("dataTable", columns,	viewDeleteEOTFileProvider, 10);
		return dataTable;
	}
	
	
	
	 private boolean moveDeletedFile(String fileName) throws IOException {

	    	
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String today = sdf.format(date); 

	        File file = new File("/home/opsjava/Delivery/Mandates/Input/Processing/" + fileName);
	        
	        File dest = new File("/home/opsjava/Delivery/Mandates/Archive/Deleted Files/"+ today +File.separator);
	        
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
	 
	 
	 private void loadData() 
		{		

		
		// Generate InstId for drop down box 
			List<SysCisBankModel> instIdListFromDb = new ArrayList<SysCisBankModel>();

			instIdListFromDb = (List<SysCisBankModel>) controller.viewAllMember();
			Collections.sort(instIdListFromDb, new memberNameOrderSorter());

			if (instIdListFromDb.size() > 0) 
			{
				for (SysCisBankModel localCommonModel : instIdListFromDb) 
				{
					WebSysCisBankModel webModel = new WebSysCisBankModel();
					webModel = new WebAdminTranslator().translateCommonsSysCisBankModelToWebModel(localCommonModel);
					 //controller.viewAllMember();           
					   instIdList.add(webModel);
					   
				}
			}// end of if
			
		}
	 
	// Sort MemberName in ASCENDING order
		private class memberNameOrderSorter implements Comparator<SysCisBankModel>
		{

			@Override
			public int compare(SysCisBankModel o1, SysCisBankModel o2) {
				if(o1.getMemberName() == null && o2.getMemberName() == null)
				{
					return 0;
				}
				else if(o1.getMemberName() == null)
				{
					return -1;
				}
				else if(o2.getMemberName() == null)
				{
					return 1;
				}
				return o1.getMemberName().compareTo(o2.getMemberName());
				
			}
			
		}


}
