package com.dimstyl.dietitianhub.services;

import com.dimstyl.dietitianhub.dtos.ClientDto;

import java.util.List;

public interface UserService {
    List<ClientDto> getAllClients();

    void registerClient(ClientDto clientDto);
}