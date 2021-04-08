package com.fitlers.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fitlers.constants.EncryptionKeys;
import com.fitlers.core.annotations.Encrypted;

@Entity
@Table(name = "USER_DETAILS")
public class UserDetails {

	@Id
	@Column(name = "USER_ID")
	private String userId;

	@Column(name = "USER_FIRST_NAME")
	@Encrypted(encryptionKey = EncryptionKeys.ENCRYPTION_KEY_NAME)
	private String userFirstName;

	@Column(name = "USER_LAST_NAME")
	@Encrypted(encryptionKey = EncryptionKeys.ENCRYPTION_KEY_NAME)
	private String userLastName;

	@Column(name = "USER_HEIGHT")
	private Double userHeight;

	@Column(name = "USER_WEIGHT")
	private Double userWeight;

	@JsonIgnore
	@OneToMany(mappedBy = "userDetails")
	private Set<RunDetails> runDetails;

	@JsonIgnore
	@OneToOne(mappedBy = "userDetails")
	private RunSummary runSummary;

	@JsonIgnore
	@OneToMany(mappedBy = "userDetails")
	private Set<EventRegistrationDetails> eventRegistrationDetails;

	@JsonIgnore
	@OneToOne(mappedBy = "userDetails")
	private UserAuthenticationDetails userAuthenticationDetails;

	@JsonIgnore
	@OneToMany(mappedBy = "userDetails")
	private Set<EventResultDetails> eventResultDetails;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserFirstName() {
		return userFirstName;
	}

	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}

	public String getUserLastName() {
		return userLastName;
	}

	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}

	public Double getUserHeight() {
		return userHeight;
	}

	public void setUserHeight(Double userHeight) {
		this.userHeight = userHeight;
	}

	public Double getUserWeight() {
		return userWeight;
	}

	public void setUserWeight(Double userWeight) {
		this.userWeight = userWeight;
	}

	public Set<RunDetails> getRunDetails() {
		return runDetails;
	}

	public void setRunDetails(Set<RunDetails> runDetails) {
		this.runDetails = runDetails;
	}

	public RunSummary getRunSummary() {
		return runSummary;
	}

	public void setRunSummary(RunSummary runSummary) {
		this.runSummary = runSummary;
	}

	public Set<EventRegistrationDetails> getEventRegistrationDetails() {
		return eventRegistrationDetails;
	}

	public void setEventRegistrationDetails(Set<EventRegistrationDetails> eventRegistrationDetails) {
		this.eventRegistrationDetails = eventRegistrationDetails;
	}

	public UserAuthenticationDetails getUserAuthenticationDetails() {
		return userAuthenticationDetails;
	}

	public void setUserAuthenticationDetails(UserAuthenticationDetails userAuthenticationDetails) {
		this.userAuthenticationDetails = userAuthenticationDetails;
	}

	public Set<EventResultDetails> getEventResultDetails() {
		return eventResultDetails;
	}

	public void setEventResultDetails(Set<EventResultDetails> eventResultDetails) {
		this.eventResultDetails = eventResultDetails;
	}

}
