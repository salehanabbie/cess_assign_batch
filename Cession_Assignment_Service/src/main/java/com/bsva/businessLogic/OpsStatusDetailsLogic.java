package com.bsva.businessLogic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.bsva.commons.model.OpsStatusDetailsModel;
import com.bsva.entities.CasOpsStatusDetailsEntity;
import com.bsva.translator.AdminTranslator;

/***
 * 
 * @author DimakatsoN
 *
 */

public class OpsStatusDetailsLogic implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static Logger log = Logger.getLogger(OpsStatusDetailsLogic.class);
	
	
	public OpsStatusDetailsLogic() {
		
	}
	
	public List<OpsStatusDetailsModel> retrieveAllOpsStatusDetails(List<CasOpsStatusDetailsEntity> opsStatusDetailsEntityList)
	{
		List<OpsStatusDetailsModel> opsStatusDetailsModelList = new ArrayList<OpsStatusDetailsModel>();
		
		for (CasOpsStatusDetailsEntity opsStatusDetailsEntity : opsStatusDetailsEntityList)
		{
			OpsStatusDetailsModel opsStatusDetailsModel = new OpsStatusDetailsModel();
			opsStatusDetailsModel = new AdminTranslator().translateOpsStatusDetailsEntityToCommonsModel(opsStatusDetailsEntity);

			opsStatusDetailsModelList.add(opsStatusDetailsModel);
		}
		log.info("opsStatusHdrsEntityList: "+opsStatusDetailsEntityList.toString());
		return opsStatusDetailsModelList;
	}
	
	
	public CasOpsStatusDetailsEntity addOpsStatusDetails(OpsStatusDetailsModel opsStatusDetailsModel) {
		
		CasOpsStatusDetailsEntity opsStatusDetailsEntity = new AdminTranslator().translateCommonsOpsStatusDetailsModelToEntity(opsStatusDetailsModel);
			
			return  opsStatusDetailsEntity; 
		
	}
	
public OpsStatusDetailsModel retrieveOpsStatusDetails(
    CasOpsStatusDetailsEntity opsStatusDetailsEntity) {
		
	OpsStatusDetailsModel opsStatusDetailsModel= new OpsStatusDetailsModel();
	opsStatusDetailsModel = new  AdminTranslator().translateOpsStatusDetailsEntityToCommonsModel(opsStatusDetailsEntity);
		return opsStatusDetailsModel;
		
	}

}
