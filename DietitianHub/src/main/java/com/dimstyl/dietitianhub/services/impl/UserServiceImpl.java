package com.dimstyl.dietitianhub.services.impl;

import com.dimstyl.dietitianhub.dtos.ClientDto;
import com.dimstyl.dietitianhub.entities.User;
import com.dimstyl.dietitianhub.mappers.UserMapper;
import com.dimstyl.dietitianhub.repositories.UserRepository;
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

    @Override
    public List<ClientDto> getAllClients() {
        List<User> clients = userRepository.findAllByRole_NameAndEnabledIsTrue(CLIENT.getRole());
        return clients.stream()
                .map(UserMapper::mapToClientDto)
                .collect(Collectors.toList());
    }
}