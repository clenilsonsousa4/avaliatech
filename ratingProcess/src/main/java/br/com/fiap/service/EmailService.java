package br.com.fiap.service;

import br.com.fiap.model.Rating;
import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import org.jboss.logging.Logger;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@ApplicationScoped
public class EmailService {
    private static final Logger logger = Logger.getLogger(EmailService.class.getName());

    @ConfigProperty(name = "report.mail.to")
    String sendTo;

    @Inject
    Mailer mailer;

    public void sendEmail(Rating rating) {
        logger.info("Sending email to " + sendTo);
        String body = generateMailBody(rating);
        mailer.send(Mail.withText(sendTo, "Recebimento de Avaliação Crítica", body));
        logger.infof("Email sent to: %s", sendTo);
    }

    private String generateMailBody(Rating rating) {
        String dateToday = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        StringBuilder emailBody = new StringBuilder("<h1>Relatório de Recebimento de Avaliação Crítica</h1>\n");
        emailBody.append(String.format("<p>Foi recebida uma avaliação crítica na data %s, com a nota %d e descrição \"%s\"</p>\n", dateToday, rating.getRating(), rating.getDescription()));

        emailBody.append("</body>\n");
        emailBody.append("</html>\n");

        logger.info("Body generated for the sent report:\n" + emailBody);

        return emailBody.toString();
    }
}
