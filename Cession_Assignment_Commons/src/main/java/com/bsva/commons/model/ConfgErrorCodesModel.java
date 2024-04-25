package com.bsva.commons.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * 
 * @author DimakatsoN
 *
 */

	public class ConfgErrorCodesModel implements Serializable 
	{

	
		private static final long serialVersionUID = 1L;
		private String errorCode, errorDescription, activeInd;
		private BigInteger severity;
		private String createdBy;
		private Date createdDate;
		private String modifiedBy;
		private Date modifiedDate;

		private BigDecimal errorCodeCount;

		public ConfgErrorCodesModel(String errorCode)
		{
			this.errorCode = errorCode;
		}


		public ConfgErrorCodesModel()
		{

		}

		public String getErrorCode() {
			return errorCode;
		}

		public void setErrorCode(String errorCode) {
			this.errorCode = errorCode;
		}

		public String getErrorDescription() {
			return errorDescription;
		}

		public void setErrorDescription(String errorDescription) {
			this.errorDescription = errorDescription;
		}

		public String getActiveInd() {
			return activeInd;
		}

		public void setActiveInd(String activeInd) {
			this.activeInd = activeInd;
		}

		public BigInteger getSeverity() {
			return severity;
		}

		public void setSeverity(BigInteger severity) {
			this.severity = severity;
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


		public BigDecimal getErrorCodeCount() {
			return errorCodeCount;
		}


		public void setErrorCodeCount(BigDecimal errorCodeCount) {
			this.errorCodeCount = errorCodeCount;
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
					+ ((errorCode == null) ? 0 : errorCode.hashCode());
			result = prime
					* result
					+ ((errorCodeCount == null) ? 0 : errorCodeCount.hashCode());
			result = prime
					* result
					+ ((errorDescription == null) ? 0 : errorDescription
							.hashCode());
			result = prime * result
					+ ((modifiedBy == null) ? 0 : modifiedBy.hashCode());
			result = prime * result
					+ ((modifiedDate == null) ? 0 : modifiedDate.hashCode());
			result = prime * result
					+ ((severity == null) ? 0 : severity.hashCode());
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
			ConfgErrorCodesModel other = (ConfgErrorCodesModel) obj;
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
			if (errorCode == null) {
				if (other.errorCode != null)
					return false;
			} else if (!errorCode.equals(other.errorCode))
				return false;
			if (errorCodeCount == null) {
				if (other.errorCodeCount != null)
					return false;
			} else if (!errorCodeCount.equals(other.errorCodeCount))
				return false;
			if (errorDescription == null) {
				if (other.errorDescription != null)
					return false;
			} else if (!errorDescription.equals(other.errorDescription))
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
			if (severity == null) {
				if (other.severity != null)
					return false;
			} else if (!severity.equals(other.severity))
				return false;
			return true;
		}


		@Override
		public String toString() {
			return "ConfgErrorCodesModel [errorCode=" + errorCode
					+ ", errorDescription=" + errorDescription + ", activeInd="
					+ activeInd + ", severity=" + severity + ", createdBy="
					+ createdBy + ", createdDate=" + createdDate
					+ ", modifiedBy=" + modifiedBy + ", modifiedDate="
					+ modifiedDate + ", errorCodeCount=" + errorCodeCount + "]";
		}


		
}
