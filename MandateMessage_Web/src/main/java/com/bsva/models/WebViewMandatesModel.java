package com.bsva.models;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Saleha Saib
 *
 */
public class WebViewMandatesModel implements Serializable
{
		/*Mandate Information*/
		String mandateId, localInstrumentCode, debitValueType, sequenceType, frequencyCode; 
		Date fromDate, toDate, firstCollectionDate, finalCollectionDate;
		String collAmtCurrency, maxAmountCurrency, collectionAmt, maximumAmt;
		String activeInd;
		
		/*Creditor Information*/
		String creditorSchemeName, contractNo, creditorId, creditorName, creditorContactDetail, creditorBranchNo, creditorBank, creditorAccNo, ultimateCreditorName, ultimateCreditorContactDetail;
		
		/*Debtor Information*/
		String debtorName, debtorIdNo, debtorAccName, debtorAccNo, debtorBranchNo, debtorBank, debtorContactDetail,ultimateDebtorName, ultimateDebtorContactDetail;

		public String getMandateId() {
			return mandateId;
		}

		public void setMandateId(String mandateId) {
			this.mandateId = mandateId;
		}

		public String getLocalInstrumentCode() {
			return localInstrumentCode;
		}

		public void setLocalInstrumentCode(String localInstrumentCode) {
			this.localInstrumentCode = localInstrumentCode;
		}

		public String getDebitValueType() {
			return debitValueType;
		}

		public void setDebitValueType(String debitValueType) {
			this.debitValueType = debitValueType;
		}

		public String getSequenceType() {
			return sequenceType;
		}

		public void setSequenceType(String sequenceType) {
			this.sequenceType = sequenceType;
		}

		public String getFrequencyCode() {
			return frequencyCode;
		}

		public void setFrequencyCode(String frequencyCode) {
			this.frequencyCode = frequencyCode;
		}

		public Date getFromDate() {
			return fromDate;
		}

		public void setFromDate(Date fromDate) {
			this.fromDate = fromDate;
		}

		public Date getToDate() {
			return toDate;
		}

		public void setToDate(Date toDate) {
			this.toDate = toDate;
		}

		public Date getFirstCollectionDate() {
			return firstCollectionDate;
		}

		public void setFirstCollectionDate(Date firstCollectionDate) {
			this.firstCollectionDate = firstCollectionDate;
		}

		public Date getFinalCollectionDate() {
			return finalCollectionDate;
		}

		public void setFinalCollectionDate(Date finalCollectionDate) {
			this.finalCollectionDate = finalCollectionDate;
		}

		public String getCollAmtCurrency() {
			return collAmtCurrency;
		}

		public void setCollAmtCurrency(String collAmtCurrency) {
			this.collAmtCurrency = collAmtCurrency;
		}

		public String getMaxAmountCurrency() {
			return maxAmountCurrency;
		}

		public void setMaxAmountCurrency(String maxAmountCurrency) {
			this.maxAmountCurrency = maxAmountCurrency;
		}

		public String getCollectionAmt() {
			return collectionAmt;
		}

		public void setCollectionAmt(String collectionAmt) {
			this.collectionAmt = collectionAmt;
		}

		public String getMaximumAmt() {
			return maximumAmt;
		}

		public void setMaximumAmt(String maximumAmt) {
			this.maximumAmt = maximumAmt;
		}

		public String getActiveInd() {
			return activeInd;
		}

		public void setActiveInd(String activeInd) {
			this.activeInd = activeInd;
		}

		public String getCreditorSchemeName() {
			return creditorSchemeName;
		}

		public void setCreditorSchemeName(String creditorSchemeName) {
			this.creditorSchemeName = creditorSchemeName;
		}

		public String getContractNo() {
			return contractNo;
		}

		public void setContractNo(String contractNo) {
			this.contractNo = contractNo;
		}

		public String getCreditorId() {
			return creditorId;
		}

		public void setCreditorId(String creditorId) {
			this.creditorId = creditorId;
		}

		public String getCreditorName() {
			return creditorName;
		}

		public void setCreditorName(String creditorName) {
			this.creditorName = creditorName;
		}

		public String getCreditorContactDetail() {
			return creditorContactDetail;
		}

		public void setCreditorContactDetail(String creditorContactDetail) {
			this.creditorContactDetail = creditorContactDetail;
		}

		public String getCreditorBranchNo() {
			return creditorBranchNo;
		}

		public void setCreditorBranchNo(String creditorBranchNo) {
			this.creditorBranchNo = creditorBranchNo;
		}

		public String getCreditorBank() {
			return creditorBank;
		}

		public void setCreditorBank(String creditorBank) {
			this.creditorBank = creditorBank;
		}

		public String getCreditorAccNo() {
			return creditorAccNo;
		}

		public void setCreditorAccNo(String creditorAccNo) {
			this.creditorAccNo = creditorAccNo;
		}

		public String getUltimateCreditorName() {
			return ultimateCreditorName;
		}

		public void setUltimateCreditorName(String ultimateCreditorName) {
			this.ultimateCreditorName = ultimateCreditorName;
		}

		public String getUltimateCreditorContactDetail() {
			return ultimateCreditorContactDetail;
		}

		public void setUltimateCreditorContactDetail(
				String ultimateCreditorContactDetail) {
			this.ultimateCreditorContactDetail = ultimateCreditorContactDetail;
		}

		public String getDebtorName() {
			return debtorName;
		}

		public void setDebtorName(String debtorName) {
			this.debtorName = debtorName;
		}

		public String getDebtorIdNo() {
			return debtorIdNo;
		}

		public void setDebtorIdNo(String debtorIdNo) {
			this.debtorIdNo = debtorIdNo;
		}

		public String getDebtorAccName() {
			return debtorAccName;
		}

		public void setDebtorAccName(String debtorAccName) {
			this.debtorAccName = debtorAccName;
		}

		public String getDebtorAccNo() {
			return debtorAccNo;
		}

		public void setDebtorAccNo(String debtorAccNo) {
			this.debtorAccNo = debtorAccNo;
		}

		public String getDebtorBranchNo() {
			return debtorBranchNo;
		}

		public void setDebtorBranchNo(String debtorBranchNo) {
			this.debtorBranchNo = debtorBranchNo;
		}

		public String getDebtorBank() {
			return debtorBank;
		}

		public void setDebtorBank(String debtorBank) {
			this.debtorBank = debtorBank;
		}

		public String getDebtorContactDetail() {
			return debtorContactDetail;
		}

		public void setDebtorContactDetail(String debtorContactDetail) {
			this.debtorContactDetail = debtorContactDetail;
		}

		public String getUltimateDebtorName() {
			return ultimateDebtorName;
		}

		public void setUltimateDebtorName(String ultimateDebtorName) {
			this.ultimateDebtorName = ultimateDebtorName;
		}

		public String getUltimateDebtorContactDetail() {
			return ultimateDebtorContactDetail;
		}

		public void setUltimateDebtorContactDetail(String ultimateDebtorContactDetail) {
			this.ultimateDebtorContactDetail = ultimateDebtorContactDetail;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((activeInd == null) ? 0 : activeInd.hashCode());
			result = prime
					* result
					+ ((collAmtCurrency == null) ? 0 : collAmtCurrency
							.hashCode());
			result = prime * result
					+ ((collectionAmt == null) ? 0 : collectionAmt.hashCode());
			result = prime * result
					+ ((contractNo == null) ? 0 : contractNo.hashCode());
			result = prime * result
					+ ((creditorAccNo == null) ? 0 : creditorAccNo.hashCode());
			result = prime * result
					+ ((creditorBank == null) ? 0 : creditorBank.hashCode());
			result = prime
					* result
					+ ((creditorBranchNo == null) ? 0 : creditorBranchNo
							.hashCode());
			result = prime
					* result
					+ ((creditorContactDetail == null) ? 0
							: creditorContactDetail.hashCode());
			result = prime * result
					+ ((creditorId == null) ? 0 : creditorId.hashCode());
			result = prime * result
					+ ((creditorName == null) ? 0 : creditorName.hashCode());
			result = prime
					* result
					+ ((creditorSchemeName == null) ? 0 : creditorSchemeName
							.hashCode());
			result = prime
					* result
					+ ((debitValueType == null) ? 0 : debitValueType.hashCode());
			result = prime * result
					+ ((debtorAccName == null) ? 0 : debtorAccName.hashCode());
			result = prime * result
					+ ((debtorAccNo == null) ? 0 : debtorAccNo.hashCode());
			result = prime * result
					+ ((debtorBank == null) ? 0 : debtorBank.hashCode());
			result = prime
					* result
					+ ((debtorBranchNo == null) ? 0 : debtorBranchNo.hashCode());
			result = prime
					* result
					+ ((debtorContactDetail == null) ? 0 : debtorContactDetail
							.hashCode());
			result = prime * result
					+ ((debtorIdNo == null) ? 0 : debtorIdNo.hashCode());
			result = prime * result
					+ ((debtorName == null) ? 0 : debtorName.hashCode());
			result = prime
					* result
					+ ((finalCollectionDate == null) ? 0 : finalCollectionDate
							.hashCode());
			result = prime
					* result
					+ ((firstCollectionDate == null) ? 0 : firstCollectionDate
							.hashCode());
			result = prime * result
					+ ((frequencyCode == null) ? 0 : frequencyCode.hashCode());
			result = prime * result
					+ ((fromDate == null) ? 0 : fromDate.hashCode());
			result = prime
					* result
					+ ((localInstrumentCode == null) ? 0 : localInstrumentCode
							.hashCode());
			result = prime * result
					+ ((mandateId == null) ? 0 : mandateId.hashCode());
			result = prime
					* result
					+ ((maxAmountCurrency == null) ? 0 : maxAmountCurrency
							.hashCode());
			result = prime * result
					+ ((maximumAmt == null) ? 0 : maximumAmt.hashCode());
			result = prime * result
					+ ((sequenceType == null) ? 0 : sequenceType.hashCode());
			result = prime * result
					+ ((toDate == null) ? 0 : toDate.hashCode());
			result = prime
					* result
					+ ((ultimateCreditorContactDetail == null) ? 0
							: ultimateCreditorContactDetail.hashCode());
			result = prime
					* result
					+ ((ultimateCreditorName == null) ? 0
							: ultimateCreditorName.hashCode());
			result = prime
					* result
					+ ((ultimateDebtorContactDetail == null) ? 0
							: ultimateDebtorContactDetail.hashCode());
			result = prime
					* result
					+ ((ultimateDebtorName == null) ? 0 : ultimateDebtorName
							.hashCode());
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
			WebViewMandatesModel other = (WebViewMandatesModel) obj;
			if (activeInd == null) {
				if (other.activeInd != null)
					return false;
			} else if (!activeInd.equals(other.activeInd))
				return false;
			if (collAmtCurrency == null) {
				if (other.collAmtCurrency != null)
					return false;
			} else if (!collAmtCurrency.equals(other.collAmtCurrency))
				return false;
			if (collectionAmt == null) {
				if (other.collectionAmt != null)
					return false;
			} else if (!collectionAmt.equals(other.collectionAmt))
				return false;
			if (contractNo == null) {
				if (other.contractNo != null)
					return false;
			} else if (!contractNo.equals(other.contractNo))
				return false;
			if (creditorAccNo == null) {
				if (other.creditorAccNo != null)
					return false;
			} else if (!creditorAccNo.equals(other.creditorAccNo))
				return false;
			if (creditorBank == null) {
				if (other.creditorBank != null)
					return false;
			} else if (!creditorBank.equals(other.creditorBank))
				return false;
			if (creditorBranchNo == null) {
				if (other.creditorBranchNo != null)
					return false;
			} else if (!creditorBranchNo.equals(other.creditorBranchNo))
				return false;
			if (creditorContactDetail == null) {
				if (other.creditorContactDetail != null)
					return false;
			} else if (!creditorContactDetail
					.equals(other.creditorContactDetail))
				return false;
			if (creditorId == null) {
				if (other.creditorId != null)
					return false;
			} else if (!creditorId.equals(other.creditorId))
				return false;
			if (creditorName == null) {
				if (other.creditorName != null)
					return false;
			} else if (!creditorName.equals(other.creditorName))
				return false;
			if (creditorSchemeName == null) {
				if (other.creditorSchemeName != null)
					return false;
			} else if (!creditorSchemeName.equals(other.creditorSchemeName))
				return false;
			if (debitValueType == null) {
				if (other.debitValueType != null)
					return false;
			} else if (!debitValueType.equals(other.debitValueType))
				return false;
			if (debtorAccName == null) {
				if (other.debtorAccName != null)
					return false;
			} else if (!debtorAccName.equals(other.debtorAccName))
				return false;
			if (debtorAccNo == null) {
				if (other.debtorAccNo != null)
					return false;
			} else if (!debtorAccNo.equals(other.debtorAccNo))
				return false;
			if (debtorBank == null) {
				if (other.debtorBank != null)
					return false;
			} else if (!debtorBank.equals(other.debtorBank))
				return false;
			if (debtorBranchNo == null) {
				if (other.debtorBranchNo != null)
					return false;
			} else if (!debtorBranchNo.equals(other.debtorBranchNo))
				return false;
			if (debtorContactDetail == null) {
				if (other.debtorContactDetail != null)
					return false;
			} else if (!debtorContactDetail.equals(other.debtorContactDetail))
				return false;
			if (debtorIdNo == null) {
				if (other.debtorIdNo != null)
					return false;
			} else if (!debtorIdNo.equals(other.debtorIdNo))
				return false;
			if (debtorName == null) {
				if (other.debtorName != null)
					return false;
			} else if (!debtorName.equals(other.debtorName))
				return false;
			if (finalCollectionDate == null) {
				if (other.finalCollectionDate != null)
					return false;
			} else if (!finalCollectionDate.equals(other.finalCollectionDate))
				return false;
			if (firstCollectionDate == null) {
				if (other.firstCollectionDate != null)
					return false;
			} else if (!firstCollectionDate.equals(other.firstCollectionDate))
				return false;
			if (frequencyCode == null) {
				if (other.frequencyCode != null)
					return false;
			} else if (!frequencyCode.equals(other.frequencyCode))
				return false;
			if (fromDate == null) {
				if (other.fromDate != null)
					return false;
			} else if (!fromDate.equals(other.fromDate))
				return false;
			if (localInstrumentCode == null) {
				if (other.localInstrumentCode != null)
					return false;
			} else if (!localInstrumentCode.equals(other.localInstrumentCode))
				return false;
			if (mandateId == null) {
				if (other.mandateId != null)
					return false;
			} else if (!mandateId.equals(other.mandateId))
				return false;
			if (maxAmountCurrency == null) {
				if (other.maxAmountCurrency != null)
					return false;
			} else if (!maxAmountCurrency.equals(other.maxAmountCurrency))
				return false;
			if (maximumAmt == null) {
				if (other.maximumAmt != null)
					return false;
			} else if (!maximumAmt.equals(other.maximumAmt))
				return false;
			if (sequenceType == null) {
				if (other.sequenceType != null)
					return false;
			} else if (!sequenceType.equals(other.sequenceType))
				return false;
			if (toDate == null) {
				if (other.toDate != null)
					return false;
			} else if (!toDate.equals(other.toDate))
				return false;
			if (ultimateCreditorContactDetail == null) {
				if (other.ultimateCreditorContactDetail != null)
					return false;
			} else if (!ultimateCreditorContactDetail
					.equals(other.ultimateCreditorContactDetail))
				return false;
			if (ultimateCreditorName == null) {
				if (other.ultimateCreditorName != null)
					return false;
			} else if (!ultimateCreditorName.equals(other.ultimateCreditorName))
				return false;
			if (ultimateDebtorContactDetail == null) {
				if (other.ultimateDebtorContactDetail != null)
					return false;
			} else if (!ultimateDebtorContactDetail
					.equals(other.ultimateDebtorContactDetail))
				return false;
			if (ultimateDebtorName == null) {
				if (other.ultimateDebtorName != null)
					return false;
			} else if (!ultimateDebtorName.equals(other.ultimateDebtorName))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "WebViewMandatesModel "
					+ "[mandateId=" + mandateId + "\n"
					+ ", localInstrumentCode=" + localInstrumentCode  + "\n"
					+ ", debitValueType=" + debitValueType  + "\n" 
					+ ", sequenceType="+ sequenceType +  "\n"
					+", frequencyCode=" + frequencyCode  + "\n"
					+ ", fromDate=" + fromDate  + "\n" 
					+ ", toDate=" + toDate  + "\n"
					+ ", firstCollectionDate=" + firstCollectionDate  + "\n"
					+ ", finalCollectionDate=" + finalCollectionDate + "\n"
					+ ", collAmtCurrency=" + collAmtCurrency + "\n"
					+ ", maxAmountCurrency=" + maxAmountCurrency  + "\n"
					+ ", collectionAmt=" + collectionAmt  + "\n"
					+ ", maximumAmt="+ maximumAmt + "\n" 
					+ ", activeInd=" + activeInd
					+ ", creditorSchemeName=" + creditorSchemeName + "\n"
					+ ", contractNo=" + contractNo + "\n" 
					+ ", creditorId="+ creditorId + "\n" 
					+ ", creditorName=" + creditorName + "\n"
					+ ", creditorContactDetail=" + creditorContactDetail + "\n"
					+ ", creditorBranchNo=" + creditorBranchNo + "\n"
					+ ", creditorBank=" + creditorBank + "\n" 
					+ ", creditorAccNo="+ creditorAccNo  + "\n" 
					+ ", ultimateCreditorName="+ ultimateCreditorName  + "\n"
					+ ", ultimateCreditorContactDetail=" + ultimateCreditorContactDetail  + "\n"
					+ ", debtorName="+ debtorName  + "\n"
					+ ", debtorIdNo=" + debtorIdNo
					+ ", debtorAccName=" + debtorAccName + "\n"
					+ ", debtorAccNo="+ debtorAccNo + "\n"
					+ ", debtorBranchNo=" + debtorBranchNo + "\n"
					+ ", debtorBank=" + debtorBank  + "\n"
					+ ", debtorContactDetail="+ debtorContactDetail + "\n" 
					+ ", ultimateDebtorName="+ ultimateDebtorName  + "\n"
					+ ", ultimateDebtorContactDetail="+ ultimateDebtorContactDetail + "]";
		}
	
		
		
	
	
}
