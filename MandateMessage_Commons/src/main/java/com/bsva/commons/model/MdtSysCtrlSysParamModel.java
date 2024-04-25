package com.bsva.commons.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

public class MdtSysCtrlSysParamModel implements Serializable {
                
    private Short sysParamId;
    private String sysName;
    private String defCurr;
    private Short inactiveDuration;
    private String sysType;
    private BigInteger archivePeriod;
    private String activeInd;
    private String createdBy;
    private Date createdDate;
    private String modifiedBy;
    private Date modifiedDate;
    private String sodRunInd;
    private String eodRunInd;
    
    
                public Short getSysParamId() {
                                return sysParamId;
                }
                public void setSysParamId(Short sysParamId) {
                                this.sysParamId = sysParamId;
                }
                public String getSysName() {
                                return sysName;
                }
                public void setSysName(String sysName) {
                                this.sysName = sysName;
                }
                public String getDefCurr() {
                                return defCurr;
                }
                public void setDefCurr(String defCurr) {
                                this.defCurr = defCurr;
                }
                public Short getInactiveDuration() {
                                return inactiveDuration;
                }
                public void setInactiveDuration(Short inactiveDuration) {
                                this.inactiveDuration = inactiveDuration;
                }
                public String getSysType() {
                                return sysType;
                }
                public void setSysType(String sysType) {
                                this.sysType = sysType;
                }
                public BigInteger getArchivePeriod() {
                                return archivePeriod;
                }
                public void setArchivePeriod(BigInteger archivePeriod) {
                                this.archivePeriod = archivePeriod;
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
                public String getModifiedBy() {
                                return modifiedBy;
                }
                public void setModifiedBy(String modifiedBy) {
                                this.modifiedBy = modifiedBy;
                }
                public Date getModifiedDate() {
                                return modifiedDate;
                }
                public void setModifiedDate(Date modifiedDate) {
                                this.modifiedDate = modifiedDate;
                }
                public String getSodRunInd() {
                                return sodRunInd;
                }
                public void setSodRunInd(String sodRunInd) {
                                this.sodRunInd = sodRunInd;
                }
                public String getEodRunInd() {
                                return eodRunInd;
                }
                public void setEodRunInd(String eodRunInd) {
                                this.eodRunInd = eodRunInd;
                }
                @Override
                public int hashCode() {
                                final int prime = 31;
                                int result = 1;
                                result = prime * result
                                                                + ((activeInd == null) ? 0 : activeInd.hashCode());
                                result = prime * result
                                                                + ((archivePeriod == null) ? 0 : archivePeriod.hashCode());
                                result = prime * result
                                                                + ((createdBy == null) ? 0 : createdBy.hashCode());
                                result = prime * result
                                                                + ((createdDate == null) ? 0 : createdDate.hashCode());
                                result = prime * result + ((defCurr == null) ? 0 : defCurr.hashCode());
                                result = prime * result
                                                                + ((eodRunInd == null) ? 0 : eodRunInd.hashCode());
                                result = prime
                                                                * result
                                                                + ((inactiveDuration == null) ? 0 : inactiveDuration.hashCode());
                                result = prime * result
                                                                + ((modifiedBy == null) ? 0 : modifiedBy.hashCode());
                                result = prime * result
                                                                + ((modifiedDate == null) ? 0 : modifiedDate.hashCode());
                                result = prime * result
                                                                + ((sodRunInd == null) ? 0 : sodRunInd.hashCode());
                                result = prime * result + ((sysName == null) ? 0 : sysName.hashCode());
                                result = prime * result
                                                                + ((sysParamId == null) ? 0 : sysParamId.hashCode());
                                result = prime * result + ((sysType == null) ? 0 : sysType.hashCode());
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
                                MdtSysCtrlSysParamModel other = (MdtSysCtrlSysParamModel) obj;
                                if (activeInd == null) {
                                                if (other.activeInd != null)
                                                                return false;
                                } else if (!activeInd.equals(other.activeInd))
                                                return false;
                                if (archivePeriod == null) {
                                                if (other.archivePeriod != null)
                                                                return false;
                                } else if (!archivePeriod.equals(other.archivePeriod))
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
                                if (defCurr == null) {
                                                if (other.defCurr != null)
                                                                return false;
                                } else if (!defCurr.equals(other.defCurr))
                                                return false;
                                if (eodRunInd == null) {
                                                if (other.eodRunInd != null)
                                                                return false;
                                } else if (!eodRunInd.equals(other.eodRunInd))
                                                return false;
                                if (inactiveDuration == null) {
                                                if (other.inactiveDuration != null)
                                                                return false;
                                } else if (!inactiveDuration.equals(other.inactiveDuration))
                                                return false;
                                if (modifiedBy == null) {
                                                if (other.modifiedBy != null)
                                                                return false;
                                } else if (!modifiedBy.equals(other.modifiedBy))
                                                return false;
                                if (modifiedDate == null) {
                                                if (other.modifiedDate != null)
                                                                return false;
                                } else if (!modifiedDate.equals(other.modifiedDate))
                                                return false;
                                if (sodRunInd == null) {
                                                if (other.sodRunInd != null)
                                                                return false;
                                } else if (!sodRunInd.equals(other.sodRunInd))
                                                return false;
                                if (sysName == null) {
                                                if (other.sysName != null)
                                                                return false;
                                } else if (!sysName.equals(other.sysName))
                                                return false;
                                if (sysParamId == null) {
                                                if (other.sysParamId != null)
                                                                return false;
                                } else if (!sysParamId.equals(other.sysParamId))
                                                return false;
                                if (sysType == null) {
                                                if (other.sysType != null)
                                                                return false;
                                } else if (!sysType.equals(other.sysType))
                                                return false;
                                return true;
                }
                @Override
                public String toString() {
                                return "MdtSysCtrlSysParamModel [sysParamId=" + sysParamId
                                                                + ", sysName=" + sysName + ", defCurr=" + defCurr
                                                                + ", inactiveDuration=" + inactiveDuration + ", sysType="
                                                                + sysType + ", archivePeriod=" + archivePeriod + ", activeInd="
                                                                + activeInd + ", createdBy=" + createdBy + ", createdDate="
                                                                + createdDate + ", modifiedBy=" + modifiedBy
                                                                + ", modifiedDate=" + modifiedDate + ", sodRunInd=" + sodRunInd
                                                                + ", eodRunInd=" + eodRunInd + "]";
                }
    
    

}
