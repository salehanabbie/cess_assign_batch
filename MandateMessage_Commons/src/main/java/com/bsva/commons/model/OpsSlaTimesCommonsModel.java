package com.bsva.commons.model;

import java.io.Serializable;

public class OpsSlaTimesCommonsModel implements Serializable{

	/**
	 * @author ElelwaniR
	 */
	private static final long serialVersionUID = 1L;
    private String service;
    private String startTime;
    private String endTime;
    
    
	public OpsSlaTimesCommonsModel()
	{
		super();
	}


	public String getService() {
		return service;
	}


	public void setService(String service) {
		this.service = service;
	}


	public String getStartTime() {
		return startTime;
	}


	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}


	public String getEndTime() {
		return endTime;
	}


	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((endTime == null) ? 0 : endTime.hashCode());
		result = prime * result + ((service == null) ? 0 : service.hashCode());
		result = prime * result
				+ ((startTime == null) ? 0 : startTime.hashCode());
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
		OpsSlaTimesCommonsModel other = (OpsSlaTimesCommonsModel) obj;
		if (endTime == null) {
			if (other.endTime != null)
				return false;
		} else if (!endTime.equals(other.endTime))
			return false;
		if (service == null) {
			if (other.service != null)
				return false;
		} else if (!service.equals(other.service))
			return false;
		if (startTime == null) {
			if (other.startTime != null)
				return false;
		} else if (!startTime.equals(other.startTime))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "OpsSlaTimesCommonsModel [service=" + service + ", startTime="
				+ startTime + ", endTime=" + endTime + "]";
	}
    
    
}
