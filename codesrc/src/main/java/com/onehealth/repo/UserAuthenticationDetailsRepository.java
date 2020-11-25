package com.onehealth.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onehealth.entities.auth.UserAuthenticationDetails;

public interface UserAuthenticationDetailsRepository extends JpaRepository<UserAuthenticationDetails, String> {

}
