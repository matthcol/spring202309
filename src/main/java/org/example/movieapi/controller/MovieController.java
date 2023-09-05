package org.example.movieapi.controller;

import org.example.movieapi.dto.MovieDetail;
import org.example.movieapi.dto.MovieSimple;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/movie")
public class MovieController {

    @GetMapping("{id}")
    public MovieDetail getById(@PathVariable("id") int id){
        // TODO
        return null;
    }
}
