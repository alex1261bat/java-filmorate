package ru.yandex.practicum.controllers;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.exceptions.ValidationException;
import ru.yandex.practicum.models.User;
import ru.yandex.practicum.services.UserService;
import ru.yandex.practicum.storage.user.InMemoryUserStorage;
import ru.yandex.practicum.storage.user.UserStorage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserControllerTest {
    UserStorage userStorage = new InMemoryUserStorage();
    UserService userService = new UserService(userStorage);
    UserController userController = new UserController(userService);

    @Test
    public void shouldThrowValidationExceptionIfUserLoginContainsWhitespaces() {
        User user = new User(LocalDate.of(2000, 10, 10), "user@mail.ru",
                "user login", "user");
        String message = null;

        try {
            userController.createUser(user);
        } catch (ValidationException validationException) {
            message = validationException.getMessage();
        }

        assertEquals("Логин не может содержать пробелы.", message);
        assertThrows(ValidationException.class, () -> userController.createUser(user));
    }

    @Test
    public void shouldSetUserNameIfEmpty() {
        User user = new User(LocalDate.of(2000, 10, 10), "user@mail.ru",
                "userLogin", "");
        User newUser = userController.createUser(user);

        assertEquals("userLogin", newUser.getName());
    }


    @Test
    public void shouldSetUserNameIfNull() {
        User user = new User(LocalDate.of(2000, 10, 10), "user@mail.ru",
                "userLogin", null);
        User newUser = userController.createUser(user);

        assertEquals("userLogin", newUser.getName());
    }
}