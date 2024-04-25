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
@Table(name = "MDT_AC_OPS_BILLING_CTRLS", schema="MANOWNER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MdtAcOpsBillingCtrlsEntity.findAll", query = "SELECT m FROM MdtAcOpsBillingCtrlsEntity m"),
    @NamedQuery(name = "MdtAcOpsBillingCtrlsEntity.findByProcessType", query = "SELECT m FROM MdtAcOpsBillingCtrlsEntity m WHERE m.processType = :processType"),
    @NamedQuery(name = "MdtAcOpsBillingCtrlsEntity.findByCurrentSeqNo", query = "SELECT m FROM MdtAcOpsBillingCtrlsEntity m WHERE m.currentSeqNo = :currentSeqNo"),
    @NamedQuery(name = "MdtAcOpsBillingCtrlsEntity.findByLastSeqNo", query = "SELECT m FROM MdtAcOpsBillingCtrlsEntity m WHERE m.lastSeqNo = :lastSeqNo"),
    @NamedQuery(name = "MdtAcOpsBillingCtrlsEntity.findByBillingWindow", query = "SELECT m FROM MdtAcOpsBillingCtrlsEntity m WHERE m.billingWindow = :billingWindow"),
    @NamedQuery(name = "MdtAcOpsBillingCtrlsEntity.findByModifiedBy", query = "SELECT m FROM MdtAcOpsBillingCtrlsEntity m WHERE m.modifiedBy = :modifiedBy"),
    @NamedQuery(name = "MdtAcOpsBillingCtrlsEntity.findByModifiedDate", query = "SELECT m FROM MdtAcOpsBillingCtrlsEntity m WHERE m.modifiedDate = :modifiedDate")})
public class MdtAcOpsBillingCtrlsEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "PROCESS_TYPE")
    private String processType;
    @Column(name = "CURRENT_SEQ_NO")
    private BigDecimal currentSeqNo;
    @Column(name = "LAST_SEQ_NO")
    private BigDecimal lastSeqNo;
    @Column(name = "BILLING_WINDOW")
    private Short billingWindow;
    @Size(max = 25)
    @Column(name = "MODIFIED_BY")
    private String modifiedBy;
    @Column(name = "MODIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;

    public MdtAcOpsBillingCtrlsEntity() {
    }

    public MdtAcOpsBillingCtrlsEntity(String processType) {
        this.processType = processType;
    }

    public String getProcessType() {
        return processType;
    }

    public void setProcessType(String processType) {
        this.processType = processType;
    }

    public BigDecimal getCurrentSeqNo() {
        return currentSeqNo;
    }

    public void setCurrentSeqNo(BigDecimal currentSeqNo) {
        this.currentSeqNo = currentSeqNo;
    }

    public BigDecimal getLastSeqNo() {
        return lastSeqNo;
    }

    public void setLastSeqNo(BigDecimal lastSeqNo) {
        this.lastSeqNo = lastSeqNo;
    }

    public Short getBillingWindow() {
        return billingWindow;
    }

    public void setBillingWindow(Short billingWindow) {
        this.billingWindow = billingWindow;
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
				+ ((billingWindow == null) ? 0 : billingWindow.hashCode());
		result = prime * result
				+ ((currentSeqNo == null) ? 0 : currentSeqNo.hashCode());
		result = prime * result
				+ ((lastSeqNo == null) ? 0 : lastSeqNo.hashCode());
		result = prime * result
				+ ((modifiedBy == null) ? 0 : modifiedBy.hashCode());
		result = prime * result
				+ ((modifiedDate == null) ? 0 : modifiedDate.hashCode());
		result = prime * result
				+ ((processType == null) ? 0 : processType.hashCode());
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
		MdtAcOpsBillingCtrlsEntity other = (MdtAcOpsBillingCtrlsEntity) obj;
		if (billingWindow == null) {
			if (other.billingWindow != null)
				return false;
		} else if (!billingWindow.equals(other.billingWindow))
			return false;
		if (currentSeqNo == null) {
			if (other.currentSeqNo != null)
				return false;
		} else if (!currentSeqNo.equals(other.currentSeqNo))
			return false;
		if (lastSeqNo == null) {
			if (other.lastSeqNo != null)
				return false;
		} else if (!lastSeqNo.equals(other.lastSeqNo))
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
		if (processType == null) {
			if (other.processType != null)
				return false;
		} else if (!processType.equals(other.processType))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MdtAcOpsBillingCtrlsEntity [processType=" + processType
				+ ", currentSeqNo=" + currentSeqNo + ", lastSeqNo=" + lastSeqNo
				+ ", billingWindow=" + billingWindow + ", modifiedBy="
				+ modifiedBy + ", modifiedDate=" + modifiedDate + "]";
	}

    
    
}
