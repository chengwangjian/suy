package com.flywind.app.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * ModemacromainId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class ModemacromainId implements java.io.Serializable {

	// Fields

	private Long id;
	private Long subId=0L;

	// Constructors

	/** default constructor */
	public ModemacromainId() {
	}

	/** full constructor */
	public ModemacromainId(Long id, Long subId) {
		this.id = id;
		this.subId = subId;
	}

	// Property accessors
	
	@Column(name = "ID", nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "subID", nullable = false)
	public Long getSubId() {
		return this.subId;
	}

	public void setSubId(Long subId) {
		this.subId = subId;
	}

	@Override
	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof ModemacromainId))
			return false;
		ModemacromainId castOther = (ModemacromainId) other;

		return ((this.getId() == castOther.getId()) || 
				(this.getId() != null && castOther.getId() != null && 
				this.getId().equals(castOther.getId())));
				/*&& ((this.getSubId() == castOther.getSubId()) || 
					(this.getSubId() != null && castOther.getSubId() != null && 
					this.getSubId().equals(castOther.getSubId())));*/
	}

	@Override
	public int hashCode() {
		int result = 17;

		result = 37 * result + (getId() == null ? 0 : this.getId().hashCode());
//		result = 37 * result + (getSubId() == null ? 0 : this.getSubId().hashCode());
		return result;
	}

}