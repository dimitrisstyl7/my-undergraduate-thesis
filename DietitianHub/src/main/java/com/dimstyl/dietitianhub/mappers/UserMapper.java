package com.dimstyl.dietitianhub.mappers;

import com.dimstyl.dietitianhub.dtos.ClientDto;
import com.dimstyl.dietitianhub.entities.User;

public class UserMapper {
    public static ClientDto mapToClientDto(User user) {
        return ClientDto.builder()
                .phone(user.getUserInfo().getPhone())
                .email(user.getUserInfo().getEmail())
                .dateOfBirth(user.getUserInfo().getDateOfBirth())
                .firstName(user.getUserInfo().getFirstName())
                .lastName(user.getUserInfo().getLastName())
                .build();
    }
}
