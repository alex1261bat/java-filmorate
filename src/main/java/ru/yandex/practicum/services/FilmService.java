package ru.yandex.practicum.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.exceptions.FilmNotFoundException;
import ru.yandex.practicum.exceptions.UserNotFoundException;
import ru.yandex.practicum.models.Film;
import ru.yandex.practicum.storage.film.FilmStorage;
import ru.yandex.practicum.storage.user.UserStorage;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    public Collection<Film> findAllFilms() {
        return filmStorage.findAllFilms();
    }

    public Film findFilmById(long id) {
        return filmStorage.findFilmById(id).orElseThrow(() -> new FilmNotFoundException(id));
    }

    public Film createFilm(Film film) {
        return filmStorage.createFilm(film);
    }

    public Film updateFilm(Film film) {
        filmStorage.findFilmById(film.getId()).orElseThrow(() -> new FilmNotFoundException(film.getId()));
        return filmStorage.updateFilm(film);
    }

    public Film deleteFilmById(long id) {
        filmStorage.findFilmById(id).orElseThrow(() -> new FilmNotFoundException(id));
        return filmStorage.deleteFilmById(id);
    }

    public Film addLike(long filmId, long userId) { // метод добавления лайка в фильм
        Film film = filmStorage.findFilmById(filmId).orElseThrow(() -> new FilmNotFoundException(filmId));

        userStorage.findUserById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        film.getLikes().add(userId);
        return film;
    }

    public Film deleteLike(long filmId, long userId) { // метод удаления лайка
        Film film = filmStorage.findFilmById(filmId).orElseThrow(() -> new FilmNotFoundException(filmId));

        userStorage.findUserById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        film.getLikes().remove(userId);
        return film;
    }

    public List<Film> findBestFilms(Integer count) { // метод получения списка лучших фильмов
        return filmStorage.findAllFilms().stream()
                .sorted(((o1, o2) -> o2.getLikes().size() - o1.getLikes().size()))
                .limit(count)
                .collect(Collectors.toList());
    }
}