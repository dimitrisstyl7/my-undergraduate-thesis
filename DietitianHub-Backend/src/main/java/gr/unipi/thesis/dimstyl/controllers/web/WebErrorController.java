package gr.unipi.thesis.dimstyl.controllers.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class WebErrorController {

    @GetMapping("/400")
    public String BadRequestPage() {
        return "error/400";
    }

    @GetMapping("/403")
    public String ForbiddenPage() {
        return "error/403";
    }

    @GetMapping("/404")
    public String NotFoundPage() {
        return "error/404";
    }

    @GetMapping("/405")
    public String MethodNotAllowedPage() {
        return "error/405";
    }

    @GetMapping("/500")
    public String InternalServerErrorPage() {
        return "error/500";
    }

    @GetMapping("/userNotFound")
    public String UserNotFoundPage() {
        return "error/user-not-found";
    }

    @GetMapping("/tagsMismatch")
    public String TagMismatchPage() {
        return "error/tags-mismatch";
    }

    @GetMapping("/dietPlanNotFound")
    public String DietPlanNotFoundPage() {
        return "error/diet-plan-not-found";
    }

    @GetMapping("/dietPlanUploadFailed")
    public String DietPlanUploadFailedPage() {
        return "error/diet-plan-upload-failed";
    }

    @GetMapping("/dietPlanDeleteFailed")
    public String DietPlanDeleteFailedPage() {
        return "error/diet-plan-delete-failed";
    }

    @GetMapping("/articleNotFound")
    public String ArticleNotFoundPage() {
        return "error/article-not-found";
    }

    @GetMapping("/announcementNotFound")
    public String AnnouncementNotFoundPage() {
        return "error/announcement-not-found";
    }

    @GetMapping("/appointmentNotFound")
    public String AppointmentNotFoundPage() {
        return "error/appointment-not-found";
    }

}
