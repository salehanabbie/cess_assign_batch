package com.bsva.businessLogic;

import java.util.ArrayList;
import java.util.List;

import com.bsva.commons.model.SysCisBankModel;
import com.bsva.entities.SysCisBankEntity;
import com.bsva.translator.AdminTranslator;

/**
 * 
 * @author DimakatsoN
 *
 */

public class SysCisBankLogic 
{
	
	public List<SysCisBankModel> retrieveAllSysCisBankData(List<SysCisBankEntity>sysCisBankEntityList ) 
	{
		List<SysCisBankModel>sysCisBankModelList = new ArrayList<SysCisBankModel>();
		
		SysCisBankModel sysCisBankModel;
		
		for(SysCisBankEntity sysCisBankEntity :  sysCisBankEntityList)
		{
			sysCisBankModel = new SysCisBankModel();
			sysCisBankModel = new AdminTranslator().translateSysCisBankEntityToCommonsModel(sysCisBankEntity);
			sysCisBankModelList.add(sysCisBankModel);
					
		}
		return sysCisBankModelList;
	}
	
	
	public SysCisBankModel retrieveSysCisBank(SysCisBankEntity sysCisBankEntity)
	{
		SysCisBankModel localModel = new SysCisBankModel();
		localModel = new AdminTranslator().translateSysCisBankEntityToCommonsModel(sysCisBankEntity);
		
		return localModel;
	}

}
