package com.bsva.panels.reports;

import com.bsva.commons.model.ReportsNamesModel;
import com.bsva.commons.properties.PropertyCacheFile;
import com.bsva.controller.Controller;
import com.bsva.models.ClientSessionModel;
import com.bsva.models.MandateDatesModel;
import com.bsva.models.WebReportsNamesModel;
import com.bsva.translator.WebAdminTranslator;
import com.itextpdf.text.DocumentException;
import java.io.IOException;
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
import org.apache.wicket.ajax.IAjaxIndicatorAware;
import org.apache.wicket.datetime.StyleDateConverter;
import org.apache.wicket.datetime.markup.html.form.DateTextField;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

public class ReportPanel extends Panel implements IAjaxIndicatorAware {

  public static Logger log = Logger.getLogger(ReportPanel.class);
  private static final long serialVersionUID = 1L;
  private Form form;
  public Controller controller = new Controller();
  private Button generateButton, showDateButton;
  private DropDownChoice<WebReportsNamesModel> reportNames;
  private DateTextField fromDate, toDate;
  private WebReportsNamesModel webReportsNamesModel;
  private List<WebReportsNamesModel> webReportsNamesList;
  private Set<WebReportsNamesModel> selected = new HashSet<WebReportsNamesModel>();
  private PropertyCacheFile propertyCache;
  Label fromDateLbl, toDateLbl;

  String id, action;

  private ClientSessionModel clientSessionModel;
  public static Session session;

  private MandateDatesModel dateModel;

  Date date = new Date();

  public ReportPanel(String id) throws ParseException {
    super(id);
    initializeComponents();
  }


  public ReportPanel(String id, WebReportsNamesModel webReportsNamesModel) {
    super(id);
    this.webReportsNamesModel = webReportsNamesModel;
    initializeComponents();
  }


  private void initializeComponents() {


    session = getSession();
    clientSessionModel = (ClientSessionModel) session.getAttribute("role");
    dateModel = new MandateDatesModel();


    form = new Form("reportForm");
    add(form);

    FeedbackPanel feedbackPanel = new FeedbackPanel("feedback_1");
    add(feedbackPanel);
    loadData();


    reportNames =
        new DropDownChoice<WebReportsNamesModel>("reportNames", new Model<WebReportsNamesModel>(),
            webReportsNamesList, new ChoiceRenderer<WebReportsNamesModel>());
    reportNames.getChoices().get(0).getReportNr();
    form.add(reportNames);

    fromDateLbl = new Label("fromDateLabel", "<b>From Date:</b>");
    fromDateLbl.setEscapeModelStrings(false);
    fromDateLbl.setVisibilityAllowed(false);

    toDateLbl = new Label("toDateLabel", "<b>To Date:</b>");
    toDateLbl.setEscapeModelStrings(false);
    toDateLbl.setVisibilityAllowed(false);

    dateModel.setFromDate(date);
    fromDate = new DateTextField("fromDate", new PropertyModel<Date>(dateModel, "fromDate"),
        new StyleDateConverter("M-", true));
    DatePicker datePicker2 = new DatePicker();
    datePicker2.setShowOnFieldClick(true);
    datePicker2.setAutoHide(true);
    fromDate.add(datePicker2);
    fromDate.setRequired(true);
    fromDate.setVisibilityAllowed(false);

    dateModel.setToDate(date);
    toDate = new DateTextField("toDate", new PropertyModel<Date>(dateModel, "toDate"),
        new StyleDateConverter("M-", true));
    DatePicker datePicker1 = new DatePicker();
    datePicker1.setShowOnFieldClick(true);
    datePicker1.setAutoHide(true);
    toDate.add(datePicker1);
    toDate.setRequired(true);
    toDate.setVisibilityAllowed(false);

    form.add(fromDateLbl);
    form.add(fromDate);
    form.add(toDateLbl);
    form.add(toDate);

    generateButton = new Button("generateButton") {

      @Override
      public void onSubmit() {

        try {
          if (selected.size() > 1) {
            error("Please select only one record...");
          }

          String choice =
              reportNames.getChoices().get(Integer.parseInt(reportNames.getValue())).getReportNr();
          //String choice =webReportsNamesModel.getReportName();
          if (choice.equalsIgnoreCase("MR009")) {
            EndofDayReport endofDayReport = new EndofDayReport();
            try {
              endofDayReport.generateReport(
                  reportNames.getChoices().get(Integer.parseInt(reportNames.getValue()))
                      .getReportNr(),
                  reportNames.getChoices().get(Integer.parseInt(reportNames.getValue()))
                      .getReportName());
            } catch (NumberFormatException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
            } catch (IOException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
            } catch (DocumentException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();

            }
          } else if (choice.equalsIgnoreCase("MR005")) {
            //2015/10/29 - Saleha R.
            //GENERATE MR007 - Online Outstanding Responses Report
            try {
              MandatesMessagingReport mandatesMessagingReport = new MandatesMessagingReport();
              mandatesMessagingReport.generateReport(
                  reportNames.getChoices().get(Integer.parseInt(reportNames.getValue()))
                      .getReportNr(),
                  reportNames.getChoices().get(Integer.parseInt(reportNames.getValue()))
                      .getReportName());
            } catch (Exception e) {
              log.error("Error on generating Oustanding Responses Report: " + e.getMessage());
              e.printStackTrace();
            }
          }    // else if (choice.equalsIgnoreCase("Batch - Late Responses Report"))
          else if (choice.equalsIgnoreCase("MR006")) {
            //2015/10/29 - Saleha R.
            //GENERATE MR006 - Batch Outstanding Responses Report
            try {
              OutstandingResponsesReport outstandingResponsesReport =
                  new OutstandingResponsesReport(
                      reportNames.getChoices().get(Integer.parseInt(reportNames.getValue()))
                          .getReportNr(),
                      reportNames.getChoices().get(Integer.parseInt(reportNames.getValue()))
                          .getReportName());
              outstandingResponsesReport.generateOustandingCreditorReport();
            } catch (Exception e) {
              log.error("Error on generating Oustanding Responses Report: " + e.getMessage());
              e.printStackTrace();
            }
          } else if (choice.equalsIgnoreCase("MR007")) {
            //2015/10/29 - Saleha R.
            //GENERATE MR007 - Online Outstanding Responses Report
            try {
              OutstandingResponsesReport outstandingResponsesReport =
                  new OutstandingResponsesReport(
                      reportNames.getChoices().get(Integer.parseInt(reportNames.getValue()))
                          .getReportNr(),
                      reportNames.getChoices().get(Integer.parseInt(reportNames.getValue()))
                          .getReportName());
              outstandingResponsesReport.generateOustandingCreditorReport();
            } catch (Exception e) {
              log.error("Error on generating Oustanding Responses Report: " + e.getMessage());
              e.printStackTrace();
            }

          } else if (choice.equalsIgnoreCase("BSCA03")) {
            OutOfBalanceReport outOfBalanceReport = new OutOfBalanceReport();
            try {
              outOfBalanceReport.generateReport(
                  reportNames.getChoices().get(Integer.parseInt(reportNames.getValue()))
                      .getReportNr(),
                  reportNames.getChoices().get(Integer.parseInt(reportNames.getValue()))
                      .getReportName());
            } catch (Exception e) {
              log.error("Error on generating Out of Balance Report:" + e.getMessage());
              e.printStackTrace();
            }
          } else if (choice.equalsIgnoreCase("MR005")) {
            MandatesMessagingReport mandatesMessagingReport = new MandatesMessagingReport();
            try {
              mandatesMessagingReport.generateReport(
                  reportNames.getChoices().get(Integer.parseInt(reportNames.getValue()))
                      .getReportNr(),
                  reportNames.getChoices().get(Integer.parseInt(reportNames.getValue()))
                      .getReportName());
            } catch (Exception e) {
              log.error("Error on generating Mandate Messaging Report:" + e.getMessage());
            }
          } else if (choice.equalsIgnoreCase("MR004")) {
            UserReport userReport = new UserReport();
            try {
              userReport.generateReport(
                  reportNames.getChoices().get(Integer.parseInt(reportNames.getValue()))
                      .getReportNr(),
                  reportNames.getChoices().get(Integer.parseInt(reportNames.getValue()))
                      .getReportName());
            } catch (Exception e) {
              log.error("Error on generating User Report:" + e.getMessage());
            }
          } else if (choice.equalsIgnoreCase("MR014")) {
            FileStatusReport fileStatusReport = new FileStatusReport();
            try {
              fileStatusReport.generateReport(
                  reportNames.getChoices().get(Integer.parseInt(reportNames.getValue()))
                      .getReportNr(),
                  reportNames.getChoices().get(Integer.parseInt(reportNames.getValue()))
                      .getReportName());
            } catch (Exception e) {
              log.error("Error on generating Mandate Matching Report:" + e.getMessage());
            }
          } else if (choice.equalsIgnoreCase("MR016")) {
            EotFileReport eotFileReport = new EotFileReport();
            try {
              eotFileReport.generateReport(
                  reportNames.getChoices().get(Integer.parseInt(reportNames.getValue()))
                      .getReportNr(),
                  reportNames.getChoices().get(Integer.parseInt(reportNames.getValue()))
                      .getReportName());
            } catch (Exception e) {
              log.error("Error on generating Eot File Report:" + e.getMessage());
            }
          } else if (choice.equalsIgnoreCase("PSMD01")) {
            Date psmd01FromDate = new SimpleDateFormat("MMMM dd, yyyy").parse(fromDate.getValue());
            try {
              controller.generatePasaBatchOutstandingResponses(psmd01FromDate);
            } catch (Exception ex) {
              log.error("Error on generating <PSMD01> Pasa Batch Outstanding Responses Report:" +
                  ex.getMessage());
              ex.printStackTrace();
            }

            //						2017-12-04 SalehaR		Remove Web method call for reports
            //						AcPasaOutstandingResponsesSummary
            //						acPasaOutstandingResponsesSummary = new
            //						AcPasaOutstandingResponsesSummary();
            //						try{
            //							acPasaOutstandingResponsesSummary.generateReport
            //							(reportNames.getChoices().get(Integer.parseInt
            //							(reportNames.getValue())).getReportNr(),reportNames
            //							.getChoices().get(Integer.parseInt(reportNames
            //							.getValue())).getReportName());
            //
            //						}
            //						catch(Exception e)
            //						{
            //							log.error("Error on generating
            //							AcPasaOutstandingResponsesSummary Report:" + e
            //							.getMessage());
            //							e.printStackTrace();
            //						}
          } else if (choice.equalsIgnoreCase("PBMD01")) {
            Date pbmd01FromDate = new SimpleDateFormat("MMMM dd, yyyy").parse(fromDate.getValue());
            try {
              controller.generatePerBankOutstandingResponses(pbmd01FromDate);
            } catch (Exception ex) {
              log.error("Error on generating <PBMD01> Per Bank Outstanding Responses Report:" +
                  ex.getMessage());
              ex.printStackTrace();
            }
            //						2017-12-04 SalehaR		Remove Web method call for reports
            //						try{
            //							MandateResponseOutstandingPerBank
            //							mandateResponseOutstandingPerBank = new
            //							MandateResponseOutstandingPerBank(reportNames
            //							.getChoices().get(Integer.parseInt(reportNames
            //							.getValue())).getReportNr(),reportNames.getChoices()
            //							.get(Integer.parseInt(reportNames.getValue()))
            //							.getReportName());
            //							mandateResponseOutstandingPerBank
            //							.generateMandateResponseOutstandingPerBank();
            //
            //							MandateResponseOutstandingPerBankCSVReport
            //							mandateResponseOutstandingPerBankCSVReport = new
            //							MandateResponseOutstandingPerBankCSVReport(reportNames
            //							.getChoices().get(Integer.parseInt(reportNames
            //							.getValue())).getReportNr(),reportNames.getChoices()
            //							.get(Integer.parseInt(reportNames.getValue()))
            //							.getReportName());
            //							mandateResponseOutstandingPerBankCSVReport
            //							.generateMandateResponseOutstandingPerBankCsv();
            //
            //						}
            //						catch(Exception e)
            //						{
            //							log.error("Error on generating
            //							MandateResponseOutstandingPerBank
            //							/MandateResponseOutstandingPerBankCSVReport:" + e
            //							.getMessage());
            //							e.printStackTrace();
            //						}
          } else if (choice.equalsIgnoreCase("PSMD06")) {
            Date psmd06FromDate = new SimpleDateFormat("MMMM dd, yyyy").parse(fromDate.getValue());
            Date psmd06ToDate = new SimpleDateFormat("MMMM dd, yyyy").parse(toDate.getValue());
            try {
              controller.generatePasaBatchAmendmentsReport(psmd06FromDate, psmd06ToDate);
            } catch (Exception ex) {
              log.error("Error on generating <PSMD06> Batch Amendments Report:" + ex.getMessage());
              ex.printStackTrace();
            }

            //						2017-12-04 SalehaR		Remove Web method call for reports
            //						MandateAmendmentReport mandateAmendmentReport = new
            //						MandateAmendmentReport();
            //						try{
            //							mandateAmendmentReport.generateReport(reportNames
            //							.getChoices().get(Integer.parseInt(reportNames
            //							.getValue())).getReportNr(),reportNames.getChoices()
            //							.get(Integer.parseInt(reportNames.getValue()))
            //							.getReportName());
            //						}
            //						catch(Exception e)
            //						{
            //							log.error("Error on generating Reason Codes Report:" + e
            //							.getMessage());
            //							e.printStackTrace();
            //						}
          } else if (choice.equalsIgnoreCase("PSMD04")) {
            Date psmd04FromDate = new SimpleDateFormat("MMMM dd, yyyy").parse(fromDate.getValue());
            try {
              controller.generatePasaBatchRejections(psmd04FromDate);
            } catch (Exception ex) {
              log.error("Error on generating <PSMD04> Batch Rejection Report:" + ex.getMessage());
              ex.printStackTrace();
            }

            //						2017-12-04 SalehaR		Remove Web method call for reports
            //						PasaMndtRejectionSummaryReport mndtRejectionSummaryReport =
            //						new PasaMndtRejectionSummaryReport();
            //						try{
            //							mndtRejectionSummaryReport.generateReport(reportNames
            //							.getChoices().get(Integer.parseInt(reportNames
            //							.getValue())).getReportNr(),reportNames.getChoices()
            //							.get(Integer.parseInt(reportNames.getValue()))
            //							.getReportName());
            //						}
            //						catch(Exception e)
            //						{
            //							log.error("Error on generating Reason Codes Report:" + e
            //							.getMessage());
            //							e.printStackTrace();
            //						}
          } else if (choice.equalsIgnoreCase("MR018")) {
            try {
              controller.generateBatchBillableTxnReport();
            } catch (Exception ex) {
              log.error("Error on generating <MR018> Batch Billable Txn Report:" + ex.getMessage());
              ex.printStackTrace();
            }
            //						2017-12-04 SalehaR		Remove Web method call for reports
            //						BillingTransactionReport billingTransactionReport = new
            //						BillingTransactionReport();
            //						try{
            //
            //							billingTransactionReport.generateReport(reportNames
            //							.getChoices().get(Integer.parseInt(reportNames
            //							.getValue())).getReportNr(),reportNames.getChoices()
            //							.get(Integer.parseInt(reportNames.getValue()))
            //							.getReportName());
            //						}
            //						catch(Exception e)
            //						{
            //							log.error("Error on generating <MR018>
            //							billingTransactionReport:" + e.getMessage());
            //						}
          } else if (choice.equalsIgnoreCase("PBMD04")) {
            try {
              controller.generateBatchBillableTxnCreditor();
            } catch (Exception ex) {
              log.error("Error on generating <PBMD04> Batch Billable Txn Report - Creditor:" +
                  ex.getMessage());
              ex.printStackTrace();
            }
            //						2017-12-04 SalehaR		Remove Web method call for reports
            //						try{
            //							MandateDailyTransCreditorReport
            //							mandateDailyTransCreditorReport = new
            //							MandateDailyTransCreditorReport(reportNames.getChoices
            //							().get(Integer.parseInt(reportNames.getValue()))
            //							.getReportNr(),reportNames.getChoices().get(Integer
            //							.parseInt(reportNames.getValue())).getReportName());
            //							mandateDailyTransCreditorReport
            //							.generateMandateDailyTransCreditorReport();
            //							log.info("in the report panel for creditor report");
            //						}
            //						catch(Exception e)
            //						{
            //							log.error("Error on generating <PBMD04>
            //							MandateDailyTransCreditorReport:" + e.getMessage());
            //						}

          } else if (choice.equalsIgnoreCase("PBMD05")) {
            try {
              controller.generateBatchBillableTxnDebtor();
            } catch (Exception ex) {
              log.error("Error on generating <PBMD05> Batch Billable Txn Report - Debtor:" +
                  ex.getMessage());
              ex.printStackTrace();
            }
            //						2017-12-04 SalehaR		Remove Web method call for reports
            //						try{
            //							MandateDailyTransDebtorReport mandateDailyTransReport =
            //							new MandateDailyTransDebtorReport(reportNames
            //							.getChoices().get(Integer.parseInt(reportNames
            //							.getValue())).getReportNr(),reportNames.getChoices()
            //							.get(Integer.parseInt(reportNames.getValue()))
            //							.getReportName());
            //							mandateDailyTransReport.generateMandateDailyTransReport();
            //						}
            //						catch(Exception e)
            //						{
            //							log.error("Error on generating <PBMD05>
            //							MandateDailyTransDebtorReport:" + e.getMessage());
            //						}

          } else if (choice.equalsIgnoreCase("MR020")) {
            Date mr020FromDate = new SimpleDateFormat("MMMM dd, yyyy").parse(fromDate.getValue());
            Date mr020ToDate = new SimpleDateFormat("MMMM dd, yyyy").parse(toDate.getValue());
            try {
              controller.generateBatchProdVolumesReport(mr020FromDate, mr020ToDate);
            } catch (Exception ex) {
              log.error("Error on generating <MR020> Batch Prod Stats:" + ex.getMessage());
              ex.printStackTrace();
            }
          } else if (choice.equalsIgnoreCase("BSCA01")) {
            Date mr022Date = new SimpleDateFormat("MMMM dd, yyyy").parse(fromDate.getValue());
            try {
              controller.generateDailyBatchProdVolumesReport(mr022Date);
            } catch (Exception ex) {
              log.error("Error on generating <BSCA01> Daily Batch Prod Stats:" + ex.getMessage());

              ex.printStackTrace();
            }
          } else if (choice.equalsIgnoreCase("PBMD08")) {
            Date pbmd08Date = new SimpleDateFormat("MMMM dd, yyyy").parse(fromDate.getValue());
            try {
              controller.generatePerBank5DayOutstanding(pbmd08Date);
            } catch (Exception ex) {
              log.error(
                  "Error on generating <PBMD08> Real Time Outstanding Resp:" + ex.getMessage());
              ex.printStackTrace();
            }
          } else if (choice.equalsIgnoreCase("PBBIL05")) {
            Date pbbil05FromDate = new SimpleDateFormat("MMMM dd, yyyy").parse(fromDate.getValue());
            Date pbbil05ToDate = new SimpleDateFormat("MMMM dd, yyyy").parse(toDate.getValue());

            try {
              controller.generateBatchTxnBillMonthlyReport(pbbil05FromDate, pbbil05ToDate, true);
            } catch (Exception ex) {
              log.error("Error on generating <PBBIL05> Batch Txn Bill Report:" + ex.getMessage());
            }
          } else if (choice.equalsIgnoreCase("PSMD08")) {
            try {
              Date psmd08fromDate =
                  new SimpleDateFormat("MMMM dd, yyyy").parse(fromDate.getValue());
              log.info("DATE VALUE IS ====> : " + psmd08fromDate);
              controller.generateDailyFileStatsReport(psmd08fromDate);
            } catch (Exception ex) {
              log.error(
                  "Error on generating <PSMD08> BSA Daily File Stats Report:" + ex.getMessage());
            }
          } else if (choice.equalsIgnoreCase("PBMD12")) {
            Date pbmd12Date = new SimpleDateFormat("MMMM dd, yyyy").parse(fromDate.getValue());
            try {
              controller.generateExceptionReport(pbmd12Date);
            } catch (Exception ex) {
              log.error(
                  "Error on generating <PBMD12> African Bank exception Report:" + ex.getMessage());

              ex.printStackTrace();
            }
          } else {
            log.info("report is not generated");
          }

        } catch (Exception e) {
          info("No record was selected...");
          log.error("<GenerateButton>: " + e.getMessage());
          //e.printStackTrace();
        }
      }
    };


    generateButton.setDefaultFormProcessing(false);
    generateButton.setVisibilityAllowed(false);

    showDateButton = new Button("showDateButton") {
      @Override
      public void onSubmit() {
        try {
          String reportChoice =
              reportNames.getChoices().get(Integer.parseInt(reportNames.getValue())).getReportNr();
          generateButton.setVisibilityAllowed(true);
          if (reportChoice.equals("PSMD05") || reportChoice.equals("PSMD02") ||
              reportChoice.equals("PBMD09") || reportChoice.equals("MR021") ||
              reportChoice.equals("MR025") || reportChoice.equals("MR020") ||
              reportChoice.equals("MR026") || reportChoice.equals("MR027") ||
              reportChoice.equals("PSMD06") || reportChoice.equals("PHIR01") ||
              reportChoice.equals("PHIR02") || reportChoice.equals("PHIR03") ||
              reportChoice.equals("PHIR04") || reportChoice.equals("PHIR08") ||
              reportChoice.equals("PHIR09") || reportChoice.equals("PHIR10")) {
            fromDateLbl.setVisibilityAllowed(true);
            fromDate.setVisibilityAllowed(true);
            toDateLbl.setVisibilityAllowed(true);
            toDate.setVisibilityAllowed(true);
            showDateButton.setVisibilityAllowed(false);
          } else {
            fromDateLbl.setVisibilityAllowed(true);
            fromDate.setVisibilityAllowed(true);
            toDateLbl.setVisibilityAllowed(false);
            toDate.setVisibilityAllowed(false);
            showDateButton.setVisibilityAllowed(false);
          }
        } catch (Exception e) {
          info("No date was selected...");
        }

      }
    };

    showDateButton.setDefaultFormProcessing(false);

    form.add(showDateButton);
    form.add(generateButton);


  }


  //initialize the list using the model supplying the information

  public void loadData() {


    List<ReportsNamesModel> reportnameListfromDB = new ArrayList<ReportsNamesModel>();
    reportnameListfromDB = (List<ReportsNamesModel>) Controller.viewActiveReports();
    Collections.sort(reportnameListfromDB, new reportNoOrderSorter());

    if (reportnameListfromDB.size() > 0) {
      webReportsNamesList = new ArrayList<WebReportsNamesModel>();

      for (ReportsNamesModel localCommonsModel : reportnameListfromDB) {
        WebReportsNamesModel webReportsNamesModel = new WebReportsNamesModel();
        webReportsNamesModel =
            new WebAdminTranslator().translateCommonsReportNamesToWebModel(localCommonsModel);
        webReportsNamesList.add(webReportsNamesModel);

      }
    }
  }


  private class reportNoOrderSorter implements Comparator<ReportsNamesModel> {

    @Override
    public int compare(ReportsNamesModel o1, ReportsNamesModel o2) {
      if (o1.getReportNr() == null && o2.getReportNr() == null) {
        return 0;
      } else if (o1.getReportNr() == null) {
        return -1;
      } else if (o2.getReportNr() == null) {
        return 1;
      }
      return o1.getReportNr().compareTo(o2.getReportNr());

    }

  }

  @Override
  public String getAjaxIndicatorMarkupId() {
    // TODO Auto-generated method stub
    return null;
  }


}
