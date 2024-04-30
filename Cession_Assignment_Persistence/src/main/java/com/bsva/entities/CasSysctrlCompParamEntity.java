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
 * @author ElelwaniR
 */
@Entity
@Table(name = "CAS_SYSCTRL_COMP_PARAM",schema = "CAMOWNER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CasSysctrlCompParamEntity.findAll", query = "SELECT m FROM CasSysctrlCompParamEntity m"),
    @NamedQuery(name = "CasSysctrlCompParamEntity.findByCompId", query = "SELECT m FROM CasSysctrlCompParamEntity m WHERE m.compId = :compId"),
    @NamedQuery(name = "CasSysctrlCompParamEntity.findByCompName", query = "SELECT m FROM CasSysctrlCompParamEntity m WHERE m.compName = :compName"),
    @NamedQuery(name = "CasSysctrlCompParamEntity.findByCompAbbrevName", query = "SELECT m FROM CasSysctrlCompParamEntity m WHERE m.compAbbrevName = :compAbbrevName"),
    @NamedQuery(name = "CasSysctrlCompParamEntity.findByRegistrationNr", query = "SELECT m FROM CasSysctrlCompParamEntity m WHERE m.registrationNr = :registrationNr"),
    @NamedQuery(name = "CasSysctrlCompParamEntity.findByActiveInd", query = "SELECT m FROM CasSysctrlCompParamEntity m WHERE m.activeInd = :activeInd"),
    @NamedQuery(name = "CasSysctrlCompParamEntity.findByAddress1", query = "SELECT m FROM CasSysctrlCompParamEntity m WHERE m.address1 = :address1"),
    @NamedQuery(name = "CasSysctrlCompParamEntity.findByAddress2", query = "SELECT m FROM CasSysctrlCompParamEntity m WHERE m.address2 = :address2"),
    @NamedQuery(name = "CasSysctrlCompParamEntity.findByCity", query = "SELECT m FROM CasSysctrlCompParamEntity m WHERE m.city = :city"),
    @NamedQuery(name = "CasSysctrlCompParamEntity.findByPostCode", query = "SELECT m FROM CasSysctrlCompParamEntity m WHERE m.postCode = :postCode"),
    @NamedQuery(name = "CasSysctrlCompParamEntity.findByDialCode", query = "SELECT m FROM CasSysctrlCompParamEntity m WHERE m.dialCode = :dialCode"),
    @NamedQuery(name = "CasSysctrlCompParamEntity.findByTelNr", query = "SELECT m FROM CasSysctrlCompParamEntity m WHERE m.telNr = :telNr"),
    @NamedQuery(name = "CasSysctrlCompParamEntity.findByFaxCode", query = "SELECT m FROM CasSysctrlCompParamEntity m WHERE m.faxCode = :faxCode"),
    @NamedQuery(name = "CasSysctrlCompParamEntity.findByFaxNr", query = "SELECT m FROM CasSysctrlCompParamEntity m WHERE m.faxNr = :faxNr"),
    @NamedQuery(name = "CasSysctrlCompParamEntity.findByEmail", query = "SELECT m FROM CasSysctrlCompParamEntity m WHERE m.email = :email"),
    @NamedQuery(name = "CasSysctrlCompParamEntity.findByContactName", query = "SELECT m FROM CasSysctrlCompParamEntity m WHERE m.contactName = :contactName"),
    @NamedQuery(name = "CasSysctrlCompParamEntity.findByLastFileNr", query = "SELECT m FROM CasSysctrlCompParamEntity m WHERE m.lastFileNr = :lastFileNr"),
    @NamedQuery(name = "CasSysctrlCompParamEntity.findByTransamissionInd", query = "SELECT m FROM CasSysctrlCompParamEntity m WHERE m.transamissionInd = :transamissionInd"),
    @NamedQuery(name = "CasSysctrlCompParamEntity.findByAchInstId", query = "SELECT m FROM CasSysctrlCompParamEntity m WHERE m.achInstId = :achInstId"),
    @NamedQuery(name = "CasSysctrlCompParamEntity.findByAchId", query = "SELECT m FROM CasSysctrlCompParamEntity m WHERE m.achId = :achId"),
    @NamedQuery(name = "CasSysctrlCompParamEntity.findByCreatedBy", query = "SELECT m FROM CasSysctrlCompParamEntity m WHERE m.createdBy = :createdBy"),
    @NamedQuery(name = "CasSysctrlCompParamEntity.findByCreatedDate", query = "SELECT m FROM CasSysctrlCompParamEntity m WHERE m.createdDate = :createdDate"),
    @NamedQuery(name = "CasSysctrlCompParamEntity.findByModifiedBy", query = "SELECT m FROM CasSysctrlCompParamEntity m WHERE m.modifiedBy = :modifiedBy"),
    @NamedQuery(name = "CasSysctrlCompParamEntity.findByModifiedDate", query = "SELECT m FROM CasSysctrlCompParamEntity m WHERE m.modifiedDate = :modifiedDate")})
public class CasSysctrlCompParamEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "COMP_ID")
    private BigDecimal compId;
    @Size(max = 50)
    @Column(name = "COMP_NAME")
    private String compName;
    @Size(max = 5)
    @Column(name = "COMP_ABBREV_NAME")
    private String compAbbrevName;
    @Size(max = 20)
    @Column(name = "REGISTRATION_NR")
    private String registrationNr;
    @Size(max = 1)
    @Column(name = "ACTIVE_IND")
    private String activeInd;
    @Size(max = 35)
    @Column(name = "ADDRESS_1")
    private String address1;
    @Size(max = 35)
    @Column(name = "ADDRESS_2")
    private String address2;
    @Size(max = 35)
    @Column(name = "CITY")
    private String city;
    @Size(max = 35)
    @Column(name = "POST_CODE")
    private String postCode;
    @Size(max = 8)
    @Column(name = "DIAL_CODE")
    private String dialCode;
    @Size(max = 10)
    @Column(name = "TEL_NR")
    private String telNr;
    @Size(max = 8)
    @Column(name = "FAX_CODE")
    private String faxCode;
    @Size(max = 10)
    @Column(name = "FAX_NR")
    private String faxNr;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 50)
    @Column(name = "EMAIL")
    private String email;
    @Size(max = 50)
    @Column(name = "CONTACT_NAME")
    private String contactName;
    @Size(max = 8)
    @Column(name = "LAST_FILE_NR")
    private String lastFileNr;
    @Size(max = 1)
    @Column(name = "TRANSAMISSION_IND")
    private String transamissionInd;
    @Size(max = 11)
    @Column(name = "ACH_INST_ID")
    private String achInstId;
    @Size(max = 3)
    @Column(name = "ACH_ID")
    private String achId;
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

    public CasSysctrlCompParamEntity() {
    }

    public CasSysctrlCompParamEntity(BigDecimal compId) {
        this.compId = compId;
    }

    public BigDecimal getCompId() {
        return compId;
    }

    public void setCompId(BigDecimal compId) {
        this.compId = compId;
    }

    public String getCompName() {
        return compName;
    }

    public void setCompName(String compName) {
        this.compName = compName;
    }

    public String getCompAbbrevName() {
        return compAbbrevName;
    }

    public void setCompAbbrevName(String compAbbrevName) {
        this.compAbbrevName = compAbbrevName;
    }

    public String getRegistrationNr() {
        return registrationNr;
    }

    public void setRegistrationNr(String registrationNr) {
        this.registrationNr = registrationNr;
    }

    public String getActiveInd() {
        return activeInd;
    }

    public void setActiveInd(String activeInd) {
        this.activeInd = activeInd;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getDialCode() {
        return dialCode;
    }

    public void setDialCode(String dialCode) {
        this.dialCode = dialCode;
    }

    public String getTelNr() {
        return telNr;
    }

    public void setTelNr(String telNr) {
        this.telNr = telNr;
    }

    public String getFaxCode() {
        return faxCode;
    }

    public void setFaxCode(String faxCode) {
        this.faxCode = faxCode;
    }

    public String getFaxNr() {
        return faxNr;
    }

    public void setFaxNr(String faxNr) {
        this.faxNr = faxNr;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getLastFileNr() {
        return lastFileNr;
    }

    public void setLastFileNr(String lastFileNr) {
        this.lastFileNr = lastFileNr;
    }

    public String getTransamissionInd() {
        return transamissionInd;
    }

    public void setTransamissionInd(String transamissionInd) {
        this.transamissionInd = transamissionInd;
    }

    public String getAchInstId() {
        return achInstId;
    }

    public void setAchInstId(String achInstId) {
        this.achInstId = achInstId;
    }

    public String getAchId() {
        return achId;
    }

    public void setAchId(String achId) {
        this.achId = achId;
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
		result = prime * result + ((achId == null) ? 0 : achId.hashCode());
		result = prime * result
				+ ((achInstId == null) ? 0 : achInstId.hashCode());
		result = prime * result
				+ ((activeInd == null) ? 0 : activeInd.hashCode());
		result = prime * result
				+ ((address1 == null) ? 0 : address1.hashCode());
		result = prime * result
				+ ((address2 == null) ? 0 : address2.hashCode());
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result
				+ ((compAbbrevName == null) ? 0 : compAbbrevName.hashCode());
		result = prime * result + ((compId == null) ? 0 : compId.hashCode());
		result = prime * result
				+ ((compName == null) ? 0 : compName.hashCode());
		result = prime * result
				+ ((contactName == null) ? 0 : contactName.hashCode());
		result = prime * result
				+ ((createdBy == null) ? 0 : createdBy.hashCode());
		result = prime * result
				+ ((createdDate == null) ? 0 : createdDate.hashCode());
		result = prime * result
				+ ((dialCode == null) ? 0 : dialCode.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((faxCode == null) ? 0 : faxCode.hashCode());
		result = prime * result + ((faxNr == null) ? 0 : faxNr.hashCode());
		result = prime * result
				+ ((lastFileNr == null) ? 0 : lastFileNr.hashCode());
		result = prime * result
				+ ((modifiedBy == null) ? 0 : modifiedBy.hashCode());
		result = prime * result
				+ ((modifiedDate == null) ? 0 : modifiedDate.hashCode());
		result = prime * result
				+ ((postCode == null) ? 0 : postCode.hashCode());
		result = prime * result
				+ ((registrationNr == null) ? 0 : registrationNr.hashCode());
		result = prime * result + ((telNr == null) ? 0 : telNr.hashCode());
		result = prime
				* result
				+ ((transamissionInd == null) ? 0 : transamissionInd.hashCode());
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
		CasSysctrlCompParamEntity other = (CasSysctrlCompParamEntity) obj;
		if (achId == null) {
			if (other.achId != null)
				return false;
		} else if (!achId.equals(other.achId))
			return false;
		if (achInstId == null) {
			if (other.achInstId != null)
				return false;
		} else if (!achInstId.equals(other.achInstId))
			return false;
		if (activeInd == null) {
			if (other.activeInd != null)
				return false;
		} else if (!activeInd.equals(other.activeInd))
			return false;
		if (address1 == null) {
			if (other.address1 != null)
				return false;
		} else if (!address1.equals(other.address1))
			return false;
		if (address2 == null) {
			if (other.address2 != null)
				return false;
		} else if (!address2.equals(other.address2))
			return false;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (compAbbrevName == null) {
			if (other.compAbbrevName != null)
				return false;
		} else if (!compAbbrevName.equals(other.compAbbrevName))
			return false;
		if (compId == null) {
			if (other.compId != null)
				return false;
		} else if (!compId.equals(other.compId))
			return false;
		if (compName == null) {
			if (other.compName != null)
				return false;
		} else if (!compName.equals(other.compName))
			return false;
		if (contactName == null) {
			if (other.contactName != null)
				return false;
		} else if (!contactName.equals(other.contactName))
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
		if (dialCode == null) {
			if (other.dialCode != null)
				return false;
		} else if (!dialCode.equals(other.dialCode))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (faxCode == null) {
			if (other.faxCode != null)
				return false;
		} else if (!faxCode.equals(other.faxCode))
			return false;
		if (faxNr == null) {
			if (other.faxNr != null)
				return false;
		} else if (!faxNr.equals(other.faxNr))
			return false;
		if (lastFileNr == null) {
			if (other.lastFileNr != null)
				return false;
		} else if (!lastFileNr.equals(other.lastFileNr))
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
		if (postCode == null) {
			if (other.postCode != null)
				return false;
		} else if (!postCode.equals(other.postCode))
			return false;
		if (registrationNr == null) {
			if (other.registrationNr != null)
				return false;
		} else if (!registrationNr.equals(other.registrationNr))
			return false;
		if (telNr == null) {
			if (other.telNr != null)
				return false;
		} else if (!telNr.equals(other.telNr))
			return false;
		if (transamissionInd == null) {
			if (other.transamissionInd != null)
				return false;
		} else if (!transamissionInd.equals(other.transamissionInd))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CasSysctrlCompParamEntity [compId=" + compId + ", compName="
				+ compName + ", compAbbrevName=" + compAbbrevName
				+ ", registrationNr=" + registrationNr + ", activeInd="
				+ activeInd + ", address1=" + address1 + ", address2="
				+ address2 + ", city=" + city + ", postCode=" + postCode
				+ ", dialCode=" + dialCode + ", telNr=" + telNr + ", faxCode="
				+ faxCode + ", faxNr=" + faxNr + ", email=" + email
				+ ", contactName=" + contactName + ", lastFileNr=" + lastFileNr
				+ ", transamissionInd=" + transamissionInd + ", achInstId="
				+ achInstId + ", achId=" + achId + ", createdBy=" + createdBy
				+ ", createdDate=" + createdDate + ", modifiedBy=" + modifiedBy
				+ ", modifiedDate=" + modifiedDate + "]";
	}

	

 
    
}


    
