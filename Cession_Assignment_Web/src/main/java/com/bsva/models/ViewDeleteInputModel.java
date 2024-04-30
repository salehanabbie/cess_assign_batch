package com.bsva.models;
import java.io.Serializable;

/***
 * 
 * @author DimakatsoN
 *
 */
public class ViewDeleteInputModel implements Serializable  
{
	private static final long serialVersionUID = 1L;
	private String fileName;
	//I ADDED THIS +++++++++++
	private String modifiedDate;
	private String fileType;

	public String getFileName() 
	{
		return fileName;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

	//I ADDED THIS +++++++++++++++++++
	public String getmodifiedDate() 
	{
		return modifiedDate;
	}

	public void setmodifiedDate(String modifiedDate) 
	{
		this.modifiedDate = modifiedDate;
	}

	public String getFileType() 
	{
		return fileType;
	}

	public void setFileType(String fileType) 
	{
		this.fileType = fileType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((modifiedDate == null) ? 0 : modifiedDate.hashCode());
		result = prime * result + ((fileName == null) ? 0 : fileName.hashCode());
		result = prime * result + ((fileType == null) ? 0 : fileType.hashCode());
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
		ViewDeleteInputModel other = (ViewDeleteInputModel) obj;
		if (modifiedDate == null) {
			if (other.modifiedDate != null)
				return false;
		} else if (!modifiedDate.equals(other.modifiedDate))
			return false;
		if (fileName == null) {
			if (other.fileName != null)
				return false;
		} else if (!fileName.equals(other.fileName))
			return false;
		if (fileType == null) {
			if (other.fileType != null)
				return false;
		} else if (!fileType.equals(other.fileType))
			return false;
		return true;
	}

	@Override
	public String toString() 
	{
		return "ViewDeleteInputModel [fileName=" + fileName + ", modifiedDate=" + modifiedDate + ", fileType="
				+ fileType + "]";
	}

	// I COMMENTED THIS OUT -----------
//	@Override
//	public int hashCode() 
//	{
//		final int prime = 31;
//		int result = 1;
//		result = prime * result
//				+ ((fileName == null) ? 0 : fileName.hashCode());
//		return result;
//	}
//
//	@Override
//	public boolean equals(Object obj) 
//	{
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		ViewDeleteInputModel other = (ViewDeleteInputModel) obj;
//		if (fileName == null) {
//			if (other.fileName != null)
//				return false;
//		} else if (!fileName.equals(other.fileName))
//			return false;
//		return true;
//	}
//
//	@Override
//	public String toString()
//	{
//		return "ViewDeleteInputModel [fileName=" + fileName + "]";
//	}
	
}
