package com.dimstyl.dietitianhub.services.impl;

import com.dimstyl.dietitianhub.dtos.ClientDto;
import com.dimstyl.dietitianhub.dtos.TagDto;
import com.dimstyl.dietitianhub.entities.Tag;
import com.dimstyl.dietitianhub.entities.User;
import com.dimstyl.dietitianhub.entities.UserInfo;
import com.dimstyl.dietitianhub.exceptions.api.ApiUserInfoNotFoundException;
import com.dimstyl.dietitianhub.exceptions.mvc.MvcUserInfoNotFoundException;
import com.dimstyl.dietitianhub.mappers.ClientDtoMapper;
import com.dimstyl.dietitianhub.mappers.TagMapper;
import com.dimstyl.dietitianhub.repositories.UserInfoRepository;
import com.dimstyl.dietitianhub.services.TagService;
import com.dimstyl.dietitianhub.services.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService {

    private final UserInfoRepository userInfoRepository;
    private final TagService tagService;

    @Override
    public void saveUserInfo(ClientDto clientDto, User user) {
        UserInfo userInfo = ClientDtoMapper.mapToUserInfo(clientDto, user);
        userInfoRepository.save(userInfo);
    }

    @Override
    public void updateUserInfo(ClientDto clientDto, int userId) {
        UserInfo existingUserInfo = userInfoRepository
                .findByUserId(userId)
                .orElseThrow(() -> new MvcUserInfoNotFoundException("User info not found for user id: %d".formatted(userId)));

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
        UserInfo userInfo = userInfoRepository
                .findByUserId(userId)
                .orElseThrow(() -> new ApiUserInfoNotFoundException("User info not found for user id: " + userId));

        return userInfo.getTags().stream()
                .map(TagMapper::mapToTagDto)
                .toList();
    }

    @Override
    public void updateClientTags(int userId, List<Integer> tagIds) {
        UserInfo userInfo = userInfoRepository
                .findByUserId(userId)
                .orElseThrow(() -> new ApiUserInfoNotFoundException("User info not found for user id: " + userId));

        List<Tag> tags = tagService.getTagsByIds(tagIds);
        userInfo.setTags(tags);
        userInfoRepository.save(userInfo);
    }

}
