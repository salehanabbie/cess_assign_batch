
package com.bsva.entities;

import java.io.Serializable;
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
@Table(name = "MDT_CNFG_ADDRESS_TYPE",schema = "MANOWNER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MdtCnfgAddressTypeEntity.findAll", query = "SELECT m FROM MdtCnfgAddressTypeEntity m"),
    @NamedQuery(name = "MdtCnfgAddressTypeEntity.findByAddressType", query = "SELECT m FROM MdtCnfgAddressTypeEntity m WHERE m.addressType = :addressType"),
    @NamedQuery(name = "MdtCnfgAddressTypeEntity.findByAddressTypeDescription", query = "SELECT m FROM MdtCnfgAddressTypeEntity m WHERE m.addressTypeDescription = :addressTypeDescription"),
    @NamedQuery(name = "MdtCnfgAddressTypeEntity.findByActiveInd", query = "SELECT m FROM MdtCnfgAddressTypeEntity m WHERE m.activeInd = :activeInd")})
public class MdtCnfgAddressTypeEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "ADDRESS_TYPE")
    private String addressType;
    @Size(max = 100)
    @Column(name = "ADDRESS_TYPE_DESCRIPTION")
    private String addressTypeDescription;
    @Size(max = 1)
    @Column(name = "ACTIVE_IND")
    private String activeInd;

    public MdtCnfgAddressTypeEntity() {
    }

    public MdtCnfgAddressTypeEntity(String addressType) {
        this.addressType = addressType;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    public String getAddressTypeDescription() {
        return addressTypeDescription;
    }

    public void setAddressTypeDescription(String addressTypeDescription) {
        this.addressTypeDescription = addressTypeDescription;
    }

    public String getActiveInd() {
        return activeInd;
    }

    public void setActiveInd(String activeInd) {
        this.activeInd = activeInd;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (addressType != null ? addressType.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MdtCnfgAddressTypeEntity)) {
            return false;
        }
        MdtCnfgAddressTypeEntity other = (MdtCnfgAddressTypeEntity) object;
        if ((this.addressType == null && other.addressType != null) || (this.addressType != null && !this.addressType.equals(other.addressType))) {
            return false;
        }
        return true;
    }

	@Override
	public String toString() {
		return "MdtCnfgAddressTypeEntity [addressType=" + addressType
				+ ", addressTypeDescription=" + addressTypeDescription
				+ ", activeInd=" + activeInd + "]";
	}

    
    
}
