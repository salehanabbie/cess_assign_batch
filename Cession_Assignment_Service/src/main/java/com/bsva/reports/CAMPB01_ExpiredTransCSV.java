package com.bsva.reports;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import org.apache.commons.lang3.time.DurationFormatUtils;
import org.apache.log4j.Logger;
import org.beanio.BeanWriter;
import org.beanio.StreamFactory;

import com.bsva.commons.model.CreditorBankModel;
import com.bsva.commons.model.MandateResponseOutstandingPerBankModel;
import com.bsva.commons.model.MndtRespOutstandingPerBankCsvModel;
import com.bsva.commons.model.SystemParameterModel;
import com.bsva.entities.MandateResponseOutstandingPerBankEntityModel;
import com.bsva.entities.CasCnfgReportNamesEntity;
import com.bsva.entities.CasOpsRepSeqNrEntity;
import com.bsva.interfaces.AdminBeanRemote;
import com.bsva.interfaces.PropertyUtilRemote;
import com.bsva.interfaces.ReportBeanRemote;
import com.bsva.interfaces.ServiceBeanRemote;
import com.bsva.utils.Util;
import com.google.common.io.Files;

/**
 * @author SalehaR-2019/11/13
 *
 */
public class CAMPB01_ExpiredTransCSV {
	String reportName, reportNr;
	private String downloaddirectory ="/home/opsjava/Delivery/Cession_Assign/Reports/";
	public static Logger log=Logger.getLogger("PBMD08_5DayOutstRespCSV");

	public static ServiceBeanRemote beanRemote;
	private static AdminBeanRemote adminBeanRemote;
	private static PropertyUtilRemote propertyUtilRemote;
	private static ReportBeanRemote reportBeanRemote;

	TreeMap<String, List<MandateResponseOutstandingPerBankEntityModel>> campb01Map;

	List<CreditorBankModel> creditorBankModelList;
	SystemParameterModel systemParameterModel = new SystemParameterModel();

	int lastSeqNoUsed;
	private String fileName, reportDir = null, tempDir = null;
	private final static String XML = "CAMPB01CSV.xml";
	String campb01, mdtLoadType = null;
	long startTime, endTime, duration;
	
	CasCnfgReportNamesEntity reportNameEntity = new CasCnfgReportNamesEntity();
	String activeIndicator = "Y";

	public CAMPB01_ExpiredTransCSV(TreeMap<String, List<MandateResponseOutstandingPerBankEntityModel>> outRespDataMap)
	{
		contextAdminBeanCheck();
		contextCheck();
		contextPropertyFileCheck();
		contextReportCheck();

		try
		{
			this.campb01Map = outRespDataMap;
			tempDir = propertyUtilRemote.getPropValue("ExtractTemp.Out");
			reportDir= propertyUtilRemote.getPropValue("Reports.Output");
			campb01 = propertyUtilRemote.getPropValue("RPT.BANK.BATCH.EXPIRED");
			mdtLoadType = propertyUtilRemote.getPropValue("MDT.LOAD.TYPE");
		}
		catch(Exception ex)
		{
			log.error("CAMPB01CSV - Could not find CessionAssignment.properties in classpath");
			reportDir = "/home/opsjava/Delivery/Cession_Assign/Output/Reports/";
			tempDir="/home/opsjava/Delivery/Cession_Assign/Output/temp/";
		}

		//Retrieve Report Name
		reportNameEntity = (CasCnfgReportNamesEntity) adminBeanRemote.retrieveReportName(campb01);
		if(reportNameEntity != null)
		{
			reportNr = reportNameEntity.getReportNr();
			reportName = reportNameEntity.getReportName();
		}
		systemParameterModel = (SystemParameterModel) adminBeanRemote.retrieveWebActiveSysParameter();
	}
	
	public void generateMandateResponseOutstandingPerBankCsv(boolean frontEnd,Date frontEndDate)
	{
		if(reportNameEntity != null) {
			
			if(reportNameEntity.getActiveInd().equalsIgnoreCase(activeIndicator)) {
				
				creditorBankModelList = new ArrayList<CreditorBankModel>();
				creditorBankModelList = (List<CreditorBankModel>) adminBeanRemote.retrieveActiveCreditorBank();
				if(creditorBankModelList.size() > 0 && campb01Map != null && campb01Map.size() > 0)
				{
					for (CreditorBankModel creditorBankModel : creditorBankModelList) 
					{	
						startTime = System.nanoTime();
						List<MandateResponseOutstandingPerBankEntityModel> batchOutsRespList = campb01Map.get(creditorBankModel.getMemberNo());

						log.info("GENERATING REPORT CAMPB01 FOR "+creditorBankModel.getMemberNo());

						if(batchOutsRespList!= null && batchOutsRespList.size() > 0)
						{
							generateReportDetail(creditorBankModel.getMemberNo(),creditorBankModel.getMemberName(), batchOutsRespList,frontEnd,frontEndDate);
						}
						endTime = System.nanoTime();
						duration = (endTime - startTime) / 1000000;
						log.info("[Total Duration for Report is: "+DurationFormatUtils.formatDuration(duration, "HH:mm:ss.S")+" milliseconds |"); 
					}
				}
			}
			
		}
	}

	public void generateReportDetail(String memberId,String memberName,List<MandateResponseOutstandingPerBankEntityModel>mdtRespOutstandingList,boolean frontEnd,Date frontEndDate)
	{
		DecimalFormat df = new DecimalFormat("### ### ### ### ### ##0.00");
		DateFormat dateTimeFormat = new SimpleDateFormat("yyyyMMdd_HH:mm:ss");
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat fileTimeFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
		DateFormat endDateFormat = new SimpleDateFormat("ddMMyyyy");
		Date currentDate = new Date();
		String strSeqNo; 
		String bankId= memberId.substring(2, 6);

		CasOpsRepSeqNrEntity casOpsRepSeqNrEntity = new CasOpsRepSeqNrEntity();
		casOpsRepSeqNrEntity = (CasOpsRepSeqNrEntity)adminBeanRemote.retrieveRepSeqNr(reportNr,memberId);

		if(casOpsRepSeqNrEntity != null)
		{
			lastSeqNoUsed = Integer.valueOf(casOpsRepSeqNrEntity.getLastSeqNo());
			lastSeqNoUsed = lastSeqNoUsed + 1;
		}
		else
		{
			lastSeqNoUsed = 1;
		}
		strSeqNo = String.format("%06d",lastSeqNoUsed);
		casOpsRepSeqNrEntity.setLastSeqNo(strSeqNo);
		adminBeanRemote.updateReportSeqNr(casOpsRepSeqNrEntity);
		String reportSeqNo = strSeqNo.substring(3,6);

		strSeqNo = String.format("%06d",lastSeqNoUsed);
		casOpsRepSeqNrEntity.setLastSeqNo(strSeqNo);
		adminBeanRemote.updateReportSeqNr(casOpsRepSeqNrEntity);

		if(frontEnd) {
			fileName =bankId+"AC"+reportNr+endDateFormat.format(frontEndDate).toString()+"."+reportSeqNo+".csv";
		}else {
			fileName =bankId+"AC"+reportNr+endDateFormat.format(currentDate).toString()+"."+reportSeqNo+".csv";
		}
		     	
		StreamFactory factory = StreamFactory.newInstance();
		factory.loadResource(XML); 

		BeanWriter writer = factory.createWriter("CAMPBO1CSV", new File(tempDir + getFileName()));
		
		MndtRespOutstandingPerBankCsvModel file2 = new MndtRespOutstandingPerBankCsvModel();
		file2.setDbtrMemberName("Debtor Bank");
		file2.setCrdMemberNo("Member Number");
		file2.setFileName("Message Id");
		file2.setTransType("Transaction Type");
		file2.setMandateReqTransId("Mandate Request Transaction Id");
		file2.setServiceId("Service Id");

		writer.write(file2); 	

		for(MandateResponseOutstandingPerBankEntityModel mndtRespOutstandingPerBankModel :mdtRespOutstandingList)
		{
			MandateResponseOutstandingPerBankModel txnModel = new MandateResponseOutstandingPerBankModel();

			txnModel.setDbtrMemberName(mndtRespOutstandingPerBankModel.getDbtrMemberName());
			txnModel.setDbtrMemberNo(mndtRespOutstandingPerBankModel.getDbtrMemberNo());
			txnModel.setFileName(mndtRespOutstandingPerBankModel.getFileName());
			txnModel.setTransType(mndtRespOutstandingPerBankModel.getTransType());
			txnModel.setMandateReqTransId(mndtRespOutstandingPerBankModel.getMandateReqTransId());
			txnModel.setServiceId(mndtRespOutstandingPerBankModel.getServiceId());

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
			log.error("Error on copying CAMPB01 report to temp "+ioe.getMessage());
			ioe.printStackTrace();
		}
		catch(Exception ex)
		{
			log.error("Error on copying CAMPB01 report to temp "+ex.getMessage());
			ex.printStackTrace();
		}
	}

	public  void copyFile(String fileName) throws IOException 
	{
		File tmpFile = new File(tempDir, fileName);
		File destFile = new File(reportDir, fileName);

		Files.copy(tmpFile, destFile);
		log.info("Copying "+fileName+"from temp to output directory...");
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
	private static void contextCheck() {
		if (beanRemote == null){
			beanRemote = Util.getServiceBean();
		}
	}

	private static void contextAdminBeanCheck() {
		if (adminBeanRemote == null) {
			adminBeanRemote = Util.getAdminBean();	
		}
	}

	private static void contextPropertyFileCheck() {
		if (propertyUtilRemote == null) {
			propertyUtilRemote = Util.getPropertyUtil();
		}
	}

	private static void contextReportCheck() {
		if (reportBeanRemote == null) {
			reportBeanRemote = Util.getReportBean();
		}
	}
}
