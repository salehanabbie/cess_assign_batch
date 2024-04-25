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
 * @author SalehaR
 */
@Entity
@Table(name = "MDT_CNFG_CURRENCY_CODES",schema = "MANOWNER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MdtCnfgCurrencyCodesEntity.findAll", query = "SELECT m FROM MdtCnfgCurrencyCodesEntity m"),
    @NamedQuery(name = "MdtCnfgCurrencyCodesEntity.findByCountryCode", query = "SELECT m FROM MdtCnfgCurrencyCodesEntity m WHERE m.countryCode = :countryCode"),
    @NamedQuery(name = "MdtCnfgCurrencyCodesEntity.findByCountryCodeLIKE", query = "SELECT m FROM MdtCnfgCurrencyCodesEntity m WHERE m.countryCode LIKE :countryCode"),    
    @NamedQuery(name = "MdtCnfgCurrencyCodesEntity.findByAlphaCurrCode", query = "SELECT m FROM MdtCnfgCurrencyCodesEntity m WHERE m.alphaCurrCode = :alphaCurrCode"),
    @NamedQuery(name = "MdtCnfgCurrencyCodesEntity.findByNumCurrCode", query = "SELECT m FROM MdtCnfgCurrencyCodesEntity m WHERE m.numCurrCode = :numCurrCode"),
    @NamedQuery(name = "MdtCnfgCurrencyCodesEntity.findByCurrCodeDesc", query = "SELECT m FROM MdtCnfgCurrencyCodesEntity m WHERE m.currCodeDesc = :currCodeDesc"),
    @NamedQuery(name = "MdtCnfgCurrencyCodesEntity.findByActiveInd", query = "SELECT m FROM MdtCnfgCurrencyCodesEntity m WHERE m.activeInd = :activeInd"),
    @NamedQuery(name = "MdtCnfgCurrencyCodesEntity.findByCreatedBy", query = "SELECT m FROM MdtCnfgCurrencyCodesEntity m WHERE m.createdBy = :createdBy"),
    @NamedQuery(name = "MdtCnfgCurrencyCodesEntity.findByCreatedDate", query = "SELECT m FROM MdtCnfgCurrencyCodesEntity m WHERE m.createdDate = :createdDate"),
    @NamedQuery(name = "MdtCnfgCurrencyCodesEntity.findByModifiedBy", query = "SELECT m FROM MdtCnfgCurrencyCodesEntity m WHERE m.modifiedBy = :modifiedBy"),
    @NamedQuery(name = "MdtCnfgCurrencyCodesEntity.findByModifiedDate", query = "SELECT m FROM MdtCnfgCurrencyCodesEntity m WHERE m.modifiedDate = :modifiedDate")})
public class MdtCnfgCurrencyCodesEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "COUNTRY_CODE")
    private String countryCode;
    @Size(max = 3)
    @Column(name = "ALPHA_CURR_CODE")
    private String alphaCurrCode;
    @Size(max = 3)
    @Column(name = "NUM_CURR_CODE")
    private String numCurrCode;
    @Size(max = 100)
    @Column(name = "CURR_CODE_DESC")
    private String currCodeDesc;
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

    public MdtCnfgCurrencyCodesEntity() {
    }

    public MdtCnfgCurrencyCodesEntity(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getAlphaCurrCode() {
        return alphaCurrCode;
    }

    public void setAlphaCurrCode(String alphaCurrCode) {
        this.alphaCurrCode = alphaCurrCode;
    }

    public String getNumCurrCode() {
        return numCurrCode;
    }

    public void setNumCurrCode(String numCurrCode) {
        this.numCurrCode = numCurrCode;
    }

    public String getCurrCodeDesc() {
        return currCodeDesc;
    }

    public void setCurrCodeDesc(String currCodeDesc) {
        this.currCodeDesc = currCodeDesc;
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
				+ ((alphaCurrCode == null) ? 0 : alphaCurrCode.hashCode());
		result = prime * result
				+ ((countryCode == null) ? 0 : countryCode.hashCode());
		result = prime * result
				+ ((createdBy == null) ? 0 : createdBy.hashCode());
		result = prime * result
				+ ((createdDate == null) ? 0 : createdDate.hashCode());
		result = prime * result
				+ ((currCodeDesc == null) ? 0 : currCodeDesc.hashCode());
		result = prime * result
				+ ((modifiedBy == null) ? 0 : modifiedBy.hashCode());
		result = prime * result
				+ ((modifiedDate == null) ? 0 : modifiedDate.hashCode());
		result = prime * result
				+ ((numCurrCode == null) ? 0 : numCurrCode.hashCode());
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
		MdtCnfgCurrencyCodesEntity other = (MdtCnfgCurrencyCodesEntity) obj;
		if (activeInd == null) {
			if (other.activeInd != null)
				return false;
		} else if (!activeInd.equals(other.activeInd))
			return false;
		if (alphaCurrCode == null) {
			if (other.alphaCurrCode != null)
				return false;
		} else if (!alphaCurrCode.equals(other.alphaCurrCode))
			return false;
		if (countryCode == null) {
			if (other.countryCode != null)
				return false;
		} else if (!countryCode.equals(other.countryCode))
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
		if (currCodeDesc == null) {
			if (other.currCodeDesc != null)
				return false;
		} else if (!currCodeDesc.equals(other.currCodeDesc))
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
		if (numCurrCode == null) {
			if (other.numCurrCode != null)
				return false;
		} else if (!numCurrCode.equals(other.numCurrCode))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MdtCnfgCurrencyCodesEntity [countryCode=" + countryCode
				+ ", alphaCurrCode=" + alphaCurrCode + ", numCurrCode="
				+ numCurrCode + ", currCodeDesc=" + currCodeDesc
				+ ", activeInd=" + activeInd + ", createdBy=" + createdBy
				+ ", createdDate=" + createdDate + ", modifiedBy=" + modifiedBy
				+ ", modifiedDate=" + modifiedDate + "]";
	}
 
    
    
}
