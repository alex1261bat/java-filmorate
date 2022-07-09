package ru.yandex.practicum.models;

import lombok.*;

import java.time.LocalDate;

@Data
public class Film {
    private final int duration;
    private final String name;
    private final LocalDate releaseDate;
    private long id;
    private String description;

    public Film(int duration, String name, LocalDate releaseDate, String description) {
        this.duration = duration;
        this.name = name;
        this.releaseDate = releaseDate;
        setId(++id);
        this.description = description;
    }
}