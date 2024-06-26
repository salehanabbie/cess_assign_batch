package com.bsva.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "CAS_CONFIG_DATA_TIME", schema = "CAMOWNER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CasConfigDataTimeEntity.findAll", query = "SELECT s FROM " +
			"CasConfigDataTimeEntity s")})
public class CasConfigDataTimeEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 22)
  @Column(name = "ID")
  private String id;
  @Column(name = "PAST_TIME_MILL")
  private long pastTimeInMill;

  public CasConfigDataTimeEntity() {
  }

  public CasConfigDataTimeEntity(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public long getPastTimeInMill() {
    return pastTimeInMill;
  }

  public void setPastTimeInMill(long pastTimeInMill) {
    this.pastTimeInMill = pastTimeInMill;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + (int) (pastTimeInMill ^ (pastTimeInMill >>> 32));
    return result;
  }

  @Override
  public boolean equals(Object obj) {
	  if (this == obj) {
		  return true;
	  }
	  if (obj == null) {
		  return false;
	  }
	  if (getClass() != obj.getClass()) {
		  return false;
	  }
    CasConfigDataTimeEntity other = (CasConfigDataTimeEntity) obj;
    if (id == null) {
		if (other.id != null) {
			return false;
		}
    } else if (!id.equals(other.id)) {
		return false;
	}
	  if (pastTimeInMill != other.pastTimeInMill) {
		  return false;
	  }
    return true;
  }

  @Override
  public String toString() {
    return "CasConfigDataTimeEntity [id=" + id + ", pastTimeInMill=" + pastTimeInMill + "]";
  }

}
