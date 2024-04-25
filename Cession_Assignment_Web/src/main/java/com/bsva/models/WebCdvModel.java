package com.bsva.models;
public class WebCdvModel 
{
	   private String accountNumber;
	   private String accountType;
	   private String branch;

	    public WebCdvModel() 
	    {
	    }

		public String getAccountNumber()
		{
			return accountNumber;
		}

		public void setAccountNumber(String accountNumber) 
		{
			this.accountNumber = accountNumber;
		}

		public String getAccountType() 
		{
			return accountType;
		}

		public void setAccountType(String accountType) 
		{
			this.accountType = accountType;
		}

		public String getBranch() 
		{
			return branch;
		}

		public void setBranch(String branch) 
		{
			this.branch = branch;
		}

		@Override
		public int hashCode()
		{
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((accountNumber == null) ? 0 : accountNumber.hashCode());
			result = prime * result
					+ ((accountType == null) ? 0 : accountType.hashCode());
			result = prime * result
					+ ((branch == null) ? 0 : branch.hashCode());
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
			WebCdvModel other = (WebCdvModel) obj;
			if (accountNumber == null) {
				if (other.accountNumber != null)
					return false;
			} else if (!accountNumber.equals(other.accountNumber))
				return false;
			if (accountType == null) {
				if (other.accountType != null)
					return false;
			} else if (!accountType.equals(other.accountType))
				return false;
			if (branch == null) {
				if (other.branch != null)
					return false;
			} else if (!branch.equals(other.branch))
				return false;
			return true;
		}

		@Override
		public String toString() 
		{
			return "WebCdvModel [accountNumber=" + accountNumber
					+ ", accountType=" + accountType + ", branch=" + branch
					+ "]";
		}
   
}
