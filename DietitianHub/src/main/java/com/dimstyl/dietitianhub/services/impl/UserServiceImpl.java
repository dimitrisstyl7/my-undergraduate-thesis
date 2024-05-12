package com.dimstyl.dietitianhub.services.impl;

import com.dimstyl.dietitianhub.dtos.ClientDto;
import com.dimstyl.dietitianhub.entities.User;
import com.dimstyl.dietitianhub.entities.UserInfo;
import com.dimstyl.dietitianhub.mappers.ClientDtoMapper;
import com.dimstyl.dietitianhub.mappers.UserMapper;
import com.dimstyl.dietitianhub.repositories.UserRepository;
import com.dimstyl.dietitianhub.services.UserInfoService;
import com.dimstyl.dietitianhub.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.dimstyl.dietitianhub.enums.UserRole.CLIENT;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserInfoService userInfoService;

    @Override
    public List<ClientDto> getAllClients() {
        List<User> clients = userRepository.findAllByRole_NameAndEnabledIsTrue(CLIENT.getRole());
        return clients.stream()
                .map(UserMapper::mapToClientDto)
                .collect(Collectors.toList());
    }

    @Override
    public void registerClient(ClientDto clientDto) {
        // Create a User entity from the ClientDto and save it to the database.
        User user = ClientDtoMapper.mapToUserOfRoleClient(clientDto);
        User newUser = userRepository.save(user);

        // Create a UserInfo entity from the ClientDto and newUser entity and save it to the database.
        UserInfo userInfo = ClientDtoMapper.mapToUserInfo(clientDto, newUser);
        userInfoService.saveUserInfo(userInfo);
    }

    @Override
    public void deleteClient(Long id) {
        // Find the user by id and set the enabled field to false.
        // Todo: Catch the exception in custom exception handler and handle it properly.
        User user = userRepository.findById(id).orElseThrow();
        user.setEnabled(false);
        userRepository.save(user);
    }
}