package ru.yandex.practicum.models;

import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class Film {
    private final int duration;
    private final String name;
    private final LocalDate releaseDate;
    private final Set<Long> likes = new HashSet<>();
    private long id;
    private String description;

    public Film(int duration, String name, LocalDate releaseDate, String description) {
        this.duration = duration;
        this.name = name;
        this.releaseDate = releaseDate;
        this.description = description;
    }
}