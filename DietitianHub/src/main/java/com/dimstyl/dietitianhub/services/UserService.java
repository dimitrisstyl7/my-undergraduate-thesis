package com.dimstyl.dietitianhub.services;

import com.dimstyl.dietitianhub.dtos.ClientDto;
import com.dimstyl.dietitianhub.entities.User;

import java.util.List;

public interface UserService {

    List<ClientDto> getAllClients();

    void registerClient(ClientDto clientDto);

    void disableClient(int id);

    User findById(int id);

}