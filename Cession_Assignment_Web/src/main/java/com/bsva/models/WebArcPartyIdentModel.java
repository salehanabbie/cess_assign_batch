package com.bsva.models;
import java.io.Serializable;
import java.util.Date;

public class WebArcPartyIdentModel implements Serializable 
{
	    private String name;
	    private String addrType;
	    private String dept;
	    private String subDept;
	    private String streetName;
	    private String buildNumber;
	    private String postCode;
	    private String townName;
	    private String countrySubDiv;
	    private String country;
	    private String addrLine;
	    private String ctryOfResidence;
	    private String namePrefix;
	    private String contactName;
	    private String phoneNr;
	    private String mobNr;
	    private String faxNr;
	    private String email;
	    private Date dateOfBirth;
	    private String provinceOfBirth;
	    private String cityOfBirth;  
	    private String ctryOfBirth;
	    private String createdBy;
	    private Date createdDate; 
	    private String modifiedBy;
	    private Date modifiedDate;

		public String getName() 
		{
			return name;
		}

		public void setName(String name)
		{
			this.name = name;
		}

		public String getAddrType() 
		{
			return addrType;
		}

		public void setAddrType(String addrType) 
		{
			this.addrType = addrType;
		}

		public String getDept()
		{
			return dept;
		}

		public void setDept(String dept)
		{
			this.dept = dept;
		}

		public String getSubDept()
		{
			return subDept;
		}

		public void setSubDept(String subDept)
		{
			this.subDept = subDept;
		}

		public String getStreetName()
		{
			return streetName;
		}

		public void setStreetName(String streetName)
		{
			this.streetName = streetName;
		}

		public String getBuildNumber() 
		{
			return buildNumber;
		}

		public void setBuildNumber(String buildNumber) 
		{
			this.buildNumber = buildNumber;
		}

		public String getPostCode() 
		{
			return postCode;
		}

		public void setPostCode(String postCode)
		{
			this.postCode = postCode;
		}

		public String getTownName() 
		{
			return townName;
		}

		public void setTownName(String townName)
{
			this.townName = townName;
		}

		public String getCountrySubDiv() 
		{
			return countrySubDiv;
		}

		public void setCountrySubDiv(String countrySubDiv) 
		{
			this.countrySubDiv = countrySubDiv;
		}

		public String getCountry() 
		{
			return country;
		}

		public void setCountry(String country)
		{
			this.country = country;
		}

		public String getAddrLine() 
		{
			return addrLine;
		}

		public void setAddrLine(String addrLine) 
		{
			this.addrLine = addrLine;
		}

		public String getCtryOfResidence()
		{
			return ctryOfResidence;
		}

		public void setCtryOfResidence(String ctryOfResidence) 
		{
			this.ctryOfResidence = ctryOfResidence;
		}

		public String getNamePrefix()
		{
			return namePrefix;
		}

		public void setNamePrefix(String namePrefix)
		{
			this.namePrefix = namePrefix;
		}

		public String getContactName() 
		{
			return contactName;
		}

		public void setContactName(String contactName)
		{
			this.contactName = contactName;
		}

		public String getPhoneNr() 
		{
			return phoneNr;
		}

		public void setPhoneNr(String phoneNr) 
		{
			this.phoneNr = phoneNr;
		}

		public String getMobNr() 
		{
			return mobNr;
		}

		public void setMobNr(String mobNr) 
		{
			this.mobNr = mobNr;
		}

		public String getFaxNr() 
		{
			return faxNr;
		}

		public void setFaxNr(String faxNr)
		{
			this.faxNr = faxNr;
		}

		public String getEmail()
		{
			return email;
		}

		public void setEmail(String email)
		{
			this.email = email;
		}

		public Date getDateOfBirth() 
		{
			return dateOfBirth;
		}

		public void setDateOfBirth(Date dateOfBirth) 
		{
			this.dateOfBirth = dateOfBirth;
		}

		public String getProvinceOfBirth() 
		{
			return provinceOfBirth;
		}

		public void setProvinceOfBirth(String provinceOfBirth) 
		{
			this.provinceOfBirth = provinceOfBirth;
		}

		public String getCityOfBirth() 
		{
			return cityOfBirth;
		}

		public void setCityOfBirth(String cityOfBirth) 
		{
			this.cityOfBirth = cityOfBirth;
		}

		public String getCtryOfBirth() 
		{
			return ctryOfBirth;
		}

		public void setCtryOfBirth(String ctryOfBirth)
		{
			this.ctryOfBirth = ctryOfBirth;
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
					+ ((addrLine == null) ? 0 : addrLine.hashCode());
			result = prime * result
					+ ((addrType == null) ? 0 : addrType.hashCode());
			result = prime * result
					+ ((buildNumber == null) ? 0 : buildNumber.hashCode());
			result = prime * result
					+ ((cityOfBirth == null) ? 0 : cityOfBirth.hashCode());
			result = prime * result
					+ ((contactName == null) ? 0 : contactName.hashCode());
			result = prime * result
					+ ((country == null) ? 0 : country.hashCode());
			result = prime * result
					+ ((countrySubDiv == null) ? 0 : countrySubDiv.hashCode());
			result = prime * result
					+ ((createdBy == null) ? 0 : createdBy.hashCode());
			result = prime * result
					+ ((createdDate == null) ? 0 : createdDate.hashCode());
			result = prime * result
					+ ((ctryOfBirth == null) ? 0 : ctryOfBirth.hashCode());
			result = prime
					* result
					+ ((ctryOfResidence == null) ? 0 : ctryOfResidence
							.hashCode());
			result = prime * result
					+ ((dateOfBirth == null) ? 0 : dateOfBirth.hashCode());
			result = prime * result + ((dept == null) ? 0 : dept.hashCode());
			result = prime * result + ((email == null) ? 0 : email.hashCode());
			result = prime * result + ((faxNr == null) ? 0 : faxNr.hashCode());
			result = prime * result + ((mobNr == null) ? 0 : mobNr.hashCode());
			result = prime * result
					+ ((modifiedBy == null) ? 0 : modifiedBy.hashCode());
			result = prime * result
					+ ((modifiedDate == null) ? 0 : modifiedDate.hashCode());
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			result = prime * result
					+ ((namePrefix == null) ? 0 : namePrefix.hashCode());
			result = prime * result
					+ ((phoneNr == null) ? 0 : phoneNr.hashCode());
			result = prime * result
					+ ((postCode == null) ? 0 : postCode.hashCode());
			result = prime
					* result
					+ ((provinceOfBirth == null) ? 0 : provinceOfBirth
							.hashCode());
			result = prime * result
					+ ((streetName == null) ? 0 : streetName.hashCode());
			result = prime * result
					+ ((subDept == null) ? 0 : subDept.hashCode());
			result = prime * result
					+ ((townName == null) ? 0 : townName.hashCode());
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
			WebArcPartyIdentModel other = (WebArcPartyIdentModel) obj;
			if (addrLine == null) {
				if (other.addrLine != null)
					return false;
			} else if (!addrLine.equals(other.addrLine))
				return false;
			if (addrType == null) {
				if (other.addrType != null)
					return false;
			} else if (!addrType.equals(other.addrType))
				return false;
			if (buildNumber == null) {
				if (other.buildNumber != null)
					return false;
			} else if (!buildNumber.equals(other.buildNumber))
				return false;
			if (cityOfBirth == null) {
				if (other.cityOfBirth != null)
					return false;
			} else if (!cityOfBirth.equals(other.cityOfBirth))
				return false;
			if (contactName == null) {
				if (other.contactName != null)
					return false;
			} else if (!contactName.equals(other.contactName))
				return false;
			if (country == null) {
				if (other.country != null)
					return false;
			} else if (!country.equals(other.country))
				return false;
			if (countrySubDiv == null) {
				if (other.countrySubDiv != null)
					return false;
			} else if (!countrySubDiv.equals(other.countrySubDiv))
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
			if (ctryOfBirth == null) {
				if (other.ctryOfBirth != null)
					return false;
			} else if (!ctryOfBirth.equals(other.ctryOfBirth))
				return false;
			if (ctryOfResidence == null) {
				if (other.ctryOfResidence != null)
					return false;
			} else if (!ctryOfResidence.equals(other.ctryOfResidence))
				return false;
			if (dateOfBirth == null) {
				if (other.dateOfBirth != null)
					return false;
			} else if (!dateOfBirth.equals(other.dateOfBirth))
				return false;
			if (dept == null) {
				if (other.dept != null)
					return false;
			} else if (!dept.equals(other.dept))
				return false;
			if (email == null) {
				if (other.email != null)
					return false;
			} else if (!email.equals(other.email))
				return false;
			if (faxNr == null) {
				if (other.faxNr != null)
					return false;
			} else if (!faxNr.equals(other.faxNr))
				return false;
			if (mobNr == null) {
				if (other.mobNr != null)
					return false;
			} else if (!mobNr.equals(other.mobNr))
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
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			if (namePrefix == null) {
				if (other.namePrefix != null)
					return false;
			} else if (!namePrefix.equals(other.namePrefix))
				return false;
			if (phoneNr == null) {
				if (other.phoneNr != null)
					return false;
			} else if (!phoneNr.equals(other.phoneNr))
				return false;
			if (postCode == null) {
				if (other.postCode != null)
					return false;
			} else if (!postCode.equals(other.postCode))
				return false;
			if (provinceOfBirth == null) {
				if (other.provinceOfBirth != null)
					return false;
			} else if (!provinceOfBirth.equals(other.provinceOfBirth))
				return false;
			if (streetName == null) {
				if (other.streetName != null)
					return false;
			} else if (!streetName.equals(other.streetName))
				return false;
			if (subDept == null) {
				if (other.subDept != null)
					return false;
			} else if (!subDept.equals(other.subDept))
				return false;
			if (townName == null) {
				if (other.townName != null)
					return false;
			} else if (!townName.equals(other.townName))
				return false;
			return true;
		}

		@Override
		public String toString() 
		{
			return "WebArcPartyIdentModel [name=" + name + ", addrType="
					+ addrType + ", dept=" + dept + ", subDept=" + subDept
					+ ", streetName=" + streetName + ", buildNumber="
					+ buildNumber + ", postCode=" + postCode + ", townName="
					+ townName + ", countrySubDiv=" + countrySubDiv
					+ ", country=" + country + ", addrLine=" + addrLine
					+ ", ctryOfResidence=" + ctryOfResidence + ", namePrefix="
					+ namePrefix + ", contactName=" + contactName
					+ ", phoneNr=" + phoneNr + ", mobNr=" + mobNr + ", faxNr="
					+ faxNr + ", email=" + email + ", dateOfBirth="
					+ dateOfBirth + ", provinceOfBirth=" + provinceOfBirth
					+ ", cityOfBirth=" + cityOfBirth + ", ctryOfBirth="
					+ ctryOfBirth + ", createdBy=" + createdBy
					+ ", createdDate=" + createdDate + ", modifiedBy="
					+ modifiedBy + ", modifiedDate=" + modifiedDate + "]";
		}	

}
