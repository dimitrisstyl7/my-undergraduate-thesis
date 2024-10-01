package gr.unipi.thesis.dimstyl.services;

import gr.unipi.thesis.dimstyl.dtos.web.WebClientCredentialChangeDto;
import gr.unipi.thesis.dimstyl.dtos.web.WebClientDto;
import gr.unipi.thesis.dimstyl.entities.User;
import jakarta.mail.MessagingException;

import java.util.List;

public interface UserService {

    List<WebClientDto> getAllClients();

    void registerClient(WebClientDto webClientDto) throws MessagingException;

    void deleteUser(int id);

    User getUser(int id);

    boolean usernameExists(String username);

    void updateClientCredentials(WebClientCredentialChangeDto credentialChangeDto);

}