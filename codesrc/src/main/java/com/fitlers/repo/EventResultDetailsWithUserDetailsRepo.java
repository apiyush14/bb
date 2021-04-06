package com.fitlers.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fitlers.entities.EventResultDetailsId;
import com.fitlers.entities.EventResultDetailsWithUserDetails;

@Repository
public interface EventResultDetailsWithUserDetailsRepo
		extends JpaRepository<EventResultDetailsWithUserDetails, EventResultDetailsId> {

}
