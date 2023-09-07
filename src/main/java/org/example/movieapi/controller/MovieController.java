package org.example.movieapi.controller;

import jakarta.validation.Valid;
import org.example.movieapi.dto.MovieCreate;
import org.example.movieapi.dto.MovieDetail;
import org.example.movieapi.dto.MovieSimple;
import org.example.movieapi.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.text.MessageFormat;
import java.util.List;

@RestController
@RequestMapping("/api/movie")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping
    public List<MovieSimple> getTopMovies(){
        return movieService.getTopMovies();
    }

    @GetMapping("{id}")
    public MovieDetail getById(@PathVariable("id") int id){
        return movieService.getById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        MessageFormat.format("Movie not found with id <{0}>", id)
                ));
    }

    @PostMapping
    public MovieSimple add(@Valid @RequestBody MovieCreate movieCreate){
        return movieService.add(movieCreate);
    }

    @PatchMapping("{movieId}/director/{directorId}")
    public MovieDetail setDirector(@PathVariable int movieId, @PathVariable int directorId){
        return movieService.setDirector(movieId, directorId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        MessageFormat.format("Movie with id <{0}> or person with id <{1}> not found",
                                movieId, directorId)
                ));
    }
}
