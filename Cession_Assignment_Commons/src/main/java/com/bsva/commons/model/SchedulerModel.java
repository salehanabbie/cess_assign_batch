package com.bsva.commons.model;

import java.io.Serializable;

public class SchedulerModel implements Serializable {
 

	private static final long serialVersionUID = 1L;
	
    private String scheduler;
    private String lastExecutTime;
    private String nextExecutTime;
    private String cron;
    private String status;
    
	public String getScheduler() {
		return scheduler;
	}
	public void setScheduler(String scheduler) {
		this.scheduler = scheduler;
	}
	public String getLastExecutTime() {
		return lastExecutTime;
	}
	public void setLastExecutTime(String lastExecutTime) {
		this.lastExecutTime = lastExecutTime;
	}
	public String getNextExecutTime() {
		return nextExecutTime;
	}
	public void setNextExecutTime(String nextExecutTime) {
		this.nextExecutTime = nextExecutTime;
	}
	public String getCron() {
		return cron;
	}
	public void setCron(String cron) {
		this.cron = cron;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cron == null) ? 0 : cron.hashCode());
		result = prime * result
				+ ((lastExecutTime == null) ? 0 : lastExecutTime.hashCode());
		result = prime * result
				+ ((nextExecutTime == null) ? 0 : nextExecutTime.hashCode());
		result = prime * result
				+ ((scheduler == null) ? 0 : scheduler.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		SchedulerModel other = (SchedulerModel) obj;
		if (cron == null) {
			if (other.cron != null)
				return false;
		} else if (!cron.equals(other.cron))
			return false;
		if (lastExecutTime == null) {
			if (other.lastExecutTime != null)
				return false;
		} else if (!lastExecutTime.equals(other.lastExecutTime))
			return false;
		if (nextExecutTime == null) {
			if (other.nextExecutTime != null)
				return false;
		} else if (!nextExecutTime.equals(other.nextExecutTime))
			return false;
		if (scheduler == null) {
			if (other.scheduler != null)
				return false;
		} else if (!scheduler.equals(other.scheduler))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "SchedulerModel [scheduler=" + scheduler + ", lastExecutTime="
				+ lastExecutTime + ", nextExecutTime=" + nextExecutTime
				+ ", cron=" + cron + ", status=" + status + "]";
	}
    
    

}
