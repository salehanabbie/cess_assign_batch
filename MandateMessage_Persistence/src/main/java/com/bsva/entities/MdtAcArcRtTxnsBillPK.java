package com.bsva.entities;



import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author DimakatsoN
 */
@Embeddable
public class MdtAcArcRtTxnsBillPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "JNL_ID")
    private BigDecimal jnlId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "TRANSMISSION_NO")
    private String transmissionNo;

    public MdtAcArcRtTxnsBillPK() {
    }

    public MdtAcArcRtTxnsBillPK(BigDecimal jnlId, String transmissionNo) {
        this.jnlId = jnlId;
        this.transmissionNo = transmissionNo;
    }
	public BigDecimal getJnlId() {
		return jnlId;
	}

	public void setJnlId(BigDecimal jnlId) {
		this.jnlId = jnlId;
	}

	public String getTransmissionNo() {
		return transmissionNo;
	}

	public void setTransmissionNo(String transmissionNo) {
		this.transmissionNo = transmissionNo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jnlId == null) ? 0 : jnlId.hashCode());
		result = prime * result + ((transmissionNo == null) ? 0 : transmissionNo.hashCode());
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
		MdtAcArcRtTxnsBillPK other = (MdtAcArcRtTxnsBillPK) obj;
		if (jnlId == null) {
			if (other.jnlId != null)
				return false;
		} else if (!jnlId.equals(other.jnlId))
			return false;
		if (transmissionNo == null) {
			if (other.transmissionNo != null)
				return false;
		} else if (!transmissionNo.equals(other.transmissionNo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MdtAcArcRtTxnsBillPK [jnlId=" + jnlId + ", transmissionNo=" + transmissionNo + "]";
	}

   
    

}
