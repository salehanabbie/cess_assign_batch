package com.bsva.businessLogic;

import java.util.ArrayList;
import java.util.List;

import com.bsva.commons.model.FileSizeLimitModel;
import com.bsva.entities.MdtAcOpsFileSizeLimitEntity;
import com.bsva.translator.AdminTranslator;
/***
 * 
 * @author DimakatsoN
 *
 */

public class OpsFileSizeLimitLogic {
	
	public List<FileSizeLimitModel> retrieveAllOpsFileSizeLimit(List<MdtAcOpsFileSizeLimitEntity> mdtAcOpsFileSizeLimitEntityList)
	{

		
			List<FileSizeLimitModel> fileSizeLimitModelList = new ArrayList<FileSizeLimitModel>();
			FileSizeLimitModel fileSizeLimitModel;

		
			for (MdtAcOpsFileSizeLimitEntity mdtAcOpsFileSizeLimitEntity : mdtAcOpsFileSizeLimitEntityList) 
			{
		
				fileSizeLimitModel = new FileSizeLimitModel();
				fileSizeLimitModel = new AdminTranslator().translateMdtAcOpsFileSizeLimitEntityToCommonsModel(mdtAcOpsFileSizeLimitEntity);
				fileSizeLimitModelList.add(fileSizeLimitModel);
			}
		
			return fileSizeLimitModelList;
}
	
	
	public MdtAcOpsFileSizeLimitEntity addOpsFileSizeLimit(FileSizeLimitModel fileSizeLimitModel) 
	{
		MdtAcOpsFileSizeLimitEntity mdtAcOpsFileSizeLimitEntity = new AdminTranslator().translateOpsFileSizeLimitCommonsModelToEntity(fileSizeLimitModel);
	
	return mdtAcOpsFileSizeLimitEntity;
	}
	
	public FileSizeLimitModel retrieveOpsFileSizeLimit(MdtAcOpsFileSizeLimitEntity mdtAcOpsFileSizeLimitEntity)
	{
		FileSizeLimitModel localModel = new FileSizeLimitModel();
		localModel = new AdminTranslator().translateMdtAcOpsFileSizeLimitEntityToCommonsModel(mdtAcOpsFileSizeLimitEntity);
		
		return localModel;
	}

}
