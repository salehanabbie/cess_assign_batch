package com.bsva.models;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author DimakatsoN
 *
 */
public class WebArcSupplDataModel implements Serializable
{
	    private String mandateReqId;
	    private String placeAndName;
	    private String envelope;
	    private String createdBy;
	    private Date createdDate;
	    private String modifiedBy;
	    private Date modifiedDate;

		public String getMandateReqId() 
		{
			return mandateReqId;
		}

		public void setMandateReqId(String mandateReqId)
		{
			this.mandateReqId = mandateReqId;
		}

		public String getPlaceAndName()
		{
			return placeAndName;
		}

		public void setPlaceAndName(String placeAndName) 
		{
			this.placeAndName = placeAndName;
		}

		public String getEnvelope()
		{
			return envelope;
		}

		public void setEnvelope(String envelope)
		{
			this.envelope = envelope;
		}

		public String getCreatedBy() 
		{
			return createdBy;
		}

		public void setCreatedBy(String createdBy) 
		{
			this.createdBy = createdBy;
		}

		public Date getCreatedDate()
		{
			return createdDate;
		}

		public void setCreatedDate(Date createdDate)
		{
			this.createdDate = createdDate;
		}

		public String getModifiedBy() 
		{
			return modifiedBy;
		}

		public void setModifiedBy(String modifiedBy)
		{
			this.modifiedBy = modifiedBy;
		}

		public Date getModifiedDate() 
		{
			return modifiedDate;
		}

		public void setModifiedDate(Date modifiedDate)
		{
			this.modifiedDate = modifiedDate;
		}

		@Override
		public int hashCode()
		{
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((createdBy == null) ? 0 : createdBy.hashCode());
			result = prime * result
					+ ((createdDate == null) ? 0 : createdDate.hashCode());
			result = prime * result
					+ ((envelope == null) ? 0 : envelope.hashCode());
			result = prime * result
					+ ((mandateReqId == null) ? 0 : mandateReqId.hashCode());
			result = prime * result
					+ ((modifiedBy == null) ? 0 : modifiedBy.hashCode());
			result = prime * result
					+ ((modifiedDate == null) ? 0 : modifiedDate.hashCode());
			result = prime * result
					+ ((placeAndName == null) ? 0 : placeAndName.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj)
		{
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			WebArcSupplDataModel other = (WebArcSupplDataModel) obj;
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
			if (envelope == null) {
				if (other.envelope != null)
					return false;
			} else if (!envelope.equals(other.envelope))
				return false;
			if (mandateReqId == null) {
				if (other.mandateReqId != null)
					return false;
			} else if (!mandateReqId.equals(other.mandateReqId))
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
			if (placeAndName == null) {
				if (other.placeAndName != null)
					return false;
			} else if (!placeAndName.equals(other.placeAndName))
				return false;
			return true;
		}

		@Override
		public String toString()
		{
			return "WebArcSupplDataModel [mandateReqId=" + mandateReqId
					+ ", placeAndName=" + placeAndName + ", envelope="
					+ envelope + ", createdBy=" + createdBy + ", createdDate="
					+ createdDate + ", modifiedBy=" + modifiedBy
					+ ", modifiedDate=" + modifiedDate + "]";
		}   

}
