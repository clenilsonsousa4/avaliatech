package br.com.fiap.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

@Entity
public class Rating extends PanacheEntity {
    public int rating;
    public String description;
    public LocalDateTime createdAt;

    public static List<Rating> findWeeklyRatings(LocalDate startTime) {
        return list("cast(createdAt as date) BETWEEN ?1 AND current_date()", startTime);
    }

    public Long getId() {
        return id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
