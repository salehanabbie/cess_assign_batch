package com.bsva.businessLogic;

import java.util.ArrayList;
import java.util.List;




import com.bsva.commons.model.ReasonCodesModel;
import com.bsva.entities.CasCnfgReasonCodesEntity;
import com.bsva.translator.AdminTranslator;

/**
 * 
 * @author DimakatsoN
 *
 */

public class ReasonCodesLogic {

	public ReasonCodesLogic(){

	}

	public List<ReasonCodesModel> retreiveAllReasonCodes(List<CasCnfgReasonCodesEntity> mdtReasonCodesList)
	{
		List<ReasonCodesModel> reasonCodesList = new ArrayList<ReasonCodesModel>();

		for (CasCnfgReasonCodesEntity reasonEntity : mdtReasonCodesList)
		{
			ReasonCodesModel reasonModel = new ReasonCodesModel();
			reasonModel = new AdminTranslator().transalateReasCodesToCommonsReasonModel(reasonEntity);
			reasonCodesList.add(reasonModel);
		}

		return reasonCodesList;
	}

	public CasCnfgReasonCodesEntity addReasonCodes(ReasonCodesModel reasonCodesModel)
	{
		CasCnfgReasonCodesEntity mdtReasonCodes = new AdminTranslator().transalateCommonsReasonCodesModelToEntity(reasonCodesModel);

		return mdtReasonCodes; 
	}

	public ReasonCodesModel retrieveReasonCode(CasCnfgReasonCodesEntity mdtReasonCodesEntity)
	{
		ReasonCodesModel localModel = new ReasonCodesModel();
		localModel = new AdminTranslator().translateReasonCodesEntityToCommonsModel(mdtReasonCodesEntity);

		return localModel;
	}

}

