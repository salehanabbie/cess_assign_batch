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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author SalehaR
 */
@Entity
@Table(name = "MDT_PROCESS_CONTROL",schema = "MANOWNER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MdtProcessControlEntity.findAll", query = "SELECT m FROM MdtProcessControlEntity m"),
    @NamedQuery(name = "MdtProcessControlEntity.findByLastSeq", query = "SELECT m FROM MdtProcessControlEntity m WHERE m.lastSeq = :lastSeq")})
public class MdtProcessControlEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "LAST_SEQ")
    private Integer lastSeq;

    public MdtProcessControlEntity() {
    }

    public MdtProcessControlEntity(Integer lastSeq) {
        this.lastSeq = lastSeq;
    }

    public Integer getLastSeq() {
        return lastSeq;
    }

    public void setLastSeq(Integer lastSeq) {
        this.lastSeq = lastSeq;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (lastSeq != null ? lastSeq.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MdtProcessControlEntity)) {
            return false;
        }
        MdtProcessControlEntity other = (MdtProcessControlEntity) object;
        if ((this.lastSeq == null && other.lastSeq != null) || (this.lastSeq != null && !this.lastSeq.equals(other.lastSeq))) {
            return false;
        }
        return true;
    }

	@Override
	public String toString() {
		return "MdtProcessControlEntity [lastSeq=" + lastSeq + "]";
	}

    
    
}
