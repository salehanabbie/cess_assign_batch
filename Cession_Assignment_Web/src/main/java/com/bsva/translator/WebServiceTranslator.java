package com.bsva.translator;

import com.bsva.commons.model.CurrencyCodesModel;
import com.bsva.commons.model.MandatesModel;
import com.bsva.commons.model.MandatesSearchModel;
import com.bsva.models.WebCountryCodesModel;
import com.bsva.models.WebMandateModel;

/**
 * @author SalehaR
 *
 */
public class WebServiceTranslator 
{

	public MandatesModel translateWebMandatesModelToCommonsModel(WebMandateModel webMandateModel)
	{
		MandatesModel mandatesModel = new MandatesModel();
		mandatesModel.setMandateId(webMandateModel.getMandateId());
		mandatesModel.setMandateReqId(webMandateModel.getMandateReqId());
		mandatesModel.setLocalInstrumentCode(webMandateModel.getLocalInstrumentCode());
		mandatesModel.setSequenceType(webMandateModel.getSequenceType());
		mandatesModel.setFrequencyCode(webMandateModel.getFrequencyCode());
		mandatesModel.setFromDate(webMandateModel.getFromDate());
		mandatesModel.setToDate(webMandateModel.getToDate());
		mandatesModel.setFirstCollectionDate(webMandateModel.getFirstCollectionDate());
		mandatesModel.setFinalCollectionDate(webMandateModel.getFinalCollectionDate());
		mandatesModel.setCollectionAmt(webMandateModel.getCollectionAmt());
		mandatesModel.setCollAmtCurrency(webMandateModel.getCollAmtCurrency());
		mandatesModel.setMaximumAmt(webMandateModel.getMaximumAmt());
		mandatesModel.setMaxAmountCurrency(webMandateModel.getMaxAmountCurrency());
		mandatesModel.setMsgId(webMandateModel.getMsgId());
		mandatesModel.setActiveInd(webMandateModel.getActiveInd());
		mandatesModel.setModReason(webMandateModel.getModReason());
		mandatesModel.setProcessStatus(webMandateModel.getProcessStatus());
		mandatesModel.setCreatedBy(webMandateModel.getCreatedBy());
		mandatesModel.setCreatedDate(webMandateModel.getCreatedDate());
		mandatesModel.setModifiedBy(webMandateModel.getModifiedBy());
		mandatesModel.setModifiedDate(webMandateModel.getModifiedDate());
		return mandatesModel;
	}
	
	public WebMandateModel translateCommonsModelToWebMandatesModel(MandatesModel mandatesModel)
	{
		WebMandateModel webMandatesModel = new WebMandateModel();
		
		webMandatesModel.setMandateId(mandatesModel.getMandateId());
		webMandatesModel.setMandateReqId(mandatesModel.getMandateReqId());
		webMandatesModel.setLocalInstrumentCode(mandatesModel.getLocalInstrumentCode());
		webMandatesModel.setSequenceType(mandatesModel.getSequenceType());
		webMandatesModel.setFrequencyCode(mandatesModel.getFrequencyCode());
		webMandatesModel.setFromDate(mandatesModel.getFromDate());
		webMandatesModel.setToDate(mandatesModel.getToDate());
		webMandatesModel.setFirstCollectionDate(mandatesModel.getFirstCollectionDate());
		webMandatesModel.setFinalCollectionDate(mandatesModel.getFinalCollectionDate());
		webMandatesModel.setCollectionAmt(mandatesModel.getCollectionAmt());
		webMandatesModel.setCollAmtCurrency(mandatesModel.getCollAmtCurrency());
		webMandatesModel.setMaximumAmt(mandatesModel.getMaximumAmt());
		webMandatesModel.setMaxAmountCurrency(mandatesModel.getMaxAmountCurrency());
		webMandatesModel.setMsgId(mandatesModel.getMsgId());
		webMandatesModel.setActiveInd(mandatesModel.getActiveInd());
		webMandatesModel.setModReason(mandatesModel.getModReason());
		webMandatesModel.setProcessStatus(mandatesModel.getProcessStatus());
		webMandatesModel.setCreatedBy(mandatesModel.getCreatedBy());
		webMandatesModel.setCreatedDate(mandatesModel.getCreatedDate());
		webMandatesModel.setModifiedBy(mandatesModel.getModifiedBy());
		webMandatesModel.setModifiedDate(mandatesModel.getModifiedDate());
		
		return webMandatesModel;
	}

	public WebCountryCodesModel translateCurrencyCodesToCountryModel(CurrencyCodesModel currencyCodesModel) 
	{
		WebCountryCodesModel webCountryCodesModel = new WebCountryCodesModel();
		
		webCountryCodesModel.setCountryCode(currencyCodesModel.getCountryCode());
		webCountryCodesModel.setActiveInd(currencyCodesModel.getActiveInd());
		
		return webCountryCodesModel;
	}

	public WebMandateModel translateCommonsSearchModelToWebMandateModel(MandatesSearchModel mandateSearchModel)
	{
		WebMandateModel webMandateModel = new WebMandateModel();
		
		webMandateModel.setMandateId(mandateSearchModel.getMandateId());
		webMandateModel.setCreditorBank(mandateSearchModel.getFiName());
		webMandateModel.setCreditorName(mandateSearchModel.getName());
		webMandateModel.setDebtorBank(mandateSearchModel.getFiName());
		webMandateModel.setDebtorName(mandateSearchModel.getName());

		return webMandateModel;
	}
	
	
	public MandatesSearchModel translateWebMandateModelToSearchCommonsModel(WebMandateModel webMandateModel)
	{
	MandatesSearchModel mandateSearchModel = new MandatesSearchModel();
	
	mandateSearchModel.setMandateId(webMandateModel.getMandateId());
	mandateSearchModel.setFiName(webMandateModel.getCreditorBank());
	mandateSearchModel.setName(webMandateModel.getCreditorName());
	mandateSearchModel.setFiName(webMandateModel.getDebtorBank());
	mandateSearchModel.setName(webMandateModel.getDebtorName());
	

	return mandateSearchModel;
	
	}
	
	
}
