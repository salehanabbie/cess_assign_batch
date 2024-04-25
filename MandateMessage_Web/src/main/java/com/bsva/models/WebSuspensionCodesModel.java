package com.bsva.models;

import java.io.Serializable;
import java.util.Date;

public class WebSuspensionCodesModel implements Serializable{

	private String suspensionCodes;
	private String suspensionCodesDescription; 
	private String activeInd;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	
	public WebSuspensionCodesModel(String suspensionCodes)
	{
		this.suspensionCodes = suspensionCodes;
	}
	public WebSuspensionCodesModel()
	{
	}
	public String getSuspensionCodes(){
		return suspensionCodes;
	}
	public void setSuspensionCodes(String suspensionCodes){
		this.suspensionCodes = suspensionCodes;
	}
	public String getSuspensionCodesDescription(){
		return suspensionCodesDescription;
	}
	public void setSuspensionCodesDescription(String suspensionCodesDescription){
		this.suspensionCodesDescription = suspensionCodesDescription;
	}
	public String getActiveInd(){
		return activeInd;
	}
	public void setActiveInd(String activeInd){
		this.activeInd = activeInd;
	}
	public String getCreatedBy(){
		return createdBy;
	}

	public void setCreatedBy(String createdBy){
		this.createdBy = createdBy;
	}
		
		public Date getCreatedDate(){
			return createdDate;
	}
		
		public void setCreatedDate(Date createdDate){
			this.createdDate = createdDate;
	}
		
		public String getModifiedBy(){
			return modifiedBy;
	}
		
		public void setModifiedBy(String modifiedBy){
			this.modifiedBy = modifiedBy;
	}
		
		public Date getModifiedDate(){
			return modifiedDate;
	}
		
		public void setModifiedDate(Date modifiedDate){
			this.modifiedDate = modifiedDate;
	}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((activeInd == null) ? 0 : activeInd.hashCode());
			result = prime * result
					+ ((createdBy == null) ? 0 : createdBy.hashCode());
			result = prime * result
					+ ((createdDate == null) ? 0 : createdDate.hashCode());
			result = prime * result
					+ ((modifiedBy == null) ? 0 : modifiedBy.hashCode());
			result = prime * result
					+ ((modifiedDate == null) ? 0 : modifiedDate.hashCode());
			result = prime
					* result
					+ ((suspensionCodes == null) ? 0 : suspensionCodes
							.hashCode());
			result = prime
					* result
					+ ((suspensionCodesDescription == null) ? 0
							: suspensionCodesDescription.hashCode());
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
			WebSuspensionCodesModel other = (WebSuspensionCodesModel) obj;
			if (activeInd == null) {
				if (other.activeInd != null)
					return false;
			} else if (!activeInd.equals(other.activeInd))
				return false;
			if (createdBy == null) {
				if (other.createdBy != null)
					return false;
			} else if (!createdBy.equals(other.createdBy))
				return false;
			if (createdDate == null) {
				if (other.createdDate != null)
					return false;
			} else if (!createdDate.equals(other.createdDate))
				return false;
			if (modifiedBy == null) {
				if (other.modifiedBy != null)
					return false;
			} else if (!modifiedBy.equals(other.modifiedBy))
				return false;
			if (modifiedDate == null) {
				if (other.modifiedDate != null)
					return false;
			} else if (!modifiedDate.equals(other.modifiedDate))
				return false;
			if (suspensionCodes == null) {
				if (other.suspensionCodes != null)
					return false;
			} else if (!suspensionCodes.equals(other.suspensionCodes))
				return false;
			if (suspensionCodesDescription == null) {
				if (other.suspensionCodesDescription != null)
					return false;
			} else if (!suspensionCodesDescription
					.equals(other.suspensionCodesDescription))
				return false;
			return true;
		}
		
		
		@Override
		public String toString() {
			return "WebSuspensionCodesModel [suspensionCodes="
					+ suspensionCodes + ", suspensionCodesDescription="
					+ suspensionCodesDescription + ", activeInd=" + activeInd
					+ ", createdBy=" + createdBy + ", createdDate="
					+ createdDate + ", modifiedBy=" + modifiedBy
					+ ", modifiedDate=" + modifiedDate + "]";
		}
		
	
		
		
}
