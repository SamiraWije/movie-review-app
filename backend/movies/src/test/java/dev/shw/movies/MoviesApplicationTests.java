package dev.shw.movies;

import dev.shw.movies.repository.MovieRepository;
import dev.shw.movies.repository.ReviewRepository;
import dev.shw.movies.service.MovieService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class MoviesApplicationTests {

	@Mock
	MovieRepository movieRepository;

	@Mock
	ReviewRepository reviewRepository;

	@InjectMocks
	MovieService movieService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	@DisplayName("Test all movies list")
	void testAllMovies() {

		List<Movie> sampleMovies = List.of(
				new Movie(new ObjectId(), "imdbId1", "Movie 1", null, null, null, null, null, null),
				new Movie(new ObjectId(), "imdbId2", "Movie 2", null, null, null, null, null, null)
		);

		when(movieRepository.findAll()).thenReturn(sampleMovies);

		List<Movie> result = movieService.allMovies();
		assertEquals(sampleMovies, result);
		verify(movieRepository, times(1)).findAll();

	}

	@Test
	@DisplayName("Test single movie")
	void testSingleMovie() {

		String movieId = "imdbId1";
		Movie sampleMovie = new Movie(new ObjectId(), "imdbId1", "Movie 1", null, null, null, null, null, null);

		when(movieRepository.findMovieByImdbId(movieId)).thenReturn(Optional.of(sampleMovie));

		Optional<Movie> result = movieService.singleMovie(movieId);
		assertEquals(Optional.of(sampleMovie),result);
	}

	@Test
	@DisplayName("Test review creation")
	void testCreateReview() {
		String reviewBody = "This is a nice movie";
		String movieId = "imdb3";

	Review sampleReview = new Review(reviewBody, LocalDateTime.now(), LocalDateTime.now());

	when(reviewRepository.insert(any(Review.class))).thenReturn(sampleReview);
	}

}
