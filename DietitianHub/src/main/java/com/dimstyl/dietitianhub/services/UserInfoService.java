package com.dimstyl.dietitianhub.services;

import com.dimstyl.dietitianhub.dtos.ClientDto;
import com.dimstyl.dietitianhub.entities.User;

public interface UserInfoService {
    void saveUserInfo(ClientDto clientDto, User user);

    void updateUserInfo(ClientDto clientDto, User user);
}
