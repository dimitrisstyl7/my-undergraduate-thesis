package gr.unipi.thesis.dimstyl.controllers.api;

import gr.unipi.thesis.dimstyl.dtos.api.ApiProfileDto;
import gr.unipi.thesis.dimstyl.entities.UserInfo;
import gr.unipi.thesis.dimstyl.security.CustomUserDetailsService;
import gr.unipi.thesis.dimstyl.services.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/profile")
@RequiredArgsConstructor
public class ApiProfileController {

    private final CustomUserDetailsService userDetailsService;
    private final UserInfoService userInfoService;

    @GetMapping
    public ResponseEntity<ApiProfileDto> getProfile() {
        UserInfo userInfo = userDetailsService.getUserDetails().user().getUserInfo();
        ApiProfileDto apiProfileDto = ApiProfileDto.builder()
                .firstName(userInfo.getFirstName())
                .lastName(userInfo.getLastName())
                .email(userInfo.getEmail())
                .phone(userInfo.getPhone())
                .gender(userInfo.getGender())
                .dateOfBirth(userInfo.getDateOfBirth())
                .build();

        return ResponseEntity.ok(apiProfileDto);
    }

    @PutMapping
    public ResponseEntity<Void> updateProfile(@RequestBody ApiProfileDto apiProfileDto) {
        UserInfo userInfo = userDetailsService.getUserDetails().user().getUserInfo();

        userInfo.setFirstName(apiProfileDto.firstName());
        userInfo.setLastName(apiProfileDto.lastName());
        userInfo.setEmail(apiProfileDto.email());
        userInfo.setPhone(apiProfileDto.phone());
        userInfo.setGender(apiProfileDto.gender());
        userInfo.setDateOfBirth(apiProfileDto.dateOfBirth());
        userInfoService.updateUserInfo(userInfo);

        return ResponseEntity.ok().build();
    }

}
