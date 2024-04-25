package com.bsva.businessLogic;

import java.util.ArrayList;
import java.util.List;
import com.bsva.commons.model.DebitValueTypeModel;
import com.bsva.entities.MdtCnfgDebitValueTypeEntity;
import com.bsva.translator.AdminTranslator;

/**
 * @author salehas
 * 
 */
public class DebitValueTypeLogic {

	public DebitValueTypeLogic() {

	}

	public List<DebitValueTypeModel> retrieveAllDebitValueType(List<MdtCnfgDebitValueTypeEntity> mdtDebitValueTypeEntityList) {

		
		List<DebitValueTypeModel> debitValueTypeEntityList = new ArrayList<DebitValueTypeModel>();
		DebitValueTypeModel localModel;
		

		for (MdtCnfgDebitValueTypeEntity localEntity:mdtDebitValueTypeEntityList) 
		{
		

			 localModel = new DebitValueTypeModel();
			  localModel = new AdminTranslator().translateDebitValueTypeEntityToCommonsModel(localEntity);
			 debitValueTypeEntityList.add(localModel);
		}

		return debitValueTypeEntityList;
	}

	public MdtCnfgDebitValueTypeEntity adddebValueTypeCode(
			DebitValueTypeModel debitValueTypeModel) {
		MdtCnfgDebitValueTypeEntity mdtDebitValueTypeEntity = new AdminTranslator().translateCommonsDebitValueModelToEntity(debitValueTypeModel);
			

		return mdtDebitValueTypeEntity;
	}
	
	public DebitValueTypeModel retrievedebValueTypeCode(MdtCnfgDebitValueTypeEntity mdtDebitValueTypeEntity)
	{
		DebitValueTypeModel localModel = new DebitValueTypeModel();
		localModel = new AdminTranslator().translateDebitValueTypeEntityToCommonsModel(mdtDebitValueTypeEntity);
		
		return localModel;
	}

}
