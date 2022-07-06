package ru.yandex.practicum.controllers;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.exceptions.ValidationException;
import ru.yandex.practicum.models.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {
    UserController userController = new UserController();

    @Test
    public void shouldThrowValidationExceptionIfUserEmailIsEmpty() {
        User user = User.builder()
                .id(1)
                .email("")
                .birthday(LocalDate.of(2000, 10, 10))
                .name("user")
                .login("userLogin")
                .build();
        String message = null;

        try {
            userController.createUser(user);
        } catch (ValidationException validationException) {
            message = validationException.getMessage();
        }

        assertEquals("Электронная почта не может быть пустой и должна содержать символ @.", message);
        assertThrows(ValidationException.class, () -> userController.createUser(user));
        userController.getUsers().clear();
    }

    @Test
    public void shouldThrowValidationExceptionIfUserEmailNotContainsAtSign() {
        User user = User.builder()
                .id(1)
                .email("user.mail.ru")
                .birthday(LocalDate.of(2000, 10, 10))
                .name("user")
                .login("userLogin")
                .build();
        String message = null;

        try {
            userController.createUser(user);
        } catch (ValidationException validationException) {
            message = validationException.getMessage();
        }

        assertEquals("Электронная почта не может быть пустой и должна содержать символ @.", message);
        assertThrows(ValidationException.class, () -> userController.createUser(user));
        userController.getUsers().clear();
    }

    @Test
    public void shouldThrowValidationExceptionIfUserLoginIsEmpty() {
        User user = User.builder()
                .id(1)
                .email("user@mail.ru")
                .birthday(LocalDate.of(2000, 10, 10))
                .name("user")
                .login("")
                .build();
        String message = null;

        try {
            userController.createUser(user);
        } catch (ValidationException validationException) {
            message = validationException.getMessage();
        }

        assertEquals("Логин не может быть пустым и содержать пробелы.", message);
        assertThrows(ValidationException.class, () -> userController.createUser(user));
        userController.getUsers().clear();
    }

    @Test
    public void shouldThrowValidationExceptionIfUserLoginContainsWhitespaces() {
        User user = User.builder()
                .id(1)
                .email("user@mail.ru")
                .birthday(LocalDate.of(2000, 10, 10))
                .name("user")
                .login("user login")
                .build();
        String message = null;

        try {
            userController.createUser(user);
        } catch (ValidationException validationException) {
            message = validationException.getMessage();
        }

        assertEquals("Логин не может быть пустым и содержать пробелы.", message);
        assertThrows(ValidationException.class, () -> userController.createUser(user));
        userController.getUsers().clear();
    }

    @Test
    public void shouldSetUserNameIfEmpty() {
        User user = User.builder()
                .id(1)
                .email("user@mail.ru")
                .birthday(LocalDate.of(2000, 10, 10))
                .name("")
                .login("userLogin")
                .build();
        User newUser = userController.createUser(user);

        assertEquals("userLogin", newUser.getName());
        userController.getUsers().clear();
    }

    @Test
    public void shouldThrowValidationExceptionIfUserBirthdayIsAfterCurrentDate() {
        User user = User.builder()
                .id(1)
                .email("user@mail.ru")
                .birthday(LocalDate.of(2022, 12, 20))
                .name("user")
                .login("userLogin")
                .build();
        String message = null;

        try {
            userController.createUser(user);
        } catch (ValidationException validationException) {
            message = validationException.getMessage();
        }

        assertEquals("Дата рождения не может быть в будущем.", message);
        assertThrows(ValidationException.class, () -> userController.createUser(user));
        userController.getUsers().clear();
    }
}