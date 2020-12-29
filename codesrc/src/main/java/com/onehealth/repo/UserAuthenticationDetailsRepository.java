package com.onehealth.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onehealth.entities.UserAuthenticationDetails;

public interface UserAuthenticationDetailsRepository extends JpaRepository<UserAuthenticationDetails, String> {

}
