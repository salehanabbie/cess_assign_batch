package com.bsva.commons.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AuditTrackingModel implements Serializable {

	/**
	 * @author ElelwaniR
	 */
	private static final long serialVersionUID = 1L;

    private BigDecimal recordId;
    private Date actionDate;
    private String tableName;
    private String columnName;
    private String record;
    private String oldValue;
    private String newValue;
    private String action;
    private String userId;
    
	public AuditTrackingModel() {
		super();
	}

	
	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public BigDecimal getRecordId() {
		return recordId;
	}

	public void setRecordId(BigDecimal recordId) {
		this.recordId = recordId;
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
		final int prime = 31;
		int result = 1;
		result = prime * result + ((action == null) ? 0 : action.hashCode());
		result = prime * result
				+ ((actionDate == null) ? 0 : actionDate.hashCode());
		result = prime * result
				+ ((columnName == null) ? 0 : columnName.hashCode());
		result = prime * result
				+ ((newValue == null) ? 0 : newValue.hashCode());
		result = prime * result
				+ ((oldValue == null) ? 0 : oldValue.hashCode());
		result = prime * result + ((record == null) ? 0 : record.hashCode());
		result = prime * result
				+ ((recordId == null) ? 0 : recordId.hashCode());
		result = prime * result
				+ ((tableName == null) ? 0 : tableName.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
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
		AuditTrackingModel other = (AuditTrackingModel) obj;
		if (action == null) {
			if (other.action != null)
				return false;
		} else if (!action.equals(other.action))
			return false;
		if (actionDate == null) {
			if (other.actionDate != null)
				return false;
		} else if (!actionDate.equals(other.actionDate))
			return false;
		if (columnName == null) {
			if (other.columnName != null)
				return false;
		} else if (!columnName.equals(other.columnName))
			return false;
		if (newValue == null) {
			if (other.newValue != null)
				return false;
		} else if (!newValue.equals(other.newValue))
			return false;
		if (oldValue == null) {
			if (other.oldValue != null)
				return false;
		} else if (!oldValue.equals(other.oldValue))
			return false;
		if (record == null) {
			if (other.record != null)
				return false;
		} else if (!record.equals(other.record))
			return false;
		if (recordId == null) {
			if (other.recordId != null)
				return false;
		} else if (!recordId.equals(other.recordId))
			return false;
		if (tableName == null) {
			if (other.tableName != null)
				return false;
		} else if (!tableName.equals(other.tableName))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}

	/*@Override
	public String toString() {
		return "AuditTrackingModel [recordId=" + recordId + ", actionDate="
				+ actionDate + ", tableName=" + tableName + ", columnName="
				+ columnName + ", record=" + record + ", oldValue=" + oldValue
				+ ", newValue=" + newValue + ", action=" + action + ", userId="
				+ userId + "]";
	}*/
    
	@Override
	public String toString() {
		return "AuditTrackingModel [recordId=" + recordId + ", actionDate="
				+ actionDate + ", tableName=" + tableName + ", columnName="
				+ columnName + ", record=" + record + ", newValue=" + newValue
				+ ", oldValue=" + oldValue	+ ", action=" + action +", userId="
				+ userId + "]";
	}
    
	
	
	
	
	
	
}
