package com.bsva.models;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ClientSessionModel implements Serializable 
{
	private String sessionKey;
	private Date date;
	private String permissions;
	private String username;
	private String userRole;
	private BigDecimal userId;
	private String MemeberNumber;
	private String systemName;
	private BigDecimal roleId;
	
	public String getSessionKey()
	{
		return sessionKey;
	}
	
	public void setSessionKey(String sessionKey)
	{
		this.sessionKey = sessionKey;
	}
	
	public Date getDate()
	{
		return date;
	}
	
	public void setDate(Date date) 
	{
		this.date = date;
	}
	
	public String getPermissions() 
	{
		return permissions;
	}
	
	public void setPermissions(String permissions) 
	{
		this.permissions = permissions;
	}
	
	public String getUsername() 
	{
		return username;
	}
	
	public void setUsername(String username) 
	{
		this.username = username;
	}
	
	public String getUserRole() 
	{
		return userRole;
	}
	
	public void setUserRole(String userRole) 
	{
		this.userRole = userRole;
	}
	
	public BigDecimal getUserId() 
	{
		return userId;
	}
	
	public void setUserId(BigDecimal userId)
	{
		this.userId = userId;
	}
	
	public String getMemeberNumber() 
	{
		return MemeberNumber;
	}
	
	public void setMemeberNumber(String memeberNumber)
	{
		MemeberNumber = memeberNumber;
	}
	
	public String getSystemName() 
	{
		return systemName;
	}
	
	public void setSystemName(String systemName)
	{
		this.systemName = systemName;
	}
	public BigDecimal getRoleId()
	{
		return roleId;
	}
	
	public void setRoleId(BigDecimal roleId)
	{
		this.roleId = roleId;
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((MemeberNumber == null) ? 0 : MemeberNumber.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result
				+ ((permissions == null) ? 0 : permissions.hashCode());
		result = prime * result + ((roleId == null) ? 0 : roleId.hashCode());
		result = prime * result
				+ ((sessionKey == null) ? 0 : sessionKey.hashCode());
		result = prime * result
				+ ((systemName == null) ? 0 : systemName.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		result = prime * result
				+ ((userRole == null) ? 0 : userRole.hashCode());
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
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
		ClientSessionModel other = (ClientSessionModel) obj;
		if (MemeberNumber == null) 
		{
			if (other.MemeberNumber != null)
				return false;
		} 
		else if (!MemeberNumber.equals(other.MemeberNumber))
			return false;
		if (date == null) 
		{
			if (other.date != null)
				return false;
		} 
		else if (!date.equals(other.date))
			return false;
		if (permissions == null)
		{
			if (other.permissions != null)
				return false;
		} 
		else if (!permissions.equals(other.permissions))
			return false;
		if (roleId == null)
		{
			if (other.roleId != null)
				return false;
		} 
		else if (!roleId.equals(other.roleId))
			return false;
		if (sessionKey == null)
		{
			if (other.sessionKey != null)
				return false;
		}
		else if (!sessionKey.equals(other.sessionKey))
			return false;
		if (systemName == null)
		{
			if (other.systemName != null)
				return false;
		}
		else if (!systemName.equals(other.systemName))
			return false;
		if (userId == null)
		{
			if (other.userId != null)
				return false;
		} 
		else if (!userId.equals(other.userId))
			return false;
		if (userRole == null) 
		{
			if (other.userRole != null)
				return false;
		} 
		else if (!userRole.equals(other.userRole))
			return false;
		if (username == null)
		{
			if (other.username != null)
				return false;
		} 
		else if (!username.equals(other.username))
			return false;
		return true;
	}
	
	@Override
	public String toString()
	{
		return "ClientSessionModel [sessionKey=" + sessionKey + ", date="
				+ date + ", permissions=" + permissions + ", username="
				+ username + ", userRole=" + userRole + ", userId=" + userId
				+ ", MemeberNumber=" + MemeberNumber + ", systemName="
				+ systemName + ", roleId=" + roleId + "]";
	}	
	
}