package com.bsva.reports;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.beanio.BeanWriter;
import org.beanio.StreamFactory;

import com.bsva.commons.model.CreditorBankModel;
import com.bsva.commons.model.MandateDailyTransModel;
import com.bsva.commons.model.SystemParameterModel;
import com.bsva.entities.ExceptionReportEntityModel;
import com.bsva.entities.MdtCnfgReportNamesEntity;
import com.bsva.entities.MdtOpsRepSeqNrEntity;
import com.bsva.interfaces.AdminBeanRemote;
import com.bsva.interfaces.PropertyUtilRemote;
import com.bsva.interfaces.ReportBeanRemote;
import com.bsva.interfaces.ServiceBeanRemote;
import com.bsva.interfaces.ValidationBeanRemote;
import com.bsva.utils.Util;
import com.google.common.io.Files;
import com.itextpdf.text.DocumentException;

public class Exception_Report_CSV {
	
	public static Logger log=Logger.getLogger(Exception_Report_CSV.class);
	public static ServiceBeanRemote beanRemote;
	public static ValidationBeanRemote valBeanRemote;
	private static AdminBeanRemote adminBeanRemote;
	private static PropertyUtilRemote propertyUtilRemote;
	private static ReportBeanRemote reportBeanRemote;

	private final static String XML = "PBMD12CSV.xml";
	private String fileName;

	MdtCnfgReportNamesEntity reportNameEntity = new MdtCnfgReportNamesEntity();
	List<ExceptionReportEntityModel>mdtDailyRjctList;
	List<CreditorBankModel> creditorBankModelList;
	CreditorBankModel creditorBankModel;

	String reportName = null, reportNr = null, reportDir = null, tempDir = null,recipientNr = null;
	String activeIndicator = "Y";
	String xlsFileName = null;
	int fileSeqNo =000;
	DateFormat sqlFormat = new SimpleDateFormat("yyyy-MM-dd");
	SystemParameterModel systemParameterModel = new SystemParameterModel();
	int lastSeqNoUsed;

	public void generateReport(Date frontEndDate,boolean frontEnd) throws FileNotFoundException, DocumentException
	{
		contextAdminBeanCheck();
		contextCheck();
		contextValidationBeanCheck();
		contextPropertyFileCheck();
		contextReportCheck();
		
		try
		{
			tempDir = propertyUtilRemote.getPropValue("ExtractTemp.Out");
			reportDir= propertyUtilRemote.getPropValue("Reports.Output");

			reportNr = propertyUtilRemote.getPropValue("RPT.DAILY.EXCEPTION.REPORT");
			log.info("reportDir ==> "+reportDir);

		}
		catch(Exception ex)
		{
			log.error("Daily Exception Report - Could not find MandateMessageCommons.properties in classpath");	
			reportDir = "/home/opsjava/Delivery/Mandates/Output/Reports/";
			tempDir="/home/opsjava/Delivery/Mandates/Output/temp/";
			reportNr = "PBMD12";
		}
		
		reportNameEntity = (MdtCnfgReportNamesEntity) adminBeanRemote.retrieveReportName(reportNr);
		if(reportNameEntity != null) {
			
			systemParameterModel = (SystemParameterModel) adminBeanRemote.retrieveWebActiveSysParameter();
			
			retrieveRjctTransactios(frontEndDate,frontEnd);
				
			if(reportNameEntity.getActiveInd().equalsIgnoreCase(activeIndicator)) {
				
				reportNr = reportNameEntity.getReportNr();
				reportName = reportNameEntity.getReportName();
				
				generateReportDetail(reportNr,frontEndDate,frontEnd);
			}
		}
	}


	public void generateReportDetail(String reportNo,Date frontEndDate,boolean frontEnd) throws DocumentException
	{
		DecimalFormat df = new DecimalFormat("### ### ### ### ### ##0.00");
		DateFormat dateTimeFormat = new SimpleDateFormat("yyyyMMdd_HH:mm:ss");
		DateFormat fileTimeFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
		DateFormat reportDate = new SimpleDateFormat("ddMMyyyy");
		
		Date currentDate = new Date();
		fileSeqNo =fileSeqNo + 1;

	
		if(frontEnd) {
		fileName= tempDir+"0007AC"+reportNo+reportDate.format(frontEndDate).toString()+".001.csv";
		xlsFileName = "0007AC"+reportNo+reportDate.format(frontEndDate).toString()+".001.csv"; 
		
		}else {
			fileName = tempDir+"0007AC"+reportNo+reportDate.format(currentDate).toString()+".001.csv";
			xlsFileName = "0007AC"+reportNo+reportDate.format(currentDate).toString()+".001.csv";

		}
		
		log.info("fileName ==> "+ xlsFileName);
		
		StreamFactory factory = StreamFactory.newInstance();
		factory.loadResource(XML); 

		BeanWriter writer = factory.createWriter("PBMD12CSV", new File(fileName));

		List<ExceptionReportEntityModel> files = new ArrayList<ExceptionReportEntityModel>();
		ExceptionReportEntityModel file = new ExceptionReportEntityModel();

		//SET COLUMNS HEADERS    
		file.setServiceId("Sub-Service ID");
		file.setInstructingAgent("Instructing Agent");
		file.setFileName("File Name");
		file.setErrorCode("Error Code");
		file.setMrti("Mandate Request Transaction Id");
		file.setCredAbbShrtNm("Creditor Abbreviated Short Name");
		file.setDrBranchNr("Debtor Branch Number");

		writer.write(file); 


		//SET DATA TO COLUMNS
		if(mdtDailyRjctList!= null &&mdtDailyRjctList.size() > 0)
		{
//			log.info("mdtDailyTransList----->"+ mdtDailyTransList.size());
			for(ExceptionReportEntityModel entityModel :mdtDailyRjctList)
			{
				log.debug("entityModel----->"+ entityModel);

				ExceptionReportEntityModel txnModel = new ExceptionReportEntityModel();

				txnModel.setServiceId(entityModel.getServiceId());
				txnModel.setInstructingAgent(entityModel.getInstructingAgent());
				txnModel.setFileName(entityModel.getFileName());
				txnModel.setErrorCode(entityModel.getErrorCode());
				txnModel.setMrti(entityModel.getMrti());
				txnModel.setCredAbbShrtNm(entityModel.getCredAbbShrtNm());
				txnModel.setDrBranchNr(entityModel.getDrBranchNr());
				
				writer.write(txnModel); 
			}

		}    
		writer.close(); 
		
		//Copy the report to the Output reports folder 
		try
		{
			copyFile(xlsFileName);
		}
		catch(IOException ioe)
		{
			log.error("Error on copying PBMD12 report to temp "+ioe.getMessage());
			ioe.printStackTrace();
		}
		catch(Exception ex)
		{
			log.error("Error on copying PBMD12 report to temp "+ex.getMessage());
			ex.printStackTrace();
		}
	}

	public List<ExceptionReportEntityModel>retrieveRjctTransactios(Date frontDate,boolean frontEnd)
	{
		mdtDailyRjctList = new ArrayList<ExceptionReportEntityModel>();
		 

		if(frontEnd) {
		mdtDailyRjctList = (List<ExceptionReportEntityModel>) reportBeanRemote.retrieveRjctTransactios(sqlFormat.format(frontDate).toString(),frontEnd);
		}
		else
		{
			mdtDailyRjctList = (List<ExceptionReportEntityModel>) reportBeanRemote.retrieveRjctTransactios(sqlFormat.format(systemParameterModel.getProcessDate()).toString(),frontEnd);	
		}

		return mdtDailyRjctList;
	}

	public  void copyFile(String fileName) throws IOException 
	{
		File tmpFile = new File(tempDir, fileName);
		File destFile = new File(reportDir, fileName);
		
		//log.info("tmpFile ==> "+ tmpFile);
		//log.info("destFile===> "+ destFile);
		
        Files.copy(tmpFile, destFile);
		log.info("Copying "+fileName+"from temp to output directory...");
	}

	

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	//CONTEXT LOOKUPS//
	public static void contextValidationBeanCheck() 
	{
		if (valBeanRemote == null) {
			valBeanRemote = Util.getValidationBean();
		}
	}

	private static void contextCheck() {
		if (beanRemote == null) {
			beanRemote = Util.getServiceBean();
		}
	}

	private static void contextAdminBeanCheck() 
	{
		if (adminBeanRemote == null) 
		{
			adminBeanRemote = Util.getAdminBean();	
		}
	}

	private static void contextPropertyFileCheck() 
	{
		if (propertyUtilRemote == null) 
		{
			propertyUtilRemote = Util.getPropertyUtil();
		}
	}
	
	private static void contextReportCheck() {
		if (reportBeanRemote == null) {
			reportBeanRemote = Util.getReportBean();
		}
	}




}
