package com.bsva.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author DimakatsoN
 */
@Entity
@Table(name = "IAM_SESSION",schema = "MANOWNER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IamSessionEntity.findAll", query = "SELECT i FROM IamSessionEntity i"),
    @NamedQuery(name = "IamSessionEntity.findBySystemSeqNo", query = "SELECT i FROM IamSessionEntity i WHERE i.systemSeqNo = :systemSeqNo"),
    @NamedQuery(name = "IamSessionEntity.findBySessionKey", query = "SELECT i FROM IamSessionEntity i WHERE i.sessionKey = :sessionKey"),
    @NamedQuery(name = "IamSessionEntity.findBySessionDate", query = "SELECT i FROM IamSessionEntity i WHERE i.sessionDate = :sessionDate"),
    @NamedQuery(name = "IamSessionEntity.findByUserName", query = "SELECT i FROM IamSessionEntity i WHERE i.userName = :userName"),
	@NamedQuery(name = "IamSession.findByUserRole", query = "SELECT i FROM IamSessionEntity i WHERE i.userRole = :userRole")})
public class IamSessionEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SYSTEM_SEQ_NO")
    private BigDecimal systemSeqNo;
    @Size(max = 35)
    @Column(name = "SESSION_KEY")
    private String sessionKey;
    @Column(name = "SESSION_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sessionDate;
    @Size(max = 35)
    @Column(name = "USER_NAME")
    private String userName;
    @Size(max = 35)
    @Column(name = "USER_ROLE")
    private String userRole;

    
    

    public IamSessionEntity() {
    }

    public IamSessionEntity(BigDecimal systemSeqNo) {
        this.systemSeqNo = systemSeqNo;
    }

    public BigDecimal getSystemSeqNo() {
        return systemSeqNo;
    }

    public void setSystemSeqNo(BigDecimal systemSeqNo) {
        this.systemSeqNo = systemSeqNo;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public Date getSessionDate() {
        return sessionDate;
    }

    public void setSessionDate(Date sessionDate) {
        this.sessionDate = sessionDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((sessionDate == null) ? 0 : sessionDate.hashCode());
		result = prime * result
				+ ((sessionKey == null) ? 0 : sessionKey.hashCode());
		result = prime * result
				+ ((systemSeqNo == null) ? 0 : systemSeqNo.hashCode());
		result = prime * result
				+ ((userName == null) ? 0 : userName.hashCode());
		result = prime * result
				+ ((userRole == null) ? 0 : userRole.hashCode());
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
		IamSessionEntity other = (IamSessionEntity) obj;
		if (sessionDate == null) {
			if (other.sessionDate != null)
				return false;
		} else if (!sessionDate.equals(other.sessionDate))
			return false;
		if (sessionKey == null) {
			if (other.sessionKey != null)
				return false;
		} else if (!sessionKey.equals(other.sessionKey))
			return false;
		if (systemSeqNo == null) {
			if (other.systemSeqNo != null)
				return false;
		} else if (!systemSeqNo.equals(other.systemSeqNo))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		if (userRole == null) {
			if (other.userRole != null)
				return false;
		} else if (!userRole.equals(other.userRole))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "IamSessionEntity [systemSeqNo=" + systemSeqNo + ", sessionKey="
				+ sessionKey + ", sessionDate=" + sessionDate + ", userName="
				+ userName + ", userRole=" + userRole + "]";
	}


	
    
    
    
}
