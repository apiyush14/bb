package com.fitlers.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fitlers.entities.EventResultDetails;
import com.fitlers.entities.EventResultDetailsId;

@Repository
public interface EventResultDetailsRepository extends JpaRepository<EventResultDetails, EventResultDetailsId> {

	Page<EventResultDetails> findByEventId(Long eventId, Pageable pageable);

}
