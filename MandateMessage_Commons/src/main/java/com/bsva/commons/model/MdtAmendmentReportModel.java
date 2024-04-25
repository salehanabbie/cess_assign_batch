package com.bsva.commons.model;

import java.io.Serializable;

/***
 * 
 * @author DimakatsoN
 *
 */

public class MdtAmendmentReportModel implements Serializable {
	
	
	private String creditorName;
	private int countMD16;
	private int countMD17;
	private int countMD19;
	private int countMD20;
	private int countMS02;
	public String getCreditorName() {
		return creditorName;
	}
	public void setCreditorName(String creditorName) {
		this.creditorName = creditorName;
	}
	public int getCountMD16() {
		return countMD16;
	}
	public void setCountMD16(int countMD16) {
		this.countMD16 = countMD16;
	}
	public int getCountMD17() {
		return countMD17;
	}
	public void setCountMD17(int countMD17) {
		this.countMD17 = countMD17;
	}
	public int getCountMD19() {
		return countMD19;
	}
	public void setCountMD19(int countMD19) {
		this.countMD19 = countMD19;
	}
	public int getCountMD20() {
		return countMD20;
	}
	public void setCountMD20(int countMD20) {
		this.countMD20 = countMD20;
	}
	public int getCountMS02() {
		return countMS02;
	}
	public void setCountMS02(int countMS02) {
		this.countMS02 = countMS02;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + countMD16;
		result = prime * result + countMD17;
		result = prime * result + countMD19;
		result = prime * result + countMD20;
		result = prime * result + countMS02;
		result = prime * result
				+ ((creditorName == null) ? 0 : creditorName.hashCode());
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
		MdtAmendmentReportModel other = (MdtAmendmentReportModel) obj;
		if (countMD16 != other.countMD16)
			return false;
		if (countMD17 != other.countMD17)
			return false;
		if (countMD19 != other.countMD19)
			return false;
		if (countMD20 != other.countMD20)
			return false;
		if (countMS02 != other.countMS02)
			return false;
		if (creditorName == null) {
			if (other.creditorName != null)
				return false;
		} else if (!creditorName.equals(other.creditorName))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "MdtAmendmentReportModel [creditorName=" + creditorName
				+ ", countMD16=" + countMD16 + ", countMD17=" + countMD17
				+ ", countMD19=" + countMD19 + ", countMD20=" + countMD20
				+ ", countMS02=" + countMS02 + "]";
	}
	
	
	

}
