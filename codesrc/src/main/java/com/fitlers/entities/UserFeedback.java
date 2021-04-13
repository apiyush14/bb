package com.fitlers.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "USER_FEEDBACK", indexes = @Index(columnList = "USER_ID"))
public class UserFeedback {

	@Id
	@Column(name = "FEEDBACK_ID")
	@GeneratedValue
	private Long feedbackId;

	@Column(name = "USER_ID")
	private String userId;

	@Column(name = "USER_FEEDBACK_RATING")
	private String userFeedbackRating;

	@Column(name = "USER_FEEDBACK_COMMENTS")
	private String userFeedbackComments;

	@CreationTimestamp
	@Column(name = "CREATED_DATE")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date createdDate;

	@UpdateTimestamp
	@Column(name = "UPDATED_DATE")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date updatedDate;

	public Long getFeedbackId() {
		return feedbackId;
	}

	public void setFeedbackId(Long feedbackId) {
		this.feedbackId = feedbackId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserFeedbackRating() {
		return userFeedbackRating;
	}

	public void setUserFeedbackRating(String userFeedbackRating) {
		this.userFeedbackRating = userFeedbackRating;
	}

	public String getUserFeedbackComments() {
		return userFeedbackComments;
	}

	public void setUserFeedbackComments(String userFeedbackComments) {
		this.userFeedbackComments = userFeedbackComments;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
}
