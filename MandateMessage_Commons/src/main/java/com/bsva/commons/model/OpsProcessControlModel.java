package com.bsva.commons.model;

import java.io.Serializable;
import java.util.Date;


/**
 * 
 * @author DimakatsoN
 *
 */
public class OpsProcessControlModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	   private Date processDate;
	   private String cisDownloadInd;
	   
	   
	public OpsProcessControlModel() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Date getProcessDate() {
		return processDate;
	}


	public void setProcessDate(Date processDate) {
		this.processDate = processDate;
	}


	public String getCisDownloadInd() {
		return cisDownloadInd;
	}


	public void setCisDownloadInd(String cisDownloadInd) {
		this.cisDownloadInd = cisDownloadInd;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((cisDownloadInd == null) ? 0 : cisDownloadInd.hashCode());
		result = prime * result
				+ ((processDate == null) ? 0 : processDate.hashCode());
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
		OpsProcessControlModel other = (OpsProcessControlModel) obj;
		if (cisDownloadInd == null) {
			if (other.cisDownloadInd != null)
				return false;
		} else if (!cisDownloadInd.equals(other.cisDownloadInd))
			return false;
		if (processDate == null) {
			if (other.processDate != null)
				return false;
		} else if (!processDate.equals(other.processDate))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "[processDate=" + processDate +", cisDownloadInd=" + cisDownloadInd + "]";
	}
	
	
	   
	   

}
