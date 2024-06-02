package com.dimstyl.dietitianhub.services;

import com.dimstyl.dietitianhub.dtos.ClientDto;
import com.dimstyl.dietitianhub.dtos.TagDto;
import com.dimstyl.dietitianhub.entities.User;

import java.util.List;

public interface UserInfoService {

    void saveUserInfo(ClientDto clientDto, User user);

    void updateUserInfo(ClientDto clientDto, User user);

    List<TagDto> getClientTags(Integer id);

    void updateClientTags(Integer id, List<Integer> tagIds);

}
