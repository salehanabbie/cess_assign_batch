package com.bsva.businessLogic;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.bsva.commons.model.ServicesModel;
import com.bsva.entities.CasOpsServicesEntity;
import com.bsva.translator.AdminTranslator;

/**
 * 
 * @author NhlanhlaM
 *
 */
public class ServicesLogic
{
	public static Logger log = Logger.getLogger(ServicesLogic.class);
	public ServicesLogic()
	{

	}

	public static List<ServicesModel> retreiveAllServices(List<CasOpsServicesEntity> allCasOpsServicesEntityList)
	{
		
		List<ServicesModel> servicesList = new ArrayList<ServicesModel>();
		ServicesModel servicesmModel;
		for (CasOpsServicesEntity sysServicesEntity : allCasOpsServicesEntityList)
		{
			servicesmModel = new ServicesModel();
			servicesmModel = new AdminTranslator().translateMdtOpsServicesEntityToCommonsModel(sysServicesEntity);
			servicesList.add(servicesmModel);
		}
		return servicesList;	
	}

	public CasOpsServicesEntity addServices(ServicesModel servicesModel)
	{
		CasOpsServicesEntity casOpsServicesEntity = new AdminTranslator().transalateCommonsServicesModelToEntity(servicesModel);
		
		return casOpsServicesEntity;
	}

	public static ServicesModel retreiveServices(CasOpsServicesEntity casOpsServicesEntity)
	{
		ServicesModel servcModels = new ServicesModel();
		servcModels = new AdminTranslator().translateMdtOpsServicesEntityToCommonsModel(
            casOpsServicesEntity);
		
		return servcModels;
	}
	
	public static List<ServicesModel> retrieveIncomingServices(List<CasOpsServicesEntity> allCasOpsServicesEntityList)
	{
		List<ServicesModel> servicesList = new ArrayList<ServicesModel>();
		ServicesModel servicesmModel;
		for (CasOpsServicesEntity sysServicesEntity : allCasOpsServicesEntityList)
		{
			servicesmModel = new ServicesModel(); 
			servicesmModel = new AdminTranslator().translateIncomingMdtOpsServicesEntityToCommonsModel(sysServicesEntity);
			servicesList.add(servicesmModel);
		}
		return servicesList;
	}
	
	public static List<ServicesModel> retrieveOutgoingServices(List<CasOpsServicesEntity> allCasOpsServicesEntityList)
	{
		List<ServicesModel> servicesList = new ArrayList<ServicesModel>();
		ServicesModel servicesmModel;
		for (CasOpsServicesEntity sysServicesEntity : allCasOpsServicesEntityList)
		{
			servicesmModel = new ServicesModel(); 
			servicesmModel = new AdminTranslator().translateOutgoingMdtOpsServicesEntityToCommonsModel(sysServicesEntity);
			servicesList.add(servicesmModel);
		}
		return servicesList;
	}
	
}