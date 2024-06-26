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
 * @author DimakatsoN
 */
@Entity
@Table(name = "CAS_CNFG_AUTH_TYPE",schema = "CAMOWNER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CasCnfgAuthTypeEntity.findAll", query = "SELECT m FROM CasCnfgAuthTypeEntity m"),
    @NamedQuery(name = "CasCnfgAuthTypeEntity.findByAuthType", query = "SELECT m FROM CasCnfgAuthTypeEntity m WHERE m.authType = :authType"),
    @NamedQuery(name = "CasCnfgAuthTypeEntity.findByAuthTypeDescription", query = "SELECT m FROM CasCnfgAuthTypeEntity m WHERE m.authTypeDescription = :authTypeDescription"),
    @NamedQuery(name = "CasCnfgAuthTypeEntity.findByActiveInd", query = "SELECT m FROM CasCnfgAuthTypeEntity m WHERE m.activeInd = :activeInd")})
public class CasCnfgAuthTypeEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "AUTH_TYPE")
    private String authType;
    @Size(max = 100)
    @Column(name = "AUTH_TYPE_DESCRIPTION")
    private String authTypeDescription;
    @Size(max = 1)
    @Column(name = "ACTIVE_IND")
    private String activeInd;

    public CasCnfgAuthTypeEntity() {
    }

    public CasCnfgAuthTypeEntity(String authType) {
        this.authType = authType;
    }

    public String getAuthType() {
        return authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }

    public String getAuthTypeDescription() {
        return authTypeDescription;
    }

    public void setAuthTypeDescription(String authTypeDescription) {
        this.authTypeDescription = authTypeDescription;
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
        hash += (authType != null ? authType.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CasCnfgAuthTypeEntity)) {
            return false;
        }
        CasCnfgAuthTypeEntity other = (CasCnfgAuthTypeEntity) object;
        if ((this.authType == null && other.authType != null) || (this.authType != null && !this.authType.equals(other.authType))) {
            return false;
        }
        return true;
    }

	@Override
	public String toString() {
		return "CasCnfgAuthTypeEntity [authType=" + authType
				+ ", authTypeDescription=" + authTypeDescription
				+ ", activeInd=" + activeInd + "]";
	}

 
    
    
}
