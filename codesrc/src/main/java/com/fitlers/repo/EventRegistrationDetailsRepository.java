package com.fitlers.repo;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fitlers.entities.EventRegistrationDetails;
import com.fitlers.entities.EventRegistrationDetailsId;

@Repository
public interface EventRegistrationDetailsRepository
		extends JpaRepository<EventRegistrationDetails, EventRegistrationDetailsId> {
	@Query("SELECT a from EventRegistrationDetails a,EventDetails b WHERE b.isEventApproved='Y' and b.eventId=a.eventId and b.eventEndDate>=CURRENT_TIMESTAMP")
	public List<EventRegistrationDetails> findAllEligibleEvents();

	@Query("SELECT a from EventRegistrationDetails a,EventDetails b WHERE b.isEventApproved='Y' and b.eventId=a.eventId and b.eventEndDate>=CURRENT_TIMESTAMP")
	public List<EventRegistrationDetails> findAllEligibleEvents(Example<EventRegistrationDetails> example, Sort sort);

	@Query("SELECT a from EventRegistrationDetails a,EventDetails b WHERE b.isEventApproved='Y' and b.eventId=a.eventId and b.eventEndDate>=CURRENT_TIMESTAMP")
	public Page<EventRegistrationDetails> findAllEligibleEvents(Example<EventRegistrationDetails> example,
			Pageable pageable);
}
