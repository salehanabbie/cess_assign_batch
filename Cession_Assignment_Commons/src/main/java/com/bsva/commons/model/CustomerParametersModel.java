package com.bsva.commons.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;


public class CustomerParametersModel implements Serializable {	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String casaAmdXsdNs;

	private String casaAccpXsdNs;

	private String activeInd;

	private String instId;
	
	private String casaStatusRepXsdNs;

	private String casaConfirmXsdNs;

	private Short processDay;

	private String createdBy;

	private Date createdDate;

	private String modifiedBy;

	private Date modifiedDate;

	public String getCasaAmdXsdNs() {
		return casaAmdXsdNs;
	}

	public void setCasaAmdXsdNs(String casaAmdXsdNs) {
		this.casaAmdXsdNs = casaAmdXsdNs;
	}

	public String getCasaAccpXsdNs() {
		return casaAccpXsdNs;
	}

	public void setCasaAccpXsdNs(String casaAccpXsdNs) {
		this.casaAccpXsdNs = casaAccpXsdNs;
	}

	public String getActiveInd() {
		return activeInd;
	}

	public void setActiveInd(String activeInd) {
		this.activeInd = activeInd;
	}

	public String getInstId() {
		return instId;
	}

	public void setInstId(String instId) {
		this.instId = instId;
	}

	public String getCasaStatusRepXsdNs() {
		return casaStatusRepXsdNs;
	}

	public void setCasaStatusRepXsdNs(String casaStatusRepXsdNs) {
		this.casaStatusRepXsdNs = casaStatusRepXsdNs;
	}

	public String getCasaConfirmXsdNs() {
		return casaConfirmXsdNs;
	}

	public void setCasaConfirmXsdNs(String casaConfirmXsdNs) {
		this.casaConfirmXsdNs = casaConfirmXsdNs;
	}

	public Short getProcessDay() {
		return processDay;
	}

	public void setProcessDay(Short processDay) {
		this.processDay = processDay;
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

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		CustomerParametersModel that = (CustomerParametersModel) o;
		return Objects.equals(casaAmdXsdNs, that.casaAmdXsdNs) &&
				Objects.equals(casaAccpXsdNs, that.casaAccpXsdNs) &&
				Objects.equals(activeInd, that.activeInd) &&
				Objects.equals(instId, that.instId) &&
				Objects.equals(casaStatusRepXsdNs, that.casaStatusRepXsdNs) &&
				Objects.equals(casaConfirmXsdNs, that.casaConfirmXsdNs) &&
				Objects.equals(processDay, that.processDay) &&
				Objects.equals(createdBy, that.createdBy) &&
				Objects.equals(createdDate, that.createdDate) &&
				Objects.equals(modifiedBy, that.modifiedBy) &&
				Objects.equals(modifiedDate, that.modifiedDate);
	}

	@Override
	public int hashCode() {
		return Objects.hash(casaAmdXsdNs, casaAccpXsdNs, activeInd, instId, casaStatusRepXsdNs,
				casaConfirmXsdNs, processDay, createdBy, createdDate, modifiedBy, modifiedDate);
	}

	@Override
	public String toString() {
		return "CustomerParametersModel{" +
				"casaAmdXsdNs='" + casaAmdXsdNs + '\'' +
				", casaAccpXsdNs='" + casaAccpXsdNs + '\'' +
				", activeInd='" + activeInd + '\'' +
				", instId='" + instId + '\'' +
				", casaStatusRepXsdNs='" + casaStatusRepXsdNs + '\'' +
				", casaConfirmXsdNs='" + casaConfirmXsdNs + '\'' +
				", processDay=" + processDay +
				", createdBy='" + createdBy + '\'' +
				", createdDate=" + createdDate +
				", modifiedBy='" + modifiedBy + '\'' +
				", modifiedDate=" + modifiedDate +
				'}';
	}
}
