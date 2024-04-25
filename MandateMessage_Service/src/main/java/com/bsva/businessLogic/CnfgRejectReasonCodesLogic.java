package com.bsva.businessLogic;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.bsva.commons.model.CnfgRejectReasonCodesModel;
import com.bsva.entities.MdtCnfgRejectReasonCodesEntity;
import com.bsva.translator.AdminTranslator;

public class CnfgRejectReasonCodesLogic
{
	
	public static Logger log = Logger.getLogger(CnfgRejectReasonCodesLogic.class);

	public List<CnfgRejectReasonCodesModel> retrieveAllRejectReasonsCodes(List<MdtCnfgRejectReasonCodesEntity> mdtCnfgRejectReasonCodesEntity)
	{
			List<CnfgRejectReasonCodesModel> rejectReasonCodesModelList= new ArrayList<CnfgRejectReasonCodesModel>();
		
			for (MdtCnfgRejectReasonCodesEntity localEntity : mdtCnfgRejectReasonCodesEntity) 
			{
				CnfgRejectReasonCodesModel localModel = new CnfgRejectReasonCodesModel();
				localModel = new  AdminTranslator(). transalateCnfgRejectReasonCodesEntityToCommonsRejectReasonModel(localEntity);
				rejectReasonCodesModelList.add(localModel);
			}
		
			return rejectReasonCodesModelList;
}
	



public MdtCnfgRejectReasonCodesEntity addRejectReasonsCodes(CnfgRejectReasonCodesModel cnfgRejectReasonCodesModel)
{
	MdtCnfgRejectReasonCodesEntity mdtCnfgRejectReasonCodesEntity = new AdminTranslator().transalateCommonsRejectReasonCodesModelToEntity(cnfgRejectReasonCodesModel);
	
	return mdtCnfgRejectReasonCodesEntity; 
}


public CnfgRejectReasonCodesModel retrieveCnfgRejectReasonCodesModel(MdtCnfgRejectReasonCodesEntity mdtCnfgRejectReasonCodesEntity)
{
	CnfgRejectReasonCodesModel localModel = new CnfgRejectReasonCodesModel();
	localModel = new AdminTranslator().transalateCnfgRejectReasonCodesEntityToCommonsRejectReasonModel(mdtCnfgRejectReasonCodesEntity);
	
	return localModel;
}


}
