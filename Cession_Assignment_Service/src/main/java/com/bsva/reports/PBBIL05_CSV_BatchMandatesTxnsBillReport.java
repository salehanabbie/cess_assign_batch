package com.bsva.reports;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.apache.log4j.Logger;
import org.beanio.BeanWriter;
import org.beanio.StreamFactory;
import com.bsva.commons.model.CreditorBankModel;
import com.bsva.commons.model.SystemParameterModel;
import com.bsva.entities.BatchTxnBillReportEntity;
import com.bsva.entities.CasCnfgReportNamesEntity;
import com.bsva.entities.CasOpsRepSeqNrEntity;
import com.bsva.interfaces.AdminBeanRemote;
import com.bsva.interfaces.PropertyUtilRemote;
import com.bsva.interfaces.ReportBeanRemote;
import com.bsva.interfaces.ServiceBeanRemote;
import com.bsva.utils.DateUtil;
import com.bsva.utils.Util;
import com.google.common.io.Files;

/**
 * @author SalehaR
 * Date: 2022/06/26 - NCH-243753
 */
public class PBBIL05_CSV_BatchMandatesTxnsBillReport {

	public static Logger log=Logger.getLogger("PBBIL05-CSV-BatchTxnBillReport");
	long startTime = System.nanoTime();
	private String fileName;

	public static ServiceBeanRemote beanRemote;
	public static ReportBeanRemote reportBeanRemote;
	private static AdminBeanRemote adminBeanRemote;
	private static PropertyUtilRemote propertyUtilRemote;

	private final static String XML = "PBBIL05CSV.xml";

	SystemParameterModel systemParameterModel;

	String reportName, reportNr, reportDir = null, tempDir = null;
	int fileSeqNo =000;
	String xlsFileName = null, pbbil05 = null;
	Date reportDate = null;

	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	DateFormat fileTimeFormat = new SimpleDateFormat("MM-yyyy");

	int lastSeqNoUsed;
	String strMonth = null;
	String month = null;
	String year = null;
	String strMonthFront = null;
	String monthFront = null;
	String yearFront = null;
	Date firstDate, lastDate = null;
	String strFromDate = null, strToDate = null;
	boolean reportRunFromFrontEnd = false;

	List<BatchTxnBillReportEntity> batchMndtTxnBillList;
	List<CreditorBankModel> creditorBankModelList;

	public TreeMap<String, List<BatchTxnBillReportEntity>> pbbil05Map;

	int maxNrOfTxns = 1000000;

	public void generateReport(Date fromDate, Date toDate, boolean frontEndRun)
	{
		contextAdminBeanCheck();
		contextCheck();
		contextReportBeanCheck();
		contextPropertyFileCheck();

		systemParameterModel = new SystemParameterModel();
		systemParameterModel =(SystemParameterModel) adminBeanRemote.retrieveWebActiveSysParameter();

		this.reportRunFromFrontEnd = frontEndRun;

		try
		{
			tempDir = propertyUtilRemote.getPropValue("ExtractTemp.Out");
			reportDir= propertyUtilRemote.getPropValue("Reports.Output");

			pbbil05 = propertyUtilRemote.getPropValue("RPT.BANK.BATCH.TXNBILL");
			log.info("pbbil05 from properties: "+pbbil05);
		}
		catch(Exception ex)
		{
			log.error("pbbil05- Could not find MandateMessageCommons.properties in classpath");	
			reportDir = "/home/opsjava/Delivery/Mandates/Output/Reports/";
			tempDir="/home/opsjava/Delivery/Mandates/Output/temp/";
		}

		//Retrieve Report Name
		CasCnfgReportNamesEntity reportNameEntity = new CasCnfgReportNamesEntity();
		reportNameEntity = (CasCnfgReportNamesEntity) adminBeanRemote.retrieveReportName(pbbil05);

		if (reportNameEntity != null) {
			if (reportNameEntity.getActiveInd().equalsIgnoreCase("Y")) {
				reportNr = reportNameEntity.getReportNr();
				reportName = reportNameEntity.getReportName();

				if(reportRunFromFrontEnd)
				{
					strFromDate = dateFormat.format(fromDate);
					strToDate = dateFormat.format(toDate);
				}
				else
				{
					if(systemParameterModel.getProcessDate() != null)
					{
						strMonth =fileTimeFormat.format(systemParameterModel.getProcessDate()).substring(0,2);
						log.debug("strMonth ==> "+strMonth);
						month= DateUtil.getMonthFullname(Integer.valueOf(strMonth), true);
						log.debug("Process Month----->"+month);
						year = String.valueOf(DateUtil.getYear(systemParameterModel.getProcessDate()));
						log.debug("year----->"+year);

						//Calculate the last date of the month
						Calendar nextNotifTime = Calendar.getInstance();
						nextNotifTime.add(Calendar.MONTH, 1);
						nextNotifTime.set(Calendar.DATE, 1);
						nextNotifTime.add(Calendar.DATE, -1);
						lastDate = nextNotifTime.getTime();

						//strFirstDate = "01"+strMonth+year;
						strFromDate=year+"-"+strMonth+"-01";
						strToDate = dateFormat.format(lastDate);

						//						strFromDate = "2018-06-01";
						//						strToDate = "2018-06-30";
						log.info("strFromDate ==>" + strFromDate);
						log.info("strToDate ==> "+ strToDate);
					}
				}

				creditorBankModelList = new ArrayList<CreditorBankModel>();
				creditorBankModelList = (List<CreditorBankModel>) adminBeanRemote.retrieveActiveCreditorBank();

				if(creditorBankModelList != null && creditorBankModelList.size() > 0)
				{
					//Retrieve data
					loadData();

					for (CreditorBankModel creditorBankModel : creditorBankModelList) 
					{
						startTime = System.nanoTime();
						List<BatchTxnBillReportEntity> crBatchTxnBillList = pbbil05Map.get(creditorBankModel.getMemberNo());

						log.info("GENERATING REPORT PBBIL05 FOR "+creditorBankModel.getMemberNo());

						if(crBatchTxnBillList!= null && crBatchTxnBillList.size() > 0)
						{

							log.info("crBatchTxnBillList Size: "+crBatchTxnBillList.size());
							//								generateCSVReportDetail(reportNr, reportName, creditorBankModel.getMemberNo(), crBatchTxnBillList);
							if(crBatchTxnBillList != null && crBatchTxnBillList.size() <= maxNrOfTxns) 
							{
								populateReportDetail(creditorBankModel.getMemberNo(), crBatchTxnBillList);
							}
							else
							{
								List<List<BatchTxnBillReportEntity>> splitTxnList = splitTxnList(crBatchTxnBillList);
								log.info("splitTxnList.size() : "+splitTxnList.size());

								for(int i=0;i <splitTxnList.size(); i++)
								{
									List<BatchTxnBillReportEntity> tt2TxnList = splitTxnList.get(i);
									populateReportDetail(creditorBankModel.getMemberNo(), tt2TxnList);
								}
							}
						}
						long endTime = System.nanoTime();
						long duration = (endTime - startTime) / 500000;
						log.info("[PBBIL05 Report Duration for : "+creditorBankModel.getMemberNo() +DurationFormatUtils.formatDuration(duration, "HH:mm:ss.S")+" milliseconds]");
					}
				}
			}
		}
	}

	public void populateReportDetail(String crBankNo, List<BatchTxnBillReportEntity> tt2TxnList)
	{
		BeanWriter writer = null;
		String strSeqNo; 

		String bankId= crBankNo.substring(2, 6);
		log.debug("bankId---->"+bankId);

		SimpleDateFormat rptNameFormat = new SimpleDateFormat("ddMMyyyy");
		String currentDate = rptNameFormat.format(systemParameterModel.getProcessDate());

		CasOpsRepSeqNrEntity casOpsRepSeqNrEntity = new CasOpsRepSeqNrEntity();
		casOpsRepSeqNrEntity = (CasOpsRepSeqNrEntity)adminBeanRemote.retrieveRepSeqNr(reportNr,crBankNo);

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

		fileName = bankId+"AC"+reportNr+currentDate+"."+reportSeqNo+".csv";
		log.info("fileName ==> " + fileName);

		StreamFactory factory = StreamFactory.newInstance();
		factory.loadResource(XML);

		writer = factory.createWriter("PBBIL05CSV", new File(tempDir + fileName));
		BatchTxnBillReportEntity title = new BatchTxnBillReportEntity();

		// SET COLUMNS HEADERS
		title.setProcessDate("PROCESS DATE");
		title.setDelTime("DELIVERY TIME");
		title.setServiceId("SERVICE");
		title.setTxnType("TRANSACTION TYPE");
		title.setFileName("FILE NAME");
		title.setMrti("MRTI");
		
		writer.write(title);


		for (BatchTxnBillReportEntity batchTxnBillEntity : tt2TxnList) 
		{
			//SET DATA IN COLUMNS
			BatchTxnBillReportEntity dataModel = new BatchTxnBillReportEntity();
			// SET COLUMNS HEADERS
			dataModel.setProcessDate(batchTxnBillEntity.getProcessDate());
			dataModel.setDelTime(batchTxnBillEntity.getDelTime());
			dataModel.setServiceId(batchTxnBillEntity.getServiceId());
			dataModel.setTxnType(batchTxnBillEntity.getTxnType());
			dataModel.setFileName(batchTxnBillEntity.getFileName());
			dataModel.setMrti(batchTxnBillEntity.getMrti());
			

			writer.write(dataModel);
		}
		writer.close();

		// Copy the report to the Output reports folder
		try {
			copyFile(fileName);
		} catch (IOException ioe) {
			log.error("Error on copying PBBIL05 report to temp " + ioe.getMessage());
			ioe.printStackTrace();
		} catch (Exception ex) {
			log.error("Error on copying PBBIL05 report to temp " + ex.getMessage());
			ex.printStackTrace();
		}	
	}

	public void loadData()
	{
		long startTime = System.nanoTime();
		pbbil05Map = new TreeMap<String, List<BatchTxnBillReportEntity>>();
		batchMndtTxnBillList = new ArrayList<BatchTxnBillReportEntity>();

		//Retrieve File Data from DB
		batchMndtTxnBillList = (List<BatchTxnBillReportEntity>) reportBeanRemote.retrievePBBIL05BatchTxnsBillData(strFromDate, strToDate);
		log.info("batchMndtTxnBillList size: "+batchMndtTxnBillList.size());
		List<BatchTxnBillReportEntity> dataPerCreditorBank;

		if(batchMndtTxnBillList != null && batchMndtTxnBillList.size() > 0) {
			for (CreditorBankModel creditorBankModel : creditorBankModelList) {
				dataPerCreditorBank = new ArrayList<BatchTxnBillReportEntity>();

				for (BatchTxnBillReportEntity batchTxnRptEntity : batchMndtTxnBillList) {
					if(batchTxnRptEntity.getOriginator().equalsIgnoreCase(creditorBankModel.getMemberNo())) {
						dataPerCreditorBank.add(batchTxnRptEntity);
					}
				}
				pbbil05Map.put(creditorBankModel.getMemberNo(), dataPerCreditorBank);
			}   
		}		
		long endTime = System.nanoTime();
		long duration = (endTime - startTime) / 500000;
		log.info("[PBBIL05 Data Retrieval time: "+DurationFormatUtils.formatDuration(duration, "HH:mm:ss.S")+" milliseconds]");
	}

	public List<List<BatchTxnBillReportEntity>> splitTxnList(List<BatchTxnBillReportEntity> crTxnBillEntityList) {

		int targetSize = maxNrOfTxns;

		List<List<BatchTxnBillReportEntity>> partitionList = null;
		//Partition List
		partitionList = ListUtils.partition(crTxnBillEntityList, targetSize);
		log.info("Partition List Size: "+partitionList.size());

		return partitionList;
	}

	public  void copyFile(String fileName) throws IOException 
	{
		File tmpFile = new File(tempDir, fileName);
		File destFile = new File(reportDir, fileName);

		Files.copy(tmpFile, destFile);
		log.info("Copying "+fileName+" from temp to output directory...");
	}

	//CONTEXT LOOKUPS//
	private static void contextCheck() {
		if (beanRemote == null) {
			beanRemote = Util.getServiceBean();
		}
	}

	private static void contextAdminBeanCheck() 
	{
		if (adminBeanRemote == null) {
			adminBeanRemote = Util.getAdminBean();	
		}
	}

	private static void contextPropertyFileCheck() 
	{
		if (propertyUtilRemote == null) {
			propertyUtilRemote = Util.getPropertyUtil();
		}
	}

	private static void contextReportBeanCheck() {
		if (reportBeanRemote == null) {
			reportBeanRemote = Util.getReportBean();
		}
	}
}
