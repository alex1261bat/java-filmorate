package ru.yandex.practicum.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.models.Film;
import ru.yandex.practicum.services.FilmService;

import java.util.Collection;

@RestController
@RequestMapping("/films")
public class FilmController {
    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    public Collection<Film> findAllFilms() { // получение списка всех фильмов
        return filmService.getFilmStorage().findAllFilms();
    }

    @GetMapping("/{filmId}")
    public Film findFilmById(@PathVariable long filmId) { // получение фильма по id
        return filmService.getFilmStorage().findFilmById(filmId);
    }

    @PostMapping
    public Film createFilm(@RequestBody Film film) { // создание фильма
        return filmService.getFilmStorage().createFilm(film);
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film) { // обновление фильма
        return filmService.getFilmStorage().updateFilm(film);
    }

    @DeleteMapping("/{filmId}")
    public Film deleteFilmById(@PathVariable long filmId) { // удаление фильма по id
        return filmService.getFilmStorage().deleteFilmById(filmId);
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
    public Collection<Film> findBestFilms(@RequestParam(defaultValue = "10", required = false) Integer count) { // получение списка лучших фильмов
        return filmService.findBestFilms(count);
    }
}