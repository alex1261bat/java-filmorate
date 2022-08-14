package ru.yandex.practicum.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.exceptions.ValidationException;
import ru.yandex.practicum.models.Film;
import ru.yandex.practicum.services.FilmService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.Collection;

@Validated
@RestController
@RequestMapping("/films")
public class FilmController {
    private static final LocalDate FIRST_RELEASE_DATE = LocalDate.of(1895, 12, 28);
    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    public Collection<Film> findAllFilms() { // получение списка всех фильмов
        return filmService.findAllFilms();
    }

    @GetMapping("/{filmId}")
    public Film findFilmById(@PathVariable long filmId) { // получение фильма по id
        return filmService.findFilmById(filmId);
    }

    @PostMapping
    public Film createFilm(@Valid @RequestBody Film film) { // создание фильма
        validateFilmReleaseDate(film);
        return filmService.createFilm(film);
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) { // обновление фильма
        return filmService.updateFilm(film);
    }

    @DeleteMapping("/{filmId}")
    public boolean deleteFilmById(@PathVariable long filmId) { // удаление фильма по id
        return filmService.deleteFilmById(filmId);
    }

    @PutMapping("/{filmId}/like/{userId}")
    public Film addLike(@PathVariable long filmId, @PathVariable long userId) { // добавления лайка в фильм
        return filmService.addLike(filmId, userId);
    }

    @DeleteMapping("/{filmId}/like/{userId}")
    public Film deleteLike(@PathVariable long filmId, @PathVariable long userId) { // удаление лайка
        return filmService.deleteLike(filmId, userId);
    }

    @GetMapping("/popular")
    public Collection<Film> findBestFilms(@RequestParam(defaultValue = "10", required = false) @Positive Integer count) { // получение списка лучших фильмов
        return filmService.findBestFilms(count);
    }

    private void validateFilmReleaseDate(Film film) { // метод валидации фильма
        if (film.getReleaseDate().isBefore(FIRST_RELEASE_DATE)) {
            throw new ValidationException("Дата релиза должна быть не ранее 28 декабря 1985 года.");
        }
    }
}