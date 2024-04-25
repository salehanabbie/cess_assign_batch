package com.bsva.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 *
 * @author SalehaR
 */
@Entity
@Table(name = "AUD_SYSTEM_PROCESS", schema="MANOWNER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AudSystemProcessEntity.findAll", query = "SELECT a FROM AudSystemProcessEntity a"),
    @NamedQuery(name = "AudSystemProcessEntity.findBySystemSeqNo", query = "SELECT a FROM AudSystemProcessEntity a WHERE a.systemSeqNo = :systemSeqNo"),
    @NamedQuery(name = "AudSystemProcessEntity.findByProcess", query = "SELECT a FROM AudSystemProcessEntity a WHERE a.process = :process"),
    @NamedQuery(name = "AudSystemProcessEntity.findByProcessDate", query = "SELECT a FROM AudSystemProcessEntity a WHERE a.processDate = :processDate"),
    @NamedQuery(name = "AudSystemProcessEntity.findByUserId", query = "SELECT a FROM AudSystemProcessEntity a WHERE a.userId = :userId")})
public class AudSystemProcessEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @GenericGenerator(name = "generator", strategy = "sequence-identity", parameters = @Parameter(name = "sequence", value = "MANOWNER.AUD_SYSTEM_PROCESS_SEQ") )
    @GeneratedValue(generator = "generator")
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SYSTEM_SEQ_NO")
    private BigDecimal systemSeqNo;
    @Size(max = 200)
    @Column(name = "PROCESS")
    private String process;
    @Column(name = "PROCESS_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date processDate;
    @Size(max = 25)
    @Column(name = "USER_ID")
    private String userId;

    public AudSystemProcessEntity() {
    }

    public AudSystemProcessEntity(BigDecimal systemSeqNo) {
        this.systemSeqNo = systemSeqNo;
    }

    public BigDecimal getSystemSeqNo() {
        return systemSeqNo;
    }

    public void setSystemSeqNo(BigDecimal systemSeqNo) {
        this.systemSeqNo = systemSeqNo;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public Date getProcessDate() {
        return processDate;
    }

    public void setProcessDate(Date processDate) {
        this.processDate = processDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((process == null) ? 0 : process.hashCode());
		result = prime * result
				+ ((processDate == null) ? 0 : processDate.hashCode());
		result = prime * result
				+ ((systemSeqNo == null) ? 0 : systemSeqNo.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
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
		AudSystemProcessEntity other = (AudSystemProcessEntity) obj;
		if (process == null) {
			if (other.process != null)
				return false;
		} else if (!process.equals(other.process))
			return false;
		if (processDate == null) {
			if (other.processDate != null)
				return false;
		} else if (!processDate.equals(other.processDate))
			return false;
		if (systemSeqNo == null) {
			if (other.systemSeqNo != null)
				return false;
		} else if (!systemSeqNo.equals(other.systemSeqNo))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AudSystemProcessEntity [systemSeqNo=" + systemSeqNo
				+ ", process=" + process + ", processDate=" + processDate
				+ ", userId=" + userId + "]";
	}

    
    
}
