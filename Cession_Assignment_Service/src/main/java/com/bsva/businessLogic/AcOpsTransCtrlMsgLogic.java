package com.bsva.businessLogic;

import java.util.ArrayList;
import java.util.List;

import com.bsva.commons.model.AcOpsTransCtrlMsgModel;
import com.bsva.entities.MdtAcOpsTransCtrlMsgEntity;
import com.bsva.translator.AdminTranslator;

public class AcOpsTransCtrlMsgLogic {
	
	
	public AcOpsTransCtrlMsgLogic ()
	{
		
	}

	
	public List<AcOpsTransCtrlMsgModel> retrieveAllACOpsRecords(List<MdtAcOpsTransCtrlMsgEntity> AcOpsTransCtrlMsgEntityList) {
		List<AcOpsTransCtrlMsgModel> acOpsTransCtrlMsgModelList = new ArrayList<AcOpsTransCtrlMsgModel>();
		AcOpsTransCtrlMsgModel acOpsTransCtrlMsgModel;
		
		for (MdtAcOpsTransCtrlMsgEntity mdtAcOpsTransCtrlMsgEntity:AcOpsTransCtrlMsgEntityList) 
		{
			acOpsTransCtrlMsgModel = new AcOpsTransCtrlMsgModel();
			acOpsTransCtrlMsgModel = new AdminTranslator().translateMdtAcOpsTransCtrlMsgEntityToCommons(mdtAcOpsTransCtrlMsgEntity);
			acOpsTransCtrlMsgModelList.add(acOpsTransCtrlMsgModel);
		}

		return acOpsTransCtrlMsgModelList;
	}

	public MdtAcOpsTransCtrlMsgEntity addCtrlMsg(AcOpsTransCtrlMsgModel acOpsTransCtrlMsgModel) 
	{
		MdtAcOpsTransCtrlMsgEntity mdtAcOpsTransCtrlMsgEntity = new AdminTranslator().translateCommonsAcOpsTransCtrlModelToEntity(acOpsTransCtrlMsgModel);
		return mdtAcOpsTransCtrlMsgEntity;
	}


	
	public AcOpsTransCtrlMsgModel retrieveMsgType(MdtAcOpsTransCtrlMsgEntity mdtAcOpsTransCtrlMsgEntity)
	{
		AcOpsTransCtrlMsgModel localModel = new AcOpsTransCtrlMsgModel();
		localModel = new AdminTranslator().translateMdtAcOpsTransCtrlMsgEntityToCommons(mdtAcOpsTransCtrlMsgEntity);
		
		return localModel;
	}


}
