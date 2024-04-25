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
@Entity
@Table(name = "MDT_AC_OPS_DAILY_BILLING", schema="MANOWNER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MdtAcOpsDailyBillingEntity.findAll", query = "SELECT m FROM MdtAcOpsDailyBillingEntity m"),
    @NamedQuery(name = "MdtAcOpsDailyBillingEntity.findBySystemSeqNo", query = "SELECT m FROM MdtAcOpsDailyBillingEntity m WHERE m.systemSeqNo = :systemSeqNo"),
    @NamedQuery(name = "MdtAcOpsDailyBillingEntity.findByCreditorBank", query = "SELECT m FROM MdtAcOpsDailyBillingEntity m WHERE m.creditorBank = :creditorBank"),
    @NamedQuery(name = "MdtAcOpsDailyBillingEntity.findByDebtorBank", query = "SELECT m FROM MdtAcOpsDailyBillingEntity m WHERE m.debtorBank = :debtorBank"),
    @NamedQuery(name = "MdtAcOpsDailyBillingEntity.findBySubService", query = "SELECT m FROM MdtAcOpsDailyBillingEntity m WHERE m.subService = :subService"),
    @NamedQuery(name = "MdtAcOpsDailyBillingEntity.findByTxnType", query = "SELECT m FROM MdtAcOpsDailyBillingEntity m WHERE m.txnType = :txnType"),
    @NamedQuery(name = "MdtAcOpsDailyBillingEntity.findByTxnStatus", query = "SELECT m FROM MdtAcOpsDailyBillingEntity m WHERE m.txnStatus = :txnStatus"),
    @NamedQuery(name = "MdtAcOpsDailyBillingEntity.findByCreatedBy", query = "SELECT m FROM MdtAcOpsDailyBillingEntity m WHERE m.createdBy = :createdBy"),
    @NamedQuery(name = "MdtAcOpsDailyBillingEntity.findByCreatedDate", query = "SELECT m FROM MdtAcOpsDailyBillingEntity m WHERE m.createdDate = :createdDate"),
    @NamedQuery(name = "MdtAcOpsDailyBillingEntity.findByBillExpStatus", query = "SELECT m FROM MdtAcOpsDailyBillingEntity m WHERE m.billExpStatus = :billExpStatus"),
	@NamedQuery(name = "MdtAcOpsDailyBillingEntity.findByActionDate", query = "SELECT m FROM MdtAcOpsDailyBillingEntity m WHERE m.actionDate = :actionDate"),
	@NamedQuery(name = "MdtAcOpsDailyBillingEntity.findByAuthCode", query = "SELECT m FROM MdtAcOpsDailyBillingEntity m WHERE m.authCode = :authCode"),
	@NamedQuery(name = "MdtAcOpsDailyBillingEntity.findByTxnId", query = "SELECT m FROM MdtAcOpsDailyBillingEntity m WHERE m.txnId = :txnId"),
	@NamedQuery(name = "MdtAcOpsDailyBillingEntity.findByMndtRefNum", query = "SELECT m FROM MdtAcOpsDailyBillingEntity m WHERE m.mndtRefNum = :mndtRefNum"),
	@NamedQuery(name = "MdtAcOpsDailyBillingEntity.findByExtMsgId", query = "SELECT m FROM MdtAcOpsDailyBillingEntity m WHERE m.extMsgId = :extMsgId"),
	@NamedQuery(name = "MdtAcOpsDailyBillingEntity.findByRespDate", query = "SELECT m FROM MdtAcOpsDailyBillingEntity m WHERE m.respDate = :respDate"),
	@NamedQuery(name = "MdtAcOpsDailyBillingEntity.findByCreatedDateSubSTR", query = "SELECT m FROM MdtAcOpsDailyBillingEntity m WHERE m.creditorBank = :creditorBank and m.actionDate = :actionDate"),
	@NamedQuery(name = "MdtAcOpsDailyBillingEntity.findByRealTimebyTxnType", query = "SELECT m FROM MdtAcOpsDailyBillingEntity m WHERE (m.txnType = :txnType1 OR m.txnType = :txnType2)")})
	public class MdtAcOpsDailyBillingEntity implements Serializable 
	{
	    private static final long serialVersionUID = 1L;
	    @GenericGenerator(name = "generator", strategy = "sequence-identity", parameters = @Parameter(name = "sequence", value = "MANOWNER.MDT_AC_OPS_DAILY_BILLING_SEQ") )
	    @GeneratedValue(generator = "generator")
	    @Id
	    @Basic(optional = false)
	    @NotNull
	    @Column(name = "SYSTEM_SEQ_NO")
	    private BigDecimal systemSeqNo;
	    @Size(max = 6)
	    @Column(name = "CREDITOR_BANK")
	    private String creditorBank;
	    @Size(max = 6)
	    @Column(name = "DEBTOR_BANK")
	    private String debtorBank;
	    @Size(max = 5)
	    @Column(name = "SUB_SERVICE")
	    private String subService;
	    @Size(max = 3)
	    @Column(name = "TXN_TYPE")
	    private String txnType;
	    @Size(max = 1)
	    @Column(name = "TXN_STATUS")
	    private String txnStatus;
	    @Size(max = 25)
	    @Column(name = "CREATED_BY")
	    private String createdBy;
	    @Column(name = "CREATED_DATE")
	    @Temporal(TemporalType.TIMESTAMP)
	    private Date createdDate;
	    @Size(max = 1)
	    @Column(name = "BILL_EXP_STATUS")
	    private String billExpStatus;
	    @Column(name = "ACTION_DATE")
	    @Temporal(TemporalType.DATE)
	    private Date actionDate;
	    @Size(max = 4)
	    @Column(name = "AUTH_CODE")
	    private String authCode;
	    @Size(max = 35)
	    @Column(name = "TXN_ID")
	    private String txnId;
	    @Size(max = 35)
	    @Column(name = "MNDT_REF_NUM")
	    private String mndtRefNum;
	    @Size(max = 35)
	    @Column(name = "EXT_MSG_ID")
	    private String extMsgId;
	    @Column(name = "RESP_DATE")
	    @Temporal(TemporalType.DATE)
	    private Date respDate;
	
		public MdtAcOpsDailyBillingEntity() {
	    }
	
	    public MdtAcOpsDailyBillingEntity(BigDecimal systemSeqNo) {
	        this.systemSeqNo = systemSeqNo;
	    }
	
	    public BigDecimal getSystemSeqNo() {
	        return systemSeqNo;
	    }
	
	    public void setSystemSeqNo(BigDecimal systemSeqNo) {
	        this.systemSeqNo = systemSeqNo;
	    }
	
	    public String getCreditorBank() {
	        return creditorBank;
	    }
	
	    public void setCreditorBank(String creditorBank) {
	        this.creditorBank = creditorBank;
	    }
	
	    public String getDebtorBank() {
	        return debtorBank;
	    }
	
	    public void setDebtorBank(String debtorBank) {
	        this.debtorBank = debtorBank;
	    }
	
	    public String getSubService() {
	        return subService;
	    }
	
	    public void setSubService(String subService) {
	        this.subService = subService;
	    }
	
	    public String getTxnType() {
	        return txnType;
	    }
	
	    public void setTxnType(String txnType) {
	        this.txnType = txnType;
	    }
	
	    public String getTxnStatus() {
	        return txnStatus;
	    }
	
	    public void setTxnStatus(String txnStatus) {
	        this.txnStatus = txnStatus;
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
	
		public String getBillExpStatus() {
			return billExpStatus;
		}
	
		public void setBillExpStatus(String billExpStatus) {
			this.billExpStatus = billExpStatus;
		}
	
		public Date getActionDate() {
			return actionDate;
		}
	
		public void setActionDate(Date actionDate) {
			this.actionDate = actionDate;
		}
		
		public String getAuthCode() {
			return authCode;
		}
	
		public void setAuthCode(String authCode) {
			this.authCode = authCode;
		}
	
		public String getTxnId() {
			return txnId;
		}
	
		public void setTxnId(String txnId) {
			this.txnId = txnId;
		}

		public String getMndtRefNum() {
			return mndtRefNum;
		}

		public void setMndtRefNum(String mndtRefNum) {
			this.mndtRefNum = mndtRefNum;
		}

		public String getExtMsgId() {
			return extMsgId;
		}

		public void setExtMsgId(String extMsgId) {
			this.extMsgId = extMsgId;
		}

		public Date getRespDate() {
			return respDate;
		}

		public void setRespDate(Date respDate) {
			this.respDate = respDate;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((actionDate == null) ? 0 : actionDate.hashCode());
			result = prime * result + ((authCode == null) ? 0 : authCode.hashCode());
			result = prime * result + ((billExpStatus == null) ? 0 : billExpStatus.hashCode());
			result = prime * result + ((createdBy == null) ? 0 : createdBy.hashCode());
			result = prime * result + ((createdDate == null) ? 0 : createdDate.hashCode());
			result = prime * result + ((creditorBank == null) ? 0 : creditorBank.hashCode());
			result = prime * result + ((debtorBank == null) ? 0 : debtorBank.hashCode());
			result = prime * result + ((extMsgId == null) ? 0 : extMsgId.hashCode());
			result = prime * result + ((mndtRefNum == null) ? 0 : mndtRefNum.hashCode());
			result = prime * result + ((respDate == null) ? 0 : respDate.hashCode());
			result = prime * result + ((subService == null) ? 0 : subService.hashCode());
			result = prime * result + ((systemSeqNo == null) ? 0 : systemSeqNo.hashCode());
			result = prime * result + ((txnId == null) ? 0 : txnId.hashCode());
			result = prime * result + ((txnStatus == null) ? 0 : txnStatus.hashCode());
			result = prime * result + ((txnType == null) ? 0 : txnType.hashCode());
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
			MdtAcOpsDailyBillingEntity other = (MdtAcOpsDailyBillingEntity) obj;
			if (actionDate == null) {
				if (other.actionDate != null)
					return false;
			} else if (!actionDate.equals(other.actionDate))
				return false;
			if (authCode == null) {
				if (other.authCode != null)
					return false;
			} else if (!authCode.equals(other.authCode))
				return false;
			if (billExpStatus == null) {
				if (other.billExpStatus != null)
					return false;
			} else if (!billExpStatus.equals(other.billExpStatus))
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
			if (creditorBank == null) {
				if (other.creditorBank != null)
					return false;
			} else if (!creditorBank.equals(other.creditorBank))
				return false;
			if (debtorBank == null) {
				if (other.debtorBank != null)
					return false;
			} else if (!debtorBank.equals(other.debtorBank))
				return false;
			if (extMsgId == null) {
				if (other.extMsgId != null)
					return false;
			} else if (!extMsgId.equals(other.extMsgId))
				return false;
			if (mndtRefNum == null) {
				if (other.mndtRefNum != null)
					return false;
			} else if (!mndtRefNum.equals(other.mndtRefNum))
				return false;
			if (respDate == null) {
				if (other.respDate != null)
					return false;
			} else if (!respDate.equals(other.respDate))
				return false;
			if (subService == null) {
				if (other.subService != null)
					return false;
			} else if (!subService.equals(other.subService))
				return false;
			if (systemSeqNo == null) {
				if (other.systemSeqNo != null)
					return false;
			} else if (!systemSeqNo.equals(other.systemSeqNo))
				return false;
			if (txnId == null) {
				if (other.txnId != null)
					return false;
			} else if (!txnId.equals(other.txnId))
				return false;
			if (txnStatus == null) {
				if (other.txnStatus != null)
					return false;
			} else if (!txnStatus.equals(other.txnStatus))
				return false;
			if (txnType == null) {
				if (other.txnType != null)
					return false;
			} else if (!txnType.equals(other.txnType))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "MdtAcOpsDailyBillingEntity [systemSeqNo=" + systemSeqNo + ", creditorBank=" + creditorBank
					+ ", debtorBank=" + debtorBank + ", subService=" + subService + ", txnType=" + txnType
					+ ", txnStatus=" + txnStatus + ", createdBy=" + createdBy + ", createdDate=" + createdDate
					+ ", billExpStatus=" + billExpStatus + ", actionDate=" + actionDate + ", authCode=" + authCode
					+ ", txnId=" + txnId + ", mndtRefNum=" + mndtRefNum + ", extMsgId=" + extMsgId + ", respDate="
					+ respDate + "]";
		}

	
	}