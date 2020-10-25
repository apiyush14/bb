package com.onehealth.entities;

import java.io.Serializable;
import java.util.Date;

public class RunDetailsId implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Date runStartDateTime;
	private String userId;

	public RunDetailsId() {

	}

	public RunDetailsId(Date runStartDateTime, String userId) {
		super();
		this.runStartDateTime = runStartDateTime;
		this.userId = userId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (userId + "_" + runStartDateTime).hashCode();
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
		RunDetailsId other = (RunDetailsId) obj;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!(userId + "_" + runStartDateTime).equals((other.userId + "_" + runStartDateTime)))
			return false;
		return true;
	}
}
