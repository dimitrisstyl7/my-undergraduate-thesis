package com.dimstyl.dietitianhub.mappers;

import com.dimstyl.dietitianhub.dtos.ClientDto;
import com.dimstyl.dietitianhub.entities.Role;
import com.dimstyl.dietitianhub.entities.User;
import com.dimstyl.dietitianhub.entities.UserInfo;

import static com.dimstyl.dietitianhub.enums.UserRole.CLIENT;
import static com.dimstyl.dietitianhub.utilities.RegistrationUtil.generateUsername;

public class ClientDtoMapper {

    public static User mapToNewRegistrationUser(ClientDto clientDto) {
        return User.builder()
                .username(generateUsername(
                        clientDto.getFirstName(),
                        clientDto.getLastName(),
                        clientDto.getDateOfBirth()))
                /* Todo: For new clients, the password will by empty.
                    A registration email will be sent to the client
                    with a link to set a password.
                 */
                .password("")
                .role(new Role(CLIENT.getId(), CLIENT.getRole()))
                .build();
    }

    public static UserInfo mapToUserInfo(ClientDto clientDto, User user) {
        return UserInfo.builder()
                .user(user)
                .firstName(clientDto.getFirstName())
                .lastName(clientDto.getLastName())
                .gender(clientDto.getGender())
                .dateOfBirth(clientDto.getDateOfBirth())
                .email(clientDto.getEmail())
                .phone(clientDto.getPhone())
                .build();
    }

}
