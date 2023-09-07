package org.example.movieapi.controller;

import org.example.movieapi.dto.MovieDetail;
import org.example.movieapi.entity.Movie;
import org.example.movieapi.service.MovieService;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(controllers = MovieController.class)
@WithMockUser
class MovieControllerTest {

    final static String BASE_URL = "/api/movie";

    // HTTP requests
    @Autowired
    MockMvc mockMvc;

    @MockBean
    MovieService movieService;

    @Test
    void getById_whenFound() throws Exception {
        int id = 123;
        String title = "Barbie";
        short year = 2023;
        // prepare mock service response
        var optMovie = Optional.<MovieDetail>of(
                MovieDetail.builder()
                        .id(id)
                        .title(title)
                        .year(year)
                        // add duration, actors, director
                        .build()
        );
        BDDMockito.given(movieService.getById(id))
                .willReturn(optMovie);
        // call controller
        String url = BASE_URL + "/{id}";
        mockMvc.perform(MockMvcRequestBuilders.get(url, id)
                .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(MockMvcResultHandlers.print())
        // verify response
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id));
                // verify other properties

        // verify mock service has been called
        BDDMockito.then(movieService)
                .should()
                .getById(id);
    }

    @Test
    void getById_whenNotFound() throws Exception {
        int id = 321;
        var optMovie = Optional.<MovieDetail>empty();
        BDDMockito.given(movieService.getById(id))
                .willReturn(optMovie);
        // call controller with mockMvc
        String url = BASE_URL + "/{id}";
        mockMvc.perform(MockMvcRequestBuilders.get(url, id)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                // verify response
                .andExpect(MockMvcResultMatchers.status().isNotFound());
                // TODO: verify json error messaqe
        // verify mock service has been called
        BDDMockito.then(movieService)
                .should()
                .getById(id);
    }

    // for tests with post, put, patch, delete, add crsf handling
    //         mockMvc.perform(post(BASE_URL)
    //                        .with(csrf())
    //                        .accept(MediaType.APPLICATION_JSON)
    //                        .contentType(MediaType.APPLICATION_JSON)
    //                        .content(JSON_CONTENT)
//                )

}