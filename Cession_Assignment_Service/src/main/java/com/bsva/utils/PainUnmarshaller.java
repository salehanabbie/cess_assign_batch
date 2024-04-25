package com.bsva.utils;

import com.bsva.entities.CasSysctrlCompParamEntity;
import com.bsva.entities.CasSysctrlSysParamEntity;
import com.bsva.entities.MdtAcOpsStatusDetailsEntity;
import com.bsva.entities.MdtAcOpsStatusHdrsEntity;
import com.bsva.entities.MdtOpsRefSeqNrEntity;
import com.bsva.interfaces.AdminBeanRemote;
import com.bsva.interfaces.ValidationBeanRemote;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.JAXBIntrospector;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

/**
 * @author jeremym
 */
public class PainUnmarshaller {
  public static ValidationBeanRemote valBeanRemote;
  public static AdminBeanRemote adminBeanRemote;
  public static Logger log = Logger.getLogger(PainUnmarshaller.class);
  public static CasSysctrlSysParamEntity casSysctrlSysParamEntity;
  public static String sadcSystem = "SADC";
  public static String acSystem = "AC";
  public static String backEndProcess = "BACKEND";
  public static CasSysctrlCompParamEntity mdtSysctrlCompParamEntity = null;
  public static String pubErrorCode;


  public PainUnmarshaller() {

  }

  public static Object unmarshallFile(String filePath, String obj, String fileName,
                                      String schemaPath) {
    return unMarshal(filePath, obj, fileName, schemaPath);

  }

  /**
   * Unmarshall the input file using the PATH and class type.
   *
   * @param filePath
   * @param obj
   * @return Object
   */
  private static <T> T unMarshal(String filePath, String obj, String fileName, String schemaPath) {

    contextValidationBeanCheck();
    contextAdminBeanCheck();
    casSysctrlSysParamEntity =
        (CasSysctrlSysParamEntity) adminBeanRemote.retrieveActiveSysParameter();
    log.debug("mdtSysctrlSysParamEntity in validation: " + casSysctrlSysParamEntity);

    mdtSysctrlCompParamEntity =
        (CasSysctrlCompParamEntity) valBeanRemote.retrieveCompanyParameters(backEndProcess);
    log.debug("mdtSysctrlCompParamEntity in FileExtract: " + mdtSysctrlCompParamEntity);


    try {
      SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
      Schema schema = sf.newSchema(new File(schemaPath));
      Validator validator = schema.newValidator();
      validator.validate(new StreamSource(new File(filePath)));


      JAXBContext jc = JAXBContext.newInstance(obj);

      Unmarshaller u = jc.createUnmarshaller();
      u.setSchema(schema);
      u.setEventHandler(new UnmarshallEventHandler());


      File XMLFile = new File(filePath);

      T object = (T) JAXBIntrospector.getValue(u.unmarshal(XMLFile));

      return object;


//ORIGINAL CODE
//			JAXBContext jc = JAXBContext.newInstance(obj);
//
//			Unmarshaller u = jc.createUnmarshaller();
//
//			File XMLFile = new File(filePath);
//
//			T object = (T) JAXBIntrospector.getValue(u.unmarshal(XMLFile));
//
//			return object;

    } catch (JAXBException | SAXException e) {
      log.error("<JAXB>Error on unmarshall : " + e.getMessage());

      if (fileName != null) {
        String transmissionInd = fileName.substring(32, 33);
        log.debug("transmissionInd : " + transmissionInd);

        if (transmissionInd.equalsIgnoreCase("D")) {
          List<MdtAcOpsStatusDetailsEntity> opsStatusDetailsList =
              new ArrayList<MdtAcOpsStatusDetailsEntity>();

          String achId = fileName.substring(0, 3);
          String service = fileName.substring(3, 8);
          String instId = fileName.substring(8, 16);
          if (casSysctrlSysParamEntity.getSysType().equalsIgnoreCase(acSystem)) {
            instId = StringUtils.stripStart(instId, "0");
          }
          String creationDate = fileName.substring(17, 25);
          String fileNo = fileName.substring(25, 31);
//					String transInd = fileName.substring(31,32);
          log.debug("achId: " + achId);
          log.debug("service: " + service);
          log.debug("instId: " + instId);
          log.debug("creationDate: " + creationDate);
          log.debug("fileNo: " + fileNo);

          String msgId =
              achId + "/" + service + "/" + "00" + instId + "/" + creationDate + "/" + fileNo;
          log.debug("msgId: " + msgId);

          log.error("pubErrorCode" + pubErrorCode);
          BigDecimal hdrSystemSeqNo = BigDecimal.ZERO;
          MdtAcOpsStatusHdrsEntity opsStatusHdrsEntity = new MdtAcOpsStatusHdrsEntity();

          String msgName = null;
          String statusReportService = "ST200";

          if (service.equalsIgnoreCase("CARIN")) {
			  msgName = "pain.010";
			  statusReportService = "ST200";
		  } else if (service.equalsIgnoreCase("RCAIN")) {
            msgName = "pain.012";
            statusReportService = "ST204";
          } else if (service.equalsIgnoreCase("ST201")) {
			  opsStatusHdrsEntity.setService("ST202");
			  msgName = "pacs.002";
			  statusReportService = "ST202";
		  }

          String outMsgId = generateMsgId(instId, statusReportService);
          log.debug("outMsgId: " + outMsgId);
          opsStatusHdrsEntity.setSystemSeqNo(new BigDecimal(999999));
          opsStatusHdrsEntity.setHdrMsgId(outMsgId);
          opsStatusHdrsEntity.setInstgAgt(instId);
          opsStatusHdrsEntity.setOrgnlMsgId(msgId);
          opsStatusHdrsEntity.setOrgnlMsgName(msgName);
          opsStatusHdrsEntity.setProcessStatus("6");
          opsStatusHdrsEntity.setGroupStatus("RJCT");
          opsStatusHdrsEntity.setService(statusReportService);

          log.debug("opsStatusHdrsEntity in pacs 002 validation =====>>>>: " + opsStatusHdrsEntity);
          hdrSystemSeqNo = valBeanRemote.saveOpsStatusHdrs(opsStatusHdrsEntity);


          //Generate the Status Details

          MdtAcOpsStatusDetailsEntity opsStatusDetailsEntity = new MdtAcOpsStatusDetailsEntity();
          opsStatusDetailsEntity.setSystemSeqNo(new BigDecimal(123));
          opsStatusDetailsEntity.setStatusHdrSeqNo(hdrSystemSeqNo);
          opsStatusDetailsEntity.setErrorCode("902121");
          opsStatusDetailsEntity.setTxnStatus("RJCT");
          opsStatusDetailsEntity.setErrorType("HDR");

          opsStatusDetailsList.add(opsStatusDetailsEntity);
          valBeanRemote.saveOpsStatusDetails(opsStatusDetailsList);


        }
      }
      e.printStackTrace();

    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return null;
  }

  public static void setError(String errorCode) {
    pubErrorCode = errorCode;
  }


  public static String generateMsgId(String destInstId, String statusRepService) {
    int lastSeqNoUsed;
    log.debug("In the generateMsgId()");
    SimpleDateFormat sdfFileDate = new SimpleDateFormat("yyyyMMdd");
    String achId, achBicCode, creationDate, fileSeqNo, liveTestInd, msgId = null;
    String outgoingService = statusRepService;/*"ST100";*/
    try {

      if (mdtSysctrlCompParamEntity != null) {
        achId = mdtSysctrlCompParamEntity.getAchId();
//				achBicCode = "00"+mdtSysctrlCompParamEntity.getAchInstId();
        liveTestInd = mdtSysctrlCompParamEntity.getTransamissionInd();
      } else {
        achId = "021";
//				achBicCode = "00000000";
//				liveTestInd = "T";
      }


      MdtOpsRefSeqNrEntity mdtOpsRefSeqNrEntity = new MdtOpsRefSeqNrEntity();
      mdtOpsRefSeqNrEntity =
          (MdtOpsRefSeqNrEntity) valBeanRemote.retrieveRefSeqNr(outgoingService, destInstId);

		if (mdtOpsRefSeqNrEntity != null) {
			lastSeqNoUsed = Integer.valueOf(mdtOpsRefSeqNrEntity.getLastSeqNr());
			lastSeqNoUsed++;
		} else {
			lastSeqNoUsed = 1;
		}

      fileSeqNo = String.format("%06d", lastSeqNoUsed);
      mdtOpsRefSeqNrEntity.setLastSeqNr(fileSeqNo);
      valBeanRemote.updateOpsRefSeqNr(mdtOpsRefSeqNrEntity);


      creationDate = sdfFileDate.format(new Date());
//			    +"/"+liveTestInd
      msgId = achId + "/" + outgoingService + "/" + "00" + destInstId + "/" + creationDate + "/" +
          fileSeqNo;
      log.debug("OutMsgId: " + msgId);
    } catch (Exception e) {
      log.error("**** Exception generating MsgId **** : " + e);
      e.printStackTrace();
      e.getCause();
    }

    return msgId;
  }


  public static void contextValidationBeanCheck() {
    if (valBeanRemote == null) {
      valBeanRemote = Util.getValidationBean();
    }

  }

  public static void contextAdminBeanCheck() {
    if (adminBeanRemote == null) {
      adminBeanRemote = Util.getAdminBean();
    }
  }

}
