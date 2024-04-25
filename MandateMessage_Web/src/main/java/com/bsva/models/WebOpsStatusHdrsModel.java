package com.bsva.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author SalehaR
 *
 */
public class WebOpsStatusHdrsModel implements Serializable
{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal systemSeqNo;
    private String hdrMsgId;
    private Date createDateTime;
    private String instdAgt;
    private String instgAgt;
    private String orgnlMsgId;
    private String orgnlMsgName;
	private Date orgnlCreateDateTime;
    private Long orgnlNoOfTxns;
    private BigDecimal orgnlCntlSum;
    private String service;
    private Long vetRunNumber;
    private String workunitRefNo;
    private String processStatus;
    private String groupStatus;
    
    
	public WebOpsStatusHdrsModel() {
		
	}
	public BigDecimal getSystemSeqNo() {
		return systemSeqNo;
	}
	public void setSystemSeqNo(BigDecimal systemSeqNo) {
		this.systemSeqNo = systemSeqNo;
	}
	public String getHdrMsgId() {
		return hdrMsgId;
	}
	public void setHdrMsgId(String hdrMsgId) {
		this.hdrMsgId = hdrMsgId;
	}
	public Date getCreateDateTime() {
		return createDateTime;
	}
	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}
	public String getInstdAgt() {
		return instdAgt;
	}
	public void setInstdAgt(String instdAgt) {
		this.instdAgt = instdAgt;
	}
	public String getInstgAgt() {
		return instgAgt;
	}
	public void setInstgAgt(String instgAgt) {
		this.instgAgt = instgAgt;
	}
	public String getOrgnlMsgId() {
		return orgnlMsgId;
	}
	public void setOrgnlMsgId(String orgnlMsgId) {
		this.orgnlMsgId = orgnlMsgId;
	}
	public String getOrgnlMsgName() {
		return orgnlMsgName;
	}
	public void setOrgnlMsgName(String orgnlMsgName) {
		this.orgnlMsgName = orgnlMsgName;
	}
	public Date getOrgnlCreateDateTime() {
		return orgnlCreateDateTime;
	}
	public void setOrgnlCreateDateTime(Date orgnlCreateDateTime) {
		this.orgnlCreateDateTime = orgnlCreateDateTime;
	}
	public Long getOrgnlNoOfTxns() {
		return orgnlNoOfTxns;
	}
	public void setOrgnlNoOfTxns(Long orgnlNoOfTxns) {
		this.orgnlNoOfTxns = orgnlNoOfTxns;
	}
	public BigDecimal getOrgnlCntlSum() {
		return orgnlCntlSum;
	}
	public void setOrgnlCntlSum(BigDecimal orgnlCntlSum) {
		this.orgnlCntlSum = orgnlCntlSum;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public Long getVetRunNumber() {
		return vetRunNumber;
	}
	public void setVetRunNumber(Long vetRunNumber) {
		this.vetRunNumber = vetRunNumber;
	}
	public String getWorkunitRefNo() {
		return workunitRefNo;
	}
	public void setWorkunitRefNo(String workunitRefNo) {
		this.workunitRefNo = workunitRefNo;
	}
	public String getProcessStatus() {
		return processStatus;
	}
	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}
	public String getGroupStatus() {
		return groupStatus;
	}
	public void setGroupStatus(String groupStatus) {
		this.groupStatus = groupStatus;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((createDateTime == null) ? 0 : createDateTime.hashCode());
		result = prime * result
				+ ((groupStatus == null) ? 0 : groupStatus.hashCode());
		result = prime * result
				+ ((hdrMsgId == null) ? 0 : hdrMsgId.hashCode());
		result = prime * result
				+ ((instdAgt == null) ? 0 : instdAgt.hashCode());
		result = prime * result
				+ ((instgAgt == null) ? 0 : instgAgt.hashCode());
		result = prime * result
				+ ((orgnlCntlSum == null) ? 0 : orgnlCntlSum.hashCode());
		result = prime
				* result
				+ ((orgnlCreateDateTime == null) ? 0 : orgnlCreateDateTime
						.hashCode());
		result = prime * result
				+ ((orgnlMsgId == null) ? 0 : orgnlMsgId.hashCode());
		result = prime * result
				+ ((orgnlMsgName == null) ? 0 : orgnlMsgName.hashCode());
		result = prime * result
				+ ((orgnlNoOfTxns == null) ? 0 : orgnlNoOfTxns.hashCode());
		result = prime * result
				+ ((processStatus == null) ? 0 : processStatus.hashCode());
		result = prime * result + ((service == null) ? 0 : service.hashCode());
		result = prime * result
				+ ((systemSeqNo == null) ? 0 : systemSeqNo.hashCode());
		result = prime * result
				+ ((vetRunNumber == null) ? 0 : vetRunNumber.hashCode());
		result = prime * result
				+ ((workunitRefNo == null) ? 0 : workunitRefNo.hashCode());
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
		WebOpsStatusHdrsModel other = (WebOpsStatusHdrsModel) obj;
		if (createDateTime == null) {
			if (other.createDateTime != null)
				return false;
		} else if (!createDateTime.equals(other.createDateTime))
			return false;
		if (groupStatus == null) {
			if (other.groupStatus != null)
				return false;
		} else if (!groupStatus.equals(other.groupStatus))
			return false;
		if (hdrMsgId == null) {
			if (other.hdrMsgId != null)
				return false;
		} else if (!hdrMsgId.equals(other.hdrMsgId))
			return false;
		if (instdAgt == null) {
			if (other.instdAgt != null)
				return false;
		} else if (!instdAgt.equals(other.instdAgt))
			return false;
		if (instgAgt == null) {
			if (other.instgAgt != null)
				return false;
		} else if (!instgAgt.equals(other.instgAgt))
			return false;
		if (orgnlCntlSum == null) {
			if (other.orgnlCntlSum != null)
				return false;
		} else if (!orgnlCntlSum.equals(other.orgnlCntlSum))
			return false;
		if (orgnlCreateDateTime == null) {
			if (other.orgnlCreateDateTime != null)
				return false;
		} else if (!orgnlCreateDateTime.equals(other.orgnlCreateDateTime))
			return false;
		if (orgnlMsgId == null) {
			if (other.orgnlMsgId != null)
				return false;
		} else if (!orgnlMsgId.equals(other.orgnlMsgId))
			return false;
		if (orgnlMsgName == null) {
			if (other.orgnlMsgName != null)
				return false;
		} else if (!orgnlMsgName.equals(other.orgnlMsgName))
			return false;
		if (orgnlNoOfTxns == null) {
			if (other.orgnlNoOfTxns != null)
				return false;
		} else if (!orgnlNoOfTxns.equals(other.orgnlNoOfTxns))
			return false;
		if (processStatus == null) {
			if (other.processStatus != null)
				return false;
		} else if (!processStatus.equals(other.processStatus))
			return false;
		if (service == null) {
			if (other.service != null)
				return false;
		} else if (!service.equals(other.service))
			return false;
		if (systemSeqNo == null) {
			if (other.systemSeqNo != null)
				return false;
		} else if (!systemSeqNo.equals(other.systemSeqNo))
			return false;
		if (vetRunNumber == null) {
			if (other.vetRunNumber != null)
				return false;
		} else if (!vetRunNumber.equals(other.vetRunNumber))
			return false;
		if (workunitRefNo == null) {
			if (other.workunitRefNo != null)
				return false;
		} else if (!workunitRefNo.equals(other.workunitRefNo))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "WebOpsStatusHdrsModel [systemSeqNo=" + systemSeqNo
				+ ", hdrMsgId=" + hdrMsgId + ", createDateTime="
				+ createDateTime + ", instdAgt=" + instdAgt + ", instgAgt="
				+ instgAgt + ", orgnlMsgId=" + orgnlMsgId + ", orgnlMsgName="
				+ orgnlMsgName + ", orgnlCreateDateTime=" + orgnlCreateDateTime
				+ ", orgnlNoOfTxns=" + orgnlNoOfTxns + ", orgnlCntlSum="
				+ orgnlCntlSum + ", service=" + service + ", vetRunNumber="
				+ vetRunNumber + ", workunitRefNo=" + workunitRefNo
				+ ", processStatus=" + processStatus + ", groupStatus="
				+ groupStatus + "]";
	}
    
    
    
    
	
}
