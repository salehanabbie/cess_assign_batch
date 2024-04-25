package com.bsva.delivery;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.ejb.EJB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

import org.apache.log4j.Logger;

import com.bsva.PropertyUtil;
import com.bsva.commons.model.OpsFileRegModel;
import com.bsva.entities.MdtAcOpsSotEotCtrlEntity;
import com.bsva.entities.MdtOpsServicesEntity;
import com.bsva.entities.CasSysctrlCompParamEntity;
import com.bsva.entities.CasSysctrlSysParamEntity;
import com.bsva.interfaces.AdminBeanRemote;
import com.bsva.interfaces.ServiceBeanRemote;
import com.bsva.interfaces.ValidationBeanRemote;
import com.bsva.utils.Util;

import bsva.std.tech.xsd.sot_001_001.ControlMessage;
import bsva.std.tech.xsd.sot_001_001.Document;
import bsva.std.tech.xsd.sot_001_001.MessageReference;
import bsva.std.tech.xsd.sot_001_001.SenderReceiverId;
import bsva.std.tech.xsd.sot_001_001.TestLiveIndicator;

/**
 * @author SalehaR
 *
 */
public class StartOfTransmissionExtract 
{
	@EJB
	PropertyUtil propertyUtil;
	private static AdminBeanRemote adminBeanRemote;
	public static  ServiceBeanRemote beanRemote;
	public static ValidationBeanRemote valBeanRemote;
	private Logger log = Logger.getLogger(StartOfTransmissionExtract.class);
	
	String fileName = null, achMemberId = null, destMemberId = null,serviceName = null,  achLiveTestInd = null, fileType = null;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	CasSysctrlCompParamEntity mdtSysctrlCompParamEntity = null;
	CasSysctrlSysParamEntity casSysctrlSysParamEntity;

	Document document;
	
	String msgRef = "SOT";
	String achInstId = "210000";
	String backEndProcess = "BACKEND";
	private String sotSchema = "/home/opsjava/Delivery/Mandates/Schema/sot.001.001.02.xsd";
	SimpleDateFormat sdfFileDate = new SimpleDateFormat("yyyyMMdd");
	String testLiveIndProp = null;
	
	public StartOfTransmissionExtract(String destMemberId,String serviceName, String fileType)
	{
		this.destMemberId = destMemberId;
		this.serviceName = serviceName;
		this.fileType = fileType;
		contextAdminBeanCheck();
		contextCheck();
		contextValidationBeanCheck();
		
		try{
			propertyUtil = new PropertyUtil();
			this.testLiveIndProp = propertyUtil.getPropValue("TestLiveInd");
			//log.info("Test Live Indicator Property: "+testLiveIndProp);
		}catch (Exception e) {
			log.error("StartOfTransmissionExtract - Could not find MandateMessageCommons.properties in classpath");
		}
	}
	

	public void createStartOfTransmissionFile()
	{
		log.debug("********************GENERATING SOT FILE******************");
		casSysctrlSysParamEntity = new CasSysctrlSysParamEntity();
		casSysctrlSysParamEntity = (CasSysctrlSysParamEntity) adminBeanRemote.retrieveActiveSysParameter();
		log.debug("SOT - mdtSysctrlSysParamEntity in FileExtract: "+casSysctrlSysParamEntity);
		
		//Check if DestMemberId and ServiceName is not Null before proceeding.
		if(destMemberId != null && serviceName != null)
		{
			try
			{
					document = new Document();
					ControlMessage controlMessage = new ControlMessage();
					
					//Retrieve process Date here
					Date procDate = null; /*= new Date();*/
					//TRS16 Processing Rules					    
				    if(casSysctrlSysParamEntity != null && casSysctrlSysParamEntity.getProcessDate() != null)
				    {
				    	procDate = casSysctrlSysParamEntity.getProcessDate();
				    }
				    else
				    {
				    	procDate = new Date();
				    }
					//String processingDarte = sdf.format(procDate);
					
					controlMessage.setPrcDte(getGregorianDateWithoutTime(procDate));
					
					//Retrieve FndCompanyParameters
					mdtSysctrlCompParamEntity = (CasSysctrlCompParamEntity) valBeanRemote.retrieveCompanyParameters(backEndProcess);
					
					
					if(mdtSysctrlCompParamEntity != null)
					{
						achMemberId = mdtSysctrlCompParamEntity.getAchInstId();
						achLiveTestInd = mdtSysctrlCompParamEntity.getTransamissionInd();
					}
					else
					{
						achMemberId = achInstId;
						achLiveTestInd = testLiveIndProp;
					}
					
					SenderReceiverId fromSenderReceiverId = new SenderReceiverId();
					fromSenderReceiverId.setMbrId(achMemberId);
					controlMessage.setFrom(fromSenderReceiverId);
					
					
					SenderReceiverId toSenderReceiverId = new SenderReceiverId();
					toSenderReceiverId.setMbrId(destMemberId);
					controlMessage.setTo(toSenderReceiverId);
					
					controlMessage.setSvcName(serviceName);
					
					controlMessage.setMsgTp(MessageReference.SOT);
					
					if(achLiveTestInd != null)
						controlMessage.setTestLive(TestLiveIndicator.fromValue(mdtSysctrlCompParamEntity.getTransamissionInd()));
					else
						controlMessage.setTestLive(TestLiveIndicator.fromValue(testLiveIndProp));
					
					document.setCntrlMsg(controlMessage);
					
					if(document != null)
						createSOTFile();
			}
			catch(Exception e)
			{
				log.error("Error on generating SOT file.... "+e.getMessage());
				e.printStackTrace();
			}
		}
		else
		{
			if(destMemberId == null)
				log.error("********************<SOTDM> Start of transmission cannot be created. The destination member is empty!********************");
			else
				if(serviceName == null)
					log.error("********************<SOTDM> Start of transmission cannot be created. The service id is empty!********************");
		}		
}
	
	public void createManualStartOfTransmissionFile(String destMemberId, String serviceName)
	{
		log.debug("********************GENERATING SOT FILE******************");
		//Check if DestMemberId and ServiceName is not Null before proceeding.
		//Check if the SOT and EOT indicators if is set N.
	
		
		if(destMemberId != null && serviceName != null)
		{
			try
			{
					document = new Document();
					ControlMessage controlMessage = new ControlMessage();
					
					//Retrieve process Date here
					Date procDate = new Date();
					//String processingDarte = sdf.format(procDate);
					
					controlMessage.setPrcDte(getGregorianDateWithoutTime(procDate));
					
					//Retrieve FndCompanyParameters
					mdtSysctrlCompParamEntity = (CasSysctrlCompParamEntity) valBeanRemote.retrieveCompanyParameters(backEndProcess);
					
					if(mdtSysctrlCompParamEntity != null)
					{
						achMemberId = mdtSysctrlCompParamEntity.getAchInstId();
						achLiveTestInd = mdtSysctrlCompParamEntity.getTransamissionInd();
					}
					else
					{
						achMemberId = achInstId;
					}
					
					SenderReceiverId fromSenderReceiverId = new SenderReceiverId();
					fromSenderReceiverId.setMbrId(achMemberId);
					controlMessage.setFrom(fromSenderReceiverId);
					
					
					SenderReceiverId toSenderReceiverId = new SenderReceiverId();
					toSenderReceiverId.setMbrId(destMemberId);
					controlMessage.setTo(toSenderReceiverId);
					
					controlMessage.setSvcName(serviceName);
					
					controlMessage.setMsgTp(MessageReference.SOT);
					
					if(achLiveTestInd != null)
						controlMessage.setTestLive(TestLiveIndicator.fromValue(mdtSysctrlCompParamEntity.getTransamissionInd()));
					else
						controlMessage.setTestLive(TestLiveIndicator.fromValue(testLiveIndProp));
					
					document.setCntrlMsg(controlMessage);
					
					if(document != null)
						createSOTFile();
			}
			catch(Exception e)
			{
				log.error("Error on generating SOT file.... "+e.getMessage());
				e.printStackTrace();
			}
		}
		else
		{
			if(destMemberId == null)
				log.error("********************<SOTDM> Start of transmission cannot be created. The destination member is empty!********************");
			else
				if(serviceName == null)
					log.error("********************<SOTDM> Start of transmission cannot be created. The service id is empty!********************");
		}
		  
         
        
}
	
	
	public  void createSOTFile() 
	{

		try 
		{
			    String outFileName = createFileName(fileType); 
	
//				String out ="/home/jboss/Mandates/Output/Delivery/SOT/"+outFileName+".xml";
//				File f = new File("/home/jboss/Mandates/Output/Delivery/SOT/" + outFileName +".xml")  ;  
			    String out ="/home/opsjava/Delivery/Mandates/Output/temp/"+outFileName+".xml";
				File f = new File("/home/opsjava/Delivery/Mandates/Output/temp/" + outFileName +".xml")  ;  
		          JAXBContext jaxbContext = JAXBContext.newInstance(Document.class);
		          Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		
		          // format the XML output
		          jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		          
		          jaxbMarshaller.setProperty("com.sun.xml.bind.xmlDeclaration", Boolean.FALSE);
		          jaxbMarshaller.setProperty("com.sun.xml.bind.xmlHeaders","<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		
		          QName qName = new QName("urn:bsva:std:tech:xsd:sot.001.001.02", "Document");
		          JAXBElement<Document> root = new JAXBElement<Document>(qName, Document.class, document);
		
		          jaxbMarshaller.marshal(root, f);
					
				createOpsFileReg(outFileName, out);
				copyFile(outFileName);
				log.debug("********************"+outFileName+" created successfully********************");
		} 
		catch (Exception e) 
		{
			log.error("<SOT> Error on createSOTFile: "+e.getMessage());
			e.printStackTrace();
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

				//Retrieve the msgType from the service Table
				MdtOpsServicesEntity mdtOpsServicesEntity = (MdtOpsServicesEntity) adminBeanRemote.retrieveOpsService(serviceName);
				
				if(mdtOpsServicesEntity != null)
				{
					fileType = mdtOpsServicesEntity.getMsgTypeId();
				}
				
// Removed hardcoding ---retrieve from database				
//				if(serviceName.equalsIgnoreCase("ST100") || serviceName.equalsIgnoreCase("ST103"))
//				  fileType = "S";
//			  else
//			  {
//				  if(serviceName.equalsIgnoreCase("MANOC"))
//					  fileType = "A";
//				  else
//				  {
//					  fileType = "M";
//				  }
//			  }
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

	public void createOpsFileReg(String outFileName, String outFilePath)
	{
		OpsFileRegModel opsFileRegModel = new OpsFileRegModel();

		Date date = new Date();
		log.debug("File Created Date");
		
		opsFileRegModel.setFileName(outFileName);
		opsFileRegModel.setFilepath(outFilePath);
		opsFileRegModel.setNameSpace("bsva:std:tech:xsd:sot.001.001.02");
		opsFileRegModel.setProcessDate(date);
		opsFileRegModel.setStatus("C");
		opsFileRegModel.setOnlineInd("N");
		opsFileRegModel.setInOutInd("O");
		Boolean result = adminBeanRemote.createOpsFileRegModel(opsFileRegModel);
		
		if (result == true) 
		{
			log.debug("********************"+outFileName+" has been created successfully. Output in TEMP directory.********************");

		} 
		else 
		{
			log.error("<SOT> Error on create Ops File Register");
		}
	}

	public  void copyFile(String fileName) throws IOException 
	{
		log.debug("Copying "+fileName+"from temp  to output directory...");
		File tmpFile = new File("/home/opsjava/Delivery/Mandates/Output/" + fileName +".xml");
		String outputFile = "/home/opsjava/Delivery/Mandates/Output/temp/" + fileName +".xml";
		FileOutputStream fos = new FileOutputStream(tmpFile);
		Path source = Paths.get(outputFile);
		Files.copy(source, fos);
	}
	
	private static void contextCheck() {
		if (beanRemote == null) {
			beanRemote = Util.getServiceBean();
		}
	}
	
	public static void contextValidationBeanCheck() 
	{
		if (valBeanRemote == null) {
			valBeanRemote = Util.getValidationBean();
		}				
	}
	
	private static void contextAdminBeanCheck() 
	{
		if (adminBeanRemote == null) 
		{
			adminBeanRemote = Util.getAdminBean();	
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
	
	public XMLGregorianCalendar getGregorianDate(Date dateToChange, boolean useTimeZone) {

        GregorianCalendar gCalendar = new GregorianCalendar();
        gCalendar.setTime(dateToChange);
        XMLGregorianCalendar xmlCalendar = null;
        try {
            xmlCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gCalendar);
            if(useTimeZone)
            {
            	xmlCalendar.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
            }
        } 
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return xmlCalendar;
    }

	
	
}
