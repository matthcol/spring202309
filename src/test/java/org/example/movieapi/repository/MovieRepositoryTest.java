package org.example.movieapi.repository;

import jakarta.persistence.EntityManager;
import org.example.movieapi.entity.Movie;
import org.example.movieapi.entity.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

// @Rollback(value = false)
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
@ActiveProfiles("testu")
class MovieRepositoryTest {

    @Autowired // DI
    MovieRepository movieRepository;

    @Autowired
    // EntityManager entityManager;
    TestEntityManager entityManager;

    // common data
    List<Person> persons;
    Movie movieOppenheimer;

    @BeforeEach
    void initData() {
        movieOppenheimer = Movie.builder()
                .title("Oppenheimer")
                .year((short) 2023)
                .build();
        persons = List.of(
                Person.builder()
                        .name("Christopher Nolan")
                        .build(),
                Person.builder()
                        .name("Cillian Murphy")
                        .build(),
                Person.builder()
                        .name("Emily Blunt")
                        .build(),
                Person.builder()
                        .name("Matt Damon")
                        .build()
        );
        entityManager.persist(movieOppenheimer);
        persons.forEach(entityManager::persist);
        entityManager.flush(); // SQL insert
        entityManager.clear(); // empty hibernate cache
    }

    @Test
    void testSave(){
        var movie = Movie.builder()
                .title("Barbie")
                .year((short) 2023)
                .build();
        movieRepository.saveAndFlush(movie);
        assertNotNull(movie.getId());
        System.out.println(movie);
    }

    // save 1 movie
    // save persons: directors + 3 actors
    // update movie to set director and actors

    @Test
    void testSetDirector(){
        var directorId = persons.get(0).getId();
        var movieId = movieOppenheimer.getId();
        // fetch movie and director from DB
        var director = entityManager.find(Person.class, directorId);
        var movie = entityManager.find(Movie.class, movieId);

        // update movie with its director
        movie.setDirector(director);
        movieRepository.flush(); // SQL: update

        // TODO: verify
    }

    @Test
    void testSetActors() {
        var movieId = movieOppenheimer.getId();
        var actorIds = persons.stream()
                .skip(1)
                .map(Person::getId)
                .toList();
        // fetch data from DB
        var movie = entityManager.find(Movie.class, movieId);
        var actors = actorIds.stream()
                .map(id -> entityManager.find(Person.class, id))
                .toList();

        // update movie with its actors
        movie.getActors().addAll(actors);
        movieRepository.flush(); // SQL: 'update' table play

        // TODO: verify
    }

    @Test
    void testReadOk() {
        var movieId = movieOppenheimer.getId();
        // read from DB
        var optMovie = movieRepository.findById(movieId);
        assertTrue(optMovie.isPresent(), "Movie with id " + movieId + " exists in DB");
        var movie = optMovie.get();
        assertAll(
                () -> assertEquals(movieId, movie.getId(), "movie id"),
                () -> assertEquals(movieOppenheimer.getTitle(), movie.getTitle(), "movie title")
        );
    }

    @Test
    void testReadKo() {
        var movieId = 0;
        // read from DB
        var optMovie = movieRepository.findById(movieId);
        assertTrue(optMovie.isEmpty(), "No movie with id 0");
    }

    @Test
    void testDelete() {

    }
}