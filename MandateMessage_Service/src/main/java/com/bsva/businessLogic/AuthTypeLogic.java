package com.bsva.businessLogic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.bsva.commons.model.CnfgAuthTypeModel;
import com.bsva.entities.MdtCnfgAuthTypeEntity;
import com.bsva.translator.AdminTranslator;

public class AuthTypeLogic implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	public List<CnfgAuthTypeModel> retrieveAllAuthTypes(List<MdtCnfgAuthTypeEntity> mdtAuthtypeList)
	{

	
		List<CnfgAuthTypeModel> authtypeModelList = new ArrayList<CnfgAuthTypeModel>();
		CnfgAuthTypeModel authytpeModel;
	

		for (MdtCnfgAuthTypeEntity mdtCnfgAuthTypeEntity : mdtAuthtypeList) {
	
			authytpeModel = new CnfgAuthTypeModel();
			authytpeModel = new AdminTranslator().translateCnfgAuthTypeEntityToCommonsModel(mdtCnfgAuthTypeEntity);
			authtypeModelList.add(authytpeModel);
		}

		return authtypeModelList;
	}



	
	public MdtCnfgAuthTypeEntity  addAuthType(CnfgAuthTypeModel authytpeModel) {

	MdtCnfgAuthTypeEntity mdtCnfgAuthTypeEntity = new AdminTranslator().translateCommnsAuthtypeModelToEntity(authytpeModel);

	return mdtCnfgAuthTypeEntity;
	}



	public CnfgAuthTypeModel retrieveAuthType(MdtCnfgAuthTypeEntity mdtCnfgAuthTypeEntity)

	{
		CnfgAuthTypeModel localModel = new CnfgAuthTypeModel();
		localModel = new AdminTranslator().translateCnfgAuthTypeEntityToCommonsModel(mdtCnfgAuthTypeEntity);
		
		return localModel;
	}


}
