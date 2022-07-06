package ru.yandex.practicum.models;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class Film {
    private final int id;
    private final int duration;
    private final String name;
    private final LocalDate releaseDate;
    private String description;
}