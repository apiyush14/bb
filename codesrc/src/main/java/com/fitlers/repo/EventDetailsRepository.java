package com.fitlers.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fitlers.entities.EventDetails;

@Repository
public interface EventDetailsRepository extends JpaRepository<EventDetails, Long> {

	@Query("SELECT e from EventDetails e WHERE e.isEventApproved='Y' and e.eventEndDate>=CURRENT_TIMESTAMP")
	public List<EventDetails> findAllEligibleEvents();

	@Query("SELECT e from EventDetails e WHERE e.isEventApproved='Y' and e.eventEndDate>=CURRENT_TIMESTAMP")
	public List<EventDetails> findAllEligibleEvents(Sort sort);

	@Query("SELECT e from EventDetails e WHERE e.isEventApproved='Y' and e.eventEndDate>=CURRENT_TIMESTAMP")
	public Page<EventDetails> findAllEligibleEvents(Pageable pageable);
}
