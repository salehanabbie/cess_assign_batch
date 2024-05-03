package com.bsva.panels.AcPasaReports;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.wicket.request.handler.resource.ResourceStreamRequestHandler;
import org.apache.wicket.util.resource.FileResourceStream;

import com.bsva.commons.model.CustomerParametersModel;
import com.bsva.commons.model.DebtorBankModel;
import com.bsva.commons.model.MandateResponseOutstandingPerBankModel;
import com.bsva.commons.model.OpsReportSeqNrModel;
import com.bsva.commons.model.SysctrlCompParamModel;
import com.bsva.commons.model.SystemParameterModel;
import com.bsva.controller.Controller;
import com.bsva.models.WebOpsReportSeqNrModel;
import com.bsva.utils.DateUtil;
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


public class MandateResponseOutstandingPerBank 
{


	String reportName, reportNr;
	
	private String downloaddirectory ="/home/opsjava/Delivery/Cession_Assign/Reports/";
	public static Logger log=Logger.getLogger(MandateResponseOutstandingPerBank.class);
	Controller controller = new Controller();
	
	List<CustomerParametersModel> custParamsModelList;
	List<DebtorBankModel> debtorBankModelList;
	DebtorBankModel debtorBankModel ;

	CustomerParametersModel customerParametersModel = new CustomerParametersModel();
	int lastSeqNoUsed;
	
	public MandateResponseOutstandingPerBank(String reportNr, String reportName)
	{
		this.reportNr = reportNr;
		this.reportName = reportName;

	}
	
	
	
	public void generateMandateResponseOutstandingPerBank()
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
						log.debug("mndtResponseOutstandingPerBankList--->"+mndtResponseOutstandingPerBankList);
						try
						{
							generateReportDetail(debtorBankModel.getMemberNo(),debtorBankModel.getMemberName(), mndtResponseOutstandingPerBankList);
						}
						catch(DocumentException | FileNotFoundException ex)
						{
							log.error("Error generating mdtRespOutstandingReport "+ ex.getMessage());
							ex.printStackTrace();
						}
					}
				}
			}
				
		}
	}
	
	
	
	public void generateReportDetail(String memberId,String memberName,List<MandateResponseOutstandingPerBankModel>mdtRespOutstandingList)throws DocumentException, FileNotFoundException
	{
		DecimalFormat df = new DecimalFormat("### ### ### ### ### ##0.00");
        DateFormat dateTimeFormat = new SimpleDateFormat("yyyyMMdd_HH:mm:ss");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat fileTimeFormat = new SimpleDateFormat("MM-yyyy");
        Date currentDate = new Date();
     
        DateFormat endDateFormat = new SimpleDateFormat("ddMMyyyy");

        debtorBankModel	= new DebtorBankModel();
        int pageNo = 1;
        String bankId= memberId.substring(2, 6);
        log.debug("bankId---->"+bankId);
        
//        OpsReportSeqNrModel opsReportSeqNrModel = new OpsReportSeqNrModel();
//        opsReportSeqNrModel = (OpsReportSeqNrModel)controller.retrieveReportSeqNr(reportNr,memberId);
        

        String files = downloaddirectory+bankId+"AC"+reportNr+endDateFormat.format(currentDate).toString()+"."+lastSeqNoUsed+".pdf";
      

		Document document = new Document(PageSize.A4.rotate());

		FileOutputStream fileOutputStream = new FileOutputStream(files);
		PdfWriter writer = null;
		writer = PdfWriter.getInstance(document, fileOutputStream);
		writer.open();
		document.open();

		File file = new File(files);


		ResourceStreamRequestHandler target = new ResourceStreamRequestHandler( new FileResourceStream(new org.apache.wicket.util.file.File(file)));
		PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(files));
		target.setFileName(files);
//		RequestCycle.get().scheduleRequestHandlerAfterCurrent(target);
		document.open();

    	SysctrlCompParamModel companyParamModel = new SysctrlCompParamModel();
		companyParamModel = (SysctrlCompParamModel) controller.retrieveCompanyParameters();
		
		SystemParameterModel systemParameterModel = new SystemParameterModel();
		systemParameterModel = (SystemParameterModel) controller.retrieveWebActiveSysParameter();
    
        
        PdfPTable freeline = new PdfPTable(1);
        freeline.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        freeline.setWidthPercentage(100);
        freeline.addCell( new PdfPCell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 8)))).setBorderColor(BaseColor.WHITE);

        PdfPTable firstHeading = new PdfPTable(3);
        firstHeading.setWidthPercentage(100);
        

        try {
               PdfPCell cell1 =  new PdfPCell(new Paragraph("Date: " + dateTimeFormat.format(currentDate),FontFactory.getFont(FontFactory.HELVETICA, 8)));
               cell1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
               cell1.setBorder(Rectangle.NO_BORDER);
               firstHeading.addCell(cell1);

               PdfPCell cell2 = new PdfPCell(new Paragraph(companyParamModel.getCompName(), FontFactory.getFont(FontFactory.HELVETICA, 9, com.itextpdf.text.Font.BOLD)));
               cell2.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
               cell2.setBorder(Rectangle.NO_BORDER);
               firstHeading.addCell(cell2);

               PdfPCell cell3 = new PdfPCell(new Paragraph("Page:  " + pageNo,   FontFactory.getFont(FontFactory.HELVETICA, 8)));
               cell3.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
               cell3.setBorder(Rectangle.NO_BORDER);
               firstHeading.addCell(cell3);

        } 
        catch (NullPointerException x) 
        {
               log.debug("Error on Page 1 Header" + x);
        }

        document.add(firstHeading);
        
        

		String strMonth = null;
		String month = null;
		String year = null;
		Date firstDate, lastDate = null;
		String strFirstDate = null, strLastDate = null;
		
        
        PdfPTable secondheading = new PdfPTable(3);
   		secondheading.setWidthPercentage(100);
   		try 
   		{

			PdfPCell processDateCell = new PdfPCell(new Paragraph(null,FontFactory.getFont(FontFactory.HELVETICA, 8)));
   			processDateCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
   			processDateCell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
   			secondheading.addCell(processDateCell);
   			
   			PdfPCell nullCell02 = new PdfPCell(new Paragraph("REG.NO."+ companyParamModel.getRegistrationNr(), FontFactory.getFont(FontFactory.HELVETICA, 8)));
   			nullCell02.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
   			nullCell02.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
   			secondheading.addCell(nullCell02);

   			String strProcDate = null;
   			if(systemParameterModel.getProcessDate() != null)
			{
				strMonth =fileTimeFormat.format(systemParameterModel.getProcessDate()).substring(0,2);
				log.debug("strMonth ==> "+strMonth);
				month= DateUtil.getMonthFullname(Integer.valueOf(strMonth));
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

   			  PdfPCell nullCell03 = new PdfPCell(new Paragraph("Process Date:"  +endDateFormat.format(currentDate).toString(),FontFactory.getFont(FontFactory.HELVETICA, 8)));
	          nullCell03.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
	          nullCell03.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
	          secondheading.addCell(nullCell03); 

	          

   		} 
   		catch (NullPointerException x)
   		{
   			log.debug("Error Finding Company Reg No" + x);
   		}
   		document.add(secondheading);
   		document.add(freeline);
   		
   		
   		
           PdfPTable thirdHeading = new PdfPTable(1);
           thirdHeading.setWidthPercentage(100);

           PdfPCell cell1 = new PdfPCell(new Paragraph(reportNr+" - "+reportName+"-", FontFactory.getFont( FontFactory.HELVETICA, 9, com.itextpdf.text.Font.BOLD)));
           cell1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
           cell1.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
           thirdHeading.addCell(cell1);

           document.add(thirdHeading);
           document.add(freeline);
           
          	// Batch Report Detail
      		PdfPTable batchHeading = new PdfPTable(1);
      		batchHeading.setWidthPercentage(100);

      		PdfPCell batchMemberTitle = new PdfPCell(new Paragraph(memberName+"  "+memberId,FontFactory.getFont(FontFactory.HELVETICA, 9,com.itextpdf.text.Font.BOLD)));
      		batchMemberTitle.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
      		batchMemberTitle.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
      		batchHeading.addCell(batchMemberTitle);

      		document.add(batchHeading);
      		document.add(freeline);
     		
      		PdfPTable batchDetailsHeading = new PdfPTable(7);
       		batchDetailsHeading.setWidthPercentage(100);

       		PdfPCell credBank = new PdfPCell(new Phrase("Creditor Bank",FontFactory.getFont(FontFactory.HELVETICA, 9, com.itextpdf.text.Font.BOLD)));
       		credBank.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
    		credBank.setUseVariableBorders(true);
    		credBank.setBorderColorTop(BaseColor.BLACK);
    		credBank.setBorderColorBottom(BaseColor.BLACK);
    		credBank.setBorderColorLeft(BaseColor.WHITE);
    		credBank.setBorderColorRight(BaseColor.WHITE);
       		batchDetailsHeading.addCell(credBank);
       		
    		PdfPCell crMemId = new PdfPCell(new Phrase("Member No",FontFactory.getFont(FontFactory.HELVETICA, 9, com.itextpdf.text.Font.BOLD)));
    		crMemId.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
    		crMemId.setUseVariableBorders(true);
    		crMemId.setBorderColorTop(BaseColor.BLACK);
    		crMemId.setBorderColorBottom(BaseColor.BLACK);
    		crMemId.setBorderColorLeft(BaseColor.WHITE);
    		crMemId.setBorderColorRight(BaseColor.WHITE);
       		batchDetailsHeading.addCell(crMemId);

    		PdfPCell fileName = new PdfPCell(new Phrase("File Name",FontFactory.getFont(FontFactory.HELVETICA, 9, com.itextpdf.text.Font.BOLD)));
    		fileName.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
    		fileName.setUseVariableBorders(true);
    		fileName.setBorderColorTop(BaseColor.BLACK);
    		fileName.setBorderColorBottom(BaseColor.BLACK);
    		fileName.setBorderColorLeft(BaseColor.WHITE);
    		fileName.setBorderColorRight(BaseColor.WHITE);
       		batchDetailsHeading.addCell(fileName);
       		
       		PdfPCell transType = new PdfPCell(new Phrase("Transaction Type",FontFactory.getFont(FontFactory.HELVETICA, 9, com.itextpdf.text.Font.BOLD)));
       		transType.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
    		transType.setUseVariableBorders(true);
    		transType.setBorderColorTop(BaseColor.BLACK);
    		transType.setBorderColorBottom(BaseColor.BLACK);
    		transType.setBorderColorLeft(BaseColor.WHITE);
    		transType.setBorderColorRight(BaseColor.WHITE);
       		batchDetailsHeading.addCell(transType);
       		
       		PdfPCell totalNrDays = new PdfPCell(new Phrase("No of days Outstanding",FontFactory.getFont(FontFactory.HELVETICA, 9, com.itextpdf.text.Font.BOLD)));
       		totalNrDays.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
    		totalNrDays.setUseVariableBorders(true);
    		totalNrDays.setBorderColorTop(BaseColor.BLACK);
    		totalNrDays.setBorderColorBottom(BaseColor.BLACK);
    		totalNrDays.setBorderColorLeft(BaseColor.WHITE);
    		totalNrDays.setBorderColorRight(BaseColor.WHITE);
       		batchDetailsHeading.addCell(totalNrDays);
       		
       		PdfPCell mndtReqTransId = new PdfPCell(new Phrase("Mandate Request Transaction Id",FontFactory.getFont(FontFactory.HELVETICA, 9, com.itextpdf.text.Font.BOLD)));
       		mndtReqTransId.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
    		mndtReqTransId.setUseVariableBorders(true);
    		mndtReqTransId.setBorderColorTop(BaseColor.BLACK);
    		mndtReqTransId.setBorderColorBottom(BaseColor.BLACK);
    		mndtReqTransId.setBorderColorLeft(BaseColor.WHITE);
    		mndtReqTransId.setBorderColorRight(BaseColor.WHITE);
       		batchDetailsHeading.addCell(mndtReqTransId);
       		
       		
    		PdfPCell serviceId = new PdfPCell(new Phrase("Sub-service ID",FontFactory.getFont(FontFactory.HELVETICA, 9, com.itextpdf.text.Font.BOLD)));
    		serviceId.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
    		serviceId.setUseVariableBorders(true);
    		serviceId.setBorderColorTop(BaseColor.BLACK);
    		serviceId.setBorderColorBottom(BaseColor.BLACK);
    		serviceId.setBorderColorLeft(BaseColor.WHITE);
    		serviceId.setBorderColorRight(BaseColor.WHITE);
       		batchDetailsHeading.addCell(serviceId);
       		
       		document.add(batchDetailsHeading);
       		document.add(freeline);
       		
       		
    		//int nrOutstanding = 1;
       		int respondDate = 0;
       		

       		if(mdtRespOutstandingList!= null && mdtRespOutstandingList.size()> 0)
       		{

       			for(MandateResponseOutstandingPerBankModel mdtResponseOutstandingModel : mdtRespOutstandingList)
       			{
/*
       				Date createdDate = mdtResponseOutstandingModel.getCreatedDate();
       				log.debug("Created Date*******:"+ createdDate );
       				log.debug("Current Date*******:"+ currentDate);
       				Calendar c1= Calendar.getInstance();
       				Calendar c2 =Calendar.getInstance();

       				c1.setTime(createdDate);
       				c2.setTime(currentDate);

       				respondDate =currentDate.getDay() - createdDate.getDay() ;
       				log.info("respondDate*******:"+  respondDate);*/
       				
       				//transactionType =mdtResponseOutstandingModel.getTransType();

       				log.debug("mdtRespOutstandingList*******:"+  mdtRespOutstandingList);
       				
       				

       				PdfPTable batchDetails = new PdfPTable(7);
       				batchDetails.setWidthPercentage(100);

       				PdfPCell creditorBankData = new PdfPCell(new Phrase(mdtResponseOutstandingModel.getCrdMemberName(),FontFactory.getFont(FontFactory.HELVETICA,8, com.itextpdf.text.Font.NORMAL)));
       				creditorBankData.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
       				creditorBankData.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
       				batchDetails.addCell(creditorBankData);

       				PdfPCell memberNoData = new PdfPCell(new Phrase(mdtResponseOutstandingModel.getCrdMemberNo(),FontFactory.getFont(FontFactory.HELVETICA,8, com.itextpdf.text.Font.NORMAL)));
       				memberNoData.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
       				memberNoData.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
       				batchDetails.addCell(memberNoData);

       				PdfPCell fileNameData = new PdfPCell(new Phrase(mdtResponseOutstandingModel.getFileName(),FontFactory.getFont(FontFactory.HELVETICA,8, com.itextpdf.text.Font.NORMAL)));
       				fileNameData.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
       				fileNameData.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
       				batchDetails.addCell(fileNameData);
       				
       				PdfPCell transTpyeData = new PdfPCell(new Phrase(mdtResponseOutstandingModel.getTransType(),FontFactory.getFont(FontFactory.HELVETICA,8, com.itextpdf.text.Font.NORMAL)));
       				transTpyeData.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
       				transTpyeData.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
       				batchDetails.addCell(transTpyeData);

       				PdfPCell totalNumberOutDataCell = new PdfPCell(new Phrase(String.valueOf(mdtResponseOutstandingModel.getNrDaysOutstanding()),FontFactory.getFont(FontFactory.HELVETICA,8, com.itextpdf.text.Font.NORMAL)));
       				totalNumberOutDataCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
       				totalNumberOutDataCell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
       				batchDetails.addCell(totalNumberOutDataCell);

       				PdfPCell mdtReqTransIdData = new PdfPCell(new Phrase(mdtResponseOutstandingModel.getMandateReqTransId(),FontFactory.getFont(FontFactory.HELVETICA,8, com.itextpdf.text.Font.NORMAL)));
       				mdtReqTransIdData.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
       				mdtReqTransIdData.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
       				batchDetails.addCell(mdtReqTransIdData);

       				PdfPCell serviceIdData = new PdfPCell(new Phrase(mdtResponseOutstandingModel.getServiceId(),FontFactory.getFont(FontFactory.HELVETICA,8, com.itextpdf.text.Font.NORMAL)));
       				serviceIdData.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
       				serviceIdData.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
       				batchDetails.addCell(serviceIdData);  

       				document.add(batchDetails);

       			}
       			document.close();
       			
       		}

       		else
       		{

       			System.out.println("THE SUMMARY TOTALS LIST IS EMPTY!!!!!!!!");
       		}

       		
	}






}
