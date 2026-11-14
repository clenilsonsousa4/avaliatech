package br.com.fiap.lambda;

import br.com.fiap.model.Rating;
import br.com.fiap.service.RatingListService;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.List;

@Named("ratingList")
public class RatingListLambda implements RequestHandler<Object, List<Rating>> {
    @Inject
    RatingListService ratingListService;

    @Override
    public List<Rating> handleRequest(Object input, Context context) {
        return ratingListService.getRatings();
    }
}
