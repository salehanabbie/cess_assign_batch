package com.bsva.businessLogic;

import java.io.Serializable;

import com.bsva.commons.model.OpsStatusDetailsModel;
import com.bsva.commons.model.OpsStatusHdrsModel;
import com.bsva.entities.CasOpsStatusDetailsEntity;
import com.bsva.entities.CasOpsStatusHdrsEntity;

/**
 * @author SalehaR
 *
 */
public class ViewFileStatusLogic implements Serializable{

	
	public OpsStatusHdrsModel translateEntityToCommonsModel(
			CasOpsStatusHdrsEntity opsStatusHdrsEntity)
	{
		OpsStatusHdrsModel opsStatusHdrsModel = new OpsStatusHdrsModel();

		opsStatusHdrsModel.setSystemSeqNo(opsStatusHdrsEntity.getSystemSeqNo());
		opsStatusHdrsModel.setHdrMsgId(opsStatusHdrsEntity.getHdrMsgId());
		opsStatusHdrsModel.setCreateDateTime(opsStatusHdrsEntity.getCreateDateTime());
		opsStatusHdrsModel.setInstdAgt(opsStatusHdrsEntity.getInstdAgt());
		opsStatusHdrsModel.setInstgAgt(opsStatusHdrsEntity.getInstgAgt());
		opsStatusHdrsModel.setOrgnlMsgId(opsStatusHdrsEntity.getOrgnlMsgId());
		opsStatusHdrsModel.setOrgnlMsgName(opsStatusHdrsEntity.getOrgnlMsgName());
		opsStatusHdrsModel.setOrgnlCntlSum(opsStatusHdrsEntity.getOrgnlCntlSum());
		opsStatusHdrsModel.setProcessStatus(opsStatusHdrsEntity.getProcessStatus());
		opsStatusHdrsModel.setGroupStatus(opsStatusHdrsEntity.getGroupStatus());
		opsStatusHdrsModel.setService(opsStatusHdrsEntity.getService());
		opsStatusHdrsModel.setVetRunNumber(opsStatusHdrsEntity.getVetRunNumber());
		opsStatusHdrsModel.setWorkunitRefNo(opsStatusHdrsEntity.getWorkunitRefNo());
		
		
		return opsStatusHdrsModel;
	}
	
	
	public OpsStatusDetailsModel translateOpsStatusDetEntityToCommonsModel (
        CasOpsStatusDetailsEntity opsStatusDetailsEntity)
	{
		OpsStatusDetailsModel opsStatusDetailsModel = new OpsStatusDetailsModel();
		
		
		opsStatusDetailsModel.setSystemSeqNo(opsStatusDetailsEntity .getSystemSeqNo());
		opsStatusDetailsModel.setStatusHdrSeqNo(opsStatusDetailsEntity.getStatusHdrSeqNo());
		opsStatusDetailsModel .setErrorCode(opsStatusDetailsEntity.getErrorCode());
		opsStatusDetailsModel.setTxnId(opsStatusDetailsEntity.getTxnId());
		opsStatusDetailsModel.setEndToEndId(opsStatusDetailsEntity.getEndToEndId());
		opsStatusDetailsModel.setTxnStatus(opsStatusDetailsEntity.getTxnStatus());
		opsStatusDetailsModel.setErrorType(opsStatusDetailsEntity.getErrorType());
		opsStatusDetailsModel.setRecordId(opsStatusDetailsEntity.getRecordId());
		opsStatusDetailsModel.setOrgnlTxnSeqNo(opsStatusDetailsEntity.getOrgnlTxnSeqNo());
		
		return opsStatusDetailsModel;
	}
	
	
}
