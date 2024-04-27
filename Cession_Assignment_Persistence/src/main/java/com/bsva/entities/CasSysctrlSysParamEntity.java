
package com.bsva.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ElelwaniR
 */
@Entity
@Table(name = "CAS_SYSCTRL_SYS_PARAM",schema = "CAMOWNER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CasSysctrlSysParamEntity.findAll", query = "SELECT m FROM CasSysctrlSysParamEntity m"),
    @NamedQuery(name = "CasSysctrlSysParamEntity.findBySysName", query = "SELECT m FROM CasSysctrlSysParamEntity m WHERE m.sysName = :sysName"),
    @NamedQuery(name = "CasSysctrlSysParamEntity.findByDefCurr", query = "SELECT m FROM CasSysctrlSysParamEntity m WHERE m.defCurr = :defCurr"),
    @NamedQuery(name = "CasSysctrlSysParamEntity.findByInactiveDuration", query = "SELECT m FROM CasSysctrlSysParamEntity m WHERE m.inactiveDuration = :inactiveDuration"),
    @NamedQuery(name = "CasSysctrlSysParamEntity.findBySysType", query = "SELECT m FROM CasSysctrlSysParamEntity m WHERE m.sysType = :sysType"),
    @NamedQuery(name = "CasSysctrlSysParamEntity.findByArchivePeriod", query = "SELECT m FROM CasSysctrlSysParamEntity m WHERE m.archivePeriod = :archivePeriod"),
    @NamedQuery(name = "CasSysctrlSysParamEntity.findByActiveInd", query = "SELECT m FROM CasSysctrlSysParamEntity m WHERE m.activeInd = :activeInd"),
    @NamedQuery(name = "CasSysctrlSysParamEntity.findByCreatedBy", query = "SELECT m FROM CasSysctrlSysParamEntity m WHERE m.createdBy = :createdBy"),
    @NamedQuery(name = "CasSysctrlSysParamEntity.findByCreatedDate", query = "SELECT m FROM CasSysctrlSysParamEntity m WHERE m.createdDate = :createdDate"),
    @NamedQuery(name = "CasSysctrlSysParamEntity.findByModifiedBy", query = "SELECT m FROM CasSysctrlSysParamEntity m WHERE m.modifiedBy = :modifiedBy"),
    @NamedQuery(name = "CasSysctrlSysParamEntity.findByModifiedDate", query = "SELECT m FROM CasSysctrlSysParamEntity m WHERE m.modifiedDate = :modifiedDate"),
    @NamedQuery(name = "CasSysctrlSysParamEntity.findBySodRunInd", query = "SELECT m FROM CasSysctrlSysParamEntity m WHERE m.sodRunInd = :sodRunInd"),
    @NamedQuery(name = "CasSysctrlSysParamEntity.findByEodRunInd", query = "SELECT m FROM CasSysctrlSysParamEntity m WHERE m.eodRunInd = :eodRunInd"),
    @NamedQuery(name = "CasSysctrlSysParamEntity.findByCisDwnldInd", query = "SELECT m FROM CasSysctrlSysParamEntity m WHERE m.cisDwnldInd = :cisDwnldInd"),
    @NamedQuery(name = "CasSysctrlSysParamEntity.findByCisDwnldDate", query = "SELECT m FROM CasSysctrlSysParamEntity m WHERE m.cisDwnldDate = :cisDwnldDate"),
    @NamedQuery(name = "CasSysctrlSysParamEntity.findByProcessDate", query = "SELECT m FROM CasSysctrlSysParamEntity m WHERE m.processDate = :processDate"),
    @NamedQuery(name = "CasSysctrlSysParamEntity.findByNextMondayHolInd", query = "SELECT m FROM CasSysctrlSysParamEntity m WHERE m.nextMondayHolInd = :nextMondayHolInd"),
    @NamedQuery(name = "CasSysctrlSysParamEntity.findByEasterDaysInd", query = "SELECT m FROM CasSysctrlSysParamEntity m WHERE m.easterDaysInd = :easterDaysInd"),
    @NamedQuery(name = "CasSysctrlSysParamEntity.findByInBalanceInd", query = "SELECT m FROM CasSysctrlSysParamEntity m WHERE m.inBalanceInd = :inBalanceInd"),
    @NamedQuery(name = "CasSysctrlSysParamEntity.findByForcecloseReason", query = "SELECT m FROM CasSysctrlSysParamEntity m WHERE m.forcecloseReason = :forcecloseReason"),
    @NamedQuery(name = "CasSysctrlSysParamEntity.findBySysCloseRunInd", query = "SELECT m FROM CasSysctrlSysParamEntity m WHERE m.sysCloseRunInd = :sysCloseRunInd"),
    @NamedQuery(name = "CasSysctrlSysParamEntity.findByResponsePeriod", query = "SELECT m FROM CasSysctrlSysParamEntity m WHERE m.responsePeriod = :responsePeriod"),
    @NamedQuery(name = "CasSysctrlSysParamEntity.findByIamPort", query = "SELECT m FROM CasSysctrlSysParamEntity m WHERE m.iamPort = :iamPort"),
    @NamedQuery(name = "CasSysctrlSysParamEntity.findByItemLimit", query = "SELECT m FROM CasSysctrlSysParamEntity m WHERE m.itemLimit = :itemLimit"),
@NamedQuery(name = "CasSysctrlSysParamEntity.findByFileTransactionLimit", query = "SELECT m FROM CasSysctrlSysParamEntity m WHERE m.fileTransactionLimit = :fileTransactionLimit")})
public class CasSysctrlSysParamEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Size(max = 100)
    @Column(name = "SYS_NAME")
    private String sysName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "DEF_CURR")
    private String defCurr;
    @Column(name = "INACTIVE_DURATION")
    private Short inactiveDuration;
    @Size(max = 35)
    @Column(name = "SYS_TYPE")
    private String sysType;
    @Column(name = "ARCHIVE_PERIOD")
    private BigInteger archivePeriod;
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
    @Size(max = 1)
    @Column(name = "SOD_RUN_IND")
    private String sodRunInd;
    @Size(max = 1)
    @Column(name = "EOD_RUN_IND")
    private String eodRunInd;
    @Size(max = 1)
    @Column(name = "CIS_DWNLD_IND")
    private String cisDwnldInd;
    @Column(name = "CIS_DWNLD_DATE")
    @Temporal(TemporalType.DATE)
    private Date cisDwnldDate;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "PROCESS_DATE")
    @Temporal(TemporalType.DATE)
    private Date processDate;
    @Size(max = 1)
    @Column(name = "NEXT_MONDAY_HOL_IND")
    private String nextMondayHolInd;
    @Size(max = 1)
    @Column(name = "EASTER_DAYS_IND")
    private String easterDaysInd;
    @Size(max = 1)
    @Column(name = "IN_BALANCE_IND")
    private String inBalanceInd;
    @Size(max = 500)
    @Column(name = "FORCECLOSE_REASON")
    private String forcecloseReason;
    @Size(max = 1)
    @Column(name = "SYS_CLOSE_RUN_IND")
    private String sysCloseRunInd;
    @Column(name = "RESPONSE_PERIOD")
    private Short responsePeriod;
    @Column(name = "IAM_PORT")
    private Short iamPort;
    @Column(name = "ITEM_LIMIT")
    private BigInteger itemLimit;
    @Column(name = "FILE_TRANSACTION_LIMIT")
    private BigInteger fileTransactionLimit;

    public CasSysctrlSysParamEntity() {
    }

    public CasSysctrlSysParamEntity(Date processDate) {
        this.processDate = processDate;
    }

    public CasSysctrlSysParamEntity(Date processDate, String defCurr) {
        this.processDate = processDate;
        this.defCurr = defCurr;
    }

    public String getSysName() {
        return sysName;
    }

    public void setSysName(String sysName) {
        this.sysName = sysName;
    }

    public String getDefCurr() {
        return defCurr;
    }

    public void setDefCurr(String defCurr) {
        this.defCurr = defCurr;
    }

    public Short getInactiveDuration() {
        return inactiveDuration;
    }

    public void setInactiveDuration(Short inactiveDuration) {
        this.inactiveDuration = inactiveDuration;
    }

    public String getSysType() {
        return sysType;
    }

    public void setSysType(String sysType) {
        this.sysType = sysType;
    }

    public BigInteger getArchivePeriod() {
        return archivePeriod;
    }

    public void setArchivePeriod(BigInteger archivePeriod) {
        this.archivePeriod = archivePeriod;
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

    public String getSodRunInd() {
        return sodRunInd;
    }

    public void setSodRunInd(String sodRunInd) {
        this.sodRunInd = sodRunInd;
    }

    public String getEodRunInd() {
        return eodRunInd;
    }

    public void setEodRunInd(String eodRunInd) {
        this.eodRunInd = eodRunInd;
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

    public Date getProcessDate() {
        return processDate;
    }

    public void setProcessDate(Date processDate) {
        this.processDate = processDate;
    }

    public String getNextMondayHolInd() {
        return nextMondayHolInd;
    }

    public void setNextMondayHolInd(String nextMondayHolInd) {
        this.nextMondayHolInd = nextMondayHolInd;
    }

    public String getEasterDaysInd() {
        return easterDaysInd;
    }

    public void setEasterDaysInd(String easterDaysInd) {
        this.easterDaysInd = easterDaysInd;
    }

    public String getInBalanceInd() {
        return inBalanceInd;
    }

    public void setInBalanceInd(String inBalanceInd) {
        this.inBalanceInd = inBalanceInd;
    }

    public String getForcecloseReason() {
        return forcecloseReason;
    }

    public void setForcecloseReason(String forcecloseReason) {
        this.forcecloseReason = forcecloseReason;
    }

    public String getSysCloseRunInd() {
        return sysCloseRunInd;
    }

    public void setSysCloseRunInd(String sysCloseRunInd) {
        this.sysCloseRunInd = sysCloseRunInd;
    }

    public Short getResponsePeriod() {
        return responsePeriod;
    }

    public void setResponsePeriod(Short responsePeriod) {
        this.responsePeriod = responsePeriod;
    }

    public Short getIamPort() {
        return iamPort;
    }

    public void setIamPort(Short iamPort) {
        this.iamPort = iamPort;
    }
    

    public BigInteger getItemLimit() {
        return itemLimit;
    }

    public void setItemLimit(BigInteger itemLimit) {
        this.itemLimit = itemLimit;
    }
    
    public BigInteger getFileTransactionLimit() {
    	return  fileTransactionLimit;
    }
    
    public void setFileTransactionLimit(BigInteger fileTransactionLimit) {
    	this.fileTransactionLimit = fileTransactionLimit;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((activeInd == null) ? 0 : activeInd.hashCode());
		result = prime * result + ((archivePeriod == null) ? 0 : archivePeriod.hashCode());
		result = prime * result + ((cisDwnldDate == null) ? 0 : cisDwnldDate.hashCode());
		result = prime * result + ((cisDwnldInd == null) ? 0 : cisDwnldInd.hashCode());
		result = prime * result + ((createdBy == null) ? 0 : createdBy.hashCode());
		result = prime * result + ((createdDate == null) ? 0 : createdDate.hashCode());
		result = prime * result + ((defCurr == null) ? 0 : defCurr.hashCode());
		result = prime * result + ((easterDaysInd == null) ? 0 : easterDaysInd.hashCode());
		result = prime * result + ((eodRunInd == null) ? 0 : eodRunInd.hashCode());
		result = prime * result + ((fileTransactionLimit == null) ? 0 : fileTransactionLimit.hashCode());
		result = prime * result + ((forcecloseReason == null) ? 0 : forcecloseReason.hashCode());
		result = prime * result + ((iamPort == null) ? 0 : iamPort.hashCode());
		result = prime * result + ((inBalanceInd == null) ? 0 : inBalanceInd.hashCode());
		result = prime * result + ((inactiveDuration == null) ? 0 : inactiveDuration.hashCode());
		result = prime * result + ((itemLimit == null) ? 0 : itemLimit.hashCode());
		result = prime * result + ((modifiedBy == null) ? 0 : modifiedBy.hashCode());
		result = prime * result + ((modifiedDate == null) ? 0 : modifiedDate.hashCode());
		result = prime * result + ((nextMondayHolInd == null) ? 0 : nextMondayHolInd.hashCode());
		result = prime * result + ((processDate == null) ? 0 : processDate.hashCode());
		result = prime * result + ((responsePeriod == null) ? 0 : responsePeriod.hashCode());
		result = prime * result + ((sodRunInd == null) ? 0 : sodRunInd.hashCode());
		result = prime * result + ((sysCloseRunInd == null) ? 0 : sysCloseRunInd.hashCode());
		result = prime * result + ((sysName == null) ? 0 : sysName.hashCode());
		result = prime * result + ((sysType == null) ? 0 : sysType.hashCode());
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
		CasSysctrlSysParamEntity other = (CasSysctrlSysParamEntity) obj;
		if (activeInd == null) {
			if (other.activeInd != null)
				return false;
		} else if (!activeInd.equals(other.activeInd))
			return false;
		if (archivePeriod == null) {
			if (other.archivePeriod != null)
				return false;
		} else if (!archivePeriod.equals(other.archivePeriod))
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
		if (defCurr == null) {
			if (other.defCurr != null)
				return false;
		} else if (!defCurr.equals(other.defCurr))
			return false;
		if (easterDaysInd == null) {
			if (other.easterDaysInd != null)
				return false;
		} else if (!easterDaysInd.equals(other.easterDaysInd))
			return false;
		if (eodRunInd == null) {
			if (other.eodRunInd != null)
				return false;
		} else if (!eodRunInd.equals(other.eodRunInd))
			return false;
		if (fileTransactionLimit == null) {
			if (other.fileTransactionLimit != null)
				return false;
		} else if (!fileTransactionLimit.equals(other.fileTransactionLimit))
			return false;
		if (forcecloseReason == null) {
			if (other.forcecloseReason != null)
				return false;
		} else if (!forcecloseReason.equals(other.forcecloseReason))
			return false;
		if (iamPort == null) {
			if (other.iamPort != null)
				return false;
		} else if (!iamPort.equals(other.iamPort))
			return false;
		if (inBalanceInd == null) {
			if (other.inBalanceInd != null)
				return false;
		} else if (!inBalanceInd.equals(other.inBalanceInd))
			return false;
		if (inactiveDuration == null) {
			if (other.inactiveDuration != null)
				return false;
		} else if (!inactiveDuration.equals(other.inactiveDuration))
			return false;
		if (itemLimit == null) {
			if (other.itemLimit != null)
				return false;
		} else if (!itemLimit.equals(other.itemLimit))
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
		if (nextMondayHolInd == null) {
			if (other.nextMondayHolInd != null)
				return false;
		} else if (!nextMondayHolInd.equals(other.nextMondayHolInd))
			return false;
		if (processDate == null) {
			if (other.processDate != null)
				return false;
		} else if (!processDate.equals(other.processDate))
			return false;
		if (responsePeriod == null) {
			if (other.responsePeriod != null)
				return false;
		} else if (!responsePeriod.equals(other.responsePeriod))
			return false;
		if (sodRunInd == null) {
			if (other.sodRunInd != null)
				return false;
		} else if (!sodRunInd.equals(other.sodRunInd))
			return false;
		if (sysCloseRunInd == null) {
			if (other.sysCloseRunInd != null)
				return false;
		} else if (!sysCloseRunInd.equals(other.sysCloseRunInd))
			return false;
		if (sysName == null) {
			if (other.sysName != null)
				return false;
		} else if (!sysName.equals(other.sysName))
			return false;
		if (sysType == null) {
			if (other.sysType != null)
				return false;
		} else if (!sysType.equals(other.sysType))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CasSysctrlSysParamEntity [sysName=" + sysName + ", defCurr=" + defCurr + ", inactiveDuration="
				+ inactiveDuration + ", sysType=" + sysType + ", archivePeriod=" + archivePeriod + ", activeInd="
				+ activeInd + ", createdBy=" + createdBy + ", createdDate=" + createdDate + ", modifiedBy=" + modifiedBy
				+ ", modifiedDate=" + modifiedDate + ", sodRunInd=" + sodRunInd + ", eodRunInd=" + eodRunInd
				+ ", cisDwnldInd=" + cisDwnldInd + ", cisDwnldDate=" + cisDwnldDate + ", processDate=" + processDate
				+ ", nextMondayHolInd=" + nextMondayHolInd + ", easterDaysInd=" + easterDaysInd + ", inBalanceInd="
				+ inBalanceInd + ", forcecloseReason=" + forcecloseReason + ", sysCloseRunInd=" + sysCloseRunInd
				+ ", responsePeriod=" + responsePeriod + ", iamPort=" + iamPort + ", itemLimit=" + itemLimit
				+ ", fileTransactionLimit=" + fileTransactionLimit + "]";
	}

	
    

    
    


    
}
