package ru.yandex.practicum.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.exceptions.ValidationException;
import ru.yandex.practicum.models.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    private static final LocalDate FIRST_RELEASE_DATE = LocalDate.of(1895, 12, 28);
    private final HashMap<Long, Film> films = new HashMap<>();
    private long id;

    @GetMapping
    public Collection<Film> findAllFilms() {
        return new ArrayList<>(films.values());
    }

    @PostMapping
    public Film createFilm(@RequestBody Film film) {
        film.setId(++id);
        validateFilm(film);
        log.trace("Сохранен объект: {}", film);
        films.put(film.getId(), film);
        return film;
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film) {
        validateFilm(film);
        log.trace("Сохранен объект: {}", film);
        films.put(film.getId(), film);
        return film;
    }

    private void validateFilm(Film film) {
        if (film.getName() == null || film.getName().equals("")) {
            log.warn("Название фильма не может быть пустым.");
            throw new ValidationException("Название фильма не может быть пустым.");
        } else if (film.getDescription() == null || film.getDescription().length() > 200) {
            log.warn("Длина описания должна быть не боле 200 символов и не должна быть null.");
            throw new ValidationException("Длина описания должна быть не боле 200 символов и не должна быть null.");
        } else if (film.getReleaseDate().isBefore(FIRST_RELEASE_DATE)) {
            log.warn("Дата релиза должна быть не ранее 28 декабря 1985 года.");
            throw new ValidationException("Дата релиза должна быть не ранее 28 декабря 1985 года.");
        } else if (film.getDuration() < 1) {
            log.warn("Продолжительность фильма должна быть положительной.");
            throw new ValidationException("Продолжительность фильма должна быть положительной.");
        }  else if (film.getId() < 1) {
            log.warn("Неверный id.");
            throw new ValidationException("Неверный id.");
        }
    }
}