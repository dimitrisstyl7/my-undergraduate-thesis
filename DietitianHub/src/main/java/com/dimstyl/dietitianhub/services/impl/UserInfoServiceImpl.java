package com.dimstyl.dietitianhub.services.impl;

import com.dimstyl.dietitianhub.dtos.ClientDto;
import com.dimstyl.dietitianhub.dtos.TagDto;
import com.dimstyl.dietitianhub.entities.Tag;
import com.dimstyl.dietitianhub.entities.User;
import com.dimstyl.dietitianhub.entities.UserInfo;
import com.dimstyl.dietitianhub.enums.RequestType;
import com.dimstyl.dietitianhub.exceptions.api.ApiUserInfoNotFoundException;
import com.dimstyl.dietitianhub.exceptions.mvc.MvcUserInfoNotFoundException;
import com.dimstyl.dietitianhub.mappers.ClientDtoMapper;
import com.dimstyl.dietitianhub.mappers.TagMapper;
import com.dimstyl.dietitianhub.repositories.UserInfoRepository;
import com.dimstyl.dietitianhub.services.TagService;
import com.dimstyl.dietitianhub.services.UserInfoService;
import com.dimstyl.dietitianhub.utilities.RequestTypeUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService {

    private final UserInfoRepository userInfoRepository;
    private final TagService tagService;
    private final HttpServletRequest request;

    @Override
    public void saveUserInfo(ClientDto clientDto, User user) {
        UserInfo userInfo = ClientDtoMapper.mapToUserInfo(clientDto, user);
        userInfoRepository.save(userInfo);
    }

    @Override
    public void updateUserInfo(ClientDto clientDto, int userId) {
        UserInfo existingUserInfo = getUserInfoByUserId(userId);

        existingUserInfo.setFirstName(clientDto.getFirstName());
        existingUserInfo.setLastName(clientDto.getLastName());
        existingUserInfo.setGender(clientDto.getGender());
        existingUserInfo.setDateOfBirth(clientDto.getDateOfBirth());
        existingUserInfo.setEmail(clientDto.getEmail());
        existingUserInfo.setPhone(clientDto.getPhone());

        userInfoRepository.save(existingUserInfo);
    }

    @Override
    public List<TagDto> getClientTags(int userId) {
        UserInfo userInfo = getUserInfoByUserId(userId);
        return userInfo.getTags().stream()
                .map(TagMapper::mapToTagDto)
                .toList();
    }

    @Override
    public void updateClientTags(int userId, List<Integer> tagIds) {
        UserInfo userInfo = getUserInfoByUserId(userId);
        List<Tag> tags = tagService.getTagsByIds(tagIds);

        userInfo.setTags(tags);

        userInfoRepository.save(userInfo);
    }

    @Override
    public UserInfo getUserInfoByUserId(int userId) {
        RequestType requestType = RequestTypeUtil.getRequestType(request.getRequestURI());

        return userInfoRepository
                .findByUserId(userId)
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
