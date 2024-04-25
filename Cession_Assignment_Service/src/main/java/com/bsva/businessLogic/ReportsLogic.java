package com.bsva.businessLogic;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.bsva.commons.model.OutstandingResponsesModel;
import com.bsva.commons.model.ReportsNamesModel;
import com.bsva.entities.MdtCnfgReportNamesEntity;
import com.bsva.entities.OutstandingResponsesModelEntity;
import com.bsva.translator.AdminTranslator;



public class ReportsLogic {
	public static Logger log = Logger.getLogger(ReportsLogic.class);
	
	 public ReportsLogic() {

     }

	
	public List<ReportsNamesModel> retrieveAllReportNames(List<MdtCnfgReportNamesEntity> MdtCnfgReportNamesList)
	{
		
		List<ReportsNamesModel> reportNamesList = new ArrayList<ReportsNamesModel>();
		
		ReportsNamesModel localModel;

		for (MdtCnfgReportNamesEntity localEntity : MdtCnfgReportNamesList) 
		
		{
			 localModel = new ReportsNamesModel();
			localModel = new AdminTranslator().translateReportNamesEntityToCommonsModel(localEntity);
			reportNamesList.add(localModel);
		}

		return reportNamesList;
	  }
	
	public MdtCnfgReportNamesEntity addReportNames(ReportsNamesModel reportsNamesModel) {
		MdtCnfgReportNamesEntity mdtCnfgReportNamesEntity = new AdminTranslator()
				.translateCommnsReportNamesModelToEntity(reportsNamesModel);

		return mdtCnfgReportNamesEntity;
	}
	
	public ReportsNamesModel retrieveReportNames(MdtCnfgReportNamesEntity mdtCnfgReportNamesEntity)
	{
		ReportsNamesModel localModel = new ReportsNamesModel();
		localModel = new AdminTranslator().translateReportNamesEntityToCommonsModel(mdtCnfgReportNamesEntity);
		
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




