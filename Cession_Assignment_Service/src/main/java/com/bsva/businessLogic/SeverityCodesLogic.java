package com.bsva.businessLogic;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.bsva.commons.model.ConfgSeverityCodesModel;
import com.bsva.entities.MdtCnfgSeverityCodesEntity;
import com.bsva.translator.AdminTranslator;

public class SeverityCodesLogic  {
	public static Logger log = Logger.getLogger(SeverityCodesLogic.class);
	

	public SeverityCodesLogic() {

	}

	public List<ConfgSeverityCodesModel> retrieveAllSeverityCodes(List<MdtCnfgSeverityCodesEntity> mdtSeverityCodesList) {
		List<ConfgSeverityCodesModel> severityCodesList = new ArrayList<ConfgSeverityCodesModel>();
		ConfgSeverityCodesModel severityModel;

		for (MdtCnfgSeverityCodesEntity severityEntity : mdtSeverityCodesList) 
		{
			log.debug(severityEntity);

			 severityModel = new ConfgSeverityCodesModel();
			severityModel = new AdminTranslator().translateCnfgSeverityCodesEntityToWebModel(severityEntity);
			severityCodesList.add(severityModel);
		}
		log.debug("Logic CnfgSeverityList: "+severityCodesList.toString());
		return severityCodesList;
	}


	public MdtCnfgSeverityCodesEntity addSeverityCode(ConfgSeverityCodesModel severityCodesModel) {
		MdtCnfgSeverityCodesEntity mdtSeverityCodesEntity = new AdminTranslator().translateCommonsConfgSeverityCodesModelToEntity(severityCodesModel);

		return mdtSeverityCodesEntity;
	}


	public ConfgSeverityCodesModel retrievesSeverityCodeModel(MdtCnfgSeverityCodesEntity mdtSeverityCodesEntity)
	{
		ConfgSeverityCodesModel localModel = new ConfgSeverityCodesModel();
		localModel = new AdminTranslator().translateCnfgSeverityCodesEntityToWebModel(mdtSeverityCodesEntity);
		
		return localModel;
	}

}
