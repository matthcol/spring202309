package org.example.movieapi.repository;

import org.example.movieapi.entity.Movie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
@ActiveProfiles("testu")
class MovieRepositoryTest {

    @Autowired // DI
    MovieRepository movieRepository;

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

}