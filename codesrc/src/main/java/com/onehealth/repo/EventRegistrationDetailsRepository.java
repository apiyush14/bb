package com.onehealth.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.onehealth.entities.EventRegistrationDetails;
import com.onehealth.entities.EventRegistrationDetailsId;

@Repository
public interface EventRegistrationDetailsRepository extends JpaRepository<EventRegistrationDetails, EventRegistrationDetailsId> {

}
