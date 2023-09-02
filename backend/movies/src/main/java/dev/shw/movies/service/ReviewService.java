package dev.shw.movies.service;

import dev.shw.movies.Movie;
import dev.shw.movies.Review;
import dev.shw.movies.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public Review createReview(String reviewBody, String imdbId) {

        Review review = reviewRepository.insert(new Review(reviewBody, LocalDateTime.now(), LocalDateTime.now()));

        mongoTemplate.update(Movie.class)
                .matching((Criteria.where("imdbId").is(imdbId)))
                .apply(new Update().push("reviewIds").value(review))
                .first();

        return review;
    }

//    public List<Review> allReviews() {
//        return reviewRepository.findAll();
//    }
}
