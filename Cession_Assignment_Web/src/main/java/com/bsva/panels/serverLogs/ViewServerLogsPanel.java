package com.bsva.panels.serverLogs;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.ListChoice;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

public class ViewServerLogsPanel extends Panel 
{
	private static final long serialVersionUID = 1L;
	private TextArea<String> serverlogs;
	private ListChoice<String> server;
	private String dataIn;
	private Form form;
	//private final String path=System.getenv("JBOSS_HOME")+ "\\standalone\\log\\manUser.log";  
	//private final String path = "C:\\apps\\wildfly-8.1.0.Final\\standalone\\log\\manUser";
	
	public ViewServerLogsPanel(String id) {
		super(id);

		initializeComponents();
	}

	private void initializeComponents() {

		form = new Form("serverlogsForm");
		add(form);

		FeedbackPanel feedbackPanel = new FeedbackPanel("feedback_1");
		add(feedbackPanel);

		try {
		/*	System.out.println("path : " + path);
			File file = new File(path);
			System.out.println("Does  exisit" + file.exists());
			FileInputStream fstream = new FileInputStream(path);
			StringBuilder builder = new StringBuilder();
			try(FileInputStream inputStream = new FileInputStream(file)) {
	            int data = inputStream.read();
	                while(data != -1) {
	                    builder.append((char)data);
	                    data = inputStream.read();
	                }
	        } catch (Exception ex) {
	          ex.printStackTrace();
	        }
			dataIn = builder.toString(); 
			
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getCause());
		}*/

		
				// Open the file that is the first
				// command line parameter	
				
				/*FileInputStream fstream = new FileInputStream//("C:/home2/Cdv_Uploads/Log.txt");
				( System.getenv("JBOSS_HOME") + "\\standalone\\log\\manUser.log");
				System .out .println("Path name*****" + fstream);
				System .out .println("JBOSS*****" + System.getenv("JBOSS_HOME") + "\\standalone\\log\\manUser.log");*/
				FileInputStream fstream = new FileInputStream("/home/opsjava/wildfly-8.1.0.Final/standalone/log/manUser.log");
				System .out .println("Path name*****" + fstream);
				BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
				 StringBuilder sbFile = new StringBuilder();
				String line;
			    int counter = 0;
				// Read File Line By Line
				while ((line = br.readLine()) != null)
				{ 
					// Print the content on the console
					line = br.readLine();
					if (line == null) {
						Thread.sleep(200);
					} else {
						counter ++; 
						 sbFile.append(line+"\n");
//						 if (counter == 10000) {
//							 break;
//						}
						     
					}
				}

				// Close the input stream
				fstream.close();

				dataIn = sbFile.toString();
			} catch (Exception e) {// Catch exception if any
				System.err.println("Error: " +e.getCause());
			}		
		serverlogs = new TextArea<String>("serverlogs", new Model<String>(dataIn));
		serverlogs.setEnabled(false);
		form.add(serverlogs);
	  }
	}


