package com.bsva.commons.model;

import java.io.Serializable;

public class CnfgAuthTypeModel implements Serializable  {
	
  private String authType;
  private String authTypeDescription;
  private String activeInd;
  
  
  
public CnfgAuthTypeModel() {
	super();
}



public String getAuthType() {
	return authType;
}



public void setAuthType(String authType) {
	this.authType = authType;
}



public String getAuthTypeDescription() {
	return authTypeDescription;
}



public void setAuthTypeDescription(String authTypeDescription) {
	this.authTypeDescription = authTypeDescription;
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
	result = prime * result + ((activeInd == null) ? 0 : activeInd.hashCode());
	result = prime * result + ((authType == null) ? 0 : authType.hashCode());
	result = prime
			* result
			+ ((authTypeDescription == null) ? 0 : authTypeDescription
					.hashCode());
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
	CnfgAuthTypeModel other = (CnfgAuthTypeModel) obj;
	if (activeInd == null) {
		if (other.activeInd != null)
			return false;
	} else if (!activeInd.equals(other.activeInd))
		return false;
	if (authType == null) {
		if (other.authType != null)
			return false;
	} else if (!authType.equals(other.authType))
		return false;
	if (authTypeDescription == null) {
		if (other.authTypeDescription != null)
			return false;
	} else if (!authTypeDescription.equals(other.authTypeDescription))
		return false;
	return true;
}



@Override
public String toString() {
	return "CnfgAuthTypeModel [authType=" + authType + ", authTypeDescription="
			+ authTypeDescription + ", activeInd=" + activeInd + "]";
}
  

}
