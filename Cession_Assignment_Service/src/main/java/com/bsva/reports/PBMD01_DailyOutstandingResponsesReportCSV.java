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
import com.bsva.commons.model.DebtorBankModel;
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
import com.bsva.interfaces.ValidationBeanRemote;
import com.bsva.utils.Util;
import com.google.common.io.Files;

/**
 * @author SalehaR
 * @author SalehaR-2019/09/30 Align to Single Table
 * @author SalehaR-2019/11/10 Align Report to new requirements(Remove R/T)
 */
public class PBMD01_DailyOutstandingResponsesReportCSV 
{
	String reportName, reportNr;
	private String downloaddirectory ="/home/opsjava/Delivery/Mandates/Reports/";
	public static Logger log=Logger.getLogger("PBMD01_Outst Resp CSV");
	
	public static ServiceBeanRemote beanRemote;
	public static ValidationBeanRemote valBeanRemote;
	private static AdminBeanRemote adminBeanRemote;
	private static PropertyUtilRemote propertyUtilRemote;
	private static ReportBeanRemote reportBeanRemote;
	
	TreeMap<String, List<MandateResponseOutstandingPerBankEntityModel>> pbmd01Map;
	
	List<DebtorBankModel> debtorBankModelList;
	DebtorBankModel debtorBankModel ;
	CasCnfgReportNamesEntity reportNameEntity = new CasCnfgReportNamesEntity();
	SystemParameterModel systemParameterModel = new SystemParameterModel();
	
	int lastSeqNoUsed;
	private String fileName, reportDir = null, tempDir = null;
	private final static String XML = "PBMD01CSV.xml";
	String pbmd01, mdtLoadType = null;
	long startTime, endTime, duration;
	String activeIndicator = "Y";

	public PBMD01_DailyOutstandingResponsesReportCSV(TreeMap<String, List<MandateResponseOutstandingPerBankEntityModel>> outRespDataMap)
	{
		contextAdminBeanCheck();
		contextCheck();
		contextValidationBeanCheck();
		contextPropertyFileCheck();
		contextReportCheck();

		try
		{
			this.pbmd01Map = outRespDataMap;
//			log.info("CSV Map List: "+pbmd01Map.size());
			tempDir = propertyUtilRemote.getPropValue("ExtractTemp.Out");
			reportDir= propertyUtilRemote.getPropValue("Reports.Output");
			pbmd01 = propertyUtilRemote.getPropValue("RPT.BANK.OUTSRESP");
			mdtLoadType = propertyUtilRemote.getPropValue("MDT.LOAD.TYPE");
		}
		catch(Exception ex)
		{
			log.error("Daily Batch Billable Txn - Could not find MandateMessageCommons.properties in classpath");	
			reportDir = "/home/opsjava/Delivery/Mandates/Output/Reports/";
			tempDir="/home/opsjava/Delivery/Mandates/Output/temp/";
		}

		//Retrieve Report Name
		reportNameEntity = (CasCnfgReportNamesEntity) adminBeanRemote.retrieveReportName(pbmd01);
		if(reportNameEntity != null)
		{
			reportNr = reportNameEntity.getReportNr();
			reportName = reportNameEntity.getReportName();
		}
		systemParameterModel = (SystemParameterModel) adminBeanRemote.retrieveWebActiveSysParameter();
	}
	
	public void generateMandateResponseOutstandingPerBankCsv(Date frontEndDate)
	{
		if(reportNameEntity != null) {
			
			if(reportNameEntity.getActiveInd().equalsIgnoreCase(activeIndicator)) {
				
				debtorBankModelList = new ArrayList<DebtorBankModel>();
				debtorBankModelList = (List<DebtorBankModel>) adminBeanRemote.retrieveActiveDebtorBank();

				if(debtorBankModelList.size() > 0 && pbmd01Map != null && pbmd01Map.size() > 0)
				{
					for (DebtorBankModel debtorBankModel : debtorBankModelList) 
					{
//						List<MandateResponseOutstandingPerBankModel> mndtResponseOutstandingPerBankList = new ArrayList<MandateResponseOutstandingPerBankModel>();
		//
//						if(mdtLoadType.equalsIgnoreCase("M")) {
//							mndtResponseOutstandingPerBankList=(List<MandateResponseOutstandingPerBankModel>) adminBeanRemote.retrieveMndtRespPerBank(debtorBankModel.getMemberNo(),"9");
//						}else {
//							mndtResponseOutstandingPerBankList=(List<MandateResponseOutstandingPerBankModel>)  reportBeanRemote.retrieveMndtRespPerBank(debtorBankModel.getMemberNo(), systemParameterModel.getProcessDate());
//						}
		//
//						log.info("GENERATING REPORT PBMD01 FOR "+debtorBankModel.getMemberNo());
		//
//						if(mndtResponseOutstandingPerBankList!= null && mndtResponseOutstandingPerBankList.size() > 0)
//						{
//							generateReportDetail(debtorBankModel.getMemberNo(),debtorBankModel.getMemberName(), mndtResponseOutstandingPerBankList);
//						}	
						
						startTime = System.nanoTime();
						List<MandateResponseOutstandingPerBankEntityModel> batchOutsRespList = pbmd01Map.get(debtorBankModel.getMemberNo());

						log.info("GENERATING REPORT PBMD01 FOR "+debtorBankModel.getMemberNo());

						if(batchOutsRespList!= null && batchOutsRespList.size() > 0)
						{
							generateReportDetail(debtorBankModel.getMemberNo(),debtorBankModel.getMemberName(), batchOutsRespList,frontEndDate);
						}
						endTime = System.nanoTime();
						duration = (endTime - startTime) / 1000000;
						log.info("[Total Duration for Report is: "+DurationFormatUtils.formatDuration(duration, "HH:mm:ss.S")+" milliseconds |"); 
					}
				}
			}
			
		}	
		
	}

	public void generateReportDetail(String memberId,String memberName,List<MandateResponseOutstandingPerBankEntityModel>mdtRespOutstandingList,Date frontEndDate)
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

		if(frontEndDate != null) {
			fileName =bankId+"AC"+reportNr+endDateFormat.format(frontEndDate).toString()+"."+reportSeqNo+".csv";
		}else {
			fileName =bankId+"AC"+reportNr+endDateFormat.format(currentDate).toString()+"."+reportSeqNo+".csv";
		}
		     	
		StreamFactory factory = StreamFactory.newInstance();
		factory.loadResource(XML); 

		BeanWriter writer = factory.createWriter("PBMD01CSV", new File(tempDir + getFileName()));


		MndtRespOutstandingPerBankCsvModel file2 = new MndtRespOutstandingPerBankCsvModel();

		file2.setCrdMemberName("Creditor Bank");
		file2.setCrdMemberNo("Member Number");
		file2.setFileName("Message Id");
		file2.setTransType("Transaction Type");
		file2.setNrDaysOutstanding("Number of Days Outstanding");
		file2.setMandateReqTransId("Mandate Request Transaction Id");
		file2.setServiceId("Service Id");

		writer.write(file2); 	


		for(MandateResponseOutstandingPerBankEntityModel mndtRespOutstandingPerBankModel :mdtRespOutstandingList)
		{
			MandateResponseOutstandingPerBankModel txnModel = new MandateResponseOutstandingPerBankModel();

			txnModel.setCrdMemberName(mndtRespOutstandingPerBankModel.getCrdMemberName());
			txnModel.setCrdMemberNo(mndtRespOutstandingPerBankModel.getCrdMemberNo());
			txnModel.setFileName(mndtRespOutstandingPerBankModel.getFileName());
			txnModel.setTransType(mndtRespOutstandingPerBankModel.getTransType());
			txnModel.setNrDaysOutstanding(mndtRespOutstandingPerBankModel.getNrDaysOutstanding());
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
			log.error("Error on copying PBMD01 report to temp "+ioe.getMessage());
			ioe.printStackTrace();
		}
		catch(Exception ex)
		{
			log.error("Error on copying PBMD01 report to temp "+ex.getMessage());
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
		if (valBeanRemote == null) 
		{
			valBeanRemote = Util.getValidationBean();
		}
	}

	private static void contextCheck() 
	{
		if (beanRemote == null)
		{
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
