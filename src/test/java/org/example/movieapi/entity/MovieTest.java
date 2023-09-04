package org.example.movieapi.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MovieTest {

    @Test
    void testGetSetTitle(){
        String titleExpected = "Barbie";
        var movie = new Movie();
        movie.setTitle(titleExpected);
        var titleRead = movie.getTitle();
        assertEquals(titleExpected, titleRead);
    }

    @Test
    void testNoArgConstructor(){
        var movie = new Movie();
        assertAll(
                () -> assertNull(movie.getTitle()),
                () -> assertEquals((short) 0, movie.getYear()),
                () -> assertNull(movie.getDuration())
        );
    }

    @Test
    void testBuilder(){
        var movie = Movie.builder()
                .title("Barbie")
                .year((short) 2023)
                .build();
        // TODO: test all combinations and check
        assertNotNull(movie.getActors());
    }

}