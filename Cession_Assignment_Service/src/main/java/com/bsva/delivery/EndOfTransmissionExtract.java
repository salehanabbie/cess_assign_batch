package com.bsva.delivery;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.ejb.EJB;
import javax.xml.XMLConstants;
import javax.xml.bind.Marshaller;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.apache.log4j.Logger;

import bsva.std.tech.xsd.eot_001_001.ControlMessage;
import bsva.std.tech.xsd.eot_001_001.Document;
import bsva.std.tech.xsd.eot_001_001.MessageType;
import bsva.std.tech.xsd.eot_001_001.SenderReceiverId;
import bsva.std.tech.xsd.eot_001_001.TestLiveIndicator;

import com.bsva.PropertyUtil;
import com.bsva.commons.model.OpsFileRegModel;
import com.bsva.entities.MandatesCountCommonsModelEntity;
import com.bsva.entities.CasOpsSotEotCtrlEntity;
import com.bsva.entities.CasSysctrlCompParamEntity;
import com.bsva.entities.CasSysctrlSysParamEntity;
import com.bsva.entities.StatusReportEotModelEntity;
import com.bsva.interfaces.AdminBeanRemote;
import com.bsva.interfaces.ServiceBeanRemote;
import com.bsva.interfaces.ValidationBeanRemote;
import com.bsva.utils.Util;

public class EndOfTransmissionExtract 
{
	@EJB
	PropertyUtil propertyUtil;
	public static AdminBeanRemote adminBeanRemote;
	public static  ServiceBeanRemote beanRemote;
	public static ValidationBeanRemote valBeanRemote;
	private Logger log = Logger.getLogger(EndOfTransmissionExtract.class);
	

	String fileName = null, achMemberId = null, destMemberId = null, serviceName = null,  achLiveTestInd = null, fileType = null;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	CasSysctrlCompParamEntity mdtSysctrlCompParamEntity = null;
	CasSysctrlSysParamEntity casSysctrlSysParamEntity;
    Document document ;
	List<CasOpsSotEotCtrlEntity>sotEotCtrlList;
	
	String xmlDateFormat = "yyyy-MM-dd'T'HH:mm:ss"; 
	String msgRef = "EOT";
	String achInstId = "210000";
	String backEndProcess = "BACKEND";
	private String eotSchema = "/home/opsjava/Delivery/Mandates/Schema/eot.001.001.02.xsd";
	String testLiveIndProp = null;
	
	public EndOfTransmissionExtract(String destMemberId, String serviceName, String fileType)
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
			log.error("EndOfTransmissionExtract - Could not find MandateMessageCommons.properties in classpath");
		}
	}
	
	public void createEndOfTransmissionFile()
	{
		log.debug("********************GENERATING EOT FILE******************");
		casSysctrlSysParamEntity = new CasSysctrlSysParamEntity();
		casSysctrlSysParamEntity = (CasSysctrlSysParamEntity) adminBeanRemote.retrieveActiveSysParameter();
		log.debug("EOT - mdtSysctrlSysParamEntity in FileExtract: "+casSysctrlSysParamEntity);

		boolean saved ;
		
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
					controlMessage.setMsgTp(MessageType.EOT);

					if(achLiveTestInd != null)
					{
						controlMessage.setTestLive(TestLiveIndicator.fromValue(mdtSysctrlCompParamEntity.getTransamissionInd()));
					}
					else
					{
						controlMessage.setTestLive(TestLiveIndicator.fromValue(testLiveIndProp));
					}
					
					BigDecimal nrOfFiles = BigDecimal.ZERO, nrOfMsgs = BigDecimal.ZERO;
					

					sotEotCtrlList = new ArrayList<CasOpsSotEotCtrlEntity>();
					sotEotCtrlList = (List<CasOpsSotEotCtrlEntity>)adminBeanRemote.retrieveAcitiveSot(destMemberId,serviceName,"Y");
					if(sotEotCtrlList != null &&  sotEotCtrlList.size() >0)
					{
						log.debug("sotEotCtrlList---------->"+sotEotCtrlList);
						if(serviceName.equalsIgnoreCase("MANOT")||serviceName.equalsIgnoreCase("MANOM")||serviceName.equalsIgnoreCase("MANCO")||serviceName.equalsIgnoreCase("MANOC" )|| serviceName.equalsIgnoreCase("MANRO")||
							serviceName.equalsIgnoreCase("MANRF")||serviceName.equalsIgnoreCase("ST103")||serviceName.equalsIgnoreCase("SROUT")||serviceName.equalsIgnoreCase("SPOUT")||serviceName.equalsIgnoreCase("MANDC"))
						{

							List<MandatesCountCommonsModelEntity> mdtlist;
							mdtlist = (List<MandatesCountCommonsModelEntity>) adminBeanRemote.retrieveOpsCount(destMemberId, serviceName);
							if(mdtlist != null && mdtlist.size() > 0)
							{
								MandatesCountCommonsModelEntity mandatesCountCommonsModelEntity = mdtlist.get(0);
								log.debug("mandatesCountCommonsModelEntity: "+mandatesCountCommonsModelEntity);

								if(mandatesCountCommonsModelEntity != null)
								{
									if(mandatesCountCommonsModelEntity.getNrOfFiles() != null)
										nrOfFiles = mandatesCountCommonsModelEntity.getNrOfFiles();	

									if(mandatesCountCommonsModelEntity.getNrOfMsgs() != null)
										nrOfMsgs = mandatesCountCommonsModelEntity.getNrOfMsgs();
								}
								
								if(serviceName.equalsIgnoreCase("ST103")||serviceName.equalsIgnoreCase("SROUT")||serviceName.equalsIgnoreCase("SPOUT"))
								{
									controlMessage.setNmbrRcds(String.valueOf("0"));
								}
								else

									if(mandatesCountCommonsModelEntity.getNrOfMsgs() != null)
										controlMessage.setNmbrRcds(nrOfMsgs.toString());

								if(mandatesCountCommonsModelEntity.getNrOfFiles() != null)
									controlMessage.setNmbrFls(nrOfFiles.toString());
							}
							else
							{
								controlMessage.setNmbrFls("0");
								controlMessage.setNmbrRcds("0");
							}
						}
						else
						{
							// Generate Number of Files for Status Reports
							if(serviceName.equalsIgnoreCase("ST100") ||serviceName.equalsIgnoreCase("ST102")||serviceName.equalsIgnoreCase("ST104")||serviceName.equalsIgnoreCase("ST105")||
							   serviceName.equalsIgnoreCase("ST106")||serviceName.equalsIgnoreCase("ST007")||serviceName.equalsIgnoreCase("ST008")||serviceName.equalsIgnoreCase("ST994"))
							{
								List<StatusReportEotModelEntity> statusReportEotList;
								statusReportEotList = (List<StatusReportEotModelEntity>) adminBeanRemote.retrieveStatusReport(destMemberId, serviceName);

								if(statusReportEotList != null && statusReportEotList.size() > 0)
								{
									StatusReportEotModelEntity statusReportEotModelEntity = statusReportEotList.get(0);
									log.debug("StatusReportEotModelEntity: "+statusReportEotModelEntity);

									if(statusReportEotModelEntity != null)
									{
										if(statusReportEotModelEntity.getNumberOfFiles() != null)
											nrOfFiles = statusReportEotModelEntity.getNumberOfFiles();	

									}

									controlMessage.setNmbrFls(nrOfFiles.toString());
									controlMessage.setNmbrRcds("0");
								}
								else
								{
									controlMessage.setNmbrFls("0");
									controlMessage.setNmbrRcds("0");
								}
							}
						}

						document.setCntrlMsg(controlMessage);

						if(document != null)
							createEOTFile();

					}
			}

			catch(Exception e)
			{
				log.error("Error on generating EOT file.... "+e.getMessage());
				e.printStackTrace();
			}
		}
		
		
		else
		{
			if(destMemberId == null)
				
				log.error("********************<EOTDM> End of transmission cannot be created. The destination member is empty!********************");
			else
				
				if(serviceName == null)
					log.error("********************<EOTDM> End of transmission cannot be created. The service id is empty!********************");
		}		
}
	
	
	
	public void createManualEndOfTransmissionFile(String destMemberId, String serviceName)
	{
		log.debug("********************GENERATING EOT FILE******************");
		
//		
//		List <MandatesCountCommonsModel> mdtOpsMndtCountList = new ArrayList<MandatesCountCommonsModel>();
//		mdtOpsMndtCountList =(List<MandatesCountCommonsModel>) adminBeanRemote.retrieveOpsCount(instId, serviceId)
		boolean saved ;
		
		//Check if DestMemberId and ServiceName is not Null before proceeding.
		if(destMemberId != null && serviceName != null)
		{
			try
			{
				
				//Retrieve Counts 
				
//				MandatesCountCommonsModelEntity mandatesCountCommonsModelEntity = new MandatesCountCommonsModelEntity();
//				mandatesCountCommonsModelEntity = (MandatesCountCommonsModelEntity) adminBeanRemote.retrieveOpsCount(destMemberId, serviceName);
//				log.debug("mandatesCountCommonsModelEntity: "+mandatesCountCommonsModelEntity);
	
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
					controlMessage.setMsgTp(MessageType.EOT);
					
					//controlMessage.setMsgRef(MessageReference.SOT);
					

					if(achLiveTestInd != null)
						controlMessage.setTestLive(TestLiveIndicator.fromValue(mdtSysctrlCompParamEntity.getTransamissionInd()));
					else
						controlMessage.setTestLive(TestLiveIndicator.fromValue(testLiveIndProp));

					BigDecimal nrOfFiles = BigDecimal.ZERO, nrOfMsgs = BigDecimal.ZERO;
					

					sotEotCtrlList = new ArrayList<CasOpsSotEotCtrlEntity>();
					sotEotCtrlList = (List<CasOpsSotEotCtrlEntity>)adminBeanRemote.retrieveAcitiveSot(destMemberId,serviceName,"Y");
					
					if(sotEotCtrlList != null &&  sotEotCtrlList.size() >0)
					{
						log.debug("sotEotCtrlList---------->"+sotEotCtrlList);
					if(serviceName.equalsIgnoreCase("MANOT")||serviceName.equalsIgnoreCase("MANOM")||serviceName.equalsIgnoreCase("MANCO")||serviceName.equalsIgnoreCase("MANOC" )||serviceName.equalsIgnoreCase("MANRO")||serviceName.equalsIgnoreCase("MANRF")||
							serviceName.equalsIgnoreCase("ST103")||serviceName.equalsIgnoreCase("SROUT")||serviceName.equalsIgnoreCase("SPOUT")||serviceName.equalsIgnoreCase("MANDC"))
					{
						List<MandatesCountCommonsModelEntity> mdtlist;
						mdtlist = (List<MandatesCountCommonsModelEntity>) adminBeanRemote.retrieveOpsCount(destMemberId, serviceName);
					

						if(mdtlist != null && mdtlist.size() > 0)
						{
							log.debug("mdtlist---------->"+mdtlist);
							MandatesCountCommonsModelEntity mandatesCountCommonsModelEntity = mdtlist.get(0);
							log.debug("mandatesCountCommonsModelEntity: "+mandatesCountCommonsModelEntity);

							if(mandatesCountCommonsModelEntity != null)
							{
								if(mandatesCountCommonsModelEntity.getNrOfFiles() != null)
									nrOfFiles = mandatesCountCommonsModelEntity.getNrOfFiles();	

								if(mandatesCountCommonsModelEntity.getNrOfMsgs() != null)
									nrOfMsgs = mandatesCountCommonsModelEntity.getNrOfMsgs();
								
							}
							if(serviceName.equalsIgnoreCase("ST103")||serviceName.equalsIgnoreCase("SROUT")||serviceName.equalsIgnoreCase("SPOUT"))
							{
								controlMessage.setNmbrRcds(String.valueOf("0"));
							}
							else
							
								if(mandatesCountCommonsModelEntity.getNrOfMsgs() != null)
									controlMessage.setNmbrRcds(nrOfMsgs.toString());
							if(mandatesCountCommonsModelEntity.getNrOfFiles() != null)
								controlMessage.setNmbrFls(nrOfFiles.toString());
						}
					}
						
					else{
						// Generate Number of Files for Status Reports

						if(serviceName.equalsIgnoreCase("ST100") ||serviceName.equalsIgnoreCase("ST102")||serviceName.equalsIgnoreCase("ST104")||serviceName.equalsIgnoreCase("ST105")||
						   serviceName.equalsIgnoreCase("ST106")||serviceName.equalsIgnoreCase("ST007")||serviceName.equalsIgnoreCase("ST008")||serviceName.equalsIgnoreCase("ST994"))
						{
						List<StatusReportEotModelEntity> statusReportEotList;
						statusReportEotList = (List<StatusReportEotModelEntity>) adminBeanRemote.retrieveStatusReport(destMemberId, serviceName);

						if(statusReportEotList != null && statusReportEotList.size() > 0)
						{
							StatusReportEotModelEntity statusReportEotModelEntity = statusReportEotList.get(0);
							log.debug("StatusReportEotModelEntity: "+statusReportEotModelEntity);

							if(statusReportEotModelEntity != null)
							{
								if(statusReportEotModelEntity.getNumberOfFiles() != null)
									nrOfFiles = statusReportEotModelEntity.getNumberOfFiles();	

							}
							controlMessage.setNmbrFls(nrOfFiles.toString());
							controlMessage.setNmbrRcds("0");
						}
						}

					}

					document.setCntrlMsg(controlMessage);
				
					if(document != null)
						createEOTFile();
			}
			
			}
			catch(Exception e)
			{
				log.error("Error on generating EOT file.... "+e.getMessage());
				e.printStackTrace();
			}
		}
		
		
		else
		{
			if(destMemberId == null)
				
				log.error("********************<EOTDM> End of transmission cannot be created. The destination member is empty!********************");
			else
				
				if(serviceName == null)
					log.error("********************<EOTDM> End of transmission cannot be created. The service id is empty!********************");
		}		
}
	
	
	
	public  void createEOTFile() 
	{

		try 
		{
			    String outFileName = createFileName(fileType); 
	
//				String out ="/home/jboss/Mandates/Output/Delivery/EOT/"+outFileName+".xml";
//				File f = new File("/home/jboss/Mandates/Output/Delivery/EOT/" + outFileName +".xml");  
			    String out ="/home/opsjava/Delivery/Mandates/Output/temp/"+outFileName+".xml";
				File f = new File("/home/opsjava/Delivery/Mandates/Output/temp/" + outFileName +".xml")  ;  
				
				SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
				Schema schema = sf.newSchema(new File(eotSchema));
				
		          JAXBContext jaxbContext = JAXBContext.newInstance(Document.class);
		          Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		          jaxbMarshaller.setSchema(schema);
		
		          // format the XML output
		          jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		          jaxbMarshaller.setProperty("com.sun.xml.bind.xmlDeclaration", Boolean.FALSE);
		          jaxbMarshaller.setProperty("com.sun.xml.bind.xmlHeaders","<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		
		          QName qName = new QName("urn:bsva:std:tech:xsd:eot.001.001.02", "Document");
		          
		          JAXBElement<Document> root = new JAXBElement<Document>(qName, Document.class, document);
		
		          jaxbMarshaller.marshal(root, f);
					
				createOpsFileReg(outFileName, out);
				copyFile(outFileName);
				log.debug("********************"+outFileName+" created successfully********************");
		
		
					} 
		
		catch (Exception e) 
		{
			log.error("<EOT> Error on createEOTFile: "+e.getMessage());
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

					creationDate = sdfFileDate.format(new Date());
//					fileSeqNo = "000000";
//					SCR 219 
					fileSeqNo = "999999";
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

	
	public void createOpsFileReg(String outFileName, String outFilePath)
	
	{
		OpsFileRegModel opsFileRegModel = new OpsFileRegModel();

		Date date = new Date();
		log.debug("File Created Date");
		
		opsFileRegModel.setFileName(outFileName);
		opsFileRegModel.setFilepath(outFilePath);
		opsFileRegModel.setNameSpace("bsva:std:tech:xsd:eot.001.001.02");
		opsFileRegModel.setProcessDate(date);
		opsFileRegModel.setStatus("C");
		opsFileRegModel.setOnlineInd("N");
		opsFileRegModel.setInOutInd("O");

		Boolean result = adminBeanRemote.createOpsFileRegModel(opsFileRegModel);
		
		if (result == true) 
		{
			log.debug("********************"+outFileName+" has been created successfully in the TEMP directory.********************");

		}
		
		else 
		{
			log.error("<EOT> Error on create Ops File Register");
		}
	}

	public  void copyFile(String fileName) throws IOException 
	{
		File tmpFile = new File("/home/opsjava/Delivery/Mandates/Output/" + fileName +".xml");
		String outputFile = "/home/opsjava/Delivery/Mandates/Output/temp/" + fileName +".xml";
		FileOutputStream fos = new FileOutputStream(tmpFile);
		Path source = Paths.get(outputFile);
		Files.copy(source, fos);
		log.info("Copying "+fileName+" from temp  to output directory...");
	}
	
	public static void contextCheck()
	
	{
		if (beanRemote == null)
		{
			beanRemote = Util.getServiceBean();
		}
	}
	
	public static void contextValidationBeanCheck() 
	{
		if (valBeanRemote == null) 
		{
			valBeanRemote = Util.getValidationBean();
		}				
	}
	
	public static void contextAdminBeanCheck() 
	{
		if (adminBeanRemote == null) 
		{
			adminBeanRemote = Util.getAdminBean();	
		}
	}
	
	public XMLGregorianCalendar getGregorianDate(Date dateToChange, boolean useTimeZone) 
	
	{

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
        catch (Exception ex)
        
        {
            ex.printStackTrace();
        }
        return xmlCalendar;
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
	 
	 
		public Date getCovertDateTime(XMLGregorianCalendar xmlGregorianCalendar) {

			Calendar cl = xmlGregorianCalendar.toGregorianCalendar();

			return cl.getTime();
		}
	


}
