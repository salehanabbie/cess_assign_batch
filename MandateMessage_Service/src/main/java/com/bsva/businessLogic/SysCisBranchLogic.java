package com.bsva.businessLogic;

import java.util.ArrayList;
import java.util.List;

import com.bsva.commons.model.SysCisBranchModel;
import com.bsva.entities.SysCisBranchEntity;
import com.bsva.translator.AdminTranslator;

/***
 * 
 * @author DimakatsoN
 *
 */



public class SysCisBranchLogic {
	
	
	public List<SysCisBranchModel> retrieveAllSysCisBranchData(List<SysCisBranchEntity>sysCisBranchEntityList ) 
	{
		List<SysCisBranchModel>sysCisBranchModelList = new ArrayList<SysCisBranchModel>();
		
		SysCisBranchModel sysCisBranchModel;
		
		for(SysCisBranchEntity sysCisBranchEntity :  sysCisBranchEntityList)
		{
			sysCisBranchModel = new SysCisBranchModel();
			sysCisBranchModel = new AdminTranslator().translateSysCisBranchEntityToCommonsModel(sysCisBranchEntity);
			sysCisBranchModelList.add(sysCisBranchModel);
					
		}
		return sysCisBranchModelList;
	}
	
	
	public SysCisBranchModel retrieveSysCisBranch(SysCisBranchEntity sysCisBranchEntity)
	{
		SysCisBranchModel localModel = new SysCisBranchModel();
		localModel = new AdminTranslator().translateSysCisBranchEntityToCommonsModel(sysCisBranchEntity);
		
		return localModel;
	}


}
