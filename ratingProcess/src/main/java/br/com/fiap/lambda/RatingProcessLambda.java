package br.com.fiap.lambda;

import br.com.fiap.model.RatingDTO;

import br.com.fiap.service.RatingProcessService;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named("ratingProcess")
public class RatingProcessLambda implements RequestHandler<RatingDTO, String> {
    @Inject
    RatingProcessService ratingProcessService;

    @Override
    public String handleRequest(RatingDTO ratingDTO, Context context) {
        return ratingProcessService.processRating(ratingDTO);
    }
}
