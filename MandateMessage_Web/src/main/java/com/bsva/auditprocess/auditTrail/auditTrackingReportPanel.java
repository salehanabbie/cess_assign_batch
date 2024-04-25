package com.bsva.auditprocess.auditTrail;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import org.apache.wicket.Session;
import org.apache.wicket.datetime.StyleDateConverter;
import org.apache.wicket.datetime.markup.html.form.DateTextField;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.validator.PatternValidator;
import org.apache.wicket.validation.validator.StringValidator;
import com.bsva.models.ClientSessionModel;
import com.bsva.models.MandateDatesModel;
import com.bsva.models.PublicHolidayDateModel;
import com.bsva.models.WebAuditTrackingModel;
import com.bsva.models.WebMdtXsdVersionsModel;
import com.itextpdf.text.DocumentException;

public class auditTrackingReportPanel  extends Panel implements Serializable
{

	/**
	 * @author ElelwaniR
	 */
	public static Logger log = Logger.getLogger(auditTrackingReportPanel.class);
	private static final long serialVersionUID = 1L;
	private MandateDatesModel dateModel;
	private Form auditProcessForm;
	private TextField <String>systemName;
	private TextField<String>userId;
	private TextField<String>actions;
	private DateTextField fromDate,toDate;
	private Button generateReportButton;
	String id;
	String reportNo, reportname, memberNo, internalInd, activeInd;
	String action;
	private ClientSessionModel clientSessionModel;
	public static Session session;
	Date frmDate = null;
	Date tDate = null;
	String reportNr = "MR012";
	String reportName ="Audit Tracking Report";
	WebAuditTrackingModel webAuditTrackingModel;
	private DropDownChoice<String> actionChoice;
	private String selectedIndicator;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	public auditTrackingReportPanel(String id,String action,WebAuditTrackingModel webAuditTrackingModel) 
	{
		super(id);
		this.id = id;
		this.action = action;
		this.webAuditTrackingModel = webAuditTrackingModel;
		initializeComponents();
	}
	
	public auditTrackingReportPanel(String id) 
	{
		super(id);
		initializeComponents();
	}

	private void initializeComponents ()
	{    
		session = getSession();
        clientSessionModel = (ClientSessionModel) session.getAttribute("role");
		auditProcessForm=new Form("auditProcessForm");
		add(auditProcessForm);
		FeedbackPanel feedbackPanel = new FeedbackPanel("feedback_1");
		add(feedbackPanel);
		dateModel = new MandateDatesModel();
		Date d = null;
		if(action == "create")
		{
			d = new Date();
		}
		else
		{
			if(action == "view" || action == "update")
			{
				d = webAuditTrackingModel.getActionDate();
			}
			else
			{
				d = new Date();
			}
		}
		userId=new TextField<String>("userId",new Model<String>(webAuditTrackingModel == null ? "": webAuditTrackingModel.getUserId()));
		userId.setRequired(true);
		userId.add(StringValidator.maximumLength(35));
		auditProcessForm.add(userId);
		List<String> actions = new ArrayList<String>();
		actions.add("CREATE");
		actions.add("UPDATE");
		actionChoice = new DropDownChoice<String>("actionChoice", new Model<String>(selectedIndicator), actions);
		actionChoice.setDefaultModelObject(actionChoice.getChoices().get(0));
		auditProcessForm.add(actionChoice);
		dateModel.setToDate(d);
		toDate = new DateTextField("toDate", new PropertyModel<Date>(dateModel,"toDate"), new StyleDateConverter("M-", true));      
        DatePicker datePicker1 = new DatePicker();
        datePicker1.setShowOnFieldClick(true);
        datePicker1.setAutoHide(true);
        toDate.add(datePicker1);
        toDate.setRequired(true);
        auditProcessForm.add(toDate);
        dateModel.setFromDate(d);
 		fromDate = new DateTextField("fromDate", new PropertyModel<Date>(dateModel, "fromDate"), new StyleDateConverter("M-", true));
        DatePicker datePicker2= new DatePicker();
        datePicker2.setShowOnFieldClick(true);
        datePicker2.setAutoHide(true);
        fromDate.add(datePicker2);
        fromDate.setRequired(true);
        auditProcessForm.add(fromDate);
        generateReportButton = new Button("generateReportButton")
        {
        	 @Override
        	 public void onSubmit()
        	 {
        		 try
        		 {
        			 	 tDate = null; frmDate = null;
        				 WebAuditTrackingModel webAuditTrackingModel = null;
        				 String toDateField = toDate.getValue();
        				 log.info("toDateField: "+toDateField);
        				 String fromDateField = fromDate.getValue();
        				 log.info("fromDateField: "+fromDateField);
        				 if(toDateField.isEmpty() || toDateField == null)
        				 {
        					 error("The To Date cannot be empty. Please select date from the calendar!");
        				 }
        				 else
        				 {
        					 if(fromDateField.isEmpty() || fromDateField == null)
        					 {
        						 error("The From Date cannot be empty. Please select date from the calendar!");
        					 }
        					 else
        					 {
        						 	boolean boolFrmDate = false, boolToDate = false;	
        							 try
                    				 {
        								 sdf.setLenient(false);
                    					 String cnvStrDate = convertDate(toDateField);
                    					 tDate = sdf.parse(cnvStrDate);
                    					 boolToDate = true;
                    				 }
                    				 catch(ParseException pe)
                    				 {
                    					 error("To Date is not a valid date. Please select valid date from calendar!");
                    					 log.info("Error on converting date: "+pe.getMessage());
                    					 boolToDate = false;
                    				 }
        							 catch(Exception e)
                    				 {
                    					 error("To Date is not a valid date. Please select valid date from calendar!");
                    					 log.info("Error on converting date: "+e.getMessage());
                    					 boolToDate = false;
                    				 }
        							 
        							 try
        							 {
        								 sdf.setLenient(false);
                    					 String cnvFromStrDate = convertDate(fromDateField);
                    					 frmDate = sdf.parse(cnvFromStrDate);
                    					 boolFrmDate = true;
                    				 }
                    				 catch(ParseException pe)
                    				 {
                    					 error("From Date is not a valid date. Please select valid date from calendar!");
                    					 log.info("Error on converting date: "+pe.getMessage());
                    					 boolFrmDate = false;
                    				 }
        							 catch(Exception e)
                    				 {
                    					 error("From Date is not a valid date. Please select valid date from calendar!");
                    					 log.info("Error on converting date: "+e.getMessage());
                    					 boolFrmDate = false;
                    				 }
        							 
        							 if(boolToDate && boolFrmDate)
        							 {
        								 if(frmDate.after(tDate))
            							 {
            								 error("To Date cannot be less than From Date. Please select a date after From Date!");
            							 }
            							 else
            							 {
            								 if(tDate.before(frmDate))
            								 {
            									 error("From Date cannot be less than To Date. Please select a date after To Date!");
            								 }
            								 else
            								 {
            									 webAuditTrackingModel = new WebAuditTrackingModel();
            			        				 webAuditTrackingModel.setActionDate(new Date());
            			        				 webAuditTrackingModel.setActionDate(frmDate);
            			        				 String choice = actionChoice.getChoices().get(Integer.parseInt(actionChoice.getValue()));
            			        				 log.info("selAction: "+choice);
            			        				 webAuditTrackingModel.setAction(choice.toLowerCase());
            			        				 webAuditTrackingModel.setUserId(userId.getValue());
            			        				 log.info("Web Model: "+ webAuditTrackingModel);
            			        				 if((userId.getValue()== null || userId.getValue().isEmpty()) && (choice == null || choice.isEmpty()))
            			        				 {
            			        					 error("userId and action are required");
            			        				 }
            			        				 else
            			        				 {
            			        					 String userID =userId.getValue();
            			        					 String action = choice;
            			        					 ManAuditReport manAuditReport = new ManAuditReport(reportNr,reportName);    			
            			        					 boolean runReport = manAuditReport.generateAudTrackingReport(userID, action, tDate, frmDate);
            			        					 if(runReport ==false)
            			        					 {
            			        						 info("No corresponding data for search criteria provided!");
            			        					 }
            			        				 }
            								 }
            							 }
        							 }
        					 }
        				 }
        		 } 
        		 catch (IOException | DocumentException e) 
        		 {
        			 log.error("Error on generation of Audit Report: "+e.getMessage());
        			 error("Error on generation of Audit Report: "+e.getMessage());
        			 e.printStackTrace();
        		 }
        	 }

         };
         generateReportButton.setDefaultFormProcessing(false);
         auditProcessForm.add(generateReportButton);
	}
	
	public String convertDate(String strDate) throws ParseException
	{
		Date currentDate = new java.util.Date(strDate);
		return new SimpleDateFormat("yyyy-MM-dd").format(currentDate);	
	}
}
