package com.bsva.models;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;


public class WebCustomerParametersModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String manAmdXsdNs;
	
	private String manCanXsdNs;

	private String manAccpXsdNs;

	private String activeInd;

	private String instId;

	private String manInitXsdNs;

	private String mdtReqIdReuseInd;

	private String mdteReqXsdNs;

	private String mdteRespXsdNs;

	private String manStatusRepXsdNs;

	private String manConfirmXsdNs;

	private Short processDay;
	
	private String cisDwnldInd;
	 
	private Date cisDwnldDate;
	 
	private String createdBy;

	private Date createdDate;

	private String modifiedBy;

	private Date modifiedDate;


	public WebCustomerParametersModel(String instId) {
		// TODO Auto-generated constructor stub
		this.instId = instId;
	}
	public WebCustomerParametersModel() {
		// TODO Auto-generated constructor stub
	}
	public String getManAmdXsdNs() {
		return manAmdXsdNs;
	}
	public void setManAmdXsdNs(String manAmdXsdNs) {
		this.manAmdXsdNs = manAmdXsdNs;
	}
	public String getManCanXsdNs() {
		return manCanXsdNs;
	}
	public void setManCanXsdNs(String manCanXsdNs) {
		this.manCanXsdNs = manCanXsdNs;
	}
	public String getManAccpXsdNs() {
		return manAccpXsdNs;
	}
	public void setManAccpXsdNs(String manAccpXsdNs) {
		this.manAccpXsdNs = manAccpXsdNs;
	}
	public String getActiveInd() {
		return activeInd;
	}
	public void setActiveInd(String activeInd) {
		this.activeInd = activeInd;
	}
	public String getInstId() {
		return instId;
	}
	public void setInstId(String instId) {
		this.instId = instId;
	}
	public String getManInitXsdNs() {
		return manInitXsdNs;
	}
	public void setManInitXsdNs(String manInitXsdNs) {
		this.manInitXsdNs = manInitXsdNs;
	}
	public String getMdtReqIdReuseInd() {
		return mdtReqIdReuseInd;
	}
	public void setMdtReqIdReuseInd(String mdtReqIdReuseInd) {
		this.mdtReqIdReuseInd = mdtReqIdReuseInd;
	}
	public String getMdteReqXsdNs() {
		return mdteReqXsdNs;
	}
	public void setMdteReqXsdNs(String mdteReqXsdNs) {
		this.mdteReqXsdNs = mdteReqXsdNs;
	}
	public String getMdteRespXsdNs() {
		return mdteRespXsdNs;
	}
	public void setMdteRespXsdNs(String mdteRespXsdNs) {
		this.mdteRespXsdNs = mdteRespXsdNs;
	}
	public String getManStatusRepXsdNs() {
		return manStatusRepXsdNs;
	}
	public void setManStatusRepXsdNs(String manStatusRepXsdNs) {
		this.manStatusRepXsdNs = manStatusRepXsdNs;
	}
	public String getManConfirmXsdNs() {
		return manConfirmXsdNs;
	}
	public void setManConfirmXsdNs(String manConfirmXsdNs) {
		this.manConfirmXsdNs = manConfirmXsdNs;
	}
	public Short getProcessDay() {
		return processDay;
	}
	public void setProcessDay(Short processDay) {
		this.processDay = processDay;
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
				+ ((cisDwnldDate == null) ? 0 : cisDwnldDate.hashCode());
		result = prime * result
				+ ((cisDwnldInd == null) ? 0 : cisDwnldInd.hashCode());
		result = prime * result
				+ ((createdBy == null) ? 0 : createdBy.hashCode());
		result = prime * result
				+ ((createdDate == null) ? 0 : createdDate.hashCode());
		result = prime * result + ((instId == null) ? 0 : instId.hashCode());
		result = prime * result
				+ ((manAccpXsdNs == null) ? 0 : manAccpXsdNs.hashCode());
		result = prime * result
				+ ((manAmdXsdNs == null) ? 0 : manAmdXsdNs.hashCode());
		result = prime * result
				+ ((manCanXsdNs == null) ? 0 : manCanXsdNs.hashCode());
		result = prime * result
				+ ((manConfirmXsdNs == null) ? 0 : manConfirmXsdNs.hashCode());
		result = prime * result
				+ ((manInitXsdNs == null) ? 0 : manInitXsdNs.hashCode());
		result = prime
				* result
				+ ((manStatusRepXsdNs == null) ? 0 : manStatusRepXsdNs
						.hashCode());
		result = prime
				* result
				+ ((mdtReqIdReuseInd == null) ? 0 : mdtReqIdReuseInd.hashCode());
		result = prime * result
				+ ((mdteReqXsdNs == null) ? 0 : mdteReqXsdNs.hashCode());
		result = prime * result
				+ ((mdteRespXsdNs == null) ? 0 : mdteRespXsdNs.hashCode());
		result = prime * result
				+ ((modifiedBy == null) ? 0 : modifiedBy.hashCode());
		result = prime * result
				+ ((modifiedDate == null) ? 0 : modifiedDate.hashCode());
		result = prime * result
				+ ((processDay == null) ? 0 : processDay.hashCode());
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
		WebCustomerParametersModel other = (WebCustomerParametersModel) obj;
		if (activeInd == null) {
			if (other.activeInd != null)
				return false;
		} else if (!activeInd.equals(other.activeInd))
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
		if (instId == null) {
			if (other.instId != null)
				return false;
		} else if (!instId.equals(other.instId))
			return false;
		if (manAccpXsdNs == null) {
			if (other.manAccpXsdNs != null)
				return false;
		} else if (!manAccpXsdNs.equals(other.manAccpXsdNs))
			return false;
		if (manAmdXsdNs == null) {
			if (other.manAmdXsdNs != null)
				return false;
		} else if (!manAmdXsdNs.equals(other.manAmdXsdNs))
			return false;
		if (manCanXsdNs == null) {
			if (other.manCanXsdNs != null)
				return false;
		} else if (!manCanXsdNs.equals(other.manCanXsdNs))
			return false;
		if (manConfirmXsdNs == null) {
			if (other.manConfirmXsdNs != null)
				return false;
		} else if (!manConfirmXsdNs.equals(other.manConfirmXsdNs))
			return false;
		if (manInitXsdNs == null) {
			if (other.manInitXsdNs != null)
				return false;
		} else if (!manInitXsdNs.equals(other.manInitXsdNs))
			return false;
		if (manStatusRepXsdNs == null) {
			if (other.manStatusRepXsdNs != null)
				return false;
		} else if (!manStatusRepXsdNs.equals(other.manStatusRepXsdNs))
			return false;
		if (mdtReqIdReuseInd == null) {
			if (other.mdtReqIdReuseInd != null)
				return false;
		} else if (!mdtReqIdReuseInd.equals(other.mdtReqIdReuseInd))
			return false;
		if (mdteReqXsdNs == null) {
			if (other.mdteReqXsdNs != null)
				return false;
		} else if (!mdteReqXsdNs.equals(other.mdteReqXsdNs))
			return false;
		if (mdteRespXsdNs == null) {
			if (other.mdteRespXsdNs != null)
				return false;
		} else if (!mdteRespXsdNs.equals(other.mdteRespXsdNs))
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
		if (processDay == null) {
			if (other.processDay != null)
				return false;
		} else if (!processDay.equals(other.processDay))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "WebCustomerParametersModel [manAmdXsdNs=" + manAmdXsdNs
				+ ", manCanXsdNs=" + manCanXsdNs + ", manAccpXsdNs="
				+ manAccpXsdNs + ", activeInd=" + activeInd + ", instId="
				+ instId + ", manInitXsdNs=" + manInitXsdNs
				+ ", mdtReqIdReuseInd=" + mdtReqIdReuseInd + ", mdteReqXsdNs="
				+ mdteReqXsdNs + ", mdteRespXsdNs=" + mdteRespXsdNs
				+ ", manStatusRepXsdNs=" + manStatusRepXsdNs
				+ ", manConfirmXsdNs=" + manConfirmXsdNs + ", processDay="
				+ processDay + ", cisDwnldInd=" + cisDwnldInd
				+ ", cisDwnldDate=" + cisDwnldDate + ", createdBy=" + createdBy
				+ ", createdDate=" + createdDate + ", modifiedBy=" + modifiedBy
				+ ", modifiedDate=" + modifiedDate + "]";
	}
	
	
	


}






