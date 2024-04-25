package com.bsva.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * @author SalehaR
 * Date: 2021/06/18
 * Modified: 2021/10/29
 */
public class BatchFileStatsReportEntityModel implements Serializable{
	
	String memberNo, memName, inService, serviceType;
	BigDecimal nrOfFiles_1, nrOfFiles_2to10, nrOfFiles_11to50, nrOfFiles_51to100, nrOfFiles_101to500, nrOfFiles_501to1000, nrOfFiles_1001to5000, nrOfFiles_5001to10000, nrOfFiles_10001to20000, nrOfFiles_grtr_20000;
	
	public String getMemberNo() {
		return memberNo;
	}
	
	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}
	
	public String getMemName() {
		return memName;
	}
	
	public void setMemName(String memName) {
		this.memName = memName;
	}
	
	public String getInService() {
		return inService;
	}
	
	public void setInService(String inService) {
		this.inService = inService;
	}
	
	public String getServiceType() {
		return serviceType;
	}
	
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	
	public BigDecimal getNrOfFiles_1() {
		return nrOfFiles_1;
	}
	
	public void setNrOfFiles_1(BigDecimal nrOfFiles_1) {
		this.nrOfFiles_1 = nrOfFiles_1;
	}
	
	public BigDecimal getNrOfFiles_2to10() {
		return nrOfFiles_2to10;
	}
	
	public void setNrOfFiles_2to10(BigDecimal nrOfFiles_2to10) {
		this.nrOfFiles_2to10 = nrOfFiles_2to10;
	}
	
	public BigDecimal getNrOfFiles_11to50() {
		return nrOfFiles_11to50;
	}
	
	public void setNrOfFiles_11to50(BigDecimal nrOfFiles_11to50) {
		this.nrOfFiles_11to50 = nrOfFiles_11to50;
	}
	
	public BigDecimal getNrOfFiles_51to100() {
		return nrOfFiles_51to100;
	}
	
	public void setNrOfFiles_51to100(BigDecimal nrOfFiles_51to100) {
		this.nrOfFiles_51to100 = nrOfFiles_51to100;
	}
	
	public BigDecimal getNrOfFiles_101to500() {
		return nrOfFiles_101to500;
	}
	
	public void setNrOfFiles_101to500(BigDecimal nrOfFiles_101to500) {
		this.nrOfFiles_101to500 = nrOfFiles_101to500;
	}
	
	public BigDecimal getNrOfFiles_501to1000() {
		return nrOfFiles_501to1000;
	}
	
	public void setNrOfFiles_501to1000(BigDecimal nrOfFiles_501to1000) {
		this.nrOfFiles_501to1000 = nrOfFiles_501to1000;
	}
	
	public BigDecimal getNrOfFiles_1001to5000() {
		return nrOfFiles_1001to5000;
	}
	
	public void setNrOfFiles_1001to5000(BigDecimal nrOfFiles_1001to5000) {
		this.nrOfFiles_1001to5000 = nrOfFiles_1001to5000;
	}
	
	public BigDecimal getNrOfFiles_5001to10000() {
		return nrOfFiles_5001to10000;
	}
	
	public void setNrOfFiles_5001to10000(BigDecimal nrOfFiles_5001to10000) {
		this.nrOfFiles_5001to10000 = nrOfFiles_5001to10000;
	}
	
	public BigDecimal getNrOfFiles_10001to20000() {
		return nrOfFiles_10001to20000;
	}
	
	public void setNrOfFiles_10001to20000(BigDecimal nrOfFiles_10001to20000) {
		this.nrOfFiles_10001to20000 = nrOfFiles_10001to20000;
	}
	
	public BigDecimal getNrOfFiles_grtr_20000() {
		return nrOfFiles_grtr_20000;
	}
	
	public void setNrOfFiles_grtr_20000(BigDecimal nrOfFiles_grtr_20000) {
		this.nrOfFiles_grtr_20000 = nrOfFiles_grtr_20000;
	}

	@Override
	public int hashCode() {
		return Objects.hash(inService, memName, memberNo, nrOfFiles_1, nrOfFiles_10001to20000, nrOfFiles_1001to5000,
				nrOfFiles_101to500, nrOfFiles_11to50, nrOfFiles_2to10, nrOfFiles_5001to10000, nrOfFiles_501to1000,
				nrOfFiles_51to100, nrOfFiles_grtr_20000, serviceType);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BatchFileStatsReportEntityModel other = (BatchFileStatsReportEntityModel) obj;
		return Objects.equals(inService, other.inService) && Objects.equals(memName, other.memName)
				&& Objects.equals(memberNo, other.memberNo) && Objects.equals(nrOfFiles_1, other.nrOfFiles_1)
				&& Objects.equals(nrOfFiles_10001to20000, other.nrOfFiles_10001to20000)
				&& Objects.equals(nrOfFiles_1001to5000, other.nrOfFiles_1001to5000)
				&& Objects.equals(nrOfFiles_101to500, other.nrOfFiles_101to500)
				&& Objects.equals(nrOfFiles_11to50, other.nrOfFiles_11to50)
				&& Objects.equals(nrOfFiles_2to10, other.nrOfFiles_2to10)
				&& Objects.equals(nrOfFiles_5001to10000, other.nrOfFiles_5001to10000)
				&& Objects.equals(nrOfFiles_501to1000, other.nrOfFiles_501to1000)
				&& Objects.equals(nrOfFiles_51to100, other.nrOfFiles_51to100)
				&& Objects.equals(nrOfFiles_grtr_20000, other.nrOfFiles_grtr_20000)
				&& Objects.equals(serviceType, other.serviceType);
	}

	@Override
	public String toString() {
		return "BatchFileStatsReportEntityModel [memberNo=" + memberNo + ", memName=" + memName + ", inService="
				+ inService + ", serviceType=" + serviceType + ", nrOfFiles_1=" + nrOfFiles_1 + ", nrOfFiles_2to10="
				+ nrOfFiles_2to10 + ", nrOfFiles_11to50=" + nrOfFiles_11to50 + ", nrOfFiles_51to100="
				+ nrOfFiles_51to100 + ", nrOfFiles_101to500=" + nrOfFiles_101to500 + ", nrOfFiles_501to1000="
				+ nrOfFiles_501to1000 + ", nrOfFiles_1001to5000=" + nrOfFiles_1001to5000 + ", nrOfFiles_5001to10000="
				+ nrOfFiles_5001to10000 + ", nrOfFiles_10001to20000=" + nrOfFiles_10001to20000
				+ ", nrOfFiles_grtr_20000=" + nrOfFiles_grtr_20000 + "]";
	}
	
}
