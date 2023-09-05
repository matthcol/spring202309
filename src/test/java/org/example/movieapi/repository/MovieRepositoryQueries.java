package org.example.movieapi.repository;

import org.example.movieapi.entity.Movie;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.text.MessageFormat;
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

    @Test
    void findByTitle() {
        movieRepository.findByTitleContainingIgnoreCase("star")
                .forEach(System.out::println);
    }

    @Test
    void findByYearBetween(){
        movieRepository.findByYearBetween((short) 1982, (short) 1984, Sort.by("year", "title"))
                .forEach(System.out::println);
    }

    @ParameterizedTest
    @ValueSource(strings={"eastwood", "tarantino", "steve mcqueen"})
    void findByDirector(String partName){
        movieRepository.findByDirectorNameEndingWithIgnoreCaseOrderByYearDesc(partName)
                .forEach(System.out::println);
    }

    @ParameterizedTest
    @ValueSource(strings={"eastwood", "tarantino", "steve mcqueen"})
    void findByActorsNameEndingWithIgnoreCaseOrderByYear(String partName){
        movieRepository.findByActorsNameEndingWithIgnoreCaseOrderByYear(partName)
                .forEach(System.out::println);
    }

    @ParameterizedTest
    @ValueSource(strings={"% eastwood", "% tarantino", "steve mcqueen"})
    void findByActor(String namePattern){
        movieRepository.findByActor(namePattern)
                .forEach(System.out::println);
    }

    @Test
    void findByTitleYear(){
        String title = "The Man Who Knew Too Much";
        short year = 1956;
        movieRepository.findByTitleYear(title, year)
                .ifPresentOrElse(
                        movie -> {
                            System.out.println(movie);
                            System.out.println(movie.getDirector());
                            System.out.println(movie.getActors());
                        },
                        () -> System.out.println("No Data")
                );
    }

    @Test
    void statsByYear(){
        movieRepository.statsByYear((short) 1980, (short) 1989)
                .forEach(stats -> System.out.println(MessageFormat.format(
                        "{0}: {1} movies, {2} minutes",
                        stats.getYear(),
                        stats.getCountMovie(),
                        stats.getTotalDuration()
                )));
    }



}
