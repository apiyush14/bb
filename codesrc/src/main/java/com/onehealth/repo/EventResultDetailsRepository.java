package com.onehealth.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.onehealth.entities.EventResultDetails;
import com.onehealth.entities.EventResultDetailsId;

@Repository
public interface EventResultDetailsRepository extends JpaRepository<EventResultDetails, EventResultDetailsId> {

}
