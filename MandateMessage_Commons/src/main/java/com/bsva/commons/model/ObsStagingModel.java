package com.bsva.commons.model;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ObsStagingModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String recordId;
	private Date actionDate;
	private String authInd;
	private String createdBy;
	private Date createdDate;
	private BigDecimal destination;
	private String modifiedBy;
	private Date modifiedDate;
	private BigDecimal originator;
	private String respBankAccName;
	private String respBankAccNo;
	private String respBankAccType;
	private BigDecimal respBankBranch;
	private String respBankInstitution;
	private String service;
	private String status;
	private String subService;
	private String systemName;
	private String trackingCode;
	private String trxnStatus;
	private String trxnType;
	
	
	public String getRecordId() {
		return recordId;
	}
	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}
	public Date getActionDate() {
		return actionDate;
	}
	public void setActionDate(Date actionDate) {
		this.actionDate = actionDate;
	}
	public String getAuthInd() {
		return authInd;
	}
	public void setAuthInd(String authInd) {
		this.authInd = authInd;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public BigDecimal getDestination() {
		return destination;
	}
	public void setDestination(BigDecimal destination) {
		this.destination = destination;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public BigDecimal getOriginator() {
		return originator;
	}
	public void setOriginator(BigDecimal originator) {
		this.originator = originator;
	}
	public String getRespBankAccName() {
		return respBankAccName;
	}
	public void setRespBankAccName(String respBankAccName) {
		this.respBankAccName = respBankAccName;
	}
	public String getRespBankAccNo() {
		return respBankAccNo;
	}
	public void setRespBankAccNo(String respBankAccNo) {
		this.respBankAccNo = respBankAccNo;
	}
	public String getRespBankAccType() {
		return respBankAccType;
	}
	public void setRespBankAccType(String respBankAccType) {
		this.respBankAccType = respBankAccType;
	}
	public BigDecimal getRespBankBranch() {
		return respBankBranch;
	}
	public void setRespBankBranch(BigDecimal respBankBranch) {
		this.respBankBranch = respBankBranch;
	}
	public String getRespBankInstitution() {
		return respBankInstitution;
	}
	public void setRespBankInstitution(String respBankInstitution) {
		this.respBankInstitution = respBankInstitution;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSubService() {
		return subService;
	}
	public void setSubService(String subService) {
		this.subService = subService;
	}
	public String getSystemName() {
		return systemName;
	}
	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}
	public String getTrackingCode() {
		return trackingCode;
	}
	public void setTrackingCode(String trackingCode) {
		this.trackingCode = trackingCode;
	}
	public String getTrxnStatus() {
		return trxnStatus;
	}
	public void setTrxnStatus(String trxnStatus) {
		this.trxnStatus = trxnStatus;
	}
	public String getTrxnType() {
		return trxnType;
	}
	public void setTrxnType(String trxnType) {
		this.trxnType = trxnType;
	}
	@Override
	public String toString() {
		return "ObsStagingModel [recordId=" + recordId + ", actionDate="
				+ actionDate + ", authInd=" + authInd + ", createdBy="
				+ createdBy + ", createdDate=" + createdDate + ", destination="
				+ destination + ", modifiedBy=" + modifiedBy
				+ ", modifiedDate=" + modifiedDate + ", originator="
				+ originator + ", respBankAccName=" + respBankAccName
				+ ", respBankAccNo=" + respBankAccNo + ", respBankAccType="
				+ respBankAccType + ", respBankBranch=" + respBankBranch
				+ ", respBankInstitution=" + respBankInstitution + ", service="
				+ service + ", status=" + status + ", subService=" + subService
				+ ", systemName=" + systemName + ", trackingCode="
				+ trackingCode + ", trxnStatus=" + trxnStatus + ", trxnType="
				+ trxnType + "]";
	}
	
	
}
