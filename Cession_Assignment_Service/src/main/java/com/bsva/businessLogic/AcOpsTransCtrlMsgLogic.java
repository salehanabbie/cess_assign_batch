package com.bsva.businessLogic;

import java.util.ArrayList;
import java.util.List;

import com.bsva.commons.model.AcOpsTransCtrlMsgModel;
import com.bsva.entities.CasOpsTransCtrlMsgEntity;
import com.bsva.translator.AdminTranslator;

public class AcOpsTransCtrlMsgLogic {
	
	
	public AcOpsTransCtrlMsgLogic ()
	{
		
	}

	
	public List<AcOpsTransCtrlMsgModel> retrieveAllACOpsRecords(List<CasOpsTransCtrlMsgEntity> AcOpsTransCtrlMsgEntityList) {
		List<AcOpsTransCtrlMsgModel> acOpsTransCtrlMsgModelList = new ArrayList<AcOpsTransCtrlMsgModel>();
		AcOpsTransCtrlMsgModel acOpsTransCtrlMsgModel;
		
		for (CasOpsTransCtrlMsgEntity casOpsTransCtrlMsgEntity :AcOpsTransCtrlMsgEntityList)
		{
			acOpsTransCtrlMsgModel = new AcOpsTransCtrlMsgModel();
			acOpsTransCtrlMsgModel = new AdminTranslator().translateMdtAcOpsTransCtrlMsgEntityToCommons(
                casOpsTransCtrlMsgEntity);
			acOpsTransCtrlMsgModelList.add(acOpsTransCtrlMsgModel);
		}

		return acOpsTransCtrlMsgModelList;
	}

	public CasOpsTransCtrlMsgEntity addCtrlMsg(AcOpsTransCtrlMsgModel acOpsTransCtrlMsgModel)
	{
		CasOpsTransCtrlMsgEntity casOpsTransCtrlMsgEntity = new AdminTranslator().translateCommonsAcOpsTransCtrlModelToEntity(acOpsTransCtrlMsgModel);
		return casOpsTransCtrlMsgEntity;
	}


	
	public AcOpsTransCtrlMsgModel retrieveMsgType(CasOpsTransCtrlMsgEntity casOpsTransCtrlMsgEntity)
	{
		AcOpsTransCtrlMsgModel localModel = new AcOpsTransCtrlMsgModel();
		localModel = new AdminTranslator().translateMdtAcOpsTransCtrlMsgEntityToCommons(
            casOpsTransCtrlMsgEntity);
		
		return localModel;
	}


}
