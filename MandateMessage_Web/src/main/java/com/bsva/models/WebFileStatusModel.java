package com.bsva.models;

import java.io.Serializable;

public class WebFileStatusModel  implements Serializable{

	/**
	 * @author ElelwaniR
	 */
	private static final long serialVersionUID = 1L;
	private String status;
	private String statusDescription;
	private String activeInd;
	 
	 
	public WebFileStatusModel()
	{
		super();
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getStatusDescription() {
		return statusDescription;
	}


	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}


	public String getActiveInd() {
		return activeInd;
	}


	public void setActiveInd(String activeInd) {
		this.activeInd = activeInd;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((activeInd == null) ? 0 : activeInd.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime
				* result
				+ ((statusDescription == null) ? 0 : statusDescription
						.hashCode());
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
		WebFileStatusModel other = (WebFileStatusModel) obj;
		if (activeInd == null) {
			if (other.activeInd != null)
				return false;
		} else if (!activeInd.equals(other.activeInd))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (statusDescription == null) {
			if (other.statusDescription != null)
				return false;
		} else if (!statusDescription.equals(other.statusDescription))
			return false;
		return true;
	}


	@Override
	public String toString()
	{
		return "WebFileStatusModel [status=" + status + ", statusDescription="
				+ statusDescription + ", activeInd=" + activeInd + "]";
	}
	 
	 
	 
}
