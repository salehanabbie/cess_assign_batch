package com.bsva.businessLogic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.bsva.commons.model.OpsStatusHdrsModel;
import com.bsva.entities.MdtAcOpsStatusHdrsEntity;
import com.bsva.translator.AdminTranslator;

/***
 * 
 * @author DimakatsoN
 *
 */

public class OpsStatusHdrsLogic implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static Logger log = Logger.getLogger(OpsStatusHdrsLogic.class);
	
	
	public OpsStatusHdrsLogic() {
		
	}
	
	public List<OpsStatusHdrsModel> retrieveAllOpsStatusHdrs(List<MdtAcOpsStatusHdrsEntity> opsStatusHdrsEntityList)
	{
		List<OpsStatusHdrsModel> opsStatusHdrsList = new ArrayList<OpsStatusHdrsModel>();
		
		for (MdtAcOpsStatusHdrsEntity opsStatusHdrsEntity : opsStatusHdrsEntityList) 
		{
			OpsStatusHdrsModel opsStatusHdrsModel = new OpsStatusHdrsModel();
			opsStatusHdrsModel = new AdminTranslator().translateOpsStatusHdrsEntityToCommonsModel(opsStatusHdrsEntity);
			opsStatusHdrsList.add(opsStatusHdrsModel);
		}
		log.info("opsStatusHdrsEntityList: "+opsStatusHdrsEntityList.toString());
		return opsStatusHdrsList;
	}
	
	
	public MdtAcOpsStatusHdrsEntity addOpsFileReg(OpsStatusHdrsModel opsStatusHdrsModel) {
		
		MdtAcOpsStatusHdrsEntity opsStatusHdrsEntity = new AdminTranslator().translateCommonsOpsStatusHdrsModelToEntity(opsStatusHdrsModel);
			
			return  opsStatusHdrsEntity; 
		
	}
	
public OpsStatusHdrsModel retrieveOpsStatusHdrs(MdtAcOpsStatusHdrsEntity opsStatusHdrsEntity) {
		
	OpsStatusHdrsModel opsStatusHdrsModel = new OpsStatusHdrsModel();
	opsStatusHdrsModel = new  AdminTranslator().translateOpsStatusHdrsEntityToCommonsModel(opsStatusHdrsEntity);
		return opsStatusHdrsModel;
		
	}
	
	
	
	
	

}
