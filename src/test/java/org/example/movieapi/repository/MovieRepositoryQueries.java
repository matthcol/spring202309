package org.example.movieapi.repository;

import org.example.movieapi.entity.Movie;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.test.context.ActiveProfiles;

import java.util.stream.Stream;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.contains;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
@ActiveProfiles("queries")
public class MovieRepositoryQueries {

    @Autowired
    MovieRepository movieRepository;

    @Test
    void findById(){
        int movieId = 5257;
        var optMovie = movieRepository.findById(movieId);
        System.out.println(optMovie);
        var movie = optMovie.get();
        System.out.println("director: " + movie.getDirector());
        System.out.println("actors:");
        movie.getActors()
                .forEach(actor -> System.out.println("\t- " + actor));
    }

    static Stream<Example<Movie>> movieExampleSource() {
        return Stream.of(
                Example.of(
                    Movie.builder()
                            .title("The Man Who Knew Too Much")
                            .year((short) 1956)
                            .build()
                ),
                Example.of(
                        Movie.builder()
                                .title("The Man Who Knew Too Much")
                                .build(),
                        ExampleMatcher.matching()
                                .withIgnorePaths("year")
                ),
                // title: star (partial, case insensitive)
                Example.of(
                        Movie.builder()
                                .title("star")
                                .build(),
                        ExampleMatcher.matching()
                                .withMatcher("title", contains().ignoreCase())
                                .withIgnorePaths("year")
                )
        );
    }

    // doc: https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#query-by-example
    @ParameterizedTest
    @MethodSource("movieExampleSource")
    void findByExample(Example<Movie> movieExample) {
        var movies = movieRepository.findAll(movieExample);
        movies.forEach(System.out::println);
    }
}
