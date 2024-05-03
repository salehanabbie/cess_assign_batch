package com.bsva.commons.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

public class OpsFileRegModel implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    private String fileName;
    private String filepath;
    private String status;
    private String reason;
    private Date processDate;
    private String nameSpace;
    private String grpHdrMsgId;
    private String onlineInd;
    private String inOutInd;
    private String extractMsgId;
	private String service;
    
	public OpsFileRegModel() {
		
	}

	public OpsFileRegModel(String  fileName) {

		this.fileName = fileName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Date getProcessDate() {
		return processDate;
	}

	public void setProcessDate(Date processDate) {
		this.processDate = processDate;
	}

	public String getNameSpace() {
		return nameSpace;
	}

	public void setNameSpace(String nameSpace) {
		this.nameSpace = nameSpace;
	}

	public String getGrpHdrMsgId() {
		return grpHdrMsgId;
	}

	public void setGrpHdrMsgId(String grpHdrMsgId) {
		this.grpHdrMsgId = grpHdrMsgId;
	}

	public String getOnlineInd() {
		return onlineInd;
	}

	public void setOnlineInd(String onlineInd) {
		this.onlineInd = onlineInd;
	}

	public String getInOutInd() {
		return inOutInd;
	}

	public void setInOutInd(String inOutInd) {
		this.inOutInd = inOutInd;
	}

	public String getExtractMsgId() {
		return extractMsgId;
	}

	public void setExtractMsgId(String extractMsgId) {
		this.extractMsgId = extractMsgId;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		OpsFileRegModel that = (OpsFileRegModel) o;
		return Objects.equals(fileName, that.fileName) &&
				Objects.equals(filepath, that.filepath) &&
				Objects.equals(status, that.status) &&
				Objects.equals(reason, that.reason) &&
				Objects.equals(processDate, that.processDate) &&
				Objects.equals(nameSpace, that.nameSpace) &&
				Objects.equals(grpHdrMsgId, that.grpHdrMsgId) &&
				Objects.equals(onlineInd, that.onlineInd) &&
				Objects.equals(inOutInd, that.inOutInd) &&
				Objects.equals(extractMsgId, that.extractMsgId) &&
				Objects.equals(service, that.service);
	}

	@Override
	public int hashCode() {
		return Objects.hash(fileName, filepath, status, reason, processDate, nameSpace, grpHdrMsgId,
				onlineInd, inOutInd, extractMsgId, service);
	}

	@Override
	public String toString() {
		return "OpsFileRegModel{" +
				"fileName='" + fileName + '\'' +
				", filepath='" + filepath + '\'' +
				", status='" + status + '\'' +
				", reason='" + reason + '\'' +
				", processDate=" + processDate +
				", nameSpace='" + nameSpace + '\'' +
				", grpHdrMsgId='" + grpHdrMsgId + '\'' +
				", onlineInd='" + onlineInd + '\'' +
				", inOutInd='" + inOutInd + '\'' +
				", extractMsgId='" + extractMsgId + '\'' +
				", service='" + service + '\'' +
				'}';
	}
}
