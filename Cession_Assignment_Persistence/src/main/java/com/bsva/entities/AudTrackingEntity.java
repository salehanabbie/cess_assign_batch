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
 * @author ElelwaniR
 */
@Entity
@Table(name = "AUD_TRACKING",schema = "CAMOWNER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AudTrackingEntity.findAll", query = "SELECT a FROM AudTrackingEntity a"),
    @NamedQuery(name = "AudTrackingEntity.findByRecordId", query = "SELECT a FROM AudTrackingEntity a WHERE a.recordId = :recordId"),
    @NamedQuery(name = "AudTrackingEntity.findByUserId", query = "SELECT a FROM AudTrackingEntity a WHERE a.userId = :userId"),
    @NamedQuery(name = "AudTrackingEntity.findByActionDate", query = "SELECT a FROM AudTrackingEntity a WHERE a.actionDate = :actionDate"),
    @NamedQuery(name = "AudTrackingEntity.findByTableName", query = "SELECT a FROM AudTrackingEntity a WHERE a.tableName = :tableName"),
    @NamedQuery(name = "AudTrackingEntity.findByColumnName", query = "SELECT a FROM AudTrackingEntity a WHERE a.columnName = :columnName"),
    @NamedQuery(name = "AudTrackingEntity.findByRecord", query = "SELECT a FROM AudTrackingEntity a WHERE a.record = :record"),
    @NamedQuery(name = "AudTrackingEntity.findByOldValue", query = "SELECT a FROM AudTrackingEntity a WHERE a.oldValue = :oldValue"),
    @NamedQuery(name = "AudTrackingEntity.findByNewValue", query = "SELECT a FROM AudTrackingEntity a WHERE a.newValue = :newValue"),
    @NamedQuery(name = "AudTrackingEntity.findByAction", query = "SELECT a FROM AudTrackingEntity a WHERE a.action = :action")})
public class AudTrackingEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @GenericGenerator(name = "generator", strategy = "sequence-identity", parameters = @Parameter(name = "sequence", value = "MANOWNER.AUD_TRACKING_SEQ") )
    @GeneratedValue(generator = "generator")
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "RECORD_ID")
    private BigDecimal recordId;
    @Size(max = 22)
    @Column(name = "USER_ID")
    private String userId;
    @Column(name = "ACTION_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date actionDate;
    @Size(max = 35)
    @Column(name = "TABLE_NAME")
    private String tableName;
    @Size(max = 35)
    @Column(name = "COLUMN_NAME")
    private String columnName;
    @Size(max = 35)
    @Column(name = "RECORD")
    private String record;
    @Size(max = 50)
    @Column(name = "OLD_VALUE")
    private String oldValue;
    @Size(max = 50)
    @Column(name = "NEW_VALUE")
    private String newValue;
    @Size(max = 15)
    @Column(name = "ACTION")
    private String action;

    public AudTrackingEntity() {
    }

    public AudTrackingEntity(BigDecimal recordId) {
        this.recordId = recordId;
    }

    public BigDecimal getRecordId() {
        return recordId;
    }

    public void setRecordId(BigDecimal recordId) {
        this.recordId = recordId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getActionDate() {
        return actionDate;
    }

    public void setActionDate(Date actionDate) {
        this.actionDate = actionDate;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (recordId != null ? recordId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AudTrackingEntity)) {
            return false;
        }
        AudTrackingEntity other = (AudTrackingEntity) object;
        if ((this.recordId == null && other.recordId != null) || (this.recordId != null && !this.recordId.equals(other.recordId))) {
            return false;
        }
        return true;
    }

	@Override
	public String toString() {
		return "AudTrackingEntity [recordId=" + recordId + ", userId=" + userId
				+ ", actionDate=" + actionDate + ", tableName=" + tableName
				+ ", columnName=" + columnName + ", record=" + record
				+ ", oldValue=" + oldValue + ", newValue=" + newValue
				+ ", action=" + action + "]";
	}

  
    
}
