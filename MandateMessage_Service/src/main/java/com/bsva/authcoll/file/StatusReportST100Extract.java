package com.bsva.authcoll.file;

import javax.ejb.EJB;

import org.apache.log4j.Logger;

import com.bsva.PropertyUtil;

/**
 * @author SalehaR
 *
 */
public class StatusReportST100Extract 
{
//	StatusReportS
	
	private Logger log = Logger.getLogger(StatusReportST100Extract.class);
	private String pacs002ServiceId = null;
	
	@EJB
	PropertyUtil propertyUtil;
	
	public StatusReportST100Extract()
	{
		
		try
		{
			propertyUtil = new PropertyUtil();
			pacs002ServiceId = propertyUtil.getPropValue("StatusRep.ST100");

			log.debug("-------pacs002ServiceId------ "+pacs002ServiceId);

		}
		catch (Exception e) 
		{
			log.error("StatusReportST100Extract - Could not find MandateMessageCommons.properties in classpath");
		}
		
		try
		{
			log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!***************************In the ST100 Scheduler call ******************************!!!!!!!!!!!!!!!!!!!!!");
			AC_StatusReport_Pacs002_001_04 ac_StatusReport_Pacs002_001_04 = new AC_StatusReport_Pacs002_001_04();
			ac_StatusReport_Pacs002_001_04.generatePacs002StatusReport(pacs002ServiceId);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			log.error("Error on AC_StatusReport_Pacs002_001_04: "+ex.getMessage() );
		}
	}
	
	
	
	
}
