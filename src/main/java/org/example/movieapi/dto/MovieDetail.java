package org.example.movieapi.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
@ToString(callSuper = true)
public class MovieDetail extends MovieSimple {
    private PersonSimple director;
    private Set<PersonSimple> actors;
}
