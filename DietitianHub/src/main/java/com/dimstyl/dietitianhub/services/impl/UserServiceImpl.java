package com.dimstyl.dietitianhub.services.impl;

import com.dimstyl.dietitianhub.dtos.ClientDto;
import com.dimstyl.dietitianhub.entities.User;
import com.dimstyl.dietitianhub.mappers.ClientDtoMapper;
import com.dimstyl.dietitianhub.mappers.UserMapper;
import com.dimstyl.dietitianhub.repositories.UserRepository;
import com.dimstyl.dietitianhub.services.UserInfoService;
import com.dimstyl.dietitianhub.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
                .toList();
    }

    @Override
    public void registerClient(ClientDto clientDto) {
        User user = ClientDtoMapper.mapToNewRegistrationUser(clientDto);

        //Todo: Firstly check if the username is already in use and throw an exception if it is.
        User newUser = userRepository.save(user);

        //Todo: What if the save operation fails? Rollback the transaction.
        User newUser = userRepository.save(user);
        userInfoService.saveUserInfo(clientDto, newUser);
    }

    @Override
    public void disableClient(Long id) {
        // Find the user by id and set the enabled field to false.
        // Todo: Catch the exception (NoSuchElementException) in custom exception handler and handle it properly.
        User user = userRepository.findById(id).orElseThrow();
        user.setEnabled(false);
        userRepository.save(user);
    }

    @Override
    public User findById(Integer id) {
        // Todo: Throw a custom exception if the user is not found and handle it in the custom exception handler.
        return userRepository.findById(id).orElseThrow();
    }
}