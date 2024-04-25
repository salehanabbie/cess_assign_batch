package com.bsva.models;

import java.io.Serializable;
/**
 * 
 * @author DimakatsoN
 *
 */

public class WebOpsFinInstIdModel implements Serializable {


    private String mandateReqId;
    
    private String finInstTypeId;

	public String getMandateReqId() {
		return mandateReqId;
	}

	public void setMandateReqId(String mandateReqId) {
		this.mandateReqId = mandateReqId;
	}

	public String getFinInstTypeId() {
		return finInstTypeId;
	}

	public void setFinInstTypeId(String finInstTypeId) {
		this.finInstTypeId = finInstTypeId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((finInstTypeId == null) ? 0 : finInstTypeId.hashCode());
		result = prime * result
				+ ((mandateReqId == null) ? 0 : mandateReqId.hashCode());
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
		WebOpsFinInstIdModel other = (WebOpsFinInstIdModel) obj;
		if (finInstTypeId == null) {
			if (other.finInstTypeId != null)
				return false;
		} else if (!finInstTypeId.equals(other.finInstTypeId))
			return false;
		if (mandateReqId == null) {
			if (other.mandateReqId != null)
				return false;
		} else if (!mandateReqId.equals(other.mandateReqId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "WebOpsFinInstIdModel [mandateReqId=" + mandateReqId
				+ ", finInstTypeId=" + finInstTypeId + "]";
	}
    
    
	

}
