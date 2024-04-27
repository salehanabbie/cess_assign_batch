package com.bsva.entities;


import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
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
@Table(name = "CAS_SYSCTRL_SCHEDULER_CRON", schema = "CAMOWNER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CasSysctrlSchedulerCronEntity.findAll", query = "SELECT m FROM CasSysctrlSchedulerCronEntity m"),
    @NamedQuery(name = "CasSysctrlSchedulerCronEntity.findBySystemSeqNo", query = "SELECT m FROM CasSysctrlSchedulerCronEntity m WHERE m.systemSeqNo = :systemSeqNo"),
    @NamedQuery(name = "CasSysctrlSchedulerCronEntity.findBySchedulerCronInterval", query = "SELECT m FROM CasSysctrlSchedulerCronEntity m WHERE m.schedulerCronInterval = :schedulerCronInterval"),
    @NamedQuery(name = "CasSysctrlSchedulerCronEntity.findByCronValue", query = "SELECT m FROM CasSysctrlSchedulerCronEntity m WHERE m.cronValue = :cronValue"),
    @NamedQuery(name = "CasSysctrlSchedulerCronEntity.findByActiveInd", query = "SELECT m FROM CasSysctrlSchedulerCronEntity m WHERE m.activeInd = :activeInd")})
public class CasSysctrlSchedulerCronEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @GenericGenerator(name = "generator", strategy = "sequence-identity", parameters = @Parameter(name = "sequence", value = "MANOWNER.MDT_SYSCTRL_SCHEDULER_CRON_SEQ"))
    @GeneratedValue(generator = "generator")
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SYSTEM_SEQ_NO")
    private BigDecimal systemSeqNo;
    @Size(max = 100)
    @Column(name = "SCHEDULER_CRON_INTERVAL")
    private String schedulerCronInterval;
    @Size(max = 50)
    @Column(name = "CRON_VALUE")
    private String cronValue;
    @Size(max = 1)
    @Column(name = "ACTIVE_IND")
    private String activeInd;

    public CasSysctrlSchedulerCronEntity() {
    }

    public CasSysctrlSchedulerCronEntity(BigDecimal systemSeqNo) {
        this.systemSeqNo = systemSeqNo;
    }

    public BigDecimal getSystemSeqNo() {
        return systemSeqNo;
    }

    public void setSystemSeqNo(BigDecimal systemSeqNo) {
        this.systemSeqNo = systemSeqNo;
    }

    public String getSchedulerCronInterval() {
        return schedulerCronInterval;
    }

    public void setSchedulerCronInterval(String schedulerCronInterval) {
        this.schedulerCronInterval = schedulerCronInterval;
    }

    public String getCronValue() {
        return cronValue;
    }

    public void setCronValue(String cronValue) {
        this.cronValue = cronValue;
    }

    public String getActiveInd() {
        return activeInd;
    }

    public void setActiveInd(String activeInd) {
        this.activeInd = activeInd;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((activeInd == null) ? 0 : activeInd.hashCode());
		result = prime * result
				+ ((cronValue == null) ? 0 : cronValue.hashCode());
		result = prime
				* result
				+ ((schedulerCronInterval == null) ? 0 : schedulerCronInterval
						.hashCode());
		result = prime * result
				+ ((systemSeqNo == null) ? 0 : systemSeqNo.hashCode());
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
		CasSysctrlSchedulerCronEntity other = (CasSysctrlSchedulerCronEntity) obj;
		if (activeInd == null) {
			if (other.activeInd != null)
				return false;
		} else if (!activeInd.equals(other.activeInd))
			return false;
		if (cronValue == null) {
			if (other.cronValue != null)
				return false;
		} else if (!cronValue.equals(other.cronValue))
			return false;
		if (schedulerCronInterval == null) {
			if (other.schedulerCronInterval != null)
				return false;
		} else if (!schedulerCronInterval.equals(other.schedulerCronInterval))
			return false;
		if (systemSeqNo == null) {
			if (other.systemSeqNo != null)
				return false;
		} else if (!systemSeqNo.equals(other.systemSeqNo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CasSysctrlSchedulerCronEntity [systemSeqNo=" + systemSeqNo
				+ ", schedulerCronInterval=" + schedulerCronInterval
				+ ", cronValue=" + cronValue + ", activeInd=" + activeInd + "]";
	}
    
}
