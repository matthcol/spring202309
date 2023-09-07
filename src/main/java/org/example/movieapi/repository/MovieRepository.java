package org.example.movieapi.repository;

import org.example.movieapi.dto.StatMovieYear;
import org.example.movieapi.entity.Movie;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

public interface MovieRepository extends JpaRepository<Movie, Integer> {

    // Spring vocabulary for method with query auto
    //  https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation
    // Results can be: Set, List, Stream, Optional
    Set<Movie> findByTitleContainingIgnoreCase(String title);

    // by year between
    List<Movie> findByYearBetween(short year1, short year2, Sort sort);

    // filmography as an actor
    List<Movie> findByActorsNameEndingWithIgnoreCaseOrderByYear(String partName);

    // filmography as a director
    List<Movie> findByDirectorNameEndingWithIgnoreCaseOrderByYearDesc(String partName);

    @Query("SELECT m FROM Movie m JOIN m.actors a WHERE lower(a.name) like :actorName ORDER BY m.year DESC")
    List<Movie> findByActor(String actorName);


    @Query("SELECT m FROM Movie m LEFT JOIN FETCH m.director LEFT JOIN FETCH m.actors " +
            "WHERE m.title = :title and m.year = :year")
    Optional<Movie> findByTitleYear(String title, short year);

    // DTO:
    // - JPA: Object[], Tuple,
    // - JPA custom DTO class or record => new org.example.movieapi.dto.StatMovieYear(year, COUNT(m), SUM(m.duration)
    // - Spring: custom DTO as an interface
    @Query("SELECT " +
            "   year as year, " +
            "   COUNT(m) as countMovie, " +
            "   COALESCE(SUM(m.duration), 0) as totalDuration " +
            "FROM Movie m " +
            "WHERE m.year BETWEEN :year1 AND :year2 " +
            "GROUP BY m.year")
    List<StatMovieYear> statsByYear(short year1, short year2);

    // for other queries (entitygraph, api criteria) see repository
    // https://github.com/matthcol/spring202210
}
