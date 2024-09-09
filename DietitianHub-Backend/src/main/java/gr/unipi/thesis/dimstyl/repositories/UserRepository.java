package gr.unipi.thesis.dimstyl.repositories;

import gr.unipi.thesis.dimstyl.entities.User;
import gr.unipi.thesis.dimstyl.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

    List<User> findAllByRoleAndEnabledIsTrueOrderByUserInfo_FirstName(UserRole role);

    boolean existsByUsername(String username);

}