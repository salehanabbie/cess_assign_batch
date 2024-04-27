package com.bsva.businessLogic;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.bsva.commons.model.AudSystemProcessModel;
import com.bsva.commons.model.AuditTrackingModel;
import com.bsva.entities.AudSystemProcessEntity;
import com.bsva.entities.AudTrackingEntity;
import com.bsva.translator.AdminTranslator;

public class AuditTrackingLogic {
	
	public static Logger log = Logger.getLogger(AuditTrackingLogic.class);
	
	public AuditTrackingLogic(){
		
	}
	
	public List<AuditTrackingModel> retrieveAllRecordId(List<AudTrackingEntity> audTrackingEntityList) 
	{

		List<AuditTrackingModel> auditTrackingModelList = new ArrayList<AuditTrackingModel>();
		AuditTrackingModel auditTrackingModel;
		

		for (AudTrackingEntity audTrackingEntity: audTrackingEntityList) 
		{

			auditTrackingModel = new AuditTrackingModel();
			auditTrackingModel = new AdminTranslator().translateAudTrackingEntityToCommonsModel(audTrackingEntity);
			  auditTrackingModelList.add(auditTrackingModel);
		}

		return auditTrackingModelList;
	}


	public AudTrackingEntity addrecordId(AuditTrackingModel auditTrackingModel) 
	{
		
		AudTrackingEntity audTrackingEntity = new AdminTranslator().translateCommnsAudTrackingEntity(auditTrackingModel);
			

		return audTrackingEntity;
	}


	
	public AuditTrackingModel retrievedrecordId(AudTrackingEntity audTrackingEntity)
	{
		AuditTrackingModel localModel = new AuditTrackingModel();
		localModel = new AdminTranslator().translateAudTrackingEntityToCommonsModel(audTrackingEntity);
		
		return localModel;
	}
	
	
	//System Audit Logic
	
	public AudSystemProcessEntity translateAudSystemModelToEntity(AudSystemProcessModel audSystemProcessModel) 
	{
		AudSystemProcessEntity audSystemProcessEntity = new AdminTranslator().translateAudSystemModelToEntity(audSystemProcessModel);
		return audSystemProcessEntity;
	}



}
