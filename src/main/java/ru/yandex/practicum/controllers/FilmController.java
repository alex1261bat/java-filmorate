package ru.yandex.practicum.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.exceptions.ValidationException;
import ru.yandex.practicum.models.Film;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    private final HashMap<Integer, Film> films = new HashMap<>();

    @GetMapping
    public Collection<Film> findAllFilms() {
        return films.values();
    }

    @PostMapping
    public Film createFilm(@RequestBody Film film) throws ValidationException {
        validateFilm(film);
        log.trace("Сохранен объект: {}", film);
        films.put(film.getId(), film);
        return film;
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film) throws ValidationException {
        validateFilm(film);
        log.trace("Сохранен объект: {}", film);
        films.put(film.getId(), film);
        return film;
    }

    private void validateFilm(Film film) {
        if (film.getName().equals("") || film.getName() == null) {
            log.warn("Название фильма не может быть пустым.");
            throw new ValidationException("Название фильма не может быть пустым.");
        } else if (film.getDescription().length() > 200) {
            log.warn("Длина описания должна быть не боле 200 символов.");
            throw new ValidationException("Длина описания должна быть не боле 200 символов.");
        } else if (film.getReleaseDate().isBefore(LocalDate.of(1985, 12, 28))) {
            log.warn("Дата релиза должна быть не ранее 28 декабря 1985 года.");
            throw new ValidationException("Дата релиза должна быть не ранее 28 декабря 1985 года.");
        } else if (film.getDuration() < 1) {
            log.warn("Продолжительность фильма должна быть положительной.");
            throw new ValidationException("Продолжительность фильма должна быть положительной.");
        }
    }
}