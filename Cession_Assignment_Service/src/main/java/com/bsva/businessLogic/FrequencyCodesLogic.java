package com.bsva.businessLogic;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.bsva.commons.model.FrequencyCodesModel;
import com.bsva.entities.CasCnfgFrequencyCodesEntity;
import com.bsva.translator.AdminTranslator;

/**
 * @author salehas
 *
 */
public class FrequencyCodesLogic 
{
	public static Logger log = Logger.getLogger(FrequencyCodesLogic.class);

	
public FrequencyCodesLogic(){
		
	}
	
	public List<FrequencyCodesModel> retreiveAllFrequencyCodes(List<CasCnfgFrequencyCodesEntity> mdtFrequencyCodesList)
	{
		
		
		List<FrequencyCodesModel> frequencyCodesList = new ArrayList<FrequencyCodesModel>();
		
		
		
		for (CasCnfgFrequencyCodesEntity frequencyEntity : mdtFrequencyCodesList)
		{
			
			FrequencyCodesModel	frequencyModel = new FrequencyCodesModel();
			frequencyModel = new AdminTranslator(). translateFrequencyCodesEntityToCommonsModel(frequencyEntity);
			frequencyCodesList.add(frequencyModel);
		}
	
		return frequencyCodesList;
}



public CasCnfgFrequencyCodesEntity addFrequencyCodes(FrequencyCodesModel frequencyCodesModel)
{
	CasCnfgFrequencyCodesEntity mdtFrequencyCodesEntity = new AdminTranslator().translateCommnsFrequencyCodeModelToEntity(frequencyCodesModel);
	
	return  mdtFrequencyCodesEntity; 
}

public FrequencyCodesModel retrieveFrequencyCode(CasCnfgFrequencyCodesEntity mdtFrequencyCodesEntity)
{
	FrequencyCodesModel localModel = new FrequencyCodesModel();
	localModel = new AdminTranslator().translateFrequencyCodesEntityToCommonsModel(mdtFrequencyCodesEntity);
	
	return localModel;
}

		
	
}
