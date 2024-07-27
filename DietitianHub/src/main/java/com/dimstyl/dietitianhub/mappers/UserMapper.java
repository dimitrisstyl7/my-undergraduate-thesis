package com.dimstyl.dietitianhub.mappers;

import com.dimstyl.dietitianhub.dtos.ClientDto;
import com.dimstyl.dietitianhub.entities.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class UserMapper {

    public static ClientDto mapToClientDto(User user) {
        return ClientDto.builder()
                .id(user.getId())
                .phone(user.getUserInfo().getPhone())
                .email(user.getUserInfo().getEmail())
                .dateOfBirth(user.getUserInfo().getDateOfBirth())
                .gender(user.getUserInfo().getGender())
                .firstName(user.getUserInfo().getFirstName())
                .lastName(user.getUserInfo().getLastName())
                .build();
    }
    
}
