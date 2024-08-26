package com.dimstyl.dietitianhub.services.impl;

import com.dimstyl.dietitianhub.dtos.ClientCredentialChangeDto;
import com.dimstyl.dietitianhub.dtos.ClientDto;
import com.dimstyl.dietitianhub.email.EmailService;
import com.dimstyl.dietitianhub.entities.DietPlan;
import com.dimstyl.dietitianhub.entities.User;
import com.dimstyl.dietitianhub.enums.UserRole;
import com.dimstyl.dietitianhub.exceptions.RegistrationFailedException;
import com.dimstyl.dietitianhub.exceptions.UserNotFoundException;
import com.dimstyl.dietitianhub.repositories.UserRepository;
import com.dimstyl.dietitianhub.services.StorageService;
import com.dimstyl.dietitianhub.services.UserInfoService;
import com.dimstyl.dietitianhub.services.UserService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static com.dimstyl.dietitianhub.security.CustomUserDetailsService.getUserDetails;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserInfoService userInfoService;
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
        String oldUsername = getUserDetails().getUsername();
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