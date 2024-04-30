package com.bsva.businessLogic;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.bsva.commons.model.OpsRefSeqNumberCommonsModel;
import com.bsva.entities.CasOpsRefSeqNrEntity;
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
	

	public List<OpsRefSeqNumberCommonsModel> retrieveAllOpsRefSeqNumber(List<CasOpsRefSeqNrEntity> casOpsRefSeqNrEntityList)
	{

		
      List<OpsRefSeqNumberCommonsModel> opsRefSeqNumberCommonsModelList = new ArrayList<OpsRefSeqNumberCommonsModel>();
			OpsRefSeqNumberCommonsModel opsRefSeqNumberCommonsModel;

		
			for (CasOpsRefSeqNrEntity casOpsRefSeqNrEntity : casOpsRefSeqNrEntityList)
			{
			
				opsRefSeqNumberCommonsModel = new OpsRefSeqNumberCommonsModel();
				opsRefSeqNumberCommonsModel = new AdminTranslator().translateOpsRefSeqNumberCommonsModelToEntity(
                    casOpsRefSeqNrEntity);
				opsRefSeqNumberCommonsModelList.add(opsRefSeqNumberCommonsModel);
			}
		
			return opsRefSeqNumberCommonsModelList;
}
	
	
	public CasOpsRefSeqNrEntity addMdtOpsRefSeqNrEntity(OpsRefSeqNumberCommonsModel opsRefSeqNumberCommonsModel)
	{
		CasOpsRefSeqNrEntity casOpsRefSeqNrEntity = new AdminTranslator().translateMdtOpsRefSeqNrEntityToComonsModel(opsRefSeqNumberCommonsModel);
	
	return casOpsRefSeqNrEntity;
	}
	
	public OpsRefSeqNumberCommonsModel retrieveMdtOpsRefSeqNrEntity(
        CasOpsRefSeqNrEntity casOpsRefSeqNrEntity)
	{
		OpsRefSeqNumberCommonsModel localModel = new OpsRefSeqNumberCommonsModel();
		localModel = new AdminTranslator().translateOpsRefSeqNumberCommonsModelToEntity(
            casOpsRefSeqNrEntity);
		
		return localModel;
	}
	
}
