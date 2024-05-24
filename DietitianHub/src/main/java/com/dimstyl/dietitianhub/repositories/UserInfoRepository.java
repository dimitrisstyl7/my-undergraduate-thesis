package com.dimstyl.dietitianhub.repositories;

import com.dimstyl.dietitianhub.entities.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
}