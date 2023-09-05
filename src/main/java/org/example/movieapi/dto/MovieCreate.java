package org.example.movieapi.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
@ToString
public class MovieCreate {
    private String title;
    private short year;
    private Short duration;
}
