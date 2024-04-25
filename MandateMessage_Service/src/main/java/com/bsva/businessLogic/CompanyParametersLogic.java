package com.bsva.businessLogic;

import com.bsva.commons.model.SysctrlCompParamModel;
import com.bsva.entities.CasSysctrlCompParamEntity;
import com.bsva.translator.AdminTranslator;

public class CompanyParametersLogic 
{
		
	public SysctrlCompParamModel retrieveCompanyParameters(CasSysctrlCompParamEntity compParamEntity)
	{
		SysctrlCompParamModel compParamModel = new SysctrlCompParamModel();
		 
		compParamModel = new AdminTranslator().translateSysctrlCompParamEntitytoCommonsModel(compParamEntity);
		return compParamModel;
	}
	
	
	
	
	
}
