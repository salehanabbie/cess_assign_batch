package com.bsva.entities;

import java.io.Serializable;
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
@Table(name = "MDT_OPS_CUST_PARAM",schema = "MANOWNER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MdtOpsCustParamEntity.findAll", query = "SELECT m FROM MdtOpsCustParamEntity m"),
    @NamedQuery(name = "MdtOpsCustParamEntity.findByInstId", query = "SELECT m FROM MdtOpsCustParamEntity m WHERE m.instId = :instId"),
    @NamedQuery(name = "MdtOpsCustParamEntity.findByManInitXsdNs", query = "SELECT m FROM MdtOpsCustParamEntity m WHERE m.manInitXsdNs = :manInitXsdNs"),
    @NamedQuery(name = "MdtOpsCustParamEntity.findByManInitLstSeq", query = "SELECT m FROM MdtOpsCustParamEntity m WHERE m.manInitLstSeq = :manInitLstSeq"),
    @NamedQuery(name = "MdtOpsCustParamEntity.findByManInitLastFileNr", query = "SELECT m FROM MdtOpsCustParamEntity m WHERE m.manInitLastFileNr = :manInitLastFileNr"),
    @NamedQuery(name = "MdtOpsCustParamEntity.findByManAmdXsdNs", query = "SELECT m FROM MdtOpsCustParamEntity m WHERE m.manAmdXsdNs = :manAmdXsdNs"),
    @NamedQuery(name = "MdtOpsCustParamEntity.findByManAmdLstSeq", query = "SELECT m FROM MdtOpsCustParamEntity m WHERE m.manAmdLstSeq = :manAmdLstSeq"),
    @NamedQuery(name = "MdtOpsCustParamEntity.findByManAmdLastFileNr", query = "SELECT m FROM MdtOpsCustParamEntity m WHERE m.manAmdLastFileNr = :manAmdLastFileNr"),
    @NamedQuery(name = "MdtOpsCustParamEntity.findByManCanXsdNs", query = "SELECT m FROM MdtOpsCustParamEntity m WHERE m.manCanXsdNs = :manCanXsdNs"),
    @NamedQuery(name = "MdtOpsCustParamEntity.findByManCanLstSeq", query = "SELECT m FROM MdtOpsCustParamEntity m WHERE m.manCanLstSeq = :manCanLstSeq"),
    @NamedQuery(name = "MdtOpsCustParamEntity.findByManCanLastFileNr", query = "SELECT m FROM MdtOpsCustParamEntity m WHERE m.manCanLastFileNr = :manCanLastFileNr"),
    @NamedQuery(name = "MdtOpsCustParamEntity.findByManAccpXsdNs", query = "SELECT m FROM MdtOpsCustParamEntity m WHERE m.manAccpXsdNs = :manAccpXsdNs"),
    @NamedQuery(name = "MdtOpsCustParamEntity.findByManAccpLstSeq", query = "SELECT m FROM MdtOpsCustParamEntity m WHERE m.manAccpLstSeq = :manAccpLstSeq"),
    @NamedQuery(name = "MdtOpsCustParamEntity.findByManAccpLastFileNr", query = "SELECT m FROM MdtOpsCustParamEntity m WHERE m.manAccpLastFileNr = :manAccpLastFileNr"),
    @NamedQuery(name = "MdtOpsCustParamEntity.findByActiveInd", query = "SELECT m FROM MdtOpsCustParamEntity m WHERE m.activeInd = :activeInd"),
    @NamedQuery(name = "MdtOpsCustParamEntity.findByCreatedBy", query = "SELECT m FROM MdtOpsCustParamEntity m WHERE m.createdBy = :createdBy"),
    @NamedQuery(name = "MdtOpsCustParamEntity.findByCreatedDate", query = "SELECT m FROM MdtOpsCustParamEntity m WHERE m.createdDate = :createdDate"),
    @NamedQuery(name = "MdtOpsCustParamEntity.findByMdtReqIdReuseInd", query = "SELECT m FROM MdtOpsCustParamEntity m WHERE m.mdtReqIdReuseInd = :mdtReqIdReuseInd"),
    @NamedQuery(name = "MdtOpsCustParamEntity.findByManStatusRepXsdNs", query = "SELECT m FROM MdtOpsCustParamEntity m WHERE m.manStatusRepXsdNs = :manStatusRepXsdNs"),
    @NamedQuery(name = "MdtOpsCustParamEntity.findByManStatusRepLstSeq", query = "SELECT m FROM MdtOpsCustParamEntity m WHERE m.manStatusRepLstSeq = :manStatusRepLstSeq"),
    @NamedQuery(name = "MdtOpsCustParamEntity.findByManStatusRepLastFileNr", query = "SELECT m FROM MdtOpsCustParamEntity m WHERE m.manStatusRepLastFileNr = :manStatusRepLastFileNr"),
    @NamedQuery(name = "MdtOpsCustParamEntity.findByManReqXsdNs", query = "SELECT m FROM MdtOpsCustParamEntity m WHERE m.manReqXsdNs = :manReqXsdNs"),
    @NamedQuery(name = "MdtOpsCustParamEntity.findByManReqLstSeq", query = "SELECT m FROM MdtOpsCustParamEntity m WHERE m.manReqLstSeq = :manReqLstSeq"),
    @NamedQuery(name = "MdtOpsCustParamEntity.findByManReqLastFileNr", query = "SELECT m FROM MdtOpsCustParamEntity m WHERE m.manReqLastFileNr = :manReqLastFileNr"),
    @NamedQuery(name = "MdtOpsCustParamEntity.findByManRespXsdNs", query = "SELECT m FROM MdtOpsCustParamEntity m WHERE m.manRespXsdNs = :manRespXsdNs"),
    @NamedQuery(name = "MdtOpsCustParamEntity.findByManRespLstSeq", query = "SELECT m FROM MdtOpsCustParamEntity m WHERE m.manRespLstSeq = :manRespLstSeq"),
    @NamedQuery(name = "MdtOpsCustParamEntity.findByManRespLastFileNr", query = "SELECT m FROM MdtOpsCustParamEntity m WHERE m.manRespLastFileNr = :manRespLastFileNr"),
    @NamedQuery(name = "MdtOpsCustParamEntity.findByManConfirmXsdNs", query = "SELECT m FROM MdtOpsCustParamEntity m WHERE m.manConfirmXsdNs = :manConfirmXsdNs"),
    @NamedQuery(name = "MdtOpsCustParamEntity.findByManConfirmLstSeq", query = "SELECT m FROM MdtOpsCustParamEntity m WHERE m.manConfirmLstSeq = :manConfirmLstSeq"),
    @NamedQuery(name = "MdtOpsCustParamEntity.findByManConfirmLstFileNr", query = "SELECT m FROM MdtOpsCustParamEntity m WHERE m.manConfirmLstFileNr = :manConfirmLstFileNr"),
    @NamedQuery(name = "MdtOpsCustParamEntity.findByManSuspXsdNs", query = "SELECT m FROM MdtOpsCustParamEntity m WHERE m.manSuspXsdNs = :manSuspXsdNs"),
    @NamedQuery(name = "MdtOpsCustParamEntity.findByManSuspLstSeq", query = "SELECT m FROM MdtOpsCustParamEntity m WHERE m.manSuspLstSeq = :manSuspLstSeq")})
public class MdtOpsCustParamEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 11)
    @Column(name = "INST_ID")
    private String instId;
    @Size(max = 50)
    @Column(name = "MAN_INIT_XSD_NS")
    private String manInitXsdNs;
    @Size(max = 6)
    @Column(name = "MAN_INIT_LST_SEQ")
    private String manInitLstSeq;
    @Size(max = 6)
    @Column(name = "MAN_INIT_LAST_FILE_NR")
    private String manInitLastFileNr;
    @Size(max = 50)
    @Column(name = "MAN_AMD_XSD_NS")
    private String manAmdXsdNs;
    @Size(max = 6)
    @Column(name = "MAN_AMD_LST_SEQ")
    private String manAmdLstSeq;
    @Size(max = 6)
    @Column(name = "MAN_AMD_LAST_FILE_NR")
    private String manAmdLastFileNr;
    @Size(max = 50)
    @Column(name = "MAN_CAN_XSD_NS")
    private String manCanXsdNs;
    @Size(max = 6)
    @Column(name = "MAN_CAN_LST_SEQ")
    private String manCanLstSeq;
    @Size(max = 6)
    @Column(name = "MAN_CAN_LAST_FILE_NR")
    private String manCanLastFileNr;
    @Size(max = 50)
    @Column(name = "MAN_ACCP_XSD_NS")
    private String manAccpXsdNs;
    @Size(max = 6)
    @Column(name = "MAN_ACCP_LST_SEQ")
    private String manAccpLstSeq;
    @Size(max = 6)
    @Column(name = "MAN_ACCP_LAST_FILE_NR")
    private String manAccpLastFileNr;
    @Size(max = 1)
    @Column(name = "ACTIVE_IND")
    private String activeInd;
    @Size(max = 25)
    @Column(name = "CREATED_BY")
    private String createdBy;
    @Column(name = "CREATED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Size(max = 1)
    @Column(name = "MDT_REQ_ID_REUSE_IND")
    private String mdtReqIdReuseInd;
    @Size(max = 50)
    @Column(name = "MAN_STATUS_REP_XSD_NS")
    private String manStatusRepXsdNs;
    @Size(max = 6)
    @Column(name = "MAN_STATUS_REP_LST_SEQ")
    private String manStatusRepLstSeq;
    @Size(max = 6)
    @Column(name = "MAN_STATUS_REP_LAST_FILE_NR")
    private String manStatusRepLastFileNr;
    @Size(max = 50)
    @Column(name = "MAN_REQ_XSD_NS")
    private String manReqXsdNs;
    @Size(max = 6)
    @Column(name = "MAN_REQ_LST_SEQ")
    private String manReqLstSeq;
    @Size(max = 6)
    @Column(name = "MAN_REQ_LAST_FILE_NR")
    private String manReqLastFileNr;
    @Size(max = 50)
    @Column(name = "MAN_RESP_XSD_NS")
    private String manRespXsdNs;
    @Size(max = 6)
    @Column(name = "MAN_RESP_LST_SEQ")
    private String manRespLstSeq;
    @Size(max = 6)
    @Column(name = "MAN_RESP_LAST_FILE_NR")
    private String manRespLastFileNr;
    @Size(max = 50)
    @Column(name = "MAN_CONFIRM_XSD_NS")
    private String manConfirmXsdNs;
    @Size(max = 6)
    @Column(name = "MAN_CONFIRM_LST_SEQ")
    private String manConfirmLstSeq;
    @Size(max = 6)
    @Column(name = "MAN_CONFIRM_LST_FILE_NR")
    private String manConfirmLstFileNr;
    @Size(max = 50)
    @Column(name = "MAN_SUSP_XSD_NS")
    private String manSuspXsdNs;
    @Size(max = 6)
    @Column(name = "MAN_SUSP_LST_SEQ")
    private String manSuspLstSeq;

    public MdtOpsCustParamEntity() {
    }

    public MdtOpsCustParamEntity(String instId) {
        this.instId = instId;
    }

    public String getInstId() {
        return instId;
    }

    public void setInstId(String instId) {
        this.instId = instId;
    }

    public String getManInitXsdNs() {
        return manInitXsdNs;
    }

    public void setManInitXsdNs(String manInitXsdNs) {
        this.manInitXsdNs = manInitXsdNs;
    }

    public String getManInitLstSeq() {
        return manInitLstSeq;
    }

    public void setManInitLstSeq(String manInitLstSeq) {
        this.manInitLstSeq = manInitLstSeq;
    }

    public String getManInitLastFileNr() {
        return manInitLastFileNr;
    }

    public void setManInitLastFileNr(String manInitLastFileNr) {
        this.manInitLastFileNr = manInitLastFileNr;
    }

    public String getManAmdXsdNs() {
        return manAmdXsdNs;
    }

    public void setManAmdXsdNs(String manAmdXsdNs) {
        this.manAmdXsdNs = manAmdXsdNs;
    }

    public String getManAmdLstSeq() {
        return manAmdLstSeq;
    }

    public void setManAmdLstSeq(String manAmdLstSeq) {
        this.manAmdLstSeq = manAmdLstSeq;
    }

    public String getManAmdLastFileNr() {
        return manAmdLastFileNr;
    }

    public void setManAmdLastFileNr(String manAmdLastFileNr) {
        this.manAmdLastFileNr = manAmdLastFileNr;
    }

    public String getManCanXsdNs() {
        return manCanXsdNs;
    }

    public void setManCanXsdNs(String manCanXsdNs) {
        this.manCanXsdNs = manCanXsdNs;
    }

    public String getManCanLstSeq() {
        return manCanLstSeq;
    }

    public void setManCanLstSeq(String manCanLstSeq) {
        this.manCanLstSeq = manCanLstSeq;
    }

    public String getManCanLastFileNr() {
        return manCanLastFileNr;
    }

    public void setManCanLastFileNr(String manCanLastFileNr) {
        this.manCanLastFileNr = manCanLastFileNr;
    }

    public String getManAccpXsdNs() {
        return manAccpXsdNs;
    }

    public void setManAccpXsdNs(String manAccpXsdNs) {
        this.manAccpXsdNs = manAccpXsdNs;
    }

    public String getManAccpLstSeq() {
        return manAccpLstSeq;
    }

    public void setManAccpLstSeq(String manAccpLstSeq) {
        this.manAccpLstSeq = manAccpLstSeq;
    }

    public String getManAccpLastFileNr() {
        return manAccpLastFileNr;
    }

    public void setManAccpLastFileNr(String manAccpLastFileNr) {
        this.manAccpLastFileNr = manAccpLastFileNr;
    }

    public String getActiveInd() {
        return activeInd;
    }

    public void setActiveInd(String activeInd) {
        this.activeInd = activeInd;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getMdtReqIdReuseInd() {
        return mdtReqIdReuseInd;
    }

    public void setMdtReqIdReuseInd(String mdtReqIdReuseInd) {
        this.mdtReqIdReuseInd = mdtReqIdReuseInd;
    }

    public String getManStatusRepXsdNs() {
        return manStatusRepXsdNs;
    }

    public void setManStatusRepXsdNs(String manStatusRepXsdNs) {
        this.manStatusRepXsdNs = manStatusRepXsdNs;
    }

    public String getManStatusRepLstSeq() {
        return manStatusRepLstSeq;
    }

    public void setManStatusRepLstSeq(String manStatusRepLstSeq) {
        this.manStatusRepLstSeq = manStatusRepLstSeq;
    }

    public String getManStatusRepLastFileNr() {
        return manStatusRepLastFileNr;
    }

    public void setManStatusRepLastFileNr(String manStatusRepLastFileNr) {
        this.manStatusRepLastFileNr = manStatusRepLastFileNr;
    }

    public String getManReqXsdNs() {
        return manReqXsdNs;
    }

    public void setManReqXsdNs(String manReqXsdNs) {
        this.manReqXsdNs = manReqXsdNs;
    }

    public String getManReqLstSeq() {
        return manReqLstSeq;
    }

    public void setManReqLstSeq(String manReqLstSeq) {
        this.manReqLstSeq = manReqLstSeq;
    }

    public String getManReqLastFileNr() {
        return manReqLastFileNr;
    }

    public void setManReqLastFileNr(String manReqLastFileNr) {
        this.manReqLastFileNr = manReqLastFileNr;
    }

    public String getManRespXsdNs() {
        return manRespXsdNs;
    }

    public void setManRespXsdNs(String manRespXsdNs) {
        this.manRespXsdNs = manRespXsdNs;
    }

    public String getManRespLstSeq() {
        return manRespLstSeq;
    }

    public void setManRespLstSeq(String manRespLstSeq) {
        this.manRespLstSeq = manRespLstSeq;
    }

    public String getManRespLastFileNr() {
        return manRespLastFileNr;
    }

    public void setManRespLastFileNr(String manRespLastFileNr) {
        this.manRespLastFileNr = manRespLastFileNr;
    }

    public String getManConfirmXsdNs() {
        return manConfirmXsdNs;
    }

    public void setManConfirmXsdNs(String manConfirmXsdNs) {
        this.manConfirmXsdNs = manConfirmXsdNs;
    }

    public String getManConfirmLstSeq() {
        return manConfirmLstSeq;
    }

    public void setManConfirmLstSeq(String manConfirmLstSeq) {
        this.manConfirmLstSeq = manConfirmLstSeq;
    }

    public String getManConfirmLstFileNr() {
        return manConfirmLstFileNr;
    }

    public void setManConfirmLstFileNr(String manConfirmLstFileNr) {
        this.manConfirmLstFileNr = manConfirmLstFileNr;
    }
    
    public String getManSuspXsdNs() {
        return manSuspXsdNs;
    }

    public void setManSuspXsdNs(String manSuspXsdNs) {
        this.manSuspXsdNs = manSuspXsdNs;
    }

    public String getManSuspLstSeq() {
        return manSuspLstSeq;
    }

    public void setManSuspLstSeq(String manSuspLstSeq) {
        this.manSuspLstSeq = manSuspLstSeq;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((activeInd == null) ? 0 : activeInd.hashCode());
		result = prime * result
				+ ((createdBy == null) ? 0 : createdBy.hashCode());
		result = prime * result
				+ ((createdDate == null) ? 0 : createdDate.hashCode());
		result = prime * result + ((instId == null) ? 0 : instId.hashCode());
		result = prime
				* result
				+ ((manAccpLastFileNr == null) ? 0 : manAccpLastFileNr
						.hashCode());
		result = prime * result
				+ ((manAccpLstSeq == null) ? 0 : manAccpLstSeq.hashCode());
		result = prime * result
				+ ((manAccpXsdNs == null) ? 0 : manAccpXsdNs.hashCode());
		result = prime
				* result
				+ ((manAmdLastFileNr == null) ? 0 : manAmdLastFileNr.hashCode());
		result = prime * result
				+ ((manAmdLstSeq == null) ? 0 : manAmdLstSeq.hashCode());
		result = prime * result
				+ ((manAmdXsdNs == null) ? 0 : manAmdXsdNs.hashCode());
		result = prime
				* result
				+ ((manCanLastFileNr == null) ? 0 : manCanLastFileNr.hashCode());
		result = prime * result
				+ ((manCanLstSeq == null) ? 0 : manCanLstSeq.hashCode());
		result = prime * result
				+ ((manCanXsdNs == null) ? 0 : manCanXsdNs.hashCode());
		result = prime
				* result
				+ ((manConfirmLstFileNr == null) ? 0 : manConfirmLstFileNr
						.hashCode());
		result = prime
				* result
				+ ((manConfirmLstSeq == null) ? 0 : manConfirmLstSeq.hashCode());
		result = prime * result
				+ ((manConfirmXsdNs == null) ? 0 : manConfirmXsdNs.hashCode());
		result = prime
				* result
				+ ((manInitLastFileNr == null) ? 0 : manInitLastFileNr
						.hashCode());
		result = prime * result
				+ ((manInitLstSeq == null) ? 0 : manInitLstSeq.hashCode());
		result = prime * result
				+ ((manInitXsdNs == null) ? 0 : manInitXsdNs.hashCode());
		result = prime
				* result
				+ ((manReqLastFileNr == null) ? 0 : manReqLastFileNr.hashCode());
		result = prime * result
				+ ((manReqLstSeq == null) ? 0 : manReqLstSeq.hashCode());
		result = prime * result
				+ ((manReqXsdNs == null) ? 0 : manReqXsdNs.hashCode());
		result = prime
				* result
				+ ((manRespLastFileNr == null) ? 0 : manRespLastFileNr
						.hashCode());
		result = prime * result
				+ ((manRespLstSeq == null) ? 0 : manRespLstSeq.hashCode());
		result = prime * result
				+ ((manRespXsdNs == null) ? 0 : manRespXsdNs.hashCode());
		result = prime
				* result
				+ ((manStatusRepLastFileNr == null) ? 0
						: manStatusRepLastFileNr.hashCode());
		result = prime
				* result
				+ ((manStatusRepLstSeq == null) ? 0 : manStatusRepLstSeq
						.hashCode());
		result = prime
				* result
				+ ((manStatusRepXsdNs == null) ? 0 : manStatusRepXsdNs
						.hashCode());
		result = prime * result
				+ ((manSuspLstSeq == null) ? 0 : manSuspLstSeq.hashCode());
		result = prime * result
				+ ((manSuspXsdNs == null) ? 0 : manSuspXsdNs.hashCode());
		result = prime
				* result
				+ ((mdtReqIdReuseInd == null) ? 0 : mdtReqIdReuseInd.hashCode());
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
		MdtOpsCustParamEntity other = (MdtOpsCustParamEntity) obj;
		if (activeInd == null) {
			if (other.activeInd != null)
				return false;
		} else if (!activeInd.equals(other.activeInd))
			return false;
		if (createdBy == null) {
			if (other.createdBy != null)
				return false;
		} else if (!createdBy.equals(other.createdBy))
			return false;
		if (createdDate == null) {
			if (other.createdDate != null)
				return false;
		} else if (!createdDate.equals(other.createdDate))
			return false;
		if (instId == null) {
			if (other.instId != null)
				return false;
		} else if (!instId.equals(other.instId))
			return false;
		if (manAccpLastFileNr == null) {
			if (other.manAccpLastFileNr != null)
				return false;
		} else if (!manAccpLastFileNr.equals(other.manAccpLastFileNr))
			return false;
		if (manAccpLstSeq == null) {
			if (other.manAccpLstSeq != null)
				return false;
		} else if (!manAccpLstSeq.equals(other.manAccpLstSeq))
			return false;
		if (manAccpXsdNs == null) {
			if (other.manAccpXsdNs != null)
				return false;
		} else if (!manAccpXsdNs.equals(other.manAccpXsdNs))
			return false;
		if (manAmdLastFileNr == null) {
			if (other.manAmdLastFileNr != null)
				return false;
		} else if (!manAmdLastFileNr.equals(other.manAmdLastFileNr))
			return false;
		if (manAmdLstSeq == null) {
			if (other.manAmdLstSeq != null)
				return false;
		} else if (!manAmdLstSeq.equals(other.manAmdLstSeq))
			return false;
		if (manAmdXsdNs == null) {
			if (other.manAmdXsdNs != null)
				return false;
		} else if (!manAmdXsdNs.equals(other.manAmdXsdNs))
			return false;
		if (manCanLastFileNr == null) {
			if (other.manCanLastFileNr != null)
				return false;
		} else if (!manCanLastFileNr.equals(other.manCanLastFileNr))
			return false;
		if (manCanLstSeq == null) {
			if (other.manCanLstSeq != null)
				return false;
		} else if (!manCanLstSeq.equals(other.manCanLstSeq))
			return false;
		if (manCanXsdNs == null) {
			if (other.manCanXsdNs != null)
				return false;
		} else if (!manCanXsdNs.equals(other.manCanXsdNs))
			return false;
		if (manConfirmLstFileNr == null) {
			if (other.manConfirmLstFileNr != null)
				return false;
		} else if (!manConfirmLstFileNr.equals(other.manConfirmLstFileNr))
			return false;
		if (manConfirmLstSeq == null) {
			if (other.manConfirmLstSeq != null)
				return false;
		} else if (!manConfirmLstSeq.equals(other.manConfirmLstSeq))
			return false;
		if (manConfirmXsdNs == null) {
			if (other.manConfirmXsdNs != null)
				return false;
		} else if (!manConfirmXsdNs.equals(other.manConfirmXsdNs))
			return false;
		if (manInitLastFileNr == null) {
			if (other.manInitLastFileNr != null)
				return false;
		} else if (!manInitLastFileNr.equals(other.manInitLastFileNr))
			return false;
		if (manInitLstSeq == null) {
			if (other.manInitLstSeq != null)
				return false;
		} else if (!manInitLstSeq.equals(other.manInitLstSeq))
			return false;
		if (manInitXsdNs == null) {
			if (other.manInitXsdNs != null)
				return false;
		} else if (!manInitXsdNs.equals(other.manInitXsdNs))
			return false;
		if (manReqLastFileNr == null) {
			if (other.manReqLastFileNr != null)
				return false;
		} else if (!manReqLastFileNr.equals(other.manReqLastFileNr))
			return false;
		if (manReqLstSeq == null) {
			if (other.manReqLstSeq != null)
				return false;
		} else if (!manReqLstSeq.equals(other.manReqLstSeq))
			return false;
		if (manReqXsdNs == null) {
			if (other.manReqXsdNs != null)
				return false;
		} else if (!manReqXsdNs.equals(other.manReqXsdNs))
			return false;
		if (manRespLastFileNr == null) {
			if (other.manRespLastFileNr != null)
				return false;
		} else if (!manRespLastFileNr.equals(other.manRespLastFileNr))
			return false;
		if (manRespLstSeq == null) {
			if (other.manRespLstSeq != null)
				return false;
		} else if (!manRespLstSeq.equals(other.manRespLstSeq))
			return false;
		if (manRespXsdNs == null) {
			if (other.manRespXsdNs != null)
				return false;
		} else if (!manRespXsdNs.equals(other.manRespXsdNs))
			return false;
		if (manStatusRepLastFileNr == null) {
			if (other.manStatusRepLastFileNr != null)
				return false;
		} else if (!manStatusRepLastFileNr.equals(other.manStatusRepLastFileNr))
			return false;
		if (manStatusRepLstSeq == null) {
			if (other.manStatusRepLstSeq != null)
				return false;
		} else if (!manStatusRepLstSeq.equals(other.manStatusRepLstSeq))
			return false;
		if (manStatusRepXsdNs == null) {
			if (other.manStatusRepXsdNs != null)
				return false;
		} else if (!manStatusRepXsdNs.equals(other.manStatusRepXsdNs))
			return false;
		if (manSuspLstSeq == null) {
			if (other.manSuspLstSeq != null)
				return false;
		} else if (!manSuspLstSeq.equals(other.manSuspLstSeq))
			return false;
		if (manSuspXsdNs == null) {
			if (other.manSuspXsdNs != null)
				return false;
		} else if (!manSuspXsdNs.equals(other.manSuspXsdNs))
			return false;
		if (mdtReqIdReuseInd == null) {
			if (other.mdtReqIdReuseInd != null)
				return false;
		} else if (!mdtReqIdReuseInd.equals(other.mdtReqIdReuseInd))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MdtOpsCustParamEntity [instId=" + instId + ", manInitXsdNs="
				+ manInitXsdNs + ", manInitLstSeq=" + manInitLstSeq
				+ ", manInitLastFileNr=" + manInitLastFileNr + ", manAmdXsdNs="
				+ manAmdXsdNs + ", manAmdLstSeq=" + manAmdLstSeq
				+ ", manAmdLastFileNr=" + manAmdLastFileNr + ", manCanXsdNs="
				+ manCanXsdNs + ", manCanLstSeq=" + manCanLstSeq
				+ ", manCanLastFileNr=" + manCanLastFileNr + ", manAccpXsdNs="
				+ manAccpXsdNs + ", manAccpLstSeq=" + manAccpLstSeq
				+ ", manAccpLastFileNr=" + manAccpLastFileNr + ", activeInd="
				+ activeInd + ", createdBy=" + createdBy + ", createdDate="
				+ createdDate + ", mdtReqIdReuseInd=" + mdtReqIdReuseInd
				+ ", manStatusRepXsdNs=" + manStatusRepXsdNs
				+ ", manStatusRepLstSeq=" + manStatusRepLstSeq
				+ ", manStatusRepLastFileNr=" + manStatusRepLastFileNr
				+ ", manReqXsdNs=" + manReqXsdNs + ", manReqLstSeq="
				+ manReqLstSeq + ", manReqLastFileNr=" + manReqLastFileNr
				+ ", manRespXsdNs=" + manRespXsdNs + ", manRespLstSeq="
				+ manRespLstSeq + ", manRespLastFileNr=" + manRespLastFileNr
				+ ", manConfirmXsdNs=" + manConfirmXsdNs
				+ ", manConfirmLstSeq=" + manConfirmLstSeq
				+ ", manConfirmLstFileNr=" + manConfirmLstFileNr
				+ ", manSuspXsdNs=" + manSuspXsdNs + ", manSuspLstSeq="
				+ manSuspLstSeq + "]";
	}

    
    
}
