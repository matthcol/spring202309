package org.example.movieapi.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
@ToString
public class PersonCreate {
    private String name;
    private LocalDate birthdate;
}
