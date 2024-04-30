package com.bsva.businessLogic;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.bsva.commons.model.SchedulerCommonsModel;
import com.bsva.entities.CasOpsSchedulerEntity;
import com.bsva.entities.CasSysctrlSchedulerEntity;
import com.bsva.translator.AdminTranslator;

public class SchedulerScreenLogic {
	
	/**
	 * @author ElelwaniR
	 *
	 */
	public SchedulerScreenLogic()
	{
		
	}


	public static Logger log = Logger.getLogger(SchedulerScreenLogic.class);
	

	public List<SchedulerCommonsModel> retrieveAllSchedulers(List<CasSysctrlSchedulerEntity> mdtSysctrlSchedulerEntityList)
	{
			List<SchedulerCommonsModel> schedulerCommonsModelList = new ArrayList<SchedulerCommonsModel>();
			SchedulerCommonsModel schedulerCommonsModel;
		
			for (CasSysctrlSchedulerEntity casSysctrlSchedulerEntity : mdtSysctrlSchedulerEntityList) 
			{			
				schedulerCommonsModel = new SchedulerCommonsModel();
				schedulerCommonsModel = new AdminTranslator().translateMdtSysctrlSchedulerEntityToCommonsModel(casSysctrlSchedulerEntity);
				schedulerCommonsModelList.add(schedulerCommonsModel);
			}
		
			return schedulerCommonsModelList;
	}
	
	
	public CasSysctrlSchedulerEntity addMdtSysctrlSchedulerEntity(SchedulerCommonsModel schedulerCommonsModel) 
	{
		CasSysctrlSchedulerEntity casSysctrlSchedulerEntity = new AdminTranslator().translateSchedulerCommonsModelToEntity(schedulerCommonsModel);
	
	return casSysctrlSchedulerEntity;
	}
	
	public SchedulerCommonsModel retrieveMdtSysctrlSchedulerEntity(CasSysctrlSchedulerEntity casSysctrlSchedulerEntity)
	{
		SchedulerCommonsModel localModel = new SchedulerCommonsModel();
		localModel = new AdminTranslator().translateMdtSysctrlSchedulerEntityToCommonsModel(casSysctrlSchedulerEntity);
		
		return localModel;
	}
	
	public List<SchedulerCommonsModel> retrieveAllOpsSchedulers(List<CasOpsSchedulerEntity> opsSchedulerList)
	{
			List<SchedulerCommonsModel> schedulerCommonsModelList = new ArrayList<SchedulerCommonsModel>();
			SchedulerCommonsModel schedulerCommonsModel;
		
			for (CasOpsSchedulerEntity casOpsSchedulerEntity : opsSchedulerList)
			{
				schedulerCommonsModel = new SchedulerCommonsModel();
				schedulerCommonsModel = new AdminTranslator().translateMdtOpsSchedulerEntityToCommonsModel(
                    casOpsSchedulerEntity);
				schedulerCommonsModelList.add(schedulerCommonsModel);
			}
		
			return schedulerCommonsModelList;
	}
	
	public List<SchedulerCommonsModel> retrieveAllAcOpsSchedulers(List<CasOpsSchedulerEntity> mdtAcOpsSchedulerList)
	{
			List<SchedulerCommonsModel> schedulerCommonsModelList = new ArrayList<SchedulerCommonsModel>();
			SchedulerCommonsModel schedulerCommonsModel;
		
			for (CasOpsSchedulerEntity casOpsSchedulerEntity : mdtAcOpsSchedulerList)
			{			
				schedulerCommonsModel = new SchedulerCommonsModel();
				schedulerCommonsModel = new AdminTranslator().translateMdtOpsSchedulerEntityToCommonsModel(
                    casOpsSchedulerEntity);
				schedulerCommonsModelList.add(schedulerCommonsModel);
			}
		
			return schedulerCommonsModelList;
	}
	

}
