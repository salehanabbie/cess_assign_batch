package com.bsva.businessLogic;

import java.util.ArrayList;
import java.util.List;
import com.bsva.commons.model.MandatesCountCommonsModel;
import com.bsva.entities.CasOpsMndtCountEntity;
import com.bsva.translator.AdminTranslator;


public class MandatesCountLogic 
{
	
	public List<MandatesCountCommonsModel> retrieveAllMandatesCountData(List<CasOpsMndtCountEntity> mdtopsCountList)
	{

		
			List<MandatesCountCommonsModel> mandatesCountCommonsModelList = new ArrayList<MandatesCountCommonsModel>();
			MandatesCountCommonsModel mandatesCountCommonsModel;

		
			for (CasOpsMndtCountEntity mdtOpsMndtCountEntity : mdtopsCountList)
			{
				
				mandatesCountCommonsModel = new MandatesCountCommonsModel();
				mandatesCountCommonsModel = new AdminTranslator().translateMdtOpsMndtCountEntityToCommonsModel(mdtOpsMndtCountEntity);
				mandatesCountCommonsModelList.add(mandatesCountCommonsModel);
			}
		
			return mandatesCountCommonsModelList;
}
	
	

	
	public MandatesCountCommonsModel retrieveMandateCount(
        CasOpsMndtCountEntity mdtOpsMndtCountEntity)
	{
		MandatesCountCommonsModel localModel = new MandatesCountCommonsModel();
		localModel = new AdminTranslator().translateMdtOpsMndtCountEntityToCommonsModel(mdtOpsMndtCountEntity);
		
		return localModel;
	}
	

}
