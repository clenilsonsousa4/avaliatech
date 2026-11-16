package br.com.fiap.lambda;

import br.com.fiap.service.RatingReportService;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.List;

@Named("ratingReport")
public class RatingReportLambda implements RequestHandler<Object, String> {
    @Inject
    RatingReportService ratingReportService;

    @Override
    public String handleRequest(Object input, Context context) {
        return ratingReportService.sendReport();
    }
}
