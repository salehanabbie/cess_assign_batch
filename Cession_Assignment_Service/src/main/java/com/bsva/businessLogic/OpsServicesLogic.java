package com.bsva.businessLogic;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import com.bsva.commons.model.ServicesModel;
import com.bsva.entities.CasOpsServicesEntity;
import com.bsva.translator.AdminTranslator;

public class OpsServicesLogic {

public static Logger log = Logger.getLogger(OpsServicesLogic.class);

	public OpsServicesLogic () {

	}
		public List<ServicesModel> retrieveAllOpsServices(List<CasOpsServicesEntity> casOpsServicesEntityList)
		{
	
			
				List<ServicesModel> opsServicesModelList = new ArrayList<ServicesModel>();
			 
	
			
				for (CasOpsServicesEntity casOpsServicesEntity : casOpsServicesEntityList)
				{
			
					ServicesModel opsServicesModel = new ServicesModel();
					opsServicesModel = new AdminTranslator().translateMdtOpsServicesEntityToCommonsModel(
                        casOpsServicesEntity);
					opsServicesModelList.add(opsServicesModel);
				}
			
				return opsServicesModelList;
	}
		
		
		
	
		public CasOpsServicesEntity addOpsService(ServicesModel servicesModel)
		{
			CasOpsServicesEntity casOpsServicesEntity = new AdminTranslator().transalateCommonsServicesModelToEntity(servicesModel);
		
		return casOpsServicesEntity;
		}
		
		public ServicesModel retrieveOpsServices(CasOpsServicesEntity casOpsServicesEntity)
		{
			ServicesModel localModel = new ServicesModel();
			localModel = new AdminTranslator().translateMdtOpsServicesEntityToCommonsModel(
                casOpsServicesEntity);
			
			return localModel;
		}

}
