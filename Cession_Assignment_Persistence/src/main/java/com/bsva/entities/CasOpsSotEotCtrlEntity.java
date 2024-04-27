
package com.bsva.entities;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ElelwaniR
 */
@Entity
@Table(name = "CAS_OPS_SOT_EOT_CTRL",schema = "CAMOWNER")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "CasOpsSotEotCtrlEntity.findAll", query = "SELECT m FROM CasOpsSotEotCtrlEntity m"),
	@NamedQuery(name = "CasOpsSotEotCtrlEntity.findByServiceId", query = "SELECT m FROM CasOpsSotEotCtrlEntity m WHERE m.mdtAcOpsSotEotCtrlPK.serviceId = :serviceId"),
	@NamedQuery(name = "CasOpsSotEotCtrlEntity.findByInstId", query = "SELECT m FROM CasOpsSotEotCtrlEntity m WHERE m.mdtAcOpsSotEotCtrlPK.instId = :instId"),
	@NamedQuery(name = "CasOpsSotEotCtrlEntity.findBySotOut", query = "SELECT m FROM CasOpsSotEotCtrlEntity m WHERE m.sotOut = :sotOut"),
	@NamedQuery(name = "CasOpsSotEotCtrlEntity.findByEotOut", query = "SELECT m FROM CasOpsSotEotCtrlEntity m WHERE m.eotOut = :eotOut"),
	@NamedQuery(name = "CasOpsSotEotCtrlEntity.findBySotIn", query = "SELECT m FROM CasOpsSotEotCtrlEntity m WHERE m.sotIn = :sotIn"),
	@NamedQuery(name = "CasOpsSotEotCtrlEntity.findByEotIn", query = "SELECT m FROM CasOpsSotEotCtrlEntity m WHERE m.eotIn = :eotIn")})
public class CasOpsSotEotCtrlEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	protected CasOpsSotEotCtrlPK casOpsSotEotCtrlPK;
	@Size(max = 1)
	@Column(name = "SOT_OUT")
	private String sotOut;
	@Size(max = 1)
	@Column(name = "EOT_OUT")
	private String eotOut;
	@Size(max = 1)
	@Column(name = "SOT_IN")
	private String sotIn;
	@Size(max = 1)
	@Column(name = "EOT_IN")
	private String eotIn;

	public CasOpsSotEotCtrlEntity() {
	}

	public CasOpsSotEotCtrlEntity(CasOpsSotEotCtrlPK casOpsSotEotCtrlPK) {
		this.casOpsSotEotCtrlPK = casOpsSotEotCtrlPK;
	}

	public CasOpsSotEotCtrlEntity(String serviceId, String instId) {
		this.casOpsSotEotCtrlPK = new CasOpsSotEotCtrlPK(serviceId, instId);
	}

	public CasOpsSotEotCtrlPK getCasOpsSotEotCtrlPK() {
		return casOpsSotEotCtrlPK;
	}

	public void setCasOpsSotEotCtrlPK(CasOpsSotEotCtrlPK casOpsSotEotCtrlPK) {
		this.casOpsSotEotCtrlPK = casOpsSotEotCtrlPK;
	}

	public String getSotOut() {
		return sotOut;
	}

	public void setSotOut(String sotOut) {
		this.sotOut = sotOut;
	}

	public String getEotOut() {
		return eotOut;
	}

	public void setEotOut(String eotOut) {
		this.eotOut = eotOut;
	}

	public String getSotIn() {
		return sotIn;
	}

	public void setSotIn(String sotIn) {
		this.sotIn = sotIn;
	}

	public String getEotIn() {
		return eotIn;
	}

	public void setEotIn(String eotIn) {
		this.eotIn = eotIn;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((eotIn == null) ? 0 : eotIn.hashCode());
		result = prime * result + ((eotOut == null) ? 0 : eotOut.hashCode());
		result = prime
				* result
				+ ((casOpsSotEotCtrlPK == null) ? 0 : casOpsSotEotCtrlPK
						.hashCode());
		result = prime * result + ((sotIn == null) ? 0 : sotIn.hashCode());
		result = prime * result + ((sotOut == null) ? 0 : sotOut.hashCode());
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
		CasOpsSotEotCtrlEntity other = (CasOpsSotEotCtrlEntity) obj;
		if (eotIn == null) {
			if (other.eotIn != null)
				return false;
		} else if (!eotIn.equals(other.eotIn))
			return false;
		if (eotOut == null) {
			if (other.eotOut != null)
				return false;
		} else if (!eotOut.equals(other.eotOut))
			return false;
		if (casOpsSotEotCtrlPK == null) {
			if (other.casOpsSotEotCtrlPK != null)
				return false;
		} else if (!casOpsSotEotCtrlPK.equals(other.casOpsSotEotCtrlPK))
			return false;
		if (sotIn == null) {
			if (other.sotIn != null)
				return false;
		} else if (!sotIn.equals(other.sotIn))
			return false;
		if (sotOut == null) {
			if (other.sotOut != null)
				return false;
		} else if (!sotOut.equals(other.sotOut))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CasOpsSotEotCtrlEntity "
				+ "[casAcOpsSotEotCtrlPK="+ casOpsSotEotCtrlPK + "\n" +
				", sotOut=" + sotOut +"\n" +  
				", eotOut="+ eotOut + "\n" + 
				", sotIn=" + sotIn +"\n" + 
				", eotIn=" + eotIn + "]";
	}


}
