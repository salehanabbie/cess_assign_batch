package com.bsva.businessLogic;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.bsva.commons.model.AmendmentCodesModel;
import com.bsva.entities.MdtCnfgAmendmentCodesEntity;
import com.bsva.translator.AdminTranslator;

public class AmendmentCodesLogic {


public static Logger log = Logger.getLogger(AmendmentCodesLogic.class);
	
	public AmendmentCodesLogic(){
		
	}

	public MdtCnfgAmendmentCodesEntity addAmendmentCodes(AmendmentCodesModel amendmentCodesModel)
	{
		
		MdtCnfgAmendmentCodesEntity mdtCnfgAmendmentCodesEntity = new MdtCnfgAmendmentCodesEntity();
		mdtCnfgAmendmentCodesEntity = new AdminTranslator().translateCommonsAmendmentCodesModelToEntity(amendmentCodesModel);
		
		return  mdtCnfgAmendmentCodesEntity; 
		
		
	}


	public AmendmentCodesModel retreiveAmendmentCodes(MdtCnfgAmendmentCodesEntity localEntity)
	{
		
		AmendmentCodesModel amendmentCodesModel = new AmendmentCodesModel();
		amendmentCodesModel = new AdminTranslator().translateMdtCnfgAmendmentCodesEntityToCommonsModel(localEntity);
		
		         return amendmentCodesModel;
	}

	
	
	public List<AmendmentCodesModel> retreiveAllAmendmentCodes(List<MdtCnfgAmendmentCodesEntity> allMdtCnfgAmendmentCodesEntityList)
	{
		
       List<AmendmentCodesModel> amendmentCodesEntityList = new ArrayList<AmendmentCodesModel>();
       
       log.debug("check if it goes inside the logic method");
		for (MdtCnfgAmendmentCodesEntity mdtCnfgAmendmentCodesEntity : allMdtCnfgAmendmentCodesEntityList)
		{
			AmendmentCodesModel amendmentCodesModel = new AmendmentCodesModel();
			amendmentCodesModel = new AdminTranslator().translateMdtCnfgAmendmentCodesEntityToCommonsModel(mdtCnfgAmendmentCodesEntity);
			amendmentCodesEntityList.add(amendmentCodesModel);
			log.debug("======================amendmentCodesModel================="+amendmentCodesModel);
		}
		 log.debug("check what the list return ***************"+amendmentCodesEntityList);
		 return amendmentCodesEntityList;
		
	
         }

	}




	
	

