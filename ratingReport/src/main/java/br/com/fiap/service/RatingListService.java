package br.com.fiap.service;

import br.com.fiap.model.Rating;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class RatingListService {
    private static final Logger logger = Logger.getLogger(RatingListService.class.getName());

    @Transactional
    public List<Rating> getWeeklyRatings() {
        LocalDate startTime = LocalDate.now().minusDays(7);

        logger.info("Value for startTime: " + startTime);

        return Rating.findWeeklyRatings(startTime);
    }
}
