package com.bsva.entities;

import java.io.Serializable;
import java.math.BigInteger;
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

/***
 * 
 * @author DimakatsoN
 *
 */
@Entity
@Table(name = "CAS_OPS_LAST_EXTRACT_TIMES",schema = "CAMOWNER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CasOpsLastExtractTimesEntity.findAll", query = "SELECT m FROM CasOpsLastExtractTimesEntity m"),
    @NamedQuery(name = "CasOpsLastExtractTimesEntity.findByServiceIdOut", query = "SELECT m FROM CasOpsLastExtractTimesEntity m WHERE m.serviceIdOut = :serviceIdOut"),
    @NamedQuery(name = "CasOpsLastExtractTimesEntity.findByLastExtractTime", query = "SELECT m FROM CasOpsLastExtractTimesEntity m WHERE m.lastExtractTime = :lastExtractTime"),
    @NamedQuery(name = "CasOpsLastExtractTimesEntity.findByFileTransactionLimit", query = "SELECT m FROM CasOpsLastExtractTimesEntity m WHERE m.fileTransactionLimit = :fileTransactionLimit"),
    @NamedQuery(name = "CasOpsLastExtractTimesEntity.findByCreatedBy", query = "SELECT m FROM CasOpsLastExtractTimesEntity m WHERE m.createdBy = :createdBy"),
    @NamedQuery(name = "CasOpsLastExtractTimesEntity.findByCreatedDate", query = "SELECT m FROM CasOpsLastExtractTimesEntity m WHERE m.createdDate = :createdDate"),
    @NamedQuery(name = "CasOpsLastExtractTimesEntity.findByModifiedBy", query = "SELECT m FROM CasOpsLastExtractTimesEntity m WHERE m.modifiedBy = :modifiedBy"),
    @NamedQuery(name = "CasOpsLastExtractTimesEntity.findByModifiedDate", query = "SELECT m FROM CasOpsLastExtractTimesEntity m WHERE m.modifiedDate = :modifiedDate")})
public class CasOpsLastExtractTimesEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "SERVICE_ID_OUT")
    private String serviceIdOut;
    @Column(name = "LAST_EXTRACT_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastExtractTime;
    @Column(name = "FILE_TRANSACTION_LIMIT")
    private BigInteger fileTransactionLimit;
    @Size(max = 20)
    @Column(name = "CREATED_BY")
    private String createdBy;
    @Column(name = "CREATED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Size(max = 20)
    @Column(name = "MODIFIED_BY")
    private String modifiedBy;
    @Column(name = "MODIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;

    public CasOpsLastExtractTimesEntity() {
    }

    public CasOpsLastExtractTimesEntity(String serviceIdOut) {
        this.serviceIdOut = serviceIdOut;
    }

    public String getServiceIdOut() {
        return serviceIdOut;
    }

    public void setServiceIdOut(String serviceIdOut) {
        this.serviceIdOut = serviceIdOut;
    }

    public Date getLastExtractTime() {
        return lastExtractTime;
    }

    public void setLastExtractTime(Date lastExtractTime) {
        this.lastExtractTime = lastExtractTime;
    }

    public BigInteger getFileTransactionLimit() {
        return fileTransactionLimit;
    }

    public void setFileTransactionLimit(BigInteger fileTransactionLimit) {
        this.fileTransactionLimit = fileTransactionLimit;
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
		result = prime * result + ((createdBy == null) ? 0 : createdBy.hashCode());
		result = prime * result + ((createdDate == null) ? 0 : createdDate.hashCode());
		result = prime * result + ((fileTransactionLimit == null) ? 0 : fileTransactionLimit.hashCode());
		result = prime * result + ((lastExtractTime == null) ? 0 : lastExtractTime.hashCode());
		result = prime * result + ((modifiedBy == null) ? 0 : modifiedBy.hashCode());
		result = prime * result + ((modifiedDate == null) ? 0 : modifiedDate.hashCode());
		result = prime * result + ((serviceIdOut == null) ? 0 : serviceIdOut.hashCode());
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
		CasOpsLastExtractTimesEntity other = (CasOpsLastExtractTimesEntity) obj;
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
		if (fileTransactionLimit == null) {
			if (other.fileTransactionLimit != null)
				return false;
		} else if (!fileTransactionLimit.equals(other.fileTransactionLimit))
			return false;
		if (lastExtractTime == null) {
			if (other.lastExtractTime != null)
				return false;
		} else if (!lastExtractTime.equals(other.lastExtractTime))
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
		if (serviceIdOut == null) {
			if (other.serviceIdOut != null)
				return false;
		} else if (!serviceIdOut.equals(other.serviceIdOut))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CasOpsLastExtractTimesEntity [serviceIdOut=" + serviceIdOut + ", lastExtractTime=" + lastExtractTime
				+ ", fileTransactionLimit=" + fileTransactionLimit + ", createdBy=" + createdBy + ", createdDate="
				+ createdDate + ", modifiedBy=" + modifiedBy + ", modifiedDate=" + modifiedDate + "]";
	}

    
    


	
    
    
    
   
}