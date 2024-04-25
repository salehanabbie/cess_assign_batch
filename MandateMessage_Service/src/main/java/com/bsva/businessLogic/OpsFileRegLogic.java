package com.bsva.businessLogic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.bsva.commons.model.AcOpsSotEotCntrlModel;
import com.bsva.commons.model.OpsFileRegModel;
import com.bsva.entities.MdtAcOpsSotEotCtrlEntity;
import com.bsva.entities.MdtOpsFileRegEntity;
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
	
	public List<OpsFileRegModel> retrieveAllDelDelivery(List<MdtOpsFileRegEntity> mdtOpsFileRegList)
	{
		List<OpsFileRegModel> opsFileRegList = new ArrayList<OpsFileRegModel>();
		
		for (MdtOpsFileRegEntity opsFileRegEntity : mdtOpsFileRegList) 
		{
			OpsFileRegModel opsFileRegModel = new OpsFileRegModel();
			opsFileRegModel = new AdminTranslator().translateCommonsToOpsFileRegEntity(opsFileRegEntity);
			opsFileRegList.add(opsFileRegModel);
		}
		log.debug("opsFileRegList: "+opsFileRegList.toString());
		return opsFileRegList;
}


	public MdtOpsFileRegEntity addOpsFileReg(OpsFileRegModel opsFileRegModel) {
		
		MdtOpsFileRegEntity mdtOpsFileRegEntity = new AdminTranslator().translateCommonsOpsFileRegModelToEntity(opsFileRegModel);
			
			return  mdtOpsFileRegEntity; 
		
	}

	public OpsFileRegModel retrieveDelDelivery(MdtOpsFileRegEntity MdtOpsFileRegEntity) {
	
	OpsFileRegModel opsFileRegModel = new OpsFileRegModel();
	
	opsFileRegModel = new AdminTranslator().translateCommonsToOpsFileRegEntity(MdtOpsFileRegEntity);


	return opsFileRegModel;
		
		
	}
	
	

	

	public AcOpsSotEotCntrlModel retrieveMdtAcOpsSotEotCtrlEntity(MdtAcOpsSotEotCtrlEntity mdtAcOpsSotEotCtrlEntity)
	{
		AcOpsSotEotCntrlModel localModel = new AcOpsSotEotCntrlModel();
		localModel = new AdminTranslator(). translateAcOpsSotEotCntrlModelToEntity(mdtAcOpsSotEotCtrlEntity);
		
		return localModel;
	}
	
	

	public OpsFileRegModel retrieveOpsFileRegModel(MdtOpsFileRegEntity opsFileRegEntity) {
		
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
