package com.dimstyl.dietitianhub.repositories;

import com.dimstyl.dietitianhub.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);

    List<User> findAllByRole_NameAndEnabledIsTrue(String role_name);

    boolean existsByUsername(String username);
}