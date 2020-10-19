package com.onehealth.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.onehealth.entities.RunDetails;
import com.onehealth.entities.RunDetailsId;

@Repository
public interface RunDetailsRepository extends JpaRepository<RunDetails, RunDetailsId>{
 
}
