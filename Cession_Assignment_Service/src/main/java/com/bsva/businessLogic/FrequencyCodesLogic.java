package com.bsva.businessLogic;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.bsva.commons.model.FrequencyCodesModel;
import com.bsva.entities.MdtCnfgFrequencyCodesEntity;
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
	
	public List<FrequencyCodesModel> retreiveAllFrequencyCodes(List<MdtCnfgFrequencyCodesEntity> mdtFrequencyCodesList)
	{
		
		
		List<FrequencyCodesModel> frequencyCodesList = new ArrayList<FrequencyCodesModel>();
		
		
		
		for (MdtCnfgFrequencyCodesEntity frequencyEntity : mdtFrequencyCodesList) 
		{
			
			FrequencyCodesModel	frequencyModel = new FrequencyCodesModel();
			frequencyModel = new AdminTranslator(). translateFrequencyCodesEntityToCommonsModel(frequencyEntity);
			frequencyCodesList.add(frequencyModel);
		}
	
		return frequencyCodesList;
}



public MdtCnfgFrequencyCodesEntity addFrequencyCodes(FrequencyCodesModel frequencyCodesModel)
{
	MdtCnfgFrequencyCodesEntity mdtFrequencyCodesEntity = new AdminTranslator().translateCommnsFrequencyCodeModelToEntity(frequencyCodesModel);
	
	return  mdtFrequencyCodesEntity; 
}

public FrequencyCodesModel retrieveFrequencyCode(MdtCnfgFrequencyCodesEntity mdtFrequencyCodesEntity)
{
	FrequencyCodesModel localModel = new FrequencyCodesModel();
	localModel = new AdminTranslator().translateFrequencyCodesEntityToCommonsModel(mdtFrequencyCodesEntity);
	
	return localModel;
}

		
	
}
