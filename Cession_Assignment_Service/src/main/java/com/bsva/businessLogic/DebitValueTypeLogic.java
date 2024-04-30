package com.bsva.businessLogic;

import java.util.ArrayList;
import java.util.List;
import com.bsva.commons.model.DebitValueTypeModel;
import com.bsva.entities.CasCnfgDebitValueTypeEntity;
import com.bsva.translator.AdminTranslator;

/**
 * @author salehas
 * 
 */
public class DebitValueTypeLogic {

	public DebitValueTypeLogic() {

	}

	public List<DebitValueTypeModel> retrieveAllDebitValueType(List<CasCnfgDebitValueTypeEntity> mdtDebitValueTypeEntityList) {

		
		List<DebitValueTypeModel> debitValueTypeEntityList = new ArrayList<DebitValueTypeModel>();
		DebitValueTypeModel localModel;
		

		for (CasCnfgDebitValueTypeEntity localEntity:mdtDebitValueTypeEntityList)
		{
		

			 localModel = new DebitValueTypeModel();
			  localModel = new AdminTranslator().translateDebitValueTypeEntityToCommonsModel(localEntity);
			 debitValueTypeEntityList.add(localModel);
		}

		return debitValueTypeEntityList;
	}

	public CasCnfgDebitValueTypeEntity adddebValueTypeCode(
			DebitValueTypeModel debitValueTypeModel) {
		CasCnfgDebitValueTypeEntity mdtDebitValueTypeEntity = new AdminTranslator().translateCommonsDebitValueModelToEntity(debitValueTypeModel);
			

		return mdtDebitValueTypeEntity;
	}
	
	public DebitValueTypeModel retrievedebValueTypeCode(
        CasCnfgDebitValueTypeEntity mdtDebitValueTypeEntity)
	{
		DebitValueTypeModel localModel = new DebitValueTypeModel();
		localModel = new AdminTranslator().translateDebitValueTypeEntityToCommonsModel(mdtDebitValueTypeEntity);
		
		return localModel;
	}

}
