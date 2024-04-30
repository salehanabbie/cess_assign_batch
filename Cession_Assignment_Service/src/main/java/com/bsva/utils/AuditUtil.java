package com.bsva.utils;

import java.math.BigDecimal;

import org.apache.log4j.Logger;

import com.bsva.beans.GenericDAO;
import com.bsva.businessLogic.CisDownloadLogic;
import com.bsva.commons.model.AuditTrackingModel;
import com.bsva.entities.AudTrackingEntity;
import com.bsva.interfaces.AdminBeanRemote;

/**
 * 
 * @author DimakatsoN
 *
 */

public class AuditUtil {
	
	public static Logger log = Logger.getLogger(AuditUtil.class);
	private static AdminBeanRemote adminBeanRemote;
	
	
	//GenericDAO genericDAO;
	public static boolean auditTracking( AuditTrackingModel auditTrackingModel){
		
			boolean saved = false;

		AudTrackingEntity audTrackingEntity = new AudTrackingEntity();
		audTrackingEntity.setRecordId(auditTrackingModel.getRecordId());
		audTrackingEntity.setUserId(auditTrackingModel.getUserId());
		audTrackingEntity.setActionDate(auditTrackingModel.getActionDate());
		audTrackingEntity.setTableName(auditTrackingModel.getTableName());
		audTrackingEntity.setColumnName(auditTrackingModel.getColumnName());
		audTrackingEntity.setRecord(auditTrackingModel.getRecord());
		audTrackingEntity.setOldValue(auditTrackingModel.getOldValue());
		audTrackingEntity.setNewValue(auditTrackingModel.getNewValue());
		audTrackingEntity.setAction(auditTrackingModel.getAction());
		saved=adminBeanRemote.createAuditTracking(audTrackingEntity);
		log.debug("audTrackingEntity after save*********" +audTrackingEntity);
	
		if (saved) 
		{
			log.debug("Audit Tracking Table has been populated...");
		}
		else{
			log.debug("Audit Tracking  Table is not populated...");
		}	
		return saved;
	}
}
