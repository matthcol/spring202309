package org.example.movieapi.controller;

import org.example.movieapi.entity.Movie;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.Objects;

@RestController
@RequestMapping("/api/dummy")
public class DummyController {

    @GetMapping
    public String coucou(){
        return "Coucou";
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,  // default
            produces = MediaType.APPLICATION_JSON_VALUE   // default
    )
    public Movie add(@RequestBody Movie movie){
        movie.setId(12345);
        return movie;
    }

    @GetMapping("movie")
    public Movie movie(){
        return Movie.builder()
                .title("Barbie")
                .year((short) 2023)
                .build();
    }

    @GetMapping("movie/{id}")
    public Movie movieById(@PathVariable("id") int id){
        return Movie.builder()
                .id(id)
                .title("Barbie")
                .year((short) 2023)
                .build();
    }

    @GetMapping("movie/search")
    public Movie movieSearch(
            @RequestParam(name = "t", required = false) String title,
            @RequestParam(name = "y", required = false, defaultValue = "-1") Short year)
    {
        return Movie.builder()
                .title(title)
                .year(year)
                .build();
    }

}
