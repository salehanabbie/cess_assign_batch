package com.bsva.models;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author NhlanhlaM
 *
 */

public class WebEntryClassesModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	  private String entryClass;	  
	    private String entryClassDesc;	    
	    private String activeInd;    
	    private String createdBy;    
	    private Date createdDate;   
	    private String modifiedBy;
	    private Date modifiedDate;
	    
	    
	    public WebEntryClassesModel (String entryClass)
	    {
	    	this.entryClass = entryClass;
	    }
	    
	    public WebEntryClassesModel()
	    {
	    	
	    }
	    
	    
		public String getEntryClass() {
			return entryClass;
		}
		public void setEntryClass(String entryClass) {
			this.entryClass = entryClass;
		}
		public String getEntryClassDesc() {
			return entryClassDesc;
		}
		public void setEntryClassDesc(String entryClassDesc) {
			this.entryClassDesc = entryClassDesc;
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
		public static long getSerialversionuid() {
			return serialVersionUID;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((activeInd == null) ? 0 : activeInd.hashCode());
			result = prime * result + ((createdBy == null) ? 0 : createdBy.hashCode());
			result = prime * result + ((createdDate == null) ? 0 : createdDate.hashCode());
			result = prime * result + ((entryClass == null) ? 0 : entryClass.hashCode());
			result = prime * result + ((entryClassDesc == null) ? 0 : entryClassDesc.hashCode());
			result = prime * result + ((modifiedBy == null) ? 0 : modifiedBy.hashCode());
			result = prime * result + ((modifiedDate == null) ? 0 : modifiedDate.hashCode());
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
			WebEntryClassesModel other = (WebEntryClassesModel) obj;
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
			if (entryClass == null) {
				if (other.entryClass != null)
					return false;
			} else if (!entryClass.equals(other.entryClass))
				return false;
			if (entryClassDesc == null) {
				if (other.entryClassDesc != null)
					return false;
			} else if (!entryClassDesc.equals(other.entryClassDesc))
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
			return true;
		}
		@Override
		public String toString() {
			return "WebMdtCnfgEntryClassesModel [entryClass=" + entryClass + ", entryClassDesc=" + entryClassDesc
					+ ", activeInd=" + activeInd + ", createdBy=" + createdBy + ", createdDate=" + createdDate
					+ ", modifiedBy=" + modifiedBy + ", modifiedDate=" + modifiedDate + "]";
		}    
	
	
}
