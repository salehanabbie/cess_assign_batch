package com.bsva.businessLogic;

import java.util.ArrayList;
import java.util.List;

import com.bsva.commons.model.SeverityCodesModel;
import com.bsva.entities.MdtCnfgSeverityCodesEntity;
import com.bsva.translator.AdminTranslator;

public class SeverityCodes2Logic 
{
	
	public SeverityCodes2Logic()
	{
		
	}


	public SeverityCodesModel retreiveSeverityCodes(MdtCnfgSeverityCodesEntity localEntity)
	{
		
		SeverityCodesModel severityCodesModel = new SeverityCodesModel();
		severityCodesModel = new AdminTranslator().translateMdtCnfgSeverityCodesEntityTocommonsModel(localEntity);
		
		return severityCodesModel;
	}

	public List<SeverityCodesModel> retreiveAllSeverityCode(List<MdtCnfgSeverityCodesEntity> allMdtCnfgSeverityCodesEntityList)
	{

		List<SeverityCodesModel> addTypeList = new ArrayList<SeverityCodesModel>();
		
		for (MdtCnfgSeverityCodesEntity localEntity : allMdtCnfgSeverityCodesEntityList)
		{
			SeverityCodesModel localModel = new SeverityCodesModel();
			localModel = new AdminTranslator().translateMdtCnfgSeverityCodesEntityTocommonsModel(localEntity);
			addTypeList.add(localModel);
		}
		 
		return addTypeList;
	}


	public MdtCnfgSeverityCodesEntity addSeverityCodes(SeverityCodesModel severityCodesModel) 
	{
		MdtCnfgSeverityCodesEntity mdtCnfgSeverityCodesEntity = new MdtCnfgSeverityCodesEntity();
		mdtCnfgSeverityCodesEntity = new AdminTranslator().translateCommnsSeverityCodesModelToEntity(severityCodesModel);
	 
		return mdtCnfgSeverityCodesEntity;
	}

}
