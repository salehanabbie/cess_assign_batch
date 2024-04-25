package com.bsva;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import org.apache.log4j.Logger;
import org.hibernate.ObjectNotFoundException;
import com.bsva.beans.GenericDAO;
import com.bsva.businessLogic.CompanyParametersLogic;
import com.bsva.businessLogic.CustParamLogic;
import com.bsva.businessLogic.SysCisBankLogic;
import com.bsva.commons.model.OpsCustParamModel;
import com.bsva.commons.model.SysCisBankModel;
import com.bsva.commons.model.SysctrlCompParamModel;
import com.bsva.entities.MdtAcArcConfDetailsEntity;
import com.bsva.entities.MdtAcArcConfHdrsEntity;
import com.bsva.entities.MdtAcArcDailyBillingEntity;
import com.bsva.entities.MdtAcArcFileRegEntity;
import com.bsva.entities.MdtAcArcGrpHdrEntity;
import com.bsva.entities.MdtAcArcMndtCountEntity;
import com.bsva.entities.MdtAcArcStatusDetailsEntity;
import com.bsva.entities.MdtAcArcStatusHdrsEntity;
import com.bsva.entities.MdtAcOpsConfDetailsEntity;
import com.bsva.entities.MdtAcOpsConfHdrsEntity;
import com.bsva.entities.MdtAcOpsDailyBillingEntity;
import com.bsva.entities.MdtAcOpsFileSizeLimitEntity;
import com.bsva.entities.MdtAcOpsGrpHdrEntity;
import com.bsva.entities.MdtAcOpsMndtCountEntity;
import com.bsva.entities.MdtAcOpsStatusDetailsEntity;
import com.bsva.entities.MdtAcOpsStatusHdrsEntity;
import com.bsva.entities.MdtCnfgAccountTypeEntity;
import com.bsva.entities.MdtCnfgAdjustmentCatEntity;
import com.bsva.entities.MdtCnfgAmendmentCodesEntity;
import com.bsva.entities.MdtCnfgAuthTypeEntity;
import com.bsva.entities.MdtCnfgCancellationCodesEntity;
import com.bsva.entities.MdtCnfgDebitValueTypeEntity;
import com.bsva.entities.MdtCnfgErrorCodesEntity;
import com.bsva.entities.MdtCnfgFrequencyCodesEntity;
import com.bsva.entities.MdtCnfgLocalInstrCodesEntity;
import com.bsva.entities.MdtCnfgReasonCodesEntity;
import com.bsva.entities.MdtCnfgRejectReasonCodesEntity;
import com.bsva.entities.MdtCnfgSequenceTypeEntity;
import com.bsva.entities.MdtCnfgValRuleEntity;
import com.bsva.entities.MdtOpsCustParamEntity;
import com.bsva.entities.MdtOpsFileRegEntity;
import com.bsva.entities.MdtOpsRefSeqNrEntity;
import com.bsva.entities.MdtOpsServicesEntity;
import com.bsva.entities.CasSysctrlCompParamEntity;
import com.bsva.entities.CasSysctrlSysParamEntity;
import com.bsva.entities.SysCisBankEntity;
import com.bsva.entities.SysCisBranchEntity;
import com.bsva.interfaces.ValidationBeanLocal;
import com.bsva.interfaces.ValidationBeanRemote;

/**
 * @author Saleha Saib
 *
 */
@Stateless
@Remote(ValidationBeanRemote.class)
public class ValidationBean  implements ValidationBeanRemote, ValidationBeanLocal {

	@EJB
	GenericDAO genericDAO;
	public static Logger log = Logger.getLogger(ValidationBean.class);
	String webProcess = "WEB";
	String bendProcess = "BACKEND";

	String extractStatus = "4";
	String matchedStatus = "M";
	String rejectedStatus = "R";
	String responseRecStatus = "9";


	public ValidationBean()
	{

	}

	public Object validateServiceId_002(String serviceName) 
	{		
		MdtOpsServicesEntity mdtOpsServicesEntity = new MdtOpsServicesEntity();
		try
		{
			mdtOpsServicesEntity = (MdtOpsServicesEntity) genericDAO.findByNamedQuery("MdtOpsServicesEntity.findByServiceIdIn","serviceIdIn", serviceName.toUpperCase());
		}
		catch(ObjectNotFoundException onfe)
		{
			log.error("No Object Found: "+ onfe.getMessage());
		}
		catch(Exception e)
		{
			log.error("Error on validateServiceId_002: "+ e.getMessage());
		}

		return mdtOpsServicesEntity;
	}

	public Object validateBicCode_003(String bicCode, String process)
	{
		/*CisMemberEntity cisMemberEntity = (CisMemberEntity) genericDAO.findByNamedQuery("CisMemberEntity.findByLikeSamosBicCodeLive","samosBicCodeLive", bicCode.toUpperCase()+ "%");
		if(cisMemberEntity != null && process.equalsIgnoreCase(webProcess))
		{
			CisMemberLogic cisMemberLogic = new CisMemberLogic();
			CisMemberModel cisMemberModel = new CisMemberModel();
			cisMemberModel = cisMemberLogic.retrieveCisMember(cisMemberEntity);
			return cisMemberModel;
		}
		else
			return cisMemberEntity;*/

		SysCisBankEntity  sysCisBankEntity = (SysCisBankEntity)genericDAO.findByNamedQuery( "SysCisBankEntity.findByMemberNo","memberNo",bicCode.toUpperCase()+ "%");
		if(sysCisBankEntity!= null && process.equalsIgnoreCase(webProcess))
		{
			SysCisBankLogic sysCisBankLogic = new SysCisBankLogic();
			SysCisBankModel sysCisBankModel = new SysCisBankModel();
			sysCisBankModel= sysCisBankLogic.retrieveSysCisBank(sysCisBankEntity);
		}

		return sysCisBankEntity;
	}

	public Object validateFileNumberingInMsgId_004(String instId)
	{

		log.debug("instId: "+instId);
		MdtOpsCustParamEntity mdtSysctrlCustParamEntity = (MdtOpsCustParamEntity) genericDAO.findByNamedQuery("MdtOpsCustParamEntity.findByInstId","instId", instId);

		return mdtSysctrlCustParamEntity;
	}

	public List<?> retrieveStatusHdrs(String service)
	{
		List<MdtAcOpsStatusHdrsEntity> mdtAcOpsStatusHdrsEntityList = genericDAO.findAllByNamedQuery("MdtAcOpsStatusHdrsEntity.findByService", "service", service);
		return mdtAcOpsStatusHdrsEntityList;
	}
	public Object validateMemberNo(String memberNo)
	{
		SysCisBankEntity sysCisBankEntity = (SysCisBankEntity) genericDAO.findByNamedQuery("SysCisBankEntity.findByMemberNo","memberNo", memberNo);
		return sysCisBankEntity;
	}

	public Object validateInstallmentOccurrence_013(String seqCode)
	{
		MdtCnfgSequenceTypeEntity mdtSequenceTypeEntity = (MdtCnfgSequenceTypeEntity) genericDAO.findByNamedQuery("MdtCnfgSequenceTypeEntity.findBySeqTypeCode", "seqTypeCode",seqCode);	
		return mdtSequenceTypeEntity;
	}

	public Object validateInstallmentFrequency_014(String freqCode)
	{
		MdtCnfgFrequencyCodesEntity mdtFrequencyCodesEntity = (MdtCnfgFrequencyCodesEntity) genericDAO.findByNamedQuery("MdtCnfgFrequencyCodesEntity.findByFrequencyCode", "frequencyCode",freqCode);
		return mdtFrequencyCodesEntity;
	}
	/*
	public Object validateBranchNo(String branchNo,String activeInd)
	{
		SysCisBranchEntity sysCisBranchEntity = (SysCisBranchEntity) genericDAO.findByNamedQuery("SysCisBranchEntity.findByBranchNo","branchNo", branchNo);
		return sysCisBranchEntity;
	}
	 */

	//Validate Debtor Branch number
	public Object validateDebtorBranchNo(String branchNo,String activeInd)
	{
		SysCisBranchEntity sysCisBranchEntity = new SysCisBranchEntity();
		try 
		{
			log.debug("DebtorBranchNo: "+branchNo);
			log.debug("activeInd: "+activeInd);

			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("branchNo", branchNo);
			parameters.put("acDebtor", activeInd);
			log.debug("---------------sparameters: ------------------"+ parameters.toString());
			sysCisBranchEntity = (SysCisBranchEntity) genericDAO.findByCriteria(SysCisBranchEntity.class, parameters);
			log.debug("---------------sysCisBranchEntity after findByCriteria: ------------------"+ sysCisBranchEntity);
		} 
		catch (ObjectNotFoundException onfe) 
		{
			log.debug("No Object Exists on DB");
		} 
		catch (Exception e) 
		{
			log.error("Error on validateDebtorBranchNo: "+ e.getMessage());
			e.printStackTrace();
		}

		return sysCisBranchEntity;
	}

	//Validate Creditor Branch number
	public Object validateCreditorBranchNo(String branchNo,String activeInd)
	{
		SysCisBranchEntity sysCisBranchEntity = new SysCisBranchEntity();
		try 
		{
			log.debug("CreditorBranchNo: "+branchNo);
			log.debug("activeInd: "+activeInd);

			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("branchNo", branchNo);
			parameters.put("acCreditor", activeInd);
			log.debug("---------------sparameters: ------------------"+ parameters.toString());
			sysCisBranchEntity = (SysCisBranchEntity) genericDAO.findByCriteria(SysCisBranchEntity.class, parameters);
			log.debug("---------------sysCisBranchEntity after findByCriteria: ------------------"+ sysCisBranchEntity);
		} 
		catch (ObjectNotFoundException onfe) 
		{
			log.debug("No Object Exists on DB");
		} 
		catch (Exception e) 
		{
			log.error("Error on validateCreditorBranchNo: "+ e.getMessage());
			e.printStackTrace();
		}

		return sysCisBranchEntity;
	}




	public Object retrieveErrorCode(String errorCode)
	{
		MdtCnfgErrorCodesEntity mdtCnfgErrorCodesEntity = (MdtCnfgErrorCodesEntity) genericDAO.findByNamedQuery("MdtCnfgErrorCodesEntity.findByErrorCode","errorCode", errorCode);
		return mdtCnfgErrorCodesEntity;
	}

	public Object retrieveSystemParameters (String  activeInd)
	{
		CasSysctrlSysParamEntity  casSysctrlSysParamEntity = (CasSysctrlSysParamEntity)genericDAO.findByNamedQuery("MdtSysctrlSysParamEntity.findByActiveInd", "activeInd", activeInd);
		return casSysctrlSysParamEntity;
	}

	public Object retrieveOpsCustParam(String bicCode)
	{
		MdtOpsCustParamEntity  mdtOpsCustParamEntity=(MdtOpsCustParamEntity)genericDAO.findByNamedQuery("MdtOpsCustParamEntity.findByInstId", "instId", bicCode);

		return mdtOpsCustParamEntity;
	}

	//AC methods to retrieve data for archive purposes

	public Object retrieveOriginalACGrpHdr(String msgId)
	{
		MdtAcOpsGrpHdrEntity mdtAcOpsGrpHdrEntity = (MdtAcOpsGrpHdrEntity) genericDAO.findByNamedQuery("MdtAcOpsGrpHdrEntity.findByMsgId","msgId", msgId);

		return mdtAcOpsGrpHdrEntity;
	}

	public Object retrieveServiceID(String service)
	{

		List <MdtAcOpsStatusHdrsEntity> mdtAcOpsStatusHdrsEntityList = genericDAO.findAllByNamedQuery("MdtAcOpsStatusHdrsEntity.findByService", "service", service);
		log.debug("mdtAcOpsStatusHdrsEntityList----------->> "+ mdtAcOpsStatusHdrsEntityList);
		return mdtAcOpsStatusHdrsEntityList;
	}
	public Object retrieveStatusDetails (BigDecimal statusHdrSeqNo)
	{
		List <MdtAcOpsStatusDetailsEntity> mdtAcOpsStatusDetailsEntityList = genericDAO.findAllByNamedQuery("MdtAcOpsStatusDetailsEntity.findByStatusHdrSeqNo", "statusHdrSeqNo",statusHdrSeqNo );
		return mdtAcOpsStatusDetailsEntityList;
	}

	//AC methods to create Archive process
	public boolean createACArchiveStatusDetails(Object obj)
	{
		if(obj instanceof MdtAcArcStatusDetailsEntity)
		{
			MdtAcArcStatusDetailsEntity mdtAcArcStatusDetailsEntity = (MdtAcArcStatusDetailsEntity) obj;
			try
			{
				genericDAO.save(mdtAcArcStatusDetailsEntity);
				return true;
			}
			catch(Exception ex)
			{
				log.error("Error on createACArchiveStatusDetails: "+ex.getMessage());
				return false;
			}
		}
		else
			return false;
	}

	public boolean createACArchiveStatusHdrs(Object obj)
	{
		if(obj instanceof MdtAcArcStatusHdrsEntity)
		{
			MdtAcArcStatusHdrsEntity mdtAcArcStatusHdrsEntity = (MdtAcArcStatusHdrsEntity) obj;
			try
			{
				genericDAO.save(mdtAcArcStatusHdrsEntity);
				return true;
			}
			catch(Exception ex)
			{
				log.error("Error on createACArchiveStatusHdrs: "+ex.getMessage());
				return false;
			}
		}
		else
			return false;
	}

	public boolean createACArcGrpHDR(Object obj)
	{
		if(obj instanceof MdtAcArcGrpHdrEntity)
		{
			MdtAcArcGrpHdrEntity mdtAcArcOpsGrpHdrEntity = (MdtAcArcGrpHdrEntity) obj;
			try
			{
				log.debug("TRYING TO SAVE GROUP HEADER ==> "+mdtAcArcOpsGrpHdrEntity.getMsgId());
				String saved = genericDAO.save(mdtAcArcOpsGrpHdrEntity);

				if(saved.equalsIgnoreCase("DUPL"))
					return false;
				else
					return true;
			}
			catch(Exception ex)
			{
				log.error("Error on createACArcMndtMsg: "+ex.getMessage());
				return false;
			}
		}
		else
			return false;
	}

	public boolean deleteOriginalACGrpHdr(Object obj)
	{
		if(obj instanceof MdtAcOpsGrpHdrEntity)
		{
			MdtAcOpsGrpHdrEntity mdtAcOpsGrpHdrEntity = (MdtAcOpsGrpHdrEntity) obj;
			try
			{
				genericDAO.delete(mdtAcOpsGrpHdrEntity);
				log.debug("**************Deleting original group header***************");
				return true;
			}
			catch(Exception ex)
			{
				log.error("Error on deleteOriginalGrpHdr: "+ex.getMessage());
				return false;
			}
		}
		else
			return false;
	}

	public boolean deleteOriginalACStatusDetails(Object obj)
	{
		if(obj instanceof MdtAcOpsStatusDetailsEntity)
		{
			MdtAcOpsStatusDetailsEntity mdtAcOpsStatusDetailsEntity = (MdtAcOpsStatusDetailsEntity) obj;
			try
			{

				genericDAO.delete(mdtAcOpsStatusDetailsEntity);
				log.debug("**************Deleting original status Details***************");
				return true;
			}
			catch(Exception ex)
			{
				log.error("Error on deleteOriginalACStatusDetails: "+ex.getMessage());
				return false;
			}
		}
		else
			return false;
	}


	public boolean deleteOriginalACStatusHdrs(Object obj)
	{
		if(obj instanceof MdtAcOpsStatusHdrsEntity)
		{
			MdtAcOpsStatusHdrsEntity mdtAcOpsStatusHdrsEntity = (MdtAcOpsStatusHdrsEntity) obj;
			try
			{

				genericDAO.delete(mdtAcOpsStatusHdrsEntity);
				log.debug("**************Deleting original status Details***************");
				return true;
			}
			catch(Exception ex)
			{
				log.error("Error on deleteOriginalACStatusHdrs: "+ex.getMessage());
				return false;
			}
		}
		else
			return false;
	}

	public boolean updateCompFileNo(Object obj)
	{
		if(obj instanceof CasSysctrlCompParamEntity)
		{
			CasSysctrlCompParamEntity mdtSysctrlCompParamEntity = (CasSysctrlCompParamEntity) obj;
			try
			{
				genericDAO.saveOrUpdate(mdtSysctrlCompParamEntity);
				return true;
			}
			catch(Exception ex)
			{
				log.error("Error on updateCompFileNo: "+ex.getMessage());
				return false;
			}
		}
		else
			return false;
	}

	public Object retrieveCompanyParameters(String process)
	{
		CasSysctrlCompParamEntity mdtSysctrlCompParamEntity = (CasSysctrlCompParamEntity) genericDAO.findByNamedQuery( "MdtSysctrlCompParamEntity.findAll", null, null);
		log.debug("mdtSysctrlCompParamEntity: "+mdtSysctrlCompParamEntity);
		if(mdtSysctrlCompParamEntity != null && process.equalsIgnoreCase(webProcess))
		{
			CompanyParametersLogic companyParametersLogic = new CompanyParametersLogic();
			SysctrlCompParamModel compParModel = new SysctrlCompParamModel();
			compParModel = companyParametersLogic.retrieveCompanyParameters(mdtSysctrlCompParamEntity);
			return compParModel;
		}
		else
			return mdtSysctrlCompParamEntity;
	}

	public Object retrieveOpsServiceIn(String inService)
	{
		MdtOpsServicesEntity mdtOpsServicesEntity = (MdtOpsServicesEntity) genericDAO.findByNamedQuery("MdtOpsServicesEntity.findByServiceIdIn", "serviceIdIn", inService);
		return mdtOpsServicesEntity;
	}

	public Object validateCancelReasonCode(String reasonCode)
	{

		MdtCnfgCancellationCodesEntity  mdtCnfgReasonCodesEntity = (MdtCnfgCancellationCodesEntity) genericDAO.findByNamedQuery("MdtCnfgCancellationCodesEntity.findByCancellationCode", "cancellationCode",reasonCode);

		return mdtCnfgReasonCodesEntity;
	}

	public boolean updateMsgLastFileSeqNr(Object obj, String process)
	{
		MdtOpsCustParamEntity mdtOpsCustParamEntity = null;
		if(process.equalsIgnoreCase(webProcess))
		{
			OpsCustParamModel opsCustParamModel = (OpsCustParamModel) obj;
			CustParamLogic custParamLogic = new CustParamLogic();
			mdtOpsCustParamEntity = custParamLogic.retrieveMdtOpsCustomerParamatersEntity(opsCustParamModel);
		}
		else
		{
			if(obj instanceof MdtOpsCustParamEntity)
				mdtOpsCustParamEntity = (MdtOpsCustParamEntity) obj;
			log.debug("mdtOpsCustParamEntity from updateMsgLastFileSeqNr:"+mdtOpsCustParamEntity);
		}

		if(mdtOpsCustParamEntity != null)
		{
			try
			{
				log.debug("mdtOpsCustParamEntity just before save:"+mdtOpsCustParamEntity);
				genericDAO.saveOrUpdate(mdtOpsCustParamEntity);
				return true;
			}
			catch(Exception ex)
			{
				log.error("Error on updateMsgLastFileSeqNr: "+ex.getMessage());
				return false;
			}
		}
		else
			return false;


	}

	public Object retrieveOpsCustomerParameters(String instId, String process)
	{
		MdtOpsCustParamEntity mdtOpsCustParamEntity = (MdtOpsCustParamEntity) genericDAO.findByNamedQuery("MdtOpsCustParamEntity.findByInstId","instId", instId);
		if(mdtOpsCustParamEntity != null && process.equalsIgnoreCase(webProcess))
		{
			CustParamLogic customerCustParamLogic = new CustParamLogic();
			OpsCustParamModel custParamModel = new OpsCustParamModel();
			custParamModel = customerCustParamLogic.retrieveOpsCustomerParameter(mdtOpsCustParamEntity);
			return custParamModel;
		}
		else
			return mdtOpsCustParamEntity;
	}

	public boolean updateOpsFileReg(Object obj)
	{
		if(obj instanceof MdtOpsFileRegEntity)
		{
			MdtOpsFileRegEntity mdtOpsFileRegEntity = (MdtOpsFileRegEntity) obj;
			log.debug("mdtOpsFileRegEntity in VAL BEAN ==> "+mdtOpsFileRegEntity);
			try
			{
				genericDAO.saveOrUpdate(mdtOpsFileRegEntity);
				return true;
			}
			catch(Exception ex)
			{
				log.error("Error on updateOpsFileReg: "+ex.getMessage());
				return false;
			}
		}
		else
			return false;
	}

	public Object retrieveOpsFileReg(String fileName)
	{
		MdtOpsFileRegEntity mdtOpsFileRegEntity = (MdtOpsFileRegEntity) genericDAO.findByNamedQuery("MdtOpsFileRegEntity.findByFileName", "fileName", fileName);
		return mdtOpsFileRegEntity;
	}

	public Object validateDebitValueType(String debitValueType)
	{
		MdtCnfgDebitValueTypeEntity mdtDebitValueTypeEntity = (MdtCnfgDebitValueTypeEntity) genericDAO.findByNamedQuery("MdtCnfgDebitValueTypeEntity.findByDebValueTypeDescription", "debValueTypeDescription",debitValueType);
		return mdtDebitValueTypeEntity;
	}

	public Object retrieveRefSeqNr(String serviceId, String instId)
	{
		MdtOpsRefSeqNrEntity mdtOpsFileSeqNrEntity = null;

		try
		{
			log.debug("serviceId: "+serviceId);
			log.debug("instId: "+instId);

			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("mdtOpsRefSeqNrPK.serviceId", serviceId);
			parameters.put("mdtOpsRefSeqNrPK.memberNo", instId);
			log.debug("---------------sparameters: ------------------"+ parameters.toString());
			mdtOpsFileSeqNrEntity = (MdtOpsRefSeqNrEntity) genericDAO.findByCriteria(MdtOpsRefSeqNrEntity.class, parameters);
			log.debug("---------------MdtOpsRefSeqNrEntity after findByCriteria: ------------------"+ mdtOpsFileSeqNrEntity);

			//mdtOpsFileSeqNrEntity = (MdtOpsRefSeqNrEntity) genericDAO.findByNamedQuery("MdtOpsRefSeqNrEntity.findByServiceId","mdtOpsRefSeqNrPK.serviceId", serviceId);
		}
		catch(ObjectNotFoundException onfe)
		{
			log.error("No Object Found:"+ onfe.getMessage());
		}
		catch(Exception e)
		{
			log.error("Error on retrieveFileSeqNo:" + e.getMessage());
		}

		return mdtOpsFileSeqNrEntity;

	}

	public boolean updateOpsRefSeqNr(Object obj)
	{
		if(obj instanceof MdtOpsRefSeqNrEntity)
		{
			MdtOpsRefSeqNrEntity mdtOpsRefSeqNrEntity = (MdtOpsRefSeqNrEntity) obj;
			try
			{
				genericDAO.saveOrUpdate(mdtOpsRefSeqNrEntity);
				return true;
			}
			catch(Exception ex)
			{
				log.error("Error on updateOpsRefSeqNr: "+ex.getMessage());
				return false;
			}
		}
		else
			return false;
	}

	public BigDecimal saveOpsStatusHdrs(Object obj)
	{
		BigDecimal sysSeqNo = BigDecimal.ZERO;
		try
		{
			if(obj instanceof MdtAcOpsStatusHdrsEntity)
			{
				MdtAcOpsStatusHdrsEntity localEntity = (MdtAcOpsStatusHdrsEntity) obj;
				log.debug("localEntity=====>>>>: "+ localEntity);
				sysSeqNo = genericDAO.saveReturnId(localEntity);
				//genericDAO.save(localEntity);
			}
		}
		catch(Exception e)
		{
			log.error("Error on saveOpsStatusHdrs: "+ e.getMessage());
			e.printStackTrace();
		}
		return sysSeqNo;
	}

	public boolean saveOpsStatusDetailsRecord(Object obj)
	{
		boolean saved = false;
		try
		{
			log.debug("In the saveOpsStatusDetailsRecord Persistence Layer ... ");
			if(obj instanceof MdtAcOpsStatusDetailsEntity)
			{

				MdtAcOpsStatusDetailsEntity localEntity = (MdtAcOpsStatusDetailsEntity) obj;
				log.debug("Writing to OpsStatusDetailsEntity table.... "+localEntity);
				genericDAO.save(localEntity);
				saved = true;
			}
			else
				saved = false;
		}
		catch(Exception e)
		{
			log.error("Error on saveOpsStatusDetailsRecord"+e.getMessage());
			e.printStackTrace();
			saved = false;
		}

		return saved;
	}


	public boolean saveOpsStatusDetails(List<?> opsStatusDetailsList)
	{
		boolean saved = false;
		try
		{
			log.debug("In the saveOpsStatusDetails Persistence Layer ... ");
			if(opsStatusDetailsList.size() > 0)
			{
				for (Object obj : opsStatusDetailsList) 
				{
					if(obj instanceof MdtAcOpsStatusDetailsEntity)
					{

						MdtAcOpsStatusDetailsEntity localEntity = (MdtAcOpsStatusDetailsEntity) obj;
						log.debug("Writing to OpsStatusDetailsEntity table.... "+localEntity);
						genericDAO.save(localEntity);
						saved = true;
					}
				}			
			}
			else
				saved = false;
		}
		catch(Exception e)
		{
			log.error("Error on saveOpsStatusDetails"+e.getMessage());
			e.printStackTrace();
			saved = false;
		}

		return saved;
	}


	public List<?> findOpsStatusDetByCriteria(String namedQuery, String fieldName, BigDecimal value, String txnId)
	{
		List<MdtAcOpsStatusDetailsEntity> opsStatusDetailsList = new ArrayList<MdtAcOpsStatusDetailsEntity>();
		try
		{
			if(value != null)
				opsStatusDetailsList = (List<MdtAcOpsStatusDetailsEntity>) genericDAO.findAllByNamedQuery(namedQuery, fieldName, value);
			else
			{
				if(txnId != null)
					opsStatusDetailsList = (List<MdtAcOpsStatusDetailsEntity>) genericDAO.findAllByNamedQuery(namedQuery, fieldName, txnId);
			}
		}
		catch(ObjectNotFoundException onfe)
		{
			log.error("No Object Found: "+ onfe.getMessage());
		}
		catch(Exception e)
		{
			log.error("Error on findOpsStatusDetByCriteria: "+ e.getMessage());
		}


		return opsStatusDetailsList;
	}

	@Override
	public Object validateRejectReasonCode(String reasonCode) {
		MdtCnfgReasonCodesEntity mdtCnfgReasonCodesEntity = (MdtCnfgReasonCodesEntity) genericDAO.findByNamedQuery("MdtCnfgReasonCodesEntity.findByReasonCode", "reasonCode",reasonCode);
		return mdtCnfgReasonCodesEntity;
	}

	public Object validateAdjustmentCategory(String adjCat)
	{
		MdtCnfgAdjustmentCatEntity mdtAdjustmentCatEntity = (MdtCnfgAdjustmentCatEntity) genericDAO.findByNamedQuery("MdtCnfgAdjustmentCatEntity.findByAdjustmentCategory", "adjustmentCategory",adjCat);
		return mdtAdjustmentCatEntity;
	}

	@Override
	public Object validateMandateRejectReasonCode(String rejectReasonCode) {
		MdtCnfgRejectReasonCodesEntity mdtCnfgRejectReasonCodesEntity =(MdtCnfgRejectReasonCodesEntity) genericDAO.findByNamedQuery("MdtCnfgRejectReasonCodesEntity.findByRejectReasonCode","rejectReasonCode",rejectReasonCode);
		return mdtCnfgRejectReasonCodesEntity;
	}

	@Override
	public Object validateExternalStatusReasonCode(String statusReasonCode) 
	{
		MdtCnfgReasonCodesEntity mdtCnfgReasonCodesEntity =(MdtCnfgReasonCodesEntity) genericDAO.findByNamedQuery("MdtCnfgReasonCodesEntity.findByReasonCode","reasonCode",statusReasonCode);
		return mdtCnfgReasonCodesEntity;
	}

	@Override
	public Object validateErrorCodes(String errorCode) 
	{
		MdtCnfgErrorCodesEntity mdtCnfgErrorCodesEntity =(MdtCnfgErrorCodesEntity) genericDAO.findByNamedQuery("MdtCnfgErrorCodesEntity.findByErrorCode","errorCode",errorCode);
		return mdtCnfgErrorCodesEntity;
	}

	@Override
	public boolean saveMdtOpsMndtCount(Object obj) {
		boolean saved = false;
		try
		{
			log.debug("In the MdtOpsMndtCountEntity Persistence Layer ... ");
			if(obj instanceof MdtAcOpsMndtCountEntity)
			{
				MdtAcOpsMndtCountEntity localEntity = (MdtAcOpsMndtCountEntity) obj;
				log.debug("Writing to MdtOpsMndtCountEntity table.... "+localEntity);
				genericDAO.save(localEntity);
				saved = true;
			}
			else
				saved = false;
		}
		catch(Exception e)
		{
			log.error("Error on MdtOpsMndtCountEntity"+e.getMessage());
			e.printStackTrace();
			saved = false;
		}

		return saved;
	}

	@Override
	public Object validateAccountType(String accountType)
	{
		MdtCnfgAccountTypeEntity mdtCnfgAccountTypeEntity = (MdtCnfgAccountTypeEntity) genericDAO.findByNamedQuery("MdtCnfgAccountTypeEntity.findByAccountTypeDescription", "accountTypeDescription",accountType);	
		return mdtCnfgAccountTypeEntity;
	}

	@Override
	public Object validateAdjCategory(String adjCategory)
	{
		MdtCnfgAdjustmentCatEntity mdtCnfgAdjustmentCatEntity = (MdtCnfgAdjustmentCatEntity) genericDAO.findByNamedQuery("MdtCnfgAdjustmentCatEntity.findByAdjustmentCategory", "adjustmentCategory",adjCategory);	
		return mdtCnfgAdjustmentCatEntity;
	}

	@Override
	public Object validateAuthType(String authType)
	{
		MdtCnfgAuthTypeEntity mdtCnfgAuthTypeEntity = (MdtCnfgAuthTypeEntity) genericDAO.findByNamedQuery("MdtCnfgAuthTypeEntity.findByAuthType", "authType",authType);	
		return mdtCnfgAuthTypeEntity;
	}

	@Override
	public Object findByValidationRuleNr(String valRuleNr)
	{
		MdtCnfgValRuleEntity mdtCnfgValRuleEntity = null;
		try
		{
			mdtCnfgValRuleEntity = (MdtCnfgValRuleEntity) genericDAO.findByNamedQuery("MdtCnfgValRuleEntity.findByRuleNr", "ruleNr",valRuleNr);
		}
		catch(ObjectNotFoundException onfe)
		{
			log.error("No Object Found: "+ onfe.getMessage());
		}
		catch(Exception e)
		{
			log.error("Error on findByValidationRuleNr: "+ e.getMessage());
		}

		return mdtCnfgValRuleEntity;
	}

	public  List<?>  retrieveLocalInstrument(String localInstrCode)
	{
		log.debug("localInstrCode.in valBean: "+ localInstrCode); 
		List<MdtCnfgLocalInstrCodesEntity> mdtCnfgLocalInstruEntityList = (List<MdtCnfgLocalInstrCodesEntity>) genericDAO.findByNamedQuery("MdtCnfgLocalInstrCodesEntity.findByLocalInstrumentCode","localInstrumentCode", localInstrCode);
		log.debug("******************************mdtAcOpsMndtMsgList.in valBean: "+ mdtCnfgLocalInstruEntityList);
		return mdtCnfgLocalInstruEntityList;
	}

	@Override
	public Object validateAmendReasonCode(String amendReason) 
	{
		MdtCnfgAmendmentCodesEntity mdtCnfgAmendmentCodesEntity =(MdtCnfgAmendmentCodesEntity) genericDAO.findByNamedQuery("MdtCnfgAmendmentCodesEntity.findByAmendmentCodes","amendmentCodes",amendReason);
		return mdtCnfgAmendmentCodesEntity;
	}

	public Object validateSysCisBankDetails(String memberNo)
	{
		SysCisBankEntity sysCisBankEntity = (SysCisBankEntity) genericDAO.findByNamedQuery("SysCisBankEntity.findByMemberNo","memberNo", memberNo);
		return sysCisBankEntity;
	}

	public BigDecimal saveOpsConfHdrs(Object obj)
	{
		BigDecimal sysSeqNo = BigDecimal.ZERO;
		try
		{
			if(obj instanceof MdtAcOpsConfHdrsEntity)
			{
				MdtAcOpsConfHdrsEntity localEntity = (MdtAcOpsConfHdrsEntity) obj;
				log.debug("localEntity=====>>>>: "+ localEntity);
				sysSeqNo = genericDAO.saveReturnId(localEntity);
				//genericDAO.save(localEntity);
			}
		}
		catch(Exception e)
		{
			log.error("Error on saveOpsConfHdrs: "+ e.getMessage());
			e.printStackTrace();
		}
		return sysSeqNo;
	}

	public boolean saveConfDetails(List<?> confDetailsList)
	{
		boolean saved = false;
		try
		{
			log.debug("In the saveConfDetails Persistence Layer ... ");
			if(confDetailsList.size() > 0)
			{
				for (Object obj : confDetailsList) 
				{
					if(obj instanceof MdtAcOpsConfDetailsEntity)
					{

						MdtAcOpsConfDetailsEntity localEntity = (MdtAcOpsConfDetailsEntity) obj;
						log.debug("Writing to MdtAcOpsConfDetailsEntity table.... "+localEntity);
						genericDAO.save(localEntity);
						saved = true;
					}
				}			
			}
			else
				saved = false;
		}
		catch(Exception e)
		{
			log.error("Error on saveConfDetails"+e.getMessage());
			e.printStackTrace();
			saved = false;
		}
		return saved;
	}

	@Override
	public boolean createArcConfStatusDetails(Object obj)
	{
		if(obj instanceof MdtAcArcConfDetailsEntity)
		{
			MdtAcArcConfDetailsEntity mdtAcArcConfDetailsEntity = (MdtAcArcConfDetailsEntity) obj;
			try
			{
				genericDAO.save(mdtAcArcConfDetailsEntity);
				return true;
			}
			catch(Exception ex)
			{
				log.error("Error on createArcConfStatusDetails: "+ex.getMessage());
				return false;
			}
		}
		else
			return false;
	}

	@Override
	public boolean deleteAcOpsConfStatusDetails(Object obj) {
		if(obj instanceof MdtAcOpsConfDetailsEntity)
		{
			MdtAcOpsConfDetailsEntity mdtAcOpsConfDetailsEntity = (MdtAcOpsConfDetailsEntity) obj;
			try
			{

				genericDAO.delete(mdtAcOpsConfDetailsEntity);
				log.debug("**************Deleting Ac Ops Conf Status Details***************");
				return true;
			}
			catch(Exception ex)
			{
				log.error("Error on deleteAcOpsConfStatusDetails: "+ex.getMessage());
				return false;
			}
		}
		else
			return false;
	}

	@Override
	public boolean createArcConfHdrsEntity(Object obj) {
		if(obj instanceof MdtAcArcConfHdrsEntity)
		{
			MdtAcArcConfHdrsEntity mdtAcArcConfHdrsEntity = (MdtAcArcConfHdrsEntity) obj;
			try
			{
				genericDAO.save(mdtAcArcConfHdrsEntity);
				return true;
			}
			catch(Exception ex)
			{
				log.error("Error on createArcConfHdrsEntity: "+ex.getMessage());
				return false;
			}
		}
		else
			return false;
	}

	@Override
	public boolean deleteAcOpsConfStatusHdrs(Object obj) {
		if(obj instanceof MdtAcOpsConfHdrsEntity)
		{
			MdtAcOpsConfHdrsEntity mdtAcOpsConfHdrsEntity = (MdtAcOpsConfHdrsEntity) obj;
			try
			{

				genericDAO.delete(mdtAcOpsConfHdrsEntity);
				log.debug("**************Deleting Ac Ops Conf Hdrs***************");
				return true;
			}
			catch(Exception ex)
			{
				log.error("Error on deleteAcOpsConfStatusHdrs: "+ex.getMessage());
				return false;
			}
		}
		else
			return false;
	}

	@Override
	public boolean createArArcMndtCout(Object obj) {
		if(obj instanceof MdtAcArcMndtCountEntity)
		{
			MdtAcArcMndtCountEntity mdtAcArcMndtCountEntity = (MdtAcArcMndtCountEntity) obj;
			try
			{
				log.debug("Saving mdtAcArcMndtCountEntity");
				genericDAO.save(mdtAcArcMndtCountEntity);
				return true;
			}
			catch(Exception ex)
			{
				log.error("Error on createArArcMndtCout: "+ex.getMessage());
				return false;
			}
		}
		else
			return false;
	}

	@Override
	public boolean deleteAcOpsMndtCount(Object obj) 
	{
		if(obj instanceof MdtAcOpsMndtCountEntity)
		{
			MdtAcOpsMndtCountEntity mdtAcOpsMndtCountEntity = (MdtAcOpsMndtCountEntity) obj;
			try
			{
				genericDAO.delete(mdtAcOpsMndtCountEntity);
				log.debug("**************Deleting MDT_AC_OPS_MNDT_COUNT***************");
				return true;
			}
			catch(Exception ex)
			{
				log.debug("Error on deleteAcOpsMndtCount: "+ex.getMessage());
				return false;
			}
		}
		else
			return false;
	}

	public boolean createArchiveDailyBilling(Object obj)
	{
		if(obj instanceof MdtAcArcDailyBillingEntity)
		{
			MdtAcArcDailyBillingEntity mdtAcArcDailyBillingEntity = (MdtAcArcDailyBillingEntity) obj;
			try
			{
				genericDAO.save(mdtAcArcDailyBillingEntity);
				return true;
			}
			catch(Exception ex)
			{
				log.error("Error on createArchiveDailyBilling: "+ex.getMessage());
				return false;
			}
		}
		else
			return false;
	}

	public boolean deleteOriginalACDailyBilling(Object obj)
	{
		if(obj instanceof MdtAcOpsDailyBillingEntity)
		{
			MdtAcOpsDailyBillingEntity mdtAcOpsDailyBillingEntity = (MdtAcOpsDailyBillingEntity) obj;
			try
			{

				genericDAO.delete(mdtAcOpsDailyBillingEntity);
				log.debug("**************Deleting original status Details***************");
				return true;
			}
			catch(Exception ex)
			{
				log.error("Error on deleteOriginalACDailyBilling: "+ex.getMessage());
				return false;
			}
		}
		else
			return false;
	}

	public Object validateCreditorBank(String creditorBank)
	{
		log.debug("creditorBank-->: "+creditorBank);
		SysCisBankEntity sysCisBankEntity = null;

		if(creditorBank != null && !creditorBank.isEmpty()) 
		{
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("memberNo", creditorBank);
			parameters.put("acCreditor", "Y");

			try
			{	
				sysCisBankEntity = (SysCisBankEntity) genericDAO.findByCriteria(SysCisBankEntity.class, parameters);
				log.debug("sysCisBankEntity ==> "+ sysCisBankEntity);
			} 
			catch (ObjectNotFoundException onfe) 
			{
				log.debug("No Object Exists on DB");
			} 
			catch (Exception e) 
			{
				log.error("Error on validateCreditorBank: "+ e.getMessage());
				e.printStackTrace();
			}			
		}
		return sysCisBankEntity; 
	}

	public Object validateDebtorBank(String debtorBank)
	{
		log.debug("debtorBank-->: "+debtorBank);
		SysCisBankEntity sysCisBankEntity = null;

		if(debtorBank != null && !debtorBank.isEmpty()) 
		{
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("memberNo", debtorBank);
			parameters.put("acDebtor", "Y");

			try
			{	
				sysCisBankEntity = (SysCisBankEntity) genericDAO.findByCriteria(SysCisBankEntity.class, parameters);
				log.debug("sysCisBankEntity ==> "+ sysCisBankEntity);
			} 
			catch (ObjectNotFoundException onfe) 
			{
				log.debug("No Object Exists on DB");
			} 
			catch (Exception e) 
			{
				log.error("Error on validateDebtorBank: "+ e.getMessage());
				e.printStackTrace();
			}			
		}
		return sysCisBankEntity; 
	}

	@Override
	public boolean createAcArcFileReg(Object obj) {
		if(obj instanceof MdtAcArcFileRegEntity)
		{
			MdtAcArcFileRegEntity mdtAcArcFileRegEntity = (MdtAcArcFileRegEntity) obj;
			try
			{
				log.debug("Saving mdtAcArcFileRegEntity");
				genericDAO.save(mdtAcArcFileRegEntity);
				return true;
			}
			catch(Exception ex)
			{
				log.error("Error on createAcArcFileReg: "+ex.getMessage());
				return false;
			}
		}
		else
			return false;
	}

	@Override
	public boolean deleteOpsFileReg(Object obj) {
		if(obj instanceof MdtOpsFileRegEntity)
		{
			MdtOpsFileRegEntity mdtOpsFileRegEntity = (MdtOpsFileRegEntity) obj;
			try
			{
				genericDAO.delete(mdtOpsFileRegEntity);
				log.debug("**************Deleting MDT_OPS_FILE_REG_ table***************");
				return true;
			}
			catch(Exception ex)
			{
				log.debug("Error on deleteOpsFileReg: "+ex.getMessage());
				return false;
			}
		}
		else
			return false;
	}

	public List<?> validatePacs002MsgId(String msgId)
	{
		List<MdtAcOpsConfHdrsEntity> mdtAcOpsConfHdrsList = null;
		try
		{
			mdtAcOpsConfHdrsList = (List<MdtAcOpsConfHdrsEntity>) genericDAO.findAllByNamedQuery("MdtAcOpsConfHdrsEntity.findByHdrMsgId", "hdrMsgId",msgId);
		}
		catch(Exception ex)
		{
			log.debug("Error on validatePacs002MsgId: "+ex.getMessage());
		}
		return mdtAcOpsConfHdrsList;
	}

	//Cacheable List
	public List<?> findAllOpsServices(){
		List<MdtOpsServicesEntity> opsServicesList = null;
		try{
			opsServicesList =  genericDAO.findAll(MdtOpsServicesEntity.class);
		}
		catch(Exception ex){
			log.debug("Error on findAllOpsServices: "+ex.getMessage());
		}
		return opsServicesList;
	}

	public List<?> findAllCisBanks(){
		List<SysCisBankEntity> sysCisBankList = null;
		try{
			sysCisBankList =  genericDAO.findAll(SysCisBankEntity.class);
		}
		catch(Exception ex){
			log.debug("Error on findAllCisBanks: "+ex.getMessage());
		}
		return sysCisBankList;
	}

	public List<?> findAllCisBranches(){
		List<SysCisBranchEntity> sysCisBranchList = null;
		try{
			sysCisBranchList =  genericDAO.findAll(SysCisBranchEntity.class);
		}
		catch(Exception ex){
			log.debug("Error on findAllCisBranches: "+ex.getMessage());
		}
		return sysCisBranchList;
	}

	public List<?> findAllConfigSequenceTypes(){
		List<MdtCnfgSequenceTypeEntity> cnfgSequenceTypeList = null;
		try{
			cnfgSequenceTypeList =  genericDAO.findAll(MdtCnfgSequenceTypeEntity.class);
		}
		catch(Exception ex){
			log.debug("Error on findAllConfigSequenceTypes: "+ex.getMessage());
		}
		return cnfgSequenceTypeList;
	}

	public List<?> findAllConfigFrequencyCodes(){
		List<MdtCnfgFrequencyCodesEntity> cnfgFrequencyCodesList = null;
		try{
			cnfgFrequencyCodesList =  genericDAO.findAll(MdtCnfgFrequencyCodesEntity.class);
		}
		catch(Exception ex){
			log.debug("Error on findAllConfigFrequencyCodes: "+ex.getMessage());
		}
		return cnfgFrequencyCodesList;
	}

	public List<?> findAllConfigAccountTypes(){
		List<MdtCnfgAccountTypeEntity> cnfgAccountTypeList = null;
		try{
			cnfgAccountTypeList =  genericDAO.findAll(MdtCnfgAccountTypeEntity.class);
		}
		catch(Exception ex){
			log.debug("Error on findAllConfigAccountTypes: "+ex.getMessage());
		}
		return cnfgAccountTypeList;
	}

	public List<?> findAllConfigAuthTypes(){
		List<MdtCnfgAuthTypeEntity> cnfgAuthTypesList = null;
		try{
			cnfgAuthTypesList =  genericDAO.findAll(MdtCnfgAuthTypeEntity.class);
		}
		catch(Exception ex){
			log.debug("Error on findAllConfigAuthTypes: "+ex.getMessage());
		}
		return cnfgAuthTypesList;
	}

	public List<?> findAllConfigAdjustmentCats(){
		List<MdtCnfgAdjustmentCatEntity> cnfgAdjustmentCatList = null;
		try{
			cnfgAdjustmentCatList =  genericDAO.findAll(MdtCnfgAdjustmentCatEntity.class);
		}
		catch(Exception ex){
			log.debug("Error on findAllConfigAdjustmentCats: "+ex.getMessage());
		}
		return cnfgAdjustmentCatList;
	}

	public List<?> findAllConfigDebitValueTypes(){
		List<MdtCnfgDebitValueTypeEntity> cnfgDebitValueTypesList = null;
		try{
			cnfgDebitValueTypesList =  genericDAO.findAll(MdtCnfgDebitValueTypeEntity.class);
		}
		catch(Exception ex){
			log.debug("Error on findAllConfigDebitValueTypes: "+ex.getMessage());
		}
		return cnfgDebitValueTypesList;
	}

	public List<?> findAllConfigReasonCodes(){
		List<MdtCnfgReasonCodesEntity> cnfgReasonCodesList = null;
		try{
			cnfgReasonCodesList =  genericDAO.findAll(MdtCnfgReasonCodesEntity.class);
		}
		catch(Exception ex){
			log.debug("Error on findAllConfigReasonCodes: "+ex.getMessage());
		}
		return cnfgReasonCodesList;
	}

	public List<?> findAllConfigAmendmentCodes(){
		List<MdtCnfgAmendmentCodesEntity> cnfgAmendmentCodesList = null;
		try{
			cnfgAmendmentCodesList =  genericDAO.findAll(MdtCnfgAmendmentCodesEntity.class);
		}
		catch(Exception ex){
			log.debug("Error on findAllConfigAmendmentCodes: "+ex.getMessage());
		}
		return cnfgAmendmentCodesList;
	}

	public List<?> findAllConfigCancellationCodes(){
		List<MdtCnfgCancellationCodesEntity> cnfgCancellationCodesList = null;
		try{
			cnfgCancellationCodesList =  genericDAO.findAll(MdtCnfgCancellationCodesEntity.class);
		}
		catch(Exception ex){
			log.debug("Error on findAllConfigCancellationCodes: "+ex.getMessage());
		}
		return cnfgCancellationCodesList;
	}

	public List<?> findAllConfigRejectReasonCodes(){
		List<MdtCnfgRejectReasonCodesEntity> cnfgRejectReasonCodesList = null;
		try{
			cnfgRejectReasonCodesList =  genericDAO.findAll(MdtCnfgRejectReasonCodesEntity.class);
		}
		catch(Exception ex){
			log.debug("Error on findAllConfigRejectReasonCodes: "+ex.getMessage());
		}
		return cnfgRejectReasonCodesList;
	}

	public List<?> findAllConfigErrorCodes(){
		List<MdtCnfgErrorCodesEntity> cnfgErrorCodesList = null;
		try{
			cnfgErrorCodesList =  genericDAO.findAll(MdtCnfgErrorCodesEntity.class);
		}
		catch(Exception ex){
			log.debug("Error on findAllConfigErrorCodes: "+ex.getMessage());
		}
		return cnfgErrorCodesList;
	}

	public Object retrieveAcOpsFileSizeLimits(String serviceName,String memberNo)
	{
		log.info("serviceName-->: "+serviceName);
		log.info("memberNo-->: "+memberNo);
		MdtAcOpsFileSizeLimitEntity mdtAcOpsFileSizeLimitEntity = new MdtAcOpsFileSizeLimitEntity();

		if(serviceName != null && !serviceName.isEmpty()  ||memberNo != null && !memberNo.isEmpty()) 
		{
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("mdtAcOpsFileSizeLimitPK.memberId", memberNo);
			parameters.put("mdtAcOpsFileSizeLimitPK.subService",serviceName);

			try
			{	
				mdtAcOpsFileSizeLimitEntity = (MdtAcOpsFileSizeLimitEntity) genericDAO.findByCriteria(MdtAcOpsFileSizeLimitEntity.class, parameters);
				log.info("mdtAcOpsFileSizeLimitEntity ==> "+ mdtAcOpsFileSizeLimitEntity);
			} 
			catch (ObjectNotFoundException onfe) 
			{
				log.debug("No Object Exists on DB");
			} 
			catch (Exception e) 
			{
				log.error("Error on retrieveAcOpsFileSizeLimits: "+ e.getMessage());
				e.printStackTrace();
			}			
		}
		return mdtAcOpsFileSizeLimitEntity; 
	}
	
	
	public List<?> findAllFileSizeLimit(){
		List<MdtAcOpsFileSizeLimitEntity> opsFileSizeLimitList = null;
		try{
			opsFileSizeLimitList =  genericDAO.findAll(MdtAcOpsFileSizeLimitEntity.class);
		}
		catch(Exception ex){
			log.debug("Error on findAllFileSizeLimit: "+ex.getMessage());
		}
		return opsFileSizeLimitList;
	}

	@Override
	public List<?> retrieveFileSizeLimit(String destInstId) {
		List<MdtAcOpsFileSizeLimitEntity> opsFileSizeLimitList = (List<MdtAcOpsFileSizeLimitEntity>) genericDAO.findAllByNamedQuery("MdtAcOpsFileSizeLimitEntity.findByMemberId", "memberId",destInstId);
		return opsFileSizeLimitList;
	}
	
	public List<?> retriveOutgoingService(String serviceId)
	{
		List<MdtAcOpsFileSizeLimitEntity> mdtAcOpsFileSizeLimitEntityList = null;
		try
		{
			mdtAcOpsFileSizeLimitEntityList = (List<MdtAcOpsFileSizeLimitEntity>) genericDAO.findAllByNamedQuery("MdtAcOpsFileSizeLimitEntity.findBySubService", "subService",serviceId);
		}
		catch(Exception ex)
		{
			log.debug("Error on retriveOutgoingService: "+ex.getMessage());
		}
		return mdtAcOpsFileSizeLimitEntityList;
	}

}

