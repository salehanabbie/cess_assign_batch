package com.bsva.businessLogic;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import com.bsva.commons.model.OpsSlaTimesCommonsModel;
import com.bsva.entities.MdtOpsSlaTimesEntity;
import com.bsva.translator.AdminTranslator;

public class OpsSlaTimesLogic {
	
	
	
	public OpsSlaTimesLogic() {
		
		/**
		 * @author ElelwaniR
		 *
		 */
		
	}
	
	
	public List<OpsSlaTimesCommonsModel> retrieveAllOpsSlaTimes(List<MdtOpsSlaTimesEntity> mdtOpsSlaTimesEntityList)
	{

		
			List<OpsSlaTimesCommonsModel> opsSlaTimesCommonsModelList = new ArrayList<OpsSlaTimesCommonsModel>();
			OpsSlaTimesCommonsModel opsSlaTimesCommonsModel;

		
			for (MdtOpsSlaTimesEntity mdtOpsSlaTimesEntity : mdtOpsSlaTimesEntityList) 
			{
		
				opsSlaTimesCommonsModel = new OpsSlaTimesCommonsModel();
				opsSlaTimesCommonsModel = new AdminTranslator().translateMdtOpsSlaTimesEntityToCommonsModel(mdtOpsSlaTimesEntity);
				opsSlaTimesCommonsModelList.add(opsSlaTimesCommonsModel);
			}
		
			return opsSlaTimesCommonsModelList;
}
	
	
	public MdtOpsSlaTimesEntity addOpsSlaTimes(OpsSlaTimesCommonsModel opsSlaTimesCommonsModel) 
	{
		MdtOpsSlaTimesEntity mdtOpsSlaTimesEntity = new AdminTranslator().translateOpsSlaTimesCommonsModelToEntity(opsSlaTimesCommonsModel);
	
	return mdtOpsSlaTimesEntity;
	}
	
	public OpsSlaTimesCommonsModel retrieveOpsSlaTimes(MdtOpsSlaTimesEntity mdtOpsSlaTimesEntity)
	{
		OpsSlaTimesCommonsModel localModel = new OpsSlaTimesCommonsModel();
		localModel = new AdminTranslator().translateMdtOpsSlaTimesEntityToCommonsModel(mdtOpsSlaTimesEntity);
		
		return localModel;
	}

		}
	