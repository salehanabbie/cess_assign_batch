package com.bsva.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.persistence.NamedQueries;
/**
 * 
 * @author DimakatsoN
 *
 */
@Entity
@Table(name = "MDT_AC_ARC_FILE_REG",schema = "MANOWNER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MdtAcArcFileRegEntity.findAll", query = "SELECT m FROM MdtAcArcFileRegEntity m"),
    @NamedQuery(name = "MdtAcArcFileRegEntity.findByFileName", query = "SELECT m FROM MdtAcArcFileRegEntity m WHERE m.fileName = :fileName"),
    @NamedQuery(name = "MdtAcArcFileRegEntity.findByFilepath", query = "SELECT m FROM MdtAcArcFileRegEntity m WHERE m.filepath = :filepath"),
    @NamedQuery(name = "MdtAcArcFileRegEntity.findByStatus", query = "SELECT m FROM MdtAcArcFileRegEntity m WHERE m.status = :status"),
    @NamedQuery(name = "MdtAcArcFileRegEntity.findByReason", query = "SELECT m FROM MdtAcArcFileRegEntity m WHERE m.reason = :reason"),
    @NamedQuery(name = "MdtAcArcFileRegEntity.findByProcessDate", query = "SELECT m FROM MdtAcArcFileRegEntity m WHERE m.processDate = :processDate"),
    @NamedQuery(name = "MdtAcArcFileRegEntity.findByNameSpace", query = "SELECT m FROM MdtAcArcFileRegEntity m WHERE m.nameSpace = :nameSpace"),
    @NamedQuery(name = "MdtAcArcFileRegEntity.findByGrpHdrMsgId", query = "SELECT m FROM MdtAcArcFileRegEntity m WHERE m.grpHdrMsgId = :grpHdrMsgId"),
    @NamedQuery(name = "MdtAcArcFileRegEntity.findByOnlineInd", query = "SELECT m FROM MdtAcArcFileRegEntity m WHERE m.onlineInd = :onlineInd"),
    @NamedQuery(name = "MdtAcArcFileRegEntity.findByInOutInd", query = "SELECT m FROM MdtAcArcFileRegEntity m WHERE m.inOutInd = :inOutInd"),
    @NamedQuery(name = "MdtAcArcFileRegEntity.findByExtractMsgId", query = "SELECT m FROM MdtAcArcFileRegEntity m WHERE m.extractMsgId = :extractMsgId"),
    @NamedQuery(name = "MdtAcArcFileRegEntity.findByArchiveDate", query = "SELECT m FROM MdtAcArcFileRegEntity m WHERE m.archiveDate = :archiveDate")})

public class MdtAcArcFileRegEntity implements Serializable{
	
	 private static final long serialVersionUID = 1L;
	    @Id
	    @Basic(optional = false)
	    @Column(name = "FILE_NAME")
	    private String fileName;
	    @Column(name = "FILEPATH")
	    private String filepath;
	    @Column(name = "STATUS")
	    private String status;
	    @Column(name = "REASON")
	    private String reason;
	    @Column(name = "PROCESS_DATE")
	    @Temporal(TemporalType.TIMESTAMP)
	    private Date processDate;
	    @Column(name = "NAME_SPACE")
	    private String nameSpace;
	    @Column(name = "GRP_HDR_MSG_ID")
	    private String grpHdrMsgId;
	    @Column(name = "ONLINE_IND")
	    private String onlineInd;
	    @Column(name = "IN_OUT_IND")
	    private String inOutInd;
	    @Column(name = "EXTRACT_MSG_ID")
	    private String extractMsgId;
	    @Column(name = "ARCHIVE_DATE")
	    @Temporal(TemporalType.TIMESTAMP)
	    private Date archiveDate;

	    public MdtAcArcFileRegEntity() {
	    }

	    public MdtAcArcFileRegEntity(String fileName) {
	        this.fileName = fileName;
	    }

	    public String getFileName() {
	        return fileName;
	    }

	    public void setFileName(String fileName) {
	        this.fileName = fileName;
	    }

	    public String getFilepath() {
	        return filepath;
	    }

	    public void setFilepath(String filepath) {
	        this.filepath = filepath;
	    }

	    public String getStatus() {
	        return status;
	    }

	    public void setStatus(String status) {
	        this.status = status;
	    }

	    public String getReason() {
	        return reason;
	    }

	    public void setReason(String reason) {
	        this.reason = reason;
	    }

	    public Date getProcessDate() {
	        return processDate;
	    }

	    public void setProcessDate(Date processDate) {
	        this.processDate = processDate;
	    }

	    public String getNameSpace() {
	        return nameSpace;
	    }

	    public void setNameSpace(String nameSpace) {
	        this.nameSpace = nameSpace;
	    }

	    public String getGrpHdrMsgId() {
	        return grpHdrMsgId;
	    }

	    public void setGrpHdrMsgId(String grpHdrMsgId) {
	        this.grpHdrMsgId = grpHdrMsgId;
	    }

	    public String getOnlineInd() {
	        return onlineInd;
	    }

	    public void setOnlineInd(String onlineInd) {
	        this.onlineInd = onlineInd;
	    }

	    public String getInOutInd() {
	        return inOutInd;
	    }

	    public void setInOutInd(String inOutInd) {
	        this.inOutInd = inOutInd;
	    }

	    public String getExtractMsgId() {
	        return extractMsgId;
	    }

	    public void setExtractMsgId(String extractMsgId) {
	        this.extractMsgId = extractMsgId;
	    }

	    public Date getArchiveDate() {
	        return archiveDate;
	    }

	    public void setArchiveDate(Date archiveDate) {
	        this.archiveDate = archiveDate;
	    }

	    @Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((archiveDate == null) ? 0 : archiveDate.hashCode());
			result = prime * result + ((extractMsgId == null) ? 0 : extractMsgId.hashCode());
			result = prime * result + ((fileName == null) ? 0 : fileName.hashCode());
			result = prime * result + ((filepath == null) ? 0 : filepath.hashCode());
			result = prime * result + ((grpHdrMsgId == null) ? 0 : grpHdrMsgId.hashCode());
			result = prime * result + ((inOutInd == null) ? 0 : inOutInd.hashCode());
			result = prime * result + ((nameSpace == null) ? 0 : nameSpace.hashCode());
			result = prime * result + ((onlineInd == null) ? 0 : onlineInd.hashCode());
			result = prime * result + ((processDate == null) ? 0 : processDate.hashCode());
			result = prime * result + ((reason == null) ? 0 : reason.hashCode());
			result = prime * result + ((status == null) ? 0 : status.hashCode());
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
			MdtAcArcFileRegEntity other = (MdtAcArcFileRegEntity) obj;
			if (archiveDate == null) {
				if (other.archiveDate != null)
					return false;
			} else if (!archiveDate.equals(other.archiveDate))
				return false;
			if (extractMsgId == null) {
				if (other.extractMsgId != null)
					return false;
			} else if (!extractMsgId.equals(other.extractMsgId))
				return false;
			if (fileName == null) {
				if (other.fileName != null)
					return false;
			} else if (!fileName.equals(other.fileName))
				return false;
			if (filepath == null) {
				if (other.filepath != null)
					return false;
			} else if (!filepath.equals(other.filepath))
				return false;
			if (grpHdrMsgId == null) {
				if (other.grpHdrMsgId != null)
					return false;
			} else if (!grpHdrMsgId.equals(other.grpHdrMsgId))
				return false;
			if (inOutInd == null) {
				if (other.inOutInd != null)
					return false;
			} else if (!inOutInd.equals(other.inOutInd))
				return false;
			if (nameSpace == null) {
				if (other.nameSpace != null)
					return false;
			} else if (!nameSpace.equals(other.nameSpace))
				return false;
			if (onlineInd == null) {
				if (other.onlineInd != null)
					return false;
			} else if (!onlineInd.equals(other.onlineInd))
				return false;
			if (processDate == null) {
				if (other.processDate != null)
					return false;
			} else if (!processDate.equals(other.processDate))
				return false;
			if (reason == null) {
				if (other.reason != null)
					return false;
			} else if (!reason.equals(other.reason))
				return false;
			if (status == null) {
				if (other.status != null)
					return false;
			} else if (!status.equals(other.status))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "MdtAcArcFileRegEntity [fileName=" + fileName + ", filepath=" + filepath + ", status=" + status
					+ ", reason=" + reason + ", processDate=" + processDate + ", nameSpace=" + nameSpace
					+ ", grpHdrMsgId=" + grpHdrMsgId + ", onlineInd=" + onlineInd + ", inOutInd=" + inOutInd
					+ ", extractMsgId=" + extractMsgId + ", archiveDate=" + archiveDate + "]";
		}


	    
}
