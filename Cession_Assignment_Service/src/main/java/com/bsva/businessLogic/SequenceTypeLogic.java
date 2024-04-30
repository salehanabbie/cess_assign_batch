package com.bsva.businessLogic;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.bsva.commons.model.SequenceTypesModel;
import com.bsva.entities.CasCnfgSequenceTypeEntity;
import com.bsva.translator.AdminTranslator;

/**
 * @author salehas
 *
 */
public class SequenceTypeLogic {
	public static Logger log = Logger.getLogger(SequenceTypeLogic.class);
	

	public SequenceTypeLogic(){
		
	}
	
	public List<SequenceTypesModel> retrieveAllSequenceTypes(List<CasCnfgSequenceTypeEntity> mdtSeqTypesList)
	{
		
		
			List<SequenceTypesModel> seqTypesList = new ArrayList<SequenceTypesModel>();
			SequenceTypesModel seqTypeModel;
			
		
			for (CasCnfgSequenceTypeEntity seqTypeEntity : mdtSeqTypesList)
			{
				
			 seqTypeModel = new SequenceTypesModel();
				seqTypeModel = new AdminTranslator().translateSequenceTypeEntityToSequenceTypesCommonsModel(seqTypeEntity);
				seqTypesList.add(seqTypeModel);
			}
		
			return seqTypesList;
}
	
	
	public CasCnfgSequenceTypeEntity addSequenceTypeCode(
			SequenceTypesModel sequenceTypesModel) {
		CasCnfgSequenceTypeEntity mdtSequenceTypeEntity = new AdminTranslator()
				.translateCommonsSequenceTypesModelToEntity(sequenceTypesModel);

		return mdtSequenceTypeEntity;
	}
	
	public SequenceTypesModel retrieveSequenceCode(CasCnfgSequenceTypeEntity mdtSequenceTypeEntity)
	{
		SequenceTypesModel localModel = new SequenceTypesModel();
		localModel = new AdminTranslator().translateSequenceTypeEntityToSequenceTypesCommonsModel(mdtSequenceTypeEntity);
		
		return localModel;
	}
	
}

