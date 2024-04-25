package com.bsva.businessLogic;

import java.util.ArrayList;
import java.util.List;

import com.bsva.commons.model.AudReportsModel;
import com.bsva.entities.AudReportsEntity;
import com.bsva.translator.AdminTranslator;

public class AuditReportsLogic
{

	public AuditReportsLogic()
	{
		
	}
	
	
	public List<AudReportsModel> retrieveAllAuditReports(List<AudReportsEntity> audReportsEntityList)
	{
			List<AudReportsModel> auditReportList = new ArrayList<AudReportsModel>();
		
			for (AudReportsEntity localEntity : audReportsEntityList) 
			{
				AudReportsModel localModel = new AudReportsModel();
				localModel = new  AdminTranslator().translateAudReportsEntityToCommonsModel(localEntity);
				auditReportList.add(localModel);
			}
		
			return auditReportList;
     }
	
	public AudReportsEntity addAuditReport(	AudReportsModel audReportsModel)
	{
		
		AudReportsEntity audReportsEntity = new AdminTranslator().translateCommnsAudReportsEntity(audReportsModel);
			
		return audReportsEntity;
	}
	
	
	
	public AudReportsModel retrieveauditReports(AudReportsEntity audReportsEntity)
	{
		AudReportsModel localModel = new AudReportsModel();
		localModel = new AdminTranslator().translateAudReportsEntityToCommonsModel(audReportsEntity);
		
		return localModel;
	}

}
