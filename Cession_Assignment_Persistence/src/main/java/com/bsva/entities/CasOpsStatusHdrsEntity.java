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
 * @author DimakatsoN
 */
//@Entity
//@Table(name = "OPS_STATUS_HDRS")
//@XmlRootElement
//@NamedQueries({
//    @NamedQuery(name = "OpsStatusHdrsEntity.findAll", query = "SELECT o FROM OpsStatusHdrsEntity o"),
//    @NamedQuery(name = "OpsStatusHdrsEntity.findBySystemSeqNo", query = "SELECT o FROM OpsStatusHdrsEntity o WHERE o.systemSeqNo = :systemSeqNo"),
//    @NamedQuery(name = "OpsStatusHdrsEntity.findByHdrMsgId", query = "SELECT o FROM OpsStatusHdrsEntity o WHERE o.hdrMsgId = :hdrMsgId"),
//    @NamedQuery(name = "OpsStatusHdrsEntity.findByCreateDateTime", query = "SELECT o FROM OpsStatusHdrsEntity o WHERE o.createDateTime = :createDateTime"),
//    @NamedQuery(name = "OpsStatusHdrsEntity.findByInstgAgt", query = "SELECT o FROM OpsStatusHdrsEntity o WHERE o.instgAgt = :instgAgt"),
//    @NamedQuery(name = "OpsStatusHdrsEntity.findByInstdAgt", query = "SELECT o FROM OpsStatusHdrsEntity o WHERE o.instdAgt = :instdAgt"),
//    @NamedQuery(name = "OpsStatusHdrsEntity.findByOrgnlMsgId", query = "SELECT o FROM OpsStatusHdrsEntity o WHERE o.orgnlMsgId = :orgnlMsgId"),
//    @NamedQuery(name = "OpsStatusHdrsEntity.findByOrgnlMsgName", query = "SELECT o FROM OpsStatusHdrsEntity o WHERE o.orgnlMsgName = :orgnlMsgName"),
//    @NamedQuery(name = "OpsStatusHdrsEntity.findByOrgnlCreateDateTime", query = "SELECT o FROM OpsStatusHdrsEntity o WHERE o.orgnlCreateDateTime = :orgnlCreateDateTime"),
//    @NamedQuery(name = "OpsStatusHdrsEntity.findByOrgnlNoOfTxns", query = "SELECT o FROM OpsStatusHdrsEntity o WHERE o.orgnlNoOfTxns = :orgnlNoOfTxns"),
//    @NamedQuery(name = "OpsStatusHdrsEntity.findByOrgnlCntlSum", query = "SELECT o FROM OpsStatusHdrsEntity o WHERE o.orgnlCntlSum = :orgnlCntlSum"),
//    @NamedQuery(name = "OpsStatusHdrsEntity.findByProcessStatus", query = "SELECT o FROM OpsStatusHdrsEntity o WHERE o.processStatus = :processStatus"),
//    @NamedQuery(name = "OpsStatusHdrsEntity.findByIN", query = "SELECT o FROM OpsStatusHdrsEntity o WHERE o.processStatus IN (:processStatus1, :processStatus2)"),
//    @NamedQuery(name = "OpsStatusHdrsEntity.findByGroupStatus", query = "SELECT o FROM OpsStatusHdrsEntity o WHERE o.groupStatus = :groupStatus"),
//    @NamedQuery(name = "OpsStatusHdrsEntity.findByService", query = "SELECT o FROM OpsStatusHdrsEntity o WHERE o.service = :service"),
//    @NamedQuery(name = "OpsStatusHdrsEntity.findByVetRunNumber", query = "SELECT o FROM OpsStatusHdrsEntity o WHERE o.vetRunNumber = :vetRunNumber"),
//    @NamedQuery(name = "OpsStatusHdrsEntity.findByWorkunitRefNo", query = "SELECT o FROM OpsStatusHdrsEntity o WHERE o.workunitRefNo = :workunitRefNo")})
@Entity
@Table(name = "CAS_OPS_STATUS_HDRS",schema = "CAMOWNER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CasOpsStatusHdrsEntity.findAll", query = "SELECT m FROM CasOpsStatusHdrsEntity m"),
    @NamedQuery(name = "CasOpsStatusHdrsEntity.findBySystemSeqNo", query = "SELECT m FROM CasOpsStatusHdrsEntity m WHERE m.systemSeqNo = :systemSeqNo"),
    @NamedQuery(name = "CasOpsStatusHdrsEntity.findByHdrMsgId", query = "SELECT m FROM CasOpsStatusHdrsEntity m WHERE m.hdrMsgId = :hdrMsgId"),
    @NamedQuery(name = "CasOpsStatusHdrsEntity.findByCreateDateTime", query = "SELECT m FROM CasOpsStatusHdrsEntity m WHERE m.createDateTime = :createDateTime"),
    @NamedQuery(name = "CasOpsStatusHdrsEntity.findByInstgAgt", query = "SELECT m FROM CasOpsStatusHdrsEntity m WHERE m.instgAgt = :instgAgt"),
    @NamedQuery(name = "CasOpsStatusHdrsEntity.findByInstdAgt", query = "SELECT m FROM CasOpsStatusHdrsEntity m WHERE m.instdAgt = :instdAgt"),
    @NamedQuery(name = "CasOpsStatusHdrsEntity.findByOrgnlMsgId", query = "SELECT m FROM CasOpsStatusHdrsEntity m WHERE m.orgnlMsgId = :orgnlMsgId"),
    @NamedQuery(name = "CasOpsStatusHdrsEntity.findByOrgnlMsgName", query = "SELECT m FROM CasOpsStatusHdrsEntity m WHERE m.orgnlMsgName = :orgnlMsgName"),
    @NamedQuery(name = "CasOpsStatusHdrsEntity.findByOrgnlCreateDateTime", query = "SELECT m FROM CasOpsStatusHdrsEntity m WHERE m.orgnlCreateDateTime = :orgnlCreateDateTime"),
    @NamedQuery(name = "CasOpsStatusHdrsEntity.findByOrgnlNoOfTxns", query = "SELECT m FROM CasOpsStatusHdrsEntity m WHERE m.orgnlNoOfTxns = :orgnlNoOfTxns"),
    @NamedQuery(name = "CasOpsStatusHdrsEntity.findByOrgnlCntlSum", query = "SELECT m FROM CasOpsStatusHdrsEntity m WHERE m.orgnlCntlSum = :orgnlCntlSum"),
    @NamedQuery(name = "CasOpsStatusHdrsEntity.findByProcessStatus", query = "SELECT m FROM CasOpsStatusHdrsEntity m WHERE m.processStatus = :processStatus"),
    @NamedQuery(name = "CasOpsStatusHdrsEntity.findByIN", query = "SELECT o FROM CasOpsStatusHdrsEntity o WHERE o.processStatus IN (:processStatus1, :processStatus2)"),
    @NamedQuery(name = "CasOpsStatusHdrsEntity.findByServiceIdIN", query = "SELECT o FROM CasOpsStatusHdrsEntity o WHERE o.service IN (:service1, :service2)"),
    @NamedQuery(name = "CasOpsStatusHdrsEntity.findByGroupStatus", query = "SELECT m FROM CasOpsStatusHdrsEntity m WHERE m.groupStatus = :groupStatus"),
    @NamedQuery(name = "CasOpsStatusHdrsEntity.findByService", query = "SELECT m FROM CasOpsStatusHdrsEntity m WHERE m.service = :service"),
    @NamedQuery(name = "CasOpsStatusHdrsEntity.findByVetRunNumber", query = "SELECT m FROM CasOpsStatusHdrsEntity m WHERE m.vetRunNumber = :vetRunNumber"),
    @NamedQuery(name = "CasOpsStatusHdrsEntity.findByWorkunitRefNo", query = "SELECT m FROM CasOpsStatusHdrsEntity m WHERE m.workunitRefNo = :workunitRefNo"),
    @NamedQuery(name = "CasOpsStatusHdrsEntity.findByProStatusAndService", query = "SELECT m FROM CasOpsStatusHdrsEntity m WHERE m.service = :service and m.processStatus = :processStatus and TRUNC(m.createDateTime) = TO_DATE(:createDateTime,'YYYY-MM-DD') and m.instgAgt = :memberId"),
    @NamedQuery(name = "CasOpsStatusHdrsEntity.findByOrgnlFileName", query = "SELECT m FROM CasOpsStatusHdrsEntity m WHERE m.orgnlFileName = :orgnlFileName"),
    @NamedQuery(name = "CasOpsStatusHdrsEntity.findByExtractFileName", query = "SELECT m FROM CasOpsStatusHdrsEntity m WHERE m.extractFileName = :extractFileName")})
public class CasOpsStatusHdrsEntity implements Serializable {
    private static final long serialVersionUID = 1L;
 
    /*    @SequenceGenerator(name="statusHdr_seq", sequenceName="OPS_STATUS_HDRS_SEQ", allocationSize = 1) 
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="statusHdr_seq")*/
    @GenericGenerator(name = "generator", strategy = "sequence-identity", parameters = @Parameter(name = "sequence", value = "CAMOWNER.CAS_OPS_STATUS_HDRS_SEQ") )
    @GeneratedValue(generator = "generator")
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SYSTEM_SEQ_NO")  
    private BigDecimal systemSeqNo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 35)
    @Column(name = "HDR_MSG_ID")
    private String hdrMsgId;
    @Column(name = "CREATE_DATE_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDateTime;
    @Size(max = 11)
    @Column(name = "INSTG_AGT")
    private String instgAgt;
    @Size(max = 11)
    @Column(name = "INSTD_AGT")
    private String instdAgt;
    @Size(max = 35)
    @Column(name = "ORGNL_MSG_ID")
    private String orgnlMsgId;
    @Size(max = 35)
    @Column(name = "ORGNL_MSG_NAME")
    private String orgnlMsgName;
    @Column(name = "ORGNL_CREATE_DATE_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date orgnlCreateDateTime;
    @Column(name = "ORGNL_NO_OF_TXNS")
    private Long orgnlNoOfTxns;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "ORGNL_CNTL_SUM")
    private BigDecimal orgnlCntlSum;
    @Size(max = 1)
    @Column(name = "PROCESS_STATUS")
    private String processStatus;
    @Size(max = 4)
    @Column(name = "GROUP_STATUS")
    private String groupStatus;
    @Size(max = 5)
    @Column(name = "SERVICE")
    private String service;
    @Column(name = "VET_RUN_NUMBER")
    private Long vetRunNumber;
    @Size(max = 16)
    @Column(name = "WORKUNIT_REF_NO")
    private String workunitRefNo;
    @Size(max = 40)
    @Column(name = "ORGNL_FILE_NAME")
    private String orgnlFileName;
    @Size(max = 40)
    @Column(name = "EXTRACT_FILE_NAME")
    private String extractFileName;


    public CasOpsStatusHdrsEntity() {
    }

    public CasOpsStatusHdrsEntity(BigDecimal systemSeqNo) {
        this.systemSeqNo = systemSeqNo;
    }

    public CasOpsStatusHdrsEntity(BigDecimal systemSeqNo, String hdrMsgId) {
        this.systemSeqNo = systemSeqNo;
        this.hdrMsgId = hdrMsgId;
    }

    public BigDecimal getSystemSeqNo() {
        return systemSeqNo;
    }

    public void setSystemSeqNo(BigDecimal systemSeqNo) {
        this.systemSeqNo = systemSeqNo;
    }

    public String getHdrMsgId() {
        return hdrMsgId;
    }

    public void setHdrMsgId(String hdrMsgId) {
        this.hdrMsgId = hdrMsgId;
    }

    public Date getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(Date createDateTime) {
        this.createDateTime = createDateTime;
    }

    public String getInstgAgt() {
        return instgAgt;
    }

    public void setInstgAgt(String instgAgt) {
        this.instgAgt = instgAgt;
    }

    public String getInstdAgt() {
        return instdAgt;
    }

    public void setInstdAgt(String instdAgt) {
        this.instdAgt = instdAgt;
    }

    public String getOrgnlMsgId() {
        return orgnlMsgId;
    }

    public void setOrgnlMsgId(String orgnlMsgId) {
        this.orgnlMsgId = orgnlMsgId;
    }

    public String getOrgnlMsgName() {
        return orgnlMsgName;
    }

    public void setOrgnlMsgName(String orgnlMsgName) {
        this.orgnlMsgName = orgnlMsgName;
    }

    public Date getOrgnlCreateDateTime() {
        return orgnlCreateDateTime;
    }

    public void setOrgnlCreateDateTime(Date orgnlCreateDateTime) {
        this.orgnlCreateDateTime = orgnlCreateDateTime;
    }

    public Long getOrgnlNoOfTxns() {
        return orgnlNoOfTxns;
    }

    public void setOrgnlNoOfTxns(Long orgnlNoOfTxns) {
        this.orgnlNoOfTxns = orgnlNoOfTxns;
    }

    public BigDecimal getOrgnlCntlSum() {
        return orgnlCntlSum;
    }

    public void setOrgnlCntlSum(BigDecimal orgnlCntlSum) {
        this.orgnlCntlSum = orgnlCntlSum;
    }

    public String getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(String processStatus) {
        this.processStatus = processStatus;
    }

    public String getGroupStatus() {
        return groupStatus;
    }

    public void setGroupStatus(String groupStatus) {
        this.groupStatus = groupStatus;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public Long getVetRunNumber() {
        return vetRunNumber;
    }

    public void setVetRunNumber(Long vetRunNumber) {
        this.vetRunNumber = vetRunNumber;
    }

    public String getWorkunitRefNo() {
        return workunitRefNo;
    }

    public void setWorkunitRefNo(String workunitRefNo) {
        this.workunitRefNo = workunitRefNo;
    }
    
    public String getOrgnlFileName() {
        return orgnlFileName;
    }

    public void setOrgnlFileName(String orgnlFileName) {
        this.orgnlFileName = orgnlFileName;
    }
    
    public String getExtractFileName() {
        return extractFileName;
    }

    public void setExtractFileName(String extractFileName) {
        this.extractFileName = extractFileName;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createDateTime == null) ? 0 : createDateTime.hashCode());
		result = prime * result + ((extractFileName == null) ? 0 : extractFileName.hashCode());
		result = prime * result + ((groupStatus == null) ? 0 : groupStatus.hashCode());
		result = prime * result + ((hdrMsgId == null) ? 0 : hdrMsgId.hashCode());
		result = prime * result + ((instdAgt == null) ? 0 : instdAgt.hashCode());
		result = prime * result + ((instgAgt == null) ? 0 : instgAgt.hashCode());
		result = prime * result + ((orgnlCntlSum == null) ? 0 : orgnlCntlSum.hashCode());
		result = prime * result + ((orgnlCreateDateTime == null) ? 0 : orgnlCreateDateTime.hashCode());
		result = prime * result + ((orgnlFileName == null) ? 0 : orgnlFileName.hashCode());
		result = prime * result + ((orgnlMsgId == null) ? 0 : orgnlMsgId.hashCode());
		result = prime * result + ((orgnlMsgName == null) ? 0 : orgnlMsgName.hashCode());
		result = prime * result + ((orgnlNoOfTxns == null) ? 0 : orgnlNoOfTxns.hashCode());
		result = prime * result + ((processStatus == null) ? 0 : processStatus.hashCode());
		result = prime * result + ((service == null) ? 0 : service.hashCode());
		result = prime * result + ((systemSeqNo == null) ? 0 : systemSeqNo.hashCode());
		result = prime * result + ((vetRunNumber == null) ? 0 : vetRunNumber.hashCode());
		result = prime * result + ((workunitRefNo == null) ? 0 : workunitRefNo.hashCode());
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
		CasOpsStatusHdrsEntity other = (CasOpsStatusHdrsEntity) obj;
		if (createDateTime == null) {
			if (other.createDateTime != null)
				return false;
		} else if (!createDateTime.equals(other.createDateTime))
			return false;
		if (extractFileName == null) {
			if (other.extractFileName != null)
				return false;
		} else if (!extractFileName.equals(other.extractFileName))
			return false;
		if (groupStatus == null) {
			if (other.groupStatus != null)
				return false;
		} else if (!groupStatus.equals(other.groupStatus))
			return false;
		if (hdrMsgId == null) {
			if (other.hdrMsgId != null)
				return false;
		} else if (!hdrMsgId.equals(other.hdrMsgId))
			return false;
		if (instdAgt == null) {
			if (other.instdAgt != null)
				return false;
		} else if (!instdAgt.equals(other.instdAgt))
			return false;
		if (instgAgt == null) {
			if (other.instgAgt != null)
				return false;
		} else if (!instgAgt.equals(other.instgAgt))
			return false;
		if (orgnlCntlSum == null) {
			if (other.orgnlCntlSum != null)
				return false;
		} else if (!orgnlCntlSum.equals(other.orgnlCntlSum))
			return false;
		if (orgnlCreateDateTime == null) {
			if (other.orgnlCreateDateTime != null)
				return false;
		} else if (!orgnlCreateDateTime.equals(other.orgnlCreateDateTime))
			return false;
		if (orgnlFileName == null) {
			if (other.orgnlFileName != null)
				return false;
		} else if (!orgnlFileName.equals(other.orgnlFileName))
			return false;
		if (orgnlMsgId == null) {
			if (other.orgnlMsgId != null)
				return false;
		} else if (!orgnlMsgId.equals(other.orgnlMsgId))
			return false;
		if (orgnlMsgName == null) {
			if (other.orgnlMsgName != null)
				return false;
		} else if (!orgnlMsgName.equals(other.orgnlMsgName))
			return false;
		if (orgnlNoOfTxns == null) {
			if (other.orgnlNoOfTxns != null)
				return false;
		} else if (!orgnlNoOfTxns.equals(other.orgnlNoOfTxns))
			return false;
		if (processStatus == null) {
			if (other.processStatus != null)
				return false;
		} else if (!processStatus.equals(other.processStatus))
			return false;
		if (service == null) {
			if (other.service != null)
				return false;
		} else if (!service.equals(other.service))
			return false;
		if (systemSeqNo == null) {
			if (other.systemSeqNo != null)
				return false;
		} else if (!systemSeqNo.equals(other.systemSeqNo))
			return false;
		if (vetRunNumber == null) {
			if (other.vetRunNumber != null)
				return false;
		} else if (!vetRunNumber.equals(other.vetRunNumber))
			return false;
		if (workunitRefNo == null) {
			if (other.workunitRefNo != null)
				return false;
		} else if (!workunitRefNo.equals(other.workunitRefNo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CasOpsStatusHdrsEntity [systemSeqNo=" + systemSeqNo + ", hdrMsgId=" + hdrMsgId + ", createDateTime="
				+ createDateTime + ", instgAgt=" + instgAgt + ", instdAgt=" + instdAgt + ", orgnlMsgId=" + orgnlMsgId
				+ ", orgnlMsgName=" + orgnlMsgName + ", orgnlCreateDateTime=" + orgnlCreateDateTime + ", orgnlNoOfTxns="
				+ orgnlNoOfTxns + ", orgnlCntlSum=" + orgnlCntlSum + ", processStatus=" + processStatus
				+ ", groupStatus=" + groupStatus + ", service=" + service + ", vetRunNumber=" + vetRunNumber
				+ ", workunitRefNo=" + workunitRefNo + ", orgnlFileName=" + orgnlFileName + ", extractFileName="
				+ extractFileName + "]";
	}
    



   
    
}
