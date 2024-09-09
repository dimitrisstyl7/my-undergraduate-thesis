package gr.unipi.thesis.dimstyl.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Map;

import static gr.unipi.thesis.dimstyl.security.CustomUserDetailsService.getUserDetails;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    // In a real-world application, we should use a no-reply email address
    @Value("${spring.mail.username}")
    private String from;
    private final JavaMailSender emailSender;
    private final String htmlTemplate;

    public void sendEmail(String to, Map<String, String> args) throws MessagingException {
        String text = htmlTemplate.formatted(
                args.get("clientFullName"),
                getUserDetails().getFullName(),
                args.get("username"),
                args.get("password")
        );

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject("Welcome to DietitianHub");
        helper.setText(text, true);
        emailSender.send(message);
    }

}
