package org.example.movieapi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

// Documentation:
// - Hibernate (JPA Provider):
// https://docs.jboss.org/hibernate/orm/6.3/userguide/html_single/Hibernate_User_Guide.html#domain-model

// lombok
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = {"director", "actors"}) // whitelist: of = {...}
// JPA
@Entity
@Table(name="movie") // customization
public class Movie {
    // strategies:
    // - SEQUENCE: fetch id before INSERT
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 300)
    private String title;

    // @Column(name = "release_year")
    private short year;

    // @Transient
    private Short duration;

    @ManyToOne
    // @JoinColumn(name="director_id", nullable = false)
    private Person director;

    // lombok
    @Builder.Default
    // JPA
    @ManyToMany
    @JoinTable(name = "play",
            joinColumns = @JoinColumn(name ="movie_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id")
    )
    private Set<Person> actors = new HashSet<>();
}
