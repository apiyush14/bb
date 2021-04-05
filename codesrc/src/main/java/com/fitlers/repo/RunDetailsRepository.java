package com.fitlers.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fitlers.entities.RunDetails;
import com.fitlers.entities.RunDetailsId;

@Repository
public interface RunDetailsRepository extends JpaRepository<RunDetails, RunDetailsId> {
	
	@Query("SELECT r from RunDetails r WHERE r.eventId>0")
	public List<RunDetails> findAllEventRuns();
	
	@Query("SELECT r from RunDetails r WHERE r.eventId>0")
	public List<RunDetails> findAllEventRuns(Sort sort);

	@Query("SELECT r from RunDetails r WHERE r.eventId>0")
	public Page<RunDetails> findAllEventRuns(Pageable pageable);
}
