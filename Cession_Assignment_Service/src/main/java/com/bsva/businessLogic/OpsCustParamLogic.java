package com.bsva.businessLogic;

import java.util.ArrayList;
import java.util.List;

import com.bsva.commons.model.OpsCustParamModel;
import com.bsva.entities.CasOpsCustParamEntity;
import com.bsva.translator.AdminTranslator;

public class OpsCustParamLogic {

	public List<OpsCustParamModel> retrieveAllOpsCustParam(
			List<CasOpsCustParamEntity> casOpsCustParamEntityList) {

		List<OpsCustParamModel> opsSysctrlCumParaModelList = new ArrayList<OpsCustParamModel>();
		
		for (CasOpsCustParamEntity localEntity : casOpsCustParamEntityList)
		{
			OpsCustParamModel localModel = new OpsCustParamModel();
			localModel = new  AdminTranslator().translateMdtOpsCustParamEntityToCommonsModel(localEntity);
			opsSysctrlCumParaModelList.add(localModel);
		}
	
		return opsSysctrlCumParaModelList;
		
	}

	public CasOpsCustParamEntity translateMdtOpsCustParam(
			OpsCustParamModel opsSysctrlCumParaModel) {
		
		CasOpsCustParamEntity casOpsCustParamEntity = new CasOpsCustParamEntity();
		
		
		casOpsCustParamEntity = new  AdminTranslator().translateOpsCustParamToEntity(opsSysctrlCumParaModel);

		return casOpsCustParamEntity;
	}

}
