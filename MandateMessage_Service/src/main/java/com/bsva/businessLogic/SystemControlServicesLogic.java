package com.bsva.businessLogic;

import java.util.ArrayList;
import java.util.List;

import com.bsva.commons.model.SystemControlServicesModel;
import com.bsva.entities.CasSysctrlServicesEntity;
import com.bsva.translator.AdminTranslator;

/**
 * 
 * @author NhlanhlaM
 *
 */

public class SystemControlServicesLogic {
	
		public SystemControlServicesLogic()
		{
		
		}


	public CasSysctrlServicesEntity addRecordId1(SystemControlServicesModel systemControlServicesModel)
	{
		CasSysctrlServicesEntity casSysctrlServicesEntity = new CasSysctrlServicesEntity();
		casSysctrlServicesEntity = new AdminTranslator().translateCommonsSystemControlServicesModelToEntity(systemControlServicesModel);
		
		return  casSysctrlServicesEntity; 
	}

	public SystemControlServicesModel retrieveRecordId(CasSysctrlServicesEntity localEntity)
	{
		SystemControlServicesModel systemControlServicesModel = new SystemControlServicesModel();
		systemControlServicesModel = new AdminTranslator().translateMdtSysctrlServicesEntityToCommonsModel(localEntity);

	  return systemControlServicesModel;
	}

	public List<SystemControlServicesModel> retrieveAllRecordId(List<CasSysctrlServicesEntity> allMdtSysctrlServicesEntityList) 
	{
		 List<SystemControlServicesModel> systemControlServicesEntityList = new ArrayList<SystemControlServicesModel>();
	       

			for (CasSysctrlServicesEntity casSysctrlServicesEntity : allMdtSysctrlServicesEntityList)
			{
				SystemControlServicesModel systemControlServicesModel = new SystemControlServicesModel();
				systemControlServicesModel = new AdminTranslator().translateMdtSysctrlServicesEntityToCommonsModel(casSysctrlServicesEntity);
				systemControlServicesEntityList.add(systemControlServicesModel);

			}

			 return systemControlServicesEntityList;
			
		
	  }


}
