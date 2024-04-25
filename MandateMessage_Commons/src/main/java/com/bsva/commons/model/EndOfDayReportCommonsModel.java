package com.bsva.commons.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class EndOfDayReportCommonsModel  implements Serializable 

{
	/**
	 * @author ElelwaniR
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal balanceBrghtForward;
	private BigDecimal Incoming;
	private BigDecimal Outgoing;
	private String serviceID;
	private BigDecimal balanceCarriedForward;

public EndOfDayReportCommonsModel() 
{
	super();
	// TODO Auto-generated constructor stub
}

public BigDecimal getBalanceBrghtForward() {
	return balanceBrghtForward;
}

public void setBalanceBrghtForward(BigDecimal balanceBrghtForward) {
	this.balanceBrghtForward = balanceBrghtForward;
}

public BigDecimal getIncoming() {
	return Incoming;
}

public void setIncoming(BigDecimal incoming) {
	Incoming = incoming;
}

public BigDecimal getOutgoing() {
	return Outgoing;
}

public void setOutgoing(BigDecimal outgoing) {
	Outgoing = outgoing;
}

public String getServiceID() {
	return serviceID;
}

public void setServiceID(String serviceID) {
	this.serviceID = serviceID;
}

public BigDecimal getBalanceCarriedForward() {
	return balanceCarriedForward;
}

public void setBalanceCarriedForward(BigDecimal balanceCarriedForward) {
	this.balanceCarriedForward = balanceCarriedForward;
}

@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((Incoming == null) ? 0 : Incoming.hashCode());
	result = prime * result + ((Outgoing == null) ? 0 : Outgoing.hashCode());
	result = prime
			* result
			+ ((balanceBrghtForward == null) ? 0 : balanceBrghtForward
					.hashCode());
	result = prime
			* result
			+ ((balanceCarriedForward == null) ? 0 : balanceCarriedForward
					.hashCode());
	result = prime * result + ((serviceID == null) ? 0 : serviceID.hashCode());
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
	EndOfDayReportCommonsModel other = (EndOfDayReportCommonsModel) obj;
	if (Incoming == null) {
		if (other.Incoming != null)
			return false;
	} else if (!Incoming.equals(other.Incoming))
		return false;
	if (Outgoing == null) {
		if (other.Outgoing != null)
			return false;
	} else if (!Outgoing.equals(other.Outgoing))
		return false;
	if (balanceBrghtForward == null) {
		if (other.balanceBrghtForward != null)
			return false;
	} else if (!balanceBrghtForward.equals(other.balanceBrghtForward))
		return false;
	if (balanceCarriedForward == null) {
		if (other.balanceCarriedForward != null)
			return false;
	} else if (!balanceCarriedForward.equals(other.balanceCarriedForward))
		return false;
	if (serviceID == null) {
		if (other.serviceID != null)
			return false;
	} else if (!serviceID.equals(other.serviceID))
		return false;
	return true;
}

@Override
public String toString() {
	return "EndOfDayReportCommonsModel [balanceBrghtForward="
			+ balanceBrghtForward + ", Incoming=" + Incoming + ", Outgoing="
			+ Outgoing + ", serviceID=" + serviceID
			+ ", balanceCarriedForward=" + balanceCarriedForward + "]";
}



}