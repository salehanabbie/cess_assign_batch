package com.bsva.models;

import java.io.Serializable;
import java.util.Date;


/**
 * 
 * @author NhlanhlaM
 *
 */

public class WebMdtXsdVersionsModel implements Serializable 
{
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String xsdType;
	private String version;
	private String xsdNamespace;
	private String jaxbNamespace;
	private String activeInd;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	
	
	public WebMdtXsdVersionsModel() {
		super();
		// TODO Auto-generated constructor stub
	}


	public WebMdtXsdVersionsModel(String xsdType2) {
		// TODO Auto-generated constructor stub
	}


	public String getXsdType() {
		return xsdType;
	}


	public void setXsdType(String xsdType) {
		this.xsdType = xsdType;
	}


	public String getVersion() {
		return version;
	}


	public void setVersion(String version) {
		this.version = version;
	}


	public String getXsdNamespace() {
		return xsdNamespace;
	}


	public void setXsdNamespace(String xsdNamespace) {
		this.xsdNamespace = xsdNamespace;
	}


	public String getJaxbNamespace() {
		return jaxbNamespace;
	}


	public void setJaxbNamespace(String jaxbNamespace) {
		this.jaxbNamespace = jaxbNamespace;
	}


	public String getActiveInd() {
		return activeInd;
	}


	public void setActiveInd(String activeInd) {
		this.activeInd = activeInd;
	}


	public String getCreatedBy() {
		return createdBy;
	}


	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}


	public Date getCreatedDate() {
		return createdDate;
	}


	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}


	public String getModifiedBy() {
		return modifiedBy;
	}


	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}


	public Date getModifiedDate() {
		return modifiedDate;
	}


	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((activeInd == null) ? 0 : activeInd.hashCode());
		result = prime * result
				+ ((createdBy == null) ? 0 : createdBy.hashCode());
		result = prime * result
				+ ((createdDate == null) ? 0 : createdDate.hashCode());
		result = prime * result
				+ ((jaxbNamespace == null) ? 0 : jaxbNamespace.hashCode());
		result = prime * result
				+ ((modifiedBy == null) ? 0 : modifiedBy.hashCode());
		result = prime * result
				+ ((modifiedDate == null) ? 0 : modifiedDate.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
		result = prime * result
				+ ((xsdNamespace == null) ? 0 : xsdNamespace.hashCode());
		result = prime * result + ((xsdType == null) ? 0 : xsdType.hashCode());
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
		WebMdtXsdVersionsModel other = (WebMdtXsdVersionsModel) obj;
		if (activeInd == null) {
			if (other.activeInd != null)
				return false;
		} else if (!activeInd.equals(other.activeInd))
			return false;
		if (createdBy == null) {
			if (other.createdBy != null)
				return false;
		} else if (!createdBy.equals(other.createdBy))
			return false;
		if (createdDate == null) {
			if (other.createdDate != null)
				return false;
		} else if (!createdDate.equals(other.createdDate))
			return false;
		if (jaxbNamespace == null) {
			if (other.jaxbNamespace != null)
				return false;
		} else if (!jaxbNamespace.equals(other.jaxbNamespace))
			return false;
		if (modifiedBy == null) {
			if (other.modifiedBy != null)
				return false;
		} else if (!modifiedBy.equals(other.modifiedBy))
			return false;
		if (modifiedDate == null) {
			if (other.modifiedDate != null)
				return false;
		} else if (!modifiedDate.equals(other.modifiedDate))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		if (xsdNamespace == null) {
			if (other.xsdNamespace != null)
				return false;
		} else if (!xsdNamespace.equals(other.xsdNamespace))
			return false;
		if (xsdType == null) {
			if (other.xsdType != null)
				return false;
		} else if (!xsdType.equals(other.xsdType))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return jaxbNamespace;

	}


	
	 
	
	 
	 
	 
	 
	

}
