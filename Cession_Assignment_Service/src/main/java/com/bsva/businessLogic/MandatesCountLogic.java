package com.bsva.businessLogic;

import java.util.ArrayList;
import java.util.List;
import com.bsva.commons.model.MandatesCountCommonsModel;
import com.bsva.entities.MdtAcOpsMndtCountEntity;
import com.bsva.translator.AdminTranslator;


public class MandatesCountLogic 
{
	
	public List<MandatesCountCommonsModel> retrieveAllMandatesCountData(List<MdtAcOpsMndtCountEntity> mdtopsCountList)
	{

		
			List<MandatesCountCommonsModel> mandatesCountCommonsModelList = new ArrayList<MandatesCountCommonsModel>();
			MandatesCountCommonsModel mandatesCountCommonsModel;

		
			for (MdtAcOpsMndtCountEntity mdtOpsMndtCountEntity : mdtopsCountList) 
			{
				
				mandatesCountCommonsModel = new MandatesCountCommonsModel();
				mandatesCountCommonsModel = new AdminTranslator().translateMdtOpsMndtCountEntityToCommonsModel(mdtOpsMndtCountEntity);
				mandatesCountCommonsModelList.add(mandatesCountCommonsModel);
			}
		
			return mandatesCountCommonsModelList;
}
	
	

	
	public MandatesCountCommonsModel retrieveMandateCount(MdtAcOpsMndtCountEntity  mdtOpsMndtCountEntity)
	{
		MandatesCountCommonsModel localModel = new MandatesCountCommonsModel();
		localModel = new AdminTranslator().translateMdtOpsMndtCountEntityToCommonsModel(mdtOpsMndtCountEntity);
		
		return localModel;
	}
	

}
