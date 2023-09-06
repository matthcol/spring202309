package org.example.movieapi.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
@ToString
public class MovieCreate {
    @NotBlank
    @Size(max = 300)
    private String title;

    @Min(1888)
    private short year;

    private Short duration;
}
