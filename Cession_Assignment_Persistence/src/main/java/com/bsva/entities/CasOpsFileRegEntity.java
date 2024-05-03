package com.bsva.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
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
 * @author ElelwaniR
 */
@Entity
@Table(name = "CAS_OPS_FILE_REG", schema = "CAMOWNER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CasOpsFileRegEntity.findAll", query = "SELECT m FROM CasOpsFileRegEntity " + "m"),
    @NamedQuery(name = "CasOpsFileRegEntity.findByFileName", query = "SELECT m FROM CasOpsFileRegEntity m WHERE m.fileName = :fileName"),
    @NamedQuery(name = "CasOpsFileRegEntity.findByFileNameLike1", query = "SELECT m FROM CasOpsFileRegEntity m WHERE m.fileName LIKE :fileName"),
    @NamedQuery(name = "CasOpsFileRegEntity.findByFileNameLike2", query = "SELECT m FROM CasOpsFileRegEntity m WHERE m.fileName LIKE :fileName AND m.fileName LIKE :fileName2"),
    @NamedQuery(name = "CasOpsFileRegEntity.findByFileNameLike3", query = "SELECT m FROM CasOpsFileRegEntity m WHERE m.fileName LIKE :fileName AND m.inOutInd = :inOutInd"),
    @NamedQuery(name = "CasOpsFileRegEntity.findByFilepath", query = "SELECT m FROM CasOpsFileRegEntity m WHERE m.filepath = :filepath"),
    @NamedQuery(name = "CasOpsFileRegEntity.findByStatus", query = "SELECT m FROM CasOpsFileRegEntity m WHERE m.status = :status"),
    @NamedQuery(name = "CasOpsFileRegEntity.findByReason", query = "SELECT m FROM CasOpsFileRegEntity m WHERE m.reason = :reason"),
    @NamedQuery(name = "CasOpsFileRegEntity.findByProcessDate", query = "SELECT m FROM CasOpsFileRegEntity m WHERE m.processDate = :processDate"),
    @NamedQuery(name = "CasOpsFileRegEntity.findByNameSpace", query = "SELECT m FROM CasOpsFileRegEntity m WHERE m.nameSpace = :nameSpace"),
    @NamedQuery(name = "CasOpsFileRegEntity.findByGrpHdrMsgId", query = "SELECT m FROM CasOpsFileRegEntity m WHERE m.grpHdrMsgId = :grpHdrMsgId"),
    @NamedQuery(name = "CasOpsFileRegEntity.findByOnlineInd", query = "SELECT m FROM CasOpsFileRegEntity m WHERE m.onlineInd = :onlineInd"),
    @NamedQuery(name = "CasOpsFileRegEntity.findByInOutInd", query = "SELECT m FROM CasOpsFileRegEntity m WHERE m.inOutInd = :inOutInd"),
    @NamedQuery(name = "CasOpsFileRegEntity.findByInOutIndASC", query = "SELECT m FROM CasOpsFileRegEntity m WHERE m.inOutInd = :inOutInd ORDER BY m.fileName ASC"),
    @NamedQuery(name = "CasOpsFileRegEntity.findByExtractMsgId", query = "SELECT m FROM CasOpsFileRegEntity m WHERE m.extractMsgId = :extractMsgId"),
    @NamedQuery(name = "CasOpsFileRegEntity.findByProcessDateTrunc", query = "SELECT m FROM CasOpsFileRegEntity m WHERE TRUNC(m.processDate) = " +
        "TO_DATE(:processDate,'YYYY-MM-DD') and m.status IN (:status1, :status2)"),
    @NamedQuery(name = "CasOpsFileRegEntity.findByProcessDateTruncService", query = "SELECT m FROM CasOpsFileRegEntity m WHERE " +
        "TRUNC(m.processDate) = TO_DATE(:processDate," +
        "'YYYY-MM-DD') and (m.status = :status1 OR m.status = :status2 OR m.status = :status3) " +
        "and (m.fileName LIKE CONCAT('%',:serviceName,'%'))")
})

public class CasOpsFileRegEntity implements Serializable {
  private static final long serialVersionUID = 1L;
  @Id
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "FILE_NAME")
  private String fileName;
  @Size(max = 100)
  @Column(name = "FILEPATH")
  private String filepath;
  @Size(max = 50)
  @Column(name = "STATUS")
  private String status;
  @Size(max = 100)
  @Column(name = "REASON")
  private String reason;
  @Column(name = "PROCESS_DATE")
  @Temporal(TemporalType.TIMESTAMP)
  private Date processDate;
  @Size(max = 100)
  @Column(name = "NAME_SPACE")
  private String nameSpace;
  @Size(max = 35)
  @Column(name = "GRP_HDR_MSG_ID")
  private String grpHdrMsgId;
  @Size(max = 1)
  @Column(name = "ONLINE_IND")
  private String onlineInd;
  @Size(max = 1)
  @Column(name = "IN_OUT_IND")
  private String inOutInd;
  @Size(max = 35)
  @Column(name = "EXTRACT_MSG_ID")
  private String extractMsgId;
  @Size(max = 5)
  @Column(name = "SERVICE_ID")
  private String serviceId;

  public CasOpsFileRegEntity() {
  }

  public CasOpsFileRegEntity(String fileName) {
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

  public String getServiceId() {
    return serviceId;
  }

  public void setServiceId(String serviceId) {
    this.serviceId = serviceId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CasOpsFileRegEntity that = (CasOpsFileRegEntity) o;
    return Objects.equals(fileName, that.fileName) &&
        Objects.equals(filepath, that.filepath) &&
        Objects.equals(status, that.status) &&
        Objects.equals(reason, that.reason) &&
        Objects.equals(processDate, that.processDate) &&
        Objects.equals(nameSpace, that.nameSpace) &&
        Objects.equals(grpHdrMsgId, that.grpHdrMsgId) &&
        Objects.equals(onlineInd, that.onlineInd) &&
        Objects.equals(inOutInd, that.inOutInd) &&
        Objects.equals(extractMsgId, that.extractMsgId) &&
        Objects.equals(serviceId, that.serviceId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(fileName, filepath, status, reason, processDate, nameSpace, grpHdrMsgId,
        onlineInd, inOutInd, extractMsgId, serviceId);
  }

  @Override
  public String toString() {
    return "CasOpsFileRegEntity{" +
        "fileName='" + fileName + '\'' +
        ", filepath='" + filepath + '\'' +
        ", status='" + status + '\'' +
        ", reason='" + reason + '\'' +
        ", processDate=" + processDate +
        ", nameSpace='" + nameSpace + '\'' +
        ", grpHdrMsgId='" + grpHdrMsgId + '\'' +
        ", onlineInd='" + onlineInd + '\'' +
        ", inOutInd='" + inOutInd + '\'' +
        ", extractMsgId='" + extractMsgId + '\'' +
        ", serviceId='" + serviceId + '\'' +
        '}';
  }
}
