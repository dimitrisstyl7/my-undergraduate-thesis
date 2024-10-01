package gr.unipi.thesis.dimstyl.services.impl;

import gr.unipi.thesis.dimstyl.dtos.web.WebClientDto;
import gr.unipi.thesis.dimstyl.dtos.web.WebTagDto;
import gr.unipi.thesis.dimstyl.entities.Tag;
import gr.unipi.thesis.dimstyl.entities.User;
import gr.unipi.thesis.dimstyl.entities.UserInfo;
import gr.unipi.thesis.dimstyl.enums.RequestType;
import gr.unipi.thesis.dimstyl.exceptions.user.ApiUserInfoNotFoundException;
import gr.unipi.thesis.dimstyl.exceptions.user.WebUserInfoNotFoundException;
import gr.unipi.thesis.dimstyl.repositories.UserInfoRepository;
import gr.unipi.thesis.dimstyl.services.TagService;
import gr.unipi.thesis.dimstyl.services.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService {

    private final UserInfoRepository userInfoRepository;
    private final TagService tagService;

    @Override
    public void saveUserInfo(WebClientDto webClientDto, User user) {
        UserInfo userInfo = webClientDto.toUserInfo(user);
        userInfoRepository.save(userInfo);
    }

    @Override
    @Transactional
    public void updateUserInfo(WebClientDto webClientDto, int userId, RequestType requestType) {
        UserInfo existingUserInfo = getUserInfo(userId, requestType);
        existingUserInfo.setFirstName(webClientDto.getFirstName());
        existingUserInfo.setLastName(webClientDto.getLastName());
        existingUserInfo.setGender(webClientDto.getGender());
        existingUserInfo.setDateOfBirth(webClientDto.getDateOfBirth());
        existingUserInfo.setEmail(webClientDto.getEmail());
        existingUserInfo.setPhone(webClientDto.getPhone());
    }

    @Override
    public List<WebTagDto> getClientTags(int userId, RequestType requestType) {
        UserInfo userInfo = getUserInfo(userId, requestType);
        return userInfo.getTags().stream()
                .map(Tag::toWebDto)
                .toList();
    }

    @Override
    public List<Tag> getClientTags(String username) {
        return getUserInfo(username).getTags();
    }

    @Override
    @Transactional
    public void updateClientTags(int userId, List<Integer> tagIds, RequestType requestType) {
        UserInfo userInfo = getUserInfo(userId, requestType);
        List<Tag> tags = tagService.getTags(tagIds);
        userInfo.setTags(tags);
    }

    @Override
    public UserInfo getUserInfo(int userId, RequestType requestType) {
        return userInfoRepository
                .findByUser_Id(userId)
                .orElseThrow(() -> {
                    String message = "User info not found for user id: %s".formatted(userId);
                    if (requestType.equals(RequestType.WEB_API)) {
                        return new ApiUserInfoNotFoundException(message);
                    } else {
                        return new WebUserInfoNotFoundException(message);
                    }
                });
    }

    @Override
    public UserInfo getUserInfo(String username) {
        return userInfoRepository
                .findByUser_Username(username)
                .orElseThrow(() -> {
                    String message = "User info not found for user with username: %s".formatted(username);
                    return new ApiUserInfoNotFoundException(message);
                });
    }

}
