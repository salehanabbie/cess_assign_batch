
package com.bsva.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ElelwaniR
 */
@Entity
@Table(name = "CAS_OPS_MNDT_COUNT",schema = "CAMOWNER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CasOpsMndtCountEntity.findAll", query = "SELECT m FROM CasOpsMndtCountEntity m"),
    @NamedQuery(name = "CasOpsMndtCountEntity.findByInstId", query = "SELECT m FROM CasOpsMndtCountEntity m WHERE m.mdtAcOpsMndtCountPK.instId = :instId"),
    @NamedQuery(name = "CasOpsMndtCountEntity.findByServiceId", query = "SELECT m FROM CasOpsMndtCountEntity m WHERE m.mdtAcOpsMndtCountPK.serviceId = :serviceId"),
    @NamedQuery(name = "CasOpsMndtCountEntity.findByNrOfFiles", query = "SELECT m FROM CasOpsMndtCountEntity m WHERE m.nrOfFiles = :nrOfFiles"),
    @NamedQuery(name = "CasOpsMndtCountEntity.findByNrOfMsgs", query = "SELECT m FROM CasOpsMndtCountEntity m WHERE m.nrOfMsgs = :nrOfMsgs"),
    @NamedQuery(name = "CasOpsMndtCountEntity.findByProcessDate", query = "SELECT m FROM CasOpsMndtCountEntity m WHERE m.processDate = :processDate"),
    @NamedQuery(name = "CasOpsMndtCountEntity.findByIncoming", query = "SELECT m FROM CasOpsMndtCountEntity m WHERE m.incoming = :incoming"),
    @NamedQuery(name = "CasOpsMndtCountEntity.findByOutgoing", query = "SELECT m FROM CasOpsMndtCountEntity m WHERE m.outgoing = :outgoing"),
    @NamedQuery(name = "CasOpsMndtCountEntity.findByMsgId", query = "SELECT m FROM CasOpsMndtCountEntity m WHERE m.mdtAcOpsMndtCountPK.msgId = :msgId"),
    @NamedQuery(name = "CasOpsMndtCountEntity.findByNrMsgsRejected", query = "SELECT m FROM CasOpsMndtCountEntity m WHERE m.nrMsgsRejected = :nrMsgsRejected"),
    @NamedQuery(name = "CasOpsMndtCountEntity.findByFileName", query = "SELECT m FROM CasOpsMndtCountEntity m WHERE m.fileName = :fileName"),
    @NamedQuery(name = "CasOpsMndtCountEntity.findByFileNameLike2", query = "SELECT m FROM CasOpsMndtCountEntity m WHERE m.fileName LIKE :fileName AND m.fileName LIKE :fileName2"),
    @NamedQuery(name = "CasOpsMndtCountEntity.findByNrMsgsAccepted", query = "SELECT m FROM CasOpsMndtCountEntity m WHERE m.nrMsgsAccepted = :nrMsgsAccepted"),
    @NamedQuery(name = "CasOpsMndtCountEntity.findByNrMsgsExtracted", query = "SELECT m FROM CasOpsMndtCountEntity m WHERE m.nrMsgsExtracted = :nrMsgsExtracted")})
public class CasOpsMndtCountEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CasOpsMndtCountPK casOpsMndtCountPK;
    @Column(name = "NR_OF_FILES")
    private Integer nrOfFiles;
    @Column(name = "NR_OF_MSGS")
    private Integer nrOfMsgs;
    @Column(name = "PROCESS_DATE")
    @Temporal(TemporalType.DATE)
    private Date processDate;
    @Size(max = 1)
    @Column(name = "INCOMING")
    private String incoming;
    @Size(max = 1)
    @Column(name = "OUTGOING")
    private String outgoing;
    @Column(name = "NR_MSGS_REJECTED")
    private Integer nrMsgsRejected;
    @Size(max = 100)
    @Column(name = "FILE_NAME")
    private String fileName;
    @Column(name = "NR_MSGS_ACCEPTED")
    private Integer nrMsgsAccepted;
    @Column(name = "NR_MSGS_EXTRACTED")
    private Integer nrMsgsExtracted;

    public CasOpsMndtCountEntity() {
    }

    public CasOpsMndtCountEntity(CasOpsMndtCountPK casOpsMndtCountPK) {
        this.casOpsMndtCountPK = casOpsMndtCountPK;
    }

    public CasOpsMndtCountEntity(String instId, String serviceId, String msgId) {
        this.casOpsMndtCountPK = new CasOpsMndtCountPK(instId, serviceId, msgId);
    }

    public CasOpsMndtCountPK getCasOpsMndtCountPK() {
        return casOpsMndtCountPK;
    }

    public void setCasOpsMndtCountPK(CasOpsMndtCountPK casOpsMndtCountPK) {
        this.casOpsMndtCountPK = casOpsMndtCountPK;
    }

    public Integer getNrOfFiles() {
        return nrOfFiles;
    }

    public void setNrOfFiles(Integer nrOfFiles) {
        this.nrOfFiles = nrOfFiles;
    }

    public Integer getNrOfMsgs() {
        return nrOfMsgs;
    }

    public void setNrOfMsgs(Integer nrOfMsgs) {
        this.nrOfMsgs = nrOfMsgs;
    }

    public Date getProcessDate() {
        return processDate;
    }

    public void setProcessDate(Date processDate) {
        this.processDate = processDate;
    }

    public String getIncoming() {
        return incoming;
    }

    public void setIncoming(String incoming) {
        this.incoming = incoming;
    }

    public String getOutgoing() {
        return outgoing;
    }

    public void setOutgoing(String outgoing) {
        this.outgoing = outgoing;
    }

    public Integer getNrMsgsRejected() {
        return nrMsgsRejected;
    }

    public void setNrMsgsRejected(Integer nrMsgsRejected) {
        this.nrMsgsRejected = nrMsgsRejected;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Integer getNrMsgsAccepted() {
        return nrMsgsAccepted;
    }

    public void setNrMsgsAccepted(Integer nrMsgsAccepted) {
        this.nrMsgsAccepted = nrMsgsAccepted;
    }

    public Integer getNrMsgsExtracted() {
        return nrMsgsExtracted;
    }

    public void setNrMsgsExtracted(Integer nrMsgsExtracted) {
        this.nrMsgsExtracted = nrMsgsExtracted;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((fileName == null) ? 0 : fileName.hashCode());
		result = prime * result
				+ ((incoming == null) ? 0 : incoming.hashCode());
		result = prime
				* result
				+ ((casOpsMndtCountPK == null) ? 0 : casOpsMndtCountPK
						.hashCode());
		result = prime * result
				+ ((nrMsgsAccepted == null) ? 0 : nrMsgsAccepted.hashCode());
		result = prime * result
				+ ((nrMsgsExtracted == null) ? 0 : nrMsgsExtracted.hashCode());
		result = prime * result
				+ ((nrMsgsRejected == null) ? 0 : nrMsgsRejected.hashCode());
		result = prime * result
				+ ((nrOfFiles == null) ? 0 : nrOfFiles.hashCode());
		result = prime * result
				+ ((nrOfMsgs == null) ? 0 : nrOfMsgs.hashCode());
		result = prime * result
				+ ((outgoing == null) ? 0 : outgoing.hashCode());
		result = prime * result
				+ ((processDate == null) ? 0 : processDate.hashCode());
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
		CasOpsMndtCountEntity other = (CasOpsMndtCountEntity) obj;
		if (fileName == null) {
			if (other.fileName != null)
				return false;
		} else if (!fileName.equals(other.fileName))
			return false;
		if (incoming == null) {
			if (other.incoming != null)
				return false;
		} else if (!incoming.equals(other.incoming))
			return false;
		if (casOpsMndtCountPK == null) {
			if (other.casOpsMndtCountPK != null)
				return false;
		} else if (!casOpsMndtCountPK.equals(other.casOpsMndtCountPK))
			return false;
		if (nrMsgsAccepted == null) {
			if (other.nrMsgsAccepted != null)
				return false;
		} else if (!nrMsgsAccepted.equals(other.nrMsgsAccepted))
			return false;
		if (nrMsgsExtracted == null) {
			if (other.nrMsgsExtracted != null)
				return false;
		} else if (!nrMsgsExtracted.equals(other.nrMsgsExtracted))
			return false;
		if (nrMsgsRejected == null) {
			if (other.nrMsgsRejected != null)
				return false;
		} else if (!nrMsgsRejected.equals(other.nrMsgsRejected))
			return false;
		if (nrOfFiles == null) {
			if (other.nrOfFiles != null)
				return false;
		} else if (!nrOfFiles.equals(other.nrOfFiles))
			return false;
		if (nrOfMsgs == null) {
			if (other.nrOfMsgs != null)
				return false;
		} else if (!nrOfMsgs.equals(other.nrOfMsgs))
			return false;
		if (outgoing == null) {
			if (other.outgoing != null)
				return false;
		} else if (!outgoing.equals(other.outgoing))
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
		return "CasOpsMndtCountEntity [mdtAcOpsMndtCountPK="
				+ casOpsMndtCountPK + ", nrOfFiles=" + nrOfFiles
				+ ", nrOfMsgs=" + nrOfMsgs + ", processDate=" + processDate
				+ ", incoming=" + incoming + ", outgoing=" + outgoing
				+ ", nrMsgsRejected=" + nrMsgsRejected + ", fileName="
				+ fileName + ", nrMsgsAccepted=" + nrMsgsAccepted
				+ ", nrMsgsExtracted=" + nrMsgsExtracted + "]";
	}

   
}
