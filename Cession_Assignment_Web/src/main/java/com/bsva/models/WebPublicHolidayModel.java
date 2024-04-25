package com.bsva.models;

import java.io.Serializable;

import java.util.Date;

public class WebPublicHolidayModel implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Date pubHolDate;
	private String pubHolidayDesc;
	private String activeInd;
	

	public WebPublicHolidayModel() {
		
	}



	public WebPublicHolidayModel(String pubHolidayDesc){
		this.pubHolidayDesc = pubHolidayDesc;
		
	}


	public Date getPubHolDate() {
		return pubHolDate;
	}


	public void setPubHolDate(Date pubHolDate) {
		this.pubHolDate = pubHolDate;
	}


	public String getPubHolidayDesc() {
		return pubHolidayDesc;
	}


	public void setPubHolidayDesc(String pubHolidayDesc) {
		this.pubHolidayDesc = pubHolidayDesc;
	}


	public String getActiveInd() {
		return activeInd;
	}


	public void setActiveInd(String activeInd) {
		this.activeInd = activeInd;
	}


	@Override
	public String toString() {
		return "WebPublicHolidayModel [pubHolDate=" + pubHolDate
				+ ", pubHolidayDesc=" + pubHolidayDesc + ", activeInd="
				+ activeInd + "]";
	}
	
	
	
}
