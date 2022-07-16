package ru.yandex.practicum.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.models.Film;
import ru.yandex.practicum.storage.film.FilmStorage;
import ru.yandex.practicum.storage.user.UserStorage;

import java.util.ArrayList;
import java.util.Comparator;
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

    public FilmStorage getFilmStorage() {
        return filmStorage;
    }

    public Film addLike(long filmId, long userId) { // метод добавления лайка в фильм
        Film film = filmStorage.findFilmById(filmId);
        userStorage.findUserById(userId);

        film.getLikes().add(userId);
        return film;
    }

    public Film deleteLike(long filmId, long userId) { // метод удаления лайка
        Film film = filmStorage.findFilmById(filmId);
        userStorage.findUserById(userId);

        film.getLikes().remove(userId);
        return film;
    }

    public List<Film> findBestFilms(Integer count) { // метод получения списка лучших фильмов
        List<Film> bestFilms = filmStorage.findAllFilms().stream()
                .sorted(((o1, o2) -> o2.getLikes().size() - o1.getLikes().size()))
                .collect(Collectors.toList());

        if (count == null || count <= 0) {
            return new ArrayList<>(bestFilms).stream()
                    .limit(10)
                    .collect(Collectors.toList());
        }

        return bestFilms.stream()
                .limit(count)
                .collect(Collectors.toList());
    }
}
