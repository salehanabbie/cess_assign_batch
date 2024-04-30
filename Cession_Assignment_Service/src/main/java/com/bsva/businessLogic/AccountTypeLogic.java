package com.bsva.businessLogic;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.bsva.commons.model.AccountTypeModel;
import com.bsva.entities.CasCnfgAccountTypeEntity;
import com.bsva.translator.AdminTranslator;

/**
 * 
 * @author NhlanhlaM
 *
 */

public class AccountTypeLogic {

public static Logger log = Logger.getLogger(AccountTypeLogic.class);
	
	public AccountTypeLogic(){
		
	}

	public CasCnfgAccountTypeEntity addAccountType(AccountTypeModel accountTypeModel)
	{
		
		CasCnfgAccountTypeEntity casCnfgAccountTypeEntity = new CasCnfgAccountTypeEntity();
		casCnfgAccountTypeEntity = new AdminTranslator().translateCommnsAccountTypeModelToEntity(accountTypeModel);
		
		return casCnfgAccountTypeEntity;
		
		
	}


	public AccountTypeModel retreiveAccountType(CasCnfgAccountTypeEntity localEntity)
	{
		
		AccountTypeModel accountTypeModel = new AccountTypeModel();
		accountTypeModel = new AdminTranslator().translateMdtCnfgAccountTypeEntityToCommonsModel(localEntity);
		
		         return accountTypeModel;
	}

	
	
	public List<AccountTypeModel> retreiveAllAccountType(List<CasCnfgAccountTypeEntity> allCasCnfgAccountTypeEntityList)
	{
		
       List<AccountTypeModel> accountTypeEntityList = new ArrayList<AccountTypeModel>();
       
       log.debug("check if it goes inside the logic method");
		for (CasCnfgAccountTypeEntity casCnfgAccountTypeEntity : allCasCnfgAccountTypeEntityList)
		{
			AccountTypeModel accountTypeModel = new AccountTypeModel();
			accountTypeModel = new AdminTranslator().translateMdtCnfgAccountTypeEntityToCommonsModel(
                casCnfgAccountTypeEntity);
			accountTypeEntityList.add(accountTypeModel);
			log.debug("======================accountTypeModel================="+accountTypeModel);
		}
		log.debug("check what the list return ***************"+accountTypeEntityList);
		 return accountTypeEntityList;
		
	
         }

	}



