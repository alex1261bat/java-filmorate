package ru.yandex.practicum.models;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class User {
    private final int id;
    private final LocalDate birthday;
    private String email;
    private String login;
    private String name;
}