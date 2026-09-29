package com.omnitrak.users.infrastructure.adapters.mail;

import com.omnitrak.users.domain.ports.out.auth.EmailNotificationPort;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JavaMailNotificationAdapter implements EmailNotificationPort {

    private final JavaMailSender mailSender;
    private final String activationBaseUrl;
    private final String resetPasswordBaseUrl;
    private final String fromEmail;

    public JavaMailNotificationAdapter(JavaMailSender mailSender,
                                       @Value("${app.mail.activation-url}")String activationBaseUrl,
                                       @Value("${app.mail.reset-password-url:http://localhost:8081/api/v1/auth/reset-password?token=}") String resetPasswordBaseUrl,
                                       @Value("${app.mail.from}") String fromEmail) {
        this.mailSender = mailSender;
        this.activationBaseUrl = activationBaseUrl;
        this.resetPasswordBaseUrl = resetPasswordBaseUrl;
        this.fromEmail = fromEmail;
    }


    @Override
    public void sendAccountActivationEmail(String toEmail, String activationToken) {
        String link = activationBaseUrl + activationToken;
        String subject = "Activa tu cuenta en OmniTrak";
        String htmlContent = """
            <div style="font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #e2e8f0; border-radius: 8px;">
                <h2 style="color: #2b6cb0;">¡Bienvenido a OmniTrak!</h2>
                <p>Gracias por registrarte. Para completar la activación de tu cuenta, haz clic en el siguiente enlace:</p>
                <div style="text-align: center; margin: 30px 0;">
                    <a href="%s" style="background-color: #3182ce; color: white; padding: 12px 24px; text-decoration: none; border-radius: 5px; font-weight: bold; display: inline-block;">Activar mi cuenta</a>
                </div>
                <p style="font-size: 12px; color: #718096;">Este enlace expirará en 24 horas.</p>
                <p style="font-size: 12px; color: #a0aec0;">Si no creaste esta cuenta, puedes ignorar este mensaje.</p>
            </div>
            """.formatted(link);

        sendHtmlEmail(toEmail, subject, htmlContent);
    }

    @Override
    public void sendPasswordResetEmail(String toEmail, String resetToken) {
        String link = resetPasswordBaseUrl + resetToken;
        String subject = "Recuperación de contraseña en OmniTrak";
        String htmlContent = """
            <div style="font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #e2e8f0; border-radius: 8px;">
                <h2 style="color: #c53030;">Recuperación de contraseña</h2>
                <p>Hemos recibido una solicitud para restablecer la contraseña de tu cuenta.</p>
                <div style="text-align: center; margin: 30px 0;">
                    <a href="%s" style="background-color: #e53e3e; color: white; padding: 12px 24px; text-decoration: none; border-radius: 5px; font-weight: bold; display: inline-block;">Restablecer contraseña</a>
                </div>
                <p style="font-size: 12px; color: #718096;">Este enlace expirará en 15 minutos.</p>
                <p style="font-size: 12px; color: #a0aec0;">Si tú no solicitaste este cambio, ignora este correo. Tu contraseña actual no se modificará.</p>
            </div>
            """.formatted(link);

        sendHtmlEmail(toEmail, subject, htmlContent);
    }

    private void sendHtmlEmail(String to, String subject, String htmlBody) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");

            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlBody, true); // true indica que es HTML

            mailSender.send(message);
            log.info("Correo '{}' enviado exitosamente a {}", subject, to);
        } catch (MessagingException e) {
            log.error("Error al enviar el correo '{}' a {}", subject, to, e);
            throw new IllegalStateException("No se pudo enviar el correo", e);
        }
    }
}
