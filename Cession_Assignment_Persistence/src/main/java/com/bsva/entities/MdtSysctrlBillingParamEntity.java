 
package com.bsva.entities;

import java.io.Serializable;
import java.math.BigDecimal;
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
 * @author SalehaR
 */
@Entity
@Table(name = "MDT_SYSCTRL_BILLING_PARAM",schema = "MANOWNER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MdtSysctrlBillingParamEntity.findAll", query = "SELECT m FROM MdtSysctrlBillingParamEntity m"),
    @NamedQuery(name = "MdtSysctrlBillingParamEntity.findByMemberId", query = "SELECT m FROM MdtSysctrlBillingParamEntity m WHERE m.memberId = :memberId"),
    @NamedQuery(name = "MdtSysctrlBillingParamEntity.findByServiceId", query = "SELECT m FROM MdtSysctrlBillingParamEntity m WHERE m.serviceId = :serviceId"),
    @NamedQuery(name = "MdtSysctrlBillingParamEntity.findByInterchangeFees", query = "SELECT m FROM MdtSysctrlBillingParamEntity m WHERE m.interchangeFees = :interchangeFees"),
    @NamedQuery(name = "MdtSysctrlBillingParamEntity.findByItemProcessCharges", query = "SELECT m FROM MdtSysctrlBillingParamEntity m WHERE m.itemProcessCharges = :itemProcessCharges"),
    @NamedQuery(name = "MdtSysctrlBillingParamEntity.findByAccNr", query = "SELECT m FROM MdtSysctrlBillingParamEntity m WHERE m.accNr = :accNr"),
    @NamedQuery(name = "MdtSysctrlBillingParamEntity.findByVat", query = "SELECT m FROM MdtSysctrlBillingParamEntity m WHERE m.vat = :vat"),
    @NamedQuery(name = "MdtSysctrlBillingParamEntity.findByCreatedBy", query = "SELECT m FROM MdtSysctrlBillingParamEntity m WHERE m.createdBy = :createdBy"),
    @NamedQuery(name = "MdtSysctrlBillingParamEntity.findByCreatedDate", query = "SELECT m FROM MdtSysctrlBillingParamEntity m WHERE m.createdDate = :createdDate"),
    @NamedQuery(name = "MdtSysctrlBillingParamEntity.findByModifiedBy", query = "SELECT m FROM MdtSysctrlBillingParamEntity m WHERE m.modifiedBy = :modifiedBy"),
    @NamedQuery(name = "MdtSysctrlBillingParamEntity.findByModifiedDate", query = "SELECT m FROM MdtSysctrlBillingParamEntity m WHERE m.modifiedDate = :modifiedDate")})
public class MdtSysctrlBillingParamEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "MEMBER_ID")
    private String memberId;
    @Size(max = 5)
    @Column(name = "SERVICE_ID")
    private String serviceId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "INTERCHANGE_FEES")
    private BigDecimal interchangeFees;
    @Column(name = "ITEM_PROCESS_CHARGES")
    private BigDecimal itemProcessCharges;
    @Size(max = 20)
    @Column(name = "ACC_NR")
    private String accNr;
    @Column(name = "VAT")
    private BigDecimal vat;
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

    public MdtSysctrlBillingParamEntity() {
    }

    public MdtSysctrlBillingParamEntity(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public BigDecimal getInterchangeFees() {
        return interchangeFees;
    }

    public void setInterchangeFees(BigDecimal interchangeFees) {
        this.interchangeFees = interchangeFees;
    }

    public BigDecimal getItemProcessCharges() {
        return itemProcessCharges;
    }

    public void setItemProcessCharges(BigDecimal itemProcessCharges) {
        this.itemProcessCharges = itemProcessCharges;
    }

    public String getAccNr() {
        return accNr;
    }

    public void setAccNr(String accNr) {
        this.accNr = accNr;
    }

    public BigDecimal getVat() {
        return vat;
    }

    public void setVat(BigDecimal vat) {
        this.vat = vat;
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
		result = prime * result + ((accNr == null) ? 0 : accNr.hashCode());
		result = prime * result
				+ ((createdBy == null) ? 0 : createdBy.hashCode());
		result = prime * result
				+ ((createdDate == null) ? 0 : createdDate.hashCode());
		result = prime * result
				+ ((interchangeFees == null) ? 0 : interchangeFees.hashCode());
		result = prime
				* result
				+ ((itemProcessCharges == null) ? 0 : itemProcessCharges
						.hashCode());
		result = prime * result
				+ ((memberId == null) ? 0 : memberId.hashCode());
		result = prime * result
				+ ((modifiedBy == null) ? 0 : modifiedBy.hashCode());
		result = prime * result
				+ ((modifiedDate == null) ? 0 : modifiedDate.hashCode());
		result = prime * result
				+ ((serviceId == null) ? 0 : serviceId.hashCode());
		result = prime * result + ((vat == null) ? 0 : vat.hashCode());
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
		MdtSysctrlBillingParamEntity other = (MdtSysctrlBillingParamEntity) obj;
		if (accNr == null) {
			if (other.accNr != null)
				return false;
		} else if (!accNr.equals(other.accNr))
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
		if (interchangeFees == null) {
			if (other.interchangeFees != null)
				return false;
		} else if (!interchangeFees.equals(other.interchangeFees))
			return false;
		if (itemProcessCharges == null) {
			if (other.itemProcessCharges != null)
				return false;
		} else if (!itemProcessCharges.equals(other.itemProcessCharges))
			return false;
		if (memberId == null) {
			if (other.memberId != null)
				return false;
		} else if (!memberId.equals(other.memberId))
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
		if (serviceId == null) {
			if (other.serviceId != null)
				return false;
		} else if (!serviceId.equals(other.serviceId))
			return false;
		if (vat == null) {
			if (other.vat != null)
				return false;
		} else if (!vat.equals(other.vat))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MdtSysctrlBillingParamEntity [memberId=" + memberId + ", serviceId="
				+ serviceId + ", interchangeFees=" + interchangeFees
				+ ", itemProcessCharges=" + itemProcessCharges + ", accNr="
				+ accNr + ", vat=" + vat + ", createdBy=" + createdBy
				+ ", createdDate=" + createdDate + ", modifiedBy=" + modifiedBy
				+ ", modifiedDate=" + modifiedDate + "]";
	}


}
