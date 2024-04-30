package com.bsva.businessLogic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.bsva.commons.model.CnfgAuthTypeModel;
import com.bsva.entities.CasCnfgAuthTypeEntity;
import com.bsva.translator.AdminTranslator;

public class AuthTypeLogic implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	public List<CnfgAuthTypeModel> retrieveAllAuthTypes(List<CasCnfgAuthTypeEntity> mdtAuthtypeList)
	{

	
		List<CnfgAuthTypeModel> authtypeModelList = new ArrayList<CnfgAuthTypeModel>();
		CnfgAuthTypeModel authytpeModel;
	

		for (CasCnfgAuthTypeEntity casCnfgAuthTypeEntity : mdtAuthtypeList) {
	
			authytpeModel = new CnfgAuthTypeModel();
			authytpeModel = new AdminTranslator().translateCnfgAuthTypeEntityToCommonsModel(
                casCnfgAuthTypeEntity);
			authtypeModelList.add(authytpeModel);
		}

		return authtypeModelList;
	}



	
	public CasCnfgAuthTypeEntity addAuthType(CnfgAuthTypeModel authytpeModel) {

	CasCnfgAuthTypeEntity casCnfgAuthTypeEntity = new AdminTranslator().translateCommnsAuthtypeModelToEntity(authytpeModel);

	return casCnfgAuthTypeEntity;
	}



	public CnfgAuthTypeModel retrieveAuthType(CasCnfgAuthTypeEntity casCnfgAuthTypeEntity)

	{
		CnfgAuthTypeModel localModel = new CnfgAuthTypeModel();
		localModel = new AdminTranslator().translateCnfgAuthTypeEntityToCommonsModel(
            casCnfgAuthTypeEntity);
		
		return localModel;
	}


}
