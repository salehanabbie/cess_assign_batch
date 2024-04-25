package com.bsva.reports;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.apache.log4j.Logger;

import com.bsva.commons.model.AmendmentCodesModel;
import com.bsva.commons.model.CreditorBankModel;
import com.bsva.commons.model.DebtorBankModel;
import com.bsva.commons.model.SysCisBankModel;
import com.bsva.commons.model.SysctrlCompParamModel;
import com.bsva.commons.model.SystemParameterModel;
import com.bsva.utils.DateUtil;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

/**
 * @author SalehaR
 *
 */
public class ReportUtils 
{
	private static Logger  log = Logger.getLogger(ReportUtils.class);
	private static DateFormat  dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
	private static DateFormat secondDateFormat = new SimpleDateFormat("yyyy-MM-dd");

	public static void generateReportHeader(Document document, SysctrlCompParamModel companyParamModel, SystemParameterModel systemParameterModel, int pageNumber,String reportNo, String reportName, boolean pasaRpt, boolean processDate) throws DocumentException
	{
		PdfPTable firstHeading = new PdfPTable(3);
		firstHeading.setWidthPercentage(100);

		try 
		{
			PdfPCell cell1 =  new PdfPCell(new Paragraph("Created Date: " + dateTimeFormat.format(new Date()),FontFactory.getFont(FontFactory.HELVETICA, 10)));
			cell1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
			cell1.setBorder(Rectangle.NO_BORDER);
			firstHeading.addCell(cell1);

			PdfPCell cell2 = new PdfPCell(new Paragraph(companyParamModel.getCompName(), FontFactory.getFont(FontFactory.HELVETICA, 10, com.itextpdf.text.Font.BOLD)));
			cell2.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			cell2.setBorder(Rectangle.NO_BORDER);
			firstHeading.addCell(cell2);

			log.debug("PAGE NUMBER ==> "+pageNumber);
			PdfPCell cell3 = new PdfPCell(new Paragraph("Page:  " + pageNumber,   FontFactory.getFont(FontFactory.HELVETICA, 10)));
			cell3.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
			cell3.setBorder(Rectangle.NO_BORDER);
			firstHeading.addCell(cell3);
		} 
		catch (NullPointerException x) 
		{
			log.debug("Error on Page 1 Header" + x);
		}
		document.add(firstHeading);

		//Second Heading
		PdfPTable secondheading = new PdfPTable(3);
		secondheading.setWidthPercentage(100);

		if(pasaRpt || reportNo.equalsIgnoreCase("MR020"))
		{
			log.debug("Populate the PROCESS MONTH !!!!");
			int month = 0;
			if(processDate) {
				month = DateUtil.getMonth(systemParameterModel.getProcessDate());
			}else {
				month = DateUtil.getMonth(getLastDateOfPreviousDay());
			}
			log.debug("int month ==> "+month);
			String abbMonth = WordUtils.capitalizeFully(DateUtil.getMonthAbbreviatedName(month));
			log.debug("abbMonth ==> "+abbMonth);
			int year = 0;
			if(processDate) {
				year = DateUtil.getYear(systemParameterModel.getProcessDate());
			}else {
				year = DateUtil.getYear(getLastDateOfPreviousDay());
			}
			log.debug("year ==> "+year);

			String procMonth = abbMonth + "-" + year;

			PdfPCell processDateCell = new PdfPCell(new Paragraph("Process Month: "+procMonth,FontFactory.getFont(FontFactory.HELVETICA, 10)));
			processDateCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
			processDateCell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
			secondheading.addCell(processDateCell);

			PdfPCell nullCell02 = new PdfPCell(new Paragraph("REG.NO."+ companyParamModel.getRegistrationNr(), FontFactory.getFont(FontFactory.HELVETICA, 10)));
			nullCell02.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			nullCell02.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
			secondheading.addCell(nullCell02);

			PdfPCell nullCell03 = new PdfPCell(new Paragraph(null, FontFactory.getFont(FontFactory.HELVETICA, 10)));
			nullCell03.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			nullCell03.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
			secondheading.addCell(nullCell03);
		}
		else
		{
			//			log.info("Populate the Process Date !!!! ");			
			PdfPCell nullCell01 = new PdfPCell(new Paragraph(" ", FontFactory.getFont(FontFactory.HELVETICA, 10)));
			nullCell01.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
			nullCell01.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
			secondheading.addCell(nullCell01);

			PdfPCell nullCell02 = new PdfPCell(new Paragraph("REG.NO."+ companyParamModel.getRegistrationNr(), FontFactory.getFont(FontFactory.HELVETICA, 10)));
			nullCell02.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			nullCell02.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
			secondheading.addCell(nullCell02);

			if(processDate) {
				PdfPCell nullCell03 = new PdfPCell(new Paragraph("Process Date: "  +secondDateFormat.format(systemParameterModel.getProcessDate()).toString(),FontFactory.getFont(FontFactory.HELVETICA, 10)));
				nullCell03.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
				nullCell03.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
				secondheading.addCell(nullCell03); 
			}else {
				PdfPCell nullCell03 = new PdfPCell(new Paragraph("Process Date: "  +secondDateFormat.format(getLastDateOfPreviousDay()).toString(),FontFactory.getFont(FontFactory.HELVETICA, 10)));
				nullCell03.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
				nullCell03.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
				secondheading.addCell(nullCell03); 
			}
			
		}

		document.add(secondheading);
		addFreeLine(document);

		//Set third heading
		PdfPTable thirdHeading = new PdfPTable(1);
		thirdHeading.setWidthPercentage(100);
		PdfPCell cell1 = null;

		if(reportNo.equalsIgnoreCase("MR020") || reportNo.equalsIgnoreCase("MR022"))
		{
			cell1 = new PdfPCell(new Paragraph(reportNo+" - "+reportName+" - Mandates Batch ", FontFactory.getFont( FontFactory.HELVETICA, 10, com.itextpdf.text.Font.BOLD)));
		}
		else
		{
			cell1 = new PdfPCell(new Paragraph(reportNo+" - "+reportName+"  ", FontFactory.getFont( FontFactory.HELVETICA, 10, com.itextpdf.text.Font.BOLD)));
		}

		cell1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		cell1.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
		thirdHeading.addCell(cell1);

		document.add(thirdHeading);

		if(pasaRpt)
		{
			addFreeLine(document);
		}
	}

	public static void addFreeLine(Document document) throws DocumentException 
	{
		PdfPTable freeline = new PdfPTable(1);
		freeline.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		freeline.setWidthPercentage(100);
		freeline.addCell( new PdfPCell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 10)))).setBorderColor(BaseColor.WHITE);
		document.add(freeline);
	}


	public static void generatePerBankMemberHeader(Document document, String memberName, String memberNo, String reportNo) throws DocumentException 
	{
		PdfPTable memberHeading = new PdfPTable(1);
		memberHeading.setWidthPercentage(100);
		PdfPCell batchMemberTitle = null;
		
		if(reportNo.equalsIgnoreCase("MR023"))
		{
		addFreeLine(document);
		 batchMemberTitle = new PdfPCell(new Paragraph(memberName.trim()+" as Creditor Bank",FontFactory.getFont(FontFactory.HELVETICA, 10,com.itextpdf.text.Font.BOLD)));
			
		}
		else {
		 batchMemberTitle = new PdfPCell(new Paragraph(memberName+"  "+memberNo,FontFactory.getFont(FontFactory.HELVETICA, 10,com.itextpdf.text.Font.BOLD)));

		}
		batchMemberTitle.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		batchMemberTitle.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
		memberHeading.addCell(batchMemberTitle);
		document.add(memberHeading);

		//Do not add freeline as there is a heading to follow
		if(!reportNo.equalsIgnoreCase("PBMD02"))
		{
			addFreeLine(document);
		}
	}

	public static void generatePBMD01Header(Document document) throws DocumentException
	{
		PdfPTable pbmd01Header = new PdfPTable(9);
		pbmd01Header.setWidthPercentage(100);

		PdfPCell credBank = new PdfPCell(new Phrase("Creditor Bank",FontFactory.getFont(FontFactory.HELVETICA, 9, com.itextpdf.text.Font.BOLD)));
		credBank.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		credBank.setUseVariableBorders(true);
		credBank.setBorderColorTop(BaseColor.BLACK);
		credBank.setBorderColorBottom(BaseColor.BLACK);
		credBank.setBorderColorLeft(BaseColor.WHITE);
		credBank.setBorderColorRight(BaseColor.WHITE);
		pbmd01Header.addCell(credBank);

		PdfPCell crMemId = new PdfPCell(new Phrase("Member Number",FontFactory.getFont(FontFactory.HELVETICA, 9, com.itextpdf.text.Font.BOLD)));
		crMemId.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		crMemId.setUseVariableBorders(true);
		crMemId.setBorderColorTop(BaseColor.BLACK);
		crMemId.setBorderColorBottom(BaseColor.BLACK);
		crMemId.setBorderColorLeft(BaseColor.WHITE);
		crMemId.setBorderColorRight(BaseColor.WHITE);
		pbmd01Header.addCell(crMemId);

		PdfPCell fileName = new PdfPCell(new Phrase("Message Id",FontFactory.getFont(FontFactory.HELVETICA, 9, com.itextpdf.text.Font.BOLD)));
		fileName.setColspan(2);
		fileName.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		fileName.setUseVariableBorders(true);
		fileName.setBorderColorTop(BaseColor.BLACK);
		fileName.setBorderColorBottom(BaseColor.BLACK);
		fileName.setBorderColorLeft(BaseColor.WHITE);
		fileName.setBorderColorRight(BaseColor.WHITE);
		pbmd01Header.addCell(fileName);

		PdfPCell transType = new PdfPCell(new Phrase("Transaction Type",FontFactory.getFont(FontFactory.HELVETICA, 9, com.itextpdf.text.Font.BOLD)));
		transType.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		transType.setUseVariableBorders(true);
		transType.setBorderColorTop(BaseColor.BLACK);
		transType.setBorderColorBottom(BaseColor.BLACK);
		transType.setBorderColorLeft(BaseColor.WHITE);
		transType.setBorderColorRight(BaseColor.WHITE);
		pbmd01Header.addCell(transType);

		PdfPCell totalNrDays = new PdfPCell(new Phrase("Number of Days Outstanding",FontFactory.getFont(FontFactory.HELVETICA, 9, com.itextpdf.text.Font.BOLD)));
		totalNrDays.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		totalNrDays.setUseVariableBorders(true);
		totalNrDays.setBorderColorTop(BaseColor.BLACK);
		totalNrDays.setBorderColorBottom(BaseColor.BLACK);
		totalNrDays.setBorderColorLeft(BaseColor.WHITE);
		totalNrDays.setBorderColorRight(BaseColor.WHITE);
		pbmd01Header.addCell(totalNrDays);

		PdfPCell mndtReqTransId = new PdfPCell(new Phrase("Mandate Request Transaction Id",FontFactory.getFont(FontFactory.HELVETICA, 9, com.itextpdf.text.Font.BOLD)));
		mndtReqTransId.setColspan(2);
		mndtReqTransId.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		mndtReqTransId.setUseVariableBorders(true);
		mndtReqTransId.setBorderColorTop(BaseColor.BLACK);
		mndtReqTransId.setBorderColorBottom(BaseColor.BLACK);
		mndtReqTransId.setBorderColorLeft(BaseColor.WHITE);
		mndtReqTransId.setBorderColorRight(BaseColor.WHITE);
		pbmd01Header.addCell(mndtReqTransId);

		PdfPCell serviceId = new PdfPCell(new Phrase("Service Id",FontFactory.getFont(FontFactory.HELVETICA, 9, com.itextpdf.text.Font.BOLD)));
		serviceId.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		serviceId.setUseVariableBorders(true);
		serviceId.setBorderColorTop(BaseColor.BLACK);
		serviceId.setBorderColorBottom(BaseColor.BLACK);
		serviceId.setBorderColorLeft(BaseColor.WHITE);
		serviceId.setBorderColorRight(BaseColor.WHITE);
		pbmd01Header.addCell(serviceId);

		document.add(pbmd01Header);
	}

	public static void generatePBMD02Header(Document document, String reportNo, String reportName,  List<DebtorBankModel> debtorBankModelList, String creditorBank) throws DocumentException
	{	
		PdfPTable thirdHeading = new PdfPTable(1);
		thirdHeading.setWidthPercentage(100);

		PdfPCell cell2 = new PdfPCell(new Paragraph("NUMBER OF REJECTIONS PER REJECTION REASON", FontFactory.getFont( FontFactory.HELVETICA, 10, com.itextpdf.text.Font.BOLD)));
		cell2.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		cell2.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
		thirdHeading.addCell(cell2);

		document.add(thirdHeading);
		addFreeLine(document);

		int headingSize = 0;

		if(debtorBankModelList != null && debtorBankModelList.size() > 0)
		{
			headingSize = debtorBankModelList.size() + 3; //3 is the size of therejectReasonHeading(Colspan)

			PdfPTable batchDetailsHeading = new PdfPTable(headingSize);
			batchDetailsHeading.setWidthPercentage(100);

			PdfPCell rejectReasonHeading = new PdfPCell(new Phrase("MANDATE REJECTION REASONS",FontFactory.getFont(FontFactory.HELVETICA, 9,com.itextpdf.text.Font.BOLD)));
			rejectReasonHeading.setColspan(3);
			rejectReasonHeading.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
			rejectReasonHeading.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
			batchDetailsHeading.addCell(rejectReasonHeading);

			String debtorBankId= null;

			//String debtorBank = null;
			for(DebtorBankModel debtorBankModel :debtorBankModelList)
			{
				debtorBankId= null;
				String debtorBank=debtorBankModel.getMemberName();
				debtorBankId= debtorBankModel.getMemberNo();
				//				log.info("debtorBankId ==> "+debtorBankId);
				//				log.info("creditorBank ==> "+creditorBank);

				PdfPCell debtorBank16 = new PdfPCell(new Phrase(debtorBank,FontFactory.getFont(FontFactory.HELVETICA, 9, com.itextpdf.text.Font.BOLD)));
				debtorBank16.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
				debtorBank16.setBorder(com.itextpdf.text.Rectangle.TOP  | com.itextpdf.text.Rectangle.BOTTOM);
				batchDetailsHeading.addCell(debtorBank16);

				document.add(batchDetailsHeading);
			}
		}
	}

	public static void generatePBMD03Header(Document document, String reportNo, String reportName,  List<DebtorBankModel> debtorBankModelList) throws DocumentException
	{
		PdfPTable thirdHeading = new PdfPTable(1);
		thirdHeading.setWidthPercentage(100);

		PdfPCell cell2 = new PdfPCell(new Paragraph("NUMBER OF REJECTIONS PER REJECTION REASON PER DEBTOR BANK", FontFactory.getFont( FontFactory.HELVETICA, 10, com.itextpdf.text.Font.BOLD)));
		cell2.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		cell2.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
		thirdHeading.addCell(cell2);

		document.add(thirdHeading);
		addFreeLine(document);

		int headingSize = 0;

		if(debtorBankModelList != null && debtorBankModelList.size() > 0)
		{
			headingSize = debtorBankModelList.size() + 4;//for the 1st column
			log.debug("headingSize == "+headingSize);
			PdfPTable batchDetailsHeading = new PdfPTable(headingSize);
			batchDetailsHeading.setWidthPercentage(100);


			PdfPCell rejectReasonHeading = new PdfPCell(new Phrase("MANDATE REJECTION REASONS",FontFactory.getFont(FontFactory.HELVETICA, 9,com.itextpdf.text.Font.BOLD)));
			rejectReasonHeading.setColspan(4);
			rejectReasonHeading.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
			rejectReasonHeading.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
			batchDetailsHeading.addCell(rejectReasonHeading);

			String debtorBankId= null;

			//String debtorBank = null;
			for(DebtorBankModel debtorBankModel :debtorBankModelList)
			{
				debtorBankId= null;
				String debtorBank=debtorBankModel.getMemberName();
				debtorBankId= debtorBankModel.getMemberNo();

				PdfPCell debtorBank16 = new PdfPCell(new Phrase(debtorBank,FontFactory.getFont(FontFactory.HELVETICA, 9, com.itextpdf.text.Font.BOLD)));
				debtorBank16.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
				debtorBank16.setBorder(com.itextpdf.text.Rectangle.TOP | com.itextpdf.text.Rectangle.BOTTOM);
				batchDetailsHeading.addCell(debtorBank16);

				document.add(batchDetailsHeading);

				debtorBankModel.setTxnCount(BigDecimal.ZERO);
			}
		}
	}

	public static void generatePSMD01Header(Document document) throws DocumentException
	{
		PdfPTable batchDetailsHeading = new PdfPTable(8);
		batchDetailsHeading.setWidthPercentage(100);

		PdfPCell credBank = new PdfPCell(new Phrase("Debtor Bank",FontFactory.getFont(FontFactory.HELVETICA, 9,com.itextpdf.text.Font.BOLD)));
		credBank.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		credBank.setUseVariableBorders(true);
		credBank.setBorderColorTop(BaseColor.BLACK);
		credBank.setBorderColorBottom(BaseColor.BLACK);
		credBank.setBorderColorLeft(BaseColor.WHITE);
		credBank.setBorderColorRight(BaseColor.WHITE);
		batchDetailsHeading.addCell(credBank);

		PdfPCell drMemId = new PdfPCell(new Phrase("Creditor Bank",FontFactory.getFont(FontFactory.HELVETICA, 9,com.itextpdf.text.Font.BOLD)));
		drMemId.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		drMemId.setUseVariableBorders(true);
		drMemId.setBorderColorTop(BaseColor.BLACK);
		drMemId.setBorderColorBottom(BaseColor.BLACK);
		drMemId.setBorderColorLeft(BaseColor.WHITE);
		drMemId.setBorderColorRight(BaseColor.WHITE);
		batchDetailsHeading.addCell(drMemId);

		PdfPCell drMemName = new PdfPCell(new Phrase("Initiation Response Outstanding - 1 Day",FontFactory.getFont(FontFactory.HELVETICA, 9,com.itextpdf.text.Font.BOLD)));
		drMemName.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		drMemName.setUseVariableBorders(true);
		drMemName.setBorderColorTop(BaseColor.BLACK);
		drMemName.setBorderColorBottom(BaseColor.BLACK);
		drMemName.setBorderColorLeft(BaseColor.WHITE);
		drMemName.setBorderColorRight(BaseColor.WHITE);
		batchDetailsHeading.addCell(drMemName);

		PdfPCell totalOutstCell = new PdfPCell(new Phrase("Initiation Response Outstanding - 2 Days",FontFactory.getFont(FontFactory.HELVETICA, 9,com.itextpdf.text.Font.BOLD)));
		totalOutstCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		totalOutstCell.setUseVariableBorders(true);
		totalOutstCell.setBorderColorTop(BaseColor.BLACK);
		totalOutstCell.setBorderColorBottom(BaseColor.BLACK);
		totalOutstCell.setBorderColorLeft(BaseColor.WHITE);
		totalOutstCell.setBorderColorRight(BaseColor.WHITE);
		batchDetailsHeading.addCell(totalOutstCell);

		PdfPCell totalNrDays = new PdfPCell(new Phrase("Amendment Response Outstanding - 1 Day",FontFactory.getFont(FontFactory.HELVETICA, 9,com.itextpdf.text.Font.BOLD)));
		totalNrDays.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		totalNrDays.setUseVariableBorders(true);
		totalNrDays.setBorderColorTop(BaseColor.BLACK);
		totalNrDays.setBorderColorBottom(BaseColor.BLACK);
		totalNrDays.setBorderColorLeft(BaseColor.WHITE);
		totalNrDays.setBorderColorRight(BaseColor.WHITE);
		batchDetailsHeading.addCell(totalNrDays);

		PdfPCell amendData = new PdfPCell(new Phrase("Amendment Response Outstanding - 2 Days",FontFactory.getFont(FontFactory.HELVETICA, 9,com.itextpdf.text.Font.BOLD)));
		amendData.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		amendData.setUseVariableBorders(true);
		amendData.setBorderColorTop(BaseColor.BLACK);
		amendData.setBorderColorBottom(BaseColor.BLACK);
		amendData.setBorderColorLeft(BaseColor.WHITE);
		amendData.setBorderColorRight(BaseColor.WHITE);
		batchDetailsHeading.addCell(amendData);

		PdfPCell cancelData = new PdfPCell(new Phrase("Cancellation Response Outstanding - 1 Day",FontFactory.getFont(FontFactory.HELVETICA, 9,com.itextpdf.text.Font.BOLD)));
		cancelData.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		cancelData.setUseVariableBorders(true);
		cancelData.setBorderColorTop(BaseColor.BLACK);
		cancelData.setBorderColorBottom(BaseColor.BLACK);
		cancelData.setBorderColorLeft(BaseColor.WHITE);
		cancelData.setBorderColorRight(BaseColor.WHITE);
		batchDetailsHeading.addCell(cancelData);

		PdfPCell cancelData2 = new PdfPCell(new Phrase("Cancellation Response Outstanding - 2 Days",FontFactory.getFont(FontFactory.HELVETICA, 9,com.itextpdf.text.Font.BOLD)));
		cancelData2.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		cancelData2.setUseVariableBorders(true);
		cancelData2.setBorderColorTop(BaseColor.BLACK);
		cancelData2.setBorderColorBottom(BaseColor.BLACK);
		cancelData2.setBorderColorLeft(BaseColor.WHITE);
		cancelData2.setBorderColorRight(BaseColor.WHITE);
		batchDetailsHeading.addCell(cancelData2);

		document.add(batchDetailsHeading);
		addFreeLine(document);
	}

	public static void generatePSMD02Header(Document document) throws DocumentException
	{
		PdfPTable batchDetailsHeading = new PdfPTable(5);
		batchDetailsHeading.setWidthPercentage(100);

		PdfPCell credBank = new PdfPCell(new Phrase("Debtor Bank",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		credBank.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		credBank.setUseVariableBorders(true);
		credBank.setBorderColorTop(BaseColor.BLACK);
		credBank.setBorderColorBottom(BaseColor.BLACK);
		credBank.setBorderColorLeft(BaseColor.WHITE);
		credBank.setBorderColorRight(BaseColor.WHITE);
		batchDetailsHeading.addCell(credBank);

		PdfPCell drMemId = new PdfPCell(new Phrase("Creditor Bank",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		drMemId.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		drMemId.setUseVariableBorders(true);
		drMemId.setBorderColorTop(BaseColor.BLACK);
		drMemId.setBorderColorBottom(BaseColor.BLACK);
		drMemId.setBorderColorLeft(BaseColor.WHITE);
		drMemId.setBorderColorRight(BaseColor.WHITE);
		batchDetailsHeading.addCell(drMemId);

		PdfPCell iniNrDays = new PdfPCell(new Phrase("Initiation Response Outstanding - 1 Day",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		iniNrDays.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		iniNrDays.setUseVariableBorders(true);
		iniNrDays.setBorderColorTop(BaseColor.BLACK);
		iniNrDays.setBorderColorBottom(BaseColor.BLACK);
		iniNrDays.setBorderColorLeft(BaseColor.WHITE);
		iniNrDays.setBorderColorRight(BaseColor.WHITE);
		batchDetailsHeading.addCell(iniNrDays);

		PdfPCell amendNrDays = new PdfPCell(new Phrase("Amendment Response Outstanding - 1 Day",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		amendNrDays.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		amendNrDays.setUseVariableBorders(true);
		amendNrDays.setBorderColorTop(BaseColor.BLACK);
		amendNrDays.setBorderColorBottom(BaseColor.BLACK);
		amendNrDays.setBorderColorLeft(BaseColor.WHITE);
		amendNrDays.setBorderColorRight(BaseColor.WHITE);
		batchDetailsHeading.addCell(amendNrDays);

		PdfPCell canNrDays = new PdfPCell(new Phrase("Cancellation Response Outstanding - 1 Day",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		canNrDays.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		canNrDays.setUseVariableBorders(true);
		canNrDays.setBorderColorTop(BaseColor.BLACK);
		canNrDays.setBorderColorBottom(BaseColor.BLACK);
		canNrDays.setBorderColorLeft(BaseColor.WHITE);
		canNrDays.setBorderColorRight(BaseColor.WHITE);
		batchDetailsHeading.addCell(canNrDays);

		document.add(batchDetailsHeading);
		addFreeLine(document);
	}

	public static void generatePSMD03Header(Document document, List<CreditorBankModel> creditorBankModelList) throws DocumentException
	{
		int headingSize = 0;

		if(creditorBankModelList != null && creditorBankModelList.size() > 0)
		{
			headingSize = creditorBankModelList.size() + 3;//for the 1st column
			log.debug("headingSize == "+headingSize);
			PdfPTable batchDetailsHeading = new PdfPTable(headingSize);
			batchDetailsHeading.setWidthPercentage(100);

			PdfPCell rejectReasonHeading = new PdfPCell(new Phrase("MANDATE REJECTION REASONS",FontFactory.getFont(FontFactory.HELVETICA, 9,com.itextpdf.text.Font.BOLD)));
			rejectReasonHeading.setColspan(3);
			rejectReasonHeading.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
			rejectReasonHeading.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
			batchDetailsHeading.addCell(rejectReasonHeading);

			String creditorBankId =  null;
			for(CreditorBankModel creditorBankModel :creditorBankModelList)
			{

				String creditorBank=creditorBankModel.getMemberName();
				creditorBankId= creditorBankModel.getMemberNo();

				PdfPCell creditorBanksData = new PdfPCell(new Phrase(creditorBank,FontFactory.getFont(FontFactory.HELVETICA, 9,com.itextpdf.text.Font.BOLD)));
				creditorBanksData.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
				creditorBanksData.setBorder(com.itextpdf.text.Rectangle.TOP | com.itextpdf.text.Rectangle.BOTTOM);
				batchDetailsHeading.addCell(creditorBanksData);

				document.add(batchDetailsHeading);
			}
		}
	}

	public static void generatePSMD04Header(Document document, List<CreditorBankModel> creditorBankModelList) throws DocumentException
	{
		int headingSize = 0;

		if(creditorBankModelList != null && creditorBankModelList.size() > 0)
		{
			headingSize = creditorBankModelList.size() + 3;//for the 1st column
			log.debug("headingSize == "+headingSize);
			PdfPTable batchDetailsHeading = new PdfPTable(headingSize);
			batchDetailsHeading.setWidthPercentage(100);


			PdfPCell rejectReasonHeading = new PdfPCell(new Phrase("MANDATE REJECTION REASONS",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
			rejectReasonHeading.setColspan(3);
			rejectReasonHeading.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
			rejectReasonHeading.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
			batchDetailsHeading.addCell(rejectReasonHeading);

			String creditorBankId =  null;
			for(CreditorBankModel creditorBankModel :creditorBankModelList)
			{

				String creditorBank=creditorBankModel.getMemberName();
				creditorBankId= creditorBankModel.getMemberNo();

				PdfPCell creditorBanksData = new PdfPCell(new Phrase(creditorBank,FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
				creditorBanksData.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
				creditorBanksData.setBorder(com.itextpdf.text.Rectangle.TOP | com.itextpdf.text.Rectangle.BOTTOM);
				batchDetailsHeading.addCell(creditorBanksData);

				document.add(batchDetailsHeading);

				creditorBankModel.setTxnCount(BigDecimal.ZERO);
			}
		}
	}

	public static void generatePSMD05Header(Document document, List<AmendmentCodesModel> amendmentCodesList) throws DocumentException
	{
		if(amendmentCodesList != null && amendmentCodesList.size() > 0)
		{
			PdfPTable batchDetailsHeading = new PdfPTable(6);
			batchDetailsHeading.setWidthPercentage(100);

			PdfPCell credBank = new PdfPCell(new Phrase("CREDITOR BANK",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
			credBank.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
			credBank.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
			batchDetailsHeading.addCell(credBank);

			for (AmendmentCodesModel amendmentCodesModel : amendmentCodesList) 
			{
				PdfPCell amendmentCode = new PdfPCell(new Phrase(amendmentCodesModel.getAmendmentCodesDescription() + " ("+amendmentCodesModel.getAmendmentCodes()+")",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
				amendmentCode.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
				amendmentCode.setUseVariableBorders(true);
				amendmentCode.setBorderColorTop(BaseColor.BLACK);
				amendmentCode.setBorderColorBottom(BaseColor.BLACK);
				amendmentCode.setBorderColorLeft(BaseColor.WHITE);
				amendmentCode.setBorderColorRight(BaseColor.WHITE);
				batchDetailsHeading.addCell(amendmentCode);

				document.add(batchDetailsHeading);
				amendmentCodesModel.setTxnCount(BigDecimal.ZERO);
			}
		}	
	}

	public static void generatePSMD06Header(Document document) throws DocumentException
	{
		PdfPTable batchDetailsHeading = new PdfPTable(6);
		batchDetailsHeading.setWidthPercentage(100);

		PdfPCell credBank = new PdfPCell(new Phrase("Creditor Bank",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		credBank.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		credBank.setUseVariableBorders(true);
		credBank.setBorderColorTop(BaseColor.BLACK);
		credBank.setBorderColorBottom(BaseColor.BLACK);
		credBank.setBorderColorLeft(BaseColor.WHITE);
		credBank.setBorderColorRight(BaseColor.WHITE);
		batchDetailsHeading.addCell(credBank);

		PdfPCell md16 = new PdfPCell(new Phrase("Request By Customer",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		md16.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		md16.setUseVariableBorders(true);
		md16.setBorderColorTop(BaseColor.BLACK);
		md16.setBorderColorBottom(BaseColor.BLACK);
		md16.setBorderColorLeft(BaseColor.WHITE);
		md16.setBorderColorRight(BaseColor.WHITE);
		batchDetailsHeading.addCell(md16);

		PdfPCell md17 = new PdfPCell(new Phrase("Cancellation/amendment requested By Initiating Party",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		md17.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		md17.setUseVariableBorders(true);
		md17.setBorderColorTop(BaseColor.BLACK);
		md17.setBorderColorBottom(BaseColor.BLACK);
		md17.setBorderColorLeft(BaseColor.WHITE);
		md17.setBorderColorRight(BaseColor.WHITE);
		batchDetailsHeading.addCell(md17);

		PdfPCell md19 = new PdfPCell(new Phrase("Unsuspend a Mandate with changes",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		md19.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		md19.setUseVariableBorders(true);
		md19.setBorderColorTop(BaseColor.BLACK);
		md19.setBorderColorBottom(BaseColor.BLACK);
		md19.setBorderColorLeft(BaseColor.WHITE);
		md19.setBorderColorRight(BaseColor.WHITE);
		batchDetailsHeading.addCell(md19);

		PdfPCell md20 = new PdfPCell(new Phrase("Unsuspend an unchanged Mandate",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		md20.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		md20.setUseVariableBorders(true);
		md20.setBorderColorTop(BaseColor.BLACK);
		md20.setBorderColorBottom(BaseColor.BLACK);
		md20.setBorderColorLeft(BaseColor.WHITE);
		md20.setBorderColorRight(BaseColor.WHITE);
		batchDetailsHeading.addCell(md20);


		PdfPCell ms02 = new PdfPCell(new Phrase("Reason has not been specified by Customer (Customer rejects Mandate)",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		ms02.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		ms02.setUseVariableBorders(true);
		ms02.setBorderColorTop(BaseColor.BLACK);
		ms02.setBorderColorBottom(BaseColor.BLACK);
		ms02.setBorderColorLeft(BaseColor.WHITE);
		ms02.setBorderColorRight(BaseColor.WHITE);
		batchDetailsHeading.addCell(ms02);

		document.add(batchDetailsHeading);
	}

	public static void generateMR020Header(Document document,int pageNumber ) throws DocumentException
	{
		PdfPTable mr020Header = new PdfPTable(1);
		mr020Header.setWidthPercentage(100);

		if(pageNumber == 1)
		{
			//Summary Info 
			PdfPCell summTitle = new PdfPCell(new Phrase("SUMMARY INFORMATION",FontFactory.getFont(FontFactory.HELVETICA, 9, com.itextpdf.text.Font.BOLD)));
			summTitle.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			summTitle.setUseVariableBorders(true);
			summTitle.setBorderColorTop(BaseColor.WHITE);
			summTitle.setBorderColorBottom(BaseColor.BLACK);
			summTitle.setBorderColorLeft(BaseColor.WHITE);
			summTitle.setBorderColorRight(BaseColor.WHITE);
			mr020Header.addCell(summTitle);
		}

		if(pageNumber == 5)
		{
			//Status report Info 
			PdfPCell stsTitle = new PdfPCell(new Phrase("STATUS REPORT INFORMATION",FontFactory.getFont(FontFactory.HELVETICA, 9, com.itextpdf.text.Font.BOLD)));
			stsTitle.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			stsTitle.setUseVariableBorders(true);
			stsTitle.setBorderColorTop(BaseColor.WHITE);
			stsTitle.setBorderColorBottom(BaseColor.BLACK);
			stsTitle.setBorderColorLeft(BaseColor.WHITE);
			stsTitle.setBorderColorRight(BaseColor.WHITE);
			mr020Header.addCell(stsTitle);
		}

		document.add(mr020Header);
	}

	public static void generateMr023Header(Document document) throws DocumentException
	{
		log.info("In the MR023 HEADER......................");
		
		PdfPTable secondHeanding = new PdfPTable(1);
		secondHeanding.setWidthPercentage(100);
		
		PdfPCell cell2= new PdfPCell(new Paragraph("Volume", FontFactory.getFont( FontFactory.HELVETICA, 10, com.itextpdf.text.Font.BOLD)));
		cell2.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		cell2.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
		secondHeanding.addCell(cell2);
		document.add(secondHeanding);
		addFreeLine(document);
	}
	

//
//	public static void generateMR024Header(Document document,int pageNumber ) throws DocumentException
//	{
//		PdfPTable mr024Header = new PdfPTable(1);
//		mr024Header.setWidthPercentage(100);
//
//		//Summary Info 
//		PdfPCell summTitle = new PdfPCell(new Phrase("SUMMARY INFORMATION",FontFactory.getFont(FontFactory.HELVETICA, 9, com.itextpdf.text.Font.BOLD)));
//		summTitle.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
//		summTitle.setUseVariableBorders(true);
//		summTitle.setBorderColorTop(BaseColor.WHITE);
//		summTitle.setBorderColorBottom(BaseColor.BLACK);
//		summTitle.setBorderColorLeft(BaseColor.WHITE);
//		summTitle.setBorderColorRight(BaseColor.WHITE);
//		mr024Header.addCell(summTitle);
//
//
//		document.add(mr024Header);
//	}



	public static void generatePBMD07Header(Document document) throws DocumentException
	{
		PdfPTable pbmd01Header = new PdfPTable(9);
		pbmd01Header.setWidthPercentage(100);

		PdfPCell credBank = new PdfPCell(new Phrase("Debtor Bank",FontFactory.getFont(FontFactory.HELVETICA, 9, com.itextpdf.text.Font.BOLD)));
		credBank.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		credBank.setUseVariableBorders(true);
		credBank.setBorderColorTop(BaseColor.BLACK);
		credBank.setBorderColorBottom(BaseColor.BLACK);
		credBank.setBorderColorLeft(BaseColor.WHITE);
		credBank.setBorderColorRight(BaseColor.WHITE);
		pbmd01Header.addCell(credBank);

		PdfPCell crMemId = new PdfPCell(new Phrase("Member Number",FontFactory.getFont(FontFactory.HELVETICA, 9, com.itextpdf.text.Font.BOLD)));
		crMemId.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		crMemId.setUseVariableBorders(true);
		crMemId.setBorderColorTop(BaseColor.BLACK);
		crMemId.setBorderColorBottom(BaseColor.BLACK);
		crMemId.setBorderColorLeft(BaseColor.WHITE);
		crMemId.setBorderColorRight(BaseColor.WHITE);
		pbmd01Header.addCell(crMemId);

		PdfPCell fileName = new PdfPCell(new Phrase("Assigment ID",FontFactory.getFont(FontFactory.HELVETICA, 9, com.itextpdf.text.Font.BOLD)));
		fileName.setColspan(2);
		fileName.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		fileName.setUseVariableBorders(true);
		fileName.setBorderColorTop(BaseColor.BLACK);
		fileName.setBorderColorBottom(BaseColor.BLACK);
		fileName.setBorderColorLeft(BaseColor.WHITE);
		fileName.setBorderColorRight(BaseColor.WHITE);
		pbmd01Header.addCell(fileName);

		PdfPCell transType = new PdfPCell(new Phrase("Suspension ID",FontFactory.getFont(FontFactory.HELVETICA, 9, com.itextpdf.text.Font.BOLD)));
		transType.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		transType.setUseVariableBorders(true);
		transType.setBorderColorTop(BaseColor.BLACK);
		transType.setBorderColorBottom(BaseColor.BLACK);
		transType.setBorderColorLeft(BaseColor.WHITE);
		transType.setBorderColorRight(BaseColor.WHITE);
		pbmd01Header.addCell(transType);

		PdfPCell totalNrDays = new PdfPCell(new Phrase("Number of Days Outstanding",FontFactory.getFont(FontFactory.HELVETICA, 9, com.itextpdf.text.Font.BOLD)));
		totalNrDays.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		totalNrDays.setUseVariableBorders(true);
		totalNrDays.setBorderColorTop(BaseColor.BLACK);
		totalNrDays.setBorderColorBottom(BaseColor.BLACK);
		totalNrDays.setBorderColorLeft(BaseColor.WHITE);
		totalNrDays.setBorderColorRight(BaseColor.WHITE);
		pbmd01Header.addCell(totalNrDays);

		PdfPCell mndtReqTransId = new PdfPCell(new Phrase("Mandate Request Transaction Id",FontFactory.getFont(FontFactory.HELVETICA, 9, com.itextpdf.text.Font.BOLD)));
		mndtReqTransId.setColspan(2);
		mndtReqTransId.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		mndtReqTransId.setUseVariableBorders(true);
		mndtReqTransId.setBorderColorTop(BaseColor.BLACK);
		mndtReqTransId.setBorderColorBottom(BaseColor.BLACK);
		mndtReqTransId.setBorderColorLeft(BaseColor.WHITE);
		mndtReqTransId.setBorderColorRight(BaseColor.WHITE);
		pbmd01Header.addCell(mndtReqTransId);

		PdfPCell serviceId = new PdfPCell(new Phrase("Mandate Reference Number",FontFactory.getFont(FontFactory.HELVETICA, 9, com.itextpdf.text.Font.BOLD)));
		serviceId.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		serviceId.setUseVariableBorders(true);
		serviceId.setBorderColorTop(BaseColor.BLACK);
		serviceId.setBorderColorBottom(BaseColor.BLACK);
		serviceId.setBorderColorLeft(BaseColor.WHITE);
		serviceId.setBorderColorRight(BaseColor.WHITE);
		pbmd01Header.addCell(serviceId);

		document.add(pbmd01Header);
	}

	public static void generatePSMD07Header(Document document) throws DocumentException
	{
		PdfPTable batchDetailsHeading = new PdfPTable(4);
		batchDetailsHeading.setWidthPercentage(100);

		PdfPCell credBank = new PdfPCell(new Phrase("Creditor Bank",FontFactory.getFont(FontFactory.HELVETICA, 9,com.itextpdf.text.Font.BOLD)));
		credBank.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		credBank.setUseVariableBorders(true);
		credBank.setBorderColorTop(BaseColor.BLACK);
		credBank.setBorderColorBottom(BaseColor.BLACK);
		credBank.setBorderColorLeft(BaseColor.WHITE);
		credBank.setBorderColorRight(BaseColor.WHITE);
		batchDetailsHeading.addCell(credBank);

		PdfPCell drMemId = new PdfPCell(new Phrase("Debtor Bank",FontFactory.getFont(FontFactory.HELVETICA, 9,com.itextpdf.text.Font.BOLD)));
		drMemId.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		drMemId.setUseVariableBorders(true);
		drMemId.setBorderColorTop(BaseColor.BLACK);
		drMemId.setBorderColorBottom(BaseColor.BLACK);
		drMemId.setBorderColorLeft(BaseColor.WHITE);
		drMemId.setBorderColorRight(BaseColor.WHITE);
		batchDetailsHeading.addCell(drMemId);

		PdfPCell drMemName = new PdfPCell(new Phrase("Suspension Response Outstanding - 1 Day",FontFactory.getFont(FontFactory.HELVETICA, 9,com.itextpdf.text.Font.BOLD)));
		drMemName.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		drMemName.setUseVariableBorders(true);
		drMemName.setBorderColorTop(BaseColor.BLACK);
		drMemName.setBorderColorBottom(BaseColor.BLACK);
		drMemName.setBorderColorLeft(BaseColor.WHITE);
		drMemName.setBorderColorRight(BaseColor.WHITE);
		batchDetailsHeading.addCell(drMemName);

		PdfPCell totalOutstCell = new PdfPCell(new Phrase("Suspension Response Outstanding - 2 Days",FontFactory.getFont(FontFactory.HELVETICA, 9,com.itextpdf.text.Font.BOLD)));
		totalOutstCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		totalOutstCell.setUseVariableBorders(true);
		totalOutstCell.setBorderColorTop(BaseColor.BLACK);
		totalOutstCell.setBorderColorBottom(BaseColor.BLACK);
		totalOutstCell.setBorderColorLeft(BaseColor.WHITE);
		totalOutstCell.setBorderColorRight(BaseColor.WHITE);
		batchDetailsHeading.addCell(totalOutstCell);

		
		document.add(batchDetailsHeading);
		addFreeLine(document);
	}
	
	public static void generatePBMD08Header(Document document) throws DocumentException
	{
		PdfPTable pbmd08Header = new PdfPTable(9);
		pbmd08Header.setWidthPercentage(100);

		PdfPCell debtBank = new PdfPCell(new Phrase("Debtor Bank",FontFactory.getFont(FontFactory.HELVETICA, 9, com.itextpdf.text.Font.BOLD)));
		debtBank.setColspan(2);
		debtBank.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		debtBank.setUseVariableBorders(true);
		debtBank.setBorderColorTop(BaseColor.BLACK);
		debtBank.setBorderColorBottom(BaseColor.BLACK);
		debtBank.setBorderColorLeft(BaseColor.WHITE);
		debtBank.setBorderColorRight(BaseColor.WHITE);
		pbmd08Header.addCell(debtBank);

		PdfPCell memNum = new PdfPCell(new Phrase("Member Number",FontFactory.getFont(FontFactory.HELVETICA, 9, com.itextpdf.text.Font.BOLD)));
		memNum.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		memNum.setUseVariableBorders(true);
		memNum.setBorderColorTop(BaseColor.BLACK);
		memNum.setBorderColorBottom(BaseColor.BLACK);
		memNum.setBorderColorLeft(BaseColor.WHITE);
		memNum.setBorderColorRight(BaseColor.WHITE);
		pbmd08Header.addCell(memNum);

		PdfPCell fileName = new PdfPCell(new Phrase("Message Id",FontFactory.getFont(FontFactory.HELVETICA, 9, com.itextpdf.text.Font.BOLD)));
		fileName.setColspan(2);
		fileName.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		fileName.setUseVariableBorders(true);
		fileName.setBorderColorTop(BaseColor.BLACK);
		fileName.setBorderColorBottom(BaseColor.BLACK);
		fileName.setBorderColorLeft(BaseColor.WHITE);
		fileName.setBorderColorRight(BaseColor.WHITE);
		pbmd08Header.addCell(fileName);

		PdfPCell transType = new PdfPCell(new Phrase("Transaction Type",FontFactory.getFont(FontFactory.HELVETICA, 9, com.itextpdf.text.Font.BOLD)));
		transType.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		transType.setUseVariableBorders(true);
		transType.setBorderColorTop(BaseColor.BLACK);
		transType.setBorderColorBottom(BaseColor.BLACK);
		transType.setBorderColorLeft(BaseColor.WHITE);
		transType.setBorderColorRight(BaseColor.WHITE);
		pbmd08Header.addCell(transType);

		PdfPCell mndtReqTransId = new PdfPCell(new Phrase("Mandate Request Transaction Id",FontFactory.getFont(FontFactory.HELVETICA, 9, com.itextpdf.text.Font.BOLD)));
		mndtReqTransId.setColspan(2);
		mndtReqTransId.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		mndtReqTransId.setUseVariableBorders(true);
		mndtReqTransId.setBorderColorTop(BaseColor.BLACK);
		mndtReqTransId.setBorderColorBottom(BaseColor.BLACK);
		mndtReqTransId.setBorderColorLeft(BaseColor.WHITE);
		mndtReqTransId.setBorderColorRight(BaseColor.WHITE);
		pbmd08Header.addCell(mndtReqTransId);

		PdfPCell serviceId = new PdfPCell(new Phrase("Service Id",FontFactory.getFont(FontFactory.HELVETICA, 9, com.itextpdf.text.Font.BOLD)));
		serviceId.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		serviceId.setUseVariableBorders(true);
		serviceId.setBorderColorTop(BaseColor.BLACK);
		serviceId.setBorderColorBottom(BaseColor.BLACK);
		serviceId.setBorderColorLeft(BaseColor.WHITE);
		serviceId.setBorderColorRight(BaseColor.WHITE);
		pbmd08Header.addCell(serviceId);

		document.add(pbmd08Header);
	}
	
	public static void generatePBMD09Header(Document document) throws DocumentException
	{
		PdfPTable pbmd01Header = new PdfPTable(8);
		pbmd01Header.setWidthPercentage(100);

		PdfPCell credBank = new PdfPCell(new Phrase("Creditor Bank",FontFactory.getFont(FontFactory.HELVETICA, 9, com.itextpdf.text.Font.BOLD)));
		credBank.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		credBank.setUseVariableBorders(true);
		credBank.setBorderColorTop(BaseColor.BLACK);
		credBank.setBorderColorBottom(BaseColor.BLACK);
		credBank.setBorderColorLeft(BaseColor.WHITE);
		credBank.setBorderColorRight(BaseColor.WHITE);
		pbmd01Header.addCell(credBank);

		PdfPCell crMemId = new PdfPCell(new Phrase("Member Number",FontFactory.getFont(FontFactory.HELVETICA, 9, com.itextpdf.text.Font.BOLD)));
		crMemId.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		crMemId.setUseVariableBorders(true);
		crMemId.setBorderColorTop(BaseColor.BLACK);
		crMemId.setBorderColorBottom(BaseColor.BLACK);
		crMemId.setBorderColorLeft(BaseColor.WHITE);
		crMemId.setBorderColorRight(BaseColor.WHITE);
		pbmd01Header.addCell(crMemId);

		PdfPCell fileName = new PdfPCell(new Phrase("Message Id",FontFactory.getFont(FontFactory.HELVETICA, 9, com.itextpdf.text.Font.BOLD)));
		fileName.setColspan(2);
		fileName.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		fileName.setUseVariableBorders(true);
		fileName.setBorderColorTop(BaseColor.BLACK);
		fileName.setBorderColorBottom(BaseColor.BLACK);
		fileName.setBorderColorLeft(BaseColor.WHITE);
		fileName.setBorderColorRight(BaseColor.WHITE);
		pbmd01Header.addCell(fileName);

		PdfPCell transType = new PdfPCell(new Phrase("Transaction Type",FontFactory.getFont(FontFactory.HELVETICA, 9, com.itextpdf.text.Font.BOLD)));
		transType.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		transType.setUseVariableBorders(true);
		transType.setBorderColorTop(BaseColor.BLACK);
		transType.setBorderColorBottom(BaseColor.BLACK);
		transType.setBorderColorLeft(BaseColor.WHITE);
		transType.setBorderColorRight(BaseColor.WHITE);
		pbmd01Header.addCell(transType);

		PdfPCell mndtReqTransId = new PdfPCell(new Phrase("Mandate Request Transaction Id",FontFactory.getFont(FontFactory.HELVETICA, 9, com.itextpdf.text.Font.BOLD)));
		mndtReqTransId.setColspan(2);
		mndtReqTransId.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		mndtReqTransId.setUseVariableBorders(true);
		mndtReqTransId.setBorderColorTop(BaseColor.BLACK);
		mndtReqTransId.setBorderColorBottom(BaseColor.BLACK);
		mndtReqTransId.setBorderColorLeft(BaseColor.WHITE);
		mndtReqTransId.setBorderColorRight(BaseColor.WHITE);
		pbmd01Header.addCell(mndtReqTransId);

		PdfPCell serviceId = new PdfPCell(new Phrase("Service Id",FontFactory.getFont(FontFactory.HELVETICA, 9, com.itextpdf.text.Font.BOLD)));
		serviceId.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		serviceId.setUseVariableBorders(true);
		serviceId.setBorderColorTop(BaseColor.BLACK);
		serviceId.setBorderColorBottom(BaseColor.BLACK);
		serviceId.setBorderColorLeft(BaseColor.WHITE);
		serviceId.setBorderColorRight(BaseColor.WHITE);
		pbmd01Header.addCell(serviceId);

		document.add(pbmd01Header);
	}
	
	public static Date getLastDateOfPreviousDay() {

		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, -1);

		Date lastDateSOD = calendar.getTime();

		return lastDateSOD;
	}


}
