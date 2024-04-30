

package com.bsva.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ElelwaniR
 */
@Entity
@Table(name = "CAS_OPS_PROCESS_CONTROLS",schema = "CAMOWNER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CasOpsProcessControlsEntity.findAll", query = "SELECT m FROM CasOpsProcessControlsEntity m"),
    @NamedQuery(name = "CasOpsProcessControlsEntity.findByProcessDate", query = "SELECT m FROM CasOpsProcessControlsEntity m WHERE m.processDate = :processDate"),
    @NamedQuery(name = "CasOpsProcessControlsEntity.findByCisDownloadInd", query = "SELECT m FROM CasOpsProcessControlsEntity m WHERE m.cisDownloadInd = :cisDownloadInd")})
public class CasOpsProcessControlsEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "PROCESS_DATE")
    @Temporal(TemporalType.DATE)
    private Date processDate;
    @Size(max = 1)
    @Column(name = "CIS_DOWNLOAD_IND")
    private String cisDownloadInd;
    

    public CasOpsProcessControlsEntity() {
    }

    public CasOpsProcessControlsEntity(Date processDate) {
        this.processDate = processDate;
    }

    public Date getProcessDate() {
        return processDate;
    }

    public void setProcessDate(Date processDate) {
        this.processDate = processDate;
    }

    public String getCisDownloadInd() {
        return cisDownloadInd;
    }

    public void setCisDownloadInd(String cisDownloadInd) {
        this.cisDownloadInd = cisDownloadInd;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cisDownloadInd == null) ? 0 : cisDownloadInd.hashCode());
		result = prime * result + ((processDate == null) ? 0 : processDate.hashCode());
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
		CasOpsProcessControlsEntity other = (CasOpsProcessControlsEntity) obj;
		if (cisDownloadInd == null) {
			if (other.cisDownloadInd != null)
				return false;
		} else if (!cisDownloadInd.equals(other.cisDownloadInd))
			return false;
		if (processDate == null) {
			if (other.processDate != null)
				return false;
		} else if (!processDate.equals(other.processDate))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CasOpsProcessControlsEntity [processDate=" + processDate + ", cisDownloadInd=" + cisDownloadInd + "]";
	}
    
    

}
