package com.bsva.models;
import java.io.Serializable;
import java.util.Date;

public class MandateDatesModel implements Serializable
{
	private static final long serialVersionUID = 1L;
	private Date fromDate, toDate, firstCollectionDate, finalCollectionDate;
			
		public MandateDatesModel()
		{
			
		}

		public Date getFromDate()
		{
			return fromDate;
		}

		public void setFromDate(Date fromDate) 
		{
			this.fromDate = fromDate;
		}

		public Date getToDate() 
		{
			return toDate;
		}

		public void setToDate(Date toDate) 
		{
			this.toDate = toDate;
		}

		public Date getFirstCollectionDate()
		{
			return firstCollectionDate;
		}

		public void setFirstCollectionDate(Date firstCollectionDate)
		{
			this.firstCollectionDate = firstCollectionDate;
		}

		public Date getFinalCollectionDate()
		{
			return finalCollectionDate;
		}

		public void setFinalCollectionDate(Date finalCollectionDate) 
		{
			this.finalCollectionDate = finalCollectionDate;
		}

		@Override
		public String toString() 
		{
			return "MandateDatesModel [fromDate=" + fromDate + ", toDate="
					+ toDate + ", firstCollectionDate=" + firstCollectionDate
					+ ", finalCollectionDate=" + finalCollectionDate + "]";
		}

		@Override
		public int hashCode()
		{
			final int prime = 31;
			int result = 1;
			result = prime
					* result
					+ ((finalCollectionDate == null) ? 0 : finalCollectionDate
							.hashCode());
			result = prime
					* result
					+ ((firstCollectionDate == null) ? 0 : firstCollectionDate
							.hashCode());
			result = prime * result
					+ ((fromDate == null) ? 0 : fromDate.hashCode());
			result = prime * result
					+ ((toDate == null) ? 0 : toDate.hashCode());
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
			MandateDatesModel other = (MandateDatesModel) obj;
			if (finalCollectionDate == null) {
				if (other.finalCollectionDate != null)
					return false;
			} else if (!finalCollectionDate.equals(other.finalCollectionDate))
				return false;
			if (firstCollectionDate == null) {
				if (other.firstCollectionDate != null)
					return false;
			} else if (!firstCollectionDate.equals(other.firstCollectionDate))
				return false;
			if (fromDate == null) {
				if (other.fromDate != null)
					return false;
			} else if (!fromDate.equals(other.fromDate))
				return false;
			if (toDate == null) {
				if (other.toDate != null)
					return false;
			} else if (!toDate.equals(other.toDate))
				return false;
			return true;
		}	
	}
