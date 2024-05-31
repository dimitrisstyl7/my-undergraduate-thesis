package com.dimstyl.dietitianhub.api;

import com.dimstyl.dietitianhub.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ApiClientsController {
    private final UserService userService;

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> disableClient(@PathVariable("id") Integer id) {
        userService.disableClient(id);
        return ResponseEntity.noContent().build();
    }
}
