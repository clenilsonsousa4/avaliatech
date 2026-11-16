package br.com.fiap.service;

import br.com.fiap.model.Rating;
import br.com.fiap.model.RatingDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class RatingProcessService {
    @Inject
    EmailService emailService;

    @Transactional
    public String processRating(RatingDTO ratingDTO) {
        if(ratingDTO.rating() < 1 || ratingDTO.rating() > 10) {
            return "Rating value must be between 1 and 10.";
        } else if(ratingDTO.description() == null || ratingDTO.description().isBlank()) {
            return "Rating description must be informed.";
        }

        var ratingModel = new Rating(ratingDTO.rating(), ratingDTO.description());
        ratingModel.persist();

        if(ratingModel.getRating() <= 3) {
            emailService.sendEmail(ratingModel);
        }

        return "Rating received. ID: " + ratingModel.getId();
    }
}
