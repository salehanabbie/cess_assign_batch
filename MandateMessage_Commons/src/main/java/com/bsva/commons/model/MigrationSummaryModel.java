package com.bsva.commons.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * @author DimakatsoN
 *
 */
public class MigrationSummaryModel  implements Serializable {
	
	private BigDecimal nrOfNaedoRequest;
	private BigDecimal nrofNaedoSuccessfulRequest;
	private BigDecimal nrNaedoOutstandingRequest;
	private BigDecimal nrNaedoExpiredRequest;
	private BigDecimal nrOfEadoRequest;
	private BigDecimal nrofEadoSuccessfulRequest;
	private BigDecimal nrEadoOutstandingRequest;
	private BigDecimal nrAedoExpiredRequest;
	private String memberNo;
	
	
	public MigrationSummaryModel() {
		super();
		// TODO Auto-generated constructor stub
	}


	public BigDecimal getNrOfNaedoRequest() {
		return nrOfNaedoRequest;
	}


	public void setNrOfNaedoRequest(BigDecimal nrOfNaedoRequest) {
		this.nrOfNaedoRequest = nrOfNaedoRequest;
	}


	public BigDecimal getNrofNaedoSuccessfulRequest() {
		return nrofNaedoSuccessfulRequest;
	}


	public void setNrofNaedoSuccessfulRequest(BigDecimal nrofNaedoSuccessfulRequest) {
		this.nrofNaedoSuccessfulRequest = nrofNaedoSuccessfulRequest;
	}


	public BigDecimal getNrNaedoOutstandingRequest() {
		return nrNaedoOutstandingRequest;
	}


	public void setNrNaedoOutstandingRequest(BigDecimal nrNaedoOutstandingRequest) {
		this.nrNaedoOutstandingRequest = nrNaedoOutstandingRequest;
	}


	public BigDecimal getNrOfEadoRequest() {
		return nrOfEadoRequest;
	}


	public void setNrOfEadoRequest(BigDecimal nrOfEadoRequest) {
		this.nrOfEadoRequest = nrOfEadoRequest;
	}


	public BigDecimal getNrofEadoSuccessfulRequest() {
		return nrofEadoSuccessfulRequest;
	}


	public void setNrofEadoSuccessfulRequest(BigDecimal nrofEadoSuccessfulRequest) {
		this.nrofEadoSuccessfulRequest = nrofEadoSuccessfulRequest;
	}


	public BigDecimal getNrEadoOutstandingRequest() {
		return nrEadoOutstandingRequest;
	}


	public void setNrEadoOutstandingRequest(BigDecimal nrEadoOutstandingRequest) {
		this.nrEadoOutstandingRequest = nrEadoOutstandingRequest;
	}

	public String getMemberNo() {
		return memberNo;
	}


	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}
	
	
	public BigDecimal getNrNaedoExpiredRequest()
	{
		return nrNaedoExpiredRequest;
	}

	public void setNrNaedoExpiredRequest(BigDecimal nrNaedoExpiredRequest)
	{
		this.nrNaedoExpiredRequest = nrNaedoExpiredRequest;
	}
	
	public BigDecimal getNrAedoExpiredRequest()
	{
		return nrAedoExpiredRequest;
	}

	public void setNrAedoExpiredRequest(BigDecimal nrAedoExpiredRequest)
	{
		this.nrAedoExpiredRequest = nrAedoExpiredRequest;
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((memberNo == null) ? 0 : memberNo.hashCode());
		result = prime * result + ((nrAedoExpiredRequest == null) ? 0 : nrAedoExpiredRequest.hashCode());
		result = prime * result + ((nrEadoOutstandingRequest == null) ? 0 : nrEadoOutstandingRequest.hashCode());
		result = prime * result + ((nrNaedoExpiredRequest == null) ? 0 : nrNaedoExpiredRequest.hashCode());
		result = prime * result + ((nrNaedoOutstandingRequest == null) ? 0 : nrNaedoOutstandingRequest.hashCode());
		result = prime * result + ((nrOfEadoRequest == null) ? 0 : nrOfEadoRequest.hashCode());
		result = prime * result + ((nrOfNaedoRequest == null) ? 0 : nrOfNaedoRequest.hashCode());
		result = prime * result + ((nrofEadoSuccessfulRequest == null) ? 0 : nrofEadoSuccessfulRequest.hashCode());
		result = prime * result + ((nrofNaedoSuccessfulRequest == null) ? 0 : nrofNaedoSuccessfulRequest.hashCode());
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
		MigrationSummaryModel other = (MigrationSummaryModel) obj;
		if (memberNo == null) {
			if (other.memberNo != null)
				return false;
		} else if (!memberNo.equals(other.memberNo))
			return false;
		if (nrAedoExpiredRequest == null) {
			if (other.nrAedoExpiredRequest != null)
				return false;
		} else if (!nrAedoExpiredRequest.equals(other.nrAedoExpiredRequest))
			return false;
		if (nrEadoOutstandingRequest == null) {
			if (other.nrEadoOutstandingRequest != null)
				return false;
		} else if (!nrEadoOutstandingRequest.equals(other.nrEadoOutstandingRequest))
			return false;
		if (nrNaedoExpiredRequest == null) {
			if (other.nrNaedoExpiredRequest != null)
				return false;
		} else if (!nrNaedoExpiredRequest.equals(other.nrNaedoExpiredRequest))
			return false;
		if (nrNaedoOutstandingRequest == null) {
			if (other.nrNaedoOutstandingRequest != null)
				return false;
		} else if (!nrNaedoOutstandingRequest.equals(other.nrNaedoOutstandingRequest))
			return false;
		if (nrOfEadoRequest == null) {
			if (other.nrOfEadoRequest != null)
				return false;
		} else if (!nrOfEadoRequest.equals(other.nrOfEadoRequest))
			return false;
		if (nrOfNaedoRequest == null) {
			if (other.nrOfNaedoRequest != null)
				return false;
		} else if (!nrOfNaedoRequest.equals(other.nrOfNaedoRequest))
			return false;
		if (nrofEadoSuccessfulRequest == null) {
			if (other.nrofEadoSuccessfulRequest != null)
				return false;
		} else if (!nrofEadoSuccessfulRequest.equals(other.nrofEadoSuccessfulRequest))
			return false;
		if (nrofNaedoSuccessfulRequest == null) {
			if (other.nrofNaedoSuccessfulRequest != null)
				return false;
		} else if (!nrofNaedoSuccessfulRequest.equals(other.nrofNaedoSuccessfulRequest))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "MigrationSummaryModel [nrOfNaedoRequest=" + nrOfNaedoRequest + ", nrofNaedoSuccessfulRequest="
				+ nrofNaedoSuccessfulRequest + ", nrNaedoOutstandingRequest=" + nrNaedoOutstandingRequest
				+ ", nrNaedoExpiredRequest=" + nrNaedoExpiredRequest + ", nrOfEadoRequest=" + nrOfEadoRequest
				+ ", nrofEadoSuccessfulRequest=" + nrofEadoSuccessfulRequest + ", nrEadoOutstandingRequest="
				+ nrEadoOutstandingRequest + ", nrAedoExpiredRequest=" + nrAedoExpiredRequest + ", memberNo=" + memberNo
				+ "]";
	}




	
	


	

	
	

	

	
	
	

}
