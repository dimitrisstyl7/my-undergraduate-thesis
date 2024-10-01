package gr.unipi.thesis.dimstyl.services;

import gr.unipi.thesis.dimstyl.dtos.web.WebClientDto;
import gr.unipi.thesis.dimstyl.dtos.web.WebTagDto;
import gr.unipi.thesis.dimstyl.entities.Tag;
import gr.unipi.thesis.dimstyl.entities.User;
import gr.unipi.thesis.dimstyl.entities.UserInfo;
import gr.unipi.thesis.dimstyl.enums.RequestType;

import java.util.List;

public interface UserInfoService {

    void saveUserInfo(WebClientDto webClientDto, User user);

    void updateUserInfo(WebClientDto webClientDto, int userId, RequestType requestType);

    List<WebTagDto> getClientTags(int id, RequestType requestType);

    List<Tag> getClientTags(String username);

    void updateClientTags(int id, List<Integer> tagIds, RequestType requestType);

    UserInfo getUserInfo(int userId, RequestType requestType);

    UserInfo getUserInfo(String username);
}
