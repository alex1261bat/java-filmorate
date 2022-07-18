package ru.yandex.practicum.models;

import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class Film {
    @Positive
    private final int duration;
    @NotBlank
    private final String name;
    @NotNull
    private final LocalDate releaseDate;
    private final Set<Long> likes = new HashSet<>();
    private long id;
    @NotBlank
    @Size(max = 200)
    private String description;

    public Film(int duration, String name, LocalDate releaseDate, String description) {
        this.duration = duration;
        this.name = name;
        this.releaseDate = releaseDate;
        this.description = description;
    }
}