package com.bsva.businessLogic;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.bsva.commons.model.OpsRefSeqNumberCommonsModel;
import com.bsva.entities.MdtOpsRefSeqNrEntity;
import com.bsva.translator.AdminTranslator;

public class OpsRefSeqNumberLogic {
	/**
	 * @author ElelwaniR
	 *
	 */
	public OpsRefSeqNumberLogic()
	{
		
	}


	public static Logger log = Logger.getLogger(OpsRefSeqNumberLogic.class);
	

	public List<OpsRefSeqNumberCommonsModel> retrieveAllOpsRefSeqNumber(List<MdtOpsRefSeqNrEntity> mdtOpsRefSeqNrEntityList)
	{

		
      List<OpsRefSeqNumberCommonsModel> opsRefSeqNumberCommonsModelList = new ArrayList<OpsRefSeqNumberCommonsModel>();
			OpsRefSeqNumberCommonsModel opsRefSeqNumberCommonsModel;

		
			for (MdtOpsRefSeqNrEntity mdtOpsRefSeqNrEntity : mdtOpsRefSeqNrEntityList) 
			{
			
				opsRefSeqNumberCommonsModel = new OpsRefSeqNumberCommonsModel();
				opsRefSeqNumberCommonsModel = new AdminTranslator().translateOpsRefSeqNumberCommonsModelToEntity(mdtOpsRefSeqNrEntity);
				opsRefSeqNumberCommonsModelList.add(opsRefSeqNumberCommonsModel);
			}
		
			return opsRefSeqNumberCommonsModelList;
}
	
	
	public MdtOpsRefSeqNrEntity addMdtOpsRefSeqNrEntity(OpsRefSeqNumberCommonsModel opsRefSeqNumberCommonsModel) 
	{
		MdtOpsRefSeqNrEntity mdtOpsRefSeqNrEntity = new AdminTranslator().translateMdtOpsRefSeqNrEntityToComonsModel(opsRefSeqNumberCommonsModel);
	
	return mdtOpsRefSeqNrEntity;
	}
	
	public OpsRefSeqNumberCommonsModel retrieveMdtOpsRefSeqNrEntity(MdtOpsRefSeqNrEntity mdtOpsRefSeqNrEntity)
	{
		OpsRefSeqNumberCommonsModel localModel = new OpsRefSeqNumberCommonsModel();
		localModel = new AdminTranslator().translateOpsRefSeqNumberCommonsModelToEntity(mdtOpsRefSeqNrEntity);
		
		return localModel;
	}
	
}
