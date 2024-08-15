package com.dimstyl.dietitianhub.services;

import com.dimstyl.dietitianhub.dtos.ClientCredentialChangeDto;
import com.dimstyl.dietitianhub.dtos.ClientDto;
import com.dimstyl.dietitianhub.entities.User;
import jakarta.mail.MessagingException;

import java.util.List;

public interface UserService {

    List<ClientDto> getAllClients();

    void registerClient(ClientDto clientDto) throws MessagingException;

    void deleteUser(int id);

    User getUser(int id);

    boolean usernameExists(String username);

    void updateClientCredentials(ClientCredentialChangeDto credentialChangeDto);

}