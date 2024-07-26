package com.dimstyl.dietitianhub.services;

import com.dimstyl.dietitianhub.dtos.ClientDto;
import com.dimstyl.dietitianhub.dtos.TagDto;
import com.dimstyl.dietitianhub.entities.User;

import java.util.List;

public interface UserInfoService {

    void saveUserInfo(ClientDto clientDto, User user);

    void updateUserInfo(ClientDto clientDto, int userId);

    List<TagDto> getClientTags(int id);

    void updateClientTags(int id, List<Integer> tagIds);

}
