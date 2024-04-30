package com.bsva.panels.AcPasaReports;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.beanio.StreamFactory;
import org.beanio.BeanWriter;
import org.beanio.StreamFactory;
import com.bsva.commons.model.CreditorBankModel;
import com.bsva.commons.model.CustomerParametersModel;
import com.bsva.commons.model.FileStatusReportModel;
import com.bsva.commons.model.MandateDailyTransModel;
import com.bsva.commons.model.MandateResponseOutstandingPerBankModel;
import com.bsva.controller.Controller;
import com.itextpdf.text.DocumentException;

/**
 * 
 * @author DimakatsoN
 * @author SalehaR - 20170817 - Read data from MDT_AC_OPS_DAILY_BILLING table
 *
 */
public class BillingTransactionReport 
{
	private String downloaddirectory;
	public static Logger log=Logger.getLogger(BillingTransactionReport.class);
	Controller controller = new Controller();
	private final static String XML = "CSVBeanIO.xml";
	private String fileName;
	List<MandateDailyTransModel>mdtDailyTransList = new ArrayList<MandateDailyTransModel>();
	List<CreditorBankModel> creditorBankModelList;
	CreditorBankModel creditorBankModel;
	String reportName, reportNr;
	int fileSeqNo =000;
	
	public void generateReport(String reportNr, String reportname) throws FileNotFoundException, DocumentException
	{
		retrieveMndtBillingTransactions();
		generateReportDetail(reportNr, reportname);
	}
	
	public void generateReportDetail(String reportNo,String reportname) throws FileNotFoundException, DocumentException
	{
		downloaddirectory = controller.getProperty("REP.DOWNLOADDIR");
		DecimalFormat df = new DecimalFormat("### ### ### ### ### ##0.00");
        DateFormat dateTimeFormat = new SimpleDateFormat("yyyyMMdd_HH:mm:ss");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat fileTimeFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        DateFormat endDateFormat = new SimpleDateFormat("ddMMyyyy");
        Date currentDate = new Date();
        fileSeqNo =fileSeqNo + 1;
        fileName =reportNo+"-"+reportname+"_"+endDateFormat.format(currentDate).toString()+".csv";	
        StreamFactory factory = StreamFactory.newInstance();
		factory.loadResource(XML); 
		BeanWriter writer = factory.createWriter("csvReportFile", new File(downloaddirectory + getFileName()));
        List<MandateDailyTransModel> files = new ArrayList<MandateDailyTransModel>();
        MandateDailyTransModel file = new MandateDailyTransModel();      
        file.setCreditorBank("CREDITOR BANK");
        file.setDebtorBank("DEBTOR BANK");
        file.setServiceId("SERVICE ID");
        file.setTxnType("TRXN TYPE");
        file.setActionDate("ACTION DATE");
        file.setExtractMsgId("EXTRACT MSG ID");
        file.setMndtReqTransId("MANDATE REQUEST TRANSACTION ID");
        file.setMndtRefNumber("MANDATE REFERENCE NUMBER");
        file.setAuthCode("AUTH CODE");
        file.setTrxnStatus("TRXN STATUS");
    	writer.write(file); 
        if(mdtDailyTransList!= null &&mdtDailyTransList.size() > 0)
        {
        	log.info("mdtDailyTransList----->"+ mdtDailyTransList.size());
        	for(MandateDailyTransModel dailyTransModel :mdtDailyTransList)
        	{
        		log.info("dailyTransModel----->"+ dailyTransModel);
        		MandateDailyTransModel txnModel = new MandateDailyTransModel();
        		txnModel.setCreditorBank(dailyTransModel.getCreditorBank());
        		txnModel.setDebtorBank(dailyTransModel.getDebtorBank());
            	txnModel.setServiceId(dailyTransModel.getServiceId());
            	txnModel.setTxnType(dailyTransModel.getTxnType());
            	txnModel.setActionDate(dailyTransModel.getActionDate());
            	txnModel.setExtractMsgId(dailyTransModel.getExtractMsgId());
            	txnModel.setMndtReqTransId(dailyTransModel.getMndtReqTransId());
            	txnModel.setMndtRefNumber(dailyTransModel.getMndtRefNumber());
            	txnModel.setAuthCode(dailyTransModel.getAuthCode());
            	txnModel.setTrxnStatus(dailyTransModel.getTrxnStatus());
            	
        		
//2017-08-18 - Remove padding for alignment as per Lukas - CSV should not have padding        		
//        		if(dailyTransModel.getCreditorBank() != null)
//        			txnModel.setCreditorBank(StringUtils.rightPad(dailyTransModel.getCreditorBank(), 30, " "));
//        		else
//        			txnModel.setCreditorBank(StringUtils.rightPad(" ",30));
//        		
//        		if(dailyTransModel.getDebtorBank() != null)
//        			txnModel.setDebtorBank(StringUtils.rightPad(dailyTransModel.getDebtorBank(), 30, " "));
//            	else
//            		txnModel.setDebtorBank(StringUtils.rightPad(" ",30));
//        		
//        		if(dailyTransModel.getServiceId() != null)
//        			txnModel.setServiceId(StringUtils.rightPad(dailyTransModel.getServiceId(), 10, " "));
//            	else
//            		txnModel.setServiceId(StringUtils.rightPad(" ",10));
//        		
//        		if(dailyTransModel.getTxnType() != null)
//        			txnModel.setTxnType(StringUtils.rightPad(dailyTransModel.getTxnType(), 3, " "));
//            	else
//            		txnModel.setTxnType(StringUtils.rightPad(" ",3));
//        		
//        		if(dailyTransModel.getActionDate() != null)
//        			txnModel.setActionDate(StringUtils.rightPad(String.valueOf(dailyTransModel.getActionDate()), 20, " "));
//            	else
//            		txnModel.setActionDate(StringUtils.rightPad(" ",20));
//        		
//        		if(dailyTransModel.getExtractMsgId() != null)
//        			txnModel.setExtractMsgId(StringUtils.rightPad(dailyTransModel.getExtractMsgId(), 35, " "));
//            	else
//            		txnModel.setExtractMsgId(StringUtils.rightPad(" ",35));
//        		
//        		if(dailyTransModel.getMndtReqTransId() != null)
//        			txnModel.setMndtReqTransId(StringUtils.rightPad(dailyTransModel.getMndtReqTransId(), 35, " "));
//            	else
//            		txnModel.setMndtReqTransId(StringUtils.rightPad(" ",35));
//        		
//        		if(dailyTransModel.getMndtRefNumber() != null)
//        			txnModel.setMndtRefNumber(StringUtils.rightPad(dailyTransModel.getMndtRefNumber(), 35, " "));
//            	else
//            		txnModel.setMndtRefNumber(StringUtils.rightPad(" ",35));
//
//        		if(dailyTransModel.getMndtReqId() != null)
//        			txnModel.setMndtReqId(StringUtils.rightPad(dailyTransModel.getMndtReqId(), 35, " "));
//            	else
//            		txnModel.setMndtReqId(StringUtils.rightPad(" ",35));
//
//        		if(dailyTransModel.getAuthCode() != null)
//        			txnModel.setAuthCode(StringUtils.rightPad(dailyTransModel.getAuthCode(), 35, " "));
//            	else
//            		txnModel.setAuthCode(StringUtils.rightPad(" ",35));
//        		
//        		if(dailyTransModel.getTrxnStatus() != null)
//        			txnModel.setTrxnStatus(StringUtils.rightPad(dailyTransModel.getTrxnStatus(), 35, " "));
//            	else
//            		txnModel.setTrxnStatus(StringUtils.rightPad(" ",35));

        		writer.write(txnModel); 
        	}
        }
    
        writer.close(); 
	}
	
	public List<MandateDailyTransModel>retrieveMndtBillingTransactions()
	{
		String tt2TxnType = controller.getProperty("AC.TT2SubService"); 			
		mdtDailyTransList = (List<MandateDailyTransModel>)controller.retrieveMndtBillingTransactions(tt2TxnType);	
		return mdtDailyTransList;
	}
	
	public String getFileName()
	{
		return fileName;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}
}