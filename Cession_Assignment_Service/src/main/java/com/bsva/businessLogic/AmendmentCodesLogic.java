package com.bsva.businessLogic;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.bsva.commons.model.AmendmentCodesModel;
import com.bsva.entities.CasCnfgAmendmentCodesEntity;
import com.bsva.translator.AdminTranslator;

public class AmendmentCodesLogic {


public static Logger log = Logger.getLogger(AmendmentCodesLogic.class);
	
	public AmendmentCodesLogic(){
		
	}

	public CasCnfgAmendmentCodesEntity addAmendmentCodes(AmendmentCodesModel amendmentCodesModel)
	{
		
		CasCnfgAmendmentCodesEntity casCnfgAmendmentCodesEntity = new CasCnfgAmendmentCodesEntity();
		casCnfgAmendmentCodesEntity = new AdminTranslator().translateCommonsAmendmentCodesModelToEntity(amendmentCodesModel);
		
		return casCnfgAmendmentCodesEntity;
		
		
	}


	public AmendmentCodesModel retreiveAmendmentCodes(CasCnfgAmendmentCodesEntity localEntity)
	{
		
		AmendmentCodesModel amendmentCodesModel = new AmendmentCodesModel();
		amendmentCodesModel = new AdminTranslator().translateMdtCnfgAmendmentCodesEntityToCommonsModel(localEntity);
		
		         return amendmentCodesModel;
	}

	
	
	public List<AmendmentCodesModel> retreiveAllAmendmentCodes(List<CasCnfgAmendmentCodesEntity> allCasCnfgAmendmentCodesEntityList)
	{
		
       List<AmendmentCodesModel> amendmentCodesEntityList = new ArrayList<AmendmentCodesModel>();
       
       log.debug("check if it goes inside the logic method");
		for (CasCnfgAmendmentCodesEntity casCnfgAmendmentCodesEntity : allCasCnfgAmendmentCodesEntityList)
		{
			AmendmentCodesModel amendmentCodesModel = new AmendmentCodesModel();
			amendmentCodesModel = new AdminTranslator().translateMdtCnfgAmendmentCodesEntityToCommonsModel(
                casCnfgAmendmentCodesEntity);
			amendmentCodesEntityList.add(amendmentCodesModel);
			log.debug("======================amendmentCodesModel================="+amendmentCodesModel);
		}
		 log.debug("check what the list return ***************"+amendmentCodesEntityList);
		 return amendmentCodesEntityList;
		
	
         }

	}




	
	

