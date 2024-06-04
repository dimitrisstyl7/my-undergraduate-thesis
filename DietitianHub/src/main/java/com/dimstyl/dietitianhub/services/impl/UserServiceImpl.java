package com.dimstyl.dietitianhub.services.impl;

import com.dimstyl.dietitianhub.customExceptions.ApiUserNotFoundException;
import com.dimstyl.dietitianhub.customExceptions.MvcUserNotFoundException;
import com.dimstyl.dietitianhub.dtos.ClientDto;
import com.dimstyl.dietitianhub.entities.User;
import com.dimstyl.dietitianhub.mappers.ClientDtoMapper;
import com.dimstyl.dietitianhub.mappers.UserMapper;
import com.dimstyl.dietitianhub.repositories.UserRepository;
import com.dimstyl.dietitianhub.services.UserInfoService;
import com.dimstyl.dietitianhub.services.UserService;
import jakarta.transaction.Transactional;
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
    @Transactional
    public void registerClient(ClientDto clientDto) {
        User user = ClientDtoMapper.mapToNewRegistrationUser(clientDto);

        if (userRepository.existsByUsername(user.getUsername())) {
            // Todo: Throw a custom exception if the username is already taken and handle it in the custom exception handler.
        }

        User newUser = userRepository.save(user);
        userInfoService.saveUserInfo(clientDto, newUser);
    }

    @Override
    public void disableClient(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new ApiUserNotFoundException("User with id " + id + " not found."));
        user.setEnabled(false);
        userRepository.save(user);
    }

    @Override
    public User findById(Integer id) {
        return userRepository.findById(id).orElseThrow(() ->
                new MvcUserNotFoundException("User with id " + id + " not found."));
    }

}