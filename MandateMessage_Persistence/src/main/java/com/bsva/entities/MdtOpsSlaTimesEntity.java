package com.bsva.entities;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ElelwaniR
 */
@Entity
@Table(name = "MDT_OPS_SLA_TIMES",schema = "MANOWNER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MdtOpsSlaTimesEntity.findAll", query = "SELECT m FROM MdtOpsSlaTimesEntity m"),
    @NamedQuery(name = "MdtOpsSlaTimesEntity.findByService", query = "SELECT m FROM MdtOpsSlaTimesEntity m WHERE m.service = :service"),
    @NamedQuery(name = "MdtOpsSlaTimesEntity.findByManyServicesNotIn", query = "SELECT m FROM MdtOpsSlaTimesEntity m WHERE m.service NOT IN (:service1, :service2, :service3)"),
    @NamedQuery(name = "MdtOpsSlaTimesEntity.findByStartTime", query = "SELECT m FROM MdtOpsSlaTimesEntity m WHERE m.startTime = :startTime"),
    @NamedQuery(name = "MdtOpsSlaTimesEntity.findByEndTime", query = "SELECT m FROM MdtOpsSlaTimesEntity m WHERE m.endTime = :endTime"),
    @NamedQuery(name = "MdtOpsSlaTimesEntity.findByCreatedBy", query = "SELECT m FROM MdtOpsSlaTimesEntity m WHERE m.createdBy = :createdBy"),
    @NamedQuery(name = "MdtOpsSlaTimesEntity.findByCreatedDate", query = "SELECT m FROM MdtOpsSlaTimesEntity m WHERE m.createdDate = :createdDate"),
    @NamedQuery(name = "MdtOpsSlaTimesEntity.findByModifiedBy", query = "SELECT m FROM MdtOpsSlaTimesEntity m WHERE m.modifiedBy = :modifiedBy"),
    @NamedQuery(name = "MdtOpsSlaTimesEntity.findByModifiedDate", query = "SELECT m FROM MdtOpsSlaTimesEntity m WHERE m.modifiedDate = :modifiedDate")})

public class MdtOpsSlaTimesEntity implements Serializable 
{
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "SERVICE")
    private String service;
    @Size(max = 6)
    @Column(name = "START_TIME")
    private String startTime;
    @Size(max = 6)
    @Column(name = "END_TIME")
    private String endTime;
    @Column(name = "CREATED_BY")
    private String createdBy;
    @Column(name = "CREATED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Size(max = 25)
    @Column(name = "MODIFIED_BY")
    private String modifiedBy;
    @Column(name = "MODIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;

    public MdtOpsSlaTimesEntity()
    {
    	
    }

    public MdtOpsSlaTimesEntity(String service)
    {
        this.service = service;
    }

    public String getService() 
    {
        return service;
    }

    public void setService(String service)
    {
        this.service = service;
    }

    public String getStartTime() 
    {
        return startTime;
    }

    public void setStartTime(String startTime)
    {
        this.startTime = startTime;
    }

    public String getEndTime() 
    {
        return endTime;
    }

    public void setEndTime(String endTime) 
    {
        this.endTime = endTime;
    }

    public String getCreatedBy() 
    {
        return createdBy;
    }

    public void setCreatedBy(String createdBy)
    {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate()
    {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) 
    {
        this.createdDate = createdDate;
    }

    public String getModifiedBy() 
    {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) 
    {
        this.modifiedBy = modifiedBy;
    }

    public Date getModifiedDate() 
    {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate)
    {
        this.modifiedDate = modifiedDate;
    }

	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((createdBy == null) ? 0 : createdBy.hashCode());
		result = prime * result
				+ ((createdDate == null) ? 0 : createdDate.hashCode());
		result = prime * result + ((endTime == null) ? 0 : endTime.hashCode());
		result = prime * result
				+ ((modifiedBy == null) ? 0 : modifiedBy.hashCode());
		result = prime * result
				+ ((modifiedDate == null) ? 0 : modifiedDate.hashCode());
		result = prime * result + ((service == null) ? 0 : service.hashCode());
		result = prime * result
				+ ((startTime == null) ? 0 : startTime.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MdtOpsSlaTimesEntity other = (MdtOpsSlaTimesEntity) obj;
		if (createdBy == null) 
		{
			if (other.createdBy != null)
				return false;
		} 
		else if (!createdBy.equals(other.createdBy))
			return false;
		if (createdDate == null) 
		{
			if (other.createdDate != null)
				return false;
		} 
		else if (!createdDate.equals(other.createdDate))
			return false;
		if (endTime == null)
		{
			if (other.endTime != null)
				return false;
		}
		else if (!endTime.equals(other.endTime))
			return false;
		if (modifiedBy == null) 
		{
			if (other.modifiedBy != null)
				return false;
		} 
		else if (!modifiedBy.equals(other.modifiedBy))
			return false;
		if (modifiedDate == null)
		{
			if (other.modifiedDate != null)
				return false;
		} 
		else if (!modifiedDate.equals(other.modifiedDate))
			return false;
		if (service == null) 
		{
			if (other.service != null)
				return false;
		} 
		else if (!service.equals(other.service))
			return false;
		if (startTime == null) 
		{
			if (other.startTime != null)
				return false;
		} 
		else if (!startTime.equals(other.startTime))
			return false;
		return true;
	}
    
}
