package com.bsva.businessLogic;

import java.util.ArrayList;
import java.util.List;



import org.apache.log4j.Logger;

import com.bsva.entities.CasCnfgErrorCodesEntity;
import com.bsva.commons.model.ConfgErrorCodesModel;
import com.bsva.translator.AdminTranslator;
/**
 * 
 * @author DimakatsoN
 *
 */

public class ErrorCodesLogic {
	
	public static Logger log = Logger.getLogger(ErrorCodesLogic.class);
	

	public ErrorCodesLogic() {

	}


	public List<ConfgErrorCodesModel> retrieveAllErrorCodes(List<CasCnfgErrorCodesEntity> mdtErrorCodesList)
	{

		//log.debug("in here");

		List<ConfgErrorCodesModel> errorCodesList = new ArrayList<ConfgErrorCodesModel>();
		ConfgErrorCodesModel errorModel;
		//log.debug("out here");

		for (CasCnfgErrorCodesEntity errorEntity : mdtErrorCodesList) {
			//log.debug(errorEntity);

			 errorModel = new ConfgErrorCodesModel();
			errorModel = new AdminTranslator()
					.translateErrorCodesEntityToCommonsModel(errorEntity);
			errorCodesList.add(errorModel);
		}

		return errorCodesList;
	}



	
	public CasCnfgErrorCodesEntity addErrorCode(ConfgErrorCodesModel errorCodesModel) {

		CasCnfgErrorCodesEntity mdtErrorCodesEntity = new AdminTranslator().translateCommonsErrorModelToEntity(errorCodesModel);

		return mdtErrorCodesEntity;
	}



	public ConfgErrorCodesModel retrieveErrorCode(CasCnfgErrorCodesEntity mdtErrorCodesEntity)

	{
		ConfgErrorCodesModel localModel = new ConfgErrorCodesModel();
		localModel = new AdminTranslator().translateErrorCodesEntityToCommonsModel(mdtErrorCodesEntity);
		
		return localModel;
	}
	
	
	

}


