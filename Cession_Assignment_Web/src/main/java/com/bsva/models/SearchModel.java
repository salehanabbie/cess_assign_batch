package com.bsva.models;
import java.io.Serializable;

/**
 * @author jeremym
 * 
 */
public class SearchModel implements Serializable 
{
	private static final long serialVersionUID = 1L;
	private String searchCriteria;
	private String searchName;

	/**
	 * @return the searchCriteria
	 */
	public String getSearchCriteria()
	{
		return searchCriteria;
	}

	/**
	 * @param searchCriteria
	 *            the searchCriteria to set
	 */
	public void setSearchCriteria(String searchCriteria)
	{
		this.searchCriteria = searchCriteria;
	}

	/**
	 * @return the searchName
	 */
	public String getSearchName() 
	{
		return searchName;
	}

	/**
	 * @param searchName
	 *            the searchName to set
	 */
	public void setSearchName(String searchName)
	{
		this.searchName = searchName;
	}

	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((searchCriteria == null) ? 0 : searchCriteria.hashCode());
		result = prime * result
				+ ((searchName == null) ? 0 : searchName.hashCode());
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
		SearchModel other = (SearchModel) obj;
		if (searchCriteria == null) {
			if (other.searchCriteria != null)
				return false;
		} else if (!searchCriteria.equals(other.searchCriteria))
			return false;
		if (searchName == null) {
			if (other.searchName != null)
				return false;
		} else if (!searchName.equals(other.searchName))
			return false;
		return true;
	}

}
