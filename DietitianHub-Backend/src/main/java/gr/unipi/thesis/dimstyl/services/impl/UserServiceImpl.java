package gr.unipi.thesis.dimstyl.services.impl;

import gr.unipi.thesis.dimstyl.dtos.ClientCredentialChangeDto;
import gr.unipi.thesis.dimstyl.dtos.ClientDto;
import gr.unipi.thesis.dimstyl.email.EmailService;
import gr.unipi.thesis.dimstyl.entities.DietPlan;
import gr.unipi.thesis.dimstyl.entities.User;
import gr.unipi.thesis.dimstyl.enums.UserRole;
import gr.unipi.thesis.dimstyl.exceptions.user.RegistrationFailedException;
import gr.unipi.thesis.dimstyl.exceptions.user.UserNotFoundException;
import gr.unipi.thesis.dimstyl.repositories.UserRepository;
import gr.unipi.thesis.dimstyl.security.CustomUserDetailsService;
import gr.unipi.thesis.dimstyl.services.StorageService;
import gr.unipi.thesis.dimstyl.services.UserInfoService;
import gr.unipi.thesis.dimstyl.services.UserService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserInfoService userInfoService;
    private final CustomUserDetailsService userDetailsService;
    private final EmailService emailService;
    private final StorageService storageService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<ClientDto> getAllClients() {
        List<User> clients = userRepository.findAllByRoleAndEnabledIsTrueOrderByUserInfo_FirstName(UserRole.CLIENT);
        return clients.stream()
                .map(User::toClientDto)
                .toList();
    }

    @Override
    @Transactional
    public void registerClient(ClientDto clientDto) throws MessagingException {
        User user = clientDto.toUserForRegistration(UserRole.CLIENT);

        if (usernameExists(user.getUsername())) {
            throw new RegistrationFailedException(
                    "Registration failed. User with username %s already exists.".formatted(user.getUsername())
            );
        }

        // Extract email, full name, username, and password from clientDto
        String email = clientDto.getEmail();
        String fullName = clientDto.getFullName();
        String username = user.getUsername();
        String password = user.getPassword();

        // Save user and user info in database
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User newUser = userRepository.save(user);
        userInfoService.saveUserInfo(clientDto, newUser);

        // Send email to client
        Map<String, String> args = Map.of(
                "clientFullName", fullName,
                "username", username,
                "password", password
        );
        emailService.sendEmail(email, args);
    }

    @Override
    @Transactional
    public void deleteUser(int id) {
        User user = getUser(id);
        List<DietPlan> dietPlans = user.getUserInfo().getDietPlans();
        dietPlans.forEach(dietPlan -> storageService.deleteDietPlanFile(dietPlan.getName()));
        userRepository.delete(user);
    }

    @Override
    public User getUser(int id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id %d not found.".formatted(id)));
    }

    @Override
    public boolean usernameExists(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    @Transactional
    public void updateClientCredentials(ClientCredentialChangeDto credentialChangeDto) {
        String oldUsername = userDetailsService.getUserDetails().getUsername();
        String newUsername = credentialChangeDto.getUsername();
        String password = credentialChangeDto.getPassword();

        User user = userRepository
                .findByUsername(oldUsername)
                .orElseThrow(() -> new UserNotFoundException("User with username %s not found.".formatted(oldUsername)));

        user.setUsername(newUsername);
        user.setPassword(passwordEncoder.encode(password));
        user.setEnabled(true);
    }

}