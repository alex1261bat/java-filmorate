package ru.yandex.practicum.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.exceptions.ValidationException;
import ru.yandex.practicum.models.Film;
import ru.yandex.practicum.services.FilmService;

import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.Collection;

@RestController
@RequestMapping("/films")
@Validated
@Slf4j
public class FilmController {
    private static final LocalDate FIRST_RELEASE_DATE = LocalDate.of(1895, 12, 28);
    private final FilmService filmService;
    private long id;

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
    public Film createFilm(@RequestBody Film film) { // создание фильма
        film.setId(++id);
        validateFilm(film);
        log.trace("Сохранен объект: {}", film);
        return filmService.createFilm(film);
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film) { // обновление фильма
        return filmService.updateFilm(film);
    }

    @DeleteMapping("/{filmId}")
    public Film deleteFilmById(@PathVariable long filmId) { // удаление фильма по id
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

    public void validateFilm(Film film) { // метод валидации фильма
        if (film.getName() == null || film.getName().isBlank()) {
            this.id -= 1;
            log.warn("Название фильма не может быть пустым.");
            throw new ValidationException("Название фильма не может быть пустым.");
        } else if (film.getDescription() == null || film.getDescription().length() > 200) {
            this.id -= 1;
            log.warn("Длина описания должна быть не боле 200 символов и не должна быть null.");
            throw new ValidationException("Длина описания должна быть не боле 200 символов и не должна быть null.");
        } else if (film.getReleaseDate() == null || film.getReleaseDate().isBefore(FIRST_RELEASE_DATE)) {
            this.id -= 1;
            log.warn("Дата релиза должна быть не ранее 28 декабря 1985 года.");
            throw new ValidationException("Дата релиза должна быть не ранее 28 декабря 1985 года.");
        } else if (film.getDuration() < 1) {
            this.id -= 1;
            log.warn("Продолжительность фильма должна быть положительной.");
            throw new ValidationException("Продолжительность фильма должна быть положительной.");
        }  else if (film.getId() < 1) {
            log.warn("Неверный id.");
            throw new ValidationException("Неверный id.");
        }
    }
}