

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
@Table(name = "CAS_SYSCTRL_CUST_PARAM",schema = "CAMOWNER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CasSysctrlCustParamEntity.findAll", query = "SELECT m FROM CasSysctrlCustParamEntity m"),
    @NamedQuery(name = "CasSysctrlCustParamEntity.findByManAmdXsdNs", query = "SELECT m FROM CasSysctrlCustParamEntity m WHERE m.manAmdXsdNs = :manAmdXsdNs"),
    @NamedQuery(name = "CasSysctrlCustParamEntity.findByManCanXsdNs", query = "SELECT m FROM CasSysctrlCustParamEntity m WHERE m.manCanXsdNs = :manCanXsdNs"),
    @NamedQuery(name = "CasSysctrlCustParamEntity.findByManAccpXsdNs", query = "SELECT m FROM CasSysctrlCustParamEntity m WHERE m.manAccpXsdNs = :manAccpXsdNs"),
    @NamedQuery(name = "CasSysctrlCustParamEntity.findByActiveInd", query = "SELECT m FROM CasSysctrlCustParamEntity m WHERE m.activeInd = :activeInd"),
    @NamedQuery(name = "CasSysctrlCustParamEntity.findByInstId", query = "SELECT m FROM CasSysctrlCustParamEntity m WHERE m.instId = :instId"),
    @NamedQuery(name = "CasSysctrlCustParamEntity.findByManInitXsdNs", query = "SELECT m FROM CasSysctrlCustParamEntity m WHERE m.manInitXsdNs = :manInitXsdNs"),
    @NamedQuery(name = "CasSysctrlCustParamEntity.findByMdtReqIdReuseInd", query = "SELECT m FROM CasSysctrlCustParamEntity m WHERE m.mdtReqIdReuseInd = :mdtReqIdReuseInd"),
    @NamedQuery(name = "CasSysctrlCustParamEntity.findByMdteReqXsdNs", query = "SELECT m FROM CasSysctrlCustParamEntity m WHERE m.mdteReqXsdNs = :mdteReqXsdNs"),
    @NamedQuery(name = "CasSysctrlCustParamEntity.findByMdteRespXsdNs", query = "SELECT m FROM CasSysctrlCustParamEntity m WHERE m.mdteRespXsdNs = :mdteRespXsdNs"),
    @NamedQuery(name = "CasSysctrlCustParamEntity.findByManStatusRepXsdNs", query = "SELECT m FROM CasSysctrlCustParamEntity m WHERE m.manStatusRepXsdNs = :manStatusRepXsdNs"),
    @NamedQuery(name = "CasSysctrlCustParamEntity.findByManConfirmXsdNs", query = "SELECT m FROM CasSysctrlCustParamEntity m WHERE m.manConfirmXsdNs = :manConfirmXsdNs"),
    @NamedQuery(name = "CasSysctrlCustParamEntity.findByProcessDay", query = "SELECT m FROM CasSysctrlCustParamEntity m WHERE m.processDay = :processDay"),
    @NamedQuery(name = "CasSysctrlCustParamEntity.findByCisDwnldInd", query = "SELECT m FROM CasSysctrlCustParamEntity m WHERE m.cisDwnldInd = :cisDwnldInd"),
    @NamedQuery(name = "CasSysctrlCustParamEntity.findByCisDwnldDate", query = "SELECT m FROM CasSysctrlCustParamEntity m WHERE m.cisDwnldDate = :cisDwnldDate"),
    @NamedQuery(name = "CasSysctrlCustParamEntity.findByCreatedBy", query = "SELECT m FROM CasSysctrlCustParamEntity m WHERE m.createdBy = :createdBy"),
    @NamedQuery(name = "CasSysctrlCustParamEntity.findByCreatedDate", query = "SELECT m FROM CasSysctrlCustParamEntity m WHERE m.createdDate = :createdDate"),
    @NamedQuery(name = "CasSysctrlCustParamEntity.findByModifiedBy", query = "SELECT m FROM CasSysctrlCustParamEntity m WHERE m.modifiedBy = :modifiedBy"),
    @NamedQuery(name = "CasSysctrlCustParamEntity.findByModifiedDate", query = "SELECT m FROM CasSysctrlCustParamEntity m WHERE m.modifiedDate = :modifiedDate"),
    @NamedQuery(name = "CasSysctrlCustParamEntity.findByManSuspXsdNs", query = "SELECT m FROM CasSysctrlCustParamEntity m WHERE m.manSuspXsdNs = :manSuspXsdNs")})
public class CasSysctrlCustParamEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Size(max = 50)
    @Column(name = "MAN_AMD_XSD_NS")
    private String manAmdXsdNs;
    @Size(max = 50)
    @Column(name = "MAN_CAN_XSD_NS")
    private String manCanXsdNs;
    @Size(max = 50)
    @Column(name = "MAN_ACCP_XSD_NS")
    private String manAccpXsdNs;
    @Size(max = 1)
    @Column(name = "ACTIVE_IND")
    private String activeInd;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 11)
    @Column(name = "INST_ID")
    private String instId;
    @Size(max = 50)
    @Column(name = "MAN_INIT_XSD_NS")
    private String manInitXsdNs;
    @Size(max = 1)
    @Column(name = "MDT_REQ_ID_REUSE_IND")
    private String mdtReqIdReuseInd;
    @Size(max = 50)
    @Column(name = "MDTE_REQ_XSD_NS")
    private String mdteReqXsdNs;
    @Size(max = 50)
    @Column(name = "MDTE_RESP_XSD_NS")
    private String mdteRespXsdNs;
    @Size(max = 50)
    @Column(name = "MAN_STATUS_REP_XSD_NS")
    private String manStatusRepXsdNs;
    @Size(max = 50)
    @Column(name = "MAN_CONFIRM_XSD_NS")
    private String manConfirmXsdNs;
    @Column(name = "PROCESS_DAY")
    private Short processDay;
    @Size(max = 1)
    @Column(name = "CIS_DWNLD_IND")
    private String cisDwnldInd;
    @Column(name = "CIS_DWNLD_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date cisDwnldDate;
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
    @Size(max = 50)
    @Column(name = "MAN_SUSP_XSD_NS")
    private String manSuspXsdNs;

    public CasSysctrlCustParamEntity() {
    }

    public CasSysctrlCustParamEntity(String instId) {
        this.instId = instId;
    }

	public String getManAmdXsdNs() {
		return manAmdXsdNs;
	}

	public void setManAmdXsdNs(String manAmdXsdNs) {
		this.manAmdXsdNs = manAmdXsdNs;
	}

	public String getManCanXsdNs() {
		return manCanXsdNs;
	}

	public void setManCanXsdNs(String manCanXsdNs) {
		this.manCanXsdNs = manCanXsdNs;
	}

	public String getManAccpXsdNs() {
		return manAccpXsdNs;
	}

	public void setManAccpXsdNs(String manAccpXsdNs) {
		this.manAccpXsdNs = manAccpXsdNs;
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

	public String getManInitXsdNs() {
		return manInitXsdNs;
	}

	public void setManInitXsdNs(String manInitXsdNs) {
		this.manInitXsdNs = manInitXsdNs;
	}

	public String getMdtReqIdReuseInd() {
		return mdtReqIdReuseInd;
	}

	public void setMdtReqIdReuseInd(String mdtReqIdReuseInd) {
		this.mdtReqIdReuseInd = mdtReqIdReuseInd;
	}

	public String getMdteReqXsdNs() {
		return mdteReqXsdNs;
	}

	public void setMdteReqXsdNs(String mdteReqXsdNs) {
		this.mdteReqXsdNs = mdteReqXsdNs;
	}

	public String getMdteRespXsdNs() {
		return mdteRespXsdNs;
	}

	public void setMdteRespXsdNs(String mdteRespXsdNs) {
		this.mdteRespXsdNs = mdteRespXsdNs;
	}

	public String getManStatusRepXsdNs() {
		return manStatusRepXsdNs;
	}

	public void setManStatusRepXsdNs(String manStatusRepXsdNs) {
		this.manStatusRepXsdNs = manStatusRepXsdNs;
	}

	public String getManConfirmXsdNs() {
		return manConfirmXsdNs;
	}

	public void setManConfirmXsdNs(String manConfirmXsdNs) {
		this.manConfirmXsdNs = manConfirmXsdNs;
	}

	public Short getProcessDay() {
		return processDay;
	}

	public void setProcessDay(Short processDay) {
		this.processDay = processDay;
	}

	public String getCisDwnldInd() {
		return cisDwnldInd;
	}

	public void setCisDwnldInd(String cisDwnldInd) {
		this.cisDwnldInd = cisDwnldInd;
	}

	public Date getCisDwnldDate() {
		return cisDwnldDate;
	}

	public void setCisDwnldDate(Date cisDwnldDate) {
		this.cisDwnldDate = cisDwnldDate;
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

	  public String getManSuspXsdNs() {
	        return manSuspXsdNs;
	    }

	    public void setManSuspXsdNs(String manSuspXsdNs) {
	        this.manSuspXsdNs = manSuspXsdNs;
	    }

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((activeInd == null) ? 0 : activeInd.hashCode());
			result = prime * result
					+ ((cisDwnldDate == null) ? 0 : cisDwnldDate.hashCode());
			result = prime * result
					+ ((cisDwnldInd == null) ? 0 : cisDwnldInd.hashCode());
			result = prime * result
					+ ((createdBy == null) ? 0 : createdBy.hashCode());
			result = prime * result
					+ ((createdDate == null) ? 0 : createdDate.hashCode());
			result = prime * result
					+ ((instId == null) ? 0 : instId.hashCode());
			result = prime * result
					+ ((manAccpXsdNs == null) ? 0 : manAccpXsdNs.hashCode());
			result = prime * result
					+ ((manAmdXsdNs == null) ? 0 : manAmdXsdNs.hashCode());
			result = prime * result
					+ ((manCanXsdNs == null) ? 0 : manCanXsdNs.hashCode());
			result = prime
					* result
					+ ((manConfirmXsdNs == null) ? 0 : manConfirmXsdNs
							.hashCode());
			result = prime * result
					+ ((manInitXsdNs == null) ? 0 : manInitXsdNs.hashCode());
			result = prime
					* result
					+ ((manStatusRepXsdNs == null) ? 0 : manStatusRepXsdNs
							.hashCode());
			result = prime * result
					+ ((manSuspXsdNs == null) ? 0 : manSuspXsdNs.hashCode());
			result = prime
					* result
					+ ((mdtReqIdReuseInd == null) ? 0 : mdtReqIdReuseInd
							.hashCode());
			result = prime * result
					+ ((mdteReqXsdNs == null) ? 0 : mdteReqXsdNs.hashCode());
			result = prime * result
					+ ((mdteRespXsdNs == null) ? 0 : mdteRespXsdNs.hashCode());
			result = prime * result
					+ ((modifiedBy == null) ? 0 : modifiedBy.hashCode());
			result = prime * result
					+ ((modifiedDate == null) ? 0 : modifiedDate.hashCode());
			result = prime * result
					+ ((processDay == null) ? 0 : processDay.hashCode());
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
			CasSysctrlCustParamEntity other = (CasSysctrlCustParamEntity) obj;
			if (activeInd == null) {
				if (other.activeInd != null)
					return false;
			} else if (!activeInd.equals(other.activeInd))
				return false;
			if (cisDwnldDate == null) {
				if (other.cisDwnldDate != null)
					return false;
			} else if (!cisDwnldDate.equals(other.cisDwnldDate))
				return false;
			if (cisDwnldInd == null) {
				if (other.cisDwnldInd != null)
					return false;
			} else if (!cisDwnldInd.equals(other.cisDwnldInd))
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
			if (instId == null) {
				if (other.instId != null)
					return false;
			} else if (!instId.equals(other.instId))
				return false;
			if (manAccpXsdNs == null) {
				if (other.manAccpXsdNs != null)
					return false;
			} else if (!manAccpXsdNs.equals(other.manAccpXsdNs))
				return false;
			if (manAmdXsdNs == null) {
				if (other.manAmdXsdNs != null)
					return false;
			} else if (!manAmdXsdNs.equals(other.manAmdXsdNs))
				return false;
			if (manCanXsdNs == null) {
				if (other.manCanXsdNs != null)
					return false;
			} else if (!manCanXsdNs.equals(other.manCanXsdNs))
				return false;
			if (manConfirmXsdNs == null) {
				if (other.manConfirmXsdNs != null)
					return false;
			} else if (!manConfirmXsdNs.equals(other.manConfirmXsdNs))
				return false;
			if (manInitXsdNs == null) {
				if (other.manInitXsdNs != null)
					return false;
			} else if (!manInitXsdNs.equals(other.manInitXsdNs))
				return false;
			if (manStatusRepXsdNs == null) {
				if (other.manStatusRepXsdNs != null)
					return false;
			} else if (!manStatusRepXsdNs.equals(other.manStatusRepXsdNs))
				return false;
			if (manSuspXsdNs == null) {
				if (other.manSuspXsdNs != null)
					return false;
			} else if (!manSuspXsdNs.equals(other.manSuspXsdNs))
				return false;
			if (mdtReqIdReuseInd == null) {
				if (other.mdtReqIdReuseInd != null)
					return false;
			} else if (!mdtReqIdReuseInd.equals(other.mdtReqIdReuseInd))
				return false;
			if (mdteReqXsdNs == null) {
				if (other.mdteReqXsdNs != null)
					return false;
			} else if (!mdteReqXsdNs.equals(other.mdteReqXsdNs))
				return false;
			if (mdteRespXsdNs == null) {
				if (other.mdteRespXsdNs != null)
					return false;
			} else if (!mdteRespXsdNs.equals(other.mdteRespXsdNs))
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
			if (processDay == null) {
				if (other.processDay != null)
					return false;
			} else if (!processDay.equals(other.processDay))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "CasSysctrlCustParamEntity [manAmdXsdNs=" + manAmdXsdNs
					+ ", manCanXsdNs=" + manCanXsdNs + ", manAccpXsdNs="
					+ manAccpXsdNs + ", activeInd=" + activeInd + ", instId="
					+ instId + ", manInitXsdNs=" + manInitXsdNs
					+ ", mdtReqIdReuseInd=" + mdtReqIdReuseInd
					+ ", mdteReqXsdNs=" + mdteReqXsdNs + ", mdteRespXsdNs="
					+ mdteRespXsdNs + ", manStatusRepXsdNs="
					+ manStatusRepXsdNs + ", manConfirmXsdNs="
					+ manConfirmXsdNs + ", processDay=" + processDay
					+ ", cisDwnldInd=" + cisDwnldInd + ", cisDwnldDate="
					+ cisDwnldDate + ", createdBy=" + createdBy
					+ ", createdDate=" + createdDate + ", modifiedBy="
					+ modifiedBy + ", modifiedDate=" + modifiedDate
					+ ", manSuspXsdNs=" + manSuspXsdNs + "]";
		}

}
