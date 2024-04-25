package com.bsva.businessLogic;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.bsva.commons.model.ProcessStatusModel;
import com.bsva.entities.CasSysctrlProcessStatusEntity;
import com.bsva.translator.AdminTranslator;



public class SysctrlProcessStatusLogic 
{
	public static Logger log = Logger.getLogger(SysctrlProcessStatusLogic.class);

	
    public SysctrlProcessStatusLogic(){
		
	}
    
   
	public List<ProcessStatusModel> retreiveAllProcessStatus(List<CasSysctrlProcessStatusEntity> mdtSysctrlProcessStatusList)
	{
		
		List<ProcessStatusModel> processStatusEntityList = new ArrayList<ProcessStatusModel>();

	
		for (CasSysctrlProcessStatusEntity casSysctrlProcessStatusEntity : mdtSysctrlProcessStatusList) 
		{
			
			ProcessStatusModel processStatusModel = new ProcessStatusModel();
			processStatusModel = new AdminTranslator().translateMdtSysctrlProcessStatusEntityToCommonsModel(casSysctrlProcessStatusEntity);
			processStatusEntityList.add(processStatusModel);
		
		}
	       
		return processStatusEntityList;
}



public CasSysctrlProcessStatusEntity addprocessStatus(ProcessStatusModel processStatusModel)
{
	 
	CasSysctrlProcessStatusEntity casSysctrlProcessStatusEntity = new CasSysctrlProcessStatusEntity();
	casSysctrlProcessStatusEntity = new AdminTranslator().translateCommnsProcessStatusModelToEntity(processStatusModel);
	
	

	
	return  casSysctrlProcessStatusEntity; 
}

public static  ProcessStatusModel retreiveProcessStatus(CasSysctrlProcessStatusEntity casSysctrlProcessStatusEntity)
{
	ProcessStatusModel processStatusModel = new ProcessStatusModel();
	processStatusModel = new AdminTranslator().translateMdtSysctrlProcessStatusEntityToCommonsModel(casSysctrlProcessStatusEntity);
	
	return processStatusModel;
   }

	

	
}
