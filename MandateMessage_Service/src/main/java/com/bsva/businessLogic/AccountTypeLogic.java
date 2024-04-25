package com.bsva.businessLogic;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.bsva.commons.model.AccountTypeModel;
import com.bsva.entities.MdtCnfgAccountTypeEntity;
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

	public MdtCnfgAccountTypeEntity addAccountType(AccountTypeModel accountTypeModel)
	{
		
		MdtCnfgAccountTypeEntity mdtCnfgAccountTypeEntity = new MdtCnfgAccountTypeEntity();
		mdtCnfgAccountTypeEntity = new AdminTranslator().translateCommnsAccountTypeModelToEntity(accountTypeModel);
		
		return  mdtCnfgAccountTypeEntity; 
		
		
	}


	public AccountTypeModel retreiveAccountType(MdtCnfgAccountTypeEntity localEntity)
	{
		
		AccountTypeModel accountTypeModel = new AccountTypeModel();
		accountTypeModel = new AdminTranslator().translateMdtCnfgAccountTypeEntityToCommonsModel(localEntity);
		
		         return accountTypeModel;
	}

	
	
	public List<AccountTypeModel> retreiveAllAccountType(List<MdtCnfgAccountTypeEntity> allMdtCnfgAccountTypeEntityList)
	{
		
       List<AccountTypeModel> accountTypeEntityList = new ArrayList<AccountTypeModel>();
       
       log.debug("check if it goes inside the logic method");
		for (MdtCnfgAccountTypeEntity mdtCnfgAccountTypeEntity : allMdtCnfgAccountTypeEntityList)
		{
			AccountTypeModel accountTypeModel = new AccountTypeModel();
			accountTypeModel = new AdminTranslator().translateMdtCnfgAccountTypeEntityToCommonsModel(mdtCnfgAccountTypeEntity);
			accountTypeEntityList.add(accountTypeModel);
			log.debug("======================accountTypeModel================="+accountTypeModel);
		}
		log.debug("check what the list return ***************"+accountTypeEntityList);
		 return accountTypeEntityList;
		
	
         }

	}



