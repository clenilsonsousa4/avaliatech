package br.com.fiap.service;

import br.com.fiap.model.Rating;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class RatingReportService {
    private static final Logger logger = Logger.getLogger(RatingReportService.class.getName());

    @Inject
    EmailService emailService;

    @Inject
    RatingListService  ratingListService;

    public String sendReport() {
        List<Rating> weeklyRatings = ratingListService.getWeeklyRatings();

        emailService.sendEmail("Relatório Semanal de Feedbacks", generateMailBody(weeklyRatings));
        return "Report sent!";
    }

    private String generateMailBody(List<Rating> weeklyRating) {
        Map<String, Integer> ratingsByDay = new HashMap<>();
        int criticRatings = 0;
        int badRatings = 0;
        int goodRatings = 0;
        int greatRatings = 0;

        for(Rating rating : weeklyRating) {
            String ratingCreationDay = rating.createdAt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            if(ratingsByDay.containsKey(ratingCreationDay)) {
                ratingsByDay.replace(ratingCreationDay, ratingsByDay.get(ratingCreationDay) + 1);
            } else {
                ratingsByDay.put(ratingCreationDay, 1);
            }

            if(rating.getRating() <= 3) {
                criticRatings++;
            } else if(rating.getRating() <= 5) {
                badRatings++;
            } else if(rating.getRating() <= 8) {
                goodRatings++;
            } else {
                greatRatings++;
            }
        }

        String dateToday = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        StringBuilder emailBody = new StringBuilder("<h1>Relatório Semanal das Avaliações</h1>\n");
        emailBody.append(String.format("<p>Relatório semanal enviado no dia %1$s com a listagem das avaliações por dia da semana e urgência.</p>\n", dateToday));
        emailBody.append("<h2>Quantidade de Avaliações Por Dia:</h2>\n");
        emailBody.append("<ul>\n");

        ratingsByDay.forEach((key, value) -> emailBody.append(String.format("<li>Dia %s: %s</li>\n", key, value)));

        emailBody.append("</ul>\n");
        emailBody.append("<h2>Quantidade de Avaliações Por Urgência:</h2>\n");
        emailBody.append("<ul>\n");
        emailBody.append(String.format("<li>Crítica: %s</li>\n", criticRatings));
        emailBody.append(String.format("<li>Ruim: %s</li>\n", badRatings));
        emailBody.append(String.format("<li>Boa: %s</li>\n", goodRatings));
        emailBody.append(String.format("<li>Ótima: %s</li>\n", greatRatings));
        emailBody.append("</ul>\n");
        emailBody.append("</body>\n");
        emailBody.append("</html>\n");

        logger.info("Body generated for the sent report:\n" + emailBody);

        return emailBody.toString();
    }
}
