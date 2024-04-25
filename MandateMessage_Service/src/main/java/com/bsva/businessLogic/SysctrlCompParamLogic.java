package com.bsva.businessLogic;

import java.util.ArrayList;
import java.util.List;

import com.bsva.commons.model.SysctrlCompParamModel;
import com.bsva.entities.CasSysctrlCompParamEntity;
import com.bsva.translator.AdminTranslator;

public class SysctrlCompParamLogic {

	public List<SysctrlCompParamModel> retrieveAllSysctrlCompParam(
			List<CasSysctrlCompParamEntity> mdtSysctrlCompParamEntityList) {

		List<SysctrlCompParamModel> sysctrlCompParamModelList = new ArrayList<SysctrlCompParamModel>();
		
		for (CasSysctrlCompParamEntity localEntity : mdtSysctrlCompParamEntityList) 
		{
			SysctrlCompParamModel localModel = new SysctrlCompParamModel();
			localModel = new  AdminTranslator().translateSysctrlCompParamEntitytoCommonsModel(localEntity);
			sysctrlCompParamModelList.add(localModel);
		}
	
		return sysctrlCompParamModelList;
		
	}

}
