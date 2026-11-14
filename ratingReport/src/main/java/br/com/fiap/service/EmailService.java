package br.com.fiap.service;

import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import io.quarkus.mailer.MailerName;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import org.jboss.logging.Logger;

@ApplicationScoped
public class EmailService {
    private static final Logger logger = Logger.getLogger(EmailService.class.getName());

    @ConfigProperty(name = "report.mail.to")
    String sendTo;

    @ConfigProperty(name = "report.mail.from")
    String from;

    @Inject
    @MailerName("aws")
    Mailer mailer;

    public void sendEmail(String subject, String body) {
        logger.info("Sending email to " + sendTo);
        mailer.send(Mail.withText(sendTo, subject, body).setFrom(from));
        logger.infof("Email sent to: %s", sendTo);
    }
}
