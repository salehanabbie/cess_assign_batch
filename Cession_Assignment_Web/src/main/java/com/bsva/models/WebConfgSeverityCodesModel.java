package com.bsva.models;

import java.io.Serializable;
import java.util.Date;



public class WebConfgSeverityCodesModel implements Serializable {
	
	
	    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

		private Short severityCode;
	 
	    private String severityCodeDesc;
	    
	    private String action;
	    
	    private String createdBy;
	   
	    private Date createdDate;
	
	    private String modifiedBy;
	  
	    private Date modifiedDate;

		public Short getSeverityCode() {
			return severityCode;
		}

		public void setSeverityCode(Short severityCode) {
			this.severityCode = severityCode;
		}

		public String getSeverityCodeDesc() {
			return severityCodeDesc;
		}

		public void setSeverityCodeDesc(String severityCodeDesc) {
			this.severityCodeDesc = severityCodeDesc;
		}

		public String getAction() {
			return action;
		}

		public void setAction(String action) {
			this.action = action;
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
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((action == null) ? 0 : action.hashCode());
			result = prime * result
					+ ((createdBy == null) ? 0 : createdBy.hashCode());
			result = prime * result
					+ ((createdDate == null) ? 0 : createdDate.hashCode());
			result = prime * result
					+ ((modifiedBy == null) ? 0 : modifiedBy.hashCode());
			result = prime * result
					+ ((modifiedDate == null) ? 0 : modifiedDate.hashCode());
			result = prime * result
					+ ((severityCode == null) ? 0 : severityCode.hashCode());
			result = prime
					* result
					+ ((severityCodeDesc == null) ? 0 : severityCodeDesc
							.hashCode());
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
			WebConfgSeverityCodesModel other = (WebConfgSeverityCodesModel) obj;
			if (action == null) {
				if (other.action != null)
					return false;
			} else if (!action.equals(other.action))
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
			if (severityCode == null) {
				if (other.severityCode != null)
					return false;
			} else if (!severityCode.equals(other.severityCode))
				return false;
			if (severityCodeDesc == null) {
				if (other.severityCodeDesc != null)
					return false;
			} else if (!severityCodeDesc.equals(other.severityCodeDesc))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return severityCodeDesc; 
		}
	    
	    

}
