package com.bsva.reports;

import java.io.File;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.bsva.commons.model.SysctrlCompParamModel;
import com.bsva.commons.model.SystemParameterModel;
import com.bsva.entities.MdtCnfgReportNamesEntity;
import com.bsva.interfaces.AdminBeanRemote;
import com.bsva.interfaces.PropertyUtilRemote;
import com.bsva.interfaces.ServiceBeanRemote;
import com.bsva.interfaces.ValidationBeanRemote;
import com.bsva.utils.Util;
import com.google.common.io.Files;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * @author SalehaR
 *
 */
public class DailyBatchVolumeReportPDF 
{
	public static Logger log=Logger.getLogger(DailyBatchVolumeReportPDF.class);

	String mr022Name,recipientNr, reportDir = null, tempDir = null;
	String reportName, reportNr, pdfFileName;

	public static ServiceBeanRemote beanRemote;
	public static ValidationBeanRemote valBeanRemote;
	private static AdminBeanRemote adminBeanRemote;
	private static PropertyUtilRemote propertyUtilRemote;

	private XSSFWorkbook mr022xlsReport;
	Document document;


	public void generateReport(XSSFWorkbook inputXlsWorkbook) throws FileNotFoundException, DocumentException
	{
		this.mr022xlsReport = inputXlsWorkbook;
		contextAdminBeanCheck();
		contextCheck();
		contextValidationBeanCheck();
		contextPropertyFileCheck();

		try
		{
			tempDir = propertyUtilRemote.getPropValue("ExtractTemp.Out");
			reportDir= propertyUtilRemote.getPropValue("Reports.Output");
			mr022Name = propertyUtilRemote.getPropValue("RPT.DAILY.BATCH.VOLUMES");
			recipientNr = propertyUtilRemote.getPropValue("AC.ACH.RPT.RECIPIENT.NUMBER");
		}
		catch(Exception ex)
		{
			log.error("MR022- Could not find MandateMessageCommons.properties in classpath");	
			reportDir = "/home/opsjava/Delivery/Mandates/Output/Reports/";
			tempDir="/home/opsjava/Delivery/Mandates/Output/temp/";
		}

		//Retrieve Report Name
		MdtCnfgReportNamesEntity reportNameEntity = new MdtCnfgReportNamesEntity();
		reportNameEntity = (MdtCnfgReportNamesEntity) adminBeanRemote.retrieveReportName(mr022Name);
		
		if(reportNameEntity != null)
		{
			reportNr = reportNameEntity.getReportNr();
			reportName = reportNameEntity.getReportName();
		}

		generateReportDetail();
	}


	public void generateReportDetail()throws DocumentException, FileNotFoundException
	{
		DecimalFormat df = new DecimalFormat("### ### ### ### ### ##0.00");
		DateFormat dateTimeFormat = new SimpleDateFormat("yyyyMMdd_HH:mm:ss");
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat fileTimeFormat = new SimpleDateFormat("MM-yyyy");
		Date currentDate = new Date();
		String strSeqNo; 
		DateFormat endDateFormat = new SimpleDateFormat("ddMMyyyy");

		int pageNo = 0;

		String files = tempDir+recipientNr+reportNr+"AC"+endDateFormat.format(currentDate).toString()+".pdf";
		pdfFileName = recipientNr+reportNr+"AC"+endDateFormat.format(currentDate).toString()+".pdf";

		SysctrlCompParamModel companyParamModel = new SysctrlCompParamModel();
		companyParamModel = (SysctrlCompParamModel) beanRemote.retrieveCompanyParameters();

		SystemParameterModel systemParameterModel = new SystemParameterModel();
		systemParameterModel = (SystemParameterModel) adminBeanRemote.retrieveWebActiveSysParameter();

		document = new Document(PageSize.A4);

		FileOutputStream fileOutputStream = new FileOutputStream(files);

		PageEvent pageEvent = new PageEvent(companyParamModel, systemParameterModel, reportNr, reportName, false, false, null, null, null, null,null, null, true);


		PdfWriter writer = null;
		writer = PdfWriter.getInstance(document, fileOutputStream);
		writer.setPageEvent(pageEvent);

		writer.open();
		document.open();

		File file = new File(files);



		//==============SUMMARY INFORMATION PAGE==================//
		PdfPTable freeline = new PdfPTable(1);
		freeline.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		freeline.setWidthPercentage(100);
		freeline.addCell( new PdfPCell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 8)))).setBorderColor(BaseColor.WHITE);

		document.add(freeline);

		PdfPTable subHdrTable = new PdfPTable(6);
		subHdrTable.setWidthPercentage(100);

		PdfPCell inpServCell = new PdfPCell(new Phrase("INPUT SERVICE",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		inpServCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		inpServCell.setBorder(com.itextpdf.text.Rectangle.BOX);
		inpServCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
		subHdrTable.addCell(inpServCell);

		PdfPCell totNrofTxnsCell = new PdfPCell(new Phrase("TOT NR OF TXNS",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		totNrofTxnsCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		totNrofTxnsCell.setBorder(com.itextpdf.text.Rectangle.BOX);
		totNrofTxnsCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
		subHdrTable.addCell(totNrofTxnsCell);

		PdfPCell accpTxnsCell = new PdfPCell(new Phrase("ACCEPTED TXNS",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		accpTxnsCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		accpTxnsCell.setBorder(com.itextpdf.text.Rectangle.BOX);
		accpTxnsCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
		subHdrTable.addCell(accpTxnsCell);

		PdfPCell rejTxnsCell = new PdfPCell(new Phrase("REJECTED TXNS",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		rejTxnsCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		rejTxnsCell.setBorder(com.itextpdf.text.Rectangle.BOX);
		rejTxnsCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
		subHdrTable.addCell(rejTxnsCell);

		PdfPCell extServCell = new PdfPCell(new Phrase("EXTRACT SERVICE",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		extServCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		extServCell.setBorder(com.itextpdf.text.Rectangle.BOX);
		extServCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
		subHdrTable.addCell(extServCell);

		PdfPCell extTxnsCell = new PdfPCell(new Phrase("EXTRACT TXNS",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		extTxnsCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		extTxnsCell.setBorder(com.itextpdf.text.Rectangle.BOX);
		extTxnsCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
		subHdrTable.addCell(extTxnsCell);

		document.add(subHdrTable);

		PdfPTable cdtrHdrTbl = new PdfPTable(2);
		cdtrHdrTbl.setWidthPercentage(100);

		PdfPCell credBankCell = new PdfPCell(new Phrase("CREDITOR BANKS",FontFactory.getFont(FontFactory.HELVETICA, 9, com.itextpdf.text.Font.BOLDITALIC, BaseColor.BLUE)));
		credBankCell.setColspan(2);
		credBankCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		credBankCell.setUseVariableBorders(true);
		cdtrHdrTbl.addCell(credBankCell);
		document.add(cdtrHdrTbl);

		//add summary data 
		XSSFSheet summWorksheet = mr022xlsReport.getSheetAt(0); 
		FormulaEvaluator evaluator = mr022xlsReport.getCreationHelper().createFormulaEvaluator();

		PdfPTable credSummInfoTbl = new PdfPTable(6);
		credSummInfoTbl.setWidthPercentage(100);

		//MANIN
		createSummaryDataRow(credSummInfoTbl, summWorksheet.getRow(6).getCell(0).toString(), summWorksheet.getRow(6).getCell(1).getNumericCellValue(), summWorksheet.getRow(6).getCell(2).getNumericCellValue(), 
				summWorksheet.getRow(6).getCell(3).getNumericCellValue(), summWorksheet.getRow(6).getCell(4).toString(), summWorksheet.getRow(6).getCell(5).getNumericCellValue());
		//MANAM
		createSummaryDataRow(credSummInfoTbl, summWorksheet.getRow(7).getCell(0).toString(), summWorksheet.getRow(7).getCell(1).getNumericCellValue(), summWorksheet.getRow(7).getCell(2).getNumericCellValue(), 
				summWorksheet.getRow(7).getCell(3).getNumericCellValue(), summWorksheet.getRow(7).getCell(4).toString(), summWorksheet.getRow(7).getCell(5).getNumericCellValue());

		//MANCN
		createSummaryDataRow(credSummInfoTbl, summWorksheet.getRow(8).getCell(0).toString(), summWorksheet.getRow(8).getCell(1).getNumericCellValue(), summWorksheet.getRow(8).getCell(2).getNumericCellValue(), 
				summWorksheet.getRow(8).getCell(3).getNumericCellValue(), summWorksheet.getRow(8).getCell(4).toString(), summWorksheet.getRow(8).getCell(5).getNumericCellValue());

		//MANRI
		createSummaryDataRow(credSummInfoTbl, summWorksheet.getRow(9).getCell(0).toString(), summWorksheet.getRow(9).getCell(1).getNumericCellValue(), summWorksheet.getRow(9).getCell(2).getNumericCellValue(), 
				summWorksheet.getRow(9).getCell(3).getNumericCellValue(), summWorksheet.getRow(9).getCell(4).toString(), summWorksheet.getRow(9).getCell(5).getNumericCellValue());

		//SRINP
		createSummaryDataRow(credSummInfoTbl, summWorksheet.getRow(10).getCell(0).toString(), summWorksheet.getRow(10).getCell(1).getNumericCellValue(), summWorksheet.getRow(10).getCell(2).getNumericCellValue(), 
				summWorksheet.getRow(10).getCell(3).getNumericCellValue(), summWorksheet.getRow(10).getCell(4).toString(), summWorksheet.getRow(10).getCell(5).getNumericCellValue());

		document.add(credSummInfoTbl);

		//SubTotal the Creditor Banks	
		PdfPTable crBnkSubTotTbl = new PdfPTable(6);
		crBnkSubTotTbl.setWidthPercentage(100);

		PdfPCell subTotServCell = new PdfPCell(new Phrase(summWorksheet.getRow(11).getCell(0).toString(),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, BaseColor.BLUE)));
		subTotServCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		subTotServCell.setBorder(com.itextpdf.text.Rectangle.BOX);
		crBnkSubTotTbl.addCell(subTotServCell);	

		CellValue subTotVal_1 = evaluator.evaluate(summWorksheet.getRow(11).getCell(1));
		PdfPCell nrOfTxnsSubTot = new PdfPCell(new Phrase(String.valueOf( (int) subTotVal_1.getNumberValue()),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, BaseColor.BLUE)));
		nrOfTxnsSubTot.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		nrOfTxnsSubTot.setBorder(com.itextpdf.text.Rectangle.BOX);
		crBnkSubTotTbl.addCell(nrOfTxnsSubTot);

		CellValue subTotVal_2 = evaluator.evaluate(summWorksheet.getRow(11).getCell(2));
		PdfPCell accpTxnsSubTot = new PdfPCell(new Phrase(String.valueOf( (int) subTotVal_2.getNumberValue()),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, BaseColor.BLUE)));
		accpTxnsSubTot.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		accpTxnsSubTot.setBorder(com.itextpdf.text.Rectangle.BOX);
		crBnkSubTotTbl.addCell(accpTxnsSubTot);

		CellValue subTotVal_3 = evaluator.evaluate(summWorksheet.getRow(11).getCell(3));
		PdfPCell rejTxnsSubTot = new PdfPCell(new Phrase(String.valueOf( (int) subTotVal_3.getNumberValue()),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, BaseColor.BLUE)));
		rejTxnsSubTot.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		rejTxnsSubTot.setBorder(com.itextpdf.text.Rectangle.BOX);
		crBnkSubTotTbl.addCell(rejTxnsSubTot);

		PdfPCell extServSubTot = new PdfPCell(new Phrase(summWorksheet.getRow(11).getCell(4).toString(),FontFactory.getFont(FontFactory.HELVETICA, 8,Font.BOLD, BaseColor.BLUE)));
		extServSubTot.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		extServSubTot.setBorder(com.itextpdf.text.Rectangle.BOX);
		crBnkSubTotTbl.addCell(extServSubTot);

		CellValue subTotVal_4 = evaluator.evaluate(summWorksheet.getRow(11).getCell(5));
		PdfPCell extTxnsSubTot = new PdfPCell(new Phrase(String.valueOf( (int) subTotVal_4.getNumberValue()),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, BaseColor.BLUE)));
		extTxnsSubTot.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		extTxnsSubTot.setBorder(com.itextpdf.text.Rectangle.BOX);
		crBnkSubTotTbl.addCell(extTxnsSubTot);

		document.add(crBnkSubTotTbl);

		PdfPTable dbtrHdrTbl = new PdfPTable(2);
		dbtrHdrTbl.setWidthPercentage(100);

		PdfPCell debtBankCell = new PdfPCell(new Phrase("DEBTOR BANKS",FontFactory.getFont(FontFactory.HELVETICA, 9, com.itextpdf.text.Font.BOLDITALIC, BaseColor.BLUE)));
		debtBankCell.setColspan(2);
		debtBankCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		debtBankCell.setUseVariableBorders(true);
		dbtrHdrTbl.addCell(debtBankCell);
		document.add(dbtrHdrTbl);

		PdfPTable debtSummInfoTbl = new PdfPTable(6);
		debtSummInfoTbl.setWidthPercentage(100);

		//ST101
		createSummaryDataRow(debtSummInfoTbl, summWorksheet.getRow(13).getCell(0).toString(), summWorksheet.getRow(13).getCell(1).getNumericCellValue(), summWorksheet.getRow(13).getCell(2).getNumericCellValue(), 
				summWorksheet.getRow(13).getCell(3).getNumericCellValue(), summWorksheet.getRow(13).getCell(4).toString(), summWorksheet.getRow(13).getCell(5).getNumericCellValue());

		//MANAC
		createSummaryDataRow(debtSummInfoTbl, summWorksheet.getRow(14).getCell(0).toString(), summWorksheet.getRow(14).getCell(1).getNumericCellValue(), summWorksheet.getRow(14).getCell(2).getNumericCellValue(), 
				summWorksheet.getRow(14).getCell(3).getNumericCellValue(), summWorksheet.getRow(14).getCell(4).toString(), summWorksheet.getRow(14).getCell(5).getNumericCellValue());

		//MANRT
		createSummaryDataRow(debtSummInfoTbl, summWorksheet.getRow(15).getCell(0).toString(), summWorksheet.getRow(15).getCell(1).getNumericCellValue(), summWorksheet.getRow(15).getCell(2).getNumericCellValue(), 
				summWorksheet.getRow(15).getCell(3).getNumericCellValue(), summWorksheet.getRow(15).getCell(4).toString(), summWorksheet.getRow(15).getCell(5).getNumericCellValue());

		//SPINP
		createSummaryDataRow(debtSummInfoTbl, summWorksheet.getRow(16).getCell(0).toString(), summWorksheet.getRow(16).getCell(1).getNumericCellValue(), summWorksheet.getRow(16).getCell(2).getNumericCellValue(), 
				summWorksheet.getRow(16).getCell(3).getNumericCellValue(), summWorksheet.getRow(16).getCell(4).toString(), summWorksheet.getRow(16).getCell(5).getNumericCellValue());

		//SubTotal the Debtor Banks	
		PdfPCell subTotServCell_1 = new PdfPCell(new Phrase(summWorksheet.getRow(17).getCell(0).toString(),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, BaseColor.BLUE)));
		subTotServCell_1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		subTotServCell_1.setBorder(com.itextpdf.text.Rectangle.BOX);
		debtSummInfoTbl.addCell(subTotServCell_1);	

		CellValue cellValue_5 = evaluator.evaluate(summWorksheet.getRow(17).getCell(1));
		PdfPCell nrOfTxnsSubTot_1 = new PdfPCell(new Phrase(String.valueOf((int) cellValue_5.getNumberValue()),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, BaseColor.BLUE)));
		nrOfTxnsSubTot_1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		nrOfTxnsSubTot_1.setBorder(com.itextpdf.text.Rectangle.BOX);
		debtSummInfoTbl.addCell(nrOfTxnsSubTot_1);

		CellValue cellValue_6 = evaluator.evaluate(summWorksheet.getRow(17).getCell(2));
		PdfPCell accpTxnsSubTot_1 = new PdfPCell(new Phrase(String.valueOf( (int) cellValue_6.getNumberValue()),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, BaseColor.BLUE)));
		accpTxnsSubTot_1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		accpTxnsSubTot_1.setBorder(com.itextpdf.text.Rectangle.BOX);
		debtSummInfoTbl.addCell(accpTxnsSubTot_1);

		CellValue cellValue_7 = evaluator.evaluate(summWorksheet.getRow(17).getCell(3));
		PdfPCell rejTxnsSubTot_1 = new PdfPCell(new Phrase(String.valueOf( (int) cellValue_7.getNumberValue()),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, BaseColor.BLUE)));
		rejTxnsSubTot_1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		rejTxnsSubTot_1.setBorder(com.itextpdf.text.Rectangle.BOX);
		debtSummInfoTbl.addCell(rejTxnsSubTot_1);

		PdfPCell extServSubTot_1 = new PdfPCell(new Phrase(summWorksheet.getRow(17).getCell(4).toString(),FontFactory.getFont(FontFactory.HELVETICA, 8,Font.BOLD, BaseColor.BLUE)));
		extServSubTot_1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		extServSubTot_1.setBorder(com.itextpdf.text.Rectangle.BOX);
		debtSummInfoTbl.addCell(extServSubTot_1);

		CellValue cellValue_8 = evaluator.evaluate(summWorksheet.getRow(17).getCell(5));
		PdfPCell extTxnsSubTot_1 = new PdfPCell(new Phrase(String.valueOf( (int) cellValue_8.getNumberValue()),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, BaseColor.BLUE)));
		extTxnsSubTot_1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		extTxnsSubTot_1.setBorder(com.itextpdf.text.Rectangle.BOX);
		debtSummInfoTbl.addCell(extTxnsSubTot_1);

		//TOTAL
		PdfPCell servicecell_total = new PdfPCell(new Phrase(summWorksheet.getRow(18).getCell(0).toString(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		servicecell_total.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		servicecell_total.setBorder(com.itextpdf.text.Rectangle.BOX);
		servicecell_total.setBackgroundColor(BaseColor.LIGHT_GRAY);
		debtSummInfoTbl.addCell(servicecell_total);	

		CellValue cellValue = evaluator.evaluate(summWorksheet.getRow(18).getCell(1));

		PdfPCell nrOfTxns_total = new PdfPCell(new Phrase(String.valueOf((int) cellValue.getNumberValue()),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		nrOfTxns_total.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		nrOfTxns_total.setBorder(com.itextpdf.text.Rectangle.BOX);
		nrOfTxns_total.setBackgroundColor(BaseColor.LIGHT_GRAY);
		debtSummInfoTbl.addCell(nrOfTxns_total);

		CellValue cellValue_1 = evaluator.evaluate(summWorksheet.getRow(18).getCell(2));
		PdfPCell accpTxns_total = new PdfPCell(new Phrase(String.valueOf( (int) cellValue_1.getNumberValue()),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		accpTxns_total.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		accpTxns_total.setBorder(com.itextpdf.text.Rectangle.BOX);
		accpTxns_total.setBackgroundColor(BaseColor.LIGHT_GRAY);
		debtSummInfoTbl.addCell(accpTxns_total);

		CellValue cellValue_2 = evaluator.evaluate(summWorksheet.getRow(18).getCell(3));
		PdfPCell rejTxns_total = new PdfPCell(new Phrase(String.valueOf( (int) cellValue_2.getNumberValue()),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		rejTxns_total.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		rejTxns_total.setBorder(com.itextpdf.text.Rectangle.BOX);
		rejTxns_total.setBackgroundColor(BaseColor.LIGHT_GRAY);
		debtSummInfoTbl.addCell(rejTxns_total);

		PdfPCell extServTxns_total = new PdfPCell(new Phrase(summWorksheet.getRow(18).getCell(4).toString(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		extServTxns_total.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		extServTxns_total.setBorder(com.itextpdf.text.Rectangle.BOX);
		extServTxns_total.setBackgroundColor(BaseColor.LIGHT_GRAY);
		debtSummInfoTbl.addCell(extServTxns_total);

		CellValue cellValue_3 = evaluator.evaluate(summWorksheet.getRow(18).getCell(5));
		PdfPCell extTxns_total = new PdfPCell(new Phrase(String.valueOf( (int) cellValue_3.getNumberValue()),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		extTxns_total.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		extTxns_total.setBorder(com.itextpdf.text.Rectangle.BOX);
		extTxns_total.setBackgroundColor(BaseColor.LIGHT_GRAY);
		debtSummInfoTbl.addCell(extTxns_total);

		//Acceptance %
		//TOTAL
		PdfPCell acceptCell = new PdfPCell(new Phrase("Acceptance %",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		acceptCell.setColspan(2);
		acceptCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		acceptCell.setBorder(com.itextpdf.text.Rectangle.BOX);
		acceptCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
		debtSummInfoTbl.addCell(acceptCell);	

		CellValue accpVal = evaluator.evaluate(summWorksheet.getRow(20).getCell(2));
		BigDecimal bdAccVal = new BigDecimal(accpVal.getNumberValue());
		bdAccVal.setScale(2,  RoundingMode.HALF_UP);
		NumberFormat percentage = NumberFormat.getPercentInstance();


		PdfPCell accpPercVal = new PdfPCell(new Phrase(percentage.format(bdAccVal),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		accpPercVal.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		accpPercVal.setBorder(com.itextpdf.text.Rectangle.BOX);
		accpPercVal.setBackgroundColor(BaseColor.LIGHT_GRAY);
		debtSummInfoTbl.addCell(accpPercVal);

		CellValue rejVal = evaluator.evaluate(summWorksheet.getRow(20).getCell(3));
		BigDecimal bdRejVal = new BigDecimal(rejVal.getNumberValue());
		bdRejVal.setScale(2,  RoundingMode.HALF_UP);
		PdfPCell rejPercVal = new PdfPCell(new Phrase(percentage.format(bdRejVal),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		rejPercVal.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		rejPercVal.setBorder(com.itextpdf.text.Rectangle.BOX);
		rejPercVal.setBackgroundColor(BaseColor.LIGHT_GRAY);
		debtSummInfoTbl.addCell(rejPercVal);

		PdfPCell nullCell = new PdfPCell(new Phrase(null,FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		nullCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		nullCell.setBorder(com.itextpdf.text.Rectangle.BOX);
		debtSummInfoTbl.addCell(nullCell);
		debtSummInfoTbl.addCell(nullCell);

		document.add(debtSummInfoTbl);

		//Null Table
		PdfPTable nullTable = new PdfPTable(1);
		nullTable.setWidthPercentage(100);

		//Summary Info 
		PdfPCell nullCell1 = new PdfPCell(new Phrase(" ",FontFactory.getFont(FontFactory.HELVETICA, 9, com.itextpdf.text.Font.BOLD)));
		nullCell1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		nullCell1.setBorder(Rectangle.NO_BORDER);
		nullTable.addCell(nullCell1);

		document.add(nullTable);

		PdfPTable stsRptHdrTbl = new PdfPTable(1);
		stsRptHdrTbl.setWidthPercentage(100);

		//Summary Info 
		PdfPCell stsRptTitle = new PdfPCell(new Phrase("STATUS REPORTS SUMMARY INFORMATION",FontFactory.getFont(FontFactory.HELVETICA, 9, com.itextpdf.text.Font.BOLD)));
		stsRptTitle.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		stsRptTitle.setBorder(Rectangle.BOTTOM);
		stsRptHdrTbl.addCell(stsRptTitle);

		document.add(stsRptHdrTbl);
		document.add(nullTable);

		PdfPTable subHdr2Table = new PdfPTable(6);
		subHdr2Table.setWidthPercentage(100);

		PdfPCell nullCell_noBorder = new PdfPCell(new Phrase(null,FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		nullCell_noBorder.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		nullCell_noBorder.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);

		PdfPCell inpServStsCell = new PdfPCell(new Phrase("INPUT SERVICE",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		inpServStsCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		inpServStsCell.setBorder(com.itextpdf.text.Rectangle.BOX);
		inpServStsCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
		subHdr2Table.addCell(inpServStsCell);

		PdfPCell totNrofTxns2Cell = new PdfPCell(new Phrase("TOT NR OF TXNS",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		totNrofTxns2Cell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		totNrofTxns2Cell.setBorder(com.itextpdf.text.Rectangle.BOX);
		totNrofTxns2Cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
		subHdr2Table.addCell(totNrofTxns2Cell);
		subHdr2Table.addCell(nullCell_noBorder);
		subHdr2Table.addCell(nullCell_noBorder);
		subHdr2Table.addCell(nullCell_noBorder);
		subHdr2Table.addCell(nullCell_noBorder);

		document.add(subHdr2Table);

		PdfPTable cdtrHdrTbl_stsRpt = new PdfPTable(6);
		cdtrHdrTbl_stsRpt.setWidthPercentage(100);

		PdfPCell credBankSRCell = new PdfPCell(new Phrase("CREDITOR BANKS",FontFactory.getFont(FontFactory.HELVETICA, 9, com.itextpdf.text.Font.BOLDITALIC, BaseColor.BLUE)));
		credBankSRCell.setColspan(2);
		credBankSRCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		credBankSRCell.setBorder(Rectangle.BOX);
		cdtrHdrTbl_stsRpt.addCell(credBankSRCell);
		cdtrHdrTbl_stsRpt.addCell(nullCell_noBorder);
		cdtrHdrTbl_stsRpt.addCell(nullCell_noBorder);
		cdtrHdrTbl_stsRpt.addCell(nullCell_noBorder);
		cdtrHdrTbl_stsRpt.addCell(nullCell_noBorder);
		document.add(cdtrHdrTbl_stsRpt);

		//StatusReport Information
		PdfPTable statusRptTbl = new PdfPTable(6);
		statusRptTbl.setWidthPercentage(100);
		//Add ST100
		createSummStatusRptRow(statusRptTbl, summWorksheet.getRow(24).getCell(0).toString(), summWorksheet.getRow(24).getCell(1).getNumericCellValue());
		//Add ST105
		createSummStatusRptRow(statusRptTbl, summWorksheet.getRow(25).getCell(0).toString(), summWorksheet.getRow(25).getCell(1).getNumericCellValue());
		//Add ST008
		createSummStatusRptRow(statusRptTbl, summWorksheet.getRow(26).getCell(0).toString(), summWorksheet.getRow(26).getCell(1).getNumericCellValue());
		document.add(statusRptTbl);

		//Sub Total the Creditor Bank Status Report Info
		PdfPTable crBnkStsRptSTotTbl = new PdfPTable(6);
		crBnkStsRptSTotTbl.setWidthPercentage(100);

		PdfPCell subTotServCell_2 = new PdfPCell(new Phrase(summWorksheet.getRow(27).getCell(0).toString(),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, BaseColor.BLUE)));
		subTotServCell_2.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		subTotServCell_2.setBorder(com.itextpdf.text.Rectangle.BOX);
		crBnkStsRptSTotTbl.addCell(subTotServCell_2);	

		CellValue cellValue_9 = evaluator.evaluate(summWorksheet.getRow(27).getCell(1));
		PdfPCell nrOfTxnsSubTot_2 = new PdfPCell(new Phrase(String.valueOf( (int) cellValue_9.getNumberValue()),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, BaseColor.BLUE)));
		nrOfTxnsSubTot_2.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		nrOfTxnsSubTot_2.setBorder(com.itextpdf.text.Rectangle.BOX);
		crBnkStsRptSTotTbl.addCell(nrOfTxnsSubTot_2);

		PdfPCell nullCell_55 = new PdfPCell(new Phrase(null,FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		nullCell_55.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		nullCell_55.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
		crBnkStsRptSTotTbl.addCell(nullCell_55);
		crBnkStsRptSTotTbl.addCell(nullCell_55);
		crBnkStsRptSTotTbl.addCell(nullCell_55);
		crBnkStsRptSTotTbl.addCell(nullCell_55);

		document.add(crBnkStsRptSTotTbl);

		PdfPTable dbtrHdrTbl_stsRpt = new PdfPTable(6);
		dbtrHdrTbl_stsRpt.setWidthPercentage(100);

		PdfPCell debtBankSRCell = new PdfPCell(new Phrase("DEBTOR BANKS",FontFactory.getFont(FontFactory.HELVETICA, 9, com.itextpdf.text.Font.BOLDITALIC, BaseColor.BLUE)));
		debtBankSRCell.setColspan(2);
		debtBankSRCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		debtBankSRCell.setBorder(Rectangle.BOX);
		dbtrHdrTbl_stsRpt.addCell(debtBankSRCell);
		dbtrHdrTbl_stsRpt.addCell(nullCell_noBorder);
		dbtrHdrTbl_stsRpt.addCell(nullCell_noBorder);
		dbtrHdrTbl_stsRpt.addCell(nullCell_noBorder);
		dbtrHdrTbl_stsRpt.addCell(nullCell_noBorder);
		document.add(dbtrHdrTbl_stsRpt);

		//StatusReport Information
		PdfPTable statusRptDbtrTbl = new PdfPTable(6);
		statusRptDbtrTbl.setWidthPercentage(100);
		//Add ST102
		createSummStatusRptRow(statusRptDbtrTbl, summWorksheet.getRow(29).getCell(0).toString(), summWorksheet.getRow(29).getCell(1).getNumericCellValue());
		//Add ST104
		createSummStatusRptRow(statusRptDbtrTbl, summWorksheet.getRow(30).getCell(0).toString(), summWorksheet.getRow(30).getCell(1).getNumericCellValue());
		//Add ST106
		createSummStatusRptRow(statusRptDbtrTbl, summWorksheet.getRow(31).getCell(0).toString(), summWorksheet.getRow(31).getCell(1).getNumericCellValue());
		//Add ST007
		createSummStatusRptRow(statusRptDbtrTbl, summWorksheet.getRow(32).getCell(0).toString(), summWorksheet.getRow(32).getCell(1).getNumericCellValue());
		document.add(statusRptDbtrTbl);

		//Sub Total the Debtor Bank Status Report Info
		PdfPTable drBnkStsRptSTotTbl = new PdfPTable(6);
		drBnkStsRptSTotTbl.setWidthPercentage(100);

		PdfPCell subTotServCell_3 = new PdfPCell(new Phrase(summWorksheet.getRow(33).getCell(0).toString(),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, BaseColor.BLUE)));
		subTotServCell_3.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		subTotServCell_3.setBorder(com.itextpdf.text.Rectangle.BOX);
		drBnkStsRptSTotTbl.addCell(subTotServCell_3);	

		CellValue cellValue_10 = evaluator.evaluate(summWorksheet.getRow(33).getCell(1));
		PdfPCell nrOfTxnsSubTot_3 = new PdfPCell(new Phrase(String.valueOf( (int) cellValue_10.getNumberValue()),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, BaseColor.BLUE)));
		nrOfTxnsSubTot_3.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		nrOfTxnsSubTot_3.setBorder(com.itextpdf.text.Rectangle.BOX);
		drBnkStsRptSTotTbl.addCell(nrOfTxnsSubTot_3);

		PdfPCell nullCell_56 = new PdfPCell(new Phrase(null,FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		nullCell_56.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		nullCell_56.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
		drBnkStsRptSTotTbl.addCell(nullCell_56);
		drBnkStsRptSTotTbl.addCell(nullCell_56);
		drBnkStsRptSTotTbl.addCell(nullCell_56);
		drBnkStsRptSTotTbl.addCell(nullCell_56);

		document.add(drBnkStsRptSTotTbl);

		//Total for Status Reports
		PdfPTable stsRptTotalTbl = new PdfPTable(6);
		stsRptTotalTbl.setWidthPercentage(100);

		PdfPCell servicecell_total_2 = new PdfPCell(new Phrase(summWorksheet.getRow(34).getCell(0).toString(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		servicecell_total_2.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		servicecell_total_2.setBorder(com.itextpdf.text.Rectangle.BOX);
		servicecell_total_2.setBackgroundColor(BaseColor.LIGHT_GRAY);
		stsRptTotalTbl.addCell(servicecell_total_2);	

		CellValue cellValue_total = evaluator.evaluate(summWorksheet.getRow(34).getCell(1));

		PdfPCell nrOfTxns_total_2 = new PdfPCell(new Phrase(String.valueOf((int) cellValue_total.getNumberValue()),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		nrOfTxns_total_2.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		nrOfTxns_total_2.setBorder(com.itextpdf.text.Rectangle.BOX);
		nrOfTxns_total_2.setBackgroundColor(BaseColor.LIGHT_GRAY);
		stsRptTotalTbl.addCell(nrOfTxns_total_2);
		stsRptTotalTbl.addCell(nullCell_noBorder);
		stsRptTotalTbl.addCell(nullCell_noBorder);
		stsRptTotalTbl.addCell(nullCell_noBorder);
		stsRptTotalTbl.addCell(nullCell_noBorder);

		document.add(stsRptTotalTbl);

		//==============CREDITOR INFORMATION PAGE_1 ==================//

		//Move to next page
		document.newPage();

		document.add(nullTable);
		//Creditor Banks Information
		PdfPTable crdBankHdrTable = new PdfPTable(8);
		crdBankHdrTable.setWidthPercentage(100);

		PdfPCell subByCdtBanksCell = new PdfPCell(new Phrase("SUBMITTED BY CREDITOR BANKS",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		subByCdtBanksCell.setColspan(5);
		subByCdtBanksCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		subByCdtBanksCell.setBorder(com.itextpdf.text.Rectangle.BOX);
		subByCdtBanksCell.setBackgroundColor(BaseColor.CYAN);
		crdBankHdrTable.addCell(subByCdtBanksCell);

		PdfPCell extToDebtBanksCell = new PdfPCell(new Phrase("EXTRACTED TO DEBTOR BANKS",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		extToDebtBanksCell.setColspan(3);
		extToDebtBanksCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		extToDebtBanksCell.setBorder(com.itextpdf.text.Rectangle.BOX);
		extToDebtBanksCell.setBackgroundColor(BaseColor.ORANGE);
		crdBankHdrTable.addCell(extToDebtBanksCell);

		document.add(crdBankHdrTable);

		PdfPTable subHdrTable_1 = new PdfPTable(8);
		subHdrTable_1.setWidthPercentage(100);

		PdfPCell inpServCell_1 = new PdfPCell(new Phrase("SERVICE",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		inpServCell_1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		inpServCell_1.setBorder(com.itextpdf.text.Rectangle.BOX);
		inpServCell_1.setBackgroundColor(BaseColor.LIGHT_GRAY);
		subHdrTable_1.addCell(inpServCell_1);

		PdfPCell bankCell_1 = new PdfPCell(new Phrase("BANKS",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		bankCell_1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		bankCell_1.setBorder(com.itextpdf.text.Rectangle.BOX);
		bankCell_1.setBackgroundColor(BaseColor.LIGHT_GRAY);
		subHdrTable_1.addCell(bankCell_1);

		PdfPCell totNrofTxnsCell_1 = new PdfPCell(new Phrase("TOT NR OF TXNS",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		totNrofTxnsCell_1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		totNrofTxnsCell_1.setBorder(com.itextpdf.text.Rectangle.BOX);
		totNrofTxnsCell_1.setBackgroundColor(BaseColor.LIGHT_GRAY);
		subHdrTable_1.addCell(totNrofTxnsCell_1);

		PdfPCell accpTxnsCell_1 = new PdfPCell(new Phrase("ACCEPTED TXNS",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		accpTxnsCell_1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		accpTxnsCell_1.setBorder(com.itextpdf.text.Rectangle.BOX);
		accpTxnsCell_1.setBackgroundColor(BaseColor.LIGHT_GRAY);
		subHdrTable_1.addCell(accpTxnsCell_1);

		PdfPCell rejTxnsCell_1 = new PdfPCell(new Phrase("REJECTED TXNS",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		rejTxnsCell_1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		rejTxnsCell_1.setBorder(com.itextpdf.text.Rectangle.BOX);
		rejTxnsCell_1.setBackgroundColor(BaseColor.LIGHT_GRAY);
		subHdrTable_1.addCell(rejTxnsCell_1);

		PdfPCell extServCell_1 = new PdfPCell(new Phrase("SERVICE",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		extServCell_1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		extServCell_1.setBorder(com.itextpdf.text.Rectangle.BOX);
		extServCell_1.setBackgroundColor(BaseColor.LIGHT_GRAY);
		subHdrTable_1.addCell(extServCell_1);

		PdfPCell extBankCell_1 = new PdfPCell(new Phrase("BANKS",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		extBankCell_1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		extBankCell_1.setBorder(com.itextpdf.text.Rectangle.BOX);
		extBankCell_1.setBackgroundColor(BaseColor.LIGHT_GRAY);
		subHdrTable_1.addCell(extBankCell_1);

		PdfPCell extTxnsCell_1 = new PdfPCell(new Phrase("EXTRACT TXNS",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		extTxnsCell_1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		extTxnsCell_1.setBorder(com.itextpdf.text.Rectangle.BOX);
		extTxnsCell_1.setBackgroundColor(BaseColor.LIGHT_GRAY);
		subHdrTable_1.addCell(extTxnsCell_1);

		document.add(subHdrTable_1);

		XSSFSheet inpCredWorksheet = mr022xlsReport.getSheetAt(1); 

		//Creditor Banks Information
		PdfPTable cdtrDataTbl = new PdfPTable(8);
		cdtrDataTbl.setWidthPercentage(100);

		//
		createCreditorBankRow(cdtrDataTbl, inpCredWorksheet.getRow(6).getCell(0).toString(), inpCredWorksheet.getRow(6).getCell(1).toString(), inpCredWorksheet.getRow(6).getCell(2).getNumericCellValue(), inpCredWorksheet.getRow(6).getCell(3).getNumericCellValue(), inpCredWorksheet.getRow(6).getCell(4).getNumericCellValue(),  inpCredWorksheet.getRow(6).getCell(6).toString(),  inpCredWorksheet.getRow(6).getCell(7).toString(),  inpCredWorksheet.getRow(6).getCell(8).getNumericCellValue());
		createCreditorBankRow(cdtrDataTbl, inpCredWorksheet.getRow(7).getCell(0).toString(), inpCredWorksheet.getRow(7).getCell(1).toString(), inpCredWorksheet.getRow(7).getCell(2).getNumericCellValue(), inpCredWorksheet.getRow(7).getCell(3).getNumericCellValue(), inpCredWorksheet.getRow(7).getCell(4).getNumericCellValue(),  inpCredWorksheet.getRow(7).getCell(6).toString(),  inpCredWorksheet.getRow(7).getCell(7).toString(),  inpCredWorksheet.getRow(7).getCell(8).getNumericCellValue());
		createCreditorBankRow(cdtrDataTbl, inpCredWorksheet.getRow(8).getCell(0).toString(), inpCredWorksheet.getRow(8).getCell(1).toString(), inpCredWorksheet.getRow(8).getCell(2).getNumericCellValue(), inpCredWorksheet.getRow(8).getCell(3).getNumericCellValue(), inpCredWorksheet.getRow(8).getCell(4).getNumericCellValue(),  inpCredWorksheet.getRow(8).getCell(6).toString(),  inpCredWorksheet.getRow(8).getCell(7).toString(),  inpCredWorksheet.getRow(8).getCell(8).getNumericCellValue());
		createCreditorBankRow(cdtrDataTbl, inpCredWorksheet.getRow(9).getCell(0).toString(), inpCredWorksheet.getRow(9).getCell(1).toString(), inpCredWorksheet.getRow(9).getCell(2).getNumericCellValue(), inpCredWorksheet.getRow(9).getCell(3).getNumericCellValue(), inpCredWorksheet.getRow(9).getCell(4).getNumericCellValue(),  inpCredWorksheet.getRow(9).getCell(6).toString(),  inpCredWorksheet.getRow(9).getCell(7).toString(),  inpCredWorksheet.getRow(9).getCell(8).getNumericCellValue());
		createCreditorBankRow(cdtrDataTbl, inpCredWorksheet.getRow(10).getCell(0).toString(), inpCredWorksheet.getRow(10).getCell(1).toString(), inpCredWorksheet.getRow(10).getCell(2).getNumericCellValue(), inpCredWorksheet.getRow(10).getCell(3).getNumericCellValue(), inpCredWorksheet.getRow(10).getCell(4).getNumericCellValue(),  inpCredWorksheet.getRow(10).getCell(6).toString(),  inpCredWorksheet.getRow(10).getCell(7).toString(),  inpCredWorksheet.getRow(10).getCell(8).getNumericCellValue());
		createCreditorBankRow(cdtrDataTbl, inpCredWorksheet.getRow(11).getCell(0).toString(), inpCredWorksheet.getRow(11).getCell(1).toString(), inpCredWorksheet.getRow(11).getCell(2).getNumericCellValue(), inpCredWorksheet.getRow(11).getCell(3).getNumericCellValue(), inpCredWorksheet.getRow(11).getCell(4).getNumericCellValue(),  inpCredWorksheet.getRow(11).getCell(6).toString(),  inpCredWorksheet.getRow(11).getCell(7).toString(),  inpCredWorksheet.getRow(11).getCell(8).getNumericCellValue());
		createCreditorBankRow(cdtrDataTbl, inpCredWorksheet.getRow(12).getCell(0).toString(), inpCredWorksheet.getRow(12).getCell(1).toString(), inpCredWorksheet.getRow(12).getCell(2).getNumericCellValue(), inpCredWorksheet.getRow(12).getCell(3).getNumericCellValue(), inpCredWorksheet.getRow(12).getCell(4).getNumericCellValue(),  inpCredWorksheet.getRow(12).getCell(6).toString(),  inpCredWorksheet.getRow(12).getCell(7).toString(),  inpCredWorksheet.getRow(12).getCell(8).getNumericCellValue());
		createCreditorBankRow(cdtrDataTbl, inpCredWorksheet.getRow(13).getCell(0).toString(), inpCredWorksheet.getRow(13).getCell(1).toString(), inpCredWorksheet.getRow(13).getCell(2).getNumericCellValue(), inpCredWorksheet.getRow(13).getCell(3).getNumericCellValue(), inpCredWorksheet.getRow(13).getCell(4).getNumericCellValue(),  inpCredWorksheet.getRow(13).getCell(6).toString(),  inpCredWorksheet.getRow(13).getCell(7).toString(),  inpCredWorksheet.getRow(13).getCell(8).getNumericCellValue());
		createCreditorBankRow(cdtrDataTbl, inpCredWorksheet.getRow(14).getCell(0).toString(), inpCredWorksheet.getRow(14).getCell(1).toString(), inpCredWorksheet.getRow(14).getCell(2).getNumericCellValue(), inpCredWorksheet.getRow(14).getCell(3).getNumericCellValue(), inpCredWorksheet.getRow(14).getCell(4).getNumericCellValue(),  inpCredWorksheet.getRow(14).getCell(6).toString(),  inpCredWorksheet.getRow(14).getCell(7).toString(),  inpCredWorksheet.getRow(14).getCell(8).getNumericCellValue());
		createCreditorBankRow(cdtrDataTbl, inpCredWorksheet.getRow(15).getCell(0).toString(), inpCredWorksheet.getRow(15).getCell(1).toString(), inpCredWorksheet.getRow(15).getCell(2).getNumericCellValue(), inpCredWorksheet.getRow(15).getCell(3).getNumericCellValue(), inpCredWorksheet.getRow(15).getCell(4).getNumericCellValue(),  inpCredWorksheet.getRow(15).getCell(6).toString(),  inpCredWorksheet.getRow(15).getCell(7).toString(),  inpCredWorksheet.getRow(15).getCell(8).getNumericCellValue());
		createCreditorBankRow(cdtrDataTbl, inpCredWorksheet.getRow(16).getCell(0).toString(), inpCredWorksheet.getRow(16).getCell(1).toString(), inpCredWorksheet.getRow(16).getCell(2).getNumericCellValue(), inpCredWorksheet.getRow(16).getCell(3).getNumericCellValue(), inpCredWorksheet.getRow(16).getCell(4).getNumericCellValue(),  inpCredWorksheet.getRow(16).getCell(6).toString(),  inpCredWorksheet.getRow(16).getCell(7).toString(),  inpCredWorksheet.getRow(16).getCell(8).getNumericCellValue());
		document.add(cdtrDataTbl);
		//Total the 
		//Creditor Banks Information
		PdfPTable cdtrTotalDataTbl = new PdfPTable(8);
		cdtrTotalDataTbl.setWidthPercentage(100);
		createTotalCreditorBankRow(cdtrTotalDataTbl, inpCredWorksheet.getRow(17).getCell(2).getNumericCellValue(),  inpCredWorksheet.getRow(17).getCell(3).getNumericCellValue(),  inpCredWorksheet.getRow(17).getCell(4).getNumericCellValue(),  inpCredWorksheet.getRow(17).getCell(8).getNumericCellValue());

		document.add(cdtrTotalDataTbl);
		document.add(nullTable);

		PdfPTable manamTable = new PdfPTable(8);
		manamTable.setWidthPercentage(100);

		//MANAM
		createCreditorBankRow(manamTable, inpCredWorksheet.getRow(19).getCell(0).toString(), inpCredWorksheet.getRow(19).getCell(1).toString(), inpCredWorksheet.getRow(19).getCell(2).getNumericCellValue(), inpCredWorksheet.getRow(19).getCell(3).getNumericCellValue(), inpCredWorksheet.getRow(19).getCell(4).getNumericCellValue(),  inpCredWorksheet.getRow(19).getCell(6).toString(),  inpCredWorksheet.getRow(19).getCell(7).toString(),  inpCredWorksheet.getRow(19).getCell(8).getNumericCellValue());
		createCreditorBankRow(manamTable, inpCredWorksheet.getRow(20).getCell(0).toString(), inpCredWorksheet.getRow(20).getCell(1).toString(), inpCredWorksheet.getRow(20).getCell(2).getNumericCellValue(), inpCredWorksheet.getRow(20).getCell(3).getNumericCellValue(), inpCredWorksheet.getRow(20).getCell(4).getNumericCellValue(),  inpCredWorksheet.getRow(20).getCell(6).toString(),  inpCredWorksheet.getRow(20).getCell(7).toString(),  inpCredWorksheet.getRow(20).getCell(8).getNumericCellValue());
		createCreditorBankRow(manamTable, inpCredWorksheet.getRow(21).getCell(0).toString(), inpCredWorksheet.getRow(21).getCell(1).toString(), inpCredWorksheet.getRow(21).getCell(2).getNumericCellValue(), inpCredWorksheet.getRow(21).getCell(3).getNumericCellValue(), inpCredWorksheet.getRow(21).getCell(4).getNumericCellValue(),  inpCredWorksheet.getRow(21).getCell(6).toString(),  inpCredWorksheet.getRow(21).getCell(7).toString(),  inpCredWorksheet.getRow(21).getCell(8).getNumericCellValue());
		createCreditorBankRow(manamTable, inpCredWorksheet.getRow(22).getCell(0).toString(), inpCredWorksheet.getRow(22).getCell(1).toString(), inpCredWorksheet.getRow(22).getCell(2).getNumericCellValue(), inpCredWorksheet.getRow(22).getCell(3).getNumericCellValue(), inpCredWorksheet.getRow(22).getCell(4).getNumericCellValue(),  inpCredWorksheet.getRow(22).getCell(6).toString(),  inpCredWorksheet.getRow(22).getCell(7).toString(),  inpCredWorksheet.getRow(22).getCell(8).getNumericCellValue());
		createCreditorBankRow(manamTable, inpCredWorksheet.getRow(23).getCell(0).toString(), inpCredWorksheet.getRow(23).getCell(1).toString(), inpCredWorksheet.getRow(23).getCell(2).getNumericCellValue(), inpCredWorksheet.getRow(23).getCell(3).getNumericCellValue(), inpCredWorksheet.getRow(23).getCell(4).getNumericCellValue(),  inpCredWorksheet.getRow(23).getCell(6).toString(),  inpCredWorksheet.getRow(23).getCell(7).toString(),  inpCredWorksheet.getRow(23).getCell(8).getNumericCellValue());
		createCreditorBankRow(manamTable, inpCredWorksheet.getRow(24).getCell(0).toString(), inpCredWorksheet.getRow(24).getCell(1).toString(), inpCredWorksheet.getRow(24).getCell(2).getNumericCellValue(), inpCredWorksheet.getRow(24).getCell(3).getNumericCellValue(), inpCredWorksheet.getRow(24).getCell(4).getNumericCellValue(),  inpCredWorksheet.getRow(24).getCell(6).toString(),  inpCredWorksheet.getRow(24).getCell(7).toString(),  inpCredWorksheet.getRow(24).getCell(8).getNumericCellValue());
		createCreditorBankRow(manamTable, inpCredWorksheet.getRow(25).getCell(0).toString(), inpCredWorksheet.getRow(25).getCell(1).toString(), inpCredWorksheet.getRow(25).getCell(2).getNumericCellValue(), inpCredWorksheet.getRow(25).getCell(3).getNumericCellValue(), inpCredWorksheet.getRow(25).getCell(4).getNumericCellValue(),  inpCredWorksheet.getRow(25).getCell(6).toString(),  inpCredWorksheet.getRow(25).getCell(7).toString(),  inpCredWorksheet.getRow(25).getCell(8).getNumericCellValue());
		createCreditorBankRow(manamTable, inpCredWorksheet.getRow(26).getCell(0).toString(), inpCredWorksheet.getRow(26).getCell(1).toString(), inpCredWorksheet.getRow(26).getCell(2).getNumericCellValue(), inpCredWorksheet.getRow(26).getCell(3).getNumericCellValue(), inpCredWorksheet.getRow(26).getCell(4).getNumericCellValue(),  inpCredWorksheet.getRow(26).getCell(6).toString(),  inpCredWorksheet.getRow(26).getCell(7).toString(),  inpCredWorksheet.getRow(26).getCell(8).getNumericCellValue());
		createCreditorBankRow(manamTable, inpCredWorksheet.getRow(27).getCell(0).toString(), inpCredWorksheet.getRow(27).getCell(1).toString(), inpCredWorksheet.getRow(27).getCell(2).getNumericCellValue(), inpCredWorksheet.getRow(27).getCell(3).getNumericCellValue(), inpCredWorksheet.getRow(27).getCell(4).getNumericCellValue(),  inpCredWorksheet.getRow(27).getCell(6).toString(),  inpCredWorksheet.getRow(27).getCell(7).toString(),  inpCredWorksheet.getRow(27).getCell(8).getNumericCellValue());
		createCreditorBankRow(manamTable, inpCredWorksheet.getRow(28).getCell(0).toString(), inpCredWorksheet.getRow(28).getCell(1).toString(), inpCredWorksheet.getRow(28).getCell(2).getNumericCellValue(), inpCredWorksheet.getRow(28).getCell(3).getNumericCellValue(), inpCredWorksheet.getRow(28).getCell(4).getNumericCellValue(),  inpCredWorksheet.getRow(28).getCell(6).toString(),  inpCredWorksheet.getRow(28).getCell(7).toString(),  inpCredWorksheet.getRow(28).getCell(8).getNumericCellValue());
		createCreditorBankRow(manamTable, inpCredWorksheet.getRow(29).getCell(0).toString(), inpCredWorksheet.getRow(29).getCell(1).toString(), inpCredWorksheet.getRow(29).getCell(2).getNumericCellValue(), inpCredWorksheet.getRow(29).getCell(3).getNumericCellValue(), inpCredWorksheet.getRow(29).getCell(4).getNumericCellValue(),  inpCredWorksheet.getRow(29).getCell(6).toString(),  inpCredWorksheet.getRow(29).getCell(7).toString(),  inpCredWorksheet.getRow(29).getCell(8).getNumericCellValue());
		document.add(manamTable);

		//Total the MANAM
		PdfPTable manamTotalDataTbl = new PdfPTable(8);
		manamTotalDataTbl.setWidthPercentage(100);
		createTotalCreditorBankRow(manamTotalDataTbl, inpCredWorksheet.getRow(30).getCell(2).getNumericCellValue(),  inpCredWorksheet.getRow(30).getCell(3).getNumericCellValue(),  inpCredWorksheet.getRow(30).getCell(4).getNumericCellValue(),  inpCredWorksheet.getRow(30).getCell(8).getNumericCellValue());

		document.add(manamTotalDataTbl);
		document.add(nullTable);

		PdfPTable mancnTable = new PdfPTable(8);
		mancnTable.setWidthPercentage(100);

		//MANCN		
		createCreditorBankRow(mancnTable, inpCredWorksheet.getRow(32).getCell(0).toString(), inpCredWorksheet.getRow(32).getCell(1).toString(), inpCredWorksheet.getRow(32).getCell(2).getNumericCellValue(), inpCredWorksheet.getRow(32).getCell(3).getNumericCellValue(), inpCredWorksheet.getRow(32).getCell(4).getNumericCellValue(),  inpCredWorksheet.getRow(32).getCell(6).toString(),  inpCredWorksheet.getRow(32).getCell(7).toString(),  inpCredWorksheet.getRow(32).getCell(8).getNumericCellValue());
		createCreditorBankRow(mancnTable, inpCredWorksheet.getRow(33).getCell(0).toString(), inpCredWorksheet.getRow(33).getCell(1).toString(), inpCredWorksheet.getRow(33).getCell(2).getNumericCellValue(), inpCredWorksheet.getRow(33).getCell(3).getNumericCellValue(), inpCredWorksheet.getRow(33).getCell(4).getNumericCellValue(),  inpCredWorksheet.getRow(33).getCell(6).toString(),  inpCredWorksheet.getRow(33).getCell(7).toString(),  inpCredWorksheet.getRow(33).getCell(8).getNumericCellValue());
		createCreditorBankRow(mancnTable, inpCredWorksheet.getRow(34).getCell(0).toString(), inpCredWorksheet.getRow(34).getCell(1).toString(), inpCredWorksheet.getRow(34).getCell(2).getNumericCellValue(), inpCredWorksheet.getRow(34).getCell(3).getNumericCellValue(), inpCredWorksheet.getRow(34).getCell(4).getNumericCellValue(),  inpCredWorksheet.getRow(34).getCell(6).toString(),  inpCredWorksheet.getRow(34).getCell(7).toString(),  inpCredWorksheet.getRow(34).getCell(8).getNumericCellValue());
		createCreditorBankRow(mancnTable, inpCredWorksheet.getRow(35).getCell(0).toString(), inpCredWorksheet.getRow(35).getCell(1).toString(), inpCredWorksheet.getRow(35).getCell(2).getNumericCellValue(), inpCredWorksheet.getRow(35).getCell(3).getNumericCellValue(), inpCredWorksheet.getRow(35).getCell(4).getNumericCellValue(),  inpCredWorksheet.getRow(35).getCell(6).toString(),  inpCredWorksheet.getRow(35).getCell(7).toString(),  inpCredWorksheet.getRow(35).getCell(8).getNumericCellValue());
		createCreditorBankRow(mancnTable, inpCredWorksheet.getRow(36).getCell(0).toString(), inpCredWorksheet.getRow(36).getCell(1).toString(), inpCredWorksheet.getRow(36).getCell(2).getNumericCellValue(), inpCredWorksheet.getRow(36).getCell(3).getNumericCellValue(), inpCredWorksheet.getRow(36).getCell(4).getNumericCellValue(),  inpCredWorksheet.getRow(36).getCell(6).toString(),  inpCredWorksheet.getRow(36).getCell(7).toString(),  inpCredWorksheet.getRow(36).getCell(8).getNumericCellValue());
		createCreditorBankRow(mancnTable, inpCredWorksheet.getRow(37).getCell(0).toString(), inpCredWorksheet.getRow(37).getCell(1).toString(), inpCredWorksheet.getRow(37).getCell(2).getNumericCellValue(), inpCredWorksheet.getRow(37).getCell(3).getNumericCellValue(), inpCredWorksheet.getRow(37).getCell(4).getNumericCellValue(),  inpCredWorksheet.getRow(37).getCell(6).toString(),  inpCredWorksheet.getRow(37).getCell(7).toString(),  inpCredWorksheet.getRow(37).getCell(8).getNumericCellValue());
		createCreditorBankRow(mancnTable, inpCredWorksheet.getRow(38).getCell(0).toString(), inpCredWorksheet.getRow(38).getCell(1).toString(), inpCredWorksheet.getRow(38).getCell(2).getNumericCellValue(), inpCredWorksheet.getRow(38).getCell(3).getNumericCellValue(), inpCredWorksheet.getRow(38).getCell(4).getNumericCellValue(),  inpCredWorksheet.getRow(38).getCell(6).toString(),  inpCredWorksheet.getRow(38).getCell(7).toString(),  inpCredWorksheet.getRow(38).getCell(8).getNumericCellValue());
		createCreditorBankRow(mancnTable, inpCredWorksheet.getRow(39).getCell(0).toString(), inpCredWorksheet.getRow(39).getCell(1).toString(), inpCredWorksheet.getRow(39).getCell(2).getNumericCellValue(), inpCredWorksheet.getRow(39).getCell(3).getNumericCellValue(), inpCredWorksheet.getRow(39).getCell(4).getNumericCellValue(),  inpCredWorksheet.getRow(38).getCell(6).toString(),  inpCredWorksheet.getRow(38).getCell(7).toString(),  inpCredWorksheet.getRow(39).getCell(8).getNumericCellValue());
		createCreditorBankRow(mancnTable, inpCredWorksheet.getRow(40).getCell(0).toString(), inpCredWorksheet.getRow(40).getCell(1).toString(), inpCredWorksheet.getRow(40).getCell(2).getNumericCellValue(), inpCredWorksheet.getRow(40).getCell(3).getNumericCellValue(), inpCredWorksheet.getRow(40).getCell(4).getNumericCellValue(),  inpCredWorksheet.getRow(40).getCell(6).toString(),  inpCredWorksheet.getRow(40).getCell(7).toString(),  inpCredWorksheet.getRow(40).getCell(8).getNumericCellValue());
		createCreditorBankRow(mancnTable, inpCredWorksheet.getRow(41).getCell(0).toString(), inpCredWorksheet.getRow(41).getCell(1).toString(), inpCredWorksheet.getRow(41).getCell(2).getNumericCellValue(), inpCredWorksheet.getRow(41).getCell(3).getNumericCellValue(), inpCredWorksheet.getRow(41).getCell(4).getNumericCellValue(),  inpCredWorksheet.getRow(41).getCell(6).toString(),  inpCredWorksheet.getRow(41).getCell(7).toString(),  inpCredWorksheet.getRow(41).getCell(8).getNumericCellValue());
		createCreditorBankRow(mancnTable, inpCredWorksheet.getRow(42).getCell(0).toString(), inpCredWorksheet.getRow(42).getCell(1).toString(), inpCredWorksheet.getRow(42).getCell(2).getNumericCellValue(), inpCredWorksheet.getRow(42).getCell(3).getNumericCellValue(), inpCredWorksheet.getRow(42).getCell(4).getNumericCellValue(),  inpCredWorksheet.getRow(42).getCell(6).toString(),  inpCredWorksheet.getRow(42).getCell(7).toString(),  inpCredWorksheet.getRow(42).getCell(8).getNumericCellValue());
		document.add(mancnTable);

		//Total the MANCN
		PdfPTable mancnTotalDataTbl = new PdfPTable(8);
		mancnTotalDataTbl.setWidthPercentage(100);
		createTotalCreditorBankRow(mancnTotalDataTbl, inpCredWorksheet.getRow(43).getCell(2).getNumericCellValue(),  inpCredWorksheet.getRow(43).getCell(3).getNumericCellValue(),  inpCredWorksheet.getRow(43).getCell(4).getNumericCellValue(),  inpCredWorksheet.getRow(43).getCell(8).getNumericCellValue());

		document.add(mancnTotalDataTbl);
		document.add(nullTable);

		PdfPTable manriTable = new PdfPTable(8);
		manriTable.setWidthPercentage(100);

		//MANRI
		createCreditorBankRow(manriTable, inpCredWorksheet.getRow(45).getCell(0).toString(), inpCredWorksheet.getRow(45).getCell(1).toString(), inpCredWorksheet.getRow(45).getCell(2).getNumericCellValue(), inpCredWorksheet.getRow(45).getCell(3).getNumericCellValue(), inpCredWorksheet.getRow(45).getCell(4).getNumericCellValue(),  inpCredWorksheet.getRow(45).getCell(6).toString(),  inpCredWorksheet.getRow(45).getCell(7).toString(),  inpCredWorksheet.getRow(45).getCell(8).getNumericCellValue());
		createCreditorBankRow(manriTable, inpCredWorksheet.getRow(46).getCell(0).toString(), inpCredWorksheet.getRow(46).getCell(1).toString(), inpCredWorksheet.getRow(46).getCell(2).getNumericCellValue(), inpCredWorksheet.getRow(46).getCell(3).getNumericCellValue(), inpCredWorksheet.getRow(46).getCell(4).getNumericCellValue(),  inpCredWorksheet.getRow(46).getCell(6).toString(),  inpCredWorksheet.getRow(46).getCell(7).toString(),  inpCredWorksheet.getRow(46).getCell(8).getNumericCellValue());
		createCreditorBankRow(manriTable, inpCredWorksheet.getRow(47).getCell(0).toString(), inpCredWorksheet.getRow(47).getCell(1).toString(), inpCredWorksheet.getRow(47).getCell(2).getNumericCellValue(), inpCredWorksheet.getRow(47).getCell(3).getNumericCellValue(), inpCredWorksheet.getRow(47).getCell(4).getNumericCellValue(),  inpCredWorksheet.getRow(47).getCell(6).toString(),  inpCredWorksheet.getRow(47).getCell(7).toString(),  inpCredWorksheet.getRow(47).getCell(8).getNumericCellValue());
		createCreditorBankRow(manriTable, inpCredWorksheet.getRow(48).getCell(0).toString(), inpCredWorksheet.getRow(48).getCell(1).toString(), inpCredWorksheet.getRow(48).getCell(2).getNumericCellValue(), inpCredWorksheet.getRow(48).getCell(3).getNumericCellValue(), inpCredWorksheet.getRow(48).getCell(4).getNumericCellValue(),  inpCredWorksheet.getRow(48).getCell(6).toString(),  inpCredWorksheet.getRow(48).getCell(7).toString(),  inpCredWorksheet.getRow(48).getCell(8).getNumericCellValue());
		createCreditorBankRow(manriTable, inpCredWorksheet.getRow(49).getCell(0).toString(), inpCredWorksheet.getRow(49).getCell(1).toString(), inpCredWorksheet.getRow(49).getCell(2).getNumericCellValue(), inpCredWorksheet.getRow(49).getCell(3).getNumericCellValue(), inpCredWorksheet.getRow(49).getCell(4).getNumericCellValue(),  inpCredWorksheet.getRow(49).getCell(6).toString(),  inpCredWorksheet.getRow(49).getCell(7).toString(),  inpCredWorksheet.getRow(49).getCell(8).getNumericCellValue());
		createCreditorBankRow(manriTable, inpCredWorksheet.getRow(50).getCell(0).toString(), inpCredWorksheet.getRow(50).getCell(1).toString(), inpCredWorksheet.getRow(50).getCell(2).getNumericCellValue(), inpCredWorksheet.getRow(50).getCell(3).getNumericCellValue(), inpCredWorksheet.getRow(50).getCell(4).getNumericCellValue(),  inpCredWorksheet.getRow(50).getCell(6).toString(),  inpCredWorksheet.getRow(50).getCell(7).toString(),  inpCredWorksheet.getRow(50).getCell(8).getNumericCellValue());
		createCreditorBankRow(manriTable, inpCredWorksheet.getRow(51).getCell(0).toString(), inpCredWorksheet.getRow(51).getCell(1).toString(), inpCredWorksheet.getRow(51).getCell(2).getNumericCellValue(), inpCredWorksheet.getRow(51).getCell(3).getNumericCellValue(), inpCredWorksheet.getRow(51).getCell(4).getNumericCellValue(),  inpCredWorksheet.getRow(51).getCell(6).toString(),  inpCredWorksheet.getRow(51).getCell(7).toString(),  inpCredWorksheet.getRow(51).getCell(8).getNumericCellValue());
		createCreditorBankRow(manriTable, inpCredWorksheet.getRow(52).getCell(0).toString(), inpCredWorksheet.getRow(52).getCell(1).toString(), inpCredWorksheet.getRow(52).getCell(2).getNumericCellValue(), inpCredWorksheet.getRow(52).getCell(3).getNumericCellValue(), inpCredWorksheet.getRow(52).getCell(4).getNumericCellValue(),  inpCredWorksheet.getRow(52).getCell(6).toString(),  inpCredWorksheet.getRow(52).getCell(7).toString(),  inpCredWorksheet.getRow(52).getCell(8).getNumericCellValue());
		createCreditorBankRow(manriTable, inpCredWorksheet.getRow(53).getCell(0).toString(), inpCredWorksheet.getRow(53).getCell(1).toString(), inpCredWorksheet.getRow(53).getCell(2).getNumericCellValue(), inpCredWorksheet.getRow(53).getCell(3).getNumericCellValue(), inpCredWorksheet.getRow(53).getCell(4).getNumericCellValue(),  inpCredWorksheet.getRow(53).getCell(6).toString(),  inpCredWorksheet.getRow(53).getCell(7).toString(),  inpCredWorksheet.getRow(53).getCell(8).getNumericCellValue());
		createCreditorBankRow(manriTable, inpCredWorksheet.getRow(54).getCell(0).toString(), inpCredWorksheet.getRow(54).getCell(1).toString(), inpCredWorksheet.getRow(54).getCell(2).getNumericCellValue(), inpCredWorksheet.getRow(54).getCell(3).getNumericCellValue(), inpCredWorksheet.getRow(54).getCell(4).getNumericCellValue(),  inpCredWorksheet.getRow(54).getCell(6).toString(),  inpCredWorksheet.getRow(54).getCell(7).toString(),  inpCredWorksheet.getRow(54).getCell(8).getNumericCellValue());
		createCreditorBankRow(manriTable, inpCredWorksheet.getRow(55).getCell(0).toString(), inpCredWorksheet.getRow(55).getCell(1).toString(), inpCredWorksheet.getRow(55).getCell(2).getNumericCellValue(), inpCredWorksheet.getRow(55).getCell(3).getNumericCellValue(), inpCredWorksheet.getRow(55).getCell(4).getNumericCellValue(),  inpCredWorksheet.getRow(55).getCell(6).toString(),  inpCredWorksheet.getRow(55).getCell(7).toString(),  inpCredWorksheet.getRow(55).getCell(8).getNumericCellValue());
		document.add(manriTable);

		//Total the MANRI
		PdfPTable manriTotalDataTbl = new PdfPTable(8);
		manriTotalDataTbl.setWidthPercentage(100);
		createTotalCreditorBankRow(manriTotalDataTbl, inpCredWorksheet.getRow(56).getCell(2).getNumericCellValue(),  inpCredWorksheet.getRow(56).getCell(3).getNumericCellValue(),  inpCredWorksheet.getRow(56).getCell(4).getNumericCellValue(),  inpCredWorksheet.getRow(56).getCell(8).getNumericCellValue());

		document.add(manriTotalDataTbl);
		document.add(nullTable);

		//==============CREDITOR INFORMATION PAGE_2 ==================//
		//Move to next page
		document.newPage();

		document.add(nullTable);
		document.add(crdBankHdrTable);
		document.add(subHdrTable_1);

		PdfPTable srinpTable = new PdfPTable(8);
		srinpTable.setWidthPercentage(100);

		//SRINP
		createCreditorBankRow(srinpTable, inpCredWorksheet.getRow(58).getCell(0).toString(), inpCredWorksheet.getRow(58).getCell(1).toString(), inpCredWorksheet.getRow(58).getCell(2).getNumericCellValue(), inpCredWorksheet.getRow(58).getCell(3).getNumericCellValue(), inpCredWorksheet.getRow(58).getCell(4).getNumericCellValue(),  inpCredWorksheet.getRow(58).getCell(6).toString(),  inpCredWorksheet.getRow(58).getCell(7).toString(),  inpCredWorksheet.getRow(58).getCell(8).getNumericCellValue());
		createCreditorBankRow(srinpTable, inpCredWorksheet.getRow(59).getCell(0).toString(), inpCredWorksheet.getRow(59).getCell(1).toString(), inpCredWorksheet.getRow(59).getCell(2).getNumericCellValue(), inpCredWorksheet.getRow(59).getCell(3).getNumericCellValue(), inpCredWorksheet.getRow(59).getCell(4).getNumericCellValue(),  inpCredWorksheet.getRow(59).getCell(6).toString(),  inpCredWorksheet.getRow(59).getCell(7).toString(),  inpCredWorksheet.getRow(59).getCell(8).getNumericCellValue());
		createCreditorBankRow(srinpTable, inpCredWorksheet.getRow(60).getCell(0).toString(), inpCredWorksheet.getRow(60).getCell(1).toString(), inpCredWorksheet.getRow(60).getCell(2).getNumericCellValue(), inpCredWorksheet.getRow(60).getCell(3).getNumericCellValue(), inpCredWorksheet.getRow(60).getCell(4).getNumericCellValue(),  inpCredWorksheet.getRow(60).getCell(6).toString(),  inpCredWorksheet.getRow(60).getCell(7).toString(),  inpCredWorksheet.getRow(60).getCell(8).getNumericCellValue());
		createCreditorBankRow(srinpTable, inpCredWorksheet.getRow(61).getCell(0).toString(), inpCredWorksheet.getRow(61).getCell(1).toString(), inpCredWorksheet.getRow(61).getCell(2).getNumericCellValue(), inpCredWorksheet.getRow(61).getCell(3).getNumericCellValue(), inpCredWorksheet.getRow(61).getCell(4).getNumericCellValue(),  inpCredWorksheet.getRow(61).getCell(6).toString(),  inpCredWorksheet.getRow(61).getCell(7).toString(),  inpCredWorksheet.getRow(61).getCell(8).getNumericCellValue());
		createCreditorBankRow(srinpTable, inpCredWorksheet.getRow(62).getCell(0).toString(), inpCredWorksheet.getRow(62).getCell(1).toString(), inpCredWorksheet.getRow(62).getCell(2).getNumericCellValue(), inpCredWorksheet.getRow(62).getCell(3).getNumericCellValue(), inpCredWorksheet.getRow(62).getCell(4).getNumericCellValue(),  inpCredWorksheet.getRow(62).getCell(6).toString(),  inpCredWorksheet.getRow(62).getCell(7).toString(),  inpCredWorksheet.getRow(62).getCell(8).getNumericCellValue());
		createCreditorBankRow(srinpTable, inpCredWorksheet.getRow(63).getCell(0).toString(), inpCredWorksheet.getRow(63).getCell(1).toString(), inpCredWorksheet.getRow(63).getCell(2).getNumericCellValue(), inpCredWorksheet.getRow(63).getCell(3).getNumericCellValue(), inpCredWorksheet.getRow(63).getCell(4).getNumericCellValue(),  inpCredWorksheet.getRow(63).getCell(6).toString(),  inpCredWorksheet.getRow(63).getCell(7).toString(),  inpCredWorksheet.getRow(63).getCell(8).getNumericCellValue());
		createCreditorBankRow(srinpTable, inpCredWorksheet.getRow(64).getCell(0).toString(), inpCredWorksheet.getRow(64).getCell(1).toString(), inpCredWorksheet.getRow(64).getCell(2).getNumericCellValue(), inpCredWorksheet.getRow(64).getCell(3).getNumericCellValue(), inpCredWorksheet.getRow(64).getCell(4).getNumericCellValue(),  inpCredWorksheet.getRow(64).getCell(6).toString(),  inpCredWorksheet.getRow(64).getCell(7).toString(),  inpCredWorksheet.getRow(64).getCell(8).getNumericCellValue());
		createCreditorBankRow(srinpTable, inpCredWorksheet.getRow(65).getCell(0).toString(), inpCredWorksheet.getRow(65).getCell(1).toString(), inpCredWorksheet.getRow(65).getCell(2).getNumericCellValue(), inpCredWorksheet.getRow(65).getCell(3).getNumericCellValue(), inpCredWorksheet.getRow(65).getCell(4).getNumericCellValue(),  inpCredWorksheet.getRow(65).getCell(6).toString(),  inpCredWorksheet.getRow(65).getCell(7).toString(),  inpCredWorksheet.getRow(65).getCell(8).getNumericCellValue());
		createCreditorBankRow(srinpTable, inpCredWorksheet.getRow(66).getCell(0).toString(), inpCredWorksheet.getRow(66).getCell(1).toString(), inpCredWorksheet.getRow(66).getCell(2).getNumericCellValue(), inpCredWorksheet.getRow(66).getCell(3).getNumericCellValue(), inpCredWorksheet.getRow(66).getCell(4).getNumericCellValue(),  inpCredWorksheet.getRow(66).getCell(6).toString(),  inpCredWorksheet.getRow(66).getCell(7).toString(),  inpCredWorksheet.getRow(66).getCell(8).getNumericCellValue());
		createCreditorBankRow(srinpTable, inpCredWorksheet.getRow(67).getCell(0).toString(), inpCredWorksheet.getRow(67).getCell(1).toString(), inpCredWorksheet.getRow(67).getCell(2).getNumericCellValue(), inpCredWorksheet.getRow(67).getCell(3).getNumericCellValue(), inpCredWorksheet.getRow(67).getCell(4).getNumericCellValue(),  inpCredWorksheet.getRow(67).getCell(6).toString(),  inpCredWorksheet.getRow(67).getCell(7).toString(),  inpCredWorksheet.getRow(67).getCell(8).getNumericCellValue());
		createCreditorBankRow(srinpTable, inpCredWorksheet.getRow(68).getCell(0).toString(), inpCredWorksheet.getRow(68).getCell(1).toString(), inpCredWorksheet.getRow(68).getCell(2).getNumericCellValue(), inpCredWorksheet.getRow(68).getCell(3).getNumericCellValue(), inpCredWorksheet.getRow(68).getCell(4).getNumericCellValue(),  inpCredWorksheet.getRow(68).getCell(6).toString(),  inpCredWorksheet.getRow(68).getCell(7).toString(),  inpCredWorksheet.getRow(68).getCell(8).getNumericCellValue());
		document.add(srinpTable);

		//Total the SRINP
		PdfPTable srinpTotalDataTbl = new PdfPTable(8);
		srinpTotalDataTbl.setWidthPercentage(100);
		createTotalCreditorBankRow(srinpTotalDataTbl, inpCredWorksheet.getRow(69).getCell(2).getNumericCellValue(),  inpCredWorksheet.getRow(69).getCell(3).getNumericCellValue(),  inpCredWorksheet.getRow(69).getCell(4).getNumericCellValue(),  inpCredWorksheet.getRow(69).getCell(8).getNumericCellValue());

		document.add(srinpTotalDataTbl);
		document.add(nullTable);

		//GRAND TOTAL TABLE
		PdfPTable crBanksGrandTotalDataTbl = new PdfPTable(8);
		crBanksGrandTotalDataTbl.setWidthPercentage(100);

		createGrandTotalTables(crBanksGrandTotalDataTbl,  inpCredWorksheet.getRow(71).getCell(2).toString(), inpCredWorksheet.getRow(71).getCell(3).toString(), inpCredWorksheet.getRow(71).getCell(4).toString(), inpCredWorksheet.getRow(71).getCell(8).toString());
		document.add(crBanksGrandTotalDataTbl);

		//==============DEBTOR INFORMATION PAGE_1 ==================//
		//Move to next page
		document.newPage();

		document.add(nullTable);
		//Debtor Banks Information
		PdfPTable dbtrBankHdrTable = new PdfPTable(8);
		dbtrBankHdrTable.setWidthPercentage(100);

		PdfPCell subByDbtBanksCell = new PdfPCell(new Phrase("SUBMITTED BY DEBTOR BANKS",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		subByDbtBanksCell.setColspan(5);
		subByDbtBanksCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		subByDbtBanksCell.setBorder(com.itextpdf.text.Rectangle.BOX);
		subByDbtBanksCell.setBackgroundColor(BaseColor.CYAN);
		dbtrBankHdrTable.addCell(subByDbtBanksCell);

		PdfPCell extToCredBanksCell = new PdfPCell(new Phrase("EXTRACTED TO CREDITOR BANKS",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		extToCredBanksCell.setColspan(3);
		extToCredBanksCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		extToCredBanksCell.setBorder(com.itextpdf.text.Rectangle.BOX);
		extToCredBanksCell.setBackgroundColor(BaseColor.ORANGE);
		dbtrBankHdrTable.addCell(extToCredBanksCell);

		document.add(dbtrBankHdrTable);

		PdfPTable subHdrTable_2 = new PdfPTable(8);
		subHdrTable_2.setWidthPercentage(100);

		PdfPCell inpServCell_2 = new PdfPCell(new Phrase("SERVICE",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		inpServCell_2.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		inpServCell_2.setBorder(com.itextpdf.text.Rectangle.BOX);
		inpServCell_2.setBackgroundColor(BaseColor.LIGHT_GRAY);
		subHdrTable_2.addCell(inpServCell_2);

		PdfPCell bankCell_2 = new PdfPCell(new Phrase("BANKS",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		bankCell_2.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		bankCell_2.setBorder(com.itextpdf.text.Rectangle.BOX);
		bankCell_2.setBackgroundColor(BaseColor.LIGHT_GRAY);
		subHdrTable_2.addCell(bankCell_2);

		PdfPCell totNrofTxnsCell_2 = new PdfPCell(new Phrase("TOT NR OF TXNS",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		totNrofTxnsCell_2.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		totNrofTxnsCell_2.setBorder(com.itextpdf.text.Rectangle.BOX);
		totNrofTxnsCell_2.setBackgroundColor(BaseColor.LIGHT_GRAY);
		subHdrTable_2.addCell(totNrofTxnsCell_2);

		PdfPCell accpTxnsCell_2 = new PdfPCell(new Phrase("ACCEPTED TXNS",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		accpTxnsCell_2.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		accpTxnsCell_2.setBorder(com.itextpdf.text.Rectangle.BOX);
		accpTxnsCell_2.setBackgroundColor(BaseColor.LIGHT_GRAY);
		subHdrTable_2.addCell(accpTxnsCell_2);

		PdfPCell rejTxnsCell_2 = new PdfPCell(new Phrase("REJECTED TXNS",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		rejTxnsCell_2.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		rejTxnsCell_2.setBorder(com.itextpdf.text.Rectangle.BOX);
		rejTxnsCell_2.setBackgroundColor(BaseColor.LIGHT_GRAY);
		subHdrTable_2.addCell(rejTxnsCell_2);

		PdfPCell extServCell_2= new PdfPCell(new Phrase("SERVICE",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		extServCell_2.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		extServCell_2.setBorder(com.itextpdf.text.Rectangle.BOX);
		extServCell_2.setBackgroundColor(BaseColor.LIGHT_GRAY);
		subHdrTable_2.addCell(extServCell_2);

		PdfPCell extBankCell_2 = new PdfPCell(new Phrase("BANKS",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		extBankCell_2.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		extBankCell_2.setBorder(com.itextpdf.text.Rectangle.BOX);
		extBankCell_2.setBackgroundColor(BaseColor.LIGHT_GRAY);
		subHdrTable_2.addCell(extBankCell_2);

		PdfPCell extTxnsCell_2 = new PdfPCell(new Phrase("EXTRACT TXNS",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		extTxnsCell_2.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		extTxnsCell_2.setBorder(com.itextpdf.text.Rectangle.BOX);
		extTxnsCell_2.setBackgroundColor(BaseColor.LIGHT_GRAY);
		subHdrTable_2.addCell(extTxnsCell_2);

		document.add(subHdrTable_2);

		XSSFSheet inpDebtWorksheet = mr022xlsReport.getSheetAt(2); 

		//Creditor Banks Information
		PdfPTable dbtrDataTbl = new PdfPTable(8);
		dbtrDataTbl.setWidthPercentage(100);

		//ST101
		createDebtorBankRow(dbtrDataTbl, inpDebtWorksheet.getRow(6).getCell(0).toString(), inpDebtWorksheet.getRow(6).getCell(1).toString(), inpDebtWorksheet.getRow(6).getCell(2).getNumericCellValue(), inpDebtWorksheet.getRow(6).getCell(3).getNumericCellValue(), inpDebtWorksheet.getRow(6).getCell(4).getNumericCellValue(),  inpDebtWorksheet.getRow(6).getCell(6).toString(),  inpDebtWorksheet.getRow(6).getCell(7).toString(),  inpDebtWorksheet.getRow(6).getCell(8).getNumericCellValue());
		createDebtorBankRow(dbtrDataTbl, inpDebtWorksheet.getRow(7).getCell(0).toString(), inpDebtWorksheet.getRow(7).getCell(1).toString(), inpDebtWorksheet.getRow(7).getCell(2).getNumericCellValue(), inpDebtWorksheet.getRow(7).getCell(3).getNumericCellValue(), inpDebtWorksheet.getRow(7).getCell(4).getNumericCellValue(),  inpDebtWorksheet.getRow(7).getCell(6).toString(),  inpDebtWorksheet.getRow(7).getCell(7).toString(),  inpDebtWorksheet.getRow(7).getCell(8).getNumericCellValue());
		createDebtorBankRow(dbtrDataTbl, inpDebtWorksheet.getRow(8).getCell(0).toString(), inpDebtWorksheet.getRow(8).getCell(1).toString(), inpDebtWorksheet.getRow(8).getCell(2).getNumericCellValue(), inpDebtWorksheet.getRow(8).getCell(3).getNumericCellValue(), inpDebtWorksheet.getRow(8).getCell(4).getNumericCellValue(),  inpDebtWorksheet.getRow(8).getCell(6).toString(),  inpDebtWorksheet.getRow(8).getCell(7).toString(),  inpDebtWorksheet.getRow(8).getCell(8).getNumericCellValue());
		createDebtorBankRow(dbtrDataTbl, inpDebtWorksheet.getRow(9).getCell(0).toString(), inpDebtWorksheet.getRow(9).getCell(1).toString(), inpDebtWorksheet.getRow(9).getCell(2).getNumericCellValue(), inpDebtWorksheet.getRow(9).getCell(3).getNumericCellValue(), inpDebtWorksheet.getRow(9).getCell(4).getNumericCellValue(),  inpDebtWorksheet.getRow(9).getCell(6).toString(),  inpDebtWorksheet.getRow(9).getCell(7).toString(),  inpDebtWorksheet.getRow(9).getCell(8).getNumericCellValue());
		createDebtorBankRow(dbtrDataTbl, inpDebtWorksheet.getRow(10).getCell(0).toString(), inpDebtWorksheet.getRow(10).getCell(1).toString(), inpDebtWorksheet.getRow(10).getCell(2).getNumericCellValue(), inpDebtWorksheet.getRow(10).getCell(3).getNumericCellValue(), inpDebtWorksheet.getRow(10).getCell(4).getNumericCellValue(),  inpDebtWorksheet.getRow(10).getCell(6).toString(),  inpDebtWorksheet.getRow(10).getCell(7).toString(),  inpDebtWorksheet.getRow(10).getCell(8).getNumericCellValue());
		createDebtorBankRow(dbtrDataTbl, inpDebtWorksheet.getRow(11).getCell(0).toString(), inpDebtWorksheet.getRow(11).getCell(1).toString(), inpDebtWorksheet.getRow(11).getCell(2).getNumericCellValue(), inpDebtWorksheet.getRow(11).getCell(3).getNumericCellValue(), inpDebtWorksheet.getRow(11).getCell(4).getNumericCellValue(),  inpDebtWorksheet.getRow(11).getCell(6).toString(),  inpDebtWorksheet.getRow(11).getCell(7).toString(),  inpDebtWorksheet.getRow(11).getCell(8).getNumericCellValue());
		createDebtorBankRow(dbtrDataTbl, inpDebtWorksheet.getRow(12).getCell(0).toString(), inpDebtWorksheet.getRow(12).getCell(1).toString(), inpDebtWorksheet.getRow(12).getCell(2).getNumericCellValue(), inpDebtWorksheet.getRow(12).getCell(3).getNumericCellValue(), inpDebtWorksheet.getRow(12).getCell(4).getNumericCellValue(),  inpDebtWorksheet.getRow(12).getCell(6).toString(),  inpDebtWorksheet.getRow(12).getCell(7).toString(),  inpDebtWorksheet.getRow(12).getCell(8).getNumericCellValue());
		createDebtorBankRow(dbtrDataTbl, inpDebtWorksheet.getRow(13).getCell(0).toString(), inpDebtWorksheet.getRow(13).getCell(1).toString(), inpDebtWorksheet.getRow(13).getCell(2).getNumericCellValue(), inpDebtWorksheet.getRow(13).getCell(3).getNumericCellValue(), inpDebtWorksheet.getRow(13).getCell(4).getNumericCellValue(),  inpDebtWorksheet.getRow(13).getCell(6).toString(),  inpDebtWorksheet.getRow(13).getCell(7).toString(),  inpDebtWorksheet.getRow(13).getCell(8).getNumericCellValue());
		createDebtorBankRow(dbtrDataTbl, inpDebtWorksheet.getRow(14).getCell(0).toString(), inpDebtWorksheet.getRow(14).getCell(1).toString(), inpDebtWorksheet.getRow(14).getCell(2).getNumericCellValue(), inpDebtWorksheet.getRow(14).getCell(3).getNumericCellValue(), inpDebtWorksheet.getRow(14).getCell(4).getNumericCellValue(),  inpDebtWorksheet.getRow(14).getCell(6).toString(),  inpDebtWorksheet.getRow(14).getCell(7).toString(),  inpDebtWorksheet.getRow(14).getCell(8).getNumericCellValue());
		createDebtorBankRow(dbtrDataTbl, inpDebtWorksheet.getRow(15).getCell(0).toString(), inpDebtWorksheet.getRow(15).getCell(1).toString(), inpDebtWorksheet.getRow(15).getCell(2).getNumericCellValue(), inpDebtWorksheet.getRow(15).getCell(3).getNumericCellValue(), inpDebtWorksheet.getRow(15).getCell(4).getNumericCellValue(),  inpDebtWorksheet.getRow(15).getCell(6).toString(),  inpDebtWorksheet.getRow(15).getCell(7).toString(),  inpDebtWorksheet.getRow(15).getCell(8).getNumericCellValue());
		createDebtorBankRow(dbtrDataTbl, inpDebtWorksheet.getRow(16).getCell(0).toString(), inpDebtWorksheet.getRow(16).getCell(1).toString(), inpDebtWorksheet.getRow(16).getCell(2).getNumericCellValue(), inpDebtWorksheet.getRow(16).getCell(3).getNumericCellValue(), inpDebtWorksheet.getRow(16).getCell(4).getNumericCellValue(),  inpDebtWorksheet.getRow(16).getCell(6).toString(),  inpDebtWorksheet.getRow(16).getCell(7).toString(),  inpDebtWorksheet.getRow(16).getCell(8).getNumericCellValue());
		document.add(dbtrDataTbl);
		//Total the ST101
		//Creditor Banks Information
		PdfPTable dbtrTotalDataTbl = new PdfPTable(8);
		dbtrTotalDataTbl.setWidthPercentage(100);
		createTotalCreditorBankRow(dbtrTotalDataTbl, inpDebtWorksheet.getRow(17).getCell(2).getNumericCellValue(),  inpDebtWorksheet.getRow(17).getCell(3).getNumericCellValue(),  inpDebtWorksheet.getRow(17).getCell(4).getNumericCellValue(),  inpDebtWorksheet.getRow(17).getCell(8).getNumericCellValue());

		document.add(dbtrTotalDataTbl);
		document.add(nullTable);

		PdfPTable manacDataTbl = new PdfPTable(8);
		manacDataTbl.setWidthPercentage(100);

		//MANAC
		createDebtorBankRow(manacDataTbl, inpDebtWorksheet.getRow(19).getCell(0).toString(), inpDebtWorksheet.getRow(19).getCell(1).toString(), inpDebtWorksheet.getRow(19).getCell(2).getNumericCellValue(), inpDebtWorksheet.getRow(19).getCell(3).getNumericCellValue(), inpDebtWorksheet.getRow(19).getCell(4).getNumericCellValue(),  inpDebtWorksheet.getRow(19).getCell(6).toString(),  inpDebtWorksheet.getRow(19).getCell(7).toString(),  inpDebtWorksheet.getRow(19).getCell(8).getNumericCellValue());
		createDebtorBankRow(manacDataTbl, inpDebtWorksheet.getRow(20).getCell(0).toString(), inpDebtWorksheet.getRow(20).getCell(1).toString(), inpDebtWorksheet.getRow(20).getCell(2).getNumericCellValue(), inpDebtWorksheet.getRow(20).getCell(3).getNumericCellValue(), inpDebtWorksheet.getRow(20).getCell(4).getNumericCellValue(),  inpDebtWorksheet.getRow(20).getCell(6).toString(),  inpDebtWorksheet.getRow(20).getCell(7).toString(),  inpDebtWorksheet.getRow(20).getCell(8).getNumericCellValue());
		createDebtorBankRow(manacDataTbl, inpDebtWorksheet.getRow(21).getCell(0).toString(), inpDebtWorksheet.getRow(21).getCell(1).toString(), inpDebtWorksheet.getRow(21).getCell(2).getNumericCellValue(), inpDebtWorksheet.getRow(21).getCell(3).getNumericCellValue(), inpDebtWorksheet.getRow(21).getCell(4).getNumericCellValue(),  inpDebtWorksheet.getRow(21).getCell(6).toString(),  inpDebtWorksheet.getRow(21).getCell(7).toString(),  inpDebtWorksheet.getRow(21).getCell(8).getNumericCellValue());
		createDebtorBankRow(manacDataTbl, inpDebtWorksheet.getRow(22).getCell(0).toString(), inpDebtWorksheet.getRow(22).getCell(1).toString(), inpDebtWorksheet.getRow(22).getCell(2).getNumericCellValue(), inpDebtWorksheet.getRow(22).getCell(3).getNumericCellValue(), inpDebtWorksheet.getRow(22).getCell(4).getNumericCellValue(),  inpDebtWorksheet.getRow(22).getCell(6).toString(),  inpDebtWorksheet.getRow(22).getCell(7).toString(),  inpDebtWorksheet.getRow(22).getCell(8).getNumericCellValue());
		createDebtorBankRow(manacDataTbl, inpDebtWorksheet.getRow(23).getCell(0).toString(), inpDebtWorksheet.getRow(23).getCell(1).toString(), inpDebtWorksheet.getRow(23).getCell(2).getNumericCellValue(), inpDebtWorksheet.getRow(23).getCell(3).getNumericCellValue(), inpDebtWorksheet.getRow(23).getCell(4).getNumericCellValue(),  inpDebtWorksheet.getRow(23).getCell(6).toString(),  inpDebtWorksheet.getRow(23).getCell(7).toString(),  inpDebtWorksheet.getRow(23).getCell(8).getNumericCellValue());
		createDebtorBankRow(manacDataTbl, inpDebtWorksheet.getRow(24).getCell(0).toString(), inpDebtWorksheet.getRow(24).getCell(1).toString(), inpDebtWorksheet.getRow(24).getCell(2).getNumericCellValue(), inpDebtWorksheet.getRow(24).getCell(3).getNumericCellValue(), inpDebtWorksheet.getRow(24).getCell(4).getNumericCellValue(),  inpDebtWorksheet.getRow(24).getCell(6).toString(),  inpDebtWorksheet.getRow(24).getCell(7).toString(),  inpDebtWorksheet.getRow(24).getCell(8).getNumericCellValue());
		createDebtorBankRow(manacDataTbl, inpDebtWorksheet.getRow(25).getCell(0).toString(), inpDebtWorksheet.getRow(25).getCell(1).toString(), inpDebtWorksheet.getRow(25).getCell(2).getNumericCellValue(), inpDebtWorksheet.getRow(25).getCell(3).getNumericCellValue(), inpDebtWorksheet.getRow(25).getCell(4).getNumericCellValue(),  inpDebtWorksheet.getRow(25).getCell(6).toString(),  inpDebtWorksheet.getRow(25).getCell(7).toString(),  inpDebtWorksheet.getRow(25).getCell(8).getNumericCellValue());
		createDebtorBankRow(manacDataTbl, inpDebtWorksheet.getRow(26).getCell(0).toString(), inpDebtWorksheet.getRow(26).getCell(1).toString(), inpDebtWorksheet.getRow(26).getCell(2).getNumericCellValue(), inpDebtWorksheet.getRow(26).getCell(3).getNumericCellValue(), inpDebtWorksheet.getRow(26).getCell(4).getNumericCellValue(),  inpDebtWorksheet.getRow(26).getCell(6).toString(),  inpDebtWorksheet.getRow(26).getCell(7).toString(),  inpDebtWorksheet.getRow(26).getCell(8).getNumericCellValue());
		createDebtorBankRow(manacDataTbl, inpDebtWorksheet.getRow(27).getCell(0).toString(), inpDebtWorksheet.getRow(27).getCell(1).toString(), inpDebtWorksheet.getRow(27).getCell(2).getNumericCellValue(), inpDebtWorksheet.getRow(27).getCell(3).getNumericCellValue(), inpDebtWorksheet.getRow(27).getCell(4).getNumericCellValue(),  inpDebtWorksheet.getRow(27).getCell(6).toString(),  inpDebtWorksheet.getRow(27).getCell(7).toString(),  inpDebtWorksheet.getRow(27).getCell(8).getNumericCellValue());
		createDebtorBankRow(manacDataTbl, inpDebtWorksheet.getRow(28).getCell(0).toString(), inpDebtWorksheet.getRow(28).getCell(1).toString(), inpDebtWorksheet.getRow(28).getCell(2).getNumericCellValue(), inpDebtWorksheet.getRow(28).getCell(3).getNumericCellValue(), inpDebtWorksheet.getRow(28).getCell(4).getNumericCellValue(),  inpDebtWorksheet.getRow(28).getCell(6).toString(),  inpDebtWorksheet.getRow(28).getCell(7).toString(),  inpDebtWorksheet.getRow(28).getCell(8).getNumericCellValue());
		createDebtorBankRow(manacDataTbl, inpDebtWorksheet.getRow(29).getCell(0).toString(), inpDebtWorksheet.getRow(29).getCell(1).toString(), inpDebtWorksheet.getRow(29).getCell(2).getNumericCellValue(), inpDebtWorksheet.getRow(29).getCell(3).getNumericCellValue(), inpDebtWorksheet.getRow(29).getCell(4).getNumericCellValue(),  inpDebtWorksheet.getRow(29).getCell(6).toString(),  inpDebtWorksheet.getRow(29).getCell(7).toString(),  inpDebtWorksheet.getRow(29).getCell(8).getNumericCellValue());
		document.add(manacDataTbl);
		//Total the MANAC
		//Creditor Banks Information
		PdfPTable manacTotalDataTbl = new PdfPTable(8);
		manacTotalDataTbl.setWidthPercentage(100);
		createTotalCreditorBankRow(manacTotalDataTbl, inpDebtWorksheet.getRow(30).getCell(2).getNumericCellValue(),  inpDebtWorksheet.getRow(30).getCell(3).getNumericCellValue(),  inpDebtWorksheet.getRow(30).getCell(4).getNumericCellValue(),  inpDebtWorksheet.getRow(30).getCell(8).getNumericCellValue());

		document.add(manacTotalDataTbl);
		document.add(nullTable);

		PdfPTable manrtDataTbl = new PdfPTable(8);
		manrtDataTbl.setWidthPercentage(100);

		//MANRT		
		createDebtorBankRow(manrtDataTbl, inpDebtWorksheet.getRow(32).getCell(0).toString(), inpDebtWorksheet.getRow(32).getCell(1).toString(), inpDebtWorksheet.getRow(32).getCell(2).getNumericCellValue(), inpDebtWorksheet.getRow(32).getCell(3).getNumericCellValue(), inpDebtWorksheet.getRow(32).getCell(4).getNumericCellValue(),  inpDebtWorksheet.getRow(32).getCell(6).toString(),  inpDebtWorksheet.getRow(32).getCell(7).toString(),  inpDebtWorksheet.getRow(32).getCell(8).getNumericCellValue());
		createDebtorBankRow(manrtDataTbl, inpDebtWorksheet.getRow(33).getCell(0).toString(), inpDebtWorksheet.getRow(33).getCell(1).toString(), inpDebtWorksheet.getRow(33).getCell(2).getNumericCellValue(), inpDebtWorksheet.getRow(33).getCell(3).getNumericCellValue(), inpDebtWorksheet.getRow(33).getCell(4).getNumericCellValue(),  inpDebtWorksheet.getRow(33).getCell(6).toString(),  inpDebtWorksheet.getRow(33).getCell(7).toString(),  inpDebtWorksheet.getRow(33).getCell(8).getNumericCellValue());
		createDebtorBankRow(manrtDataTbl, inpDebtWorksheet.getRow(34).getCell(0).toString(), inpDebtWorksheet.getRow(34).getCell(1).toString(), inpDebtWorksheet.getRow(34).getCell(2).getNumericCellValue(), inpDebtWorksheet.getRow(34).getCell(3).getNumericCellValue(), inpDebtWorksheet.getRow(34).getCell(4).getNumericCellValue(),  inpDebtWorksheet.getRow(34).getCell(6).toString(),  inpDebtWorksheet.getRow(34).getCell(7).toString(),  inpDebtWorksheet.getRow(34).getCell(8).getNumericCellValue());
		createDebtorBankRow(manrtDataTbl, inpDebtWorksheet.getRow(35).getCell(0).toString(), inpDebtWorksheet.getRow(35).getCell(1).toString(), inpDebtWorksheet.getRow(35).getCell(2).getNumericCellValue(), inpDebtWorksheet.getRow(35).getCell(3).getNumericCellValue(), inpDebtWorksheet.getRow(35).getCell(4).getNumericCellValue(),  inpDebtWorksheet.getRow(35).getCell(6).toString(),  inpDebtWorksheet.getRow(35).getCell(7).toString(),  inpDebtWorksheet.getRow(35).getCell(8).getNumericCellValue());
		createDebtorBankRow(manrtDataTbl, inpDebtWorksheet.getRow(36).getCell(0).toString(), inpDebtWorksheet.getRow(36).getCell(1).toString(), inpDebtWorksheet.getRow(36).getCell(2).getNumericCellValue(), inpDebtWorksheet.getRow(36).getCell(3).getNumericCellValue(), inpDebtWorksheet.getRow(36).getCell(4).getNumericCellValue(),  inpDebtWorksheet.getRow(36).getCell(6).toString(),  inpDebtWorksheet.getRow(36).getCell(7).toString(),  inpDebtWorksheet.getRow(36).getCell(8).getNumericCellValue());
		createDebtorBankRow(manrtDataTbl, inpDebtWorksheet.getRow(37).getCell(0).toString(), inpDebtWorksheet.getRow(37).getCell(1).toString(), inpDebtWorksheet.getRow(37).getCell(2).getNumericCellValue(), inpDebtWorksheet.getRow(37).getCell(3).getNumericCellValue(), inpDebtWorksheet.getRow(37).getCell(4).getNumericCellValue(),  inpDebtWorksheet.getRow(37).getCell(6).toString(),  inpDebtWorksheet.getRow(37).getCell(7).toString(),  inpDebtWorksheet.getRow(37).getCell(8).getNumericCellValue());
		createDebtorBankRow(manrtDataTbl, inpDebtWorksheet.getRow(38).getCell(0).toString(), inpDebtWorksheet.getRow(38).getCell(1).toString(), inpDebtWorksheet.getRow(38).getCell(2).getNumericCellValue(), inpDebtWorksheet.getRow(38).getCell(3).getNumericCellValue(), inpDebtWorksheet.getRow(38).getCell(4).getNumericCellValue(),  inpDebtWorksheet.getRow(38).getCell(6).toString(),  inpDebtWorksheet.getRow(38).getCell(7).toString(),  inpDebtWorksheet.getRow(38).getCell(8).getNumericCellValue());
		createDebtorBankRow(manrtDataTbl, inpDebtWorksheet.getRow(39).getCell(0).toString(), inpDebtWorksheet.getRow(39).getCell(1).toString(), inpDebtWorksheet.getRow(39).getCell(2).getNumericCellValue(), inpDebtWorksheet.getRow(39).getCell(3).getNumericCellValue(), inpDebtWorksheet.getRow(39).getCell(4).getNumericCellValue(),  inpDebtWorksheet.getRow(39).getCell(6).toString(),  inpDebtWorksheet.getRow(39).getCell(7).toString(),  inpDebtWorksheet.getRow(39).getCell(8).getNumericCellValue());
		createDebtorBankRow(manrtDataTbl, inpDebtWorksheet.getRow(40).getCell(0).toString(), inpDebtWorksheet.getRow(40).getCell(1).toString(), inpDebtWorksheet.getRow(40).getCell(2).getNumericCellValue(), inpDebtWorksheet.getRow(40).getCell(3).getNumericCellValue(), inpDebtWorksheet.getRow(40).getCell(4).getNumericCellValue(),  inpDebtWorksheet.getRow(40).getCell(6).toString(),  inpDebtWorksheet.getRow(40).getCell(7).toString(),  inpDebtWorksheet.getRow(40).getCell(8).getNumericCellValue());
		createDebtorBankRow(manrtDataTbl, inpDebtWorksheet.getRow(41).getCell(0).toString(), inpDebtWorksheet.getRow(41).getCell(1).toString(), inpDebtWorksheet.getRow(41).getCell(2).getNumericCellValue(), inpDebtWorksheet.getRow(41).getCell(3).getNumericCellValue(), inpDebtWorksheet.getRow(41).getCell(4).getNumericCellValue(),  inpDebtWorksheet.getRow(41).getCell(6).toString(),  inpDebtWorksheet.getRow(41).getCell(7).toString(),  inpDebtWorksheet.getRow(41).getCell(8).getNumericCellValue());
		createDebtorBankRow(manrtDataTbl, inpDebtWorksheet.getRow(42).getCell(0).toString(), inpDebtWorksheet.getRow(42).getCell(1).toString(), inpDebtWorksheet.getRow(42).getCell(2).getNumericCellValue(), inpDebtWorksheet.getRow(42).getCell(3).getNumericCellValue(), inpDebtWorksheet.getRow(42).getCell(4).getNumericCellValue(),  inpDebtWorksheet.getRow(42).getCell(6).toString(),  inpDebtWorksheet.getRow(42).getCell(7).toString(),  inpDebtWorksheet.getRow(42).getCell(8).getNumericCellValue());		
		document.add(manrtDataTbl);
		//Total the MANRT
		//Creditor Banks Information
		PdfPTable manrtTotalDataTbl = new PdfPTable(8);
		manrtTotalDataTbl.setWidthPercentage(100);
		createTotalCreditorBankRow(manrtTotalDataTbl, inpDebtWorksheet.getRow(43).getCell(2).getNumericCellValue(),  inpDebtWorksheet.getRow(43).getCell(3).getNumericCellValue(),  inpDebtWorksheet.getRow(43).getCell(4).getNumericCellValue(),  inpDebtWorksheet.getRow(43).getCell(8).getNumericCellValue());

		document.add(manrtTotalDataTbl);
		document.add(nullTable);

		PdfPTable spinpDataTbl = new PdfPTable(8);
		spinpDataTbl.setWidthPercentage(100);

		//SPINP
		createDebtorBankRow(spinpDataTbl, inpDebtWorksheet.getRow(45).getCell(0).toString(), inpDebtWorksheet.getRow(45).getCell(1).toString(), inpDebtWorksheet.getRow(45).getCell(2).getNumericCellValue(), inpDebtWorksheet.getRow(45).getCell(3).getNumericCellValue(), inpDebtWorksheet.getRow(45).getCell(4).getNumericCellValue(),  inpDebtWorksheet.getRow(45).getCell(6).toString(),  inpDebtWorksheet.getRow(45).getCell(7).toString(),  inpDebtWorksheet.getRow(45).getCell(8).getNumericCellValue());
		createDebtorBankRow(spinpDataTbl, inpDebtWorksheet.getRow(46).getCell(0).toString(), inpDebtWorksheet.getRow(46).getCell(1).toString(), inpDebtWorksheet.getRow(46).getCell(2).getNumericCellValue(), inpDebtWorksheet.getRow(46).getCell(3).getNumericCellValue(), inpDebtWorksheet.getRow(46).getCell(4).getNumericCellValue(),  inpDebtWorksheet.getRow(46).getCell(6).toString(),  inpDebtWorksheet.getRow(46).getCell(7).toString(),  inpDebtWorksheet.getRow(46).getCell(8).getNumericCellValue());
		createDebtorBankRow(spinpDataTbl, inpDebtWorksheet.getRow(47).getCell(0).toString(), inpDebtWorksheet.getRow(47).getCell(1).toString(), inpDebtWorksheet.getRow(47).getCell(2).getNumericCellValue(), inpDebtWorksheet.getRow(47).getCell(3).getNumericCellValue(), inpDebtWorksheet.getRow(47).getCell(4).getNumericCellValue(),  inpDebtWorksheet.getRow(47).getCell(6).toString(),  inpDebtWorksheet.getRow(47).getCell(7).toString(),  inpDebtWorksheet.getRow(47).getCell(8).getNumericCellValue());
		createDebtorBankRow(spinpDataTbl, inpDebtWorksheet.getRow(48).getCell(0).toString(), inpDebtWorksheet.getRow(48).getCell(1).toString(), inpDebtWorksheet.getRow(48).getCell(2).getNumericCellValue(), inpDebtWorksheet.getRow(48).getCell(3).getNumericCellValue(), inpDebtWorksheet.getRow(48).getCell(4).getNumericCellValue(),  inpDebtWorksheet.getRow(48).getCell(6).toString(),  inpDebtWorksheet.getRow(48).getCell(7).toString(),  inpDebtWorksheet.getRow(48).getCell(8).getNumericCellValue());
		createDebtorBankRow(spinpDataTbl, inpDebtWorksheet.getRow(49).getCell(0).toString(), inpDebtWorksheet.getRow(49).getCell(1).toString(), inpDebtWorksheet.getRow(49).getCell(2).getNumericCellValue(), inpDebtWorksheet.getRow(49).getCell(3).getNumericCellValue(), inpDebtWorksheet.getRow(49).getCell(4).getNumericCellValue(),  inpDebtWorksheet.getRow(49).getCell(6).toString(),  inpDebtWorksheet.getRow(49).getCell(7).toString(),  inpDebtWorksheet.getRow(49).getCell(8).getNumericCellValue());
		createDebtorBankRow(spinpDataTbl, inpDebtWorksheet.getRow(50).getCell(0).toString(), inpDebtWorksheet.getRow(50).getCell(1).toString(), inpDebtWorksheet.getRow(50).getCell(2).getNumericCellValue(), inpDebtWorksheet.getRow(50).getCell(3).getNumericCellValue(), inpDebtWorksheet.getRow(50).getCell(4).getNumericCellValue(),  inpDebtWorksheet.getRow(50).getCell(6).toString(),  inpDebtWorksheet.getRow(50).getCell(7).toString(),  inpDebtWorksheet.getRow(50).getCell(8).getNumericCellValue());
		createDebtorBankRow(spinpDataTbl, inpDebtWorksheet.getRow(51).getCell(0).toString(), inpDebtWorksheet.getRow(51).getCell(1).toString(), inpDebtWorksheet.getRow(51).getCell(2).getNumericCellValue(), inpDebtWorksheet.getRow(51).getCell(3).getNumericCellValue(), inpDebtWorksheet.getRow(51).getCell(4).getNumericCellValue(),  inpDebtWorksheet.getRow(51).getCell(6).toString(),  inpDebtWorksheet.getRow(51).getCell(7).toString(),  inpDebtWorksheet.getRow(51).getCell(8).getNumericCellValue());
		createDebtorBankRow(spinpDataTbl, inpDebtWorksheet.getRow(52).getCell(0).toString(), inpDebtWorksheet.getRow(52).getCell(1).toString(), inpDebtWorksheet.getRow(52).getCell(2).getNumericCellValue(), inpDebtWorksheet.getRow(52).getCell(3).getNumericCellValue(), inpDebtWorksheet.getRow(52).getCell(4).getNumericCellValue(),  inpDebtWorksheet.getRow(52).getCell(6).toString(),  inpDebtWorksheet.getRow(52).getCell(7).toString(),  inpDebtWorksheet.getRow(52).getCell(8).getNumericCellValue());
		createDebtorBankRow(spinpDataTbl, inpDebtWorksheet.getRow(53).getCell(0).toString(), inpDebtWorksheet.getRow(53).getCell(1).toString(), inpDebtWorksheet.getRow(53).getCell(2).getNumericCellValue(), inpDebtWorksheet.getRow(53).getCell(3).getNumericCellValue(), inpDebtWorksheet.getRow(53).getCell(4).getNumericCellValue(),  inpDebtWorksheet.getRow(53).getCell(6).toString(),  inpDebtWorksheet.getRow(53).getCell(7).toString(),  inpDebtWorksheet.getRow(53).getCell(8).getNumericCellValue());
		createDebtorBankRow(spinpDataTbl, inpDebtWorksheet.getRow(54).getCell(0).toString(), inpDebtWorksheet.getRow(54).getCell(1).toString(), inpDebtWorksheet.getRow(54).getCell(2).getNumericCellValue(), inpDebtWorksheet.getRow(54).getCell(3).getNumericCellValue(), inpDebtWorksheet.getRow(54).getCell(4).getNumericCellValue(),  inpDebtWorksheet.getRow(54).getCell(6).toString(),  inpDebtWorksheet.getRow(54).getCell(7).toString(),  inpDebtWorksheet.getRow(54).getCell(8).getNumericCellValue());
		createDebtorBankRow(spinpDataTbl, inpDebtWorksheet.getRow(55).getCell(0).toString(), inpDebtWorksheet.getRow(55).getCell(1).toString(), inpDebtWorksheet.getRow(55).getCell(2).getNumericCellValue(), inpDebtWorksheet.getRow(55).getCell(3).getNumericCellValue(), inpDebtWorksheet.getRow(55).getCell(4).getNumericCellValue(),  inpDebtWorksheet.getRow(55).getCell(6).toString(),  inpDebtWorksheet.getRow(55).getCell(7).toString(),  inpDebtWorksheet.getRow(55).getCell(8).getNumericCellValue());

		document.add(spinpDataTbl);
		//Total the SPINP
		//Creditor Banks Information
		PdfPTable spinpTotalDataTbl = new PdfPTable(8);
		spinpTotalDataTbl.setWidthPercentage(100);
		createTotalCreditorBankRow(spinpTotalDataTbl, inpDebtWorksheet.getRow(56).getCell(2).getNumericCellValue(),  inpDebtWorksheet.getRow(56).getCell(3).getNumericCellValue(),  inpDebtWorksheet.getRow(56).getCell(4).getNumericCellValue(),  inpDebtWorksheet.getRow(56).getCell(8).getNumericCellValue());

		document.add(spinpTotalDataTbl);
		document.add(nullTable);

		//GRAND TOTAL TABLE
		PdfPTable drBanksGrandTotalDataTbl = new PdfPTable(8);
		drBanksGrandTotalDataTbl.setWidthPercentage(100);

		createGrandTotalTables(drBanksGrandTotalDataTbl,  inpDebtWorksheet.getRow(58).getCell(2).toString(), inpDebtWorksheet.getRow(58).getCell(3).toString(), inpDebtWorksheet.getRow(58).getCell(4).toString(), inpDebtWorksheet.getRow(58).getCell(8).toString());
		document.add(drBanksGrandTotalDataTbl);

		//==============STATUS REPORTS PAGE==================//
		//Move to next page
		document.newPage();

		document.add(nullTable);

		// StsHdrInfo
		PdfPTable subHdrTable_3 = new PdfPTable(6);
		subHdrTable_3.setWidthPercentage(100);

		PdfPCell inpServCell_3 = new PdfPCell(new Phrase("SERVICE",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		inpServCell_3.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		inpServCell_3.setBorder(com.itextpdf.text.Rectangle.BOX);
		inpServCell_3.setBackgroundColor(BaseColor.LIGHT_GRAY);
		subHdrTable_3.addCell(inpServCell_3);

		PdfPCell bankCell_3 = new PdfPCell(new Phrase("BANKS",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		bankCell_3.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		bankCell_3.setBorder(com.itextpdf.text.Rectangle.BOX);
		bankCell_3.setBackgroundColor(BaseColor.LIGHT_GRAY);
		subHdrTable_3.addCell(bankCell_3);

		PdfPCell totNrofTxnsCell_3 = new PdfPCell(new Phrase("TOT NR OF STATUS RPTS",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		totNrofTxnsCell_3.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		totNrofTxnsCell_3.setBorder(com.itextpdf.text.Rectangle.BOX);
		totNrofTxnsCell_3.setBackgroundColor(BaseColor.LIGHT_GRAY);
		subHdrTable_3.addCell(totNrofTxnsCell_3);

		PdfPCell debtInpServCell_3 = new PdfPCell(new Phrase("SERVICE",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		debtInpServCell_3.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		debtInpServCell_3.setBorder(com.itextpdf.text.Rectangle.BOX);
		debtInpServCell_3.setBackgroundColor(BaseColor.LIGHT_GRAY);
		subHdrTable_3.addCell(debtInpServCell_3);

		PdfPCell debtBanks_3 = new PdfPCell(new Phrase("BANKS",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		debtBanks_3.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		debtBanks_3.setBorder(com.itextpdf.text.Rectangle.BOX);
		debtBanks_3.setBackgroundColor(BaseColor.LIGHT_GRAY);
		subHdrTable_3.addCell(debtBanks_3);

		PdfPCell totNrofTxnsCell_4 = new PdfPCell(new Phrase("TOT NR OF STATUS RPTS",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		totNrofTxnsCell_4.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		totNrofTxnsCell_4.setBorder(com.itextpdf.text.Rectangle.BOX);
		totNrofTxnsCell_4.setBackgroundColor(BaseColor.LIGHT_GRAY);
		subHdrTable_3.addCell(totNrofTxnsCell_4);

		document.add(subHdrTable_3);

		//SubHdr Banks Info
		PdfPTable subHdrBankTable = new PdfPTable(6);
		subHdrBankTable.setWidthPercentage(100);

		PdfPCell cdtBanksCell = new PdfPCell(new Phrase("CREDITOR BANKS",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		cdtBanksCell.setColspan(3);
		cdtBanksCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		cdtBanksCell.setBorder(com.itextpdf.text.Rectangle.BOX);
		cdtBanksCell.setBackgroundColor(BaseColor.CYAN);
		subHdrBankTable.addCell(cdtBanksCell);

		PdfPCell debtBanksCell = new PdfPCell(new Phrase("DEBTOR BANKS",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		debtBanksCell.setColspan(3);
		debtBanksCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		debtBanksCell.setBorder(com.itextpdf.text.Rectangle.BOX);
		debtBanksCell.setBackgroundColor(BaseColor.ORANGE);
		subHdrBankTable.addCell(debtBanksCell);

		document.add(subHdrBankTable);

		XSSFSheet stsRepWorksheet = mr022xlsReport.getSheetAt(3); 

		//Creditor Banks Information
		PdfPTable st100DataTable = new PdfPTable(6);
		st100DataTable.setWidthPercentage(100);

		//ST100
		createStatusReportRow(false, st100DataTable, stsRepWorksheet.getRow(6).getCell(0).toString(), stsRepWorksheet.getRow(6).getCell(1).toString(), stsRepWorksheet.getRow(6).getCell(2).getNumericCellValue(), stsRepWorksheet.getRow(6).getCell(4).toString(), stsRepWorksheet.getRow(6).getCell(5).toString(),  stsRepWorksheet.getRow(6).getCell(6).getNumericCellValue());
		createStatusReportRow(false, st100DataTable, stsRepWorksheet.getRow(7).getCell(0).toString(), stsRepWorksheet.getRow(7).getCell(1).toString(), stsRepWorksheet.getRow(7).getCell(2).getNumericCellValue(), stsRepWorksheet.getRow(7).getCell(4).toString(), stsRepWorksheet.getRow(7).getCell(5).toString(),  stsRepWorksheet.getRow(7).getCell(6).getNumericCellValue());
		createStatusReportRow(false, st100DataTable, stsRepWorksheet.getRow(8).getCell(0).toString(), stsRepWorksheet.getRow(8).getCell(1).toString(), stsRepWorksheet.getRow(8).getCell(2).getNumericCellValue(), stsRepWorksheet.getRow(8).getCell(4).toString(), stsRepWorksheet.getRow(8).getCell(5).toString(),  stsRepWorksheet.getRow(8).getCell(6).getNumericCellValue());
		createStatusReportRow(false, st100DataTable, stsRepWorksheet.getRow(9).getCell(0).toString(), stsRepWorksheet.getRow(9).getCell(1).toString(), stsRepWorksheet.getRow(9).getCell(2).getNumericCellValue(), stsRepWorksheet.getRow(9).getCell(4).toString(), stsRepWorksheet.getRow(9).getCell(5).toString(),  stsRepWorksheet.getRow(9).getCell(6).getNumericCellValue());
		createStatusReportRow(false, st100DataTable, stsRepWorksheet.getRow(10).getCell(0).toString(), stsRepWorksheet.getRow(10).getCell(1).toString(), stsRepWorksheet.getRow(10).getCell(2).getNumericCellValue(), stsRepWorksheet.getRow(10).getCell(4).toString(), stsRepWorksheet.getRow(10).getCell(5).toString(),  stsRepWorksheet.getRow(10).getCell(6).getNumericCellValue());
		createStatusReportRow(false, st100DataTable, stsRepWorksheet.getRow(11).getCell(0).toString(), stsRepWorksheet.getRow(11).getCell(1).toString(), stsRepWorksheet.getRow(11).getCell(2).getNumericCellValue(), stsRepWorksheet.getRow(11).getCell(4).toString(), stsRepWorksheet.getRow(11).getCell(5).toString(),  stsRepWorksheet.getRow(11).getCell(6).getNumericCellValue());
		createStatusReportRow(false, st100DataTable, stsRepWorksheet.getRow(12).getCell(0).toString(), stsRepWorksheet.getRow(12).getCell(1).toString(), stsRepWorksheet.getRow(12).getCell(2).getNumericCellValue(), stsRepWorksheet.getRow(12).getCell(4).toString(), stsRepWorksheet.getRow(12).getCell(5).toString(),  stsRepWorksheet.getRow(12).getCell(6).getNumericCellValue());
		createStatusReportRow(false, st100DataTable, stsRepWorksheet.getRow(13).getCell(0).toString(), stsRepWorksheet.getRow(13).getCell(1).toString(), stsRepWorksheet.getRow(13).getCell(2).getNumericCellValue(), stsRepWorksheet.getRow(13).getCell(4).toString(), stsRepWorksheet.getRow(13).getCell(5).toString(),  stsRepWorksheet.getRow(13).getCell(6).getNumericCellValue());
		createStatusReportRow(false, st100DataTable, stsRepWorksheet.getRow(14).getCell(0).toString(), stsRepWorksheet.getRow(14).getCell(1).toString(), stsRepWorksheet.getRow(14).getCell(2).getNumericCellValue(), stsRepWorksheet.getRow(14).getCell(4).toString(), stsRepWorksheet.getRow(14).getCell(5).toString(),  stsRepWorksheet.getRow(14).getCell(6).getNumericCellValue());
		createStatusReportRow(false, st100DataTable, stsRepWorksheet.getRow(15).getCell(0).toString(), stsRepWorksheet.getRow(15).getCell(1).toString(), stsRepWorksheet.getRow(15).getCell(2).getNumericCellValue(), null, null,  0);

		document.add(st100DataTable);
		//Total the ST100
		//Creditor Banks Information
		PdfPTable st100TotalDataTbl = new PdfPTable(6);
		st100TotalDataTbl.setWidthPercentage(100);
		createTotalStatusReportRow(false, st100TotalDataTbl,  stsRepWorksheet.getRow(16).getCell(2).getNumericCellValue(),  stsRepWorksheet.getRow(15).getCell(6).getNumericCellValue());

		document.add(st100TotalDataTbl);
		document.add(nullTable);

		PdfPTable st105DataTable = new PdfPTable(6);
		st105DataTable.setWidthPercentage(100);

		//ST105
		createStatusReportRow(false, st105DataTable, stsRepWorksheet.getRow(18).getCell(0).toString(), stsRepWorksheet.getRow(18).getCell(1).toString(), stsRepWorksheet.getRow(18).getCell(2).getNumericCellValue(), stsRepWorksheet.getRow(18).getCell(4).toString(), stsRepWorksheet.getRow(18).getCell(5).toString(),  stsRepWorksheet.getRow(18).getCell(6).getNumericCellValue());
		createStatusReportRow(false, st105DataTable, stsRepWorksheet.getRow(19).getCell(0).toString(), stsRepWorksheet.getRow(19).getCell(1).toString(), stsRepWorksheet.getRow(19).getCell(2).getNumericCellValue(), stsRepWorksheet.getRow(19).getCell(4).toString(), stsRepWorksheet.getRow(19).getCell(5).toString(),  stsRepWorksheet.getRow(19).getCell(6).getNumericCellValue());
		createStatusReportRow(false, st105DataTable, stsRepWorksheet.getRow(20).getCell(0).toString(), stsRepWorksheet.getRow(20).getCell(1).toString(), stsRepWorksheet.getRow(20).getCell(2).getNumericCellValue(), stsRepWorksheet.getRow(20).getCell(4).toString(), stsRepWorksheet.getRow(20).getCell(5).toString(),  stsRepWorksheet.getRow(20).getCell(6).getNumericCellValue());
		createStatusReportRow(false, st105DataTable, stsRepWorksheet.getRow(21).getCell(0).toString(), stsRepWorksheet.getRow(21).getCell(1).toString(), stsRepWorksheet.getRow(21).getCell(2).getNumericCellValue(), stsRepWorksheet.getRow(21).getCell(4).toString(), stsRepWorksheet.getRow(21).getCell(5).toString(),  stsRepWorksheet.getRow(21).getCell(6).getNumericCellValue());
		createStatusReportRow(false, st105DataTable, stsRepWorksheet.getRow(22).getCell(0).toString(), stsRepWorksheet.getRow(22).getCell(1).toString(), stsRepWorksheet.getRow(22).getCell(2).getNumericCellValue(), stsRepWorksheet.getRow(22).getCell(4).toString(), stsRepWorksheet.getRow(22).getCell(5).toString(),  stsRepWorksheet.getRow(22).getCell(6).getNumericCellValue());
		createStatusReportRow(false, st105DataTable, stsRepWorksheet.getRow(23).getCell(0).toString(), stsRepWorksheet.getRow(23).getCell(1).toString(), stsRepWorksheet.getRow(23).getCell(2).getNumericCellValue(), stsRepWorksheet.getRow(23).getCell(4).toString(), stsRepWorksheet.getRow(23).getCell(5).toString(),  stsRepWorksheet.getRow(23).getCell(6).getNumericCellValue());
		createStatusReportRow(false, st105DataTable, stsRepWorksheet.getRow(24).getCell(0).toString(), stsRepWorksheet.getRow(24).getCell(1).toString(), stsRepWorksheet.getRow(24).getCell(2).getNumericCellValue(), stsRepWorksheet.getRow(24).getCell(4).toString(), stsRepWorksheet.getRow(24).getCell(5).toString(),  stsRepWorksheet.getRow(24).getCell(6).getNumericCellValue());
		createStatusReportRow(false, st105DataTable, stsRepWorksheet.getRow(25).getCell(0).toString(), stsRepWorksheet.getRow(25).getCell(1).toString(), stsRepWorksheet.getRow(25).getCell(2).getNumericCellValue(), stsRepWorksheet.getRow(25).getCell(4).toString(), stsRepWorksheet.getRow(25).getCell(5).toString(),  stsRepWorksheet.getRow(25).getCell(6).getNumericCellValue());
		createStatusReportRow(false, st105DataTable, stsRepWorksheet.getRow(26).getCell(0).toString(), stsRepWorksheet.getRow(26).getCell(1).toString(), stsRepWorksheet.getRow(26).getCell(2).getNumericCellValue(), stsRepWorksheet.getRow(26).getCell(4).toString(), stsRepWorksheet.getRow(26).getCell(5).toString(),  stsRepWorksheet.getRow(26).getCell(6).getNumericCellValue());
		createStatusReportRow(false, st105DataTable, stsRepWorksheet.getRow(27).getCell(0).toString(), stsRepWorksheet.getRow(27).getCell(1).toString(), stsRepWorksheet.getRow(27).getCell(2).getNumericCellValue(), null, null,  0);
		document.add(st105DataTable);
		//Total the ST105
		//Creditor Banks Information
		PdfPTable st105TotalDataTbl = new PdfPTable(6);
		st105TotalDataTbl.setWidthPercentage(100);
		createTotalStatusReportRow(false, st105TotalDataTbl,  stsRepWorksheet.getRow(28).getCell(2).getNumericCellValue(),  stsRepWorksheet.getRow(27).getCell(6).getNumericCellValue());

		document.add(st105TotalDataTbl);
		document.add(nullTable);

		PdfPTable st008DataTable = new PdfPTable(6);
		st008DataTable.setWidthPercentage(100);

		//ST008
		createStatusReportRow(false, st008DataTable, stsRepWorksheet.getRow(30).getCell(0).toString(), stsRepWorksheet.getRow(30).getCell(1).toString(), stsRepWorksheet.getRow(30).getCell(2).getNumericCellValue(), stsRepWorksheet.getRow(30).getCell(4).toString(), stsRepWorksheet.getRow(30).getCell(5).toString(),  stsRepWorksheet.getRow(30).getCell(6).getNumericCellValue());
		createStatusReportRow(false, st008DataTable, stsRepWorksheet.getRow(31).getCell(0).toString(), stsRepWorksheet.getRow(31).getCell(1).toString(), stsRepWorksheet.getRow(31).getCell(2).getNumericCellValue(), stsRepWorksheet.getRow(31).getCell(4).toString(), stsRepWorksheet.getRow(31).getCell(5).toString(),  stsRepWorksheet.getRow(31).getCell(6).getNumericCellValue());
		createStatusReportRow(false, st008DataTable, stsRepWorksheet.getRow(32).getCell(0).toString(), stsRepWorksheet.getRow(32).getCell(1).toString(), stsRepWorksheet.getRow(32).getCell(2).getNumericCellValue(), stsRepWorksheet.getRow(32).getCell(4).toString(), stsRepWorksheet.getRow(32).getCell(5).toString(),  stsRepWorksheet.getRow(32).getCell(6).getNumericCellValue());
		createStatusReportRow(false, st008DataTable, stsRepWorksheet.getRow(33).getCell(0).toString(), stsRepWorksheet.getRow(33).getCell(1).toString(), stsRepWorksheet.getRow(33).getCell(2).getNumericCellValue(), stsRepWorksheet.getRow(33).getCell(4).toString(), stsRepWorksheet.getRow(33).getCell(5).toString(),  stsRepWorksheet.getRow(33).getCell(6).getNumericCellValue());
		createStatusReportRow(false, st008DataTable, stsRepWorksheet.getRow(34).getCell(0).toString(), stsRepWorksheet.getRow(34).getCell(1).toString(), stsRepWorksheet.getRow(34).getCell(2).getNumericCellValue(), stsRepWorksheet.getRow(34).getCell(4).toString(), stsRepWorksheet.getRow(34).getCell(5).toString(),  stsRepWorksheet.getRow(34).getCell(6).getNumericCellValue());
		createStatusReportRow(false, st008DataTable, stsRepWorksheet.getRow(35).getCell(0).toString(), stsRepWorksheet.getRow(35).getCell(1).toString(), stsRepWorksheet.getRow(35).getCell(2).getNumericCellValue(), stsRepWorksheet.getRow(35).getCell(4).toString(), stsRepWorksheet.getRow(35).getCell(5).toString(),  stsRepWorksheet.getRow(35).getCell(6).getNumericCellValue());
		createStatusReportRow(false, st008DataTable, stsRepWorksheet.getRow(36).getCell(0).toString(), stsRepWorksheet.getRow(36).getCell(1).toString(), stsRepWorksheet.getRow(36).getCell(2).getNumericCellValue(), stsRepWorksheet.getRow(36).getCell(4).toString(), stsRepWorksheet.getRow(36).getCell(5).toString(),  stsRepWorksheet.getRow(36).getCell(6).getNumericCellValue());
		createStatusReportRow(false, st008DataTable, stsRepWorksheet.getRow(37).getCell(0).toString(), stsRepWorksheet.getRow(37).getCell(1).toString(), stsRepWorksheet.getRow(37).getCell(2).getNumericCellValue(), stsRepWorksheet.getRow(37).getCell(4).toString(), stsRepWorksheet.getRow(37).getCell(5).toString(),  stsRepWorksheet.getRow(37).getCell(6).getNumericCellValue());
		createStatusReportRow(false, st008DataTable, stsRepWorksheet.getRow(38).getCell(0).toString(), stsRepWorksheet.getRow(38).getCell(1).toString(), stsRepWorksheet.getRow(38).getCell(2).getNumericCellValue(), stsRepWorksheet.getRow(38).getCell(4).toString(), stsRepWorksheet.getRow(38).getCell(5).toString(),  stsRepWorksheet.getRow(38).getCell(6).getNumericCellValue());
		createStatusReportRow(false, st008DataTable, stsRepWorksheet.getRow(39).getCell(0).toString(), stsRepWorksheet.getRow(39).getCell(1).toString(), stsRepWorksheet.getRow(39).getCell(2).getNumericCellValue(), null, null,  0);
		document.add(st008DataTable);
		//Total the ST008
		//Creditor Banks Information
		PdfPTable st008TotalDataTbl = new PdfPTable(6);
		st008TotalDataTbl.setWidthPercentage(100);
		createTotalStatusReportRow(false, st008TotalDataTbl,  stsRepWorksheet.getRow(40).getCell(2).getNumericCellValue(),  stsRepWorksheet.getRow(39).getCell(6).getNumericCellValue());

		document.add(st008TotalDataTbl);
		document.add(nullTable);

		PdfPTable st007DataTable = new PdfPTable(6);
		st007DataTable.setWidthPercentage(100);

		//ST007
		createStatusReportRow(true, st007DataTable, null, null, 0, stsRepWorksheet.getRow(42).getCell(4).toString(), stsRepWorksheet.getRow(42).getCell(5).toString(),  stsRepWorksheet.getRow(42).getCell(6).getNumericCellValue());
		createStatusReportRow(true, st007DataTable, null, null, 0, stsRepWorksheet.getRow(43).getCell(4).toString(), stsRepWorksheet.getRow(43).getCell(5).toString(),  stsRepWorksheet.getRow(43).getCell(6).getNumericCellValue());
		createStatusReportRow(true, st007DataTable, null, null, 0, stsRepWorksheet.getRow(44).getCell(4).toString(), stsRepWorksheet.getRow(44).getCell(5).toString(),  stsRepWorksheet.getRow(44).getCell(6).getNumericCellValue());
		createStatusReportRow(true, st007DataTable, null, null,0, stsRepWorksheet.getRow(45).getCell(4).toString(), stsRepWorksheet.getRow(45).getCell(5).toString(),  stsRepWorksheet.getRow(45).getCell(6).getNumericCellValue());
		createStatusReportRow(true, st007DataTable, null,null, 0, stsRepWorksheet.getRow(46).getCell(4).toString(), stsRepWorksheet.getRow(46).getCell(5).toString(),  stsRepWorksheet.getRow(46).getCell(6).getNumericCellValue());
		createStatusReportRow(true, st007DataTable, null,null, 0, stsRepWorksheet.getRow(47).getCell(4).toString(), stsRepWorksheet.getRow(47).getCell(5).toString(),  stsRepWorksheet.getRow(47).getCell(6).getNumericCellValue());
		createStatusReportRow(true, st007DataTable, null, null, 0, stsRepWorksheet.getRow(48).getCell(4).toString(), stsRepWorksheet.getRow(48).getCell(5).toString(),  stsRepWorksheet.getRow(48).getCell(6).getNumericCellValue());
		createStatusReportRow(true, st007DataTable, null, null, 0, stsRepWorksheet.getRow(49).getCell(4).toString(), stsRepWorksheet.getRow(49).getCell(5).toString(),  stsRepWorksheet.getRow(49).getCell(6).getNumericCellValue());
		createStatusReportRow(true, st007DataTable, null, null, 0, stsRepWorksheet.getRow(50).getCell(4).toString(), stsRepWorksheet.getRow(50).getCell(5).toString(),  stsRepWorksheet.getRow(50).getCell(6).getNumericCellValue());
		document.add(st007DataTable);
		//Total the ST007
		//Creditor Banks Information
		PdfPTable st007TotalDataTbl = new PdfPTable(6);
		st007TotalDataTbl.setWidthPercentage(100);
		createTotalStatusReportRow(true, st007TotalDataTbl,  0,  stsRepWorksheet.getRow(39).getCell(6).getNumericCellValue());

		document.add(st007TotalDataTbl);
		document.add(nullTable);

		//GRAND TOTAL TABLE
		PdfPTable stsRptGrandTotalDataTbl = new PdfPTable(6);
		stsRptGrandTotalDataTbl.setWidthPercentage(100);

		PdfPCell gtTotalCell = new PdfPCell(new Phrase("GRAND TOTAL",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		gtTotalCell.setColspan(2);
		gtTotalCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		gtTotalCell.setBorder(com.itextpdf.text.Rectangle.BOX);
		gtTotalCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
		stsRptGrandTotalDataTbl.addCell(gtTotalCell);	

		String crStsTotal = stsRepWorksheet.getRow(54).getCell(2).getStringCellValue();
		String drStsTotal = stsRepWorksheet.getRow(54).getCell(6).getStringCellValue();
		
		PdfPCell gtNrOfTxns = new PdfPCell(new Phrase(crStsTotal,FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		gtNrOfTxns.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		gtNrOfTxns.setBorder(com.itextpdf.text.Rectangle.BOX);
		gtNrOfTxns.setBackgroundColor(BaseColor.BLUE);
		stsRptGrandTotalDataTbl.addCell(gtNrOfTxns);

		stsRptGrandTotalDataTbl.addCell(gtTotalCell);

		PdfPCell gtExtTxns = new PdfPCell(new Phrase(drStsTotal,FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		gtExtTxns.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		gtExtTxns.setBorder(com.itextpdf.text.Rectangle.BOX);
		gtExtTxns.setBackgroundColor(BaseColor.BLUE);
		stsRptGrandTotalDataTbl.addCell(gtExtTxns);

		document.add(stsRptGrandTotalDataTbl);

		document.close();

		log.info("*****REPORT GENERATION COMPLETED*****");
		//Copy the report to the Output reports folder 
		try
		{
			copyFile(pdfFileName);
		}
		catch(IOException ioe)
		{
			log.error("Error on copying MR022 report to temp "+ioe.getMessage());
			ioe.printStackTrace();
		}
		catch(Exception ex)
		{
			log.error("Error on copying MR022 report to temp "+ex.getMessage());
			ex.printStackTrace();
		}

	}


	public  void createSummaryDataRow(PdfPTable table, String service, double totTxns, double accpTxns, double rejTxns, String extService, double extTxns ) throws DocumentException
	{
		PdfPCell servicecell = new PdfPCell(new Phrase(service,FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		servicecell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		servicecell.setBorder(com.itextpdf.text.Rectangle.BOX);
		servicecell.setBackgroundColor(BaseColor.LIGHT_GRAY);
		table.addCell(servicecell);	

		PdfPCell nrOfTxns = new PdfPCell(new Phrase(String.valueOf((int) totTxns),FontFactory.getFont(FontFactory.HELVETICA, 8)));
		nrOfTxns.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		nrOfTxns.setBorder(com.itextpdf.text.Rectangle.BOX);
		table.addCell(nrOfTxns);

		PdfPCell accpTxnsCell = new PdfPCell(new Phrase(String.valueOf((int) accpTxns),FontFactory.getFont(FontFactory.HELVETICA, 8)));
		accpTxnsCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		accpTxnsCell.setBorder(com.itextpdf.text.Rectangle.BOX);
		table.addCell(accpTxnsCell);

		PdfPCell rejTxnsCell = new PdfPCell(new Phrase(String.valueOf((int) rejTxns),FontFactory.getFont(FontFactory.HELVETICA, 8)));
		rejTxnsCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		rejTxnsCell.setBorder(com.itextpdf.text.Rectangle.BOX);
		table.addCell(rejTxnsCell);

		PdfPCell extServ = new PdfPCell(new Phrase(extService,FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		extServ.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		extServ.setBackgroundColor(BaseColor.LIGHT_GRAY);
		extServ.setBorder(com.itextpdf.text.Rectangle.BOX);
		table.addCell(extServ);

		PdfPCell extTxnsCell = new PdfPCell(new Phrase(String.valueOf((int) extTxns),FontFactory.getFont(FontFactory.HELVETICA, 8)));
		extTxnsCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		extTxnsCell.setBorder(com.itextpdf.text.Rectangle.BOX);
		table.addCell(extTxnsCell);
	}

	public  void createSummStatusRptRow(PdfPTable table, String value1, double value2) throws DocumentException
	{
		PdfPCell statusServCell = new PdfPCell(new Phrase(value1,FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		statusServCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		statusServCell.setBorder(com.itextpdf.text.Rectangle.BOX);
		statusServCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
		table.addCell(statusServCell);	

		PdfPCell statusNrOfTxns = new PdfPCell(new Phrase(String.valueOf( (int) value2),FontFactory.getFont(FontFactory.HELVETICA, 8)));
		statusNrOfTxns.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		statusNrOfTxns.setBorder(com.itextpdf.text.Rectangle.BOX);
		table.addCell(statusNrOfTxns);

		PdfPCell nullCell = new PdfPCell(new Phrase(null,FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		nullCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		nullCell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
		table.addCell(nullCell);
		table.addCell(nullCell);
		table.addCell(nullCell);
		table.addCell(nullCell);
	}

	public  void createCreditorBankRow(PdfPTable table, String service, String bank, double totTxns, double accpTxns, double rejTxns, String extService, String debtBank, double extTxns ) throws DocumentException
	{
		PdfPCell crServCell = new PdfPCell(new Phrase(service,FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		crServCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		crServCell.setBorder(com.itextpdf.text.Rectangle.BOX);
		table.addCell(crServCell);	

		PdfPCell crBankCell = new PdfPCell(new Phrase(bank,FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		crBankCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		crBankCell.setBorder(com.itextpdf.text.Rectangle.BOX);
		table.addCell(crBankCell);	

		PdfPCell totTxnsCell = new PdfPCell(new Phrase(String.valueOf((int) totTxns),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		totTxnsCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		totTxnsCell.setBorder(com.itextpdf.text.Rectangle.BOX);
		table.addCell(totTxnsCell);	

		PdfPCell accpTxnCell = new PdfPCell(new Phrase(String.valueOf((int) accpTxns),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		accpTxnCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		accpTxnCell.setBorder(com.itextpdf.text.Rectangle.BOX);
		table.addCell(accpTxnCell);	

		PdfPCell rejTxnCell = new PdfPCell(new Phrase(String.valueOf((int) rejTxns),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		rejTxnCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		rejTxnCell.setBorder(com.itextpdf.text.Rectangle.BOX);
		table.addCell(rejTxnCell);	

		if(extService != null)
		{
			PdfPCell extServiceCell = new PdfPCell(new Phrase(extService,FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
			extServiceCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			extServiceCell.setBorder(com.itextpdf.text.Rectangle.BOX);
			table.addCell(extServiceCell);	

			PdfPCell extBankCell = new PdfPCell(new Phrase(debtBank,FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
			extBankCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			extBankCell.setBorder(com.itextpdf.text.Rectangle.BOX);
			table.addCell(extBankCell);	

			PdfPCell extTxnCell = new PdfPCell(new Phrase(String.valueOf((int) extTxns),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
			extTxnCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			extTxnCell.setBorder(com.itextpdf.text.Rectangle.BOX);
			table.addCell(extTxnCell);	
		}
		else
		{
			PdfPCell extServiceCell = new PdfPCell(new Phrase(null,FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
			extServiceCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			extServiceCell.setBorder(com.itextpdf.text.Rectangle.BOX);
			table.addCell(extServiceCell);	

			PdfPCell extBankCell = new PdfPCell(new Phrase(null,FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
			extBankCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			extBankCell.setBorder(com.itextpdf.text.Rectangle.BOX);
			table.addCell(extBankCell);	

			PdfPCell extTxnCell = new PdfPCell(new Phrase(null,FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
			extTxnCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			extTxnCell.setBorder(com.itextpdf.text.Rectangle.BOX);
			table.addCell(extTxnCell);	
		}
	}

	public  void createTotalCreditorBankRow(PdfPTable table, double totTxns, double accpTxns, double rejTxns, double extTxns ) throws DocumentException
	{
		PdfPCell crServCell = new PdfPCell(new Phrase(null,FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		crServCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		crServCell.setBorder(com.itextpdf.text.Rectangle.BOX);
		crServCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
		table.addCell(crServCell);	

		PdfPCell crBankCell = new PdfPCell(new Phrase("Total",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		crBankCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		crBankCell.setBorder(com.itextpdf.text.Rectangle.BOX);
		crBankCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
		table.addCell(crBankCell);	

		PdfPCell totTxnsCell = new PdfPCell(new Phrase(String.valueOf((int) totTxns),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		totTxnsCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		totTxnsCell.setBorder(com.itextpdf.text.Rectangle.BOX);
		totTxnsCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
		table.addCell(totTxnsCell);	

		PdfPCell accpTxnCell = new PdfPCell(new Phrase(String.valueOf((int) accpTxns),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		accpTxnCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		accpTxnCell.setBorder(com.itextpdf.text.Rectangle.BOX);
		accpTxnCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
		table.addCell(accpTxnCell);	

		PdfPCell rejTxnCell = new PdfPCell(new Phrase(String.valueOf((int) rejTxns),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		rejTxnCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		rejTxnCell.setBorder(com.itextpdf.text.Rectangle.BOX);
		rejTxnCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
		table.addCell(rejTxnCell);	

		PdfPCell extServiceCell = new PdfPCell(new Phrase(null,FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		extServiceCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		extServiceCell.setBorder(com.itextpdf.text.Rectangle.BOX);
		extServiceCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
		table.addCell(extServiceCell);	

		PdfPCell extBankCell = new PdfPCell(new Phrase("Total",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		extBankCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		extBankCell.setBorder(com.itextpdf.text.Rectangle.BOX);
		extBankCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
		table.addCell(extBankCell);	

		PdfPCell extTxnCell = new PdfPCell(new Phrase(String.valueOf((int) extTxns),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		extTxnCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		extTxnCell.setBorder(com.itextpdf.text.Rectangle.BOX);
		extTxnCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
		table.addCell(extTxnCell);	
	}

	public  void createGrandTotalTables(PdfPTable table, String totTxns, String accTxns, String rejTxns, String extTxns) throws DocumentException
	{
		PdfPCell gtTotalCell = new PdfPCell(new Phrase("GRAND TOTAL",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		gtTotalCell.setColspan(2);
		gtTotalCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		gtTotalCell.setBorder(com.itextpdf.text.Rectangle.BOX);
		gtTotalCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
		table.addCell(gtTotalCell);	

		PdfPCell gtNrOfTxns = new PdfPCell(new Phrase(totTxns,FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		gtNrOfTxns.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		gtNrOfTxns.setBorder(com.itextpdf.text.Rectangle.BOX);
		gtNrOfTxns.setBackgroundColor(BaseColor.BLUE);
		table.addCell(gtNrOfTxns);

		PdfPCell gtAccpTxns = new PdfPCell(new Phrase(accTxns,FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		gtAccpTxns.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		gtAccpTxns.setBorder(com.itextpdf.text.Rectangle.BOX);
		gtAccpTxns.setBackgroundColor(BaseColor.GREEN);
		table.addCell(gtAccpTxns);

		PdfPCell gtRejTxns = new PdfPCell(new Phrase(rejTxns,FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		gtRejTxns.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		gtRejTxns.setBorder(com.itextpdf.text.Rectangle.BOX);
		gtRejTxns.setBackgroundColor(BaseColor.RED);
		table.addCell(gtRejTxns);

		table.addCell(gtTotalCell);	

		PdfPCell gtExtTxns = new PdfPCell(new Phrase(extTxns,FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		gtExtTxns.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		gtExtTxns.setBorder(com.itextpdf.text.Rectangle.BOX);
		gtExtTxns.setBackgroundColor(BaseColor.GREEN);
		table.addCell(gtExtTxns);


	}

	public  void createDebtorBankRow(PdfPTable table, String service, String bank, double totTxns, double accpTxns, double rejTxns, String extService, String debtBank, double extTxns ) throws DocumentException
	{
		if(bank != null)
		{
			PdfPCell drServCell = new PdfPCell(new Phrase(service,FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
			drServCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			drServCell.setBorder(com.itextpdf.text.Rectangle.BOX);
			table.addCell(drServCell);	

			PdfPCell drBankCell = new PdfPCell(new Phrase(bank,FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
			drBankCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			drBankCell.setBorder(com.itextpdf.text.Rectangle.BOX);
			table.addCell(drBankCell);	

			PdfPCell totTxnsCell = new PdfPCell(new Phrase(String.valueOf((int) totTxns),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
			totTxnsCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			totTxnsCell.setBorder(com.itextpdf.text.Rectangle.BOX);
			table.addCell(totTxnsCell);	

			PdfPCell accpTxnCell = new PdfPCell(new Phrase(String.valueOf((int) accpTxns),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
			accpTxnCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			accpTxnCell.setBorder(com.itextpdf.text.Rectangle.BOX);
			table.addCell(accpTxnCell);	

			PdfPCell rejTxnCell = new PdfPCell(new Phrase(String.valueOf((int) rejTxns),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
			rejTxnCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			rejTxnCell.setBorder(com.itextpdf.text.Rectangle.BOX);
			table.addCell(rejTxnCell);	
		}
		else
		{
			PdfPCell drServCell = new PdfPCell(new Phrase(null,FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
			drServCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			drServCell.setBorder(com.itextpdf.text.Rectangle.BOX);
			table.addCell(drServCell);	

			PdfPCell drBankCell = new PdfPCell(new Phrase(null,FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
			drBankCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			drBankCell.setBorder(com.itextpdf.text.Rectangle.BOX);
			table.addCell(drBankCell);	

			PdfPCell totTxnsCell = new PdfPCell(new Phrase(null,FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
			totTxnsCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			totTxnsCell.setBorder(com.itextpdf.text.Rectangle.BOX);
			table.addCell(totTxnsCell);	

			PdfPCell accpTxnCell = new PdfPCell(new Phrase(null,FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
			accpTxnCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			accpTxnCell.setBorder(com.itextpdf.text.Rectangle.BOX);
			table.addCell(accpTxnCell);	

			PdfPCell rejTxnCell = new PdfPCell(new Phrase(null,FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
			rejTxnCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			rejTxnCell.setBorder(com.itextpdf.text.Rectangle.BOX);
			table.addCell(rejTxnCell);	
		}

		PdfPCell extServiceCell = new PdfPCell(new Phrase(extService,FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		extServiceCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		extServiceCell.setBorder(com.itextpdf.text.Rectangle.BOX);
		table.addCell(extServiceCell);	

		PdfPCell extBankCell = new PdfPCell(new Phrase(debtBank,FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		extBankCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		extBankCell.setBorder(com.itextpdf.text.Rectangle.BOX);
		table.addCell(extBankCell);	

		PdfPCell extTxnCell = new PdfPCell(new Phrase(String.valueOf((int) extTxns),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		extTxnCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		extTxnCell.setBorder(com.itextpdf.text.Rectangle.BOX);
		table.addCell(extTxnCell);	

	}

	public  void createStatusReportRow(boolean lastRow, PdfPTable table, String service, String bank, double totTxns,String extService, String debtBank, double extTxns ) throws DocumentException
	{

		if(lastRow)
		{
			PdfPCell crServCell = new PdfPCell(new Phrase(null,FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
			crServCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			crServCell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
			table.addCell(crServCell);	

			PdfPCell crBankCell = new PdfPCell(new Phrase(null,FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
			crBankCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			crBankCell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
			table.addCell(crBankCell);	

			PdfPCell totTxnsCell = new PdfPCell(new Phrase(null,FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
			totTxnsCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			totTxnsCell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
			table.addCell(totTxnsCell);	
		}
		else
		{
			PdfPCell crServCell = new PdfPCell(new Phrase(service,FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
			crServCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			crServCell.setBorder(com.itextpdf.text.Rectangle.BOX);
			table.addCell(crServCell);	

			PdfPCell crBankCell = new PdfPCell(new Phrase(bank,FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
			crBankCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			crBankCell.setBorder(com.itextpdf.text.Rectangle.BOX);
			table.addCell(crBankCell);	

			PdfPCell totTxnsCell = new PdfPCell(new Phrase(String.valueOf((int) totTxns),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
			totTxnsCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			totTxnsCell.setBorder(com.itextpdf.text.Rectangle.BOX);
			table.addCell(totTxnsCell);	
		}

		if(debtBank != null)
		{
			PdfPCell extServiceCell = new PdfPCell(new Phrase(extService,FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
			extServiceCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			extServiceCell.setBorder(com.itextpdf.text.Rectangle.BOX);
			table.addCell(extServiceCell);	

			PdfPCell extBankCell = new PdfPCell(new Phrase(debtBank,FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
			extBankCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			extBankCell.setBorder(com.itextpdf.text.Rectangle.BOX);
			table.addCell(extBankCell);	

			PdfPCell extTxnCell = new PdfPCell(new Phrase(String.valueOf((int) extTxns),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
			extTxnCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			extTxnCell.setBorder(com.itextpdf.text.Rectangle.BOX);
			table.addCell(extTxnCell);	
		}
		else
		{
			PdfPCell extServiceCell = new PdfPCell(new Phrase(null,FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
			extServiceCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			extServiceCell.setBorder(com.itextpdf.text.Rectangle.BOX);
			table.addCell(extServiceCell);	

			PdfPCell extBankCell = new PdfPCell(new Phrase(null,FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
			extBankCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			extBankCell.setBorder(com.itextpdf.text.Rectangle.BOX);
			table.addCell(extBankCell);	

			PdfPCell extTxnCell = new PdfPCell(new Phrase(null,FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
			extTxnCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			extTxnCell.setBorder(com.itextpdf.text.Rectangle.BOX);
			table.addCell(extTxnCell);	
		}
	}

	public  void createTotalStatusReportRow(boolean lastRow, PdfPTable table, double crTotTxns, double drTotTxns) throws DocumentException
	{
		if(lastRow)
		{
			PdfPCell crServCell = new PdfPCell(new Phrase(null,FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
			crServCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			crServCell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
			table.addCell(crServCell);	

			PdfPCell crBankCell = new PdfPCell(new Phrase(null,FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
			crBankCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			crBankCell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
			table.addCell(crBankCell);	

			PdfPCell totTxnsCell = new PdfPCell(new Phrase(null,FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
			totTxnsCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			totTxnsCell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
			table.addCell(totTxnsCell);	
		}
		else
		{
			PdfPCell crServCell = new PdfPCell(new Phrase(null,FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
			crServCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			crServCell.setBorder(com.itextpdf.text.Rectangle.BOX);
			crServCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			table.addCell(crServCell);	

			PdfPCell crBankCell = new PdfPCell(new Phrase("Total",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
			crBankCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			crBankCell.setBorder(com.itextpdf.text.Rectangle.BOX);
			crBankCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			table.addCell(crBankCell);	

			PdfPCell totTxnsCell = new PdfPCell(new Phrase(String.valueOf((int) crTotTxns),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
			totTxnsCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			totTxnsCell.setBorder(com.itextpdf.text.Rectangle.BOX);
			totTxnsCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			table.addCell(totTxnsCell);	
		}

		PdfPCell drServCell = new PdfPCell(new Phrase(null,FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		drServCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		drServCell.setBorder(com.itextpdf.text.Rectangle.BOX);
		drServCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
		table.addCell(drServCell);	


		PdfPCell extBankCell = new PdfPCell(new Phrase("Total",FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		extBankCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		extBankCell.setBorder(com.itextpdf.text.Rectangle.BOX);
		extBankCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
		table.addCell(extBankCell);	

		PdfPCell extTxnCell = new PdfPCell(new Phrase(String.valueOf((int) drTotTxns),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		extTxnCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		extTxnCell.setBorder(com.itextpdf.text.Rectangle.BOX);
		extTxnCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
		table.addCell(extTxnCell);	
	}

	public  void copyFile(String fileName) throws IOException 
	{
		File tmpFile = new File(tempDir, fileName);
		File destFile = new File(reportDir, fileName);
		
		log.info("tmpFile ==> "+ tmpFile);
		log.info("destFile===> "+ destFile);
		
		Files.copy(tmpFile, destFile);
		log.info("Copying "+fileName+" from temp to output directory...");
	}

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
