package ru.yandex.practicum.services;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.models.Film;
import ru.yandex.practicum.models.FilmComparator;
import ru.yandex.practicum.storage.film.FilmStorage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Data
public class FilmService {
    private final FilmStorage filmStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public Film addLike(long filmId, long userId) { // метод добавления лайка в фильм
        Film film = filmStorage.findFilmById(filmId);

        film.getLikes().add(userId);
        return film;
    }

    public Film deleteLike(long filmId, long userId) { // метод удаления лайка
        Film film = filmStorage.findFilmById(filmId);

        film.getLikes().remove(userId);
        return film;
    }

    public Collection<Film> findBestFilms(Integer count) { // метод получения списка лучших фильмов
        ArrayList<Film> bestFilms = (ArrayList<Film>) filmStorage.findAllFilms();

        bestFilms.sort(new FilmComparator().reversed());

        if (count == null || count == 0) {
            return new ArrayList<>(bestFilms).stream()
                    .limit(10)
                    .collect(Collectors.toList());
        }

        return bestFilms.stream()
                .limit(count)
                .collect(Collectors.toList());
    }
}
