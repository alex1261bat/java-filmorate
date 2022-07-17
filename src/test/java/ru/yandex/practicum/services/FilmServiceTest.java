package ru.yandex.practicum.services;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.exceptions.FilmNotFoundException;
import ru.yandex.practicum.models.Film;
import ru.yandex.practicum.models.User;
import ru.yandex.practicum.storage.film.FilmStorage;
import ru.yandex.practicum.storage.film.InMemoryFilmStorage;
import ru.yandex.practicum.storage.user.InMemoryUserStorage;
import ru.yandex.practicum.storage.user.UserStorage;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FilmServiceTest {
    UserStorage userStorage = new InMemoryUserStorage();
    FilmStorage filmStorage = new InMemoryFilmStorage();
    FilmService filmService = new FilmService(filmStorage, userStorage);

    @Test
    void shouldThrowFilmNotFoundExceptionIfNoSuchFilm() {
        String message = null;

        try {
            filmService.findFilmById(0);
        } catch (FilmNotFoundException filmNotFoundException) {
            message = filmNotFoundException.getMessage();
        }

        assertEquals("Фильм с id=" + 0 + " не существует.", message);
        assertThrows(FilmNotFoundException.class, () -> filmService.findFilmById(0));
    }

    @Test
    void shouldAddLike() {
        Film film = new Film(10, "Film", LocalDate.of(2012, 10, 23),
                "description");
        User user = new User(LocalDate.of(2000, 10, 10), "user@mail.ru",
                "userLogin", "user");

        filmStorage.createFilm(film);
        userStorage.createUser(user);

        assertEquals(0, filmStorage.findFilmById(film.getId())
                .orElseThrow(() -> new FilmNotFoundException(film.getId())).getLikes().size());

        filmService.addLike(film.getId(), user.getId());

        assertEquals(1, filmStorage.findFilmById(film.getId())
                .orElseThrow(() -> new FilmNotFoundException(film.getId())).getLikes().size());
    }

    @Test
    void shouldDeleteLike() {
        Film film = new Film(10, "Film", LocalDate.of(2012, 10, 23),
                "description");
        User user = new User(LocalDate.of(2000, 10, 10), "user@mail.ru",
                "userLogin", "user");

        filmStorage.createFilm(film);
        userStorage.createUser(user);

        assertEquals(0, filmStorage.findFilmById(film.getId())
                .orElseThrow(() -> new FilmNotFoundException(film.getId())).getLikes().size());

        filmService.addLike(film.getId(), user.getId());

        assertEquals(1, filmStorage.findFilmById(film.getId())
                .orElseThrow(() -> new FilmNotFoundException(film.getId())).getLikes().size());

        filmService.deleteLike(film.getId(), user.getId());

        assertEquals(0, filmStorage.findFilmById(film.getId())
                .orElseThrow(() -> new FilmNotFoundException(film.getId())).getLikes().size());
    }

    @Test
    void shouldFindBestFilms() {
        Film film = new Film(10, "Film", LocalDate.of(2012, 10, 23),
                "description");
        Film film1 = new Film(100, "Film1", LocalDate.of(2011, 1, 22),
                "description1");
        Film film2 = new Film(1, "Film2", LocalDate.of(2013, 11, 21),
                "description2");
        User user = new User(LocalDate.of(2000, 10, 10), "user@mail.ru",
                "userLogin", "user");
        User user1 = new User(LocalDate.of(2001, 1, 1), "user1@mail.ru",
                "user1Login", "user1");

        filmStorage.createFilm(film);
        filmStorage.createFilm(film1);
        filmStorage.createFilm(film2);
        userStorage.createUser(user);
        userStorage.createUser(user1);
        filmService.addLike(film.getId(), user.getId());
        filmService.addLike(film.getId(), user1.getId());
        filmService.addLike(film1.getId(), user.getId());

        List<Film> bestFilms = filmService.findBestFilms(3);

        assertEquals(film, bestFilms.get(0));
        assertEquals(film1, bestFilms.get(1));
        assertEquals(film2, bestFilms.get(2));
    }
}