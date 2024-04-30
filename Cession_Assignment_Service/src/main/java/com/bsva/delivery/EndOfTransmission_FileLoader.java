package com.bsva.delivery;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.ejb.EJB;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.log4j.Logger;

import bsva.std.tech.xsd.eot_001_001.ControlMessage;
import bsva.std.tech.xsd.eot_001_001.Document;

import com.bsva.PropertyUtil;
import com.bsva.commons.model.OpsFileRegModel;
import com.bsva.entities.CasOpsSotEotCtrlEntity;
import com.bsva.entities.CasOpsTransCtrlMsgEntity;
import com.bsva.entities.CasSysctrlCompParamEntity;
import com.bsva.entities.CasSysctrlSysParamEntity;
import com.bsva.interfaces.AdminBeanRemote;
import com.bsva.interfaces.ServiceBeanRemote;
import com.bsva.interfaces.ValidationBeanRemote;
import com.bsva.utils.PainUnmarshaller;
import com.bsva.utils.Util;

/***
 * 
 * @author DimakatsoN
 *
 */
public class EndOfTransmission_FileLoader implements Serializable {
	@EJB
	PropertyUtil propertyUtil;
	private static final long serialVersionUID = 1L;
	public static Logger log = Logger.getLogger(EndOfTransmission_FileLoader.class);
	public static String systemName = "CAMOWNER";
	public static Date todaysDate;
	public static AdminBeanRemote adminBeanRemote;
	public static ServiceBeanRemote beanRemote;
	public static ValidationBeanRemote valBeanRemote;
	private CasSysctrlSysParamEntity casSysctrlSysParamEntity = null;
	private CasOpsTransCtrlMsgEntity casOpsTransCtrlMsgEntity;
	private CasOpsSotEotCtrlEntity casOpsSotEotCtrlEntity;
	boolean update = false ;
	
	ControlMessage controlMessage;

	CasSysctrlCompParamEntity mdtSysctrlCompParamEntity = null;
    Document document ;
    //String fileName;
	String msgRef = "EOT";
	String achInstId = "210000";
	String backEndProcess = "BACKEND";
	private String eotSchema = "/home/opsjava/Delivery/Mandates/Schema/eot.001.001.02.xsd";
	public static boolean result, unmarshall = false;
	String fileName = null, achMemberId = null, destMemberId = null, serviceName = null,  achLiveTestInd = null, fileType = null;
	String testLiveIndProp = null;
	
	public EndOfTransmission_FileLoader(String filepath, String fileName)
	{
		log.debug("filepath in eot Loadeer----> "+filepath);
		log.debug("fileName in EOT Loadeer----> "+fileName);
		contextCheck();
		contextAdminBeanCheck();
		contextValidationBeanCheck();
		try{
			propertyUtil = new PropertyUtil();
			this.testLiveIndProp = propertyUtil.getPropValue("TestLiveInd");
			//log.info("Test Live Indicator Property: "+testLiveIndProp);
		}catch (Exception e) {
			log.error("EndOfTransmission_FileLoader - Could not find MandateMessageCommons.properties in classpath");
		}
		
		
		this.fileName = fileName;
		casSysctrlSysParamEntity = new CasSysctrlSysParamEntity();
		casOpsTransCtrlMsgEntity = new CasOpsTransCtrlMsgEntity();
		casSysctrlSysParamEntity = (CasSysctrlSysParamEntity) adminBeanRemote.retrieveActiveSysParameter();
		

		try 
		{
			log.debug("inside Loader");
			document = (Document) PainUnmarshaller.unmarshallFile(filepath,"bsva.std.tech.xsd.eot_001_001",fileName, eotSchema);
			log.debug("document---> "+document);
			if(document == null)
				unmarshall = false;
			else{
				unmarshall = true;
				controlMessage = document.getCntrlMsg();
				}

		} 
		catch (Exception ex) 
		{
			unmarshall = false;
			log.error("Error on Unmarshal:" + ex.getMessage());
			ex.printStackTrace();
		}
		if(unmarshall == true)
		{
			log.debug("unmarshall = true ...set objects and validate");
			createOpsFileReg(fileName,filepath);
			createEOTFile(controlMessage);
			
		}

		}
	
	public void createEOTFile(ControlMessage controlMessage)
	{
		log.debug("inside Loader3");
		
		
		// _______________________TRANSACTION CONTROL
		// Unmarshall_______________________
		
		if(controlMessage!= null)
		{
			casOpsTransCtrlMsgEntity.setCtrlMsgId(new BigDecimal(123));
			if( controlMessage.getPrcDte()!= null)
				casOpsTransCtrlMsgEntity.setProcessDate(getGregorianDateWithoutTime(controlMessage.getPrcDte()));
			if(controlMessage.getFrom()!= null && controlMessage.getFrom().getMbrId()!= null)
				casOpsTransCtrlMsgEntity.setMemberIdFm(controlMessage.getFrom().getMbrId());
			if(controlMessage.getTo()!= null && controlMessage.getTo().getMbrId()!= null)
				casOpsTransCtrlMsgEntity.setMemberIdTo(controlMessage.getTo().getMbrId());
			if(controlMessage.getSvcName()!=null)
				casOpsTransCtrlMsgEntity.setServiceId(controlMessage.getSvcName());
			if(controlMessage.getMsgTp() !=null)
				casOpsTransCtrlMsgEntity.setMsgType(controlMessage.getMsgTp().toString());
			if(controlMessage.getTestLive()!=null);
			if(controlMessage.getNmbrFls()!=null)	
				casOpsTransCtrlMsgEntity.setNrOfFiles(Integer.valueOf((controlMessage.getNmbrFls())));
			if(controlMessage.getNmbrRcds()!=null)
				casOpsTransCtrlMsgEntity.setNrOfRecords(Integer.valueOf((controlMessage.getNmbrRcds())));
			if(controlMessage.getValRcds()!= null && controlMessage.getValRcds().getCcy()!= null)
				casOpsTransCtrlMsgEntity.setValueOfRecordsCurr(controlMessage.getValRcds().getCcy());
			casOpsTransCtrlMsgEntity.setActiveInd("Y");
			result = beanRemote.createLoadEOTandSOT(casOpsTransCtrlMsgEntity);
			if(result == true)
			{
				log.debug("mdtAcOpsTransCtrlMsgEntity EOT loaded");

			}
			else{
				log.error("mdtAcOpsTransCtrlMsgEntity EOT not loaded");
			}
			
			
		}
		
		
	}
	


	private String createFileName(String fileType) 
	{
		String achId, creationDate, fileSeqNo, testLiveInd, fileName = null;
		SimpleDateFormat sdfFileDate = new SimpleDateFormat("yyyyMMdd");
		
		try
		{	
				if(mdtSysctrlCompParamEntity != null)
				{
					achId = mdtSysctrlCompParamEntity.getAchId();
					testLiveInd = mdtSysctrlCompParamEntity.getTransamissionInd();
				}
				else
				{
					achId = "021";
					testLiveInd = testLiveIndProp;
				}

					creationDate = sdfFileDate.format(new Date());
					fileSeqNo = "000000";
					fileName = achId+serviceName+"00"+destMemberId+fileType+creationDate+fileSeqNo+testLiveInd+"E";
			}
			catch (Exception e) 
		    {
					log.error("<S"
							+ "EOT> Exception generating fileName for EOT file : " + e.getMessage());
					e.printStackTrace();
			}

		return fileName;
	}
	
	public void createOpsFileReg(String fileName, String filepath)
	{
		OpsFileRegModel opsFileRegModel = new OpsFileRegModel();

		Date date = new Date();
		log.debug("File Created Date");
		
		opsFileRegModel.setFileName(fileName);
		opsFileRegModel.setFilepath(filepath);
		opsFileRegModel.setNameSpace("bsva:std:tech:xsd:eot.001.001.02");
		opsFileRegModel.setProcessDate(date);
		opsFileRegModel.setStatus("LS");
		opsFileRegModel.setInOutInd("I");


		Boolean result = adminBeanRemote.createOpsFileRegModel(opsFileRegModel);
		
		if (result == true) 
		{
			updateSOTIN(achInstId, serviceName);
			log.debug("************* LOAD : "+fileName+" has been created successfully*************************");

		} 
		else 
		{
			log.error("Error on Loading EOT.");
		}
	}
	
public void updateSOTIN(String instgAgt, String service)
{
	
	casOpsSotEotCtrlEntity = new CasOpsSotEotCtrlEntity();
	casOpsSotEotCtrlEntity =  (CasOpsSotEotCtrlEntity) beanRemote.retrieveSOTEOTCntrl(instgAgt, service);
	log.debug("mdtAcOpsSotEotCtrlEntity: "+ casOpsSotEotCtrlEntity);
	
	if(casOpsSotEotCtrlEntity != null)
	{
		if(casOpsSotEotCtrlEntity.getSotOut().equalsIgnoreCase("N"))
		{

		
				casOpsSotEotCtrlEntity.setSotOut("Y");
			
				result = beanRemote.createLoadEOTandSOT(casOpsTransCtrlMsgEntity);

			StartOfTransmissionExtract startOfTransmissionExtract = new StartOfTransmissionExtract(instgAgt, service, "S");
			startOfTransmissionExtract.createStartOfTransmissionFile();
			
			casOpsSotEotCtrlEntity.setSotOut("Y");
			
			

	
		}
	}	

	}
	 public XMLGregorianCalendar getGregorianDateWithoutTime(Date dateToChange) {

	        GregorianCalendar gCalendar = new GregorianCalendar();
	        gCalendar.setTime(dateToChange);
	        XMLGregorianCalendar xmlCalendar = null;
	        try 
	        {
	            xmlCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendarDate(gCalendar.get(Calendar.YEAR), gCalendar.get(Calendar.MONDAY)+1, gCalendar.get(Calendar.DAY_OF_MONTH),DatatypeConstants.FIELD_UNDEFINED);
	        } 
	        catch (Exception ex) {
	            ex.printStackTrace();
	        }
	        return xmlCalendar;
	    }
	 
	 
	 public Date getGregorianDateWithoutTime(XMLGregorianCalendar xmlGregorianCalendar) {

			Calendar cl = xmlGregorianCalendar.toGregorianCalendar();

			return cl.getTime();
		}

	
	 
	 private static void contextAdminBeanCheck() {
			if (adminBeanRemote == null) {
				adminBeanRemote = Util.getAdminBean();
			}

		}

		private static void contextCheck() {
			if (beanRemote == null) {
				beanRemote = Util.getServiceBean();
			}
		}

		public static void contextValidationBeanCheck() {
			if (valBeanRemote == null) {
				valBeanRemote = Util.getValidationBean();
			}

		}
	

}
