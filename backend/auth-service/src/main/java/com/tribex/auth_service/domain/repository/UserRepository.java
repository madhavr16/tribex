package com.tribex.auth_service.domain.repository;


import com.tribex.auth_service.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/*
    JpaRepository gives:
    - save()
    - findAll()
    - delete()
    - findById()
    etc.
 */
public interface UserRepository
        extends JpaRepository<User, Long> {

    /*
        Find user by email
     */
    Optional<User> findByEmail(String email);

    /*
        Check if email already exists
     */
    boolean existsByEmail(String email);
}
