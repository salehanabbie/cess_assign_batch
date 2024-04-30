package com.bsva.panels.ManualBilling;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.wicket.Session;
import org.apache.wicket.datetime.StyleDateConverter;
import org.apache.wicket.datetime.markup.html.form.DateTextField;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;

import com.bsva.controller.Controller;
import com.bsva.models.ClientSessionModel;
import com.bsva.models.MandateDatesModel;


/**
 * 
 * @author DimakatsoN
 *
 */
public class ManualBilling  extends Panel implements Serializable{

	private static final long serialVersionUID = 1L;
	public static Logger log = Logger.getLogger(ManualBilling.class);
	private DateTextField fromDate,toDate;
	private Button interchngBillButton,interchngTT2BillButton;
	private Button rtTxnsBillButton,rtTxnsTT2BillButton;
	private Form form;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	Date frmDate = null;
	Date tDate = null;
	String srinpService = "SRINP";
	
	//Session Info 
	private ClientSessionModel clientSessionModel;
	private String userName;
	public static Session session;

	
	
	Controller controller;
	public static String id;
	String action;
	private MandateDatesModel dateModel;

	public ManualBilling(String id) {
		super(id);
		this.id=id;
		initializeComponents();	
		
	}
	
	private void initializeComponents() 
	{
		controller=new Controller();
		form=new Form ("form");
		dateModel = new MandateDatesModel();
		add(form);
		
		session = getSession();
		clientSessionModel = (ClientSessionModel) session.getAttribute("role");
		userName = clientSessionModel.getUsername();
		log.debug("clientSessionModel ==> "+clientSessionModel);
		
		Date d = null;
		if(action == "create")
		{
			d = new Date();
		}
		else
		{
			if(action == "view" || action == "update")
			{
				d = new Date();
			}
			else
			{
				d = new Date();
			}
		}
		dateModel.setToDate(d);
		toDate = new DateTextField("toDate", new PropertyModel<Date>(dateModel,"toDate"), new StyleDateConverter("M-", true));      
        DatePicker datePicker1 = new DatePicker();
        datePicker1.setShowOnFieldClick(true);
        datePicker1.setAutoHide(true);
        toDate.add(datePicker1);
        toDate.setRequired(true);
        
       
        dateModel.setFromDate(d);
 		fromDate = new DateTextField("fromDate", new PropertyModel<Date>(dateModel, "fromDate"), new StyleDateConverter("M-", true));
        DatePicker datePicker2= new DatePicker();
        datePicker2.setShowOnFieldClick(true);
        datePicker2.setAutoHide(true);
        fromDate.add(datePicker2);
        fromDate.setRequired(true);
      
        
        
        interchngBillButton =new Button ("interchngBillButton")
		{
			private static final long serialVersionUID = 1L;

			public void onSubmit()
			{	 
				tDate = null; frmDate = null;
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
     
									 try
										{
										 
										//Interchange Billing from JNL_ACQ TO MDT_AC_OPS_DAILY_BILLING
										//BILLING ~~~~~>  ST012 / TT1 / 5501 OR 5502/ SUCCESSFUL & UNSUCC
											controller.generateTT1Pain009(frmDate,tDate);
											
										//BILLING ~~~~~>  MANIR / TT1 / 5501 / SUCCESSFUL & UNSUCC
											controller.generateTT1MANIRData(frmDate,tDate);
											
										//BILLING ~~~~~>  MANIR / TT3 / 5502 SUCCESSFUL & UNSUCC
											controller.generateTT3ManirPain009Succ(frmDate,tDate);
											
										//BILLING ~~~~~>  MANDR / TT1 / 5503 	SUCCESSFUL & UNSUCC	
											controller.generateTT1MandrPain010Succ(frmDate,tDate);
											
										//BILLING ~~~~~>  MANIR / TT1 / 5503 SUCCESSFUL & UNSUCC
											controller.generateTT1ManamManirData(frmDate,tDate);
										//BILLING ~~~~~>  MANIR / TT3 / 5504 SUCCESSFUL & UNSUCC	
											controller.generateTT3ManIrPain010Succ(frmDate,tDate);
										//BILLING ~~~~~>  MANIR / TT1 / 5505 SUCCESSFUL & UNSUCC
											controller.generateTT1ManIrPain011Succ(frmDate,tDate);
											
									//Interchange Billing Export 
											controller.generateBillingExport(frmDate,tDate,srinpService);
											info("Realtime Interchange data has been pushed successfully ...");

											
								
											
										}
										catch(Exception ex)
										{
											log.error("Error on generating <PHIR01> PHIR Mandate Initiations Report:" + ex.getMessage());
											ex.printStackTrace();
										}
								 }
							 
							 }
				 
					 }
				 }
			} 
		};

		rtTxnsBillButton =new Button ("rtTxnsBillButton")
		{
			private static final long serialVersionUID = 1L;

			public void onSubmit()
			{	

				tDate = null; frmDate = null;
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
    
									 try
										{
											controller.generateRtTxnsBill(frmDate,tDate);
											controller.pushRtTxnsBillToBillowner(frmDate,tDate);
											info("Realtime Transactional data has been pushed successfully ...");
										}
										catch(Exception ex)
										{
											log.error("Error on generating TXN BILLING:" + ex.getMessage());
											error("An error occured on pushing Realtime Transactional data!");
											ex.printStackTrace();
										}
								 }
							 
							 }
				 
					 }
				 }
			} 
			
		};
		
        interchngTT2BillButton =new Button ("interchngTT2BillButton")
		{
			private static final long serialVersionUID = 1L;

			public void onSubmit()
			{	 
				tDate = null; frmDate = null;
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
     
									 try 
										{
										 
										// TT2 Interchange Billing
										 controller.generateTT2InterchangeBill(frmDate,tDate);
										 controller.generateSRINPinterchangeBill(frmDate,tDate);
										 controller.generateMANRTinterchangeBill(frmDate,tDate);
										 //TT2 Interchange Billing Export 
										controller.generateBillingExport(frmDate,tDate,srinpService);
										info("TT2 Interchange Billing data has been pushed successfully ...");
											
								
											
										}
										catch(Exception ex)
										{
											log.error("Error on generating <PHIR01> PHIR Mandate Initiations Report:" + ex.getMessage());
											ex.printStackTrace();
										}
								 }
							 
							 }
				 
					 }
				 }
			} 
		};

		rtTxnsTT2BillButton =new Button ("rtTxnsTT2BillButton")
		{
			private static final long serialVersionUID = 1L;

			public void onSubmit()
			{	

				tDate = null; frmDate = null;
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
    
									 try
										{
											controller.generateTT2TxnsBilling(frmDate,tDate);
											controller.pushTT2TxnsBillToBillowner(frmDate,tDate);
											info("TT2 Transactional data has been pushed successfully ...");
										}
										catch(Exception ex)
										{
											log.error("Error on generating TT2 TXN BILLING:" + ex.getMessage());
											error("An error occured on pushing TT2 Transactional data!");
											ex.printStackTrace();
										}
								 }
							 
							 }
				 
					 }
				 }
			} 
			
		};
		
		form.add(toDate);
		form.add(fromDate);
		form.add(interchngBillButton);
		form.add(rtTxnsBillButton);
		form.add(interchngTT2BillButton);
		form.add(rtTxnsTT2BillButton);
		
		form.add(new FeedbackPanel("feedbackPanel"));
        
	}
	
	public String convertDate(String strDate) throws ParseException
	{
		Date currentDate = new java.util.Date(strDate);
		return new SimpleDateFormat("yyyy-MM-dd").format(currentDate);	
	}

}
