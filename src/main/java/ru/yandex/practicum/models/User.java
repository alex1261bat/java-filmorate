package ru.yandex.practicum.models;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class User {
    @NotNull
    @PastOrPresent
    private LocalDate birthday;
    private final Set<Long> friends = new HashSet<>();
    private long id;
    @Email
    private String email;
    @NotBlank
    private String login;
    private String name;

    public User(long id, String name, String login, String email, LocalDate birthday) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.email = email;
        this.birthday = birthday;
    }
}