package com.fitlers.repo;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fitlers.entities.EventResultDetails;
import com.fitlers.entities.EventResultDetailsId;

@Repository
public interface EventResultDetailsRepository extends JpaRepository<EventResultDetails, EventResultDetailsId> {

//	@Query("SELECT e from EventResultDetails ")
//	Page<EventResultDetails> findByEventId(Long eventId, Pageable pageable);

	@Query("SELECT e from EventResultDetails e")
	public List<EventResultDetails> findByExample(Example<EventResultDetails> example,
			Sort sort);
}
