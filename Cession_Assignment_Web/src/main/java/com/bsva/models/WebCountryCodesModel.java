
package com.bsva.models;

import java.io.Serializable;


/**
 * @author salehas
 *
 */
public class WebCountryCodesModel implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String countryCode;
	private String alphaCurrCode;
	private String numCurrCode;
	private String currCodeDesc;
	private String activeInd;

	public WebCountryCodesModel(String countryCode) {
		this.countryCode = countryCode;
	}

	public WebCountryCodesModel() {

	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getAlphaCurrCode() {
		return alphaCurrCode;
	}

	public void setAlphaCurrCode(String alphaCurrCode) {
		this.alphaCurrCode = alphaCurrCode;
	}

	public String getNumCurrCode() {
		return numCurrCode;
	}

	public void setNumCurrCode(String numCurrCode) {
		this.numCurrCode = numCurrCode;
	}

	public String getCurrCodeDesc() {
		return currCodeDesc;
	}

	public void setCurrCodeDesc(String currCodeDesc) {
		this.currCodeDesc = currCodeDesc;
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
				+ ((alphaCurrCode == null) ? 0 : alphaCurrCode.hashCode());
		result = prime * result
				+ ((countryCode == null) ? 0 : countryCode.hashCode());
		result = prime * result
				+ ((currCodeDesc == null) ? 0 : currCodeDesc.hashCode());
		result = prime * result
				+ ((numCurrCode == null) ? 0 : numCurrCode.hashCode());
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
		WebCountryCodesModel other = (WebCountryCodesModel) obj;
		if (activeInd == null) {
			if (other.activeInd != null)
				return false;
		} else if (!activeInd.equals(other.activeInd))
			return false;
		if (alphaCurrCode == null) {
			if (other.alphaCurrCode != null)
				return false;
		} else if (!alphaCurrCode.equals(other.alphaCurrCode))
			return false;
		if (countryCode == null) {
			if (other.countryCode != null)
				return false;
		} else if (!countryCode.equals(other.countryCode))
			return false;
		if (currCodeDesc == null) {
			if (other.currCodeDesc != null)
				return false;
		} else if (!currCodeDesc.equals(other.currCodeDesc))
			return false;
		if (numCurrCode == null) {
			if (other.numCurrCode != null)
				return false;
		} else if (!numCurrCode.equals(other.numCurrCode))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return countryCode;
	}

	
	
	

}