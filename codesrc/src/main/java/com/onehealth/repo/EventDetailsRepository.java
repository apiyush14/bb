package com.onehealth.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.onehealth.entities.event.EventDetails;

@Repository
public interface EventDetailsRepository extends JpaRepository<EventDetails, Long>{

}