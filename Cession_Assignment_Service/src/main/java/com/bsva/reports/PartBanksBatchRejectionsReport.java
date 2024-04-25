package com.bsva.reports;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import com.bsva.commons.model.CreditorBankModel;
import com.bsva.commons.model.DebtorBankModel;
import com.bsva.commons.model.MandateRejectionModel;
import com.bsva.commons.model.ReasonCodesModel;
import com.bsva.commons.model.SysctrlCompParamModel;
import com.bsva.commons.model.SystemParameterModel;
import com.bsva.entities.MdtCnfgReportNamesEntity;
import com.bsva.entities.MdtOpsRepSeqNrEntity;
import com.bsva.interfaces.AdminBeanRemote;
import com.bsva.interfaces.PropertyUtilRemote;
import com.bsva.interfaces.ReportBeanRemote;
import com.bsva.interfaces.ServiceBeanRemote;
import com.bsva.interfaces.ValidationBeanRemote;
import com.bsva.utils.DateUtil;
import com.bsva.utils.Util;
import com.google.common.io.Files;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * @author SalehaR
 * @author SalehaR -2019/10/09 - Align Reports to run from Single Table
 */
public class PartBanksBatchRejectionsReport 
{
	//	private String downloaddirectory ="/home/opsjava/Delivery/Mandates/Reports/";
	public static Logger log=Logger.getLogger(PartBanksBatchRejectionsReport.class);
	public static ServiceBeanRemote beanRemote;
	public static ValidationBeanRemote valBeanRemote;
	private static AdminBeanRemote adminBeanRemote;
	private static PropertyUtilRemote propertyUtilRemote;
	private static ReportBeanRemote reportBeanRemote;

	List<CreditorBankModel> creditorBankModelList;
	CreditorBankModel creditorBankModel ;
	List<ReasonCodesModel> rejectReasonCodesList;
	List<ReasonCodesModel> reasonCodesList;
	List<DebtorBankModel> debtorBankModelList;
	DebtorBankModel debtorBankModel ;
	String feedbackMsg = null;
	String reportName, reportNr, reportDir = null, tempDir = null, mdtLoadType = null;
	String pdfFileName = null, pbmd03;

	public PartBanksBatchRejectionsReport()
	{
		contextAdminBeanCheck();
		contextCheck();
		contextValidationBeanCheck();
		contextPropertyFileCheck();
		contextReportCheck();

		try
		{
			tempDir = propertyUtilRemote.getPropValue("ExtractTemp.Out");
			log.info("tempDir ==> "+tempDir);
			reportDir= propertyUtilRemote.getPropValue("Reports.Output");
			log.info("reportDir ==> "+reportDir);
			pbmd03 = propertyUtilRemote.getPropValue("RPT.BANK.BATCH.REJECTIONS");
			mdtLoadType = propertyUtilRemote.getPropValue("MDT.LOAD.TYPE");
		}
		catch(Exception ex)
		{
			log.error("PBMD03- Could not find MandateMessageCommons.properties in classpath");	
			reportDir = "/home/opsjava/Delivery/Mandates/Output/Reports/";
			tempDir="/home/opsjava/Delivery/Mandates/Output/temp/";
		}

		//Retrieve Report Name
		MdtCnfgReportNamesEntity reportNameEntity = new MdtCnfgReportNamesEntity();
		reportNameEntity = (MdtCnfgReportNamesEntity) adminBeanRemote.retrieveReportName(pbmd03);

		if(reportNameEntity != null)
		{
			reportNr = reportNameEntity.getReportNr();
			reportName = reportNameEntity.getReportName();
		}
	}

	public void generateMndRejectBatchPerBankReport()
	{
		creditorBankModelList = new ArrayList<CreditorBankModel>();

		creditorBankModelList=(List<CreditorBankModel>) adminBeanRemote.retrieveActiveCreditorBank();
		log.debug("in the PBMD03 ");

		if(creditorBankModelList!= null && creditorBankModelList.size() > 0)
		{
			for (CreditorBankModel creditorBankModel : creditorBankModelList) 
			{
				try
				{
					generateReportDetail(creditorBankModel.getMemberNo(),creditorBankModel.getMemberName(),creditorBankModelList);
				}
				catch(DocumentException de)
				{
					de.printStackTrace();
					log.error("<DE> Error generating generateMndRejectBatchPerBankReport "+ de.getMessage());
				}
				catch(FileNotFoundException fne)
				{
					fne.printStackTrace();
					log.error("<FNE> Error generating generateMndRejectBatchPerBankReport "+ fne.getMessage());
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
					log.error("<EX> Error generating generateMndRejectBatchPerBankReport "+ ex.getMessage());
				}
			}

		}
	}



	public void generateReportDetail(String memberId, String memberName,List<CreditorBankModel>credBankList)throws DocumentException, FileNotFoundException
	{
		log.info("*****GENERATING PMBD03 REPORT for "+memberName+"*****");
		log.debug("in the generateReportDetail method");
		DecimalFormat df = new DecimalFormat("### ### ### ### ### ##0.00");
		DateFormat dateTimeFormat = new SimpleDateFormat("yyyyMMdd_HH:mm:ss");
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat fileTimeFormat = new SimpleDateFormat("MM-yyyy");
		DateFormat endDateFormat = new SimpleDateFormat("ddMMyyyy");
		Date currentDate = new Date();
		DateUtil dateUtil = new DateUtil();

		int pageNo = 1;
		String bankId= memberId.substring(2, 6);

		int lastSeqNoUsed;
		String strSeqNo; 

		MdtOpsRepSeqNrEntity mdtOpsRepSeqNrEntity = new MdtOpsRepSeqNrEntity();
		mdtOpsRepSeqNrEntity = (MdtOpsRepSeqNrEntity)adminBeanRemote.retrieveRepSeqNr(reportNr,memberId);

		if(mdtOpsRepSeqNrEntity != null)
		{
			lastSeqNoUsed = Integer.valueOf(mdtOpsRepSeqNrEntity.getLastSeqNo());
			lastSeqNoUsed = lastSeqNoUsed + 1;
		}
		else
			lastSeqNoUsed = 1;

		strSeqNo = String.format("%06d",lastSeqNoUsed);
		mdtOpsRepSeqNrEntity.setLastSeqNo(strSeqNo);
		adminBeanRemote.updateReportSeqNr(mdtOpsRepSeqNrEntity);

		String reportSeqNo = strSeqNo.substring(3,6);

		String files = tempDir+bankId+"AC"+reportNr+endDateFormat.format(currentDate).toString()+"."+reportSeqNo + ".pdf";
		pdfFileName = bankId+"AC"+reportNr+endDateFormat.format(currentDate).toString()+"."+reportSeqNo + ".pdf";

		SysctrlCompParamModel companyParamModel = new SysctrlCompParamModel();
		companyParamModel = (SysctrlCompParamModel) beanRemote.retrieveCompanyParameters();

		SystemParameterModel systemParameterModel = new SystemParameterModel();
		systemParameterModel =(SystemParameterModel) adminBeanRemote.retrieveWebActiveSysParameter();

		debtorBankModelList = new ArrayList<DebtorBankModel>();
		debtorBankModelList = (List<DebtorBankModel>) adminBeanRemote.retrieveActiveDebtorBank();

		Document document = new Document(PageSize.A4.rotate());

		FileOutputStream fileOutputStream = new FileOutputStream(files);

		PageEvent pageEvent = new PageEvent(companyParamModel, systemParameterModel, reportNr, reportName, true, false, memberName, memberId, debtorBankModelList, null, null,null,true);

		PdfWriter writer = null;
		writer = PdfWriter.getInstance(document, fileOutputStream);
		writer.setPageEvent(pageEvent);
		writer.open();
		document.open();

		File file = new File(files);

		PdfPTable freeline = new PdfPTable(1);
		freeline.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		freeline.setWidthPercentage(100);
		freeline.addCell( new PdfPCell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 8)))).setBorderColor(BaseColor.WHITE);

		//		PdfPTable firstHeading = new PdfPTable(3);
		//		firstHeading.setWidthPercentage(100);
		//
		//		try {
		//			PdfPCell cell1 = new PdfPCell(new Paragraph("Date: " + dateTimeFormat.format(currentDate), FontFactory.getFont(FontFactory.HELVETICA, 8)));
		//			cell1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		//			cell1.setBorder(Rectangle.NO_BORDER);
		//			firstHeading.addCell(cell1);
		//
		//			PdfPCell cell2 = new PdfPCell(new Paragraph(companyParamModel.getCompName(), FontFactory.getFont(FontFactory.HELVETICA, 9, com.itextpdf.text.Font.BOLD)));
		//			cell2.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		//			cell2.setBorder(Rectangle.NO_BORDER);
		//			firstHeading.addCell(cell2);
		//
		//			PdfPCell cell3 = new PdfPCell(new Paragraph("Page:  " + pageNo,   FontFactory.getFont(FontFactory.HELVETICA, 8)));
		//			cell3.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
		//			cell3.setBorder(Rectangle.NO_BORDER);
		//			firstHeading.addCell(cell3);
		//
		//		} 
		//		catch (NullPointerException x) 
		//		{
		//			log.debug("Error on Page 1 Header" + x);
		//		}
		//
		//		document.add(firstHeading);


		String strMonth = null;
		String month = null;
		String year = null;
		Date firstDate, lastDate = null;
		String strFirstDate = null, strLastDate = null;

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

			strFirstDate = "01"+strMonth+year;
			strLastDate = endDateFormat.format(lastDate);
			log.debug("lastDate ==> "+ lastDate);
			log.debug("endDate ==> "+ endDateFormat.format(lastDate));
		}

		int headingSize = debtorBankModelList.size() + 4;//for the 1st column

		//		PdfPTable secondheading = new PdfPTable(3);
		//		secondheading.setWidthPercentage(100);
		//		try 
		//		{
		//
		//			PdfPCell nullCell001 = new PdfPCell(new Paragraph(null,FontFactory.getFont(FontFactory.HELVETICA, 8)));
		//			nullCell001.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		//			nullCell001.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
		//			secondheading.addCell(nullCell001);
		//
		//			PdfPCell nullCell02 = new PdfPCell(new Paragraph("REG.NO."+ companyParamModel.getRegistrationNr(), FontFactory.getFont(FontFactory.HELVETICA, 8)));
		//			nullCell02.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		//			nullCell02.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
		//			secondheading.addCell(nullCell02);
		//
		//			if(systemParameterModel.getProcessDate() != null)
		//			{
		//				strMonth =fileTimeFormat.format(systemParameterModel.getProcessDate()).substring(0,2);
		//				log.debug("strMonth ==> "+strMonth);
		//				month= DateUtil.getMonthFullname(Integer.valueOf(strMonth), true);
		//				log.debug("Process Month----->"+month);
		//				year = String.valueOf(DateUtil.getYear(systemParameterModel.getProcessDate()));
		//				log.debug("year----->"+year);
		//
		//				//Calculate the last date of the month
		//				Calendar nextNotifTime = Calendar.getInstance();
		//				nextNotifTime.add(Calendar.MONTH, 1);
		//				nextNotifTime.set(Calendar.DATE, 1);
		//				nextNotifTime.add(Calendar.DATE, -1);
		//				lastDate = nextNotifTime.getTime();
		//
		//				strFirstDate = "01"+strMonth+year;
		//				strLastDate = endDateFormat.format(lastDate);
		//				log.debug("lastDate ==> "+ lastDate);
		//				log.debug("endDate ==> "+ endDateFormat.format(lastDate));
		//			}
		//
		//
		//			PdfPCell nullCell03 = new PdfPCell(new Paragraph("Process Month: "+month+ " "+year,FontFactory.getFont(FontFactory.HELVETICA, 8)));
		//			nullCell03.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
		//			nullCell03.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
		//			secondheading.addCell(nullCell03);
		//
		//		} 
		//		catch (NullPointerException x)
		//		{
		//			log.debug("Error Finding Company Reg No" + x);
		//		}
		//		document.add(secondheading);
		//		document.add(freeline);

		//		PdfPTable bankIdHeadings = new PdfPTable(1);
		//		bankIdHeadings.setWidthPercentage(100);
		//
		//		PdfPCell reportNameId = new PdfPCell(new Paragraph(reportNr+" - "+reportName +"-",FontFactory.getFont(FontFactory.HELVETICA, 9, com.itextpdf.text.Font.BOLD)));
		//		reportNameId.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		//		reportNameId.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
		//		bankIdHeadings.addCell(reportNameId);
		//		document.add(bankIdHeadings);
		//
		//
		//		PdfPTable bankNameHeading = new PdfPTable(1);
		//		bankNameHeading.setWidthPercentage(100);
		//
		//		PdfPCell bankName= new PdfPCell(new Paragraph(memberName + "  " +memberId, FontFactory.getFont( FontFactory.HELVETICA, 9, com.itextpdf.text.Font.BOLD)));
		//		bankName.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		//		bankName.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
		//		bankNameHeading.addCell(bankName);
		//		document.add(bankNameHeading);

		//		PdfPTable thirdHeading = new PdfPTable(1);
		//		thirdHeading.setWidthPercentage(100);
		//
		//		PdfPCell cell2 = new PdfPCell(new Paragraph("NUMBER OF REJECTIONS PER REJECTION REASON PER DEBTOR BANK", FontFactory.getFont( FontFactory.HELVETICA, 9, com.itextpdf.text.Font.BOLD)));
		//		cell2.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		//		cell2.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
		//		thirdHeading.addCell(cell2);
		//
		//		document.add(thirdHeading);
		//		document.add(freeline);

		//		debtorBankModelList = new ArrayList<DebtorBankModel>();
		//		debtorBankModelList = (List<DebtorBankModel>) adminBeanRemote.retrieveActiveDebtorBank();
		//		int headingSize = 0;

		//				if(debtorBankModelList != null && debtorBankModelList.size() > 0)
		//				{
		//			headingSize = debtorBankModelList.size() + 4;//for the 1st column
		//			log.debug("headingSize == "+headingSize);
		//			PdfPTable batchDetailsHeading = new PdfPTable(headingSize);
		//			batchDetailsHeading.setWidthPercentage(100);
		//
		//
		//			PdfPCell rejectReasonHeading = new PdfPCell(new Phrase("MANDATE REJECTION REASONS",FontFactory.getFont(FontFactory.HELVETICA, 9,com.itextpdf.text.Font.BOLD)));
		//			rejectReasonHeading.setColspan(4);
		//			rejectReasonHeading.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		//			rejectReasonHeading.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
		//			batchDetailsHeading.addCell(rejectReasonHeading);
		//
		//			String debtorBankId= null;
		//
		//			//String debtorBank = null;
		//			for(DebtorBankModel debtorBankModel :debtorBankModelList)
		//			{
		//				debtorBankId= null;
		//				String debtorBank=debtorBankModel.getMemberName();
		//				debtorBankId= debtorBankModel.getMemberNo();
		//
		//				PdfPCell debtorBank16 = new PdfPCell(new Phrase(debtorBank,FontFactory.getFont(FontFactory.HELVETICA, 9, com.itextpdf.text.Font.BOLD)));
		//				debtorBank16.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		//				debtorBank16.setBorder(com.itextpdf.text.Rectangle.TOP | com.itextpdf.text.Rectangle.BOTTOM);
		//				batchDetailsHeading.addCell(debtorBank16);
		//
		//				document.add(batchDetailsHeading);
		//
		//				debtorBankModel.setTxnCount(BigDecimal.ZERO);
		//			}
		if(debtorBankModelList != null && debtorBankModelList.size() > 0)
		{
			//String debtorBank = null;
			for(DebtorBankModel debtorBankModel :debtorBankModelList)
			{
				debtorBankModel.setTxnCount(BigDecimal.ZERO);
			}

			//Populate Data 
			rejectReasonCodesList = new ArrayList<ReasonCodesModel>();
			rejectReasonCodesList = (List<ReasonCodesModel>) adminBeanRemote.retrieveRejectionCodesForRejectionsReport();
			if(rejectReasonCodesList!= null && rejectReasonCodesList.size()> 0)
			{
				//Loop through reason codes
				for(ReasonCodesModel reasonCodesModel :rejectReasonCodesList)
				{
					String reasonCode = reasonCodesModel.getReasonCode();
					String reasonDes = reasonCodesModel.getName();

					PdfPTable reasonCodeData = new PdfPTable(headingSize);
					reasonCodeData.setWidthPercentage(100);

					PdfPCell rejectReason = new PdfPCell(new Phrase(reasonDes,FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
					rejectReason.setColspan(4);
					rejectReason.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
					rejectReason.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
					reasonCodeData.addCell(rejectReason);	

					//Loop through Debtor Banks to get information
					for(DebtorBankModel dbModel :debtorBankModelList)
					{
						log.debug("Creditor Bank ==> "+memberId);
						log.debug("Debtor Bank ==> "+dbModel.getMemberName());
						log.debug("rejectReasonCode ==> "+ reasonCode);
						log.debug("txnCount ==> "+ dbModel.getTxnCount());

						if(reasonCode.equalsIgnoreCase("NMTC"))
						{
							MandateRejectionModel  mandateRejectionModel = new MandateRejectionModel();	
							mandateRejectionModel =(MandateRejectionModel) adminBeanRemote.retrieveMdte002RejectReasonDataPerBank(reasonCode, memberId, dbModel.getMemberNo(), strFirstDate, strLastDate);

							log.debug(reasonCode+ " Count ---->"+mandateRejectionModel.getRejectReasonCodeCount());
							if(mandateRejectionModel == null || mandateRejectionModel.getRejectReasonCodeCount() == null)
								mandateRejectionModel.setRejectReasonCodeCount(BigDecimal.ZERO);

							dbModel.setTxnCount(dbModel.getTxnCount().add(mandateRejectionModel.getRejectReasonCodeCount()));

							PdfPCell bankData = new PdfPCell(new Phrase(String.valueOf(mandateRejectionModel.getRejectReasonCodeCount()),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
							bankData.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
							bankData.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
							reasonCodeData.addCell(bankData);	
						}
						else
						{
							MandateRejectionModel  mandateRejectionModel = new MandateRejectionModel();	
							if(mdtLoadType.equalsIgnoreCase("M")) {
								mandateRejectionModel =(MandateRejectionModel) adminBeanRemote.retrievePain012ReasonCodeDataPerBank(reasonCode, memberId, dbModel.getMemberNo(), strFirstDate, strLastDate);
							}else {
								mandateRejectionModel =(MandateRejectionModel) reportBeanRemote.retrievePain012ReasonCodeDataPerBank(reasonCode, memberId, dbModel.getMemberNo(), strFirstDate, strLastDate);
							}
							
							log.debug(reasonCode+ " Count ---->"+mandateRejectionModel.getRejectReasonCodeCount());
							if(mandateRejectionModel == null || mandateRejectionModel.getRejectReasonCodeCount() == null)
								mandateRejectionModel.setRejectReasonCodeCount(BigDecimal.ZERO);

							dbModel.setTxnCount(dbModel.getTxnCount().add(mandateRejectionModel.getRejectReasonCodeCount()));

							PdfPCell bankData = new PdfPCell(new Phrase(String.valueOf(mandateRejectionModel.getRejectReasonCodeCount()),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
							bankData.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
							bankData.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
							reasonCodeData.addCell(bankData);	
						}

						document.add(reasonCodeData);

						log.debug("==========TOTAL for "+reasonCode+" ========== ");
						log.debug("==========CR_BANK "+memberId+ " ========== ");
						log.debug("==========DR_BANK "+dbModel.getMemberName()+ " ========== ");
						log.debug("==========TXN COUNT "+dbModel.getTxnCount()+ " ========== ");

					}
				}//end of FOR reject reason
			}//if reject list > 0

			//POPULATE TOTALS PER DEBTOR BANK/REASON CODE
			PdfPTable totalTable = new PdfPTable(headingSize);
			totalTable.setWidthPercentage(100);

			PdfPCell totalHeading = new PdfPCell(new Phrase("TOTAL",FontFactory.getFont(FontFactory.HELVETICA, 9, com.itextpdf.text.Font.BOLD)));
			totalHeading.setColspan(4);
			totalHeading.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
			totalHeading.setBorder(com.itextpdf.text.Rectangle.TOP | com.itextpdf.text.Rectangle.LEFT);
			totalTable.addCell(totalHeading);

			BigDecimal grandTotal = BigDecimal.ZERO;
			int count = 2;
			
			log.debug("Grand Total ==>"+grandTotal);
			for(DebtorBankModel debtModel :debtorBankModelList)
			{
				count++;
				log.debug("==========DR_BANK "+debtModel.getMemberName()+ " ========== ");
				log.debug("==========FINAL TXN COUNT "+debtModel.getTxnCount()+ " ========== ");

				grandTotal = grandTotal.add(debtModel.getTxnCount());
				log.debug("Grand Total AFTER counts==>"+grandTotal);
				PdfPCell rsnCountData = new PdfPCell(new Phrase(String.valueOf(debtModel.getTxnCount()),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
				rsnCountData.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
				
				if(count == headingSize)
				{
					rsnCountData.setBorder(com.itextpdf.text.Rectangle.TOP | com.itextpdf.text.Rectangle.BOTTOM |com.itextpdf.text.Rectangle.RIGHT);
				}
				else
				{
					rsnCountData.setBorder(com.itextpdf.text.Rectangle.TOP | com.itextpdf.text.Rectangle.BOTTOM);
				}


				totalTable.addCell(rsnCountData);	
			}

			document.add(totalTable);


			//POPULATE GRAND TOTAL
			PdfPTable grandTotalTable = new PdfPTable(headingSize);
			grandTotalTable.setWidthPercentage(100);
			log.info("Grand Total SETTING counts==>"+grandTotal);
			PdfPCell grdTotalHeading = new PdfPCell(new Phrase("GRAND TOTAL                    "+String.valueOf(grandTotal),FontFactory.getFont(FontFactory.HELVETICA, 9, com.itextpdf.text.Font.BOLD)));
			grdTotalHeading.setColspan(4);
			grdTotalHeading.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
			grdTotalHeading.setBorder(com.itextpdf.text.Rectangle.BOTTOM | com.itextpdf.text.Rectangle.LEFT |com.itextpdf.text.Rectangle.RIGHT);
			grandTotalTable.addCell(grdTotalHeading);

			PdfPCell nullCell001 = new PdfPCell(new Paragraph(null,FontFactory.getFont(FontFactory.HELVETICA, 8)));
			nullCell001.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
			nullCell001.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
			
			int loopSize = headingSize-4;
			log.debug("loopSize ==>"+loopSize);
			for(int i=0; i < loopSize; i++) 
			{
				grandTotalTable.addCell(nullCell001);
			}

			document.add(grandTotalTable);
		}//debtor bank list > 0
		document.close();
		log.info("*****REPORT GENERATION COMPLETED*****");

		//Copy the report to the Output reports folder 
		try
		{
			copyFile(pdfFileName);
		}
		catch(IOException ioe)
		{
			log.error("Error on copying PBMD03 report to temp "+ioe.getMessage());
			ioe.printStackTrace();
		}
		catch(Exception ex)
		{
			log.error("Error on copying PBMD03 report to temp "+ex.getMessage());
			ex.printStackTrace();
		}


	}

	public  void copyFile(String fileName) throws IOException 
	{
		File tmpFile = new File(tempDir, fileName);
		File destFile = new File(reportDir, fileName);

		log.info("tmpFile ==> "+ tmpFile);
		log.info("destFile===> "+ destFile);

		Files.copy(tmpFile, destFile);
		log.info("Copying "+fileName+"from temp to output directory...");
	}

	//CONTEXT LOOKUPS//
	public static void contextValidationBeanCheck() 
	{
		if (valBeanRemote == null) {
			valBeanRemote = Util.getValidationBean();
		}
	}

	private static void contextCheck() 
	{
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
	
	private static void contextReportCheck() {
		if (reportBeanRemote == null) {
			reportBeanRemote = Util.getReportBean();
		}
	}

}
