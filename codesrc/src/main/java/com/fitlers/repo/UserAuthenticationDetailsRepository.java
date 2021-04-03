package com.fitlers.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fitlers.entities.UserAuthenticationDetails;

public interface UserAuthenticationDetailsRepository extends JpaRepository<UserAuthenticationDetails, String> {

}
