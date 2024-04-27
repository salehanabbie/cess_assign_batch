package com.bsva.authcoll.file;

import javax.ejb.EJB;

import org.apache.log4j.Logger;

import com.bsva.PropertyUtil;

/**
 * @author SalehaR
 *
 */
public class StatusReportST202Extract
{
	private Logger log = Logger.getLogger(StatusReportST202Extract.class);
	private String pacs002ServiceId = null;
	
	@EJB
	PropertyUtil propertyUtil;
	
	public StatusReportST202Extract()
	{
		try
		{
			propertyUtil = new PropertyUtil();
			pacs002ServiceId = propertyUtil.getPropValue("StatusRep.ST202");

			log.debug("-------pacs002ServiceId------ "+pacs002ServiceId);

		}
		catch (Exception e) 
		{
			log.error("StatusReportST102Extract - Could not find MandateMessageCommons.properties in classpath");
		}
		
		try
		{
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
