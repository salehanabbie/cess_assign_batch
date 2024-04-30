package com.bsva.panels.PHIReports;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.wicket.Session;
import org.apache.wicket.datetime.StyleDateConverter;
import org.apache.wicket.datetime.markup.html.form.DateTextField;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.bsva.commons.model.ReportsNamesModel;
import com.bsva.commons.model.ServicesModel;
import com.bsva.commons.model.SysCisBankModel;
import com.bsva.controller.Controller;
import com.bsva.models.ClientSessionModel;
import com.bsva.models.MandateDatesModel;
import com.bsva.models.WebAuditTrackingModel;
import com.bsva.models.WebReportsNamesModel;
import com.bsva.translator.WebAdminTranslator;
import com.itextpdf.text.DocumentException;

/**
 * 
 * @author DimakatsoN
 *
 */

public class ViewPHIReportsPanel extends Panel implements Serializable  {
	
	public static Logger log = Logger.getLogger(ViewPHIReportsPanel.class);
	private DateTextField fromDate,toDate;
	private Button generateReportButton;
	String id;
	String reportNo, reportname;
	String action;
	private ClientSessionModel clientSessionModel;
	public static Session session;
	Date frmDate = null;
	Date tDate = null;
	WebReportsNamesModel webReportsNamesModel;
	private DropDownChoice<WebReportsNamesModel>  reportNames;
	private List<WebReportsNamesModel> webReportsNamesList;
	private Set<WebReportsNamesModel> selected  = new HashSet<WebReportsNamesModel>();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private Form phiReportForm;
	private MandateDatesModel dateModel;
	Controller controller;
	
	public ViewPHIReportsPanel(String id) {
		super(id);
		initializeComponents();
	}
	
	private void initializeComponents ()
	{
		controller = new Controller();
		session = getSession();
        clientSessionModel = (ClientSessionModel) session.getAttribute("role");
        phiReportForm=new Form("phiReportForm");
		add(phiReportForm);
		dateModel = new MandateDatesModel();
		FeedbackPanel feedbackPanel = new FeedbackPanel("feedback_1");
		add(feedbackPanel);
		loadData();
		
		reportNames = new DropDownChoice<WebReportsNamesModel>("reportNames",new Model<WebReportsNamesModel>(),webReportsNamesList,new ChoiceRenderer<WebReportsNamesModel>());
		reportNames.getChoices().get(0).getReportNr();
		phiReportForm.add(reportNames);
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
        phiReportForm.add(toDate);
       
        dateModel.setFromDate(d);
 		fromDate = new DateTextField("fromDate", new PropertyModel<Date>(dateModel, "fromDate"), new StyleDateConverter("M-", true));
        DatePicker datePicker2= new DatePicker();
        datePicker2.setShowOnFieldClick(true);
        datePicker2.setAutoHide(true);
        fromDate.add(datePicker2);
        fromDate.setRequired(true);
        phiReportForm.add(fromDate);
        
        generateReportButton = new Button("generateReportButton")
        {
        	 @Override
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
      
									 else {
											 if(selected.size() > 1)
											 {
												 error("Please select only one record...");
											 }

											 String choice = reportNames.getChoices().get(Integer.parseInt(reportNames.getValue())).getReportNr();
											if(choice.equalsIgnoreCase("PHIR02"))
											{
												try
												{
													controller.generatePHIR02Report(frmDate,tDate);
												}
												catch(Exception ex)
												{
													log.error("Error on generating <PHIR02> PHIR Mandate Amendments Report:" + ex.getMessage());
													ex.printStackTrace();
												}
											}
									 }
								 }
							 }
						 }
				 }
    	 }

     };
     generateReportButton.setDefaultFormProcessing(false);
     phiReportForm.add(generateReportButton);
}

	
	
	//initialize the list using the model supplying the information

	public void loadData()
	{


		List<ReportsNamesModel>reportnameListfromDB = new ArrayList<ReportsNamesModel>();
		reportnameListfromDB=(List<ReportsNamesModel>)Controller.viewActiveReports();
		Collections.sort(reportnameListfromDB, new reportNoOrderSorter());

		if (reportnameListfromDB.size()>0)
		{
			webReportsNamesList=new ArrayList<WebReportsNamesModel>();

			for (ReportsNamesModel localCommonsModel:reportnameListfromDB){
				WebReportsNamesModel webReportsNamesModel = new WebReportsNamesModel();
				webReportsNamesModel = new WebAdminTranslator().translateCommonsReportNamesToWebModel(localCommonsModel);
				webReportsNamesList.add(webReportsNamesModel);

			}
		}
	}


	private class reportNoOrderSorter implements Comparator<ReportsNamesModel>
	{

		@Override
		public int compare(ReportsNamesModel o1, ReportsNamesModel o2) {
			if(o1.getReportNr() == null && o2.getReportNr() == null)
			{
				return 0;
			}
			else if(o1.getReportNr() == null)
			{
				return -1;
			}
			else if(o2.getReportNr() == null)
			{
				return 1;
			}
			return o1.getReportNr().compareTo(o2.getReportNr());

		}

	}
	
	public String convertDate(String strDate) throws ParseException
	{
		Date currentDate = new java.util.Date(strDate);
		return new SimpleDateFormat("yyyy-MM-dd").format(currentDate);	
	}
}
