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
import com.bsva.entities.CasCnfgReportNamesEntity;
import com.bsva.interfaces.AdminBeanRemote;
import com.bsva.interfaces.PropertyUtilRemote;
import com.bsva.interfaces.ServiceBeanRemote;
import com.bsva.interfaces.ValidationBeanRemote;
import com.bsva.utils.Util;
import com.google.common.io.Files;
import com.itextpdf.text.DocumentException;

/**
 * @author SalehaR
 *
 */
public class DailyBatchBillableTxnReport 
{

//	private String downloaddirectory ="/home/opsjava/Delivery/Mandates/Reports/";
//	private String tempDir="/home/opsjava/Delivery/Mandates/Output/temp/";
	
	public static Logger log=Logger.getLogger(DailyBatchBillableTxnReport.class);
	public static ServiceBeanRemote beanRemote;
	public static ValidationBeanRemote valBeanRemote;
	private static AdminBeanRemote adminBeanRemote;
	private static PropertyUtilRemote propertyUtilRemote;

	private final static String XML = "MR018CSV.xml";
	private String fileName;

	CasCnfgReportNamesEntity reportNameEntity = new CasCnfgReportNamesEntity();
	List<MandateDailyTransModel>mdtDailyTransList = new ArrayList<MandateDailyTransModel>();
	List<CreditorBankModel> creditorBankModelList;
	CreditorBankModel creditorBankModel;

	String reportName = null, reportNr = null, reportDir = null, tempDir = null,recipientNr = null;
	String activeIndicator = "Y";
	int fileSeqNo =000;


	public void generateReport() throws FileNotFoundException, DocumentException
	{
		contextAdminBeanCheck();
		contextCheck();
		contextValidationBeanCheck();
		contextPropertyFileCheck();
		
		try
		{
			tempDir = propertyUtilRemote.getPropValue("ExtractTemp.Out");
//			log.info("tempDir ==> "+tempDir);
			reportDir= propertyUtilRemote.getPropValue("Reports.Output");

			recipientNr = propertyUtilRemote.getPropValue("AC.ACH.RPT.RECIPIENT.NUMBER");
			log.info("reportDir ==> "+reportDir);

		}
		catch(Exception ex)
		{
			log.error("Daily Batch Billable Txn - Could not find CessionAssignment.properties in classpath");
			reportDir = "/home/opsjava/Delivery/Cession_Assign/Output/Reports/";
			tempDir="/home/opsjava/Delivery/Cession_Assign/Output/temp/";
		}

		retrieveMndtBillingTransactions();
		
		if(reportNameEntity != null) {
			
			if(reportNameEntity.getActiveInd().equalsIgnoreCase(activeIndicator)) {
				generateReportDetail(reportNr, reportName);
			}
		}
	}


	public void generateReportDetail(String reportNo,String reportname) throws DocumentException
	{
		DecimalFormat df = new DecimalFormat("### ### ### ### ### ##0.00");
		DateFormat dateTimeFormat = new SimpleDateFormat("yyyyMMdd_HH:mm:ss");
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat fileTimeFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
		DateFormat reportDate = new SimpleDateFormat("ddMMyyyy");
		
		Date currentDate = new Date();
		fileSeqNo =fileSeqNo + 1;

//		fileName =reportNo+"-"+reportname+"_"+fileTimeFormat.format(currentDate).toString()+".csv";	
		fileName =recipientNr+reportNo+"AC"+reportDate.format(currentDate).toString()+".csv";	
		log.info("fileName ==> "+ fileName);
		
		StreamFactory factory = StreamFactory.newInstance();
		factory.loadResource(XML); 

		BeanWriter writer = factory.createWriter("mr018ReportFile", new File(tempDir + getFileName()));

		List<MandateDailyTransModel> files = new ArrayList<MandateDailyTransModel>();
		MandateDailyTransModel file = new MandateDailyTransModel();

		//SET COLUMNS HEADERS       
		file.setCreditorBank("Creditor Bank");
		file.setDebtorBank("Debtor Bank");
		file.setServiceId("Service Id");
		file.setTxnType("Transaction Type");
		file.setActionDate("Action Date");
		file.setRespDate("Response Date");
		file.setExtractMsgId("Extract Message Id");
		file.setMndtReqTransId("Mandate Request Transaction Id");
		file.setMndtRefNumber("Mandate Reference Number");
		file.setAuthCode("Authorisation Code");
		file.setTrxnStatus("Transaction Status");

		writer.write(file); 


		//SET DATA TO COLUMNS
		if(mdtDailyTransList!= null &&mdtDailyTransList.size() > 0)
		{
//			log.info("mdtDailyTransList----->"+ mdtDailyTransList.size());
			for(MandateDailyTransModel dailyTransModel :mdtDailyTransList)
			{
				log.debug("dailyTransModel----->"+ dailyTransModel);

				MandateDailyTransModel txnModel = new MandateDailyTransModel();

				txnModel.setCreditorBank(dailyTransModel.getCreditorBank());
				txnModel.setDebtorBank(dailyTransModel.getDebtorBank());
				txnModel.setServiceId(dailyTransModel.getServiceId());
				txnModel.setTxnType(dailyTransModel.getTxnType());
				txnModel.setActionDate(dailyTransModel.getActionDate());
				txnModel.setRespDate(dailyTransModel.getRespDate());
				txnModel.setExtractMsgId(dailyTransModel.getExtractMsgId());
				txnModel.setMndtReqTransId(dailyTransModel.getMndtReqTransId());
				txnModel.setMndtRefNumber(dailyTransModel.getMndtRefNumber());
				txnModel.setAuthCode(dailyTransModel.getAuthCode());
				txnModel.setTrxnStatus(dailyTransModel.getTrxnStatus());

				writer.write(txnModel); 
			}
		}    
		writer.close(); 
		
		//Copy the report to the Output reports folder 
		try
		{
			copyFile(fileName);
		}
		catch(IOException ioe)
		{
			log.error("Error on copying MR018 report to temp "+ioe.getMessage());
			ioe.printStackTrace();
		}
		catch(Exception ex)
		{
			log.error("Error on copying MR018 report to temp "+ex.getMessage());
			ex.printStackTrace();
		}
	}

	public List<MandateDailyTransModel>retrieveMndtBillingTransactions()
	{
		String tt2TxnType = propertyUtilRemote.getPropValue("AC.TT2SubService"); 
		String mr018 = propertyUtilRemote.getPropValue("RPT.DAILY.BATCH.BILL");


		//Retrieve Report Name
		reportNameEntity = (CasCnfgReportNamesEntity) adminBeanRemote.retrieveReportName(mr018);

		if(reportNameEntity != null)
		{
			reportNr = reportNameEntity.getReportNr();
			reportName = reportNameEntity.getReportName();
		}

		mdtDailyTransList = (List<MandateDailyTransModel>) adminBeanRemote.retrieveMndtBillingTransactions(tt2TxnType);

		return mdtDailyTransList;
	}

	public  void copyFile(String fileName) throws IOException 
	{
		File tmpFile = new File(tempDir, fileName);
		File destFile = new File(reportDir, fileName);
		
//		log.info("tmpFile ==> "+ tmpFile);
//		log.info("destFile===> "+ destFile);
		
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




}
