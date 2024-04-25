package com.bsva.models;

import java.io.Serializable;

public class WebQuartzSchedulerModel  implements Serializable{

	/**
	 * @author ElelwaniR
	 */
	private static final long serialVersionUID = 1L;
	  private String schedulerKey;
	  private String schedulerName;
	  private String rescheduleTime;
	  private String activeInd;
	  
	  
	public WebQuartzSchedulerModel() 
	{
		super();
	}

	public WebQuartzSchedulerModel(String schedulerName) 
	{
	
	}
	public String getSchedulerKey() {
		return schedulerKey;
	}


	public void setSchedulerKey(String schedulerKey) {
		this.schedulerKey = schedulerKey;
	}


	public String getSchedulerName() {
		return schedulerName;
	}


	public void setSchedulerName(String schedulerName) {
		this.schedulerName = schedulerName;
	}


	public String getRescheduleTime() {
		return rescheduleTime;
	}


	public void setRescheduleTime(String rescheduleTime) {
		this.rescheduleTime = rescheduleTime;
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
		result = prime * result
				+ ((rescheduleTime == null) ? 0 : rescheduleTime.hashCode());
		result = prime * result
				+ ((schedulerKey == null) ? 0 : schedulerKey.hashCode());
		result = prime * result
				+ ((schedulerName == null) ? 0 : schedulerName.hashCode());
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
		WebQuartzSchedulerModel other = (WebQuartzSchedulerModel) obj;
		if (activeInd == null) {
			if (other.activeInd != null)
				return false;
		} else if (!activeInd.equals(other.activeInd))
			return false;
		if (rescheduleTime == null) {
			if (other.rescheduleTime != null)
				return false;
		} else if (!rescheduleTime.equals(other.rescheduleTime))
			return false;
		if (schedulerKey == null) {
			if (other.schedulerKey != null)
				return false;
		} else if (!schedulerKey.equals(other.schedulerKey))
			return false;
		if (schedulerName == null) {
			if (other.schedulerName != null)
				return false;
		} else if (!schedulerName.equals(other.schedulerName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return schedulerName;
	}


	
	  
	  
}
