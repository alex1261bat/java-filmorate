package ru.yandex.practicum.storage.user;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.models.Film;
import ru.yandex.practicum.models.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryUserStorageTest {
    UserStorage userStorage = new InMemoryUserStorage();

    @Test
    void shouldFindAllUsers() {
        assertEquals(0, userStorage.findAllUsers().size());

        User user = new User(LocalDate.of(2000, 10, 10), "user@mail.ru",
                "userLogin", "user");

        user.setId(1);
        userStorage.createUser(user);

        assertEquals(1, userStorage.findAllUsers().size());
    }

    @Test
    void shouldFindUserById() {
        User user = new User(LocalDate.of(2000, 10, 10), "user@mail.ru",
                "userLogin", "user");

        user.setId(1);
        userStorage.createUser(user);

        User user1 = userStorage.findUserById(user.getId()).get();

        assertEquals(user, user1);
    }

    @Test
    void shouldCreateUser() {
        User user = new User(LocalDate.of(2000, 10, 10), "user@mail.ru",
                "userLogin", "user");

        user.setId(1);

        assertEquals(0, userStorage.findAllUsers().size());

        userStorage.createUser(user);

        assertEquals(1, userStorage.findAllUsers().size());
    }

    @Test
    void shouldUpdateUser() {
        User user = new User(LocalDate.of(2000, 10, 10), "user@mail.ru",
                "userLogin", "user");
        User user1 = new User(LocalDate.of(2002, 1, 10), "user1@mail.ru",
                "user1Login", "user1");

        user.setId(1);
        user1.setId(1);
        userStorage.createUser(user);
        userStorage.updateUser(user1);

        User user2 = userStorage.findUserById(user.getId()).get();

        assertEquals(user1, user2);
    }

    @Test
    void shouldDeleteUserById() {
        User user = new User(LocalDate.of(2000, 10, 10), "user@mail.ru",
                "userLogin", "user");

        user.setId(1);
        userStorage.createUser(user);

        assertEquals(1, userStorage.findAllUsers().size());

        userStorage.deleteUserById(user.getId());

        assertEquals(0, userStorage.findAllUsers().size());
    }
}