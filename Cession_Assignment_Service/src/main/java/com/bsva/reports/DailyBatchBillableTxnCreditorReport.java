package com.bsva.reports;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.beanio.BeanWriter;
import org.beanio.StreamFactory;

import com.bsva.commons.model.CreditorBankModel;
import com.bsva.commons.model.CustomerParametersModel;
import com.bsva.commons.model.MandateDailyTransModel;
import com.bsva.entities.MdtCnfgReportNamesEntity;
import com.bsva.entities.MdtOpsRepSeqNrEntity;
import com.bsva.interfaces.AdminBeanRemote;
import com.bsva.interfaces.PropertyUtilRemote;
import com.bsva.interfaces.ServiceBeanRemote;
import com.bsva.interfaces.ValidationBeanRemote;
import com.bsva.utils.Util;
import com.google.common.io.Files;

/**
 * @author SalehaR - 2017-08-21 - Update Report Data
 *
 */
public class DailyBatchBillableTxnCreditorReport 
{
	//	private String downloaddirectory ="/home/opsjava/Delivery/Mandates/Reports/";
	public static Logger log=Logger.getLogger(DailyBatchBillableTxnCreditorReport.class);
	private final static String XML = "PBMD04CSV.xml";
	private String fileName;

	public static ServiceBeanRemote beanRemote;
	public static ValidationBeanRemote valBeanRemote;
	private static AdminBeanRemote adminBeanRemote;
	private static PropertyUtilRemote propertyUtilRemote;

	List<CreditorBankModel> creditorBankModelList;
	CreditorBankModel creditorBankModel ;

	List<CustomerParametersModel> custParamsModelList;
	CustomerParametersModel customerParametersModel = new CustomerParametersModel();

	String reportName, reportNr , reportDir = null, tempDir = null;
	String pbmd04;
	int lastSeqNoUsed;
	String tt2TxnType;
	String activeIndicator = "Y";

	public DailyBatchBillableTxnCreditorReport()
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
//			log.info("reportDir ==> "+reportDir);

			pbmd04 = propertyUtilRemote.getPropValue("RPT.DAILY.TXN.CRED");
			tt2TxnType = propertyUtilRemote.getPropValue("AC.TT2SubService"); 
		}
		catch(Exception ex)
		{
			log.error("Daily Batch Billable Txn - Could not find MandateMessageCommons.properties in classpath");	
			reportDir = "/home/opsjava/Delivery/Mandates/Output/Reports/";
			tempDir="/home/opsjava/Delivery/Mandates/Output/temp/";
		}
	}


	public void generateMandateDailyTransCreditorReport() throws ParseException
	{
		//Retrieve Report Name
		MdtCnfgReportNamesEntity reportNameEntity = new MdtCnfgReportNamesEntity();
		reportNameEntity = (MdtCnfgReportNamesEntity) adminBeanRemote.retrieveReportName(pbmd04);
//		log.info("reportNameEntity ==>"+reportNameEntity);
		if(reportNameEntity != null)
		{
			reportNr = reportNameEntity.getReportNr();
			reportName = reportNameEntity.getReportName();
			
			if(reportNameEntity.getActiveInd().equalsIgnoreCase(activeIndicator)) {
				
				creditorBankModelList = new ArrayList<CreditorBankModel>();
				creditorBankModelList = (List<CreditorBankModel>) adminBeanRemote.retrieveActiveCreditorBank();
//				log.info("creditorBankModelList ==>"+creditorBankModelList.size());
				if(creditorBankModelList.size() > 0)
				{
					for (CreditorBankModel creditorBankModel : creditorBankModelList) 
					{
						List<MandateDailyTransModel> mandateDailyTransModelList = new ArrayList<MandateDailyTransModel>();

						mandateDailyTransModelList=(List<MandateDailyTransModel>) adminBeanRemote.retrieveMndtDailyTransPerCreditor(creditorBankModel.getMemberNo(), tt2TxnType);

						if(mandateDailyTransModelList!= null && mandateDailyTransModelList.size() > 0)
						{
							log.info("*****GENERATING PMBD04 REPORT for "+creditorBankModel.getMemberNo()+"*****");
							generateReportDetail(creditorBankModel.getMemberNo(), mandateDailyTransModelList);
						}
					}
				}
			}
		}
	}

	public void generateReportDetail(String memberId,List<MandateDailyTransModel>mdtDailyTransList)
	{
		DecimalFormat df = new DecimalFormat("### ### ### ### ### ##0.00");
		DateFormat dateTimeFormat = new SimpleDateFormat("yyyyMMdd_HH:mm:ss");
		DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
		DateFormat fileTimeFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
		Date currentDate = new Date();

		String bankId= memberId.substring(2, 6);
		String strSeqNo; 


		MdtOpsRepSeqNrEntity mdtOpsRepSeqNrEntity = new MdtOpsRepSeqNrEntity();
		mdtOpsRepSeqNrEntity = (MdtOpsRepSeqNrEntity)adminBeanRemote.retrieveRepSeqNr(reportNr,memberId);

		if(mdtOpsRepSeqNrEntity != null)
		{
			lastSeqNoUsed = Integer.valueOf(mdtOpsRepSeqNrEntity.getLastSeqNo());
			lastSeqNoUsed = lastSeqNoUsed + 1;
		}
		else
			lastSeqNoUsed = 1;

		strSeqNo = String.format("%06d",lastSeqNoUsed);
		mdtOpsRepSeqNrEntity.setLastSeqNo(strSeqNo);
		adminBeanRemote.updateReportSeqNr(mdtOpsRepSeqNrEntity);


		String reportSeqNo = strSeqNo.substring(3,6);


		fileName =bankId+"AC"+reportNr+dateFormat.format(currentDate).toString()+"."+reportSeqNo+".csv";     	
		StreamFactory factory = StreamFactory.newInstance();
		factory.loadResource(XML); 

		BeanWriter writer = factory.createWriter("pbmd04ReportFile", new File(tempDir + getFileName()));

		MandateDailyTransModel file2 = new MandateDailyTransModel();

		//SET COLUMNS HEARDERS
		file2.setCreditorBank("Creditor Bank");
		file2.setDebtorBank("Debtor Bank");
		file2.setServiceId("Service Id");
		file2.setTxnType("Transaction Type");
		file2.setActionDate("Action Date");
		file2.setRespDate("Response Date");
		file2.setExtractMsgId("Extract Message Id");
		file2.setMndtReqTransId("Mandate Request Transaction Id");
		file2.setMndtRefNumber("Mandate Reference Number");
		file2.setAuthCode("Authorisation Code");
		file2.setTrxnStatus("Transaction Status");
		writer.write(file2); 

		//SET DATA TO COLUMNS

		for(MandateDailyTransModel dailyTransModel : mdtDailyTransList)
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

		writer.close(); 
		//Copy the report to the Output reports folder 
		try
		{
			copyFile(fileName);
		}
		catch(IOException ioe)
		{
			log.error("Error on copying PBMD04 report to temp "+ioe.getMessage());
			ioe.printStackTrace();
		}
		catch(Exception ex)
		{
			log.error("Error on copying PBMD04 report to temp "+ex.getMessage());
			ex.printStackTrace();
		}
	}

	public  void copyFile(String fileName) throws IOException 
	{
		File tmpFile = new File(tempDir, fileName);
		File destFile = new File(reportDir, fileName);
		
//		log.info("tmpFile ==> "+ tmpFile);
//		log.info("destFile===> "+ destFile);
		
        Files.copy(tmpFile, destFile);
		log.info("Copying "+fileName+" from temp to output directory...");
	}

	public String getFileName() 
	{
		return fileName;
	}

	public void setFileName(String fileName) 
	{
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
