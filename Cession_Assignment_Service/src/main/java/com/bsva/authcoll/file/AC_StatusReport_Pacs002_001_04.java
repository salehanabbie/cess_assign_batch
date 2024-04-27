package com.bsva.authcoll.file;

import com.bsva.PropertyUtil;
import com.bsva.commons.model.OpsFileRegModel;
import com.bsva.entities.CasOpsMndtCountEntity;
import com.bsva.entities.CasOpsRefSeqNrEntity;
import com.bsva.entities.CasOpsSotEotCtrlEntity;
import com.bsva.entities.CasOpsStatusDetailsEntity;
import com.bsva.entities.CasOpsStatusHdrsEntity;
import com.bsva.entities.CasSysctrlCompParamEntity;
import com.bsva.entities.CasSysctrlSysParamEntity;
import com.bsva.interfaces.AdminBeanRemote;
import com.bsva.interfaces.FileProcessBeanRemote;
import com.bsva.interfaces.ServiceBeanRemote;
import com.bsva.interfaces.ValidationBeanRemote;
import com.bsva.utils.DateUtil;
import com.bsva.utils.Util;
import iso.std.iso._20022.tech.xsd.pacs_002_001.BranchAndFinancialInstitutionIdentification5;
import iso.std.iso._20022.tech.xsd.pacs_002_001.ClearingSystemMemberIdentification2;
import iso.std.iso._20022.tech.xsd.pacs_002_001.Document;
import iso.std.iso._20022.tech.xsd.pacs_002_001.FIToFIPaymentStatusReportV04;
import iso.std.iso._20022.tech.xsd.pacs_002_001.FinancialInstitutionIdentification8;
import iso.std.iso._20022.tech.xsd.pacs_002_001.GenericOrganisationIdentification1;
import iso.std.iso._20022.tech.xsd.pacs_002_001.GroupHeader53;
import iso.std.iso._20022.tech.xsd.pacs_002_001.MandateRelatedInformation8;
import iso.std.iso._20022.tech.xsd.pacs_002_001.NumberOfTransactionsPerStatus3;
import iso.std.iso._20022.tech.xsd.pacs_002_001.OrganisationIdentification8;
import iso.std.iso._20022.tech.xsd.pacs_002_001.OriginalGroupHeader1;
import iso.std.iso._20022.tech.xsd.pacs_002_001.OriginalTransactionReference16;
import iso.std.iso._20022.tech.xsd.pacs_002_001.Party11Choice;
import iso.std.iso._20022.tech.xsd.pacs_002_001.PartyIdentification43;
import iso.std.iso._20022.tech.xsd.pacs_002_001.PaymentTransaction33;
import iso.std.iso._20022.tech.xsd.pacs_002_001.StatusReason6Choice;
import iso.std.iso._20022.tech.xsd.pacs_002_001.StatusReasonInformation9;
import iso.std.iso._20022.tech.xsd.pacs_002_001.TransactionGroupStatus3Code;
import iso.std.iso._20022.tech.xsd.pacs_002_001.TransactionIndividualStatus3Code;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.apache.log4j.Logger;

/**
 * @author Saleha Saib
 * Modified by DimakatsoN - 2016/09/13 - Alignment to TRS 16
 * @author SalehaR-2019/05/10 Debug Statements & Code CleanUp
 * @author SalehaR-2020/09/08: Status Report Optimisation
 */
public class AC_StatusReport_Pacs002_001_04 {
  @EJB
  PropertyUtil propertyUtil;
  private Logger log = Logger.getLogger("AC_StatusReport_Extract");

  public static ServiceBeanRemote beanRemote;
  private static AdminBeanRemote adminBeanRemote;
  public static ValidationBeanRemote valBeanRemote;
  public static FileProcessBeanRemote fileProcessBeanRemote;

  String backEndProcess = "BACKEND";
  //	String st100Service = "ST100";
  String reportToBeProdStatus = "6";
  String readyForExtractStatus = "3";
  String repProdStatus = "7";
  String extractedStatus = "4";
  String messgId;

  CasSysctrlCompParamEntity mdtSysctrlCompParamEntity = null;
  CasSysctrlSysParamEntity casSysctrlSysParamEntity;
  CasOpsRefSeqNrEntity casOpsRefSeqNrEntity = null;
  String serviceId, mdtReqId, instdIdFileName, instIdMsgId;
  List<CasOpsStatusHdrsEntity> statusGrpHdrList = null;
  List<CasOpsStatusDetailsEntity> grpHdrErrorList, transErrorList;

  String pacs002ServiceName = null, outputFileBic = null, outgoingService = null;
  int lastSeqNoUsed;
  String groupStatus = null;
  boolean populateOpi = false, populateOad = false;
  CasOpsSotEotCtrlEntity casOpsSotEotCtrlEntity;
  private String urn = "urn:iso:std:iso:20022:tech:xsd:pacs.002.001.04";
  public static boolean result;

  String pac002ServiceId = null;
  String xmlDateFormat = "yyyy-MM-dd'T'HH:mm:ss";
  int accpTxns = 0, rejTxns = 0;
  int nrOfMsgs = 0;
  String testLiveIndProp = null;
  String outFileName;
  long startTime, endTime, duration;


  public AC_StatusReport_Pacs002_001_04() {
    startTime = System.nanoTime();
    contextCheck();
    contextAdminBeanCheck();
    contextValidationBeanCheck();
    contextFileProcBeanCheck();
    try {
      propertyUtil = new PropertyUtil();
      this.testLiveIndProp = propertyUtil.getPropValue("TestLiveInd");
      //log.info("Test Live Indicator Property: "+testLiveIndProp);
    } catch (Exception e) {
      log.error(
          "AC_StatusReport_Pacs002_001_04 - Could not find MandateMessageCommons.properties in " +
				  "classpath");
    }
  }

  public void generatePacs002StatusReport(String pac002ServiceId) throws Exception {
    this.pac002ServiceId = null;
    this.pac002ServiceId = pac002ServiceId;

    try {
      statusGrpHdrList = new ArrayList<CasOpsStatusHdrsEntity>();
      statusGrpHdrList =
          (List<CasOpsStatusHdrsEntity>) beanRemote.retrieveStatusHdrsByStatus(reportToBeProdStatus,
              pac002ServiceId);

		if (statusGrpHdrList != null && statusGrpHdrList.size() > 0) {
			mdtSysctrlCompParamEntity =
					(CasSysctrlCompParamEntity) valBeanRemote.retrieveCompanyParameters(
							backEndProcess);

			casSysctrlSysParamEntity = new CasSysctrlSysParamEntity();
			casSysctrlSysParamEntity =
					(CasSysctrlSysParamEntity) adminBeanRemote.retrieveActiveSysParameter();

			Document pacsDocument;
			FIToFIPaymentStatusReportV04 fiToFiPmtStsRep04;
			for (CasOpsStatusHdrsEntity statusGrpHdrEntity : statusGrpHdrList) {
				log.info(
						"********GENERATING Status Report " + statusGrpHdrEntity.getHdrMsgId() +
								" ********");

				pacsDocument = new Document();
				fiToFiPmtStsRep04 = new FIToFIPaymentStatusReportV04();

				GroupHeader53 groupHeader = new GroupHeader53();
				groupHeader = populateGroupHdr(statusGrpHdrEntity);

				OriginalGroupHeader1 originalGrpHdrStsInf = new OriginalGroupHeader1();
				originalGrpHdrStsInf = populateOriginalGrpHdrInfAndSts(statusGrpHdrEntity);

				//_______Add Groupd Header & Original GrpHdr Info and Status____//
				fiToFiPmtStsRep04.setGrpHdr(groupHeader);

				//Only Populate this section if GHErrorList is empty
				if (!(grpHdrErrorList != null && grpHdrErrorList.size() > 0)) {
					Map<String, List<CasOpsStatusDetailsEntity>> statusDetailsMap =
							new HashMap<String, List<CasOpsStatusDetailsEntity>>();
					statusDetailsMap = fileProcessBeanRemote.retrieveStatusReportRejections(
							statusGrpHdrEntity.getSystemSeqNo(), "TXN");

					if (statusDetailsMap != null && statusDetailsMap.size() > 0) {

						for (Map.Entry<String, List<CasOpsStatusDetailsEntity>> entry :
								statusDetailsMap.entrySet()) {
							//Populate them in file
							PaymentTransaction33 paymentInfo = new PaymentTransaction33();
							paymentInfo = generateTransErrors(entry.getValue());

							//_______Add Transaction Info and Status____//
							if (paymentInfo != null) {
								fiToFiPmtStsRep04.getTxInfAndSts().add(paymentInfo);
							}
						}
					}
				}

				fiToFiPmtStsRep04.setOrgnlGrpInfAndSts(originalGrpHdrStsInf);

				//_______Add FiToFiPmtSts to pacsDoc____//
				pacsDocument.setFIToFIPmtStsRpt(fiToFiPmtStsRep04);

				if (pacsDocument != null) {
					createPacs002(pacsDocument, statusGrpHdrEntity.getService(),
							statusGrpHdrEntity.getHdrMsgId());
				}

				if (statusGrpHdrEntity.getProcessStatus().equalsIgnoreCase(reportToBeProdStatus)) {
					statusGrpHdrEntity.setProcessStatus(repProdStatus);
				}
				statusGrpHdrEntity.setExtractFileName(outFileName);
				beanRemote.updateOpsStatusHdrs(statusGrpHdrEntity);
			}//end of loop through ops Vet Hdrs.
			endTime = System.nanoTime();
			duration = (endTime - startTime) / 1000000;
			log.info("|TOTAL EXECUTION TIME FOR " + outFileName + " IS " +
					DurationFormatUtils.formatDuration(duration, "HH:mm:ss.S") + " milliseconds |");
		}//end of if Vet Hdrs List > 0
		else {
			log.debug(
					"**** No Entry In Ops Status Report Details --- PACS 002 Status Report will not be " +
							"produced ---");
		}
    } catch (Exception e) {
      log.error("**** Exception Creating Status Report **** : " + e);
      e.printStackTrace();
      e.getCause();
      //				throw new Exception(e);
    }
  }

  public void createPacs002(Document doc, String serviceId, String extractMsgId) {
    try {
      outFileName = generatePacs002FileName(serviceId);

      String out = "/home/opsjava/Delivery/Cession_Assign/Output/temp/" + outFileName + ".xml";
      File f =
          new File("/home/opsjava/Delivery/Cession_Assign/Output/temp/" + outFileName + ".xml");
      JAXBContext jaxbContext = JAXBContext.newInstance(Document.class);
      Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

      // format the XML output
      jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
      //Remove the standalone="yes"
      jaxbMarshaller.setProperty("com.sun.xml.bind.xmlDeclaration", Boolean.FALSE);
      jaxbMarshaller.setProperty("com.sun.xml.bind.xmlHeaders",
          "<?xml version=\"1.0\" encoding=\"UTF-8\"?>");

      QName qName = new QName(urn, "Document");
      JAXBElement<Document> root = new JAXBElement<Document>(qName, Document.class, doc);

      jaxbMarshaller.marshal(root, f);

      createOpsFileReg(outFileName, out, extractMsgId);

      copyFile(outFileName);
    } catch (Exception e) {
      log.error("Error on creating createPacs002 Status file: " + e.getMessage());
      e.printStackTrace();
    }
  }

  public String generatePacs002FileName(String service) {
    String achId, creationDate, fileSeqNo, testLiveInd, fileName = null;
    SimpleDateFormat sdfFileDate = new SimpleDateFormat("yyyyMMdd");

    int fileLastSeqNo = 0;
    try {
      if (mdtSysctrlCompParamEntity != null) {
        achId = mdtSysctrlCompParamEntity.getAchId();
        testLiveInd = mdtSysctrlCompParamEntity.getTransamissionInd();
      } else {
        achId = "021";
        testLiveInd = testLiveIndProp;
      }

      fileSeqNo = String.format("%06d", lastSeqNoUsed);
      log.debug("PACS 002 Status Report File No To be used====. :" + fileSeqNo);

      //					creationDate = sdfFileDate.format(new Date());
      //TRS16 Processing Rules
      if (casSysctrlSysParamEntity != null && casSysctrlSysParamEntity.getProcessDate() != null) {
        creationDate = sdfFileDate.format(casSysctrlSysParamEntity.getProcessDate());
      } else {
        creationDate = sdfFileDate.format(new Date());
      }

      fileName =
          achId + service + "00" + instdIdFileName + "S" + creationDate + fileSeqNo + testLiveInd +
              "D";
    } catch (Exception e) {
      log.error("**** Exception generating generatePacs002FileName **** : " + e);
      e.printStackTrace();
      e.getCause();
    }

    return fileName;

  }

  public XMLGregorianCalendar getGregorianDate(Date dateToChange) {

    GregorianCalendar gCalendar = new GregorianCalendar();
    gCalendar.setTime(dateToChange);


    XMLGregorianCalendar xmlCalendar = null;
    try {
      xmlCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gCalendar);
    } catch (Exception ex) {
      // Logger.getLogger(StringReplace.class.getName()).log(Level.SEVERE,
      // null, ex);
      ex.printStackTrace();
    }
    return xmlCalendar;
  }

  public GroupHeader53 populateGroupHdr(CasOpsStatusHdrsEntity statusGrpHdrEntity) {
    log.debug("-------------populating the Group Hdr--------------");
    BranchAndFinancialInstitutionIdentification5 branchFinInfo;
    FinancialInstitutionIdentification8 finInstInfo;
    ClearingSystemMemberIdentification2 clearingSystemMemberIdentification2;


    //_______________POPULATE GRPHDR INFO________________//
    GroupHeader53 grpHeader = new GroupHeader53();
    //Generate MsgId

    grpHeader.setMsgId(statusGrpHdrEntity.getHdrMsgId());
    /*validate msgIdFormat*/
    String[] splitMsgId = statusGrpHdrEntity.getHdrMsgId().split("/");

    String fileNo = splitMsgId[4];
    lastSeqNoUsed = Integer.valueOf(
        fileNo);                                                                                                                    /*msgId.substring(28,32);*/
    log.debug("fileNo: " + fileNo);

    //				grpHeader.setCreDtTm(getGregorianDate(new Date()));

    //				DateFormat df1 = new SimpleDateFormat()


    grpHeader.setCreDtTm(DateUtil.toXMLGregorianCalendarWithFormat(new Date(), xmlDateFormat));


    //	Set Instructing Agent
    branchFinInfo = new BranchAndFinancialInstitutionIdentification5();
    finInstInfo = new FinancialInstitutionIdentification8();
    clearingSystemMemberIdentification2 = new ClearingSystemMemberIdentification2();

    String instrtgAgnt = "210000";
	  if (mdtSysctrlCompParamEntity != null) {
		  instrtgAgnt = mdtSysctrlCompParamEntity.getAchInstId();
	  } else {
		  instrtgAgnt = "210000";
	  }
    clearingSystemMemberIdentification2.setMmbId(instrtgAgnt);

	  if (clearingSystemMemberIdentification2 != null &&
			  clearingSystemMemberIdentification2.getMmbId() != null) {
		  finInstInfo.setClrSysMmbId(clearingSystemMemberIdentification2);
	  }

	  if (finInstInfo != null && finInstInfo.getClrSysMmbId() != null) {
		  branchFinInfo.setFinInstnId(finInstInfo);
	  }

	  if (branchFinInfo != null && branchFinInfo.getFinInstnId() != null) {
		  grpHeader.setInstgAgt(branchFinInfo);
	  }


    //Set Instructed Agent
    branchFinInfo = new BranchAndFinancialInstitutionIdentification5();
    finInstInfo = new FinancialInstitutionIdentification8();
    clearingSystemMemberIdentification2 = new ClearingSystemMemberIdentification2();

	  if (statusGrpHdrEntity.getInstgAgt() != null) {
		  clearingSystemMemberIdentification2.setMmbId(statusGrpHdrEntity.getInstgAgt());
	  }

	  if (clearingSystemMemberIdentification2 != null &&
			  clearingSystemMemberIdentification2.getMmbId() != null) {
		  finInstInfo.setClrSysMmbId(clearingSystemMemberIdentification2);
	  }

	  if (finInstInfo != null && finInstInfo.getClrSysMmbId() != null) {
		  branchFinInfo.setFinInstnId(finInstInfo);
	  }

	  if (branchFinInfo != null && branchFinInfo.getFinInstnId() != null) {
		  grpHeader.setInstdAgt(branchFinInfo);
	  }
    instdIdFileName = statusGrpHdrEntity.getInstgAgt();

    log.debug("-------------finish populating the Group Hdr--------------");
    return grpHeader;
  }

  public OriginalGroupHeader1 populateOriginalGrpHdrInfAndSts(
      CasOpsStatusHdrsEntity statusGrpHdrEntity) {
    grpHdrErrorList = new ArrayList<CasOpsStatusDetailsEntity>();

    //_______________POPULATE ORGNL GRP INFO & STATUS________________//
    OriginalGroupHeader1 orgGrpHdr = new OriginalGroupHeader1();

	  if (statusGrpHdrEntity.getOrgnlMsgId() != null) {
		  orgGrpHdr.setOrgnlMsgId(statusGrpHdrEntity.getOrgnlMsgId());
	  }
    //
    //	       if(statusGrpHdrEntity.getOrgnlNoOfTxns() != null)
    //	    	   orgGrpHdr.setOrgnlNbOfTxs(String.valueOf(statusGrpHdrEntity.getOrgnlNoOfTxns
	  //	    	   ()));

	  if (statusGrpHdrEntity.getOrgnlMsgName() != null) {
		  orgGrpHdr.setOrgnlMsgNmId(statusGrpHdrEntity.getOrgnlMsgName());
	  }

	  if (statusGrpHdrEntity.getOrgnlCreateDateTime() != null) {
		  orgGrpHdr.setOrgnlCreDtTm(
				  DateUtil.toXMLGregorianCalendarWithFormat(
						  statusGrpHdrEntity.getOrgnlCreateDateTime(),
						  xmlDateFormat));
	  }

    if (statusGrpHdrEntity.getGroupStatus() != null) {
      //	    	   log.info("-------------populating the Group Hdr status--------------");
		if (statusGrpHdrEntity.getGroupStatus().equalsIgnoreCase("ACCP")) {
			orgGrpHdr.setGrpSts(TransactionGroupStatus3Code.ACCP);
		} else if (statusGrpHdrEntity.getGroupStatus().equalsIgnoreCase("RJCT")) {
			orgGrpHdr.setGrpSts(TransactionGroupStatus3Code.RJCT);
		} else if (statusGrpHdrEntity.getGroupStatus().equalsIgnoreCase("PART")) {
			orgGrpHdr.setGrpSts(TransactionGroupStatus3Code.PART);
		}
      //	    	   log.info("grp HRD status---------> "+ orgGrpHdr.getGrpSts());

    }
    List<CasOpsStatusDetailsEntity> opsStatusDetailsList =
        new ArrayList<CasOpsStatusDetailsEntity>();
    opsStatusDetailsList =
        (List<CasOpsStatusDetailsEntity>) valBeanRemote.findOpsStatusDetByCriteria(
            "CasOpsStatusDetailsEntity.findByStatusHdrSeqNo", "statusHdrSeqNo",
            statusGrpHdrEntity.getSystemSeqNo(), null);
    //	       log.info("opsStatusDetailsList.size(): "+ opsStatusDetailsList.size());
    //			       log.info("statusGrpHdrEntity.getSystemSeqNo()------>"+statusGrpHdrEntity
	  //			       .getSystemSeqNo());
    //	       log.info("opsStatusDetailsList--------->" +opsStatusDetailsList);


    if (opsStatusDetailsList != null && opsStatusDetailsList.size() > 0) {
      for (CasOpsStatusDetailsEntity opsStatusDetailsEntity : opsStatusDetailsList) {
        if (opsStatusDetailsEntity.getErrorType().equalsIgnoreCase("HDR") &&
            opsStatusDetailsEntity.getTxnStatus().equalsIgnoreCase("RJCT")) {
          grpHdrErrorList.add(opsStatusDetailsEntity);
        }
      }
    }


    if (grpHdrErrorList != null && grpHdrErrorList.size() > 0) {
      for (CasOpsStatusDetailsEntity grpHdrError : grpHdrErrorList) {
        StatusReasonInformation9 statRsnInfo = new StatusReasonInformation9();
        StatusReason6Choice stRsnChoice = new StatusReason6Choice();

        stRsnChoice.setPrtry(grpHdrError.getErrorCode());
        statRsnInfo.setRsn(stRsnChoice);
		  if (grpHdrError.getErrorCode() != null) {
			  orgGrpHdr.getStsRsnInf().add(statRsnInfo);
		  }
        //****Retrieve the error code description***/
				/*  MdtCnfgErrorCodesEntity errorEntity = (MdtCnfgErrorCodesEntity) beanRemote
				.retrieveErrorCodeDesc(grpHdrError.getErrorCode());

					if(errorEntity != null)
							statRsnInfo.getAddtlInf().add(errorEntity.getErrorCodeDesc());
				 */

      }//end of for - loop through list
    }//end of check if there are errors


    if (statusGrpHdrEntity.getGroupStatus().equalsIgnoreCase("ACCP") ||
        (statusGrpHdrEntity.getGroupStatus().equalsIgnoreCase("PART"))) {

      //Populate # of Transactions per file
      NumberOfTransactionsPerStatus3 numberOfTransactionsPerStatus3;
      //Retrieve MdtOpsCount.

      CasOpsMndtCountEntity mdtOpsMndtCountEntity =
          (CasOpsMndtCountEntity) beanRemote.retrievePacs002Count(
              statusGrpHdrEntity.getOrgnlMsgId());

      if (mdtOpsMndtCountEntity != null) {
        log.debug("mdtOpsMndtCountEntity------> " + mdtOpsMndtCountEntity);
        int totalAccpt = 0;
        int rejMsgs = 0;
        int accpMsg = 0;


        if (mdtOpsMndtCountEntity.getNrOfMsgs() != null) {
			if (mdtOpsMndtCountEntity.getNrMsgsRejected() != null) {
				rejMsgs = mdtOpsMndtCountEntity.getNrMsgsRejected();
			}

			if (mdtOpsMndtCountEntity.getNrMsgsAccepted() != null) {
				accpMsg = mdtOpsMndtCountEntity.getNrMsgsAccepted();
			}

          //totalAccpt = mdtOpsMndtCountEntity.getNrOfMsgs() - rejMsgs;

          numberOfTransactionsPerStatus3 = new NumberOfTransactionsPerStatus3();
          numberOfTransactionsPerStatus3.setDtldNbOfTxs(String.valueOf(accpMsg));
          numberOfTransactionsPerStatus3.setDtldSts(TransactionIndividualStatus3Code.ACCP);
          orgGrpHdr.getNbOfTxsPerSts().add(numberOfTransactionsPerStatus3);
        }

        numberOfTransactionsPerStatus3 = new NumberOfTransactionsPerStatus3();
        numberOfTransactionsPerStatus3.setDtldNbOfTxs(String.valueOf(rejMsgs));
        numberOfTransactionsPerStatus3.setDtldSts(TransactionIndividualStatus3Code.RJCT);
        orgGrpHdr.getNbOfTxsPerSts().add(numberOfTransactionsPerStatus3);

				/*
					  if(mdtOpsMndtCountEntity.getNrOfMsgs()  == rejMsgs)
					  {
						  StatusReasonInformation9 statRsnInfo = new StatusReasonInformation9();
						  StatusReason6Choice stRsnChoice = new StatusReason6Choice();

						  //Error code for all txns rejected
						  stRsnChoice.setPrtry("902203");
						  statRsnInfo.setRsn(stRsnChoice);
						  orgGrpHdr.getStsRsnInf().add(statRsnInfo);
					  }
				 */
      }
    } else {
      if (opsStatusDetailsList.size() > 0) {

        CasOpsStatusDetailsEntity casOpsStatusDetailsEntity = new CasOpsStatusDetailsEntity();

        casOpsStatusDetailsEntity = opsStatusDetailsList.get(0);
        if (casOpsStatusDetailsEntity.getErrorType().equalsIgnoreCase("TXN") &&
            casOpsStatusDetailsEntity.getTxnStatus().equalsIgnoreCase("RJCT")) {

          //Populate # of Transactions per file
          NumberOfTransactionsPerStatus3 numberOfTransactionsPerStatus3;
          //Retrieve MdtOpsCount.

          CasOpsMndtCountEntity mdtOpsMndtCountEntity =
              (CasOpsMndtCountEntity) beanRemote.retrievePacs002Count(
                  statusGrpHdrEntity.getOrgnlMsgId());


          if (mdtOpsMndtCountEntity != null) {
            log.debug("mdtOpsMndtCountEntity------> " + mdtOpsMndtCountEntity);
            int totalAccpt = 0;
            int rejMsgs = 0;
            int accpMsg = 0;


            if (mdtOpsMndtCountEntity.getNrOfMsgs() != null) {
				if (mdtOpsMndtCountEntity.getNrMsgsRejected() != null) {
					rejMsgs = mdtOpsMndtCountEntity.getNrMsgsRejected();
				}

				if (mdtOpsMndtCountEntity.getNrMsgsAccepted() != null) {
					accpMsg = mdtOpsMndtCountEntity.getNrMsgsAccepted();
				}

              //totalAccpt = mdtOpsMndtCountEntity.getNrOfMsgs() - rejMsgs;

              numberOfTransactionsPerStatus3 = new NumberOfTransactionsPerStatus3();
              numberOfTransactionsPerStatus3.setDtldNbOfTxs(String.valueOf(accpMsg));
              numberOfTransactionsPerStatus3.setDtldSts(TransactionIndividualStatus3Code.ACCP);
              orgGrpHdr.getNbOfTxsPerSts().add(numberOfTransactionsPerStatus3);
            }

            numberOfTransactionsPerStatus3 = new NumberOfTransactionsPerStatus3();
            numberOfTransactionsPerStatus3.setDtldNbOfTxs(String.valueOf(rejMsgs));
            numberOfTransactionsPerStatus3.setDtldSts(TransactionIndividualStatus3Code.RJCT);
            orgGrpHdr.getNbOfTxsPerSts().add(numberOfTransactionsPerStatus3);


            if (mdtOpsMndtCountEntity.getNrOfMsgs() == rejMsgs) {
              StatusReasonInformation9 statRsnInfo = new StatusReasonInformation9();
              StatusReason6Choice stRsnChoice = new StatusReason6Choice();

              //Error code for all txns rejected
              stRsnChoice.setPrtry("902203");
              statRsnInfo.setRsn(stRsnChoice);
              orgGrpHdr.getStsRsnInf().add(statRsnInfo);
            }

          }

        }
      }
    }
    return orgGrpHdr;
  }


  public PaymentTransaction33 generateTransErrors(List<CasOpsStatusDetailsEntity> transErrorList) {
    PaymentTransaction33 transInfo = null;
    transInfo = new PaymentTransaction33();

//		transErrorList = new ArrayList<MdtAcOpsStatusDetailsEntity>();
//		transErrorList = (List<MdtAcOpsStatusDetailsEntity>) beanRemote.retrieveTransactionErrors
//		(txnId, hdrSeqNo);

    if (transErrorList != null && transErrorList.size() > 0) {
//			log.debug("txnErrors.size(): "+ transErrorList.size());

//		te_endTime = System.nanoTime();
//		te_duration = (te_endTime - te_startTime) / 1000000;
//		log.info("TXN ERRORS RETRIEVAL TIME IS "+DurationFormatUtils.formatDuration(te_duration,
//		"HH:mm:ss.S")+" milliseconds. ");

      CasOpsStatusDetailsEntity transErrorEntity = new CasOpsStatusDetailsEntity();
      transErrorEntity = transErrorList.get(0);

		if (transErrorEntity.getTxnId() != null) {
			transInfo.setOrgnlTxId(transErrorEntity.getTxnId());
		}

      transInfo.setTxSts(TransactionIndividualStatus3Code.RJCT);

      for (CasOpsStatusDetailsEntity txnErrorEntity : transErrorList) {
        if (txnErrorEntity.getTxnStatus().equalsIgnoreCase("ACCP")) {
          transInfo.setTxSts(TransactionIndividualStatus3Code.ACCP);
          accpTxns = accpTxns + 1;
        } else {
          if (txnErrorEntity.getTxnStatus().equalsIgnoreCase("RJCT")) {
            transInfo.setTxSts(TransactionIndividualStatus3Code.RJCT);
            rejTxns = rejTxns + 1;
          }
        }


        //						transInfo.getStsRsnInf().clear();
        StatusReasonInformation9 statusReasonInformation9 = new StatusReasonInformation9();
        StatusReason6Choice statusReason6Choice = new StatusReason6Choice();

        statusReason6Choice.setPrtry(txnErrorEntity.getErrorCode());
        statusReasonInformation9.setRsn(statusReason6Choice);
        //This was removed as per TRS 16
        //				         MdtCnfgErrorCodesEntity errorEntity = (MdtCnfgErrorCodesEntity)
		  //				         beanRemote.retrieveErrorCodeDesc(txnErrorEntity.getErrorCode
		  //				         ());
        //
        //				         if(errorEntity != null)
        //								statusReasonInformation9.getAddtlInf().add(errorEntity
		  //								.getErrorCodeDesc());

        transInfo.getStsRsnInf().add(statusReasonInformation9);
      }

      OriginalTransactionReference16 originalTransactionReference16 =
          new OriginalTransactionReference16();

      //Populate the Mandate Reference Number
      if (transErrorEntity.getMandateRefNumber() != null) {
        MandateRelatedInformation8 mandateRelatedInformation10 = new MandateRelatedInformation8();
        mandateRelatedInformation10.setMndtId(transErrorEntity.getMandateRefNumber());
        originalTransactionReference16.setMndtRltdInf(mandateRelatedInformation10);
      }

      //Populate the Debtor Bank Branch Number
      log.debug("**********transErrorEntity.getDebtorBranchNo() :" +
          transErrorEntity.getDebtorBranchNo());
      if (transErrorEntity.getDebtorBranchNo() != null) {
        BranchAndFinancialInstitutionIdentification5 branchAndFinancialInstitutionIdentification5 =
            new BranchAndFinancialInstitutionIdentification5();
        FinancialInstitutionIdentification8 financialInstitutionIdentification8 =
            new FinancialInstitutionIdentification8();
        ClearingSystemMemberIdentification2 clearingSystemMemberIdentification2 =
            new ClearingSystemMemberIdentification2();
        clearingSystemMemberIdentification2.setMmbId(transErrorEntity.getDebtorBranchNo());
        financialInstitutionIdentification8.setClrSysMmbId(clearingSystemMemberIdentification2);
        branchAndFinancialInstitutionIdentification5.setFinInstnId(
            financialInstitutionIdentification8);
        originalTransactionReference16.setDbtrAgt(branchAndFinancialInstitutionIdentification5);
      }

      //Populate the Creditor Abbreviated Short Name
      log.debug("**********transErrorEntity.getCrAbbShortName() :" +
          transErrorEntity.getCrAbbShortName());
      if (transErrorEntity.getCrAbbShortName() != null) {
        PartyIdentification43 partyIdentification43 = new PartyIdentification43();
        Party11Choice party11Choice = new Party11Choice();
        OrganisationIdentification8 organisationIdentification8 = new OrganisationIdentification8();
        GenericOrganisationIdentification1 genericOrganisationIdentification1 =
            new GenericOrganisationIdentification1();
        genericOrganisationIdentification1.setId(transErrorEntity.getCrAbbShortName());
        organisationIdentification8.getOthr().add(genericOrganisationIdentification1);
        party11Choice.setOrgId(organisationIdentification8);
        partyIdentification43.setId(party11Choice);
        originalTransactionReference16.setUltmtCdtr(partyIdentification43);
      }


		if (originalTransactionReference16.getMndtRltdInf() != null ||
				originalTransactionReference16.getDbtrAgt() != null ||
				originalTransactionReference16.getUltmtCdtr() != null) {
			transInfo.setOrgnlTxRef(originalTransactionReference16);
		}

    }

    return transInfo;
  }

  public void createOpsFileReg(String outFileName, String outFilePath, String extractMsgId) {
    OpsFileRegModel opsFileRegModel = new OpsFileRegModel();

    Date date = new Date();
    log.debug("File Created Date");

    opsFileRegModel.setFileName(outFileName);
    opsFileRegModel.setFilepath(outFilePath);
    opsFileRegModel.setNameSpace("iso:std:iso:20022:tech:xsd:pacs.002.001.04");
    opsFileRegModel.setProcessDate(date);
    opsFileRegModel.setStatus("C");
    opsFileRegModel.setInOutInd("O");
    opsFileRegModel.setOnlineInd("N");
    opsFileRegModel.setExtractMsgId(extractMsgId);

    Boolean result = adminBeanRemote.createOpsFileRegModel(opsFileRegModel);

    if (result == true) {
      log.debug("************* STATUS REPORT : " + outFileName +
          " has been created successfully in TEMP directory.*************************");

    } else {
      log.error("Error on creating PACS 002 Status Report........");
    }
  }

  public void copyFile(String fileName) throws IOException {
    File tmpFile = new File("/home/opsjava/Delivery/Cession_Assign/Output/" + fileName + ".xml");
    String outputFile = "/home/opsjava/Delivery/Cession_Assign/Output/temp/" + fileName + ".xml";
    FileOutputStream fos = new FileOutputStream(tmpFile);
    Path source = Paths.get(outputFile);
    Files.copy(source, fos);
    log.debug("Copying " + fileName + "from temp  to output directory...");
  }


  private static void contextCheck() {
    if (beanRemote == null) {
      beanRemote = Util.getServiceBean();
    }
  }

  private static void contextAdminBeanCheck() {
    if (adminBeanRemote == null) {
      adminBeanRemote = Util.getAdminBean();
    }
  }


  public static void contextValidationBeanCheck() {
    if (valBeanRemote == null) {
      valBeanRemote = Util.getValidationBean();
    }
  }

  public static void contextFileProcBeanCheck() {
    if (fileProcessBeanRemote == null) {
      fileProcessBeanRemote = Util.getFileProcessBean();
    }
  }

}
