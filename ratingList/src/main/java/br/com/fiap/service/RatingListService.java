package br.com.fiap.service;

import br.com.fiap.model.Rating;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class RatingListService {
    @Transactional
    public List<Rating> getRatings() {
        return Rating.listAll();
    }
}
