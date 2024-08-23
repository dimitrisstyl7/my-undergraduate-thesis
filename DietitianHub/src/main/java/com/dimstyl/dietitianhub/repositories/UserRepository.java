package com.dimstyl.dietitianhub.repositories;

import com.dimstyl.dietitianhub.entities.User;
import com.dimstyl.dietitianhub.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

    List<User> findAllByRoleAndEnabledIsTrueOrderByUserInfo_FirstName(UserRole role);

    boolean existsByUsername(String username);

}