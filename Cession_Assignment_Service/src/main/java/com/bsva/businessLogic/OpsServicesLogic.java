package com.bsva.businessLogic;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import com.bsva.commons.model.ServicesModel;
import com.bsva.entities.MdtOpsServicesEntity;
import com.bsva.translator.AdminTranslator;

public class OpsServicesLogic {

public static Logger log = Logger.getLogger(OpsServicesLogic.class);

	public OpsServicesLogic () {

	}
		public List<ServicesModel> retrieveAllOpsServices(List<MdtOpsServicesEntity> mdtOpsServicesEntityList)
		{
	
			
				List<ServicesModel> opsServicesModelList = new ArrayList<ServicesModel>();
			 
	
			
				for (MdtOpsServicesEntity mdtOpsServicesEntity : mdtOpsServicesEntityList) 
				{
			
					ServicesModel opsServicesModel = new ServicesModel();
					opsServicesModel = new AdminTranslator().translateMdtOpsServicesEntityToCommonsModel(mdtOpsServicesEntity);
					opsServicesModelList.add(opsServicesModel);
				}
			
				return opsServicesModelList;
	}
		
		
		
	
		public MdtOpsServicesEntity addOpsService(ServicesModel servicesModel) 
		{
			MdtOpsServicesEntity mdtOpsServicesEntity = new AdminTranslator().transalateCommonsServicesModelToEntity(servicesModel);
		
		return mdtOpsServicesEntity;
		}
		
		public ServicesModel retrieveOpsServices(MdtOpsServicesEntity mdtOpsServicesEntity)
		{
			ServicesModel localModel = new ServicesModel();
			localModel = new AdminTranslator().translateMdtOpsServicesEntityToCommonsModel(mdtOpsServicesEntity);
			
			return localModel;
		}

}
