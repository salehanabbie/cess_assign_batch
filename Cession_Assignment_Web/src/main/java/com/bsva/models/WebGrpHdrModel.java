package com.bsva.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * @author salehas
 *
 */
public class WebGrpHdrModel implements Serializable
{
	
	String msgId, authCode ;
	Date crDateTime;
	BigDecimal initPtySeqNo, instrPtyIdSeNo, instrdAgentPtySeqNo, id, version;
	
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	public Date getCrDateTime() {
		return crDateTime;
	}
	public void setCrDateTime(Date crDateTime) {
		this.crDateTime = crDateTime;
	}
	public BigDecimal getInitPtySeqNo() {
		return initPtySeqNo;
	}
	public void setInitPtySeqNo(BigDecimal initPtySeqNo) {
		this.initPtySeqNo = initPtySeqNo;
	}
	public BigDecimal getInstrPtyIdSeNo() {
		return instrPtyIdSeNo;
	}
	public void setInstrPtyIdSeNo(BigDecimal instrPtyIdSeNo) {
		this.instrPtyIdSeNo = instrPtyIdSeNo;
	}
	public BigDecimal getInstrdAgentPtySeqNo() {
		return instrdAgentPtySeqNo;
	}
	public void setInstrdAgentPtySeqNo(BigDecimal instrdAgentPtySeqNo) {
		this.instrdAgentPtySeqNo = instrdAgentPtySeqNo;
	}
	public String getAuthCode() {
		return authCode;
	}
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}
	public BigDecimal getId() {
		return id;
	}
	public void setId(BigDecimal id) {
		this.id = id;
	}
	public BigDecimal getVersion() {
		return version;
	}
	public void setVersion(BigDecimal version) {
		this.version = version;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((authCode == null) ? 0 : authCode.hashCode());
		result = prime * result
				+ ((crDateTime == null) ? 0 : crDateTime.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((initPtySeqNo == null) ? 0 : initPtySeqNo.hashCode());
		result = prime * result
				+ ((instrPtyIdSeNo == null) ? 0 : instrPtyIdSeNo.hashCode());
		result = prime
				* result
				+ ((instrdAgentPtySeqNo == null) ? 0 : instrdAgentPtySeqNo
						.hashCode());
		result = prime * result + ((msgId == null) ? 0 : msgId.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
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
		WebGrpHdrModel other = (WebGrpHdrModel) obj;
		if (authCode == null) {
			if (other.authCode != null)
				return false;
		} else if (!authCode.equals(other.authCode))
			return false;
		if (crDateTime == null) {
			if (other.crDateTime != null)
				return false;
		} else if (!crDateTime.equals(other.crDateTime))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (initPtySeqNo == null) {
			if (other.initPtySeqNo != null)
				return false;
		} else if (!initPtySeqNo.equals(other.initPtySeqNo))
			return false;
		if (instrPtyIdSeNo == null) {
			if (other.instrPtyIdSeNo != null)
				return false;
		} else if (!instrPtyIdSeNo.equals(other.instrPtyIdSeNo))
			return false;
		if (instrdAgentPtySeqNo == null) {
			if (other.instrdAgentPtySeqNo != null)
				return false;
		} else if (!instrdAgentPtySeqNo.equals(other.instrdAgentPtySeqNo))
			return false;
		if (msgId == null) {
			if (other.msgId != null)
				return false;
		} else if (!msgId.equals(other.msgId))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "WebGrpHdrModel [msgId=" + msgId + ", authCode=" + authCode
				+ ", crDateTime=" + crDateTime + ", initPtySeqNo="
				+ initPtySeqNo + ", instrPtyIdSeNo=" + instrPtyIdSeNo
				+ ", instrdAgentPtySeqNo=" + instrdAgentPtySeqNo + ", id=" + id
				+ ", version=" + version + "]";
	}
	
	
	
}
