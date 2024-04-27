package com.bsva.businessLogic;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.bsva.commons.model.CisMemberModel;
import com.bsva.commons.model.CustomerParametersModel;
import com.bsva.commons.model.OpsCustParamModel;
import com.bsva.entities.CisMemberEntity;
import com.bsva.entities.CasOpsCustParamEntity;
import com.bsva.entities.CasSysctrlCustParamEntity;
import com.bsva.translator.AdminTranslator;

/**
 * 
 * @author DimakatsoN
 *
 */
public class CustParamLogic
{
	public static Logger log = Logger.getLogger(CustParamLogic.class);
	public CustParamLogic() {

	}

	public List<CustomerParametersModel> retrieveAllCustomerParameter(List<CasSysctrlCustParamEntity> mdtSysCustParamList) {


		List<CustomerParametersModel> custParamsList = new ArrayList<CustomerParametersModel>();
		CustomerParametersModel custParamModel;
	
		for (CasSysctrlCustParamEntity sysCustParamEntity : mdtSysCustParamList) {
			

			 custParamModel = new CustomerParametersModel();
			 
			custParamModel = new AdminTranslator().translateCustomerParametersEntityToCommonsModel(sysCustParamEntity);
			custParamsList.add(custParamModel);
		}

		return custParamsList;
	}


	public CasSysctrlCustParamEntity addCustomerParameter(CustomerParametersModel customerParametersModel) {
		CasSysctrlCustParamEntity casSysctrlCustParamEntity = new AdminTranslator().transalateCommonsCustomerParametersModelToEntity(customerParametersModel);

		return casSysctrlCustParamEntity;
	}


	public CustomerParametersModel retrieveCustomerParameter(CasSysctrlCustParamEntity casSysctrlCustParamEntity)
	{
		CustomerParametersModel custModel = new CustomerParametersModel();
		custModel = new AdminTranslator().translateCustomerParametersEntityToCommonsModel(casSysctrlCustParamEntity);
		
		return custModel;
	}

	public OpsCustParamModel retrieveOpsCustomerParameter(
        CasOpsCustParamEntity casOpsCustParamEntity)
	{
		OpsCustParamModel custModel = new OpsCustParamModel();
		custModel = new AdminTranslator().translateMdtOpsCustParamEntityToCommonsModel(
            casOpsCustParamEntity);
		
		return custModel;
	}

	public CasOpsCustParamEntity retrieveOpsCustomerParamatersEntity(OpsCustParamModel opsCustParamModel)
	{
		CasOpsCustParamEntity casOpsCustParamEntity = new CasOpsCustParamEntity();
		casOpsCustParamEntity = new AdminTranslator().translateOpsCustParamToEntity(opsCustParamModel);
		
		return casOpsCustParamEntity;
	}
	
	
	public List<CustomerParametersModel> translateOpsCustParametersEntityToCommonsModel(List<CasOpsCustParamEntity> mdtOpsCustParamList) {


		List<CustomerParametersModel> custParamsList = new ArrayList<CustomerParametersModel>();
		CustomerParametersModel custParamModel;
	
		for (CasOpsCustParamEntity opsCustParametersEntity : mdtOpsCustParamList) {
			

			 custParamModel = new CustomerParametersModel();
			 
			custParamModel = new AdminTranslator().translateCustomerParametersEntityToCommonsModel(opsCustParametersEntity);
			custParamsList.add(custParamModel);
		}

		return custParamsList;
	}

	public List<CisMemberModel> retrieveCisMember(List<CisMemberEntity> cisMemberEntity) 
	{
		 List<CisMemberModel> cisMemberModelList = new ArrayList<CisMemberModel>();
		 
		 for (CisMemberEntity localEntity : cisMemberEntity)
		 {
			 CisMemberModel cisMemberModel = new CisMemberModel();
			 cisMemberModel= new AdminTranslator().translateCisMemberEntityToCommonsModel(localEntity);
			 cisMemberModelList.add(cisMemberModel);
		 }
		    log.debug("Customer model list from logic method --> " + cisMemberModelList.toString());
		   return cisMemberModelList;
	}
	
}
