package ru.yandex.practicum.models;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class User {
    private final LocalDate birthday;
    private final Set<Long> friends = new HashSet<>();
    private long id;
    private String email;
    private String login;
    private String name;

    public User(LocalDate birthday, String email, String login, String name) {
        this.birthday = birthday;
        this.email = email;
        this.login = login;
        this.name = name;
    }
}