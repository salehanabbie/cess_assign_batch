

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
 * @author ElelwaniR
 */
@Entity
@Table(name = "MDT_OPS_PROCESS_CONTROLS",schema = "MANOWNER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MdtOpsProcessControlsEntity.findAll", query = "SELECT m FROM MdtOpsProcessControlsEntity m"),
    @NamedQuery(name = "MdtOpsProcessControlsEntity.findByInstId", query = "SELECT m FROM MdtOpsProcessControlsEntity m WHERE m.instId = :instId"),
    @NamedQuery(name = "MdtOpsProcessControlsEntity.findByProcessDate", query = "SELECT m FROM MdtOpsProcessControlsEntity m WHERE m.processDate = :processDate"),
    @NamedQuery(name = "MdtOpsProcessControlsEntity.findByNrOfDaysProc", query = "SELECT m FROM MdtOpsProcessControlsEntity m WHERE m.nrOfDaysProc = :nrOfDaysProc"),
    @NamedQuery(name = "MdtOpsProcessControlsEntity.findByPubHolProc", query = "SELECT m FROM MdtOpsProcessControlsEntity m WHERE m.pubHolProc = :pubHolProc"),
    @NamedQuery(name = "MdtOpsProcessControlsEntity.findByMaxNrRecords", query = "SELECT m FROM MdtOpsProcessControlsEntity m WHERE m.maxNrRecords = :maxNrRecords"),
    @NamedQuery(name = "MdtOpsProcessControlsEntity.findByActiveInd", query = "SELECT m FROM MdtOpsProcessControlsEntity m WHERE m.activeInd = :activeInd")})
public class MdtOpsProcessControlsEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 11)
    @Column(name = "INST_ID")
    private String instId;
    @Column(name = "PROCESS_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date processDate;
    @Size(max = 2)
    @Column(name = "NR_OF_DAYS_PROC")
    private String nrOfDaysProc;
    @Size(max = 1)
    @Column(name = "PUB_HOL_PROC")
    private String pubHolProc;
    @Column(name = "MAX_NR_RECORDS")
    private BigDecimal maxNrRecords;
    @Size(max = 1)
    @Column(name = "ACTIVE_IND")
    private String activeInd;

    public MdtOpsProcessControlsEntity() {
    }

	public String getInstId() {
		return instId;
	}

	public void setInstId(String instId) {
		this.instId = instId;
	}

	public Date getProcessDate() {
		return processDate;
	}

	public void setProcessDate(Date processDate) {
		this.processDate = processDate;
	}

	public String getNrOfDaysProc() {
		return nrOfDaysProc;
	}

	public void setNrOfDaysProc(String nrOfDaysProc) {
		this.nrOfDaysProc = nrOfDaysProc;
	}

	public String getPubHolProc() {
		return pubHolProc;
	}

	public void setPubHolProc(String pubHolProc) {
		this.pubHolProc = pubHolProc;
	}

	public BigDecimal getMaxNrRecords() {
		return maxNrRecords;
	}

	public void setMaxNrRecords(BigDecimal maxNrRecords) {
		this.maxNrRecords = maxNrRecords;
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
		result = prime * result + ((instId == null) ? 0 : instId.hashCode());
		result = prime * result
				+ ((maxNrRecords == null) ? 0 : maxNrRecords.hashCode());
		result = prime * result
				+ ((nrOfDaysProc == null) ? 0 : nrOfDaysProc.hashCode());
		result = prime * result
				+ ((processDate == null) ? 0 : processDate.hashCode());
		result = prime * result
				+ ((pubHolProc == null) ? 0 : pubHolProc.hashCode());
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
		MdtOpsProcessControlsEntity other = (MdtOpsProcessControlsEntity) obj;
		if (activeInd == null) {
			if (other.activeInd != null)
				return false;
		} else if (!activeInd.equals(other.activeInd))
			return false;
		if (instId == null) {
			if (other.instId != null)
				return false;
		} else if (!instId.equals(other.instId))
			return false;
		if (maxNrRecords == null) {
			if (other.maxNrRecords != null)
				return false;
		} else if (!maxNrRecords.equals(other.maxNrRecords))
			return false;
		if (nrOfDaysProc == null) {
			if (other.nrOfDaysProc != null)
				return false;
		} else if (!nrOfDaysProc.equals(other.nrOfDaysProc))
			return false;
		if (processDate == null) {
			if (other.processDate != null)
				return false;
		} else if (!processDate.equals(other.processDate))
			return false;
		if (pubHolProc == null) {
			if (other.pubHolProc != null)
				return false;
		} else if (!pubHolProc.equals(other.pubHolProc))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MdtOpsProcessControlsEntity [instId=" + instId
				+ ", processDate=" + processDate + ", nrOfDaysProc="
				+ nrOfDaysProc + ", pubHolProc=" + pubHolProc
				+ ", maxNrRecords=" + maxNrRecords + ", activeInd=" + activeInd
				+ "]";
	}

    
  
    
}
