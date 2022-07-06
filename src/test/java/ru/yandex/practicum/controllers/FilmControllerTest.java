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
        Film film = Film.builder()
                .id(1)
                .name("")
                .description("film")
                .duration(10)
                .releaseDate(LocalDate.of(2012, 10, 23))
                .build();
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
        Film film = Film.builder()
                .id(1)
                .name("film")
                .description("fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff" +
                        "ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff" +
                        "ffffffffffffffffffffff")
                .duration(10)
                .releaseDate(LocalDate.of(2012, 10, 23))
                .build();

        String message = null;

        try {
            filmController.createFilm(film);
        } catch (ValidationException validationException) {
            message = validationException.getMessage();
        }

        assertEquals("Длина описания должна быть не боле 200 символов.", message);
        assertThrows(ValidationException.class, () -> filmController.createFilm(film));
    }

    @Test
    public void shouldThrowValidationExceptionIfFilmReleaseDateIsBefore1895_12_28() {
        Film film = Film.builder()
                .id(1)
                .name("film")
                .description("filmDescription")
                .duration(10)
                .releaseDate(LocalDate.of(1985, 12, 27))
                .build();
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
        Film film = Film.builder()
                .id(1)
                .name("film")
                .description("filmDescription")
                .duration(0)
                .releaseDate(LocalDate.of(2012, 12, 27))
                .build();
        String message = null;

        try {
            filmController.createFilm(film);
        } catch (ValidationException validationException) {
            message = validationException.getMessage();
        }

        assertEquals("Продолжительность фильма должна быть положительной.", message);
        assertThrows(ValidationException.class, () -> filmController.createFilm(film));
    }
}