package org.example.movieapi.aspect;

import org.aspectj.lang.annotation.*;
import org.example.movieapi.dto.MovieCreate;
import org.example.movieapi.dto.MovieSimple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class MovieAddAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(MovieAddAspect.class);

    @Before(
            value = "execution(* org.example.movieapi.service.impl.MovieServiceJpa.add(org.example.movieapi.dto.MovieCreate))" +
                    " && args(movie)"
    )
    public void beforeMovieAdded(MovieCreate movie) {
        LOGGER.debug("Movie about to be added: {}", movie);
    }

    @After("execution(* org.example.movieapi.service.impl.MovieServiceJpa.add(..))")
    public void afterMovieAdded(){
        LOGGER.debug("Movie added has been called");
    }

    @AfterReturning(
            pointcut = "execution(* org.example.movieapi.service.impl.MovieServiceJpa.add(..))",
            returning = "movieAdded"
    )
    public void afterReturningMovieAdded(MovieSimple movieAdded){
        LOGGER.info("Movie added: {}", movieAdded);
    }

    @AfterThrowing(
            pointcut = "execution(* org.example.movieapi.service.impl.MovieServiceJpa.add(..))",
            throwing = "error"
    )
    public void afterReturningMovieAdded(Exception error){
        LOGGER.error("Movie add failed: {}", error.getMessage());
    }
}
