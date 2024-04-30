package com.bsva.businessLogic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.bsva.commons.model.MdtSysCtrlSysParamModel;
import com.bsva.entities.CasSysctrlSysParamEntity;
import com.bsva.translator.AdminTranslator;

public class SysCtrlSysParamLogic implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public List<MdtSysCtrlSysParamModel> retrieveAllSysCtrlSysParam(
			List<CasSysctrlSysParamEntity> mdtSysctrlSysParamEntityList) {
		
		List<MdtSysCtrlSysParamModel> sysCtrlSysParamModelList = new ArrayList<MdtSysCtrlSysParamModel>();
		
		
		
		for (CasSysctrlSysParamEntity localEntity : mdtSysctrlSysParamEntityList) 
		{
			MdtSysCtrlSysParamModel localModel = new MdtSysCtrlSysParamModel();
			
			localModel = new  AdminTranslator().translateMdtSysCtrlSysParamEntityToCommonsModel(localEntity);
			sysCtrlSysParamModelList.add(localModel);
		}
	
		return sysCtrlSysParamModelList;

	}
	
	

}
