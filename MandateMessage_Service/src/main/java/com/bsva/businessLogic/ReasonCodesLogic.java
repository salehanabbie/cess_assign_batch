package com.bsva.businessLogic;

import java.util.ArrayList;
import java.util.List;




import com.bsva.commons.model.ReasonCodesModel;
import com.bsva.entities.MdtCnfgCancellationCodesEntity;
import com.bsva.entities.MdtCnfgReasonCodesEntity;
import com.bsva.entities.MdtCnfgRejectReasonCodesEntity;
import com.bsva.translator.AdminTranslator;

/**
 * 
 * @author DimakatsoN
 *
 */

public class ReasonCodesLogic {

	public ReasonCodesLogic(){

	}

	public List<ReasonCodesModel> retreiveAllReasonCodes(List<MdtCnfgReasonCodesEntity> mdtReasonCodesList)
	{
		List<ReasonCodesModel> reasonCodesList = new ArrayList<ReasonCodesModel>();

		for (MdtCnfgReasonCodesEntity reasonEntity : mdtReasonCodesList) 
		{
			ReasonCodesModel reasonModel = new ReasonCodesModel();
			reasonModel = new AdminTranslator().transalateReasCodesToCommonsReasonModel(reasonEntity);
			reasonCodesList.add(reasonModel);
		}

		return reasonCodesList;
	}



	public MdtCnfgReasonCodesEntity addReasonCodes(ReasonCodesModel reasonCodesModel)
	{
		MdtCnfgReasonCodesEntity mdtReasonCodes = new AdminTranslator().transalateCommonsReasonCodesModelToEntity(reasonCodesModel);

		return mdtReasonCodes; 
	}



	public ReasonCodesModel retrieveReasonCode(MdtCnfgReasonCodesEntity mdtReasonCodesEntity)
	{
		ReasonCodesModel localModel = new ReasonCodesModel();
		localModel = new AdminTranslator().translateReasonCodesEntityToCommonsModel(mdtReasonCodesEntity);

		return localModel;
	}


	public ReasonCodesModel retrieveReportReasonCode_mdte002(MdtCnfgRejectReasonCodesEntity mdtCnfgRejectReasonCodesEntity)
	{
		ReasonCodesModel localModel = new ReasonCodesModel();
		localModel = new AdminTranslator().translateReportReasonCodesMdte002ToCommonsModel(mdtCnfgRejectReasonCodesEntity);

		return localModel;
	}

	public ReasonCodesModel retrieveReportReasonCode_Canc(MdtCnfgCancellationCodesEntity mdtCnfgCancellationCodesEntity)
	{
		ReasonCodesModel localModel = new ReasonCodesModel();
		localModel = new AdminTranslator().translateReportReasonCodesCancToCommonsModel(mdtCnfgCancellationCodesEntity);

		return localModel;
	}

}

