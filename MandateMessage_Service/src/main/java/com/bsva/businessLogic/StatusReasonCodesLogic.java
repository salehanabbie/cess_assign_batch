package com.bsva.businessLogic;

import java.util.ArrayList;
import java.util.List;

import com.bsva.commons.model.StatusReasonCodesModel;
import com.bsva.entities.MdtCnfgStatusReasonCodesEntity;
import com.bsva.translator.AdminTranslator;

/**
 * 
 * @author NhlanhlaM
 *
 */

public class StatusReasonCodesLogic {
	
	public StatusReasonCodesLogic(){
		
	}

	public StatusReasonCodesModel retreiveStatusReasonCodes(MdtCnfgStatusReasonCodesEntity localEntity) 
	{
		StatusReasonCodesModel statusReasonCodesModel = new StatusReasonCodesModel();
		statusReasonCodesModel = new AdminTranslator().translateMdtCnfgStatusReasonCodesEntityToCommonsModel(localEntity);
		
		return statusReasonCodesModel;
	}

	
	public MdtCnfgStatusReasonCodesEntity addStatusReasonCodes(StatusReasonCodesModel statusReasonCodesModel)
	{
		MdtCnfgStatusReasonCodesEntity mdtCnfgStatusReasonCodesEntity = new MdtCnfgStatusReasonCodesEntity();
		mdtCnfgStatusReasonCodesEntity = new AdminTranslator().translateCommnsStatusReasonCodesModelToEntity(statusReasonCodesModel);
	 
		return mdtCnfgStatusReasonCodesEntity;
	}


	public List<StatusReasonCodesModel> retreiveStatusReasonCodes(List<MdtCnfgStatusReasonCodesEntity> mdtCnfgStatusReasonCodesEntityList)
	{
		 List<StatusReasonCodesModel> statusReasonCodeEntityList = new ArrayList<StatusReasonCodesModel>();
		 
		 for (MdtCnfgStatusReasonCodesEntity mdtCnfgStatusReasonCodesEntity : mdtCnfgStatusReasonCodesEntityList)
		 {
			 StatusReasonCodesModel statusReasonCodesModel = new StatusReasonCodesModel();
			 statusReasonCodesModel= new AdminTranslator().translateMdtCnfgStatusReasonCodesEntityToCommonsModel(mdtCnfgStatusReasonCodesEntity);
			 statusReasonCodeEntityList.add(statusReasonCodesModel);
		 }
		return statusReasonCodeEntityList;
	}

}
