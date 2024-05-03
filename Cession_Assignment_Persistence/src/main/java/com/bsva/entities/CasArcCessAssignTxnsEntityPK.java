package com.bsva.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author SalehaR
 */
@Embeddable
public class CasArcCessAssignTxnsEntityPK implements Serializable {
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 35)
	@Column(name = "MSG_ID")
	private String msgId;
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 35)
	@Column(name = "MANDATE_REQ_TRAN_ID")
	private String mandateReqTranId;

	public CasArcCessAssignTxnsEntityPK() {
	}

	public CasArcCessAssignTxnsEntityPK(String msgId, String mandateReqTranId) {
		this.msgId = msgId;
		this.mandateReqTranId = mandateReqTranId;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getMandateReqTranId() {
		return mandateReqTranId;
	}

	public void setMandateReqTranId(String mandateReqTranId) {
		this.mandateReqTranId = mandateReqTranId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mandateReqTranId == null) ? 0 : mandateReqTranId.hashCode());
		result = prime * result + ((msgId == null) ? 0 : msgId.hashCode());
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
		CasArcCessAssignTxnsEntityPK other = (CasArcCessAssignTxnsEntityPK) obj;
		if (mandateReqTranId == null) {
			if (other.mandateReqTranId != null)
				return false;
		} else if (!mandateReqTranId.equals(other.mandateReqTranId))
			return false;
		if (msgId == null) {
			if (other.msgId != null)
				return false;
		} else if (!msgId.equals(other.msgId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MdtAcArcMandateTxnsEntityPK [msgId=" + msgId + ", mandateReqTranId=" + mandateReqTranId + "]";
	}

}
