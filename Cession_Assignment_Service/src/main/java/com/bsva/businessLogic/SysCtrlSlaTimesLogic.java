package com.bsva.businessLogic;

import java.util.ArrayList;
import java.util.List;

import com.bsva.commons.model.SysCtrlSlaTimeModel;
import com.bsva.entities.CasSysctrlSlaTimesEntity;
import com.bsva.translator.AdminTranslator;
/**
 * 
 * @author DimakatsoN
 *
 */

public class SysCtrlSlaTimesLogic {
	
	public SysCtrlSlaTimesLogic() {

	}
	
	
	
	public List<SysCtrlSlaTimeModel> retrieveAllSysCtrlSlaTimes(List<CasSysctrlSlaTimesEntity> mdtSysctrlSlaTimesEntityList)
	{

		
			List<SysCtrlSlaTimeModel> sysCtrlSlaTimeModelList = new ArrayList<SysCtrlSlaTimeModel>();
			SysCtrlSlaTimeModel sysCtrlSlaTimeModel;

		
			for (CasSysctrlSlaTimesEntity casSysctrlSlaTimesEntity : mdtSysctrlSlaTimesEntityList) 
			{
		
				sysCtrlSlaTimeModel = new SysCtrlSlaTimeModel();
				sysCtrlSlaTimeModel = new AdminTranslator().translateEntityToSysCtrlSlaTimeModel(casSysctrlSlaTimesEntity);
				sysCtrlSlaTimeModelList.add(sysCtrlSlaTimeModel);
			}
		
			return sysCtrlSlaTimeModelList;
}
	
	
	public CasSysctrlSlaTimesEntity addSysCtrlSlaTimes(SysCtrlSlaTimeModel sysCtrlSlaTimeModel) 
	{
		CasSysctrlSlaTimesEntity casSysctrlSlaTimesEntity = new AdminTranslator().translateModelToMdtSysctrlSlaTimesEntity(sysCtrlSlaTimeModel);
	
	return casSysctrlSlaTimesEntity;
	}
	
	public SysCtrlSlaTimeModel retrieveSysCtrlSlaTimes(CasSysctrlSlaTimesEntity casSysctrlSlaTimesEntity)
	{
		SysCtrlSlaTimeModel localModel = new SysCtrlSlaTimeModel();
		localModel = new AdminTranslator().translateEntityToSysCtrlSlaTimeModel(casSysctrlSlaTimesEntity);
		
		return localModel;
	}

		

}
