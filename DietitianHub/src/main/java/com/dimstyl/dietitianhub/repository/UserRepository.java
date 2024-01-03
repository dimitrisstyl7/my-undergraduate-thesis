package com.dimstyl.dietitianhub.repository;

import com.dimstyl.dietitianhub.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}