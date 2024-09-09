package gr.unipi.thesis.dimstyl.controllers.api;

import gr.unipi.thesis.dimstyl.dtos.TagDto;
import gr.unipi.thesis.dimstyl.services.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/clients")
@RequiredArgsConstructor
public class ApiClientController {

    private final UserInfoService userInfoService;

    @GetMapping("/{id}/tags")
    public ResponseEntity<List<TagDto>> getClientTags(@PathVariable("id") int id) {
        return ResponseEntity.ok(userInfoService.getClientTags(id));
    }

    @PutMapping("/{id}/tags")
    public ResponseEntity<Void> updateClientTags(@PathVariable("id") int id, @RequestBody List<Integer> tagIds) {
        userInfoService.updateClientTags(id, tagIds);
        return ResponseEntity.noContent().location(URI.create("/clients?tagsUpdateSuccess")).build();
    }

}
