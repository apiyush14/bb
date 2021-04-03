package com.fitlers.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fitlers.entities.RunSummary;

@Repository
public interface RunSummaryRepository extends JpaRepository<RunSummary, String> {

}
