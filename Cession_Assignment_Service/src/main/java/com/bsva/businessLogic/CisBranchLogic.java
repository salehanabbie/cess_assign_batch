package com.bsva.businessLogic;

import java.util.ArrayList;
import java.util.List;

import com.bsva.commons.model.CisBranchModel;
import com.bsva.entities.CisBranchEntity;
import com.bsva.translator.AdminTranslator;

public class CisBranchLogic {

	public List<CisBranchModel> retrieveAllBranch(List<CisBranchEntity> cisBranchEntityList) {
		
		List<CisBranchModel> cisBranchModelList = new ArrayList<CisBranchModel>();
		
		for (CisBranchEntity localEntity : cisBranchEntityList) 
		{
			CisBranchModel localModel = new CisBranchModel();
			localModel = new  AdminTranslator().translateCisBranchEntityToCommonsModel(localEntity);
			cisBranchModelList.add(localModel);
		}
	
		return cisBranchModelList;
		// TODO Auto-generated method stub
	}

}
