package com.bsva.panels.AcPasaReports;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.beanio.BeanWriter;
import org.beanio.StreamFactory;

import com.bsva.commons.model.CustomerParametersModel;
import com.bsva.commons.model.DebtorBankModel;
import com.bsva.commons.model.MandateDailyTransModel;
import com.bsva.commons.model.MandateResponseOutstandingPerBankModel;
import com.bsva.commons.model.MndtRespOutstandingPerBankCsvModel;
import com.bsva.controller.Controller;
import com.itextpdf.text.DocumentException;

public class MandateResponseOutstandingPerBankCSVReport {



	String reportName, reportNr;

	private String downloaddirectory ="/home/opsjava/Delivery/Cession_Assign/Reports/";
	public static Logger log=Logger.getLogger(MandateResponseOutstandingPerBankCSVReport.class);
	Controller controller = new Controller();

	List<CustomerParametersModel> custParamsModelList;
	List<DebtorBankModel> debtorBankModelList;
	DebtorBankModel debtorBankModel ;

	CustomerParametersModel customerParametersModel = new CustomerParametersModel();
	int fileSeqNo =000;
	private String fileName;
	private final static String XML = "PBMD01CSV.xml";

	public MandateResponseOutstandingPerBankCSVReport(String reportNr, String reportName)
	{
		this.reportNr = reportNr;
		this.reportName = reportName;

	}



	public void generateMandateResponseOutstandingPerBankCsv()
	{


		//		custParamsModelList = new ArrayList<CustomerParametersModel>();
		//		custParamsModelList = (List<CustomerParametersModel>) controller.retrieveCustomerParameters();

		debtorBankModelList = new ArrayList<DebtorBankModel>();
		debtorBankModelList = (List<DebtorBankModel>) controller.retrieveActiveDebtorBank();



		if(debtorBankModelList.size() > 0)
		{

			for (DebtorBankModel debtorBankModel : debtorBankModelList) 
			{
				List<MandateResponseOutstandingPerBankModel> mndtResponseOutstandingPerBankList = new ArrayList<MandateResponseOutstandingPerBankModel>();
				if(reportNr.equalsIgnoreCase("PBMD01"))
				{
					mndtResponseOutstandingPerBankList=(List<MandateResponseOutstandingPerBankModel>)controller.retrieveMndtRespPerBank(debtorBankModel.getMemberNo(),"9");
					log.info("GENERATING REPORT PBMD01 FOR "+debtorBankModel.getMemberNo());

					if(mndtResponseOutstandingPerBankList!= null && mndtResponseOutstandingPerBankList.size() > 0)
					{

						generateReportDetail(debtorBankModel.getMemberNo(),debtorBankModel.getMemberName(), mndtResponseOutstandingPerBankList);
					}	
				}
			}

		}
	}

	public void generateReportDetail(String memberId,String memberName,List<MandateResponseOutstandingPerBankModel>mdtRespOutstandingList)
	{
		DecimalFormat df = new DecimalFormat("### ### ### ### ### ##0.00");
		DateFormat dateTimeFormat = new SimpleDateFormat("yyyyMMdd_HH:mm:ss");
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat fileTimeFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
		 DateFormat endDateFormat = new SimpleDateFormat("ddMMyyyy");
		Date currentDate = new Date();
		fileSeqNo =fileSeqNo +1;
		String bankId= memberId.substring(2, 6);
		String strSeqNo = String.format("%03d",fileSeqNo);

		fileName =bankId+"AC"+reportNr+endDateFormat.format(currentDate).toString()+"."+strSeqNo+".csv";     	
		StreamFactory factory = StreamFactory.newInstance();
		factory.loadResource(XML); 

		BeanWriter writer = factory.createWriter("PBMD01CSV", new File(downloaddirectory + getFileName()));


		MndtRespOutstandingPerBankCsvModel file2 = new MndtRespOutstandingPerBankCsvModel();

		file2.setCrdMemberName("CREDITORBANK");
		file2.setCrdMemberNo("MEMBERNO");
		file2.setFileName("FILENAME");
		file2.setTransType("TRANSACTIONTYPE");
		file2.setMandateReqTransId("MANDATEREQUESTTRANSACTIONID");
		file2.setNrDaysOutstanding("NoOFDAYSOUTSTANDING");
		file2.setServiceId("SUBSERVICEID");

		writer.write(file2); 	



		for(MandateResponseOutstandingPerBankModel mndtRespOutstandingPerBankModel :mdtRespOutstandingList)
		{
			MandateResponseOutstandingPerBankModel txnModel = new MandateResponseOutstandingPerBankModel();

			txnModel.setCrdMemberName(mndtRespOutstandingPerBankModel.getCrdMemberName());
			txnModel.setCrdMemberNo(mndtRespOutstandingPerBankModel.getCrdMemberNo());
			txnModel.setFileName(mndtRespOutstandingPerBankModel.getFileName());
			txnModel.setTransType(mndtRespOutstandingPerBankModel.getTransType());
			txnModel.setMandateReqTransId(mndtRespOutstandingPerBankModel.getMandateReqTransId());
			txnModel.setNrDaysOutstanding(mndtRespOutstandingPerBankModel.getNrDaysOutstanding());
			txnModel.setServiceId(mndtRespOutstandingPerBankModel.getServiceId());
			
			writer.write(txnModel);

		}

		 writer.close();
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
