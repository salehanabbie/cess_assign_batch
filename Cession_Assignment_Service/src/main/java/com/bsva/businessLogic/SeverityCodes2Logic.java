package com.bsva.businessLogic;

import java.util.ArrayList;
import java.util.List;

import com.bsva.commons.model.SeverityCodesModel;
import com.bsva.entities.CasCnfgSeverityCodesEntity;
import com.bsva.translator.AdminTranslator;

public class SeverityCodes2Logic 
{
	
	public SeverityCodes2Logic()
	{
		
	}


	public SeverityCodesModel retreiveSeverityCodes(CasCnfgSeverityCodesEntity localEntity)
	{
		
		SeverityCodesModel severityCodesModel = new SeverityCodesModel();
		severityCodesModel = new AdminTranslator().translateMdtCnfgSeverityCodesEntityTocommonsModel(localEntity);
		
		return severityCodesModel;
	}

	public List<SeverityCodesModel> retreiveAllSeverityCode(List<CasCnfgSeverityCodesEntity> allCasCnfgSeverityCodesEntityList)
	{

		List<SeverityCodesModel> addTypeList = new ArrayList<SeverityCodesModel>();
		
		for (CasCnfgSeverityCodesEntity localEntity : allCasCnfgSeverityCodesEntityList)
		{
			SeverityCodesModel localModel = new SeverityCodesModel();
			localModel = new AdminTranslator().translateMdtCnfgSeverityCodesEntityTocommonsModel(localEntity);
			addTypeList.add(localModel);
		}
		 
		return addTypeList;
	}


	public CasCnfgSeverityCodesEntity addSeverityCodes(SeverityCodesModel severityCodesModel)
	{
		CasCnfgSeverityCodesEntity casCnfgSeverityCodesEntity = new CasCnfgSeverityCodesEntity();
		casCnfgSeverityCodesEntity = new AdminTranslator().translateCommnsSeverityCodesModelToEntity(severityCodesModel);
	 
		return casCnfgSeverityCodesEntity;
	}

}
