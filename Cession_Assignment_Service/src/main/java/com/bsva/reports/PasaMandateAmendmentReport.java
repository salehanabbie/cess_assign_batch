package com.bsva.reports;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.apache.log4j.Logger;
import org.beanio.BeanWriter;
import org.beanio.StreamFactory;
import com.bsva.commons.model.SystemParameterModel;
import com.bsva.entities.CasCnfgReportNamesEntity;
import com.bsva.entities.PasaMandateReportEntityModel;
import com.bsva.entities.SysCisBranchEntity;
import com.bsva.interfaces.AdminBeanRemote;
import com.bsva.interfaces.PropertyUtilRemote;
import com.bsva.interfaces.ReportBeanRemote;
import com.bsva.interfaces.ServiceBeanRemote;
import com.bsva.interfaces.ValidationBeanRemote;
import com.bsva.utils.DateUtil;
import com.bsva.utils.Util;
import com.google.common.io.Files;
import com.itextpdf.text.DocumentException;

/**
 *@author SalehaR
 *@author SalehaR - 29 October 2018 - Change the PHIR status for expired transactions
 *@author SalehaR - 10 December 2018 - Align PHI reports to V1.4 of PASA Spec (CHG-148253)
 *@author SalehaR - 14 December 2018 - Align RealTime PHI reports to V1.4 of PASA Spec (CHG-144056)
 *@author SalehaR - 06/02/2019 - Align PHI Batch Reports to V1.5 of PASA Spec (CHG-148253)
 *@author SalehaR-2019/10/10 - Align to Single Table
 *@author SalehaR - 04/11/2019-Optimised Real Time PHIR SQL
 */
public class PasaMandateAmendmentReport 
{
	public static Logger log=Logger.getLogger(PasaMandateAmendmentReport.class);
	private String fileName;

	public static ServiceBeanRemote beanRemote;
	public static ValidationBeanRemote valBeanRemote;
	private static AdminBeanRemote adminBeanRemote;
	private static PropertyUtilRemote propertyUtilRemote;
	private static ReportBeanRemote reportBeanRemote;

	List<PasaMandateReportEntityModel> reportDataList;
	List<PasaMandateReportEntityModel> pasaMandateReportModelList;

	private final static String XML = "PHIR-MA.xml";

	HashMap<String,String> creditorBranches = null;
	HashMap<String,String> debtorBranches = null;

	List<SysCisBranchEntity> debtorBranchList;
	SysCisBranchEntity debtorBranchEntity;

	SystemParameterModel systemParameterModel;

	String reportName,reportNr, reportDir = null, tempDir = null;
	int fileSeqNo =000;
	String xlsFileName = null, psmc01 = null, invBank = null;
	String authStatus = null, declStatus = null, noRespStatus = null, valFailStatus = null, /*outRespStatus = null, */respNotRec = null,notifyStatus = null;

	SimpleDateFormat monthFormat = new SimpleDateFormat("MMM-yyyy");
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	DateFormat rtDateFormat = new SimpleDateFormat("yyyyMMdd");
	String manamService = "MANAM";
	String pain010NmId = "pain.010";
	String phirRepNr = null, strRepPeriod, mdtLoadType;
	int reportPeriod = 0;
	boolean isRunFromSOD = false;
	Date startDate,endDate;

	long startTime, endTime, duration;
	
	String activeIndicator = "Y";

	public void generateReport(boolean sodRun,Date frmDate,Date date)throws FileNotFoundException, DocumentException
	{
		contextAdminBeanCheck();
		contextCheck();
		contextValidationBeanCheck();
		contextPropertyFileCheck();
		contextReportBeanCheck();

		startTime = System.nanoTime();

		this.isRunFromSOD = sodRun;
		this.startDate = frmDate ;
		this.endDate = date;

		try
		{
			tempDir = propertyUtilRemote.getPropValue("ExtractTemp.Out");
			log.debug("tempDir ==> "+tempDir);
			reportDir= propertyUtilRemote.getPropValue("Reports.Output");
			log.debug("reportDir ==> "+reportDir);
			invBank = propertyUtilRemote.getPropValue("ERROR_CODES_REPORT_INV_BANK");
			authStatus =  propertyUtilRemote.getPropValue("STATUS.AUTH");
			declStatus =  propertyUtilRemote.getPropValue("STATUS.DECLINED");
			noRespStatus =  propertyUtilRemote.getPropValue("STATUS.NO_RESP");
			valFailStatus =  propertyUtilRemote.getPropValue("STATUS.VALFAIL");
			notifyStatus = propertyUtilRemote.getPropValue("STATUS.NOTIFY");
			//			outRespStatus =  propertyUtilRemote.getPropValue("STATUS.OUTRESP");
			respNotRec = propertyUtilRemote.getPropValue("STATUS.RESPNOTREC");
			phirRepNr = propertyUtilRemote.getPropValue("RPT.PASA.PHIR02");
			strRepPeriod = propertyUtilRemote.getPropValue("PHIR.REPORTPERIOD");
			mdtLoadType = propertyUtilRemote.getPropValue("MDT.LOAD.TYPE");
		}
		catch(Exception ex)
		{
			log.error("PHIR Mandate Amendment - Could not find MandateMessageCommons.properties in classpath");	
			reportDir = "/home/opsjava/Delivery/Cession_Assign/Output/Reports/";
			tempDir="/home/opsjava/Delivery/Cession_Assign/Output/temp/";
			invBank = "INVBNK"; 
			authStatus = "AUTHENTICATED";
			declStatus = "DECLINED";
			noRespStatus = "NO_RESPONSE";
			valFailStatus = "VALIDATION_FAILURE";
			//			outRespStatus = "OUTSTANDING_RESPONSE";
			respNotRec = "RESPONSE_FILE_NOT_RECEIVED";
			phirRepNr = "PHIR02";
			strRepPeriod = "6";
			notifyStatus = "NOTIFICATION";
		}

		//Retrieve Report Name
		CasCnfgReportNamesEntity reportNameEntity = new CasCnfgReportNamesEntity();
	    reportNameEntity = (CasCnfgReportNamesEntity) adminBeanRemote.retrieveReportName(phirRepNr);
	    
	    if(reportNameEntity != null) {
	    	
	    	if(reportNameEntity.getActiveInd().equalsIgnoreCase(activeIndicator)) {
	    		
	    		systemParameterModel = new SystemParameterModel();
	    		systemParameterModel =(SystemParameterModel) adminBeanRemote.retrieveWebActiveSysParameter();

	    		retieveMandateAmendmentData();

	    		if(reportDataList != null && reportDataList.size() > 0)
	    		{
	    			log.info("****GENERATING PASA HEALTH INDICATOR REPORT FOR MANDATE AMENDMENTS*****");
	    			Collections.sort(reportDataList, new PasaDataStatusSorter());
	    			generateCSVReportDetail();
	    			endTime = System.nanoTime();
	    			duration = (endTime - startTime) / 1000000;
	    			log.info("[Total Duration for PHIR02 Report is: "+ DurationFormatUtils.formatDuration(duration, "HH:mm:ss.S")+" milliseconds |");
	    		}
	    		else
	    		{
	    			log.info("****NO DATA FOR PASA HEALTH INDICATOR REPORT FOR MANDATE AMENDMENTS*****");
	    		}
	    	}
	    	
	    }

	}

	public void generateCSVReportDetail()
	{
		DecimalFormat df = new DecimalFormat("### ### ### ### ### ##0.00");
		DateFormat dateTimeFormat = new SimpleDateFormat("yyyyMMdd_HH:mm:ss");
		DateFormat fileTimeFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
		DateFormat reportDate = new SimpleDateFormat("ddMMyyyy");

		Date currentDate = new Date();
		fileSeqNo =fileSeqNo + 1;
		//		String reportNo = "PHIR-MA";
		//		fileName =reportNo+"AC"+fileTimeFormat.format(currentDate).toString()+".csv";
		fileName = "0000"+phirRepNr+"ACMD"+reportDate.format(currentDate).toString()+".csv";	
		log.debug("fileName ==> "+ fileName);

		StreamFactory factory = StreamFactory.newInstance();
		factory.loadResource(XML); 

		BeanWriter writer = factory.createWriter("phir-ma-report", new File(tempDir + getFileName()));
		PasaMandateReportEntityModel title = new PasaMandateReportEntityModel();

		//SET COLUMNS HEADERS       
		title.setMrti("MRTI");
		title.setCreditorBank("CREDITOR_BANK");
		title.setDebtorBank("DEBTOR_BANK");
		title.setCreationDate("SYSTEM_DATE");
		title.setReason("AMENDMENT_REASON");
		title.setAuthType("AUTHENTICATION_TYPE");
		title.setContRefNum("CONTRACT_REFERENCE_NUMBER");
		title.setDbtrAuthReq("DEBTOR_AUTHENTICATION_REQUIRED");
		title.setInstOcc("INSTALLMENT_OCCURENCE");
		title.setCdtrAbbShtNm("CREDITOR_ABBREVIATED_SHORT_NAME");
		title.setStatus("STATUS");
		title.setErrorCode("ERROR_CODE");
		title.setDataSource("DATA_SOURCE");

		writer.write(title); 

		//SET DATA TO COLUMNS
			for(PasaMandateReportEntityModel reportModel :reportDataList)
			{
				log.debug("reportModel----->"+ reportModel);

				PasaMandateReportEntityModel dataModel = new PasaMandateReportEntityModel();
				dataModel.setMrti(reportModel.getMrti());
				dataModel.setCreditorBank(reportModel.getCreditorBank());
				dataModel.setDebtorBank(reportModel.getDebtorBank());
				dataModel.setCreationDate(reportModel.getCreationDate());
				dataModel.setReason(reportModel.getReason());
				dataModel.setAuthType(reportModel.getAuthType());
				dataModel.setContRefNum(reportModel.getContRefNum());
				dataModel.setDbtrAuthReq(reportModel.getDbtrAuthReq());
				dataModel.setInstOcc(reportModel.getInstOcc());
				dataModel.setCdtrAbbShtNm(reportModel.getCdtrAbbShtNm());
				dataModel.setStatus(reportModel.getStatus());
				dataModel.setErrorCode(reportModel.getErrorCode());
				dataModel.setDataSource(reportModel.getDataSource());

				writer.write(dataModel); 
			}
		writer.close(); 

		//Copy the report to the Output reports folder 
		try
		{
			copyFile(fileName);
		}
		catch(IOException ioe)
		{
			log.error("Error on copying PHIR02 report to temp "+ioe.getMessage());
			ioe.printStackTrace();
		}
		catch(Exception ex)
		{
			log.error("Error on copying PHIR02 report to temp "+ex.getMessage());
			ex.printStackTrace();
		}
	}


	public  void copyFile(String fileName) throws IOException 
	{
		File tmpFile = new File(tempDir, fileName);
		File destFile = new File(reportDir, fileName);

		log.debug("tmpFile ==> "+ tmpFile);
		log.debug("destFile===> "+ destFile);

		Files.copy(tmpFile, destFile);
		log.info("Copying "+fileName+"from temp to output directory...");
	}

	public void retieveMandateAmendmentData()
	{
		reportDataList = new ArrayList<PasaMandateReportEntityModel>();
		Date pastDate = null;

		//		======================================================================================================================================//				
		//		//#### UNCOMMENT LINES 230 TO 251 FOR MANUAL GENERATION####//
		//		//#### IF YOU ARE UNCOMMENTING THIS THEN COMMENT OUT THE SYSTEM GENERATED CODE BELOW [LINE 251 - 266] ####
		//		Calendar cal = Calendar.getInstance();
		//		cal.clear(); // <- you need this call
		//		//*****This date must always be the last date*****//
		//		cal.set(2018, Calendar.APRIL, 6); //Year, month and day of month
		//		Date date = cal.getTime();
		//		
		//		try 
		//		{
		//			reportPeriod = Integer.valueOf(strRepPeriod);
		//			//Calculate date for 6 days
		//			
		//			pastDate = DateUtil.calculatePastDate(date, reportPeriod);
		//			log.debug("past Date in report : "+pastDate);
		//		} 
		//		catch (ParseException e) 
		//		{
		//			log.error("Error on calculating past date ==> "+e.getMessage());
		//			e.printStackTrace();
		//		}
		//
		//		String fromDate = dateFormat.format(pastDate);
		//		String toDate = dateFormat.format(date);
		//======================================================================================================================================//		


		//======================================================================================================================================//
		//		//#####LINES 257 TO 272 ARE FOR SYSTEM GENERATED#####

		String fromDate = null, toDate = null;
		String realTimeFromDate = null, realTimeToDate = null;
		if(startDate != null && endDate!=null)
		{
			fromDate = dateFormat.format(startDate);
			toDate = dateFormat.format(endDate);
			realTimeFromDate = rtDateFormat.format(startDate);
			realTimeToDate = rtDateFormat.format(endDate);
		}
		else if(startDate == null && endDate ==null) {
			try 
			{
				reportPeriod = Integer.valueOf(strRepPeriod);

				if(isRunFromSOD)
				{
					Date yesterdayDate = DateUtils.addDays(systemParameterModel.getProcessDate(),-1);
					// Calculate date for 6 days
					pastDate = DateUtil.calculatePastDate(yesterdayDate, reportPeriod);
					log.debug("past Date in report : " + pastDate);
					fromDate = dateFormat.format(pastDate);
					toDate = dateFormat.format(yesterdayDate);
					realTimeFromDate = rtDateFormat.format(pastDate);
					realTimeToDate = rtDateFormat.format(yesterdayDate);
				}
				else
				{
					// Calculate date for 6 days
					pastDate = DateUtil.calculatePastDate(systemParameterModel.getProcessDate(), reportPeriod);
					log.debug("past Date in report : " + pastDate);
					fromDate = dateFormat.format(pastDate);
					toDate = dateFormat.format(systemParameterModel.getProcessDate());
					realTimeFromDate = rtDateFormat.format(pastDate);
					realTimeToDate = rtDateFormat.format(systemParameterModel.getProcessDate());
				}
			} 
			catch (ParseException e) 
			{
				log.error("Error on calculating past date ==> " + e.getMessage());
				e.printStackTrace();
			}
		}


		//		======================================================================================================================================//
		//DO NOT CHANGE FROM HERE ONWARDS
		log.info("PHIR02 - fromDate ==>" + fromDate);
		log.info("PHIR02 - toDate ==>" + toDate);

		pasaMandateReportModelList = new ArrayList<>();

		pasaMandateReportModelList = (List<PasaMandateReportEntityModel>) reportBeanRemote.retrievePHIRAmendmentMandateInfo(fromDate,toDate);
		log.info("pasaMandateReportModelList: " + pasaMandateReportModelList.size());

		if(pasaMandateReportModelList != null && pasaMandateReportModelList.size() > 0){
			for (PasaMandateReportEntityModel pasaMandateReportEntityModel : pasaMandateReportModelList){
				reportDataList.add(pasaMandateReportEntityModel);
			}
		}
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	//Sort list by Status 
	private class PasaDataStatusSorter implements Comparator<PasaMandateReportEntityModel>
	{
		@Override
		public int compare(PasaMandateReportEntityModel o1, PasaMandateReportEntityModel o2) 
		{
			if(o1.getStatus() == null && o2.getStatus() == null)
			{
				return 0;
			}
			else if(o1.getStatus() == null)
			{
				return -1;
			}
			else if(o2.getStatus() == null)
			{
				return 1;
			}
			return o1.getStatus().compareTo(o2.getStatus());	
		}
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
	
	private static void contextReportBeanCheck() {
		if (reportBeanRemote == null) {
			reportBeanRemote = Util.getReportBean();
		}
	}
}

