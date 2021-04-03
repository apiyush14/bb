package com.fitlers.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fitlers.entities.RunDetails;
import com.fitlers.entities.RunDetailsId;

@Repository
public interface RunDetailsRepository extends JpaRepository<RunDetails, RunDetailsId> {

}
