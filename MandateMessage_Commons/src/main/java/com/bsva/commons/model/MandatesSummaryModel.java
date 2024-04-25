package com.bsva.commons.model;

import java.io.Serializable;

public class MandatesSummaryModel implements Serializable{

	private String finInstName;
	private int NrofMandates;
	private double valofMandates;
	public String getFinInstName() {
		return finInstName;
	}
	public void setFinInstName(String finInstName) {
		this.finInstName = finInstName;
	}
	public int getNrofMandates() {
		return NrofMandates;
	}
	public void setNrofMandates(int nrofMandates) {
		NrofMandates = nrofMandates;
	}
	public double getValofMandates() {
		return valofMandates;
	}
	public void setValofMandates(double valofMandates) {
		this.valofMandates = valofMandates;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + NrofMandates;
		result = prime * result
				+ ((finInstName == null) ? 0 : finInstName.hashCode());
		long temp;
		temp = Double.doubleToLongBits(valofMandates);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		MandatesSummaryModel other = (MandatesSummaryModel) obj;
		if (NrofMandates != other.NrofMandates)
			return false;
		if (finInstName == null) {
			if (other.finInstName != null)
				return false;
		} else if (!finInstName.equals(other.finInstName))
			return false;
		if (Double.doubleToLongBits(valofMandates) != Double
				.doubleToLongBits(other.valofMandates))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "MandatesSummaryModel [finInstName=" + finInstName
				+ ", NrofMandates=" + NrofMandates + ", valofMandates="
				+ valofMandates + "]";
	}
	
	
}
