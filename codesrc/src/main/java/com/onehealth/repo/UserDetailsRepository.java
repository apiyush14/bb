package com.onehealth.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onehealth.entities.UserDetails;

public interface UserDetailsRepository extends JpaRepository<UserDetails, String> {

}
