package ru.yandex.practicum.models;

import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class User {
    @NotNull
    @Past
    private final LocalDate birthday;
    private final Set<Long> friends = new HashSet<>();
    private long id;
    @Email
    private String email;
    @NotBlank
    private String login;
    private String name;

    public User(LocalDate birthday, String email, String login, String name) {
        this.birthday = birthday;
        this.email = email;
        this.login = login;
        this.name = name;
    }
}