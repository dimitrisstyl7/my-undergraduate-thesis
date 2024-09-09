package gr.unipi.thesis.dimstyl.email;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class EmailConfig {

    @Value("${spring.mail.host}")
    private String mailServerHost;

    @Value("${spring.mail.port}")
    private Integer mailServerPort;

    @Value("${spring.mail.username}")
    private String mailServerUsername;

    @Value("${spring.mail.password}")
    private String mailServerPassword;

    @Value("${spring.mail.properties.mail.smtp.auth}")
    private String mailServerAuth;

    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private String mailServerStartTls;

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(mailServerHost);
        mailSender.setPort(mailServerPort);

        mailSender.setUsername(mailServerUsername);
        mailSender.setPassword(mailServerPassword);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", mailServerAuth);
        props.put("mail.smtp.starttls.enable", mailServerStartTls);
        props.put("mail.debug", "true");

        return mailSender;
    }

    @Bean
    public String registrationEmailTemplate() {
        return """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <style>
                        body {
                            font-family: Arial, sans-serif;
                            line-height: 1.6;
                            color: #333;
                            background-color: #f9f9f9;
                            margin: 0;
                            padding: 0;
                        }
                        .email-container {
                            max-width: 600px;
                            margin: 20px auto;
                            background-color: #fff;
                            padding: 20px;
                            border-radius: 8px;
                            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
                        }
                        .content {
                            font-size: 16px;
                        }
                        .content p {
                            margin-bottom: 20px;
                        }
                        .button {
                            display: inline-block;
                            padding: 10px 20px;
                            margin-top: 10px;
                            background-color: #4CAF50;
                            color: #ffffff;
                            text-decoration: none;
                            border-radius: 4px;
                            text-align: center;
                        }
                        .footer {
                            margin-top: 30px;
                            font-size: 14px;
                            text-align: center;
                            color: #888;
                        }
                    </style>
                </head>
                <body>
                    <div class="email-container">
                        <div class="content">
                            <p>Hello, <strong>%s</strong>!</p>
                            <p>Your dietitian, <strong>%s</strong>, has just registered you to DietitianHub.</p>
                            <p>Your username is: <strong>%s</strong>, and your password is: <strong>%s</strong> .</p>
                            <p>To activate your account, please update your credentials for security purposes. \
                            To do so, click the button below:</p>
                
                            <p>
                                <a href="http://localhost:8080/auth/updateCredentials" target="_blank" class="button">
                                Update Credentials
                                </a>
                            </p>
                            <p>If you have any questions, please do not hesitate to contact us.</p>
                        </div>
                        <div class="footer">
                            Best regards,<br>
                            The DietitianHub Team
                        </div>
                    </div>
                </body>
                </html>
                """;
    }

}
