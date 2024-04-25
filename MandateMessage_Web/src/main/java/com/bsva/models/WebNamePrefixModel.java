package com.bsva.models;

import java.io.Serializable;

/**
 * @author Saleha Saib
 *
 */
public class WebNamePrefixModel implements Serializable
{

		String namePrefix, namePrefixDesc, activeInd;
		
		
		
		 public WebNamePrefixModel()
			{
		
			}

		
		 public WebNamePrefixModel(String namePrefix)
			{
				this.namePrefix = namePrefix;
			}

		public String getNamePrefix() {
			return namePrefix;
		}

		public void setNamePrefix(String namePrefix) {
			this.namePrefix = namePrefix;
		}

		public String getNamePrefixDesc() {
			return namePrefixDesc;
		}

		public void setNamePrefixDesc(String namePrefixDesc) {
			this.namePrefixDesc = namePrefixDesc;
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
					+ ((namePrefix == null) ? 0 : namePrefix.hashCode());
			result = prime
					* result
					+ ((namePrefixDesc == null) ? 0 : namePrefixDesc.hashCode());
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
			WebNamePrefixModel other = (WebNamePrefixModel) obj;
			if (activeInd == null) {
				if (other.activeInd != null)
					return false;
			} else if (!activeInd.equals(other.activeInd))
				return false;
			if (namePrefix == null) {
				if (other.namePrefix != null)
					return false;
			} else if (!namePrefix.equals(other.namePrefix))
				return false;
			if (namePrefixDesc == null) {
				if (other.namePrefixDesc != null)
					return false;
			} else if (!namePrefixDesc.equals(other.namePrefixDesc))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return  namePrefix;
		}
		
		
	
	
}
