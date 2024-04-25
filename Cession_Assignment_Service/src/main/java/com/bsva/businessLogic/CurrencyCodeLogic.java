package com.bsva.businessLogic;

import java.util.ArrayList;
import java.util.List;

import com.bsva.commons.model.CurrencyCodesModel;
import com.bsva.entities.MdtCnfgCurrencyCodesEntity;
import com.bsva.translator.AdminTranslator;

/**
 * @author salehas
 *
 */
public class CurrencyCodeLogic 
{

	public List<CurrencyCodesModel> retrieveAllCurrencyCodes(List<MdtCnfgCurrencyCodesEntity> mdtCurrencyCodesEntity)
	{
			List<CurrencyCodesModel> currCodesList = new ArrayList<CurrencyCodesModel>();
		
			for (MdtCnfgCurrencyCodesEntity localEntity : mdtCurrencyCodesEntity) 
			{
				CurrencyCodesModel localModel = new CurrencyCodesModel();
				localModel = new  AdminTranslator().translateCurrencyCodesEntityToCommonsModel(localEntity);
				currCodesList.add(localModel);
			}
		
			return currCodesList;
}
	


public MdtCnfgCurrencyCodesEntity addCurrencyCodes(CurrencyCodesModel currencyCodesModel)
{
	MdtCnfgCurrencyCodesEntity mdtCurrencyCodes = new AdminTranslator().translateCommonsCurrencyCodesModelToEntity(currencyCodesModel);
	
	return mdtCurrencyCodes; 
}

public CurrencyCodesModel retrieveCurrencyCode(MdtCnfgCurrencyCodesEntity mdtCurrencyCodesEntity)
{
	CurrencyCodesModel localModel = new CurrencyCodesModel();
	localModel = new AdminTranslator().translateCurrencyCodesEntityToCommonsModel(mdtCurrencyCodesEntity);
	
	return localModel;
}



}



