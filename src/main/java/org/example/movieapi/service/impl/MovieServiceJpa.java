package org.example.movieapi.service.impl;

import org.example.movieapi.dto.MovieCreate;
import org.example.movieapi.dto.MovieDetail;
import org.example.movieapi.dto.MovieSimple;
import org.example.movieapi.entity.Movie;
import org.example.movieapi.repository.MovieRepository;
import org.example.movieapi.repository.PersonRepository;
import org.example.movieapi.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceJpa implements MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public MovieSimple add(MovieCreate movie) {
        var movieEntity = modelMapper.map(movie, Movie.class);
        movieRepository.saveAndFlush(movieEntity);
        return modelMapper.map(movieEntity, MovieSimple.class);
    }

    @Override
    public Optional<MovieDetail> getById(int id) {
        return movieRepository.findById(id)
                .map(movieEntity -> modelMapper.map(movieEntity, MovieDetail.class));
    }

    @Override
    public List<MovieSimple> getByTitle() {
        return null;
    }

    // @Transactional() // ReadOnly
    @Override
    public List<MovieSimple> getTopMovies() {
        int actualYear = LocalDate.now().getYear();
        return movieRepository.findByYearBetween((short) (actualYear - 10), (short) actualYear, Sort.by("year"))
                .stream()
                .map(movieEntity -> modelMapper.map(movieEntity, MovieSimple.class))
                .toList();
    }

    @Override
    public Optional<MovieDetail> update(int movieId, MovieCreate movieCreate) {
        // TODO
        return Optional.empty();
    }

    @Override
    public Optional<MovieDetail> setDirector(int movieId, int directorId) {
        return movieRepository.findById(movieId)
                .flatMap(movieEntity -> personRepository.findById(directorId)
                        .map(directorEntity -> {
                            movieEntity.setDirector(directorEntity);
                            movieRepository.flush();
                            return modelMapper.map(movieEntity, MovieDetail.class);
                        })
                );
    }

    @Override
    public Optional<MovieDetail> setActors(int movieId, Iterable<Integer> actorIds) {
        // TODO
        return Optional.empty();
    }
}
