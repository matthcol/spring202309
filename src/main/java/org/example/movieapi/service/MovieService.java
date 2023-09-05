package org.example.movieapi.service;

import org.example.movieapi.dto.MovieCreate;
import org.example.movieapi.dto.MovieDetail;
import org.example.movieapi.dto.MovieSimple;

import java.util.List;
import java.util.Optional;

public interface MovieService {

    MovieSimple add(MovieCreate movie);

    Optional<MovieDetail> getById(int id);

    List<MovieSimple> getByTitle();

    // add: update, setDirector, setActors, delete, stats and other queries
}
