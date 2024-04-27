package com.bsva.businessLogic;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.bsva.commons.model.ConfgSeverityCodesModel;
import com.bsva.entities.CasCnfgSeverityCodesEntity;
import com.bsva.translator.AdminTranslator;

public class SeverityCodesLogic  {
	public static Logger log = Logger.getLogger(SeverityCodesLogic.class);
	

	public SeverityCodesLogic() {

	}

	public List<ConfgSeverityCodesModel> retrieveAllSeverityCodes(List<CasCnfgSeverityCodesEntity> mdtSeverityCodesList) {
		List<ConfgSeverityCodesModel> severityCodesList = new ArrayList<ConfgSeverityCodesModel>();
		ConfgSeverityCodesModel severityModel;

		for (CasCnfgSeverityCodesEntity severityEntity : mdtSeverityCodesList)
		{
			log.debug(severityEntity);

			 severityModel = new ConfgSeverityCodesModel();
			severityModel = new AdminTranslator().translateCnfgSeverityCodesEntityToWebModel(severityEntity);
			severityCodesList.add(severityModel);
		}
		log.debug("Logic CnfgSeverityList: "+severityCodesList.toString());
		return severityCodesList;
	}


	public CasCnfgSeverityCodesEntity addSeverityCode(ConfgSeverityCodesModel severityCodesModel) {
		CasCnfgSeverityCodesEntity mdtSeverityCodesEntity = new AdminTranslator().translateCommonsConfgSeverityCodesModelToEntity(severityCodesModel);

		return mdtSeverityCodesEntity;
	}


	public ConfgSeverityCodesModel retrievesSeverityCodeModel(
        CasCnfgSeverityCodesEntity mdtSeverityCodesEntity)
	{
		ConfgSeverityCodesModel localModel = new ConfgSeverityCodesModel();
		localModel = new AdminTranslator().translateCnfgSeverityCodesEntityToWebModel(mdtSeverityCodesEntity);
		
		return localModel;
	}

}
