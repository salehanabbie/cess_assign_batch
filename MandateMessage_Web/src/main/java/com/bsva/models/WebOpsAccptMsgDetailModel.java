package com.bsva.models;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author DimakatsoN
 *
 */

public class WebOpsAccptMsgDetailModel implements Serializable {


    private String accptGrpHdrMsgId;
   
    private String origMessageId;
   
    private String msgNameId;
   
    private Date origCreateDate;
  
    private String accepted;
   
    private String rejectReasonCode;
    
    private String addRejectRsnInf;
    
    private String origMandateId;
    
    private String contents;
    
    private String manReqId;
    
    private String recordType;

    private String fileName;
    
    
	public WebOpsAccptMsgDetailModel() {
	
	}

	public String getAccptGrpHdrMsgId() {
		return accptGrpHdrMsgId;
	}

	public void setAccptGrpHdrMsgId(String accptGrpHdrMsgId) {
		this.accptGrpHdrMsgId = accptGrpHdrMsgId;
	}

	public String getOrigMessageId() {
		return origMessageId;
	}

	public void setOrigMessageId(String origMessageId) {
		this.origMessageId = origMessageId;
	}

	public String getMsgNameId() {
		return msgNameId;
	}

	public void setMsgNameId(String msgNameId) {
		this.msgNameId = msgNameId;
	}

	public Date getOrigCreateDate() {
		return origCreateDate;
	}

	public void setOrigCreateDate(Date origCreateDate) {
		this.origCreateDate = origCreateDate;
	}

	public String getAccepted() {
		return accepted;
	}

	public void setAccepted(String accepted) {
		this.accepted = accepted;
	}

	public String getRejectReasonCode() {
		return rejectReasonCode;
	}

	public void setRejectReasonCode(String rejectReasonCode) {
		this.rejectReasonCode = rejectReasonCode;
	}

	public String getAddRejectRsnInf() {
		return addRejectRsnInf;
	}

	public void setAddRejectRsnInf(String addRejectRsnInf) {
		this.addRejectRsnInf = addRejectRsnInf;
	}

	public String getOrigMandateId() {
		return origMandateId;
	}

	public void setOrigMandateId(String origMandateId) {
		this.origMandateId = origMandateId;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}
	
	public String getManReqId() {
		return manReqId;
	}

	public void setManReqId(String manReqId) {
		this.manReqId = manReqId;
	}

	public String getRecordType() {
		return recordType;
	}

	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}
	
		public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((accepted == null) ? 0 : accepted.hashCode());
		result = prime
				* result
				+ ((accptGrpHdrMsgId == null) ? 0 : accptGrpHdrMsgId.hashCode());
		result = prime * result
				+ ((addRejectRsnInf == null) ? 0 : addRejectRsnInf.hashCode());
		result = prime * result
				+ ((contents == null) ? 0 : contents.hashCode());
		result = prime * result
				+ ((fileName == null) ? 0 : fileName.hashCode());
		result = prime * result
				+ ((manReqId == null) ? 0 : manReqId.hashCode());
		result = prime * result
				+ ((msgNameId == null) ? 0 : msgNameId.hashCode());
		result = prime * result
				+ ((origCreateDate == null) ? 0 : origCreateDate.hashCode());
		result = prime * result
				+ ((origMandateId == null) ? 0 : origMandateId.hashCode());
		result = prime * result
				+ ((origMessageId == null) ? 0 : origMessageId.hashCode());
		result = prime * result
				+ ((recordType == null) ? 0 : recordType.hashCode());
		result = prime
				* result
				+ ((rejectReasonCode == null) ? 0 : rejectReasonCode.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WebOpsAccptMsgDetailModel other = (WebOpsAccptMsgDetailModel) obj;
		if (accepted == null) {
			if (other.accepted != null)
				return false;
		} else if (!accepted.equals(other.accepted))
			return false;
		if (accptGrpHdrMsgId == null) {
			if (other.accptGrpHdrMsgId != null)
				return false;
		} else if (!accptGrpHdrMsgId.equals(other.accptGrpHdrMsgId))
			return false;
		if (addRejectRsnInf == null) {
			if (other.addRejectRsnInf != null)
				return false;
		} else if (!addRejectRsnInf.equals(other.addRejectRsnInf))
			return false;
		if (contents == null) {
			if (other.contents != null)
				return false;
		} else if (!contents.equals(other.contents))
			return false;
		if (fileName == null) {
			if (other.fileName != null)
				return false;
		} else if (!fileName.equals(other.fileName))
			return false;
		if (manReqId == null) {
			if (other.manReqId != null)
				return false;
		} else if (!manReqId.equals(other.manReqId))
			return false;
		if (msgNameId == null) {
			if (other.msgNameId != null)
				return false;
		} else if (!msgNameId.equals(other.msgNameId))
			return false;
		if (origCreateDate == null) {
			if (other.origCreateDate != null)
				return false;
		} else if (!origCreateDate.equals(other.origCreateDate))
			return false;
		if (origMandateId == null) {
			if (other.origMandateId != null)
				return false;
		} else if (!origMandateId.equals(other.origMandateId))
			return false;
		if (origMessageId == null) {
			if (other.origMessageId != null)
				return false;
		} else if (!origMessageId.equals(other.origMessageId))
			return false;
		if (recordType == null) {
			if (other.recordType != null)
				return false;
		} else if (!recordType.equals(other.recordType))
			return false;
		if (rejectReasonCode == null) {
			if (other.rejectReasonCode != null)
				return false;
		} else if (!rejectReasonCode.equals(other.rejectReasonCode))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "WebOpsAccptMsgDetailModel [accptGrpHdrMsgId="
				+ accptGrpHdrMsgId + ", origMessageId=" + origMessageId
				+ ", msgNameId=" + msgNameId + ", origCreateDate="
				+ origCreateDate + ", accepted=" + accepted
				+ ", rejectReasonCode=" + rejectReasonCode
				+ ", addRejectRsnInf=" + addRejectRsnInf + ", origMandateId="
				+ origMandateId + ", contents=" + contents + ", manReqId="
				+ manReqId + ", recordType=" + recordType + ", fileName="
				+ fileName + "]";
	}

	
	
	
}
