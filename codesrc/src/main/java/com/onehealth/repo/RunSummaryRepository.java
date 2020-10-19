package com.onehealth.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.onehealth.entities.RunSummary;

@Repository
public interface RunSummaryRepository extends JpaRepository<RunSummary, String> {

}
