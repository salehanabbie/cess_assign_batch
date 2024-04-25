package com.bsva.businessLogic;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.bsva.commons.model.ServicesModel;
import com.bsva.entities.MdtOpsServicesEntity;
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

	public static List<ServicesModel> retreiveAllServices(List<MdtOpsServicesEntity> allMdtOpsServicesEntityList) 
	{
		
		List<ServicesModel> servicesList = new ArrayList<ServicesModel>();
		ServicesModel servicesmModel;
		for (MdtOpsServicesEntity sysServicesEntity : allMdtOpsServicesEntityList) 
		{
			servicesmModel = new ServicesModel();
			servicesmModel = new AdminTranslator().translateMdtOpsServicesEntityToCommonsModel(sysServicesEntity);
			servicesList.add(servicesmModel);
		}
		return servicesList;	
	}

	public MdtOpsServicesEntity addServices(ServicesModel servicesModel) 
	{
		MdtOpsServicesEntity mdtOpsServicesEntity = new AdminTranslator().transalateCommonsServicesModelToEntity(servicesModel);
		
		return mdtOpsServicesEntity;
	}

	public static ServicesModel retreiveServices(MdtOpsServicesEntity mdtOpsServicesEntity)
	{
		ServicesModel servcModels = new ServicesModel();
		servcModels = new AdminTranslator().translateMdtOpsServicesEntityToCommonsModel(mdtOpsServicesEntity);
		
		return servcModels;
	}
	
	public static List<ServicesModel> retrieveIncomingServices(List<MdtOpsServicesEntity> allMdtOpsServicesEntityList)
	{
		List<ServicesModel> servicesList = new ArrayList<ServicesModel>();
		ServicesModel servicesmModel;
		for (MdtOpsServicesEntity sysServicesEntity : allMdtOpsServicesEntityList)
		{
			servicesmModel = new ServicesModel(); 
			servicesmModel = new AdminTranslator().translateIncomingMdtOpsServicesEntityToCommonsModel(sysServicesEntity);
			servicesList.add(servicesmModel);
		}
		return servicesList;
	}
	
	public static List<ServicesModel> retrieveOutgoingServices(List<MdtOpsServicesEntity> allMdtOpsServicesEntityList)
	{
		List<ServicesModel> servicesList = new ArrayList<ServicesModel>();
		ServicesModel servicesmModel;
		for (MdtOpsServicesEntity sysServicesEntity : allMdtOpsServicesEntityList) 
		{
			servicesmModel = new ServicesModel(); 
			servicesmModel = new AdminTranslator().translateOutgoingMdtOpsServicesEntityToCommonsModel(sysServicesEntity);
			servicesList.add(servicesmModel);
		}
		return servicesList;
	}
	
}