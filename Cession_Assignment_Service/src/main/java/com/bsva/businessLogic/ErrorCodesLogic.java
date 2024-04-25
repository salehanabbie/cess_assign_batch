package com.bsva.businessLogic;

import java.util.ArrayList;
import java.util.List;



import org.apache.log4j.Logger;

import com.bsva.entities.MdtCnfgErrorCodesEntity;
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


	public List<ConfgErrorCodesModel> retrieveAllErrorCodes(List<MdtCnfgErrorCodesEntity> mdtErrorCodesList)
	{

		//log.debug("in here");

		List<ConfgErrorCodesModel> errorCodesList = new ArrayList<ConfgErrorCodesModel>();
		ConfgErrorCodesModel errorModel;
		//log.debug("out here");

		for (MdtCnfgErrorCodesEntity errorEntity : mdtErrorCodesList) {
			//log.debug(errorEntity);

			 errorModel = new ConfgErrorCodesModel();
			errorModel = new AdminTranslator()
					.translateErrorCodesEntityToCommonsModel(errorEntity);
			errorCodesList.add(errorModel);
		}

		return errorCodesList;
	}



	
	public MdtCnfgErrorCodesEntity addErrorCode(ConfgErrorCodesModel errorCodesModel) {

		MdtCnfgErrorCodesEntity mdtErrorCodesEntity = new AdminTranslator().translateCommonsErrorModelToEntity(errorCodesModel);

		return mdtErrorCodesEntity;
	}



	public ConfgErrorCodesModel retrieveErrorCode(MdtCnfgErrorCodesEntity mdtErrorCodesEntity)

	{
		ConfgErrorCodesModel localModel = new ConfgErrorCodesModel();
		localModel = new AdminTranslator().translateErrorCodesEntityToCommonsModel(mdtErrorCodesEntity);
		
		return localModel;
	}
	
	
	

}


