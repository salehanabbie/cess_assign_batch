package com.bsva.businessLogic;

import java.util.ArrayList;
import java.util.List;

import com.bsva.commons.model.FileSizeLimitModel;
import com.bsva.entities.CasOpsFileSizeLimitEntity;
import com.bsva.translator.AdminTranslator;
/***
 * 
 * @author DimakatsoN
 *
 */

public class OpsFileSizeLimitLogic {
	
	public List<FileSizeLimitModel> retrieveAllOpsFileSizeLimit(List<CasOpsFileSizeLimitEntity> casOpsFileSizeLimitEntityList)
	{

		
			List<FileSizeLimitModel> fileSizeLimitModelList = new ArrayList<FileSizeLimitModel>();
			FileSizeLimitModel fileSizeLimitModel;

		
			for (CasOpsFileSizeLimitEntity casOpsFileSizeLimitEntity : casOpsFileSizeLimitEntityList)
			{
		
				fileSizeLimitModel = new FileSizeLimitModel();
				fileSizeLimitModel = new AdminTranslator().translateMdtAcOpsFileSizeLimitEntityToCommonsModel(
                    casOpsFileSizeLimitEntity);
				fileSizeLimitModelList.add(fileSizeLimitModel);
			}
		
			return fileSizeLimitModelList;
}
	
	
	public CasOpsFileSizeLimitEntity addOpsFileSizeLimit(FileSizeLimitModel fileSizeLimitModel)
	{
		CasOpsFileSizeLimitEntity casOpsFileSizeLimitEntity = new AdminTranslator().translateOpsFileSizeLimitCommonsModelToEntity(fileSizeLimitModel);
	
	return casOpsFileSizeLimitEntity;
	}
	
	public FileSizeLimitModel retrieveOpsFileSizeLimit(
        CasOpsFileSizeLimitEntity casOpsFileSizeLimitEntity)
	{
		FileSizeLimitModel localModel = new FileSizeLimitModel();
		localModel = new AdminTranslator().translateMdtAcOpsFileSizeLimitEntityToCommonsModel(
            casOpsFileSizeLimitEntity);
		
		return localModel;
	}

}
