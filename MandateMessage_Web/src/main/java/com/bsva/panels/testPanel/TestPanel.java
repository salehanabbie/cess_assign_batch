package com.bsva.panels.testPanel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.wicket.Session;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;

import com.bsva.controller.Controller;
import com.bsva.models.ClientSessionModel;
import com.bsva.utils.ZipUtil;

public class TestPanel extends Panel implements Serializable 
{
	public static Logger log = Logger.getLogger(TestPanel.class);
	private static final long serialVersionUID = 1L;
	private int width = 400;

	private Form form;
	private Button testButton;
	private Button testSOTFilesButton;
	private Button testArchiveBtn;
	private Button testMnthlyReports;
	private Button testPasaReports;

	//Session Info 
	private ClientSessionModel clientSessionModel;
	private String userName;
	public static Session session;

	Controller controller;
	public static String id;

	public TestPanel(String id) 
	{	
		super(id);
		this.id=id;
		initializeComponents();	
	}

	private void initializeComponents() 
	{		
		controller=new Controller();
		form=new Form ("form");

		add(form);

		session = getSession();
		clientSessionModel = (ClientSessionModel) session.getAttribute("role");
		userName = clientSessionModel.getUsername();
		log.debug("clientSessionModel ==> "+clientSessionModel);

		testButton =new Button ("testButton")
		{
			private static final long serialVersionUID = 1L;

			public void onSubmit()
			{	 
//				controller.testPanelCode();
//				controller.retrieveJnlAcq();
			}
		};

		testSOTFilesButton =new Button ("testSOTFilesButton")
		{
			private static final long serialVersionUID = 1L;

			public void onSubmit()
			{	
//				moveFiles();
//				controller.testArchiveProcess();
				controller.generateDailyFileStatsReport(new Date());
			}
		};

		testArchiveBtn =new Button ("testArchiveBtn")
		{
			private static final long serialVersionUID = 1L;

			public void onSubmit()
			{	
				//				controller.summariseErrorCodesData();
				//				controller.retrieveRealTimeErrorCodeReport();
				//				controller.testReportsBackEnd(userName);

				//				Calendar cal = Calendar.getInstance();
				//				cal.clear(); // <- you need this call
				//
				//				cal.set(2018, Calendar.JANUARY, 04); //Year, month and day of month
				//				Date date = cal.getTime();
				//				
				//				try {
				//					Date pastDate = calculateResponseDates(date, 2);
				//					log.info("PAST DATE PARSED ===> "+pastDate);
				//				} catch (ParseException e) {
				//					// TODO Auto-generated catch block
				//					e.printStackTrace();
				//				}

				controller.testArchiveProcess();
				
			}
		};

		testMnthlyReports =new Button ("testMonthlyReports")
		{
			private static final long serialVersionUID = 1L;

			public void onSubmit()
			{	
				//				log.info("Actual Max "+ Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH));
				//				
				//				DateFormat endDateFormat = new SimpleDateFormat("ddMMyyyy"); 
				//				
				//				//Calculate the last date of the month
				//				Calendar nextNotifTime = Calendar.getInstance();
				//				nextNotifTime.add(Calendar.MONTH, 1);
				//				nextNotifTime.set(Calendar.DATE, 1);
				//				nextNotifTime.add(Calendar.DATE, -1);
				//				Date lastDate = nextNotifTime.getTime();
				//
				//				
				//				String strLastDate = endDateFormat.format(lastDate);
				//				log.info("strLastDate ==> "+ strLastDate);
				//				SystemParameterModel systemParamsModel = (SystemParameterModel) controller.retrieveWebActiveSysParameter();
				//				String currentProcDate = endDateFormat.format(systemParamsModel.getProcessDate());
				//				log.info("currentProcDate ==> "+ currentProcDate);
				//				
				//				
				//				if(currentProcDate.equalsIgnoreCase(strLastDate))
				//				{
				//					System.out.println("Dates Match!");
				//				}


				controller.testMontlyReportsBackEnd(userName);


			}
		};

		form.add(testButton);
		form.add(testSOTFilesButton);
		form.add(testArchiveBtn);
		form.add(testMnthlyReports);
		form.add(new FeedbackPanel("feedbackPanel"));
	}

	public Date calculateResponseDates(Date currentDate, int rspnsPeriod) throws ParseException
	{
		Calendar c = Calendar.getInstance(); 
		c.setTime(currentDate); 
		//			c.add(Calendar.DATE, -2);
		c.add(Calendar.DATE, -rspnsPeriod);

		log.info("currentDate********:"+ currentDate);
		log.info("pastDate********:"+ c.getTime());
		return currentDate = c.getTime();
	}


	private void moveFiles()
	{
		try 
		{
//			METHOD 1
//			/******* Clean out archive folder first **********/
//			log.info("In the move files method");
////##### STRUGGLING TO CREATE ZIPPED FILE FIRST			
//			//Create a zip file first
////			File outputZipFile = new File("C:\\home\\MyFile.zip");
//			
//			//Creating the directory
////			boolean boolInputZip = outputZipFile.mkdir();
////			log.info("boolInputZip: "+boolInputZip);
//			boolean boolInputZip = true;
//			if(boolInputZip){
//				System.out.println("Zip Directory created successfully");
//				log.info("In the moving files method ############################");
//				ZipUtil zipUtil = new ZipUtil();
//				boolean zipComp = zipUtil.zipFiles();
//				
//				if(zipComp) {
//					//Delete Input Folder
//					Path myZipPath = Paths.get("C:\\testzip");
//					Files.delete(myZipPath);
//				}
//			}else{
//				System.out.println("Sorry couldn’t create specified directory");
//			}
			
			
//			METHOD 2
//			String folderToZip = "/home/testzip";
//		    String zipName = "/home/MyZip.zip";
//		    ZipUtil.zipFolder(Paths.get(folderToZip), Paths.get(zipName));
////		    zipFolder(Paths.get(folderToZip), Paths.get(zipName));
//		    
//		    //Delete Current Folder
//		    log.info("****** Deleting "+folderToZip +" ********");
//		    Files.delete(Paths.get(folderToZip));
		    
		   //Test using End of Day Logic Class
		}
		catch (Exception e) 
		{
			log.error("Error on clean out of processing folder: "+e.getMessage());
			e.printStackTrace();
		}
	}
	
//	public void zipFolder(final Path sourceFolderPath, Path zipPath) throws Exception 
//	{	
//		log.info("In the zipFolder method ");
//       final ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipPath.toFile()));
//        
//        Files.walkFileTree(sourceFolderPath, new SimpleFileVisitor<Path>() {
//            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
//                zos.putNextEntry(new ZipEntry(sourceFolderPath.relativize(file).toString()));
//                Files.copy(file, zos);
//                zos.closeEntry();
//                return FileVisitResult.CONTINUE;
//            }
//        });
//        zos.close();
//    }



}


