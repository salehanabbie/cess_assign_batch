package com.bsva.models;
import java.io.Serializable;

/***
 * 
 * @author DimakatsoN
 *
 */
public class ViewDeleteEOTFileModel implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String fileName;

	public String getFileName() 
	{
		return fileName;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((fileName == null) ? 0 : fileName.hashCode());
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
		ViewDeleteEOTFileModel other = (ViewDeleteEOTFileModel) obj;
		if (fileName == null) {
			if (other.fileName != null)
				return false;
		} else if (!fileName.equals(other.fileName))
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		return "ViewDeleteEOTFileModel [fileName=" + fileName + "]";
	}
}
