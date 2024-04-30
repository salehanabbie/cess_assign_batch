package com.bsva.businessLogic;

import java.util.ArrayList;
import java.util.List;
import com.bsva.commons.model.OpsSlaTimesCommonsModel;
import com.bsva.entities.CasOpsSlaTimesEntity;
import com.bsva.translator.AdminTranslator;

public class OpsSlaTimesLogic {
	
	
	
	public OpsSlaTimesLogic() {
		
		/**
		 * @author ElelwaniR
		 *
		 */
		
	}
	
	
	public List<OpsSlaTimesCommonsModel> retrieveAllOpsSlaTimes(List<CasOpsSlaTimesEntity> casOpsSlaTimesEntityList)
	{

		
			List<OpsSlaTimesCommonsModel> opsSlaTimesCommonsModelList = new ArrayList<OpsSlaTimesCommonsModel>();
			OpsSlaTimesCommonsModel opsSlaTimesCommonsModel;

		
			for (CasOpsSlaTimesEntity casOpsSlaTimesEntity : casOpsSlaTimesEntityList)
			{
		
				opsSlaTimesCommonsModel = new OpsSlaTimesCommonsModel();
				opsSlaTimesCommonsModel = new AdminTranslator().translateMdtOpsSlaTimesEntityToCommonsModel(
                    casOpsSlaTimesEntity);
				opsSlaTimesCommonsModelList.add(opsSlaTimesCommonsModel);
			}
		
			return opsSlaTimesCommonsModelList;
}
	
	
	public CasOpsSlaTimesEntity addOpsSlaTimes(OpsSlaTimesCommonsModel opsSlaTimesCommonsModel)
	{
		CasOpsSlaTimesEntity casOpsSlaTimesEntity = new AdminTranslator().translateOpsSlaTimesCommonsModelToEntity(opsSlaTimesCommonsModel);
	
	return casOpsSlaTimesEntity;
	}
	
	public OpsSlaTimesCommonsModel retrieveOpsSlaTimes(CasOpsSlaTimesEntity casOpsSlaTimesEntity)
	{
		OpsSlaTimesCommonsModel localModel = new OpsSlaTimesCommonsModel();
		localModel = new AdminTranslator().translateMdtOpsSlaTimesEntityToCommonsModel(
            casOpsSlaTimesEntity);
		
		return localModel;
	}

		}
	