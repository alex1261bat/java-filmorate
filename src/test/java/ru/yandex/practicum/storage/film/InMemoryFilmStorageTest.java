package ru.yandex.practicum.storage.film;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.models.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryFilmStorageTest {
    FilmStorage filmStorage = new InMemoryFilmStorage();

    @Test
    void shouldFindAllFilms() {
        assertEquals(0, filmStorage.findAllFilms().size());

        Film film = new Film(10, "Film", LocalDate.of(2012, 10, 23),
                "description");

        filmStorage.createFilm(film);

        assertEquals(1, filmStorage.findAllFilms().size());
    }

    @Test
    void shouldFindFilmById() {
        Film film = new Film(10, "Film", LocalDate.of(2012, 10, 23),
                "description");

        filmStorage.createFilm(film);

        Film film1 = filmStorage.findFilmById(film.getId()).get();

        assertEquals(film, film1);
    }

    @Test
    void shouldCreateFilm() {
        Film film = new Film(10, "Film", LocalDate.of(2012, 10, 23),
                "description");

        assertEquals(0, filmStorage.findAllFilms().size());

        filmStorage.createFilm(film);

        assertEquals(1, filmStorage.findAllFilms().size());
    }

    @Test
    void shouldUpdateFilm() {
        Film film = new Film(10, "Film", LocalDate.of(2012, 10, 23),
                "description");

        filmStorage.createFilm(film);

        Film film1 = filmStorage.findFilmById(film.getId()).get();

        film1.setDescription("description1");
        filmStorage.updateFilm(film1);

        Film film2 = filmStorage.findFilmById(film.getId()).get();

        assertEquals(film1, film2);
    }

    @Test
    void shouldDeleteFilmById() {
        Film film = new Film(10, "Film", LocalDate.of(2012, 10, 23),
                "description");

        filmStorage.createFilm(film);

        assertEquals(1, filmStorage.findAllFilms().size());

        filmStorage.deleteFilmById(film.getId());

        assertEquals(0, filmStorage.findAllFilms().size());
    }
}