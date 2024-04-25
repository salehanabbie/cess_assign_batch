package com.bsva.reports;

import com.itextpdf.text.DocumentException;
import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import org.apache.log4j.Logger;

/**
 * @author SalehaR
 * @author SalehaR - 2019/10/09 Remove Obsolete Reports
 */
public class MonthlyReportsLogic {
  public static Logger log = Logger.getLogger(MonthlyReportsLogic.class);


  boolean allReportsProduced = false,
  //			pbmd02Report = false,
  //			pbmd03Report = false,
  //psmd03Report = false,
  psmd04Report = false,
  //psmd05Report = false,
  psmd06Report = false,
  //psmc01Report = false,
  mr025Report = false,
      psmc02Report = false,
      pbbil05Report = false,
      pbbil09Report = false;

  Date currentDate = new Date();

  public MonthlyReportsLogic() {
    allReportsProduced = false;
    //		pbmd02Report = false;
    //		pbmd03Report = false;
    //psmd03Report = false;
    psmd04Report = false;
    //psmd05Report = false;
    psmd06Report = false;
    //psmc01Report = false;
    mr025Report = false;
    psmc02Report = false;
    pbbil05Report = false;
    pbbil09Report = false;

  }

  public boolean generateMonthlyReports() {
    //PSMD04 - PASA BATCH REJECTIONS	REPORTS
    try {
      log.info("***********Generating PSMD04 Report*****************");
      PasaBatchRejectionsReport pasaBatchRejectionsReport = new PasaBatchRejectionsReport();
      pasaBatchRejectionsReport.generateReport(null);

      psmd04Report = true;
      log.info("***********PSMD04 Report Completed*****************");
    } catch (FileNotFoundException e) {
      psmd04Report = false;
      log.error("<FE> Error on populating PSMD04 Report :" + e.getMessage());
      e.printStackTrace();
    } catch (DocumentException e) {
      psmd04Report = false;
      log.error("<DE> Error on populating PSMD04 Report :" + e.getMessage());
      e.printStackTrace();
    } catch (Exception e) {
      psmd04Report = false;
      log.error("<EX> Error on populating PSMD04 Report :" + e.getMessage());
      e.printStackTrace();
    }

    //		PSMD06 - PASA BATCH AMENDMENT REPORTS
    try {
      log.info("***********Generating PSMD06 Report*****************");
      PasaBatchAmendmentReport pasaBatchAmendmentReport = new PasaBatchAmendmentReport();
      pasaBatchAmendmentReport.generateReport(null, null);

      psmd06Report = true;
      log.info("***********PSMD06 Report Completed*****************");
    } catch (FileNotFoundException e) {
      psmd06Report = false;
      log.error("<FE> Error on populating PSMD06 Report :" + e.getMessage());
      e.printStackTrace();
    } catch (DocumentException e) {
      psmd06Report = false;
      log.error("<DE> Error on populating PSMD06 Report :" + e.getMessage());
      e.printStackTrace();
      psmd06Report = false;
    } catch (Exception e) {
      psmd06Report = false;
      log.error("<EX> Error on populating PSMD06 Report :" + e.getMessage());
      e.printStackTrace();
    }

    //MR020 - PRODUCTION BATCH VOLUMES
    try {
      log.info("***********Generating MR020 Report*****************");
      //Old Report
      //			BSAMonthlyBatchVolumesReport bsaMonthlyBatchVolumesReport = new
		//			BSAMonthlyBatchVolumesReport();
      //			bsaMonthlyBatchVolumesReport.generateReport();

      //New Report
      MR020_BSAMonthlyBatchVolumesReport mr020_BSAMonthlyBatchVolumesReport =
          new MR020_BSAMonthlyBatchVolumesReport();
      mr020_BSAMonthlyBatchVolumesReport.generateReport(null, null);
      log.info("***********MR020 Report Completed*****************");
    } catch (FileNotFoundException e) {
      log.error("<FE> Error on populating MR020 Report :" + e.getMessage());
      e.printStackTrace();
    } catch (DocumentException e) {
      log.error("<DE> Error on populating MR020 Report :" + e.getMessage());
      e.printStackTrace();
    } catch (Exception e) {
      log.error("<EX> Error on populating MR020 Report :" + e.getMessage());
      e.printStackTrace();
    }

    //PBBIL05 MANDATES TXN BILLING REPORT BATCH
    try {
      log.info("***********Generating PBBIL05 Report*****************");
//			2022/07/04-SalehaR-Old version of report (excel) replaced by CSV
//			PBBIL05_BatchMandatesTxnBillReport pbbil05report = new
//			PBBIL05_BatchMandatesTxnBillReport();
//			pbbil05report.generateReport(null,null, false);

      PBBIL05_CSV_BatchMandatesTxnsBillReport pbbil05report =
          new PBBIL05_CSV_BatchMandatesTxnsBillReport();
      pbbil05report.generateReport(null, null, false);
      log.info("***********PBBIL05 Report Completed*****************");
      pbbil05Report = true;
    } catch (Exception e) {
      pbbil05Report = false;
      log.error("<EX> Error on populating PBBIL05 Report :" + e.getMessage());
      e.printStackTrace();
    }


    log.info("allReportsProduced = " + allReportsProduced);

    //		log.info("pbmd02Report = "+pbmd02Report);
    //		log.info("pbmd03Report = "+pbmd03Report);
    //log.info("psmd03Report = "+psmd03Report);
    log.info("psmd04Report = " + psmd04Report);
    //log.info("psmd05Report = "+psmd05Report);
    log.info("psmd06Report = " + psmd06Report);
    //log.info("psmc01Report = "+psmc01Report);
    log.info("psmc02Report =" + psmc02Report);
    log.info("mr025Report =" + mr025Report);
    log.info("pbbil05Report =" + pbbil05Report);
    log.info("pbbil09Report =" + pbbil09Report);


    //2019/10/09-Remove Obsolete Reports
    //		if(pbmd02Report && pbmd03Report && psmd03Report && psmd04Report && psmd05Report &&
	  //		psmd06Report && psmc01Report && psmc02Report)
    if (/*psmd03Report &&*/ psmd04Report /*&& psmd05Report*/ && psmd06Report /*&& psmc01Report*/ &&
        psmc02Report && pbbil05Report && pbbil09Report) {
      allReportsProduced = true;
    }


    return allReportsProduced;
  }

  public void generateMonthlyReportsAfterSOD() throws FileNotFoundException, DocumentException {

    if (isFirstDayOfTheMonth()) {

    }
  }


  public boolean isFirstDayOfTheMonth() {

    Calendar calender = new GregorianCalendar();
    calender.setTime(currentDate);

    if (calender.get(Calendar.DAY_OF_MONTH) == 1) {
      return true;
    }
    return false;
  }

}
