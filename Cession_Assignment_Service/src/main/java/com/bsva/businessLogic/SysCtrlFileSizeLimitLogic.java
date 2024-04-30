package com.bsva.businessLogic;

import java.util.ArrayList;
import java.util.List;

import com.bsva.commons.model.SysCtrlFileSizeLimitModel;
import com.bsva.entities.CasSysctrlFileSizeLimitEntity;
import com.bsva.translator.AdminTranslator;
/**
 * 
 * @author DimakatsoN
 *
 */
public class SysCtrlFileSizeLimitLogic 
{
	public List<SysCtrlFileSizeLimitModel> retrieveAllSysctrlFileSizeLimit(List<CasSysctrlFileSizeLimitEntity> mdtSysctrlFileSizeLimitEntityList)
	{

		
			List<SysCtrlFileSizeLimitModel> sysCtrlFileSizeLimitModelList = new ArrayList<SysCtrlFileSizeLimitModel>();
			SysCtrlFileSizeLimitModel sysCtrlFileSizeLimitModel;

		
			for (CasSysctrlFileSizeLimitEntity casSysctrlFileSizeLimitEntity : mdtSysctrlFileSizeLimitEntityList) 
			{
		
				sysCtrlFileSizeLimitModel = new SysCtrlFileSizeLimitModel();
				sysCtrlFileSizeLimitModel = new AdminTranslator().translateMdtSysctrlFileSizeLimitEntityToCommonsModel(casSysctrlFileSizeLimitEntity);
				sysCtrlFileSizeLimitModelList.add(sysCtrlFileSizeLimitModel);
			}
		
			return sysCtrlFileSizeLimitModelList;
}
	
	
	public CasSysctrlFileSizeLimitEntity addSysctrlFileSizeLimit(SysCtrlFileSizeLimitModel sysCtrlFileSizeLimitModel) 
	{
		CasSysctrlFileSizeLimitEntity casSysctrlFileSizeLimitEntity = new AdminTranslator().translateSysCtrlFileSizeLimitCommonsModelToEntity(sysCtrlFileSizeLimitModel);
	
	return casSysctrlFileSizeLimitEntity;
	}
	
	public SysCtrlFileSizeLimitModel retrieveSysctrlFileSizeLimit(CasSysctrlFileSizeLimitEntity casSysctrlFileSizeLimitEntity)
	{
		SysCtrlFileSizeLimitModel localModel = new SysCtrlFileSizeLimitModel();
		localModel = new AdminTranslator().translateMdtSysctrlFileSizeLimitEntityToCommonsModel(casSysctrlFileSizeLimitEntity);
		
		return localModel;
	}


}
