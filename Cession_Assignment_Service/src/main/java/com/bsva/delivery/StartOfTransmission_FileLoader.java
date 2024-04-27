package com.bsva.delivery;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.ejb.EJB;
import javax.xml.datatype.XMLGregorianCalendar;
import org.apache.log4j.Logger;
import bsva.std.tech.xsd.sot_001_001.ControlMessage;
import bsva.std.tech.xsd.sot_001_001.Document;

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

/**
 * 
 * @author DimakatsoN
 *
 */
public class StartOfTransmission_FileLoader implements Serializable
{
	@EJB
	PropertyUtil propertyUtil;
	private static final long serialVersionUID = 1L;
	public static Logger log = Logger.getLogger(StartOfTransmission_FileLoader.class);
	public static String systemName = "MANOWNER";
	public static Date todaysDate;
	public static AdminBeanRemote adminBeanRemote;
	public static ServiceBeanRemote beanRemote;
	public static ValidationBeanRemote valBeanRemote;
	private CasSysctrlSysParamEntity casSysctrlSysParamEntity = null;
	private CasOpsTransCtrlMsgEntity casOpsTransCtrlMsgEntity;
	private CasOpsSotEotCtrlEntity casOpsSotEotCtrlEntity;
	
	String fileName = null, achMemberId = null, destMemberId = null, serviceName = null,  achLiveTestInd = null, fileType = null;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	CasSysctrlCompParamEntity mdtSysctrlCompParamEntity = null;
	Document document;
	public static boolean result, unmarshall = false;
	String msgRef = "SOT";
	String achInstId = "210000";
	String backEndProcess = "BACKEND";
	private String sotSchema = "/home/opsjava/Delivery/Mandates/Schema/sot.001.001.02.xsd";
	ControlMessage controlMessage;
	String testLiveIndProp = null;
	
	public StartOfTransmission_FileLoader (String filepath, String fileName)
	{
		log.debug("filepath in SOT Loader----> "+filepath);
		log.debug("fileName in SOT Loader----> "+fileName);
		contextCheck();
		contextAdminBeanCheck();
		contextValidationBeanCheck();
		try{
			propertyUtil = new PropertyUtil();
			this.testLiveIndProp = propertyUtil.getPropValue("TestLiveInd");
			//log.info("Test Live Indicator Property: "+testLiveIndProp);
		}catch (Exception e) {
			log.error("StartOfTransmission_FileLoader - Could not find MandateMessageCommons.properties in classpath");
		}
		
		this.fileName = fileName;
		casSysctrlSysParamEntity = new CasSysctrlSysParamEntity();
		todaysDate = new Date();
		casOpsTransCtrlMsgEntity = new CasOpsTransCtrlMsgEntity();
		casSysctrlSysParamEntity = (CasSysctrlSysParamEntity) adminBeanRemote.retrieveActiveSysParameter();
		
		try 
		{
			log.debug("inside pain");
			document = (Document) PainUnmarshaller.unmarshallFile(filepath,"bsva.std.tech.xsd.sot_001_001",fileName, sotSchema);
			log.debug("document---> "+document);
			if(document == null)
				unmarshall = false;
			else
			{
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
			createSOTFile(controlMessage);
			
		}
	}
	
	public void createSOTFile(ControlMessage controlMessage)
	{
		log.debug("inside createSOTFile");
	
		
		// _______________________TRANSACTION CONTROL
		// Unmarshall_______________________
		
		if(controlMessage!= null)
		{
			casOpsTransCtrlMsgEntity.setCtrlMsgId(new BigDecimal(123));

			if(controlMessage.getPrcDte()!= null)
				casOpsTransCtrlMsgEntity.setProcessDate(getGregorianDateWithoutTime(controlMessage.getPrcDte()));
			if(controlMessage.getFrom()!= null && controlMessage.getFrom().getMbrId()!= null)
				casOpsTransCtrlMsgEntity.setMemberIdFm(controlMessage.getFrom().getMbrId().toString());
			if(controlMessage.getTo()!= null && controlMessage.getTo().getMbrId()!= null)
				casOpsTransCtrlMsgEntity.setMemberIdTo(controlMessage.getTo().getMbrId());
			if(controlMessage.getSvcName()!= null)
				casOpsTransCtrlMsgEntity.setServiceId(controlMessage.getSvcName());
			if(controlMessage.getMsgTp() != null)
				casOpsTransCtrlMsgEntity.setMsgType(controlMessage.getMsgTp().toString());
			casOpsTransCtrlMsgEntity.setActiveInd("Y");
			//if(controlMessage.getTestLive()!=null);

			result = beanRemote.createLoadEOTandSOT(casOpsTransCtrlMsgEntity);
			if(result == true){
				updateSOTIN(achInstId, serviceName);
				log.debug("mdtAcOpsTransCtrlMsgEntity SOT loaded");
			}
			else{
				log.error("mdtAcOpsTransCtrlMsgEntity SOT not loaded");
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
				
				if(serviceName.equalsIgnoreCase("ST100") || serviceName.equalsIgnoreCase("ST103"))
				  fileType = "S";
			  else
			  {
				  if(serviceName.equalsIgnoreCase("MANOC"))
					  fileType = "A";
				  else
				  {
					  fileType = "M";
				  }
			  }
				

					creationDate = sdfFileDate.format(new Date());
					fileSeqNo = "000000";
					fileName = achId+serviceName+"00"+destMemberId+fileType+creationDate+fileSeqNo+testLiveInd+"S";
			}
			catch (Exception e) 
		    {
					log.error("<SOT> Exception generating fileName for SOT file : " + e.getMessage());
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
		opsFileRegModel.setNameSpace("bsva:std:tech:xsd:sot.001.001.02");
		opsFileRegModel.setProcessDate(date);
		opsFileRegModel.setStatus("LS");
		opsFileRegModel.setInOutInd("I");

		Boolean result = adminBeanRemote.createOpsFileRegModel(opsFileRegModel);
		
		if (result == true) 
		{
			log.debug("********************"+filepath+" has been populated in Ops File Reg.********************");

		} 
		else 
		{
			log.error("<SOT> Error on create Ops File Register");
		}
	}

	
	 public Date getGregorianDateWithoutTime(XMLGregorianCalendar xmlGregorianCalendar) {

			Calendar cl = xmlGregorianCalendar.toGregorianCalendar();

			return cl.getTime();
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
	 	
	 		}
	 	}	

	 	}

	public boolean updateSOTIndicator ()
	{
		boolean saved =false;
		
		
		return saved;
		
		
		
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

