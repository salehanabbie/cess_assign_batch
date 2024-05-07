package com.bsva.auditprocess.auditTrail;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.handler.resource.ResourceStreamRequestHandler;
import org.apache.wicket.util.resource.FileResourceStream;
import com.itextpdf.text.Element;
import com.bsva.commons.model.AuditTrackingSqlModel;
import com.bsva.commons.model.ReportsNamesModel;
import com.bsva.commons.model.SysctrlCompParamModel;
import com.bsva.commons.model.SystemParameterModel;
import com.bsva.controller.Controller;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.DocumentException;

public class ManAuditReport 
{
	private String downloaddirectory ="/home/opsjava/Delivery/Cession_Assign/Reports/";
	public static Logger log = Logger.getLogger(ManAuditReport.class);
	Controller controller = new Controller();
	String reportName, reportNr;
	List<AuditTrackingSqlModel> auditTackingList;
	AuditTrackingSqlModel auditTrackingModel ;
	
	public ManAuditReport(String reportNr, String reportName)
	{
		this.reportNr = reportNr;
		this.reportName = reportName;
	}
	
	public boolean  generateAudTrackingReport(String userId,String action, Date fromDate, Date toDate) throws DocumentException, IOException 
	{
		log.info("in the generate report method ");
		ReportsNamesModel reportsModel =new ReportsNamesModel();
		auditTackingList = new ArrayList<AuditTrackingSqlModel>();
		if(reportNr.equalsIgnoreCase("MR012"))
		{
			auditTackingList =(List<AuditTrackingSqlModel>)controller.retrieveAuditTracking(userId,fromDate,toDate,action);
			if(auditTackingList != null && auditTackingList.size() >0 )
			{
				for(AuditTrackingSqlModel auditTrackingModel : auditTackingList)
				{
					generateReportColumns( reportName, reportNr, auditTackingList);
				}
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			log.info("Mandate Tracking Report does not exist");
			return false;
		}
	}
	
   private void generateReportColumns(String reportname, String reportNo, List<AuditTrackingSqlModel> auditTrackingList) throws DocumentException, IOException 
   {
	    log.info("The report cloumns method ****************");
		DecimalFormat df = new DecimalFormat("### ### ### ### ### ##0.00");
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat dateTimeFormat = new SimpleDateFormat("yyyyMMdd_HH:mm:ss");
		DateFormat fileTimeFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
		Date currentDate = new Date();
		int pageNo = 1;
		String files = downloaddirectory+reportNo+"-"+reportname+"_"+fileTimeFormat.format(currentDate).toString()+".pdf";
		Document document = new Document(PageSize.A4.rotate());
		FileOutputStream fileOutputStream = new FileOutputStream(files);
		PdfWriter writer = null;
		writer = PdfWriter.getInstance(document, fileOutputStream);
		writer.open();
		document.open();
		File file = new File(files);
		ResourceStreamRequestHandler target = new ResourceStreamRequestHandler(new FileResourceStream(new org.apache.wicket.util.file.File(file)));
		target.setFileName(files);
		RequestCycle.get().scheduleRequestHandlerAfterCurrent(target);
		document.open();
		
		SysctrlCompParamModel companyParamModel = new SysctrlCompParamModel();
		companyParamModel = (SysctrlCompParamModel) controller.retrieveCompanyParameters();
		
		SystemParameterModel systemParameterModel = new SystemParameterModel();
		systemParameterModel = (SystemParameterModel) controller.retrieveWebActiveSysParameter();
		
		PdfPTable freeline = new PdfPTable(1);
		freeline.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		freeline.setWidthPercentage(100);
		freeline.addCell(new PdfPCell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 8)))).setBorderColor(BaseColor.WHITE);
	
		PdfPTable firstHeading = new PdfPTable(3);
		firstHeading.setWidthPercentage(100);
		try 
		{
			PdfPCell cell1 = new PdfPCell(new Paragraph("Date: "+ dateTimeFormat.format(currentDate)+   "  "  ,FontFactory.getFont(FontFactory.HELVETICA, 8)));
			cell1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
			cell1.setBorder(Rectangle.NO_BORDER);
			firstHeading.addCell(cell1);
	
			PdfPCell cell2 = new PdfPCell(new Paragraph("" + companyParamModel.getCompName(), FontFactory.getFont(FontFactory.HELVETICA, 9, com.itextpdf.text.Font.BOLD)));
			cell2.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			cell2.setBorder(Rectangle.NO_BORDER);
			firstHeading.addCell(cell2);
	
			PdfPCell cell3 = new PdfPCell(new Paragraph("Page:  " + pageNo,FontFactory.getFont(FontFactory.HELVETICA, 8)));
			cell3.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
			cell3.setBorder(Rectangle.NO_BORDER);
			firstHeading.addCell(cell3);
		} 
		catch (NullPointerException x)
		{
			log.debug("Error on Page 1 Header" + x);
		}
		document.add(firstHeading);
		PdfPTable secondHeading = new PdfPTable(3);
		secondHeading.setWidthPercentage(100);
		try 
		{
			PdfPCell nullCell001 = new PdfPCell(new Paragraph(null,FontFactory.getFont(FontFactory.HELVETICA, 8)));
			nullCell001.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
			nullCell001.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
			secondHeading.addCell(nullCell001);
	
			PdfPCell regNoCell = new PdfPCell(new Paragraph("REG. NO. "+ companyParamModel.getRegistrationNr(),FontFactory.getFont(FontFactory.HELVETICA, 8)));
			regNoCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			regNoCell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
			secondHeading.addCell(regNoCell);
	
			String strProcDate = null;
			if(systemParameterModel.getProcessDate() != null)
			{
				 strProcDate = dateFormat.format(systemParameterModel.getProcessDate());
			}
	          PdfPCell nullCell03 = new PdfPCell(new Paragraph("ProcessDate: " + strProcDate,  FontFactory.getFont(FontFactory.HELVETICA, 8)));
	          nullCell03.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
	          nullCell03.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
	          secondHeading.addCell(nullCell03);
		} 
		catch (NullPointerException x) 
		{
			log.debug("Error Finding Company Reg No" + x);
		}
		document.add(secondHeading);
		document.add(freeline);
		
		 PdfPTable reportNameTb = new PdfPTable(1);
	     reportNameTb.setWidthPercentage(100);
	     
	     String batchMemTitle;
	     PdfPCell cell1 = new PdfPCell(new Paragraph(reportNr+ "-"+ reportName, FontFactory.getFont( FontFactory.HELVETICA, 9, com.itextpdf.text.Font.BOLD)));
	     cell1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
	     cell1.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
	     reportNameTb.addCell(cell1);
	     document.add(reportNameTb);
	     document.add(freeline);
    
	     PdfPTable dataheaders = new PdfPTable(7);
		 dataheaders.setWidthPercentage(100);
		 dataheaders.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		 
		 PdfPCell userIdCell = new PdfPCell(new Phrase("User ID", FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
		 userIdCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		 userIdCell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
	     dataheaders.addCell(userIdCell);
	        
	     PdfPCell actionDateCell = new PdfPCell(new Phrase("Action Date", FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
	     actionDateCell.setHorizontalAlignment(Element.ALIGN_LEFT);
	     actionDateCell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
	     dataheaders.addCell(actionDateCell);
	     
	     PdfPCell tableNameCell = new PdfPCell(new Phrase("Screen Name", FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
	     tableNameCell.setHorizontalAlignment(Element.ALIGN_LEFT);
	     tableNameCell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
	     dataheaders.addCell(tableNameCell);
	     
	     PdfPCell columnNameCell = new PdfPCell(new Phrase("Field Name", FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
	     columnNameCell.setHorizontalAlignment(Element.ALIGN_LEFT);
	     columnNameCell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
	     dataheaders.addCell(columnNameCell);
	     
	     PdfPCell oldValueCell = new PdfPCell(new Phrase("Old Value", FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
	     oldValueCell.setHorizontalAlignment(Element.ALIGN_LEFT);
	     oldValueCell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
	     dataheaders.addCell(oldValueCell);
	     
	     PdfPCell newValueCell = new PdfPCell(new Phrase("New Value", FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
	     newValueCell.setHorizontalAlignment(Element.ALIGN_LEFT);
	     newValueCell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
	     dataheaders.addCell(newValueCell);
	     
	     PdfPCell actionCell = new PdfPCell(new Phrase("Action", FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.BOLD)));
	     actionCell.setHorizontalAlignment(Element.ALIGN_LEFT);
	     actionCell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
	     dataheaders.addCell(actionCell);
	     
	     document.add(dataheaders);
		 document.add(freeline);
		 if(auditTackingList.size() > 0)
		 {
			 for(AuditTrackingSqlModel auditTrackingModel : auditTackingList)
			 {
				PdfPTable popDataTable = new PdfPTable(7);
				popDataTable.setWidthPercentage(100);

				PdfPCell userID = new PdfPCell(new Phrase(auditTrackingModel.getUserId(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.NORMAL)));
				userID.setHorizontalAlignment(Element.ALIGN_LEFT);
				userID.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
				popDataTable.addCell(userID);
				
				PdfPCell toDate = new PdfPCell(new Phrase(dateFormat.format(auditTrackingModel.getActionDate()),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.NORMAL)));
				toDate.setHorizontalAlignment(Element.ALIGN_LEFT);
				toDate.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
				popDataTable.addCell(toDate);

				PdfPCell screenName = new PdfPCell(new Phrase(auditTrackingModel.getTableName(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.NORMAL)));
				screenName.setHorizontalAlignment(Element.ALIGN_LEFT);
				screenName.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
				popDataTable.addCell(screenName);
				
				PdfPCell fieldName = new PdfPCell(new Phrase(auditTrackingModel.getColumnName(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.NORMAL)));
				fieldName.setHorizontalAlignment(Element.ALIGN_LEFT);
				fieldName.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
				popDataTable.addCell(fieldName);
				
				PdfPCell oldValue = new PdfPCell(new Phrase(auditTrackingModel.getOldValue(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.NORMAL)));
				oldValue.setHorizontalAlignment(Element.ALIGN_LEFT);
				oldValue.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
				popDataTable.addCell(oldValue);
				
				PdfPCell newValue = new PdfPCell(new Phrase(auditTrackingModel.getNewValue(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.NORMAL)));
				newValue.setHorizontalAlignment(Element.ALIGN_LEFT);
				newValue.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
				popDataTable.addCell(newValue);
				
				PdfPCell action = new PdfPCell(new Phrase(auditTrackingModel.getAction(),FontFactory.getFont(FontFactory.HELVETICA, 8,com.itextpdf.text.Font.NORMAL)));
				action.setHorizontalAlignment(Element.ALIGN_LEFT);
				action.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
				popDataTable.addCell(action);
				
				document.add(popDataTable);
		 }
	 }
	document.close();
  }
}
