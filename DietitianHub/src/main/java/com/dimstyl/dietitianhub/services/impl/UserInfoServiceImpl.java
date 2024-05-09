package com.dimstyl.dietitianhub.services.impl;

import com.dimstyl.dietitianhub.entities.UserInfo;
import com.dimstyl.dietitianhub.repositories.UserInfoRepository;
import com.dimstyl.dietitianhub.services.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService {
    private final UserInfoRepository userInfoRepository;

    @Override
    public void saveUserInfo(UserInfo userInfo) {
        userInfoRepository.save(userInfo);
    }
}
