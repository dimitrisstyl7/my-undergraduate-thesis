package com.dimstyl.dietitianhub.services;

import com.dimstyl.dietitianhub.dtos.ClientDto;
import com.dimstyl.dietitianhub.entities.User;

public interface UserInfoService {
    void save(ClientDto clientDto, User user);

    void update(ClientDto clientDto, User user);
}
