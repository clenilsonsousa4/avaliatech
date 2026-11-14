package br.com.fiap.lambda;

import br.com.fiap.model.Rating;
import br.com.fiap.service.EmailService;
import br.com.fiap.service.RatingListService;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.List;

@Named("ratingReport")
public class RatingReportLambda implements RequestHandler<Object, String> {
    @Inject
    EmailService emailService;

    @Inject
    RatingListService  ratingListService;

    @Override
    public String handleRequest(Object input, Context context) {
        List<Rating> weeklyRatings = ratingListService.getWeeklyRatings();

        String bodyEmail = """                
                <h1>Relatório Semanal das Avaliações</h1>
                <p>Relatório semanal enviado no dia ??? com a listagem das avaliações por dia da semana e urgência.</p>
        
                <h2>Quantidade de Avaliações Por Dia:</h2>
                <ul>
                  <li>Dia :</li>
                  <li>Dia :</li>
                  <li>Dia :</li>
                  <li>Dia :</li>
                  <li>Dia :</li>
                  <li>Dia :</li>
                  <li>Dia :</li>
                </ul>
        
                <h2>Quantidade de Avaliações Por Urgência:</h2>
                <ul>
                  <li>Crítica: </li>
                  <li>Ruim: </li>
                  <li>Boa: </li>
                  <li>Ótima: </li>
                </ul>
        
                </body>
                </html>
        """;

        emailService.sendEmail("Relatório Semanal de Feedbacks", bodyEmail);
        return "Report sent!";
    }
}
