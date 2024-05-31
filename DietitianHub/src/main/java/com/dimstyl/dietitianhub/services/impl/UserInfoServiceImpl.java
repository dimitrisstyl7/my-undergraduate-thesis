package com.dimstyl.dietitianhub.services.impl;

import com.dimstyl.dietitianhub.dtos.ClientDto;
import com.dimstyl.dietitianhub.entities.User;
import com.dimstyl.dietitianhub.entities.UserInfo;
import com.dimstyl.dietitianhub.mappers.ClientDtoMapper;
import com.dimstyl.dietitianhub.repositories.UserInfoRepository;
import com.dimstyl.dietitianhub.services.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService {
    private final UserInfoRepository userInfoRepository;

    @Override
    public void saveUserInfo(ClientDto clientDto, User user) {
        UserInfo userInfo = ClientDtoMapper.mapToUserInfo(clientDto, user);
        userInfoRepository.save(userInfo);
    }

    @Override
    public void updateUserInfo(ClientDto clientDto, User user) {
        UserInfo existingUserInfo = userInfoRepository.findByUser(user);

        if (existingUserInfo == null) {
            // Todo: Throw a custom exception if user info not found and handle it in the custom exception handler.
        }

        existingUserInfo.setFirstName(clientDto.getFirstName());
        existingUserInfo.setLastName(clientDto.getLastName());
        existingUserInfo.setGender(clientDto.getGender());
        existingUserInfo.setDateOfBirth(clientDto.getDateOfBirth());
        existingUserInfo.setEmail(clientDto.getEmail());
        existingUserInfo.setPhone(clientDto.getPhone());
        userInfoRepository.save(existingUserInfo);
    }
}
