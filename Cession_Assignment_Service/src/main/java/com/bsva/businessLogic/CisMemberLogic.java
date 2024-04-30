package com.bsva.businessLogic;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.bsva.commons.model.CisMemberModel;
import com.bsva.commons.model.CustomerParametersModel;
import com.bsva.entities.CisMemberEntity;
import com.bsva.entities.CasSysctrlCustParamEntity;
import com.bsva.translator.AdminTranslator;

public class CisMemberLogic {
	public static Logger log = Logger.getLogger(CisMemberLogic.class);
	
	public List<CisMemberModel> retrieveAllMember(List<CisMemberEntity> cisMemberEntityList) 
	{
		List<CisMemberModel> cisMemberModelList = new ArrayList<CisMemberModel>();
		
		for (CisMemberEntity localEntity : cisMemberEntityList) 
		{
			CisMemberModel localModel = new CisMemberModel();
			localModel = new  AdminTranslator().translateCisMemberEntityToCommonsModel(localEntity);
			cisMemberModelList.add(localModel);
			
		}
		  log.debug("Customer model list from logic method --> " + cisMemberModelList.toString());
		return cisMemberModelList;
	}

	public CisMemberModel retrieveCisMember(CisMemberEntity cisMemberEntity) 
	{
			CisMemberModel localModel = new CisMemberModel();
			localModel = new  AdminTranslator().translateCisMemberEntityToCommonsModel(cisMemberEntity);
			
		return localModel;
	}
	 
	
	
}
