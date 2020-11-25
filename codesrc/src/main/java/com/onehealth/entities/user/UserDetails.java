package com.onehealth.entities.user;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.onehealth.entities.RunDetails;
import com.onehealth.entities.RunSummary;
import com.onehealth.entities.auth.UserAuthenticationDetails;
import com.onehealth.entities.event.registration.EventRegistrationDetails;

@Entity
@Table(name="USER_DETAILS")
public class UserDetails {

	@Id
	@Column(name="USER_ID")
	private String userId;	
	
	@Column(name="USER_FIRST_NAME")
	private String userFirstName;
	
	@Column(name="USER_LAST_NAME")
	private String userLastName;
	
	@OneToMany(mappedBy="userDetails")
	private Set<RunDetails> runDetails;
	
	@OneToOne(mappedBy="userDetails")
	private RunSummary runSummary;
	
	@OneToMany(mappedBy="userDetails")
	private Set<EventRegistrationDetails> eventRegistrationDetails;
	
	@OneToOne(mappedBy = "userDetails")
	private UserAuthenticationDetails userAuthenticationDetails;

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
	
}
