package ru.yandex.practicum.storage.user;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.exceptions.ValidationException;
import ru.yandex.practicum.models.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryUserStorageTest {
    InMemoryUserStorage inMemoryUserStorage = new InMemoryUserStorage();

    @Test
    public void shouldThrowValidationExceptionIfUserEmailIsEmpty() {
        User user = new User(LocalDate.of(2000, 10, 10), "",
                "userLogin", "user");
        String message = null;

        try {
            inMemoryUserStorage.createUser(user);
        } catch (ValidationException validationException) {
            message = validationException.getMessage();
        }

        assertEquals("Электронная почта не может быть пустой и должна содержать символ @.", message);
        assertThrows(ValidationException.class, () -> inMemoryUserStorage.createUser(user));
    }

    @Test
    public void shouldThrowValidationExceptionIfUserEmailNotContainsAtSign() {
        User user = new User(LocalDate.of(2000, 10, 10), "user.mail.ru",
                "userLogin", "user");
        String message = null;

        try {
            inMemoryUserStorage.createUser(user);
        } catch (ValidationException validationException) {
            message = validationException.getMessage();
        }

        assertEquals("Электронная почта не может быть пустой и должна содержать символ @.", message);
        assertThrows(ValidationException.class, () -> inMemoryUserStorage.createUser(user));
    }

    @Test
    public void shouldThrowValidationExceptionIfUserLoginIsEmpty() {
        User user = new User(LocalDate.of(2000, 10, 10), "user@mail.ru",
                "", "user");
        String message = null;

        try {
            inMemoryUserStorage.createUser(user);
        } catch (ValidationException validationException) {
            message = validationException.getMessage();
        }

        assertEquals("Логин не может быть пустым и содержать пробелы.", message);
        assertThrows(ValidationException.class, () -> inMemoryUserStorage.createUser(user));
    }

    @Test
    public void shouldThrowValidationExceptionIfUserLoginContainsWhitespaces() {
        User user = new User(LocalDate.of(2000, 10, 10), "user@mail.ru",
                "user login", "user");
        String message = null;

        try {
            inMemoryUserStorage.createUser(user);
        } catch (ValidationException validationException) {
            message = validationException.getMessage();
        }

        assertEquals("Логин не может быть пустым и содержать пробелы.", message);
        assertThrows(ValidationException.class, () -> inMemoryUserStorage.createUser(user));
    }

    @Test
    public void shouldSetUserNameIfEmpty() {
        User user = new User(LocalDate.of(2000, 10, 10), "user@mail.ru",
                "userLogin", "");
        User newUser = inMemoryUserStorage.createUser(user);

        assertEquals("userLogin", newUser.getName());
    }

    @Test
    public void shouldThrowValidationExceptionIfUserBirthdayIsAfterCurrentDate() {
        User user = new User(LocalDate.of(2022, 12, 20), "user@mail.ru",
                "userLogin", "user");
        String message = null;

        try {
            inMemoryUserStorage.createUser(user);
        } catch (ValidationException validationException) {
            message = validationException.getMessage();
        }

        assertEquals("Дата рождения не может быть в будущем.", message);
        assertThrows(ValidationException.class, () -> inMemoryUserStorage.createUser(user));
    }

    @Test
    public void shouldThrowValidationExceptionIfUserEmailIsNull() {
        User user = new User(LocalDate.of(2000, 10, 10), null,
                "userLogin", "user");
        String message = null;

        try {
            inMemoryUserStorage.createUser(user);
        } catch (ValidationException validationException) {
            message = validationException.getMessage();
        }

        assertEquals("Электронная почта не может быть пустой и должна содержать символ @.", message);
        assertThrows(ValidationException.class, () -> inMemoryUserStorage.createUser(user));
    }

    @Test
    public void shouldThrowValidationExceptionIfUserLoginIsNull() {
        User user = new User(LocalDate.of(2000, 10, 10), "user@mail.ru",
                null, "user");
        String message = null;

        try {
            inMemoryUserStorage.createUser(user);
        } catch (ValidationException validationException) {
            message = validationException.getMessage();
        }

        assertEquals("Логин не может быть пустым и содержать пробелы.", message);
        assertThrows(ValidationException.class, () -> inMemoryUserStorage.createUser(user));
    }

    @Test
    public void shouldThrowValidationExceptionIfUserNameIsNull() {
        User user = new User(LocalDate.of(2000, 10, 10), "user@mail.ru",
                "userLogin", null);
        User newUser = inMemoryUserStorage.createUser(user);

        assertEquals("userLogin", newUser.getName());
    }
}