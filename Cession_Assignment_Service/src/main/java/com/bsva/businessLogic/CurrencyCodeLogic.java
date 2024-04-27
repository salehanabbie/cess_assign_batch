package com.bsva.businessLogic;

import java.util.ArrayList;
import java.util.List;

import com.bsva.commons.model.CurrencyCodesModel;
import com.bsva.entities.CasCnfgCurrencyCodesEntity;
import com.bsva.translator.AdminTranslator;

/**
 * @author salehas
 *
 */
public class CurrencyCodeLogic 
{

	public List<CurrencyCodesModel> retrieveAllCurrencyCodes(List<CasCnfgCurrencyCodesEntity> mdtCurrencyCodesEntity)
	{
			List<CurrencyCodesModel> currCodesList = new ArrayList<CurrencyCodesModel>();
		
			for (CasCnfgCurrencyCodesEntity localEntity : mdtCurrencyCodesEntity)
			{
				CurrencyCodesModel localModel = new CurrencyCodesModel();
				localModel = new  AdminTranslator().translateCurrencyCodesEntityToCommonsModel(localEntity);
				currCodesList.add(localModel);
			}
		
			return currCodesList;
}
	


public CasCnfgCurrencyCodesEntity addCurrencyCodes(CurrencyCodesModel currencyCodesModel)
{
	CasCnfgCurrencyCodesEntity mdtCurrencyCodes = new AdminTranslator().translateCommonsCurrencyCodesModelToEntity(currencyCodesModel);
	
	return mdtCurrencyCodes; 
}

public CurrencyCodesModel retrieveCurrencyCode(CasCnfgCurrencyCodesEntity mdtCurrencyCodesEntity)
{
	CurrencyCodesModel localModel = new CurrencyCodesModel();
	localModel = new AdminTranslator().translateCurrencyCodesEntityToCommonsModel(mdtCurrencyCodesEntity);
	
	return localModel;
}



}



