

package com.bsva.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
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
    @NamedQuery(name = "CasSysctrlCustParamEntity.findByCasaAmdXsdNs", query = "SELECT m FROM CasSysctrlCustParamEntity m WHERE m.casaAmdXsdNs = :casaAmdXsdNs"),
    @NamedQuery(name = "CasSysctrlCustParamEntity.findByCasaAccpXsdNs", query = "SELECT m FROM CasSysctrlCustParamEntity m WHERE m.casaAccpXsdNs = :casaAccpXsdNs"),
    @NamedQuery(name = "CasSysctrlCustParamEntity.findByActiveInd", query = "SELECT m FROM CasSysctrlCustParamEntity m WHERE m.activeInd = :activeInd"),
    @NamedQuery(name = "CasSysctrlCustParamEntity.findByInstId", query = "SELECT m FROM CasSysctrlCustParamEntity m WHERE m.instId = :instId"),
    @NamedQuery(name = "CasSysctrlCustParamEntity.findByCasaStatusRepXsdNs", query = "SELECT m FROM CasSysctrlCustParamEntity m WHERE m.casaStatusRepXsdNs = :casaStatusRepXsdNs"),
    @NamedQuery(name = "CasSysctrlCustParamEntity.findByCasaConfirmXsdNs", query = "SELECT m FROM CasSysctrlCustParamEntity m WHERE m.casaConfirmXsdNs = :casaConfirmXsdNs"),
    @NamedQuery(name = "CasSysctrlCustParamEntity.findByProcessDay", query = "SELECT m FROM CasSysctrlCustParamEntity m WHERE m.processDay = :processDay"),
    @NamedQuery(name = "CasSysctrlCustParamEntity.findByCreatedBy", query = "SELECT m FROM CasSysctrlCustParamEntity m WHERE m.createdBy = :createdBy"),
    @NamedQuery(name = "CasSysctrlCustParamEntity.findByCreatedDate", query = "SELECT m FROM CasSysctrlCustParamEntity m WHERE m.createdDate = :createdDate"),
    @NamedQuery(name = "CasSysctrlCustParamEntity.findByModifiedBy", query = "SELECT m FROM CasSysctrlCustParamEntity m WHERE m.modifiedBy = :modifiedBy"),
    @NamedQuery(name = "CasSysctrlCustParamEntity.findByModifiedDate", query = "SELECT m FROM CasSysctrlCustParamEntity m WHERE m.modifiedDate = :modifiedDate")})
public class CasSysctrlCustParamEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Size(max = 50)
    @Column(name = "CASA_AMD_XSD_NS")
	private String casaAmdXsdNs;
    @Size(max = 50)
    @Column(name = "CASA_ACCP_XSD_NS")
    private String casaAccpXsdNs;
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
    @Column(name = "CASA_STATUS_REP_XSD_NS")
    private String casaStatusRepXsdNs;
    @Size(max = 50)
    @Column(name = "CASA_CONFIRM_XSD_NS")
    private String casaConfirmXsdNs;
    @Column(name = "PROCESS_DAY")
    private Short processDay;
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

    public CasSysctrlCustParamEntity() {
    }

    public CasSysctrlCustParamEntity(String instId) {
        this.instId = instId;
    }

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
    CasSysctrlCustParamEntity that = (CasSysctrlCustParamEntity) o;
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


}
