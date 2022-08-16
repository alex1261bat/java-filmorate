package ru.yandex.practicum.models;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class Film {
    @Positive
    private int duration;
    @NotBlank
    private String name;
    @NotNull
    private LocalDate releaseDate;
    private final Set<Long> likes = new HashSet<>();
    private final Set<Genre> genres = new HashSet<>();
    @NotNull
    private RatingMPA mpa;
    private long id;
    @NotBlank
    @Size(max = 200)
    private String description;

    public Film(long id, String name, String description, int duration, LocalDate releaseDate, RatingMPA mpa) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.releaseDate = releaseDate;
        this.mpa = mpa;
    }
}