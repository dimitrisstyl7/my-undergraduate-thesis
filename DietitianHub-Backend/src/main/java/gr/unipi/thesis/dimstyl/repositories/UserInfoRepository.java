package gr.unipi.thesis.dimstyl.repositories;

import gr.unipi.thesis.dimstyl.entities.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {

    Optional<UserInfo> findByUser_Id(int userId);

    Optional<UserInfo> findByUser_Username(String username);

}
