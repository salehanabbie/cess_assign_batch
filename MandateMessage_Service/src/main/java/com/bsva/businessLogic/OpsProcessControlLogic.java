package com.bsva.businessLogic;

import java.util.ArrayList;
import java.util.List;
import com.bsva.commons.model.OpsProcessControlCommonsModel;
import com.bsva.entities.MdtOpsProcessControlsEntity;
import com.bsva.translator.AdminTranslator;

public class OpsProcessControlLogic {

	
	public OpsProcessControlLogic()
	{
		
	}
	
	
	public List<OpsProcessControlCommonsModel> retrieveAllOpsProcessControlCommonsModel(List<MdtOpsProcessControlsEntity> mdtOpsProcessControlsEntityList)
	{

		
			List<OpsProcessControlCommonsModel> opsProcessControlCommonsModelList = new ArrayList<OpsProcessControlCommonsModel>();
			OpsProcessControlCommonsModel opsProcessControlCommonsModel;

		
			for (MdtOpsProcessControlsEntity mdtOpsProcessControlsEntity : mdtOpsProcessControlsEntityList) 
			{
			
				opsProcessControlCommonsModel = new OpsProcessControlCommonsModel();
				opsProcessControlCommonsModel = new AdminTranslator().translateMdtOpsProcessControlsEntityToModel(mdtOpsProcessControlsEntity);
				opsProcessControlCommonsModelList.add(opsProcessControlCommonsModel);
			}
		
			return opsProcessControlCommonsModelList;
}
	
	
	public MdtOpsProcessControlsEntity addMdtOpsProcessControlsEntity(OpsProcessControlCommonsModel opsProcessControlCommonsModel) 
	{
		MdtOpsProcessControlsEntity mdtOpsProcessControlsEntity = new AdminTranslator().translateCommonsMdtOpsProcessControlsModelToEntity(opsProcessControlCommonsModel);
	
	return mdtOpsProcessControlsEntity;
	}
	
	public OpsProcessControlCommonsModel retrieveOpsProcessControlCommonsModel(MdtOpsProcessControlsEntity mdtOpsProcessControlsEntity)
	{
		OpsProcessControlCommonsModel localModel = new OpsProcessControlCommonsModel();
		localModel = new AdminTranslator().translateMdtOpsProcessControlsEntityToModel(mdtOpsProcessControlsEntity);
		
		return localModel;
	}
	
}
