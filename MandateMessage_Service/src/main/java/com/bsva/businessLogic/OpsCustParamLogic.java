package com.bsva.businessLogic;

import java.util.ArrayList;
import java.util.List;

import com.bsva.commons.model.OpsCustParamModel;
import com.bsva.entities.MdtOpsCustParamEntity;
import com.bsva.translator.AdminTranslator;

public class OpsCustParamLogic {

	public List<OpsCustParamModel> retrieveAllOpsCustParam(
			List<MdtOpsCustParamEntity> mdtOpsCustParamEntityList) {

		List<OpsCustParamModel> opsSysctrlCumParaModelList = new ArrayList<OpsCustParamModel>();
		
		for (MdtOpsCustParamEntity localEntity : mdtOpsCustParamEntityList) 
		{
			OpsCustParamModel localModel = new OpsCustParamModel();
			localModel = new  AdminTranslator().translateMdtOpsCustParamEntityToCommonsModel(localEntity);
			opsSysctrlCumParaModelList.add(localModel);
		}
	
		return opsSysctrlCumParaModelList;
		
	}

	public MdtOpsCustParamEntity translateMdtOpsCustParam(
			OpsCustParamModel opsSysctrlCumParaModel) {
		
		MdtOpsCustParamEntity mdtOpsCustParamEntity = new MdtOpsCustParamEntity();
		
		
		mdtOpsCustParamEntity = new  AdminTranslator().translateOpsCustParamToEntity(opsSysctrlCumParaModel);

		return mdtOpsCustParamEntity;
	}

}
