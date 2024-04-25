package com.bsva.models;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author DimakatsoN
 *
 */
public class WebArcGrpHdrModel  implements Serializable
{
	private String msgId;  
    private Date createDateTime;
    private String authCode;
    private String createdBy;

	public String getMsgId()
	{
		return msgId;
	}

	public void setMsgId(String msgId) 
	{
		this.msgId = msgId;
	}

	public Date getCreateDateTime() 
	
	{
		return createDateTime;
	}

	public void setCreateDateTime(Date createDateTime) 
	{
		this.createDateTime = createDateTime;
	}

	public String getAuthCode() 
	{
		return authCode;
	}

	public void setAuthCode(String authCode) 
	{
		this.authCode = authCode;
	}

	public String getCreatedBy() 
	{
		return createdBy;
	}

	public void setCreatedBy(String createdBy) 
	{
		this.createdBy = createdBy;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((authCode == null) ? 0 : authCode.hashCode());
		result = prime * result
				+ ((createDateTime == null) ? 0 : createDateTime.hashCode());
		result = prime * result
				+ ((createdBy == null) ? 0 : createdBy.hashCode());
		result = prime * result + ((msgId == null) ? 0 : msgId.hashCode());
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
		WebArcGrpHdrModel other = (WebArcGrpHdrModel) obj;
		if (authCode == null) {
			if (other.authCode != null)
				return false;
		} else if (!authCode.equals(other.authCode))
			return false;
		if (createDateTime == null) {
			if (other.createDateTime != null)
				return false;
		} else if (!createDateTime.equals(other.createDateTime))
			return false;
		if (createdBy == null) {
			if (other.createdBy != null)
				return false;
		} else if (!createdBy.equals(other.createdBy))
			return false;
		if (msgId == null) {
			if (other.msgId != null)
				return false;
		} else if (!msgId.equals(other.msgId))
			return false;
		return true;
	}

	@Override
	public String toString() 
	{
		return "WebArcGrpHdrModel [msgId=" + msgId + ", createDateTime="
				+ createDateTime + ", authCode=" + authCode + ", createdBy="
				+ createdBy + "]";
	}

}
