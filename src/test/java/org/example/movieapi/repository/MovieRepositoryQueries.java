package org.example.movieapi.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

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
    }
}
