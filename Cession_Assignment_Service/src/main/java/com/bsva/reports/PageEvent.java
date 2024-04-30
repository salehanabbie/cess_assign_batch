package com.bsva.reports;

import java.text.ParseException;
import java.util.List;

import org.apache.log4j.Logger;

import com.bsva.commons.model.AmendmentCodesModel;
import com.bsva.commons.model.CreditorBankModel;
import com.bsva.commons.model.DebtorBankModel;
import com.bsva.commons.model.SysCisBankModel;
import com.bsva.commons.model.SysctrlCompParamModel;
import com.bsva.commons.model.SystemParameterModel;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class PageEvent extends PdfPageEventHelper 
{	
	private static Logger log = Logger.getLogger(PageEvent.class);

	private PdfPTable repeatTableHeader;
	public int pageNumber = 0;

	private SysctrlCompParamModel companyParamModel;
	private SystemParameterModel systemParameterModel;
	List<DebtorBankModel> debtorBankList;
	List<CreditorBankModel> creditorBankList;
	List<AmendmentCodesModel> amendmentCodesList;
	List<SysCisBankModel>sysCisBankList;

	private String reportNo, reportName, memberName, memberNo;
	boolean perBankRpt, pasaRpt, processDate;

	public PageEvent(SysctrlCompParamModel companyParamModel, SystemParameterModel systemParameterModel, String reportNo, String reportName, 
	
	boolean perBankRpt, boolean pasaRpt, String memberName, String memberNo, List<DebtorBankModel> debtorBankModelList, List<CreditorBankModel> creditorBankModelList, List<AmendmentCodesModel> amendmentCodesModelList,List<SysCisBankModel> sysCisBankModelList, boolean processDate)  
	{
		this.companyParamModel = companyParamModel;
		this.systemParameterModel = systemParameterModel;
		this.reportName = reportName;
		this.reportNo = reportNo;
		this.perBankRpt = perBankRpt;
		this.pasaRpt = pasaRpt;
		this.memberName = memberName;
		this.memberNo = memberNo;
		this.debtorBankList = debtorBankModelList;
		this.creditorBankList = creditorBankModelList;
		this.amendmentCodesList = amendmentCodesModelList;
		this.sysCisBankList =sysCisBankModelList;
		this.processDate = processDate;
	}

	@Override
	public void onStartPage(PdfWriter pdfWriter, Document document) 
	{
		try 
		{
			pageNumber++;// LesetjaM - Page number fix. PdfWriter getPageNumber() sometimes starts from 2. Added logic to control numbering manually.
			addHeader(document);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			log.error("Exception, ", e);
		} 
	}

	public void  addHeader(Document document) throws DocumentException, ParseException
	{ 
		ReportUtils.generateReportHeader(document, companyParamModel, systemParameterModel, pageNumber, reportNo, reportName, pasaRpt, processDate);

		if(perBankRpt)
		{
			ReportUtils.generatePerBankMemberHeader(document, memberName, memberNo, reportNo);
		}

		//Check if specific report headings are required
		//Daily Reports
		if(reportNo.equalsIgnoreCase("PBMD01"))
		{
			ReportUtils.generatePBMD01Header(document);
		}
		if(reportNo.equalsIgnoreCase("PBMD02"))
		{
			ReportUtils.generatePBMD02Header(document, reportNo, reportName, debtorBankList, memberNo);
		}
		if(reportNo.equalsIgnoreCase("PBMD03"))
		{
			ReportUtils.generatePBMD03Header(document, reportNo, reportName, debtorBankList);
		}

		if(reportNo.equalsIgnoreCase("MR023"))
		{
			ReportUtils.generateMr023Header(document);
		}

		if(reportNo.equalsIgnoreCase("MR020"))
		{
			ReportUtils.generateMR020Header(document, pageNumber);
		}
		if(reportNo.equalsIgnoreCase("MR022"))
		{
			ReportUtils.generateMR020Header(document, pageNumber);
		}

/*		if(reportNo.equalsIgnoreCase("MR024"))
		{
			ReportUtils.generateMR024Header(document, pageNumber);
		}*/
		if(reportNo.equalsIgnoreCase("PBMD07"))
		{
			ReportUtils.generatePBMD07Header(document);
		}
		if(reportNo.equalsIgnoreCase("PBMD08"))
		{
			ReportUtils.generatePBMD08Header(document);
		}
		if(reportNo.equalsIgnoreCase("PBMD09"))
		{
			ReportUtils.generatePBMD09Header(document);
		}

		

		//PASA Monthly Reports
		if(reportNo.equalsIgnoreCase("PSMD01"))
		{
			ReportUtils.generatePSMD01Header(document);
		}
		if(reportNo.equalsIgnoreCase("PSMD02"))
		{
			ReportUtils.generatePSMD02Header(document);
		}
		if(reportNo.equalsIgnoreCase("PSMD03"))
		{
			ReportUtils.generatePSMD03Header(document, creditorBankList);
		}
		if(reportNo.equalsIgnoreCase("PSMD04"))
		{
			ReportUtils.generatePSMD04Header(document, creditorBankList);
		}
		if(reportNo.equalsIgnoreCase("PSMD05"))
		{
			ReportUtils.generatePSMD05Header(document, amendmentCodesList);
		}
		if(reportNo.equalsIgnoreCase("PSMD06"))
		{
			ReportUtils.generatePSMD06Header(document);
		}
		if(reportNo.equalsIgnoreCase("PSMD07"))
		{
			ReportUtils.generatePSMD07Header(document);
		}
	}
}
