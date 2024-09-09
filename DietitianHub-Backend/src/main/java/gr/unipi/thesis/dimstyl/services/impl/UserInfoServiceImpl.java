package gr.unipi.thesis.dimstyl.services.impl;

import gr.unipi.thesis.dimstyl.dtos.ClientDto;
import gr.unipi.thesis.dimstyl.dtos.TagDto;
import gr.unipi.thesis.dimstyl.entities.Tag;
import gr.unipi.thesis.dimstyl.entities.User;
import gr.unipi.thesis.dimstyl.entities.UserInfo;
import gr.unipi.thesis.dimstyl.enums.RequestType;
import gr.unipi.thesis.dimstyl.exceptions.user.ApiUserInfoNotFoundException;
import gr.unipi.thesis.dimstyl.exceptions.user.MvcUserInfoNotFoundException;
import gr.unipi.thesis.dimstyl.repositories.UserInfoRepository;
import gr.unipi.thesis.dimstyl.services.TagService;
import gr.unipi.thesis.dimstyl.services.UserInfoService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService {

    private final UserInfoRepository userInfoRepository;
    private final TagService tagService;
    private final HttpServletRequest request;

    @Override
    public void saveUserInfo(ClientDto clientDto, User user) {
        UserInfo userInfo = clientDto.toUserInfo(user);
        userInfoRepository.save(userInfo);
    }

    @Override
    @Transactional
    public void updateUserInfo(ClientDto clientDto, int userId) {
        UserInfo existingUserInfo = getUserInfo(userId);
        existingUserInfo.setFirstName(clientDto.getFirstName());
        existingUserInfo.setLastName(clientDto.getLastName());
        existingUserInfo.setGender(clientDto.getGender());
        existingUserInfo.setDateOfBirth(clientDto.getDateOfBirth());
        existingUserInfo.setEmail(clientDto.getEmail());
        existingUserInfo.setPhone(clientDto.getPhone());
    }

    @Override
    public List<TagDto> getClientTags(int userId) {
        UserInfo userInfo = getUserInfo(userId);
        return userInfo.getTags().stream()
                .map(Tag::toDto)
                .toList();
    }

    @Override
    @Transactional
    public void updateClientTags(int userId, List<Integer> tagIds) {
        UserInfo userInfo = getUserInfo(userId);
        List<Tag> tags = tagService.getTags(tagIds);
        userInfo.setTags(tags);
    }

    @Override
    public UserInfo getUserInfo(int userId) {
        RequestType requestType = RequestType.byUri(request.getRequestURI());

        return userInfoRepository
                .findByUser_Id(userId)
                .orElseThrow(() -> {
                    String message = "User info not found for user id: %s".formatted(userId);
                    if (requestType.equals(RequestType.API)) {
                        return new ApiUserInfoNotFoundException(message);
                    } else {
                        return new MvcUserInfoNotFoundException(message);
                    }
                });
    }

}
