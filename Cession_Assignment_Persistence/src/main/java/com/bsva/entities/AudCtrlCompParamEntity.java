package com.bsva.entities;



import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ElelwaniR
 */
@Entity
@Table(name = "AUD_CTRL_COMP_PARAM",schema = "MANOWNER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AudCtrlCompParamEntity.findAll", query = "SELECT a FROM AudCtrlCompParamEntity a"),
    @NamedQuery(name = "AudCtrlCompParamEntity.findByAchId", query = "SELECT a FROM AudCtrlCompParamEntity a WHERE a.achId = :achId"),
    @NamedQuery(name = "AudCtrlCompParamEntity.findByActiveInd", query = "SELECT a FROM AudCtrlCompParamEntity a WHERE a.activeInd = :activeInd"),
    @NamedQuery(name = "AudCtrlCompParamEntity.findByAddress1", query = "SELECT a FROM AudCtrlCompParamEntity a WHERE a.address1 = :address1"),
    @NamedQuery(name = "AudCtrlCompParamEntity.findByAddress2", query = "SELECT a FROM AudCtrlCompParamEntity a WHERE a.address2 = :address2"),
    @NamedQuery(name = "AudCtrlCompParamEntity.findByBicCode", query = "SELECT a FROM AudCtrlCompParamEntity a WHERE a.bicCode = :bicCode"),
    @NamedQuery(name = "AudCtrlCompParamEntity.findByCity", query = "SELECT a FROM AudCtrlCompParamEntity a WHERE a.city = :city"),
    @NamedQuery(name = "AudCtrlCompParamEntity.findByCompAbbrevName", query = "SELECT a FROM AudCtrlCompParamEntity a WHERE a.compAbbrevName = :compAbbrevName"),
    @NamedQuery(name = "AudCtrlCompParamEntity.findByCompId", query = "SELECT a FROM AudCtrlCompParamEntity a WHERE a.compId = :compId"),
    @NamedQuery(name = "AudCtrlCompParamEntity.findByCompName", query = "SELECT a FROM AudCtrlCompParamEntity a WHERE a.compName = :compName"),
    @NamedQuery(name = "AudCtrlCompParamEntity.findByContactName", query = "SELECT a FROM AudCtrlCompParamEntity a WHERE a.contactName = :contactName"),
    @NamedQuery(name = "AudCtrlCompParamEntity.findByDialCode", query = "SELECT a FROM AudCtrlCompParamEntity a WHERE a.dialCode = :dialCode"),
    @NamedQuery(name = "AudCtrlCompParamEntity.findByEmail", query = "SELECT a FROM AudCtrlCompParamEntity a WHERE a.email = :email"),
    @NamedQuery(name = "AudCtrlCompParamEntity.findByFaxCode", query = "SELECT a FROM AudCtrlCompParamEntity a WHERE a.faxCode = :faxCode"),
    @NamedQuery(name = "AudCtrlCompParamEntity.findByFaxNr", query = "SELECT a FROM AudCtrlCompParamEntity a WHERE a.faxNr = :faxNr"),
    @NamedQuery(name = "AudCtrlCompParamEntity.findByPostCode", query = "SELECT a FROM AudCtrlCompParamEntity a WHERE a.postCode = :postCode"),
    @NamedQuery(name = "AudCtrlCompParamEntity.findByRegistrationNr", query = "SELECT a FROM AudCtrlCompParamEntity a WHERE a.registrationNr = :registrationNr"),
    @NamedQuery(name = "AudCtrlCompParamEntity.findByTelNr", query = "SELECT a FROM AudCtrlCompParamEntity a WHERE a.telNr = :telNr")})
public class AudCtrlCompParamEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Size(max = 3)
    @Column(name = "ACH_ID")
    private String achId;
    @Size(max = 1)
    @Column(name = "ACTIVE_IND")
    private String activeInd;
    @Size(max = 35)
    @Column(name = "ADDRESS_1")
    private String address1;
    @Size(max = 35)
    @Column(name = "ADDRESS_2")
    private String address2;
    @Size(max = 11)
    @Column(name = "BIC_CODE")
    private String bicCode;
    @Size(max = 35)
    @Column(name = "CITY")
    private String city;
    @Size(max = 5)
    @Column(name = "COMP_ABBREV_NAME")
    private String compAbbrevName;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "COMP_ID")
    private BigDecimal compId;
    @Size(max = 35)
    @Column(name = "COMP_NAME")
    private String compName;
    @Size(max = 50)
    @Column(name = "CONTACT_NAME")
    private String contactName;
    @Size(max = 8)
    @Column(name = "DIAL_CODE")
    private String dialCode;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 50)
    @Column(name = "EMAIL")
    private String email;
    @Size(max = 8)
    @Column(name = "FAX_CODE")
    private String faxCode;
    @Size(max = 10)
    @Column(name = "FAX_NR")
    private String faxNr;
    @Size(max = 35)
    @Column(name = "POST_CODE")
    private String postCode;
    @Size(max = 20)
    @Column(name = "REGISTRATION_NR")
    private String registrationNr;
    @Size(max = 10)
    @Column(name = "TEL_NR")
    private String telNr;

    public AudCtrlCompParamEntity() {
    }

    public AudCtrlCompParamEntity(BigDecimal compId) {
        this.compId = compId;
    }

    public String getAchId() {
        return achId;
    }

    public void setAchId(String achId) {
        this.achId = achId;
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

    public String getBicCode() {
        return bicCode;
    }

    public void setBicCode(String bicCode) {
        this.bicCode = bicCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCompAbbrevName() {
        return compAbbrevName;
    }

    public void setCompAbbrevName(String compAbbrevName) {
        this.compAbbrevName = compAbbrevName;
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

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getDialCode() {
        return dialCode;
    }

    public void setDialCode(String dialCode) {
        this.dialCode = dialCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getRegistrationNr() {
        return registrationNr;
    }

    public void setRegistrationNr(String registrationNr) {
        this.registrationNr = registrationNr;
    }

    public String getTelNr() {
        return telNr;
    }

    public void setTelNr(String telNr) {
        this.telNr = telNr;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (compId != null ? compId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AudCtrlCompParamEntity)) {
            return false;
        }
        AudCtrlCompParamEntity other = (AudCtrlCompParamEntity) object;
        if ((this.compId == null && other.compId != null) || (this.compId != null && !this.compId.equals(other.compId))) {
            return false;
        }
        return true;
    }

	@Override
	public String toString() {
		return "AudCtrlCompParamEntity [achId=" + achId + ", activeInd="
				+ activeInd + ", address1=" + address1 + ", address2="
				+ address2 + ", bicCode=" + bicCode + ", city=" + city
				+ ", compAbbrevName=" + compAbbrevName + ", compId=" + compId
				+ ", compName=" + compName + ", contactName=" + contactName
				+ ", dialCode=" + dialCode + ", email=" + email + ", faxCode="
				+ faxCode + ", faxNr=" + faxNr + ", postCode=" + postCode
				+ ", registrationNr=" + registrationNr + ", telNr=" + telNr
				+ "]";
	}

}
