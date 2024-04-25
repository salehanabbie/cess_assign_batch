package com.bsva.models;
import java.io.Serializable;

/**
 * @author jeremym
 *
 */
public class UniversalModel implements Serializable 
{
	private static final long serialVersionUID = 1L;
	private String code; 
	private String description; 
	private String codeInput; 
	private String descriptionInput;
	private boolean isUpdate; 
	/**
	 * @return the isUpdate
	 */
	public boolean isUpdate() 
	{
		return isUpdate;
	}
	/**
	 * @param isUpdate the isUpdate to set
	 */
	public void setUpdate(boolean isUpdate) 
	{
		this.isUpdate = isUpdate;
	}
	/**
	 * @return the code
	 */
	public String getCode()
	{
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(String code) 
	{
		this.code = code;
	}
	/**
	 * @return the description
	 */
	public String getDescription() 
	{
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) 
	{
		this.description = description;
	}
	/**
	 * @return the codeInput
	 */
	public String getCodeInput() 
	{
		return codeInput;
	}
	/**
	 * @param codeInput the codeInput to set
	 */
	public void setCodeInput(String codeInput) 
	{
		this.codeInput = codeInput;
	}
	/**
	 * @return the descriptionInput
	 */
	public String getDescriptionInput() 
	{
		return descriptionInput;
	}
	/**
	 * @param descriptionInput the descriptionInput to set
	 */
	public void setDescriptionInput(String descriptionInput) 
	{
		this.descriptionInput = descriptionInput;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result
				+ ((codeInput == null) ? 0 : codeInput.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime
				* result
				+ ((descriptionInput == null) ? 0 : descriptionInput.hashCode());
		result = prime * result + (isUpdate ? 1231 : 1237);
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
		UniversalModel other = (UniversalModel) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (codeInput == null) {
			if (other.codeInput != null)
				return false;
		} else if (!codeInput.equals(other.codeInput))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (descriptionInput == null) {
			if (other.descriptionInput != null)
				return false;
		} else if (!descriptionInput.equals(other.descriptionInput))
			return false;
		if (isUpdate != other.isUpdate)
			return false;
		return true;
	} 
	
}
