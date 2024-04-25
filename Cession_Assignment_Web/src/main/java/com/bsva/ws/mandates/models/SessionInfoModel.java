package com.bsva.ws.mandates.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by CharlesD on 2014/07/31.
 */
public class SessionInfoModel implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;


    private String sessionKey;

    private String userName;

    private BigDecimal userId;
    
    private Date createdDate;

    private Integer periodValid;

    private String active;

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public BigDecimal getUserId() {
        return userId;
    }

    public void setUserId(BigDecimal userId) {
        this.userId = userId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public Integer getPeriodValid() {
        return periodValid;
    }

    public void setPeriodValid(Integer periodValid) {
        this.periodValid = periodValid;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sessionKey != null ? sessionKey.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof SessionInfoModel)) {
            return false;
        }
        SessionInfoModel other = (SessionInfoModel)object;

        if ((this.sessionKey == null && other.sessionKey != null) || (this.sessionKey != null && !this.sessionKey.equals(other.sessionKey))) {
            return false;
        }
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
           return false;
        }
		if ((this.userName == null && other.userName != null)
				|| (this.userName != null && !this.userName
						.equals(other.userName))) {
			return false;
		}
         return true;
    }

    @Override
    public String toString() {
        return "UsrSessionInfoEntity [sessionKey=" + sessionKey
                + ", userId=" + userId
                + ", active=" + active
                + ", periodValid=" + periodValid
                + ", createdDate=" + createdDate.toString()
                + "]";
    }

}
