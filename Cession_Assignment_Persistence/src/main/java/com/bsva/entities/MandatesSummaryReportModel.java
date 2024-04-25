package com.bsva.entities;

import java.io.Serializable;
import java.math.BigDecimal;

public class MandatesSummaryReportModel implements Serializable {

	private String finInstName;
	private BigDecimal NrofMandates;
	private BigDecimal valofMandates;
	
	
	public MandatesSummaryReportModel() {
		super();
	}


	public String getFinInstName() {
		return finInstName;
	}


	public void setFinInstName(String finInstName) {
		this.finInstName = finInstName;
	}


	public BigDecimal getNrofMandates() {
		return NrofMandates;
	}


	public void setNrofMandates(BigDecimal nrofMandates) {
		NrofMandates = nrofMandates;
	}


	public BigDecimal getValofMandates() {
		return valofMandates;
	}


	public void setValofMandates(BigDecimal valofMandates) {
		this.valofMandates = valofMandates;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((NrofMandates == null) ? 0 : NrofMandates.hashCode());
		result = prime * result
				+ ((finInstName == null) ? 0 : finInstName.hashCode());
		result = prime * result
				+ ((valofMandates == null) ? 0 : valofMandates.hashCode());
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
		MandatesSummaryReportModel other = (MandatesSummaryReportModel) obj;
		if (NrofMandates == null) {
			if (other.NrofMandates != null)
				return false;
		} else if (!NrofMandates.equals(other.NrofMandates))
			return false;
		if (finInstName == null) {
			if (other.finInstName != null)
				return false;
		} else if (!finInstName.equals(other.finInstName))
			return false;
		if (valofMandates == null) {
			if (other.valofMandates != null)
				return false;
		} else if (!valofMandates.equals(other.valofMandates))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "MandatesSummaryReportModel [finInstName=" + finInstName
				+ ", NrofMandates=" + NrofMandates + ", valofMandates="
				+ valofMandates + "]";
	}

	
	
	
}
