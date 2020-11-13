package com.onehealth.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.onehealth.entities.event.registration.EventRegistrationDetails;
import com.onehealth.entities.event.registration.EventRegistrationDetailsId;

@Repository
public interface EventRegistrationDetailsRepository extends JpaRepository<EventRegistrationDetails, EventRegistrationDetailsId> {

}
