package com.bsva.businessLogic;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.bsva.commons.model.AdjustmentCategoryModel;
import com.bsva.entities.MdtCnfgAdjustmentCatEntity;
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

	public static AdjustmentCategoryModel retreiveAdjustmentCategory(MdtCnfgAdjustmentCatEntity localEntity) {
		
		AdjustmentCategoryModel adjustmentCategoryModel = new AdjustmentCategoryModel();
		adjustmentCategoryModel = new AdminTranslator().translateMdtCnfgAdjustmentCatEntityToCommonsModel(localEntity);
		log.debug("======================adjustmentCategoryModel================="+adjustmentCategoryModel);
		
		         return adjustmentCategoryModel;
	   }
		
    public List<AdjustmentCategoryModel> retreiveAllAdjustmentCategory(List<MdtCnfgAdjustmentCatEntity> allMdtCnfgAdjustmentCatEntityList) {
		
		List<AdjustmentCategoryModel> adjustmentCategoryEntityList = new ArrayList<AdjustmentCategoryModel>();
		
		log.debug("check if it goes inside the logic method");
		for (MdtCnfgAdjustmentCatEntity mdtCnfgAdjustmentCatEntity : allMdtCnfgAdjustmentCatEntityList)
		{
			AdjustmentCategoryModel adjustmentCategoryModel = new AdjustmentCategoryModel();
			adjustmentCategoryModel = new AdminTranslator().translateMdtCnfgAdjustmentCatEntityToCommonsModel(mdtCnfgAdjustmentCatEntity);
			adjustmentCategoryEntityList.add(adjustmentCategoryModel);
			log.debug("======================adjustmentCategoryModel================="+adjustmentCategoryModel);
		}
		
		 log.debug("check what the list return ***************"+adjustmentCategoryEntityList);
			
		 return adjustmentCategoryEntityList;
	
	}


	public MdtCnfgAdjustmentCatEntity addAdjustmentCategory(AdjustmentCategoryModel adjustmentCategoryModel) {
		
		MdtCnfgAdjustmentCatEntity mdtCnfgAdjustmentCatEntity = new MdtCnfgAdjustmentCatEntity();
		mdtCnfgAdjustmentCatEntity = new AdminTranslator().translateCommnsAdjustmentCategoryModelToEntity(adjustmentCategoryModel);
		
		
		log.debug("adjustmentCategoryModel================="+adjustmentCategoryModel);
		
		return  mdtCnfgAdjustmentCatEntity; 
	}

	

}
