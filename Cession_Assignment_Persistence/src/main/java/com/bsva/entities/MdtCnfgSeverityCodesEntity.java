package com.bsva.entities;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

	/**
	 *
	 * @author ElelwaniR
	 */
	@Entity
	@Table(name = "MDT_CNFG_SEVERITY_CODES",schema = "MANOWNER")
	@XmlRootElement
	@NamedQueries({
	    @NamedQuery(name = "MdtCnfgSeverityCodesEntity.findAll", query = "SELECT m FROM MdtCnfgSeverityCodesEntity m"),
	    @NamedQuery(name = "MdtCnfgSeverityCodesEntity.findBySeverityCode", query = "SELECT m FROM MdtCnfgSeverityCodesEntity m WHERE m.severityCode = :severityCode"),
	    @NamedQuery(name = "MdtCnfgSeverityCodesEntity.findBySeverityCodeDesc", query = "SELECT m FROM MdtCnfgSeverityCodesEntity m WHERE m.severityCodeDesc = :severityCodeDesc"),
	    @NamedQuery(name = "MdtCnfgSeverityCodesEntity.findByActiveInd", query = "SELECT m FROM MdtCnfgSeverityCodesEntity m WHERE m.activeInd = :activeInd"),
	    @NamedQuery(name = "MdtCnfgSeverityCodesEntity.findByCreatedBy", query = "SELECT m FROM MdtCnfgSeverityCodesEntity m WHERE m.createdBy = :createdBy"),
	    @NamedQuery(name = "MdtCnfgSeverityCodesEntity.findByCreatedDate", query = "SELECT m FROM MdtCnfgSeverityCodesEntity m WHERE m.createdDate = :createdDate"),
	    @NamedQuery(name = "MdtCnfgSeverityCodesEntity.findByModifiedBy", query = "SELECT m FROM MdtCnfgSeverityCodesEntity m WHERE m.modifiedBy = :modifiedBy"),
	    @NamedQuery(name = "MdtCnfgSeverityCodesEntity.findByModifiedDate", query = "SELECT m FROM MdtCnfgSeverityCodesEntity m WHERE m.modifiedDate = :modifiedDate")})
	public class MdtCnfgSeverityCodesEntity implements Serializable {
	    private static final long serialVersionUID = 1L;
	    @Id
	    @Basic(optional = false)
	    @NotNull
	    @Column(name = "SEVERITY_CODE")
	    private Short severityCode;
	    @Size(max = 50)
	    @Column(name = "SEVERITY_CODE_DESC")
	    private String severityCodeDesc;
	    @Size(max = 1)
	    @Column(name = "ACTIVE_IND")
	    private String activeInd;
	    @Size(max = 25)
	    @Column(name = "CREATED_BY")
	    private String createdBy;
	    @Column(name = "CREATED_DATE")
	    @Temporal(TemporalType.TIMESTAMP)
	    private Date createdDate;
	    @Size(max = 25)
	    @Column(name = "MODIFIED_BY")
	    private String modifiedBy;
	    @Column(name = "MODIFIED_DATE")
	    @Temporal(TemporalType.TIMESTAMP)
	    private Date modifiedDate;

	    public MdtCnfgSeverityCodesEntity() {
	    }

	    public MdtCnfgSeverityCodesEntity(Short severityCode) {
	        this.severityCode = severityCode;
	    }

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
			MdtCnfgSeverityCodesEntity other = (MdtCnfgSeverityCodesEntity) obj;
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
			return "MdtCnfgSeverityCodesEntity [severityCode=" + severityCode
					+ ", severityCodeDesc=" + severityCodeDesc + ", activeInd="
					+ activeInd + ", createdBy=" + createdBy + ", createdDate="
					+ createdDate + ", modifiedBy=" + modifiedBy
					+ ", modifiedDate=" + modifiedDate + "]";
		}

		
	   
	    
	}

