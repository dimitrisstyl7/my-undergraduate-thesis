package gr.unipi.thesis.dimstyl.controllers.api;

import gr.unipi.thesis.dimstyl.dtos.api.ApiDietPlanDto;
import gr.unipi.thesis.dimstyl.enums.RequestType;
import gr.unipi.thesis.dimstyl.security.CustomUserDetailsService;
import gr.unipi.thesis.dimstyl.services.DietPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/dietPlans")
@RequiredArgsConstructor
public class ApiDietPlanController {

    private final CustomUserDetailsService userDetailsService;
    private final DietPlanService dietPlanService;

    @GetMapping
    public ResponseEntity<List<ApiDietPlanDto>> getDietPlans() {
        String username = userDetailsService.getUserDetails().getUsername();
        return ResponseEntity.ok(dietPlanService.getLatest15DietPlans(username));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource> downloadDietPlan(@PathVariable("id") int dietPlanId) {
        Resource file = dietPlanService.getDietPlanFileAsResource(dietPlanId, RequestType.API);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + file.getFilename() + "\"")
                .contentType(MediaType.APPLICATION_PDF)
                .body(file);
    }

}
