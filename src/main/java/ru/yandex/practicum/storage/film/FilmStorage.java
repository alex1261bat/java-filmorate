package ru.yandex.practicum.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.models.Film;

import java.util.Collection;
import java.util.Optional;

@Component
public interface FilmStorage {

    Collection<Film> findAllFilms(); // метод получения списка всех фильмов

    Optional<Film> findFilmById(long id); // метод получения фильма по id

    Film createFilm(Film film); // метод создания фильма

    Film updateFilm(Film film); // метод обновления фильма

    boolean deleteFilmById(long id); // метод удаления фильма по id

    void saveLikeToTable(long filmId, long userId); // добавление лайка в DB

    void deleteLikeFromTable(long filmId, long userId); // удаление лайка из DB
}