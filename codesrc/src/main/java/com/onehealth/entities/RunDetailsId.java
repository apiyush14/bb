package com.onehealth.entities;

import java.io.Serializable;

public class RunDetailsId implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long runId;
	private String userId;

	public RunDetailsId() {

	}

	public RunDetailsId(Long runId, String userId) {
		super();
		this.runId = runId;
		this.userId = userId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (userId + "_" + runId).hashCode();
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
		} else if (!(userId + "_" + runId).equals((other.userId + "_" + other.runId)))
			return false;
		return true;
	}
}
