package com.bsva.businessLogic;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.bsva.commons.model.SequenceTypesModel;
import com.bsva.entities.MdtCnfgSequenceTypeEntity;
import com.bsva.translator.AdminTranslator;

/**
 * @author salehas
 *
 */
public class SequenceTypeLogic {
	public static Logger log = Logger.getLogger(SequenceTypeLogic.class);
	

	public SequenceTypeLogic(){
		
	}
	
	public List<SequenceTypesModel> retrieveAllSequenceTypes(List<MdtCnfgSequenceTypeEntity> mdtSeqTypesList)
	{
		
		
			List<SequenceTypesModel> seqTypesList = new ArrayList<SequenceTypesModel>();
			SequenceTypesModel seqTypeModel;
			
		
			for (MdtCnfgSequenceTypeEntity seqTypeEntity : mdtSeqTypesList) 
			{
				
			 seqTypeModel = new SequenceTypesModel();
				seqTypeModel = new AdminTranslator().translateSequenceTypeEntityToSequenceTypesCommonsModel(seqTypeEntity);
				seqTypesList.add(seqTypeModel);
			}
		
			return seqTypesList;
}
	
	
	public MdtCnfgSequenceTypeEntity addSequenceTypeCode(
			SequenceTypesModel sequenceTypesModel) {
		MdtCnfgSequenceTypeEntity mdtSequenceTypeEntity = new AdminTranslator()
				.translateCommonsSequenceTypesModelToEntity(sequenceTypesModel);

		return mdtSequenceTypeEntity;
	}
	
	public SequenceTypesModel retrieveSequenceCode(MdtCnfgSequenceTypeEntity mdtSequenceTypeEntity)
	{
		SequenceTypesModel localModel = new SequenceTypesModel();
		localModel = new AdminTranslator().translateSequenceTypeEntityToSequenceTypesCommonsModel(mdtSequenceTypeEntity);
		
		return localModel;
	}
	
}

