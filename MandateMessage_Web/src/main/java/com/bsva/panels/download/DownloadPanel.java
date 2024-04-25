package com.bsva.panels.download;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.wicket.Session;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import com.bsva.controller.Controller;

import com.bsva.models.ClientSessionModel;
import com.csvreader.CsvWriter;

public class DownloadPanel extends Panel  {


	private static final long serialVersionUID = 1L;

	private Button downloadButton;

	private ClientSessionModel clientSessionModel;

	public static Session session;

	private Form form;

	public static String id;

	
	
	public Controller controller = new Controller();
	
	

	public DownloadPanel(String id) {

		super(id);
		this.id = id;
		initializeComponents();

	}

	public void initializeComponents() {

		form = new Form("form");

		session = getSession();
		clientSessionModel = (ClientSessionModel) session.getAttribute("role");
				

		downloadButton = new Button("downloadButton") {

			@Override
			public void onSubmit() {

				try {

					if ((clientSessionModel.getUserRole()
							.equalsIgnoreCase("MANDATE_CREDITOR_BANK"))
							|| (clientSessionModel.getUserRole()
									.equalsIgnoreCase("MANDATE_DEBTOR_BANK")))

					{

					} else if ((clientSessionModel.getUserRole()
							.equalsIgnoreCase("MANDATE_CREDITOR"))) {
						
						System.out.println("check a role======== "+ clientSessionModel.getUserRole());

					}
					//Controller.Mdte_001_001_FileLoader(id);
					//Controller.retrieveAllMdteReqDwnId(id);
		
				
					
					
					
					System.out.println("check a role======== "+ clientSessionModel.getUserRole());

					System.out.println("check if it getting role");
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		};
		
		
		
		downloadButton.setDefaultFormProcessing(true);
		add(form);
		form.add(downloadButton);

	   }
	    


	   public void GenetateMdet002() throws FileNotFoundException, IOException {

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String dates = dateFormat.format(date);

		String FILE = "/home/jboss/Mandate/Output/ " + "MandateFile_" + dates
				+ "."
				+ "mdte";
            

		CsvWriter csvOutput = new CsvWriter(new FileWriter(FILE, true), ',');

		csvOutput.write("INTERCHANGE");
		csvOutput.endRecord();
		csvOutput.close();

	 }
	   
	   public  void readMdte001() throws FileNotFoundException, IOException
	   {  
		   

     }
}
