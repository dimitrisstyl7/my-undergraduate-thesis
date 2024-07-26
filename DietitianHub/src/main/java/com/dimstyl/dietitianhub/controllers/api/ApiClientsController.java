package com.dimstyl.dietitianhub.controllers.api;

import com.dimstyl.dietitianhub.dtos.TagDto;
import com.dimstyl.dietitianhub.services.UserInfoService;
import com.dimstyl.dietitianhub.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/clients/")
@RequiredArgsConstructor
public class ApiClientsController {

    private final UserService userService;
    private final UserInfoService userInfoService;

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> disableClient(@PathVariable("id") int id) {
        userService.disableClient(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/tags")
    public ResponseEntity<List<TagDto>> getClientTags(@PathVariable("id") int id) {
        return ResponseEntity.ok(userInfoService.getClientTags(id));
    }

    @PutMapping("/{id}/tags")
    public ResponseEntity<Void> updateClientTags(@PathVariable("id") int id, @RequestBody List<Integer> tagIds) {
        userInfoService.updateClientTags(id, tagIds);
        return ResponseEntity.noContent().build();
    }

}
