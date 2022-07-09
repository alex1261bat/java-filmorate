package ru.yandex.practicum.controllers;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.exceptions.ValidationException;
import ru.yandex.practicum.models.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FilmControllerTest {
    FilmController filmController = new FilmController();

    @Test
    public void shouldThrowValidationExceptionIfFilmNameIsEmpty() {
        Film film = new Film(10, "", LocalDate.of(2012, 10, 23),
                "description");
        String message = null;

        try {
            filmController.createFilm(film);
        } catch (ValidationException validationException) {
            message = validationException.getMessage();
        }

        assertEquals("Название фильма не может быть пустым.", message);
        assertThrows(ValidationException.class, () -> filmController.createFilm(film));
    }

    @Test
    public void shouldThrowValidationExceptionIfFilmDescriptionLengthMore200() {
        Film film = new Film(10, "film", LocalDate.of(2012, 10, 23),
                "fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff" +
                        "ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff" +
                        "ffffffffffffffffffffff");
        String message = null;

        try {
            filmController.createFilm(film);
        } catch (ValidationException validationException) {
            message = validationException.getMessage();
        }

        assertEquals("Длина описания должна быть не боле 200 символов и не должна быть null.", message);
        assertThrows(ValidationException.class, () -> filmController.createFilm(film));
    }

    @Test
    public void shouldThrowValidationExceptionIfFilmReleaseDateIsBefore1895_12_28() {
        Film film = new Film(10, "film", LocalDate.of(1895, 12, 27),
                "filmDescription");
        String message = null;

        try {
            filmController.createFilm(film);
        } catch (ValidationException validationException) {
            message = validationException.getMessage();
        }

        assertEquals("Дата релиза должна быть не ранее 28 декабря 1985 года.", message);
        assertThrows(ValidationException.class, () -> filmController.createFilm(film));
    }

    @Test
    public void shouldThrowValidationExceptionIfFilmDurationIsLess1() {
        Film film = new Film(0, "film", LocalDate.of(2012, 12, 27),
                "filmDescription");
        String message = null;

        try {
            filmController.createFilm(film);
        } catch (ValidationException validationException) {
            message = validationException.getMessage();
        }

        assertEquals("Продолжительность фильма должна быть положительной.", message);
        assertThrows(ValidationException.class, () -> filmController.createFilm(film));
    }

    @Test
    public void shouldThrowValidationExceptionIfFilmNameIsNull() {
        Film film = new Film(10, null, LocalDate.of(2012, 10, 23),
                "description");
        String message = null;

        try {
            filmController.createFilm(film);
        } catch (ValidationException validationException) {
            message = validationException.getMessage();
        }

        assertEquals("Название фильма не может быть пустым.", message);
        assertThrows(ValidationException.class, () -> filmController.createFilm(film));
    }

    @Test
    public void shouldThrowValidationExceptionIfFilmDescriptionIsNull() {
        Film film = new Film(10, "film", LocalDate.of(2012, 10, 23),
                null);
        String message = null;

        try {
            filmController.createFilm(film);
        } catch (ValidationException validationException) {
            message = validationException.getMessage();
        }

        assertEquals("Длина описания должна быть не боле 200 символов и не должна быть null.", message);
        assertThrows(ValidationException.class, () -> filmController.createFilm(film));
    }
}