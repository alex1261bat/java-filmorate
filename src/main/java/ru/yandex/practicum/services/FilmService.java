package ru.yandex.practicum.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FilmService(FilmStorage filmStorage, UserStorage userStorage, JdbcTemplate jdbcTemplate) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
        this.jdbcTemplate = jdbcTemplate;
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
        findFilmById(film.getId());
        return filmStorage.updateFilm(film);
    }

    public boolean deleteFilmById(long id) {
        findFilmById(id);
        return filmStorage.deleteFilmById(id);
    }

    public Film addLike(long filmId, long userId) { // метод добавления лайка в фильм
        Film film = findFilmById(filmId);

        userStorage.findUserById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        film.getLikes().add(userId);
        saveLikeToTable(filmId, userId);
        return film;
    }

    public Film deleteLike(long filmId, long userId) { // метод удаления лайка
        Film film = findFilmById(filmId);

        userStorage.findUserById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        film.getLikes().remove(userId);
        deleteLikeFromTable(filmId, userId);
        return film;
    }

    public List<Film> findBestFilms(Integer count) { // метод получения списка лучших фильмов
        return filmStorage.findAllFilms().stream()
                .sorted(((o1, o2) -> o2.getLikes().size() - o1.getLikes().size()))
                .limit(count)
                .collect(Collectors.toList());
    }

    private void saveLikeToTable(long filmId, long userId) {
        String sqlQuery = "insert into film_likes (film_id, user_id) " +
                "values (?, ?)";

        jdbcTemplate.update(sqlQuery, filmId, userId);
    }

    private void deleteLikeFromTable (long filmId, long userId) {
        String sqlQuery = "delete from film_likes where film_id = ? and user_id = ?";

        jdbcTemplate.update(sqlQuery, filmId, userId);
    }
}