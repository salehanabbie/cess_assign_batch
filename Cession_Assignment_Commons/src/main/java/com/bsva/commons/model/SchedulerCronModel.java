package com.bsva.commons.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author SalehaR
 *
 */
public class SchedulerCronModel implements Serializable
{
		private BigDecimal systemSeqNo;
	    private String schedulerCronInterval;
	    private String cronValue;
	    private String activeInd;
	    
		public BigDecimal getSystemSeqNo() {
			return systemSeqNo;
		}
		public void setSystemSeqNo(BigDecimal systemSeqNo) {
			this.systemSeqNo = systemSeqNo;
		}
		public String getSchedulerCronInterval() {
			return schedulerCronInterval;
		}
		public void setSchedulerCronInterval(String schedulerCronInterval) {
			this.schedulerCronInterval = schedulerCronInterval;
		}
		public String getCronValue() {
			return cronValue;
		}
		public void setCronValue(String cronValue) {
			this.cronValue = cronValue;
		}
		public String getActiveInd() {
			return activeInd;
		}
		public void setActiveInd(String activeInd) {
			this.activeInd = activeInd;
		}
		
		
		@Override
		public String toString() {
			return "SchedulerCronModel [systemSeqNo=" + systemSeqNo
					+ ", schedulerCronInterval=" + schedulerCronInterval
					+ ", cronValue=" + cronValue + ", activeInd=" + activeInd
					+ "]";
		}

	
}
