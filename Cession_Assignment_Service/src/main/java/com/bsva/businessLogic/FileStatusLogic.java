package com.bsva.businessLogic;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.bsva.commons.model.FileStatusCommonsModel;
import com.bsva.entities.CasSysctrlFileStatusEntity;
import com.bsva.translator.AdminTranslator;

public class FileStatusLogic
{
	public FileStatusLogic()
	{
		
	}

public static Logger log = Logger.getLogger(FileStatusLogic.class);
	

	public List<FileStatusCommonsModel> retrieveAllMdtSysctrlFileStatusEntityList(List<CasSysctrlFileStatusEntity> mdtSysctrlFileStatusEntityList)
	{

		
      List<FileStatusCommonsModel> fileStatusCommonsModelList = new ArrayList<FileStatusCommonsModel>();
      FileStatusCommonsModel fileStatusCommonsModel;

		
			for (CasSysctrlFileStatusEntity casSysctrlFileStatusEntity : mdtSysctrlFileStatusEntityList) 
			{
			
				fileStatusCommonsModel = new FileStatusCommonsModel();
				fileStatusCommonsModel = new AdminTranslator().translateMdtSysctrlFileStatusEntityToCommonsModel(casSysctrlFileStatusEntity);
				fileStatusCommonsModelList.add(fileStatusCommonsModel);
			}
		
			return fileStatusCommonsModelList;
}
	
	
	public CasSysctrlFileStatusEntity addMdtSysctrlFileStatusEntity(FileStatusCommonsModel fileStatusCommonsModel) 
	{
		CasSysctrlFileStatusEntity casSysctrlFileStatusEntity = new AdminTranslator().translateFileStatusCommonsModelToEntity(fileStatusCommonsModel);
	
	return casSysctrlFileStatusEntity;
	}
	
	public FileStatusCommonsModel retrieveMdtSysctrlFileStatusEntity(CasSysctrlFileStatusEntity casSysctrlFileStatusEntity)
	{
		FileStatusCommonsModel localModel = new FileStatusCommonsModel();
		localModel = new AdminTranslator(). translateMdtSysctrlFileStatusEntityToCommonsModel(casSysctrlFileStatusEntity);
		
		return localModel;
	}
}
