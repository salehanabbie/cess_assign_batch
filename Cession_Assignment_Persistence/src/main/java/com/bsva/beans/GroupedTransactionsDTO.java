/**
 * 
 */
package com.bsva.beans;

import java.io.Serializable;

/**
 * @author ElelwaniR
 *
 */
public class GroupedTransactionsDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String collumn;
	private Long count;

	public String getCollumn() {
		return collumn;
	}

	public void setCollumn(String collumn) {
		this.collumn = collumn;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((collumn == null) ? 0 : collumn.hashCode());
		result = prime * result + ((count == null) ? 0 : count.hashCode());
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
		GroupedTransactionsDTO other = (GroupedTransactionsDTO) obj;
		if (collumn == null) {
			if (other.collumn != null)
				return false;
		} else if (!collumn.equals(other.collumn))
			return false;
		if (count == null) {
			if (other.count != null)
				return false;
		} else if (!count.equals(other.count))
			return false;
		return true;
	}

	
	
}
