package com.bsva.panels.viewFiles;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Serializable;

import org.apache.log4j.Logger;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.ListChoice;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

import com.bsva.TemplatePage;
import com.bsva.controller.Controller;
import com.bsva.panels.AC_viewFileStatus.ViewFileStatusPanel;

/**
 * @author SalehaR
 *
 */
public class ViewFile extends Panel implements Serializable 
{
	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(ViewFile.class);
	
	public static String id;
	
	private TextArea<String> viewFile;
	private ListChoice<String> server;
	private String dataIn, inputDirectory, outputDirectory;
	private Form form;
	private String fileName;
	private Button backButton;
	Controller controller;
	private String fileDirection = null;
	
	ViewIncomingFiles viewIncomingFiles;
	ViewOutgoingFiles viewOutgoingFiles;
	

	
	public ViewFile(String id, String fileName, String fileDir) 
	{
		super(id);
		this.id = id;
		this.fileDirection = fileDir;
		controller = new Controller();
		
		
		
		initializeComponents(fileName);
	}
	
	private void initializeComponents(String fileName) 
	{
		inputDirectory = controller.getProperty("ProcessingFile.In");
		outputDirectory = controller.getProperty("ExtractTemp.Out");

		
		form = new Form("viewFileForm");
		add(form);

		FeedbackPanel feedbackPanel = new FeedbackPanel("feedback_1");
		add(feedbackPanel);

		try 
		{
			// Open the file that is the first
			// command line parameter
			
			log.info("inputDirectory ==> "+inputDirectory);
			log.info("outputDirectory ==> "+outputDirectory);
			log.info("fileName ==> "+fileName);
			FileInputStream fstream;
			
			if(fileDirection.equalsIgnoreCase("INC"))
			{
				log.info("inputDirectory + fileName ==>"+inputDirectory + fileName);
				fstream = new FileInputStream(inputDirectory + fileName/*+ ".xml"*/);
			}
			else
			{
				log.info("inputDirectory + fileName ==>"+outputDirectory + fileName);
				fstream = new FileInputStream(outputDirectory + fileName+ ".xml");
			}
				

			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			StringBuilder sbFile = new StringBuilder();
			String line;
			int counter = 0;
			
			// Read File Line By Line
			while ((line = br.readLine()) != null)
			{
				// Print the content on the console
				line = br.readLine();
				
				if (line == null) 
				{
					Thread.sleep(2000);
				} 
				else 
				{
					counter++;
					sbFile.append(line + "\n");
					if (counter == 70000) 
					{
						break;
					}
				}
			}

			// Close the input stream
			fstream.close();

			dataIn = sbFile.toString();
		} 
		catch (Exception e) {// Catch exception if any
			log.error("Error: " + e.getMessage());
		}

		viewFile = new TextArea<String>("viewFile", new Model<String>(dataIn));
		viewFile.setEscapeModelStrings(false);
		viewFile.setEnabled(false);
		form.add(viewFile);
		
		backButton = new Button("backButton") 
		{
			@Override
			public void onSubmit() 
			{
				if(fileDirection.equalsIgnoreCase("INC"))
				{
					viewIncomingFiles = new ViewIncomingFiles(id);
					viewIncomingFiles.setOutputMarkupId(true);
					viewIncomingFiles.setOutputMarkupPlaceholderTag(true);
					MarkupContainer markupContainer = form .getParent().getParent();
					markupContainer.remove(form.getParent());
					markupContainer.add(viewIncomingFiles);
					//TemplatePage.setContent(viewIncomingFiles);	
				}
				else
				{
					viewOutgoingFiles = new ViewOutgoingFiles(id);
					viewOutgoingFiles.setOutputMarkupId(true);
					viewOutgoingFiles.setOutputMarkupPlaceholderTag(true);
					MarkupContainer markupContainer = form .getParent().getParent();
					markupContainer.remove(form.getParent());
					markupContainer.add(viewOutgoingFiles);
					//TemplatePage.setContent(viewOutgoingFiles);	
				}
							
			}
		};
		backButton.setDefaultFormProcessing(false);
		form.add(backButton);
	}
}
