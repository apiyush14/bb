package com.fitlers.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fitlers.entities.UserDetails;

public interface UserDetailsRepository extends JpaRepository<UserDetails, String> {

}
