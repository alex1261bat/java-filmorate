package ru.yandex.practicum.models;

import lombok.Data;

import java.time.LocalDate;

@Data
public class User {
    private final LocalDate birthday;
    private int id;
    private String email;
    private String login;
    private String name;

    public User(LocalDate birthday, String email, String login, String name) {
        this.birthday = birthday;
        setId();
        this.email = email;
        this.login = login;
        this.name = name;
    }

    private void setId() {
        ++id;
    }
}