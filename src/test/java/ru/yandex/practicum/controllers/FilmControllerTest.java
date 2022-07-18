package ru.yandex.practicum.controllers;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.exceptions.ValidationException;
import ru.yandex.practicum.models.Film;
import ru.yandex.practicum.services.FilmService;
import ru.yandex.practicum.storage.film.FilmStorage;
import ru.yandex.practicum.storage.film.InMemoryFilmStorage;
import ru.yandex.practicum.storage.user.InMemoryUserStorage;
import ru.yandex.practicum.storage.user.UserStorage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FilmControllerTest {
    FilmStorage filmStorage = new InMemoryFilmStorage();
    UserStorage userStorage = new InMemoryUserStorage();
    FilmService filmService = new FilmService(filmStorage, userStorage);
    FilmController filmController = new FilmController(filmService);

    @Test
    void shouldThrowValidationExceptionIfFilmReleaseDateIsBefore1895_12_28() {
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
}