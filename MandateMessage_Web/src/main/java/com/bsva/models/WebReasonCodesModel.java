
package com.bsva.models;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author DimakatsoN
 *
 */

public class WebReasonCodesModel implements Serializable {
	private String reasonCode;
	private String reasonCodeDescription;
	private String activeInd;
	 private String createdBy;
	    private Date createdDate;
	    private String modifiedBy;
	    private Date modifiedDate;
	    private String name;

	public WebReasonCodesModel(String reasonCode) {
		this.reasonCode = reasonCode;
	}

	public WebReasonCodesModel() {

	}

	public String getReasonCode() {
		return reasonCode;
	}

	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}

	public String getReasonCodeDescription() {
		return reasonCodeDescription;
	}

	public void setReasonCodeDescription(String reasonCodeDescription) {
		this.reasonCodeDescription = reasonCodeDescription;
	}

	public String getActiveInd() {
		return activeInd;
	}

	public void setActiveInd(String activeInd) {
		this.activeInd = activeInd;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "WebReasonCodesModel [reasonCode=" + reasonCode
				+ ", reasonCodeDescription=" + reasonCodeDescription
				+ ", activeInd=" + activeInd + ", createdBy=" + createdBy
				+ ", createdDate=" + createdDate + ", modifiedBy=" + modifiedBy
				+ ", modifiedDate=" + modifiedDate + ", name=" + name + "]";
	}

	
	

}