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

        userStorage.createUser(user);

        assertEquals(1, userStorage.findAllUsers().size());
    }

    @Test
    void shouldFindUserById() {
        User user = new User(LocalDate.of(2000, 10, 10), "user@mail.ru",
                "userLogin", "user");

        userStorage.createUser(user);

        User user1 = userStorage.findUserById(user.getId()).get();

        assertEquals(user, user1);
    }

    @Test
    void shouldCreateUser() {
        User user = new User(LocalDate.of(2000, 10, 10), "user@mail.ru",
                "userLogin", "user");

        assertEquals(0, userStorage.findAllUsers().size());

        userStorage.createUser(user);

        assertEquals(1, userStorage.findAllUsers().size());
    }

    @Test
    void shouldUpdateUser() {
        User user = new User(LocalDate.of(2000, 10, 10), "user@mail.ru",
                "userLogin", "user");

        userStorage.createUser(user);

        User user1 = userStorage.findUserById(user.getId()).get();

        user.setName("user1");
        userStorage.updateUser(user1);

        User user2 = userStorage.findUserById(user.getId()).get();

        assertEquals(user1, user2);
    }

    @Test
    void shouldDeleteUserById() {
        User user = new User(LocalDate.of(2000, 10, 10), "user@mail.ru",
                "userLogin", "user");

        userStorage.createUser(user);

        assertEquals(1, userStorage.findAllUsers().size());

        userStorage.deleteUserById(user.getId());

        assertEquals(0, userStorage.findAllUsers().size());
    }
}