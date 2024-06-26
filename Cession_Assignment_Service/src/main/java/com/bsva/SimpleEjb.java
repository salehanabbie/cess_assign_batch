package com.bsva;

import com.bsva.authcoll.file.AC_Pacs002_001_04_Extract;
import com.bsva.authcoll.file.StatusReportST200Extract;
import com.bsva.authcoll.file.StatusReportST202Extract;
import com.bsva.authcoll.file.StatusReportST204Extract;
import com.bsva.authcoll.singletable.file.AC_Pain010_Extract_ST;
import com.bsva.authcoll.singletable.file.AC_Pain012_Extract_ST;
import com.bsva.authcoll.singletable.file.EotFilesCreator_ST;
import com.bsva.billing.BillingExport;
import com.bsva.businessLogic.StartOfDayLogic;
import com.bsva.reports.DailyReportsLogic;
import com.bsva.reports.MonthlyReportsLogic;
import java.text.ParseException;
import javax.ejb.Stateless;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
public class SimpleEjb {

  // inside this class call all the logic for the jobs you would like to schedule.
  private static final Logger log = LoggerFactory.getLogger(SimpleEjb.class);

  // start of day logic call
  public void runStartOfday() throws ParseException {
    StartOfDayLogic startofDayLogic = new StartOfDayLogic();
    startofDayLogic.SodImplementation();
  }

  //pain 010 extract logic call .
  public void runPain010Extract() {
    AC_Pain010_Extract_ST ac_Pain010_Extract_ST = new AC_Pain010_Extract_ST();
    ac_Pain010_Extract_ST.extract();
  }

  //pain 012 extract logic call .
  public void runPain012Extract() {
    AC_Pain012_Extract_ST ac_Pain012_Extract_ST = new AC_Pain012_Extract_ST();
    ac_Pain012_Extract_ST.extract();
  }

  public void runACPacs002Extract() throws Exception {
    AC_Pacs002_001_04_Extract ac_Pacs002_001_04_Extract = new AC_Pacs002_001_04_Extract();
    ac_Pacs002_001_04_Extract.generatePacs002Report();
  }

  public void startFileWatcher() {
    FileWatcher filewatcher = new FileWatcher();
    filewatcher.startMonitor();
  }

  public void runStatusReportST200Extract() throws Exception {
    StatusReportST200Extract statusReportST200Extract = new StatusReportST200Extract();
  }

  public void runStatusReportST202Extract() throws Exception {
    StatusReportST202Extract statusReportST202Extract = new StatusReportST202Extract();
  }

  public void runStatusReportST204Extract() throws Exception {
    StatusReportST204Extract statusReportST204Extract = new StatusReportST204Extract();
  }

  public void runBillingExport() throws Exception {
    BillingExport billingExport = new BillingExport();
    billingExport.exportInterchangeBilling();
    billingExport.exportTxnBilling();
  }

  public void runEotExtract(String mdtLoadType) throws Exception {
    log.debug("Simple EJB  Eot File Extract....");
      EotFilesCreator_ST eotFilesCreator_ST = new EotFilesCreator_ST();
      eotFilesCreator_ST.EotImplementation();
  }

  public void runDailyReports() throws Exception {
    DailyReportsLogic dailyReportsLogic = new DailyReportsLogic();
    dailyReportsLogic.generateDailyReportsAfterSOD();

    MonthlyReportsLogic monthlyReportsLogic = new MonthlyReportsLogic();
    monthlyReportsLogic.generateMonthlyReportsAfterSOD();
  }

}

