package org.example.movieapi.repository;

import org.example.movieapi.entity.Movie;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public interface MovieRepository extends JpaRepository<Movie, Integer> {

    // Spring vocabulary for method with query auto
    //  https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation
    // Results can be: Set, List, Stream, Optional
    Set<Movie> findByTitleContainingIgnoreCase(String title);

    // by year between
    Stream<Movie> findByYearBetween(short year1, short year2, Sort sort);

    // filmography as an actor
    List<Movie> findByActorsNameEndingWithIgnoreCaseOrderByYear(String partName);

    // filmography as a director
    List<Movie> findByDirectorNameEndingWithIgnoreCaseOrderByYearDesc(String partName);

}
