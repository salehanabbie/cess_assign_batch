package com.bsva.businessLogic;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.bsva.commons.model.OutstandingResponsesModel;
import com.bsva.commons.model.ReportsNamesModel;
import com.bsva.entities.CasCnfgReportNamesEntity;
import com.bsva.entities.OutstandingResponsesModelEntity;
import com.bsva.translator.AdminTranslator;



public class ReportsLogic {
	public static Logger log = Logger.getLogger(ReportsLogic.class);
	
	 public ReportsLogic() {

     }

	
	public List<ReportsNamesModel> retrieveAllReportNames(List<CasCnfgReportNamesEntity> MdtCnfgReportNamesList)
	{
		
		List<ReportsNamesModel> reportNamesList = new ArrayList<ReportsNamesModel>();
		
		ReportsNamesModel localModel;

		for (CasCnfgReportNamesEntity localEntity : MdtCnfgReportNamesList)
		
		{
			 localModel = new ReportsNamesModel();
			localModel = new AdminTranslator().translateReportNamesEntityToCommonsModel(localEntity);
			reportNamesList.add(localModel);
		}

		return reportNamesList;
	  }
	
	public CasCnfgReportNamesEntity addReportNames(ReportsNamesModel reportsNamesModel) {
		CasCnfgReportNamesEntity casCnfgReportNamesEntity = new AdminTranslator()
				.translateCommnsReportNamesModelToEntity(reportsNamesModel);

		return casCnfgReportNamesEntity;
	}
	
	public ReportsNamesModel retrieveReportNames(CasCnfgReportNamesEntity casCnfgReportNamesEntity)
	{
		ReportsNamesModel localModel = new ReportsNamesModel();
		localModel = new AdminTranslator().translateReportNamesEntityToCommonsModel(
            casCnfgReportNamesEntity);
		
		return localModel;
	  }
	
	
	public OutstandingResponsesModel translateOutstandingResponsesEntityToCommonsModel(OutstandingResponsesModelEntity outEntity)

	{			
		OutstandingResponsesModel outstandingResponsesModel = new OutstandingResponsesModel();
		
		outstandingResponsesModel.setMandateReqTranId(outEntity.getMandateReqTranId());
		outstandingResponsesModel.setMandateRefNr(outEntity.getMandateRefNr());
		outstandingResponsesModel.setMandateReqId(outEntity.getMandateReqId());
		outstandingResponsesModel.setServiceId(outEntity.getServiceId());
		outstandingResponsesModel.setCreditorMemberNo(outEntity.getCreditorMemberNo());
		outstandingResponsesModel.setFileName(outEntity.getFileName());
		outstandingResponsesModel.setBankName(outEntity.getBankName());
		
		return outstandingResponsesModel;
	}
	
	
	
	
	}




