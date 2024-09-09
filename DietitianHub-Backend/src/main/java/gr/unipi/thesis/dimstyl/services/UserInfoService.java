package gr.unipi.thesis.dimstyl.services;

import gr.unipi.thesis.dimstyl.dtos.ClientDto;
import gr.unipi.thesis.dimstyl.dtos.TagDto;
import gr.unipi.thesis.dimstyl.entities.User;
import gr.unipi.thesis.dimstyl.entities.UserInfo;

import java.util.List;

public interface UserInfoService {

    void saveUserInfo(ClientDto clientDto, User user);

    void updateUserInfo(ClientDto clientDto, int userId);

    List<TagDto> getClientTags(int id);

    void updateClientTags(int id, List<Integer> tagIds);

    UserInfo getUserInfo(int userId);

}
