package com.bsva.businessLogic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


import com.bsva.commons.model.IamSessionModel;
import com.bsva.entities.IamSessionEntity;
import com.bsva.translator.AdminTranslator;

/**
 * 
 * @author DimakatsoN
 *
 */

public class IamSessionLogic implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public IamSessionLogic(){
		
	}

	
	public List<IamSessionModel> retreiveIamSessions (List<IamSessionEntity> iamSessionList)
	{
		List<IamSessionModel> iamSessionModelList= new ArrayList<IamSessionModel>();
		IamSessionModel iamSessionModel;
		
		for(IamSessionEntity iamSessionEntity:iamSessionList)
		{
			iamSessionModel = new IamSessionModel();
			iamSessionModel = new AdminTranslator().translateEntityToIamSessionModel(iamSessionEntity);
			iamSessionModelList.add(iamSessionModel);
		}
		return iamSessionModelList;
	}
	
	
	public IamSessionEntity addIamSession(IamSessionModel iamSessionModel)
	{
		IamSessionEntity iamSessionEntity = new AdminTranslator().translateModelToIamSessionEntity(iamSessionModel);
		
		return iamSessionEntity;
	}
	
	
	public IamSessionModel retrieveIamSession(IamSessionEntity iamSessionEntity)

	{
		IamSessionModel iamSessionModel = new IamSessionModel();
		iamSessionModel = new AdminTranslator().translateEntityToIamSessionModel(iamSessionEntity);
		
		return iamSessionModel;
	}
	
	
}
