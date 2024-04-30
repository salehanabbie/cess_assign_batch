package com.bsva.businessLogic;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.bsva.commons.model.AdjustmentCategoryModel;
import com.bsva.entities.CasCnfgAdjustmentCatEntity;
import com.bsva.translator.AdminTranslator;

/**
 * 
 * @author NhlanhlaM
 *
 */


public class AdjustmentCategoryLogic {
	
public static Logger log = Logger.getLogger(AdjustmentCategoryLogic.class);
	
	public AdjustmentCategoryLogic(){
		
	}

	public static AdjustmentCategoryModel retreiveAdjustmentCategory(
        CasCnfgAdjustmentCatEntity localEntity) {
		
		AdjustmentCategoryModel adjustmentCategoryModel = new AdjustmentCategoryModel();
		adjustmentCategoryModel = new AdminTranslator().translateMdtCnfgAdjustmentCatEntityToCommonsModel(localEntity);
		log.debug("======================adjustmentCategoryModel================="+adjustmentCategoryModel);
		
		         return adjustmentCategoryModel;
	   }
		
    public List<AdjustmentCategoryModel> retreiveAllAdjustmentCategory(List<CasCnfgAdjustmentCatEntity> allCasCnfgAdjustmentCatEntityList) {
		
		List<AdjustmentCategoryModel> adjustmentCategoryEntityList = new ArrayList<AdjustmentCategoryModel>();
		
		log.debug("check if it goes inside the logic method");
		for (CasCnfgAdjustmentCatEntity casCnfgAdjustmentCatEntity : allCasCnfgAdjustmentCatEntityList)
		{
			AdjustmentCategoryModel adjustmentCategoryModel = new AdjustmentCategoryModel();
			adjustmentCategoryModel = new AdminTranslator().translateMdtCnfgAdjustmentCatEntityToCommonsModel(
                casCnfgAdjustmentCatEntity);
			adjustmentCategoryEntityList.add(adjustmentCategoryModel);
			log.debug("======================adjustmentCategoryModel================="+adjustmentCategoryModel);
		}
		
		 log.debug("check what the list return ***************"+adjustmentCategoryEntityList);
			
		 return adjustmentCategoryEntityList;
	
	}


	public CasCnfgAdjustmentCatEntity addAdjustmentCategory(AdjustmentCategoryModel adjustmentCategoryModel) {
		
		CasCnfgAdjustmentCatEntity casCnfgAdjustmentCatEntity = new CasCnfgAdjustmentCatEntity();
		casCnfgAdjustmentCatEntity = new AdminTranslator().translateCommnsAdjustmentCategoryModelToEntity(adjustmentCategoryModel);
		
		
		log.debug("adjustmentCategoryModel================="+adjustmentCategoryModel);
		
		return casCnfgAdjustmentCatEntity;
	}

	

}
