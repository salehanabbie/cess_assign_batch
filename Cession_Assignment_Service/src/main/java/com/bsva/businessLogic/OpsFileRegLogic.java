package com.bsva.businessLogic;

import com.bsva.entities.CasOpsFileRegEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.bsva.commons.model.AcOpsSotEotCntrlModel;
import com.bsva.commons.model.OpsFileRegModel;
import com.bsva.entities.CasOpsSotEotCtrlEntity;
import com.bsva.translator.AdminTranslator;
import com.bsva.translator.ServiceTranslator;
/**
 * 
 * @author DimakatsoN
 *
 */

public class OpsFileRegLogic implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static Logger log = Logger.getLogger(OpsFileRegLogic.class);
	
public OpsFileRegLogic(){
		
	}
	
	public List<OpsFileRegModel> retrieveAllDelDelivery(List<CasOpsFileRegEntity> mdtOpsFileRegList)
	{
		List<OpsFileRegModel> opsFileRegList = new ArrayList<OpsFileRegModel>();
		
		for (CasOpsFileRegEntity opsFileRegEntity : mdtOpsFileRegList)
		{
			OpsFileRegModel opsFileRegModel = new OpsFileRegModel();
			opsFileRegModel = new AdminTranslator().translateCommonsToOpsFileRegEntity(opsFileRegEntity);
			opsFileRegList.add(opsFileRegModel);
		}
		log.debug("opsFileRegList: "+opsFileRegList.toString());
		return opsFileRegList;
}


	public CasOpsFileRegEntity addOpsFileReg(OpsFileRegModel opsFileRegModel) {
		
		CasOpsFileRegEntity casOpsFileRegEntity = new AdminTranslator().translateCommonsOpsFileRegModelToEntity(opsFileRegModel);
			
			return casOpsFileRegEntity;
		
	}

	public OpsFileRegModel retrieveDelDelivery(CasOpsFileRegEntity CasOpsFileRegEntity) {
	
	OpsFileRegModel opsFileRegModel = new OpsFileRegModel();
	opsFileRegModel = new AdminTranslator().translateCommonsToOpsFileRegEntity(CasOpsFileRegEntity);

	return opsFileRegModel;
	}
	
	

	

	public AcOpsSotEotCntrlModel retrieveMdtAcOpsSotEotCtrlEntity(
        CasOpsSotEotCtrlEntity casOpsSotEotCtrlEntity)
	{
		AcOpsSotEotCntrlModel localModel = new AcOpsSotEotCntrlModel();
		localModel = new AdminTranslator(). translateAcOpsSotEotCntrlModelToEntity(
            casOpsSotEotCtrlEntity);
		
		return localModel;
	}
	
	

	public OpsFileRegModel retrieveOpsFileRegModel(CasOpsFileRegEntity opsFileRegEntity) {
		
		OpsFileRegModel opsFileRegModel = new OpsFileRegModel();
		opsFileRegModel = new  ServiceTranslator().translateOpsFileRegEntityToCommonsModel(opsFileRegEntity);
		return opsFileRegModel;
		
	}

//	public MdtOpsFileRegEntity deleteFileRegRecord(
//			OpsFileRegModel opsFileRegModel) {
//		
//		MdtOpsFileRegEntity mdtOpsFileRegEntity = new MdtOpsFileRegEntity();
//		
//		mdtOpsFileRegEntity = new AdminTranslator().translateCommonsOpsFileRegModelToEntity(opsFileRegModel);
//		
//		// TODO Auto-generated method stub
//		return mdtOpsFileRegEntity;
//	}

	
	
}
