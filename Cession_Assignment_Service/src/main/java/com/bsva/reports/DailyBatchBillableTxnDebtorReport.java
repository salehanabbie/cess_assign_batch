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

import com.bsva.commons.model.DebtorBankModel;
import com.bsva.commons.model.MandateDailyTransModel;
import com.bsva.entities.CasCnfgReportNamesEntity;
import com.bsva.entities.CasOpsRepSeqNrEntity;
import com.bsva.interfaces.AdminBeanRemote;
import com.bsva.interfaces.PropertyUtilRemote;
import com.bsva.interfaces.ServiceBeanRemote;
import com.bsva.interfaces.ValidationBeanRemote;
import com.bsva.utils.Util;
import com.google.common.io.Files;

/**
 * 
 * @author DimakatsoN
 * @author SalehaR - Update Report Content
  * @author SalehaR-2019/05/06 Debug Statements & Code CleanUp
 */

public class DailyBatchBillableTxnDebtorReport {

	//	private String downloaddirectory ="/home/opsjava/Delivery/Mandates/Reports/";
	public static Logger log=Logger.getLogger(DailyBatchBillableTxnDebtorReport.class);
	public static ServiceBeanRemote beanRemote;
	public static ValidationBeanRemote valBeanRemote;
	private static AdminBeanRemote adminBeanRemote;
	private static PropertyUtilRemote propertyUtilRemote;


	private final static String XML = "PBMD05CSV.xml";
	private String fileName;

	List<DebtorBankModel> debtorBankModelList;
	DebtorBankModel debtorBankModel ;

	String reportName, reportNr, reportDir = null, tempDir = null;
	int lastSeqNoUsed;
	String pbmd05;
	String tt2TxnType;
	String activeIndicator = "Y";

	public DailyBatchBillableTxnDebtorReport()
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

			pbmd05 = propertyUtilRemote.getPropValue("RPT.DAILY.TXN.DEBT");
			tt2TxnType = propertyUtilRemote.getPropValue("AC.TT2SubService"); 
		}
		catch(Exception ex)
		{
			log.error("Daily Batch Billable Txn - Could not find CessionAssignment.properties in classpath");
			reportDir = "/home/opsjava/Delivery/Cession_Assign/Output/Reports/";
			tempDir="/home/opsjava/Delivery/Cession_Assign/Output/temp/";
		}
	}


	public void generateMandateDailyTransDebtorReport() throws ParseException
	{
		//Retrieve Report Name
		CasCnfgReportNamesEntity reportNameEntity = new CasCnfgReportNamesEntity();
		reportNameEntity = (CasCnfgReportNamesEntity) adminBeanRemote.retrieveReportName(pbmd05);
//		log.info("reportNameEntity ==>"+reportNameEntity);
		if(reportNameEntity != null)
		{
			reportNr = reportNameEntity.getReportNr();
			reportName = reportNameEntity.getReportName();
			
			if(reportNameEntity.getActiveInd().equalsIgnoreCase(activeIndicator)) {
				
				debtorBankModelList = new ArrayList<DebtorBankModel>();
				debtorBankModelList = (List<DebtorBankModel>) adminBeanRemote.retrieveActiveDebtorBank();

				if(debtorBankModelList.size() > 0)
				{
					for (DebtorBankModel debtorBankModel : debtorBankModelList) 
					{
						List<MandateDailyTransModel> mandateDailyTransModelList = new ArrayList<MandateDailyTransModel>();

						mandateDailyTransModelList=(List<MandateDailyTransModel>) adminBeanRemote.retrieveMndtDailyTransPerDebtor(debtorBankModel.getMemberNo(), tt2TxnType);

						if(mandateDailyTransModelList!= null && mandateDailyTransModelList.size() > 0)
						{
							log.info("*****GENERATING PMBD05 REPORT for "+debtorBankModel.getMemberNo()+"*****");
							generateReportDetail(debtorBankModel.getMemberNo(), mandateDailyTransModelList);
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


		CasOpsRepSeqNrEntity casOpsRepSeqNrEntity = new CasOpsRepSeqNrEntity();
		casOpsRepSeqNrEntity = (CasOpsRepSeqNrEntity)adminBeanRemote.retrieveRepSeqNr(reportNr,memberId);

		if(casOpsRepSeqNrEntity != null)
		{
			lastSeqNoUsed = Integer.valueOf(casOpsRepSeqNrEntity.getLastSeqNo());
			lastSeqNoUsed = lastSeqNoUsed + 1;
		}
		else
			lastSeqNoUsed = 1;

		strSeqNo = String.format("%06d",lastSeqNoUsed);
		casOpsRepSeqNrEntity.setLastSeqNo(strSeqNo);
		adminBeanRemote.updateReportSeqNr(casOpsRepSeqNrEntity);


		String reportSeqNo = strSeqNo.substring(3,6);

		fileName =bankId+"AC"+reportNr+dateFormat.format(currentDate).toString()+"."+reportSeqNo+".csv";     	
		StreamFactory factory = StreamFactory.newInstance();
		factory.loadResource(XML); 

		BeanWriter writer = factory.createWriter("pbmd05ReportFile", new File(tempDir + getFileName()));

		MandateDailyTransModel file2 = new MandateDailyTransModel();

		//SET COLUMNS HEARDERS
		file2.setCreditorBank("Creditor Bank");
		file2.setDebtorBank("Debtor Bank");
		file2.setServiceId("Service Id");
		file2.setTxnType("Transaction Type");
		file2.setActionDate("Action Date");
		file2.setRespDate("Response Date");
		file2.setExtractMsgId("Extract Message Id");
		file2.setMndtReqTransId("Mandate Suspension Id");
		file2.setMndtRefNumber("Mandate Reference Number");
		file2.setAuthCode("Authorisation Code");
		file2.setTrxnStatus("Transaction Status");

		writer.write(file2); 

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
			log.error("Error on copying PBMD05 report to temp "+ioe.getMessage());
			ioe.printStackTrace();
		}
		catch(Exception ex)
		{
			log.error("Error on copying PBMD05 report to temp "+ex.getMessage());
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
