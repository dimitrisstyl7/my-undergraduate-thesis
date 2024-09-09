package gr.unipi.thesis.dimstyl.services;

import gr.unipi.thesis.dimstyl.dtos.ClientCredentialChangeDto;
import gr.unipi.thesis.dimstyl.dtos.ClientDto;
import gr.unipi.thesis.dimstyl.entities.User;
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